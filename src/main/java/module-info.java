module com.gasoiltracker {
    requires javafx.base;  // Requiere el módulo de JavaFX base
    requires javafx.controls;  // Si usas controles JavaFX
    requires javafx.fxml;  // Si usas FXML
    requires javafx.graphics;

    opens com.gasoiltracker.controller to javafx.fxml;
    opens com.gasoiltracker.model to javafx.base;  // Abre el paquete com.gasoiltracker.model a javafx.base
    
    exports com.gasoiltracker;  // Asegúrate de exportar tu paquete
}

