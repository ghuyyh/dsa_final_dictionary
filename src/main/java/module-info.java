module dsa {
    requires transitive javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;

    opens dsa to javafx.fxml;
    opens dsa.model to com.google.gson;
    exports dsa;
    exports dsa.model;
    exports dsa.index;
    exports dsa.loader;
    exports dsa.benchmark;
}
