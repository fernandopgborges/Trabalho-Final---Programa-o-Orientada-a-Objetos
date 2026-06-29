package br.pucrs.nomeusuario.exemplo;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

import br.pucrs.nomeusuario.exemplo.dados.CartaoCredito;
import br.pucrs.nomeusuario.exemplo.dados.CatalogoClientes;
import br.pucrs.nomeusuario.exemplo.dados.CatalogoContratos;
import br.pucrs.nomeusuario.exemplo.dados.CatalogoFormaPagamento;
import br.pucrs.nomeusuario.exemplo.dados.CatalogoJogos;
import br.pucrs.nomeusuario.exemplo.dados.Categoria;
import br.pucrs.nomeusuario.exemplo.dados.Cliente;
import br.pucrs.nomeusuario.exemplo.dados.Contrato;
import br.pucrs.nomeusuario.exemplo.dados.Corporativo;
import br.pucrs.nomeusuario.exemplo.dados.FormaPagamento;
import br.pucrs.nomeusuario.exemplo.dados.Individual;
import br.pucrs.nomeusuario.exemplo.dados.Jogo;
import br.pucrs.nomeusuario.exemplo.dados.PIX;

@PageTitle( "ACMESpiele - Menu" )
@Route( "" )
public class ACMESpiele extends VerticalLayout {
   private Button buttonLerArquivosIniciais;

   private Button buttonCadastroCliente;
   private Button buttonCadastroJogo;
   private Button buttonCadastroPagamento;
   private Button buttonCadastroContrato;

   private Button buttonRelatorioCliente;
   private Button buttonRelatorioJogo;
   private Button buttonRelatorioContrato;

   private CatalogoClientes catalogoClientes;
   private CatalogoJogos catalogoJogos;
   private CatalogoFormaPagamento catalogoFormaPagamento;
   private CatalogoContratos catalogoContratos;

   public ACMESpiele() {
      catalogoClientes = (CatalogoClientes) VaadinSession.getCurrent().getAttribute( "catalogoClientes" );

      catalogoJogos = (CatalogoJogos) VaadinSession.getCurrent().getAttribute( "catalogoJogos" );

      catalogoFormaPagamento = (CatalogoFormaPagamento) VaadinSession.getCurrent().getAttribute( "catalogoFormaPagamento" );

      catalogoContratos = (CatalogoContratos) VaadinSession.getCurrent().getAttribute( "catalogoContratos" );
      
      setAlignItems( Alignment.CENTER );

      H1 titulo = new H1( "ACMESpiele - Menu Principal" );
      Hr linha = new Hr();
      linha.getStyle().set( "border-top", "2px solid blue" );
      linha.setWidth( "75%" );

      add( titulo );
      add( linha );

      buttonLerArquivosIniciais = new Button( "Ler arquivos base" );

      buttonCadastroCliente = new Button( "Cadastrar Ciente" );
      buttonCadastroJogo = new Button( "Cadastrar Jogo" );
      buttonCadastroPagamento = new Button( "Cadastrar Forma de Pagamento" );
      buttonCadastroContrato = new Button( "Cadastrar Contrato" );

      buttonRelatorioCliente = new Button( "Ver Relatório de Clientes" );
      buttonRelatorioJogo = new Button( "Ver Relatório de Jogos" );
      buttonRelatorioContrato = new Button( "Ver Relatório de Contratos" );

      buttonLerArquivosIniciais.addClickListener( e -> {
         inicializar();
         Notification.show( "Arquivos Iniciais Lidos!" );
      } );

      var tratadorBotoes = new TratadorEventosBotoes();
      buttonCadastroCliente.addClickListener( tratadorBotoes );
      buttonCadastroJogo.addClickListener( tratadorBotoes );
      buttonCadastroPagamento.addClickListener( tratadorBotoes );
      buttonCadastroContrato.addClickListener( tratadorBotoes );

      buttonRelatorioCliente.addClickListener( tratadorBotoes );
      buttonRelatorioJogo.addClickListener( tratadorBotoes );
      buttonRelatorioContrato.addClickListener( tratadorBotoes );

      add( buttonLerArquivosIniciais );

      add( buttonCadastroCliente );
      add( buttonCadastroJogo );
      add( buttonCadastroPagamento );
      add( buttonCadastroContrato );

      add( buttonRelatorioCliente, buttonRelatorioJogo, buttonRelatorioContrato );
   }

   public void inicializar() {
      lerArquivoClientes( "ArquivosLeitura/CLIENTESINICIAL.CSV" );
      lerArquivoJogos( "ArquivosLeitura/JOGOSINICIAL.CSV" );
      lerAquivoPagamento( "ArquivosLeitura/FORMASPAGAMENTOINICIAL.CSV" );
      lerArquivoContratos( "ArquivosLeitura/CONTRATOSINICIAL.CSV" );
   }

   public void lerArquivoClientes( String pathStr ) {
      if ( catalogoClientes == null ) {
         catalogoClientes = new CatalogoClientes();
      }

      Path pathClientes = Paths.get( pathStr );

      try ( BufferedReader reader =
            Files.newBufferedReader( pathClientes, Charset.forName( "UTF-8" ) ) ) {

         String line;

         while ( ( line = reader.readLine() ) != null ) {

            String[] partes = line.split( ";" );

            try {

               if ( partes.length < 5 ) continue;

               int numero = Integer.parseInt( partes[0] );
               String nome = partes[1];
               String email = partes[2];
               int tipo = Integer.parseInt( partes[3] );
               String doc = partes[4];
               String nomeFantasia = ( partes.length > 5 ) ? partes[5] : "";

               Cliente cliente = null;

               switch ( tipo ) {
                  case 1:
                     cliente = new Individual( numero, nome, email, doc );
                     break;
                  case 2:
                     cliente = new Corporativo( numero, nome, email, doc, nomeFantasia );
                     break;
                  default:
                     continue;
               }

               catalogoClientes.cadastrarCliente( cliente );

            } catch ( NumberFormatException e ) {
               System.out.println( "Linha inválida ignorada: " + line );
            }
         }

         VaadinSession.getCurrent().setAttribute( "catalogoClientes", catalogoClientes );

      } catch ( IOException e ) {
         Notification.show( "Erro: " + e.getMessage() );
      }
   }

   public void lerArquivoJogos( String pathStr ) {
      if ( catalogoJogos == null ) {
         catalogoJogos = new CatalogoJogos();
      }

      Path pathJogos = Paths.get( pathStr );

      try ( BufferedReader reader =
            Files.newBufferedReader( pathJogos, Charset.forName( "UTF-8" ) ) ) {

         String line;

         while ( ( line = reader.readLine() ) != null ) {

            String[] partes = line.split( ";" );

            try {

               if ( partes.length < 5 ) continue;

               int codigo = Integer.parseInt( partes[0] );
               String nome = partes[1];
               int ano = Integer.parseInt( partes[2] );
               double valorMensal = Double.parseDouble( partes[3] );
               Categoria categoria = Categoria.valueOf( partes[4] );

               Jogo jogo = new Jogo( codigo, nome, ano, valorMensal, categoria );

               catalogoJogos.cadastrarJogo( jogo );

            } catch ( NumberFormatException e ) {
               System.out.println( "Linha inválida ignorada: " + line );
            }
         }

         VaadinSession.getCurrent().setAttribute( "catalogoJogos", catalogoJogos );

      } catch ( IOException e ) {
         Notification.show( "Erro: " + e.getMessage() );
      }
   }

   public void lerAquivoPagamento( String pathStr ) {
      if ( catalogoFormaPagamento == null ) {
         catalogoFormaPagamento = new CatalogoFormaPagamento();
      }

      Path pathPagamentos = Paths.get( pathStr );

      try ( BufferedReader reader =
            Files.newBufferedReader( pathPagamentos, Charset.forName( "UTF-8" ) ) ) {

         String line;

         while ( ( line = reader.readLine() ) != null ) {

            String[] partes = line.split( ";" );

            try {

               if ( partes.length < 5 ) continue;

               int codigo = Integer.parseInt( partes[0] );
               int diaVencimento = Integer.parseInt( partes[1] );
               int numeroCliente = Integer.parseInt( partes[2] );
               int tipo = Integer.parseInt( partes[3] );
               String chave = partes[4];

               FormaPagamento novaForma;

               SimpleDateFormat format = new SimpleDateFormat( "dd/MM/yyyy" );

               if ( tipo == 1 ) {

                  if ( partes.length < 6 ) continue;

                  Date validade;

                  try {
                     validade = format.parse( partes[5] );
                  } catch ( ParseException e ) {
                     continue;
                  }

                  novaForma = new CartaoCredito(
                     codigo, diaVencimento, numeroCliente, chave, validade
                  );

               } else {

                  novaForma = new PIX(
                     codigo, numeroCliente, diaVencimento, chave
                  );
               }

               catalogoFormaPagamento.cadastraFormaPagamento( novaForma );

            } catch ( NumberFormatException e ) {
               System.out.println( "Linha inválida ignorada: " + line );
            }
         }

         VaadinSession.getCurrent().setAttribute( "catalogoFormaPagamento", catalogoFormaPagamento );

      } catch ( IOException e ) {
         Notification.show( "Erro: " + e.getMessage() );
      }
   }

   public void lerArquivoContratos( String pathStr ) {
      if ( catalogoContratos == null ) {
         catalogoContratos = new CatalogoContratos();
      }

      catalogoClientes = (CatalogoClientes) VaadinSession.getCurrent().getAttribute( "catalogoClientes" );
      catalogoJogos = (CatalogoJogos) VaadinSession.getCurrent().getAttribute( "catalogoJogos" );
      catalogoFormaPagamento = (CatalogoFormaPagamento) VaadinSession.getCurrent().getAttribute( "catalogoFormaPagamento" );

      Queue<Contrato> filaContratos = new LinkedList<>();

      Path path = Paths.get( pathStr );

      try ( BufferedReader reader =
            Files.newBufferedReader( path, Charset.forName( "UTF-8" ) ) ) {

         String line;
         SimpleDateFormat format = new SimpleDateFormat( "dd/MM/yyyy" );

         while ( ( line = reader.readLine() ) != null ) {

            String[] partes = line.split( ";" );

            if ( partes.length < 6 ) continue;

            try {

               int id = Integer.parseInt( partes[0].trim() );

               boolean jaExiste = false;
               for ( Contrato c : filaContratos ) {
                  if ( c.getId() == id ) {
                     jaExiste = true;
                     break;
                  }
               }
               if ( jaExiste ) continue;

               Date data = format.parse( partes[1].trim() );
               int periodo = Integer.parseInt( partes[2].trim() );
               int numeroCliente = Integer.parseInt( partes[3].trim() );
               int codigoJogo = Integer.parseInt( partes[4].trim() );
               int codigoPagamento = Integer.parseInt( partes[5].trim() );

               Cliente cliente = catalogoClientes.buscarCliente( numeroCliente );
               if ( cliente == null ) continue;

               Jogo jogo = catalogoJogos.buscarJogo( codigoJogo );
               if ( jogo == null || !jogo.getJogoDisponivel() ) continue;

               FormaPagamento formaPagamento =
                     catalogoFormaPagamento.buscarFormaPagamento( codigoPagamento );
               if ( formaPagamento == null ) continue;

               Contrato contrato = new Contrato(
                     id, data, periodo, cliente, jogo, formaPagamento
               );

               jogo.setJogoDisponivel( false );
               jogo.setContrato( id );

               filaContratos.add( contrato );

            } catch ( Exception e ) {
               System.out.println( "Linha inválida: " + line );
            }
         }

         for ( Contrato c : filaContratos ) {
            catalogoContratos.cadastraContrato( c );
         }

         VaadinSession.getCurrent().setAttribute( "catalogoContratos", catalogoContratos );
         VaadinSession.getCurrent().setAttribute( "catalogoJogos", catalogoJogos );

      } catch ( IOException e ) {
         Notification.show( "Erro: " + e.getMessage() );
      }
   }

   class TratadorEventosBotoes implements ComponentEventListener<ClickEvent<Button>> {

      @Override 
      public void onComponentEvent( ClickEvent<Button> event ) {
         var sourceEvento = event.getSource();
         String path = "";

         CatalogoClientes sClientes = (CatalogoClientes) VaadinSession.getCurrent().getAttribute( "catalogoClientes" );
         CatalogoJogos sJogos = (CatalogoJogos) VaadinSession.getCurrent().getAttribute( "catalogoJogos" );
         CatalogoContratos sContratos = (CatalogoContratos) VaadinSession.getCurrent().getAttribute( "catalogoContratos" );

         if ( sourceEvento == buttonCadastroCliente ) {
            path = "cadastroCliente";
         } else if ( sourceEvento == buttonCadastroJogo ) {
            path = "cadastroJogo";
         } else if ( sourceEvento == buttonCadastroPagamento ) {
            path = "cadastroPagamento";
         } else if ( sourceEvento == buttonCadastroContrato ) {
            path = "cadastroContrato";
         } else if ( sourceEvento == buttonRelatorioCliente ) {
            if ( sClientes == null || sClientes.getClientes().isEmpty() ) {
               Notification.show( "Nenhum cliente cadastrado!" );
               return;
            }
            path = "relatorioCliente";
         } else if ( sourceEvento == buttonRelatorioJogo ) {
            if ( sJogos == null || sJogos.getJogos().isEmpty() ) {
               Notification.show( "Nenhum jogo cadastrado!" );
               return;
            }
            path = "relatorioJogo";
         } else if ( sourceEvento == buttonRelatorioContrato ) {
            if ( sContratos == null || sContratos.relatorioContrato().isEmpty() ) {
               Notification.show( "Nenhum contrato cadastrado!" );
               return;
            }
            path = "relatorioContrato";
         }

         UI.getCurrent().navigate( path );
      }
   }
}