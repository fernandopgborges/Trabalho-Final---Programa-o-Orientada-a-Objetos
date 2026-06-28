package br.pucrs.nomeusuario.exemplo.dados;

import java.util.Date;

public class Contrato {

	private int id;

	private Date data;

	private int periodo;

	private int numeroCliente;

	private int codigoJogo;

	private int codPagamento;

	public Contrato( int id, Date data, int periodo, int numeroCliente, int codigoJogo, int codPagamento ) {
		this.id = id;
		this.data = data;
		this.periodo = periodo;
		this.numeroCliente = numeroCliente;
		this.codigoJogo = codigoJogo;
		this.codPagamento = codPagamento;
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

	public int getNumeroCliente() {
		return numeroCliente;
	}

	public int getCodigoJogo() {
		return codigoJogo;
	}

	public int getCodPagamento() {
		return codPagamento;
	}

	public double calculaValorFinal() {
		return 0;
	}

}