package Model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Licencia {
     
    private String tipo;
    private LocalDate fechaDeCaducidad;

    public Licencia(String tipo, LocalDate fechaDeCaducidad) {
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
  
}
