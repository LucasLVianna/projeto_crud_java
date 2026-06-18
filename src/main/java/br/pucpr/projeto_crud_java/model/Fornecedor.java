package br.pucpr.projeto_crud_java.model;
import java.io.Serializable;

public class Fornecedor implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String nome;
    private String contato;
    private String cep;
    private String pix;


    public Fornecedor(int id, String nome, String contato, String cep, String pix) {
        this.id = id;
        this.nome = nome;
        this.contato = contato;
        this.cep = cep;
        this.pix = pix;
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

    public String getContato() {
        return contato;
    }

    public void setContato(String contato) {
        this.contato = contato;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getPix() {
        return pix;
    }

    public void setPix(String pix) {
        this.pix = pix;
    }

    @Override
    public String toString() {
        return "Fornecedor [ID: " + id + "] " + nome +
                " | Contato: " + contato +
                " | CEP: " + cep +
                " | PIX: " + pix;
    }
}