
package com.dimata.harisma.utility.machine;

import com.dimata.harisma.entity.attendance.MachineTransaction;
import com.dimata.harisma.entity.attendance.PstMachineTransaction;
import com.dimata.harisma.entity.employee.Absensi;
import com.dimata.harisma.entity.employee.Employee;
import com.dimata.harisma.entity.employee.PstEmployee;
import com.dimata.harisma.entity.masterdata.PstPeriod;
import com.dimata.harisma.session.attendance.SessMachineTransaction;
import com.dimata.harisma.utility.odbc.TransferPresenceFromMdbTidex;
import java.util.*;
import java.sql.*;
import com.dimata.system.entity.PstSystemProperty;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.Vector;

public class WatcherMachine implements Runnable {

    int i = 0;
    String machineNumber = "01";
    public static final String station_access = "04";
    public static final String mode_access = "0";
    String[] machineNumbers;
    public static final int STATUS_NOT_PROCES = 0;
    public static final int STATUS_PROCES = 1;
    /* configurasi time mode database access */
    public static final String TIME_IN = "A";
    public static final String TIME_OUT = "B";
    public static final String TIME_TUGAS = "C";
    private String MachineFnSpot = "";
    private String useNoMachine = "no";
    private static String db_access = "";
    /*GEDE_20110831_01*/ private  int transferData = 0;
    private String errMsg="";
    private int er=0;
    private  int totalData = 0;
    private boolean running = false; 
    private long sleepTimeByRecord=10;/*}*/


    public WatcherMachine() {
        try {
            MachineFnSpot = PstSystemProperty.getValueByName("MACHINE_FN_SPOT");
        } catch (Exception e) {
            MachineFnSpot = "";
            System.out.println("EXCEPTION SYS PROP MACHINE_FN_SPOT NOT EXIST :: " + e.toString());
        }

        try {
            useNoMachine = PstSystemProperty.getValueByName("MACHINE_NO_IN_OUT");
        } catch (Exception E) {
            System.out.println("Exception " + E.toString());
            useNoMachine = "no";
        }

        try {
            db_access = PstSystemProperty.getValueByName("DATA_BASE_FN");
        } catch (Exception e) {
            db_access = "";
            System.out.println("Exception " + e.toString());
        }


    }

    public void run() {
        setRunning(true);
        System.out.println(".:: [Watcher Machine] started ....................");
        machineNumber = String.valueOf(PstSystemProperty.getValueByName("ABSEN_TMA_NO"));
        StringTokenizer strTokenizer = new StringTokenizer(machineNumber, ",");
        machineNumbers = new String[strTokenizer.countTokens()];
        int count = 0;

        while (strTokenizer.hasMoreTokens()) {
            machineNumbers[count] = strTokenizer.nextToken();
            System.out.println("ABSEN MACHINE :::::::::: " + machineNumbers[count]);
            count++;
        }
        long TIMEKEEPING_INTERVAL = Long.parseLong(PstSystemProperty.getValueByName("TIMEKEEPING_INTERVAL"));
        while (this.isRunning()) {
            try {
                // proses download data from machine
                process();
                // sleeping time for next process
                //long TIMEKEEPING_INTERVAL = Long.parseLong(PstSystemProperty.getValueByName("TIMEKEEPING_INTERVAL"));
                int sleepTime = (int) (TIMEKEEPING_INTERVAL * 60 * 1000);
                System.out.println(".:: [Watcher Machine] finished, service will sleep for " + TIMEKEEPING_INTERVAL + " minutes\r");
                Thread.sleep(sleepTime);
            } catch (Exception e) {
                System.out.println("\t[Watcher Machine] interrupted with message : " + e);
            }
        }
    }

    public static String getBarcodeNo(String userid) {

        String db_access = "";

        try {
            db_access = PstSystemProperty.getValueByName("DATA_BASE_FN");
        } catch (Exception e) {
            System.out.println("[exception] " + e.toString());
            System.out.println("EXCEPTION SYS PROP DATA_BASE_FN NOT YET SET");
        }

        Connection cn = null;
        Statement stm = null;

        try {

            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            String cs = "jdbc:odbc:" + db_access;
            cn = DriverManager.getConnection(cs);

            String qry = "SELECT Badgenumber FROM USERINFO WHERE USERID = " + userid;

            stm = cn.createStatement();
            ResultSet rs = stm.executeQuery(qry);

            while (rs.next()) {

                String barcodeNomor = rs.getString(1);

                return barcodeNomor;

            }

            rs.close();

        } catch (Exception e) {
            System.out.println("EXCEPTION " + e.toString());
        } finally {
            try {
                stm.close();
                cn.close();
            } catch (Exception e) {
                System.out.println("EXCEPTION " + e.toString());
            }
        }
        return null;
    }

    public  String getNoMachine(String SensorId) {

        String db_access = "";

        try {
            db_access = PstSystemProperty.getValueByName("DATA_BASE_FN");
        } catch (Exception e) {
            System.out.println("Exception " + e.toString());
        }

        Connection cn = null;
        Statement stm = null;

        try {
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            String cs = "jdbc:odbc:" + db_access;
            cn = DriverManager.getConnection(cs);

            String qry = "SELECT " + Att2000.Fld_MachineAlias + " FROM " + Att2000.Tbl_Machines
                    + " WHERE " + Att2000.Fld_MachineNumber + " = " + SensorId;

            stm = cn.createStatement();
            ResultSet rs = stm.executeQuery(qry);

            while (rs.next()) {

                String machineAlias = rs.getString(1);

                return machineAlias;

            }

            rs.close();

        } catch (Exception e) {
            System.out.println("EXCEPTION " + e.toString());
        } finally {
            try {
                stm.close();
                cn.close();
            } catch (Exception e) {
                System.out.println("EXCEPTION " + e.toString());
            }
        }

        return null;

    }

    public  String getMachineAlias(String machineNumber) {

        String db_access = "";

        try {
            db_access = PstSystemProperty.getValueByName("DATA_BASE_FN");
        } catch (Exception e) {
            System.out.println("Exception " + e.toString());
        }

        Connection cn = null;
        Statement stm = null;

        try {

            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            String cs = "jdbc:odbc:" + db_access;
            cn = DriverManager.getConnection(cs);

            String qry = "SELECT " + Att2000.Fld_MachineNumber + " FROM " + Att2000.Tbl_Machines
                    + " WHERE " + Att2000.Fld_MachineAlias + " = '" + machineNumber + "'";

            stm = cn.createStatement();
            ResultSet rs = stm.executeQuery(qry);

            while (rs.next()) {

                String mchNumber = rs.getString(1);
                return mchNumber;

            }

            rs.close();

        } catch (Exception e) {
            System.out.println("EXCEPTION " + e.toString());
        } finally {
            try {
                stm.close();
                cn.close();
            } catch (Exception e) {
                System.out.println("EXCEPTION " + e.toString());
            }
        }

        return null;

    }

    public  boolean updateODBC(String userid, String checktime) {
        String db_access = "";

        try {
            db_access = PstSystemProperty.getValueByName("DATA_BASE_FN");
        } catch (Exception e) {
            System.out.println("Exception " + e.toString());
        }

        Connection cn = null;
        Statement stmt = null;

        try {

            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            String cs = "jdbc:odbc:" + db_access;
            cn = DriverManager.getConnection(cs);

            String qry = "UPDATE CHECKINOUT SET STATUS='" + STATUS_PROCES + "' WHERE USERID=" + userid + " AND CHECKTIME= #" + checktime + "#";

            stmt = cn.createStatement();
            stmt.executeUpdate(qry);
         
            return true;

        } catch (Exception e) {
            System.out.println("EXCEPTION " + e.toString());
        } finally {
            try {
                stmt.close();
                cn.close();
            } catch (Exception e) {
                System.out.println("EXCEPTION " + e.toString());
            }
        }

        return false;
    }

    private  Date getMachineDate(String str_time) throws Exception {

        Date date = new Date();
        if (str_time.length() > 0) {

            int tYear = Integer.parseInt(str_time.substring(0, 4)) - 1900;
            int tMonth = Integer.parseInt(str_time.substring(5, 7)) - 1;
            int tDate = Integer.parseInt(str_time.substring(8, 10));
            int tHour = Integer.parseInt(str_time.substring(11, 13));
            int tMin = Integer.parseInt(str_time.substring(14, 16));
            return new Date(tYear, tMonth, tDate, tHour, tMin);

        }
        return date;
    }

    /**
     * @Desc    : Untuk memproses data finger spot
     * */
    public  Vector TransactionAccess() {
int er=0;
        /* String db_access = "";

        try {
        db_access = PstSystemProperty.getValueByName("DATA_BASE_FN");
        } catch (Exception e) {
        db_access = "";
        System.out.println("Exception " + e.toString());
        } */

        Connection cn = null;
        Statement stm = null;

        /* mengecek keberadaan database */
        if (db_access != null && !db_access.equals("")) {

            try {

                /* Configurasi database access */
                Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
                String cs = "jdbc:odbc:" + db_access;
                cn = DriverManager.getConnection(cs);

                String sql = "SELECT " + Att2000.Fld_InOut_USERID + ","
                        + Att2000.Fld_InOut_CHECKTIME + ","
                        + Att2000.Fld_InOut_CHECKTYPE + ","
                        + Att2000.Fld_InOut_VERIFYCODE + ","
                        + Att2000.Fld_InOut_SENSORID + ","
                        + Att2000.Fld_InOut_WorkCode + ","
                        + Att2000.Fld_InOut_sn
                        + " FROM " + Att2000.Tbl_CheckInOut + " WHERE "
                        + Att2000.Fld_InOut_STATUS + " IS NULL OR " + Att2000.Fld_InOut_STATUS + " = '" + Att2000.Status_Not_Transfered + "'";

                System.out.println("Sql ==== " + sql.toString());
                stm = cn.createStatement();
                ResultSet rs = stm.executeQuery(sql);

                System.out.println(" --------------------------------------------------------------------");
                System.out.println(" ------------------- DOWNLOAD DATA FINGGERSPOT ----------------------");
                System.out.println(" --------------------------------------------------------------------");

                int no = 1;
              /*GEDE_20110831_01{*/  int iTotalData= getRecordTotal();
                if(iTotalData>0){
                    totalData =  iTotalData;
                    transferData=0;
                }
                while (iTotalData>0 && rs.next()) {
                    Thread.sleep(getSleepTimeByRecord());  /* }*/
                    String userId = rs.getString(Att2000.Fld_InOut_USERID);

                    String str_time = rs.getString(Att2000.Fld_InOut_CHECKTIME);

                    String CHECKTYPE = rs.getString(Att2000.Fld_InOut_CHECKTYPE);

                    String idMesin = rs.getString(Att2000.Fld_InOut_SENSORID);

                    Date time = getMachineDate(str_time);

                    String barcodeNomor = getBarcodeNo(userId);

                    /* untuk megecek keberadaan employee */

                    String whereClause = PstEmployee.fieldNames[PstEmployee.FLD_BARCODE_NUMBER] + "='" + barcodeNomor + "' AND "
                            + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + " = " + PstEmployee.NO_RESIGN;

                    Vector EmpMatch = PstEmployee.list(0, 0, whereClause, null);

                    /* jika employee ada */

                    if (EmpMatch != null && EmpMatch.size() > 0) {

                        Employee employee = new Employee();

                        employee = (Employee) EmpMatch.get(0);

                        int idxSwap = str_time.indexOf(" ");

                        String date = str_time.substring(0, idxSwap);

                        /* untuk mengecek keberadaan periode */
                        long periodId = PstPeriod.getPeriodIdBySelectedDateString(date);

                        if (periodId != 0) {

                            String mode = Att2000.mode_in;  /* default */

                            if (CHECKTYPE.equals(Att2000.CheckType_CheckIn)) {
                                mode = Att2000.mode_in;
                            } else if (CHECKTYPE.equals(Att2000.CheckType_CheckOut)) {
                                mode = Att2000.mode_out;
                            } else {
                                mode = Att2000.mode_in;
                            }

                            String station = "0";
                            station = getNoMachine(idMesin);

                            if (station.equals(null)) {
                                station = "0";
                            }

                            MachineTransaction machineTransaction = new MachineTransaction();
                            machineTransaction.setStation(station);
                            machineTransaction.setCardId(employee.getBarcodeNumber());
                            machineTransaction.setMode(mode);
                            machineTransaction.setDateTransaction(time);
                            machineTransaction.setPosted(PstMachineTransaction.POSTED_STATUS_OPEN);

                            long machine_id = 0;

                            try {
                                machine_id = PstMachineTransaction.insertExc(machineTransaction);
       /* GEDE_20110831_01 {*/    if(machine_id!=0){
                                   transferData += 1;
            //  System.out.println(sum);
                           /* } */}

                            } catch (Exception e) {
                                System.out.println("Exception " + e.toString());
                            }

                            if (machine_id != 0) {

                                updateODBC(userId, str_time);
                                System.out.println(" < SUKSES >" + no + " USER ID >>>> " + barcodeNomor + " TIME >>>> " + time + " \n");
                                no++;
                          
                            } else {

                                System.out.println(" < FAILD >" + no + " USER ID >>>> " + barcodeNomor + " TIME >>>> " + time + " \n");

                            }
                        }
                    }
 else{
 errMsg="no matching employee data for machine userId="+userId + " employee barcode=" +barcodeNomor+ " TIME=" +time;
 er++;
 System.out.println(    getErrMsg());
 }
                }

                rs.close();

                System.out.println(" --------------------------------------------------------------------");
                System.out.println(" --------------------------- END DOWNLOAD ---------------------------");
                System.out.println(" --------------------------------------------------------------------");


            } catch (Exception e) {
                System.out.println("Exception " + e.toString());
            } finally {
                try {
                    stm.close();
                    cn.close();
                } catch (Exception e) {
                    System.out.println("Exception " + e.toString());
                }
            }

        }
        return null;
    }

    /**
     * @Author  : Roy Andika
     * @Descr   : Untuk melakukan process dari mesin absensi dan canteen
     */
    public void process() {

        /*=============== UNTUK MESIN FINGER SPOT ===================== */

        //String MachineFnSpot = "";

        /*try {
        MachineFnSpot = PstSystemProperty.getValueByName("MACHINE_FN_SPOT");
        } catch (Exception e) {
        MachineFnSpot = "";
        System.out.println("EXCEPTION SYS PROP MACHINE_FN_SPOT NOT EXIST :: "+e.toString());
        }*/

        /* Pengecekan untuk Database Finger Spot ( Exist or not )*/
        if (this.MachineFnSpot != null && !this.MachineFnSpot.equals("") && this.MachineFnSpot.equals("ok")) {

            /* Menggunakan no machine untuk mengecek in dan out */
            /*String useNoMachine = "no";

            try{
            useNoMachine = PstSystemProperty.getValueByName("MACHINE_NO_IN_OUT");
            }catch(Exception E){
            System.out.println("Exception "+E.toString());
            useNoMachine = "no";
            }
             */
            if (this.useNoMachine != null && !this.useNoMachine.equals("") && this.useNoMachine.equals("ok")) { // menggunakan no mesin untuk mendeteksi in dan out

                TransactionAccessByNoMachine();

            } else {  /* Deafult menggunakan check type sebagai indikator dari in dan out */

                TransactionAccess();

            }

            SessMachineTransaction.analistPresentAll();

        }

        /*================= END MESIN FINGER SPOT ===================== */


        /*================= UNTUK MESIN TIDEX DATABASE ACCESS ===================== */

        String TDX_CARD_ON = "";

        /* Pengecekan untuk mesin tidex card (Exist or not)*/
        try {
            TDX_CARD_ON = PstSystemProperty.getValueByName("TDX_CARD_ON");
        } catch (Exception E) {
            TDX_CARD_ON = "";
            System.out.println("SYSTEM PROPERTY TIDEX_CARD_ON NOT SET");
            System.out.println("MACHINE TIDEX CARD NOT EXIST");
        }

        if (!TDX_CARD_ON.equals("") && TDX_CARD_ON.equals("ok")) {

            System.out.println("Take data transaction");
            takeDataMachineTransaction();

            SessMachineTransaction.analistPresentAll();

        }

        /*=============== END MESIN TIDEX DATABASE ACCESS ===================== */

        /*----------------------- Mesin Tidex -----------------------------*/

        String MachineTidex = "";

        try {
            MachineTidex = PstSystemProperty.getValueByName("MACHINE_TDX_TMA");
        } catch (Exception e) {
            MachineTidex = "";
            System.out.println("EXCEPTION SYS PROP MACHINE_TDX_TMA NOT EXIST");
        }

        if (!MachineTidex.equals("") && MachineTidex.equals("ok")) {

            System.out.println("\r   -> [Watcher Machine] process #" + i + " @ " + new Date().toLocaleString());
            i++;

            // Watcher machine
            System.out.println("-----------------------------------------------------------");

            for (int ic = 0; ic < machineNumbers.length; ic++) {
                String msg = "";
                try {
                    Vector vData = new Vector(1, 1);
                    I_Machine i_Machine = MachineBroker.getMachineByNumber(machineNumbers[ic]);
                    System.out.println("+++++" + machineNumbers[ic]);

                    if (i_Machine.processCheckMachine()) {

                        vData = i_Machine.processDownloadTransaction();

                        Vector vTransaction = new Vector(1, 1);
                        String strInvalidData = "";
                        int iInvalisData = 0;
                        try {
                            vTransaction = (Vector) vData.get(0);
                            strInvalidData = (String) vData.get(1);
                            iInvalisData = Integer.parseInt(strInvalidData);
                        } catch (Exception ex) {
                            System.out.println("Exc " + ex.toString());
                        }
                        msg = (vTransaction.size() + iInvalisData) + " transaction(s) downloaded from Machine-" + i_Machine.getMachineNumber();
                        if (vTransaction.size() > 0) {
                            msg += "-->  Data transactions download     : " + String.valueOf(vTransaction.size());
                            msg += "-->  Not Valid data download : " + strInvalidData;
                        }
                    } else {
                        msg = "[ERROR]  Unable to download from Machine-" + i_Machine.getMachineNumber() + "";
                    }
                } catch (Exception ex) {
                    msg = "[ERROR]  Unable to download from Machine-" + machineNumbers[ic] + ":: Cek koneksi ke mesin!";
                    ex.printStackTrace();
                }
                System.out.println(msg);
            }
        }

        /*------------------------ END TIDEX----------------------------- */
    }

    /**
     * @Author  Roy Andika
     * @Desc    UNTUK MENNGAMBIL DATA TRANSAKSI DARI MESIN TIDEX DATABASE ACCESS
     * @return
     */
    public  void takeDataMachineTransaction() {

        String dbTidexAccess = "";

        try {
            dbTidexAccess = PstSystemProperty.getValueByName("DATA_BASE_MDB_TDX");
        } catch (Exception e) {
            dbTidexAccess = "";
            System.out.println("EXCEPTION SYS PROP DATA_BASE_TIDEX_ACCESS NOT YET SET");
        }

        Connection cn = null;
        Statement stm = null;

        try {

            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            String cs = "jdbc:odbc:" + dbTidexAccess;
            cn = DriverManager.getConnection(cs);

            String sql = "SELECT " + TransferPresenceFromMdbTidex.CardID + ","
                    + TransferPresenceFromMdbTidex.DateTrn + ","
                    + TransferPresenceFromMdbTidex.Station + ","
                    + TransferPresenceFromMdbTidex.Mode + " FROM " + TransferPresenceFromMdbTidex.tableName
                    + " WHERE (" + TransferPresenceFromMdbTidex.HarismaSt + "<1 OR " + TransferPresenceFromMdbTidex.HarismaSt + " IS NULL) ORDER BY " + TransferPresenceFromMdbTidex.CardID + ", " + TransferPresenceFromMdbTidex.DateTrn;

            System.out.println("SQL : " + sql);
            stm = cn.createStatement();
            ResultSet rs = stm.executeQuery(sql);

            System.out.println("======= [ RETRIEVE FROM DATABSE MDB ACCESS ] ===========");
            System.out.println("==== Barcode | Date | Status =====");

            while (rs.next()) {         /*GEDE_20110831_01{*/
                Thread.sleep(getSleepTimeByRecord()); /* }*/
                MdbTdx mdbTdx = new MdbTdx();

                String cardID = rs.getString(TransferPresenceFromMdbTidex.CardID);
                String DateTrn = rs.getString(TransferPresenceFromMdbTidex.DateTrn);
                String station = rs.getString(TransferPresenceFromMdbTidex.Station);
                String mode = rs.getString(TransferPresenceFromMdbTidex.Mode);
                Date Datetime = getMachineDate(DateTrn);

                long employeeId = 0;

                try {

                    employeeId = PstEmployee.getEmployeeByBarcode(cardID);

                } catch (Exception E) {
                    employeeId = 0;
                    System.out.println("[ EXCEPTION ] GET EMPLOYEE BY BARCODE NUMBER " + E.toString());
                }

                /* Kondisi dimana employee dengan barcode number yang dicari ditemukan */
                if (employeeId != 0) {

                    long periodId = PstPeriod.getPeriodIdBySelectedDateString(DateTrn); /* Untuk mendapatkan schedule pada waktu tgl absen */

                    /* Pengecekan apakah schedule sudah ada atau tidak */

                    if (periodId != 0) {

                        String modeTrans = Att2000.mode_in;   /* Default */

                        if (mode.equals(mdbTdx.mode_in)) {
                            modeTrans = Att2000.mode_in;
                        } else {
                            modeTrans = Att2000.mode_out;
                        }

                        MachineTransaction machineTransaction = new MachineTransaction();
                        machineTransaction.setCardId(cardID);
                        machineTransaction.setMode(modeTrans);
                        machineTransaction.setStation(station);
                        machineTransaction.setPosted(PstMachineTransaction.POSTED_STATUS_OPEN);
                        machineTransaction.setDateTransaction(Datetime);

                        long machineTransactionId = 0;

                        try {
                            machineTransactionId = PstMachineTransaction.insertExc(machineTransaction);

                            if (machineTransactionId != 0) {

                                updateMdbTdx(cardID, DateTrn); /* Untuk mengupdate status transaksi yang sudah disimpan */
                                System.out.println("==== " + cardID + " | " + DateTrn + " |  [success] =====");

                            } else {

                                System.out.println("==== " + cardID + " | " + DateTrn + " |  [fail] =====");

                            }

                        } catch (Exception e) {
                            System.out.println("Exception " + e.toString());
                        }
                    }
                }
            }

            rs.close();
            stm.close();


        } catch (Exception e) {
            System.out.println("Exception " + e.toString());
        } finally {
            try {
                stm.close();
                cn.close();
            } catch (Exception e) {
                System.out.println("EXCEPTION " + e.toString());
            }
        }
    }

    /**
     * @Author  Roy Andika
     * @Desc    Untuk menupdate record transaction yang sudah disimpan di table machine transaction
     * @param   card
     * @param   time
     */
    private  void updateMdbTdx(String card, String time) {

        String db_mdb_access = "";

        try {
            db_mdb_access = PstSystemProperty.getValueByName("DATA_BASE_MDB_TDX");
        } catch (Exception e) {
            System.out.println("Exception " + e.toString());
        }

        Connection cn = null;
        Statement stmt = null;

        try {

            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            String cs = "jdbc:odbc:" + db_mdb_access;
            cn = DriverManager.getConnection(cs);

            String sql = "UPDATE " + TransferPresenceFromMdbTidex.tableName + " SET "
                    + TransferPresenceFromMdbTidex.HarismaSt + " = 1 WHERE "
                    + TransferPresenceFromMdbTidex.CardID + " = '" + card + "' AND "
                    + TransferPresenceFromMdbTidex.DateTrn + " = #" + time + "#";

            stmt = cn.createStatement();
            stmt.executeUpdate(sql);

        } catch (Exception E) {
            System.out.println("Exception " + E.toString());
        } finally {
            try {
                cn.close();
                stmt.close();
            } catch (Exception E) {
                System.out.println("Exception " + E.toString());
            }
        }
    }

    /**
     * @Author  Roy Andika
     * @Desc    Untuk megupdate status
     */
    private  void updateMdbTdx() {

        String db_mdb_access = "";

        try {
            db_mdb_access = PstSystemProperty.getValueByName("DATA_BASE_MDB_TDX");
        } catch (Exception e) {
            System.out.println("Exception " + e.toString());
        }

        Connection cn = null;
        Statement stmt = null;

        try {

            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            String cs = "jdbc:odbc:" + db_mdb_access;
            cn = DriverManager.getConnection(cs);

            String sql = "UPDATE " + TransferPresenceFromMdbTidex.tableName + " SET "
                    + TransferPresenceFromMdbTidex.HarismaSt + " = 1 ";

            stmt = cn.createStatement();
            stmt.executeUpdate(sql);

        } catch (Exception E) {
            System.out.println("Exception " + E.toString());
        } finally {
            try {
                cn.close();
                stmt.close();
            } catch (Exception E) {
                System.out.println("Exception " + E.toString());
            }
        }
    }

    /**
     * @Author  : Roy Andika
     * @Desc    : Untuk memproses data finger spot
     * @Desc    : Untuk memproses data in dan data out berdasarkan mesin, exm : Mesin no 01, untuk in dan mesin no 03 untuk out
     * */
    public Vector TransactionAccessByNoMachine() {

        String noMachineIn = String.valueOf(PstSystemProperty.getValueByName("MACHINE_ABS_IN"));
        String noMachineOut = String.valueOf(PstSystemProperty.getValueByName("MACHINE_ABS_OUT"));

        Hashtable strNoMchIn = new Hashtable();
        Hashtable strNoMchOut = new Hashtable();

        StringTokenizer strTokenizerNoIn = new StringTokenizer(noMachineIn, ",");
        StringTokenizer strTokenizerNoOut = new StringTokenizer(noMachineOut, ",");

        while (strTokenizerNoIn.hasMoreTokens()) {
            String strNoIn = strTokenizerNoIn.nextToken();
            strNoMchIn.put(strNoIn, strNoIn);
        }

        while (strTokenizerNoOut.hasMoreTokens()) {
            String strNoOut = strTokenizerNoOut.nextToken();
            strNoMchOut.put(strNoOut, strNoOut);
        }

        String db_access = "";

        try {
            db_access = PstSystemProperty.getValueByName("DATA_BASE_FN");
        } catch (Exception e) {
            db_access = "";
            System.out.println("Exception " + e.toString());
        }

        Connection cn = null;
        Statement stm = null;

        /* mengecek keberadaan database */
        if (!db_access.equals("")) {

            try {

                /* Configurasi database access */
                Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
                String cs = "jdbc:odbc:" + db_access;
                cn = DriverManager.getConnection(cs);

                String sql = "SELECT " + Att2000.Fld_InOut_USERID + ","
                        + Att2000.Fld_InOut_CHECKTIME + ","
                        + Att2000.Fld_InOut_CHECKTYPE + ","
                        + Att2000.Fld_InOut_VERIFYCODE + ","
                        + Att2000.Fld_InOut_SENSORID + ","
                        + Att2000.Fld_InOut_WorkCode + ","
                        + Att2000.Fld_InOut_sn
                        + " FROM " + Att2000.Tbl_CheckInOut + " WHERE "
                        + Att2000.Fld_InOut_STATUS + " IS NULL OR " + Att2000.Fld_InOut_STATUS + " = '" + Att2000.Status_Not_Transfered + "'";

                System.out.println("Sql ==== " + sql.toString());
                stm = cn.createStatement();
                ResultSet rs = stm.executeQuery(sql);

                System.out.println(" --------------------------------------------------------------------");
                System.out.println(" ------------------- DOWNLOAD DATA FINGGERSPOT ----------------------");
                System.out.println(" --------------------------------------------------------------------");

                int no = 1;
            /*GEDE_20110831_01{    getRecordTotal();  } */
                while (rs.next()) {

                    try {

                        String userId = rs.getString(Att2000.Fld_InOut_USERID);

                        String str_time = rs.getString(Att2000.Fld_InOut_CHECKTIME);

                        String idMesin = rs.getString(Att2000.Fld_InOut_SENSORID);

                        Date time = getMachineDate(str_time);

                        String barcodeNomor = getBarcodeNo(userId);

                        /* untuk megecek keberadaan employee */

                        String whereClause = PstEmployee.fieldNames[PstEmployee.FLD_BARCODE_NUMBER] + "='" + barcodeNomor + "' AND "
                                + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + " = " + PstEmployee.NO_RESIGN;

                        Vector EmpMatch = PstEmployee.list(0, 0, whereClause, null);

                        /* jika employee ada */

                        if (EmpMatch != null && EmpMatch.size() > 0) {

                            Employee employee = new Employee();

                            employee = (Employee) EmpMatch.get(0);

                            int idxSwap = str_time.indexOf(" ");

                            String date = str_time.substring(0, idxSwap);

                            /* untuk mengecek keberadaan periode */
                            long periodId = PstPeriod.getPeriodIdBySelectedDateString(date);

                            if (periodId != 0) {

                                String station = "0";
                                station = getNoMachine(idMesin);

                                if (station.equals(null)) {
                                    station = "0";
                                }

                                String mode = Att2000.mode_in;  /* default */

                                if (station.equals((String) strNoMchIn.get(station))) {

                                    mode = Att2000.mode_in;

                                } else if (station.equals((String) strNoMchOut.get(station))) {

                                    mode = Att2000.mode_out;

                                } else {

                                    mode = Att2000.mode_in;

                                }

                                MachineTransaction machineTransaction = new MachineTransaction();
                                machineTransaction.setStation(station);
                                machineTransaction.setCardId(employee.getBarcodeNumber());
                                machineTransaction.setMode(mode);
                                machineTransaction.setDateTransaction(time);
                                machineTransaction.setPosted(PstMachineTransaction.POSTED_STATUS_OPEN);

                                long machine_id = 0;

                                try {
                                    machine_id = PstMachineTransaction.insertExc(machineTransaction);
                                } catch (Exception e) {
                                    System.out.println("Exception " + e.toString());
                                }

                                if (machine_id != 0) {

                                    updateODBC(userId, str_time);
                                    System.out.println(" [ SUKSES ] " + no + " USER ID >>>> " + barcodeNomor + " TIME >>>> " + time + " \n");
                                    no++;

                                } else {

                                    System.out.println(" [ FAILD ] " + no + " USER ID >>>> " + barcodeNomor + " TIME >>>> " + time + " \n");

                                }
                            }
                        }

                    } catch (Exception E) {
                        System.out.println("[exception] " + E.toString());
                    }


                }

                rs.close();

                System.out.println(" --------------------------------------------------------------------");
                System.out.println(" --------------------------- END DOWNLOAD ---------------------------");
                System.out.println(" --------------------------------------------------------------------");

            } catch (Exception e) {
                System.out.println("Exception " + e.toString());
            } finally {
                try {
                    stm.close();
                    cn.close();
                } catch (Exception e) {
                    System.out.println("Exception " + e.toString());
                }
            }

        }
        return null;
    }

    /*GEDE_20110901_01{*/


    private int getRecordTotal() {
        String db_access = "";
        int iTotalData=0;
        try {
            db_access = PstSystemProperty.getValueByName("DATA_BASE_FN");
        } catch (Exception e) {
            System.out.println("[exception] " + e.toString());
            System.out.println("EXCEPTION SYS PROP DATA_BASE_FN NOT YET SET");
        }

        Connection cn = null;

        Statement stm = null;
        try {
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            String cs = "jdbc:odbc:" + db_access;
            cn = DriverManager.getConnection(cs);
            String qry;
            qry = "select count(" + Att2000.Fld_InOut_USERID + ") as size from " + Att2000.Tbl_CheckInOut + " where "
                    + "status ='" + STATUS_NOT_PROCES + "'";
            stm = cn.createStatement();
            ResultSet rs = stm.executeQuery(qry);
            while (rs.next()) {
                iTotalData = rs.getInt("size");
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("EXCEPTION " + e.toString());
        } finally {
            try {
                if (cn != null) {
                    cn.close();
                }
            } catch (Exception e) {
                System.out.println("EXCEPTION " + e.toString());

            }
        }
       return iTotalData;
    }

    /**
     * @return the transferData
     */
    public int getTransferData() {
        return transferData;
    }


    /**
     * @return the totalData
     */
    public int getTotalData() {
        return totalData;
    }

    /**
     * @return the running
     */
    public boolean isRunning() {
        return running;
    }

    /**
     * @param running the running to set
     */
    public void setRunning(boolean running) {
        this.running = running;
    }

    /**
     * @return the sleepTimeByRecord
     */
    public long getSleepTimeByRecord() {
        return sleepTimeByRecord;
    }

    /**
     * @param sleepTimeByRecord the sleepTimeByRecord to set
     */
    public void setSleepTimeByRecord(long sleepTimeByRecord) {
        this.sleepTimeByRecord = sleepTimeByRecord;
    }

    /**
     * @return the errMsg
     */
    public String getErrMsg() {
        return errMsg;
    }

    /**
     * @return the er
     */
    public int getEr() {
        return er;
    }
/*}GEDE_20110831_01*/

}
