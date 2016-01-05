package controllers;

import play.*;
import play.mvc.*;
import java.util.*;
import models.*;
import views.html.developer.projects.project_list;
import views.html.developer.issues.*;
import play.libs.Json;
import play.mvc.Security;
import java.sql.Timestamp;
import java.util.Date;
import play.data.DynamicForm;
import play.data.Form;

@Security.Authenticated(DeveloperSecurity.class)
public class Developers extends Controller {
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
            result[0] = ok(issue_list.render(FullIssue.loadByProject(conn, projectId)));
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
        Result[] results = {badRequest()};
        
        DynamicForm form = Form.form().bindFromRequest();

        DSDB.withConnection(conn-> {
            long issueId = Long.parseLong(form.get("issueId"));
            Issue.loadById(conn, issueId).insertResponse(conn, Com.getLoggedInUserId(), form.get("comment"), new Timestamp(new Date().getTime()));
            results[0] = viewIssue(issueId);
        });

        return results[0];
    }

    public static class IssueSaveRequest {
        private Long id;
        private String status;
    }

    public Result saveIssue() {
    	IssueSaveRequest post = Json.fromJson(request().body().asJson(), IssueSaveRequest.class);
		//change issue types to waiting approval

        Result[] results = {badRequest()};

        DSDB.withConnection(conn -> {
            Issue p = Issue.loadById(conn, post.id);
            p.setStatus(post.status);
            Issue.update(conn, p);
            results[0] = ok("Ok");
        });
        return results[0];
    }
}