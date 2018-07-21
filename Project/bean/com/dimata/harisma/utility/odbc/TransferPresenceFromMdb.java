/*
 * TransferPresenceFromMdb.java
 *
 * Created on December 18, 2004, 7:57 AM
 */

package com.dimata.harisma.utility.odbc;

import java.util.*;
import java.sql.*;

import com.dimata.qdep.db.*;
import com.dimata.harisma.entity.attendance.*; 
import com.dimata.harisma.entity.masterdata.*; 
import com.dimata.harisma.entity.employee.*; 

/**
 *
 * @author  gedhy
 */
public class TransferPresenceFromMdb extends DBConnection
{
    
    public static final String tableName = "Transaction";
    
    public static final char ACTION_IN = 'A';
    public static final char ACTION_OUT_HOME = 'B';                
    
    public String driver = "org.gjt.mm.mysql.Driver";
    public String url = "jdbc:mysql://localhost:3306/harisma";
    
    
    public static final String actionNames[] = 
    {
        "In",
        "Out Home"        
    };
    

    public Vector retrieveData()
    {
        Connection dbConn = null;
        Statement stmt = null;
        Vector result = new Vector(1,1);            
        try{
            String sql = "SELECT * FROM " + tableName + 
                         " ORDER BY CARDID";                               

            System.out.println("retrieveData sql : "+sql);
            dbConn = doConnect(driver, url);                
            stmt = dbConn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next())
            {
                Vector vectTemp = new Vector(1,1);
                vectTemp.add(rs.getString(1));                                
                vectTemp.add(rs.getString(2));                
                vectTemp.add(rs.getString(3));
                vectTemp.add(rs.getString(4));
                vectTemp.add(rs.getString(5));                  

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
       
    public int deleteDataPresence(String cardId, String dateTrn, String posted, String station, String mode)
    {
        Connection dbConn = null;
        Statement stmt = null;                        
        int result = 0;
        try{                
            String sql = "DELETE FROM " + tableName +
                         " WHERE CARDID = '" + cardId + "'" +
                         " AND DATETRN = #" + dateTrn + "#" + 
                         " AND POSTED = '" + posted + "'" +
                         " AND STATION = '" + station + "'" +                         
                         " AND MODE = '" + mode + "'";                

            System.out.println("sql delete : "+sql);
            dbConn = doConnect(driver, url);                
            stmt = dbConn.createStatement();
            result = stmt.executeUpdate(sql);                                

        }catch(Exception e){
            System.out.println("err delete : "+e.toString());
        }finally{
            doDisconnect(); 
            return result;
        }
    }
    
   
    public void transferDataPresence()
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
                
                /*
                java.lang.Character charKu = new java.lang.Character();
                char chrPresenceStatusTemp = ((java.lang.Character) tr.get(3)).get;
                int intPresenceStatus = 0;
                switch(chrPresenceStatusTemp)
                {
                    case ACTION_IN :
                        intPresenceStatus = Presence.STATUS_IN;
                        break;                        
                    
                    case ACTION_OUT_HOME : 
                        intPresenceStatus = Presence.STATUS_OUT;
                        break;                        
                        
                    default :      
                        break;
                }
                */
                
                /*
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
                long presenceId = 0;
                try
                {
                    if(employeeId != 0)
                    {
                        presenceId = PstPresence.insertExc(presence);

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
                        }long employeeId = PstEmployee.getEmployeeByBarcode(String.valueOf(tr.get(2)));

                    }
                    catch(Exception e)
                    {
                        System.out.println("Update Presence exc : "+e.toString());
                    }
                }     
                */
                
            } 
        }         
    }            
    

    /**
     * Testing method
     */    
    public static void main(String args[])  
    {
        TransferPresenceFromMdb objTransferPresenceFromMdb = new TransferPresenceFromMdb();        
        
        
        Vector vectPresenceData = objTransferPresenceFromMdb.retrieveData();
        if(vectPresenceData!=null && vectPresenceData.size()>0)
        {
            int maxPresence = vectPresenceData.size();
            for(int i=0; i<maxPresence; i++)
            {
                Vector vectTemp = (Vector) vectPresenceData.get(i);                
                 
                String strDate = String.valueOf(vectTemp.get(1));
                int intYear = Integer.parseInt(strDate.substring(0,4));
                int intMonth = Integer.parseInt(strDate.substring(5,7)); 
                int intDate = Integer.parseInt(strDate.substring(8,10));
                int intHour = Integer.parseInt(strDate.substring(11,13));
                int intMinutes = Integer.parseInt(strDate.substring(14,16));
                int intSecond = Integer.parseInt(strDate.substring(17,19));
                java.util.Date dtPresence = new java.util.Date(intYear-1900, intMonth-1, intDate, intHour, intMinutes, intSecond);

                long employeeId = PstEmployee.getEmployeeByBarcode(String.valueOf(vectTemp.get(0)));
                System.out.println("employeeId  : " + employeeId);
                
                /*
                System.out.println("vectTemp(1) : " + vectTemp.get(0));
                System.out.println("vectTemp(2) : " + vectTemp.get(1));
                System.out.println("dtPresence  : " + dtPresence);                
                System.out.println("vectTemp(3) : " + vectTemp.get(2));
                System.out.println("vectTemp(4) : " + vectTemp.get(3));
                System.out.println("vectTemp(5) : " + vectTemp.get(4));                
                System.out.println("intYear     : " + intYear);
                System.out.println("intMonth    : " + intMonth);
                System.out.println("intDate     : " + intDate);
                System.out.println("intHour     : " + intHour);
                System.out.println("intMinutes  : " + intMinutes);
                System.out.println("intSecond   : " + intSecond);
                */                
                
                System.out.println("");
            }
        }
        
        /*
        int result = transfer.deleteDataPresence("05-25-2004", "2335", "222222", "0");  
        System.out.println("delete finish ... : " + result);
        */
        
        /*  
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
        */               
    }            
}
