package com.mhuiq.httpMocker.httpClient;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class HttpServiceNoHttps implements HttpService {

	private HttpClientCfg cfg;
	private HttpClient client = HttpClients.createDefault();
	
	public HttpServiceNoHttps(HttpClientCfg cfg) {
		this.cfg = cfg;
	}
	
	public Map<String, Object> sendPost() {
		Map<String, Object> respMap = new HashMap<String, Object>();
		HttpPost httpPost = new HttpPost(cfg.getPotol() + "://" + cfg.getHost() + ":" + cfg.getPort() + cfg.getPath());
		httpPost.addHeader("Content-Type", "text/plain;charset=" + cfg.getCharset());
		try {
			httpPost.setEntity(new StringEntity(cfg.getParams()));
			HttpResponse resp = client.execute(httpPost);
			Header[] headers = resp.getAllHeaders();
			List<String> headerStr = new ArrayList<String>();
			headerStr.add(resp.getStatusLine().toString());
			for (Header h : headers) {
				headerStr.add(h.getName() + ":" + h.getValue());
				
			}
			respMap.put("HEADER", headerStr);
			respMap.put("BODY", EntityUtils.toString(resp.getEntity()));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return respMap;
	}
	
	public Map<String, Object> sendGet() {
		Map<String, Object> respMap = new HashMap<String, Object>();
		String params = cfg.getParams();
		if (null != params && (params = params.trim()).length() != 0) {
			params = "?" + params;
		}
		HttpGet httpGet = new HttpGet(cfg.getPotol() + "://" + cfg.getHost() + ":" + cfg.getPort() + cfg.getPath() + params);
		
		httpGet.addHeader("Content-Type", "charset=" + cfg.getCharset());
		try {
			HttpResponse resp = client.execute(httpGet);
			Header[] headers = resp.getAllHeaders();
			List<String> headerStr = new ArrayList<String>();
			headerStr.add(resp.getStatusLine().toString());
			for (Header h : headers) {
				headerStr.add(h.getName() + ":" + h.getValue());
			}
			respMap.put("HEADER", headerStr);
			respMap.put("BODY", EntityUtils.toString(resp.getEntity()));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return respMap;
	}
	
	
}
