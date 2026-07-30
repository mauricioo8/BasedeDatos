
package basedatosjava.dao;

import basedatosjava.ConexionBD;
import basedatosjava.modelo.Curso;
 
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
 
/**
 * Clase encargada de toda la comunicación con la tabla CURSOS.
 * Cada método abre su propia conexión y la cierra con try-with-resources
 * para no dejar conexiones abiertas.
 */
public class CursoDAO {
   // ---------- REGISTRAR ----------
    public boolean registrar(Curso curso) {
        String sql = "INSERT INTO cursos (codigo, nombre, creditos, horas_semanales, docente) "
                + "VALUES (?, ?, ?, ?, ?)";
 
        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {
 
            ps.setString(1, curso.getCodigo());
            ps.setString(2, curso.getNombre());
            ps.setInt(3, curso.getCreditos());
            ps.setInt(4, curso.getHorasSemanales());
            ps.setString(5, curso.getDocente());
 
            int filas = ps.executeUpdate();
            return filas > 0;
 
        } catch (SQLException e) {
            System.out.println("Error al registrar curso: " + e.getMessage());
            return false;
        }
    }
 
    // ---------- EDITAR ----------
    public boolean editar(Curso curso) {
        String sql = "UPDATE cursos SET codigo = ?, nombre = ?, creditos = ?, "
                + "horas_semanales = ?, docente = ? WHERE id = ?";
 
        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {
 
            ps.setString(1, curso.getCodigo());
            ps.setString(2, curso.getNombre());
            ps.setInt(3, curso.getCreditos());
            ps.setInt(4, curso.getHorasSemanales());
            ps.setString(5, curso.getDocente());
            ps.setInt(6, curso.getId());
 
            int filas = ps.executeUpdate();
            return filas > 0;
 
        } catch (SQLException e) {
            System.out.println("Error al editar curso: " + e.getMessage());
            return false;
        }
    }
 
    // ---------- ELIMINAR ----------
    public boolean eliminar(int id) {
        String sql = "DELETE FROM cursos WHERE id = ?";
 
        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {
 
            ps.setInt(1, id);
            int filas = ps.executeUpdate();
            return filas > 0;
 
        } catch (SQLException e) {
            System.out.println("Error al eliminar curso: " + e.getMessage());
            System.out.println("Sugerencia: si el curso ya tiene matrículas registradas, "
                    + "puede que la base de datos impida eliminarlo por integridad referencial.");
            return false;
        }
    }
 
    // ---------- BUSCAR (por nombre, código o docente) ----------
    public List<Curso> buscar(String criterio) {
        List<Curso> resultado = new ArrayList<>();
        String sql = "SELECT * FROM cursos WHERE nombre LIKE ? OR codigo LIKE ? OR docente LIKE ? "
                + "ORDER BY nombre";
 
        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {
 
            String patron = "%" + criterio + "%";
            ps.setString(1, patron);
            ps.setString(2, patron);
            ps.setString(3, patron);
 
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    resultado.add(mapearCurso(rs));
                }
            }
 
        } catch (SQLException e) {
            System.out.println("Error al buscar cursos: " + e.getMessage());
        }
        return resultado;
    }
 
    // ---------- LISTAR TODOS ----------
    public List<Curso> listarTodos() {
        List<Curso> lista = new ArrayList<>();
        String sql = "SELECT * FROM cursos ORDER BY nombre";
 
        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
 
            while (rs.next()) {
                lista.add(mapearCurso(rs));
            }
 
        } catch (SQLException e) {
            System.out.println("Error al listar cursos: " + e.getMessage());
        }
        return lista;
    }
 
    // ---------- BUSCAR POR ID (útil para Integrante 3 al matricular) ----------
    public Curso buscarPorId(int id) {
        String sql = "SELECT * FROM cursos WHERE id = ?";
 
        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {
 
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearCurso(rs);
                }
            }
 
        } catch (SQLException e) {
            System.out.println("Error al buscar curso por id: " + e.getMessage());
        }
        return null;
    }
 
    // ---------- Método auxiliar: convierte una fila del ResultSet en un objeto Curso ----------
    private Curso mapearCurso(ResultSet rs) throws SQLException {
        Curso curso = new Curso();
        curso.setId(rs.getInt("id"));
        curso.setCodigo(rs.getString("codigo"));
        curso.setNombre(rs.getString("nombre"));
        curso.setCreditos(rs.getInt("creditos"));
        curso.setHorasSemanales(rs.getInt("horas_semanales"));
        curso.setDocente(rs.getString("docente"));
        return curso;
    }
}

