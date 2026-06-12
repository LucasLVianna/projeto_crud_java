package br.pucpr.projeto_crud_java.model;
import java.io.Serializable;

public class Produto implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private String nome;
    private String categoria;
    private String unidade_medida;

    public Produto(int id, String nome, String categoria, String unidade_medida) {
        this.id = id;
        this.nome = nome;
        this.categoria = categoria;
        this.unidade_medida = unidade_medida;
    }

    // Getters e Setters


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

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getUnidade_medida() {
        return unidade_medida;
    }

    public void setUnidade_medida(String unidade_medida) {
        this.unidade_medida = unidade_medida;
    }

    @Override
    public String toString() {
        return "Produto [ID: " + id + "] " + nome +
                " | Categoria: " + categoria +
                " | Unidade Medida: " + unidade_medida;
    }
}