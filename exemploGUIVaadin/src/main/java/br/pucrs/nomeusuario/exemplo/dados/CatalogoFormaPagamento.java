package br.pucrs.nomeusuario.exemplo.dados;

import java.util.ArrayList;
import java.util.TreeMap;

public class CatalogoFormaPagamento {

    private TreeMap <Integer, FormaPagamento> pagamentos;

    public CatalogoFormaPagamento()
    {
        pagamentos = new TreeMap<>();
    }
    
    public boolean cadastraFormaPagamento (FormaPagamento novaForma)
    {
        if(!pagamentos.containsKey(novaForma.getCod()))
        {
            pagamentos.put( novaForma.getCod(), novaForma );
            return true;
        }
        else
        {
            return false;
        }
    }

    public FormaPagamento buscarFormaPagamento( int cod ) {
        return pagamentos.get( cod );
    }

    public ArrayList<FormaPagamento> getFormasPagamento() {
        ArrayList<FormaPagamento> pagamentosList = new ArrayList<>( pagamentos.values() ); 
        return pagamentosList;
    }

}