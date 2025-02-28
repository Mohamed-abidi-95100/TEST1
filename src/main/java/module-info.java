module com.example.stage {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;

    requires java.sql; // Pour la base de données
    requires jbcrypt; // Pour le hachage de mots de passe

    requires org.apache.pdfbox;
        // Ensure the PDFBox library (e.g., pdfbox-<version>.jar) is added to your module path or as a dependency in your build system.

    opens com.example.stage to javafx.fxml, com.example.stage; // Ouvre le package principal pour les FXML et le module lui-même
    opens com.example.stage.controller to javafx.fxml, com.example.stage; // Ajoutez cette ligne pour ouvrir le package controller
    exports com.example.stage; // Exporte le package principal
}