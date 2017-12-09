package dao;
import java.util.List;
import main.ItemCardapio;

public interface ItemCardapioDAO {
    List<ItemCardapio> listaTodos() throws Exception;
    ItemCardapio buscaPorId(Long id) throws Exception;
    void cria(ItemCardapio itemCardapio) throws Exception;
}
