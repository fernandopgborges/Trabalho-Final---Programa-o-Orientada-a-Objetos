package br.pucrs.nomeusuario.exemplo.TelasTF;

import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Date;
import java.util.stream.Collectors;

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

import br.pucrs.nomeusuario.exemplo.dados.*;
import br.pucrs.nomeusuario.exemplo.dados.CatalogoClientes;
import br.pucrs.nomeusuario.exemplo.dados.CatalogoContratos;
import br.pucrs.nomeusuario.exemplo.dados.CatalogoFormaPagamento;
import br.pucrs.nomeusuario.exemplo.dados.CatalogoJogos;
import br.pucrs.nomeusuario.exemplo.dados.Cliente;
import br.pucrs.nomeusuario.exemplo.dados.Contrato;
import br.pucrs.nomeusuario.exemplo.dados.FormaPagamento;
import br.pucrs.nomeusuario.exemplo.dados.Jogo;

@Route( "/cadastroContrato" )
public class CadastroContratosView extends VerticalLayout {

    private Button buttonVoltar;
    private Button buttonAdicionar;

    private ComboBox< Cliente > comboClientes;
    private ComboBox< Jogo > comboJogos;
    private ComboBox< FormaPagamento > comboPagamentos;

    private Grid< Contrato > gridContratos;

    private TextField fieldId;
    private TextField fieldPeriodo;

    private DatePicker fieldData;

    private CatalogoClientes catalogoClientes;
    private CatalogoJogos catalogoJogos;
    private CatalogoFormaPagamento catalogoPagamentos;
    private CatalogoContratos catalogoContratos;

    public CadastroContratosView( ) {

        buttonVoltar = new Button( "Voltar" );
        buttonAdicionar = new Button( "Adicionar Contrato" );

        comboClientes = new ComboBox<>( "Clientes" );
        comboJogos = new ComboBox<>( "Jogos Disponíveis" );
        comboPagamentos = new ComboBox<>( "Forma de Pagamento" );

        gridContratos = new Grid<>( Contrato.class, false );

        fieldId = new TextField( "ID do contrato" );
        fieldPeriodo = new TextField( "Período" );
        fieldData = new DatePicker( "Data do contrato" );

        catalogoClientes =
            ( CatalogoClientes ) VaadinSession.getCurrent( ).getAttribute( "catalogoClientes" );

        catalogoJogos =
            ( CatalogoJogos ) VaadinSession.getCurrent( ).getAttribute( "catalogoJogos" );

        catalogoPagamentos =
            ( CatalogoFormaPagamento ) VaadinSession.getCurrent( ).getAttribute( "catalogoFormaPagamento" );

        catalogoContratos =
            ( CatalogoContratos ) VaadinSession.getCurrent( ).getAttribute( "catalogoContratos" );

        if ( catalogoContratos == null ) {
            catalogoContratos = new CatalogoContratos();
        }

        comboClientes.setItems( catalogoClientes.getClientes( ) );
        comboClientes.setItemLabelGenerator( c ->
            c.getNumero( ) + " - " + c.getNome( )
        );

        atualizarJogosDisponiveis( );

        comboJogos.setItemLabelGenerator( j ->
            j.getCodigo( ) + " - " + j.getNome( )
        );

        comboPagamentos.setItems( catalogoPagamentos.getFormasPagamento( ) );
        comboPagamentos.setItemLabelGenerator( p -> {
            String text = String.valueOf( p.getCod( ) ) + "-";
            if ( p instanceof PIX ) text+= "PIX";
            else if ( p instanceof CartaoCredito ) text+= "Cartão de Crédito";
            return text;
        } );

        gridContratos.addColumn( c -> c.getId( ) ).setHeader( "ID" );

        gridContratos.addColumn( c -> {
            Cliente cliente = catalogoClientes.buscarCliente( c.getNumeroCliente( ) );
            return ( cliente != null )
                ? cliente.getNumero( ) + " - " + cliente.getNome( )
                : "Desconhecido";
        } ).setHeader( "Cliente" );

        gridContratos.addColumn( c -> {
            Jogo jogo = catalogoJogos.buscarJogo( c.getCodigoJogo( ) );
            return ( jogo != null )
                ? jogo.getCodigo( ) + " - " + jogo.getNome( )
                : "Desconhecido";
        } ).setHeader( "Jogo" );

        gridContratos.addColumn( c -> {
            FormaPagamento p = catalogoPagamentos.buscarFormaPagamento( c.getCodPagamento( ) );
            return ( p != null )
                ? String.valueOf( p.getCod( ) )
                : "Desconhecido";
        } ).setHeader( "Pagamento" );

        gridContratos.addColumn( c -> c.getPeriodo( ) ).setHeader( "Período" );

        SimpleDateFormat format = new SimpleDateFormat( "dd/MM/yyyy" );
        gridContratos.addColumn( c -> format.format( c.getData( ) ) ).setHeader( "Data" );
        gridContratos.addColumn( c -> {
            String formatada = String.format( "%.2f", c.calculaValorFinal( catalogoContratos ) );
            return formatada;
        }).setHeader( "Valor Final");

        gridContratos.setItems( catalogoContratos.relatorioContrato() );

        buttonAdicionar.addClickListener( e -> {
            if ( fieldId.isEmpty( ) || fieldPeriodo.isEmpty( ) || fieldData.isEmpty( ) ) {
                Notification.show( "Preencha os campos!" );
                return;
            }

            if ( comboClientes.isEmpty( ) || comboJogos.isEmpty( ) || comboPagamentos.isEmpty( ) ) {
                Notification.show( "Selecione cliente, jogo e pagamento!" );
                return;
            }

            int id;
            int periodo;

            try {
                id = Integer.parseInt( fieldId.getValue( ) );
                periodo = Integer.parseInt( fieldPeriodo.getValue( ) );
            } catch ( Exception ex ) {
                Notification.show( "Valores inválidos!" );
                return;
            }

            Date data = Date.from(
                fieldData.getValue( )
                    .atStartOfDay( ZoneId.systemDefault( ) )
                    .toInstant( )
            );

            Cliente cliente = comboClientes.getValue( );
            Jogo jogo = comboJogos.getValue( );
            FormaPagamento pagamento = comboPagamentos.getValue( );

            if ( !jogo.getJogoDisponivel( ) ) {
                Notification.show( "Jogo indisponível!" );
                return;
            }

            Contrato contrato = new Contrato(
                id,
                data,
                periodo,
                cliente,
                jogo,
                pagamento
            );

            catalogoContratos.cadastraContrato( contrato );

            jogo.setJogoDisponivel( false );
            jogo.setContrato( id );

            atualizarJogosDisponiveis();
            comboJogos.clear();

            gridContratos.setItems( catalogoContratos.relatorioContrato() );

            Notification.show( "Contrato adicionado!" );
        });

        buttonVoltar.addClickListener( e -> {
            VaadinSession.getCurrent( ).setAttribute( "catalogoJogos", catalogoJogos );
            VaadinSession.getCurrent( ).setAttribute( "catalogoClientes", catalogoClientes );
            VaadinSession.getCurrent( ).setAttribute( "catalogoFormaPagamento", catalogoPagamentos );
            VaadinSession.getCurrent( ).setAttribute( "catalogoContratos", catalogoContratos );
            UI.getCurrent().navigate( "" );
        } );

        add(
            fieldId,
            fieldPeriodo,
            fieldData,
            comboClientes,
            comboJogos,
            comboPagamentos,
            buttonAdicionar,
            gridContratos,
            buttonVoltar
        );
    }

    private void atualizarJogosDisponiveis( ) {

        var lista = catalogoJogos.getJogos( )
            .stream( )
            .filter( j -> j.getJogoDisponivel( ) )
            .collect( Collectors.toList( ) );

        comboJogos.setItems( lista );

        if ( lista.isEmpty( ) ) {
            Notification.show( "Todos os jogos estão indisponíveis!" );
        }
    }
}
