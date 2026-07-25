package DAO;

import Model.Activo;
import Model.Laboratorio;
import Model.Mantenimiento;
import Model.Usuario;
import java.util.ArrayList;

public class DatosMemoria {

    private final ArrayList<Activo> listaActivos = new ArrayList<>();
    private final ArrayList<Laboratorio> listaLaboratorios = new ArrayList<>();
    private final ArrayList<Mantenimiento> listaMantenimientos = new ArrayList<>();
    private final ArrayList<Usuario> listaUsuarios = new ArrayList<>();

    public final Activos activos = new Activos();
    public final Laboratorios laboratorios = new Laboratorios();
    public final Mantenimientos mantenimientos = new Mantenimientos();
    public final Usuarios usuarios = new Usuarios();

    public class Activos implements LecturaActivos, EscrituraActivos {

        @Override
        public void guardar(Activo activo) {
            if (buscarActivo(activo.getId()) != null) {
                throw new IllegalArgumentException("Ya existe un activo con ese ID.");
            }
            listaActivos.add(activo);
        }

        @Override
        public void actualizar(Activo activo) {
            for (int i = 0; i < listaActivos.size(); i++) {
                if (listaActivos.get(i).getId().equalsIgnoreCase(activo.getId())) {
                    listaActivos.set(i, activo);
                    return;
                }
            }
            throw new IllegalArgumentException("No se encontró el activo.");
        }

        @Override
        public void eliminar(String id) {
            listaActivos.removeIf(a -> a.getId().equalsIgnoreCase(id));
        }

        @Override
        public Activo buscarActivo(String id) {
            for (Activo activo : listaActivos) {
                if (activo.getId().equalsIgnoreCase(id)) {
                    return activo;
                }
            }
            return null;
        }

        @Override
        public ArrayList<Activo> listarTodos() {
            return new ArrayList<>(listaActivos);
        }
    }

    public class Laboratorios implements ILaboratorioDAO {

        @Override
        public void guardar(Laboratorio laboratorio) {
            if (buscar(laboratorio.getCodigoLab()) != null) {
                throw new IllegalArgumentException("Ya existe un laboratorio con ese código.");
            }
            listaLaboratorios.add(laboratorio);
        }

        @Override
        public void actualizar(Laboratorio laboratorio) {
            for (int i = 0; i < listaLaboratorios.size(); i++) {
                if (listaLaboratorios.get(i).getCodigoLab()
                        .equalsIgnoreCase(laboratorio.getCodigoLab())) {
                    listaLaboratorios.set(i, laboratorio);
                    return;
                }
            }
            throw new IllegalArgumentException("No se encontró el laboratorio.");
        }

        @Override
        public Laboratorio buscar(String codigo) {
            for (Laboratorio laboratorio : listaLaboratorios) {
                if (laboratorio.getCodigoLab().equalsIgnoreCase(codigo)) {
                    return laboratorio;
                }
            }
            return null;
        }

        @Override
        public ArrayList<Laboratorio> listar() {
            return new ArrayList<>(listaLaboratorios);
        }
    }

    public class Mantenimientos implements IMantenimientoDAO {

        @Override
        public void guardar(Mantenimiento mantenimiento) {
            listaMantenimientos.add(mantenimiento);
        }

        @Override
        public ArrayList<Mantenimiento> listarHistorial(String idActivo) {
            ArrayList<Mantenimiento> resultado = new ArrayList<>();
            for (Mantenimiento mantenimiento : listaMantenimientos) {
                if (mantenimiento.getActivo() != null
                        && mantenimiento.getActivo().getId().equalsIgnoreCase(idActivo)) {
                    resultado.add(mantenimiento);
                }
            }
            return resultado;
        }

        @Override
        public ArrayList<Mantenimiento> listarTodos() {
            return new ArrayList<>(listaMantenimientos);
        }
    }

    public class Usuarios implements IUsuarioDAO {

        @Override
        public void guardar(Usuario usuario) {
            if (buscar(usuario.getCodigoUsuario()) != null) {
                throw new IllegalArgumentException("Ya existe un usuario con ese código.");
            }
            listaUsuarios.add(usuario);
        }

        @Override
        public void actualizar(Usuario usuario) {
            for (int i = 0; i < listaUsuarios.size(); i++) {
                if (listaUsuarios.get(i).getCodigoUsuario()
                        .equalsIgnoreCase(usuario.getCodigoUsuario())) {
                    listaUsuarios.set(i, usuario);
                    return;
                }
            }
            throw new IllegalArgumentException("No se encontró el usuario.");
        }

        @Override
        public void eliminar(String codigo) {
            listaUsuarios.removeIf(u -> u.getCodigoUsuario().equalsIgnoreCase(codigo));
        }

        @Override
        public Usuario buscar(String codigo) {
            for (Usuario usuario : listaUsuarios) {
                if (usuario.getCodigoUsuario().equalsIgnoreCase(codigo)) {
                    return usuario;
                }
            }
            return null;
        }

        @Override
        public ArrayList<Usuario> listar() {
            return new ArrayList<>(listaUsuarios);
        }
    }

}
