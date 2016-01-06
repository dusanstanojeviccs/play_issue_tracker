package models;

import java.sql.*;
import controllers.DSDB;
import java.util.*;

public class FullIssue {
	public Issue issue;
	public String projectName;
	public String postedBy;
	public static Long id;
	public void parse(ResultSet rs) throws SQLException {
		issue = new Issue();
		issue.parse(rs);
		postedBy = rs.getString("username");
	}
	public static List<FullIssue> loadByProject(Connection conn, long projectId) throws SQLException {
		String query = "SELECT issues.*, users.username FROM issues JOIN users ON issues.posted_by = users.id WHERE issues.project_id = ?";
		
		PreparedStatement statement = conn.prepareStatement(query);
        statement.setLong(1, projectId);
        List<FullIssue> issueList = new LinkedList<FullIssue>();

        DSDB.executeAndParse(statement, rs -> {
            FullIssue issues = new FullIssue();
            issues.parse(rs);
            issueList.add(issues);
        });

        return issueList;
	}

	public Issue getIssue() {
		return issue;
	}

}