package Client;
import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

import javafx.event.ActionEvent;
import javafx.scene.text.Text;

public class CController {

    @FXML private TextField usernameField;
    @FXML private Button setButton;
    @FXML private TextField portField;
    @FXML private Button connectButton;
    @FXML private TextField serverField;
    @FXML public TextArea chatTextArea;
    @FXML private Button sendButton;
    @FXML private TextField messageField;
    @FXML private TextField senderField;
    @FXML private TextArea onlineTextArea;
    @FXML private Button disconnectButton;
    @FXML private Button updateButton;
    
    public static String messagelog = "";
    
    @FXML protected void handleSetButtonAction(ActionEvent event) {
    	Client.name = usernameField.getText();
    	messagelog = messagelog + "SYS: Username set to " + Client.name + "\n";
    	chatTextArea.setText(messagelog);
    	System.out.println("name set");
    	
    }
    
    
    @FXML protected void handleConnectButtonAction(ActionEvent event) throws UnknownHostException, IOException {
    	if (Client.name == "") {
    		messagelog = messagelog + "SYS: Please set username to a valid username" + "\n";
    		chatTextArea.setText(messagelog);
    	} else if ((serverField.getText() != "") &&(portField.getText() != "")) {
    	
	    	String mchine = serverField.getText();
	    	System.out.println(mchine);
	    	int port = Integer.valueOf(portField.getText());
	    	boolean connection = Client.connect(mchine, port);
	    	if (connection) {
	    		messagelog = messagelog + "SYS: Successfully connected to server " + mchine + ":" + portField.getText() + "\n" + "SYS: Type '/whoishere' to get list of online users\n" ;
	    	} else {
	    		messagelog = messagelog + "SYS: Error connecting to server " + mchine + "\n";
	    	}
	    	chatTextArea.setText(messagelog);
    	} else {
    		messagelog = messagelog + "SYS: Please specify a server" + "\n";
    		chatTextArea.setText(messagelog);
    	}
    }
    
    @FXML protected void handleDCButtonAction(ActionEvent event) {
    	Client.serverthread.getWriter().println("ÿDisconnect" + "þ" + "SYS: " + Client.name + " has left the chat\n" );
    	if (Client.disconnect()) {
    		
    		messagelog = messagelog + "SYS: Disconnected from server " + serverField.getText() + ":" + portField.getText() + "\n";
    		chatTextArea.setText(messagelog);
    	}
    }
    
    @FXML protected void handleSendButtonAction(ActionEvent event) {
    	String message = messageField.getText();
    	String sender = senderField.getText();
    	if (sender.equals("")) {
    		messagelog = messagelog + "Please specify a sender!" + "\n";
    		chatTextArea.setText(messagelog);
    	} else {
	    	//System.out.println(Client.serverthread.getWriter());
	    	Client.serverthread.getWriter().println(sender + "þ" + Client.name + "@" + sender + ": " + message);
	    	//Client.serverthread.getWriter().println(Client.name + ": " + message);
	    	messageField.setText("");
	    	
	    	if (!(sender.equals("All"))) {
	    	messagelog = messagelog + Client.name + "@" + sender + ": " + message + "\n";
	    	}
	    	chatTextArea.setText(messagelog);
    	}
    }
    
    public void updateMessage(String message) {
    	messagelog = messagelog + message + "\n";
    	chatTextArea.setText(messagelog);
    	System.out.println("message updated");
    	updateButton.fire();
    }
    
    @FXML protected void handleUpdateButtonAction(ActionEvent event) {
    	chatTextArea.setText(messagelog);
    	System.out.println("button fired");
    	
    }
    	  
}