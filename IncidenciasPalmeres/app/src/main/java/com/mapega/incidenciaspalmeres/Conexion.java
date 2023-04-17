package com.mapega.incidenciaspalmeres;

import android.text.TextUtils;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

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

            // Cerrar la conexión y los recursos
            stmt.close();
            conn.close();
            return true; // Operación exitosa
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Error al insertar en la base de datos
        }
    }
    public static int getUserPermission(String gmail) {
        int permisos = 0;
        try (Connection conn = getConnection()) {
            String query = "SELECT tipo_permiso FROM usuarios WHERE gmail=?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, gmail);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        permisos = rs.getInt("tipo_permiso");
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return permisos;
    }
    public static int getUserId(String gmail){
        int id = -1; // Valor predeterminado si no se encuentra el usuario

        Connection conn = null;
        try {
            conn = getConnection();
            // Crea una sentencia SQL para buscar el usuario por su email
            String sql = "SELECT id FROM usuarios WHERE gmail = ?";
            PreparedStatement sentencia = conn.prepareStatement(sql);
            sentencia.setString(1, gmail);

            // Ejecuta la sentencia y obtiene el resultado
            ResultSet resultado = sentencia.executeQuery();
            if (resultado.next()) {
                // Si se encuentra el usuario, obtiene su ID
                id = resultado.getInt("id");
            }
        } catch (SQLException e) {
            // Maneja la excepción SQL
            System.out.println("Error al buscar el usuario por email: " + e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return id;
    }
    public static int getUserGmail(int id){
        String gmail = null; // Valor predeterminado si no se encuentra el usuario

        Connection conn = null;
        try {
            conn = getConnection();
            // Crea una sentencia SQL para buscar el usuario por su id
            String sql = "SELECT gmail FROM usuarios WHERE id = ?";
            PreparedStatement sentencia = conn.prepareStatement(sql);
            sentencia.setString(1, String.valueOf(id));

            // Ejecuta la sentencia y obtiene el resultado
            ResultSet resultado = sentencia.executeQuery();
            if (resultado.next()) {
                // Si se encuentra el usuario, obtiene su gmail
                gmail = resultado.getString("gmail");
            }
        } catch (SQLException e) {
            // Maneja la excepción SQL
            System.out.println("Error al buscar el usuario por id: " + e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return id;
    }
    public static boolean addInciMantenimiento(String titulo, int idUsuario, String descripcion){

        // Verificar que los campos requeridos no están vacíos
        if (TextUtils.isEmpty(titulo) || idUsuario == -1) {
            return false;
        }
        // Establece una conexión con la base de datos
        Connection conexion = null;
        try {
            conexion = getConnection();

            // Crea una sentencia SQL para insertar la incidencia
            String sql = "INSERT INTO incidencias (titulo, id_usuario_creador, descripcion) VALUES (?, ?, ?)";
            PreparedStatement sentencia = conexion.prepareStatement(sql);
            sentencia.setString(1, titulo);
            sentencia.setInt(2, idUsuario);
            sentencia.setString(3, descripcion);

            // Ejecuta la sentencia
            sentencia.executeUpdate();
            return true; // Operación exitosa
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Error al insertar en la base de datos
        }finally {
            if (conexion != null) {
                try {
                    conexion.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public static boolean addInciAlmacen(String producto, int cantidad, int idUsuario, String descripcion){

        // Verificar que los campos requeridos no están vacíos
        if (idUsuario == -1) {
            return false;
        } else if (TextUtils.isEmpty(producto) || cantidad <= 0) {
            return false;
        }

        // Establece una conexión con la base de datos
        Connection conn = null;
        try {
            conn = getConnection();

            // Crea una sentencia SQL para insertar la incidencia de almacen
            String sql = "INSERT INTO incidencias_almacen (producto, cantidad, id_usuario_creador, descripcion) VALUES (?, ?, ?, ?)";
            PreparedStatement sentencia = conn.prepareStatement(sql);
            sentencia.setString(1, producto);
            sentencia.setInt(2, cantidad);
            sentencia.setInt(3, idUsuario);
            sentencia.setString(4, descripcion);

            // Ejecuta la sentencia
            sentencia.executeUpdate();
            return true; // Operación exitosa
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Error al insertar en la base de datos
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public static List<IncidenciaMantenimiento> selectInciMantenimento(){
        List<IncidenciaMantenimiento> resultados = new ArrayList<>();
        // Establece una conexión con la base de datos
        Connection conn = null;
        try {
            conn = getConnection();

            String sql = "SELECT * FROM incidencias_mantenimiento";

            // Ejecutamos la consulta y obtenemos los resultados
            try (PreparedStatement ps = conn.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    int idUsuario = rs.getInt("id_usuario_creador");
                    String titulo = rs.getString("titulo");
                    String descripcion = rs.getString("descripcion");
                    boolean resuelta = rs.getBoolean("done");

                    IncidenciaMantenimiento incidencia = new IncidenciaMantenimiento(id, idUsuario, titulo, descripcion, resuelta);

                    resultados.add(incidencia);
                }
            }
            conn.close();

            return resultados;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
