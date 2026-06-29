package br.pucrs.nomeusuario.exemplo.TelasTF;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

import br.pucrs.nomeusuario.exemplo.dados.CartaoCredito;
import br.pucrs.nomeusuario.exemplo.dados.CatalogoClientes;
import br.pucrs.nomeusuario.exemplo.dados.CatalogoFormaPagamento;
import br.pucrs.nomeusuario.exemplo.dados.Cliente;
import br.pucrs.nomeusuario.exemplo.dados.FormaPagamento;
import br.pucrs.nomeusuario.exemplo.dados.PIX;

@Route( "/cadastroPagamento" )
public class CadastroPagamentoView extends VerticalLayout {

    private Button buttonVoltar;
    private Button buttonAdicionar;
    private Grid<FormaPagamento> gridPagamentos;

    private TextField fieldCod;
    private TextField fieldDiaVencimento;
    private TextField fieldChaveNumero;

    private ComboBox<Cliente> comboCliente;
    private ComboBox<String> comboTipo;
    private DatePicker datePicker;

    public CadastroPagamentoView() {

        buttonVoltar = new Button( "Voltar" );
        buttonAdicionar = new Button( "Adicionar Pagamento" );

        gridPagamentos = new Grid<FormaPagamento>( FormaPagamento.class, false );

        CatalogoFormaPagamento catalogoTmp =
            ( CatalogoFormaPagamento ) VaadinSession.getCurrent().getAttribute( "catalogoFormaPagamento" );

        if ( catalogoTmp == null ) {
            catalogoTmp = new CatalogoFormaPagamento();
        }

        final CatalogoFormaPagamento catalogoFormaPagamento = catalogoTmp;

        CatalogoClientes catalogoCtmp =
            ( CatalogoClientes ) VaadinSession.getCurrent().getAttribute( "catalogoClientes" );

        if ( catalogoCtmp == null ) {
            catalogoCtmp = new CatalogoClientes();
        }

        final CatalogoClientes catalogoClientes = catalogoCtmp;

        comboCliente = new ComboBox<>( "Escolha o Cliente:" );
        comboCliente.setItems( catalogoClientes.getClientes() );
        comboCliente.setItemLabelGenerator( c -> c.getNumero() + ":" + c.getNome() );

        comboTipo = new ComboBox<>( "Tipo de Pagamento" );
        comboTipo.setItems( "Cartão de Crédito", "PIX" );

        datePicker = new DatePicker( "Validade (Cartão)" );

        fieldCod = new TextField( "Código" );
        fieldDiaVencimento = new TextField( "Dia Vencimento" );
        fieldChaveNumero = new TextField( "Chave / Número" );

        buttonAdicionar.addClickListener( e -> {

            String tipo = comboTipo.getValue();
            String codStr = fieldCod.getValue();
            String diaStr = fieldDiaVencimento.getValue();
            String chaveNumero = fieldChaveNumero.getValue();

            if ( tipo == null || codStr.isEmpty() || diaStr.isEmpty() || chaveNumero.isEmpty() || comboCliente.isEmpty() ) {
                Notification.show( "Preencha todos os campos!" );
                return;
            }

            int cod;
            int diaVencimento;
            
            int numeroCliente = comboCliente.getValue().getNumero();

            try {
                cod = Integer.parseInt( codStr );
                diaVencimento = Integer.parseInt( diaStr );
            } catch ( NumberFormatException ex ) {
                Notification.show( "Número inválido!" );
                return;
            }

            FormaPagamento novaForma;

            SimpleDateFormat format = new SimpleDateFormat( "dd/MM/yyyy" );

            if ( tipo.equalsIgnoreCase( "Cartão de Crédito" ) ) {

                if ( datePicker.getValue() == null ) {
                    Notification.show( "Informe a validade do cartão!" );
                    return;
                }

                Date validade;

                try {
                    String dataStr = datePicker.getValue().format(
                        java.time.format.DateTimeFormatter.ofPattern( "dd/MM/yyyy" )
                    );

                    validade = format.parse( dataStr );

                } catch ( ParseException ex ) {
                    Notification.show( "Data inválida!" );
                    return;
                }

                novaForma = new CartaoCredito(
                    cod,
                    diaVencimento,
                    numeroCliente,
                    chaveNumero,
                    validade
                );

            } else if ( tipo.equalsIgnoreCase( "PIX" ) ) {

                novaForma = new PIX(
                    cod,
                    diaVencimento,
                    numeroCliente,
                    chaveNumero
                );

            } else {
                Notification.show( "Tipo inválido!" );
                return;
            }

            boolean cadastroCheck =
                catalogoFormaPagamento.cadastraFormaPagamento( novaForma );

            String mensagem = "Erro ao cadastrar: código repetido!";

            if ( cadastroCheck ) {
                mensagem = "Pagamento cadastrado com sucesso!";
                gridPagamentos.setItems( catalogoFormaPagamento.getFormasPagamento() );
            }

            Notification.show( mensagem );
        });

        buttonVoltar.addClickListener( e -> {
            VaadinSession.getCurrent().setAttribute( "catalogoFormaPagamento", catalogoFormaPagamento );
            UI.getCurrent().navigate( "" );
        });

        gridPagamentos.addColumn( p -> p.getCod() ).setHeader( "Código" );
        gridPagamentos.addColumn( p -> p.getDiaVencimento() ).setHeader( "Dia Vencimento" );

        gridPagamentos.addColumn( p -> {
            Cliente c = catalogoClientes.buscarCliente( p.getNumeroCliente() );
            return c.getNumero() + ": " + c.getNome();
        } ).setHeader( "Cliente" );

        gridPagamentos.addColumn( p -> {
            if ( p instanceof PIX ) {
                return "PIX";
            } else if ( p instanceof CartaoCredito ) {
                return "Cartão de Crédito";
            }
            return "";
        }).setHeader( "Tipo" );

        gridPagamentos.addColumn( p -> {
            if ( p instanceof PIX pix ) {
                return pix.getChave();
            } else if ( p instanceof CartaoCredito cartao ) {
                return cartao.getNumero();
            }
            return "";
        } ).setHeader( "Chave / Número" );

        SimpleDateFormat format = new SimpleDateFormat( "dd/MM/yyyy" );

        gridPagamentos.addColumn( p -> {
            if ( p instanceof CartaoCredito cartao ) {
                return format.format( cartao.getValidade() );
            }
            return "N/A";
        } ).setHeader( "Data" );

        gridPagamentos.setItems( catalogoFormaPagamento.getFormasPagamento() );

        add(
            fieldCod,
            fieldDiaVencimento,
            comboCliente,
            fieldChaveNumero,
            comboTipo,
            datePicker,
            buttonAdicionar,
            gridPagamentos,
            buttonVoltar
        );
    }
}