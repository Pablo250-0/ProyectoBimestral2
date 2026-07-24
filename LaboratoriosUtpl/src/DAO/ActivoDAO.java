package DAO;

import Model.Activo;
import Model.Computadora;
import Model.EstadoM;
import Model.Impresora;
import Model.Licencia;
import Model.Software;
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
public class ActivoDAO implements EscrituraActivos, LecturaActivos {

    private static final String COLUMNAS =
            "tipoActivo, nombre, estado, fechaIngreso, fechaDeBaja, costoBase, "
            + "ubicacion, numeroSerie, responsable, vidaUtil, "
            + "fechaProximoMantenimiento, tiempoDeUso, ultimoMantenimiento, estadoM, "
            + "sistemaOperativo, procesador, ram, cantidadAlmacenamiento, "
            + "tipoImpresion, velocidadImpresion, nivelTinta, "
            + "proveedor, version, "
            + "plataforma, numeroDeInstalaciones, tipoDeSoftware, "
            + "licenciaTipo, licenciaFechaCaducidad, id";

    @Override
    public void guardar(Activo activo) {
        String sql = "INSERT INTO activos (" + COLUMNAS + ") "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
                + "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexionSQLite.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            asignarParametros(pstmt, activo);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error al guardar activo: " + e.getMessage());
        }
    }

    @Override
    public void actualizar(Activo activo) {
        String sql = "UPDATE activos SET "
                + "tipoActivo = ?, nombre = ?, estado = ?, fechaIngreso = ?, "
                + "fechaDeBaja = ?, costoBase = ?, ubicacion = ?, numeroSerie = ?, "
                + "responsable = ?, vidaUtil = ?, fechaProximoMantenimiento = ?, "
                + "tiempoDeUso = ?, ultimoMantenimiento = ?, estadoM = ?, "
                + "sistemaOperativo = ?, procesador = ?, ram = ?, "
                + "cantidadAlmacenamiento = ?, tipoImpresion = ?, "
                + "velocidadImpresion = ?, nivelTinta = ?, proveedor = ?, "
                + "version = ?, plataforma = ?, numeroDeInstalaciones = ?, "
                + "tipoDeSoftware = ?, licenciaTipo = ?, licenciaFechaCaducidad = ? "
                + "WHERE id = ?";

        try (Connection conn = ConexionSQLite.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            asignarParametros(pstmt, activo);
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

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return construirActivo(rs);
                }
                return null;
            }

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

    // ---------- objeto -> base de datos ----------

    private void asignarParametros(PreparedStatement pstmt, Activo activo) throws SQLException {

        pstmt.setString(1, tipoDe(activo));
        pstmt.setString(2, activo.getNombre());
        pstmt.setString(3, activo.getEstado());
        pstmt.setString(4, texto(activo.getFechaIngreso()));
        pstmt.setString(5, texto(activo.getFechaDeBaja()));
        pstmt.setDouble(6, activo.getCostoBase());

        // las columnas especificas arrancan en null y se llenan segun el tipo
        for (int i = 7; i <= 28; i++) {
            pstmt.setObject(i, null);
        }

        if (activo instanceof Computadora c) {
            asignarHardware(pstmt, c);
            pstmt.setString(15, c.getSistemaOperativo());
            pstmt.setString(16, c.getProcesador());
            pstmt.setString(17, c.getRam());
            pstmt.setInt(18, c.getCantidadAlmacenamiento());

        } else if (activo instanceof Impresora i) {
            asignarHardware(pstmt, i);
            pstmt.setString(19, i.getTipoImpresion());
            pstmt.setDouble(20, i.getVelocidadImpresion());
            pstmt.setInt(21, i.getNivelTinta());

        } else if (activo instanceof Software s) {
            pstmt.setString(22, s.getProveedor());
            pstmt.setString(23, s.getVersion());
            pstmt.setString(24, s.getPlataforma());
            pstmt.setInt(25, s.getNumeroDeInstalaciones());
            pstmt.setString(26, s.getTipoDeSoftware());

            Licencia lic = s.getLicencia();
            if (lic != null) {
                pstmt.setString(27, lic.getTipo());
                pstmt.setString(28, texto(lic.getFechaDeCaducidad()));
            }
        }

        pstmt.setString(29, activo.getId());
    }

    private void asignarHardware(PreparedStatement pstmt, Model.Hardware h) throws SQLException {
        pstmt.setString(7, h.getUbicacion());
        pstmt.setString(8, h.getNumeroSerie());
        pstmt.setString(9, h.getResponsable());
        pstmt.setInt(10, h.getVidaUtil());
        pstmt.setString(11, texto(h.getFechaDeProximoMantenimiento()));
        pstmt.setInt(12, h.getTiempoDeUso());
        pstmt.setString(13, texto(h.getUltimoMantenimiento()));
        pstmt.setString(14, h.getEstadoM() != null ? h.getEstadoM().name() : null);
    }

    private String tipoDe(Activo activo) {
        if (activo instanceof Computadora) {
            return "COMPUTADORA";
        }
        if (activo instanceof Impresora) {
            return "IMPRESORA";
        }
        if (activo instanceof Software) {
            return "SOFTWARE";
        }
        throw new IllegalArgumentException("Tipo de activo no soportado: "
                + activo.getClass().getSimpleName());
    }

    // ---------- base de datos -> objeto ----------

    private Activo construirActivo(ResultSet rs) throws SQLException {
        String tipo = rs.getString("tipoActivo");

        if (tipo == null) {
            return null;
        }

        switch (tipo) {
            case "COMPUTADORA":
                return new Computadora(
                        rs.getString("sistemaOperativo"),
                        rs.getString("procesador"),
                        rs.getString("ram"),
                        rs.getInt("cantidadAlmacenamiento"),
                        fecha(rs, "fechaProximoMantenimiento"),
                        rs.getInt("tiempoDeUso"),
                        fecha(rs, "ultimoMantenimiento"),
                        estado(rs, "estadoM"),
                        rs.getString("ubicacion"),
                        rs.getString("numeroSerie"),
                        rs.getString("responsable"),
                        rs.getInt("vidaUtil"),
                        rs.getString("nombre"),
                        rs.getString("id"),
                        rs.getString("estado"),
                        fecha(rs, "fechaIngreso"),
                        fecha(rs, "fechaDeBaja"),
                        rs.getDouble("costoBase"));

            case "IMPRESORA":
                return new Impresora(
                        rs.getString("tipoImpresion"),
                        rs.getDouble("velocidadImpresion"),
                        rs.getInt("nivelTinta"),
                        fecha(rs, "fechaProximoMantenimiento"),
                        rs.getInt("tiempoDeUso"),
                        fecha(rs, "ultimoMantenimiento"),
                        estado(rs, "estadoM"),
                        rs.getString("ubicacion"),
                        rs.getString("numeroSerie"),
                        rs.getString("responsable"),
                        rs.getInt("vidaUtil"),
                        rs.getString("nombre"),
                        rs.getString("id"),
                        rs.getString("estado"),
                        fecha(rs, "fechaIngreso"),
                        fecha(rs, "fechaDeBaja"),
                        rs.getDouble("costoBase"));

            case "SOFTWARE":
                Licencia licencia = null;
                if (rs.getString("licenciaTipo") != null) {
                    licencia = new Licencia(
                            rs.getString("licenciaTipo"),
                            fecha(rs, "licenciaFechaCaducidad"));
                }
                return new Software(
                        rs.getString("plataforma"),
                        rs.getInt("numeroDeInstalaciones"),
                        rs.getString("tipoDeSoftware"),
                        licencia,
                        rs.getString("proveedor"),
                        rs.getString("version"),
                        rs.getString("nombre"),
                        rs.getString("id"),
                        rs.getString("estado"),
                        fecha(rs, "fechaIngreso"),
                        fecha(rs, "fechaDeBaja"),
                        rs.getDouble("costoBase"));

            default:
                System.err.println("Tipo desconocido en la base: " + tipo);
                return null;
        }
    }

    // ---------- utilidades ----------

    private String texto(LocalDate fecha) {
        return fecha != null ? fecha.toString() : null;
    }

    private LocalDate fecha(ResultSet rs, String columna) throws SQLException {
        String valor = rs.getString(columna);
        return valor != null ? LocalDate.parse(valor) : null;
    }

    private EstadoM estado(ResultSet rs, String columna) throws SQLException {
        String valor = rs.getString(columna);
        return valor != null ? EstadoM.valueOf(valor) : null;
    }
}