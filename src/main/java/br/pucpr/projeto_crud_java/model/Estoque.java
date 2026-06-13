package br.pucpr.projeto_crud_java.model;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

public class Estoque implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String nome;
    private LocalDate data_criacao;
    private String status;

    public Estoque(int id, String nome, LocalDate data_criacao, String status) {
        this.id = id;
        this.nome = nome;
        this.data_criacao = data_criacao;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getData_criacao() {
        return data_criacao;
    }

    public void setData_criacao(LocalDate data_criacao) {
        this.data_criacao = data_criacao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Estoque [ID: " + id + "] " + nome +
                " | Data de Criação: " + data_criacao +
                " | Status: " + status;
    }
}