module org.csjchoisoojong.client {
    requires javafx.controls;
    requires javafx.fxml;
    requires common;


    opens run to javafx.fxml;
    exports org.csjchoisoojong.run;
    exports org.csjchoisoojong.controllers;

    opens controllers to javafx.fxml;
}