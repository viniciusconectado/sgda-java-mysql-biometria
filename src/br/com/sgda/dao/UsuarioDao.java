
package br.com.sgda.dao;

import br.com.sgda.jdbc.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class UsuarioDao {
     private Connection conexao;

    public UsuarioDao() throws ClassNotFoundException {
        conexao = Conexao.getConexao();
    }

    public boolean fazerLogin(String usuario, String senha) {

        String sql = "SELECT idusuario,usuario FROM usuario WHERE"
                + " usuario = ? AND senha = ?";

        try {
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setString(1, usuario);
            stmt.setString(2, senha);            
            ResultSet result = stmt.executeQuery();
            if(result.first()) {                
                return true;
            } else {
                return false;
            }
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return false;
    }
}
