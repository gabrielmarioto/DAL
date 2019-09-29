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
public class Conexao
{

    private Connection connect;
    private String erro;

    public Conexao()
    {
        connect = null;
        erro = "";
    }

    public boolean conectar(String local, String banco, String usuario, String senha)
    {
        boolean executou = false;
        try
        {
            String url = local + banco;
            connect = DriverManager.getConnection(url, usuario, senha);
            executou = true;
        } catch (SQLException ex)
        {
            erro = "Erro: " + ex.toString();
        } catch (Exception e)
        {
            erro = "Erro: " + e.toString();
        }
        return executou;
    }

    public boolean manipular(String sql)
    {
        boolean executou = false;
        try
        {
            Statement state = connect.createStatement();
            int result = state.executeUpdate(sql);
            state.close();
            if (result >= 1)
            {
                executou = true;
            }
        } catch (SQLException ex)
        {
            erro = "Erro: " + ex.toString();
        } catch (Exception e)
        {
            erro = "Erro: " + e.toString();
        }
        return executou;
    }

    public ResultSet consultar(String sql)
    {
        ResultSet rs = null;
        try
        {
            Statement state = connect.createStatement();
            rs = state.executeQuery(sql);
        } catch (SQLException ex)
        {
            erro = "Erro: " + ex.toString();
        } catch (Exception e)
        {
            erro = "Erro: " + e.toString();
        }

        return rs;
    }

    public boolean getEstadoConexao()
    {
        return (connect != null);
    }

    public String getMessageErro()
    {
        return erro;
    }

    public int getMaxPK(String chave, String tabela)
    {
        String sql = "select max ("+chave+") from "+tabela;
        int max = 0;
        ResultSet rs = consultar(sql);
        try
        {
            if (rs.next())
            {
                max += rs.getInt(1);
            }
        } catch (SQLException sqlex)
        {
            erro = "Erro: " + sqlex.toString();
            max = -1;
        }
        return max;
    }
}
