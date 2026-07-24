/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import DAO.IMantenimientoDAO;
import Model.EstadoM;
import Model.Mantenimiento;
import java.util.ArrayList;

/**
 * @author Pablo
 */
public class ControladorMantenimiento {

    private final IMantenimientoDAO mantenimientoDAO;

    public ControladorMantenimiento(IMantenimientoDAO mantenimientoDAO) {
        this.mantenimientoDAO = mantenimientoDAO;
    }

    public void registrarMantenimiento(Mantenimiento mantenimiento) {
        mantenimientoDAO.guardar(mantenimiento);
    }

    public ArrayList<Mantenimiento> consultarHistorial(String idActivo) {
        return mantenimientoDAO.listarHistorial(idActivo);
    }

    public ArrayList<Mantenimiento> listarPendientes() {
        ArrayList<Mantenimiento> pendientes = new ArrayList<>();
        for (Mantenimiento m : mantenimientoDAO.listarTodos()) {
            if (m.getEstado() == EstadoM.MANTENIMIENTO) {
                pendientes.add(m);
            }
        }
        return pendientes;
    }
}