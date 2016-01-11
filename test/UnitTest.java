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
    public void testParseAdmin() {
        Admin testAdmin = new Admin();
        testAdmin.setId(1);
        testAdmin.setUsername("AdminTest");
        testAdmin.setPassword("AdminTest");

        assertEquals(1, testAdmin.getId());
        assertEquals("AdminTest", testAdmin.getUsername());
        assertEquals("AdminTest", testAdmin.getPassword());
    }

    @Test
    public void testParseDeveloper() {
        Developer testDeveloper = new Developer();
        testDeveloper.setId(1);
        testDeveloper.setUsername("DeveloperTest");
        testDeveloper.setPassword("DeveloperTest");

        assertEquals(1, testDeveloper.getId());
        assertEquals("DeveloperTest", testDeveloper.getUsername());
        assertEquals("DeveloperTest", testDeveloper.getPassword());
    }

    @Test
    public void testParseQAUser() {
        QAUser testQAUser = new QAUser();
        testQAUser.setId(1);
        testQAUser.setUsername("QAUserTest");
        testQAUser.setPassword("QAUserTest");

        assertEquals(1, testQAUser.getId());
        assertEquals("QAUserTest", testQAUser.getUsername());
        assertEquals("QAUserTest", testQAUser.getPassword());
    }

    @Test
    public void testParseProject() {
        Project testProject = new Project();
        testProject.setId(1);
        testProject.setName("ProjectTest");

        assertEquals(1, testProject.getId());
        assertEquals("ProjectTest", testProject.getName());
    }

    @Test
    public void testParseIssue() throws SQLException {
        Issue testIssue = new Issue();
        testIssue.setId(1);
        testIssue.setProjectId(1);
        testIssue.setPostedBy(1);
        testIssue.setTitle("IssueTest");
        testIssue.setText("IssueTextTest");
        testIssue.setStatus("IssueStatusTest");

        assertEquals(1, testIssue.getId());
        assertEquals(1, testIssue.getProjectId());
        assertEquals(1, testIssue.getPostedBy());
        assertEquals("IssueTest", testIssue.getTitle());
        assertEquals("IssueTextTest", testIssue.getText());
        assertEquals("IssueStatusTest", testIssue.getStatus());
    }
}