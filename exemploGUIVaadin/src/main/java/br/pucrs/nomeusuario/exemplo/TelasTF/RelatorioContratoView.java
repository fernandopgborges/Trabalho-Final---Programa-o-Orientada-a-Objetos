package br.pucrs.nomeusuario.exemplo.TelasTF;

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

@Route("/relatorioContrato")
public class RelatorioContratoView extends VerticalLayout {

    private Grid<Contrato> gridContratos;
    private Button buttonVoltar;

    private CatalogoClientes catalogoClientes;
    private CatalogoJogos catalogoJogos;
    private CatalogoFormaPagamento catalogoPagamentos;
    private CatalogoContratos catalogoContratos;

    public RelatorioContratoView() {

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

        H1 titulo = new H1("Relatório de Contratos");

        gridContratos = new Grid<>(Contrato.class, false);

        gridContratos.addColumn(c -> c.getId()).setHeader("ID");

        gridContratos.addColumn(c -> {
            Cliente cliente = catalogoClientes.buscarCliente(c.getNumeroCliente());
            return (cliente != null)
                ? cliente.getNumero() + " - " + cliente.getNome()
                : "Desconhecido";
        }).setHeader("Cliente");

        gridContratos.addColumn(c -> {
            Jogo jogo = catalogoJogos.buscarJogo(c.getCodigoJogo());
            return (jogo != null)
                ? jogo.getCodigo() + " - " + jogo.getNome()
                : "Desconhecido";
        }).setHeader("Jogo");

        gridContratos.addColumn(c -> {
            FormaPagamento p = catalogoPagamentos.buscarFormaPagamento(c.getCodPagamento());
            return (p != null)
                ? String.valueOf(p.getCod())
                : "Desconhecido";
        }).setHeader("Pagamento");

        gridContratos.addColumn(c -> c.getPeriodo()).setHeader("Período");

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        gridContratos.addColumn(c -> format.format(c.getData())).setHeader("Data");

        gridContratos.addColumn(c -> {
            String valor = String.format("%.2f", c.calculaValorFinal(catalogoContratos));
            return valor;
        }).setHeader("Valor Final");

        var lista = catalogoContratos.relatorioContrato();

        if (lista.isEmpty()) {
            Notification.show("Nenhum contrato cadastrado!");
        }

        gridContratos.setItems(lista);

        buttonVoltar = new Button("Voltar");

        buttonVoltar.addClickListener(e -> {
            VaadinSession.getCurrent().setAttribute("catalogoJogos", catalogoJogos);
            VaadinSession.getCurrent().setAttribute("catalogoClientes", catalogoClientes);
            VaadinSession.getCurrent().setAttribute("catalogoFormaPagamento", catalogoPagamentos);
            VaadinSession.getCurrent().setAttribute("catalogoContratos", catalogoContratos);

            UI.getCurrent().navigate("");
        });

        add(titulo, gridContratos, buttonVoltar);
    }
}