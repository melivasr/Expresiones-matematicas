package client.interfaz;

import client.Comandos;
import client.Juego;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
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

    public Stage esperaCamStage;

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

        String comando = isLogicalOperation ?
                Comandos.GetComandoLogicalOperation(campoOperacion.getText(), Juego.GetInstance().getNombreUsuario()) :
                Comandos.GetComandoOperation(campoOperacion.getText(), Juego.GetInstance().getNombreUsuario());

        String resultado = Juego.GetInstance().EjecutarComando(comando);
        this.setResultadoLabel(resultado);
        campoOperacion.setDisable(false);
        calcularBoton.setText("Calculando");
        calcularBoton.setPrefWidth(140);
        calcularBoton.setLayoutX(230);
        calcularBoton.setDisable(false);
    }


    public void showCamara(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("camara.fxml"));
            Stage camStage = new Stage();
            Scene camScene = new Scene(fxmlLoader.load(), 600, 315);
            camStage.setTitle("Reconocimiento de Expresiones");
            camStage.setScene(camScene);
            camStage.show();
            this.esperaCamStage = camStage;
            CamaraController camaraController = fxmlLoader.getController();
            camaraController.setEsperaCamStage(camStage);



        } catch (Exception e) {
            System.out.println("No se puede inicar la ventana de cámara");
            System.out.println(e.toString());
        }
    }


    public void setIsLogicalOperation() {
        isLogicalOperation = true;
    }
}
