package com.gdufe.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.gdufe.login.LoginingInfo;

public class HttpUtil {
	
	/*
	 * 使用post访问网站，获得网站的响应消息response
	 * @author  Lapple
	 * @see     java.lang.Object#toString()
	 * @see     java.lang.StringBuffer
	 * @see     java.lang.StringBuilder
	 * @see     java.nio.charset.Charset
	 * @since   JDK1.0
	 * */
	public static HttpResponse post(HttpClient client,String postAddr,String cookie,List<NameValuePair> loginParams){
		if(postAddr==null||postAddr.trim().length()==0){
			throw new RuntimeException("the post address is empty");
		}
		HttpPost post = new HttpPost(postAddr);
		HttpResponse response = null;
		post.setHeader("User-Agent","Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.98 Safari/537.36");
		post.setHeader("Content-Type","application/x-www-form-urlencoded");
		if(cookie!=null&&cookie.trim().length()!=0){
			post.setHeader("Cookie",cookie);
		}
		try {
			//对发送的表单数据进行编码，若有中文这里很容易出错
			post.setEntity(new UrlEncodedFormEntity(loginParams,"utf-8"));
			//client = HttpClients.createDefault();
			response = client.execute(post);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		post.abort();
		return response;
	}
	
	/*
	 * 登陆后发送post请求的方法
	 * 会自动把loginingInfo中的cookie设置到post请求中
	 * */
	public static HttpResponse post(LoginingInfo info,List<NameValuePair> postParams){
		HttpClient client = info.getClient();
		String visitingAddr = info.getVisitingAddr();
		return post(client,visitingAddr,info.getCookie(),postParams);
	}
	
	
	/*
	 * 简单发送请求，不需要使用cookie保持会话，若要保持登陆状态，请使用get(HttpClient,String,String)
	 * */
	public static HttpResponse get(HttpClient client,String getAddr){
		if(getAddr==null||getAddr.trim().length()==0){
			throw new RuntimeException("the post address is empty");
		}
		HttpGet get = new HttpGet(getAddr);
		get.setHeader("User-Agent","Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.98 Safari/537.36");
		get.setHeader("Content-Type","application/x-www-form-urlencoded");
		HttpResponse response = null;
		try {
			response = client.execute(get);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		get.abort();
		return response;
		
	}
	
	/*
	 * 使用带有cookie的get访问
	 * */
	public static HttpResponse get(HttpClient client,String getAddr,String loginCookie){
		HttpGet get = new HttpGet(getAddr);
		get.setHeader("User-Agent","Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.98 Safari/537.36");
		get.setHeader("Content-Type","application/x-www-form-urlencoded");
		
		get.setHeader("Cookie",loginCookie);
		HttpResponse response = null;
		try {
			response = client.execute(get);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		get.abort();
		return response;
	}
	
	/*
	 * 把访问站点的信息封装到loginingInfo中，需要访问的地址封装到loginingInfo中的visitingAddr中
	 * */
	public static HttpResponse get(LoginingInfo loginingInfo){
		HttpClient client = loginingInfo.getClient();
		String addr = loginingInfo.getVisitingAddr();
		String cookie =  loginingInfo.getCookie();
		return get(client,addr,cookie);
	}
	
	
	
	
}
