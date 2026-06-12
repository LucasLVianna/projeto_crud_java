package br.pucpr.projeto_crud_java.ui;

import br.pucpr.projeto_crud_java.model.ArquivoProduto;
import br.pucpr.projeto_crud_java.model.Produto;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.util.ArrayList;

public class ProdutoUI extends Stage {

    // Campos do formulário
    private TextField txtId;
    private TextField txtNome;
    private TextField txtCategoria;
    private TextField txtUnidadeMedida;

    // Tabela
    private TableView<Produto> tabela;
    private ObservableList<Produto> dadosTabela;

    // Botões
    private Button btnAdicionar;
    private Button btnAtualizar;
    private Button btnDeletar;
    private Button btnLimpar;

    // Feedback
    private Label lblMensagem;

    public ProdutoUI() {
        setTitle("MyKeeper — Gerenciar Produtos");
        setMinWidth(820);
        setMinHeight(560);

        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #f5f5f5;");

        root.setTop(buildHeader());
        root.setLeft(buildFormulario());
        root.setCenter(buildTabela());

        Scene scene = new Scene(root, 820, 560);
        setScene(scene);

        carregarTabela();
    }

    // -------------------------------------------------------------------------
    // HEADER
    // -------------------------------------------------------------------------
    private VBox buildHeader() {
        VBox header = new VBox();
        header.setStyle("-fx-background-color: #2e7d32;");
        header.setPadding(new Insets(14, 20, 14, 20));

        Label titulo = new Label("Produtos");
        titulo.setFont(Font.font("System", FontWeight.BOLD, 20));
        titulo.setTextFill(Color.WHITE);

        Label subtitulo = new Label("Inserção, consulta, atualização e exclusão");
        subtitulo.setFont(Font.font("System", 12));
        subtitulo.setTextFill(Color.web("#c8e6c9"));

        header.getChildren().addAll(titulo, subtitulo);
        return header;
    }

    // -------------------------------------------------------------------------
    // FORMULÁRIO (lado esquerdo)
    // -------------------------------------------------------------------------
    private VBox buildFormulario() {
        VBox form = new VBox(8);
        form.setPadding(new Insets(20));
        form.setPrefWidth(270);
        form.setStyle("-fx-background-color: #ffffff; -fx-border-color: #e0e0e0; -fx-border-width: 0 1 0 0;");

        Label lblTitulo = new Label("Dados do Produto");
        lblTitulo.setFont(Font.font("System", FontWeight.BOLD, 14));
        lblTitulo.setTextFill(Color.web("#2e7d32"));

        // ID — somente leitura, gerado automaticamente
        txtId = new TextField();
        txtId.setPromptText("ID (automático)");
        txtId.setEditable(false);
        txtId.setStyle("-fx-background-color: #eeeeee;");

        txtNome = new TextField();
        txtNome.setPromptText("Ex: Arroz integral");

        txtCategoria = new TextField();
        txtCategoria.setPromptText("Ex: Grãos");

        txtUnidadeMedida = new TextField();
        txtUnidadeMedida.setPromptText("Ex: kg, L, un");

        // Feedback
        lblMensagem = new Label("");
        lblMensagem.setWrapText(true);
        lblMensagem.setMaxWidth(230);
        lblMensagem.setFont(Font.font("System", 12));

        // Botões
        btnAdicionar = new Button("Adicionar produto");
        btnAdicionar.setPrefWidth(230);
        btnAdicionar.setStyle("-fx-background-color: #2e7d32; -fx-text-fill: white; -fx-font-weight: bold;");

        btnAtualizar = new Button("Salvar alterações");
        btnAtualizar.setPrefWidth(230);
        btnAtualizar.setStyle("-fx-background-color: #1565c0; -fx-text-fill: white; -fx-font-weight: bold;");
        btnAtualizar.setDisable(true);

        btnDeletar = new Button("Excluir produto");
        btnDeletar.setPrefWidth(230);
        btnDeletar.setStyle("-fx-background-color: #c62828; -fx-text-fill: white; -fx-font-weight: bold;");
        btnDeletar.setDisable(true);

        btnLimpar = new Button("Limpar campos");
        btnLimpar.setPrefWidth(230);
        btnLimpar.setStyle("-fx-background-color: #757575; -fx-text-fill: white;");

        form.getChildren().addAll(
                lblTitulo,
                new Label("ID:"), txtId,
                new Label("Nome:"), txtNome,
                new Label("Categoria:"), txtCategoria,
                new Label("Unidade de medida:"), txtUnidadeMedida,
                new Separator(),
                lblMensagem,
                btnAdicionar,
                btnAtualizar,
                btnDeletar,
                btnLimpar
        );

        btnAdicionar.setOnAction(e -> acaoAdicionar());
        btnAtualizar.setOnAction(e -> acaoAtualizar());
        btnDeletar.setOnAction(e -> acaoDeletar());
        btnLimpar.setOnAction(e -> limparCampos());

        return form;
    }

    // -------------------------------------------------------------------------
    // TABELA (centro)
    // -------------------------------------------------------------------------
    private VBox buildTabela() {
        VBox container = new VBox(10);
        container.setPadding(new Insets(20));

        Label lblTitulo = new Label("Produtos cadastrados");
        lblTitulo.setFont(Font.font("System", FontWeight.BOLD, 14));

        tabela = new TableView<>();
        tabela.setPlaceholder(new Label("Nenhum produto cadastrado."));
        VBox.setVgrow(tabela, Priority.ALWAYS);

        // Os nomes em PropertyValueFactory devem bater com os getters de Produto
        TableColumn<Produto, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colId.setPrefWidth(50);

        TableColumn<Produto, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colNome.setPrefWidth(200);

        TableColumn<Produto, String> colCategoria = new TableColumn<>("Categoria");
        colCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        colCategoria.setPrefWidth(150);

        // ATENÇÃO: o getter é getUnidade_medida(), mas PropertyValueFactory
        // espera o nome do atributo sem "get" e com a primeira letra minúscula.
        // Como o atributo tem underscore, passamos "unidade_medida" aqui.
        TableColumn<Produto, String> colUnidade = new TableColumn<>("Unidade");
        colUnidade.setCellValueFactory(new PropertyValueFactory<>("unidade_medida"));
        colUnidade.setPrefWidth(100);

        tabela.getColumns().addAll(colId, colNome, colCategoria, colUnidade);

        dadosTabela = FXCollections.observableArrayList();
        tabela.setItems(dadosTabela);

        // Clique numa linha → preenche o formulário e ativa Atualizar/Deletar
        tabela.getSelectionModel().selectedItemProperty().addListener(
                (obs, anterior, selecionado) -> {
                    if (selecionado != null) {
                        preencherFormulario(selecionado);
                        btnAtualizar.setDisable(false);
                        btnDeletar.setDisable(false);
                        btnAdicionar.setDisable(true);
                    }
                }
        );

        container.getChildren().addAll(lblTitulo, tabela);
        return container;
    }

    // -------------------------------------------------------------------------
    // AÇÕES DOS BOTÕES
    // -------------------------------------------------------------------------
    private void acaoAdicionar() {
        try {
            String nome = txtNome.getText().trim();
            String categoria = txtCategoria.getText().trim();
            String unidade = txtUnidadeMedida.getText().trim();

            if (nome.isEmpty() || categoria.isEmpty() || unidade.isEmpty()) {
                mostrarMensagem("Preencha todos os campos.", false);
                return;
            }

            int novoId = ArquivoProduto.gerarId();
            Produto novo = new Produto(novoId, nome, categoria, unidade);
            ArquivoProduto.adicionarProduto(novo);

            mostrarMensagem("Produto adicionado com sucesso!", true);
            limparCampos();
            carregarTabela();

        } catch (Exception e) {
            mostrarMensagem("Erro inesperado: " + e.getMessage(), false);
        }
    }

    private void acaoAtualizar() {
        try {
            String idStr = txtId.getText().trim();
            if (idStr.isEmpty()) {
                mostrarMensagem("Selecione um produto na tabela para editar.", false);
                return;
            }

            String nome = txtNome.getText().trim();
            String categoria = txtCategoria.getText().trim();
            String unidade = txtUnidadeMedida.getText().trim();

            if (nome.isEmpty() || categoria.isEmpty() || unidade.isEmpty()) {
                mostrarMensagem("Preencha todos os campos.", false);
                return;
            }

            int id = Integer.parseInt(idStr);
            Produto atualizado = new Produto(id, nome, categoria, unidade);
            ArquivoProduto.atualizarProduto(atualizado);

            mostrarMensagem("Produto atualizado com sucesso!", true);
            limparCampos();
            carregarTabela();

        } catch (NumberFormatException e) {
            mostrarMensagem("ID inválido.", false);
        } catch (Exception e) {
            mostrarMensagem("Erro inesperado: " + e.getMessage(), false);
        }
    }

    private void acaoDeletar() {
        Produto selecionado = tabela.getSelectionModel().getSelectedItem();
        if (selecionado == null) {
            mostrarMensagem("Selecione um produto na tabela.", false);
            return;
        }

        Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacao.setTitle("Confirmar exclusão");
        confirmacao.setHeaderText("Excluir produto");
        confirmacao.setContentText("Deseja realmente excluir \"" + selecionado.getNome() + "\"?");

        confirmacao.showAndWait().ifPresent(resposta -> {
            if (resposta == ButtonType.OK) {
                ArquivoProduto.deletarProduto(selecionado.getId());
                mostrarMensagem("Produto excluído.", true);
                limparCampos();
                carregarTabela();
            }
        });
    }

    // -------------------------------------------------------------------------
    // UTILITÁRIOS
    // -------------------------------------------------------------------------
    private void carregarTabela() {
        ArrayList<Produto> lista = ArquivoProduto.lerLista();
        dadosTabela.setAll(lista);
    }

    private void preencherFormulario(Produto p) {
        txtId.setText(String.valueOf(p.getId()));
        txtNome.setText(p.getNome());
        txtCategoria.setText(p.getCategoria());
        txtUnidadeMedida.setText(p.getUnidade_medida());
    }

    private void limparCampos() {
        txtId.clear();
        txtNome.clear();
        txtCategoria.clear();
        txtUnidadeMedida.clear();
        tabela.getSelectionModel().clearSelection();
        lblMensagem.setText("");
        btnAdicionar.setDisable(false);
        btnAtualizar.setDisable(true);
        btnDeletar.setDisable(true);
    }

    private void mostrarMensagem(String mensagem, boolean sucesso) {
        lblMensagem.setText(mensagem);
        lblMensagem.setTextFill(sucesso ? Color.web("#2e7d32") : Color.web("#c62828"));
    }
}