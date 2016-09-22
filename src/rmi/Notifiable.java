package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by Anton on 2016-09-22.
 */
public interface Notifiable extends Remote {
    void send(String s) throws RemoteException;
    void disconnect(String reason) throws RemoteException;
}
