package com.gdufe.login;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.http.client.HttpClient;

/*
 * �����½����Ϣ��ʹ�õ�client,���ֻỰ��cookie����½״̬loginStatus
 * �Լ�������Ҫ���ʵĵ�ַ
 * ���й���cookie�Ĺ���
 * */
public class LoginingInfo {
	private HttpClient client;	//ͬһ�ε�½Ӧ��ʹ��ͬһ��client
	private String loginAddr;	//��½��վ��
	private String cookie;		//����cookie,���ֻỰ
	private Status loginStatus;
	private String visitingAddr;	//��Ҫ���ʵĵ�ַ
	
	private HashMap<String,String> cookiesMap;
	
	public String getVisitingAddr() {
		return visitingAddr;
	}

	public void setVisitingAddr(String visitingAddr) {
		this.visitingAddr = visitingAddr;
	}

	public LoginingInfo(HttpClient client,String loginAddr){
		this.client = client;
		this.loginAddr = loginAddr;
		this.loginStatus = Status.OFF_LOGIN;
	}
	
	public LoginingInfo(HttpClient client,String loginAddr, String cookies) {
		super();
		this.client = client;
		this.loginAddr = loginAddr;
		this.loginStatus = Status.OFF_LOGIN;
		//����cookies�ַ������ж��ַ����Ƿ�Ϊ��,����Ϊ��֤������Ҫ�����cookie
		if(cookies!=null&&cookies.trim().length()!=0){
			//��cookiesMapΪ�����ȴ���һ��cookiesMap
			if(this.cookiesMap==null){
				this.cookiesMap = new HashMap<String,String>();
			}
			String[] _cookies = cookies.split(";");
			for(String s:_cookies){
				addCookie(s);
			}
		}
	}
	
	public void setLoginStatus(Status loginStatus){
		this.loginStatus = loginStatus;
	}
	
	public Status getLoginStatus(){
		return loginStatus;
	}
	
	public String getLoginAddr() {
		return loginAddr;
	}
	public void setLoginAddr(String login_addr) {
		this.loginAddr = login_addr;
	}
	
	

	public HttpClient getClient() {
		return client;
	}

	public void setClient(HttpClient client) {
		this.client = client;
	}

	public void addCookie(String cookie){
		if(cookiesMap==null){
			this.cookiesMap = new HashMap<String,String>();
		}
		//JSESSIONID=1C962D8D93BA962BA1450B2A4FF72AD6; Path=/
		String[] str = cookie.split(";");
		for(String s:str){
			String[] str1 = s.split("=");
			if(str1.length!=2){
				try {
					throw new Exception("�����cookie����");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if(str1[0].trim().length()!=0)
				cookiesMap.put(str1[0], str1[1]);
		}
		
	}
	
	public String cookieMaptoString(){
		String cookieStr = "";
		Set<String> keySet = cookiesMap.keySet();
		for(String k:keySet){
			cookieStr += k+"="+cookiesMap.get(k)+";";
		}
		return cookieStr;
	}
	
}
