package chatclient.Controller;

import chatclient.Model.ChatClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.rmi.RemoteException;

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

        if(chatClient.isConnected()){
            if(input.length() > 0){
                chatClient.sendToServer(input);
            }
        }else{
            if(input.length() > 3){
                chatClient.start(input);
                if(chatClient.isConnected()){
                    clear();
                }else{
                    addMessage("Could not connect to host.");
                }

            }else{
                addMessage("Invalid hostname.");
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
