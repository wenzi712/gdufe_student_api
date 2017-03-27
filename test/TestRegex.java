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
		String div = "<div id=\"4E2907040A3F4A58B0CD99A423357B46-2-2\" style=\"display: none;\" class=\"kbcontent\">"
				+ "���˼�������ԭ��"
				+ "<br>"
				+ "<font title=\"��ʦ\">Ԭ�̺����</font>"
				+ "<br>"
				+ "<font title=\"�ܴ�(�ڴ�)\">3-17(��)</font>"
						+ "<br>"
				+ "<font title=\"����\">����¥(SJ2)419</font>"
						+ "<br>[07-08]��"
						+ "<br>"
						+ " </div> ";
		
		
		String regex1 = "<div.*>.+<br><font title=\"��ʦ\">";
		Pattern p = Pattern.compile(regex1);
		Matcher m = p.matcher(div.toString());
		if(m.find()){
			System.out.println(m.group());
		}
		
	}
}
