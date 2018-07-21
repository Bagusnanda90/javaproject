/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dimata.harisma.session.employee;

import com.dimata.harisma.entity.employee.Employee;
import com.dimata.harisma.entity.employee.PstEmployee;
import com.dimata.harisma.entity.masterdata.FingerPrint;
import com.dimata.harisma.entity.masterdata.PstFingerPrint;
import com.dimata.qdep.db.DBHandler;
import com.dimata.qdep.db.DBResultSet;
import com.dimata.system.entity.PstSystemProperty;
import java.sql.ResultSet;
import java.util.Vector;

/**
 *
 * @author artha
 */

public class SessFingerPrint {
    /**
     * Mncari Nomor Finger Berikutnya dari yang atelah ada
     */
    public static int getNextFingerPrint(){
        int nextFingerPrint = 0;
        int iIntervalFinger = 0;
        try{
            iIntervalFinger = Integer.parseInt(String.valueOf(PstSystemProperty.getValueByName("FINGER_PRINT_NUMBER")));
            nextFingerPrint = maxFingerPrintNum()+iIntervalFinger;
        }catch(Exception ex){}
        return nextFingerPrint;
    }
    
    
    private static int maxFingerPrintNum(){
        DBResultSet dbrs = null;
        int result = 0;
        try{
                String sql = "SELECT MAX("+PstFingerPrint.fieldNames[PstFingerPrint.FLD_FINGER_PRINT]
                        +") FROM " + PstFingerPrint.TBL_HR_FINGER_PRINT;

                dbrs = DBHandler.execQueryResult(sql);
                ResultSet rs = dbrs.getResultSet();

                while(rs.next()) { 
                    result = rs.getInt(1); 
                }
                rs.close();
        }catch(Exception e){
                System.out.println("err : "+e.toString());
        }finally{
                DBResultSet.close(dbrs);
                return result;
        }
    }
    
    /**
     * Mencari emp num yang belum dipergunakan
     */
    public static Vector listEmployeeBarcodeFree(){
        Vector vList = new Vector(1,1);
        String whereClause = PstFingerPrint.fieldNames[PstFingerPrint.FLD_EMPLOYEE_NUM]
                +" NOT IN ("+getEmployeeBarcode()+")";
        vList = PstFingerPrint.list(0, 0, whereClause, PstFingerPrint.fieldNames[PstFingerPrint.FLD_EMPLOYEE_NUM]);
        return vList;
    }
    
    /**
     * Mencari employee num yang sudah dipakai pada employee
     */
    public static String getEmployeeBarcode(){
        String strBarcode = "\'-1\'";
        Vector vListEmp = new Vector(1,1);
        String whereClause = PstEmployee.fieldNames[PstEmployee.FLD_BARCODE_NUMBER]
                +" != \"\"";
        vListEmp = PstEmployee.list(0, 0, whereClause, null);
        for(int i=0;i<vListEmp.size();i++){
            Employee emp = new Employee();
            emp = (Employee)vListEmp.get(i);
            strBarcode += ",\'"+emp.getBarcodeNumber()+"\'";
        }
        return strBarcode;
    }
    
    public static synchronized boolean synchronizeDataFromEployee(){
        boolean isSuccess = false;
        String whereClause = PstEmployee.fieldNames[PstEmployee.FLD_BARCODE_NUMBER]
                +" != \"\"";
        
        Vector vListEmp = new Vector(1,1);
        vListEmp = PstEmployee.list(0, 0, whereClause, null);
        System.out.println("-----------------------------------------------------");
        System.out.println("-----------START SYNCHRONIZE FINGER PRINT-------------");
        System.out.println("-----------------------------------------------------");
        for(int i=0;i<vListEmp.size();i++){
            Employee emp = new Employee();
            emp = (Employee)vListEmp.get(i);
            if(emp.getBarcodeNumber()!=null){
                if(emp.getBarcodeNumber().length()>0){
                    if(!PstFingerPrint.checkEmpNum(emp.getBarcodeNumber())){
                        int empFinget = -1;
                        try{
                            empFinget = Integer.parseInt(emp.getEmpPin());
                            if(empFinget>-1){
                                FingerPrint fPrint = new FingerPrint();
                                fPrint.setEmployeeNum(emp.getBarcodeNumber());
                                fPrint.setFingerPrint(empFinget);
                                try{
                                    PstFingerPrint.insertExc(fPrint);
                                    System.out.println(" - BARCODE : "+emp.getBarcodeNumber()+" ["+empFinget+"]");
                                }catch(Exception ex){}
                            }
                        }catch(Exception ex){}
                    }
                }
            }
        }
        return isSuccess;
    }
    
}
