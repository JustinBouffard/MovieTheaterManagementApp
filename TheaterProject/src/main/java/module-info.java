module com.example.theaterproject {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.theaterproject to javafx.fxml;
    exports com.example.theaterproject;
}