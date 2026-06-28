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

@Route( "/cadastroCliente")
public class cadastroClienteView extends VerticalLayout {
    private Button buttonVoltar;
    private Button buttonAdicionar;
    private Grid<Cliente> gridClientes;
    private TextField fieldNumber;
    private TextField fieldName;
    private TextField fieldEmail;
    private TextField fieldDocumento;
    private TextField fieldNomeFantasia;

    private ComboBox<String> comboTipo;

    public cadastroClienteView() {
        buttonVoltar = new Button( "Voltar" );
        gridClientes = new Grid<Cliente>( Cliente.class, false );

        CatalogoClientes catalogoTmp = ( CatalogoClientes )VaadinSession.getCurrent().getAttribute( "catalogoClientes" );

        if ( catalogoTmp == null ) {
            catalogoTmp = new CatalogoClientes();
        }

        CatalogoClientes catalogoClientes = catalogoTmp;

        comboTipo = new ComboBox<>( "Escolha o tipo de Cliente:" );
        comboTipo.setItems( "Individual", "Corporativo" );

        fieldNumber         = new TextField("Número" );
        fieldName           = new TextField("Nome:" );
        fieldEmail          = new TextField("Email:" );
        fieldDocumento      = new TextField( "Documento (CPF - Individual| CNPJ - Corporativo):" );
        fieldNomeFantasia   = new TextField( "Nome Fantasia (Corporativo):" );

        buttonAdicionar = new Button( "Adicionar Cliente" );
        buttonAdicionar.addClickListener( e -> {
            String tipo = comboTipo.getValue();

            int numero;          
            try {
                numero = Integer.parseInt( fieldNumber.getValue() );
            } catch ( NumberFormatException exception ) {
                Notification.show( "Número Inválido!" );
                return;
            }
            String nome         = fieldName.getValue();
            String email        = fieldEmail.getValue();
            String documento    = fieldDocumento.getValue();
            String nomeFantasia = fieldNomeFantasia.getValue();

            if ( tipo.isEmpty() || fieldNumber.isEmpty() || nome.isEmpty()
                    || email.isEmpty() || documento.isEmpty() ) {

                Notification.show( "Preencha todos os espaços!" );
                return;
            }

            Cliente c = null;
            if ( tipo.equalsIgnoreCase( "Corporativo" ) ) {
                if ( nomeFantasia.isEmpty() ) {
                    Notification.show( "Nome fantasia é obrigatório para clientes Corporativos!" ); 
                    return;
                }

                c = new Corporativo( numero, nome, email, documento, nomeFantasia );
            } else if ( tipo.equalsIgnoreCase( "Individual" ) ){
                c = new Individual( numero, nome, email, documento ); 
            } else {
                Notification.show( "Erro: Tipo Inválido! Tente 'Corporativo' ou 'Individual'!" ); 
                return;
            }
            var cadastroCheck = catalogoClientes.cadastrarCliente( c );
            var mensagemNotificação = "Erro ao Cadastrar Cliente: Número Repetido!";
            if ( cadastroCheck ) {
                mensagemNotificação = "Cliente cadastrado com sucesso!";
                gridClientes.setItems( catalogoClientes.getClientes() );
            }

            Notification.show( mensagemNotificação );
        });

        buttonVoltar.addClickListener( e -> {
            UI.getCurrent().navigate( "" );
            VaadinSession.getCurrent().setAttribute("catalogoClientes", catalogoClientes ); 
        });

        // Programando a grid
        gridClientes.addColumn( c -> c.getNumero() ).setHeader( "Numero" );
        gridClientes.addColumn( c -> c.getNome() ).setHeader( "Nome" );
        gridClientes.addColumn( c -> c.getEmail() ).setHeader( "Email" );
        gridClientes.addColumn( c -> {
            if ( c instanceof Corporativo ) {
                return "Corporativo";
            } else if ( c instanceof Individual ){
                return "Individual";
            }
            return "";
        }).setHeader( "Tipo" );

        gridClientes.addColumn( c -> {
            if ( c instanceof Corporativo cc ) {
                return cc.getCnpj();
            } else if ( c instanceof Individual ci){
                return ci.getCpf();
            }
            return "";
        }).setHeader( "Documento (CPF/CPNJ)" );

        gridClientes.addColumn( c -> {
            if ( c instanceof Corporativo cc ) {
                return cc.getNomeFantasia();
            }
            return "N/A";
        }).setHeader( "Nome Fantasia" );

        gridClientes.setItems( catalogoClientes.getClientes() );

        add( fieldNumber );
        add( fieldName );
        add( fieldEmail );
        add( comboTipo );
        add( fieldDocumento );
        add( fieldNomeFantasia );
        add( buttonAdicionar );
        add( gridClientes );
        add( buttonVoltar );
    }
}
