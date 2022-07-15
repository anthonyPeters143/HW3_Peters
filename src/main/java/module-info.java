module com.example.hw3_peters {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.hw3_peters to javafx.fxml;
    exports com.example.hw3_peters;
}