package client.interfaz;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.bytedeco.javacv.*;
import org.bytedeco.opencv.opencv_core.Mat;
import java.io.File;

/**
 * Clase controlador para la funcionalidad de la cámara.
 */
public class CamaraController {

    public Stage esperaCamStage;
    private FrameGrabber grabber;
    private Thread frameGrabberThread; // Variable para mantener una referencia al hilo de captura

    @FXML
    public Button backBoton;

    @FXML
    public Button takePictureButton;

    @FXML
    public ImageView imageView;

    @FXML
    public ImageView imageViewResult;

    /**
     * Constructor de la clase CamaraController.
     */
    public CamaraController() {
        grabber = new OpenCVFrameGrabber(0);
    }

    /**
     * Método para inicializar la captura de frames de la cámara.
     */
    @FXML
    public void initialize() {

        Runnable frameGrabber = () -> {
            try {
                grabber.start();
                while (!Thread.currentThread().isInterrupted()) {
                    Frame frame = grabber.grab();

                    Mat mat = new OpenCVFrameConverter.ToMat().convert(frame);
                    if (mat != null) {
                        Image img = toJavaFXImage(mat);
                        imageView.setImage(img);
                    }
                }

                grabber.close();
            } catch (FrameGrabber.Exception e) {
                throw new RuntimeException(e);
            }
        };

        frameGrabberThread = new Thread(frameGrabber);
        frameGrabberThread.setDaemon(true);
        frameGrabberThread.start();
    }

    /**
     * Método para tomar una foto con la cámara.
     */
    public void tomarFoto() {
        try {
            Frame frame = grabber.grab();
            Mat mat = new OpenCVFrameConverter.ToMat().convert(frame);

            if (mat != null) {
                Image img = toJavaFXImage(mat);
                imageViewResult.setImage(img);

                File imagesDirectory = new File("imagenes");
                if (!imagesDirectory.exists()) {
                    imagesDirectory.mkdirs();
                }
                String filename = "captura.png";
                File imageFile = new File(imagesDirectory, filename);

                if (imageFile.exists()) {
                    imageFile.delete();
                }

                org.bytedeco.opencv.global.opencv_imgcodecs.imwrite(imageFile.getAbsolutePath(), mat);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Método para volver a la pantalla anterior y cerrar la cámara.
     * @param event Evento de acción.
     */
    @FXML
    public void volverAWindow1(ActionEvent event) {
        // Detener el hilo de captura de frames
        frameGrabberThread.interrupt();

        esperaCamStage.close();
    }
    /**
     * Establece la instancia de la clase Stage que se utilizará para mostrar la interfaz de usuario de la calculadora.
     *
     * @param stage Es la instancia de la clase Stage que se utilizará.
     */
    public void setEsperaCamStage(Stage stage) {
        esperaCamStage = stage;
    }

    /**
     * Convierte una instancia de la clase Mat en una imagen JavaFX.
     *
     * @param mat Es la instancia de la clase Mat que se convertirá en una imagen JavaFX.
     * @return Una instancia de la clase Image que representa la imagen JavaFX correspondiente a la instancia de Mat proporcionada.
     */
    private Image toJavaFXImage(Mat mat) {
        int width = mat.cols();
        int height = mat.rows();
        int channels = mat.channels();

        byte[] buffer = new byte[width * height * channels];
        mat.data().get(buffer);

        WritableImage writableImage = new WritableImage(width, height);
        PixelWriter pixelWriter = writableImage.getPixelWriter();

        int pixelIndex = 0;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int red = buffer[pixelIndex] & 0xFF;
                int green = buffer[pixelIndex + 1] & 0xFF;
                int blue = buffer[pixelIndex + 2] & 0xFF;
                int alpha = 255; // Assuming 3 channels (BGR)

                pixelWriter.setArgb(x, y, (alpha << 24) | (blue << 16) | (green << 8) | red);
                pixelIndex += channels;
            }
        }

        return writableImage;
    }

}