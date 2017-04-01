package com.gdufe.login;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.gdufe.util.HttpUtil;

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
	
	/*
	 * 把cookieMap转换为字符转
	 * */
	public String cookieMap2String(){
		String cookieStr = "";
		Set<String> keySet;
		if(cookiesMap!=null){
			keySet = cookiesMap.keySet();
			for(String k:keySet){
				cookieStr += k+"="+cookiesMap.get(k)+";";
			}
		}
			
		return cookieStr;
	}
	
	//检查登陆状态
	public void checkLoginStatus(){
		if(this.getLoginStatus()==Status.OFF_LOGIN){
			try {
				throw new Exception("off login,please login");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/*
	 * @param fileName
	 * 			把登陆 持久化到fileName文件名的文件中
	 * 			使用第一行cookie对应保存主机Host地地址
	 * */
	public void cookiePersist(String fileName){
		BufferedWriter bufw = null;
		try {
			bufw = new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream(fileName)));
			bufw.write("Host:"+this.loginAddr+"\n");
			bufw.write(this.cookieMap2String());
			bufw.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(bufw!=null){
				try {
					bufw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/*
	 * 
	 * @param fileName
	 * 			在文件中读取cookie，并自动组装一个LoginingInfo对象，并对cookie的状态进行测试，判断其是否过期
	 * */
	public static LoginingInfo readCookie(String fileName){
		BufferedReader bufr;
		String cookie = "";
		String host = "";
		try {
			bufr = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
			String str = "";
			int len = 0;
			boolean firstLine = true;
			while((str=bufr.readLine())!=null){
				//第一行保存访问的地址，不读
				if(firstLine){
					host += str.substring(5);
					firstLine = false;
					continue;
				}
				cookie += str;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
 			e.printStackTrace();
		}
		
		LoginingInfo info = new LoginingInfo(HttpClients.createDefault(),host,cookie);
		info.setVisitingAddr(host);
		HttpResponse rep = HttpUtil.get(info);
		
		//检查cookie是否过期，这里判断效率很慢。。以后心情好再改吧
		String html = "";
		try {
			html = EntityUtils.toString(rep.getEntity());
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String regex = ".*用户名.*";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(html);
		if(m.find()){
			info.setLoginStatus(Status.OFF_LOGIN);
			try {
				throw new Exception("cookie过期，重新登录");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			info.setLoginStatus(Status.ON_LOGIN);
		}
		return info;
	}
}
