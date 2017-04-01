import java.util.List;


import org.apache.http.impl.client.HttpClients;

import com.gdufe.login.LoginingInfo;
import com.gdufe.login.PortalClient;

import com.gdufe.model.Book;
import com.gdufe.model.BookItem;
import com.gdufe.query.LibraryQuery;

import com.gdufe.query.CardQuery;


public class ProtalLoginTest {
	public static void main(String[] args) {
		PortalClient client = new PortalClient(HttpClients.createDefault());
		LoginingInfo info = client.login("14251102221", "291608411");

		LibraryQuery query = new LibraryQuery(info);
		List<Book> books = query.searchBook("c”Ô—‘");
		query.getBookItem(books.get(1));
		System.out.println(books.get(1).getItems());

		CardQuery query = new CardQuery(info);
		System.out.println(query.getCardBalance());
	}
}
