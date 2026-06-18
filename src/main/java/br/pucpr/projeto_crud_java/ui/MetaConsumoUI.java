package br.pucpr.projeto_crud_java.ui;

import br.pucpr.projeto_crud_java.model.ArquivoMetaConsumo;
import br.pucpr.projeto_crud_java.model.MetaConsumo;

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

public class MetaConsumoUI extends Stage {

    private TextField txtId = new TextField();
    private TextField txtCategoria = new TextField();
    private TextField txtLimiteMensal = new TextField();
    private TextField txtUnidadeMedida = new TextField();

    private TableView<MetaConsumo> tabela = new TableView<>();
    private ObservableList<MetaConsumo> dados = FXCollections.observableArrayList();

    private Button btnAdicionar = new Button("Adicionar");
    private Button btnAtualizar = new Button("Atualizar");
    private Button btnDeletar = new Button("Deletar");
    private Button btnLimpar = new Button("Limpar");
    private Button btnCancelar = new Button("Cancelar");
    private Button btnVoltar = new Button("Voltar");

    private Label lblMensagem = new Label();

    public MetaConsumoUI() {
        setTitle("Metas de Consumo");

        txtId.setPromptText("ID");
        txtId.setEditable(false);
        txtCategoria.setPromptText("Categoria");
        txtLimiteMensal.setPromptText("Limite mensal");
        txtUnidadeMedida.setPromptText("Unidade de medida");

        btnAtualizar.setDisable(true);
        btnDeletar.setDisable(true);
        btnCancelar.setDisable(true);

        TableColumn<MetaConsumo, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<MetaConsumo, String> colCategoria = new TableColumn<>("Categoria");
        colCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        colCategoria.setPrefWidth(180);

        TableColumn<MetaConsumo, Double> colLimite = new TableColumn<>("Limite mensal");
        colLimite.setCellValueFactory(new PropertyValueFactory<>("limiteMensal"));
        colLimite.setPrefWidth(140);

        TableColumn<MetaConsumo, String> colUnidade = new TableColumn<>("Unidade");
        colUnidade.setCellValueFactory(new PropertyValueFactory<>("unidadeMedida"));
        colUnidade.setPrefWidth(160);

        tabela.getColumns().addAll(colId, colCategoria, colLimite, colUnidade);
        tabela.setItems(dados);

        tabela.getSelectionModel().selectedItemProperty().addListener((obs, anterior, selecionado) -> {
            if (selecionado != null) {
                txtId.setText(String.valueOf(selecionado.getId()));
                txtCategoria.setText(selecionado.getCategoria());
                txtLimiteMensal.setText(String.valueOf(selecionado.getLimiteMensal()));
                txtUnidadeMedida.setText(selecionado.getUnidadeMedida());
                btnAtualizar.setDisable(false);
                btnDeletar.setDisable(false);
                btnAdicionar.setDisable(true);
                btnCancelar.setDisable(false);
            }
        });

        btnAdicionar.setOnAction(e -> {
            try {
                if (camposTextoInvalidos()) {
                    lblMensagem.setText("Preencha todos os campos.");
                    return;
                }
                MetaConsumo meta = new MetaConsumo(
                        ArquivoMetaConsumo.gerarId(),
                        txtCategoria.getText().trim(),
                        lerLimiteMensal(),
                        txtUnidadeMedida.getText().trim()
                );
                ArquivoMetaConsumo.adicionar(meta);
                lblMensagem.setText("Meta de consumo adicionada.");
                limpar();
                carregarTabela();
            } catch (NumberFormatException ex) {
                lblMensagem.setText("Digite um limite mensal numerico valido.");
            } catch (Exception ex) {
                lblMensagem.setText("Erro: " + ex.getMessage());
            }
        });

        btnAtualizar.setOnAction(e -> {
            try {
                if (camposTextoInvalidos()) {
                    lblMensagem.setText("Preencha todos os campos.");
                    return;
                }
                MetaConsumo meta = new MetaConsumo(
                        Integer.parseInt(txtId.getText()),
                        txtCategoria.getText().trim(),
                        lerLimiteMensal(),
                        txtUnidadeMedida.getText().trim()
                );
                ArquivoMetaConsumo.atualizar(meta);
                lblMensagem.setText("Meta de consumo atualizada.");
                limpar();
                carregarTabela();
            } catch (NumberFormatException ex) {
                lblMensagem.setText("Digite um limite mensal numerico valido.");
            } catch (Exception ex) {
                lblMensagem.setText("Erro: " + ex.getMessage());
            }
        });

        btnDeletar.setOnAction(e -> {
            MetaConsumo selecionada = tabela.getSelectionModel().getSelectedItem();
            if (selecionada == null) {
                lblMensagem.setText("Selecione uma meta de consumo.");
                return;
            }
            ArquivoMetaConsumo.deletar(selecionada.getId());
            lblMensagem.setText("Meta de consumo deletada.");
            limpar();
            carregarTabela();
        });

        btnLimpar.setOnAction(e -> limpar());

        btnCancelar.setOnAction(e -> limpar());

        btnVoltar.setOnAction(e -> {
            Stage stage = (Stage) btnVoltar.getScene().getWindow();
            stage.close();
        });

        HBox campos = new HBox(8, txtId, txtCategoria, txtLimiteMensal, txtUnidadeMedida);
        HBox botoes = new HBox(8, btnAdicionar, btnAtualizar, btnDeletar, btnLimpar, btnCancelar, btnVoltar);
        VBox root = new VBox(8, campos, botoes, lblMensagem, tabela);
        root.setStyle("-fx-padding: 12;");

        setScene(new Scene(root, 760, 450));
        carregarTabela();
    }

    private boolean camposTextoInvalidos() {
        return txtCategoria.getText().trim().isEmpty()
                || txtLimiteMensal.getText().trim().isEmpty()
                || txtUnidadeMedida.getText().trim().isEmpty();
    }

    private double lerLimiteMensal() {
        String valor = txtLimiteMensal.getText().trim().replace(",", ".");
        return Double.parseDouble(valor);
    }

    private void carregarTabela() {
        ArrayList<MetaConsumo> lista = ArquivoMetaConsumo.lerLista();
        dados.setAll(lista);
    }

    private void limpar() {
        txtId.clear();
        txtCategoria.clear();
        txtLimiteMensal.clear();
        txtUnidadeMedida.clear();
        tabela.getSelectionModel().clearSelection();
        lblMensagem.setText("");
        btnAdicionar.setDisable(false);
        btnAtualizar.setDisable(true);
        btnDeletar.setDisable(true);
        btnCancelar.setDisable(true);
    }
}
