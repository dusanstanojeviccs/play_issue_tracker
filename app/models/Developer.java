package models;

import java.sql.*;
import controllers.DSDB;
import java.util.*;

public class Developer {
	private long id;
	private String username;
	private String password;
	
    public void parse(ResultSet rs) throws SQLException {
        this.id = rs.getLong("id");
        this.username = rs.getString("username");
        this.password = rs.getString("password");
    }

	public static List<Developer> load(Connection conn) throws SQLException {
        String query = "SELECT * FROM developers";

        PreparedStatement statement = conn.prepareStatement(query);

        List<Developer> developerList = new LinkedList<Developer>();

        DSDB.executeAndParse(statement, rs -> {
            Developer dev = new Developer();
            dev.parse(rs);
            developerList.add(dev);
        });

        return developerList;
	}
    public static Developer load(Connection conn, String username, String password)throws SQLException{
        String query = "SELECT * FROM developers where username = ? and password = ?";

        PreparedStatement statement = conn.prepareStatement(query);
        int i = 1;
        statement.setString(i++, username);
        statement.setString(i++, password);

        List<Developer> developerList = new LinkedList<Developer>();

        DSDB.executeAndParse(statement, rs ->{
            Developer developer = new Developer();
            developer.parse(rs);

            developerList.add(developer);
        });
        return developerList.isEmpty()?null:developerList.get(0);
    }

    public static Developer loadById(Connection conn, long id) throws SQLException {
        String query = "SELECT * FROM developers where id = ?";

        PreparedStatement statement = conn.prepareStatement(query);
        int i = 1;
        statement.setLong(i++, id);

        List<Developer> devList = new LinkedList<Developer>();

        DSDB.executeAndParse(statement, rs -> {
            Developer dev = new Developer();
            dev.parse(rs);

            devList.add(dev);
        });

        return devList.isEmpty()?null:devList.get(0);
    }

    public static long insert(Connection conn, Developer developer) throws SQLException {
        String query = "INSERT INTO `developers`(id, `username`, `password`) VALUES (null, ?, ?)";

        PreparedStatement statement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        int i = 1;
        statement.setString(i++, developer.username);
        statement.setString(i++, developer.password);

        statement.executeUpdate();
        ResultSet resultSet = statement.getGeneratedKeys();
        if (resultSet.next())
            return resultSet.getLong(1);

        return 0l;
    }

    public static void update(Connection conn, Developer developer) throws SQLException {
        final String query = "UPDATE `developers` SET `username`=?, password=? WHERE id=?";

        PreparedStatement statement = conn.prepareStatement(query);

        int i = 1;
        statement.setString(i++, developer.username);
        statement.setString(i++, developer.password);
        statement.setLong(i++, developer.id);

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