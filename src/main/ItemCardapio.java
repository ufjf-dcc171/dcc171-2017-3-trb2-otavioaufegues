/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.io.Serializable;

/**
 *
 * @author Otávio
 */
public class ItemCardapio implements Serializable {

    private Long codigo;
    private String nome;
    private double valorUnitario;
    
    public ItemCardapio(int codigo, String nome, double valorUnitario) {
        this.codigo = new Long(codigo);
        this.nome = nome;
        this.valorUnitario = valorUnitario;
    }

    public ItemCardapio() {
    }

     public int getCodigo() {
        return codigo.intValue();
    }
      
    public void setCodigo(int codigo) {
        this.codigo = new Long(codigo);
    }
    
    public Long getId(){
        return codigo;
    }
    
    public void setId(Long id){
        this.codigo = id;
    }
    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(double valorUnitario) {
        this.valorUnitario = valorUnitario;
    }
    
    @Override
    public String toString(){
        return "["+codigo+"] "+nome+" (R$"+valorUnitario+")";
    }
    
    
}
