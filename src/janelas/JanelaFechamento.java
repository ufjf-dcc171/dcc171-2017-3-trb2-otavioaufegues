/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package janelas;

import dao.ComandaDAO;
import dao.Dao;
import dao.ItemComandaDAO;
import java.awt.HeadlessException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import main.Comanda;
import main.ItemCardapio;
import main.ItemComanda;
import main.Mesa;

/**
 *
 * @author Otávio
 */
public class JanelaFechamento extends JFrame {

    private final SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    List<ItemCardapio> cardapio;
    JList<String> DetalhesFechamento;
    ComandaDAO comandaDAO = Dao.getComandaDAO();
    ItemComandaDAO itemComandaDAO = Dao.getItemComandaDAO();

    public JanelaFechamento(String janela, Mesa mesas, List<Comanda> comandas, List<ItemCardapio> cardapio) throws HeadlessException {
        super("Fechamento Mesa " + mesas.getNumero());
        this.cardapio = cardapio;
        if (janela.equals("mesa")) {
            imprimirDadosFechamentoMesa(mesas, comandas);
        } else {
            imprimirDadosFechamento(mesas, comandas);
        }
    }

    private void imprimirDadosFechamento(Mesa mesas, List<Comanda> comandas) {
        Box vertical = Box.createVerticalBox();
        vertical.add(new JLabel("Fechamento da mesa " + mesas.getNumero()));
        vertical.add(new JLabel(" "));
        vertical.add(new JLabel("Hora abertura: " + mesas.getHoraAbertura()));
        vertical.add(new JLabel("Hora fechamento: " + (mesas.getHoraFechamento() == null ? "-" : mesas.getHoraFechamento())));
        vertical.add(new JLabel(" "));
        vertical.add(new JLabel(" "));
        try {
            for (Comanda comanda : comandas) {
                vertical.add(new JLabel("Comanda #" + comanda.getId()));
                vertical.add(new JLabel("Hora Abertura:" + formater.format(comanda.getHoraAbertura())));
                vertical.add(new JLabel("Hora Fechamento:" + formater.format(comanda.getHoraFechamento())));
                vertical.add(new JLabel(" "));
                double valorTotal = 0;
                List<ItemComanda> items = itemComandaDAO.listaItemsComanda(comanda.getId());
                for (ItemComanda itemComanda : items) {
                    ItemCardapio itemCardapio = buscarItemCardapio(itemComanda.getCodigoItem());
                    vertical.add(new JLabel(itemCardapio.getNome() + ", Valor Unidade: R$" + itemCardapio.getValorUnitario()));
                    vertical.add(new JLabel("Qtd: " + itemComanda.getQuantidade() + " x R$" + itemCardapio.getValorUnitario() + " = R$" + itemComanda.getQuantidade() * itemCardapio.getValorUnitario()));
                    valorTotal += itemComanda.getQuantidade() * itemCardapio.getValorUnitario();
                    vertical.add(new JLabel(" "));
                }
                vertical.add(new JLabel(" "));
                vertical.add(new JLabel("Valor Total da Mesa: R$" + valorTotal));
                vertical.add(new JLabel(" "));
            }
            add(vertical);

        } catch (Exception ex) {
            Logger.getLogger(JanelaFechamento.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Erro no metodo 'imprimirDadosFechamento'.");
        }
    }

    private void imprimirDadosFechamentoMesa(Mesa mesas, List<Comanda> comandas) {
        Box vertical = Box.createVerticalBox();
        vertical.add(new JLabel("Fechamento da mesa " + mesas.getNumero()));
        vertical.add(new JLabel(" "));
        vertical.add(new JLabel("Hora abertura: " + mesas.getHoraAbertura()));
        vertical.add(new JLabel("Hora fechamento: " + (mesas.getHoraFechamento() == null ? "-" : mesas.getHoraFechamento())));
        vertical.add(new JLabel(" "));
        vertical.add(new JLabel(" "));
        try {
            double valorTotal = 0;
            for (Comanda comanda : comandas) {
                vertical.add(new JLabel("Comanda #" + comanda.getId()));
                vertical.add(new JLabel("Hora Abertura:" + formater.format(comanda.getHoraAbertura())));
                vertical.add(new JLabel("Hora Fechamento:" + formater.format(comanda.getHoraFechamento())));
                vertical.add(new JLabel(" "));

                List<ItemComanda> items = itemComandaDAO.listaItemsComanda(comanda.getId());
                for (ItemComanda itemComanda : items) {
                    ItemCardapio itemCardapio = buscarItemCardapio(itemComanda.getCodigoItem());
                    vertical.add(new JLabel(itemCardapio.getNome() + ", Valor Unidade: R$" + itemCardapio.getValorUnitario()));
                    vertical.add(new JLabel("Qtd: " + itemComanda.getQuantidade() + " x R$" + itemCardapio.getValorUnitario() + " = R$" + itemComanda.getQuantidade() * itemCardapio.getValorUnitario()));
                    valorTotal += itemComanda.getQuantidade() * itemCardapio.getValorUnitario();
                    vertical.add(new JLabel(" "));
                }

            }
            vertical.add(new JLabel(" "));
            vertical.add(new JLabel("Valor Total da Mesa: R$" + valorTotal));
            vertical.add(new JLabel(" "));
            add(vertical);

        } catch (Exception ex) {
            Logger.getLogger(JanelaFechamento.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Erro no metodo 'imprimirDadosFechamento'.");
        }
    }

    private ItemCardapio buscarItemCardapio(int codigoItem) {
        for (ItemCardapio ic : cardapio) {
            if (ic.getCodigo() == codigoItem) {
                return ic;
            }
        }
        return null;
    }
}
