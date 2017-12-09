package dao;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import main.Mesa;

public class MesaDAOArquivo implements MesaDAO{

    private final SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    private static MesaDAOArquivo instancia;

    public static MesaDAOArquivo getInstancia() {
        if (instancia == null) {
            instancia = new MesaDAOArquivo();
        }
        return instancia;
    }
    
    @Override
    public List<Mesa> listaTodos() throws Exception {
        List<Mesa> mesas = new ArrayList<>();
        try {
            Scanner input = new Scanner(Paths.get("src/db/mesas.txt"));
            while (input.hasNext()) {
                Mesa m = new Mesa();
                m.setNumero(Integer.parseInt(input.nextLine()));
                m.setAberto(Integer.parseInt(input.nextLine())>0);
                try {
                    String horaAbertura = input.nextLine();
                    String horaFechamento = input.nextLine();
                    if(horaAbertura.equals("-"))
                        m.setHoraAbertura(null);
                    else
                            m.setHoraAbertura(new SimpleDateFormat("yyyy-MM-dd' 'HH:mm:ss").parse(horaAbertura));
                    
                    if(horaFechamento.equals("-"))
                        m.setHoraFechamento(null);
                    else
                            m.setHoraFechamento(new SimpleDateFormat("yyyy-MM-dd' 'HH:mm:ss").parse(horaFechamento));
                } catch (ParseException ex) {
                    System.out.println("Falha no parse de Datas");
                }
                //System.out.println("\nMesa "+m.getNumero()+", Aberto: "+(m.isAberto()?"true":"false"));
                //System.out.println("Hora Abertura:"+(m.getHoraAbertura()==null?"-":formater.format(m.getHoraAbertura())));
                //System.out.println("Hora Fechamento:"+(m.getHoraFechamento()==null?"-":formater.format(m.getHoraFechamento())));
                mesas.add(m);
                input.nextLine();
            }
            
        } catch (IOException | NumberFormatException ex) {
            System.out.println("fail");
        }
        return mesas;
    }

    @Override
    public void atualiza(Mesa mesa) throws Exception {
       List<Mesa> mesas = listaTodos();
       for(Mesa m : mesas){
           if(m.getNumero() == mesa.getNumero()){
               m.setAberto(mesa.isAberto());
               m.setHoraAbertura(mesa.getHoraAbertura());
               m.setHoraFechamento(mesa.getHoraFechamento());
           }
       }
       String dados = "";
       for(Mesa m : mesas){
            dados += String.valueOf(m.getNumero()) + '\n';
            dados += (m.isAberto()?"1":"0") + '\n';
            dados += (m.getHoraAbertura()==null?"-":formater.format(m.getHoraAbertura())) + '\n';
            dados += (m.getHoraFechamento()==null?"-":formater.format(m.getHoraFechamento())) + '\n' + '\n';
       }
       
       FileOutputStream out = new FileOutputStream("src/db/mesas.txt");
       out.write(dados.getBytes());

    }

    @Override
    public void cria(Mesa mesa) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
