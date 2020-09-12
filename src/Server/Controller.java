package Server;
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
import java.util.List;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Controller {

    @FXML private Text addressText;
    @FXML private Button enterButton;
    @FXML private TextField serverField;
    @FXML private TextField portField;
    @FXML private TextArea chatTextArea;
    @FXML private Button sendButton;
    @FXML private TextField messageField;
    @FXML private ChoiceBox<String> peopleChoiceBox;
    @FXML private static TextArea onlineTextArea;
    
    public static String messagelog = "";
    
    @FXML protected void handleEnterButtonAction(ActionEvent event) throws InterruptedException {
    	
    	/**
        int port = Integer.valueOf(portField.getText());
        addressText.setText("Listening on port " + portField.getText());
  	  	Platform.exit();
        ChatServer.setupserver(port);	
        //Stage stage = (Stage) enterButton.getScene().getWindow();
        //stage.close();
        
        **/
    }
    
    @FXML protected void handleSendButtonAction(ActionEvent event) {
    	
    }
    
    @FXML public static void updateOnline(List<String> rows) {
    	String online = String.join("\n", rows);
    	onlineTextArea.setText(online);
    	System.out.println(online);
    }
    	  
}