/*
 * PrintPaySlip.java
 *
 * Created on July 12, 2007, 3:20 PM
 */

package com.dimata.harisma.printout;

/* package java */
import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.Date;

import com.dimata.harisma.entity.payroll.*;
import com.dimata.printman.DSJ_PrinterService;
import com.dimata.printman.DSJ_PrintObj;
import com.dimata.qdep.form.FRMHandler;
import com.dimata.util.Formater;
import com.dimata.harisma.entity.payroll.*;
import com.dimata.harisma.entity.employee.*;
import com.dimata.harisma.entity.masterdata.*;
import com.dimata.system.entity.system.*;

import java.util.Vector;
import java.util.StringTokenizer;

/**
 *
 * @author  yunny
 */
public class PrintPaySlip {
    
    static int rowx = 0;
    /** Creates a new instance of PrintPaySlip */
    public PrintPaySlip() {
    }
    
    
    public synchronized static DSJ_PrintObj PrintForm(String[] strEmpOid, long periodId, Vector listCetakGaji, Vector listEmp, int keyPeriod,long payGroupId) {
        DSJ_PrintObj obj = new DSJ_PrintObj();
        try {
            rowx = 0;
            if(obj==null)
                obj = new DSJ_PrintObj();
            
            DSJ_PrinterService prnSvc = DSJ_PrinterService.getInstance();
            /* ====== set start main page ======== */
            //obj.setObjDescription(" ******** PRINT DAFTAR GAJI ******** ");
            //obj.setObjDescription(" ******** PRINT DAFTAR GAJI2 ******** ");
            
            obj.setPageLength(31);
            obj.setPageWidth(160);
            obj.setSkipLineIsPaperFix(2);
            
            int linebackup = 0;
            int rowxAwal = 0;
            int rowSelisih = 0;
            
            for(int k=0;k<listEmp.size();k++){
                Vector listCetak = (Vector)listCetakGaji.get(k);
                long employeeId = Long.parseLong((String)strEmpOid[k]);
                rowxAwal = rowx ;
                //update by satrya 2013-01-25
                obj = printDftGaji(obj, listCetak, periodId, employeeId, keyPeriod,0);//2
                rowSelisih = rowx - rowxAwal;
                //linebackup = rowx - linebackup; // 23  
                int intmod = obj.getPageLength() - rowSelisih;
                if(intmod > 0){
                    int[] coly = {40,40};
                    //int[] coly = {75,75};
                    obj.newColumn(2,"",coly);
                    for(int a=0;a< intmod; a++){
                        rowx++;    
                        //linebackup++;
                        obj.setColumnValue(0,rowx,"",obj.TEXT_LEFT);
                    }
                  
                }else if(intmod < 0){
                    int[] coly = {40,40};
                    // int[] coly = {75,75};
                    obj.newColumn(2,"",coly);
                    for(int a=0;a< (intmod*-1);a++){
                        //linebackup++;
                        rowx++;
                        obj.setColumnValue(0,rowx,"",obj.TEXT_LEFT);
                    }
                }
                
               
                
            }
            
            //set printer in use
            
            obj.setPrnIndex(0);
            
            //set max line in page
            //obj.setPageLength(60);
            //obj.setSkipLineIsPaperFix(6);
            //set top write page
            obj.setTopMargin(1);
            //set start printer to write left
            obj.setLeftMargin(0);
            obj.setRightMargin(0);
            //description print
            
            //set caracter/ cpi to use in page
            //obj.setCpiIndex(obj.PRINTER_12_CPI); // 12 CPI = 96 char /line
            obj.setCpiIndex(obj.PRINTER_12_CPI);
            obj.setFont(obj.ROMAN);
            /* ======== end main page ========= */
            
        } catch (Exception exc) {
            exc.printStackTrace();
            System.out.println("Exc PrintForm : " + exc);
        }
        return obj;
    }
    // method ini adalah isinya
    public static DSJ_PrintObj printDftGaji(DSJ_PrintObj obj,Vector listCetakGaji,long periodeId,long employeeId, int keyPeriod,long payGroupId){
        int halaman  = 1;
        //System.out.println("listCetakGaji="+listCetakGaji.size());
        int maxListCetakGj  = listCetakGaji.size();
        if(listCetakGaji!=null&&maxListCetakGj>0){
            // int rowy = 0;
            int pageLngth = 0;
            int count = 1;
            int countPage = 0;
            boolean bool = false;
            int noUrut = 0;
            
            int listGajiCount = 0;
            boolean checkHal = false;
            boolean checkGol = false;
            boolean checkSatker = false;
            
            for(int j=0;j<maxListCetakGj;j++){
                System.out.println(">>>>>>>>>>rowx : "+rowx);
                int lengthHeader = 15;
                int start = 0;
                int end = 0;
                int val = 0;
                int valComp = 0;
                long oidSatker = 0;
                long oidPeriode = 0;
                Vector temp = (Vector)listCetakGaji.get(j);
                PayComponent payComponent= (PayComponent)temp.get(0);
                SalaryLevelDetail salaryLevelDetail = (SalaryLevelDetail) temp.get(1);
                PaySlipComp paySlipComp = (PaySlipComp) temp.get(2);
                noUrut = noUrut+1;
                
                String paySlipId = ""+paySlipComp.getOID();
                String salaryLevel = ""+salaryLevelDetail.getLevelCode();
                int compType = payComponent.getCompType();
                PaySlip paySlip= new PaySlip();
                employeeId = 0;
                String department = "";
                String position ="";
                String section ="";
                String division ="";
                Date commDate = new Date();
                int statusApp = 0;
                int statusPaid = 0;
                String frmCurrency = "#,###";
                double presenceDay=0;
                double paidLeave = 0;
                double absent = 0;
                double unpaidLeave = 0;
                double late = 0;
                long bankId = 0;
                double totalBenefit = PstSalaryLevelDetail.getSumBenefitDoub(PstSalaryLevelDetail.YES_TAKE,salaryLevel,PstPayComponent.TYPE_BENEFIT,paySlipComp.getOID(),keyPeriod,payGroupId);
               double totalDeduction = PstSalaryLevelDetail.getSumBenefitDoub(PstSalaryLevelDetail.YES_TAKE,salaryLevel,PstPayComponent.TYPE_DEDUCTION,paySlipComp.getOID(),keyPeriod,payGroupId);
               
                try{
                    paySlip = PstPaySlip.fetchExc(paySlipComp.getOID());
                    employeeId = paySlip.getEmployeeId();
                    //department = paySlip.getDepartment();
                    position = paySlip.getPosition();
                    division = paySlip.getDivision();
                    //section = paySlip.getSection();
                    commDate = paySlip.getCommencDate();
                    statusApp = paySlip.getStatus();
                    statusPaid = paySlip.getPaidStatus();
                    presenceDay = paySlip.getDayPresent();
                    paidLeave = paySlip.getDayPaidLv();
                    absent = paySlip.getDayAbsent();
                    unpaidLeave = paySlip.getDayUnpaidLv();
                    late = paySlip.getDayLate();
                    bankId = paySlip.getBankId();
                    
                }catch(Exception e){
                    System.out.println("err:"+e.toString());
                }
                
                String strStatusApp = "";
                String strStatusPaid = "";
              //if(statusApp==PstPaySlip.YES_APPROVE){
                    strStatusApp = "APPROVED";
               /* }
                else{
                    strStatusApp = "DRAFT";
                }*/
               //if(statusPaid==PstPaySlip.YES_PAID){ 
                    strStatusPaid = "PAID";  
              /*  }
                else{
                    strStatusPaid = "NOT PAID";
                }*/
                // ambil karywan yang memiliki employee id diatas
                Employee employee = new Employee();
                String fullName = "";
                String empNum = "";
                long departmentId= 0;
                long sectionId = 0;
                try{
                    employee = PstEmployee.fetchExc(employeeId);
                    fullName = employee.getFullName();
                    empNum = employee.getEmployeeNum();
                    departmentId = employee.getDepartmentId();
                    sectionId = employee.getSectionId();
                    // System.out.println(">>>>>>>>>>getEmployeeId : "+employeeId);
                }catch(Exception e){
                    System.out.println("err:"+e.toString());
                }
                
                //ambil nama department dari departmentId yang didapat
                Department dept = new Department();
                try{
                    dept = PstDepartment.fetchExc(departmentId);
                    department = dept.getDepartment();
                    // System.out.println(">>>>>>>>>>getEmployeeId : "+employeeId);
                }catch(Exception e){
                    System.out.println("err: get Department"+e.toString());
                }
                
                //ambil nama section dari sectionId yang didapat
                Section sect = new Section();
                try{
                    sect = PstSection.fetchExc(sectionId);
                    section = sect.getSection();
                    // System.out.println(">>>>>>>>>>getEmployeeId : "+employeeId);
                }catch(Exception e){
                    System.out.println("err: get Section"+e.toString());
                }
                
                //ambil no Rekenning employee :
                String bankAccountNr = PstPayEmpLevel.getBankAccNr(employeeId);
                /*boolean ketPanjang = true;
                String ket2 ="";*/
                //todo
                
                if((j==0)||(checkHal))/*||(checkSatker)||(checkGol))*/{
                    //rowx++;
                    obj = mainHeader(obj, periodeId, fullName,department,position,division,section,salaryLevel,commDate,strStatusApp,strStatusPaid, empNum);
                    checkHal = false;
                    checkGol = false;
                    checkSatker = false;
                    //rowx = rowx + (6*(j+1));
                    // rowx = rowx + 6;
                    // rowy = rowx;
                }
                
                //int[] coly = {5,29,7,11,11,11,7,8,11,7,7,8,8,8,11,13};
                //1,2, 3,4, 5, 6, 7,8,9
                //int[] coly = {5,29,7,11,11,11,7,8,17,7,7,8,8,8,11,7};
                if(j==0){
                    //int[] coly = {20,15,5,20,15,5};
                    int[] coly = {30,30,20,30,20,20};
                    obj.newColumn(6,"",coly);
                }
                
                rowx++;
                //ROW 8
                /*
                if(salaryLevelDetail.getLevelCode().equals(""+PstSystemProperty.getValueByName("SALARY_LEVEL4")) ||salaryLevelDetail.getLevelCode().equals(""+PstSystemProperty.getValueByName("SALARY_LEVEL5")) || salaryLevelDetail.getLevelCode().equals(""+PstSystemProperty.getValueByName("SALARY_LEVEL6")) || salaryLevelDetail.getLevelCode().equals(""+PstSystemProperty.getValueByName("SALARY_STAFF_TRAINING"))){
                   if(payComponent.getCompCode().equals(""+PstSystemProperty.getValueByName("COMP_CODE1"))){
                    String compName = PstPayComponent.getComponentName(PstSystemProperty.getValueByName("COMP_CODE1"));
                    obj.setColumnValue(0,rowx,""+compName,obj.TEXT_LEFT);
                    obj.setColumnValue(1,rowx,""+Formater.formatNumber(Double.parseDouble(PstSystemProperty.getValueByName("GP_VALUE")),frmCurrency),obj.TEXT_RIGHT);
                    obj.setColumnValue(2,rowx,"",obj.TEXT_RIGHT);
                    obj.setColumnValue(3,rowx,"",obj.TEXT_RIGHT);
                    obj.setColumnValue(4,rowx,"",obj.TEXT_RIGHT);
                    obj.setColumnValue(5,rowx,"",obj.TEXT_RIGHT);
                   }
                   else if(payComponent.getCompCode().equals(""+PstSystemProperty.getValueByName("COMP_CODE2"))){
                     String compName = PstPayComponent.getComponentName(PstSystemProperty.getValueByName("COMP_CODE2"));
                     obj.setColumnValue(0,rowx,""+compName,obj.TEXT_LEFT);
                     obj.setColumnValue(1,rowx,""+Formater.formatNumber(Double.parseDouble(PstSystemProperty.getValueByName("TJ.TTP_VALUE")),frmCurrency),obj.TEXT_RIGHT);
                     obj.setColumnValue(2,rowx,"",obj.TEXT_RIGHT);
                     obj.setColumnValue(3,rowx,"",obj.TEXT_RIGHT);
                     obj.setColumnValue(4,rowx,"",obj.TEXT_RIGHT);
                     obj.setColumnValue(5,rowx,"",obj.TEXT_RIGHT);
                   }
                   totalBenefit = Double.parseDouble(PstSystemProperty.getValueByName("GP_VALUE")) + Double.parseDouble(PstSystemProperty.getValueByName("TJ.TTP_VALUE")) ;
                   totalDeduction = 0;
                     
                }else*/{
                   if(compType==PstPayComponent.TYPE_BENEFIT){
                    obj.setColumnValue(0,rowx,""+payComponent.getCompName(),obj.TEXT_LEFT);
                    obj.setColumnValue(1,rowx,""+Formater.formatNumber(paySlipComp.getCompValue(),frmCurrency),obj.TEXT_RIGHT);
                    obj.setColumnValue(2,rowx,"",obj.TEXT_RIGHT);
                    obj.setColumnValue(3,rowx,"",obj.TEXT_RIGHT);
                    obj.setColumnValue(4,rowx,"",obj.TEXT_RIGHT);
                    obj.setColumnValue(5,rowx,"",obj.TEXT_RIGHT);
                    }
                    else{
                        obj.setColumnValue(0,rowx,"",obj.TEXT_LEFT);
                        obj.setColumnValue(1,rowx,"",obj.TEXT_RIGHT);
                        obj.setColumnValue(2,rowx,"",obj.TEXT_RIGHT);
                        obj.setColumnValue(3,rowx,""+payComponent.getCompName(),obj.TEXT_LEFT);
                        obj.setColumnValue(4,rowx,""+Formater.formatNumber(paySlipComp.getCompValue(),frmCurrency),obj.TEXT_RIGHT);
                        obj.setColumnValue(5,rowx,"",obj.TEXT_RIGHT);
                    }
                }
                
                if(j==(maxListCetakGj-1)){
                    rowx++;
                    /*int[] colt = {100};
                    obj.newColumn(1,"",colt);
                    obj.setColumnValue(0,rowx,"===============================================================================",obj.TEXT_LEFT);
                    */
                     obj.setCpiIndex(DSJ_PrintObj.PRINTER_12_CPI);
                     obj.addRptLine("=");
                    
                    
                    rowx++;
                    //int[] colz = {20,15,5,20,15,5};
                    int[] colz = {30,30,20,30,20,20};
                    obj.newColumn(6,"",colz);
                    obj.setColumnValue(0,rowx,"Total Benefit : ",obj.TEXT_LEFT);
                    obj.setColumnValue(1,rowx,""+Formater.formatNumber(totalBenefit,frmCurrency),obj.TEXT_RIGHT);
                    obj.setColumnValue(2,rowx,"",obj.TEXT_RIGHT);
                    obj.setColumnValue(3,rowx,"Total Deduction : ",obj.TEXT_LEFT);
                    obj.setColumnValue(4,rowx,""+Formater.formatNumber(totalDeduction,frmCurrency),obj.TEXT_RIGHT);
                    obj.setColumnValue(5,rowx,"",obj.TEXT_RIGHT);
                    
                    //rowx++;
                    
                    rowx++;
                    obj.setColumnValue(0,rowx,"Total Take Home Pay : ",obj.TEXT_LEFT);
                    obj.setColumnValue(1,rowx,""+Formater.formatNumber((totalBenefit-totalDeduction),frmCurrency),obj.TEXT_RIGHT);
                    obj.setColumnValue(2,rowx,"",obj.TEXT_RIGHT);
                    obj.setColumnValue(3,rowx,"",obj.TEXT_LEFT);
                    obj.setColumnValue(4,rowx,"",obj.TEXT_RIGHT);
                    obj.setColumnValue(5,rowx,"",obj.TEXT_RIGHT);
                    
                    // rowx,
                    obj = getFooter(obj, fullName,presenceDay,paidLeave,absent,unpaidLeave,late,bankId,bankAccountNr);
                    // rowx = rowx + 15;
                }
                System.out.println(">>>>>>>>>>rowx las : "+rowx);
            }
            
            // rowx++;
            //rowxz = rowx;
        }
        return obj;
    }
    
    // int rowx,
    //static long curPerId = 0;
    public static DSJ_PrintObj mainHeader(DSJ_PrintObj obj, long periodId, String fullName, String  department, String  position, String division, String section, String salaryLevel, Date commDate, String strStatusApp, String strStatusPaid, String empNum) {
        //int[] col = {36,119,44};
        //int[] col = {48, 4, 48};
        obj.setCpiIndex(DSJ_PrintObj.PRINTER_12_CPI);
        int[] col = {60,90};
        /*
        Period hrPeriode = null;
        if( (curPerId!=periodId) || (hrPeriode==null) ){
           curPerId =  periodId;
           try{
            Period hrPeriod = PstPeriod.fetchExc(periodId);
           } catch(Exception exc){ 
           }           
        } 
          */              
        obj.newColumn(2, "", col);
        
        //Period pr = new Period();
        //update by satrya 2013-02-12
         PayPeriod pr = new PayPeriod();
        String periodName ="";
        try{
            pr = PstPayPeriod.fetchExc(periodId);
            //update by satrya 2013-02-012
            // pr = PstPeriod.fetchExc(periodId);
            periodName = pr.getPeriod();
        }
        catch(Exception e){
        }
        /*
        Calendar newCalendar = Calendar.getInstance();
        newCalendar.setTime(new Date());
        Date dStartDateOfMonth = newCalendar.getTime();
        Date dEndDateOfMonth = new Date(dStartDateOfMonth.getYear(),dStartDateOfMonth.getMonth(),newCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));        
        String slipDate = ""+Formater.formatTimeLocale(dEndDateOfMonth,"dd-MMM-yyyy");
        */
        String slipDate = ""+Formater.formatTimeLocale(pr.getPaySlipDate(),"dd-MMM-yyyy");
        
        //ROW 1
        obj.setColumnValue(0, rowx, "Payroll Slip : "+periodName, obj.TEXT_LEFT);
        //obj.setColumnValue(1, rowx, "", obj.TEXT_CENTER);
        obj.setColumnValue(1, rowx, "Denpasar,"+slipDate, obj.TEXT_RIGHT);
        //int[] colx = {17,25,4,20,20};
        int[] colx = {17,33,50,50};
        //obj.newColumn(5, "", colx);
        obj.newColumn(4, "", colx);
        /*rowx++;
        //ROW 2
        obj.setColumnValue(0, rowx, "Status : ", obj.TEXT_RIGHT);
        obj.setColumnValue(1, rowx, ""+strStatusApp+"/"+strStatusPaid, obj.TEXT_LEFT);
        obj.setColumnValue(2, rowx, "", obj.TEXT_CENTER);
        obj.setColumnValue(3, rowx, "Salary Level : ", obj.TEXT_RIGHT);
        obj.setColumnValue(4, rowx, ""+salaryLevel, obj.TEXT_LEFT);*/
        
        rowx++;
        //ROW 3
        obj.setColumnValue(0, rowx, "Emp.Num/Name : ", obj.TEXT_RIGHT);
        obj.setColumnValue(1, rowx, ""+empNum+"/"+fullName, obj.TEXT_LEFT);
        //obj.setColumnValue(2, rowx, "", obj.TEXT_CENTER);
        obj.setColumnValue(2, rowx, "Section : ", obj.TEXT_RIGHT);
        obj.setColumnValue(3, rowx, ""+section, obj.TEXT_LEFT);
        
        rowx++;
        //ROW 4
        //obj.setColumnValue(0, rowx, " SUB SATKER : " + satker.getKdGiat(), obj.TEXT_LEFT);
        obj.setColumnValue(0, rowx, "Dept : ", obj.TEXT_RIGHT);
        obj.setColumnValue(1, rowx, ""+department, obj.TEXT_LEFT);
        //obj.setColumnValue(2, rowx, "", obj.TEXT_CENTER);
        obj.setColumnValue(2, rowx, "Comm.Date : ", obj.TEXT_RIGHT);
        obj.setColumnValue(3, rowx, ""+Formater.formatTimeLocale(commDate,"dd-MMM-yyyy"), obj.TEXT_LEFT);
        
        rowx++;
        //ROW 5
        obj.setCpiIndex(DSJ_PrintObj.PRINTER_12_CPI);
        obj.addRptLine("=");
        /*int[] colt = {100};
        obj.newColumn(1,"",colt);
        obj.setColumnValue(0,rowx,"===============================================================================",obj.TEXT_LEFT);
*/

        rowx++;
        
        //int[] cols = {5,29,7,59,38,11,13};
        //int[] cols = {5,29,7,65,38,11,7};
        //int[] cols = {20,15,5,20,15,5};
        int[] cols = {30,30,20,30,20,20};
        obj.newColumn(6,"",cols);
        
        //ROW 1
        obj.setColumnValue(0,rowx,"BENEFIT",obj.TEXT_LEFT);
        obj.setColumnValue(1,rowx,"VALUE",obj.TEXT_RIGHT);
        obj.setColumnValue(2,rowx,"",obj.TEXT_LEFT);
        obj.setColumnValue(3,rowx,"DEDUCTION",obj.TEXT_LEFT);
        obj.setColumnValue(4,rowx,"VALUE",obj.TEXT_RIGHT);
        obj.setColumnValue(5,rowx,"",obj.TEXT_RIGHT);
        
        rowx++;
        //ROW 9
        //obj.addRptLine("=");
        /*int[] coly = {100};
        obj.newColumn(1,"",coly);
        obj.setColumnValue(0,rowx,"===============================================================================",obj.TEXT_LEFT);
*/
        obj.setCpiIndex(DSJ_PrintObj.PRINTER_12_CPI);
        obj.addRptLine("=");

        return obj;
    }
    //obj = getFooter(obj, fullName, rowx, presenceDay,paidLeave,absent,unpaidLeave,late);
    // int rowx,
    public static DSJ_PrintObj getFooter(DSJ_PrintObj obj, String  fullName, double presenceDay,double paidLeave,double absent, double unpaidLeave,double late, long bankId, String bankAccountNr){
        
        PayBanks payBanks = new PayBanks();
        String bankName = "";
        String bankBranch ="";
        try{
            payBanks = PstPayBanks.fetchExc(bankId);
            bankName = payBanks.getBankName();
            bankBranch = payBanks.getBankBranch();
        }catch(Exception e){
            
        }
        //int[] colz = {5,36,11,11,11,7,8,11,7,7,8,8,8,11,13};
        //int[] colz =   {5,36,11,11,11,7,8,17,7,7,8,8,8,11,7};
       // int[] colz =   {50};
       // int[] colz =   {80};
        //obj.newColumn(1,"",colz);
        
        rowx++;
        obj.setCpiIndex(DSJ_PrintObj.PRINTER_12_CPI);
        obj.addRptLine("=");
       // obj.setColumnValue(0,rowx,"===============================================================================",obj.TEXT_LEFT);
       
       /* rowx++;
        obj.setColumnValue(0,rowx,"Presence Summary :",obj.TEXT_LEFT);*/
        
        /*rowx++;
        obj.setColumnValue(0,rowx,"Presence="+presenceDay+",Paid Leave="+paidLeave+",Absent="+absent+",Unpaid Leave="+unpaidLeave+",Late="+late+",Total="+((presenceDay+paidLeave)-(absent+unpaidLeave+late)),obj.TEXT_LEFT);*/
        
        //ROW 8
        
        //int[] coly =   {30,5,20,5,20};
        int[] coly =   {50,10,40,10,40};

        obj.newColumn(5,"",coly);
        
       /* rowx++;
        obj.setColumnValue(0,rowx,"",obj.TEXT_LEFT);
        obj.setColumnValue(1,rowx,"",obj.TEXT_LEFT);
        obj.setColumnValue(2,rowx,"",obj.TEXT_LEFT);
        obj.setColumnValue(3,rowx,"",obj.TEXT_LEFT);
        obj.setColumnValue(4,rowx,"",obj.TEXT_LEFT);*/
        //ROW 8
        
        rowx++;
        obj.setColumnValue(0,rowx,"Transfer To :",obj.TEXT_LEFT);
        obj.setColumnValue(1,rowx,"",obj.TEXT_LEFT);
        obj.setColumnValue(2,rowx,"Prepared By : ",obj.TEXT_CENTER);
        obj.setColumnValue(3,rowx,"",obj.TEXT_LEFT);
        obj.setColumnValue(4,rowx,"Received By :",obj.TEXT_CENTER);
        
        rowx++;
        obj.setColumnValue(0,rowx,""+bankName+" - "+bankBranch,obj.TEXT_LEFT);
        obj.setColumnValue(1,rowx,"",obj.TEXT_LEFT);
        obj.setColumnValue(2,rowx,"",obj.TEXT_LEFT);
        obj.setColumnValue(3,rowx,"",obj.TEXT_LEFT);
        obj.setColumnValue(4,rowx,"",obj.TEXT_LEFT);
        
        // rowx++;
        
        
        rowx++;
        obj.setColumnValue(0,rowx,"No : "+bankAccountNr,obj.TEXT_LEFT);
        obj.setColumnValue(1,rowx,"",obj.TEXT_LEFT);
        obj.setColumnValue(2,rowx,"",obj.TEXT_LEFT);
        obj.setColumnValue(3,rowx,"",obj.TEXT_LEFT);
        obj.setColumnValue(4,rowx,"",obj.TEXT_LEFT);
        
        rowx++;
        obj.setColumnValue(0,rowx," A/N :"+fullName,obj.TEXT_LEFT);
        obj.setColumnValue(1,rowx,"",obj.TEXT_LEFT);
        obj.setColumnValue(2,rowx,"",obj.TEXT_LEFT);
        obj.setColumnValue(3,rowx,"",obj.TEXT_LEFT);
        obj.setColumnValue(4,rowx,"",obj.TEXT_LEFT);
        
        rowx++;
        obj.setColumnValue(0,rowx,"",obj.TEXT_LEFT);
        obj.setColumnValue(1,rowx,"",obj.TEXT_LEFT);
        obj.setColumnValue(2,rowx,""+PstSystemProperty.getValueByName("ACC_NAME").toUpperCase()+"",obj.TEXT_CENTER);
        obj.setColumnValue(3,rowx,"",obj.TEXT_CENTER);
        obj.setColumnValue(4,rowx,""+fullName,obj.TEXT_CENTER);
        
        rowx++;
        obj.setColumnValue(0,rowx,"",obj.TEXT_LEFT);
        obj.setColumnValue(1,rowx,"",obj.TEXT_LEFT);
        obj.setColumnValue(2,rowx,"(HRD)",obj.TEXT_CENTER);
        obj.setColumnValue(3,rowx,"",obj.TEXT_LEFT);
        obj.setColumnValue(4,rowx,"(Employee)",obj.TEXT_CENTER);
        
        rowx++;
        //ROW 9
        /*int[] colt = {100};
        obj.newColumn(1,"",colt);
        obj.setColumnValue(0,rowx,"===============================================================================",obj.TEXT_LEFT);
        //obj.setColumnValue(0,rowx,"============================================================================================================================",obj.TEXT_LEFT);
*/
        obj.setCpiIndex(DSJ_PrintObj.PRINTER_12_CPI);
        obj.addRptLine("=");
        
        
        //
        
        return obj;
    }
    
    
    
    public static DSJ_PrintObj printDftGajiTopDown(DSJ_PrintObj obj,Vector listCetakGaji,long periodeId,long employeeId, int keyPeriod,long payGroupId){
        int halaman  = 1;
        //System.out.println("listCetakGaji="+listCetakGaji.size());
        int maxListCetakGj  = listCetakGaji.size();
        if(listCetakGaji!=null&&maxListCetakGj>0){
            // int rowy = 0;
            int pageLngth = 0;
            int count = 1;
            int countPage = 0;
            boolean bool = false;
            int noUrut = 0;
            
            int listGajiCount = 0;
            boolean checkHal = false;
            boolean checkGol = false;
            boolean checkSatker = false;
            
            for(int j=0;j<maxListCetakGj;j++){
                System.out.println(">>>>>>>>>>rowx : "+rowx);
                int lengthHeader = 15;
                int start = 0;
                int end = 0;
                int val = 0;
                int valComp = 0;
                long oidSatker = 0;
                long oidPeriode = 0;
                Vector temp = (Vector)listCetakGaji.get(j);
                PayComponent payComponent= (PayComponent)temp.get(0);
                SalaryLevelDetail salaryLevelDetail = (SalaryLevelDetail) temp.get(1);
                PaySlipComp paySlipComp = (PaySlipComp) temp.get(2);
                noUrut = noUrut+1;
                
                String paySlipId = ""+paySlipComp.getOID();
                String salaryLevel = ""+salaryLevelDetail.getLevelCode();
                int compType = payComponent.getCompType();
                PaySlip paySlip= new PaySlip();
                employeeId = 0;
                String department = "";
                String position ="";
                String section ="";
                String division ="";
                Date commDate = new Date();
                int statusApp = 0;
                int statusPaid = 0;
                String frmCurrency = "#,###";
                double presenceDay=0;
                double paidLeave = 0;
                double absent = 0;
                double unpaidLeave = 0;
                double late = 0;
                long bankId = 0;
                double totalBenefit = PstSalaryLevelDetail.getSumBenefitDoub(PstSalaryLevelDetail.YES_TAKE,salaryLevel,PstPayComponent.TYPE_BENEFIT,paySlipComp.getOID(),keyPeriod,payGroupId);
               double totalDeduction = PstSalaryLevelDetail.getSumBenefitDoub(PstSalaryLevelDetail.YES_TAKE,salaryLevel,PstPayComponent.TYPE_DEDUCTION,paySlipComp.getOID(),keyPeriod,payGroupId);
               
                try{
                    paySlip = PstPaySlip.fetchExc(paySlipComp.getOID());
                    employeeId = paySlip.getEmployeeId();
                    //department = paySlip.getDepartment();
                    position = paySlip.getPosition();
                    division = paySlip.getDivision();
                    //section = paySlip.getSection();
                    commDate = paySlip.getCommencDate();
                    statusApp = paySlip.getStatus();
                    statusPaid = paySlip.getPaidStatus();
                    presenceDay = paySlip.getDayPresent();
                    paidLeave = paySlip.getDayPaidLv();
                    absent = paySlip.getDayAbsent();
                    unpaidLeave = paySlip.getDayUnpaidLv();
                    late = paySlip.getDayLate();
                    bankId = paySlip.getBankId();
                    
                }catch(Exception e){
                    System.out.println("err:"+e.toString());
                }
                
                String strStatusApp = "";
                String strStatusPaid = "";
              //if(statusApp==PstPaySlip.YES_APPROVE){
                    strStatusApp = "APPROVED";
               /* }
                else{
                    strStatusApp = "DRAFT";
                }*/
               //if(statusPaid==PstPaySlip.YES_PAID){ 
                    strStatusPaid = "PAID";  
              /*  }
                else{
                    strStatusPaid = "NOT PAID";
                }*/
                // ambil karywan yang memiliki employee id diatas
                Employee employee = new Employee();
                String fullName = "";
                String empNum = "";
                long departmentId= 0;
                long sectionId = 0;
                try{
                    employee = PstEmployee.fetchExc(employeeId);
                    fullName = employee.getFullName();
                    empNum = employee.getEmployeeNum();
                    departmentId = employee.getDepartmentId();
                    sectionId = employee.getSectionId();
                    // System.out.println(">>>>>>>>>>getEmployeeId : "+employeeId);
                }catch(Exception e){
                    System.out.println("err:"+e.toString());
                }
                
                //ambil nama department dari departmentId yang didapat
                Department dept = new Department();
                try{
                    dept = PstDepartment.fetchExc(departmentId);
                    department = dept.getDepartment();
                    // System.out.println(">>>>>>>>>>getEmployeeId : "+employeeId);
                }catch(Exception e){
                    System.out.println("err: get Department"+e.toString());
                }
                
                //ambil nama section dari sectionId yang didapat
                Section sect = new Section();
                try{
                    sect = PstSection.fetchExc(sectionId);
                    section = sect.getSection();
                    // System.out.println(">>>>>>>>>>getEmployeeId : "+employeeId);
                }catch(Exception e){
                    System.out.println("err: get Section"+e.toString());
                }
                
                //ambil no Rekenning employee :
                String bankAccountNr = PstPayEmpLevel.getBankAccNr(employeeId);
                /*boolean ketPanjang = true;
                String ket2 ="";*/
                //todo
                
                if((j==0)||(checkHal))/*||(checkSatker)||(checkGol))*/{
                    //rowx++;
                    obj = mainHeader(obj, periodeId, fullName,department,position,division,section,salaryLevel,commDate,strStatusApp,strStatusPaid, empNum);
                    checkHal = false;
                    checkGol = false;
                    checkSatker = false;
                    //rowx = rowx + (6*(j+1));
                    // rowx = rowx + 6;
                    // rowy = rowx;
                }
                
                //int[] coly = {5,29,7,11,11,11,7,8,11,7,7,8,8,8,11,13};
                //1,2, 3,4, 5, 6, 7,8,9
                //int[] coly = {5,29,7,11,11,11,7,8,17,7,7,8,8,8,11,7};
                if(j==0){
                    //int[] coly = {20,15,5,20,15,5};
                    int[] coly = {30,30,20,30,20,20};
                    obj.newColumn(6,"",coly);
                }
                
                rowx++;
                //ROW 8
                /*
                if(salaryLevelDetail.getLevelCode().equals(""+PstSystemProperty.getValueByName("SALARY_LEVEL4")) ||salaryLevelDetail.getLevelCode().equals(""+PstSystemProperty.getValueByName("SALARY_LEVEL5")) || salaryLevelDetail.getLevelCode().equals(""+PstSystemProperty.getValueByName("SALARY_LEVEL6")) || salaryLevelDetail.getLevelCode().equals(""+PstSystemProperty.getValueByName("SALARY_STAFF_TRAINING"))){
                   if(payComponent.getCompCode().equals(""+PstSystemProperty.getValueByName("COMP_CODE1"))){
                    String compName = PstPayComponent.getComponentName(PstSystemProperty.getValueByName("COMP_CODE1"));
                    obj.setColumnValue(0,rowx,""+compName,obj.TEXT_LEFT);
                    obj.setColumnValue(1,rowx,""+Formater.formatNumber(Double.parseDouble(PstSystemProperty.getValueByName("GP_VALUE")),frmCurrency),obj.TEXT_RIGHT);
                    obj.setColumnValue(2,rowx,"",obj.TEXT_RIGHT);
                    obj.setColumnValue(3,rowx,"",obj.TEXT_RIGHT);
                    obj.setColumnValue(4,rowx,"",obj.TEXT_RIGHT);
                    obj.setColumnValue(5,rowx,"",obj.TEXT_RIGHT);
                   }
                   else if(payComponent.getCompCode().equals(""+PstSystemProperty.getValueByName("COMP_CODE2"))){
                     String compName = PstPayComponent.getComponentName(PstSystemProperty.getValueByName("COMP_CODE2"));
                     obj.setColumnValue(0,rowx,""+compName,obj.TEXT_LEFT);
                     obj.setColumnValue(1,rowx,""+Formater.formatNumber(Double.parseDouble(PstSystemProperty.getValueByName("TJ.TTP_VALUE")),frmCurrency),obj.TEXT_RIGHT);
                     obj.setColumnValue(2,rowx,"",obj.TEXT_RIGHT);
                     obj.setColumnValue(3,rowx,"",obj.TEXT_RIGHT);
                     obj.setColumnValue(4,rowx,"",obj.TEXT_RIGHT);
                     obj.setColumnValue(5,rowx,"",obj.TEXT_RIGHT);
                   }
                   totalBenefit = Double.parseDouble(PstSystemProperty.getValueByName("GP_VALUE")) + Double.parseDouble(PstSystemProperty.getValueByName("TJ.TTP_VALUE")) ;
                   totalDeduction = 0;
                     
                }else*/{
                   if(compType==PstPayComponent.TYPE_BENEFIT){
                    obj.setColumnValue(0,rowx,""+payComponent.getCompName(),obj.TEXT_LEFT);
                    obj.setColumnValue(1,rowx,""+Formater.formatNumber(paySlipComp.getCompValue(),frmCurrency),obj.TEXT_RIGHT);
                    obj.setColumnValue(2,rowx,"",obj.TEXT_RIGHT);
                    obj.setColumnValue(3,rowx,"",obj.TEXT_RIGHT);
                    obj.setColumnValue(4,rowx,"",obj.TEXT_RIGHT);
                    obj.setColumnValue(5,rowx,"",obj.TEXT_RIGHT);
                    }
                    else{
                        obj.setColumnValue(0,rowx,"",obj.TEXT_LEFT);
                        obj.setColumnValue(1,rowx,"",obj.TEXT_RIGHT);
                        obj.setColumnValue(2,rowx,"",obj.TEXT_RIGHT);
                        obj.setColumnValue(3,rowx,""+payComponent.getCompName(),obj.TEXT_LEFT);
                        obj.setColumnValue(4,rowx,""+Formater.formatNumber(paySlipComp.getCompValue(),frmCurrency),obj.TEXT_RIGHT);
                        obj.setColumnValue(5,rowx,"",obj.TEXT_RIGHT);
                    }
                }
                
                if(j==(maxListCetakGj-1)){
                    rowx++;
                    /*int[] colt = {100};
                    obj.newColumn(1,"",colt);
                    obj.setColumnValue(0,rowx,"===============================================================================",obj.TEXT_LEFT);
                    */
                     obj.setCpiIndex(DSJ_PrintObj.PRINTER_12_CPI);
                     obj.addRptLine("=");
                    
                    
                    rowx++;
                    //int[] colz = {20,15,5,20,15,5};
                    int[] colz = {30,30,20,30,20,20};
                    obj.newColumn(6,"",colz);
                    obj.setColumnValue(0,rowx,"Total Benefit : ",obj.TEXT_LEFT);
                    obj.setColumnValue(1,rowx,""+Formater.formatNumber(totalBenefit,frmCurrency),obj.TEXT_RIGHT);
                    obj.setColumnValue(2,rowx,"",obj.TEXT_RIGHT);
                    obj.setColumnValue(3,rowx,"Total Deduction : ",obj.TEXT_LEFT);
                    obj.setColumnValue(4,rowx,""+Formater.formatNumber(totalDeduction,frmCurrency),obj.TEXT_RIGHT);
                    obj.setColumnValue(5,rowx,"",obj.TEXT_RIGHT);
                    
                    //rowx++;
                    
                    rowx++;
                    obj.setColumnValue(0,rowx,"Total Take Home Pay : ",obj.TEXT_LEFT);
                    obj.setColumnValue(1,rowx,""+Formater.formatNumber((totalBenefit-totalDeduction),frmCurrency),obj.TEXT_RIGHT);
                    obj.setColumnValue(2,rowx,"",obj.TEXT_RIGHT);
                    obj.setColumnValue(3,rowx,"",obj.TEXT_LEFT);
                    obj.setColumnValue(4,rowx,"",obj.TEXT_RIGHT);
                    obj.setColumnValue(5,rowx,"",obj.TEXT_RIGHT);
                    
                    // rowx,
                    obj = getFooter(obj, fullName,presenceDay,paidLeave,absent,unpaidLeave,late,bankId,bankAccountNr);
                    // rowx = rowx + 15;
                }
                System.out.println(">>>>>>>>>>rowx las : "+rowx);
            }
            
            // rowx++;
            //rowxz = rowx;
        }
        return obj;
    }
    
    
}
