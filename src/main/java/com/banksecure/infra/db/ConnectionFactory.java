package com.banksecure.infra.db;
import java.sql.*;

public class ConnectionFactory {
public Connection getConnection(){
    try {
        return DriverManager.getConnection("jdbc:h2:mem://localhost/test");
    } catch (SQLException e) {
        throw new RuntimeException(e);
       }
    }
}
