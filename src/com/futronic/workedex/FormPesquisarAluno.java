
package com.futronic.workedex;

import br.com.sgda.dao.AlunoDao;
import br.com.sgda.modelo.Matricula;
import br.com.sgda.modelo.Pessoa;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;
import util.Convert;

/**
 *
 * @author ACER-NOTE
 */
public class FormPesquisarAluno extends javax.swing.JInternalFrame {

    MatricularAluno m;
    AlunoDao dao;

 
    public FormPesquisarAluno(MatricularAluno janela) throws ClassNotFoundException, SQLException, Exception {
        initComponents();
        this.dao = new AlunoDao();
        colocaNaTabela();  
        this.m = janela;
       
         
  
    }

   
    
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tfPesquisar = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblAlunos = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        setMaximizable(true);
        setResizable(true);

        tfPesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfPesquisarActionPerformed(evt);
            }
        });

        tblAlunos.setAutoCreateRowSorter(true);
        tblAlunos.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 255, 102)));
        tblAlunos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "NOME", "CPF", "SEXO", "DATA "
            }
        ));
        tblAlunos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblAlunosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblAlunos);

        jButton2.setText("usar cliente");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(25, Short.MAX_VALUE)
                .addComponent(tfPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton2))
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 356, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tfPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfPesquisarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfPesquisarActionPerformed

    private void tblAlunosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblAlunosMouseClicked
       
    }//GEN-LAST:event_tblAlunosMouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        //  m.pegaCliente(dao.PopulaCliente((int)tblClientes.getValueAt(tblClientes.getSelectedRow(),0)));
        int linha = tblAlunos.getSelectedRow();//captura a linha selecionada da tabela
        int valorlc = (int)tblAlunos.getValueAt(linha,0); // pega o valor
        Pessoa alunonovo;
        try {
            alunonovo = dao.PopulaAluno(valorlc); //preenche o cliente com o id da linha selecionada
            m.pegaAluno(alunonovo);//metodo da janela recebida cadastro, passando o cliente. cliente populado
        } catch (SQLException ex) {
            Logger.getLogger(FormPesquisarAluno.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(FormPesquisarAluno.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_jButton2ActionPerformed

   public void colocaNaTabela() throws ClassNotFoundException, SQLException, Exception{      
        AlunoDao dao = new AlunoDao();
        List<Pessoa> alunoss = dao.getAlunos();
        int linha = 0;
        DefaultTableModel dtm = (DefaultTableModel) tblAlunos.getModel();
        dtm.setNumRows(alunoss.size());
        for (Pessoa pess : alunoss) {
            tblAlunos.setValueAt(pess.getId(), linha, 0);
            tblAlunos.setValueAt(pess.getNome(), linha, 1);
            tblAlunos.setValueAt(pess.getCpf(), linha, 2);
         //tblAlunos.setValueAt(pess.getSexo(), linha, 3);
         
             String sexo = "FEMININO";
            if (pess.getSexo() == 1) {
                sexo = "MASCULINO";
            } 

            
            tblAlunos.setValueAt(sexo, linha, 3);
            
            //tblAlunos.setValueAt(pess.getDataNascimento(), linha, 4);
            
            
          
            tblAlunos.setValueAt(Convert.convertDateToInterface(pess.getDataNascimento()), linha, 4);
            
           // tblAlunos.setValueAt(GenericCalendar.getCalendarCurrent(false),  linha, 4);
        

            
         
            linha++;
        }
       
    } 
   
  

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblAlunos;
    private javax.swing.JTextField tfPesquisar;
    // End of variables declaration//GEN-END:variables
}
