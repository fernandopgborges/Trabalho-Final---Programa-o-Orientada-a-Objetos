package dados;
public abstract class Cliente {

    private int numero;
    private String nome;
    private String email;

    public Cliente (int numero, String nome, String email)
    {
        this.numero = numero;
        this.nome = nome;
        this.email = email;
    }

    public int getNumero ()
    {
        return this.numero;
    }

    public String getNome ()
    {
        return this.nome;
    }

    public String getEmail ()
    {
        return this.email;
    }

    public void setNome (String nome)
    {
        this.nome = nome;
    }
    
    public abstract String descrever ();

    public String descreverBase()
    {
        return getNumero() + ";" + getNome() + ";" + getEmail();
    }

}
