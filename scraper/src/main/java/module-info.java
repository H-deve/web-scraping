module scraping.test.webscraping {

    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;


    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires mysql.connector.j;
    requires htmlunit;
    requires jdk.xml.dom;
    requires htmlunit.cssparser;
    requires org.json;
//    requires kernel;
//    requires layout;
    requires org.apache.poi.poi;
    requires itextpdf;
    requires sib.api.v3.sdk;
    requires com.google.gson;
    requires java.sql;
    requires org.apache.pdfbox;
    requires javafx.swing;

    opens fr.cda.campingcar to javafx.fxml;
    exports fr.cda.campingcar;
    exports fr.cda.campingcar.util.App;
    opens fr.cda.campingcar.util.App to javafx.fxml;
    exports fr.cda.campingcar.Controller;
    opens fr.cda.campingcar.Controller to javafx.fxml;
}