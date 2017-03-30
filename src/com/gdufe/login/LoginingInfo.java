package com.gdufe.login;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.http.client.HttpClient;

/*
 * 保存登陆的信息，使用的client,保持会话的cookie，登陆状态loginStatus
 * 以及接下来要访问的地址
 * 具有管理cookie的功能
 * */
public class LoginingInfo {
	private HttpClient client;	//同一次登陆应该使用同一个client
	private String loginAddr;	//登陆的站点
	private String cookie;		//保存cookie,保持会话
	private Status loginStatus;
	private String visitingAddr;	//正要访问的地址
	
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
		//传入cookies字符串，判断字符串是否为空,若不为空证明有需要保存的cookie
		if(cookies!=null&&cookies.trim().length()!=0){
			//若cookiesMap为空则先创建一个cookiesMap
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
					throw new Exception("插入的cookie有误");
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
