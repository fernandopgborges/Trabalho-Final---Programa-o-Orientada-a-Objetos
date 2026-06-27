package dados;

import java.util.ArrayList;

public class ListaContratos {
    private ArrayList<Contrato> lista;

    public ListaContratos() {
        lista = new ArrayList<>();
    }

    public ArrayList<Contrato> getLista() {
        return lista;
    }

    public void adicionarContrato( Contrato c ) {
        lista.add( c );
    }

    public void removerContrato( Contrato c ) {
        lista.remove( c );
    }
}
