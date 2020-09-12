package Client;

import java.io.*;
import java.net.*;
import java.util.Scanner;

import Server.ServerThread;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
public class Client extends Application{

  public static String machine;
  public static String name = "";
  public static int portNumber;
  public static Thread client;
  public static ServerThread serverthread;
  public static Socket socket;
  
  public static CController control;
	
  public void start(Stage primaryStage) throws Exception{
	  FXMLLoader loader = new FXMLLoader(getClass().getResource("chat.fxml"));
	  Parent root = loader.load();
	  control = (CController)loader.getController();
	  //Parent root = FXMLLoader.load(getClass().getResource("chat.fxml"));
      //control = (CController) FXMLLoader.getController();
      primaryStage.setTitle("Chat App (Client)");
      primaryStage.setScene(new Scene(root, 1000, 700));
      primaryStage.show();
  }
  
  public static boolean disconnect() {
	  System.out.println(name + " has left");
		
	  try {
		socket.close();
		return true;
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return false;
	}
  }

  public static boolean connect(String mchine, int portNmber) {
	  portNumber = portNmber;
	  socket = null;
	  machine = mchine;	  
	  try {
		  /** start new connected server thread **/
		  
		  socket = new Socket(machine, portNumber);
		  Thread.sleep(1000);
		  
		  //Thread server = new Thread(new ServerThread(socket));
		  //server.start();
		  serverthread = new ServerThread(socket);
		  System.out.println("serverthread setup");
		  client = new Thread(serverthread);
		  System.out.println("thread setup");

		  client.start();
		  System.out.println("client run");
		  
			//send username over
		  Thread.sleep(1000);
	  	  serverthread.getWriter().println(name);
	  	  System.out.println("username sent");

		  
	  } catch (IOException e) {
		  System.err.println("Fatal Connection Error!");
		  //System.exit(1);
		  return false;
	  } catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return false;
	}
	  System.out.println("Connected on port " + String.valueOf(portNumber));
	  return true;
  }
  

  public static void main(String[] args) throws InterruptedException {
	  
	  /** setting up client side socket to connect to server **/
	  
	  
	  /**
	  System.out.println("username: ");
	  Scanner scan = new Scanner(System.in);
	  String name = scan.nextLine();
	  scan.close();
	  **/
	  
	  
      Application.launch(args);
  }
  
}