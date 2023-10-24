package server.modelo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * La clase RegistroOperaciones se encarga de registrar las operaciones realizadas en la calculadora.
 * Las operaciones se almacenan en archivos CSV con el nombre de usuario como parte del nombre del archivo.
 */
public class RegistroOperaciones {

    /**
     * La ruta base para los archivos de registro de operaciones.
     */
    private static final String FILE_PATH = "data/registro_operaciones_";
    private static final String CSV = ".csv";
    private static final String CSV_HEADER = "Expresion, Resultado, Fecha";

    /**
     * Lee las operaciones registradas para un usuario específico.
     *
     * @param nombre El nombre del usuario para el que se deben leer las operaciones.
     * @return Una cadena con las operaciones registradas para el usuario, o una cadena vacía si no hay operaciones o si ocurre un error.
     */
    public static String leerOperaciones(String nombre) {
        try {
            return Files.readString(Paths.get(FILE_PATH + nombre + CSV));
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
    /**
     * Lee las operaciones registradas para un usuario específico.
     *
     * @param nombre El nombre del usuario para el que se deben leer las operaciones.
     * @return Una cadena con las operaciones registradas para el usuario, o una cadena vacía si no hay operaciones o si ocurre un error.
     * @throws Exception Si ocurre un error al leer las operaciones.
     */
    public static void registrarOperacion(String nombre, String expresion, String resultado) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String fecha = dateFormat.format(new Date());
            String registro = String.format("%s,%s,%s", expresion, resultado, fecha);
            String userPath = FILE_PATH + nombre + CSV;

            File file = new File(userPath);
            boolean fileExists = file.exists();

            FileWriter fileWriter = new FileWriter(userPath, true);

            if (!fileExists) {
                fileWriter.append(CSV_HEADER);
                fileWriter.append("\n");
            }
            fileWriter.append(registro);
            fileWriter.append("\n");
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


