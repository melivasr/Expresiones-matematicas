module client.interfaz {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.xml;
    requires org.json;
    requires java.desktop;
    requires java.scripting;
    requires com.opencsv;
    requires org.bytedeco.javacpp;
    requires org.bytedeco.openblas;
    requires org.bytedeco.flandmark;
    requires org.bytedeco.tesseract;
    requires javafx.media;
    requires tess4j;
    requires org.bytedeco.javacv;


    opens client.interfaz to javafx.fxml;
    exports client.interfaz;

    opens client.interfaztest to javafx.fxml;
    opens client.modelo to javafx.base;
    exports server.modelo;
    exports utils.Doble;
    exports server;
    exports utils.Cola;
}