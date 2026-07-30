package basedatosjava.modelo;

import java.time.LocalDate;

public class Alumno {

    private int id;
    private String codigo;
    private String nombre;
    private String apellido;
    private String correo;
    private String telefono;
    private LocalDate fechaNacimiento;

    public Alumno() {
    }

    public Alumno(String codigo, String nombre, String apellido,
                  String correo, String telefono, LocalDate fechaNacimiento) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.telefono = telefono;
        this.fechaNacimiento = fechaNacimiento;
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

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    @Override
    public String toString() {
        return String.format(
                "ID: %-4d Código: %-10s Nombre: %-15s Apellido: %-15s Correo: %-25s Tel: %-12s Nac: %s",
                id, codigo, nombre, apellido, correo, telefono, fechaNacimiento);
    }
}
