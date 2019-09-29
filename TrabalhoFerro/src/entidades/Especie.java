/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

/**
 *
 * @author GABRIEL
 */
public class Especie
{
    private int cod;
    private String nome;
    
    public Especie (int cod, String nome)
    {
        this.cod = cod;
        this.nome = nome;
    }
    public Especie(String nome)
    {
        this(0,nome);
    }
    public Especie()
    {
        this(0,"");
    }
    
    public int getCod()
    {
        return cod;
    }
    public void setCod(int cod)
    {
        this.cod = cod;
    }
    public String getNome()
    {
        return nome;
    }
    public void setNome(String nome)
    {
        this.nome = nome;
    }
    @Override
    public String toString()
    {
        return  nome;
    }
}
