package controllers;

import play.*;
import play.mvc.*;
import java.util.*;
import models.*;
import views.html.qa.projects.project_list;
import views.html.qa.issues.*;
import java.sql.Timestamp;
import java.util.Date;
import play.libs.Json;
import play.mvc.Security;

@Security.Authenticated(QaSecurity.class)
public class QAUsers extends Controller {
	public Result viewProjects() {
    	Result[] result = {badRequest()};
        DSDB.withConnection(conn -> {
            List<Project> projectList = Project.load(conn);

            result[0] = ok(project_list.render( 
                projectList));
        });
        return result[0];
	}
	public Result viewIssues(long projectId) {
        Result[] result = {badRequest()};
        DSDB.withConnection(conn -> {
            result[0] = ok(issue_list.render(FullIssue.loadByProject(conn, projectId), projectId));
        });
        return result[0];
    }

	 public Result viewIssue(long id) {
        Result[] result = {badRequest()};
        DSDB.withConnection(conn -> {
            Issue issueList = Issue.loadById(conn, id);
            result[0] = ok(single_issue.render( 
                issueList));
        });
        return result[0];
    }
    public static class IssueResponse {
        public Long issueId;
        public String content;
    }
    public Result submitResponse() {
        IssueResponse post = Json.fromJson(request().body().asJson(), IssueResponse.class);
        Result[] results = {badRequest()};
        
        DSDB.withConnection(conn-> {
            results[0] = ok(Json.stringify(Json.toJson(Issue.loadById(conn, post.issueId).insertResponse(conn, Com.getLoggedInUserId(), post.content, new Timestamp(new Date().getTime())))));
        });
        

        return results[0];
    }

    public static class IssueSaveRequest {
        public Long id;
        public String title;
        public String text;
        public String status;
        public long projectId;
    }

    public Result saveIssue() {
    	IssueSaveRequest post = Json.fromJson(request().body().asJson(), IssueSaveRequest.class);

        Result[] results = {badRequest()};

        DSDB.withConnection(conn -> {
            Issue p = new Issue();
            p.setTitle(post.title);
            p.setText(post.text);
            p.setStatus(post.status);
            p.setProjectId(post.projectId);
            p.setPostedBy(Com.getLoggedInUserId());
            if (post.id!=null && post.id!=0) {
                p.setId(post.id);
                Issue.update(conn, p);
            } else {
                p.setId(Issue.insert(conn, p));    
            }
            results[0] = ok(""+p.getId());
        });
        return results[0];
    }

}