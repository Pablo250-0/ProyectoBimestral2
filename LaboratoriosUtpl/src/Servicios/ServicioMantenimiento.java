package Service;

import DAO.IMantenimientoDAO;
import Model.EstadoM;
import Model.Hardware;
import Model.Mantenimiento;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * @author Pablo
 */
public class ServicioMantenimiento {

    /** Meses que se dejan entre un mantenimiento preventivo y el siguiente. */
    private static final int MESES_ENTRE_MANTENIMIENTOS = 6;

    private final IMantenimientoDAO mantenimientoDAO;
    private final ServicioCosto servicioCosto;

    public ServicioMantenimiento(IMantenimientoDAO mantenimientoDAO,
                                 ServicioCosto servicioCosto) {
        this.mantenimientoDAO = mantenimientoDAO;
        this.servicioCosto = servicioCosto;
    }

    public Mantenimiento crearMantenimiento(String idMantenimiento, Hardware activo,
                                            String tipo, String descripcion) {

        double costo = servicioCosto.calcularCostoMantenimiento(activo);

        Mantenimiento mantenimiento = new Mantenimiento(
                idMantenimiento, tipo, descripcion, costo,
                EstadoM.MANTENIMIENTO, LocalDate.now(), activo);

        mantenimientoDAO.guardar(mantenimiento);

        activo.setUltimoMantenimiento(LocalDate.now());
        activo.setFechaDeProximoMantenimiento(
                LocalDate.now().plusMonths(MESES_ENTRE_MANTENIMIENTOS));

        return mantenimiento;
    }

    public ArrayList<Mantenimiento> obtenerHistorial(String idActivo) {
        return mantenimientoDAO.listarHistorial(idActivo);
    }

    public double calcularCostoPreventivo(ArrayList<Hardware> activos) {
        double total = 0;
        for (Hardware h : activos) {
            if (h.requiereMantenimiento()) {
                total += servicioCosto.calcularCostoMantenimiento(h);
            }
        }
        return total;
    }

    public double calcularCostoHistorico(String idActivo) {
        double total = 0;
        for (Mantenimiento m : mantenimientoDAO.listarHistorial(idActivo)) {
            total += m.getCosto();
        }
        return total;
    }
}