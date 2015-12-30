package controllers;

import play.*;
import play.mvc.*;
import java.util.*;
import models.*;
import views.html.admin.users.user_list;
import views.html.admin.projects.project_list;
import views.html.admin.issues.issue_list;

public class Admins extends Controller {
	public Result users() {
    	Result[] result = {badRequest()};
        DSDB.withConnection(conn -> {
            List<Admin> adminList = Admin.load(conn);
            List<Developer> developerList = Developer.load(conn);
            List<Issue> issueList = Issue.load(conn);
            List<Project> projectList = Project.load(conn);
            List<QAUser> qaUserList = QAUser.load(conn);

            result[0] = ok(user_list.render( 
                adminList, developerList, issueList,
                projectList, qaUserList));
        });
        return result[0];
    }

    public Result issues() {
    	Result[] result = {badRequest()};
        DSDB.withConnection(conn -> {
            List<Issue> issueList = Issue.load(conn);
            result[0] = ok(issue_list.render( 
                issueList));
        });
        return result[0];
    }

    public Result projects() {
    	Result[] result = {badRequest()};
        DSDB.withConnection(conn -> {
            List<Project> projectList = Project.load(conn);

            result[0] = ok(project_list.render( 
                projectList));
        });
        return result[0];
    }

    public Result updateIssue() {
    	return ok("Yo");
    }

    public Result updateProject() {
    	return ok("Yo");
    }
    public static class UserSaveRequest {
        public Long id;
        public String username;
        public String type;
    }

    public Result saveUser() {
        return ok("Successfully you add users");
    }

    public static class ProjectSaveRequest {
        public Long id;
        public String name;
    }

/* OVDE JE PROBLEM */
    public Result saveProject() {
        DSDB.withConnection(conn -> {
            Project p = new Project();
            p.setName("YOLO");
            p.setId (Project.insert(conn, p));
        });
        return ok("YOLO");
    }
}