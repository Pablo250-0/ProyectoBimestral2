package Model;

import java.time.LocalDate;

public class Impresora extends Hardware {
    
    private String tipoImpresion;
    private double velocidadImpresion;
    private int nivelTinta;

    public Impresora(String tipoImpresion, double velocidadImpresion, int nivelTinta, LocalDate fechaDeProximoMantenimiento, int tiempoDeUso, LocalDate ultimoMantenimiento, EstadoM estadoM, String ubicacion, String numeroSerie, String responsable, int vidaUtil, String nombre, String id, String estado, LocalDate fechaIngreso, LocalDate fechaDeBaja, double costoBase) {
        super(fechaDeProximoMantenimiento, tiempoDeUso, ultimoMantenimiento, estadoM, ubicacion, numeroSerie, responsable, vidaUtil, nombre, id, estado, fechaIngreso, fechaDeBaja, costoBase);
        this.tipoImpresion = tipoImpresion;
        this.velocidadImpresion = velocidadImpresion;
        this.nivelTinta = nivelTinta;
    }

    public String getTipoImpresion() {
        return tipoImpresion;
    }

    public void setTipoImpresion(String tipoImpresion) {
        this.tipoImpresion = tipoImpresion;
    }

    public double getVelocidadImpresion() {
        return velocidadImpresion;
    }

    public void setVelocidadImpresion(double velocidadImpresion) {
        this.velocidadImpresion = velocidadImpresion;
    }

    public int getNivelTinta() {
        return nivelTinta;
    }

    public void setNivelTinta(int nivelTinta) {
        this.nivelTinta = nivelTinta;
    }

    @Override
    public String verificarEstado() {
        if (requiereMantenimiento() || nivelTinta <= 5) {
            estadoM = EstadoM.MANTENIMIENTO;
        } else if (vidaUtil > 0 && tiempoDeUso >= vidaUtil) {
            estadoM = EstadoM.OBSOLETO;
        } else {
            estadoM = EstadoM.FUNCIONAL;
        }
        return estado;
    }

    @Override
    public double calculoDeCosto() {
        if (vidaUtil <= 0) return costoBase;
        double factor = Math.max(0.10, 1 - ((double) tiempoDeUso / vidaUtil));
        return costoBase * factor;
    }
    
    
}
