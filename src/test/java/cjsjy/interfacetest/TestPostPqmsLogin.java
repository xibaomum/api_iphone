package cjsjy.interfacetest;

import org.testng.annotations.Test;

import com.alibaba.fastjson.JSONObject;
import com.test.client.PostClient;
import com.test.utils.JSONParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.message.BasicNameValuePair;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;

public class TestPostPqmsLogin {
	PostClient client;
	JSONObject responseBody;
	JSONParser jParser;
	int responseCode;
	String url = "http://10.6.172.179:8080/pqms/loginvalidate";
	String postBody;

	@Test
	public void testPostRequest() {

		// 断言反馈的状态码是否正确
		Assert.assertEquals(responseCode, 200);
	}

	@BeforeClass
	public void beforeClass() throws ClientProtocolException, IOException {
		client = new PostClient();

		// 用NameValuePair的list来添加请求主体参数
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("username", "admin"));
		params.add(new BasicNameValuePair("password", "123456"));

		// 用哈希图准备请求头部信息
		HashMap<String, String> hashHead = new HashMap<String, String>();
		hashHead.put("Content-Type", "application/json;charset=UTF-8");

		// 传参发送post请求并接收反馈
		client.sendPost(url, params, hashHead);

		responseBody = client.getBodyInJSON();
		responseCode = client.getCodeInNumber();

		System.out.println(responseBody);

	}

}