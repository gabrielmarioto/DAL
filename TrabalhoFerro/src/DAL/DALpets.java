/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAL;

import banco.Banco;
import entidades.Pet;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author GABRIEL
 */
public class DALpets
{
 public boolean salvar(Pet p)
    {
        String sql="insert into pet values ('#0',#1',#2,'#3','#4','#5',#6,#7)";
        sql=sql.replace("#0",""+p.getCod());
        sql=sql.replace("#1",p.getNome());
        sql=sql.replace("#2",""+p.getPeso());
        sql=sql.replace("#3",p.getCor());
        sql=sql.replace("#4",p.getDataNasc().toString());
        sql=sql.replace("#5",""+p.getSexo());
        sql=sql.replace("#6",""+p.getEspecie().getCod());
        sql=sql.replace("#7",""+p.getRaca().getCod());
        
        return Banco.getCon().manipular(sql);
    }
    public boolean alterar(Pet p)
    {
        String sql="update pet set pet_nome='#1', pet_peso=#2, pet_cor'#3', pet_dtnasc='#4', pet_sexo='#5', esp_cod=#6, rac_cod='#7' where pet_cod=#8";
        sql=sql.replace("#1",p.getNome());
        sql=sql.replace("#1",p.getNome());
        sql=sql.replace("#2",""+p.getPeso());
        sql=sql.replace("#3",p.getCor());
        sql=sql.replace("#4",p.getDataNasc().toString());
        sql=sql.replace("#5",""+p.getSexo());
        sql=sql.replace("#6",""+p.getEspecie().getCod());
        sql=sql.replace("#7",""+p.getRaca().getCod());
        sql=sql.replace("#8",""+p.getCod());
        
        return Banco.getCon().manipular(sql);
    }
    public boolean apagar(Pet p)
    {
        return Banco.getCon().
                manipular("delete from pet where pet_cod="+p.getCod());
    }
    public Pet getUm(int cod)
    {   
        Pet pet=null;
        String sql="select * from pet where pet_cod="+cod;
        ResultSet rs=Banco.getCon().consultar(sql);
        try{
           if(rs.next())
              pet=new Pet(rs.getInt("pet_cod"),rs.getString("pet_nome"),
                          rs.getDouble("pet_peso"),rs.getString("pet_cor"),
                          rs.getDate("pet_dtnasc").toLocalDate(),
                          rs.getString("pet_sexo").charAt(0),
                          new DALespecie().getUm(rs.getInt("esp_cod")),
                          new DALracas().getUm(rs.getInt("rac_cod")));
        }
        catch(Exception e){}
        return pet;
    }
    public List <Pet> get(String filtro) //se filtro vazio, retorna todas
    {   
        List <Pet> pets=new ArrayList();
        Pet pet=null;
        String sql="select * from pet";
        if(!filtro.isEmpty())
            sql=sql+"where "+filtro;
        ResultSet rs=Banco.getCon().consultar(sql);
        try{
           while (rs.next())
           {   pet=new Pet(rs.getInt("pet_cod"),rs.getString("pet_nome"),
                          rs.getDouble("pet_peso"),rs.getString("pet_cor"),
                          rs.getDate("pet_dtnasc").toLocalDate(),
                          rs.getString("pet_sexo").charAt(0),
                          new DALespecie().getUm(rs.getInt("esp_cod")),
                          new DALracas().getUm(rs.getInt("rac_cod")));
              pets.add(pet);
           }
        }
        catch(Exception e){}
        return pets;
    }     
    
}
