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

    public ArrayList<Cliente> consultaClienteMaiorValor()
    {
        ArrayList <Cliente> retorno = new ArrayList<>();
        double valorTotalClienteValioso;

        valorTotalClienteValioso = contratos.values()
                                            .stream()
                                            

        return retorno;
    }

    public double valorTotalContratosCliente (Cliente clienteConsultado)
    {
        double somaValorCliente = contratos.values()
                                           .stream()
                                           .filter(contrato -> contratos.getCliente==clienteConsultado)
                                           .mapToDouble(contrato -> contrato.calculaValorFinal())
                                           .sum();

        return somaValorCliente;

    }
}