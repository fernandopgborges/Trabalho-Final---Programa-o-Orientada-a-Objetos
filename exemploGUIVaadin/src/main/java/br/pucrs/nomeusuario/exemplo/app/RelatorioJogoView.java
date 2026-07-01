package br.pucrs.nomeusuario.exemplo.app;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

import br.pucrs.nomeusuario.exemplo.dados.CatalogoJogos;
import br.pucrs.nomeusuario.exemplo.dados.Jogo;

@Route("/relatorioJogo")
public class RelatorioJogoView extends VerticalLayout {

    private Button buttonVoltar;
    private Grid<Jogo> gridJogos;

    public RelatorioJogoView() {

        H1 titulo = new H1("Relatorio Jogos");

        buttonVoltar = new Button("Voltar");
        gridJogos = new Grid<>(Jogo.class, false);

        CatalogoJogos catalogoJogos =
            (CatalogoJogos) VaadinSession.getCurrent().getAttribute("catalogoJogos");

        if (catalogoJogos == null) {
            catalogoJogos = new CatalogoJogos();
        }

        gridJogos.addColumn(c -> c.getCodigo()).setHeader("Código");
        gridJogos.addColumn(c -> c.getNome()).setHeader("Nome");
        gridJogos.addColumn(c -> c.getAno()).setHeader("Ano");
        gridJogos.addColumn(c -> c.getValorDiario()).setHeader("Valor Diário");
        gridJogos.addColumn(c -> c.getCategoria().name()).setHeader("Categoria");

        gridJogos.addColumn(c -> {
            if (c.getJogoDisponivel()) {
                return "Disponível";
            } else {
                return "Indisponível - Código: " + c.getContrato();
            }
        }).setHeader("Disponível");

        gridJogos.setItems(catalogoJogos.getJogos());

        buttonVoltar.addClickListener(e -> {
            UI.getCurrent().navigate("");
        });

        add(titulo);
        add(gridJogos);
        add(buttonVoltar);
    }
}