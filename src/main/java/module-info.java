module cca.transfer {
    requires transitive javafx.controls;
    requires javafx.fxml;
    requires org.apache.poi.ooxml;

    opens cca.transfer to javafx.fxml;
    exports cca.transfer;
}
