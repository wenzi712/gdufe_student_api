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
	 * ʹ��post������վ�������վ����Ӧ��Ϣresponse
	 * @author  Lapple
	 * @param client
	 * 			��½�Ŀͻ���
	 * @param postAddr
	 * 			post�ĵ�ַ
	 * @param cookie
	 * 			post��Ҫ���͵�cookie,���ڱ����½״̬
	 * @param loginParams
	 * 			post��Ҫ���͵Ĳ���
	 * */
	public static HttpResponse post(HttpClient client,String postAddr,String cookie,List<NameValuePair> loginParams){
		if(postAddr==null||postAddr.trim().length()==0){
			throw new RuntimeException("the post address is empty");
		}
		HttpPost post = new HttpPost(postAddr);
		HttpResponse response = null;
		post.setHeader("User-Agent","Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.98 Safari/537.36");
		if(cookie!=null&&cookie.trim().length()!=0){
			post.setHeader("Cookie",cookie);
		}
		try {
			//�Է��͵ı����ݽ��б��룬����������������׳���
			post.setEntity(new UrlEncodedFormEntity(loginParams,"utf-8"));
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
	 * ��½����post����ķ���
	 * ���Զ���loginingInfo�е�cookie���õ�post������
	 * */
	public static HttpResponse post(LoginingInfo info,List<NameValuePair> postParams){
		HttpClient client = info.getClient();
		String visitingAddr = info.getVisitingAddr();
		return post(client,visitingAddr,info.cookieMap2String(),postParams);
	}
	
	
	/*
	 * �򵥷������󣬲���Ҫʹ��cookie���ֻỰ����Ҫ���ֵ�½״̬����ʹ��get(HttpClient,String,String)
	 * */
	public static HttpResponse get(HttpClient client,String getAddr){
		if(getAddr==null||getAddr.trim().length()==0){
			throw new RuntimeException("the post address is empty");
		}
		HttpGet get = new HttpGet(getAddr);
		get.setHeader("User-Agent","Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.98 Safari/537.36");
		//get.setHeader("Content-Type","application/x-www-form-urlencoded");
		HttpResponse response = null;
		try {
			response = client.execute(get);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
//get.abort();
		return response;
		
	}
	
	/*
	 * @param client
	 * 			�ͻ���
	 * @param getAddr
	 * 			��Ҫget�ĵ�ַ
	 * @param loginCookie
	 * 			���б��ֵ�½״̬��cookie
	 * ʹ�ô���cookie��get����
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
/*
 *������һ�����޸���bug
 *�������abort��ֹ��ProtalClient��½����get�����ʱ��ͻ����socket close�쳣��ԭ��δ֪
 *�ƺ�����������վ����������
 *���ǰ���ע�͵����ܻ�Խ���ϵͳ�Ŀͻ������Ӱ��
 * */
//get.abort();
		return response;
	}
	
	/*
	 * �ѷ���վ�����Ϣ��װ��loginingInfo�У���Ҫ���ʵĵ�ַ��װ��loginingInfo�е�visitingAddr��
	 * */
	public static HttpResponse get(LoginingInfo loginingInfo){
		HttpClient client = loginingInfo.getClient();
		String addr = loginingInfo.getVisitingAddr();
		String cookie =  loginingInfo.cookieMap2String();
		return get(client,addr,cookie);
	}
	
	
	
	
}
