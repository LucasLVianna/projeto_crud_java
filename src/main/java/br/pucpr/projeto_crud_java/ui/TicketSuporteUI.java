package br.pucpr.projeto_crud_java.ui;

import br.pucpr.projeto_crud_java.model.ArquivoTicketSuporte;
import br.pucpr.projeto_crud_java.model.TicketSuporte;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;

public class TicketSuporteUI extends Stage {

    private TextField txtId = new TextField();
    private TextField txtTitulo = new TextField();
    private TextField txtPrioridade = new TextField();
    private TextField txtStatus = new TextField();

    private TableView<TicketSuporte> tabela = new TableView<>();
    private ObservableList<TicketSuporte> dados = FXCollections.observableArrayList();

    private Button btnAdicionar = new Button("Adicionar");
    private Button btnAtualizar = new Button("Atualizar");
    private Button btnDeletar = new Button("Deletar");
    private Button btnLimpar = new Button("Limpar");

    private Label lblMensagem = new Label();

    public TicketSuporteUI() {
        setTitle("Tickets de Suporte");

        txtId.setPromptText("ID");
        txtId.setEditable(false);
        txtTitulo.setPromptText("Titulo");
        txtPrioridade.setPromptText("Prioridade");
        txtStatus.setPromptText("Status");

        btnAtualizar.setDisable(true);
        btnDeletar.setDisable(true);

        TableColumn<TicketSuporte, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<TicketSuporte, String> colTitulo = new TableColumn<>("Titulo");
        colTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        colTitulo.setPrefWidth(220);

        TableColumn<TicketSuporte, String> colPrioridade = new TableColumn<>("Prioridade");
        colPrioridade.setCellValueFactory(new PropertyValueFactory<>("prioridade"));
        colPrioridade.setPrefWidth(140);

        TableColumn<TicketSuporte, String> colStatus = new TableColumn<>("Status");
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colStatus.setPrefWidth(140);

        tabela.getColumns().addAll(colId, colTitulo, colPrioridade, colStatus);
        tabela.setItems(dados);

        tabela.getSelectionModel().selectedItemProperty().addListener((obs, anterior, selecionado) -> {
            if (selecionado != null) {
                txtId.setText(String.valueOf(selecionado.getId()));
                txtTitulo.setText(selecionado.getTitulo());
                txtPrioridade.setText(selecionado.getPrioridade());
                txtStatus.setText(selecionado.getStatus());
                btnAtualizar.setDisable(false);
                btnDeletar.setDisable(false);
                btnAdicionar.setDisable(true);
            }
        });

        btnAdicionar.setOnAction(e -> {
            try {
                if (camposInvalidos()) {
                    lblMensagem.setText("Preencha todos os campos.");
                    return;
                }
                TicketSuporte ticket = new TicketSuporte(
                        ArquivoTicketSuporte.gerarId(),
                        txtTitulo.getText().trim(),
                        txtPrioridade.getText().trim(),
                        txtStatus.getText().trim()
                );
                ArquivoTicketSuporte.adicionar(ticket);
                lblMensagem.setText("Ticket adicionado.");
                limpar();
                carregarTabela();
            } catch (Exception ex) {
                lblMensagem.setText("Erro: " + ex.getMessage());
            }
        });

        btnAtualizar.setOnAction(e -> {
            try {
                if (camposInvalidos()) {
                    lblMensagem.setText("Preencha todos os campos.");
                    return;
                }
                TicketSuporte ticket = new TicketSuporte(
                        Integer.parseInt(txtId.getText()),
                        txtTitulo.getText().trim(),
                        txtPrioridade.getText().trim(),
                        txtStatus.getText().trim()
                );
                ArquivoTicketSuporte.atualizar(ticket);
                lblMensagem.setText("Ticket atualizado.");
                limpar();
                carregarTabela();
            } catch (Exception ex) {
                lblMensagem.setText("Erro: " + ex.getMessage());
            }
        });

        btnDeletar.setOnAction(e -> {
            TicketSuporte selecionado = tabela.getSelectionModel().getSelectedItem();
            if (selecionado == null) {
                lblMensagem.setText("Selecione um ticket.");
                return;
            }
            ArquivoTicketSuporte.deletar(selecionado.getId());
            lblMensagem.setText("Ticket deletado.");
            limpar();
            carregarTabela();
        });

        btnLimpar.setOnAction(e -> limpar());

        HBox campos = new HBox(8, txtId, txtTitulo, txtPrioridade, txtStatus);
        HBox botoes = new HBox(8, btnAdicionar, btnAtualizar, btnDeletar, btnLimpar);
        VBox root = new VBox(8, campos, botoes, lblMensagem, tabela);
        root.setStyle("-fx-padding: 12;");

        setScene(new Scene(root, 720, 450));
        carregarTabela();
    }

    private boolean camposInvalidos() {
        return txtTitulo.getText().trim().isEmpty()
                || txtPrioridade.getText().trim().isEmpty()
                || txtStatus.getText().trim().isEmpty();
    }

    private void carregarTabela() {
        ArrayList<TicketSuporte> lista = ArquivoTicketSuporte.lerLista();
        dados.setAll(lista);
    }

    private void limpar() {
        txtId.clear();
        txtTitulo.clear();
        txtPrioridade.clear();
        txtStatus.clear();
        tabela.getSelectionModel().clearSelection();
        lblMensagem.setText("");
        btnAdicionar.setDisable(false);
        btnAtualizar.setDisable(true);
        btnDeletar.setDisable(true);
    }
}
