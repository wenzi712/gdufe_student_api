import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/*
 * <div id="4E2907040A3F4A58B0CD99A423357B46-2-2" style="display: none;" class="kbcontent">
	 ���˼�������ԭ��
	 <br>
	 <font title="��ʦ">Ԭ�̺����</font>
	 <br>
	 <font title="�ܴ�(�ڴ�)">3-17(��)</font>
	 <br>
	 <font title="����">��ѧ¥(SJ1)108</font>
	 <br>[01-02]��
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
