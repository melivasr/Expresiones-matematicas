package client.interfaz;

import client.Comandos;
import client.Juego;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import javafx.scene.Scene;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

public class RegistroController extends Application {

    private Stage esperaStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        String nombre = "";
        String comando =  Comandos.GetComandoTableRequest(nombre);

        Juego.GetInstance().Conectarse(comando);

        Parent root = FXMLLoader.load(getClass().getResource("registro.fxml"));
        primaryStage.setTitle("Registro de Operaciones");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    @FXML
    public void volverASeleccion(ActionEvent actionEvent) throws IOException {
        esperaStage.close();
    }

    @FXML
    public void setEsperaStage(Stage stage) {
        esperaStage = stage;
    }
}

