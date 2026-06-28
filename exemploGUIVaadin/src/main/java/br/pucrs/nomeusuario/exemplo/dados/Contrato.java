package br.pucrs.nomeusuario.exemplo.dados;

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

	public double calculaValorFinal() {
		return 0;
	}

}