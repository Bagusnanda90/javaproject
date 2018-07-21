/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * AttendanceApp.java
 *
 * Created on Oct 18, 2011, 11:16:26 PM
 */
package com.dimata.harisma.utility.machine;

import com.dimata.system.entity.PstSystemProperty;
import java.util.Date;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Scanner;
import java.util.Vector;
import javax.swing.JComponent;
/**
 *
 * @author ktanjana
 */
public class AttendanceApp extends javax.swing.JFrame {
        String TKEEPING_TYPE = PstSystemProperty.getValueByName("TIMEKEEPING_TYPE");
        String strStatusSvrmgrMachine = "";
        String strButtonStatusMachine = "";
        String strStatusSvrmgrPresence = "";
        String strButtonStatusPresence = "";
        String strStatusPortStatus = "";
        String strButtonPortStatus = "";
        String sTime01 = "";
        String sTime02 = "";    /*GEDE_20110901_01 {*/
        TransManager svrmgrMachine = TransManager.getInstance(false);  /* }*/        
    

    /** Creates new form AttendanceApp */
    public AttendanceApp() {
        initComponents();
         if(svrmgrMachine!=null){
             svrmgrMachine.setTextArea(jTextArea1);
         }
                             String odbcClasses = PstSystemProperty.getValueByName("ATT_MACHINE_ODBC_CLASS");
                             Vector vClass = com.dimata.util.StringParser.parseGroup(odbcClasses);  
  /*Vector vClassSysProp = com.dimata.util.StringParser.parseGroup(odbcClasses);
 Hashtable attMachClass = new Hashtable(); 
 Vector vClass = new Vector();
 if(vClassSysProp !=null && vClassSysProp.size() > 0){  
     for(int idx=0;idx<vClassSysProp.size();idx++){        
        String[] strGrup = (String[]) vClassSysProp.get(idx); 
        if(strGrup!=null && strGrup.length>0 ){
            String className = "com.dimata.harisma.utility.machine."+strGrup[0];
            String user ="";
            String pwd ="";
            String dsnName="";
            String host ="";
            String port ="";            
            if(strGrup.length>1){
                Vector vParams = com.dimata.util.StringParser.parseGroup(strGrup[1],"&","=");
                if(vParams!=null && vParams.size()>0){
                    for(int i=0;i<vParams.size();i++){
                       String[] strParam = (String[]) vParams.get(i);
                       if(strParam!=null && strParam.length>0 ){
                          String paramName  = strParam[0];
                          String paramValue = strParam.length >1 ? strParam[1]:"";
                          if(paramName!=null && paramName.equalsIgnoreCase("user")){
                            user = paramValue;  
                          } else if(paramName!=null && paramName.equalsIgnoreCase("pwd")){
                            pwd = paramValue;  
                          } else if(paramName!=null && paramName.equalsIgnoreCase("dsnName")){
                            dsnName = paramValue;  
                          }else if(paramName!=null && paramName.equalsIgnoreCase("host")){
                            host = paramValue;  
                          }else if(paramName!=null && paramName.equalsIgnoreCase("port")){
                            port = paramValue;  
                          }
                       }                       
                    }
                }
           }
           DBMachineConfig dbC = new DBMachineConfig();
           dbC.setClassName(className);
           dbC.setUser(user);
           dbC.setPwd(pwd);
           dbC.setDsn(dsnName);
           dbC.setHost(host);
           dbC.setPort(port);
           attMachClass.put(className,dbC );
           vClass.add(className);
       }
   }
 }*/            
 

                              if(vClass!=null && vClass.size()>0){
                                  for(int i=0; i<vClass.size();i++){
                                      String[] classA = (String[]) vClass.get(i);
                                      svrmgrMachine.addTxtProcessClass("com.dimata.harisma.utility.machine."+classA[0]);                                      
                                      switch(i){
                                          case 0:
                                              jLabel1.setText(classA[0]);
                                              svrmgrMachine.setProgressBar(0, jProgressBar1);
                                              break;
                                          case 1:
                                              jLabel2.setText(classA[0]);
                                              svrmgrMachine.setProgressBar(1, jProgressBar2);
                                              break;
                                          case 2:
                                              jLabel3.setText(classA[0]);
                                              svrmgrMachine.setProgressBar(2, jProgressBar3);
                                              break;
                                          case 3:
                                              jLabel4.setText(classA[0]);
                                              svrmgrMachine.setProgressBar(3, jProgressBar4);
                                              break;
                                      }
                                  }
                                   if ( svrmgrMachine.getTxtProcessClassSize()>0) {
                                    for(int i =0; i< svrmgrMachine.getTxtProcessClassSize();i++) {
                                            
                                    }
                                    }
                              }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jProgressBar1 = new javax.swing.JProgressBar();
        jProgressBar2 = new javax.swing.JProgressBar();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jProgressBar3 = new javax.swing.JProgressBar();
        jLabel4 = new javax.swing.JLabel();
        jProgressBar4 = new javax.swing.JProgressBar();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel5 = new javax.swing.JLabel();
        dtTo = new com.toedter.calendar.JDateChooser();
        jLabel6 = new javax.swing.JLabel();
        dtFrom = new com.toedter.calendar.JDateChooser();
        jLabel7 = new javax.swing.JLabel();

        jButton1.setText("jButton1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButton2.setText("Start Data Transfer");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel1.setText("-");

        jLabel2.setText("-");

        jLabel3.setText("-");

        jLabel4.setText("-");

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jLabel5.setText("Dimata - Attendance Machine Data Transfer");

        dtTo.setName("dateTo"); // NOI18N

        jLabel6.setText("From");

        dtFrom.setName("dateFrom"); // NOI18N

        jLabel7.setText("To");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(dtTo, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(93, 93, 93)
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(dtFrom, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2)
                        .addContainerGap())
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel4))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGap(12, 12, 12)))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(0, 0, Short.MAX_VALUE))
                                .addComponent(jProgressBar1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jProgressBar3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jProgressBar4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jProgressBar2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGap(32, 32, 32))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addComponent(jScrollPane1)
                            .addContainerGap()))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2)
                    .addComponent(jProgressBar2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3)
                    .addComponent(jProgressBar3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jProgressBar4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dtFrom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(dtTo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(22, 22, 22))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
// TODO add your handling code here:
    if(!svrmgrMachine.running){
        try 
        {
            svrmgrMachine.startTransfer(dtFrom.getDate(), dtTo.getDate());
          //  svrmgrMachine.startTransfer();
            
            jButton2.setText("Stop Data Transfer");

        }
        catch (Exception e) 
        {
                jTextArea1.setText("\t Exception svrmgrMachine.startTransfer() = " + e);
        }
    } else{
        try 
        {
                svrmgrMachine.stopTransfer();                
                jButton2.setText("Start Data Transfer");
        }
        catch (Exception e) 
        {
                jTextArea1.setText("\t Exception svrmgrMachine.stopWatcherMachine() = " + e);
        }
    }
    
}//GEN-LAST:event_jButton2ActionPerformed

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
            java.util.logging.Logger.getLogger(AttendanceApp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AttendanceApp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AttendanceApp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AttendanceApp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new AttendanceApp().setVisible(true);
                
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JDateChooser dtFrom;
    private com.toedter.calendar.JDateChooser dtTo;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JProgressBar jProgressBar2;
    private javax.swing.JProgressBar jProgressBar3;
    private javax.swing.JProgressBar jProgressBar4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    // End of variables declaration//GEN-END:variables
}
