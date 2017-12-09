/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import dao.Dao;
import dao.ItemComandaDAO;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author Otávio
 */
public class ItemTableModel extends AbstractTableModel {

    private List<ItemComanda> itensComanda;
    private List<ItemCardapio> cardapio;
    private final Comanda comanda;
    private final ItemComandaDAO itemComandaDAO;

    public ItemTableModel(Comanda comanda, List<ItemCardapio> cardapio) {
        this.comanda = comanda;
        this.cardapio = cardapio;
        itensComanda = comanda.getItensComanda();
        itemComandaDAO = Dao.getItemComandaDAO();
        try {
            itensComanda = itemComandaDAO.listaItemsComanda(comanda.getId());
        } catch (Exception ex) {
            Logger.getLogger(ItemTableModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "Codigo";
            case 1:
                return "Nome";
            case 2:
                return "Preco Unitario";
            case 3:
                return "Quantidade";
            case 4:
                return "Preco Total";
            default:
                return "";
        }
    }

    @Override
    public int getRowCount() {
        try {
            itensComanda = itemComandaDAO.listaItemsComanda(comanda.getId());
            return itensComanda.size();
        } catch (Exception ex) {
            return 0;
        }

    }

    @Override
    public int getColumnCount() {
        return 5;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        try {
            int codigoItem = itensComanda.get(rowIndex).getCodigoItem();
            ItemComanda itemp = getItemComanda(codigoItem);
            ItemCardapio itemc = getItemCardapio(codigoItem);
            switch (columnIndex) {
                case 0:
                    return codigoItem;
                case 1:
                    return itemc.getNome();
                case 2:
                    return itemc.getValorUnitario();
                case 3:
                    return itemp.getQuantidade();
                case 4:
                    return itemc.getValorUnitario() * itemp.getQuantidade();
                default:
                    return "";
            }
        } catch (Exception ex) {
            System.out.println("Erro ao buscar ITENSCOMANDA");
            return "";
        }
    }

    public ItemComanda getItemComanda(int codigo) {
        for (ItemComanda i : itensComanda) {
            if (i.getCodigoItem() == codigo) {
                return i;
            }
        }
        return null;
    }

    public ItemCardapio getItemCardapio(int codigo) {
        for (ItemCardapio i : cardapio) {
            if (i.getCodigo() == codigo) {
                return i;
            }
        }
        return null;
    }

    public ItemComanda getItemComandaPorID(Long id) {
        for (ItemComanda i : itensComanda) {
            if (i.getIdComanda().compareTo(id) == 0) {
                return i;
            }
        }
        return null;
    }

    public ItemComanda getItemComandaPorRow(int row) {
        return itensComanda.get(row);

    }

}
