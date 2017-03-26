package com.gdufe.login;

import java.util.List;

import org.apache.http.client.HttpClient;

/*
 * �����½����Ϣ��ʹ�õ�client,���ֻỰ��cookie����½״̬loginStatus
 * �Լ�������Ҫ���ʵĵ�ַ
 * */
public class LoginingInfo {
	private HttpClient client;	//ͬһ�ε�½Ӧ��ʹ��ͬһ��client
	private String loginAddr;	//��½��վ��
	private String cookie;		//����cookie,���ֻỰ
	private Status loginStatus;
	private String visitingAddr;	//��Ҫ���ʵĵ�ַ
	
	
	
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
		this.cookie = cookies;
		this.loginStatus = Status.OFF_LOGIN;
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
	public String getCookie() {
		return cookie;
	}
	public void setCookie(String cookie) {
		this.cookie = cookie;
	}

	public HttpClient getClient() {
		return client;
	}

	public void setClient(HttpClient client) {
		this.client = client;
	}

	
}
