/**
 * Created by IntelliJ IDEA.
 * User: gadnyana
 * Date: Jul 22, 2004
 * Time: 1:17:44 PM
 * To change this template use Options | File Templates.
 */
package com.dimata.harisma.session.attendance;

import com.dimata.harisma.entity.attendance.LLStockManagement;
import com.dimata.harisma.entity.attendance.LlStockTaken;
import com.dimata.qdep.db.DBResultSet;
import com.dimata.qdep.db.DBHandler;
import com.dimata.harisma.entity.masterdata.LeavePeriod;
import com.dimata.harisma.entity.masterdata.PstLeavePeriod;
import com.dimata.harisma.entity.masterdata.Period;
import com.dimata.harisma.entity.masterdata.PstPeriod;
import com.dimata.harisma.entity.employee.PstEmployee;
import com.dimata.harisma.entity.attendance.PstLLStockManagement;

import com.dimata.harisma.entity.attendance.PstLlStockReport;
import com.dimata.harisma.entity.attendance.PstLlStockTaken;
import com.dimata.harisma.entity.leave.ll.LLAppMain;
import com.dimata.harisma.entity.leave.ll.PstLLAppMain;
import com.dimata.harisma.session.leave.ll.SessLLAppMain;
import com.dimata.util.Formater;
import java.util.Vector;
import java.util.Date;
import java.sql.ResultSet;

public class SessLongLeave 
{

    /** gadnyana
     * pencarian long lave sesuai dengan periode parameter
     * @param oidPeriod
     * @return
     */
    public Vector getLongLeave(long oidPeriod) {
        DBResultSet dbrs = null;
        Vector list = new Vector();
        try {
            LeavePeriod leavePeriod = new LeavePeriod();
            leavePeriod = PstLeavePeriod.fetchExc(oidPeriod);
            String sQL = "SELECT " +
                    " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + ", " +
                    " SUM(LL." + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_QTY_RESIDUE] + ") AS RESIDUE" +
                    " FROM " + PstLLStockManagement.TBL_LL_STOCK_MANAGEMENT + " LL" +
                    " INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " EMP ON " +
                    " LL." + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_EMPLOYEE_ID] +
                    " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] +
                    " WHERE YEAR(LL." + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_OWNING_DATE] + ")=" + (leavePeriod.getEndDate().getYear() - 1) +
                    " AND LL." + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_LL_STATUS] + "!=" + PstLLStockManagement.LL_STS_EXPIRED +
                    " AND LL." + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_LL_STATUS] + "!=" + PstLLStockManagement.LL_STS_NOT_AKTIF +
                    " GROUP BY LL." + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_EMPLOYEE_ID] + " ORDER BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM];

            dbrs = DBHandler.execQueryResult(sQL);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                Vector vt = new Vector(1, 1);
                vt.add("" + rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]));
                vt.add("" + rs.getInt("RESIDUE"));

                list.add(vt);
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("ERR : SessLongLeave: getLongLeave > " + e.toString());
        } finally {
            DBResultSet.close(dbrs);
        }
        return list;
    }


    /**
     * pencarian long lave sesuai dengan periode parameter
     * @param oidPeriod
     * @return
     * @created by Edhy
     */
    public static Vector getLongLeaveStock() 
    {
        DBResultSet dbrs = null;
        Vector list = new Vector();
        try 
        {
            String sQL = "SELECT EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + ", " +
                    " SUM(LL." + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_QTY_RESIDUE] + ") AS RESIDUE" +
                    " FROM " + PstLLStockManagement.TBL_LL_STOCK_MANAGEMENT + " LL" +
                    " INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " EMP ON " +
                    " LL." + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_EMPLOYEE_ID] +
                    " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] +
                    " WHERE YEAR(LL." + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_OWNING_DATE] + ") <= " + ((new Date()).getYear() + 1900) +
                    " AND LL." + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_LL_STATUS] + "!=" + PstLLStockManagement.LL_STS_EXPIRED +
                    " AND LL." + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_LL_STATUS] + "!=" + PstLLStockManagement.LL_STS_NOT_AKTIF +
                    " GROUP BY LL." + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_EMPLOYEE_ID] + 
                    " ORDER BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM];

//            System.out.println("SQL get LongLeaveStock : " + sQL);
            dbrs = DBHandler.execQueryResult(sQL);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) 
            {
                Vector vt = new Vector(1, 1);
                vt.add("" + rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]));                
                vt.add("" + rs.getInt("RESIDUE"));

                list.add(vt);
            }
            rs.close();
        }
        catch (Exception e) 
        {
            System.out.println("ERR : SessLongLeave: getLongLeave > " + e.toString());
        }
        finally 
        {
            DBResultSet.close(dbrs);
        }
        return list;
    }

    
    /** gadnyana
     * pencarian long lave sesuai dengan periode, dan oid employee parameter
     * @param oidPeriod
     * @param empOid
     * @return
     */
    public Vector getLongLeave(long oidPeriod, long empOid) {
        DBResultSet dbrs = null;
        Vector list = new Vector();
        try {
            LeavePeriod leavePeriod = new LeavePeriod();
            leavePeriod = PstLeavePeriod.fetchExc(oidPeriod);
            String sQL = "SELECT " +
                    " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + ", " +
                    " SUM(LL." + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_QTY_RESIDUE] + ") AS RESIDUE" +
                    " FROM " + PstLLStockManagement.TBL_LL_STOCK_MANAGEMENT + " LL" +
                    " INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " EMP ON " +
                    " LL." + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_EMPLOYEE_ID] +
                    " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] +
                    " WHERE YEAR(LL." + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_OWNING_DATE] + ") = " + (leavePeriod.getEndDate().getYear() - 1) +
                    " AND LL." + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_LL_STATUS] + "!=" + PstLLStockManagement.LL_STS_EXPIRED +
                    " AND LL." + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_LL_STATUS] + "!=" + PstLLStockManagement.LL_STS_NOT_AKTIF +
                    " AND LL." + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_EMPLOYEE_ID] + "=" + empOid +
                    " GROUP BY LL." + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_EMPLOYEE_ID] + " ORDER BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM];

            dbrs = DBHandler.execQueryResult(sQL);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                Vector vt = new Vector(1, 1);
                vt.add("" + rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]));
                vt.add("" + rs.getInt("RESIDUE"));

                list.add(vt);
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("ERR : SessLongLeave: getLongLeave > " + e.toString());
        } finally {
            DBResultSet.close(dbrs);
        }
        return list;
    }

    /**
     * @param empOid
     * @return
     * @created by Edhy
     */    
    public int getCurrentLLStock(long empOid) 
    {
        DBResultSet dbrs = null;        
        int result = 0;
        try 
        {
            String sQL = "SELECT SUM(" + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_QTY_RESIDUE] + ")" + 
                         " FROM " + PstLLStockManagement.TBL_LL_STOCK_MANAGEMENT +                          
                         " WHERE " + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_EMPLOYEE_ID] + 
                         " = " + empOid + 
                         " GROUP BY " + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_EMPLOYEE_ID];                        
            
//            System.out.println("getCurrentLLStock.sql : " + sQL);             
            dbrs = DBHandler.execQueryResult(sQL);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) 
            {
                result = rs.getInt(1);
                break;
            }
            rs.close();
        }
        catch (Exception e) 
        {
            System.out.println("Exc on " + getClass().getName() + " : " + e.toString());
        }
        finally 
        {
            DBResultSet.close(dbrs);
        }
        return result;
    }    
    
    /** gadnyana
     * method yang di panggila di jsp
     * untuk pencarian ll
     * @return vector
     */
    public static Vector prosessGetLongLeave(long oidPeriod) {
        Vector vt = new Vector(1, 1);
        try {
            if (oidPeriod != 0) {
                Period period = PstPeriod.fetchExc(oidPeriod);
                LeavePeriod leavePeriod = PstLeavePeriod.cekAlreadyExistLeavePeriod(period.getStartDate());
                SessLongLeave sessLongLeave = new SessLongLeave();
                vt = sessLongLeave.getLongLeave(leavePeriod.getOID());
            }
        } catch (Exception e) {
            System.out.println("ERR > prosessGetLongLeave : " + e.toString());
        }
        return vt;
    }

    public static Vector prosessGetLongLeave(long oidPeriod, long oidEmp) {
        Vector vt = new Vector(1, 1);
        try {
            if (oidPeriod != 0) {
                Period period = PstPeriod.fetchExc(oidPeriod);
                LeavePeriod leavePeriod = PstLeavePeriod.cekAlreadyExistLeavePeriod(period.getStartDate());
                SessLongLeave sessLongLeave = new SessLongLeave();
                vt = sessLongLeave.getLongLeave(leavePeriod.getOID(),oidEmp);
            }
        } catch (Exception e) {
            System.out.println("ERR > prosessGetLongLeave : " + e.toString());
        }
        return vt;
    }
    
    //Mencari jumah stock LL
    //Artha
    //untuk di hardrock
    public static synchronized int getLLBalance(long employeeId, Date currDate){
        int balance = 0;
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT SUM("+PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_QTY_RESIDUE]
                    +") FROM " + PstLLStockManagement.TBL_LL_STOCK_MANAGEMENT
                    +" WHERE "+PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_EMPLOYEE_ID]
                    +" = "+employeeId
                    +" AND "+PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_EXPIRED_DATE]
                    +" >= '"+Formater.formatDate(currDate, "yyyy-MM-dd")+"'";
                 
           // System.out.println("PstLLAppMain sql : "+sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while(rs.next()) {
                balance = rs.getInt(1);
            }
            rs.close();            
        }catch(Exception e) {
            System.out.println(e);
        }finally {
            DBResultSet.close(dbrs);
        }  
        //Mengurangi degan yg suadah taken
        int requestRes = getLLAppMainReq(employeeId, currDate);
        balance = balance-requestRes;
        
        return balance;
    }
    
    //Mecari jml LL yg sedang di ajukan
    private static synchronized int getLLAppMainReq(long employeeId, Date currDate){
        int req= 0;
        /**
         SELECT SUM( REQUEST_QTY ) , SUM( TAKEN_QTY )
FROM hr_ll_app
WHERE EMPLOYEE_ID =100133
AND END_DATE >= '2009-03-27'
GROUP BY `EMPLOYEE_ID`
LIMIT 0 , 30
         */
        
        DBResultSet dbrs = null;
        try 
        {
            String query = "SELECT SUM("+PstLLAppMain.fieldNames[PstLLAppMain.FLD_REQUEST_QTY]+")"
                +",SUM("+PstLLAppMain.fieldNames[PstLLAppMain.FLD_TAKEN_QTY]+")"
                +" FROM "+PstLLAppMain.TBL_LL_APP_MAIN
                +" WHERE "+PstLLAppMain.fieldNames[PstLLAppMain.FLD_EMPLOYEE_ID]
                +" = "+employeeId
                +" AND "+PstLLAppMain.fieldNames[PstLLAppMain.FLD_END_DATE]
                +" >= "+Formater.formatDate(currDate, "yyyy-MM-dd")
                +" GROUP BY "+PstLLAppMain.fieldNames[PstLLAppMain.FLD_EMPLOYEE_ID]
                ;
//            System.out.println("\tSessLongLeave.getLLAppMainReq.sql : " + sQL);             
            dbrs = DBHandler.execQueryResult(query);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) 
            {
                int reqs = 0;
                int taken = 0;
                reqs = rs.getInt(1);
                taken = rs.getInt(2);
                req = reqs-taken;
                break;
            }
            rs.close();
        }
        catch (Exception e) 
        {
            System.out.println("Exc on : " + e.toString());
        }
        finally 
        {
            DBResultSet.close(dbrs);
        }
        return req;
    }
    
    /**
     * Melakukan process taken LL berdasarkan employee dan tanggal pengambilan
     * fungsi ini di pergunakna untuk taken LL yang telah di approve
     * @param employeeId: OID employee
     * @param takenDate : tanggal pengambilan
     * @return boolean : true if success and false if not success
     * @autor : artha
     */
    public static synchronized void processTakenLLApproved(Date takenDate){
        //mencari LL Stock yang di ajukan
        Vector vListAppMain = SessLLAppMain.listLLAppMain(takenDate);
        for(int i=0;i<vListAppMain.size();i++){
            LLAppMain llMain = new LLAppMain();
            llMain = (LLAppMain)vListAppMain.get(i);
            //Jika sudah di approve
            if(llMain.getApproval3Id()>0){
                Date dateStart = llMain.getStartDate();
                while(dateStart.getTime() <= takenDate.getTime()){
                    boolean isTaken = PstLlStockTaken.existLlStockTaken(llMain.getEmployeeId(), dateStart);
                    if(isTaken==false){
                        boolean isSuccess  = takenLLApproved(llMain.getEmployeeId(), dateStart);
                        //Menyesuaikan jml LL Taken
                        llMain.setTakenQty(llMain.getTakenQty()+1);
                        try{
                            PstLLAppMain.updateExc(llMain);
                        }catch(Exception ex){}
                    }
                    dateStart.setDate(dateStart.getDate()+1);
                }
            }
        }
    }
    
    /**
     * Melakukan process taken LL berdasarkan employee dan tanggal pengambilan
     * fungsi ini di pergunakna untuk taken LL yang belum di approve
     * @param employeeId: OID employee
     * @param takenDate : tanggal pengambilan
     * @return boolean : true if success and false if not success
     * @autor : artha
     */
    public static synchronized void processTakenLLUnApproved(long employeeId, Date takenDate){
            boolean isTaken = PstLlStockTaken.existLlStockTaken(employeeId, takenDate);
            if(isTaken==false){
                boolean isSuccess  = takenLLApproved(employeeId, takenDate);
                LLAppMain llAppMain = new LLAppMain();
                llAppMain = SessLLAppMain.getLLAppMain(employeeId, takenDate);                 
                //Menyesuaikan jml LL Taken
                if(llAppMain.getOID()>0){
                    llAppMain.setTakenQty(llAppMain.getTakenQty()+1);
                    try{
                        PstLLAppMain.updateExc(llAppMain);
                    }catch(Exception ex){}
                }
            }
    }
    
    
    
    /**
     * Menambil LL dengan memgupdate LL Stock Man dan mmengenerate LL Stock Taken
     */
    private static boolean takenLLApproved(long employeeId, Date takenDate){
        boolean isSuccess = false;
        LLStockManagement llStockMan = getLLStockMan(employeeId, takenDate);
        //Mengambil LL
        isSuccess = generateLLStockTaken(employeeId, llStockMan, takenDate, 1);
        return isSuccess;
    }
    
    
    /*
     *Mencari LL stock Man yang dapat dipergunakan 
     */
    private static LLStockManagement getLLStockMan(long employeeId, Date takenDate){
        String whereClause = PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_EMPLOYEE_ID]
                +" = "+employeeId
                +" AND "+PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_EXPIRED_DATE]
                +" >= '"+Formater.formatDate(takenDate, "yyyy-MM-dd")+"'"
                +" AND "+PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_QTY_RESIDUE]
                +" > 0";
                ;
        Vector vLLStockMan = new Vector(1,1);
        vLLStockMan = PstLLStockManagement.list(0, 0, whereClause, PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_OWNING_DATE]+" ASC ");
        LLStockManagement llStockMan = new LLStockManagement();
        if(vLLStockMan.size()>0){
            llStockMan = (LLStockManagement)vLLStockMan.get(0);
        }
        
        return llStockMan;
    }
    
    
    /**
     * Membuat LL stock taken
     */
    private static boolean generateLLStockTaken(long employeeId, LLStockManagement llStockMan, Date takenDate, int takenQty){
        boolean isSuccess = false;
        LlStockTaken llStock = new LlStockTaken();
        llStock.setEmployeeId(employeeId);
        if(llStockMan.getOID()>0){
            llStockMan.setQtyUsed(llStockMan.getQtyUsed()+1);
            llStockMan.setQtyResidue(llStockMan.getQtyResidue()-1);
            
            if(llStockMan.getQtyResidue()<=0){
                llStockMan.setLLStatus(PstLLStockManagement.LL_STS_TAKEN);
            }
            
            try{
                PstLLStockManagement.updateExc(llStockMan);
            }catch(Exception ex){}
            
            llStock.setLlStockId(llStockMan.getOID());
            llStock.setPaidDate(takenDate);
        }
        
        llStock.setTakenQty(takenQty);
        llStock.setTakenDate(takenDate);
        
        try{
            PstLlStockTaken.insertExc(llStock);
            isSuccess = true;
        }catch(Exception ex){}
        
        return isSuccess;
    }
    
    /**
     * Proses pembayaran LL yang masih utang
     */
    public static boolean proccessPaid(long employeeId){
        boolean isSuccess = false;
        Vector vLLTakenUnpaid = new Vector(1,1);
        vLLTakenUnpaid = PstLlStockTaken.getLlPayable(employeeId);
        for(int i=0;i<vLLTakenUnpaid.size();i++){
            LLStockManagement llMan = getLLStockMan(employeeId, new Date());
            if(llMan.getOID()>0){
                LlStockTaken llTaken = (LlStockTaken)vLLTakenUnpaid.get(i);
                paidLLTaken(llMan, llTaken, new Date());    
            }else{
                //Tidak ada data
                break;
            }
        }
        return isSuccess;
    }
    /**
     * keterangan: untuk membarayrkan cuti LL yg masih minus
     * @param employeeId
     * @return 
     */
     public static boolean proccessPaidLL(long employeeId){
        boolean isSuccess = false;
        Vector vLLTakenUnpaid = new Vector(1,1);
        vLLTakenUnpaid = PstLlStockTaken.getLlUnpaid(employeeId);
        for(int i=0;i<vLLTakenUnpaid.size();i++){
            LLStockManagement llMan = PstLlStockTaken.getLLPaidStockManagement(employeeId);
            if(llMan.getOID()>0){
                LlStockTaken llTaken = (LlStockTaken)vLLTakenUnpaid.get(i);
                paidLLTaken(llMan, llTaken, new Date());    
            }else{
                //Tidak ada data
                break;
            }
        }
        return isSuccess;
    }
    
    /**
     * Mengupdate date yang terbaru
     */
    private static void paidLLTaken(LLStockManagement llMan, LlStockTaken llTaken, Date paidDate){
        if(llMan.getQtyResidue()>=llTaken.getTakenQty()){
            llMan.setQtyResidue(llMan.getQtyResidue()-llTaken.getTakenQty());
            llMan.setQtyUsed(llMan.getQtyUsed()+llTaken.getTakenQty());
            
            if(llMan.getQtyResidue()<=0){
                llMan.setLLStatus(PstLLStockManagement.LL_STS_TAKEN);
            }
            
            
            llTaken.setLlStockId(llMan.getOID());
            llTaken.setPaidDate(paidDate);
            
            try{
                PstLLStockManagement.updateExc(llMan);
                PstLlStockTaken.updateExc(llTaken);
            }catch(Exception ex){}
            
        }else{
            
        }
    }
    
}
