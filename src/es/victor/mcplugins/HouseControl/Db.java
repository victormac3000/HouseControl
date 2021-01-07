package es.victor.mcplugins.HouseControl;

import java.sql.*;

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
            throw new DbException("JDBC driver error!\n" + e.getStackTrace());
        }
        String sql = "USE " + database + ";";
        try {
            this.connection = DriverManager.getConnection(url,username,password);
        } catch (SQLException e) {
            throw new DbException("SQL error!\n" + e.getMessage());
        }

        try {
            PreparedStatement stmt = this.connection.prepareStatement(sql);
            ResultSet results = stmt.executeQuery();
        } catch (Exception e) {
            throw new DbException("SQL error!\n" + e.getMessage());
        }
    }

    public void close() {
        try {
            if (connection!=null && !connection.isClosed()) {
                connection.close();
            }
        } catch(Exception e) {
            System.out.println("Error closing the connection");
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return this.connection;
    }
}