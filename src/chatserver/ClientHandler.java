package chatserver;

import rmi.Notifiable;

import java.rmi.RemoteException;

/**
 * Created by Anton on 2016-09-22.
 */
public class ClientHandler {
    private Notifiable client;
    private String name = "unnamed";

    public ClientHandler(Notifiable n){
        this.client = n;
    }

    public void send(String s) throws RemoteException {
        client.send(s);
    }

    public String getName(){
        return name;
    }

    public void setName(String newName){
        this.name = newName;
    }

    public boolean compare(Notifiable n){
        return this.client.equals(n);
    }
}
