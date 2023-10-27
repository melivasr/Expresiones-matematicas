package client.interfaz;

import client.Comandos;
import client.Socket;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


/**
 * Esta clase controla la ventana principal de la calculadora.
 * @author Melissa Vásquez
 */
public class Window1Controller {

    @FXML
    public Button backBoton;
    public Button camaraBoton;
    public Button obtenerExpresion;

    @FXML
    private Button calcularBoton;

    public Stage window1ControllerStage;

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
     * Inicia la ventana de camara.
     * @param actionEvent El evento de acción.
     */

    public void showCamara(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("camara.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(fxmlLoader.load(), 600, 315);
            stage.setTitle("Cámara");
            stage.setScene(scene);
            stage.show();
            this.window1ControllerStage = stage;
            CamaraController camaraController = fxmlLoader.getController();
            camaraController.setEsperaCamStage(stage);



        } catch (Exception e) {
            System.out.println("No se puede iniciar la ventana de camara");
            e.printStackTrace();;
        }
    }

    /**
     * Inicia la calculadora con la operación ingresada.
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
     * Método que establece la operación lógica.
     *
     * Establece la variable isLogicalOperation en true.
     */
    public void setIsLogicalOperation() {
        isLogicalOperation = true;
    }

    /**
     * Método que obtiene la expresión de una imagen y la muestra en un campo de texto.
     *
     * @param actionEvent El evento que desencadena la obtención de la expresión.
     */
    public void obtenerExpresion(ActionEvent actionEvent) {
        try {
            // Carga la imagen desde el archivo
            File imagenFile = new File("imagenes\\captura.png");
            BufferedImage originalImage = ImageIO.read(imagenFile);

            // Convierte la imagen a formato TIFF (Tesseract requiere este formato)
            File tempFile = File.createTempFile("tempImage", ".tif");
            ImageIO.write(originalImage, "tif", tempFile);

            // Tesseract para reconocer el texto en la imagen
            Tesseract tesseract = new Tesseract();
            tesseract.setTessVariable("user_defined_dpi", "100");
            tesseract.setDatapath("C:\\Program Files\\Tesseract-OCR\\tessdata");
            String whiteListChars = this.isLogicalOperation? "(^|&~)01" : "(-+*/%)0123456789";
            tesseract.setTessVariable("tessedit_char_whitelist", whiteListChars);
            tesseract.setLanguage("eng");
            String resultado = tesseract.doOCR(tempFile);

            campoOperacion.setText(resultado);

        } catch (IOException | TesseractException e) {
            e.printStackTrace();
            System.out.println("Error al obtener la expresión de la imagen.");
        }
    }

}
