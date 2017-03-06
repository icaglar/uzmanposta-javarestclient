package com.uzmanposta.restclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.uzmanposta.exception.UnauthorizedException;
import com.uzmanposta.restclient.request.LogRequest;
import com.uzmanposta.restclient.result.AuthenticationToken;
import com.uzmanposta.restclient.result.QueryResult;
import com.uzmanposta.restclient.util.CharUtil;

public class UzmanPostaRestClient implements IRestClient {

	public static final String TOKEN_URL = "https://api.uzmanposta.com/v1k/login";
	public static final String LOGS_URL = "https://api.uzmanposta.com/v1k/logs";

	private String email;
	private String password;
	
	public  UzmanPostaRestClient(String email,String password) {
		this.setEmail(email);
		this.setPassword(password);
	}
	
	@Override
	public AuthenticationToken getToken() {

		AuthenticationToken result = new AuthenticationToken();
		
		JSONObject main = createTokenRequestJson();

		try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {

			HttpPost httpPost = new HttpPost(TOKEN_URL);
			httpPost.addHeader(new BasicHeader("Content-Type", "application/json"));

			// Decode json
			StringWriter out = new StringWriter();
			main.writeJSONString(out);
			String jsonText = out.toString();

			StringEntity entity = new StringEntity(jsonText);
			httpPost.setEntity(entity);
			
			// Execute post request
			HttpResponse r = httpClient.execute(httpPost);

			BufferedReader rd = new BufferedReader(new InputStreamReader(r.getEntity().getContent()));
			String line = "";
			StringBuilder jsonResult = new StringBuilder();
			
			// Parse our JSON response
			while ((line = rd.readLine()) != null)
			{
				jsonResult.append(line);
			}
			JSONParser j = new JSONParser();
			JSONObject o = (JSONObject) j.parse(new String(jsonResult));
			result.setAccessToken(o.get("access_token").toString());
			result.setClientID(o.get("client_id").toString());
			Timestamp stamp = new Timestamp(Long.parseLong(o.get("expire_time").toString()));
			Date expireTime = new Date(stamp.getTime());
			result.setExpireTime(expireTime);
		} catch (ParseException e)
		{
			System.out.println(e);
		} catch (IOException e)
		{
			System.out.println(e);
		} catch (org.json.simple.parser.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}
	
	@Override
	public List<QueryResult> getLogs(LogRequest logRequest) throws UnauthorizedException {
		
		AuthenticationToken authenticationToken = getToken();
		
		List<QueryResult> resultList = new ArrayList<QueryResult>();
		
		try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {

			HttpGet httpGet = new HttpGet(LOGS_URL);
			httpGet.addHeader(new BasicHeader("Accept", "application/json"));
			httpGet.addHeader(new BasicHeader("Authorization", "Bearer "+authenticationToken.getAccessToken()));
			
			String startDate = new SimpleDateFormat("yyyy-MM-dd").format(logRequest.getStartDate());
			
			URI uri;
			try {
				uri = new URIBuilder(httpGet.getURI())
				.addParameter("domain",logRequest.getDomain())
				.addParameter("mailfrom", logRequest.getFromEmail())
				.addParameter("mailto", logRequest.getMailTo())
				.addParameter("start_date",startDate)
				.addParameter("subject", CharUtil.returnUnicodeOfString(logRequest.getSubject()))
				.build();
				
				httpGet.setURI(uri);
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			HttpResponse r = httpClient.execute(httpGet);
			if(r.getStatusLine().getStatusCode()==401){
				throw new UnauthorizedException("Unauthorized");
			}
			BufferedReader rd = new BufferedReader(new InputStreamReader(r.getEntity().getContent()));
			String line = "";
			StringBuilder jsonResult = new StringBuilder();
			
			// Parse our JSON response
			while ((line = rd.readLine()) != null)
			{
				jsonResult.append(line);
			}
			JSONParser j = new JSONParser();
			JSONArray jsonArray = (JSONArray) j.parse(new String(jsonResult));
			
			Iterator iterator = jsonArray.iterator();
			
			while(iterator.hasNext()){
				
				QueryResult queryResult = new QueryResult();
				JSONObject jsonObject =  (JSONObject) iterator.next();
				queryResult.setHeaderfrom(jsonObject.get("headerfrom").toString());
				queryResult.setMailfrom(jsonObject.get("mailfrom").toString());
				queryResult.setStatus(jsonObject.get("status").toString());
				queryResult.setStatus_code(jsonObject.get("status_code").toString());
				
				resultList.add(queryResult);
			}
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (org.json.simple.parser.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return resultList;
		
	}

	protected JSONObject createTokenRequestJson() {

		JSONObject tokenJson = new JSONObject();
		tokenJson.put("email", this.getEmail());
		tokenJson.put("password", this.getPassword());
		return tokenJson;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	

}
