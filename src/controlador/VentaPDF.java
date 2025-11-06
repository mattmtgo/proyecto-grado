package controlador;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.sql.Connection;
import java.sql.Statement;
import conexion.Conexion;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import vista.InterFacturacion;

public class VentaPDF {

    private String empresaClinte;
    private String nombreCliente;
    private String telefonoCliente;
    private String cedulaCliente;
    private String direccionCliente;
    private String fechaActual = "";
    private String nombreArchivoPDFventa;
    private String tipoPago = "Efectivo"; // Atributo de la clase

    // MÉTODO CORREGIDO (Línea 44 en tu traza de error)
    public void setTipoPago(String tipoPago) {
        this.tipoPago = tipoPago; // ✅ Asigna el valor del parámetro al atributo de la clase.
    }

    public void DatosCliete(int idCliente) {
        Connection cn = Conexion.conectar();
        String sql = "select * from tb_cliente where idCliente = '" + idCliente + "'";
        Statement st;
        try {

            st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                nombreCliente = rs.getString("nombre") + " " + rs.getString("empresa");
                cedulaCliente = rs.getString("cedula");
                telefonoCliente = rs.getString("telefono");
                direccionCliente = rs.getString("direccion");
            }
            cn.close();
        } catch (SQLException e) {
            System.out.println("Error al obtener datos del cliente: " + e);
        }
    }

    public void generarFacturaPDF() {
        try {
            // Fecha actual
            Date date = new Date();
            fechaActual = new SimpleDateFormat("yyyy/MM/dd").format(date);

            // Reemplazar "/" por "_" para el nombre del archivo
            String fechaNueva = fechaActual.replace("/", "_");
            nombreArchivoPDFventa = "Venta_" + nombreCliente + "_" + fechaNueva + ".pdf";

            // Ruta Descargas del usuario
            String rutaDescargas = System.getProperty("user.home") + "/Downloads/";
            File fileDescargas = new File(rutaDescargas + nombreArchivoPDFventa);

            // Crear documento PDF
            Document doc = new Document();
            PdfWriter.getInstance(doc, new FileOutputStream(fileDescargas));
            doc.open();

            // Imagen del encabezado
            Image img = Image.getInstance("src/img/plaza1.1.png");

            // Fuente negrita
            Font negrita = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, BaseColor.BLUE);

            // Fecha y número de factura
            Paragraph fecha = new Paragraph();
            fecha.add(Chunk.NEWLINE);
            fecha.add("Fecha: " + fechaActual + "\n\n");

            // Tabla encabezado
            PdfPTable Encabezado = new PdfPTable(4);
            Encabezado.setWidthPercentage(100);
            Encabezado.getDefaultCell().setBorder(0);
            float[] ColumnaEncabezado = new float[]{20f, 30f, 70f, 40f};
            Encabezado.setWidths(ColumnaEncabezado);
            Encabezado.setHorizontalAlignment(Element.ALIGN_LEFT);

            Encabezado.addCell(img);

            String NIT = "830.123.707-8";
            String nombre = "Plaza Y Terraza";
            String telefono = "(601)5270991";
            String direccion = "Cl. 71B #77A-91, Engativá, Bogotá, Colombia";
            String correo = "plazayterraza@gmail.com";

            Encabezado.addCell("");
            Encabezado.addCell("NIT: " + NIT + "\nNOMBRE: " + nombre + "\nTELÉFONO: " + telefono
                    + "\nDIRECCIÓN: " + direccion + "\nCORREO: " + correo);
            Encabezado.addCell(fecha);

            doc.add(Encabezado);

            // Datos del cliente
            Paragraph cliente = new Paragraph();
            cliente.add(Chunk.NEWLINE);
            cliente.add("Datos del cliente: " + "\n\n");
            doc.add(cliente);

            PdfPTable tablaCliente = new PdfPTable(4);
            tablaCliente.setWidthPercentage(100);
            tablaCliente.getDefaultCell().setBorder(0);
            float[] ColumnaCliente = new float[]{25f, 45f, 30f, 40f};
            tablaCliente.setWidths(ColumnaCliente);
            tablaCliente.setHorizontalAlignment(Element.ALIGN_LEFT);

            PdfPCell cliente1 = new PdfPCell(new Phrase("Cedula: ", negrita));
            PdfPCell cliente2 = new PdfPCell(new Phrase("Nombre: ", negrita));
            PdfPCell cliente3 = new PdfPCell(new Phrase("Télefono: ", negrita));
            PdfPCell cliente4 = new PdfPCell(new Phrase("Dirección: ", negrita));

            cliente1.setBorder(0);
            cliente2.setBorder(0);
            cliente3.setBorder(0);
            cliente4.setBorder(0);

            tablaCliente.addCell(cliente1);
            tablaCliente.addCell(cliente2);
            tablaCliente.addCell(cliente3);
            tablaCliente.addCell(cliente4);
            tablaCliente.addCell(cedulaCliente);
            tablaCliente.addCell(nombreCliente);
            tablaCliente.addCell(telefonoCliente);
            tablaCliente.addCell(direccionCliente);

            doc.add(tablaCliente);

            // 🔹 Mostrar el tipo de pago (usa el atributo corregido)
            Paragraph tipo = new Paragraph();
            tipo.add(Chunk.NEWLINE);
            tipo.add("Método de pago: " + tipoPago + "\n\n");
            tipo.getFont().setStyle(Font.BOLDITALIC);
            doc.add(tipo);

            // Espacio
            Paragraph espacio = new Paragraph();
            espacio.add(Chunk.NEWLINE);
            doc.add(espacio);

            // Tabla productos
            PdfPTable tablaProducto = new PdfPTable(4);
            tablaProducto.setWidthPercentage(100);
            tablaProducto.getDefaultCell().setBorder(0);
            float[] ColumnaProducto = new float[]{15f, 50f, 15f, 20f};
            tablaProducto.setWidths(ColumnaProducto);
            tablaProducto.setHorizontalAlignment(Element.ALIGN_LEFT);

            PdfPCell producto1 = new PdfPCell(new Phrase("Cantidad: ", negrita));
            PdfPCell producto2 = new PdfPCell(new Phrase("Descripción: ", negrita));
            PdfPCell producto3 = new PdfPCell(new Phrase("Precio Unitario: ", negrita));
            PdfPCell producto4 = new PdfPCell(new Phrase("Precio Total: ", negrita));

            producto1.setBorder(0);
            producto2.setBorder(0);
            producto3.setBorder(0);
            producto4.setBorder(0);
            producto1.setBackgroundColor(BaseColor.LIGHT_GRAY);
            producto2.setBackgroundColor(BaseColor.LIGHT_GRAY);
            producto3.setBackgroundColor(BaseColor.LIGHT_GRAY);
            producto4.setBackgroundColor(BaseColor.LIGHT_GRAY);

            tablaProducto.addCell(producto1);
            tablaProducto.addCell(producto2);
            tablaProducto.addCell(producto3);
            tablaProducto.addCell(producto4);

            for (int i = 0; i < InterFacturacion.jTable_productos.getRowCount(); i++) {
                String producto = InterFacturacion.jTable_productos.getValueAt(i, 1).toString();
                String cantidad = InterFacturacion.jTable_productos.getValueAt(i, 2).toString();
                String precio = InterFacturacion.jTable_productos.getValueAt(i, 3).toString();
                String total = InterFacturacion.jTable_productos.getValueAt(i, 7).toString();

                tablaProducto.addCell(cantidad);
                tablaProducto.addCell(producto);
                tablaProducto.addCell(precio);
                tablaProducto.addCell(total);
            }

            doc.add(tablaProducto);

            // Total a pagar
            Paragraph info = new Paragraph();
            info.add(Chunk.NEWLINE);
            info.add("Total a Pagar: " + InterFacturacion.txt_total_pagar.getText());
            info.setAlignment(Element.ALIGN_RIGHT);
            doc.add(info);

            // Firma
            Paragraph firma = new Paragraph();
            firma.add(Chunk.NEWLINE);
            firma.add("Cancelación y firma\n\n");
            firma.add("____________________");
            firma.setAlignment(Element.ALIGN_CENTER);
            doc.add(firma);

            // Mensaje
            Paragraph mensaje = new Paragraph();
            mensaje.add(Chunk.NEWLINE);
            mensaje.add("¡Gracias por su compra!");
            mensaje.setAlignment(Element.ALIGN_CENTER);
            doc.add(mensaje);

            // Cerrar documento
            doc.close();

            // Abrir PDF automáticamente
            Desktop.getDesktop().open(fileDescargas);

            System.out.println("PDF guardado en: " + fileDescargas.getAbsolutePath());

        } catch (DocumentException | IOException e) {
            System.out.println("Error al generar PDF: " + e);
        }
    }

}
