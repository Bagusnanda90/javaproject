/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dimata.harisma.utility.machine.dcanteen;


import com.dimata.harisma.entity.attendance.MachineTransaction;
import com.dimata.harisma.entity.canteen.CanteenVisitation;
import com.dimata.harisma.entity.canteen.PstCanteenVisitation;
import com.dimata.harisma.entity.employee.PstEmployee;
import com.dimata.qdep.db.DBException;

/**
 *
 * @author Administrator
 */
public class SaverDataCanteen {
    
    /**
     * Menyimpan data transaksi yang diperoleh dari mesin
     */
    public static synchronized boolean saveTransaction(MachineTransaction transaction) throws DBException{
        boolean isSuccess = false;
        if(transaction != null){
            CanteenVisitation canteenVisitation = new CanteenVisitation();
            int visitationStatus = 0;
            long oidVisitation = 0;
            long empOid = PstEmployee.getEmployeeByBarcode(transaction.getCardId());
            if ((empOid == 0) ) { // try get employee id by employee number;
                    String barcode00 = "000000"+transaction.getCardId();
                        if (barcode00.length() > 6){
                          barcode00 =  barcode00.substring((barcode00.length()-6), barcode00.length());
                        }
                        empOid = PstEmployee.getEmployeeByBarcode(barcode00);
                    
                }
            if (empOid == 0) { // try get employee id by employee number;
                    empOid = PstEmployee.getEmployeeIdByNum(transaction.getCardId());
                }
                
            switch (transaction.getMode().charAt(0)){
                case 'A' : canteenVisitation.setStatus(0); visitationStatus = 0; break;
                case 'B' : canteenVisitation.setStatus(1); visitationStatus = 1; break;
                case 'C' : canteenVisitation.setStatus(2); visitationStatus = 2; break;
                case 'D' : canteenVisitation.setStatus(3); visitationStatus = 3; break;
                case 'E' : canteenVisitation.setStatus(4); visitationStatus = 4; break;
                case 'F' : canteenVisitation.setStatus(5); visitationStatus = 5; break;
                default : break;
            }                                

            // inserting downloaded data to database                                   
            canteenVisitation.setEmployeeId(empOid);
            canteenVisitation.setVisitationTime(transaction.getDateTransaction());
            canteenVisitation.setTransferred(0);
            canteenVisitation.setNumOfVisitation(1);
            oidVisitation = PstCanteenVisitation.insertExc(canteenVisitation);    
            isSuccess = true;
        }
        return isSuccess;
    }
    //update by satrya 19-04-2012
        public static synchronized boolean saveTransaction(long  empOid ,MachineTransaction transaction) throws DBException{
        boolean isSuccess = false;
        if(transaction != null ){
            CanteenVisitation canteenVisitation = new CanteenVisitation();
            int visitationStatus = 0;
            long oidVisitation = 0;
            //long empOid = PstEmployee.getEmployeeByBarcode(transaction.getCardId());
            //priska 20150522
           if(transaction != null && transaction.getMode() != null && transaction.getMode().length() > 0){
         
            switch (transaction.getMode().charAt(0)){
                case 'A' : canteenVisitation.setStatus(0); visitationStatus = 0; break;
                case 'B' : canteenVisitation.setStatus(1); visitationStatus = 1; break;
                case 'C' : canteenVisitation.setStatus(2); visitationStatus = 2; break;
                case 'D' : canteenVisitation.setStatus(3); visitationStatus = 3; break;
                case 'E' : canteenVisitation.setStatus(4); visitationStatus = 4; break;
                case 'F' : canteenVisitation.setStatus(5); visitationStatus = 5; break;
                default : canteenVisitation.setStatus(0); visitationStatus = 0; break;
            }                                
           }
            // inserting downloaded data to database                                   
            canteenVisitation.setEmployeeId(empOid);
            canteenVisitation.setVisitationTime(transaction.getDateTransaction());
            canteenVisitation.setTransferred(0);
            canteenVisitation.setNumOfVisitation(1);
            try{
            oidVisitation = PstCanteenVisitation.insertExc(canteenVisitation);    
            isSuccess = true;
            } catch(Exception exc){
                System.out.println(exc);
            }            
        }
        return isSuccess;
    }
}
