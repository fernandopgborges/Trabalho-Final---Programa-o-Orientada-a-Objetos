package dados;

import java.util.ArrayList;

public class BibliotecaJogos {
    private ArrayList<Jogo> biblioteca;

    public BibliotecaJogos() {
        biblioteca = new ArrayList<>();
    }

    public ArrayList<Jogo> getBiblioteca() {
        return biblioteca;
    }

    public int getTamanho() {
        return biblioteca.size();
    }
    public void adicionarJogo( Jogo game ) {
        biblioteca.add( game );
    }

}
