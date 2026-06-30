package br.pucrs.nomeusuario.exemplo.TelasTF;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

import br.pucrs.nomeusuario.exemplo.dados.CatalogoClientes;
import br.pucrs.nomeusuario.exemplo.dados.CatalogoContratos;
import br.pucrs.nomeusuario.exemplo.dados.CatalogoJogos;
import br.pucrs.nomeusuario.exemplo.dados.Cliente;
import br.pucrs.nomeusuario.exemplo.dados.Contrato;
import br.pucrs.nomeusuario.exemplo.dados.Jogo;

@Route( "/consultas" )
public class ConsultasView extends VerticalLayout {

    private Button buttonVoltar;

    public ConsultasView( ) {

        Button btnJogo = new Button( "Jogo com maior valor diário" );
        Button btnContrato = new Button( "Contrato com maior valor final" );
        Button btnCliente = new Button( "Cliente com maior montante" );

        buttonVoltar = new Button( "Voltar" );
        buttonVoltar.addClickListener( e -> UI.getCurrent().navigate( "" ) );

        add( btnJogo, btnContrato, btnCliente, buttonVoltar );

        btnJogo.addClickListener( e -> {

            CatalogoJogos catalogoJogos =
                ( CatalogoJogos ) VaadinSession.getCurrent( ).getAttribute( "catalogoJogos" );

            if ( catalogoJogos == null || catalogoJogos.getJogos().isEmpty( ) ) {
                Notification.show( "Nenhum jogo encontrado!" );
                return;
            }

            double maior = -1;
            List<Jogo> maiores = new ArrayList<>( );

            for ( Jogo j : catalogoJogos.getJogos() ) {

                if ( j.getValorDiario( ) > maior ) {
                maior = j.getValorDiario( );
                maiores.clear( );
                maiores.add( j );
                } else if ( j.getValorDiario( ) == maior ) {
                maiores.add( j );
                }
            }

            String msg = "Maior valor diário: \n";
            for ( Jogo j : maiores ) {
                msg += j.getNome( ) + " - R$ " + j.getValorDiario( ) + "\n";
            }

            Notification.show( msg );
        });

        btnContrato.addClickListener( e -> {

            CatalogoContratos catalogoContratos =
                ( CatalogoContratos ) VaadinSession.getCurrent( ).getAttribute( "catalogoContratos" );

            if ( catalogoContratos == null || catalogoContratos.relatorioContrato().isEmpty( ) ) {
                Notification.show( "Nenhum contrato encontrado!" );
                return;
            }

            double maior = -1;
            List<Contrato> maiores = new ArrayList<>( );

            for ( Contrato c : catalogoContratos.relatorioContrato() ) {

                double valor = c.calculaValorFinal(catalogoContratos);

                if ( valor > maior ) {
                maior = valor;
                maiores.clear( );
                maiores.add( c );
                } else if ( valor == maior ) {
                maiores.add( c );
                }
            }

            String msg = "Maior valor de contrato: \n";
            for ( Contrato c : maiores ) {
                msg += "Contrato " + c.getId( ) + " - R$ " + c.calculaValorFinal(catalogoContratos) + "\n";
            }

            Notification.show( msg );
        });

        btnCliente.addClickListener( e -> {

            CatalogoClientes catalogoClientes =
                ( CatalogoClientes ) VaadinSession.getCurrent( ).getAttribute( "catalogoClientes" );

            CatalogoContratos catalogoContratos =
                ( CatalogoContratos ) VaadinSession.getCurrent( ).getAttribute( "catalogoContratos" );

            if ( catalogoClientes == null || catalogoClientes.getClientes().isEmpty( ) ||
                catalogoContratos == null || catalogoContratos.relatorioContrato().isEmpty( ) ) {

                Notification.show( "Dados insuficientes!" );
                return;
            }

            double maior = -1;
            List<Cliente> melhores = new ArrayList<>( );

            for ( Cliente cliente : catalogoClientes.getClientes() ) {

                double soma = 0;

                for ( Contrato c : catalogoContratos.relatorioContrato()) {
                if ( c.getCliente( ).getNumero( ) == cliente.getNumero( ) ) {
                    soma += c.calculaValorFinal(catalogoContratos);
                }
                }

                if ( soma > maior ) {
                maior = soma;
                melhores.clear( );
                melhores.add( cliente );
                } else if ( soma == maior && soma > 0 ) {
                melhores.add( cliente );
                }
            }

            if ( maior <= 0 ) {
                Notification.show( "Nenhum cliente com contratos!" );
                return;
            }

            String msg = "Cliente(s) com maior montante: \n";
            for ( Cliente c : melhores ) {
                msg += c.getNome( ) + " - R$ " + maior + "\n";
            }

            Notification.show( msg );
        });
    }
}