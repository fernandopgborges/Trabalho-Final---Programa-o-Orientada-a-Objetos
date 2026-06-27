package dados;
public class Jogo {
    private int codigo;
    private String nome;
    private int ano;
    private double valorMinuto;
    private Categoria categoria;
    private Contrato contrato;


    public Jogo (int codigo, String nome, int ano, double valorMinuto, Categoria categoria)
    {
        this.codigo = codigo;
        this.nome = nome;
        this.ano = ano;
        this.valorMinuto = valorMinuto;
        this.categoria = categoria;
    }
    
    public int getCodigo ()
    {
        return this.codigo;
    }

    public String getNome ()
    {
        return this.nome;
    }

    public int getAno ()
    {
        return this.ano;
    }

    public double getValorMinuto ()
    {
        return this.valorMinuto;
    }

    public Categoria getCategoria ()
    {
        return this.categoria;
    }

    public String descrever ()
    {
        return codigo + ";" + nome + ";" + ano + ";" + valorMinuto + ";" + categoria;
    }

    public String descreverParaConsulta ()
    {
        return codigo + ";" + nome + ";" + categoria;
    }

    public String descreverParaConsultaCategoria ()
    {
        return categoria + ";" + codigo + ";" + nome;
    }

}
