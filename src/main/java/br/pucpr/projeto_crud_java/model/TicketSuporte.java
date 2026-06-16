package br.pucpr.projeto_crud_java.model;

import java.io.Serializable;

public class TicketSuporte implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String titulo;
    private String prioridade;
    private String status;

    public TicketSuporte(int id, String titulo, String prioridade, String status) {
        this.id = id;
        this.titulo = titulo;
        this.prioridade = prioridade;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(String prioridade) {
        this.prioridade = prioridade;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "TicketSuporte [ID: " + id + "] " + titulo +
                " | Prioridade: " + prioridade +
                " | Status: " + status;
    }
}
