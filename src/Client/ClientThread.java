package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import Server.ChatServer;

public class ClientThread extends ChatServer implements Runnable{

	private Socket socket;
	private String name;
	
	/**constructor method**/
	public ClientThread(Socket scket) {
		this.socket = scket;
		this.name = "noname";
	}
	
	
	public ClientThread(Socket scket, String nme) {
		this.socket = scket;
		this.name=nme;
	}
	
	
	/**for sending and receiving**/
	private BufferedReader br;
	private PrintWriter pw;
	
	public PrintWriter getWriter() {
		return this.pw;
	}
	
	public String getName() {
		return this.name;
	}
	
	@Override
	public void run() {
		try {
			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			System.out.println("Client br setup");
			pw = new PrintWriter(socket.getOutputStream(), true);
			System.out.println("Client pw setup");

			this.name = br.readLine();
			System.out.println("name: " + name);
			names.add(this.name);
			
			
			//checks for new messages consistently
			while (socket.isClosed() == false) {
				String message = br.readLine();
				
				String[] parts = message.split("þ");
				String recipient = parts[0];
				System.out.println(recipient);
				System.out.println(message);
				message = parts[1];
				
				for (ClientThread client : clients) {
					/**
					if (parts[0] == "") {
						client.getWriter().println(message);
						System.out.println(parts[1] + " sent to " + client.getName());
					} else if (client.getName() == parts[0]) {
							client.getWriter().println(message);
							System.out.println(message + " sent to " + client.getName());
						} **/
					if (recipient.equals("ÿDisconnect")) {
						client.getWriter().println(message);
						System.out.println(message + " sent to " + client.getName());
						System.out.println(name + " socket closed");
						if (client.getName().equals(this.name)) {
							int index = clients.indexOf(client);
							clients.remove(client);
							names.remove(this.name);
						}
						break;
						
					}
					if ((recipient.equals("All")) || (recipient.equals(client.getName())) ) {
						client.getWriter().println(message);
						System.out.println(message + " sent to " + client.getName());
					}
					if (message.contains("/whoishere")) {
						String people = String.join(", ", ChatServer.names);
						client.getWriter().println("SYS: Online - " + people);
						System.out.println(people);
						}
					}
				}
			
			
			
					
				
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
