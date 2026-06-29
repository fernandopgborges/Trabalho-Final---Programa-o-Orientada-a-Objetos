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
    
    public boolean alteraDadosCliente(Cliente clienteModificado)
    {
        Cliente cliente = clientes.get(clienteModificado.getNumero());

        if (cliente == null)
        {
            return false;
        }

        cliente.setNome(clienteModificado.getNome());
        cliente.setEmail(clienteModificado.getEmail());

        if (cliente instanceof Individual clienteOriginal && clienteModificado instanceof Individual clienteNovo)
        {
            clienteOriginal.setCpf(clienteNovo.getCpf());
        }
        else if (cliente instanceof Corporativo clienteOriginal && clienteModificado instanceof Corporativo clienteNovo)
        {
            clienteOriginal.setCnpj(clienteNovo.getCnpj());
            clienteOriginal.setNomeFantasia(clienteNovo.getNomeFantasia());
        }

        return true;
    }

}