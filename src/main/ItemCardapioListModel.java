/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.util.ArrayList;
import java.util.List;
import javax.swing.ListModel;
import javax.swing.event.ListDataListener;

/**
 *
 * @author Otávio
 */
public class ItemCardapioListModel implements ListModel<ItemCardapio> {

    private final List<ItemCardapio> ItensCardapio;
    private final List<ListDataListener> dataListeners;

    public ItemCardapioListModel(List<ItemCardapio> ItensCardapio) {
        this.ItensCardapio = ItensCardapio;
        this.dataListeners = new ArrayList<>();
    }

    @Override
    public int getSize() {
        return ItensCardapio.size();
    }

    @Override
    public ItemCardapio getElementAt(int index) {
        return ItensCardapio.get(index);
    }

    @Override
    public void addListDataListener(ListDataListener l) {
        this.dataListeners.add(l);
    }

    @Override
    public void removeListDataListener(ListDataListener l) {
        this.dataListeners.remove(l);
    }

}
