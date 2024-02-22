module com.example.foodiesfavgui {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.foodiesfavgui to javafx.fxml;
    exports com.example.foodiesfavgui;
}