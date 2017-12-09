/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import main.TRB2;

/**
 *
 * @author Otávio
 */
public class Dao {

    public static MesaDAO getMesaDAO() {
        return MesaDAOArquivo.getInstancia();
    }

    public static ComandaDAO getComandaDAO() {
        return ComandaDAOArquivo.getInstancia();
    }

    public static ItemComandaDAO getItemComandaDAO() {
        return ItemComandaDAOArquivo.getInstancia();
    }

    public static ItemCardapioDAO getItemCardapioDAO() {
        return ItemCardapioDAOArquivo.getInstancia();
    }

}
