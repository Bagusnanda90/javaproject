/*
 * TransferPresenceFromMdfText.java
 *
 * Created on November 24, 2006, 11:04 AM
 */

package com.dimata.harisma.utility.odbc;

/**
 *
 * @author  yunny
 */

import java.util.*;
import java.sql.*;
import java.io.*;
import java.util.Date;
import com.dimata.util.blob.*;
import com.dimata.qdep.db.*;
import com.dimata.harisma.entity.attendance.*; 
import com.dimata.harisma.entity.masterdata.*; 
import com.dimata.harisma.entity.service.*; 
import com.dimata.harisma.entity.employee.*; 
import com.dimata.system.entity.system.*;
import com.dimata.harisma.utility.service.parser.CardneticTextIntimas;
import com.dimata.util.*;


public class TransferPresenceFromMdfText extends DBConnection   
{    
    //public static final String tableName1 = "HR_EMP_SCHEDULE_HISTORY";
    //public static final String tableName2 = "HR_SCHEDULE_SYMBOL";
    public static final int ACTION_OUT_HOME = 0;
    public static final int ACTION_IN = 1;            
    public static final int ACTION_OUT_ON_DUTY = 2;
    public static final int ACTION_IN_LUNCH = 3;
    public static final int ACTION_IN_BREAK = 4;
    public static final int ACTION_IN_CALLBACK = 5;
   
    
    public static final String actionNames[] = 
    {
        "Out Home",
        "In",
        "Out On Duty",
        "In Lunch",
        "In Break",
        "In Callback"
    };
    
    public String dbDriver = "sun.jdbc.odbc.JdbcOdbcDriver";
    public String dbUrl = "jdbc:odbc:harismaodbc"; 

    
    /*
     * proses :
     * 1. ambil system property
     * 2. rename file XXX dengan XXX-1, agar file XXX menjadi hilang dan bisa dicreate oleh 
     * mesin timekeeping, kalau dalam proses grabing data ada yang posting
     * 3. file yang di download di backup sampai 5 file
     * 4. ambil data dari file XXX-1 untuk di proses ke presence
     */
    public Vector retrieveData()
    {
        String fileDrive = PstSystemProperty.getValueByName("CARDNETIC_TEXT_FILE_DRIVE");
        String filePath = PstSystemProperty.getValueByName("CARDNETIC_TEXT_FILE_PATH");
        String fileName = PstSystemProperty.getValueByName("CARDNETIC_TEXT_FILE_NAME");
        
        System.out.println("fileDrive : "+fileDrive);
        System.out.println("filePath : "+filePath);
        System.out.println("fileName : "+fileName);
        
        String fileExt = "txt";
        StringTokenizer strTok = new StringTokenizer(fileName, ".");
        while(strTok.hasMoreTokens()){
            fileName = strTok.nextToken();
            fileExt = strTok.nextToken();
            
        }
        Connection dbConn = null;
        Statement stmt = null; 
        Connection dbConn1 = null;
        Statement stmt1 = null; 
        Vector result = new Vector(1,1);
        
        //TextLoader tl = new TextLoader();
        //String str = tl.getFileString(filePath);
        Date backupNow = new Date();
        int yBackup = backupNow.getYear()+1900;
        int mBackup = backupNow.getMonth()+1;
        int dBackup = backupNow.getDate();
        int hBackup = backupNow.getHours();
        int mnBackup = backupNow.getMinutes();
        int sBackup = backupNow.getSeconds();
        System.out.println("sekarang" +yBackup+mBackup+dBackup);
        com.dimata.util.blob.File filex = new com.dimata.util.blob.File();
        //untuk sementara tidak dilakukan penghapusan terhadap file log
        //jadi fileName+"-5 direname jadi file backup
        filex.renameFile(fileDrive, filePath, fileName+"-5."+fileExt, fileName+"_"+yBackup +mBackup +dBackup +hBackup +mnBackup +sBackup+"."+fileExt);
        System.out.println("rename file 5 jadi file "+fileName+"_"+yBackup +mBackup +dBackup +hBackup +mnBackup +sBackup+"."+fileExt);
        /*filex.deleteFile(fileDrive, filePath, fileName+"-5."+fileExt);
        System.out.println("deleting file 5");*/
        filex.renameFile(fileDrive, filePath, fileName+"-4."+fileExt, fileName+"-5."+fileExt);
        System.out.println("rename file 4");
        filex.renameFile(fileDrive, filePath, fileName+"-3."+fileExt, fileName+"-4."+fileExt);
        System.out.println("rename file 3");
        filex.renameFile(fileDrive, filePath, fileName+"-2."+fileExt, fileName+"-3."+fileExt);
        System.out.println("rename file 2");
        filex.renameFile(fileDrive, filePath, fileName+"-1."+fileExt, fileName+"-2."+fileExt);
        System.out.println("rename file 1");
        filex.renameFile(fileDrive, filePath, fileName+"."+fileExt, fileName+"-1."+fileExt);
        System.out.println("rename file 0");

        String str = filex.getFileString(fileDrive+":"+System.getProperty("file.separator")+filePath+System.getProperty("file.separator")+fileName+"-1."+fileExt);
        System.out.println("str::::::"+str);
        System.out.println(str);
        StringReader sr = new StringReader(str);
        LineNumberReader ln = new LineNumberReader(sr);
        Vector strBuffer = new Vector(1,1);
        boolean stop = false;
        int i=0;
        while(!stop){
            try{
                String s = ln.readLine();
                
                System.out.println(s);
                
                if(s==null || s.length()<1){
                    stop = true;
                }
                else{
                    //dapatkan nilai id,date dari file log.txt
                    CardneticTextIntimas ct = new CardneticTextIntimas(s);
                    Vector vectTemp = new Vector(1,1);
                    String id=ct.getSwappingId().trim();
                    long idEmp=0;
                    vectTemp.add(ct.getSwappingDate(id));
                    vectTemp.add("");
                    vectTemp.add(""+ct.getSwappingId());
                    vectTemp.add(""+ct.getSwappingTime(id));
                    //vectTemp.add(""+ct.getSwappingType());
                    String tglText = (String)ct.getSwappingDate(id).substring(8,10);
                    
                    //untuk id yang jumlah 1
                    if(id.trim().length()==3){
                     //lakukan kondisi dengan token
                      int token=0;
                      StringTokenizer tokId1 = new StringTokenizer(id);
                       while(tokId1.hasMoreTokens()){
                         String tokenId1=(String)tokId1.nextToken();
                         token++;
                       }
                     
                         if(token>1){
                             String idToken = id.substring(0, 1);
                             id=idToken;
                             idEmp = PstEmployee.getEmployeeByBarcode(id);
                             //System.out.println("EmployeeIdkuAtas"+idEmp);
                             
                         }
                         else{
                             idEmp = PstEmployee.getEmployeeByBarcode(id); 
                            // System.out.println("EmployeeIdku"+idEmp);
                             
                         }  
                  
                    }
                    else{
                         idEmp = PstEmployee.getEmployeeByBarcode(id); 
                         //System.out.println("EmployeeIdkuElse"+idEmp);
                    }
                    //untuk status
                      /* Date datecoba = new Date();
                       datecoba = (Date)vectTemp.get(0);*/
                   
                    //Date dateTxt = Formater.formatDate(String.valueOf(vectTemp.get(0)),"yyyy-mm-dd");
                    //System.out.print("tgl di file txt:::::"+dateTxt);
                    Date newDate=new Date();
                    int newY = newDate.getYear()+1900;
                    int newmm = newDate.getMonth()+1;
                    int newmmPrev =newmm-1;
                    if(newmmPrev==0){
                        newmmPrev=newmm+12;
                    }
                    int newdd = newDate.getDate();
                               long periodId = PstPeriod.getPeriodIdBySelectedDateString(String.valueOf(vectTemp.get(0)));
                               //int categoryType = PstPeriod.getPeriodIdBySelectedDateString(String.valueOf(vectTemp.get(0)));
                               String sql = "SELECT S."+PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_TIME_IN]+
                                             ", S."+PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_TIME_OUT]+
                                             ", S."+PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SYMBOL]+
                                             ", CAT."+PstScheduleCategory.fieldNames[PstScheduleCategory.FLD_CATEGORY_TYPE]+
                                             " FROM " +PstEmpSchedule.TBL_HR_EMP_SCHEDULE+" H"+
                                             ", "+PstScheduleSymbol.TBL_HR_SCHEDULE_SYMBOL+" S"+
                                             ", "+PstScheduleCategory.TBL_HR_SCHEDULE_CATEGORY+" CAT"+
                                             " WHERE"+
                                             " H."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID]+
                                             "="+idEmp+ " AND"+
                                             " H."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_PERIOD_ID]+
                                             "="+periodId+
                                             " AND CAT."+PstScheduleCategory.fieldNames[PstScheduleCategory.FLD_SCHEDULE_CATEGORY_ID]+
                                             "= S."+PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_CATEGORY_ID];
                               
                                             if(tglText.equals("01")){
                                              
                                               System.out.println("\n\nin newdd ==  1 : date "+newDate);  
                                             
                                               Date checkDate = (Date)newDate.clone();
                                               checkDate.setDate(checkDate.getDate()-1);
                                               newdd = checkDate.getDate();
                                               /*GregorianCalendar calendar = new GregorianCalendar(2006,11,20);
                                               calendar.get.getMaximum(0);*/
                                               sql=sql+" AND"+
                                                       " H."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D1]+
                                                       "="+
                                                       "S."+PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]+
                                                       " OR"+
                                                       " (H."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND1]+
                                                       "="+
                                                       "S."+PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]+
                                                       " AND"+
                                                       " H."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID]+
                                                       " ="+idEmp+
                                                       ")";
                                          
                                             }
                                            else if(tglText.equals("02")){
                                                  sql=sql+" AND"+
                                                          " H."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2]+
                                                          "="+
                                                          "S."+PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]+
                                                          " OR"+
                                                          " (H."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND2]+
                                                          "="+
                                                          "S."+PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]+
                                                          " AND"+
                                                          " H."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID]+
                                                          " ="+idEmp+
                                                          ")";       
                                             }
                                            else if(tglText.equals("03")){
                                                  sql=sql+" AND"+
                                                          " H."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D3]+
                                                          "="+
                                                          "S."+PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]+
                                                          " OR"+
                                                          " (H."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND3]+
                                                          "="+
                                                          "S."+PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]+
                                                          " AND"+
                                                          " H."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID]+
                                                          " ="+idEmp+
                                                          ")";
                            
                                             }
                                            else if(tglText.equals("04")){
                                                  sql=sql+" AND"+
                                                          " H."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D4]+
                                                          "="+
                                                          "S."+PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]+
                                                          " OR"+
                                                          " (H."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND4]+
                                                          "="+
                                                          "S."+PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]+
                                                          " AND"+
                                                          " H."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID]+
                                                          " ="+idEmp+
                                                          ")";

                                              }
                                            else if(tglText.equals("05")){
                                                  sql=sql+" AND"+
                                                          " H."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D5]+
                                                          "="+
                                                          "S."+PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]+
                                                          " OR"+
                                                          " (H."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND5]+
                                                          "="+
                                                          "S."+PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]+
                                                          " AND"+
                                                          " H."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID]+
                                                          " ="+idEmp+
                                                          ")";

                                              }
                                            else if(tglText.equals("06")){
                                                  sql=sql+" AND"+
                                                          " H."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D6]+
                                                          "="+
                                                          "S."+PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]+
                                                          " OR"+
                                                          " (H."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND6]+
                                                          "="+
                                                          "S."+PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]+
                                                          " AND"+
                                                          " H."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID]+
                                                          " ="+idEmp+
                                                          ")";

                                              }
                                            else if(tglText.equals("07")){
                                                  sql=sql+" AND"+
                                                          " H."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D7]+
                                                          "="+
                                                          "S."+PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]+
                                                          " OR"+
                                                          " (H."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND7]+
                                                          "="+
                                                          "S."+PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]+
                                                          " AND"+
                                                          " H."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID]+
                                                          " ="+idEmp+
                                                          ")";

                                              }
                                            else if(tglText.equals("08")){
                                                  sql=sql+" AND"+
                                                          " H."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D8]+
                                                          "="+
                                                          "S."+PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]+
                                                          " OR"+
                                                          " (H."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND8]+
                                                          "="+
                                                          "S."+PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]+
                                                          " AND"+
                                                          " H."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID]+
                                                          " ="+idEmp+
                                                          ")";

                                              }
                                            else if(tglText.equals("09")){
                                                  sql=sql+" AND"+
                                                          " H."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D9]+
                                                          "="+
                                                          "S."+PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]+
                                                          " OR"+
                                                          " (H."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND9]+
                                                          "="+
                                                          "S."+PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]+
                                                          " AND"+
                                                          " H."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID]+
                                                          " ="+idEmp+
                                                          ")";

                                              }
                                            else if(tglText.equals("10")){
                                                  sql=sql+" AND"+
                                                          " H."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D10]+
                                                          "="+
                                                          "S."+PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]+
                                                          " OR"+
                                                          " (H."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND10]+
                                                          "="+
                                                          "S."+PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]+
                                                          " AND"+
                                                          " H."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID]+
                                                          " ="+idEmp+
                                                          ")";

                                              }
                                            else if(tglText.equals("11")){
                                                  sql=sql+" AND"+
                                                          " H."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D11]+
                                                          "="+
                                                          "S."+PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]+
                                                          " OR"+
                                                          " (H."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND11]+
                                                          "="+
                                                          "S."+PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]+
                                                          " AND"+
                                                          " H."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID]+
                                                          " ="+idEmp+
                                                          ")";
                                              }
                                            else if(tglText.equals("12")){
                                                  sql=sql+" AND"+
                                                          " H."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D12]+
                                                          "="+
                                                          "S."+PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]+
                                                          " OR"+
                                                          " (H."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND12]+
                                                          "="+
                                                          "S."+PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]+
                                                          " AND"+
                                                          " H."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID]+
                                                          " ="+idEmp+
                                                          ")";

                                              }
                                            else if(tglText.equals("13")){
                                                  sql=sql+" AND"+
                                                          " H."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D13]+
                                                          "="+
                                                          "S."+PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]+
                                                          " OR"+
                                                          " (H."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND13]+
                                                          "="+
                                                          "S."+PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]+
                                                          " AND"+
                                                          " H."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID]+
                                                          " ="+idEmp+
                                                          ")";

                                              }
                                            else if(tglText.equals("14")){
                                                  sql=sql+" AND"+
                                                          " H."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D14]+
                                                          "="+
                                                          "S."+PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]+
                                                          " OR"+
                                                          " (H."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND14]+
                                                          "="+
                                                          "S."+PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]+
                                                          " AND"+
                                                          " H."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID]+
                                                          " ="+idEmp+
                                                          ")";

                                              }
                                            else if(tglText.equals("15")){
                                                  sql=sql+" AND"+
                                                          " H."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D15]+
                                                          "="+
                                                          "S."+PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]+
                                                          " OR"+
                                                          " (H."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND15]+
                                                          "="+
                                                          "S."+PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]+
                                                          " AND"+
                                                          " H."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID]+
                                                          " ="+idEmp+
                                                          ")";

                                              }
                                            else if(tglText.equals("16")){
                                                  sql=sql+" AND"+
                                                          " H."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D16]+
                                                          "="+
                                                          "S."+PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]+
                                                          " OR"+
                                                          " (H."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND16]+
                                                          "="+
                                                          "S."+PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]+
                                                          " AND"+
                                                          " H."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID]+
                                                          " ="+idEmp+
                                                          ")";

                                              }
                                            else if(tglText.equals("17")){
                                                  sql=sql+" AND"+
                                                          " H."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D17]+
                                                          "="+
                                                          "S."+PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]+
                                                          " OR"+
                                                          " (H."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND17]+
                                                          "="+
                                                          "S."+PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]+
                                                          " AND"+
                                                          " H."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID]+
                                                          " ="+idEmp+
                                                          ")";

                                              }
                                            else if(tglText.equals("18")){
                                                  sql=sql+" AND"+
                                                          " H."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D18]+
                                                          "="+
                                                          "S."+PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]+
                                                          " OR"+
                                                          " (H."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND18]+
                                                          "="+
                                                          "S."+PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]+
                                                          " AND"+
                                                          " H."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID]+
                                                          " ="+idEmp+
                                                          ")";

                                              }
                                            else if(tglText.equals("19")){
                                                  sql=sql+" AND"+
                                                          " H."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D19]+
                                                          "="+
                                                          "S."+PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]+
                                                          " OR"+
                                                          " (H."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND19]+
                                                          "="+
                                                          "S."+PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]+
                                                          " AND"+
                                                          " H."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID]+
                                                          " ="+idEmp+
                                                          ")";

                                              }
                                            else if(tglText.equals("20")){
                                                  sql=sql+" AND"+
                                                          " H."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D20]+
                                                          "="+
                                                          "S."+PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]+
                                                          " OR"+
                                                          " (H."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND20]+
                                                          "="+
                                                          "S."+PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]+
                                                          " AND"+
                                                          " H."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID]+
                                                          " ="+idEmp+
                                                          ")";

                                              }
                                            else if(tglText.equals("21")){
                                                  sql=sql+" AND"+
                                                          " H."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D21]+
                                                          "="+
                                                          "S."+PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]+
                                                          " OR"+
                                                          " (H."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND21]+
                                                          "="+
                                                          "S."+PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]+
                                                          " AND"+
                                                          " H."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID]+
                                                          " ="+idEmp+
                                                          ")";

                                              }
                                            else if(tglText.equals("22")){
                                                  sql=sql+" AND"+
                                                          " H."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D22]+
                                                          "="+
                                                          "S."+PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]+
                                                          " OR"+
                                                          " (H."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND22]+
                                                          "="+
                                                          "S."+PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]+
                                                          " AND"+
                                                          " H."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID]+
                                                          " ="+idEmp+
                                                          ")";

                                              }
                                            else if(tglText.equals("23")){
                                                  sql=sql+" AND"+
                                                          " H."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D23]+
                                                          "="+
                                                          "S."+PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]+
                                                          " OR"+
                                                          " (H."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND23]+
                                                          "="+
                                                          "S."+PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]+
                                                          " AND"+
                                                          " H."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID]+
                                                          " ="+idEmp+
                                                          ")";

                                              }
                                            else if(tglText.equals("24")){
                                                  sql=sql+" AND"+
                                                          " H."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D24]+
                                                          "="+
                                                          "S."+PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]+
                                                          " OR"+
                                                          " (H."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND24]+
                                                          "="+
                                                          "S."+PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]+
                                                          " AND"+
                                                          " H."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID]+
                                                          " ="+idEmp+
                                                          ")";

                                              }
                                            else if(tglText.equals("25")){
                                                  sql=sql+" AND"+
                                                          " H."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D25]+
                                                          "="+
                                                          "S."+PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]+
                                                          " OR"+
                                                          " (H."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND25]+
                                                          "="+
                                                          "S."+PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]+
                                                          " AND"+
                                                          " H."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID]+
                                                          " ="+idEmp+
                                                          ")";

                                              }
                                            else if(tglText.equals("26")){
                                                  sql=sql+" AND"+
                                                          " H."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D26]+
                                                          "="+
                                                          "S."+PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]+
                                                          " OR"+
                                                          " (H."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND26]+
                                                          "="+
                                                          "S."+PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]+
                                                          " AND"+
                                                          " H."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID]+
                                                          " ="+idEmp+
                                                          ")";

                                              }
                                            else if(tglText.equals("27")){
                                                  sql=sql+" AND"+
                                                          " H."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D27]+
                                                          "="+
                                                          "S."+PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]+
                                                          " OR"+
                                                          " (H."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND27]+
                                                          "="+
                                                          "S."+PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]+
                                                          " AND"+
                                                          " H."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID]+
                                                          " ="+idEmp+
                                                          ")";

                                              }
                                            else if(tglText.equals("28")){
                                                  sql=sql+" AND"+
                                                          " H."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D28]+
                                                          "="+
                                                          "S."+PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]+
                                                          " OR"+
                                                          " (H."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND28]+
                                                          "="+
                                                          "S."+PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]+
                                                          " AND"+
                                                          " H."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID]+
                                                          " ="+idEmp+
                                                          ")";
                                                 

                                              }
                                            else if(tglText.equals("29")){
                                                  sql=sql+" AND"+
                                                          " H."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D29]+
                                                          "="+
                                                          "S."+PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]+
                                                          " OR"+
                                                          " (H."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND29]+
                                                          "="+
                                                          "S."+PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]+
                                                          " AND"+
                                                          " H."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID]+
                                                          " ="+idEmp+
                                                          ")";
                                              }
                                            else if(tglText.equals("30")){
                                                  sql=sql+" AND"+
                                                          " H."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D30]+
                                                          "="+
                                                          "S."+PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]+
                                                          " OR"+
                                                          " (H."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND30]+
                                                          "="+
                                                          "S."+PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]+
                                                          " AND"+
                                                          " H."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID]+
                                                          " ="+idEmp+
                                                          ")";

                                              }
                                            else if(tglText.equals("31")){
                                                  sql=sql+" AND"+
                                                          " H."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D31]+
                                                          "="+
                                                          "S."+PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]+
                                                          " OR"+
                                                          " (H."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND31]+
                                                          "="+
                                                          "S."+PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]+
                                                          " AND"+
                                                          " H."+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID]+
                                                          " ="+idEmp+
                                                          ")";

                                              }
                        dbConn = doConnect(dbDriver, dbUrl);                
                        stmt = dbConn.createStatement();
                        ResultSet rs = stmt.executeQuery(sql);  
                        System.out.println("SQL" +sql);
                        //System.out.println("rs.next()" +rs.next());
                        while(rs.next())
                            {   
                                //System.out.println("masuk");
                                vectTemp.add(""+rs.getTime(1));
                                vectTemp.add(""+rs.getTime(2));
                                vectTemp.add(""+rs.getInt(4));
                            }
               
                   vectTemp.add("0");
                   vectTemp.add("0"); 
                   result.add(vectTemp); 
                   System.out.println("nilai VectTemp" +vectTemp);
                 }
                
            }
            catch(Exception exx){
                System.out.println("Error nich"+exx.toString());
            }
        }

        return result;
         
    }        
       
    public int deleteDataPresence(String fdDate, String fcTime, String idNumber, String fcStatus)
    {
    /*    Connection dbConn = null;
        Statement stmt = null;                        
        int result = 0;
        try{                
            String sql = "DELETE FROM " + tableName +
                         " WHERE FDDATE = #" + fdDate + "#" + 
                         " AND FCTIME = '" + fcTime + "'" +
                         " AND FCIDNUMBER = '" + idNumber + "'" +
                         " AND FCSTATUS = '" + fcStatus + "'";                

            System.out.println("sql delete : "+sql);
            dbConn = doConnect();                
            stmt = dbConn.createStatement();
            result = stmt.executeUpdate(sql);                                

        }catch(Exception e){
            System.out.println("err delete : "+e.toString());
        }finally{
            doDisconnect(); 
            return result;
        }
     */
        return 0;
    }
    

    public int deleteDataPresence()
    {
    /*    Connection dbConn = null;
        Statement stmt = null;                        
        int result = 0;
        try{                
            String sql = "DELETE FROM " + tableName;

            System.out.println("sql delete : "+sql);
            dbConn = doConnect();                
            stmt = dbConn.createStatement();
            result = stmt.executeUpdate(sql);                  

        }catch(Exception e){
            System.out.println("err delete : "+e.toString());
        }finally{
            doDisconnect(); 
            return result;
        }
     */
        return 0;
    }

    public Vector transferDataPresence()
    {
        TransferPresenceFromMdfText transfer = new TransferPresenceFromMdfText();
        Vector vectPresenceData = transfer.retrieveData();                    
        if(vectPresenceData!=null && vectPresenceData.size()>0)
        {            
            System.out.println("\n\n------ START TRANSFER DATA CARDNETIC TEXT ---------\n\n");
            System.out.println("\nvectPresenceData.size() : "+vectPresenceData.size()+"\n");
            
            int maxResult = vectPresenceData.size();
            for(int i=0; i<maxResult; i++)
            {
                Vector tr = (Vector)vectPresenceData.get(i);
                
                // proses pembuatan object presence untuk diproses 
                Presence presence = new Presence();                         
                
                System.out.println("processing data i = "+i);
                System.out.println("emp id:"+String.valueOf(tr.get(2)));
               
                
                long employeeId = PstEmployee.getEmployeeByBarcode(String.valueOf(tr.get(2)).trim());
                presence.setEmployeeId(employeeId);   
             
           
                //edited by yunny
                String date=(String)tr.get(0);
                int y = Integer.parseInt(date.substring(0,4).trim());
                int M = Integer.parseInt(date.substring(5,7));
                
                int d = Integer.parseInt(date.substring(8,10));
                String time=(String)tr.get(3);
                int h = Integer.parseInt(time.substring(0,2));
                int m = Integer.parseInt(time.substring(3,5));
                
                //presence.setPresenceDatetime(tempPresDate);
                java.util.Date presenceDateTime = new java.util.Date(y-1900, M-1, d, h, m);
                presence.setPresenceDatetime(presenceDateTime);
               //status
                String scheduleIn=""+tr.get(4)+"";
                System.out.println("scheduleIn::::" +scheduleIn);
                /*String scheduleIn2=""+tr.get(6)+"";
                System.out.println("scheduleIn2::::" +scheduleIn2);
                String scheduleOut2=""+tr.get(7)+"";
                System.out.println("scheduleOut2::::" +scheduleOut2);*/
               
                
                if(tr.get(4).equals("0")|| tr.get(5).equals("0")){
                    
                }
                else{
                double hoursScheduleIn=Double.parseDouble(scheduleIn.substring(0,2));
               // int intHoursScheduleIn=Integer.parseInt(scheduleIn.substring(0,2));
                System.out.println("hoursScheduleIn::::" +hoursScheduleIn);
                double minuteScheduleIn=Double.parseDouble(scheduleIn.substring(3,5));
                
                String scheduleOut=""+tr.get(5)+"";
                String scheduleCategory = ""+tr.get(6)+"";
                //String scheduleOut2=""+tr.get(7)+"";
                double hoursScheduleOut=Double.parseDouble(scheduleOut.substring(0,2));
                double minuteScheduleOut=Double.parseDouble(scheduleOut.substring(3,5));
                int changeCategory = 0;
                //percobaan bagi yang tidak ada split
                 //ganti jam pulang ( untuk schedule tertentu)--kondisi tidak wajar
                 // jam masuk dan pulang disamain dengan ST3
                     if(hoursScheduleOut == 23){
                        //System.out.println("masuk sini " );
                        hoursScheduleOut = 8;
                        hoursScheduleIn = 22;
                        changeCategory = 1;
                        scheduleCategory = "5";
                     }
                
                //**********************************************
                double timeHoursStatus=hoursScheduleOut+hoursScheduleIn;
                double timeMinuteStatus=minuteScheduleOut+minuteScheduleIn;
                   if(timeMinuteStatus >= 60){
                        timeHoursStatus=timeHoursStatus + 1;
                        timeMinuteStatus=timeMinuteStatus - 60;
                    }
                double x=(timeHoursStatus/2);
                System.out.println("nilai x "+x );
                 //Kondisi untuk Menentukan status
                  //untuk kondisi yang schedule in  dan out dalam hari yang berbeda
                    System.out.println("nilai tr get(6)...... "+tr.get(6) );
                    // untuk kondisi across
                      if(scheduleCategory.equals("5")){
                           System.out.println(" accross" );
                                if(h < x){
                                    int PresenceStatusTemp=1;
                                    presence.setStatus(PresenceStatusTemp);
                                    System.out.println("Status:"+PresenceStatusTemp);
                                }
                                else if(h >= x ){
                                      int PresenceStatusTemp=0;
                                        presence.setStatus(PresenceStatusTemp);
                                        System.out.println("Status:"+PresenceStatusTemp);
                                  } 
                       
                      }
                      else{
                          //untuk kondisi yang schedule in  dan out dalam hari yang sama
                           if(h < x){
                                int PresenceStatusTemp=0;
                                presence.setStatus(PresenceStatusTemp);
                                System.out.println("Status:"+PresenceStatusTemp);
                            }

                            else if(h >= x ){
                                int PresenceStatusTemp=1;
                                presence.setStatus(PresenceStatusTemp);
                                System.out.println("Status:"+PresenceStatusTemp);
                            }
                      }
                }
                
                //percobaan non split sampe sini
                
               /* if(scheduleIn2.equals("0")){
                    double timeHoursStatus=hoursScheduleOut+hoursScheduleIn;
                    double timeMinuteStatus=minuteScheduleOut+minuteScheduleIn;
                    if(timeMinuteStatus >= 60){
                        timeHoursStatus=timeHoursStatus + 1;
                        timeMinuteStatus=timeMinuteStatus - 60;
                    }
                    double x=(timeHoursStatus/2);
                    //Kondisi untuk Menentukan status
                    //untuk kondisi yang schedule in  dan out dalam hari yang sma
                     if(hoursScheduleIn < hoursScheduleOut){
                            if(h < x){
                                int PresenceStatusTemp=0;
                                presence.setStatus(PresenceStatusTemp);
                                System.out.println("Status:"+PresenceStatusTemp);
                            }

                            else if(h >= x ){
                                int PresenceStatusTemp=1;
                                presence.setStatus(PresenceStatusTemp);
                                 System.out.println("Status:"+PresenceStatusTemp);
                            }
                      } 
                      //untuk kondisi yang schedule in  dan out dalam hari yang berbeda
                      else if(hoursScheduleIn > hoursScheduleOut){
                             if(h < x){
                                int PresenceStatusTemp=1;
                                presence.setStatus(PresenceStatusTemp);
                                 System.out.println("Status:"+PresenceStatusTemp);
                            }
                            else if(h >= x ){
                                int PresenceStatusTemp=0;
                                presence.setStatus(PresenceStatusTemp);
                                 System.out.println("Status:"+PresenceStatusTemp);
                            } 
                      }
                 }
                
                else {
                    System.out.println("Masuk Split");
                    double hoursScheduleIn2=Double.parseDouble(scheduleIn2.substring(0,2));
                    System.out.println("hoursScheduleIn2  "+hoursScheduleIn2);
                    double minuteScheduleIn2=Double.parseDouble(scheduleIn2.substring(3,5));
                    double hoursScheduleOut2=Double.parseDouble(scheduleOut2.substring(0,2));
                    double minuteScheduleOut2=Double.parseDouble(scheduleOut2.substring(3,5));
                    double firstIn=hoursScheduleIn-h;
                    System.out.println("firstIn  "+firstIn);
                    double firstOut=hoursScheduleOut-h;
                    double secondIn=hoursScheduleIn2-h;
                    System.out.println("secondIn  "+secondIn);
                    double secondOut=hoursScheduleOut2-h;
                    if(firstIn >= 0 && secondIn >= 0){
                        if(firstIn < secondIn){
                            //untuk jam pertama
                            double timeHoursStatus=hoursScheduleOut+hoursScheduleIn;
                            double timeMinuteStatus=minuteScheduleOut+minuteScheduleIn;
                            if(timeMinuteStatus >= 60){
                                    timeHoursStatus=timeHoursStatus + 1;
                                    timeMinuteStatus=timeMinuteStatus - 60;
                            }
                            
                        double x=(timeHoursStatus/2);
                        //Kondisi untuk Menentukan status
                        //untuk kondisi yang schedule in  dan out dalam hari yang sama
                            if(hoursScheduleIn < hoursScheduleOut){
                                if(h < x){
                                    int PresenceStatusTemp=0;
                                    presence.setStatus(PresenceStatusTemp);
                                    System.out.println("Status:"+PresenceStatusTemp);

                                }
                                else if(h >= x ){
                                    int PresenceStatusTemp=1;
                                    presence.setStatus(PresenceStatusTemp);
                                     System.out.println("Status:"+PresenceStatusTemp);
                                }
                         
                           } 
                        
                         //untuk kondisi yang schedule in  dan out dalam hari yang berbeda
                            else if(hoursScheduleIn > hoursScheduleOut){
                             if(h < x){
                                int PresenceStatusTemp=1;
                                presence.setStatus(PresenceStatusTemp);
                                 System.out.println("Status:"+PresenceStatusTemp);
                            }
                            else if(h >= x ){
                                int PresenceStatusTemp=0;
                                presence.setStatus(PresenceStatusTemp);
                                 System.out.println("Status:"+PresenceStatusTemp);
                            } 
                          }
             
                        }
                    }
                    
                    else if(firstIn < 0 && secondIn >= 0)
                    {
                        firstIn=firstIn*-1;
                        if(firstIn < secondIn){
                            //untuk jam pertama
                            double timeHoursStatus=hoursScheduleOut+hoursScheduleIn;
                            double timeMinuteStatus=minuteScheduleOut+minuteScheduleIn;
                             if(timeMinuteStatus >= 60){
                                timeHoursStatus=timeHoursStatus + 1;
                                timeMinuteStatus=timeMinuteStatus - 60;
                            }
                            double x=(timeHoursStatus/2);
                            //Kondisi untuk Menentukan status
                            //untuk kondisi yang schedule in  dan out dalam hari yang sama
                            if(hoursScheduleIn < hoursScheduleOut){
                                 if(h < x){
                                        int PresenceStatusTemp=0;
                                        presence.setStatus(PresenceStatusTemp);
                                }
                                 else if(h >= x ){
                                     int PresenceStatusTemp=1;
                                     presence.setStatus(PresenceStatusTemp);
                                  
                                }
                            } 
                            //untuk kondisi yang schedule in  dan out dalam hari yang berbeda
                            else if(hoursScheduleIn > hoursScheduleOut){
                                 if(h < x){
                                    int PresenceStatusTemp=1;
                                    presence.setStatus(PresenceStatusTemp);
                                }
                                else if(h >= x ){
                                    int PresenceStatusTemp=0;
                                    presence.setStatus(PresenceStatusTemp);
                                } 
                            }
                        }
                        
                        else if(secondIn == firstIn){
                            if(firstOut < secondOut){
                                //untuk jam pertama
                                double timeHoursStatus=hoursScheduleOut+hoursScheduleIn;
                                double timeMinuteStatus=minuteScheduleOut+minuteScheduleIn;
                                 if(timeMinuteStatus >= 60){
                                    timeHoursStatus=timeHoursStatus + 1;
                                    timeMinuteStatus=timeMinuteStatus - 60;
                                }
                                double x=(timeHoursStatus/2);
                                //Kondisi untuk Menentukan status
                                //untuk kondisi yang schedule in  dan out dalam hari yang sama
                                if(hoursScheduleIn < hoursScheduleOut){
                                    if(h < x){
                                        int PresenceStatusTemp=0;
                                        presence.setStatus(PresenceStatusTemp);
                                     }
                                    else if(h >= x ){
                                    int PresenceStatusTemp=1;
                                    presence.setStatus(PresenceStatusTemp);
                                    }
                                } 
                            //untuk kondisi yang schedule in  dan out dalam hari yang berbeda
                            else if(hoursScheduleIn > hoursScheduleOut){
                                 if(h < x){
                                    int PresenceStatusTemp=1;
                                    presence.setStatus(PresenceStatusTemp);
                                 }
                                else if(h >= x ){
                                    int PresenceStatusTemp=0;
                                    presence.setStatus(PresenceStatusTemp);
                                } 
                            }
                         }
                        }
                        else if(secondIn < firstIn)
                        {
                            //untuk jam yang kedua
                            double timeHoursStatus2=hoursScheduleOut2+hoursScheduleIn2;
                            double timeMinuteStatus2=minuteScheduleOut2+minuteScheduleIn2;
                            if(timeMinuteStatus2 >= 60){
                                timeHoursStatus2=timeHoursStatus2 + 1;
                                timeMinuteStatus2=timeMinuteStatus2 - 60;
                            }
                            double x2=(timeHoursStatus2/2);
                            //Kondisi untuk Menentukan status
                            //untuk kondisi yang schedule in  dan out dalam hari yang sama
                             if(hoursScheduleIn2 < hoursScheduleOut2){
                                 if(h < x2){
                                     int PresenceStatusTemp=0;
                                     presence.setStatus(PresenceStatusTemp);
                                 }
                                 else if(h >= x2 ){
                                      int PresenceStatusTemp=1;
                                      presence.setStatus(PresenceStatusTemp);
                                }
                            } 
                            //untuk kondisi yang schedule in  dan out dalam hari yang berbeda
                            else if(hoursScheduleIn2 > hoursScheduleOut2){
                                     if(h < x2){
                                        int PresenceStatusTemp=1;
                                        presence.setStatus(PresenceStatusTemp);
                                         System.out.println("Statusjam2:"+PresenceStatusTemp);
                                    }
                                    else if(h >= x2 ){
                                        int PresenceStatusTemp=0;
                                        presence.setStatus(PresenceStatusTemp);
                                         System.out.println("Statusjam2:"+PresenceStatusTemp);
                                    } 
                             }
                       }
                    }
                    
                    else if(firstIn < 0 && secondIn < 0){
                        firstIn=firstIn*-1;
                        secondIn=secondIn*-1;
                         if(secondIn < firstIn)
                          {
                            double timeHoursStatus2=hoursScheduleOut2+hoursScheduleIn2;
                            double timeMinuteStatus2=minuteScheduleOut2+minuteScheduleIn2;
                            if(timeMinuteStatus2 >= 60){
                                timeHoursStatus2=timeHoursStatus2 + 1;
                                timeMinuteStatus2=timeMinuteStatus2 - 60;
                            }
                            double x2=(timeHoursStatus2/2);
                            //Kondisi untuk Menentukan status
                            //untuk kondisi yang schedule in  dan out dalam hari yang sama
                            if(hoursScheduleIn2 < hoursScheduleOut2){
                            if(h < x2){
                                 int PresenceStatusTemp=0;
                                 presence.setStatus(PresenceStatusTemp);
                            }
                            else if(h >= x2 ){
                                 int PresenceStatusTemp=1;
                                 presence.setStatus(PresenceStatusTemp);
                            }
                          } 
                            
                          //untuk kondisi yang schedule in  dan out dalam hari yang berbeda
                          else if(hoursScheduleIn2 > hoursScheduleOut2){
                             if(h < x2){
                                 int PresenceStatusTemp=1;
                                 presence.setStatus(PresenceStatusTemp);
                              }
                             else if(h >= x2 ){
                                        int PresenceStatusTemp=0;
                                        presence.setStatus(PresenceStatusTemp);
                                         System.out.println("Statusjam2:"+PresenceStatusTemp);
                               } 
                           }
                       }
                    }
                }*/
               
                

              
               //int intPresenceStatusTemp = (PresenceStatusTemp);
               //System.out.println("Waktu In:"+intPresenceStatusTemp);*/
               /* int intPresenceStatus = 0;
                switch(intPresenceStatusTemp)
                {
                    case ACTION_OUT_HOME : 
                        intPresenceStatus = Presence.STATUS_OUT;
                        break;
                        
                    case ACTION_IN :
                        intPresenceStatus = Presence.STATUS_IN;
                        break;
                        
                    case ACTION_OUT_ON_DUTY :
                        intPresenceStatus = Presence.STATUS_OUT_ON_DUTY;
                        break;

                    case ACTION_IN_LUNCH :
                        intPresenceStatus = Presence.STATUS_IN_PERSONAL;
                        break;

                    case ACTION_IN_BREAK :
                        intPresenceStatus = Presence.STATUS_IN_PERSONAL;
                        break;

                    case ACTION_IN_CALLBACK :
                        intPresenceStatus = Presence.STATUS_CALL_BACK;
                        break;
                        
                    default :      
                        break;
                }
                
                System.out.println("intPresenceStatus : "+intPresenceStatus);
                
                presence.setStatus(intPresenceStatus);*/
                //presence.setStatus(1);
                presence.setAnalyzed(0);
                presence.setTransferred(PstPresence.PRESENCE_NOT_TRANSFERRED);
                
                
                // insert data ke table presence harisma
                boolean objPresenceExist = false;
                long presenceId = 0;
                try
                {
                    if(employeeId != 0)
                    {
                        objPresenceExist = PstPresence.presenceExist(presence);
                        
                        System.out.println("objPresenceExist : "+objPresenceExist);
                        
                        if(!objPresenceExist)
                        {
                            presenceId = PstPresence.insertExc(presence);
                            System.out.println("sukkses input presence : presenceId : "+presenceId);
                        }
                        // delete dari database  
                        //transfer.deleteDataPresence(com.dimata.util.Formater.formatDate(tempPresDate,"MM-dd-yyyy"), String.valueOf(tr.get(1)), String.valueOf(tr.get(2)), String.valueOf(tr.get(3)));
                    }
                }
                catch(Exception e)
                {
                    System.out.println("Err : " + e.toString());   
                }
                       
                
                // untuk check ke empSchedule
                // update presence (IN or OUT) on employee schedule  
                if(!objPresenceExist)
                {
                    
                    System.out.println("\nin update emp schedule ");
                    
                    if(presenceId != 0)
                    {
                        long periodId = PstPeriod.getPeriodIdBySelectedDate(presence.getPresenceDatetime());                                
                        
                        System.out.println("\nperiodId : "+periodId);

                        int updatedFieldIndex = -1;  
                        long updatePeriodId = periodId;
                        Vector vectFieldIndex = PstEmpSchedule.getFieldIndexWillUpdated(periodId, presence.getEmployeeId(), presence.getStatus(), presence.getPresenceDatetime());                                
                        
                        System.out.println("\nvectFieldIndex : "+vectFieldIndex);
                        
                        if(vectFieldIndex!=null && vectFieldIndex.size()==2)
                        {
                            updatePeriodId = Long.parseLong(String.valueOf(vectFieldIndex.get(0)));
                            updatedFieldIndex = Integer.parseInt(String.valueOf(vectFieldIndex.get(1)));
                        }

                        int updateStatus = 0;  
                        try
                        {
                            updateStatus = PstEmpSchedule.updateScheduleDataByPresence(updatePeriodId, presence.getEmployeeId(), updatedFieldIndex, presence.getPresenceDatetime());                    
                            
                            System.out.println("\nupdate emp sch status : "+updateStatus);
                            
                            if(updateStatus>0)
                            {
                                presence.setTransferred(PstPresence.PRESENCE_TRANSFERRED);
                                PstPresence.updateExc(presence);                        
                                
                                System.out.println("\nsuccess update presence II");
                            }

                        }
                        catch(Exception e)
                        {
                            System.out.println("Update Presence exc : "+e.toString());
                        }
                    }
                }
            } 
        }                  
        return vectPresenceData;
    }        
    

    /**
     * Testing method
     */    
    public static void main(String args[])  
    {
        TransferPresenceFromMdfText transfer = new TransferPresenceFromMdfText(); 
        
        /*
        int result = transfer.deleteDataPresence();                        
        System.out.println("result : " + result);                 
        Vector vectResult = transfer.retrieveData();
        System.out.println("Size : " + vectResult.size());        
        */
        
        
        
        Vector result = transfer.transferDataPresence();
        if(result!=null && result.size()>0)
        {            
            int maxResult = result.size();
            for(int i=0; i<maxResult; i++){
                Vector tr = (Vector)result.get(i);
                System.out.print(String.valueOf(tr.get(0)));
                System.out.print(String.valueOf(tr.get(1)));
                System.out.print(String.valueOf(tr.get(2)));
                System.out.println(String.valueOf(tr.get(3)));
            
            } 
        } 
        
    }        

    
}
