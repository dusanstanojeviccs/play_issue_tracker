package controllers;

import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;

import static play.mvc.Controller.session;

public class AdminSecurity extends Security.Authenticator {

    @Override
    public String getUsername(Http.Context ctx) {
        String adminLoggedIn = session("admin_loggedin");

        if(adminLoggedIn == null || !"yes".equals(adminLoggedIn)) {
            return null;
        }
        else {
            return "OK";
        }
    }

    @Override
    public Result onUnauthorized(Http.Context ctx) {
        session().clear();
        return redirect(controllers.routes.Application.login());
    }
}
