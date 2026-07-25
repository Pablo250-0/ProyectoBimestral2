/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.Activo;
import Model.EstadoM;
import Model.Hardware;
import Model.Mantenimiento;
import Util.ConexionSQLite;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * @author Pablo
 */
public class MantenimientoDAO implements IMantenimientoDAO {

    private final LecturaActivos lectorActivos;

    public MantenimientoDAO(LecturaActivos lectorActivos) {
        this.lectorActivos = lectorActivos;
    }

    @Override
    public void guardar(Mantenimiento mantenimiento) {
        String sql = "INSERT INTO mantenimientos (idMantenimiento, tipo, descripcion, "
                + "costo, estado, fecha, idActivo) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexionSQLite.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, mantenimiento.getIdMantenimiento());
            pstmt.setString(2, mantenimiento.getTipo());
            pstmt.setString(3, mantenimiento.getDescripcion());
            pstmt.setDouble(4, mantenimiento.getCosto());
            pstmt.setString(5, mantenimiento.getEstado() != null
                    ? mantenimiento.getEstado().name() : null);
            pstmt.setString(6, mantenimiento.getFecha() != null
                    ? mantenimiento.getFecha().toString() : null);
            pstmt.setString(7, mantenimiento.getActivo() != null
                    ? mantenimiento.getActivo().getId() : null);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error al guardar mantenimiento: " + e.getMessage());
        }
    }

    @Override
    public ArrayList<Mantenimiento> listarHistorial(String idActivo) {
        String sql = "SELECT * FROM mantenimientos WHERE idActivo = ? ORDER BY fecha DESC";
        return consultar(sql, idActivo);
    }

    @Override
    public ArrayList<Mantenimiento> listarTodos() {
        String sql = "SELECT * FROM mantenimientos ORDER BY fecha DESC";
        return consultar(sql, null);
    }

    // ---------- apoyo ----------

    private ArrayList<Mantenimiento> consultar(String sql, String idActivo) {
        ArrayList<Mantenimiento> lista = new ArrayList<>();

        try (Connection conn = ConexionSQLite.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            if (idActivo != null) {
                pstmt.setString(1, idActivo);
            }

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    lista.add(construirMantenimiento(rs));
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al consultar mantenimientos: " + e.getMessage());
        }
        return lista;
    }

    private Mantenimiento construirMantenimiento(ResultSet rs) throws SQLException {
        String estado = rs.getString("estado");
        String fecha = rs.getString("fecha");

        return new Mantenimiento(
                rs.getString("idMantenimiento"),
                rs.getString("tipo"),
                rs.getString("descripcion"),
                rs.getDouble("costo"),
                estado != null ? EstadoM.valueOf(estado) : null,
                fecha != null ? LocalDate.parse(fecha) : null,
                buscarHardware(rs.getString("idActivo")));
    }

    private Hardware buscarHardware(String idActivo) {
        if (idActivo == null) {
            return null;
        }
        Activo activo = lectorActivos.buscarActivo(idActivo);
        return (activo instanceof Hardware h) ? h : null;
    }
}