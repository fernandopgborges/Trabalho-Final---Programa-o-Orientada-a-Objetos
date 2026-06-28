package br.pucrs.nomeusuario.exemplo.dados;

public class FormaPagamento {

	private int cod;

	private int diaVencimento;

	private int numeroCliente;

	public FormaPagamento( int cod, int diaVencimento ) {
		this.cod = cod;
		this.diaVencimento = diaVencimento;
		this.numeroCliente = -1;
	}

	public FormaPagamento( int cod, int diaVencimento, int numeroCliente ) {
		this.cod = cod;
		this.diaVencimento = diaVencimento;
		this.numeroCliente = numeroCliente;
	}

	public int getCod() {
		return this.cod;
	}

	public void setCod(int cod) {
		this.cod = cod;
	}
	

	public int getDiaVencimento() {
		return this.diaVencimento;
	}

	public void setDiaVencimento(int diaVencimento) {
		this.diaVencimento = diaVencimento;
	}

	public int getNumeroCliente() {
		return this.numeroCliente;
	}

	@Override
	public String toString() {
		return " cod='" + getCod() + "'" + ", diaVencimento='" + getDiaVencimento() + "'";
	}


}