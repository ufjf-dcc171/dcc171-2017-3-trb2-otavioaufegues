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
public class ComandasListModel implements ListModel<Comanda> {

    private final List<Comanda> comandas;
    private final List<ListDataListener> dataListeners;

    public ComandasListModel(List<Comanda> comandas) {
        this.comandas = comandas;
        this.dataListeners =  new ArrayList<>();
    }

    @Override
    public int getSize() {
        return comandas.size();
    }

    @Override
    public Comanda getElementAt(int index) {
        return comandas.get(index);
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
