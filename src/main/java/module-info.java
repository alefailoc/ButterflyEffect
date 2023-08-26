module com.example.butterflyeffect {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires javafx.swing;
    requires com.google.gson;


    opens com.example.butterflyeffect to javafx.fxml;
    exports com.example.butterflyeffect;
}