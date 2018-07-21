/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.utility.harisma.machine;

/**
 *
 * @author Dimata 007
 */
import com.dimata.harisma.entity.attendance.MachineTransaction;
import com.dimata.harisma.entity.attendance.PstMachineTransaction;
import com.dimata.harisma.session.aplikasidesktop.attendance.SessAplicationDestopAbsensiAttendance;
import com.dimata.harisma.session.aplikasidesktop.attendance.SessDestopApplication;
import com.dimata.harisma.utility.harisma.machine.db.DBException;
import com.dimata.harisma.utility.harisma.machine.transferdataemployee.PstCareerPathDestopTransfer;
import com.dimata.harisma.utility.harisma.machine.transferdataemployee.PstEmployeeDesktopTransfer;
import com.dimata.harisma.utility.harisma.machine.transferdataemployee.PstMachineTransferDesktop;
import com.dimata.harisma.utility.harisma.machine.transferdataemployee.TabelMachineTransaction;
import com.dimata.harisma.utility.harisma.masterdatadesktop.entity.PstEmployeeDummyTransfer;
import com.dimata.harisma.utility.harisma.masterdatadesktop.entity.PstMachineTransactionDesktop;
import com.dimata.util.Formater;
import com.dimata.util.ImagesParser;
import java.awt.Color;
import java.util.Date;
import java.util.Hashtable;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Absensi extends javax.swing.JFrame {

    /**
     * Creates new form Absensi
     */
    private SessDestopApplication obSessDestopApplication = new SessDestopApplication();
    public static final int BUTTON_IN = 0;
    public static final int BUTTON_OUT = 1;
    private int nilaiBtn = 0;
    Hashtable hashExpiredContrack = new Hashtable();

    public Absensi() {
        initComponents();
        this.setResizable(false);
        Date dtParam = new Date();
        String where = "whn." + PstCareerPathDestopTransfer.fieldNames[PstCareerPathDestopTransfer.FLD_WORK_TO] + " BETWEEN \"" + Formater.formatDate(new Date(dtParam.getYear(), dtParam.getMonth() - 4, dtParam.getDate()), "yyyy-MM-dd") + "\" AND \"" + Formater.formatDate(new Date(dtParam.getYear(), dtParam.getMonth() + 5, dtParam.getDate()), "yyyy-MM-dd") + "\"";
        hashExpiredContrack = PstCareerPathDestopTransfer.hashExpiredWorkingDays(0, 0, where, PstCareerPathDestopTransfer.fieldNames[PstCareerPathDestopTransfer.FLD_WORK_TO] + " DESC ");
        bodyContainer.setBackground(SessAplicationDestopAbsensiAttendance.getColor() != null ? SessAplicationDestopAbsensiAttendance.getColor() : Color.WHITE);
        jPanelIsi.setBackground(SessAplicationDestopAbsensiAttendance.getColor() != null ? SessAplicationDestopAbsensiAttendance.getColor() : Color.WHITE);
        Formater formater = new Formater();
        ImagesParser imagesParser = new ImagesParser();
        //initComponents();

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
        labelNamaEmp.setText("<html><p>" + namaEmp + "</p></html>");
        labelPosition.setText("<html><p>" + (obSessDestopApplication.getPositionName() != null && obSessDestopApplication.getPositionName().length() > 0 ? "" + obSessDestopApplication.getPositionName() : "-") + "</p></html>");
        if (obSessDestopApplication.getKetSymbol() != null && obSessDestopApplication.getKetSymbol().indexOf("-") < 0) {
            String schedule = obSessDestopApplication.getSymbol() + " " + obSessDestopApplication.getSchTimeIn() + " " + obSessDestopApplication.getSchTimeOut() + " [" + obSessDestopApplication.getSchBreakOut() + " " + obSessDestopApplication.getSchBreakIn() + "] ";
            labelSchedule.setText("<html><p>" + schedule + "</p></html>");
        } else {
            if (obSessDestopApplication.getKetSymbol() != null && obSessDestopApplication.getSymbol() != null) {
                labelSchedule.setText("<html><p>" + obSessDestopApplication.getSymbol() + " " + obSessDestopApplication.getKetSymbol() + "</p></html>");
            } else {
                labelSchedule.setText("");
            }
        }
        labelStationMachine.setText("<html><p>" + (SessAplicationDestopAbsensiAttendance.getNoStation() != null && SessAplicationDestopAbsensiAttendance.getNoStation().length() > 0 ? SessAplicationDestopAbsensiAttendance.getNoStation() : "-") + "</p></html>");
        labelMessageAbsence.setVisible(false);
        panelRemider.setVisible(true);
        labelTxtRemider.setVisible(true);
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
        labelTxtRemider.setText("<html><p>" + infoContrakExpired + "</p></html>");
//        btnDone.setVisible(false);
//        jSeparator2.setVisible(false);
//        panelButtonYesOrNo.setVisible(false);
        setLocationRelativeTo(this);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        //this.setResizable(false);
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
        jPanelIsi = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        labelNamaEmp = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        labelPosition = new javax.swing.JLabel();
        labelDate = new javax.swing.JLabel();
        labelTime = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        nameSchedulelabel = new javax.swing.JLabel();
        labelSchedule = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        labelStationMachine = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        btnIn = new javax.swing.JButton();
        btnOut = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JSeparator();
        labelMessageAbsence = new javax.swing.JLabel();
        panelRemider = new javax.swing.JPanel();
        labelRemider = new javax.swing.JLabel();
        labelTxtRemider = new javax.swing.JLabel();
        iconHarisma = new javax.swing.JLabel();
        iconClient = new javax.swing.JLabel();
        judulDepan = new javax.swing.JLabel();
        labelLogoDimata = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        bodyContainer.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jPanelIsi.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        labelNamaEmp.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        labelNamaEmp.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        labelNamaEmp.setText("Nama");
        labelNamaEmp.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setText("Name :");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelNamaEmp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelNamaEmp)
                .addGap(23, 23, 23))
        );

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setText("Position :");

        labelPosition.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        labelPosition.setText("Position");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelPosition, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelPosition)
                .addGap(51, 51, 51))
        );

        labelDate.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        labelDate.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelDate.setText("date");
        labelDate.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        labelTime.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        labelTime.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelTime.setText("time");
        labelTime.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        nameSchedulelabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        nameSchedulelabel.setText("Schedule :");

        labelSchedule.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        labelSchedule.setText("schedule");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(nameSchedulelabel, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelSchedule, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nameSchedulelabel)
                    .addComponent(labelSchedule))
                .addGap(5, 5, 5))
        );

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        labelStationMachine.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        labelStationMachine.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        labelStationMachine.setText("stsMachine");
        labelStationMachine.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setText("Station Machine :");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(labelStationMachine, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(labelStationMachine))
                .addGap(5, 5, 5))
        );

        btnIn.setFont(new java.awt.Font("Calibri", 0, 36)); // NOI18N
        btnIn.setText("IN");
        btnIn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnIn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInActionPerformed(evt);
            }
        });

        btnOut.setFont(new java.awt.Font("Calibri", 0, 36)); // NOI18N
        btnOut.setText("Out");
        btnOut.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnOut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOutActionPerformed(evt);
            }
        });

        labelMessageAbsence.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        labelMessageAbsence.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelMessageAbsence.setText("Absensi Diterima");
        labelMessageAbsence.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        labelMessageAbsence.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        panelRemider.setBackground(new java.awt.Color(255, 255, 255));
        panelRemider.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        labelRemider.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        labelRemider.setText("REMINDER :");

        labelTxtRemider.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        labelTxtRemider.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelTxtRemider.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout panelRemiderLayout = new javax.swing.GroupLayout(panelRemider);
        panelRemider.setLayout(panelRemiderLayout);
        panelRemiderLayout.setHorizontalGroup(
            panelRemiderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRemiderLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelRemiderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelTxtRemider, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panelRemiderLayout.createSequentialGroup()
                        .addComponent(labelRemider)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panelRemiderLayout.setVerticalGroup(
            panelRemiderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRemiderLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(labelRemider)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(labelTxtRemider, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanelIsiLayout = new javax.swing.GroupLayout(jPanelIsi);
        jPanelIsi.setLayout(jPanelIsiLayout);
        jPanelIsiLayout.setHorizontalGroup(
            jPanelIsiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelIsiLayout.createSequentialGroup()
                .addContainerGap(179, Short.MAX_VALUE)
                .addComponent(btnIn, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnOut, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(179, Short.MAX_VALUE))
            .addGroup(jPanelIsiLayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanelIsiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelIsiLayout.createSequentialGroup()
                        .addComponent(labelMessageAbsence, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(5, 5, 5))
                    .addGroup(jPanelIsiLayout.createSequentialGroup()
                        .addGroup(jPanelIsiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelIsiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelIsiLayout.createSequentialGroup()
                                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanelIsiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(labelTime, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(labelDate, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(5, 5, 5))
                    .addGroup(jPanelIsiLayout.createSequentialGroup()
                        .addComponent(jSeparator3)
                        .addContainerGap())))
            .addGroup(jPanelIsiLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelRemider, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanelIsiLayout.setVerticalGroup(
            jPanelIsiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelIsiLayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanelIsiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanelIsiLayout.createSequentialGroup()
                        .addComponent(labelDate, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(labelTime, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelIsiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelIsiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelIsiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnIn, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnOut, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(labelMessageAbsence)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelRemider, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        iconHarisma.setBackground(new java.awt.Color(255, 255, 255));
        iconHarisma.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        iconHarisma.setText("icon_harisma");
        iconHarisma.setToolTipText("");
        iconHarisma.setName("iconHarisma"); // NOI18N

        iconClient.setBackground(new java.awt.Color(255, 255, 255));
        iconClient.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        iconClient.setText("icon_client");
        iconClient.setToolTipText("");
        iconClient.setName("iconClient"); // NOI18N

        judulDepan.setBackground(new java.awt.Color(255, 255, 255));
        judulDepan.setFont(new java.awt.Font("Vani", 1, 18)); // NOI18N
        judulDepan.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        judulDepan.setText("Outlet Attendance System");
        judulDepan.setToolTipText("Outlet Attendance System");
        judulDepan.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        judulDepan.setName("judul"); // NOI18N

        labelLogoDimata.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        jLabel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        javax.swing.GroupLayout bodyContainerLayout = new javax.swing.GroupLayout(bodyContainer);
        bodyContainer.setLayout(bodyContainerLayout);
        bodyContainerLayout.setHorizontalGroup(
            bodyContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bodyContainerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(bodyContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(bodyContainerLayout.createSequentialGroup()
                        .addComponent(iconClient, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(judulDepan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(iconHarisma, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanelIsi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, bodyContainerLayout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labelLogoDimata, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        bodyContainerLayout.setVerticalGroup(
            bodyContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bodyContainerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(bodyContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(iconHarisma, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(judulDepan, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE)
                    .addComponent(iconClient, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelIsi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(bodyContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelLogoDimata, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bodyContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(bodyContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnInActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInActionPerformed
        // TODO add your handling code here:
        if (btnIn.getText().equalsIgnoreCase("Yes")) {
            //sementara di bwt true, tujuannya untuk pengecekan apakah karyawan tsb memang ada di outlet yg sama
            btnIn.setText("IN");
            if (true) {
                MachineTransaction machineTransaction = new MachineTransaction();
                machineTransaction.setCardId(obSessDestopApplication != null ? obSessDestopApplication.getBarcodeNumber() : "");//nama empNumber
                Date dtTrans = Formater.reFormatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
                machineTransaction.setDateTransaction(dtTrans);//tgl absence
                machineTransaction.setMode("" + this.getNilaiBtn());//mode in out
                machineTransaction.setStation("" + SessAplicationDestopAbsensiAttendance.getNoStation());//station
                //machineTransaction.setPosted(1);
                long oid = 0;
                try {
                    if (machineTransaction.getDateTransaction() != null) {
                        oid = PstMachineTransactionDesktop.insertExc(machineTransaction);
                    }
                } catch (DBException ex) {
                    System.out.println("Exc insenrt Machine Trans" + ex);
                }
                
                //jalankan auto download priska20150628
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
                            //update status posted 20150507 priska
                //priska menambahkan fungsi untuk auto upload ketika ok
                TabelMachineTransaction tabelMachineTransaction = new TabelMachineTransaction();
                tabelMachineTransaction.setCardId(machineTransaction.getCardId());
                tabelMachineTransaction.setDateTransaction(machineTransaction.getDateTransaction());
                tabelMachineTransaction.setMode(machineTransaction.getMode());
                tabelMachineTransaction.setNote(machineTransaction.getNote());
                tabelMachineTransaction.setPosted(machineTransaction.getPosted());
                tabelMachineTransaction.setStation(machineTransaction.getStation());
                tabelMachineTransaction.setVerify(machineTransaction.getVerify());
                tabelMachineTransaction.setMachineTransId(machineTransaction.getOID());

                
                
                        try {
                            long oidServer = PstMachineTransaction.insertTransfer(tabelMachineTransaction);
                            
                            //update status posted 20150507 priska
                            if (oidServer > 0){
                                String oidS= String.valueOf(oid);
                                PstMachineTransferDesktop.updateStatusPosted(oidS);
                           
                            }
                            } catch (Exception exc) {
                           // this.setMessage("Exception transfer Employee  Outlet" + exc);
                        }
                }
                //end untuk 
                if (oid != 0) {
                    panelRemider.setVisible(true);
                    labelMessageAbsence.setVisible(true);
                    //update
                    if(this.getNilaiBtn()==BUTTON_IN){
                        PstEmployeeDummyTransfer.updateTimeIN(new Date(),machineTransaction.getCardId());
                    }else if(this.getNilaiBtn()==BUTTON_OUT){
                        PstEmployeeDummyTransfer.updateTimeOut(new Date(),machineTransaction.getCardId());
                    }
                    
                    //panelButtonYesOrNo.setVisible(false);
                    //                    btnYes.setVisible(false);
                    //                    btnNo.setVisible(false);
                    //                    btnDone.setVisible(true);
                    labelTxtRemider.setVisible(true);
                    String infoContrakExpired = "";
                    if (hashExpiredContrack != null && hashExpiredContrack.size() > 0 && hashExpiredContrack.containsKey(obSessDestopApplication.getEmpNumber())) {
                        try {
                            infoContrakExpired = (String) hashExpiredContrack.get(obSessDestopApplication.getEmpNumber());
                            if (infoContrakExpired != null && infoContrakExpired.length() > 0) {
                                infoContrakExpired = " Masa Kerja Anda Sesuai Kontrak tinggal " + infoContrakExpired + " silakan menghubungi Kepala Divisi untuk memperpanjang Kontrak kerja";
                            }
                        } catch (Exception exc) {
                        }
                    }
                    labelTxtRemider.setText("<html><p>" + infoContrakExpired + "</p></html>");
                    //JOptionPane.showMessageDialog(null, "<html><p>"+infoContrakExpired+" </p></html>");
                    //formater.getTimeLoginError(jOptionPane, this);
                    this.dispose();
                    new Login().setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "<html><p>Gagal Absence</p></html>");
                    this.dispose();
                    new Login().setVisible(true);
                }
            }
        }
        if (btnIn.getText().equalsIgnoreCase("IN")) {
            panelRemider.setVisible(true);
            labelRemider.setVisible(false);
            labelTxtRemider.setText("<html><p>Apakah Anda yakin untuk absen IN ? Schedule Anda " + obSessDestopApplication.getSymbol() + " . Seharusnya IN jam " + (obSessDestopApplication.getSchTimeIn()!=null?obSessDestopApplication.getSchTimeIn():"-") + "</p></html>");
            labelTxtRemider.setFont(new java.awt.Font("Arial Black", 1, 15));
            setNilaiBtn(BUTTON_IN);
            btnIn.setText("Yes");
            btnOut.setText("No");
        }
    }//GEN-LAST:event_btnInActionPerformed

    private void btnOutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOutActionPerformed

        if (btnOut.getText().equalsIgnoreCase("No")) {
            btnOut.setText("Out");
            //sementara di bwt true, tujuannya untuk pengecekan apakah karyawan tsb memang ada di outlet yg sama
            panelRemider.setVisible(false);
            labelTxtRemider.setVisible(false);
            JOptionPane.showMessageDialog(null, "<html><p>Hubungi Team Leader apabila ada kekeliruan jadwal.</p></html>");
            this.dispose();
            new Login().setVisible(true);
        }
        if (btnOut.getText().equalsIgnoreCase("Out")) {
            panelRemider.setVisible(true);
            labelRemider.setVisible(false);
            labelTxtRemider.setText("<html><p>Apakah Anda yakin untuk absen Out ? Schedule Anda " + obSessDestopApplication.getSymbol() + " . Seharusnya Out jam " + (obSessDestopApplication.getSchTimeOut()!=null ? obSessDestopApplication.getSchTimeOut() : "-") + "</p></html>");
            labelTxtRemider.setFont(new java.awt.Font("Arial Black", 1, 15));
            setNilaiBtn(BUTTON_OUT);
            btnIn.setText("Yes");
            btnOut.setText("No");
        }
    }//GEN-LAST:event_btnOutActionPerformed

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
            java.util.logging.Logger.getLogger(Absensi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Absensi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Absensi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Absensi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Absensi().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel bodyContainer;
    private javax.swing.JButton btnIn;
    private javax.swing.JButton btnOut;
    private javax.swing.JLabel iconClient;
    private javax.swing.JLabel iconHarisma;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanelIsi;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JLabel judulDepan;
    private javax.swing.JLabel labelDate;
    private javax.swing.JLabel labelLogoDimata;
    private javax.swing.JLabel labelMessageAbsence;
    private javax.swing.JLabel labelNamaEmp;
    private javax.swing.JLabel labelPosition;
    private javax.swing.JLabel labelRemider;
    private javax.swing.JLabel labelSchedule;
    private javax.swing.JLabel labelStationMachine;
    private javax.swing.JLabel labelTime;
    private javax.swing.JLabel labelTxtRemider;
    private javax.swing.JLabel nameSchedulelabel;
    private javax.swing.JPanel panelRemider;
    // End of variables declaration//GEN-END:variables

    /**
     * @return the nilaiBtn
     */
    public int getNilaiBtn() {
        return nilaiBtn;
    }

    /**
     * @param nilaiBtn the nilaiBtn to set
     */
    public void setNilaiBtn(int nilaiBtn) {
        this.nilaiBtn = nilaiBtn;
    }
}
