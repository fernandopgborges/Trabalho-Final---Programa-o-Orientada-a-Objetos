package br.pucrs.nomeusuario.exemplo.TelasTF;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route( "/cadastroCliente")
public class cadastroClienteView extends VerticalLayout {
    private Button buttonVoltar;

    public cadastroClienteView() {
        buttonVoltar = new Button( "Voltar" );

        buttonVoltar.addClickListener( e -> UI.getCurrent().navigate( "" ) );

        add( buttonVoltar );
    }
}
