module br.pucpr.projeto_crud_java {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;

    opens br.pucpr.projeto_crud_java to javafx.fxml;
    opens br.pucpr.projeto_crud_java.model to javafx.base;
    opens br.pucpr.projeto_crud_java.ui to javafx.fxml;

    exports br.pucpr.projeto_crud_java;
    exports br.pucpr.projeto_crud_java.model;
    exports br.pucpr.projeto_crud_java.ui;
}