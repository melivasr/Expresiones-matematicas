package client.interfaz;

import java.io.IOException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Inicia la aplicación. Carga la escena y muestra la ventana de la aplicación.
 *
 * @throws IOException si hay un problema al cargar el archivo FXML.
 */

public class ApplicationRunner extends Application {
    public Stage esperaControllerStage;
    public Stage seleccionStage;

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

    // Método para obtener la referencia a la ventana de selección
    public Stage getSeleccionStage() {
        return seleccionStage;
    }
    public void CambiarPantalla(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("espera.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(fxmlLoader.load(), 600, 315);
            stage.setTitle("Calculadora Algebraica");
            stage.setScene(scene);
            stage.show();
            this.esperaControllerStage = stage;
            EsperaController esperaController = fxmlLoader.getController();
            esperaController.setEsperaStage(stage);


        } catch (Exception e) {
            System.out.println("No se puede inicar la ventana del juego");
            System.out.println(e.toString());
        }
    }
}