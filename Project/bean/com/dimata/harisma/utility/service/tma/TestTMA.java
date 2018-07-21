/*
 * TestTMA.java --- Only for testing
 * @author  gedhy
 * @edited by rusdianta 
 * Created on December 16, 2004, 8:10 AM
 */

package com.dimata.harisma.utility.service.tma;

public class TestTMA {
    
    /** Creates a new instance of TestTMA */
    public TestTMA() {
    }
    
    /**
     * @param args the command line arguments
     */
    
    public static void main(String[] args) {
        
        // Employee ID list ......
        String[] employeeId = {
            "13005773",
            "13004963",
            "13004371",
            "13005297",
            "13002516",
            "13005930",
            "13005285",
            "13004768",
            "13005737",
            "13004782"            
        };        
        
        String[] employeePIN = {
            "773",
            "963",
            "371",
            "297",
            "516",
            "930",
            "285",
            "768",
            "737",
            "782"            
        };

        AccessTMA objAccessTMA = new AccessTMA();   
                
        /*
        // test upload data to machine
        System.out.println("Start upload UserID and PIN");               
        for(int i=0; i<arrEmpId.length; i++)
        {            
            // hapus semua user dari mesin
            java.util.Vector vectResult = objAccessTMA.executeCommand(objAccessTMA.DELETE, "01", arrEmpId[i]);
            
            // upload userId dan PIN ke mesin
            vectResult = objAccessTMA.executeCommand(objAccessTMA.UPLOAD, "01", arrEmpId[i]+"\\"+arrEmpPin[i]+"\\");            
            
            System.out.println("\tProses delete dan upload ke : " + i);
        }        
        System.out.println("End upload UserID and PIN");
        */
        
        /*java.util.Vector vectResult = objAccessTMA.executeCommand(objAccessTMA.VALIDATION, "01", "1");
        if(vectResult!=null && vectResult.size()>0)
        {
            System.out.println("vectResult : " + vectResult);
        }    
        
        /* --- start of modified by rusdianta --- */
        
        String USED_PORT = "/dev/ttyS0";//COM1";
        String MACHINE_NO = "01";
        CanteenTMAAccess TMA2 = new CanteenTMAAccess(USED_PORT);    
        java.util.Vector results = TMA2.executeCommand(TMA2.CHECK_MACHINE, MACHINE_NO, "");
        System.out.println("RESULT OF CHECK_MACHINE = " + results);
        results = TMA2.executeCommand(TMA2.VALIDATION, MACHINE_NO, "1");
        System.out.println("RESULT OF VALIDATION = " + results);
        results = TMA2.executeCommand(TMA2.DOWNLOAD, MACHINE_NO, "");
        System.out.println("RESULT OF DOWNLOAD = " + results);
        
        
        //vectResult = objAccessTMA.executeCommand(objAccessTMA.DOWNLOAD, "01", "");
    }
    
}
