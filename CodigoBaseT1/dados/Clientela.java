package dados;

import java.util.ArrayList;

public class Clientela {
    private ArrayList<Cliente> clientela;

    public Clientela() {
        clientela = new ArrayList<>();
    }

    public void adicionarCliente( Cliente c ) {
        clientela.add( c );
    }

    public ArrayList<Cliente> getClientela() {
        return clientela;
    }
}
