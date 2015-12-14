package models;

import java.sql.*;
import controllers.DSDB;
import java.util.*;


/*
CREATE DATABASE issue_tracker;
USE issue_tracker;

CREATE TABLE admins
(id int NOT NULL AUTO_INCREMENT,
username varchar(30),
password varchar(30),
PRIMARY KEY (id));

*/


public class Admin {
	private long id;
	private String username;
	private String password;

    public void parse(ResultSet rs) throws SQLException {
        this.id = rs.getLong("id");
        this.username = rs.getString("username");
        this.password = rs.getString("password");
    }

	public static List<Admin> load(Connection conn) throws SQLException {
        String query = "SELECT * FROM admins";

        PreparedStatement statement = conn.prepareStatement(query);

        List<Admin> adminList = new LinkedList<Admin>();

        DSDB.executeAndParse(statement, rs -> {
            Admin admin = new Admin();
            admin.parse(rs);

            adminList.add(admin);
        });

        return adminList;
	}
    // public long insert(Connection conn, Admin admin) {
        
    // }

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
}