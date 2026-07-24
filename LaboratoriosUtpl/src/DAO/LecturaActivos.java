package DAO;

import Model.Activo;
import java.util.ArrayList;

/**
 * @author Pablo
 */
public interface LecturaActivos {

    Activo buscarActivo(String id);

    ArrayList<Activo> listarTodos();
}