/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Model.Activo;

/**
 *
 * @author Pablo
 */
public interface EscrituraActivos {

    void guardar(Activo activo);

    void actualizar(Activo activo);

    void eliminar(String id);

}
