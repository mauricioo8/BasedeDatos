
package basedatosjava.modelo;

public class Curso {

    private int idCurso;
    private String nombre;
    private int creditos;


    public Curso() {
    }


    public Curso(int idCurso, String nombre, int creditos) {
        this.idCurso = idCurso;
        this.nombre = nombre;
        this.creditos = creditos;
    }


    public int getIdCurso() {
        return idCurso;
    }


    public void setIdCurso(int idCurso) {
        this.idCurso = idCurso;
    }


    public String getNombre() {
        return nombre;
    }


    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    public int getCreditos() {
        return creditos;
    }


    public void setCreditos(int creditos) {
        this.creditos = creditos;
    }


    @Override
    public String toString() {
        return nombre;
    }
}