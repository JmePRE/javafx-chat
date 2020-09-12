package Server;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Client.ClientThread;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
public class ChatServer extends Application{
	
	public static ServerSocket serverSocket;
	public static int portNumber;
	public static ArrayList<ClientThread> clients;
	public static InetAddress ip;
	public static List<String> names;

	/**set up server**/
  
  public void start(Stage primaryStage) throws Exception{
      Parent root = FXMLLoader.load(getClass().getResource("chat.fxml"));
      ip = InetAddress.getLocalHost();
      primaryStage.setTitle("Chat App (Server) - " + ip); 
      primaryStage.setScene(new Scene(root, 1000, 700));
      primaryStage.show();
  }
  
  public static void acceptClients() throws InterruptedException {
	  clients = new ArrayList<ClientThread>();
	  names = new ArrayList<String>();
	  System.out.println("ready to accept");
	  int n=0;
	  /**create new thread for each client request continuously**/
	  
	  while (true) try {
		  Socket socket = serverSocket.accept();
		  System.out.println("accepted");
		  n+=1;
		  ClientThread client = new ClientThread(socket);
		  
		  Thread thread = new Thread(client);
		  thread.start();
		  clients.add(client);
		  System.out.println(clients);
		  System.out.println("new client!");
		  
		  
		  //client.getWriter().println("SYS: Your username is " + client.getName());
		  
		  
	  } catch (IOException e) {
		  System.out.println("Unable to accept connection on port "+String.valueOf(portNumber));
	  }
  }

  public static void setupserver(int portnumbr) throws InterruptedException {
	  /** setting up server socket to listen for connections **/
	  portNumber = portnumbr;
	  serverSocket = null;
	  
	  try {
		  
		  serverSocket = new ServerSocket(portNumber);
		  System.out.println("Listening on port " + String.valueOf(portNumber));
		  System.out.println("Your address is " + ip);
		  
		  
		  acceptClients();

		  
	  } catch (IOException e) {
		  System.err.println("Unable to listen on port " + String.valueOf(portNumber));
		  System.exit(1);
	  }
  }
  

  public static void main(String[] args) {

	  try {
		ip = InetAddress.getLocalHost();
	} catch (UnknownHostException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	  Scanner sc= new Scanner(System.in);    
	  System.out.print("Enter desired port (integer only): ");  
	  String port= sc.next();   //reads string before the space  
	  System.out.println("Connecting to port: "+ port); 
	  int portn = Integer.parseInt(port);
	  try {
		setupserver(portn);
	} catch (InterruptedException e) {
		e.printStackTrace();
		System.out.println("ERROR: Port must be an integer!");
	}

	  
      
  }
  
}