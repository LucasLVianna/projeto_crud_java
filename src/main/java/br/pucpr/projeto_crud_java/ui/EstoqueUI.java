package br.pucpr.projeto_crud_java.ui;

import br.pucpr.projeto_crud_java.model.ArquivoEstoque;
import br.pucpr.projeto_crud_java.model.Estoque;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;

public class EstoqueUI extends Stage {

    private TextField txtNome = new TextField();
    private DatePicker txtData = new DatePicker();
    private TextField txtStatus = new TextField();
    private TextField txtId = new TextField();

    private TableView<Estoque> tabela = new TableView<>();
    private ObservableList<Estoque> dados = FXCollections.observableArrayList();

    private Button btnAdicionar = new Button("Adicionar");
    private Button btnAtualizar = new Button("Atualizar");
    private Button btnDeletar = new Button("Deletar");
    private Button btnLimpar = new Button("Limpar");

    private Label lblMensagem = new Label();

    public EstoqueUI() {
        setTitle("Estoque");

        txtId.setPromptText("ID");
        txtId.setEditable(false);
        txtNome.setPromptText("Nome");
        txtData.setPromptText("Data de criação");
        txtStatus.setPromptText("Status");

        btnAtualizar.setDisable(true);
        btnDeletar.setDisable(true);

        // Colunas da tabela
        TableColumn<Estoque, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Estoque, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colNome.setPrefWidth(150);

        TableColumn<Estoque, String> colData = new TableColumn<>("Data de criação");
        colData.setCellValueFactory(new PropertyValueFactory<>("data_criacao"));
        colData.setPrefWidth(150);

        TableColumn<Estoque, String> colStatus = new TableColumn<>("Status");
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colStatus.setPrefWidth(100);

        tabela.getColumns().addAll(colId, colNome, colData, colStatus);
        tabela.setItems(dados);

        // Clique na tabela preenche o formulário
        tabela.getSelectionModel().selectedItemProperty().addListener((obs, ant, selecionado) -> {
            if (selecionado != null) {
                txtId.setText(String.valueOf(selecionado.getId()));
                txtNome.setText(selecionado.getNome());
                txtData.setValue(selecionado.getData_criacao());
                txtStatus.setText(selecionado.getStatus());
                btnAtualizar.setDisable(false);
                btnDeletar.setDisable(false);
                btnAdicionar.setDisable(true);
            }
        });

        // Ações
        btnAdicionar.setOnAction(e -> {
            try {
                if (txtNome.getText().trim().isEmpty() || txtData.getValue() == null || txtStatus.getText().trim().isEmpty()) {
                    lblMensagem.setText("Preencha todos os campos.");
                    return;
                }
                Estoque novoEstoque = new Estoque(ArquivoEstoque.gerarId(), txtNome.getText().trim(), txtData.getValue(), txtStatus.getText().trim());
                ArquivoEstoque.adicionar(novoEstoque);
                lblMensagem.setText("Estoque adicionado.");
                limpar();
                carregarTabela();
            } catch (Exception ex) {
                lblMensagem.setText("Erro: " + ex.getMessage());
            }
        });

        btnAtualizar.setOnAction(e -> {
            try {
                if (txtNome.getText().trim().isEmpty() || txtData.getValue() == null || txtStatus.getText().trim().isEmpty()) {
                    lblMensagem.setText("Preencha todos os campos.");
                    return;
                }
                Estoque estoqueAtualizado = new Estoque(Integer.parseInt(txtId.getText()), txtNome.getText().trim(), txtData.getValue(), txtStatus.getText().trim());
                ArquivoEstoque.atualizar(estoqueAtualizado);
                lblMensagem.setText("Estoque atualizado.");
                limpar();
                carregarTabela();
            } catch (Exception ex) {
                lblMensagem.setText("Erro: " + ex.getMessage());
            }
        });

        btnDeletar.setOnAction(e -> {
            Estoque selecionado = tabela.getSelectionModel().getSelectedItem();
            if (selecionado == null) {
                lblMensagem.setText("Selecione um estoque.");
                return;
            }
            ArquivoEstoque.deletar(selecionado.getId());
            lblMensagem.setText("Estoque deletado.");
            limpar();
            carregarTabela();
        });

        btnLimpar.setOnAction(e -> limpar());

        // Layout
        HBox campos = new HBox(8, txtId, txtNome, txtData, txtStatus);
        HBox botoes = new HBox(8, btnAdicionar, btnAtualizar, btnDeletar, btnLimpar);
        VBox root = new VBox(8, campos, botoes, lblMensagem, tabela);
        root.setStyle("-fx-padding: 12;");

        setScene(new Scene(root, 700, 450));
        carregarTabela();
    }

    private void carregarTabela() {
        ArrayList<Estoque> lista = ArquivoEstoque.lerLista();
        dados.setAll(lista);
    }

    private void limpar() {
        txtId.clear();
        txtNome.clear();
        txtData.setValue(null);
        txtStatus.clear();
        tabela.getSelectionModel().clearSelection();
        lblMensagem.setText("");
        btnAdicionar.setDisable(false);
        btnAtualizar.setDisable(true);
        btnDeletar.setDisable(true);
    }
}