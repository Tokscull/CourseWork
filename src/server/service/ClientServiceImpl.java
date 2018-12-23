package server.service;

import api.entity.Client;
import api.service.ClientService;
import server.utilities.DatabaseConnection;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.ServerNotActiveException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ClientServiceImpl extends UnicastRemoteObject implements ClientService, ServiceImpl {

    public ClientServiceImpl() throws RemoteException {
    }

    @Override
    public Client insertClient(Client client) throws RemoteException {
        try {
            System.out.print("\nClient " + getClientHost() + " request Client.insertClient() method...");
        } catch (ServerNotActiveException e) {
            e.printStackTrace();
        }

        PreparedStatement statement = null;
        String sql = "insert into clients (idClient, surnameClient, nameClient," +
                " numberClient, countryClient, townClient, addressClient, noteClient, username, password)" +
                " values (null, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        Client list;
        try {
            statement = DatabaseConnection.getConnection().prepareStatement(sql, 1);
            statement.setString(1,client.getSurnameClient());
            statement.setString(2,client.getNameClient());
            statement.setString(3,client.getNumberClient());
            statement.setString(4,client.getCountryClient());
            statement.setString(5,client.getTownClient());
            statement.setString(6,client.getAddressClient());
            statement.setString(7,client.getNoteClient());
            statement.setString(8,client.getUsername());
            statement.setString(9,client.getPassword());
            statement.executeUpdate();
            ResultSet result = statement.getGeneratedKeys();
            if (result.next()) {
                client.setIdClient(result.getLong(1));
            }
            result.close();
            System.out.println("[successful]");
            list = client;
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
    public Client registrationClient(Client client) throws RemoteException {
        try {
            System.out.print("\nClient " + getClientHost() + " request Client.registrationClient() method...");
        } catch (ServerNotActiveException e) {
            e.printStackTrace();
        }

        PreparedStatement statement = null;
        String sql = "insert into clients (idClient, surnameClient, nameClient," +
                "  username, password)values (null, ?, ?, ?, ?)";

        Client list;
        try {
            statement = DatabaseConnection.getConnection().prepareStatement(sql, 1);
            statement.setString(1,client.getSurnameClient());
            statement.setString(2,client.getNameClient());
            statement.setString(3,client.getUsername());
            statement.setString(4,client.getPassword());
            statement.executeUpdate();
            ResultSet result = statement.getGeneratedKeys();
            if (result.next()) {
                client.setIdClient(result.getLong(1));
            }
            result.close();
            System.out.println("[successful]");
            list = client;
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
    public List<Client> getAllClient() throws RemoteException {
        try {
            System.out.print("\nClient " + getClientHost() + " request Client.getAllClient() method...");
        } catch (ServerNotActiveException e) {
            e.printStackTrace();
        }

        Statement statement = null;
        String sql = "select * from clients";

        ArrayList list;
        try{
            statement = DatabaseConnection.getConnection().createStatement();
            ResultSet result = statement.executeQuery(sql);
            list = new ArrayList();

            while(result.next()) {
                Client client = new Client.Builder()
                        .buildIdClient(result.getLong("idClient"))
                        .buildSurnameClient(result.getString("surnameClient"))
                        .buildNameClient(result.getString("nameClient"))
                        .buildNumberClient(result.getString("numberClient"))
                        .buildCountryClient(result.getString("countryClient"))
                        .buildTownClient(result.getString("townClient"))
                        .buildAddressClient(result.getString("addressClient"))
                        .buildNoteClient(result.getString("noteClient"))
                        .buildUsername(result.getString("username"))
                        .buildPassword(result.getString("password"))
                        .build();

                list.add(client);
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
    public void deleteClient(Long id) throws RemoteException {
        try {
            System.out.print("\nClient " + getClientHost() + " Client.request deleteClient() method...");
        } catch (ServerNotActiveException e) {
            e.printStackTrace();
        }

        delete("delete from clients where idClient = ", id);

    }

    @Override
    public void updateClient(Client client) throws RemoteException {
        try {
            System.out.print("\nClient " + getClientHost() + " request Client.updateClient() method...");
        } catch (ServerNotActiveException e) {
            e.printStackTrace();
        }

        PreparedStatement statement = null;
        String sql = "update clients set surnameClient = ?, nameClient = ?, numberClient = ?, countryClient = ?, townClient = ?," +
                " addressClient = ?, noteClient = ?, username = ?, password = ?  where idClient = ?";

        try{
            statement = DatabaseConnection.getConnection().prepareStatement(sql);
            statement.setString(1,client.getSurnameClient());
            statement.setString(2,client.getNameClient());
            statement.setString(3,client.getNumberClient());
            statement.setString(4,client.getCountryClient());
            statement.setString(5,client.getTownClient());
            statement.setString(6,client.getAddressClient());
            statement.setString(7,client.getNoteClient());
            statement.setString(8,client.getUsername());
            statement.setString(9,client.getPassword());
            statement.setLong(10,client.getIdClient());
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
    public Client getClientById(Long idClient) throws RemoteException {
        try {
            System.out.print("\nClient " + getClientHost() + " request Client.getClientById() method...");
        } catch (ServerNotActiveException var21) {
            var21.printStackTrace();
        }

        PreparedStatement statement = null;
        String sql = "select * from clients where idClient = ?";

        Client client = new Client();
        try {
            statement = DatabaseConnection.getConnection().prepareStatement(sql);
            statement.setLong(1, idClient);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                client = new Client.Builder()
                        .buildIdClient(result.getLong("idClient"))
                        .buildSurnameClient(result.getString("surnameClient"))
                        .buildNameClient(result.getString("nameClient"))
                        .buildNumberClient(result.getString("numberClient"))
                        .buildCountryClient(result.getString("countryClient"))
                        .buildTownClient(result.getString("townClient"))
                        .buildAddressClient(result.getString("addressClient"))
                        .buildNoteClient(result.getString("noteClient"))
                        .buildUsername(result.getString("username"))
                        .buildPassword(result.getString("password"))
                        .build();
            }

            result.close();
            System.out.println("[successful]");
        } catch (SQLException var22) {
            System.out.println("[failed]");
            var22.printStackTrace();
            client = null;
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException var20) {
                    var20.printStackTrace();
                }
            }
            try {
                DatabaseConnection.getConnection().close();
            } catch (SQLException var19) {
                var19.printStackTrace();
            }
        }

        return client;
    }

    @Override
    public Long checkLoginAndPassword(String login, String password) throws RemoteException {
        try {
            System.out.print("\nClient " + getClientHost() + " request Client.checkLoginAndPassword() method...");
        } catch (ServerNotActiveException var20) {
            var20.printStackTrace();
        }

        PreparedStatement statement = null;
        String sql = "select idClient from clients where username = ? and password = ?";

        String list;
        try {
            statement = DatabaseConnection.getConnection().prepareStatement(sql);
            statement.setString(1, login);
            statement.setString(2, password);
            ResultSet result = statement.executeQuery();
            list = new String();

            if (result.next()) {
                list = result.getString(1);
            }
            result.close();
            System.out.println("[successful]");
        } catch (SQLException e) {
            System.out.println("[failed]");
            e.printStackTrace();
            list = null;
        }finally {
            if(statement != null){
                try {
                    statement.close();
                }catch (SQLException ex){
                    ex.printStackTrace();
                }
            }
            try{
                DatabaseConnection.getConnection().close();
            }catch (SQLException var){
                var.printStackTrace();
            }
        }

        if (list.isEmpty() || list == null)
            return null;
        else
            return Long.parseLong(list);
    }
}
