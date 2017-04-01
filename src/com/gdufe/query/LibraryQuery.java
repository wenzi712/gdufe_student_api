package com.gdufe.query;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.gdufe.Addr.Address;
import com.gdufe.login.LoginingInfo;
import com.gdufe.model.Book;
import com.gdufe.model.BookItem;
import com.gdufe.util.HttpUtil;

/*
 * @author lapple
 * */
import com.gdufe.login.LoginingInfo;

public class LibraryQuery {
	private LoginingInfo info;
	
	public LibraryQuery(LoginingInfo info){
		this.info = info;
	}
	
	
	/*
	 * @param title
	 * 			根据搜索的title书名获取页面元素
	 * 
	 * 只获取检索结果第一页的内容，如果没有显示想要的结果，补全检索的词条进行更精准的查询
	 * 
	 * http://opac.library.gdufe.edu.cn/opac/openlink.php
	 * ?strSearchType=title
	 * &match_flag=forward
	 * &historyCount=1
	 * &strText=%E7%A6%BB%E6%95%A3%E6%95%B0%E5%AD%A6
	 * &doctype=ALL
	 * &with_ebook=on
	 * &displaypg=20
	 * &showmode=list
	 * &sort=CATA_DATE
	 * &orderby=desc
	 * &dept=ALL
	 * */
	private Element getSearchResultElem(String title){
		//没有登陆不能查询，抛出异常
		info.checkLoginStatus();
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("strSearchType","title"));
		params.add(new BasicNameValuePair("match_flag","forward"));
		params.add(new BasicNameValuePair("historyCount","1"));
		params.add(new BasicNameValuePair("strText",title));
		params.add(new BasicNameValuePair("doctype","ALL"));
		params.add(new BasicNameValuePair("with_ebook","on"));
		params.add(new BasicNameValuePair("displaypg","20"));
		params.add(new BasicNameValuePair("sort","list"));
		params.add(new BasicNameValuePair("showmode","CATA_DATE"));
		params.add(new BasicNameValuePair("orderby","desc"));
		params.add(new BasicNameValuePair("dept","ALL"));
		
		String str = "";
		try {
			str = EntityUtils.toString(new UrlEncodedFormEntity(params,"utf-8"));
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String html = "";
		HttpResponse rep = HttpUtil.get(info.getClient(),Address.LIBSEARCH+"?"+str);
		try {
			html += EntityUtils.toString(rep.getEntity());
		} catch (ParseException | IOException e) {
			e.printStackTrace();
		}
		
		return Jsoup.parse(html);
	}
	
	/*
	 * @param html
	 * 			传入经getSearchResultElem返回的Element提取出结果页面的书目信息
	 * 
	 * <li class="book_list_info"> 
	 * <h3>
	 * <span>中文图书</span>
	 * <a href="item.php?marc_no=0000458412">19.妙趣横生的算法:C语言实现.2版</a> 
	 * TP301.6/85(2D) </h3>
	 *  <p> 
	 *  <span>馆藏复本：3 <br> 可借复本：2</span> 
	 *  杨峰编著 <br> 
	 *  清华大学出版社&nbsp;2015 <br>
	 *   <img src="../tpl/images/star0.gif" title="总体评分及评价人数">(0) 
	 *   <a href="item.php?marc_no=0000458412" class="tooltip" onmouseover="showDetail('0000458412')" onmouseout="hideDetail('0000458412')">馆藏<s></s></a>
	 *    </p> 
 	 *	<div id="detail0000458412" style="display:none;">
  	 *	加载中...
 	 *		</div> </li>
	 * 
	 * */
	private List<Book> extractBooks(Element html){
		List<Book> books = new ArrayList<Book>();
		Elements book_list_info = html.getElementsByClass("book_list_info");
		for(Element e:book_list_info){
			Book book = new Book();
			book.setTitle(e.select("a").get(0).text().substring(2));
			
			
			String regex = "</a>.+</h3>";
			Pattern p = Pattern.compile(regex);
			Matcher m = p.matcher(e.toString());
			if(m.find()){
				book.setIndex(Jsoup.parse(m.group()).text());
			}
			
			String[] str = e.select("span").get(1).toString().split("<br>");
			book.setSum(Jsoup.parse(str[0]).text());
			book.setBorrowed(Jsoup.parse(str[1]).text());
			
			String regex1 = "\\d</span>.*<br>.*<br>.*<img";
			p = Pattern.compile(regex1);
			m = p.matcher(e.toString());
			if(m.find()){
				//(美)H.H. 塔恩(H.H. Tan)[等]著 机械工业出版社?2016
				//这里有个讨厌的? 是&nbsp; 转码而来的，待解决
				String info = Jsoup.parse(m.group().substring(1)).text();
				book.setBookInfo(info);
			}
			
			Element h3 = e.getElementsByTag("h3").first();
			String link = Address.LIBSEARCHBASIC+"/"+h3.select("a").get(0).attr("href");
			book.setItemLink(link);
			books.add(book);
		}
		return books;
	}
	
	
	/*
	 * @param title
	 * 			根据搜索的title书名返回一个List
	 * */
	public List<Book> searchBook(String title){
		Element html = getSearchResultElem(title);
		return extractBooks(html);
	}
	
	/*
	 * @param book
	 * 			要获取item对应的book
	 * 根据Book对象中的link提取item
	 * 获取后set到book对象中
	 * 
	 *	&nbsp; 转码乱码，待解决
	 * 	
	 * 
	 */
	public void getBookItem(Book book){
		if(book==null||book.getItemLink()==null)
			return;
		
		ArrayList<BookItem> items = new ArrayList<BookItem>();
		info.setVisitingAddr(book.getItemLink());
		HttpResponse rep = HttpUtil.get(info);
		Elements trs = null;
		try {
			trs = Jsoup.parse(EntityUtils.toString(rep.getEntity()))
					.getElementById("tab_item").getElementsByTag("tr");
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for(int i=1;i<trs.size();i++){
			BookItem item = new BookItem();
			Elements tds = trs.get(i).getElementsByTag("td");
			String index = tds.get(0).text();
			item.setIndex(index);
			
			String locate = tds.get(3).text();
			item.setLocate(locate);

			String status = tds.get(4).text();
			item.setStatus(status);
			
			items.add(item);
		}
		book.setItems(items);
	}
	
}
