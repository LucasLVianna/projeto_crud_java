package br.pucpr.projeto_crud_java.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class ArquivoMetaConsumo {

    private static final String CAMINHO_ARQUIVO = "metas_consumo.dat";

    public static void salvarLista(ArrayList<MetaConsumo> lista) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(CAMINHO_ARQUIVO));
            oos.writeObject(lista);
            oos.close();
        } catch (IOException e) {
            System.err.println("Erro ao salvar: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public static ArrayList<MetaConsumo> lerLista() {
        ArrayList<MetaConsumo> lista = new ArrayList<>();
        try {
            File arq = new File(CAMINHO_ARQUIVO);
            if (arq.exists()) {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(CAMINHO_ARQUIVO));
                lista = (ArrayList<MetaConsumo>) ois.readObject();
                ois.close();
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao ler: " + e.getMessage());
        }
        return lista;
    }

    public static int gerarId() {
        ArrayList<MetaConsumo> lista = lerLista();
        int maiorId = 0;
        for (MetaConsumo meta : lista) {
            if (meta.getId() > maiorId) {
                maiorId = meta.getId();
            }
        }
        return maiorId + 1;
    }

    public static void adicionar(MetaConsumo meta) {
        ArrayList<MetaConsumo> lista = lerLista();
        lista.add(meta);
        salvarLista(lista);
    }

    public static void atualizar(MetaConsumo atualizada) {
        ArrayList<MetaConsumo> lista = lerLista();
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getId() == atualizada.getId()) {
                lista.set(i, atualizada);
                break;
            }
        }
        salvarLista(lista);
    }

    public static void deletar(int id) {
        ArrayList<MetaConsumo> lista = lerLista();
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getId() == id) {
                lista.remove(i);
                break;
            }
        }
        salvarLista(lista);
    }
}
