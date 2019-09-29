/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package banco;

/**
 *
 * @author GABRIEL
 */
public class Banco
{
    private static Conexao con;
    public static boolean conectar()
    {
        con = new Conexao();
        {
            return con.conectar("jdbc:postgresql://localhost:5432/","pets", "postgres", "postgres123");
        }
    }
    private Banco (){}
    public static Conexao getCon (){return con;}
    

}
