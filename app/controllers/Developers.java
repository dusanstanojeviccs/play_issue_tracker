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
    Boolean error = true;
    public Result viewIssue(long id, Boolean error ) {
        Result[] result = {badRequest()};
        DSDB.withConnection(conn -> {
            Issue issue = Issue.loadById(conn, id);
            List<IssueResponse> issueResponseList = IssueResponse.load(conn, id);
            result[0] = ok(single_issue.render(issue, issueResponseList, error));
        });
        return result[0];
    }

    public Result submitResponse() {
        Result[] results = {badRequest()};
        
        String com = "comment";
        DynamicForm form = Form.form().bindFromRequest();
        if(form.get(com).length() < 2){
        DSDB.withConnection(conn-> {
            long issueId = Long.parseLong(form.get("issueId"));results[0] =  redirect(controllers.routes.Developers.viewIssue(issueId, error));
        });
        }
        else {
        DSDB.withConnection(conn-> {
            long issueId = Long.parseLong(form.get("issueId"));
            Issue.loadById(conn, issueId).insertResponse(conn, Com.getLoggedInUserId(), form.get(com), new Timestamp(new Date().getTime()));
            results[0] =  redirect(controllers.routes.Developers.viewIssue(issueId, !error));
        });
        }
        return results[0];
    }

    public Result changeIssueStatus(Long id) {
        Result[] results = {ok("Bad")};
        DSDB.withConnection(conn-> {
            Issue toSave = Issue.loadById(conn, id);
            toSave.setStatus("Waiting Approval");
            Issue.update(conn, toSave);
            results[0] =  ok("Ok");
        });
        return results[0];
    }
}