/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.Activo;
import Model.Laboratorio;
import Util.ConexionSQLite;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author Pablo
 */
public class LaboratorioDAO implements ILaboratorioDAO {

    private final LecturaActivos lectorActivos;

    public LaboratorioDAO(LecturaActivos lectorActivos) {
        this.lectorActivos = lectorActivos;
    }

    @Override
    public void guardar(Laboratorio laboratorio) {
        String sql = "INSERT INTO laboratorios (codigoLab, nombreLab, edificio, aula, capacidad) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConexionSQLite.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, laboratorio.getCodigoLab());
            pstmt.setString(2, laboratorio.getNombreLab());
            pstmt.setString(3, laboratorio.getEdificio());
            pstmt.setString(4, laboratorio.getAula());
            pstmt.setInt(5, laboratorio.getCapacidad());
            pstmt.executeUpdate();

            guardarActivos(conn, laboratorio);

        } catch (SQLException e) {
            System.err.println("Error al guardar laboratorio: " + e.getMessage());
        }
    }

    @Override
    public void actualizar(Laboratorio laboratorio) {
        String sql = "UPDATE laboratorios SET nombreLab = ?, edificio = ?, aula = ?, "
                + "capacidad = ? WHERE codigoLab = ?";

        try (Connection conn = ConexionSQLite.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, laboratorio.getNombreLab());
            pstmt.setString(2, laboratorio.getEdificio());
            pstmt.setString(3, laboratorio.getAula());
            pstmt.setInt(4, laboratorio.getCapacidad());
            pstmt.setString(5, laboratorio.getCodigoLab());
            pstmt.executeUpdate();

            borrarActivos(conn, laboratorio.getCodigoLab());
            guardarActivos(conn, laboratorio);

        } catch (SQLException e) {
            System.err.println("Error al actualizar laboratorio: " + e.getMessage());
        }
    }

    @Override
    public Laboratorio buscar(String codigoLab) {
        String sql = "SELECT * FROM laboratorios WHERE codigoLab = ?";

        try (Connection conn = ConexionSQLite.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, codigoLab);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return construirLaboratorio(rs);
                }
                return null;
            }

        } catch (SQLException e) {
            System.err.println("Error al buscar laboratorio: " + e.getMessage());
            return null;
        }
    }

    @Override
    public ArrayList<Laboratorio> listar() {
        ArrayList<Laboratorio> lista = new ArrayList<>();
        String sql = "SELECT * FROM laboratorios ORDER BY codigoLab";

        try (Connection conn = ConexionSQLite.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                lista.add(construirLaboratorio(rs));
            }

        } catch (SQLException e) {
            System.err.println("Error al listar laboratorios: " + e.getMessage());
        }
        return lista;
    }

    // ---------- apoyo ----------

    private void guardarActivos(Connection conn, Laboratorio laboratorio) throws SQLException {
        if (laboratorio.getActivos() == null || laboratorio.getActivos().isEmpty()) {
            return;
        }

        String sql = "INSERT INTO laboratorio_activos (codigoLab, idActivo) VALUES (?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            for (Activo a : laboratorio.getActivos()) {
                pstmt.setString(1, laboratorio.getCodigoLab());
                pstmt.setString(2, a.getId());
                pstmt.addBatch();
            }
            pstmt.executeBatch();
        }
    }

    private void borrarActivos(Connection conn, String codigoLab) throws SQLException {
        String sql = "DELETE FROM laboratorio_activos WHERE codigoLab = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, codigoLab);
            pstmt.executeUpdate();
        }
    }

    private ArrayList<Activo> cargarActivos(String codigoLab) {
        ArrayList<Activo> activos = new ArrayList<>();
        String sql = "SELECT idActivo FROM laboratorio_activos WHERE codigoLab = ?";

        try (Connection conn = ConexionSQLite.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, codigoLab);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Activo a = lectorActivos.buscarActivo(rs.getString("idActivo"));
                    if (a != null) {
                        activos.add(a);
                    }
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al cargar activos del laboratorio: " + e.getMessage());
        }
        return activos;
    }

    private Laboratorio construirLaboratorio(ResultSet rs) throws SQLException {
        String codigoLab = rs.getString("codigoLab");
        return new Laboratorio(
                codigoLab,
                rs.getString("nombreLab"),
                rs.getString("edificio"),
                rs.getString("aula"),
                rs.getInt("capacidad"),
                cargarActivos(codigoLab));
    }
}