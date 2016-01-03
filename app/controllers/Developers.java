package controllers;

import play.*;
import play.mvc.*;
import java.util.*;
import models.*;
import views.html.developer.projects.project_list;
import views.html.developer.issues.issue_list;
import play.libs.Json;
import play.mvc.Security;

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
	public Result viewIssues() {
		Result[] result = {badRequest()};
        DSDB.withConnection(conn -> {
            List<Issue> issueList = Issue.load(conn);
            result[0] = ok(issue_list.render( 
                issueList));
        });
        return result[0];
	}
	
	public Result submitResponse() {
		return ok("Ok");
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