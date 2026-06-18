package br.pucpr.projeto_crud_java.model;

import java.io.*;
import java.util.ArrayList;

public class ArquivoReceita {

    private static final String CAMINHO_ARQUIVO = "receita.dat";

    public static void salvarLista(ArrayList<Receita> lista) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(CAMINHO_ARQUIVO));
            oos.writeObject(lista);
            oos.close();
        } catch (IOException e) {
            System.err.println("Erro ao salvar: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public static ArrayList<Receita> lerLista() {
        ArrayList<Receita> lista = new ArrayList<>();
        try {
            File arq = new File(CAMINHO_ARQUIVO);
            if (arq.exists()) {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(CAMINHO_ARQUIVO));
                lista = (ArrayList<Receita>) ois.readObject();
                ois.close();
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao ler: " + e.getMessage());
        }
        return lista;
    }

    public static int gerarId() {
        ArrayList<Receita> lista = lerLista();
        int maiorId = 0;
        for (Receita r : lista) {
            if (r.getId() > maiorId) maiorId = r.getId();
        }
        return maiorId + 1;
    }

    public static void adicionar(Receita r) {
        ArrayList<Receita> lista = lerLista();
        lista.add(r);
        salvarLista(lista);
    }

    public static void atualizar(Receita atualizada) {
        ArrayList<Receita> lista = lerLista();
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getId() == atualizada.getId()) {
                lista.set(i, atualizada);
                break;
            }
        }
        salvarLista(lista);
    }

    public static void deletar(int id) {
        ArrayList<Receita> lista = lerLista();
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getId() == id) {
                lista.remove(i);
                break;
            }
        }
        salvarLista(lista);
    }
}