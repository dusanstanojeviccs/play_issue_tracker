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