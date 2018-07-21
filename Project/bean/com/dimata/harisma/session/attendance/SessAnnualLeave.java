/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.session.attendance;

import com.dimata.harisma.entity.attendance.AlStockManagement;
import com.dimata.harisma.entity.attendance.AlStockTaken;
import com.dimata.harisma.entity.attendance.PstAlStockManagement;
import com.dimata.harisma.entity.attendance.PstAlStockTaken;
import com.dimata.qdep.db.DBHandler;
import com.dimata.qdep.db.DBResultSet;
import java.sql.ResultSet;
import java.util.Date;
import java.util.Vector;

/**
 *
 * @author Satrya Ramayu
 */
public class SessAnnualLeave {
    /**
     * Proses pembayaran AL yang masih utang
     */
    public static boolean proccessPaid(long employeeId){
        boolean isSuccess = false;
        Vector vAlTakenUnpaid = new Vector(1,1);
        //pencarian Al stok yg minus
        vAlTakenUnpaid = SessAnnualLeave.getAlUnpaid(employeeId);
        for(int i=0;i<vAlTakenUnpaid.size();i++){
            //pencarian Al stock yg masih aktif
            AlStockManagement alStockManagement = getAlPaidStockManagement(employeeId);
            if(alStockManagement.getOID()>0){
                AlStockTaken alStockTaken = (AlStockTaken)vAlTakenUnpaid.get(i);
                //pencarian AL untuk di update
                paidAlTaken(alStockManagement, alStockTaken, new Date());    
            }else{
                //Tidak ada data
                break;
            }
        }
        return isSuccess;
    }
 /**
  * pencarian al yg perlu di bayarkan
  * @param employeeId
  * @return 
  */   
public static Vector getAlUnpaid(long employeeId){  
        Vector result = new Vector(1,1);       
        if(employeeId==0){
            return result;
        }
        DBResultSet dbrs = null;        
        String stSQL = 
               " SELECT AL_TAKEN.* FROM "+PstAlStockTaken.TBL_HR_AL_STOCK_TAKEN + " AS AL_TAKEN "
+ " INNER JOIN "+PstAlStockManagement.TBL_AL_STOCK_MANAGEMENT + " AS AL_MAN ON AL_TAKEN."+PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_AL_STOCK_ID]
+ " =AL_MAN."+PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_AL_STOCK_ID]
+ " WHERE (AL_MAN."+PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_QTY_RESIDUE]
        +"<0 OR AL_TAKEN."+PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_QTY] + ">= AL_MAN."+PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_AL_QTY]+")" 
+ " AND AL_MAN."+PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_AL_QTY]+"!=0"
+ " AND AL_TAKEN."+PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_EMPLOYEE_ID]+"="+employeeId
+ " ORDER BY " + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_DATE];
        try
        {
//            System.out.println("SQL getLlPayable : " + stSQL);
            dbrs = DBHandler.execQueryResult(stSQL);
            ResultSet rs = dbrs.getResultSet();            
            while(rs.next())
            {
                AlStockTaken alStockTaken = new AlStockTaken();
                alStockTaken.setOID(rs.getLong(PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_AL_STOCK_TAKEN_ID]));
                alStockTaken.setEmployeeId(rs.getLong(PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_EMPLOYEE_ID]));
                alStockTaken.setAlStockId(rs.getInt(PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_AL_STOCK_ID]));
                alStockTaken.setTakenDate(rs.getDate(PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_DATE]));
                alStockTaken.setTakenQty(rs.getFloat(PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_QTY]));                
                alStockTaken.setPaidDate(rs.getDate(PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_PAID_DATE]));
                
                result.add(alStockTaken);
            }
            rs.close();
        }
        catch(Exception e)
        {
            System.out.println("Exc when getLlPayable : " + e.toString());
        }
        finally
        {
            DBResultSet.close(dbrs);
            return result;
        }        
    }
/**
 * create by satrya 2013-08-26
 * untuk mencari stok yg masih sisa
 * @param employeeId
 * @return 
 */  
public static AlStockManagement getAlPaidStockManagement(long employeeId){  
        //Vector result = new Vector(1,1);  
    AlStockManagement alStockManagement = new AlStockManagement();
        if(employeeId==0){
            return alStockManagement;
        }
        DBResultSet dbrs = null;        
        String stSQL = 
               " SELECT AL_MAN.* FROM "+PstAlStockTaken.TBL_HR_AL_STOCK_TAKEN + " AS AL_TAKEN "
+ " INNER JOIN "+PstAlStockManagement.TBL_AL_STOCK_MANAGEMENT + " AS AL_MAN ON AL_TAKEN."+PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_AL_STOCK_ID]
+ " =AL_MAN."+PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_AL_STOCK_ID]
+ " WHERE (AL_MAN."+PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_QTY_RESIDUE]+">0 AND  AL_MAN."+PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_AL_QTY]
+ " >= AL_TAKEN."+PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_QTY]+") AND AL_MAN."+PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_AL_QTY]+"!=0"
+ " AND AL_MAN."+PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_AL_STATUS]+"="+PstAlStockManagement.AL_STS_AKTIF;
        try
        {
//            System.out.println("SQL getLlPayable : " + stSQL);
            dbrs = DBHandler.execQueryResult(stSQL);
            ResultSet rs = dbrs.getResultSet();            
            while(rs.next())
            {
                alStockManagement = new AlStockManagement();
                PstAlStockManagement.resultToObject(rs, alStockManagement);
                
            }
            rs.close();
        }
        catch(Exception e)
        {
            System.out.println("Exc when getLlPayable : " + e.toString());
        }
        finally
        {
            DBResultSet.close(dbrs);
            return alStockManagement;
        }        
    }
   
    
    private static void paidAlTaken(AlStockManagement alStockManagement, AlStockTaken alStockTaken, Date paidDate){
        if(alStockManagement.getQtyResidue()>=alStockTaken.getTakenQty()){
            alStockManagement.setQtyResidue(alStockManagement.getQtyResidue()-alStockTaken.getTakenQty());
            alStockManagement.setQtyUsed(alStockManagement.getQtyUsed()+alStockTaken.getTakenQty());
            
            if(alStockManagement.getQtyResidue()<=0){
                alStockManagement.setAlStatus(PstAlStockManagement.AL_STS_TAKEN);
            }
            
            
            alStockTaken.setAlStockId(alStockManagement.getOID());
            alStockTaken.setPaidDate(paidDate);
            
            try{
                PstAlStockManagement.updateExc(alStockManagement);
                PstAlStockTaken.updateExc(alStockTaken);
            }catch(Exception ex){
                System.out.println("Exc upd alstok"+ex);
            }
            
        }else{
            
        }
    }
}
