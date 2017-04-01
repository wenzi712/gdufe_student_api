package com.gdufe.login;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import com.gdufe.Addr.Address;
import com.gdufe.util.HttpUtil;

public class Client {
	
	private static String loginAddr = Address.LOGIN;
	public HttpClient client = null;
	
	public Client(HttpClient client){
		this.client = client;
	}
	
	/*
	 * 登陆后返回登陆的信息，包括登陆的地址，保持登陆状态的cookie等
	 * */
	public LoginingInfo login(String username,String password){
		List<NameValuePair> loginParams = new ArrayList<NameValuePair>();
		loginParams.add(new BasicNameValuePair("USERNAME",username));
		loginParams.add(new BasicNameValuePair("PASSWORD",password));
		
		HttpResponse response = HttpUtil.post(client,loginAddr,null,loginParams);
		
		LoginingInfo info = new LoginingInfo(client,Address.INDEX);
		info.setVisitingAddr(Address.LOGIN);
		if(check(response)==false){
			System.out.println("登陆失败，可能是账号密码错误");
			return info;
		}
		
		//Set-Cookie是服务器发送的分配给客户端保持会话的Cookie
		Header[] login_cookies = response.getHeaders("Set-Cookie");
		String cookie = "";
		for(Header h:login_cookies){
			cookie += h.getValue().split(";")[0];
		}
		info = new LoginingInfo(client,loginAddr,cookie);
		info.setLoginStatus(Status.ON_LOGIN);
		return info;
		
	}
	
	/*
	 * 检查是否登陆成功
	 * */
	public boolean check(HttpResponse response){
		int statusCode = response.getStatusLine().getStatusCode();
		//302为重定向，登陆成功后会发生重定向
		if(statusCode==302)
			return true;
		//200表示网页无任何异常，返回成功，在登陆页面登陆失败才出现这个状态码
		else if(statusCode==200)
			return false;
		else{
			throw new RuntimeException("unknown logging exception.statusCode:"+statusCode);
		}
	}
	
	
}
