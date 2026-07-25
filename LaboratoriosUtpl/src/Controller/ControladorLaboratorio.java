/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import DAO.ILaboratorioDAO;
import Model.Laboratorio;
import java.util.ArrayList;

/**
 * @author Pablo
 */
public class ControladorLaboratorio {

    private final ILaboratorioDAO laboratorioDAO;

    public ControladorLaboratorio(ILaboratorioDAO laboratorioDAO) {
        this.laboratorioDAO = laboratorioDAO;
    }

    public void registrarLaboratorio(Laboratorio laboratorio) {
        laboratorioDAO.guardar(laboratorio);
    }

    public void actualizarLaboratorio(Laboratorio laboratorio) {
        laboratorioDAO.actualizar(laboratorio);
    }

    public Laboratorio buscarLaboratorio(String codigoLab) {
        return laboratorioDAO.buscar(codigoLab);
    }

    public ArrayList<Laboratorio> listarLaboratorios() {
        return laboratorioDAO.listar();
    }
}