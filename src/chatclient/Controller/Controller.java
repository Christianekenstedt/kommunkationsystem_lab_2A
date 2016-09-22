package chatclient.Controller;

import chatclient.Model.ChatClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class Controller {

    @FXML
    private TextField inputField;

    @FXML
    private Button sendBtn;

    @FXML
    private TextArea outputArea;

    private ChatClient chatClient = null;
    @FXML
    public void sendBtnPressed(ActionEvent event) {
        String input = inputField.getText();
        if (chatClient != null){
            if(input.length()>3){
                chatClient.start(input);
            }else{
                addMessage("Invalid hostname");
            }
        }else{
            if (input.length()>0){
                // Skicka från klienten.
            }
        }
        inputField.clear();
    }

    public void setChatClient(ChatClient chatClient){
        this.chatClient = chatClient;
    }

    public void addMessage(String message){
        outputArea.appendText(message + "\n");
    }

    public void reset(){
        sendBtn.setText("Connect");
        inputField.setPromptText("Address");
    }

    public void clear(){
        outputArea.clear();
        sendBtn.setText("Send");
        inputField.setPromptText("Message...");
    }
}
