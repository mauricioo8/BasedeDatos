package basedatosjava.vista;

import basedatosjava.modelo.Curso;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

/**
 * Adapta una lista de Curso para que pueda mostrarse dentro de un JTable.
 */
public class CursoTableModel extends AbstractTableModel {

    private final String[] columnas = {
            "ID", "Código", "Nombre", "Créditos", "Horas Sem.", "Docente"
    };

    private List<Curso> cursos = new ArrayList<>();

    public void setCursos(List<Curso> cursos) {
        this.cursos = cursos;
        fireTableDataChanged();
    }

    public Curso getCursoEn(int fila) {
        return cursos.get(fila);
    }

    @Override
    public int getRowCount() {
        return cursos.size();
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

        Curso c = cursos.get(fila);

        return switch (columna) {
            case 0 -> c.getId();
            case 1 -> c.getCodigo();
            case 2 -> c.getNombre();
            case 3 -> c.getCreditos();
            case 4 -> c.getHorasSemanales();
            case 5 -> c.getDocente();
            default -> "";
        };
    }
}
