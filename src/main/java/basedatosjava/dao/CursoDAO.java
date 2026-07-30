
package basedatosjava.dao;

import basedatosjava.ConexionBD;
import basedatosjava.modelo.Curso;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CursoDAO {


    // ---------- REGISTRAR ----------
    
    public boolean registrar(Curso curso) {

    String sql = "INSERT INTO cursos (nombre, creditos) VALUES (?, ?)";

    try (Connection conexion = ConexionBD.obtenerConexion();
         PreparedStatement ps = conexion.prepareStatement(sql)) {

        System.out.println("1. Conexion obtenida");

        ps.setString(1, curso.getNombre());
        ps.setInt(2, curso.getCreditos());

        System.out.println("2. Datos preparados");

        int filas = ps.executeUpdate();

        System.out.println("3. Insert terminado");

        return filas > 0;

    } catch (SQLException e) {

        e.printStackTrace();
        return false;
    }
}



    // ---------- EDITAR ----------
    public boolean editar(Curso curso) {

        String sql = "UPDATE cursos SET nombre = ?, creditos = ? WHERE id_curso = ?";


        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {


            ps.setString(1, curso.getNombre());
            ps.setInt(2, curso.getCreditos());
            ps.setInt(3, curso.getIdCurso());


            int filas = ps.executeUpdate();

            return filas > 0;


        } catch (SQLException e) {

            System.out.println("Error al editar curso: " + e.getMessage());
            return false;
        }
    }



    // ---------- ELIMINAR ----------
    public boolean eliminar(int idCurso) {

        String sql = "DELETE FROM cursos WHERE id_curso = ?";


        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {


            ps.setInt(1, idCurso);

            int filas = ps.executeUpdate();

            return filas > 0;


        } catch (SQLException e) {

            System.out.println("Error al eliminar curso: " + e.getMessage());
            return false;
        }
    }



    // ---------- BUSCAR ----------
    public List<Curso> buscar(String criterio) {


        List<Curso> resultado = new ArrayList<>();

        String sql = "SELECT * FROM cursos WHERE nombre LIKE ? ORDER BY nombre";


        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {


            String patron = "%" + criterio + "%";

            ps.setString(1, patron);


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



    // ---------- MAPEAR RESULTADO ----------
    private Curso mapearCurso(ResultSet rs) throws SQLException {


        Curso curso = new Curso();


        curso.setIdCurso(rs.getInt("id_curso"));
        curso.setNombre(rs.getString("nombre"));
        curso.setCreditos(rs.getInt("creditos"));


        return curso;
    }

}