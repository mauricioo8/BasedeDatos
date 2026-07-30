/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package basedatosjava.vista;


import basedatosjava.modelo.Matricula;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class MatriculaTableModel extends AbstractTableModel {

    private final String[] columnas = {
            "ID",
            "Alumno ID",
            "Curso ID",
            "Fecha",
            "Estado"
    };

    private List<Matricula> matriculas = new ArrayList<>();

    public void setMatriculas(List<Matricula> matriculas) {
        this.matriculas = matriculas;
        fireTableDataChanged();
    }

    public Matricula getMatriculaEn(int fila) {
        return matriculas.get(fila);
    }

    @Override
    public int getRowCount() {
        return matriculas.size();
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

        Matricula m = matriculas.get(fila);

        switch (columna) {
            case 0:
                return m.getId();
            case 1:
                return m.getAlumnoId();
            case 2:
                return m.getCursoId();
            case 3:
                return m.getFechaMatricula();
            case 4:
                return m.getEstado();
            default:
                return "";
        }
    }
}
