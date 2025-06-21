module com.gasoiltracker {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    opens com.gasoiltracker.controller to javafx.fxml;
    exports com.gasoiltracker;
}
