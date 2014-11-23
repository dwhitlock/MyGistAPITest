package com.test.endpoint;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.simple.JSONObject;

public class HttpPostReq
{
	private String authHeader;
	
	public HttpPostReq(String user, String pass)
	{
		 String auth=new StringBuffer(user).append(":").append(pass).toString();
	     byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
	     authHeader = "Basic " + new String(encodedAuth);
	}
	
    private HttpPost createConnectivity(String url)
    {
        HttpPost post = new HttpPost(url);
        post.setHeader("AUTHORIZATION", authHeader);
        post.setHeader("Content-Type", "application/json");
        post.setHeader("Accept", "application/json");
        post.setHeader("X-Stream" , "true");
        return post;
    }
     
    public int executePostRequest(String jsonData,  HttpPost httpPost)
    {
    	int rtncode=0;
    	try
    	{
	        HttpResponse response=null;
	        String line = "";
	        StringBuffer result = new StringBuffer();
	        httpPost.setEntity(new StringEntity(jsonData));
	        HttpClient client = HttpClientBuilder.create().build();
	        response = client.execute(httpPost);
	        System.out.println("Post parameters : " + jsonData );
	        rtncode = response.getStatusLine().getStatusCode();
	        System.out.println("Response Code : " + rtncode);
	        BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
	        while ((line = reader.readLine()) != null){ result.append(line); }
	        System.out.println(result.toString());
    	}
    	catch (UnsupportedEncodingException e){
            System.out.println("error while encoding api url : "+e);
        }
        catch (IOException e){
            System.out.println("ioException occured while sending http request : "+e);
        }
        catch(Exception e){
            System.out.println("exception occured while sending http request : "+e);
        }
        finally{
        	httpPost.releaseConnection();
        }
    	return rtncode;
    }
    
    public String getAuthHeader()
    {
    	return authHeader;
    }
    
    public boolean executeGetRequest(HttpGet httpGet)
    {
    	int rtncode = 400;
    	try{
    		HttpResponse response=null;
            String line = "";
            StringBuffer result = new StringBuffer();
            HttpClient client = HttpClientBuilder.create().build();
            response = client.execute(httpGet);
            rtncode = response.getStatusLine().getStatusCode();
            System.out.println("Response Code : " + rtncode);
            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            while ((line = reader.readLine()) != null){ result.append(line); }
            	System.out.println(result.toString());
    	}
    	catch (UnsupportedEncodingException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        return  rtncode == 200;
    }
    
    public boolean isConnected()
    {
    	boolean success = false;
    	HttpGet httpGet = new HttpGet("https://api.github.com");
    	httpGet.setHeader("AUTHORIZATION", authHeader);
		success = executeGetRequest(httpGet);
    	return success;
    }
}
