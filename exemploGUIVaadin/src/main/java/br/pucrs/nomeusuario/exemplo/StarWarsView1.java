package br.pucrs.nomeusuario.exemplo;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Eventos - Inner class")
@Route("/starwars1")
public class StarWarsView1 extends HorizontalLayout {
    private Button botaoOk;
    private Button botaoLimpa;
    private TextField campoTexto;
    private Text textoMensagem;

    public StarWarsView1() {
        botaoOk = new Button("OK");
        botaoLimpa = new Button("Limpa");
        campoTexto = new TextField("Mensagem");
        textoMensagem = new Text("");

        add(campoTexto);
        add(botaoOk);
        add(botaoLimpa);
        add(textoMensagem);

        // Vinculo do tratador de eventos
        TratadorEventos tratadorEventos = new TratadorEventos();
        botaoOk.addClickListener(tratadorEventos);
        botaoLimpa.addClickListener(tratadorEventos);
    }

    // Inner classe que trata eventos
    class TratadorEventos implements ComponentEventListener {
        @Override
        public void onComponentEvent(ComponentEvent event) {
            if (event.getSource() == botaoOk)
                textoMensagem.setText(campoTexto.getValue());
            else if (event.getSource() == botaoLimpa) {
                campoTexto.setValue("");
                textoMensagem.setText("");
            }

        }

    }

}