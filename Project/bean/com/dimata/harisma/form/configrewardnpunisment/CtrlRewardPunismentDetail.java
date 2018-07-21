/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.form.configrewardnpunisment;

import com.dimata.harisma.entity.configrewardnpunisment.PstEntriOpnameSales;
import com.dimata.harisma.entity.configrewardnpunisment.PstRewardAndPunishmentDetail;
import com.dimata.harisma.entity.configrewardnpunisment.PstRewardAndPunishmentMain;
import com.dimata.harisma.entity.configrewardnpunisment.RewardnPunismentDetail;
import com.dimata.harisma.entity.koefisionposition.PstKoefisionPosition;
import com.dimata.qdep.db.DBException;
import com.dimata.qdep.form.Control;
import com.dimata.qdep.form.FRMMessage;
import com.dimata.qdep.system.I_DBExceptionInfo;
import com.dimata.util.Command;
import com.dimata.util.lang.I_Language;
import java.util.ResourceBundle;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author PRISKA
 */
public class CtrlRewardPunismentDetail extends Control implements I_Language  {
    public static int RSLT_OK = 0;
    public static int RSLT_UNKNOWN_ERROR = 1;
    public static int RSLT_EST_CODE_EXIST = 2;
    public static int RSLT_FORM_INCOMPLETE = 3;

    public static String[][] resultText ={
        {"Berhasil","Tidak Bisa Di Prosess","kode Sudah Ada","Data Belum Lengkap"},
        {"Succes", "Can not process", "Estimation code exist", "Data incomplete"}
    };

    private int start;
    private String msgString;
    private RewardnPunismentDetail rewardnPunismentDetail;
    private PstRewardAndPunishmentDetail pstRewardAndPunishmentDetail;
    private FrmRewardPunismentDetail frmRewardPunismentDetail;
    int language = LANGUAGE_DEFAULT;

    public CtrlRewardPunismentDetail (HttpServletRequest request){
                msgString = "";
                rewardnPunismentDetail = new RewardnPunismentDetail();
                try{
                     pstRewardAndPunishmentDetail = new PstRewardAndPunishmentDetail(0);
                }catch(Exception e){
                    System.out.println("Exception"+e);
                   
                }
		frmRewardPunismentDetail = new FrmRewardPunismentDetail(request, rewardnPunismentDetail);
	}
    private String getSystemMessage(int msgCode){
		switch (msgCode){
			case I_DBExceptionInfo.MULTIPLE_ID :
                        this.frmRewardPunismentDetail.addError(FrmRewardPunismentDetail.FRM_FLD_REWARD_PUNISMENT_DETAIL_ID,                                        
                             resultText[language][RSLT_EST_CODE_EXIST] );
				return resultText[language][RSLT_EST_CODE_EXIST];
			default:
				return resultText[language][RSLT_UNKNOWN_ERROR];
		}

	}
    private int getControlMsgId(int msgCode){
            switch (msgCode){
                case I_DBExceptionInfo.MULTIPLE_ID :
                        return RSLT_EST_CODE_EXIST;
                        default:
                            return RSLT_UNKNOWN_ERROR;
		}
	}
    public int getLanguage(){ return language; }
    public void setLanguage(int language){ this.language = language; }
    public RewardnPunismentDetail getRewardnPunismentDetail() { return rewardnPunismentDetail; }
    public FrmRewardPunismentDetail getForm() { return frmRewardPunismentDetail; }
    public String getMessage(){ return msgString; }
    public int getStart() { return start; }
    
    public int action(int cmd , long oidrewardpunismentdetail , Vector listrewardpunisment) throws DBException{
		msgString = "";
		int excCode = I_DBExceptionInfo.NO_EXCEPTION;
		int rsCode = RSLT_OK;
                switch(cmd){
			case Command.ADD :
				break;
                        case Command.SAVE :
				if(oidrewardpunismentdetail != 0){
					try{
						rewardnPunismentDetail = pstRewardAndPunishmentDetail.fetchExc(oidrewardpunismentdetail);
					}catch(Exception exc){}
				}
				frmRewardPunismentDetail.requestEntityObject(rewardnPunismentDetail);
				if(frmRewardPunismentDetail.errorSize()>0) {
					msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
                                    return RSLT_FORM_INCOMPLETE ;
				}
				if(rewardnPunismentDetail.getOID()==0){
					try{
						long oid = pstRewardAndPunishmentDetail.insertExc(this.rewardnPunismentDetail);
					}catch(DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
						return getControlMsgId(excCode);
					}catch (Exception exc){
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
						return getControlMsgId(I_DBExceptionInfo.UNKNOWN);
					}
				}else{
					try {
						long oid = pstRewardAndPunishmentDetail.updateExc(this.rewardnPunismentDetail);
					}catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					}catch (Exception exc){
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
					}
				}
				break;
			case Command.EDIT :

				if (oidrewardpunismentdetail  != 0) {

					try {

						rewardnPunismentDetail = pstRewardAndPunishmentDetail.fetchExc(oidrewardpunismentdetail);

					} catch (DBException dbexc){

						excCode = dbexc.getErrorCode();

						msgString = getSystemMessage(excCode);

					} catch (Exception exc){

						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);

					}

				}

				break;



			case Command.ASK :

				if (oidrewardpunismentdetail != 0) {

					try {

						rewardnPunismentDetail = pstRewardAndPunishmentDetail.fetchExc(oidrewardpunismentdetail);

					} catch (DBException dbexc){

						excCode = dbexc.getErrorCode();

						msgString = getSystemMessage(excCode);

					} catch (Exception exc){

						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);

					}

				}

				break;
			case Command.DELETE :
				if (oidrewardpunismentdetail!= 0){

					try{
						long oid = pstRewardAndPunishmentDetail.deleteExc(oidrewardpunismentdetail);
				if(oid!=0){
                                        msgString = FRMMessage.getMessage(FRMMessage.MSG_DELETED);
                                        excCode = RSLT_OK;
				}else{
				msgString = FRMMessage.getMessage(FRMMessage.ERR_DELETED);
							excCode = RSLT_FORM_INCOMPLETE;
				}
					}catch(DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					}catch(Exception exc){
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
					}
				}
				break;
                            
                            //priska 2014-12-08
                            case Command.POST :
                                    frmRewardPunismentDetail.requestEntityObject(rewardnPunismentDetail);
                                    //oidrewardpunismentdetail;
                                    int nilaiAdjusment = rewardnPunismentDetail.getAdjusment();
                                    if(rewardnPunismentDetail.getRewardnPunismentMainId() !=0 && rewardnPunismentDetail.getAdjusment()!=0){
                                        String where = PstRewardAndPunishmentDetail.fieldNames[PstRewardAndPunishmentDetail.FLD_REWARD_PUNISMENT_MAIN_ID] +" = " + rewardnPunismentDetail.getRewardnPunismentMainId() ; 
                                        Vector listrpd = PstRewardAndPunishmentDetail.list(start, rsCode, where, msgString);
                                        
                                        //jumlah total baru
                                        double jumlahtotal = 0 ;
                                        
                                        //variabel untuk total yang baru pada detail sesuai rpd id
                                        double totalbaru = 0;
                                        
                                        //cari total ulang
                                        for (int i=0 ; i < listrpd.size(); i++){
                                            RewardnPunismentDetail rewardnPunismentDetail = (RewardnPunismentDetail) listrpd.get(i);
                                             if (oidrewardpunismentdetail == rewardnPunismentDetail.getOID() && oidrewardpunismentdetail!=0){
                                                 
                                                   //mencari tipe toleransi
                                                String wherecla = " EOS."+ PstEntriOpnameSales.fieldNames[PstEntriOpnameSales.FLD_ENTRI_OPNAME_SALES_ID] + " = RPM." + PstRewardAndPunishmentMain.fieldNames[PstRewardAndPunishmentMain.FLD_ENTRI_OPNAME_SALES_ID] + " AND RPM." + PstRewardAndPunishmentMain.fieldNames[PstRewardAndPunishmentMain.FLD_REWARD_PUNISMENT_MAIN_ID] + " = " +rewardnPunismentDetail.getRewardnPunismentMainId() ; 
                                                long type = PstEntriOpnameSales.gettypetoleransi(wherecla);
                                                String typetoleransi="";
                                                if ((type==0)){
                                                    typetoleransi=PstKoefisionPosition.fieldNames[PstKoefisionPosition.FLD_NILAI_KOEFISION_DC];
                                                } else{
                                                    typetoleransi=PstKoefisionPosition.fieldNames[PstKoefisionPosition.FLD_NILAI_KOEFISION_OUTLET];
                                                }
                                                //menentukan nilai koefisien berdasarkan type tolerance dan koefisien id
                                                String  wherekoefisienid = PstKoefisionPosition.fieldNames[PstKoefisionPosition.FLD_KOEFISION_POSITION_ID]+ " = " + rewardnPunismentDetail.getKoefisienId();
                                                //mencari koefisien
                                                double nKoefisien = PstKoefisionPosition.koefisiennilai( wherekoefisienid, typetoleransi);
                                                
                                                jumlahtotal=jumlahtotal +  (nKoefisien*(rewardnPunismentDetail.getHariKerja()+nilaiAdjusment));
                                                totalbaru = (nKoefisien*(rewardnPunismentDetail.getHariKerja()+nilaiAdjusment));
                                                
                                             } else {
                                                 jumlahtotal = jumlahtotal + rewardnPunismentDetail.getTotal();
                                             }
                                       }
                                        
                                        String wheremain =  PstRewardAndPunishmentMain.fieldNames[PstRewardAndPunishmentMain.FLD_REWARD_PUNISMENT_MAIN_ID] + " = " + rewardnPunismentDetail.getRewardnPunismentMainId();
                                        double nilaiopname =  PstEntriOpnameSales.getnilaiopname(wheremain);
                                        //set beban ulang
                                       for (int i=0 ; i < listrpd.size(); i++){
                                           RewardnPunismentDetail rewardnPunismentDetail = (RewardnPunismentDetail) listrpd.get(i);
                                           
                                           
                                           if (oidrewardpunismentdetail == rewardnPunismentDetail.getOID() && oidrewardpunismentdetail!=0){
                                               
                                               
                                               rewardnPunismentDetail.setTotal(totalbaru);
                                               double nilaiopnameabs = Math.abs(nilaiopname);
                                               double beban = (totalbaru*nilaiopnameabs)/jumlahtotal;
                                               rewardnPunismentDetail.setBeban(beban);
                                               rewardnPunismentDetail.setAdjusment(nilaiAdjusment);
                                               long rewardnP = PstRewardAndPunishmentDetail.updateExc(rewardnPunismentDetail);
                                           } else {
                                               double nilaiopnameabs = Math.abs(nilaiopname);
                                               double beban = (rewardnPunismentDetail.getTotal()*nilaiopnameabs)/jumlahtotal;
                                               rewardnPunismentDetail.setBeban(beban);
                                               
                                               long rewardnP = PstRewardAndPunishmentDetail.updateExc(rewardnPunismentDetail);
                                           }
                                       } 
                                        
                                    }
                                    
                                
				
                                
				break;
                            
                            
			default :
		}
		return rsCode;
	}
}
