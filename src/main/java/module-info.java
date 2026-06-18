module br.pucpr.projeto_crud_java {
    requires javafx.controls;
    requires javafx.base;

    opens br.pucpr.projeto_crud_java.model to javafx.base;

    exports br.pucpr.projeto_crud_java;
    exports br.pucpr.projeto_crud_java.model;
    exports br.pucpr.projeto_crud_java.ui;
}
