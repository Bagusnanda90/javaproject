/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.utility.harisma.machine;

import com.dimata.harisma.entity.admin.PstAppUser;
import com.dimata.harisma.entity.attendance.PstEmpSchedule;
import com.dimata.harisma.entity.attendance.PstMachineTransaction;
import com.dimata.harisma.entity.attendance.SessEmpScheduleAttendance;
import com.dimata.harisma.entity.employee.PstEmployee;
import com.dimata.harisma.entity.masterdata.ScheduleSymbol;
import com.dimata.harisma.session.admin.SessUserSession;
import com.dimata.harisma.session.aplikasidesktop.attendance.SessAplicationDestopAbsensiAttendance;
import com.dimata.harisma.session.aplikasidesktop.attendance.SessDestopApplication;
import com.dimata.harisma.session.attendance.SessMachineTransactionDestop;
import com.dimata.harisma.utility.harisma.machine.db.DBHandler;
import com.dimata.harisma.utility.harisma.machine.transferdataemployee.PstInformationHrdDesktop;
import com.dimata.harisma.utility.harisma.machine.transferdataemployee.PstScheduleDestopTransfer;
import com.dimata.harisma.utility.harisma.machine.transferdataemployee.ServiceEmployeeOutletTransfer;
import com.dimata.harisma.utility.harisma.machine.transferdataemployee.TabelDataInformationTransfer;
import com.dimata.harisma.utility.harisma.masterdatadesktop.entity.EmployeeDummyTransfer;
import com.dimata.harisma.utility.harisma.masterdatadesktop.entity.PstAppUserDesktop;
import com.dimata.harisma.utility.harisma.masterdatadesktop.entity.PstEmpScheduleDesktop;
import com.dimata.harisma.utility.harisma.masterdatadesktop.entity.PstEmployeeDesktop;
import com.dimata.harisma.utility.harisma.masterdatadesktop.entity.PstEmployeeDummyTransfer;
import com.dimata.harisma.utility.harisma.masterdatadesktop.entity.PstKonfigurasiOutletSetting;
import com.dimata.harisma.utility.harisma.masterdatadesktop.entity.PstMachineTransactionDesktop;
import com.dimata.harisma.utility.harisma.masterdatadesktop.entity.PstPeriodDesktop;
import com.dimata.harisma.utility.harisma.masterdatadesktop.entity.PstScheduleSymbolDesktop;
import com.dimata.util.Formater;
import com.dimata.util.ImagesParser;
import com.dimata.util.JtableDestop;
import com.mysql.jdbc.Connection;
import java.awt.Color;
import java.sql.Statement;
import java.util.Date;
import java.util.Hashtable;
import java.util.Vector;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Dimata 007
 */
public class Login extends javax.swing.JFrame {

    /**
     * Creates new form Login
     */
    
    private String sUrlGambarHarisma;
    private String sUurlGambarClient;
    private String sLogoDimata;
    private String sUrlIconLogin;
    private JScrollPane scrollPane;
    private DefaultTableModel DftTabMode;
    private boolean checkTime;
    private String user;
    SessAplicationDestopAbsensiAttendance sessAplicationDestopAbsensiAttendance = new SessAplicationDestopAbsensiAttendance();
   // ServiceEmployeeOutletTransfer serviceEmployeeOutletTransfer = ServiceEmployeeOutletTransfer.getInstance(false);
    
   
    
    public Login() {
        
        Formater formater = new Formater();
        ImagesParser imagesParser = new ImagesParser();
        JtableDestop jtableDestop = new JtableDestop();
        initComponents();
        
        BodyContainer.setBackground(SessAplicationDestopAbsensiAttendance.getColor() != null ? SessAplicationDestopAbsensiAttendance.getColor() : Color.WHITE);
        body.setBackground(SessAplicationDestopAbsensiAttendance.getColor() != null ? SessAplicationDestopAbsensiAttendance.getColor() : Color.WHITE);
        //this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        //this.setUndecorated(true);
        //this.setAlwaysOnTop(true);
        this.setResizable(false);
        //this.setVisible(true);
        ///IconHarisma.setIcon(new javax.swing.ImageIcon("D:\\share\\Share Ramayu\\SATRYA\\MEMBUAT PROGRAM\\HARISMA\\KTI\\harisma_construck_20131104\\jsp\\images\\harismaFlatWhite.jpg")); // NOI18N
        //LabelDate.setText("" + Formater.formatDate(new Date(), "EE, dd MMMM yyyy"));
        //LabelTime.setText("Time : " + Formater.formatDate(new Date(), "HH:mm:ss"));

        labelDate.setText("");
        labelTime.setText("");
        //labelremidertext.setEnabled(false);
        //labelremidertext.setText("<html><p>MASA AKTIF ANDA HANYA 7 HARI SILAHKAN REGISTARSI TERLEBIH DAHULU, KE BAGIAN HRD KARENA ANDA AKAN DI DEPORTASI</p></html>");
        //labelremidertext.setFont(new java.awt.Font("Arial Black", 1, 13));
        //labelInformation.setEnabled(false);
        sessAplicationDestopAbsensiAttendance = (SessAplicationDestopAbsensiAttendance) PstKonfigurasiOutletSetting.getListSetting(0, 1, "", "");
        //Pst
        //labelInfo.setText("<html> MASA AKTIF ANDA HANYA 7 HARI SILAHKAN INFORMASIKAN TERLEBIH DAHULU, KE BAGIAN HRD </html>");
        //labelInfo.setText("<html><p>MASA AKTIF ANDA HANYA 7 HARI SILAHKAN REGISTARSI TERLEBIH DAHULU, KE BAGIAN HRD KARENA ANDA AKAN DI DEPORTASI</p></html>");
        labelInfo.setFont(new java.awt.Font("Arial Black", 1, 13));
          String reminder ="";
        try {
          
            Date dtStart = new Date();
            dtStart.setHours(0);
            dtStart.setMinutes(0);
            dtStart.setSeconds(0);
            Date dtEnd = new Date();
            dtEnd.setHours(23);
            dtEnd.setMinutes(59);
            dtEnd.setSeconds(59);
            String whereClause = "\"" + Formater.formatDate(dtEnd, "yyyy-MM-dd HH:mm") + "\" >= " + PstInformationHrdDesktop.fieldNames[PstInformationHrdDesktop.FLD_DATE_START] + " AND  " + PstInformationHrdDesktop.fieldNames[PstInformationHrdDesktop.FLD_DATE_END] + ">=\"" + Formater.formatDate(dtStart, "yyyy-MM-dd HH:mm") + "\"";
            Vector listInformation = PstInformationHrdDesktop.list(0, 0, whereClause, PstInformationHrdDesktop.fieldNames[PstInformationHrdDesktop.FLD_DATE_START]);
            if (listInformation != null && listInformation.size() > 0) {
                reminder = "<ul>";
                for (int idx = 0; idx < listInformation.size(); idx++) {
                    TabelDataInformationTransfer tabelDataInformationTransfer = (TabelDataInformationTransfer) listInformation.get(idx);
                    reminder = reminder + "<li>" + tabelDataInformationTransfer.getNamaInformation() + " Date:" + Formater.formatDate(tabelDataInformationTransfer.getDateStart(), "yyyy-MM-dd HH:mm") + " Until " + Formater.formatDate(tabelDataInformationTransfer.getDateEnd(), "yyyy-MM-dd HH:mm") + "</li>";
                }
                reminder = reminder + "</ul>";
            }
        } catch (Exception exc) {
        }
        labelInfo.setText("<html><p>" + reminder + "</p></html>");
        sUrlGambarHarisma = sessAplicationDestopAbsensiAttendance == null || sessAplicationDestopAbsensiAttendance.getUrlImages().length() == 0 ? "D:\\Dimata\\Aplikasi\\Absensi\\Images\\harismaFlatWhite.jpg" : sessAplicationDestopAbsensiAttendance.getUrlImages() + "\\harismaFlatWhite.jpg";
        sUurlGambarClient = sessAplicationDestopAbsensiAttendance == null || sessAplicationDestopAbsensiAttendance.getUrlImages().length() == 0 ? "D:\\Dimata\\Aplikasi\\Absensi\\Images\\MM.jpg" : sessAplicationDestopAbsensiAttendance.getUrlImages() + "\\MM.jpg";
        //this.tampilkanImage();
        sLogoDimata = sessAplicationDestopAbsensiAttendance == null || sessAplicationDestopAbsensiAttendance.getUrlImages().length() == 0 ? "D:\\Dimata\\Aplikasi\\Absensi\\Images\\Logo Dimata.png" : sessAplicationDestopAbsensiAttendance.getUrlImages() + "\\Logo Dimata.png";
        sUrlIconLogin = sessAplicationDestopAbsensiAttendance == null || sessAplicationDestopAbsensiAttendance.getUrlImages().length() == 0 ? "D:\\Dimata\\Aplikasi\\Absensi\\Images\\LogIn.png" : sessAplicationDestopAbsensiAttendance.getUrlImages() + "\\LogIn.png";

        //untuk menampilkan jam dan tanggal
        formater.getTimeInJavaDesktop(labelTime, labelDate);
        //formater.getTime();

        //untuk merubah size gambar sesuai yg di tentukan
        imagesParser.tampilkanImage(iconHarisma, iconClient, this.getsUrlGambarHarisma(), this.getsUurlGambarClient());
        imagesParser.tampilkanImageJButton(btnActionLogin, this.getsUrlIconLogin());
        //imagesParser.tampilkanImage(iconHarisma, iconClient, urlGambarHarisma, urlGambarClient);
        imagesParser.tampilkanImage(labelLogoDimata, this.getsLogoDimata());
        //labelErrorLogin.setVisible(false);
        body.setSize(500, 500);

        //Dimension windowSize = getSize();
        setLocationRelativeTo(this);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        int tmbhBolehAbs = sessAplicationDestopAbsensiAttendance == null?0:sessAplicationDestopAbsensiAttendance.getTambahanBolehAbs();
        String getIdScheduleSymbol = PstScheduleSymbolDesktop.listScheduleIdSymbol(tmbhBolehAbs);
        long periodId = PstPeriodDesktop.getPeriodIdBySelectedDate(new Date());
        String employeeIdNotAktiv = PstEmployeeDesktop.oidEmpNotAktiv(0, 0, "", "");
        Vector listInOut = PstEmpScheduleDesktop.getINandOUT(periodId, getIdScheduleSymbol, getIdScheduleSymbol,employeeIdNotAktiv);
        
        //update by satrya 2014-11-09
        Date dtPrev = new Date(new Date().getYear(), new Date().getMonth(), new Date().getDate()-1);
        long periodIdCross = PstPeriodDesktop.getPeriodIdBySelectedDate(dtPrev);
        Hashtable hashCheckScheduleCrossDay=null;
            if(periodIdCross!=0){
                String wherex = " empsch." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_PERIOD_ID] +"="+periodIdCross;
                hashCheckScheduleCrossDay = PstEmpScheduleDesktop.hashCheckScheduleCrossDay(tmbhBolehAbs,dtPrev, wherex, PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] );
        }
        String employeeIdCrossDay = PstEmployeeDesktop.oidEmpCrossDay(hashCheckScheduleCrossDay,0, 0, "", "");
        Vector listEmpCrossDay = PstEmpScheduleDesktop.getINandOUTCrossDay(dtPrev,periodIdCross, getIdScheduleSymbol, getIdScheduleSymbol,employeeIdCrossDay);
        String whereCheck = PstMachineTransaction.fieldNames[PstMachineTransaction.FLD_DATE_TRANS] + " BETWEEN \"" + Formater.formatDate(dtPrev, "yyyy-MM-dd 00:00:00") + "\" AND \"" + Formater.formatDate(new Date(), "yyyy-MM-dd HH:mm:59") + "\"";
        Hashtable hashMachineTransCheckCross = PstMachineTransactionDesktop.hashListMachineTrans(0, 0, whereCheck, "");
        String whereTransfer = PstEmployeeDummyTransfer.fieldNames[PstEmployeeDummyTransfer.FLD_DATE_CREATE_TRANSFER]+ " BETWEEN \"" + Formater.formatDate(new Date(), "yyyy-MM-dd 00:00:00") + "\" AND \"" + Formater.formatDate(new Date(), "yyyy-MM-dd 23:59:59") + "\"";
        Vector listEmployeeTrans = PstEmployeeDummyTransfer.listEmployeeDesktopWithTimeINOUT(0, 0, whereTransfer, null);
        
        
        
        String where = PstMachineTransaction.fieldNames[PstMachineTransaction.FLD_DATE_TRANS] + " BETWEEN \"" + Formater.formatDate(new Date(), "yyyy-MM-dd 00:00:00") + "\" AND \"" + Formater.formatDate(new Date(), "yyyy-MM-dd HH:mm:59") + "\"";
        Hashtable hashMachineTrans = PstMachineTransactionDesktop.hashListMachineTrans(0, 0, where, "");
        //String listInOutygNull = "";
        //String blink="<html><blink>Why would somebody use this?</blink></html>";
        Object[] Baris = {"hidden", "No", "Nama Employee", "Schedule", "Information"};
        DftTabMode = new DefaultTableModel(null, Baris);
        jTableRemider.setModel(DftTabMode);
        if (  (listInOut != null && listInOut.size() > 0) || (listEmpCrossDay!=null&& listEmpCrossDay.size()>0) || (listEmployeeTrans!=null && listEmployeeTrans.size()>0) ) {
            //untuk membuat header table
//             Object[] Baris={"No","Nama Employee","Schedule","Information"};
//            DftTabMode=new DefaultTableModel(null,Baris);
//            jTableRemider.setModel(DftTabMode);
            jTableRemider.setEnabled(false);//agar tidak bisa di edit
            int no = 0;
            try {
                if(listInOut != null && listInOut.size() > 0){
                        for (int x = 0; x < listInOut.size(); x++) {
                        Date dtIN = null;
                        Date dtOut = null;
                        SessEmpScheduleAttendance sessEmpSchedule = (SessEmpScheduleAttendance) listInOut.get(x);
                        if (hashMachineTrans != null && hashMachineTrans.size() > 0 && hashMachineTrans.containsKey(sessEmpSchedule.getBarcodeNumber())) {
                            SessMachineTransactionDestop sessMachineTransactionDestop = (SessMachineTransactionDestop) hashMachineTrans.get(sessEmpSchedule.getBarcodeNumber());

                            int button = -1;
                            if (sessMachineTransactionDestop != null && sessMachineTransactionDestop.getSizeDtTransaction() > 0 && sessMachineTransactionDestop.getSizeDtTransaction() == sessMachineTransactionDestop.getSizeDtTransaction()) {
                                for (int idxMd = 0; idxMd < sessMachineTransactionDestop.getSizeMode(); idxMd++) {
                                    String sButton = sessMachineTransactionDestop.getMode(idxMd);
                                    button = sButton != null && sButton.length() > 0 ? Integer.parseInt(sButton) : -1;
                                    if (button == Absensi.BUTTON_IN) {
                                        if (dtIN == null) {
                                            dtIN = sessMachineTransactionDestop.getDtTransaction(idxMd);
                                        } else if (sessMachineTransactionDestop.getDtTransaction(idxMd) == null && dtIN != null) {
                                            //tidak di set lagi
                                        } else {
                                            dtIN = sessMachineTransactionDestop.getDtTransaction(idxMd);
                                        }
                                    } else if (button == Absensi.BUTTON_OUT) {
                                        if (dtOut == null) {
                                            dtOut = sessMachineTransactionDestop.getDtTransaction(idxMd);
                                        } else if (sessMachineTransactionDestop.getDtTransaction(idxMd) == null && dtOut != null) {
                                            //tidak di set lagi
                                        } else {
                                            dtOut = sessMachineTransactionDestop.getDtTransaction(idxMd);
                                        }

                                    }
                                }
                            }
                        }
                        if (dtIN == null || dtOut == null) {
                            //Date dtIN = sessEmpSchedule.getTimeIn();
                            //Date dtOut = sessEmpSchedule.getTimeOut();
                            Date dtIN2nd = sessEmpSchedule.getSchTimeIn2nd();
                            Date dtOut2nd = sessEmpSchedule.getSchTimeOut2nd();
                            String fullName = sessEmpSchedule.getFullName();
                            String schTimeIn = sessEmpSchedule.getSchTimeIn() == null ? "" : Formater.formatDate(sessEmpSchedule.getSchTimeIn(), "HH:mm");
                            String schTimeOut = sessEmpSchedule.getSchTimeOut() == null ? "" : Formater.formatDate(sessEmpSchedule.getSchTimeOut(), "HH:mm");
                            String symbol = sessEmpSchedule.getSymbol();
                            no = no + 1;
                            String[] dataField = {"0", "" + no, fullName, "<html><B>"  +symbol + " [" + sessEmpSchedule.getSschTimeIn() + "," + sessEmpSchedule.getSschTimeOut() + "]"+ (sessEmpSchedule.getTanggalSchedulenya()==null?"":sessEmpSchedule.getTanggalSchedulenya())+"</B></html>", "<html><blink><B>" + (dtIN == null ? " Not IN " : "") + (dtOut == null ? (dtIN == null ? " ,Not OUT " : " Not OUT ") : "") + "</B></blink></html>"};
                            DftTabMode.addRow(dataField);
                            //listInOutygNull = listInOutygNull + fullName + " " + symbol + " [" + sessEmpSchedule.getSschTimeIn() + "," + sessEmpSchedule.getSschTimeOut() + "]" + (dtIN == null ? " Not IN " : "") + (dtOut == null ? " Not OUT " : "") + ",<br>";
                        }
                    }
                
                }
                
                if(listEmpCrossDay!=null&& listEmpCrossDay.size()>0){
                    for(int c=0;c<listEmpCrossDay.size();c++){
                            Date dtIN = null;
                        Date dtOut = null;
                        SessEmpScheduleAttendance sessEmpSchedule = (SessEmpScheduleAttendance) listEmpCrossDay.get(c);
                        if (hashMachineTransCheckCross != null && hashMachineTransCheckCross.size() > 0 && hashMachineTransCheckCross.containsKey(sessEmpSchedule.getBarcodeNumber())) {
                            SessMachineTransactionDestop sessMachineTransactionDestop = (SessMachineTransactionDestop) hashMachineTransCheckCross.get(sessEmpSchedule.getBarcodeNumber());

                            int button = -1;
                            if (sessMachineTransactionDestop != null && sessMachineTransactionDestop.getSizeDtTransaction() > 0 && sessMachineTransactionDestop.getSizeDtTransaction() == sessMachineTransactionDestop.getSizeDtTransaction()) {
                                for (int idxMd = 0; idxMd < sessMachineTransactionDestop.getSizeMode(); idxMd++) {
                                    String sButton = sessMachineTransactionDestop.getMode(idxMd);
                                    button = sButton != null && sButton.length() > 0 ? Integer.parseInt(sButton) : -1;
                                    if (button == Absensi.BUTTON_IN) {
                                        if (dtIN == null) {
                                            dtIN = sessMachineTransactionDestop.getDtTransaction(idxMd);
                                        } else if (sessMachineTransactionDestop.getDtTransaction(idxMd) == null && dtIN != null) {
                                            //tidak di set lagi
                                        } else {
                                            dtIN = sessMachineTransactionDestop.getDtTransaction(idxMd);
                                        }
                                    } else if (button == Absensi.BUTTON_OUT) {
                                        if (dtOut == null) {
                                            dtOut = sessMachineTransactionDestop.getDtTransaction(idxMd);
                                        } else if (sessMachineTransactionDestop.getDtTransaction(idxMd) == null && dtOut != null) {
                                            //tidak di set lagi
                                        } else {
                                            dtOut = sessMachineTransactionDestop.getDtTransaction(idxMd);
                                        }

                                    }
                                }
                            }
                        }
                        if (dtIN == null || dtOut == null) {
                            //Date dtIN = sessEmpSchedule.getTimeIn();
                            //Date dtOut = sessEmpSchedule.getTimeOut();
                            String fullName = sessEmpSchedule.getFullName();
                            String symbol = sessEmpSchedule.getSymbol();
                            no = no + 1;
                            String[] dataField = {"0", "" + no, fullName, "<html><B>"+ symbol + " [" + sessEmpSchedule.getSschTimeIn() + "," + sessEmpSchedule.getSschTimeOut() + "]"+(sessEmpSchedule.getTanggalSchedulenya()==null?"":sessEmpSchedule.getTanggalSchedulenya())+"</B></html>", "<html><blink><B>" + (dtIN == null ? " Not IN " : "") + (dtOut == null ? (dtIN == null ? " ,Not OUT " : " Not OUT ") : "") + "</B></blink></html>"};
                            DftTabMode.addRow(dataField);
                            //listInOutygNull = listInOutygNull + fullName + " " + symbol + " [" + sessEmpSchedule.getSschTimeIn() + "," + sessEmpSchedule.getSschTimeOut() + "]" + (dtIN == null ? " Not IN " : "") + (dtOut == null ? " Not OUT " : "") + ",<br>";
                        }
                    }
                }
                
                if((listEmployeeTrans!=null && listEmployeeTrans.size()>0)){
                    for(int t=0;t<listEmployeeTrans.size();t++){
                        SessDestopApplication sessDestopApplication = (SessDestopApplication)listEmployeeTrans.get(t);
                        if(sessDestopApplication.getActTimeIn()==null || sessDestopApplication.getActTimeOut()==null){
                            
                            String fullName = sessDestopApplication.getNamaEmployee();
                            String schTimeIn = sessDestopApplication.getSchTimeIn() == null ? "" : sessDestopApplication.getSchTimeIn();
                            String schTimeOut = sessDestopApplication.getSchTimeOut() == null ? "" : sessDestopApplication.getSchTimeOut();
                            String symbol = sessDestopApplication.getSymbol();
                            no = no + 1;
                            String[] dataField = {"0", "" + no, fullName, "<html><B>" +symbol + " [" + schTimeIn + "," + schTimeOut + "]"+ (sessDestopApplication.getTanggalSchedulenya()==null?"":sessDestopApplication.getTanggalSchedulenya())+"</B></html>", "<html><blink><B>" + (sessDestopApplication.getActTimeIn() == null ? " Not IN " : "") + (sessDestopApplication.getActTimeOut() == null ? (sessDestopApplication.getActTimeIn() == null ? " ,Not OUT " : " Not OUT ") : "") + "</B></blink></html>"};
                            DftTabMode.addRow(dataField);
                        }
                    }
                }
            } catch (Exception exc) {
            }
            //listInOutygNull = listInOutygNull.substring(0, listInOutygNull.length() - 1);
            //labelremidertext.setText("<html><p>" + listInOutygNull + "</p></html>");
            jtableDestop.resizeColumnWidth(jTableRemider); 
        } else {

            JOptionPane.showMessageDialog(BodyContainer, "<html> Can't Find Schedule in This Period, Please Download Period In HR System</html>", "warning message", JOptionPane.WARNING_MESSAGE);
            //JOptionPane.showMessageDialog(null, "<html> Can't Fine Schedule in This Period, Please Download Period In HR System</html>");
            //JOptionPane.showInternalMessageDialog(BodyContainer, "information","information", JOptionPane.INFORMATION_MESSAGE);
        }
       
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
        body = new javax.swing.JPanel();
        label_user_akses = new javax.swing.JLabel();
        labelDate = new javax.swing.JLabel();
        labelTime = new javax.swing.JLabel();
        panelUserAkses = new javax.swing.JPanel();
        cbPilihanTypeUser = new javax.swing.JComboBox();
        labelPassword = new javax.swing.JLabel();
        txtUserName = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtPassword = new javax.swing.JPasswordField();
        btnActionLogin = new javax.swing.JButton();
        panelRemider = new javax.swing.JPanel();
        labelRemider = new javax.swing.JLabel();
        jScrollPaneTableRemider = new javax.swing.JScrollPane();
        jTableRemider = new javax.swing.JTable();
        panelInformation = new javax.swing.JPanel();
        labelInformation = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        labelInfo = new javax.swing.JLabel();
        panelHeader = new javax.swing.JPanel();
        iconHarisma = new javax.swing.JLabel();
        judulDepan = new javax.swing.JLabel();
        iconClient = new javax.swing.JLabel();
        PanelFooter = new javax.swing.JPanel();
        labelLogoDimata = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));

        BodyContainer.setBackground(new java.awt.Color(255, 255, 255));
        BodyContainer.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        body.setBackground(new java.awt.Color(255, 255, 255));
        body.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        body.setName("body"); // NOI18N
        body.setPreferredSize(new java.awt.Dimension(500, 410));

        label_user_akses.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        label_user_akses.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label_user_akses.setText("User Akses");
        label_user_akses.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        labelDate.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        labelDate.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelDate.setText("date");
        labelDate.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        labelTime.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        labelTime.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelTime.setText("time");
        labelTime.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        panelUserAkses.setBackground(new java.awt.Color(255, 255, 255));
        panelUserAkses.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        cbPilihanTypeUser.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "NIK", "User ID" }));
        cbPilihanTypeUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbPilihanTypeUserActionPerformed(evt);
            }
        });

        labelPassword.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        labelPassword.setText("Password");
        labelPassword.setToolTipText("");
        labelPassword.setName("labelPassword"); // NOI18N

        txtUserName.setName("txtUserName"); // NOI18N
        txtUserName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUserNameActionPerformed(evt);
            }
        });

        jLabel7.setText(":");

        jLabel8.setText(":");

        txtPassword.setName("txtPassword"); // NOI18N
        txtPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPasswordActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelUserAksesLayout = new javax.swing.GroupLayout(panelUserAkses);
        panelUserAkses.setLayout(panelUserAksesLayout);
        panelUserAksesLayout.setHorizontalGroup(
            panelUserAksesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelUserAksesLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(panelUserAksesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbPilihanTypeUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelPassword))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 41, Short.MAX_VALUE)
                .addGroup(panelUserAksesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelUserAksesLayout.createSequentialGroup()
                        .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelUserAksesLayout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtUserName, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        panelUserAksesLayout.setVerticalGroup(
            panelUserAksesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelUserAksesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelUserAksesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtUserName, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbPilihanTypeUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelUserAksesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelPassword)
                    .addComponent(jLabel8)
                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnActionLogin.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        btnActionLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActionLoginActionPerformed(evt);
            }
        });

        panelRemider.setBackground(new java.awt.Color(255, 255, 255));
        panelRemider.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        labelRemider.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        labelRemider.setText("REMINDER :");

        jScrollPaneTableRemider.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        jScrollPaneTableRemider.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        jTableRemider.setFont(new java.awt.Font("Cambria", 0, 16)); // NOI18N
        jTableRemider.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPaneTableRemider.setViewportView(jTableRemider);

        javax.swing.GroupLayout panelRemiderLayout = new javax.swing.GroupLayout(panelRemider);
        panelRemider.setLayout(panelRemiderLayout);
        panelRemiderLayout.setHorizontalGroup(
            panelRemiderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRemiderLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelRemiderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPaneTableRemider, javax.swing.GroupLayout.DEFAULT_SIZE, 535, Short.MAX_VALUE)
                    .addGroup(panelRemiderLayout.createSequentialGroup()
                        .addComponent(labelRemider)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panelRemiderLayout.setVerticalGroup(
            panelRemiderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRemiderLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(labelRemider)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPaneTableRemider, javax.swing.GroupLayout.DEFAULT_SIZE, 158, Short.MAX_VALUE)
                .addContainerGap())
        );

        panelInformation.setBackground(new java.awt.Color(255, 255, 255));
        panelInformation.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        labelInformation.setBackground(new java.awt.Color(255, 255, 255));
        labelInformation.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        labelInformation.setText("INFORMATION :");

        labelInfo.setText("text");
        labelInfo.setToolTipText("");
        labelInfo.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        labelInfo.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        jScrollPane1.setViewportView(labelInfo);

        javax.swing.GroupLayout panelInformationLayout = new javax.swing.GroupLayout(panelInformation);
        panelInformation.setLayout(panelInformationLayout);
        panelInformationLayout.setHorizontalGroup(
            panelInformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInformationLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelInformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelInformationLayout.createSequentialGroup()
                        .addComponent(labelInformation)
                        .addGap(0, 426, Short.MAX_VALUE))
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        panelInformationLayout.setVerticalGroup(
            panelInformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInformationLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelInformation)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout bodyLayout = new javax.swing.GroupLayout(body);
        body.setLayout(bodyLayout);
        bodyLayout.setHorizontalGroup(
            bodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bodyLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(bodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(bodyLayout.createSequentialGroup()
                        .addGroup(bodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(panelUserAkses, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(label_user_akses, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(bodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(bodyLayout.createSequentialGroup()
                                .addComponent(btnActionLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, bodyLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(bodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(labelDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(labelTime, javax.swing.GroupLayout.DEFAULT_SIZE, 184, Short.MAX_VALUE)))))
                    .addComponent(panelRemider, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelInformation, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        bodyLayout.setVerticalGroup(
            bodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bodyLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(bodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(label_user_akses, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                    .addComponent(labelDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(bodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(bodyLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnActionLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(bodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(bodyLayout.createSequentialGroup()
                            .addGap(11, 11, 11)
                            .addComponent(labelTime, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(bodyLayout.createSequentialGroup()
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(panelUserAkses, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelRemider, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelInformation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        panelHeader.setBackground(new java.awt.Color(255, 255, 255));

        iconHarisma.setBackground(new java.awt.Color(255, 255, 255));
        iconHarisma.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        iconHarisma.setText("icon_harisma");
        iconHarisma.setToolTipText("");
        iconHarisma.setName("iconHarisma"); // NOI18N

        judulDepan.setBackground(new java.awt.Color(255, 255, 255));
        judulDepan.setFont(new java.awt.Font("Vani", 1, 18)); // NOI18N
        judulDepan.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        judulDepan.setText("Outlet Attendance System");
        judulDepan.setToolTipText("Outlet Attendance System");
        judulDepan.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        judulDepan.setName("judul"); // NOI18N

        iconClient.setBackground(new java.awt.Color(255, 255, 255));
        iconClient.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        iconClient.setText("icon_client");
        iconClient.setToolTipText("");
        iconClient.setName("iconClient"); // NOI18N

        javax.swing.GroupLayout panelHeaderLayout = new javax.swing.GroupLayout(panelHeader);
        panelHeader.setLayout(panelHeaderLayout);
        panelHeaderLayout.setHorizontalGroup(
            panelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelHeaderLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(iconClient, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(judulDepan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(iconHarisma, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        panelHeaderLayout.setVerticalGroup(
            panelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelHeaderLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(iconHarisma, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(iconClient, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(judulDepan, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        PanelFooter.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout PanelFooterLayout = new javax.swing.GroupLayout(PanelFooter);
        PanelFooter.setLayout(PanelFooterLayout);
        PanelFooterLayout.setHorizontalGroup(
            PanelFooterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelFooterLayout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelLogoDimata, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        PanelFooterLayout.setVerticalGroup(
            PanelFooterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(labelLogoDimata, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(PanelFooterLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout BodyContainerLayout = new javax.swing.GroupLayout(BodyContainer);
        BodyContainer.setLayout(BodyContainerLayout);
        BodyContainerLayout.setHorizontalGroup(
            BodyContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelHeader, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(BodyContainerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(BodyContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(body, javax.swing.GroupLayout.DEFAULT_SIZE, 579, Short.MAX_VALUE)
                    .addComponent(PanelFooter, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        BodyContainerLayout.setVerticalGroup(
            BodyContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, BodyContainerLayout.createSequentialGroup()
                .addComponent(panelHeader, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(body, javax.swing.GroupLayout.DEFAULT_SIZE, 480, Short.MAX_VALUE)
                .addGap(10, 10, 10)
                .addComponent(PanelFooter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(BodyContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(BodyContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(1, 1, 1))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cbPilihanTypeUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbPilihanTypeUserActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbPilihanTypeUserActionPerformed

    private void btnActionLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActionLoginActionPerformed
        // TODO add your handling code here:
      
        //DownloadAuto();
        
       // boolean status = autodownload();
        String loginID = txtUserName.getText();
        String passwd = txtPassword.getText();
        int dologin = SessUserSession.DO_LOGIN_OK;
        //SessUserSession userSess = new SessUserSession();
        PstAppUserDesktop pstAppUserDesktop = new PstAppUserDesktop();
        Formater formater = new Formater();
        int pilihan = cbPilihanTypeUser.getSelectedIndex();
        //Employee objemployee = new Employee();
        int tmbhBolehAbs = sessAplicationDestopAbsensiAttendance == null?0:sessAplicationDestopAbsensiAttendance.getTambahanBolehAbs();
        if (pilihan == SessUserSession.DO_LOGIN_WITH_PIN) {
            //maka menggunakan PIN
            String where = "emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] + " = '" + loginID + "' AND "
                    + "emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMP_PIN] + " = '" + passwd + "'";
            Vector listEmployee = new Vector();
            //update by satrya 2014-09-11
            Date dtPrev = new Date(new Date().getYear(), new Date().getMonth(), new Date().getDate()-1);
            long periodPrev =PstPeriodDesktop.getPeriodIdBySelectedDate(dtPrev);
            Hashtable hashCheckScheduleCrossDay=null;
            if(periodPrev!=0){
                String wherex = where + " AND empsch." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_PERIOD_ID] +"="+periodPrev;
                hashCheckScheduleCrossDay = PstEmpScheduleDesktop.hashCheckScheduleCrossDay(tmbhBolehAbs,dtPrev, wherex, PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] );
            }
            
           boolean isCrossDay=false;
            listEmployee = PstEmployeeDesktop.listEmployeeDesktop(hashCheckScheduleCrossDay,0, 1, where, null);
            Vector listEmployeeTrans = PstEmployeeDummyTransfer.listEmployeeDesktop(0, 1, where, null);
            if (listEmployee != null && listEmployee.size() > 0) {
                SessDestopApplication sessDestopApplication = (SessDestopApplication) listEmployee.get(0);
                dologin = pstAppUserDesktop.doLogin("Employee", "dsj2009");

                //untuk mendapatkan schedulenya
                //update by satrya 2014-11-09
                if(hashCheckScheduleCrossDay!=null && hashCheckScheduleCrossDay.containsKey(""+sessDestopApplication.getEmployeeId())){
                    isCrossDay=true;
                }
                ScheduleSymbol scheduleSymbol = PstEmpScheduleDesktop.getDailyFirstSchedule(isCrossDay?dtPrev:new Date(), sessDestopApplication.getEmployeeId());
                sessDestopApplication.setSchBreakIn(scheduleSymbol.getBreakIn());
                sessDestopApplication.setSchBreakOut(scheduleSymbol.getBreakOut());
                sessDestopApplication.setSchTimeIn(scheduleSymbol.getTimeIn());
                sessDestopApplication.setSchTimeOut(scheduleSymbol.getTimeOut());
                sessDestopApplication.setSymbol(scheduleSymbol.getSymbol());
                sessDestopApplication.setKetSymbol(scheduleSymbol.getSchedule());
                //sessAplicationDestopAbsensiAttendance.setLabelDate(labelDate);
                //sessAplicationDestopAbsensiAttendance.setLabelTime(labelTime);
                String noStation = "";
                try {
                    noStation = DBHandler.getMesinLocation();
                } catch (Exception exc) {
                    System.out.println("Error Station" + exc);
                }


                sessAplicationDestopAbsensiAttendance.setLogoDimata(this.getsLogoDimata());
                sessAplicationDestopAbsensiAttendance.setUrlGambarClient(this.getsUurlGambarClient());
                sessAplicationDestopAbsensiAttendance.setUrlGambarHarisma(this.getsUrlGambarHarisma());
                sessAplicationDestopAbsensiAttendance.setObSessDestopApplication(sessDestopApplication);
                sessAplicationDestopAbsensiAttendance.setNoStation(noStation);
                sessAplicationDestopAbsensiAttendance.setColor(Color.WHITE);
                this.dispose();
                new Absensi().setVisible(true);
                //JOptionPane jOptionPane = new JOptionPane();
                //this.setCheckTime(false);
                //formater.getTimeLoginError(jOptionPane, this);
                //new Absensi().setVisible(true);
            } else if (listEmployeeTrans != null && listEmployeeTrans.size() > 0) {
                //jika tidak ada ambil data emp yg transfer
                //nanti ada akan mencari schedulenya
                 SessDestopApplication sessDestopApplication = (SessDestopApplication) listEmployeeTrans.get(0);
                dologin = pstAppUserDesktop.doLogin("Employee", "dsj2009");
                String noStation = "";
                try {
                    noStation = DBHandler.getMesinLocation();
                } catch (Exception exc) {
                    System.out.println("Error Station" + exc);
                }


                sessAplicationDestopAbsensiAttendance.setLogoDimata(this.getsLogoDimata());
                sessAplicationDestopAbsensiAttendance.setUrlGambarClient(this.getsUurlGambarClient());
                sessAplicationDestopAbsensiAttendance.setUrlGambarHarisma(this.getsUrlGambarHarisma());
                sessAplicationDestopAbsensiAttendance.setObSessDestopApplication(sessDestopApplication);
                sessAplicationDestopAbsensiAttendance.setNoStation(noStation);
                sessAplicationDestopAbsensiAttendance.setColor(Color.WHITE);
                this.dispose();
                new Absensi().setVisible(true);
            } else {
                panelRemider.setVisible(false);
                panelInformation.setVisible(false);
                //labelErrorLogin.setVisible(true);
                body.setSize(581, 573);
                this.setUser(loginID);
                //isikan wktu
                this.setCheckTime(true);
                JOptionPane.showMessageDialog(null, "<html>NIK or Password are invalid <br>  <b>User \"" + user + "\"</b> Cannot Login</html>");
                //formater.getTimeLoginError(jOptionPane, this);
                this.dispose();
                new Login().setVisible(true);
            }
        } else {
            dologin = pstAppUserDesktop.doLogin(loginID, passwd);
            if (dologin != SessUserSession.DO_LOGIN_OK) { 
                panelRemider.setVisible(false);
                panelInformation.setVisible(false);
                //labelErrorLogin.setVisible(true);
                body.setSize(581, 573);
                //isikan wktu
                //JOptionPane jOptionPane = new JOptionPane();
                this.setCheckTime(true);
                JOptionPane.showMessageDialog(null, "<html>NIK or Password are invalid <br>  <b>User \"" + user + "\"</b> Cannot Login</html>");
                //formater.getTimeLoginError(jOptionPane, this);
                this.dispose();
                new Login().setVisible(true);

            } else {
                String where = "appuser." + PstAppUser.fieldNames[PstAppUser.FLD_LOGIN_ID] + " = '" + loginID + "' AND "
                        + "appuser." + PstAppUser.fieldNames[PstAppUser.FLD_PASSWORD] + " = '" + passwd + "'";
                //update by satrya 2014-09-11
//            Date dtPrev = new Date(new Date().getYear(), new Date().getMonth(), new Date().getDate()-1);
//            long periodPrev =PstPeriodDesktop.getPeriodIdBySelectedDate(dtPrev);
            Hashtable hashCheckScheduleCrossDay=null;
            //tidak perlu karena dia superuser ataupun kadif ini login ke aplikasi lengkap
//            if(periodPrev!=0){
//                String wherex = "empsch." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_PERIOD_ID] +"="+periodPrev;
//                hashCheckScheduleCrossDay = PstEmpScheduleDesktop.hashCheckScheduleCrossDay(dtPrev, wherex, PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] );
//            }
//             boolean iscrossDay=false;
//             
                Vector listEmployee = PstEmployeeDesktop.listEmployeeDesktop(hashCheckScheduleCrossDay,0, 1, where, null);
                if (listEmployee != null && listEmployee.size() > 0) {
                    SessDestopApplication sessDestopApplication = (SessDestopApplication) listEmployee.get(0);
                    //untuk mendapatkan schedulenya
                   
                     //update by satrya 2014-11-09
//                    if(hashCheckScheduleCrossDay!=null && hashCheckScheduleCrossDay.containsKey(""+sessDestopApplication.getEmployeeId())){
//                        iscrossDay=true;
//                    }
                    ScheduleSymbol scheduleSymbol = PstEmpScheduleDesktop.getDailyFirstSchedule(new Date(), sessDestopApplication.getEmployeeId());
                    sessDestopApplication.setSchBreakIn(scheduleSymbol.getBreakIn());
                    sessDestopApplication.setSchBreakOut(scheduleSymbol.getBreakOut());
                    sessDestopApplication.setSchTimeIn(scheduleSymbol.getTimeIn());
                    sessDestopApplication.setSchTimeOut(scheduleSymbol.getTimeOut());
                    sessDestopApplication.setSymbol(scheduleSymbol.getSymbol());
                    sessDestopApplication.setKetSymbol(scheduleSymbol.getSchedule());
                    //sessAplicationDestopAbsensiAttendance.setLabelDate(labelDate);
                    //sessAplicationDestopAbsensiAttendance.setLabelTime(labelTime);
                    SessAplicationDestopAbsensiAttendance.setUserNameLogin(sessDestopApplication.getNamaEmployee());
                    SessAplicationDestopAbsensiAttendance.setOidUserLogin(sessDestopApplication.getEmployeeId());
                    String noStation = "";
                    try {
                        noStation = DBHandler.getMesinLocation();
                    } catch (Exception exc) {
                        System.out.println("Error Station" + exc);
                    }

                    //SessAplicationDestopAbsensiAttendance sessAplicationDestopAbsensiAttendance = new SessAplicationDestopAbsensiAttendance();

                    sessAplicationDestopAbsensiAttendance.setLogoDimata(this.getsLogoDimata());
                    sessAplicationDestopAbsensiAttendance.setUrlGambarClient(this.getsUurlGambarClient());
                    sessAplicationDestopAbsensiAttendance.setUrlGambarHarisma(this.getsUrlGambarHarisma());
                    SessAplicationDestopAbsensiAttendance.setObSessDestopApplication(sessDestopApplication);
                    SessAplicationDestopAbsensiAttendance.setNoStation(noStation);

                    SessAplicationDestopAbsensiAttendance.setBtnHome(sessAplicationDestopAbsensiAttendance == null || sessAplicationDestopAbsensiAttendance.getUrlImagesIcon().length() == 0 ? "D:\\Dimata\\Aplikasi\\Absensi\\images_desktop\\home.png" : sessAplicationDestopAbsensiAttendance.getUrlImagesIcon() + "\\home.png");
                    SessAplicationDestopAbsensiAttendance.setBtnDownloadData(sessAplicationDestopAbsensiAttendance == null || sessAplicationDestopAbsensiAttendance.getUrlImagesIcon().length() == 0 ? "D:\\Dimata\\Aplikasi\\Absensi\\images_desktop\\Download.png" : sessAplicationDestopAbsensiAttendance.getUrlImagesIcon() + "\\Download.png");
                    SessAplicationDestopAbsensiAttendance.setBtnUploadData(sessAplicationDestopAbsensiAttendance == null || sessAplicationDestopAbsensiAttendance.getUrlImagesIcon().length() == 0 ? "D:\\Dimata\\Aplikasi\\Absensi\\images_desktop\\Upload.png" : sessAplicationDestopAbsensiAttendance.getUrlImagesIcon() + "\\Upload.png");
                    SessAplicationDestopAbsensiAttendance.setBtnEmployeeManagement(sessAplicationDestopAbsensiAttendance == null || sessAplicationDestopAbsensiAttendance.getUrlImagesIcon().length() == 0 ? "D:\\Dimata\\Aplikasi\\Absensi\\images_desktop\\Employee.png" : sessAplicationDestopAbsensiAttendance.getUrlImagesIcon() + "\\Employee.png");
                    SessAplicationDestopAbsensiAttendance.setBtnBakupData(sessAplicationDestopAbsensiAttendance == null || sessAplicationDestopAbsensiAttendance.getUrlImagesIcon().length() == 0 ? "D:\\Dimata\\Aplikasi\\Absensi\\images_desktop\\Backup Data.png" : sessAplicationDestopAbsensiAttendance.getUrlImagesIcon() + "\\Backup Data.png");
                    SessAplicationDestopAbsensiAttendance.setBtnLogOut(sessAplicationDestopAbsensiAttendance == null || sessAplicationDestopAbsensiAttendance.getUrlImagesIcon().length() == 0 ? "D:\\Dimata\\Aplikasi\\Absensi\\images_desktop\\Logout.png" : sessAplicationDestopAbsensiAttendance.getUrlImagesIcon() + "\\Logout.png");

                    this.dispose();
                    new AdminHome().setVisible(true);
                } else {

                    //admin
                    this.setCheckTime(false);
                    //JOptionPane jOptionPane = new JOptionPane();
                    JOptionPane.showMessageDialog(null, "<html>NIK or Password are invalid <br>  <b>User \"" + user + "\"</b> Cannot Login</html>");

                    //formater.getTimeLoginError(jOptionPane, this);
                    this.dispose();
                    new Login().setVisible(true);
                }
            }
        }

    }//GEN-LAST:event_btnActionLoginActionPerformed

    
    
//    private void DownloadAuto() {                                                 
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
//            Date dtStartEmpOutlet = new Date(); 
//            dtStartEmpOutlet.setDate(dtStartEmpOutlet.getDate()-4);
//            dtStartEmpOutlet.setMonth(dtStartEmpOutlet.getMonth());
//            dtStartEmpOutlet.setYear(dtStartEmpOutlet.getYear());
//        
//            
//            
//            Date dtEndEmpOutlet = new Date(); 
//            // maka lalukan download sesuai parameter
//            //serviceEmployeeOutletTransfer = ServiceEmployeeOutletTransfer.getInstance(false);
//            serviceEmployeeOutletTransfer = ServiceEmployeeOutletTransfer.getInstance(true);
//            if (noLocation != null && noLocation.length() > 0) {
//                if (!serviceEmployeeOutletTransfer.getStatus()) {
//                    try {
//
//                        serviceEmployeeOutletTransfer.startServiceAuto(dtStartEmpOutlet, dtEndEmpOutlet, noLocation, true, true, true, true,"");
//                       // btnRunDtEmployee.setText("Stop");
//                        
//                    } catch (Exception e) {
//                        System.out.printf("\t Exception svrmgrMachine.startTransfer() = " + e);
//                       // msgDownloadData.setText("\t Exception svrmgrMachine.startTransfer() = " + e);
//                    }
//                } else {
//                    try {
//                        serviceEmployeeOutletTransfer.stopService();
//                       // btnRunDtEmployee.setText("Run");
//                    } catch (Exception e) {
//                        System.out.printf("\t Exception svrmgrMachine.stopWatcherMachine() = " + e);
//                       
//                    }
//                }
//            } else {
//                JOptionPane.showMessageDialog(BodyContainer, "<html> Please insert no mesin in outletHarisma.xml </html>", "Inane warning", JOptionPane.WARNING_MESSAGE);
//            }
//        
//    } 
//    
//    public  static  boolean autodownload(){
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
//        try{
//              Date dt = EmployeeOutletTransferDataAuto.deleteByDateToday();
//        }catch(Exception e) {
//                System.out.println("delete data mapping outlet hari ini gagal");
//        } 
//         
//            Date dtStartEmpOutlet = new Date(); 
//            dtStartEmpOutlet.setDate(dtStartEmpOutlet.getDate()-4);
//            dtStartEmpOutlet.setMonth(dtStartEmpOutlet.getMonth());
//            dtStartEmpOutlet.setYear(dtStartEmpOutlet.getYear());
//        
//            
//            
//            Date dtEndEmpOutlet = new Date(); 
//            // maka lalukan download sesuai parameter
//            //ServiceEmployeeOutletTransfer serviceEmployeeOutletTransfer = new ServiceEmployeeOutletTransfer();
//            if (noLocation != null && noLocation.length() > 0) {
//                EmployeeOutletTransferDataAuto employeeOutletTransferDataAuto = (EmployeeOutletTransferDataAuto) ServiceEmployeeOutletTransfer.setServiceAuto(dtStartEmpOutlet, dtEndEmpOutlet, noLocation, true, true, true, true,"");
//                EmployeeOutletTransferDataAuto.autodownload(employeeOutletTransferDataAuto);
//            } else {
//                //JOptionPane.showMessageDialog(BodyContainer, "<html> Please insert no mesin in outletHarisma.xml </html>", "Inane warning", JOptionPane.WARNING_MESSAGE);
//            }
//       return status;
//    }
    
    
    
    
    private void txtUserNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUserNameActionPerformed
        // TODO add your handling code here:
        txtPassword.requestFocusInWindow();
    }//GEN-LAST:event_txtUserNameActionPerformed

    private void txtPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPasswordActionPerformed
        // TODO add your handling code here:\
        btnActionLoginActionPerformed(evt);
    }//GEN-LAST:event_txtPasswordActionPerformed

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
                if ("Metal".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                
                new Login().setVisible(true);
            }
        });

    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel BodyContainer;
    private javax.swing.JPanel PanelFooter;
    private javax.swing.JPanel body;
    private javax.swing.JButton btnActionLogin;
    private javax.swing.JComboBox cbPilihanTypeUser;
    private javax.swing.JLabel iconClient;
    private javax.swing.JLabel iconHarisma;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPaneTableRemider;
    private javax.swing.JTable jTableRemider;
    private javax.swing.JLabel judulDepan;
    private javax.swing.JLabel labelDate;
    private javax.swing.JLabel labelInfo;
    private javax.swing.JLabel labelInformation;
    private javax.swing.JLabel labelLogoDimata;
    private javax.swing.JLabel labelPassword;
    private javax.swing.JLabel labelRemider;
    private javax.swing.JLabel labelTime;
    private javax.swing.JLabel label_user_akses;
    private javax.swing.JPanel panelHeader;
    private javax.swing.JPanel panelInformation;
    private javax.swing.JPanel panelRemider;
    private javax.swing.JPanel panelUserAkses;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtUserName;
    // End of variables declaration//GEN-END:variables

    /**
     * @return the sUrlGambarHarisma
     */
    public String getsUrlGambarHarisma() {
        return sUrlGambarHarisma;
    }

    /**
     * @param sUrlGambarHarisma the sUrlGambarHarisma to set
     */
    public void setsUrlGambarHarisma(String sUrlGambarHarisma) {
        this.sUrlGambarHarisma = sUrlGambarHarisma;
    }

    /**
     * @return the sUurlGambarClient
     */
    public String getsUurlGambarClient() {
        return sUurlGambarClient;
    }

    /**
     * @param sUurlGambarClient the sUurlGambarClient to set
     */
    public void setsUurlGambarClient(String sUurlGambarClient) {
        this.sUurlGambarClient = sUurlGambarClient;
    }

    /**
     * @return the sLogoDimata
     */
    public String getsLogoDimata() {
        return sLogoDimata;
    }

    /**
     * @param sLogoDimata the sLogoDimata to set
     */
    public void setsLogoDimata(String sLogoDimata) {
        this.sLogoDimata = sLogoDimata;
    }

    /**
     * @return the sUrlIconLogin
     */
    public String getsUrlIconLogin() {
        return sUrlIconLogin;
    }

    /**
     * @param sUrlIconLogin the sUrlIconLogin to set
     */
    public void setsUrlIconLogin(String sUrlIconLogin) {
        this.sUrlIconLogin = sUrlIconLogin;
    }

    /**
     * @return the checkTime
     */
    public boolean isCheckTime() {
        return checkTime;
    }

    /**
     * @param checkTime the checkTime to set
     */
    public void setCheckTime(boolean checkTime) {
        this.checkTime = checkTime;
    }

    /**
     * @return the user
     */
    public String getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(String user) {
        this.user = user;
    }
}
