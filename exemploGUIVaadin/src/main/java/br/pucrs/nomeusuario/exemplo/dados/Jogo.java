package br.pucrs.nomeusuario.exemplo.dados;

public class Jogo {

	private int codigo;

	private String nome;

	private int ano;

	private double valorDiario;

	private Categoria categoria;

	private boolean jogoDisponivel;

	private int contrato;

	public Jogo(int codigo, String nome, int ano, double valorDiario, Categoria categoria) {
		this.codigo = codigo;
		this.nome = nome;
		this.ano = ano;
		this.valorDiario = valorDiario;
		this.categoria = categoria;
		this.jogoDisponivel = true;
		this.contrato = -1;
	}

	public int getCodigo() {
		return this.codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getAno() {
		return this.ano;
	}

	public void setAno(int ano) {
		this.ano = ano;
	}

	public double getValorDiario() {
		return this.valorDiario;
	}

	public void setValorDiario(double valorDiario) {
		this.valorDiario = valorDiario;
	}

	public Categoria getCategoria() {
		return this.categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public boolean getJogoDisponivel ()
	{
		return this.jogoDisponivel;
	}

	public int getContrato() {
		return contrato;
	}

	public void setContrato( int c ) {
		contrato = c;
	}

	public void setJogoDisponivel(boolean jogoDisponivel)
	{
		this.jogoDisponivel = jogoDisponivel;
	}

}