package api.service;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface BackupService extends Remote {

    String backupDb() throws RemoteException;

    String restoreDb() throws RemoteException;
}
