package com.mapega.incidenciaspalmeres;

import android.text.TextUtils;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;

public class Conexion {

    private static final String URL = "jdbc:mysql://incidenciaspalmeres.cxodsrdet67p.eu-west-3.rds.amazonaws.com:3306/palmeres";
    private static final String USER = "admin";
    private static final String PASSWORD = "601315473Mpg";
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
    public static boolean testConnection() {
        try (Connection conn = getConnection()) {
            if (conn != null) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public static boolean login(String email, String password) {
        try {
            // Conectarse a la base de datos
            java.sql.Connection conn = Conexion.getConnection();

            // Ejecutar la consulta de login
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM usuarios WHERE gmail = ? AND contrasena = ?");
            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            // Verificar si el usuario existe
            boolean result = rs.next();

            // Cerrar la conexión y los recursos
            rs.close();
            stmt.close();
            conn.close();

            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public static boolean addRequest(String gmail, String dni, String description) {
        // Verificar que los campos requeridos no están vacíos
        if (TextUtils.isEmpty(gmail) || TextUtils.isEmpty(dni) || TextUtils.isEmpty(description)) {
            return false;
        }

        // Crear la conexión a la base de datos
        try (Connection conn = getConnection()) {

            // Crear la sentencia SQL para la inserción
            String sql = "INSERT INTO solicitudes (gmail, dni, descripcion) VALUES (?, ?, ?)";

            // Crear el objeto PreparedStatement con la sentencia SQL
            PreparedStatement stmt = conn.prepareStatement(sql);

            // Establecer los valores de los parámetros
            stmt.setString(1, gmail);
            stmt.setString(2, dni);
            stmt.setString(3, description);

            stmt.executeUpdate();
            return true; // Operación exitosa
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Error al insertar en la base de datos
        }
    }
}
