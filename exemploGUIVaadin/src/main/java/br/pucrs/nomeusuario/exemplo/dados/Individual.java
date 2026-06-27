package br.pucrs.nomeusuario.exemplo.dados;

public class Individual extends Cliente {

    private String cpf;

    public Individual (int numero, String nome, String email, String cpf)
    {
        super (numero, nome, email);
        this.cpf = cpf;
    }

    public String getCpf ()
    {
        return this.cpf;
    }

    public String descrever ()
    {
        return getNumero() + ";" + getNome() + ";" + getEmail() + ";" + cpf;
    }
}
