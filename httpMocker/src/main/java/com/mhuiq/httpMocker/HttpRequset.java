package com.mhuiq.httpMocker;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

public class HttpRequset {
	private final static String HEADER = "HEADER";
	private final static String RESPONSE = "RESPONSE";
	
	private int connTmo ;
	private int respTmo;
	
	public HttpRequset(int connTmo, int respTmo) {
		this.connTmo = connTmo;
		this.respTmo = respTmo;
	}
	
	/**
	 * 向指定URL发送GET方法的请求
	 * 
	 * @param url
	 *            发送请求的URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return URL 所代表远程资源的响应结果
	 */
	public Map<String, Object> sendGet(String url, String param, String charsetName) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		InputStream is = null;
		ByteArrayOutputStream baos = null;
		try {
			if (param.length() > 0) {
				param = "?" + param;
			}
			String urlNameString = url + param;
			URL realUrl = new URL(urlNameString);
			// 打开和URL之间的连接
			URLConnection connection = realUrl.openConnection();
			// 设置通用的请求属性
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("Content-Type", "text/html;charset=" + charsetName);
			connection.setRequestProperty("Accept-Charset", charsetName);
			connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			connection.setConnectTimeout(connTmo);
			connection.setReadTimeout(respTmo);
			// 建立实际的连接
			connection.connect();
			// 获取所有响应头字段
			resultMap.put(HEADER, connection.getHeaderFields());
			baos = new ByteArrayOutputStream();
			// 定义 BufferedReader输入流来读取URL的响应
			is = connection.getInputStream();
			byte[] bytes = new byte[256];
			int len;
			while ((len = is.read(bytes)) != -1) {
				baos.write(bytes, 0, len);
			}
			resultMap.put(RESPONSE, baos.toString(charsetName));
		} catch (Exception e) {
			System.out.println("发送GET请求出现异常！" + e);
			e.printStackTrace();
		} finally {
			try {
				if (null != is) {
					is.close();
				}
				if (null != baos) {
					baos.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return resultMap;
	}

	/**
	 * 向指定 URL 发送POST方法的请求
	 * 
	 * @param url
	 *            发送请求的 URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return 所代表远程资源的响应结果
	 */
	public Map<String, Object> sendPost(String url, String param, String charsetName) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		OutputStream os = null;
		InputStream is = null;
		ByteArrayOutputStream baos = null;
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			conn.setRequestProperty("Content-Type", "text/html;charset=" + charsetName);
			conn.setRequestProperty("Accept-Charset", charsetName);
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			
			os = conn.getOutputStream();
			// 发送请求参数
			os.write(param.getBytes(charsetName));
			// flush输出流的缓冲
			os.flush();
			resultMap.put(HEADER, conn.getHeaderFields());
			// 定义BufferedReader输入流来读取URL的响应
			is = conn.getInputStream();
			baos = new ByteArrayOutputStream();
			byte[] bytes = new byte[256];
			int len;
			while ((len = is.read(bytes)) != -1) {
				baos.write(bytes, 0, len);
			}
			resultMap.put(RESPONSE, baos.toString(charsetName));
		} catch (Exception e) {
			System.out.println("发送 POST 请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (null != baos) {
					baos.close();
				}
				if (null != os) {
					os.close();
				}
				if (null != is) {
					is.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return resultMap;
	}
}
