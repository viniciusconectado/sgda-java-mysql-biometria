
package br.com.sgda.dao;

import br.com.sgda.jdbc.Conexao;
import br.com.sgda.modelo.Pessoa;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.text.MaskFormatter;
import util.Convert;


public class AlunoDao {

    private Connection conexao;
  

    public AlunoDao() throws ClassNotFoundException {
        conexao = Conexao.getConexao();
    }

    public void insere(Pessoa aluno) throws SQLException {
        String sql = "insert into aluno (nome,cpf,datanascimento,sexo)"
                + "values(?,?,?,?)";
        try {
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setString(1, aluno.getNome());
            stmt.setString(2, aluno.getCpf());
            stmt.setTimestamp(3, Convert.getCurrentTimeStamp(aluno.getDataNascimento()));    
            //stmt.setDate(3, (Date) aluno.getDataNascimento());
            stmt.setInt(4, aluno.getSexo());
           /* if(aluno.getSexo().equals("M")){
                stmt.setInt(4, 0);
            }else{
                stmt.setInt(4, 1);
            }*/
            
            stmt.execute();
            stmt.close();
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        String sql2 = "insert into endereco (logradouro, bairro,numero, cep,cidade,estado)"
                + "values(?,?,?,?,?,?)";

        PreparedStatement stmt = conexao.prepareStatement(sql2);
        stmt.setString(1, aluno.getEndereco());
        stmt.setString(2, aluno.getBairro());
        stmt.setInt(3, aluno.getNumero());
        stmt.setInt(4, aluno.getCep());
        stmt.setString(5, aluno.getCidade());
        stmt.setString(6, aluno.getEstado());
        stmt.execute();
        stmt.close();
        
        this.conexao.close();
    }
    
    //lista dos alunos na que estão no banco
     public List<Pessoa> getAlunos() throws SQLException, Exception {
        List<Pessoa> pessoas = new ArrayList<Pessoa>();
        String sql = "SELECT idAluno, nome, cpf, sexo, datanascimento FROM aluno ORDER BY nome";
        PreparedStatement stmt = conexao.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            Pessoa Pess = new Pessoa();
            Pess.setId(rs.getInt("idAluno"));
            Pess.setNome(rs.getString("nome"));
            Pess.setCpf(rs.getString("cpf"));
            Pess.setSexo(rs.getInt("sexo"));

            Pess.setDataNascimento(Convert.convertDateInterfaceToDateDatabase(
                Convert.convertDateToInterface(rs.getTimestamp("datanascimento"), true)
                    
            ));
          
          
            pessoas.add(Pess);
        }
        rs.close();
        stmt.close();
        return pessoas;
    }
     
     public Pessoa PopulaAluno(int id) throws SQLException, Exception{
         Pessoa pobject = new Pessoa();
        String sql = "select * from aluno "
                + "WHERE idAluno = ?";
        PreparedStatement stmt = conexao.prepareStatement(sql);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()){
           
            pobject.setId(rs.getInt("idAluno"));
            pobject.setNome(rs.getString("nome"));
            pobject.setEndereco(rs.getString("cpf"));
            pobject.setCpf(rs.getString("sexo"));  
            
            pobject.setDataNascimento(Convert.convertDateInterfaceToDateDatabase(
                Convert.convertDateToInterface(rs.getTimestamp("datanascimento"), true)
                    
            ));
            
        }
        return pobject;
      
 }
     //metodo para pesquisar os alunos
     public List<Pessoa> buscaPorNome(String busca) throws SQLException {
        List<Pessoa> alunoss = new ArrayList<Pessoa>();
        String sql = "SELECT idAluno, nome, cpf, sexo FROM aluno "
                + "WHERE nome LIKE ? "
                + "ORDER BY nome";
        PreparedStatement stmt = conexao.prepareStatement(sql);
        stmt.setString(1, "%" + busca + "%");
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            Pessoa aluno = new Pessoa();
            aluno.setId(rs.getInt("idAluno"));
            aluno.setNome(rs.getString("nome"));
            aluno.setCpf(rs.getString("cpf"));
            aluno.setSexo(rs.getInt("sexo"));  
            

            alunoss.add(aluno);
        }
        rs.close();
        stmt.close();
        return alunoss;
    }
     
     public void exluir(int id) throws SQLException{
         try {
              String sql = "DELETE FROM aluno WHERE idAluno = ?";
              PreparedStatement stmt = conexao.prepareStatement(sql);
              stmt.setInt(1, id);
              stmt.execute();
              JOptionPane.showMessageDialog(null, "excluido");
              stmt.close(); 
         } catch (Exception e) {
             JOptionPane.showMessageDialog(null, "erro na exclusão "+e);
         }
         
     }
    
}
