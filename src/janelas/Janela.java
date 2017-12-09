/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package janelas;

import dao.ComandaDAO;
import dao.Dao;
import dao.ItemCardapioDAO;
import dao.MesaDAO;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import main.Comanda;
import main.ComandasListModel;
import main.ItemCardapio;
import main.Mesa;
import main.MesaListModel;

/**
 *
 * @author Otávio
 */
public class Janela extends JFrame {

    private List<Mesa> mesas;
    private List<ItemCardapio> cardapio;
    private MesaDAO mesaDAO;
    private ComandaDAO comandaDAO;
    private ItemCardapioDAO itemCardapioDAO;

    private final JList<Comanda> ItensCardapio = new JList<>(new DefaultListModel<>());
    private final JList<Mesa> listaMesas = new JList<>(new DefaultListModel<>());
    private final JList<Comanda> listaComandas = new JList<>(new DefaultListModel<>());
    private final JButton btnAbrirMesa = new JButton("Abrir Mesa");
    private final JButton btnFecharMesa = new JButton("Fechar Mesa");
    private final JButton btnAbrirComanda = new JButton("Abrir Comanda");
    private final JButton btnFecharComanda = new JButton("Fechar Comanda");
    private final JButton btnAcessarComanda = new JButton("Fazer Pedido");
    private final JButton btnExcluirComanda = new JButton("Excluir Comanda");

    public Janela() {
        super("Restaurante");

        carregarDadosIniciais();

        listaMesas.setModel(new MesaListModel(mesas));
        setLayout(new GridLayout(2, 2, 10, 10));

        add(new JScrollPane(listaMesas));
        add(new JScrollPane(listaComandas));

        JPanel botoesMesa = new JPanel(new GridLayout(2, 2));
        JPanel botoesComandas = new JPanel(new GridLayout(2, 2));
        add(botoesMesa);
        botoesMesa.add(btnAbrirMesa);
        botoesMesa.add(btnFecharMesa);

        add(botoesComandas);
        botoesComandas.add(btnAbrirComanda);
        botoesComandas.add(btnFecharComanda);
        botoesComandas.add(btnAcessarComanda);

        btnAbrirComanda.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAbrirComandaActionPerformed(evt);
            }
        });

        btnAcessarComanda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAcessarComandaActionPerformed(evt);
            }
        });

        btnFecharComanda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFecharComandaActionPerformed(evt);
            }
        });

        btnExcluirComanda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirComandaActionPerformed(evt);
            }
        });


        btnAbrirMesa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAbrirMesaActionPerformed(evt);
            }
        });

        btnFecharMesa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFecharMesaActionPerformed(evt);
            }
        });

        listaMesas.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                listaMesasValueChanged(evt);
            }
        });

        listaComandas.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                listaComandasValueChanged(evt);
            }
        });
    }

    private void carregarDadosIniciais() {
        ((DefaultListModel<Mesa>) listaMesas.getModel()).clear();
        ((DefaultListModel<Comanda>) listaComandas.getModel()).clear();

        instanciaDAOs();
        inicializarMesas();
        inicializartensCardapio();
    }

    private void instanciaDAOs() {
        comandaDAO = Dao.getComandaDAO();
        mesaDAO = Dao.getMesaDAO();
        itemCardapioDAO = Dao.getItemCardapioDAO();
    }

    private void inicializarMesas() {
        try {

            DefaultListModel<Mesa> modelo = (DefaultListModel<Mesa>) listaMesas.getModel();
            mesas = mesaDAO.listaTodos();
            for (Mesa m : mesas) {
                modelo.addElement(m);
            }

        } catch (Exception ex) {
            Logger.getLogger(Janela.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Erro no metodo 'inicializarMesas()'");
        }
    }

    private void inicializartensCardapio() {
        try {
            cardapio = itemCardapioDAO.listaTodos();
        } catch (Exception ex) {
            Logger.getLogger(Janela.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Erro no metodo 'inicializartensCardapio()'");
        }

    }

    private void abreMesa() throws Exception {
        Mesa selectedMesa = (Mesa) listaMesas.getSelectedValue();
        selectedMesa.setAberto(true);
        selectedMesa.setHoraAbertura(new Date());
        mesaDAO.atualiza(selectedMesa);
    }

    private void fechaMesa() throws Exception {
        Mesa selectedMesa = (Mesa) listaMesas.getSelectedValue();
        selectedMesa.setAberto(false);
        selectedMesa.setHoraFechamento(new Date());
        List<Comanda> comandas = comandaDAO.listaComandasNaMesa(selectedMesa.getNumero());
        System.out.println("ooi");
        for (Comanda comanda : comandas) {
            comanda.setAberto(false);
            comanda.setHoraFechamento(new Date());
            comandaDAO.atualiza(comanda);
        }

        abreTelaFechamento("mesa",selectedMesa, comandas);

        selectedMesa.setHoraAbertura(null);
        selectedMesa.setHoraFechamento(null);
        mesaDAO.atualiza(selectedMesa);
        listaMesas.repaint();

        selectedMesa.setHoraAbertura(null);
        selectedMesa.setHoraFechamento(null);
        exibirComandas(selectedMesa.getNumero());
    }

    private void criaNovaComanda() throws Exception {
        DefaultListModel<Comanda> modelo = (DefaultListModel<Comanda>) listaComandas.getModel();
        Mesa selectedMesa = (Mesa) listaMesas.getSelectedValue();

        Comanda novaComanda = new Comanda(selectedMesa.getNumero());
        comandaDAO.cria(novaComanda);

        exibirComandas(selectedMesa.getNumero());
    }

    private void acessaComanda() throws Exception {
        Comanda comanda = (Comanda) listaComandas.getSelectedValue();

        JanelaComanda janelaComanda = new JanelaComanda(comanda, cardapio);
        janelaComanda.setSize(750, 300);
        janelaComanda.setLocationRelativeTo(null);
        janelaComanda.setVisible(true);
        janelaComanda.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void fechaComanda() throws Exception {
        Comanda comanda = (Comanda) listaComandas.getSelectedValue();
        Mesa selectedMesa = (Mesa) listaMesas.getSelectedValue();
        comanda.setAberto(false);
        comanda.setHoraFechamento(new Date());
        comandaDAO.atualiza(comanda);
        abreTelaFechamento("comanda",selectedMesa, Arrays.asList(comanda));
        exibirComandas(selectedMesa.getNumero());
    }

    private void excluiComanda() throws Exception {
        if (!listaComandas.isSelectionEmpty()) {
            Comanda comanda = (Comanda) listaComandas.getSelectedValue();
            if (comanda.isAberto()) {
                Mesa m = (Mesa) listaMesas.getSelectedValue();
                comandaDAO.exclui(comanda);
                exibirComandas(m.getNumero());
            }
        }
    }

    private void abreTelaFechamento(String janela,Mesa mesa, List<Comanda> comandas) {
        JanelaFechamento janelaFechamento = new JanelaFechamento(janela ,mesa, comandas, cardapio);
        janelaFechamento.setSize(500, 500);
        janelaFechamento.setLocationRelativeTo(null);
        janelaFechamento.setVisible(true);
        janelaFechamento.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void exibirComandas(int numeroMesa) {
        try {
            DefaultListModel<Comanda> modelo = (DefaultListModel<Comanda>) listaComandas.getModel();
            modelo.clear();
            List<Comanda> listagemComandas = comandaDAO.listaComandasAbertasNaMesa(numeroMesa);
            for (Comanda comanda : listagemComandas) {
                modelo.addElement(comanda);
            }
        } catch (Exception ex) {
            System.out.println("Falha ao buscar Comandas no Banco de dados");
        }

    }

    private String getTime() {
        Calendar c = Calendar.getInstance();
        return c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE) + ":" + c.get(Calendar.SECOND);
    }

    private void btnAbrirMesaActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            abreMesa();
        } catch (Exception ex) {
            System.out.println("Erro no metodo 'abreMesa()'");
            return;
        }

        btnAbrirComanda.setEnabled(true);
        btnFecharMesa.setEnabled(true);
        btnAbrirMesa.setEnabled(false);
        listaMesas.repaint();
    }

    private void btnFecharMesaActionPerformed(java.awt.event.ActionEvent evt) {

        try {
            fechaMesa();
        } catch (Exception ex) {
            System.out.println("Erro no metodo 'fechaMesa()'");
            return;
        }

        btnAbrirComanda.setEnabled(false);
        btnFecharMesa.setEnabled(false);
        btnAbrirMesa.setEnabled(true);
    }

    private void btnAbrirComandaActionPerformed(java.awt.event.ActionEvent evt) {

        try {
            criaNovaComanda();
        } catch (Exception ex) {
            System.out.println("Erro no metodo 'criaNovaComanda()'");
        }

    }

    private void btnAcessarComandaActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            acessaComanda();
        } catch (Exception ex) {
            System.out.println("Erro no metodo 'acessaComanda()'");
        }
    }

    private void listaMesasValueChanged(javax.swing.event.ListSelectionEvent evt) {
        if (!listaMesas.isSelectionEmpty()) {

            Mesa selectedMesa = (Mesa) listaMesas.getSelectedValue();
            if (selectedMesa.isAberto()) {
                btnAbrirComanda.setEnabled(true);
                btnFecharMesa.setEnabled(true);
                btnAbrirMesa.setEnabled(false);
            } else {
                btnAbrirComanda.setEnabled(false);
                btnFecharMesa.setEnabled(false);
                btnAbrirMesa.setEnabled(true);
            }
            exibirComandas(selectedMesa.getNumero());
        }

    }

    private void listaComandasValueChanged(javax.swing.event.ListSelectionEvent evt) {
        if (!listaComandas.isSelectionEmpty()) {
            Comanda comanda = (Comanda) listaComandas.getSelectedValue();
            btnAcessarComanda.setEnabled(true);
            if (comanda.isAberto()) {
                btnFecharComanda.setEnabled(true);
                btnExcluirComanda.setEnabled(true);
            } else {
                btnFecharComanda.setEnabled(false);
                btnExcluirComanda.setEnabled(false);
            }
        } else {
            btnAcessarComanda.setEnabled(false);
            btnFecharComanda.setEnabled(false);
            btnExcluirComanda.setEnabled(false);
        }
    }

    private void btnFecharComandaActionPerformed(java.awt.event.ActionEvent evt) {
        if (!listaComandas.isSelectionEmpty()) {

            try {
                fechaComanda();
            } catch (Exception ex) {
                System.out.println("Erro no metodo 'fechaComanda()'");
                return;
            }

            btnFecharComanda.setEnabled(false);
            btnExcluirComanda.setEnabled(false);

        }
    }

    private void btnExcluirComandaActionPerformed(java.awt.event.ActionEvent evt) {

        try {
            excluiComanda();
        } catch (Exception ex) {
            System.out.println("Erro no metodo 'excluiComanda()'");
        }

    }

}
