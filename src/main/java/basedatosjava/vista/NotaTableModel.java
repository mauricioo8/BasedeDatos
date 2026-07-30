
package basedatosjava.vista;


import basedatosjava.modelo.Nota;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class NotaTableModel extends AbstractTableModel {

    private final String[] columnas = {
            "ID", "Matrícula ID", "Descripción", "Valor", "Fecha"
    };

    private List<Nota> notas = new ArrayList<>();


    public void setNotas(List<Nota> notas) {
        this.notas = notas;
        fireTableDataChanged();
    }


    public Nota getNotaEn(int fila) {
        return notas.get(fila);
    }


    @Override
    public int getRowCount() {
        return notas.size();
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

        Nota n = notas.get(fila);

        return switch (columna) {

            case 0 -> n.getId();

            case 1 -> n.getMatriculaId();

            case 2 -> n.getDescripcion();

            case 3 -> n.getValor();

            case 4 -> n.getFecha() != null
                    ? n.getFecha().toString()
                    : "";

            default -> "";
        };
    }
}
