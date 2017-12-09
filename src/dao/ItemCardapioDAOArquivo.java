package dao;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import main.ItemCardapio;

public class ItemCardapioDAOArquivo implements ItemCardapioDAO {

    private static ItemCardapioDAOArquivo instancia = null;

    public static ItemCardapioDAOArquivo getInstancia() {
        if (instancia == null) {
            instancia = new ItemCardapioDAOArquivo();
        }
        return instancia;
    }

    @Override
    public List<ItemCardapio> listaTodos() throws Exception {
        List<ItemCardapio> itens = new ArrayList();
        try {
            Scanner input = new Scanner(Paths.get("src/db/itenscardapio.txt"));
            String line;

            while (input.hasNext()) {

                String idItem = input.nextLine();
                String nome = input.nextLine();
                double preco = input.nextDouble();
                ItemCardapio item = new ItemCardapio(Integer.parseInt(idItem), nome, preco);
                itens.add(item);
                input.nextLine();
                input.nextLine();

            }

        } catch (IOException | NumberFormatException ex) {
            System.out.println("Erro no listaTodos");
        }

        return itens;
    }

    @Override
    public ItemCardapio buscaPorId(Long id) throws Exception {
        List<ItemCardapio> itens = listaTodos();
        for (ItemCardapio i : itens) {
            if (i.getId().equals(id)) {
                return i;
            }
        }
        return null;
    }

    @Override
    public void cria(ItemCardapio itemCardapio) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
