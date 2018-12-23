package api.service;



import api.entity.Order;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface OrderService extends Remote {

    Order insertOrder(Order order) throws RemoteException;

    Order insertOrderWithoutClient(Order order) throws RemoteException;

    Order insertOrderFromClient(Order order) throws RemoteException;

    List<Order> getAllUnActiveOrder() throws RemoteException;

    List<Order> getAllActiveOrder() throws RemoteException;

    void deleteOrder(Long id) throws RemoteException;

    void updateOrder(Order order) throws RemoteException;
}
