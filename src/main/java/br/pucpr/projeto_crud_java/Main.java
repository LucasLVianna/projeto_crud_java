package br.pucpr.projeto_crud_java;

import br.pucpr.projeto_crud_java.ui.*;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        stage.setTitle("MyKeeper");

        Button btnProduto          = new Button("Produtos");
        Button btnCategoria        = new Button("Categorias");
        Button btnEstoque          = new Button("Estoque");
        Button btnContaSuporte     = new Button("Contas de Suporte");
        Button btnTicketSuporte    = new Button("Tickets de Suporte");
        Button btnReceita          = new Button("Receitas");
        Button btnListaCompras     = new Button("Lista de Compras");
        Button btnMetaConsumo      = new Button("Meta de Consumo");
        Button btnHistoricoPrecos  = new Button("Histórico de Preços");
        Button btnFornecedor       = new Button("Fornecedores");

        btnProduto.setOnAction(e         -> new ProdutoUI().show());
        btnCategoria.setOnAction(e       -> new CategoriaUI().show());
        btnEstoque.setOnAction(e         -> new EstoqueUI().show());
        btnContaSuporte.setOnAction(e    -> new ContaSuporteUI().show());
        btnTicketSuporte.setOnAction(e   -> new TicketSuporteUI().show());
        btnReceita.setOnAction(e         -> new ReceitaUI().show());
        btnListaCompras.setOnAction(e    -> new ListaComprasUI().show());
        btnMetaConsumo.setOnAction(e     -> new MetaConsumoUI().show());
        btnHistoricoPrecos.setOnAction(e -> new HistoricoPrecosUI().show());
        btnFornecedor.setOnAction(e      -> new FornecedorUI().show());

        FlowPane root = new FlowPane(10, 10,
                btnProduto, btnCategoria, btnEstoque, btnContaSuporte, btnTicketSuporte,
                btnReceita, btnListaCompras, btnMetaConsumo, btnHistoricoPrecos, btnFornecedor
        );
        root.setStyle("-fx-padding: 16;");

        stage.setScene(new Scene(root, 500, 120));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}