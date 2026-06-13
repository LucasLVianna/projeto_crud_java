package br.pucpr.projeto_crud_java.ui;

import br.pucpr.projeto_crud_java.model.ArquivoFornecedor;
import br.pucpr.projeto_crud_java.model.Fornecedor;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;

public class FornecedorUI extends Stage {

    private TextField txtNome = new TextField();
    private TextField txtContato = new TextField();
    private TextField txtCEP = new TextField();
    private TextField txtPIX = new TextField();
    private TextField txtId = new TextField();

    // Telefone: aceita (XX) XXXXX-XXXX ou (XX) XXXX-XXXX
    private boolean validarTelefone(String contato) {
        return contato.matches("\\(\\d{2}\\)\\s?\\d{4,5}-\\d{4}");
    }

    // CEP: aceita XXXXX-XXX ou XXXXXXXX
    private boolean validarCEP(String cep) {
        return cep.matches("\\d{5}-?\\d{3}");
    }

    // PIX como CPF: aceita XXX.XXX.XXX-XX ou só números
    private boolean validarCPF(String cpf) {
        return cpf.matches("\\d{3}\\.?\\d{3}\\.?\\d{3}-?\\d{2}");
    }

    private TableView<Fornecedor> tabela = new TableView<>();
    private ObservableList<Fornecedor> dados = FXCollections.observableArrayList();

    private Button btnAdicionar = new Button("Adicionar");
    private Button btnAtualizar = new Button("Atualizar");
    private Button btnDeletar = new Button("Deletar");
    private Button btnLimpar = new Button("Limpar");

    private Label lblMensagem = new Label();

    public FornecedorUI() {
        setTitle("Fornecedor");

        txtId.setPromptText("ID");
        txtId.setEditable(false);
        txtNome.setPromptText("Nome");
        txtContato.setPromptText("Contato");
        txtCEP.setPromptText("CEP");
        txtPIX.setPromptText("PIX");

        btnAtualizar.setDisable(true);
        btnDeletar.setDisable(true);

        // Colunas da tabela
        TableColumn<Fornecedor, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Fornecedor, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colNome.setPrefWidth(150);

        TableColumn<Fornecedor, String> colContato = new TableColumn<>("Contato");
        colContato.setCellValueFactory(new PropertyValueFactory<>("contato"));
        colContato.setPrefWidth(150);

        TableColumn<Fornecedor, String> colCEP = new TableColumn<>("CEP");
        colCEP.setCellValueFactory(new PropertyValueFactory<>("cep"));
        colCEP.setPrefWidth(100);

        TableColumn<Fornecedor, String> colPIX = new TableColumn<>("PIX");
        colPIX.setCellValueFactory(new PropertyValueFactory<>("pix"));
        colPIX.setPrefWidth(100);

        tabela.getColumns().addAll(colId, colNome, colContato, colCEP, colPIX);
        tabela.setItems(dados);

        // Clique na tabela preenche o formulário
        tabela.getSelectionModel().selectedItemProperty().addListener((obs, ant, selecionado) -> {
            if (selecionado != null) {
                txtId.setText(String.valueOf(selecionado.getId()));
                txtNome.setText(selecionado.getNome());
                txtContato.setText(selecionado.getContato());
                txtCEP.setText(selecionado.getCep());
                txtPIX.setText(selecionado.getPix());
                btnAtualizar.setDisable(false);
                btnDeletar.setDisable(false);
                btnAdicionar.setDisable(true);
            }
        });

        // Ações
        btnAdicionar.setOnAction(e -> {
            try {
                if (txtNome.getText().trim().isEmpty() || txtContato.getText().trim().isEmpty() || txtCEP.getText().trim().isEmpty() || txtPIX.getText().trim().isEmpty()) {
                    lblMensagem.setText("Preencha todos os campos.");
                    return;
                }
                if (txtNome.getText().trim().isEmpty() || txtContato.getText().trim().isEmpty() || txtCEP.getText().trim().isEmpty() || txtPIX.getText().trim().isEmpty()) {
                    lblMensagem.setText("Preencha todos os campos.");
                    return;
                }
                if (!validarTelefone(txtContato.getText().trim())) {
                    lblMensagem.setText("Contato inválido. Use (XX) XXXXX-XXXX.");
                    return;
                }
                if (!validarCEP(txtCEP.getText().trim())) {
                    lblMensagem.setText("CEP inválido. Use XXXXX-XXX.");
                    return;
                }
                if (!validarCPF(txtPIX.getText().trim())) {
                    lblMensagem.setText("PIX/CPF inválido. Use XXX.XXX.XXX-XX.");
                    return;
                }
                Fornecedor novoFornecedor = new Fornecedor(ArquivoFornecedor.gerarId(), txtNome.getText().trim(), txtContato.getText().trim(), txtCEP.getText().trim(), txtPIX.getText().trim());
                ArquivoFornecedor.adicionar(novoFornecedor);
                lblMensagem.setText("Fornecedor adicionado.");
                carregarTabela();
            } catch (Exception ex) {
                lblMensagem.setText("Erro: " + ex.getMessage());
            }
        });

        btnAtualizar.setOnAction(e -> {
            try {
                if (txtNome.getText().trim().isEmpty() || txtContato.getText().trim().isEmpty() || txtCEP.getText().trim().isEmpty() || txtPIX.getText().trim().isEmpty()) {
                    lblMensagem.setText("Preencha todos os campos.");
                    return;
                }
                if (txtNome.getText().trim().isEmpty() || txtContato.getText().trim().isEmpty() || txtCEP.getText().trim().isEmpty() || txtPIX.getText().trim().isEmpty()) {
                    lblMensagem.setText("Preencha todos os campos.");
                    return;
                }
                if (!validarTelefone(txtContato.getText().trim())) {
                    lblMensagem.setText("Contato inválido. Use (XX) XXXXX-XXXX.");
                    return;
                }
                if (!validarCEP(txtCEP.getText().trim())) {
                    lblMensagem.setText("CEP inválido. Use XXXXX-XXX.");
                    return;
                }
                if (!validarCPF(txtPIX.getText().trim())) {
                    lblMensagem.setText("PIX/CPF inválido. Use XXX.XXX.XXX-XX.");
                    return;
                }
                Fornecedor fornecedorAtualizado = new Fornecedor(Integer.parseInt(txtId.getText()), txtNome.getText().trim(), txtContato.getText().trim(), txtCEP.getText().trim(), txtPIX.getText().trim());
                ArquivoFornecedor.atualizar(fornecedorAtualizado);
                lblMensagem.setText("Fornecedor atualizado.");
                carregarTabela();
            } catch (Exception ex) {
                lblMensagem.setText("Erro: " + ex.getMessage());
            }
        });

        btnDeletar.setOnAction(e -> {
            Fornecedor selecionado = tabela.getSelectionModel().getSelectedItem();
            if (selecionado == null) {
                lblMensagem.setText("Selecione um fornecedor.");
                return;
            }
            ArquivoFornecedor.deletar(selecionado.getId());
            lblMensagem.setText("Fornecedor deletado.");
            carregarTabela();
        });

        btnLimpar.setOnAction(e -> limpar());

        // Layout
        HBox campos = new HBox(8, txtId, txtNome, txtContato, txtCEP, txtPIX);
        HBox botoes = new HBox(8, btnAdicionar, btnAtualizar, btnDeletar, btnLimpar);
        VBox root = new VBox(8, campos, botoes, lblMensagem, tabela);
        root.setStyle("-fx-padding: 12;");

        setScene(new Scene(root, 700, 450));
        carregarTabela();
    }

    private void carregarTabela() {
        ArrayList<Fornecedor> lista = ArquivoFornecedor.lerLista();
        dados.setAll(lista);
    }

    private void limpar() {
        txtId.clear();
        txtNome.clear();
        txtContato.clear();
        txtCEP.clear();
        txtPIX.clear();
        tabela.getSelectionModel().clearSelection();
        lblMensagem.setText("");
        btnAdicionar.setDisable(false);
        btnAtualizar.setDisable(true);
        btnDeletar.setDisable(true);
    }
}