package models;

import java.sql.*;
import controllers.DSDB;
import java.util.*;

public class Project {
	private long id;
	private String name;

	public void parse(ResultSet rs) throws SQLException {
        this.id = rs.getLong("id");
        this.name = rs.getString("name");
    }

	public static List<Project> load(Connection conn) throws SQLException {
        String query = "SELECT * FROM projects";

        PreparedStatement statement = conn.prepareStatement(query);

        List<Project> projectList = new LinkedList<Project>();

        DSDB.executeAndParse(statement, rs -> {
            Project project = new Project();
            project.parse(rs);

            projectList.add(project);
        });

        return projectList;
	}

	public List<Developer> getDevelopers(Connection conn) throws SQLException {
		String query = "SELECT * FROM developers JOIN developers_projects "+
						"ON developers.id=developers_projects.developer_id WHERE "+
						"developers_projects.project_id = ?";

        PreparedStatement statement = conn.prepareStatement(query);
        
        //statement.bind(0, id);

        List<Developer> developerList = new LinkedList<Developer>();

        DSDB.executeAndParse(statement, rs -> {
            Developer dev = new Developer();
            dev.parse(rs);
            developerList.add(dev);
        });

        return developerList;
	}

	public List<QAUser> getQAUsers(Connection conn) throws SQLException {
        String query = "SELECT * FROM qa_users JOIN qa_users_projects "+
                        "ON qa_users.id=qa_users_projects.qa_user_id WHERE "+
                        "qa_users_projects.project_id = ?";

        PreparedStatement statement = conn.prepareStatement(query);
        
        //statement.bind(0, id);

        List<QAUser> qaList = new LinkedList<QAUser>();

        DSDB.executeAndParse(statement, rs -> {
            QAUser qa = new QAUser();
            qa.parse(rs);
            qaList.add(qa);
        });

        return qaList;
	}

    public static long insert(Connection conn, Project project) throws SQLException {
        String query = "INSERT INTO `projects`(id, `name`) VALUES (null, ?)";

        PreparedStatement statement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        int i = 1;
        statement.setString(i++, project.name);

        long[] toRet = {0l};

        DSDB.executeAndParse(statement, rs -> {
            toRet[0] = rs.getLong(1);
        });

        return toRet[0];
    }

	public List<Issue> getIssues(Connection conn) throws SQLException {
        String query = "SELECT * FROM issues WHERE project_id=?";

        PreparedStatement statement = conn.prepareStatement(query);
        //statement.bind(0, id);
        List<Issue> issueList = new LinkedList<Issue>();

        DSDB.executeAndParse(statement, rs -> {
            Issue issue = new Issue();
            issue.parse(rs);
            issueList.add(issue);
        });

        return issueList;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setId(long id) {
		this.id = id;
	}

    public long getId() {
        return id;
    }

    public String getName() {
    	return name;
    }
}