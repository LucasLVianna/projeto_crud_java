package br.pucpr.projeto_crud_java.model;

import java.io.Serializable;
import java.time.LocalDate;

public class HistoricoPreco implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private int idProduto;
    private double preco;
    private LocalDate data;

    public HistoricoPreco(int id, int idProduto, double preco, LocalDate data) {
        this.id = id;
        this.idProduto = idProduto;
        this.preco = preco;
        this.data = data;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getIdProduto() { return idProduto; }
    public void setIdProduto(int idProduto) { this.idProduto = idProduto; }

    public double getPreco() { return preco; }
    public void setPreco(double preco) { this.preco = preco; }

    public LocalDate getData() { return data; }
    public void setData(LocalDate data) { this.data = data; }

    @Override
    public String toString() {
        return "HistoricoPreco [ID: " + id + "]" +
                " | Produto ID: " + idProduto +
                " | Preço: " + preco +
                " | Data: " + data;
    }
}
