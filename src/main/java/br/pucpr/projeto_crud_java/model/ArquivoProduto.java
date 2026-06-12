package br.pucpr.projeto_crud_java.model;
import java.io.*;
import java.util.ArrayList;

public class ArquivoProduto {

    private static final String CAMINHO_ARQUIVO = "produtos.dat";

    // Salva a lista inteira no arquivo
    public static void salvarLista(ArrayList<Produto> produtos) {
        try {
            File arq = new File(CAMINHO_ARQUIVO);
            if (!arq.exists()) {
                arq.createNewFile();
            }
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(arq));
            oos.writeObject(produtos);
            oos.close();
            System.out.println("Lista de produtos salva com sucesso.");
        } catch (FileNotFoundException e) {
            System.err.println("Erro ao salvar lista: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Erro ao salvar lista: " + e.getMessage());
        }
    }

    // Lê a lista inteira do arquivo
    public static ArrayList<Produto> lerLista() {
        ArrayList<Produto> lista = new ArrayList<>();
        try {
            File arq = new File(CAMINHO_ARQUIVO);
            if (arq.exists()) {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(CAMINHO_ARQUIVO));
                lista = (ArrayList<Produto>) ois.readObject();
                ois.close();
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao ler lista: " + e.getMessage());
        }
        return lista;
    }

    // Gera um novo ID único (maior ID existente + 1)
    public static int gerarId() {
        ArrayList<Produto> lista = lerLista();
        int maiorId = 0;
        for (Produto p : lista) {
            if (p.getId() > maiorId) {
                maiorId = p.getId();
            }
        }
        return maiorId + 1;
    }

    // Adiciona um novo produto à lista e grava
    public static void adicionarProduto(Produto novoProduto) {
        ArrayList<Produto> lista = lerLista();
        lista.add(novoProduto);
        salvarLista(lista);
    }

    // Atualiza um produto existente pelo ID
    public static void atualizarProduto(Produto atualizado) {
        ArrayList<Produto> lista = lerLista();
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getId() == atualizado.getId()) {
                lista.set(i, atualizado);
                break;
            }
        }
        salvarLista(lista);
    }

    // Remove um produto pelo ID
    public static void deletarProduto(int id) {
        ArrayList<Produto> lista = lerLista();
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getId() == id) {
                lista.remove(i);
                break;
            }
        }
        salvarLista(lista);
    }

    // Busca um produto pelo ID (retorna null se não encontrar)
    public static Produto buscarPorId(int id) {
        ArrayList<Produto> lista = lerLista();
        for (Produto p : lista) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }
}
