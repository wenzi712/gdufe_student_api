import org.apache.http.impl.client.HttpClients;

import com.gdufe.login.LoginingInfo;
import com.gdufe.login.PortalClient;
import com.gdufe.query.CardQuery;


public class ProtalLoginTest {
	public static void main(String[] args) {
		PortalClient client = new PortalClient(HttpClients.createDefault());
		LoginingInfo info = client.login("14251102221", "291608411");
		CardQuery query = new CardQuery(info);
		System.out.println(query.getCardBalance());
	}
}
