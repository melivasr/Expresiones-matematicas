package client.interfaz;

import client.Comandos;
import client.Socket;
import com.opencsv.exceptions.CsvException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import java.io.IOException;
import com.opencsv.CSVReader;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import client.modelo.Registro;

import java.io.StringReader;
import java.util.List;

/**
 * Clase RegistroController que extiende de Application.
 * Esta clase se encarga de gestionar la interfaz de usuario
 * para el registro de las operaciones realizadas en la calculadora.
 *
 * @author Melissa Vásquez
 */
public class RegistroController extends Application {

    private Stage esperaStage;

    // Declaración de las variables de la tabla de registros
    @FXML
    private TableView<Registro> tableView;

    @FXML
    private TableColumn<Registro, String> expresionColumn;
    @FXML
    private TableColumn<Registro, String> resultadoColumn;
    @FXML
    private TableColumn<Registro, String> fechaColumn;
    private String nombre;
    /**
     * Método start que se ejecuta al iniciar la aplicación.
     *
     * @param primaryStage - Es el escenario principal de la aplicación.
     * @throws Exception - Excepción general
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        String nombre = "";
        String comando =  Comandos.GetComandoTableRequest(nombre);
        String resultado = Socket.GetInstance().EjecutarComando(comando);

        // Carga del archivo FXML y configuración del escenario
        Parent root = FXMLLoader.load(getClass().getResource("registro.fxml"));
        primaryStage.setTitle("Registro de Operaciones");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }

    /**
     * Método main que se ejecuta al iniciar la aplicación.
     *
     * @param args - Argumentos de la línea de comandos
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Método initialize que se ejecuta al inicializar la interfaz de usuario.
     * <p>
     * Este método se encarga de configurar las columnas de la tabla y
     * cargar los datos desde un archivo CSV.
     */
    @FXML
    public void initialize() {
        // Configuración de las columnas de la tabla
        expresionColumn.setCellValueFactory(new PropertyValueFactory<>("expresion"));
        resultadoColumn.setCellValueFactory(new PropertyValueFactory<>("resultado"));
        fechaColumn.setCellValueFactory(new PropertyValueFactory<>("fecha"));

        String comando = Comandos.GetComandoTableRequest(Socket.GetInstance().getNombreUsuario());

        String resultado = Socket.GetInstance().EjecutarComando(comando);

        cargarDatosDesdeCSV(resultado);
    }

    /**
     * Método para cargar datos desde un CSV en la tabla
     * @param input - Cadena de texto en formato CSV
     */
    private void cargarDatosDesdeCSV(String input) {
        try {
            // Crea un lector de CSV a partir de la cadena de entrada
            CSVReader reader = new CSVReader(new StringReader(input));
            List<String[]> registros = reader.readAll();
            reader.close();

            // Itera sobre cada registro en el CSV y lo agrega a la tabla
            for (int i = 1; i < registros.size(); i++) {
                String[] registro = registros.get(i);
                tableView.getItems().add(new Registro(registro[0], registro[1], registro[2]));
            }
        } catch (CsvException | IOException e) {
            // Lanza una excepción en caso de que ocurra un error al leer el CSV o agregar los datos a la tabla
            throw new RuntimeException(e);
        }
    }

    /**
     * Método para volver a la selección anterior al cerrar la ventana de espera
     * @param actionEvent - Evento de acción del método
     * @throws IOException - Excepción que se lanza si hay un error al cerrar la ventana
     */
    @FXML
    public void volverASeleccion(ActionEvent actionEvent) throws IOException {
        esperaStage.close();
    }


    /**
     * Método para establecer la etapa de espera
     * @param stage - Etapa que se establece como la etapa de espera
     */
    @FXML
    public void setEsperaStage(Stage stage) {
        esperaStage = stage;
    }

    /**
     * Método para establecer el nombre
     * @param nombre - Nombre que se establece
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}

