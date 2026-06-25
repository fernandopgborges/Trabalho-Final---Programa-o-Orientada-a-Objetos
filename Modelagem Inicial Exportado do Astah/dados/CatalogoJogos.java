import java.util.ArrayList;
import java.util.TreeMap;
import java.util.stream.Collectors;

import dados.Cliente;
import dados.Jogo;

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

    public ArrayList<Jogo> relatorioJogos()
    {
        ArrayList<Jogo> relatorio = new ArrayList<>(jogos.values());
        return relatorio;
    }

    public Jogo buscarJogo(int codigo)
    {
        Jogo jogoBuscado = jogos.get(codigo);

        return jogoBuscado;
    }

    public ArrayList<Jogo> consultaJogoMaiorValor()
    {
        double valorJogo;
        ArrayList <Jogo> retorno = new ArrayList<>();

        valorJogo = jogos.values()
                      .stream()
                      .max(Comparator.comparingDouble(Jogo::getValorDiario))
                      .get()
                      .getValorDiario();

        retorno = jogos.values()
                       .stream()
                       .filter(jogo -> jogo.getValorDiario()==valorJogo)
                       .collect(Collectors.toList());

        return retorno;               
    }

}
