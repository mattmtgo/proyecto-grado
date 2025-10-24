package controlador;

import java.sql.Connection;
import modelo.Usuario;
import conexion.Conexion;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import modelo.Cliente;

/**
 *
 * @author Aide
 */
public class Ctrl_Usuario {

    public boolean guardar(Usuario objeto) {
        boolean respuesta = false;
        Connection cn = Conexion.conectar();

        try {
            //  SHA2 desde MySQL 
            String sql = "INSERT INTO tb_usuario (idUsuario, nombre, apellido, usuario, password, telefono, estado) "
                    + "VALUES (NULL, ?, ?, ?, SHA2(?, 256), ?, ?)";

            PreparedStatement consulta = cn.prepareStatement(sql);
            consulta.setString(1, objeto.getNombre());
            consulta.setString(2, objeto.getApellido());
            consulta.setString(3, objeto.getUsuario());
            consulta.setString(4, objeto.getPassword());
            consulta.setString(5, objeto.getTelefono());
            consulta.setInt(6, objeto.getEstado());

            if (consulta.executeUpdate() > 0) {
                respuesta = true;
                System.out.println(" Usuario guardado correctamente con contraseña encriptada");
            }

            cn.close();

        } catch (SQLException e) {
            System.out.println("❌ Error al guardar Usuario: " + e);
        }

        return respuesta;
    }

    //metodo para consultar si existe la cliente 
    public boolean existeUsuario(String usuario) {
        boolean respuesta = false;
        String sql = "select usuario from tb_usuario where usuario = '" + usuario + "';";
        Statement st;
        try {
            Connection cn = Conexion.conectar();
            st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                respuesta = true;
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar el usuario: " + e);
        }
        return respuesta;
    }

    //metodo para iniciar sesión 
    public boolean loginUser(Usuario objeto) {
        boolean respuesta = false;

        String sql = "SELECT usuario FROM tb_usuario WHERE usuario = ? AND password = SHA2(?, 256)";

        try ( Connection cn = Conexion.conectar();  PreparedStatement ps = cn.prepareStatement(sql)) {

            // Asignamos los valores
            ps.setString(1, objeto.getUsuario());
            ps.setString(2, objeto.getPassword());

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                respuesta = true; // usuario y contraseña correctos
                System.out.println("✅ Inicio de sesión exitoso");
            } else {
                System.out.println("❌ Usuario o contraseña incorrectos");
            }

        } catch (SQLException e) {
            System.out.println("Error al iniciar sesión: " + e);
            JOptionPane.showMessageDialog(null, "Error al iniciar sesión");
        }

        return respuesta;
    }

    public boolean actualizar(Usuario objeto, int idUsuario) {
        boolean respuesta = false;
        Connection cn = Conexion.conectar();
        try {

            PreparedStatement consulta = cn.prepareStatement("update tb_usuario set nombre=?, apellido =?, usuario =?, password =?, telefono =?, estado =? where idUsuario ='" + idUsuario + "'");
            consulta.setString(1, objeto.getNombre());
            consulta.setString(2, objeto.getApellido());
            consulta.setString(3, objeto.getUsuario());
            consulta.setString(4, objeto.getPassword());
            consulta.setString(5, objeto.getTelefono());
            consulta.setInt(6, objeto.getEstado());

            if (consulta.executeUpdate() > 0) {
                respuesta = true;
            }
            cn.close();
        } catch (SQLException e) {
            System.out.println("Error al actualizar usuario: " + e);
        }

        return respuesta;
    }

    public boolean eliminar(int idUsuario) {
        boolean respuesta = false;

        String sql = "DELETE FROM tb_usuario WHERE idUsuario = ?";
        try ( Connection cn = Conexion.conectar();  PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);
            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas > 0) {
                respuesta = true;
                System.out.println("✅ Usuario eliminado correctamente");
            }

        } catch (SQLException e) {
            System.out.println("Error al eliminar usuario: " + e);
        }

        return respuesta;
    }

    public boolean actualizarPasswordPorTelefono(String telefono, String nuevaPassword) {
    boolean respuesta = false;

    String sqlSelect = "SELECT password FROM tb_usuario WHERE telefono = ?";
    String sqlUpdate = "UPDATE tb_usuario SET password = SHA2(?, 256) WHERE telefono = ?";

    try (Connection cn = Conexion.conectar();
         PreparedStatement psSelect = cn.prepareStatement(sqlSelect);
         PreparedStatement psUpdate = cn.prepareStatement(sqlUpdate)) {

        // 1️⃣ Verificamos si el teléfono existe y obtenemos la contraseña actual
        psSelect.setString(1, telefono);
        ResultSet rs = psSelect.executeQuery();

        if (rs.next()) {
            String passwordActualHash = rs.getString("password");

            // 2️⃣ Calculamos el hash SHA2 de la nueva contraseña
            String sqlHash = "SELECT SHA2(?, 256) AS nuevaHash";
            PreparedStatement psHash = cn.prepareStatement(sqlHash);
            psHash.setString(1, nuevaPassword);
            ResultSet rsHash = psHash.executeQuery();

            if (rsHash.next()) {
                String nuevaHash = rsHash.getString("nuevaHash");

                // 3️⃣ Comparamos hashes
                if (nuevaHash.equals(passwordActualHash)) {
                    JOptionPane.showMessageDialog(null,
                            "No puede usar la misma contraseña que tenía antes.\nIngrese una diferente.");
                    return false;
                }
            }

            // 4️⃣ Si es diferente, actualizamos la contraseña
            psUpdate.setString(1, nuevaPassword);
            psUpdate.setString(2, telefono);
            int filasAfectadas = psUpdate.executeUpdate();

            if (filasAfectadas > 0) {
                respuesta = true;
                System.out.println("✅ Contraseña actualizada correctamente");
            }

        } else {
            JOptionPane.showMessageDialog(null, "El teléfono no se encuentra registrado.");
        }

    } catch (SQLException e) {
        System.out.println("Error al actualizar contraseña: " + e);
    }

    return respuesta;
}

}
