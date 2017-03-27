import java.util.Set;

import org.apache.http.impl.client.HttpClients;

import com.gdufe.login.Client;
import com.gdufe.login.LoginingInfo;
import com.gdufe.login.Status;
import com.gdufe.model.Course;
import com.gdufe.query.CourseQuery;


public class LoginTest {
	public static void main(String[] args) {
		Client login = new Client(HttpClients.createDefault());
		LoginingInfo info = login.login("14251102221", "291608411");
		
		System.out.println(info.getLoginStatus());
		
		if(info.getLoginStatus()==Status.ON_LOGIN){
			Set<Course> set = new CourseQuery(info).getScheduleSet("2014","2015",1);
			System.out.println(set.size());
			for(Course c:set){
				System.out.println(c.toString());
			}
		}

	}
}
