package app;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

import dados.Categoria;
import dados.Cliente;
import dados.Contrato;
import dados.Corporativo;
import dados.Individual;
import dados.Jogo;

public class ACMESpieleVelha {

    private Scanner entrada = new Scanner(System.in);  // Atributo para entrada padrao (teclado)
    private PrintStream saidaPadrao = System.out;   // Guarda a saida padrao - tela (console)
    private final String nomeArquivoEntrada = "datain.txt";  // Nome do arquivo de entrada de dados
    private final String nomeArquivoSaida = "dataout.txt";  // Nome do arquivo de saida de dados
    
    private ArrayList <Cliente> listaClientes;
    private ArrayList <Jogo> listaJogos;
    private ArrayList <Contrato> listaContratos;

    public ACMESpieleVelha ()
    {
        redirecionaEntrada();    // Redireciona Entrada para arquivos
        redirecionaSaida();    // Redireciona Saida para arquivos
        
        listaClientes = new ArrayList<>();
        listaJogos = new ArrayList<>();
        listaContratos = new ArrayList<>();
    }

    public void executar()
    {
        cadastrarClientesIndividuais();
        cadastrarClientesCorporativos();
        cadastrarJogos();
        cadastrarContratos();
        consultarJogoCodigo();
        consultarJogoCategoria();
        mudarNomeCliente();
        removerContratosJogo();
        listaTodosContratos();
        mostraClienteMaiorValor();
    }

    private void cadastrarClientesIndividuais ()
    {
        String linha = "";
        int numeroCliente= 0;
        String nomeCliente;
        String emailCliente;
        String cpfCliente;


         linha = entrada.nextLine();

        while(!linha.equals("-1"))
        {
            numeroCliente = Integer.parseInt(linha);
            linha = entrada.nextLine();
            nomeCliente = linha;
            linha = entrada.nextLine();
            emailCliente = linha;
            linha = entrada.nextLine();
            cpfCliente = linha;
            linha = entrada.nextLine();

            boolean numRepetido = false;

            numRepetido = clienteExiste(numeroCliente);

            if(numRepetido==true)
            {
                System.out.println("1:erro-numero repetido.");
            }
            else
            {
                Cliente novoCliente = new Individual(numeroCliente, nomeCliente, emailCliente, cpfCliente);
                listaClientes.add(novoCliente);
                System.out.println("1:" + novoCliente.descrever());
            }
        }

        
    }

    private void cadastrarClientesCorporativos ()
    {
        String linha = "";
        int numeroCliente= 0;
        String nomeCliente;
        String emailCliente;
        String cnpjCliente;
        String nomeFantasia;


         linha = entrada.nextLine();

        while(!linha.equals("-1"))
        {
            numeroCliente = Integer.parseInt(linha);
            linha = entrada.nextLine();
            nomeCliente = linha;
            linha = entrada.nextLine();
            emailCliente = linha;
            linha = entrada.nextLine();
            cnpjCliente = linha;
            linha = entrada.nextLine();
            nomeFantasia = linha;
            linha = entrada.nextLine();

            boolean numRepetido = false;

            numRepetido = clienteExiste(numeroCliente);

            if(numRepetido==true)
            {
                System.out.println("2:erro-numero repetido.");
            }
            else
            {
                Cliente novoCliente = new Corporativo(numeroCliente, nomeCliente, emailCliente, cnpjCliente, nomeFantasia);
                listaClientes.add(novoCliente);
                System.out.println("2:" + novoCliente.descrever());
            }
        }

        
    }

    private void cadastrarJogos()
    {
        String linha = "";
        int codigoJogo= 0;
        String nomeJogo;
        int anoJogo;
        double valorMinuto;
        Categoria categoriaJogo = null;
        String categoriaLida;


         linha = entrada.nextLine();

        while(!linha.equals("-1"))
        {
            codigoJogo = Integer.parseInt(linha);
            linha = entrada.nextLine();
            nomeJogo = linha;
            linha = entrada.nextLine();
            anoJogo = Integer.parseInt(linha);
            linha = entrada.nextLine();
            valorMinuto = Double.parseDouble(linha);
            linha = entrada.nextLine();
            categoriaLida = linha;
            linha = entrada.nextLine();

            boolean numRepetido = false;
            boolean categoriaValida= true;

            numRepetido = jogoExiste(codigoJogo);

            if(numRepetido==true)
            {
                System.out.println("3:erro-codigo repetido.");
            }
            
            if(!categoriaLida.equals("AVENTURA") && !categoriaLida.equals("ESTRATEGIA") && !categoriaLida.equals("CORRIDA"))
            {
                categoriaValida = false;
                System.out.println("3:erro-categoria inexistente.");
            }
            else
            {
                categoriaJogo = Categoria.valueOf(categoriaLida);
            }

            if(!numRepetido && categoriaValida)
            {
                Jogo novoJogo = new Jogo(codigoJogo, nomeJogo, anoJogo, valorMinuto, categoriaJogo);
                listaJogos.add(novoJogo);
                System.out.println("3:" + novoJogo.descrever());
            }
        }
    }

    private void cadastrarContratos()
    {
        String linha = "";
        int idContrato = 0;
        int periodoContrato;
        int idCliente;
        int idJogo;
        Cliente novoCliente = null;
        Jogo novoJogo = null;


         linha = entrada.nextLine();

        while(!linha.equals("-1"))
        {
            idContrato = Integer.parseInt(linha);
            linha = entrada.nextLine();
            periodoContrato = Integer.parseInt(linha);
            linha = entrada.nextLine();
            idCliente = Integer.parseInt(linha);
            linha = entrada.nextLine();
            idJogo = Integer.parseInt(linha);
            linha = entrada.nextLine();
            
            boolean idRepetido;
            boolean clienteExiste;
            boolean jogoExiste;

            idRepetido = contratoExiste(idContrato);
            clienteExiste = clienteExiste(idCliente);
            jogoExiste = jogoExiste(idJogo);

            if(idRepetido)
            {
                System.out.println("4:erro-id repetido.");
            }

            if(!clienteExiste)
            {
                System.out.println("4:erro-cliente inexistente.");
            }
            else
            {
                novoCliente = buscarCliente(idCliente);
            }

            if(!jogoExiste)
            {
                System.out.println("4:erro-jogo inexistente.");
            }
            else
            {
                novoJogo = buscarJogo(idJogo);
            }
            

            if(!idRepetido && clienteExiste && jogoExiste)
            {
                Contrato novoContrato = new Contrato(idContrato, periodoContrato, novoCliente, novoJogo);
                listaContratos.add(novoContrato);
                System.out.println("4:" + novoContrato.descrever());
            }
        }
    }

    private void consultarJogoCodigo()
    {
        
        int codigoJogo = 0;
        String linha = "";
        Jogo jogoConsultado;

        linha = entrada.nextLine();

        codigoJogo = Integer.parseInt(linha);
        

        if(!jogoExiste(codigoJogo))
        {
            System.out.println("5:erro-codigo inexistente.");
        }
        else
        {
            jogoConsultado = buscarJogo(codigoJogo);
            System.out.println("5:" + jogoConsultado.descreverParaConsulta());
        }
    }

    private void consultarJogoCategoria()
    {
        String linha = "";
        String categoriaConsultada;

        linha = entrada.nextLine();
        categoriaConsultada = linha;

        boolean categoriaConsultadaExiste = categoriaExiste(categoriaConsultada);
        boolean jogoCategoriaExiste = false;

        if(!categoriaConsultadaExiste)
        {
            System.out.println("6:erro-categoria inexistente.");
        }
        else
        {
            for (Jogo checaJogo : listaJogos)
            {
                if(checaJogo.getCategoria().name().equals(categoriaConsultada))
                {
                    jogoCategoriaExiste = true;
                    System.out.println("6:" + categoriaConsultada + ";" + checaJogo.getCodigo() + ";" + checaJogo.getNome());
                }
            }

            if(!jogoCategoriaExiste)
            {
                System.out.println("6:erro-nenhum jogo encontrado.");
            }
        }
    }

    private void mudarNomeCliente ()
    {
        String linha = "";
        int numeroCliente = 0;
        String nomeCliente = "";

        linha = entrada.nextLine();
        numeroCliente = Integer.parseInt(linha);
        linha = entrada.nextLine();
        nomeCliente = linha;

        if(clienteExiste(numeroCliente))
        {
            Cliente c = buscarCliente(numeroCliente);
            
            c.setNome(nomeCliente);
            System.out.println("7:" + c.descrever());
        }
        else
        {
            System.out.println("7:erro-numero inexistente.");
        }

    }

    private void removerContratosJogo ()
    {
        String linha = "";
        int codigoJogo = 0;

        linha = entrada.nextLine();
        codigoJogo = Integer.parseInt(linha);

        boolean contratoJogoExiste = false;
        Contrato c;

        if(jogoExiste(codigoJogo))
        {
            for(int i = listaContratos.size()-1; i >= 0; i--)
            {
                c = listaContratos.get(i);

                if(c.getJogo().getCodigo()==codigoJogo)
                {
                    contratoJogoExiste = true;
                    System.out.println("8:contrato removido: " + c.getId());
                    listaContratos.remove(i);
                }
            }

            if(!contratoJogoExiste)
            {
                System.out.println("8:nenhum contrato encontrado.");
            }

        }
        else
        {
            System.out.println("8:erro-codigo inexistente.");
        }
    }

    private void listaTodosContratos ()
    {

        boolean listaContratosVazia = listaContratos.isEmpty(); 


        if(!listaContratosVazia)
        {
            for(Contrato imprimeContrato : listaContratos)
            {
                System.out.println("9:" + imprimeContrato.descrever());
            }
        }
        else
        {
            System.out.println("9:erro-nenhum contrato cadastrado.");
        }

    }

    private void mostraClienteMaiorValor ()
    {

        double maiorValor = 0;
        Cliente clienteValioso = null;


        if(!listaContratos.isEmpty())
        {
            for(Cliente checaCliente : listaClientes)
            {

                double somaValor = 0;

                for(Contrato checaContrato : listaContratos)
                {
                    if(checaContrato.getCliente()==checaCliente)
                    {
                        somaValor+=checaContrato.getJogo().getValorMinuto();
                    }
                }

                if(somaValor>maiorValor)
                {
                    maiorValor = somaValor;
                    clienteValioso = checaCliente;
                }

            }

            System.out.println("10:" + clienteValioso.descreverBase() + ";" + maiorValor);
        }
        else
        {
            System.out.println("10:erro-nenhum contrato encontrado.");
        }

    }










    //                                                                                       METODOS AUXILIARES

    private boolean clienteExiste (int numero)
    {
        for (Cliente checaCliente : listaClientes)
        {
            if(checaCliente.getNumero()==numero)
            {
                return true;
            }
        }

        return false;
    }

    private Cliente buscarCliente (int numero)
    {
        Cliente clienteBuscado = null;
        
        for (Cliente checaCliente : listaClientes)
        {
            if(checaCliente.getNumero()==numero)
            {
                clienteBuscado = checaCliente;
                return clienteBuscado;
            }
        }

        return clienteBuscado;

    }

    private boolean jogoExiste (int codigo)
    {
        for (Jogo checaJogo : listaJogos)
        {
            if(checaJogo.getCodigo()==codigo)
            {
                return true;
            }
        }

        return false;
    }

    private Jogo buscarJogo (int codigo)
    {
        Jogo jogoBuscado = null;
        
        for (Jogo checaJogo : listaJogos)
        {
            if(checaJogo.getCodigo()==codigo)
            {
                jogoBuscado = checaJogo;
                return jogoBuscado;
            }
        }

        return jogoBuscado;

    }

    private boolean contratoExiste (int id)
    {
        for (Contrato checaContrato : listaContratos)
        {
            if(checaContrato.getId()==id)
            {
                return true;
            }
        }

        return false;
    }

    private boolean categoriaExiste(String categoria) 
    {
    for (Categoria cat : Categoria.values()) 
        {
            if (cat.name().equals(categoria)) 
            {
                return true;
            }
        }

        return false;
    }












    //                                                                             CODIGOS DE ENTRADA E SAIDA PARA ARQUIVOS

    // Redireciona Entrada de dados para arquivos em vez de teclado
    // Chame este metodo para redirecionar a leitura de dados para arquivos
    private void redirecionaEntrada() {
        try {
            BufferedReader streamEntrada = new BufferedReader(new FileReader(nomeArquivoEntrada));
            entrada = new Scanner(streamEntrada);   // Usa como entrada um arquivo
        } catch (Exception e) {
            System.out.println(e);
        }
        Locale.setDefault(Locale.ENGLISH);   // Ajusta para ponto decimal
        entrada.useLocale(Locale.ENGLISH);   // Ajusta para leitura para ponto decimal
    }

    // Redireciona Saida de dados para arquivos em vez da tela (terminal)
    // Chame este metodo para redirecionar a escrita de dados para arquivos
    private void redirecionaSaida() {
        try {
            PrintStream streamSaida = new PrintStream(new File(nomeArquivoSaida), Charset.forName("UTF-8"));
            System.setOut(streamSaida);             // Usa como saida um arquivo
        } catch (Exception e) {
            System.out.println(e);
        }
        Locale.setDefault(Locale.ENGLISH);   // Ajusta para ponto decimal
    }

    // Restaura Entrada padrao para o teclado
    // Chame este metodo para retornar a leitura de dados para o teclado
    private void restauraEntrada() {
        entrada = new Scanner(System.in);
    }

    // Restaura Saida padrao para a tela (terminal)
    // Chame este metodo para retornar a escrita de dados para a tela
    private void restauraSaida() {
        System.setOut(saidaPadrao);
    }


}
