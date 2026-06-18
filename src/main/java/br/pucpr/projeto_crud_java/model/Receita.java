package br.pucpr.projeto_crud_java.model;
import java.io.Serializable;

public class Receita implements Serializable {

    private int id;
    private String nome;
    private String ingredientes;
    private String modoPreparo;
    private int tempoPreparo;

    public Receita(int id, String nome, String ingredientes, String modoPreparo, int tempoPreparo) {
        this.id = id;
        this.nome = nome;
        this.ingredientes = ingredientes;
        this.modoPreparo = modoPreparo;
        this.tempoPreparo = tempoPreparo;
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

    public String getIngredientes() {
        return ingredientes;
    }
    public void setIngredientes(String ingredientes) {
        this.ingredientes = ingredientes;
    }

    public String getModoPreparo() {
        return modoPreparo;
    }
    public void setModoPreparo(String modoPreparo) {this.modoPreparo = modoPreparo;}

    public int getTempoPreparo() {
        return tempoPreparo;
    }
    public void setTempoPreparo(int tempoPreparo) {
        this.tempoPreparo = tempoPreparo;
    }


    @Override
    public String toString() {
        return "Receita [ID: " + id + "] " + nome +
                " | Tempo: " + tempoPreparo + " min" +
                " | Ingredientes: " + ingredientes +
                " | Modo de Preparo: " + modoPreparo;
    }
}





