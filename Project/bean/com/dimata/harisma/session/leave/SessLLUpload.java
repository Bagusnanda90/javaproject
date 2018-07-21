/*
 * Khusus untuk sanur paradise
 */

package com.dimata.harisma.session.leave;

import com.dimata.harisma.entity.attendance.LLStockManagement;
import com.dimata.harisma.entity.attendance.LlStockExpired;
import com.dimata.harisma.entity.attendance.LlStockTaken;
import com.dimata.harisma.entity.attendance.PstLLStockManagement;
import com.dimata.harisma.entity.attendance.PstLlStockExpired;
import com.dimata.harisma.entity.attendance.PstLlStockTaken;
import com.dimata.harisma.entity.employee.Employee;
import com.dimata.harisma.entity.employee.PstEmployee;
import com.dimata.harisma.entity.leave.LLUpload;
import com.dimata.harisma.entity.leave.PstLLUpload;
import com.dimata.harisma.entity.search.SrcLLUpload;
import com.dimata.harisma.entity.leave.I_Leave;
import com.dimata.harisma.entity.masterdata.EmpCategory;
import com.dimata.harisma.entity.masterdata.Level;
import com.dimata.harisma.entity.masterdata.PstEmpCategory;
import com.dimata.harisma.entity.masterdata.PstLevel;
import com.dimata.system.entity.system.PstSystemProperty;
import com.dimata.qdep.db.DBException;
import com.dimata.qdep.db.DBHandler;
import com.dimata.qdep.db.DBResultSet;
import com.dimata.util.Formater;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Hashtable;
import java.util.Vector;

/**
 *
 * @author gArtha
 */
public class SessLLUpload {
    
    /**
     * @desc menghitung banyak ll yang diperoleh (entitled) sampai dengan tanggal tertentu
     * @param id employee
     * @param tanggal perhitungan
     * @param periode tahun
     * @return banyak ll yang entitled pada tanggal tertentu
     */
    public static int getLLEntitled(long empId,Date opnameDate,int lengthPeriodInMonth){
        int iLLEntitled = 0;
        
        I_Leave leaveConfig = null;           
        try {
            leaveConfig = (I_Leave)(Class.forName(PstSystemProperty.getValueByName("LEAVE_CONFIG")).newInstance());            
        }
        catch(Exception e) {
            System.out.println("Exception : " + e.getMessage());
        }
        
        try {
            Employee objEmployee = new Employee();
            Date dCommencingDate = new Date();
            objEmployee = PstEmployee.fetchExc(empId);
            if (objEmployee.getCommencingDate() != null) {
                dCommencingDate = new Date(objEmployee.getCommencingDate().getTime());
                int iCommencingYear = 0;
                iCommencingYear = dCommencingDate.getYear()+lengthPeriodInMonth/12;
                dCommencingDate.setYear(iCommencingYear);
               // dCommencingDate.setYear(opnameDate.getYear());
                // Jika waktu commencing date lebih kecil dari waktu terkini
                // atau dengan kata lain, earned ytd akan muncul jika waktu commencing
                // pegawai telah lewat dari waktu sekarang
                if (dCommencingDate.getTime() <= opnameDate.getTime()) {
                    Date dateCommInt5 = getStartPeriodDate(empId, opnameDate, leaveConfig.getIntervalLLinMonths()[I_Leave.INTERVAL_LL_5_YEAR]);
                    if(leaveConfig.getIntervalLLinMonths()[I_Leave.INTERVAL_LL_8_YEAR]==lengthPeriodInMonth){
                        if(dateCommInt5.getTime()<=dCommencingDate.getTime()){
                            iLLEntitled = leaveConfig.getLLEntile(leaveConfig.getLevel(empId), leaveConfig.getCategory(empId),lengthPeriodInMonth);
                        }
                    }else{
                        iLLEntitled = leaveConfig.getLLEntile(leaveConfig.getLevel(empId), leaveConfig.getCategory(empId),lengthPeriodInMonth);
                    }
                }
            }
        } catch (DBException ex) {
            ex.printStackTrace();
        }
        return iLLEntitled;
    }
    
    /**
     * @desc mencari tanggal start periode dari employee dan tanggal diopname
     * @param id employee
     * @param tanggal perhitungan
     * @param durasi dari periode yang dihitung, 5 tahun/8 tahun
     * @return tanggal start period dari masa opname
     */
    public static java.util.Date getStartPeriodDate(long empId,Date opnameDate,int durationPeriodInMonth){
        //System.out.println("::: "+empId +" :::: "+opnameDate);
        
        int durationInYear = durationPeriodInMonth/12;
        Date dateStartPer = null; 
        try {
            Employee objEmployee = new Employee();
            objEmployee = PstEmployee.fetchExc(empId);
            Date tempDate = objEmployee.getCommencingDate();
            if(durationInYear<0 ){
               return opnameDate;
            }
            if(tempDate!=null){
                int x=0;
                while(tempDate.getYear()<opnameDate.getYear() && x<50){ // keluar jika loop melebihi 50
                    tempDate.setYear(tempDate.getYear()+durationInYear);
                    x++;
                }
                //System.err.println("::::::::::::::::: YEAR NEAR COMM ::::: "+tempDate+" :::: OPNAME DATE :::::"+opnameDate);
                if(tempDate.getYear() == opnameDate.getYear()){
                    if(opnameDate.getMonth()>=objEmployee.getCommencingDate().getMonth()){
                        if(opnameDate.getMonth()==objEmployee.getCommencingDate().getMonth()){
                            if(opnameDate.getDate()<objEmployee.getCommencingDate().getDate()){
                                tempDate.setYear(tempDate.getYear()-durationInYear);
                            }
                        }
                    }else{
                        tempDate.setYear(tempDate.getYear()-durationInYear);
                    }
                }//Tahun employee pada kelipatan 5 lebih besar
                else{
                    tempDate.setYear(tempDate.getYear()-durationInYear);
                }  

                dateStartPer = (Date)tempDate.clone();
            }
        } catch (DBException ex) {
            ex.printStackTrace();
        }
        //System.out.println("SessLLUpload getStartPeriodDate ::::::: tanggal : "+dateStartPer);
        return dateStartPer;
    }
    
    
    public static Vector getLLUploadData(SrcLLUpload srcLLUpload,int start,int recordToGet){
        Vector result = new Vector();        
        String where = "";
        Hashtable hashTable;
        
        I_Leave leaveConfig = null;           
        try {
            leaveConfig = (I_Leave)(Class.forName(PstSystemProperty.getValueByName("LEAVE_CONFIG")).newInstance());            
        }
        catch(Exception e) {
            System.out.println("Exception : " + e.getMessage());
        }
        
        if(srcLLUpload.getOpnameDate()!=null){
            if(where.length()>0){
                where += " AND ";
            }
            where += PstLLUpload.fieldNames[PstLLUpload.FLD_OPNAME_DATE] 
                    + " = \"" + Formater.formatDate(srcLLUpload.getOpnameDate(), "yyyy-MM-dd")+"\"";
        }
        
        //result = PstLLUpload.list(start, recordToGet, where, null);
        result = PstLLUpload.list(0,0, where, null);
        
        //Melakukan pencarian pada data ll taken 
        //jika tidak ditemukan pada ll upload
        if(result.size()<=0){
            Vector vEmployee = new Vector(1,1);
            vEmployee = getDataEmployee(srcLLUpload, start, recordToGet);
            for(int i=0;i<vEmployee.size();i++){
                LLUpload objLLUpload = new LLUpload();
                Employee objEmployee = (Employee)vEmployee.get(i);
                Date dateStartPer = getStartPeriodDate(objEmployee.getOID(), srcLLUpload.getOpnameDate(), (leaveConfig.getIntervalLLinMonths()[I_Leave.INTERVAL_LL_5_YEAR]));
                objLLUpload.setEmployeeId(objEmployee.getOID());
                if(dateStartPer!=null){
                    for(int j=0;j<5;j++){
                        Date dateStartYear = (Date)dateStartPer.clone();
                        dateStartPer.setYear(dateStartYear.getYear()+i);

                        Date dateEndYear = (Date)dateStartYear.clone();
                        dateEndYear.setDate(dateEndYear.getDate()-1);
                        //TAKEN_DATE BETWEEN "2008-09-14" AND "2009-01-14"
                        String whereTaken = PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_TAKEN_DATE]
                                +" BETWEEN \""+Formater.formatDate(dateStartYear, "yyyy-MM-dd")
                                +"\" AND \""+Formater.formatDate(dateEndYear, "yyyy-MM-dd")+"\"";
                        int countTaken = PstLlStockTaken.getCount(whereTaken);
                        switch(j){
                            case 0: 
                                objLLUpload.setLlTakenYear1(countTaken);
                                break;
                            case 1: 
                                objLLUpload.setLlTakenYear2(countTaken);
                                break;
                            case 2: 
                                objLLUpload.setLlTakenYear3(countTaken);
                                break;
                            case 3: 
                                objLLUpload.setLlTakenYear4(countTaken);
                                break;
                            case 4: 
                                objLLUpload.setLlTakenYear5(countTaken);
                                break;
                        }
                    }
                }
                result.add(objLLUpload);
            }
        }
        
        return result;        
    }
    /**
     * @desc mengambil data LL yang akan diset
     * @param srcLLUpload menampung data yang menjadi acuan dalam pencarian
     * @param start menentukan awal dari pencarian data
     * @param recordToGet menentukan banyak data yang akan diambil
     * @return Vector
     */
    public static Vector searchLLData(SrcLLUpload srcLLUpload, int start, int recordToGet){
        Vector vResult = new Vector(1,1);
        Vector vEmployee = new Vector(1,1);
        Hashtable hLLUpload = new Hashtable();
        
        vEmployee = getDataEmployee(srcLLUpload, start, recordToGet);
        hLLUpload = getLLUploaded(srcLLUpload, start, recordToGet);
        Vector VLLUpload = getLLUploadData(srcLLUpload, start, recordToGet); 
        
        System.out.println("SessLLUpload.searchLLUpload [Banyak data employee] ::::::::: "+vEmployee.size());
        
        for(int i=0;i<vEmployee.size();i++){
            Employee objEmployee = new Employee();
            LLUpload objLLUpload = new LLUpload();
            
            objEmployee = (Employee)vEmployee.get(i);
            
            Vector temp = new Vector(1,1);
            temp.add(objEmployee);
            
            boolean status = false;
            
            for(int v=0; v<VLLUpload.size(); v++){
                LLUpload LLUploaded =(LLUpload)VLLUpload.get(v);
                if(LLUploaded.getEmployeeId()==objEmployee.getOID()){
                  temp.add(LLUploaded);                    
                  status=true;                  
                  break;
                }  
            }
            if(status == false){                
                
                temp.add(objLLUpload);                
            }   
            
            temp.add(objLLUpload);
            temp.add(srcLLUpload);
            vResult.add(temp);
        }
        
        return vResult;
    }
    
    //getLLStockManagementAktif
    
    public static long getStockID(long employeeID){
        long StockID = 0;        
        DBResultSet dbrs = null;
        
        try{
            String sql = "SELECT "+PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_LL_STOCK_ID]+" FROM "+PstLLStockManagement.TBL_LL_STOCK_MANAGEMENT +
                    " WHERE "+PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_EMPLOYEE_ID]+" = "+employeeID+" AND "
                    +PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_LL_STATUS]+" = "+PstLLStockManagement.LL_STS_AKTIF;
            dbrs = DBHandler.execQueryResult(sql);
            System.out.println("SQL GET STOCK ID "+sql);
	    ResultSet rs = dbrs.getResultSet();
	    while(rs.next()) {
		StockID = rs.getLong(1);	
	    }
            rs.close();
            return StockID;
            
        }catch(Exception e){
            System.out.println(" [EXCEPTION ] :::: "+e.toString());
        }finally {
	    DBResultSet.close(dbrs);
        }    
        return StockID;
    }
    
    
    /**
     * @desc mencari data employee berdasarkan acuan pencarian
     * @param srcLLUpload menampung data yang menjadi acuan dalam pencarian
     * @param start menentukan awal dari pencarian data
     * @param recordToGet menentukan banyak data yang akan diambil
     * @return Vector
     */
    private static Vector getDataEmployee(SrcLLUpload srcLLUpload, int start, int recordToGet){
        
        DBResultSet dbResulset = null;
        try{
            String sql = "SELECT "
                    + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    +","+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
                    +","+PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                    +","+PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID]
                    +","+PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]
                    +" FROM "+PstEmployee.TBL_HR_EMPLOYEE;

            String where = "";
            
            if (srcLLUpload.getEmployeeName().length() > 0) {
                if(where.length()>0){
                    where += " AND ";
                }
                where += " (" + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME] + " LIKE \'%" + srcLLUpload.getEmployeeName() + "%\')";
            }
            if (srcLLUpload.getEmployeePayroll().length() > 0) {
                if(where.length()>0){
                    where += " AND ";
                }
                where += "(" + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] + " LIKE \'%" + srcLLUpload.getEmployeePayroll() + "%\')";
            }
            if (srcLLUpload.getEmployeeCategory() > 0) {
                if(where.length()>0){
                    where += " AND ";
                }
                where += "(" + PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID] + "=" + srcLLUpload.getEmployeeCategory() + ")";
            }
            //if (srcLLUpload.getEmployeeDepartement() > 0) {
            if (srcLLUpload.getEmployeeDepartement()!= 0) {
                if(where.length()>0){
                    where += " AND ";
                }
                where += "(" + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] + "=" + srcLLUpload.getEmployeeDepartement() + ")";
            }
            if (srcLLUpload.getEmployeeSection() > 0) {
                if(where.length()>0){
                    where += " AND ";
                    
                }
                where += "" +
                        "(" + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID] + "=" + srcLLUpload.getEmployeeSection() + ")";
            }
            if (srcLLUpload.getEmployeePosition() > 0) {
                if(where.length()>0){
                    where += " AND ";
                }
                where += "(" + PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID] + "=" + srcLLUpload.getEmployeePosition() + ")";
            }
            if(where.length()>0){
                where = " WHERE "+where+" AND "+PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]+" = "+PstEmployee.NO_RESIGN;
            }else{
                where = " WHERE "+PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]+" = "+PstEmployee.NO_RESIGN;
            }
            
            sql = sql + where; 
            
            if(start == 0 && recordToGet == 0)
                sql = sql + "";
            else
                sql = sql + " LIMIT " + start + ","+ recordToGet ;   
            
            System.out.println("[SQL] SessLLUpload.getDataEmployee ::::::::"+sql);
            
            dbResulset = DBHandler.execQueryResult(sql);
            ResultSet rs = dbResulset.getResultSet();
            Vector result = new Vector(1,1);
            while(rs.next()){
                Employee objEmployee = new Employee();
                objEmployee.setOID(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]));
                objEmployee.setEmployeeNum(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]));
                objEmployee.setFullName(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]));
                objEmployee.setCommencingDate(rs.getDate(PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]));
                objEmployee.setEmpCategoryId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID]));
                result.add(objEmployee);
            }
            rs.close();
            return result;
        }catch(Exception e) {
            System.out.println(e);
        }finally {
            DBResultSet.close(dbResulset);
        }
        return new Vector(1,1);
    }
    
     /**
     * @desc mencari data ll yang telah diupload berdasarkan acuan pencarian
     * @param srcLLUpload menampung data yang menjadi acuan dalam pencarian
     * @param start menentukan awal dari pencarian data
     * @param recordToGet menentukan banyak data yang akan diambil
     * @return Vector
     */
    private static Hashtable getLLUploaded(SrcLLUpload srcLLUpload, int start, int recordToGet){
        Vector result = new Vector(1,1);
        String where = "";
        Hashtable hashTable;
        
        I_Leave leaveConfig = null;           
        try {
            leaveConfig = (I_Leave)(Class.forName(PstSystemProperty.getValueByName("LEAVE_CONFIG")).newInstance());            
        }
        catch(Exception e) {
            System.out.println("Exception : " + e.getMessage());
        }
        
        if(srcLLUpload.getOpnameDate()!=null){
            if(where.length()>0){
                where += " AND ";
            }
            where += PstLLUpload.fieldNames[PstLLUpload.FLD_OPNAME_DATE] 
                    + " = \"" + Formater.formatDate(srcLLUpload.getOpnameDate(), "yyyy-MM-dd")+"\"";
        }
        
        result = PstLLUpload.list(start, recordToGet, where, null);
        
        //Melakukan pencarian pada data ll taken 
        //jika tidak ditemukan pada ll upload
        if(result.size()<=0){
            Vector vEmployee = new Vector(1,1);
            vEmployee = getDataEmployee(srcLLUpload, start, recordToGet);
            for(int i=0;i<vEmployee.size();i++){
                LLUpload objLLUpload = new LLUpload();
                Employee objEmployee = (Employee)vEmployee.get(i);
                Date dateStartPer = getStartPeriodDate(objEmployee.getOID(), srcLLUpload.getOpnameDate(), (leaveConfig.getIntervalLLinMonths()[I_Leave.INTERVAL_LL_5_YEAR]));
                objLLUpload.setEmployeeId(objEmployee.getOID());
                if(dateStartPer!=null){
                    for(int j=0;j<5;j++){
                        Date dateStartYear = (Date)dateStartPer.clone();
                        dateStartPer.setYear(dateStartYear.getYear()+i);

                        Date dateEndYear = (Date)dateStartYear.clone();
                        dateEndYear.setDate(dateEndYear.getDate()-1);
                        //TAKEN_DATE BETWEEN "2008-09-14" AND "2009-01-14"
                        String whereTaken = PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_TAKEN_DATE]
                                +" BETWEEN \""+Formater.formatDate(dateStartYear, "yyyy-MM-dd")
                                +"\" AND \""+Formater.formatDate(dateEndYear, "yyyy-MM-dd")+"\"";
                        int countTaken = PstLlStockTaken.getCount(whereTaken);
                        switch(j){
                            case 0: 
                                objLLUpload.setLlTakenYear1(countTaken);
                                break;
                            case 1: 
                                objLLUpload.setLlTakenYear2(countTaken);
                                break;
                            case 2: 
                                objLLUpload.setLlTakenYear3(countTaken);
                                break;
                            case 3: 
                                objLLUpload.setLlTakenYear4(countTaken);
                                break;
                            case 4: 
                                objLLUpload.setLlTakenYear5(countTaken);
                                break;
                        }
                    }
                }
                result.add(objLLUpload);
            }
        }
        
        
        hashTable = new Hashtable(result.size());
        for(int i=0;i<result.size();i++){
            LLUpload objLLUpload = new LLUpload();
            objLLUpload = (LLUpload)result.get(i);
            hashTable.put(String.valueOf(objLLUpload.getEmployeeId()), objLLUpload);
        }
        
        return hashTable;
    }
    
    public synchronized static Vector saveLLUploadBalance(Vector vLLUpload){
    I_Leave leaveConfig=null;            
    try {
         leaveConfig = (I_Leave)(Class.forName(PstSystemProperty.getValueByName("LEAVE_CONFIG")).newInstance());            
    }
    catch(Exception e) {
        System.out.println("Exception : " + e.getMessage());
    }
        Vector vLLUploadId  = new Vector(1,1);
        try{
            Date dateOpname = new Date();

            String[] emp_id       = (String[])vLLUpload.get(0);
            String[] llUpload_id  = (String[])vLLUpload.get(1);
            String[] data_status  = (String[])vLLUpload.get(2);
            boolean[] is_process  = (boolean[])vLLUpload.get(3);
            dateOpname = (Date)vLLUpload.get(4);            
            String[] new_previous = (String[])vLLUpload.get(5);
            String[] new_ll = (String[])vLLUpload.get(6);
            String[] new_qty = (String[])vLLUpload.get(7);
            String[] new_taken = (String[])vLLUpload.get(8);   
            String[] new_ll_2 = (String[])vLLUpload.get(9);
            
            for(int i=0;i<emp_id.length;i++){
                if(is_process[i]){
                    LLUpload objLLUpload = new LLUpload();
                    objLLUpload.setEmployeeId(Long.parseLong(emp_id[i]));
                    long vStockID = getStockID(Long.parseLong(emp_id[i]));
                    
                    objLLUpload.setLLStockID(vStockID);                    
                    objLLUpload.setDataStatus(Integer.parseInt(data_status[i]));
                    objLLUpload.setOpnameDate(dateOpname);
                    objLLUpload.setLastPerToClearLL(Float.parseFloat(new_previous[i]));
                    objLLUpload.setNewLL(Float.parseFloat(new_ll[i]));
                    objLLUpload.setLLQty(Float.parseFloat(new_qty[i]));
                    objLLUpload.setLlTakenYear1(Float.parseFloat(new_taken[i]));
                 //update by satrya 2013-09-03
                  if(leaveConfig!=null && leaveConfig.getLLShowEntile2() && new_ll_2[i]!=null && new_ll_2[i].length()>0){
                    objLLUpload.setNewLL2(Float.parseFloat(new_ll_2[i]));
                  }   
                    if(Long.parseLong(llUpload_id[i])>0){ //Jika al upload telah ada maka akan diupdate, jika tidak maka akan disimpan yang baru
                        try {
                            objLLUpload.setOID(Long.parseLong(llUpload_id[i]));
                            PstLLUpload.updateExc(objLLUpload);
                            vLLUploadId.add(String.valueOf(Long.parseLong(llUpload_id[i])));
                        } catch (DBException ex) {
                            ex.printStackTrace();
                            System.out.println("EXCEPTION "+ex.toString());
                        }
                    }else{
                        try {
                            long id = PstLLUpload.insertExc(objLLUpload);
                            vLLUploadId.add(String.valueOf(id));
                        } catch (DBException ex) {
                            ex.printStackTrace();
                            System.out.println("EXCEPTION "+ex.toString());
                        }
                    }
                }
            }
        }catch(Exception  ex){
            System.out.println("[ERROR] SessLLUpload.saveLLUpload  :::::::: "+ex.toString());
        }
        return vLLUploadId;
    }
    
    
    // untuk mendapatkan time entitle date II
    public static String getEntitleII(Date entitle_I,int interval_2){
        
        DBResultSet dbrs = null;
        try{
            
            String sql = "SELECT DATE_ADD("+Formater.formatDate(entitle_I,"yyyy-MM-dd")+", INTERVAL "+interval_2+" MONTH )";
            
            dbrs = DBHandler.execQueryResult(sql);
            
            ResultSet rs = dbrs.getResultSet();
            
            while(rs.next()){
                String date = rs.getString(1);
                return date;
            }
            
        }catch(Exception e){
            System.out.println("EXCEPTION "+e.toString());
        }
        return null;
        
    }
    
    // Untuk mendapatkan time entitle date
    
    public static Date getHaveStockLL(long employee_id,int interval_1){
        
        Employee employee = new Employee();
        
        try{
            employee = PstEmployee.fetchExc(employee_id);
        }catch(Exception e){
            System.out.println("Exception SessLLUpload.getHaveStockLL :::"+ e.toString());
        }
        
        Date tmp_ent_date       = employee.getCommencingDate();
        boolean entitle_time    = false;
        boolean entitle_true    = false;
        
        entitle_true = DATE_DIFF(new Date(),employee.getCommencingDate());  //untuk mendapatkan kondisi commencing date apakah lebih kecil ketimbang current date
        
        if(entitle_true){
            
            for(int i = 0 ; i < 100 ; i++){
                
                entitle_time = getTimeEntitle(tmp_ent_date,interval_1);
                
                if(entitle_time){
                    return tmp_ent_date;
                }
                
                tmp_ent_date = DATEADD(tmp_ent_date,interval_1);
            }
            
        }
        
        return null;
    }


    public static boolean getMinLLTime(Date date_add,int interval,Date opnameDate){
        
        DBResultSet dbrs = null;
        
        try{
            
            String sql = "SELECT DATE_ADD('"+Formater.formatDate(date_add,"yyyy-MM-dd")+"',INTERVAL "+interval+
                    " MONTH ) <= '"+Formater.formatDate(opnameDate,"yyyy-MM-dd")+"'"; 
            
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            
            while(rs.next()){
                int result = rs.getInt(1);
                if(result == 1)
                    return true;
                
            }
                    
        }catch(Exception e){
            System.out.println("Exception "+e.toString());
        }
        
        return false;
        
    }
            
    
    public static boolean getTimeEntitle(Date input,int interval){
        
        DBResultSet dbrs = null;
        
        try{
            
            String sql = "SELECT CURRENT_DATE >= '"+Formater.formatDate(input,"yyyy-MM-dd")+"' AND CURRENT_DATE < DATE_ADD('"+
                    Formater.formatDate(input,"yyyy-MM-dd")+"', INTERVAL "+interval+" MONTH )";
            
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            
            while(rs.next()){
                
                int result = rs.getInt(1);
                if(result == 1)
                    return true;
                
            }
            
        }catch(Exception e){
            System.out.println("Exception "+e.toString());
        }finally{
            DBResultSet.close(dbrs);
        }
        
        return false;
    }
    
    
    public static boolean DATE_DIFF(Date date1,Date date2){
        
        DBResultSet dbrs = null;
        
        try{
            String sql = "SELECT DATEDIFF('"+Formater.formatDate(date1,"yyyy-MM-dd")+"','"+Formater.formatDate(date2,"yyyy-MM-dd")+"')";
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            
            while(rs.next()){
                
                int result = rs.getInt(1);
                if(result > 0)
                    return true;
                
            }
            
        }catch(Exception e){
            System.out.println("Exception SessLLUpload.DATE_DIFF(Date date1,Date date2) :::"+ e.toString());
        }
        
        return false;
    }
    
    /*
     *   
     * 
     * 
     */
    
    public static Date DATEADD(Date date_add,int interval){
        
        DBResultSet dbrs = null;
       
        try{
           String sql = "SELECT DATE_ADD('"+Formater.formatDate(date_add,"yyyy-MM-dd")+"',INTERVAL "+interval+" MONTH )"; 
           dbrs = DBHandler.execQueryResult(sql);
           ResultSet rs = dbrs.getResultSet();
           
           while(rs.next()){
               Date result = rs.getDate(1);
               return result;
           }
           
        }catch(Exception e){
            System.out.println("Exception :: "+e.toString());
        }
        
        return null;
        
    }
    
    /**
     * @desc method untuk menyimpan data pada database (LLUpload)
     *      - jika data telah ada sebelumnya, maka data akan diupdate
     *      - jika data belum ada sebelumnya, maka data akan dibuat
     * @param vector dari data yang diperlukan dalam proses
     * @return 
     */
    public synchronized static Vector saveLLUpload(Vector vLLUpload){
        Vector vLLUploadId  = new Vector(1,1);
        try{
            Date dateOpname = new Date();

            String[] emp_id       = (String[])vLLUpload.get(0);
            String[] llUpload_id  = (String[])vLLUpload.get(1);
            String[] taken_year_1 = (String[])vLLUpload.get(2);
            String[] taken_year_2 = (String[])vLLUpload.get(3);
            String[] taken_year_3 = (String[])vLLUpload.get(4);
            String[] taken_year_4 = (String[])vLLUpload.get(5);
            String[] taken_year_5 = (String[])vLLUpload.get(6);
            String[] data_status  = (String[])vLLUpload.get(7);
            boolean[] is_process  = (boolean[])vLLUpload.get(8);
            dateOpname = (Date)vLLUpload.get(9);
            
            for(int i=0;i<emp_id.length;i++){
                if(is_process[i]){
                    LLUpload objLLUpload = new LLUpload();
                    objLLUpload.setEmployeeId(Long.parseLong(emp_id[i]));
                    long vStockID = getStockID(Long.parseLong(emp_id[i]));
                    
                    if(vStockID == 0)
                        vStockID = 0;
                    
                    objLLUpload.setLLStockID(vStockID);                    
                    objLLUpload.setDataStatus(Integer.parseInt(data_status[i]));
                    objLLUpload.setOpnameDate(dateOpname);
                    objLLUpload.setLlTakenYear1(Float.parseFloat(taken_year_1[i]));
                    objLLUpload.setLlTakenYear2(Float.parseFloat(taken_year_2[i]));
                    objLLUpload.setLlTakenYear3(Float.parseFloat(taken_year_3[i]));
                    objLLUpload.setLlTakenYear4(Float.parseFloat(taken_year_4[i]));
                    objLLUpload.setLlTakenYear5(Float.parseFloat(taken_year_5[i]));
                    
                    //Menampilkan informasi object ll upload
                    /*System.out.println(objLLUpload.getEmployeeId());
                    System.out.println(objLLUpload.getLlTakenYear1());
                    System.out.println(objLLUpload.getLlTakenYear2());
                    System.out.println(objLLUpload.getLlTakenYear3());
                    System.out.println(objLLUpload.getLlTakenYear4());
                    System.out.println(objLLUpload.getLlTakenYear5());
                    */
                    
                    if(Long.parseLong(llUpload_id[i])>0){ //Jika al upload telah ada maka akan diupdate, jika tidak maka akan disimpan yang baru
                        try {
                            objLLUpload.setOID(Long.parseLong(llUpload_id[i]));
                            PstLLUpload.updateExc(objLLUpload);
                            vLLUploadId.add(String.valueOf(Long.parseLong(llUpload_id[i])));
                        } catch (DBException ex) {
                            ex.printStackTrace();
                        }
                    }else{
                        try {
                            long id = PstLLUpload.insertExc(objLLUpload);
                            vLLUploadId.add(String.valueOf(id));
                        } catch (DBException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
        }catch(Exception  ex){
            System.out.println("[ERROR] SessLLUpload.saveLLUpload  :::::::: "+ex.toString());
        }
        return vLLUploadId;
    }
    
    
    public static LLStockManagement getValueStockManagement(long empID){
        
        String where = PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_EMPLOYEE_ID]+" = "+empID
                +" AND "+PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_LL_STATUS]+" = "+PstLLStockManagement.LL_STS_AKTIF;
        Vector VStockManagement = PstLLStockManagement.list(0, 1, where, null);
        
        if(VStockManagement.size() > 0){
            LLStockManagement LLStockManagementData = (LLStockManagement)VStockManagement.get(0);
            return LLStockManagementData;
        }        
        return null;
    }
    
    /**
     * create by satrya 2013-07-04
     * @return 
     */
     public static Hashtable getHashValueStockManagement(){
        
        String where = PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_LL_STATUS]+" = "+PstLLStockManagement.LL_STS_AKTIF;
        String order = PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_EMPLOYEE_ID] + " ASC ";
                
        Vector VStockManagement = PstLLStockManagement.listStockManagementLL(0, 0, where, order);
        Hashtable hashStockManagement = new Hashtable();
        if(VStockManagement.size() > 0){
           for(int x=0;x<VStockManagement.size();x++){
            LLStockManagement LLStockManagementData = (LLStockManagement)VStockManagement.get(x);
             hashStockManagement.put(LLStockManagementData.getEmployeeId(), LLStockManagementData);
           }
        }        
        return hashStockManagement;
    }
    
    /**
     * @desc method untuk menyimpan data pada database (LLUpload)
     *      - jika data telah ada sebelumnya, maka data akan diupdate
     *      - jika data belum ada sebelumnya, maka data akan dibuat
     * @param vector dari data yang diperlukan dalam proses
     * @return 
     */
    public synchronized static Vector saveLLUploadHR(Vector vLLUpload){
        Vector vLLUploadId = new Vector(1,1);
        try{
            Date dateOpname = new Date();

            String[] emp_id       = (String[])vLLUpload.get(0);
            String[] llUpload_id  = (String[])vLLUpload.get(1);
            String[] taken_year_1 = (String[])vLLUpload.get(2);
            String[] taken_year_2 = (String[])vLLUpload.get(3);
            String[] taken_year_3 = (String[])vLLUpload.get(4);
            String[] taken_year_4 = (String[])vLLUpload.get(5);
            String[] taken_year_5 = (String[])vLLUpload.get(6);
            String[] data_status  = (String[])vLLUpload.get(7);
            boolean[] is_process   = (boolean[])vLLUpload.get(8);
            dateOpname = (Date)vLLUpload.get(9);
            String[] stock = (String[])vLLUpload.get(10);
            
            for(int i=0;i<emp_id.length;i++){
                if(is_process[i]){
                    LLUpload objLLUpload = new LLUpload();
                    objLLUpload.setEmployeeId(Long.parseLong(emp_id[i]));
                    objLLUpload.setDataStatus(Integer.parseInt(data_status[i]));
                    objLLUpload.setOpnameDate(dateOpname);
                    objLLUpload.setLlTakenYear1(Float.parseFloat(taken_year_1[i]));
                    objLLUpload.setLlTakenYear2(Float.parseFloat(taken_year_2[i]));
                    objLLUpload.setLlTakenYear3(Float.parseFloat(taken_year_3[i]));
                    objLLUpload.setLlTakenYear4(Float.parseFloat(taken_year_4[i]));
                    objLLUpload.setLlTakenYear5(Float.parseFloat(taken_year_5[i]));
                    objLLUpload.setStock(Integer.parseInt(stock[i]));
                    
                    //Menampilkan informasi object ll upload
                  /*System.out.println(objLLUpload.getEmployeeId());
                    System.out.println(objLLUpload.getLlTakenYear1());
                    System.out.println(objLLUpload.getLlTakenYear2());
                    System.out.println(objLLUpload.getLlTakenYear3());
                    System.out.println(objLLUpload.getLlTakenYear4());
                    System.out.println(objLLUpload.getLlTakenYear5());
                    */
                    
                    if(Long.parseLong(llUpload_id[i])>0){ //Jika al upload telah ada maka akan diupdate, jika tidak maka akan disimpan yang baru
                        try {
                            objLLUpload.setOID(Long.parseLong(llUpload_id[i]));
                            PstLLUpload.updateExc(objLLUpload);
                            
                            vLLUploadId.add(String.valueOf(Long.parseLong(llUpload_id[i])));
                        } catch (DBException ex) {
                            ex.printStackTrace();
                        }
                    }else{
                        try {
                            long id = PstLLUpload.insertExc(objLLUpload);
                            
                            vLLUploadId.add(String.valueOf(id));
                        } catch (DBException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
        }catch(Exception  ex){
            System.out.println("[ERROR] SessLLUpload.saveLLUpload  :::::::: "+ex.toString());
        }
       
        return vLLUploadId;
    }
    
    
    /////////////proses opname/////////////////////
    
     /**
     * @desc mencari ll stock management dari periode dan id employee
     * @param id employee
     * @param tanggal perolehan
     * @return id dari hr_ll_stock_management
     */
    private static LLStockManagement getLLStockManagement(long employeeId,Date ownDate){
        LLStockManagement objLLStockManagement = new LLStockManagement();
        
        DBResultSet dbrs;                  
        String strWhere = PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_EMPLOYEE_ID] 
                +" = " + employeeId 
                +" AND " + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_OWNING_DATE] 
                +" = \""+Formater.formatDate(ownDate, "yyyy-MM-dd 00:00:00")+"\"";
        
        Vector vLLStockManagement = new Vector();
        vLLStockManagement = PstLLStockManagement.list(0,1, strWhere, PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_OWNING_DATE]+" ASC");
        
        if(vLLStockManagement.size()>0){
            objLLStockManagement = (LLStockManagement)vLLStockManagement.get(0);
        }else{
            return new LLStockManagement();
        }
        return objLLStockManagement;
    }
    
    /**
     * @desc mengopname ll stock taken
     * @param employee id
     * @param ll stock management id
     * @param tanggal pelaksanaan opname
     * @param jumlah hari yang seharusnya tersisa
     * @return boolean status proses opname (true jika berhasil, false jika gagal)
     */
    private static boolean opnameLLStockTaken(long employeeId,long lLLStockManId,Date opnameDate,float iTaken){
        boolean isSuccess = true;
        
        int iCountTakenCurrPeriod = 0;
        
        //Mengupdate ll taken yang masih dalam status hutang
        Vector vPayAble = new Vector();
        vPayAble = PstLlStockTaken.getLlPayable(employeeId);
        if(vPayAble.size()>0){
            for(int i=0;i<vPayAble.size();i++){
                try {
                    LlStockTaken objLLStockTaken = new LlStockTaken();
                    objLLStockTaken = (LlStockTaken) vPayAble.get(i);
                    objLLStockTaken.setLlStockId(lLLStockManId);
                    objLLStockTaken.setPaidDate(opnameDate);
                    PstLlStockTaken.updateExc(objLLStockTaken);
                } catch (DBException ex) {
                    isSuccess = false;
                    System.out.println("[ERROR] SessLLUpload.opnameLLStockTaken ::: "+ex.toString());
                }
            }
        }
        
        //Jika berhasil diupdate maka semua data yang taken akan diopname
        if(isSuccess){
            Date dateOpnameStart = (Date)opnameDate.clone();
            dateOpnameStart.setYear(opnameDate.getYear()-1);
            dateOpnameStart.setDate(opnameDate.getDate()+1);
            String strWhere = PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_EMPLOYEE_ID]
                    +" = "+employeeId
                    +" AND "+PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_LL_STOCK_ID]
                    +" = "+lLLStockManId
                    +" AND "+PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_TAKEN_DATE]
                    +" BETWEEN \""+Formater.formatDate(dateOpnameStart, "yyyy-MM-dd")
                    +"\" AND \""+Formater.formatDate(opnameDate, "yyyy-MM-dd")+"\"";
            //System.out.println("[WHERE] SessLLUpload ::: "+strWhere);
            iCountTakenCurrPeriod = PstLlStockTaken.getSumLeave(strWhere);
            if(iCountTakenCurrPeriod != iTaken){
                float temp = 0;
                temp = iTaken - iCountTakenCurrPeriod;
                LlStockTaken objLlStockTaken = new LlStockTaken();
                objLlStockTaken.setLlStockId(lLLStockManId);
                objLlStockTaken.setEmployeeId(employeeId);
                objLlStockTaken.setPaidDate(opnameDate);
                objLlStockTaken.setTakenDate(opnameDate);
                objLlStockTaken.setTakenQty(temp);
                try {
                    PstLlStockTaken.insertExc(objLlStockTaken);
                } catch (DBException ex) {
                    isSuccess = false;
                    ex.printStackTrace();
                }
            }
        }
        return isSuccess;
    }
    
    public static LLUpload getOpnamePrevious(long empID){
        
        String where = PstLLUpload.fieldNames[PstLLUpload.FLD_DATA_STATUS]+" = "+PstLLUpload.FLD_DOC_STATUS_NOT_PROCESS+
                " AND "+PstLLUpload.fieldNames[PstLLUpload.FLD_EMPLOYEE_ID]+" = "+empID;
        
        Vector vLLUpload = PstLLUpload.list(0, 1, where, null);
        
        if(vLLUpload.size() > 0){
            LLUpload LLUploadData = (LLUpload)vLLUpload.get(0);
            return LLUploadData;            
        }else{
            return null;
        }       
    }
/**
 * create by satrya 2013-07-04
 * @param empID
 * @param alUploadId
 * @return 
 */    
public static LLUpload getLLOpnamePrevious(long empID,long alUploadId) {
         String where = "p."+PstLLUpload.fieldNames[PstLLUpload.FLD_OPNAME_DATE] + " < (SELECT p1."
+ PstLLUpload.fieldNames[PstLLUpload.FLD_OPNAME_DATE] + "  FROM "+ PstLLUpload.TBL_LL_UPLOAD + " p1 WHERE p1."
+ PstLLUpload.fieldNames[PstLLUpload.FLD_LL_UPLOAD_ID] + " =\""+alUploadId+"\""+") AND "
+ PstLLUpload.fieldNames[PstLLUpload.FLD_EMPLOYEE_ID]+"="+empID;
       
        Vector vAlUpload = PstLLUpload.listLLOpname(where);

        if (vAlUpload.size() > 0) {
            LLUpload AlUploadData = (LLUpload) vAlUpload.get(0);
            return AlUploadData;
        } else {
            return null;
        }
    }
/**
 * create by satrya 2013-07-04
 * @param empID
 * @param llUploadId
 * @return 
 */
public static LLUpload getLLOpnameSameDateOpname(long empID,long llUploadId) {

       String where = "p."+PstLLUpload.fieldNames[PstLLUpload.FLD_OPNAME_DATE] + " = (SELECT p1."
+ PstLLUpload.fieldNames[PstLLUpload.FLD_OPNAME_DATE] + "  FROM "+ PstLLUpload.TBL_LL_UPLOAD + " p1 WHERE p1."
+ PstLLUpload.fieldNames[PstLLUpload.FLD_LL_UPLOAD_ID] + " =\""+llUploadId+"\""+") AND "
+ PstLLUpload.fieldNames[PstLLUpload.FLD_EMPLOYEE_ID]+"="+empID;
       
        Vector vLLUpload = PstLLUpload.listLLOpname(where);

        if (vLLUpload.size() > 0) {
            LLUpload LLUploadData = (LLUpload) vLLUpload.get(0);
            return LLUploadData;
        } else {
            return null;
        }
    }
/**
 * create by satrya 2013-07-04
 * @param empID
 * @param llUploadId
 * @return 
 */
 public static LLUpload getLLOpnameCurr(long empID,long llUploadId) {

       String where = "p."+PstLLUpload.fieldNames[PstLLUpload.FLD_OPNAME_DATE] + " > (SELECT p1."
+ PstLLUpload.fieldNames[PstLLUpload.FLD_OPNAME_DATE] + "  FROM "+ PstLLUpload.TBL_LL_UPLOAD + " p1 WHERE p1."
+ PstLLUpload.fieldNames[PstLLUpload.FLD_LL_UPLOAD_ID] + " =\""+llUploadId+"\""+") AND "
+ PstLLUpload.fieldNames[PstLLUpload.FLD_EMPLOYEE_ID]+"="+empID;
       
        Vector vAlUpload = PstLLUpload.listLLOpname(where);

        if (vAlUpload.size() > 0) {
            LLUpload AlUploadData = (LLUpload) vAlUpload.get(0);
            return AlUploadData;
        } else {
            return null;
        }
    }
/**
 * create by satrya 2013-07-04
 * @return 
 */    
 public static Hashtable getHashOpnamePrevious(){
        String where = PstLLUpload.fieldNames[PstLLUpload.FLD_DATA_STATUS]+" = "+PstLLUpload.FLD_DOC_STATUS_NOT_PROCESS;
        String order = PstLLUpload.fieldNames[PstLLUpload.FLD_EMPLOYEE_ID] + " ASC ";;
        Vector vLLUpload = PstLLUpload.list(0, 0, where, order);
        Hashtable hashOpnamePrev = new Hashtable();
        if(vLLUpload!=null && vLLUpload.size() > 0){
          for(int x=0;x<vLLUpload.size();x++){
            LLUpload LLUploadData = (LLUpload)vLLUpload.get(x);
            hashOpnamePrev.put(LLUploadData.getEmployeeId(), LLUploadData);
          }
        }   
        return hashOpnamePrev;
    }
    
    
    /**
     * @desc mengopname ll stock expired
     * @param employee id
     * @param tanggal opname
     * @param jumlah ll yang expired
     */
    private static boolean opnameLLStockExpired(long iLLStockManId,Date expiredDate,int iLLExpired){
        boolean isSuccess = true;
        if(iLLExpired>0){
            Vector vLlStockExpired = new Vector();
            String strWhere = "";
            strWhere = PstLlStockExpired.fieldNames[PstLlStockExpired.FLD_LL_STOCK_ID] 
                    + " = " + iLLStockManId;
            vLlStockExpired = PstLlStockExpired.list(0, 0, strWhere, PstLlStockExpired.fieldNames[PstLlStockExpired.FLD_EXPIRED_DATE]);

            LlStockExpired objLlStockExpired = new LlStockExpired();

            if (vLlStockExpired.size() > 0) {
                objLlStockExpired = (LlStockExpired) vLlStockExpired.get(0);
                objLlStockExpired.setExpiredDate(expiredDate);
                objLlStockExpired.setExpiredQty(iLLExpired);
                try {
                    PstLlStockExpired.updateExc(objLlStockExpired);
                } catch (DBException ex) {
                    isSuccess = false;
                    ex.printStackTrace();
                }
            } else {
                objLlStockExpired.setExpiredDate(expiredDate);
                objLlStockExpired.setExpiredQty(iLLExpired);
                objLlStockExpired.setLlStockId(iLLStockManId);
                try {
                    PstLlStockExpired.insertExc(objLlStockExpired);
                } catch (DBException ex) {
                    isSuccess = false;
                    ex.printStackTrace();
                }
            }

            LLStockManagement llStockMan = new LLStockManagement();
            try{
                llStockMan = (LLStockManagement)PstLLStockManagement.fetchExc(iLLStockManId);
                if(llStockMan.getOID()>0){
                    llStockMan.setLLStatus(PstLLStockManagement.LL_STS_EXPIRED);
                    PstLLStockManagement.updateExc(llStockMan);
                }
            }catch(Exception ex){
                isSuccess = false;
                System.out.println("[ERROR] SessLLUpload.opnameLLStockExpired :::::::: "+ex.toString());
            }
        }
        return isSuccess;
    }
    
    /**
     * @desc mengopname data stock management dari awal sampai 2 period sebelum opname  yang active menjadi expired
     * @param employee id
     * @param untilDate
     */
    public static boolean cleanLLStockManPrev(long employeeId, Date opnameDate){
        boolean status = false;
        DBResultSet dbResultSet = null;
        String strSql = "SELECT " + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_LL_STOCK_ID] 
                + ", " + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_QTY_RESIDUE] 
                + " FROM " + PstLLStockManagement.TBL_LL_STOCK_MANAGEMENT 
                + " WHERE "+ PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_EMPLOYEE_ID] 
                + " = " + String.valueOf(employeeId) 
                + " AND "+PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_LL_STATUS] 
                + " = " + PstLLStockManagement.LL_STS_AKTIF
                + " AND "+PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_EXPIRED_DATE] 
                + " = " + Formater.formatDate(opnameDate, "yyyy-MM-dd");
        
        //System.out.println("[SQL] SessLLUpload.opnameLLStockManagementUntil :::: "+strSql);
        try {
            dbResultSet = DBHandler.execQueryResult(strSql);
            ResultSet rs = dbResultSet.getResultSet();
            while(rs.next()){
                long llStockManId = 0;
                int qtyResidue = 0;
                //System.out.println(":::::::::::::::::::OPNAME LL STOCK EXPIRED ::::::::::::::::::");
                llStockManId = rs.getLong(PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_LL_STOCK_ID] );
                qtyResidue = rs.getInt(PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_QTY_RESIDUE]);
                status = opnameLLStockExpired(llStockManId, opnameDate, qtyResidue);
            }
            status = true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (DBException ex) {
            ex.printStackTrace();
        }

        return status;
    }
    
     /**
     * @desc mencari banyak periode yang ada sampai tgl opname
     * @param employee id
     * @param tanggal opname
     * @return banyak periode sampai dengan tgl opname
     */
    private static int getPeriodLL(long employeeId,Date opnameDate,int durationPeriodInMonth){
        int iPeriod = 0;
        //System.out.println("::: "+empId +" :::: "+opnameDate);
        int durationInYear = durationPeriodInMonth/12;
        try {
            Employee objEmployee = new Employee();
            objEmployee = PstEmployee.fetchExc(employeeId);
            Date tempDate = (Date)objEmployee.getCommencingDate().clone();
            
            while(tempDate.getYear()<opnameDate.getYear()){
                tempDate.setYear(tempDate.getYear()+durationInYear);
                iPeriod += 1;
            }
            
            if(tempDate.getYear() == opnameDate.getYear()){
                if(opnameDate.getMonth()>=objEmployee.getCommencingDate().getMonth()){
                    if(opnameDate.getMonth()==objEmployee.getCommencingDate().getMonth()){
                        if(opnameDate.getDate()>objEmployee.getCommencingDate().getDate()){
                            // min 1
                            iPeriod -= 1;
                        }
                    }
                }else{
                    // min 1
                    iPeriod -= 1;
                }
            }//Tahun employee pada kelipatan 5 lebih besar
            else{
                // min 1
                iPeriod -= 1;
            }  
        } catch (DBException ex) {
            ex.printStackTrace();
        }
        return iPeriod;
    }
    
    
    /**
     * @desc memproses data pada ll upload
     * @param vector id dari employee
     * 
     */
    public static boolean opnameLLAllData(Vector vLLUploadId){
        boolean status = true;
         I_Leave leaveConfig = null;
            try {
                leaveConfig = (I_Leave) (Class.forName(PstSystemProperty.getValueByName("LEAVE_CONFIG")).newInstance());
            } catch (Exception e) {
                System.out.println("[ EXCEPTION ] :::: " + e.getMessage());
            }
        Hashtable hashEmpList = PstEmployee.hashListEmployee(); 
        Hashtable hashEmpCat = PstEmpCategory.hashListEmpCategory();
        Hashtable hashEmpLevel = PstLevel.hashlistLevel();
        Hashtable hashStockActiveByEmp = hashListLLStockManagementAktifbyEmpId();
        Hashtable hashStockManagementById = hashListLLStockManagementAktifById();
        for(int i=0;i<vLLUploadId.size();i++){
            String strLLUploadId = "";
            strLLUploadId = (String)vLLUploadId.get(i);
            long llUploadId = 0;
            llUploadId = Long.parseLong(strLLUploadId);
            LLUpload llUpload = new LLUpload();
            try {
                llUpload = PstLLUpload.fetchExc(llUploadId);
                String strError = "";
                //strError = proccessOpnameLL(llUpload);
                strError = processLLBalancing(llUpload,leaveConfig,hashEmpList,hashEmpCat,hashEmpLevel,hashStockActiveByEmp,hashStockManagementById);
                if(strError.length()>0){
                    System.out.println("[ERROR] SessLLUpload opnameLLAllData ::::::::::::: "+strError);
                    return false;
                }
            } catch (DBException ex) {
                status = false;
                ex.printStackTrace();
            }
        }
        return status;
    }
    
     /**
     * @desc memproses data pada ll upload (Hard Rock)
     * @param vector id dari employee
     * 
     */
    public static boolean opnameLLAllDataHR(Vector vLLUploadId){
        boolean status = true;
        for(int i=0;i<vLLUploadId.size();i++){
            String strLLUploadId = "";
            strLLUploadId = (String) vLLUploadId.get(i);
            long llUploadId = 0;
            llUploadId = Long.parseLong(strLLUploadId);
            LLUpload llUpload = new LLUpload();
            try {
                llUpload = PstLLUpload.fetchExc(llUploadId);
                String strError = "";
                strError = proccessOpnameLL_HR(llUpload);
                if(strError.length()>0){
                    System.out.println("[ERROR] SessLLUpload opnameLLAllData ::::::::::::: "+strError);
                    return false;
                }
            } catch (DBException ex) {
                status = false;
                ex.printStackTrace();
            }
        }
        return status;
    }
    
    
    public static Vector getIdStockManagementLLAktif(long employeeID){
        
        long StockManagementID = 0;
        DBResultSet dbrs = null;
        
        try{
            String sql = "SELECT "+PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_LL_STOCK_ID]+","
                                  +PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_ENTITLED]+","
                                  +PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_LL_STOCK_ID]+","
                                  +PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_OWNING_DATE]+","
                                  +PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_OPENING_LL]+","
                                  +PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_PREV_BALANCE]+","
                                  +PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_LL_QTY]+","
                                  +PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_QTY_USED]+","
                                  +PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_QTY_RESIDUE]+","
                                  +PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_LL_STATUS]+","
                                  +PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_ENTITLED]+","
                                  +PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_RECORD_DATE]+","
                                  +PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_ENTITLED_DATE]                                  
                                  +" FROM "+PstLLStockManagement.TBL_LL_STOCK_MANAGEMENT+
                    " WHERE "+PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_EMPLOYEE_ID]+" = "+employeeID+" AND "
                    +PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_LL_STATUS]+" = "+PstLLStockManagement.LL_STS_AKTIF;
            dbrs = DBHandler.execQueryResult(sql);
                        //System.out.println("SQL LIST1"+sql);
	    ResultSet rs = dbrs.getResultSet();
            Vector result = new Vector();
	    while(rs.next()) {
                LLStockManagement llstockmanagement = new LLStockManagement();
                llstockmanagement.setEmployeeId(rs.getLong(1));
                llstockmanagement.setEntitled(rs.getInt(2));
                llstockmanagement.setOID(rs.getInt(3));
                llstockmanagement.setDtOwningDate(rs.getDate(4));
                llstockmanagement.setOpeningLL(rs.getFloat(5));
                llstockmanagement.setPrevBalance(rs.getFloat(6));
                llstockmanagement.setLLQty(rs.getFloat(7));
                llstockmanagement.setQtyUsed(rs.getFloat(8));
                llstockmanagement.setQtyResidue(rs.getFloat(9));
                llstockmanagement.setLLStatus(rs.getInt(10));
                llstockmanagement.setEntitled(rs.getFloat(11));
                llstockmanagement.setRecordDate(rs.getDate(12));
                llstockmanagement.setEntitledDate(rs.getDate(13));                
                
                result.add(llstockmanagement);        
	    }
            rs.close();
            return result;
            
        }catch(Exception e){
            System.out.println(" [EXCEPTION ] :::: "+e.toString());
        }finally {
	    DBResultSet.close(dbrs);
        }    
        
        return null;               
       
    }
    
    private static LLStockManagement getLLStockManagementAktif(long employeeId) {
        LLStockManagement objLLStockManagement = new LLStockManagement();

        DBResultSet dbrs;
        String strWhere = PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_EMPLOYEE_ID]+" = "+employeeId+" AND "+PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_LL_STATUS]+" = "+PstLLStockManagement.LL_STS_AKTIF;

        Vector vLLStockManagement = new Vector();
        vLLStockManagement = PstLLStockManagement.list(0, 1, strWhere, PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_OWNING_DATE] + " ASC");

        if (vLLStockManagement.size() > 0) {
            objLLStockManagement = (LLStockManagement) vLLStockManagement.get(0);
        } else {
            return new LLStockManagement();
        }
        return objLLStockManagement;
    }
    
/**
 * Create by satrya 2013-07-04
 * @return 
 */  
private static Hashtable hashListLLStockManagementAktifbyEmpId() {
        Hashtable hashListLLStockMan = new Hashtable();
       
        String strWhere = PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_LL_STATUS]+" = "+PstLLStockManagement.LL_STS_AKTIF;
        String order = PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_EMPLOYEE_ID]+ " ASC ";

        Vector vLLStockManagement = new Vector();
        vLLStockManagement = PstLLStockManagement.list(0, 0, strWhere, order);

        if (vLLStockManagement!=null && vLLStockManagement.size() > 0) {
            for(int x=0;x<vLLStockManagement.size();x++){
                LLStockManagement  objLLStockManagement = (LLStockManagement) vLLStockManagement.get(x);
                hashListLLStockMan.put(objLLStockManagement.getEmployeeId(), objLLStockManagement);
            }
          
        } 
        return hashListLLStockMan;
    }
    
private static Hashtable hashListLLStockManagementAktifById() {
        Hashtable hashListLLStockMan = new Hashtable();
       
        String strWhere = PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_LL_STATUS]+" = "+PstLLStockManagement.LL_STS_AKTIF;
        String order = PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_EMPLOYEE_ID]+ " ASC ";

        Vector vLLStockManagement = new Vector();
        vLLStockManagement = PstLLStockManagement.list(0, 0, strWhere, order);

        if (vLLStockManagement!=null && vLLStockManagement.size() > 0) {
            for(int x=0;x<vLLStockManagement.size();x++){
                LLStockManagement  objLLStockManagement = (LLStockManagement) vLLStockManagement.get(x);
                hashListLLStockMan.put(objLLStockManagement.getOID(), objLLStockManagement);
            }
          
        } 
        return hashListLLStockMan;
    }
    public static String processLLBalancing(LLUpload LLUploadArg,I_Leave leaveConfig
            ,Hashtable hashEmpList,Hashtable hashEmpCat,Hashtable hashEmpLevel,Hashtable hashStockActiveByEmp,Hashtable hashStockManagementById){
        String strError = "";
        long employeeId = 0;
        Date dateOpname = new Date();
        dateOpname = LLUploadArg.getOpnameDate();
        employeeId = LLUploadArg.getEmployeeId();
        LLUpload llUpload = new LLUpload();
        llUpload = LLUploadArg;
        
        if (LLUploadArg.getOID() > 0) {
           

            Employee employee = new Employee();
            
          
                //employee = PstEmployee.fetchExc(employeeId);
            if(hashEmpList!=null && hashEmpList.size()>0 && hashEmpList.get(employeeId)!=null){
                employee = (Employee)hashEmpList.get(employeeId); 
             }

            // entitle counted on commencing date
            if (leaveConfig.getALEntitleBy() == I_Leave.AL_ENTITLE_BY_COMMENCING || leaveConfig.getALEntitleBy() == I_Leave.AL_ENTITLE_ANUAL || leaveConfig.getALEntitleBy() == I_Leave.AL_ENTITLE_BY_PERIOD) {

                // earn only once a year on beginning period
                if (leaveConfig.getAlEarnedBy() == I_Leave.AL_EARNED_BY_TOTAL) {
   
                    int LoS_5 = 0;
                    int LoS_8 = 0;
                    
                    EmpCategory empCategory = new EmpCategory();
                    Level level = new Level();
                    
                    if(hashEmpCat!=null && hashEmpCat.size()>0 &&  hashEmpCat.get(employee.getEmpCategoryId())!=null){
                        empCategory = (EmpCategory)hashEmpCat.get(employee.getEmpCategoryId());
                    }

                    if(hashEmpLevel!=null && hashEmpLevel.size()>0 &&  hashEmpLevel.get(employee.getLevelId())!=null){
                        level = (Level)hashEmpLevel.get(employee.getLevelId());
                    }

                   /* try {
                        level = PstLevel.fetchExc(employee.getLevelId());
                    } catch (Exception e) {
                        System.out.println("Exception " + e.toString());
                    }*/
                    
                    int configuration = leaveConfig.getIntervalLLinMonths()[LeaveConfigHR.iINTERVAL_LL_HARDROCK_I];     // range mendapatkan LL (dalam bulan)
                    
                    int extend_1 = leaveConfig.getLLValidityMonths(level.getLevel(), empCategory.getEmpCategory(), configuration);
                    
                    Date dt_ent_date = getHaveStockLL(employee.getOID(),configuration);
                    
                    String var_date_exp_I = SessLeaveClosing.getDateIntervalMonth(extend_1, dt_ent_date);
                    
                    //Length of Service at 5 and 8 inteterval
                    if((leaveConfig.getIntervalLLinMonths()[I_Leave.INTERVAL_LL_5_YEAR])!=0){
                         LoS_5 = getPeriodLL(employeeId, dateOpname, leaveConfig.getIntervalLLinMonths()[I_Leave.INTERVAL_LL_5_YEAR]);
                    }
                   
                    if(leaveConfig.getIntervalLLinMonths()[I_Leave.INTERVAL_LL_8_YEAR]!=0){
                         LoS_8 = getPeriodLL(employeeId, dateOpname, leaveConfig.getIntervalLLinMonths()[I_Leave.INTERVAL_LL_8_YEAR]);
                    }
                   

                    Date dateStartCurrPer = new Date();
                    dateStartCurrPer = getStartPeriodDate(employeeId, dateOpname, (8*12));
                    
                    Date dateStartLastPer = new Date();
                    dateStartLastPer = (Date) dateStartCurrPer.clone();
                    dateStartLastPer.setYear(dateStartLastPer.getYear() - 1);
                   
                    LLStockManagement llStockManagementData = new LLStockManagement();//getLLStockManagementAktif(employeeId);
                   if(hashStockActiveByEmp!=null && hashStockActiveByEmp.size()>0 && hashStockActiveByEmp.get(employeeId)!=null){
                        llStockManagementData = (LLStockManagement)hashStockActiveByEmp.get(employeeId);
                    }
                    long llStockManagementId =0;
                    
                    if( llStockManagementData.getOID() == 0){                                             
                        llStockManagementData.setEmployeeId(LLUploadArg.getEmployeeId());
                        llStockManagementData.setLLStatus(PstLLStockManagement.LL_STS_AKTIF);
                        llStockManagementData.setLeavePeriodeId(0);                        
                        llStockManagementData.setEntitled(LLUploadArg.getNewLL());
                        llStockManagementData.setQtyUsed(LLUploadArg.getLlTakenYear1());
                        llStockManagementData.setOpeningLL(0);
                        llStockManagementData.setPrevBalance(LLUploadArg.getLastPerToClearLL());                                                                                              
                        llStockManagementData.setLLQty(LLUploadArg.getLLQty());
                        llStockManagementData.setQtyResidue(llStockManagementData.getPrevBalance()+llStockManagementData.getEntitled()
                                 +llStockManagementData.getEntitle2()- llStockManagementData.getQtyUsed());
                        llStockManagementData.setRecordDate(new Date());                                                 
                        llStockManagementData.setEntitledDate(dt_ent_date);                        
                        llStockManagementData.setExpiredDate(Formater.formatDate(var_date_exp_I,"yyyy-MM-dd"));
                        
                        try{
                            llStockManagementId = PstLLStockManagement.insertExc(llStockManagementData);
                            System.out.println("Stock management inserted");
                        } catch (DBException ex) {
                            System.out.println("Error update stock management "+ex);
                            return("Error insert LL stock data");
                        } 
                        
                    } else{
                        
                        LLStockManagement llStockManagement = new LLStockManagement();
                        
                       /* try{
                            llStockManagement = PstLLStockManagement.fetchExc(LLUploadArg.getLLStockID());
                        }catch(Exception e){
                            System.out.println("EXCEPTION "+e);
                        }*/
                        if(hashStockManagementById!=null && hashStockManagementById.size()>0 && hashStockManagementById.get(LLUploadArg.getLLStockID())!=null){
                            llStockManagement = (LLStockManagement)hashStockManagementById.get(LLUploadArg.getLLStockID());
                        }
                        
                        
                        llStockManagementData.setEmployeeId(LLUploadArg.getEmployeeId());
                        llStockManagementData.setLLStatus(PstLLStockManagement.LL_STS_AKTIF);
                        llStockManagementData.setLeavePeriodeId(0);                        
                        llStockManagementData.setEntitled(LLUploadArg.getNewLL());
                        llStockManagementData.setQtyUsed(LLUploadArg.getLlTakenYear1());
                        llStockManagementData.setOpeningLL(llStockManagement.getOpeningLL());
                        llStockManagementData.setPrevBalance(LLUploadArg.getLastPerToClearLL());                                                                                              
                        llStockManagementData.setLLQty(LLUploadArg.getLLQty());
                        llStockManagementData.setRecordDate(llStockManagement.getRecordDate());                        
                        llStockManagementData.setEntitledDate(llStockManagement.getEntitledDate());                        
                        llStockManagementData.setEntitle2(LLUploadArg.getNewLL2());
                        llStockManagementData.setQtyResidue(llStockManagementData.getPrevBalance()+llStockManagementData.getEntitled()
                                 +llStockManagementData.getEntitle2()- llStockManagementData.getQtyUsed());                        
                        llStockManagementData.setEntitleDate2(llStockManagement.getEntitleDate2());
                        llStockManagementData.setExpiredDate(llStockManagement.getExpiredDate());
                        llStockManagementData.setExpiredDate2(llStockManagement.getExpiredDate2());                       
                        try{
                            llStockManagementId = PstLLStockManagement.updateExc(llStockManagementData);
                            System.out.println("Stock management update");
                        } catch (DBException ex) {
                            System.out.println("Error update stock management");
                        }                         
                    }
                    
                    if( llStockManagementData.getOID() != 0){                                                                     
                        try{
                            
                            int sum_taken = 0;
                            DBResultSet dbrs = null;
                            
                            try{
                                String sql = "SELECT SUM("+PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_TAKEN_QTY]+") FROM "+PstLlStockTaken.TBL_HR_LL_STOCK_TAKEN +
                                        " WHERE "+PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_LL_STOCK_ID]+" = "+llStockManagementId;
                                
                                dbrs = DBHandler.execQueryResult(sql);
                                ResultSet rs = dbrs.getResultSet();       
                                
                                while(rs.next()){
                                    sum_taken = rs.getInt(1);
                                }
                                
                                rs.close();
                                
                            }catch(Exception e){
                                System.out.println("EXCEPTION "+e.toString());
                            }                            
                            
                            if(llStockManagementData.getQtyUsed() > sum_taken){
                                float selisih = llStockManagementData.getQtyUsed()  - sum_taken;
                                prosesInsertDataTakenLL(1,llStockManagementId,llStockManagementData.getEmployeeId(),selisih,dateOpname);
                                
                                
                            }else if(llStockManagementData.getQtyUsed() < sum_taken){
                                float selisih = sum_taken - llStockManagementData.getQtyUsed() ; 
                                prosesInsertDataTakenLL(2,llStockManagementId,llStockManagementData.getEmployeeId(),selisih,dateOpname);
                            }                              
                                
                            LLUpload objLLUpload = new LLUpload();
                            objLLUpload = getDataUpload(LLUploadArg.getOID());                            
                            objLLUpload.setDataStatus(PstLLUpload.FLD_DOC_STATUS_PROCESS);
                            PstLLUpload.updateExc(objLLUpload);
                        }catch(Exception e){
                            System.out.println("Exception ::: "+e.toString());
                        }
                    }                   
                }           
            }
        }         
        return strError;
    }
    
    public static LLUpload getDataUpload(long UploadID){
        LLUpload uploadData = new LLUpload();
        
        String where = PstLLUpload.fieldNames[PstLLUpload.FLD_LL_UPLOAD_ID]+" = "+UploadID;
        
        Vector LLUploadData = PstLLUpload.list(0 ,1,where,null);
        
        if(LLUploadData.size() > 0){
            uploadData = (LLUpload) LLUploadData.get(0);
            return uploadData;
        } else{
            return null;
        }       
    }
  
    
    /**
     * @desc proses opname dari LL secara keseluruhan
     * @param employee id dan tanggal opname
     * @return string, error
     */
    public static String proccessOpnameLL(LLUpload llUploadArg){
        String strError = "";
        long employeeId = 0;
        long llStockManId = 0;
        Date dateOpname = new Date();
        dateOpname = llUploadArg.getOpnameDate();
        employeeId = llUploadArg.getEmployeeId();
        LLUpload objLLUpload = new LLUpload();
        objLLUpload = llUploadArg;
        
        if(llUploadArg.getOID()>0){
            I_Leave leaveConfig = null;           
            try {
                leaveConfig = (I_Leave)(Class.forName(PstSystemProperty.getValueByName("LEAVE_CONFIG")).newInstance());            
            }
        
            catch(Exception e) {
                System.out.println("Exception : " + e.getMessage());
            }
            
            Employee employee = new Employee();
            try {
                employee = PstEmployee.fetchExc(employeeId);
            } catch (DBException ex) {
                strError = "EMPLOYEE NOT FOUND!";
                ex.printStackTrace();
            }
            
            /////////////////PROCESS//////////////////
            int LoS_5 = 0;
            int LoS_8 = 0;
            //Length of Service at 5 and 8 inteterval
            LoS_5 = getPeriodLL(employeeId, dateOpname, leaveConfig.getIntervalLLinMonths()[I_Leave.INTERVAL_LL_5_YEAR]);
            LoS_8 = getPeriodLL(employeeId, dateOpname, leaveConfig.getIntervalLLinMonths()[I_Leave.INTERVAL_LL_8_YEAR]);
            
            //Date start of interval
            Date dateStartPer_Intv5 = getStartPeriodDate(employeeId, dateOpname, leaveConfig.getIntervalLLinMonths()[I_Leave.INTERVAL_LL_5_YEAR]);
            Date dateStartPer_Intv8 = getStartPeriodDate(employeeId, dateOpname, leaveConfig.getIntervalLLinMonths()[I_Leave.INTERVAL_LL_8_YEAR]);

            //LL Stock Management at 5&8 interval
            LLStockManagement objLLStockMan_Intv5 = new LLStockManagement();
            LLStockManagement objLLStockMan_Intv8 = new LLStockManagement();
            
            //If haven't over than 5 year work
            if(LoS_5>0){
                
                //Membersihkan data yang seharusnya sudah expired
                cleanLLStockManPrev(employeeId, dateOpname);
                
                //Mencari tanggal expired
                int iValidityLL_Intv5 = 0;
                int iValidityLL_Intv8 = 0;
                iValidityLL_Intv5 = leaveConfig.getLLValidityMonths(leaveConfig.getLevel(employeeId), leaveConfig.getCategory(employeeId), leaveConfig.getIntervalLLinMonths()[I_Leave.INTERVAL_LL_5_YEAR]);
                iValidityLL_Intv8 = leaveConfig.getLLValidityMonths(leaveConfig.getLevel(employeeId), leaveConfig.getCategory(employeeId), leaveConfig.getIntervalLLinMonths()[I_Leave.INTERVAL_LL_8_YEAR]);
                
                int iYearAdd_Intv5  = iValidityLL_Intv5/12;
                int iMonthAdd_Intv5 = iValidityLL_Intv5%12;
                int iYearAdd_Intv8  = iValidityLL_Intv8/12;
                int iMonthAdd_Intv8 = iValidityLL_Intv8%12;
                
       //         System.out.println("::::: ADD YEAR INT 5  :: "+iYearAdd_Intv5);
       //         System.out.println("::::: ADD MONTH INT 5 :: "+iMonthAdd_Intv5);
       //         System.out.println("::::: ADD YEAR INT 8  :: "+iYearAdd_Intv8);
       //         System.out.println("::::: ADD MONTH INT 8 :: "+iMonthAdd_Intv8);
                
                Date expDate_Intv5 = (Date)dateStartPer_Intv5.clone();
                Date expDate_Intv8 = (Date)dateStartPer_Intv8.clone();
                
                
                
                expDate_Intv5.setYear(dateStartPer_Intv5.getYear()+iYearAdd_Intv5);
                expDate_Intv5.setMonth(dateStartPer_Intv5.getMonth()+iMonthAdd_Intv5);
                expDate_Intv8.setYear(dateStartPer_Intv8.getYear()+iYearAdd_Intv8);
                expDate_Intv8.setMonth(dateStartPer_Intv8.getMonth()+iMonthAdd_Intv8);
                
       //         System.out.println("::::: EXPIRED DATE INTV 5 :: "+expDate_Intv5);
       //         System.out.println("::::: EXPIRED DATE INTV 8 :: "+expDate_Intv8);
                
                //Object
                objLLStockMan_Intv5 = getLLStockManagement(employeeId, dateStartPer_Intv5);
                objLLStockMan_Intv5.setDtOwningDate(dateStartPer_Intv5);
                objLLStockMan_Intv5.setEmployeeId(employeeId);
                objLLStockMan_Intv5.setEntitled(getLLEntitled(employeeId, dateOpname, leaveConfig.getIntervalLLinMonths()[I_Leave.INTERVAL_LL_5_YEAR]));
                //objLLStockMan_Intv5.setExpiredDate(expDate_Intv5);
                objLLStockMan_Intv5.setLLQty(objLLStockMan_Intv5.getEntitled());
                objLLStockMan_Intv5.setLeavePeriodeId(0);
                objLLStockMan_Intv5.setStNote("LL Opname");
                objLLStockMan_Intv5.setQtyUsed(0);
                objLLStockMan_Intv5.setQtyResidue(objLLStockMan_Intv5.getEntitled());
                
                //Cek pengambilan ll
                float iTaken = llUploadArg.getLlTakenYear1()
                        +llUploadArg.getLlTakenYear2()
                        +llUploadArg.getLlTakenYear3()
                        +llUploadArg.getLlTakenYear4()
                        +llUploadArg.getLlTakenYear5();
                
                
                //Terdapat LL sebanyak n pada tahun kelipata 8
                if(LoS_8>0 && (dateStartPer_Intv8.getTime()>=dateStartPer_Intv5.getTime())){ 
                    //dengan interval 8 harus muncul belakangan supaya 
                    //bisa dipergunakan ;;; dateStartPer_Intv8.getTime()>=dateStartPer_Intv5.getTime()
                    
                    objLLStockMan_Intv8 = getLLStockManagement(employeeId, dateStartPer_Intv8);
                    
                    objLLStockMan_Intv8.setDtOwningDate(dateStartPer_Intv8);
                    objLLStockMan_Intv8.setEmployeeId(employeeId);
                    objLLStockMan_Intv8.setEntitled(getLLEntitled(employeeId, dateOpname, leaveConfig.getIntervalLLinMonths()[I_Leave.INTERVAL_LL_8_YEAR]));
                    //objLLStockMan_Intv8.setExpiredDate(expDate_Intv8);
                    objLLStockMan_Intv8.setLLQty(objLLStockMan_Intv8.getEntitled());
                    objLLStockMan_Intv8.setLeavePeriodeId(0);
                    objLLStockMan_Intv8.setLLStatus(PstLLStockManagement.LL_STS_AKTIF);
                    objLLStockMan_Intv8.setStNote("LL Opname");
                    objLLStockMan_Intv8.setQtyUsed(0);
                    objLLStockMan_Intv8.setQtyResidue(objLLStockMan_Intv8.getEntitled());
                        
                    if(objLLStockMan_Intv8.getLLStatus()==PstLLStockManagement.LL_STS_AKTIF){
                        if(iTaken<=objLLStockMan_Intv8.getQtyResidue()){
                            objLLStockMan_Intv8.setQtyUsed(iTaken);
                            objLLStockMan_Intv8.setQtyResidue(objLLStockMan_Intv8.getEntitled()-iTaken);
                            if(objLLStockMan_Intv8.getQtyResidue()==0){
                                objLLStockMan_Intv8.setLLStatus(PstLLStockManagement.LL_STS_TAKEN);
                            }
                            iTaken = 0;
                        }else{
                            objLLStockMan_Intv8.setQtyUsed(objLLStockMan_Intv8.getEntitled());
                            objLLStockMan_Intv8.setQtyResidue(0);
                            objLLStockMan_Intv8.setLLStatus(PstLLStockManagement.LL_STS_TAKEN);
                            iTaken -= objLLStockMan_Intv8.getEntitled();
                        }
                    }
                    
                    if(objLLStockMan_Intv8.getOID()>0){
                        try {
                            llStockManId = PstLLStockManagement.updateExc(objLLStockMan_Intv8);
                        } catch (DBException ex) {
                            strError = "Update data into LL Stock Management Failed";
                            ex.printStackTrace();
                        }
                    }else{
                        try {
                            llStockManId = PstLLStockManagement.insertExc(objLLStockMan_Intv8);
                        } catch (DBException ex) {
                            strError = "Insert data into LL Stock Management Failed";
                            ex.printStackTrace();
                        }
                    }
                    //Meng-opname pengambilan ll
                    opnameLLStockTaken(employeeId, llStockManId, objLLStockMan_Intv8.getDtOwningDate(), objLLStockMan_Intv8.getQtyUsed());
                    float iOverTaken = 0;
                    if(iTaken>objLLStockMan_Intv5.getEntitled()){
                        iOverTaken = iTaken-objLLStockMan_Intv5.getEntitled();
                        iTaken -= iOverTaken;
                        opnameLLStockTaken(employeeId, 0, dateOpname, iOverTaken);
                        System.out.println("[WARNING] Terjadi pengambilan berlebih pada LL :::::::::::::::::::::: "+employeeId);
                    }
                    objLLStockMan_Intv5.setQtyUsed(iTaken);
                    objLLStockMan_Intv5.setQtyResidue(objLLStockMan_Intv5.getEntitled()-iTaken);
                    if(objLLStockMan_Intv5.getQtyResidue()<=0){
                        objLLStockMan_Intv5.setLLStatus(PstLLStockManagement.LL_STS_TAKEN);
                    }
                    int llStockMan_Int5 = 0;
                    if(objLLStockMan_Intv5.getOID()>0){
                        try {
                            llStockManId = PstLLStockManagement.updateExc(objLLStockMan_Intv5);
                        } catch (DBException ex) {
                            strError = "Update data into LL Stock Management Failed";
                            ex.printStackTrace();
                        }
                    }else{
                        try {
                            llStockManId = PstLLStockManagement.insertExc(objLLStockMan_Intv5);
                        } catch (DBException ex) {
                            strError = "Insert data into LL Stock Management Failed";
                            ex.printStackTrace();
                        }
                    }
                    
                    //mengopname ll taken yang tersisa
                    int countOpnameLL = 0;
                    for(int i=0;i<5;i++){
                        float iToGet = 0;
                        switch(i){
                            case 0:
                                iToGet = llUploadArg.getLlTakenYear1();
                                break;
                            case 1: 
                                iToGet = llUploadArg.getLlTakenYear2();
                                break;
                            case 2: 
                                iToGet = llUploadArg.getLlTakenYear3();
                                break;
                            case 3: 
                                iToGet = llUploadArg.getLlTakenYear4();
                                break;
                            case 4: 
                                iToGet = llUploadArg.getLlTakenYear5();
                                break;
                        }
                        if(dateStartPer_Intv5.getTime()==dateStartPer_Intv8.getTime()){
                            iToGet = iToGet-objLLStockMan_Intv8.getQtyUsed();
                        }
                        countOpnameLL += iToGet; 
                        if(countOpnameLL>iTaken){
                            float iOver = countOpnameLL-iTaken;
                            if(iOver<iToGet){
                                iToGet -= iOver;
                            }else{
                                iToGet = 0;
                            }
                        }
                        if(!(iToGet <= 0 )){
                            Date takenDate = (Date)dateStartPer_Intv5.clone();
                            takenDate.setYear(takenDate.getYear()+i+1);//untuk mengincrement tahun pengambilan LL
                            takenDate.setDate(takenDate.getDate()-1);
                            opnameLLStockTaken(employeeId, llStockManId, takenDate, iToGet);
                        }
                    }
                    
                }
                //Ini untuk yang tidak memiliki interval 8
                //Perbedaan terjadi pada pengambilan LL
                else{
                    //////////////////////////////////////////////////
                    float iOverTaken = 0;
                    if(iTaken>objLLStockMan_Intv5.getEntitled()){
                        iOverTaken = iTaken-objLLStockMan_Intv5.getEntitled();
                        iTaken -= iOverTaken;
                        opnameLLStockTaken(employeeId, 0, dateOpname, iOverTaken);
                    }
                    objLLStockMan_Intv5.setQtyUsed(iTaken);
                    objLLStockMan_Intv5.setQtyResidue(objLLStockMan_Intv5.getEntitled()-iTaken);
                    if(objLLStockMan_Intv5.getQtyResidue()<=0){
                        objLLStockMan_Intv5.setLLStatus(PstLLStockManagement.LL_STS_TAKEN);
                    }
                    int llStockMan_Int5 = 0;
                    if(objLLStockMan_Intv5.getOID()>0){
                        try {
                            llStockManId = PstLLStockManagement.updateExc(objLLStockMan_Intv5);
                        } catch (DBException ex) {
                            strError = "Update data into LL Stock Management Failed";
                            ex.printStackTrace();
                        }
                    }else{
                        try {
                            llStockManId = PstLLStockManagement.insertExc(objLLStockMan_Intv5);
                        } catch (DBException ex) {
                            strError = "Insert data into LL Stock Management Failed";
                            ex.printStackTrace();
                        }
                    }
                    
                    //mengopname ll taken yang tersisa
                    int countOpnameLL = 0;
                    for(int i=0;i<5;i++){
                        float iToGet = 0;
                        switch(i){
                            case 0:
                                iToGet = llUploadArg.getLlTakenYear1();
                                break;
                            case 1: 
                                iToGet = llUploadArg.getLlTakenYear2();
                                break;
                            case 2: 
                                iToGet = llUploadArg.getLlTakenYear3();
                                break;
                            case 3: 
                                iToGet = llUploadArg.getLlTakenYear4();
                                break;
                            case 4: 
                                iToGet = llUploadArg.getLlTakenYear5();
                                break;
                        }
                       // System.out.println("Data to get "+i+" ::: "+iToGet);
                        countOpnameLL += iToGet; 
                        if(countOpnameLL>iTaken){
                            float iOver = countOpnameLL-iTaken;
                            if(iOver<iToGet){
                                iToGet -= iOver;
                            }else{
                                iToGet = 0;
                            }
                        }
                      //  System.out.println("Data to get "+i+" ::: "+iToGet);
                        if(!(iToGet <= 0 )){
                            Date takenDate = (Date)dateStartPer_Intv5.clone();
                            takenDate.setYear(takenDate.getYear()+i+1);//untuk mengincrement tahun pengambilan LL
                            takenDate.setDate(takenDate.getDate()-1);
                            opnameLLStockTaken(employeeId, llStockManId, takenDate, iToGet);
                        }
                }
            
            }
           }
            //////////////////////////////////////////
            
        }else{
            strError = "LL Upload data not found!";
        }
        
        //MENDAFTARKAN STATUS LL YANG TELAH DIPROSES
        if(strError.length()<=0){
             llUploadArg.setDataStatus(PstLLUpload.FLD_DOC_STATUS_PROCESS);
            try {
                PstLLUpload.updateExc(llUploadArg);
            } catch (DBException ex) {
                ex.printStackTrace();
            }
        }
        
        return strError;
    }
    
    /**
     * @desc proses opname dari LL secara keseluruhan (Hard Rock)
     * @param employee id dan tanggal opname
     * @return string, error
     */
    public static String proccessOpnameLL_HR(LLUpload llUploadArg){
        
        // interval 5 year dan 8 year tidak harus bernilai 5 dan 8 tahun
        // konfigurasi tergantung kelas yang mengimplementasikan I_Leave
        // dalam code program, istilah 5 year dan 8 year tetap digunakan
        // untuk alasan kemudahan (karena dari dulu ditulis seperti itu :) )
        
        String strError = "";
        long employeeId = 0;
        long llStockManId = 0;
        Date dateOpname = new Date();
        dateOpname = llUploadArg.getOpnameDate();
        employeeId = llUploadArg.getEmployeeId();
        LLUpload objLLUpload = new LLUpload();
        objLLUpload = llUploadArg;
        
        if(llUploadArg.getOID()>0){
            I_Leave leaveConfig = null;           
            try {
                leaveConfig = (I_Leave)(Class.forName(PstSystemProperty.getValueByName("LEAVE_CONFIG")).newInstance());            
            }
            catch(Exception e) {
                System.out.println("Exception : " + e.getMessage());
            }
            
            Employee employee = new Employee();
            try {
                employee = PstEmployee.fetchExc(employeeId);
            } catch (DBException ex) {
                strError = "EMPLOYEE NOT FOUND!";
                ex.printStackTrace();
            }
                      
            /////////////////PROCESS//////////////////
            int LoS_5 = 0;
            int LoS_8 = 0;
            
            //Length of Service at 5 and 8 interval
            LoS_5 = getPeriodLL(employeeId, dateOpname, leaveConfig.getIntervalLLinMonths()[I_Leave.INTERVAL_LL_5_YEAR]);
            LoS_8 = getPeriodLL(employeeId, dateOpname, leaveConfig.getIntervalLLinMonths()[I_Leave.INTERVAL_LL_8_YEAR]);
            
            //Date start of interval
            Date dateStartPer_Intv5 = getStartPeriodDate(employeeId, dateOpname, leaveConfig.getIntervalLLinMonths()[I_Leave.INTERVAL_LL_5_YEAR]);
            Date dateStartPer_Intv8 = getStartPeriodDate(employeeId, dateOpname, leaveConfig.getIntervalLLinMonths()[I_Leave.INTERVAL_LL_8_YEAR]);

            //LL Stock Management at 5&8 interval
            LLStockManagement objLLStockMan_Intv5 = new LLStockManagement();
            LLStockManagement objLLStockMan_Intv8 = new LLStockManagement();
            
            //If over than 5 year work
            if(LoS_5>0){
                
                //Membersihkan data yang seharusnya sudah expired
                cleanLLStockManPrev(employeeId, dateOpname);
                
                //Mencari tanggal expired
                int iValidityLL_Intv5 = 0;
                int iValidityLL_Intv8 = 0;
                iValidityLL_Intv5 = leaveConfig.getLLValidityMonths(leaveConfig.getLevel(employeeId), leaveConfig.getCategory(employeeId), leaveConfig.getIntervalLLinMonths()[I_Leave.INTERVAL_LL_5_YEAR]);
                iValidityLL_Intv8 = leaveConfig.getLLValidityMonths(leaveConfig.getLevel(employeeId), leaveConfig.getCategory(employeeId), leaveConfig.getIntervalLLinMonths()[I_Leave.INTERVAL_LL_8_YEAR]);
                
                int iYearAdd_Intv5  = iValidityLL_Intv5/12;
                int iMonthAdd_Intv5 = iValidityLL_Intv5%12;
                int iYearAdd_Intv8  = iValidityLL_Intv8/12;
                int iMonthAdd_Intv8 = iValidityLL_Intv8%12;
  
                Date expDate_Intv5 = (Date)dateStartPer_Intv5.clone();
                Date expDate_Intv8 = (Date)dateStartPer_Intv8.clone();
                
                
                expDate_Intv5.setYear(dateStartPer_Intv5.getYear()+iYearAdd_Intv5);
                expDate_Intv5.setMonth(dateStartPer_Intv5.getMonth()+iMonthAdd_Intv5);
                expDate_Intv8.setYear(dateStartPer_Intv8.getYear()+iYearAdd_Intv8);
                expDate_Intv8.setMonth(dateStartPer_Intv8.getMonth()+iMonthAdd_Intv8);
                
                
                //Object
                objLLStockMan_Intv5 = getLLStockManagement(employeeId, dateStartPer_Intv5);
                objLLStockMan_Intv5.setDtOwningDate(dateStartPer_Intv5);
                objLLStockMan_Intv5.setEmployeeId(employeeId);
                //objLLStockMan_Intv5.setEntitled(getLLEntitled(employeeId, dateOpname, leaveConfig.getIntervalLLinMonths()[I_Leave.INTERVAL_LL_5_YEAR]));
                objLLStockMan_Intv5.setEntitled(llUploadArg.getStock());
                //objLLStockMan_Intv5.setExpiredDate(expDate_Intv5);
                objLLStockMan_Intv5.setLLQty(objLLStockMan_Intv5.getEntitled());                
                objLLStockMan_Intv5.setLeavePeriodeId(0);
                objLLStockMan_Intv5.setStNote("LL Opname");
                objLLStockMan_Intv5.setQtyUsed(0);
                objLLStockMan_Intv5.setQtyResidue(objLLStockMan_Intv5.getEntitled());
                objLLStockMan_Intv5.setLLStatus(PstLLStockManagement.LL_STS_AKTIF);
                
                //Cek pengambilan ll
                /*int iTaken = llUploadArg.getLlTakenYear1()
                        +llUploadArg.getLlTakenYear2()
                        +llUploadArg.getLlTakenYear3()
                        +llUploadArg.getLlTakenYear4()
                        +llUploadArg.getLlTakenYear5();*/
                
                int iTaken = 0;
                
                
                //Terdapat LL sebanyak n pada tahun kelipatan 8
                if(LoS_8>0 && (dateStartPer_Intv8.getTime()>=dateStartPer_Intv5.getTime())){ 
                    //dengan interval 8 harus muncul belakangan supaya 
                    //bisa dipergunakan ;;; dateStartPer_Intv8.getTime()>=dateStartPer_Intv5.getTime()
                    
                    objLLStockMan_Intv8 = getLLStockManagement(employeeId, dateStartPer_Intv8);
                    
                    objLLStockMan_Intv8.setDtOwningDate(dateStartPer_Intv8);
                    objLLStockMan_Intv8.setEmployeeId(employeeId);
                    //objLLStockMan_Intv8.setEntitled(getLLEntitled(employeeId, dateOpname, leaveConfig.getIntervalLLinMonths()[I_Leave.INTERVAL_LL_8_YEAR]));
                    objLLStockMan_Intv8.setEntitled(llUploadArg.getStock());
                   // objLLStockMan_Intv8.setExpiredDate(expDate_Intv8);
                    objLLStockMan_Intv8.setLLQty(objLLStockMan_Intv8.getEntitled());
                    objLLStockMan_Intv8.setLeavePeriodeId(0);
                    objLLStockMan_Intv8.setLLStatus(PstLLStockManagement.LL_STS_AKTIF);
                    objLLStockMan_Intv8.setStNote("LL Opname");
                    objLLStockMan_Intv8.setQtyUsed(0);
                    objLLStockMan_Intv8.setQtyResidue(objLLStockMan_Intv8.getEntitled());
                        
                    if(objLLStockMan_Intv8.getLLStatus()==PstLLStockManagement.LL_STS_AKTIF){
                        if(iTaken<=objLLStockMan_Intv8.getQtyResidue()){
                            objLLStockMan_Intv8.setQtyUsed(iTaken);
                            objLLStockMan_Intv8.setQtyResidue(objLLStockMan_Intv8.getEntitled()-iTaken);
                            if(objLLStockMan_Intv8.getQtyResidue()==0){
                                objLLStockMan_Intv8.setLLStatus(PstLLStockManagement.LL_STS_TAKEN);
                            }
                            iTaken = 0;
                        }else{
                            objLLStockMan_Intv8.setQtyUsed(objLLStockMan_Intv8.getEntitled());
                            objLLStockMan_Intv8.setQtyResidue(0);
                            objLLStockMan_Intv8.setLLStatus(PstLLStockManagement.LL_STS_TAKEN);
                            iTaken -= objLLStockMan_Intv8.getEntitled();
                        }
                    }
                    
                    if(objLLStockMan_Intv8.getOID()>0){
                        /*try {
                            llStockManId = PstLLStockManagement.updateExc(objLLStockMan_Intv8);
                        } catch (DBException ex) {
                            strError = "Update data into LL Stock Management Failed";
                            ex.printStackTrace();
                        }*/
                    }else{
                        /*try {
                            llStockManId = PstLLStockManagement.insertExc(objLLStockMan_Intv8);
                        } catch (DBException ex) {
                            strError = "Insert data into LL Stock Management Failed";
                            ex.printStackTrace();
                        }*/
                    }
                    
                    //Meng-opname pengambilan ll
                    opnameLLStockTaken(employeeId, llStockManId, objLLStockMan_Intv8.getDtOwningDate(), objLLStockMan_Intv8.getQtyUsed());
                    float iOverTaken = 0;
                    if(iTaken>objLLStockMan_Intv5.getEntitled()){
                        iOverTaken = iTaken-objLLStockMan_Intv5.getEntitled();
                        iTaken -= iOverTaken;
                        opnameLLStockTaken(employeeId, 0, dateOpname, iOverTaken);
                        System.out.println("[WARNING] Terjadi pengambilan berlebih pada LL :::::::::::::::::::::: "+employeeId);
                    }
                    objLLStockMan_Intv5.setQtyUsed(iTaken);
                    objLLStockMan_Intv5.setQtyResidue(objLLStockMan_Intv5.getEntitled()-iTaken);
                    if(objLLStockMan_Intv5.getQtyResidue()<=0){
                        objLLStockMan_Intv5.setLLStatus(PstLLStockManagement.LL_STS_TAKEN);
                    }
                    int llStockMan_Int5 = 0;
                    if(objLLStockMan_Intv5.getOID()>0){
                        try {
                            llStockManId = PstLLStockManagement.updateExc(objLLStockMan_Intv5);
                        } catch (DBException ex) {
                            strError = "Update data into LL Stock Management Failed";
                            ex.printStackTrace();
                        }
                    }else{
                        try {
                            llStockManId = PstLLStockManagement.insertExc(objLLStockMan_Intv5);
                        } catch (DBException ex) {
                            strError = "Insert data into LL Stock Management Failed";
                            ex.printStackTrace();
                        }
                    }
                    
                    //mengopname ll taken yang tersisa
                    int countOpnameLL = 0;
                    for(int i=0;i<5;i++){
                        float iToGet = 0;
                        switch(i){
                            case 0:
                                iToGet = llUploadArg.getLlTakenYear1();
                                break;
                            case 1: 
                                iToGet = llUploadArg.getLlTakenYear2();
                                break;
                            case 2: 
                                iToGet = llUploadArg.getLlTakenYear3();
                                break;
                            case 3: 
                                iToGet = llUploadArg.getLlTakenYear4();
                                break;
                            case 4: 
                                iToGet = llUploadArg.getLlTakenYear5();
                                break;
                        }
                        if(dateStartPer_Intv5.getTime()==dateStartPer_Intv8.getTime()){
                            iToGet = iToGet-objLLStockMan_Intv8.getQtyUsed();
                        }
                        countOpnameLL += iToGet; 
                        if(countOpnameLL>iTaken){
                            int iOver = countOpnameLL-iTaken;
                            if(iOver<iToGet){
                                iToGet -= iOver;
                            }else{
                                iToGet = 0;
                            }
                        }
                        if(!(iToGet <= 0 )){
                            Date takenDate = (Date)dateStartPer_Intv5.clone();
                            takenDate.setYear(takenDate.getYear()+i+1);//untuk mengincrement tahun pengambilan LL
                            takenDate.setDate(takenDate.getDate()-1);
                            opnameLLStockTaken(employeeId, llStockManId, takenDate, iToGet);
                        }
                    }
                    
                }
                //Ini untuk yang tidak memiliki interval 8
                //Perbedaan terjadi pada pengambilan LL
                else{
                    //////////////////////////////////////////////////
                    float iOverTaken = 0;
                    if(iTaken>objLLStockMan_Intv5.getEntitled()){
                        iOverTaken = iTaken-objLLStockMan_Intv5.getEntitled();
                        iTaken -= iOverTaken;
                        opnameLLStockTaken(employeeId, 0, dateOpname, iOverTaken);
                    }
                    objLLStockMan_Intv5.setQtyUsed(iTaken);
                    objLLStockMan_Intv5.setQtyResidue(objLLStockMan_Intv5.getEntitled()-iTaken);
                    if(objLLStockMan_Intv5.getQtyResidue()<=0){
                        objLLStockMan_Intv5.setLLStatus(PstLLStockManagement.LL_STS_TAKEN);
                    }
                    
                    // ----
                    
                    int llStockMan_Int5 = 0;
                    if(objLLStockMan_Intv5.getOID()>0){
                        try {
                            llStockManId = PstLLStockManagement.updateExc(objLLStockMan_Intv5);
                        } catch (DBException ex) {
                            strError = "Update data into LL Stock Management Failed";
                            ex.printStackTrace();
                        }
                    }else{
                        try {
                            llStockManId = PstLLStockManagement.insertExc(objLLStockMan_Intv5);
                        } catch (DBException ex) {
                            strError = "Insert data into LL Stock Management Failed";
                            ex.printStackTrace();
                        }
                    }
                    
                    //mengopname ll taken yang tersisa
                    int countOpnameLL = 0;
                    for(int i=0;i<5;i++){
                        float iToGet = 0;
                        switch(i){
                            case 0:
                                iToGet = llUploadArg.getLlTakenYear1();
                                break;
                            case 1: 
                                iToGet = llUploadArg.getLlTakenYear2();
                                break;
                            case 2: 
                                iToGet = llUploadArg.getLlTakenYear3();
                                break;
                            case 3: 
                                iToGet = llUploadArg.getLlTakenYear4();
                                break;
                            case 4: 
                                iToGet = llUploadArg.getLlTakenYear5();
                                break;
                        }
                        
                        // System.out.println("Data to get "+i+" ::: "+iToGet);
                        countOpnameLL += iToGet; 
                        if(countOpnameLL>iTaken){
                            int iOver = countOpnameLL-iTaken;
                            if(iOver<iToGet){
                                iToGet -= iOver;
                            }else{
                                iToGet = 0;
                            }
                        }
                        
                        //  System.out.println("Data to get "+i+" ::: "+iToGet);
                        if(!(iToGet <= 0 )){
                            Date takenDate = (Date)dateStartPer_Intv5.clone();
                            takenDate.setYear(takenDate.getYear()+i+1);//untuk mengincrement tahun pengambilan LL
                            takenDate.setDate(takenDate.getDate()-1);
                            opnameLLStockTaken(employeeId, llStockManId, takenDate, iToGet);
                        }
                        
                    } // end for
            
                } // end if (LoS_8>0 && (dateStartPer_Intv8.getTime()>=dateStartPer_Intv5.getTime()))
                
            } // end if (LoS_5>0)
            
        }  // llUploadArg.getOID()<=0
        else {
            strError = "LL Upload data not found!";
        }
        
        //MENDAFTARKAN STATUS LL YANG TELAH DIPROSES
        if(strError.length()<=0){
             llUploadArg.setDataStatus(PstLLUpload.FLD_DOC_STATUS_PROCESS);
            try {
                PstLLUpload.updateExc(llUploadArg);
            } catch (DBException ex) {
                ex.printStackTrace();
            }
        }
        
        return strError;
    }
    
    /**
     * @desc mencari jumlah data ll yang telah diupload
     * per tgl
     * @return vector dari LLUpload
     */
    public static Vector getAllLLUpload(int start,int recordToGet){
        Vector vLLUploadOpname = new Vector();
        //select distinct taken_date from hr_ll_stock_taken
        DBResultSet dbResultSet = null;
        String sql = "SELECT DISTINCT "
                +PstLLUpload.fieldNames[PstLLUpload.FLD_OPNAME_DATE]
                +" FROM "+PstLLUpload.TBL_LL_UPLOAD
                +" ORDER BY "+PstLLUpload.fieldNames[PstLLUpload.FLD_OPNAME_DATE];
        if(start!=0 || recordToGet!=0){
            sql += " LIMIT "+start+","+recordToGet;
        }
    //    System.out.println("All ll upload...SQL ::: "+sql);
        try{
            dbResultSet = DBHandler.execQueryResult(sql);
            ResultSet rs = dbResultSet.getResultSet();
            while(rs.next()){
                Date date = rs.getDate(PstLLUpload.fieldNames[PstLLUpload.FLD_OPNAME_DATE]);
                vLLUploadOpname.add(Formater.formatDate(date, "yyyy-MM-dd"));
            }
        }catch(Exception ex){}
        return vLLUploadOpname;
    }
    
    public static boolean statusTakeDate(long empId,long stockId,String dateTake){
        DBResultSet dbrs = null;
        Vector listStockTaken = new Vector();
        try{
            String where = PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_EMPLOYEE_ID]+" = "+empId+" AND "
                            +PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_LL_STOCK_ID]+" = "+stockId+" AND "
                            +PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_TAKEN_DATE]+" = '"+dateTake+"'";
            
            System.out.println("Where "+where);
            
            listStockTaken = PstLlStockTaken.list(0,0,where,null);
            
            if(listStockTaken.size()>0){
                return true;
            }
            
        }catch(Exception e){
            System.out.println("EXCEPTION ::::"+e.toString());
        }
        return false;        
    }
    
    public static Date getDateInsertTakenDate(long empId,long stockId,Date opnameDate){
        DBResultSet dbrs = null;
        Vector listStockTaken = new Vector();
        try{
          
            boolean statusTakenNotEmpty = true;
            
            int tanggal = opnameDate.getDate();
            int bulan = opnameDate.getMonth()+1;
            int tahun = opnameDate.getYear() + 1900;
            
            while(statusTakenNotEmpty == true){            
                
                String dateSekarang = tahun+"-"+bulan+"-"+tanggal;
                
                Date dateformat = Formater.formatDate(dateSekarang,"yyyy-MM-dd");
                
                String dateformatString = Formater.formatDate(dateformat,"yyyy-MM-dd");
                
                statusTakenNotEmpty = statusTakeDate(empId,stockId,dateformatString);
                
                if(statusTakenNotEmpty==false)
                    return dateformat;                
                
                tanggal--;
                
                if(tanggal==0){
                    if(bulan<0){
                        tahun --;
                        bulan = 11;
                        if(bulan==1)
                            tanggal = 31;
                        else if(bulan==2)
                            tanggal = 28;  
                        else if(bulan==3)
                            tanggal = 31;
                        else if(bulan==4)
                            tanggal = 30;
                        else if(bulan==5)
                            tanggal =31;
                        else if(bulan==6)
                            tanggal =30;
                        else if(bulan==7)
                            tanggal =31;
                        else if(bulan==8)
                            tanggal =31;
                        else if(bulan==9)
                            tanggal =30;
                        else if(bulan==10)
                            tanggal =31;
                        else if(bulan==11)
                            tanggal =30;
                        else if(bulan==11)
                            tanggal =31;       
                        
                    }else{
                        if(bulan==1)
                            tanggal = 31;
                        else if(bulan==2)
                            tanggal = 28;  
                        else if(bulan==3)
                            tanggal = 31;
                        else if(bulan==4)
                            tanggal = 30;
                        else if(bulan==5)
                            tanggal =31;
                        else if(bulan==6)
                            tanggal =30;
                        else if(bulan==7)
                            tanggal =31;
                        else if(bulan==8)
                            tanggal =31;
                        else if(bulan==9)
                            tanggal =30;
                        else if(bulan==10)
                            tanggal =31;
                        else if(bulan==11)
                            tanggal =30;
                        else if(bulan==12)
                            tanggal =31;
                    }                  
                }                
            }  
        }catch(Exception e){
            System.out.println("EXCEPTION :::"+e.toString());
        }
        return null;
    }
    
    
    public static long prosesInsertDataTakenLL(int statusInsert, long stockId,long empId,float selisih, Date Opname){
        
        Date dateSekarang = getDateInsertTakenDate(empId,stockId,Opname);
        
        long StockTakenId = 0;
        
        if(statusInsert == 1){
               float qty = selisih;
               
               LlStockTaken objLlStockTaken = new LlStockTaken();
               
               objLlStockTaken.setEmployeeId(empId);
               objLlStockTaken.setLlStockId(stockId);
               objLlStockTaken.setTakenFromStatus(PstLlStockTaken.TAKEN_FROM_STATUS_SYSTEM);
               objLlStockTaken.setTakenQty(qty);
               objLlStockTaken.setTakenDate(dateSekarang);
               try{            
                    StockTakenId = PstLlStockTaken.insertExc(objLlStockTaken);
               }catch(Exception e){
                    System.out.println("EXCEPTION ::"+e.toString());
               }  
               return StockTakenId;
               
        }else if(statusInsert == 2){
               float qty = 0 - selisih;
               
               LlStockTaken objLlStockTaken = new LlStockTaken();
               
               objLlStockTaken.setEmployeeId(empId);
               objLlStockTaken.setLlStockId(stockId);
               objLlStockTaken.setTakenFromStatus(PstLlStockTaken.TAKEN_FROM_STATUS_SYSTEM);
               objLlStockTaken.setTakenQty(qty);
               objLlStockTaken.setTakenDate(dateSekarang);
               try{            
                    StockTakenId = PstLlStockTaken.insertExc(objLlStockTaken);
               }catch(Exception e){
                    System.out.println("EXCEPTION ::"+e.toString());
               }  
               return StockTakenId;
        }
        return 0;
    }
    
    public static int getTotalExpired(long stock_ll_id){
        
        int result = 0;
        DBResultSet dbResultSet = null;
        
        try{
            
            String sql = "SELECT "+PstLlStockExpired.fieldNames[PstLlStockExpired.FLD_EXPIRED_QTY]+" FROM "+
                    PstLlStockExpired.TBL_HR_LL_STOCK_EXPIRED+" WHERE "+
                    PstLlStockExpired.fieldNames[PstLlStockExpired.FLD_LL_STOCK_ID]+"="+stock_ll_id;
            
            dbResultSet = DBHandler.execQueryResult(sql);
            ResultSet rs = dbResultSet.getResultSet();
            
            while(rs.next()){
                int qty = rs.getInt(1);
                result = result + qty;
                
            }
            return result;
        }catch(Exception e){
            System.out.println("Exception "+e.toString());
        }finally{
            DBResultSet.close(dbResultSet);
        }
        
        return 0;
    }
}