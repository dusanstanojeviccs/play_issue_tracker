package models;

import java.sql.*;
import controllers.DSDB;
import java.util.*;

public class QAUser {
	private long id;
	private String username;
	private String password;


    public void parse(ResultSet rs) throws SQLException {
        this.id = rs.getLong("id");
        this.username = rs.getString("username");
        this.password = rs.getString("password");
    }

	public static List<QAUser> load(Connection conn) throws SQLException {
        String query = "SELECT * FROM qa_users";

        PreparedStatement statement = conn.prepareStatement(query);

        List<QAUser> qaList = new LinkedList<QAUser>();

        DSDB.executeAndParse(statement, rs -> {
            QAUser qa = new QAUser();
            qa.parse(rs);
            qaList.add(qa);
        });

        return qaList;
	}

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String toString() {
        return this.username;
    }
}