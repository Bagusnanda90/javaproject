/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.utility.harisma.machine;

import com.dimata.harisma.entity.attendance.MachineTransaction;
import com.dimata.harisma.session.aplikasidesktop.attendance.SessAplicationDestopAbsensiAttendance;
import com.dimata.harisma.session.aplikasidesktop.attendance.SessDestopApplication;
import com.dimata.harisma.utility.harisma.machine.db.DBHandler;
import com.dimata.harisma.utility.harisma.machine.transferdataemployee.ServiceEmployeeOutletTransfer;
import com.dimata.harisma.utility.harisma.machine.transferdataemployee.ServiceEmployeeOutletTransferUpload;
import com.dimata.harisma.utility.harisma.machine.transferdataemployee.ServiceTransferDataPresence;
import com.dimata.harisma.utility.harisma.masterdatadesktop.entity.PstMachineTransactionDesktop;
import com.dimata.harisma.utility.harisma.masterdatadesktop.entity.PstMachineTransactionDesktopDummy;
import com.dimata.util.Formater;
import com.dimata.util.ImagesParser;
import java.awt.Color;
import java.util.Date;
import java.util.Vector;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Dimata 007
 */
public class AdminUploadData extends javax.swing.JFrame {

    /**
     * Creates new form AdminHome
     */
    private SessDestopApplication obSessDestopApplication = new SessDestopApplication();
    private final String[] okFileExtensions = new String[]{"sql"};
    public static String menuUrl;
    ServiceEmployeeOutletTransfer serviceEmployeeOutletTransfer = ServiceEmployeeOutletTransfer.getInstance(false);
    ServiceTransferDataPresence serviceTransferDataPresence = ServiceTransferDataPresence.getInstance(false);
    ServiceEmployeeOutletTransferUpload serviceEmployeeOutletTransferUpload = ServiceEmployeeOutletTransferUpload.getInstance(false);
    //ServiceTransferDataPresence serviceTransferDataPresence = ServiceTransferDataPresence.getInstance(false);
    //ServiceEmployeeScheduleTransfer serviceEmployeeScheduleTransfer = ServiceEmployeeScheduleTransfer.getInstance(false);
    //ServiceScheduleTransfer serviceScheduleTransfer = ServiceScheduleTransfer.getInstance(false);

    public AdminUploadData() {

        Formater formater = new Formater();
        ImagesParser imagesParser = new ImagesParser();
        initComponents();
        BodyContainer.setBackground(SessAplicationDestopAbsensiAttendance.getColor() != null ? SessAplicationDestopAbsensiAttendance.getColor() : Color.WHITE);
        body.setBackground(SessAplicationDestopAbsensiAttendance.getColor() != null ? SessAplicationDestopAbsensiAttendance.getColor() : Color.WHITE);
        this.setResizable(false);
        btnHome2.setIcon(new javax.swing.ImageIcon(SessAplicationDestopAbsensiAttendance.getBtnHome())); // NOI18N
        btnDownloadData2.setIcon(new javax.swing.ImageIcon(SessAplicationDestopAbsensiAttendance.getBtnDownloadData())); // NOI18N
        btnUploadData2.setIcon(new javax.swing.ImageIcon(SessAplicationDestopAbsensiAttendance.getBtnUploadData())); // NOI18N
        btnEmployeeManagement2.setIcon(new javax.swing.ImageIcon(SessAplicationDestopAbsensiAttendance.getBtnEmployeeManagement())); // NOI18N
        btnBakupData2.setIcon(new javax.swing.ImageIcon(SessAplicationDestopAbsensiAttendance.getBtnBakupData())); // NOI18N
        btnLogOut2.setIcon(new javax.swing.ImageIcon(SessAplicationDestopAbsensiAttendance.getBtnLogOut())); // NOI18N
        imagesParser.tampilkanImage(iconHarisma, iconClient, SessAplicationDestopAbsensiAttendance.getUrlGambarHarisma(), SessAplicationDestopAbsensiAttendance.getUrlGambarClient());
        imagesParser.tampilkanImage(labelLogoDimata, SessAplicationDestopAbsensiAttendance.getLogoDimata());
        labelDate.setText("");
        labelTime.setText("");
        formater.getTimeInJavaDesktop(labelTime, labelDate);
        obSessDestopApplication = new SessDestopApplication();

        try {
            obSessDestopApplication = SessAplicationDestopAbsensiAttendance.getObSessDestopApplication();
        } catch (Exception exc) {
        }
        String namaEmp = (obSessDestopApplication.getNamaEmployee() != null && obSessDestopApplication.getNamaEmployee().length() > 0 ? "" + obSessDestopApplication.getNamaEmployee() : "-");
        labelNamaEmp.setText("<html><p> Name: <br>&nbsp;" + namaEmp + "</p></html>");
        labelPosition.setText("<html><p> Position: <br>&nbsp;" + (obSessDestopApplication.getPositionName() != null && obSessDestopApplication.getPositionName().length() > 0 ? "" + obSessDestopApplication.getPositionName() : "-") + "</p></html>");

        setLocationRelativeTo(this);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        //labelMenu.setText("<html>Download Data :</html>");
        btnSaveIn.setVisible(false);
        //lblProgress.setVisible(false);

        if (menuUrl != null && menuUrl.equalsIgnoreCase("Bakup Data")) {

            btnSaveIn.setVisible(true);
            labelDownloadDtEmployee.setText("Backup Database to Directory");
            labelDownloadInformation.setText("Repair Database");
            labelImportFlasdisk.setVisible(false);
            btnBrowseFd.setVisible(false);
            btnRunFd.setVisible(false);
            lblProgressFlasdisk.setVisible(false);
            jProgressBarFd.setVisible(false);

            msgFlasdisk.setVisible(false);
            labelMenu.setText("<html>Bakup Data :</html>");

            labelStartDateAttendance.setVisible(false);
            txtStartDateInformation.setVisible(false);
            labelEndDateAttendance.setVisible(false);
            txtDateEndInformation.setVisible(false);
            labelStartDateUpload.setVisible(false);
            dtStartUpload.setVisible(false);
            labelEndDateUpload.setVisible(false);
            dtEndUpload.setVisible(false);
            txtSaveExFd.setVisible(false);
            dtStartExFd.setVisible(false);
            dtEndExFd.setVisible(false);
            labelStartDateFd.setVisible(false);
            labelEndDateFd.setVisible(false);
        } else if (menuUrl != null && menuUrl.equalsIgnoreCase("Upload Data")) {
            labelDownloadDtEmployee.setText("Upload Employee Data to Server");
            labelDownloadInformation.setText("Upload Attendance Data to Server");
            labelImportFlasdisk.setText("Export data Absensi to flashdisk");
            msgUploadData.setVisible(true);
            labelMenu.setText("<html>Upload Data :</html>");
            txtBakupBrowse.setVisible(false);
            txtSaveExFd.setEditable(false);
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

        fileBrowser = new usu.widget.FileBrowser();
        BodyContainer = new javax.swing.JPanel();
        body = new javax.swing.JPanel();
        labelDate = new javax.swing.JLabel();
        labelTime = new javax.swing.JLabel();
        labelNamaEmp = new javax.swing.JLabel();
        labelPosition = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        labelGarisTengahButton = new javax.swing.JLabel();
        panelButtonMenu2 = new usu.widget.Panel();
        labelGarisKiriButton2 = new javax.swing.JLabel();
        btnHome2 = new usu.widget.ButtonGlass();
        labelGarisKananLabel2 = new javax.swing.JLabel();
        btnDownloadData2 = new usu.widget.ButtonGlass();
        btnUploadData2 = new usu.widget.ButtonGlass();
        btnEmployeeManagement2 = new usu.widget.ButtonGlass();
        btnBakupData2 = new usu.widget.ButtonGlass();
        btnLogOut2 = new usu.widget.ButtonGlass();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        labelDownloadDtEmployee = new javax.swing.JLabel();
        btnSaveIn = new javax.swing.JButton();
        btnRunDtEmployee = new javax.swing.JButton();
        lblProgress = new javax.swing.JLabel();
        jProgressBarDtEmployeeOrBakup = new javax.swing.JProgressBar();
        msgUploadData = new javax.swing.JLabel();
        labelDownloadInformation = new javax.swing.JLabel();
        btnRunInformation = new javax.swing.JButton();
        lblProgressInformation = new javax.swing.JLabel();
        jProgressBarAttendanceOrRepairDb = new javax.swing.JProgressBar();
        labelStartDateAttendance = new javax.swing.JLabel();
        txtStartDateInformation = new com.toedter.calendar.JDateChooser();
        labelEndDateAttendance = new javax.swing.JLabel();
        txtDateEndInformation = new com.toedter.calendar.JDateChooser();
        labelImportFlasdisk = new javax.swing.JLabel();
        btnBrowseFd = new javax.swing.JButton();
        btnRunFd = new javax.swing.JButton();
        jProgressBarFd = new javax.swing.JProgressBar();
        lblProgressFlasdisk = new javax.swing.JLabel();
        labelStartDateUpload = new javax.swing.JLabel();
        dtStartUpload = new com.toedter.calendar.JDateChooser();
        labelEndDateUpload = new javax.swing.JLabel();
        dtEndUpload = new com.toedter.calendar.JDateChooser();
        msgUploadDataPresence = new javax.swing.JLabel();
        msgFlasdisk = new javax.swing.JLabel();
        txtSaveExFd = new javax.swing.JTextField();
        txtBakupBrowse = new javax.swing.JTextField();
        labelStartDateFd = new javax.swing.JLabel();
        dtStartExFd = new com.toedter.calendar.JDateChooser();
        labelEndDateFd = new javax.swing.JLabel();
        dtEndExFd = new com.toedter.calendar.JDateChooser();
        labelMenu = new javax.swing.JLabel();
        panelHeader = new javax.swing.JPanel();
        iconHarisma = new javax.swing.JLabel();
        judulDepan = new javax.swing.JLabel();
        iconClient = new javax.swing.JLabel();
        labelGarisAja = new javax.swing.JLabel();
        labelLogoDimata = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        BodyContainer.setBackground(new java.awt.Color(255, 255, 255));

        body.setBackground(new java.awt.Color(255, 255, 255));
        body.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        body.setName("body"); // NOI18N
        body.setPreferredSize(new java.awt.Dimension(500, 410));

        labelDate.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        labelDate.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelDate.setText("date");
        labelDate.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(0, 0, 0), new java.awt.Color(0, 0, 0)));

        labelTime.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        labelTime.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelTime.setText("time");
        labelTime.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(0, 0, 0), new java.awt.Color(0, 0, 0)));

        labelNamaEmp.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        labelNamaEmp.setText("jLabel1");
        labelNamaEmp.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        labelPosition.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        labelPosition.setText("jLabel1");
        labelPosition.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jSeparator1.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        panelButtonMenu2.setBackground(new java.awt.Color(255, 255, 255));

        btnHome2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHome2ActionPerformed(evt);
            }
        });

        btnDownloadData2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDownloadData2ActionPerformed(evt);
            }
        });

        btnUploadData2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUploadData2ActionPerformed(evt);
            }
        });

        btnEmployeeManagement2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEmployeeManagement2ActionPerformed(evt);
            }
        });

        btnBakupData2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBakupData2ActionPerformed(evt);
            }
        });

        btnLogOut2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogOut2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelButtonMenu2Layout = new javax.swing.GroupLayout(panelButtonMenu2);
        panelButtonMenu2.setLayout(panelButtonMenu2Layout);
        panelButtonMenu2Layout.setHorizontalGroup(
            panelButtonMenu2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelButtonMenu2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelGarisKananLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 8, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(btnHome2, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(btnDownloadData2, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(btnUploadData2, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(btnEmployeeManagement2, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(btnBakupData2, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(btnLogOut2, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(labelGarisKiriButton2, javax.swing.GroupLayout.DEFAULT_SIZE, 7, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelButtonMenu2Layout.setVerticalGroup(
            panelButtonMenu2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelButtonMenu2Layout.createSequentialGroup()
                .addGroup(panelButtonMenu2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelGarisKananLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(labelGarisKiriButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(50, 50, 50))
            .addGroup(panelButtonMenu2Layout.createSequentialGroup()
                .addGroup(panelButtonMenu2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnHome2, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDownloadData2, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUploadData2, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEmployeeManagement2, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBakupData2, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(panelButtonMenu2Layout.createSequentialGroup()
                .addComponent(btnLogOut2, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jSeparator3.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        jSeparator2.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        labelDownloadDtEmployee.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        labelDownloadDtEmployee.setText("Upload Employee Data To Server");
        labelDownloadDtEmployee.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        btnSaveIn.setText("Save In");
        btnSaveIn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveInActionPerformed(evt);
            }
        });

        btnRunDtEmployee.setText("Run");
        btnRunDtEmployee.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRunDtEmployeeActionPerformed(evt);
            }
        });

        lblProgress.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        lblProgress.setText("Progress :");

        jProgressBarDtEmployeeOrBakup.setStringPainted(true);

        msgUploadData.setText("message download data");

        labelDownloadInformation.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        labelDownloadInformation.setText("Upload Attendance Data to Server");
        labelDownloadInformation.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        btnRunInformation.setText("Run");
        btnRunInformation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRunInformationActionPerformed(evt);
            }
        });

        lblProgressInformation.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        lblProgressInformation.setText("Progress :");

        jProgressBarAttendanceOrRepairDb.setStringPainted(true);

        labelStartDateAttendance.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        labelStartDateAttendance.setText("Start Date");

        labelEndDateAttendance.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        labelEndDateAttendance.setText("End Date");

        labelImportFlasdisk.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        labelImportFlasdisk.setText("Export data to flashdisk");
        labelImportFlasdisk.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        btnBrowseFd.setText("Save");
        btnBrowseFd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBrowseFdActionPerformed(evt);
            }
        });

        btnRunFd.setText("Run");
        btnRunFd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRunFdActionPerformed(evt);
            }
        });

        jProgressBarFd.setStringPainted(true);

        lblProgressFlasdisk.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        lblProgressFlasdisk.setText("Progress :");

        labelStartDateUpload.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        labelStartDateUpload.setText("Start Date");

        labelEndDateUpload.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        labelEndDateUpload.setText("End Date");

        msgUploadDataPresence.setText("message download data");

        msgFlasdisk.setText("message download data");

        labelStartDateFd.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        labelStartDateFd.setText("Start Date");

        labelEndDateFd.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        labelEndDateFd.setText("End Date");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(labelStartDateAttendance, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtStartDateInformation, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(labelEndDateAttendance, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtDateEndInformation, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(labelStartDateUpload, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dtStartUpload, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(labelEndDateUpload, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dtEndUpload, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(labelDownloadDtEmployee, javax.swing.GroupLayout.PREFERRED_SIZE, 328, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtBakupBrowse, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSaveIn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnRunDtEmployee, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(labelDownloadInformation, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(lblProgress)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jProgressBarDtEmployeeOrBakup, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnRunInformation, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(msgUploadData, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lblProgressInformation)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jProgressBarAttendanceOrRepairDb, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(msgUploadDataPresence, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(labelStartDateFd, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(dtStartExFd, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(labelEndDateFd, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(dtEndExFd, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(labelImportFlasdisk, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtSaveExFd, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnBrowseFd, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnRunFd, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(131, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblProgressFlasdisk)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jProgressBarFd, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(msgFlasdisk, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(labelDownloadDtEmployee)
                                .addComponent(btnSaveIn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnRunDtEmployee))
                            .addComponent(txtBakupBrowse, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dtStartUpload, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dtEndUpload, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelStartDateUpload)
                            .addComponent(labelEndDateUpload))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(labelImportFlasdisk)
                                .addComponent(btnBrowseFd)
                                .addComponent(btnRunFd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(txtSaveExFd, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dtStartExFd, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dtEndExFd, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelStartDateFd)
                            .addComponent(labelEndDateFd))
                        .addGap(7, 7, 7)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblProgress)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jProgressBarDtEmployeeOrBakup, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(msgUploadData, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lblProgressFlasdisk, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jProgressBarFd, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(msgFlasdisk, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelDownloadInformation, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnRunInformation, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(8, 8, 8)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblProgressInformation)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jProgressBarAttendanceOrRepairDb, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(msgUploadDataPresence, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtStartDateInformation, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDateEndInformation, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelStartDateAttendance)
                    .addComponent(labelEndDateAttendance))
                .addGap(141, 141, 141))
        );

        jScrollPane1.setViewportView(jPanel1);

        labelMenu.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        labelMenu.setText("jLabel1");

        javax.swing.GroupLayout bodyLayout = new javax.swing.GroupLayout(body);
        body.setLayout(bodyLayout);
        bodyLayout.setHorizontalGroup(
            bodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bodyLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(bodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(bodyLayout.createSequentialGroup()
                        .addComponent(labelNamaEmp, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labelPosition, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labelDate, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labelTime, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(bodyLayout.createSequentialGroup()
                        .addComponent(labelGarisTengahButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(bodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(bodyLayout.createSequentialGroup()
                                .addComponent(labelMenu)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jSeparator3, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(bodyLayout.createSequentialGroup()
                                .addGroup(bodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(panelButtonMenu2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jSeparator2))
                                .addGap(4, 4, 4))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))))
                .addContainerGap())
        );
        bodyLayout.setVerticalGroup(
            bodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bodyLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(bodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(bodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(labelDate, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(labelTime, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(bodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(labelPosition, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(labelNamaEmp, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE))
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addGroup(bodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(bodyLayout.createSequentialGroup()
                        .addComponent(labelGarisTengahButton, javax.swing.GroupLayout.DEFAULT_SIZE, 91, Short.MAX_VALUE)
                        .addGap(270, 270, 270))
                    .addGroup(bodyLayout.createSequentialGroup()
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panelButtonMenu2, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labelMenu)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 213, Short.MAX_VALUE)
                        .addGap(15, 15, 15))))
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
                    .addComponent(judulDepan, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 71, Short.MAX_VALUE))
                .addGap(5, 5, 5))
        );

        javax.swing.GroupLayout BodyContainerLayout = new javax.swing.GroupLayout(BodyContainer);
        BodyContainer.setLayout(BodyContainerLayout);
        BodyContainerLayout.setHorizontalGroup(
            BodyContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelHeader, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(BodyContainerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(BodyContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(body, javax.swing.GroupLayout.DEFAULT_SIZE, 723, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, BodyContainerLayout.createSequentialGroup()
                        .addComponent(labelGarisAja, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(2, 2, 2)
                        .addComponent(labelLogoDimata, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        BodyContainerLayout.setVerticalGroup(
            BodyContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, BodyContainerLayout.createSequentialGroup()
                .addComponent(panelHeader, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(body, javax.swing.GroupLayout.DEFAULT_SIZE, 416, Short.MAX_VALUE)
                .addGap(10, 10, 10)
                .addGroup(BodyContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelGarisAja, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelLogoDimata, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(BodyContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(BodyContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnRunInformationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRunInformationActionPerformed
        // TODO add your handling code here:
        String noLocation = "";//DBHandler.getMesinLocation();
        try {
            noLocation = DBHandler.getMesinLocation();
        } catch (Exception exc) {
            msgUploadDataPresence.setText("Please insert no mesin in outletHarisma.xml" + exc);
        }
        if (menuUrl != null && menuUrl.equalsIgnoreCase("Bakup Data")) {
            //untuk repair database
            try {
                String url[] = SessAplicationDestopAbsensiAttendance.getUrlLocationMysql() != null && SessAplicationDestopAbsensiAttendance.getUrlLocationMysql().length() > 0 ? SessAplicationDestopAbsensiAttendance.getUrlLocationMysql().split(",") : null;
                String command = url != null && url.length == 2 ? (String) url[0] : "";
                String commandUty = url != null && url.length == 2 ? (String) url[1] : "";
                String sDbnPort[] = SessAplicationDestopAbsensiAttendance.getPortnDbName() != null ? SessAplicationDestopAbsensiAttendance.getPortnDbName().split(",") : null;
                String host = sDbnPort != null && sDbnPort.length == 3 ? (String) sDbnPort[0] : "";
                String port = sDbnPort != null && sDbnPort.length == 3 ? (String) sDbnPort[1] : "";
                String nmDb = sDbnPort != null && sDbnPort.length == 3 ? (String) sDbnPort[2] : "";
                //String[] sql = new String[]{"cmd.exe", "/c", "mysql -h" + host + " -u" + SessAplicationDestopAbsensiAttendance.getUsernameMysql() + " -p" + SessAplicationDestopAbsensiAttendance.getPasswordMysql() + " -P" + port + " " + nmDb + "<" + txtFldBroseFile.getText()};
                String[] sql = new String[]{command, commandUty, "mysqlcheck  --auto-repair" + " -h" + host + " -u" + SessAplicationDestopAbsensiAttendance.getUsernameMysql() + " -p" + SessAplicationDestopAbsensiAttendance.getPasswordMysql() + " -P" + port + " " + nmDb};
                if (command.length() > 0 && commandUty.length() > 0 && nmDb.length() > 0 && port.length() > 0 && host.length() > 0) {
                    Process prosess = Runtime.getRuntime().exec(sql);
                    int prosesSukses = prosess.waitFor();
                    if (prosesSukses == 0) {
                        jProgressBarAttendanceOrRepairDb.setValue(100);
                        msgUploadDataPresence.setText("repair database Sukses");
                    } else {
                        //JOptionPane.showMessageDialog(null, "restore database gagal");
                        msgUploadDataPresence.setText("repair database gagal");
                    }
                } else {
                    msgUploadDataPresence.setText("repair database gagal");
                }
            } catch (Exception exc) {
                msgUploadDataPresence.setText("repair database gagal" + exc);
            }

        } else if (menuUrl != null && menuUrl.equalsIgnoreCase("Upload Data")) {
            //untuk upload data bro
            if (true) {
                // maka lalukan download sesuai parameter
                serviceTransferDataPresence = ServiceTransferDataPresence.getInstance(true);
                if (noLocation != null && noLocation.length() > 0) {
                    if (!serviceTransferDataPresence.getStatus() && btnRunInformation.getText().equalsIgnoreCase("Run")) {
                        try {
                            serviceTransferDataPresence.startService(dtStartUpload.getDate(), dtEndUpload.getDate(), noLocation, jProgressBarAttendanceOrRepairDb, msgUploadDataPresence, btnRunInformation);
                            btnRunInformation.setText("Stop");
                        } catch (Exception e) {
                            msgUploadDataPresence.setText("\t Exception svrmgrMachine.startTransfer() = " + e);
                        }
                    } else {
                        try {
                            serviceTransferDataPresence.stopService();
                            btnRunDtEmployee.setText("Run");
                        } catch (Exception e) {
                            msgUploadDataPresence.setText("\t Exception svrmgrMachine.stopWatcherMachine() = " + e);
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(BodyContainer, "<html> Please insert no mesin in outletHarisma.xml </html>", "Inane warning", JOptionPane.WARNING_MESSAGE);
                }
            } else {
                //lakukan download semua
                JOptionPane.showMessageDialog(BodyContainer, "<html> Please Select Date </html>", "Inane warning", JOptionPane.WARNING_MESSAGE);

            }
        } else {
            //bakup data
        }
    }//GEN-LAST:event_btnRunInformationActionPerformed

    private void btnRunDtEmployeeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRunDtEmployeeActionPerformed
        // TODO add your handling code here:
        if (menuUrl != null && menuUrl.equalsIgnoreCase("Bakup Data")) {
            if (txtBakupBrowse != null && txtBakupBrowse.getText() != null) {
                String formatSql = ".sql";
                String[] valueTxtFldBrowser = txtBakupBrowse.getText().split(formatSql);
                if (valueTxtFldBrowser != null) {
                    try {
                        if (txtBakupBrowse.getText().isEmpty()) {
                            JOptionPane.showMessageDialog(null, "Please select the sql data");
                        } else {
                            String url[] = SessAplicationDestopAbsensiAttendance.getUrlLocationMysql() != null && SessAplicationDestopAbsensiAttendance.getUrlLocationMysql().length() > 0 ? SessAplicationDestopAbsensiAttendance.getUrlLocationMysql().split(",") : null;
                            String command = url != null && url.length == 2 ? (String) url[0] : "";
                            String commandUty = url != null && url.length == 2 ? (String) url[1] : "";
                            String sDbnPort[] = SessAplicationDestopAbsensiAttendance.getPortnDbName() != null ? SessAplicationDestopAbsensiAttendance.getPortnDbName().split(",") : null;
                            String host = sDbnPort != null && sDbnPort.length == 3 ? (String) sDbnPort[0] : "";
                            String port = sDbnPort != null && sDbnPort.length == 3 ? (String) sDbnPort[1] : "";
                            String nmDb = sDbnPort != null && sDbnPort.length == 3 ? (String) sDbnPort[2] : "";
                            //String[] sql = new String[]{"cmd.exe", "/c", "mysql -h" + host + " -u" + SessAplicationDestopAbsensiAttendance.getUsernameMysql() + " -p" + SessAplicationDestopAbsensiAttendance.getPasswordMysql() + " -P" + port + " " + nmDb + "<" + txtFldBroseFile.getText()};
                            String[] sql = new String[]{command, commandUty, "mysqldump -h" + host + " -u" + SessAplicationDestopAbsensiAttendance.getUsernameMysql() + " -p" + SessAplicationDestopAbsensiAttendance.getPasswordMysql() + " -P" + port + " " + nmDb + ">" + txtBakupBrowse.getText()};
                            if (command.length() > 0 && commandUty.length() > 0 && nmDb.length() > 0 && port.length() > 0 && host.length() > 0) {
                                Process run = Runtime.getRuntime().exec(sql);
                                int prosesSukses = run.waitFor();
                                if (prosesSukses == 0) {
                                    jProgressBarDtEmployeeOrBakup.setValue(100);
                                    msgUploadData.setText("bakup database Sukses");
                                } else {
                                    //JOptionPane.showMessageDialog(null, "restore database gagal");
                                    msgUploadData.setText("bakup database gagal");
                                }
                            } else {
                                msgUploadData.setText("bakup database gagal");
                            }
                        }
                    } catch (Exception e) {
                        //JOptionPane.showMessageDialog(null, "restore database gagal, Periksa kembali");
                        msgUploadData.setText("bakup database gagal, please check again");
                    }
                } else {
                    // format
                    JOptionPane.showMessageDialog(null, "Not Format .sql");
                }
            }
        } else if (menuUrl != null && menuUrl.equalsIgnoreCase("Upload Data")) {
            if (dtStartUpload.getDate() != null && dtEndUpload.getDate() != null) {
                // maka lalukan download sesuai parameter
                //serviceEmployeeOutletTransfer = ServiceEmployeeOutletTransfer.getInstance(false);
                serviceEmployeeOutletTransferUpload = ServiceEmployeeOutletTransferUpload.getInstance(true);
                String noLocation = "";//DBHandler.getMesinLocation();
                try {
                    noLocation = DBHandler.getMesinLocation();
                } catch (Exception exc) {
                    msgUploadData.setText("Please insert no mesin in outletHarisma.xml" + exc);
                }
                if (noLocation != null && noLocation.length() > 0) {
                    if (!serviceEmployeeOutletTransferUpload.getStatus() && btnRunDtEmployee.getText().equalsIgnoreCase("Run")) {
                        try {

                            serviceEmployeeOutletTransferUpload.startService(dtStartUpload.getDate(), dtEndUpload.getDate(), noLocation, jProgressBarDtEmployeeOrBakup, msgUploadData, btnRunDtEmployee);
                            btnRunDtEmployee.setText("Stop");
                            if (serviceEmployeeOutletTransfer != null) {
                                msgUploadData.setText(serviceEmployeeOutletTransfer.getMessageTransferEmployee());
                            }
                        } catch (Exception e) {
                            msgUploadData.setText("\t Exception svrmgrMachine.stopTransfer upload data = " + e);
                        }
                    } else {
                        try {
                            serviceEmployeeOutletTransfer.stopService();
                            btnRunDtEmployee.setText("Run");
                        } catch (Exception e) {
                            msgUploadData.setText("\t Exception svrmgrMachine.stopTransfer upload data = " + e);
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(BodyContainer, "<html> Please insert no mesin in outletHarisma.xml </html>", "Inane warning", JOptionPane.WARNING_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(BodyContainer, "<html> Date is null please check again </html>", "Inane warning", JOptionPane.WARNING_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnRunDtEmployeeActionPerformed

    private void btnSaveInActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveInActionPerformed
        // TODO add your handling code here:
        if (menuUrl != null && menuUrl.equalsIgnoreCase("Bakup Data")) {
            int returnVal = fileBrowser.showSaveDialog(this);
            if (returnVal == fileBrowser.APPROVE_OPTION) {
                String fileSelected = "";
                if (fileBrowser.getSelectedFile().getPath() != null && fileBrowser.getSelectedFile().getPath().length() > 0) {
                    fileSelected = fileBrowser.getSelectedFile().getPath().toLowerCase();
                    fileSelected = fileSelected.replace(".sql", "");
                }
                txtBakupBrowse.setText(fileSelected + ".sql");
            }
        } else if (menuUrl != null && menuUrl.equalsIgnoreCase("Upload Data")) {
        }
    }//GEN-LAST:event_btnSaveInActionPerformed

    private void btnLogOut2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogOut2ActionPerformed
        // TODO add your handling code here:
        this.dispose();
        new Login().setVisible(true);
    }//GEN-LAST:event_btnLogOut2ActionPerformed

    private void btnBakupData2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBakupData2ActionPerformed
        // TODO add your handling code here:
        this.dispose();
        AdminUploadData.setMenuUrl("Bakup Data");
        new AdminUploadData().setVisible(true);
    }//GEN-LAST:event_btnBakupData2ActionPerformed

    private void btnEmployeeManagement2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEmployeeManagement2ActionPerformed
        // TODO add your handling code here:
        this.dispose();
        new AdminEmployeeManagement().setVisible(true);
    }//GEN-LAST:event_btnEmployeeManagement2ActionPerformed

    private void btnUploadData2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUploadData2ActionPerformed
        // TODO add your handling code here:
        this.dispose();
        AdminUploadData.setMenuUrl("Upload Data");
        new AdminUploadData().setVisible(true);
    }//GEN-LAST:event_btnUploadData2ActionPerformed

    private void btnDownloadData2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDownloadData2ActionPerformed
        // TODO add your handling code here:
        this.dispose();
        //AdminDownloadBakupData.setMenuUrl("Download Data");
        new AdminDownloadData().setVisible(true);
    }//GEN-LAST:event_btnDownloadData2ActionPerformed

    private void btnHome2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHome2ActionPerformed
        // TODO add your handling code here:
        this.dispose();
        new AdminHome().setVisible(true);
    }//GEN-LAST:event_btnHome2ActionPerformed

    private void btnBrowseFdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBrowseFdActionPerformed
        // TODO add your handling code here:
        if (menuUrl != null && menuUrl.equalsIgnoreCase("Upload Data")) {
            int returnVal = fileBrowser.showSaveDialog(this);
            if (returnVal == fileBrowser.APPROVE_OPTION) {
                String fileSelected = "";
                if (fileBrowser.getSelectedFile().getPath() != null && fileBrowser.getSelectedFile().getPath().length() > 0) {
                    fileSelected = fileBrowser.getSelectedFile().getPath().toLowerCase();
                    fileSelected = fileSelected.replace(".sql", "");
                }
                txtSaveExFd.setText(fileSelected + ".sql");
            }
        }
    }//GEN-LAST:event_btnBrowseFdActionPerformed

    private void btnRunFdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRunFdActionPerformed
        // TODO add your handling code here:
        if (menuUrl != null && menuUrl.equalsIgnoreCase("Bakup Data")) {
            //s
        } else if (menuUrl != null && menuUrl.equalsIgnoreCase("Upload Data")) {
            //Export Data Absensi
            if (dtStartExFd.getDate() != null && dtEndExFd.getDate() != null) {
                Date dtStart = dtStartExFd.getDate();
                Date dtEnd = dtEndExFd.getDate();
                if (dtStart.getTime() > dtEnd.getTime()) {
                    Date tempFromDate = dtStart;
                    Date tempToDate = dtEnd;
                    dtStart = tempToDate;
                    dtEnd = tempFromDate;
                }

                String whereClause = PstMachineTransactionDesktop.fieldNames[PstMachineTransactionDesktop.FLD_DATE_TRANS] + " between \"" + Formater.formatDate(dtStart, "yyyy-MM-dd 00:00:00") + "\" AND \"" + Formater.formatDate(dtEnd, "yyyy-MM-dd 23:59:59") + "\"";
                Vector listDtAbs = PstMachineTransactionDesktop.list(0, 0, whereClause, "");
                if (listDtAbs != null && listDtAbs.size() > 0) {
                    int delAllsuccess = PstMachineTransactionDesktopDummy.deleteAllDummy();
                    if (delAllsuccess > 0) {
                        msgFlasdisk.setText("Delete All Dummy Success");
                    }
                    for (int i = 0; i < listDtAbs.size(); i++) {
                        MachineTransaction machineTransaction = (MachineTransaction) listDtAbs.get(i);
                        try {
                            PstMachineTransactionDesktopDummy.insertExc(machineTransaction);
                            if (i <= 60) {
                                jProgressBarFd.setValue(i);
                            } else if (i == listDtAbs.size()) {
                                jProgressBarFd.setValue(60);
                            }
                        } catch (Exception exc) {
                            msgFlasdisk.setText("Exc" + exc);
                        }
                    }
                    if (txtSaveExFd != null && txtSaveExFd.getText() != null) {
                        String formatSql = ".sql";
                        String[] valueTxtFldBrowser = txtSaveExFd.getText().split(formatSql);
                        if (valueTxtFldBrowser != null) {
                            try {
                                if (txtSaveExFd.getText().isEmpty()) {
                                    JOptionPane.showMessageDialog(null, "Please Save Directory the sql data");
                                } else {
                                    String url[] = SessAplicationDestopAbsensiAttendance.getUrlLocationMysql() != null && SessAplicationDestopAbsensiAttendance.getUrlLocationMysql().length() > 0 ? SessAplicationDestopAbsensiAttendance.getUrlLocationMysql().split(",") : null;
                                    String command = url != null && url.length == 2 ? (String) url[0] : "";
                                    String commandUty = url != null && url.length == 2 ? (String) url[1] : "";
                                    String sDbnPort[] = SessAplicationDestopAbsensiAttendance.getPortnDbName() != null ? SessAplicationDestopAbsensiAttendance.getPortnDbName().split(",") : null;
                                    String host = sDbnPort != null && sDbnPort.length == 3 ? (String) sDbnPort[0] : "";
                                    String port = sDbnPort != null && sDbnPort.length == 3 ? (String) sDbnPort[1] : "";
                                    String nmDb = sDbnPort != null && sDbnPort.length == 3 ? (String) sDbnPort[2] : "";
                                    //String[] sql = new String[]{"cmd.exe", "/c", "mysql -h" + host + " -u" + SessAplicationDestopAbsensiAttendance.getUsernameMysql() + " -p" + SessAplicationDestopAbsensiAttendance.getPasswordMysql() + " -P" + port + " " + nmDb + "<" + txtFldBroseFile.getText()};
                                    String[] sql = new String[]{command, commandUty, "mysqldump -f -n -t -c  --skip-extended-insert -h" + host + " -u" + SessAplicationDestopAbsensiAttendance.getUsernameMysql() + " -p" + SessAplicationDestopAbsensiAttendance.getPasswordMysql() + " -P" + port + " " + nmDb + " " + PstMachineTransactionDesktopDummy.TBL_HR_MACHINE_TRANS + " >" + txtSaveExFd.getText()};
                                    if (command.length() > 0 && commandUty.length() > 0 && nmDb.length() > 0 && port.length() > 0 && host.length() > 0) {
                                        Process run = Runtime.getRuntime().exec(sql);
                                        int prosesSukses = run.waitFor();
                                        if (prosesSukses == 0) {
                                            jProgressBarFd.setValue(100);
                                            msgFlasdisk.setText("Export Flasdisk Absensi database Sukses");
                                        } else {
                                            //JOptionPane.showMessageDialog(null, "restore database gagal");
                                            msgFlasdisk.setText("Export Flasdisk Absensi database gagal");
                                        }
                                    } else {
                                        msgFlasdisk.setText("Export Flasdisk Absensi database gagal");
                                    }
                                }
                            } catch (Exception e) {
                                //JOptionPane.showMessageDialog(null, "restore database gagal, Periksa kembali");
                                msgFlasdisk.setText("Export Flasdisk Absensi database gagal, please check again");
                            }
                        } else {
                            // format
                            JOptionPane.showMessageDialog(null, "Not Format .sql");
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(BodyContainer, "<html> No Data Absensi Dari Tanggal yang di pilih </html>", "Inane warning", JOptionPane.WARNING_MESSAGE);
                }
            } else {
                //msgUploadDataPresence.setText("Please insert no mesin in outletHarisma.xml" + exc);
                JOptionPane.showMessageDialog(BodyContainer, "<html> Please insert Date start or Date to </html>", "Inane warning", JOptionPane.WARNING_MESSAGE);
            }

        }
    }//GEN-LAST:event_btnRunFdActionPerformed

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
            java.util.logging.Logger.getLogger(AdminUploadData.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AdminUploadData.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AdminUploadData.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AdminUploadData.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AdminUploadData().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel BodyContainer;
    private javax.swing.JPanel body;
    private usu.widget.ButtonGlass btnBakupData2;
    private javax.swing.JButton btnBrowseFd;
    private usu.widget.ButtonGlass btnDownloadData2;
    private usu.widget.ButtonGlass btnEmployeeManagement2;
    private usu.widget.ButtonGlass btnHome2;
    private usu.widget.ButtonGlass btnLogOut2;
    private javax.swing.JButton btnRunDtEmployee;
    private javax.swing.JButton btnRunFd;
    private javax.swing.JButton btnRunInformation;
    private javax.swing.JButton btnSaveIn;
    private usu.widget.ButtonGlass btnUploadData2;
    private com.toedter.calendar.JDateChooser dtEndExFd;
    private com.toedter.calendar.JDateChooser dtEndUpload;
    private com.toedter.calendar.JDateChooser dtStartExFd;
    private com.toedter.calendar.JDateChooser dtStartUpload;
    private usu.widget.FileBrowser fileBrowser;
    private javax.swing.JLabel iconClient;
    private javax.swing.JLabel iconHarisma;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JProgressBar jProgressBarAttendanceOrRepairDb;
    private javax.swing.JProgressBar jProgressBarDtEmployeeOrBakup;
    private javax.swing.JProgressBar jProgressBarFd;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JLabel judulDepan;
    private javax.swing.JLabel labelDate;
    private javax.swing.JLabel labelDownloadDtEmployee;
    private javax.swing.JLabel labelDownloadInformation;
    private javax.swing.JLabel labelEndDateAttendance;
    private javax.swing.JLabel labelEndDateFd;
    private javax.swing.JLabel labelEndDateUpload;
    private javax.swing.JLabel labelGarisAja;
    private javax.swing.JLabel labelGarisKananLabel2;
    private javax.swing.JLabel labelGarisKiriButton2;
    private javax.swing.JLabel labelGarisTengahButton;
    private javax.swing.JLabel labelImportFlasdisk;
    private javax.swing.JLabel labelLogoDimata;
    private javax.swing.JLabel labelMenu;
    private javax.swing.JLabel labelNamaEmp;
    private javax.swing.JLabel labelPosition;
    private javax.swing.JLabel labelStartDateAttendance;
    private javax.swing.JLabel labelStartDateFd;
    private javax.swing.JLabel labelStartDateUpload;
    private javax.swing.JLabel labelTime;
    private javax.swing.JLabel lblProgress;
    private javax.swing.JLabel lblProgressFlasdisk;
    private javax.swing.JLabel lblProgressInformation;
    private javax.swing.JLabel msgFlasdisk;
    private javax.swing.JLabel msgUploadData;
    private javax.swing.JLabel msgUploadDataPresence;
    private usu.widget.Panel panelButtonMenu2;
    private javax.swing.JPanel panelHeader;
    private javax.swing.JTextField txtBakupBrowse;
    private com.toedter.calendar.JDateChooser txtDateEndInformation;
    private javax.swing.JTextField txtSaveExFd;
    private com.toedter.calendar.JDateChooser txtStartDateInformation;
    // End of variables declaration//GEN-END:variables

    /**
     * @return the menuUrl
     */
    public String getMenuUrl() {
        return menuUrl;
    }

    /**
     * @param menuUrl the menuUrl to set
     */
    public static void setMenuUrl(String menuUrls) {
        menuUrl = menuUrls;
    }
}
