/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dimata.harisma.utility.machine;


import com.dimata.harisma.entity.attendance.MachineTransaction;
import com.dimata.harisma.entity.attendance.PstMachineTransaction;
//import com.dimata.harisma.entity.canteen.CanteenVisitation;
//import com.dimata.harisma.entity.canteen.PstCanteenVisitation;
//import com.dimata.harisma.entity.employee.Employee;
//import com.dimata.harisma.entity.employee.PstEmployee;
import com.dimata.harisma.session.attendance.SessMachineTransaction;
//import com.dimata.harisma.utility.machine.dcanteen.SaverDataCanteen;
import com.dimata.system.entity.system.PstSystemProperty;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.Vector;
/**
 *
 * @author Administrator
 */
public class TDX_2000 implements I_Machine{
    
    OpenPorts reader = OpenPorts.getInstant();
    MachineConf machineConf;
    public static boolean CHECK_SWEEP_TIME = false;   
    public static int IGNORE_TIME = 15 * 60 * 1000;          /* --- in milli seconds --- */
    
    public boolean initMachine(MachineConf machineConf) {
        this.machineConf = machineConf;
        boolean isConn = false;
        if(machineConf.getMachineCommMode()==COMM_MODE_COM){ //COM MODE
            try{
                reader.setMachinePortName(machineConf.getMachinePort());
                if(machineConf.getMachineUse()==USE_ABSENCE){
                    CHECK_SWEEP_TIME = Integer.parseInt(PstSystemProperty.getValueByName("CANTEEN_CHECK_SWEEP_TIME")) > 0;
                    int ignoreTime = Integer.parseInt(PstSystemProperty.getValueByName("CANTEEN_IGNORE_SWEEP_TIME")) * 60 * 1000;
                    if (ignoreTime > 0){
                        IGNORE_TIME = ignoreTime;} 
                }
            }catch(Exception ex){}
        }else{ //TCP/IP MODE
        }
        return isConn;
    }

    public boolean sendCommand(String str) {
        boolean isSend = false;
        if(machineConf.getMachineCommMode()==COMM_MODE_COM){
            OpenPorts.execute(str); isSend=true;
        }else{
            isSend=true;
        }
        return isSend;
    }
    
    public String getResult(){
        String strReturm  = "";
        if(machineConf.getMachineCommMode()==COMM_MODE_COM){
            strReturm = reader.getResult();
        }else{
            
        }
        return strReturm;
    }

    public void closeConnection() {
        if(machineConf.getMachineCommMode()==COMM_MODE_COM){
            OpenPorts.disconnect();
        }else{
            
        }
    }

    public boolean processCheckMachine() {
        boolean isSuccess = false;
        System.out.println(":::::: TEST MACHINE :::::: [TDX 2000]");
        if(machineConf.getMachineCommMode()==COMM_MODE_COM){
            try{
              //  OpenPorts.disconnect();
                sendCommand(cmdCheckMachineId());
            }catch(Exception e){
                System.err.println("\t[MACHINE ERROR] CHECK MACHINE error : reader.execute(\"*" + machineConf.getMachineNumber() + "X\") : " + e.toString());
            }

            Thread tCek = new Thread();
            tCek.start();
            try{ 
                tCek.sleep(INTERVAL);   
            }catch(Exception e){
                System.err.println("\tSet interval for Thread : " + e.toString());                    
            }
            String strResult = "";
            try{
                strResult = reader.getResult().substring(2,4);
                System.out.println("CONF "+machineConf.getMachineNumber()+" ::::: READER "+reader.getResult().substring(2,4));
            }catch(Exception ex){}
            if(machineConf.getMachineNumber().equals(strResult)){
                isSuccess = true;
                System.out.println("-----MACHINE "+machineConf.getMachineNumber()+" :::: OK");
            }else{
                System.out.println("-----MACHINE "+machineConf.getMachineNumber()+" :::: NOT CONNECTED :::: "+strResult);
            }
            tCek.stop();
        }else{
            
        }        
        return isSuccess;
    }

    public boolean processReset(){
        boolean isSuccess = false;
        if(machineConf.getMachineCommMode()==COMM_MODE_COM){
            if(processCheckMachine()){
                lockMachine(true);
                processClearMessage();
                Thread tReseta = new Thread();
                tReseta.start();
                isSuccess = true;
                try{ 
                    tReseta.sleep(INTERVAL); 
                }catch(Exception e){
                    System.err.println("\tSet interval for Thread : " + e.toString());                                                            
                }
                tReseta.stop();
                sendCommand(cmdClearTransaction());
                Thread tReset = new Thread();
                tReset.start();
                isSuccess = true;
                try{ 
                    tReset.sleep(INTERVAL); 
                }catch(Exception e){
                    System.err.println("\tSet interval for Thread : " + e.toString());                                                            
                }
                tReset.stop();
                sendCommand(cmdClearUser());
                Thread tReset2 = new Thread();
                tReset2.start();
                try{ 
                    tReset2.sleep(INTERVAL); 
                }catch(Exception e){
                    System.err.println("\tSet interval for Thread : " + e.toString());                                                            
                }
                tReset2.stop();
                sendCommand(cmdClearMessage());
                Thread tReset2a = new Thread();
                tReset2a.start();
                try{ 
                    tReset2a.sleep(INTERVAL); 
                }catch(Exception e){
                    System.err.println("\tSet interval for Thread : " + e.toString());                                                            
                }
                tReset2a.stop();
                sendCommand(cmdRestart());
                Thread tReset3 = new Thread();
                tReset3.start();
                try{ 
                    tReset3.sleep(INTERVAL); 
                }catch(Exception e){
                    System.err.println("\tSet interval for Thread : " + e.toString());                                                            
                }
                tReset3.stop();
                
                lockMachine(false);
            }
        }else{
            
        }
        return isSuccess;
    }

    public boolean processValidation(int intMode) {
        boolean isSuccess = false;
        if(machineConf.getMachineCommMode()==COMM_MODE_COM){
            String strParameter = "0";
            if(intMode==MODE_VALADATION_USE_PIN){
                strParameter = "1";
            }
            sendCommand(cmdValidation(strParameter));
            Thread tVal = new Thread();
            tVal.start();
            try{ 
                tVal.sleep(INTERVAL); 
            }catch (Exception e){
                System.err.println("\tSet interval for Thread : " + e.toString());                                                            
            }
            tVal.stop();
        }else{
            
        }
            
        return isSuccess;
    }

    public Vector processDownloadTransaction(){
        Vector vTemp = new Vector(1,1);
        Vector vTransaction = new Vector(1,1);
        int inValidData = 0;
        String resultRead = ""; //"OFF"untuk test saja
        String resultRemove = "";
        if(machineConf.getMachineCommMode()==COMM_MODE_COM){
            int dataCount = 0;
            long t1 = System.currentTimeMillis();
            System.out.println("--------------------------------------------------------------------------------");
            System.out.println("-------------------------------DOWNLOAD START-----------------------------------");
            System.out.println("--------------------------------------------------------------------------------");
            System.out.println("--------------------------------Finger Print------------------------------------");
            if (resultRead.equals("OFF") == false){
                lockMachine(true);
                try{
                    //System.out.println("===================================>1");
                    while ( !(resultRemove.equals("FFOVER")) ){
                        sendCommand(cmdReadTransaction());
                        Thread tRead = new Thread();
                        tRead.start();
                        Thread.sleep(INTERVAL);
                        resultRead = reader.getResult();
                        //System.out.println("\t[Access Machine] " + resultRead + " >> " + resultRead);

                        /* --- variable declaration for presence data will inserted
                        add by edhy  --- */
                        boolean isSaveData = false;
                        boolean isIsValid = false;
                        
                        try{
                            if(resultRead.compareToIgnoreCase("FFI") >= 9){
                                isIsValid = true;
                            }else{
                                /*System.out.println("------------->2 Not Valid Data");
                                //Untuk data yang tidak valid
                                //Tampung data pada code employee
                                MachineTransaction objTransaction = new MachineTransaction();
                                objTransaction = getTransaction("FFI01TA08010101010000");
                                // vResult.addElement(resultRead);
                                objTransaction.setCardId(resultRead);
                                SessMachineTransaction.downloadTransaction(objTransaction);
                                vTransaction.add(objTransaction); */
                            }
                        }catch(Exception ex){
                            /*System.out.println("------------->3 Not Valid Data");
                            //Untuk data yang tidak valid
                            //Tampung data pada code employee
                            MachineTransaction objTransaction = new MachineTransaction();
                            objTransaction = getTransaction("FFI01TA08010101010000");
                            // vResult.addElement(resultRead);
                            objTransaction.setCardId(resultRead);
                            SessMachineTransaction.downloadTransaction(objTransaction);
                            vTransaction.add(objTransaction); */
                        }
                        
                        if ( (!resultRead.equalsIgnoreCase("FFOVER")) && (isIsValid)) 
                        {            
                            System.out.println("\t[Access Machine] MACHINE-" +machineConf.getMachineNumber()+ " transaction #" + dataCount + " = " + resultRead);
                            MachineTransaction objTransaction = new MachineTransaction();
                            objTransaction = getTransaction(resultRead);
                            // vResult.addElement(resultRead);
                            isSaveData = SessMachineTransaction.downloadTransaction(objTransaction);
                            vTransaction.add(objTransaction);
                        }                            
                        tRead.stop();
                        // jika presence berhasil di insert, maka hapus record tersebut dari mesin
                        if(isSaveData)
                        {
                            sendCommand(cmdRemoveTransaction());
                            Thread tRemove = new Thread();
                            tRemove.start();
                            Thread.sleep(INTERVAL);
                            resultRemove = reader.getResult();                                                                                                              
                            tRemove.stop();
                        }else{
                            if(isIsValid){
                                lockMachine(false);
                                resultRemove = "FFOVER";
                                System.out.println("\t ERROR DATABASE : CAN'T SAVE DATA TO DATABASE AT TABLE hr_machine_transaction, PLEASE CHECK DATABASE ");
                            }else{
                                inValidData += 1;
                                sendCommand(cmdRemoveTransaction());
                                Thread tRemove = new Thread();
                                tRemove.start();
                                Thread.sleep(INTERVAL);
                                resultRemove = reader.getResult();                                                                                                              
                                tRemove.stop();
                            }
                        }
                        
                        // jika pada saat di read sudah FFOVER, maka berarti data sudah habis
                        if( resultRead.equalsIgnoreCase("FFOVER") )
                        {
                            lockMachine(false);
                            resultRemove = "FFOVER";
                        }
                        dataCount += 1;
                    }
                }
                catch (Exception e) {
                    lockMachine(false);
                    resultRemove = "FFOVER";
                    System.out.println("\t[Watcher Machine] inside -while- exception : " + e);
                }
                lockMachine(false);
            }
            long t2 = System.currentTimeMillis();
            vTemp.add(vTransaction);
            vTemp.add(""+inValidData);
                    
            System.out.println("\t[Access Machine] Download time for Machine " + machineConf.getMachineNumber() + ": " + (t2-t1) + "ms");
            System.out.println("-------------------------------Download Finish-----------------------------------");            
            
            System.out.println("Start analyst transaction : ");
            System.out.println("-------------------------------");
            SessMachineTransaction.analistPresentAll();
            
        }else{}
        return vTemp;
    }

    public Vector processList() {
        int dataCount = 0;
        Vector vUser = new Vector();
        if(machineConf.getMachineCommMode()==COMM_MODE_COM){
            if(processCheckMachine()){
                lockMachine(true);
                String resultRead = "";
                try{ 
                    sendCommand(cmdPointerUserToTop()); 
                }catch (Exception e){ 
                    System.out.println("[ERROR] GO to top pointer user : "+e.toString()); 
                }
                Thread tInit = new Thread();
                tInit.start();

                try{ 
                    tInit.sleep(INTERVAL); 
                }catch (Exception e){
                    System.err.println("\tSet interval for Thread : " + e.toString());                                                                                
                }
                resultRead = reader.getResult();       
                if (resultRead.equals("FFDONE")){
                    while (!(resultRead.equals("FFOVER"))){
                        dataCount++;
                        try { 
                            sendCommand(cmdReadUser()); 
                        }catch (Exception e){ 
                            System.out.println("[ERROR] get user from machine : "+e.toString()); 
                        }
                        Thread tRead = new Thread();
                        tRead.start();

                        try { 
                            tRead.sleep(INTERVAL); 
                        }catch (Exception e){
                            System.err.println("\tSet interval for Thread : " + e.toString());
                        }
                        resultRead = reader.getResult();
                        System.out.println("\t[Access Machine] " + dataCount + "::::" + resultRead);
                        tRead.stop();
                        vUser.add(createEmployeeObj(resultRead));
                    }             
                }
                lockMachine(false);
            }
        }else{}
        return vUser;
    }

    public boolean processUpload(EmployeeUp employeeUp) {
        boolean isSuccess = false;
        if(machineConf.getMachineCommMode()==COMM_MODE_COM){
            if(processCheckMachine()){
            //   lockMachine(true);
               // processDelete(employeeUp);
                boolean isAvaileable = false;
                try { 
                    String strBercode = new String();
                    strBercode = employeeUp.getBarcode().trim();
                    sendCommand(cmdCekUser(strBercode));
                    System.out.println(cmdCekUser(strBercode));
                }catch(Exception ex){} 
                
                Thread tCek = new Thread();
                tCek.start();
                try{ 
                    tCek.sleep(INTERVAL);   
                }catch(Exception e){
                    System.err.println("\tSet interval for Thread : " + e.toString());                    
                }
                
                String resultRead = reader.getResult();
                try{resultRead.length();}catch(Exception ex){resultRead="";}
                System.out.println("::::::::::::"+reader.getResult());
                if(!resultRead.equals("FFERROR")&&resultRead.length()>8){
                   isAvaileable = true;
                }
              //  System.out.println("::::: EMP_DATA ::: "+(createEmployeeParam(employeeUp)));
                if(isAvaileable){
                    System.out.println("------------------- UPDATE");
                    try { 
                        sendCommand(cmdUpdateUser(createEmployeeParam(employeeUp)));
                    }catch(Exception ex){}
                }else{
                    System.out.println("------------------- NEW DATA");
                    sendCommand(cmdUploadUser(createEmployeeParam(employeeUp)));
                }
                tCek.stop();
                isSuccess = true;
                Thread tRemove = new Thread();
                tRemove.start();
                try { 
                    tRemove.sleep(INTERVAL); 
                }catch (Exception e){
                    System.err.println("\tSet interval for Thread : " + e.toString());
                }
                tRemove.stop();
             //   lockMachine(false);
            }
        }else{}
        return isSuccess;
    }

    public boolean processDelete(EmployeeUp employeeUp) {
        boolean isSuccess = false;
        if(machineConf.getMachineCommMode()==COMM_MODE_COM){
          //  sendCommand(cmdLockMachine(true));
            sendCommand(cmdRemoveUser(employeeUp.getBarcode()));
            isSuccess = true;
            Thread tDel4 = new Thread();
            tDel4.start();
            try { 
                Thread.sleep(INTERVAL); 
            }catch (Exception e){
                System.err.println("\tSet interval for Thread : " + e.toString());
            }
            tDel4.stop();
         //   sendCommand(cmdLockMachine(false));
        }else{}
        return isSuccess;                
    }

    public Date processGetTime() {
        Date machineDate = new Date();
        if(machineConf.getMachineCommMode()==COMM_MODE_COM){
            try {
                String resultRead = "";
                sendCommand(cmdGetMachineDate());
                Thread tGetTime = new Thread();
                tGetTime.start();
                try {
                    tGetTime.sleep(INTERVAL);
                } catch (Exception e) {
                    System.err.println("\t[ERROR]Set interval for Thread : " + e.toString());
                }
                resultRead = reader.getResult();
                tGetTime.stop();
                machineDate = getMachineDate(resultRead, 0);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }else{}
        return machineDate;
    }

    public boolean processSetTime(Date date) {
        boolean isSuccess = false;
        if(machineConf.getMachineCommMode()==COMM_MODE_COM){
            sendCommand(cmdSetMachineDate(createMachineDateParam(date)));
            isSuccess = true;
            Thread tSetTime = new Thread();
            tSetTime.start();
            try { 
                tSetTime.sleep(INTERVAL); 
            }catch (Exception e){
                System.err.println("[ERROR]\tSet interval for Thread : " + e.toString());
            }
            tSetTime.stop();
        }else{}
        return isSuccess;
    }

    public boolean processSendMessage(EmployeeUp employeeUp, String message) {
        boolean isSuccess = false;
        if(machineConf.getMachineCommMode()==COMM_MODE_COM){
            if(processCheckMachine()&&message.length()>0){
                //sendCommand(cmdLockMachine(true));
                if(message.length()<32){
                    for(int i=0;i<32-message.length();i++){
                        if(i<=32-message.length()){
                            message += "_";
                        }else{
                            message += " ";//
                        }
                    }
                }
                sendCommand(cmdSendMessage(createMessageParam(employeeUp, message)));
                isSuccess = true;
                Thread tSetTime = new Thread();
                tSetTime.start();
                try { 
                    tSetTime.sleep(INTERVAL); 
                }catch (Exception e){
                    System.err.println("[ERROR]\tSet interval for Thread : " + e.toString());
                }
                tSetTime.stop();
               // sendCommand(cmdLockMachine(false));
            }
        }else{}
        return isSuccess;
    }

    public boolean processClearMessage() {
        boolean isSuccess = false;
        if(machineConf.getMachineCommMode()==COMM_MODE_COM){
         //   sendCommand(cmdLockMachine(true));
            sendCommand(cmdClearMessage());
            isSuccess = true;
            Thread tSetTime = new Thread();
            tSetTime.start();
            try { 
                tSetTime.sleep(INTERVAL); 
            }catch (Exception e){
                System.err.println("[ERROR]\tSet interval for Thread : " + e.toString());
            }
            tSetTime.stop();
         //   sendCommand(cmdLockMachine(false));
        }else{}
        return isSuccess;
    }
    
    /**
     * Untu mengunci dan membua mesin
     */
    public boolean processOpenComm(int commStatus) {
        boolean isSuccess = false;
        switch(commStatus){
            case COMM_STATUS_OPEN : 
                isSuccess = lockMachine(true);
                break;
            case COMM_STATUS_CLOSE : 
                isSuccess = lockMachine(false);
                break;
        }
        return isSuccess;
    }
    
    public boolean lockMachine(boolean isLock){
        boolean isSuccess = false;
        if(machineConf.getMachineCommMode()==COMM_MODE_COM){
         //   sendCommand(cmdLockMachine(true));
            sendCommand(cmdLockMachine(isLock));
            isSuccess = true;
            Thread tSetTime = new Thread();
            tSetTime.start();
            try { 
                tSetTime.sleep(INTERVAL); 
            }catch (Exception e){
                System.err.println("[ERROR]\tSet interval for Thread : " + e.toString());
            }
            tSetTime.stop();
         //   sendCommand(cmdLockMachine(false));
        }else{}
        return isSuccess;
    }

    /**********************************
     * Command yang dijalankan mesin
     **********************************/
    /**
     * Cek koneksi ke mesin
     */
    private String cmdCheckMachineId(){
        String command = "";
        command = "*"+machineConf.getMachineNumber()+"X";
        return command;
    }
    /**
     * set validation
     * V = 1, BOLEH MENGGUNAKAN PIN
     * V = 0, TIDAK BOLEH MENGGUNAKAN PIN
     */
    private String cmdValidation(String strParameter){
        String command = "";
        command = "*" +machineConf.getMachineNumber()+ "S24"+strParameter;
        return command;
    }
    /**
     * Reset transaction and user
     */
    private String cmdReset(){
        String command = "";
        command = "*"+machineConf.getMachineNumber()+"RST";
        return command;
    }
    /**
     * Restart machine configure
     */
    private String cmdRestart(){
        String command = "";
        command = "*"+machineConf.getMachineNumber()+"RESTART";
        return command;
    }
    /**
     * Membaca transaction
     */
    private String cmdReadTransaction(){
        String command = "";
        command = "*"+machineConf.getMachineNumber()+"READ";
        return command;
    }
    /**
     * Membersihkan transaction pada pointer
     */
    private String cmdRemoveTransaction(){
        String command = "";
        command = "*"+machineConf.getMachineNumber()+"REMOVE";
        return command;
    }
    /**
     * Membersihkan semua transaction
     */
    private String cmdClearTransaction(){
        String command = "";
        command = "*"+machineConf.getMachineNumber()+"CLEAR";
        return command;
    }
    /**
     * Mengeset pointer to top transaction
     */
    private String cmdPointerTransactionToTop(){
        String command = "";
        command = "*"+machineConf.getMachineNumber()+"TOP";
        return command;
    }
    /**
     * Membaca jumlah transaction
     */
    private String cmdReadTransactionNumber(){
        String command = "";
        command = "*"+machineConf.getMachineNumber()+"NOT";
        return command;
    }
    /**
     * Membaca user data pada mesin
     */
    private String cmdReadUser(){
        String command = "";
        command = "*"+machineConf.getMachineNumber()+"OU";
        return command;
    }
    /**
     * Menginsertkan user ke mesin
     */
    private String cmdUploadUser(String strParameter){
        String command = "";
        command = "*"+machineConf.getMachineNumber()+"OI"+strParameter;
        return command;
    }
    /**
     * Menegcek user id
     */
    private String cmdCekUser(String strParameter){
        String command = "";
        command = "*"+machineConf.getMachineNumber()+"OV"+strParameter;
        return command;
    }
    /**
     * Mengupdate user
     */
    private String cmdUpdateUser(String strParameter){
        String command = "";
        command = "*"+machineConf.getMachineNumber()+"UPOI"+strParameter;
        return command;
    }
    /**
     * Membersihkan data user pada mesin sesuai dengan id-nya
     * strParameter : U..U (U..U, USER ID)
     */
    private String cmdRemoveUser(String strParameter){
        String command = "";
        command = "*"+machineConf.getMachineNumber()+"OD"+strParameter;
        return command;
    }
    /**
     * Membersihkan semua data user pada mesin
     */
    private String cmdClearUser(){
        String command = "";
        command = "*"+machineConf.getMachineNumber()+"OC";
        return command;
    }
    /**
     * Mengeset pointer to top User
     */
    private String cmdReadUserNumber(){
        String command = "";
        command = "*"+machineConf.getMachineNumber()+"NOU";
        return command;
    }
     /**
     * Mengeset pointer to top User
     */
    private String cmdPointerUserToTop(){
        String command = "";
        command = "*"+machineConf.getMachineNumber()+"OT";
        return command;
    }
    /**
     * Mengunci mesin ketika proses download transaction, jika true maka akan di-lock
     * jika false maka mesin tidak akan di-lock
     */
    private String cmdLockMachine(boolean isLock){
        String command = "";
        if(isLock){
            command = "*"+machineConf.getMachineNumber()+"LOCKS";
        }else{
            command = "*"+machineConf.getMachineNumber()+"LOCKR";
        }
        return command;
    }
    /**
     * Mengirimkan pesan ke mesin per employee
     * strParameter : U..U\1C..C
     *                U..U, USER ID Barcode MAKS 10 DIGIT, '1'=FLAG 1
                      C..C, MESSAGES UNTUK USER MAKS 32 DIGIT  
     */
    private String cmdSendMessage(String strParameter){
        String command = "";
        command = "*"+machineConf.getMachineNumber()+"MSGS"+strParameter;
        return command;
    }
    /**
     * Membaca pesan pada mesin yang di kirimkan pada employee
     */
    private String cmdReadMessage(){
        String command = "";
        command = "*"+machineConf.getMachineNumber()+"MSGR";
        return command;
    }
    /**
     * Mengeset pointer to top message
     */
    private String cmdPointerMessageToTop(){
        String command = "";
        command = "*"+machineConf.getMachineNumber()+"MT";
        return command;
    }
    /**
     * Membersihkan semua pesan
     */
    private String cmdClearMessage(){
        String command = "";
        command = "*"+machineConf.getMachineNumber()+"CLMS";
        return command;
    }
    /**
     * Membaca Tanggal pada mesin
     */
    private String cmdGetMachineDate(){
        String command = "";
        command = "*"+machineConf.getMachineNumber()+"TR";
        return command;
    }
    /**
     * Mengeset Tanggal pada mesin
     */
    private String cmdSetMachineDate(String strParameter){
        String command = "";
        command = "*"+machineConf.getMachineNumber()+"TW" + strParameter;
        return command;
    }
    
    
    /**
     * Fungsi tambahan dalam memprose data
     */
    
    /**
     * Memecah data transaction yang diperoleh dari mesin
     */
    private MachineTransaction getTransaction(String output){
        MachineTransaction objTransaction = new MachineTransaction();
        //FFI01TA08010101010000
        if(output.length()>0){
            String tNumber = "";
            String tFunction = "";
            String tBarcode = "";
            Date dateTransaction = new Date();
            try{
                tNumber = output.substring(3, 5);
                tFunction = output.substring(6, 7);
                tBarcode = output.substring(17, output.length());
                dateTransaction = getMachineDate(output,7);
            }catch(Exception ex){
                try {
                    tNumber = output;
                    output = "FFI01TA08010101010000";
                    tFunction = output.substring(6, 7);
                    tBarcode = output.substring(17, output.length());
                    dateTransaction = getMachineDate(output, 7);
                } catch (Exception ex1) {
                    ex1.printStackTrace();
                }
            }
            System.out.println(" ::: dateTransaction : "+dateTransaction);
            
        //    System.out.println(" ::: BARCODE : "+tBarcode);
        //    System.out.println(" ::: FUCTION : "+tFunction);
        //    System.out.println(" ::: DATE    : "+dateTransaction);
            
            objTransaction.setStation(machineConf.getMachineNumber());
            objTransaction.setCardId(tBarcode);
            objTransaction.setMode(tFunction);
            objTransaction.setDateTransaction(dateTransaction);
            objTransaction.setPosted(PstMachineTransaction.POSTED_STATUS_OPEN);
        }
        return objTransaction;
    }
    
    /**
     * Membaca output tanggal dari mesin
     */
    private Date getMachineDate(String strDateOut, int intStartRead) throws Exception{
        if(intStartRead<0){intStartRead = 0;}
        int i=intStartRead;
        Date date = new Date();
        if(strDateOut.length()>0){
            int tYear = Integer.parseInt(strDateOut.substring(i+0,i+2)) + 100;
            int tMonth = Integer.parseInt(strDateOut.substring(i+2,i+4)) - 1;
            int tDate = Integer.parseInt(strDateOut.substring(i+4,i+6));
            int tHour = Integer.parseInt(strDateOut.substring(i+6,i+8));
            int tMin = Integer.parseInt(strDateOut.substring(i+8,i+10));
            return new Date(tYear, tMonth, tDate, tHour, tMin);
        }
        return date;
    }
    
    
    private Date getMachineDate(String str_time) throws Exception{
        
        Date date = new Date();
        if(str_time.length()>0){
            
           int tYear = Integer.parseInt(str_time.substring(0,4));
           int tMonth = Integer.parseInt(str_time.substring(5,7));
           int tDate = Integer.parseInt(str_time.substring(8,10));
           int tHour = Integer.parseInt(str_time.substring(11,13));
           int tMin = Integer.parseInt(str_time.substring(14,16));
           return new Date(tYear, tMonth, tDate, tHour, tMin);
           
        }
        return date;
    }
    
    /**
     * Membuat parameter tanggal
     */
    private String createMachineDateParam(Date date){
        String strDate = "";
        if(date==null){date = new Date();}
        String tYear = String.valueOf(date.getYear()-100);
        String tMonth = String.valueOf(date.getMonth()+1);
        String tDate = String.valueOf(date.getDate());
        String tHour = String.valueOf(date.getHours());
        String tMin = String.valueOf(date.getMinutes());
        strDate = tYear+tMonth+tDate+tHour+tMin;
        return strDate;
    }
    
    /**
     * Membuat string parameter untuk upload data employee
     */
    private String createEmployeeParam(EmployeeUp objEmployeeUp){
     /** Ini settingan untuk finger print, untuk yang lain silahkan di overwrite
     * U..U\P..P\MXSSNNNNNNNNNN
     * U..U, USER ID Barcode MAKS 10 DIGIT
     * P..P, USER hex ID 6 DIGIT atau finger ID.  Urutkan dari terkecil hingga terbesar saat upload
     * M, 'C'=Card only, ' F'=Finger, 'S'=Supervisor  
     * X=Flag -> '1' ada SMS, '0' tnp SMS. S=Verifikasi akses -> bila tidak digunakan ganti dengan kode '@'  
     * N..N, Nama User maks 10 digit
     */
        String param = "";
        if(objEmployeeUp.getBarcode().equals("")){
            objEmployeeUp.setBarcode("000000");
        }
        String fingerid = objEmployeeUp.getUserHexId();
      //  System.out.println(fingerid+" LENGTH "+(6-fingerid.toString().length()));
      //  System.out.println("::::: "+Integer.toHexString(10));
        String strFingerId = fingerid.toString();
        for(int iCo=0;iCo<(6-fingerid.length());iCo++){
            strFingerId = "0"+strFingerId;
            //System.out.println(fingerid);
        }
        param =  objEmployeeUp.getBarcode()
                +"\\"+strFingerId
                +"\\"+"F@"
                +"@@"+objEmployeeUp.getName();
        return param;
    }
    /**
     * Membuat string parameter untuk upload data employee
     */
    private EmployeeUp createEmployeeObj(String strUser){
     /** Ini settingan untuk finger print, untuk yang lain silahkan di overwrite
     * U..U\P..P\MXSSNNNNNNNNNN
     * U..U, USER ID Barcode MAKS 10 DIGIT
     * P..P, USER hex ID 6 DIGIT atau finger ID.  Urutkan dari terkecil hingga terbesar saat upload
     * M, 'C'=Card only, ' F'=Finger, 'S'=Supervisor  
     * X=Flag -> '1' ada SMS, '0' tnp SMS. S=Verifikasi akses -> bila tidak digunakan ganti dengan kode '@'  
     * N..N, Nama User maks 10 digit
     */
        String delim = "\\";
        EmployeeUp objEmployeeUp = new EmployeeUp();
        StringTokenizer strTokenizer = new StringTokenizer(strUser, delim);
        if(strTokenizer.hasMoreTokens()){
            String split_1 = strTokenizer.nextToken();
            String split_2 = strTokenizer.nextToken();
            String split_3 = strTokenizer.nextToken();
            String empBarcode = split_1;
            String empHexId   = strTokenizer.nextToken();
            String empCode    = split_3.substring(0, 1);
            String empName    = split_3.substring(4, split_3.length()-1);
            objEmployeeUp.setBarcode(empBarcode);
            objEmployeeUp.setCode(empCode);
            objEmployeeUp.setUserHexId(empHexId);
            objEmployeeUp.setName(empName);
        }
        return objEmployeeUp;
    }
    
    /**
     * Membuat string parameter untuk upload message
     */
    private String createMessageParam(EmployeeUp objEmployeeUp,String strMessage){
     /** Ini settingan untuk finger print, untuk yang lain silahkan di over)write
     * U..U\1C..C
     * U..U, USER ID Barcode MAKS 10 DIGIT
     * 1 = flag
     * C..C, Pesan yang dikirimkan
     */
        String spliter = "\\1";
        String param = "";
        param = objEmployeeUp.getBarcode()+spliter+strMessage;
        return param;
    }

    public String getMachineNumber() {
        return machineConf.getMachineNumber();
    }
 
    public static void main(String[] arg){
            
            MachineTransaction mcTran = new MachineTransaction();
            mcTran.setCardId("123456");
            mcTran.setDateTransaction(new Date());
            mcTran.setMode("A");
            mcTran.setStation("01");
            mcTran.setPosted(0);
            SessMachineTransaction.downloadTransaction(mcTran);
                    
            SessMachineTransaction.analistPresentAll();
        }

    
}