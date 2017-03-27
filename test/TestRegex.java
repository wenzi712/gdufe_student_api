import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/*
 * <div id="4E2907040A3F4A58B0CD99A423357B46-2-2" style="display: none;" class="kbcontent">
	 马克思主义基本原理
	 <br>
	 <font title="老师">袁继红教授</font>
	 <br>
	 <font title="周次(节次)">3-17(周)</font>
	 <br>
	 <font title="教室">励学楼(SJ1)108</font>
	 <br>[01-02]节
	 <br>
	</div> 
 * */
public class TestRegex {
	public static void main(String[] args) {
		String div = "<div id=\"4E2907040A3F4A58B0CD99A423357B46-2-2\" style=\"display: none;\" class=\"kbcontent\">"
				+ "马克思主义基本原理"
				+ "<br>"
				+ "<font title=\"老师\">袁继红教授</font>"
				+ "<br>"
				+ "<font title=\"周次(节次)\">3-17(周)</font>"
						+ "<br>"
				+ "<font title=\"教室\">笃行楼(SJ2)419</font>"
						+ "<br>[07-08]节"
						+ "<br>"
						+ " </div> ";
		
		
		String regex1 = "<div.*>.+<br><font title=\"老师\">";
		Pattern p = Pattern.compile(regex1);
		Matcher m = p.matcher(div.toString());
		if(m.find()){
			System.out.println(m.group());
		}
		
	}
}
