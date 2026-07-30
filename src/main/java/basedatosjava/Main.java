package basedatosjava;

import basedatosjava.vista.VentanaAlumnos;
import basedatosjava.vista.VentanaCursos;
import basedatosjava.vista.VentanaMatriculas;
import basedatosjava.vista.VentanaNotas;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            JFrame menu = new JFrame("Sistema Gestión Académica");

            menu.setSize(350,200);
            menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            menu.setLocationRelativeTo(null);


            JButton btnAlumnos = new JButton("Gestión de Alumnos");
            JButton btnCursos = new JButton("Gestión de Cursos");
            JButton btnMatriculas = new JButton("Gestión de Matrículas");
            JButton btnNotas = new JButton("Gestión de Notas");

            btnAlumnos.addActionListener(e -> {

                VentanaAlumnos ventana = new VentanaAlumnos();
                ventana.setVisible(true);

            });


            btnCursos.addActionListener(e -> {

                VentanaCursos ventana = new VentanaCursos();
                ventana.setVisible(true);

            });

              btnMatriculas.addActionListener(e -> {

    JOptionPane.showMessageDialog(null, "Antes de crear la ventana");

    VentanaMatriculas ventana = new VentanaMatriculas();

ventana.setVisible(true);

JOptionPane.showMessageDialog(null, "La ventana ya debería estar visible");

});



           btnNotas.addActionListener(e -> {
    
               VentanaNotas ventana = new VentanaNotas();
               ventana.setVisible(true);

});

            JPanel panel = new JPanel(new GridLayout(4,1,10,10));


            panel.add(btnAlumnos);
            panel.add(btnCursos);
            panel.add(btnMatriculas);
            panel.add(btnNotas);
            
            


            menu.add(panel);

            menu.setVisible(true);

        });
    }
}