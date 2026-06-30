package br.pucrs.nomeusuario.exemplo.dados;

public class PIX extends FormaPagamento {

	private String chave;

	public PIX( int cod, int diaVencimento, int numeroCliente, String chave ) {
		super( cod, diaVencimento, numeroCliente );
		this.chave = chave;
	}


	public String getChave() {
		return this.chave;
	}

	public void setChave(String chave) {
		this.chave = chave;
	}

	@Override
	public String descrever() {
		return 	getCod() + ";" + 
				getDiaVencimento() + ";" +
				"1;" +
				getNumeroCliente() + ";" +
				chave;
	}
}