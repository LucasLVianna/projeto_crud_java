package br.pucpr.projeto_crud_java.model;

import java.io.*;
import java.util.ArrayList;

public class ArquivoContaSuporte {

    private static final String CAMINHO_ARQUIVO = "contaSuporte.dat";

    public static void salvarLista(ArrayList<ContaSuporte> lista) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(CAMINHO_ARQUIVO));
            oos.writeObject(lista);
            oos.close();
        } catch (IOException e) {
            System.err.println("Erro ao salvar: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public static ArrayList<ContaSuporte> lerLista() {
        ArrayList<ContaSuporte> lista = new ArrayList<>();
        try {
            File arq = new File(CAMINHO_ARQUIVO);
            if (arq.exists()) {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(CAMINHO_ARQUIVO));
                lista = (ArrayList<ContaSuporte>) ois.readObject();
                ois.close();
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao ler: " + e.getMessage());
        }
        return lista;
    }

    public static int gerarId() {
        ArrayList<ContaSuporte> lista = lerLista();
        int maiorId = 0;
        for (ContaSuporte c : lista) {
            if (c.getId() > maiorId) maiorId = c.getId();
        }
        return maiorId + 1;
    }

    public static void adicionar(ContaSuporte c) {
        ArrayList<ContaSuporte> lista = lerLista();
        lista.add(c);
        salvarLista(lista);
    }

    public static void atualizar(ContaSuporte atualizado) {
        ArrayList<ContaSuporte> lista = lerLista();
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getId() == atualizado.getId()) {
                lista.set(i, atualizado);
                break;
            }
        }
        salvarLista(lista);
    }

    public static void deletar(int id) {
        ArrayList<ContaSuporte> lista = lerLista();
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getId() == id) {
                lista.remove(i);
                break;
            }
        }
        salvarLista(lista);
    }
}