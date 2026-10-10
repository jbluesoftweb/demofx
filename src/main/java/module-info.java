module lat.jbluesoft.demofx {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;


    opens lat.jbluesoft.demofx to javafx.base;
    opens lat.jbluesoft.demofx.models to javafx.base;

    exports lat.jbluesoft.demofx;
}