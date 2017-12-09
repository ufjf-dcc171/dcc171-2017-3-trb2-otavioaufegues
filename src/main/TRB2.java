/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import janelas.Janela;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;

/**
 *
 * @author Otávio
 */
public class TRB2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Janela janela = new Janela();
        janela.setSize(500, 300);
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setLocationRelativeTo(null);
        janela.setVisible(true);
    }
    
//    private static List<Mesa> getSampleData() {
////        Comanda comanda1 = new Comanda(1, "1234");
//
//        List<Mesa> mesas = new ArrayList<Mesa>() {
//            {
//                Mesa m1 = new Mesa(1);
//                m1.setComandas(new ArrayList<Comanda>());
//                add(m1);
//                Mesa m2 = new Mesa(2);
//                m2.setComandas(new ArrayList<Comanda>());
//                add(m2);
//                Mesa m3 = new Mesa(3);
//                m3.setComandas(new ArrayList<Comanda>());
//                add(m3);
//                Mesa m4 = new Mesa(4);
//                m4.setComandas(new ArrayList<Comanda>());
//                add(m4);
//            }
//        };
//
//        return mesas;
//    }

}
