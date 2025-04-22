module org.example.ormfinal_cw {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.ormfinal_cw to javafx.fxml;
    exports org.example.ormfinal_cw;
}