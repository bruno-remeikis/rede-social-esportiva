package com.faesa.app.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class OracleConnector
{
    private static final String
        USER = "system",
        PASSWORD = "oracle",
        HOST = "localhost",
        PORT = "1521",
        SID = "XE",
        //URL = "jdbc:oracle:thin:" + USER + '/' + PASSWORD + '@' + HOST + ':' + PORT + ':' + DB_NAME;
        URL = "jdbc:oracle:thin:@" + HOST + ':' + PORT + ':' + SID;
    
    private static Connection connection = null;
    
    static {
        try {
            //Class.forName("oracle.jdbc.driver.OracleDriver");
            Class.forName("oracle.jdbc.OracleDriver");
        }
        catch(ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Erro ao configurar driver Oracle: " + e.getMessage());
        }
    }
    
    public static Connection getConnection()
    {
        try {
            if(connection == null || connection.isClosed())
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
        }
        catch(SQLException e) {
            System.out.println("Erro ao tentar conexão com o Banco de Dados: " + e.getMessage());
        }
        
        return connection;
    }
}
