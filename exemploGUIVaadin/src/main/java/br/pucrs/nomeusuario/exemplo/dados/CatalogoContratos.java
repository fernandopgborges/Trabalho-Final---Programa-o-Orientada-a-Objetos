package br.pucrs.nomeusuario.exemplo.dados;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.TreeMap;
import java.util.stream.Collectors;

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
        ArrayList<Contrato> relatorio = new ArrayList<>(contratos.values());
        return relatorio;
    }

    public boolean removeContrato(Contrato contratoRemovido)
    {
        if(contratos.isEmpty() || !contratos.containsKey(contratoRemovido.getId()))
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
                      .max(Comparator.comparingDouble(contrato -> contrato.calculaValorFinal(this)))
                      .get()
                      .calculaValorFinal(this);

        retorno = contratos.values()
                       .stream()
                       .filter(contrato -> contrato.calculaValorFinal(this)==valorContrato)
                       .collect(Collectors.toCollection(ArrayList::new));

        return retorno;               
    }

    public ArrayList<Cliente> consultaClienteMaiorValor(CatalogoClientes catalogoClientes)
    {
        ArrayList <Cliente> retorno = new ArrayList<>();

        Cliente clienteValioso = catalogoClientes.getClientes()
                                  .stream()
                                  .max(Comparator.comparingDouble(cliente -> valorTotalContratosCliente(cliente)))
                                  .get();
            
        double maiorValor = valorTotalContratosCliente(clienteValioso);
        
        retorno = catalogoClientes.getClientes()
                                  .stream()
                                  .filter(cliente -> valorTotalContratosCliente(cliente)==maiorValor)
                                  .collect(Collectors.toCollection(ArrayList::new));


        if(maiorValor==0) return null;
        else return retorno;
    }

    public double valorTotalContratosCliente (Cliente clienteConsultado)
    {
        double somaValorCliente = contratos.values()
                                           .stream()
                                           .filter(contrato -> contrato.getCliente()==clienteConsultado)
                                           .mapToDouble(contrato -> contrato.calculaValorFinal(this))
                                           .sum();

        return somaValorCliente;
    }

    public int contarContratosCliente(Cliente clienteConsultado)
    {
        long quantidade = contratos.values()
                                    .stream()
                                    .filter(contrato -> contrato.getCliente().equals(clienteConsultado))
                                    .count();

        return (int) quantidade;
    }
}