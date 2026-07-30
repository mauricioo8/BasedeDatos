
package basedatosjava.vista;

import basedatosjava.dao.NotaDAO;
import basedatosjava.modelo.Nota;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class VentanaNotas extends JFrame {

    private final NotaDAO notaDAO = new NotaDAO();
    private final NotaTableModel modeloTabla = new NotaTableModel();

    private final JTextField txtId = new JTextField(5);
    private final JTextField txtMatricula = new JTextField(10);
    private final JTextField txtDescripcion = new JTextField(15);
    private final JTextField txtValor = new JTextField(10);
    private final JTextField txtFecha = new JTextField(10);

    private final JTable tabla = new JTable(modeloTabla);


    public VentanaNotas() {

        super("Gestión de Notas");

        construirInterfaz();

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800,500);
        setLocationRelativeTo(null);

        cargarTodos();
    }


    private void construirInterfaz(){

        setLayout(new BorderLayout(10,10));

        add(construirPanelFormulario(), BorderLayout.NORTH);
        add(new JScrollPane(tabla), BorderLayout.CENTER);


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
                BorderFactory.createTitledBorder("Datos de la Nota")
        );


        GridBagConstraints c = new GridBagConstraints();

        c.insets = new Insets(4,4,4,4);
        c.fill = GridBagConstraints.HORIZONTAL;


        int fila = 0;


        agregarCampo(panel,c,fila++,"ID:",txtId);
        txtId.setEditable(false);

        agregarCampo(panel,c,fila++,"ID Matrícula:",txtMatricula);
        agregarCampo(panel,c,fila++,"Descripción:",txtDescripcion);
        agregarCampo(panel,c,fila++,"Valor:",txtValor);
        agregarCampo(panel,c,fila++,"Fecha AAAA-MM-DD:",txtFecha);



        JButton btnRegistrar = new JButton("Registrar");
        JButton btnEditar = new JButton("Editar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnListar = new JButton("Listar");
        JButton btnLimpiar = new JButton("Limpiar");


        btnRegistrar.addActionListener(e -> registrar());
        btnEditar.addActionListener(e -> editar());
        btnEliminar.addActionListener(e -> eliminar());
        btnListar.addActionListener(e -> cargarTodos());
        btnLimpiar.addActionListener(e -> limpiar());


        JPanel botones = new JPanel(new FlowLayout());

        botones.add(btnRegistrar);
        botones.add(btnEditar);
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
                              String texto,
                              JTextField campo){

        c.gridx=0;
        c.gridy=fila;

        panel.add(new JLabel(texto),c);

        c.gridx=1;

        panel.add(campo,c);
    }



    private void registrar(){

        try{

            Nota nota = leerFormulario();

            if(notaDAO.registrar(nota)){

                JOptionPane.showMessageDialog(this,
                        "Nota registrada correctamente.");

                cargarTodos();
                limpiar();

            }else{

                JOptionPane.showMessageDialog(this,
                        "No se pudo registrar la nota.");
            }


        }catch(Exception e){

            JOptionPane.showMessageDialog(this,
                    "Error: " + e.getMessage());
        }

    }



    private Nota leerFormulario(){

        Nota n = new Nota();

        n.setMatriculaId(
                Integer.parseInt(txtMatricula.getText())
        );

        n.setDescripcion(
                txtDescripcion.getText()
        );

        n.setValor(
                Double.parseDouble(txtValor.getText())
        );


        if(txtFecha.getText().isBlank()){

            n.setFecha(LocalDate.now());

        }else{

            n.setFecha(
                    LocalDate.parse(txtFecha.getText())
            );
        }


        return n;
    }



    private void cargarTodos(){

        List<Nota> lista =
                notaDAO.listarPorMatricula(0);

        modeloTabla.setNotas(lista);

    }



    private void eliminar(){

        if(txtId.getText().isBlank()){

            JOptionPane.showMessageDialog(this,
                    "Selecciona una nota.");

            return;
        }


        int id =
                Integer.parseInt(txtId.getText());


        if(notaDAO.eliminar(id)){

            JOptionPane.showMessageDialog(this,
                    "Nota eliminada.");

            cargarTodos();
            limpiar();
        }

    }



    private void editar(){

        JOptionPane.showMessageDialog(this,
                "Edición de notas pendiente.");
    }



    private void limpiar(){

        txtId.setText("");
        txtMatricula.setText("");
        txtDescripcion.setText("");
        txtValor.setText("");
        txtFecha.setText("");

        tabla.clearSelection();
    }



    private void cargarFormularioDesdeFila(int fila){

        Nota n =
                modeloTabla.getNotaEn(fila);


        txtId.setText(
                String.valueOf(n.getId())
        );

        txtMatricula.setText(
                String.valueOf(n.getMatriculaId())
        );

        txtDescripcion.setText(
                n.getDescripcion()
        );

        txtValor.setText(
                String.valueOf(n.getValor())
        );

        txtFecha.setText(
                n.getFecha()!=null
                ? n.getFecha().toString()
                : ""
        );

    }

}
