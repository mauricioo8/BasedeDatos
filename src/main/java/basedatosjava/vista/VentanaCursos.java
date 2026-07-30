package basedatosjava.vista;

import basedatosjava.dao.CursoDAO;
import basedatosjava.modelo.Curso;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class VentanaCursos extends JFrame {

    private final CursoDAO cursoDAO = new CursoDAO();
    private final CursoTableModel modeloTabla = new CursoTableModel();

    private final JTextField txtId = new JTextField(5);
    private final JTextField txtCodigo = new JTextField(10);
    private final JTextField txtNombre = new JTextField(15);
    private final JTextField txtCreditos = new JTextField(5);
    private final JTextField txtHorasSemanales = new JTextField(5);
    private final JTextField txtDocente = new JTextField(15);
    private final JTextField txtBusqueda = new JTextField(15);

    private final JTable tabla = new JTable(modeloTabla);


    public VentanaCursos() {

        super("Gestión de Cursos");

        construirInterfaz();
        cargarTodos();

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(750, 500);
        setLocationRelativeTo(null);
    }


    private void construirInterfaz() {

        setLayout(new BorderLayout(10,10));

        add(construirPanelFormulario(), BorderLayout.NORTH);
        add(new JScrollPane(tabla), BorderLayout.CENTER);
        add(construirPanelBusqueda(), BorderLayout.SOUTH);


        tabla.getSelectionModel().addListSelectionListener(e -> {

            int fila = tabla.getSelectedRow();

            if(fila >= 0){
                cargarFormularioDesdeFila(fila);
            }

        });

    }



    private JPanel construirPanelFormulario(){

        JPanel panel = new JPanel(new GridBagLayout());

        panel.setBorder(
                BorderFactory.createTitledBorder("Datos del curso")
        );


        GridBagConstraints c = new GridBagConstraints();

        c.insets = new Insets(4,4,4,4);
        c.fill = GridBagConstraints.HORIZONTAL;


        int fila = 0;


        agregarCampo(panel,c,fila++,"ID:",txtId);
        txtId.setEditable(false);

        agregarCampo(panel,c,fila++,"Código:",txtCodigo);
        agregarCampo(panel,c,fila++,"Nombre:",txtNombre);
        agregarCampo(panel,c,fila++,"Créditos:",txtCreditos);
        agregarCampo(panel,c,fila++,"Horas semanales:",txtHorasSemanales);
        agregarCampo(panel,c,fila++,"Docente:",txtDocente);



        JButton btnRegistrar = new JButton("Registrar");
        JButton btnEditar = new JButton("Editar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnLimpiar = new JButton("Limpiar");


        btnRegistrar.addActionListener(e -> registrar());
        btnEditar.addActionListener(e -> editar());
        btnEliminar.addActionListener(e -> eliminar());
        btnLimpiar.addActionListener(e -> limpiarFormulario());



        JPanel botones = new JPanel(new FlowLayout());

        botones.add(btnRegistrar);
        botones.add(btnEditar);
        botones.add(btnEliminar);
        botones.add(btnLimpiar);


        c.gridx=0;
        c.gridy=fila;
        c.gridwidth=2;

        panel.add(botones,c);


        return panel;
    }



    private void agregarCampo(JPanel panel, GridBagConstraints c,
                              int fila,String etiqueta,JTextField campo){

        c.gridx=0;
        c.gridy=fila;

        panel.add(new JLabel(etiqueta),c);


        c.gridx=1;

        panel.add(campo,c);
    }



    private JPanel construirPanelBusqueda(){

        JPanel panel = new JPanel(new FlowLayout());

        panel.setBorder(
                BorderFactory.createTitledBorder("Buscar")
        );


        JButton btnBuscar = new JButton("Buscar");
        JButton btnListar = new JButton("Listar todos");


        btnBuscar.addActionListener(e -> buscar());
        btnListar.addActionListener(e -> cargarTodos());


        panel.add(new JLabel("Nombre, código o docente:"));
        panel.add(txtBusqueda);
        panel.add(btnBuscar);
        panel.add(btnListar);


        return panel;
    }



   private void registrar(){

    try{

        Curso curso = leerFormulario();

        if(cursoDAO.registrar(curso)){

            JOptionPane.showMessageDialog(this,
                    "Curso registrado correctamente.");

            cargarTodos();
            limpiarFormulario();

        }else{

            JOptionPane.showMessageDialog(this,
                    "No se pudo registrar el curso. Revisa que el código no esté repetido.");
        }

    }catch(Exception e){

        JOptionPane.showMessageDialog(this,
                "Error: " + e.getMessage());
    }

}



    private void cargarTodos(){

        List<Curso> cursos = cursoDAO.listarTodos();

        modeloTabla.setCursos(cursos);

    }



    private Curso leerFormulario(){

        if(txtCodigo.getText().isBlank()){
            throw new IllegalArgumentException(
                    "El código es obligatorio."
            );
        }

        if(txtNombre.getText().isBlank()){

            throw new IllegalArgumentException(
                    "El nombre es obligatorio."
            );
        }

        int creditos;
        int horasSemanales;

        try {
            creditos = Integer.parseInt(txtCreditos.getText().trim());
            horasSemanales = Integer.parseInt(txtHorasSemanales.getText().trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                    "Créditos y horas semanales deben ser números enteros."
            );
        }

        return new Curso(
                txtCodigo.getText().trim(),
                txtNombre.getText().trim(),
                creditos,
                horasSemanales,
                txtDocente.getText().trim()
        );

    }



    private void limpiarFormulario(){

        txtId.setText("");
        txtCodigo.setText("");
        txtNombre.setText("");
        txtCreditos.setText("");
        txtHorasSemanales.setText("");
        txtDocente.setText("");

        tabla.clearSelection();

    }



    private void buscar(){

        List<Curso> cursos =
                cursoDAO.buscar(txtBusqueda.getText());

        modeloTabla.setCursos(cursos);

    }



    private void cargarFormularioDesdeFila(int fila){

        int filaModelo = tabla.convertRowIndexToModel(fila);
        Curso curso =
                modeloTabla.getCursoEn(filaModelo);


        txtId.setText(
                String.valueOf(curso.getId())
        );

        txtCodigo.setText(
                curso.getCodigo()
        );

        txtNombre.setText(
                curso.getNombre()
        );

        txtCreditos.setText(
                String.valueOf(curso.getCreditos())
        );

        txtHorasSemanales.setText(
                String.valueOf(curso.getHorasSemanales())
        );

        txtDocente.setText(
                curso.getDocente()
        );

    }

  private void editar(){

    if(txtId.getText().isBlank()){

        JOptionPane.showMessageDialog(this,
                "Selecciona un curso de la tabla.");

        return;
    }


    try{

        Curso curso = leerFormulario();

        curso.setId(
                Integer.parseInt(txtId.getText())
        );


        if(cursoDAO.editar(curso)){

            JOptionPane.showMessageDialog(this,
                    "Curso actualizado correctamente.");

            cargarTodos();
            limpiarFormulario();

        } else {

            JOptionPane.showMessageDialog(this,
                    "No se pudo actualizar el curso.");
        }


    }catch(Exception e){

        JOptionPane.showMessageDialog(this,
                "Error: " + e.getMessage());
    }

}



private void eliminar(){

    if(txtId.getText().isBlank()){

        JOptionPane.showMessageDialog(this,
                "Selecciona un curso.");

        return;
    }

    int confirmacion = JOptionPane.showConfirmDialog(this,
            "¿Seguro que deseas eliminar este curso?", "Confirmar",
            JOptionPane.YES_NO_OPTION);

    if (confirmacion != JOptionPane.YES_OPTION) {
        return;
    }

    int id = Integer.parseInt(
            txtId.getText()
    );


    if(cursoDAO.eliminar(id)){

        JOptionPane.showMessageDialog(this,
                "Curso eliminado correctamente.");

        cargarTodos();
        limpiarFormulario();

    } else {

        JOptionPane.showMessageDialog(this,
                "No se pudo eliminar. Puede que tenga matrículas asociadas.");
    }

}

}
