import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Client extends JFrame {
	
	private JTextField userText;
	private JTextArea chatWindow;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private String message = "";
	private String serverIP;
	private Socket connection;
	
	//constructor
	public Client(String host){
		super("Client");
		serverIP = host;
		userText = new JTextField();
		userText.setEditable(false);
		userText.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				sendMessage(event.getActionCommand());
				userText.setText("");
			}
		});
		add(userText,BorderLayout.NORTH);
		chatWindow = new JTextArea();
		add(new JScrollPane(chatWindow),BorderLayout.CENTER);
		setSize(300,150);
		setVisible(true);
	}
	//Connect to server
	public void startRunning(){
		try{
			connectToServer();
			setupStreams();
			whileChatting();
		}catch(EOFException eofException){
			showMessage("\nClient terminated connection");
		}
		catch(IOException ioException){
			ioException.printStackTrace();
		}
		finally{
			closeStreams();
		}
	}
	//Connet to server
	private void connectToServer() throws IOException{
		showMessage("Attempting connection... \n");
		connection = new Socket(InetAddress.getByName(serverIP),6789);
		showMessage("Connected to: " + connection.getInetAddress().getHostName());
	}
	//Setup streams
	private void setupStreams() throws IOException{
		output = new ObjectOutputStream(connection.getOutputStream());
		output.flush();
		input = new ObjectInputStream(connection.getInputStream());
		showMessage("\n Streams setup");
	}
	//While chatting with server
	private void whileChatting() throws IOException{
		ableToType(true);
		do{
			try{
				message = (String) input.readObject();
				showMessage("\n" + message);
			}catch(ClassNotFoundException classNotFoundException){
				showMessage("\n Object not found");
			}
		}while(!message.equals("Server - END"));
	}
	//Close client
	private void closeStreams(){
		showMessage("\n Closing streams");
		ableToType(false);
		try{
			output.close();
			input.close();
			connection.close();
		}catch(IOException ioException){
			ioException.printStackTrace();
		}
	}
	//Send messages to server
	private void sendMessage(String message){
		try{
			output.writeObject("CLIENT - " + message);
			output.flush();
			showMessage("\nCLIENT - " + message);
		}catch(IOException ioException){
			chatWindow.append("\n Error while sending message");
		}
	}
	//Show message
	private void showMessage(final String message){
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				chatWindow.append(message);
			}
		});
	}
	//Change users ability to type
	private void ableToType(final boolean tof){
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				userText.setEditable(tof);
			}
		});
	}
}
