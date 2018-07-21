/**
 * Created by IntelliJ IDEA.
 * User: gadnyana
 * Date: Jun 4, 2004
 * Time: 10:42:50 AM
 * To change this template use Options | File Templates.
 */
package com.dimata.harisma.utility.service.leavedp;

import com.dimata.qdep.db.*;
import com.dimata.util.DateCalc;
import com.dimata.harisma.entity.attendance.*;
import com.dimata.harisma.entity.employee.Employee;
import com.dimata.harisma.entity.employee.PstEmployee;
import com.dimata.harisma.entity.attendance.PstAlStockManagement;
import com.dimata.harisma.entity.attendance.AlStockManagement;
import com.dimata.harisma.entity.leave.I_Leave;
import com.dimata.harisma.entity.masterdata.PstLeavePeriod;
import com.dimata.harisma.entity.masterdata.LeavePeriod;  
import com.dimata.harisma.entity.masterdata.PstPeriod;  
import com.dimata.system.entity.PstSystemProperty;
import com.dimata.util.Formater;


import java.util.Vector;
import java.util.Date;

public class ServiceAlStock   
{
    static Vector listLeave = new Vector(1, 1);
    private static boolean running = false; 
    public static final int QTY_ADD = 1;
    public static final int QTY_ADD_DEC = 2;   
   
    //update by satrya 2013-2-25
    private static ServiceAlStock serviceAlStock = null;
    private static AutomaticStockAlUpdater automaticStockAlUpdater =null;
    private Date dtSelect=null;
    
    public Date getDate(){
        return dtSelect;
    }
    
    public void setDate(Date dt ){
        dtSelect = dt;
    } 
    public static AutomaticStockAlUpdater getStockAl(){
        if(automaticStockAlUpdater !=null){
            return automaticStockAlUpdater;
        }
        else{
            return automaticStockAlUpdater = new AutomaticStockAlUpdater();
        }
        
    }
  public static ServiceAlStock getInstance(boolean withAssistant) {
        if (serviceAlStock == null) {
            serviceAlStock = new ServiceAlStock();            
            if(withAssistant){
                serviceAlStock = new ServiceAlStock();
                serviceAlStock.setRunning(false);                
            } else{
                serviceAlStock=null;
               
            }
        }
        return serviceAlStock;
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

    public void startService() 
            //public void startService() 
    {
   /* if (!isRunning()) 
        {
            System.out.println(".................... ServiceAlStock started ....................");            
            try 
            {
                setRunning(true);
                Thread thr = null;
                if(automaticStockAlUpdater!=null){
                     automaticStockAlUpdater.setRunning(true);
                    automaticStockAlUpdater.setStartDate(getDate());
                    thr = new Thread(automaticStockAlUpdater);
                }else{
                    automaticStockAlUpdater= new AutomaticStockAlUpdater();
                     automaticStockAlUpdater.setRunning(true);
                    automaticStockAlUpdater.setStartDate(getDate());
                    thr = new Thread(automaticStockAlUpdater);
                }
                
                thr.setDaemon(false);
                thr.start();

            }
            catch (Exception e) 
            {                
                System.out.println(">>> Exc on starting ServiceAlStock : " + e.toString());            
            }
        }*/
    if(automaticStockAlUpdater!=null){
            automaticStockAlUpdater.setStartDate(getDate());
            automaticStockAlUpdater.setRunning(true);
            Runnable task = (Runnable) automaticStockAlUpdater;                                      
            Thread worker = new Thread(task);
            worker.setDaemon(false);
            worker.start();                        
        }else {
            automaticStockAlUpdater= new AutomaticStockAlUpdater();
            automaticStockAlUpdater.setStartDate(getDate());
            automaticStockAlUpdater.setRunning(true);
            Runnable task = (Runnable) automaticStockAlUpdater;                                      
            Thread worker = new Thread(task);
            worker.setDaemon(false);
            worker.start();                        
    }

    }

    public boolean getStatus() 
    {
       // return isRunning();
        //update by satrya 2013-02-25
       if (automaticStockAlUpdater!=null) {                    
           return  automaticStockAlUpdater.isRunning(); 
            
        }
        else{
             return false;
        }
    }

    public void stopService() 
    {
        //setRunning(false);
        if(automaticStockAlUpdater!=null){
            automaticStockAlUpdater.setRunning(false);
           
        }
        System.out.println(".................... ServiceAlStock stoped ....................");            
    }

    
    
    /**
     * Proses update status AL (owning dan expired), di jalankan oleh thread     
     * @edited by Edhy
     */    
    public void processAlStock()   
    {  
        try 
        {
            processAlStock(new Date());
        } 
        catch (Exception e) 
        {
            System.out.println("Exc on processAlStock : " + e.toString());
        }
    }
    
    /**
     * Proses update status AL (owning dan expired), di jalankan oleh thread     
     * @edited by Edhy
     */    
    public void processAlStock(Date dtNow)   
    {  
        try 
        {
            // process AL's activated 
           // processOwningAlStock(dtNow);
            processOwningAlStockHR(dtNow);
            
            // process AL's expired
            //Khusus di hard rock tidak ada al yang expired
           // setAlStockExpired(dtNow);              
        } 
        catch (Exception e) 
        {
            System.out.println("Exc on processAlStock : " + e.toString());
        }
    }
    
    
    
    // --------------------------- START PROCESS AL OWNING ------------------------    
    /**
     * Proses perolehan AL tiap2 tanggal 1, di jalankan oleh thread     
     * adanya proses pembayaran hutang AL dengan perolehan ini jika ada :)
     *     
     * @param vct Vector of employee yang memperoleh AL bulan ini
     * @param leavePeriod OID object of leave period
     * @param dt tanggal perolehan/proses perolehan AL
     * @created by gadnyana
     */
    public static void processOwningAlStock() 
    {
        //Dengan perhitunga hardrock
        processOwningAlStock(new Date());
        //other
        //processOwningAlStock(new Date());
    }
    
    /**
     * Proses perolehan AL tiap2 tanggal 1, di jalankan oleh thread     
     * adanya proses pembayaran hutang AL dengan perolehan ini jika ada :)
     *     
     * @param vct Vector of employee yang memperoleh AL bulan ini
     * @param leavePeriod OID object of leave period
     * @param dt tanggal perolehan/proses perolehan AL
     * @created by gadnyana
     */
    public static void processOwningAlStock(Date dtNow) 
    {
        if(dtNow.getDate() == 1)
        {
            try 
            {
                String where = PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + " = " + PstEmployee.NO_RESIGN;
                Vector vct = PstEmployee.list(0, 0, where, null);            
                if (vct!=null && vct.size()>0) 
                {                                    
                    // iterate process for each employee
                    for (int i = 0; i < vct.size(); i++)   
                    {
                        Employee emp = (Employee) vct.get(i);
                        AlStockManagement alStkMnt = PstAlStockManagement.checkAlStockManagement(dtNow, emp.getOID());

                        // pengecekan apakan commencing date employee ini adalah tahu ini atau tidak
                        // karena perhitungan earn AL akan berbeda                    
                        int intWorkingDuration = 0;
                        Date empCommDate = emp.getCommencingDate();
                        if(dtNow.getYear()-empCommDate.getYear()==0)
                        {                    
                            // faktor pembagi (menyatakan entitle AL ini utk berapa bulan ???)                        
                            intWorkingDuration = dtNow.getMonth() - empCommDate.getMonth();                        
                        }                    
                        else if(dtNow.getYear()-empCommDate.getYear()>0)
                        {
                            // faktor pembagi (maximal adalah 12 jika masa kerja 1 thn atau lebih)                        
                            intWorkingDuration = 12;                                                
                        }

                        // jika masa kerja adalah lebih dari satu bulan
                        if(intWorkingDuration > 0)
                        {
                            // mencari total entitle pada tahun yang bersangkutan, diambil dari record AL terakhir
                            float entitleOfLastAl = PstAlStockManagement.getEntitleAlLast(emp.getOID());

                            // mencari total earn AL yang sudah ada sampai saat ini
                            float totalEarnAlTillToday = PstAlStockManagement.getTotalEarnALYearly(emp.getOID(), new Date());

                            // menghitung jumlah earn AL yang seharusnya diperoleh bulan ini
                            double doubleAlQtyCurrMonth = ((double)entitleOfLastAl / (double)intWorkingDuration) * ((double)(dtNow.getMonth()+1));
                            float alQtyCurrMonth = ((int) doubleAlQtyCurrMonth) - totalEarnAlTillToday;                                            

                            // belum ada AL pada bulan yang bersangkutan, maka insert data AL baru
                            if (alStkMnt.getOID() == 0) 
                            {
                                alStkMnt.setEntitled(entitleOfLastAl);                        
                                alStkMnt.setAlQty(alQtyCurrMonth);                                                
                                alStkMnt.setDtOwningDate(dtNow);
                                alStkMnt.setEmployeeId(emp.getOID());                              
                                alStkMnt.setAlStatus(PstAlStockManagement.AL_STS_AKTIF);
                                alStkMnt.setQtyResidue(QTY_ADD);  
                                alStkMnt.setQtyUsed(0);
                                alStkMnt.setStNote("");
                                PstAlStockManagement.insertExc(alStkMnt);

                                // pembayaran hutang AL jika ada                                                                                                                                     
                                Vector vectOidLeavePaid = PstAlStockManagement.paidAlPayable(emp.getOID(), alStkMnt);																												
                            }
                        }
                    }
                }
                else 
                {
                    System.out.println("No employee on prosesOwningAlStock ... ");
                }
            }
            catch (Exception e) 
            {
                System.out.println("Exc when prosesOwningAlStock : " + e.toString());   
            }
        }
    }    
    
    /**
     * untuk Hard Rock Hotel
     * Proses perolehan AL tanggal commencing date
     * adanya proses pembayaran hutang AL dengan perolehan ini jika ada :)
     * @param dt tanggal perolehan/proses perolehan AL
     * @created by gadnyana
     * @modif by artha
     */
    public static void processOwningAlStockHR(Date dtNow) 
    {
        try 
        {
            String where = PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] 
                    + " = " + PstEmployee.NO_RESIGN
                    +" AND MONTH(" + PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE] + ")" +
                       " = " + (dtNow.getMonth() + 1);
            Vector vct = PstEmployee.list(0, 0, where, null);            
            if (vct!=null && vct.size()>0) 
            {                                    
                // iterate process for each employee
                for (int i = 0; i < vct.size(); i++)   
                {
                    Employee emp = (Employee) vct.get(i);

                    // pengecekan apakan commencing date employee ini adalah tahun ini atau tidak
                    Date empCommDate = emp.getCommencingDate();

                    //Selisih tahun
                    int diff = differenceYear(empCommDate, dtNow);

                    // jika masa kerja adalah lebih dari satu tahun
                    //tidak memproleh Al jika meperoleh LL ykni pada tahun ke 7 dan 8
                    //disini dipergunakan difference tahun yakni 6 & 7
                    if(isOverThanAYear(empCommDate,dtNow) && (diff!=6 && diff!=7))
                    {
                        Date owningLL = new Date(dtNow.getYear(),empCommDate.getMonth(),empCommDate.getDate());

                        //Mencari apakah AL sudah diperoleh untuk periode ini?
                        //update by satrya 2012-10-16
                        String whereClause = PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_EMPLOYEE_ID]
                                +" = "+emp.getOID()
                                +" AND "+PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_OWNING_DATE]
                                +" = '"+Formater.formatDate(owningLL, "yyyy-MM-dd")+"'";

                        Vector vAlStockMan = new Vector(1,1);
                        try{
                            vAlStockMan = PstAlStockManagement.list(0, 0, whereClause, null);
                        }catch(Exception ex){}
                        // jika karyawan telah bekerja 12 bulang
                        if (vAlStockMan.size()<=0)
                        {
                            float alQty = 0;                          

                            I_Leave leave = null;
                            /* Instantiate configuration class */
                            try {
                                leave = (I_Leave)(Class.forName(PstSystemProperty.getValueByName("LEAVE_CONFIG")).newInstance());
                            }
                            catch(Exception e) {
                                System.out.println("Error on loading leave config " + e.getMessage());
                            }

                            String strLevel = leave.getLevel(emp.getOID());
                            String strType = leave.getCategory(emp.getOID());
                            int LoS = (int) DateCalc.dayDifference(emp.getCommencingDate(), dtNow);
                            alQty = leave.getALEntitleAnualLeave(strLevel, strType, LoS, empCommDate, dtNow );

                            AlStockManagement alStkMnt = new AlStockManagement();
                            alStkMnt.setEntitled(alQty);                        
                            alStkMnt.setAlQty(alQty);                                                
                            alStkMnt.setDtOwningDate(owningLL);
                            alStkMnt.setEmployeeId(emp.getOID());                      
                            alStkMnt.setAlStatus(PstAlStockManagement.AL_STS_AKTIF);
                            alStkMnt.setQtyResidue(alQty);  
                            alStkMnt.setQtyUsed(0);
                            alStkMnt.setStNote("");

                            PstAlStockManagement.insertExc(alStkMnt);

                            // pembayaran hutang AL jika ada                                                                                                                                     
                            Vector vectOidLeavePaid = PstAlStockManagement.paidAlPayable(emp.getOID(), alStkMnt);																												
                        }
                    }
                }
            }
            else 
            {
                System.out.println("No employee on prosesOwningAlStock ... ");
            }
        }
        catch (Exception e) 
        {
            System.out.println("Exc when prosesOwningAlStock : " + e.toString());   
        }
    }    
    
    // --------------------------- END PROCESS AL OWNING ------------------------        
    
    
    
    
    
    // --------------------------- START PROCESS AL EXPIRED ------------------------            
    /**
     * untuk expired al jika sudah memasuk tanggal 1 juli
     * @param dt
     * @edited by Edhy     
     */
    public static void setAlStockExpired()    
    {
        setAlStockExpired(new Date());
    }    
    
    /**
     * untuk expired al jika sudah memasuk tanggal 1 juli
     * @param dt
     * @edited by Edhy     
     */
    public static void setAlStockExpired(Date dt)   
    {
        // generate tanggal jatuh tempo yaitu setiap tanggal 1 juli tiap tahunnya
        Date dateExp = new Date();
        dateExp.setDate(1);
        dateExp.setMonth(6);  
        
        if ((dateExp.getDate() == dt.getDate()) && (dateExp.getMonth() == dt.getMonth())) 
        {            
            int result = 0;
            int yrPrevYear = (dt.getYear() + 1900) - 1;             
            
            // store AlStockExpired to db
            storeAlStockExpired(yrPrevYear, dateExp);                       
            try    
            {
                String sql = "UPDATE " + PstAlStockManagement.TBL_AL_STOCK_MANAGEMENT + 
                             " SET " + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_AL_STATUS] +
                             " = " + PstAlStockManagement.AL_STS_EXPIRED + 
                             ", " + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_NOTE] +
                             " = \"Expired date : " + Formater.formatDate(dt,"MMM-dd-yyyy") + "\"" + 
                             " WHERE YEAR(" + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_OWNING_DATE] + ")" + 
                             " = " + yrPrevYear +
                             " AND " + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_AL_STATUS] + 
                             " != " + PstAlStockManagement.AL_STS_EXPIRED;                                                

//                System.out.println("sQL setAlStockExpired : " + sql);               
                result = DBHandler.execUpdate(sql);                       
            }
            catch (Exception e) 
            {
                System.out.println("exc when prosesExpiredAlStock : " + e.toString());
            }
        }
    }        
    
    /**
     * untuk expired al jika sudah memasuk tanggal 1 juli
     * @param dt
     * @edited by Edhy     
     */
    public static void storeAlStockExpired(int yrPrevYear, Date dateExp) 
    {
        String whereClause = " YEAR(" + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_OWNING_DATE] + ")" + 
                             " = " + yrPrevYear +
                             " AND " + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_AL_STATUS] + 
                             " != " + PstAlStockManagement.AL_STS_EXPIRED;                
        String orderBy = PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_OWNING_DATE];
        Vector vectAlStockMan = PstAlStockManagement.list(0, 0, whereClause, orderBy);
        if(vectAlStockMan!=null && vectAlStockMan.size()>0)
        {
            int maxAlStockExpired = vectAlStockMan.size();   
            for(int i=0; i<maxAlStockExpired; i++)
            {
                AlStockManagement objAlStockManagement = (AlStockManagement) vectAlStockMan.get(i);

                // insert AlStockExpired
                try
                {
                    PstAlStockExpired objPstAlStockExpired = new PstAlStockExpired();
                    AlStockExpired objAlStockExpired = new AlStockExpired();
                    objAlStockExpired.setAlStockId(objAlStockManagement.getOID());
                    objAlStockExpired.setExpiredDate(dateExp);
                    objAlStockExpired.setExpiredQty(objAlStockManagement.getQtyResidue());
                    objPstAlStockExpired.insertExc(objAlStockExpired);
                }
                catch(Exception e)
                {
                    System.out.println("Exc when insert alStockExpired : " + e.toString());
                }
            }                
        }            
    }        
    // --------------------------- END PROCESS AL EXPIRED ------------------------            
    
    
    /**
     * Mengecek apakah employee sudah bekerja lebih dari 1 tahun
     */
    public static boolean isOverThanAYear(Date commDate, Date currDate){
        //update by satrya 2013-09-26
        //private static boolean isOverThanAYear(Date commDate, Date currDate){
        boolean isValid = false;
        Date dateCommCurr = (Date)commDate.clone();
        dateCommCurr.setYear(commDate.getYear()+1);
        
        //Jika bekerja lebih dari 1 tahun
        if(dateCommCurr.getTime()<=currDate.getTime()){
            //jika pada bulan ini, tetapi belum melewati tanggal sekarang
            if(dateCommCurr.getDate()<currDate.getDate()){
                isValid = true;
            }else{
                isValid = false;
            }
        }
        
        return isValid;
    }
    
    
    /**
     * Mancari selisih dua tahun
     * @param commDate : tanggal commencing
     * @param currDate : tanggal sekarang
     * @return int : selisih dalam tahun
     */
   private static int differenceYear(Date commDate, Date currDate){
      
        int difference = 0;
        if(currDate.getYear()-commDate.getYear()>=1){
            //Hanya selish 1 tahun
            if(currDate.getYear()-commDate.getYear()==1){
                // cek apakah bulan sekarang jd seuda lewat dari comm date
                //pada bulan yang sama
                if(currDate.getMonth()-commDate.getMonth()==0){
                    if(currDate.getDate()-commDate.getDate()>0){
                        return 1;//hanya selisih 1 tahun
                    }
                }else if(currDate.getMonth()-commDate.getMonth()>0){//lebih dari satu bulan
                    return 1;//hanya selisih 1 tahun
                }
            }else{//lebih dari 1 tahun
                // cek apakah bulan sekarang jd seuda lewat dari comm date
                //pada bulan yang sama
                int diff = currDate.getYear()-commDate.getYear();
                if(currDate.getMonth()-commDate.getMonth()==0){
                    if(currDate.getDate()-commDate.getDate()>0){
                        return diff;
                    }else{
                        return (diff-1);
                    }
                }else if(currDate.getMonth()-commDate.getMonth()>0){//lebih dari satu bulan
                    return diff;//hanya selisih 1 tahun
                }else{
                    return (diff-1);
                }
            }
        }
        return difference;
    } 
    
    
    /**
     * @param args
     */    
    public static void main(String[] args) {
        ServiceAlStock srv = new ServiceAlStock();  
        srv.processOwningAlStockHR(new Date());          
    }
}
