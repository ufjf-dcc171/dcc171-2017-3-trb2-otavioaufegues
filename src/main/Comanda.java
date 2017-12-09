package main;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Comanda implements Serializable {

    private int mesa;
    private Long id;
    private boolean aberto;
    private Date horaAbertura = new Date();
    private Date horaFechamento = null;
    ArrayList<ItemComanda> itensComanda = new ArrayList<>();

    public Comanda() {
    }

    public Comanda(int mesa) {
        this.mesa = mesa;
        this.aberto = true;
    }

    public boolean isAberto() {
        return aberto;
    }

    public void setAberto(boolean aberto) {
        this.aberto = aberto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ArrayList<ItemComanda> getItensComanda() {
        return itensComanda;
    }

    public int getMesa() {
        return mesa;
    }

    public void setMesa(int mesa) {
        this.mesa = mesa;
    }

    public Date getHoraAbertura() {
        return horaAbertura;
    }

    public void setHoraAbertura(Date horaAbertura) {
        this.horaAbertura = horaAbertura;
    }

    public Date getHoraFechamento() {
        return horaFechamento;
    }

    public void setHoraFechamento(Date horaFechamento) {
        this.horaFechamento = horaFechamento;
    }

    @Override
    public String toString() {
        return "Comanda " + getId() + " " + (aberto ? "[Aberto]" : "[Fechado]");
    }


    public void acrescentarItem(int codigo, int quantidade) {
        itensComanda.add(new ItemComanda(codigo, quantidade));
    }

}
