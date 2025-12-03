package com.banksecure.infra.db;
import java.sql.*;

public class ConnectionFactory {
public Connection getConnection(){

    try {
        return DriverManager.getConnection("jdbc:h2:~/banksecure_db;AUTO_SERVER=TRUE", "sa", "");
    } catch (SQLException e) {
        throw new RuntimeException(e);
    
       }
   
    }
    
}
