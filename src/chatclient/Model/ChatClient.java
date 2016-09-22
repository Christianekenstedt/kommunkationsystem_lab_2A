package chatclient.Model;

import chatclient.Controller.Controller;
import rmi.Chat;
import rmi.Notifiable;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by chris on 2016-09-22.
 */
public class ChatClient extends UnicastRemoteObject implements Notifiable {

    private Controller controller;
    private int port;
    private String serverName;
    private Chat chat = null;
    public ChatClient(Controller controller) throws RemoteException {
        super();
        this.controller = controller;
    }

    private void setChat(Chat chat){
        this.chat = chat;
    }

    public void start(String host){
        //TODO: Take all input and doo lookup


        try{
            String url = "rmi://"+host+"/"+"Chat";
            //Chat chat = (Chat) Namin.lookup(url);
            this.setChat(chat);

            chat.register(this);

            this.run();

        } catch (Exception exc){
            System.out.println(exc.toString());
        }

    }

    public void run(){

    }

    @Override
    public void send(String s) throws RemoteException {

    }
}
