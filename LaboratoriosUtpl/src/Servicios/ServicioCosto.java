package Service;

import Model.Activo;
import Model.Hardware;
import java.util.ArrayList;

/**
 * @author Pablo
 */
public class ServicioCosto {

    /** Porcentaje del costo base que se estima por mantenimiento. */
    private static final double TASA_MANTENIMIENTO = 0.15;

    /** Recargo si el equipo ya paso su fecha de mantenimiento. */
    private static final double RECARGO_ATRASO = 0.10;

    public double calcularCostoTotal(ArrayList<Activo> activos) {
        double total = 0;
        for (Activo a : activos) {
            total += a.calcularCosto();
        }
        return total;
    }

    public double calcularCostoMantenimiento(Hardware activo) {
        if (activo == null) {
            return 0;
        }

        double costo = activo.getCostoBase() * TASA_MANTENIMIENTO;

        if (activo.requiereMantenimiento()) {
            costo += activo.getCostoBase() * RECARGO_ATRASO;
        }

        int vidaUtil = activo.getVidaUtil();
        if (vidaUtil > 0) {
            double desgaste = (double) activo.getTiempoDeUso() / vidaUtil;
            costo += activo.getCostoBase() * Math.min(desgaste, 1.0) * TASA_MANTENIMIENTO;
        }

        return costo;
    }

    public double calcularDepreciacion(Activo activo) {
        return activo.getCostoBase() - activo.calcularCosto();
    }
}