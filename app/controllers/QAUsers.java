package controllers;

import play.*;
import play.mvc.*;
import java.util.*;
import models.*;
import views.html.qa.projects.project_list;
import views.html.qa.issues.issue_list;

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
        private String title;
        private String text;
        private String status;
    }

    public Result saveIssue() {
    	IssueSaveRequest post = Json.fromJson(request().body().asJson(), IssueSaveRequest.class);

        Result[] results = {badRequest()};

        DSDB.withConnection(conn -> {
            Issue p = new Issue();
            p.setTitle(post.title);
            p.setText(post.text);
            p.setStatus(post.status);
            if (post.id!=null && post.id!=0) {
                Issue.update(conn, p);
            } else {
                p.setId(Issue.insert(conn, p));    
            }
            results[0] = ok("Ok");
        });
        return results[0];
    }
}