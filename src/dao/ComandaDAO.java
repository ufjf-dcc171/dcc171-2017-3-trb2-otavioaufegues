package dao;

import java.util.List;
import main.Comanda;

public interface ComandaDAO {
    List<Comanda> listaTodos() throws Exception;
    List<Comanda> listaComandasAbertasNaMesa(int NumMesa) throws Exception;
    List<Comanda> listaComandasNaMesa(int NumMesa) throws Exception;
    Comanda buscaPorId(Long id) throws Exception;  
    void exclui(Comanda comanda) throws Exception;
    void atualiza(Comanda comanda) throws Exception;
    void cria(Comanda comanda) throws Exception;

}
