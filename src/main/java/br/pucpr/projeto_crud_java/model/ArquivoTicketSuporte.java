package br.pucpr.projeto_crud_java.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class ArquivoTicketSuporte {

    private static final String CAMINHO_ARQUIVO = "tickets_suporte.dat";

    public static void salvarLista(ArrayList<TicketSuporte> lista) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(CAMINHO_ARQUIVO));
            oos.writeObject(lista);
            oos.close();
        } catch (IOException e) {
            System.err.println("Erro ao salvar: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public static ArrayList<TicketSuporte> lerLista() {
        ArrayList<TicketSuporte> lista = new ArrayList<>();
        try {
            File arq = new File(CAMINHO_ARQUIVO);
            if (arq.exists()) {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(CAMINHO_ARQUIVO));
                lista = (ArrayList<TicketSuporte>) ois.readObject();
                ois.close();
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao ler: " + e.getMessage());
        }
        return lista;
    }

    public static int gerarId() {
        ArrayList<TicketSuporte> lista = lerLista();
        int maiorId = 0;
        for (TicketSuporte ticket : lista) {
            if (ticket.getId() > maiorId) {
                maiorId = ticket.getId();
            }
        }
        return maiorId + 1;
    }

    public static void adicionar(TicketSuporte ticket) {
        ArrayList<TicketSuporte> lista = lerLista();
        lista.add(ticket);
        salvarLista(lista);
    }

    public static void atualizar(TicketSuporte atualizado) {
        ArrayList<TicketSuporte> lista = lerLista();
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getId() == atualizado.getId()) {
                lista.set(i, atualizado);
                break;
            }
        }
        salvarLista(lista);
    }

    public static void deletar(int id) {
        ArrayList<TicketSuporte> lista = lerLista();
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getId() == id) {
                lista.remove(i);
                break;
            }
        }
        salvarLista(lista);
    }
}
