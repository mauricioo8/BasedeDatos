package basedatosjava;

import basedatosjava.dao.AlumnoDAO;
import basedatosjava.modelo.Alumno;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class MainConsola {

    private static final Scanner sc = new Scanner(System.in);
    private static final AlumnoDAO alumnoDAO = new AlumnoDAO();
    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static void main(String[] args) {
        int opcion;
        do {
            mostrarMenu();
            opcion = leerEntero("Elige una opción: ");

            switch (opcion) {
                case 1 -> registrarAlumno();
                case 2 -> editarAlumno();
                case 3 -> eliminarAlumno();
                case 4 -> buscarAlumnos();
                case 5 -> listarAlumnos();
                case 0 -> System.out.println("Saliendo del programa...");
                default -> System.out.println("Opción no válida.");
            }
            System.out.println();
        } while (opcion != 0);
    }

    private static void mostrarMenu() {
        System.out.println("===== GESTIÓN DE ALUMNOS =====");
        System.out.println("1. Registrar alumno");
        System.out.println("2. Editar alumno");
        System.out.println("3. Eliminar alumno");
        System.out.println("4. Buscar alumno");
        System.out.println("5. Listar todos los alumnos");
        System.out.println("0. Salir");
    }

    private static void registrarAlumno() {
        System.out.println("--- Registrar nuevo alumno ---");
        String codigo = leerTexto("Código: ");
        String nombre = leerTexto("Nombre: ");
        String apellido = leerTexto("Apellido: ");
        String correo = leerTexto("Correo: ");
        String telefono = leerTexto("Teléfono: ");
        LocalDate fechaNacimiento = leerFecha("Fecha de nacimiento (yyyy-MM-dd, opcional, Enter para omitir): ");

        Alumno alumno = new Alumno(codigo, nombre, apellido, correo, telefono, fechaNacimiento);

        if (alumnoDAO.registrar(alumno)) {
            System.out.println("Alumno registrado correctamente.");
        } else {
            System.out.println("No se pudo registrar el alumno.");
        }
    }

    private static void editarAlumno() {
        System.out.println("--- Editar alumno ---");
        int id = leerEntero("ID del alumno a editar: ");

        System.out.println("Escribe los nuevos datos (los datos se sobrescriben completos):");
        String codigo = leerTexto("Código: ");
        String nombre = leerTexto("Nombre: ");
        String apellido = leerTexto("Apellido: ");
        String correo = leerTexto("Correo: ");
        String telefono = leerTexto("Teléfono: ");
        LocalDate fechaNacimiento = leerFecha("Fecha de nacimiento (yyyy-MM-dd, opcional, Enter para omitir): ");

        Alumno alumno = new Alumno(codigo, nombre, apellido, correo, telefono, fechaNacimiento);
        alumno.setId(id);

        if (alumnoDAO.editar(alumno)) {
            System.out.println("Alumno actualizado correctamente.");
        } else {
            System.out.println("No se pudo actualizar (revisa que el ID exista).");
        }
    }

    private static void eliminarAlumno() {
        System.out.println("--- Eliminar alumno ---");
        int id = leerEntero("ID del alumno a eliminar: ");

        if (alumnoDAO.eliminar(id)) {
            System.out.println("Alumno eliminado correctamente.");
        } else {
            System.out.println("No se pudo eliminar (revisa el ID o si tiene matrículas asociadas).");
        }
    }

    private static void buscarAlumnos() {
        System.out.println("--- Buscar alumno ---");
        String criterio = leerTexto("Escribe nombre, apellido o código a buscar: ");
        List<Alumno> resultados = alumnoDAO.buscar(criterio);
        mostrarLista(resultados);
    }

    private static void listarAlumnos() {
        System.out.println("--- Lista de alumnos ---");
        List<Alumno> alumnos = alumnoDAO.listarTodos();
        mostrarLista(alumnos);
    }

    private static void mostrarLista(List<Alumno> alumnos) {
        if (alumnos.isEmpty()) {
            System.out.println("No se encontraron alumnos.");
            return;
        }
        for (Alumno a : alumnos) {
            System.out.println(a);
        }
    }

    // ---------- Métodos auxiliares de lectura ----------

    private static String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return sc.nextLine();
    }

    private static int leerEntero(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Por favor ingresa un número válido.");
            }
        }
    }

    private static LocalDate leerFecha(String mensaje) {
        System.out.print(mensaje);
        String texto = sc.nextLine().trim();
        if (texto.isEmpty()) {
            return null;
        }
        try {
            return LocalDate.parse(texto, FORMATO_FECHA);
        } catch (DateTimeParseException e) {
            System.out.println("Formato inválido, se guardará sin fecha de nacimiento.");
            return null;
        }
    }
}
