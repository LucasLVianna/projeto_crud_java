package br.pucpr.projeto_crud_java.ui;

import br.pucpr.projeto_crud_java.model.ArquivoHistoricoPreco;
import br.pucpr.projeto_crud_java.model.HistoricoPreco;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;

public class HistoricoPrecosUI extends Stage {

    private TextField txtId = new TextField();
    private TextField txtIdProduto = new TextField();
    private TextField txtPreco = new TextField();
    private DatePicker txtData = new DatePicker();

    private TableView<HistoricoPreco> tabela = new TableView<>();
    private ObservableList<HistoricoPreco> dados = FXCollections.observableArrayList();

    private Button btnAdicionar = new Button("Adicionar");
    private Button btnAtualizar = new Button("Atualizar");
    private Button btnDeletar = new Button("Deletar");
    private Button btnLimpar = new Button("Limpar");
    private Button btnCancelar = new Button("Cancelar");

    private Label lblMensagem = new Label();

    public HistoricoPrecosUI() {
        setTitle("Histórico de Preços");

        txtId.setPromptText("ID");
        txtId.setEditable(false);
        txtIdProduto.setPromptText("ID do Produto");
        txtPreco.setPromptText("Preço");
        txtData.setPromptText("Data");
        txtData.setEditable(false);

        btnAtualizar.setDisable(true);
        btnDeletar.setDisable(true);
        btnCancelar.setDisable(true);

        TableColumn<HistoricoPreco, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<HistoricoPreco, Integer> colIdProduto = new TableColumn<>("ID Produto");
        colIdProduto.setCellValueFactory(new PropertyValueFactory<>("idProduto"));
        colIdProduto.setPrefWidth(100);

        TableColumn<HistoricoPreco, Double> colPreco = new TableColumn<>("Preço");
        colPreco.setCellValueFactory(new PropertyValueFactory<>("preco"));
        colPreco.setPrefWidth(120);

        TableColumn<HistoricoPreco, String> colData = new TableColumn<>("Data");
        colData.setCellValueFactory(new PropertyValueFactory<>("data"));
        colData.setPrefWidth(120);

        tabela.getColumns().addAll(colId, colIdProduto, colPreco, colData);
        tabela.setItems(dados);

        tabela.getSelectionModel().selectedItemProperty().addListener((obs, ant, selecionado) -> {
            if (selecionado != null) {
                txtId.setText(String.valueOf(selecionado.getId()));
                txtIdProduto.setText(String.valueOf(selecionado.getIdProduto()));
                txtPreco.setText(String.valueOf(selecionado.getPreco()));
                txtData.setValue(selecionado.getData());
                btnAtualizar.setDisable(false);
                btnDeletar.setDisable(false);
                btnAdicionar.setDisable(true);
                btnCancelar.setDisable(false);
            }
        });

        btnAdicionar.setOnAction(e -> {
            try {
                if (txtIdProduto.getText().trim().isEmpty() || txtPreco.getText().trim().isEmpty() || txtData.getValue() == null) {
                    lblMensagem.setText("Preencha todos os campos.");
                    return;
                }
                HistoricoPreco h = new HistoricoPreco(
                        ArquivoHistoricoPreco.gerarId(),
                        Integer.parseInt(txtIdProduto.getText().trim()),
                        Double.parseDouble(txtPreco.getText().trim()),
                        txtData.getValue()
                );
                ArquivoHistoricoPreco.adicionar(h);
                lblMensagem.setText("Registro adicionado.");
                limpar();
                carregarTabela();
            } catch (NumberFormatException ex) {
                lblMensagem.setText("ID do Produto e Preço devem ser numéricos.");
            } catch (Exception ex) {
                lblMensagem.setText("Erro: " + ex.getMessage());
            }
        });

        btnAtualizar.setOnAction(e -> {
            try {
                if (txtIdProduto.getText().trim().isEmpty() || txtPreco.getText().trim().isEmpty() || txtData.getValue() == null) {
                    lblMensagem.setText("Preencha todos os campos.");
                    return;
                }
                HistoricoPreco h = new HistoricoPreco(
                        Integer.parseInt(txtId.getText()),
                        Integer.parseInt(txtIdProduto.getText().trim()),
                        Double.parseDouble(txtPreco.getText().trim()),
                        txtData.getValue()
                );
                ArquivoHistoricoPreco.atualizar(h);
                lblMensagem.setText("Registro atualizado.");
                limpar();
                carregarTabela();
            } catch (NumberFormatException ex) {
                lblMensagem.setText("ID do Produto e Preço devem ser numéricos.");
            } catch (Exception ex) {
                lblMensagem.setText("Erro: " + ex.getMessage());
            }
        });

        btnDeletar.setOnAction(e -> {
            HistoricoPreco selecionado = tabela.getSelectionModel().getSelectedItem();
            if (selecionado == null) {
                lblMensagem.setText("Selecione um registro.");
                return;
            }
            ArquivoHistoricoPreco.deletar(selecionado.getId());
            lblMensagem.setText("Registro deletado.");
            limpar();
            carregarTabela();
        });

        btnLimpar.setOnAction(e -> limpar());

        btnCancelar.setOnAction(e -> limpar());

        HBox campos = new HBox(8, txtId, txtIdProduto, txtPreco, txtData);
        HBox botoes = new HBox(8, btnAdicionar, btnAtualizar, btnDeletar, btnLimpar, btnCancelar);
        VBox root = new VBox(8, campos, botoes, lblMensagem, tabela);
        root.setStyle("-fx-padding: 12;");

        setScene(new Scene(root, 700, 450));
        carregarTabela();
    }

    private void carregarTabela() {
        ArrayList<HistoricoPreco> lista = ArquivoHistoricoPreco.lerLista();
        dados.setAll(lista);
    }

    private void limpar() {
        txtId.clear();
        txtIdProduto.clear();
        txtPreco.clear();
        txtData.setValue(null);
        tabela.getSelectionModel().clearSelection();
        lblMensagem.setText("");
        btnAdicionar.setDisable(false);
        btnAtualizar.setDisable(true);
        btnDeletar.setDisable(true);
        btnCancelar.setDisable(true);
    }
}
