/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package janelas;

import dao.Dao;
import dao.ItemComandaDAO;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import main.Comanda;
import main.ItemCardapio;
import main.ItemComanda;
import main.ItemTableModel;

/**
 *
 * @author Otávio
 */
public class JanelaComanda extends JFrame {

    private Comanda comanda;
    private List<ItemCardapio> cardapio;
    ItemComandaDAO itemComandaDAO = Dao.getItemComandaDAO();

    private ArrayList<ItemComanda> listaComanda;

    private JTable tabelaItens;
    private JComboBox itensCardapio = new JComboBox();

    private JButton btnAdicionar = new JButton("Adicionar Item");
    private JButton btnRemover = new JButton("Remover Item");

    private JTextField txtQuantidade = new JTextField(20);
    private JLabel labelQuantidade = new JLabel("Quantidade");

    private JLabel labelItem = new JLabel("Escolha um produto");
    private JLabel lblValorTotal = new JLabel();

    public JanelaComanda(Comanda comanda, List<ItemCardapio> cardapio) {
        super("Detalhes do Comanda " + comanda.getId());

        this.comanda = comanda;
        this.cardapio = cardapio;

        itensCardapio.setModel(new DefaultComboBoxModel(cardapio.toArray()));
        tabelaItens = new JTable(new ItemTableModel(comanda, cardapio));
        tabelaItens.setModel(new ItemTableModel(comanda, cardapio));
        tabelaItens.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tabelaItens.getSelectionModel().addListSelectionListener(new ItensComandaHandler());

        txtQuantidade.setText("1");

        JPanel formulario = new JPanel();
        JPanel tabela = new JPanel();
        setLayout(new GridLayout(2, 3));
        formulario.setLayout(new FlowLayout(FlowLayout.LEADING));
        formulario.add(btnAdicionar);
        formulario.add(btnRemover);
        formulario.add(itensCardapio);
        formulario.add(labelQuantidade);
        formulario.add(txtQuantidade);
        add(tabela);
        add(formulario);
        tabela.setLayout(new FlowLayout(FlowLayout.LEFT));
        tabela.add(new JScrollPane(tabelaItens));

        btnRemover.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoverActionPerformed(evt);
            }
        });


        btnAdicionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNovoItemActionPerformed(evt);
            }
        });
    }

    private void btnNovoItemActionPerformed(java.awt.event.ActionEvent evt) {
        // se a comanda esta fechada, impede cadastro ou edicao de item
        if (!comanda.isAberto()) {
            return;
        }
        // checa se o campo Quantidade eh numerico e maior que 0
        if (isNumeric(txtQuantidade.getText()) && Integer.parseInt(txtQuantidade.getText()) > 0) {

            // update de um dos items do comanda
            if (tabelaItens.getSelectedRow() >= 0) {
                int cod_item = tabelaItens.getSelectedRow();
                ItemComanda itemComanda = getItemComandaPorRow(cod_item);
                int novo_cod = ((ItemCardapio) itensCardapio.getSelectedItem()).getCodigo();
                itemComanda.setCodigoItem(novo_cod);
                int nova_quant = Integer.parseInt(txtQuantidade.getText());
                itemComanda.setQuantidade(nova_quant);
                try {
                    itemComandaDAO.atualiza(itemComanda);
                } catch (Exception ex) {
                    System.out.println("Erro ao atualizar registro");
                }
                ((AbstractTableModel) tabelaItens.getModel()).fireTableDataChanged();
                tabelaItens.setEnabled(true);
            } // novo item sendo acrescentado ao comanda
            else {
                ItemCardapio i = (ItemCardapio) itensCardapio.getSelectedItem();
                try {
                    itemComandaDAO.cria(new ItemComanda(comanda.getId(), i.getCodigo(), Integer.parseInt(txtQuantidade.getText())));
                } catch (Exception ex) {
                    System.out.println("Falha ao criar novo registro ITEMPEDIDO");
                }
                ((AbstractTableModel) tabelaItens.getModel()).fireTableDataChanged();
                tabelaItens.setEnabled(true);
            }
            atualizarValorTotal();

        } else {
            txtQuantidade.grabFocus();
        }
    }

    private void btnRemoverActionPerformed(java.awt.event.ActionEvent evt) {
        if (!comanda.isAberto()) {
            return;
        }
        if (tabelaItens.getSelectedRow() >= 0) {
            System.out.println("oi");
            int item_deletar = tabelaItens.getSelectedRow();
            ItemComanda itemComanda = getItemComandaPorRow(item_deletar);
            try {
                itemComandaDAO.exclui(itemComanda);
            } catch (Exception ex) {
                Logger.getLogger(JanelaComanda.class.getName()).log(Level.SEVERE, null, ex);
            }
            ((AbstractTableModel) tabelaItens.getModel()).fireTableDataChanged();
            tabelaItens.setEnabled(true);
            atualizarValorTotal();
        }
    }

    public boolean isNumeric(String str) {
        try {
            int d = Integer.parseInt(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    private ItemCardapio getItemCardapio(int codigo) {
        return ((ItemTableModel) tabelaItens.getModel()).getItemCardapio(codigo);
    }

    private ItemComanda getItemComanda(int codigo) {
        return ((ItemTableModel) tabelaItens.getModel()).getItemComanda(codigo);
    }

    private ItemComanda getItemComandaPorRow(int row) {
        return ((ItemTableModel) tabelaItens.getModel()).getItemComandaPorRow(row);
    }

    private void atualizarValorTotal() {
        lblValorTotal.setText(String.valueOf(calcularValorTotal()));
    }

    private double calcularValorTotal() {
        double vt = 0;
        try {
            List<ItemComanda> items = itemComandaDAO.listaItemsComanda(comanda.getId());
            for (ItemComanda i : items) {
                double vu = getItemCardapio(i.getCodigoItem()).getValorUnitario();
                vt += i.getQuantidade() * vu;
            }
            return vt;
        } catch (Exception ex) {
            Logger.getLogger(JanelaComanda.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }

    }

    private class ItensComandaHandler implements ListSelectionListener {

        @Override
        public void valueChanged(ListSelectionEvent event) {
            if (tabelaItens.getSelectedRow() >= 0) {
                int cod_cardapio = (int) tabelaItens.getValueAt(tabelaItens.getSelectedRow(), 0);
                ItemCardapio itemCardapio = getItemCardapio(cod_cardapio);
                ItemComanda itemComanda = getItemComanda(cod_cardapio);
                itensCardapio.setSelectedItem(itemCardapio);
                txtQuantidade.setText(String.valueOf(itemComanda.getQuantidade()));
            }
        }
    }
}
