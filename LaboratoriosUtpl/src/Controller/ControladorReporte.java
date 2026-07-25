package Controller;

import DAO.LecturaActivos;
import Model.Activo;
import Service.ServicioCosto;
import Service.ServicioEstado;
import java.util.ArrayList;

/**
 * @author Pablo
 */
public class ControladorReporte {

    private final LecturaActivos lector;
    private final ServicioCosto servicioCosto;
    private final ServicioEstado servicioEstado;

    public ControladorReporte(LecturaActivos lector, ServicioCosto servicioCosto,
                              ServicioEstado servicioEstado) {
        this.lector = lector;
        this.servicioCosto = servicioCosto;
        this.servicioEstado = servicioEstado;
    }

    public String generarReporteInventario() {
        ArrayList<Activo> activos = lector.listarTodos();
        StringBuilder reporte = new StringBuilder();

        reporte.append("=== REPORTE DE INVENTARIO ===\n");
        reporte.append("Total de activos: ").append(activos.size()).append("\n\n");

        for (Activo a : activos) {
            reporte.append(a.getId())
                   .append(" | ").append(a.getNombre())
                   .append(" | ").append(servicioEstado.evaluarActivo(a))
                   .append("\n");
        }
        return reporte.toString();
    }

    public String generarReporteCostos() {
        ArrayList<Activo> activos = lector.listarTodos();
        StringBuilder reporte = new StringBuilder();

        reporte.append("=== REPORTE DE COSTOS ===\n\n");

        for (Activo a : activos) {
            reporte.append(a.getId())
                   .append(" | ").append(a.getNombre())
                   .append(" | ").append(String.format("%.2f", a.calcularCosto()))
                   .append(" | depreciacion ")
                   .append(String.format("%.2f", servicioCosto.calcularDepreciacion(a)))
                   .append("\n");
        }

        reporte.append("\nTOTAL: ")
               .append(String.format("%.2f", servicioCosto.calcularCostoTotal(activos)));
        return reporte.toString();
    }

    public String generarReporteAlertas() {
        ArrayList<String> alertas = servicioEstado.generarAlertas(lector.listarTodos());
        StringBuilder reporte = new StringBuilder();

        reporte.append("=== ALERTAS ===\n\n");

        if (alertas.isEmpty()) {
            reporte.append("Sin alertas pendientes.");
            return reporte.toString();
        }

        for (String alerta : alertas) {
            reporte.append(alerta).append("\n");
        }
        return reporte.toString();
    }
}