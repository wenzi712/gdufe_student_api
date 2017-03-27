import java.util.Set;

import org.apache.http.impl.client.HttpClients;

import com.gdufe.login.Client;
import com.gdufe.login.LoginingInfo;
import com.gdufe.login.Status;
import com.gdufe.model.Course;
import com.gdufe.model.Student;
import com.gdufe.query.CourseQuery;
import com.gdufe.query.StudentQuery;


public class LoginTest {
	public static void main(String[] args) {
		Client login = new Client(HttpClients.createDefault());
		LoginingInfo info = login.login("14251102221", "291608411");
		
		System.out.println(info.getLoginStatus());
		
		/*if(info.getLoginStatus()==Status.ON_LOGIN){
			Set<Score> set = new ScoreQuery(info).getScoreSetByTerm("2014","2015", 1);
			for(Score s:set){
				System.out.println(s.toString());
			}
		}*/
		
		/*if(info.getLoginStatus()==Status.ON_LOGIN){
			Set<Course> set = new CourseQuery(info).getScheduleSet("2014","2015", 1);
			for(Course c:set){
				System.out.println(c.toString());
			}
		}*/
		
		if(info.getLoginStatus()==Status.ON_LOGIN){
			Student s = new StudentQuery(info).getStudent();
			Set<Course> set = new CourseQuery(info).getScheduleSet("2014","2015", 1);
			for(Course c:set){
				System.out.println(c.toString());
			}
			System.out.println(s);
		}
		
	}
}
