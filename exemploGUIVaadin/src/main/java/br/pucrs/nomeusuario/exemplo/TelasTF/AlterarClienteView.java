package br.pucrs.nomeusuario.exemplo.TelasTF;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

import br.pucrs.nomeusuario.exemplo.dados.CatalogoClientes;
import br.pucrs.nomeusuario.exemplo.dados.Cliente;
import br.pucrs.nomeusuario.exemplo.dados.Corporativo;
import br.pucrs.nomeusuario.exemplo.dados.Individual;

@Route("/alterarCliente")
public class AlterarClienteView extends VerticalLayout {

    private Button buttonVoltar;
    private Button buttonSalvar;

    private Grid<Cliente> gridClientes;

    private TextField fieldNumero;
    private TextField fieldNome;
    private TextField fieldEmail;
    private TextField fieldDocumento;
    private TextField fieldNomeFantasia;

    private ComboBox<String> comboTipo;

    private CatalogoClientes catalogoClientes;

    public AlterarClienteView() {

        catalogoClientes =
            (CatalogoClientes) VaadinSession.getCurrent().getAttribute("catalogoClientes");

        if (catalogoClientes == null) {
            catalogoClientes = new CatalogoClientes();
        }

        buttonVoltar = new Button("Voltar");
        buttonSalvar = new Button("Salvar Alterações");

        gridClientes = new Grid<>(Cliente.class, false);

        comboTipo = new ComboBox<>("Tipo");
        comboTipo.setItems("Individual", "Corporativo");
        comboTipo.setReadOnly(true);

        fieldNumero = new TextField("Número");
        fieldNumero.setReadOnly(true);

        fieldNome = new TextField("Nome");
        fieldEmail = new TextField("Email");
        fieldDocumento = new TextField("Documento");
        fieldNomeFantasia = new TextField("Nome Fantasia");

        gridClientes.addColumn(c -> c.getNumero()).setHeader("Número");
        gridClientes.addColumn(c -> c.getNome()).setHeader("Nome");
        gridClientes.addColumn(c -> c.getEmail()).setHeader("Email");

        gridClientes.addColumn(c -> {
            if (c instanceof Individual)
                return "Individual";
            else
                return "Corporativo";
        }).setHeader("Tipo");

        gridClientes.setItems(catalogoClientes.getClientes());

        gridClientes.asSingleSelect().addValueChangeListener(event -> {

            Cliente cliente = event.getValue();

            if (cliente == null)
                return;

            fieldNumero.setValue(String.valueOf(cliente.getNumero()));
            fieldNome.setValue(cliente.getNome());
            fieldEmail.setValue(cliente.getEmail());

            if (cliente instanceof Individual individual) {

                comboTipo.setValue("Individual");
                fieldDocumento.setValue(individual.getCpf());
                fieldNomeFantasia.clear();
                fieldNomeFantasia.setReadOnly(true);

            } else if (cliente instanceof Corporativo corporativo) {

                comboTipo.setValue("Corporativo");
                fieldDocumento.setValue(corporativo.getCnpj());
                fieldNomeFantasia.setReadOnly(false);
                fieldNomeFantasia.setValue(corporativo.getNomeFantasia());

            }

        });

        buttonSalvar.addClickListener(e -> {

            Cliente selecionado = gridClientes.asSingleSelect().getValue();

            if (selecionado == null) {
                Notification.show("Selecione um cliente.");
                return;
            }

            Cliente clienteAlterado;

            if (selecionado instanceof Individual) {

                clienteAlterado = new Individual(
                        Integer.parseInt(fieldNumero.getValue()),
                        fieldNome.getValue(),
                        fieldEmail.getValue(),
                        fieldDocumento.getValue());

            } else {

                clienteAlterado = new Corporativo(
                        Integer.parseInt(fieldNumero.getValue()),
                        fieldNome.getValue(),
                        fieldEmail.getValue(),
                        fieldDocumento.getValue(),
                        fieldNomeFantasia.getValue());

            }

            boolean alterou = catalogoClientes.alteraDadosCliente(clienteAlterado);

            if (alterou) {
                gridClientes.setItems(catalogoClientes.getClientes());
                Notification.show("Cliente alterado.");
            } else {
                Notification.show("Erro ao alterar.");
            }

        });

        buttonVoltar.addClickListener(e -> {

            VaadinSession.getCurrent().setAttribute("catalogoClientes", catalogoClientes);

            UI.getCurrent().navigate("");

        });

        add(
                gridClientes,
                fieldNumero,
                fieldNome,
                fieldEmail,
                comboTipo,
                fieldDocumento,
                fieldNomeFantasia,
                buttonSalvar,
                buttonVoltar);
    }
}
