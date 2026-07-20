import Controller.ActivoDAO;
import Controller.ControladorActivos;
import Model.*;
import java.time.LocalDate;

public class TestIntegracion {
    public static void main(String[] args) {
        ActivoDAO dao = new ActivoDAO();
        ControladorActivos ctrl = new ControladorActivos(dao, dao);

        // limpiar restos de pruebas anteriores
        dao.eliminar("HW-TEST");
        dao.eliminar("SW-TEST");
        dao.eliminar("LC-TEST");

        System.out.println("=== 1. GUARDAR (C) ===");
        Hardware hw = new Hardware(
                LocalDate.now().minusDays(10),  // proximo mantenimiento YA VENCIDO
                120, LocalDate.now().minusMonths(6), 1825,
                "Proyector Epson X41", "HW-TEST", "Operativo",
                LocalDate.of(2024, 3, 15), null, "Aula 301", 450.0);
        ctrl.registrarActivo(hw);

        Licencia lc = new Licencia("Educativa", LocalDate.now().plusYears(1),
                "MathWorks", "R2025b", "Licencia MATLAB", "LC-TEST", "Activa",
                LocalDate.of(2025, 1, 10), null, "Lab Simulacion", 2150.0);
        ctrl.registrarActivo(lc);

        Software sw = new Software("Windows", 35, "Propietario", lc,
                "MathWorks", "R2025b", "MATLAB", "SW-TEST", "Disponible",
                LocalDate.of(2025, 1, 10), null, "Lab Simulacion", 0.0);
        ctrl.registrarActivo(sw);
        System.out.println("3 activos guardados");

        System.out.println("\n=== 2. BUSCAR (R) — el switch en accion ===");
        Activo recuperado = ctrl.consultarActivo("HW-TEST");
        System.out.println("Clase reconstruida: " + recuperado.getClass().getSimpleName());
        System.out.println("Nombre: " + recuperado.getNombre());
        System.out.println("Estado (polimorfico): " + recuperado.verificarEstado());
        System.out.println("Costo (polimorfico): $" + recuperado.calculoDeCosto());

        System.out.println("\n=== 3. LISTAR ===");
        for (Activo a : ctrl.listarInventario()) {
            System.out.println("  " + a.getClass().getSimpleName() + " | " + a.getId()
                    + " | " + a.getNombre() + " | " + a.verificarEstado());
        }

        System.out.println("\n=== 4. ACTUALIZAR (U) ===");
        recuperado.setNombre("Proyector Epson X41 (reubicado)");
        recuperado.setAreaDePertenencia("Aula 305");
        ctrl.editarActivo(recuperado);
        System.out.println("Tras editar: " + ctrl.consultarActivo("HW-TEST").getNombre()
                + " en " + ctrl.consultarActivo("HW-TEST").getAreaDePertenencia());

        System.out.println("\n=== 5. VERIFICAR MANTENIMIENTOS (el flujo del enunciado) ===");
        for (Activo a : ctrl.verificarMantenimientos()) {
            System.out.println("  PENDIENTE: " + a.getNombre() + " -> " + a.verificarEstado());
        }

        System.out.println("\n=== 6. ELIMINAR (D) ===");
        ctrl.darDeBaja("HW-TEST");
        System.out.println("Tras eliminar, buscar HW-TEST devuelve: " + ctrl.consultarActivo("HW-TEST"));
        ctrl.darDeBaja("SW-TEST");
        ctrl.darDeBaja("LC-TEST");

        System.out.println("\n*** CRUD COMPLETO FUNCIONANDO ***");
    }
}