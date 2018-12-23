package api.service;

import api.entity.Furniture;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface FurnitureService extends Remote {

    Furniture insertFurniture(Furniture furniture) throws RemoteException;

    List<Furniture>  getAllFurniture() throws RemoteException;

    void deleteFurniture(Long id) throws RemoteException;

    void updateFurniture(Furniture furniture) throws RemoteException;

    void updateQuantityFurniture(Long quantity, Long idFurniture) throws RemoteException;

    Furniture getFurnitureById(Long idFurniture) throws RemoteException;

    Furniture getFurnitureByName(String nameFurniture) throws RemoteException;
}
