
package basedatosjava.dao;

import basedatosjava.ConexionBD;
import basedatosjava.modelo.Alumno;
import basedatosjava.modelo.Curso;
import basedatosjava.modelo.Matricula;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MatriculaDAO {


    // VERIFICAR SI YA EXISTE
    public boolean yaEstaMatriculado(int alumnoId, int cursoId) {

        String sql = "SELECT id FROM matriculas WHERE alumno_id = ? AND curso_id = ?";


        try(Connection conexion = ConexionBD.obtenerConexion();
            PreparedStatement ps = conexion.prepareStatement(sql)) {


            ps.setInt(1, alumnoId);
            ps.setInt(2, cursoId);


            ResultSet rs = ps.executeQuery();

            return rs.next();


        } catch(SQLException e){

            System.out.println("Error al verificar matrícula: " + e.getMessage());
            return false;

        }

    }




    // REGISTRAR MATRÍCULA
    public boolean matricular(Matricula matricula){


        if(yaEstaMatriculado(
                matricula.getAlumnoId(),
                matricula.getCursoId())){


            System.out.println("El alumno ya está matriculado.");
            return false;

        }



        String sql = 
        "INSERT INTO matriculas(alumno_id, curso_id, fecha_matricula, estado)"
        + " VALUES(?,?,?,?)";



        try(Connection conexion = ConexionBD.obtenerConexion();
            PreparedStatement ps = conexion.prepareStatement(sql)){



            LocalDate fecha = matricula.getFechaMatricula() != null
                    ? matricula.getFechaMatricula()
                    : LocalDate.now();


            String estado = matricula.getEstado() != null
                    ? matricula.getEstado()
                    : "ACTIVO";



            ps.setInt(1, matricula.getAlumnoId());
            ps.setInt(2, matricula.getCursoId());
            ps.setDate(3, Date.valueOf(fecha));
            ps.setString(4, estado);



            return ps.executeUpdate() > 0;



        }catch(SQLException e){

            System.out.println("Error al matricular alumno: "
                    + e.getMessage());

            return false;

        }

    }




    // CAMBIAR ESTADO
    public boolean cambiarEstado(int id, String estado){


        String sql =
        "UPDATE matriculas SET estado=? WHERE id=?";


        try(Connection conexion = ConexionBD.obtenerConexion();
            PreparedStatement ps = conexion.prepareStatement(sql)){


            ps.setString(1, estado);
            ps.setInt(2,id);


            return ps.executeUpdate()>0;


        }catch(SQLException e){

            System.out.println("Error al cambiar estado: "
                    + e.getMessage());

            return false;

        }

    }




    // ELIMINAR
    public boolean eliminar(int id){


        String sql =
        "DELETE FROM matriculas WHERE id=?";



        try(Connection conexion = ConexionBD.obtenerConexion();
            PreparedStatement ps = conexion.prepareStatement(sql)){


            ps.setInt(1,id);


            return ps.executeUpdate()>0;



        }catch(SQLException e){

            System.out.println("Error al eliminar matrícula: "
                    + e.getMessage());

            return false;

        }

    }




    // BUSCAR POR ID
    public Matricula buscarPorId(int id){


        String sql =
        "SELECT * FROM matriculas WHERE id=?";



        try(Connection conexion = ConexionBD.obtenerConexion();
            PreparedStatement ps = conexion.prepareStatement(sql)){



            ps.setInt(1,id);


            ResultSet rs = ps.executeQuery();



            if(rs.next()){

                return mapearMatricula(rs);

            }



        }catch(SQLException e){

            System.out.println("Error al buscar matrícula: "
                    + e.getMessage());

        }



        return null;

    }





    // LISTAR CURSOS DE UN ALUMNO
    public List<Matricula> listarPorAlumno(int alumnoId){


        List<Matricula> lista = new ArrayList<>();


        String sql =
        "SELECT m.*, c.nombre AS c_nombre, c.creditos AS c_creditos "
        + "FROM matriculas m "
        + "JOIN cursos c ON m.curso_id = c.id_curso "
        + "WHERE m.alumno_id=?";



        try(Connection conexion = ConexionBD.obtenerConexion();
            PreparedStatement ps = conexion.prepareStatement(sql)){



            ps.setInt(1,alumnoId);



            ResultSet rs = ps.executeQuery();



            while(rs.next()){

                lista.add(mapearMatriculaConCurso(rs));

            }



        }catch(SQLException e){

            System.out.println("Error al listar cursos: "
                    + e.getMessage());

        }



        return lista;

    }





    // LISTAR ALUMNOS DE UN CURSO
    public List<Matricula> listarPorCurso(int cursoId){


        List<Matricula> lista = new ArrayList<>();


        String sql =
        "SELECT m.*, "
        + "a.codigo AS a_codigo, "
        + "a.nombre AS a_nombre, "
        + "a.apellido AS a_apellido, "
        + "a.correo AS a_correo, "
        + "a.telefono AS a_telefono "
        + "FROM matriculas m "
        + "JOIN alumnos a ON m.alumno_id=a.id "
        + "WHERE m.curso_id=?";



        try(Connection conexion = ConexionBD.obtenerConexion();
            PreparedStatement ps = conexion.prepareStatement(sql)){



            ps.setInt(1,cursoId);


            ResultSet rs = ps.executeQuery();



            while(rs.next()){

                lista.add(mapearMatriculaConAlumno(rs));

            }



        }catch(SQLException e){

            System.out.println("Error al listar alumnos: "
                    + e.getMessage());

        }



        return lista;

    }





    // LISTAR TODAS
    public List<Matricula> listarTodas(){


        List<Matricula> lista = new ArrayList<>();


        String sql =
        "SELECT * FROM matriculas ORDER BY fecha_matricula DESC";



        try(Connection conexion = ConexionBD.obtenerConexion();
            PreparedStatement ps = conexion.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()){



            while(rs.next()){

                lista.add(mapearMatricula(rs));

            }



        }catch(SQLException e){

            System.out.println("Error al listar matrículas: "
                    + e.getMessage());

        }



        return lista;

    }





    // MAPEAR MATRÍCULA
    private Matricula mapearMatricula(ResultSet rs)
            throws SQLException{


        Matricula m = new Matricula();


        m.setId(rs.getInt("id"));

        m.setAlumnoId(
                rs.getInt("alumno_id")
        );


        m.setCursoId(
                rs.getInt("curso_id")
        );



        Date fecha = rs.getDate("fecha_matricula");


        if(fecha != null){

            m.setFechaMatricula(
                    fecha.toLocalDate()
            );

        }



        m.setEstado(
                rs.getString("estado")
        );


        return m;

    }





    // MAPEAR CON CURSO
    private Matricula mapearMatriculaConCurso(ResultSet rs)
            throws SQLException{


        Matricula m = mapearMatricula(rs);


        Curso c = new Curso();


        c.setIdCurso(
                rs.getInt("curso_id")
        );


        c.setNombre(
                rs.getString("c_nombre")
        );


        c.setCreditos(
                rs.getInt("c_creditos")
        );


        m.setCurso(c);


        return m;

    }





    // MAPEAR CON ALUMNO
    private Matricula mapearMatriculaConAlumno(ResultSet rs)
            throws SQLException{


        Matricula m = mapearMatricula(rs);



        Alumno a = new Alumno();


        a.setId(
                rs.getInt("alumno_id")
        );


        a.setCodigo(
                rs.getString("a_codigo")
        );


        a.setNombre(
                rs.getString("a_nombre")
        );


        a.setApellido(
                rs.getString("a_apellido")
        );


        a.setCorreo(
                rs.getString("a_correo")
        );


        a.setTelefono(
                rs.getString("a_telefono")
        );


        m.setAlumno(a);



        return m;

    }

}