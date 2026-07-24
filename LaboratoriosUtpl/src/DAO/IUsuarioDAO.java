/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.Usuario;
import java.util.ArrayList;

/**
 * @author Pablo
 */
public interface IUsuarioDAO {

    void guardar(Usuario usuario);

    void actualizar(Usuario usuario);

    void eliminar(String codigoUsuario);

    Usuario buscar(String codigoUsuario);

    ArrayList<Usuario> listar();
}