/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.utility.machine;
import com.dimata.system.entity.PstSystemProperty;
import java.util.Scanner;
/**
 *
 * @author ktanjana  2011
 */
public class AttMachineTrans {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        String TKEEPING_TYPE = PstSystemProperty.getValueByName("TIMEKEEPING_TYPE");
        String strStatusSvrmgrMachine = "";
        String strButtonStatusMachine = "";
        String strStatusSvrmgrPresence = "";
        String strButtonStatusPresence = "";
        String strStatusPortStatus = "";
        String strButtonPortStatus = "";
        String sTime01 = "";
        String sTime02 = "";    /*GEDE_20110901_01 {*/
        TransManager svrmgrMachine = TransManager.getInstance(false);  /* }*/


        TransManager.addTxtProcessClass("com.dimata.harisma.utility.machine.NitgendbacTransfer");
        TransManager.addTxtProcessClass("com.dimata.harisma.utility.machine.Intelliscanv1Transfer");
        TransManager.addTxtProcessClass("com.dimata.harisma.utility.machine.Att2000Transfer");
        String iCommandMachine = "1";  // 1=start , 2=stop , 3 = exit

        do{
        if (TransManager.getTxtProcessClassSize() > 0) {
            for (int i = 0; i < TransManager.getTxtProcessClassSize(); i++) {
                System.out.println("Machine: "+TransManager.getTxtProcessClass(i)+
                        " Data :"+svrmgrMachine.getProcentTransfer(i)+" of "+svrmgrMachine.getTotalRecord(i));
            }
        }


            if (svrmgrMachine.getStatus()) {
                strStatusSvrmgrMachine = "Run";
                strButtonStatusMachine = "Stop";
            } else {
                strStatusSvrmgrMachine = "Stop";
                strButtonStatusMachine = "Run";
            }

         
            if (iCommandMachine != null) {
                if (iCommandMachine.equalsIgnoreCase("1")) {
                    try {

                        svrmgrMachine.startTransfer();

                    } catch (Exception e) {
                        System.out.println("\t Exception svrmgrMachine.startTransfer() = " + e);
                    }
                } else if (iCommandMachine.equalsIgnoreCase("2")) {
                    try {
                        svrmgrMachine.stopTransfer();
                    } catch (Exception e) {
                        System.out.println("\t Exception svrmgrMachine.stopWatcherMachine() = " + e);
                    }
                }
            }
            Scanner input=new Scanner(System.in); // Decl. & init. a Scanner.
            System.out.println("Mesin status="+strStatusSvrmgrMachine);
            System.out.println("1=start , 2=stop , 3 = exit : ");
            iCommandMachine=input.next();

        } while(iCommandMachine.compareTo("3")!=0);
        
    }
}
