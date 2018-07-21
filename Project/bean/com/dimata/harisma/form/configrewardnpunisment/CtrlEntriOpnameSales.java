/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.form.configrewardnpunisment;

import com.dimata.harisma.entity.attendance.PstEmpSchedule;
import com.dimata.harisma.entity.attendance.employeeoutlet.EmployeeOutlet;
import com.dimata.harisma.entity.attendance.employeeoutlet.PstEmployeeOutlet;
import com.dimata.harisma.entity.configrewardnpunisment.EntriOpnameSales;
import com.dimata.harisma.entity.configrewardnpunisment.PstConfigRewardAndPunishment;
import com.dimata.harisma.entity.configrewardnpunisment.PstEntriOpnameSales;
import com.dimata.harisma.entity.configrewardnpunisment.PstRewardAndPunishmentDetail;
import com.dimata.harisma.entity.configrewardnpunisment.PstRewardAndPunishmentMain;
import com.dimata.harisma.entity.configrewardnpunisment.RewardnPunismentDetail;
import com.dimata.harisma.entity.configrewardnpunisment.RewardnPunismentMain;
import com.dimata.harisma.entity.configrewardnpunisment.SessRewardPunishmentDetail;
import com.dimata.harisma.entity.configrewardnpunisment.SpecialEmpScheduleCount;
import com.dimata.harisma.entity.employee.Employee;
import com.dimata.harisma.entity.employee.PstEmployee;
import com.dimata.harisma.entity.koefisionposition.KoefisionPosition;
import com.dimata.harisma.entity.koefisionposition.PstKoefisionPosition;
import com.dimata.harisma.entity.masterdata.PstPeriod;
import com.dimata.harisma.utility.harisma.machine.transferdataemployee.PstScheduleDestopTransfer;
import com.dimata.qdep.db.DBException;
import com.dimata.qdep.entity.I_DocStatus;
import com.dimata.qdep.form.Control;
import com.dimata.qdep.form.FRMMessage;
import com.dimata.qdep.system.I_DBExceptionInfo;
import com.dimata.util.Command;
import com.dimata.util.Formater;
import com.dimata.util.lang.I_Language;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Devin
 */
public class CtrlEntriOpnameSales extends Control implements I_Language {

    public static int RSLT_OK = 0;
    public static int RSLT_UNKNOWN_ERROR = 1;
    public static int RSLT_EST_CODE_EXIST = 2;
    public static int RSLT_FORM_INCOMPLETE = 3;
    public static String[][] resultText = {
        {"Berhasil", "Tidak Bisa Di Prosess", "kode Sudah Ada", "Data Belum Lengkap"},
        {"Succes", "Can not process", "Estimation code exist", "Data incomplete"}
    };
    private int start;
    private String msgString;
    private EntriOpnameSales entriOpnameSales;
    private PstEntriOpnameSales pstEntriOpnameSales;
    private FrmEntriOpnameSales frmEntriOpnameSales;
    int language = LANGUAGE_DEFAULT;

    public CtrlEntriOpnameSales(HttpServletRequest request) {
        msgString = "";

        entriOpnameSales = new EntriOpnameSales();
        try {
            pstEntriOpnameSales = new PstEntriOpnameSales(0);
        } catch (Exception e) {
            System.out.println("Exception" + e);

        }
        frmEntriOpnameSales = new FrmEntriOpnameSales(request, entriOpnameSales);
    }

    private String getSystemMessage(int msgCode) {
        switch (msgCode) {
            case I_DBExceptionInfo.MULTIPLE_ID:
                this.frmEntriOpnameSales.addError(FrmEntriOpnameSales.FRM_FLD_ENTRI_OPNAME_SALES_ID,
                        resultText[language][RSLT_EST_CODE_EXIST]);
                return resultText[language][RSLT_EST_CODE_EXIST];
            default:
                return resultText[language][RSLT_UNKNOWN_ERROR];
        }

    }

    private int getControlMsgId(int msgCode) {
        switch (msgCode) {
            case I_DBExceptionInfo.MULTIPLE_ID:
                return RSLT_EST_CODE_EXIST;
            default:
                return RSLT_UNKNOWN_ERROR;
        }
    }

    public int getLanguage() {
        return language;
    }

    public void setLanguage(int language) {
        this.language = language;
    }

    public EntriOpnameSales getEntriOpnameSales() {
        return entriOpnameSales;
    }

    public FrmEntriOpnameSales getForm() {
        return frmEntriOpnameSales;
    }

    public String getMessage() {
        return msgString;
    }

    public int getStart() {
        return start;
    }

    public int action(int cmd, long oidEntriOpname, Vector listEntriOpnameSales) {
        msgString = "";
        int excCode = I_DBExceptionInfo.NO_EXCEPTION;
        int rsCode = RSLT_OK;
        switch (cmd) {
            case Command.GOTO:
                frmEntriOpnameSales.requestEntityObject(oidEntriOpname, entriOpnameSales, listEntriOpnameSales);
                break;
            case Command.POST:
                //prosess entri opname
                boolean chekError = false;
                frmEntriOpnameSales.requestEntityMultipleObject(listEntriOpnameSales);
               
                Hashtable hasCekDataSudahUpdate = PstRewardAndPunishmentMain.hashListRewatPunismentMainAda();//carilah data di table reward n punisment main parameter Key :oidEntriOpname : object rewardnPunismentMain
                if (frmEntriOpnameSales.getVlistEntriOpname() != null && frmEntriOpnameSales.getVlistEntriOpname().size() > 0) {
                    //for (int idx = 0; idx < frmEntriOpnameSales.getVlistEntriOpname().size(); idx++) {
                        
                    for (int idx = 0; idx < frmEntriOpnameSales.getVlistEntriOpname().size(); idx++) {
                        EntriOpnameSales entriOpnameSaless = (EntriOpnameSales) frmEntriOpnameSales.getVlistEntriOpname().get(idx);
                        //cek statusnya jika sudah di prosess
                       // if (hasCekDataSudahUpdate != null && hasCekDataSudahUpdate.containsKey(entriOpnameSaless.getPeriodId()+ "_" +entriOpnameSaless.getLocationId()+"_"+entriOpnameSaless.getJenisSoId())) {
                        //jika document tsb statusnya sudah prosess maka akan update detail saja
                        if (hasCekDataSudahUpdate != null && hasCekDataSudahUpdate.containsKey(""+entriOpnameSaless.getOID())) {
                            
                            if (chekError == false) {
                                //update main dan detail
                               RewardnPunismentMain rewardnPunismentMain = new RewardnPunismentMain();
                                try{
                                     rewardnPunismentMain = (RewardnPunismentMain)hasCekDataSudahUpdate.get(""+entriOpnameSaless.getOID());
                                     
                                     
                                     //priska 2014-12-3 mencari main id untuk di delete pada detailnya
                                     long rewardPunismentMainId=PstRewardAndPunishmentMain.getMainIdWhereEntriOpname(rewardnPunismentMain.getEntriOpnameId());
                                     //menghapus semua data yang sudah diset pada detail untuk di set ulang
                                     long deletealldetail= PstRewardAndPunishmentDetail.deletewheremain(rewardPunismentMainId);
                             //        long deletemain= PstRewardAndPunishmentMain.deleteExc(rewardPunismentMainId);
                           
                              //ganti data main
                               // rewardnPunismentMain = PstRewardAndPunishmentMain.insertExcs(rewardnPunismentMain);
                                     //long success= PstRewardAndPunishmentMain.updateExc(rewardnPunismentMain);
                                     if (rewardnPunismentMain.getOID() != 0) {
                                        //jika sukses insert main maka insert ke detail
                                        //priska 27-11-2014
                                        
                                        //insert ke detail
                                        
                                        
                                        String DtFromPeriod = String.valueOf(entriOpnameSaless.getDtFromPeriod());
                                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                                        Date pAwal = formatter.parse(DtFromPeriod);
                                            
                                        long diff = (entriOpnameSaless.getDtToPeriod().getTime()) - (entriOpnameSaless.getDtFromPeriod().getTime()); 
                                        long hariperiode = diff/(1000 * 60 * 60 * 24);
                                        
                                        String  sqlplus ="";
                                        
                                        for (int day = 1 ; (day<=hariperiode+1) ; day++){
                                            //cari nama nama employee yang berada di outlet pada tanggal periode Awal opname; 
                                            Vector daftarnama = PstEmployeeOutlet.carinama(entriOpnameSaless.getLocationId(),pAwal);
                                            
                                            long periodId = PstPeriod.cariPeriodIdnya(pAwal);
                                            //jika yang pertama tidak diisi union
                                            if (day == 1){
                                                    sqlplus = sqlplus + "SELECT e.`EMPLOYEE_ID` AS EMP_ID, \""+ pAwal +"\" AS P_DATE FROM "+PstEmpSchedule.TBL_HR_EMP_SCHEDULE+ " e WHERE e.D"+
                                                    pAwal.getDate() +" <> '504404559441803359' AND e.STATUS"+pAwal.getDate()+" NOT IN (0,2) AND e.`"+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_PERIOD_ID]+"` = "+ periodId +
                                                    " AND e." +PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID]+ " IN ( ";
                                                    //memasukan employeee id ke query
                                                    for (int jumEmp = 0 ; jumEmp < daftarnama.size(); jumEmp++ ){
                                                        EmployeeOutlet employeeOutlet = (EmployeeOutlet) daftarnama.get(jumEmp);
                                                        if (jumEmp == (daftarnama.size()-1)){
                                                            sqlplus = sqlplus + employeeOutlet.getEmployeeId();
                                                        } else {
                                                            sqlplus = sqlplus + employeeOutlet.getEmployeeId() + " , ";
                                                        }
                                                    }        
                                                sqlplus = sqlplus + " ) ";
                                                
                                            } else {
                                                    sqlplus = sqlplus + " UNION  SELECT e.`EMPLOYEE_ID` AS EMP_ID, \""+ pAwal +"\" AS P_DATE FROM "+PstEmpSchedule.TBL_HR_EMP_SCHEDULE+ " e WHERE e.D"+
                                                    pAwal.getDate() +" <> '504404559441803359' AND e.STATUS"+pAwal.getDate()+" NOT IN (0,2) AND e.`"+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_PERIOD_ID]+"` = "+ periodId +
                                                    " AND e." +PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID]+ " IN ( ";
                                                    //memasukan employee ke query
                                                    for (int jumEmp = 0 ; jumEmp < daftarnama.size(); jumEmp++ ){
                                                        EmployeeOutlet employeeOutlet = (EmployeeOutlet) daftarnama.get(jumEmp);
                                                       if (jumEmp == (daftarnama.size()-1)){
                                                            sqlplus = sqlplus + employeeOutlet.getEmployeeId();
                                                        } else {
                                                            sqlplus = sqlplus + employeeOutlet.getEmployeeId() + " , ";
                                                        }
                                                    }         
                                                sqlplus = sqlplus + " ) ";
                                            }
                                            pAwal.setDate(pAwal.getDate()+1);
                                    }                  
                                        
                                        Vector jumlahKerja = PstEmpSchedule.caricount(sqlplus);
                                        
                                        //cari hari free
                                        int harifree = PstConfigRewardAndPunishment.getdayfree(null);
                                        
                                        //cari total kerja
                                        long JtotalHarikerja = 0 ;
                                        for (int tKerja=0 ; tKerja<jumlahKerja.size(); tKerja++){
                                            SpecialEmpScheduleCount specialEmpScheduleCount = (SpecialEmpScheduleCount) jumlahKerja.get(tKerja);
                                            specialEmpScheduleCount.getCount();
                                            if (specialEmpScheduleCount.getCount()>harifree && harifree!=0){
                                            JtotalHarikerja = JtotalHarikerja + specialEmpScheduleCount.getCount();
                                            }
                                        }
                                        
                                        for (int i=0 ; i< jumlahKerja.size(); i++){
                                            SpecialEmpScheduleCount specialEmpScheduleCount = (SpecialEmpScheduleCount) jumlahKerja.get(i);
                                            //memastikan jumlah kerja melebihi 10 hari
                                            if ((specialEmpScheduleCount.getCount() > harifree ) && (specialEmpScheduleCount.getCount() !=0)){
                                                RewardnPunismentDetail rewardnPunismentDetail = new RewardnPunismentDetail();
                                                
                                                //memasukan data kariawan
                                                rewardnPunismentDetail.setRewardnPunismentMainId(rewardnPunismentMain.getOID());
                                                rewardnPunismentDetail.setEmployeeId(specialEmpScheduleCount.getEmployeeId());
                                                
                                                
                                                //mencari koefisien idString whereLoc= "EO."+PstEmployeeOutlet.fieldNames[PstEmployeeOutlet.FLD_LOCATION_ID] + " = " + entriOpnameSaless.getLocationId() +" AND RPM."+PstRewardAndPunishmentMain.fieldNames[PstRewardAndPunishmentMain.FLD_ENTRI_OPNAME_SALES_ID]+ " = "+entriOpnameSaless.getOID();
                                        
                                                String whereLoc= "EO."+PstEmployeeOutlet.fieldNames[PstEmployeeOutlet.FLD_LOCATION_ID] + " = " + entriOpnameSaless.getLocationId() +" AND RPM."+PstRewardAndPunishmentMain.fieldNames[PstRewardAndPunishmentMain.FLD_ENTRI_OPNAME_SALES_ID]+ " = "+entriOpnameSaless.getOID() +" AND EO."+PstEmployeeOutlet.fieldNames[PstEmployeeOutlet.FLD_EMPLOYEE_ID]+ " = "+specialEmpScheduleCount.getEmployeeId();
                                                long koefisienPositionId = PstRewardAndPunishmentDetail.getKoefisienPosition(0, 0, whereLoc, null,entriOpnameSaless.getDtFromPeriod(),entriOpnameSaless.getDtToPeriod());
                                        
                                                rewardnPunismentDetail.setKoefisienId(koefisienPositionId);
                                                rewardnPunismentDetail.setHariKerja((int) (specialEmpScheduleCount.getCount()));
                                                
                                                //mencari tipe toleransi
                                                String typetoleransi="";
                                                if ((entriOpnameSaless.getTypeTolerance()==0)){
                                                    typetoleransi=PstKoefisionPosition.fieldNames[PstKoefisionPosition.FLD_NILAI_KOEFISION_DC];
                                                } else{
                                                    typetoleransi=PstKoefisionPosition.fieldNames[PstKoefisionPosition.FLD_NILAI_KOEFISION_OUTLET];
                                                }
                                                //menentukan nilai koefisien berdasarkan type tolerance dan koefisien id
                                                String  wherekoefisienid= PstKoefisionPosition.fieldNames[PstKoefisionPosition.FLD_KOEFISION_POSITION_ID]+ " = " + koefisienPositionId;
                                                //mencari koefisien
                                                double nKoefisien = PstKoefisionPosition.koefisiennilai( wherekoefisienid, typetoleransi);
                                                
                                                //menghitung nilai total jumlah kerja * nilai koefisien
                                                int nilaiTotal= (int)(specialEmpScheduleCount.getCount()*nKoefisien);
                                                rewardnPunismentDetail.setTotal(nilaiTotal);
                                                
                                                //mencari absolut reward punishment
                                                double rewardorpunisment = Math.abs(entriOpnameSaless.getPlusMinus());
                                                double beban = (specialEmpScheduleCount.getCount()*rewardorpunisment)/JtotalHarikerja;
                                               // untuk perhitungan beban dan seterusnya akan dilakukan setelah mengetahui jumlah total kerja semua kariawan yang bekerja lebih dari 10 hari ;
                                                rewardnPunismentDetail.setBeban(beban);
                                                rewardnPunismentDetail.setTunai(0);
                                                rewardnPunismentDetail.setLamamasacicilan(0);
                                                rewardnPunismentDetail.setAdjusment(0);
                                                long rewardnP = PstRewardAndPunishmentDetail.insertExc(rewardnPunismentDetail);
                                            }
                                        }
                                        
//                                        
//                                        String whereLoc= "EO."+PstEmployeeOutlet.fieldNames[PstEmployeeOutlet.FLD_LOCATION_ID] + " = " + entriOpnameSaless.getLocationId() +" AND RPM."+PstRewardAndPunishmentMain.fieldNames[PstRewardAndPunishmentMain.FLD_ENTRI_OPNAME_SALES_ID]+ " = "+entriOpnameSaless.getOID();
//                                        
//                                        //menentukan banyak employee pada outlet yang bekerja sesuai periode yang ditentukan
//                                        Vector listEmployeeRewardPunishment = PstRewardAndPunishmentDetail.listRewardPunishmentDetailDistinct(0, 0, whereLoc, null,entriOpnameSaless.getDtFromPeriod(),entriOpnameSaless.getDtToPeriod());
//                                        
//                                        
//                                        
//                                        
//                                        
//                                        
//                                        
//                                        Vector listrpm = new Vector();
//                                        //meng kalkulasikan semua data sesuai periode opname
//                                        for (int i=0; i < listEmployeeRewardPunishment.size(); i++){
//                                        
//                                            SessRewardPunishmentDetail sessRewardPunishmentDetail= (SessRewardPunishmentDetail) listEmployeeRewardPunishment.get(i);
//                                            
//                                            //menghitung jumlah kerja setiap kariawan sesuai periode opname
//                                            long whereemployeeid= sessRewardPunishmentDetail.getEmployeeId();
//                                            long jmlharikerja = PstRewardAndPunishmentDetail.listRewardPunishmentDetailharikerja(0, 0, whereLoc, null,entriOpnameSaless.getDtFromPeriod(),entriOpnameSaless.getDtToPeriod(), whereemployeeid);
//                                            
//                                            //mencari jumlah hari freeday atau itungan hari untuk kariawan yang tidak ikut dalam reward dan punisment
//                                            int harifree = PstConfigRewardAndPunishment.getdayfree(null);
//                                            //memastikan jumlah kerja melebihi 10 hari
//                                            if ((jmlharikerja > harifree ) && (jmlharikerja !=0)){
//                                                RewardnPunismentDetail rewardnPunismentDetail = new RewardnPunismentDetail();
//                                                
//                                                //memasukan data kariawan
//                                                rewardnPunismentDetail.setRewardnPunismentMainId(sessRewardPunishmentDetail.getRewardnPunismentMainId());
//                                                rewardnPunismentDetail.setEmployeeId(sessRewardPunishmentDetail.getEmployeeId());
//                                                rewardnPunismentDetail.setKoefisienId(sessRewardPunishmentDetail.getKoefisienId());
//                                                
//                                                rewardnPunismentDetail.setHariKerja((int) jmlharikerja);
//                                             
//                                                //mencari tipe toleransi
//                                                String typetoleransi="";
//                                                if ((entriOpnameSaless.getTypeTolerance()==0)){
//                                                    typetoleransi=PstKoefisionPosition.fieldNames[PstKoefisionPosition.FLD_NILAI_KOEFISION_DC];
//                                                } else{
//                                                    typetoleransi=PstKoefisionPosition.fieldNames[PstKoefisionPosition.FLD_NILAI_KOEFISION_OUTLET];
//                                                }
//                                                //menentukan nilai koefisien berdasarkan type tolerance dan koefisien id
//                                                String  wherekoefisienid= PstKoefisionPosition.fieldNames[PstKoefisionPosition.FLD_KOEFISION_POSITION_ID]+ " = " + sessRewardPunishmentDetail.getKoefisienId();
//                                                //mencari koefisien
//                                                double nKoefisien = PstKoefisionPosition.koefisiennilai( wherekoefisienid, typetoleransi);
//                                                
//                                                //menghitung nilai total jumlah kerja * nilai koefisien
//                                                int nilaiTotal= (int)(jmlharikerja*nKoefisien);
//                                                rewardnPunismentDetail.setTotal(nilaiTotal);
//                                                
//                                               // untuk perhitungan beban dan seterusnya akan dilakukan setelah mengetahui jumlah total kerja semua kariawan yang bekerja lebih dari 10 hari ;
//                                                rewardnPunismentDetail.setBeban(0);
//                                                rewardnPunismentDetail.setTunai(0);
//                                                rewardnPunismentDetail.setLamamasacicilan(0);
//                                                jtotalkerja=jtotalkerja+jmlharikerja;
//                                                
//                                                listrpm.add(rewardnPunismentDetail);
//                                                //long rewardnP = PstRewardAndPunishmentDetail.insertExc(rewardnPunismentDetail);
//                                            }
//                                        }
//                                            //melakukan entri ke database dengan perhitungan beban
//                                            for (int datavalid = 0 ; datavalid < listrpm.size(); datavalid++){
//                                            
//                                                RewardnPunismentDetail rewardnPunismentDetail = (RewardnPunismentDetail) listrpm.get(datavalid);
//                                                
//                                                rewardnPunismentDetail.setRewardnPunismentMainId(rewardnPunismentDetail.getRewardnPunismentMainId());
//                                                rewardnPunismentDetail.setEmployeeId(rewardnPunismentDetail.getEmployeeId());
//                                                rewardnPunismentDetail.setKoefisienId(rewardnPunismentDetail.getKoefisienId());
//                                                rewardnPunismentDetail.setHariKerja(rewardnPunismentDetail.getHariKerja());
//                                                rewardnPunismentDetail.setTotal(rewardnPunismentDetail.getTotal());
//                                                
//                                                //mencari absolut reward punishment
//                                                double rewardorpunisment = Math.abs(entriOpnameSaless.getPlusMinus());
//                                                double beban = (rewardnPunismentDetail.getHariKerja()*rewardorpunisment)/jtotalkerja;
//                                                rewardnPunismentDetail.setBeban(beban);
//                                                rewardnPunismentDetail.setTunai(beban);
//                                                rewardnPunismentDetail.setLamamasacicilan(0);
//                                                
//                                                long rewardnP = PstRewardAndPunishmentDetail.insertExc(rewardnPunismentDetail);
//                                            }
//                               
//                                         
//                                        
                                        
                                        msgString = msgString + resultText[language][RSLT_OK];
                                        
                                    }
                                }catch(Exception exc){
                                    System.out.println("Exc update rewrd n punisment main"+exc);
                                    msgString = msgString + getSystemMessage(I_DBExceptionInfo.UNKNOWN) + " In Doc : " + rewardnPunismentMain.getDetailNbhNo();
                                }
                            }
                        } else {
                            //insert main
                            if (chekError == false) {
                                RewardnPunismentMain rewardnPunismentMain = new RewardnPunismentMain();
                                rewardnPunismentMain.setStatusDoc(I_DocStatus.DOCUMENT_STATUS_DRAFT);
                                rewardnPunismentMain.setDtCreateDocument(new Date());
                                rewardnPunismentMain.setJenisSoId(entriOpnameSaless.getJenisSoId());
                                rewardnPunismentMain.setLocationId(entriOpnameSaless.getLocationId());
                                rewardnPunismentMain.setNetSales(entriOpnameSaless.getNetSalesPeriod());
                                rewardnPunismentMain.setBarangHilang(entriOpnameSaless.getBarangHilang());
                                rewardnPunismentMain.setStatusOpname(entriOpnameSaless.getStatusOpname());
                                rewardnPunismentMain.setNilaiStatusOpname(entriOpnameSaless.getPlusMinus());
                                rewardnPunismentMain.setCreateFormMain(entriOpnameSaless.getCreateLocationName());
                                rewardnPunismentMain.setDtFromPeriod(entriOpnameSaless.getDtFromPeriod());
                                rewardnPunismentMain.setDtToPeriod(entriOpnameSaless.getDtToPeriod());
                                rewardnPunismentMain.setEntriOpnameId(entriOpnameSaless.getOID()); 
                                try {
                                    rewardnPunismentMain = PstRewardAndPunishmentMain.insertExcs(rewardnPunismentMain);
                                    if (rewardnPunismentMain.getOID() != 0) {
                                        //jika sukses insert main maka insert ke detail
                                        //priska 27-11-2014
                                        
                                        
                                        String DtFromPeriod = String.valueOf(entriOpnameSaless.getDtFromPeriod());
                                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                                        Date pAwal = formatter.parse(DtFromPeriod);
                                            
                                        long diff = (entriOpnameSaless.getDtToPeriod().getTime()) - (entriOpnameSaless.getDtFromPeriod().getTime()); 
                                        long hariperiode = diff/(1000 * 60 * 60 * 24);
                                        
                                        String  sqlplus ="";
                                        
                                        for (int day = 1 ; (day<=hariperiode+1) ; day++){
                                            //cari nama nama employee yang berada di outlet pada tanggal periode Awal opname; 
                                            Vector daftarnama = PstEmployeeOutlet.carinama(entriOpnameSaless.getLocationId(),pAwal);
                                            
                                            long periodId = PstPeriod.cariPeriodIdnya(pAwal);
                                            //jika yang pertama tidak diisi union
                                            if (day == 1){
                                                    sqlplus = sqlplus + "SELECT e.`EMPLOYEE_ID` AS EMP_ID, \""+ pAwal +"\" AS P_DATE FROM "+PstEmpSchedule.TBL_HR_EMP_SCHEDULE+ " e WHERE e.D"+
                                                    pAwal.getDate() +" <> '504404559441803359' AND e.STATUS"+pAwal.getDate()+" NOT IN (0,2) AND e.`"+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_PERIOD_ID]+"` = "+ periodId +
                                                    " AND e." +PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID]+ " IN ( ";
                                                    //memasukan employeee id ke query
                                                    for (int jumEmp = 0 ; jumEmp < daftarnama.size(); jumEmp++ ){
                                                        EmployeeOutlet employeeOutlet = (EmployeeOutlet) daftarnama.get(jumEmp);
                                                        if (jumEmp == (daftarnama.size()-1)){
                                                            sqlplus = sqlplus + employeeOutlet.getEmployeeId();
                                                        } else {
                                                            sqlplus = sqlplus + employeeOutlet.getEmployeeId() + " , ";
                                                        }
                                                    }        
                                                sqlplus = sqlplus + " ) ";
                                                
                                            } else {
                                                    sqlplus = sqlplus + " UNION  SELECT e.`EMPLOYEE_ID` AS EMP_ID, \""+ pAwal +"\" AS P_DATE FROM "+PstEmpSchedule.TBL_HR_EMP_SCHEDULE+ " e WHERE e.D"+
                                                    pAwal.getDate() +" <> '504404559441803359' AND e.STATUS"+pAwal.getDate()+" NOT IN (0,2) AND e.`"+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_PERIOD_ID]+"` = "+ periodId +
                                                    " AND e." +PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID]+ " IN ( ";
                                                    //memasukan employee ke query
                                                    for (int jumEmp = 0 ; jumEmp < daftarnama.size(); jumEmp++ ){
                                                        EmployeeOutlet employeeOutlet = (EmployeeOutlet) daftarnama.get(jumEmp);
                                                       if (jumEmp == (daftarnama.size()-1)){
                                                            sqlplus = sqlplus + employeeOutlet.getEmployeeId();
                                                        } else {
                                                            sqlplus = sqlplus + employeeOutlet.getEmployeeId() + " , ";
                                                        }
                                                    }         
                                                sqlplus = sqlplus + " ) ";
                                            }
                                            pAwal.setDate(pAwal.getDate()+1);
                                    }                  
                                        
                                        Vector jumlahKerja = PstEmpSchedule.caricount(sqlplus);
                                        
                                        //cari hari free
                                        int harifree = PstConfigRewardAndPunishment.getdayfree(null);
                                        
                                        //cari total kerja
                                        long JtotalHarikerja = 0 ;
                                        for (int tKerja=0 ; tKerja<jumlahKerja.size(); tKerja++){
                                            SpecialEmpScheduleCount specialEmpScheduleCount = (SpecialEmpScheduleCount) jumlahKerja.get(tKerja);
                                            specialEmpScheduleCount.getCount();
                                            if (specialEmpScheduleCount.getCount()>harifree && harifree!=0){
                                            JtotalHarikerja = JtotalHarikerja + specialEmpScheduleCount.getCount();
                                            }
                                        }
                                        
                                        for (int i=0 ; i< jumlahKerja.size(); i++){
                                            SpecialEmpScheduleCount specialEmpScheduleCount = (SpecialEmpScheduleCount) jumlahKerja.get(i);
                                            //memastikan jumlah kerja melebihi 10 hari
                                            if ((specialEmpScheduleCount.getCount() > harifree ) && (specialEmpScheduleCount.getCount() !=0)){
                                                RewardnPunismentDetail rewardnPunismentDetail = new RewardnPunismentDetail();
                                                
                                                //memasukan data kariawan
                                                rewardnPunismentDetail.setRewardnPunismentMainId(rewardnPunismentMain.getOID());
                                                rewardnPunismentDetail.setEmployeeId(specialEmpScheduleCount.getEmployeeId());
                                                
                                                
                                                //mencari koefisien idString whereLoc= "EO."+PstEmployeeOutlet.fieldNames[PstEmployeeOutlet.FLD_LOCATION_ID] + " = " + entriOpnameSaless.getLocationId() +" AND RPM."+PstRewardAndPunishmentMain.fieldNames[PstRewardAndPunishmentMain.FLD_ENTRI_OPNAME_SALES_ID]+ " = "+entriOpnameSaless.getOID();
                                        
                                                String whereLoc= "EO."+PstEmployeeOutlet.fieldNames[PstEmployeeOutlet.FLD_LOCATION_ID] + " = " + entriOpnameSaless.getLocationId() +" AND RPM."+PstRewardAndPunishmentMain.fieldNames[PstRewardAndPunishmentMain.FLD_ENTRI_OPNAME_SALES_ID]+ " = "+entriOpnameSaless.getOID() +" AND EO."+PstEmployeeOutlet.fieldNames[PstEmployeeOutlet.FLD_EMPLOYEE_ID]+ " = "+specialEmpScheduleCount.getEmployeeId();
                                                long koefisienPositionId = PstRewardAndPunishmentDetail.getKoefisienPosition(0, 0, whereLoc, null,entriOpnameSaless.getDtFromPeriod(),entriOpnameSaless.getDtToPeriod());
                                        
                                                rewardnPunismentDetail.setKoefisienId(koefisienPositionId);
                                                rewardnPunismentDetail.setHariKerja((int) (specialEmpScheduleCount.getCount()));
                                                
                                                //mencari tipe toleransi
                                                String typetoleransi="";
                                                if ((entriOpnameSaless.getTypeTolerance()==0)){
                                                    typetoleransi=PstKoefisionPosition.fieldNames[PstKoefisionPosition.FLD_NILAI_KOEFISION_DC];
                                                } else{
                                                    typetoleransi=PstKoefisionPosition.fieldNames[PstKoefisionPosition.FLD_NILAI_KOEFISION_OUTLET];
                                                }
                                                //menentukan nilai koefisien berdasarkan type tolerance dan koefisien id
                                                String  wherekoefisienid= PstKoefisionPosition.fieldNames[PstKoefisionPosition.FLD_KOEFISION_POSITION_ID]+ " = " + koefisienPositionId;
                                                //mencari koefisien
                                                double nKoefisien = PstKoefisionPosition.koefisiennilai( wherekoefisienid, typetoleransi);
                                                
                                                //menghitung nilai total jumlah kerja * nilai koefisien
                                                int nilaiTotal= (int)(specialEmpScheduleCount.getCount()*nKoefisien);
                                                rewardnPunismentDetail.setTotal(nilaiTotal);
                                                
                                                //mencari absolut reward punishment
                                                double rewardorpunisment = Math.abs(entriOpnameSaless.getPlusMinus());
                                                double beban = (specialEmpScheduleCount.getCount()*rewardorpunisment)/JtotalHarikerja;
                                               // untuk perhitungan beban dan seterusnya akan dilakukan setelah mengetahui jumlah total kerja semua kariawan yang bekerja lebih dari 10 hari ;
                                                rewardnPunismentDetail.setBeban(beban);
                                                rewardnPunismentDetail.setTunai(0);
                                                rewardnPunismentDetail.setLamamasacicilan(0);
                                                rewardnPunismentDetail.setAdjusment(0);
                                                long rewardnP = PstRewardAndPunishmentDetail.insertExc(rewardnPunismentDetail);
                                            }
                                        }
                                        
                                        
//                                        
//                                        
//                                        //insert ke detail
//                                        String whereLoc= "EO."+PstEmployeeOutlet.fieldNames[PstEmployeeOutlet.FLD_LOCATION_ID] + " = " + entriOpnameSaless.getLocationId() +" AND RPM."+PstRewardAndPunishmentMain.fieldNames[PstRewardAndPunishmentMain.FLD_ENTRI_OPNAME_SALES_ID]+ " = "+entriOpnameSaless.getOID();
//                                        
//                                        //menentukan banyak employee pada outlet yang bekerja sesuai periode yang ditentukan
//                                        Vector listEmployeeRewardPunishment = PstRewardAndPunishmentDetail.listRewardPunishmentDetailDistinct(0, 0, whereLoc, null,entriOpnameSaless.getDtFromPeriod(),entriOpnameSaless.getDtToPeriod());
//                                        
//                                        //mencari total bekerja semua kariawan pada periodeopname yang ditentukan
//                                    //    long totaljmlharikerja = PstRewardAndPunishmentDetail.listRewardPunishmentDetailharikerjatotal(0, 0, whereLoc, null,entriOpnameSaless.getDtFromPeriod(),entriOpnameSaless.getDtToPeriod());
//                                           
//                                        long jtotalkerja=0;
//                                        Vector listrpm = new Vector();
//                                        //meng kalkulasikan semua data sesuai periode opname
//                                        for (int i=0; i < listEmployeeRewardPunishment.size(); i++){
//                                        
//                                            SessRewardPunishmentDetail sessRewardPunishmentDetail= (SessRewardPunishmentDetail) listEmployeeRewardPunishment.get(i);
//                                            
//                                            //menghitung jumlah kerja setiap kariawan sesuai periode opname
//                                            long whereemployeeid= sessRewardPunishmentDetail.getEmployeeId();
//                                            long jmlharikerja = PstRewardAndPunishmentDetail.listRewardPunishmentDetailharikerja(0, 0, whereLoc, null,entriOpnameSaless.getDtFromPeriod(),entriOpnameSaless.getDtToPeriod(), whereemployeeid);
//                                            
//                                            //mencari jumlah hari freeday atau itungan hari untuk kariawan yang tidak ikut dalam reward dan punisment
//                                            int harifree = PstConfigRewardAndPunishment.getdayfree(null);
//                                            //memastikan jumlah kerja melebihi 10 hari
//                                            if ((jmlharikerja > harifree ) && (jmlharikerja !=0)){
//                                                RewardnPunismentDetail rewardnPunismentDetail = new RewardnPunismentDetail();
//                                                
//                                                //memasukan data kariawan
//                                                rewardnPunismentDetail.setRewardnPunismentMainId(sessRewardPunishmentDetail.getRewardnPunismentMainId());
//                                                rewardnPunismentDetail.setEmployeeId(sessRewardPunishmentDetail.getEmployeeId());
//                                                rewardnPunismentDetail.setKoefisienId(sessRewardPunishmentDetail.getKoefisienId());
//                                                
//                                                rewardnPunismentDetail.setHariKerja((int) jmlharikerja);
//                                             
//                                                //mencari tipe toleransi
//                                                String typetoleransi="";
//                                                if ((entriOpnameSaless.getTypeTolerance()==0)){
//                                                    typetoleransi=PstKoefisionPosition.fieldNames[PstKoefisionPosition.FLD_NILAI_KOEFISION_DC];
//                                                } else{
//                                                    typetoleransi=PstKoefisionPosition.fieldNames[PstKoefisionPosition.FLD_NILAI_KOEFISION_OUTLET];
//                                                }
//                                                //menentukan nilai koefisien berdasarkan type tolerance dan koefisien id
//                                                String  wherekoefisienid= PstKoefisionPosition.fieldNames[PstKoefisionPosition.FLD_KOEFISION_POSITION_ID]+ " = " + sessRewardPunishmentDetail.getKoefisienId();
//                                                //mencari koefisien
//                                                double nKoefisien = PstKoefisionPosition.koefisiennilai( wherekoefisienid, typetoleransi);
//                                                
//                                                //menghitung nilai total jumlah kerja * nilai koefisien
//                                                int nilaiTotal= (int)(jmlharikerja*nKoefisien);
//                                                rewardnPunismentDetail.setTotal(nilaiTotal);
//                                                
//                                               // untuk perhitungan beban dan seterusnya akan dilakukan setelah mengetahui jumlah total kerja semua kariawan yang bekerja lebih dari 10 hari ;
//                                                rewardnPunismentDetail.setBeban(0);
//                                                rewardnPunismentDetail.setTunai(0);
//                                                rewardnPunismentDetail.setLamamasacicilan(0);
//                                                jtotalkerja=jtotalkerja+jmlharikerja;
//                                                
//                                                listrpm.add(rewardnPunismentDetail);
//                                                //long rewardnP = PstRewardAndPunishmentDetail.insertExc(rewardnPunismentDetail);
//                                            }
//                                        }
//                                            //melakukan entri ke database dengan perhitungan beban
//                                            for (int datavalid = 0 ; datavalid < listrpm.size(); datavalid++){
//                                            
//                                                RewardnPunismentDetail rewardnPunismentDetail = (RewardnPunismentDetail) listrpm.get(datavalid);
//                                                
//                                                rewardnPunismentDetail.setRewardnPunismentMainId(rewardnPunismentDetail.getRewardnPunismentMainId());
//                                                rewardnPunismentDetail.setEmployeeId(rewardnPunismentDetail.getEmployeeId());
//                                                rewardnPunismentDetail.setKoefisienId(rewardnPunismentDetail.getKoefisienId());
//                                                rewardnPunismentDetail.setHariKerja(rewardnPunismentDetail.getHariKerja());
//                                                rewardnPunismentDetail.setTotal(rewardnPunismentDetail.getTotal());
//                                                
//                                                //mencari absolut reward punishment
//                                                double rewardorpunisment = Math.abs(entriOpnameSaless.getPlusMinus());
//                                                double beban = (rewardnPunismentDetail.getHariKerja()*rewardorpunisment)/jtotalkerja;
//                                                rewardnPunismentDetail.setBeban(beban);
//                                                rewardnPunismentDetail.setTunai(beban);
//                                                rewardnPunismentDetail.setLamamasacicilan(0);
//                                                
//                                                long rewardnP = PstRewardAndPunishmentDetail.insertExc(rewardnPunismentDetail);
//                                            }
//                               
                                         
                                        
                                        
                                        msgString = msgString + resultText[language][RSLT_OK];
                                        
                                    }
                                } catch (Exception e) {
                                    msgString = msgString + getSystemMessage(I_DBExceptionInfo.UNKNOWN) + " In Doc : " + rewardnPunismentMain.getDetailNbhNo();
                                }
                            }

                        }
                    } //loop frm 

                }
                break;
            case Command.ADD:
                break;
            case Command.SAVE:
                if (oidEntriOpname != 0) {
                    try {
                        entriOpnameSales = PstEntriOpnameSales.fetchExc(oidEntriOpname);
                    } catch (Exception exc) {
                    }
                }
                frmEntriOpnameSales.requestEntityObject(oidEntriOpname, entriOpnameSales, listEntriOpnameSales);
                if (frmEntriOpnameSales.errorSize() > 0) {
                    msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
                    return RSLT_FORM_INCOMPLETE;
                }
                String whereClasue = PstEntriOpnameSales.fieldNames[PstEntriOpnameSales.FLD_LOCATION_ID] + "=" + entriOpnameSales.getLocationId() + " AND " + PstEntriOpnameSales.fieldNames[PstEntriOpnameSales.FLD_ENTRI_OPNAME_SALES_ID] + "<>" + oidEntriOpname;
                Vector cekDataIsExist = PstEntriOpnameSales.list(0, 0, whereClasue, "");
                if (cekDataIsExist != null && cekDataIsExist.size() > 0) {
                    msgString = " Data Outlet AllReady Exist In the Period ";
                    return RSLT_EST_CODE_EXIST;
                }
                if (entriOpnameSales.getOID() == 0) {
                    try {
                        long oid = PstEntriOpnameSales.insertExc(this.entriOpnameSales);
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                        return getControlMsgId(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                        return getControlMsgId(I_DBExceptionInfo.UNKNOWN);
                    }
                } else {
                    try {
                        long oid = PstEntriOpnameSales.updateExc(this.entriOpnameSales);
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;
            case Command.EDIT:

                if (oidEntriOpname != 0) {

                    try {

                        entriOpnameSales = PstEntriOpnameSales.fetchExc(oidEntriOpname);

                    } catch (DBException dbexc) {

                        excCode = dbexc.getErrorCode();

                        msgString = getSystemMessage(excCode);

                    } catch (Exception exc) {

                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);

                    }

                }

                break;



            case Command.ASK:

                if (oidEntriOpname != 0) {

                    try {

                        entriOpnameSales = PstEntriOpnameSales.fetchExc(oidEntriOpname);

                    } catch (DBException dbexc) {

                        excCode = dbexc.getErrorCode();

                        msgString = getSystemMessage(excCode);

                    } catch (Exception exc) {

                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);

                    }

                }

                break;
            case Command.DELETE:
                if (oidEntriOpname != 0) {

                    try {
                        long oid = PstEntriOpnameSales.deleteExc(oidEntriOpname);
                        if (oid != 0) {
                            
                                //priska 2014-12-4 mencari main id untuk di delete pada detailnya
                                     long rewardPunismentMainId=PstRewardAndPunishmentMain.getMainIdWhereEntriOpname(oidEntriOpname);
                                     //menghapus semua data yang sudah diset pada detail untuk di set ulang
                                     long deletealldetail= PstRewardAndPunishmentDetail.deletewheremain(rewardPunismentMainId);
                                     long deletemain= PstRewardAndPunishmentMain.deleteExc(rewardPunismentMainId);
                            
                            msgString = FRMMessage.getMessage(FRMMessage.MSG_DELETED);
                            excCode = RSLT_OK;
                        } else {
                            msgString = FRMMessage.getMessage(FRMMessage.ERR_DELETED);
                            excCode = RSLT_FORM_INCOMPLETE;
                        }
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;

            default:
        }
        return rsCode;
    }
}
