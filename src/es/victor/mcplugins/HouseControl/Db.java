package es.victor.mcplugins.HouseControl;

import java.sql.*;
import java.util.Arrays;

public class Db {
    final String username = "mcuser";
    final String password = "maincra";
    final String database = "HouseControl";
    final String url = "jdbc:mysql://127.0.0.1";
    static Connection connection;

    public Db() throws DbException {
        this.open();
    }

    public void open() throws DbException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new DbException("JDBC driver error!\n" + Arrays.toString(e.getStackTrace()));
        }
        String sql = "USE " + database + ";";
        try {
            connection = DriverManager.getConnection(url,username,password);
        } catch (SQLException e) {
            throw new DbException("SQL error!\n" + e.getMessage());
        }

        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.executeQuery();
        } catch (Exception e) {
            throw new DbException("SQL error!\n" + e.getMessage());
        }
    }

    public Connection getConnection() {
        return connection;
    }
}