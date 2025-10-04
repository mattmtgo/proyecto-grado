
package modelo;


public class CabeceraVenta {
    
    //atribitos//
    private int idCabeceraventa;
    private int idCliente;
    private double valorPagar;
    private String fechaventa;
    private int estado;
    
    //constructor
    
    public CabeceraVenta(){
        this.idCabeceraventa = 0;
        this.idCliente = 0;
        this.valorPagar = 0;
        this.fechaventa = "";
        this.estado = 0;
    }

    public CabeceraVenta(int idCabeceraventa, int idCliente, double valorPagar, String fechaventa, int estado) {
        this.idCabeceraventa = idCabeceraventa;
        this.idCliente = idCliente;
        this.valorPagar = valorPagar;
        this.fechaventa = fechaventa;
        this.estado = estado;
    }

    //getter and setter//

    public int getIdCabeceraventa() {
        return idCabeceraventa;
    }

    public void setIdCabeceraventa(int idCabeceraventa) {
        this.idCabeceraventa = idCabeceraventa;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public double getValorPagar() {
        return valorPagar;
    }

    public void setValorPagar(double valorPagar) {
        this.valorPagar = valorPagar;
    }

    public String getFechaventa() {
        return fechaventa;
    }

    public void setFechaventa(String fechaventa) {
        this.fechaventa = fechaventa;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "CabeceraVenta{" + "idCabeceraventa=" + idCabeceraventa + ", idCliente=" + idCliente + ", valorPagar=" + valorPagar + ", fechaventa=" + fechaventa + ", estado=" + estado + '}';
    }
    
    
}
