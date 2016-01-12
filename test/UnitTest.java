import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.*;

import play.mvc.*;
import play.test.*;
import play.data.DynamicForm;
import play.data.validation.ValidationError;
import play.data.validation.Constraints.RequiredValidator;
import play.i18n.Lang;
import play.libs.F;
import play.libs.F.*;
import play.twirl.api.Content;
import java.sql.Timestamp;
import java.util.Date;

import java.sql.*;
import controllers.DSDB;
import java.util.*;
import models.*;
import controllers.*;

import play.*;
import play.mvc.*;
import java.util.*;
import models.*;
import views.html.admin.users.user_list;
import views.html.admin.projects.project_list;
import views.html.admin.issues.issue_list;

import play.libs.Json;

import play.mvc.Security;

import controllers.AdminSecurity;


import static play.test.Helpers.*;
import static org.junit.Assert.*;

public class UnitTest {

    @Test
    public void simpleCheck() {
        int a = 1 + 1;
        assertEquals(2, a);
    }

    @Test
    public void testParseAdminId() {
        Admin testAdmin = new Admin();
        testAdmin.setId(1);

        assertEquals(1, testAdmin.getId());
    }

    @Test
    public void testParseAdminUsername() {
        Admin testAdmin = new Admin();
        testAdmin.setUsername("AdminTest");

        assertEquals("AdminTest", testAdmin.getUsername());
    }

    @Test
    public void testParseAdminPassword() {
        Admin testAdmin = new Admin();
        testAdmin.setPassword("AdminTest");

        assertEquals("AdminTest", testAdmin.getPassword());
    }



    @Test
    public void testParseDeveloperId() {
        Developer testDeveloper = new Developer();
        testDeveloper.setId(1);

        assertEquals(1, testDeveloper.getId());
    }

    @Test
    public void testParseDeveloperUsername() {
        Developer testDeveloper = new Developer();
        testDeveloper.setUsername("DeveloperTest");

        assertEquals("DeveloperTest", testDeveloper.getUsername());
    }

    @Test
    public void testParseDeveloperPassword() {
        Developer testDeveloper = new Developer();
        testDeveloper.setPassword("DeveloperTest");

        assertEquals("DeveloperTest", testDeveloper.getPassword());
    }

    @Test
    public void testParseQAUserId() {
        QAUser testQAUser = new QAUser();
        testQAUser.setId(1);

        assertEquals(1, testQAUser.getId());
    }

    @Test
    public void testParseQAUserUsername() {
        QAUser testQAUser = new QAUser();
        testQAUser.setUsername("QAUserTest");

        assertEquals("QAUserTest", testQAUser.getUsername());
    }

    @Test
    public void testParseQAUserPassword() {
        QAUser testQAUser = new QAUser();
        testQAUser.setPassword("QAUserTest");

        assertEquals("QAUserTest", testQAUser.getPassword());
    }


    @Test
    public void testParseProjectId() {
        Project testProject = new Project();
        testProject.setId(1);

        assertEquals(1, testProject.getId());
    }

    @Test
    public void testParseProjectName() {
        Project testProject = new Project();
        testProject.setName("ProjectTest");

        assertEquals("ProjectTest", testProject.getName());
    }

    @Test
    public void testParseIssueId() throws SQLException {
        Issue testIssue = new Issue();
        testIssue.setId(1);

        assertEquals(1, testIssue.getId());
    }

    @Test
    public void testParseIssueProjectId() throws SQLException {
        Issue testIssue = new Issue();
        testIssue.setProjectId(1);

        assertEquals(1, testIssue.getProjectId());
    }

    @Test
    public void testParseIssuePostedBy() throws SQLException {
        Issue testIssue = new Issue();
        testIssue.setPostedBy(1);

        assertEquals(1, testIssue.getPostedBy());
    }

    @Test
    public void testParseIssueTitle() throws SQLException {
        Issue testIssue = new Issue();
        testIssue.setTitle("IssueTest");

        assertEquals("IssueTest", testIssue.getTitle());
    }

    @Test
    public void testParseIssueText() throws SQLException {
        Issue testIssue = new Issue();
        testIssue.setText("IssueTextTest");

        assertEquals("IssueTextTest", testIssue.getText());
    }

    @Test
    public void testParseIssueStatus() throws SQLException {
        Issue testIssue = new Issue();
        testIssue.setStatus("IssueStatusTest");

        assertEquals("IssueStatusTest", testIssue.getStatus());
    }

    @Test
    public void testParseIssueResponseId() throws SQLException {
        IssueResponse testIssueResponse = new IssueResponse();
        testIssueResponse.setId(1);

        assertEquals(1, testIssueResponse.getId());
    }

    @Test
    public void testParseIssueResponseUserId() throws SQLException {
        IssueResponse testIssueResponse = new IssueResponse();
        testIssueResponse.setUserId(1);

        assertEquals(1, testIssueResponse.getUserId());
    }

    @Test
    public void testParseIssueResponseContent() throws SQLException {
        IssueResponse testIssueResponse = new IssueResponse();
        testIssueResponse.setContent("ContentTest");
        
        assertEquals("ContentTest", testIssueResponse.getContent());
    }

    @Test
    public void testParseIssueResponseUserType() {
        IssueResponse testIssueResponse = new IssueResponse();
        testIssueResponse.setUserType("TestUserType");

        assertEquals("TestUserType", testIssueResponse.getUserType());
    }

    @Test
    public void testParseIssueResponseTimestamp() {
        IssueResponse testIssueResponse = new IssueResponse();
        Timestamp testTimestamp = new Timestamp(123);
        testIssueResponse.setTimestamp(testTimestamp);

        assertEquals(testTimestamp, testIssueResponse.getTimestamp());
    }

    @Test public void testParseFullIssue() {
        FullIssue testFullIssue = new FullIssue();
        Issue testIssue = new Issue();
        testFullIssue.setIssue(testIssue);
        assertEquals(testIssue, testFullIssue.getIssue());

    }
}