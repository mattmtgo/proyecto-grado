package controlador;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import conexion.Conexion;
import java.io.FileOutputStream;
import java.sql.SQLException;
import java.awt.Font;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Aide
 */
public class Reportes {

    public void ReportesClientes() {
        Document documento = new Document();
        try {

            String ruta = System.getProperty("user.home");
            PdfWriter.getInstance(documento, new FileOutputStream(ruta + "/Downloads/Reportes_Clientes.pdf"));
            Image header = Image.getInstance("src/img/Plazayterraza.jpg");
            header.scaleToFit(650, 1000);
            header.setAlignment(Chunk.ALIGN_CENTER);
            Paragraph parrafo = new Paragraph();
            parrafo.setAlignment(Paragraph.ALIGN_CENTER);
            parrafo.add("Reporte Creado por \n Plaza Y Terraza S.A.S \n\n");
            parrafo.setFont(FontFactory.getFont("Times New Roman", 18, Font.BOLD, BaseColor.DARK_GRAY));
            parrafo.add("Reporte de Clientes \n\n");

            documento.open();
            documento.add(header);
            documento.add(parrafo);

            PdfPTable tabla = new PdfPTable(5);
            tabla.addCell("Código");
            tabla.addCell("Nombres");
            tabla.addCell("Cédula");
            tabla.addCell("Teléfono");
            tabla.addCell("Dirección");

            try {
                Connection cn = Conexion.conectar();
                PreparedStatement pst = cn.prepareStatement("select idCliente, concat(empresa,' ',nombre) as nombres, cedula, telefono, direccion from tb_cliente");
                ResultSet rs = pst.executeQuery();
                if (rs.next()) {
                    do {
                        tabla.addCell(rs.getString(1));
                        tabla.addCell(rs.getString(2));
                        tabla.addCell(rs.getString(3));
                        tabla.addCell(rs.getString(4));
                        tabla.addCell(rs.getString(5));
                    } while (rs.next());
                    documento.add(tabla);

                }

            } catch (SQLException e) {
                System.out.println("Error en: " + e);
            }
            documento.close();

            JOptionPane.showMessageDialog(null, "¡Reporte de Clientes Creado Exitosamente!, revisa tus descargas.");
        } catch (DocumentException e) {
            System.out.println("Error1 en: " + e);
        } catch (FileNotFoundException ex) {
            System.out.println("Error2 en: " + ex);
        } catch (IOException ex) {
            System.out.println("Error3 en: " + ex);
        }
    }

    public void ReportesProveedores() {
        Document documento = new Document();
        try {
            // Ruta de guardado
            String ruta = System.getProperty("user.home");
            PdfWriter.getInstance(documento, new FileOutputStream(ruta + "/Downloads/Reportes_Proveedores.pdf"));

            // Encabezado
            Image header = Image.getInstance("src/img/Plazayterraza.jpg");
            header.scaleToFit(650, 1000);
            header.setAlignment(Chunk.ALIGN_CENTER);

            // Título del reporte
            Paragraph parrafo = new Paragraph();
            parrafo.setAlignment(Paragraph.ALIGN_CENTER);
            parrafo.add("Reporte Creado por \n Plaza Y Terraza S.A.S \n\n");
            parrafo.setFont(FontFactory.getFont("Times New Roman", 18, Font.BOLD, BaseColor.DARK_GRAY));
            parrafo.add("Reporte de Proveedores \n\n");

            // Abrir documento
            documento.open();
            documento.add(header);
            documento.add(parrafo);

            // Tabla con columnas
            PdfPTable tabla = new PdfPTable(5);
            tabla.addCell("Código");
            tabla.addCell("Empresa");
            tabla.addCell("Nombre");
            tabla.addCell("Teléfono");
            tabla.addCell("Dirección");

            // Consultar datos desde la base de datos
            try {
                Connection cn = Conexion.conectar();
                PreparedStatement pst = cn.prepareStatement(
                        "SELECT idProveedor, empresa, nombre, telefono, direccion FROM tb_proveedor"
                );
                ResultSet rs = pst.executeQuery();

                if (rs.next()) {
                    do {
                        tabla.addCell(rs.getString("idProveedor"));
                        tabla.addCell(rs.getString("empresa"));
                        tabla.addCell(rs.getString("nombre"));
                        tabla.addCell(rs.getString("telefono"));
                        tabla.addCell(rs.getString("direccion"));
                    } while (rs.next());
                    documento.add(tabla);
                } else {
                    Paragraph sinDatos = new Paragraph("No hay proveedores registrados actualmente.\n\n");
                    sinDatos.setAlignment(Paragraph.ALIGN_CENTER);
                    documento.add(sinDatos);
                }

                cn.close();
            } catch (SQLException e) {
                System.out.println("❌ Error al consultar proveedores: " + e);
            }

            // Cerrar documento
            documento.close();

            JOptionPane.showMessageDialog(null, "✅ ¡Reporte de Proveedores creado exitosamente! Revisa tu carpeta de descargas.");

        } catch (DocumentException e) {
            System.out.println("❌ Error DocumentException: " + e);
        } catch (FileNotFoundException ex) {
            System.out.println("❌ Error FileNotFoundException: " + ex);
        } catch (IOException ex) {
            System.out.println("❌ Error IOException: " + ex);
        }
    }

    public void ReportesProductos() {
        Document documento = new Document();
        try {

            String ruta = System.getProperty("user.home");
            PdfWriter.getInstance(documento, new FileOutputStream(ruta + "/Downloads/Reportes_Productos.pdf"));
            Image header = Image.getInstance("src/img/Plazayterraza.jpg");
            header.scaleToFit(650, 1000);
            header.setAlignment(Chunk.ALIGN_CENTER);
            Paragraph parrafo = new Paragraph();
            parrafo.setAlignment(Paragraph.ALIGN_CENTER);
            parrafo.add("Reporte Creado por \n Plaza Y Terraza S.A.S \n\n");
            parrafo.setFont(FontFactory.getFont("Times New Roman", 18, Font.BOLD, BaseColor.DARK_GRAY));
            parrafo.add("Reporte de Productos \n\n");

            documento.open();
            documento.add(header);
            documento.add(parrafo);

            float[] columnsWidths = {4, 5, 5, 5, 7, 5, 6};

            PdfPTable tabla = new PdfPTable(columnsWidths);
            tabla.addCell("Código");
            tabla.addCell("Nombre");
            tabla.addCell("Cantidad");
            tabla.addCell("Precio");
            tabla.addCell("Descripción");
            tabla.addCell("% IVA");
            tabla.addCell("Categoría");

            try {
                Connection cn = Conexion.conectar();
                PreparedStatement pst = cn.prepareStatement("select p.idProducto, p.nombre, p.cantidad, p.precio, p.descripcion, p.porcentajeIva, c.descripcion as categoria, p.estado from tb_producto as p, tb_categoria as c where p.idCategoria = c.idCategoria;");
                ResultSet rs = pst.executeQuery();
                if (rs.next()) {
                    do {
                        tabla.addCell(rs.getString(1));
                        tabla.addCell(rs.getString(2));
                        tabla.addCell(rs.getString(3));
                        tabla.addCell(rs.getString(4));
                        tabla.addCell(rs.getString(5));
                        tabla.addCell(rs.getString(6));
                        tabla.addCell(rs.getString(7));
                    } while (rs.next());
                    documento.add(tabla);

                }

            } catch (SQLException e) {
                System.out.println("Error4 en: " + e);
            }
            documento.close();

            JOptionPane.showMessageDialog(null, "¡Reporte de Productos Creado Exitosamente!, revisa tus descargas.");
        } catch (DocumentException e) {
            System.out.println("Error1 en: " + e);
        } catch (FileNotFoundException ex) {
            System.out.println("Error2 en: " + ex);
        } catch (IOException ex) {
            System.out.println("Error3 en: " + ex);
        }
    }

    public void ReportesCategorias() {
        Document documento = new Document();
        try {

            String ruta = System.getProperty("user.home");
            PdfWriter.getInstance(documento, new FileOutputStream(ruta + "/Downloads/Reportes_Categorias.pdf"));
            Image header = Image.getInstance("src/img/Plazayterraza.jpg");
            header.scaleToFit(650, 1000);
            header.setAlignment(Chunk.ALIGN_CENTER);
            Paragraph parrafo = new Paragraph();
            parrafo.setAlignment(Paragraph.ALIGN_CENTER);
            parrafo.add("Reporte Creado por \n Plaza Y Terraza S.A.S \n\n");
            parrafo.setFont(FontFactory.getFont("Times New Roman", 18, Font.BOLD, BaseColor.DARK_GRAY));
            parrafo.add("Reporte de Categorías \n\n");

            documento.open();
            documento.add(header);
            documento.add(parrafo);

            PdfPTable tabla = new PdfPTable(2);
            tabla.addCell("Código");
            tabla.addCell("Descripción");

            try {
                Connection cn = Conexion.conectar();
                PreparedStatement pst = cn.prepareStatement("select * from tb_categoria");
                ResultSet rs = pst.executeQuery();
                if (rs.next()) {
                    do {
                        tabla.addCell(rs.getString(1));
                        tabla.addCell(rs.getString(2));

                    } while (rs.next());
                    documento.add(tabla);

                }

            } catch (SQLException e) {
                System.out.println("Error4 en: " + e);
            }
            documento.close();

            JOptionPane.showMessageDialog(null, "¡Reporte de Categorías Creado Exitosamente!, revisa tus descargas.");
        } catch (DocumentException e) {
            System.out.println("Error1 en: " + e);
        } catch (FileNotFoundException ex) {
            System.out.println("Error2 en: " + ex);
        } catch (IOException ex) {
            System.out.println("Error3 en: " + ex);
        }
    }

    public void ReportesVentas() {
        Document documento = new Document();
        try {
            String ruta = System.getProperty("user.home");
            PdfWriter.getInstance(documento, new FileOutputStream(ruta + "/Downloads/Reportes_Ventas.pdf"));

            Image header = Image.getInstance("src/img/Plazayterraza.jpg");
            header.scaleToFit(650, 1000);
            header.setAlignment(Chunk.ALIGN_CENTER);

            Paragraph parrafo = new Paragraph();
            parrafo.setAlignment(Paragraph.ALIGN_CENTER);
            parrafo.add("Reporte Creado por \n Plaza Y Terraza S.A.S \n\n");
            parrafo.setFont(FontFactory.getFont("Times New Roman", 18, Font.BOLD, BaseColor.DARK_GRAY));
            parrafo.add("Reporte de Ventas \n\n");

            documento.open();
            documento.add(header);
            documento.add(parrafo);

            // --- Columnas con "Productos" ---
            float[] columnsWidths = {3, 9, 5, 6, 5};
            PdfPTable tabla = new PdfPTable(columnsWidths);

            tabla.addCell("Código");
            tabla.addCell("Cliente");
            tabla.addCell("Total a Pagar");
            tabla.addCell("Fecha");
            tabla.addCell("Productos");

            try {
                Connection cn = Conexion.conectar();
                PreparedStatement pst = cn.prepareStatement(
                        "SELECT cv.idCabeceraVenta AS id, "
                        + "CONCAT(c.empresa, ' ', c.nombre) AS cliente, "
                        + "cv.valorPagar AS total, "
                        + "cv.fechaVenta AS fecha, "
                        + "GROUP_CONCAT(p.nombre SEPARATOR ', ') AS productos "
                        + "FROM tb_cabecera_venta AS cv "
                        + "INNER JOIN tb_cliente AS c ON cv.idCliente = c.idCliente "
                        + "INNER JOIN tb_detalle_venta AS dv ON cv.idCabeceraVenta = dv.idCabeceraVenta "
                        + "INNER JOIN tb_producto AS p ON dv.idProducto = p.idProducto "
                        + "GROUP BY cv.idCabeceraVenta;"
                );

                ResultSet rs = pst.executeQuery();
                if (rs.next()) {
                    do {
                        tabla.addCell(rs.getString("id"));
                        tabla.addCell(rs.getString("cliente"));
                        tabla.addCell(rs.getString("total"));
                        tabla.addCell(rs.getString("fecha"));
                        tabla.addCell(rs.getString("productos"));
                    } while (rs.next());
                    documento.add(tabla);
                }

                cn.close();

            } catch (SQLException e) {
                System.out.println("Error al obtener datos: " + e);
            }

            documento.close();
            JOptionPane.showMessageDialog(null, "¡Reporte de Ventas Creado Exitosamente!, revisa tus descargas.");

        } catch (DocumentException e) {
            System.out.println("Error al generar documento: " + e);
        } catch (FileNotFoundException ex) {
            System.out.println("Archivo no encontrado: " + ex);
        } catch (IOException ex) {
            System.out.println("Error al cargar imagen o archivo: " + ex);
        }
    }
}
