package server.service;

import api.entity.Furniture;
import api.service.FurnitureService;
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

public class FurnitureServiceImpl extends UnicastRemoteObject implements FurnitureService, ServiceImpl {

    public FurnitureServiceImpl() throws  RemoteException{    }


    @Override
    public Furniture insertFurniture(Furniture furniture) throws RemoteException {
        try {
            System.out.print("\nClient " + getClientHost() + " request Furniture.insertFObject() method...");
        } catch (ServerNotActiveException e) {
            e.printStackTrace();
        }

        PreparedStatement statement = null;
        String sql = "insert into products(idProducts, nameProduct, brand," +
                " quantity, purchasePrice, price, note, nameProvider) values (null, ?, ?, ?, ?, ?, ?, ?)";

        Furniture var5;
        try {
            statement = DatabaseConnection.getConnection().prepareStatement(sql, 1);
            statement.setString(1,furniture.getNameProduct());
            statement.setString(2,furniture.getBrand());
            statement.setLong(3,furniture.getQuantity());
            statement.setFloat(4,furniture.getPurchasePrice());
            statement.setFloat(5,furniture.getPrice());
            statement.setString(6,furniture.getNote());
            statement.setString(7,furniture.getNameProvider());
            statement.executeUpdate();
            ResultSet result = statement.getGeneratedKeys();
            if (result.next()) {
                furniture.setIdProducts(result.getLong(1));
            }
            result.close();
            System.out.println("[successful]");
            var5 = furniture;
        } catch (SQLException e) {
            System.out.println("[failed]");
            e.printStackTrace();
            var5 = null;
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
        return var5;
    }

    @Override
    public List<Furniture> getAllFurniture() throws RemoteException {

        try {
            System.out.print("\nClient " + getClientHost() + " request Furniture.getAllFObject() method...");
        } catch (ServerNotActiveException e) {
            e.printStackTrace();
        }

        Statement statement = null;
        String sql = "select * from products";

        ArrayList list;
        try{
            statement = DatabaseConnection.getConnection().createStatement();
            ResultSet result = statement.executeQuery(sql);
            list = new ArrayList();

            while(result.next()) {
                Furniture furniture = new Furniture();
                furniture.setIdProducts(result.getLong("idProducts"));
                furniture.setNameProduct(result.getString("nameProduct"));
                furniture.setBrand(result.getString("brand"));
                furniture.setQuantity(result.getLong("quantity"));
                furniture.setPurchasePrice(result.getFloat("purchasePrice"));
                furniture.setPrice(result.getFloat("price"));
                furniture.setNote(result.getString("note"));
                furniture.setNameProvider(result.getString("nameProvider"));


                list.add(furniture);
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
    public void deleteFurniture(Long id) throws RemoteException {
        try {
            System.out.print("\nClient " + getClientHost() + " request Furniture.deleteFurniture() method...");
        } catch (ServerNotActiveException e) {
            e.printStackTrace();
        }

        Developer developer = new Developer();
        developer.setService(new FurnitureServiceImpl());
        developer.executeService("delete from products where idProducts = ", id);
    //    delete("delete from products where idProducts = ", id);
    }

    @Override
    public void updateFurniture(Furniture furniture) throws RemoteException {
        try {
            System.out.print("\nClient " + getClientHost() + " request Furniture.updateFurniture() method...");
        } catch (ServerNotActiveException e) {
            e.printStackTrace();
        }

        PreparedStatement statement = null;
        String sql = "update products set nameProduct = ?, brand = ?, quantity = ?, purchasePrice = ?, price = ?," +
                " note = ?, nameProvider = ?  where idProducts = ?";

        try{
            statement = DatabaseConnection.getConnection().prepareStatement(sql);
            statement.setString(1,furniture.getNameProduct());
            statement.setString(2,furniture.getBrand());
            statement.setLong(3,furniture.getQuantity());
            statement.setFloat(4,furniture.getPurchasePrice());
            statement.setFloat(5,furniture.getPrice());
            statement.setString(6,furniture.getNote());
            statement.setString(7,furniture.getNameProvider());
            statement.setLong(8,furniture.getIdProducts());
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
    public void updateQuantityFurniture(Long quantity, Long idFurniture) throws RemoteException {
        try {
            System.out.print("\nClient " + getClientHost() + " request Furniture.updateQuantityFurniture() method...");
        } catch (ServerNotActiveException e) {
            e.printStackTrace();
        }

        PreparedStatement statement = null;
        String sql = "update products set  quantity = ? where idProducts = ?";

        try{
            statement = DatabaseConnection.getConnection().prepareStatement(sql);
            statement.setLong(1,quantity);
            statement.setLong(2,idFurniture);
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
    public Furniture getFurnitureById(Long idFurniture) throws RemoteException {
        try {
            System.out.print("\nClient " + getClientHost() + " request Furniture.getFurnitureById() method...");
        } catch (ServerNotActiveException var21) {
            var21.printStackTrace();
        }

        PreparedStatement statement = null;
        String sql = "select * from products where idProducts = ?";

        Furniture furniture = new Furniture();
        try {
            statement = DatabaseConnection.getConnection().prepareStatement(sql);
            statement.setLong(1, idFurniture);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                furniture.setIdProducts(result.getLong("idProducts"));
                furniture.setNameProduct(result.getString("nameProduct"));
                furniture.setBrand(result.getString("brand"));
                furniture.setQuantity(result.getLong("quantity"));
                furniture.setPurchasePrice(result.getFloat("purchasePrice"));
                furniture.setPrice(result.getFloat("price"));
                furniture.setNote(result.getString("note"));
                furniture.setNameProvider(result.getString("nameProvider"));
            }

            result.close();
            System.out.println("[successful]");
        } catch (SQLException var22) {
            System.out.println("[failed]");
            var22.printStackTrace();
            furniture = null;
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

        return furniture;
    }

    @Override
    public Furniture getFurnitureByName(String nameFurniture) throws RemoteException {
        try {
            System.out.print("\nClient " + getClientHost() + " request Furniture.getFurnitureByName() method...");
        } catch (ServerNotActiveException var21) {
            var21.printStackTrace();
        }

        PreparedStatement statement = null;
        String sql = "select * from products where nameProduct = ?";

        Furniture furniture = new Furniture();
        try {
            statement = DatabaseConnection.getConnection().prepareStatement(sql);
            statement.setString(1, nameFurniture);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                furniture.setIdProducts(result.getLong("idProducts"));
                furniture.setNameProduct(result.getString("nameProduct"));
                furniture.setBrand(result.getString("brand"));
                furniture.setQuantity(result.getLong("quantity"));
                furniture.setPurchasePrice(result.getFloat("purchasePrice"));
                furniture.setPrice(result.getFloat("price"));
                furniture.setNote(result.getString("note"));
                furniture.setNameProvider(result.getString("nameProvider"));
            }

            result.close();
            System.out.println("[successful]");
        } catch (SQLException var22) {
            System.out.println("[failed]");
            var22.printStackTrace();
            furniture = null;
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

        return furniture;
    }
}
