package client.interfaz;

import java.io.IOException;

import client.Socket;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Inicia la aplicación. Carga la escena y muestra la ventana de la aplicación.
 *
 * @throws IOException si hay un problema al cargar el archivo FXML.
 * @author Melissa Vásquez
 */

public class ApplicationRunner extends Application {
    public Stage esperaControllerStage;
    public Stage seleccionStage;
    public Button expresionesEvaluadas;
    public TextField campoNombre;

    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(ApplicationRunner.class.getResource("seleccion.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 315);
        stage.setTitle("Calculator");
        stage.setScene(scene);
        stage.show();

        seleccionStage = stage;
    }

    /**
     * Punto de entrada de la aplicación. Lanza la aplicación.
     *
     * @param args los argumentos de la línea de comandos.
     */
    public static void main(String[] args) {
        launch();
    }

    public void iniciarCalculadora(ActionEvent actionEvent) {
    }
    public Stage getSeleccionStage() {
        return seleccionStage;
    }

    /**
     * Muestra la ventana de la calculadora algebraica.
     *
     * @param actionEvent el evento de acción de este método.
     */
    public void ShowWindow1(ActionEvent actionEvent) {
        try {
            String nombre = campoNombre.getText();
            Socket.GetInstance().setNombreUsuario(nombre);
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("window1.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(fxmlLoader.load(), 600, 315);
            stage.setTitle("Calculadora Algebraica");
            stage.setScene(scene);
            stage.show();
            this.esperaControllerStage = stage;
            Window1Controller window1Controller = fxmlLoader.getController();
            window1Controller.setEsperaStage(stage);



        } catch (Exception e) {
            System.out.println("No se puede inicar la ventana del juego");
            System.out.println(e.toString());
        }
    }

    /**
     * Muestra la ventana de la calculadora booleana.
     *
     * @param actionEvent el evento de acción de este método.
     */
    public void ShowWindow2(ActionEvent actionEvent) {
        try {
            String nombre = campoNombre.getText();
            Socket.GetInstance().setNombreUsuario(nombre);
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("window1.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(fxmlLoader.load(), 600, 315);
            stage.setTitle("Calculadora Lógica");
            stage.setScene(scene);
            stage.show();
            this.esperaControllerStage = stage;
            Window1Controller window1Controller = fxmlLoader.getController();
            window1Controller.setEsperaStage(stage);
            window1Controller.setIsLogicalOperation();


        } catch (Exception e) {
            System.out.println("No se puede inicar la ventana del juego");
            System.out.println(e.toString());
        }
    }

    /**
     * Muestra la ventana de registro de operaciones.
     *
     * @param actionEvent el evento de acción de este método.
     */
    public void ShowRegistro(ActionEvent actionEvent) {
        try {
            String nombre = campoNombre.getText();
            Socket.GetInstance().setNombreUsuario(nombre);
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("registro.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(fxmlLoader.load(), 600, 315);
            stage.setTitle("Registro de Operaciones");
            stage.setScene(scene);
            stage.show();
            this.esperaControllerStage = stage;
            RegistroController registroController = fxmlLoader.getController();
            registroController.setEsperaStage(stage);


        } catch (Exception e) {
            System.out.println("No se puede inicar la ventana del juego");
            e.printStackTrace();
        }
    }

}