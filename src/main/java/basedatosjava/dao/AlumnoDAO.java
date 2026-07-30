package basedatosjava.dao;

import basedatosjava.ConexionBD;
import basedatosjava.modelo.Alumno;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase encargada de toda la comunicación con la tabla ALUMNOS.
 * Cada método abre su propia conexión y la cierra con try-with-resources
 * para no dejar conexiones abiertas.
 */
public class AlumnoDAO {

    // ---------- REGISTRAR ----------
    public boolean registrar(Alumno alumno) {
        String sql = "INSERT INTO alumnos (codigo, nombre, apellido, correo, telefono, fecha_nacimiento) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, alumno.getCodigo());
            ps.setString(2, alumno.getNombre());
            ps.setString(3, alumno.getApellido());
            ps.setString(4, alumno.getCorreo());
            ps.setString(5, alumno.getTelefono());
            ps.setDate(6, alumno.getFechaNacimiento() != null
                    ? Date.valueOf(alumno.getFechaNacimiento()) : null);

            int filas = ps.executeUpdate();
            return filas > 0;

        } catch (SQLException e) {
            System.out.println("Error al registrar alumno: " + e.getMessage());
            return false;
        }
    }

    // ---------- EDITAR ----------
    public boolean editar(Alumno alumno) {
        String sql = "UPDATE alumnos SET codigo = ?, nombre = ?, apellido = ?, "
                + "correo = ?, telefono = ?, fecha_nacimiento = ? WHERE id = ?";

        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, alumno.getCodigo());
            ps.setString(2, alumno.getNombre());
            ps.setString(3, alumno.getApellido());
            ps.setString(4, alumno.getCorreo());
            ps.setString(5, alumno.getTelefono());
            ps.setDate(6, alumno.getFechaNacimiento() != null
                    ? Date.valueOf(alumno.getFechaNacimiento()) : null);
            ps.setInt(7, alumno.getId());

            int filas = ps.executeUpdate();
            return filas > 0;

        } catch (SQLException e) {
            System.out.println("Error al editar alumno: " + e.getMessage());
            return false;
        }
    }

    // ---------- ELIMINAR ----------
    public boolean eliminar(int id) {
        String sql = "DELETE FROM alumnos WHERE id = ?";

        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, id);
            int filas = ps.executeUpdate();
            return filas > 0;

        } catch (SQLException e) {
            System.out.println("Error al eliminar alumno: " + e.getMessage());
            System.out.println("Sugerencia: si el alumno ya tiene matrículas registradas, "
                    + "puede que la base de datos impida eliminarlo por integridad referencial.");
            return false;
        }
    }

    // ---------- BUSCAR (por nombre, apellido o código) ----------
    public List<Alumno> buscar(String criterio) {
        List<Alumno> resultado = new ArrayList<>();
        String sql = "SELECT * FROM alumnos WHERE nombre LIKE ? OR apellido LIKE ? OR codigo LIKE ? "
                + "ORDER BY apellido, nombre";

        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            String patron = "%" + criterio + "%";
            ps.setString(1, patron);
            ps.setString(2, patron);
            ps.setString(3, patron);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    resultado.add(mapearAlumno(rs));
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al buscar alumnos: " + e.getMessage());
        }
        return resultado;
    }

    // ---------- LISTAR TODOS ----------
    public List<Alumno> listarTodos() {
        List<Alumno> lista = new ArrayList<>();
        String sql = "SELECT * FROM alumnos ORDER BY apellido, nombre";

        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(mapearAlumno(rs));
            }

        } catch (SQLException e) {
            System.out.println("Error al listar alumnos: " + e.getMessage());
        }
        return lista;
    }

    // ---------- Método auxiliar: convierte una fila del ResultSet en un objeto Alumno ----------
    private Alumno mapearAlumno(ResultSet rs) throws SQLException {
        Alumno alumno = new Alumno();
        alumno.setId(rs.getInt("id"));
        alumno.setCodigo(rs.getString("codigo"));
        alumno.setNombre(rs.getString("nombre"));
        alumno.setApellido(rs.getString("apellido"));
        alumno.setCorreo(rs.getString("correo"));
        alumno.setTelefono(rs.getString("telefono"));
        Date fecha = rs.getDate("fecha_nacimiento");
        if (fecha != null) {
            alumno.setFechaNacimiento(fecha.toLocalDate());
        }
        return alumno;
    }
}
