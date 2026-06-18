package br.pucpr.projeto_crud_java.ui;

import br.pucpr.projeto_crud_java.model.ArquivoListaCompras;
import br.pucpr.projeto_crud_java.model.ListaCompras;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;

public class ListaComprasUI extends Stage {

    private TextField txtNome = new TextField();
    private DatePicker txtData = new DatePicker();
    private TextField txtStatus = new TextField();
    private TextField txtId = new TextField();

    private TableView<ListaCompras> tabela = new TableView<>();
    private ObservableList<ListaCompras> dados = FXCollections.observableArrayList();

    private Button btnAdicionar = new Button("Adicionar");
    private Button btnAtualizar = new Button("Atualizar");
    private Button btnDeletar = new Button("Deletar");
    private Button btnLimpar = new Button("Limpar");
    private Button btnCancelar = new Button("Cancelar");

    private Label lblMensagem = new Label();

    public ListaComprasUI() {
        setTitle("Lista de Compras");

        txtId.setPromptText("ID");
        txtId.setEditable(false);
        txtNome.setPromptText("Nome");
        txtData.setPromptText("Data de criação");
        txtStatus.setPromptText("Status");

        btnAtualizar.setDisable(true);
        btnDeletar.setDisable(true);
        btnCancelar.setDisable(true);

        // Colunas da tabela
        TableColumn<ListaCompras, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<ListaCompras, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colNome.setPrefWidth(150);

        TableColumn<ListaCompras, String> colData = new TableColumn<>("Data de criação");
        colData.setCellValueFactory(new PropertyValueFactory<>("data_criacao"));
        colData.setPrefWidth(150);

        TableColumn<ListaCompras, String> colStatus = new TableColumn<>("Status");
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
                btnCancelar.setDisable(false);
            }
        });

        // Ações
        btnAdicionar.setOnAction(e -> {
            try {
                if (txtNome.getText().trim().isEmpty() || txtData.getValue() == null || txtStatus.getText().trim().isEmpty()) {
                    lblMensagem.setText("Preencha todos os campos.");
                    return;
                }
                ListaCompras novaLista = new ListaCompras(ArquivoListaCompras.gerarId(), txtNome.getText().trim(), txtData.getValue(), txtStatus.getText().trim());
                ArquivoListaCompras.adicionar(novaLista);
                lblMensagem.setText("Lista de compras adicionada.");
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
                ListaCompras listaComprasAtualizada = new ListaCompras(Integer.parseInt(txtId.getText()), txtNome.getText().trim(), txtData.getValue(), txtStatus.getText().trim());
                ArquivoListaCompras.atualizar(listaComprasAtualizada);
                lblMensagem.setText("Lista de Compras atualizada.");
                limpar();
                carregarTabela();
            } catch (Exception ex) {
                lblMensagem.setText("Erro: " + ex.getMessage());
            }
        });

        btnDeletar.setOnAction(e -> {
            ListaCompras selecionado = tabela.getSelectionModel().getSelectedItem();
            if (selecionado == null) {
                lblMensagem.setText("Selecione uma lista de compras.");
                return;
            }
            ArquivoListaCompras.deletar(selecionado.getId());
            lblMensagem.setText("Lista deletada.");
            limpar();
            carregarTabela();
        });

        btnLimpar.setOnAction(e -> limpar());

        btnCancelar.setOnAction(e -> limpar());

        // Layout
        HBox campos = new HBox(8, txtId, txtNome, txtData, txtStatus);
        HBox botoes = new HBox(8, btnAdicionar, btnAtualizar, btnDeletar, btnLimpar, btnCancelar);
        VBox root = new VBox(8, campos, botoes, lblMensagem, tabela);
        root.setStyle("-fx-padding: 12;");

        setScene(new Scene(root, 700, 450));
        carregarTabela();
    }

    private void carregarTabela() {
        ArrayList<ListaCompras> lista = ArquivoListaCompras.lerLista();
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
        btnCancelar.setDisable(true);
    }
}