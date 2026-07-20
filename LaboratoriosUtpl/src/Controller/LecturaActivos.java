/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import java.util.ArrayList;
import Model.Activo;

/**
 *
 * @author Pablo
 */
public interface LecturaActivos {

    Activo buscarActivo(String id);

    ArrayList<Activo> listarTodos();
}