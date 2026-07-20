package Model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Software extends ActivoDigital {

    private String plataforma;
    private int numeroDeInstalaciones;
    private String tipoDeSoftware;
    private Licencia licencia;

    public Software(String plataforma, int numeroDeInstalaciones, String tipoDeSoftware, Licencia licencia, String proveedor, String version, String nombre, String id, String estado, LocalDate fechaIngreso, LocalDate fechaDeBaja, String areaDePertenencia, double costoBase) {
        super(proveedor, version, nombre, id, estado, fechaIngreso, fechaDeBaja, areaDePertenencia, costoBase);
        this.plataforma = plataforma;
        this.numeroDeInstalaciones = numeroDeInstalaciones;
        this.tipoDeSoftware = tipoDeSoftware;
        this.licencia = licencia;
    }

    public String getPlataforma() {
        return plataforma;
    }

    public int getNumeroDeInstalaciones() {
        return numeroDeInstalaciones;
    }

    public String getTipoDeSoftware() {
        return tipoDeSoftware;
    }

    public Licencia getLicencia() {
        return licencia;
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
    public double calculoDeCosto() {
        return this.getCostoBase();
    }
}
