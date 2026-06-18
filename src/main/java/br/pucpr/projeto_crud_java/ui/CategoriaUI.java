package br.pucpr.projeto_crud_java.ui;

import br.pucpr.projeto_crud_java.model.ArquivoCategoria;
import br.pucpr.projeto_crud_java.model.Categoria;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;

public class CategoriaUI extends Stage {

    private TextField txtNome = new TextField();
    private TextField txtDescricao = new TextField();
    private TextField txtId = new TextField();
    private TextField txtSetor = new TextField();

    private TableView<Categoria> tabela = new TableView<>();
    private ObservableList<Categoria> dados = FXCollections.observableArrayList();

    private Button btnAdicionar = new Button("Adicionar");
    private Button btnAtualizar = new Button("Atualizar");
    private Button btnDeletar = new Button("Deletar");
    private Button btnLimpar = new Button("Limpar");
    private Button btnCancelar = new Button("Cancelar");
    private Button btnVoltar = new Button("Voltar");

    private Label lblMensagem = new Label();

    public CategoriaUI() {
        setTitle("Categorias");

        txtId.setPromptText("ID");
        txtId.setEditable(false);
        txtNome.setPromptText("Nome");
        txtDescricao.setPromptText("Descrição");
        txtSetor.setPromptText("Setor");


        btnAtualizar.setDisable(true);
        btnDeletar.setDisable(true);
        btnCancelar.setDisable(true);

        // Colunas da tabela
        TableColumn<Categoria, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Categoria, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colNome.setPrefWidth(150);

        TableColumn<Categoria, String> colDescricao = new TableColumn<>("Descrição");
        colDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        colDescricao.setPrefWidth(150);


        TableColumn<Categoria, String> colSetor = new TableColumn<>("Setor");
        colSetor.setCellValueFactory(new PropertyValueFactory<>("setor"));
        colSetor.setPrefWidth(150);

        tabela.getColumns().addAll(colId, colNome, colDescricao);
        tabela.setItems(dados);

        // Clique na tabela preenche o formulário
        tabela.getSelectionModel().selectedItemProperty().addListener((obs, ant, selecionado) -> {
            if (selecionado != null) {
                txtId.setText(String.valueOf(selecionado.getId()));
                txtNome.setText(selecionado.getNome());
                txtDescricao.setText(selecionado.getDescricao());
                btnAtualizar.setDisable(false);
                btnDeletar.setDisable(false);
                btnAdicionar.setDisable(true);
                btnCancelar.setDisable(false);
            }
        });

        // Ações
        btnAdicionar.setOnAction(e -> {
            try {
                if (txtNome.getText().trim().isEmpty() || txtDescricao.getText().trim().isEmpty() || txtSetor.getText().trim().isEmpty()) {
                    lblMensagem.setText("Preencha todos os campos.");
                    return;
                }
                Categoria c = new Categoria(ArquivoCategoria.gerarId(), txtNome.getText().trim(), txtDescricao.getText().trim(), txtSetor.getText().trim());
                ArquivoCategoria.adicionar(c);
                lblMensagem.setText("Categoria adicionado.");
                limpar();
                carregarTabela();
            } catch (Exception ex) {
                lblMensagem.setText("Erro: " + ex.getMessage());
            }
        });

        btnAtualizar.setOnAction(e -> {
            try {
                if (txtNome.getText().trim().isEmpty() || txtDescricao.getText().trim().isEmpty() || txtSetor.getText().trim().isEmpty()) {
                    lblMensagem.setText("Preencha todos os campos.");
                    return;
                }
                Categoria c = new Categoria(Integer.parseInt(txtId.getText()), txtNome.getText().trim(), txtDescricao.getText().trim(), txtSetor.getText().trim());
                ArquivoCategoria.atualizar(c);
                lblMensagem.setText("Categoria atualizado.");
                limpar();
                carregarTabela();
            } catch (Exception ex) {
                lblMensagem.setText("Erro: " + ex.getMessage());
            }
        });

        btnDeletar.setOnAction(e -> {
            Categoria selecionado = tabela.getSelectionModel().getSelectedItem();
            if (selecionado == null) {
                lblMensagem.setText("Selecione uma categoria.");
                return;
            }
            ArquivoCategoria.deletar(selecionado.getId());
            lblMensagem.setText("Categoria deletado.");
            limpar();
            carregarTabela();
        });

        btnLimpar.setOnAction(e -> limpar());

        btnCancelar.setOnAction(e -> limpar());

        btnVoltar.setOnAction(e -> {
            Stage stage = (Stage) btnVoltar.getScene().getWindow();
            stage.close();
        });

        // Layout
        HBox campos = new HBox(8, txtId, txtNome, txtDescricao, txtSetor);
        HBox botoes = new HBox(8, btnAdicionar, btnAtualizar, btnDeletar, btnLimpar, btnCancelar, btnVoltar);
        VBox root = new VBox(8, campos, botoes, lblMensagem, tabela);
        root.setStyle("-fx-padding: 12;");

        setScene(new Scene(root, 700, 450));
        carregarTabela();
    }

    private void carregarTabela() {
        ArrayList<Categoria> lista = ArquivoCategoria.lerLista();
        dados.setAll(lista);
    }

    private void limpar() {
        txtId.clear();
        txtNome.clear();
        txtDescricao.clear();
        tabela.getSelectionModel().clearSelection();
        lblMensagem.setText("");
        btnAdicionar.setDisable(false);
        btnAtualizar.setDisable(true);
        btnDeletar.setDisable(true);
        btnCancelar.setDisable(true);
    }
}
