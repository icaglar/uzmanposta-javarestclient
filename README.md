# uzmanposta-javarestclient




```
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
```
