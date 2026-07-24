/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.Mantenimiento;
import java.util.ArrayList;

/**
 * @author Pablo
 */
public interface IMantenimientoDAO {

    void guardar(Mantenimiento mantenimiento);

    ArrayList<Mantenimiento> listarHistorial(String idActivo);

    ArrayList<Mantenimiento> listarTodos();
}