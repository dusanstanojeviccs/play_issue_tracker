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

	 public Result viewIssue(long id, Boolean error) {
        Result[] result = {badRequest()};
        DSDB.withConnection(conn -> {
            Issue issue = Issue.loadById(conn, id);
            List<IssueResponse> issueResponseList = IssueResponse.load(conn, id);
            result[0] = ok(single_issue.render( 
                issue, issueResponseList, error));
        });
        return result[0];
    }

    public Result submitResponse() {
        Result[] results = {badRequest()};
        
        DynamicForm form = Form.form().bindFromRequest();
        if(form.get("comment").length() < 2){
            DSDB.withConnection(conn-> {
                long issueId = Long.parseLong(form.get("issueId"));
                results[0] =  redirect(controllers.routes.QAUsers.viewIssue(issueId, true));
            });
        }
        else{
            DSDB.withConnection(conn-> {
                long issueId = Long.parseLong(form.get("issueId"));
                Issue.loadById(conn, issueId).insertResponse(conn, Com.getLoggedInUserId(), form.get("comment"), new Timestamp(new Date().getTime()));
                results[0] =  redirect(controllers.routes.QAUsers.viewIssue(issueId, false));
            });
        }
        return results[0];
    }

    public static class IssueStatusChangeRequest {
        public Long id;
        public String newStatus;
    }

    public Result changeIssueStatus() {
        IssueStatusChangeRequest post = Json.fromJson(request().body().asJson(), IssueStatusChangeRequest.class);
        Result[] results = {ok("Bad")};
        DSDB.withConnection(conn-> {
            Issue toSave = Issue.loadById(conn, post.id);
            toSave.setStatus(post.newStatus);
            Issue.update(conn, toSave);
            results[0] =  ok("Ok");
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