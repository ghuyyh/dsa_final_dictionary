module dsa {
    requires transitive javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;

    opens dsa to javafx.fxml;
    exports dsa;
}
