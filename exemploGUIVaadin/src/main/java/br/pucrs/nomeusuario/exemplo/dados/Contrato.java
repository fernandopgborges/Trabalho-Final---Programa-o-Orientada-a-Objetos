package br.pucrs.nomeusuario.exemplo.dados;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Contrato {

	private int id;

	private Date data;

	private int periodo;

	private Cliente cliente;

	private Jogo jogo;

	private FormaPagamento formaPagamento;

	public Contrato( int id, Date data, int periodo, Cliente cliente, Jogo jogo, FormaPagamento formaPagamento ) {
		this.id = id;
		this.data = data;
		this.periodo = periodo;
		this.cliente = cliente;
		this.jogo = jogo;
		this.formaPagamento = formaPagamento;
	}

	public int getId() {
		return this.id;
	}

	public void setId( int id ) {
		this.id = id;
	}

	public Date getData() {
		return this.data;
	}

	public void setData( Date data ) {
		this.data = data;
	}

	public int getPeriodo() {
		return this.periodo;
	}

	public void setPeriodo( int periodo ) {
		this.periodo = periodo;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public int getNumeroCliente() {
		return cliente.getNumero();
	}

	public Jogo getJogo() {
		return jogo;
	}

	public int getCodigoJogo() {
		return jogo.getCodigo();
	}

	public FormaPagamento getFormaPagamento() {
		return formaPagamento;
	}

	public int getCodPagamento() {
		return formaPagamento.getCod();
	}

	public double calculaValorFinal(CatalogoContratos catalogoContratos) 
	{
		double percentual = 0;	
		
		switch (jogo.getCategoria())
		{
			case AVENTURA:
				percentual = .05;
				break;
			case ESTRATEGIA:
				percentual = .10;
				break;
			case CORRIDA:
				percentual = .15;
				break;
		}

		if (formaPagamento instanceof PIX)
		{
			percentual += -.05;
		}
		else if (formaPagamento instanceof CartaoCredito)
		{
			percentual += .05;
		}

		double valor = jogo.getValorDiario() * (1 + percentual);

		int quantidadeContratos = catalogoContratos.contarContratosCliente(cliente);

		if (quantidadeContratos > 2)
		{
			valor = valor * 0.97;
		}

		return valor;
	}

	public String getDataFormatada() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		return sdf.format( this.data );
	}

	public String descrever() {
		return 	id + ";" +
				getDataFormatada() + ";" +
				periodo + ";" +
				getNumeroCliente() + ";" +
				getCodigoJogo() + ";" +
				getCodPagamento();
	}
}