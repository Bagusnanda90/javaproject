<%-- 
    Document   : srcempscheduleNew
    Created on : Jun 27, 2017, 2:21:38 PM
    Author     : Gunadi
--%>

<%@page import="com.dimata.harisma.entity.attendance.PstEmpSchedule"%>
<%@page import="com.dimata.harisma.form.attendance.FrmEmpSchedule"%>
<%@page import="com.dimata.system.entity.system.PstSystemProperty"%>
<%@page import="java.util.GregorianCalendar"%>
<%@page import="java.util.Date"%>
<%@page import="com.dimata.util.Command"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ include file = "../../main/javainit.jsp" %>
<%
    int iCommand = FRMQueryString.requestCommand(request);
    String error="";
    long periodId = FRMQueryString.requestLong(request, "period_id");
    
    Period periodX = new Period();
    if (periodId > 0){
        try {
        periodX= PstPeriod.fetchExc(periodId);
        } catch (Exception exc){}
    }
    
    int startDatePeriodX = periodX==null || periodX.getOID()==0 ?  Integer.parseInt(String.valueOf(PstSystemProperty.getValueByName("START_DATE_PERIOD"))) : periodX.getStartDate().getDate() ; 
    Vector listPeriod = PstPeriod.list(0, 1000, "", "START_DATE DESC");
    Vector periodKey = new Vector(1, 1);
    Vector periodValue = new Vector(1, 1);
    for (int p = 0; p < listPeriod.size(); p++) {
        Period period = (Period) listPeriod.get(p);

        // uncoment this for filter period from now up
        //if (period.getEndDate().after(new Date())) {
            periodKey.add(period.getPeriod());
            periodValue.add("" + period.getOID());
        //}
    }
    
    int COL_EMP_SCH=2; 
    String msgString = "";
    String topHeader = "";
    StringBuffer drawList = new StringBuffer();
    Vector vSchedulePerEmp = new Vector(1,1);
    Hashtable schdelueDate = new Hashtable();
    Hashtable schdelueName = new Hashtable();
    String depName = "";
    String creatorName = "";
    if (iCommand == Command.LIST) {
            Vector vector = (Vector) session.getValue("WORK_SCHEDULE");
            Hashtable schName = (Hashtable) session.getValue("SCHEDULE_NAME");
            Hashtable schDate = (Hashtable) session.getValue("SCHEDULE_DATE");
            String sCreator = (String) session.getValue("CREATOR_NAME"); 
            String sdeptName = (String) session.getValue("DEPARTEMENT_NAME"); 

            vSchedulePerEmp = (Vector) vector.clone();
            schdelueDate = (Hashtable) schDate.clone();
            schdelueName = (Hashtable) schName.clone();
            creatorName = (String) sCreator;
            depName = (String) sdeptName;
            
            boolean useDayHeader = true;
                 EmployeeUpload employeeUpload = new EmployeeUpload();
                if (vSchedulePerEmp.size() > 0) {
                    drawList.append("\n<table cellpadding=\"2\" cellspacing=\"2\" border=\"0\">" + 
                            "\n\t<tr>" +
                            "\n\t\t<td colspan=\"3\"><B><font size=\"3\">" + topHeader  + "</font></B></td>" +
                            "\n\t</tr>" +
                            "\n\t<tr>" +
                            "\n\t\t<td colspan=\"2\"><B>DEPARTMENT</B></td>" +
                            "\n\t\t<td>" + (depName.length()>0 ? depName : employeeUpload.getDeptName())+ "</td>" +
                            "\n\t</tr>" +
                            "\n\t<tr>" +
                            "\n\t\t<td colspan=\"2\"><B>CREATED BY</B></td>" +
                            "\n\t\t<td> " + (creatorName.length()>0 ? creatorName: employeeUpload.getCreatorName()) + "</td>" +
                            "\n\t</tr>" +
                            "\n\t<tr>" +
                            "\n\t\t<td>&nbsp;</td>" +
                            "\n\t\t<td></td>" +
                            "\n\t\t<td></td>" +
                            "\n\t</tr>" +
                            "\n\t<tr>" +
                            "\n\t\t<td><li></td>" +
                            "\n\t\t<td colspan=\"2\">Choose the Period of Working Schedule</td>" +
                            "\n\t</tr>" +
                            "\n\t<tr>" +
                            "\n\t\t<td>&nbsp;</td>" +
                            "\n\t\t<td>Period</td>" +
                            "\n\t\t<td>" + ControlCombo.draw("period_id", "formElemen", "select...", "" + (periodX.getOID()!=0?periodX.getOID():periodId), periodValue, periodKey) + "</td>" +
                            "\n\t</tr>" +
                            "\n\t<tr>" +
                            "\n\t\t<td><li></td>" +
                            "\n\t\t<td colspan=\"2\">List of Working Schedule</td>" +
                            "\n\t</tr>" +
                            "\n</table>" +
                            "\n<table class=\"table tschedule\">" +
                            "\n\t<tbody>"+
                            "\n\t<tr>" +
                            "\n\t\t<td width=\"1%\" " + (useDayHeader ? "rowspan=\"3\"" : "rowspan=\"2\"") + ">No</td>" +
                            "\n\t\t<td width=\"15%\" " + (useDayHeader ? "rowspan=\"3\"" : "rowspan=\"2\"") + ">Employee</td>" +
                            "\n\t\t<td width=\"8%\" " + (useDayHeader ? "rowspan=\"3\"" : "rowspan=\"2\"") + ">Payroll</td>" +
                            "\n\t\t<td width=\"77%\" colspan=\"" + (schdelueDate.size()) + "\" align=\"center\">Date</td>" +
                            "\n\t</tr>");

                    double width = 77 / (new Double(schdelueDate.size()+2)).doubleValue();

                    if (useDayHeader) {
                        // untuk header (day symbol)																
                        drawList.append("\n\t<tr>");
                        for (int i = 2; i < schdelueName.size()+2; i++) {
                                String dayCode = "";
                                try{
                                    dayCode = (String.valueOf(schdelueName.get(""+i))).trim(); 
                                } catch (Exception exc){
                                    
                                }
                                drawList.append("\n\t\t<td align=\"center\" width=\"" + width + "%\">" + dayCode+ "</td>");
                        }
                        drawList.append("\n\t</tr>");
                    }

                    drawList.append("\n\t<tr class=\"listheader\">");
                    for (int h = 2; h < schdelueDate.size()+2; h++) {
                        //if ((h % numcol) > 1) {
                                String dateNumber = "";
                                try{
                                dateNumber = (String.valueOf(schdelueDate.get(""+h))).trim();
                                dateNumber=dateNumber.substring(0, dateNumber.indexOf("."));
                                } catch (Exception exc){
                                    
                                }
                            //String dt = String.valueOf(v.elementAt(numcol));
                            drawList.append("\n\t\t<td align=\"center\" width=\"" + width + "%\" class=\"tableheader\">" + dateNumber + "</td>");
                        //}
                       // v.remove(startDate);
                    }
                    drawList.append("\n\t</tr>\n\t<tr>");

                    
                    Hashtable hashSchedule = new Hashtable();
                    hashSchedule.put(" ", "0");
                    Hashtable hashScheduleOidKey = new Hashtable();
                    hashScheduleOidKey.put("0", " ");
                    Hashtable hashCategory = new Hashtable();
                    hashCategory.put(" ", "0");
                    //add by priska 20151112
                    Period period = new Period();
                    try { period = PstPeriod.fetchExc(periodId); } catch (Exception ex){}
                    Vector listSchSymbol = PstScheduleSymbol.listScheduleSymbolAndCategory();
                    if (listSchSymbol != null && listSchSymbol.size() > 0){
                        int intListSchSymbol = listSchSymbol.size();
                        for (int ls = 0; ls < intListSchSymbol; ls++) {
                            try {
                                Vector vectSchldCat = (Vector) listSchSymbol.get(ls);
                                ScheduleSymbol schSymbol = (ScheduleSymbol) vectSchldCat.get(0);
                                ScheduleCategory schCategory = (ScheduleCategory) vectSchldCat.get(1);

                                hashSchedule.put(schSymbol.getSymbol().toUpperCase(), String.valueOf(schSymbol.getOID()));
                                hashScheduleOidKey.put(String.valueOf(schSymbol.getOID()) , schSymbol.getSymbol().toUpperCase());
                                hashCategory.put(schSymbol.getSymbol().toUpperCase(), String.valueOf(schCategory.getCategoryType()));
                            } catch (Exception exc) {
                                out.println("Error symbol on : ls = " + ls);
                            }
                        }
                    }
                    
                    
                    Hashtable hashPayroll = new Hashtable();
                    Hashtable hashEmpNum = new Hashtable();
                    Hashtable hashEmpName = new Hashtable();
                    String whereC = PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + "=0";
                    Vector listEmployee = PstEmployee.list(0, 0, whereC, "");

                    for (int e = 0; e < listEmployee.size(); e++) {
                        Employee employee = (Employee) listEmployee.get(e);
                        hashPayroll.put(employee.getEmployeeNum(), String.valueOf(employee.getOID()));
                        hashEmpNum.put(employee.getEmployeeNum(), employee.getEmployeeNum());
                        hashEmpName.put(employee.getEmployeeNum(), employee.getFullName());
                    }
                    
                    
                    // inisialisalsi proses utk memberi warna row yg error
                    int inIterasi = 33;	 // di set 33 karena maksimum sisa hasil bagi dengan jumlah kolom (33) adalah 32														
                    int idxScheduleErr = 0;

                    String scdSymbol = "";
                    String tmp1stSchd = "";
                    String sch="";
                    String ndsch = "";
                    String writeSchd = "";
                  
                        String clsHtml = "class=\"listgensell\"";
                    //int startEmployeeRow=ROW_EMPLOYEE;
                   
                    //int iCell =0;
                    //String strNameOnList ="";
                        drawList.append("\n\t</tr>\n\t");
                        int start =0; 
                    for (int i =0; i < vSchedulePerEmp.size(); i++){
                        //Vector scheduleAfterCheck = new Vector();
                        try {
                        employeeUpload = (EmployeeUpload)vSchedulePerEmp.get(i);
                       /// input employeenya
                         drawList.append("<tr>");
                        //prosess no per employee
                         String stEmpNameExcel = employeeUpload.getEmpNameKeyPayrol().trim();
                         String strEmployeeInDB= employeeUpload.getEmpName().trim();
                         String messageFullName=null;
                         if(stEmpNameExcel!=null && stEmpNameExcel.length()>0 
                           && strEmployeeInDB!=null && strEmployeeInDB.length()>0){
                             if(!strEmployeeInDB.equalsIgnoreCase(stEmpNameExcel)){
                                messageFullName = "<strong> <font color=\"#000000\"> <i>" +stEmpNameExcel+ "  </i></font></strong> ( Excel ) <strong> <font color=\"#FF0000\">  Not Match   </font></strong> <strong> <font color=\"#000000\"> <i>" +strEmployeeInDB+ "</i> </font></strong>( Harisma ) please cek your excel"; 
                             }
                         }else{
                             messageFullName = "<strong> <font color=\"#000000\"> <i>"+ stEmpNameExcel +"</i></font></strong> <strong> <font color=\"#FF0000\">  is not in Harisma  </font></strong>"; 
                         }
                        drawList.append("\n\t\t<td " + clsHtml  + ">" + ((start+i)+1) 
                         + "</td><td " + clsHtml + " width=\"" + width + "%\">" 
                         + (messageFullName==null ? "<strong> <font color=\"#000000\"> "+ employeeUpload.getEmpName() +"</font></strong>" : (messageFullName))  + "</td>"); 
                         
                        String stEmpNumberExcel = employeeUpload.getEmpNumberExcel();
                         String strEmpNumberDB= employeeUpload.getEmpNumb();
                         String messageNumber=null;
                         if(stEmpNumberExcel!=null && stEmpNumberExcel.length()>0 
                           && strEmpNumberDB!=null && strEmpNumberDB.length()>0){
                             if(!strEmpNumberDB.equalsIgnoreCase(stEmpNumberExcel)){
                                messageNumber = "<strong> <font color=\"#000000\"><i> Payroll: "+employeeUpload.getEmpNumberExcel()+" </i> </font></strong> (Excel) <strong><font color=\"#FF0000\"> not in Harisma  </font></strong>"; 
                             }
                         }else{
                             messageNumber = " Payroll: <strong> <font color=\"#000000\"><i>  "+employeeUpload.getEmpNumberExcel()+" </i> </font></strong> (Excel) <strong><font color=\"#FF0000\"> not in Harisma  </font></strong>"; 
                         }
                        String st1 = (messageNumber == null ?"":"bgcolor=\"#FFFF00\"");
                        //String st2 = (messageNumber == null ?"":"<strong><font color=\"#FF0000\">");
                       // String st3 = (messageNumber == null ?"": (messageNumber)); 
                        
                         
                        drawList.append("\n\t\t<td " + clsHtml +" " + st1+ " width=\"" + width + "%\">");
                        drawList.append("<input type=\"hidden\" name=\"employee_id\" value=\"" + employeeUpload.getEmpId() + "\">");
                        drawList.append("<input type=\"hidden\" name=\"employee_num\" value=\"" + employeeUpload.getEmpNumb() + "\">");
                        drawList.append(messageNumber == null? "<strong> <font color=\"#000000\">"+ employeeUpload.getEmpNumberExcel() +" </font></strong>":messageNumber + "</td>");
                        
                      for (int h = COL_EMP_SCH; h < schdelueDate.size()+COL_EMP_SCH; h++) {
                            String clsHtmlIdxScheduleErrPre = "";
                        String clsHtmlIdxScheduleErrPost = "";
                          int iStartPeriode = 2+(h-COL_EMP_SCH);
                        String dateNumber = "";
                        int dtNumber =0;
                        try{
                            dateNumber = (String.valueOf(schdelueDate.get(""+iStartPeriode))).trim();
                            //dateNumber=dateNumber.substring(0, dateNumber.indexOf("."));
                            dtNumber = Integer.parseInt(dateNumber);
                            //pengecekan dengan colom di schedule
                            if(employeeUpload.getScheduleCheck(iStartPeriode)){    
                                tmp1stSchd = (String.valueOf(employeeUpload.getSchedule(h))).trim();
                                
                                //add by priska menampilkan schedule yang berhasil dirubah saja 20151112
                                if (Command.SAVE == iCommand){
                                long schOid = PstEmpSchedule.getSchedule(dtNumber, employeeUpload.getEmpId(), period.getStartDate());
                                sch = hashScheduleOidKey.get(""+schOid).toString();
                                }
                                  if (hashSchedule.get(tmp1stSchd.trim().toUpperCase()) == null) {
                                     sch = "?";
                                     writeSchd = sch;
                                  }else{
                                    sch = tmp1stSchd.toUpperCase(); 
                                    writeSchd = sch;
                                  }
                         if (sch.equalsIgnoreCase("?")) {
                                clsHtmlIdxScheduleErrPre = "<span class=\"errfont\">";
                                clsHtmlIdxScheduleErrPost = "</span>";
                        }
                                   //prosess pemasukan schedule
                                
                          switch (dtNumber){  
                              case 1:
                                drawList.append("\n\t\t<td " + clsHtml + " width=\"" + width + "%\" align=\"center\">");
                                drawList.append("<input type=\"hidden\" name=\"D1\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"D2ND1\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"CAT1\" value=\"" + hashCategory.get(sch.trim()) + "\">");
                                drawList.append(clsHtmlIdxScheduleErrPre + writeSchd + clsHtmlIdxScheduleErrPost + "</td>"); 
                                break;

                            case 2:
                                drawList.append("\n\t\t<td " + clsHtml + " width=\"" + width + "%\" align=\"center\">");
                                drawList.append("<input type=\"hidden\" name=\"D2\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"D2ND2\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"CAT2\" value=\"" + hashCategory.get(sch.trim()) + "\">");
                                drawList.append(clsHtmlIdxScheduleErrPre + writeSchd + clsHtmlIdxScheduleErrPost + "</td>");
                                break;

                            case 3:
                                drawList.append("\n\t\t<td " + clsHtml + "  width=\"" + width + "%\" align=\"center\">");
                                drawList.append("<input type=\"hidden\" name=\"D3\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"D2ND3\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"CAT3\" value=\"" + hashCategory.get(sch.trim()) + "\">");
                                drawList.append(clsHtmlIdxScheduleErrPre + writeSchd + clsHtmlIdxScheduleErrPost + "</td>");
                                break;

                            case 4:
                                drawList.append("\n\t\t<td " + clsHtml + "  width=\"" + width + "%\" align=\"center\">");
                                drawList.append("<input type=\"hidden\" name=\"D4\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"D2ND4\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"CAT4\" value=\"" + hashCategory.get(sch.trim()) + "\">");
                                drawList.append(clsHtmlIdxScheduleErrPre + writeSchd + clsHtmlIdxScheduleErrPost + "</td>");
                                break;

                            case 5:
                                drawList.append("\n\t\t<td " + clsHtml + "  width=\"" + width + "%\" align=\"center\">");
                                drawList.append("<input type=\"hidden\" name=\"D5\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"D2ND5\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"CAT5\" value=\"" + hashCategory.get(sch.trim()) + "\">");
                                drawList.append(clsHtmlIdxScheduleErrPre + writeSchd + clsHtmlIdxScheduleErrPost + "</td>");
                                break;

                            case 6:
                                drawList.append("\n\t\t<td " + clsHtml + "  width=\"" + width + "%\" align=\"center\">");
                                drawList.append("<input type=\"hidden\" name=\"D6\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"D2ND6\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"CAT6\" value=\"" + hashCategory.get(sch.trim()) + "\">");
                                drawList.append(clsHtmlIdxScheduleErrPre + writeSchd + clsHtmlIdxScheduleErrPost + "</td>");
                                break;

                            case 7:
                                drawList.append("\n\t\t<td " + clsHtml + "  width=\"" + width + "%\" align=\"center\">");
                                drawList.append("<input type=\"hidden\" name=\"D7\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"D2ND7\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"CAT7\" value=\"" + hashCategory.get(sch.trim()) + "\">");
                                drawList.append(clsHtmlIdxScheduleErrPre + writeSchd + clsHtmlIdxScheduleErrPost + "</td>");
                                break;

                            case 8:
                                drawList.append("\n\t\t<td " + clsHtml + "  width=\"" + width + "%\" align=\"center\">");
                                drawList.append("<input type=\"hidden\" name=\"D8\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"D2ND8\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"CAT8\" value=\"" + hashCategory.get(sch.trim()) + "\">");
                                drawList.append(clsHtmlIdxScheduleErrPre + writeSchd + clsHtmlIdxScheduleErrPost + "</td>");
                                break;

                            case 9:
                                drawList.append("\n\t\t<td " + clsHtml + "  width=\"" + width + "%\" align=\"center\">");
                                drawList.append("<input type=\"hidden\" name=\"D9\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"D2ND9\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"CAT9\" value=\"" + hashCategory.get(sch.trim()) + "\">");
                                drawList.append(clsHtmlIdxScheduleErrPre + writeSchd + clsHtmlIdxScheduleErrPost + "</td>");
                                break;

                            case 10:
                                drawList.append("\n\t\t<td " + clsHtml + "  width=\"" + width + "%\" align=\"center\">");
                                drawList.append("<input type=\"hidden\" name=\"D10\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"D2ND10\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"CAT10\" value=\"" + hashCategory.get(sch.trim()) + "\">");
                                drawList.append(clsHtmlIdxScheduleErrPre + writeSchd + clsHtmlIdxScheduleErrPost + "</td>");
                                break;

                            case 11:
                                drawList.append("\n\t\t<td " + clsHtml + "  width=\"" + width + "%\" align=\"center\">");
                                drawList.append("<input type=\"hidden\" name=\"D11\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"D2ND11\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"CAT11\" value=\"" + hashCategory.get(sch.trim()) + "\">");
                                drawList.append(clsHtmlIdxScheduleErrPre + writeSchd + clsHtmlIdxScheduleErrPost + "</td>");
                                break;

                            case 12:
                                drawList.append("\n\t\t<td " + clsHtml + "  width=\"" + width + "%\" align=\"center\">");
                                drawList.append("<input type=\"hidden\" name=\"D12\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"D2ND12\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"CAT12\" value=\"" + hashCategory.get(sch.trim()) + "\">");
                                drawList.append(clsHtmlIdxScheduleErrPre + writeSchd + clsHtmlIdxScheduleErrPost + "</td>");
                                break;

                            case 13:
                                drawList.append("\n\t\t<td " + clsHtml + "  width=\"" + width + "%\" align=\"center\">");
                                drawList.append("<input type=\"hidden\" name=\"D13\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"D2ND13\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"CAT13\" value=\"" + hashCategory.get(sch.trim()) + "\">");
                                drawList.append(clsHtmlIdxScheduleErrPre + writeSchd + clsHtmlIdxScheduleErrPost + "</td>");
                                break;

                            case 14:
                                drawList.append("\n\t\t<td " + clsHtml + "  width=\"" + width + "%\" align=\"center\">");
                                drawList.append("<input type=\"hidden\" name=\"D14\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"D2ND14\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"CAT14\" value=\"" + hashCategory.get(sch.trim()) + "\">");
                                drawList.append(clsHtmlIdxScheduleErrPre + writeSchd + clsHtmlIdxScheduleErrPost + "</td>");
                                break;

                            case 15:
                                drawList.append("\n\t\t<td " + clsHtml + "  width=\"" + width + "%\" align=\"center\">");
                                drawList.append("<input type=\"hidden\" name=\"D15\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"D2ND15\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"CAT15\" value=\"" + hashCategory.get(sch.trim()) + "\">");
                                drawList.append(clsHtmlIdxScheduleErrPre + writeSchd + clsHtmlIdxScheduleErrPost + "</td>");
                                break;

                            case 16:
                                drawList.append("\n\t\t<td " + clsHtml + "  width=\"" + width + "%\" align=\"center\">");
                                drawList.append("<input type=\"hidden\" name=\"D16\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"D2ND16\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"CAT16\" value=\"" + hashCategory.get(sch.trim()) + "\">");
                                drawList.append(clsHtmlIdxScheduleErrPre + writeSchd + clsHtmlIdxScheduleErrPost + "</td>");
                                break;

                            case 17:
                                drawList.append("\n\t\t<td " + clsHtml + "  width=\"" + width + "%\" align=\"center\">");
                                drawList.append("<input type=\"hidden\" name=\"D17\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"D2ND17\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"CAT17\" value=\"" + hashCategory.get(sch.trim()) + "\">");
                                drawList.append(clsHtmlIdxScheduleErrPre + writeSchd + clsHtmlIdxScheduleErrPost + "</td>");
                                break;

                            case 18:
                                drawList.append("\n\t\t<td " + clsHtml + "  width=\"" + width + "%\" align=\"center\">");
                                drawList.append("<input type=\"hidden\" name=\"D18\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"D2ND18\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"CAT18\" value=\"" + hashCategory.get(sch.trim()) + "\">");
                                drawList.append(clsHtmlIdxScheduleErrPre + writeSchd + clsHtmlIdxScheduleErrPost + "</td>");
                                break;

                            case 19:
                                drawList.append("\n\t\t<td " + clsHtml + "  width=\"" + width + "%\" align=\"center\">");
                                drawList.append("<input type=\"hidden\" name=\"D19\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"D2ND19\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"CAT19\" value=\"" + hashCategory.get(sch.trim()) + "\">");
                                drawList.append(clsHtmlIdxScheduleErrPre + writeSchd + clsHtmlIdxScheduleErrPost + "</td>");
                                break;

                            case 20:
                                drawList.append("\n\t\t<td " + clsHtml + "  width=\"" + width + "%\" align=\"center\">");
                                drawList.append("<input type=\"hidden\" name=\"D20\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"D2ND20\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"CAT20\" value=\"" + hashCategory.get(sch.trim()) + "\">");
                                drawList.append(clsHtmlIdxScheduleErrPre + writeSchd + clsHtmlIdxScheduleErrPost + "</td>");
                                break;

                            case 21:
                                drawList.append("\n\t\t<td " + clsHtml + "  width=\"" + width + "%\" align=\"center\">");
                                drawList.append("<input type=\"hidden\" name=\"D21\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"D2ND21\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"CAT21\" value=\"" + hashCategory.get(sch.trim()) + "\">");
                                drawList.append(clsHtmlIdxScheduleErrPre + writeSchd + clsHtmlIdxScheduleErrPost + "</td>");
                                break;

                            case 22:
                                drawList.append("\n\t\t<td " + clsHtml + " width=\"" + width + "%\" align=\"center\">");
                                drawList.append("<input type=\"hidden\" name=\"D22\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"D2ND22\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"CAT22\" value=\"" + hashCategory.get(sch.trim()) + "\">");
                                drawList.append(clsHtmlIdxScheduleErrPre + writeSchd + clsHtmlIdxScheduleErrPost + "</td>");
                                break;

                            case 23:
                                drawList.append("\n\t\t<td  " + clsHtml + " width=\"" + width + "%\" align=\"center\">");
                                drawList.append("<input type=\"hidden\" name=\"D23\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"D2ND23\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"CAT23\" value=\"" + hashCategory.get(sch.trim()) + "\">");
                                drawList.append(clsHtmlIdxScheduleErrPre + writeSchd + clsHtmlIdxScheduleErrPost + "</td>");
                                break;

                            case 24:
                                drawList.append("\n\t\t<td " + clsHtml + "  width=\"" + width + "%\" align=\"center\">");
                                drawList.append("<input type=\"hidden\" name=\"D24\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"D2ND24\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"CAT24\" value=\"" + hashCategory.get(sch.trim()) + "\">");
                                drawList.append(clsHtmlIdxScheduleErrPre + writeSchd + clsHtmlIdxScheduleErrPost + "</td>");
                                break;

                            case 25:
                                drawList.append("\n\t\t<td " + clsHtml + "  width=\"" + width + "%\" align=\"center\">");
                                drawList.append("<input type=\"hidden\" name=\"D25\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"D2ND25\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"CAT25\" value=\"" + hashCategory.get(sch.trim()) + "\">");
                                drawList.append(clsHtmlIdxScheduleErrPre + writeSchd + clsHtmlIdxScheduleErrPost + "</td>");
                                break;

                            case 26:
                                drawList.append("\n\t\t<td  " + clsHtml + " width=\"" + width + "%\" align=\"center\">");
                                drawList.append("<input type=\"hidden\" name=\"D26\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"D2ND26\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"CAT26\" value=\"" + hashCategory.get(sch.trim()) + "\">");
                                drawList.append(clsHtmlIdxScheduleErrPre + writeSchd + clsHtmlIdxScheduleErrPost + "</td>");
                                break;

                            case 27:
                                drawList.append("\n\t\t<td " + clsHtml + "  width=\"" + width + "%\" align=\"center\">");
                                drawList.append("<input type=\"hidden\" name=\"D27\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"D2ND27\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"CAT27\" value=\"" + hashCategory.get(sch.trim()) + "\">");
                                drawList.append(clsHtmlIdxScheduleErrPre + writeSchd + clsHtmlIdxScheduleErrPost + "</td>");
                                break;

                            case 28:
                                drawList.append("\n\t\t<td " + clsHtml + "  width=\"" + width + "%\" align=\"center\">");
                                drawList.append("<input type=\"hidden\" name=\"D28\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"D2ND28\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"CAT28\" value=\"" + hashCategory.get(sch.trim()) + "\">");
                                drawList.append(clsHtmlIdxScheduleErrPre + writeSchd + clsHtmlIdxScheduleErrPost + "</td>");
                                break;

                            case 29:
                                drawList.append("\n\t\t<td " + clsHtml + "  width=\"" + width + "%\" align=\"center\">");
                                drawList.append("<input type=\"hidden\" name=\"D29\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"D2ND29\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"CAT29\" value=\"" + hashCategory.get(sch.trim()) + "\">");
                                drawList.append(clsHtmlIdxScheduleErrPre + writeSchd + clsHtmlIdxScheduleErrPost + "</td>");
                                break;

                            case 30:
                                drawList.append("\n\t\t<td " + clsHtml + "  width=\"" + width + "%\" align=\"center\">");
                                drawList.append("<input type=\"hidden\" name=\"D30\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"D2ND30\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"CAT30\" value=\"" + hashCategory.get(sch.trim()) + "\">");
                                drawList.append(clsHtmlIdxScheduleErrPre + writeSchd + clsHtmlIdxScheduleErrPost + "</td>");
                                break;

                            case 31:
                                drawList.append("\n\t\t<td " + clsHtml + "  width=\"" + width + "%\" align=\"center\">");
                                drawList.append("<input type=\"hidden\" name=\"D31\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"D2ND31\" value=\"" + hashSchedule.get(sch.trim()) + "\">");
                                drawList.append("<input type=\"hidden\" name=\"CAT31\" value=\"" + hashCategory.get(sch.trim()) + "\">");
                                drawList.append(clsHtmlIdxScheduleErrPre + writeSchd + clsHtmlIdxScheduleErrPost + "</td>");
                                break;

                            default:
                                break;
                          }
                               
                            }
                        }
                        catch (Exception exc){   

                        }
                        
                        
                    }
                    drawList.append("\n\t</tr>");  
                                       } catch (Exception exc){
                                           error = "error";
                                       }
                }
                    drawList.append("\n\t</tr> " + "\n</tbody>" + "\n</table>");  
                }
                    

                    drawList.append("<br>" +
                            "\n<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" align=\"left\">");
                    
                    if (iCommand == Command.SAVE && (msgString != null && msgString.length() > 0)){
                        drawList.append("\n\t<tr>" +
                                "\n\t\t<td colspan=\"4\">" + msgString + "</td>" +
                                "\n\t</tr>" +
                                "\n\t<tr>" +
                                "\n\t\t<td colspan=\"4\">&nbsp;</td>" +
                                "\n\t</tr>");
                    }
                    //if (iCommand != Command.SAVE) {
//                    drawList.append("\n\t<tr>" +
//                            "\n\t\t<td width=\"1%\"><img src=\"" + approot + "/images/spacer.gif\" width=\"4\" height=\"4\"></td>" +
//                            "\n\t\t<td width=\"5%\"><a href=\"javascript:cmdSave()\" onMouseOut=\"MM_swapImgRestore()\" onMouseOver=\"MM_swapImage('Image300','','" + approot + "/images/BtnSaveOn.jpg',1)\"><img name=\"Image300\" border=\"0\" src=\"" + approot + "/images/BtnSave.jpg\" width=\"24\" height=\"24\" alt=\"Save\"></a></td>" +
//                            "\n\t\t<td width=\"1%\"><img src=\"" + approot + "/images/spacer.gif\" width=\"4\" height=\"4\"></td>" +
//                            "\n\t\t<td width=\"93%\"nowrap> <a href=\"javascript:cmdSave()\" class=\"command\">Save Working Schedule</a></td>" +
//                            "\n\t</tr>"); 
                    //   }
                    drawList.append("\n</table>");
                
        }
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <title>Working Schedule | Dimata Hairisma</title>
        <!-- Tell the browser to be responsive to screen width -->
        <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
        
        <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
        <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
        <!--[if lt IE 9]>
            <script src="<%= approot%>/assets/html5shiv/3.7.3/html5shiv.min.js"></script>
            <script src="<%= approot%>/assets/respond/1.4.2/respond.min.js"></script>
        <![endif]-->
        <style>
            #masterdata_icon {
                color: black;
            }
            #masterdata_icon:hover {
                color: #0088cc;
            }
            .modal-lg{
	    max-width: 1200px;
	    width: 100%;
            }
            
            #tableperiodElement th { font-size: 14px; }
            #tableperiodElement td { font-size: 12px; 
                width: 1px;
                white-space: nowrap;
            }
            
            #customfilter .select2{
                margin-top: 20px;
            }
            
        </style>
        <style>
            #masterdata_icon {
                color: black;
            }
            #masterdata_icon:hover {
                color: #0088cc;
            }
            .modal-lg{
	    max-width: 1200px;
	    width: 100%;
            }
            
            /* declare a 7 column grid on the table */
            #calendar {
                    width: 100%;
              display: grid;
              grid-template-columns: repeat(7, 1fr);
            }

            #calendar tr, #calendar tbody {
              grid-column: 1 / -1;
              display: grid;
              grid-template-columns: repeat(7, 1fr);
             width: 100%;
            }

            caption {
                    text-align: center;
              grid-column: 1 / -1;
              font-size: 130%;
              font-weight: bold;
              padding: 10px 0;
            }

            #calendar a {
                    color: #8e352e;
                    text-decoration: none;
            }

            #calendar td, #calendar th {
                    padding: 5px;
                    box-sizing:border-box;
                    border: 1px solid #ccc;
            }

            #calendar .weekdays {
                    background: #8e352e;  
            }


            #calendar .weekdays th {
                    text-align: center;
                    text-transform: uppercase;
                    line-height: 20px;
                    border: none !important;
                    padding: 10px 6px;
                    color: #fff;
                    font-size: 13px;
            }

            #calendar td {
                    min-height: 50px;
              display: flex;
              flex-direction: column;
            }

            #calendar .days li:hover {
                    background: #d3d3d3;
            }

            #calendar .date {
                    text-align: center;
                    margin-bottom: 5px;
                    padding: 4px;
                    background: #333;
                    color: #fff;
                    width: 25px;
                    border-radius: 50%;
              flex: 0 0 auto;
              align-self: flex-end;
            }

            #calendar .event {
              flex: 0 0 auto;
                    font-size: 13px;
                    border-radius: 4px;
                    padding: 5px;
                    margin-bottom: 5px;
                    line-height: 14px;
                    background: #e4f2f2;
                    border: 1px solid #b5dbdc;
                    color: #009aaf;
                    text-decoration: none;
            }

            #calendar .event-desc {
                    color: #666;
                    margin: 3px 0 7px 0;
                    text-decoration: none;	
            }

            #calendar .other-month {
                    background: #f5f5f5;
                    color: #666;
            }

            /* ============================
                                            Mobile Responsiveness
               ============================*/


            @media(max-width: 768px) {

                    #calendar .weekdays, #calendar .other-month {
                            display: none;
                    }

                    #calendar li {
                            height: auto !important;
                            border: 1px solid #ededed;
                            width: 100%;
                            padding: 10px;
                            margin-bottom: -1px;
                    }

              #calendar, #calendar tr, #calendar tbody {
                grid-template-columns: 1fr;
              }

              #calendar  tr {
                grid-column: 1 / 2;
              }

                    #calendar .date {
                            align-self: flex-start;
                    }
            }
            
            @media (min-width: 992px) {
                .modal-lg {
                  width: 2000px;
                }
              }
              
              .table>tbody>tr>td {
                     vertical-align: middle !important;
                }
                
            .upschedule-modal-body{
                overflow-x:auto;
            }
            
        </style>
       <%@ include file="../../template/css.jsp" %>
    </head>
    
    <body class="hold-transition skin-blue sidebar-mini sidebar-collapse">
        <input type="hidden" name="command" id="command" value="<%= Command.NONE %>">
        <input type="hidden" name="approot" id="approot" value="<%= approot %>">
        <div class="wrapper">
            <%@ include file="../../template/header.jsp" %>
            <%@ include file="../../template/sidebar.jsp" %>
            <div class="content-wrapper">
                <section class="content-header">
                    <h1>
                        Attendance
                    </h1>
                    <ol class="breadcrumb">
                        <li><a href="../../home.jsp"><i class="fa fa-home"></i> Home</a></li>
                        <li class="active">Attendance</li>
                        <li class="active">Working Schedule</li>
                    </ol>
                </section>
                <!-- Main content -->
                <section class="content">
                  <!-- Small boxes (Stat box) -->
                  <div class="row">
                    <div class="col-md-12">
                        <div class="box box-primary collapsed-box">
                            
                            <div class="box-header with-border">
                                <h3 class="box-title">Advance Search</h3>
                                <div class="box-tools pull-right">
                                    <button class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-plus"></i></button>
                                </div><!-- /.box-tools -->
                            </div><!-- /.box-header -->
                            <form id="customSearch" method="get" action="">
                                <div class="box-body">
                                    
                                    <input type="hidden" name="FRM_FIELD_DATA_FOR" id="generaldatafor">
                                    <div class="row">
                                        <div class="col-md-8">
                                            <div class="form-group">
                                                <label class="col-sm-1 control-label">Filter</label>
                                                <div class="col-sm-4">
                                                    <select name="filter" id="filter" class="form-control comboFilter" style="width: 100%;">
                                                        <option value="all">All</option>
                                                        <option value="emp_num">Employee Number</option>
                                                        <option value="emp_name">Employee Name</option>
                                                        <option value="company">Company</option>
                                                        <option value="division">Division</option>
                                                        <option value="department">Department</option>
                                                        <option value="section">Section</option>
                                                        <option value="position">Position</option>
                                                        <option value="emp_cat">Employee Category</option>
                                                        <option value="resign">Resign Status</option>
                                                    </select>
                                                </div>
                                                <div class="col-sm-1">
                                                    <button id="add" class="btn btn-primary" type="button"><i class="fa fa-check"></i> Add</button>
                                                </div>    
                                                <div class="col-sm-2">
                                                    <button id="clear" class="btn btn-warning" type="button" style="display:none;"><i class="fa fa-eraser"></i> Clear</button>
                                                </div>  
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-12">
                                            <div class="form-group"  id="customfilter"></div>
                                        </div>
                                            
                                    </div>
                                    <div class="row">
                                    </div>
                                        
                                </div><!-- /.box-body -->
                                <div class="box-footer">
                                    <button id="search" class="btn btn-primary" type="submit"><i class='fa fa-filter'></i> Filter</button>
                                </div>
                            </form>
                        </div>
                        <div class='box box-primary'>
                            <div class='box-header'>
                                <div class="col-md-10" align="left">
                                <button class="btn btn-primary btnaddgeneral" data-oid="0" data-for="addSchedule">
                                    <i class="fa fa-plus"></i> Add Working Schedule
                                </button>
                                <button class="btn btn-danger btndeletedivision" data-for="division">
                                    <i class="fa fa-trash"></i> Delete
                                </button>
                                <button class="btn btn-success btnupload" data-for="division">
                                    <i class="fa fa-file"></i> Upload From Excel
                                </button>    
                                <button class="btn btn-success" onclick="javascript:cmdPrintXLS()">
                                    <i class="fa fa-file"></i> Export To Excel
                                </button>  
                                </div>
                            </div>
                            <div class="box-body">
                                <div class="col-md-10" align="left">
                                    <form class="form-horizontal">
                                        <div class="form-group">
                                            <label class="control-label" for="period">Period:</label>
                                            <select name="period" id="period">
                                                <%
                                                    Vector listPeriodX = PstPeriod.list(0, 0, "", PstPeriod.fieldNames[PstPeriod.FLD_START_DATE]+ " DESC");
                                                    if (listPeriodX.size() > 0){
                                                        for (int i=0; i < listPeriodX.size(); i++){
                                                             Period period = (Period) listPeriodX.get(i);                                                   
                                                 %>
                                                            <option value="<%=period.getOID()%>"><%=period.getPeriod()%></option>
                                                 <%
                                                        }

                                                    }
                                                %>
                                            </select>
                                        </div>
                                    </form>
                                </div>
                                <div id="periodElement">
                                    <table class="table table-bordered table-striped" width="100%">
                                        <thead>
                                            <tr>
                                                <th  style="width:2%"></th>
                                                <th  style="width:8%">Period</th>
                                                <th  style="width:30%">Employee</th>
                                                <%
                                                        Date now = new Date();
                                                        int monthStartDate = Integer.parseInt(String.valueOf(now.getMonth()));
                                                        int yearStartDate =  Integer.parseInt(String.valueOf(now.getYear()+1900));
                                                        int dateStartDate =  Integer.parseInt(String.valueOf(now.getDate()));
                                                        int startDatePeriod = Integer.parseInt(String.valueOf(PstSystemProperty.getValueByName("START_DATE_PERIOD")));
                                                
                                                        GregorianCalendar periodStart = new GregorianCalendar(yearStartDate, monthStartDate-1, dateStartDate);
                                                        int maxDayOfMonth = periodStart.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);	
                                                   %>
                                                     <th class="no-sort" style="width:2%"><%=startDatePeriod%></th>   
                                                    <%
                                                        for(int i = 0 ; i < maxDayOfMonth-1 ; i++) {
                                                                        if(startDatePeriod == maxDayOfMonth){
                                                                                startDatePeriod = 1;
                                                                                %>
                                                                                    <th class="no-sort" style="width:2%"><%=startDatePeriod%></th> 
                                                                                <%
                                                                        }
                                                                        else {
                                                                                startDatePeriod = startDatePeriod +1;
                                                                                %>
                                                                                    <th class="no-sort" style="width:2%"><%=startDatePeriod%></th> 
                                                                                <%

                                                                        }

                                                        }
                                                %>
                                                <th>Action</th>
                                            </tr>
                                        </thead>
                                    </table>
                                </div>
                            </div>
                            <div class='box-footer'>
                                
                            </div>
                        </div>
                    </div><!-- ./col -->
                  </div><!-- /.row -->

                </section><!-- /.content -->
<!--                <section class="content">
                    <div class="row">
                        <div class="col-md-12">
                            <div class="box">
                                <div class="box-body">
                                    <div class="row">
                                        <div class="col-md-12">
                                            <div class="col-xs-12">
                                                <a> <h3>Period List : </h3></a>
                                                <hr>
                                                <div class="table-responsive">
                                                <div id="listdata">
                                                </div>
                                                </div>    
                                            </div>
                                            <div class="box-footer">
                                    <div class="row pull-left">
                                        <div class="col-md-12">
                                            <button type="button" class="btn btn-primary btn-sm fa fa-plus addeditdata" data-oid="0" data-for="showperiodform" data-command="0"> Add Period</button> 
                                            
                                             Modal 
                                                <div id="myModal" class="modal fade" role="dialog" tabindex="-1" aria-labelledby="myModalLabel" aria-hidden="true">
                                                     <div class="modal-dialog">

                                                         Modal content
                                                         
                                                            <div class="modal-content">
                                                            <div class="modal-header">
                                                            <button type="button" class="close" data-dismiss="modal">&times;</button>
                                                            <h4 class="addeditgeneral-title"></h4>
                                                            </div>
                                                            <form id="form-modal">
                                                             <input type="hidden" name="oid" id="oid">
                                                             <input type="hidden" name="datafor" id="datafor">
                                                             <input type="hidden" name="command" id="command">
                                                            <div class="modal-body active-scroll" id="modalbody" >
                                                            </div>
                                                            <div class="modal-footer">
                                                                 <button type="button" class="btn btn-default" data-dismiss="modal">CANCEL</button>
                                                                 <button type="submit" class="btn btn-primary" id="btnsave">SAVE</button>
                                                            </div>
                                                            </form>
                                                            </div>
                                                            </div>
                                                        </div>
                                                        
                                                        <div id="mdl_delete" class="modal fade" role="dialog">
                                                        <div class="modal-dialog">
                                                        <div class="modal-content">
                                                            <div class="modal-body">
                                                            <button type="button" class="close" data-dismiss="modal">&times;</button>
                                                            <h4 class="modal-title">Delete Period</h4>
                                                            </div>
                                                            <div class="modal-body">
                                                                
                                                                Are You Sure ? 
                                                            </div>
                                                            <div class="modal-footer">
                                                            <button type="button" data-dismiss="modal" class="btn btn-primary btn-danger" id="delete">Delete</button>
                                                            <button type="button" data-dismiss="modal" class="btn">Cancel</button>
                                                            </div>
                                                            </div>
                                                        </div>
                                                        </div>
                                            
                                                    </div>
                                                </div>
                                        
                                        
                                        </div>
                                    </div>
                                        </div>
                                        </div>
                                    </div>
                                </div>
                            </div>-->
                        </div>
           
            <%@include file="../../template/plugin.jsp" %>
            <%@ include file="../../template/footer.jsp" %>
        
        
        
        <script type="text/javascript">
            
        function cmdPrintXLS(){
           // alert("tes");
               // var checkBoxes = (".<%= FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_EMP_SCHEDULE_ID]%>");
                var oidPeriod = $("#period").val();
                var empNum = $("#employee_number").val();
                if (typeof empNum === "undefined"){
                    empNum = "";
                }
                var empName = $("#employee_name").val();
                if (typeof empName === "undefined"){
                    empName = "";
                }
                var companyId = $("#company_id").val();
                if (typeof companyId === "undefined"){
                    companyId = 0;
                }
                var divisionId = $("#division_id").val();
                if (typeof divisionId === "undefined"){
                    divisionId = 0;
                }
                var departmentId = $("#department_id").val();
                if (typeof departmentId === "undefined"){
                    departmentId = 0;
                }
                var sectionId = $("#section_id").val();
                if (typeof sectionId === "undefined"){
                    sectionId = 0;
                }
                var positionId = $("#position_id").val();
                if (typeof positionId === "undefined"){
                    positionId = 0;
                }
                var levelId = $("#level_id").val();
                if (typeof levelId === "undefined"){
                    levelId = 0;
                }
                var empCatId = $("#category_id").val();
                if (typeof empCatId === "undefined"){
                    empCatId = 0;
                }
                var resign = null;
                $(".resign").each(function (i) {
                    if ($(this).is(":checked")) {
                        resign = $(".resign:checked").val();
                    }
                });
                var dataAjaxSource = "?FRM_FIELD_PERIOD="+oidPeriod+"&FRM_FIELD_EMPNUMBER="+empNum+"&FRM_FIELD_EMPLOYEE="+empName;
                dataAjaxSource = dataAjaxSource + "&FRM_FIELD_COMPANY="+companyId+"&FRM_FIELD_DIVISION="+divisionId;
                dataAjaxSource = dataAjaxSource + "&FRM_FIELD_DEPARTMENT="+departmentId+"&FRM_FIELD_SECTION="+sectionId;
                dataAjaxSource = dataAjaxSource + "&FRM_FIELD_POSITION="+positionId+"&FRM_FIELD_RESIGNED="+resign;
                dataAjaxSource = dataAjaxSource + "&level_id="+levelId+"&FRM_FIELD_EMP_CATEGORY="+empCatId+"&level_id="+levelId;
               /* var vals = "";
                $(".<!--%= FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_EMP_SCHEDULE_ID]%>").each(function(i){

                    if(!$(this).is(":checked") || $(this).is(":checked")){
                        if(vals.length == 0){
                            vals += ""+$(this).val();
                        }else{
                            vals += ","+$(this).val();
                        }
                    }
                });*/
                
                //alert(vals);
                
		window.open("<%=approot%>/servlet/com.dimata.harisma.report.EmpScheduleListXLS"+dataAjaxSource, "Report") //, "status=yes,toolbar=no,menubar=yes,location=no");
                //window.location.href = "< %=approot%>/servlet/com.dimata.harisma.report.EmpScheduleListXLS";
	}
            
	$(document).ready(function(){
            
            var config = {
                '.chosen-select'           : {},
                '.chosen-select-deselect'  : {allow_single_deselect:true},
                '.chosen-select-no-single' : {disable_search_threshold:10},
                '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
                '.chosen-select-width'     : {width:"100%"}
            }
            for (var selector in config) {
                $(selector).chosen(config[selector]);
            }
	    //SET ACTIVE MENU
	    var activeMenu = function(parentId, childId){
		$(parentId).addClass("active").find(".treeview-menu").slideDown();
		$(childId).addClass("active");
	    }

	    activeMenu("#masterdata", "#division");
	    
	    
	    var approot = $("#approot").val();
	    var command = $("#command").val();
	    var dataSend = null;
	    
	    var oid = null;
	    var dataFor = null;

	    //FUNCTION VARIABLE
	    var onDone = null;
	    var onSuccess = null;
	    var callBackDataTables = null;
	    var iCheckBox = null;
	    var addeditgeneral = null;
	    var exportgeneral = null;
	    var areaTypeForm = null;
	    var deletegeneral = null;
	    
	    //MODAL SETTING
	    var modalSetting = function(elementId, backdrop, keyboard, show){
		$(elementId).modal({
		    backdrop	: backdrop,
		    keyboard	: keyboard,
		    show	: show
		});
	    };
            
            function datePicker(contentId, formatDate){
		$(contentId).datepicker({
		    format : formatDate
		});
		$(contentId).on('changeDate', function(ev){
		    $(this).datepicker('hide');
		});
	    }
	    
	    var getDataFunction = function(onDone, onSuccess, approot, command, dataSend, servletName, dataAppendTo, notification, dataType){
		/*
		 * getDataFor	: # FOR PROCCESS FILTER
		 * onDone	: # ON DONE FUNCTION,
		 * onSuccess	: # ON ON SUCCESS FUNCTION,
		 * approot	: # APPLICATION ROOT,
		 * dataSend	: # DATA TO SEND TO THE SERVLET,
		 * servletName  : # SERVLET'S NAME,
		 * dataType	: # Data Type (JSON, HTML, TEXT)
		 */
		$(this).getData({
		   onDone	: function(data){
		       onDone(data);
		   },
		   onSuccess	: function(data){
			onSuccess(data);
		   },
		   approot	: approot,
		   dataSend	: dataSend,
		   servletName	: servletName,
		   dataAppendTo	: dataAppendTo,
		   notification : notification,
		   ajaxDataType	: dataType
		});
	    }
	    
	    //SHOW ADD OR EDIT FORM
	    addeditgeneral = function(elementId){
		$(elementId).click(function(){
		    $("#addeditgeneral").modal("show");
		    command = $("#command").val();
		    oid = $(this).data('oid');
		    dataFor = $(this).data('for');
		    $("#generaldatafor").val(dataFor);
		    $("#oid").val(oid);

		    //SET TITLE MODAL
		    if(oid != 0){
			if(dataFor == 'showperiodform'){
			    $(".addeditgeneral-title").html("Edit Period");
			}

		    }else{
			if(dataFor == 'showperiodform'){
			    $(".addeditgeneral-title").html("Add Period");
			}
		    }


		    dataSend = {
			"FRM_FIELD_DATA_FOR"	: dataFor,
			"FRM_FIELD_OID"		 : oid,
			"command"		 : command
		    }
		    onDone = function(data){
                        datePicker(".datepicker", "yyyy-mm-dd");
			$(".colorpicker").colorpicker();
                        $(".chosen-select").chosen();
                        $("#period1").unbind().change(function(){
                            var oidPeriod = $(this).val();
                            dataFor = $(this).data("for");
                            var target = $(this).data("replacement");
                            var dataSends = {
                                "FRM_FIELD_DATA_FOR": dataFor,
                                "command": command,
                                "OID_PERIOD" : oidPeriod
                            }
                            var onDones = function(data){
                                $("#calendarContainer").append(data.FRM_FIELD_HTML);
                            };
                            var onSuccesses = function(data){

                            };
                            getDataFunction(onDones, onSuccesses, approot, command, dataSends, "AjaxEmpWorkingSchedule", target, false, "json");
                        });
		    };
		    onSuccess = function(data){
			
		    };
		    getDataFunction(onDone, onSuccess, approot, command, dataSend, "AjaxEmpWorkingSchedule", ".addeditgeneral-body", false, "json");
		});
	    };
	    
	    //DELETE GENERAL
	    deletegeneral = function(elementId, checkboxelement){

		$(elementId).click(function(){
		    dataFor = $(this).data("for");
		    var checkBoxes = (checkboxelement);
		    var vals = "";
		    $(checkboxelement).each(function(i){

			if($(this).is(":checked")){
			    if(vals.length == 0){
				vals += ""+$(this).val();
			    }else{
				vals += ","+$(this).val();
			    }
			}
		    });
                    
		    var confirmText = "Are you sure want to delete these data?";
		    if(vals.length == 0){
			alert("Please select the data");
		    }else{
			command = <%= Command.DELETEALL %>;
			var currentHtml = $(this).html();
			$(this).html("Deleting...").attr({"disabled":true});
			if(confirm(confirmText)){
			    dataSend = {
				"FRM_FIELD_DATA_FOR"	    : dataFor,
				"FRM_FIELD_OID_DELETE"	    : vals,
				"command"		    : command
			    };
			    onSuccess = function(data){

			    };
			    if(dataFor == "division"){
				onDone = function(data){
				    runDataTables();
				};
			    }
			    getDataFunction(onDone, onSuccess, approot, command, dataSend, "AjaxEmpWorkingSchedule", null, true, "json");
			    $(this).removeAttr("disabled").html(currentHtml);
			}else{
			    $(this).removeAttr("disabled").html(currentHtml);
			}
		    }

		});
	    };
	    
            var deletesingle = function (elementId) {

                $(elementId).unbind().click(function () {
                    dataFor = $(this).data("for");
                    var vals = $(this).data("oid");
                    var empid = $(this).data("empid");
                    var delResign = $(this).data("resign");
                    var servletName = "";
                    var confirmTextSingle = "Are you sure want to delete these data?";

                    command = <%= Command.DELETE%>;
                    var currentHtml = $(this).html();
                    $(this).html("Deleting...").attr({"disabled": true});
                    if (confirm(confirmTextSingle)) {
                        dataSend = {
                            "FRM_FIELD_DATA_FOR": dataFor,
                            "FRM_FIELD_OID": vals,
                            "command": command
                        };
                        onSuccess = function (data) {

                        };

                        onDone = function(data){
                            runDataTables();
                        };
                        getDataFunction(onDone, onSuccess, approot, command, dataSend, "AjaxEmpWorkingSchedule", null, true, "json");
                        $(this).removeAttr("disabled").html(currentHtml);
                    } else {
                            $(this).removeAttr("disabled").html(currentHtml);
                        }
                });
            };
            
	    //FUNCTION FOR DATA TABLES
	    callBackDataTables = function(){
		addeditgeneral(".btneditgeneral");
                deletesingle(".btndeletesingle");
		iCheckBox();
	    }

	    

	    //VALIDATE FORM
	    function validateOptions (elementId, checkType, classError, minLength, matchValue){

		/* OPTIONS
		 * minLength    : INT VALUE,
		 * matchValue   : STRING OR INT VALUE,
		 * classError   : STRING VALUE,
		 * checkType    : STRING VALUE ('text' for default, 'email' for email check
		 */

		$(elementId).validate({
		    minLength   : minLength,
		    matchValue  : matchValue,
		    classError  : classError,
		    checkType   : checkType
		});
	    }

	    //iCheck
	    iCheckBox = function(){
		$("input[type='checkbox'], input[type='radio']").iCheck({
		    checkboxClass: 'icheckbox_minimal-blue',
		    radioClass: 'iradio_minimal-blue'
		});

		
	    }


	    //DATA TABLES SETTING
	    function dataTablesOptions(elementIdParent, elementId, servletName, dataFor, callBackDataTables){
                var table = $('#'+elementId).DataTable();
                table.destroy();
		var datafilter = $("#datafilter").val();
		var privUpdate = $("#privUpdate").val();
                var oidPeriod = $("#period").val();
                var empNum = $("#employee_number").val();
                if (typeof empNum === "undefined"){
                    empNum = "";
                }
                var empName = $("#employee_name").val();
                if (typeof empName === "undefined"){
                    empName = "";
                }
                var companyId = $("#company_id").val();
                if (typeof companyId === "undefined"){
                    companyId = 0;
                }
                var divisionId = $("#division_id").val();
                if (typeof divisionId === "undefined"){
                    divisionId = 0;
                }
                var departmentId = $("#department_id").val();
                if (typeof departmentId === "undefined"){
                    departmentId = 0;
                }
                var sectionId = $("#section_id").val();
                if (typeof sectionId === "undefined"){
                    sectionId = 0;
                }
                var positionId = $("#position_id").val();
                if (typeof positionId === "undefined"){
                    positionId = 0;
                }
                var levelId = $("#level_id").val();
                if (typeof levelId === "undefined"){
                    levelId = 0;
                }
                var empCatId = $("#category_id").val();
                if (typeof empCatId === "undefined"){
                    empCatId = 0;
                }
                var resign = null;
                $(".resign").each(function (i) {
                    if ($(this).is(":checked")) {
                        resign = $(".resign:checked").val();
                    }
                });
                var dataAjaxSource = "&FRM_FIELD_EMPNUMBER="+empNum+"&FRM_FIELD_EMPLOYEE="+empName;
                dataAjaxSource = dataAjaxSource + "&FRM_FIELD_COMPANY="+companyId+"&FRM_FIELD_DIVISION="+divisionId;
                dataAjaxSource = dataAjaxSource + "&FRM_FIELD_DEPARTMENT="+departmentId+"&FRM_FIELD_SECTION="+sectionId;
                dataAjaxSource = dataAjaxSource + "&FRM_FIELD_POSITION="+positionId+"&FRM_FIELD_RESIGNED="+resign;
                dataAjaxSource = dataAjaxSource + "&level_id="+levelId+"&FRM_FIELD_EMP_CATEGORY="+empCatId+"&level_id="+levelId;
		$(elementIdParent).find('table').addClass('table-bordered table-striped table-hover').attr({'id':elementId});
		$("#"+elementId).dataTable({"bDestroy": true,
                    "scrollX":true,
                    "searching": false,
		    "iDisplayLength": 10,
		    "bProcessing" : true,
		    "oLanguage" : {
			"sProcessing" : "<div class='progress progress-striped active'><div class='progress-bar progress-bar-primary' style='width: 100%'><b>Please Wait...</b></div></div>"
		    },
		    "bServerSide" : true,
		    "sAjaxSource" : "<%= approot %>/"+servletName+"?command=<%= Command.LIST%>&FRM_FIELD_DATA_FILTER="+datafilter+"&FRM_FIELD_DATA_FOR="+dataFor+"&privUpdate="+privUpdate+"&FRM_FIELD_PERIOD="+oidPeriod+dataAjaxSource,
		    aoColumnDefs: [
			{
			   bSortable: false,
			   aTargets: ['no-sort']
			}
		      ],
		    "initComplete": function(settings, json) {
			if(callBackDataTables != null){
			    callBackDataTables();
			}
		    },
		    "fnDrawCallback": function( oSettings ) {
			if(callBackDataTables != null){
			    callBackDataTables();
			}
		    },
		    "fnPageChange" : function(oSettings){

		    }
		});

		$(elementIdParent).find("#"+elementId+"_filter").find("input").addClass("form-control");
		$(elementIdParent).find("#"+elementId+"_length").find("select").addClass("form-control");
		$("#"+elementId).css("width","100%");
	    }
	    
	    function runDataTables(){
		dataTablesOptions("#periodElement", "tableperiodElement", "AjaxEmpWorkingSchedule", "listSchedule", callBackDataTables);
	    }
	    
	    modalSetting("#addeditgeneral", "static", false, false);
	    addeditgeneral(".btnaddgeneral");
	    deletegeneral(".btndeletedivision", ".<%= FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_EMP_SCHEDULE_ID]%>");
		    
	    runDataTables();
            
            $("#period").change(function(){
                runDataTables();
            });
            
            $("form#customSearch").submit(
                    function(){
                    runDataTables();
                    return false;
                });

	    //FORM SUBMIT
	    $("form#generalform").submit(function(){
		var currentBtnHtml = $("#btngeneralform").html();
		$("#btngeneralform").html("Saving...").attr({"disabled":"true"});
		var generaldatafor = $("#generaldatafor").val();
                onDone = function(data){
                    runDataTables();
                };
		

		if($(this).find(".has-error").length == 0){
		    onSuccess = function(data){
			$("#btngeneralform").removeAttr("disabled").html(currentBtnHtml);
			$("#addeditgeneral").modal("hide");
		    };

		    dataSend = $(this).serialize();
		    getDataFunction(onDone, onSuccess, approot, command, dataSend, "AjaxEmpWorkingSchedule", null, true, "json");
		}else{
		    $("#btngeneralform").removeAttr("disabled").html(currentBtnHtml);
		}

		return false;
	    });
            
            $("form#upscheduleform").submit(function(){
		var currentBtnHtml = $("#btngeneralform").html();
		$("#btnupscheduleform").html("Saving...").attr({"disabled":"true"});
		var generaldatafor = $("#generaldatafor").val();
                onDone = function(data){
                    runDataTables();
                };
		

		if($(this).find(".has-error").length == 0){
		    onSuccess = function(data){
			$("#btnupscheduleform").removeAttr("disabled").html(currentBtnHtml);
			$("#upschedule").modal("hide");
		    };

		    dataSend = $(this).serialize();
		    getDataFunction(onDone, onSuccess, approot, command, dataSend, "AjaxEmpWorkingSchedule", null, true, "json");
		}else{
		    $("#btnupscheduleform").removeAttr("disabled").html(currentBtnHtml);
		}

		return false;
	    });
            
            //UPLOAD
            var upload = function(elementId){
                $(elementId).click(function(){
                    $("#btnupload").html("Save").removeAttr("disabled");
                    $(".modal-dialog").css("width", "50%");
                    $("#uploaddoc").modal("show");

                    oid = $(this).data('oid');
                    var periodId = $("#periodup").val();
                    dataFor = $(this).data('for');
//                    if (dataFor == "showEmpRelevantDocForm") {
                        $("#formupload").attr("action","<%=approot%>/system/excel_up/up_sch_process_mfull_responsive.jsp?periodup="+periodId);
//                    }
                    
                    $("#tempname").val("");
                    $("#generaldatafor").val(dataFor);
                    $("#oiddata").val(oid);
                });
            };
            $("#periodup").change(function(){
                $("#btnupload").html("Save").removeAttr("disabled");
                    $(".modal-dialog").css("width", "50%");
                    $("#uploaddoc").modal("show");

                    oid = $(this).data('oid');
                    var periodId = $("#periodup").val();
                    dataFor = $(this).data('for');
//                    if (dataFor == "showEmpRelevantDocForm") {
                        $("#formupload").attr("action","<%=approot%>/system/excel_up/up_sch_process_mfull_responsive.jsp?periodup="+periodId);
//                    }
                    
                    $("#tempname").val("");
                    $("#generaldatafor").val(dataFor);
                    $("#oiddata").val(oid);
            });
            

            //UPLOAD

            function uploadTrigger(){
                $("#uploadtrigger").unbind().click(function(){
                    $("#FRM_DOC").trigger('click');
                });

                $("#FRM_DOC").unbind().change(function(){
                    var doc = $("#FRM_DOC").val();
                    if (doc.indexOf("'") > -1){
                        $('#btnupload').prop('disabled', true);
                        alert("File Name Can't Contain ' Character ")
                        $("#tempname").val('');
                    } else {
                        $('#btnupload').prop('disabled', false);
                        $("#tempname").val(doc);
                    }

                });

                $("#tempname").unbind().click(function(){
                    $("#FRM_DOC").trigger('click');
                });
            }
            upload(".btnupload");
            uploadTrigger();
            <%
            if (iCommand == Command.LIST){
            %>
             $("#upschedule").modal("show");
             <%
            }
	    %>
                    
            $("#clear").click(function(){
                $('#customfilter').empty();
                $('#clear').hide();
            });

            $("#add").click(function(){
                $('#clear').show();
                var filter = $("#filter").val();
                dataFor = "addfilter";

                dataSend = {
                    "FRM_FIELD_FILTER" : filter,
                    "FRM_FIELD_DATA_FOR" : dataFor
                };
                var onDones = function(data){
                    if(filter == "all"){
                        $("#customfilter").html(data.FRM_FIELD_HTML);
                    }else{
                        if($("#customfilter").find("#"+filter).length == 0){
                             $("#customfilter").append(data.FRM_FIELD_HTML);
                        }                           
                    }
                    $(".comboDivision").select2({
                        placeholder: "Division"
                    });
                    $(".comboCompany").select2({
                        placeholder: "Company"
                    });
                    $(".comboDepartment").select2({
                        placeholder: "Department"
                    });
                    $(".comboSection").select2({
                        placeholder: "Section"
                    });
                    $(".comboPosition").select2({
                        placeholder: "Position"
                    });
                    $(".comboLevel").select2({
                        placeholder: "Level"
                    });
                    $(".comboCategory").select2({
                        placeholder: "Emp Category"
                    });
                    $(".comboMarital").select2({
                        placeholder: "Marital Status"
                    });
                    $(".comboRace").select2({
                        placeholder: "Race"
                    });
                    $(".comboReligion").select2({
                        placeholder: "Religion"
                    });
                    $(".comboBirthday").select2({
                        placeholder: "Birthday"
                    });

//                        $('#employee_number').on('keyup', function(e) {
//                            if (e.keyCode === 13) {
//                                $('#search').click();
//                            }
//                        });
//
//                        $('#employee_name').on('keyup', function(e) {
//                            if (e.keyCode === 13) {
//                                $('#search').click();
//                            }
//                        });

                };
                var onSuccesses = function(data){};
                getDataFunction(onDones, onSuccesses, approot, command, dataSend, "AjaxEmployee", null, false, "json");
            });   
            
            <%
            if (!error.equals("")){
            %>  
               alert("Import Failed, Check Imported File")     
               $("#btnupscheduleform").attr({"disabled":"true"});
            <%
                }
            %>
                    
	})
      </script>
      <div id="addeditgeneral" class="modal fade nonprint" tabindex="-1">
	<div class="modal-dialog nonprint">
	    <div class="modal-content">
		<div class="modal-header">
		    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
		    <h4 class="addeditgeneral-title"></h4>
		</div>
                <form id="generalform" enctype="multipart/form-data">
		    <input type="hidden" name="FRM_FIELD_DATA_FOR" id="generaldatafor">
		    <input type="hidden" name="command" value="<%= Command.SAVE %>">
		    <input type="hidden" name="FRM_FIELD_OID" id="oid">
                    <input type="hidden" name="login_position_id" value="<%=emplx.getPositionId()%>">
		    <div class="modal-body ">
			<div class="row">
			    <div class="col-md-12">
				<div class="box-body addeditgeneral-body">

				</div>
			    </div>
			</div>
		    </div>
		    <div class="modal-footer">
			<button type="submit" class="btn btn-primary" id="btngeneralform"><i class="fa fa-check"></i> Save</button>
			<button type="button" data-dismiss="modal" class="btn btn-danger"><i class="fa fa-ban"></i> Close</button>
		    </div>
		</form>
	    </div>
	</div>
    </div>
      <div id="upschedule" class="modal fade nonprint" tabindex="-1">
	<div class="modal-dialog modal-lg nonprint">
	    <div class="modal-content">
		<div class="modal-header">
		    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
		    <h4 class="upschedule-title"></h4>
		</div>
                <form id="upscheduleform" enctype="multipart/form-data">
		    <input type="hidden" name="FRM_FIELD_DATA_FOR" id="generaldatafor" value="upload">
                    <input type="hidden" name="PERIOD_ID" value="<%=periodId%>">
		    <input type="hidden" name="command" value="<%= Command.SAVE %>">
                    <input type="hidden" name="POSITION_OF_USER_LOGIN" value="<%=positionOfLoginUser%>">
		    <input type="hidden" name="FRM_FIELD_OID" id="oid">
                    <input type="hidden" name="login_position_id" value="<%=emplx.getPositionId()%>">
		    <div class="modal-body upschedule-modal-body">
			<div class="row">
			    <div class="col-md-12">
				<div class="box-body upschedule-body">
<%=drawList%> 
				</div>
			    </div>
			</div>
		    </div>
		    <div class="modal-footer">
			<button type="submit" class="btn btn-primary" id="btnupscheduleform"><i class="fa fa-check"></i> Save</button>
			<button type="button" data-dismiss="modal" class="btn btn-danger"><i class="fa fa-ban"></i> Close</button>
		    </div>
		</form>
	    </div>
	</div>
    </div>                    
    <div id="uploaddoc" class="modal fade nonprint" tabindex="-1">
	<div class="modal-dialog nonprint">
	    <div class="modal-content">
		<div class="modal-header">
		    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
		    <h4 class="addeditgeneral-title-doc">Upload Schedule</h4>
		</div>
                <form method="POST" id="formupload" enctype="multipart/form-data">		    
		    <input type="hidden" name="command" value="<%= Command.POST %>">
                    <input type="hidden" name="FRM_FIELD_DATA_FOR" id="generaldatafor">
                    <input style="width:0px; height:0px;"  type="file" name="FRM_DOC" id="FRM_DOC">
		   <input type="hidden" name="FRM_FIELD_OID" id="oiddata">
		    <div class="modal-body ">
			<div class="row">
			    <div class="col-md-12">
				<div class="box-body upload-body">
                                    <div class="row">
                                        <div class="col-md-12">
                                            <div class="form-group">
                                                <a href="sample_fomat_schedule_v3_start20.xls">Download Template</a>
                                            </div>
                                            <div class="form-group">
                                                <label>Period</label>
                                                <select name="periodup" id="periodup" class="form-control">
                                                    <%
                                                        Vector listPeriodUpload = PstPeriod.list(0, 0, "", PstPeriod.fieldNames[PstPeriod.FLD_START_DATE]+ " DESC");
                                                        if (listPeriodUpload.size() > 0){
                                                            for (int i=0; i < listPeriodUpload.size(); i++){
                                                                 Period period = (Period) listPeriodUpload.get(i);                                                   
                                                     %>
                                                                <option value="<%=period.getOID()%>"><%=period.getPeriod()%></option>
                                                     <%
                                                            }

                                                        }
                                                    %>
                                                </select>
                                            </div>
                                            <div class="form-group">
                                                <label>Upload Schedule</label>
                                                <div class="input-group my-colorpicker2 colorpicker-element">
                                                    <input required id="tempname" class="form-control" type="text">
                                                    <div style="cursor: pointer" class="input-group-addon" id="uploadtrigger">
                                                        <i class="fa fa-file-pdf-o"></i>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>    
				</div>
			    </div>
			</div>
		    </div>
		    <div class="modal-footer">
			<button type="submit" class="btn btn-primary" id="btnupload"><i class="fa fa-check"></i> Save</button>
			<button type="button" data-dismiss="modal" class="btn btn-danger"><i class="fa fa-ban"></i> Close</button>
		    </div>
		</form>
	    </div>
	</div>
    </div>
   </div><!-- ./wrapper -->
    </body>
</html>
