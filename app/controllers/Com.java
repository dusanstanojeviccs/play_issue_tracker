package controllers;
import models.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import static play.mvc.Controller.session;

public class Com {
	public static final String[] ADMIN_PAGE_NAMES = {"Users", "Projects", "Issues"};
	public static final String[] DEVELOPER_PAGE_NAMES = {"Projects", "Issues"};
	public static final String[] QA_PAGE_NAMES = {"Projects", "Issues"};

	public static String getType() {
		return session("user_type");
	}

	public static String getUsername() {
		return session("user_username");
	}

	public static long getLoggedInUserId() {
		return Long.parseLong(session("user_id"));
	}

	public static Admin getLoggedInAdmin(Connection conn) throws SQLException {
		return Admin.loadById(conn, Long.parseLong(session("user_id")));
	}
	public static QAUser getLoggedInQA(Connection conn) throws SQLException {
		return QAUser.loadById(conn, Long.parseLong(session("user_id")));
	}
	public static Developer getLoggedInDeveloper(Connection conn) throws SQLException {
		return Developer.loadById(conn, Long.parseLong(session("user_id")));
	}

	public static String formatTimestamp(Timestamp t) {
		return new SimpleDateFormat("DD/MM/YYYY HH:mm:ss").format(t);
	}
}