package org.conexion;


import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnexionBD {
    private static final String URL = "jdbc:mysql://localhost:3306/db_factura?serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
    private static Connection connexionBAD;

    public ConnexionBD() {
    }

    public static Connection getConnection() {
        if (connexionBAD == null) {
            try {
                connexionBAD = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_factura?serverTimezone=UTC", "root", "root");
            } catch (SQLException var1) {
                SQLException e = var1;
                throw new RuntimeException("ERROR AL CONECTARSE A LA BASE DE DATOS " + e);
            }
        }

        return connexionBAD;
    }
}