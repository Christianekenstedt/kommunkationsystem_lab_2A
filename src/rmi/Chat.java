package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by Anton on 2016-09-22.
 */
public interface Chat extends Remote{

    void register(Notifiable n) throws RemoteException;

    void deregister(Notifiable n) throws RemoteException;

    void sendMessage(Notifiable n, String s) throws RemoteException;
}
