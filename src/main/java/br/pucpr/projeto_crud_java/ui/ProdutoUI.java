package br.pucpr.projeto_crud_java.ui;

import br.pucpr.projeto_crud_java.model.ArquivoProduto;
import br.pucpr.projeto_crud_java.model.Produto;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;

public class ProdutoUI extends Stage {

    private TextField txtNome = new TextField();
    private TextField txtCategoria = new TextField();
    private TextField txtUnidade = new TextField();
    private TextField txtId = new TextField();

    private TableView<Produto> tabela = new TableView<>();
    private ObservableList<Produto> dados = FXCollections.observableArrayList();

    private Button btnAdicionar = new Button("Adicionar");
    private Button btnAtualizar = new Button("Atualizar");
    private Button btnDeletar = new Button("Deletar");
    private Button btnLimpar = new Button("Limpar");
    private Button btnCancelar = new Button("Cancelar");

    private Label lblMensagem = new Label();

    public ProdutoUI() {
        setTitle("Produtos");

        txtId.setPromptText("ID");
        txtId.setEditable(false);
        txtNome.setPromptText("Nome");
        txtCategoria.setPromptText("Categoria");
        txtUnidade.setPromptText("Unidade de medida");

        btnAtualizar.setDisable(true);
        btnDeletar.setDisable(true);
        btnCancelar.setDisable(true);

        // Colunas da tabela
        TableColumn<Produto, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Produto, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colNome.setPrefWidth(150);

        TableColumn<Produto, String> colCategoria = new TableColumn<>("Categoria");
        colCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        colCategoria.setPrefWidth(150);

        TableColumn<Produto, String> colUnidade = new TableColumn<>("Unidade");
        colUnidade.setCellValueFactory(new PropertyValueFactory<>("unidade_medida"));
        colUnidade.setPrefWidth(100);

        tabela.getColumns().addAll(colId, colNome, colCategoria, colUnidade);
        tabela.setItems(dados);

        // Clique na tabela preenche o formulário
        tabela.getSelectionModel().selectedItemProperty().addListener((obs, ant, selecionado) -> {
            if (selecionado != null) {
                txtId.setText(String.valueOf(selecionado.getId()));
                txtNome.setText(selecionado.getNome());
                txtCategoria.setText(selecionado.getCategoria());
                txtUnidade.setText(selecionado.getUnidade_medida());
                btnAtualizar.setDisable(false);
                btnDeletar.setDisable(false);
                btnAdicionar.setDisable(true);
                btnCancelar.setDisable(false);
            }
        });

        // Ações
        btnAdicionar.setOnAction(e -> {
            try {
                if (txtNome.getText().trim().isEmpty() || txtCategoria.getText().trim().isEmpty() || txtUnidade.getText().trim().isEmpty()) {
                    lblMensagem.setText("Preencha todos os campos.");
                    return;
                }
                Produto p = new Produto(ArquivoProduto.gerarId(), txtNome.getText().trim(), txtCategoria.getText().trim(), txtUnidade.getText().trim());
                ArquivoProduto.adicionar(p);
                lblMensagem.setText("Produto adicionado.");
                limpar();
                carregarTabela();
            } catch (Exception ex) {
                lblMensagem.setText("Erro: " + ex.getMessage());
            }
        });

        btnAtualizar.setOnAction(e -> {
            try {
                if (txtNome.getText().trim().isEmpty() || txtCategoria.getText().trim().isEmpty() || txtUnidade.getText().trim().isEmpty()) {
                    lblMensagem.setText("Preencha todos os campos.");
                    return;
                }
                Produto p = new Produto(Integer.parseInt(txtId.getText()), txtNome.getText().trim(), txtCategoria.getText().trim(), txtUnidade.getText().trim());
                ArquivoProduto.atualizar(p);
                lblMensagem.setText("Produto atualizado.");
                limpar();
                carregarTabela();
            } catch (Exception ex) {
                lblMensagem.setText("Erro: " + ex.getMessage());
            }
        });

        btnDeletar.setOnAction(e -> {
            Produto selecionado = tabela.getSelectionModel().getSelectedItem();
            if (selecionado == null) {
                lblMensagem.setText("Selecione um produto.");
                return;
            }
            ArquivoProduto.deletar(selecionado.getId());
            lblMensagem.setText("Produto deletado.");
            limpar();
            carregarTabela();
        });

        btnLimpar.setOnAction(e -> limpar());

        btnCancelar.setOnAction(e -> limpar());

        // Layout
        HBox campos = new HBox(8, txtId, txtNome, txtCategoria, txtUnidade);
        HBox botoes = new HBox(8, btnAdicionar, btnAtualizar, btnDeletar, btnLimpar, btnCancelar);
        VBox root = new VBox(8, campos, botoes, lblMensagem, tabela);
        root.setStyle("-fx-padding: 12;");

        setScene(new Scene(root, 700, 450));
        carregarTabela();
    }

    private void carregarTabela() {
        ArrayList<Produto> lista = ArquivoProduto.lerLista();
        dados.setAll(lista);
    }

    private void limpar() {
        txtId.clear();
        txtNome.clear();
        txtCategoria.clear();
        txtUnidade.clear();
        tabela.getSelectionModel().clearSelection();
        lblMensagem.setText("");
        btnAdicionar.setDisable(false);
        btnAtualizar.setDisable(true);
        btnDeletar.setDisable(true);
        btnCancelar.setDisable(true);
    }
}