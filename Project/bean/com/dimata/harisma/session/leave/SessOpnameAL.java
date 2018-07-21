/*
 * ******************************************************
 * ***************** DIMATA SORA JAYATE *****************
 * ******************************************************
 * Class Description : [project description ... ] 
 * Imput Parameters  : [input parameter ...]
 * Output            : [output ...] 
 * ******************************************************
 */

package com.dimata.harisma.session.leave;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.Vector;
import java.text.*;
import java.util.*;
import com.dimata.util.*;
import java.util.Date;

import com.dimata.util.Excel;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Vector;
import com.dimata.qdep.db.*;
import com.dimata.gui.jsp.*;
import java.sql.*;
import com.dimata.harisma.entity.attendance.*;
import com.dimata.harisma.entity.leave.*;

import com.dimata.harisma.entity.employee.*; 
import com.dimata.system.entity.PstSystemProperty;

/**
 * @author gArtha
 * Ini dipergunakan untuk membaca data dan menjadikan ve
 * Ini dipergunakan untuk membaca data dan menjadikan ve
 */

public class SessOpnameAL {
    
    /**Membaca object menjadi vector
     * @Param obj Object
     * @Param numCol int
     * @Return Vector
    */
    
    public static Vector readObjToVecor(Object obj,int numCol) {

        Vector vDataUp = new Vector(1,1);
        try {
            byte byteText[] = null;
            byteText = (byte[]) obj;
            ByteArrayInputStream inStream;
            inStream = new ByteArrayInputStream(byteText);

            // jumlah kolom untuk al opname excel
            Vector vTemp = new Vector(1,1);
            vTemp = Excel.ReadStream((InputStream) inStream, numCol);
            
            int index = 0;
            for(int i=0;i<vTemp.size();i+=numCol){
                Vector vLine = new Vector();
                for(int j=0;j<numCol;j++){
                    vLine.add(vTemp.get(index));
                    
                    index++;
                }
                
                vDataUp.add(vLine);
            }
            inStream.close();

        } catch (Exception e) {
            System.out.println("harisma.session.leave.SessOpnameAL ::: ERROR : " + e.toString());
        } finally{
            return vDataUp;
        }
    }
    
    public static int prosesLeave(){
        int a = 0;
    
        return 1;
    }
    
    
    /**
    * 
    * @author Putu Roy Andika
    * @desc method untuk mendapatkan employee yang comancing date hari ini
    */
    
    public Vector<Employee> getEmployeeCommencingNow(Date dateClosing){
        DBResultSet dbrs = null;
        
        try{       
            String tahunSekarang = Formater.formatDate(dateClosing, "MM-dd");
        
            String sql = "";
        
            sql = "SELECT "+PstEmployee.FLD_EMPLOYEE_ID+","+PstEmployee.FLD_COMMENCING_DATE+" FROM "+PstEmployee.TBL_HR_EMPLOYEE+
                " WHERE "+PstEmployee.FLD_COMMENCING_DATE+" LIKE '%"+tahunSekarang+"'";
        
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
        
            Vector employee_Id = new Vector();
                
            while(rs.next()){
                Employee employeedata = new Employee();
                employeedata.setOID(rs.getLong(1));
                employeedata.setCommencingDate(rs.getDate(2));
               
                employee_Id.add(employeedata);
            }
        
            return employee_Id;
            
        }catch(Exception e){            
            System.out.println("Exception "+e);
        }
        
        return new Vector(1,1);
    }
    
    //close leave by Period    
    
    public static Vector getEmployeeByPeriod(){
        
        Vector employee = new Vector();
        try{
            
            
            
        }catch(Exception e){
            System.out.println("Exception "+e.toString());            
        }   
 
        return employee;
        
    }
    
    //By Roy Andika
    //Close Leave by commencing date    
    public void closeLeavebyCommencingDate(Date DateClosing){
        
        Vector employeeList= getEmployeeCommencingNow(DateClosing);
 
        if(employeeList.size() <= 0){
            System.out.println("jumlah employee yang commencing date sekarang adalah null");
        }else{
           
            System.out.println("Proses close leave start ..");
           
            I_Leave leaveConfig = null;

            try {
                leaveConfig = (I_Leave) (Class.forName(PstSystemProperty.getValueByName("LEAVE_CONFIG")).newInstance());
            } catch (Exception e) {
                System.out.println("Exception : " + e.getMessage());
            }
            
            Vector employeeLevel = new Vector();
            Vector employeeCategory = new Vector();
            
            for(int i = 0 ; i < employeeList.size() ; i++){           
                //mendapatkan level employee
                Employee employee = (Employee) employeeList.get(i);
                employeeLevel.add(leaveConfig.getCategory(employee.getOID()));                
                
                //mendapatkan category employee
                employeeCategory.add(leaveConfig.getLevel(employee.getOID()));                   
                
                Vector valueEntitle = new Vector();
                
                //value untuk entitle
                int LoS = (int) DateCalc.dayDifference(employee.getCommencingDate(), new Date());
                float intvalueEntitle = leaveConfig.getALEntitleAnualLeave(leaveConfig.getLevel(employee.getOID()),
                        leaveConfig.getCategory(employee.getOID()), LoS, employee.getCommencingDate(),DateClosing );
                
                //insert data ke hr_al_stock_management                
                System.out.println("Insert data ke tabel hr_al_stock_management : BELUM BERES !!!!");
                // ini BELUM BERES !!!!
                LeaveConfigHR DataAnnualLeave = new LeaveConfigHR();

            }
        }       
        
        Vector tempEmployee = new Vector();
 
    }
    
    //close leave period by period
    
    public void closeLeavebyPeriod(){
               
        
    }
    
    
    public static void insertNewDataAL(Vector valueAL,Vector emp_ID){
        String sql = "";
        Date dateSekarang = new Date();
        String note = "";
        
        float AlQty = 0;
        float Entitled = 0;
        float QtyUsed = 0;
        float QtyResidue = 0;
        float QtyUseds = 0;        
       
        try{
            
            for(int i=0 ; i<emp_ID.size(); i++){
                
                long employee_ID = Long.parseLong(emp_ID.get(i).toString());
                AlStockManagement alstockmanagement = new AlStockManagement();
            
                alstockmanagement.setAlQty(AlQty);
                alstockmanagement.setAlStatus(1);
                alstockmanagement.setEntitled(Entitled);
                alstockmanagement.setStNote(note);
                alstockmanagement.setQtyUsed(QtyUsed);
                alstockmanagement.setEmployeeId(employee_ID);  
                alstockmanagement.setDtOwningDate(dateSekarang);
                alstockmanagement.setQtyResidue(QtyResidue);
                alstockmanagement.setQtyUsed(QtyUseds);
   
            }
            
        }catch(Exception e){
            System.out.println("Exception : "+e.toString());
        }   
    }
    
    
    public static Vector getPreviousAL(Vector employeeID){
        Vector temp = new Vector();
        Date currentDate = new Date();
        
        int yearPrevious = Integer.parseInt(Formater.formatDate(currentDate, "yyyy"))-1;
                
        for(int i=0; i<employeeID.size();i++){
            long employee_ID;            
            
            Vector employee = new Vector();
            employee_ID = Long.parseLong(employeeID.get(i).toString());     
            String sql = "";
            try{
                sql = "SELECT "+PstEmployee.FLD_COMMENCING_DATE;
            }catch(Exception e){
                System.out.println("Exception "+e.toString());
            }        
        } 
        return temp;        
    }
    
   /*
    * untuk menegcek apakah sebelumnya ada opname yang belum diproses pada periode itu
   */ 
   public static boolean checkOpnameBefore(long empID, Date commancingDate){
       
       Date currentDate = new Date();
       String currenDateInt = Formater.formatDate(currentDate, "yyyy-MM-dd");
       String fixDate = currenDateInt+" 00:00:00";
       System.out.println("fix date sekarang "+fixDate);
       String dateTest = "2009-12-05 00:00:00";
       String commancingDateFix = commancingDate.toString()+" 00:00:00";
       boolean result = false;
       String sql = "";
       DBResultSet dbrs = null;
       try{
           sql+="SELECT "+PstAlUpload.fieldNames[PstAlUpload.FLD_EMPLOYEE_ID]+" FROM "+PstAlUpload.TBL_AL_UPLOAD+
                   " WHERE "+PstAlUpload.fieldNames[PstAlUpload.FLD_DATA_STATUS]+" = 0 AND "+PstAlUpload.fieldNames[PstAlUpload.FLD_OPNAME_DATE]+
                   " BETWEEN '"+commancingDate+"' AND '"+dateTest+"' AND "+PstAlUpload.fieldNames[PstAlUpload.FLD_EMPLOYEE_ID]+
                   " = '"+empID+"'";
           
           System.out.println("sql cek opname before "+sql);
           
           dbrs = DBHandler.execQueryResult(sql);
	   ResultSet rs = dbrs.getResultSet();
           while(rs.next()){
               return true;
           }
           
       }catch(Exception e){
           System.out.println("Exception "+e.toString());
       }       
       return result;       
   }

}