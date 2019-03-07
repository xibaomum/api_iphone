package com.test.client;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class PostClient {
	// httpClient是用来发送http请求的HttpClient实例；
	CloseableHttpClient httpclient;
	// httpPost是post请求的一个实例；
	HttpPost httpPost;
	// httpResponse用来存储我们接收到的反馈;
	CloseableHttpResponse httpResponse;
	// responseCode用来存储反馈的状态码；
	int responseCode;
	// responseBody用来存储反馈的主体信息,JSON格式；；
	JSONObject responseBody;
	// responseHeader用来存储反馈的头部信息；
	HashMap<String, String> responseHeads;

	// 通过httpclient获取post请求的反馈
	public void sendPost(String url, List<NameValuePair> params, HashMap<String, String> headers)
			throws ClientProtocolException, IOException {
		// List<NameValuePair> params = new ArrayList<NameValuePair>();
		
		//定义一个连接器httpclient
		httpclient = HttpClients.createDefault();

		// 设置请求发送方式为post
		httpPost = new HttpPost(url);

		// 设置请求主体格式
		HttpEntity entity = new UrlEncodedFormEntity(params, "UTF-8");
		//请求主体
		httpPost.setEntity(entity);
		// httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
		
		// 设置头部信息
		//获取头部map集合的所有键值set集合keyset（）
		Set<String> set = headers.keySet();
		//获取set集合的迭代器iterator
		for (Iterator<String> iterator = set.iterator(); iterator.hasNext();) {
			//键值
			String key = iterator.next();
			//value
			String value = headers.get(key);
			httpPost.addHeader(key, value);
		}
		
		httpResponse = httpclient.execute(httpPost);
	}

	// getBodyInJSON方法：以JSON格式获取到反馈的主体
	public JSONObject getBodyInJSON() throws ParseException, IOException {
		//反馈主体
		HttpEntity entity;
		String entityToString;

		// 从反馈中提取出反馈主体
		entity = httpResponse.getEntity();
		// 用EntityUtils工具类将反馈主体处理为字符串形式
		entityToString = EntityUtils.toString(entity);
		responseBody = JSON.parseObject(entityToString);
		System.out.println("(client)This is your response body:" + responseBody);

		return responseBody;
	}

	// getCodeInNumber方法：获取反馈状态码
	public int getCodeInNumber() {
		responseCode = httpResponse.getStatusLine().getStatusCode();

		System.out.println("(client)This is your response code" + responseCode);

		return responseCode;
	}

}
