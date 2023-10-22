package client.interfaz;

import client.Juego;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utils.Doble.ListaDoble;

import java.awt.*;
import java.io.IOException;

public class EsperaController {

    @FXML
    public Button backBoton;

    @FXML
    private Button calcularBoton;


    @FXML
    private TextField campoNombre;

    // Etiqueta para mostrar la posición del jugador en la cola
    @FXML
    private Label posicionCola;

    public void setResultadoLabel(String resultadoLabel) {
        this.posicionCola.setText(resultadoLabel);
    }

    // Variable para almacenar la instancia de Stage
    private Stage esperaStage;

    @FXML
    public void volverASeleccion(ActionEvent actionEvent) throws IOException {
        esperaStage.close();
    }

    @FXML
    public void setEsperaStage(Stage stage) {
        esperaStage = stage;
    }
    @FXML
    public void iniciarCalculadora(ActionEvent event) {
        if(campoNombre.getText().isEmpty())
        {
            return;
        }
        Juego.GetInstance().setEsperaController(this);
        Juego.GetInstance().Conectarse(campoNombre.getText());

        String expresion = campoNombre.getText();
        campoNombre.setDisable(false);
        calcularBoton.setText("Calculando");
        calcularBoton.setPrefWidth(140);
        calcularBoton.setLayoutX(230);
        calcularBoton.setDisable(false);
    }
    /**
     * Reinicia la ventana del juego. Cierra la ventana actual y habilita
     * nuevamente el campo de nombre y el botón de conexión.
     */
    public void resetWindow()
    {
        this.esperaStage.close();
        campoNombre.setDisable(false);
        calcularBoton.setText("Conectarse");
        calcularBoton.setPrefWidth(70);
        calcularBoton.setLayoutX(266);
        calcularBoton.setDisable(false);
    }
}
