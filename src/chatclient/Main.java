package chatclient;

import chatclient.Controller.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("View/chatclient.fxml")
        );
        Parent root = loader.load();
        primaryStage.setTitle("Chat Notifiable");
        primaryStage.setScene(new Scene(root, 400, 400));

        Controller controller = loader.getController();
        //TODO: Ev skicka in controller till någon datamodell och datamodell till controller.
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
