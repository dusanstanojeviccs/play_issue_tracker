package models;

import java.sql.*;
import controllers.DSDB;
import java.util.*;
import play.db.DB;

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
        this.status = rs.getString("status");
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

    public QAUser getPostedBy() throws SQLException {
        return getQAUser(this.postedBy);
    }

    public QAUser getQAUser (long id) throws SQLException {
        Connection conn = DB.getConnection();
        QAUser qa = new QAUser();
        String query = "SELECT * FROM qa_users WHERE id=?";

        PreparedStatement st = conn.prepareStatement(query);
        st.setLong(1, id);

        ResultSet rs = st.executeQuery();

        while (rs.next()) {
            qa.setId(rs.getLong("id"));
            qa.setUsername(rs.getString("username"));
            qa.setPassword(rs.getString("password"));
        }
        conn.close();
        return qa;
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