package com.dimata.harisma.session.arap;

import com.dimata.qdep.db.DBResultSet;
import com.dimata.qdep.db.DBHandler;
import com.dimata.harisma.entity.search.SrcArApEntry;
import com.dimata.harisma.entity.arap.*;
import com.dimata.harisma.entity.employee.Employee;
import com.dimata.harisma.entity.employee.PstEmployee;
import com.dimata.harisma.entity.periode.PstPeriode;
import com.dimata.harisma.entity.periode.Periode;
import com.dimata.harisma.form.arap.CtrlArApMain;
import com.dimata.util.Formater;
import com.dimata.qdep.entity.I_DocStatus;

import java.sql.Connection;
import java.util.Vector;
import java.util.Date;
import java.util.Enumeration;
import java.sql.ResultSet;

/**
 * Created by IntelliJ IDEA.
 * User: nengock
 * Date: Oct 12, 2005
 * Time: 10:11:12 PM
 * To change this template use File | Settings | File Templates.
 */
public class SessArApEntry {
    public static final String SESS_SEARCH_ARAP_ENTRY = "SESS_SEARCH_ARAP_ENTRY";

    public static Vector listArApMain(SrcArApEntry srcArApEntry, int start, int recordToGet) {
        DBResultSet dbrs = null;
        Vector list = new Vector();
        try {
            String sql = "SELECT " +
                    " A." + PstArApMain.fieldNames[PstArApMain.FLD_ARAP_MAIN_ID] +
                    ", A." + PstArApMain.fieldNames[PstArApMain.FLD_VOUCHER_NO] +
                    ", A." + PstArApMain.fieldNames[PstArApMain.FLD_NOTA_NO] +
                    ", A." + PstArApMain.fieldNames[PstArApMain.FLD_VOUCHER_DATE] +
		    ", A." + PstArApMain.fieldNames[PstArApMain.FLD_NOTA_DATE] +
                    ", A." + PstArApMain.fieldNames[PstArApMain.FLD_AMOUNT] +
                    ", CL." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME] +
                    ", A." + PstArApMain.fieldNames[PstArApMain.FLD_RATE] +
                    ", A." + PstArApMain.fieldNames[PstArApMain.FLD_ARAP_DOC_STATUS] +
                    " FROM " + PstArApMain.TBL_ARAP_MAIN + " AS A " +
                    " INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " AS CL " +
                    " ON CL." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + " = A." + PstArApMain.fieldNames[PstArApMain.FLD_EMPLOYEE_ID];

            String where = "";
            String sLike = " LIKE ";
            if(DBHandler.DBSVR_TYPE == DBHandler.DBSVR_POSTGRESQL)
                sLike = " ILIKE ";
             
            if (srcArApEntry.getVoucherNo().length() > 0) {
                where = " A." + PstArApMain.fieldNames[PstArApMain.FLD_VOUCHER_NO] + sLike +"'%" + srcArApEntry.getVoucherNo() + "%'";
            }

            if (srcArApEntry.getNotaNo().length() > 0) {
                if (where.length() > 0) {
                    where = where + " AND A." + PstArApMain.fieldNames[PstArApMain.FLD_NOTA_NO]+ sLike + "'%" + srcArApEntry.getNotaNo() + "%'";
                } else {
                    where = " A." + PstArApMain.fieldNames[PstArApMain.FLD_NOTA_NO] + sLike + "'%" + srcArApEntry.getNotaNo() + "%'";
                }
            }


            if (where.length() > 0) {
                where = where + " AND A." + PstArApMain.fieldNames[PstArApMain.FLD_ARAP_TYPE] + " = " + srcArApEntry.getArApType();
            } else {
                where = " A." + PstArApMain.fieldNames[PstArApMain.FLD_ARAP_TYPE] + " = " + srcArApEntry.getArApType();
            }
            

            
            if (srcArApEntry.getDescription() != null && srcArApEntry.getDescription().length() >0 ) {
                if (where.length() > 0) {
                    where = where + " AND A." + PstArApMain.fieldNames[PstArApMain.FLD_DESCRIPTION] + " LIKE '%" + srcArApEntry.getDescription().trim() + "%'";
                } else {
                    where = " A." + PstArApMain.fieldNames[PstArApMain.FLD_DESCRIPTION] + " LIKE '%" + srcArApEntry.getDescription().trim() + "%'";
                }
            }
            
            
            if (srcArApEntry.getNominal() != 0) {
                if (where.length() > 0) {
                    where = where + " AND A." + PstArApMain.fieldNames[PstArApMain.FLD_AMOUNT] + " =" + srcArApEntry.getNominal();
                } else {
                    where = " A." + PstArApMain.fieldNames[PstArApMain.FLD_AMOUNT] +" =" + srcArApEntry.getNominal();
                }
            }
                       
          
            if (srcArApEntry.getContactName().length() > 0) {
                if (where.length() > 0) {
                        where = where + " AND CL." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]+ sLike +"'%" + srcArApEntry.getContactName() + "%'";
                } else {
                    where = " CL." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]+ sLike +"'%" + srcArApEntry.getContactName() + "%'";
                }
            }

            if (srcArApEntry.getType() != 0) {
                if (where.length() > 0) {
                    where = where + " AND A." + PstArApMain.fieldNames[PstArApMain.FLD_NOTA_DATE] + " BETWEEN '" + Formater.formatDate(srcArApEntry.getFromDate(), "yyyy-MM-dd") + "' AND '" + Formater.formatDate(srcArApEntry.getUntilDate(), "yyyy-MM-dd") + "'";
                } else {
                    where = " A." + PstArApMain.fieldNames[PstArApMain.FLD_NOTA_DATE] + " BETWEEN '" + Formater.formatDate(srcArApEntry.getFromDate(), "yyyy-MM-dd") + "' AND '" + Formater.formatDate(srcArApEntry.getUntilDate(), "yyyy-MM-dd") + "'";
                }
            }
            if (where.length() > 0)
                sql = sql + " WHERE " + where;

            sql = sql + " ORDER BY A." + PstArApMain.fieldNames[PstArApMain.FLD_VOUCHER_NO];
            if(start<0) { start =0; }
          //  System.out.println(sql);
            switch (DBHandler.DBSVR_TYPE) {
                case DBHandler.DBSVR_MYSQL:
                    if (start == 0 && recordToGet == 0)
                        sql = sql + "";
                    else
                        sql = sql + " LIMIT " + start + "," + recordToGet;
                    break;

                case DBHandler.DBSVR_POSTGRESQL:
                    if (start == 0 && recordToGet == 0)
                        sql = sql + "";
                    else
                        sql = sql + " LIMIT " + recordToGet + " OFFSET " + start;

                    break;

                case DBHandler.DBSVR_SYBASE:
                    break;

                case DBHandler.DBSVR_ORACLE:
                    break;

                case DBHandler.DBSVR_MSSQL:
                    break;

                default:
                    break;
            }

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                Vector vt = new Vector();
                ArApMain arApMain = new ArApMain();
                arApMain.setOID(rs.getLong(PstArApMain.fieldNames[PstArApMain.FLD_ARAP_MAIN_ID]));
                arApMain.setVoucherNo(rs.getString(PstArApMain.fieldNames[PstArApMain.FLD_VOUCHER_NO]));
                arApMain.setVoucherDate(rs.getDate(PstArApMain.fieldNames[PstArApMain.FLD_VOUCHER_DATE]));
		arApMain.setNotaDate(rs.getDate(PstArApMain.fieldNames[PstArApMain.FLD_NOTA_DATE]));
                arApMain.setNotaNo(rs.getString(PstArApMain.fieldNames[PstArApMain.FLD_NOTA_NO]));
                arApMain.setAmount(rs.getDouble(PstArApMain.fieldNames[PstArApMain.FLD_AMOUNT]));
                arApMain.setRate(rs.getDouble(PstArApMain.fieldNames[PstArApMain.FLD_RATE]));
                arApMain.setArApDocStatus(rs.getInt(PstArApMain.fieldNames[PstArApMain.FLD_ARAP_DOC_STATUS]));

                Employee employee = new Employee();
                employee.setFullName(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]));

                vt.add(arApMain);
                vt.add(employee);
                list.add(vt);
            }

        } catch (Exception e) {
            System.out.println("err.SessArApEntry - listArApEntry " + e.toString());
        } finally {
            DBResultSet.close(dbrs);
        }
        return list;
    }

    /**
     * gadnyana
     * untuk mencari jumlah row di daftar aktiva
     *
     * @param srcArApEntry
     * @return
     */
    public static int countArApMain(SrcArApEntry srcArApEntry) {
        DBResultSet dbrs = null;
        int count = 0;
        try {
            String sql = "SELECT COUNT(" +
                    " A." + PstArApMain.fieldNames[PstArApMain.FLD_ARAP_MAIN_ID] + ") AS CNT " +
                    " FROM " + PstArApMain.TBL_ARAP_MAIN + " AS A " +
                    " INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " AS CL " +
                    " ON CL." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + " = A." + PstArApMain.fieldNames[PstArApMain.FLD_EMPLOYEE_ID];

            String where = "";
            if (srcArApEntry.getVoucherNo().length() > 0) {
                where = " A." + PstArApMain.fieldNames[PstArApMain.FLD_VOUCHER_NO] + " LIKE '%" + srcArApEntry.getVoucherNo() + "%'";
            }


            if (srcArApEntry.getNotaNo().length() > 0) {
                if (where.length() > 0) {
                    where = where + " AND A." + PstArApMain.fieldNames[PstArApMain.FLD_NOTA_NO] + " LIKE '%" + srcArApEntry.getNotaNo() + "%'";
                } else {
                    where = " A." + PstArApMain.fieldNames[PstArApMain.FLD_NOTA_NO] + " LIKE '%" + srcArApEntry.getNotaNo() + "%'";
                }
            }

            if (where.length() > 0) {
                where = where + " AND A." + PstArApMain.fieldNames[PstArApMain.FLD_ARAP_TYPE] + " = " + srcArApEntry.getArApType();
            } else {
                where = " A." + PstArApMain.fieldNames[PstArApMain.FLD_ARAP_TYPE] + " = " + srcArApEntry.getArApType();
            }

            if (srcArApEntry.getContactName().length() > 0) {
                if (where.length() > 0) {
                    where = where + " AND CL." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME] + " LIKE '%" + srcArApEntry.getContactName() + "%'";
                } else {
                    where = " CL." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME] + " LIKE '%" + srcArApEntry.getContactName() + "%'";
                }
            }

            if (srcArApEntry.getType() != 0) {
                if (where.length() > 0) {
                    where = where + " AND A." + PstArApMain.fieldNames[PstArApMain.FLD_VOUCHER_DATE] + " BETWEEN '" + Formater.formatDate(srcArApEntry.getFromDate(), "yyyy-MM-dd") + "' AND '" + Formater.formatDate(srcArApEntry.getUntilDate(), "yyyy-MM-dd") + "'";
                } else {
                    where = " A." + PstArApMain.fieldNames[PstArApMain.FLD_VOUCHER_DATE] + " BETWEEN '" + Formater.formatDate(srcArApEntry.getFromDate(), "yyyy-MM-dd") + "' AND '" + Formater.formatDate(srcArApEntry.getUntilDate(), "yyyy-MM-dd") + "'";
                }
            }
   
            
            if (srcArApEntry.getDescription() != null && srcArApEntry.getDescription().length() >0 ) {
                if (where.length() > 0) {
                    where = where + " AND A." + PstArApMain.fieldNames[PstArApMain.FLD_DESCRIPTION] + " LIKE '%" + srcArApEntry.getDescription().trim() + "%'";
                } else {
                    where = " A." + PstArApMain.fieldNames[PstArApMain.FLD_DESCRIPTION] + " LIKE '%" + srcArApEntry.getDescription().trim() + "%'";
                }
            }
            
            
            if (srcArApEntry.getNominal() != 0) {
                if (where.length() > 0) {
                    where = where + " AND A." + PstArApMain.fieldNames[PstArApMain.FLD_AMOUNT] + " =" + srcArApEntry.getNominal();
                } else {
                    where = " A." + PstArApMain.fieldNames[PstArApMain.FLD_AMOUNT] +" =" + srcArApEntry.getNominal();
                }
            }
            
            
            if (where.length() > 0)
                sql = sql + " WHERE " + where;

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                count = rs.getInt(1);
            }

        } catch (Exception e) {
            System.out.println("err.SessArApEntry - countArApMain " + e.toString());
        } finally {
            DBResultSet.close(dbrs);
        }
        return count;
    }

    public int postingArApMain(long accountingBookType, long userOID, long currentPeriodOid, ArApMain arApMain, long journalId) {
        int result = 0;
        /**
         * object untuk jurnal umum, sekarang di create dari journal umum dan di link, supaya bisa multiple
         */
        
        if (arApMain != null && arApMain.getVoucherDate().compareTo(new Date()) <= 0 && ( journalId!=0 || arApMain.getJournalId()!=0) ) {
            /*JurnalUmum jurnalUmum = new JurnalUmum();
            jurnalUmum.setTglTransaksi(arApMain.getNotaDate());
            jurnalUmum.setTglEntry(arApMain.getVoucherDate());
            jurnalUmum.setKeterangan(arApMain.getDescription());
            jurnalUmum.setBookType(accountingBookType);
            jurnalUmum.setCurrType(arApMain.getIdCurrency());
            jurnalUmum.setUserId(userOID);
            jurnalUmum.setPeriodeId(currentPeriodOid);
            jurnalUmum.setReferenceDoc(arApMain.getVoucherNo());
            jurnalUmum.setJurnalType(I_JournalType.TIPE_JURNAL_UMUM);
            //String strVoucher = SessJurnal.generateVoucherNumber(currentPeriodOid, jurnalUmum.getTglTransaksi());
            //jurnalUmum.setSJurnalNumber(strVoucher);
            jurnalUmum.setContactOid(arApMain.getContactId());
            //jurnalUmum.setVoucherNo(strVoucher.substring(0, 4));
            //jurnalUmum.setVoucherCounter(Integer.parseInt(strVoucher.substring(5)));
            jurnalUmum.setDepartmentOid(PstSystemProperty.getPropertyLongbyName("SPECIAL_JOURNAL_DEPT"));
            
            
            
            JurnalDetail jurnaldetail = new JurnalDetail();
            jurnaldetail.setIndex(0);
            if (arApMain.getArApType() == PstArApMain.TYPE_AR)
                jurnaldetail.setIdPerkiraan(arApMain.getIdPerkiraan());
            else
                jurnaldetail.setIdPerkiraan(arApMain.getIdPerkiraanLawan());
            jurnaldetail.setDataStatus(PstJurnalDetail.DATASTATUS_ADD);
            jurnaldetail.setDebet(arApMain.getAmount());
            jurnaldetail.setCurrType(arApMain.getIdCurrency());
            jurnaldetail.setCurrAmount(arApMain.getRate());
            jurnaldetail.setNote(arApMain.getDescription());
            jurnaldetail.setKredit(0);
            jurnalUmum.addDetails(jurnaldetail.getIndex(), jurnaldetail);
            
             //* object untuk jurnal detail
            
            //Proses journal distribution
            
            Vector vJDistribution = (Vector)getJDistribution(arApMain);
            if(vJDistribution != null && vJDistribution.size() > 0){
                for(int i = 0; i < vJDistribution.size(); i++){
                    JournalDistribution objJDistribution = (JournalDistribution)vJDistribution.get(i);
                    jurnaldetail.addJDistributions(i, objJDistribution);
                }
            }
            
           
            // item lawan
            jurnaldetail = new JurnalDetail();
            jurnaldetail.setIndex(1);
            if (arApMain.getArApType() == PstArApMain.TYPE_AP)
                jurnaldetail.setIdPerkiraan(arApMain.getIdPerkiraan());
            else
                jurnaldetail.setIdPerkiraan(arApMain.getIdPerkiraanLawan());
            jurnaldetail.setDataStatus(PstJurnalDetail.DATASTATUS_ADD);
            jurnaldetail.setCurrType(arApMain.getIdCurrency());
            jurnaldetail.setCurrAmount(arApMain.getRate());            
            jurnaldetail.setNote(arApMain.getDescription());
            jurnaldetail.setDebet(0);
            jurnaldetail.setKredit(arApMain.getAmount());
            jurnalUmum.addDetails(jurnaldetail.getIndex(), jurnaldetail);
            */
            
            //CtrlJurnalUmum ctrlJurnalUmum = new CtrlJurnalUmum();
            try {                
                //result = ctrlJurnalUmum.JournalPosted(CtrlJurnalUmum.POSTED_JD, 0, jurnalUmum);
		//System.out.println("result 0 ::::::::::::::::::::::::::;; "+result);
                arApMain.setArApDocStatus(I_DocStatus.DOCUMENT_STATUS_POSTED);
                arApMain.setArApMainStatus(I_DocStatus.DOCUMENT_STATUS_POSTED);
                if ( arApMain.getJournalId()==0) { 
                    arApMain.setJournalId( journalId);
                }//jurnalUmum.getOID());
                PstArApMain.updateExc(arApMain);
            } catch (Exception e) {
                result = 1;
            }

        } else {
	    System.out.println("result 1 ::::::::::::::::::::::::::;; "+result);
            result = 1;
        }
	
	System.out.println("result 2 ::::::::::::::::::::::::::;; "+result);
        if (result == 1) {
            try {
                arApMain.setArApDocStatus(0);
                PstArApMain.updateExc(arApMain);
            } catch (Exception e) {
                System.out.println("err Fail Update ArApMain: " + e.toString());
            }
        }

        return result;
    }
    
   public static int postingArApPayment       (long accountingBookType, long userOID, long currentPeriodOid, ArApPayment arApPayment) {
        int result = 0;
        try {
            // cek tanggal transaksi dan entry

            Date startDate = null;
            Date finishDate = null;
            Date lastDate = null;
            Vector sessperiode = PstPeriode.getCurrPeriod();
            if (sessperiode != null && sessperiode.size() > 0) {
                Periode periode = (Periode) sessperiode.get(0);
                startDate = periode.getTglAwal();
                finishDate = periode.getTglAkhir();
                lastDate = periode.getTglAkhirEntry();
            }

            if (arApPayment != null && arApPayment.getPaymentDate().compareTo(startDate)>=0 && arApPayment.getPaymentDate().compareTo(finishDate)<=0) {
                String whereItem = PstArApItem.fieldNames[PstArApItem.FLD_ARAP_MAIN_ID] + "=" + arApPayment.getArapMainId() +
                        " AND " + PstArApItem.fieldNames[PstArApItem.FLD_SELLING_AKTIVA_ID] + "=" + arApPayment.getSellingAktivaId() +
                        " AND " + PstArApItem.fieldNames[PstArApItem.FLD_RECEIVE_AKTIVA_ID] + "=" + arApPayment.getReceiveAktivaId() +
                        " AND " + PstArApItem.fieldNames[PstArApItem.FLD_LEFT_TO_PAY] + " > 0";
                String orderItem = PstArApItem.fieldNames[PstArApItem.FLD_DUE_DATE];
                Vector vectItem = PstArApItem.list(0, 0, whereItem, orderItem);
                Enumeration enumItem = vectItem.elements();
                double dPay = arApPayment.getAmount();
                double dItem = 0;
                boolean isClose = false;
                while (enumItem.hasMoreElements() && dPay > 0) {
                    ArApItem item = (ArApItem) enumItem.nextElement();

                    if (dPay > item.getLeftToPay()) {
                        dPay = dPay - item.getLeftToPay();
                        item.setLeftToPay(0);
                        item.setArApItemStatus(PstArApMain.STATUS_CLOSED);
                    } else {
                        item.setLeftToPay(item.getLeftToPay() - dPay);
                        dPay = 0;
                    }
                    dItem = item.getLeftToPay();
                    try {
                        PstArApItem.updateExc(item);
                    } catch (Exception e) {
                        System.out.println("err on update item: " + e.toString());
                        result = 1;
                    }
                }
                if (!enumItem.hasMoreElements()) {
                    isClose = (dItem == 0);
                }

                if (arApPayment.getLeftToPay() > arApPayment.getAmount()) {
                    arApPayment.setLeftToPay(arApPayment.getLeftToPay() - arApPayment.getAmount());
                } else {
                    arApPayment.setLeftToPay(0);
                }
                long idPerkiraanMain = 0;
                String description = "Payment ";
                

                PstArApPayment.createOrderNomor(arApPayment);
                

                
            } else {
                result = CtrlArApMain.RSLT_ERR_TRANS_DATE;
            }
        } catch (Exception e) {
            System.out.println("err on Posting Payment: " + e.toString());
            result = CtrlArApMain.RSLT_UNKNOWN;
        }

        return result;
    }

public static synchronized void updateArapPayment(long lArapPaymentId, ArApPayment oArApPayment){
    int iResult = 0;
    int iUpdateArapPayment = 0;
    long lUpdateArapItem = 0;
    int iUpdateJournalMain = 0;
    int iUpdateJournalDetail = 0;
    long lIdJournalMain = 0;
    ArApMain objArApMain = new ArApMain();
    ArApPayment objArApPayment = new ArApPayment();
    if(lArapPaymentId != 0){
	try{
	    objArApPayment = PstArApPayment.fetchExc(lArapPaymentId);
	}catch(Exception e){}
    }
    
    if(objArApPayment.getArapMainId() != 0){
	try{
	    objArApMain = PstArApMain.fetchExc(objArApPayment.getArapMainId());
	}catch(Exception e){}
	double dLeftToPay = 0;		
	try{	    
	    objArApPayment.setPaymentDate(oArApPayment.getPaymentDate());
	    objArApPayment.setAmount(oArApPayment.getAmount());
	    objArApPayment.setIdCurrency(oArApPayment.getIdCurrency());
	    objArApPayment.setIdPerkiraanPayment(oArApPayment.getIdPerkiraanPayment());
	    objArApPayment.setRate(oArApPayment.getRate());
	    long lUpdate = PstArApPayment.updateExc(objArApPayment);
	    iUpdateArapPayment = 1;
	    if(iUpdateArapPayment == 1){
		dLeftToPay = getLeftToPay(objArApMain, objArApPayment, 0);	
		objArApPayment.setLeftToPay(dLeftToPay);
		long lNextUpdate = PstArApPayment.updateExc(objArApPayment);
	    }
	}catch(Exception e){
	    //System.out.println("Exception on updateArapPayment ::::: "+e.toString());
	}
	
	if(iUpdateArapPayment == 1){
	    try{
		lUpdateArapItem = updateArapItem(objArApPayment.getArapMainId(), dLeftToPay);
	    }catch(Exception e){
		//System.out.println("Exception on updateArapItem ::::: "+e.toString());
	    }
	}

    }    
   
}

/*
 try {            
        Connection con = makeDatabaseConnection();
        con.setAutoCommit(false);           
        //  Do whatever database transaction functionality
        //  is necessary           
        con.commit();          
    } catch (Exception ex) {
        try {
            con.rollback();
        } catch (SQLException sqx) {
            throw new EJBException("Rollback failed: " +
                sqx.getMessage());
        }
    } finally {
        releaseDatabaseConnection();
    }
 */

public static synchronized void deleteArapPayment(long lIdArapPayment){
    Connection con = DBHandler.getDBConnection();
    ArApPayment objArApPayment = new ArApPayment();
    ArApPayment oArApPayment = new ArApPayment();
    ArApMain objArApMain = new ArApMain();
    double dPaymentToDelete = 0;
    double dLeftToPay = 0;
    if(lIdArapPayment != 0){
	try{
	    objArApPayment = PstArApPayment.fetchExc(lIdArapPayment);	    
	    dPaymentToDelete = objArApPayment.getAmount();
	}catch(Exception e){}
    }
    
    if(objArApPayment.getArapMainId() != 0){
	try{
	    objArApMain = PstArApMain.fetchExc(objArApPayment.getArapMainId());
	}catch(Exception e){}
    }
    
    try{
	long lUpdateDB = 0;
	con.setAutoCommit(false);
	lUpdateDB = PstArApPayment.deleteExc(lIdArapPayment,con);
	
	long lIdJournalMain = 0;
	
	oArApPayment = (ArApPayment)getObjArApPayment(objArApMain.getOID());	
	dLeftToPay = getLeftToPay(objArApMain, objArApPayment /* oArApPayment*/, 1);
	//System.out.println("dLeftToPay ::::::: "+dLeftToPay);
	if(objArApMain.getOID() != 0){	    
	    //oArApPayment.setLeftToPay(dLeftToPay);
	    try{
		//lUpdateDB = PstArApPayment.updateExc(oArApPayment, con);
		lUpdateDB = updateArapItem(objArApMain.getOID(), dLeftToPay, con);
	    }catch(Exception e){}
	}
	
	if(lUpdateDB != 0){
	    con.commit();
	}else{
	    con.rollback();
	}
    }catch(Exception e){}
}

public static synchronized Vector getListObjArapPayment(long lArapMainId){
    DBResultSet dbrs = null;
    Vector vResult = new Vector(1,1);
    try{
	String sql = " SELECT "+PstArApPayment.fieldNames[PstArApPayment.FLD_ARAP_PAYMENT_ID]+
		     " FROM "+PstArApPayment.TBL_ARAP_PAYMENT+
		     " WHERE "+PstArApPayment.fieldNames[PstArApPayment.FLD_ARAP_MAIN_ID]+
		     " = "+lArapMainId+
		     " ORDER BY "+PstArApPayment.fieldNames[PstArApPayment.FLD_ARAP_PAYMENT_ID]+" DESC ";
	
	//System.out.println("SQL SessArApEntry.getListObjArapPayment :::::::: "+sql);
	dbrs = DBHandler.execQueryResult(sql);
	ResultSet rs = dbrs.getResultSet();
	while(rs.next()){
	    ArApPayment objArApPayment = new ArApPayment();
	    long lArapPaymentId = 0;
	    lArapPaymentId = rs.getLong(PstArApPayment.fieldNames[PstArApPayment.FLD_ARAP_PAYMENT_ID]);
	    if(lArapPaymentId != 0){
		objArApPayment = PstArApPayment.fetchExc(lArapPaymentId);
		vResult.add(objArApPayment);
	    }	    
	}
    }catch(Exception e){}
    return vResult;
}

public static synchronized ArApPayment getObjArApPayment(long lArapMainId){
    ArApPayment objArApPayment = new ArApPayment();
    Vector vListObjArapPayment = new Vector(1,1);
    if(lArapMainId != 0){
	vListObjArapPayment = getListObjArapPayment(lArapMainId);	
    }    
    if(vListObjArapPayment != null && vListObjArapPayment.size() > 0){
	for(int i = 0; i < vListObjArapPayment.size(); i++){
	    objArApPayment = (ArApPayment)vListObjArapPayment.get(0);	   
	}
    }
    return objArApPayment;
}

public static synchronized double getTotalPayment(ArApPayment objArApPayment, int iUpdate){
    DBResultSet dbrs = null;
    double dResult = 0;
    if(objArApPayment.getArapMainId() != 0){
	try{
	    String sql = " SELECT SUM("+PstArApPayment.fieldNames[PstArApPayment.FLD_AMOUNT]+") AS AMOUNT "+
			 " FROM "+PstArApPayment.TBL_ARAP_PAYMENT+
			 " WHERE "+PstArApPayment.fieldNames[PstArApPayment.FLD_ARAP_MAIN_ID]+
			 " = "+objArApPayment.getArapMainId();
	     if(iUpdate == 1){
		     sql += " AND "+PstArApPayment.fieldNames[PstArApPayment.FLD_ARAP_PAYMENT_ID]+	   
			    " != "+objArApPayment.getOID();
	    }
	    
	    //System.out.println("SQL SessArApEntry.getTotalPayment ::::::::::::: "+sql);
	    dbrs = DBHandler.execQueryResult(sql);
	    ResultSet rs = dbrs.getResultSet();
	    while(rs.next()){
		dResult = rs.getDouble("AMOUNT");
	    }
	    rs.close();
	}catch(Exception e){}
    }
    return dResult;
}

public static synchronized double getLeftToPay(ArApMain objArApMain, ArApPayment objArApPayment, int iUpdate){
    double dResult = 0;
    double dArApAmount = 0;
    double dTotalPayment = 0;
    
    //System.out.println("objArApPayment.getArapMainId() ::::::::::::: "+objArApPayment.getArapMainId());
    if(objArApPayment.getArapMainId() != 0){
	dTotalPayment = getTotalPayment(objArApPayment, iUpdate);
	//System.out.println("dTotalPayment ::::::::::::: "+dTotalPayment);
    }
    
    //System.out.println("objArApMain.getAmount() ::::::::::::: "+objArApMain.getAmount());
    if(objArApMain.getAmount() > 0){
	dArApAmount = objArApMain.getAmount();
    }
    dResult = dArApAmount - dTotalPayment;
    if(dResult == 0){
	dResult = dArApAmount;
    }
    return dResult;
}

public static synchronized Vector getListObjArapItem(long lArapMainId){
    DBResultSet dbrs = null;
    Vector vResult = new Vector(1,1);    
    
    if(lArapMainId != 0){
	try{
	    String sql = " SELECT "+PstArApItem.fieldNames[PstArApItem.FLD_ARAP_ITEM_ID]+
			 " FROM "+PstArApItem.TBL_ARAP_ITEM+
			 " WHERE "+PstArApItem.fieldNames[PstArApItem.FLD_ARAP_MAIN_ID]+
			 " = "+lArapMainId+
			 " ORDER BY "+PstArApItem.fieldNames[PstArApItem.FLD_ARAP_ITEM_ID]+" DESC ";
	    
	    //System.out.println("SQL SessArApEntry.getListObjArapItem ::::::::::: "+sql);
	    dbrs = DBHandler.execQueryResult(sql);
	    ResultSet rs = dbrs.getResultSet();
	    while(rs.next()){
		long lIdArapItem = 0;
		ArApItem objArApItem = new ArApItem();
		lIdArapItem = rs.getLong(PstArApItem.fieldNames[PstArApItem.FLD_ARAP_ITEM_ID]);
		if(lIdArapItem != 0){
		    try{
			objArApItem = PstArApItem.fetchExc(lIdArapItem);
			vResult.add(objArApItem);
		    }catch(Exception e){}
		}
	    }
	    rs.close();
	}catch(Exception e){}
    }
    return vResult;
}

public static synchronized ArApItem getObjArapItem(long lArapMainId){
    ArApItem objArApItem = new ArApItem();
    Vector vListArapItem = new Vector(1,1);
    vListArapItem = (Vector)getListObjArapItem(lArapMainId);   
    if(vListArapItem != null && vListArapItem.size() > 0){
	for(int i = 0; i < vListArapItem.size(); i++){
	    objArApItem = (ArApItem)vListArapItem.get(0);
	}
    }
    return objArApItem;
}

public static synchronized long updateArapItem(long lArapMainId, double dLeftToPay){
    return updateArapItem(lArapMainId, dLeftToPay,null);
}

public static synchronized long updateArapItem(long lArapMainId, double dLeftToPay, Connection con){
    long lResult = 0;
    ArApItem objArApItem = new ArApItem();
    try{
        Vector vListArapItem = (Vector)getListObjArapItem(lArapMainId);   	
        if(vListArapItem != null && vListArapItem.size() > 0){
	for(int i = 0; i < vListArapItem.size(); i++){        
            objArApItem = (ArApItem)vListArapItem.get(i); ;	
            if(objArApItem != null && dLeftToPay>=0 ){                
                if(dLeftToPay<= objArApItem.getAngsuran()){
                    objArApItem.setLeftToPay(dLeftToPay);                    
                    dLeftToPay =0;
                } else{
                    dLeftToPay = dLeftToPay - objArApItem.getAngsuran();
                    if(dLeftToPay<0)
                        dLeftToPay=0;
                    objArApItem.setLeftToPay(objArApItem.getAngsuran());                    
                }
                try{
                    if(con == null){
                        lResult = PstArApItem.updateExc(objArApItem);
                    }else{
                        lResult = PstArApItem.updateExc(objArApItem, con);
                    }
                }catch(Exception e){}                
            } 
         }
        }
    }catch(Exception e){}
    return lResult;
}

public static synchronized String getStringDelete(String strTblName, String StrwhClause, long lOid){
    String sResult = "";
    try{
	sResult = " DELETE FROM "+strTblName+" WHERE "+StrwhClause+" = "+lOid;
    }catch(Exception e){}
    return sResult;
}


}
