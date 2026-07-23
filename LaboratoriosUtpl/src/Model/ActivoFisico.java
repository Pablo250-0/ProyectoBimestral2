package Model;

import java.time.LocalDate;

public abstract class ActivoFisico extends Activo {

    protected String ubicacion;
    protected String numeroSerie;
    protected String responsable;
    protected int vidaUtil;

    public ActivoFisico(String nombre, String id, String estado, LocalDate fechaIngreso, LocalDate fechaDeBaja, double costoBase) {
        super(nombre, id, estado, fechaIngreso, fechaDeBaja, costoBase);
    }

    
    public ActivoFisico(String ubicacion, String numeroSerie, String responsable, int vidaUtil, String nombre, String id, String estado, LocalDate fechaIngreso, LocalDate fechaDeBaja, double costoBase) {
        super(nombre, id, estado, fechaIngreso, fechaDeBaja, costoBase);
        this.ubicacion = ubicacion;
        this.numeroSerie = numeroSerie;
        this.responsable = responsable;
        this.vidaUtil = vidaUtil;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getNumeroSerie() {
        return numeroSerie;
    }

    public void setNumeroSerie(String numeroSerie) {
        this.numeroSerie = numeroSerie;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    public int getVidaUtil() {
        return vidaUtil;
    }

    public void setVidaUtil(int vidaUtil) {
        this.vidaUtil = vidaUtil;
    }

    @Override
    public String toString() {
        return "ActivoFisico{" + "ubicacion=" + ubicacion + ", numeroSerie=" + numeroSerie + ", responsable=" + responsable + ", vidaUtil=" + vidaUtil + '}';
    }

    public abstract String verificarEstado();

    public abstract double calculoDeCosto();
    
}
