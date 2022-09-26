package com.seshu.general;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.json.JSONException;
import org.json.JSONObject;

public class RestTest {
	public static void main(String[] args) {//throws ClientProtocolException, IOException, JSONException {
		try{
			HttpClient client = new DefaultHttpClient();
			HttpPost post = new HttpPost("resturl");
			JSONObject json = new JSONObject();
			json.put("storerkey", "3000");
			json.put("sku", "221010");
			json.put("descr", "test sku from java rest api client");
			StringEntity se = new StringEntity(json.toString());
			// List nameValuePairs = new ArrayList(1);
			// nameValuePairs.add(new BasicNameValuePair("name", "value")); //you
			// can as many name value pair as you want in the list.
			HttpParams params = new BasicHttpParams();
			params.setParameter("username", "seshu");
			params.setParameter("password", "seshu");
			params.setParameter("Tenant", "test");
			post.setParams(params);
			post.setEntity(se);
			//post.
			HttpResponse response = client.execute(post);
			BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			String line = "";
			while ((line = rd.readLine()) != null) {
				System.out.println(line);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}