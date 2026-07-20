package Model;

import java.time.LocalDate;

public abstract class Activo {
    
     protected String nombre;
    protected String id;
    protected String estado;
    protected LocalDate fechaIngreso;
    protected LocalDate fechaDeBaja;
    protected String areaDePertenencia;
    protected double costoBase;

    public Activo(String nombre, String id, String estado, LocalDate fechaIngreso, LocalDate fechaDeBaja, String areaDePertenencia, double costoBase) {
        this.nombre = nombre;
        this.id = id;
        this.estado = estado;
        this.fechaIngreso = fechaIngreso;
        this.fechaDeBaja = fechaDeBaja;
        this.areaDePertenencia = areaDePertenencia;
        this.costoBase = costoBase;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public LocalDate getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(LocalDate fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public LocalDate getFechaDeBaja() {
        return fechaDeBaja;
    }

    public void setFechaDeBaja(LocalDate fechaDeBaja) {
        this.fechaDeBaja = fechaDeBaja;
    }

    public String getAreaDePertenencia() {
        return areaDePertenencia;
    }

    public void setAreaDePertenencia(String areaDePertenencia) {
        this.areaDePertenencia = areaDePertenencia;
    }

    public double getCostoBase() {
        return costoBase;
    }

    public void setCostoBase(double costoBase) {
        this.costoBase = costoBase;
    }
    
    public abstract String verificarEstado();
    
    public abstract double calculoDeCosto();

    @Override
    public String toString() {
        return "Activo{" + "nombre=" + nombre + ", id=" + id + ", estado=" + estado + ", fechaIngreso=" + fechaIngreso + ", fechaDeBaja=" + fechaDeBaja + ", areaDePertenencia=" + areaDePertenencia + ", costoBase=" + costoBase + '}';
    }
}
