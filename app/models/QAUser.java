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
        String query = "SELECT * FROM users WHERE type='Tester'";

        PreparedStatement statement = conn.prepareStatement(query);

        List<QAUser> qaList = new LinkedList<QAUser>();

        DSDB.executeAndParse(statement, rs -> {
            QAUser qa = new QAUser();
            qa.parse(rs);
            qaList.add(qa);
        });

        return qaList;
	}

    public static QAUser load(Connection conn, String username, String password)throws SQLException{
        String query = "SELECT * FROM users where username = ? and password = ? and type='Tester'";

        PreparedStatement statement = conn.prepareStatement(query);
        int i = 1;
        statement.setString(i++, username);
        statement.setString(i++, password);

        List<QAUser> qaList = new LinkedList<QAUser>();

        DSDB.executeAndParse(statement, rs ->{
            QAUser qa = new QAUser();
            qa.parse(rs);

            qaList.add(qa);
        });
        return qaList.isEmpty()?null:qaList.get(0);
    }
    public static QAUser loadById(Connection conn, long id) throws SQLException {
        String query = "SELECT * FROM users where id = ? and type='Tester'";

        PreparedStatement statement = conn.prepareStatement(query);
        int i = 1;
        statement.setLong(i++, id);

        List<QAUser> qaList = new LinkedList<QAUser>();

        DSDB.executeAndParse(statement, rs -> {
            QAUser qa = new QAUser();
            qa.parse(rs);

            qaList.add(qa);
        });

        return qaList.isEmpty()?null:qaList.get(0);
    }

    public static long insert(Connection conn, QAUser qa) throws SQLException {
        String query = "INSERT INTO `users`(id, `username`, `password`, type) VALUES (null, ?, ?, 'Tester')";

        PreparedStatement statement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        int i = 1;
        statement.setString(i++, qa.username);
        statement.setString(i++, qa.password);

        statement.executeUpdate();
        ResultSet resultSet = statement.getGeneratedKeys();
        if (resultSet.next())
            return resultSet.getLong(1);

        return 0l;
    }

    public static void update(Connection conn, QAUser qa) throws SQLException {
        final String query = "UPDATE `users` SET `username`=?, password=? WHERE id=?";

        PreparedStatement statement = conn.prepareStatement(query);

        int i = 1;
        statement.setString(i++, qa.username);
        statement.setString(i++, qa.password);
        statement.setLong(i++, qa.id);

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
    public String toString() {
        return this.username;
    }
}