
package br.com.sgda.dao;

import br.com.sgda.jdbc.Conexao;
import br.com.sgda.modelo.Aluno;
import br.com.sgda.modelo.Matricula;
import br.com.sgda.modelo.Pessoa;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import util.Convert;


public class MatriculaDao {

    private Connection conexao;

    public MatriculaDao() throws ClassNotFoundException {

        conexao = Conexao.getConexao();
    }

    public void insere(Matricula matricula) throws SQLException {
        String sql = "insert into matricula (descricao, Aluno_idAluno, data_inicio)"
                + "values(?,?,?)";
        try {
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setString(1, matricula.getDescricao());
            stmt.setInt(2, matricula.getPessoa().getId());
            stmt.setTimestamp(3, Convert.getCurrentTimeStamp(matricula.getData()));

            stmt.execute();
            stmt.close();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        this.conexao.close();
    }

  

}
