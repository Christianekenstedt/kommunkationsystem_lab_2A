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

    private ArrayList<ClientHandler> clients;

    public ChatImpl() throws RemoteException {
        super();

        this.clients = new ArrayList<>();
    }


    @Override
    public synchronized void register(Notifiable n) {
        try {
            n.send("Welcome!");
            clients.add(new ClientHandler(n));
            System.out.println("Register: Client joined.");
        } catch (RemoteException e) {
            System.out.println("Could not send to client.");
        }
    }

    @Override
    public synchronized void deregister(Notifiable n) {
        try{
            clients.remove(getClientHandler(n));
            n.disconnect("Client disconnected.");
            System.out.println("Client disconnected.");
        }catch(Exception e){
            System.out.println("Deregister: No such client found.");
        }

    }

    @Override
    public synchronized void sendMessage(Notifiable client, String s) {
        ArrayList<ClientHandler> toRemove = new ArrayList<>();
        if(s.charAt(0) == '/'){
            try {
                processCommand(client, s);
            } catch (RemoteException e) {
                ClientHandler ch = getClientHandler(client);

                if(ch != null){
                    System.out.println("Could not execute command for client:" + ch.getName());
                    toRemove.add(ch);
                }

            }
        }
        else{
            ClientHandler sender = getClientHandler(client);
            s = sender.getName() + ": " + s;
            for(ClientHandler n : clients){
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
        }
        clients.remove(toRemove);
    }

    public String getClientNames(){
        String names = "Clients connected:\n";
        for(ClientHandler ch : clients){
            names += ch.getName() + "\n";
        }
        return names;
    }

    public ClientHandler getClientHandler(Notifiable n){
        for(ClientHandler ch : clients){
            if(ch.compare(n)){
                return ch;
            }
        }
        return null;
    }

    public ClientHandler getClientHandler(String name){
        for(ClientHandler ch : clients){
            if(ch.getName().equals(name)){
                return ch;
            }
        }
        return null;
    }

    public void processCommand(Notifiable client, String cmd) throws RemoteException {
        String[] split = cmd.split(" ");
        switch(split[0]){
            case "/help":
                client.send(getHelpString());
                break;
            case "/who":
                client.send(getClientNames());
                break;
            case "/nick":
                String newName = cmd.substring(cmd.indexOf(' ')).trim();
                if(newName.length() < 3){
                    client.send("Name is too short.");
                }else{
                    if(getClientHandler(newName) == null){
                        getClientHandler(client).setName(newName);
                    }else{
                        client.send("Name is already taken.");
                    }
                }
                break;
            case "/quit":
                deregister(client);
                break;

            default:
                client.send("Unknown command. Use /help to see available commands.");
                break;
        }
    }

    public String getHelpString(){
        return "/who - see all connected\n/nick - change name\n/quit - leave server\n";
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
