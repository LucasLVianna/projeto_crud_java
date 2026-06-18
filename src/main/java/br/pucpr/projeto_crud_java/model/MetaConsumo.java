package br.pucpr.projeto_crud_java.model;

import java.io.Serializable;

public class MetaConsumo implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String categoria;
    private double limiteMensal;
    private String unidadeMedida;

    public MetaConsumo(int id, String categoria, double limiteMensal, String unidadeMedida) {
        this.id = id;
        this.categoria = categoria;
        this.limiteMensal = limiteMensal;
        this.unidadeMedida = unidadeMedida;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public double getLimiteMensal() {
        return limiteMensal;
    }

    public void setLimiteMensal(double limiteMensal) {
        this.limiteMensal = limiteMensal;
    }

    public String getUnidadeMedida() {
        return unidadeMedida;
    }

    public void setUnidadeMedida(String unidadeMedida) {
        this.unidadeMedida = unidadeMedida;
    }

    @Override
    public String toString() {
        return "MetaConsumo [ID: " + id + "] " + categoria +
                " | Limite mensal: " + limiteMensal +
                " | Unidade: " + unidadeMedida;
    }
}
