package server.modelo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RegistroOperaciones {

    private static final String CSV_FILE_PATH = "data/registro_operaciones.csv";
    private static final String CSV_HEADER = "Expresion, Resultado, Fecha";

    public static void registrarOperacion(String expresion, String resultado) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String fecha = dateFormat.format(new Date());
            String registro = String.format("%s,%s,%s",expresion, resultado, fecha);

            File file = new File(CSV_FILE_PATH);
            boolean fileExists = file.exists();

            FileWriter fileWriter = new FileWriter(CSV_FILE_PATH, true);

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


