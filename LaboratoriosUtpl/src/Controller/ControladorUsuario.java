/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import DAO.IUsuarioDAO;
import Model.Usuario;
import java.util.ArrayList;

/**
 * @author Pablo
 */
public class ControladorUsuario {

    private final IUsuarioDAO usuarioDAO;

    public ControladorUsuario(IUsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    public void registrarUsuario(Usuario usuario) {
        usuarioDAO.guardar(usuario);
    }

    public void actualizarUsuario(Usuario usuario) {
        usuarioDAO.actualizar(usuario);
    }

    public void eliminarUsuario(String codigoUsuario) {
        usuarioDAO.eliminar(codigoUsuario);
    }

    public Usuario buscarUsuario(String codigoUsuario) {
        return usuarioDAO.buscar(codigoUsuario);
    }

    public ArrayList<Usuario> listarUsuarios() {
        return usuarioDAO.listar();
    }
}
