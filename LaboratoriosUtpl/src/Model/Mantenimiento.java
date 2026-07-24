/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.time.LocalDate;

/**
 * @author Pablo
 */
public class Mantenimiento {

    private String idMantenimiento;
    private String tipo;
    private String descripcion;
    private double costo;
    private EstadoM estado;
    private LocalDate fecha;
    private Hardware activo;

    public Mantenimiento(String idMantenimiento, String tipo, String descripcion,
                         double costo, EstadoM estado, LocalDate fecha, Hardware activo) {
        this.idMantenimiento = idMantenimiento;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.costo = costo;
        this.estado = estado;
        this.fecha = fecha;
        this.activo = activo;
    }

    public String getIdMantenimiento() {
        return idMantenimiento;
    }

    public void setIdMantenimiento(String idMantenimiento) {
        this.idMantenimiento = idMantenimiento;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public EstadoM getEstado() {
        return estado;
    }

    public void setEstado(EstadoM estado) {
        this.estado = estado;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Hardware getActivo() {
        return activo;
    }

    public void setActivo(Hardware activo) {
        this.activo = activo;
    }

    @Override
    public String toString() {
        return "Mantenimiento{" + "idMantenimiento=" + idMantenimiento
                + ", tipo=" + tipo + ", descripcion=" + descripcion
                + ", costo=" + costo + ", estado=" + estado
                + ", fecha=" + fecha + '}';
    }
}