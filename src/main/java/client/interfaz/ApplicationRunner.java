package client.interfaz;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Inicia la aplicación. Carga la escena y muestra la ventana de la aplicación.
 *
 * @throws IOException si hay un problema al cargar el archivo FXML.
 */

public class ApplicationRunner extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(ApplicationRunner.class.getResource("espera.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 315);
        stage.setTitle("Calculator");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Punto de entrada de la aplicación. Lanza la aplicación.
     *
     * @param args los argumentos de la línea de comandos.
     */
    public static void main(String[] args) {
        launch();
    }
}