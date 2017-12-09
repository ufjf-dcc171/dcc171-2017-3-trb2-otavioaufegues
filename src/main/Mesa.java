/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

/**
 *
 * @author Otávio
 */
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Mesa implements Serializable{

    private Long numero;
    private boolean aberto = false;
    private Date horaAbertura = null;
    private Date horaFechamento = null;

    public Mesa(int numero) {
        this.numero = new Long(numero);
    }

    public Mesa() {
        
    }

    public Long getId() {
        return numero;
    }

    public void setId(Long id) {
        this.numero = id;
    }
    public int getNumero() {
        return numero.intValue();
    }

    public void setNumero(int numero) {
        this.numero = new Long(numero);
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

    public boolean isAberto() {
        return aberto;
    }

    public void setAberto(boolean aberto) {
        this.aberto = aberto;
    }

    
    @Override
    public String toString() {
        return "Mesa "+this.numero+" "+(aberto ? "[Aberto]" : "[Fechado]");
    }
    
}
