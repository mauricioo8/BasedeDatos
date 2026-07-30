
package basedatosjava.dao;

import basedatosjava.ConexionBD;
import basedatosjava.modelo.Curso;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CursoDAO {


    // REGISTRAR CURSO
    public boolean registrar(Curso curso) {

        String sql = "INSERT INTO cursos (nombre, creditos) VALUES (?, ?)";

        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {


            ps.setString(1, curso.getNombre());
            ps.setInt(2, curso.getCreditos());

            int filas = ps.executeUpdate();

            return filas > 0;


        } catch (SQLException e) {

            System.out.println("Error al registrar curso: " + e.getMessage());
            return false;

        }
    }



    // EDITAR CURSO
    public boolean editar(Curso curso) {

        String sql = "UPDATE cursos SET nombre = ?, creditos = ? WHERE id_curso = ?";


        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {


            ps.setString(1, curso.getNombre());
            ps.setInt(2, curso.getCreditos());
            ps.setInt(3, curso.getIdCurso());


            return ps.executeUpdate() > 0;


        } catch (SQLException e) {

            System.out.println("Error al editar curso: " + e.getMessage());
            return false;

        }

    }



    // ELIMINAR CURSO
    public boolean eliminar(int idCurso) {


        String sql = "DELETE FROM cursos WHERE id_curso = ?";


        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {


            ps.setInt(1, idCurso);


            return ps.executeUpdate() > 0;


        } catch (SQLException e) {


            System.out.println("Error al eliminar curso: " + e.getMessage());
            return false;

        }

    }




    // LISTAR TODOS
    public List<Curso> listarTodos() {


        List<Curso> lista = new ArrayList<>();

        String sql = "SELECT * FROM cursos ORDER BY nombre";


        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {


            while (rs.next()) {


                Curso curso = new Curso();


                curso.setIdCurso(
                        rs.getInt("id_curso")
                );


                curso.setNombre(
                        rs.getString("nombre")
                );


                curso.setCreditos(
                        rs.getInt("creditos")
                );


                lista.add(curso);

            }


        } catch (SQLException e) {

            System.out.println("Error al listar cursos: " + e.getMessage());

        }


        return lista;

    }





    // BUSCAR CURSO POR NOMBRE
    public List<Curso> buscar(String nombre) {


        List<Curso> lista = new ArrayList<>();


        String sql = 
                "SELECT * FROM cursos WHERE nombre LIKE ? ORDER BY nombre";



        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {


            ps.setString(1, "%" + nombre + "%");


            ResultSet rs = ps.executeQuery();



            while (rs.next()) {


                Curso curso = new Curso();


                curso.setIdCurso(
                        rs.getInt("id_curso")
                );


                curso.setNombre(
                        rs.getString("nombre")
                );


                curso.setCreditos(
                        rs.getInt("creditos")
                );


                lista.add(curso);

            }



        } catch (SQLException e) {

            System.out.println("Error al buscar cursos: " + e.getMessage());

        }



        return lista;

    }




    // BUSCAR POR ID
    public Curso buscarPorId(int idCurso) {


        String sql = 
                "SELECT * FROM cursos WHERE id_curso = ?";



        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {



            ps.setInt(1, idCurso);



            ResultSet rs = ps.executeQuery();



            if(rs.next()) {


                Curso curso = new Curso();


                curso.setIdCurso(
                        rs.getInt("id_curso")
                );


                curso.setNombre(
                        rs.getString("nombre")
                );


                curso.setCreditos(
                        rs.getInt("creditos")
                );


                return curso;

            }



        } catch(SQLException e) {


            System.out.println("Error al buscar curso: " + e.getMessage());


        }



        return null;

    }


}