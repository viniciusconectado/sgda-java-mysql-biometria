
package com.futronic.workedex;

import br.com.sgda.dao.AlunoDao;
import br.com.sgda.jdbc.Conexao;
import br.com.sgda.modelo.Pessoa;
import java.awt.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;
import com.futronic.SDKHelper.*;
import com.sun.jna.platform.win32.*;
import java.text.ParseException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.MaskFormatter;
import util.Convert;
import util.GenericCalendar;

public class MainForm extends javax.swing.JFrame
        implements IEnrollmentCallBack, IVerificationCallBack, IIdentificationCallBack {

    static final long serialVersionUID = 1L;
    private boolean update;

    /**
     * Creates new form MainForm
     */
    public MainForm() {
        
        initComponents();
        m_FingerPrintImage = new MyIcon();
        FingerImage.setIcon(m_FingerPrintImage);
        
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

    ////////////////////////////////////////////////////////////////////
    // ICallBack interface implementation
    ////////////////////////////////////////////////////////////////////
    /**
     * The "Put your finger on the scanner" event.
     *
     * @param Progress the current progress data structure.
     */
    public void OnPutOn(FTR_PROGRESS Progress) {
        txtMessage.setText("Pressione o dedo no leitor e aguarde ...");
    }

    /**
     * The "Take off your finger from the scanner" event.
     *
     * @param Progress the current progress data structure.
     */
    public void OnTakeOff(FTR_PROGRESS Progress) {
        txtMessage.setText("Take off finger from device, please ...");
    }

    /**
     * The "Show the current fingerprint image" event.
     *
     * @param Bitmap the instance of Bitmap class with fingerprint image.
     */
    public void UpdateScreenImage(java.awt.image.BufferedImage Bitmap) {
        m_FingerPrintImage.setImage(Bitmap);
        FingerImage.repaint();
    }

    /**
     * The "Fake finger detected" event.
     *
     * @param Progress the fingerprint image.
     *
     * @return <code>true</code> if the current indetntification operation
     * should be aborted, otherwise is <code>false</code>
     */
    public boolean OnFakeSource(FTR_PROGRESS Progress) {
        int nResponse;
        nResponse = JOptionPane.showConfirmDialog(this,
                "Fake source detected. Do you want continue process?",
                getTitle(), JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        return (nResponse == JOptionPane.NO_OPTION);
    }

    ////////////////////////////////////////////////////////////////////
    // ICallBack interface implementation
    ////////////////////////////////////////////////////////////////////
    /**
     * The "Enrollment operation complete" event.
     *
     * @param bSuccess <code>true</code> if the operation succeeds, otherwise is
     * <code>false</code>.
     * @param The Futronic SDK return code (see FTRAPI.h).
     */
    public void OnEnrollmentComplete(boolean bSuccess, int nResult) {
        if (bSuccess) {
            // set status string
            txtMessage.setText("Enrollment process finished successfully. Quality: "
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

    /**
     * The "Verification operation complete" event.
     *
     * @param bSuccess <code>true</code> if the operation succeeds, otherwise is
     * <code>false</code>
     * @param nResult the Futronic SDK return code.
     * @param bVerificationSuccess if the operation succeeds (bSuccess is
     * <code>true</code>), this parameters shows the verification operation
     * result. <code>true</code> if the captured from the attached scanner
     * template is matched, otherwise is <code>false</code>.
     */
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

    /**
     * The "Get base template operation complete" event.
     *
     * @param bSuccess <code>true</code> if the operation succeeds, otherwise is
     * <code>false</code>.
     * @param nResult The Futronic SDK return code.
     */
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

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnIdentify = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        FingerImage = new javax.swing.JLabel();
        txtMessage = new javax.swing.JTextField();
        tfnomealuno = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        tfdatanascimentoaluno = new javax.swing.JTextField();
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
        jPanel2 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Java example for Futronic SDK v. 4.2");
        setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        getContentPane().setLayout(null);

        btnIdentify.setFont(new java.awt.Font("Microsoft New Tai Lue", 0, 12)); // NOI18N
        btnIdentify.setText("IDENTIFICAR!");
        btnIdentify.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIdentifyActionPerformed(evt);
            }
        });
        getContentPane().add(btnIdentify);
        btnIdentify.setBounds(10, 10, 120, 30);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Informações do Aluno"));
        jPanel1.setLayout(null);

        FingerImage.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        FingerImage.setMaximumSize(new java.awt.Dimension(160, 210));
        FingerImage.setMinimumSize(new java.awt.Dimension(160, 210));
        FingerImage.setPreferredSize(new java.awt.Dimension(160, 210));
        jPanel1.add(FingerImage);
        FingerImage.setBounds(450, 10, 160, 210);

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
        jPanel1.add(tfdatanascimentoaluno);
        tfdatanascimentoaluno.setBounds(210, 90, 230, 24);

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
        jPanel1.add(tfcpfaluno);
        tfcpfaluno.setBounds(10, 90, 170, 30);

        getContentPane().add(jPanel1);
        jPanel1.setBounds(10, 50, 620, 400);

        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        org.jdesktop.layout.GroupLayout jPanel2Layout = new org.jdesktop.layout.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 616, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 46, Short.MAX_VALUE)
        );

        getContentPane().add(jPanel2);
        jPanel2.setBounds(10, 460, 620, 50);

        setSize(new java.awt.Dimension(651, 562));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnIdentifyActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnIdentifyActionPerformed
    {//GEN-HEADEREND:event_btnIdentifyActionPerformed
        
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

    private void formWindowClosing(java.awt.event.WindowEvent evt)//GEN-FIRST:event_formWindowClosing
    {//GEN-HEADEREND:event_formWindowClosing
        if (m_Operation != null) {
            m_Operation.Dispose();
        }
    }//GEN-LAST:event_formWindowClosing

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

    private void CreateFile(String cpf)
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
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTextField tfbairroaluno;
    private javax.swing.JTextField tfcepaluno;
    private javax.swing.JFormattedTextField tfcpfaluno;
    private javax.swing.JTextField tfdatanascimentoaluno;
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
        //Criar um metodo que limpa os campos para um novo cadastro
        //Colocar o foco no primeiro campo.
        //Resetar o componente FingerImage
    }
    
    public void getUserBySQL(String cpf) {
        System.out.println("CPF A SER UTILIZADO NA WHERE DA SQL: " + cpf);
        //JOptionPane.showMessageDialog(null, "Usuário cadastrado com sucesso"+cpf);
        System.out.println("VAMOS LA! HORA DE FAZR A CONSULTA SQL PARA PREEENCHER O RESTANTE DAS INFORMAÇÕES");
        
        this.update = true;
    }
    
    
}
