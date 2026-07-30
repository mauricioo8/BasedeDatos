
package basedatosjava.dao;

import basedatosjava.ConexionBD;
import basedatosjava.modelo.Nota;
 
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
 
/**
 * Clase encargada de toda la comunicación con la tabla NOTAS.
 * Cada método abre su propia conexión y la cierra con try-with-resources
 * para no dejar conexiones abiertas.
 */
public class NotaDAO {
 
    // ---------- REGISTRAR ----------
    public boolean registrar(Nota nota) {
        String sql = "INSERT INTO notas (matricula_id, descripcion, valor, fecha) VALUES (?, ?, ?, ?)";
 
        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {
 
            ps.setInt(1, nota.getMatriculaId());
            ps.setString(2, nota.getDescripcion());
            ps.setDouble(3, nota.getValor());
            ps.setDate(4, Date.valueOf(nota.getFecha()));
 
            int filas = ps.executeUpdate();
            return filas > 0;
 
        } catch (SQLException e) {
            System.out.println("Error al registrar nota: " + e.getMessage());
            System.out.println("Sugerencia: revisa que el ID de matrícula exista.");
            return false;
        }
    }
 
    // ---------- EDITAR ----------
    public boolean editar(Nota nota) {
        String sql = "UPDATE notas SET descripcion = ?, valor = ?, fecha = ? WHERE id = ?";
 
        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {
 
            ps.setString(1, nota.getDescripcion());
            ps.setDouble(2, nota.getValor());
            ps.setDate(3, Date.valueOf(nota.getFecha()));
            ps.setInt(4, nota.getId());
 
            int filas = ps.executeUpdate();
            return filas > 0;
 
        } catch (SQLException e) {
            System.out.println("Error al editar nota: " + e.getMessage());
            return false;
        }
    }
 
    // ---------- ELIMINAR ----------
    public boolean eliminar(int id) {
        String sql = "DELETE FROM notas WHERE id = ?";
 
        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {
 
            ps.setInt(1, id);
            int filas = ps.executeUpdate();
            return filas > 0;
 
        } catch (SQLException e) {
            System.out.println("Error al eliminar nota: " + e.getMessage());
            return false;
        }
    }
 
    // ---------- NOTAS DE UNA MATRÍCULA ----------
    public List<Nota> listarPorMatricula(int matriculaId) {
        List<Nota> resultado = new ArrayList<>();
        String sql = "SELECT * FROM notas WHERE matricula_id = ? ORDER BY fecha";
 
        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {
 
            ps.setInt(1, matriculaId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    resultado.add(mapearNota(rs));
                }
            }
 
        } catch (SQLException e) {
            System.out.println("Error al listar notas: " + e.getMessage());
        }
        return resultado;
    }
 
    // ---------- PROMEDIO DE UNA MATRÍCULA (null si aún no tiene notas) ----------
    public Double calcularPromedio(int matriculaId) {
        String sql = "SELECT AVG(valor) AS promedio FROM notas WHERE matricula_id = ?";
 
        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {
 
            ps.setInt(1, matriculaId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    double promedio = rs.getDouble("promedio");
                    return rs.wasNull() ? null : promedio;
                }
            }
 
        } catch (SQLException e) {
            System.out.println("Error al calcular promedio: " + e.getMessage());
        }
        return null;
    }
 
    // ---------- Método auxiliar: convierte una fila del ResultSet en un objeto Nota ----------
    private Nota mapearNota(ResultSet rs) throws SQLException {
        Nota nota = new Nota();
        nota.setId(rs.getInt("id"));
        nota.setMatriculaId(rs.getInt("matricula_id"));
        nota.setDescripcion(rs.getString("descripcion"));
        nota.setValor(rs.getDouble("valor"));
        Date fecha = rs.getDate("fecha");
        if (fecha != null) {
            nota.setFecha(fecha.toLocalDate());
        }
        return nota;
    }
}