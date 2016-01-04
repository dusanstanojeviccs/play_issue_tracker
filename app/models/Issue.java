package models;

import java.sql.*;
import controllers.DSDB;
import java.util.*;
import play.db.DB;

public class Issue {
	private long id;
    private long projectId;
	private long postedBy;
	private String title;
	private String text;
	private String status;

	public void parse(ResultSet rs) throws SQLException {
        this.id = rs.getLong("id");
        this.postedBy = rs.getLong("posted_by");
        this.title = rs.getString("title");
        this.text = rs.getString("text");
        this.status = rs.getString("status");
        this.projectId = rs.getLong("project_id");
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
    public static Issue loadById(Connection conn, long id) throws SQLException {
        String query = "SELECT * FROM issues WHERE id=?";

        PreparedStatement statement = conn.prepareStatement(query);
        statement.setLong(1, id);
        List<Issue> issueList = new LinkedList<Issue>();

        DSDB.executeAndParse(statement, rs -> {
            Issue issue = new Issue();
            issue.parse(rs);
            issueList.add(issue);
        });

        return issueList.get(0);
    }


    public static long insert(Connection conn, Issue issue) throws SQLException {
        String query = "INSERT INTO `issues`(id, `posted_by`, `title`, `text`, `status`, project_id) VALUES (null, ?, ?, ?, ?, ?)";

        PreparedStatement statement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        int i = 1;
        statement.setLong(i++, issue.postedBy);
        statement.setString(i++, issue.title);
        statement.setString(i++, issue.text);
        statement.setString(i++, issue.status);
        statement.setLong(i++, issue.projectId);
        statement.executeUpdate();
        ResultSet resultSet = statement.getGeneratedKeys();
        if (resultSet.next())
            return resultSet.getLong(1);

        return 0l;
    }

    public long insertResponse(Connection conn, long userId, String content, Timestamp timestamp) throws SQLException {
        String query = "INSERT INTO `issue_response`(id, `issue_id`, `user_id`, `content`, date_added) VALUES (null, ?, ?, ?, ?)";

        PreparedStatement statement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        int i = 1;
        statement.setLong(i++, this.id);
        statement.setLong(i++, userId);
        statement.setString(i++, content);
        statement.setTimestamp(i++, timestamp);
        statement.executeUpdate();
        ResultSet resultSet = statement.getGeneratedKeys();
        if (resultSet.next())
            return resultSet.getLong(1);

        return 0l;
    }

    public static void update(Connection conn, Issue issue) throws SQLException {
        String query = "UPDATE `issues` SET `posted_by`=?, `title`=?, `text`=?, `status`=?, project_id=? WHERE id = ?)";


        PreparedStatement statement = conn.prepareStatement(query);

        int i = 1;
        statement.setLong(i++, issue.postedBy);
        statement.setString(i++, issue.title);
        statement.setString(i++, issue.text);
        statement.setString(i++, issue.status);
        statement.setLong(i++, issue.projectId);
        statement.setLong(i++, issue.id);

        statement.execute();
    }

    public long getId() {
        return id;
    }

    public long getPostedBy() throws SQLException {
        return this.postedBy;
    }

    public QAUser getQAUser (Connection conn) throws SQLException {
        return QAUser.loadById(conn, postedBy);
    }
    
    public String getTitle() {
        return title;
    }
    public String getText(){
        return text;
    }
    public String getStatus(){
        return status;
    }

    public void setId(long id) {
        this.id = id;
    }
    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }
    public void setPostedBy(long postedBy){
        this.postedBy = postedBy;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setText(String text){
        this.text = text;
    }
    public void setStatus(String status){
        this.status = status;
    }
}