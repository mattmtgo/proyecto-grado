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
}
