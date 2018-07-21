/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.masterdata;

import java.util.Hashtable;
import java.util.Vector;

/**
 *
 * @author Devin
 */
public class TblCekErrorPublicLeave{
   private Hashtable cekErrorCategoryEmp = new  Hashtable();
   private Hashtable cekErrorSdhAdaDetail = new  Hashtable();
   private Hashtable cekErrorGagalTersimpan = new  Hashtable();

    /**
     * @return the cekErrorCategoryEmp
     */
    public String getCekErrorCategoryEmp(long oidEmployee) {
        String Err="";
        if(cekErrorCategoryEmp !=null && cekErrorCategoryEmp.containsKey(oidEmployee)){
            Err=(String)cekErrorCategoryEmp.get(oidEmployee);
        }
        return Err;
        //return cekErrorCategoryEmp;
    }

    /**
     * @param cekErrorCategoryEmp the cekErrorCategoryEmp to set
     */
    public void addCekErrorCategoryEmp(long oidEmployee,String msqError) {
         this.cekErrorCategoryEmp.put(oidEmployee, msqError);
       // this.cekErrorCategoryEmp = cekErrorCategoryEmp;
    }

    /**
     * @return the cekErrorSdhAdaDetail
     */
    public String getCekErrorSdhAdaDetail(long EmpId) {
        String Error ="";
        if(cekErrorSdhAdaDetail !=null && cekErrorSdhAdaDetail.containsKey(EmpId)){
            Error=(String)cekErrorSdhAdaDetail.get(EmpId);
        }
        return Error;
        //return cekErrorSdhAdaDetail;
    }

    /**
     * @param cekErrorSdhAdaDetail the cekErrorSdhAdaDetail to set
     */
    public void addCekErrorSdhAdaDetail(long oidEmp,String Error) {
        this.cekErrorSdhAdaDetail.put(oidEmp, Error);
        //this.cekErrorSdhAdaDetail = cekErrorSdhAdaDetail;
    }

    /**
     * @return the cekErrorGagalTersimpan
     */
    public String getCekErrorGagalTersimpan(long empId) {
        String Error="";
        if(cekErrorGagalTersimpan !=null && cekErrorGagalTersimpan.containsKey(empId)){
            Error = (String)cekErrorGagalTersimpan.get(empId);
        }
        return Error;
        //return cekErrorGagalTersimpan;
    }

    /**
     * @param cekErrorGagalTersimpan the cekErrorGagalTersimpan to set
     */
    public void addCekErrorGagalTersimpan(long oidEmp, String Error) {
        this.cekErrorGagalTersimpan.put(oidEmp, Error);
        //this.cekErrorGagalTersimpan = cekErrorGagalTersimpan;
    }
}
