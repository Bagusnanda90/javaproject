/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.utility.harisma.machine;

/**
 *
 * @author PRISKA 20150623
 */
import com.dimata.harisma.session.aplikasidesktop.attendance.SessAplicationDestopAbsensiAttendance;
import com.dimata.harisma.utility.harisma.machine.db.DBHandler;
import com.dimata.harisma.utility.harisma.machine.transferdataemployee.ServiceEmployeeOutletTransfer;
import com.dimata.harisma.utility.harisma.masterdatadesktop.entity.PstKonfigurasiOutletSetting;
import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import org.apache.poi.ss.usermodel.BorderStyle;


public class FSplash extends JWindow implements Runnable{
 static JProgressBar JP;
 static JLabel Jlabel;
 int i=0;
public void run(){
JLabel SplashLabel = new JLabel(new ImageIcon(getClass().getResource("image/dimata_system_logo.png")));
SplashLabel.setToolTipText("Tampilan Images Splash Screen");
Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();


JP = new JProgressBar(0, 100);
JP.setStringPainted(true);
Jlabel = new JLabel();
Jlabel.setText("");

        for (int i = 0; i <= 30; i++) {
            try {
                JP.setValue(i);
                Thread.sleep(70);
            } catch (Exception e) {
            }
        }
        
getContentPane().add(SplashLabel,BorderLayout.CENTER);

getContentPane().add(Jlabel,BorderLayout.PAGE_START);
getContentPane().add(JP,BorderLayout.PAGE_END);
//pane.add(SplashLabel,BorderLayout.CENTER);
//ProgressBar pbar= new JProgressBar(0,15000);
//pane.add(pbar);

setSize(728,387);
setLocation((screen.width - 728)/2,((screen.height-387)/2));
//
//setSize(400,200);
//setLocation((screen.width - 400)/2,((screen.height-200)/2));
show();
 for (int i = 30; i <= 100; i++) {
            try {
                JP.setValue(i);
                Thread.sleep(70);
            } catch (Exception e) {
            }
        }
}

public static void main(String[] args) {
    
     
try {
UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
} catch (Exception e) {

}
FSplash FormSplash = new FSplash();
//NewJFrame2 FormJFrame2 = new NewJFrame2();
//--End variable the contains forms
Thread ThFormSplash = new Thread(FormSplash);
//End the form container variable
ThFormSplash.start();

while(!FormSplash.isShowing()){
try{
//Display the FormSplash for 7 seconds
  //  Task();
   
               // Thread.sleep(50);
Thread.sleep(1500);
//Thread.sleep(100);
}catch(InterruptedException e){}
}

//jalankan auto download priska20150628
 java.sql.Connection connectionsql = null;
       // Statement statement = null;

new Login().setVisible(true);
       try{
            connectionsql = (java.sql.Connection) com.dimata.qdep.db.DBHandler.getDBConnection();
            System.out.println(" connection "+(connectionsql!=null));
        }catch (Exception e){
           
       }
       //tampilkan login
       
       if (connectionsql != null){
       //DownloadAuto();
        //tes ping
//        String ip = "192.168.16.108";//args[0];
//        String pingResult = "";
//        int nilai = 1;
//        String pingCmd = "ping " + ip;
//
//        try {
//        Runtime r = Runtime.getRuntime();
//        Process p = r.exec(pingCmd);
//
//        BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
//        String inputLine;
//        while ((inputLine = in.readLine()) != null) {
//        System.out.println(inputLine);
//        pingResult += inputLine;
//        nilai++;
//        }
//        in.close();
//        }//try
//        catch (IOException e) {
//        System.out.println(e);
//        }  
//
      // if (nilai > 5 && nilai < 20){ 
           
        try{
//          String jamnya = "";
//                try {
//                       jamnya = PstKonfigurasiOutletSetting.getAutoStart("", 0, 0);
//                } catch (Exception e){
//                    int menitN = (int) Math.floor(Math.random()*50);
//                    int jamN = (int) Math.floor(Math.random()*2);
//                    if (jamN > 2){
//                        jamN = 1;
//                        jamN = jamN + 7;
//                    } 
//
//                    if (menitN > 59){
//                        menitN = 15;
//                    } 
//                    if (menitN <= 9 ){
//                        jamnya = "0"+jamN+":0"+menitN+":00";
//                    } else {
//                        jamnya = "0"+jamN+":"+menitN+":00";
//                    }
//
//                }  
//                 Time newTime = Time.valueOf(jamnya);
            
         new alarm().setVisible(true);
  
        } catch (Exception e){
            System.out.print("Tidak Ada Koneksi");
        }
        //} else {
        //    FormSplash.dispose();
        //
        //    new NewJFrame2().setVisible(true);
        //}
        
       } else {
           FormSplash.dispose();
           new NewJFrame2().setVisible(true);
       }
       
     
        FormSplash.dispose();
        
       // FormJFrame2.dispose();
}



    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the FormEditor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
