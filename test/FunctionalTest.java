import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.GET;
import static play.test.Helpers.contentAsString;
import static play.test.Helpers.route;
import static play.test.Helpers.running;
import static play.test.Helpers.*;

import java.util.*;

import com.google.common.collect.ImmutableMap;
import org.junit.Test;

import play.libs.ws.WS;

import play.mvc.Result;
import play.test.FakeApplication;
import play.test.Helpers;
import play.test.WithApplication;
import play.twirl.api.Content;
import play.mvc.Http.RequestBuilder;

public class FunctionalTest  extends WithApplication {
  
  	@Override
  	protected FakeApplication provideFakeApplication() {
	    return new FakeApplication(new java.io.File("."), Helpers.class.getClassLoader(),
	        new HashMap<String, String>(), new ArrayList<String>(), null);
  	}
	@Test
	public void callIndex() {
		Result result = new controllers.Application().index();
	    assertEquals(OK, result.status());
	    assertEquals("text/html", result.contentType());
	    assertEquals("utf-8", result.charset());
	    assertTrue(contentAsString(result).contains(""));
	}

	private int timeout = 1000;

	@Test
	public void testLogin() {
	    running(testServer(3333), () -> {
	        assertEquals(OK, WS.url("http://localhost:3333").get().get(timeout).getStatus());
	    });
	}
	@Test
	public void testAdminUsers() {
	    running(testServer(3333), () -> {
	        assertEquals(OK, WS.url("http://localhost:3333/admin/users").get().get(timeout).getStatus());
	    });
	}
	@Test
	public void testAdminProjects() {
	    running(testServer(3333), () -> {
	        assertEquals(OK, WS.url("http://localhost:3333/admin/projects").get().get(timeout).getStatus());
	    });
	}
	@Test
	public void testAdminIssues() {
	    running(testServer(3333), () -> {
	        assertEquals(OK, WS.url("http://localhost:3333/admin/issues").get().get(timeout).getStatus());
	    });
	}
	@Test
	public void testDevProjects() {
	    running(testServer(3333), () -> {
	        assertEquals(OK, WS.url("http://localhost:3333/dev/projects").get().get(timeout).getStatus());
	    });
	}
	@Test
	public void testDevIssues() {
	    running(testServer(3333), () -> {
	        assertEquals(OK, WS.url("http://localhost:3333/dev/issues/1").get().get(timeout).getStatus());
	    });
	}
	@Test
	public void testDevIssue() {
	    running(testServer(3333), () -> {
	        assertEquals(OK, WS.url("http://localhost:3333/dev/issue/1").get().get(timeout).getStatus());
	    });
	}
	@Test
	public void testQaProjects() {
	    running(testServer(3333), () -> {
	        assertEquals(OK, WS.url("http://localhost:3333/qa/projects").get().get(timeout).getStatus());
	    });
	}
	@Test
	public void testQaIssues() {
	    running(testServer(3333), () -> {
	        assertEquals(OK, WS.url("http://localhost:3333/qa/issues/1").get().get(timeout).getStatus());
	    });
	}
	@Test
	public void testQaIssue() {
	    running(testServer(3333), () -> {
	        assertEquals(OK, WS.url("http://localhost:3333/qa/issue/1").get().get(timeout).getStatus());
	    });
	}
	@Test
	public void testBadRoute() {
	    RequestBuilder request = new RequestBuilder()
	        .method(GET)
	        .uri("/asasd");

	    Result result = route(request);
	    assertEquals(NOT_FOUND, result.status());
	}
}