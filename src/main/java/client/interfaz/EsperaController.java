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

public class EsperaController {

    // Lista para almacenar los jugadores
    ListaDoble<String> jugadores = new ListaDoble<String>();

    // Lista para almacenar los jugadores
    @FXML
    private Button aceptarNombre;

    @FXML
    private Label resultadoLabel;

    // Botón para abrir la ventana del juego
    @FXML
    private Button calcularBoton;

    // Botón para ver la posición del jugador en la cola
    @FXML
    private Button verPosicion;

    // Campo de texto para ingresar el nombre del jugador
    @FXML
    private TextField campoNombre;

    // Etiqueta para mostrar la posición del jugador en la cola
    @FXML
    private Label posicionCola;

    public void setResultadoLabel(String resultadoLabel) {
        this.posicionCola.setText(resultadoLabel);
    }

    // Variable para almacenar la instancia de Stage
    public Stage gameControllerStage;


    /**
     * Actualiza la posición del jugador en la cola.
     *
     * @param event El evento que activa el método.
     */
    @FXML
    public void actualizarPosicion(ActionEvent event){
        posicionCola.setText(String.valueOf(jugadores.getNodePosition(campoNombre.getText())));
        System.out.println(jugadores.getNodePosition(campoNombre.getText()));
    }
    public ListaDoble<String> getJugadores() {
        return jugadores;
    }

    /**
     * Cambia la pantalla del juego.
     *
     * @param nombre El nombre del jugador.
     * @param gameController El controlador del juego.
     */
    public void CambiarPantalla(String nombre, Object gameController)
    {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("game1.fxml"));
            Stage stage= new Stage();
            Scene scene = new Scene(fxmlLoader.load(), 624, 750);
            stage.setTitle("Calculadora");
            stage.setScene(scene);
            stage.show();
            this.gameControllerStage = stage;

            gameController = fxmlLoader.getController();

        }catch (Exception e){
            System.out.println("No se puede inicar la ventana del juego");
            System.out.println(e.toString());
        }

    }


    /**
     * Inicia el juego. Si el campo de nombre está vacío, no se hace nada.
     * Si no está vacío, se conecta al juego con el nombre proporcionado.
     *
     * @param event el evento de acción que activó este método.
     */
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
        this.gameControllerStage.close();
        campoNombre.setDisable(false);
        calcularBoton.setText("Conectarse");
        calcularBoton.setPrefWidth(70);
        calcularBoton.setLayoutX(266);
        calcularBoton.setDisable(false);
    }
}
