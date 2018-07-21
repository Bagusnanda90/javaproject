package com.dimata.harisma.utility.harisma.machine;

import com.dimata.harisma.session.aplikasidesktop.attendance.SessAplicationDestopAbsensiAttendance;
import com.dimata.harisma.utility.harisma.machine.db.DBHandler;
import com.dimata.harisma.utility.harisma.machine.transferdataemployee.ServiceEmployeeOutletTransfer;
import com.dimata.harisma.utility.harisma.machine.transferdataemployee.ServiceEmployeeOutletTransferAutoD;
import javax.swing.*;
import java.awt.*;
import java.util.Date;

public class Progress {

    static JProgressBar JP;
    private JLabel headerLabel;
    private JFrame frame;
    private JPanel panel;
    SessAplicationDestopAbsensiAttendance sessAplicationDestopAbsensiAttendance = new SessAplicationDestopAbsensiAttendance();
    ServiceEmployeeOutletTransferAutoD serviceEmployeeOutletTransferAuto = ServiceEmployeeOutletTransferAutoD.getInstance(false);
    
 
    public static void main(String[] args) {
        new Progress();
    }

    public Progress() {
        frame = new JFrame();
        panel = new JPanel();
        panel.setPreferredSize(new Dimension(200, 200));
        frame.setSize(400,400);
      //  frame.se
        JP = new JProgressBar(0, 100);
        JP.setStringPainted(true);
        panel.add(JP);
        
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.setContentPane(panel);
        frame.pack();
        frame.setVisible(true);
        headerLabel = new JLabel("", JLabel.CENTER);
        //Task();
         //hapus dulu schedule hari ini yang employenya dipindahkan mendadak
       // Connection connection = null;
        java.sql.Connection connectionsql = null;
       // Statement statement = null;

       try{
        // connection = (Connection) DBHandler.getDBConnection();
         connectionsql = (java.sql.Connection) com.dimata.qdep.db.DBHandler.getDBConnection();
        // statement = (Statement) com.dimata.qdep.db.DBHandler.getStatement(connectionsql);
            System.out.println(" connection "+(connectionsql!=null));
        }catch (Exception e){
           
       }
       
       
       if (connectionsql != null){
       //DownloadAuto();
        try{
        //boolean status = autodownload();
        DownloadAuto();
        } catch (Exception e){
            System.out.print("Tidak Ada Koneksi");
        }
       }
       // this.frame.dispose();
       // new Login().setVisible(true);
    }

    public void Task() {
        headerLabel.setText("Control in action: JLabel"); 
        panel.add(headerLabel);
        for (int i = 0; i <= 80; i++) {
            try {
                JP.setValue(i);
                Thread.sleep(50);
            } catch (Exception e) {
            }
        }
    }
    
//   public  static  boolean autodownload(){
//         boolean status=false;
//        // TODO add your handling code here:
//        //delete();
//        String noLocation = "";//DBHandler.getMesinLocation();
//        try {
//            noLocation = DBHandler.getMesinLocation();
//        } catch (Exception exc) {
//            System.out.printf("Please insert no mesin in outletHarisma.xml");
//            //  msgDownloadData.setText("Please insert no mesin in outletHarisma.xml" + exc);
//        }
//            
////        try{
////              Date dt = EmployeeOutletTransferDataAuto.deleteByDateToday();
////        }catch(Exception e) {
////                System.out.println("delete data mapping outlet hari ini gagal");
////        } 
////         
//            Date dtStartEmpOutlet = new Date(); 
//            dtStartEmpOutlet.setDate(dtStartEmpOutlet.getDate()-3);
//            dtStartEmpOutlet.setMonth(dtStartEmpOutlet.getMonth());
//            dtStartEmpOutlet.setYear(dtStartEmpOutlet.getYear());
//        
//           
//            Date dtEndEmpOutlet = new Date(); 
//            // maka lalukan download sesuai parameter
//            //ServiceEmployeeOutletTransfer serviceEmployeeOutletTransfer = new ServiceEmployeeOutletTransfer();
////            if (noLocation != null && noLocation.length() > 0) {
////                EmployeeOutletTransferDataAuto employeeOutletTransferDataAuto = (EmployeeOutletTransferDataAuto) ServiceEmployeeOutletTransfer.setServiceAuto(dtStartEmpOutlet, dtEndEmpOutlet, noLocation, true, true, true, true,"");
////               
////                EmployeeOutletTransferDataAuto.autodownload(employeeOutletTransferDataAuto);
////                
////            } else {
////                //JOptionPane.showMessageDialog(BodyContainer, "<html> Please insert no mesin in outletHarisma.xml </html>", "Inane warning", JOptionPane.WARNING_MESSAGE);
////            }
//                serviceEmployeeOutletTransferAuto = serviceEmployeeOutletTransferAuto.getInstance(true);
//             if (noLocation != null && noLocation.length() > 0) {
//                if (!serviceEmployeeOutletTransferAuto.getStatus()) {
//                    try {
//
//                        serviceEmployeeOutletTransferAuto.startServiceAuto(dtStartEmpOutlet, dtEndEmpOutlet, noLocation, true, true, true, true,"",JP);
//                       // btnRunDtEmployee.setText("Stop");
//                        
//                    } catch (Exception e) {
//                        System.out.printf("\t Exception svrmgrMachine.startTransfer() = " + e);
//                       // msgDownloadData.setText("\t Exception svrmgrMachine.startTransfer() = " + e);
//                    }
//                } else {
//                    try {
//                        serviceEmployeeOutletTransferAuto.stopService();
//                       // btnRunDtEmployee.setText("Run");
//                    } catch (Exception e) {
//                        System.out.printf("\t Exception svrmgrMachine.stopWatcherMachine() = " + e);
//                       
//                    }
//                }
//            } else {
//              //  JOptionPane.showMessageDialog(BodyContainer, "<html> Please insert no mesin in outletHarisma.xml </html>", "Inane warning", JOptionPane.WARNING_MESSAGE);
//            }
//
//            
//            
//       return status;
//    }
    
     private void DownloadAuto() {                                                 
        // TODO add your handling code here:
        String noLocation = "";//DBHandler.getMesinLocation();
        try {
            noLocation = DBHandler.getMesinLocation();
        } catch (Exception exc) {
            System.out.printf("Please insert no mesin in outletHarisma.xml");
            //  msgDownloadData.setText("Please insert no mesin in outletHarisma.xml" + exc);
        }
                
            Date dtStartEmpOutlet = new Date(); 
            dtStartEmpOutlet.setDate(dtStartEmpOutlet.getDate()-4);
            dtStartEmpOutlet.setMonth(dtStartEmpOutlet.getMonth());
            dtStartEmpOutlet.setYear(dtStartEmpOutlet.getYear());
        
            Date dtEndEmpOutlet = new Date(); 
            // maka lalukan download sesuai parameter
            //serviceEmployeeOutletTransfer = ServiceEmployeeOutletTransfer.getInstance(false);
            serviceEmployeeOutletTransferAuto = serviceEmployeeOutletTransferAuto.getInstance(true);
            if (noLocation != null && noLocation.length() > 0) {
                if (!serviceEmployeeOutletTransferAuto.getStatus()) {
                    try {

                      //  serviceEmployeeOutletTransferAuto.startServiceAuto(dtStartEmpOutlet, dtEndEmpOutlet, noLocation, true, true, true, true,"",JP);
                       // btnRunDtEmployee.setText("Stop");
                        
                    } catch (Exception e) {
                        System.out.printf("\t Exception svrmgrMachine.startTransfer() = " + e);
                       // msgDownloadData.setText("\t Exception svrmgrMachine.startTransfer() = " + e);
                    }
                } else {
                    try {
                        serviceEmployeeOutletTransferAuto.stopService();
                       // btnRunDtEmployee.setText("Run");
                    } catch (Exception e) {
                        System.out.printf("\t Exception svrmgrMachine.stopWatcherMachine() = " + e);
                       
                    }
                }
            } else {
              //  JOptionPane.showMessageDialog(BodyContainer, "<html> Please insert no mesin in outletHarisma.xml </html>", "Inane warning", JOptionPane.WARNING_MESSAGE);
            }
        
    }
}
