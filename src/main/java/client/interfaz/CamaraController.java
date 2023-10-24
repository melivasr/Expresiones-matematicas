package client.interfaz;

import client.Juego;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.opencv.opencv_core.IplImage;
import org.bytedeco.opencv.opencv_core.Mat;
import org.bytedeco.opencv.opencv_videoio.VideoCapture;
import org.bytedeco.javacv.JavaFXFrameConverter;
import org.bytedeco.javacv.OpenCVFrameGrabber;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CamaraController {

    public Stage esperaCamStage;


    @FXML
    public Button backBoton;

    @FXML
    public Button takePictureButton;


    public CamaraController() throws FrameGrabber.Exception {}

    @FXML
    public void setEsperaCamStage(Stage stage) {
        esperaCamStage = stage;
    }


    FrameGrabber grabber = new OpenCVFrameGrabber(0);

    private final JavaFXFrameConverter converter = new JavaFXFrameConverter();

    Frame frame = grabber.grab();

    Image img = converter.convert(frame);

    @FXML
    public ImageView imageView = new ImageView(img);






    /*
    private JavaFXFrameConverter converter = new JavaFXFrameConverter();

        public void initialize() {
            try {
                // Initialize webcam access
                videoCapture = new VideoCapture(0);
                videoCapture.start();

                // Update the image view with the webcam feed
                Runnable frameGrabber = () -> {
                    try {
                        Mat frame = videoCapture.grab();
                        imageView.setImage(converter.convert(frame));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                };

                new Thread(frameGrabber).start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @FXML
        private void takePhoto() {
            if (videoCapture != null) {
                try {
                    Mat frame = videoCapture.grab();
                    // Save the frame as an image (you need to implement this)
                    // For saving, you can use Java's ImageIO or a library like JavaCV.
                    // Also, display the image in your JavaFX application.
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

     */


}
