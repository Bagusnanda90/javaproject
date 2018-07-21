/*
 * @author  	: artha
 * @version  	: 01 
 */
/*******************************************************************
 * Class Description 	: [project description ... ] 
 * Imput Parameters 	: [input parameter ...] 
 * Output 		: [output ...] 
 *******************************************************************/
package com.dimata.harisma.session.attendance;

import com.dimata.harisma.entity.attendance.MachineTransaction;
import com.dimata.harisma.entity.attendance.PstMachineTransaction;
import com.dimata.harisma.entity.canteen.CanteenVisitation;
import com.dimata.harisma.entity.canteen.PstCanteenVisitation;
import com.dimata.harisma.entity.employee.Employee;
import com.dimata.harisma.entity.employee.PstEmployee;
import com.dimata.harisma.session.log.SessSysLogger;
import com.dimata.harisma.utility.machine.SaverData;
import com.dimata.harisma.utility.machine.dcanteen.SaverDataCanteen;
import com.dimata.qdep.db.DBException;
import com.dimata.qdep.db.DBHandler;
import com.dimata.system.entity.PstSystemProperty;
import com.dimata.util.Formater;
import java.util.Date;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.Vector;
import com.dimata.qdep.db.DBResultSet;
import java.sql.*;
import com.dimata.harisma.entity.employee.*;
import com.dimata.harisma.utility.machine.WatcherMachine;

public class SessMachineTransaction {

    public static boolean CHECK_SWEEP_TIME = false;
    public static int IGNORE_TIME = 15 * 60 * 1000;          /* --- in milli seconds --- */


    /**
     * @desc Menyimpan transaction to machine
     * @param <DESC>param</DESC> data transaction
     * @return status boolean
     */
    public static boolean downloadTransaction(MachineTransaction transction) {
        boolean status = false;
        try {
            long oidTrans = PstMachineTransaction.insertExc(transction);
            System.out.print("-----------------------------:::" + oidTrans + ", " + transction.getCardId() + ", " + transction.getDateTransaction());
            if (oidTrans > 0) {
                status = true;
            }
        } catch (DBException ex) {
            ex.printStackTrace();
        }
        return status;
    }

    /**
     * @desc Menyimpan transaction to machine
     * @param <DESC>param</DESC> data transaction
     * @return status boolean
     */
    public static int analistPresent(Date date) {

        String strMachineAbsence = String.valueOf(PstSystemProperty.getValueByName("ABSEN_TMA_NO"));
        String strMachineCanteen = String.valueOf(PstSystemProperty.getValueByName("CANTEEN_TMA_NO"));
        CHECK_SWEEP_TIME = Integer.parseInt(PstSystemProperty.getValueByName("CANTEEN_CHECK_SWEEP_TIME")) > 0;
        int ignoreTime = Integer.parseInt(PstSystemProperty.getValueByName("CANTEEN_IGNORE_SWEEP_TIME")) * 60 * 1000;
        if (ignoreTime > 0) {
            IGNORE_TIME = ignoreTime;
        }

        Hashtable strMcNoAbsence = null;
        Hashtable strMcNoCanteen = null;

        StringTokenizer strTokenizerAb = new StringTokenizer(strMachineAbsence, ",");
        StringTokenizer strTokenizerCt = new StringTokenizer(strMachineCanteen, ",");

        while (strTokenizerAb.hasMoreTokens()) {
            strMcNoAbsence.put(strTokenizerAb.nextToken(), strTokenizerAb.nextToken());
        }

        while (strTokenizerCt.hasMoreTokens()) {
            strMcNoCanteen.put(strTokenizerCt.nextToken(), strTokenizerCt.nextToken());
        }

        int iValidData = 0;

        String whereClause = PstMachineTransaction.fieldNames[PstMachineTransaction.FLD_POSTED] + "=" + PstMachineTransaction.POSTED_STATUS_OPEN + " AND " + PstMachineTransaction.fieldNames[PstMachineTransaction.FLD_DATE_TRANS] + "=" + Formater.formatDate(date, "yyyy-MM-dd hh:mm:ss");
        Vector vTrans = new Vector(1, 1);
        vTrans = PstMachineTransaction.list(0, 0, whereClause, PstMachineTransaction.fieldNames[PstMachineTransaction.FLD_DATE_TRANS]);

        for (int i = 0; i < vTrans.size(); i++) {
            boolean isSaveData = false;
            boolean isCorectStation = true;
            MachineTransaction mcTran = new MachineTransaction();
            mcTran = (MachineTransaction) vTrans.get(i);
            //Jika no sesuai dengan no mesin absen
            if (mcTran.getStation().equals((String) strMcNoAbsence.get(mcTran.getStation()))) {
                try {
                    if (!SaverData.saveTransaction(mcTran)) {
                        isSaveData = false;
                    }
                } catch (DBException ex) {
                    isSaveData = false;
                }
            }//Jika no sesuai dengan no mesin canteen
            else if (mcTran.getStation().equals((String) strMcNoCanteen.get(mcTran.getStation()))) {
                isSaveData = true;
                boolean IGNORE = false;

                if (CHECK_SWEEP_TIME) {
                    Employee emp = new Employee();
                    emp = PstEmployee.getEmployeeByNum(mcTran.getCardId());
                    CanteenVisitation canteenVisitation = PstCanteenVisitation.getLatestVisitation(emp.getOID());
                    if (canteenVisitation == null) {
                        IGNORE = false;
                    } else {
                        Date lastEmpTransTime = canteenVisitation.getVisitationTime();
                        long transactionTime = mcTran.getDateTransaction().getTime();
                        long lastEmployeeVisitation = lastEmpTransTime.getTime();
                        long diff = transactionTime - lastEmployeeVisitation;
                        IGNORE = (diff <= IGNORE_TIME);
                        if (IGNORE) {
                            System.out.println("Visitation data with employeeOid = " + emp.getOID() + " is IGNORED ...");
                        }
                    }
                    if (!IGNORE) {
                        try {
                            if (!SaverDataCanteen.saveTransaction(mcTran)) {
                                isSaveData = false;
                            }
                        } catch (DBException ex) {
                            isSaveData = false;
                        }
                    } else {
                        isSaveData = false;
                    }
                } else {
                    try {
                        if (!SaverDataCanteen.saveTransaction(mcTran)) {
                            isSaveData = false;
                        }
                    } catch (DBException ex) {
                        isSaveData = false;
                    }
                }
            }

            if (isSaveData) {
                mcTran.setPosted(PstMachineTransaction.POSTED_STATUS_PROCESSED);
                iValidData += 1;
            } else {
                mcTran.setPosted(PstMachineTransaction.POSTED_STATUS_INVALID_DATA);
            }
            try {
                PstMachineTransaction.updateExc(mcTran);
            } catch (Exception ex) {
                System.out.println("[ERROR] SessMachineTransaction.analistPresent :::: " + ex.toString());
            }
        }
        return iValidData;
    }

    /**
     * @desc Menyimpan transaction to machine
     * @param <DESC>param</DESC> data transaction
     * @return status boolean
     */
    public static int analistPresentAll() {
            
            String strMachineAbsence = String.valueOf(PstSystemProperty.getValueByName("ABSEN_TMA_NO"));
            String strMachineCanteen = String.valueOf(PstSystemProperty.getValueByName("CANTEEN_TMA_NO"));
            CHECK_SWEEP_TIME = Integer.parseInt(PstSystemProperty.getValueByName("CANTEEN_CHECK_SWEEP_TIME")) > 0;
            int ignoreTime = Integer.parseInt(PstSystemProperty.getValueByName("CANTEEN_IGNORE_SWEEP_TIME")) * 60 * 1000;
            
            if (ignoreTime > 0) {
                IGNORE_TIME = ignoreTime;
            }

            Hashtable strMcNoAbsence = new Hashtable();
            Hashtable strMcNoCanteen = new Hashtable();

            StringTokenizer strTokenizerAb = new StringTokenizer(strMachineAbsence, ",");
            StringTokenizer strTokenizerCt = new StringTokenizer(strMachineCanteen, ",");

            while (strTokenizerAb.hasMoreTokens()) {
                String strAb = strTokenizerAb.nextToken();
                strMcNoAbsence.put(strAb, strAb);
            }

            while (strTokenizerCt.hasMoreTokens()) {
                String strAb = strTokenizerCt.nextToken();
                strMcNoCanteen.put(strAb, strAb);
            }

            int iValidData = 0;

            String whereClause = PstMachineTransaction.fieldNames[PstMachineTransaction.FLD_POSTED] + "=" + PstMachineTransaction.POSTED_STATUS_OPEN;
            Vector vTrans = new Vector(1, 1);
            vTrans = PstMachineTransaction.list(0, 0, whereClause, PstMachineTransaction.fieldNames[PstMachineTransaction.FLD_DATE_TRANS]);

            System.out.println("\n\n\n-------------- START ANALIZING DATA ---------------------");

            for (int i = 0; i < vTrans.size(); i++) {

                boolean isSaveData = false;
                
                MachineTransaction mcTran = new MachineTransaction();
                mcTran = (MachineTransaction) vTrans.get(i);
                //Jika no sesuai dengan no mesin absen
                System.out.println("[" + (i + 1) + "]Date :: " + Formater.formatDate(mcTran.getDateTransaction(), "yyyy-MM-dd hh:mm:ss") + " >>" + mcTran.getStation() + " | " + mcTran.getMode() + " | " + mcTran.getCardId());
                if (mcTran.getStation().equals((String) strMcNoAbsence.get(mcTran.getStation()))){
                    System.out.println("---------- Process at absence : " + mcTran.getCardId());
                    isSaveData = true;
                    try {
                        if (!SaverData.saveTransaction(mcTran)) {
                            isSaveData = false;
                        }
                    } catch (DBException ex) {
                        isSaveData = false;
                        System.out.println("[exception] "+ex);
                    }
                }//Jika no sesuai dengan no mesin canteen
                else if (mcTran.getStation().equals((String) strMcNoCanteen.get(mcTran.getStation()))) {
                    System.out.println("---------- Process at canteen : " + mcTran.getCardId());
                    isSaveData = true;
                    boolean IGNORE = false;

                    if (CHECK_SWEEP_TIME){
                        //Employee emp = new Employee();
                        long empOid = 0;
                        empOid = PstEmployee.getEmployeeByBarcode(mcTran.getCardId());
                        CanteenVisitation canteenVisitation = PstCanteenVisitation.getLatestVisitation(empOid);
                        if (canteenVisitation == null){
                            IGNORE = false;
                        } else {
                            Date lastEmpTransTime = canteenVisitation.getVisitationTime();
                            long transactionTime = mcTran.getDateTransaction().getTime();
                            long lastEmployeeVisitation = lastEmpTransTime.getTime();
                            long diff = Math.abs(transactionTime - lastEmployeeVisitation);
                            IGNORE = (diff <= IGNORE_TIME);
                            if (IGNORE) {
                                System.out.println("Visitation data with employeeOid = " + empOid + " is IGNORED ...");
                            }
                        }
                        if (!IGNORE) {
                            try {
                                if (!SaverDataCanteen.saveTransaction(mcTran)) {
                                    isSaveData = false;
                                }
                            } catch (DBException ex) {
                                isSaveData = false;
                            }
                        } else {
                            isSaveData = false;
                        }
                    } else {
                        try {
                            if (!SaverDataCanteen.saveTransaction(mcTran)) {
                                isSaveData = false;
                            }
                        } catch (DBException ex) {
                            isSaveData = false;
                        }
                    }
                } else {
                    System.out.println("---------- Not Process : " + mcTran.getCardId());
                }

                if (isSaveData) {
                    mcTran.setPosted(PstMachineTransaction.POSTED_STATUS_PROCESSED);
                    iValidData += 1;
                } else {
                    mcTran.setPosted(PstMachineTransaction.POSTED_STATUS_INVALID_DATA);
                    SessSysLogger.logWarning("TRANSACTION NOT VALID", "From machine:" + mcTran.getStation() + "; Emp Num:" + mcTran.getCardId() + "; Mode:" + mcTran.getMode() + "; Date:" + Formater.formatDate(mcTran.getDateTransaction(), "yyyy-MM-dd"));
                }
                try {
                    PstMachineTransaction.updateExc(mcTran);
                } catch (Exception ex) {
                    System.out.println("[ERROR] SessMachineTransaction.analistPresent :::: " + ex.toString());
                }
            }
            return iValidData;
        
    }

    public static void resetAllTransactions() {
        String query = " UPDATE " + PstMachineTransaction.TBL_HR_MACHINE_TRANS + " SET " + PstMachineTransaction.fieldNames[PstMachineTransaction.FLD_POSTED] + " = " + 0 + " WHERE " + PstMachineTransaction.fieldNames[PstMachineTransaction.FLD_POSTED] + " = -1";
        try {
            DBHandler.execUpdate(query);
        } catch (Exception ex) {
            System.out.println("[ERROR] SessMachineTransaction.resetAllTransactions :::: " + ex.toString());
        }
    }

    public static void updateStatusAbsence(String driver, String p_url, String p_user, String p_pass) {

        DBResultSet dbrs = null;
        Vector result = new Vector(1, 1);

        try {
            Class.forName(driver);
            System.out.println("Driver Ditemukan");
        } catch (ClassNotFoundException e) {
            javax.swing.JOptionPane.showMessageDialog(null, "Driver Tidak Ditemukan " + e.toString());
        }

        try {
            String url = p_url;
            String user = p_user;
            String pass = p_pass;
            Connection con = DriverManager.getConnection(url, user, pass);

            String sql = "Update absensi set status = 1 where status = 0";

            Statement stm = con.createStatement();

            stm.executeUpdate(sql);
            System.out.println("update status berhasil");
            stm.close();

        } catch (Exception e) {
            System.out.println("Exception " + e.toString());
        }

    }

    public static Vector takedatamachineTransaction(String driver, String p_url, String p_user, String p_pass) {

        //DBResultSet dbrs = null;

        try {
            Class.forName(driver);
            System.out.println("Driver Ditemukan");
        } catch (ClassNotFoundException e) {
            javax.swing.JOptionPane.showMessageDialog(null, "Driver Tidak Ditemukan " + e.toString());
        }

        try {
            String url = p_url;
            String user = p_user;
            String pass = p_pass;
            Connection con = DriverManager.getConnection(url, user, pass);

            String sql = "SELECT * FROM absensi where status = 0";
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery(sql);

            Vector resultAbs = new Vector();

            while (rs.next()) {

                Absensi abs = new Absensi();

                String p_trans_kode = rs.getString("trans_kode");
                String p_trans_pengenal = rs.getString("trans_pengenal");
                Date p_trans_tgl = rs.getDate("trans_tgl");
                Date p_trans_jam = rs.getTime("trans_jam");
                String p_trans_status = rs.getString("trans_status");
                String p_trans_mesin = rs.getString("trans_mesin");

                System.out.println("Data yang berhasil diambil :" + p_trans_kode + " " + p_trans_pengenal + " " + p_trans_tgl + " " + p_trans_jam + " " + p_trans_status + " " + p_trans_mesin);

                abs.setTrans_kode(p_trans_kode);
                abs.setTrans_tanggal(p_trans_tgl);
                abs.setTrans_jam(p_trans_jam);
                abs.setTrans_pengenal(p_trans_pengenal);
                abs.setTrans_status(p_trans_status);
                abs.setTrans_mesin(p_trans_mesin);

                resultAbs.add(abs);

            }

            rs.close();
            stm.close();
            con.close();

            return resultAbs;
        } catch (Exception e) {
            System.out.println("Exception " + e.toString());
        }

        return null;
    }

    public static int insertDataToMachineTransaction(Vector dataMachine) {

        try {
            for (int i = 0; i < dataMachine.size(); i++) {
                Absensi abs = (Absensi) dataMachine.get(i);

                String year = "" + abs.getTrans_tanggal();
                String yy = year.substring(0, 4);
                String mm = year.substring(5, 7);
                String dd = year.substring(8, 10);

                String time = "" + abs.getTrans_jam();
                String jj = time.substring(0, 2);
                String ii = time.substring(3, 5);

                MachineTransaction mt = new MachineTransaction();
                mt.setCardId(abs.getTrans_pengenal());
                mt.setMode(abs.getTrans_status());
                mt.setPosted(PstMachineTransaction.POSTED_STATUS_OPEN);
                mt.setDateTransactionDate(yy, mm, dd, jj, ii);

                mt.setStation(abs.getTrans_mesin());

                System.out.println("-----------------------------:::" + mt.getCardId() + ", " + mt.getDateTransaction());

                long oidTrans = PstMachineTransaction.insertExc(mt);

                System.out.println("-----------------------------:::" + oidTrans + ", " + mt.getCardId() + ", " + mt.getDateTransaction());

            }

            return 1;
        } catch (Exception e) {
            System.out.println("Exception :" + e.toString());
        }

        return 0;
    }

    public static Vector analisis() {
        String whereClause = PstMachineTransaction.fieldNames[PstMachineTransaction.FLD_POSTED] + "=" + PstMachineTransaction.POSTED_STATUS_OPEN;
        Vector vTrans = new Vector(1, 1);

        vTrans = PstMachineTransaction.list(0, 0, whereClause, PstMachineTransaction.fieldNames[PstMachineTransaction.FLD_DATE_TRANS]);

        System.out.println("Start Analizing Data .............");

        for (int i = 0; i < vTrans.size(); i++) {

            boolean isSaveData = false;
            boolean isCorectStation = true;
            MachineTransaction mcTran = new MachineTransaction();
            mcTran = (MachineTransaction) vTrans.get(i);

            //Jika no sesuai dengan no mesin absen
            System.out.println("[" + (i + 1) + "]Date :: " + Formater.formatDate(mcTran.getDateTransaction(), "yyyy-MM-dd hh:mm:ss") + " >>" + mcTran.getStation() + " | " + mcTran.getMode() + " | " + mcTran.getCardId());

            System.out.println("---------- Process at absence : " + mcTran.getCardId());

            isSaveData = true;
            try {
                if (!SaverData.saveTransaction(mcTran)) {
                    isSaveData = false;
                }
            } catch (DBException ex) {
                isSaveData = false;
            }
        }
        return null;
    }
}

