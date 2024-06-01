module org.csjchoisoojong.client {
    requires javafx.controls;
    requires javafx.fxml;
    requires common;
    requires org.controlsfx.controls;
    requires com.jfoenix;
    requires jidefx.common;


    opens org.csjchoisoojong.run to javafx.fxml;
    exports org.csjchoisoojong.run;
    exports org.csjchoisoojong.controllers;
    exports org.csjchoisoojong.connector;
    exports org.csjchoisoojong.processing;
    exports org.csjchoisoojong.controllers.tools;

    opens org.csjchoisoojong.controllers to javafx.fxml;

}