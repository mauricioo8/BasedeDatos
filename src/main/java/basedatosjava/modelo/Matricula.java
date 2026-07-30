
package basedatosjava.modelo;

import java.time.LocalDate;
 
public class Matricula {
 
    private int id;
    private int alumnoId;
    private int cursoId;
    private LocalDate fechaMatricula;
    private String estado; // "ACTIVO", "RETIRADO", "FINALIZADO", etc.
 
    // Campos "enriquecidos": el DAO los llena solo cuando hace un JOIN,
    // para no tener que hacer una consulta aparte al mostrar listas
    // (por ejemplo, al mostrar los cursos de un alumno).
    private Alumno alumno;
    private Curso curso;
 
    public Matricula() {
    }
 
    public Matricula(int alumnoId, int cursoId, LocalDate fechaMatricula, String estado) {
        this.alumnoId = alumnoId;
        this.cursoId = cursoId;
        this.fechaMatricula = fechaMatricula;
        this.estado = estado;
    }
 
    public int getId() {
        return id;
    }
 
    public void setId(int id) {
        this.id = id;
    }
 
    public int getAlumnoId() {
        return alumnoId;
    }
 
    public void setAlumnoId(int alumnoId) {
        this.alumnoId = alumnoId;
    }
 
    public int getCursoId() {
        return cursoId;
    }
 
    public void setCursoId(int cursoId) {
        this.cursoId = cursoId;
    }
 
    public LocalDate getFechaMatricula() {
        return fechaMatricula;
    }
 
    public void setFechaMatricula(LocalDate fechaMatricula) {
        this.fechaMatricula = fechaMatricula;
    }
 
    public String getEstado() {
        return estado;
    }
 
    public void setEstado(String estado) {
        this.estado = estado;
    }
 
    public Alumno getAlumno() {
        return alumno;
    }
 
    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }
 
    public Curso getCurso() {
        return curso;
    }
 
    public void setCurso(Curso curso) {
        this.curso = curso;
    }
 
    @Override
    public String toString() {
        String infoCurso = curso != null
                ? String.format("%s - %s", curso.getCodigo(), curso.getNombre())
                : "Curso ID: " + cursoId;
        String infoAlumno = alumno != null
                ? String.format("%s %s", alumno.getNombre(), alumno.getApellido())
                : "Alumno ID: " + alumnoId;
        return String.format(
                "Matrícula ID: %-4d %-30s %-30s Fecha: %-12s Estado: %s",
                id, infoAlumno, infoCurso, fechaMatricula, estado);
    }
}