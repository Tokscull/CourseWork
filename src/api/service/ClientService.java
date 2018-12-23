package api.service;

import api.entity.Client;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ClientService extends Remote {

    Client insertClient(Client client) throws RemoteException;

    Client registrationClient(Client staff)throws RemoteException;

    List<Client> getAllClient() throws RemoteException;

    void deleteClient(Long id) throws RemoteException;

    void updateClient(Client client) throws RemoteException;

    Client getClientById(Long idClient) throws RemoteException;

    Long checkLoginAndPassword(String login, String password) throws RemoteException;
}
