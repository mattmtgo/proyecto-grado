package controlador;

import conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import modelo.Cliente;
import modelo.Producto;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

/**
 *
 * @author Aide
 */
public class Ctrl_Cliente {

    //metodo para registrar nuevo cliente
    public boolean guardar(Cliente objeto) {
        boolean respuesta = false;
        Connection cn = Conexion.conectar();

        try {
            // 🔑 Clave secreta (debe tener exactamente 16 caracteres para AES-128)
            String claveSecreta = "ClaveSegura12345";
            String cedulaCifrada = encriptarAES(objeto.getCedula(), claveSecreta);

            PreparedStatement consulta = cn.prepareStatement(
                    "INSERT INTO tb_cliente VALUES (?,?,?,?,?,?,?)"
            );
            consulta.setInt(1, 0);
            consulta.setString(2, objeto.getEmpresa());
            consulta.setString(3, objeto.getNombre());
            consulta.setString(4, cedulaCifrada);
            consulta.setString(5, objeto.getTelefono());
            consulta.setString(6, objeto.getDireccion());
            consulta.setInt(7, objeto.getEstado());

            if (consulta.executeUpdate() > 0) {
                respuesta = true;
                System.out.println("Cliente guardado con cédula cifrada correctamente.");
            }

            cn.close();

        } catch (Exception e) {
            System.out.println("Error al guardar cliente: " + e);
        }

        return respuesta;
    }

    //metodo para consultar si existe la cliente 
    public boolean existeCliente(String cedula) {
        boolean respuesta = false;
        String sql = "select cedula from tb_cliente where cedula = '" + cedula + "';";
        Statement st;
        try {
            Connection cn = Conexion.conectar();
            st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                respuesta = true;
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar el cliente: " + e);
        }
        return respuesta;
    }

    public boolean actualizar(Cliente objeto, int idCLiente) {
        boolean respuesta = false;
        Connection cn = Conexion.conectar();
        try {

            PreparedStatement consulta = cn.prepareStatement("update tb_cliente set empresa=?, nombre =?, cedula =?, telefono =?, direccion =?, estado =? where idCliente ='" + idCLiente + "'");
            consulta.setString(1, objeto.getEmpresa());
            consulta.setString(2, objeto.getNombre());
            consulta.setString(3, objeto.getCedula());
            consulta.setString(4, objeto.getTelefono());
            consulta.setString(5, objeto.getDireccion());
            consulta.setInt(6, objeto.getEstado());

            if (consulta.executeUpdate() > 0) {
                respuesta = true;
            }
            cn.close();
        } catch (SQLException e) {
            System.out.println("Error al actualizar cliente: " + e);
        }

        return respuesta;
    }

    public boolean eliminar(int idCliente) {
        boolean respuesta = false;

        String sql = "DELETE FROM tb_cliente WHERE idCliente = ?";
        try ( Connection cn = Conexion.conectar();  PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, idCliente);
            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas > 0) {
                respuesta = true;
                System.out.println("✅ Cliente eliminado correctamente");
            }

        } catch (SQLException e) {
            System.out.println("Error al eliminar cliente: " + e);
        }

        return respuesta;
    }

    private String encriptarAES(String texto, String clave) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(clave.getBytes("UTF-8"), "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] textoCifrado = cipher.doFinal(texto.getBytes("UTF-8"));
        return Base64.getEncoder().encodeToString(textoCifrado);
    }
}
