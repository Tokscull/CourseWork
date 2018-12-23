package server.service;

import api.entity.Staff;
import api.service.StaffService;
import server.utilities.DatabaseConnection;

import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class StaffServiceImpl extends UnicastRemoteObject implements StaffService, ServiceImpl {

    public StaffServiceImpl() throws RemoteException {}

    @Override
    public Staff insertStaff(Staff staff) throws RemoteException {
        try {
            System.out.print("\nClient " + getClientHost() + " request Staff.insertStaff() method...");
        } catch (ServerNotActiveException e) {
            e.printStackTrace();
        }

        PreparedStatement statement = null;
        String sql = "insert into staffs (idStaff, surnameStaff, nameStaff," +
                " positionStaff, birthDayStaff, employmentDayStaff, numberStaff, noteStaff,username,password)" +
                " values (null, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        Staff list;
        try {
            statement = DatabaseConnection.getConnection().prepareStatement(sql, 1);
            statement.setString(1,staff.getSurnameStaff());
            statement.setString(2,staff.getNameStaff());
            statement.setString(3,staff.getPositionStaff());
            statement.setDate(4, Date.valueOf(staff.getBirthDayStaff().toString()));
            statement.setDate(5,Date.valueOf(staff.getEmploymentDayStaff().toString()));
            statement.setString(6,staff.getNumberStaff());
            statement.setString(7,staff.getNoteStaff());
            statement.setString(8,staff.getUsername());
            statement.setString(9,staff.getPassword());
            statement.executeUpdate();
            ResultSet result = statement.getGeneratedKeys();
            if (result.next()) {
                staff.setIdStaff(result.getLong(1));
            }
            result.close();
            System.out.println("[successful]");
            list = staff;
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
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    @Override
    public Staff registrationStaff(Staff staff) throws RemoteException {
        try {
            System.out.print("\nClient " + getClientHost() + " request Staff.registrationStaff() method...");
        } catch (ServerNotActiveException e) {
            e.printStackTrace();
        }

        PreparedStatement statement = null;
        String sql = "insert into staffs (idStaff, surnameStaff, nameStaff," +
                " username,password, birthDayStaff,employmentDayStaff) values (null, ?, ?, ?, ?, ?, ?)";

        Staff list;
        try {
            statement = DatabaseConnection.getConnection().prepareStatement(sql, 1);
            statement.setString(1,staff.getSurnameStaff());
            statement.setString(2,staff.getNameStaff());
            statement.setString(3,staff.getUsername());
            statement.setString(4,staff.getPassword());
            java.util.Date utilDate = new java.util.Date();
            statement.setDate(5, new Date(utilDate.getTime()));
            statement.setDate(6, new Date(utilDate.getTime()));
            statement.executeUpdate();
            ResultSet result = statement.getGeneratedKeys();
            if (result.next()) {
                staff.setIdStaff(result.getLong(1));
            }
            result.close();
            System.out.println("[successful]");
            list = staff;
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
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    @Override
    public List<Staff> getAllStaff() throws RemoteException {
        try {
            System.out.print("\nClient " + getClientHost() + " request Staff.getAllStaff() method...");
        } catch (ServerNotActiveException e) {
            e.printStackTrace();
        }

        Statement statement = null;
        String sql = "select * from staffs";

        ArrayList list;
        try{
            statement = DatabaseConnection.getConnection().createStatement();
            ResultSet result = statement.executeQuery(sql);
            list = new ArrayList();

            while(result.next()) {
                Staff staff = new Staff();
                staff.setIdStaff(result.getLong("idStaff"));
                staff.setSurnameStaff(result.getString("surnameStaff"));
                staff.setNameStaff(result.getString("nameStaff"));
                staff.setPositionStaff(result.getString("positionStaff"));
                staff.setBirthDayStaff(LocalDate.parse(result.getDate("birthDayStaff").toString()));
                staff.setEmploymentDayStaff(LocalDate.parse(result.getDate("employmentDayStaff").toString()));
                staff.setNumberStaff(result.getString("numberStaff"));
                staff.setNoteStaff(result.getString("noteStaff"));
                staff.setUsername(result.getString("username"));
                staff.setPassword(result.getString("password"));
                list.add(staff);
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
    public void deleteStaff(Long id) throws RemoteException {
        try {
            System.out.print("\nClient " + getClientHost() + " request Staff.deleteStaff() method...");
        } catch (ServerNotActiveException e) {
            e.printStackTrace();
        }

        delete("delete from staffs where idStaff = ",id);

    }

    @Override
    public void updateStaff(Staff staff) throws RemoteException {
        try {
            System.out.print("\nClient " + getClientHost() + " request Staff.updateStaff() method...");
        } catch (ServerNotActiveException e) {
            e.printStackTrace();
        }

        PreparedStatement statement = null;
        String sql = "update staffs set surnameStaff = ?, nameStaff = ?, positionStaff = ?, birthDayStaff = ?, employmentDayStaff = ?," +
                " numberStaff = ?, noteStaff = ?, username = ?, password = ?  where idStaff = ?";

        try{
            statement = DatabaseConnection.getConnection().prepareStatement(sql);
            statement.setString(1,staff.getSurnameStaff());
            statement.setString(2,staff.getNameStaff());
            statement.setString(3,staff.getPositionStaff());
            statement.setDate(4,Date.valueOf(staff.getBirthDayStaff().toString()));
            statement.setDate(5,Date.valueOf(staff.getEmploymentDayStaff().toString()));
            statement.setString(6,staff.getNumberStaff());
            statement.setString(7,staff.getNoteStaff());
            statement.setString(8,staff.getUsername());
            statement.setString(9,staff.getPassword());
            statement.setLong(10,staff.getIdStaff());
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
    public Long checkLoginAndPassword(String login, String password) throws RemoteException {
        try {
            System.out.print("\nClient " + getClientHost() + " request Staff.checkLoginAndPassword() method...");
        } catch (ServerNotActiveException var20) {
            var20.printStackTrace();
        }

        PreparedStatement statement = null;
        String sql = "select idStaff from staffs where username = ? and password = ?";

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
