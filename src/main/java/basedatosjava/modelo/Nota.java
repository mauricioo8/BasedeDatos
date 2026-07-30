
package basedatosjava.modelo;

import java.time.LocalDate;
 
public class Nota {
 
    private int id;
    private int matriculaId;
    private String descripcion; // ej: "Examen Parcial", "Trabajo Final"
    private double valor;
    private LocalDate fecha;
 
    public Nota() {
    }
 
    public Nota(int matriculaId, String descripcion, double valor, LocalDate fecha) {
        this.matriculaId = matriculaId;
        this.descripcion = descripcion;
        this.valor = valor;
        this.fecha = fecha;
    }
 
    public int getId() {
        return id;
    }
 
    public void setId(int id) {
        this.id = id;
    }
 
    public int getMatriculaId() {
        return matriculaId;
    }
 
    public void setMatriculaId(int matriculaId) {
        this.matriculaId = matriculaId;
    }
 
    public String getDescripcion() {
        return descripcion;
    }
 
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
 
    public double getValor() {
        return valor;
    }
 
    public void setValor(double valor) {
        this.valor = valor;
    }
 
    public LocalDate getFecha() {
        return fecha;
    }
 
    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
 
    @Override
    public String toString() {
        return String.format("Nota ID: %-4d %-20s Valor: %-6.2f Fecha: %s",
                id, descripcion, valor, fecha);
    }
}
