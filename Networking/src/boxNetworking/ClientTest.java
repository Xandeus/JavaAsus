package boxNetworking;
import javax.swing.JFrame;
public class ClientTest {
	public static void main(String args[]){
		Client client;
		
		client = new Client("192.168.56.1");
		client.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		client.startRunning();
	}
}