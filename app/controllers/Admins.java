package controllers;

import play.*;
import play.mvc.*;
import java.util.*;
import models.*;
import views.html.admin.users.user_list;
import views.html.admin.projects.project_list;
import views.html.admin.issues.issue_list;

import play.libs.Json;

import play.mvc.Security;

import controllers.AdminSecurity;

@Security.Authenticated(AdminSecurity.class)
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
                p.setId(post.id);
                Issue.update(conn, p);
            } else {
                p.setId(Issue.insert(conn, p));    
            }
            results[0] = ok(p.getId()+"");
        });
        return results[0];
    }

    public static class UserSaveRequest {
        public Long id;
        public String username;
        public String password;
        public String type;
    }

    public Result saveUser() {
        UserSaveRequest post = Json.fromJson(request().body().asJson(), UserSaveRequest.class);

        Result[] results = {badRequest()};

        DSDB.withConnection(conn -> {
            if (post.type.equals("admin")) {
                Admin u = new Admin();
                u.setId(post.id);
                u.setUsername(post.username);
                u.setPassword(post.password);
                if (post.id!=null && post.id!=0) {
                    Admin.update(conn, u);
                } else {
                    u.setId(Admin.insert(conn, u));    
                }
                results[0] = ok(u.getId()+"");
            } else if (post.type.equals("developer")) {
                Developer u = new Developer();
                u.setId(post.id);
                u.setUsername(post.username);
                u.setPassword(post.password);
                if (post.id!=null && post.id!=0) {
                    Developer.update(conn, u);
                } else {
                    u.setId(Developer.insert(conn, u));    
                }
                results[0] = ok(u.getId()+"");
            } else if (post.type.equals("tester")) {
                QAUser u = new QAUser();
                u.setId(post.id);
                u.setUsername(post.username);
                u.setPassword(post.password);
                if (post.id!=null && post.id!=0) {

                    QAUser.update(conn, u);
                } else {
                    u.setId(QAUser.insert(conn, u));    
                }
                results[0] = ok(u.getId()+"");
            }
        });
        return results[0];
    }

    public static class ProjectSaveRequest {
        public Long id;
        public String name;
    }

/* OVDE JE PROBLEM */
    public Result saveProject() {
        ProjectSaveRequest post = Json.fromJson(request().body().asJson(), ProjectSaveRequest.class);

        Result[] results = {badRequest()};

        DSDB.withConnection(conn -> {
            Project p = new Project();
            p.setName(post.name);
            if (post.id!=null && post.id!=0) {
                p.setId(post.id);
                Project.update(conn, p);
            } else {
                p.setId(Project.insert(conn, p));    
            }
            results[0] = ok(p.getId()+"");
        });
        return results[0];
    }
}