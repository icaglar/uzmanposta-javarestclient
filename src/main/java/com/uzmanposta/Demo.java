package com.uzmanposta;

import java.util.Date;
import java.util.List;

import com.uzmanposta.restclient.UzmanPostaRestClient;
import com.uzmanposta.restclient.request.LogRequest;
import com.uzmanposta.restclient.result.QueryResult;

public class Demo {

	public static void main(String[] args) {
		
		UzmanPostaRestClient client = new UzmanPostaRestClient("email address here", "password here");
		
		LogRequest logRequest = new LogRequest.LogRequestBuilder()
		.addDomain("yourdomain.com")
		.addFromEmail("fromemail@abc.com")
		.addMailTo("toemail@abc.com")
		.addSubject("Mail Subject")
		.addStartDate(new Date("2017/03/06"))
		.build();
		
		List<QueryResult> logs = client.getLogs(logRequest);	
		
		for (QueryResult queryResult : logs) {
			System.out.println(queryResult.getStatus_code());
		}
		
	}
}
