/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

/**
 *
 * @author Pablo
 */
package Controller;

import Model.Activo;
import java.util.ArrayList;

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

    public void editarActivo(Activo activo) {
        escritor.actualizar(activo);
    }

    public void darDeBaja(String id) {
        escritor.eliminar(id);
    }

    public Activo consultarActivo(String id) {
        return lector.buscarActivo(id);
    }

    public ArrayList<Activo> listarInventario() {
        return lector.listarTodos();
    }

    public ArrayList<Activo> verificarMantenimientos() {
        ArrayList<Activo> pendientes = new ArrayList<>();
        for (Activo a : lector.listarTodos()) {
            if (a.verificarEstado().equals("MANTENIMIENTO_PENDIENTE")) {
                pendientes.add(a);
            }
        }
        return pendientes;
    }
}