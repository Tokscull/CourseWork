package server.service;

import api.entity.Provider;
import api.service.ProviderService;
import server.utilities.DatabaseConnection;

import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProviderServiceImpl extends UnicastRemoteObject implements ProviderService, ServiceImpl {

    public ProviderServiceImpl() throws RemoteException {}

    @Override
    public Provider insertProvider(Provider provider) throws RemoteException {
        try {
            System.out.print("\nClient " + getClientHost() + " request Provider.insertProvider() method...");
        } catch (ServerNotActiveException e) {
            e.printStackTrace();
        }

        PreparedStatement statement = null;
        String sql = "insert into providers (idProviders, nameProvider, country," +
                " town, address, contactPerson, contactNumber, note) values (null, ?, ?, ?, ?, ?, ?, ?)";

        Provider list;
        try {
            statement = DatabaseConnection.getConnection().prepareStatement(sql, 1);
            statement.setString(1,provider.getNameProvider());
            statement.setString(2,provider.getCountry());
            statement.setString(3,provider.getTown());
            statement.setString(4,provider.getAddress());
            statement.setString(5,provider.getContactPerson());
            statement.setString(6,provider.getContactNumber());
            statement.setString(7,provider.getNote());
            statement.executeUpdate();
            ResultSet result = statement.getGeneratedKeys();
            if (result.next()) {
                provider.setIdProviders(result.getLong(1));
            }
            result.close();
            System.out.println("[successful]");
            list = provider;
        } catch (SQLException e) {
            System.out.println("[failed]");
            e.printStackTrace();
            list = null;
        }finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException var19) {
                    var19.printStackTrace();
                }
            }

            try {
                DatabaseConnection.getConnection().close();
            } catch (SQLException var18) {
                var18.printStackTrace();
            }

        }
        return list;
    }

    @Override
    public List<Provider> getAllProvider() throws RemoteException {
        try {
            System.out.print("\nClient " + getClientHost() + " request Provider.getAllFProvider() method...");
        } catch (ServerNotActiveException e) {
            e.printStackTrace();
        }

        Statement statement = null;
        String sql = "select * from providers";

        ArrayList list;
        try{
            statement = DatabaseConnection.getConnection().createStatement();
            ResultSet result = statement.executeQuery(sql);
            list = new ArrayList();

            while(result.next()) {
                Provider provider = new Provider();
                provider.setIdProviders(result.getLong("idProviders"));
                provider.setNameProvider(result.getString("nameProvider"));
                provider.setCountry(result.getString("country"));
                provider.setTown(result.getString("town"));
                provider.setAddress(result.getString("address"));
                provider.setContactPerson(result.getString("contactPerson"));
                provider.setContactNumber(result.getString("contactNumber"));
                provider.setNote(result.getString("note"));

                list.add(provider);
            }

            result.close();
            System.out.println("[successful]");
        } catch (SQLException e) {
            System.out.println("[failed]");
            e.printStackTrace();
            list = null;
        }finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            try {
                DatabaseConnection.getConnection().close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    @Override
    public void deleteProvider(Long id) throws RemoteException {
        try {
            System.out.print("\nClient " + getClientHost() + " request Provider.deleteProvider() method...");
        } catch (ServerNotActiveException e) {
            e.printStackTrace();
        }

        delete("delete from providers where idProviders = ",id);

    }

    @Override
    public void updateProvider(Provider provider) throws RemoteException {
        try {
            System.out.print("\nClient " + getClientHost() + " request Provider.updateFurniture() method...");
        } catch (ServerNotActiveException e) {
            e.printStackTrace();
        }

        PreparedStatement statement = null;
        String sql = "update providers set nameProvider = ?, country = ?, town = ?, address = ?, contactPerson = ?," +
                " contactNumber = ?, note = ?  where idProviders = ?";

        try{
            statement = DatabaseConnection.getConnection().prepareStatement(sql);
            statement.setString(1,provider.getNameProvider());
            statement.setString(2,provider.getCountry());
            statement.setString(3,provider.getTown());
            statement.setString(4,provider.getAddress());
            statement.setString(5,provider.getContactPerson());
            statement.setString(6,provider.getContactNumber());
            statement.setString(7,provider.getNote());
            statement.setLong(8,provider.getIdProviders());
            statement.executeUpdate();
            System.out.println("[successful]");
        } catch (SQLException e) {
            System.out.println("[failed]");
            e.printStackTrace();
        }finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            try {
                DatabaseConnection.getConnection().close();
            } catch (SQLException var16) {
                var16.printStackTrace();
            }
        }
    }

    @Override
    public List<String> getAllNameProvider() throws RemoteException {
        try {
            System.out.print("\nClient " + getClientHost() + " request Provider.getAllNameProvider() method...");
        } catch (ServerNotActiveException var20) {
            var20.printStackTrace();
        }

        Statement statement = null;
        String sql = "select nameProvider from providers";

        ArrayList list;
        try {
            statement = DatabaseConnection.getConnection().createStatement();
            ResultSet result = statement.executeQuery(sql);
            list = new ArrayList();

            while (result.next()) {
                list.add(result.getString(1));
            }
            result.close();
            System.out.println("[successful]");
        } catch (SQLException e) {
            System.out.println("[failed]");
            e.printStackTrace();
            list = null;
        }finally {
            if(statement != null){
                try{
                    statement.close();
                }catch (SQLException var20){
                    var20.printStackTrace();
                }
            }
            try {
                DatabaseConnection.getConnection().close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;

    }
}
