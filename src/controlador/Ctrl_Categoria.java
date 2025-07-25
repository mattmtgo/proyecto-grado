
package controlador;

import java.sql.PreparedStatement;
import modelo.Categoria;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author Aide
 */
public class Ctrl_Categoria {
    
    //metodo para registrar nueva categoría
    public boolean guardar(Categoria objeto){
        boolean respuesta = false;
        Connection cn = conexion.Conexion.conectar();
        try {
            
            PreparedStatement consulta = cn.prepareStatement("insert into tb_categoria values(?,?,?)");
            consulta.setInt(1, 0);
            consulta.setString(2,objeto.getDescripcion());
            consulta.setInt(3, objeto.getEstado());
            
            if (consulta.executeUpdate() >0) {
                respuesta = true;       
                
            }
            
            cn.close();
            
        } catch (SQLException e) {
            System.out.println("Error al guardar categoría: " + e);
                    
        }
        
        return respuesta;
    }
    
}
