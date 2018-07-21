/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dimata.harisma.session.employee;

import com.dimata.harisma.entity.employee.EmpMessage;
import com.dimata.harisma.entity.employee.Employee;
import com.dimata.harisma.entity.employee.PstEmpMessage;
import com.dimata.harisma.entity.employee.PstEmployee;
import com.dimata.harisma.entity.search.SrcEmployee;
import com.dimata.qdep.db.DBException;
import com.dimata.qdep.db.DBHandler;
import com.dimata.qdep.db.DBResultSet;
import com.dimata.util.Formater;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Vector;

/**
 *
 * @author D
 */
public class SessEmpMessage {

    
    public static final int DATE_FORMAT_ID = 0;
    public static final int DATE_FORMAT_OTHER = 1;
    
    /**
     * Mengambil list message yang dikirimkan kepada employee
     * @return Vector
     */
    public static Vector listDataEmpMessageReady(Date currDate){
        Vector vData = new Vector(1,1);
        DBResultSet dbrs = null;
        try {
            String query = "SELECT " + "emp." 
                    + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] 
                    + ",emp." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME] 
                    + ",emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] 
                    + ",emp." + PstEmployee.fieldNames[PstEmployee.FLD_BARCODE_NUMBER] 
                    +",emp." + PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID] 
                    + ",empMsg." + PstEmpMessage.fieldNames[PstEmpMessage.FLD_EMP_MESSAGE_ID] 
                    + ",empMsg." + PstEmpMessage.fieldNames[PstEmpMessage.FLD_MESSAGE] 
                    + ",empMsg." + PstEmpMessage.fieldNames[PstEmpMessage.FLD_START_DATE] 
                    + ",empMsg." + PstEmpMessage.fieldNames[PstEmpMessage.FLD_END_DATE] 
                    + ",empMsg." + PstEmpMessage.fieldNames[PstEmpMessage.FLD_ID_SEND] 
                    + " FROM " + PstEmployee.TBL_HR_EMPLOYEE 
                    + " AS emp " + " RIGHT JOIN " + PstEmpMessage.TBL_HR_EMP_MESSAGE 
                    + " AS empMsg " + " ON emp." 
                    + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] 
                    + " = empMsg." + PstEmpMessage.fieldNames[PstEmpMessage.FLD_EMPLOYEE_ID] 
                    + " WHERE emp." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] 
                    + " = " + PstEmployee.NO_RESIGN
                    + " AND emp."+PstEmployee.fieldNames[PstEmployee.FLD_BARCODE_NUMBER]
                    + " != \"\""
                    + " AND empMsg."+PstEmpMessage.fieldNames[PstEmpMessage.FLD_START_DATE]
                    + " <= \""+Formater.formatDate(currDate, "yyyy-MM-dd")+"\""
                    + " AND empMsg."+PstEmpMessage.fieldNames[PstEmpMessage.FLD_END_DATE]
                    + " >= \""+Formater.formatDate(currDate, "yyyy-MM-dd")+"\""
                    ;
            
            System.out.println("[SQL] SessEmpMessage.listDataEmpMessageReady :::: "+query);
            dbrs = DBHandler.execQueryResult(query);
            ResultSet rs = dbrs.getResultSet();
            while(rs.next()){
                Vector vTemp = new Vector(1,1);
                Employee emp = new Employee();
                EmpMessage empMsg = new EmpMessage();

                emp.setOID(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]));
                emp.setFullName(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]));
                emp.setEmployeeNum(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]));
                emp.setBarcodeNumber(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_BARCODE_NUMBER]));
                emp.setPositionId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]));

                empMsg.setEmployeeId(rs.getLong(PstEmpMessage.fieldNames[PstEmpMessage.FLD_EMPLOYEE_ID]));
                empMsg.setOID(rs.getLong(PstEmpMessage.fieldNames[PstEmpMessage.FLD_EMP_MESSAGE_ID]));
                empMsg.setMessage(rs.getString(PstEmpMessage.fieldNames[PstEmpMessage.FLD_MESSAGE]));
                empMsg.setStartDate(rs.getDate(PstEmpMessage.fieldNames[PstEmpMessage.FLD_START_DATE]));
                empMsg.setEndDate(rs.getDate(PstEmpMessage.fieldNames[PstEmpMessage.FLD_END_DATE]));
                empMsg.setIsSend(rs.getInt(PstEmpMessage.fieldNames[PstEmpMessage.FLD_ID_SEND]));

                vTemp.add(emp);
                vTemp.add(empMsg);
                vData.add(vTemp);
            }
            return vData;
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (DBException ex) {
            ex.printStackTrace();
        }
        return new Vector(1,1);
    }
    
    
    /**
     * Mengambil list message yang dikirimkan kepada employee
     * @param srcEmployee : menampung acuan pencarian
     * @param start : awal liat
     * @param recordtoget : banyak data yang akan digunakan
     * @param orderBy : diurutkan diurutkan dengan
     * @return Vector
     */
    public static Vector listDataEmpMessage(SrcEmployee srcEmp,int start, int recordToGet, String orderBy){
        Vector vData = new Vector(1,1);
        DBResultSet dbrs = null;
        if(srcEmp!=null){
            try {
                String query = "SELECT " + "emp." 
                        + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] 
                        + ",emp." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME] 
                        + ",emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] 
                        + ",emp." + PstEmployee.fieldNames[PstEmployee.FLD_BARCODE_NUMBER] 
                        +",emp." + PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID] 
                        + ",empMsg." + PstEmpMessage.fieldNames[PstEmpMessage.FLD_EMP_MESSAGE_ID] 
                        + ",empMsg." + PstEmpMessage.fieldNames[PstEmpMessage.FLD_MESSAGE] 
                        + ",empMsg." + PstEmpMessage.fieldNames[PstEmpMessage.FLD_START_DATE] 
                        + ",empMsg." + PstEmpMessage.fieldNames[PstEmpMessage.FLD_END_DATE] 
                        + ",empMsg." + PstEmpMessage.fieldNames[PstEmpMessage.FLD_ID_SEND] 
                        + " FROM " + PstEmployee.TBL_HR_EMPLOYEE 
                        + " AS emp " + " LEFT JOIN " + PstEmpMessage.TBL_HR_EMP_MESSAGE 
                        + " AS empMsg " + " ON emp." 
                        + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] 
                        + " = empMsg." + PstEmpMessage.fieldNames[PstEmpMessage.FLD_EMPLOYEE_ID] 
                        + " WHERE emp." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] 
                        + " = " + PstEmployee.NO_RESIGN
                        + " AND emp."+PstEmployee.fieldNames[PstEmployee.FLD_BARCODE_NUMBER]
                        + " != \"\"";
                        ;
                if (srcEmp.getName().length() > 0) {
                    query = query + " AND emp." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME] + " LIKE \"%" + srcEmp.getName() + "%\"";
                }
                if (srcEmp.getEmpnumber().length() > 0) {
                    query = query + " AND emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] + " LIKE \"%" + srcEmp.getEmpnumber() + "%\"";
                }
                if (srcEmp.getEmpCategory() > 0) {
                    query = query + " AND emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID] + " = " + srcEmp.getEmpCategory() + "";
                }
                if (srcEmp.getDepartment() > 0) {
                    query = query + " AND emp." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] + " = " + srcEmp.getDepartment() + "";
                }
                if (srcEmp.getSection() > 0) {
                    query = query + " AND emp." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID] + " = " + srcEmp.getSection() + "";
                }
                if (srcEmp.getPosition() > 0) {
                    query = query + " AND emp." + PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID] + " = " + srcEmp.getPosition() + "";
                }
                
                if(orderBy.length()>0){
                    query = query + " ORDER BY "+orderBy;
                }
                
                if(start>0 && recordToGet >0){
                    query = query + " LIMIT "+start+","+recordToGet;
                }
               // System.out.println("[SQL] SessEmpMessage :::: "+query);
                dbrs = DBHandler.execQueryResult(query);
                ResultSet rs = dbrs.getResultSet();
                while(rs.next()){
                    Vector vTemp = new Vector(1,1);
                    Employee emp = new Employee();
                    EmpMessage empMsg = new EmpMessage();
                    
                    emp.setOID(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]));
                    emp.setFullName(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]));
                    emp.setEmployeeNum(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]));
                    emp.setBarcodeNumber(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_BARCODE_NUMBER]));
                    emp.setPositionId(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]));
                    
                    empMsg.setEmployeeId(rs.getLong(PstEmpMessage.fieldNames[PstEmpMessage.FLD_EMPLOYEE_ID]));
                    empMsg.setOID(rs.getLong(PstEmpMessage.fieldNames[PstEmpMessage.FLD_EMP_MESSAGE_ID]));
                    empMsg.setMessage(rs.getString(PstEmpMessage.fieldNames[PstEmpMessage.FLD_MESSAGE]));
                    empMsg.setStartDate(rs.getDate(PstEmpMessage.fieldNames[PstEmpMessage.FLD_START_DATE]));
                    empMsg.setEndDate(rs.getDate(PstEmpMessage.fieldNames[PstEmpMessage.FLD_END_DATE]));
                    empMsg.setIsSend(rs.getInt(PstEmpMessage.fieldNames[PstEmpMessage.FLD_ID_SEND]));
                    
                    vTemp.add(emp);
                    vTemp.add(empMsg);
                    vData.add(vTemp);
                }
                return vData;
            } catch (SQLException ex) {
                ex.printStackTrace();
            } catch (DBException ex) {
                ex.printStackTrace();
            }
        }
        return new Vector(1,1);
    }
    
    
    /**
     * menghtung banyak data dari query pengecek banyak list message
     * @param srcEmployee : menampung acuan pencarian
     */
    public static int countDataEmpMessage(SrcEmployee srcEmp){
        int maxData = 0;
        DBResultSet dbrs = null;
        if(srcEmp!=null){
            try {
                String query = "SELECT " 
                        + "COUNT(emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+")"
                        + " FROM " + PstEmployee.TBL_HR_EMPLOYEE 
                        + " AS emp " + " LEFT JOIN " + PstEmpMessage.TBL_HR_EMP_MESSAGE 
                        + " AS empMsg " + " ON emp." 
                        + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] 
                        + " = empMsg." + PstEmpMessage.fieldNames[PstEmpMessage.FLD_EMPLOYEE_ID] 
                        + " WHERE emp." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] 
                        + " = " + PstEmployee.NO_RESIGN
                        + " AND emp."+PstEmployee.fieldNames[PstEmployee.FLD_BARCODE_NUMBER]
                        + " != \"\"";
                        ;
                        
                if (srcEmp.getName().length() > 0) {
                    query = query + " AND emp." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME] + " LIKE \"%" + srcEmp.getName() + "%\"";
                }
                if (srcEmp.getEmpnumber().length() > 0) {
                    query = query + " AND emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] + " LIKE \"%" + srcEmp.getEmpnumber() + "%\"";
                }
                if (srcEmp.getEmpCategory() > 0) {
                    query = query + " AND emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID] + " = " + srcEmp.getEmpCategory() + "";
                }
                if (srcEmp.getDepartment() > 0) {
                    query = query + " AND emp." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] + " = " + srcEmp.getDepartment() + "";
                }
                if (srcEmp.getSection() > 0) {
                    query = query + " AND emp." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID] + " = " + srcEmp.getSection() + "";
                }
                if (srcEmp.getPosition() > 0) {
                    query = query + " AND emp." + PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID] + " = " + srcEmp.getPosition() + "";
                }
                
                dbrs = DBHandler.execQueryResult(query);
                ResultSet rs = dbrs.getResultSet();
                while(rs.next()){
                    maxData = rs.getInt(1);
                }
                return maxData;
            } catch (SQLException ex) {
                ex.printStackTrace();
            } catch (DBException ex) {
                ex.printStackTrace();
            }
        }
        return 0;
    }
    
    
    
    
    
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
    public static Date parseStringToDate(String strDate, int dateFormat){
        String yyyy="1900",mm="1",dd="0";
        int iyyyy=0,imm=0,idd=0;
        switch(dateFormat){
            case DATE_FORMAT_ID : 
                //13-12-2008
                dd = strDate.substring(0,2);
                if("0".equals(dd.substring(0,1))){
                    dd = dd.substring(1,2);
                }
                mm = strDate.substring(3,5);
                if("0".equals(mm.substring(0,1))){
                    mm = mm.substring(1,2);
                }
                yyyy = strDate.substring(6,10);
                break;
            case DATE_FORMAT_OTHER : 
                //2008-12-13
                yyyy = strDate.substring(0,4);
                mm = strDate.substring(5,7);
                if("0".equals(mm.substring(0,1))){
                    mm = mm.substring(1,2);
                }
                dd = strDate.substring(8,10);
                if("0".equals(dd.substring(0,1))){
                    dd = dd.substring(1,2);
                }
                break;
        }
        iyyyy = Integer.parseInt(yyyy)-1900;
        imm = Integer.parseInt(mm)-1;
        idd = Integer.parseInt(dd);
        try{
          //  System.out.println("DATE OUT :::::::: "+new Date(iyyyy, imm, idd));
            return new Date(iyyyy, imm, idd);
        }catch(Exception ex){}
        return new Date();
    }
    
    /**
     * proses : mencari employee yang akan ulang tahun besok.
     * param currDate : tanggal sekarang
     * return vector data employee yang akan ulang tahun.
     */
    
    public static Vector listEmpBirdDay(Date currDate){
        Vector vEmp = new Vector(1,1);
        Vector vEmpBirdDayAMounth = new Vector(1,1);
        vEmpBirdDayAMounth = SessEmployee.getBirthdayReminder();
        for(int i=0;i<vEmpBirdDayAMounth.size();i++){
            Vector vTemp = new Vector(1,1);
            Employee emp = new Employee();
            vTemp = (Vector)vEmpBirdDayAMounth.get(i);
            emp = (Employee)vTemp.get(0);
            if(emp.getBirthDate().getMonth()==currDate.getMonth()){
                if(emp.getBirthDate().getDate()==currDate.getDate()+1){
                    vEmp.add(emp);
                }
            }
        }
        return vEmp;
    }
    
}
