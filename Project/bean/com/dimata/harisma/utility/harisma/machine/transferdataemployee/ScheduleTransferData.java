/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.utility.harisma.machine.transferdataemployee;

import com.dimata.harisma.entity.attendance.PstEmpSchedule;
import com.dimata.harisma.entity.employee.PstEmployee;
import com.dimata.harisma.entity.masterdata.PstPeriod;
import com.dimata.harisma.entity.masterdata.PstScheduleSymbol;
import com.dimata.qdep.db.DBHandler;
import com.dimata.qdep.db.DBResultSet;
import java.sql.ResultSet;
import java.util.Date;
import java.util.Hashtable;

/**
 *
 * @author Dimata 007
 */
public class ScheduleTransferData implements Runnable{
    private String message;
    private int recordSize = 0;
    private int progressSize = 0;
    private boolean running = false;
    private long sleepMs = 50;
    private Date dtStart;
    private Date dtFinish;
    private String codeLocationMesin;
    private String inputScheduleParam;
    public void run() {
        try{
        this.setRunning(true);
        this.setMessage("Process transfer data Employee  Schedule");
       
        
        DBResultSet dbrs = null;
        ResultSet rs = null;

       
        Hashtable hashSchedule = new Hashtable();
        
        while (this.running) {

            try {

                Thread.sleep(this.getSleepMs() * 30);

                int limit = 50;
                String sql;
                sql = "SELECT *  FROM " + PstScheduleSymbol.TBL_HR_SCHEDULE_SYMBOL + " AS sch ";
                
                if(inputScheduleParam!=null && inputScheduleParam.length()>0){
                    sql = sql + " AND " + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SYMBOL] + " IN(" + inputScheduleParam+")";
                }
                sql = sql + " ORDER BY emp." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SYMBOL];
               
                message = "Run query download data employee " + sql;
                
                int theSize = getCountScheduleDownload(inputScheduleParam);
                if (theSize > 0) {
                    recordSize = theSize;
                    progressSize = 0;
                } else {
                    recordSize = 0;
                    progressSize = 0;
                }
                message = " Total  download data employee " + theSize;
                dbrs = DBHandler.execQueryResult(sql);
                rs = dbrs.getResultSet();


                while (rs!=null && rs.next() && theSize > 0 && isRunning()) {

                    Thread.sleep(this.getSleepMs());
                    TabelEmployeeScheduleTransfer tabelEmployeeScheduleTransfer = new TabelEmployeeScheduleTransfer();
                    tabelEmployeeScheduleTransfer.setScheduleId(rs.getLong(PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]));
                    tabelEmployeeScheduleTransfer.setScheduleCategory(rs.getLong(PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_CATEGORY_ID]));
                    tabelEmployeeScheduleTransfer.setScheduleSymbol(rs.getString(PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SYMBOL]));
                    tabelEmployeeScheduleTransfer.setNameSchedule(rs.getString(PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE]));
                    tabelEmployeeScheduleTransfer.setTimeIn(rs.getDate(PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_TIME_IN]));
                    tabelEmployeeScheduleTransfer.setTimeOut(rs.getDate(PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_TIME_OUT]));
                    tabelEmployeeScheduleTransfer.setBreakIn(rs.getDate(PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_BREAK_IN]));
                    tabelEmployeeScheduleTransfer.setBreakOut(rs.getDate(PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_BREAK_OUT]));
                    
                   
                    
                    

                    
                    //insert data Period
                    if (hashSchedule.size() == 0 || hashSchedule.containsKey(""+tabelEmployeeScheduleTransfer.getScheduleId()) == false) {
                        
                        PstScheduleDestopTransfer.insertExc(tabelEmployeeScheduleTransfer); 
                        hashSchedule.put(""+tabelEmployeeScheduleTransfer.getScheduleId(), tabelEmployeeScheduleTransfer.getScheduleSymbol());
                        
                        message = " Insert Data Schedule " + tabelEmployeeScheduleTransfer.getScheduleSymbol() + " In local";
                    } else {
                        
                        PstScheduleDestopTransfer.updateExc(tabelEmployeeScheduleTransfer); 
                        message = " Update Data Schedule " + tabelEmployeeScheduleTransfer.getScheduleSymbol() + " In local";
                       
                    }
                    

                    progressSize++;
                    if (this.progressSize == theSize) {
                        this.progressSize = 0;
                    }
                    Thread.sleep(this.getSleepMs());

                }




                // simpan ke hr_machine transaction
                // set record dari table mesin , sudah diambil dan disimpan
            } catch (Exception exc) {
                this.setMessage("Exception transfer Employee  Schedule"+exc);
            }//update by devin 2014-01-15
            finally {
                try {
                    if(rs!=null){
                         rs.close();
                    }
                   
                    //stmt.close();
                } catch (Exception exc) {
                    this.setMessage("Exception transfer Employee  Schedule"+exc);
                }
            }
        }

        this.running = false;

        }catch(Exception exc){
            this.setMessage("Exception transfer Employee  Schedule"+exc);
        }
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return the recordSize
     */
    public int getRecordSize() {
        return recordSize;
    }

    /**
     * @param recordSize the recordSize to set
     */
    public void setRecordSize(int recordSize) {
        this.recordSize = recordSize;
    }

    /**
     * @return the progressSize
     */
    public int getProgressSize() {
        return progressSize;
    }

    /**
     * @param progressSize the progressSize to set
     */
    public void setProgressSize(int progressSize) {
        this.progressSize = progressSize;
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
     * @return the sleepMs
     */
    public long getSleepMs() {
        return sleepMs;
    }

    /**
     * @param sleepMs the sleepMs to set
     */
    public void setSleepMs(long sleepMs) {
        this.sleepMs = sleepMs;
    }

    /**
     * @return the dtStart
     */
    public Date getDtStart() {
        return dtStart;
    }

    /**
     * @param dtStart the dtStart to set
     */
    public void setDtStart(Date dtStart) {
        this.dtStart = dtStart;
    }

    /**
     * @return the dtFinish
     */
    public Date getDtFinish() {
        return dtFinish;
    }

    /**
     * @param dtFinish the dtFinish to set
     */
    public void setDtFinish(Date dtFinish) {
        this.dtFinish = dtFinish;
    }

    /**
     * @return the codeLocationMesin
     */
    public String getCodeLocationMesin() {
        return codeLocationMesin;
    }

    /**
     * @param codeLocationMesin the codeLocationMesin to set
     */
    public void setCodeLocationMesin(String codeLocationMesin) {
        this.codeLocationMesin = codeLocationMesin;
    }
    
    /**
     * mencari jumlah berapa yg ada employee schedulenya
     * @param whereClause
     * @param Start
     * @param End
     * @return 
     */
    public static int getCountScheduleDownload(String whereClause) {
       
        DBResultSet dbrs = null;
        try {
            
            String sql = "SELECT COUNT("+PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID] + ")"
                        +" FROM " + PstScheduleSymbol.TBL_HR_SCHEDULE_SYMBOL + " AS sch ";
                
                if(whereClause!=null && whereClause.length()>0){
                    sql = sql + " WHERE " + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SYMBOL] + " IN(" + whereClause+")";
                }
                sql = sql + " ORDER BY " + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SYMBOL];

           
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            int count = 0;
            while (rs.next()) {
                count = rs.getInt(1);
            }

            rs.close();
            return count;
        } catch (Exception e) {
            return 0;
        } finally {
            DBResultSet.close(dbrs);
        }
    }

    /**
     * @return the inputScheduleParam
     */
    public String getInputScheduleParam() {
        return inputScheduleParam;
    }

    /**
     * @param inputScheduleParam the inputScheduleParam to set
     */
    public void setInputScheduleParam(String inputScheduleParam) {
        this.inputScheduleParam = inputScheduleParam;
    }
    
}
