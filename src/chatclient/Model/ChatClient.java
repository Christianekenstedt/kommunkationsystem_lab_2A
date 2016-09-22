package chatclient.Model;

import chatclient.Controller.Controller;
import javafx.application.Platform;
import rmi.Chat;
import rmi.Notifiable;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by chris on 2016-09-22.
 */
public class ChatClient extends UnicastRemoteObject implements Notifiable {

    private Controller controller;
    private Chat chat = null;

    public boolean isConnected() {
        return connected;
    }

    private boolean connected = false;

    public ChatClient(Controller controller) throws RemoteException {
        super();
        this.controller = controller;
    }

    public void start(String host){
        try{
            String url = "rmi://"+host+"/"+"Chat";
            this.chat = (Chat) Naming.lookup(url);

            chat.register(this);
            connected = true;
            //controller.clear();

        } catch (Exception exc){
            System.out.println(exc.toString());
        }

    }

    public void sendToServer(String s){
        try {
            chat.sendMessage(this, s);
        } catch (RemoteException e) {
            Platform.runLater(() -> controller.addMessage("Could not send message to server."));
            connected = false;
            stop();
        }
    }

    public void stop(){
        try {
            if(isConnected()){
                chat.deregister(this);
            }
        } catch (RemoteException e) {
            Platform.runLater(() -> controller.addMessage("Could not deregister. No connection to server."));
        }finally {
            controller.reset();
            connected = false;
            Platform.runLater(() -> controller.addMessage("Connection closed."));
        }
    }

    @Override
    public void send(String s) throws RemoteException {
        Platform.runLater(() -> controller.addMessage(s));
    }

    @Override
    public void disconnect(String reason) throws RemoteException {
        Platform.runLater(() -> controller.addMessage("Disconnected from server."));
        controller.reset();
        connected = false;
    }
}
