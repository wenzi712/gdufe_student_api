package com.gdufe.login;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.gdufe.Addr.Address;
import com.gdufe.util.HttpUtil;

/*
 * 信息门户的客户端
 * */
public class PortalClient {
	
	private static String loginAddr = Address.PORTALLOGIN;
	public HttpClient client = null;
	
	public PortalClient(HttpClient client){
		this.client = client;
	}
	
	/*
	 * 信息门户的登陆方法
	 * 需要手动输入验证码
	 * */
	public LoginingInfo login(String username,String password) {
		Scanner sc = new Scanner(System.in);
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("Login.Token1",username));
		params.add(new BasicNameValuePair("Login.Token2",password));
		String imgCookie = downloadImg();
		String verifyCode = sc.nextLine();
		params.add(new BasicNameValuePair("captchaField",verifyCode));
		
		HttpResponse rep =  HttpUtil.post(client,Address.PORTALLOGIN,imgCookie,params);
		Header[] cookies = rep.getHeaders("Set-cookie");
		String loginedCookie = "";
		for(Header h:cookies){
			loginedCookie += h.getValue();
		}
		
		LoginingInfo info = new LoginingInfo(client,Address.PROTALINDEX,loginedCookie);
		
		//设置状态码
		if(rep.getStatusLine().getStatusCode()==200){
			info.setLoginStatus(Status.ON_LOGIN);
		}

		return  info;
		
		
	}
	
	//下载验证码图片到桌面,返回图片对应的cookie
	public String downloadImg(){
		HttpResponse rep = HttpUtil.get(client, Address.PROTALVERIFICATIONIMG);
		//获取验证码对应的cookie
		String cookie = rep.getHeaders("Set-cookie")[0].getValue().split(";")[0];
		
		HttpEntity entity = rep.getEntity();
		try {
			InputStream in = entity.getContent();
			FileOutputStream fos = new FileOutputStream("C://Users//帅//Desktop//img.png");
			int len;
			byte[] buf = new byte[1024];
			while((len=in.read(buf))!=-1){
				fos.write(buf, 0, len);
			}
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
		}
		
		return cookie;
	}
	
}










