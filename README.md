# Gestión de Alumnos (Integrante 1)

Módulo de gestión de alumnos para el trabajo grupal "Sistema Académico".

## Requisitos previos

- JDK 17 o superior
- Maven (o la extensión "Extension Pack for Java" de VSCode, que ya lo incluye)
- MySQL Server corriendo, con la base de datos `sistema_academico` ya creada

## Pasos para ejecutar el proyecto

1. **Crear la tabla `alumnos`**
   Abre MySQL Workbench y ejecuta el script `sql/alumnos.sql`.

2. **Configurar tu contraseña local**
   Copia el archivo de ejemplo y edítalo con tu propia contraseña de MySQL:

   ```
   cp src/main/resources/config.properties.example src/main/resources/config.properties
   ```

   Luego abre `config.properties` y reemplaza `TU_CONTRASENA_AQUI` por tu contraseña real.
   Este archivo **no se sube a Git** (está en `.gitignore`), así que cada integrante
   pone la suya sin afectar a los demás.

3. **Ejecutar el programa**

   Desde la terminal, dentro de la carpeta del proyecto:

   ```
   mvn compile exec:java
   ```

   O desde VSCode: clic derecho sobre `Main.java` → "Run Java".

   Esto abre la interfaz gráfica (Swing) con el formulario, los botones
   (Registrar, Editar, Eliminar) y la tabla de alumnos. Haz clic en una fila
   de la tabla para cargar sus datos en el formulario antes de editar o eliminar.

   Si prefieres el menú de consola (versión anterior), ejecuta en su lugar
   la clase `MainConsola.java`.

## Estructura del proyecto

```
gestion-alumnos/
├── pom.xml
├── sql/
│   └── alumnos.sql              -> script de creación de la tabla
├── src/main/resources/
│   └── config.properties.example -> plantilla de configuración
└── src/main/java/basedatosjava/
    ├── ConexionBD.java          -> maneja la conexión a MySQL
    ├── Main.java                -> menú de consola
    ├── modelo/
    │   └── Alumno.java          -> representa un registro de la tabla alumnos
    └── dao/
        └── AlumnoDAO.java       -> lógica SQL: registrar, editar, eliminar, buscar, listar
```

## Integración con el resto del equipo

- La tabla `alumnos` expone la columna `id`, que el Integrante 3 usará como
  clave foránea (`alumno_id`) en la tabla `matriculas`.
- Si necesitas agregar una columna nueva a `alumnos`, avisa al equipo antes
  de modificar el script `sql/alumnos.sql`, para que todos actualicen su
  base de datos local por igual.
