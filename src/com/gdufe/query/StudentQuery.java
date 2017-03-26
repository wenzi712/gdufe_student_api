package com.gdufe.query;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.gdufe.Addr.Address;
import com.gdufe.login.LoginingInfo;
import com.gdufe.login.Status;
import com.gdufe.model.Student;
import com.gdufe.util.HttpUtil;
import com.gdufe.util.ReadUtil;

public class StudentQuery {
	private LoginingInfo loginingInfo;
	/*
	 * 要先登陆才可以进行查询
	 * */
	public StudentQuery(LoginingInfo loginingInfo) {
		super();
		this.loginingInfo = loginingInfo;
	}
	
	/*
	 * 获取学籍信息
	 * 返回的是jsoup的Element格式
	 * */
	public Element getStudentStatusElem(){
		if(loginingInfo.getLoginStatus()==Status.OFF_LOGIN){
			throw new RuntimeException("off-login,please login");
		}
		loginingInfo.setVisitingAddr(Address.STUDENTSTATUS);
		HttpResponse response = HttpUtil.get(loginingInfo);
		HttpEntity entity = response.getEntity();
		
		return getTable(entity);
	}
	
	
	public Element getTable(HttpEntity entity){
		String html = ReadUtil.read2Str(entity);
		Document doc = Jsoup.parse(html);
		Element table = doc.getElementById("xjkpTable");
		return table;
	}

	/*
	 * 返回Student的model
	 * */
	public Student getStudent(){
		Student stu = new Student();
		
		Element table = getStudentStatusElem();
		Elements trs = table.getElementsByTag("tr");

		Element tr2 = trs.get(2);
		Elements tds = tr2.getElementsByTag("td");
		stu.setCollege(tds.get(0).text().substring(3));
		stu.setMajor(tds.get(1).text().substring(3));
		stu.setClazz(tds.get(3).text().substring(3));
		stu.setId(tds.get(4).text().substring(3));

		Element tr3 = trs.get(3);
		tds = tr3.getElementsByTag("td");
		stu.setName(tds.get(1).text());
		stu.setSex(tds.get(3).text());
		
		//获取入学日期
		Element trEnrollTime = table.select("tr:contains(入学日期)").get(0);
		Element tdEnrollTime = trEnrollTime.getElementsByTag("td").get(1);
		String t = tdEnrollTime.text().substring(1);
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		try {
			Date enrollTime = format.parse(t);
			stu.setEnrollTime(enrollTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		
		
		
		return stu;
		
	}
	
}
