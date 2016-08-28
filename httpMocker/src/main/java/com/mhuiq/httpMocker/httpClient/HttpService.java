package com.mhuiq.httpMocker.httpClient;

import java.util.Map;

public interface HttpService {
	
	public Map<String, Object> sendPost();
	
	public Map<String, Object> sendGet();

}
