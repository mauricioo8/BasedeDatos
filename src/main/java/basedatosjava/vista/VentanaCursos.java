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
    private final JTextField txtNombre = new JTextField(20);
    private final JTextField txtCreditos = new JTextField(5);
    private final JTextField txtBusqueda = new JTextField(15);


    private final JTable tabla = new JTable(modeloTabla);



    public VentanaCursos(){


        super("Gestión de Cursos");


        construirInterfaz();
        cargarTodos();


        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600,450);
        setLocationRelativeTo(null);

    }





    private void construirInterfaz(){


        setLayout(new BorderLayout(10,10));


        add(construirPanelFormulario(),
                BorderLayout.NORTH);


        add(new JScrollPane(tabla),
                BorderLayout.CENTER);


        add(construirPanelBusqueda(),
                BorderLayout.SOUTH);



        tabla.getSelectionModel()
                .addListSelectionListener(e -> {


                    int fila = tabla.getSelectedRow();


                    if(fila >=0){

                        cargarFormularioDesdeFila(fila);

                    }


                });


    }







    private JPanel construirPanelFormulario(){


        JPanel panel = new JPanel(new GridLayout(4,2,5,5));


        panel.setBorder(
                BorderFactory.createTitledBorder(
                        "Datos del curso"
                )
        );


        panel.add(new JLabel("ID:"));

        txtId.setEditable(false);

        panel.add(txtId);



        panel.add(new JLabel("Nombre:"));
        panel.add(txtNombre);



        panel.add(new JLabel("Créditos:"));
        panel.add(txtCreditos);




        JButton btnRegistrar =
                new JButton("Registrar");


        JButton btnEditar =
                new JButton("Editar");


        JButton btnEliminar =
                new JButton("Eliminar");


        JButton btnLimpiar =
                new JButton("Limpiar");



        btnRegistrar.addActionListener(e -> registrar());

        btnEditar.addActionListener(e -> editar());

        btnEliminar.addActionListener(e -> eliminar());

        btnLimpiar.addActionListener(e -> limpiar());



        JPanel botones = new JPanel();


        botones.add(btnRegistrar);
        botones.add(btnEditar);
        botones.add(btnEliminar);
        botones.add(btnLimpiar);



        panel.add(botones);



        return panel;

    }







    private JPanel construirPanelBusqueda(){


        JPanel panel = new JPanel();


        JButton btnBuscar =
                new JButton("Buscar");


        JButton btnListar =
                new JButton("Listar");



        btnBuscar.addActionListener(e -> buscar());


        btnListar.addActionListener(e -> cargarTodos());



        panel.add(new JLabel("Nombre:"));

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

                limpiar();


            }else{


                JOptionPane.showMessageDialog(this,
                        "No se pudo registrar el curso.");

            }



        }catch(Exception e){


            JOptionPane.showMessageDialog(this,
                    e.getMessage());


        }


    }








    private Curso leerFormulario(){



        if(txtNombre.getText().isBlank()){


            throw new IllegalArgumentException(
                    "Ingrese el nombre del curso."
            );

        }



        int creditos;


        try{


            creditos = Integer.parseInt(
                    txtCreditos.getText()
            );



        }catch(Exception e){


            throw new IllegalArgumentException(
                    "Los créditos deben ser números."
            );


        }



        return new Curso(
                0,
                txtNombre.getText(),
                creditos
        );


    }







    private void cargarTodos(){


        List<Curso> lista =
                cursoDAO.listarTodos();


        modeloTabla.setCursos(lista);


    }








    private void cargarFormularioDesdeFila(int fila){


        int filaModelo =
                tabla.convertRowIndexToModel(fila);



        Curso curso =
                modeloTabla.getCursoEn(filaModelo);



        txtId.setText(
                String.valueOf(curso.getIdCurso())
        );


        txtNombre.setText(
                curso.getNombre()
        );


        txtCreditos.setText(
                String.valueOf(curso.getCreditos())
        );


    }








    private void editar(){


        if(txtId.getText().isBlank()){


            JOptionPane.showMessageDialog(this,
                    "Seleccione un curso.");

            return;

        }



        Curso curso = leerFormulario();


        curso.setIdCurso(
                Integer.parseInt(
                        txtId.getText()
                )
        );



        if(cursoDAO.editar(curso)){


            JOptionPane.showMessageDialog(this,
                    "Curso actualizado.");


            cargarTodos();

            limpiar();


        }else{


            JOptionPane.showMessageDialog(this,
                    "No se pudo actualizar.");

        }


    }








    private void eliminar(){


        if(txtId.getText().isBlank()){

            return;

        }



        int id =
                Integer.parseInt(
                        txtId.getText()
                );



        if(cursoDAO.eliminar(id)){


            JOptionPane.showMessageDialog(this,
                    "Curso eliminado.");

            cargarTodos();

            limpiar();


        }



    }







    private void buscar(){


        List<Curso> lista =
                cursoDAO.buscar(
                        txtBusqueda.getText()
                );


        modeloTabla.setCursos(lista);


    }







    private void limpiar(){


        txtId.setText("");

        txtNombre.setText("");

        txtCreditos.setText("");

        tabla.clearSelection();


    }


}
