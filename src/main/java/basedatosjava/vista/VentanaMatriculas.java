
package basedatosjava.vista;

import basedatosjava.dao.MatriculaDAO;
import basedatosjava.modelo.Matricula;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class VentanaMatriculas extends JFrame {

    private final MatriculaDAO matriculaDAO = new MatriculaDAO();
    private final MatriculaTableModel modeloTabla = new MatriculaTableModel();

    private final JTextField txtId = new JTextField(5);
    private final JTextField txtAlumno = new JTextField(10);
    private final JTextField txtCurso = new JTextField(10);
    private final JTextField txtFecha = new JTextField(10);
    private final JTextField txtEstado = new JTextField(10);

    private final JTable tabla = new JTable(modeloTabla);

    public VentanaMatriculas() {

        super("Gestión de Matrículas");

        construirInterfaz();

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800,500);
        setLocationRelativeTo(null);

        cargarTodos();
    }

    private void construirInterfaz(){

        setLayout(new BorderLayout(10,10));

        add(construirPanelFormulario(),BorderLayout.NORTH);
        add(new JScrollPane(tabla),BorderLayout.CENTER);

        tabla.getSelectionModel().addListSelectionListener(e->{

            int fila = tabla.getSelectedRow();

            if(fila>=0){
                cargarFormularioDesdeFila(fila);
            }

        });

    }

    private JPanel construirPanelFormulario(){

        JPanel panel = new JPanel(new GridBagLayout());

        panel.setBorder(
                BorderFactory.createTitledBorder("Datos de la Matrícula")
        );

        GridBagConstraints c = new GridBagConstraints();

        c.insets = new Insets(4,4,4,4);
        c.fill = GridBagConstraints.HORIZONTAL;

        int fila=0;

        agregarCampo(panel,c,fila++,"ID:",txtId);
        txtId.setEditable(false);

        agregarCampo(panel,c,fila++,"ID Alumno:",txtAlumno);
        agregarCampo(panel,c,fila++,"ID Curso:",txtCurso);
        agregarCampo(panel,c,fila++,"Fecha (AAAA-MM-DD):",txtFecha);
        agregarCampo(panel,c,fila++,"Estado:",txtEstado);

        JButton btnRegistrar = new JButton("Registrar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnListar = new JButton("Listar");
        JButton btnLimpiar = new JButton("Limpiar");

        btnRegistrar.addActionListener(e->registrar());
        btnEliminar.addActionListener(e->eliminar());
        btnListar.addActionListener(e->cargarTodos());
        btnLimpiar.addActionListener(e->limpiarFormulario());

        JPanel botones = new JPanel(new FlowLayout());

        botones.add(btnRegistrar);
        botones.add(btnEliminar);
        botones.add(btnListar);
        botones.add(btnLimpiar);

        c.gridx=0;
        c.gridy=fila;
        c.gridwidth=2;

        panel.add(botones,c);

        return panel;
    }

    private void agregarCampo(JPanel panel,
                              GridBagConstraints c,
                              int fila,
                              String etiqueta,
                              JTextField campo){

        c.gridx=0;
        c.gridy=fila;

        panel.add(new JLabel(etiqueta),c);

        c.gridx=1;

        panel.add(campo,c);

    }
    
 private void registrar() {

        try {

            Matricula matricula = leerFormulario();

            if (matriculaDAO.matricular(matricula)) {

                JOptionPane.showMessageDialog(this,
                        "Matrícula registrada correctamente.");

                cargarTodos();
                limpiarFormulario();

            } else {

                JOptionPane.showMessageDialog(this,
                        "No se pudo registrar la matrícula.");

            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(this,
                    "Error: " + e.getMessage());

        }

    }

    private Matricula leerFormulario() {

        Matricula m = new Matricula();

        m.setAlumnoId(Integer.parseInt(txtAlumno.getText()));
        m.setCursoId(Integer.parseInt(txtCurso.getText()));

        if (txtFecha.getText().isBlank()) {
            m.setFechaMatricula(LocalDate.now());
        } else {
            m.setFechaMatricula(LocalDate.parse(txtFecha.getText()));
        }

        if (txtEstado.getText().isBlank()) {
            m.setEstado("ACTIVO");
        } else {
            m.setEstado(txtEstado.getText());
        }

        return m;
    }

    private void cargarTodos() {

        modeloTabla.setMatriculas(
                matriculaDAO.listarTodas()
        );

    }

    private void eliminar() {

        if (txtId.getText().isBlank()) {

            JOptionPane.showMessageDialog(this,
                    "Selecciona una matrícula.");

            return;
        }

        int id = Integer.parseInt(txtId.getText());

        if (matriculaDAO.eliminar(id)) {

            JOptionPane.showMessageDialog(this,
                    "Matrícula eliminada.");

            cargarTodos();
            limpiarFormulario();

        }

    }

    private void limpiarFormulario() {

        txtId.setText("");
        txtAlumno.setText("");
        txtCurso.setText("");
        txtFecha.setText("");
        txtEstado.setText("");

        tabla.clearSelection();

    }

    private void cargarFormularioDesdeFila(int fila) {

        Matricula m = modeloTabla.getMatriculaEn(fila);

        txtId.setText(String.valueOf(m.getId()));
        txtAlumno.setText(String.valueOf(m.getAlumnoId()));
        txtCurso.setText(String.valueOf(m.getCursoId()));

        if (m.getFechaMatricula() != null) {
            txtFecha.setText(m.getFechaMatricula().toString());
        } else {
            txtFecha.setText("");
        }

        txtEstado.setText(m.getEstado());

    }

}