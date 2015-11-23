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
    		result[0] = ok(user_list.render("Primer prosledjivanja String-a", adminList));
    	});
        return result[0];
    }
}