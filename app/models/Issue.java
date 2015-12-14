package models;

import java.sql.*;
import controllers.DSDB;
import java.util.*;

public class Issue {
	private long id;
	private long postedBy;
	private String title;
	private String text;
	private String status;

	public void parse(ResultSet rs) throws SQLException {
        this.id = rs.getLong("id");
        this.postedBy = rs.getLong("posted_by");
        this.title = rs.getString("title");
        this.text = rs.getString("text");
        this.status = rs.getString("text");
    }

	public static List<Issue> load(Connection conn) throws SQLException {
        String query = "SELECT * FROM issues";

        PreparedStatement statement = conn.prepareStatement(query);

        List<Issue> issueList = new LinkedList<Issue>();

        DSDB.executeAndParse(statement, rs -> {
            Issue issue = new Issue();
            issue.parse(rs);
            issueList.add(issue);
        });

        return issueList;
	}

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}