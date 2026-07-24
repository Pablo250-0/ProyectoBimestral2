package Model;

import java.time.LocalDate;

/**
 * @author Pablo
 */
public class Computadora extends Hardware {

    private String sistemaOperativo;
    private String procesador;
    private String ram;
    private int cantidadAlmacenamiento;

    public Computadora(String sistemaOperativo, String procesador, String ram, int cantidadAlmacenamiento, LocalDate fechaDeProximoMantenimiento, int tiempoDeUso, LocalDate ultimoMantenimiento, EstadoM estadoM, String ubicacion, String numeroSerie, String responsable, int vidaUtil, String nombre, String id, String estado, LocalDate fechaIngreso, LocalDate fechaDeBaja, double costoBase) {
        super(fechaDeProximoMantenimiento, tiempoDeUso, ultimoMantenimiento, estadoM, ubicacion, numeroSerie, responsable, vidaUtil, nombre, id, estado, fechaIngreso, fechaDeBaja, costoBase);
        this.sistemaOperativo = sistemaOperativo;
        this.ram = ram;
        this.cantidadAlmacenamiento = cantidadAlmacenamiento;
    }

    public String getSistemaOperativo() {
        return sistemaOperativo;
    }

    public void setSistemaOperativo(String sistemaOperativo) {
        this.sistemaOperativo = sistemaOperativo;
    }

    public String getProcesador() {
        return procesador;
    }

    public void setProcesador(String procesador) {
        this.procesador = procesador;
    }

    public String getRam() {
        return ram;
    }

    public void setRam(String ram) {
        this.ram = ram;
    }

    public int getCantidadAlmacenamiento() {
        return cantidadAlmacenamiento;
    }

    public void setCantidadAlmacenamiento(int cantidadAlmacenamiento) {
        this.cantidadAlmacenamiento = cantidadAlmacenamiento;
    }

    
    @Override
    public String verificarEstado() {
        if (requiereMantenimiento()) {
            estadoM = EstadoM.MANTENIMIENTO;
        } else if (vidaUtil > 0 && tiempoDeUso >= vidaUtil) {
            estadoM = EstadoM.OBSOLETO;
        } else {
            estadoM = EstadoM.FUNCIONAL;
        }
        return estado;
    }

    @Override
    public double calcularCosto() {
        if (vidaUtil <= 0) return costoBase;
        double factor = Math.max(0.10, 1 - ((double) tiempoDeUso / vidaUtil));
        return costoBase * factor;
    }
    
    
}