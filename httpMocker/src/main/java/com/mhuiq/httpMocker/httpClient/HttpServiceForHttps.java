package com.mhuiq.httpMocker.httpClient;

import java.io.FileInputStream;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;



public class HttpServiceForHttps implements HttpService {
	
	private final HttpClientCfg clientCfg;
	
	public HttpServiceForHttps(HttpClientCfg clientCfg) {
		this.clientCfg = clientCfg;
		
	}
	
	/**
	 * 发送Https Post请求
	 */
	public Map<String, Object> sendPost() {
		try {
			Map<String, Object> respMap = new HashMap<String, Object>();
			HttpClient client = getClient();
			
			HttpPost httpPost = new HttpPost(clientCfg.getPotol() + "://" + clientCfg.getHost() + ":" + clientCfg.getPort());
			httpPost.setHeader("Content-Type", "text/plain;charset=" + clientCfg.getCharset());
			httpPost.setEntity(new StringEntity(clientCfg.getParams()));
			
			HttpResponse response = client.execute(httpPost);
			Header[] headers = response.getAllHeaders();
			List<String> headerStr = new ArrayList<String>();
			headerStr.add(response.getStatusLine().toString());
			for (Header h : headers) {
				headerStr.add(h.getName() + ":" + h.getValue());
				
			}
			respMap.put("HEADER", headerStr);
			respMap.put("BODY", EntityUtils.toString(response.getEntity()));
			return respMap;
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return null;
	}
	
	/**
	 * 发送Https Get请求
	 */
	public Map<String, Object> sendGet() {
		try {
			Map<String, Object> respMap = new HashMap<String, Object>();
			HttpClient client = getClient();
			String params = "";
			if (null != clientCfg.getParams()) {
				params = "?" + clientCfg.getParams().trim();
			}
			HttpGet httpGet = new HttpGet(clientCfg.getHost() + ":" + clientCfg.getPort() + clientCfg.getPath() + params);
			httpGet.setHeader("Content-Type", "text/plain;charset=" + clientCfg.getCharset());
			
			HttpResponse response = client.execute(httpGet);
			Header[] headers = response.getAllHeaders();
			List<String> headerStr = new ArrayList<String>();
			headerStr.add(response.getStatusLine().toString());
			for (Header h : headers) {
				headerStr.add(h.getName() + ":" + h.getValue());
				
			}
			respMap.put("HEADER", headerStr);
			respMap.put("BODY", EntityUtils.toString(response.getEntity()));
			return respMap;
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return null;
	}
	
	/**
	 * 获取https的client端
	 */
	private HttpClient getClient() throws Exception {
		KeyStore ks = KeyStore.getInstance(clientCfg.getKeyStoreAlg());
		ks.load(new FileInputStream(clientCfg.getKeyStorePath()), clientCfg.getKeyStorePwd().toCharArray());
		KeyStore tks = KeyStore.getInstance(clientCfg.getTrustKeyStoreAlg());
		tks.load(new FileInputStream(clientCfg.getTrustKeyStorePath()), clientCfg.getTrustKeyStorePwd().toCharArray());
		KeyManagerFactory kmf = KeyManagerFactory.getInstance(clientCfg.getKmfAlg());
		TrustManagerFactory tmf = TrustManagerFactory.getInstance(clientCfg.getTmfAlg());
		kmf.init(ks, clientCfg.getKeyStorePwd().toCharArray());
		tmf.init(tks);
		SSLContext sslContext = SSLContext.getInstance(clientCfg.getEncryAlg());
		sslContext.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
		SSLConnectionSocketFactory ssfsf = new SSLConnectionSocketFactory(sslContext);
		return HttpClients.custom().setSSLSocketFactory(ssfsf).build();
	}
}
