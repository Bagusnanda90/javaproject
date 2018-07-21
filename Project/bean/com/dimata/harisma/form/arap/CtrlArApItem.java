package com.dimata.harisma.form.arap;

import javax.servlet.http.*;

import com.dimata.util.*;
import com.dimata.util.lang.I_Language;
import com.dimata.harisma.entity.arap.PstArApItem;
import com.dimata.harisma.entity.arap.ArApItem;
import com.dimata.harisma.entity.arap.ArApMain;
import com.dimata.harisma.entity.arap.PstArApMain;
import com.dimata.harisma.entity.masterdata.Period;
import com.dimata.harisma.entity.masterdata.PstPeriod;
import com.dimata.harisma.entity.payroll.PayComponent;
import com.dimata.harisma.entity.payroll.PstPayComponent;
import com.dimata.qdep.form.Control;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

public class CtrlArApItem extends Control implements I_Language {

    public static final int RSLT_OK = 0;
    public static final int RSLT_PAYMENT_NULL = 1;
    public static final int RSLT_DESCRIPTION = 2;
    public static final int RSLT_INCOMPLETE = 3; 
    public static final int RSLT_UNKNOWN = 4;
    public static String resultText[][] = {
        {"OK", "Angsuran belum diinput", "Keterangan belum diinput", "Data belum diinputkan","System menemukan kesalahan. Silahkan entry data kembali"},
        {"OK", "Credit term value is required", "Description is required", "Form incomplete","Unidentification error.Pleas try again"}
    };


    private int start;
    private String msgString;
    private ArApItem arap;
    private PstArApItem pstArApItem;
    private FrmArApItem frmArAp;
    private int language = LANGUAGE_DEFAULT;

    public CtrlArApItem(HttpServletRequest request) {
        msgString = "";
        arap = new ArApItem();
        try {
            pstArApItem = new PstArApItem(0);
        } catch (Exception e) {
        }
        frmArAp = new FrmArApItem(request, arap);
    }

    public int getLanguage() {
        return language;
    }

    public void setLanguage(int language) {
        this.language = language;
    }

    public ArApItem getArApItem() {
        return arap;
    }

    public FrmArApItem getForm() {
        return frmArAp;
    }

    public String getMessage() {
        return msgString;
    }

    public int action(int cmd, long Oid) {
        this.start = start;
        int result = RSLT_OK;
        msgString = "";
        
        switch (cmd) {
            case Command.ADD:
                break;

            case Command.SAVE:
                frmArAp.requestEntityObject(arap);
                arap.setOID(Oid);
                arap.setLeftToPay(arap.getAngsuran());
                
                if (frmArAp.errorSize() > 0) {
                    msgString = resultText[language][RSLT_INCOMPLETE];
                    return RSLT_INCOMPLETE;
                }
                
                if(arap.getAngsuran() == 0){
                    msgString = resultText[language][RSLT_PAYMENT_NULL];
                    return RSLT_PAYMENT_NULL;
                }
                
                if(arap.getDescription() == null && arap.getDescription().length() == 0){
                    msgString = resultText[language][RSLT_DESCRIPTION];
                    return RSLT_DESCRIPTION;
                }
                
                if (arap.getOID() == 0) {
                    try {
                        long oid = pstArApItem.insertExc(this.arap);
                    } catch (Exception exc) {
                        msgString = resultText[language][RSLT_UNKNOWN];
                        return RSLT_UNKNOWN;
                    }
                } else {
                    try {
                        ArApItem arApItem = new ArApItem();
                        arApItem = pstArApItem.fetchExc(Oid);
                        // misal buat baru 2500 sudah close 2000, total 10000/10
                        arApItem.setAngsuran(arap.getAngsuran());
                        long oid = pstArApItem.updateExc(arApItem); // update payment yang baru diinput
                                                
                        ArApMain arpMain = new ArApMain();
                        arpMain = PstArApMain.fetchExc(arApItem.getArApMainId());
                        double totalAmount = arpMain.getAmount(); //10000
                        double paymentAmount = arpMain.getPayment_amount_plan(); //1000
                        double totalAngsuranClose = PstArApItem.getTotalAngsuran("arap_main_id="+arApItem.getArApMainId()+" and arap_item_status=1"); // 2000
                        double editAmount = arap.getAngsuran();//2500
                        double totalBayar = totalAngsuranClose + editAmount;
                        double sisa = totalAmount - totalBayar;//10000 - (2500+2000) = 5500
                        double mod = sisa % paymentAmount; // 500
                        double jmlh = (sisa-mod)/paymentAmount; // (5500 - 500)/1000 = 5
                        
                        Vector listItemOld = PstArApItem.list(0, 0, "arap_item_id != "+Oid+" AND arap_main_id="+arApItem.getArApMainId()+" AND arap_item_status=0", "");
                        
                        for(int i = 0; i<listItemOld.size();i++){
                            arApItem = (ArApItem) listItemOld.get(i);
                            oid = pstArApItem.deleteExc(arApItem.getOID());
                        }
                        
                        if(sisa != 0){
                            Date startPaymentDate = null;
                            Date endPaymentDate = null;
                            
                            Vector listItem = PstArApItem.list(0, 0, "arap_main_id="+arApItem.getArApMainId(), "due_date asc");
                            
                            PayComponent payComponent = new  PayComponent();
                            try{
                            payComponent = PstPayComponent.fetchExc(arpMain.getComponentDeductionId());
                            }catch(Exception e){
                                System.out.printf("paycomponent null");
                            }

                            if(arpMain.getPeriodType() == 1){
                                Period period = new Period();
                                period = PstPeriod.fetchExc(arpMain.getPeriodId());

                                Calendar calendar = Calendar.getInstance();
                                calendar.setTime(period.getStartDate());
                                startPaymentDate = calendar.getTime();

                                calendar.setTime(period.getEndDate());
                                endPaymentDate = calendar.getTime();
                            } else {
                                startPaymentDate = arpMain.getStartofpaymentdate();
                            }
                                
                            for(int j = 0; j<listItem.size();j++){
                                ArApItem arApItem2 = (ArApItem) listItem.get(j);
                                
                                // edit data awal dan akhir
                                Date cloneStartDate = null;
                                if(arpMain.getPeriodType() == 1){
                                    arApItem2.setDueDate(endPaymentDate);
                                    arApItem2.setDescription(payComponent.getCompName() + " " + String.valueOf(startPaymentDate).toString() +" - "+ String.valueOf(endPaymentDate).toString() );
                                    
                                    // start get next date
                                    //SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                                    Calendar calendar = Calendar.getInstance();
                                    calendar.setTime(startPaymentDate);
                                    calendar.add(Calendar.MONTH, 1);
                                    Date nextStartDate = calendar.getTime();

                                    startPaymentDate = nextStartDate;

                                    calendar.setTime(startPaymentDate);
                                    calendar.add(Calendar.MONTH, 1);
                                    calendar.set(Calendar.DAY_OF_MONTH, 1);
                                    calendar.add(Calendar.DATE, -1);
                                    Date nextEndDate = calendar.getTime();

                                    endPaymentDate = nextEndDate;
                                    // end get next date

                                    long saveItem = PstArApItem.updateExc(arApItem2);
                                } else {
                                    cloneStartDate = (Date) startPaymentDate.clone();
                                    int every = arpMain.getPeriodeEvery();
                                    if (arpMain.getPeriodeEveryDMY() == 0){
                                        cloneStartDate.setDate(cloneStartDate.getDate() + every);
                                    } else if (arpMain.getPeriodeEveryDMY() == 1){
                                        cloneStartDate.setDate(cloneStartDate.getDate() + (7*every));
                                    } else if (arpMain.getPeriodeEveryDMY() == 2){
                                        cloneStartDate.setDate(cloneStartDate.getDate());
                                        cloneStartDate.setMonth(cloneStartDate.getMonth() + every);
                                        cloneStartDate.setYear(cloneStartDate.getYear());
                                    } else if (arpMain.getPeriodeEveryDMY() == 3){
                                        cloneStartDate.setYear(cloneStartDate.getYear() + every);
                                    }
                                    cloneStartDate.setDate(cloneStartDate.getDate() - 1);

                                    arApItem2.setDueDate(cloneStartDate);
                                    arApItem2.setDescription(payComponent.getCompName() + " " + String.valueOf(startPaymentDate).toString() +" - "+ String.valueOf(cloneStartDate).toString() );
                                   
                                    long saveItem = PstArApItem.updateExc(arApItem2);
                                    //int every = arap.getPeriodeEvery();
                                    if (arpMain.getPeriodeEveryDMY() == 0){
                                        startPaymentDate.setDate(startPaymentDate.getDate() + every);
                                    } else if (arpMain.getPeriodeEveryDMY() == 1){
                                        startPaymentDate.setDate(startPaymentDate.getDate() + (7*every));
                                    } else if (arpMain.getPeriodeEveryDMY() == 2){
                                        startPaymentDate.setDate(startPaymentDate.getDate());
                                        startPaymentDate.setMonth(startPaymentDate.getMonth() + every);
                                        startPaymentDate.setYear(startPaymentDate.getYear());
                                    } else if (arpMain.getPeriodeEveryDMY() == 3){
                                        startPaymentDate.setYear(startPaymentDate.getYear() + every);
                                    }
                                }
                                // buat data baru
                                if(j == listItem.size()-1){
                                    for (int i = 0 ; i < jmlh; i++){
                                        arApItem2.setArApMainId(arApItem.getArApMainId());
                                        arApItem2.setAngsuran(paymentAmount);

                                        arApItem2.setLeftToPay(paymentAmount);

                                        cloneStartDate = null;
                                        if(arpMain.getPeriodType() == 1){
                                            arApItem2.setDueDate(endPaymentDate);
                                            arApItem2.setDescription(payComponent.getCompName() + " " + String.valueOf(startPaymentDate).toString() +" - "+ String.valueOf(endPaymentDate).toString() );
                                            arApItem2.setArApItemStatus(0);
                                            //arApItem.setDouble(FLD_LEFT_TO_PAY, aktiva.getLeftToPay());
                                            arApItem2.setCurrencyId(arpMain.getIdCurrency());
                                            arApItem2.setRate(arpMain.getRate());
                                            //arApItem2.setLong(FLD_SELLING_AKTIVA_ID, aktiva.getSellingAktivaId());
                                            //arApItem.setLong(FLD_RECEIVE_AKTIVA_ID, aktiva.getReceiveAktivaId());

                                            // start get next date
                                            //SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                                            Calendar calendar = Calendar.getInstance();
                                            calendar.setTime(startPaymentDate);
                                            calendar.add(Calendar.MONTH, 1);
                                            Date nextStartDate = calendar.getTime();

                                            startPaymentDate = nextStartDate;

                                            calendar.setTime(startPaymentDate);
                                            calendar.add(Calendar.MONTH, 1);
                                            calendar.set(Calendar.DAY_OF_MONTH, 1);
                                            calendar.add(Calendar.DATE, -1);
                                            Date nextEndDate = calendar.getTime();

                                            endPaymentDate = nextEndDate;
                                            // end get next date

                                            long saveItem = PstArApItem.insertExc(arApItem2);
                                        } else {
                                            cloneStartDate = (Date) startPaymentDate.clone();
                                            int every = arpMain.getPeriodeEvery();
                                            if (arpMain.getPeriodeEveryDMY() == 0){
                                                cloneStartDate.setDate(cloneStartDate.getDate() + every);
                                            } else if (arpMain.getPeriodeEveryDMY() == 1){
                                                cloneStartDate.setDate(cloneStartDate.getDate() + (7*every));
                                            } else if (arpMain.getPeriodeEveryDMY() == 2){
                                                cloneStartDate.setDate(cloneStartDate.getDate());
                                                cloneStartDate.setMonth(cloneStartDate.getMonth() + every);
                                                cloneStartDate.setYear(cloneStartDate.getYear());
                                            } else if (arpMain.getPeriodeEveryDMY() == 3){
                                                cloneStartDate.setYear(cloneStartDate.getYear() + every);
                                            }
                                            cloneStartDate.setDate(cloneStartDate.getDate() - 1);

                                            arApItem2.setDueDate(cloneStartDate);
                                            arApItem2.setDescription(payComponent.getCompName() + " " + String.valueOf(startPaymentDate).toString() +" - "+ String.valueOf(cloneStartDate).toString() );
                                            arApItem2.setArApItemStatus(0);
                                            //arApItem.setDouble(FLD_LEFT_TO_PAY, aktiva.getLeftToPay());
                                            arApItem2.setCurrencyId(arpMain.getIdCurrency());
                                            arApItem2.setRate(arpMain.getRate());
                                            //arApItem.setLong(FLD_SELLING_AKTIVA_ID, aktiva.getSellingAktivaId());
                                            //arApItem.setLong(FLD_RECEIVE_AKTIVA_ID, aktiva.getReceiveAktivaId());


                                            long saveItem = PstArApItem.insertExc(arApItem2);
                                            //int every = arap.getPeriodeEvery();
                                            if (arpMain.getPeriodeEveryDMY() == 0){
                                                startPaymentDate.setDate(startPaymentDate.getDate() + every);
                                            } else if (arpMain.getPeriodeEveryDMY() == 1){
                                                startPaymentDate.setDate(startPaymentDate.getDate() + (7*every));
                                            } else if (arpMain.getPeriodeEveryDMY() == 2){
                                                startPaymentDate.setDate(startPaymentDate.getDate());
                                                startPaymentDate.setMonth(startPaymentDate.getMonth() + every);
                                                startPaymentDate.setYear(startPaymentDate.getYear());
                                            } else if (arpMain.getPeriodeEveryDMY() == 3){
                                                startPaymentDate.setYear(startPaymentDate.getYear() + every);
                                            }
                                        }
                                        if(i == jmlh-1 && mod != 0){
                                            arApItem2.setArApMainId(arApItem.getArApMainId());
                                            arApItem2.setAngsuran(mod);

                                            arApItem2.setLeftToPay(mod);

                                            cloneStartDate = null;
                                            if(arpMain.getPeriodType() == 1){
                                                arApItem2.setDueDate(endPaymentDate);
                                                arApItem2.setDescription(payComponent.getCompName() + " " + String.valueOf(startPaymentDate).toString() +" - "+ String.valueOf(endPaymentDate).toString() );
                                                arApItem2.setArApItemStatus(0);
                                                //arApItem.setDouble(FLD_LEFT_TO_PAY, aktiva.getLeftToPay());
                                                arApItem2.setCurrencyId(arpMain.getIdCurrency());
                                                arApItem2.setRate(arpMain.getRate());
                                                //arApItem2.setLong(FLD_SELLING_AKTIVA_ID, aktiva.getSellingAktivaId());
                                                //arApItem.setLong(FLD_RECEIVE_AKTIVA_ID, aktiva.getReceiveAktivaId());

                                                // start get next date
                                                //SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                                                Calendar calendar = Calendar.getInstance();
                                                calendar.setTime(startPaymentDate);
                                                calendar.add(Calendar.MONTH, 1);
                                                Date nextStartDate = calendar.getTime();

                                                startPaymentDate = nextStartDate;

                                                calendar.setTime(startPaymentDate);
                                                calendar.add(Calendar.MONTH, 1);
                                                calendar.set(Calendar.DAY_OF_MONTH, 1);
                                                calendar.add(Calendar.DATE, -1);
                                                Date nextEndDate = calendar.getTime();

                                                endPaymentDate = nextEndDate;
                                                // end get next date

                                                long saveItem = PstArApItem.insertExc(arApItem2);
                                            } else {
                                                cloneStartDate = (Date) startPaymentDate.clone();
                                                int every = arpMain.getPeriodeEvery();
                                                if (arpMain.getPeriodeEveryDMY() == 0){
                                                    cloneStartDate.setDate(cloneStartDate.getDate() + every);
                                                } else if (arpMain.getPeriodeEveryDMY() == 1){
                                                    cloneStartDate.setDate(cloneStartDate.getDate() + (7*every));
                                                } else if (arpMain.getPeriodeEveryDMY() == 2){
                                                    cloneStartDate.setDate(cloneStartDate.getDate());
                                                    cloneStartDate.setMonth(cloneStartDate.getMonth() + every);
                                                    cloneStartDate.setYear(cloneStartDate.getYear());
                                                } else if (arpMain.getPeriodeEveryDMY() == 3){
                                                    cloneStartDate.setYear(cloneStartDate.getYear() + every);
                                                }
                                                cloneStartDate.setDate(cloneStartDate.getDate() - 1);

                                                arApItem2.setDueDate(cloneStartDate);
                                                arApItem2.setDescription(payComponent.getCompName() + " " + String.valueOf(startPaymentDate).toString() +" - "+ String.valueOf(cloneStartDate).toString() );
                                                arApItem2.setArApItemStatus(0);
                                                //arApItem.setDouble(FLD_LEFT_TO_PAY, aktiva.getLeftToPay());
                                                arApItem2.setCurrencyId(arpMain.getIdCurrency());
                                                arApItem2.setRate(arpMain.getRate());
                                                //arApItem.setLong(FLD_SELLING_AKTIVA_ID, aktiva.getSellingAktivaId());
                                                //arApItem.setLong(FLD_RECEIVE_AKTIVA_ID, aktiva.getReceiveAktivaId());


                                                long saveItem = PstArApItem.insertExc(arApItem2);
                                                //int every = arap.getPeriodeEvery();
                                                if (arpMain.getPeriodeEveryDMY() == 0){
                                                    startPaymentDate.setDate(startPaymentDate.getDate() + every);
                                                } else if (arpMain.getPeriodeEveryDMY() == 1){
                                                    startPaymentDate.setDate(startPaymentDate.getDate() + (7*every));
                                                } else if (arpMain.getPeriodeEveryDMY() == 2){
                                                    startPaymentDate.setDate(startPaymentDate.getDate());
                                                    startPaymentDate.setMonth(startPaymentDate.getMonth() + every);
                                                    startPaymentDate.setYear(startPaymentDate.getYear());
                                                } else if (arpMain.getPeriodeEveryDMY() == 3){
                                                    startPaymentDate.setYear(startPaymentDate.getYear() + every);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        
                       /* ArApItem arapOld = new ArApItem();
                        arapOld = PstArApItem.fetchExc(Oid);
                        double selisih = arap.getAngsuran() - arapOld.getAngsuran(); // 350 - 100 = 250
                        double sisa = 0;
                        double bulat = 0;
                        double jumlah = 0;
                        double kurang = 0;
                        
                        sisa = arap.getAngsuran() % arapOld.getAngsuran(); // 250 habis dibagi 100 sisa 50
                        bulat = arap.getAngsuran() - sisa; //250 - 50 = 200
                        jumlah = bulat/arapOld.getAngsuran(); // 200 / 100 = 2
                        
                        if(selisih == 0 || jumlah > 0){
                            arapOld.setAngsuran(arap.getAngsuran());
                            long oid = pstArApItem.updateExc(arapOld);
                            jumlah = jumlah - 1;
                        }
                        if(selisih != 0){
                            Vector listArapItem = PstArApItem.list(0, 0, "ARAP_MAIN_ID='"+arapOld.getArApMainId()+"' && due_date > '"+arapOld.getDueDate()+"'", "due_date DESC");
                            ArApItem arApItemA = (ArApItem) listArapItem.get(listArapItem.size()-1);
                            kurang = arApItemA.getAngsuran() - selisih;
                            arapOld.setAngsuran(kurang);
                            long oid = pstArApItem.updateExc(arApItemA);
                            
                            if(selisih > arapOld.getAngsuran()) {
                               // Vector listArapItem = PstArApItem.list(0, 0, "ARAP_MAIN_ID='"+arapOld.getArApMainId()+"' && due_date > '"+arapOld.getDueDate()+"'", "due_date DESC");
                                for(int i=0; i < listArapItem.size(); i++){
                                    ArApItem arApItem = (ArApItem) listArapItem.get(i);

                                    if(jumlah > 0) {
                                        //arApItem.setArApItemStatus(1);
                                        oid = pstArApItem.deleteExc(arApItem);
                                        //oid = pstArApItem.updateExc(arApItem);
                                        jumlah = jumlah - 1;
                                    } else if(jumlah == 0 && sisa != 0) {
                                        kurang = arApItem.getAngsuran() - sisa;
                                        arApItem.setAngsuran(kurang);
                                        oid = pstArApItem.updateExc(arApItem);
                                        i = listArapItem.size();
                                    }
                                }
                            }
                        }*/
                    } catch (Exception exc) {
                        msgString = resultText[language][RSLT_UNKNOWN];
                        return RSLT_UNKNOWN;
                    }
                }
                break;

            case Command.EDIT:
                if (Oid != 0) {
                    try {
                        arap = (ArApItem) pstArApItem.fetchExc(Oid);
                    } catch (Exception exc) {
                        msgString = resultText[language][RSLT_UNKNOWN];
                        return RSLT_UNKNOWN;
                    }
                }
                break;

            case Command.ASK:
                if (Oid != 0) {
                    try {
                        arap = (ArApItem) pstArApItem.fetchExc(Oid);
                    } catch (Exception exc) {
                        msgString = resultText[language][RSLT_UNKNOWN];
                        return RSLT_UNKNOWN;
                    }
                }
                break;

            case Command.DELETE:
                if (Oid != 0) {
                    PstArApItem pstArApItem = new PstArApItem();
                    try {
                        long oid = pstArApItem.deleteExc(Oid);
                        this.start = 0;
                    } catch (Exception exc) {
                        msgString = resultText[language][RSLT_UNKNOWN];
                        return RSLT_UNKNOWN;
                    }
                }
                break;

            default:

        }
        return result;
    }
}
