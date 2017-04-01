package com.gdufe.query;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import com.gdufe.Addr.Address;
import com.gdufe.login.LoginingInfo;
import com.gdufe.login.Status;
import com.gdufe.util.HttpUtil;

public class CardQuery {
	private LoginingInfo info;
	/*
	 * 要登陆才能进行查询
	 * */
	public CardQuery(LoginingInfo info){
		this.info = info;
	}
	
	/*
	 * 获取饭卡信息的网站和员原来信息门户网站的登陆信息是不一样的
	 * 需要创建一个新的loginingInfo避免修改登录信息门户的info
	 * */
	private Element getCardInfoTable(){
		info.checkLoginStatus();

		if(info.getLoginStatus()==Status.OFF_LOGIN){
			try {
				throw new Exception("off-login,please login");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		//先访问此网站获取饭卡需要的jseeionid,它与信息门户的jseesionid不同
		info.setVisitingAddr(Address.PROTALHOME);
		HttpResponse rep = HttpUtil.get(info);

		LoginingInfo newInfo = new LoginingInfo(info.getClient(),info.getLoginAddr());
		String html = "";
		try {
			Header[] head = rep.getHeaders("Set-Cookie");
			for(Header h:head){
				newInfo.addCookie(h.getValue());
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		newInfo.setVisitingAddr(Address.PROTALCARDINFO);
		HttpResponse rep1 = HttpUtil.get(newInfo);
		

		try {
			html += EntityUtils.toString(rep1.getEntity());
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return Jsoup.parse(html).getElementsByTag("table").get(0);
	}
	
	/*
	 *解释CardInfoTable内容获取饭卡余额 
	 * */
	//balance竟然有余额的意思
	public String getCardBalance(){
		Element table = getCardInfoTable();
		String regex = "<td class=\"neiwen\">.+元（卡余额）";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(table.toString());
		if(m.find()){
			return Jsoup.parse(m.group()).text().substring(0, 6);
		}
		return null;
	}
	
}
