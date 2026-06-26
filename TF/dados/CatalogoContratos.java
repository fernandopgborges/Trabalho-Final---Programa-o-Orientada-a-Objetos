import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.TreeMap;
import java.util.stream.Collectors;

import dados.Cliente;
import dados.Contrato;
import dados.Jogo;

public class CatalogoContratos {

    private TreeMap <Integer, Contrato> contratos;


    public CatalogoContratos()
    {
        contratos = new TreeMap<>(Collections.reverseOrder());
    }


    public boolean cadastraContrato (Contrato novoContrato)
    {
        if(!contratos.containsKey(novoContrato.getId()))
        {
            contratos.put(novoContrato.getId(), novoContrato);
            return true;
        }
        else
            {
                return false;
            }
    }

    public ArrayList<Contrato> relatorioContrato()
    {
        ArrayList<Contrato> relatorio = new ArrayList<>(contratos);
        return relatorio;
        
    }

    public boolean removeContrato(Contrato contratoRemovido)
    {
        if(contratos.isEmpty() || contratos.containsKey(contratoRemovido.getId()))
        {
            return false;
        }
        else
        {
            contratos.remove(contratoRemovido.getId());
            return true;
        }
    }

    public ArrayList<Contrato> consultaContratoMaiorValor()
    {
        double valorContrato;
        ArrayList <Contrato> retorno = new ArrayList<>();

        valorContrato = contratos.values()
                      .stream()
                      .max(Comparator.comparingDouble(Contrato::calculaValorFinal))
                      .get()
                      .calculaValorFinal();

        retorno = contratos.values()
                       .stream()
                       .filter(contrato -> contrato.calculaValorFinal()==valorContrato)
                       .collect(Collectors.toList());

        return retorno;               
    }

    public ArrayList<Cliente> consultaClienteMaiorValor(CatalogoClientes catalogoClientes)
    {
        ArrayList <Cliente> retorno = new ArrayList<>();

        Cliente clienteValioso = catalogoClientes.relatorioClientes()
                                  .stream()
                                  .max(Comparator.comparingDouble(cliente -> valorTotalContratosCliente(cliente)))
                                  .get();
            
        double maiorValor = valorTotalContratosCliente(clienteValioso);
        
        retorno = catalogoClientes.relatorioClientes()
                                  .stream()
                                  .filter(cliente -> valorTotalContratosCliente(cliente)==maiorValor)
                                  .collect(Collectors.toList());


        if(maiorValor==0) return null;
        else return retorno;
    }

    public double valorTotalContratosCliente (Cliente clienteConsultado)
    {
        double somaValorCliente = contratos.values()
                                           .stream()
                                           .filter(contrato -> contrato.getCliente==clienteConsultado)
                                           .mapToDouble(contrato -> contrato.calculaValorFinal())
                                           .sum();

        return somaValorCliente;

    }
}
