/*
 * ServiceDpStock.java
 *
 * Created on December 8, 2004, 4:51 PM
 */

package com.dimata.harisma.utility.service.leavedp;

import java.util.Vector; 
import java.util.Date;

import com.dimata.util.Formater;
import com.dimata.qdep.db.*;

import com.dimata.harisma.entity.attendance.*;
import com.dimata.harisma.entity.employee.Employee;  
import com.dimata.harisma.entity.employee.PstEmployee;
import com.dimata.harisma.entity.leave.I_Leave;
import com.dimata.harisma.entity.leave.LeaveConfigurationParameter;
import com.dimata.harisma.entity.masterdata.PstPublicHolidays;
import com.dimata.harisma.entity.masterdata.PstScheduleCategory;
import com.dimata.harisma.entity.masterdata.PstPeriod;
import com.dimata.system.entity.PstSystemProperty;
import java.util.Hashtable;

/**
 *
 * @author  gedhy
 */
public class ServiceDpStock 
{   
    
    private static boolean running = false;
      //update by satrya 2013-2-25
    private static ServiceDpStock serviceDpStock = null;
    private static AutomaticStockDpUpdater automaticStockDpUpdater =null;
    //update by satrya 2013-03-19
   private Date dtSelect=null;
   private String empLogin = null;
     public Date getDate(){
        return dtSelect;
    }
    
    public void setDate(Date dt ){
        dtSelect = dt;
    }
    //update by satrya 2013-07-01
     public String getEmpLogin(){
        return empLogin;
    }
    
    public void setEmpLogin(String empLoginx ){
        empLogin = empLoginx;
    }
    
         public static AutomaticStockDpUpdater getStockDP(){
        if(automaticStockDpUpdater !=null){
            return automaticStockDpUpdater;
        }
        else{
            return automaticStockDpUpdater = new AutomaticStockDpUpdater();
        }
        
    }
  public static ServiceDpStock getInstance(boolean withAssistant) {
        if (serviceDpStock == null) {
            serviceDpStock = new ServiceDpStock();            
            if(withAssistant){
                serviceDpStock = new ServiceDpStock();
                serviceDpStock.setRunning(false);                
            } else{
                serviceDpStock=null;
               
            }
        }
        return serviceDpStock;
    }

    /**
     * @return the running
     */
    public static boolean isRunning() {
        return running;
    }

    /**
     * @param aRunning the running to set
     */
    public static void setRunning(boolean aRunning) {
        running = aRunning;
    }

    /*
     * start thread/service process
     */
    public void startService()          
    {
        /*if (!running) 
        {
            System.out.println(".................... ServiceDpStock started ....................");
            try 
            {                
                running = true;
              //  Thread thr = new Thread(new AutomaticStockDpUpdater());
              //  thr.setDaemon(false);
               // thr.start();

            }
            catch (Exception e) 
            {
                System.out.println(">>> Exc on starting ServiceDpStock : " + e.toString());
            }
        }*/
        // hidden by satrya ramayu 2013-03-19
                  if(automaticStockDpUpdater!=null){
                  automaticStockDpUpdater.setStartDate(getDate());
                  automaticStockDpUpdater.setEmpLogin(getEmpLogin());
                  automaticStockDpUpdater.setRunning(true);
                  Runnable task = (Runnable) automaticStockDpUpdater;                                      
                  Thread worker = new Thread(task);
                  worker.setDaemon(false);
                  worker.start();                        
              }else {
                  automaticStockDpUpdater= new AutomaticStockDpUpdater();
                  automaticStockDpUpdater.setStartDate(getDate());
                  automaticStockDpUpdater.setEmpLogin(getEmpLogin());
                  automaticStockDpUpdater.setRunning(true);
                  Runnable task = (Runnable) automaticStockDpUpdater;                                      
                  Thread worker = new Thread(task);
                  worker.setDaemon(false);
                  worker.start();                        
          }
    }

    
    /**
     * get thread/service status
     * @return
     */    
    public boolean getStatus() 
    {
       // return isRunning();
        //update by satrya 2013-02-25
                  if (automaticStockDpUpdater!=null) {                    
           return  automaticStockDpUpdater.isRunning(); 
            
        }
        else{
             return false;
        }
    }

    /**
     * stop thread/service process
     */        
    public void stopService() 
    {
        //setRunning(false);
        if(automaticStockDpUpdater!=null){
            automaticStockDpUpdater.setRunning(false);
           
        }
        System.out.println(".................... ServiceDPStock stoped ....................");       
    }

    
    /**
     * Proses update status DP (active dan expired), di jalankan oleh thread     
     *
     * Aturan sbb : 
     *  - proses activated dijalankan terlebih dahulu
     *  - Diikuti oleh proses expired 
     *
     * Catanan : 
     *  - Jika hari ini adalah tanggal 1, maka laporan stock akhir DP bulan/period lalu harus 
     *    ditransfer dijadikan stock awal bulan ini
     *  - Proses Dp Stock Updater ini harus dijalankan setelah proses 'pengambilan DP'
     *    yang 'numpang' di service absence dijalankan, hal ini dikarenakan kita pake pola
     *    "AFTER DATE" sehingga memungkinkan pengambilan DP pada saat tanggal jatuh tempo
     *    DP tersebut
     *
     * @edited by Edhy  
     */    
    public void processDpStock()   
    {  
        try 
        {
            //Matiakn untuk Hard Rock
            //processDpStock(new Date());
        } 
        catch (Exception e) 
        {
            System.out.println("Exc on processStockDp : " + e.toString());
        }
    }
    
    /**
     * Proses update status DP (active dan expired), di jalankan oleh thread     
     *
     * Aturan sbb : 
     *  - proses activated dijalankan terlebih dahulu
     *  - Diikuti oleh proses expired 
     *
     * Catanan : 
     *  - Jika hari ini adalah tanggal 1, maka laporan stock akhir DP bulan/period lalu harus 
     *    ditransfer dijadikan stock awal bulan ini
     *  - Proses Dp Stock Updater ini harus dijalankan setelah proses 'pengambilan DP'
     *    yang 'numpang' di service absence dijalankan, hal ini dikarenakan kita pake pola
     *    "AFTER DATE" sehingga memungkinkan pengambilan DP pada saat tanggal jatuh tempo
     *    DP tersebut
     *
     * @edited by Edhy
     */    
    public void processDpStock(Date dtNow)   
    {  
        try 
        {
            // process DP's activated 
            processDpStockActive(dtNow);
            
            // process DP's expired
            processDpStockExpired(dtNow);              
        } 
        catch (Exception e) 
        {
            System.out.println("Exc on processStockDp : " + e.toString());
        }
    }
 
/**
 * create by satrya 2013-07-01
 * Ketrerangan: melakukan prosess penambahan DP automatis
 * @param dt 
 */ 
public static void processAutomaticStockDPStockThread(Date dt,String empNameLogin, Vector vct,I_Leave leaveConfig,Hashtable hashCekDpStockTaken,LeaveConfigurationParameter leaveConfigurationParameter) 
    {        
        try 
        {    
            
            if(vct!=null && vct.size()>0)
            {            
               for (int i=0; i<vct.size(); i++) 
                {
                    Employee emp = (Employee) vct.get(i);
                    //Hashtable hashCekAccDate = new Hashtable();
                    //hanya test saja
                    if(emp!=null && emp.getFullName().equalsIgnoreCase("DRS.I MADE SUARDIKA")){
                      int x=0;
                    }
                    try 
                    {
                      if(dt!=null){
                       String dtempKey = emp.getOID()+"_"+Formater.formatDate(dt, "yyyy-MM-dd");
                       //hannya untuk test saja
                       /*if(emp.getFullName().equalsIgnoreCase("FRIEDA FRANCISCA ZUTHER")){
                           boolean test = true;
                       }*/
                        int tmpEntitleDp = leaveConfig.getDPEntitleByDate(emp.getEmployeeNum(), dt,leaveConfigurationParameter);
                      //if(tmpEntitleDp!=0){
                        long oidPeriod = PstPeriod.getPeriodIdBySelectedDate(dt);
                                    DpStockManagement dpStock = new DpStockManagement(); 
                                    dpStock.setDtOwningDate(dt);
                                    dpStock.setDtExpiredDate(new Date(dt.getTime()+ ((30L*24L*60L*60L*1000L)*(long)leaveConfig.getDpValidity(leaveConfig.getStrLevels()[0]) )));
                                    dpStock.setDtStartDate(dt);
                                    dpStock.setEmployeeId(emp.getOID());
                                    // kenapa di pakai empTime.getOID() supaya  spesific masing" overtime detail dp_stoc yg di generate,di karenakan jika memakai empTime.GetPeriodId , jika karyawan tersebut OT di period yg sama maka nantinya bisa terhapus
                                    dpStock.setLeavePeriodeId(oidPeriod);                                                                       
                                    dpStock.setQtyResidue((float)(tmpEntitleDp )); //empTime.getNetDuration()/8f));
                                    dpStock.setiDpQty((float)(tmpEntitleDp));
                                    dpStock.setQtyUsed(0f); 
                                    dpStock.setStNote("Dp generated from Presence on Holiday "+ empNameLogin);
                                    dpStock.setToBeTaken(0f);
                                    //update by satrya 2013-02-24
                                    dpStock.setFlagStock(PstDpStockManagement.DP_FLAG_EDIT_NO); //artinya ini generate by OT
                            if(hashCekDpStockTaken!=null && hashCekDpStockTaken.size()>0 && hashCekDpStockTaken.get(dtempKey)!=null){
                                DpStockManagement dpStockCek = (DpStockManagement) hashCekDpStockTaken.get(dtempKey);
                                
                             if(dpStockCek!=null){
                               //kemungkinan ada perubahan schedule yg kemarinnya dapat  dikarenakan schedulenya off, tpi karena di rubah jdi gji dapat
                                if(tmpEntitleDp==0 && dpStockCek.getiDpQty()!=0 && dpStockCek.getFlagStock()==PstDpStockManagement.DP_FLAG_EDIT_NO){
                                    //update by satrya 2013-12-20 kasusnya di di delete jika ada penambahan otomatis lewat service center
                                    //if(tmpEntitleDp==0 && dpStockCek.getiDpQty()!=0){ 
                                    //maka delete
                                    try{
                                   PstDpStockManagement.deleteExc(dpStockCek.getOID()); 
                                   PstDpStockTaken.updateStockIdAndPaidDateBecomeNull(dpStockCek);
                                   }catch(Exception exc){
                                       System.out.println("ExcServiceDpStock"+exc);
                                   }
                                  // jika belum dapat sama sekali 
                                }else if(tmpEntitleDp!=0 && tmpEntitleDp!=dpStockCek.getiDpQty() && dpStockCek.getiDpQty()==0){
                                    // insert
                                    try{
                                    PstDpStockManagement.insertExc(dpStock);
                                   }catch(Exception exc){
                                       System.out.println("ExcServiceDpStock"+exc);
                                   }
                                  //jika sudah dapat tpi ada perubahan
                                }else if(tmpEntitleDp!=0 && tmpEntitleDp!=dpStockCek.getiDpQty() && dpStockCek.getiDpQty()!=0){
                                    // update
                                   try{
                                    PstDpStockManagement.updateExc(dpStock);
                                   }catch(Exception exc){
                                       System.out.println("ExcServiceDpStock"+exc);
                                   }
                                }
                             }
                            }else{
                                //artinya belum ada yg di tambahkan
                              if(tmpEntitleDp!=0){
                                try{
                                    PstDpStockManagement.insertExc(dpStock);
                                   }catch(Exception exc){
                                       System.out.println("ExcServiceDpStock"+exc);
                                   }
                              }
                            }
                      //}
                                    
                                       
                           
                       
                        //Membayar DP yang belum di paid
                        //by atrha
                       PstDpStockManagement.checkPaidDPPayable(emp.getOID()); 
                       //System.out.println("Test Date Service DP Stock:"+Formater.formatDate(dt, "dd-MM-YYYY"));
                      } 
                    } 
                    catch (Exception e) 
                    {
                        System.out.println("Exception processLLStock auto emp : " + emp.getOID() + " : " + e.toString());
                    }
                }
            }
        } 
        catch (Exception e) 
        {
            System.out.println("Exception processLLStock : " + e.toString());            
        }
    }
    
    

    // --------------------------- START PROCESS DP ACTIVATED ------------------------    
    /**
     * Proses update status, di jalankan oleh thread
     * Jika sesuai dengan "aturan", maka set status Dp tersebut menjadi aktif
     *
     * Aturan sbb : 
     *  - pada schedule adalah MASUK pada saat PH, dan realnya MASUK pada saat PH (2 DP)
     *  - pada schedule adalah OFF pada saat PH, dan realnya OFF pada saat PH (1 DP)
     *  - pada schedule adalah EOD, dan realnya MASUK pada hari itu(1 DP)
     *
     * Proses pengecekan ini dilakukan sehari sesudah tanggal PH bersangkutan, 
     * karena untuk menentukan/mengetahui dengan pasti status presence (real) 
     * pada saat PH adalah minimal keesokan harinya 
     *
     * Adanya proses pembayaran 'hutang DP' kalau ada, dengan Stock DP yang baru aktif ini
     *
     * Jika hari ini adalah tanggal 1, maka laporan stock akhir DP bulan/period lalu harus 
     * ditransfer dijadikan stock awal bulan ini
     *
     * @created by Edhy
     */    
    public void processDpStockActive() 
    {
        try 
        {   
            //Untuk hardrock process dp dipisah kan
          //  processDpStockActive(new Date());            
        } 
        catch (Exception e) 
        {
            System.out.println("Exc on processStockDp : " + e.toString());
        }
    }

    /**
     * Proses update status, di jalankan oleh thread
     * Jika sesuai dengan "aturan", maka set status Dp tersebut menjadi aktif
     *
     * Aturan sbb : 
     *  - pada schedule adalah MASUK pada saat PH, dan realnya MASUK pada saat PH (2 DP)
     *  - pada schedule adalah OFF pada saat PH, dan realnya OFF pada saat PH (1 DP)
     *  - pada schedule adalah EOD, dan realnya MASUK pada hari itu(1 DP)
     *
     * Proses pengecekan ini dilakukan sehari sesudah tanggal PH bersangkutan, 
     * karena untuk menentukan/mengetahui dengan pasti status presence (real) 
     * pada saat PH adalah minimal keesokan harinya 
     *
     * Adanya proses pembayaran 'hutang DP' kalau ada, dengan Stock DP yang baru aktif ini
     *
     * Jika hari ini adalah tanggal 1, maka laporan stock akhir DP bulan/period lalu harus 
     * ditransfer dijadikan stock awal bulan ini
     *
     * @created by Gadnyana
     * @edited by Edhy
     */    
    public void processDpStockActive(Date dtNow)   
    {  
        try 
        {
            // Create object Date "yesterday" and get periodeId base on it
            Date dtYesterday = new Date(dtNow.getYear(), dtNow.getMonth(), dtNow.getDate()-1);             
            long yesterdayPeriodId = PstPeriod.getPeriodIdBySelectedDate(dtYesterday);
                        
            // update/aktivasi Dp perolehan dari PH
            DPPHActivated(dtYesterday, yesterdayPeriodId);
            
            // update/aktivasi DP perolehan dari EOD
            DPEODActivated(dtYesterday, yesterdayPeriodId);            
        } 
        catch (Exception e) 
        {
            System.out.println("Exc on processStockDp : " + e.toString());
        }
    }
    
    
    /**
     * Proses aktivasi Dp karena perolehan dari PH
     * @param dtYesterday
     * @param periodId
     * @created by Edhy
     */    
    public void DPPHActivated(Date dtYesterday, long periodId)
    {
        if (cekPublicHolidays(dtYesterday)) 
        {
            String whereClause = PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + " = " + PstEmployee.NO_RESIGN;
            Vector vectEmp = PstEmployee.list(0, 0, whereClause, ""); 
            if (vectEmp != null && vectEmp.size() > 0) 
            {
                for (int k=0; k<vectEmp.size(); k++) 
                {
                    Employee employee = (Employee) vectEmp.get(k);

                    // fetch DP stock per employee
                    Vector vtDpStock = PstDpStockManagement.listDpStock(employee.getOID());                        
                    if (vtDpStock != null && vtDpStock.size() > 0) 
                    {
                        for (int i=0; i<vtDpStock.size(); i++)  
                        {
                            DpStockManagement dpStockMng = (DpStockManagement) vtDpStock.get(i);
                            String strYesterdayDt = Formater.formatDate(dtYesterday, "yyyy-MM-dd");
                            String strDpDt = Formater.formatDate(dpStockMng.getDtOwningDate(), "yyyy-MM-dd");
                            if (strYesterdayDt.equals(strDpDt)) 
                            {

                                // proses DP kategori PRESENCE pada PH
                                // check employee actual presence on public holiday
                                if (cekPresenceEmployee(employee.getOID(), periodId, dtYesterday)) 
                                {
                                    if (dpStockMng.getiDpStatus() == PstDpStockManagement.DP_STS_NOT_AKTIF) 
                                    {
                                        try 
                                        {
                                            dpStockMng.setiDpStatus(PstDpStockManagement.DP_STS_AKTIF);
                                            PstDpStockManagement.updateExc(dpStockMng);

                                            // pembayaran hutang DP jika ada                                                                                                                                     
                                            Vector vectOidLeavePaid = PstDpStockManagement.paidDpPayable(employee.getOID(), dpStockMng);																												                                            
                                        }
                                        catch (Exception e)     
                                        {
                                            System.out.println("Exc when update dpStockManagement on processStockDp : " + e.toString());
                                        }
                                    }
                                }                                    

                                // proses DP kategori OFF pada PH
                                else
                                {
                                    int scheduleCategory = PstEmpSchedule.getScheduleCategory(periodId, employee.getOID(), dtYesterday.getDate());
                                    if(scheduleCategory==PstScheduleCategory.CATEGORY_OFF)
                                    {
                                        if (dpStockMng.getiDpStatus() == PstDpStockManagement.DP_STS_NOT_AKTIF) 
                                        {
                                            try 
                                            {
                                                dpStockMng.setiDpStatus(PstDpStockManagement.DP_STS_AKTIF);
                                                PstDpStockManagement.updateExc(dpStockMng);                                                    

                                                // pembayaran hutang DP jika ada                                                                                                                                     
                                                Vector vectOidLeavePaid = PstDpStockManagement.paidDpPayable(employee.getOID(), dpStockMng);																												                                                
                                            }
                                            catch (Exception e) 
                                            {
                                                System.out.println("Exc when update dpStockManagement on processStockDp : " + e.toString());
                                            }
                                        }                                            
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }              
    }
    
    /**
     * Proses aktivasi Dp karena perolehan dari PH
     * @param dtYesterday
     * @param periodId
     * @created by Edhy
     */    
    public void DPEODActivated(Date dtYesterday, long periodId)
    {
        String strDtYesterday = Formater.formatDate(dtYesterday,"yyyy-MM-dd");
        String whereClause = PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_OWNING_DATE] +
                             " = \"" + strDtYesterday + "\"" + 
                             " AND " + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_STATUS] +   
                             " = " + PstDpStockManagement.DP_STS_NOT_AKTIF;
        Vector listDpStockNotActive = PstDpStockManagement.list(0,0,whereClause,"");
        if(listDpStockNotActive!=null && listDpStockNotActive.size()>0)            
        {
            int maxDpStockNotActive = listDpStockNotActive.size();        
            for(int i=0; i<maxDpStockNotActive; i++)
            {
                DpStockManagement objDpStockManagement = (DpStockManagement) listDpStockNotActive.get(i);
                long employeeId = objDpStockManagement.getEmployeeId();
                
                // check aturan utk pengaktifan DP (EOD) ==> harus masuk pada saat EOD
                boolean presenceOK = cekPresenceEmployee(employeeId, periodId, dtYesterday);

                // jika presence OK, maka proses aktivasi dilakukan
                if(presenceOK)
                {                              
                    // pengambilan kategori schedule kemarin
                    int scheduleCategory = PstEmpSchedule.getScheduleCategory(periodId, employeeId, dtYesterday.getDate());
                    if(scheduleCategory==PstScheduleCategory.CATEGORY_EXTRA_ON_DUTY)
                    {
                        try
                        {
                            objDpStockManagement.setiDpStatus(PstDpStockManagement.DP_STS_AKTIF);
                            PstDpStockManagement.updateExc(objDpStockManagement);

                            // pembayaran hutang DP jika ada                                                                                                                                     
                            Vector vectOidLeavePaid = PstDpStockManagement.paidDpPayable(employeeId, objDpStockManagement);																												
                        }
                        catch(Exception e)
                        {
                            System.out.println("Exc when activated Dp EOD : " + e.toString());
                        }
                    }                    
                }                
            }
        }
    }
    // --------------------------- END PROCESS DP ACTIVATED ------------------------    
    
    
    
    
    
    // --------------------------- START PROCESS DP EXPIRED ------------------------       
    /**     
     * Proses update status menjadi expired, di jalankan oleh thread
     * Jika sesuai dengan "aturan", maka set status Dp tersebut menjadi expired
     *
     * Aturan sbb : 
     *  - proses peng-expired-an DP memakai pola "AFTER DATE" sehingga memungkinkan
     *    pengambilan DP pada saat tanggal jatuh temponya, misalnya : 
     *    * Expired date = 08-12-2004
     *    * Taken date   = 08-12-2004
     *
     * Proses pengecekan ini dilakukan sehari sesudah tanggal bersangkutan, 
     * menyesuaikan dengan pola pengaktifan DP
     *
     * @created by Edhy
     */    
    public static void processDpStockExpired()
    {
        processDpStockExpired(new Date());        
    }
    
    /**     
     * Proses update status menjadi expired, di jalankan oleh thread
     * Jika sesuai dengan "aturan", maka set status Dp tersebut menjadi expired
     *
     * Aturan sbb : 
     *  - proses peng-expired-an DP memakai pola "AFTER DATE" sehingga memungkinkan
     *    pengambilan DP pada saat tanggal jatuh temponya, misalnya : 
     *    * Expired date = 08-12-2004
     *    * Taken date   = 08-12-2004
     *
     * Proses pengecekan ini dilakukan sehari sesudah tanggal bersangkutan, 
     * menyesuaikan dengan pola pengaktifan DP
     *
     * @created by Edhy
     */    
    public static void processDpStockExpired(Date dtCurrDate)
    {               
        // Create object Date "yesterday" and get periodeId base on it
        Date dtYesterday = new Date(dtCurrDate.getYear(), dtCurrDate.getMonth(), dtCurrDate.getDate()-1);             
        long yesterdayPeriodId = PstPeriod.getPeriodIdBySelectedDate(dtYesterday);
        
        DpStockManagement objDpStockMgn = new DpStockManagement();
        Date dtExpDate = new Date();
        Vector vExpDp = PstDpStockManagement.listDpByExpDate(dtYesterday);
        if(vExpDp != null && vExpDp.size() > 0)  
        {
            for(int i= 0; i<vExpDp.size(); i++)
            {
                objDpStockMgn = new DpStockManagement();
                objDpStockMgn = (DpStockManagement) vExpDp.get(i);
                if(objDpStockMgn.getiExceptionFlag() == PstDpStockManagement.EXC_STS_NO)
                {
                    dtExpDate = objDpStockMgn.getDtExpiredDate();
                }
                
                else if(objDpStockMgn.getiExceptionFlag() == PstDpStockManagement.EXC_STS_YES)
                {
                    dtExpDate = objDpStockMgn.getDtExpiredDateExc();
                }

                if(Formater.formatDate(dtExpDate, "dd-MM-yyyy").trim().equals(Formater.formatDate(dtYesterday, "dd-MM-yyyy").trim()))
                {
                    ServiceDpStock ServiceDpStock = new ServiceDpStock();
                    ServiceDpStock.updateDpStockManagement(objDpStockMgn);                        
                     
                    // insert Dp expired
                    try
                    {                        
                        PstDpStockExpired objPstDpStockExpired = new PstDpStockExpired();
                        DpStockExpired objDpStockExpired = new DpStockExpired();
                        objDpStockExpired.setDpStockId(objDpStockMgn.getOID());
                        objDpStockExpired.setExpiredDate(dtExpDate);
                        objDpStockExpired.setExpiredQty(objDpStockMgn.getQtyResidue());
                        objPstDpStockExpired.insertExc(objDpStockExpired);                    
                    }
                    catch(Exception e)
                    {
                        System.out.println("Exc when insert to expired Dp : " + e.toString());
                    }                    
                }
            }            
        }            
    }
    // --------------------------- END PROCESS DP EXPIRED ------------------------       
    
    
    
    
    
    
    
    
    /**
     * untuk cek tangggal sekarang apakah hari holidays
     * @param dtNow
     * @return
     * @created by Gadnyana
     */
    private boolean cekPublicHolidays(Date dtNow) 
    {
        try 
        {
            String whereClause = PstPublicHolidays.fieldNames[PstPublicHolidays.FLD_HOLIDAY_DATE] + "='" + Formater.formatDate(dtNow, "yyyy-MM-dd")+"'";
            Vector vtList = PstPublicHolidays.list(0, 0, whereClause, "");
            if (vtList != null && vtList.size() > 0)
            {
                return true;
            }
        }
        catch (Exception e) 
        {
            System.out.println("Exc when cekPublicHolidays : " + e.toString());
        }
        return false;
    }


    /**
     * @param oid
     * @param dtNow
     * @return
     */    
    private boolean cekPresenceEmployee(long oid, long periodId, Date dtNow) 
    {
        try 
        {
            int selectedIndex = dtNow.getDate();
            String whereClause = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID] + " = " + oid + 
                                 " AND " + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_PERIOD_ID] + " = " + periodId + 
                                 " AND (NOT ISNULL("+PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + (selectedIndex - 1)] +")"+
                                 " OR NOT ISNULL("+ PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + (selectedIndex - 1)]+"))";
            Vector vtEmpSch = PstEmpSchedule.list(0, 0, whereClause, "");
            if (vtEmpSch != null && vtEmpSch.size() > 0)
            {
                return true;
            }
        } 
        catch (Exception e) 
        {
            System.out.println("Exc when cekPresenceEmployee : " + e.toString());
        }
        return false;  
    }
    
    
    /**
     * @param objDpStockMgn
     */    
    public void updateDpStockManagement(DpStockManagement objDpStockMgn)
    {  
        if(objDpStockMgn != null && objDpStockMgn.getOID() != 0)
        {
            objDpStockMgn.setiDpStatus(PstDpStockManagement.DP_STS_EXPIRED);
            try 
            {
                PstDpStockManagement.updateExc(objDpStockMgn);
            }
            catch (DBException e) 
            {
                e.printStackTrace();
            }
        }
    }
    
}
