
package com.futronic.workedex;

import com.futronic.workedex.DbRecord;
import java.util.Vector;
import br.com.sgda.dao.AlunoDao;
import br.com.sgda.jdbc.Conexao;
import br.com.sgda.modelo.Pessoa;
import java.awt.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;
import com.futronic.SDKHelper.*;
import com.sun.jna.platform.win32.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import util.Convert;
import util.GenericCalendar;
 /**
 *
 * @author ACER-NOTE
 */
public class CAluno extends javax.swing.JInternalFrame 
            implements IEnrollmentCallBack, IVerificationCallBack, IIdentificationCallBack {


    static final long serialVersionUID = 1L;
    private boolean update;
    
    
    public CAluno() {
        initComponents();

        m_FingerPrintImage = new CAluno.MyIcon();
        FingerImage.setIcon(m_FingerPrintImage);
        

        //clase da data - util
        try {
            System.out.println(Convert.convertDateToInterface(GenericCalendar.getCalendarCurrent(true), true));
        } catch (Exception e) {
        }

        // Get database folder
        try {
            m_DbDir = GetDatabaseDir();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "Initialization failed. Application will be close.\nError description: " + e.getMessage(),
                    getTitle(), JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
//
//        // Set default parameters
//        try {
//            FutronicEnrollment dummy = new FutronicEnrollment();
//            for (int i = 0; i < m_FarnValue2Index.length; i++) {
//                if (dummy.getFARnLevel() == m_FarnValue2Index[i]) {
//                    cbFARNLevel.setSelectedIndex(i);
//                }
//            }
//            cbMaxFrames.setSelectedItem(String.valueOf(dummy.getMaxModels()));
//            chDetectFakeFinger.setSelected(dummy.getFakeDetection());
//            cbMIOTOff.setSelected(dummy.getMIOTControlOff());
//            chFastMode.setSelected(dummy.getFastMode());
//            SetIdentificationLimit(dummy.getIdentificationsLeft());
//        } catch (FutronicException e) {
//            JOptionPane.showMessageDialog(null,
//                    "Initialization failed. Application will be close.\nError description: " + e.getMessage(),
//                    getTitle(), JOptionPane.ERROR_MESSAGE);
//            System.exit(0);
//        }
//        
        m_Operation = null;
    }

    public void OnPutOn(FTR_PROGRESS Progress) {
        txtMessage.setText("Pressione o dedo no leitor e aguarde ...");
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnIdentify = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        FingerImage = new javax.swing.JLabel();
        txtMessage = new javax.swing.JTextField();
        tfnomealuno = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        tfenderecoaluno = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        tfbairroaluno = new javax.swing.JTextField();
        tfnumeroaluno = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        tfcepaluno = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        cmbSexo = new javax.swing.JComboBox<>();
        tfcpfaluno = new javax.swing.JFormattedTextField();
        tfdatanascimentoaluno = new javax.swing.JFormattedTextField();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        setMaximizable(true);
        setResizable(true);

        btnIdentify.setFont(new java.awt.Font("Microsoft New Tai Lue", 0, 12)); // NOI18N
        btnIdentify.setText("IDENTIFICAR!");
        btnIdentify.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIdentifyActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Informações do Aluno"));
        jPanel1.setLayout(null);

        FingerImage.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        FingerImage.setMaximumSize(new java.awt.Dimension(160, 210));
        FingerImage.setMinimumSize(new java.awt.Dimension(160, 210));
        FingerImage.setPreferredSize(new java.awt.Dimension(160, 210));
        jPanel1.add(FingerImage);
        FingerImage.setBounds(460, 40, 160, 210);

        txtMessage.setEditable(false);
        txtMessage.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jPanel1.add(txtMessage);
        txtMessage.setBounds(10, 330, 600, 21);
        jPanel1.add(tfnomealuno);
        tfnomealuno.setBounds(10, 40, 430, 24);

        jLabel2.setFont(new java.awt.Font("Microsoft New Tai Lue", 0, 13)); // NOI18N
        jLabel2.setText("NOME:");
        jPanel1.add(jLabel2);
        jLabel2.setBounds(10, 20, 430, 20);

        jLabel3.setFont(new java.awt.Font("Microsoft New Tai Lue", 0, 13)); // NOI18N
        jLabel3.setText("CPF:");
        jPanel1.add(jLabel3);
        jLabel3.setBounds(10, 70, 190, 20);

        jLabel4.setFont(new java.awt.Font("Microsoft New Tai Lue", 0, 13)); // NOI18N
        jLabel4.setText("DATA DE NASCIMENTO:");
        jPanel1.add(jLabel4);
        jLabel4.setBounds(210, 70, 141, 20);

        jLabel5.setFont(new java.awt.Font("Microsoft New Tai Lue", 0, 13)); // NOI18N
        jLabel5.setText("LOGRADOURO");
        jPanel1.add(jLabel5);
        jLabel5.setBounds(10, 120, 140, 20);
        jPanel1.add(tfenderecoaluno);
        tfenderecoaluno.setBounds(10, 140, 430, 24);

        jLabel6.setFont(new java.awt.Font("Microsoft New Tai Lue", 0, 13)); // NOI18N
        jLabel6.setText("BAIRRO:");
        jPanel1.add(jLabel6);
        jLabel6.setBounds(10, 170, 120, 20);
        jPanel1.add(tfbairroaluno);
        tfbairroaluno.setBounds(10, 190, 430, 24);
        jPanel1.add(tfnumeroaluno);
        tfnumeroaluno.setBounds(10, 240, 170, 24);

        jLabel7.setFont(new java.awt.Font("Microsoft New Tai Lue", 0, 13)); // NOI18N
        jLabel7.setText("NÚMERO");
        jPanel1.add(jLabel7);
        jLabel7.setBounds(10, 220, 170, 20);

        jLabel8.setFont(new java.awt.Font("Microsoft New Tai Lue", 0, 13)); // NOI18N
        jLabel8.setText("CEP:");
        jPanel1.add(jLabel8);
        jLabel8.setBounds(190, 220, 170, 20);
        jPanel1.add(tfcepaluno);
        tfcepaluno.setBounds(190, 240, 250, 24);

        jLabel9.setFont(new java.awt.Font("Microsoft New Tai Lue", 0, 13)); // NOI18N
        jLabel9.setText("SEXO:");
        jPanel1.add(jLabel9);
        jLabel9.setBounds(10, 270, 150, 20);

        jButton1.setFont(new java.awt.Font("Microsoft New Tai Lue", 0, 13)); // NOI18N
        jButton1.setText("Salvar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1);
        jButton1.setBounds(500, 360, 110, 30);

        cmbSexo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "M", "F" }));
        jPanel1.add(cmbSexo);
        cmbSexo.setBounds(10, 290, 170, 24);

        try {
            tfcpfaluno.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("###.###.###-##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        jPanel1.add(tfcpfaluno);
        tfcpfaluno.setBounds(10, 90, 180, 30);

        try {
            tfdatanascimentoaluno.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        jPanel1.add(tfdatanascimentoaluno);
        tfdatanascimentoaluno.setBounds(210, 90, 220, 30);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnIdentify, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 572, Short.MAX_VALUE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnIdentify, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        setBounds(0, 0, 728, 491);
    }// </editor-fold>//GEN-END:initComponents

      public void OnTakeOff(FTR_PROGRESS Progress) {
        txtMessage.setText("Retire o dedo e aguarde, por favor ...");
    }
      
        public void UpdateScreenImage(java.awt.image.BufferedImage Bitmap) {
        m_FingerPrintImage.setImage(Bitmap);
        FingerImage.repaint();
    }
        
        public boolean OnFakeSource(FTR_PROGRESS Progress) {
        int nResponse;
        nResponse = JOptionPane.showConfirmDialog(this,
                "Fake source detected. Do you want continue process?",
                getTitle(), JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        return (nResponse == JOptionPane.NO_OPTION);
    }
        
        public void OnEnrollmentComplete(boolean bSuccess, int nResult) {
        if (bSuccess) {
            // set status string
            txtMessage.setText("Processo de leitura biométrica realizada com sucesso. Quality: "
                    + ((FutronicEnrollment) m_Operation).getQuality());

            // Set template into passport and save it
            ((DbRecord) m_OperationObj).setTemplate(((FutronicEnrollment) m_Operation).getTemplate());
            try {
                ((DbRecord) m_OperationObj).Save(m_DbDir + File.separator + ((DbRecord) m_OperationObj).getUserName());
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this,
                        e.getMessage(),
                        getTitle(), JOptionPane.WARNING_MESSAGE);
            }

        } else {
            txtMessage.setText("Enrollment process failed. Error description: "
                    + FutronicSdkBase.SdkRetCode2Message(nResult));
        }

        m_Operation = null;
        m_OperationObj = null;
    }
        
        public void OnVerificationComplete(boolean bSuccess,
            int nResult,
            boolean bVerificationSuccess) {
        StringBuffer szResult = new StringBuffer();
        if (bSuccess) {
            if (bVerificationSuccess) {
                szResult.append("Verification is successful.");
                szResult.append("User Name: ");
                szResult.append(((DbRecord) m_OperationObj).getUserName());
            } else {
                szResult.append("Verification failed.");
            }
        } else {
            szResult.append("Verification process failed.");
            szResult.append("Error description: ");
            szResult.append(FutronicSdkBase.SdkRetCode2Message(nResult));
        }
        txtMessage.setText(szResult.toString());
        //SetIdentificationLimit(m_Operation.getIdentificationsLeft());
        m_Operation = null;
        m_OperationObj = null;
    }
         
         public void OnGetBaseTemplateComplete(boolean bSuccess, int nResult ) {
        StringBuffer szMessage = new StringBuffer();
        if (bSuccess) {
            txtMessage.setText("Starting identification...");
            Vector<DbRecord> Users = (Vector<DbRecord>) m_OperationObj;
            FtrIdentifyRecord[] rgRecords = new FtrIdentifyRecord[Users.size()];
            for (int iUsers = 0; iUsers < Users.size(); iUsers++) {
                rgRecords[iUsers] = Users.get(iUsers).getFtrIdentifyRecord();
            }

            FtrIdentifyResult result = new FtrIdentifyResult();

            nResult = ((FutronicIdentification) m_Operation).Identification(rgRecords, result);
            if (nResult == FutronicSdkBase.RETCODE_OK) {
                szMessage.append("Aluno identificado com sucesso...");
                if (result.m_Index != -1) {
                    getUserBySQL(Users.get(result.m_Index).getUserName());
                } else {
                    szMessage.append("not found");
                }
            } else {
                szMessage.append("Identification failed.");
                szMessage.append(FutronicSdkBase.SdkRetCode2Message(nResult));
            }
        } else {
            szMessage.append("Can not retrieve base template.");
            szMessage.append("Error description: ");
            szMessage.append(FutronicSdkBase.SdkRetCode2Message(nResult));
        }
        txtMessage.setText(szMessage.toString());
        m_Operation = null;
        m_OperationObj = null;
    }
         
    private void btnIdentifyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIdentifyActionPerformed
        //String m_DbDir = null;
        Vector<DbRecord> Users = DbRecord.ReadRecords(m_DbDir);
        if (Users.size() == 0) {
            JOptionPane.showMessageDialog(this,
                    "Não existe alunos cadastrados para verificação.",
                    getTitle(), JOptionPane.ERROR_MESSAGE);
            return;
        }
        m_OperationObj = Users;

        try {
            m_Operation = new FutronicIdentification();

            // Set control properties
            m_Operation.setFakeDetection(false);
            m_Operation.setFFDControl(true);
            m_Operation.setFARN(1);
            m_Operation.setVersion(VersionCompatible.ftr_version_previous);
            m_Operation.setFastMode(false);

            // start verification process
            ((FutronicIdentification) m_Operation).GetBaseTemplate(this);
            
        } catch (FutronicException e) {
            JOptionPane.showMessageDialog(this,
                    "Can not start identification operation.\nError description: " + e.getMessage(),
                    getTitle(), JOptionPane.ERROR_MESSAGE);
            m_Operation = null;
            m_OperationObj = null;
        }
    }//GEN-LAST:event_btnIdentifyActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            if (isChecked()) {
                if(this.update){
                    //CRAIR UMA SQL DE UPDATE NA TABELA ALUNO E ENDEREÇO.
                    //LIMPA OS CAMPOS DE NOVO PARA ESPERAR UM NOVO CADASTRO
                    //UPDATE DEVE SER FALSO, SO ASSIM O SISTEMA VAI ENTENDER QUE O PROXIMO PODE SER UM CADASTRO.

                    this.update = false;
                }else{
                    if (!isUserExists(tfcpfaluno.getText())) {
                        //aqui o soft da inicio a um processo paralelo
                        isGetIdentify(tfcpfaluno.getText());

                        //este processo munitora se o processo criando anteriormente foi executado
                        //leva como base a criação do arquivo com o cpf do aluno.
                        new Thread() {
                            @Override
                            public void run(){
                                try {
                                    File file = null;
                                    while(true){
                                        file = new File(System.getProperty("user.dir") + "\\img\\" + tfcpfaluno.getText().trim());
                                            if(file.exists()){
                                                insertInformation();
                                                break;
                                            }
                                        }
                                    } catch (Exception e) {
                                        JOptionPane.showMessageDialog(null, "Erro ao tentar salvar o cadastro."
                                            + "ERRO:" + e.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
                                    }
                                }
                            }.start();
                        } else {
                            JOptionPane.showMessageDialog(null, "Este usuário já esta cadastrado, por favor se identificar.", "Atenção", JOptionPane.WARNING_MESSAGE);
                        }
                    }
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erro ao tentar cadastrar o aluno.\n"
                    + "Erro: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
    }//GEN-LAST:event_jButton1ActionPerformed

public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(() -> {
            JFrame.setDefaultLookAndFeelDecorated(true);
            JDialog.setDefaultLookAndFeelDecorated(true);
            new MainForm().setVisible(true);
        });
    }

    /**
     * Get the database directory.
     *
     * @return the database directory.
     */
    static private String GetDatabaseDir() throws Exception {
        return System.getProperty("user.dir") + "\\img\\";
    }

    private boolean isUserExists(String szUserName) {
        File f = new File(m_DbDir, szUserName);
        return f.exists();
    }

    private void CreateFile(String cpf)  //criar arquivo de acordo com cpf
            throws AppException {
        File f = new File(m_DbDir, cpf);
        try {
            f.createNewFile();
            f.delete();
        } catch (Exception e) {
            throw new AppException("Can not create file " + cpf + " in database.");
        }
    }

    /**
     * Returns an ImageIcon, or null if the path was invalid.
     */
    protected static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = MainForm.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

    public Object getObject() throws Exception {
        Pessoa pessoa = new Pessoa();
        pessoa.setNome(tfnomealuno.getText().trim().toUpperCase());
        pessoa.setCpf(tfcpfaluno.getText().trim().toUpperCase());
        pessoa.setDataNascimento(Convert.convertDateInterfaceToDateDatabase(tfdatanascimentoaluno.getText()));
       // pessoa.setDataNascimento(tfdatanascimentoaluno.getText());
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy"); 
        //Date data = formato.parse(tfdatanascimentoaluno.getText());
       // pessoa.getDataNascimento();
        
        pessoa.setEndereco(tfenderecoaluno.getText().trim().toUpperCase());
        pessoa.setBairro(tfbairroaluno.getText().trim().toUpperCase());
        pessoa.setNumero(Integer.parseInt(tfnumeroaluno.getText().trim().toUpperCase()));
        pessoa.setCep(Integer.parseInt(tfcepaluno.getText().trim().replaceAll("-", "")));
       // pessoa.setSexo(this.cmbSexo.getSelectedItem().toString());
         if (cmbSexo.getSelectedIndex() == 0) {
            pessoa.setSexo(1);
        } else {
            pessoa.setSexo(0);
        }
        
         
        return pessoa;
    }
    
    private void limpaFormulario() {
        tfnomealuno.setText(null);
        tfbairroaluno.setText(null);
        tfcepaluno.setText(null);
        tfnumeroaluno.setText(null);
        tfenderecoaluno.setText(null);
        tfcpfaluno.setText(null);
        tfdatanascimentoaluno.setText(null);
    }

    private boolean isGetIdentify(String cpf) throws Exception {
        boolean yes = false;
        CreateFile(cpf);
                
        m_OperationObj = new DbRecord();
        ((DbRecord) m_OperationObj).setUserName(cpf);
        m_Operation = new FutronicEnrollment();

        // Set control properties
        m_Operation.setFakeDetection(false);
        m_Operation.setFFDControl(true);
        m_Operation.setFARN(1);
        m_Operation.setFastMode(false);
        ((FutronicEnrollment) m_Operation).setMIOTControlOff(false);
        ((FutronicEnrollment) m_Operation).setMaxModels(3);
        m_Operation.setVersion(VersionCompatible.ftr_version_previous);

        // start enrollment process
        ((FutronicEnrollment) m_Operation).Enrollment(this);

        return yes;
    }

    class FARNValueVerifier extends InputVerifier {

        public boolean shouldYieldFocus(JComponent input) {
            String szErrorMessage = null;
            boolean bRetCode = true;

            JTextField tf = (JTextField) input;
            int nValue = -1;
            try {
                nValue = Integer.parseInt(tf.getText());
            } catch (NumberFormatException e) {
                szErrorMessage = "Invalid FARN value. Only digits are permited";
                bRetCode = false;
            }
            if (bRetCode && (nValue > 1000 || nValue < 1)) {
                szErrorMessage = "Invalid FARN value. The range of value is from 1 to 1000";
                bRetCode = false;
            }
            if (!bRetCode) {
                java.awt.Container myFrame = input;
                do {
                    myFrame = myFrame.getParent();
                } while (myFrame.getParent() != null);
                JOptionPane.showMessageDialog(myFrame,
                        szErrorMessage,
                        ((JFrame) myFrame).getTitle(), JOptionPane.ERROR_MESSAGE);
            }
            return bRetCode;
        }

        public boolean verify(JComponent input) {
            return true;
        }
    }

    class MyIcon implements Icon {

        public MyIcon() {
            m_Image = null;
        }

        public void paintIcon(Component c, Graphics g, int x, int y) {
            if (m_Image != null) {
                g.drawImage(m_Image, x, y, getIconWidth(), getIconHeight(), null);
            } else {
                g.fillRect(x, y, getIconWidth(), getIconHeight());
            }
        }

        public int getIconWidth() {
            return 160;
        }

        public int getIconHeight() {
            return 210;
        }

        public boolean LoadImage(String path) {
            boolean bRetCode = false;
            Image newImg;
            try {
                File f = new File(path);
                newImg = ImageIO.read(f);
                bRetCode = true;
                setImage(newImg);
            } catch (IOException e) {
            }

            return bRetCode;
        }

        public void setImage(Image Img) {
            if (Img != null) {
                m_Image = Img.getScaledInstance(getIconWidth(), getIconHeight(), Image.SCALE_FAST);
            } else {
                m_Image = null;
            }
        }

        private Image m_Image;
    }

    private static final FarnValues[] m_FarnValue2Index
            = {
                FarnValues.farn_low,
                FarnValues.farn_below_normal,
                FarnValues.farn_normal,
                FarnValues.farn_above_normal,
                FarnValues.farn_high,
                FarnValues.farn_max,
                FarnValues.farn_custom
            };

    private MyIcon m_FingerPrintImage;

    /**
     * Contain reference for current operation object
     */
    private FutronicSdkBase m_Operation;

    /**
     * A database directory name.
     */
    private String m_DbDir;

    /**
     * The type of this parameter is depending from current operation. For
     * enrollment operation this is DbRecord.
     */
    private Object m_OperationObj;

    public void teste() {
        try {
            // Get user name
            String szUserName = JOptionPane.showInputDialog(this, "Enter user name", "User name", JOptionPane.PLAIN_MESSAGE);
            System.out.println(szUserName);
            if (szUserName == null || szUserName.length() == 0) {
                JOptionPane.showMessageDialog(this,
                        "You must enter a user name.",
                        getTitle(), JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Try creat the file for template
            if (isUserExists(szUserName)) {
                int nResponse;
                nResponse = JOptionPane.showConfirmDialog(this,
                        "User already exists. Do you want replace it?",
                        getTitle(),
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);
                System.out.println("usuario existente.");
                if (nResponse == JOptionPane.NO_OPTION) {
                    return;
                }
            } else {
                CreateFile(szUserName);

            }

            m_OperationObj = new DbRecord();
            ((DbRecord) m_OperationObj).setUserName(szUserName);

            m_Operation = new FutronicEnrollment();

            // Set control properties
            m_Operation.setFakeDetection(false);
            m_Operation.setFFDControl(true);
            m_Operation.setFARN(1);
            m_Operation.setFastMode(false);
            ((FutronicEnrollment) m_Operation).setMIOTControlOff(false);
            ((FutronicEnrollment) m_Operation).setMaxModels(3);
            m_Operation.setVersion(VersionCompatible.ftr_version_previous);

            // start enrollment process
            ((FutronicEnrollment) m_Operation).Enrollment(this);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Can not start enrollment operation.\nError description: " + e.getMessage(),
                    getTitle(), JOptionPane.ERROR_MESSAGE);
            m_Operation = null;
            m_OperationObj = null;
            System.out.println("erro!");
        }

    }
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel FingerImage;
    private javax.swing.JButton btnIdentify;
    private javax.swing.JComboBox<String> cmbSexo;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField tfbairroaluno;
    private javax.swing.JTextField tfcepaluno;
    private javax.swing.JFormattedTextField tfcpfaluno;
    private javax.swing.JFormattedTextField tfdatanascimentoaluno;
    private javax.swing.JTextField tfenderecoaluno;
    private javax.swing.JTextField tfnomealuno;
    private javax.swing.JTextField tfnumeroaluno;
    private javax.swing.JTextField txtMessage;
    // End of variables declaration//GEN-END:variables

    private boolean isChecked() throws Exception {
        boolean yes = false;

        if (tfnomealuno.getText().trim().length() > 0) {
            yes = true;
        } else {
            JOptionPane.showMessageDialog(null, "Informe o nome do aluno.");
            this.tfnomealuno.requestFocus();
            this.tfnomealuno.selectAll();
        }

        return yes;
    }
    
    public void insertInformation() throws Exception {
        AlunoDao alunoDao = new AlunoDao();
        alunoDao.insere((Pessoa) getObject());
        JOptionPane.showMessageDialog(null, "Usuário cadastrado com sucesso.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
       
        limpaFormulario();
        //Criar um metodo que limpa os campos para um novo cadastro
        //Colocar o foco no primeiro campo.
        //Resetar o componente FingerImage
    }
    
    public void getUserBySQL(String cpf) {
      //  System.out.println("CPF A SER UTILIZADO NA WHERE DA SQL:" JO);
        JOptionPane.showMessageDialog(null, "Usuário cadastrado com sucesso"+cpf);
        System.out.println("VAMOS LA! HORA DE FAZR A CONSULTA SQL PARA PREEENCHER O RESTANTE DAS INFORMAÇÕES");
        
        this.update = true;
    }

   //ENROLLMENT PROCESS FINISHED SU @Override
    //public void OnVerificationComplete(boolean bSuccess, int nResult, boolean bVerificationSuccess) {
     //   throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
   // }

   // @Override
   // public void OnGetBaseTemplateComplete(boolean bSuccess, int nResult) {
   //     throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
   // }

   // private boolean isUserExists(String text) {
   //     throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
   // }

    //private void isGetIdentify(String text) {
     //   throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
   // }
}
