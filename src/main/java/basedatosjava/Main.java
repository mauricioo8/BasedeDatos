package basedatosjava;

import basedatosjava.vista.VentanaAlumnos;
import basedatosjava.vista.VentanaCursos;

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


            btnAlumnos.addActionListener(e -> {

                VentanaAlumnos ventana = new VentanaAlumnos();
                ventana.setVisible(true);

            });


            btnCursos.addActionListener(e -> {

                VentanaCursos ventana = new VentanaCursos();
                ventana.setVisible(true);

            });


            JPanel panel = new JPanel(new GridLayout(2,1,10,10));

            panel.add(btnAlumnos);
            panel.add(btnCursos);


            menu.add(panel);

            menu.setVisible(true);

        });
    }
}