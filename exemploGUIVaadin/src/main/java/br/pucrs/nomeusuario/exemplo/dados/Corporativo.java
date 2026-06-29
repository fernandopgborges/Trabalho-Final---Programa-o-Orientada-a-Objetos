package br.pucrs.nomeusuario.exemplo.dados;

public class Corporativo extends Cliente{

    private String cnpj;
    private String nomeFantasia;

    public Corporativo (int numero, String nome, String email, String cnpj, String nomeFantasia)
    {
        super(numero, nome, email);
        this.cnpj = cnpj;
        this.nomeFantasia = nomeFantasia;
    }

    public String getCnpj ()
    {
        return this.cnpj;
    }

    public void setCnpj(String cnpj) 
    {
        this.cnpj = cnpj;
    }

    public void setNomeFantasia(String nomeFantasia) 
    {
        this.nomeFantasia = nomeFantasia;
    }

    public String getNomeFantasia ()
    {
        return this.nomeFantasia;
    }

    public String descrever ()
    {
        return getNumero() + ";" + getNome() + ";" + getEmail() + ";" + cnpj + ";" + nomeFantasia;
    }
}
