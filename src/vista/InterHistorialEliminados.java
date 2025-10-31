package vista;

import conexion.Conexion;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class InterHistorialEliminados extends javax.swing.JInternalFrame {

    public InterHistorialEliminados() {
        initComponents();
        this.setSize(new Dimension(950, 500));
        this.setTitle("Historial de Eliminaciones");

        // 🔹 Cargar opciones del ComboBox
        cmb_tablaOrigen.addItem("Todos");
        cmb_tablaOrigen.addItem("Usuarios");
        cmb_tablaOrigen.addItem("Clientes");
        cmb_tablaOrigen.addItem("Ventas");
        cmb_tablaOrigen.addItem("Proveedores");
        cmb_tablaOrigen.addItem("Productos");
        cmb_tablaOrigen.addItem("Categorías");

        // 🔹 Cargar historial completo al iniciar
        CargarHistorial("Todos");

        // 🔹 Fondo personalizado
        ImageIcon wallpaper = new ImageIcon("src/img/fondo8.jpg");
        Icon icono = new ImageIcon(wallpaper.getImage().getScaledInstance(1200, 800, WIDTH));
        jLabel_wallpaper.setIcon(icono);
        this.repaint();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable_historial = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        btn_refrescar = new javax.swing.JButton();
        btn_filtrar = new javax.swing.JButton();
        cmb_tablaOrigen = new javax.swing.JComboBox<>();
        jLabel_wallpaper = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Historial de Eliminaciones");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 10, -1, -1));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTable_historial.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        jTable_historial.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable_historial);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 710, 250));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 730, 270));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btn_refrescar.setBackground(new java.awt.Color(0, 255, 51));
        btn_refrescar.setText("Refrescar");
        btn_refrescar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_refrescarActionPerformed(evt);
            }
        });
        jPanel2.add(btn_refrescar, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 80, -1, -1));

        btn_filtrar.setBackground(new java.awt.Color(0, 204, 204));
        btn_filtrar.setText("Filtrar");
        btn_filtrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_filtrarActionPerformed(evt);
            }
        });
        jPanel2.add(btn_filtrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 50, 80, -1));

        cmb_tablaOrigen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmb_tablaOrigenActionPerformed(evt);
            }
        });
        jPanel2.add(cmb_tablaOrigen, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 140, -1));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 130, 140, 120));
        getContentPane().add(jLabel_wallpaper, new org.netbeans.lib.awtextra.AbsoluteConstraints(-120, -60, 1070, 530));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_refrescarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_refrescarActionPerformed
        CargarHistorial("Todos");
        cmb_tablaOrigen.setSelectedItem("Todos");
    }//GEN-LAST:event_btn_refrescarActionPerformed

    private void btn_filtrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_filtrarActionPerformed

        String seleccion = cmb_tablaOrigen.getSelectedItem().toString();
        CargarHistorial(seleccion);

    }//GEN-LAST:event_btn_filtrarActionPerformed

    private void cmb_tablaOrigenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmb_tablaOrigenActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmb_tablaOrigenActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_filtrar;
    private javax.swing.JButton btn_refrescar;
    private javax.swing.JComboBox<String> cmb_tablaOrigen;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel_wallpaper;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    public static javax.swing.JScrollPane jScrollPane1;
    public static javax.swing.JTable jTable_historial;
    // End of variables declaration//GEN-END:variables

    // ================= MÉTODO PARA CARGAR HISTORIAL ==================
    private void CargarHistorial(String filtroTabla) {
        Connection con = Conexion.conectar();
        DefaultTableModel model = new DefaultTableModel();
        String sql;

        // Consulta base dependiendo del filtro
        if ("Todos".equals(filtroTabla)) {
            sql = "SELECT idHistorial, tabla_origen, idRegistroEliminado, datos, fechaEliminacion "
                    + "FROM tb_historial_eliminados ORDER BY fechaEliminacion DESC";
        } else {
            sql = "SELECT idHistorial, tabla_origen, idRegistroEliminado, datos, fechaEliminacion "
                    + "FROM tb_historial_eliminados WHERE tabla_origen = ? ORDER BY fechaEliminacion DESC";
        }

        try ( PreparedStatement pst = con.prepareStatement(sql)) {

            if (!"Todos".equals(filtroTabla)) {
                // 🔹 Conversión del nombre visible a nombre real de tabla
                String tabla = "";
                if ("Usuarios".equalsIgnoreCase(filtroTabla)) {
                    tabla = "tb_usuario";
                } else if ("Clientes".equalsIgnoreCase(filtroTabla)) {
                    tabla = "tb_cliente";
                } else if ("Proveedores".equalsIgnoreCase(filtroTabla)) {
                    tabla = "tb_proveedor";
                } else if ("Productos".equalsIgnoreCase(filtroTabla)) {
                    tabla = "tb_producto";
                } else if ("Categorías".equalsIgnoreCase(filtroTabla) || "Categorias".equalsIgnoreCase(filtroTabla)) {
                    tabla = "tb_categoria";
                } else if ("Ventas".equalsIgnoreCase(filtroTabla)) {
                    tabla = "tb_cabecera_venta";
                } else {
                    tabla = filtroTabla; // fallback
                }

                pst.setString(1, tabla);
            }

            try ( ResultSet rs = pst.executeQuery()) {
                model.addColumn("ID");
                model.addColumn("Tabla");
                model.addColumn("ID Eliminado");
                model.addColumn("Datos");
                model.addColumn("Fecha");

                while (rs.next()) {
                    Object[] fila = new Object[5];
                    for (int i = 0; i < 5; i++) {
                        fila[i] = rs.getObject(i + 1);
                    }
                    model.addRow(fila);
                }

                // ✅ Asignar modelo sin cambiar el objeto JTable original
                jTable_historial.setModel(model);

                // Evitar edición
                jTable_historial.setDefaultEditor(Object.class, null);
            }

        } catch (SQLException e) {
            System.out.println("❌ Error al cargar historial: " + e);
        } finally {
            try {
                if (con != null && !con.isClosed()) {
                    con.close();
                }
            } catch (SQLException ex) {
                System.out.println("⚠️ Error al cerrar conexión: " + ex);
            }
        }
    }
}
