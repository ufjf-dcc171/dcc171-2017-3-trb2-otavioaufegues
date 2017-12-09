package dao;

import main.ItemComanda;
import java.util.List;

public interface ItemComandaDAO {
    List<ItemComanda> listaTodos() throws Exception;
    List<ItemComanda> listaItemsComanda(Long numComanda) throws Exception;
    void exclui(ItemComanda itemComanda) throws Exception;
    void atualiza(ItemComanda itemComanda) throws Exception;
    void cria(ItemComanda itemComanda) throws Exception;
}
