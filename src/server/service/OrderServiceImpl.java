package server.service;


import api.entity.Order;
import server.utilities.DatabaseConnection;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.ServerNotActiveException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class OrderServiceImpl extends UnicastRemoteObject implements api.service.OrderService, ServiceImpl {
    public OrderServiceImpl() throws RemoteException { }

    @Override
    public Order insertOrder(Order order) throws RemoteException {
        try {
            System.out.print("\nClient " + getClientHost() + " request Order.insertOrder() method...");
        } catch (ServerNotActiveException e) {
            e.printStackTrace();
        }

        PreparedStatement statement = null;
        String sql = "insert into orders(idOrder, nameFurniture, noteFurniture," +
                " quantityFurniture, priceFurniture, priceDelivery, idClient, idStaff, dateOrder)" +
                " values (null, ?, ?, ?, ?, ?, ?, ?, ?)";

        Order var5;
        try {
            statement = DatabaseConnection.getConnection().prepareStatement(sql, 1);
            statement.setString(1,order.getNameFurniture());
            statement.setString(2,order.getNoteFurniture());
            statement.setLong(3,order.getQuantityFurniture());
            statement.setFloat(4,order.getPriceFurniture());
            statement.setFloat(5,order.getPriceDelivery());
            statement.setLong(6,order.getIdClient());
            statement.setLong(7,order.getIdStaff());
            statement.setDate(8, order.getDateOrder());

            statement.executeUpdate();
            ResultSet result = statement.getGeneratedKeys();
            if (result.next()) {
                order.setIdOrder(result.getLong(1));
            }
            result.close();
            System.out.println("[successful]");
            var5 = order;
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
    public Order insertOrderWithoutClient(Order order) throws RemoteException {
        try {
            System.out.print("\nClient " + getClientHost() + " request Order.insertOrderWithoutClient() method...");
        } catch (ServerNotActiveException e) {
            e.printStackTrace();
        }

        PreparedStatement statement = null;
        String sql = "insert into orders(idOrder, nameFurniture, noteFurniture," +
                " quantityFurniture, priceFurniture, priceDelivery, idClient, idStaff, dateOrder)" +
                " values (null, ?, ?, ?, ?, ?, null, ?, ?)";

        Order var5;
        try {
            statement = DatabaseConnection.getConnection().prepareStatement(sql, 1);
            statement.setString(1,order.getNameFurniture());
            statement.setString(2,order.getNoteFurniture());
            statement.setLong(3,order.getQuantityFurniture());
            statement.setFloat(4,order.getPriceFurniture());
            statement.setFloat(5,order.getPriceDelivery());
            statement.setLong(6,order.getIdStaff());
            statement.setDate(7, order.getDateOrder());
            statement.executeUpdate();
            ResultSet result = statement.getGeneratedKeys();
            if (result.next()) {
                order.setIdOrder(result.getLong(1));
            }
            result.close();
            System.out.println("[successful]");
            var5 = order;
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
    public Order insertOrderFromClient(Order order) throws RemoteException {
        try {
            System.out.print("\nClient " + getClientHost() + " request Order.insertOrderFromClient() method...");
        } catch (ServerNotActiveException e) {
            e.printStackTrace();
        }

        PreparedStatement statement = null;
        String sql = "insert into orders(idOrder, nameFurniture, noteFurniture," +
                " quantityFurniture, priceFurniture, priceDelivery, idClient, idStaff, dateOrder)" +
                " values (null, ?, ?, ?, ?, null, ?, null, null)";

        Order var5;
        try {
            statement = DatabaseConnection.getConnection().prepareStatement(sql, 1);
            statement.setString(1,order.getNameFurniture());
            statement.setString(2,order.getNoteFurniture());
            statement.setLong(3,order.getQuantityFurniture());
            statement.setFloat(4,order.getPriceFurniture());
            statement.setFloat(5,order.getIdClient());
            statement.executeUpdate();
            ResultSet result = statement.getGeneratedKeys();
            if (result.next()) {
                order.setIdOrder(result.getLong(1));
            }
            result.close();
            System.out.println("[successful]");
            var5 = order;
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
    public List<Order> getAllUnActiveOrder() throws RemoteException {

        try {
            System.out.print("\nClient " + getClientHost() + " Order.request getAllOrder() method...");
        } catch (ServerNotActiveException e) {
            e.printStackTrace();
        }

        Statement statement = null;
        String sql = "select * from orders where dateOrder != 'null'";

        ArrayList list;
        try{
            statement = DatabaseConnection.getConnection().createStatement();
            ResultSet result = statement.executeQuery(sql);
            list = new ArrayList();

            while(result.next()) {
                Order order = new Order();
                order.setIdOrder(result.getLong("idOrder"));
                order.setNameFurniture(result.getString("nameFurniture"));
                order.setNoteFurniture(result.getString("noteFurniture"));
                order.setQuantityFurniture(result.getLong("quantityFurniture"));
                order.setPriceFurniture(result.getFloat("priceFurniture"));
                order.setPriceDelivery(result.getFloat("priceDelivery"));
                order.setIdClient(result.getLong("idClient"));
                order.setIdStaff(result.getLong("idStaff"));
                order.setDateOrder(result.getDate("dateOrder"));

                list.add(order);
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
    public List<Order> getAllActiveOrder() throws RemoteException {

        try {
            System.out.print("\nClient " + getClientHost() + " request Order.getAllActiveOrder() method...");
        } catch (ServerNotActiveException e) {
            e.printStackTrace();
        }

        Statement statement = null;
        String sql = "select * from orders where dateOrder IS NULL";

        ArrayList list;
        try{
            statement = DatabaseConnection.getConnection().createStatement();
            ResultSet result = statement.executeQuery(sql);
            list = new ArrayList();

            while(result.next()) {
                Order order = new Order();
                order.setIdOrder(result.getLong("idOrder"));
                order.setNameFurniture(result.getString("nameFurniture"));
                order.setNoteFurniture(result.getString("noteFurniture"));
                order.setQuantityFurniture(result.getLong("quantityFurniture"));
                order.setPriceFurniture(result.getFloat("priceFurniture"));
                order.setPriceDelivery(result.getFloat("priceDelivery"));
                order.setIdClient(result.getLong("idClient"));
                order.setIdStaff(result.getLong("idStaff"));
                order.setDateOrder(result.getDate("dateOrder"));

                list.add(order);
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
    public void deleteOrder(Long id) throws RemoteException {
        try {
            System.out.print("\nClient " + getClientHost() + " request Order.deleteOrder() method...");
        } catch (ServerNotActiveException e) {
            e.printStackTrace();
        }
        delete("delete from orders where idOrder = ", id);
    }

    @Override
    public void updateOrder(Order order) throws RemoteException {
        try {
            System.out.print("\nClient " + getClientHost() + " request Order.updateOrder() method...");
        } catch (ServerNotActiveException e) {
            e.printStackTrace();
        }

        PreparedStatement statement = null;
        String sql = "update orders set nameFurniture = ?, noteFurniture = ?, quantityFurniture = ?, priceFurniture = ?, priceDelivery = ?," +
                " idClient = ?, idStaff = ?, dateOrder = ?  where idOrder = ?";

        try{
            statement = DatabaseConnection.getConnection().prepareStatement(sql);
            statement.setString(1,order.getNameFurniture());
            statement.setString(2,order.getNoteFurniture());
            statement.setLong(3,order.getQuantityFurniture());
            statement.setFloat(4,order.getPriceFurniture());
            statement.setFloat(5,order.getPriceDelivery());
            statement.setLong(6,order.getIdClient());
            statement.setLong(7,order.getIdStaff());
            statement.setDate(8,order.getDateOrder());
            statement.setLong(9,order.getIdOrder());
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
}


