package dados;

import java.util.Date;

public class Contrato {

	private int id;

	private Date data;

	private int periodo;

	private FormaPagamento formaPagamento;

	public double calculaValorFinal() {
		return 0;
	}


	public Contrato(int id, Date data, int periodo, FormaPagamento formaPagamento) {
		this.id = id;
		this.data = data;
		this.periodo = periodo;
		this.formaPagamento = formaPagamento;
	}


	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getData() {
		return this.data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public int getPeriodo() {
		return this.periodo;
	}

	public void setPeriodo(int periodo) {
		this.periodo = periodo;
	}

	public FormaPagamento getFormaPagamento() {
		return this.formaPagamento;
	}

	public void setFormaPagamento(FormaPagamento formaPagamento) {
		this.formaPagamento = formaPagamento;
	}


}
