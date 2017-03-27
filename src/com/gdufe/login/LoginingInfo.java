package com.gdufe.login;

import java.util.List;

import org.apache.http.client.HttpClient;

/*
 * 保存登陆的信息，使用的client,保持会话的cookie，登陆状态loginStatus
 * 以及接下来要访问的地址
 * */
public class LoginingInfo {
	private HttpClient client;	//同一次登陆应该使用同一个client
	private String loginAddr;	//登陆的站点
	private String cookie;		//保存cookie,保持会话
	private Status loginStatus;
	private String visitingAddr;	//正要访问的地址
	
	
	
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
