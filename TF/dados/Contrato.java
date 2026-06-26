package dados;

import java.util.Date;

public class Contrato {

	private int id;

	private Date data;

	private int periodo;

	private FormaPagamento formaPagamento;

	private Jogo jogo;

	private Cliente cliente;

	
	public Contrato(int id, Date data, int periodo, FormaPagamento formaPagamento, Jogo jogo, Cliente cliente) {
		this.id = id;
		this.data = data;
		this.periodo = periodo;
		this.formaPagamento = formaPagamento;
		this.jogo = jogo;
		this.cliente = cliente;
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

	public Cliente getCliente()
	{
		return this.cliente;
	}

	public Jogo getJogo()
	{
		return this.jogo;
	}

	public double calculaValorFinal() {
		return 0;
	}


}
