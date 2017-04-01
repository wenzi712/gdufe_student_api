package com.gdufe.query;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.gdufe.Addr.Address;
import com.gdufe.login.LoginingInfo;
import com.gdufe.login.Status;
import com.gdufe.model.Course;
import com.gdufe.model.Score;
import com.gdufe.util.HttpUtil;

/*
 * ר�����ڲ�ѯ�ɼ�����
 * @author lapple
 * */
public class ScoreQuery {

	private LoginingInfo loginingInfo;
	
	/*
	 * Ҫ�ȵ�½�ſ��Խ��в�ѯ
	 * */
	public ScoreQuery(LoginingInfo loginingInfo) {
		super();
		this.loginingInfo = loginingInfo;
	}
	
	/*
	 * 
	 * ���ݿγ�����ȡ�γ̳ɼ�
	 * ���ص���jsoup��Element��ʽ
	 * @param courseName
	 * 			�γ̵����֣����γ̲����ڷ��ؿ�
	 * */
	private Element getScoreElemByCourseName(String courseName){
		loginingInfo.checkLoginStatus();
		
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
	
	/*
	 * ���Ѿ���һ����ȡ�˵�entity������һ��score�ĳɼ���
	 * */
	private Element getScoreTable(HttpEntity entity){
		String html = "";
		try {
			html += EntityUtils.toString(entity);
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Document doc = Jsoup.parse(html);
		Element table = doc.getElementById("dataList");
		return table;
	}

	/*���ݿγ̵����ַ���һ��map������ֻ��һ����ֵ�ԣ�keyΪ�γ�����value��һ��score����
	 * 
	 * @param courseName
	 * 			�γ���
	 * */

	//����ֻ��һ����ֵ�Ե�Map
	public Map<String,Score> getScoreMapByCourseName(String courseName){
		Element table = getScoreElemByCourseName(courseName);
		Elements tds = table.getElementsByTag("td");
		Score s = new Score();
		
		s.setTerm(tds.get(1).text());
		s.setId(tds.get(2).text());
		s.setName(tds.get(3).text());
		s.setScore(Integer.parseInt(tds.get(4).text()));
		s.setCredit(Float.parseFloat((tds.get(5).text())));
		
		Map<String,Score> map = new HashMap<String,Score>();
		map.put(courseName, s);
		return map;
	}

	/*
	 * ����ĳ��ѧ�ڵ����пγ̵ĳɼ���set����
	 * ����һ��ѧ�ڵ����пγ̵ĳɼ���set����
	 * */
	public Set<Score> getScoreSetByTerm(String date1,String date2,int term){
		CourseQuery courseQuery = new CourseQuery(loginingInfo);
		Set<String> courseOfTerm = courseQuery.getCourseNames(date1, date2, term);
		Set<Score> socreOfTerm = new HashSet<Score>();
		
		for(String c:courseOfTerm){
			HashMap m = (HashMap) getScoreMapByCourseName(c);
			socreOfTerm.add((Score)m.get(c));
		}
		return socreOfTerm;
	}
	
}
