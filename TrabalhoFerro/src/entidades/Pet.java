/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.time.LocalDate;

/**
 *
 * @author GABRIEL
 */
public class Pet
{
    private int cod;
    private String nome;
    private double peso;
    private String cor;
    private LocalDate datanasc;
    private char sexo;
    private Especie especie;
    private Racas raca;
    
    public Pet(int cod, String nome, double peso, String cor, LocalDate datanasc, char sexo, Especie especie, Racas raca)
    {
        this.cod = cod;
        this.nome = nome;
        this.peso = peso;
        this.cor = cor;
        this.datanasc = datanasc;
        this.sexo = sexo;
        this.especie = especie;
        this.raca = raca;
    }
    public Pet(String nome, double peso, String cor, LocalDate datanasc, char sexo, Especie especie, Racas raca)
    {
        this(0,nome,peso,cor,datanasc,sexo,especie,raca);
    }
    public Pet()
    {
        this(0,"",0.0,"",LocalDate.now(),'m',null,null);
    }
    
    public int getCod()
    {
        return cod;
    }
    public String getNome()
    {
        return nome;
    }
    public double getPeso()
    {
        return peso;
    }
    public String getCor()
    {
        return cor;
    }
    public LocalDate getDataNasc()
    {
        return datanasc;
    }
    public char getSexo()
    {
        return sexo;
    }
    public Especie getEspecie()
    {
        return especie;
    }
    public Racas getRaca()
    {
        return raca;
    }

    public void setCod(int cod)
    {
        this.cod = cod;
    }

    public void setNome(String nome)
    {
        this.nome = nome;
    }

    public void setPeso(double peso)
    {
        this.peso = peso;
    }

    public void setCor(String cor)
    {
        this.cor = cor;
    }

    public void setDatanasc(LocalDate datanasc)
    {
        this.datanasc = datanasc;
    }

    public void setSexo(char sexo)
    {
        this.sexo = sexo;
    }

    public void setEspecie(Especie especie)
    {
        this.especie = especie;
    }

    public void setRaca(Racas raca)
    {
        this.raca = raca;
    }
    
}
