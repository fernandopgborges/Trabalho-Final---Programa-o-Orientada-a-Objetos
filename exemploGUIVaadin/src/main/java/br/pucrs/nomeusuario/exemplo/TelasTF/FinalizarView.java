package br.pucrs.nomeusuario.exemplo.TelasTF;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route( "/finalizar" )
public class FinalizarView extends VerticalLayout {
    public FinalizarView() {
        H1 titulo = new H1( "Acabou!" );
        H2 subtitulo = new H2( "Obrigado por usar!");

        add( titulo, subtitulo );
    }
}
