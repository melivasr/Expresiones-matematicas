package client.interfaz;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.stage.Stage;
import org.bytedeco.javacv.*;
import org.bytedeco.opencv.opencv_core.Mat;
import org.bytedeco.opencv.opencv_videoio.VideoCapture;

import java.io.ByteArrayInputStream;

public class CamaraController {

    public Stage esperaCamStage;
    private FrameGrabber grabber;

    private VideoCapture capture;

    @FXML
    public Button backBoton;

    @FXML
    public Button takePictureButton;

    @FXML
    public ImageView imageView;

    public CamaraController() {
        grabber = new OpenCVFrameGrabber(0);
    }

    @FXML
    public void initialize() {


        Runnable frameGrabber = () -> {
            try {
                grabber.start();
                while (true) {
                    Frame frame = grabber.grab();

                    Mat mat = new OpenCVFrameConverter.ToMat().convert(frame);
                    if (mat != null) {
                        Image img = toJavaFXImage(mat);
                        imageView.setImage(img);
                    }
                }
            } catch (FrameGrabber.Exception e) {
                throw new RuntimeException(e);
            }
        };

        new Thread(frameGrabber).start();

    }

    @FXML
    public void tomarFoto() {
        // Captura una imagen y guárdala o procesarla según tus necesidades
        try {
            Frame frame = grabber.grab();
            Mat mat = new OpenCVFrameConverter.ToMat().convert(frame);
            if (mat != null) {
                // Guardar la imagen o procesarla
            }
        } catch (FrameGrabber.Exception e) {
            e.printStackTrace();
        }
    }



    @FXML
    public void volverAWindow1(ActionEvent event) {
        esperaCamStage.close();
    }

    public void setEsperaCamStage(Stage stage) {
        esperaCamStage = stage;
    }

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