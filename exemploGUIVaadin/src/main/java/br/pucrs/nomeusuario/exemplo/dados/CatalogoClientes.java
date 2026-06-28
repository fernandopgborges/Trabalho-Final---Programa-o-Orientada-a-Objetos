package br.pucrs.nomeusuario.exemplo.dados;

import java.util.ArrayList;
import java.util.TreeMap;

public class CatalogoClientes {

    private TreeMap<Integer, Cliente> clientes;

    public CatalogoClientes ()
    {
        clientes = new TreeMap<>();
    }

    public boolean cadastrarCliente(Cliente novoCliente)
    {
        if(clientes.containsKey(novoCliente.getNumero()))
        {
            return false;
        }
        else
        {
            clientes.put(novoCliente.getNumero(),novoCliente);
            return true;
        }

    }

    public Cliente buscarCliente( int numero ) {
        return clientes.get( numero );
    }

    public ArrayList<Cliente> getClientes()
    {
        ArrayList<Cliente> relatorio = new ArrayList<>(clientes.values());
        return relatorio;
    }
    
    public boolean alteraDadosCliente (Cliente clienteModificado)
    {
        if(clientes.isEmpty()|| !clientes.containsKey(clienteModificado.getNumero()))
        {
            return false;
        }
        else
        {
            clientes.replace(clienteModificado.getNumero(), clienteModificado);
            return true;
        }
    }

}