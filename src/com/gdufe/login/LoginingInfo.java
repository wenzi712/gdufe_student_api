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
	
	/*
	 * ��cookieMapת��Ϊ�ַ�ת
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
	
	//����½״̬
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
	 * 			�ѵ�½ �־û���fileName�ļ������ļ���
	 * 			ʹ�õ�һ��cookie��Ӧ��������Host�ص�ַ
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
	 * 			���ļ��ж�ȡcookie�����Զ���װһ��LoginingInfo���󣬲���cookie��״̬���в��ԣ��ж����Ƿ����
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
				//��һ�б�����ʵĵ�ַ������
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
		
		//���cookie�Ƿ���ڣ������ж�Ч�ʺ��������Ժ�������ٸİ�
		String html = "";
		try {
			html = EntityUtils.toString(rep.getEntity());
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String regex = ".*�û���.*";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(html);
		if(m.find()){
			info.setLoginStatus(Status.OFF_LOGIN);
			try {
				throw new Exception("cookie���ڣ����µ�¼");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			info.setLoginStatus(Status.ON_LOGIN);
		}
		return info;
	}
}
