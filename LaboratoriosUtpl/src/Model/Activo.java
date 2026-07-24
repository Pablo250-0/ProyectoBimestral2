/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author Pablo
 */
public abstract class Activo {
<<<<<<< Updated upstream
}
=======
    
    protected String nombre;
    protected String id;
    protected String estado;
    protected LocalDate fechaIngreso;
    protected LocalDate fechaDeBaja;
    protected double costoBase;

    public Activo(String nombre, String id, String estado, LocalDate fechaIngreso, LocalDate fechaDeBaja, double costoBase) {
        this.nombre = nombre;
        this.id = id;
        this.estado = estado;
        this.fechaIngreso = fechaIngreso;
        this.fechaDeBaja = fechaDeBaja;
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

    public double getCostoBase() {
        return costoBase;
    }

    public void setCostoBase(double costoBase) {
        this.costoBase = costoBase;
    }

    public abstract double calcularCosto();

    @Override
    public String toString() {
        return "Activo{" + "nombre=" + nombre + ", id=" + id + ", estado=" + estado + ", fechaIngreso=" + fechaIngreso + ", fechaDeBaja=" + fechaDeBaja + ", costoBase=" + costoBase + '}';
    }

    
}
>>>>>>> Stashed changes
