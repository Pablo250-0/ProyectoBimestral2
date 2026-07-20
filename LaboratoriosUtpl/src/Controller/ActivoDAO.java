package Controller;

import Model.Activo;
import Model.Hardware;
import Model.Software;
import Model.Licencia;
import Util.ConexionSQLite;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class ActivoDAO implements EscrituraActivos, LecturaActivos {

    @Override
    public void guardar(Activo activo) {
        String sql = "INSERT INTO activos (id, tipoActivo, nombre, estado, fechaIngreso, "
                + "fechaDeBaja, areaPertenencia, costoBase, fechaProximoMantenimiento, "
                + "tiempoDeUso, ultimoMantenimiento, vidaUtil, proveedor, version, "
                + "plataforma, numeroDeInstalaciones, fechaCaducidad, tipoLicencia, tipoDeSoftware) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexionSQLite.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, activo.getId());
            pstmt.setString(2, tipoDe(activo));
            pstmt.setString(3, activo.getNombre());
            pstmt.setString(4, activo.getEstado());
            pstmt.setString(5, activo.getFechaIngreso().toString());
            pstmt.setString(6, activo.getFechaDeBaja() != null ? activo.getFechaDeBaja().toString() : null);
            pstmt.setString(7, activo.getAreaDePertenencia());
            pstmt.setDouble(8, activo.getCostoBase());

            for (int i = 9; i <= 19; i++) {
                pstmt.setObject(i, null);
            }

            if (activo instanceof Hardware h) {
                pstmt.setString(9, h.getFechaDeProximoMantenimiento().toString());
                pstmt.setInt(10, h.getTiempoDeUso());
                pstmt.setString(11, h.getUltimoMantenimiento().toString());
                pstmt.setInt(12, h.getVidaUtil());
            } else if (activo instanceof Software s) {
                pstmt.setString(13, s.getProveedor());
                pstmt.setString(14, s.getVersion());
                pstmt.setString(15, s.getPlataforma());
                pstmt.setInt(16, s.getNumeroDeInstalaciones());
                pstmt.setString(19, s.getTipoDeSoftware());
            } else if (activo instanceof Licencia l) {
                pstmt.setString(13, l.getProveedor());
                pstmt.setString(14, l.getVersion());
                pstmt.setString(17, l.getFechaDeCaducidad() != null ? l.getFechaDeCaducidad().toString() : null);
                pstmt.setString(18, l.getTipo());
            }

            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error al guardar activo: " + e.getMessage());
        }
    }

    private String tipoDe(Activo activo) {
        if (activo instanceof Hardware) return "HARDWARE";
        if (activo instanceof Software) return "SOFTWARE";
        return "LICENCIA";
    }

    @Override
    public void actualizar(Activo activo) {
        String sql = "UPDATE activos SET nombre = ?, estado = ?, fechaIngreso = ?, "
                + "fechaDeBaja = ?, areaPertenencia = ?, costoBase = ?, "
                + "fechaProximoMantenimiento = ?, tiempoDeUso = ?, ultimoMantenimiento = ?, "
                + "vidaUtil = ?, proveedor = ?, version = ?, plataforma = ?, "
                + "numeroDeInstalaciones = ?, fechaCaducidad = ?, tipoLicencia = ?, "
                + "tipoDeSoftware = ? WHERE id = ?";

        try (Connection conn = ConexionSQLite.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, activo.getNombre());
            pstmt.setString(2, activo.getEstado());
            pstmt.setString(3, activo.getFechaIngreso().toString());
            pstmt.setString(4, activo.getFechaDeBaja() != null ? activo.getFechaDeBaja().toString() : null);
            pstmt.setString(5, activo.getAreaDePertenencia());
            pstmt.setDouble(6, activo.getCostoBase());

            for (int i = 7; i <= 17; i++) {
                pstmt.setObject(i, null);
            }

            if (activo instanceof Hardware h) {
                pstmt.setString(7, h.getFechaDeProximoMantenimiento().toString());
                pstmt.setInt(8, h.getTiempoDeUso());
                pstmt.setString(9, h.getUltimoMantenimiento().toString());
                pstmt.setInt(10, h.getVidaUtil());
            } else if (activo instanceof Software s) {
                pstmt.setString(11, s.getProveedor());
                pstmt.setString(12, s.getVersion());
                pstmt.setString(13, s.getPlataforma());
                pstmt.setInt(14, s.getNumeroDeInstalaciones());
                pstmt.setString(17, s.getTipoDeSoftware());
            } else if (activo instanceof Licencia l) {
                pstmt.setString(11, l.getProveedor());
                pstmt.setString(12, l.getVersion());
                pstmt.setString(15, l.getFechaDeCaducidad() != null ? l.getFechaDeCaducidad().toString() : null);
                pstmt.setString(16, l.getTipo());
            }

            pstmt.setString(18, activo.getId());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error al actualizar activo: " + e.getMessage());
        }
    }

    @Override
    public void eliminar(String id) {
        String sql = "DELETE FROM activos WHERE id = ?";

        try (Connection conn = ConexionSQLite.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, id);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error al eliminar activo: " + e.getMessage());
        }
    }

    @Override
    public Activo buscarActivo(String id) {
        String sql = "SELECT * FROM activos WHERE id = ?";

        try (Connection conn = ConexionSQLite.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return construirActivo(rs);
            }
            return null;

        } catch (SQLException e) {
            System.err.println("Error al buscar activo: " + e.getMessage());
            return null;
        }
    }

    @Override
    public ArrayList<Activo> listarTodos() {
        ArrayList<Activo> lista = new ArrayList<>();
        String sql = "SELECT * FROM activos";

        try (Connection conn = ConexionSQLite.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Activo a = construirActivo(rs);
                if (a != null) {
                    lista.add(a);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al listar activos: " + e.getMessage());
        }
        return lista;
    }

private Activo construirActivo(ResultSet rs) throws SQLException {
        String tipo = rs.getString("tipoActivo");

        String id = rs.getString("id");
        String nombre = rs.getString("nombre");
        String estado = rs.getString("estado");
        LocalDate fechaIngreso = LocalDate.parse(rs.getString("fechaIngreso"));
        LocalDate fechaDeBaja = rs.getString("fechaDeBaja") != null
                ? LocalDate.parse(rs.getString("fechaDeBaja")) : null;
        String area = rs.getString("areaPertenencia");
        double costoBase = rs.getDouble("costoBase");

        switch (tipo) {
            case "HARDWARE":
                return new Hardware(
                    LocalDate.parse(rs.getString("fechaProximoMantenimiento")),
                    rs.getInt("tiempoDeUso"),
                    LocalDate.parse(rs.getString("ultimoMantenimiento")),
                    rs.getInt("vidaUtil"),
                    nombre, id, estado, fechaIngreso, fechaDeBaja, area, costoBase
                );
            case "SOFTWARE":
                return new Software(
                    rs.getString("plataforma"),
                    rs.getInt("numeroDeInstalaciones"),
                    rs.getString("tipoDeSoftware"),
                    null,                                    // licencia asociada: no se persiste aún
                    rs.getString("proveedor"), rs.getString("version"),
                    nombre, id, estado, fechaIngreso, fechaDeBaja, area, costoBase
                );
            case "LICENCIA":
                return new Licencia(
                    rs.getString("tipoLicencia"),
                    LocalDate.parse(rs.getString("fechaCaducidad")),
                    rs.getString("proveedor"), rs.getString("version"),
                    nombre, id, estado, fechaIngreso, fechaDeBaja, area, costoBase
                );
        }
        return null;
    }
}