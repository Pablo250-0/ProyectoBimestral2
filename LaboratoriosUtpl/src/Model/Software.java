package Model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * @author Pablo
 */
public class Software extends ActivoDigital {
    
    private String plataforma;
    private int numeroDeInstalaciones;
    private String tipoDeSoftware;
    private Licencia licencia;

    public Software(String plataforma, int numeroDeInstalaciones, String tipoDeSoftware, Licencia licencia, String proveedor, String version, String nombre, String id, String estado, LocalDate fechaIngreso, LocalDate fechaDeBaja, double costoBase) {
        super(proveedor, version, nombre, id, estado, fechaIngreso, fechaDeBaja, costoBase);
        this.plataforma = plataforma;
        this.numeroDeInstalaciones = numeroDeInstalaciones;
        this.tipoDeSoftware = tipoDeSoftware;
        this.licencia = licencia;
    }
    
    public String getPlataforma() {
        return plataforma;
    }

    public void setPlataforma(String plataforma) {
        this.plataforma = plataforma;
    }

    public int getNumeroDeInstalaciones() {
        return numeroDeInstalaciones;
    }

    public void setNumeroDeInstalaciones(int numeroDeInstalaciones) {
        this.numeroDeInstalaciones = numeroDeInstalaciones;
    }

    public String getTipoDeSoftware() {
        return tipoDeSoftware;
    }

    public void setTipoDeSoftware(String tipoDeSoftware) {
        this.tipoDeSoftware = tipoDeSoftware;
    }

    public Licencia getLicencia() {
        return licencia;
    }

    public void setLicencia(Licencia licencia) {
        this.licencia = licencia;
    }

    @Override
    public String verificarEstado() {
        
        LocalDate hoy = LocalDate.now();
        
        if (esVersionDesactualizada()) {
            return "Desactualizada";
        }
        if (licencia != null) {
            LocalDate fechaCaducidad = licencia.getFechaDeCaducidad();
            
            if (hoy.isAfter(fechaCaducidad)) {
                return "Expirada";
            }
            
            long diasParaVencer = ChronoUnit.DAYS.between(hoy, fechaCaducidad);
            if (diasParaVencer >= 0 && diasParaVencer < 30) {
                return "Por vencer";
            }
        }
        if (this.numeroDeInstalaciones >= 100) {
            return "Saturado";
        }

        return "Disponible";
        
    }

    private boolean esVersionDesactualizada() {
        return "v1.0".equals(this.getVersion()); 
    }
    
    @Override
    public double calcularCosto() {
        return this.getCostoBase();
    }
}