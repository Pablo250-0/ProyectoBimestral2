package Model;

import java.time.LocalDate;

public abstract class ActivoDigital extends Activo {

    protected String proveedor;
    protected String version;

    public ActivoDigital(String proveedor, String version, String nombre, String id, String estado, LocalDate fechaIngreso, LocalDate fechaDeBaja, double costoBase) {
        super(nombre, id, estado, fechaIngreso, fechaDeBaja, costoBase);
        this.proveedor = proveedor;
        this.version = version;
    }

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public abstract String verificarEstado();

    public abstract double calculoDeCosto();
}
