/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.Laboratorio;
import java.util.ArrayList;

/**
 * @author Pablo
 */
public interface ILaboratorioDAO {

    void guardar(Laboratorio laboratorio);

    void actualizar(Laboratorio laboratorio);

    Laboratorio buscar(String codigoLab);

    ArrayList<Laboratorio> listar();
}