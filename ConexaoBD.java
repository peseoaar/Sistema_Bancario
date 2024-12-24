package org.example;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoBD{

    public Connection conectar() {
        // conexao BD
        String url = "jdbc:mysql://localhost:3306/banco";
        String usuario = "root";
        String senha = "06102002";
        try {
            return DriverManager.getConnection(url, usuario, senha);
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }
    }

}


