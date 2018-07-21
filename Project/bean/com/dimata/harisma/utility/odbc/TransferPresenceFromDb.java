/*
 * TransferPresenceFromDb.java
 *
 * Created on September 15, 2004, 11:04 AM
 */

package com.dimata.harisma.utility.odbc;

/**
 *
 * @author  gedhy
 */

import java.util.*;
import java.sql.*;

import com.dimata.qdep.db.*;
import com.dimata.harisma.entity.attendance.*; 
import com.dimata.harisma.entity.masterdata.*; 
import com.dimata.harisma.entity.employee.*; 

public class TransferPresenceFromDb  extends DBConnection   
{    
    public static final String tableName = "Cardraw";
    
    public static final int ACTION_OUT_HOME = 0;
    public static final int ACTION_IN = 1;            
    public static final int ACTION_OUT_ON_DUTY = 2;
    public static final int ACTION_IN_LUNCH = 3;
    public static final int ACTION_IN_BREAK = 4;
    public static final int ACTION_IN_CALLBACK = 5;
    
    public String driver = "org.gjt.mm.mysql.Driver";
    public String url = "jdbc:mysql://localhost:3306/harisma";
    
    
    public static final String actionNames[] = 
    {
        "Out Home",
        "In",
        "Out On Duty",
        "In Lunch",
        "In Break",
        "In Callback"
    };
    

    public Vector retrieveData()
    {
        Connection dbConn = null;
        Statement stmt = null; 
        Vector result = new Vector(1,1);            
        try{
            String sql = "SELECT * FROM " + tableName + 
                         " ORDER BY FCIDNUMBER";                               
            
            System.out.println("sql : "+sql);
            dbConn = doConnect(driver,url);                
            stmt = dbConn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);            
            while(rs.next())
            {                                
                Vector vectTemp = new Vector(1,1);
                vectTemp.add(rs.getDate(1));
                vectTemp.add(rs.getString(2));
                vectTemp.add(rs.getString(3));
                vectTemp.add(rs.getString(4));                
                result.add(vectTemp);                 
            }
        }
        catch(Exception e)
        {
            System.out.println("err retrieve : "+e.toString());
        }
        finally
        {
            doDisconnect();
            return result;
        } 
    }        
       
    public int deleteDataPresence(String fdDate, String fcTime, String idNumber, String fcStatus)
    {
        Connection dbConn = null;
        Statement stmt = null;                        
        int result = 0;
        try{                
            String sql = "DELETE FROM " + tableName +
                         " WHERE FDDATE = #" + fdDate + "#" + 
                         " AND FCTIME = '" + fcTime + "'" +
                         " AND FCIDNUMBER = '" + idNumber + "'" +
                         " AND FCSTATUS = '" + fcStatus + "'";                

            System.out.println("sql delete : "+sql);
            dbConn = doConnect(driver,url);                
            stmt = dbConn.createStatement();
            result = stmt.executeUpdate(sql);                                

        }catch(Exception e){
            System.out.println("err delete : "+e.toString());
        }finally{
            doDisconnect(); 
            return result;
        }
    }
    

    public int deleteDataPresence()
    {
        Connection dbConn = null;
        Statement stmt = null;                        
        int result = 0;
        try{                
            String sql = "DELETE FROM " + tableName;

            System.out.println("sql delete : "+sql);
            dbConn = doConnect(driver,url);                
            stmt = dbConn.createStatement();
            result = stmt.executeUpdate(sql);                  

        }catch(Exception e){
            System.out.println("err delete : "+e.toString());
        }finally{
            doDisconnect(); 
            return result;
        }
    }

    public Vector transferDataPresence()
    {
        TransferPresenceFromDb transfer = new TransferPresenceFromDb();
        Vector vectPresenceData = transfer.retrieveData();                     
        if(vectPresenceData!=null && vectPresenceData.size()>0)
        {            
            int maxResult = vectPresenceData.size();
            for(int i=0; i<maxResult; i++)
            {
                Vector tr = (Vector)vectPresenceData.get(i);
                
                // proses pembuatan object presence untuk diproses 
                Presence presence = new Presence();                         
                long employeeId = PstEmployee.getEmployeeByBarcode(String.valueOf(tr.get(2)));
                presence.setEmployeeId(employeeId);   
                
                int intPresenceStatusTemp = Integer.parseInt(String.valueOf(tr.get(3)));
                int intPresenceStatus = 0;
                switch(intPresenceStatusTemp)
                {
                    case ACTION_OUT_HOME : 
                        intPresenceStatus = Presence.STATUS_OUT;
                        break;
                        
                    case ACTION_IN :
                        intPresenceStatus = Presence.STATUS_IN;
                        break;
                        
                    case ACTION_OUT_ON_DUTY :
                        intPresenceStatus = Presence.STATUS_OUT_ON_DUTY;
                        break;

                    case ACTION_IN_LUNCH :
                        intPresenceStatus = Presence.STATUS_IN_PERSONAL;
                        break;

                    case ACTION_IN_BREAK :
                        intPresenceStatus = Presence.STATUS_IN_PERSONAL;
                        break;

                    case ACTION_IN_CALLBACK :
                        intPresenceStatus = Presence.STATUS_CALL_BACK;
                        break;
                        
                    default :      
                        break;
                }
                
                presence.setStatus(intPresenceStatus);
                
                java.util.Date tempPresDate = (java.util.Date)tr.get(0);
                String tempPresTime = String.valueOf(tr.get(1));
                int y = tempPresDate.getYear();
                int M = tempPresDate.getMonth();
                int d = tempPresDate.getDate();
                int h = Integer.parseInt(tempPresTime.substring(0,2));
                int m = Integer.parseInt(tempPresTime.substring(2,4));
                java.util.Date presenceDateTime = new java.util.Date(y, M, d, h, m);
                presence.setPresenceDatetime(presenceDateTime);
                
                presence.setAnalyzed(0);
                presence.setTransferred(PstPresence.PRESENCE_NOT_TRANSFERRED);
                
                
                // insert data ke table presence harisma
                boolean objPresenceExist = false;
                long presenceId = 0;
                try
                {
                    if(employeeId != 0)
                    {
                        objPresenceExist = PstPresence.presenceExist(presence);
                        if(!objPresenceExist)
                        {
                            presenceId = PstPresence.insertExc(presence);
                        }
                        // delete dari database  
                        transfer.deleteDataPresence(com.dimata.util.Formater.formatDate(tempPresDate,"MM-dd-yyyy"), String.valueOf(tr.get(1)), String.valueOf(tr.get(2)), String.valueOf(tr.get(3)));
                    }
                }
                catch(Exception e)
                {
                    System.out.println("Err : " + e.toString());   
                }
                       
                
                // untuk check ke empSchedule
                // update presence (IN or OUT) on employee schedule  
                if(!objPresenceExist)
                {
                    if(presenceId != 0)
                    {
                        long periodId = PstPeriod.getPeriodIdBySelectedDate(presence.getPresenceDatetime());                                

                        int updatedFieldIndex = -1;  
                        long updatePeriodId = periodId;
                        Vector vectFieldIndex = PstEmpSchedule.getFieldIndexWillUpdated(periodId, presence.getEmployeeId(), presence.getStatus(), presence.getPresenceDatetime());                                
                        if(vectFieldIndex!=null && vectFieldIndex.size()==2)
                        {
                            updatePeriodId = Long.parseLong(String.valueOf(vectFieldIndex.get(0)));
                            updatedFieldIndex = Integer.parseInt(String.valueOf(vectFieldIndex.get(1)));
                        }

                        int updateStatus = 0;  
                        try
                        {
                            updateStatus = PstEmpSchedule.updateScheduleDataByPresence(updatePeriodId, presence.getEmployeeId(), updatedFieldIndex, presence.getPresenceDatetime());                    
                            if(updateStatus>0)
                            {
                                presence.setTransferred(PstPresence.PRESENCE_TRANSFERRED);
                                PstPresence.updateExc(presence);                        
                            }

                        }
                        catch(Exception e)
                        {
                            System.out.println("Update Presence exc : "+e.toString());
                        }
                    }
                }
            } 
        }                  
        return vectPresenceData;
    }        
    

    /**
     * Testing method
     */    
    public static void main(String args[])  
    {
        TransferPresenceFromDb transfer = new TransferPresenceFromDb(); 
        
        /*
        int result = transfer.deleteDataPresence();                        
        System.out.println("result : " + result);                 
        Vector vectResult = transfer.retrieveData();
        System.out.println("Size : " + vectResult.size());        
        */
        
        
        Vector result = transfer.transferDataPresence();
        if(result!=null && result.size()>0)
        {            
            int maxResult = result.size();
            for(int i=0; i<maxResult; i++){
                Vector tr = (Vector)result.get(i);
                System.out.print(String.valueOf(tr.get(0)));
                System.out.print(String.valueOf(tr.get(1)));
                System.out.print(String.valueOf(tr.get(2)));
                System.out.println(String.valueOf(tr.get(3)));                
            } 
        }         
    }        

    
}
