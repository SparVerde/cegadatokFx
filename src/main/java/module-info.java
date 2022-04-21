module com.example.cegadatokfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.cegadatokfx to javafx.fxml;
    exports com.example.cegadatokfx;
}