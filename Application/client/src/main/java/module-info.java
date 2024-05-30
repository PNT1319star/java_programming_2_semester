module org.csjchoisoojong.client {
    requires javafx.controls;
    requires javafx.fxml;
    requires common;


    opens org.csjchoisoojong.run to javafx.fxml;
    exports org.csjchoisoojong.run;
    exports org.csjchoisoojong.controllers;

    opens org.csjchoisoojong.controllers to javafx.fxml;
}