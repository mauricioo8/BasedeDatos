package basedatosjava.vista;

import basedatosjava.dao.AlumnoDAO;
import basedatosjava.modelo.Alumno;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class VentanaAlumnos extends JFrame {

    private final AlumnoDAO alumnoDAO = new AlumnoDAO();
    private final AlumnoTableModel modeloTabla = new AlumnoTableModel();
    private final DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    // Campos del formulario
    private final JTextField txtId = new JTextField(5);
    private final JTextField txtCodigo = new JTextField(10);
    private final JTextField txtNombre = new JTextField(15);
    private final JTextField txtApellido = new JTextField(15);
    private final JTextField txtCorreo = new JTextField(20);
    private final JTextField txtTelefono = new JTextField(12);
    private final JTextField txtFechaNacimiento = new JTextField(10);
    private final JTextField txtBusqueda = new JTextField(15);

    private final JTable tabla = new JTable(modeloTabla);

    public VentanaAlumnos() {
        super("Gestión de Alumnos");
        construirInterfaz();
        cargarTodos();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(950, 550);
        setLocationRelativeTo(null);
    }

    private void construirInterfaz() {
        setLayout(new BorderLayout(10, 10));

        add(construirPanelFormulario(), BorderLayout.NORTH);
        add(construirPanelTabla(), BorderLayout.CENTER);
        add(construirPanelBusqueda(), BorderLayout.SOUTH);

        // Al hacer clic en una fila, cargar los datos en el formulario
        tabla.getSelectionModel().addListSelectionListener(evento -> {
            int fila = tabla.getSelectedRow();
            if (fila >= 0) {
                cargarFormularioDesdeFila(fila);
            }
        });
    }

    private JPanel construirPanelFormulario() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Datos del alumno"));
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(4, 4, 4, 4);
        c.fill = GridBagConstraints.HORIZONTAL;

        int fila = 0;

        agregarCampo(panel, c, fila++, "ID (solo lectura, se llena al elegir de la tabla):", txtId);
        txtId.setEditable(false);

        agregarCampo(panel, c, fila++, "Código:", txtCodigo);
        agregarCampo(panel, c, fila++, "Nombre:", txtNombre);
        agregarCampo(panel, c, fila++, "Apellido:", txtApellido);
        agregarCampo(panel, c, fila++, "Correo:", txtCorreo);
        agregarCampo(panel, c, fila++, "Teléfono:", txtTelefono);
        agregarCampo(panel, c, fila++, "Fecha nacimiento (yyyy-MM-dd):", txtFechaNacimiento);

        // Botones
        JButton btnRegistrar = new JButton("Registrar");
        JButton btnEditar = new JButton("Editar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnLimpiar = new JButton("Limpiar formulario");

        btnRegistrar.addActionListener(e -> registrar());
        btnEditar.addActionListener(e -> editar());
        btnEliminar.addActionListener(e -> eliminar());
        btnLimpiar.addActionListener(e -> limpiarFormulario());

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBotones.add(btnRegistrar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnLimpiar);

        c.gridx = 0;
        c.gridy = fila;
        c.gridwidth = 2;
        panel.add(panelBotones, c);

        return panel;
    }

    private void agregarCampo(JPanel panel, GridBagConstraints c, int fila, String etiqueta, JTextField campo) {
        c.gridx = 0;
        c.gridy = fila;
        c.gridwidth = 1;
        panel.add(new JLabel(etiqueta), c);

        c.gridx = 1;
        panel.add(campo, c);
    }

    private JScrollPane construirPanelTabla() {
        tabla.setAutoCreateRowSorter(true);
        return new JScrollPane(tabla);
    }

    private JPanel construirPanelBusqueda() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setBorder(BorderFactory.createTitledBorder("Buscar / Listar"));

        JButton btnBuscar = new JButton("Buscar");
        JButton btnListarTodos = new JButton("Listar todos");

        btnBuscar.addActionListener(e -> buscar());
        btnListarTodos.addActionListener(e -> cargarTodos());

        panel.add(new JLabel("Nombre, apellido o código:"));
        panel.add(txtBusqueda);
        panel.add(btnBuscar);
        panel.add(btnListarTodos);

        return panel;
    }

    // ---------- Acciones ----------

    private void registrar() {
        try {
            Alumno alumno = leerFormulario();
            if (alumnoDAO.registrar(alumno)) {
                JOptionPane.showMessageDialog(this, "Alumno registrado correctamente.");
                cargarTodos();
                limpiarFormulario();
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo registrar el alumno.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Datos inválidos", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void editar() {
        if (txtId.getText().isBlank()) {
            JOptionPane.showMessageDialog(this,
                    "Selecciona primero un alumno de la tabla para editar.",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            Alumno alumno = leerFormulario();
            alumno.setId(Integer.parseInt(txtId.getText()));
            if (alumnoDAO.editar(alumno)) {
                JOptionPane.showMessageDialog(this, "Alumno actualizado correctamente.");
                cargarTodos();
                limpiarFormulario();
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo actualizar el alumno.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Datos inválidos", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void eliminar() {
        if (txtId.getText().isBlank()) {
            JOptionPane.showMessageDialog(this,
                    "Selecciona primero un alumno de la tabla para eliminar.",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int confirmacion = JOptionPane.showConfirmDialog(this,
                "¿Seguro que deseas eliminar este alumno?", "Confirmar",
                JOptionPane.YES_NO_OPTION);
        if (confirmacion != JOptionPane.YES_OPTION) {
            return;
        }
        int id = Integer.parseInt(txtId.getText());
        if (alumnoDAO.eliminar(id)) {
            JOptionPane.showMessageDialog(this, "Alumno eliminado correctamente.");
            cargarTodos();
            limpiarFormulario();
        } else {
            JOptionPane.showMessageDialog(this,
                    "No se pudo eliminar. Puede que tenga matrículas asociadas.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void buscar() {
        List<Alumno> resultados = alumnoDAO.buscar(txtBusqueda.getText().trim());
        modeloTabla.setAlumnos(resultados);
    }

    private void cargarTodos() {
        List<Alumno> alumnos = alumnoDAO.listarTodos();
        modeloTabla.setAlumnos(alumnos);
    }

    private void cargarFormularioDesdeFila(int filaVista) {
        int filaModelo = tabla.convertRowIndexToModel(filaVista);
        Alumno a = modeloTabla.getAlumnoEn(filaModelo);
        txtId.setText(String.valueOf(a.getId()));
        txtCodigo.setText(a.getCodigo());
        txtNombre.setText(a.getNombre());
        txtApellido.setText(a.getApellido());
        txtCorreo.setText(a.getCorreo());
        txtTelefono.setText(a.getTelefono());
        txtFechaNacimiento.setText(a.getFechaNacimiento() != null ? a.getFechaNacimiento().toString() : "");
    }

    private void limpiarFormulario() {
        txtId.setText("");
        txtCodigo.setText("");
        txtNombre.setText("");
        txtApellido.setText("");
        txtCorreo.setText("");
        txtTelefono.setText("");
        txtFechaNacimiento.setText("");
        tabla.clearSelection();
    }

    private Alumno leerFormulario() {
        if (txtCodigo.getText().isBlank() || txtNombre.getText().isBlank() || txtApellido.getText().isBlank()) {
            throw new IllegalArgumentException("Código, nombre y apellido son obligatorios.");
        }

        LocalDate fecha = null;
        String textoFecha = txtFechaNacimiento.getText().trim();
        if (!textoFecha.isBlank()) {
            try {
                fecha = LocalDate.parse(textoFecha, formatoFecha);
            } catch (DateTimeParseException e) {
                throw new IllegalArgumentException("La fecha debe tener el formato yyyy-MM-dd.");
            }
        }

        return new Alumno(
                txtCodigo.getText().trim(),
                txtNombre.getText().trim(),
                txtApellido.getText().trim(),
                txtCorreo.getText().trim(),
                txtTelefono.getText().trim(),
                fecha
        );
    }
}
