package controllers;

import play.*;
import play.mvc.*;
import views.html.*;
import java.util.*;
import models.*;
import play.data.DynamicForm;
import play.data.Form;

public class Application extends Controller {

    public Result index() {
    	return ok(index.render(false));
    }

    public Result logout() {
    	return redirect(controllers.routes.Application.index());	
    }

    public Result login() {
    	Result[] result = {badRequest()};
    	DynamicForm form = Form.form().bindFromRequest();

	    if (form.data().size() != 2) {
	    	result[0] = ok(index.render(true));    
	    } else {
            DSDB.withConnection(conn -> {
                if (Admin.load(conn, form.get("username"), form.get("password"))!=null)
                    result[0] = redirect(controllers.routes.Admins.users());
                else if(Developer.load(conn, form.get("username"), form.get("password"))!=null)
                    result[0] = redirect(controllers.routes.Admins.users());
                else
                    result[0] = ok(index.render(true));
        
            });
    	}
		
    	
        return result[0];
    }
}
