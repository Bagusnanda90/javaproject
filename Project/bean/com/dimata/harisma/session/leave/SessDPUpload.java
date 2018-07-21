/*
 * Khusus untuk sanur paradise
 */
package com.dimata.harisma.session.leave;

import com.dimata.harisma.entity.attendance.DpStockManagement;
import com.dimata.harisma.entity.attendance.DpStockExpired;
import com.dimata.harisma.entity.attendance.PstDpStockManagement;
import com.dimata.harisma.entity.attendance.PstDpStockExpired;
import com.dimata.harisma.entity.employee.Employee;
import com.dimata.harisma.entity.employee.PstEmployee;
import com.dimata.harisma.entity.leave.DPUpload;
import com.dimata.harisma.entity.leave.PstDPUpload;
import com.dimata.harisma.entity.search.SrcDPUpload;
import com.dimata.harisma.entity.leave.I_Leave;
import com.dimata.system.entity.system.PstSystemProperty;
import com.dimata.qdep.db.DBException;
import com.dimata.qdep.db.DBHandler;
import com.dimata.qdep.db.DBResultSet;
import com.dimata.util.Formater;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Vector;
import com.dimata.harisma.entity.leave.*;

/**
 *
 * @author gArtha
 */
public class SessDPUpload {
    /**
     * @desc mengambil data DP yang akan diset
     * @param srcDPUpload menampung data yang menjadi acuan dalam pencarian
     * @param start menentukan awal dari pencarian data
     * @param recordToGet menentukan banyak data yang akan diambil
     * @return Vector
     */
    public static Vector searchDpData(SrcDPUpload srcDPUpload, int start, int recordToGet){
        
        DBResultSet dbResulset = null;
        try {
            
            String sql = "SELECT emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + ",emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] + ",emp." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME] + ",emp." + PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE] + ",up." + PstDPUpload.fieldNames[PstDPUpload.FLD_DP_UPLOAD_ID] + ",up." + PstDPUpload.fieldNames[PstDPUpload.FLD_OPNAME_DATE] + ",up." + PstDPUpload.fieldNames[PstDPUpload.FLD_DATA_STATUS] + ",up." + PstDPUpload.fieldNames[PstDPUpload.FLD_DP_AQ_DATE] + ",up." + PstDPUpload.fieldNames[PstDPUpload.FLD_DP_NUMBER] + " FROM " + PstEmployee.TBL_HR_EMPLOYEE + " AS emp LEFT JOIN " + PstDPUpload.TBL_DP_UPLOAD + " AS up ON emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + " = up." + PstDPUpload.fieldNames[PstDPUpload.FLD_EMPLOYEE_ID] + " AND up." + PstDPUpload.fieldNames[PstDPUpload.FLD_OPNAME_DATE] + " = \"" + Formater.formatDate(srcDPUpload.getOpnameDate(), "yyyy-MM-dd") + "\"";            

            String where = "";
            if (srcDPUpload.getEmployeeName().length() > 0) {
                if (where.length() > 0) {
                    where += " AND ";
                } else {
                    where += " WHERE ";
                }
                where += " (emp." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME] + " LIKE \'%" + srcDPUpload.getEmployeeName() + "%\')";
            }
            if (srcDPUpload.getEmployeePayroll().length() > 0) {
                if (where.length() > 0) {
                    where += " AND ";
                } else {
                    where += " WHERE ";
                }
                where += "(emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] + " LIKE \'%" + srcDPUpload.getEmployeePayroll() + "%\')";
            }
            if (srcDPUpload.getEmployeeCategory() > 0) {
                if (where.length() > 0) {
                    where += " AND ";
                } else {
                    where += " WHERE ";
                }
                where += "(emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID] + "=" + srcDPUpload.getEmployeeCategory() + ")";
            }
            if (srcDPUpload.getEmployeeDepartement() > 0) {
                if (where.length() > 0) {
                    where += " AND ";
                } else {
                    where += " WHERE ";
                }
                where += "(emp." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] + "=" + srcDPUpload.getEmployeeDepartement() + ")";
            }
            if (srcDPUpload.getEmployeeSection() > 0) {
                if (where.length() > 0) {
                    where += " AND ";
                } else {
                    where += " WHERE ";
                }
                where += "" +
                        "(emp." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID] + "=" + srcDPUpload.getEmployeeSection() + ")";
            }
            if (srcDPUpload.getEmployeePosition() > 0) {
                if (where.length() > 0) {
                    where += " AND ";
                } else {
                    where += " WHERE ";
                }
                where += "(emp." + PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID] + "=" + srcDPUpload.getEmployeePosition() + ")";
            }

            if (where.length() > 0) {
                sql = sql + where;
            }

            if (start == 0 && recordToGet == 0) {
                sql = sql + "";
            } else {
                sql = sql + " LIMIT " + start + "," + recordToGet;
            }

            dbResulset = DBHandler.execQueryResult(sql);
            ResultSet rs = dbResulset.getResultSet();
            Vector result = new Vector(1, 1);
            while (rs.next()) {
                Vector vTemp = new Vector();
                Employee objEmployee = new Employee();
                objEmployee.setOID(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]));
                objEmployee.setEmployeeNum(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]));
                objEmployee.setFullName(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]));
                objEmployee.setCommencingDate(rs.getDate(PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]));

                DPUpload objDPUpload = new DPUpload();
                objDPUpload.setOID(rs.getLong(PstDPUpload.fieldNames[PstDPUpload.FLD_DP_UPLOAD_ID]));
                objDPUpload.setEmployeeId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]));
                objDPUpload.setOpnameDate(rs.getDate(PstDPUpload.fieldNames[PstDPUpload.FLD_OPNAME_DATE]));
                objDPUpload.setDataStatus(rs.getInt(PstDPUpload.fieldNames[PstDPUpload.FLD_DATA_STATUS]));
                objDPUpload.setAcquisitionDate(rs.getDate(PstDPUpload.fieldNames[PstDPUpload.FLD_DP_AQ_DATE]));
                objDPUpload.setDPNumber(rs.getInt(PstDPUpload.fieldNames[PstDPUpload.FLD_DP_NUMBER]));

                vTemp.add(objEmployee);
                vTemp.add(objDPUpload);
                result.add(vTemp);
            }
            rs.close();
            return result;
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbResulset);
        }
        return new Vector(1, 1);
    }

    /**
     * @desc method untuk menyimpan data pada database (DPUpload)
     *      - jika data telah ada sebelumnya, maka data akan diupdate
     *      - jika data belum ada sebelumnya, maka data akan dibuat
     * @param vector dari data yang diperlukan dalam proses
     * @return 
     */
    public synchronized static Vector saveDPUpload(Vector vDPUpload) {
        Vector vDPUploadId = new Vector(1, 1);
        try {
            Date dateOpname = new Date();

            String[] emp_id = (String[]) vDPUpload.get(0);
            String[] dpUpload_id = (String[]) vDPUpload.get(1);
            String[] acquisitionDate = (String[]) vDPUpload.get(2);
            String[] dpAcquired = (String[]) vDPUpload.get(3);
            String[] data_status = (String[]) vDPUpload.get(4);
            boolean[] is_process = (boolean[]) vDPUpload.get(5);
            dateOpname = (Date) vDPUpload.get(6);
            String[] dpStockId = (String[]) vDPUpload.get(7);

            for (int i = 0; i < vDPUpload.size(); i++) {
                try{
                if (is_process[i]) {

                    DPUpload objDPUpload = new DPUpload();
                    objDPUpload.setEmployeeId(Long.parseLong(emp_id[i]));
                    objDPUpload.setDataStatus(Integer.parseInt(data_status[i]));
                    objDPUpload.setOpnameDate(dateOpname);
                    objDPUpload.setAcquisitionDate(parseStringToDate(acquisitionDate[i], DATE_FORMAT_OTHER));
                    objDPUpload.setDPNumber(Float.parseFloat(dpAcquired[i]));
                    objDPUpload.setDpStockId(Long.parseLong(dpStockId[i]));

                    if (Long.parseLong(dpUpload_id[i]) > 0) { //Jika dp upload telah ada maka akan diupdate
                        try {
                            objDPUpload.setOID(Long.parseLong(dpUpload_id[i]));
                            PstDPUpload.updateExc(objDPUpload);
                            vDPUploadId.add(String.valueOf(Long.parseLong(dpUpload_id[i])));
                        } catch (DBException ex) {
                            ex.printStackTrace();
                            System.out.println("EXCEPTION " + ex.toString());
                        }
                    } else { // Jika tidak maka akan disimpan yang baru
                        try {
                            long id = PstDPUpload.insertExc(objDPUpload);
                            vDPUploadId.add(String.valueOf(id));
                        } catch (DBException ex) {
                            ex.printStackTrace();
                            System.out.println("EXCEPTION " + ex.toString());
                        }
                    }
                }
                } catch (Exception ex) {
                    System.out.println(ex.toString());
                }
            }
        } catch (Exception ex) {
            System.out.println("[ERROR] SessDPUpload.saveDPUpload  :::::::: " + ex.toString());
        }
        return vDPUploadId;
    }

    /*
     * @DESC : Untuk menyimpan data yang diupload
     * 
     * 
     */
    public synchronized static Vector saveDPUpload_Process(Vector vDPUpload) {
        Vector vDPUploadId = new Vector(1, 1);
        try {
            Date dateOpname = new Date();

            String[] emp_id = (String[]) vDPUpload.get(0);
            String[] dpUpload_id = (String[]) vDPUpload.get(1);
            String[] acquisitionDate = (String[]) vDPUpload.get(2);
            String[] dpAcquired = (String[]) vDPUpload.get(3);
            String[] data_status = (String[]) vDPUpload.get(4);
            boolean[] is_process = (boolean[]) vDPUpload.get(5);
            dateOpname = (Date) vDPUpload.get(6);
            String[] dpStockId = (String[]) vDPUpload.get(7);

            for (int i = 0; i < emp_id.length; i++) {
               try{
                if (is_process[i]) {

                    if (Long.parseLong(dpUpload_id[i]) > 0) { //Jika dp upload telah ada maka akan diupdate



                    }

                    long DpStockSama = getOIDOpnameSama(Long.parseLong(emp_id[i]), Formater.formatDate(acquisitionDate[i], "yyyy-MM-dd"));
                    long DpStockSama_2 = getOIDOpnameSama_2(Long.parseLong(emp_id[i]), Formater.formatDate(acquisitionDate[i], "yyyy-MM-dd"));

                    long dpStockOid = 0;

                    try {

                        dpStockOid = Long.parseLong(dpStockId[i]);

                    } catch (Exception e) {
                        System.out.println("Exception " + e.toString());
                    }

                    if (dpStockOid == 0 && (DpStockSama != 0 || DpStockSama_2 != 0)) { //kondisi dimana stock belum ada dan acquisition date yang baru sudah ada

                    // tidak dijalankan

                    } else { //kondisi dimana stock belum ada dan acquisition date yang baru belum ada

                        DPUpload objDPUpload = new DPUpload();
                        objDPUpload.setEmployeeId(Long.parseLong(emp_id[i]));
                        objDPUpload.setDataStatus(Integer.parseInt(data_status[i]));
                        objDPUpload.setOpnameDate(dateOpname);
                        objDPUpload.setAcquisitionDate(parseStringToDate(acquisitionDate[i], DATE_FORMAT_OTHER));
                        objDPUpload.setDPNumber(Float.parseFloat(dpAcquired[i]));
                        objDPUpload.setDpStockId(Long.parseLong(dpStockId[i]));

                        if (Long.parseLong(dpUpload_id[i]) != 0) { //Jika dp upload telah ada maka akan diupdate
                            try {
                                objDPUpload.setOID(Long.parseLong(dpUpload_id[i]));
                                PstDPUpload.updateExc(objDPUpload);
                                vDPUploadId.add(String.valueOf(Long.parseLong(dpUpload_id[i])));
                            } catch (DBException ex) {
                                ex.printStackTrace();
                                System.out.println("EXCEPTION " + ex.toString());
                            }
                        } else { // Jika tidak maka akan disimpan yang baru
                            try {
                                long id = PstDPUpload.insertExc(objDPUpload);
                                vDPUploadId.add(String.valueOf(id));
                            } catch (DBException ex) {
                                ex.printStackTrace();
                                System.out.println("EXCEPTION " + ex.toString());
                            }
                        }
                    }
                }
               } catch (Exception ex) {
                System.out.println("[ERROR] SessDPUpload.saveDPUpload  :::::::: " + ex.toString());
                }                                
            }
        } catch (Exception ex) {
            System.out.println("[ERROR] SessDPUpload.saveDPUpload  :::::::: " + ex.toString());
        }
        return vDPUploadId;
    }

    /**
     * @desc mencari dp stock management dari periode dan id employee
     * @param id employee
     * @param tanggal perolehan
     * @return id dari hr_ll_stock_management
     */
    private static DpStockManagement getDPStockManagement(long employeeId, Date ownDate) {
        DpStockManagement objDpStockManagement = new DpStockManagement();

        return objDpStockManagement;
    }

    /**
     * @desc mengopname dp stock taken
     * @param employee id
     * @param dp stock management id
     * @param tanggal pelaksanaan opname
     * @param jumlah hari yang seharusnya tersisa
     * @return boolean status proses opname (true jika berhasil, false jika gagal)
     */
    private static boolean opnameDpStockTaken(long employeeId, long lLLStockManId, Date opnameDate, int iTaken) {
        boolean isSuccess = true;
        return isSuccess;
    }

    /**
     * @desc mengopname dp stock expired
     * @param employee id
     * @param tanggal opname
     * @param jumlah dp yang expired
     */
    private static boolean opnameDpStockExpired(long iDpStockManId, Date expiredDate, float iDpExpired) {

        boolean isSuccess = true;
        Vector vDpStockExp = new Vector(1, 1);
        String whereClause = PstDpStockExpired.fieldNames[PstDpStockExpired.FLD_DP_STOCK_ID] + "=" + iDpStockManId;

        vDpStockExp = PstDpStockExpired.list(0, 0, whereClause, null);
        DpStockExpired objDpStockExpired;

        if (vDpStockExp.size() > 0) {
            objDpStockExpired = (DpStockExpired) vDpStockExp.get(0);
        } else {
            objDpStockExpired = new DpStockExpired();
        }

        objDpStockExpired.setDpStockId(iDpStockManId);
        objDpStockExpired.setExpiredDate(expiredDate);
        objDpStockExpired.setExpiredQty(iDpExpired);

        if (objDpStockExpired.getOID() > 0) { // jika stock sudah ada
            try {
                PstDpStockExpired.updateExc(objDpStockExpired);
            } catch (DBException ex) {
                isSuccess = false;
                ex.printStackTrace();
                System.out.println("EXCEPTION " + ex.toString());
            }
        } else {  // jika stock belum ada
            try {
                PstDpStockExpired.insertExc(objDpStockExpired);
            } catch (DBException ex) {
                isSuccess = false;
                ex.printStackTrace();
                System.out.println("EXCEPTION " + ex.toString());
            }
        }
        return isSuccess;
    }

    /**
     * @desc meng-opname semua data Dp yang telah ter-upload
     * @param vDpUpload : vector dari id yang akan diproses
     * @return isSuccess : true jika berhasil dan false jika gagal
     */
    public static boolean opnameAllDp(Vector vDpUploadId) {
        boolean isSuccess = true;
        for (int i = 0; i < vDpUploadId.size(); i++) {
            String strOidDpUoload = (String) vDpUploadId.get(i);
            try {
                DPUpload dpUpload = PstDPUpload.fetchExc(Long.parseLong(strOidDpUoload));
                String strError = proccessOpnameDp(dpUpload);
                if (strError.length() > 0) {
                    isSuccess = false;
                }
            } catch (DBException ex) {
                isSuccess = false;
                ex.printStackTrace();
            }
        }
        return isSuccess;
    }

    public static void UpdateStatusDP(long DP_Stock_id, int new_status) {

        try {

            String sql = "UPDATE " + PstDpStockManagement.TBL_DP_STOCK_MANAGEMENT + " SET " +
                    PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_STATUS] + " = " + new_status +
                    " WHERE " + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_STOCK_ID] + "=" + DP_Stock_id;

            DBHandler.execUpdate(sql);

            System.out.println("UPDATE STOCK DP ID " + DP_Stock_id + " SUCCESS");

        } catch (Exception e) {
            System.out.println("EXCEPTION " + e.toString());
        }

    }

    /** 
     * @author  : Roy Andika
     * @Desc    : untuk mengupdate status Dp (expired and taken) yang sudah expired   
     * @Desc    : update dilakukan setelah membuka form leave application           
     */
    
    public static void UpdateStatusDpStock(){
        
        /* Default no -> expired dp Not process, ok - > expored dp process */
        String expiredDp = "no";
        
        try{
            expiredDp = PstSystemProperty.getValueByName("PROCESS_EXPIRED_DP");
        }catch(Exception E){
            expiredDp = "no";
            System.out.println("EXEPTION "+E.toString());
        }
        
        if(expiredDp.equals("ok")){ // Pengecekan untuk sys prop, apakah ok or no, ok - > dp di process
        
        String strWhere = PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EXPIRED_DATE] + "<'" +
                Formater.formatDate(new Date(), "yyyy-MM-dd 00:00:00") + "' AND " +
                PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_STATUS] + "=" + PstDpStockManagement.DP_STS_AKTIF +
                " AND " + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EXCEPTION_FLAG] + " = " +
                PstDpStockManagement.EXC_STS_NO;

        Vector vStockMan = new Vector(1, 1);

        vStockMan = (Vector) PstDpStockManagement.list(0, 0, strWhere, null);

        for (int i = 0; i < vStockMan.size(); i++) {

            DpStockManagement objStockManOpname = (DpStockManagement) vStockMan.get(i);
            objStockManOpname.setiDpStatus(PstDpStockManagement.DP_STS_EXPIRED);
            UpdateStatusDP(objStockManOpname.getOID(), PstDpStockManagement.DP_STS_EXPIRED);
            float residue = objStockManOpname.getiDpQty() - objStockManOpname.getQtyUsed();
            System.out.println("UPDATE STATUS DP EMPLOYEE NO EXCEPTION :::: " + objStockManOpname.getEmployeeId());
            opnameDpStockExpired(objStockManOpname.getOID(), objStockManOpname.getDtExpiredDate(), residue);
        }

        /*Cek yang expired lalu opname yang telah expired (With Exception status) */
        String strWhereWIthExc = PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EXPIRED_DATE_EXC] + "<'" +
                Formater.formatDate(new Date(), "yyyy-MM-dd 00:00:00") + "' AND " +
                PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_STATUS] + "=" + PstDpStockManagement.DP_STS_AKTIF +
                " AND " + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EXPIRED_DATE_EXC] + " = " +
                PstDpStockManagement.EXC_STS_YES;

        Vector vStockManExcExp = new Vector(1, 1);

        vStockManExcExp = (Vector) PstDpStockManagement.list(0, 0, strWhereWIthExc, null);

        for (int i = 0; i < vStockManExcExp.size(); i++) {

            DpStockManagement objStockManOpnameExc = (DpStockManagement) vStockManExcExp.get(i);
            objStockManOpnameExc.setiDpStatus(PstDpStockManagement.DP_STS_EXPIRED);
            UpdateStatusDP(objStockManOpnameExc.getOID(), PstDpStockManagement.DP_STS_EXPIRED);
            System.out.println("UPDATE STATUS DP EMPLOYEE WITH EXCEPTION :::: " + objStockManOpnameExc.getEmployeeId());
            float residue = objStockManOpnameExc.getiDpQty() - objStockManOpnameExc.getQtyUsed();
            opnameDpStockExpired(objStockManOpnameExc.getOID(), objStockManOpnameExc.getDtExpiredDate(), residue);

        }

        //Cek yang taken sampai habis lalu opname
        String strWhereTaken = PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_QTY] + " - " +
                PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_QTY_USED] + " <= 0  AND " +
                PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_STATUS] + "=" + PstDpStockManagement.DP_STS_AKTIF;

        Vector vStockManTaken = new Vector(1, 1);
        vStockManTaken = (Vector) PstDpStockManagement.list(0, 0, strWhereTaken, null);

        for (int i = 0; i < vStockManTaken.size(); i++) {
            DpStockManagement objStockManOpname = (DpStockManagement) vStockManTaken.get(i);
            UpdateStatusDP(objStockManOpname.getOID(), PstDpStockManagement.DP_STS_TAKEN);
            System.out.println("UPDATE STATUS DP EMPLOYEE STOK EMPTY :::: " + objStockManOpname.getEmployeeId());
        }
        }

    }

    /**
     * @desc proses opname dari Dp secara keseluruhan
     * @param employee id dan tanggal opname
     * @return string, error
     */
    public static String proccessOpnameDp(DPUpload dpUploadArg) {

        UpdateStatusDpStock();      // untuk mengupdate status dp yang telah expired dan habis

        String strError = "";

        I_Leave leaveConfig = null;
        try {
            leaveConfig = (I_Leave) (Class.forName(PstSystemProperty.getValueByName("LEAVE_CONFIG")).newInstance());
        } catch (Exception e) {
            System.out.println("Exception : " + e.getMessage());
        }

        if (dpUploadArg.getDpStockId() == 0) {  // kondisi dimana stock belum ada, tetapi data yang diupload sudah ada dan akan create stock baru berdasarkan data yang diupload

            int iValidityMonth = leaveConfig.getDpValidity(leaveConfig.getLevel(dpUploadArg.getEmployeeId()));
            Date dateExpired = (Date) dpUploadArg.getAcquisitionDate().clone();
            dateExpired.setMonth(dateExpired.getMonth() + iValidityMonth);
            System.out.println(dpUploadArg.getEmployeeId() + ":::::::::::::::: VALIDITY MONTH :::: " + iValidityMonth);

            DpStockManagement dpStockManagementBaru = new DpStockManagement();

            //object untuk menyimpan ke table stock management
            dpStockManagementBaru.setLeavePeriodeId(0);
            dpStockManagementBaru.setiDpQty(dpUploadArg.getDPNumber());
            dpStockManagementBaru.setDtOwningDate(dpUploadArg.getAcquisitionDate());
            dpStockManagementBaru.setDtExpiredDate(dateExpired);
            dpStockManagementBaru.setEmployeeId(dpUploadArg.getEmployeeId());
            dpStockManagementBaru.setStNote("");
            dpStockManagementBaru.setQtyResidue(dpUploadArg.getDPNumber());
            dpStockManagementBaru.setiDpStatus(PstDpStockManagement.DP_STS_AKTIF);

            if (dpUploadArg.getDPNumber() == 0) {

                dpStockManagementBaru.setiDpStatus(PstDpStockManagement.DP_STS_TAKEN);

            }
            
            long dp_stock_id = 0;
            
            try {

                dp_stock_id = PstDpStockManagement.insertExc(dpStockManagementBaru);

            } catch (DBException ex){
                System.out.println("EXCEPTION " + ex.toString());
                strError = ex.toString();
                ex.printStackTrace();
            }
            
            dpUploadArg.setDpStockId(dp_stock_id);      //untuk mengeset dp stock id
            
            try{
                
                PstDPUpload.updateExc(dpUploadArg);
                
            }catch(Exception e){
                System.out.println("EXCEPTION "+e.toString());
            }

            
        } else {

            //jika kondisi stock management sudah ada dan akan di update

            DpStockManagement dpStockManagement = new DpStockManagement();

            try {
                dpStockManagement = PstDpStockManagement.fetchExc(dpUploadArg.getDpStockId());
            } catch (Exception e) {
                System.out.println("EXCEPTION " + e.toString());
            }

            float residu = 0;
            residu = dpUploadArg.getDPNumber() - dpStockManagement.getQtyUsed();

            dpStockManagement.setiDpQty(dpUploadArg.getDPNumber());
            dpStockManagement.setQtyResidue(residu);

            if (residu == 0) {   // kodisi dimana dp yang diinputkan waktu balancing adalah 0 / habis
                dpStockManagement.setiDpStatus(PstDpStockManagement.DP_STS_TAKEN);      // status akan diubah menjadi taken
            }

            try {
                PstDpStockManagement.updateExc(dpStockManagement);
            } catch (Exception e) {

                System.out.println("EXCEPTION " + e.toString());

            }
        }

        if (strError.length() == 0){
            dpUploadArg.setDataStatus(PstDPUpload.FLD_DOC_STATUS_PROCESS);
            try {
                PstDPUpload.updateExc(dpUploadArg);
            } catch (DBException ex) {
                ex.printStackTrace();
            }
        }

        /*
        //Jika input jumlah dp adalah positif, maka proses
        if (dpUploadArg.getDPNumber() >= 0) {
        //Cari apakah telah ada data pada tanggal kepemilikan,
        //jika ada update dan jika tidak buat yang baru
        vDpStock = PstDpStockManagement.listDpStockPerEmp(dpUploadArg.getEmployeeId(), dpUploadArg.getAcquisitionDate());
        if (vDpStock.size() > 0) {
        objDpStockManagement = (DpStockManagement) vDpStock.get(0);
        } else {
        objDpStockManagement = new DpStockManagement();
        objDpStockManagement.setDtExpiredDateExc(dpUploadArg.getAcquisitionDate());
        }
        objDpStockManagement.setEmployeeId(dpUploadArg.getEmployeeId());
        objDpStockManagement.setDtOwningDate(dpUploadArg.getAcquisitionDate());
        int iValidityMonth = leaveConfig.getDpValidity(leaveConfig.getLevel(dpUploadArg.getEmployeeId()));
        Date dateExpired = (Date) dpUploadArg.getAcquisitionDate().clone();
        dateExpired.setMonth(dateExpired.getMonth() + iValidityMonth);
        System.out.println(objDpStockManagement.getEmployeeId() + ":::::::::::::::: VALIDITY MONTH :::: " + iValidityMonth);
        objDpStockManagement.setDtExpiredDate(dateExpired);
        objDpStockManagement.setiDpQty(dpUploadArg.getDPNumber());
        objDpStockManagement.setQtyResidue(objDpStockManagement.getiDpQty() - objDpStockManagement.getQtyUsed());
        objDpStockManagement.setiDpStatus(PstDpStockManagement.DP_STS_AKTIF);
        if (objDpStockManagement.getQtyResidue() == 0) {
        objDpStockManagement.setiDpStatus(PstDpStockManagement.DP_STS_TAKEN);
        }
        //Cek status dp sesuai dengan tanggal pengambilan
        if (objDpStockManagement.getDtExpiredDate().getTime() < dpUploadArg.getOpnameDate().getTime()) {
        if (objDpStockManagement.getQtyResidue() > 0) {
        objDpStockManagement.setiDpStatus(PstDpStockManagement.DP_STS_EXPIRED);
        opnameDpStockExpired(objDpStockManagement.getOID(), objDpStockManagement.getDtExpiredDate(), objDpStockManagement.getQtyResidue());
        }
        }
        long objId = 0; //Simpan oid DpStockManagement untuk proses pembayaran DP yang belum terbayar(paid)
        try {
        if (objDpStockManagement.getOID() > 0) {
        objId = PstDpStockManagement.updateExc(objDpStockManagement);
        } else {
        objId = PstDpStockManagement.insertExc(objDpStockManagement);
        }
        } catch (DBException ex) {
        strError = ex.toString();
        ex.printStackTrace();
        }
        //Melakukan pembayaran untuk 
        //dp yang diambil sebelum perolehan dp
        Vector vDpTaken = new Vector(1, 1);
        vDpTaken = PstDpStockTaken.getDpPayableBeforeOpname(dpUploadArg.getEmployeeId(), dpUploadArg.getOpnameDate());
        System.out.println(":::::::::::::: HUTANG DP YANG BELUM TERBAYAR ::::::: " + vDpTaken.size());
        if (objDpStockManagement.getQtyResidue() <= vDpTaken.size()) {      // kindisi untuk 
        System.out.println("::::: RESIDU DP STOCK <= OPNAME STOCK :::::");
        objDpStockManagement.setQtyUsed(objDpStockManagement.getiDpQty());
        objDpStockManagement.setQtyResidue(0);
        objDpStockManagement.setiDpStatus(PstDpStockManagement.DP_STS_TAKEN);
        for (int i = 0; i < objDpStockManagement.getQtyResidue(); i++) {
        DpStockTaken objDPStock = (DpStockTaken) vDpTaken.get(i);
        objDPStock.setDpStockId(objId);
        objDPStock.setPaidDate(dpUploadArg.getOpnameDate());
        try {
        PstDpStockTaken.updateExc(objDPStock);
        } catch (DBException ex) {
        strError = ex.toString();
        ex.printStackTrace();
        }
        }
        } else {
        System.out.println("::::: RESIDU DP STOCK > OPNAME STOCK");
        objDpStockManagement.setQtyUsed(objDpStockManagement.getQtyUsed() + vDpTaken.size());
        objDpStockManagement.setQtyResidue(objDpStockManagement.getQtyResidue() - vDpTaken.size());
        for (int i = 0; i < vDpTaken.size(); i++) {
        DpStockTaken objDPStock = (DpStockTaken) vDpTaken.get(i);
        objDPStock.setDpStockId(objId);
        objDPStock.setPaidDate(dpUploadArg.getOpnameDate());
        try {
        PstDpStockTaken.updateExc(objDPStock);
        } catch (DBException ex) {
        strError = ex.toString();
        ex.printStackTrace();
        }
        }
        }
        try {
        objDpStockManagement.setOID(objId);
        PstDpStockManagement.updateExc(objDpStockManagement);
        } catch (DBException ex) {
        strError = ex.toString();
        ex.printStackTrace();
        }
        } //Jika input jumlah dp adalah negatif, maka buatkan taken dp
        else {
        int takenVal = 0;
        takenVal = -1 * dpUploadArg.getDPNumber();
        String strWhereTemp = PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_EMPLOYEE_ID] + "=" + dpUploadArg.getEmployeeId() + " AND " + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_DATE] + "='" + dpUploadArg.getAcquisitionDate() + "'";
        int countTaken = PstDpStockTaken.getCount(strWhereTemp);
        for (int i = 0; i < takenVal - countTaken; i++) {
        Date tempDate = (Date) dpUploadArg.getAcquisitionDate().clone();
        DpStockTaken objDpStockTaken = new DpStockTaken();
        DpStockManagement objDpStockManActive = new DpStockManagement();
        objDpStockManActive = PstDpStockManagement.getDpStockPerEmpFirst(dpUploadArg.getEmployeeId());
        if (objDpStockManActive.getQtyResidue() > 0) {
        try {
        objDpStockManActive.setQtyUsed(objDpStockManActive.getQtyUsed() + 1);
        objDpStockManActive.setQtyResidue(objDpStockManActive.getQtyResidue() - 1);
        if (objDpStockManActive.getQtyResidue() == 0) {
        objDpStockManActive.setiDpStatus(PstDpStockManagement.DP_STS_TAKEN);
        }
        PstDpStockManagement.updateExc(objDpStockManActive);
        objDpStockTaken.setDpStockId(objDpStockManActive.getOID());
        objDpStockTaken.setPaidDate(dpUploadArg.getAcquisitionDate());
        } catch (DBException ex) {
        ex.printStackTrace();
        }
        }
        objDpStockTaken.setEmployeeId(dpUploadArg.getEmployeeId());
        objDpStockTaken.setTakenDate(tempDate);
        objDpStockTaken.setTakenQty(1);
        try {
        PstDpStockTaken.insertExc(objDpStockTaken);
        } catch (DBException ex) {
        strError = ex.toString();
        ex.printStackTrace();
        }
        }
        }
         * */
        //Update dp status


        return strError;
    }

    /**
     * @desc mencari data dp yang telah diupload per tgl
     * @return vector dari dpUpload
     * 
     */
    public static Vector getAllDpUpload(int start, int recordToGet) {

        Vector vUploadOpname = new Vector();
        DBResultSet dbResultSet = null;
        String sql = "SELECT DISTINCT " + PstDPUpload.fieldNames[PstDPUpload.FLD_OPNAME_DATE] + " FROM " + PstDPUpload.TBL_DP_UPLOAD + " ORDER BY " + PstDPUpload.fieldNames[PstDPUpload.FLD_OPNAME_DATE];
        if (start != 0 || recordToGet != 0) {
            sql += " LIMIT " + start + "," + recordToGet;
        }

        try {
            dbResultSet = DBHandler.execQueryResult(sql);
            ResultSet rs = dbResultSet.getResultSet();
            while (rs.next()) {
                Date date = rs.getDate(PstDPUpload.fieldNames[PstDPUpload.FLD_OPNAME_DATE]);
                vUploadOpname.add(Formater.formatDate(date, "yyyy-MM-dd"));
            }
        } catch (Exception ex) {
        }
        return vUploadOpname;
    }
    public static final int DATE_FORMAT_ID = 0;
    public static final int DATE_FORMAT_OTHER = 1;

    /**
     * @param mem parsing tanggal
     * var thn = date1.substring(0,4);
    var bln = date1.substring(5,7);	
    if(bln.charAt(0)=="0"){
    bln = ""+bln.charAt(1);
    }
    var hri = date1.substring(8,10);
    if(hri.charAt(0)=="0"){
    hri = ""+hri.charAt(1);
    }
     */
    public static Date parseStringToDate(String strDate, int dateFormat) {
        String yyyy = "1900", mm = "1", dd = "0";
        int iyyyy = 0, imm = 0, idd = 0;
        switch (dateFormat) {
            case DATE_FORMAT_ID:
                //13-12-2008
                dd = strDate.substring(0, 2);
                if ("0".equals(dd.substring(0, 1))) {
                    dd = dd.substring(1, 2);
                }
                mm = strDate.substring(3, 5);
                if ("0".equals(mm.substring(0, 1))) {
                    mm = mm.substring(1, 2);
                }
                yyyy = strDate.substring(6, 10);
                break;
            case DATE_FORMAT_OTHER:
                //2008-12-13
                yyyy = strDate.substring(0, 4);
                mm = strDate.substring(5, 7);
                if ("0".equals(mm.substring(0, 1))) {
                    mm = mm.substring(1, 2);
                }
                dd = strDate.substring(8, 10);
                if ("0".equals(dd.substring(0, 1))) {
                    dd = dd.substring(1, 2);
                }
                break;
        }
        iyyyy = Integer.parseInt(yyyy) - 1900;
        imm = Integer.parseInt(mm) - 1;
        idd = Integer.parseInt(dd);
        try {
            //  System.out.println("DATE OUT :::::::: "+new Date(iyyyy, imm, idd));
            return new Date(iyyyy, imm, idd);
        } catch (Exception ex) {
        }
        return new Date();
    }

    public static Vector getEmployeeDP(SrcDPUpload srcDPUpload, int start, int recordToGet) {

        DBResultSet dbrs = null;

        try {

            String sql = "SELECT " + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + " FROM " +
                    PstEmployee.TBL_HR_EMPLOYEE + " WHERE " +
                    PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + "=" + PstEmployee.FLD_RESIGNED;

            String where = "";
            if (srcDPUpload.getEmployeeName().length() > 0) {
                if (where.length() > 0) {
                    where += " AND ";
                } else {
                    where += " WHERE ";
                }
                where += " (emp." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME] + " LIKE \'%" + srcDPUpload.getEmployeeName() + "%\')";
            }
            if (srcDPUpload.getEmployeePayroll().length() > 0) {
                if (where.length() > 0) {
                    where += " AND ";
                } else {
                    where += " WHERE ";
                }
                where += "(emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] + " LIKE \'%" + srcDPUpload.getEmployeePayroll() + "%\')";
            }
            if (srcDPUpload.getEmployeeCategory() > 0) {
                if (where.length() > 0) {
                    where += " AND ";
                } else {
                    where += " WHERE ";
                }
                where += "(emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID] + "=" + srcDPUpload.getEmployeeCategory() + ")";
            }
            if (srcDPUpload.getEmployeeDepartement() > 0) {
                if (where.length() > 0) {
                    where += " AND ";
                } else {
                    where += " WHERE ";
                }
                where += "(emp." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] + "=" + srcDPUpload.getEmployeeDepartement() + ")";
            }
            if (srcDPUpload.getEmployeeSection() > 0) {
                if (where.length() > 0) {
                    where += " AND ";
                } else {
                    where += " WHERE ";
                }
                where += "" +
                        "(emp." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID] + "=" + srcDPUpload.getEmployeeSection() + ")";
            }
            if (srcDPUpload.getEmployeePosition() > 0) {
                if (where.length() > 0) {
                    where += " AND ";
                } else {
                    where += " WHERE ";
                }
                where += "(emp." + PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID] + "=" + srcDPUpload.getEmployeePosition() + ")";
            }

            if (where.length() > 0) {
                sql = sql + where;
            }

            if (start == 0 && recordToGet == 0) {
                sql = sql + "";
            } else {
                sql = sql + " LIMIT " + start + "," + recordToGet;
            }


            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            Vector result = new Vector();

            int idx = 0;

            while (rs.next()) {

                long employee_id = 0;

                if (idx >= start && idx <= recordToGet) {

                    employee_id = rs.getLong(1);
                    Vector list = listDPStock(employee_id);
                    if (list != null) {
                        for (int i = 0; i < list.size(); i++) {

                            DpStockManagement dpStockManagement = (DpStockManagement) list.get(i);

                            result.add(dpStockManagement);

                            idx++;
                        }
                    } else {

                        DpStockManagement dpStockManagement = new DpStockManagement();
                        dpStockManagement.setEmployeeId(employee_id);

                        try {
                            PstDpStockManagement.insertExc(dpStockManagement);
                        } catch (Exception e) {
                            System.out.println("Exception " + e.toString());
                        }

                        idx++;

                    }
                } else {

                    idx++;

                }

            }


        } catch (Exception e) {
            System.out.println("EXCEPTION " + e.toString());
        } finally {
            DBResultSet.close(dbrs);
        }

        return null;
    }

    public static Vector listDPStock(long employee_ID) {
        DBResultSet dbrs = null;
        Vector result = new Vector();

        try {

            String sql = "SELECT " + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_STOCK_ID] +
                    "," + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EMPLOYEE_ID] +
                    "," + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_QTY] +
                    "," + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_QTY_RESIDUE] +
                    "," + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_QTY_USED] +
                    "," + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_NOTE] +
                    "," + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_QTY_USED] +
                    "," + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_STATUS] +
                    " FROM " + PstDpStockManagement.TBL_DP_STOCK_MANAGEMENT + " WHERE " + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_STATUS] + "=" + PstDpStockManagement.DP_STS_AKTIF;

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {

                DpListUpload dpListUpload = new DpListUpload();

            }

        } catch (Exception e) {
            System.out.println("EXCEPTION " + e.toString());
        } finally {
            DBResultSet.close(dbrs);
        }

        return null;

    }

    public static long getOIDDpManagement(long employeeOID, Date AcquisitionDate) {

        DBResultSet dbrs = null;

        try {
            String sql = "SELECT " + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_STOCK_ID] + " FROM " +
                    PstDpStockManagement.TBL_DP_STOCK_MANAGEMENT +
                    " WHERE " + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EMPLOYEE_ID] + "=" + employeeOID +
                    " AND " + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_OWNING_DATE] + " = '" +
                    Formater.formatDate(AcquisitionDate, "yyyy-MM-dd") + "' ";

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                long DpStockManagementId = rs.getLong(1);

                return DpStockManagementId;
            }


        } catch (Exception e) {
            System.out.println("ESCEPTION " + e.toString());
        } finally {
            DBResultSet.close(dbrs);
        }
        return 0;
    }

    public static Vector listDPUpload(long employeeOID) {

        Vector result = new Vector();

        String where = PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EMPLOYEE_ID] + "=" + employeeOID + " AND " +
                PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_STATUS] + "=" + PstDpStockManagement.DP_STS_AKTIF;

        String order = PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_OWNING_DATE];

        Vector listDpStock = PstDpStockManagement.list(0, 0, where, order);

        if (listDpStock != null || listDpStock.size() > 0) {

            for (int i = 0; i < listDpStock.size(); i++) {

                DpStockManagement dpStockManagement = new DpStockManagement();

                dpStockManagement = (DpStockManagement) listDpStock.get(i);

                result.add(dpStockManagement);

            }

            return result;

        }
        return null;

    }

    public static Vector ListDPStock(SrcDPUpload srcDPUpload, int start, int recordToGet) {

        String where = PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + "=" + PstEmployee.NO_RESIGN;

        if (srcDPUpload.getEmployeeName().length() > 0) {
            if (where.length() > 0) {
                where += " AND ";
            } else {
                where += " WHERE ";
            }
            where += PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME] + " LIKE \'%" + srcDPUpload.getEmployeeName() + "%\'";
        }
        if (srcDPUpload.getEmployeePayroll().length() > 0) {
            if (where.length() > 0) {
                where += " AND ";
            } else {
                where += " WHERE ";
            }
            where += PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] + " LIKE \'%" + srcDPUpload.getEmployeePayroll() + "%\'";
        }
        if (srcDPUpload.getEmployeeCategory() > 0) {
            if (where.length() > 0) {
                where += " AND ";
            } else {
                where += " WHERE ";
            }
            where += PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID] + "=" + srcDPUpload.getEmployeeCategory();
        }

        if (srcDPUpload.getEmployeeDepartement() > 0) {
            if (where.length() > 0) {
                where += " AND ";
            } else {
                where += " WHERE ";
            }
            where += PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] + "=" + srcDPUpload.getEmployeeDepartement();
        }

        if (srcDPUpload.getEmployeeSection() > 0) {
            if (where.length() > 0) {
                where += " AND ";
            } else {
                where += " WHERE ";
            }
            where += PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID] + "=" + srcDPUpload.getEmployeeSection();
        }

        if (srcDPUpload.getEmployeePosition() > 0) {
            if (where.length() > 0) {
                where += " AND ";
            } else {
                where += " WHERE ";
            }
            where += PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID] + "=" + srcDPUpload.getEmployeePosition();
        }

        Vector listEmployee = PstEmployee.list(start, recordToGet, where, null);

        Vector result = new Vector();

        if (listEmployee != null || listEmployee.size() > 0) {

            for (int i = 0; i < listEmployee.size(); i++) { //looping sebanyak data employee

                Employee employee = (Employee) listEmployee.get(i);

                Vector listDpStock = listDPUpload(employee.getOID());

                for (int j = 0; j < listDpStock.size(); j++) {

                    DpStockManagement dpStockManagement = new DpStockManagement();

                    dpStockManagement = (DpStockManagement) listDpStock.get(j);

                    listDpUpload objListDpUpload = new listDpUpload();

                    objListDpUpload.setEmployee_id(employee.getOID());
                    objListDpUpload.setEmployee_num(employee.getEmployeeNum());
                    objListDpUpload.setFull_name(employee.getFullName());
                    objListDpUpload.setDepartment_id(employee.getDepartmentId());
                    objListDpUpload.setCommencing_date(employee.getCommencingDate());

                    objListDpUpload.setDp_stock_id(dpStockManagement.getOID());
                    objListDpUpload.setDp_qty(dpStockManagement.getiDpQty());
                    objListDpUpload.setDp_status(dpStockManagement.getiDpStatus());
                    objListDpUpload.setExpired_date(dpStockManagement.getDtExpiredDate());
                    objListDpUpload.setExpired_date_exc(dpStockManagement.getDtExpiredDateExc());
                    objListDpUpload.setNote(dpStockManagement.getStNote());
                    objListDpUpload.setOwning_date(dpStockManagement.getDtOwningDate());
                    objListDpUpload.setQty_residue(dpStockManagement.getQtyResidue());
                    objListDpUpload.setQty_used(dpStockManagement.getQtyUsed());
                    objListDpUpload.setException_flag(dpStockManagement.getiExceptionFlag());

                    result.add(objListDpUpload);
                }
            }

            return result;

        } else {

            return null;

        }
    }

    //untuk mendapatkan size dari list DP
    public static int sizeListDP(SrcDPUpload srcDPUpload) {

        DBResultSet dbrs = null;

        try {


            String sql_A = "SELECT COUNT(EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + ")" +
                    " FROM " + PstEmployee.TBL_HR_EMPLOYEE + " EMP INNER JOIN hr_view_dp_stock_aktif DPM " +
                    " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + " = DPM." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EMPLOYEE_ID] +
                    " WHERE EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + " = " + PstEmployee.NO_RESIGN +
                    " AND DPM." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_STOCK_ID] +
                    " NOT IN ( SELECT DPU." + PstDPUpload.fieldNames[PstDPUpload.FLD_DP_STOCK_ID] +
                    " FROM " + PstDPUpload.TBL_DP_UPLOAD + " DPU WHERE DPU." + PstDPUpload.fieldNames[PstDPUpload.FLD_DATA_STATUS] +
                    " = " + PstDPUpload.FLD_DOC_STATUS_NOT_PROCESS + " ) ";

            String sql_B = "SELECT COUNT(EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + ")" +
                    " FROM " + PstEmployee.TBL_HR_EMPLOYEE + " EMP INNER JOIN hr_view_dp_upload_not_proces DPU ON " +
                    " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + " = DPU." + PstDPUpload.fieldNames[PstDPUpload.FLD_EMPLOYEE_ID] +
                    " WHERE EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + " = " + PstEmployee.NO_RESIGN;

            String sql_D = "SELECT COUNT(EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + ")" +
                    " FROM " + PstEmployee.TBL_HR_EMPLOYEE + " EMP " +
                    " WHERE EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + " = " + PstEmployee.NO_RESIGN + " AND " +
                    " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + " NOT IN " +
                    " ( SELECT EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] +
                    " FROM " + PstEmployee.TBL_HR_EMPLOYEE + " EMP INNER JOIN hr_view_dp_stock_aktif DPM " +
                    " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + " = DPM." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EMPLOYEE_ID] +
                    " WHERE EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + " = " + PstEmployee.NO_RESIGN +
                    " AND DPM." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_STOCK_ID] +
                    " NOT IN ( SELECT DPU." + PstDPUpload.fieldNames[PstDPUpload.FLD_DP_STOCK_ID] +
                    " FROM " + PstDPUpload.TBL_DP_UPLOAD + " DPU WHERE DPU." + PstDPUpload.fieldNames[PstDPUpload.FLD_DATA_STATUS] +
                    " = " + PstDPUpload.FLD_DOC_STATUS_NOT_PROCESS + " ) )" +
                    " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] +
                    " NOT IN ( SELECT EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] +
                    " FROM " + PstEmployee.TBL_HR_EMPLOYEE + " EMP INNER JOIN hr_view_dp_upload_not_proces DPU ON " +
                    " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + " = DPU." + PstDPUpload.fieldNames[PstDPUpload.FLD_EMPLOYEE_ID] +
                    " WHERE EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + " = " + PstEmployee.NO_RESIGN + " ) ";

            /*
            String sql_A = "SELECT COUNT( EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + " ) " +
            " FROM " + PstEmployee.TBL_HR_EMPLOYEE + " EMP LEFT JOIN hr_view_dp_stock_aktif DPM " +
            " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + " = DPM." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EMPLOYEE_ID] +                    
            " WHERE EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + " = " + PstEmployee.NO_RESIGN +
            " AND DPM." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_STOCK_ID] +
            " NOT IN ( SELECT DPU." + PstDPUpload.fieldNames[PstDPUpload.FLD_DP_STOCK_ID] +
            " FROM " + PstDPUpload.TBL_DP_UPLOAD + " DPU WHERE DPU." + PstDPUpload.fieldNames[PstDPUpload.FLD_DATA_STATUS] +
            " = " + PstDPUpload.FLD_DOC_STATUS_NOT_PROCESS + " ) ";
            String sql_B = "SELECT COUNT( EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + " ) " +
            " FROM " + PstEmployee.TBL_HR_EMPLOYEE + " EMP LEFT JOIN hr_view_dp_upload_not_proces DPU ON " +
            " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + " = DPU." + PstDPUpload.fieldNames[PstDPUpload.FLD_EMPLOYEE_ID] +
            " WHERE EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + " = " + PstEmployee.NO_RESIGN;
             */
            String where = "";

            if (srcDPUpload.getEmployeeName().length() > 0) {

                where += " AND ";

                where += " (EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME] + " LIKE \'%" + srcDPUpload.getEmployeeName() + "%\')";
            }
            if (srcDPUpload.getEmployeePayroll().length() > 0) {

                where += " AND ";

                where += "(EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] + " LIKE \'%" + srcDPUpload.getEmployeePayroll() + "%\')";
            }
            if (srcDPUpload.getEmployeeCategory() > 0) {

                where += " AND ";

                where += "(EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID] + "=" + srcDPUpload.getEmployeeCategory() + ")";
            }
            if (srcDPUpload.getEmployeeDepartement() > 0) {

                where += " AND ";

                where += "(EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] + "=" + srcDPUpload.getEmployeeDepartement() + ")";
            }
            if (srcDPUpload.getEmployeeSection() > 0) {

                where += " AND ";

                where += "(EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID] + "=" + srcDPUpload.getEmployeeSection() + ")";
            }
            if (srcDPUpload.getEmployeePosition() > 0) {

                where += " AND ";

                where += "(EMP." + PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID] + "=" + srcDPUpload.getEmployeePosition() + ")";
            }

            if (where.length() > 0) {
                sql_A = sql_A + where;
                sql_B = sql_B + where;
                sql_D = sql_D + where;
            }

            String sql_C = sql_A + " UNION " + sql_B + " UNION " + sql_D;

            dbrs = DBHandler.execQueryResult(sql_C);
            ResultSet rs = dbrs.getResultSet();

            int sum = 0;
            while (rs.next()) {

                sum = sum + rs.getInt(1);

            }

            return sum;

        } catch (Exception e) {
            System.out.println("EXCEPTION " + e.toString());
        } finally {
            DBResultSet.close(dbrs);
        }

        return 0;
    }

    //untuk mendapatkan list DP
    public static Vector listDP(SrcDPUpload srcDPUpload, int start, int recordToGet) {

        DBResultSet dbrs = null;

        Vector result = new Vector();

        try {

            String sql_A = "SELECT EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] +
                    ",EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] +
                    ",EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME] +
                    ",EMP." + PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE] +
                    ",EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] +
                    ", 0 as '" + PstDPUpload.fieldNames[PstDPUpload.FLD_DP_UPLOAD_ID] + "' " +
                    ",DPM." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_STOCK_ID] +
                    " FROM " + PstEmployee.TBL_HR_EMPLOYEE + " EMP INNER JOIN hr_view_dp_stock_aktif DPM " +
                    " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + " = DPM." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EMPLOYEE_ID] +
                    " WHERE EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + " = " + PstEmployee.NO_RESIGN +
                    " AND DPM." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_STOCK_ID] +
                    " NOT IN ( SELECT DPU." + PstDPUpload.fieldNames[PstDPUpload.FLD_DP_STOCK_ID] +
                    " FROM " + PstDPUpload.TBL_DP_UPLOAD + " DPU WHERE DPU." + PstDPUpload.fieldNames[PstDPUpload.FLD_DATA_STATUS] +
                    " = " + PstDPUpload.FLD_DOC_STATUS_NOT_PROCESS + " ) ";

            String sql_B = "SELECT EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] +
                    ",EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] +
                    ",EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME] +
                    ",EMP." + PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE] +
                    ",EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] +
                    ",DPU." + PstDPUpload.fieldNames[PstDPUpload.FLD_DP_UPLOAD_ID] +
                    ",DPU." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_STOCK_ID] +
                    " FROM " + PstEmployee.TBL_HR_EMPLOYEE + " EMP INNER JOIN hr_view_dp_upload_not_proces DPU ON " +
                    " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + " = DPU." + PstDPUpload.fieldNames[PstDPUpload.FLD_EMPLOYEE_ID] +
                    " WHERE EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + " = " + PstEmployee.NO_RESIGN;

            String sql_D = "SELECT EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] +
                    ",EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] +
                    ",EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME] +
                    ",EMP." + PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE] +
                    ",EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] +
                    ", 0 as '" + PstDPUpload.fieldNames[PstDPUpload.FLD_DP_UPLOAD_ID] + "' " +
                    ", 0 as '" + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_STOCK_ID] + "' " +
                    " FROM " + PstEmployee.TBL_HR_EMPLOYEE + " EMP " +
                    " WHERE EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + " = " + PstEmployee.NO_RESIGN + " AND " +
                    " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + " NOT IN " +
                    " ( SELECT EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] +
                    " FROM " + PstEmployee.TBL_HR_EMPLOYEE + " EMP INNER JOIN hr_view_dp_stock_aktif DPM " +
                    " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + " = DPM." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EMPLOYEE_ID] +
                    " WHERE EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + " = " + PstEmployee.NO_RESIGN +
                    " AND DPM." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_STOCK_ID] +
                    " NOT IN ( SELECT DPU." + PstDPUpload.fieldNames[PstDPUpload.FLD_DP_STOCK_ID] +
                    " FROM " + PstDPUpload.TBL_DP_UPLOAD + " DPU WHERE DPU." + PstDPUpload.fieldNames[PstDPUpload.FLD_DATA_STATUS] +
                    " = " + PstDPUpload.FLD_DOC_STATUS_NOT_PROCESS + " ) )" +
                    " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] +
                    " NOT IN ( SELECT EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] +
                    " FROM " + PstEmployee.TBL_HR_EMPLOYEE + " EMP INNER JOIN hr_view_dp_upload_not_proces DPU ON " +
                    " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + " = DPU." + PstDPUpload.fieldNames[PstDPUpload.FLD_EMPLOYEE_ID] +
                    " WHERE EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + " = " + PstEmployee.NO_RESIGN + " ) ";


            String where = "";

            if (srcDPUpload.getEmployeeName().length() > 0) {

                where += " AND ";

                where += " (EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME] + " LIKE \'%" + srcDPUpload.getEmployeeName() + "%\')";
            }
            if (srcDPUpload.getEmployeePayroll().length() > 0) {

                where += " AND ";

                where += "(EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] + " LIKE \'%" + srcDPUpload.getEmployeePayroll() + "%\')";
            }
            if (srcDPUpload.getEmployeeCategory() > 0) {

                where += " AND ";

                where += "(EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID] + "=" + srcDPUpload.getEmployeeCategory() + ")";
            }
            if (srcDPUpload.getEmployeeDepartement() > 0) {

                where += " AND ";

                where += "(EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] + "=" + srcDPUpload.getEmployeeDepartement() + ")";
            }
            if (srcDPUpload.getEmployeeSection() > 0) {

                where += " AND ";

                where += "(EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID] + "=" + srcDPUpload.getEmployeeSection() + ")";
            }
            if (srcDPUpload.getEmployeePosition() > 0) {

                where += " AND ";

                where += "(EMP." + PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID] + "=" + srcDPUpload.getEmployeePosition() + ")";
            }

            if (where.length() > 0) {
                sql_A = sql_A + where;
                sql_B = sql_B + where;
                sql_D = sql_D + where;
            }

            String sql_C = sql_A + " UNION " + sql_B + " UNION " + sql_D + " ORDER BY " + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM];

            if (start == 0 && recordToGet == 0) {
                sql_C = sql_C + "";
            } else {
                sql_C = sql_C + " LIMIT " + start + "," + recordToGet;
            }

            dbrs = DBHandler.execQueryResult(sql_C);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {

                listDpUpload objListDpUpload = new listDpUpload();

                objListDpUpload.setEmployee_id(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]));
                objListDpUpload.setEmployee_num(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]));
                objListDpUpload.setFull_name(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]));
                objListDpUpload.setDepartment_id(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]));
                objListDpUpload.setCommencing_date(rs.getDate(PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]));
                objListDpUpload.setDp_stock_id(rs.getLong(PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_STOCK_ID]));
                objListDpUpload.setDp_upload_id(rs.getLong(PstDPUpload.fieldNames[PstDPUpload.FLD_DP_UPLOAD_ID]));

                result.add(objListDpUpload);

            }

            return result;

        } catch (Exception e) {
            System.out.println("Exception " + e.toString());
        } finally {
            DBResultSet.close(dbrs);
        }
        return null;
    }
    
    
    
    public static int sizeDp(SrcDPUpload srcDPUpload){
        
         DBResultSet dbrs = null;
        
        try{
            
            String sql = "SELECT COUNT(EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+") "+
                    " FROM "+ PstEmployee.TBL_HR_EMPLOYEE+" EMP WHERE EMP."+
                    PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]+" = "+PstEmployee.NO_RESIGN;  
            
            String where = "";

            if (srcDPUpload.getEmployeeName().length() > 0) {

                where += " AND ";

                where += " (EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME] + " LIKE \'%" + srcDPUpload.getEmployeeName() + "%\')";
            }
            if (srcDPUpload.getEmployeePayroll().length() > 0) {

                where += " AND ";

                where += "(EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] + " LIKE \'%" + srcDPUpload.getEmployeePayroll() + "%\')";
            }
            if (srcDPUpload.getEmployeeCategory() > 0) {

                where += " AND ";

                where += "(EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID] + "=" + srcDPUpload.getEmployeeCategory() + ")";
            }
            if (srcDPUpload.getEmployeeDepartement() > 0) {

                where += " AND ";

                where += "(EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] + "=" + srcDPUpload.getEmployeeDepartement() + ")";
            }
            if (srcDPUpload.getEmployeeSection() > 0) {

                where += " AND ";

                where += "(EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID] + "=" + srcDPUpload.getEmployeeSection() + ")";
            }
            if (srcDPUpload.getEmployeePosition() > 0) {

                where += " AND ";

                where += "(EMP." + PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID] + "=" + srcDPUpload.getEmployeePosition() + ")";
            }

            if (where.length() > 0) {
                
                sql = sql + where;
                
            }
            
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            
            while(rs.next()){
                int size = rs.getInt(1);
                return size;
            }
            
        }catch(Exception E){
            System.out.println("Exception "+E.toString());
        }finally{
            DBResultSet.close(dbrs);
        }
        
        return 0;
        
    }
    
    /**
     * @Author  Roy Andika
     * @param   srcDPUpload
     * @param   start
     * @param   recordToGet
     * @Desc    Untuk mendapatkan list balancing Dp
     * @return
     */
    public static Vector listDPBalancing(SrcDPUpload srcDPUpload, int start, int recordToGet){

        DBResultSet dbrs = null;

        Vector resultDp = new Vector();

        try {
            
            String sql = "SELECT EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+
                    ",EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] +
                    ",EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME] +
                    ",EMP." + PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE] +
                    ",EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] +
                    " FROM "+ PstEmployee.TBL_HR_EMPLOYEE+" EMP WHERE EMP."+
                    PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]+" = "+PstEmployee.NO_RESIGN;                    

            String where = "";

            if (srcDPUpload.getEmployeeName().length() > 0) {

                where += " AND ";

                where += " (EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME] + " LIKE \'%" + srcDPUpload.getEmployeeName() + "%\')";
            }
            if (srcDPUpload.getEmployeePayroll().length() > 0) {

                where += " AND ";

                where += "(EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] + " LIKE \'%" + srcDPUpload.getEmployeePayroll() + "%\')";
            }
            if (srcDPUpload.getEmployeeCategory() > 0) {

                where += " AND ";

                where += "(EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID] + "=" + srcDPUpload.getEmployeeCategory() + ")";
            }
            if (srcDPUpload.getEmployeeDepartement() > 0) {

                where += " AND ";

                where += "(EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] + "=" + srcDPUpload.getEmployeeDepartement() + ")";
            }
            if (srcDPUpload.getEmployeeSection() > 0) {

                where += " AND ";

                where += "(EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID] + "=" + srcDPUpload.getEmployeeSection() + ")";
            }
            if (srcDPUpload.getEmployeePosition() > 0) {

                where += " AND ";

                where += "(EMP." + PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID] + "=" + srcDPUpload.getEmployeePosition() + ")";
            }

            if (where.length() > 0) {
                
                sql = sql + where;
                
            }

            sql = sql + " ORDER BY " + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM];
/*
            if (start == 0 && recordToGet == 0) {
                sql = sql + "";
            } else {
                sql = sql + " LIMIT " + start + "," + recordToGet;
            }
*/
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {

                listDpUpload objListDpUpload = new listDpUpload();

                objListDpUpload.setEmployee_id(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]));
                objListDpUpload.setEmployee_num(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]));
                objListDpUpload.setFull_name(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]));
                objListDpUpload.setDepartment_id(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]));
                objListDpUpload.setCommencing_date(rs.getDate(PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]));
                
                Vector result1 = new Vector();
                Vector result2 = new Vector();
                Vector result3 = new Vector();
                Vector result4 = new Vector();                           
                
                try{
                    result1 = getDpStockBalancing(objListDpUpload.getEmployee_id());
                }catch(Exception E){
                    System.out.println("Exception "+E.toString());
                }
                
                try{
                    result2 = getDpUploadBalancing(objListDpUpload.getEmployee_id());
                }catch(Exception E){
                    System.out.println("Exception "+E.toString());
                }
                
                try{
                    result3 = getDPBalancingUploadNull(objListDpUpload.getEmployee_id());
                }catch(Exception E){
                    System.out.println("Exception "+E.toString());    
                }        
                
                try{
                    result4 = listDpUploadNull(objListDpUpload.getEmployee_id());
                }catch(Exception E){
                    System.out.println("Exception "+E.toString());    
                }

              if( (result1 == null || result1.size() <1 ) && (result2 == null || result2.size() <1 ) && (result3 == null || result3.size() <1 ) && 
                      (result4 == null || result4.size()<1 ) ){
                        // jika tidak mempunyai stock DP maka add stock kosong supaya muncul di editor
                        listDpUpload objlistDpUpload = new listDpUpload();
                        
                        listDpUpload lstDpUpload = new listDpUpload();                                                
                        objlistDpUpload.setEmployee_id(objListDpUpload.getEmployee_id());
                        objlistDpUpload.setEmployee_num(objListDpUpload.getEmployee_num());
                        objlistDpUpload.setFull_name(objListDpUpload.getFull_name());
                        objlistDpUpload.setDepartment_id(objListDpUpload.getDepartment_id());
                        objlistDpUpload.setCommencing_date(objListDpUpload.getCommencing_date());                        
                        objlistDpUpload.setDp_stock_id(0);
                        objlistDpUpload.setDp_upload_id(0);                        
                        resultDp.add(objlistDpUpload);
              }
                
                if(result1 != null && result1.size() > 0){
                    
                    for(int idx = 0 ; idx < result1.size() ; idx++){
                        
                        listDpUpload objlistDpUpload = new listDpUpload();
                        
                        listDpUpload lstDpUpload = new listDpUpload();
                        lstDpUpload = (listDpUpload)result1.get(idx);
                        
                        objlistDpUpload.setEmployee_id(objListDpUpload.getEmployee_id());
                        objlistDpUpload.setEmployee_num(objListDpUpload.getEmployee_num());
                        objlistDpUpload.setFull_name(objListDpUpload.getFull_name());
                        objlistDpUpload.setDepartment_id(objListDpUpload.getDepartment_id());
                        objlistDpUpload.setCommencing_date(objListDpUpload.getCommencing_date());
                        
                        objlistDpUpload.setDp_stock_id(lstDpUpload.getDp_stock_id());
                        objlistDpUpload.setDp_upload_id(lstDpUpload.getDp_upload_id());
                        
                        resultDp.add(objlistDpUpload);
                        
                    }
                    
                }
                
                if(result2 != null && result2.size() > 0){
                    
                    for(int idx = 0 ; idx < result2.size() ; idx++){
                        
                        listDpUpload objlistDpUpload = new listDpUpload();
                        listDpUpload lstDpUpload = new listDpUpload();
                        lstDpUpload = (listDpUpload)result2.get(idx);
                        
                        objlistDpUpload.setEmployee_id(objListDpUpload.getEmployee_id());
                        objlistDpUpload.setEmployee_num(objListDpUpload.getEmployee_num());
                        objlistDpUpload.setFull_name(objListDpUpload.getFull_name());
                        objlistDpUpload.setDepartment_id(objListDpUpload.getDepartment_id());
                        objlistDpUpload.setCommencing_date(objListDpUpload.getCommencing_date());
                        
                        objlistDpUpload.setDp_stock_id(lstDpUpload.getDp_stock_id());
                        objlistDpUpload.setDp_upload_id(lstDpUpload.getDp_upload_id());
                        
                        resultDp.add(objlistDpUpload);
                        
                    }                    
                }
                
                if(result3 != null && result3.size() > 0){
                    
                    for(int idx = 0 ; idx < result3.size() ; idx++){
                        
                        listDpUpload objlistDpUpload = new listDpUpload();
                        listDpUpload lstDpUpload = new listDpUpload();
                        lstDpUpload = (listDpUpload)result3.get(idx);
                        
                        objlistDpUpload.setEmployee_id(objListDpUpload.getEmployee_id());
                        objlistDpUpload.setEmployee_num(objListDpUpload.getEmployee_num());
                        objlistDpUpload.setFull_name(objListDpUpload.getFull_name());
                        objlistDpUpload.setDepartment_id(objListDpUpload.getDepartment_id());
                        objlistDpUpload.setCommencing_date(objListDpUpload.getCommencing_date());
                        
                        objlistDpUpload.setDp_stock_id(lstDpUpload.getDp_stock_id());
                        objlistDpUpload.setDp_upload_id(lstDpUpload.getDp_upload_id());
                        
                        resultDp.add(objlistDpUpload);
                        
                    }                    
                }
                
                if(result4 != null && result4.size() > 0){
                    
                    for(int idx = 0 ; idx < result4.size() ; idx++){
                        
                        listDpUpload objlistDpUpload = new listDpUpload();
                        
                        listDpUpload lstDpUpload = new listDpUpload();
                        lstDpUpload = (listDpUpload)result4.get(idx);
                        
                        objlistDpUpload.setEmployee_id(objListDpUpload.getEmployee_id());
                        objlistDpUpload.setEmployee_num(objListDpUpload.getEmployee_num());
                        objlistDpUpload.setFull_name(objListDpUpload.getFull_name());
                        objlistDpUpload.setDepartment_id(objListDpUpload.getDepartment_id());
                        objlistDpUpload.setCommencing_date(objListDpUpload.getCommencing_date());
                        
                        objlistDpUpload.setDp_stock_id(lstDpUpload.getDp_stock_id());
                        objlistDpUpload.setDp_upload_id(lstDpUpload.getDp_upload_id());
                        
                        resultDp.add(objlistDpUpload);
                        
                    }                    
                }

            }

            return resultDp;

        } catch (Exception e) {
            System.out.println("Exception " + e.toString());
        } finally {
            DBResultSet.close(dbrs);
        }
        return null;
    }
    
    
    /**
     * @Author  Roy Andika
     * @param   employeeId
     * @return
     * @Desc    Untuk mendapatkan dp stock dan dp upload dimana data stock aktif dan dp upload sudah diproses
     */
    public static Vector getDpStockBalancing(long employeeId){
        
        DBResultSet dbrs = null;
        try{            
            String sql = "SELECT DISTINCT DPM."+PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_STOCK_ID]+
                    ",0 as DP_UPLOAD_ID FROM "+PstDpStockManagement.TBL_DP_STOCK_MANAGEMENT+" DPM "+
                    " LEFT JOIN "+PstDPUpload.TBL_DP_UPLOAD+" DPU ON DPM."+PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_STOCK_ID]+" = "+
                    " DPU."+PstDPUpload.fieldNames[PstDPUpload.FLD_DP_STOCK_ID]+" WHERE DPM."+PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EMPLOYEE_ID]+" = "+
                    employeeId+" AND "+PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_STATUS]+" = "+PstDpStockManagement.DP_STS_AKTIF+
                    " AND DPU."+PstDPUpload.fieldNames[PstDPUpload.FLD_DATA_STATUS]+" = "+PstDPUpload.FLD_DOC_STATUS_PROCESS;                    
            
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            
            Vector result = new Vector();
            
            while(rs.next()){
                
                listDpUpload objListDpUpload = new listDpUpload();
                objListDpUpload.setDp_stock_id(rs.getLong(PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_STOCK_ID]));
                objListDpUpload.setDp_upload_id(rs.getLong(PstDPUpload.fieldNames[PstDPUpload.FLD_DP_UPLOAD_ID]));

                result.add(objListDpUpload);
            }
            
            return result;
            
        }catch(Exception E){
            System.out.println("Exception "+E.toString());
        }finally{
            DBResultSet.close(dbrs);
        }
        
        return null;
        
    }
    
    /**
     * @Author Roy Andika
     * @param employeeId
     * @return
     * @Desc Untuk mendapatkan dp stock dan dp upload dimana data stock aktif dan dp upload belum ada
     */    
    
    public static Vector getDPBalancingUploadNull(long employeeId){
        
        DBResultSet dbrs = null;
        
        try{
            String sql = "SELECT DPM."+PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_STOCK_ID]+
                    ", 0 as DP_UPLOAD_ID FROM "+PstDpStockManagement.TBL_DP_STOCK_MANAGEMENT+" DPM "+
                    " LEFT JOIN "+PstDPUpload.TBL_DP_UPLOAD+" DPU ON DPM."+PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_STOCK_ID]+" = "+
                    " DPU."+PstDPUpload.fieldNames[PstDPUpload.FLD_DP_STOCK_ID]+" WHERE DPM."+PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EMPLOYEE_ID]+" = "+
                    employeeId+" AND "+PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_STATUS]+" = "+PstDpStockManagement.DP_STS_AKTIF+
                    " AND DPU."+PstDPUpload.fieldNames[PstDPUpload.FLD_DP_UPLOAD_ID]+" is null ";                    
            
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            
            Vector result = new Vector();
            
            while(rs.next()){
                listDpUpload objListDpUpload = new listDpUpload();
                objListDpUpload.setDp_stock_id(rs.getLong(PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_STOCK_ID]));
                objListDpUpload.setDp_upload_id(rs.getLong(PstDPUpload.fieldNames[PstDPUpload.FLD_DP_UPLOAD_ID]));

                result.add(objListDpUpload);
            }
            
            return result;
                    
        }catch(Exception E){
            System.out.println("Exception "+E.toString());
        }finally{
            DBResultSet.close(dbrs);
        }
        
        return null;
    }
    
    /**
     * @Author Roy A
     * @return
     * @Desc : UNTUK MENDAPTAKAN LIST EMPLOYEE DIMANA STOCK DP NULL DAN DP UPLOAD EXIST
     */
    public static Vector listDpUploadNull(long employee_id){
                
        DBResultSet dbrs = null;
        
        try{
                        
            String sql = "SELECT 0 as DP_STOCK_ID ,"+PstDPUpload.fieldNames[PstDPUpload.FLD_DP_UPLOAD_ID]+" FROM "+
                    PstDPUpload.TBL_DP_UPLOAD+" WHERE "+PstDPUpload.fieldNames[PstDPUpload.FLD_EMPLOYEE_ID]+"="+employee_id+" AND "+
                    PstDPUpload.fieldNames[PstDPUpload.FLD_DP_STOCK_ID]+" = 0";
            
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            
            Vector result = new Vector();
            
            while(rs.next()){
                listDpUpload objListDpUpload = new listDpUpload();
                objListDpUpload.setDp_stock_id(rs.getLong(PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_STOCK_ID]));
                objListDpUpload.setDp_upload_id(rs.getLong(PstDPUpload.fieldNames[PstDPUpload.FLD_DP_UPLOAD_ID]));

                result.add(objListDpUpload);
            }
            
            return result;
            
            
        }catch(Exception E){
            System.out.println("Exception "+E.toString());
        }finally{
            DBResultSet.close(dbrs);
        }
        
        return null;
    }
    
    /**
     * @Author Roy Andika
     * @param employeeId
     * @return
     * @Desc Untuk mendapatkan dp stock dan dp upload dimana data stock belum ada
     */
    public static Vector getDpUploadBalancing(long employeeId){
        
        DBResultSet dbrs = null;
        
        try{
            
            
            String sql = "SELECT DPM."+PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_STOCK_ID]+
                    ",DPU."+PstDPUpload.fieldNames[PstDPUpload.FLD_DP_UPLOAD_ID]+" FROM "+PstDPUpload.TBL_DP_UPLOAD+" DPU "+
                    " LEFT JOIN "+PstDpStockManagement.TBL_DP_STOCK_MANAGEMENT+" DPM ON DPU."+PstDPUpload.fieldNames[PstDPUpload.FLD_DP_STOCK_ID]+
                    " = DPM."+PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_STOCK_ID]+
                    " WHERE DPM."+PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EMPLOYEE_ID]+" = "+employeeId+" AND "+
                    " ( DPM."+PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_STOCK_ID]+" is null OR DPM."+
                    PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_STOCK_ID]+" = 0 ) ";
                    
            
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            
            Vector result = new Vector();
            
            while(rs.next()){
                
                listDpUpload objListDpUpload = new listDpUpload();
                objListDpUpload.setDp_stock_id(rs.getLong(PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_STOCK_ID]));
                objListDpUpload.setDp_upload_id(rs.getLong(PstDPUpload.fieldNames[PstDPUpload.FLD_DP_UPLOAD_ID]));

                result.add(objListDpUpload);
            }
            
            return result;
            
        }catch(Exception E){
            System.out.println("Exception "+E.toString());
        }finally{
            DBResultSet.close(dbrs);
        }
        
        return null;
        
    }
    
    

    /*
     * Desc untuk mendapatkan dp upload 
     * value yang dikirim balik value 
     */
    public static DPUpload getDpUpload(long employeeOID) {

        String where = PstDPUpload.fieldNames[PstDPUpload.FLD_EMPLOYEE_ID] + "=" + employeeOID + " AND " +
                PstDPUpload.fieldNames[PstDPUpload.FLD_DATA_STATUS] + "=" + PstDPUpload.FLD_DOC_STATUS_NOT_PROCESS;

        Vector result = new Vector();

        result = PstDPUpload.list(0, 0, where, null);

        DPUpload dpUpload = new DPUpload();

        if (result == null || result.size() <= 0) {

            return null;

        } else {

            dpUpload = (DPUpload) result.get(0);
            return dpUpload;

        }
    }

    public static long updateDPNote(long stockDPId, String note) {

        DBResultSet dbrs = null;

        try {

            String sql = "UPDATE " + PstDpStockManagement.TBL_DP_STOCK_MANAGEMENT +
                    " SET " + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_NOTE] + "='" + note + "'" +
                    " WHERE " + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_STOCK_ID] + "=" + stockDPId;

            DBHandler.execUpdate(sql);

            return stockDPId;

        } catch (Exception e) {

            System.out.println("EXCEPTION " + e.toString());

        } finally {

            DBResultSet.close(dbrs);

        }

        return 0;
    }

    public static DPUpload getDpUploadAqqDate(long employee_id, Date aq_date) {

        String where = PstDPUpload.fieldNames[PstDPUpload.FLD_EMPLOYEE_ID] + " = " + employee_id +
                " AND " + PstDPUpload.fieldNames[PstDPUpload.FLD_DP_STOCK_ID] + " = " + PstDPUpload.FLD_VAL_DP_STOCK_ID_EMPTY +
                " AND " + PstDPUpload.fieldNames[PstDPUpload.FLD_DATA_STATUS] + " = " + PstDPUpload.FLD_DOC_STATUS_NOT_PROCESS +
                " AND " + PstDPUpload.fieldNames[PstDPUpload.FLD_DP_AQ_DATE] + " = '" + Formater.formatDate(aq_date, "yyyy-MM-dd") + " 00:00:00'";

        Vector result = PstDPUpload.list(0, 0, where, null);

        if (result == null || result.size() <= 0) {

            return null;

        } else {

            return (DPUpload) result.get(0);

        }
    }

    public static DPUpload getDPUploadByDPId(long employee_id, long dp_stock_id) {

        if (employee_id == 0 || dp_stock_id == 0) {

            return null;

        }

        String where = PstDPUpload.fieldNames[PstDPUpload.FLD_EMPLOYEE_ID] + " = " + employee_id +
                " AND " + PstDPUpload.fieldNames[PstDPUpload.FLD_DP_STOCK_ID] + " = " + dp_stock_id +
                " AND " + PstDPUpload.fieldNames[PstDPUpload.FLD_DATA_STATUS] + " = " + PstDPUpload.FLD_DOC_STATUS_NOT_PROCESS;

        Vector result = PstDPUpload.list(0, 0, where, null);

        if (result == null || result.size() <= 0) {

            return null;

        } else {

            return (DPUpload) result.get(0);

        }
    }

    public static long getOIDOpnAktif(long stock_Oid) {

        DBResultSet dbrs = null;
        try {

            String sql = "SELECT " + PstDPUpload.fieldNames[PstDPUpload.FLD_DP_UPLOAD_ID] + " FROM " + PstDPUpload.TBL_DP_UPLOAD +
                    " WHERE " + PstDPUpload.fieldNames[PstDPUpload.FLD_DP_STOCK_ID] + " = " + stock_Oid + " AND " +
                    PstDPUpload.fieldNames[PstDPUpload.FLD_DATA_STATUS] + " = " + PstDPUpload.FLD_DOC_STATUS_NOT_PROCESS;

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            long dpOid = 0;
            while (rs.next()) {
                dpOid = rs.getLong(1);
                return dpOid;
            }

        } catch (Exception e) {
            System.out.println("EXCEPTION " + e.toString());
        } finally {
            DBResultSet.close(dbrs);
        }

        return 0;

    }

    public static long getDpOpnDateProcess(long employeeOid, Date opnDt){

        DBResultSet dbrs = null;

        try {
            String sql = "SELECT " + PstDPUpload.fieldNames[PstDPUpload.FLD_DP_UPLOAD_ID] + " FROM " + PstDPUpload.TBL_DP_UPLOAD +
                    " WHERE " + PstDPUpload.fieldNames[PstDPUpload.FLD_OPNAME_DATE] + " = '" + Formater.formatDate(opnDt, "yyyy-MM-dd") + "' " +
                    " AND " + PstDPUpload.fieldNames[PstDPUpload.FLD_EMPLOYEE_ID] + " = " + employeeOid +
                    " AND " + PstDPUpload.fieldNames[PstDPUpload.FLD_DATA_STATUS] + " = " + PstDPUpload.FLD_DOC_STATUS_PROCESS;

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            long dpOid = 0;
            while (rs.next()) {
                dpOid = rs.getLong(1);
                return dpOid;
            }
        } catch (Exception e) {
            System.out.println("EXCEPTION :::: " + e.toString());
        } finally {
            DBResultSet.close(dbrs);
        }

        return 0;
    }

    public static long getDpOpnDateProcess(long employeeOid, Date opnDt, long dpOid) {

        DBResultSet dbrs = null;

        try {
            String sql = "SELECT " + PstDPUpload.fieldNames[PstDPUpload.FLD_DP_UPLOAD_ID] + " FROM " + PstDPUpload.TBL_DP_UPLOAD +
                    " WHERE " + PstDPUpload.fieldNames[PstDPUpload.FLD_OPNAME_DATE] + " = '" + Formater.formatDate(opnDt, "yyyy-MM-dd") + "' " +
                    " AND " + PstDPUpload.fieldNames[PstDPUpload.FLD_EMPLOYEE_ID] + " = " + employeeOid +
                    " AND " + PstDPUpload.fieldNames[PstDPUpload.FLD_DP_UPLOAD_ID] + " = " + dpOid +
                    " AND " + PstDPUpload.fieldNames[PstDPUpload.FLD_DATA_STATUS] + " = " + PstDPUpload.FLD_DOC_STATUS_PROCESS;

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            long dpOID = 0;
            while (rs.next()) {
                dpOID = rs.getLong(1);
                return dpOID;
            }
        } catch (Exception e) {
            System.out.println("EXCEPTION :::: " + e.toString());
        } finally {
            DBResultSet.close(dbrs);
        }

        return 0;
    }

    public static long getDpOpnDateProcessStock(long employeeOid, long StockOid, Date opnameDate) {

        DBResultSet dbrs = null;

        try {
            String sql = "SELECT " + PstDPUpload.fieldNames[PstDPUpload.FLD_DP_UPLOAD_ID] + " FROM " + PstDPUpload.TBL_DP_UPLOAD +
                    " WHERE " + PstDPUpload.fieldNames[PstDPUpload.FLD_EMPLOYEE_ID] + " = " + employeeOid +
                    " AND " + PstDPUpload.fieldNames[PstDPUpload.FLD_DP_STOCK_ID] + " = " + StockOid +
                    " AND " + PstDPUpload.fieldNames[PstDPUpload.FLD_OPNAME_DATE] + " = '" + Formater.formatDate(opnameDate, "yyyy-MM-dd") + " 00:00:00'" +
                    " AND " + PstDPUpload.fieldNames[PstDPUpload.FLD_DATA_STATUS] + " = " + PstDPUpload.FLD_DOC_STATUS_PROCESS;

            
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            long dpOID = 0;
            while (rs.next()) {
                dpOID = rs.getLong(1);
                return dpOID;
            }
        } catch (Exception e) {
            System.out.println("EXCEPTION :::: " + e.toString());
        } finally {
            DBResultSet.close(dbrs);
        }

        return 0;
    }

    public static long getOIDOpnameSama(long employee_id, Date owningDt) {

        DBResultSet dbrs = null;

        try {

            String sql = "SELECT " + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_STOCK_ID] + " FROM " + PstDpStockManagement.TBL_DP_STOCK_MANAGEMENT +
                    " WHERE " + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EMPLOYEE_ID] + " = " + employee_id +
                    " AND " + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_OWNING_DATE] + " = '" + Formater.formatDate(owningDt, "yyyy-MM-dd") + "'";

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            long dpStockOID = 0;

            while (rs.next()) {
                dpStockOID = rs.getLong(1);
                return dpStockOID;
            }

        } catch (Exception e) {
            System.out.println("EXCEPTION :::: " + e.toString());
        } finally {
            DBResultSet.close(dbrs);
        }

        return 0;
    }

    public static long getOIDOpnameSama_2(long employee_id, Date owningDt) {

        DBResultSet dbrs = null;

        try {

            String sql = "SELECT " + PstDPUpload.fieldNames[PstDPUpload.FLD_DP_UPLOAD_ID] + " FROM " +
                    PstDPUpload.TBL_DP_UPLOAD + " WHERE " +
                    PstDPUpload.fieldNames[PstDPUpload.FLD_EMPLOYEE_ID] + " = " + employee_id + " AND " +
                    PstDPUpload.fieldNames[PstDPUpload.FLD_DP_AQ_DATE] + " = '" + Formater.formatDate(owningDt, "yyyy-MM-dd") + " 00:00:00'";

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            long dpStockUpload = 0;
            while (rs.next()) {

                dpStockUpload = rs.getLong(1);
                return dpStockUpload;

            }

        } catch (Exception e) {
            System.out.println("EXCEPTION :::: " + e.toString());
        } finally {
            DBResultSet.close(dbrs);
        }

        return 0;

    }
}
