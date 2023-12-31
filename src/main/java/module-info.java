module br.fipp.sisdentalfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires java.sql;
    requires java.desktop;
    requires java.logging;
    requires org.json;
    requires jasperreports;

    opens br.fipp.sisdentalfx to javafx.fxml;
    opens br.fipp.sisdentalfx.db.entidades to javafx.fxml;
    exports br.fipp.sisdentalfx;
    exports br.fipp.sisdentalfx.db.entidades;
}