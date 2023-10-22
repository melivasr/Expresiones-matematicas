package client.interfaz;

import client.Comandos;
import client.Juego;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class Window1Controller {

    @FXML
    public Button backBoton;
    public Button camaraBoton;

    @FXML
    private Button calcularBoton;


    @FXML
    private TextField campoOperacion;

    // Etiqueta para mostrar la posición del jugador en la cola
    @FXML
    private Label resultado;
    private String nombre;


    public void setResultadoLabel(String resultadoLabel) {
        this.resultado.setText(resultadoLabel);
    }

    // Variable para almacenar la instancia de Stage
    private Stage esperaStage;

    private boolean isLogicalOperation;

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
        if(campoOperacion.getText().isEmpty())
        {
            return;
        }
        Juego.GetInstance().setEsperaController(this);
        String comando = isLogicalOperation ?
                Comandos.GetComandoLogicalOperation(campoOperacion.getText(), nombre) :
                Comandos.GetComandoOperation(campoOperacion.getText(), nombre);

        Juego.GetInstance().Conectarse(comando);

        campoOperacion.setDisable(false);
        calcularBoton.setText("Calculando");
        calcularBoton.setPrefWidth(140);
        calcularBoton.setLayoutX(230);
        calcularBoton.setDisable(false);
    }

    public void setIsLogicalOperation() {
        isLogicalOperation = true;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
