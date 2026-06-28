package br.pucrs.nomeusuario.exemplo.dados;

/* 
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class CatalogoContratos {

    private TreeMap<Integer, Contrato> contratos;

    public CatalogoContratos() {
        contratos = new TreeMap<>(Collections.reverseOrder());
    }

    public boolean cadastraContrato(Contrato novoContrato) {
        if (!contratos.containsKey(novoContrato.getId())) {
            contratos.put(novoContrato.getId(), novoContrato);
            return true;
        }
        return false;
    }

    public ArrayList<Contrato> relatorioContrato() {
        return new ArrayList<>(contratos.values());
    }

    public boolean removeContrato(Contrato contratoRemovido) {
        if (contratos.isEmpty() ||
            !contratos.containsKey(contratoRemovido.getId())) {
            return false;
        }

        contratos.remove(contratoRemovido.getId());
        return true;
    }

    public ArrayList<Contrato> consultaContratoMaiorValor() {

        Optional<Contrato> max = contratos.values()
                .stream()
                .max(Comparator.comparingDouble(Contrato::calculaValorFinal));

        if (max.isEmpty()) {
            return new ArrayList<>();
        }

        double valorMax = max.get().calculaValorFinal();

        return contratos.values()
                .stream()
                .filter(c -> Double.compare(c.calculaValorFinal(), valorMax) == 0)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<Cliente> consultaClienteMaiorValor() {

        Map<Cliente, Double> totalPorCliente = contratos.values()
                .stream()
                .collect(Collectors.groupingBy(
                        Contrato::getCo,
                        Collectors.summingDouble(Contrato::calculaValorFinal)
                ));

        Optional<Map.Entry<Cliente, Double>> max =
                totalPorCliente.entrySet()
                        .stream()
                        .max(Map.Entry.comparingByValue());

        if (max.isEmpty()) {
            return new ArrayList<>();
        }

        double maiorValor = max.get().getValue();

        return totalPorCliente.entrySet()
                .stream()
                .filter(e -> Double.compare(e.getValue(), maiorValor) == 0)
                .map(Map.Entry::getKey)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public double valorTotalContratosCliente(Cliente clienteConsultado) {

        return contratos.values()
                .stream()
                .filter(c -> c.getCliente().equals(clienteConsultado))
                .mapToDouble(Contrato::calculaValorFinal)
                .sum();
    }
}

*/