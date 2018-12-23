package server.service;

import server.utilities.DatabaseConnection;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface ServiceImpl {

    default void delete(String sqlString, Long idDeleteColumn){

        PreparedStatement statement = null;
        String sql = sqlString + "?";

        try {
            statement = DatabaseConnection.getConnection().prepareStatement(sql);
            statement.setLong(1, idDeleteColumn);
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
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
