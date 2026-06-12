package br.pucpr.projeto_crud_java.model;
import java.io.*;
import java.util.ArrayList;

public class ArquivoCategoria {


    private static final String CAMINHO_ARQUIVO = "categoria.dat";

    public static void salvarLista(ArrayList<Categoria> lista) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(CAMINHO_ARQUIVO));
            oos.writeObject(lista);
            oos.close();
        } catch (IOException e) {
            System.err.println("Erro ao salvar: " + e.getMessage());
        }
    }

    public static ArrayList<Categoria> lerLista() {
        ArrayList<Categoria> lista = new ArrayList<>();
        try {
            File arq = new File(CAMINHO_ARQUIVO);
            if (arq.exists()) {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(CAMINHO_ARQUIVO));
                lista = (ArrayList<Categoria>) ois.readObject();
                ois.close();
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao ler: " + e.getMessage());
        }
        return lista;
    }

    public static int gerarId() {
        ArrayList<Categoria> lista = lerLista();
        int maiorId = 0;
        for (Categoria c : lista) {
            if (c.getId() > maiorId) maiorId = c.getId();
        }
        return maiorId + 1;
    }

    public static void adicionar(Categoria c) {
        ArrayList<Categoria> lista = lerLista();
        lista.add(c);
        salvarLista(lista);
    }

    public static void atualizar(Categoria atualizado) {
        ArrayList<Categoria> lista = lerLista();
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getId() == atualizado.getId()) {
                lista.set(i, atualizado);
                break;
            }
        }
        salvarLista(lista);
    }

    public static void deletar(int id) {
        ArrayList<Categoria> lista = lerLista();
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getId() == id) {
                lista.remove(i);
                break;
            }
        }
        salvarLista(lista);
    }
}
