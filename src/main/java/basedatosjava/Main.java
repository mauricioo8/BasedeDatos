package basedatosjava;

import basedatosjava.vista.VentanaAlumnos;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        // Swing debe ejecutarse en su propio hilo (Event Dispatch Thread)
        SwingUtilities.invokeLater(() -> {
            VentanaAlumnos ventana = new VentanaAlumnos();
            ventana.setVisible(true);
        });
    }
}
