module com.example.theaterproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.desktop;


    opens com.example.theaterproject to javafx.fxml;
    exports com.example.theaterproject;
    opens com.example.theaterproject.Controllers to javafx.fxml;
}