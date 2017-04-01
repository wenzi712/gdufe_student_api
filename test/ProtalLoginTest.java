import com.gdufe.login.LoginingInfo;
import com.gdufe.query.CardQuery;


public class ProtalLoginTest {
	public static void main(String[] args) {
		/*PortalClient client = new PortalClient(HttpClients.createDefault());
		LoginingInfo info = client.login("14251102221", "291608411");
System.out.println(info.cookieMap2String());
		info.cookiePersist("C://Users//˧//Desktop//cookie.txt");*/
		
		LoginingInfo info = LoginingInfo.readCookie("C://Users//˧//Desktop//cookie.txt");
		CardQuery query = new CardQuery(info);
		System.out.println(query.getCardBalance());
		
		
		
	}
}
