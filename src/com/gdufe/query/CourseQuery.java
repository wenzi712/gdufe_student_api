package com.gdufe.query;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import com.gdufe.util.HttpUtil;

/*ר�����ڲ�ѯ�γ̵Ķ���
 * @author  Lapple
 * */


public class CourseQuery {

	private LoginingInfo loginingInfo;
	
	private static final String CURRENTYEAR = "current";
	private static final int CURRENTTERM = -1;
	 
	/*
	 * Ҫ�ȵ�½�ſ��Խ��в�ѯ
	 * */
	public CourseQuery(LoginingInfo loginingInfo) {
		super();
		this.loginingInfo = loginingInfo;
	}
	
	
	/*
	 * ��ȡ�γ̱�Ĭ���ǵ�ǰѧ��
	 * ���ص���jsoup�ĸ�ʽ
	 * ������ֱ��ʹ�ã���Ϊֱ����ȡ�Ŀγ̱�html��ʽ�����⡣
	 * */
	private Element getScheduleElem(){
		//Ĭ�ϵ�ǰѧ��
		return getScheduleElem(CURRENTYEAR, CURRENTYEAR, CURRENTTERM);
	}
	
	/*
	 *@param  date1
     *         һ��ѧ��ĵ�һ��
     *@param  date2
     *			һ��ѧ��ĵڶ��꣬��date1������1
     *@param  term
     *			ѧ�ڣ�ֻ����1����2
	 * */
	private Element getScheduleElem(String date1,String date2,int term){
		List<NameValuePair> postParams = new ArrayList<NameValuePair>();
		//������ǵ�ǰѧ��
		if(!(date1.equals(CURRENTYEAR)&&date2.equals(CURRENTYEAR)&&term==CURRENTTERM)){
			int d1=0,d2=0;
			try{
				d1 = Integer.parseInt(date1);
				d2 = Integer.parseInt(date2);
			}catch(NumberFormatException e){
				e.printStackTrace();
			}
			
			if(d1!=d2-1||d1>2019){
				try {
					throw new Exception("���ڸ�ʽ����");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			if(term<=0||term>=3){
				try {
					throw new Exception("ѧ�ڴ���");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		
			//xnxq01id:2016-2017-1
			//���ò���
			String xnxq01id = String.format("%s-%s-%d",date1,date2,term);
			postParams.add(new BasicNameValuePair("xnxq01id",xnxq01id));
			postParams.add(new BasicNameValuePair("sfFD","1"));
		}
		

		if(loginingInfo.getLoginStatus()==Status.OFF_LOGIN){
			throw new RuntimeException("off-login,please login");
		}
		

		loginingInfo.setVisitingAddr(Address.SCHEDULEQUERY);
		HttpResponse response = HttpUtil.post(loginingInfo, postParams);

		HttpEntity entity = response.getEntity();
		
		return getTable(entity);
		
	}
	
	
	
	private Element getTable(HttpEntity entity){
		String html = "";
		try {
			html += EntityUtils.toString(entity);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Document doc = Jsoup.parse(html);
		Element table = doc.getElementById("kbtable");
		Elements trs = table.getElementsByTag("tr");
		trs.get(trs.size()-1).remove();
		return table;
	}
	
	/*
	 * �����е�ǰѧ�ڵĿγ�
	 * */
	public Set<Course> getScheduleSet(){
		return getScheduleSet(CURRENTYEAR, CURRENTYEAR, CURRENTTERM);
	}
	
	/*
	 * ��ȡѧ�ڿα����ص���set��set��ÿ��Ԫ�ض���һ��course
	 * */
	public Set<Course> getScheduleSet(String date1,String date2,int term){
		
		Set<Course> set = new HashSet<Course>();
		Element table = getScheduleElem(date1,date2,term);
		Elements kbContent = table.getElementsByClass("kbcontent");
		Elements divs = kbContent.select("div:contains(��)");

		for(Element div:divs){
			Course c = new Course();
			String _div = div.toString();
			//����.����ƥ�����ƥ��
			String regex1 = "<div.+>\\n.*";
			Pattern p = Pattern.compile(regex1);
			Matcher m = p.matcher(_div);
			if(m.find()){
				c.setCourseName(Jsoup.parse(m.group(0)).text());
			}
			
			
			String[] str = div.getElementsByTag("font").text().split(" ");
			c.setTeacherName(str[0]);
			c.setCourseWeek(str[1]);
			c.setLocation(str[2]);
			
			String regex2 = "\\[\\d\\d-\\d\\d\\]��";
			Pattern p2 = Pattern.compile(regex2);
			Matcher m2 = p2.matcher(_div);
			if(m2.find()){
				c.setCourseTime(m2.group());
			}
			
			
			
			String id = div.attr("id");
			int _id = id.charAt(id.length()-3)-'0';
			c.setDay(_id);
			
			set.add(c);
		}
		return set;
	}
	
	/*
	 * ����ĳ��ָ��ѧ�ڵĿγ�����set����
	 * */
	//����һѧ�ڵ����пγ���
	public Set<String> getCourseNames(String date1,String date2,int term){
		Element table = getScheduleElem(date1,date2,term);
		Elements kbContent = table.getElementsByClass("kbcontent");
		Elements divs = kbContent.select("div:contains(��)");
		
		Set<String> set = new HashSet<String>();
		for(Element div:divs){
			
			String[] str = div.text().split(" ");
			if(str.length!=5)
				continue;
			
			set.add(str[0]);
		}
		return set;
	}
	
}
