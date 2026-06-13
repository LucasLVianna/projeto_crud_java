package br.pucpr.projeto_crud_java.model;
import java.io.*;
import java.util.ArrayList;

public class ArquivoEstoque {

    private static final String CAMINHO_ARQUIVO = "estoque.dat";

    public static void salvarLista(ArrayList<Estoque> lista) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(CAMINHO_ARQUIVO));
            oos.writeObject(lista);
            oos.close();
        } catch (IOException e) {
            System.err.println("Erro ao salvar: " + e.getMessage());
        }
    }

    public static ArrayList<Estoque> lerLista() {
        ArrayList<Estoque> lista = new ArrayList<>();
        try {
            File arq = new File(CAMINHO_ARQUIVO);
            if (arq.exists()) {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(CAMINHO_ARQUIVO));
                lista = (ArrayList<Estoque>) ois.readObject();
                ois.close();
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao ler: " + e.getMessage());
        }
        return lista;
    }

    public static int gerarId() {
        ArrayList<Estoque> lista = lerLista();
        int maiorId = 0;
        for (Estoque e : lista) {
            if (e.getId() > maiorId) maiorId = e.getId();
        }
        return maiorId + 1;
    }

    public static void adicionar(Estoque e) {
        ArrayList<Estoque> lista = lerLista();
        lista.add(e);
        salvarLista(lista);
    }

    public static void atualizar(Estoque atualizado) {
        ArrayList<Estoque> lista = lerLista();
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getId() == atualizado.getId()) {
                lista.set(i, atualizado);
                break;
            }
        }
        salvarLista(lista);
    }

    public static void deletar(int id) {
        ArrayList<Estoque> lista = lerLista();
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getId() == id) {
                lista.remove(i);
                break;
            }
        }
        salvarLista(lista);
    }
}