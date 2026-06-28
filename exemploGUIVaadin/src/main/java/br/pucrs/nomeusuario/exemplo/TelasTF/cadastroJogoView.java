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

import br.pucrs.nomeusuario.exemplo.dados.CatalogoJogos;
import br.pucrs.nomeusuario.exemplo.dados.Categoria;
import br.pucrs.nomeusuario.exemplo.dados.Jogo;
@Route( "/cadastroJogo" )
public class cadastroJogoView extends VerticalLayout {
    private Button buttonVoltar;
    private Button buttonAdicionar;
    private Grid<Jogo> gridJogos;
    private TextField fieldCodigo;
    private TextField fieldName;
    private TextField fieldAno;
    private TextField fieldValorDiario;

    private ComboBox<Categoria> comboCategoria;

    public cadastroJogoView() {
        buttonVoltar = new Button( "Voltar" );
        gridJogos = new Grid<Jogo>( Jogo.class, false );

        CatalogoJogos catalogoTmp =
            ( CatalogoJogos ) VaadinSession.getCurrent().getAttribute( "catalogoJogos" );

        if ( catalogoTmp == null ) {
            catalogoTmp = new CatalogoJogos();
        }

        final CatalogoJogos catalogoJogos = catalogoTmp;

        comboCategoria = new ComboBox<>( "Categoria do Jogo" );
        comboCategoria.setItems( Categoria.values() );

        fieldCodigo      = new TextField( "Código" );
        fieldName        = new TextField( "Nome:" );
        fieldAno         = new TextField( "Ano:" );
        fieldValorDiario = new TextField( "Valor Diário:" );

        buttonAdicionar = new Button( "Adicionar Jogo" );

        buttonAdicionar.addClickListener( e -> {
            String codigoStr = fieldCodigo.getValue();
            String nome      = fieldName.getValue();
            String anoStr    = fieldAno.getValue();
            String valorStr  = fieldValorDiario.getValue();
            Categoria categoria = comboCategoria.getValue();

            if ( codigoStr.isEmpty() || nome.isEmpty() || anoStr.isEmpty()
                    || valorStr.isEmpty() || comboCategoria.isEmpty() ) {

                Notification.show( "Preencha todos os campos!" );
                return;
            }

            int codigo;
            int ano;
            double valorDiario;

            try {
                codigo = Integer.parseInt( codigoStr );
            } catch ( NumberFormatException ex ) {
                Notification.show( "Código inválido!" );
                return;
            }

            try {
                ano = Integer.parseInt( anoStr );
            } catch ( NumberFormatException ex ) {
                Notification.show( "Ano inválido!" );
                return;
            }

            try {
                valorDiario = Double.parseDouble( valorStr );
            } catch ( NumberFormatException ex ) {
                Notification.show( "Valor mensal inválido!" );
                return;
            }

            Jogo j = new Jogo( codigo, nome, ano, valorDiario, categoria );

            boolean cadastroCheck = catalogoJogos.cadastrarJogo( j );

            String mensagem = "Erro ao cadastrar: Código repetido!";

            if ( cadastroCheck ) {
                mensagem = "Jogo cadastrado com sucesso!";
                gridJogos.setItems( catalogoJogos.getJogos() );
            }

            Notification.show( mensagem );
        });

        buttonVoltar.addClickListener( e -> {
            VaadinSession.getCurrent().setAttribute( "catalogoJogos", catalogoJogos );
            UI.getCurrent().navigate( "" );
        });

        // GRID CORRIGIDA
        gridJogos.addColumn( c -> c.getCodigo() ).setHeader( "Código" );
        gridJogos.addColumn( c -> c.getNome() ).setHeader( "Nome" );
        gridJogos.addColumn( c -> c.getAno() ).setHeader( "Ano" );
        gridJogos.addColumn( c -> c.getValorDiario() ).setHeader( "Valor Diário" );
        gridJogos.addColumn( c -> c.getCategoria().name() ).setHeader( "Categoria" );
        gridJogos.addColumn( c -> {
            if ( c.getJogoDisponivel() ) {
                return "Disponível";
            } else {
                return "Indisponível - Código: " + c.getContrato();
            }
        } ).setHeader( "Disponível" );

        gridJogos.setItems( catalogoJogos.getJogos() );

        add( fieldCodigo );
        add( fieldName );
        add( fieldAno );
        add( fieldValorDiario );
        add( comboCategoria );
        add( buttonAdicionar );
        add( gridJogos );
        add( buttonVoltar );
    }
}