package br.pucpr.projeto_crud_java;

import br.pucpr.projeto_crud_java.ui.*;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stagePrincipal) {
        stagePrincipal.setTitle("MyKeeper");
        stagePrincipal.setMinWidth(720);
        stagePrincipal.setMinHeight(540);

        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #f5f5f5;");
        root.setTop(buildHeader());
        root.setCenter(buildGrid());

        Scene scene = new Scene(root, 720, 540);
        stagePrincipal.setScene(scene);
        stagePrincipal.show();
    }

    // -------------------------------------------------------------------------
    // HEADER
    // -------------------------------------------------------------------------
    private VBox buildHeader() {
        VBox header = new VBox();
        header.setStyle("-fx-background-color: #2e7d32;");
        header.setPadding(new Insets(18, 24, 18, 24));

        Label titulo = new Label("MyKeeper");
        titulo.setFont(Font.font("System", FontWeight.BOLD, 24));
        titulo.setTextFill(Color.WHITE);

        Label subtitulo = new Label("Sistema de gerenciamento de estoque alimentar");
        subtitulo.setFont(Font.font("System", 13));
        subtitulo.setTextFill(Color.web("#c8e6c9"));

        header.getChildren().addAll(titulo, subtitulo);
        return header;
    }

    // -------------------------------------------------------------------------
    // GRID DE BOTÕES
    // -------------------------------------------------------------------------
    private GridPane buildGrid() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(16);
        grid.setVgap(16);
        grid.setPadding(new Insets(32));

        String[][] telas = {
                {"Produtos",              "Cadastro de produtos do estoque"},
                {"Categorias",            "Organização por categorias"},
                {"Estoque",               "Controle de quantidade em estoque"},
                {"Contas de Suporte",     "Usuários de suporte do sistema"},
                {"Tickets de Suporte",    "Chamados e solicitações de suporte"},
                {"Receitas",              "Receitas e modos de preparo"},
                {"Lista de Compras",      "Itens a comprar"},
                {"Meta de Consumo",       "Metas nutricionais e de consumo"},
                {"Histórico de Preços",   "Registro de preços de compras"},
                {"Fornecedores",          "Cadastro de fornecedores"}
        };

        for (int i = 0; i < telas.length; i++) {
            VBox card = buildCard(telas[i][0], telas[i][1]);
            grid.add(card, i % 5, i / 5);
        }

        return grid;
    }

    // -------------------------------------------------------------------------
    // CARD DE CADA TELA
    // -------------------------------------------------------------------------
    private VBox buildCard(String titulo, String descricao) {
        VBox card = new VBox(8);
        card.setAlignment(Pos.CENTER);
        card.setPrefSize(120, 100);
        card.setPadding(new Insets(14));
        card.setStyle(
                "-fx-background-color: #ffffff;" +
                        "-fx-border-color: #e0e0e0;" +
                        "-fx-border-radius: 6;" +
                        "-fx-background-radius: 6;" +
                        "-fx-cursor: hand;"
        );

        Label lblTitulo = new Label(titulo);
        lblTitulo.setFont(Font.font("System", FontWeight.BOLD, 12));
        lblTitulo.setTextFill(Color.web("#2e7d32"));
        lblTitulo.setWrapText(true);
        lblTitulo.setAlignment(Pos.CENTER);

        Label lblDesc = new Label(descricao);
        lblDesc.setFont(Font.font("System", 10));
        lblDesc.setTextFill(Color.web("#757575"));
        lblDesc.setWrapText(true);
        lblDesc.setAlignment(Pos.CENTER);

        card.getChildren().addAll(lblTitulo, lblDesc);

        // Hover
        card.setOnMouseEntered(e -> card.setStyle(
                "-fx-background-color: #e8f5e9;" +
                        "-fx-border-color: #2e7d32;" +
                        "-fx-border-radius: 6;" +
                        "-fx-background-radius: 6;" +
                        "-fx-cursor: hand;"
        ));
        card.setOnMouseExited(e -> card.setStyle(
                "-fx-background-color: #ffffff;" +
                        "-fx-border-color: #e0e0e0;" +
                        "-fx-border-radius: 6;" +
                        "-fx-background-radius: 6;" +
                        "-fx-cursor: hand;"
        ));

        // Clique abre a tela correspondente
        card.setOnMouseClicked(e -> abrirTela(titulo));

        return card;
    }

    // -------------------------------------------------------------------------
    // ROTEADOR DE TELAS
    // -------------------------------------------------------------------------
    private void abrirTela(String titulo) {
        Stage tela;
        switch (titulo) {
            case "Produtos":           tela = new ProdutoUI();          break;
            case "Categorias":         tela = new CategoriaUI();        break;
            case "Estoque":            tela = new EstoqueUI();          break;
            case "Contas de Suporte":  tela = new ContaSuporteUI();     break;
            case "Tickets de Suporte": tela = new TicketSuporteUI();    break;
            case "Receitas":           tela = new ReceitaUI();          break;
            case "Lista de Compras":   tela = new ListaComprasUI();     break;
            case "Meta de Consumo":    tela = new MetaConsumoUI();      break;
            case "Histórico de Preços":tela = new HistoricoPrecosUI();  break;
            case "Fornecedores":       tela = new FornecedorUI();       break;
            default: return;
        }
        tela.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
