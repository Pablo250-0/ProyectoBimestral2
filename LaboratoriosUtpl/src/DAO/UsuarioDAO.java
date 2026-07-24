/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.CarreraIngenieria;
import Model.Rol;
import Model.Usuario;
import Util.ConexionSQLite;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author Pablo
 */
public class UsuarioDAO implements IUsuarioDAO {

    @Override
    public void guardar(Usuario usuario) {
        String sql = "INSERT INTO usuarios (codigoUsuario, cedula, nombres, telefono, "
                + "direccion, correo, rol, codigoCarrera) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexionSQLite.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            guardarCarrera(conn, usuario.getCarrera());
            asignarParametros(pstmt, usuario);
            pstmt.setString(1, usuario.getCodigoUsuario());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error al guardar usuario: " + e.getMessage());
        }
    }

    @Override
    public void actualizar(Usuario usuario) {
        String sql = "UPDATE usuarios SET cedula = ?, nombres = ?, telefono = ?, "
                + "direccion = ?, correo = ?, rol = ?, codigoCarrera = ? "
                + "WHERE codigoUsuario = ?";

        try (Connection conn = ConexionSQLite.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            guardarCarrera(conn, usuario.getCarrera());

            pstmt.setString(1, usuario.getCedula());
            pstmt.setString(2, usuario.getNombres());
            pstmt.setString(3, usuario.getTelefono());
            pstmt.setString(4, usuario.getDireccion());
            pstmt.setString(5, usuario.getCorreo());
            pstmt.setString(6, usuario.getRol() != null ? usuario.getRol().name() : null);
            pstmt.setString(7, usuario.getCarrera() != null
                    ? usuario.getCarrera().getCodigo() : null);
            pstmt.setString(8, usuario.getCodigoUsuario());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error al actualizar usuario: " + e.getMessage());
        }
    }

    @Override
    public void eliminar(String codigoUsuario) {
        String sql = "DELETE FROM usuarios WHERE codigoUsuario = ?";

        try (Connection conn = ConexionSQLite.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, codigoUsuario);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error al eliminar usuario: " + e.getMessage());
        }
    }

    @Override
    public Usuario buscar(String codigoUsuario) {
        String sql = "SELECT u.*, c.nombre AS carreraNombre, c.facultad AS carreraFacultad "
                + "FROM usuarios u LEFT JOIN carreras c ON u.codigoCarrera = c.codigo "
                + "WHERE u.codigoUsuario = ?";

        try (Connection conn = ConexionSQLite.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, codigoUsuario);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return construirUsuario(rs);
                }
                return null;
            }

        } catch (SQLException e) {
            System.err.println("Error al buscar usuario: " + e.getMessage());
            return null;
        }
    }

    @Override
    public ArrayList<Usuario> listar() {
        ArrayList<Usuario> lista = new ArrayList<>();
        String sql = "SELECT u.*, c.nombre AS carreraNombre, c.facultad AS carreraFacultad "
                + "FROM usuarios u LEFT JOIN carreras c ON u.codigoCarrera = c.codigo "
                + "ORDER BY u.codigoUsuario";

        try (Connection conn = ConexionSQLite.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                lista.add(construirUsuario(rs));
            }

        } catch (SQLException e) {
            System.err.println("Error al listar usuarios: " + e.getMessage());
        }
        return lista;
    }

    // ---------- apoyo ----------

    private void asignarParametros(PreparedStatement pstmt, Usuario usuario) throws SQLException {
        pstmt.setString(2, usuario.getCedula());
        pstmt.setString(3, usuario.getNombres());
        pstmt.setString(4, usuario.getTelefono());
        pstmt.setString(5, usuario.getDireccion());
        pstmt.setString(6, usuario.getCorreo());
        pstmt.setString(7, usuario.getRol() != null ? usuario.getRol().name() : null);
        pstmt.setString(8, usuario.getCarrera() != null
                ? usuario.getCarrera().getCodigo() : null);
    }

    private void guardarCarrera(Connection conn, CarreraIngenieria carrera) throws SQLException {
        if (carrera == null) {
            return;
        }

        String sql = "INSERT OR REPLACE INTO carreras (codigo, nombre, facultad) VALUES (?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, carrera.getCodigo());
            pstmt.setString(2, carrera.getNombre());
            pstmt.setString(3, carrera.getFacultad());
            pstmt.executeUpdate();
        }
    }

    private Usuario construirUsuario(ResultSet rs) throws SQLException {
        CarreraIngenieria carrera = null;
        String codigoCarrera = rs.getString("codigoCarrera");

        if (codigoCarrera != null) {
            carrera = new CarreraIngenieria(codigoCarrera,
                    rs.getString("carreraNombre"),
                    rs.getString("carreraFacultad"));
        }

        String rol = rs.getString("rol");

        return new Usuario(
                rs.getString("codigoUsuario"),
                carrera,
                rs.getString("correo"),
                rol != null ? Rol.valueOf(rol) : null,
                rs.getString("cedula"),
                rs.getString("nombres"),
                rs.getString("telefono"),
                rs.getString("direccion"));
    }
}