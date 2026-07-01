package br.pucrs.nomeusuario.exemplo.app;

import java.text.SimpleDateFormat;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

import br.pucrs.nomeusuario.exemplo.dados.CatalogoClientes;
import br.pucrs.nomeusuario.exemplo.dados.CatalogoContratos;
import br.pucrs.nomeusuario.exemplo.dados.CatalogoFormaPagamento;
import br.pucrs.nomeusuario.exemplo.dados.CatalogoJogos;
import br.pucrs.nomeusuario.exemplo.dados.Cliente;
import br.pucrs.nomeusuario.exemplo.dados.Contrato;
import br.pucrs.nomeusuario.exemplo.dados.FormaPagamento;
import br.pucrs.nomeusuario.exemplo.dados.Jogo;

@Route("/removerContrato")
public class RemoverContratoView extends VerticalLayout {

    private Grid<Contrato> gridContratos;

    private Button buttonRemover;
    private Button buttonVoltar;

    private CatalogoClientes catalogoClientes;
    private CatalogoJogos catalogoJogos;
    private CatalogoFormaPagamento catalogoPagamentos;
    private CatalogoContratos catalogoContratos;

    public RemoverContratoView() {

        catalogoClientes =
            (CatalogoClientes) VaadinSession.getCurrent().getAttribute("catalogoClientes");

        catalogoJogos =
            (CatalogoJogos) VaadinSession.getCurrent().getAttribute("catalogoJogos");

        catalogoPagamentos =
            (CatalogoFormaPagamento) VaadinSession.getCurrent().getAttribute("catalogoFormaPagamento");

        catalogoContratos =
            (CatalogoContratos) VaadinSession.getCurrent().getAttribute("catalogoContratos");

        if (catalogoContratos == null) {
            catalogoContratos = new CatalogoContratos();
        }

        H1 titulo = new H1("Remover Contrato");

        gridContratos = new Grid<>(Contrato.class, false);

        gridContratos.addColumn(c -> c.getId()).setHeader("ID");

        gridContratos.addColumn(c -> {
            Cliente cliente = catalogoClientes.buscarCliente(c.getNumeroCliente());
            return cliente.getNumero() + " - " + cliente.getNome();
        }).setHeader("Cliente");

        gridContratos.addColumn(c -> {
            Jogo jogo = catalogoJogos.buscarJogo(c.getCodigoJogo());
            return jogo.getCodigo() + " - " + jogo.getNome();
        }).setHeader("Jogo");

        gridContratos.addColumn(c -> {
            FormaPagamento p = catalogoPagamentos.buscarFormaPagamento(c.getCodPagamento());
            return String.valueOf(p.getCod());
        }).setHeader("Pagamento");

        gridContratos.addColumn(Contrato::getPeriodo).setHeader("Período");

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        gridContratos.addColumn(c -> format.format(c.getData())).setHeader("Data");

        gridContratos.setItems(catalogoContratos.relatorioContrato());

        buttonRemover = new Button("Remover Contrato");

        buttonRemover.addClickListener(e -> {

            Contrato contrato = gridContratos.asSingleSelect().getValue();

            if (contrato == null) {
                Notification.show("Selecione um contrato.");
                return;
            }

            catalogoContratos.removeContrato(contrato);

            // libera o jogo novamente
            contrato.getJogo().setJogoDisponivel(true);
            contrato.getJogo().setContrato(-1);

            gridContratos.setItems(catalogoContratos.relatorioContrato());

            Notification.show("Contrato removido.");
        });

        buttonVoltar = new Button("Voltar");

        buttonVoltar.addClickListener(e -> {

            VaadinSession.getCurrent().setAttribute("catalogoContratos", catalogoContratos);
            VaadinSession.getCurrent().setAttribute("catalogoJogos", catalogoJogos);

            UI.getCurrent().navigate("");
        });

        add(
            titulo,
            gridContratos,
            buttonRemover,
            buttonVoltar
        );
    }
}