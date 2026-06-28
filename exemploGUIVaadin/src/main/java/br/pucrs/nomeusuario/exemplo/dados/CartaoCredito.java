package br.pucrs.nomeusuario.exemplo.dados;

import java.util.Date;

public class CartaoCredito extends FormaPagamento {

	private String numero;

	private Date validade;

	public CartaoCredito( int cod,int diaVencimento, int numeroCliente, String numero, Date validade ) {
		super( cod, diaVencimento, numeroCliente );
		this.numero = numero;
		this.validade = validade;
	}
	

	public String getNumero() {
		return this.numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public Date getValidade() {
		return this.validade;
	}

	public void setValidade(Date validade) {
		this.validade = validade;
	}

}