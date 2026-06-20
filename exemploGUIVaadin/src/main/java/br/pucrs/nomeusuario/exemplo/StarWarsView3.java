package br.pucrs.nomeusuario.exemplo;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Eventos - Anonymous Inner Class")
@Route("/starwars3")
public class StarWarsView3 extends HorizontalLayout {
    private Button botaoOk;
    private Button botaoLimpa;
    private TextField campoTexto;
    private Text textoMensagem;

    public StarWarsView3() {
        botaoOk = new Button("OK");
        botaoLimpa = new Button("Limpa");
        campoTexto = new TextField("Mensagem");
        textoMensagem = new Text("");

        add(campoTexto);
        add(botaoOk);
        add(botaoLimpa);
        add(textoMensagem);

        // Vinculo e tratamento de eventos
        botaoOk.addClickListener(new ComponentEventListener<ClickEvent<Button>>() {
            @Override
            public void onComponentEvent(ClickEvent<Button> event) {
                textoMensagem.setText(campoTexto.getValue());                
            }
        });
        botaoLimpa.addClickListener(new ComponentEventListener<ClickEvent<Button>>() {
            @Override
            public void onComponentEvent(ClickEvent<Button> event) {
                campoTexto.setValue("");
                textoMensagem.setText("");                
            }
        });
    }

}