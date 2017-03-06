package com.uzmanposta.restclient;

import java.util.List;

import com.uzmanposta.exception.UnauthorizedException;
import com.uzmanposta.restclient.request.LogRequest;
import com.uzmanposta.restclient.result.AuthenticationToken;
import com.uzmanposta.restclient.result.QueryResult;

public interface IRestClient {
	
	public AuthenticationToken getToken();
	
	public List<QueryResult> getLogs(LogRequest logRequest) throws UnauthorizedException;

}
