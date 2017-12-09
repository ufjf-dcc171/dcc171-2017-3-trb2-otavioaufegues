package dao;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths; 
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.Comanda;

public class ComandaDAOArquivo implements ComandaDAO {

    private final SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    private static ComandaDAOArquivo instancia = null;
    private static int codigoComandaGerador = 0;

    public static ComandaDAOArquivo getInstancia() {
        if (instancia == null) {
            instancia = new ComandaDAOArquivo();
        }
        return instancia;
    }

    @Override
    public List<Comanda> listaTodos() throws Exception {

        List<Comanda> comandas = new ArrayList<>();
        try {
            Scanner input = new Scanner(Paths.get("src/db/comandas.txt"));
            while (input.hasNext()) {
                Comanda c = new Comanda();
                String[] dados = input.nextLine().split("-");
                c.setMesa(Integer.parseInt(dados[0]));
                c.setId(Long.parseLong(dados[1]));
                if (c.getId() > codigoComandaGerador) {
                    codigoComandaGerador = c.getId().intValue();
                }
                c.setAberto(Integer.parseInt(input.nextLine()) > 0);
                try {
                    String horaAbertura = input.nextLine();
                    String horaFechamento = input.nextLine();
                    if (horaAbertura.equals("-")) {
                        c.setHoraAbertura(null);
                    } else {
                        c.setHoraAbertura(new SimpleDateFormat("yyyy-MM-dd' 'HH:mm:ss").parse(horaAbertura));
                    }

                    if (horaFechamento.equals("-")) {
                        c.setHoraFechamento(null);
                    } else {
                        c.setHoraFechamento(new SimpleDateFormat("yyyy-MM-dd' 'HH:mm:ss").parse(horaFechamento));
                    }
                } catch (ParseException ex) {
                    System.out.println("Erro no listaTodosMesa(1)");
                }

                comandas.add(c);
                input.nextLine();
            }

        } catch (IOException | NumberFormatException ex) {
            System.out.println("Erro no listaTodosMesa(2)");
        }
        return comandas;

    }

    public List<Comanda> listaComandasAbertasNaMesa(int NumMesa) throws Exception {
        List<Comanda> comandas = listaTodos();
        List<Comanda> comandasAbertasNaMesa = new ArrayList();
        for (Comanda c : comandas) {
            if (c.getMesa() == NumMesa && c.isAberto()) {
                comandasAbertasNaMesa.add(c);
            }
        }
        return comandasAbertasNaMesa;
    }

    @Override
    public List<Comanda> listaComandasNaMesa(int NumMesa) throws Exception {
        List<Comanda> comandas = listaTodos();
        List<Comanda> comandasNaMesa = new ArrayList();
        for (Comanda c : comandas) {
            if (c.getMesa() == NumMesa) {
                comandasNaMesa.add(c);
            }
        }
        return comandasNaMesa;
    }

    @Override
    public Comanda buscaPorId(Long id) throws Exception {
        List<Comanda> comandas = listaTodos();
        for (Comanda c : comandas) {
            if (c.getId().equals(id)) {
                return c;
            }
        }
        return null;

    }

    @Override
    public void exclui(Comanda comanda) throws Exception {
        List<Comanda> comandas = listaTodos();
        Comanda excluir = null;
        for (Comanda c : comandas) {
            if (c.getId().equals(comanda.getId())) {
                excluir = c;
            }
        }
        comandas.remove(excluir);
        salvaDados(comandas);

    }

    @Override
    public void atualiza(Comanda comanda) throws Exception {
        List<Comanda> comandas = listaTodos();
        for (Comanda c : comandas) {
            if (c.getId().equals(comanda.getId())) {
                c.setAberto(comanda.isAberto());
                c.setHoraAbertura(comanda.getHoraAbertura());
                c.setHoraFechamento(comanda.getHoraFechamento());
                c.setMesa(comanda.getMesa());
            }
        }
        salvaDados(comandas);

    }

    @Override
    public void cria(Comanda comanda) throws Exception {
        List<Comanda> comandas = listaTodos();
        comanda.setId(new Long(++codigoComandaGerador));
        comandas.add(comanda);
        salvaDados(comandas);

    }

    public void salvaDados(List<Comanda> comandas) {
        String dados = "";
        for (Comanda c : comandas) {
            dados += String.valueOf(c.getMesa()) + "-" + String.valueOf(c.getId()) + '\n';
            dados += (c.isAberto() ? "1" : "0") + '\n';
            String strAbertura = c.getHoraAbertura() == null ? "-" : formater.format(c.getHoraAbertura());
            String strFechamento = c.getHoraFechamento() == null ? "-" : formater.format(c.getHoraFechamento());
            dados += strAbertura + '\n';
            dados += strFechamento + '\n' + '\n';
        }

        try {
            FileOutputStream out = new FileOutputStream("src/db/comandas.txt");
            out.write(dados.getBytes());
        } catch (IOException ex) {
            Logger.getLogger(ComandaDAOArquivo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
