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
import play.data.DynamicForm;
import play.data.Form;

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
            Issue issue = Issue.loadById(conn, id);
            List<IssueResponse> issueResponseList = IssueResponse.load(conn, id);
            result[0] = ok(single_issue.render( 
                issue, issueResponseList));
        });
        return result[0];
    }

    public Result submitResponse() {
        Result[] results = {badRequest()};
        
        DynamicForm form = Form.form().bindFromRequest();

        DSDB.withConnection(conn-> {
            long issueId = Long.parseLong(form.get("issueId"));
            Issue.loadById(conn, issueId).insertResponse(conn, Com.getLoggedInUserId(), form.get("comment"), new Timestamp(new Date().getTime()));
            results[0] =  redirect(controllers.routes.QAUsers.viewIssue(issueId));
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