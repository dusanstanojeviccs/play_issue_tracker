package controllers;

import play.*;
import play.mvc.*;
import views.html.*;
import java.util.*;
import models.*;
import play.data.DynamicForm;
import play.data.Form;

import static play.mvc.Controller.session;

public class Application extends Controller {

    public Result index() {
    	return ok(index.render(false));
    }

    public Result logout() {
        session().clear();
    	return redirect(controllers.routes.Application.index());	
    }

    public Result login() {
    	Result[] result = {badRequest()};
    	DynamicForm form = Form.form().bindFromRequest();

	    if (form.data().size() != 2) {
	    	result[0] = ok(index.render(true));    
	    } else {
            DSDB.withConnection(conn -> {
                Admin loadedAdmin = null;
                QAUser loadedQA = null;
                Developer loadedDeveloper = null;
                if ((loadedAdmin = Admin.load(conn, form.get("username"), form.get("password")))!=null) {
                    session("admin_loggedin", "yes");
                    session("user_id", String.valueOf(loadedAdmin.getId()));
                    session("user_username", form.get("username"));
                    session("user_type", "Admin");
                    result[0] = redirect(controllers.routes.Admins.users());
                } else if ((loadedDeveloper = Developer.load(conn, form.get("username"), form.get("password")))!=null) {
                    session("developer_loggedin", "yes");
                    session("user_id", String.valueOf(loadedDeveloper.getId()));
                    session("user_username", form.get("username"));
                    session("user_type", "Developer");
                    result[0] = redirect(controllers.routes.Developers.viewProjects());
                } else if ((loadedQA = QAUser.load(conn, form.get("username"), form.get("password")))!=null) {
                    session("qa_loggedin", "yes");
                    session("user_id", String.valueOf(loadedQA.getId()));
                    session("user_username", form.get("username"));
                    session("user_type", "Tester");
                    result[0] = redirect(controllers.routes.QAUsers.viewProjects());
                } else {
                    result[0] = ok(index.render(true));
                }
        
            });
    	}
		
    	
        return result[0];
    }
}
