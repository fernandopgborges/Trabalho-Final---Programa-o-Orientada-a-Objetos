package br.pucrs.nomeusuario.exemplo;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Eventos - Expressoes Lambda")
@Route("/starwars4")
public class StarWarsView4 extends HorizontalLayout {
    private Button botaoOk;
    private Button botaoLimpa;
    private TextField campoTexto;
    private Text textoMensagem;

    public StarWarsView4() {
        botaoOk = new Button("OK");
        botaoLimpa = new Button("Limpa");
        campoTexto = new TextField("Mensagem");
        textoMensagem = new Text("");

        add(campoTexto);
        add(botaoOk);
        add(botaoLimpa);
        add(textoMensagem);

        // Vinculo e tratamento de eventos
        botaoOk.addClickListener(e -> textoMensagem.setText(campoTexto.getValue()));
            
        botaoLimpa.addClickListener(e -> {
                campoTexto.setValue("");
                textoMensagem.setText("");                
        });
    }

}