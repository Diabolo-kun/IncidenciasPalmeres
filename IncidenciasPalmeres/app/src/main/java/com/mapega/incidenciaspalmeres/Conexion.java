package com.mapega.incidenciaspalmeres;

import android.text.TextUtils;

import com.mapega.incidenciaspalmeres.ObjectClass.Aviso;
import com.mapega.incidenciaspalmeres.ObjectClass.IncidenciaAlmacen;
import com.mapega.incidenciaspalmeres.ObjectClass.IncidenciaMantenimiento;
import com.mapega.incidenciaspalmeres.ObjectClass.Usuario;

import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Timestamp;
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
    public static Usuario getUser(String gmail) {
        Usuario usuario = null; // Valor predeterminado si no se encuentra el usuario

        Connection conn = null;
        try {
            conn = getConnection();
            // Crea una sentencia SQL para buscar el usuario por su email
            String sql = "SELECT * FROM usuarios WHERE gmail = ?";
            PreparedStatement sentencia = conn.prepareStatement(sql);
            sentencia.setString(1, gmail);

            // Ejecuta la sentencia y obtiene el resultado
            ResultSet resultado = sentencia.executeQuery();
            if (resultado.next()) {
                // Si se encuentra el usuario, crea una instancia de Usuario con sus datos
                usuario = new Usuario();
                usuario.setId(resultado.getInt("id"));
                usuario.setNombre(resultado.getString("nombre"));
                usuario.setGmail(resultado.getString("gmail"));
                usuario.setDni(resultado.getString("dni"));
                usuario.setNumero_telefono(resultado.getInt("numero_telefono"));
                usuario.setContrasena(resultado.getString("contrasena"));
                usuario.setPuesto(resultado.getString("puesto"));
                usuario.setDescripcion(resultado.getString("descripcion"));
                usuario.setTipo_permiso(resultado.getInt("tipo_permiso"));
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

        return usuario;
    }
    public static Usuario getUserId(int id) {
        Usuario usuario = null; // Valor predeterminado si no se encuentra el usuario

        Connection conn = null;
        try {
            conn = getConnection();
            // Crea una sentencia SQL para buscar el usuario por su email
            String sql = "SELECT * FROM usuarios WHERE id = ?";
            PreparedStatement sentencia = conn.prepareStatement(sql);
            sentencia.setInt(1, id);

            // Ejecuta la sentencia y obtiene el resultado
            ResultSet resultado = sentencia.executeQuery();
            if (resultado.next()) {
                // Si se encuentra el usuario, crea una instancia de Usuario con sus datos
                usuario = new Usuario();
                usuario.setId(resultado.getInt("id"));
                usuario.setNombre(resultado.getString("nombre"));
                usuario.setGmail(resultado.getString("gmail"));
                usuario.setDni(resultado.getString("dni"));
                usuario.setNumero_telefono(resultado.getInt("numero_telefono"));
                usuario.setContrasena(resultado.getString("contrasena"));
                usuario.setPuesto(resultado.getString("puesto"));
                usuario.setDescripcion(resultado.getString("descripcion"));
                usuario.setTipo_permiso(resultado.getInt("tipo_permiso"));
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

        return usuario;
    }
    public static void updateProfile(String nuevo_nombre, String nuevo_gmail, String nuevo_tel, String nuevo_pass, int id){
        try (Connection connection = getConnection()) {
            // Crear la consulta SQL para actualizar el estado de la incidencia
            String sql = "UPDATE usuarios SET ";
            String comma = "";
            if (!TextUtils.isEmpty(nuevo_nombre)) {
                sql += comma + "nombre = ?";
                comma = ", ";
            }
            if (!TextUtils.isEmpty(nuevo_gmail)) {
                sql += comma + "gmail = ?";
                comma = ", ";
            }
            if (!TextUtils.isEmpty(nuevo_tel)) {
                sql += comma + "numero_telefono = ?";
                comma = ", ";
            }
            if (!TextUtils.isEmpty(nuevo_pass)) {
                sql += comma + "contrasena = ?";
                comma = ", ";
            }
            sql += " WHERE id = ?";

            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                // Establecer los parámetros de la consulta
                int count=1;
                if (!TextUtils.isEmpty(nuevo_nombre)) {
                    statement.setString(count,nuevo_nombre);
                    count++;
                }
                if (!TextUtils.isEmpty(nuevo_gmail)) {
                    statement.setString(count,nuevo_gmail);
                    count++;
                }
                if (!TextUtils.isEmpty(nuevo_tel)) {
                    statement.setString(count,nuevo_tel);
                    count++;
                }
                if (!TextUtils.isEmpty(nuevo_pass)) {
                    statement.setString(count,nuevo_pass);
                    count++;
                }
                statement.setString(count, String.valueOf(id));
                // Ejecutar la consulta
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            // Manejar la excepción
            e.printStackTrace();
        }
    }
    public static boolean addMantenimiento(String titulo, int idUsuario, String descripcion){

        // Verificar que los campos requeridos no están vacíos
        if (TextUtils.isEmpty(titulo) || idUsuario == -1) {
            return false;
        }
        // Establece una conexión con la base de datos
        Connection conexion = null;
        try {
            conexion = getConnection();

            // Crea una sentencia SQL para insertar la incidencia
            String sql = "INSERT INTO incidencias_mantenimiento (titulo, id_usuario_creador, descripcion) VALUES (?, ?, ?)";
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
    public static boolean addAlmacen(String producto, int cantidad, int idUsuario, String descripcion){

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
    public static List<Aviso> getAvisosList(){
        List<Aviso> avisos = new ArrayList<>();
        Connection conn = null;
        try {
            conn = getConnection();

            String sql = "SELECT * FROM avisos ORDER BY nivel_prioridad , fecha_creacion DESC;";

            // Ejecutamos la consulta y obtenemos los resultados
            try (PreparedStatement ps = conn.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    int idUsuario = rs.getInt("id_usuario_creador");
                    String titulo = rs.getString("titulo");
                    String descripcion = rs.getString("descripcion");
                    int nivel_prioridad = rs.getInt("nivel_prioridad");
                    boolean visible = rs.getBoolean("visible_status");
                    Date fechaCreacion = new Date(rs.getDate("fecha_creacion").getTime());

                    Aviso aviso = new Aviso(id, titulo, fechaCreacion, idUsuario, descripcion, nivel_prioridad, visible);

                    avisos.add(aviso);
                }

            }
            conn.close();

            return avisos;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static List<IncidenciaMantenimiento> getMantenimentoList(){
        List<IncidenciaMantenimiento> resultados = new ArrayList<>();
        // Establece una conexión con la base de datos
        Connection conn = null;
        try {
            conn = getConnection();

            String sql = "SELECT * FROM incidencias_mantenimiento ORDER BY done , nivel_prioridad;";

            // Ejecutamos la consulta y obtenemos los resultados
            try (PreparedStatement ps = conn.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    int idUsuario = rs.getInt("id_usuario_creador");
                    String titulo = rs.getString("titulo");
                    String descripcion = rs.getString("descripcion");
                    boolean done = rs.getBoolean("done");
                    Date fechaCreacion = rs.getDate("fecha_creacion");
                    Date fechaFinalizacion = rs.getDate("fecha_finalizacion");
                    int nivelPrioridad = rs.getInt("nivel_prioridad");

                    IncidenciaMantenimiento incidencia = new IncidenciaMantenimiento(id, idUsuario, titulo, descripcion, done, fechaCreacion, fechaFinalizacion, nivelPrioridad);

                    resultados.add(incidencia);
                }
            }
            conn.close();

            return resultados;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public static List<IncidenciaAlmacen> getAlmacenList() {
        List<IncidenciaAlmacen> resultado = new ArrayList<>();
        Connection conn = null;
        try {
            conn = getConnection();

            String sql = "SELECT * FROM incidencias_almacen ORDER BY pedido , nivel_prioridad;";

            // Ejecutamos la consulta y obtenemos los resultados
            try (PreparedStatement ps = conn.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    int idUsuario = rs.getInt("id_usuario_creador");
                    String producto = rs.getString("producto");
                    int cantidad = rs.getInt("cantidad");
                    String descripcion = rs.getString("descripcion");
                    boolean pedido = rs.getBoolean("pedido");
                    Date fechaCreacion = rs.getDate("fecha_creacion");
                    Date fechaFinalizacion = rs.getDate("fecha_finalizacion");
                    int nivelPrioridad = rs.getInt("nivel_prioridad");

                    IncidenciaAlmacen incidencia = new IncidenciaAlmacen(id, idUsuario, producto, cantidad, descripcion, pedido, fechaCreacion, fechaFinalizacion, nivelPrioridad);

                    resultado.add(incidencia);
                }
            }
            conn.close();

            return resultado;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void done(int objetoId, boolean estadoCheckBox) {
        try (Connection connection = getConnection()) {
            // Crear la consulta SQL para actualizar el estado de la incidencia
            String sql = "UPDATE incidencias_mantenimiento SET done = ?";
            if (estadoCheckBox) {
                sql += ", fecha_finalizacion = ?";
            }else {
                sql += ", fecha_finalizacion = NULL";
            }
            sql += " WHERE id = ?";

            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                // Establecer los parámetros de la consulta
                statement.setBoolean(1, estadoCheckBox);
                if (estadoCheckBox) {
                    // Obtener la fecha actual con hora, minutos y segundos
                    java.util.Date date = new java.util.Date();
                    Timestamp timestamp = new Timestamp(date.getTime());

                    statement.setTimestamp(2, timestamp);
                    statement.setInt(3, objetoId);
                } else {
                    statement.setInt(2, objetoId);
                }

                // Ejecutar la consulta
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            // Manejar la excepción
            e.printStackTrace();
        }
    }
    public static void pedido(int objetoId, boolean estadoCheckBox) {
        try (Connection connection = getConnection()) {
            // Crear la consulta SQL para actualizar el estado de la incidencia
            String sql = "UPDATE incidencias_almacen SET pedido = ?";
            if (estadoCheckBox) {
                sql += ", fecha_finalizacion = ?";
            }else {
                sql += ", fecha_finalizacion = NULL";
            }
            sql += " WHERE id = ?";

            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                // Establecer los parámetros de la consulta
                statement.setBoolean(1, estadoCheckBox);
                if (estadoCheckBox) {
                    // Obtener la fecha actual con hora, minutos y segundos
                    java.util.Date date = new java.util.Date();
                    Timestamp timestamp = new Timestamp(date.getTime());

                    statement.setTimestamp(2, timestamp);
                    statement.setInt(3, objetoId);
                } else {
                    statement.setInt(2, objetoId);
                }

                // Ejecutar la consulta
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            // Manejar la excepción
            e.printStackTrace();
        }
    }
    public static boolean addError(int idUsuario, String informe){
        // Verificar que los campos requeridos no están vacíos
        if (TextUtils.isEmpty(informe)) {
            return false;
        }
        // Establece una conexión con la base de datos
        Connection conn = null;
        try {
            conn = getConnection();

            // Crea una sentencia SQL para insertar la incidencia de almacen
            String sql = "INSERT INTO errores (id_usuario_creador, texto_error) VALUES (?, ?)";
            PreparedStatement sentencia = conn.prepareStatement(sql);
            sentencia.setInt(1, idUsuario);
            sentencia.setString(2, informe);

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
}
