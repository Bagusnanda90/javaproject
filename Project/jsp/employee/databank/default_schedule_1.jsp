<%-- 
    Document   : default_schedule
    Created on : Dec 24, 2011, 3:56:13 PM
    Author     : ktanjana
--%>
<%@ page language = "java" %>
<!-- package java -->
<%@ page import = "java.util.*" %>
<!-- package dimata -->
<%@ page import = "com.dimata.util.*" %>
<!-- package qdep -->
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>
<!--package harisma -->
<%@ page import = "com.dimata.harisma.entity.employee.*" %>
<%@ page import = "com.dimata.harisma.form.employee.*" %>
<%@ page import = "com.dimata.harisma.form.locker.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ page import = "com.dimata.harisma.entity.locker.*" %>
<%@ page import = "com.dimata.harisma.form.search.*" %>
<%@ page import = "com.dimata.harisma.session.employee.SessEmployeePicture" %>
<%@page import = "com.dimata.harisma.form.masterdata.FrmKecamatan" %>

<%@ include file = "../../main/javainit.jsp" %>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_DATABANK, AppObjInfo.OBJ_DATABANK);%>
<%@ include file = "../../main/checkuser.jsp" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Edit Default Schedule</title>
    </head>
    <body>
        <h1>Edit Default Schedule</h1>
        <%
         long oidEmployee = FRMQueryString.requestLong(request, "employee_oid");
         Employee employee = new Employee();
         try{
            employee = PstEmployee.fetchExc(oidEmployee);
         } catch(Exception exc){
             
         }
         String whereClauseDS = PstDefaultSchedule.fieldNames[PstDefaultSchedule.FLD_EMPLOYEE_ID]+"="+employee.getOID();
         String orderDS= PstDefaultSchedule.fieldNames[PstDefaultSchedule.FLD_DAY_INDEX] ;
         Vector dftSchedules = PstDefaultSchedule.list(0, 7, whereClauseDS, orderDS);
        %>
        Default Schedule for :  <%=employee.getEmployeeNum()%> / <%= employee.getFullName() %>
        <form name="defsch" >
        <table >
            <tr>
                <td>&nbsp;</td>
                <td>Mon</td>
                <td>Tue</td>
                <td>Wed</td>
                <td>Thu</td>
                <td>Fri</td>
                <td>Sat</td>
                <td>Sun</td>
            </tr>            
            <tr>
                <td> 1st </td>
                <% Vector schSyhmbol = PstScheduleSymbol.listAll();
                   Vector comp_value = new Vector();                   
                   Vector comp_key = new Vector();
                   
                   for(int idx=0; idx < schSyhmbol.size();idx++){
                       ScheduleSymbol schSym= (ScheduleSymbol) schSyhmbol.get(idx);                       
                       comp_value.add(""+schSym.getOID());
                       comp_key.add(""+ schSym.getSymbol());
                   }
                   
                  for(int idx=1;idx <= 7; idx++){
                    DefaultSchedule dfltSch = PstDefaultSchedule.getDefaultSchedule(idx, dftSchedules);   
                    switch(idx){                                            
                         case Calendar.MONDAY: %>                
                         <td><%=PstScheduleSymbol.getScheduleSymbol(dfltSch.getSchedule1()) %>
                             <%= ControlCombo.draw("sche1_"+idx,
                             "formElemen", null, "" +dfltSch.getSchedule1()  , comp_value, comp_key)%>
                         </td>
                      <%     break;
                          case Calendar.TUESDAY: %>                
                        <td><%=PstScheduleSymbol.getScheduleSymbol(dfltSch.getSchedule1()) %>
                             <%= ControlCombo.draw("sche1_"+idx,
                             "formElemen", null, "" +dfltSch.getSchedule1()  , comp_value, comp_key)%>
                         </td>
                        <%     break;
                          case Calendar.WEDNESDAY: %>                
                        <td><%=PstScheduleSymbol.getScheduleSymbol(dfltSch.getSchedule1()) %>
                             <%= ControlCombo.draw("sche1_"+idx,
                             "formElemen", null, "" +dfltSch.getSchedule1()  , comp_value, comp_key)%>
                         </td>
                        <%     break;
                          case Calendar.THURSDAY : %>                
                        <td><%=PstScheduleSymbol.getScheduleSymbol(dfltSch.getSchedule1()) %>
                             <%= ControlCombo.draw("sche1_"+idx,
                             "formElemen", null, "" +dfltSch.getSchedule1()  , comp_value, comp_key)%>
                         </td>
                        <%     break;
                          case Calendar.FRIDAY: %>                
                        <td><%=PstScheduleSymbol.getScheduleSymbol(dfltSch.getSchedule1()) %>
                             <%= ControlCombo.draw("sche1_"+idx,
                             "formElemen", null, "" +dfltSch.getSchedule1()  , comp_value, comp_key)%>
                         </td>
                        <%     break;
                          case Calendar.SATURDAY: %>                
                        <td><%=PstScheduleSymbol.getScheduleSymbol(dfltSch.getSchedule1()) %>
                             <%= ControlCombo.draw("sche1_"+idx,
                             "formElemen", null, "" +dfltSch.getSchedule1()  , comp_value, comp_key)%>
                         </td>
                        <%     break;
                          case Calendar.SUNDAY: %>                
                        <td><%=PstScheduleSymbol.getScheduleSymbol(dfltSch.getSchedule1()) %>
                             <%= ControlCombo.draw("sche1_"+idx,
                             "formElemen", null, "" +dfltSch.getSchedule1()  , comp_value, comp_key)%>
                         </td>
                        <% break;
                          default :
                                ;%>
                <%    }
                    }%>
            </tr>            
            <tr>
                <td>2nd</td>
                <%                    
                  for(int idx=1;idx <= 7; idx++){
                    DefaultSchedule dfltSch = PstDefaultSchedule.getDefaultSchedule(idx, dftSchedules);   
                    switch(idx){                                            
                         case Calendar.MONDAY: %>                
                         <td><%=PstScheduleSymbol.getScheduleSymbol(dfltSch.getSchedule1()) %>
                             <%= ControlCombo.draw("sche2_"+idx,
                             "formElemen", null, "" +dfltSch.getSchedule2()  , comp_value, comp_key)%>
                         </td>
                      <%     break;
                          case Calendar.TUESDAY: %>                
                        <td><%=PstScheduleSymbol.getScheduleSymbol(dfltSch.getSchedule1()) %>
                             <%= ControlCombo.draw("sche2_"+idx,
                             "formElemen", null, "" +dfltSch.getSchedule2()  , comp_value, comp_key)%>
                         </td>
                        <%     break;
                          case Calendar.WEDNESDAY: %>                
                        <td><%=PstScheduleSymbol.getScheduleSymbol(dfltSch.getSchedule1()) %>
                             <%= ControlCombo.draw("sche2_"+idx,
                             "formElemen", null, "" +dfltSch.getSchedule2()  , comp_value, comp_key)%>
                         </td>
                        <%     break;
                          case Calendar.THURSDAY : %>                
                        <td><%=PstScheduleSymbol.getScheduleSymbol(dfltSch.getSchedule1()) %>
                             <%= ControlCombo.draw("sche2_"+idx,
                             "formElemen", null, "" +dfltSch.getSchedule2()  , comp_value, comp_key)%>
                         </td>
                        <%     break;
                          case Calendar.FRIDAY: %>                
                        <td><%=PstScheduleSymbol.getScheduleSymbol(dfltSch.getSchedule1()) %>
                             <%= ControlCombo.draw("sche2_"+idx,
                             "formElemen", null, "" +dfltSch.getSchedule2()  , comp_value, comp_key)%>
                         </td>
                        <%     break;
                          case Calendar.SATURDAY: %>                
                        <td><%=PstScheduleSymbol.getScheduleSymbol(dfltSch.getSchedule1()) %>
                             <%= ControlCombo.draw("sche2_"+idx,
                             "formElemen", null, "" +dfltSch.getSchedule2()  , comp_value, comp_key)%>
                         </td>
                        <%     break;
                          case Calendar.SUNDAY: %>                
                        <td><%=PstScheduleSymbol.getScheduleSymbol(dfltSch.getSchedule1()) %>
                             <%= ControlCombo.draw("sche2_"+idx,
                             "formElemen", null, "" +dfltSch.getSchedule2()  , comp_value, comp_key)%>
                         </td>
                        <% break;
                          default :
                                ;%>
                <%    }
                    }%>
            </tr>                        
        </table>
        </form>
    </body>
</html>
