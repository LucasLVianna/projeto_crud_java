package br.pucpr.projeto_crud_java.model;

import java.io.*;
import java.util.ArrayList;

public class ArquivoHistoricoPreco {

    private static final String CAMINHO_ARQUIVO = "historico_precos.dat";

    public static void salvarLista(ArrayList<HistoricoPreco> lista) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(CAMINHO_ARQUIVO));
            oos.writeObject(lista);
            oos.close();
        } catch (IOException e) {
            System.err.println("Erro ao salvar: " + e.getMessage());
        }
    }

    public static ArrayList<HistoricoPreco> lerLista() {
        ArrayList<HistoricoPreco> lista = new ArrayList<>();
        try {
            File arq = new File(CAMINHO_ARQUIVO);
            if (arq.exists()) {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(CAMINHO_ARQUIVO));
                lista = (ArrayList<HistoricoPreco>) ois.readObject();
                ois.close();
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao ler: " + e.getMessage());
        }
        return lista;
    }

    public static int gerarId() {
        ArrayList<HistoricoPreco> lista = lerLista();
        int maiorId = 0;
        for (HistoricoPreco h : lista) {
            if (h.getId() > maiorId) maiorId = h.getId();
        }
        return maiorId + 1;
    }

    public static void adicionar(HistoricoPreco h) {
        ArrayList<HistoricoPreco> lista = lerLista();
        lista.add(h);
        salvarLista(lista);
    }

    public static void atualizar(HistoricoPreco atualizado) {
        ArrayList<HistoricoPreco> lista = lerLista();
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getId() == atualizado.getId()) {
                lista.set(i, atualizado);
                break;
            }
        }
        salvarLista(lista);
    }

    public static void deletar(int id) {
        ArrayList<HistoricoPreco> lista = lerLista();
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getId() == id) {
                lista.remove(i);
                break;
            }
        }
        salvarLista(lista);
    }
}
