package br.pucrs.nomeusuario.exemplo;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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

      // Adicionando os Botões no layout

      add( buttonCadastroCliente );
      add( buttonCadastroJogo );
      add( buttonCadastroPagamento );
      add( buttonCadastroContrato );
   }

   public void inicializar() {

      

      // Lê clientes do CLIENTESINICIAL.CSV
      Path pathClientes = Paths.get( "ArquivosLeitura/CLIENTESINICIAL.CSV" );
      try ( BufferedReader reader = 
         Files.newBufferedReader( pathClientes, Charset.forName( "UTF-8" ) ) )  {
            String line = null;
            while ( ( line = reader.readLine() ) != null ) {
               Notification.show( "Linha lida:" + line );
            } 
      } catch ( IOException e ) {
         System.err.format( "Erro de Entrada/Saída : %s%n", e );
      }

   }

   class TratadorEventosBotoes implements ComponentEventListener {
      @Override 
      public void onComponentEvent( ComponentEvent event ) {
         var sourceEvento = event.getSource();
         String path = "";

         if ( sourceEvento == buttonCadastroCliente ) {
            path = "/cadastroCliente";
         }

         UI.getCurrent().navigate( path );
      }
   }
   
}
