package br.pucrs.nomeusuario.exemplo.dados;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class CatalogoJogos {

    private TreeMap <Integer, Jogo> jogos;


    public CatalogoJogos()
    {
        jogos = new TreeMap<>();
    }

    public boolean cadastrarJogo(Jogo novoJogo)
    {
        if(jogos.containsKey(novoJogo.getCodigo()))
        {
            return false;
        }
        else
        {
            jogos.put(novoJogo.getCodigo(), novoJogo);
            return true;
        }
    }

    public ArrayList<Jogo> getJogos()
    {
        ArrayList<Jogo> relatorio = new ArrayList<>(jogos.values());
        return relatorio;
    }

    public Jogo buscarJogo(int codigo)
    {
        Jogo jogoBuscado = jogos.get(codigo);

        return jogoBuscado;
    }

   public ArrayList<Jogo> consultaJogoMaiorValor() {
        
        if (jogos.isEmpty()) {
            return new ArrayList<>();
        }

        double valorJogo = jogos.values()
                .stream()
                .max(Comparator.comparingDouble(Jogo::getValorDiario))
                .get()
                .getValorDiario();

        return jogos.values()
                .stream()
                .filter(jogo -> Double.compare(jogo.getValorDiario(), valorJogo) == 0)
                .collect(Collectors.toCollection(ArrayList::new));
    }

}