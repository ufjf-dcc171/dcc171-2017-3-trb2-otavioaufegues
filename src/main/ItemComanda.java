package main;

import java.io.Serializable;

public class ItemComanda implements Serializable {

    private Long id;
    private Long idComanda;
    private int codigoItem;
    private int quantidade;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ItemComanda(int codigoItem, int quantidade) {
        this.codigoItem = codigoItem;
        this.quantidade = quantidade;
    }
    
    public ItemComanda(Long codigoComanda, int codigoItem, int quantidade) {
        this.idComanda = codigoComanda;
        this.codigoItem = codigoItem;
        this.quantidade = quantidade;
    }

    public ItemComanda() {}

    public int getCodigoItem() {
        return codigoItem;
    }

    public void setCodigoItem(int codigoItem) {
        this.codigoItem = codigoItem;
    }

    public Long getIdComanda() {
        return idComanda;
    }

    public void setIdComanda(Long idComanda) {
        this.idComanda = idComanda;
    }
    
    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    @Override
    public String toString() {
        return "ItemComanda{" + "id=" + id + ", codigoComanda=" + idComanda + ", codigoItem=" + codigoItem + ", quantidade=" + quantidade + '}';
    }

 


}
