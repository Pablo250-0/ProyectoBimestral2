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

    ArrayList<Activo> listaActivos = new ArrayList<>();

    Activo buscarActivo(String id);

}
