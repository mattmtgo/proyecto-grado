package controlador;

import conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import modelo.Proveedor;

/**
 *
 * @author Mateo
 */
public class Ctrl_Proveedor {

    // 🔹 Registrar nuevo proveedor
    public boolean guardar(Proveedor objeto) {
        boolean respuesta = false;
        Connection cn = Conexion.conectar();

        try {
            PreparedStatement consulta = cn.prepareStatement(
                "INSERT INTO tb_proveedor VALUES (?,?,?,?,?,?,?)"
            );
            consulta.setInt(1, 0); // idProveedor
            consulta.setString(2, objeto.getEmpresa());
            consulta.setString(3, objeto.getNombre());
            consulta.setString(4, objeto.getCedula());
            consulta.setString(5, objeto.getTelefono());
            consulta.setString(6, objeto.getDireccion());
            consulta.setInt(7, objeto.getEstado());

            if (consulta.executeUpdate() > 0) {
                respuesta = true;
                System.out.println("✅ Proveedor guardado correctamente.");
            }

        } catch (Exception e) {
            System.out.println("❌ Error al guardar proveedor: " + e);
        } finally {
            try { cn.close(); } catch (Exception ex) { }
        }

        return respuesta;
    }

    // 🔹 Verificar si el proveedor ya existe (por cédula)
    public boolean existeProveedor(String cedula) {
        boolean respuesta = false;
        String sql = "SELECT cedula FROM tb_proveedor WHERE cedula = ?";
        try (Connection cn = Conexion.conectar(); PreparedStatement pst = cn.prepareStatement(sql)) {
            pst.setString(1, cedula);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                respuesta = true;
            }
        } catch (SQLException e) {
            System.out.println("❌ Error al consultar proveedor: " + e);
        }
        return respuesta;
    }

    // 🔹 Actualizar proveedor
    public boolean actualizar(Proveedor objeto, int idProveedor) {
        boolean respuesta = false;
        Connection cn = Conexion.conectar();
        try {
            PreparedStatement consulta = cn.prepareStatement(
                "UPDATE tb_proveedor SET empresa=?, nombre=?, cedula=?, telefono=?, direccion=?, estado=? WHERE idProveedor=?"
            );
            consulta.setString(1, objeto.getEmpresa());
            consulta.setString(2, objeto.getNombre());
            consulta.setString(3, objeto.getCedula());
            consulta.setString(4, objeto.getTelefono());
            consulta.setString(5, objeto.getDireccion());
            consulta.setInt(6, objeto.getEstado());
            consulta.setInt(7, idProveedor);

            if (consulta.executeUpdate() > 0) {
                respuesta = true;
                System.out.println("✅ Proveedor actualizado correctamente.");
            }
        } catch (SQLException e) {
            System.out.println("❌ Error al actualizar proveedor: " + e);
        } finally {
            try { cn.close(); } catch (Exception ex) { }
        }

        return respuesta;
    }

    // 🔹 Eliminar proveedor
    public boolean eliminar(int idProveedor) {
        boolean respuesta = false;
        String sql = "DELETE FROM tb_proveedor WHERE idProveedor = ?";
        try (Connection cn = Conexion.conectar(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, idProveedor);
            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas > 0) {
                respuesta = true;
                System.out.println("✅ Proveedor eliminado correctamente.");
            }

        } catch (SQLException e) {
            System.out.println("❌ Error al eliminar proveedor: " + e);
        }

        return respuesta;
    }

    // 🔹 Verificar si el teléfono ya está registrado
    public boolean existeTelefono(String telefono) {
        boolean existe = false;
        String sql = "SELECT telefono FROM tb_proveedor WHERE telefono = ?";
        try (Connection cn = Conexion.conectar(); PreparedStatement pst = cn.prepareStatement(sql)) {
            pst.setString(1, telefono);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                existe = true;
            }
        } catch (SQLException e) {
            System.out.println("❌ Error al verificar teléfono del proveedor: " + e);
        }
        return existe;
    }
}