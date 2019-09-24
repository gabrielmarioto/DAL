package banco;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author GABRIEL
 */
class Conexao
{
    private Connection connect;
    private String erro;
    public Conexao()
    {
        erro = "";
        connect = null;
    }
    public boolean conectar (String local, String banco, String usuario, String senha)
    {
        boolean conectado = false;
        try
        {
            String url = local + banco;
            connect = DriverManager.getConnection(url, usuario, senha);
            conectado = true;
        }
        catch(SQLException ex)
        {
            erro = "Conexão com banco de dados! " + ex.toString();
        }
        catch(Exception e)
        {
            erro = "Outro erro!" + e.toString();
        }
        return conectado;
    }
    public String getErro()
    {
        return erro;
    }
    public boolean getConexao()
    {
        return (connect != null);
    }
    
    public boolean manipular(String sql) //INSERIR, ALTERAR E EXCLUIR
    {
        boolean executou = false;
        try
        {
            Statement state = connect.createStatement();
            int result = state.executeUpdate(sql);
            state.close();
            if(result >= 1)
                executou = true; // CONEXAO COM SUCESSO
            
        }
        catch ( SQLException ex) // ERRO NA CONEXAO
        {
            erro = "Erro: " + ex.toString() ;
        }
        return executou;
    }
    public ResultSet consultar(String sql) // SELECT
    {
        ResultSet rs = null;
        
        try
        {
            Statement state = connect.createStatement();
            rs = state.executeQuery(sql);
        }
        catch( SQLException ex)
        {
            erro = "Erro: " + ex.toString();
            rs = null;
        }
        
        return rs;
    }
    
    public int getMaxPK(String tabela, String chave)
    {
        String sql = "select max ("+chave+") from "+tabela;
        
        int max = 0;
        
        ResultSet rs = consultar(sql);
        try
        {
            if(rs.next())
                max = rs.getInt(1);
        }
        catch(SQLException ex)
        {
            erro = "Erro: "+ ex.toString();
            max = -1;
        }
        return max;
    }
}
