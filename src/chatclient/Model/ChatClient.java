package chatclient.Model;

import chatclient.Controller.Controller;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by chris on 2016-09-22.
 */
public class ChatClient extends UnicastRemoteObject {

    private Controller controller;

    public ChatClient(Controller controller) throws RemoteException {
        super();
        this.controller = controller;
    }

    public void start(){
        //TODO: Take all input and doo lookup
    }
}
