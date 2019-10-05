/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAL;

import banco.Banco;
import entidades.Especie;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author GABRIEL
 */
public class DALespecie
{
    public boolean salvar(Especie e)
    {
        String sql = "insert into especie values ('#1','#2') ";
        sql = sql.replace("#1",""+e.getCod());
        sql = sql.replace("#2", e.getNome());
        return Banco.getCon().manipular(sql);
    }

    public boolean alterar(Especie e)
    {
        String sql = " update especie set esp_nome = '#1' where esp_cod = '#2' ";
        sql = sql.replace("#1", e.getNome());
        sql = sql.replace("#2",""+e.getCod());
        return Banco.getCon().manipular(sql);
    }
    public boolean apagar(Especie e)
    {
        return Banco.getCon().manipular("delete from especie where esp_cod = "+e.getCod());
    }
    public List <Especie> get(String filtro)
    {
        List <Especie> especie= new ArrayList();
        String sql = "select * from especie";
        if(!filtro.isEmpty())
            sql+=" where rac_nome like upper('%"+filtro+"%')";
        ResultSet rs = Banco.getCon().consultar(sql);
        try
        {
            if(rs.next())
                especie.add(new Especie(rs.getInt("esp_cod"),rs.getString("esp_nome")));
        }
        catch(SQLException ex)
        {
            
        }
        return especie;
    }
    public Especie getUm(int cod)
    {   
        Especie especie=null;
        String sql="select * from especie where esp_cod = "+cod;
        ResultSet rs=Banco.getCon().consultar(sql);
        try{
           if(rs.next())
              especie = new Especie(rs.getInt("esp_cod"),rs.getString("esp_nome"));
        }
        catch(Exception e){}
        return especie;
    }
    public List <Especie> get()
    {
        List <Especie> especie= new ArrayList();
        String sql = "select * from especie";
        ResultSet rs = Banco.getCon().consultar(sql);
        try
        {
            if(rs.next())
                especie.add(new Especie(rs.getInt("esp_cod"),rs.getString("esp_nome")));
        }
        catch(SQLException ex)
        {
            
        }
        return especie;
    }
}
