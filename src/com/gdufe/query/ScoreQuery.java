package com.gdufe.query;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.gdufe.Addr.Address;
import com.gdufe.login.LoginingInfo;
import com.gdufe.login.Status;
import com.gdufe.util.HttpUtil;
import com.gdufe.util.ReadUtil;

public class ScoreQuery {

	private LoginingInfo loginingInfo;
	
	/*
	 * 要先登陆才可以进行查询
	 * */
	public ScoreQuery(LoginingInfo loginingInfo) {
		super();
		this.loginingInfo = loginingInfo;
	}
	
	/*
	 * 
	 * 根据课程名获取课程成绩
	 * 返回的是jsoup的格式
	 * */
	public Element getScoreElemByCourseName(String courseName){
		if(loginingInfo.getLoginStatus()==Status.OFF_LOGIN){
			throw new RuntimeException("off-login,please login");
		}
		
		//kcmc:离散数学
		//fxkc:0
		//xsfs:all
		//kksj:
		//kcxz:
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("kksj",""));
		params.add(new BasicNameValuePair("kcxz",""));
		params.add(new BasicNameValuePair("kcmc",courseName));
		params.add(new BasicNameValuePair("xsfs","all"));
		params.add(new BasicNameValuePair("fxkc","0"));
		loginingInfo.setVisitingAddr(Address.SOCREQUERY);
		HttpResponse response = HttpUtil.post(loginingInfo,params);
		HttpEntity entity = response.getEntity();
		
		return getScoreTable(entity);
	}
	
	public Element getScoreTable(HttpEntity entity){
		String html = ReadUtil.read2Str(entity);
		Document doc = Jsoup.parse(html);
		//Element table = doc.getElementById("dataList");
		Element table = doc.getElementsByClass("Nsb_pw").get(3);
		return table;
	}

	public Map<String,String> getScoreMapByCourseName(String courseName){
		Element table = getScoreElemByCourseName(courseName);
		String score = table.getElementsByTag("td").get(6).text();
		Map map = new HashMap<String,String>();
		map.put(courseName, score);
		return map;
	}

	/*public Map<String,String> getScoreMapByTerm(String date1,String date2,int term){
		
	}*/
	
}
