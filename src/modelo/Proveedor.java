package modelo;

/**
 *
 * @author Mateo
 */
public class Proveedor {

    // 🔹 Atributos
    private int idProveedor;
    private String empresa;
    private String nombre;
    private String cedula;
    private String telefono;
    private String direccion;
    private int estado;

    // 🔹 Constructor vacío
    public Proveedor() {
        this.idProveedor = 0;
        this.empresa = "";
        this.nombre = "";
        this.cedula = "";
        this.telefono = "";
        this.direccion = "";
        this.estado = 0;
    }

    // 🔹 Constructor con parámetros
    public Proveedor(int idProveedor, String empresa, String nombre, String cedula, String telefono, String direccion, int estado) {
        this.idProveedor = idProveedor;
        this.empresa = empresa;
        this.nombre = nombre;
        this.cedula = cedula;
        this.telefono = telefono;
        this.direccion = direccion;
        this.estado = estado;
    }

    // 🔹 Métodos Get y Set
    public int getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
}