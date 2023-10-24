package client.interfaz;

import client.Comandos;
import client.Socket;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Esta clase controla la ventana principal de la calculadora.
 * @author Melissa Vásquez
 */
public class Window1Controller {

    @FXML
    public Button backBoton;
    public Button camaraBoton;

    @FXML
    private Button calcularBoton;


    @FXML
    private TextField campoOperacion;

    // Etiqueta para mostrar el resultado
    @FXML
    private Label resultado;
    private String nombre;

    /**
     * Establece el texto del label de resultado.
     * @param resultadoLabel El texto a establecer.
     */
    public void setResultadoLabel(String resultadoLabel) {
        this.resultado.setText(resultadoLabel);
    }

    // Variable para almacenar la instancia de Stage
    private Stage esperaStage;

    private boolean isLogicalOperation;


    /**
     * Cierra la ventana de la calculadora.
     * @param actionEvent El evento de acción.
     * @throws IOException Si ocurre un error al cerrar la ventana.
     */
    @FXML
    public void volverASeleccion(ActionEvent actionEvent) throws IOException {
        esperaStage.close();
    }

    /**
     * Establece la instancia de Stage de espera.
     * @param stage La instancia de Stage a establecer.
     */
    @FXML
    public void setEsperaStage(Stage stage) {
        esperaStage = stage;
    }
    /**
     * Inicia la calculadora.
     * @param event El evento de acción.
     */
    @FXML
    public void iniciarCalculadora(ActionEvent event) {
        if(campoOperacion.getText().isEmpty())
        {
            return;
        }

        String comando = isLogicalOperation ?
                Comandos.GetComandoLogicalOperation(campoOperacion.getText(), Socket.GetInstance().getNombreUsuario()) :
                Comandos.GetComandoOperation(campoOperacion.getText(), Socket.GetInstance().getNombreUsuario());

        String resultado = Socket.GetInstance().EjecutarComando(comando);
        this.setResultadoLabel(resultado);
        campoOperacion.setDisable(false);
        calcularBoton.setText("Calcular");
        calcularBoton.setPrefWidth(140);
        calcularBoton.setLayoutX(230);
        calcularBoton.setDisable(false);
    }
    /**
     * Establece la operación lógica.
     */
    public void setIsLogicalOperation() {
        isLogicalOperation = true;
    }
}
