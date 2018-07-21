/**
 * Created by IntelliJ IDEA.
 * User: gadnyana
 * Date: Jun 4, 2004
 * Time: 10:42:50 AM
 * To change this template use Options | File Templates.
 */
package com.dimata.harisma.utility.service.leavedp;  

// import core java package
import com.dimata.common.entity.logger.PstLoginLog;
import java.util.*;

// import project harisma package
import com.dimata.harisma.entity.employee.*;
import com.dimata.harisma.entity.attendance.*;
import com.dimata.harisma.entity.leave.I_Leave;
import com.dimata.harisma.session.attendance.SessAnnualLeave;

import com.dimata.harisma.session.attendance.SessLongLeave;
import com.dimata.system.entity.PstSystemProperty;
import com.dimata.util.Formater;

public class ServiceLLStock 
{
    
    static Vector listLeave = new Vector(1, 1);
    static boolean running = false;
    public static final int QTY_ENTITLED = 60;   
        private static AutomaticStockLLUpdater automaticStockLLUpdater =null;
       //update by satrya 2013-03-19
   private Date dtSelect=null;
     public Date getDate(){
        return dtSelect;
    }
    
    public void setDate(Date dt ){
        dtSelect = dt;
    } 
    public void startService() 
    {
        /*if (!running) 
        {
            System.out.println(".................... ServiceLLStock started ....................");            
            try 
            {
                running = true;
                Thread thr = new Thread(new AutomaticStockLLUpdater());
                thr.setDaemon(false);
                thr.start();  

            }
            catch (Exception e) 
            {
                System.out.println(">>> Exc on starting ServiceLLStock : " + e.toString());
            }
        }*/
        if(automaticStockLLUpdater!=null){
                  automaticStockLLUpdater.setStartDate(getDate());
                  automaticStockLLUpdater.setRunning(true);
                  Runnable task = (Runnable) automaticStockLLUpdater;                                      
                  Thread worker = new Thread(task);
                  worker.setDaemon(false);
                  worker.start();                        
              }else {
                  automaticStockLLUpdater= new AutomaticStockLLUpdater();
                   automaticStockLLUpdater.setStartDate(getDate());
                  automaticStockLLUpdater.setRunning(true);
                  Runnable task = (Runnable) automaticStockLLUpdater;                                      
                  Thread worker = new Thread(task);
                  worker.setDaemon(false);
                  worker.start();                        
          }
    }

    public boolean getStatus() 
    {
       // return running;
        if (automaticStockLLUpdater!=null) {                    
           return  automaticStockLLUpdater.isRunning(); 
            
        }
        else{
             return false;
        }
    }

    public void stopService() 
    {
           //setRunning(false);
        if(automaticStockLLUpdater!=null){
            automaticStockLLUpdater.setRunning(false);
           
        }
        System.out.println(".................... ServiceLLStock stoped ....................");            
    }


    
    
    // --------------------------- START PROCESS LL OWNING ------------------------    
    /**
     * @param dt
     * @created by Gadnyana
     * @edited by Edhy, Artha
     */    
    public static void processLLStockTread(Date dt) 
            //update by satrya 2013-07-01
            //public static void processLLStock() 
    {   
     if(dt!=null){
        processLLStockHR(dt);
        //update by satrya 2013-07-01
        //processLLStockHR(new Date());
        
        //Taken
        processLLTaken(dt);
        //update by satrya 2013-07-01
        //processLLTaken(new Date());
     }
    }
    
    public static void processLLTaken(Date dt){
        System.out.println("==========================================================");
        System.out.println("...............  START TAKEN LL PROCESS ..................");
        System.out.println("...............     --- APPORVAL---     ..................");
        System.out.println("==========================================================");
        SessLongLeave.processTakenLLApproved(dt);
    }
    
    
    /**
     * @param dt
     * @created by Gadnyana
     * @edited by Edhy
     */    
    public static void processLLStock(Date dt) 
    {        
        try 
        {    
            String where = PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + 
                           " = " + PstEmployee.NO_RESIGN +
                           " AND MONTH(" + PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE] + ")" +
                           " = " + (dt.getMonth() + 1);
            Vector vct = PstEmployee.list(0, 0, where, null);                        
            if(vct!=null && vct.size()>0)
            {            
                int currYear = dt.getYear() + 1900;
                String whereClause = "";
                for (int i=0; i<vct.size(); i++) 
                {
                    Employee emp = (Employee) vct.get(i);
                    try 
                    {
                        Date cmDate = emp.getCommencingDate();
                        int cmYear = cmDate.getYear() + 1900;
                        int age = currYear - cmYear;
                        int LLAdd = QTY_ENTITLED;                    
                        int max = age / 6;

                        // jika masa kerja lebih dari 5 tahun
                        if (max >= 0) 
                        {                   
                            // iterasi sebanyak masa kerja (kelipatan 5 tahun)
                            for (int k=0; k<max; k++) 
                            {                                               
                                whereClause = PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_EMPLOYEE_ID] + "=" + emp.getOID() +                                          
                                              " AND " + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_ENTITLED] + "=" + (k + 1);
                                Vector vtll = PstLLStockManagement.list(0, 0, whereClause, "");

                                // jika LL belum ada
                                if (vtll==null || vtll.size()==0) 
                                { 
                                    // jika pas 5 tahun, bulan sekarang
                                    if ((age % 5 == 0)) 
                                    { 
                                        // cek lagi jika tahun nya tidak sama
                                        whereClause = whereClause + " AND YEAR(" + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_OWNING_DATE] + ")=" + (dt.getYear() + 1900);                                    
                                        vtll = PstLLStockManagement.list(0, 0, whereClause, "");

                                        // jika LL belum ada
                                        if (vtll == null || vtll.size() == 0) 
                                        { 
                                            // jika pas dengan bulan, tanggal sekarang
                                            if ((dt.getMonth() == cmDate.getMonth()) && (dt.getDate() == cmDate.getDate())) 
                                            {  
                                                LLStockManagement llStockManagement = new LLStockManagement();                                            
                                                if (llStockManagement.getOID() == 0) 
                                                {
                                                    llStockManagement.setEmployeeId(emp.getOID());
                                                    llStockManagement.setLLQty(LLAdd);                                                
                                                    llStockManagement.setLLStatus(PstLLStockManagement.LL_STS_AKTIF);
                                                    llStockManagement.setQtyUsed(0);
                                                    llStockManagement.setQtyResidue(llStockManagement.getLLQty() - llStockManagement.getQtyUsed());                                                
                                                    llStockManagement.setStNote("");
                                                    llStockManagement.setDtOwningDate(dt);
                                                    llStockManagement.setEntitled(k + 1);
                                                    llStockManagement.setLeavePeriodeId(0);
                                                    try 
                                                    {
                                                        PstLLStockManagement.insertExc(llStockManagement);
                                                    } 
                                                    catch (Exception ex) 
                                                    {
                                                        System.out.println("Error InsertLL : " + ex.toString());
                                                    }
                                                }
                                            }
                                        }
                                    }

  
                                    // jika 5 tahun bekerja sudah lewat
                                    else 
                                    { 
                                        LLStockManagement llStockManagement = new LLStockManagement();                                    
                                        if (llStockManagement.getOID() == 0) 
                                        {
                                            llStockManagement.setEmployeeId(emp.getOID());
                                            llStockManagement.setLLQty(LLAdd);                                        
                                            llStockManagement.setLLStatus(PstLLStockManagement.LL_STS_AKTIF);                                        
                                            llStockManagement.setQtyUsed(0);
                                            llStockManagement.setQtyResidue(llStockManagement.getLLQty() - llStockManagement.getQtyUsed());                                                
                                            llStockManagement.setStNote("");

                                            int yr = cmDate.getYear();
                                            yr = yr + ((k + 1) * 5);
                                            Date dtCrt = new Date(yr,cmDate.getMonth(),cmDate.getDate());
                                            llStockManagement.setDtOwningDate(dtCrt);
                                            llStockManagement.setEntitled(k + 1);
                                            llStockManagement.setLeavePeriodeId(0);

                                            try 
                                            {
                                                PstLLStockManagement.insertExc(llStockManagement);
                                            }
                                            catch (Exception ex) 
                                            {
                                                System.out.println("Error InsertLL : " + ex.toString());
                                            }
                                        }
                                    }
                                }
                            }
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
    
    /**
     * @param dt
     * @created by Gadnyana
     * @edited by Artha
     */    
    public static void processLLStockHR(Date dt) 
    {        
        try 
        {    
            String where = PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + 
                           " = " + PstEmployee.NO_RESIGN +
                           " AND MONTH(" + PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE] + ")" +
                           " = " + (dt.getMonth() + 1);
            Vector vct = PstEmployee.list(0, 0, where, null);                        
            if(vct!=null && vct.size()>0)
            {            
                int currYear = dt.getYear() + 1900;
                String whereClause = "";
                for (int i=0; i<vct.size(); i++) 
                {
                    Employee emp = (Employee) vct.get(i);
                    try 
                    {
                        //update by satrya 2013-07-06
                        Date cmDate = null;
                        int cmYear =0;
                        if(emp.getCommencingDate()!=null){
                            cmDate = emp.getCommencingDate();
                            cmYear = cmDate.getYear() + 1900;
                        }
//                       if(emp.getFullName().equalsIgnoreCase("I WAYAN DIANA")){
//                          boolean x= true;
//                       }
                        //int cmYear = cmDate.getYear() + 1900;
                        int age = currYear - cmYear;
                        int LLAdd = QTY_ENTITLED;
                        I_Leave leave = null;
                        /* Instantiate configuration class */
                        try {
                            leave = (I_Leave)(Class.forName(PstSystemProperty.getValueByName("LEAVE_CONFIG")).newInstance());
                        }
                        catch(Exception e) {
                            System.out.println("Error on loading leave config " + e.getMessage());
                            return;
                        }
                        int[] intervalLL = leave.getIntervalLLinMonths();
                        int max = (intervalLL[leave.INTERVAL_LL_5_YEAR]/12)!=0?age / (intervalLL[leave.INTERVAL_LL_5_YEAR]/12):-1;
                        //hanya untuk test
                        /* if(""+emp.getOID()=="504404343614772223" || emp.getFullName().equalsIgnoreCase("AGUS MARIYANTO")){
                                    int tx=0;
                                }*/
                    if(cmDate!=null){
                        // jika masa kerja lebih dari 5 tahun
                        if (max >= 0) 
                        {            
                            if ((age % (intervalLL[leave.INTERVAL_LL_5_YEAR]/12) == 0)) 
                                {
                               
                                //Cari LL yang di peroleh pada tahun ini
                                //algoritma : owning date dari LL adalah sehari setelah commencing date di tahun ke 6 atau di awal tahun ke 7
                                Date owningLL = new Date(dt.getYear(),cmDate.getMonth(),cmDate.getDate());
                                whereClause = PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_EMPLOYEE_ID] + "=" + emp.getOID() +                                          
                                              " AND " + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_OWNING_DATE] 
                                              + "= '" +Formater.formatDate(owningLL, "yyyy-MM-dd")+"'";
                                Vector vtll = PstLLStockManagement.list(0, 0, whereClause, "");

                                // jika LL belum ada
                                if (vtll==null || vtll.size()==0) 
                                { 
                                    //sudah waktunya untuk memiliki LL, karena commencing Date pada tahun
                                    //sekarang telah minimal lewat 1 hari
                                    if(dt.getTime() > owningLL.getTime()){
                                        LLStockManagement llStockMan = new LLStockManagement();
                                        llStockMan.setDtOwningDate(owningLL);
                                        llStockMan.setEmployeeId(emp.getOID());
                                        llStockMan.setQtyUsed(0);
                                        llStockMan.setLLStatus(PstLLStockManagement.LL_STS_AKTIF);
                                        
                                        int qtyLL = 0;
                                        String strLevel = leave.getLevel(emp.getOID());
                                        String strType = leave.getCategory(emp.getOID());
                                        qtyLL = leave.getLLEntile(strLevel, strType, intervalLL[leave.INTERVAL_LL_5_YEAR]);
                                        llStockMan.setLLQty(qtyLL);
                                        llStockMan.setEntitled(qtyLL);
                                        llStockMan.setQtyResidue(qtyLL);
                                        Date entitleDate = null;
                                        if(cmDate!=null){
                                            Date commercingDate = cmDate;
                                            commercingDate.setYear(dt.getYear());
                                            entitleDate = commercingDate;
                                        }
                                        //update by satrya 2013-07-07
                                        llStockMan.setEntitledDate(entitleDate);
                                        int monthExp = 0;
                                        monthExp = leave.getLLValidityMonths(strLevel, strType, intervalLL[leave.INTERVAL_LL_5_YEAR]);
                                        int iYear = monthExp / 12;
                                        int iMonth = monthExp % 12;
                                        Date dateExp = (Date) owningLL.clone();
                                        dateExp.setYear(owningLL.getYear()+iYear);
                                        dateExp.setMonth(owningLL.getMonth()+iMonth);

                                        llStockMan.setExpiredDate(dateExp);
                                        try{
                                            PstLLStockManagement.insertExc(llStockMan);
                                        }catch(Exception ex){
                                            System.out.println("Exception insert LL"+ex);
                                        }
                                    }

                                }
                                //UPDATE by satrya 2013-08-18
                                //untuk menghitung tambahan Al jika dia bekerja sdh lebih dari 5 tahun
                                String hitungAlStok = leave.setAutoALEntitle(dt,emp.getOID());
                            }
                        }
                    }//cek jika commercing datenya tdk = null
                        //Membayar LL yang belum di paid
                        //by atrha
                        SessLongLeave.proccessPaid(emp.getOID());
                        
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
    
    /**
     * keterangan: prosess entitle LL automatic
     * create by satrya 2013-09-27
     * @param dt
     * @param leave 
     */
    public static void processLLStockHR(Date dt,I_Leave leave) 
    {        
        try 
        {    
            String where = PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + 
                           " = " + PstEmployee.NO_RESIGN +
                           " AND MONTH(" + PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE] + ")" +
                           " = " + (dt.getMonth() + 1);
            Vector vct = PstEmployee.list(0, 0, where, null);                        
            if(vct!=null && vct.size()>0)
            {            
                int currYear = dt.getYear() + 1900;
                String whereClause = "";
                for (int i=0; i<vct.size(); i++) 
                {
                    Employee emp = (Employee) vct.get(i);
                    if(emp!=null && emp.getFullName()!=null && emp.getFullName().equalsIgnoreCase("NI PUTU HARTINI")){
                        boolean x=true;
                    }
                    try 
                    {
                        //update by satrya 2013-07-06
                        Date cmDate = null;
                        int cmYear =0;
                        if(emp.getCommencingDate()!=null){
                            cmDate = emp.getCommencingDate();
                            cmYear = cmDate.getYear() + 1900;
                        }
//                       if(emp.getFullName().equalsIgnoreCase("I WAYAN DIANA")){
//                          boolean x= true;
//                       }
                        //int cmYear = cmDate.getYear() + 1900;
                        int age = currYear - cmYear;
                        int LLAdd = QTY_ENTITLED;
                        
                        int[] intervalLL = leave.getIntervalLLinMonths();
                        int max = (intervalLL[leave.INTERVAL_LL_5_YEAR]/12)!=0 && age!=0 ?age / (intervalLL[leave.INTERVAL_LL_5_YEAR]/12):-1;
                        //hanya untuk test
                        if(/*emp.getFullName().equalsIgnoreCase("DJOKO YULISTIAN") 
                                || emp.getFullName().equalsIgnoreCase("I NYOMAN WIDIANA")
                                || emp.getFullName().equalsIgnoreCase("I Ketut Budi Astawa")
                                || emp.getFullName().equalsIgnoreCase("I Made Mahardika")
                                || emp.getFullName().equalsIgnoreCase("I Nyoman Waisnawa")
                                || emp.getFullName().equalsIgnoreCase("Ida Ayu Gede Wijayanti")||*/
                                 emp.getFullName().equalsIgnoreCase("ANAK AGUNG AYU TRISNAWATI") || 
                                emp.getFullName().equalsIgnoreCase("MARINA EFENDI")// ||
                               // emp.getFullName().equalsIgnoreCase("MADE DWI SUARDIANA") ||
                               // emp.getFullName().equalsIgnoreCase("Hesti Ratna Hapsari")
                                ){
                                    int tx=0;
                        }
                    if(cmDate!=null){
                        // jika masa kerja lebih dari 5 tahun
                        if (max >= 0) 
                        {            
                            if ((age % (intervalLL[leave.INTERVAL_LL_5_YEAR]/12) == 0)) 
                                {
                               
                                //Cari LL yang di peroleh pada tahun ini
                                //algoritma : owning date dari LL adalah sehari setelah commencing date di tahun ke 6 atau di awal tahun ke 7
                                Date owningLL = new Date(dt.getYear(),cmDate.getMonth(),cmDate.getDate());
                                whereClause = PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_EMPLOYEE_ID] + "=" + emp.getOID() +                                          
                                              " AND " + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_OWNING_DATE] 
                                              + "= '" +Formater.formatDate(owningLL, "yyyy-MM-dd")+"'";
                                Vector vtll = PstLLStockManagement.list(0, 0, whereClause, "");

                                // jika LL belum ada
                                if (vtll==null || vtll.size()==0) 
                                { 
                                    //sudah waktunya untuk memiliki LL, karena commencing Date pada tahun
                                    //sekarang telah minimal lewat 1 hari
                                    if(dt.getTime() > owningLL.getTime()){
                                        LLStockManagement llStockMan = new LLStockManagement();
                                        llStockMan.setDtOwningDate(owningLL);
                                        llStockMan.setEmployeeId(emp.getOID());
                                        llStockMan.setQtyUsed(0);
                                        llStockMan.setLLStatus(PstLLStockManagement.LL_STS_AKTIF);
                                        
                                        int qtyLL = 0;
                                        String strLevel = leave.getLevel(emp.getOID());
                                        String strType = leave.getCategory(emp.getOID());
                                        qtyLL = leave.getLLEntile(strLevel, strType, intervalLL[leave.INTERVAL_LL_5_YEAR]);
                                        llStockMan.setLLQty(qtyLL);
                                        llStockMan.setEntitled(qtyLL);
                                        llStockMan.setQtyResidue(qtyLL);
                                        Date entitleDate = null;
                                        if(cmDate!=null){
                                            Date commercingDate = cmDate;
                                            commercingDate.setYear(dt.getYear());
                                            entitleDate = commercingDate;
                                        }
                                        //update by satrya 2013-07-07
                                        llStockMan.setEntitledDate(entitleDate);
                                        int monthExp = 0;
                                        monthExp = leave.getLLValidityMonths(strLevel, strType, intervalLL[leave.INTERVAL_LL_5_YEAR]);
                                        int iYear = monthExp / 12;
                                        int iMonth = monthExp % 12;
                                        Date dateExp = (Date) owningLL.clone();
                                        dateExp.setYear(owningLL.getYear()+iYear);
                                        dateExp.setMonth(owningLL.getMonth()+iMonth);

                                        llStockMan.setExpiredDate(dateExp);
                                        try{
                                            PstLLStockManagement.insertExc(llStockMan);
                                        }catch(Exception ex){
                                            System.out.println("Exception insert LL"+ex);
                                        }
                                    }

                                }
                                //UPDATE by satrya 2013-08-18
                                //untuk menghitung tambahan Al jika dia bekerja sdh lebih dari 5 tahun
                                String hitungAlStok = leave.setAutoALEntitle(dt,emp.getOID());
                            }
                        }
                    }//cek jika commercing datenya tdk = null
                        //Membayar LL yang belum di paid
                        //by atrha
                        SessLongLeave.proccessPaidLL(emp.getOID());
                        //SessAnnualLeave.proccessPaid(emp.getOID());
                        
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
    // --------------------------- END PROCESS LL OWNING ------------------------    
    
    
    /**
     * @param args
     */    
    public static void main(String[] args) {
        ServiceLLStock srv = new ServiceLLStock();
        srv.startService();
    }
}
