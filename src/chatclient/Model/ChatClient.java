package chatclient.Model;

import chatclient.Controller.Controller;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by chris on 2016-09-22.
 */
public class ChatClient extends UnicastRemoteObject {

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
        }catch(NotBoundException nbe){
            System.out.println(nbe.toString());
            System.out.println("'Chat' not avaliable.");

        }catch (Exception exc){
            System.out.println(exc.toString());
        }

    }

    public void run(){}
}
