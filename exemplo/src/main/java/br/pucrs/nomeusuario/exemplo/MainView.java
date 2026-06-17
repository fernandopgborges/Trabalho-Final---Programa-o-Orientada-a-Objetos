package br.pucrs.nomeusuario.exemplo;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;

@Route("")
public class MainView extends VerticalLayout {
    public MainView() {
       Button sayHelloButton = new Button("Say hello");
       sayHelloButton.addClickListener(e -> {
             Notification.show("Hello world!");
       });
       add(sayHelloButton);

       add(new Hr());

    RouterLink linkParaDemo1 = new RouterLink("Ir para o demo 1 - Alguns componentes", Demo1View.class);
    add(linkParaDemo1);
    add( new RouterLink("Ir para o demo 2 - Mais alguns componentes", Demo2View.class));
   }
}
