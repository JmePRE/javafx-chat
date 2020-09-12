package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import Client.CController;
import Client.Client;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class ServerThread implements Runnable{

	private Socket socket;
	
	/**constructor method**/
	public ServerThread(Socket scket) {
		this.socket = scket;
	}
	
	private String name;
	private BufferedReader serverbr;
	private BufferedReader clientbr;
	private PrintWriter spw;
	
	public ServerThread(Socket sckt, String nme) {
		this.socket = sckt;
		this.name = nme;
	}
	
	public PrintWriter getWriter() {
		return this.spw;
	}
	
	
	public CController getController() throws IOException {
		
	    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Client/chat.fxml"));
	    Parent root = loader.load();
	    CController control = (CController)loader.getController();
	    System.out.println(control);
	    return control;

	} 
	
	@Override
	public void run(){
		try { 
		spw = new PrintWriter(socket.getOutputStream(), true);
		System.out.println("spw setup");

		serverbr = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		System.out.println("server br setup");

		clientbr = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("client br setup");
		
		//CController control = getController();

		
		while (socket.isClosed() == false) {
			if(serverbr.ready()) {
				String message = serverbr.readLine();
				System.out.println("client has received " + message);
				
				//CController.updateMessage(message);
				Platform.runLater(new Runnable(){

					@Override
					public void run() {
						// TODO Auto-generated method stub
						Client.control.updateMessage(message);
					}
					
				});
			}
			if (clientbr.ready()) {
				spw.println(name + ": " + clientbr.readLine());
			}
		}
		
	} catch (IOException e) {
		e.printStackTrace();
		
	}

}
}
