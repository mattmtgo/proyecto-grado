package controlador;

import conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import modelo.CabeceraVenta;
import java.sql.Statement;
import java.sql.ResultSet;
import modelo.DetalleVenta;

public class Ctrl_RegistrarVenta {

    public static int idCabeceraRegistrada;
    java.math.BigDecimal iDColVar;

    // Método para guardar la cabecera de venta
    public boolean guardar(CabeceraVenta objeto) {
        boolean respuesta = false;
        Connection cn = Conexion.conectar();
        try {

            PreparedStatement consulta = cn.prepareStatement(
                    "INSERT INTO tb_cabecera_venta VALUES(?,?,?,?,?,?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            consulta.setInt(1, 0);
            consulta.setInt(2, objeto.getIdCliente());
            consulta.setDouble(3, objeto.getValorPagar());
            consulta.setString(4, objeto.getFechaventa());
            consulta.setInt(5, objeto.getEstado());
            consulta.setString(6, objeto.getTipoPago());

            if (consulta.executeUpdate() > 0) {
                respuesta = true;
            }

            ResultSet rs = consulta.getGeneratedKeys();
            while (rs.next()) {
                iDColVar = rs.getBigDecimal(1);
                idCabeceraRegistrada = iDColVar.intValue();
            }

            cn.close();

        } catch (SQLException e) {
            System.out.println("Error al guardar cabecera de venta: " + e);
        }

        return respuesta;
    }

    // Método para guardar detalle de venta
    public boolean guardarDetalle(DetalleVenta objeto) {
        boolean respuesta = false;
        Connection cn = Conexion.conectar();
        try {

            PreparedStatement consulta = cn.prepareStatement(
                    "INSERT INTO tb_detalle_venta VALUES(?,?,?,?,?,?,?,?,?,?)"
            );
            consulta.setInt(1, 0);
            consulta.setInt(2, idCabeceraRegistrada);
            consulta.setInt(3, objeto.getIdProducto());
            consulta.setInt(4, objeto.getCantidad());
            consulta.setDouble(5, objeto.getPrecioUnitario());
            consulta.setDouble(6, objeto.getSubTotal());
            consulta.setDouble(7, objeto.getDescuento());
            consulta.setDouble(8, objeto.getIva());
            consulta.setDouble(9, objeto.getTotalPagar());
            consulta.setInt(10, objeto.getEstado());

            if (consulta.executeUpdate() > 0) {
                respuesta = true;
            }

            cn.close();

        } catch (SQLException e) {
            System.out.println("Error al guardar detalle de venta: " + e);
        }

        return respuesta;
    }

    // Método para actualizar cabecera de venta
    public boolean actualizar(CabeceraVenta objeto, int idCabeceraVenta) {
        boolean respuesta = false;
        Connection cn = Conexion.conectar();
        try {

            PreparedStatement consulta = cn.prepareStatement(
                    "UPDATE tb_cabecera_venta SET idCliente = ?, estado = ? WHERE idCabeceraVenta = ?"
            );
            consulta.setInt(1, objeto.getIdCliente());
            consulta.setInt(2, objeto.getEstado());
            consulta.setInt(3, idCabeceraVenta);

            if (consulta.executeUpdate() > 0) {
                respuesta = true;
            }

            cn.close();
        } catch (SQLException e) {
            System.out.println("Error al actualizar cabecera de venta: " + e);
        }

        return respuesta;
    }

    //Método para eliminar una venta
    public boolean eliminar(int idVenta) {
        boolean respuesta = false;
        Connection cn = Conexion.conectar();
        try {
            //eliminamos los detalles de la venta
            PreparedStatement pst1 = cn.prepareStatement(
                    "DELETE FROM tb_detalle_venta WHERE idCabeceraVenta = ?"
            );
            pst1.setInt(1, idVenta);
            pst1.executeUpdate();
            pst1.close();

            //eliminamos la cabecera de la venta
            PreparedStatement pst2 = cn.prepareStatement(
                    "DELETE FROM tb_cabecera_venta WHERE idCabeceraVenta = ?"
            );
            pst2.setInt(1, idVenta);
            int filas = pst2.executeUpdate();
            pst2.close();

            if (filas > 0) {
                respuesta = true;
            }

            cn.close();
        } catch (SQLException e) {
            System.out.println("Error al eliminar venta: " + e);
        }
        return respuesta;
    }
}
