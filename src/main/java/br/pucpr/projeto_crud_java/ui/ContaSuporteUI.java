package br.pucpr.projeto_crud_java.ui;

import br.pucpr.projeto_crud_java.model.ArquivoContaSuporte;
import br.pucpr.projeto_crud_java.model.ContaSuporte;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;

public class ContaSuporteUI extends Stage {

    private TextField txtId = new TextField();
    private TextField txtNome = new TextField();
    private TextField txtCep = new TextField();
    private TextField txtEmail = new TextField();
    private TextField txtSenha = new TextField();
    private TextField txtCpf = new TextField();

    private TableView<ContaSuporte> tabela = new TableView<>();
    private ObservableList<ContaSuporte> dados = FXCollections.observableArrayList();

    private Button btnAdicionar = new Button("Adicionar");
    private Button btnAtualizar = new Button("Atualizar");
    private Button btnDeletar = new Button("Deletar");
    private Button btnLimpar = new Button("Limpar");
    private Button btnCancelar = new Button("Cancelar");
    private Button btnVoltar = new Button("Voltar");

    private Label lblMensagem = new Label();

    private boolean validarCep(String cep) {
        return cep.matches("\\d{8}");
    }

    private boolean validarEmail(String email) {
        return email.contains("@") && email.contains(".");
    }

    private boolean validarCpf(String cpf) {
        return cpf.matches("\\d{11}");
    }

    public ContaSuporteUI() {
        setTitle("Conta de Suporte");

        txtId.setPromptText("ID");
        txtId.setEditable(false);
        txtNome.setPromptText("Nome");
        txtCep.setPromptText("CEP");
        txtEmail.setPromptText("E-mail");
        txtSenha.setPromptText("Senha");
        txtCpf.setPromptText("CPF");

        btnAtualizar.setDisable(true);
        btnDeletar.setDisable(true);
        btnCancelar.setDisable(true);

        TableColumn<ContaSuporte, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<ContaSuporte, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colNome.setPrefWidth(150);

        TableColumn<ContaSuporte, String> colCep = new TableColumn<>("CEP");
        colCep.setCellValueFactory(new PropertyValueFactory<>("cep"));
        colCep.setPrefWidth(100);

        TableColumn<ContaSuporte, String> colEmail = new TableColumn<>("E-mail");
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colEmail.setPrefWidth(180);


        TableColumn<ContaSuporte, String> colSenha = new TableColumn<>("Senha");
        colSenha.setCellValueFactory(new PropertyValueFactory<>("senha"));
        colSenha.setPrefWidth(180);

        TableColumn<ContaSuporte, String> colCpf = new TableColumn<>("CPF");
        colCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        colCpf.setPrefWidth(120);

        tabela.getColumns().addAll(colId, colNome, colCep, colEmail, colSenha, colCpf);
        tabela.setItems(dados);

        tabela.getSelectionModel().selectedItemProperty().addListener((obs, ant, selecionado) -> {
            if (selecionado != null) {
                txtId.setText(String.valueOf(selecionado.getId()));
                txtNome.setText(selecionado.getNome());
                txtCep.setText(selecionado.getCep());
                txtEmail.setText(selecionado.getEmail());
                txtSenha.setText(selecionado.getSenha());
                txtCpf.setText(selecionado.getCpf());
                btnAtualizar.setDisable(false);
                btnDeletar.setDisable(false);
                btnAdicionar.setDisable(true);
                btnCancelar.setDisable(false);
            }
        });

        btnAdicionar.setOnAction(e -> {
            try {
                if (txtNome.getText().trim().isEmpty() || txtCep.getText().trim().isEmpty()
                        || txtEmail.getText().trim().isEmpty() || txtSenha.getText().trim().isEmpty()
                        || txtCpf.getText().trim().isEmpty()) {
                    lblMensagem.setText("Preencha todos os campos.");
                    return;
                }
                if (!validarCep(txtCep.getText().trim())) {
                    lblMensagem.setText("CEP invalido. Digite 8 numeros.");
                    return;
                }
                if (!validarEmail(txtEmail.getText().trim())) {
                    lblMensagem.setText("E-mail invalido.");
                    return;
                }
                if (!validarCpf(txtCpf.getText().trim())) {
                    lblMensagem.setText("CPF invalido. Digite 11 numeros.");
                    return;
                }
                ContaSuporte novaConta = new ContaSuporte(ArquivoContaSuporte.gerarId(),
                        txtNome.getText().trim(), txtCep.getText().trim(),
                        txtEmail.getText().trim(), txtSenha.getText().trim(), txtCpf.getText().trim());
                ArquivoContaSuporte.adicionar(novaConta);
                lblMensagem.setText("Conta adicionada.");
                limpar();
                carregarTabela();
            } catch (Exception ex) {
                lblMensagem.setText("Erro: " + ex.getMessage());
            }
        });

        btnAtualizar.setOnAction(e -> {
            try {
                if (txtNome.getText().trim().isEmpty() || txtCep.getText().trim().isEmpty()
                        || txtEmail.getText().trim().isEmpty() || txtSenha.getText().trim().isEmpty()
                        || txtCpf.getText().trim().isEmpty()) {
                    lblMensagem.setText("Preencha todos os campos.");
                    return;
                }
                if (!validarCep(txtCep.getText().trim())) {
                    lblMensagem.setText("CEP invalido. Digite 8 numeros.");
                    return;
                }
                if (!validarEmail(txtEmail.getText().trim())) {
                    lblMensagem.setText("E-mail invalido.");
                    return;
                }
                if (!validarCpf(txtCpf.getText().trim())) {
                    lblMensagem.setText("CPF invalido. Digite 11 numeros.");
                    return;
                }
                ContaSuporte contaAtualizada = new ContaSuporte(Integer.parseInt(txtId.getText()),
                        txtNome.getText().trim(), txtCep.getText().trim(),
                        txtEmail.getText().trim(), txtSenha.getText().trim(), txtCpf.getText().trim());
                ArquivoContaSuporte.atualizar(contaAtualizada);
                lblMensagem.setText("Conta atualizada.");
                limpar();
                carregarTabela();
            } catch (Exception ex) {
                lblMensagem.setText("Erro: " + ex.getMessage());
            }
        });

        btnDeletar.setOnAction(e -> {
            ContaSuporte selecionado = tabela.getSelectionModel().getSelectedItem();
            if (selecionado == null) {
                lblMensagem.setText("Selecione uma conta.");
                return;
            }
            ArquivoContaSuporte.deletar(selecionado.getId());
            lblMensagem.setText("Conta deletada.");
            limpar();
            carregarTabela();
        });

        btnLimpar.setOnAction(e -> limpar());
        btnCancelar.setOnAction(e -> limpar());

        btnVoltar.setOnAction(e -> {
            Stage stage = (Stage) btnVoltar.getScene().getWindow();
            stage.close();
        });

        HBox campos = new HBox(8, txtId, txtNome, txtCep, txtEmail, txtSenha, txtCpf);
        HBox botoes = new HBox(8, btnAdicionar, btnAtualizar, btnDeletar, btnLimpar, btnCancelar, btnVoltar);
        VBox root = new VBox(8, campos, botoes, lblMensagem, tabela);
        root.setStyle("-fx-padding: 12;");

        setScene(new Scene(root, 800, 450));
        carregarTabela();
    }

    private void carregarTabela() {
        ArrayList<ContaSuporte> lista = ArquivoContaSuporte.lerLista();
        dados.setAll(lista);
    }

    private void limpar() {
        txtId.clear();
        txtNome.clear();
        txtCep.clear();
        txtEmail.clear();
        txtSenha.clear();
        txtCpf.clear();
        tabela.getSelectionModel().clearSelection();
        lblMensagem.setText("");
        btnAdicionar.setDisable(false);
        btnAtualizar.setDisable(true);
        btnDeletar.setDisable(true);
        btnCancelar.setDisable(true);
    }
}
