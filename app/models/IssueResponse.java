
package models;

import java.sql.*;
import controllers.DSDB;
import java.util.*;
import play.db.DB;

public class IssueResponse {
	private long id;
	private long userId;
	private String content;
	private Timestamp timestamp;
    private String user;
    private String userType;

	public void parse(ResultSet rs) throws SQLException {
        this.id = rs.getLong("id");
        this.userId = rs.getLong("user_id");
        this.content = rs.getString("content").replace("\n", "<br />");
        this.timestamp = rs.getTimestamp("date_added");
        this.user = rs.getString("username");
        this.userType = rs.getString("type");
    }

	public static List<IssueResponse> load(Connection conn, long issueId) throws SQLException {
        String query = "SELECT * FROM `issue_response` JOIN users ON issue_response.user_id=users.id WHERE issue_id = ? ORDER BY `issue_response`.`date_added` ASC";

        PreparedStatement statement = conn.prepareStatement(query);
        statement.setLong(1, issueId);

        List<IssueResponse> issueResponseList = new LinkedList<IssueResponse>();

        DSDB.executeAndParse(statement, rs -> {
            IssueResponse issueResponse = new IssueResponse();
            issueResponse.parse(rs);
            issueResponseList.add(issueResponse);
        });

        return issueResponseList;
	}

	 public long getId() {
        return id;
    }

    public long getUserId(){
    	return userId;
    }

    public String getContent(){
    	return content;
    }
    public Timestamp getTimestamp(){
    	return timestamp;
    }
    public String getUser(){
    	return user;
    }
    public String getUserType(){
        return userType;
    }
    public void setUserType(String userType){
        this.userType = userType;
    }
    public void setId(long id) {
        this.id = id;
    }
    public void setUserId(long userId){
    	this.userId = userId;
    }
    public void setContent(String content){
    	this.content = content;
    }
    public void setTimestamp(Timestamp timestamp){
    	this.timestamp = timestamp;
    }
}