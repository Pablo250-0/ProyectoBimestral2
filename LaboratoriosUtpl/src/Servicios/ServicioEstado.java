package Service;

import Model.Activo;
import Model.EstadoM;
import Model.Hardware;
import Model.Licencia;
import Model.Software;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

/**
 * @author Pablo
 */
public class ServicioEstado {

    /** Dias de anticipacion con que se avisa el vencimiento de una licencia. */
    private static final int DIAS_AVISO_LICENCIA = 30;

    public EstadoM evaluarActivo(Activo activo) {
        if (activo == null) {
            return null;
        }
        if (activo instanceof Hardware h) {
            return evaluarHardware(h);
        }
        if (activo instanceof Software s) {
            return evaluarSoftware(s);
        }
        return EstadoM.FUNCIONAL;
    }

    private EstadoM evaluarHardware(Hardware h) {
        if (h.getFechaDeBaja() != null) {
            return EstadoM.BAJA;
        }
        if (h.requiereMantenimiento()) {
            return EstadoM.MANTENIMIENTO;
        }
        if (h.getVidaUtil() > 0 && h.getTiempoDeUso() >= h.getVidaUtil()) {
            return EstadoM.OBSOLETO;
        }
        return EstadoM.FUNCIONAL;
    }

    private EstadoM evaluarSoftware(Software s) {
        if (s.getFechaDeBaja() != null) {
            return EstadoM.BAJA;
        }

        Licencia licencia = s.getLicencia();
        if (licencia != null && licencia.getFechaDeCaducidad() != null
                && LocalDate.now().isAfter(licencia.getFechaDeCaducidad())) {
            return EstadoM.OBSOLETO;
        }

        return EstadoM.FUNCIONAL;
    }

    public ArrayList<String> generarAlertas(ArrayList<Activo> activos) {
        ArrayList<String> alertas = new ArrayList<>();

        for (Activo a : activos) {

            if (a instanceof Hardware h) {
                if (h.requiereMantenimiento()) {
                    alertas.add("[" + h.getId() + "] " + h.getNombre()
                            + ": mantenimiento vencido desde "
                            + h.getFechaDeProximoMantenimiento());
                }
                if (h.getVidaUtil() > 0 && h.getTiempoDeUso() >= h.getVidaUtil()) {
                    alertas.add("[" + h.getId() + "] " + h.getNombre()
                            + ": supero su vida util (" + h.getVidaUtil() + " meses)");
                }
            }

            if (a instanceof Software s) {
                alertas.addAll(alertasDeLicencia(s));
            }
        }
        return alertas;
    }

    private ArrayList<String> alertasDeLicencia(Software s) {
        ArrayList<String> alertas = new ArrayList<>();
        Licencia licencia = s.getLicencia();

        if (licencia == null || licencia.getFechaDeCaducidad() == null) {
            return alertas;
        }

        LocalDate hoy = LocalDate.now();
        LocalDate caducidad = licencia.getFechaDeCaducidad();

        if (hoy.isAfter(caducidad)) {
            alertas.add("[" + s.getId() + "] " + s.getNombre()
                    + ": licencia expirada el " + caducidad);
        } else {
            long dias = ChronoUnit.DAYS.between(hoy, caducidad);
            if (dias <= DIAS_AVISO_LICENCIA) {
                alertas.add("[" + s.getId() + "] " + s.getNombre()
                        + ": licencia vence en " + dias + " dias");
            }
        }
        return alertas;
    }
}