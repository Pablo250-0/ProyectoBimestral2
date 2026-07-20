package Model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Licencia extends ActivoDigital {
     
    private String tipo;
    private LocalDate fechaDeCaducidad;

    public Licencia(String tipo, LocalDate fechaDeCaducidad, String proveedor, String version, String nombre, String id, String estado, LocalDate fechaIngreso, LocalDate fechaDeBaja, String areaDePertenencia, double costoBase) {
        super(proveedor, version, nombre, id, estado, fechaIngreso, fechaDeBaja, areaDePertenencia, costoBase);
        this.tipo = tipo;
        this.fechaDeCaducidad = fechaDeCaducidad;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public LocalDate getFechaDeCaducidad() {
        return fechaDeCaducidad;
    }

    public void setFechaDeCaducidad(LocalDate fechaDeCaducidad) {
        this.fechaDeCaducidad = fechaDeCaducidad;
    }
    
    @Override
    public String verificarEstado() {
        LocalDate hoy = LocalDate.now();
        if (hoy.isAfter(fechaDeCaducidad)) {
            return "Expirada";
        }
        long diasParaVencer = ChronoUnit.DAYS.between(hoy, fechaDeCaducidad);
        if (diasParaVencer >= 0 && diasParaVencer < 30) {
            return "Por vencer";
        }else {
            return "Activa";
        }
    }

    @Override
    public double calculoDeCosto() {
        return costoBase;
    }
}
