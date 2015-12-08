package CarpoolServer;

public class DBTest {

	public static void main(String[] args) {
		DatabaseConnector dc = new DatabaseConnector();
		dc.connect();
		dc.update("user", "id", "testid", "password", "123456");
		dc.close();
	}

}
