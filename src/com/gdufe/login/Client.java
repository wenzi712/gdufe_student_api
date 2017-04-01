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
	 * ��½�󷵻ص�½����Ϣ��������½�ĵ�ַ�����ֵ�½״̬��cookie��
	 * */
	public LoginingInfo login(String username,String password){
		List<NameValuePair> loginParams = new ArrayList<NameValuePair>();
		loginParams.add(new BasicNameValuePair("USERNAME",username));
		loginParams.add(new BasicNameValuePair("PASSWORD",password));
		
		HttpResponse response = HttpUtil.post(client,loginAddr,null,loginParams);
		
		LoginingInfo info = new LoginingInfo(client,Address.INDEX);
		info.setVisitingAddr(Address.LOGIN);
		if(check(response)==false){
			System.out.println("��½ʧ�ܣ��������˺��������");
			return info;
		}
		
		//Set-Cookie�Ƿ��������͵ķ�����ͻ��˱��ֻỰ��Cookie
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
	 * ����Ƿ��½�ɹ�
	 * */
	public boolean check(HttpResponse response){
		int statusCode = response.getStatusLine().getStatusCode();
		//302Ϊ�ض��򣬵�½�ɹ���ᷢ���ض���
		if(statusCode==302)
			return true;
		//200��ʾ��ҳ���κ��쳣�����سɹ����ڵ�½ҳ���½ʧ�ܲų������״̬��
		else if(statusCode==200)
			return false;
		else{
			throw new RuntimeException("unknown logging exception.statusCode:"+statusCode);
		}
	}
	
	
}
