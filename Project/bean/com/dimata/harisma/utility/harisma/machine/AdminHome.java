/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.utility.harisma.machine;

import com.dimata.harisma.session.aplikasidesktop.attendance.SessAplicationDestopAbsensiAttendance;
import com.dimata.harisma.session.aplikasidesktop.attendance.SessDestopApplication;
import com.dimata.harisma.utility.harisma.machine.transferdataemployee.PstCareerPathDestopTransfer;
import com.dimata.harisma.utility.harisma.machine.transferdataemployee.PstInformationHrdDesktop;
import com.dimata.harisma.utility.harisma.machine.transferdataemployee.TabelDataInformationTransfer;
import com.dimata.harisma.utility.harisma.masterdatadesktop.entity.KonfigurasiMasterOutletSetting;
import com.dimata.harisma.utility.harisma.masterdatadesktop.entity.PstKonfigurasiOutletSetting;
import com.dimata.util.Formater;
import com.dimata.util.ImagesParser;
import java.awt.Color;
import java.util.Date;
import java.util.Hashtable;
import java.util.Vector;
import javax.swing.JFrame;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

/**
 *
 * @author Dimata 007
 */
public class AdminHome extends javax.swing.JFrame {

    /**
     * Creates new form AdminHome
     */
    private SessDestopApplication obSessDestopApplication = new SessDestopApplication();
    Hashtable hashExpiredContrack = new Hashtable();

    public AdminHome() {
        Formater formater = new Formater();
        ImagesParser imagesParser = new ImagesParser();
        initComponents();
        this.setResizable(false);
        Date dtParam = new Date();
        String where = "whn." + PstCareerPathDestopTransfer.fieldNames[PstCareerPathDestopTransfer.FLD_WORK_TO] + " BETWEEN \"" + Formater.formatDate(new Date(dtParam.getYear(), dtParam.getMonth() - 4, dtParam.getDate()), "yyyy-MM-dd") + "\" AND \"" + Formater.formatDate(new Date(dtParam.getYear(), dtParam.getMonth() + 5, dtParam.getDate()), "yyyy-MM-dd") + "\"";
        hashExpiredContrack = PstCareerPathDestopTransfer.hashExpiredWorkingDays(0, 0, where, PstCareerPathDestopTransfer.fieldNames[PstCareerPathDestopTransfer.FLD_WORK_TO] + " DESC ");
        bodyContainer.setBackground(SessAplicationDestopAbsensiAttendance.getColor() != null ? SessAplicationDestopAbsensiAttendance.getColor() : Color.WHITE);
        body.setBackground(SessAplicationDestopAbsensiAttendance.getColor() != null ? SessAplicationDestopAbsensiAttendance.getColor() : Color.WHITE);
        btnHome.setIcon(new javax.swing.ImageIcon(SessAplicationDestopAbsensiAttendance.getBtnHome())); // NOI18N
        btnDownloadData.setIcon(new javax.swing.ImageIcon(SessAplicationDestopAbsensiAttendance.getBtnDownloadData())); // NOI18N
        btnUploadData.setIcon(new javax.swing.ImageIcon(SessAplicationDestopAbsensiAttendance.getBtnUploadData())); // NOI18N
        btnEmployeeManagement.setIcon(new javax.swing.ImageIcon(SessAplicationDestopAbsensiAttendance.getBtnEmployeeManagement())); // NOI18N
        btnBakupData.setIcon(new javax.swing.ImageIcon(SessAplicationDestopAbsensiAttendance.getBtnBakupData())); // NOI18N
        btnLogOut.setIcon(new javax.swing.ImageIcon(SessAplicationDestopAbsensiAttendance.getBtnLogOut())); // NOI18N
        imagesParser.tampilkanImage(iconHarisma, iconClient, SessAplicationDestopAbsensiAttendance.getUrlGambarHarisma(), SessAplicationDestopAbsensiAttendance.getUrlGambarClient());
        imagesParser.tampilkanImage(labelLogoDimata, SessAplicationDestopAbsensiAttendance.getLogoDimata());
        labelDate.setText("");
        labelTime.setText("");
        formater.getTimeInJavaDesktop(labelTime, labelDate);
        obSessDestopApplication = new SessDestopApplication();
        String reminder = "-";
        try {
            obSessDestopApplication = SessAplicationDestopAbsensiAttendance.getObSessDestopApplication();
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
            System.out.println("exc desktop "+exc);
        }
        labelTextInformation.setVisible(true);
        labelTextInformation.setText("<html><p>" + reminder + "</p></html>");

        String infoContrakExpired = "";
        if (hashExpiredContrack != null && hashExpiredContrack.size() > 0 && hashExpiredContrack.containsKey(obSessDestopApplication.getEmpNumber())) {
            try {
                infoContrakExpired = (String) hashExpiredContrack.get(obSessDestopApplication.getEmpNumber());
                if (infoContrakExpired != null && infoContrakExpired.length() > 0) {
                    infoContrakExpired = " Masa Kerja Anda Sesuai Kontrak  " + infoContrakExpired + " silakan menghubungi Kepala Divisi untuk memperpanjang Kontrak kerja";
                }
            } catch (Exception exc) {
            }
        }
        labelTextRemider.setText("<html><p>" + infoContrakExpired + "</p></html>");
        String namaEmp = (obSessDestopApplication.getNamaEmployee() != null && obSessDestopApplication.getNamaEmployee().length() > 0 ? "" + obSessDestopApplication.getNamaEmployee() : "-");
        labelNamaEmp.setText("<html><p> Name: <br>&nbsp;" + namaEmp + "</p></html>");
        labelPosition.setText("<html><p> Position: <br>&nbsp;" + (obSessDestopApplication.getPositionName() != null && obSessDestopApplication.getPositionName().length() > 0 ? "" + obSessDestopApplication.getPositionName() : "-") + "</p></html>");

        setLocationRelativeTo(this);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bodyContainer = new javax.swing.JPanel();
        iconHarisma = new javax.swing.JLabel();
        judulDepan = new javax.swing.JLabel();
        iconClient = new javax.swing.JLabel();
        body = new javax.swing.JPanel();
        labelDate = new javax.swing.JLabel();
        labelTime = new javax.swing.JLabel();
        labelNamaEmp = new javax.swing.JLabel();
        labelPosition = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        panelReminder = new javax.swing.JPanel();
        labelReminder = new javax.swing.JLabel();
        jScrollPaneRemider = new javax.swing.JScrollPane();
        labelTextRemider = new javax.swing.JLabel();
        labelInformation = new javax.swing.JLabel();
        labelTextInformation = new javax.swing.JLabel();
        labelGarisTengahButton = new javax.swing.JLabel();
        panelButtonMenu = new usu.widget.Panel();
        labelGarisKiriButton = new javax.swing.JLabel();
        btnHome = new usu.widget.ButtonGlass();
        labelGarisKananLabel = new javax.swing.JLabel();
        btnDownloadData = new usu.widget.ButtonGlass();
        btnUploadData = new usu.widget.ButtonGlass();
        btnEmployeeManagement = new usu.widget.ButtonGlass();
        btnBakupData = new usu.widget.ButtonGlass();
        btnLogOut = new usu.widget.ButtonGlass();
        jSeparator3 = new javax.swing.JSeparator();
        labelGarisAja = new javax.swing.JLabel();
        labelLogoDimata = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        bodyContainer.setBackground(new java.awt.Color(255, 255, 255));
        bodyContainer.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        iconHarisma.setBackground(new java.awt.Color(255, 255, 255));
        iconHarisma.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        iconHarisma.setText("icon_harisma");
        iconHarisma.setToolTipText("");
        iconHarisma.setName("iconHarisma"); // NOI18N
        iconHarisma.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                iconHarismaMouseClicked(evt);
            }
        });

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
        labelNamaEmp.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        labelNamaEmp.setText("jLabel1");
        labelNamaEmp.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        labelNamaEmp.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        labelPosition.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        labelPosition.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        labelPosition.setText("jLabel1");
        labelPosition.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        labelPosition.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jSeparator1.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jSeparator2.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        labelReminder.setFont(new java.awt.Font("Calibri", 0, 24)); // NOI18N
        labelReminder.setText("Remider :");
        labelReminder.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        labelTextRemider.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        labelTextRemider.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jScrollPaneRemider.setViewportView(labelTextRemider);

        labelInformation.setFont(new java.awt.Font("Calibri", 0, 24)); // NOI18N
        labelInformation.setText("Information :");
        labelInformation.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        labelTextInformation.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        labelTextInformation.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        labelTextInformation.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout panelReminderLayout = new javax.swing.GroupLayout(panelReminder);
        panelReminder.setLayout(panelReminderLayout);
        panelReminderLayout.setHorizontalGroup(
            panelReminderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelReminderLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelReminderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPaneRemider)
                    .addGroup(panelReminderLayout.createSequentialGroup()
                        .addGroup(panelReminderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelReminder, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelInformation, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(labelTextInformation, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        panelReminderLayout.setVerticalGroup(
            panelReminderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelReminderLayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(labelReminder, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPaneRemider)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelInformation, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelTextInformation, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        panelButtonMenu.setBackground(new java.awt.Color(255, 255, 255));
        panelButtonMenu.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        btnHome.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnHome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHomeActionPerformed(evt);
            }
        });

        btnDownloadData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDownloadDataActionPerformed(evt);
            }
        });

        btnUploadData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUploadDataActionPerformed(evt);
            }
        });

        btnEmployeeManagement.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEmployeeManagementActionPerformed(evt);
            }
        });

        btnBakupData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBakupDataActionPerformed(evt);
            }
        });

        btnLogOut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogOutActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelButtonMenuLayout = new javax.swing.GroupLayout(panelButtonMenu);
        panelButtonMenu.setLayout(panelButtonMenuLayout);
        panelButtonMenuLayout.setHorizontalGroup(
            panelButtonMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelButtonMenuLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelGarisKananLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 7, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(btnHome, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(btnDownloadData, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(btnUploadData, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(btnEmployeeManagement, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(btnBakupData, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(btnLogOut, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(labelGarisKiriButton, javax.swing.GroupLayout.DEFAULT_SIZE, 7, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelButtonMenuLayout.setVerticalGroup(
            panelButtonMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelButtonMenuLayout.createSequentialGroup()
                .addGroup(panelButtonMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelGarisKananLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(labelGarisKiriButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(50, 50, 50))
            .addGroup(panelButtonMenuLayout.createSequentialGroup()
                .addGroup(panelButtonMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnHome, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDownloadData, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUploadData, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEmployeeManagement, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBakupData, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(panelButtonMenuLayout.createSequentialGroup()
                .addComponent(btnLogOut, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jSeparator3.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        javax.swing.GroupLayout bodyLayout = new javax.swing.GroupLayout(body);
        body.setLayout(bodyLayout);
        bodyLayout.setHorizontalGroup(
            bodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, bodyLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(bodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSeparator3)
                    .addComponent(panelReminder, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, bodyLayout.createSequentialGroup()
                        .addComponent(labelNamaEmp, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labelPosition, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labelDate, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labelTime, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, bodyLayout.createSequentialGroup()
                        .addGroup(bodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(panelButtonMenu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jSeparator2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labelGarisTengahButton)))
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
                .addGroup(bodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(bodyLayout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(labelGarisTengahButton, javax.swing.GroupLayout.DEFAULT_SIZE, 113, Short.MAX_VALUE)
                        .addGap(301, 301, 301))
                    .addGroup(bodyLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panelButtonMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 8, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panelReminder, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );

        javax.swing.GroupLayout bodyContainerLayout = new javax.swing.GroupLayout(bodyContainer);
        bodyContainer.setLayout(bodyContainerLayout);
        bodyContainerLayout.setHorizontalGroup(
            bodyContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bodyContainerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(bodyContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(bodyContainerLayout.createSequentialGroup()
                        .addComponent(iconClient, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(judulDepan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(iconHarisma, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(body, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 718, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, bodyContainerLayout.createSequentialGroup()
                        .addComponent(labelGarisAja, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(2, 2, 2)
                        .addComponent(labelLogoDimata, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        bodyContainerLayout.setVerticalGroup(
            bodyContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bodyContainerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(bodyContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(iconHarisma, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(iconClient, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(judulDepan, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(body, javax.swing.GroupLayout.DEFAULT_SIZE, 487, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(bodyContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelGarisAja, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelLogoDimata, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(bodyContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bodyContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void iconHarismaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_iconHarismaMouseClicked
        // TODO add your handling code here:
        if (SessAplicationDestopAbsensiAttendance.getUserNameLogin() != null && SessAplicationDestopAbsensiAttendance.getUserNameLogin().length() > 0 && SessAplicationDestopAbsensiAttendance.getUserNameLogin().equalsIgnoreCase("Administrator")) {
            long oidKonfiguration = PstKonfigurasiOutletSetting.getOid("", 0, 1);
            KonfigurasiMasterOutletSetting konfigurasiMasterOutletSetting = new KonfigurasiMasterOutletSetting();
            if (oidKonfiguration != 0) {
                try {
                    konfigurasiMasterOutletSetting = PstKonfigurasiOutletSetting.fetchExc(oidKonfiguration);
                } catch (Exception e) {
                }
            }
            new KonfigurationSetting(konfigurasiMasterOutletSetting).setVisible(true);
        }
    }//GEN-LAST:event_iconHarismaMouseClicked

    private void btnHomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHomeActionPerformed
        // TODO add your handling code here:
        this.dispose();
        new AdminHome().setVisible(true);
    }//GEN-LAST:event_btnHomeActionPerformed

    private void btnDownloadDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDownloadDataActionPerformed
        // TODO add your handling code here:
        this.dispose();
        // AdminUploadData.setMenuUrl("Download Data");
        new AdminDownloadData().setVisible(true);
    }//GEN-LAST:event_btnDownloadDataActionPerformed

    private void btnUploadDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUploadDataActionPerformed
        // TODO add your handling code here:
        this.dispose();
        AdminUploadData.setMenuUrl("Upload Data");
        new AdminUploadData().setVisible(true);
    }//GEN-LAST:event_btnUploadDataActionPerformed

    private void btnEmployeeManagementActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEmployeeManagementActionPerformed
        // TODO add your handling code here:
        this.dispose();
        new AdminEmployeeManagement().setVisible(true);
    }//GEN-LAST:event_btnEmployeeManagementActionPerformed

    private void btnBakupDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBakupDataActionPerformed
        // TODO add your handling code here:
        this.dispose();
        AdminUploadData.setMenuUrl("Bakup Data");
        new AdminUploadData().setVisible(true);
    }//GEN-LAST:event_btnBakupDataActionPerformed

    private void btnLogOutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogOutActionPerformed
        // TODO add your handling code here:
        this.dispose();
        new Login().setVisible(true);
    }//GEN-LAST:event_btnLogOutActionPerformed

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
            java.util.logging.Logger.getLogger(AdminHome.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AdminHome.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AdminHome.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AdminHome.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AdminHome().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel body;
    private javax.swing.JPanel bodyContainer;
    private usu.widget.ButtonGlass btnBakupData;
    private usu.widget.ButtonGlass btnDownloadData;
    private usu.widget.ButtonGlass btnEmployeeManagement;
    private usu.widget.ButtonGlass btnHome;
    private usu.widget.ButtonGlass btnLogOut;
    private usu.widget.ButtonGlass btnUploadData;
    private javax.swing.JLabel iconClient;
    private javax.swing.JLabel iconHarisma;
    private javax.swing.JScrollPane jScrollPaneRemider;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JLabel judulDepan;
    private javax.swing.JLabel labelDate;
    private javax.swing.JLabel labelGarisAja;
    private javax.swing.JLabel labelGarisKananLabel;
    private javax.swing.JLabel labelGarisKiriButton;
    private javax.swing.JLabel labelGarisTengahButton;
    private javax.swing.JLabel labelInformation;
    private javax.swing.JLabel labelLogoDimata;
    private javax.swing.JLabel labelNamaEmp;
    private javax.swing.JLabel labelPosition;
    private javax.swing.JLabel labelReminder;
    private javax.swing.JLabel labelTextInformation;
    private javax.swing.JLabel labelTextRemider;
    private javax.swing.JLabel labelTime;
    private usu.widget.Panel panelButtonMenu;
    private javax.swing.JPanel panelReminder;
    // End of variables declaration//GEN-END:variables
}
