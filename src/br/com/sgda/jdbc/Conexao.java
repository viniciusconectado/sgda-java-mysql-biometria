
package br.com.sgda.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author ACER-NOTE
 */
public class Conexao {
    public static Connection getConexao() throws 
             ClassNotFoundException{
         String url="jdbc:mysql://localhost/basesgda";
         String usuario="root";
         String senha="";
         try{
             Connection con = 
                     DriverManager.getConnection(url,usuario,senha);
             Class.forName("com.mysql.jdbc.Driver");
           //  JOptionPane.showMessageDialog(null,"Conectado com sucesso!");
             return con;   
         }catch (SQLException eSql){
             JOptionPane.showMessageDialog(null,"Erro ao conectar com o banco de dados:" +
                  eSql.getMessage());
             
             return null;   
         }
     }

}
