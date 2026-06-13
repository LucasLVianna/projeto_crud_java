package br.pucpr.projeto_crud_java.model;
import java.io.*;
import java.util.ArrayList;

public class ArquivoFornecedor {

    private static final String CAMINHO_ARQUIVO = "fornecedor.dat";

    public static void salvarLista(ArrayList<Fornecedor> lista) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(CAMINHO_ARQUIVO));
            oos.writeObject(lista);
            oos.close();
        } catch (IOException e) {
            System.err.println("Erro ao salvar: " + e.getMessage());
        }
    }

    public static ArrayList<Fornecedor> lerLista() {
        ArrayList<Fornecedor> lista = new ArrayList<>();
        try {
            File arq = new File(CAMINHO_ARQUIVO);
            if (arq.exists()) {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(CAMINHO_ARQUIVO));
                lista = (ArrayList<Fornecedor>) ois.readObject();
                ois.close();
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao ler: " + e.getMessage());
        }
        return lista;
    }

    public static int gerarId() {
        ArrayList<Fornecedor> lista = lerLista();
        int maiorId = 0;
        for (Fornecedor f : lista) {
            if (f.getId() > maiorId) maiorId = f.getId();
        }
        return maiorId + 1;
    }

    public static void adicionar(Fornecedor f) {
        ArrayList<Fornecedor> lista = lerLista();
        lista.add(f);
        salvarLista(lista);
    }

    public static void atualizar(Fornecedor atualizado) {
        ArrayList<Fornecedor> lista = lerLista();
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getId() == atualizado.getId()) {
                lista.set(i, atualizado);
                break;
            }
        }
        salvarLista(lista);
    }

    public static void deletar(int id) {
        ArrayList<Fornecedor> lista = lerLista();
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getId() == id) {
                lista.remove(i);
                break;
            }
        }
        salvarLista(lista);
    }
}