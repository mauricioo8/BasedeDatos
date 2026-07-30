package basedatosjava;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Clase encargada de crear la conexión con la base de datos.
 * Lee los datos (host, usuario, clave) desde config.properties
 * para que cada integrante del equipo pueda usar su propia
 * contraseña local sin modificar el código Java.
 */
public class ConexionBD {

    private static final Properties propiedades = new Properties();

    static {
        try (InputStream entrada =
                ConexionBD.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (entrada == null) {
                throw new RuntimeException(
                        "No se encontró config.properties en src/main/resources");
            }
            propiedades.load(entrada);
        } catch (IOException e) {
            throw new RuntimeException("Error al leer config.properties: " + e.getMessage());
        }
    }

    public static Connection obtenerConexion() throws SQLException {
        String url = propiedades.getProperty("db.url");
        String usuario = propiedades.getProperty("db.usuario");
        String clave = propiedades.getProperty("db.clave");
        return DriverManager.getConnection(url, usuario, clave);
    }
}
