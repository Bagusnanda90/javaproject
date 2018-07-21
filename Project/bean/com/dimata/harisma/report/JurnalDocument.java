/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.report;

import com.dimata.aiso.entity.masterdata.Perkiraan;
import com.dimata.aiso.entity.masterdata.PstPerkiraan;
import com.dimata.harisma.entity.log.ChangeValue;
import com.dimata.harisma.entity.masterdata.EmpDoc;
import com.dimata.harisma.entity.masterdata.PstComponentCoaMap;
import com.dimata.harisma.entity.payroll.PayPeriod;
import com.dimata.harisma.entity.payroll.PstPayPeriod;
import com.dimata.qdep.db.DBHandler;
import com.dimata.qdep.db.DBResultSet;
import com.dimata.util.Formater;
import java.sql.ResultSet;
import java.util.Vector;

/**
 *
 * @author GUSWIK
 */
public class JurnalDocument {

    public static String getPeriodName(long oid) {
        String str = "-";
        try {
            PayPeriod payPeriod = PstPayPeriod.fetchExc(oid);
            str = payPeriod.getPeriod();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return str;
    }

    public static String listJurnal(long oidPeriod, long companyId, String[] divisionSelect) {
        String valueReturn = "";

        Vector listPerkiraan = new Vector(1, 1);
        Vector listDebet = new Vector(1, 1);
        Vector listKredit = new Vector(1, 1);
        listPerkiraan = PstPerkiraan.list(0, 0, "", "");
        listDebet = PstPerkiraan.list(0, 0, "" + PstPerkiraan.fieldNames[PstPerkiraan.FLD_TANDA_DEBET_KREDIT] + " = 0", "");
        listKredit = PstPerkiraan.list(0, 0, "" + PstPerkiraan.fieldNames[PstPerkiraan.FLD_TANDA_DEBET_KREDIT] + " = 1", "");
        long divisionId = 0;
        ChangeValue changeValue = new ChangeValue();
        DBResultSet dbrs = null;
        try {
            valueReturn = "<table>"
                    + "<tr>"
                    + "<td valign=\"top\" style=\"padding-left: 32px\">";
            double[][] dataCoaDebet = null;
            String[][] dataAccountDebet = null;
            int n = 0;
            if (divisionSelect != null && divisionSelect.length > 0) {
                if (listDebet != null && listDebet.size() > 0) {
                    for (int i = 0; i < divisionSelect.length; i++) {
                        valueReturn += "<div class=\"content-list\">"
                                + "<div>&nbsp;</div>"
                                + "<table>"
                                + "<tr>"
                                + "<td class=\"td_title\" valign=\"top\"><strong>DEBET</strong></td>"
                                + "</tr>"
                                + "</table>"
                                + "<table border=\"1\" cellpadding=\"0\" cellspacing=\"0\">";;
                        double total = 0;
                        double debitTotal = 0;
                        double creditTotal = 0;
                        double debitSum = 0;
                        double creditSum = 0;
                        int no = 0;
                        /* inisialisasi arr 2 dimenesi */
                        if (dataCoaDebet == null) {
                            for (int p = 0; p < listDebet.size(); p++) {
                                Perkiraan perkiraan = (Perkiraan) listDebet.get(p);
                                divisionId = Long.valueOf(divisionSelect[i]);
                                boolean check = PstComponentCoaMap.isCoaMapping(perkiraan.getOID(), divisionId);
                                if (check) {
                                    n++;
                                }
                            }
                            dataCoaDebet = new double[n][2];
                            dataAccountDebet = new String[n][2];
                        }


                        for (int p = 0; p < listDebet.size(); p++) {
                            Perkiraan perkiraan = (Perkiraan) listDebet.get(p);
                            divisionId = Long.valueOf(divisionSelect[i]);
                            boolean check = PstComponentCoaMap.isCoaMapping(perkiraan.getOID(), divisionId);
                            if (check) {
                                total = PstComponentCoaMap.getValueCoa(perkiraan.getOID(), oidPeriod, divisionId);
                                dataAccountDebet[no][0] = perkiraan.getNoPerkiraan();
                                dataAccountDebet[no][1] = perkiraan.getNama();
                                if (perkiraan.getTandaDebetKredit() == 0) {
                                    debitTotal = total;
                                    creditTotal = 0;
                                    dataCoaDebet[no][0] = dataCoaDebet[no][0] + total;
                                    debitSum = debitSum + debitTotal;
                                } else {
                                    debitTotal = 0;
                                    creditTotal = total;
                                    dataCoaDebet[no][1] = dataCoaDebet[no][1] + total;
                                    creditSum = creditSum + creditTotal;
                                }

                                no++;
                                valueReturn += "<tr>"
                                        + "<td style=\"background-color: #FFF\">" + no + "</td>"
                                        + "<td style=\"background-color: #FFF\">" + perkiraan.getNama() + "</td>"
                                        + "<td style=\"background-color: #FFF\">" + perkiraan.getNoPerkiraan() + "</td>"
                                        + "<td style=\"background-color: #FFF\">" + Formater.formatNumberMataUang(debitTotal, "Rp") + " </td>"
                                        + "</tr>";
                            }
                        }
                        valueReturn += "<tr>"
                                + "<td style=\"background-color: #EEE;\" colspan=\"3\"><strong>Total</strong></td>"
                                + "<td style=\"background-color: #EEE;\">"
                                + "<strong>" + Formater.formatNumberMataUang(debitSum, "Rp") + "</strong>"
                                + "</td>"
                                + "</tr>"
                                + "</table>"
                                + "</div>";

                    }
                }
            }

            double[][] dataCoaKredit = null;
            String[][] dataAccountKredit = null;
            int x = 0;
            if (divisionSelect != null && divisionSelect.length > 0) {
                if (listKredit != null && listKredit.size() > 0) {
                    for (int i = 0; i < divisionSelect.length; i++) {
                        valueReturn += "<div class=\"content-list\">"
                                + "<div>&nbsp;</div>"
                                + "<table>"
                                + "<tr>"
                                + "<td class=\"td_title\" valign=\"top\"><strong>KREDIT</strong></td>"
                                + "</tr>"
                                + "</table>"
                                + "<table border=\"1\" cellpadding=\"0\" cellspacing=\"0\">";;
                        double total = 0;
                        double debitTotal = 0;
                        double creditTotal = 0;
                        double debitSum = 0;
                        double creditSum = 0;
                        int no = 0;
                        /* inisialisasi arr 2 dimenesi */
                        if (dataCoaKredit == null) {
                            for (int p = 0; p < listKredit.size(); p++) {
                                Perkiraan perkiraan = (Perkiraan) listKredit.get(p);
                                divisionId = Long.valueOf(divisionSelect[i]);
                                boolean check = PstComponentCoaMap.isCoaMapping(perkiraan.getOID(), divisionId);
                                if (check) {
                                    x++;
                                }
                            }
                            dataCoaKredit = new double[n][2];
                            dataAccountKredit = new String[n][2];
                        }


                        for (int p = 0; p < listKredit.size(); p++) {
                            Perkiraan perkiraan = (Perkiraan) listKredit.get(p);
                            divisionId = Long.valueOf(divisionSelect[i]);
                            boolean check = PstComponentCoaMap.isCoaMapping(perkiraan.getOID(), divisionId);
                            if (check) {
                                total = PstComponentCoaMap.getValueCoa(perkiraan.getOID(), oidPeriod, divisionId);
                                dataAccountKredit[no][0] = perkiraan.getNoPerkiraan();
                                dataAccountKredit[no][1] = perkiraan.getNama();
                                if (perkiraan.getTandaDebetKredit() == 0) {
                                    debitTotal = total;
                                    creditTotal = 0;
                                    dataCoaKredit[no][0] = dataCoaKredit[no][0] + total;
                                    debitSum = debitSum + debitTotal;
                                } else {
                                    debitTotal = 0;
                                    creditTotal = total;
                                    dataCoaKredit[no][1] = dataCoaKredit[no][1] + total;
                                    creditSum = creditSum + creditTotal;
                                }

                                no++;
                                valueReturn += "<tr>"
                                        + "<td style=\"background-color: #FFF\">" + no + "</td>"
                                        + "<td style=\"background-color: #FFF\">" + perkiraan.getNama() + "</td>"
                                        + "<td style=\"background-color: #FFF\">" + perkiraan.getNoPerkiraan() + "</td>"
                                        + "<td style=\"background-color: #FFF\">" + Formater.formatNumberMataUang(creditTotal, "Rp") + " </td>"
                                        + "</tr>";
                            }
                        }
                        valueReturn += "<tr>"
                                + "<td style=\"background-color: #EEE;\" colspan=\"3\"><strong>Total</strong></td>"
                                + "<td style=\"background-color: #EEE;\">"
                                + "<strong>" + Formater.formatNumberMataUang(creditSum, "Rp") + "</strong>"
                                + "</td>"
                                + "</tr>"
                                + "</table>"
                                + "</div>";

                    }
                }
            }

            valueReturn += "</td>"
                    + "</tr>"
                    + "</table>";

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return valueReturn;
    }

    public static String listJurnalAllDiv(long oidPeriod, long companyId, String[] divisionSelect) {
        String valueReturn = "";

        Vector listPerkiraan = new Vector(1, 1);
        Vector listDebet = new Vector(1, 1);
        Vector listKredit = new Vector(1, 1);
        listPerkiraan = PstPerkiraan.list(0, 0, "", "");
        listDebet = PstPerkiraan.list(0, 0, "" + PstPerkiraan.fieldNames[PstPerkiraan.FLD_TANDA_DEBET_KREDIT] + " = 0", "");
        listKredit = PstPerkiraan.list(0, 0, "" + PstPerkiraan.fieldNames[PstPerkiraan.FLD_TANDA_DEBET_KREDIT] + " = 1", "");
        long divisionId = 0;
        DBResultSet dbrs = null;
        try {
            valueReturn = "<table>"
            + "<tr>"
            + "<td valign=\"top\" style=\"padding-left: 32px\">";
            //Start Debet
            double[][] dataCoaDebet = null;
            String[][] dataAccountDebet = null;
            int n = 0;
            if (divisionSelect != null && divisionSelect.length > 0) {
                if (listDebet != null && listDebet.size() > 0) {

                    for (int i = 0; i < divisionSelect.length; i++) {

                        double total = 0;
                        double debitTotal = 0;
                        double creditTotal = 0;
                        double debitSum = 0;
                        double creditSum = 0;
                        int no = 0;
                        /* inisialisasi arr 2 dimenesi */
                        if (dataCoaDebet == null) {
                            for (int p = 0; p < listDebet.size(); p++) {
                                Perkiraan perkiraan = (Perkiraan) listDebet.get(p);
                                divisionId = Long.valueOf(divisionSelect[i]);
                                boolean check = PstComponentCoaMap.isCoaMapping(perkiraan.getOID(), divisionId);
                                if (check) {
                                    n++;
                                }
                            }
                            dataCoaDebet = new double[n][2];
                            dataAccountDebet = new String[n][2];
                        }


                        for (int p = 0; p < listDebet.size(); p++) {
                            Perkiraan perkiraan = new Perkiraan();
                            perkiraan = (Perkiraan) listDebet.get(p);
                            if (perkiraan.getOID() == 0) {
                                continue;
                            }
                            divisionId = Long.valueOf(divisionSelect[i]);
                            boolean check = PstComponentCoaMap.isCoaMapping(perkiraan.getOID(), divisionId);
                            if (check) {
                                total = PstComponentCoaMap.getValueCoa(perkiraan.getOID(), oidPeriod, divisionId);
                                try {
                                    dataAccountDebet[no][0] = perkiraan.getNoPerkiraan();
                                    dataAccountDebet[no][1] = perkiraan.getNama();
                                    if (perkiraan.getTandaDebetKredit() == 0) {
                                        debitTotal = total;
                                        creditTotal = 0;
                                        dataCoaDebet[no][0] = dataCoaDebet[no][0] + total;
                                        debitSum = debitSum + debitTotal;
                                    } else {
                                        debitTotal = 0;
                                        creditTotal = total;
                                        dataCoaDebet[no][1] = dataCoaDebet[no][1] + total;
                                        creditSum = creditSum + creditTotal;
                                    }
                                } catch (Exception e) {
                                }
                                no++;

                            }
                        }

                    }
                }
            }
            if (divisionSelect != null && divisionSelect.length > 1) {
                double dataDebitSum = 0;
                double dataCreditSum = 0;
                if (dataCoaDebet != null) {
                    valueReturn += "<div>&nbsp;</div>"
                            + "<div class=\"content-list\">"
                            + "<strong style=\"color:#575757\">DEBET</strong>"
                            + "<div>&nbsp;</div>"
                            + "<table border=\"1\" cellpadding=\"0\" cellspacing=\"0\">";
                    for (int i = 0; i < n; i++) {
                        dataDebitSum = dataDebitSum + dataCoaDebet[i][0];
                        dataCreditSum = dataCreditSum + dataCoaDebet[i][1];
                        valueReturn += "<tr>"
                                + "<td>" + (i + 1) + "</td>"
                                + "<td>" + dataAccountDebet[i][1] + "</td>"
                                + "<td>" + dataAccountDebet[i][0] + "</td>"
                                + "<td>" + Formater.formatNumberMataUang(dataCoaDebet[i][0], "Rp") + ""
                                + "</tr>";
                    }
                    valueReturn += " <tr>"
                            + "<td colspan=\"3\"><strong>Total</strong></td>"
                            + "<td><strong>" + Formater.formatNumberMataUang(dataDebitSum, "Rp") + "</strong></td>"
                            + "</tr>"
                            + "</table>"
                            + "</div>";
                }
            }
//End Debet
//Start Kredit
            double[][] dataCoaKredit = null;
            String[][] dataAccountKredit = null;
            int x = 0;
            if (divisionSelect != null && divisionSelect.length > 0) {
                if (listKredit != null && listKredit.size() > 0) {

                    for (int i = 0; i < divisionSelect.length; i++) {

                        double total = 0;
                        double debitTotal = 0;
                        double creditTotal = 0;
                        double debitSum = 0;
                        double creditSum = 0;
                        int no = 0;
                        /* inisialisasi arr 2 dimenesi */
                        if (dataCoaKredit == null) {
                            for (int p = 0; p < listKredit.size(); p++) {
                                Perkiraan perkiraan = (Perkiraan) listKredit.get(p);
                                divisionId = Long.valueOf(divisionSelect[i]);
                                boolean check = PstComponentCoaMap.isCoaMapping(perkiraan.getOID(), divisionId);
                                if (check) {
                                    x++;
                                }
                            }
                            dataCoaKredit = new double[n][2];
                            dataAccountKredit = new String[n][2];
                        }


                        for (int p = 0; p < listKredit.size(); p++) {
                            Perkiraan perkiraan = new Perkiraan();
                            perkiraan = (Perkiraan) listKredit.get(p);
                            if (perkiraan.getOID() == 0) {
                                continue;
                            }
                            divisionId = Long.valueOf(divisionSelect[i]);
                            boolean check = PstComponentCoaMap.isCoaMapping(perkiraan.getOID(), divisionId);
                            if (check) {
                                total = PstComponentCoaMap.getValueCoa(perkiraan.getOID(), oidPeriod, divisionId);
                                try {
                                    dataAccountKredit[no][0] = perkiraan.getNoPerkiraan();
                                    dataAccountKredit[no][1] = perkiraan.getNama();
                                    if (perkiraan.getTandaDebetKredit() == 0) {
                                        debitTotal = total;
                                        creditTotal = 0;
                                        dataCoaKredit[no][0] = dataCoaKredit[no][0] + total;
                                        debitSum = debitSum + debitTotal;
                                    } else {
                                        debitTotal = 0;
                                        creditTotal = total;
                                        dataCoaKredit[no][1] = dataCoaKredit[no][1] + total;
                                        creditSum = creditSum + creditTotal;
                                    }
                                } catch (Exception e) {
                                }
                                no++;

                            }
                        }


                    }
                }
            }
            if (divisionSelect != null && divisionSelect.length > 1) {
                double dataDebitSum = 0;
                double dataCreditSum = 0;
                if (dataCoaKredit != null) {
                    valueReturn += "<div>&nbsp;</div>"
                            + "<div class=\"content-list\">"
                            + "<strong style=\"color:#575757\">KREDIT</strong>"
                            + "<div>&nbsp;</div>"
                            + "<table border=\"1\" cellpadding=\"0\" cellspacing=\"0\">";
                    for (int i = 0; i < x; i++) {
                        dataDebitSum = dataDebitSum + dataCoaKredit[i][0];
                        dataCreditSum = dataCreditSum + dataCoaKredit[i][1];
                        valueReturn += "<tr>"
                                + "<td>" + (i + 1) + "</td>"
                                + "<td>" + dataAccountKredit[i][1] + "</td>"
                                + "<td>" + dataAccountKredit[i][0] + "</td>"
                                + "<td>" + Formater.formatNumberMataUang(dataCoaKredit[i][1], "Rp") + ""
                                + "</tr>";
                    }
                    valueReturn += " <tr>"
                            + "<td colspan=\"3\"><strong>Total</strong></td>"
                            + "<td><strong>" + Formater.formatNumberMataUang(dataCreditSum, "Rp") + "</strong></td>"
                            + "</tr>"
                            + "</table>"
                            + "</div>";
                }
            }
//End Kredit
            valueReturn += "</td>"
                    + "</tr>"
                    + "</table>";

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return valueReturn;
    }
    /*
     * Document : function print jurnal
     * author : Hendra Putu
     * Date : 2016-12-18
     */
    public static String printJurnal(long oidPeriod, String[] divisionSelect, String[] componentSelect) {
        String htmlOutput = "";
        double[][] dataCoa = null;
        String[][] dataAccount = null;
        int n = 0;
        long divisionId = 0;
        double dataDebitSum = 0;
        double dataCreditSum = 0;
        String perkiraanIds = "";
        String whereDebet = "";
        String whereCredit = "";
        if (componentSelect != null && componentSelect.length > 0){
            for (int i=0; i<componentSelect.length; i++){
                perkiraanIds = perkiraanIds + componentSelect[i]+",";
            }
            perkiraanIds = perkiraanIds.substring(0, (perkiraanIds.length()-1));
            whereDebet = PstPerkiraan.fieldNames[PstPerkiraan.FLD_IDPERKIRAAN]+" IN("+perkiraanIds+")";
            whereDebet += " AND "+PstPerkiraan.fieldNames[PstPerkiraan.FLD_TANDA_DEBET_KREDIT] + "=0";
            whereCredit = PstPerkiraan.fieldNames[PstPerkiraan.FLD_IDPERKIRAAN]+" IN("+perkiraanIds+")";
            whereCredit += " AND "+PstPerkiraan.fieldNames[PstPerkiraan.FLD_TANDA_DEBET_KREDIT] + "=1";
        }
        
        Vector perkiraanDebetList = PstPerkiraan.list(0, 0, whereDebet, "");
        Vector perkiraanCreditList = PstPerkiraan.list(0, 0, whereCredit, "");
        if (divisionSelect != null && divisionSelect.length > 0) {
            if (perkiraanDebetList != null && perkiraanDebetList.size() > 0) {
                for (int i = 0; i < divisionSelect.length; i++) {
                    double total = 0;
                    double debitTotal = 0;
                    double creditTotal = 0;
                    double debitSum = 0;
                    double creditSum = 0;
                    int no = 0;
                    /* inisialisasi arr 2 dimenesi */
                    if (dataCoa == null) {
                        for (int p = 0; p < perkiraanDebetList.size(); p++) {
                            Perkiraan perkiraan = (Perkiraan) perkiraanDebetList.get(p);
                            divisionId = Long.valueOf(divisionSelect[i]);
                            boolean check = PstComponentCoaMap.isCoaMapping(perkiraan.getOID(), divisionId);
                            if (check) {
                                n++;
                            }
                        }
                        dataCoa = new double[n][2];
                        dataAccount = new String[n][2];
                    }

                    for (int p = 0; p < perkiraanDebetList.size(); p++) {
                        Perkiraan perkiraan = (Perkiraan) perkiraanDebetList.get(p);
                        divisionId = Long.valueOf(divisionSelect[i]);
                        boolean check = PstComponentCoaMap.isCoaMapping(perkiraan.getOID(), divisionId);
                        if (check) {
                            total = PstComponentCoaMap.getValueCoa(perkiraan.getOID(), oidPeriod, divisionId);
                            dataAccount[no][0] = perkiraan.getNoPerkiraan();
                            dataAccount[no][1] = perkiraan.getNama();
                            if (perkiraan.getTandaDebetKredit() == 0) {
                                debitTotal = total;
                                creditTotal = 0;
                                dataCoa[no][0] = dataCoa[no][0] + total;
                                debitSum = debitSum + debitTotal;
                            } else {
                                debitTotal = 0;
                                creditTotal = total;
                                dataCoa[no][1] = dataCoa[no][1] + total;
                                creditSum = creditSum + creditTotal;
                            }

                            no++;
                        }
                    }
                }
            }
            
            /* print debet output */
            if (dataCoa != null){
                htmlOutput = "<div>&nbsp;</div>";
                htmlOutput += "<div>DEBET</div>";
                htmlOutput += "<table border=\"1\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">";
                for (int i=0; i<n; i++){
                    dataDebitSum = dataDebitSum + dataCoa[i][0];
                    htmlOutput += "<tr>"
                    + "<td style=\"background-color: #FFF\">" + (i+1) + "</td>"
                    + "<td style=\"background-color: #FFF\">" + dataAccount[i][0] + "</td>"
                    + "<td style=\"background-color: #FFF\">" + dataAccount[i][1] + "</td>"
                    + "<td style=\"background-color: #FFF\">" + Formater.formatNumberMataUang(dataCoa[i][0], "Rp") + " </td>"
                    + "</tr>";
                }
                htmlOutput += "<tr>"
                + "<td style=\"background-color: #EEE;\" colspan=\"3\"><strong>Total</strong></td>"
                + "<td style=\"background-color: #EEE;\">"
                + "<strong>" + Formater.formatNumberMataUang(dataDebitSum, "Rp") + "</strong>"
                + "</td>"
                + "</tr>";
                htmlOutput += "</table>";
            }
            
            dataCoa = null;
            dataAccount = null;
        
            if (perkiraanCreditList != null && perkiraanCreditList.size() > 0) {
                for (int i = 0; i < divisionSelect.length; i++) {
                    double total = 0;
                    double debitTotal = 0;
                    double creditTotal = 0;
                    double debitSum = 0;
                    double creditSum = 0;
                    int no = 0;
                    /* inisialisasi arr 2 dimenesi */
                    if (dataCoa == null) {
                        for (int p = 0; p < perkiraanCreditList.size(); p++) {
                            Perkiraan perkiraan = (Perkiraan) perkiraanCreditList.get(p);
                            divisionId = Long.valueOf(divisionSelect[i]);
                            boolean check = PstComponentCoaMap.isCoaMapping(perkiraan.getOID(), divisionId);
                            if (check) {
                                n++;
                            }
                        }
                        dataCoa = new double[n][2];
                        dataAccount = new String[n][2];
                    }

                    for (int p = 0; p < perkiraanCreditList.size(); p++) {
                        Perkiraan perkiraan = (Perkiraan) perkiraanCreditList.get(p);
                        divisionId = Long.valueOf(divisionSelect[i]);
                        boolean check = PstComponentCoaMap.isCoaMapping(perkiraan.getOID(), divisionId);
                        if (check) {
                            total = PstComponentCoaMap.getValueCoa(perkiraan.getOID(), oidPeriod, divisionId);
                            dataAccount[no][0] = perkiraan.getNoPerkiraan();
                            dataAccount[no][1] = perkiraan.getNama();
                            if (perkiraan.getTandaDebetKredit() == 0) {
                                debitTotal = total;
                                creditTotal = 0;
                                dataCoa[no][0] = dataCoa[no][0] + total;
                                debitSum = debitSum + debitTotal;
                            } else {
                                debitTotal = 0;
                                creditTotal = total;
                                dataCoa[no][1] = dataCoa[no][1] + total;
                                creditSum = creditSum + creditTotal;
                            }

                            no++;
                        }
                    }
                }
            }
            
            /* print credit output */
            if (dataCoa != null){
                htmlOutput += "<div>&nbsp;</div>";
                htmlOutput += "<div>CREDIT</div>";
                htmlOutput += "<table border=\"1\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">";
                for (int i=0; i<n; i++){
                    dataCreditSum = dataCreditSum + dataCoa[i][1];
                    if (dataAccount[i][0] != null && dataAccount[i][1] != null){
                        htmlOutput += "<tr>"
                        + "<td style=\"background-color: #FFF\">" + (i+1) + "</td>"
                        + "<td style=\"background-color: #FFF\">" + dataAccount[i][0] + "</td>"
                        + "<td style=\"background-color: #FFF\">" + dataAccount[i][1] + "</td>"
                        + "<td style=\"background-color: #FFF\">" + Formater.formatNumberMataUang(dataCoa[i][1], "Rp") + " </td>"
                        + "</tr>";
                    }
                }
                htmlOutput += "<tr>"
                + "<td style=\"background-color: #EEE;\" colspan=\"3\"><strong>Total</strong></td>"
                + "<td style=\"background-color: #EEE;\">"
                + "<strong>" + Formater.formatNumberMataUang(dataCreditSum, "Rp") + "</strong>"
                + "</td>"
                + "</tr>";
                htmlOutput += "</table>";
            }

        }
        return htmlOutput;
    }
}
