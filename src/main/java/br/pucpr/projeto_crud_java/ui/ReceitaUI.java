package br.pucpr.projeto_crud_java.ui;

import br.pucpr.projeto_crud_java.model.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;

public class ReceitaUI extends Stage {

    private TextField txtId = new TextField();
    private TextField txtNome = new TextField();
    private TextField txtIngredientes = new TextField();
    private TextField txtModoPreparo = new TextField();
    private TextField txtTempoPreparo = new TextField();

    private TableView<Receita> tabela = new TableView<>();
    private ObservableList<Receita> dados = FXCollections.observableArrayList();

    private Button btnAdicionar = new Button("Adicionar");
    private Button btnAtualizar = new Button("Atualizar");
    private Button btnDeletar = new Button("Deletar");
    private Button btnLimpar = new Button("Limpar");
    private Button btnCancelar = new Button("Cancelar");
    private Button btnVoltar = new Button("Voltar");

    private Label lblMensagem = new Label();

    private boolean validarTempoPreparo(String tempo) {return tempo.matches("\\d+");}

    public ReceitaUI() {
        setTitle("Receita");

        txtId.setPromptText("ID");
        txtId.setEditable(false); // id nao pode ser alterado
        txtNome.setPromptText("Nome");
        txtIngredientes.setPromptText("Ingredientes");
        txtModoPreparo.setPromptText("Modo de Preparo");
        txtTempoPreparo.setPromptText("Tempo de Preparo (min)");

        btnAtualizar.setDisable(true);
        btnDeletar.setDisable(true);

        TableColumn<Receita, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Receita, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colNome.setPrefWidth(150);

        TableColumn<Receita, String> colIngredientes = new TableColumn<>("Ingredientes");
        colIngredientes.setCellValueFactory(new PropertyValueFactory<>("ingredientes"));
        colIngredientes.setPrefWidth(200);

        TableColumn<Receita, String> colModoPreparo = new TableColumn<>("Modo de Preparo");
        colModoPreparo.setCellValueFactory(new PropertyValueFactory<>("modoPreparo"));
        colModoPreparo.setPrefWidth(250);

        TableColumn<Receita, Integer> colTempoPreparo = new TableColumn<>("Tempo (min)");
        colTempoPreparo.setCellValueFactory(new PropertyValueFactory<>("tempoPreparo"));
        colTempoPreparo.setPrefWidth(100);

        tabela.getColumns().addAll(colId, colNome, colIngredientes, colModoPreparo, colTempoPreparo);
        tabela.setItems(dados);

        // preenche os campos quando seleciona uma receita
        tabela.getSelectionModel().selectedItemProperty().addListener((obs, ant, selecionado) -> {
            if (selecionado != null) {
                txtId.setText(String.valueOf(selecionado.getId()));
                txtNome.setText(selecionado.getNome());
                txtIngredientes.setText(selecionado.getIngredientes());
                txtModoPreparo.setText(selecionado.getModoPreparo());
                txtTempoPreparo.setText(String.valueOf(selecionado.getTempoPreparo()));
                btnAtualizar.setDisable(false);
                btnDeletar.setDisable(false);
                btnAdicionar.setDisable(true);
                btnCancelar.setDisable(false);
            }
        });

        btnAdicionar.setOnAction(e -> {
            try {

                // verifica se tudo foi preenchido
                if (txtNome.getText().trim().isEmpty() || txtIngredientes.getText().trim().isEmpty()
                        || txtModoPreparo.getText().trim().isEmpty() || txtTempoPreparo.getText().trim().isEmpty()) {
                    lblMensagem.setText("Preencha todos os campos.");
                    return;
                }

                if (!validarTempoPreparo(txtTempoPreparo.getText().trim())) {
                    lblMensagem.setText("Tempo de preparo invalido. Use apenas numeros.");
                    return;
                }

                // cria a receita e salva
                Receita novaReceita = new Receita(
                        ArquivoReceita.gerarId(),
                        txtNome.getText().trim(),
                        txtIngredientes.getText().trim(),
                        txtModoPreparo.getText().trim(),
                        Integer.parseInt(txtTempoPreparo.getText().trim())
                );
                ArquivoReceita.adicionar(novaReceita);
                lblMensagem.setText("Receita adicionada.");
                carregarTabela();
                limpar();
            } catch (Exception ex) {
                lblMensagem.setText("Erro: " + ex.getMessage());
            }
        });

        btnAtualizar.setOnAction(e -> {
            try {

                if (txtNome.getText().trim().isEmpty() || txtIngredientes.getText().trim().isEmpty()
                        || txtModoPreparo.getText().trim().isEmpty() || txtTempoPreparo.getText().trim().isEmpty()) {
                    lblMensagem.setText("Preencha todos os campos.");
                    return;
                }

                if (!validarTempoPreparo(txtTempoPreparo.getText().trim())) {
                    lblMensagem.setText("Tempo de preparo invalido. Use apenas numeros.");
                    return;
                }

                // atualiza usando o id da receita selecionada
                Receita receitaAtualizada = new Receita(
                        Integer.parseInt(txtId.getText()),
                        txtNome.getText().trim(),
                        txtIngredientes.getText().trim(),
                        txtModoPreparo.getText().trim(),
                        Integer.parseInt(txtTempoPreparo.getText().trim())
                );

                ArquivoReceita.atualizar(receitaAtualizada);
                lblMensagem.setText("Receita atualizada.");
                carregarTabela();
                limpar();

            } catch (Exception ex) {
                lblMensagem.setText("Erro: " + ex.getMessage());
            }
        });

        btnDeletar.setOnAction(e -> {
            Receita selecionado = tabela.getSelectionModel().getSelectedItem();

            if (selecionado == null) {
                lblMensagem.setText("Selecione uma receita.");
                return;
            }
            ArquivoReceita.deletar(selecionado.getId());
            lblMensagem.setText("Receita deletada.");
            carregarTabela();
            limpar();
        });

        btnLimpar.setOnAction(e -> limpar());
        btnCancelar.setOnAction(e -> limpar());

        btnVoltar.setOnAction(e -> {
            Stage stage = (Stage) btnVoltar.getScene().getWindow();
            stage.close();
        });

        HBox campos = new HBox(8, txtId, txtNome, txtIngredientes, txtModoPreparo, txtTempoPreparo);
        HBox botoes = new HBox(8, btnAdicionar, btnAtualizar, btnDeletar, btnLimpar, btnCancelar, btnVoltar);
        VBox root = new VBox(8, campos, botoes, lblMensagem, tabela);
        root.setStyle("-fx-padding: 12;");

        setScene(new Scene(root, 800, 450));
        carregarTabela();
    }

    private void carregarTabela() {
        ArrayList<Receita> lista = ArquivoReceita.lerLista();
        dados.setAll(lista);
    }

    private void limpar() {
        txtId.clear();
        txtNome.clear();
        txtIngredientes.clear();
        txtModoPreparo.clear();
        txtTempoPreparo.clear();
        tabela.getSelectionModel().clearSelection();
        lblMensagem.setText("");
        btnAdicionar.setDisable(false);
        btnAtualizar.setDisable(true);
        btnDeletar.setDisable(true);
        btnCancelar.setDisable(true);
    }
}
