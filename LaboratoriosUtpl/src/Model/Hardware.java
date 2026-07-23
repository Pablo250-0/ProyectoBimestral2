package Model;

import java.time.LocalDate;

public abstract class Hardware extends ActivoFisico {

    protected LocalDate fechaDeProximoMantenimiento;
    protected int tiempoDeUso;
    protected LocalDate ultimoMantenimiento;
    protected EstadoM estadoM;

    public Hardware(LocalDate fechaDeProximoMantenimiento, int tiempoDeUso, LocalDate ultimoMantenimiento, EstadoM estadoM, String ubicacion, String numeroSerie, String responsable, int vidaUtil, String nombre, String id, String estado, LocalDate fechaIngreso, LocalDate fechaDeBaja, double costoBase) {
        super(ubicacion, numeroSerie, responsable, vidaUtil, nombre, id, estado, fechaIngreso, fechaDeBaja, costoBase);
        this.fechaDeProximoMantenimiento = fechaDeProximoMantenimiento;
        this.tiempoDeUso = tiempoDeUso;
        this.ultimoMantenimiento = ultimoMantenimiento;
        this.estadoM = estadoM;
    }

    public LocalDate getFechaDeProximoMantenimiento() {
        return fechaDeProximoMantenimiento;
    }

    public void setFechaDeProximoMantenimiento(LocalDate fechaDeProximoMantenimiento) {
        this.fechaDeProximoMantenimiento = fechaDeProximoMantenimiento;
    }

    public int getTiempoDeUso() {
        return tiempoDeUso;
    }

    public void setTiempoDeUso(int tiempoDeUso) {
        this.tiempoDeUso = tiempoDeUso;
    }

    public LocalDate getUltimoMantenimiento() {
        return ultimoMantenimiento;
    }

    public void setUltimoMantenimiento(LocalDate ultimoMantenimiento) {
        this.ultimoMantenimiento = ultimoMantenimiento;
    }

    public EstadoM getEstadoM() {
        return estadoM;
    }

    public void setEstadoM(EstadoM estadoM) {
        this.estadoM = estadoM;
    }

    public boolean requiereMantenimiento() {
        return fechaDeProximoMantenimiento != null && !LocalDate.now().isBefore(fechaDeProximoMantenimiento);
    }

    public abstract String verificarEstado();

    public abstract double calculoDeCosto();

}
