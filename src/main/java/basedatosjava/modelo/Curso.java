
package basedatosjava.modelo;

public class Curso {
 
    private int id;
    private String codigo;
    private String nombre;
    private int creditos;
    private int horasSemanales;
    private String docente;
 
    public Curso() {
    }
 
    public Curso(String codigo, String nombre, int creditos, int horasSemanales, String docente) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.creditos = creditos;
        this.horasSemanales = horasSemanales;
        this.docente = docente;
    }
 
    public int getId() {
        return id;
    }
 
    public void setId(int id) {
        this.id = id;
    }
 
    public String getCodigo() {
        return codigo;
    }
 
    public void setCodigo(String codigo) {
        this.codigo = codigo;
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
 
    public int getHorasSemanales() {
        return horasSemanales;
    }
 
    public void setHorasSemanales(int horasSemanales) {
        this.horasSemanales = horasSemanales;
    }
 
    public String getDocente() {
        return docente;
    }
 
    public void setDocente(String docente) {
        this.docente = docente;
    }
 
    @Override
    public String toString() {
        return String.format(
                "ID: %-4d Código: %-10s Nombre: %-25s Créditos: %-3d Horas/sem: %-3d Docente: %s",
                id, codigo, nombre, creditos, horasSemanales, docente);
    }
}
