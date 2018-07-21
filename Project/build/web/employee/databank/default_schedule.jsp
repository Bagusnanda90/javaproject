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

<%
    int iCommand = FRMQueryString.requestCommand(request);
    long employeeId = FRMQueryString.requestLong(request, "employeeId");
    String result = "";
         //update by satrya 2013-04-09
    int schedulePerWeek = 0;    
    int recordToGet=7;
    try{
        schedulePerWeek = Integer.parseInt(PstSystemProperty.getValueByName("ATTANDACE_DEFAULT_SCHEDULE_PER_WEEK"));
        if(schedulePerWeek!=0){
            recordToGet=35;
        }
    }catch(Exception ex){
        System.out.println("Execption ATTANDACE_DEFAULT_SCHEDULE_PER_WEEK: " + ex.toString());
        schedulePerWeek=0;
    }
    if(iCommand==Command.SAVE && employeeId!=0){
          PstDefaultSchedule.deleteByEmployee(employeeId);
          result = "OK";
          for(int idx=1;idx <= recordToGet; idx++){                   
              //update by satrya 2013-04-08
              //for(int idx=1;idx <= 7; idx++){                   
                long oidSch1= FRMQueryString.requestLong(request, "sche1_"+idx);
                long oidSch2= FRMQueryString.requestLong(request, "sche2_"+idx);
                if(oidSch1!=0 || oidSch2!=0){
                       DefaultSchedule df = new DefaultSchedule();
                       df.setEmployeeId(employeeId);
                       df.setDayIndex(idx);
                       df.setSchedule1(oidSch1);
                       df.setSchedule2(oidSch2);
                       try{
                       PstDefaultSchedule.insertExc(df);
                         } catch(Exception exc){
                             result = exc.toString();
                             System.out.println(exc);
                         }                       
                   }
            }
    }

         Employee employee = new Employee();
         try{
           if(employeeId!=0){
            employee = PstEmployee.fetchExc(employeeId);
           }
         } catch(Exception exc){
             System.out.println("Exception employeeId"+exc);
         }
         String whereClauseDS = PstDefaultSchedule.fieldNames[PstDefaultSchedule.FLD_EMPLOYEE_ID]+"="+employee.getOID();
         String orderDS= PstDefaultSchedule.fieldNames[PstDefaultSchedule.FLD_DAY_INDEX] ;
         Vector dftSchedules = PstDefaultSchedule.list(0, recordToGet, whereClauseDS, orderDS); 
         //Vector dftSchedules = PstDefaultSchedule.list(0, 7, whereClauseDS, orderDS);
         //update by devin 2014-02-28
         long oidScheduleOff = Long.parseLong(String.valueOf(PstSystemProperty.getPropertyLongbyName("OID_DAY_OFF")));
         
        Vector schSyhmbol = PstScheduleSymbol.listAll();
                   Vector comp_value = new Vector();                   
                   Vector comp_key = new Vector();
                   ScheduleSymbol sys = new ScheduleSymbol();
                    //update by devin 2014-02-28
                        if(oidScheduleOff!=0){
                             try{
                             sys=PstScheduleSymbol.fetchExc(oidScheduleOff);
                        }catch(Exception exc){

                        }
                   }
                  
                  if(sys !=null && sys.getSymbol() !=null && sys.getSymbol().length()>0){
                       comp_value.add(""+sys.getOID());
                       comp_key.add(sys.getSymbol());
                  }
                  
                   for(int idx=0; idx < schSyhmbol.size();idx++){
                       ScheduleSymbol schSym= (ScheduleSymbol) schSyhmbol.get(idx);                       
                       if(schSym.getSymbol().equalsIgnoreCase("Off")){
                           
                           
                       }else{
                       comp_value.add(""+schSym.getOID());
                       comp_key.add(""+ schSym.getSymbol());
                   }         
                           }
                      
                            
        %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Edit Default Schedule</title>
    </head>
    <body>
        <script language="Javascript">
            function saveSchedules(){
                document.defsch.command.value="<%=Command.SAVE%>";
                document.defsch.submit();
            }
            
            
            function setDefaultOnEmployee(){
                <% if(iCommand==Command.SAVE && employeeId!=0 && result.equals("OK") ) {
                  for(int idx=1;idx <= 35; idx++){
                      // for(int idx=1;idx <= 7; idx++){
                    DefaultSchedule dfltSch = PstDefaultSchedule.getDefaultSchedule(idx, dftSchedules);                       
                    if(dfltSch.getDayIndex()==idx){
                        %> self.opener.document.frm_employee.<%=("sche1_"+idx)%>.value="<%=PstScheduleSymbol.getScheduleSymbol(dfltSch.getSchedule1())%>";
                    <%
                    } else{
                        %>self.opener.document.frm_employee.<%=("sche1_"+idx)%>.value="-";
                    <% }                      
                 }
                  for(int idx=1;idx <= 7; idx++){
                    DefaultSchedule dfltSch = PstDefaultSchedule.getDefaultSchedule(idx, dftSchedules);                       
                    if(dfltSch.getDayIndex()==idx){
                        %> self.opener.document.frm_employee.<%=("sche2_"+idx)%>.value="<%=PstScheduleSymbol.getScheduleSymbol(dfltSch.getSchedule2())%>";
                    <%
                    } else{
                        %>self.opener.document.frm_employee.<%=("sche2_"+idx)%>.value="-";
                    <% }                      
                 }
                 %> 
                  self.close();
                <%
                 }                 
                %>
                 
            }
            
            
        </script>
        <h1>Edit Default Schedule</h1>
        Default Schedule for :  <%=employee.getEmployeeNum()%> / <%= employee.getFullName() %>
        <form name="defsch" >
        <table >
            <tr>
                <td>&nbsp;<input type="hidden" name="command" value="0"/>
                    <input type="hidden" name="employeeId" value="<%=employeeId%>"/></td>
                <td>Sun</td>
                <td>Mon</td>
                <td>Tue</td>
                <td>Wed</td>
                <td>Thu</td>
                <td>Fri</td>
                <td>Sat</td>
            </tr>            
            <tr>
                <td> 1st </td>
                <%                    
                  for(int idx=1;idx <= 7; idx++){
                    DefaultSchedule dfltSch = PstDefaultSchedule.getDefaultSchedule(idx, dftSchedules);   
                    %> <td> <%
                    if(dfltSch.getDayIndex()==idx){
                        %><%= ControlCombo.draw("sche1_"+idx,
                             "formElemen", null, "" +dfltSch.getSchedule1()  , comp_value, comp_key)%>
                             <%
                    } else{
                        %><%= ControlCombo.draw("sche1_"+idx,
                             "formElemen", null, "0" , comp_value, comp_key)%>
                    <% } %>
                     </td>
                  <%}%>
            </tr>      
            <!-- update by satrya 2013-04-08 
                di pakai di melia agar tidak input schedulenya satu"
            -->
            <%if(schedulePerWeek!=0){%>
            <tr>
                <td> 2nd </td>
                <%                    
                  for(int idx=8;idx <= 14; idx++){
                    DefaultSchedule dfltSch = PstDefaultSchedule.getDefaultSchedule(idx, dftSchedules);   
                    %> <td> <%
                    if(dfltSch.getDayIndex()==idx){
                        %><%= ControlCombo.draw("sche1_"+idx,
                             "formElemen", null, "" +dfltSch.getSchedule1()  , comp_value, comp_key)%>
                             <%
                    } else{
                        %><%= ControlCombo.draw("sche1_"+idx,
                             "formElemen", null, "0" , comp_value, comp_key)%>
                    <% } %>
                     </td>
                  <%}%>
            </tr> 
             <tr>
                <td> 3rd </td>
                <%                    
                  for(int idx=15;idx <= 21; idx++){
                    DefaultSchedule dfltSch = PstDefaultSchedule.getDefaultSchedule(idx, dftSchedules);   
                    %> <td> <%
                    if(dfltSch.getDayIndex()==idx){
                        %><%= ControlCombo.draw("sche1_"+idx,
                             "formElemen", null, "" +dfltSch.getSchedule1()  , comp_value, comp_key)%>
                             <%
                    } else{
                        %><%= ControlCombo.draw("sche1_"+idx,
                             "formElemen", null, "0" , comp_value, comp_key)%>
                    <% } %>
                     </td>
                  <%}%>
            </tr> 
            <tr>
                <td> 4th </td>
                <%                    
                  for(int idx=22;idx <= 28; idx++){
                    DefaultSchedule dfltSch = PstDefaultSchedule.getDefaultSchedule(idx, dftSchedules);   
                    %> <td> <%
                    if(dfltSch.getDayIndex()==idx){
                        %><%= ControlCombo.draw("sche1_"+idx,
                             "formElemen", null, "" +dfltSch.getSchedule1()  , comp_value, comp_key)%>
                             <%
                    } else{
                        %><%= ControlCombo.draw("sche1_"+idx,
                             "formElemen", null, "0" , comp_value, comp_key)%>
                    <% } %>
                     </td>
                  <%}%>
            </tr>
            <tr>
                <td> 5th </td>
                <%                    
                  for(int idx=29;idx <= 35; idx++){
                    DefaultSchedule dfltSch = PstDefaultSchedule.getDefaultSchedule(idx, dftSchedules);   
                    %> <td> <%
                    if(dfltSch.getDayIndex()==idx){
                        %><%= ControlCombo.draw("sche1_"+idx,
                             "formElemen", null, "" +dfltSch.getSchedule1()  , comp_value, comp_key)%>
                             <%
                    } else{
                        %><%= ControlCombo.draw("sche1_"+idx,
                             "formElemen", null, "0" , comp_value, comp_key)%>
                    <% } %>
                     </td>
                  <%}%>
            </tr>
            <%}%>
            <%if(schedulePerWeek!=0){%>
            <%if(false){%>
            <tr>
                <td>2nd2</td>
                <%                    
                for(int idx=1;idx <= 7; idx++){
                    DefaultSchedule dfltSch = PstDefaultSchedule.getDefaultSchedule(idx, dftSchedules);   
                    %> <td> <%
                    if(dfltSch.getDayIndex()==idx){
                        %><%= ControlCombo.draw("sche2_"+idx,
                             "formElemen", null, "" +dfltSch.getSchedule2()  , comp_value, comp_key)%>
                             <%
                    } else{
                        %><%= ControlCombo.draw("sche2_"+idx,
                             "formElemen", null, "0" , comp_value, comp_key)%>
                    <% } %>
                     </td>
                  <%}%>
            </tr>
            <%}%>
            <%}%>
            <tr>
                <td>
                    <a href="javascript:saveSchedules()">Save Schedule</a>
                </td>
            </tr>
            <tr>
                <td>
                    &nbsp;<%=result%> 
                    <% if(result.equals("OK")){%>
                    <script language="Javascript" >
                        setDefaultOnEmployee();
                    </script>
                    <!-- update by devin 2014-02-04 -->
                    <script>
                         self.close();
                    </script>
                    <%}
                   
                   
                   else{
                       
                    }%>
                </td>
            </tr>
        </table>
        </form>
    </body>
</html>
