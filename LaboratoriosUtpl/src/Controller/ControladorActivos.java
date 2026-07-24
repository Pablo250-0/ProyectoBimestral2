package Controller;

import DAO.LecturaActivos;
import DAO.EscrituraActivos;
import Model.Activo;
import java.util.ArrayList;

/**
 * @author Pablo
 */
public class ControladorActivos {

    private final LecturaActivos lector;
    private final EscrituraActivos escritor;

    public ControladorActivos(LecturaActivos lector, EscrituraActivos escritor) {
        this.lector = lector;
        this.escritor = escritor;
    }

    public void registrarActivo(Activo activo) {
        escritor.guardar(activo);
    }

    public void actualizarActivo(Activo activo) {
        escritor.actualizar(activo);
    }

    public void eliminarActivo(String id) {
        escritor.eliminar(id);
    }

    public Activo buscarActivo(String id) {
        return lector.buscarActivo(id);
    }

    public ArrayList<Activo> listarActivos() {
        return lector.listarTodos();
    }
}