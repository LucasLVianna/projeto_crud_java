package br.pucpr.projeto_crud_java.model;
import java.io.*;
import java.util.ArrayList;

public class ArquivoListaCompras {

    private static final String CAMINHO_ARQUIVO = "listaCompras.dat";

    public static void salvarLista(ArrayList<ListaCompras> lista) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(CAMINHO_ARQUIVO));
            oos.writeObject(lista);
            oos.close();
        } catch (IOException e) {
            System.err.println("Erro ao salvar: " + e.getMessage());
        }
    }

    public static ArrayList<ListaCompras> lerLista() {
        ArrayList<ListaCompras> lista = new ArrayList<>();
        try {
            File arq = new File(CAMINHO_ARQUIVO);
            if (arq.exists()) {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(CAMINHO_ARQUIVO));
                lista = (ArrayList<ListaCompras>) ois.readObject();
                ois.close();
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao ler: " + e.getMessage());
        }
        return lista;
    }

    public static int gerarId() {
        ArrayList<ListaCompras> lista = lerLista();
        int maiorId = 0;
        for (ListaCompras lc : lista) {
            if (lc.getId() > maiorId) maiorId = lc.getId();
        }
        return maiorId + 1;
    }

    public static void adicionar(ListaCompras lc) {
        ArrayList<ListaCompras> lista = lerLista();
        lista.add(lc);
        salvarLista(lista);
    }

    public static void atualizar(ListaCompras atualizado) {
        ArrayList<ListaCompras> lista = lerLista();
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getId() == atualizado.getId()) {
                lista.set(i, atualizado);
                break;
            }
        }
        salvarLista(lista);
    }

    public static void deletar(int id) {
        ArrayList<ListaCompras> lista = lerLista();
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getId() == id) {
                lista.remove(i);
                break;
            }
        }
        salvarLista(lista);
    }
}