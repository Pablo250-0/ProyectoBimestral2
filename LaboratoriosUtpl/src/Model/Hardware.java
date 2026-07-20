package Model;

import java.time.LocalDate;

public class Hardware extends Activo {
    
    private LocalDate fechaDeProximoMantenimiento;
    private int tiempoDeUso;
    private LocalDate ultimoMantenimiento;
    private int vidaUtil;

    public Hardware(LocalDate fechaDeProximoMantenimiento, int tiempoDeUso, LocalDate ultimoMantenimiento, int vidaUtil, String nombre, String id, String estado, LocalDate fechaIngreso, LocalDate fechaDeBaja, String areaDePertenencia, double costoBase) {
        super(nombre, id, estado, fechaIngreso, fechaDeBaja, areaDePertenencia, costoBase);
        this.fechaDeProximoMantenimiento = fechaDeProximoMantenimiento;
        this.tiempoDeUso = tiempoDeUso;
        this.ultimoMantenimiento = ultimoMantenimiento;
        this.vidaUtil = vidaUtil;
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

    public int getVidaUtil() {
        return vidaUtil;
    }

    public void setVidaUtil(int vidaUtil) {
        this.vidaUtil = vidaUtil;
    }

    @Override
    public String verificarEstado() {
        if (LocalDate.now().isAfter(fechaDeProximoMantenimiento)) {
            return "Vencido";
        }

        if (LocalDate.now().plusDays(30).isAfter(fechaDeProximoMantenimiento)) {
            return "Próximo mantenimiento";
        }

        return "Operativo";
    }

    @Override
    public double calculoDeCosto() {
        return costoBase +(tiempoDeUso * 8);
    }
    
}
