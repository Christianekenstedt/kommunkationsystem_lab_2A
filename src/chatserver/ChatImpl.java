package chatserver;

import rmi.Chat;
import rmi.Notifiable;

import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.*;
import java.util.ArrayList;

/**
 * Created by Anton on 2016-09-22.
 */
public class ChatImpl extends UnicastRemoteObject implements Chat {

    private ArrayList<Notifiable> clients;

    public ChatImpl() throws RemoteException {
        super();

        this.clients = new ArrayList<>();
    }


    @Override
    public synchronized void register(Notifiable n) {
        clients.add(n);
    }

    @Override
    public synchronized void deregister(Notifiable n) {
        clients.remove(n);
    }

    @Override
    public synchronized void sendMessage(String s) {
        ArrayList<Notifiable> toRemove = new ArrayList<>();
        for(Notifiable n : clients){
            try{
                n.send(s);
            }catch(NullPointerException npe){
                System.out.println("Could not send to client. Removing from list.");
                toRemove.add(n);
            }catch(RemoteException re){
                System.out.println(re.getMessage());
                toRemove.add(n);
            }
        }

        clients.remove(toRemove);
    }

    public static void main(String[] args){
        try{
            LocateRegistry.createRegistry(1099);
            ChatImpl chat = new ChatImpl();
            Naming.rebind("Chat", chat);
            System.out.println("Chat server started");
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}
