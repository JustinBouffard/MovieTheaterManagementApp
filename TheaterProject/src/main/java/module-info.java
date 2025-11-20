module com.example.theaterproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;


    opens com.example.theaterproject to javafx.fxml;
    exports com.example.theaterproject;
}