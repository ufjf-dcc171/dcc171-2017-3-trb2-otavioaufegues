package dao;

import java.util.List;
import main.Mesa;

public interface MesaDAO {
    List<Mesa> listaTodos() throws Exception;
    void atualiza(Mesa mesa) throws Exception;
    void cria(Mesa mesa) throws Exception;
}
