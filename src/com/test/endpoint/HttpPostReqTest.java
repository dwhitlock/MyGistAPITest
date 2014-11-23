package com.test.endpoint;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.nio.charset.Charset;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.json.simple.JSONObject;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.test.endpoint.HttpPostReqTest;

public class HttpPostReqTest {

	private String authHeader;
	private String jsonData;
	private String username;
	private String pass;
	private HttpPost post;
	private String url = "https://api.github.com/gists";
	
	@Before
	public void setUp(){
		username=System.getProperty("githubUser");
		pass = System.getProperty("githubPass");

		Map<String, String> contentObj = new LinkedHashMap<String, String>();
 	    Map<String, String> filesObj = new LinkedHashMap<String, String>();
 	    contentObj.put("content","This is a basic readme file.");
 	    filesObj.put("readme.txt",contentObj.toString());	
 	    
        JSONObject user=new JSONObject();
        user.put("description", "the description for this gist");
        user.put("comment", "This is a auto-generated comment");
        user.put("public", new Boolean(true));
        user.put("files", filesObj);
        jsonData=user.toString();
        
        String auth=new StringBuffer(username).append(":").append(pass).toString();
	    byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
	    authHeader = "Basic " + new String(encodedAuth);
	    
        post = new HttpPost(url);
        post.setHeader("AUTHORIZATION", authHeader);
        post.setHeader("Content-Type", "application/json");
        post.setHeader("Accept", "application/json");
        post.setHeader("X-Stream" , "true");
	}

	@Test
	public void testExecutePostRequest() {
		HttpPostReq httpPostReq=new HttpPostReq(username, pass);
        assertTrue(httpPostReq.executePostRequest( jsonData, post) == 201);
	}
	
	@Test
	public void testBadMessageExecutePostRequest() {
		HttpPostReq httpPostReq=new HttpPostReq(username, pass);
        assertTrue(httpPostReq.executePostRequest( "This is a bad message", post) == 400);
	}

	@Test
	public void testExecuteGetRequest() {
		HttpPostReq httpPostReq=new HttpPostReq(username, pass);
		HttpGet get = new HttpGet(url);
		get.setHeader("AUTHORIZATION", httpPostReq.getAuthHeader());
		assertTrue(httpPostReq.executeGetRequest(get));
	}
	
	@Test
	public void testIsConnected() {
		HttpPostReq httpPostReq=new HttpPostReq(username, pass);
		assertTrue(httpPostReq.isConnected());
	}

	@Test
	public void testNotIsConnected() {
		HttpPostReq httpPostReq=new HttpPostReq(username, "badpassword");
		assertFalse("This is a negative test", httpPostReq.isConnected());
	}
}
