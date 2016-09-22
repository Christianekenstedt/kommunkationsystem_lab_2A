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

    private ChatClient chatClient;
    @FXML
    void sendBtnPressed(ActionEvent event) {

    }

    public void setChatClient(ChatClient chatClient){
        this.chatClient = chatClient;
    }

}
