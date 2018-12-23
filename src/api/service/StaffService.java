package api.service;

import api.entity.Staff;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface StaffService extends Remote {

    Staff insertStaff(Staff staff) throws RemoteException;

    Staff registrationStaff(Staff staff)throws RemoteException;

    List<Staff> getAllStaff() throws RemoteException;

    void deleteStaff(Long id) throws RemoteException;

    void updateStaff(Staff staff) throws RemoteException;

    Long checkLoginAndPassword(String login, String password) throws RemoteException;

}
