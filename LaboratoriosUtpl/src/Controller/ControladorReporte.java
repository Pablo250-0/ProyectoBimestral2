/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import DAO.LecturaActivos;
import Model.Activo;
import java.util.ArrayList;

/**
 * @author Pablo
 */
public class ControladorReporte {

    private final LecturaActivos lector;

    public ControladorReporte(LecturaActivos lector) {
        this.lector = lector;
    }

    public String generarReporteInventario() {
        ArrayList<Activo> activos = lector.listarTodos();
        StringBuilder reporte = new StringBuilder();

        reporte.append("=== REPORTE DE INVENTARIO ===\n");
        reporte.append("Total de activos: ").append(activos.size()).append("\n\n");

        for (Activo a : activos) {
            reporte.append(a.getId())
                   .append(" | ").append(a.getNombre())
                   .append(" | ").append(a.getEstado())
                   .append("\n");
        }
        return reporte.toString();
    }

    public String generarReporteCostos() {
        ArrayList<Activo> activos = lector.listarTodos();
        StringBuilder reporte = new StringBuilder();
        double total = 0;

        reporte.append("=== REPORTE DE COSTOS ===\n\n");

        for (Activo a : activos) {
            double costo = a.calcularCosto();
            total += costo;
            reporte.append(a.getId())
                   .append(" | ").append(a.getNombre())
                   .append(" | ").append(String.format("%.2f", costo))
                   .append("\n");
        }

        reporte.append("\nTOTAL: ").append(String.format("%.2f", total));
        return reporte.toString();
    }
}