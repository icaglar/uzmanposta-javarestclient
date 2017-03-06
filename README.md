# uzmanposta-javarestclient
 <br />
UzmanPostaRestClient client = new UzmanPostaRestClient("email address here", "password here"); <br />
		
LogRequest logRequest = new LogRequest.LogRequestBuilder()  <br />
.addDomain("yourdomain.com")  <br />
.addFromEmail("fromemail@abc.com")  <br />
.addMailTo("toemail@abc.com")  <br />
.addSubject("Mail Subject")  <br />
.addStartDate(new Date("2017/03/06"))  <br />
.build();  <br />
		 <br />
List<QueryResult> logs = client.getLogs(logRequest);	 <br />
 <br />
for (QueryResult queryResult : logs) { <br />
  System.out.println(queryResult.getStatus_code()); <br />
} <br />
