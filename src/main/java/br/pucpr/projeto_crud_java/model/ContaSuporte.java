package br.pucpr.projeto_crud_java.model;

import java.io.Serializable;

public class ContaSuporte implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private String nome;
    private String cep;
    private String email;
    private String senha;
    private String cpf;

    public ContaSuporte(int id, String nome, String cep, String email, String senha, String cpf) {
        this.id = id;
        this.nome = nome;
        this.cep = cep;
        this.email = email;
        this.senha = senha;
        this.cpf = cpf;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCep() { return cep; }
    public void setCep(String cep) { this.cep = cep; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    @Override
    public String toString() {
        return "ContaSuporte [ID: " + id + "] " + nome +
                " | E-mail: " + email +
                " | CEP: " + cep +
                " | CPF: " + cpf;
    }
}
