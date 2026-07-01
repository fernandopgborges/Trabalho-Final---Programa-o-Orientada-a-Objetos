package br.pucrs.nomeusuario.exemplo.app;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

import br.pucrs.nomeusuario.exemplo.dados.CatalogoClientes;
import br.pucrs.nomeusuario.exemplo.dados.Cliente;
import br.pucrs.nomeusuario.exemplo.dados.Corporativo;
import br.pucrs.nomeusuario.exemplo.dados.Individual;

@Route("/relatorioCliente")
public class RelatorioClienteView extends VerticalLayout {

    private Button buttonVoltar;
    private Grid<Cliente> gridClientes;

    public RelatorioClienteView() {

        H1 titulo = new H1("Relatorio Clientes");

        buttonVoltar = new Button("Voltar");
        gridClientes = new Grid<>(Cliente.class, false);

        CatalogoClientes catalogoClientes =
            (CatalogoClientes) VaadinSession.getCurrent().getAttribute("catalogoClientes");

        if (catalogoClientes == null) {
            catalogoClientes = new CatalogoClientes();
        }
        
        gridClientes.addColumn(c -> c.getNumero()).setHeader("Numero");
        gridClientes.addColumn(c -> c.getNome()).setHeader("Nome");
        gridClientes.addColumn(c -> c.getEmail()).setHeader("Email");

        gridClientes.addColumn(c -> {
            if (c instanceof Corporativo) {
                return "Corporativo";
            } else if (c instanceof Individual) {
                return "Individual";
            }
            return "";
        }).setHeader("Tipo");

        gridClientes.addColumn(c -> {
            if (c instanceof Corporativo cc) {
                return cc.getCnpj();
            } else if (c instanceof Individual ci) {
                return ci.getCpf();
            }
            return "";
        }).setHeader("Documento (CPF/CNPJ)");

        gridClientes.addColumn(c -> {
            if (c instanceof Corporativo cc) {
                return cc.getNomeFantasia();
            }
            return "N/A";
        }).setHeader("Nome Fantasia");

        gridClientes.setItems(catalogoClientes.getClientes());

        buttonVoltar.addClickListener(e -> {
            UI.getCurrent().navigate("");
        });

        add(titulo);
        add(gridClientes);
        add(buttonVoltar);
    }
}
