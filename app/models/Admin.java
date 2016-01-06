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
        String query = "SELECT * FROM users WHERE type='Admin'";

        PreparedStatement statement = conn.prepareStatement(query);

        List<Admin> adminList = new LinkedList<Admin>();

        DSDB.executeAndParse(statement, rs -> {
            Admin admin = new Admin();
            admin.parse(rs);

            adminList.add(admin);
        });

        return adminList;
	}
    public static Admin load(Connection conn, String username, String password) throws SQLException {
        String query = "SELECT * FROM users where type='Admin' and username = ? and password = ?";

        PreparedStatement statement = conn.prepareStatement(query);
        int i = 1;
        statement.setString(i++, username);
        statement.setString(i++, password);

        List<Admin> adminList = new LinkedList<Admin>();

        DSDB.executeAndParse(statement, rs -> {
            Admin admin = new Admin();
            admin.parse(rs);

            adminList.add(admin);
        });

        return adminList.isEmpty()?null:adminList.get(0);
    }

    public static Admin loadById(Connection conn, long id) throws SQLException {
        String query = "SELECT * FROM users where id = ? and type='Admin'";

        PreparedStatement statement = conn.prepareStatement(query);
        int i = 1;
        statement.setLong(i++, id);

        List<Admin> adminList = new LinkedList<Admin>();

        DSDB.executeAndParse(statement, rs -> {
            Admin admin = new Admin();
            admin.parse(rs);

            adminList.add(admin);
        });

        return adminList.isEmpty()?null:adminList.get(0);
    }

    public static long insert(Connection conn, Admin admin) throws SQLException {
        String query = "INSERT INTO `users`(id, `username`, `password`, type) VALUES (null, ?, ?, 'Admin')";

        PreparedStatement statement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        int i = 1;
        statement.setString(i++, admin.username);
        statement.setString(i++, admin.password);

        statement.executeUpdate();
        ResultSet resultSet = statement.getGeneratedKeys();
        if (resultSet.next())
            return resultSet.getLong(1);

        return 0l;
    }

    public static void update(Connection conn, Admin admin) throws SQLException {
        final String query = "UPDATE `users` SET `username`=?, password=? WHERE id=? and type='Admin'";

        PreparedStatement statement = conn.prepareStatement(query);

        int i = 1;
        statement.setString(i++, admin.username);
        statement.setString(i++, admin.password);
        statement.setLong(i++, admin.id);

        statement.execute();
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
}