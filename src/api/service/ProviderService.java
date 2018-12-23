package api.service;

import api.entity.Provider;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ProviderService extends Remote {
    Provider insertProvider(Provider provider) throws RemoteException;

    List<Provider> getAllProvider() throws RemoteException;

    void deleteProvider(Long id) throws RemoteException;

    void updateProvider(Provider provider) throws RemoteException;

    List<String> getAllNameProvider() throws RemoteException;
}
