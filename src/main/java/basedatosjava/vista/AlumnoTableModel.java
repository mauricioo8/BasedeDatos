package basedatosjava.vista;

import basedatosjava.modelo.Alumno;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

/**
 * Adapta una lista de Alumno para que pueda mostrarse dentro de un JTable.
 */
public class AlumnoTableModel extends AbstractTableModel {

    private final String[] columnas = {
            "ID", "Código", "Nombre", "Apellido", "Correo", "Teléfono", "Fecha Nac."
    };

    private List<Alumno> alumnos = new ArrayList<>();

    public void setAlumnos(List<Alumno> alumnos) {
        this.alumnos = alumnos;
        fireTableDataChanged();
    }

    public Alumno getAlumnoEn(int fila) {
        return alumnos.get(fila);
    }

    @Override
    public int getRowCount() {
        return alumnos.size();
    }

    @Override
    public int getColumnCount() {
        return columnas.length;
    }

    @Override
    public String getColumnName(int columna) {
        return columnas[columna];
    }

    @Override
    public Object getValueAt(int fila, int columna) {
        Alumno a = alumnos.get(fila);
        return switch (columna) {
            case 0 -> a.getId();
            case 1 -> a.getCodigo();
            case 2 -> a.getNombre();
            case 3 -> a.getApellido();
            case 4 -> a.getCorreo();
            case 5 -> a.getTelefono();
            case 6 -> a.getFechaNacimiento() != null ? a.getFechaNacimiento().toString() : "";
            default -> "";
        };
    }
}
