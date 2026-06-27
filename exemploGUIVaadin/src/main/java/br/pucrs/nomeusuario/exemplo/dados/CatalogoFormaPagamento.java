package br.pucrs.nomeusuario.exemplo.dados;

import java.util.TreeMap;

public class CatalogoFormaPagamento {

    private Treemap <Integer, FormaPagamento> pagamentos;

    public CatalogoFormaPagamento()
    {
        pagamentos = new TreeMap<>();
    }
    
    public boolean cadastraFormaPagamento (FormaPagamento novaForma)
    {
        if(!pagamentos.containsKey(novaForma.getCod()))
        {
            pagamentos.put(novaForma);
            return true;
        }
        else
        {
            return false;
        }
    }

}