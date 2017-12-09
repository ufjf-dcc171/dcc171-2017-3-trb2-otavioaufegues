package dao;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import main.ItemComanda;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.Comanda;
import main.TRB2;
import dao.Dao;
import dao.ComandaDAO;

public class ItemComandaDAOArquivo implements ItemComandaDAO {

    private static ItemComandaDAOArquivo instancia;
    private static int itemComandaGerador = 0;

    public static ItemComandaDAOArquivo getInstancia() {
        if (instancia == null) {
            instancia = new ItemComandaDAOArquivo();
        }
        return instancia;
    }
    
    @Override
    public List<ItemComanda> listaTodos() throws Exception {
        List<ItemComanda> items = new ArrayList<>();
        try {
            Scanner input = new Scanner(Paths.get("src/db/itenscomanda.txt"));
            String line;
            while (input.hasNext()) {

                Long idComanda = Long.valueOf(input.nextLine().split("-")[1]);
                
                while((line = input.nextLine()).isEmpty() == false )
                {
                    ItemComanda ip = new ItemComanda();
                    ip.setIdComanda(idComanda);
                    String[] dados = line.split("-");
                    ip.setId(new Long(dados[0]));
                    ip.setCodigoItem(Integer.parseInt(dados[1]));
                    ip.setQuantidade(Integer.parseInt(dados[2]));
                    items.add(ip);
                    if(ip.getId().intValue() > itemComandaGerador){
                        itemComandaGerador = ip.getId().intValue();
                    }

                }
            }
            
        } catch (IOException | NumberFormatException ex) {
            System.out.println("Erro no listaTodos(1)");
        }
        return items;
    }

    @Override
    public List<ItemComanda> listaItemsComanda(Long numComanda) throws Exception {
        List<ItemComanda> items = listaTodos();
        List<ItemComanda> itemsComanda = new ArrayList<>();
        for(ItemComanda itensComanda: items){
            if(itensComanda.getIdComanda().equals(numComanda)){
                itemsComanda.add(itensComanda);
            }
        }
     
        return itemsComanda;

    }

    @Override
    public void exclui(ItemComanda itemComanda) throws Exception {
        List<ItemComanda> itemsComanda = listaTodos();
        ItemComanda excluir = null;
        for(ItemComanda ip : itemsComanda){
            if(ip.getId().equals(itemComanda.getId())){
                excluir = ip;
                break;
            }
        }
        itemsComanda.remove(excluir);
        salvaDados(itemsComanda);
        
    }

    @Override
    public void atualiza(ItemComanda itemComanda) throws Exception {
        List<ItemComanda> itemsComanda = listaTodos();
        for(ItemComanda ip : itemsComanda){
            if(ip.getId().equals(itemComanda.getId())){
                ip.setCodigoItem(itemComanda.getCodigoItem());
                ip.setQuantidade(itemComanda.getQuantidade());
                ip.setIdComanda(itemComanda.getIdComanda());
                break;
            }
        }
        salvaDados(itemsComanda);
        
    }

    @Override
    public void cria(ItemComanda itemComanda) throws Exception {
        List<ItemComanda> itemsComanda = listaTodos();
        itemComanda.setId(new Long(++itemComandaGerador));
        itemsComanda.add(itemComanda);
        salvaDados(itemsComanda);
        
      
    }
    
    public void salvaDados(List<ItemComanda> itemsComanda){
        
        try {
            ComandaDAO dao = Dao.getComandaDAO();
            List<Comanda> comandas = dao.listaTodos();
            
            String dados = "";
            for(Comanda p : comandas){
                dados += String.valueOf(p.getMesa())+"-"+String.valueOf(p.getId()) + '\n';
                for(ItemComanda ip : itemsComanda){
                        if(ip.getIdComanda().equals(p.getId())){
                            dados += ip.getId()+"-"+ip.getCodigoItem()+"-"+ip.getQuantidade()  + '\n';
                        }
                        
                }
                dados += '\n';
            }
            
            FileOutputStream out = new FileOutputStream("src/db/itenscomanda.txt");
            out.write(dados.getBytes());
 
        } catch (Exception ex) {
            Logger.getLogger(ItemComandaDAOArquivo.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

}
