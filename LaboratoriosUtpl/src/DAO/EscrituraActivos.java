package DAO;

import Model.Activo;

/**
 * @author Pablo
 */
public interface EscrituraActivos {

    void guardar(Activo activo);

    void actualizar(Activo activo);

    void eliminar(String id);
}