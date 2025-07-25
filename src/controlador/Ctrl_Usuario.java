package controlador;

import java.sql.Connection;
import modelo.Usuario;
import conexion.Conexion;
import java.sql.Statement;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import java.sql.ResultSet;

/**
 *
 * @author Aide
 */
public class Ctrl_Usuario {

    //metodo para iniciar sesión 
    public boolean loginUser(Usuario objeto) {

        boolean respuesta = false;

        Connection cn = Conexion.conectar();
        String sql = "select usuario, password from tb_usuario where usuario = '" + objeto.getUsuario() + "' and password = '" + objeto.getPassword() + "'";
        Statement st;
        try {

            st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                respuesta = true;
            }

        } catch (SQLException e) {
            System.out.println("Error al Iniciar Sesión");
            JOptionPane.showMessageDialog(null, "Error al Iniciar Sesión");

        }

        return respuesta;
    }
}
