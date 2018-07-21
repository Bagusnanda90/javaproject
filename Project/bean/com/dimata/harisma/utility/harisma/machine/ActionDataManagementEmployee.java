/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.utility.harisma.machine;

import com.dimata.harisma.entity.masterdata.Position;
import com.dimata.harisma.session.aplikasidesktop.attendance.SessAplicationDestopAbsensiAttendance;
import com.dimata.harisma.utility.harisma.machine.transferdataemployee.PstPositionDestopTransfer;
import com.dimata.harisma.utility.harisma.machine.transferdataemployee.PstScheduleDestopTransfer;
import com.dimata.harisma.utility.harisma.masterdatadesktop.entity.EmployeeDummyTransfer;
import com.dimata.harisma.utility.harisma.masterdatadesktop.entity.PstEmployeeDummyTransfer;
import com.dimata.harisma.utility.harisma.masterdatadesktop.entity.ScheduleSymbolDesktop;
import com.dimata.util.JComboBoxItem;
import java.awt.Color;
import java.util.Vector;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JRootPane;

/**
 *
 * @author Dimata 007
 */
public class ActionDataManagementEmployee extends javax.swing.JFrame {

    /**
     * Creates new form ActionDataManagementEmployee
     */
    public static final int STATUS_AKTIV = 0;
    public static final int STATUS_NON_AKTIV = 1;
    public static final int STATUS_TRANSFER = 2;
    public static final String[] fieldNameStatus = {
        "Aktiv",
        "Non Aktiv",
        "Transfer",};
    public static int RSLT_OK = 0;
    public static int RSLT_UNKNOWN_ERROR = 1;
    public static int RSLT_EST_CODE_EXIST = 2;
    public static int RSLT_FORM_INCOMPLETE = 3;
    public static int RSLT_OK_REFESH = 4;
    public static String[][] resultText = {
        {"Berhasil", "Tidak dapat diproses", "NoPerkiraan sudah ada", "Data tidak lengkap", "Berhasil, Silahkan refesh halaman"},
        {"Succes", "Can not process", "Estimation code exist", "Data incomplete", "Succes, Please refesh page"}
    };
    public static int DEFAULT_LANGUAGE = 0;
    public static int ENGLISH_LANGUAGE = 1;

    public ActionDataManagementEmployee(String oidTransEmp) {
        initComponents();
        txtOidEmpTransfer.setVisible(false);

        BodyContainer.setBackground(SessAplicationDestopAbsensiAttendance.getColor() != null ? SessAplicationDestopAbsensiAttendance.getColor() : Color.WHITE);
        Vector listSchedule = PstScheduleDestopTransfer.List(0, 0, "", PstScheduleDestopTransfer.fieldNames[PstScheduleDestopTransfer.FLD_SYMBOL] + " ASC ");
        Vector listPosition = PstPositionDestopTransfer.list(0, 0, "", PstPositionDestopTransfer.fieldNames[PstPositionDestopTransfer.FLD_POSITION] + " ASC ");
//        JComboBoxItem jComboBoxItem = new JComboBoxItem();
         this.setResizable(false); 
        JComboBoxItem jComboBoxItem = new JComboBoxItem();
        if (oidTransEmp == null || oidTransEmp.length() == 0 || oidTransEmp.equalsIgnoreCase("0")) {
            btnDelete.setVisible(false);
        }
        Vector key = new Vector();
        Vector value = new Vector();
        Vector keyPos = new Vector();
        Vector valuePos = new Vector();
        Vector keySts = new Vector();
        Vector valueSts = new Vector();

        key.add("-select-");
        value.add("0");

        if (listSchedule != null) {
            for (int x = 0; x < listSchedule.size(); x++) {
                ScheduleSymbolDesktop scheduleSymbolDesktop = (ScheduleSymbolDesktop) listSchedule.get(x);
                key.add(scheduleSymbolDesktop.getSymbol() + " [" + scheduleSymbolDesktop.getSschTimeIn() + "," + scheduleSymbolDesktop.getSschTimeOut() + "]");
                value.add("" + scheduleSymbolDesktop.getOID());

            }
        }


        keyPos.add("-select-");
        valuePos.add("0");
        if (listSchedule != null) {
            for (int x = 0; x < listPosition.size(); x++) {
                Position position = (Position) listPosition.get(x);
                keyPos.add(position.getPosition());
                valuePos.add("" + position.getOID());
            }
        }

        valueSts.add("" + STATUS_AKTIV);
        valueSts.add("" + STATUS_NON_AKTIV);
        valueSts.add("" + STATUS_TRANSFER);

        keySts.add("Aktiv");
        keySts.add("Non-AKtiv");
        keySts.add("Transfer");

        if (oidTransEmp != null && oidTransEmp.length() > 0 && !oidTransEmp.equalsIgnoreCase("0")) {
            try {
                headerEmployeeManagement.setText(" Update Employee Management");
                String sOIdTrans[] = oidTransEmp.split("_");
                long oidTrans = 0;
                int empSts = 0;
                try {
                    if (sOIdTrans != null && sOIdTrans.length == 2) {
                        oidTrans = Long.parseLong((String) sOIdTrans[0]);
                        empSts = Integer.parseInt(sOIdTrans[1]);
                    }
                } catch (Exception exc) {
                }

                if (oidTrans != 0) {
                    if (empSts == STATUS_TRANSFER) {
                        EmployeeDummyTransfer employeeDummyTransfer = PstEmployeeDummyTransfer.fetchExc(oidTrans);
                        txtEmpNum.setText(employeeDummyTransfer.getEmployeeNum());
                        txtEmpName.setText(employeeDummyTransfer.getEmployeeFullName());
                        txtEmpPin.setText(employeeDummyTransfer.getEmployeePin());
                        jComboBoxItem.drawCombobox(cbxSts, "" + employeeDummyTransfer.getStatusEmployee(), keySts, valueSts);
                        jComboBoxItem.drawCombobox(cbxPos, "" + employeeDummyTransfer.getPositionId(), keyPos, valuePos);
                        jComboBoxItem.drawCombobox(cbxSch, "" + employeeDummyTransfer.getScheduleId(), key, value);
                        txtOidEmpTransfer.setText("" + oidTrans);
                    } else {
                        //maka yg dipilih pasti yg aktiv ato yg non aktiv
                    }

                } else {
                    headerEmployeeManagement.setText(" Add Employee Management");
                    txtEmpNum.setText("");
                    txtEmpName.setText("");
                    txtEmpPin.setText("");
                    jComboBoxItem.drawCombobox(cbxSts, "" + STATUS_TRANSFER, keySts, valueSts);
                    jComboBoxItem.drawCombobox(cbxPos, null, keyPos, valuePos);
                    jComboBoxItem.drawCombobox(cbxSch, null, key, value);
                    txtOidEmpTransfer.setText("0");
                }

            } catch (Exception exc) {
                JOptionPane.showMessageDialog(null, exc);
            }
        } else {
            headerEmployeeManagement.setText(" Add Employee Management");
            txtEmpNum.setText("");
            txtEmpName.setText("");
            txtEmpPin.setText("");
            jComboBoxItem.drawCombobox(cbxSts, "" + STATUS_TRANSFER, keySts, valueSts);
            jComboBoxItem.drawCombobox(cbxPos, null, keyPos, valuePos);
            jComboBoxItem.drawCombobox(cbxSch, null, key, value);
            txtOidEmpTransfer.setText("0");

        }
        setLocationRelativeTo(this);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        BodyContainer = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        headerEmployeeManagement = new javax.swing.JLabel();
        txtEmpNum = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtEmpName = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        cbxSts = new javax.swing.JComboBox();
        cbxPos = new javax.swing.JComboBox();
        cbxSch = new javax.swing.JComboBox();
        btnSave = new javax.swing.JButton();
        btnClose = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        txtOidEmpTransfer = new javax.swing.JTextField();
        lblEmpPin = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txtEmpPin = new javax.swing.JTextField();

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("Employee Number");

        headerEmployeeManagement.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        headerEmployeeManagement.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        headerEmployeeManagement.setText("Employee Management");
        headerEmployeeManagement.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setText("Nama Employee");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setText("Status");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setText("Position");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setText("Schedule");

        jLabel8.setText(":");

        jLabel9.setText(":");

        jLabel10.setText(":");

        jLabel11.setText(":");

        jLabel12.setText(":");

        btnSave.setText("Save");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnClose.setText("Close");
        btnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseActionPerformed(evt);
            }
        });

        btnDelete.setText("Delete");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        lblEmpPin.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblEmpPin.setText("Employee Pin");

        jLabel13.setText(":");

        javax.swing.GroupLayout BodyContainerLayout = new javax.swing.GroupLayout(BodyContainer);
        BodyContainer.setLayout(BodyContainerLayout);
        BodyContainerLayout.setHorizontalGroup(
            BodyContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BodyContainerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(BodyContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtOidEmpTransfer, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(headerEmployeeManagement, javax.swing.GroupLayout.PREFERRED_SIZE, 443, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, BodyContainerLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(BodyContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, BodyContainerLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtEmpNum, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, BodyContainerLayout.createSequentialGroup()
                        .addComponent(lblEmpPin)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtEmpPin, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, BodyContainerLayout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbxSts, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, BodyContainerLayout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtEmpName, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, BodyContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(BodyContainerLayout.createSequentialGroup()
                            .addComponent(btnSave)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btnDelete)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btnClose))
                        .addGroup(BodyContainerLayout.createSequentialGroup()
                            .addGroup(BodyContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, BodyContainerLayout.createSequentialGroup()
                                    .addComponent(jLabel5)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, BodyContainerLayout.createSequentialGroup()
                                    .addComponent(jLabel6)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(BodyContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(cbxPos, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(cbxSch, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(43, 43, 43))
        );
        BodyContainerLayout.setVerticalGroup(
            BodyContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BodyContainerLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(headerEmployeeManagement)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                .addComponent(txtOidEmpTransfer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(BodyContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtEmpNum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(BodyContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblEmpPin)
                    .addComponent(txtEmpPin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(BodyContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtEmpName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addGap(4, 4, 4)
                .addGroup(BodyContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel10)
                    .addComponent(cbxSts, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(BodyContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel11)
                    .addComponent(cbxPos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(BodyContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel12)
                    .addComponent(cbxSch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(BodyContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSave)
                    .addComponent(btnClose)
                    .addComponent(btnDelete))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(BodyContainer, javax.swing.GroupLayout.PREFERRED_SIZE, 432, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(BodyContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        // TODO add your handling code here:
        try {
            Object itemSch = cbxSch.getSelectedItem();
            String valueSch = ((JComboBoxItem) itemSch).getValue();

            Object itemPos = cbxPos.getSelectedItem();
            String valuePos = ((JComboBoxItem) itemPos).getValue();

            Object itemSts = cbxSts.getSelectedItem();
            String valueSts = ((JComboBoxItem) itemSts).getValue();

            EmployeeDummyTransfer employeeDummyTransfer = new EmployeeDummyTransfer();
            employeeDummyTransfer.setEmployeeFullName(txtEmpName.getText());
            employeeDummyTransfer.setEmployeeNum(txtEmpNum.getText());
            employeeDummyTransfer.setEmployeePin(txtEmpPin.getText());
            employeeDummyTransfer.setPositionId(valuePos != null && valuePos.length() > 0 ? Long.parseLong(valuePos) : 0);
            employeeDummyTransfer.setScheduleId(valueSch != null && valueSch.length() > 0 ? Long.parseLong(valueSch) : 0);
            employeeDummyTransfer.setStatusEmployee(valueSts != null && valueSts.length() > 0 ? Integer.parseInt(valueSts) : 0);
            employeeDummyTransfer.setOID(Long.parseLong(txtOidEmpTransfer.getText()));
            employeeDummyTransfer.setLoginUser(SessAplicationDestopAbsensiAttendance.getOidUserLogin());
            if (employeeDummyTransfer.getOID() == 0) {
                try {
                    long oid = PstEmployeeDummyTransfer.insertExc(employeeDummyTransfer);
                    if (oid != 0) {
                        JOptionPane.showMessageDialog(null, resultText[DEFAULT_LANGUAGE][RSLT_OK_REFESH]);
                        this.dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, resultText[DEFAULT_LANGUAGE][RSLT_UNKNOWN_ERROR]);
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Error" + e);
                }
            } else {
                try {
                    long oid = PstEmployeeDummyTransfer.updateExc(employeeDummyTransfer);
                    if (oid != 0) {
                        JOptionPane.showMessageDialog(null, resultText[DEFAULT_LANGUAGE][RSLT_OK_REFESH]);
                        this.dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, resultText[DEFAULT_LANGUAGE][RSLT_UNKNOWN_ERROR]);
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Error" + e);
                }
            }
        } catch (Exception e) {
        }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btnCloseActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        // TODO add your handling code here:
        try {
            long oidSelect = txtOidEmpTransfer != null && txtOidEmpTransfer.getText().length() > 0 ? Long.parseLong(txtOidEmpTransfer.getText()) : 0;
            if (oidSelect != 0) {
                int messageType = JOptionPane.QUESTION_MESSAGE;
                String[] options = {"Yes", "Cancel"};
                int choice = JOptionPane.showInternalOptionDialog(
                        this.getContentPane(),
                        "Are you sure to delete?",
                        "Option Dialog Box", 0, messageType,
                        null, options, "yes");
                if (choice == 0) {
                    long oid = PstEmployeeDummyTransfer.deleteExc(oidSelect);
                    if (oid != 0) {
                        JOptionPane.showMessageDialog(null, resultText[DEFAULT_LANGUAGE][RSLT_OK_REFESH]);
                        this.dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, resultText[DEFAULT_LANGUAGE][RSLT_UNKNOWN_ERROR]);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, resultText[DEFAULT_LANGUAGE][RSLT_FORM_INCOMPLETE]);
            }


        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error" + e);
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ActionDataManagementEmployee.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ActionDataManagementEmployee.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ActionDataManagementEmployee.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ActionDataManagementEmployee.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ActionDataManagementEmployee("0").setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel BodyContainer;
    private javax.swing.JButton btnClose;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnSave;
    private javax.swing.JComboBox cbxPos;
    private javax.swing.JComboBox cbxSch;
    private javax.swing.JComboBox cbxSts;
    private javax.swing.JLabel headerEmployeeManagement;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel lblEmpPin;
    private javax.swing.JTextField txtEmpName;
    private javax.swing.JTextField txtEmpNum;
    private javax.swing.JTextField txtEmpPin;
    private javax.swing.JTextField txtOidEmpTransfer;
    // End of variables declaration//GEN-END:variables
}
