module lat.jbluesoft.demofx {
    requires javafx.controls;
    requires javafx.fxml;


    opens lat.jbluesoft.demofx to javafx.fxml;
    exports lat.jbluesoft.demofx;
}