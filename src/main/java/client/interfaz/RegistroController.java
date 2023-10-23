package client.interfaz;

import client.Comandos;
import client.Juego;
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
import java.io.FileReader;
import java.io.StringReader;
import java.util.List;


public class RegistroController extends Application {

    private Stage esperaStage;
    
    

    @FXML
    private TableView<Registro> tableView;

    @FXML
    private TableColumn<Registro, String> expresionColumn;
    @FXML
    private TableColumn<Registro, String> resultadoColumn;
    @FXML
    private TableColumn<Registro, String> fechaColumn;
    private String nombre;

    @Override
    public void start(Stage primaryStage) throws Exception {
        String nombre = "";
        String comando =  Comandos.GetComandoTableRequest(nombre);

        String resultado = Juego.GetInstance().EjecutarComando(comando);

        Parent root = FXMLLoader.load(getClass().getResource("registro.fxml"));
        primaryStage.setTitle("Registro de Operaciones");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();


    }

    public static void main(String[] args) {
        launch(args);
    }

    @FXML
    public void initialize() {
        expresionColumn.setCellValueFactory(new PropertyValueFactory<>("expresion"));
        resultadoColumn.setCellValueFactory(new PropertyValueFactory<>("resultado"));
        fechaColumn.setCellValueFactory(new PropertyValueFactory<>("fecha"));

        String comando = Comandos.GetComandoTableRequest(Juego.GetInstance().getNombreUsuario());
        String resultado = Juego.GetInstance().EjecutarComando(comando);
        cargarDatosDesdeCSV(resultado);
    }

    private void cargarDatosDesdeCSV(String input) {
        try {
            CSVReader reader = new CSVReader(new StringReader(input));
            List<String[]> registros = reader.readAll();
            reader.close();

            for (int i = 1; i < registros.size(); i++) {
                String[] registro = registros.get(i);
                tableView.getItems().add(new Registro(registro[0], registro[1], registro[2]));
            }
        } catch (CsvException | IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    public void volverASeleccion(ActionEvent actionEvent) throws IOException {
        esperaStage.close();
    }

    @FXML
    public void setEsperaStage(Stage stage) {
        esperaStage = stage;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}

