package controllers;

import play.*;
import play.mvc.*;
import java.util.*;
import models.*;
import views.html.admin.users.user_list;

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
    	return ok("Yo");
    }

    public Result projects() {
    	return ok("Yo");
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
    	return ok("Yo");
    }
}