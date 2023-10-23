package server.modelo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RegistroOperaciones {

    private static final String FILE_PATH = "data/registro_operaciones_";
    private static final String CSV = ".csv";
    private static final String CSV_HEADER = "Expresion, Resultado, Fecha";

    public static String leerOperaciones(String nombre) {
        try {
            return Files.readString(Paths.get(FILE_PATH + nombre + CSV));
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

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


