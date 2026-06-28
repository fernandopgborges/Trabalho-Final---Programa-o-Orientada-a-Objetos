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

import com.vaadin.flow.component.ComponentEvent;
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

@PageTitle("ACMESpiele - Menu")
@Route("")
public class ACMESpiele extends VerticalLayout {
   private Button buttonCadastroCliente;
   private Button buttonCadastroJogo;
   private Button buttonCadastroPagamento;
   private Button buttonCadastroContrato;

    public ACMESpiele() {
      inicializar();

      setAlignItems( Alignment.CENTER );

      H1 titulo = new H1( "ACMESpiele - Menu Principal" );
      Hr linha = new Hr();
      linha.getStyle().set("border-top", "2px solid blue");
      linha.setWidth("75%");

      add( titulo );
      add( linha );

      // aqui começa os Botões que vão servir como os menus do T1

      //Instanciando os Botões

      buttonCadastroCliente = new Button( "Cadastrar Ciente" );
      buttonCadastroJogo = new Button( "Cadastrar Jogo" );
      buttonCadastroPagamento = new Button( "Cadastrar Forma de Pagamento" );
      buttonCadastroContrato = new Button( "Cadastrar Contrato" );

      // Tratamento dos Botões
      var tratadorBotoes = new TratadorEventosBotoes();
      buttonCadastroCliente.addClickListener( tratadorBotoes );
      buttonCadastroJogo.addClickListener( tratadorBotoes );
      buttonCadastroPagamento.addClickListener( tratadorBotoes );
      buttonCadastroContrato.addClickListener( tratadorBotoes );

      // Adicionando os Botões no layout
      add( buttonCadastroCliente );
      add( buttonCadastroJogo );
      add( buttonCadastroPagamento );
      add( buttonCadastroContrato );
   }

   public void inicializar() {
      lerArquivoClientes( "ArquivosLeitura/CLIENTESINICIAL.CSV" );
      lerArquivoJogos( "ArquivosLeitura/JOGOSINICIAL.CSV" );
      lerAquivoPagamento( "ArquivosLeitura/FORMASPAGAMENTOINICIAL.CSV" );
      lerArquivoContratos( "ArquivosLeitura/CONTRATOSINICIAL.CSV" );
   }
   
   public void lerArquivoClientes( String pathStr ) {
      CatalogoClientes catalogoClientes = ( CatalogoClientes )VaadinSession.getCurrent().getAttribute( "catalogoClientes" );

      if ( catalogoClientes == null ) {
         catalogoClientes = new CatalogoClientes();
      }

      Path pathClientes = Paths.get( pathStr );
      try ( BufferedReader reader =
            Files.newBufferedReader( pathClientes, Charset.forName( "UTF-8" ) ) ) {

         String line = null;

         while ( ( line = reader.readLine() ) != null ) {

            String[] partes = line.split( ";" );

            try {

               if ( partes.length < 5 ) {
                  continue; // quando linha nao puder ser dividida em pelo menos 5 partes
               }

               int numero = Integer.parseInt(partes[0]);
               
               String nome = partes[1];
               String email = partes[2];
               int tipo = Integer.parseInt(partes[3]);
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

               boolean cadastroCheck = catalogoClientes.cadastrarCliente(cliente);

               if ( cadastroCheck ) {
                  Notification.show("Cliente carregado: " + nome);
               }

            } catch (NumberFormatException e) {
               System.out.println("Linha inválida ignorada: " + line);
            }
         }

         VaadinSession.getCurrent().setAttribute("catalogoClientes", catalogoClientes );

         Notification.show("Clientes carregados com sucesso!");

      } catch (IOException e) {
         Notification.show("Erro: " + e.getMessage());
      }
   }

   public void lerArquivoJogos( String pathStr ) {
      CatalogoJogos catalogoJogos = ( CatalogoJogos )VaadinSession.getCurrent().getAttribute( "catalogoJogos" );

      if ( catalogoJogos == null ) {
         catalogoJogos = new CatalogoJogos();
      }

      Path pathJogos = Paths.get( pathStr );
      try ( BufferedReader reader =
            Files.newBufferedReader( pathJogos, Charset.forName( "UTF-8" ) ) ) {

         String line = null;
         while ( ( line = reader.readLine() ) != null ) {
            String[] partes = line.split( ";" );

            try {
               if ( partes.length < 5 ) {
                  continue; // quando linha nao puder ser dividida em pelo menos 5 partes
               }

               int codigo = Integer.parseInt( partes[0] );
               
               String nome = partes[1];
               int ano = Integer.parseInt( partes[2] );
               double valorMensal = Double.parseDouble( partes[3] );
               Categoria categoria = Categoria.valueOf( partes[4] );

               Jogo jogo = new Jogo( codigo, nome, ano, valorMensal, categoria );

               boolean cadastroCheck = catalogoJogos.cadastrarJogo( jogo );

               if ( cadastroCheck ) {
                  Notification.show("Jogo carregado: " + nome);
               }

            } catch ( NumberFormatException e ) {
               System.out.println( "Linha inválida ignorada: " + line );
            }
         }

         VaadinSession.getCurrent().setAttribute("catalogoJogos", catalogoJogos );

         Notification.show("Jogos carregados com sucesso!");

      } catch (IOException e) {
         Notification.show("Erro: " + e.getMessage());
      }
   }

   public void lerAquivoPagamento( String pathStr) {
      CatalogoFormaPagamento catalogoFormaPagamento = ( CatalogoFormaPagamento )VaadinSession.getCurrent().getAttribute( "catalogoFormaPagamento" );

      if ( catalogoFormaPagamento == null ) {
         catalogoFormaPagamento = new CatalogoFormaPagamento();
      }

      Path pathPagamentos = Paths.get( pathStr );
      try ( BufferedReader reader =
            Files.newBufferedReader( pathPagamentos, Charset.forName( "UTF-8" ) ) ) {

         String line = null;
         while ( ( line = reader.readLine() ) != null ) {
            String[] partes = line.split( ";" );

            try {
               if ( partes.length < 5 ) {
                  continue;
               }

               int codigo = Integer.parseInt( partes[0] );
               int diaVencimento = Integer.parseInt( partes[1] );
               int numeroCliente = Integer.parseInt( partes[2] ); // numero do cliente registrado
               int tipo = Integer.parseInt( partes[3] );
               String chave = partes[4];

               FormaPagamento novaForma;

               SimpleDateFormat format = new SimpleDateFormat( "dd/MM/yyyy" );

               if ( tipo == 1 ) {

                  // CARTÃO (tem validade)
                  if ( partes.length < 6 ) {
                     continue;
                  }
                  
                  Date validade = null;

                  try {
                     validade = format.parse( partes[5] );
                  } catch ( ParseException e ) {
                     System.out.println( "Data inválida na linha: " + line );
                     continue;
                  }

                  novaForma = new CartaoCredito(
                     codigo,
                     diaVencimento,
                     numeroCliente,
                     chave,
                     validade
                  );

               } else {

                  // PIX (não tem validade)
                  novaForma = new PIX(
                     codigo,
                     numeroCliente,
                     diaVencimento,
                     chave
                  );
               }

               boolean cadastroCheck = catalogoFormaPagamento.cadastraFormaPagamento( novaForma );

               if ( cadastroCheck ) {
                  Notification.show("Pagamento carregado: " + codigo );
               }

            } catch ( NumberFormatException e ) {
               System.out.println( "Linha inválida ignorada: " + line );
            }
         }

         VaadinSession.getCurrent().setAttribute("catalogoFormaPagamento", catalogoFormaPagamento );

         Notification.show("Pagamentos carregados com sucesso!");

      } catch (IOException e) {
         Notification.show("Erro: " + e.getMessage());
      }
   }

   public void lerArquivoContratos( String pathStr ) {
      Queue<Contrato> filaContratos =
         ( Queue<Contrato> ) VaadinSession.getCurrent().getAttribute( "filaContratos" );

      CatalogoJogos catalogoJogos =
         ( CatalogoJogos ) VaadinSession.getCurrent().getAttribute( "catalogoJogos" );

      if ( filaContratos == null ) {
         filaContratos = new LinkedList<>();
      }

      if ( catalogoJogos == null ) {
         catalogoJogos = new CatalogoJogos();
      }

      Path path = Paths.get( pathStr );
      try ( BufferedReader reader =
               Files.newBufferedReader( path, Charset.forName( "UTF-8" ) ) ) {

         String line = null;
         SimpleDateFormat format = new SimpleDateFormat( "dd/MM/yyyy" );

         while ( ( line = reader.readLine() ) != null ) {

               String[] partes = line.split( ";" );

               if ( partes.length < 6 ) {
                  continue;
               }

               try {

                  int id = Integer.parseInt( partes[0].trim() );
                  
                  boolean jaExiste = false;

                  for ( Contrato c : filaContratos ) {
                     if ( c.getId() == id ) {
                        jaExiste = true;
                        break;
                     }
                  }
                  if ( jaExiste ) {
                     continue;
                  }

                  int periodo = Integer.parseInt( partes[2].trim() );
                  int numeroCliente = Integer.parseInt( partes[3].trim() );
                  int codigoJogo = Integer.parseInt( partes[4].trim() );
                  int codigoPagamento = Integer.parseInt( partes[5].trim() );

                  Date data;
                  try {
                     data = format.parse( partes[1].trim() );
                  } catch ( ParseException e ) {
                     continue;
                  }

                  Contrato novoContrato = new Contrato(
                     id,
                     data,
                     periodo,
                     numeroCliente,
                     codigoJogo,
                     codigoPagamento
                  );
                  if ( filaContratos.contains( novoContrato ) ) {
                     continue;
                  }

                  filaContratos.add( novoContrato );

                  Jogo jogo = catalogoJogos.buscarJogo( codigoJogo );
                  if ( jogo != null ) {
                     jogo.setJogoDisponivel( false );
                     jogo.setContrato( novoContrato.getId() );
                  }

                  Notification.show( "Contrato carregado: " + id );

               } catch ( NumberFormatException e ) {
               }
         }

         VaadinSession.getCurrent().setAttribute( "filaContratos", filaContratos );
         VaadinSession.getCurrent().setAttribute( "catalogoJogos", catalogoJogos );

         Notification.show( "Contratos carregados com sucesso!" );

      } catch ( IOException e ) {
         Notification.show( "Erro: " + e.getMessage() );
      }
   }

   class TratadorEventosBotoes implements ComponentEventListener {

      @Override 
      public void onComponentEvent( ComponentEvent event ) {
         var sourceEvento = event.getSource();
         String path = "";

         if ( sourceEvento == buttonCadastroCliente ) {
            path = "/cadastroCliente";
         } else if ( sourceEvento == buttonCadastroJogo ) {
            path = "/cadastroJogo";
         } else if ( sourceEvento == buttonCadastroPagamento ) {
            path = "/cadastroPagamento";
         } else if ( sourceEvento == buttonCadastroContrato ) {
            path = "/cadastroContrato";
         }

         UI.getCurrent().navigate( path );
      }
   }
   
}
