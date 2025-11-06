package controlador;

import conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import modelo.Producto;

/**
 *
 * @author Aide
 */
public class Ctrl_Producto {
    //metodo para registrar nuevo producto

    public boolean guardar(Producto objeto) {
        boolean respuesta = false;
        Connection cn = Conexion.conectar();
        try {
            PreparedStatement consulta = cn.prepareStatement(
                    "INSERT INTO tb_producto VALUES (?,?,?,?,?,?,?,?,?)"
            );
            consulta.setInt(1, 0);
            consulta.setString(2, objeto.getNombre());
            consulta.setInt(3, objeto.getCantidad());
            consulta.setDouble(4, objeto.getPrecio());
            consulta.setString(5, objeto.getDescripcion());
            consulta.setInt(6, objeto.getPorcentajeIva());
            consulta.setInt(7, objeto.getIdCategoria());
            consulta.setInt(8, objeto.getEstado());
            consulta.setNull(9, java.sql.Types.INTEGER); // <-- agregamos el proveedor como NULL

            if (consulta.executeUpdate() > 0) {
                respuesta = true;
            }

            cn.close();

        } catch (SQLException e) {
            System.out.println("Error al guardar producto: " + e);
        }

        return respuesta;
    }

    //metodo para consultar si existe la producto 
    public boolean existeProducto(String producto) {
        boolean respuesta = false;
        String sql = "select nombre from tb_producto where nombre = '" + producto + "';";
        Statement st;

        try {
            Connection cn = Conexion.conectar();
            st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                respuesta = true;
            }

        } catch (SQLException e) {
            System.out.println("Error al consultar el producto: " + e);

        }

        return respuesta;
    }

    public boolean actualizar(Producto objeto, int idProducto) {
        boolean respuesta = false;
        Connection cn = Conexion.conectar();
        try {

            PreparedStatement consulta = cn.prepareStatement("update tb_producto set nombre=?, cantidad =?, precio =?, descripcion =?, porcentajeIva =?, idCategoria =?, estado =? where idProducto ='" + idProducto + "'");
            consulta.setString(1, objeto.getNombre());
            consulta.setInt(2, objeto.getCantidad());
            consulta.setDouble(3, objeto.getPrecio());
            consulta.setString(4, objeto.getDescripcion());
            consulta.setInt(5, objeto.getPorcentajeIva());
            consulta.setInt(6, objeto.getIdCategoria());
            consulta.setInt(7, objeto.getEstado());

            if (consulta.executeUpdate() > 0) {
                respuesta = true;
            }
            cn.close();
        } catch (SQLException e) {
            System.out.println("Error al actualizar producto: " + e);
        }

        return respuesta;
    }

    public boolean eliminar(int idProducto) {
        boolean respuesta = false;

        String sql = "DELETE FROM tb_producto WHERE idProducto = ?";
        try ( Connection cn = Conexion.conectar();  PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, idProducto);
            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas > 0) {
                respuesta = true;
                System.out.println("✅ Producto eliminado correctamente");
            }

        } catch (SQLException e) {
            System.out.println("Error al eliminar producto: " + e);
        }

        return respuesta;
    }

    public boolean actualizarStock(Producto object, int idProducto) {
        boolean respuesta = false;
        Connection cn = Conexion.conectar();
        try {

            PreparedStatement consulta = cn.prepareStatement(
                    "update tb_producto set cantidad =? where idProducto ='" + idProducto + "'");
            consulta.setInt(1, object.getCantidad());

            if (consulta.executeUpdate() > 0) {
                respuesta = true;
            }
            cn.close();
        } catch (SQLException e) {
            System.out.println("Error al actualizar stock del producto: " + e);
        }

        return respuesta;
    }

}
