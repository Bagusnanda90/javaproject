/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.utility.harisma.machine;

import com.dimata.harisma.entity.attendance.SessEmpScheduleAttendance;
import com.dimata.harisma.session.aplikasidesktop.attendance.SessAplicationDestopAbsensiAttendance;
import com.dimata.harisma.session.aplikasidesktop.attendance.SessDestopApplication;
import com.dimata.harisma.utility.harisma.masterdatadesktop.entity.EmployeeDummyTransfer;
import com.dimata.harisma.utility.harisma.masterdatadesktop.entity.PstEmpScheduleDesktop;
import com.dimata.harisma.utility.harisma.masterdatadesktop.entity.PstEmployeeDummyTransfer;
import com.dimata.harisma.utility.harisma.masterdatadesktop.entity.PstPeriodDesktop;
import com.dimata.harisma.utility.harisma.masterdatadesktop.entity.PstScheduleSymbolDesktop;
import com.dimata.util.Formater;
import com.dimata.util.ImagesParser;
import com.dimata.util.JComboBoxItem;
import com.dimata.util.JtableDestop;
import java.awt.Color;
import java.util.Date;
import java.util.Vector;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Satrya Ramayu
 */
public class AdminEmployeeManagement extends javax.swing.JFrame {

    /**
     * Creates new form AdminHome
     */
    private SessDestopApplication obSessDestopApplication = new SessDestopApplication();
    private DefaultTableModel DftTabMode;
    JtableDestop jtableDestop = new JtableDestop();
    JComboBoxItem jComboBoxItem = new JComboBoxItem();
    Vector valuests = new Vector();
    Vector keySts = new Vector();
    public AdminEmployeeManagement() {
        Formater formater = new Formater();
        ImagesParser imagesParser = new ImagesParser();
        JtableDestop jtableDestop = new JtableDestop();
        initComponents();
        valuests.add("" + ActionDataManagementEmployee.STATUS_AKTIV);
        valuests.add("" + ActionDataManagementEmployee.STATUS_NON_AKTIV);
        valuests.add("" + ActionDataManagementEmployee.STATUS_TRANSFER);
        valuests.add("" + 3);

        keySts.add("Aktiv");
        keySts.add("Non-AKtiv");
        keySts.add("Transfer");
        keySts.add("All");
        this.setResizable(false);
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
        try {
            obSessDestopApplication = SessAplicationDestopAbsensiAttendance.getObSessDestopApplication();
        } catch (Exception exc) {
        }
        String namaEmp = (obSessDestopApplication.getNamaEmployee() != null && obSessDestopApplication.getNamaEmployee().length() > 0 ? "" + obSessDestopApplication.getNamaEmployee() : "-");
        labelNamaEmp.setText("<html><p> Name: <br>&nbsp;" + namaEmp + "</p></html>");
        labelPosition.setText("<html><p> Position: <br>&nbsp;" + (obSessDestopApplication.getPositionName() != null && obSessDestopApplication.getPositionName().length() > 0 ? "" + obSessDestopApplication.getPositionName() : "-") + "</p></html>");

        String nik = txtNik.getText();
        String name = txtName.getText();
        String position = txtPosition.getText();
        String schedule = txtSchedule.getText();
        
        
         jComboBoxItem.drawCombobox(cbxStatusEmp, ""+3, keySts, valuests);
         Object itemSts = cbxStatusEmp.getSelectedItem();
        String valueSts = ((JComboBoxItem) itemSts).getValue();
        int statusEmp = valueSts!=null && valueSts.length()>0 ?Integer.parseInt(valueSts):0;
        
        String getIdScheduleSymbol = PstScheduleSymbolDesktop.listScheduleIdSymbolAll();
        long periodId = PstPeriodDesktop.getPeriodIdBySelectedDate(new Date());
        Vector listEmployee = PstEmpScheduleDesktop.getDataEmployeeInDesktop(periodId, getIdScheduleSymbol, getIdScheduleSymbol, "", "", "", "", 3);
        Vector listEmployeeTrans = PstEmployeeDummyTransfer.getDataEmployeeInDesktop(nik, name, position, schedule, statusEmp);
        tblViewEmployee.setVisible(true);
        Object[] Baris = {"hidden id", "No", "Employee Number", "Nama Employee", "Status", "Position", "Schedule", "No Telp / HandPhone ", "Address / Address Permanent"};
        DftTabMode = new DefaultTableModel(null, Baris);
        tblViewEmployee.setModel(DftTabMode);
        
        tblViewEmployee.setEditingColumn(0);
        tblViewEmployee.setEnabled(true);//agar tidak bisa di edit
        int no = 0;
        if (listEmployee != null && listEmployee.size() > 0 || listEmployeeTrans != null && listEmployeeTrans.size() > 0) {

            for (int x = 0; x < listEmployee.size(); x++) {
                String fullName = "";
                String noTlp = "";
                String handpone = "";
                String phoneEmergensi = "";
                String alamat = "";
                String alamatPermanent = "";
                String employeeNumber = "";
                String schedules = "";
                String sPosition = "";
                 String sStsEmployee="";
                //Employee employee = (Employee)listEmployee.get(x);
                SessEmpScheduleAttendance sessEmpSchedule = (SessEmpScheduleAttendance) listEmployee.get(x);
                if (sessEmpSchedule != null) {
                    fullName = sessEmpSchedule.getFullName() != null && sessEmpSchedule.getFullName().length() > 0 ? sessEmpSchedule.getFullName() : "-";
                    noTlp = sessEmpSchedule.getPhone() != null && sessEmpSchedule.getPhone().length() > 0 ? sessEmpSchedule.getPhone() : "-";
                    handpone = sessEmpSchedule.getHandphone() != null && sessEmpSchedule.getHandphone().length() > 0 ? sessEmpSchedule.getHandphone() : "-";
                    phoneEmergensi = sessEmpSchedule.getPhoneEmg() != null && sessEmpSchedule.getPhoneEmg().length() > 0 ? sessEmpSchedule.getPhoneEmg() : "";
                    alamat = sessEmpSchedule.getAlamat() != null && sessEmpSchedule.getAlamat().length() > 0 ? sessEmpSchedule.getAlamat() : "-";
                    alamatPermanent = sessEmpSchedule.getAlamatPermanent() != null && sessEmpSchedule.getAlamatPermanent().length() > 0 ? sessEmpSchedule.getAlamatPermanent() : "";
                    employeeNumber = sessEmpSchedule.getEmployeeNum() != null && sessEmpSchedule.getEmployeeNum().length() > 0 ? sessEmpSchedule.getEmployeeNum() : "-";
                    schedules = sessEmpSchedule.getSymbol() != null && sessEmpSchedule.getSymbol().length() > 0 ? sessEmpSchedule.getSymbol() : "-";
                    sPosition = sessEmpSchedule.getPosition() != null && sessEmpSchedule.getPosition().length() > 0 ? sessEmpSchedule.getPosition() : "-";
                    sStsEmployee = ActionDataManagementEmployee.fieldNameStatus[sessEmpSchedule.getStatusEmpTrans()];
                }

                no = no + 1;
                String[] dataField = {""+sessEmpSchedule.getEmployeeId()+"_"+ActionDataManagementEmployee.STATUS_AKTIV, "" + no, employeeNumber,fullName,sStsEmployee,sPosition, "<html><B>" + schedules + " [" + sessEmpSchedule.getSschTimeIn() + "," + sessEmpSchedule.getSschTimeOut() + "]</B></html>", "<html><B>" + noTlp + " / " + handpone + (phoneEmergensi.length() > 0 ? "/" + phoneEmergensi : "") + "</B></html>", "<html>" + alamat + (alamatPermanent.length() > 0 ? " / " + alamatPermanent : "") + "</html>"};
                DftTabMode.addRow(dataField);

            }
            //nanti masukkan Vector listEmpTransfer

            jtableDestop.resizeColumnWidth(tblViewEmployee);
        } else {
            tblViewEmployee.setVisible(false);
            JOptionPane.showMessageDialog(null, "Employee Can't Find");
        }
        
        if (listEmployeeTrans != null && listEmployeeTrans.size() > 0) {
            for (int x = 0; x < listEmployeeTrans.size(); x++) {
                String fullName = "";
                String noTlp = "";
                String handpone = "";
                String phoneEmergensi = "";
                String alamat = "";
                String alamatPermanent = "";
                String employeeNumber = "";
                String schedules = "";
                String sPosition = "";
                String sStsEmployee="";
                //Employee employee = (Employee)listEmployee.get(x);
                 EmployeeDummyTransfer employeeDummyTransfer = (EmployeeDummyTransfer)listEmployeeTrans.get(x);
                if (employeeDummyTransfer != null) {
                    fullName = employeeDummyTransfer.getEmployeeFullName() != null && employeeDummyTransfer.getEmployeeFullName().length() > 0 ? employeeDummyTransfer.getEmployeeFullName() : "-";
                    noTlp = "-";
                    handpone = "-";
                    phoneEmergensi = "-";
                    alamat = "-";
                    alamatPermanent = "-";
                    employeeNumber = employeeDummyTransfer.getEmployeeNum()!=null && employeeDummyTransfer.getEmployeeNum().length()>0?employeeDummyTransfer.getEmployeeNum():"-";
                    schedules = employeeDummyTransfer.getScheduleName()!=null && employeeDummyTransfer.getScheduleName().length()>0 ?employeeDummyTransfer.getScheduleName():"-";
                    sPosition = employeeDummyTransfer.getPositionName()!=null && employeeDummyTransfer.getPositionName().length()>0 ?employeeDummyTransfer.getPositionName():"-";
                    sStsEmployee = ActionDataManagementEmployee.fieldNameStatus[employeeDummyTransfer.getStatusEmployee()];
                }

                no = no + 1;
                String[] dataField = {""+employeeDummyTransfer.getOID()+"_"+ActionDataManagementEmployee.STATUS_TRANSFER, "" + no, employeeNumber,fullName,sStsEmployee,sPosition, "<html><B>" + schedules + " [" + employeeDummyTransfer.getSchTimeIn()+ "," + employeeDummyTransfer.getSchTimeOut() + "]</B></html>", "<html><B>" + noTlp + " / " + handpone + (phoneEmergensi.length() > 0 ? "/" + phoneEmergensi : "") + "</B></html>", "<html>" + alamat + (alamatPermanent.length() > 0 ? " / " + alamatPermanent : "") + "</html>"};
                DftTabMode.addRow(dataField);
            }
        }
        BodyContainer.setBackground(SessAplicationDestopAbsensiAttendance.getColor() != null ? SessAplicationDestopAbsensiAttendance.getColor() : Color.WHITE);
        body.setBackground(SessAplicationDestopAbsensiAttendance.getColor() != null ? SessAplicationDestopAbsensiAttendance.getColor() : Color.WHITE);
        jPanelPembantu.setBackground(SessAplicationDestopAbsensiAttendance.getColor() != null ? SessAplicationDestopAbsensiAttendance.getColor() : Color.WHITE);
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

        BodyContainer = new javax.swing.JPanel();
        body = new javax.swing.JPanel();
        labelDate = new javax.swing.JLabel();
        labelTime = new javax.swing.JLabel();
        labelNamaEmp = new javax.swing.JLabel();
        labelPosition = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        panelReminder = new javax.swing.JPanel();
        labelSearchEmployee = new javax.swing.JLabel();
        labelNik = new javax.swing.JLabel();
        labelSearchEmployee2 = new javax.swing.JLabel();
        labelSearchEmployee3 = new javax.swing.JLabel();
        labelSearchEmployee4 = new javax.swing.JLabel();
        labelSearchEmployee5 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        labelTitik2Nik = new javax.swing.JLabel();
        labelTitik2Nik1 = new javax.swing.JLabel();
        labelTitik2Nik2 = new javax.swing.JLabel();
        labelTitik2Nik3 = new javax.swing.JLabel();
        labelTitik2Nik4 = new javax.swing.JLabel();
        cbxStatusEmp = new javax.swing.JComboBox();
        jSeparator4 = new javax.swing.JSeparator();
        btnSearch = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblViewEmployee = new javax.swing.JTable();
        txtNik = new usu.widget.glass.TextBoxGlass();
        txtName = new usu.widget.glass.TextBoxGlass();
        txtPosition = new usu.widget.glass.TextBoxGlass();
        txtSchedule = new usu.widget.glass.TextBoxGlass();
        jPanelPembantu = new javax.swing.JPanel();
        labelGarisTengahButton = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        panelButtonMenu = new usu.widget.Panel();
        labelGarisKiriButton = new javax.swing.JLabel();
        btnHome = new usu.widget.ButtonGlass();
        labelGarisKananLabel = new javax.swing.JLabel();
        btnDownloadData = new usu.widget.ButtonGlass();
        btnUploadData = new usu.widget.ButtonGlass();
        btnEmployeeManagement = new usu.widget.ButtonGlass();
        btnBakupData = new usu.widget.ButtonGlass();
        btnLogOut = new usu.widget.ButtonGlass();
        btnAdd = new javax.swing.JButton();
        panelHeader = new javax.swing.JPanel();
        iconHarisma = new javax.swing.JLabel();
        judulDepan = new javax.swing.JLabel();
        iconClient = new javax.swing.JLabel();
        labelLogoDimata = new javax.swing.JLabel();
        labelGarisAja = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        BodyContainer.setBackground(new java.awt.Color(255, 255, 255));
        BodyContainer.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        BodyContainer.setPreferredSize(new java.awt.Dimension(620, 610));

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

        panelReminder.setBackground(new java.awt.Color(255, 255, 255));

        labelSearchEmployee.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        labelSearchEmployee.setText("Search Employee :");

        labelNik.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        labelNik.setText("NIK ");

        labelSearchEmployee2.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        labelSearchEmployee2.setText("NAME ");

        labelSearchEmployee3.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        labelSearchEmployee3.setText("POSITION");

        labelSearchEmployee4.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        labelSearchEmployee4.setText("SCHEDULE");

        labelSearchEmployee5.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        labelSearchEmployee5.setText("STATUS");

        labelTitik2Nik.setFont(new java.awt.Font("Calibri", 0, 24)); // NOI18N
        labelTitik2Nik.setText(":");

        labelTitik2Nik1.setFont(new java.awt.Font("Calibri", 0, 24)); // NOI18N
        labelTitik2Nik1.setText(":");

        labelTitik2Nik2.setFont(new java.awt.Font("Calibri", 0, 24)); // NOI18N
        labelTitik2Nik2.setText(":");

        labelTitik2Nik3.setFont(new java.awt.Font("Calibri", 0, 24)); // NOI18N
        labelTitik2Nik3.setText(":");

        labelTitik2Nik4.setFont(new java.awt.Font("Calibri", 0, 24)); // NOI18N
        labelTitik2Nik4.setText(":");

        btnSearch.setText("Search");
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        tblViewEmployee.setModel(new javax.swing.table.DefaultTableModel(
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
        tblViewEmployee.setMaximumSize(new java.awt.Dimension(2147483647, 120));
        tblViewEmployee.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblViewEmployee);

        txtNik.setHorizontalAlignment(javax.swing.JTextField.LEFT);

        txtName.setHorizontalAlignment(javax.swing.JTextField.LEFT);

        txtPosition.setHorizontalAlignment(javax.swing.JTextField.LEFT);

        txtSchedule.setHorizontalAlignment(javax.swing.JTextField.LEFT);

        javax.swing.GroupLayout jPanelPembantuLayout = new javax.swing.GroupLayout(jPanelPembantu);
        jPanelPembantu.setLayout(jPanelPembantuLayout);
        jPanelPembantuLayout.setHorizontalGroup(
            jPanelPembantuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanelPembantuLayout.setVerticalGroup(
            jPanelPembantuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 21, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panelReminderLayout = new javax.swing.GroupLayout(panelReminder);
        panelReminder.setLayout(panelReminderLayout);
        panelReminderLayout.setHorizontalGroup(
            panelReminderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelReminderLayout.createSequentialGroup()
                .addComponent(labelSearchEmployee, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(524, 524, 524))
            .addComponent(jScrollPane1)
            .addComponent(jSeparator4, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(panelReminderLayout.createSequentialGroup()
                .addGroup(panelReminderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelNik, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelSearchEmployee5, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelReminderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(labelTitik2Nik4)
                    .addComponent(labelTitik2Nik))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelReminderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtNik, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cbxStatusEmp, 0, 134, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelReminderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelReminderLayout.createSequentialGroup()
                        .addComponent(labelSearchEmployee2)
                        .addGap(50, 50, 50)
                        .addComponent(labelTitik2Nik1, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(labelSearchEmployee4, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labelTitik2Nik3, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelReminderLayout.createSequentialGroup()
                        .addComponent(labelSearchEmployee3, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labelTitik2Nik2, javax.swing.GroupLayout.PREFERRED_SIZE, 6, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtPosition, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(87, 87, 87)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelReminderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanelPembantu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtSchedule, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(panelReminderLayout.createSequentialGroup()
                .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelReminderLayout.createSequentialGroup()
                .addComponent(jSeparator3)
                .addContainerGap())
        );
        panelReminderLayout.setVerticalGroup(
            panelReminderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelReminderLayout.createSequentialGroup()
                .addComponent(labelSearchEmployee, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(jSeparator3, javax.swing.GroupLayout.DEFAULT_SIZE, 1, Short.MAX_VALUE)
                .addGap(1, 1, 1)
                .addGroup(panelReminderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panelReminderLayout.createSequentialGroup()
                        .addGroup(panelReminderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(labelNik, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelTitik2Nik, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNik, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelSearchEmployee2, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelSearchEmployee4, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelTitik2Nik3, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtSchedule, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelTitik2Nik1, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(panelReminderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelSearchEmployee3, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtPosition, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(panelReminderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(labelSearchEmployee5, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(labelTitik2Nik4, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(cbxStatusEmp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(labelTitik2Nik2, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jPanelPembantu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1))
        );

        jSeparator2.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        panelButtonMenu.setBackground(new java.awt.Color(255, 255, 255));
        panelButtonMenu.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

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
                .addComponent(labelGarisKananLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                .addComponent(labelGarisKiriButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                .addGap(5, 5, 5))
        );

        btnAdd.setText("Add");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout bodyLayout = new javax.swing.GroupLayout(body);
        body.setLayout(bodyLayout);
        bodyLayout.setHorizontalGroup(
            bodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bodyLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(bodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(bodyLayout.createSequentialGroup()
                        .addComponent(btnAdd)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(labelGarisTengahButton)
                        .addGap(5, 5, 5))
                    .addGroup(bodyLayout.createSequentialGroup()
                        .addGroup(bodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, bodyLayout.createSequentialGroup()
                                .addComponent(labelNamaEmp, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(labelPosition, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(labelDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(labelTime, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(panelReminder, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(panelButtonMenu, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.LEADING))
                        .addGap(10, 10, 10))))
        );
        bodyLayout.setVerticalGroup(
            bodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bodyLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(bodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(labelPosition, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                    .addComponent(labelNamaEmp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSeparator1)
                    .addComponent(labelDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(labelTime, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(11, 11, 11)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 4, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelButtonMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelReminder, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addGroup(bodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelGarisTengahButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(bodyLayout.createSequentialGroup()
                        .addComponent(btnAdd)
                        .addGap(0, 4, Short.MAX_VALUE))))
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
                .addComponent(iconClient, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(judulDepan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(iconHarisma, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        panelHeaderLayout.setVerticalGroup(
            panelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelHeaderLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(iconHarisma, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(judulDepan, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
                    .addComponent(iconClient, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                    .addComponent(body, javax.swing.GroupLayout.DEFAULT_SIZE, 700, Short.MAX_VALUE)
                    .addGroup(BodyContainerLayout.createSequentialGroup()
                        .addComponent(labelGarisAja, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(2, 2, 2)
                        .addComponent(labelLogoDimata, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        BodyContainerLayout.setVerticalGroup(
            BodyContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BodyContainerLayout.createSequentialGroup()
                .addComponent(panelHeader, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(body, javax.swing.GroupLayout.DEFAULT_SIZE, 394, Short.MAX_VALUE)
                .addGap(3, 3, 3)
                .addGroup(BodyContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelGarisAja, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelLogoDimata, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1, 1, 1))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(BodyContainer, javax.swing.GroupLayout.DEFAULT_SIZE, 722, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(BodyContainer, javax.swing.GroupLayout.DEFAULT_SIZE, 532, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNameActionPerformed
    }//GEN-LAST:event_txtNameActionPerformed

    private void txtNikActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNikActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNikActionPerformed

    private void txtPositionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPositionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPositionActionPerformed

    private void txtScheduleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtScheduleActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtScheduleActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        // TODO add your handling code here:
        String nik = txtNik.getText();
        String name = txtName.getText();
        String position = txtPosition.getText();
        String schedule = txtSchedule.getText();
        Object itemSts = cbxStatusEmp.getSelectedItem();
        String valueSts = ((JComboBoxItem) itemSts).getValue();
        int statusEmp = valueSts!=null && valueSts.length()>0 ?Integer.parseInt(valueSts):0;
        Vector listEmployeeTrans = PstEmployeeDummyTransfer.getDataEmployeeInDesktop(nik, name, position, schedule, statusEmp);
         int tmbhBolehAbs = SessAplicationDestopAbsensiAttendance.getTambahanBolehAbs();
        String getIdScheduleSymbol = PstScheduleSymbolDesktop.listScheduleIdSymbol(tmbhBolehAbs);
        long periodId = PstPeriodDesktop.getPeriodIdBySelectedDate(new Date());
        Vector listEmployee = PstEmpScheduleDesktop.getDataEmployeeInDesktop(periodId, getIdScheduleSymbol, getIdScheduleSymbol, nik, name, position, schedule, statusEmp);
        
        tblViewEmployee.setVisible(true);
        Object[] Baris = {"hidden Id", "No", "Employee Number", "Nama Employee", "Status", "Position", "Schedule", "No Telp / HandPhone ", "Address / Address Permanent"};
        DftTabMode = new DefaultTableModel(null, Baris);
        tblViewEmployee.setModel(DftTabMode);
        tblViewEmployee.setEnabled(true);//agar tidak bisa di edit
         int no = 0;
        if (listEmployee != null && listEmployee.size() > 0 || listEmployeeTrans!=null && listEmployeeTrans.size()>0) {

           
            for (int x = 0; x < listEmployee.size(); x++) {
                String fullName = "";
                String noTlp = "";
                String handpone = "";
                String phoneEmergensi = "";
                String alamat = "";
                String alamatPermanent = "";
                String employeeNumber = "";
                String schedules = "";
                String sPosition = "";
                String sStsEmployee="";
                //Employee employee = (Employee)listEmployee.get(x);
                SessEmpScheduleAttendance sessEmpSchedule = (SessEmpScheduleAttendance) listEmployee.get(x);
                if (sessEmpSchedule != null) {
                    fullName = sessEmpSchedule.getFullName() != null && sessEmpSchedule.getFullName().length() > 0 ? sessEmpSchedule.getFullName() : "-";
                    noTlp = sessEmpSchedule.getPhone() != null && sessEmpSchedule.getPhone().length() > 0 ? sessEmpSchedule.getPhone() : "-";
                    handpone = sessEmpSchedule.getHandphone() != null && sessEmpSchedule.getHandphone().length() > 0 ? sessEmpSchedule.getHandphone() : "-";
                    phoneEmergensi = sessEmpSchedule.getPhoneEmg() != null && sessEmpSchedule.getPhoneEmg().length() > 0 ? sessEmpSchedule.getPhoneEmg() : "";
                    alamat = sessEmpSchedule.getAlamat() != null && sessEmpSchedule.getAlamat().length() > 0 ? sessEmpSchedule.getAlamat() : "-";
                    alamatPermanent = sessEmpSchedule.getAlamatPermanent() != null && sessEmpSchedule.getAlamatPermanent().length() > 0 ? sessEmpSchedule.getAlamatPermanent() : "";
                    employeeNumber = sessEmpSchedule.getEmployeeNum() != null && sessEmpSchedule.getEmployeeNum().length() > 0 ? sessEmpSchedule.getEmployeeNum() : "-";
                    schedules = sessEmpSchedule.getSymbol() != null && sessEmpSchedule.getSymbol().length() > 0 ? sessEmpSchedule.getSymbol() : "-";
                    sPosition = sessEmpSchedule.getPosition() != null && sessEmpSchedule.getPosition().length() > 0 ? sessEmpSchedule.getPosition() : "-";
                     sStsEmployee = ActionDataManagementEmployee.fieldNameStatus[sessEmpSchedule.getStatusEmpTrans()];
                }

                no = no + 1;
                String[] dataField = {"0", "" + no,employeeNumber,fullName,sStsEmployee,sPosition, "<html><B>" + schedules + " [" + sessEmpSchedule.getSschTimeIn() + "," + sessEmpSchedule.getSschTimeOut() + "]</B></html>", "<html><B>" + noTlp + " / " + handpone + (phoneEmergensi.length() > 0 ? "/" + phoneEmergensi : "") + "</B></html>", "<html>" + alamat + (alamatPermanent.length() > 0 ? " / " + alamatPermanent : "") + "</html>"};
                DftTabMode.addRow(dataField);
            }
           
        } else { 
            tblViewEmployee.setVisible(false);
            JOptionPane.showMessageDialog(null, "Employee Can't Find");
        }
        
        if (listEmployeeTrans != null && listEmployeeTrans.size() > 0) {
            for (int x = 0; x < listEmployeeTrans.size(); x++) {
                String fullName = "";
                String noTlp = "";
                String handpone = "";
                String phoneEmergensi = "";
                String alamat = "";
                String alamatPermanent = "";
                String employeeNumber = "";
                String schedules = "";
                String sPosition = "";
                String sStsEmployee="";
                //Employee employee = (Employee)listEmployee.get(x);
                 EmployeeDummyTransfer employeeDummyTransfer = (EmployeeDummyTransfer)listEmployeeTrans.get(x);
                if (employeeDummyTransfer != null) {
                    fullName = employeeDummyTransfer.getEmployeeFullName() != null && employeeDummyTransfer.getEmployeeFullName().length() > 0 ? employeeDummyTransfer.getEmployeeFullName() : "-";
                    noTlp = "-";
                    handpone = "-";
                    phoneEmergensi = "-";
                    alamat = "-";
                    alamatPermanent = "-";
                    employeeNumber = employeeDummyTransfer.getEmployeeNum()!=null && employeeDummyTransfer.getEmployeeNum().length()>0?employeeDummyTransfer.getEmployeeNum():"-";
                    schedules = employeeDummyTransfer.getScheduleName()!=null && employeeDummyTransfer.getScheduleName().length()>0 ?employeeDummyTransfer.getScheduleName():"-";
                    sPosition = employeeDummyTransfer.getPositionName()!=null && employeeDummyTransfer.getPositionName().length()>0 ?employeeDummyTransfer.getPositionName():"-";
                    sStsEmployee = ActionDataManagementEmployee.fieldNameStatus[employeeDummyTransfer.getStatusEmployee()];
                }

                no = no + 1;
                String[] dataField = {""+employeeDummyTransfer.getOID()+"_"+ActionDataManagementEmployee.STATUS_TRANSFER, "" + no,employeeNumber,fullName,sStsEmployee,sPosition, "<html><B>" + schedules + " [" + employeeDummyTransfer.getSchTimeIn()+ "," + employeeDummyTransfer.getSchTimeOut() + "]</B></html>", "<html><B>" + noTlp + " / " + handpone + (phoneEmergensi.length() > 0 ? "/" + phoneEmergensi : "") + "</B></html>", "<html>" + alamat + (alamatPermanent.length() > 0 ? " / " + alamatPermanent : "") + "</html>"};
                DftTabMode.addRow(dataField);
            }
        }
         jtableDestop.resizeColumnWidth(tblViewEmployee);
    }//GEN-LAST:event_btnSearchActionPerformed

    private void btnHomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHomeActionPerformed
        // TODO add your handling code here:
        this.dispose();
        new AdminHome().setVisible(true);
    }//GEN-LAST:event_btnHomeActionPerformed

    private void btnDownloadDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDownloadDataActionPerformed
        // TODO add your handling code here:
        this.dispose();
        //AdminDownloadBakupData.setMenuUrl("Download Data");
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

    public void klik_record() {
        //saat pilih record
        try {
            int baris = tblViewEmployee.getSelectedRow();
            String idEmpTrans = DftTabMode.getValueAt(baris, 0).toString();
            if(idEmpTrans!=null && idEmpTrans.length()>0){
                 String sOIdTrans[] = idEmpTrans.split("_");
                 if(sOIdTrans.length>1 && !sOIdTrans[1].equalsIgnoreCase("0")){
                     new ActionDataManagementEmployee(idEmpTrans).setVisible(true);
                 }
                
            }
            
        } catch (Exception exc) {
            System.out.println("exc" + exc);
        }
    }
    private void jTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableMouseClicked
        // TODO add your handling code here:
        klik_record();
    }//GEN-LAST:event_jTableMouseClicked

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        // TODO add your handling code here:
        new ActionDataManagementEmployee("0").setVisible(true);
    }//GEN-LAST:event_btnAddActionPerformed

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
            java.util.logging.Logger.getLogger(AdminEmployeeManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AdminEmployeeManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AdminEmployeeManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AdminEmployeeManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AdminEmployeeManagement().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel BodyContainer;
    private javax.swing.JPanel body;
    private javax.swing.JButton btnAdd;
    private usu.widget.ButtonGlass btnBakupData;
    private usu.widget.ButtonGlass btnDownloadData;
    private usu.widget.ButtonGlass btnEmployeeManagement;
    private usu.widget.ButtonGlass btnHome;
    private usu.widget.ButtonGlass btnLogOut;
    private javax.swing.JButton btnSearch;
    private usu.widget.ButtonGlass btnUploadData;
    private javax.swing.JComboBox cbxStatusEmp;
    private javax.swing.JLabel iconClient;
    private javax.swing.JLabel iconHarisma;
    private javax.swing.JPanel jPanelPembantu;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JLabel judulDepan;
    private javax.swing.JLabel labelDate;
    private javax.swing.JLabel labelGarisAja;
    private javax.swing.JLabel labelGarisKananLabel;
    private javax.swing.JLabel labelGarisKiriButton;
    private javax.swing.JLabel labelGarisTengahButton;
    private javax.swing.JLabel labelLogoDimata;
    private javax.swing.JLabel labelNamaEmp;
    private javax.swing.JLabel labelNik;
    private javax.swing.JLabel labelPosition;
    private javax.swing.JLabel labelSearchEmployee;
    private javax.swing.JLabel labelSearchEmployee2;
    private javax.swing.JLabel labelSearchEmployee3;
    private javax.swing.JLabel labelSearchEmployee4;
    private javax.swing.JLabel labelSearchEmployee5;
    private javax.swing.JLabel labelTime;
    private javax.swing.JLabel labelTitik2Nik;
    private javax.swing.JLabel labelTitik2Nik1;
    private javax.swing.JLabel labelTitik2Nik2;
    private javax.swing.JLabel labelTitik2Nik3;
    private javax.swing.JLabel labelTitik2Nik4;
    private usu.widget.Panel panelButtonMenu;
    private javax.swing.JPanel panelHeader;
    private javax.swing.JPanel panelReminder;
    private javax.swing.JTable tblViewEmployee;
    private usu.widget.glass.TextBoxGlass txtName;
    private usu.widget.glass.TextBoxGlass txtNik;
    private usu.widget.glass.TextBoxGlass txtPosition;
    private usu.widget.glass.TextBoxGlass txtSchedule;
    // End of variables declaration//GEN-END:variables
}
