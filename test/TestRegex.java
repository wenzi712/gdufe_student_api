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
		String str = "";
		String[] s = str.split("=");
		System.out.println(s.length);
		for(String s1:s)
			System.out.println(s1);
	}
}
