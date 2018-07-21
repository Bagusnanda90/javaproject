<%-- 
    Document   : index2
    Created on : Jun 30, 2012, 11:08:09 AM
    Author     : ktanjana
--%>

<%@page import="com.dimata.harisma.entity.attendance.I_Atendance"%>
<%@ include file = "/main/javainit.jsp" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    I_Atendance attdConfig = null;
    try {
        attdConfig = (I_Atendance) (Class.forName(PstSystemProperty.getValueByName("ATTENDANCE_CONFIG")).newInstance());
    } catch (Exception e) {
        System.out.println("Exception : " + e.getMessage());
        System.out.println("Please contact your system administration to setup system property: ATTENDANCE_CONFIG ");
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <%@ include file="../template/css.jsp" %>
    </head>
<script type="text/javascript" language="javascript" src="ajax.js">
    
</script>
</head>    
    <body>
        <table id="GridView1"  style="width:100%;border-collapse:collapse;" width="100%" cellspacing="0"><tbody><tr class="GridviewScrollHeader"><td rowspan="2">No</td><td rowspan="2">Date</td><td rowspan="2">Payrol<br>Numb</td><td rowspan="2">Employee</td><td colspan="4" align="center">plan</td><td colspan="4" align="center">Actual</td><td colspan="2" align="center">Difference</td><td rowspan="2">Duration</td><td colspan="1" align="center">Schedule</td><td rowspan="2">Leave</td><td rowspan="2">Insentif</td><td rowspan="2">OT.<br>Frm</td><td rowspan="2">Allwn</td><td rowspan="2">Paid<br>/Dp</td><td rowspan="2">Net<br>OT</td><td rowspan="2">OT.<br>Idx</td><td rowspan="2">Status</td><td rowspan="2">Reason</td><td rowspan="2">Note</td><td rowspan="2">Select to  <br> Update <br><a href="Javascript:SetAllCheckBoxes('frpresence','userSelect', true)">All</a> | <a href="Javascript:SetAllCheckBoxes('frpresence','userSelect', false)">Deselect All</a> </td></tr><tr class="GridviewScrollHeader"><td>Time<br>In</td><td>Break<br>Out</td><td>Break<br>In</td><td>Time<br>Out</td><td>Time<br>In</td><td>Break<br>Out</td><td>Break<br>In</td><td>Time<br>Out</td><td>In</td><td>Out</td><td>Symbol</td></tr><tr class="GridviewScrollItem"><td class="Freeze">1</td><td class="Freeze"><font color="red">Sun</font>,<br><font color="red">1/10/2017</font>&nbsp;</td><td class="Freeze">19750922001</td><td class="Freeze">Dewa Gede Darmika</td><td class="Freeze">09:00</td><td class="Freeze">13:00</td><td class="Freeze">14:00</td><td class="Freeze">18:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665865736250_d_1_d_1506787200000_d_2017092800_d_19750922001_symbol" value="P9" size="2" title="09:00-18:00&nbsp;[13:00&nbsp;14:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092800','1506787200000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092800','1506787200000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092800','1506787200000')">[New]</a></center></td><td><center></center></td><td><select name="504404665865736250_d_1_d_1506787200000_d_2017092800_d_19750922001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665865736250_d_1_d_1506787200000_d_2017092800_d_19750922001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665865736250_d_1_d_1506787200000_d_2017092800_d_19750922001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665865736250_d_1_d_1506787200000_d_2017092800_d_19750922001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665865736250_d_1_d_1506787200000_d_2017092800_d_19750922001_oVerIdx" value="-" type="text"></td><td><select name="504404665865736250_d_1_d_1506787200000_d_2017092800_d_19750922001_status" onkeydown="javascript:fnTrapKD()" id="504404665865736250_d_1_d_1506787200000_d_2017092800_d_19750922001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665865736250_d_1_d_1506787200000_d_2017092800_d_19750922001_reason" onkeydown="javascript:fnTrapKD()" id="504404665865736250_d_1_d_1506787200000_d_2017092800_d_19750922001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665865736250_d_1_d_1506787200000_d_2017092800_d_19750922001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665865736250_d_1_d_1506787200000_d_2017092800_d_19750922001" value="504404665865736250_d_1_d_1506787200000_d_2017092800_d_19750922001" type="checkbox"><input size="4" name="504404665865736250_d_1_d_1506787200000_d_2017092800_d_19750922001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">2</td><td class="Freeze"><font color="red">Sun</font>,<br><font color="red">1/10/2017</font>&nbsp;</td><td class="Freeze">19820202001</td><td class="Freeze">Arif Eko Cahyono</td><td class="Freeze">09:00</td><td class="Freeze">13:00</td><td class="Freeze">14:00</td><td class="Freeze">18:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665865736287_d_1_d_1506787200000_d_2017092802_d_19820202001_symbol" value="P9" size="2" title="09:00-18:00&nbsp;[13:00&nbsp;14:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092802','1506787200000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092802','1506787200000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092802','1506787200000')">[New]</a></center></td><td><center></center></td><td><select name="504404665865736287_d_1_d_1506787200000_d_2017092802_d_19820202001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665865736287_d_1_d_1506787200000_d_2017092802_d_19820202001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665865736287_d_1_d_1506787200000_d_2017092802_d_19820202001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665865736287_d_1_d_1506787200000_d_2017092802_d_19820202001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665865736287_d_1_d_1506787200000_d_2017092802_d_19820202001_oVerIdx" value="-" type="text"></td><td><select name="504404665865736287_d_1_d_1506787200000_d_2017092802_d_19820202001_status" onkeydown="javascript:fnTrapKD()" id="504404665865736287_d_1_d_1506787200000_d_2017092802_d_19820202001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665865736287_d_1_d_1506787200000_d_2017092802_d_19820202001_reason" onkeydown="javascript:fnTrapKD()" id="504404665865736287_d_1_d_1506787200000_d_2017092802_d_19820202001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665865736287_d_1_d_1506787200000_d_2017092802_d_19820202001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665865736287_d_1_d_1506787200000_d_2017092802_d_19820202001" value="504404665865736287_d_1_d_1506787200000_d_2017092802_d_19820202001" type="checkbox"><input size="4" name="504404665865736287_d_1_d_1506787200000_d_2017092802_d_19820202001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">3</td><td class="Freeze"><font color="red">Sun</font>,<br><font color="red">1/10/2017</font>&nbsp;</td><td class="Freeze">19870603001</td><td class="Freeze">Moses Sangga Mila</td><td class="Freeze">08:00</td><td class="Freeze">12:00</td><td class="Freeze">13:00</td><td class="Freeze">17:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665865736276_d_1_d_1506787200000_d_2017092801_d_19870603001_symbol" value="P8" size="2" title="08:00-17:00&nbsp;[12:00&nbsp;13:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092801','1506787200000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092801','1506787200000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092801','1506787200000')">[New]</a></center></td><td><center></center></td><td><select name="504404665865736276_d_1_d_1506787200000_d_2017092801_d_19870603001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665865736276_d_1_d_1506787200000_d_2017092801_d_19870603001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665865736276_d_1_d_1506787200000_d_2017092801_d_19870603001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665865736276_d_1_d_1506787200000_d_2017092801_d_19870603001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665865736276_d_1_d_1506787200000_d_2017092801_d_19870603001_oVerIdx" value="-" type="text"></td><td><select name="504404665865736276_d_1_d_1506787200000_d_2017092801_d_19870603001_status" onkeydown="javascript:fnTrapKD()" id="504404665865736276_d_1_d_1506787200000_d_2017092801_d_19870603001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665865736276_d_1_d_1506787200000_d_2017092801_d_19870603001_reason" onkeydown="javascript:fnTrapKD()" id="504404665865736276_d_1_d_1506787200000_d_2017092801_d_19870603001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665865736276_d_1_d_1506787200000_d_2017092801_d_19870603001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665865736276_d_1_d_1506787200000_d_2017092801_d_19870603001" value="504404665865736276_d_1_d_1506787200000_d_2017092801_d_19870603001" type="checkbox"><input size="4" name="504404665865736276_d_1_d_1506787200000_d_2017092801_d_19870603001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">4</td><td class="Freeze"><font color="red">Sun</font>,<br><font color="red">1/10/2017</font>&nbsp;</td><td class="Freeze">19890519001</td><td class="Freeze">Khoirul Huda</td><td class="Freeze">08:00</td><td class="Freeze">12:00</td><td class="Freeze">13:00</td><td class="Freeze">17:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665795260590_d_1_d_1506787200000_d_2017092799_d_19890519001_symbol" value="P8" size="2" title="08:00-17:00&nbsp;[12:00&nbsp;13:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092799','1506787200000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092799','1506787200000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092799','1506787200000')">[New]</a></center></td><td><center></center></td><td><select name="504404665795260590_d_1_d_1506787200000_d_2017092799_d_19890519001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665795260590_d_1_d_1506787200000_d_2017092799_d_19890519001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665795260590_d_1_d_1506787200000_d_2017092799_d_19890519001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665795260590_d_1_d_1506787200000_d_2017092799_d_19890519001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665795260590_d_1_d_1506787200000_d_2017092799_d_19890519001_oVerIdx" value="-" type="text"></td><td><select name="504404665795260590_d_1_d_1506787200000_d_2017092799_d_19890519001_status" onkeydown="javascript:fnTrapKD()" id="504404665795260590_d_1_d_1506787200000_d_2017092799_d_19890519001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665795260590_d_1_d_1506787200000_d_2017092799_d_19890519001_reason" onkeydown="javascript:fnTrapKD()" id="504404665795260590_d_1_d_1506787200000_d_2017092799_d_19890519001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665795260590_d_1_d_1506787200000_d_2017092799_d_19890519001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665795260590_d_1_d_1506787200000_d_2017092799_d_19890519001" value="504404665795260590_d_1_d_1506787200000_d_2017092799_d_19890519001" type="checkbox"><input size="4" name="504404665795260590_d_1_d_1506787200000_d_2017092799_d_19890519001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">5</td><td class="Freeze"><font color="red">Sun</font>,<br><font color="red">1/10/2017</font>&nbsp;</td><td class="Freeze">19930325001</td><td class="Freeze">Dita Ayuningtyas Fitriani</td><td class="Freeze">08:00</td><td class="Freeze">12:00</td><td class="Freeze">13:00</td><td class="Freeze">17:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665865735931_d_1_d_1506787200000_d_2017092798_d_19930325001_symbol" value="P8" size="2" title="08:00-17:00&nbsp;[12:00&nbsp;13:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092798','1506787200000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092798','1506787200000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092798','1506787200000')">[New]</a></center></td><td><center></center></td><td><select name="504404665865735931_d_1_d_1506787200000_d_2017092798_d_19930325001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665865735931_d_1_d_1506787200000_d_2017092798_d_19930325001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665865735931_d_1_d_1506787200000_d_2017092798_d_19930325001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665865735931_d_1_d_1506787200000_d_2017092798_d_19930325001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665865735931_d_1_d_1506787200000_d_2017092798_d_19930325001_oVerIdx" value="-" type="text"></td><td><select name="504404665865735931_d_1_d_1506787200000_d_2017092798_d_19930325001_status" onkeydown="javascript:fnTrapKD()" id="504404665865735931_d_1_d_1506787200000_d_2017092798_d_19930325001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665865735931_d_1_d_1506787200000_d_2017092798_d_19930325001_reason" onkeydown="javascript:fnTrapKD()" id="504404665865735931_d_1_d_1506787200000_d_2017092798_d_19930325001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665865735931_d_1_d_1506787200000_d_2017092798_d_19930325001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665865735931_d_1_d_1506787200000_d_2017092798_d_19930325001" value="504404665865735931_d_1_d_1506787200000_d_2017092798_d_19930325001" type="checkbox"><input size="4" name="504404665865735931_d_1_d_1506787200000_d_2017092798_d_19930325001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">6</td><td class="Freeze">Mon,<br>2/10/2017&nbsp;</td><td class="Freeze">19750922001</td><td class="Freeze">Dewa Gede Darmika</td><td class="Freeze">09:00</td><td class="Freeze">13:00</td><td class="Freeze">14:00</td><td class="Freeze">18:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665865736250_d_2_d_1506873600000_d_2017092800_d_19750922001_symbol" value="P9" size="2" title="09:00-18:00&nbsp;[13:00&nbsp;14:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092800','1506873600000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092800','1506873600000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092800','1506873600000')">[New]</a></center></td><td><center></center></td><td><select name="504404665865736250_d_2_d_1506873600000_d_2017092800_d_19750922001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665865736250_d_2_d_1506873600000_d_2017092800_d_19750922001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665865736250_d_2_d_1506873600000_d_2017092800_d_19750922001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665865736250_d_2_d_1506873600000_d_2017092800_d_19750922001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665865736250_d_2_d_1506873600000_d_2017092800_d_19750922001_oVerIdx" value="-" type="text"></td><td><select name="504404665865736250_d_2_d_1506873600000_d_2017092800_d_19750922001_status" onkeydown="javascript:fnTrapKD()" id="504404665865736250_d_2_d_1506873600000_d_2017092800_d_19750922001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665865736250_d_2_d_1506873600000_d_2017092800_d_19750922001_reason" onkeydown="javascript:fnTrapKD()" id="504404665865736250_d_2_d_1506873600000_d_2017092800_d_19750922001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665865736250_d_2_d_1506873600000_d_2017092800_d_19750922001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665865736250_d_2_d_1506873600000_d_2017092800_d_19750922001" value="504404665865736250_d_2_d_1506873600000_d_2017092800_d_19750922001" type="checkbox"><input size="4" name="504404665865736250_d_2_d_1506873600000_d_2017092800_d_19750922001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">7</td><td class="Freeze">Mon,<br>2/10/2017&nbsp;</td><td class="Freeze">19820202001</td><td class="Freeze">Arif Eko Cahyono</td><td class="Freeze">09:00</td><td class="Freeze">13:00</td><td class="Freeze">14:00</td><td class="Freeze">18:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665865736287_d_2_d_1506873600000_d_2017092802_d_19820202001_symbol" value="P9" size="2" title="09:00-18:00&nbsp;[13:00&nbsp;14:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092802','1506873600000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092802','1506873600000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092802','1506873600000')">[New]</a></center></td><td><center></center></td><td><select name="504404665865736287_d_2_d_1506873600000_d_2017092802_d_19820202001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665865736287_d_2_d_1506873600000_d_2017092802_d_19820202001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665865736287_d_2_d_1506873600000_d_2017092802_d_19820202001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665865736287_d_2_d_1506873600000_d_2017092802_d_19820202001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665865736287_d_2_d_1506873600000_d_2017092802_d_19820202001_oVerIdx" value="-" type="text"></td><td><select name="504404665865736287_d_2_d_1506873600000_d_2017092802_d_19820202001_status" onkeydown="javascript:fnTrapKD()" id="504404665865736287_d_2_d_1506873600000_d_2017092802_d_19820202001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665865736287_d_2_d_1506873600000_d_2017092802_d_19820202001_reason" onkeydown="javascript:fnTrapKD()" id="504404665865736287_d_2_d_1506873600000_d_2017092802_d_19820202001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665865736287_d_2_d_1506873600000_d_2017092802_d_19820202001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665865736287_d_2_d_1506873600000_d_2017092802_d_19820202001" value="504404665865736287_d_2_d_1506873600000_d_2017092802_d_19820202001" type="checkbox"><input size="4" name="504404665865736287_d_2_d_1506873600000_d_2017092802_d_19820202001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">8</td><td class="Freeze">Mon,<br>2/10/2017&nbsp;</td><td class="Freeze">19870603001</td><td class="Freeze">Moses Sangga Mila</td><td class="Freeze">08:00</td><td class="Freeze">12:00</td><td class="Freeze">13:00</td><td class="Freeze">17:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665865736276_d_2_d_1506873600000_d_2017092801_d_19870603001_symbol" value="P8" size="2" title="08:00-17:00&nbsp;[12:00&nbsp;13:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092801','1506873600000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092801','1506873600000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092801','1506873600000')">[New]</a></center></td><td><center></center></td><td><select name="504404665865736276_d_2_d_1506873600000_d_2017092801_d_19870603001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665865736276_d_2_d_1506873600000_d_2017092801_d_19870603001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665865736276_d_2_d_1506873600000_d_2017092801_d_19870603001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665865736276_d_2_d_1506873600000_d_2017092801_d_19870603001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665865736276_d_2_d_1506873600000_d_2017092801_d_19870603001_oVerIdx" value="-" type="text"></td><td><select name="504404665865736276_d_2_d_1506873600000_d_2017092801_d_19870603001_status" onkeydown="javascript:fnTrapKD()" id="504404665865736276_d_2_d_1506873600000_d_2017092801_d_19870603001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665865736276_d_2_d_1506873600000_d_2017092801_d_19870603001_reason" onkeydown="javascript:fnTrapKD()" id="504404665865736276_d_2_d_1506873600000_d_2017092801_d_19870603001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665865736276_d_2_d_1506873600000_d_2017092801_d_19870603001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665865736276_d_2_d_1506873600000_d_2017092801_d_19870603001" value="504404665865736276_d_2_d_1506873600000_d_2017092801_d_19870603001" type="checkbox"><input size="4" name="504404665865736276_d_2_d_1506873600000_d_2017092801_d_19870603001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">9</td><td class="Freeze">Mon,<br>2/10/2017&nbsp;</td><td class="Freeze">19890519001</td><td class="Freeze">Khoirul Huda</td><td class="Freeze">08:00</td><td class="Freeze">12:00</td><td class="Freeze">13:00</td><td class="Freeze">17:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665795260590_d_2_d_1506873600000_d_2017092799_d_19890519001_symbol" value="P8" size="2" title="08:00-17:00&nbsp;[12:00&nbsp;13:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092799','1506873600000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092799','1506873600000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092799','1506873600000')">[New]</a></center></td><td><center></center></td><td><select name="504404665795260590_d_2_d_1506873600000_d_2017092799_d_19890519001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665795260590_d_2_d_1506873600000_d_2017092799_d_19890519001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665795260590_d_2_d_1506873600000_d_2017092799_d_19890519001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665795260590_d_2_d_1506873600000_d_2017092799_d_19890519001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665795260590_d_2_d_1506873600000_d_2017092799_d_19890519001_oVerIdx" value="-" type="text"></td><td><select name="504404665795260590_d_2_d_1506873600000_d_2017092799_d_19890519001_status" onkeydown="javascript:fnTrapKD()" id="504404665795260590_d_2_d_1506873600000_d_2017092799_d_19890519001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665795260590_d_2_d_1506873600000_d_2017092799_d_19890519001_reason" onkeydown="javascript:fnTrapKD()" id="504404665795260590_d_2_d_1506873600000_d_2017092799_d_19890519001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665795260590_d_2_d_1506873600000_d_2017092799_d_19890519001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665795260590_d_2_d_1506873600000_d_2017092799_d_19890519001" value="504404665795260590_d_2_d_1506873600000_d_2017092799_d_19890519001" type="checkbox"><input size="4" name="504404665795260590_d_2_d_1506873600000_d_2017092799_d_19890519001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">10</td><td class="Freeze">Mon,<br>2/10/2017&nbsp;</td><td class="Freeze">19930325001</td><td class="Freeze">Dita Ayuningtyas Fitriani</td><td class="Freeze">08:00</td><td class="Freeze">12:00</td><td class="Freeze">13:00</td><td class="Freeze">17:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665865735931_d_2_d_1506873600000_d_2017092798_d_19930325001_symbol" value="P8" size="2" title="08:00-17:00&nbsp;[12:00&nbsp;13:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092798','1506873600000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092798','1506873600000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092798','1506873600000')">[New]</a></center></td><td><center></center></td><td><select name="504404665865735931_d_2_d_1506873600000_d_2017092798_d_19930325001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665865735931_d_2_d_1506873600000_d_2017092798_d_19930325001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665865735931_d_2_d_1506873600000_d_2017092798_d_19930325001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665865735931_d_2_d_1506873600000_d_2017092798_d_19930325001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665865735931_d_2_d_1506873600000_d_2017092798_d_19930325001_oVerIdx" value="-" type="text"></td><td><select name="504404665865735931_d_2_d_1506873600000_d_2017092798_d_19930325001_status" onkeydown="javascript:fnTrapKD()" id="504404665865735931_d_2_d_1506873600000_d_2017092798_d_19930325001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665865735931_d_2_d_1506873600000_d_2017092798_d_19930325001_reason" onkeydown="javascript:fnTrapKD()" id="504404665865735931_d_2_d_1506873600000_d_2017092798_d_19930325001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665865735931_d_2_d_1506873600000_d_2017092798_d_19930325001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665865735931_d_2_d_1506873600000_d_2017092798_d_19930325001" value="504404665865735931_d_2_d_1506873600000_d_2017092798_d_19930325001" type="checkbox"><input size="4" name="504404665865735931_d_2_d_1506873600000_d_2017092798_d_19930325001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">11</td><td class="Freeze">Tue,<br>3/10/2017&nbsp;</td><td class="Freeze">19750922001</td><td class="Freeze">Dewa Gede Darmika</td><td class="Freeze">09:00</td><td class="Freeze">13:00</td><td class="Freeze">14:00</td><td class="Freeze">18:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665865736250_d_3_d_1506960000000_d_2017092800_d_19750922001_symbol" value="P9" size="2" title="09:00-18:00&nbsp;[13:00&nbsp;14:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092800','1506960000000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092800','1506960000000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092800','1506960000000')">[New]</a></center></td><td><center></center></td><td><select name="504404665865736250_d_3_d_1506960000000_d_2017092800_d_19750922001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665865736250_d_3_d_1506960000000_d_2017092800_d_19750922001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665865736250_d_3_d_1506960000000_d_2017092800_d_19750922001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665865736250_d_3_d_1506960000000_d_2017092800_d_19750922001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665865736250_d_3_d_1506960000000_d_2017092800_d_19750922001_oVerIdx" value="-" type="text"></td><td><select name="504404665865736250_d_3_d_1506960000000_d_2017092800_d_19750922001_status" onkeydown="javascript:fnTrapKD()" id="504404665865736250_d_3_d_1506960000000_d_2017092800_d_19750922001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665865736250_d_3_d_1506960000000_d_2017092800_d_19750922001_reason" onkeydown="javascript:fnTrapKD()" id="504404665865736250_d_3_d_1506960000000_d_2017092800_d_19750922001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665865736250_d_3_d_1506960000000_d_2017092800_d_19750922001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665865736250_d_3_d_1506960000000_d_2017092800_d_19750922001" value="504404665865736250_d_3_d_1506960000000_d_2017092800_d_19750922001" type="checkbox"><input size="4" name="504404665865736250_d_3_d_1506960000000_d_2017092800_d_19750922001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">12</td><td class="Freeze">Tue,<br>3/10/2017&nbsp;</td><td class="Freeze">19820202001</td><td class="Freeze">Arif Eko Cahyono</td><td class="Freeze">09:00</td><td class="Freeze">13:00</td><td class="Freeze">14:00</td><td class="Freeze">18:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665865736287_d_3_d_1506960000000_d_2017092802_d_19820202001_symbol" value="P9" size="2" title="09:00-18:00&nbsp;[13:00&nbsp;14:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092802','1506960000000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092802','1506960000000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092802','1506960000000')">[New]</a></center></td><td><center></center></td><td><select name="504404665865736287_d_3_d_1506960000000_d_2017092802_d_19820202001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665865736287_d_3_d_1506960000000_d_2017092802_d_19820202001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665865736287_d_3_d_1506960000000_d_2017092802_d_19820202001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665865736287_d_3_d_1506960000000_d_2017092802_d_19820202001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665865736287_d_3_d_1506960000000_d_2017092802_d_19820202001_oVerIdx" value="-" type="text"></td><td><select name="504404665865736287_d_3_d_1506960000000_d_2017092802_d_19820202001_status" onkeydown="javascript:fnTrapKD()" id="504404665865736287_d_3_d_1506960000000_d_2017092802_d_19820202001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665865736287_d_3_d_1506960000000_d_2017092802_d_19820202001_reason" onkeydown="javascript:fnTrapKD()" id="504404665865736287_d_3_d_1506960000000_d_2017092802_d_19820202001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665865736287_d_3_d_1506960000000_d_2017092802_d_19820202001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665865736287_d_3_d_1506960000000_d_2017092802_d_19820202001" value="504404665865736287_d_3_d_1506960000000_d_2017092802_d_19820202001" type="checkbox"><input size="4" name="504404665865736287_d_3_d_1506960000000_d_2017092802_d_19820202001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">13</td><td class="Freeze">Tue,<br>3/10/2017&nbsp;</td><td class="Freeze">19870603001</td><td class="Freeze">Moses Sangga Mila</td><td class="Freeze">08:00</td><td class="Freeze">12:00</td><td class="Freeze">13:00</td><td class="Freeze">17:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665865736276_d_3_d_1506960000000_d_2017092801_d_19870603001_symbol" value="P8" size="2" title="08:00-17:00&nbsp;[12:00&nbsp;13:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092801','1506960000000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092801','1506960000000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092801','1506960000000')">[New]</a></center></td><td><center></center></td><td><select name="504404665865736276_d_3_d_1506960000000_d_2017092801_d_19870603001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665865736276_d_3_d_1506960000000_d_2017092801_d_19870603001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665865736276_d_3_d_1506960000000_d_2017092801_d_19870603001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665865736276_d_3_d_1506960000000_d_2017092801_d_19870603001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665865736276_d_3_d_1506960000000_d_2017092801_d_19870603001_oVerIdx" value="-" type="text"></td><td><select name="504404665865736276_d_3_d_1506960000000_d_2017092801_d_19870603001_status" onkeydown="javascript:fnTrapKD()" id="504404665865736276_d_3_d_1506960000000_d_2017092801_d_19870603001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665865736276_d_3_d_1506960000000_d_2017092801_d_19870603001_reason" onkeydown="javascript:fnTrapKD()" id="504404665865736276_d_3_d_1506960000000_d_2017092801_d_19870603001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665865736276_d_3_d_1506960000000_d_2017092801_d_19870603001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665865736276_d_3_d_1506960000000_d_2017092801_d_19870603001" value="504404665865736276_d_3_d_1506960000000_d_2017092801_d_19870603001" type="checkbox"><input size="4" name="504404665865736276_d_3_d_1506960000000_d_2017092801_d_19870603001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">14</td><td class="Freeze">Tue,<br>3/10/2017&nbsp;</td><td class="Freeze">19890519001</td><td class="Freeze">Khoirul Huda</td><td class="Freeze">08:00</td><td class="Freeze">12:00</td><td class="Freeze">13:00</td><td class="Freeze">17:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665795260590_d_3_d_1506960000000_d_2017092799_d_19890519001_symbol" value="P8" size="2" title="08:00-17:00&nbsp;[12:00&nbsp;13:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092799','1506960000000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092799','1506960000000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092799','1506960000000')">[New]</a></center></td><td><center></center></td><td><select name="504404665795260590_d_3_d_1506960000000_d_2017092799_d_19890519001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665795260590_d_3_d_1506960000000_d_2017092799_d_19890519001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665795260590_d_3_d_1506960000000_d_2017092799_d_19890519001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665795260590_d_3_d_1506960000000_d_2017092799_d_19890519001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665795260590_d_3_d_1506960000000_d_2017092799_d_19890519001_oVerIdx" value="-" type="text"></td><td><select name="504404665795260590_d_3_d_1506960000000_d_2017092799_d_19890519001_status" onkeydown="javascript:fnTrapKD()" id="504404665795260590_d_3_d_1506960000000_d_2017092799_d_19890519001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665795260590_d_3_d_1506960000000_d_2017092799_d_19890519001_reason" onkeydown="javascript:fnTrapKD()" id="504404665795260590_d_3_d_1506960000000_d_2017092799_d_19890519001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665795260590_d_3_d_1506960000000_d_2017092799_d_19890519001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665795260590_d_3_d_1506960000000_d_2017092799_d_19890519001" value="504404665795260590_d_3_d_1506960000000_d_2017092799_d_19890519001" type="checkbox"><input size="4" name="504404665795260590_d_3_d_1506960000000_d_2017092799_d_19890519001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">15</td><td class="Freeze">Tue,<br>3/10/2017&nbsp;</td><td class="Freeze">19930325001</td><td class="Freeze">Dita Ayuningtyas Fitriani</td><td class="Freeze">08:00</td><td class="Freeze">12:00</td><td class="Freeze">13:00</td><td class="Freeze">17:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665865735931_d_3_d_1506960000000_d_2017092798_d_19930325001_symbol" value="P8" size="2" title="08:00-17:00&nbsp;[12:00&nbsp;13:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092798','1506960000000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092798','1506960000000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092798','1506960000000')">[New]</a></center></td><td><center></center></td><td><select name="504404665865735931_d_3_d_1506960000000_d_2017092798_d_19930325001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665865735931_d_3_d_1506960000000_d_2017092798_d_19930325001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665865735931_d_3_d_1506960000000_d_2017092798_d_19930325001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665865735931_d_3_d_1506960000000_d_2017092798_d_19930325001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665865735931_d_3_d_1506960000000_d_2017092798_d_19930325001_oVerIdx" value="-" type="text"></td><td><select name="504404665865735931_d_3_d_1506960000000_d_2017092798_d_19930325001_status" onkeydown="javascript:fnTrapKD()" id="504404665865735931_d_3_d_1506960000000_d_2017092798_d_19930325001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665865735931_d_3_d_1506960000000_d_2017092798_d_19930325001_reason" onkeydown="javascript:fnTrapKD()" id="504404665865735931_d_3_d_1506960000000_d_2017092798_d_19930325001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665865735931_d_3_d_1506960000000_d_2017092798_d_19930325001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665865735931_d_3_d_1506960000000_d_2017092798_d_19930325001" value="504404665865735931_d_3_d_1506960000000_d_2017092798_d_19930325001" type="checkbox"><input size="4" name="504404665865735931_d_3_d_1506960000000_d_2017092798_d_19930325001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">16</td><td class="Freeze">Wed,<br>4/10/2017&nbsp;</td><td class="Freeze">19750922001</td><td class="Freeze">Dewa Gede Darmika</td><td class="Freeze">09:00</td><td class="Freeze">13:00</td><td class="Freeze">14:00</td><td class="Freeze">18:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665865736250_d_4_d_1507046400000_d_2017092800_d_19750922001_symbol" value="P9" size="2" title="09:00-18:00&nbsp;[13:00&nbsp;14:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092800','1507046400000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092800','1507046400000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092800','1507046400000')">[New]</a></center></td><td><center></center></td><td><select name="504404665865736250_d_4_d_1507046400000_d_2017092800_d_19750922001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665865736250_d_4_d_1507046400000_d_2017092800_d_19750922001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665865736250_d_4_d_1507046400000_d_2017092800_d_19750922001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665865736250_d_4_d_1507046400000_d_2017092800_d_19750922001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665865736250_d_4_d_1507046400000_d_2017092800_d_19750922001_oVerIdx" value="-" type="text"></td><td><select name="504404665865736250_d_4_d_1507046400000_d_2017092800_d_19750922001_status" onkeydown="javascript:fnTrapKD()" id="504404665865736250_d_4_d_1507046400000_d_2017092800_d_19750922001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665865736250_d_4_d_1507046400000_d_2017092800_d_19750922001_reason" onkeydown="javascript:fnTrapKD()" id="504404665865736250_d_4_d_1507046400000_d_2017092800_d_19750922001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665865736250_d_4_d_1507046400000_d_2017092800_d_19750922001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665865736250_d_4_d_1507046400000_d_2017092800_d_19750922001" value="504404665865736250_d_4_d_1507046400000_d_2017092800_d_19750922001" type="checkbox"><input size="4" name="504404665865736250_d_4_d_1507046400000_d_2017092800_d_19750922001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">17</td><td class="Freeze">Wed,<br>4/10/2017&nbsp;</td><td class="Freeze">19820202001</td><td class="Freeze">Arif Eko Cahyono</td><td class="Freeze">09:00</td><td class="Freeze">13:00</td><td class="Freeze">14:00</td><td class="Freeze">18:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665865736287_d_4_d_1507046400000_d_2017092802_d_19820202001_symbol" value="P9" size="2" title="09:00-18:00&nbsp;[13:00&nbsp;14:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092802','1507046400000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092802','1507046400000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092802','1507046400000')">[New]</a></center></td><td><center></center></td><td><select name="504404665865736287_d_4_d_1507046400000_d_2017092802_d_19820202001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665865736287_d_4_d_1507046400000_d_2017092802_d_19820202001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665865736287_d_4_d_1507046400000_d_2017092802_d_19820202001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665865736287_d_4_d_1507046400000_d_2017092802_d_19820202001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665865736287_d_4_d_1507046400000_d_2017092802_d_19820202001_oVerIdx" value="-" type="text"></td><td><select name="504404665865736287_d_4_d_1507046400000_d_2017092802_d_19820202001_status" onkeydown="javascript:fnTrapKD()" id="504404665865736287_d_4_d_1507046400000_d_2017092802_d_19820202001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665865736287_d_4_d_1507046400000_d_2017092802_d_19820202001_reason" onkeydown="javascript:fnTrapKD()" id="504404665865736287_d_4_d_1507046400000_d_2017092802_d_19820202001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665865736287_d_4_d_1507046400000_d_2017092802_d_19820202001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665865736287_d_4_d_1507046400000_d_2017092802_d_19820202001" value="504404665865736287_d_4_d_1507046400000_d_2017092802_d_19820202001" type="checkbox"><input size="4" name="504404665865736287_d_4_d_1507046400000_d_2017092802_d_19820202001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">18</td><td class="Freeze">Wed,<br>4/10/2017&nbsp;</td><td class="Freeze">19870603001</td><td class="Freeze">Moses Sangga Mila</td><td class="Freeze">08:00</td><td class="Freeze">12:00</td><td class="Freeze">13:00</td><td class="Freeze">17:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665865736276_d_4_d_1507046400000_d_2017092801_d_19870603001_symbol" value="P8" size="2" title="08:00-17:00&nbsp;[12:00&nbsp;13:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092801','1507046400000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092801','1507046400000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092801','1507046400000')">[New]</a></center></td><td><center></center></td><td><select name="504404665865736276_d_4_d_1507046400000_d_2017092801_d_19870603001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665865736276_d_4_d_1507046400000_d_2017092801_d_19870603001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665865736276_d_4_d_1507046400000_d_2017092801_d_19870603001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665865736276_d_4_d_1507046400000_d_2017092801_d_19870603001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665865736276_d_4_d_1507046400000_d_2017092801_d_19870603001_oVerIdx" value="-" type="text"></td><td><select name="504404665865736276_d_4_d_1507046400000_d_2017092801_d_19870603001_status" onkeydown="javascript:fnTrapKD()" id="504404665865736276_d_4_d_1507046400000_d_2017092801_d_19870603001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665865736276_d_4_d_1507046400000_d_2017092801_d_19870603001_reason" onkeydown="javascript:fnTrapKD()" id="504404665865736276_d_4_d_1507046400000_d_2017092801_d_19870603001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665865736276_d_4_d_1507046400000_d_2017092801_d_19870603001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665865736276_d_4_d_1507046400000_d_2017092801_d_19870603001" value="504404665865736276_d_4_d_1507046400000_d_2017092801_d_19870603001" type="checkbox"><input size="4" name="504404665865736276_d_4_d_1507046400000_d_2017092801_d_19870603001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">19</td><td class="Freeze">Wed,<br>4/10/2017&nbsp;</td><td class="Freeze">19890519001</td><td class="Freeze">Khoirul Huda</td><td class="Freeze">08:00</td><td class="Freeze">12:00</td><td class="Freeze">13:00</td><td class="Freeze">17:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665795260590_d_4_d_1507046400000_d_2017092799_d_19890519001_symbol" value="P8" size="2" title="08:00-17:00&nbsp;[12:00&nbsp;13:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092799','1507046400000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092799','1507046400000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092799','1507046400000')">[New]</a></center></td><td><center></center></td><td><select name="504404665795260590_d_4_d_1507046400000_d_2017092799_d_19890519001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665795260590_d_4_d_1507046400000_d_2017092799_d_19890519001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665795260590_d_4_d_1507046400000_d_2017092799_d_19890519001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665795260590_d_4_d_1507046400000_d_2017092799_d_19890519001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665795260590_d_4_d_1507046400000_d_2017092799_d_19890519001_oVerIdx" value="-" type="text"></td><td><select name="504404665795260590_d_4_d_1507046400000_d_2017092799_d_19890519001_status" onkeydown="javascript:fnTrapKD()" id="504404665795260590_d_4_d_1507046400000_d_2017092799_d_19890519001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665795260590_d_4_d_1507046400000_d_2017092799_d_19890519001_reason" onkeydown="javascript:fnTrapKD()" id="504404665795260590_d_4_d_1507046400000_d_2017092799_d_19890519001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665795260590_d_4_d_1507046400000_d_2017092799_d_19890519001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665795260590_d_4_d_1507046400000_d_2017092799_d_19890519001" value="504404665795260590_d_4_d_1507046400000_d_2017092799_d_19890519001" type="checkbox"><input size="4" name="504404665795260590_d_4_d_1507046400000_d_2017092799_d_19890519001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">20</td><td class="Freeze">Wed,<br>4/10/2017&nbsp;</td><td class="Freeze">19930325001</td><td class="Freeze">Dita Ayuningtyas Fitriani</td><td class="Freeze">08:00</td><td class="Freeze">12:00</td><td class="Freeze">13:00</td><td class="Freeze">17:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665865735931_d_4_d_1507046400000_d_2017092798_d_19930325001_symbol" value="P8" size="2" title="08:00-17:00&nbsp;[12:00&nbsp;13:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092798','1507046400000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092798','1507046400000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092798','1507046400000')">[New]</a></center></td><td><center></center></td><td><select name="504404665865735931_d_4_d_1507046400000_d_2017092798_d_19930325001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665865735931_d_4_d_1507046400000_d_2017092798_d_19930325001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665865735931_d_4_d_1507046400000_d_2017092798_d_19930325001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665865735931_d_4_d_1507046400000_d_2017092798_d_19930325001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665865735931_d_4_d_1507046400000_d_2017092798_d_19930325001_oVerIdx" value="-" type="text"></td><td><select name="504404665865735931_d_4_d_1507046400000_d_2017092798_d_19930325001_status" onkeydown="javascript:fnTrapKD()" id="504404665865735931_d_4_d_1507046400000_d_2017092798_d_19930325001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665865735931_d_4_d_1507046400000_d_2017092798_d_19930325001_reason" onkeydown="javascript:fnTrapKD()" id="504404665865735931_d_4_d_1507046400000_d_2017092798_d_19930325001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665865735931_d_4_d_1507046400000_d_2017092798_d_19930325001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665865735931_d_4_d_1507046400000_d_2017092798_d_19930325001" value="504404665865735931_d_4_d_1507046400000_d_2017092798_d_19930325001" type="checkbox"><input size="4" name="504404665865735931_d_4_d_1507046400000_d_2017092798_d_19930325001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">21</td><td class="Freeze">Thu,<br>5/10/2017&nbsp;</td><td class="Freeze">19750922001</td><td class="Freeze">Dewa Gede Darmika</td><td class="Freeze">09:00</td><td class="Freeze">13:00</td><td class="Freeze">14:00</td><td class="Freeze">18:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665865736250_d_5_d_1507132800000_d_2017092800_d_19750922001_symbol" value="P9" size="2" title="09:00-18:00&nbsp;[13:00&nbsp;14:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092800','1507132800000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092800','1507132800000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092800','1507132800000')">[New]</a></center></td><td><center></center></td><td><select name="504404665865736250_d_5_d_1507132800000_d_2017092800_d_19750922001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665865736250_d_5_d_1507132800000_d_2017092800_d_19750922001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665865736250_d_5_d_1507132800000_d_2017092800_d_19750922001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665865736250_d_5_d_1507132800000_d_2017092800_d_19750922001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665865736250_d_5_d_1507132800000_d_2017092800_d_19750922001_oVerIdx" value="-" type="text"></td><td><select name="504404665865736250_d_5_d_1507132800000_d_2017092800_d_19750922001_status" onkeydown="javascript:fnTrapKD()" id="504404665865736250_d_5_d_1507132800000_d_2017092800_d_19750922001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665865736250_d_5_d_1507132800000_d_2017092800_d_19750922001_reason" onkeydown="javascript:fnTrapKD()" id="504404665865736250_d_5_d_1507132800000_d_2017092800_d_19750922001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665865736250_d_5_d_1507132800000_d_2017092800_d_19750922001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665865736250_d_5_d_1507132800000_d_2017092800_d_19750922001" value="504404665865736250_d_5_d_1507132800000_d_2017092800_d_19750922001" type="checkbox"><input size="4" name="504404665865736250_d_5_d_1507132800000_d_2017092800_d_19750922001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">22</td><td class="Freeze">Thu,<br>5/10/2017&nbsp;</td><td class="Freeze">19820202001</td><td class="Freeze">Arif Eko Cahyono</td><td class="Freeze">09:00</td><td class="Freeze">13:00</td><td class="Freeze">14:00</td><td class="Freeze">18:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665865736287_d_5_d_1507132800000_d_2017092802_d_19820202001_symbol" value="P9" size="2" title="09:00-18:00&nbsp;[13:00&nbsp;14:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092802','1507132800000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092802','1507132800000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092802','1507132800000')">[New]</a></center></td><td><center></center></td><td><select name="504404665865736287_d_5_d_1507132800000_d_2017092802_d_19820202001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665865736287_d_5_d_1507132800000_d_2017092802_d_19820202001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665865736287_d_5_d_1507132800000_d_2017092802_d_19820202001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665865736287_d_5_d_1507132800000_d_2017092802_d_19820202001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665865736287_d_5_d_1507132800000_d_2017092802_d_19820202001_oVerIdx" value="-" type="text"></td><td><select name="504404665865736287_d_5_d_1507132800000_d_2017092802_d_19820202001_status" onkeydown="javascript:fnTrapKD()" id="504404665865736287_d_5_d_1507132800000_d_2017092802_d_19820202001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665865736287_d_5_d_1507132800000_d_2017092802_d_19820202001_reason" onkeydown="javascript:fnTrapKD()" id="504404665865736287_d_5_d_1507132800000_d_2017092802_d_19820202001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665865736287_d_5_d_1507132800000_d_2017092802_d_19820202001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665865736287_d_5_d_1507132800000_d_2017092802_d_19820202001" value="504404665865736287_d_5_d_1507132800000_d_2017092802_d_19820202001" type="checkbox"><input size="4" name="504404665865736287_d_5_d_1507132800000_d_2017092802_d_19820202001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">23</td><td class="Freeze">Thu,<br>5/10/2017&nbsp;</td><td class="Freeze">19870603001</td><td class="Freeze">Moses Sangga Mila</td><td class="Freeze">08:00</td><td class="Freeze">12:00</td><td class="Freeze">13:00</td><td class="Freeze">17:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665865736276_d_5_d_1507132800000_d_2017092801_d_19870603001_symbol" value="P8" size="2" title="08:00-17:00&nbsp;[12:00&nbsp;13:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092801','1507132800000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092801','1507132800000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092801','1507132800000')">[New]</a></center></td><td><center></center></td><td><select name="504404665865736276_d_5_d_1507132800000_d_2017092801_d_19870603001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665865736276_d_5_d_1507132800000_d_2017092801_d_19870603001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665865736276_d_5_d_1507132800000_d_2017092801_d_19870603001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665865736276_d_5_d_1507132800000_d_2017092801_d_19870603001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665865736276_d_5_d_1507132800000_d_2017092801_d_19870603001_oVerIdx" value="-" type="text"></td><td><select name="504404665865736276_d_5_d_1507132800000_d_2017092801_d_19870603001_status" onkeydown="javascript:fnTrapKD()" id="504404665865736276_d_5_d_1507132800000_d_2017092801_d_19870603001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665865736276_d_5_d_1507132800000_d_2017092801_d_19870603001_reason" onkeydown="javascript:fnTrapKD()" id="504404665865736276_d_5_d_1507132800000_d_2017092801_d_19870603001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665865736276_d_5_d_1507132800000_d_2017092801_d_19870603001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665865736276_d_5_d_1507132800000_d_2017092801_d_19870603001" value="504404665865736276_d_5_d_1507132800000_d_2017092801_d_19870603001" type="checkbox"><input size="4" name="504404665865736276_d_5_d_1507132800000_d_2017092801_d_19870603001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">24</td><td class="Freeze">Thu,<br>5/10/2017&nbsp;</td><td class="Freeze">19890519001</td><td class="Freeze">Khoirul Huda</td><td class="Freeze">08:00</td><td class="Freeze">12:00</td><td class="Freeze">13:00</td><td class="Freeze">17:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665795260590_d_5_d_1507132800000_d_2017092799_d_19890519001_symbol" value="P8" size="2" title="08:00-17:00&nbsp;[12:00&nbsp;13:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092799','1507132800000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092799','1507132800000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092799','1507132800000')">[New]</a></center></td><td><center></center></td><td><select name="504404665795260590_d_5_d_1507132800000_d_2017092799_d_19890519001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665795260590_d_5_d_1507132800000_d_2017092799_d_19890519001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665795260590_d_5_d_1507132800000_d_2017092799_d_19890519001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665795260590_d_5_d_1507132800000_d_2017092799_d_19890519001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665795260590_d_5_d_1507132800000_d_2017092799_d_19890519001_oVerIdx" value="-" type="text"></td><td><select name="504404665795260590_d_5_d_1507132800000_d_2017092799_d_19890519001_status" onkeydown="javascript:fnTrapKD()" id="504404665795260590_d_5_d_1507132800000_d_2017092799_d_19890519001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665795260590_d_5_d_1507132800000_d_2017092799_d_19890519001_reason" onkeydown="javascript:fnTrapKD()" id="504404665795260590_d_5_d_1507132800000_d_2017092799_d_19890519001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665795260590_d_5_d_1507132800000_d_2017092799_d_19890519001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665795260590_d_5_d_1507132800000_d_2017092799_d_19890519001" value="504404665795260590_d_5_d_1507132800000_d_2017092799_d_19890519001" type="checkbox"><input size="4" name="504404665795260590_d_5_d_1507132800000_d_2017092799_d_19890519001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">25</td><td class="Freeze">Thu,<br>5/10/2017&nbsp;</td><td class="Freeze">19930325001</td><td class="Freeze">Dita Ayuningtyas Fitriani</td><td class="Freeze">08:00</td><td class="Freeze">12:00</td><td class="Freeze">13:00</td><td class="Freeze">17:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665865735931_d_5_d_1507132800000_d_2017092798_d_19930325001_symbol" value="P8" size="2" title="08:00-17:00&nbsp;[12:00&nbsp;13:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092798','1507132800000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092798','1507132800000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092798','1507132800000')">[New]</a></center></td><td><center></center></td><td><select name="504404665865735931_d_5_d_1507132800000_d_2017092798_d_19930325001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665865735931_d_5_d_1507132800000_d_2017092798_d_19930325001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665865735931_d_5_d_1507132800000_d_2017092798_d_19930325001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665865735931_d_5_d_1507132800000_d_2017092798_d_19930325001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665865735931_d_5_d_1507132800000_d_2017092798_d_19930325001_oVerIdx" value="-" type="text"></td><td><select name="504404665865735931_d_5_d_1507132800000_d_2017092798_d_19930325001_status" onkeydown="javascript:fnTrapKD()" id="504404665865735931_d_5_d_1507132800000_d_2017092798_d_19930325001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665865735931_d_5_d_1507132800000_d_2017092798_d_19930325001_reason" onkeydown="javascript:fnTrapKD()" id="504404665865735931_d_5_d_1507132800000_d_2017092798_d_19930325001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665865735931_d_5_d_1507132800000_d_2017092798_d_19930325001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665865735931_d_5_d_1507132800000_d_2017092798_d_19930325001" value="504404665865735931_d_5_d_1507132800000_d_2017092798_d_19930325001" type="checkbox"><input size="4" name="504404665865735931_d_5_d_1507132800000_d_2017092798_d_19930325001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">26</td><td class="Freeze">Fri,<br>6/10/2017&nbsp;</td><td class="Freeze">19750922001</td><td class="Freeze">Dewa Gede Darmika</td><td class="Freeze">09:00</td><td class="Freeze">13:00</td><td class="Freeze">14:00</td><td class="Freeze">18:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665865736250_d_6_d_1507219200000_d_2017092800_d_19750922001_symbol" value="P9" size="2" title="09:00-18:00&nbsp;[13:00&nbsp;14:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092800','1507219200000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092800','1507219200000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092800','1507219200000')">[New]</a></center></td><td><center></center></td><td><select name="504404665865736250_d_6_d_1507219200000_d_2017092800_d_19750922001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665865736250_d_6_d_1507219200000_d_2017092800_d_19750922001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665865736250_d_6_d_1507219200000_d_2017092800_d_19750922001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665865736250_d_6_d_1507219200000_d_2017092800_d_19750922001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665865736250_d_6_d_1507219200000_d_2017092800_d_19750922001_oVerIdx" value="-" type="text"></td><td><select name="504404665865736250_d_6_d_1507219200000_d_2017092800_d_19750922001_status" onkeydown="javascript:fnTrapKD()" id="504404665865736250_d_6_d_1507219200000_d_2017092800_d_19750922001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665865736250_d_6_d_1507219200000_d_2017092800_d_19750922001_reason" onkeydown="javascript:fnTrapKD()" id="504404665865736250_d_6_d_1507219200000_d_2017092800_d_19750922001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665865736250_d_6_d_1507219200000_d_2017092800_d_19750922001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665865736250_d_6_d_1507219200000_d_2017092800_d_19750922001" value="504404665865736250_d_6_d_1507219200000_d_2017092800_d_19750922001" type="checkbox"><input size="4" name="504404665865736250_d_6_d_1507219200000_d_2017092800_d_19750922001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">27</td><td class="Freeze">Fri,<br>6/10/2017&nbsp;</td><td class="Freeze">19820202001</td><td class="Freeze">Arif Eko Cahyono</td><td class="Freeze">09:00</td><td class="Freeze">13:00</td><td class="Freeze">14:00</td><td class="Freeze">18:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665865736287_d_6_d_1507219200000_d_2017092802_d_19820202001_symbol" value="P9" size="2" title="09:00-18:00&nbsp;[13:00&nbsp;14:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092802','1507219200000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092802','1507219200000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092802','1507219200000')">[New]</a></center></td><td><center></center></td><td><select name="504404665865736287_d_6_d_1507219200000_d_2017092802_d_19820202001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665865736287_d_6_d_1507219200000_d_2017092802_d_19820202001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665865736287_d_6_d_1507219200000_d_2017092802_d_19820202001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665865736287_d_6_d_1507219200000_d_2017092802_d_19820202001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665865736287_d_6_d_1507219200000_d_2017092802_d_19820202001_oVerIdx" value="-" type="text"></td><td><select name="504404665865736287_d_6_d_1507219200000_d_2017092802_d_19820202001_status" onkeydown="javascript:fnTrapKD()" id="504404665865736287_d_6_d_1507219200000_d_2017092802_d_19820202001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665865736287_d_6_d_1507219200000_d_2017092802_d_19820202001_reason" onkeydown="javascript:fnTrapKD()" id="504404665865736287_d_6_d_1507219200000_d_2017092802_d_19820202001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665865736287_d_6_d_1507219200000_d_2017092802_d_19820202001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665865736287_d_6_d_1507219200000_d_2017092802_d_19820202001" value="504404665865736287_d_6_d_1507219200000_d_2017092802_d_19820202001" type="checkbox"><input size="4" name="504404665865736287_d_6_d_1507219200000_d_2017092802_d_19820202001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">28</td><td class="Freeze">Fri,<br>6/10/2017&nbsp;</td><td class="Freeze">19870603001</td><td class="Freeze">Moses Sangga Mila</td><td class="Freeze">08:00</td><td class="Freeze">12:00</td><td class="Freeze">13:00</td><td class="Freeze">17:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665865736276_d_6_d_1507219200000_d_2017092801_d_19870603001_symbol" value="P8" size="2" title="08:00-17:00&nbsp;[12:00&nbsp;13:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092801','1507219200000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092801','1507219200000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092801','1507219200000')">[New]</a></center></td><td><center></center></td><td><select name="504404665865736276_d_6_d_1507219200000_d_2017092801_d_19870603001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665865736276_d_6_d_1507219200000_d_2017092801_d_19870603001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665865736276_d_6_d_1507219200000_d_2017092801_d_19870603001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665865736276_d_6_d_1507219200000_d_2017092801_d_19870603001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665865736276_d_6_d_1507219200000_d_2017092801_d_19870603001_oVerIdx" value="-" type="text"></td><td><select name="504404665865736276_d_6_d_1507219200000_d_2017092801_d_19870603001_status" onkeydown="javascript:fnTrapKD()" id="504404665865736276_d_6_d_1507219200000_d_2017092801_d_19870603001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665865736276_d_6_d_1507219200000_d_2017092801_d_19870603001_reason" onkeydown="javascript:fnTrapKD()" id="504404665865736276_d_6_d_1507219200000_d_2017092801_d_19870603001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665865736276_d_6_d_1507219200000_d_2017092801_d_19870603001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665865736276_d_6_d_1507219200000_d_2017092801_d_19870603001" value="504404665865736276_d_6_d_1507219200000_d_2017092801_d_19870603001" type="checkbox"><input size="4" name="504404665865736276_d_6_d_1507219200000_d_2017092801_d_19870603001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">29</td><td class="Freeze">Fri,<br>6/10/2017&nbsp;</td><td class="Freeze">19890519001</td><td class="Freeze">Khoirul Huda</td><td class="Freeze">08:00</td><td class="Freeze">12:00</td><td class="Freeze">13:00</td><td class="Freeze">17:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665795260590_d_6_d_1507219200000_d_2017092799_d_19890519001_symbol" value="P8" size="2" title="08:00-17:00&nbsp;[12:00&nbsp;13:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092799','1507219200000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092799','1507219200000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092799','1507219200000')">[New]</a></center></td><td><center></center></td><td><select name="504404665795260590_d_6_d_1507219200000_d_2017092799_d_19890519001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665795260590_d_6_d_1507219200000_d_2017092799_d_19890519001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665795260590_d_6_d_1507219200000_d_2017092799_d_19890519001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665795260590_d_6_d_1507219200000_d_2017092799_d_19890519001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665795260590_d_6_d_1507219200000_d_2017092799_d_19890519001_oVerIdx" value="-" type="text"></td><td><select name="504404665795260590_d_6_d_1507219200000_d_2017092799_d_19890519001_status" onkeydown="javascript:fnTrapKD()" id="504404665795260590_d_6_d_1507219200000_d_2017092799_d_19890519001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665795260590_d_6_d_1507219200000_d_2017092799_d_19890519001_reason" onkeydown="javascript:fnTrapKD()" id="504404665795260590_d_6_d_1507219200000_d_2017092799_d_19890519001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665795260590_d_6_d_1507219200000_d_2017092799_d_19890519001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665795260590_d_6_d_1507219200000_d_2017092799_d_19890519001" value="504404665795260590_d_6_d_1507219200000_d_2017092799_d_19890519001" type="checkbox"><input size="4" name="504404665795260590_d_6_d_1507219200000_d_2017092799_d_19890519001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">30</td><td class="Freeze">Fri,<br>6/10/2017&nbsp;</td><td class="Freeze">19930325001</td><td class="Freeze">Dita Ayuningtyas Fitriani</td><td class="Freeze">08:00</td><td class="Freeze">12:00</td><td class="Freeze">13:00</td><td class="Freeze">17:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665865735931_d_6_d_1507219200000_d_2017092798_d_19930325001_symbol" value="P8" size="2" title="08:00-17:00&nbsp;[12:00&nbsp;13:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092798','1507219200000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092798','1507219200000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092798','1507219200000')">[New]</a></center></td><td><center></center></td><td><select name="504404665865735931_d_6_d_1507219200000_d_2017092798_d_19930325001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665865735931_d_6_d_1507219200000_d_2017092798_d_19930325001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665865735931_d_6_d_1507219200000_d_2017092798_d_19930325001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665865735931_d_6_d_1507219200000_d_2017092798_d_19930325001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665865735931_d_6_d_1507219200000_d_2017092798_d_19930325001_oVerIdx" value="-" type="text"></td><td><select name="504404665865735931_d_6_d_1507219200000_d_2017092798_d_19930325001_status" onkeydown="javascript:fnTrapKD()" id="504404665865735931_d_6_d_1507219200000_d_2017092798_d_19930325001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665865735931_d_6_d_1507219200000_d_2017092798_d_19930325001_reason" onkeydown="javascript:fnTrapKD()" id="504404665865735931_d_6_d_1507219200000_d_2017092798_d_19930325001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665865735931_d_6_d_1507219200000_d_2017092798_d_19930325001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665865735931_d_6_d_1507219200000_d_2017092798_d_19930325001" value="504404665865735931_d_6_d_1507219200000_d_2017092798_d_19930325001" type="checkbox"><input size="4" name="504404665865735931_d_6_d_1507219200000_d_2017092798_d_19930325001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">31</td><td class="Freeze"><font color="darkblue">Sat</font>,<br><font color="darkblue">7/10/2017</font>&nbsp;</td><td class="Freeze">19750922001</td><td class="Freeze">Dewa Gede Darmika</td><td class="Freeze">09:00</td><td class="Freeze">13:00</td><td class="Freeze">14:00</td><td class="Freeze">18:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665865736250_d_7_d_1507305600000_d_2017092800_d_19750922001_symbol" value="P9" size="2" title="09:00-18:00&nbsp;[13:00&nbsp;14:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092800','1507305600000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092800','1507305600000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092800','1507305600000')">[New]</a></center></td><td><center></center></td><td><select name="504404665865736250_d_7_d_1507305600000_d_2017092800_d_19750922001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665865736250_d_7_d_1507305600000_d_2017092800_d_19750922001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665865736250_d_7_d_1507305600000_d_2017092800_d_19750922001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665865736250_d_7_d_1507305600000_d_2017092800_d_19750922001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665865736250_d_7_d_1507305600000_d_2017092800_d_19750922001_oVerIdx" value="-" type="text"></td><td><select name="504404665865736250_d_7_d_1507305600000_d_2017092800_d_19750922001_status" onkeydown="javascript:fnTrapKD()" id="504404665865736250_d_7_d_1507305600000_d_2017092800_d_19750922001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665865736250_d_7_d_1507305600000_d_2017092800_d_19750922001_reason" onkeydown="javascript:fnTrapKD()" id="504404665865736250_d_7_d_1507305600000_d_2017092800_d_19750922001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665865736250_d_7_d_1507305600000_d_2017092800_d_19750922001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665865736250_d_7_d_1507305600000_d_2017092800_d_19750922001" value="504404665865736250_d_7_d_1507305600000_d_2017092800_d_19750922001" type="checkbox"><input size="4" name="504404665865736250_d_7_d_1507305600000_d_2017092800_d_19750922001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">32</td><td class="Freeze"><font color="darkblue">Sat</font>,<br><font color="darkblue">7/10/2017</font>&nbsp;</td><td class="Freeze">19820202001</td><td class="Freeze">Arif Eko Cahyono</td><td class="Freeze">09:00</td><td class="Freeze">13:00</td><td class="Freeze">14:00</td><td class="Freeze">18:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665865736287_d_7_d_1507305600000_d_2017092802_d_19820202001_symbol" value="P9" size="2" title="09:00-18:00&nbsp;[13:00&nbsp;14:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092802','1507305600000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092802','1507305600000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092802','1507305600000')">[New]</a></center></td><td><center></center></td><td><select name="504404665865736287_d_7_d_1507305600000_d_2017092802_d_19820202001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665865736287_d_7_d_1507305600000_d_2017092802_d_19820202001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665865736287_d_7_d_1507305600000_d_2017092802_d_19820202001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665865736287_d_7_d_1507305600000_d_2017092802_d_19820202001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665865736287_d_7_d_1507305600000_d_2017092802_d_19820202001_oVerIdx" value="-" type="text"></td><td><select name="504404665865736287_d_7_d_1507305600000_d_2017092802_d_19820202001_status" onkeydown="javascript:fnTrapKD()" id="504404665865736287_d_7_d_1507305600000_d_2017092802_d_19820202001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665865736287_d_7_d_1507305600000_d_2017092802_d_19820202001_reason" onkeydown="javascript:fnTrapKD()" id="504404665865736287_d_7_d_1507305600000_d_2017092802_d_19820202001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665865736287_d_7_d_1507305600000_d_2017092802_d_19820202001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665865736287_d_7_d_1507305600000_d_2017092802_d_19820202001" value="504404665865736287_d_7_d_1507305600000_d_2017092802_d_19820202001" type="checkbox"><input size="4" name="504404665865736287_d_7_d_1507305600000_d_2017092802_d_19820202001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">33</td><td class="Freeze"><font color="darkblue">Sat</font>,<br><font color="darkblue">7/10/2017</font>&nbsp;</td><td class="Freeze">19870603001</td><td class="Freeze">Moses Sangga Mila</td><td class="Freeze">08:00</td><td class="Freeze">12:00</td><td class="Freeze">13:00</td><td class="Freeze">17:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665865736276_d_7_d_1507305600000_d_2017092801_d_19870603001_symbol" value="P8" size="2" title="08:00-17:00&nbsp;[12:00&nbsp;13:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092801','1507305600000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092801','1507305600000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092801','1507305600000')">[New]</a></center></td><td><center></center></td><td><select name="504404665865736276_d_7_d_1507305600000_d_2017092801_d_19870603001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665865736276_d_7_d_1507305600000_d_2017092801_d_19870603001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665865736276_d_7_d_1507305600000_d_2017092801_d_19870603001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665865736276_d_7_d_1507305600000_d_2017092801_d_19870603001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665865736276_d_7_d_1507305600000_d_2017092801_d_19870603001_oVerIdx" value="-" type="text"></td><td><select name="504404665865736276_d_7_d_1507305600000_d_2017092801_d_19870603001_status" onkeydown="javascript:fnTrapKD()" id="504404665865736276_d_7_d_1507305600000_d_2017092801_d_19870603001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665865736276_d_7_d_1507305600000_d_2017092801_d_19870603001_reason" onkeydown="javascript:fnTrapKD()" id="504404665865736276_d_7_d_1507305600000_d_2017092801_d_19870603001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665865736276_d_7_d_1507305600000_d_2017092801_d_19870603001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665865736276_d_7_d_1507305600000_d_2017092801_d_19870603001" value="504404665865736276_d_7_d_1507305600000_d_2017092801_d_19870603001" type="checkbox"><input size="4" name="504404665865736276_d_7_d_1507305600000_d_2017092801_d_19870603001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">34</td><td class="Freeze"><font color="darkblue">Sat</font>,<br><font color="darkblue">7/10/2017</font>&nbsp;</td><td class="Freeze">19890519001</td><td class="Freeze">Khoirul Huda</td><td class="Freeze">08:00</td><td class="Freeze">12:00</td><td class="Freeze">13:00</td><td class="Freeze">17:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665795260590_d_7_d_1507305600000_d_2017092799_d_19890519001_symbol" value="P8" size="2" title="08:00-17:00&nbsp;[12:00&nbsp;13:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092799','1507305600000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092799','1507305600000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092799','1507305600000')">[New]</a></center></td><td><center></center></td><td><select name="504404665795260590_d_7_d_1507305600000_d_2017092799_d_19890519001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665795260590_d_7_d_1507305600000_d_2017092799_d_19890519001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665795260590_d_7_d_1507305600000_d_2017092799_d_19890519001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665795260590_d_7_d_1507305600000_d_2017092799_d_19890519001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665795260590_d_7_d_1507305600000_d_2017092799_d_19890519001_oVerIdx" value="-" type="text"></td><td><select name="504404665795260590_d_7_d_1507305600000_d_2017092799_d_19890519001_status" onkeydown="javascript:fnTrapKD()" id="504404665795260590_d_7_d_1507305600000_d_2017092799_d_19890519001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665795260590_d_7_d_1507305600000_d_2017092799_d_19890519001_reason" onkeydown="javascript:fnTrapKD()" id="504404665795260590_d_7_d_1507305600000_d_2017092799_d_19890519001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665795260590_d_7_d_1507305600000_d_2017092799_d_19890519001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665795260590_d_7_d_1507305600000_d_2017092799_d_19890519001" value="504404665795260590_d_7_d_1507305600000_d_2017092799_d_19890519001" type="checkbox"><input size="4" name="504404665795260590_d_7_d_1507305600000_d_2017092799_d_19890519001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">35</td><td class="Freeze"><font color="darkblue">Sat</font>,<br><font color="darkblue">7/10/2017</font>&nbsp;</td><td class="Freeze">19930325001</td><td class="Freeze">Dita Ayuningtyas Fitriani</td><td class="Freeze">08:00</td><td class="Freeze">12:00</td><td class="Freeze">13:00</td><td class="Freeze">17:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665865735931_d_7_d_1507305600000_d_2017092798_d_19930325001_symbol" value="P8" size="2" title="08:00-17:00&nbsp;[12:00&nbsp;13:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092798','1507305600000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092798','1507305600000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092798','1507305600000')">[New]</a></center></td><td><center></center></td><td><select name="504404665865735931_d_7_d_1507305600000_d_2017092798_d_19930325001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665865735931_d_7_d_1507305600000_d_2017092798_d_19930325001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665865735931_d_7_d_1507305600000_d_2017092798_d_19930325001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665865735931_d_7_d_1507305600000_d_2017092798_d_19930325001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665865735931_d_7_d_1507305600000_d_2017092798_d_19930325001_oVerIdx" value="-" type="text"></td><td><select name="504404665865735931_d_7_d_1507305600000_d_2017092798_d_19930325001_status" onkeydown="javascript:fnTrapKD()" id="504404665865735931_d_7_d_1507305600000_d_2017092798_d_19930325001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665865735931_d_7_d_1507305600000_d_2017092798_d_19930325001_reason" onkeydown="javascript:fnTrapKD()" id="504404665865735931_d_7_d_1507305600000_d_2017092798_d_19930325001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665865735931_d_7_d_1507305600000_d_2017092798_d_19930325001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665865735931_d_7_d_1507305600000_d_2017092798_d_19930325001" value="504404665865735931_d_7_d_1507305600000_d_2017092798_d_19930325001" type="checkbox"><input size="4" name="504404665865735931_d_7_d_1507305600000_d_2017092798_d_19930325001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">36</td><td class="Freeze"><font color="red">Sun</font>,<br><font color="red">8/10/2017</font>&nbsp;</td><td class="Freeze">19750922001</td><td class="Freeze">Dewa Gede Darmika</td><td class="Freeze">09:00</td><td class="Freeze">13:00</td><td class="Freeze">14:00</td><td class="Freeze">18:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665865736250_d_8_d_1507392000000_d_2017092800_d_19750922001_symbol" value="P9" size="2" title="09:00-18:00&nbsp;[13:00&nbsp;14:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092800','1507392000000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092800','1507392000000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092800','1507392000000')">[New]</a></center></td><td><center></center></td><td><select name="504404665865736250_d_8_d_1507392000000_d_2017092800_d_19750922001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665865736250_d_8_d_1507392000000_d_2017092800_d_19750922001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665865736250_d_8_d_1507392000000_d_2017092800_d_19750922001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665865736250_d_8_d_1507392000000_d_2017092800_d_19750922001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665865736250_d_8_d_1507392000000_d_2017092800_d_19750922001_oVerIdx" value="-" type="text"></td><td><select name="504404665865736250_d_8_d_1507392000000_d_2017092800_d_19750922001_status" onkeydown="javascript:fnTrapKD()" id="504404665865736250_d_8_d_1507392000000_d_2017092800_d_19750922001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665865736250_d_8_d_1507392000000_d_2017092800_d_19750922001_reason" onkeydown="javascript:fnTrapKD()" id="504404665865736250_d_8_d_1507392000000_d_2017092800_d_19750922001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665865736250_d_8_d_1507392000000_d_2017092800_d_19750922001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665865736250_d_8_d_1507392000000_d_2017092800_d_19750922001" value="504404665865736250_d_8_d_1507392000000_d_2017092800_d_19750922001" type="checkbox"><input size="4" name="504404665865736250_d_8_d_1507392000000_d_2017092800_d_19750922001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">37</td><td class="Freeze"><font color="red">Sun</font>,<br><font color="red">8/10/2017</font>&nbsp;</td><td class="Freeze">19820202001</td><td class="Freeze">Arif Eko Cahyono</td><td class="Freeze">09:00</td><td class="Freeze">13:00</td><td class="Freeze">14:00</td><td class="Freeze">18:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665865736287_d_8_d_1507392000000_d_2017092802_d_19820202001_symbol" value="P9" size="2" title="09:00-18:00&nbsp;[13:00&nbsp;14:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092802','1507392000000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092802','1507392000000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092802','1507392000000')">[New]</a></center></td><td><center></center></td><td><select name="504404665865736287_d_8_d_1507392000000_d_2017092802_d_19820202001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665865736287_d_8_d_1507392000000_d_2017092802_d_19820202001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665865736287_d_8_d_1507392000000_d_2017092802_d_19820202001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665865736287_d_8_d_1507392000000_d_2017092802_d_19820202001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665865736287_d_8_d_1507392000000_d_2017092802_d_19820202001_oVerIdx" value="-" type="text"></td><td><select name="504404665865736287_d_8_d_1507392000000_d_2017092802_d_19820202001_status" onkeydown="javascript:fnTrapKD()" id="504404665865736287_d_8_d_1507392000000_d_2017092802_d_19820202001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665865736287_d_8_d_1507392000000_d_2017092802_d_19820202001_reason" onkeydown="javascript:fnTrapKD()" id="504404665865736287_d_8_d_1507392000000_d_2017092802_d_19820202001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665865736287_d_8_d_1507392000000_d_2017092802_d_19820202001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665865736287_d_8_d_1507392000000_d_2017092802_d_19820202001" value="504404665865736287_d_8_d_1507392000000_d_2017092802_d_19820202001" type="checkbox"><input size="4" name="504404665865736287_d_8_d_1507392000000_d_2017092802_d_19820202001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">38</td><td class="Freeze"><font color="red">Sun</font>,<br><font color="red">8/10/2017</font>&nbsp;</td><td class="Freeze">19870603001</td><td class="Freeze">Moses Sangga Mila</td><td class="Freeze">08:00</td><td class="Freeze">12:00</td><td class="Freeze">13:00</td><td class="Freeze">17:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665865736276_d_8_d_1507392000000_d_2017092801_d_19870603001_symbol" value="P8" size="2" title="08:00-17:00&nbsp;[12:00&nbsp;13:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092801','1507392000000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092801','1507392000000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092801','1507392000000')">[New]</a></center></td><td><center></center></td><td><select name="504404665865736276_d_8_d_1507392000000_d_2017092801_d_19870603001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665865736276_d_8_d_1507392000000_d_2017092801_d_19870603001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665865736276_d_8_d_1507392000000_d_2017092801_d_19870603001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665865736276_d_8_d_1507392000000_d_2017092801_d_19870603001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665865736276_d_8_d_1507392000000_d_2017092801_d_19870603001_oVerIdx" value="-" type="text"></td><td><select name="504404665865736276_d_8_d_1507392000000_d_2017092801_d_19870603001_status" onkeydown="javascript:fnTrapKD()" id="504404665865736276_d_8_d_1507392000000_d_2017092801_d_19870603001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665865736276_d_8_d_1507392000000_d_2017092801_d_19870603001_reason" onkeydown="javascript:fnTrapKD()" id="504404665865736276_d_8_d_1507392000000_d_2017092801_d_19870603001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665865736276_d_8_d_1507392000000_d_2017092801_d_19870603001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665865736276_d_8_d_1507392000000_d_2017092801_d_19870603001" value="504404665865736276_d_8_d_1507392000000_d_2017092801_d_19870603001" type="checkbox"><input size="4" name="504404665865736276_d_8_d_1507392000000_d_2017092801_d_19870603001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">39</td><td class="Freeze"><font color="red">Sun</font>,<br><font color="red">8/10/2017</font>&nbsp;</td><td class="Freeze">19890519001</td><td class="Freeze">Khoirul Huda</td><td class="Freeze">08:00</td><td class="Freeze">12:00</td><td class="Freeze">13:00</td><td class="Freeze">17:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665795260590_d_8_d_1507392000000_d_2017092799_d_19890519001_symbol" value="P8" size="2" title="08:00-17:00&nbsp;[12:00&nbsp;13:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092799','1507392000000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092799','1507392000000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092799','1507392000000')">[New]</a></center></td><td><center></center></td><td><select name="504404665795260590_d_8_d_1507392000000_d_2017092799_d_19890519001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665795260590_d_8_d_1507392000000_d_2017092799_d_19890519001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665795260590_d_8_d_1507392000000_d_2017092799_d_19890519001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665795260590_d_8_d_1507392000000_d_2017092799_d_19890519001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665795260590_d_8_d_1507392000000_d_2017092799_d_19890519001_oVerIdx" value="-" type="text"></td><td><select name="504404665795260590_d_8_d_1507392000000_d_2017092799_d_19890519001_status" onkeydown="javascript:fnTrapKD()" id="504404665795260590_d_8_d_1507392000000_d_2017092799_d_19890519001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665795260590_d_8_d_1507392000000_d_2017092799_d_19890519001_reason" onkeydown="javascript:fnTrapKD()" id="504404665795260590_d_8_d_1507392000000_d_2017092799_d_19890519001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665795260590_d_8_d_1507392000000_d_2017092799_d_19890519001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665795260590_d_8_d_1507392000000_d_2017092799_d_19890519001" value="504404665795260590_d_8_d_1507392000000_d_2017092799_d_19890519001" type="checkbox"><input size="4" name="504404665795260590_d_8_d_1507392000000_d_2017092799_d_19890519001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">40</td><td class="Freeze"><font color="red">Sun</font>,<br><font color="red">8/10/2017</font>&nbsp;</td><td class="Freeze">19930325001</td><td class="Freeze">Dita Ayuningtyas Fitriani</td><td class="Freeze">08:00</td><td class="Freeze">12:00</td><td class="Freeze">13:00</td><td class="Freeze">17:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665865735931_d_8_d_1507392000000_d_2017092798_d_19930325001_symbol" value="P8" size="2" title="08:00-17:00&nbsp;[12:00&nbsp;13:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092798','1507392000000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092798','1507392000000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092798','1507392000000')">[New]</a></center></td><td><center></center></td><td><select name="504404665865735931_d_8_d_1507392000000_d_2017092798_d_19930325001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665865735931_d_8_d_1507392000000_d_2017092798_d_19930325001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665865735931_d_8_d_1507392000000_d_2017092798_d_19930325001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665865735931_d_8_d_1507392000000_d_2017092798_d_19930325001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665865735931_d_8_d_1507392000000_d_2017092798_d_19930325001_oVerIdx" value="-" type="text"></td><td><select name="504404665865735931_d_8_d_1507392000000_d_2017092798_d_19930325001_status" onkeydown="javascript:fnTrapKD()" id="504404665865735931_d_8_d_1507392000000_d_2017092798_d_19930325001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665865735931_d_8_d_1507392000000_d_2017092798_d_19930325001_reason" onkeydown="javascript:fnTrapKD()" id="504404665865735931_d_8_d_1507392000000_d_2017092798_d_19930325001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665865735931_d_8_d_1507392000000_d_2017092798_d_19930325001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665865735931_d_8_d_1507392000000_d_2017092798_d_19930325001" value="504404665865735931_d_8_d_1507392000000_d_2017092798_d_19930325001" type="checkbox"><input size="4" name="504404665865735931_d_8_d_1507392000000_d_2017092798_d_19930325001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">41</td><td class="Freeze">Mon,<br>9/10/2017&nbsp;</td><td class="Freeze">19750922001</td><td class="Freeze">Dewa Gede Darmika</td><td class="Freeze">09:00</td><td class="Freeze">13:00</td><td class="Freeze">14:00</td><td class="Freeze">18:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665865736250_d_9_d_1507478400000_d_2017092800_d_19750922001_symbol" value="P9" size="2" title="09:00-18:00&nbsp;[13:00&nbsp;14:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092800','1507478400000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092800','1507478400000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092800','1507478400000')">[New]</a></center></td><td><center></center></td><td><select name="504404665865736250_d_9_d_1507478400000_d_2017092800_d_19750922001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665865736250_d_9_d_1507478400000_d_2017092800_d_19750922001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665865736250_d_9_d_1507478400000_d_2017092800_d_19750922001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665865736250_d_9_d_1507478400000_d_2017092800_d_19750922001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665865736250_d_9_d_1507478400000_d_2017092800_d_19750922001_oVerIdx" value="-" type="text"></td><td><select name="504404665865736250_d_9_d_1507478400000_d_2017092800_d_19750922001_status" onkeydown="javascript:fnTrapKD()" id="504404665865736250_d_9_d_1507478400000_d_2017092800_d_19750922001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665865736250_d_9_d_1507478400000_d_2017092800_d_19750922001_reason" onkeydown="javascript:fnTrapKD()" id="504404665865736250_d_9_d_1507478400000_d_2017092800_d_19750922001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665865736250_d_9_d_1507478400000_d_2017092800_d_19750922001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665865736250_d_9_d_1507478400000_d_2017092800_d_19750922001" value="504404665865736250_d_9_d_1507478400000_d_2017092800_d_19750922001" type="checkbox"><input size="4" name="504404665865736250_d_9_d_1507478400000_d_2017092800_d_19750922001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">42</td><td class="Freeze">Mon,<br>9/10/2017&nbsp;</td><td class="Freeze">19820202001</td><td class="Freeze">Arif Eko Cahyono</td><td class="Freeze">09:00</td><td class="Freeze">13:00</td><td class="Freeze">14:00</td><td class="Freeze">18:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665865736287_d_9_d_1507478400000_d_2017092802_d_19820202001_symbol" value="P9" size="2" title="09:00-18:00&nbsp;[13:00&nbsp;14:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092802','1507478400000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092802','1507478400000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092802','1507478400000')">[New]</a></center></td><td><center></center></td><td><select name="504404665865736287_d_9_d_1507478400000_d_2017092802_d_19820202001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665865736287_d_9_d_1507478400000_d_2017092802_d_19820202001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665865736287_d_9_d_1507478400000_d_2017092802_d_19820202001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665865736287_d_9_d_1507478400000_d_2017092802_d_19820202001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665865736287_d_9_d_1507478400000_d_2017092802_d_19820202001_oVerIdx" value="-" type="text"></td><td><select name="504404665865736287_d_9_d_1507478400000_d_2017092802_d_19820202001_status" onkeydown="javascript:fnTrapKD()" id="504404665865736287_d_9_d_1507478400000_d_2017092802_d_19820202001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665865736287_d_9_d_1507478400000_d_2017092802_d_19820202001_reason" onkeydown="javascript:fnTrapKD()" id="504404665865736287_d_9_d_1507478400000_d_2017092802_d_19820202001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665865736287_d_9_d_1507478400000_d_2017092802_d_19820202001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665865736287_d_9_d_1507478400000_d_2017092802_d_19820202001" value="504404665865736287_d_9_d_1507478400000_d_2017092802_d_19820202001" type="checkbox"><input size="4" name="504404665865736287_d_9_d_1507478400000_d_2017092802_d_19820202001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">43</td><td class="Freeze">Mon,<br>9/10/2017&nbsp;</td><td class="Freeze">19870603001</td><td class="Freeze">Moses Sangga Mila</td><td class="Freeze">08:00</td><td class="Freeze">12:00</td><td class="Freeze">13:00</td><td class="Freeze">17:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665865736276_d_9_d_1507478400000_d_2017092801_d_19870603001_symbol" value="P8" size="2" title="08:00-17:00&nbsp;[12:00&nbsp;13:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092801','1507478400000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092801','1507478400000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092801','1507478400000')">[New]</a></center></td><td><center></center></td><td><select name="504404665865736276_d_9_d_1507478400000_d_2017092801_d_19870603001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665865736276_d_9_d_1507478400000_d_2017092801_d_19870603001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665865736276_d_9_d_1507478400000_d_2017092801_d_19870603001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665865736276_d_9_d_1507478400000_d_2017092801_d_19870603001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665865736276_d_9_d_1507478400000_d_2017092801_d_19870603001_oVerIdx" value="-" type="text"></td><td><select name="504404665865736276_d_9_d_1507478400000_d_2017092801_d_19870603001_status" onkeydown="javascript:fnTrapKD()" id="504404665865736276_d_9_d_1507478400000_d_2017092801_d_19870603001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665865736276_d_9_d_1507478400000_d_2017092801_d_19870603001_reason" onkeydown="javascript:fnTrapKD()" id="504404665865736276_d_9_d_1507478400000_d_2017092801_d_19870603001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665865736276_d_9_d_1507478400000_d_2017092801_d_19870603001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665865736276_d_9_d_1507478400000_d_2017092801_d_19870603001" value="504404665865736276_d_9_d_1507478400000_d_2017092801_d_19870603001" type="checkbox"><input size="4" name="504404665865736276_d_9_d_1507478400000_d_2017092801_d_19870603001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">44</td><td class="Freeze">Mon,<br>9/10/2017&nbsp;</td><td class="Freeze">19890519001</td><td class="Freeze">Khoirul Huda</td><td class="Freeze">08:00</td><td class="Freeze">12:00</td><td class="Freeze">13:00</td><td class="Freeze">17:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665795260590_d_9_d_1507478400000_d_2017092799_d_19890519001_symbol" value="P8" size="2" title="08:00-17:00&nbsp;[12:00&nbsp;13:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092799','1507478400000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092799','1507478400000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092799','1507478400000')">[New]</a></center></td><td><center></center></td><td><select name="504404665795260590_d_9_d_1507478400000_d_2017092799_d_19890519001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665795260590_d_9_d_1507478400000_d_2017092799_d_19890519001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665795260590_d_9_d_1507478400000_d_2017092799_d_19890519001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665795260590_d_9_d_1507478400000_d_2017092799_d_19890519001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665795260590_d_9_d_1507478400000_d_2017092799_d_19890519001_oVerIdx" value="-" type="text"></td><td><select name="504404665795260590_d_9_d_1507478400000_d_2017092799_d_19890519001_status" onkeydown="javascript:fnTrapKD()" id="504404665795260590_d_9_d_1507478400000_d_2017092799_d_19890519001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665795260590_d_9_d_1507478400000_d_2017092799_d_19890519001_reason" onkeydown="javascript:fnTrapKD()" id="504404665795260590_d_9_d_1507478400000_d_2017092799_d_19890519001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665795260590_d_9_d_1507478400000_d_2017092799_d_19890519001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665795260590_d_9_d_1507478400000_d_2017092799_d_19890519001" value="504404665795260590_d_9_d_1507478400000_d_2017092799_d_19890519001" type="checkbox"><input size="4" name="504404665795260590_d_9_d_1507478400000_d_2017092799_d_19890519001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">45</td><td class="Freeze">Mon,<br>9/10/2017&nbsp;</td><td class="Freeze">19930325001</td><td class="Freeze">Dita Ayuningtyas Fitriani</td><td class="Freeze">08:00</td><td class="Freeze">12:00</td><td class="Freeze">13:00</td><td class="Freeze">17:00</td><td class="Freeze">9/10/17<br>14:21 - M<br></td><td class="Freeze">-</td><td class="Freeze">-</td><td>9/10/17<br>23:21 - M<br></td><td><font color="#CC0000">-6h, -21m</font></td><td>6h, 21m</td><td><font color="#000000"><b><center>8h, </center></b></font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665865735931_d_9_d_1507478400000_d_2017092798_d_19930325001_symbol" value="P8" size="2" title="08:00-17:00&nbsp;[12:00&nbsp;13:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092798','1507478400000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092798','1507478400000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092798','1507478400000')">[New]</a></center></td><td><center></center></td><td><select name="504404665865735931_d_9_d_1507478400000_d_2017092798_d_19930325001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665865735931_d_9_d_1507478400000_d_2017092798_d_19930325001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665865735931_d_9_d_1507478400000_d_2017092798_d_19930325001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665865735931_d_9_d_1507478400000_d_2017092798_d_19930325001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665865735931_d_9_d_1507478400000_d_2017092798_d_19930325001_oVerIdx" value="-" type="text"></td><td><select name="504404665865735931_d_9_d_1507478400000_d_2017092798_d_19930325001_status" onkeydown="javascript:fnTrapKD()" id="504404665865735931_d_9_d_1507478400000_d_2017092798_d_19930325001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665865735931_d_9_d_1507478400000_d_2017092798_d_19930325001_reason" onkeydown="javascript:fnTrapKD()" id="504404665865735931_d_9_d_1507478400000_d_2017092798_d_19930325001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665865735931_d_9_d_1507478400000_d_2017092798_d_19930325001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665865735931_d_9_d_1507478400000_d_2017092798_d_19930325001" value="504404665865735931_d_9_d_1507478400000_d_2017092798_d_19930325001" type="checkbox"><input size="4" name="504404665865735931_d_9_d_1507478400000_d_2017092798_d_19930325001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">46</td><td class="Freeze">Tue,<br>10/10/2017&nbsp;</td><td class="Freeze">19750922001</td><td class="Freeze">Dewa Gede Darmika</td><td class="Freeze">09:00</td><td class="Freeze">13:00</td><td class="Freeze">14:00</td><td class="Freeze">18:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665865736250_d_10_d_1507564800000_d_2017092800_d_19750922001_symbol" value="P9" size="2" title="09:00-18:00&nbsp;[13:00&nbsp;14:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092800','1507564800000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092800','1507564800000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092800','1507564800000')">[New]</a></center></td><td><center></center></td><td><select name="504404665865736250_d_10_d_1507564800000_d_2017092800_d_19750922001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665865736250_d_10_d_1507564800000_d_2017092800_d_19750922001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665865736250_d_10_d_1507564800000_d_2017092800_d_19750922001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665865736250_d_10_d_1507564800000_d_2017092800_d_19750922001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665865736250_d_10_d_1507564800000_d_2017092800_d_19750922001_oVerIdx" value="-" type="text"></td><td><select name="504404665865736250_d_10_d_1507564800000_d_2017092800_d_19750922001_status" onkeydown="javascript:fnTrapKD()" id="504404665865736250_d_10_d_1507564800000_d_2017092800_d_19750922001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665865736250_d_10_d_1507564800000_d_2017092800_d_19750922001_reason" onkeydown="javascript:fnTrapKD()" id="504404665865736250_d_10_d_1507564800000_d_2017092800_d_19750922001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665865736250_d_10_d_1507564800000_d_2017092800_d_19750922001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665865736250_d_10_d_1507564800000_d_2017092800_d_19750922001" value="504404665865736250_d_10_d_1507564800000_d_2017092800_d_19750922001" type="checkbox"><input size="4" name="504404665865736250_d_10_d_1507564800000_d_2017092800_d_19750922001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">47</td><td class="Freeze">Tue,<br>10/10/2017&nbsp;</td><td class="Freeze">19820202001</td><td class="Freeze">Arif Eko Cahyono</td><td class="Freeze">09:00</td><td class="Freeze">13:00</td><td class="Freeze">14:00</td><td class="Freeze">18:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665865736287_d_10_d_1507564800000_d_2017092802_d_19820202001_symbol" value="P9" size="2" title="09:00-18:00&nbsp;[13:00&nbsp;14:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092802','1507564800000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092802','1507564800000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092802','1507564800000')">[New]</a></center></td><td><center></center></td><td><select name="504404665865736287_d_10_d_1507564800000_d_2017092802_d_19820202001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665865736287_d_10_d_1507564800000_d_2017092802_d_19820202001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665865736287_d_10_d_1507564800000_d_2017092802_d_19820202001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665865736287_d_10_d_1507564800000_d_2017092802_d_19820202001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665865736287_d_10_d_1507564800000_d_2017092802_d_19820202001_oVerIdx" value="-" type="text"></td><td><select name="504404665865736287_d_10_d_1507564800000_d_2017092802_d_19820202001_status" onkeydown="javascript:fnTrapKD()" id="504404665865736287_d_10_d_1507564800000_d_2017092802_d_19820202001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665865736287_d_10_d_1507564800000_d_2017092802_d_19820202001_reason" onkeydown="javascript:fnTrapKD()" id="504404665865736287_d_10_d_1507564800000_d_2017092802_d_19820202001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665865736287_d_10_d_1507564800000_d_2017092802_d_19820202001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665865736287_d_10_d_1507564800000_d_2017092802_d_19820202001" value="504404665865736287_d_10_d_1507564800000_d_2017092802_d_19820202001" type="checkbox"><input size="4" name="504404665865736287_d_10_d_1507564800000_d_2017092802_d_19820202001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">48</td><td class="Freeze">Tue,<br>10/10/2017&nbsp;</td><td class="Freeze">19870603001</td><td class="Freeze">Moses Sangga Mila</td><td class="Freeze">08:00</td><td class="Freeze">12:00</td><td class="Freeze">13:00</td><td class="Freeze">17:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665865736276_d_10_d_1507564800000_d_2017092801_d_19870603001_symbol" value="P8" size="2" title="08:00-17:00&nbsp;[12:00&nbsp;13:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092801','1507564800000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092801','1507564800000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092801','1507564800000')">[New]</a></center></td><td><center></center></td><td><select name="504404665865736276_d_10_d_1507564800000_d_2017092801_d_19870603001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665865736276_d_10_d_1507564800000_d_2017092801_d_19870603001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665865736276_d_10_d_1507564800000_d_2017092801_d_19870603001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665865736276_d_10_d_1507564800000_d_2017092801_d_19870603001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665865736276_d_10_d_1507564800000_d_2017092801_d_19870603001_oVerIdx" value="-" type="text"></td><td><select name="504404665865736276_d_10_d_1507564800000_d_2017092801_d_19870603001_status" onkeydown="javascript:fnTrapKD()" id="504404665865736276_d_10_d_1507564800000_d_2017092801_d_19870603001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665865736276_d_10_d_1507564800000_d_2017092801_d_19870603001_reason" onkeydown="javascript:fnTrapKD()" id="504404665865736276_d_10_d_1507564800000_d_2017092801_d_19870603001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665865736276_d_10_d_1507564800000_d_2017092801_d_19870603001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665865736276_d_10_d_1507564800000_d_2017092801_d_19870603001" value="504404665865736276_d_10_d_1507564800000_d_2017092801_d_19870603001" type="checkbox"><input size="4" name="504404665865736276_d_10_d_1507564800000_d_2017092801_d_19870603001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">49</td><td class="Freeze">Tue,<br>10/10/2017&nbsp;</td><td class="Freeze">19890519001</td><td class="Freeze">Khoirul Huda</td><td class="Freeze">08:00</td><td class="Freeze">12:00</td><td class="Freeze">13:00</td><td class="Freeze">17:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665795260590_d_10_d_1507564800000_d_2017092799_d_19890519001_symbol" value="P8" size="2" title="08:00-17:00&nbsp;[12:00&nbsp;13:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092799','1507564800000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092799','1507564800000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092799','1507564800000')">[New]</a></center></td><td><center></center></td><td><select name="504404665795260590_d_10_d_1507564800000_d_2017092799_d_19890519001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665795260590_d_10_d_1507564800000_d_2017092799_d_19890519001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665795260590_d_10_d_1507564800000_d_2017092799_d_19890519001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665795260590_d_10_d_1507564800000_d_2017092799_d_19890519001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665795260590_d_10_d_1507564800000_d_2017092799_d_19890519001_oVerIdx" value="-" type="text"></td><td><select name="504404665795260590_d_10_d_1507564800000_d_2017092799_d_19890519001_status" onkeydown="javascript:fnTrapKD()" id="504404665795260590_d_10_d_1507564800000_d_2017092799_d_19890519001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665795260590_d_10_d_1507564800000_d_2017092799_d_19890519001_reason" onkeydown="javascript:fnTrapKD()" id="504404665795260590_d_10_d_1507564800000_d_2017092799_d_19890519001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665795260590_d_10_d_1507564800000_d_2017092799_d_19890519001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665795260590_d_10_d_1507564800000_d_2017092799_d_19890519001" value="504404665795260590_d_10_d_1507564800000_d_2017092799_d_19890519001" type="checkbox"><input size="4" name="504404665795260590_d_10_d_1507564800000_d_2017092799_d_19890519001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">50</td><td class="Freeze">Tue,<br>10/10/2017&nbsp;</td><td class="Freeze">19930325001</td><td class="Freeze">Dita Ayuningtyas Fitriani</td><td class="Freeze">08:00</td><td class="Freeze">12:00</td><td class="Freeze">13:00</td><td class="Freeze">17:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665865735931_d_10_d_1507564800000_d_2017092798_d_19930325001_symbol" value="P8" size="2" title="08:00-17:00&nbsp;[12:00&nbsp;13:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092798','1507564800000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092798','1507564800000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092798','1507564800000')">[New]</a></center></td><td><center></center></td><td><select name="504404665865735931_d_10_d_1507564800000_d_2017092798_d_19930325001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665865735931_d_10_d_1507564800000_d_2017092798_d_19930325001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665865735931_d_10_d_1507564800000_d_2017092798_d_19930325001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665865735931_d_10_d_1507564800000_d_2017092798_d_19930325001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665865735931_d_10_d_1507564800000_d_2017092798_d_19930325001_oVerIdx" value="-" type="text"></td><td><select name="504404665865735931_d_10_d_1507564800000_d_2017092798_d_19930325001_status" onkeydown="javascript:fnTrapKD()" id="504404665865735931_d_10_d_1507564800000_d_2017092798_d_19930325001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665865735931_d_10_d_1507564800000_d_2017092798_d_19930325001_reason" onkeydown="javascript:fnTrapKD()" id="504404665865735931_d_10_d_1507564800000_d_2017092798_d_19930325001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665865735931_d_10_d_1507564800000_d_2017092798_d_19930325001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665865735931_d_10_d_1507564800000_d_2017092798_d_19930325001" value="504404665865735931_d_10_d_1507564800000_d_2017092798_d_19930325001" type="checkbox"><input size="4" name="504404665865735931_d_10_d_1507564800000_d_2017092798_d_19930325001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">51</td><td class="Freeze">Wed,<br>11/10/2017&nbsp;</td><td class="Freeze">19750922001</td><td class="Freeze">Dewa Gede Darmika</td><td class="Freeze">09:00</td><td class="Freeze">13:00</td><td class="Freeze">14:00</td><td class="Freeze">18:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665865736250_d_11_d_1507651200000_d_2017092800_d_19750922001_symbol" value="P9" size="2" title="09:00-18:00&nbsp;[13:00&nbsp;14:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092800','1507651200000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092800','1507651200000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092800','1507651200000')">[New]</a></center></td><td><center></center></td><td><select name="504404665865736250_d_11_d_1507651200000_d_2017092800_d_19750922001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665865736250_d_11_d_1507651200000_d_2017092800_d_19750922001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665865736250_d_11_d_1507651200000_d_2017092800_d_19750922001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665865736250_d_11_d_1507651200000_d_2017092800_d_19750922001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665865736250_d_11_d_1507651200000_d_2017092800_d_19750922001_oVerIdx" value="-" type="text"></td><td><select name="504404665865736250_d_11_d_1507651200000_d_2017092800_d_19750922001_status" onkeydown="javascript:fnTrapKD()" id="504404665865736250_d_11_d_1507651200000_d_2017092800_d_19750922001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665865736250_d_11_d_1507651200000_d_2017092800_d_19750922001_reason" onkeydown="javascript:fnTrapKD()" id="504404665865736250_d_11_d_1507651200000_d_2017092800_d_19750922001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665865736250_d_11_d_1507651200000_d_2017092800_d_19750922001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665865736250_d_11_d_1507651200000_d_2017092800_d_19750922001" value="504404665865736250_d_11_d_1507651200000_d_2017092800_d_19750922001" type="checkbox"><input size="4" name="504404665865736250_d_11_d_1507651200000_d_2017092800_d_19750922001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">52</td><td class="Freeze">Wed,<br>11/10/2017&nbsp;</td><td class="Freeze">19820202001</td><td class="Freeze">Arif Eko Cahyono</td><td class="Freeze">09:00</td><td class="Freeze">13:00</td><td class="Freeze">14:00</td><td class="Freeze">18:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665865736287_d_11_d_1507651200000_d_2017092802_d_19820202001_symbol" value="P9" size="2" title="09:00-18:00&nbsp;[13:00&nbsp;14:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092802','1507651200000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092802','1507651200000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092802','1507651200000')">[New]</a></center></td><td><center></center></td><td><select name="504404665865736287_d_11_d_1507651200000_d_2017092802_d_19820202001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665865736287_d_11_d_1507651200000_d_2017092802_d_19820202001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665865736287_d_11_d_1507651200000_d_2017092802_d_19820202001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665865736287_d_11_d_1507651200000_d_2017092802_d_19820202001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665865736287_d_11_d_1507651200000_d_2017092802_d_19820202001_oVerIdx" value="-" type="text"></td><td><select name="504404665865736287_d_11_d_1507651200000_d_2017092802_d_19820202001_status" onkeydown="javascript:fnTrapKD()" id="504404665865736287_d_11_d_1507651200000_d_2017092802_d_19820202001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665865736287_d_11_d_1507651200000_d_2017092802_d_19820202001_reason" onkeydown="javascript:fnTrapKD()" id="504404665865736287_d_11_d_1507651200000_d_2017092802_d_19820202001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665865736287_d_11_d_1507651200000_d_2017092802_d_19820202001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665865736287_d_11_d_1507651200000_d_2017092802_d_19820202001" value="504404665865736287_d_11_d_1507651200000_d_2017092802_d_19820202001" type="checkbox"><input size="4" name="504404665865736287_d_11_d_1507651200000_d_2017092802_d_19820202001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">53</td><td class="Freeze">Wed,<br>11/10/2017&nbsp;</td><td class="Freeze">19870603001</td><td class="Freeze">Moses Sangga Mila</td><td class="Freeze">08:00</td><td class="Freeze">12:00</td><td class="Freeze">13:00</td><td class="Freeze">17:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665865736276_d_11_d_1507651200000_d_2017092801_d_19870603001_symbol" value="P8" size="2" title="08:00-17:00&nbsp;[12:00&nbsp;13:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092801','1507651200000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092801','1507651200000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092801','1507651200000')">[New]</a></center></td><td><center></center></td><td><select name="504404665865736276_d_11_d_1507651200000_d_2017092801_d_19870603001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665865736276_d_11_d_1507651200000_d_2017092801_d_19870603001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665865736276_d_11_d_1507651200000_d_2017092801_d_19870603001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665865736276_d_11_d_1507651200000_d_2017092801_d_19870603001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665865736276_d_11_d_1507651200000_d_2017092801_d_19870603001_oVerIdx" value="-" type="text"></td><td><select name="504404665865736276_d_11_d_1507651200000_d_2017092801_d_19870603001_status" onkeydown="javascript:fnTrapKD()" id="504404665865736276_d_11_d_1507651200000_d_2017092801_d_19870603001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665865736276_d_11_d_1507651200000_d_2017092801_d_19870603001_reason" onkeydown="javascript:fnTrapKD()" id="504404665865736276_d_11_d_1507651200000_d_2017092801_d_19870603001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665865736276_d_11_d_1507651200000_d_2017092801_d_19870603001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665865736276_d_11_d_1507651200000_d_2017092801_d_19870603001" value="504404665865736276_d_11_d_1507651200000_d_2017092801_d_19870603001" type="checkbox"><input size="4" name="504404665865736276_d_11_d_1507651200000_d_2017092801_d_19870603001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">54</td><td class="Freeze">Wed,<br>11/10/2017&nbsp;</td><td class="Freeze">19890519001</td><td class="Freeze">Khoirul Huda</td><td class="Freeze">08:00</td><td class="Freeze">12:00</td><td class="Freeze">13:00</td><td class="Freeze">17:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665795260590_d_11_d_1507651200000_d_2017092799_d_19890519001_symbol" value="P8" size="2" title="08:00-17:00&nbsp;[12:00&nbsp;13:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092799','1507651200000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092799','1507651200000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092799','1507651200000')">[New]</a></center></td><td><center></center></td><td><select name="504404665795260590_d_11_d_1507651200000_d_2017092799_d_19890519001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665795260590_d_11_d_1507651200000_d_2017092799_d_19890519001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665795260590_d_11_d_1507651200000_d_2017092799_d_19890519001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665795260590_d_11_d_1507651200000_d_2017092799_d_19890519001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665795260590_d_11_d_1507651200000_d_2017092799_d_19890519001_oVerIdx" value="-" type="text"></td><td><select name="504404665795260590_d_11_d_1507651200000_d_2017092799_d_19890519001_status" onkeydown="javascript:fnTrapKD()" id="504404665795260590_d_11_d_1507651200000_d_2017092799_d_19890519001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665795260590_d_11_d_1507651200000_d_2017092799_d_19890519001_reason" onkeydown="javascript:fnTrapKD()" id="504404665795260590_d_11_d_1507651200000_d_2017092799_d_19890519001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665795260590_d_11_d_1507651200000_d_2017092799_d_19890519001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665795260590_d_11_d_1507651200000_d_2017092799_d_19890519001" value="504404665795260590_d_11_d_1507651200000_d_2017092799_d_19890519001" type="checkbox"><input size="4" name="504404665795260590_d_11_d_1507651200000_d_2017092799_d_19890519001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">55</td><td class="Freeze">Wed,<br>11/10/2017&nbsp;</td><td class="Freeze">19930325001</td><td class="Freeze">Dita Ayuningtyas Fitriani</td><td class="Freeze">08:00</td><td class="Freeze">12:00</td><td class="Freeze">13:00</td><td class="Freeze">17:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665865735931_d_11_d_1507651200000_d_2017092798_d_19930325001_symbol" value="P8" size="2" title="08:00-17:00&nbsp;[12:00&nbsp;13:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092798','1507651200000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092798','1507651200000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092798','1507651200000')">[New]</a></center></td><td><center></center></td><td><select name="504404665865735931_d_11_d_1507651200000_d_2017092798_d_19930325001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665865735931_d_11_d_1507651200000_d_2017092798_d_19930325001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665865735931_d_11_d_1507651200000_d_2017092798_d_19930325001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665865735931_d_11_d_1507651200000_d_2017092798_d_19930325001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665865735931_d_11_d_1507651200000_d_2017092798_d_19930325001_oVerIdx" value="-" type="text"></td><td><select name="504404665865735931_d_11_d_1507651200000_d_2017092798_d_19930325001_status" onkeydown="javascript:fnTrapKD()" id="504404665865735931_d_11_d_1507651200000_d_2017092798_d_19930325001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665865735931_d_11_d_1507651200000_d_2017092798_d_19930325001_reason" onkeydown="javascript:fnTrapKD()" id="504404665865735931_d_11_d_1507651200000_d_2017092798_d_19930325001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665865735931_d_11_d_1507651200000_d_2017092798_d_19930325001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665865735931_d_11_d_1507651200000_d_2017092798_d_19930325001" value="504404665865735931_d_11_d_1507651200000_d_2017092798_d_19930325001" type="checkbox"><input size="4" name="504404665865735931_d_11_d_1507651200000_d_2017092798_d_19930325001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">56</td><td class="Freeze">Thu,<br>12/10/2017&nbsp;</td><td class="Freeze">19750922001</td><td class="Freeze">Dewa Gede Darmika</td><td class="Freeze">09:00</td><td class="Freeze">13:00</td><td class="Freeze">14:00</td><td class="Freeze">18:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665865736250_d_12_d_1507737600000_d_2017092800_d_19750922001_symbol" value="P9" size="2" title="09:00-18:00&nbsp;[13:00&nbsp;14:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092800','1507737600000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092800','1507737600000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092800','1507737600000')">[New]</a></center></td><td><center></center></td><td><select name="504404665865736250_d_12_d_1507737600000_d_2017092800_d_19750922001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665865736250_d_12_d_1507737600000_d_2017092800_d_19750922001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665865736250_d_12_d_1507737600000_d_2017092800_d_19750922001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665865736250_d_12_d_1507737600000_d_2017092800_d_19750922001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665865736250_d_12_d_1507737600000_d_2017092800_d_19750922001_oVerIdx" value="-" type="text"></td><td><select name="504404665865736250_d_12_d_1507737600000_d_2017092800_d_19750922001_status" onkeydown="javascript:fnTrapKD()" id="504404665865736250_d_12_d_1507737600000_d_2017092800_d_19750922001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665865736250_d_12_d_1507737600000_d_2017092800_d_19750922001_reason" onkeydown="javascript:fnTrapKD()" id="504404665865736250_d_12_d_1507737600000_d_2017092800_d_19750922001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665865736250_d_12_d_1507737600000_d_2017092800_d_19750922001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665865736250_d_12_d_1507737600000_d_2017092800_d_19750922001" value="504404665865736250_d_12_d_1507737600000_d_2017092800_d_19750922001" type="checkbox"><input size="4" name="504404665865736250_d_12_d_1507737600000_d_2017092800_d_19750922001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">57</td><td class="Freeze">Thu,<br>12/10/2017&nbsp;</td><td class="Freeze">19820202001</td><td class="Freeze">Arif Eko Cahyono</td><td class="Freeze">09:00</td><td class="Freeze">13:00</td><td class="Freeze">14:00</td><td class="Freeze">18:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665865736287_d_12_d_1507737600000_d_2017092802_d_19820202001_symbol" value="P9" size="2" title="09:00-18:00&nbsp;[13:00&nbsp;14:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092802','1507737600000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092802','1507737600000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092802','1507737600000')">[New]</a></center></td><td><center></center></td><td><select name="504404665865736287_d_12_d_1507737600000_d_2017092802_d_19820202001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665865736287_d_12_d_1507737600000_d_2017092802_d_19820202001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665865736287_d_12_d_1507737600000_d_2017092802_d_19820202001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665865736287_d_12_d_1507737600000_d_2017092802_d_19820202001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665865736287_d_12_d_1507737600000_d_2017092802_d_19820202001_oVerIdx" value="-" type="text"></td><td><select name="504404665865736287_d_12_d_1507737600000_d_2017092802_d_19820202001_status" onkeydown="javascript:fnTrapKD()" id="504404665865736287_d_12_d_1507737600000_d_2017092802_d_19820202001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665865736287_d_12_d_1507737600000_d_2017092802_d_19820202001_reason" onkeydown="javascript:fnTrapKD()" id="504404665865736287_d_12_d_1507737600000_d_2017092802_d_19820202001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665865736287_d_12_d_1507737600000_d_2017092802_d_19820202001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665865736287_d_12_d_1507737600000_d_2017092802_d_19820202001" value="504404665865736287_d_12_d_1507737600000_d_2017092802_d_19820202001" type="checkbox"><input size="4" name="504404665865736287_d_12_d_1507737600000_d_2017092802_d_19820202001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">58</td><td class="Freeze">Thu,<br>12/10/2017&nbsp;</td><td class="Freeze">19870603001</td><td class="Freeze">Moses Sangga Mila</td><td class="Freeze">08:00</td><td class="Freeze">12:00</td><td class="Freeze">13:00</td><td class="Freeze">17:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665865736276_d_12_d_1507737600000_d_2017092801_d_19870603001_symbol" value="P8" size="2" title="08:00-17:00&nbsp;[12:00&nbsp;13:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092801','1507737600000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092801','1507737600000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092801','1507737600000')">[New]</a></center></td><td><center></center></td><td><select name="504404665865736276_d_12_d_1507737600000_d_2017092801_d_19870603001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665865736276_d_12_d_1507737600000_d_2017092801_d_19870603001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665865736276_d_12_d_1507737600000_d_2017092801_d_19870603001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665865736276_d_12_d_1507737600000_d_2017092801_d_19870603001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665865736276_d_12_d_1507737600000_d_2017092801_d_19870603001_oVerIdx" value="-" type="text"></td><td><select name="504404665865736276_d_12_d_1507737600000_d_2017092801_d_19870603001_status" onkeydown="javascript:fnTrapKD()" id="504404665865736276_d_12_d_1507737600000_d_2017092801_d_19870603001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665865736276_d_12_d_1507737600000_d_2017092801_d_19870603001_reason" onkeydown="javascript:fnTrapKD()" id="504404665865736276_d_12_d_1507737600000_d_2017092801_d_19870603001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665865736276_d_12_d_1507737600000_d_2017092801_d_19870603001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665865736276_d_12_d_1507737600000_d_2017092801_d_19870603001" value="504404665865736276_d_12_d_1507737600000_d_2017092801_d_19870603001" type="checkbox"><input size="4" name="504404665865736276_d_12_d_1507737600000_d_2017092801_d_19870603001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">59</td><td class="Freeze">Thu,<br>12/10/2017&nbsp;</td><td class="Freeze">19890519001</td><td class="Freeze">Khoirul Huda</td><td class="Freeze">08:00</td><td class="Freeze">12:00</td><td class="Freeze">13:00</td><td class="Freeze">17:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665795260590_d_12_d_1507737600000_d_2017092799_d_19890519001_symbol" value="P8" size="2" title="08:00-17:00&nbsp;[12:00&nbsp;13:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092799','1507737600000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092799','1507737600000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092799','1507737600000')">[New]</a></center></td><td><center></center></td><td><select name="504404665795260590_d_12_d_1507737600000_d_2017092799_d_19890519001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665795260590_d_12_d_1507737600000_d_2017092799_d_19890519001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665795260590_d_12_d_1507737600000_d_2017092799_d_19890519001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665795260590_d_12_d_1507737600000_d_2017092799_d_19890519001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665795260590_d_12_d_1507737600000_d_2017092799_d_19890519001_oVerIdx" value="-" type="text"></td><td><select name="504404665795260590_d_12_d_1507737600000_d_2017092799_d_19890519001_status" onkeydown="javascript:fnTrapKD()" id="504404665795260590_d_12_d_1507737600000_d_2017092799_d_19890519001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665795260590_d_12_d_1507737600000_d_2017092799_d_19890519001_reason" onkeydown="javascript:fnTrapKD()" id="504404665795260590_d_12_d_1507737600000_d_2017092799_d_19890519001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665795260590_d_12_d_1507737600000_d_2017092799_d_19890519001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665795260590_d_12_d_1507737600000_d_2017092799_d_19890519001" value="504404665795260590_d_12_d_1507737600000_d_2017092799_d_19890519001" type="checkbox"><input size="4" name="504404665795260590_d_12_d_1507737600000_d_2017092799_d_19890519001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">60</td><td class="Freeze">Thu,<br>12/10/2017&nbsp;</td><td class="Freeze">19930325001</td><td class="Freeze">Dita Ayuningtyas Fitriani</td><td class="Freeze">08:00</td><td class="Freeze">12:00</td><td class="Freeze">13:00</td><td class="Freeze">17:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665865735931_d_12_d_1507737600000_d_2017092798_d_19930325001_symbol" value="P8" size="2" title="08:00-17:00&nbsp;[12:00&nbsp;13:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092798','1507737600000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092798','1507737600000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092798','1507737600000')">[New]</a></center></td><td><center></center></td><td><select name="504404665865735931_d_12_d_1507737600000_d_2017092798_d_19930325001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665865735931_d_12_d_1507737600000_d_2017092798_d_19930325001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665865735931_d_12_d_1507737600000_d_2017092798_d_19930325001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665865735931_d_12_d_1507737600000_d_2017092798_d_19930325001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665865735931_d_12_d_1507737600000_d_2017092798_d_19930325001_oVerIdx" value="-" type="text"></td><td><select name="504404665865735931_d_12_d_1507737600000_d_2017092798_d_19930325001_status" onkeydown="javascript:fnTrapKD()" id="504404665865735931_d_12_d_1507737600000_d_2017092798_d_19930325001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665865735931_d_12_d_1507737600000_d_2017092798_d_19930325001_reason" onkeydown="javascript:fnTrapKD()" id="504404665865735931_d_12_d_1507737600000_d_2017092798_d_19930325001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665865735931_d_12_d_1507737600000_d_2017092798_d_19930325001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665865735931_d_12_d_1507737600000_d_2017092798_d_19930325001" value="504404665865735931_d_12_d_1507737600000_d_2017092798_d_19930325001" type="checkbox"><input size="4" name="504404665865735931_d_12_d_1507737600000_d_2017092798_d_19930325001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">61</td><td class="Freeze">Fri,<br>13/10/2017&nbsp;</td><td class="Freeze">19750922001</td><td class="Freeze">Dewa Gede Darmika</td><td class="Freeze">09:00</td><td class="Freeze">13:00</td><td class="Freeze">14:00</td><td class="Freeze">18:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665865736250_d_13_d_1507824000000_d_2017092800_d_19750922001_symbol" value="P9" size="2" title="09:00-18:00&nbsp;[13:00&nbsp;14:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092800','1507824000000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092800','1507824000000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092800','1507824000000')">[New]</a></center></td><td><center></center></td><td><select name="504404665865736250_d_13_d_1507824000000_d_2017092800_d_19750922001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665865736250_d_13_d_1507824000000_d_2017092800_d_19750922001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665865736250_d_13_d_1507824000000_d_2017092800_d_19750922001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665865736250_d_13_d_1507824000000_d_2017092800_d_19750922001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665865736250_d_13_d_1507824000000_d_2017092800_d_19750922001_oVerIdx" value="-" type="text"></td><td><select name="504404665865736250_d_13_d_1507824000000_d_2017092800_d_19750922001_status" onkeydown="javascript:fnTrapKD()" id="504404665865736250_d_13_d_1507824000000_d_2017092800_d_19750922001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665865736250_d_13_d_1507824000000_d_2017092800_d_19750922001_reason" onkeydown="javascript:fnTrapKD()" id="504404665865736250_d_13_d_1507824000000_d_2017092800_d_19750922001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665865736250_d_13_d_1507824000000_d_2017092800_d_19750922001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665865736250_d_13_d_1507824000000_d_2017092800_d_19750922001" value="504404665865736250_d_13_d_1507824000000_d_2017092800_d_19750922001" type="checkbox"><input size="4" name="504404665865736250_d_13_d_1507824000000_d_2017092800_d_19750922001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">62</td><td class="Freeze">Fri,<br>13/10/2017&nbsp;</td><td class="Freeze">19820202001</td><td class="Freeze">Arif Eko Cahyono</td><td class="Freeze">09:00</td><td class="Freeze">13:00</td><td class="Freeze">14:00</td><td class="Freeze">18:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665865736287_d_13_d_1507824000000_d_2017092802_d_19820202001_symbol" value="P9" size="2" title="09:00-18:00&nbsp;[13:00&nbsp;14:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092802','1507824000000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092802','1507824000000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092802','1507824000000')">[New]</a></center></td><td><center></center></td><td><select name="504404665865736287_d_13_d_1507824000000_d_2017092802_d_19820202001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665865736287_d_13_d_1507824000000_d_2017092802_d_19820202001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665865736287_d_13_d_1507824000000_d_2017092802_d_19820202001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665865736287_d_13_d_1507824000000_d_2017092802_d_19820202001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665865736287_d_13_d_1507824000000_d_2017092802_d_19820202001_oVerIdx" value="-" type="text"></td><td><select name="504404665865736287_d_13_d_1507824000000_d_2017092802_d_19820202001_status" onkeydown="javascript:fnTrapKD()" id="504404665865736287_d_13_d_1507824000000_d_2017092802_d_19820202001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665865736287_d_13_d_1507824000000_d_2017092802_d_19820202001_reason" onkeydown="javascript:fnTrapKD()" id="504404665865736287_d_13_d_1507824000000_d_2017092802_d_19820202001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665865736287_d_13_d_1507824000000_d_2017092802_d_19820202001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665865736287_d_13_d_1507824000000_d_2017092802_d_19820202001" value="504404665865736287_d_13_d_1507824000000_d_2017092802_d_19820202001" type="checkbox"><input size="4" name="504404665865736287_d_13_d_1507824000000_d_2017092802_d_19820202001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">63</td><td class="Freeze">Fri,<br>13/10/2017&nbsp;</td><td class="Freeze">19870603001</td><td class="Freeze">Moses Sangga Mila</td><td class="Freeze">08:00</td><td class="Freeze">12:00</td><td class="Freeze">13:00</td><td class="Freeze">17:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665865736276_d_13_d_1507824000000_d_2017092801_d_19870603001_symbol" value="P8" size="2" title="08:00-17:00&nbsp;[12:00&nbsp;13:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092801','1507824000000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092801','1507824000000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092801','1507824000000')">[New]</a></center></td><td><center></center></td><td><select name="504404665865736276_d_13_d_1507824000000_d_2017092801_d_19870603001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665865736276_d_13_d_1507824000000_d_2017092801_d_19870603001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665865736276_d_13_d_1507824000000_d_2017092801_d_19870603001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665865736276_d_13_d_1507824000000_d_2017092801_d_19870603001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665865736276_d_13_d_1507824000000_d_2017092801_d_19870603001_oVerIdx" value="-" type="text"></td><td><select name="504404665865736276_d_13_d_1507824000000_d_2017092801_d_19870603001_status" onkeydown="javascript:fnTrapKD()" id="504404665865736276_d_13_d_1507824000000_d_2017092801_d_19870603001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665865736276_d_13_d_1507824000000_d_2017092801_d_19870603001_reason" onkeydown="javascript:fnTrapKD()" id="504404665865736276_d_13_d_1507824000000_d_2017092801_d_19870603001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665865736276_d_13_d_1507824000000_d_2017092801_d_19870603001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665865736276_d_13_d_1507824000000_d_2017092801_d_19870603001" value="504404665865736276_d_13_d_1507824000000_d_2017092801_d_19870603001" type="checkbox"><input size="4" name="504404665865736276_d_13_d_1507824000000_d_2017092801_d_19870603001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">64</td><td class="Freeze">Fri,<br>13/10/2017&nbsp;</td><td class="Freeze">19890519001</td><td class="Freeze">Khoirul Huda</td><td class="Freeze">08:00</td><td class="Freeze">12:00</td><td class="Freeze">13:00</td><td class="Freeze">17:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665795260590_d_13_d_1507824000000_d_2017092799_d_19890519001_symbol" value="P8" size="2" title="08:00-17:00&nbsp;[12:00&nbsp;13:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092799','1507824000000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092799','1507824000000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092799','1507824000000')">[New]</a></center></td><td><center></center></td><td><select name="504404665795260590_d_13_d_1507824000000_d_2017092799_d_19890519001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665795260590_d_13_d_1507824000000_d_2017092799_d_19890519001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665795260590_d_13_d_1507824000000_d_2017092799_d_19890519001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665795260590_d_13_d_1507824000000_d_2017092799_d_19890519001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665795260590_d_13_d_1507824000000_d_2017092799_d_19890519001_oVerIdx" value="-" type="text"></td><td><select name="504404665795260590_d_13_d_1507824000000_d_2017092799_d_19890519001_status" onkeydown="javascript:fnTrapKD()" id="504404665795260590_d_13_d_1507824000000_d_2017092799_d_19890519001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665795260590_d_13_d_1507824000000_d_2017092799_d_19890519001_reason" onkeydown="javascript:fnTrapKD()" id="504404665795260590_d_13_d_1507824000000_d_2017092799_d_19890519001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665795260590_d_13_d_1507824000000_d_2017092799_d_19890519001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665795260590_d_13_d_1507824000000_d_2017092799_d_19890519001" value="504404665795260590_d_13_d_1507824000000_d_2017092799_d_19890519001" type="checkbox"><input size="4" name="504404665795260590_d_13_d_1507824000000_d_2017092799_d_19890519001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">65</td><td class="Freeze">Fri,<br>13/10/2017&nbsp;</td><td class="Freeze">19930325001</td><td class="Freeze">Dita Ayuningtyas Fitriani</td><td class="Freeze">08:00</td><td class="Freeze">12:00</td><td class="Freeze">13:00</td><td class="Freeze">17:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665865735931_d_13_d_1507824000000_d_2017092798_d_19930325001_symbol" value="P8" size="2" title="08:00-17:00&nbsp;[12:00&nbsp;13:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092798','1507824000000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092798','1507824000000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092798','1507824000000')">[New]</a></center></td><td><center></center></td><td><select name="504404665865735931_d_13_d_1507824000000_d_2017092798_d_19930325001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665865735931_d_13_d_1507824000000_d_2017092798_d_19930325001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665865735931_d_13_d_1507824000000_d_2017092798_d_19930325001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665865735931_d_13_d_1507824000000_d_2017092798_d_19930325001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665865735931_d_13_d_1507824000000_d_2017092798_d_19930325001_oVerIdx" value="-" type="text"></td><td><select name="504404665865735931_d_13_d_1507824000000_d_2017092798_d_19930325001_status" onkeydown="javascript:fnTrapKD()" id="504404665865735931_d_13_d_1507824000000_d_2017092798_d_19930325001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665865735931_d_13_d_1507824000000_d_2017092798_d_19930325001_reason" onkeydown="javascript:fnTrapKD()" id="504404665865735931_d_13_d_1507824000000_d_2017092798_d_19930325001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665865735931_d_13_d_1507824000000_d_2017092798_d_19930325001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665865735931_d_13_d_1507824000000_d_2017092798_d_19930325001" value="504404665865735931_d_13_d_1507824000000_d_2017092798_d_19930325001" type="checkbox"><input size="4" name="504404665865735931_d_13_d_1507824000000_d_2017092798_d_19930325001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">66</td><td class="Freeze"><font color="darkblue">Sat</font>,<br><font color="darkblue">14/10/2017</font>&nbsp;</td><td class="Freeze">19750922001</td><td class="Freeze">Dewa Gede Darmika</td><td class="Freeze">09:00</td><td class="Freeze">13:00</td><td class="Freeze">14:00</td><td class="Freeze">18:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665865736250_d_14_d_1507910400000_d_2017092800_d_19750922001_symbol" value="P9" size="2" title="09:00-18:00&nbsp;[13:00&nbsp;14:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092800','1507910400000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092800','1507910400000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092800','1507910400000')">[New]</a></center></td><td><center></center></td><td><select name="504404665865736250_d_14_d_1507910400000_d_2017092800_d_19750922001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665865736250_d_14_d_1507910400000_d_2017092800_d_19750922001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665865736250_d_14_d_1507910400000_d_2017092800_d_19750922001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665865736250_d_14_d_1507910400000_d_2017092800_d_19750922001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665865736250_d_14_d_1507910400000_d_2017092800_d_19750922001_oVerIdx" value="-" type="text"></td><td><select name="504404665865736250_d_14_d_1507910400000_d_2017092800_d_19750922001_status" onkeydown="javascript:fnTrapKD()" id="504404665865736250_d_14_d_1507910400000_d_2017092800_d_19750922001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665865736250_d_14_d_1507910400000_d_2017092800_d_19750922001_reason" onkeydown="javascript:fnTrapKD()" id="504404665865736250_d_14_d_1507910400000_d_2017092800_d_19750922001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665865736250_d_14_d_1507910400000_d_2017092800_d_19750922001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665865736250_d_14_d_1507910400000_d_2017092800_d_19750922001" value="504404665865736250_d_14_d_1507910400000_d_2017092800_d_19750922001" type="checkbox"><input size="4" name="504404665865736250_d_14_d_1507910400000_d_2017092800_d_19750922001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">67</td><td class="Freeze"><font color="darkblue">Sat</font>,<br><font color="darkblue">14/10/2017</font>&nbsp;</td><td class="Freeze">19820202001</td><td class="Freeze">Arif Eko Cahyono</td><td class="Freeze">09:00</td><td class="Freeze">13:00</td><td class="Freeze">14:00</td><td class="Freeze">18:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665865736287_d_14_d_1507910400000_d_2017092802_d_19820202001_symbol" value="P9" size="2" title="09:00-18:00&nbsp;[13:00&nbsp;14:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092802','1507910400000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092802','1507910400000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092802','1507910400000')">[New]</a></center></td><td><center></center></td><td><select name="504404665865736287_d_14_d_1507910400000_d_2017092802_d_19820202001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665865736287_d_14_d_1507910400000_d_2017092802_d_19820202001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665865736287_d_14_d_1507910400000_d_2017092802_d_19820202001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665865736287_d_14_d_1507910400000_d_2017092802_d_19820202001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665865736287_d_14_d_1507910400000_d_2017092802_d_19820202001_oVerIdx" value="-" type="text"></td><td><select name="504404665865736287_d_14_d_1507910400000_d_2017092802_d_19820202001_status" onkeydown="javascript:fnTrapKD()" id="504404665865736287_d_14_d_1507910400000_d_2017092802_d_19820202001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665865736287_d_14_d_1507910400000_d_2017092802_d_19820202001_reason" onkeydown="javascript:fnTrapKD()" id="504404665865736287_d_14_d_1507910400000_d_2017092802_d_19820202001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665865736287_d_14_d_1507910400000_d_2017092802_d_19820202001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665865736287_d_14_d_1507910400000_d_2017092802_d_19820202001" value="504404665865736287_d_14_d_1507910400000_d_2017092802_d_19820202001" type="checkbox"><input size="4" name="504404665865736287_d_14_d_1507910400000_d_2017092802_d_19820202001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">68</td><td class="Freeze"><font color="darkblue">Sat</font>,<br><font color="darkblue">14/10/2017</font>&nbsp;</td><td class="Freeze">19870603001</td><td class="Freeze">Moses Sangga Mila</td><td class="Freeze">08:00</td><td class="Freeze">12:00</td><td class="Freeze">13:00</td><td class="Freeze">17:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665865736276_d_14_d_1507910400000_d_2017092801_d_19870603001_symbol" value="P8" size="2" title="08:00-17:00&nbsp;[12:00&nbsp;13:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092801','1507910400000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092801','1507910400000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092801','1507910400000')">[New]</a></center></td><td><center></center></td><td><select name="504404665865736276_d_14_d_1507910400000_d_2017092801_d_19870603001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665865736276_d_14_d_1507910400000_d_2017092801_d_19870603001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665865736276_d_14_d_1507910400000_d_2017092801_d_19870603001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665865736276_d_14_d_1507910400000_d_2017092801_d_19870603001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665865736276_d_14_d_1507910400000_d_2017092801_d_19870603001_oVerIdx" value="-" type="text"></td><td><select name="504404665865736276_d_14_d_1507910400000_d_2017092801_d_19870603001_status" onkeydown="javascript:fnTrapKD()" id="504404665865736276_d_14_d_1507910400000_d_2017092801_d_19870603001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665865736276_d_14_d_1507910400000_d_2017092801_d_19870603001_reason" onkeydown="javascript:fnTrapKD()" id="504404665865736276_d_14_d_1507910400000_d_2017092801_d_19870603001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665865736276_d_14_d_1507910400000_d_2017092801_d_19870603001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665865736276_d_14_d_1507910400000_d_2017092801_d_19870603001" value="504404665865736276_d_14_d_1507910400000_d_2017092801_d_19870603001" type="checkbox"><input size="4" name="504404665865736276_d_14_d_1507910400000_d_2017092801_d_19870603001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">69</td><td class="Freeze"><font color="darkblue">Sat</font>,<br><font color="darkblue">14/10/2017</font>&nbsp;</td><td class="Freeze">19890519001</td><td class="Freeze">Khoirul Huda</td><td class="Freeze">08:00</td><td class="Freeze">12:00</td><td class="Freeze">13:00</td><td class="Freeze">17:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665795260590_d_14_d_1507910400000_d_2017092799_d_19890519001_symbol" value="P8" size="2" title="08:00-17:00&nbsp;[12:00&nbsp;13:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092799','1507910400000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092799','1507910400000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092799','1507910400000')">[New]</a></center></td><td><center></center></td><td><select name="504404665795260590_d_14_d_1507910400000_d_2017092799_d_19890519001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665795260590_d_14_d_1507910400000_d_2017092799_d_19890519001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665795260590_d_14_d_1507910400000_d_2017092799_d_19890519001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665795260590_d_14_d_1507910400000_d_2017092799_d_19890519001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665795260590_d_14_d_1507910400000_d_2017092799_d_19890519001_oVerIdx" value="-" type="text"></td><td><select name="504404665795260590_d_14_d_1507910400000_d_2017092799_d_19890519001_status" onkeydown="javascript:fnTrapKD()" id="504404665795260590_d_14_d_1507910400000_d_2017092799_d_19890519001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665795260590_d_14_d_1507910400000_d_2017092799_d_19890519001_reason" onkeydown="javascript:fnTrapKD()" id="504404665795260590_d_14_d_1507910400000_d_2017092799_d_19890519001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665795260590_d_14_d_1507910400000_d_2017092799_d_19890519001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665795260590_d_14_d_1507910400000_d_2017092799_d_19890519001" value="504404665795260590_d_14_d_1507910400000_d_2017092799_d_19890519001" type="checkbox"><input size="4" name="504404665795260590_d_14_d_1507910400000_d_2017092799_d_19890519001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">70</td><td class="Freeze"><font color="darkblue">Sat</font>,<br><font color="darkblue">14/10/2017</font>&nbsp;</td><td class="Freeze">19930325001</td><td class="Freeze">Dita Ayuningtyas Fitriani</td><td class="Freeze">08:00</td><td class="Freeze">12:00</td><td class="Freeze">13:00</td><td class="Freeze">17:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665865735931_d_14_d_1507910400000_d_2017092798_d_19930325001_symbol" value="P8" size="2" title="08:00-17:00&nbsp;[12:00&nbsp;13:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092798','1507910400000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092798','1507910400000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092798','1507910400000')">[New]</a></center></td><td><center></center></td><td><select name="504404665865735931_d_14_d_1507910400000_d_2017092798_d_19930325001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665865735931_d_14_d_1507910400000_d_2017092798_d_19930325001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665865735931_d_14_d_1507910400000_d_2017092798_d_19930325001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665865735931_d_14_d_1507910400000_d_2017092798_d_19930325001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665865735931_d_14_d_1507910400000_d_2017092798_d_19930325001_oVerIdx" value="-" type="text"></td><td><select name="504404665865735931_d_14_d_1507910400000_d_2017092798_d_19930325001_status" onkeydown="javascript:fnTrapKD()" id="504404665865735931_d_14_d_1507910400000_d_2017092798_d_19930325001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665865735931_d_14_d_1507910400000_d_2017092798_d_19930325001_reason" onkeydown="javascript:fnTrapKD()" id="504404665865735931_d_14_d_1507910400000_d_2017092798_d_19930325001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665865735931_d_14_d_1507910400000_d_2017092798_d_19930325001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665865735931_d_14_d_1507910400000_d_2017092798_d_19930325001" value="504404665865735931_d_14_d_1507910400000_d_2017092798_d_19930325001" type="checkbox"><input size="4" name="504404665865735931_d_14_d_1507910400000_d_2017092798_d_19930325001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">71</td><td class="Freeze"><font color="red">Sun</font>,<br><font color="red">15/10/2017</font>&nbsp;</td><td class="Freeze">19750922001</td><td class="Freeze">Dewa Gede Darmika</td><td class="Freeze">09:00</td><td class="Freeze">13:00</td><td class="Freeze">14:00</td><td class="Freeze">18:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665865736250_d_15_d_1507996800000_d_2017092800_d_19750922001_symbol" value="P9" size="2" title="09:00-18:00&nbsp;[13:00&nbsp;14:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092800','1507996800000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092800','1507996800000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092800','1507996800000')">[New]</a></center></td><td><center></center></td><td><select name="504404665865736250_d_15_d_1507996800000_d_2017092800_d_19750922001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665865736250_d_15_d_1507996800000_d_2017092800_d_19750922001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665865736250_d_15_d_1507996800000_d_2017092800_d_19750922001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665865736250_d_15_d_1507996800000_d_2017092800_d_19750922001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665865736250_d_15_d_1507996800000_d_2017092800_d_19750922001_oVerIdx" value="-" type="text"></td><td><select name="504404665865736250_d_15_d_1507996800000_d_2017092800_d_19750922001_status" onkeydown="javascript:fnTrapKD()" id="504404665865736250_d_15_d_1507996800000_d_2017092800_d_19750922001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665865736250_d_15_d_1507996800000_d_2017092800_d_19750922001_reason" onkeydown="javascript:fnTrapKD()" id="504404665865736250_d_15_d_1507996800000_d_2017092800_d_19750922001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665865736250_d_15_d_1507996800000_d_2017092800_d_19750922001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665865736250_d_15_d_1507996800000_d_2017092800_d_19750922001" value="504404665865736250_d_15_d_1507996800000_d_2017092800_d_19750922001" type="checkbox"><input size="4" name="504404665865736250_d_15_d_1507996800000_d_2017092800_d_19750922001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">72</td><td class="Freeze"><font color="red">Sun</font>,<br><font color="red">15/10/2017</font>&nbsp;</td><td class="Freeze">19820202001</td><td class="Freeze">Arif Eko Cahyono</td><td class="Freeze">09:00</td><td class="Freeze">13:00</td><td class="Freeze">14:00</td><td class="Freeze">18:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665865736287_d_15_d_1507996800000_d_2017092802_d_19820202001_symbol" value="P9" size="2" title="09:00-18:00&nbsp;[13:00&nbsp;14:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092802','1507996800000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092802','1507996800000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092802','1507996800000')">[New]</a></center></td><td><center></center></td><td><select name="504404665865736287_d_15_d_1507996800000_d_2017092802_d_19820202001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665865736287_d_15_d_1507996800000_d_2017092802_d_19820202001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665865736287_d_15_d_1507996800000_d_2017092802_d_19820202001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665865736287_d_15_d_1507996800000_d_2017092802_d_19820202001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665865736287_d_15_d_1507996800000_d_2017092802_d_19820202001_oVerIdx" value="-" type="text"></td><td><select name="504404665865736287_d_15_d_1507996800000_d_2017092802_d_19820202001_status" onkeydown="javascript:fnTrapKD()" id="504404665865736287_d_15_d_1507996800000_d_2017092802_d_19820202001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665865736287_d_15_d_1507996800000_d_2017092802_d_19820202001_reason" onkeydown="javascript:fnTrapKD()" id="504404665865736287_d_15_d_1507996800000_d_2017092802_d_19820202001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665865736287_d_15_d_1507996800000_d_2017092802_d_19820202001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665865736287_d_15_d_1507996800000_d_2017092802_d_19820202001" value="504404665865736287_d_15_d_1507996800000_d_2017092802_d_19820202001" type="checkbox"><input size="4" name="504404665865736287_d_15_d_1507996800000_d_2017092802_d_19820202001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">73</td><td class="Freeze"><font color="red">Sun</font>,<br><font color="red">15/10/2017</font>&nbsp;</td><td class="Freeze">19870603001</td><td class="Freeze">Moses Sangga Mila</td><td class="Freeze">08:00</td><td class="Freeze">12:00</td><td class="Freeze">13:00</td><td class="Freeze">17:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665865736276_d_15_d_1507996800000_d_2017092801_d_19870603001_symbol" value="P8" size="2" title="08:00-17:00&nbsp;[12:00&nbsp;13:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092801','1507996800000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092801','1507996800000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092801','1507996800000')">[New]</a></center></td><td><center></center></td><td><select name="504404665865736276_d_15_d_1507996800000_d_2017092801_d_19870603001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665865736276_d_15_d_1507996800000_d_2017092801_d_19870603001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665865736276_d_15_d_1507996800000_d_2017092801_d_19870603001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665865736276_d_15_d_1507996800000_d_2017092801_d_19870603001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665865736276_d_15_d_1507996800000_d_2017092801_d_19870603001_oVerIdx" value="-" type="text"></td><td><select name="504404665865736276_d_15_d_1507996800000_d_2017092801_d_19870603001_status" onkeydown="javascript:fnTrapKD()" id="504404665865736276_d_15_d_1507996800000_d_2017092801_d_19870603001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665865736276_d_15_d_1507996800000_d_2017092801_d_19870603001_reason" onkeydown="javascript:fnTrapKD()" id="504404665865736276_d_15_d_1507996800000_d_2017092801_d_19870603001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665865736276_d_15_d_1507996800000_d_2017092801_d_19870603001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665865736276_d_15_d_1507996800000_d_2017092801_d_19870603001" value="504404665865736276_d_15_d_1507996800000_d_2017092801_d_19870603001" type="checkbox"><input size="4" name="504404665865736276_d_15_d_1507996800000_d_2017092801_d_19870603001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">74</td><td class="Freeze"><font color="red">Sun</font>,<br><font color="red">15/10/2017</font>&nbsp;</td><td class="Freeze">19890519001</td><td class="Freeze">Khoirul Huda</td><td class="Freeze">08:00</td><td class="Freeze">12:00</td><td class="Freeze">13:00</td><td class="Freeze">17:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665795260590_d_15_d_1507996800000_d_2017092799_d_19890519001_symbol" value="P8" size="2" title="08:00-17:00&nbsp;[12:00&nbsp;13:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092799','1507996800000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092799','1507996800000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092799','1507996800000')">[New]</a></center></td><td><center></center></td><td><select name="504404665795260590_d_15_d_1507996800000_d_2017092799_d_19890519001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665795260590_d_15_d_1507996800000_d_2017092799_d_19890519001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665795260590_d_15_d_1507996800000_d_2017092799_d_19890519001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665795260590_d_15_d_1507996800000_d_2017092799_d_19890519001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665795260590_d_15_d_1507996800000_d_2017092799_d_19890519001_oVerIdx" value="-" type="text"></td><td><select name="504404665795260590_d_15_d_1507996800000_d_2017092799_d_19890519001_status" onkeydown="javascript:fnTrapKD()" id="504404665795260590_d_15_d_1507996800000_d_2017092799_d_19890519001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665795260590_d_15_d_1507996800000_d_2017092799_d_19890519001_reason" onkeydown="javascript:fnTrapKD()" id="504404665795260590_d_15_d_1507996800000_d_2017092799_d_19890519001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665795260590_d_15_d_1507996800000_d_2017092799_d_19890519001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665795260590_d_15_d_1507996800000_d_2017092799_d_19890519001" value="504404665795260590_d_15_d_1507996800000_d_2017092799_d_19890519001" type="checkbox"><input size="4" name="504404665795260590_d_15_d_1507996800000_d_2017092799_d_19890519001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">75</td><td class="Freeze"><font color="red">Sun</font>,<br><font color="red">15/10/2017</font>&nbsp;</td><td class="Freeze">19930325001</td><td class="Freeze">Dita Ayuningtyas Fitriani</td><td class="Freeze">08:00</td><td class="Freeze">12:00</td><td class="Freeze">13:00</td><td class="Freeze">17:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665865735931_d_15_d_1507996800000_d_2017092798_d_19930325001_symbol" value="P8" size="2" title="08:00-17:00&nbsp;[12:00&nbsp;13:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092798','1507996800000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092798','1507996800000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092798','1507996800000')">[New]</a></center></td><td><center></center></td><td><select name="504404665865735931_d_15_d_1507996800000_d_2017092798_d_19930325001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665865735931_d_15_d_1507996800000_d_2017092798_d_19930325001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665865735931_d_15_d_1507996800000_d_2017092798_d_19930325001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665865735931_d_15_d_1507996800000_d_2017092798_d_19930325001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665865735931_d_15_d_1507996800000_d_2017092798_d_19930325001_oVerIdx" value="-" type="text"></td><td><select name="504404665865735931_d_15_d_1507996800000_d_2017092798_d_19930325001_status" onkeydown="javascript:fnTrapKD()" id="504404665865735931_d_15_d_1507996800000_d_2017092798_d_19930325001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665865735931_d_15_d_1507996800000_d_2017092798_d_19930325001_reason" onkeydown="javascript:fnTrapKD()" id="504404665865735931_d_15_d_1507996800000_d_2017092798_d_19930325001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665865735931_d_15_d_1507996800000_d_2017092798_d_19930325001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665865735931_d_15_d_1507996800000_d_2017092798_d_19930325001" value="504404665865735931_d_15_d_1507996800000_d_2017092798_d_19930325001" type="checkbox"><input size="4" name="504404665865735931_d_15_d_1507996800000_d_2017092798_d_19930325001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">76</td><td class="Freeze">Mon,<br>16/10/2017&nbsp;</td><td class="Freeze">19750922001</td><td class="Freeze">Dewa Gede Darmika</td><td class="Freeze">09:00</td><td class="Freeze">13:00</td><td class="Freeze">14:00</td><td class="Freeze">18:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665865736250_d_16_d_1508083200000_d_2017092800_d_19750922001_symbol" value="P9" size="2" title="09:00-18:00&nbsp;[13:00&nbsp;14:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092800','1508083200000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092800','1508083200000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092800','1508083200000')">[New]</a></center></td><td><center></center></td><td><select name="504404665865736250_d_16_d_1508083200000_d_2017092800_d_19750922001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665865736250_d_16_d_1508083200000_d_2017092800_d_19750922001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665865736250_d_16_d_1508083200000_d_2017092800_d_19750922001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665865736250_d_16_d_1508083200000_d_2017092800_d_19750922001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665865736250_d_16_d_1508083200000_d_2017092800_d_19750922001_oVerIdx" value="-" type="text"></td><td><select name="504404665865736250_d_16_d_1508083200000_d_2017092800_d_19750922001_status" onkeydown="javascript:fnTrapKD()" id="504404665865736250_d_16_d_1508083200000_d_2017092800_d_19750922001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665865736250_d_16_d_1508083200000_d_2017092800_d_19750922001_reason" onkeydown="javascript:fnTrapKD()" id="504404665865736250_d_16_d_1508083200000_d_2017092800_d_19750922001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665865736250_d_16_d_1508083200000_d_2017092800_d_19750922001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665865736250_d_16_d_1508083200000_d_2017092800_d_19750922001" value="504404665865736250_d_16_d_1508083200000_d_2017092800_d_19750922001" type="checkbox"><input size="4" name="504404665865736250_d_16_d_1508083200000_d_2017092800_d_19750922001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">77</td><td class="Freeze">Mon,<br>16/10/2017&nbsp;</td><td class="Freeze">19820202001</td><td class="Freeze">Arif Eko Cahyono</td><td class="Freeze">09:00</td><td class="Freeze">13:00</td><td class="Freeze">14:00</td><td class="Freeze">18:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665865736287_d_16_d_1508083200000_d_2017092802_d_19820202001_symbol" value="P9" size="2" title="09:00-18:00&nbsp;[13:00&nbsp;14:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092802','1508083200000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092802','1508083200000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092802','1508083200000')">[New]</a></center></td><td><center></center></td><td><select name="504404665865736287_d_16_d_1508083200000_d_2017092802_d_19820202001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665865736287_d_16_d_1508083200000_d_2017092802_d_19820202001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665865736287_d_16_d_1508083200000_d_2017092802_d_19820202001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665865736287_d_16_d_1508083200000_d_2017092802_d_19820202001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665865736287_d_16_d_1508083200000_d_2017092802_d_19820202001_oVerIdx" value="-" type="text"></td><td><select name="504404665865736287_d_16_d_1508083200000_d_2017092802_d_19820202001_status" onkeydown="javascript:fnTrapKD()" id="504404665865736287_d_16_d_1508083200000_d_2017092802_d_19820202001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665865736287_d_16_d_1508083200000_d_2017092802_d_19820202001_reason" onkeydown="javascript:fnTrapKD()" id="504404665865736287_d_16_d_1508083200000_d_2017092802_d_19820202001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665865736287_d_16_d_1508083200000_d_2017092802_d_19820202001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665865736287_d_16_d_1508083200000_d_2017092802_d_19820202001" value="504404665865736287_d_16_d_1508083200000_d_2017092802_d_19820202001" type="checkbox"><input size="4" name="504404665865736287_d_16_d_1508083200000_d_2017092802_d_19820202001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">78</td><td class="Freeze">Mon,<br>16/10/2017&nbsp;</td><td class="Freeze">19870603001</td><td class="Freeze">Moses Sangga Mila</td><td class="Freeze">08:00</td><td class="Freeze">12:00</td><td class="Freeze">13:00</td><td class="Freeze">17:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665865736276_d_16_d_1508083200000_d_2017092801_d_19870603001_symbol" value="P8" size="2" title="08:00-17:00&nbsp;[12:00&nbsp;13:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092801','1508083200000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092801','1508083200000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092801','1508083200000')">[New]</a></center></td><td><center></center></td><td><select name="504404665865736276_d_16_d_1508083200000_d_2017092801_d_19870603001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665865736276_d_16_d_1508083200000_d_2017092801_d_19870603001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665865736276_d_16_d_1508083200000_d_2017092801_d_19870603001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665865736276_d_16_d_1508083200000_d_2017092801_d_19870603001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665865736276_d_16_d_1508083200000_d_2017092801_d_19870603001_oVerIdx" value="-" type="text"></td><td><select name="504404665865736276_d_16_d_1508083200000_d_2017092801_d_19870603001_status" onkeydown="javascript:fnTrapKD()" id="504404665865736276_d_16_d_1508083200000_d_2017092801_d_19870603001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665865736276_d_16_d_1508083200000_d_2017092801_d_19870603001_reason" onkeydown="javascript:fnTrapKD()" id="504404665865736276_d_16_d_1508083200000_d_2017092801_d_19870603001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665865736276_d_16_d_1508083200000_d_2017092801_d_19870603001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665865736276_d_16_d_1508083200000_d_2017092801_d_19870603001" value="504404665865736276_d_16_d_1508083200000_d_2017092801_d_19870603001" type="checkbox"><input size="4" name="504404665865736276_d_16_d_1508083200000_d_2017092801_d_19870603001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">79</td><td class="Freeze">Mon,<br>16/10/2017&nbsp;</td><td class="Freeze">19890519001</td><td class="Freeze">Khoirul Huda</td><td class="Freeze">08:00</td><td class="Freeze">12:00</td><td class="Freeze">13:00</td><td class="Freeze">17:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665795260590_d_16_d_1508083200000_d_2017092799_d_19890519001_symbol" value="P8" size="2" title="08:00-17:00&nbsp;[12:00&nbsp;13:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092799','1508083200000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092799','1508083200000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092799','1508083200000')">[New]</a></center></td><td><center></center></td><td><select name="504404665795260590_d_16_d_1508083200000_d_2017092799_d_19890519001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665795260590_d_16_d_1508083200000_d_2017092799_d_19890519001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665795260590_d_16_d_1508083200000_d_2017092799_d_19890519001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665795260590_d_16_d_1508083200000_d_2017092799_d_19890519001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665795260590_d_16_d_1508083200000_d_2017092799_d_19890519001_oVerIdx" value="-" type="text"></td><td><select name="504404665795260590_d_16_d_1508083200000_d_2017092799_d_19890519001_status" onkeydown="javascript:fnTrapKD()" id="504404665795260590_d_16_d_1508083200000_d_2017092799_d_19890519001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665795260590_d_16_d_1508083200000_d_2017092799_d_19890519001_reason" onkeydown="javascript:fnTrapKD()" id="504404665795260590_d_16_d_1508083200000_d_2017092799_d_19890519001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665795260590_d_16_d_1508083200000_d_2017092799_d_19890519001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665795260590_d_16_d_1508083200000_d_2017092799_d_19890519001" value="504404665795260590_d_16_d_1508083200000_d_2017092799_d_19890519001" type="checkbox"><input size="4" name="504404665795260590_d_16_d_1508083200000_d_2017092799_d_19890519001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">80</td><td class="Freeze">Mon,<br>16/10/2017&nbsp;</td><td class="Freeze">19930325001</td><td class="Freeze">Dita Ayuningtyas Fitriani</td><td class="Freeze">08:00</td><td class="Freeze">12:00</td><td class="Freeze">13:00</td><td class="Freeze">17:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665865735931_d_16_d_1508083200000_d_2017092798_d_19930325001_symbol" value="P8" size="2" title="08:00-17:00&nbsp;[12:00&nbsp;13:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092798','1508083200000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092798','1508083200000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092798','1508083200000')">[New]</a></center></td><td><center></center></td><td><select name="504404665865735931_d_16_d_1508083200000_d_2017092798_d_19930325001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665865735931_d_16_d_1508083200000_d_2017092798_d_19930325001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665865735931_d_16_d_1508083200000_d_2017092798_d_19930325001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665865735931_d_16_d_1508083200000_d_2017092798_d_19930325001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665865735931_d_16_d_1508083200000_d_2017092798_d_19930325001_oVerIdx" value="-" type="text"></td><td><select name="504404665865735931_d_16_d_1508083200000_d_2017092798_d_19930325001_status" onkeydown="javascript:fnTrapKD()" id="504404665865735931_d_16_d_1508083200000_d_2017092798_d_19930325001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665865735931_d_16_d_1508083200000_d_2017092798_d_19930325001_reason" onkeydown="javascript:fnTrapKD()" id="504404665865735931_d_16_d_1508083200000_d_2017092798_d_19930325001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665865735931_d_16_d_1508083200000_d_2017092798_d_19930325001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665865735931_d_16_d_1508083200000_d_2017092798_d_19930325001" value="504404665865735931_d_16_d_1508083200000_d_2017092798_d_19930325001" type="checkbox"><input size="4" name="504404665865735931_d_16_d_1508083200000_d_2017092798_d_19930325001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">81</td><td class="Freeze">Tue,<br>17/10/2017&nbsp;</td><td class="Freeze">19750922001</td><td class="Freeze">Dewa Gede Darmika</td><td class="Freeze">09:00</td><td class="Freeze">13:00</td><td class="Freeze">14:00</td><td class="Freeze">18:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665865736250_d_17_d_1508169600000_d_2017092800_d_19750922001_symbol" value="P9" size="2" title="09:00-18:00&nbsp;[13:00&nbsp;14:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092800','1508169600000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092800','1508169600000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092800','1508169600000')">[New]</a></center></td><td><center></center></td><td><select name="504404665865736250_d_17_d_1508169600000_d_2017092800_d_19750922001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665865736250_d_17_d_1508169600000_d_2017092800_d_19750922001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665865736250_d_17_d_1508169600000_d_2017092800_d_19750922001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665865736250_d_17_d_1508169600000_d_2017092800_d_19750922001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665865736250_d_17_d_1508169600000_d_2017092800_d_19750922001_oVerIdx" value="-" type="text"></td><td><select name="504404665865736250_d_17_d_1508169600000_d_2017092800_d_19750922001_status" onkeydown="javascript:fnTrapKD()" id="504404665865736250_d_17_d_1508169600000_d_2017092800_d_19750922001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665865736250_d_17_d_1508169600000_d_2017092800_d_19750922001_reason" onkeydown="javascript:fnTrapKD()" id="504404665865736250_d_17_d_1508169600000_d_2017092800_d_19750922001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665865736250_d_17_d_1508169600000_d_2017092800_d_19750922001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665865736250_d_17_d_1508169600000_d_2017092800_d_19750922001" value="504404665865736250_d_17_d_1508169600000_d_2017092800_d_19750922001" type="checkbox"><input size="4" name="504404665865736250_d_17_d_1508169600000_d_2017092800_d_19750922001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">82</td><td class="Freeze">Tue,<br>17/10/2017&nbsp;</td><td class="Freeze">19820202001</td><td class="Freeze">Arif Eko Cahyono</td><td class="Freeze">09:00</td><td class="Freeze">13:00</td><td class="Freeze">14:00</td><td class="Freeze">18:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665865736287_d_17_d_1508169600000_d_2017092802_d_19820202001_symbol" value="P9" size="2" title="09:00-18:00&nbsp;[13:00&nbsp;14:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092802','1508169600000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092802','1508169600000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092802','1508169600000')">[New]</a></center></td><td><center></center></td><td><select name="504404665865736287_d_17_d_1508169600000_d_2017092802_d_19820202001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665865736287_d_17_d_1508169600000_d_2017092802_d_19820202001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665865736287_d_17_d_1508169600000_d_2017092802_d_19820202001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665865736287_d_17_d_1508169600000_d_2017092802_d_19820202001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665865736287_d_17_d_1508169600000_d_2017092802_d_19820202001_oVerIdx" value="-" type="text"></td><td><select name="504404665865736287_d_17_d_1508169600000_d_2017092802_d_19820202001_status" onkeydown="javascript:fnTrapKD()" id="504404665865736287_d_17_d_1508169600000_d_2017092802_d_19820202001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665865736287_d_17_d_1508169600000_d_2017092802_d_19820202001_reason" onkeydown="javascript:fnTrapKD()" id="504404665865736287_d_17_d_1508169600000_d_2017092802_d_19820202001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665865736287_d_17_d_1508169600000_d_2017092802_d_19820202001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665865736287_d_17_d_1508169600000_d_2017092802_d_19820202001" value="504404665865736287_d_17_d_1508169600000_d_2017092802_d_19820202001" type="checkbox"><input size="4" name="504404665865736287_d_17_d_1508169600000_d_2017092802_d_19820202001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">83</td><td class="Freeze">Tue,<br>17/10/2017&nbsp;</td><td class="Freeze">19870603001</td><td class="Freeze">Moses Sangga Mila</td><td class="Freeze">08:00</td><td class="Freeze">12:00</td><td class="Freeze">13:00</td><td class="Freeze">17:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665865736276_d_17_d_1508169600000_d_2017092801_d_19870603001_symbol" value="P8" size="2" title="08:00-17:00&nbsp;[12:00&nbsp;13:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092801','1508169600000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092801','1508169600000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092801','1508169600000')">[New]</a></center></td><td><center></center></td><td><select name="504404665865736276_d_17_d_1508169600000_d_2017092801_d_19870603001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665865736276_d_17_d_1508169600000_d_2017092801_d_19870603001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665865736276_d_17_d_1508169600000_d_2017092801_d_19870603001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665865736276_d_17_d_1508169600000_d_2017092801_d_19870603001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665865736276_d_17_d_1508169600000_d_2017092801_d_19870603001_oVerIdx" value="-" type="text"></td><td><select name="504404665865736276_d_17_d_1508169600000_d_2017092801_d_19870603001_status" onkeydown="javascript:fnTrapKD()" id="504404665865736276_d_17_d_1508169600000_d_2017092801_d_19870603001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665865736276_d_17_d_1508169600000_d_2017092801_d_19870603001_reason" onkeydown="javascript:fnTrapKD()" id="504404665865736276_d_17_d_1508169600000_d_2017092801_d_19870603001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665865736276_d_17_d_1508169600000_d_2017092801_d_19870603001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665865736276_d_17_d_1508169600000_d_2017092801_d_19870603001" value="504404665865736276_d_17_d_1508169600000_d_2017092801_d_19870603001" type="checkbox"><input size="4" name="504404665865736276_d_17_d_1508169600000_d_2017092801_d_19870603001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">84</td><td class="Freeze">Tue,<br>17/10/2017&nbsp;</td><td class="Freeze">19890519001</td><td class="Freeze">Khoirul Huda</td><td class="Freeze">08:00</td><td class="Freeze">12:00</td><td class="Freeze">13:00</td><td class="Freeze">17:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665795260590_d_17_d_1508169600000_d_2017092799_d_19890519001_symbol" value="P8" size="2" title="08:00-17:00&nbsp;[12:00&nbsp;13:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092799','1508169600000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092799','1508169600000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092799','1508169600000')">[New]</a></center></td><td><center></center></td><td><select name="504404665795260590_d_17_d_1508169600000_d_2017092799_d_19890519001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665795260590_d_17_d_1508169600000_d_2017092799_d_19890519001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665795260590_d_17_d_1508169600000_d_2017092799_d_19890519001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665795260590_d_17_d_1508169600000_d_2017092799_d_19890519001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665795260590_d_17_d_1508169600000_d_2017092799_d_19890519001_oVerIdx" value="-" type="text"></td><td><select name="504404665795260590_d_17_d_1508169600000_d_2017092799_d_19890519001_status" onkeydown="javascript:fnTrapKD()" id="504404665795260590_d_17_d_1508169600000_d_2017092799_d_19890519001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665795260590_d_17_d_1508169600000_d_2017092799_d_19890519001_reason" onkeydown="javascript:fnTrapKD()" id="504404665795260590_d_17_d_1508169600000_d_2017092799_d_19890519001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665795260590_d_17_d_1508169600000_d_2017092799_d_19890519001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665795260590_d_17_d_1508169600000_d_2017092799_d_19890519001" value="504404665795260590_d_17_d_1508169600000_d_2017092799_d_19890519001" type="checkbox"><input size="4" name="504404665795260590_d_17_d_1508169600000_d_2017092799_d_19890519001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">85</td><td class="Freeze">Tue,<br>17/10/2017&nbsp;</td><td class="Freeze">19930325001</td><td class="Freeze">Dita Ayuningtyas Fitriani</td><td class="Freeze">08:00</td><td class="Freeze">12:00</td><td class="Freeze">13:00</td><td class="Freeze">17:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665865735931_d_17_d_1508169600000_d_2017092798_d_19930325001_symbol" value="P8" size="2" title="08:00-17:00&nbsp;[12:00&nbsp;13:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092798','1508169600000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092798','1508169600000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092798','1508169600000')">[New]</a></center></td><td><center></center></td><td><select name="504404665865735931_d_17_d_1508169600000_d_2017092798_d_19930325001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665865735931_d_17_d_1508169600000_d_2017092798_d_19930325001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665865735931_d_17_d_1508169600000_d_2017092798_d_19930325001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665865735931_d_17_d_1508169600000_d_2017092798_d_19930325001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665865735931_d_17_d_1508169600000_d_2017092798_d_19930325001_oVerIdx" value="-" type="text"></td><td><select name="504404665865735931_d_17_d_1508169600000_d_2017092798_d_19930325001_status" onkeydown="javascript:fnTrapKD()" id="504404665865735931_d_17_d_1508169600000_d_2017092798_d_19930325001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665865735931_d_17_d_1508169600000_d_2017092798_d_19930325001_reason" onkeydown="javascript:fnTrapKD()" id="504404665865735931_d_17_d_1508169600000_d_2017092798_d_19930325001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665865735931_d_17_d_1508169600000_d_2017092798_d_19930325001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665865735931_d_17_d_1508169600000_d_2017092798_d_19930325001" value="504404665865735931_d_17_d_1508169600000_d_2017092798_d_19930325001" type="checkbox"><input size="4" name="504404665865735931_d_17_d_1508169600000_d_2017092798_d_19930325001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">86</td><td class="Freeze">Wed,<br>18/10/2017&nbsp;</td><td class="Freeze">19750922001</td><td class="Freeze">Dewa Gede Darmika</td><td class="Freeze">09:00</td><td class="Freeze">13:00</td><td class="Freeze">14:00</td><td class="Freeze">18:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665865736250_d_18_d_1508256000000_d_2017092800_d_19750922001_symbol" value="P9" size="2" title="09:00-18:00&nbsp;[13:00&nbsp;14:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092800','1508256000000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092800','1508256000000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092800','1508256000000')">[New]</a></center></td><td><center></center></td><td><select name="504404665865736250_d_18_d_1508256000000_d_2017092800_d_19750922001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665865736250_d_18_d_1508256000000_d_2017092800_d_19750922001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665865736250_d_18_d_1508256000000_d_2017092800_d_19750922001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665865736250_d_18_d_1508256000000_d_2017092800_d_19750922001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665865736250_d_18_d_1508256000000_d_2017092800_d_19750922001_oVerIdx" value="-" type="text"></td><td><select name="504404665865736250_d_18_d_1508256000000_d_2017092800_d_19750922001_status" onkeydown="javascript:fnTrapKD()" id="504404665865736250_d_18_d_1508256000000_d_2017092800_d_19750922001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665865736250_d_18_d_1508256000000_d_2017092800_d_19750922001_reason" onkeydown="javascript:fnTrapKD()" id="504404665865736250_d_18_d_1508256000000_d_2017092800_d_19750922001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665865736250_d_18_d_1508256000000_d_2017092800_d_19750922001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665865736250_d_18_d_1508256000000_d_2017092800_d_19750922001" value="504404665865736250_d_18_d_1508256000000_d_2017092800_d_19750922001" type="checkbox"><input size="4" name="504404665865736250_d_18_d_1508256000000_d_2017092800_d_19750922001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">87</td><td class="Freeze">Wed,<br>18/10/2017&nbsp;</td><td class="Freeze">19820202001</td><td class="Freeze">Arif Eko Cahyono</td><td class="Freeze">09:00</td><td class="Freeze">13:00</td><td class="Freeze">14:00</td><td class="Freeze">18:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665865736287_d_18_d_1508256000000_d_2017092802_d_19820202001_symbol" value="P9" size="2" title="09:00-18:00&nbsp;[13:00&nbsp;14:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092802','1508256000000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092802','1508256000000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092802','1508256000000')">[New]</a></center></td><td><center></center></td><td><select name="504404665865736287_d_18_d_1508256000000_d_2017092802_d_19820202001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665865736287_d_18_d_1508256000000_d_2017092802_d_19820202001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665865736287_d_18_d_1508256000000_d_2017092802_d_19820202001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665865736287_d_18_d_1508256000000_d_2017092802_d_19820202001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665865736287_d_18_d_1508256000000_d_2017092802_d_19820202001_oVerIdx" value="-" type="text"></td><td><select name="504404665865736287_d_18_d_1508256000000_d_2017092802_d_19820202001_status" onkeydown="javascript:fnTrapKD()" id="504404665865736287_d_18_d_1508256000000_d_2017092802_d_19820202001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665865736287_d_18_d_1508256000000_d_2017092802_d_19820202001_reason" onkeydown="javascript:fnTrapKD()" id="504404665865736287_d_18_d_1508256000000_d_2017092802_d_19820202001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665865736287_d_18_d_1508256000000_d_2017092802_d_19820202001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665865736287_d_18_d_1508256000000_d_2017092802_d_19820202001" value="504404665865736287_d_18_d_1508256000000_d_2017092802_d_19820202001" type="checkbox"><input size="4" name="504404665865736287_d_18_d_1508256000000_d_2017092802_d_19820202001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">88</td><td class="Freeze">Wed,<br>18/10/2017&nbsp;</td><td class="Freeze">19870603001</td><td class="Freeze">Moses Sangga Mila</td><td class="Freeze">08:00</td><td class="Freeze">12:00</td><td class="Freeze">13:00</td><td class="Freeze">17:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665865736276_d_18_d_1508256000000_d_2017092801_d_19870603001_symbol" value="P8" size="2" title="08:00-17:00&nbsp;[12:00&nbsp;13:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092801','1508256000000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092801','1508256000000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092801','1508256000000')">[New]</a></center></td><td><center></center></td><td><select name="504404665865736276_d_18_d_1508256000000_d_2017092801_d_19870603001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665865736276_d_18_d_1508256000000_d_2017092801_d_19870603001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665865736276_d_18_d_1508256000000_d_2017092801_d_19870603001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665865736276_d_18_d_1508256000000_d_2017092801_d_19870603001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665865736276_d_18_d_1508256000000_d_2017092801_d_19870603001_oVerIdx" value="-" type="text"></td><td><select name="504404665865736276_d_18_d_1508256000000_d_2017092801_d_19870603001_status" onkeydown="javascript:fnTrapKD()" id="504404665865736276_d_18_d_1508256000000_d_2017092801_d_19870603001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665865736276_d_18_d_1508256000000_d_2017092801_d_19870603001_reason" onkeydown="javascript:fnTrapKD()" id="504404665865736276_d_18_d_1508256000000_d_2017092801_d_19870603001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665865736276_d_18_d_1508256000000_d_2017092801_d_19870603001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665865736276_d_18_d_1508256000000_d_2017092801_d_19870603001" value="504404665865736276_d_18_d_1508256000000_d_2017092801_d_19870603001" type="checkbox"><input size="4" name="504404665865736276_d_18_d_1508256000000_d_2017092801_d_19870603001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">89</td><td class="Freeze">Wed,<br>18/10/2017&nbsp;</td><td class="Freeze">19890519001</td><td class="Freeze">Khoirul Huda</td><td class="Freeze">08:00</td><td class="Freeze">12:00</td><td class="Freeze">13:00</td><td class="Freeze">17:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665795260590_d_18_d_1508256000000_d_2017092799_d_19890519001_symbol" value="P8" size="2" title="08:00-17:00&nbsp;[12:00&nbsp;13:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092799','1508256000000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092799','1508256000000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092799','1508256000000')">[New]</a></center></td><td><center></center></td><td><select name="504404665795260590_d_18_d_1508256000000_d_2017092799_d_19890519001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665795260590_d_18_d_1508256000000_d_2017092799_d_19890519001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665795260590_d_18_d_1508256000000_d_2017092799_d_19890519001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665795260590_d_18_d_1508256000000_d_2017092799_d_19890519001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665795260590_d_18_d_1508256000000_d_2017092799_d_19890519001_oVerIdx" value="-" type="text"></td><td><select name="504404665795260590_d_18_d_1508256000000_d_2017092799_d_19890519001_status" onkeydown="javascript:fnTrapKD()" id="504404665795260590_d_18_d_1508256000000_d_2017092799_d_19890519001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665795260590_d_18_d_1508256000000_d_2017092799_d_19890519001_reason" onkeydown="javascript:fnTrapKD()" id="504404665795260590_d_18_d_1508256000000_d_2017092799_d_19890519001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665795260590_d_18_d_1508256000000_d_2017092799_d_19890519001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665795260590_d_18_d_1508256000000_d_2017092799_d_19890519001" value="504404665795260590_d_18_d_1508256000000_d_2017092799_d_19890519001" type="checkbox"><input size="4" name="504404665795260590_d_18_d_1508256000000_d_2017092799_d_19890519001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">90</td><td class="Freeze">Wed,<br>18/10/2017&nbsp;</td><td class="Freeze">19930325001</td><td class="Freeze">Dita Ayuningtyas Fitriani</td><td class="Freeze">08:00</td><td class="Freeze">12:00</td><td class="Freeze">13:00</td><td class="Freeze">17:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665865735931_d_18_d_1508256000000_d_2017092798_d_19930325001_symbol" value="P8" size="2" title="08:00-17:00&nbsp;[12:00&nbsp;13:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092798','1508256000000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092798','1508256000000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092798','1508256000000')">[New]</a></center></td><td><center></center></td><td><select name="504404665865735931_d_18_d_1508256000000_d_2017092798_d_19930325001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665865735931_d_18_d_1508256000000_d_2017092798_d_19930325001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665865735931_d_18_d_1508256000000_d_2017092798_d_19930325001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665865735931_d_18_d_1508256000000_d_2017092798_d_19930325001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665865735931_d_18_d_1508256000000_d_2017092798_d_19930325001_oVerIdx" value="-" type="text"></td><td><select name="504404665865735931_d_18_d_1508256000000_d_2017092798_d_19930325001_status" onkeydown="javascript:fnTrapKD()" id="504404665865735931_d_18_d_1508256000000_d_2017092798_d_19930325001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665865735931_d_18_d_1508256000000_d_2017092798_d_19930325001_reason" onkeydown="javascript:fnTrapKD()" id="504404665865735931_d_18_d_1508256000000_d_2017092798_d_19930325001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665865735931_d_18_d_1508256000000_d_2017092798_d_19930325001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665865735931_d_18_d_1508256000000_d_2017092798_d_19930325001" value="504404665865735931_d_18_d_1508256000000_d_2017092798_d_19930325001" type="checkbox"><input size="4" name="504404665865735931_d_18_d_1508256000000_d_2017092798_d_19930325001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">91</td><td class="Freeze">Thu,<br>19/10/2017&nbsp;</td><td class="Freeze">19750922001</td><td class="Freeze">Dewa Gede Darmika</td><td class="Freeze">09:00</td><td class="Freeze">13:00</td><td class="Freeze">14:00</td><td class="Freeze">18:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665865736250_d_19_d_1508342400000_d_2017092800_d_19750922001_symbol" value="P9" size="2" title="09:00-18:00&nbsp;[13:00&nbsp;14:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092800','1508342400000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092800','1508342400000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092800','1508342400000')">[New]</a></center></td><td><center></center></td><td><select name="504404665865736250_d_19_d_1508342400000_d_2017092800_d_19750922001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665865736250_d_19_d_1508342400000_d_2017092800_d_19750922001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665865736250_d_19_d_1508342400000_d_2017092800_d_19750922001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665865736250_d_19_d_1508342400000_d_2017092800_d_19750922001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665865736250_d_19_d_1508342400000_d_2017092800_d_19750922001_oVerIdx" value="-" type="text"></td><td><select name="504404665865736250_d_19_d_1508342400000_d_2017092800_d_19750922001_status" onkeydown="javascript:fnTrapKD()" id="504404665865736250_d_19_d_1508342400000_d_2017092800_d_19750922001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665865736250_d_19_d_1508342400000_d_2017092800_d_19750922001_reason" onkeydown="javascript:fnTrapKD()" id="504404665865736250_d_19_d_1508342400000_d_2017092800_d_19750922001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665865736250_d_19_d_1508342400000_d_2017092800_d_19750922001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665865736250_d_19_d_1508342400000_d_2017092800_d_19750922001" value="504404665865736250_d_19_d_1508342400000_d_2017092800_d_19750922001" type="checkbox"><input size="4" name="504404665865736250_d_19_d_1508342400000_d_2017092800_d_19750922001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">92</td><td class="Freeze">Thu,<br>19/10/2017&nbsp;</td><td class="Freeze">19820202001</td><td class="Freeze">Arif Eko Cahyono</td><td class="Freeze">09:00</td><td class="Freeze">13:00</td><td class="Freeze">14:00</td><td class="Freeze">18:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665865736287_d_19_d_1508342400000_d_2017092802_d_19820202001_symbol" value="P9" size="2" title="09:00-18:00&nbsp;[13:00&nbsp;14:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092802','1508342400000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092802','1508342400000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092802','1508342400000')">[New]</a></center></td><td><center></center></td><td><select name="504404665865736287_d_19_d_1508342400000_d_2017092802_d_19820202001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665865736287_d_19_d_1508342400000_d_2017092802_d_19820202001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665865736287_d_19_d_1508342400000_d_2017092802_d_19820202001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665865736287_d_19_d_1508342400000_d_2017092802_d_19820202001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665865736287_d_19_d_1508342400000_d_2017092802_d_19820202001_oVerIdx" value="-" type="text"></td><td><select name="504404665865736287_d_19_d_1508342400000_d_2017092802_d_19820202001_status" onkeydown="javascript:fnTrapKD()" id="504404665865736287_d_19_d_1508342400000_d_2017092802_d_19820202001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665865736287_d_19_d_1508342400000_d_2017092802_d_19820202001_reason" onkeydown="javascript:fnTrapKD()" id="504404665865736287_d_19_d_1508342400000_d_2017092802_d_19820202001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665865736287_d_19_d_1508342400000_d_2017092802_d_19820202001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665865736287_d_19_d_1508342400000_d_2017092802_d_19820202001" value="504404665865736287_d_19_d_1508342400000_d_2017092802_d_19820202001" type="checkbox"><input size="4" name="504404665865736287_d_19_d_1508342400000_d_2017092802_d_19820202001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">93</td><td class="Freeze">Thu,<br>19/10/2017&nbsp;</td><td class="Freeze">19870603001</td><td class="Freeze">Moses Sangga Mila</td><td class="Freeze">08:00</td><td class="Freeze">12:00</td><td class="Freeze">13:00</td><td class="Freeze">17:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665865736276_d_19_d_1508342400000_d_2017092801_d_19870603001_symbol" value="P8" size="2" title="08:00-17:00&nbsp;[12:00&nbsp;13:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092801','1508342400000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092801','1508342400000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092801','1508342400000')">[New]</a></center></td><td><center></center></td><td><select name="504404665865736276_d_19_d_1508342400000_d_2017092801_d_19870603001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665865736276_d_19_d_1508342400000_d_2017092801_d_19870603001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665865736276_d_19_d_1508342400000_d_2017092801_d_19870603001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665865736276_d_19_d_1508342400000_d_2017092801_d_19870603001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665865736276_d_19_d_1508342400000_d_2017092801_d_19870603001_oVerIdx" value="-" type="text"></td><td><select name="504404665865736276_d_19_d_1508342400000_d_2017092801_d_19870603001_status" onkeydown="javascript:fnTrapKD()" id="504404665865736276_d_19_d_1508342400000_d_2017092801_d_19870603001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665865736276_d_19_d_1508342400000_d_2017092801_d_19870603001_reason" onkeydown="javascript:fnTrapKD()" id="504404665865736276_d_19_d_1508342400000_d_2017092801_d_19870603001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665865736276_d_19_d_1508342400000_d_2017092801_d_19870603001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665865736276_d_19_d_1508342400000_d_2017092801_d_19870603001" value="504404665865736276_d_19_d_1508342400000_d_2017092801_d_19870603001" type="checkbox"><input size="4" name="504404665865736276_d_19_d_1508342400000_d_2017092801_d_19870603001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">94</td><td class="Freeze">Thu,<br>19/10/2017&nbsp;</td><td class="Freeze">19890519001</td><td class="Freeze">Khoirul Huda</td><td class="Freeze">08:00</td><td class="Freeze">12:00</td><td class="Freeze">13:00</td><td class="Freeze">17:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665795260590_d_19_d_1508342400000_d_2017092799_d_19890519001_symbol" value="P8" size="2" title="08:00-17:00&nbsp;[12:00&nbsp;13:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092799','1508342400000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092799','1508342400000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092799','1508342400000')">[New]</a></center></td><td><center></center></td><td><select name="504404665795260590_d_19_d_1508342400000_d_2017092799_d_19890519001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665795260590_d_19_d_1508342400000_d_2017092799_d_19890519001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665795260590_d_19_d_1508342400000_d_2017092799_d_19890519001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665795260590_d_19_d_1508342400000_d_2017092799_d_19890519001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665795260590_d_19_d_1508342400000_d_2017092799_d_19890519001_oVerIdx" value="-" type="text"></td><td><select name="504404665795260590_d_19_d_1508342400000_d_2017092799_d_19890519001_status" onkeydown="javascript:fnTrapKD()" id="504404665795260590_d_19_d_1508342400000_d_2017092799_d_19890519001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665795260590_d_19_d_1508342400000_d_2017092799_d_19890519001_reason" onkeydown="javascript:fnTrapKD()" id="504404665795260590_d_19_d_1508342400000_d_2017092799_d_19890519001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665795260590_d_19_d_1508342400000_d_2017092799_d_19890519001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665795260590_d_19_d_1508342400000_d_2017092799_d_19890519001" value="504404665795260590_d_19_d_1508342400000_d_2017092799_d_19890519001" type="checkbox"><input size="4" name="504404665795260590_d_19_d_1508342400000_d_2017092799_d_19890519001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">95</td><td class="Freeze">Thu,<br>19/10/2017&nbsp;</td><td class="Freeze">19930325001</td><td class="Freeze">Dita Ayuningtyas Fitriani</td><td class="Freeze">08:00</td><td class="Freeze">12:00</td><td class="Freeze">13:00</td><td class="Freeze">17:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665865735931_d_19_d_1508342400000_d_2017092798_d_19930325001_symbol" value="P8" size="2" title="08:00-17:00&nbsp;[12:00&nbsp;13:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092798','1508342400000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092798','1508342400000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092798','1508342400000')">[New]</a></center></td><td><center></center></td><td><select name="504404665865735931_d_19_d_1508342400000_d_2017092798_d_19930325001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665865735931_d_19_d_1508342400000_d_2017092798_d_19930325001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665865735931_d_19_d_1508342400000_d_2017092798_d_19930325001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665865735931_d_19_d_1508342400000_d_2017092798_d_19930325001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665865735931_d_19_d_1508342400000_d_2017092798_d_19930325001_oVerIdx" value="-" type="text"></td><td><select name="504404665865735931_d_19_d_1508342400000_d_2017092798_d_19930325001_status" onkeydown="javascript:fnTrapKD()" id="504404665865735931_d_19_d_1508342400000_d_2017092798_d_19930325001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665865735931_d_19_d_1508342400000_d_2017092798_d_19930325001_reason" onkeydown="javascript:fnTrapKD()" id="504404665865735931_d_19_d_1508342400000_d_2017092798_d_19930325001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665865735931_d_19_d_1508342400000_d_2017092798_d_19930325001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665865735931_d_19_d_1508342400000_d_2017092798_d_19930325001" value="504404665865735931_d_19_d_1508342400000_d_2017092798_d_19930325001" type="checkbox"><input size="4" name="504404665865735931_d_19_d_1508342400000_d_2017092798_d_19930325001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">96</td><td class="Freeze">Fri,<br>20/10/2017&nbsp;</td><td class="Freeze">19750922001</td><td class="Freeze">Dewa Gede Darmika</td><td class="Freeze">09:00</td><td class="Freeze">13:00</td><td class="Freeze">14:00</td><td class="Freeze">18:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665865736250_d_20_d_1508428800000_d_2017092800_d_19750922001_symbol" value="P9" size="2" title="09:00-18:00&nbsp;[13:00&nbsp;14:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092800','1508428800000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092800','1508428800000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092800','1508428800000')">[New]</a></center></td><td><center></center></td><td><select name="504404665865736250_d_20_d_1508428800000_d_2017092800_d_19750922001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665865736250_d_20_d_1508428800000_d_2017092800_d_19750922001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665865736250_d_20_d_1508428800000_d_2017092800_d_19750922001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665865736250_d_20_d_1508428800000_d_2017092800_d_19750922001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665865736250_d_20_d_1508428800000_d_2017092800_d_19750922001_oVerIdx" value="-" type="text"></td><td><select name="504404665865736250_d_20_d_1508428800000_d_2017092800_d_19750922001_status" onkeydown="javascript:fnTrapKD()" id="504404665865736250_d_20_d_1508428800000_d_2017092800_d_19750922001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665865736250_d_20_d_1508428800000_d_2017092800_d_19750922001_reason" onkeydown="javascript:fnTrapKD()" id="504404665865736250_d_20_d_1508428800000_d_2017092800_d_19750922001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665865736250_d_20_d_1508428800000_d_2017092800_d_19750922001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665865736250_d_20_d_1508428800000_d_2017092800_d_19750922001" value="504404665865736250_d_20_d_1508428800000_d_2017092800_d_19750922001" type="checkbox"><input size="4" name="504404665865736250_d_20_d_1508428800000_d_2017092800_d_19750922001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">97</td><td class="Freeze">Fri,<br>20/10/2017&nbsp;</td><td class="Freeze">19820202001</td><td class="Freeze">Arif Eko Cahyono</td><td class="Freeze">09:00</td><td class="Freeze">13:00</td><td class="Freeze">14:00</td><td class="Freeze">18:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665865736287_d_20_d_1508428800000_d_2017092802_d_19820202001_symbol" value="P9" size="2" title="09:00-18:00&nbsp;[13:00&nbsp;14:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092802','1508428800000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092802','1508428800000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092802','1508428800000')">[New]</a></center></td><td><center></center></td><td><select name="504404665865736287_d_20_d_1508428800000_d_2017092802_d_19820202001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665865736287_d_20_d_1508428800000_d_2017092802_d_19820202001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665865736287_d_20_d_1508428800000_d_2017092802_d_19820202001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665865736287_d_20_d_1508428800000_d_2017092802_d_19820202001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665865736287_d_20_d_1508428800000_d_2017092802_d_19820202001_oVerIdx" value="-" type="text"></td><td><select name="504404665865736287_d_20_d_1508428800000_d_2017092802_d_19820202001_status" onkeydown="javascript:fnTrapKD()" id="504404665865736287_d_20_d_1508428800000_d_2017092802_d_19820202001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665865736287_d_20_d_1508428800000_d_2017092802_d_19820202001_reason" onkeydown="javascript:fnTrapKD()" id="504404665865736287_d_20_d_1508428800000_d_2017092802_d_19820202001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665865736287_d_20_d_1508428800000_d_2017092802_d_19820202001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665865736287_d_20_d_1508428800000_d_2017092802_d_19820202001" value="504404665865736287_d_20_d_1508428800000_d_2017092802_d_19820202001" type="checkbox"><input size="4" name="504404665865736287_d_20_d_1508428800000_d_2017092802_d_19820202001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">98</td><td class="Freeze">Fri,<br>20/10/2017&nbsp;</td><td class="Freeze">19870603001</td><td class="Freeze">Moses Sangga Mila</td><td class="Freeze">08:00</td><td class="Freeze">12:00</td><td class="Freeze">13:00</td><td class="Freeze">17:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665865736276_d_20_d_1508428800000_d_2017092801_d_19870603001_symbol" value="P8" size="2" title="08:00-17:00&nbsp;[12:00&nbsp;13:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092801','1508428800000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092801','1508428800000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092801','1508428800000')">[New]</a></center></td><td><center></center></td><td><select name="504404665865736276_d_20_d_1508428800000_d_2017092801_d_19870603001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665865736276_d_20_d_1508428800000_d_2017092801_d_19870603001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665865736276_d_20_d_1508428800000_d_2017092801_d_19870603001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665865736276_d_20_d_1508428800000_d_2017092801_d_19870603001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665865736276_d_20_d_1508428800000_d_2017092801_d_19870603001_oVerIdx" value="-" type="text"></td><td><select name="504404665865736276_d_20_d_1508428800000_d_2017092801_d_19870603001_status" onkeydown="javascript:fnTrapKD()" id="504404665865736276_d_20_d_1508428800000_d_2017092801_d_19870603001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665865736276_d_20_d_1508428800000_d_2017092801_d_19870603001_reason" onkeydown="javascript:fnTrapKD()" id="504404665865736276_d_20_d_1508428800000_d_2017092801_d_19870603001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665865736276_d_20_d_1508428800000_d_2017092801_d_19870603001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665865736276_d_20_d_1508428800000_d_2017092801_d_19870603001" value="504404665865736276_d_20_d_1508428800000_d_2017092801_d_19870603001" type="checkbox"><input size="4" name="504404665865736276_d_20_d_1508428800000_d_2017092801_d_19870603001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">99</td><td class="Freeze">Fri,<br>20/10/2017&nbsp;</td><td class="Freeze">19890519001</td><td class="Freeze">Khoirul Huda</td><td class="Freeze">08:00</td><td class="Freeze">12:00</td><td class="Freeze">13:00</td><td class="Freeze">17:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665795260590_d_20_d_1508428800000_d_2017092799_d_19890519001_symbol" value="P8" size="2" title="08:00-17:00&nbsp;[12:00&nbsp;13:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092799','1508428800000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092799','1508428800000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092799','1508428800000')">[New]</a></center></td><td><center></center></td><td><select name="504404665795260590_d_20_d_1508428800000_d_2017092799_d_19890519001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665795260590_d_20_d_1508428800000_d_2017092799_d_19890519001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665795260590_d_20_d_1508428800000_d_2017092799_d_19890519001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665795260590_d_20_d_1508428800000_d_2017092799_d_19890519001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665795260590_d_20_d_1508428800000_d_2017092799_d_19890519001_oVerIdx" value="-" type="text"></td><td><select name="504404665795260590_d_20_d_1508428800000_d_2017092799_d_19890519001_status" onkeydown="javascript:fnTrapKD()" id="504404665795260590_d_20_d_1508428800000_d_2017092799_d_19890519001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665795260590_d_20_d_1508428800000_d_2017092799_d_19890519001_reason" onkeydown="javascript:fnTrapKD()" id="504404665795260590_d_20_d_1508428800000_d_2017092799_d_19890519001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665795260590_d_20_d_1508428800000_d_2017092799_d_19890519001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665795260590_d_20_d_1508428800000_d_2017092799_d_19890519001" value="504404665795260590_d_20_d_1508428800000_d_2017092799_d_19890519001" type="checkbox"><input size="4" name="504404665795260590_d_20_d_1508428800000_d_2017092799_d_19890519001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">100</td><td class="Freeze">Fri,<br>20/10/2017&nbsp;</td><td class="Freeze">19930325001</td><td class="Freeze">Dita Ayuningtyas Fitriani</td><td class="Freeze">08:00</td><td class="Freeze">12:00</td><td class="Freeze">13:00</td><td class="Freeze">17:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665865735931_d_20_d_1508428800000_d_2017092798_d_19930325001_symbol" value="P8" size="2" title="08:00-17:00&nbsp;[12:00&nbsp;13:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092798','1508428800000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092798','1508428800000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092798','1508428800000')">[New]</a></center></td><td><center></center></td><td><select name="504404665865735931_d_20_d_1508428800000_d_2017092798_d_19930325001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665865735931_d_20_d_1508428800000_d_2017092798_d_19930325001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665865735931_d_20_d_1508428800000_d_2017092798_d_19930325001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665865735931_d_20_d_1508428800000_d_2017092798_d_19930325001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665865735931_d_20_d_1508428800000_d_2017092798_d_19930325001_oVerIdx" value="-" type="text"></td><td><select name="504404665865735931_d_20_d_1508428800000_d_2017092798_d_19930325001_status" onkeydown="javascript:fnTrapKD()" id="504404665865735931_d_20_d_1508428800000_d_2017092798_d_19930325001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665865735931_d_20_d_1508428800000_d_2017092798_d_19930325001_reason" onkeydown="javascript:fnTrapKD()" id="504404665865735931_d_20_d_1508428800000_d_2017092798_d_19930325001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665865735931_d_20_d_1508428800000_d_2017092798_d_19930325001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665865735931_d_20_d_1508428800000_d_2017092798_d_19930325001" value="504404665865735931_d_20_d_1508428800000_d_2017092798_d_19930325001" type="checkbox"><input size="4" name="504404665865735931_d_20_d_1508428800000_d_2017092798_d_19930325001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">101</td><td class="Freeze"><font color="darkblue">Sat</font>,<br><font color="darkblue">21/10/2017</font>&nbsp;</td><td class="Freeze">19750922001</td><td class="Freeze">Dewa Gede Darmika</td><td class="Freeze">09:00</td><td class="Freeze">13:00</td><td class="Freeze">14:00</td><td class="Freeze">18:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665865736250_d_21_d_1508515200000_d_2017092800_d_19750922001_symbol" value="P9" size="2" title="09:00-18:00&nbsp;[13:00&nbsp;14:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092800','1508515200000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092800','1508515200000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092800','1508515200000')">[New]</a></center></td><td><center></center></td><td><select name="504404665865736250_d_21_d_1508515200000_d_2017092800_d_19750922001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665865736250_d_21_d_1508515200000_d_2017092800_d_19750922001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665865736250_d_21_d_1508515200000_d_2017092800_d_19750922001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665865736250_d_21_d_1508515200000_d_2017092800_d_19750922001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665865736250_d_21_d_1508515200000_d_2017092800_d_19750922001_oVerIdx" value="-" type="text"></td><td><select name="504404665865736250_d_21_d_1508515200000_d_2017092800_d_19750922001_status" onkeydown="javascript:fnTrapKD()" id="504404665865736250_d_21_d_1508515200000_d_2017092800_d_19750922001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665865736250_d_21_d_1508515200000_d_2017092800_d_19750922001_reason" onkeydown="javascript:fnTrapKD()" id="504404665865736250_d_21_d_1508515200000_d_2017092800_d_19750922001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665865736250_d_21_d_1508515200000_d_2017092800_d_19750922001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665865736250_d_21_d_1508515200000_d_2017092800_d_19750922001" value="504404665865736250_d_21_d_1508515200000_d_2017092800_d_19750922001" type="checkbox"><input size="4" name="504404665865736250_d_21_d_1508515200000_d_2017092800_d_19750922001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">102</td><td class="Freeze"><font color="darkblue">Sat</font>,<br><font color="darkblue">21/10/2017</font>&nbsp;</td><td class="Freeze">19820202001</td><td class="Freeze">Arif Eko Cahyono</td><td class="Freeze">09:00</td><td class="Freeze">13:00</td><td class="Freeze">14:00</td><td class="Freeze">18:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665865736287_d_21_d_1508515200000_d_2017092802_d_19820202001_symbol" value="P9" size="2" title="09:00-18:00&nbsp;[13:00&nbsp;14:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092802','1508515200000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092802','1508515200000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092802','1508515200000')">[New]</a></center></td><td><center></center></td><td><select name="504404665865736287_d_21_d_1508515200000_d_2017092802_d_19820202001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665865736287_d_21_d_1508515200000_d_2017092802_d_19820202001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665865736287_d_21_d_1508515200000_d_2017092802_d_19820202001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665865736287_d_21_d_1508515200000_d_2017092802_d_19820202001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665865736287_d_21_d_1508515200000_d_2017092802_d_19820202001_oVerIdx" value="-" type="text"></td><td><select name="504404665865736287_d_21_d_1508515200000_d_2017092802_d_19820202001_status" onkeydown="javascript:fnTrapKD()" id="504404665865736287_d_21_d_1508515200000_d_2017092802_d_19820202001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665865736287_d_21_d_1508515200000_d_2017092802_d_19820202001_reason" onkeydown="javascript:fnTrapKD()" id="504404665865736287_d_21_d_1508515200000_d_2017092802_d_19820202001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665865736287_d_21_d_1508515200000_d_2017092802_d_19820202001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665865736287_d_21_d_1508515200000_d_2017092802_d_19820202001" value="504404665865736287_d_21_d_1508515200000_d_2017092802_d_19820202001" type="checkbox"><input size="4" name="504404665865736287_d_21_d_1508515200000_d_2017092802_d_19820202001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">103</td><td class="Freeze"><font color="darkblue">Sat</font>,<br><font color="darkblue">21/10/2017</font>&nbsp;</td><td class="Freeze">19870603001</td><td class="Freeze">Moses Sangga Mila</td><td class="Freeze">08:00</td><td class="Freeze">12:00</td><td class="Freeze">13:00</td><td class="Freeze">17:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665865736276_d_21_d_1508515200000_d_2017092801_d_19870603001_symbol" value="P8" size="2" title="08:00-17:00&nbsp;[12:00&nbsp;13:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092801','1508515200000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092801','1508515200000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092801','1508515200000')">[New]</a></center></td><td><center></center></td><td><select name="504404665865736276_d_21_d_1508515200000_d_2017092801_d_19870603001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665865736276_d_21_d_1508515200000_d_2017092801_d_19870603001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665865736276_d_21_d_1508515200000_d_2017092801_d_19870603001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665865736276_d_21_d_1508515200000_d_2017092801_d_19870603001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665865736276_d_21_d_1508515200000_d_2017092801_d_19870603001_oVerIdx" value="-" type="text"></td><td><select name="504404665865736276_d_21_d_1508515200000_d_2017092801_d_19870603001_status" onkeydown="javascript:fnTrapKD()" id="504404665865736276_d_21_d_1508515200000_d_2017092801_d_19870603001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665865736276_d_21_d_1508515200000_d_2017092801_d_19870603001_reason" onkeydown="javascript:fnTrapKD()" id="504404665865736276_d_21_d_1508515200000_d_2017092801_d_19870603001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665865736276_d_21_d_1508515200000_d_2017092801_d_19870603001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665865736276_d_21_d_1508515200000_d_2017092801_d_19870603001" value="504404665865736276_d_21_d_1508515200000_d_2017092801_d_19870603001" type="checkbox"><input size="4" name="504404665865736276_d_21_d_1508515200000_d_2017092801_d_19870603001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">104</td><td class="Freeze"><font color="darkblue">Sat</font>,<br><font color="darkblue">21/10/2017</font>&nbsp;</td><td class="Freeze">19890519001</td><td class="Freeze">Khoirul Huda</td><td class="Freeze">08:00</td><td class="Freeze">12:00</td><td class="Freeze">13:00</td><td class="Freeze">17:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665795260590_d_21_d_1508515200000_d_2017092799_d_19890519001_symbol" value="P8" size="2" title="08:00-17:00&nbsp;[12:00&nbsp;13:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092799','1508515200000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092799','1508515200000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092799','1508515200000')">[New]</a></center></td><td><center></center></td><td><select name="504404665795260590_d_21_d_1508515200000_d_2017092799_d_19890519001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665795260590_d_21_d_1508515200000_d_2017092799_d_19890519001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665795260590_d_21_d_1508515200000_d_2017092799_d_19890519001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665795260590_d_21_d_1508515200000_d_2017092799_d_19890519001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665795260590_d_21_d_1508515200000_d_2017092799_d_19890519001_oVerIdx" value="-" type="text"></td><td><select name="504404665795260590_d_21_d_1508515200000_d_2017092799_d_19890519001_status" onkeydown="javascript:fnTrapKD()" id="504404665795260590_d_21_d_1508515200000_d_2017092799_d_19890519001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665795260590_d_21_d_1508515200000_d_2017092799_d_19890519001_reason" onkeydown="javascript:fnTrapKD()" id="504404665795260590_d_21_d_1508515200000_d_2017092799_d_19890519001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665795260590_d_21_d_1508515200000_d_2017092799_d_19890519001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665795260590_d_21_d_1508515200000_d_2017092799_d_19890519001" value="504404665795260590_d_21_d_1508515200000_d_2017092799_d_19890519001" type="checkbox"><input size="4" name="504404665795260590_d_21_d_1508515200000_d_2017092799_d_19890519001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">105</td><td class="Freeze"><font color="darkblue">Sat</font>,<br><font color="darkblue">21/10/2017</font>&nbsp;</td><td class="Freeze">19930325001</td><td class="Freeze">Dita Ayuningtyas Fitriani</td><td class="Freeze">08:00</td><td class="Freeze">12:00</td><td class="Freeze">13:00</td><td class="Freeze">17:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665865735931_d_21_d_1508515200000_d_2017092798_d_19930325001_symbol" value="P8" size="2" title="08:00-17:00&nbsp;[12:00&nbsp;13:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092798','1508515200000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092798','1508515200000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092798','1508515200000')">[New]</a></center></td><td><center></center></td><td><select name="504404665865735931_d_21_d_1508515200000_d_2017092798_d_19930325001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665865735931_d_21_d_1508515200000_d_2017092798_d_19930325001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665865735931_d_21_d_1508515200000_d_2017092798_d_19930325001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665865735931_d_21_d_1508515200000_d_2017092798_d_19930325001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665865735931_d_21_d_1508515200000_d_2017092798_d_19930325001_oVerIdx" value="-" type="text"></td><td><select name="504404665865735931_d_21_d_1508515200000_d_2017092798_d_19930325001_status" onkeydown="javascript:fnTrapKD()" id="504404665865735931_d_21_d_1508515200000_d_2017092798_d_19930325001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665865735931_d_21_d_1508515200000_d_2017092798_d_19930325001_reason" onkeydown="javascript:fnTrapKD()" id="504404665865735931_d_21_d_1508515200000_d_2017092798_d_19930325001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665865735931_d_21_d_1508515200000_d_2017092798_d_19930325001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665865735931_d_21_d_1508515200000_d_2017092798_d_19930325001" value="504404665865735931_d_21_d_1508515200000_d_2017092798_d_19930325001" type="checkbox"><input size="4" name="504404665865735931_d_21_d_1508515200000_d_2017092798_d_19930325001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">106</td><td class="Freeze"><font color="red">Sun</font>,<br><font color="red">22/10/2017</font>&nbsp;</td><td class="Freeze">19750922001</td><td class="Freeze">Dewa Gede Darmika</td><td class="Freeze">09:00</td><td class="Freeze">13:00</td><td class="Freeze">14:00</td><td class="Freeze">18:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665865736250_d_22_d_1508601600000_d_2017092800_d_19750922001_symbol" value="P9" size="2" title="09:00-18:00&nbsp;[13:00&nbsp;14:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092800','1508601600000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092800','1508601600000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092800','1508601600000')">[New]</a></center></td><td><center></center></td><td><select name="504404665865736250_d_22_d_1508601600000_d_2017092800_d_19750922001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665865736250_d_22_d_1508601600000_d_2017092800_d_19750922001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665865736250_d_22_d_1508601600000_d_2017092800_d_19750922001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665865736250_d_22_d_1508601600000_d_2017092800_d_19750922001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665865736250_d_22_d_1508601600000_d_2017092800_d_19750922001_oVerIdx" value="-" type="text"></td><td><select name="504404665865736250_d_22_d_1508601600000_d_2017092800_d_19750922001_status" onkeydown="javascript:fnTrapKD()" id="504404665865736250_d_22_d_1508601600000_d_2017092800_d_19750922001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665865736250_d_22_d_1508601600000_d_2017092800_d_19750922001_reason" onkeydown="javascript:fnTrapKD()" id="504404665865736250_d_22_d_1508601600000_d_2017092800_d_19750922001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665865736250_d_22_d_1508601600000_d_2017092800_d_19750922001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665865736250_d_22_d_1508601600000_d_2017092800_d_19750922001" value="504404665865736250_d_22_d_1508601600000_d_2017092800_d_19750922001" type="checkbox"><input size="4" name="504404665865736250_d_22_d_1508601600000_d_2017092800_d_19750922001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">107</td><td class="Freeze"><font color="red">Sun</font>,<br><font color="red">22/10/2017</font>&nbsp;</td><td class="Freeze">19820202001</td><td class="Freeze">Arif Eko Cahyono</td><td class="Freeze">09:00</td><td class="Freeze">13:00</td><td class="Freeze">14:00</td><td class="Freeze">18:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665865736287_d_22_d_1508601600000_d_2017092802_d_19820202001_symbol" value="P9" size="2" title="09:00-18:00&nbsp;[13:00&nbsp;14:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092802','1508601600000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092802','1508601600000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092802','1508601600000')">[New]</a></center></td><td><center></center></td><td><select name="504404665865736287_d_22_d_1508601600000_d_2017092802_d_19820202001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665865736287_d_22_d_1508601600000_d_2017092802_d_19820202001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665865736287_d_22_d_1508601600000_d_2017092802_d_19820202001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665865736287_d_22_d_1508601600000_d_2017092802_d_19820202001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665865736287_d_22_d_1508601600000_d_2017092802_d_19820202001_oVerIdx" value="-" type="text"></td><td><select name="504404665865736287_d_22_d_1508601600000_d_2017092802_d_19820202001_status" onkeydown="javascript:fnTrapKD()" id="504404665865736287_d_22_d_1508601600000_d_2017092802_d_19820202001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665865736287_d_22_d_1508601600000_d_2017092802_d_19820202001_reason" onkeydown="javascript:fnTrapKD()" id="504404665865736287_d_22_d_1508601600000_d_2017092802_d_19820202001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665865736287_d_22_d_1508601600000_d_2017092802_d_19820202001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665865736287_d_22_d_1508601600000_d_2017092802_d_19820202001" value="504404665865736287_d_22_d_1508601600000_d_2017092802_d_19820202001" type="checkbox"><input size="4" name="504404665865736287_d_22_d_1508601600000_d_2017092802_d_19820202001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">108</td><td class="Freeze"><font color="red">Sun</font>,<br><font color="red">22/10/2017</font>&nbsp;</td><td class="Freeze">19870603001</td><td class="Freeze">Moses Sangga Mila</td><td class="Freeze">08:00</td><td class="Freeze">12:00</td><td class="Freeze">13:00</td><td class="Freeze">17:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665865736276_d_22_d_1508601600000_d_2017092801_d_19870603001_symbol" value="P8" size="2" title="08:00-17:00&nbsp;[12:00&nbsp;13:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092801','1508601600000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092801','1508601600000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092801','1508601600000')">[New]</a></center></td><td><center></center></td><td><select name="504404665865736276_d_22_d_1508601600000_d_2017092801_d_19870603001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665865736276_d_22_d_1508601600000_d_2017092801_d_19870603001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665865736276_d_22_d_1508601600000_d_2017092801_d_19870603001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665865736276_d_22_d_1508601600000_d_2017092801_d_19870603001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665865736276_d_22_d_1508601600000_d_2017092801_d_19870603001_oVerIdx" value="-" type="text"></td><td><select name="504404665865736276_d_22_d_1508601600000_d_2017092801_d_19870603001_status" onkeydown="javascript:fnTrapKD()" id="504404665865736276_d_22_d_1508601600000_d_2017092801_d_19870603001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665865736276_d_22_d_1508601600000_d_2017092801_d_19870603001_reason" onkeydown="javascript:fnTrapKD()" id="504404665865736276_d_22_d_1508601600000_d_2017092801_d_19870603001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665865736276_d_22_d_1508601600000_d_2017092801_d_19870603001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665865736276_d_22_d_1508601600000_d_2017092801_d_19870603001" value="504404665865736276_d_22_d_1508601600000_d_2017092801_d_19870603001" type="checkbox"><input size="4" name="504404665865736276_d_22_d_1508601600000_d_2017092801_d_19870603001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">109</td><td class="Freeze"><font color="red">Sun</font>,<br><font color="red">22/10/2017</font>&nbsp;</td><td class="Freeze">19890519001</td><td class="Freeze">Khoirul Huda</td><td class="Freeze">08:00</td><td class="Freeze">12:00</td><td class="Freeze">13:00</td><td class="Freeze">17:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665795260590_d_22_d_1508601600000_d_2017092799_d_19890519001_symbol" value="P8" size="2" title="08:00-17:00&nbsp;[12:00&nbsp;13:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092799','1508601600000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092799','1508601600000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092799','1508601600000')">[New]</a></center></td><td><center></center></td><td><select name="504404665795260590_d_22_d_1508601600000_d_2017092799_d_19890519001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665795260590_d_22_d_1508601600000_d_2017092799_d_19890519001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665795260590_d_22_d_1508601600000_d_2017092799_d_19890519001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665795260590_d_22_d_1508601600000_d_2017092799_d_19890519001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665795260590_d_22_d_1508601600000_d_2017092799_d_19890519001_oVerIdx" value="-" type="text"></td><td><select name="504404665795260590_d_22_d_1508601600000_d_2017092799_d_19890519001_status" onkeydown="javascript:fnTrapKD()" id="504404665795260590_d_22_d_1508601600000_d_2017092799_d_19890519001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665795260590_d_22_d_1508601600000_d_2017092799_d_19890519001_reason" onkeydown="javascript:fnTrapKD()" id="504404665795260590_d_22_d_1508601600000_d_2017092799_d_19890519001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665795260590_d_22_d_1508601600000_d_2017092799_d_19890519001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665795260590_d_22_d_1508601600000_d_2017092799_d_19890519001" value="504404665795260590_d_22_d_1508601600000_d_2017092799_d_19890519001" type="checkbox"><input size="4" name="504404665795260590_d_22_d_1508601600000_d_2017092799_d_19890519001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">110</td><td class="Freeze"><font color="red">Sun</font>,<br><font color="red">22/10/2017</font>&nbsp;</td><td class="Freeze">19930325001</td><td class="Freeze">Dita Ayuningtyas Fitriani</td><td class="Freeze">08:00</td><td class="Freeze">12:00</td><td class="Freeze">13:00</td><td class="Freeze">17:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665865735931_d_22_d_1508601600000_d_2017092798_d_19930325001_symbol" value="P8" size="2" title="08:00-17:00&nbsp;[12:00&nbsp;13:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092798','1508601600000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092798','1508601600000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092798','1508601600000')">[New]</a></center></td><td><center></center></td><td><select name="504404665865735931_d_22_d_1508601600000_d_2017092798_d_19930325001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665865735931_d_22_d_1508601600000_d_2017092798_d_19930325001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665865735931_d_22_d_1508601600000_d_2017092798_d_19930325001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665865735931_d_22_d_1508601600000_d_2017092798_d_19930325001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665865735931_d_22_d_1508601600000_d_2017092798_d_19930325001_oVerIdx" value="-" type="text"></td><td><select name="504404665865735931_d_22_d_1508601600000_d_2017092798_d_19930325001_status" onkeydown="javascript:fnTrapKD()" id="504404665865735931_d_22_d_1508601600000_d_2017092798_d_19930325001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665865735931_d_22_d_1508601600000_d_2017092798_d_19930325001_reason" onkeydown="javascript:fnTrapKD()" id="504404665865735931_d_22_d_1508601600000_d_2017092798_d_19930325001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665865735931_d_22_d_1508601600000_d_2017092798_d_19930325001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665865735931_d_22_d_1508601600000_d_2017092798_d_19930325001" value="504404665865735931_d_22_d_1508601600000_d_2017092798_d_19930325001" type="checkbox"><input size="4" name="504404665865735931_d_22_d_1508601600000_d_2017092798_d_19930325001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">111</td><td class="Freeze"><p class="masterTooltip"><abbr title="Uji Coba PH"><font color="#FF0000">Libur<br>Mon,<br>23/10/2017</font></abbr></p></td><td class="Freeze">19750922001</td><td class="Freeze">Dewa Gede Darmika</td><td class="Freeze">09:00</td><td class="Freeze">13:00</td><td class="Freeze">14:00</td><td class="Freeze">18:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665865736250_d_23_d_1508688000000_d_2017092800_d_19750922001_symbol" value="P9" size="2" title="09:00-18:00&nbsp;[13:00&nbsp;14:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092800','1508688000000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092800','1508688000000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092800','1508688000000')">[New]</a></center></td><td><center></center></td><td><select name="504404665865736250_d_23_d_1508688000000_d_2017092800_d_19750922001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665865736250_d_23_d_1508688000000_d_2017092800_d_19750922001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665865736250_d_23_d_1508688000000_d_2017092800_d_19750922001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665865736250_d_23_d_1508688000000_d_2017092800_d_19750922001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665865736250_d_23_d_1508688000000_d_2017092800_d_19750922001_oVerIdx" value="-" type="text"></td><td><select name="504404665865736250_d_23_d_1508688000000_d_2017092800_d_19750922001_status" onkeydown="javascript:fnTrapKD()" id="504404665865736250_d_23_d_1508688000000_d_2017092800_d_19750922001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665865736250_d_23_d_1508688000000_d_2017092800_d_19750922001_reason" onkeydown="javascript:fnTrapKD()" id="504404665865736250_d_23_d_1508688000000_d_2017092800_d_19750922001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665865736250_d_23_d_1508688000000_d_2017092800_d_19750922001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665865736250_d_23_d_1508688000000_d_2017092800_d_19750922001" value="504404665865736250_d_23_d_1508688000000_d_2017092800_d_19750922001" type="checkbox"><input size="4" name="504404665865736250_d_23_d_1508688000000_d_2017092800_d_19750922001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">112</td><td class="Freeze"><p class="masterTooltip"><abbr title="Uji Coba PH"><font color="#FF0000">Libur<br>Mon,<br>23/10/2017</font></abbr></p></td><td class="Freeze">19820202001</td><td class="Freeze">Arif Eko Cahyono</td><td class="Freeze">09:00</td><td class="Freeze">13:00</td><td class="Freeze">14:00</td><td class="Freeze">18:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665865736287_d_23_d_1508688000000_d_2017092802_d_19820202001_symbol" value="P9" size="2" title="09:00-18:00&nbsp;[13:00&nbsp;14:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092802','1508688000000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092802','1508688000000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092802','1508688000000')">[New]</a></center></td><td><center></center></td><td><select name="504404665865736287_d_23_d_1508688000000_d_2017092802_d_19820202001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665865736287_d_23_d_1508688000000_d_2017092802_d_19820202001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665865736287_d_23_d_1508688000000_d_2017092802_d_19820202001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665865736287_d_23_d_1508688000000_d_2017092802_d_19820202001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665865736287_d_23_d_1508688000000_d_2017092802_d_19820202001_oVerIdx" value="-" type="text"></td><td><select name="504404665865736287_d_23_d_1508688000000_d_2017092802_d_19820202001_status" onkeydown="javascript:fnTrapKD()" id="504404665865736287_d_23_d_1508688000000_d_2017092802_d_19820202001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665865736287_d_23_d_1508688000000_d_2017092802_d_19820202001_reason" onkeydown="javascript:fnTrapKD()" id="504404665865736287_d_23_d_1508688000000_d_2017092802_d_19820202001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665865736287_d_23_d_1508688000000_d_2017092802_d_19820202001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665865736287_d_23_d_1508688000000_d_2017092802_d_19820202001" value="504404665865736287_d_23_d_1508688000000_d_2017092802_d_19820202001" type="checkbox"><input size="4" name="504404665865736287_d_23_d_1508688000000_d_2017092802_d_19820202001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">113</td><td class="Freeze"><p class="masterTooltip"><abbr title="Uji Coba PH"><font color="#FF0000">Libur<br>Mon,<br>23/10/2017</font></abbr></p></td><td class="Freeze">19870603001</td><td class="Freeze">Moses Sangga Mila</td><td class="Freeze">08:00</td><td class="Freeze">12:00</td><td class="Freeze">13:00</td><td class="Freeze">17:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665865736276_d_23_d_1508688000000_d_2017092801_d_19870603001_symbol" value="P8" size="2" title="08:00-17:00&nbsp;[12:00&nbsp;13:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092801','1508688000000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092801','1508688000000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092801','1508688000000')">[New]</a></center></td><td><center></center></td><td><select name="504404665865736276_d_23_d_1508688000000_d_2017092801_d_19870603001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665865736276_d_23_d_1508688000000_d_2017092801_d_19870603001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665865736276_d_23_d_1508688000000_d_2017092801_d_19870603001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665865736276_d_23_d_1508688000000_d_2017092801_d_19870603001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665865736276_d_23_d_1508688000000_d_2017092801_d_19870603001_oVerIdx" value="-" type="text"></td><td><select name="504404665865736276_d_23_d_1508688000000_d_2017092801_d_19870603001_status" onkeydown="javascript:fnTrapKD()" id="504404665865736276_d_23_d_1508688000000_d_2017092801_d_19870603001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665865736276_d_23_d_1508688000000_d_2017092801_d_19870603001_reason" onkeydown="javascript:fnTrapKD()" id="504404665865736276_d_23_d_1508688000000_d_2017092801_d_19870603001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665865736276_d_23_d_1508688000000_d_2017092801_d_19870603001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665865736276_d_23_d_1508688000000_d_2017092801_d_19870603001" value="504404665865736276_d_23_d_1508688000000_d_2017092801_d_19870603001" type="checkbox"><input size="4" name="504404665865736276_d_23_d_1508688000000_d_2017092801_d_19870603001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">114</td><td class="Freeze"><p class="masterTooltip"><abbr title="Uji Coba PH"><font color="#FF0000">Libur<br>Mon,<br>23/10/2017</font></abbr></p></td><td class="Freeze">19890519001</td><td class="Freeze">Khoirul Huda</td><td class="Freeze">00:00</td><td class="Freeze">00:00</td><td class="Freeze">00:00</td><td class="Freeze">00:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665795260590_d_23_d_1508688000000_d_2017092799_d_19890519001_symbol" value="PH" size="2" title="00:00-00:00&nbsp;[00:00&nbsp;00:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092799','1508688000000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092799','1508688000000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092799','1508688000000')">[New]</a></center></td><td><center></center></td><td><select name="504404665795260590_d_23_d_1508688000000_d_2017092799_d_19890519001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665795260590_d_23_d_1508688000000_d_2017092799_d_19890519001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665795260590_d_23_d_1508688000000_d_2017092799_d_19890519001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665795260590_d_23_d_1508688000000_d_2017092799_d_19890519001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665795260590_d_23_d_1508688000000_d_2017092799_d_19890519001_oVerIdx" value="-" type="text"></td><td><select name="504404665795260590_d_23_d_1508688000000_d_2017092799_d_19890519001_status" onkeydown="javascript:fnTrapKD()" id="504404665795260590_d_23_d_1508688000000_d_2017092799_d_19890519001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665795260590_d_23_d_1508688000000_d_2017092799_d_19890519001_reason" onkeydown="javascript:fnTrapKD()" id="504404665795260590_d_23_d_1508688000000_d_2017092799_d_19890519001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665795260590_d_23_d_1508688000000_d_2017092799_d_19890519001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665795260590_d_23_d_1508688000000_d_2017092799_d_19890519001" value="504404665795260590_d_23_d_1508688000000_d_2017092799_d_19890519001" type="checkbox"><input size="4" name="504404665795260590_d_23_d_1508688000000_d_2017092799_d_19890519001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">115</td><td class="Freeze"><p class="masterTooltip"><abbr title="Uji Coba PH"><font color="#FF0000">Libur<br>Mon,<br>23/10/2017</font></abbr></p></td><td class="Freeze">19930325001</td><td class="Freeze">Dita Ayuningtyas Fitriani</td><td class="Freeze">08:00</td><td class="Freeze">12:00</td><td class="Freeze">13:00</td><td class="Freeze">17:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665865735931_d_23_d_1508688000000_d_2017092798_d_19930325001_symbol" value="P8" size="2" title="08:00-17:00&nbsp;[12:00&nbsp;13:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092798','1508688000000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092798','1508688000000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092798','1508688000000')">[New]</a></center></td><td><center></center></td><td><select name="504404665865735931_d_23_d_1508688000000_d_2017092798_d_19930325001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665865735931_d_23_d_1508688000000_d_2017092798_d_19930325001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665865735931_d_23_d_1508688000000_d_2017092798_d_19930325001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665865735931_d_23_d_1508688000000_d_2017092798_d_19930325001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665865735931_d_23_d_1508688000000_d_2017092798_d_19930325001_oVerIdx" value="-" type="text"></td><td><select name="504404665865735931_d_23_d_1508688000000_d_2017092798_d_19930325001_status" onkeydown="javascript:fnTrapKD()" id="504404665865735931_d_23_d_1508688000000_d_2017092798_d_19930325001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665865735931_d_23_d_1508688000000_d_2017092798_d_19930325001_reason" onkeydown="javascript:fnTrapKD()" id="504404665865735931_d_23_d_1508688000000_d_2017092798_d_19930325001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665865735931_d_23_d_1508688000000_d_2017092798_d_19930325001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665865735931_d_23_d_1508688000000_d_2017092798_d_19930325001" value="504404665865735931_d_23_d_1508688000000_d_2017092798_d_19930325001" type="checkbox"><input size="4" name="504404665865735931_d_23_d_1508688000000_d_2017092798_d_19930325001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">116</td><td class="Freeze">Tue,<br>24/10/2017&nbsp;</td><td class="Freeze">19750922001</td><td class="Freeze">Dewa Gede Darmika</td><td class="Freeze">09:00</td><td class="Freeze">13:00</td><td class="Freeze">14:00</td><td class="Freeze">18:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665865736250_d_24_d_1508774400000_d_2017092800_d_19750922001_symbol" value="P9" size="2" title="09:00-18:00&nbsp;[13:00&nbsp;14:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092800','1508774400000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092800','1508774400000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092800','1508774400000')">[New]</a></center></td><td><center></center></td><td><select name="504404665865736250_d_24_d_1508774400000_d_2017092800_d_19750922001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665865736250_d_24_d_1508774400000_d_2017092800_d_19750922001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665865736250_d_24_d_1508774400000_d_2017092800_d_19750922001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665865736250_d_24_d_1508774400000_d_2017092800_d_19750922001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665865736250_d_24_d_1508774400000_d_2017092800_d_19750922001_oVerIdx" value="-" type="text"></td><td><select name="504404665865736250_d_24_d_1508774400000_d_2017092800_d_19750922001_status" onkeydown="javascript:fnTrapKD()" id="504404665865736250_d_24_d_1508774400000_d_2017092800_d_19750922001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665865736250_d_24_d_1508774400000_d_2017092800_d_19750922001_reason" onkeydown="javascript:fnTrapKD()" id="504404665865736250_d_24_d_1508774400000_d_2017092800_d_19750922001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665865736250_d_24_d_1508774400000_d_2017092800_d_19750922001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665865736250_d_24_d_1508774400000_d_2017092800_d_19750922001" value="504404665865736250_d_24_d_1508774400000_d_2017092800_d_19750922001" type="checkbox"><input size="4" name="504404665865736250_d_24_d_1508774400000_d_2017092800_d_19750922001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">117</td><td class="Freeze">Tue,<br>24/10/2017&nbsp;</td><td class="Freeze">19820202001</td><td class="Freeze">Arif Eko Cahyono</td><td class="Freeze">09:00</td><td class="Freeze">13:00</td><td class="Freeze">14:00</td><td class="Freeze">18:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665865736287_d_24_d_1508774400000_d_2017092802_d_19820202001_symbol" value="P9" size="2" title="09:00-18:00&nbsp;[13:00&nbsp;14:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092802','1508774400000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092802','1508774400000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092802','1508774400000')">[New]</a></center></td><td><center></center></td><td><select name="504404665865736287_d_24_d_1508774400000_d_2017092802_d_19820202001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665865736287_d_24_d_1508774400000_d_2017092802_d_19820202001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665865736287_d_24_d_1508774400000_d_2017092802_d_19820202001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665865736287_d_24_d_1508774400000_d_2017092802_d_19820202001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665865736287_d_24_d_1508774400000_d_2017092802_d_19820202001_oVerIdx" value="-" type="text"></td><td><select name="504404665865736287_d_24_d_1508774400000_d_2017092802_d_19820202001_status" onkeydown="javascript:fnTrapKD()" id="504404665865736287_d_24_d_1508774400000_d_2017092802_d_19820202001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665865736287_d_24_d_1508774400000_d_2017092802_d_19820202001_reason" onkeydown="javascript:fnTrapKD()" id="504404665865736287_d_24_d_1508774400000_d_2017092802_d_19820202001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665865736287_d_24_d_1508774400000_d_2017092802_d_19820202001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665865736287_d_24_d_1508774400000_d_2017092802_d_19820202001" value="504404665865736287_d_24_d_1508774400000_d_2017092802_d_19820202001" type="checkbox"><input size="4" name="504404665865736287_d_24_d_1508774400000_d_2017092802_d_19820202001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">118</td><td class="Freeze">Tue,<br>24/10/2017&nbsp;</td><td class="Freeze">19870603001</td><td class="Freeze">Moses Sangga Mila</td><td class="Freeze">08:00</td><td class="Freeze">12:00</td><td class="Freeze">13:00</td><td class="Freeze">17:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665865736276_d_24_d_1508774400000_d_2017092801_d_19870603001_symbol" value="P8" size="2" title="08:00-17:00&nbsp;[12:00&nbsp;13:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092801','1508774400000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092801','1508774400000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092801','1508774400000')">[New]</a></center></td><td><center></center></td><td><select name="504404665865736276_d_24_d_1508774400000_d_2017092801_d_19870603001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665865736276_d_24_d_1508774400000_d_2017092801_d_19870603001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665865736276_d_24_d_1508774400000_d_2017092801_d_19870603001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665865736276_d_24_d_1508774400000_d_2017092801_d_19870603001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665865736276_d_24_d_1508774400000_d_2017092801_d_19870603001_oVerIdx" value="-" type="text"></td><td><select name="504404665865736276_d_24_d_1508774400000_d_2017092801_d_19870603001_status" onkeydown="javascript:fnTrapKD()" id="504404665865736276_d_24_d_1508774400000_d_2017092801_d_19870603001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665865736276_d_24_d_1508774400000_d_2017092801_d_19870603001_reason" onkeydown="javascript:fnTrapKD()" id="504404665865736276_d_24_d_1508774400000_d_2017092801_d_19870603001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665865736276_d_24_d_1508774400000_d_2017092801_d_19870603001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665865736276_d_24_d_1508774400000_d_2017092801_d_19870603001" value="504404665865736276_d_24_d_1508774400000_d_2017092801_d_19870603001" type="checkbox"><input size="4" name="504404665865736276_d_24_d_1508774400000_d_2017092801_d_19870603001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">119</td><td class="Freeze">Tue,<br>24/10/2017&nbsp;</td><td class="Freeze">19890519001</td><td class="Freeze">Khoirul Huda</td><td class="Freeze">08:00</td><td class="Freeze">12:00</td><td class="Freeze">13:00</td><td class="Freeze">17:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665795260590_d_24_d_1508774400000_d_2017092799_d_19890519001_symbol" value="P8" size="2" title="08:00-17:00&nbsp;[12:00&nbsp;13:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092799','1508774400000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092799','1508774400000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092799','1508774400000')">[New]</a></center></td><td><center></center></td><td><select name="504404665795260590_d_24_d_1508774400000_d_2017092799_d_19890519001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665795260590_d_24_d_1508774400000_d_2017092799_d_19890519001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665795260590_d_24_d_1508774400000_d_2017092799_d_19890519001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665795260590_d_24_d_1508774400000_d_2017092799_d_19890519001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665795260590_d_24_d_1508774400000_d_2017092799_d_19890519001_oVerIdx" value="-" type="text"></td><td><select name="504404665795260590_d_24_d_1508774400000_d_2017092799_d_19890519001_status" onkeydown="javascript:fnTrapKD()" id="504404665795260590_d_24_d_1508774400000_d_2017092799_d_19890519001" disabled="" class="elementForm">
	<option value="0">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3" selected="">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665795260590_d_24_d_1508774400000_d_2017092799_d_19890519001_reason" onkeydown="javascript:fnTrapKD()" id="504404665795260590_d_24_d_1508774400000_d_2017092799_d_19890519001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665795260590_d_24_d_1508774400000_d_2017092799_d_19890519001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665795260590_d_24_d_1508774400000_d_2017092799_d_19890519001" value="504404665795260590_d_24_d_1508774400000_d_2017092799_d_19890519001" type="checkbox"><input size="4" name="504404665795260590_d_24_d_1508774400000_d_2017092799_d_19890519001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">120</td><td class="Freeze">Tue,<br>24/10/2017&nbsp;</td><td class="Freeze">19930325001</td><td class="Freeze">Dita Ayuningtyas Fitriani</td><td class="Freeze">08:00</td><td class="Freeze">12:00</td><td class="Freeze">13:00</td><td class="Freeze">17:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665865735931_d_24_d_1508774400000_d_2017092798_d_19930325001_symbol" value="P8" size="2" title="08:00-17:00&nbsp;[12:00&nbsp;13:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092798','1508774400000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092798','1508774400000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092798','1508774400000')">[New]</a></center></td><td><center></center></td><td><select name="504404665865735931_d_24_d_1508774400000_d_2017092798_d_19930325001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665865735931_d_24_d_1508774400000_d_2017092798_d_19930325001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665865735931_d_24_d_1508774400000_d_2017092798_d_19930325001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665865735931_d_24_d_1508774400000_d_2017092798_d_19930325001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665865735931_d_24_d_1508774400000_d_2017092798_d_19930325001_oVerIdx" value="-" type="text"></td><td><select name="504404665865735931_d_24_d_1508774400000_d_2017092798_d_19930325001_status" onkeydown="javascript:fnTrapKD()" id="504404665865735931_d_24_d_1508774400000_d_2017092798_d_19930325001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665865735931_d_24_d_1508774400000_d_2017092798_d_19930325001_reason" onkeydown="javascript:fnTrapKD()" id="504404665865735931_d_24_d_1508774400000_d_2017092798_d_19930325001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665865735931_d_24_d_1508774400000_d_2017092798_d_19930325001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665865735931_d_24_d_1508774400000_d_2017092798_d_19930325001" value="504404665865735931_d_24_d_1508774400000_d_2017092798_d_19930325001" type="checkbox"><input size="4" name="504404665865735931_d_24_d_1508774400000_d_2017092798_d_19930325001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">121</td><td class="Freeze">Wed,<br>25/10/2017&nbsp;</td><td class="Freeze">19750922001</td><td class="Freeze">Dewa Gede Darmika</td><td class="Freeze">09:00</td><td class="Freeze">13:00</td><td class="Freeze">14:00</td><td class="Freeze">18:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665865736250_d_25_d_1508860800000_d_2017092800_d_19750922001_symbol" value="P9" size="2" title="09:00-18:00&nbsp;[13:00&nbsp;14:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092800','1508860800000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092800','1508860800000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092800','1508860800000')">[New]</a></center></td><td><center></center></td><td><select name="504404665865736250_d_25_d_1508860800000_d_2017092800_d_19750922001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665865736250_d_25_d_1508860800000_d_2017092800_d_19750922001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665865736250_d_25_d_1508860800000_d_2017092800_d_19750922001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665865736250_d_25_d_1508860800000_d_2017092800_d_19750922001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665865736250_d_25_d_1508860800000_d_2017092800_d_19750922001_oVerIdx" value="-" type="text"></td><td><select name="504404665865736250_d_25_d_1508860800000_d_2017092800_d_19750922001_status" onkeydown="javascript:fnTrapKD()" id="504404665865736250_d_25_d_1508860800000_d_2017092800_d_19750922001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665865736250_d_25_d_1508860800000_d_2017092800_d_19750922001_reason" onkeydown="javascript:fnTrapKD()" id="504404665865736250_d_25_d_1508860800000_d_2017092800_d_19750922001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665865736250_d_25_d_1508860800000_d_2017092800_d_19750922001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665865736250_d_25_d_1508860800000_d_2017092800_d_19750922001" value="504404665865736250_d_25_d_1508860800000_d_2017092800_d_19750922001" type="checkbox"><input size="4" name="504404665865736250_d_25_d_1508860800000_d_2017092800_d_19750922001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">122</td><td class="Freeze">Wed,<br>25/10/2017&nbsp;</td><td class="Freeze">19820202001</td><td class="Freeze">Arif Eko Cahyono</td><td class="Freeze">09:00</td><td class="Freeze">13:00</td><td class="Freeze">14:00</td><td class="Freeze">18:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665865736287_d_25_d_1508860800000_d_2017092802_d_19820202001_symbol" value="P9" size="2" title="09:00-18:00&nbsp;[13:00&nbsp;14:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092802','1508860800000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092802','1508860800000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092802','1508860800000')">[New]</a></center></td><td><center></center></td><td><select name="504404665865736287_d_25_d_1508860800000_d_2017092802_d_19820202001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665865736287_d_25_d_1508860800000_d_2017092802_d_19820202001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665865736287_d_25_d_1508860800000_d_2017092802_d_19820202001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665865736287_d_25_d_1508860800000_d_2017092802_d_19820202001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665865736287_d_25_d_1508860800000_d_2017092802_d_19820202001_oVerIdx" value="-" type="text"></td><td><select name="504404665865736287_d_25_d_1508860800000_d_2017092802_d_19820202001_status" onkeydown="javascript:fnTrapKD()" id="504404665865736287_d_25_d_1508860800000_d_2017092802_d_19820202001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665865736287_d_25_d_1508860800000_d_2017092802_d_19820202001_reason" onkeydown="javascript:fnTrapKD()" id="504404665865736287_d_25_d_1508860800000_d_2017092802_d_19820202001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665865736287_d_25_d_1508860800000_d_2017092802_d_19820202001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665865736287_d_25_d_1508860800000_d_2017092802_d_19820202001" value="504404665865736287_d_25_d_1508860800000_d_2017092802_d_19820202001" type="checkbox"><input size="4" name="504404665865736287_d_25_d_1508860800000_d_2017092802_d_19820202001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">123</td><td class="Freeze">Wed,<br>25/10/2017&nbsp;</td><td class="Freeze">19870603001</td><td class="Freeze">Moses Sangga Mila</td><td class="Freeze">08:00</td><td class="Freeze">12:00</td><td class="Freeze">13:00</td><td class="Freeze">17:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665865736276_d_25_d_1508860800000_d_2017092801_d_19870603001_symbol" value="P8" size="2" title="08:00-17:00&nbsp;[12:00&nbsp;13:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092801','1508860800000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092801','1508860800000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092801','1508860800000')">[New]</a></center></td><td><center></center></td><td><select name="504404665865736276_d_25_d_1508860800000_d_2017092801_d_19870603001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665865736276_d_25_d_1508860800000_d_2017092801_d_19870603001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665865736276_d_25_d_1508860800000_d_2017092801_d_19870603001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665865736276_d_25_d_1508860800000_d_2017092801_d_19870603001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665865736276_d_25_d_1508860800000_d_2017092801_d_19870603001_oVerIdx" value="-" type="text"></td><td><select name="504404665865736276_d_25_d_1508860800000_d_2017092801_d_19870603001_status" onkeydown="javascript:fnTrapKD()" id="504404665865736276_d_25_d_1508860800000_d_2017092801_d_19870603001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665865736276_d_25_d_1508860800000_d_2017092801_d_19870603001_reason" onkeydown="javascript:fnTrapKD()" id="504404665865736276_d_25_d_1508860800000_d_2017092801_d_19870603001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665865736276_d_25_d_1508860800000_d_2017092801_d_19870603001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665865736276_d_25_d_1508860800000_d_2017092801_d_19870603001" value="504404665865736276_d_25_d_1508860800000_d_2017092801_d_19870603001" type="checkbox"><input size="4" name="504404665865736276_d_25_d_1508860800000_d_2017092801_d_19870603001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">124</td><td class="Freeze">Wed,<br>25/10/2017&nbsp;</td><td class="Freeze">19890519001</td><td class="Freeze">Khoirul Huda</td><td class="Freeze">08:00</td><td class="Freeze">12:00</td><td class="Freeze">13:00</td><td class="Freeze">17:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665795260590_d_25_d_1508860800000_d_2017092799_d_19890519001_symbol" value="P8" size="2" title="08:00-17:00&nbsp;[12:00&nbsp;13:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092799','1508860800000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092799','1508860800000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092799','1508860800000')">[New]</a></center></td><td><center></center></td><td><select name="504404665795260590_d_25_d_1508860800000_d_2017092799_d_19890519001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665795260590_d_25_d_1508860800000_d_2017092799_d_19890519001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665795260590_d_25_d_1508860800000_d_2017092799_d_19890519001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665795260590_d_25_d_1508860800000_d_2017092799_d_19890519001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665795260590_d_25_d_1508860800000_d_2017092799_d_19890519001_oVerIdx" value="-" type="text"></td><td><select name="504404665795260590_d_25_d_1508860800000_d_2017092799_d_19890519001_status" onkeydown="javascript:fnTrapKD()" id="504404665795260590_d_25_d_1508860800000_d_2017092799_d_19890519001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665795260590_d_25_d_1508860800000_d_2017092799_d_19890519001_reason" onkeydown="javascript:fnTrapKD()" id="504404665795260590_d_25_d_1508860800000_d_2017092799_d_19890519001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665795260590_d_25_d_1508860800000_d_2017092799_d_19890519001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665795260590_d_25_d_1508860800000_d_2017092799_d_19890519001" value="504404665795260590_d_25_d_1508860800000_d_2017092799_d_19890519001" type="checkbox"><input size="4" name="504404665795260590_d_25_d_1508860800000_d_2017092799_d_19890519001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">125</td><td class="Freeze">Wed,<br>25/10/2017&nbsp;</td><td class="Freeze">19930325001</td><td class="Freeze">Dita Ayuningtyas Fitriani</td><td class="Freeze">08:00</td><td class="Freeze">12:00</td><td class="Freeze">13:00</td><td class="Freeze">17:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665865735931_d_25_d_1508860800000_d_2017092798_d_19930325001_symbol" value="P8" size="2" title="08:00-17:00&nbsp;[12:00&nbsp;13:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092798','1508860800000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092798','1508860800000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092798','1508860800000')">[New]</a></center></td><td><center></center></td><td><select name="504404665865735931_d_25_d_1508860800000_d_2017092798_d_19930325001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665865735931_d_25_d_1508860800000_d_2017092798_d_19930325001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665865735931_d_25_d_1508860800000_d_2017092798_d_19930325001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665865735931_d_25_d_1508860800000_d_2017092798_d_19930325001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665865735931_d_25_d_1508860800000_d_2017092798_d_19930325001_oVerIdx" value="-" type="text"></td><td><select name="504404665865735931_d_25_d_1508860800000_d_2017092798_d_19930325001_status" onkeydown="javascript:fnTrapKD()" id="504404665865735931_d_25_d_1508860800000_d_2017092798_d_19930325001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665865735931_d_25_d_1508860800000_d_2017092798_d_19930325001_reason" onkeydown="javascript:fnTrapKD()" id="504404665865735931_d_25_d_1508860800000_d_2017092798_d_19930325001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665865735931_d_25_d_1508860800000_d_2017092798_d_19930325001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665865735931_d_25_d_1508860800000_d_2017092798_d_19930325001" value="504404665865735931_d_25_d_1508860800000_d_2017092798_d_19930325001" type="checkbox"><input size="4" name="504404665865735931_d_25_d_1508860800000_d_2017092798_d_19930325001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">126</td><td class="Freeze">Thu,<br>26/10/2017&nbsp;</td><td class="Freeze">19750922001</td><td class="Freeze">Dewa Gede Darmika</td><td class="Freeze">09:00</td><td class="Freeze">13:00</td><td class="Freeze">14:00</td><td class="Freeze">18:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665865736250_d_26_d_1508947200000_d_2017092800_d_19750922001_symbol" value="P9" size="2" title="09:00-18:00&nbsp;[13:00&nbsp;14:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092800','1508947200000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092800','1508947200000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092800','1508947200000')">[New]</a></center></td><td><center></center></td><td><select name="504404665865736250_d_26_d_1508947200000_d_2017092800_d_19750922001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665865736250_d_26_d_1508947200000_d_2017092800_d_19750922001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665865736250_d_26_d_1508947200000_d_2017092800_d_19750922001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665865736250_d_26_d_1508947200000_d_2017092800_d_19750922001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665865736250_d_26_d_1508947200000_d_2017092800_d_19750922001_oVerIdx" value="-" type="text"></td><td><select name="504404665865736250_d_26_d_1508947200000_d_2017092800_d_19750922001_status" onkeydown="javascript:fnTrapKD()" id="504404665865736250_d_26_d_1508947200000_d_2017092800_d_19750922001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665865736250_d_26_d_1508947200000_d_2017092800_d_19750922001_reason" onkeydown="javascript:fnTrapKD()" id="504404665865736250_d_26_d_1508947200000_d_2017092800_d_19750922001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665865736250_d_26_d_1508947200000_d_2017092800_d_19750922001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665865736250_d_26_d_1508947200000_d_2017092800_d_19750922001" value="504404665865736250_d_26_d_1508947200000_d_2017092800_d_19750922001" type="checkbox"><input size="4" name="504404665865736250_d_26_d_1508947200000_d_2017092800_d_19750922001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">127</td><td class="Freeze">Thu,<br>26/10/2017&nbsp;</td><td class="Freeze">19820202001</td><td class="Freeze">Arif Eko Cahyono</td><td class="Freeze">09:00</td><td class="Freeze">13:00</td><td class="Freeze">14:00</td><td class="Freeze">18:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665865736287_d_26_d_1508947200000_d_2017092802_d_19820202001_symbol" value="P9" size="2" title="09:00-18:00&nbsp;[13:00&nbsp;14:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092802','1508947200000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092802','1508947200000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092802','1508947200000')">[New]</a></center></td><td><center></center></td><td><select name="504404665865736287_d_26_d_1508947200000_d_2017092802_d_19820202001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665865736287_d_26_d_1508947200000_d_2017092802_d_19820202001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665865736287_d_26_d_1508947200000_d_2017092802_d_19820202001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665865736287_d_26_d_1508947200000_d_2017092802_d_19820202001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665865736287_d_26_d_1508947200000_d_2017092802_d_19820202001_oVerIdx" value="-" type="text"></td><td><select name="504404665865736287_d_26_d_1508947200000_d_2017092802_d_19820202001_status" onkeydown="javascript:fnTrapKD()" id="504404665865736287_d_26_d_1508947200000_d_2017092802_d_19820202001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665865736287_d_26_d_1508947200000_d_2017092802_d_19820202001_reason" onkeydown="javascript:fnTrapKD()" id="504404665865736287_d_26_d_1508947200000_d_2017092802_d_19820202001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665865736287_d_26_d_1508947200000_d_2017092802_d_19820202001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665865736287_d_26_d_1508947200000_d_2017092802_d_19820202001" value="504404665865736287_d_26_d_1508947200000_d_2017092802_d_19820202001" type="checkbox"><input size="4" name="504404665865736287_d_26_d_1508947200000_d_2017092802_d_19820202001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">128</td><td class="Freeze">Thu,<br>26/10/2017&nbsp;</td><td class="Freeze">19870603001</td><td class="Freeze">Moses Sangga Mila</td><td class="Freeze">08:00</td><td class="Freeze">12:00</td><td class="Freeze">13:00</td><td class="Freeze">17:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665865736276_d_26_d_1508947200000_d_2017092801_d_19870603001_symbol" value="P8" size="2" title="08:00-17:00&nbsp;[12:00&nbsp;13:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092801','1508947200000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092801','1508947200000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092801','1508947200000')">[New]</a></center></td><td><center></center></td><td><select name="504404665865736276_d_26_d_1508947200000_d_2017092801_d_19870603001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665865736276_d_26_d_1508947200000_d_2017092801_d_19870603001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665865736276_d_26_d_1508947200000_d_2017092801_d_19870603001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665865736276_d_26_d_1508947200000_d_2017092801_d_19870603001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665865736276_d_26_d_1508947200000_d_2017092801_d_19870603001_oVerIdx" value="-" type="text"></td><td><select name="504404665865736276_d_26_d_1508947200000_d_2017092801_d_19870603001_status" onkeydown="javascript:fnTrapKD()" id="504404665865736276_d_26_d_1508947200000_d_2017092801_d_19870603001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665865736276_d_26_d_1508947200000_d_2017092801_d_19870603001_reason" onkeydown="javascript:fnTrapKD()" id="504404665865736276_d_26_d_1508947200000_d_2017092801_d_19870603001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665865736276_d_26_d_1508947200000_d_2017092801_d_19870603001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665865736276_d_26_d_1508947200000_d_2017092801_d_19870603001" value="504404665865736276_d_26_d_1508947200000_d_2017092801_d_19870603001" type="checkbox"><input size="4" name="504404665865736276_d_26_d_1508947200000_d_2017092801_d_19870603001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">129</td><td class="Freeze">Thu,<br>26/10/2017&nbsp;</td><td class="Freeze">19890519001</td><td class="Freeze">Khoirul Huda</td><td class="Freeze">08:00</td><td class="Freeze">12:00</td><td class="Freeze">13:00</td><td class="Freeze">17:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665795260590_d_26_d_1508947200000_d_2017092799_d_19890519001_symbol" value="P8" size="2" title="08:00-17:00&nbsp;[12:00&nbsp;13:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092799','1508947200000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092799','1508947200000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092799','1508947200000')">[New]</a></center></td><td><center></center></td><td><select name="504404665795260590_d_26_d_1508947200000_d_2017092799_d_19890519001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665795260590_d_26_d_1508947200000_d_2017092799_d_19890519001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665795260590_d_26_d_1508947200000_d_2017092799_d_19890519001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665795260590_d_26_d_1508947200000_d_2017092799_d_19890519001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665795260590_d_26_d_1508947200000_d_2017092799_d_19890519001_oVerIdx" value="-" type="text"></td><td><select name="504404665795260590_d_26_d_1508947200000_d_2017092799_d_19890519001_status" onkeydown="javascript:fnTrapKD()" id="504404665795260590_d_26_d_1508947200000_d_2017092799_d_19890519001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665795260590_d_26_d_1508947200000_d_2017092799_d_19890519001_reason" onkeydown="javascript:fnTrapKD()" id="504404665795260590_d_26_d_1508947200000_d_2017092799_d_19890519001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665795260590_d_26_d_1508947200000_d_2017092799_d_19890519001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665795260590_d_26_d_1508947200000_d_2017092799_d_19890519001" value="504404665795260590_d_26_d_1508947200000_d_2017092799_d_19890519001" type="checkbox"><input size="4" name="504404665795260590_d_26_d_1508947200000_d_2017092799_d_19890519001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">130</td><td class="Freeze">Thu,<br>26/10/2017&nbsp;</td><td class="Freeze">19930325001</td><td class="Freeze">Dita Ayuningtyas Fitriani</td><td class="Freeze">08:00</td><td class="Freeze">12:00</td><td class="Freeze">13:00</td><td class="Freeze">17:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665865735931_d_26_d_1508947200000_d_2017092798_d_19930325001_symbol" value="P8" size="2" title="08:00-17:00&nbsp;[12:00&nbsp;13:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092798','1508947200000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092798','1508947200000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092798','1508947200000')">[New]</a></center></td><td><center></center></td><td><select name="504404665865735931_d_26_d_1508947200000_d_2017092798_d_19930325001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665865735931_d_26_d_1508947200000_d_2017092798_d_19930325001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665865735931_d_26_d_1508947200000_d_2017092798_d_19930325001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665865735931_d_26_d_1508947200000_d_2017092798_d_19930325001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665865735931_d_26_d_1508947200000_d_2017092798_d_19930325001_oVerIdx" value="-" type="text"></td><td><select name="504404665865735931_d_26_d_1508947200000_d_2017092798_d_19930325001_status" onkeydown="javascript:fnTrapKD()" id="504404665865735931_d_26_d_1508947200000_d_2017092798_d_19930325001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665865735931_d_26_d_1508947200000_d_2017092798_d_19930325001_reason" onkeydown="javascript:fnTrapKD()" id="504404665865735931_d_26_d_1508947200000_d_2017092798_d_19930325001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665865735931_d_26_d_1508947200000_d_2017092798_d_19930325001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665865735931_d_26_d_1508947200000_d_2017092798_d_19930325001" value="504404665865735931_d_26_d_1508947200000_d_2017092798_d_19930325001" type="checkbox"><input size="4" name="504404665865735931_d_26_d_1508947200000_d_2017092798_d_19930325001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">131</td><td class="Freeze">Fri,<br>27/10/2017&nbsp;</td><td class="Freeze">19750922001</td><td class="Freeze">Dewa Gede Darmika</td><td class="Freeze">09:00</td><td class="Freeze">13:00</td><td class="Freeze">14:00</td><td class="Freeze">18:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665865736250_d_27_d_1509033600000_d_2017092800_d_19750922001_symbol" value="P9" size="2" title="09:00-18:00&nbsp;[13:00&nbsp;14:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092800','1509033600000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092800','1509033600000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092800','1509033600000')">[New]</a></center></td><td><center></center></td><td><select name="504404665865736250_d_27_d_1509033600000_d_2017092800_d_19750922001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665865736250_d_27_d_1509033600000_d_2017092800_d_19750922001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665865736250_d_27_d_1509033600000_d_2017092800_d_19750922001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665865736250_d_27_d_1509033600000_d_2017092800_d_19750922001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665865736250_d_27_d_1509033600000_d_2017092800_d_19750922001_oVerIdx" value="-" type="text"></td><td><select name="504404665865736250_d_27_d_1509033600000_d_2017092800_d_19750922001_status" onkeydown="javascript:fnTrapKD()" id="504404665865736250_d_27_d_1509033600000_d_2017092800_d_19750922001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665865736250_d_27_d_1509033600000_d_2017092800_d_19750922001_reason" onkeydown="javascript:fnTrapKD()" id="504404665865736250_d_27_d_1509033600000_d_2017092800_d_19750922001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665865736250_d_27_d_1509033600000_d_2017092800_d_19750922001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665865736250_d_27_d_1509033600000_d_2017092800_d_19750922001" value="504404665865736250_d_27_d_1509033600000_d_2017092800_d_19750922001" type="checkbox"><input size="4" name="504404665865736250_d_27_d_1509033600000_d_2017092800_d_19750922001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">132</td><td class="Freeze">Fri,<br>27/10/2017&nbsp;</td><td class="Freeze">19820202001</td><td class="Freeze">Arif Eko Cahyono</td><td class="Freeze">09:00</td><td class="Freeze">13:00</td><td class="Freeze">14:00</td><td class="Freeze">18:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665865736287_d_27_d_1509033600000_d_2017092802_d_19820202001_symbol" value="P9" size="2" title="09:00-18:00&nbsp;[13:00&nbsp;14:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092802','1509033600000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092802','1509033600000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092802','1509033600000')">[New]</a></center></td><td><center></center></td><td><select name="504404665865736287_d_27_d_1509033600000_d_2017092802_d_19820202001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665865736287_d_27_d_1509033600000_d_2017092802_d_19820202001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665865736287_d_27_d_1509033600000_d_2017092802_d_19820202001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665865736287_d_27_d_1509033600000_d_2017092802_d_19820202001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665865736287_d_27_d_1509033600000_d_2017092802_d_19820202001_oVerIdx" value="-" type="text"></td><td><select name="504404665865736287_d_27_d_1509033600000_d_2017092802_d_19820202001_status" onkeydown="javascript:fnTrapKD()" id="504404665865736287_d_27_d_1509033600000_d_2017092802_d_19820202001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665865736287_d_27_d_1509033600000_d_2017092802_d_19820202001_reason" onkeydown="javascript:fnTrapKD()" id="504404665865736287_d_27_d_1509033600000_d_2017092802_d_19820202001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665865736287_d_27_d_1509033600000_d_2017092802_d_19820202001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665865736287_d_27_d_1509033600000_d_2017092802_d_19820202001" value="504404665865736287_d_27_d_1509033600000_d_2017092802_d_19820202001" type="checkbox"><input size="4" name="504404665865736287_d_27_d_1509033600000_d_2017092802_d_19820202001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">133</td><td class="Freeze">Fri,<br>27/10/2017&nbsp;</td><td class="Freeze">19870603001</td><td class="Freeze">Moses Sangga Mila</td><td class="Freeze">08:00</td><td class="Freeze">12:00</td><td class="Freeze">13:00</td><td class="Freeze">17:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665865736276_d_27_d_1509033600000_d_2017092801_d_19870603001_symbol" value="P8" size="2" title="08:00-17:00&nbsp;[12:00&nbsp;13:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092801','1509033600000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092801','1509033600000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092801','1509033600000')">[New]</a></center></td><td><center></center></td><td><select name="504404665865736276_d_27_d_1509033600000_d_2017092801_d_19870603001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665865736276_d_27_d_1509033600000_d_2017092801_d_19870603001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665865736276_d_27_d_1509033600000_d_2017092801_d_19870603001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665865736276_d_27_d_1509033600000_d_2017092801_d_19870603001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665865736276_d_27_d_1509033600000_d_2017092801_d_19870603001_oVerIdx" value="-" type="text"></td><td><select name="504404665865736276_d_27_d_1509033600000_d_2017092801_d_19870603001_status" onkeydown="javascript:fnTrapKD()" id="504404665865736276_d_27_d_1509033600000_d_2017092801_d_19870603001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665865736276_d_27_d_1509033600000_d_2017092801_d_19870603001_reason" onkeydown="javascript:fnTrapKD()" id="504404665865736276_d_27_d_1509033600000_d_2017092801_d_19870603001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665865736276_d_27_d_1509033600000_d_2017092801_d_19870603001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665865736276_d_27_d_1509033600000_d_2017092801_d_19870603001" value="504404665865736276_d_27_d_1509033600000_d_2017092801_d_19870603001" type="checkbox"><input size="4" name="504404665865736276_d_27_d_1509033600000_d_2017092801_d_19870603001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">134</td><td class="Freeze">Fri,<br>27/10/2017&nbsp;</td><td class="Freeze">19890519001</td><td class="Freeze">Khoirul Huda</td><td class="Freeze">08:00</td><td class="Freeze">12:00</td><td class="Freeze">13:00</td><td class="Freeze">17:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665795260590_d_27_d_1509033600000_d_2017092799_d_19890519001_symbol" value="P8" size="2" title="08:00-17:00&nbsp;[12:00&nbsp;13:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092799','1509033600000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092799','1509033600000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092799','1509033600000')">[New]</a></center></td><td><center></center></td><td><select name="504404665795260590_d_27_d_1509033600000_d_2017092799_d_19890519001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665795260590_d_27_d_1509033600000_d_2017092799_d_19890519001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665795260590_d_27_d_1509033600000_d_2017092799_d_19890519001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665795260590_d_27_d_1509033600000_d_2017092799_d_19890519001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665795260590_d_27_d_1509033600000_d_2017092799_d_19890519001_oVerIdx" value="-" type="text"></td><td><select name="504404665795260590_d_27_d_1509033600000_d_2017092799_d_19890519001_status" onkeydown="javascript:fnTrapKD()" id="504404665795260590_d_27_d_1509033600000_d_2017092799_d_19890519001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665795260590_d_27_d_1509033600000_d_2017092799_d_19890519001_reason" onkeydown="javascript:fnTrapKD()" id="504404665795260590_d_27_d_1509033600000_d_2017092799_d_19890519001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665795260590_d_27_d_1509033600000_d_2017092799_d_19890519001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665795260590_d_27_d_1509033600000_d_2017092799_d_19890519001" value="504404665795260590_d_27_d_1509033600000_d_2017092799_d_19890519001" type="checkbox"><input size="4" name="504404665795260590_d_27_d_1509033600000_d_2017092799_d_19890519001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">135</td><td class="Freeze">Fri,<br>27/10/2017&nbsp;</td><td class="Freeze">19930325001</td><td class="Freeze">Dita Ayuningtyas Fitriani</td><td class="Freeze">08:00</td><td class="Freeze">12:00</td><td class="Freeze">13:00</td><td class="Freeze">17:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665865735931_d_27_d_1509033600000_d_2017092798_d_19930325001_symbol" value="P8" size="2" title="08:00-17:00&nbsp;[12:00&nbsp;13:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092798','1509033600000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092798','1509033600000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092798','1509033600000')">[New]</a></center></td><td><center></center></td><td><select name="504404665865735931_d_27_d_1509033600000_d_2017092798_d_19930325001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665865735931_d_27_d_1509033600000_d_2017092798_d_19930325001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665865735931_d_27_d_1509033600000_d_2017092798_d_19930325001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665865735931_d_27_d_1509033600000_d_2017092798_d_19930325001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665865735931_d_27_d_1509033600000_d_2017092798_d_19930325001_oVerIdx" value="-" type="text"></td><td><select name="504404665865735931_d_27_d_1509033600000_d_2017092798_d_19930325001_status" onkeydown="javascript:fnTrapKD()" id="504404665865735931_d_27_d_1509033600000_d_2017092798_d_19930325001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665865735931_d_27_d_1509033600000_d_2017092798_d_19930325001_reason" onkeydown="javascript:fnTrapKD()" id="504404665865735931_d_27_d_1509033600000_d_2017092798_d_19930325001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665865735931_d_27_d_1509033600000_d_2017092798_d_19930325001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665865735931_d_27_d_1509033600000_d_2017092798_d_19930325001" value="504404665865735931_d_27_d_1509033600000_d_2017092798_d_19930325001" type="checkbox"><input size="4" name="504404665865735931_d_27_d_1509033600000_d_2017092798_d_19930325001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">136</td><td class="Freeze"><font color="darkblue">Sat</font>,<br><font color="darkblue">28/10/2017</font>&nbsp;</td><td class="Freeze">19750922001</td><td class="Freeze">Dewa Gede Darmika</td><td class="Freeze">09:00</td><td class="Freeze">13:00</td><td class="Freeze">14:00</td><td class="Freeze">18:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665865736250_d_28_d_1509120000000_d_2017092800_d_19750922001_symbol" value="P9" size="2" title="09:00-18:00&nbsp;[13:00&nbsp;14:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092800','1509120000000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092800','1509120000000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092800','1509120000000')">[New]</a></center></td><td><center></center></td><td><select name="504404665865736250_d_28_d_1509120000000_d_2017092800_d_19750922001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665865736250_d_28_d_1509120000000_d_2017092800_d_19750922001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665865736250_d_28_d_1509120000000_d_2017092800_d_19750922001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665865736250_d_28_d_1509120000000_d_2017092800_d_19750922001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665865736250_d_28_d_1509120000000_d_2017092800_d_19750922001_oVerIdx" value="-" type="text"></td><td><select name="504404665865736250_d_28_d_1509120000000_d_2017092800_d_19750922001_status" onkeydown="javascript:fnTrapKD()" id="504404665865736250_d_28_d_1509120000000_d_2017092800_d_19750922001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665865736250_d_28_d_1509120000000_d_2017092800_d_19750922001_reason" onkeydown="javascript:fnTrapKD()" id="504404665865736250_d_28_d_1509120000000_d_2017092800_d_19750922001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665865736250_d_28_d_1509120000000_d_2017092800_d_19750922001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665865736250_d_28_d_1509120000000_d_2017092800_d_19750922001" value="504404665865736250_d_28_d_1509120000000_d_2017092800_d_19750922001" type="checkbox"><input size="4" name="504404665865736250_d_28_d_1509120000000_d_2017092800_d_19750922001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">137</td><td class="Freeze"><font color="darkblue">Sat</font>,<br><font color="darkblue">28/10/2017</font>&nbsp;</td><td class="Freeze">19820202001</td><td class="Freeze">Arif Eko Cahyono</td><td class="Freeze">09:00</td><td class="Freeze">13:00</td><td class="Freeze">14:00</td><td class="Freeze">18:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665865736287_d_28_d_1509120000000_d_2017092802_d_19820202001_symbol" value="P9" size="2" title="09:00-18:00&nbsp;[13:00&nbsp;14:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092802','1509120000000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092802','1509120000000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092802','1509120000000')">[New]</a></center></td><td><center></center></td><td><select name="504404665865736287_d_28_d_1509120000000_d_2017092802_d_19820202001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665865736287_d_28_d_1509120000000_d_2017092802_d_19820202001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665865736287_d_28_d_1509120000000_d_2017092802_d_19820202001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665865736287_d_28_d_1509120000000_d_2017092802_d_19820202001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665865736287_d_28_d_1509120000000_d_2017092802_d_19820202001_oVerIdx" value="-" type="text"></td><td><select name="504404665865736287_d_28_d_1509120000000_d_2017092802_d_19820202001_status" onkeydown="javascript:fnTrapKD()" id="504404665865736287_d_28_d_1509120000000_d_2017092802_d_19820202001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665865736287_d_28_d_1509120000000_d_2017092802_d_19820202001_reason" onkeydown="javascript:fnTrapKD()" id="504404665865736287_d_28_d_1509120000000_d_2017092802_d_19820202001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665865736287_d_28_d_1509120000000_d_2017092802_d_19820202001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665865736287_d_28_d_1509120000000_d_2017092802_d_19820202001" value="504404665865736287_d_28_d_1509120000000_d_2017092802_d_19820202001" type="checkbox"><input size="4" name="504404665865736287_d_28_d_1509120000000_d_2017092802_d_19820202001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">138</td><td class="Freeze"><font color="darkblue">Sat</font>,<br><font color="darkblue">28/10/2017</font>&nbsp;</td><td class="Freeze">19870603001</td><td class="Freeze">Moses Sangga Mila</td><td class="Freeze">08:00</td><td class="Freeze">12:00</td><td class="Freeze">13:00</td><td class="Freeze">17:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665865736276_d_28_d_1509120000000_d_2017092801_d_19870603001_symbol" value="P8" size="2" title="08:00-17:00&nbsp;[12:00&nbsp;13:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092801','1509120000000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092801','1509120000000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092801','1509120000000')">[New]</a></center></td><td><center></center></td><td><select name="504404665865736276_d_28_d_1509120000000_d_2017092801_d_19870603001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665865736276_d_28_d_1509120000000_d_2017092801_d_19870603001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665865736276_d_28_d_1509120000000_d_2017092801_d_19870603001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665865736276_d_28_d_1509120000000_d_2017092801_d_19870603001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665865736276_d_28_d_1509120000000_d_2017092801_d_19870603001_oVerIdx" value="-" type="text"></td><td><select name="504404665865736276_d_28_d_1509120000000_d_2017092801_d_19870603001_status" onkeydown="javascript:fnTrapKD()" id="504404665865736276_d_28_d_1509120000000_d_2017092801_d_19870603001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665865736276_d_28_d_1509120000000_d_2017092801_d_19870603001_reason" onkeydown="javascript:fnTrapKD()" id="504404665865736276_d_28_d_1509120000000_d_2017092801_d_19870603001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665865736276_d_28_d_1509120000000_d_2017092801_d_19870603001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665865736276_d_28_d_1509120000000_d_2017092801_d_19870603001" value="504404665865736276_d_28_d_1509120000000_d_2017092801_d_19870603001" type="checkbox"><input size="4" name="504404665865736276_d_28_d_1509120000000_d_2017092801_d_19870603001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">139</td><td class="Freeze"><font color="darkblue">Sat</font>,<br><font color="darkblue">28/10/2017</font>&nbsp;</td><td class="Freeze">19890519001</td><td class="Freeze">Khoirul Huda</td><td class="Freeze">08:00</td><td class="Freeze">12:00</td><td class="Freeze">13:00</td><td class="Freeze">17:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665795260590_d_28_d_1509120000000_d_2017092799_d_19890519001_symbol" value="P8" size="2" title="08:00-17:00&nbsp;[12:00&nbsp;13:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092799','1509120000000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092799','1509120000000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092799','1509120000000')">[New]</a></center></td><td><center></center></td><td><select name="504404665795260590_d_28_d_1509120000000_d_2017092799_d_19890519001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665795260590_d_28_d_1509120000000_d_2017092799_d_19890519001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665795260590_d_28_d_1509120000000_d_2017092799_d_19890519001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665795260590_d_28_d_1509120000000_d_2017092799_d_19890519001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665795260590_d_28_d_1509120000000_d_2017092799_d_19890519001_oVerIdx" value="-" type="text"></td><td><select name="504404665795260590_d_28_d_1509120000000_d_2017092799_d_19890519001_status" onkeydown="javascript:fnTrapKD()" id="504404665795260590_d_28_d_1509120000000_d_2017092799_d_19890519001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665795260590_d_28_d_1509120000000_d_2017092799_d_19890519001_reason" onkeydown="javascript:fnTrapKD()" id="504404665795260590_d_28_d_1509120000000_d_2017092799_d_19890519001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665795260590_d_28_d_1509120000000_d_2017092799_d_19890519001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665795260590_d_28_d_1509120000000_d_2017092799_d_19890519001" value="504404665795260590_d_28_d_1509120000000_d_2017092799_d_19890519001" type="checkbox"><input size="4" name="504404665795260590_d_28_d_1509120000000_d_2017092799_d_19890519001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">140</td><td class="Freeze"><font color="darkblue">Sat</font>,<br><font color="darkblue">28/10/2017</font>&nbsp;</td><td class="Freeze">19930325001</td><td class="Freeze">Dita Ayuningtyas Fitriani</td><td class="Freeze">08:00</td><td class="Freeze">12:00</td><td class="Freeze">13:00</td><td class="Freeze">17:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665865735931_d_28_d_1509120000000_d_2017092798_d_19930325001_symbol" value="P8" size="2" title="08:00-17:00&nbsp;[12:00&nbsp;13:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092798','1509120000000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092798','1509120000000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092798','1509120000000')">[New]</a></center></td><td><center></center></td><td><select name="504404665865735931_d_28_d_1509120000000_d_2017092798_d_19930325001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665865735931_d_28_d_1509120000000_d_2017092798_d_19930325001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665865735931_d_28_d_1509120000000_d_2017092798_d_19930325001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665865735931_d_28_d_1509120000000_d_2017092798_d_19930325001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665865735931_d_28_d_1509120000000_d_2017092798_d_19930325001_oVerIdx" value="-" type="text"></td><td><select name="504404665865735931_d_28_d_1509120000000_d_2017092798_d_19930325001_status" onkeydown="javascript:fnTrapKD()" id="504404665865735931_d_28_d_1509120000000_d_2017092798_d_19930325001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665865735931_d_28_d_1509120000000_d_2017092798_d_19930325001_reason" onkeydown="javascript:fnTrapKD()" id="504404665865735931_d_28_d_1509120000000_d_2017092798_d_19930325001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665865735931_d_28_d_1509120000000_d_2017092798_d_19930325001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665865735931_d_28_d_1509120000000_d_2017092798_d_19930325001" value="504404665865735931_d_28_d_1509120000000_d_2017092798_d_19930325001" type="checkbox"><input size="4" name="504404665865735931_d_28_d_1509120000000_d_2017092798_d_19930325001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">141</td><td class="Freeze"><font color="red">Sun</font>,<br><font color="red">29/10/2017</font>&nbsp;</td><td class="Freeze">19750922001</td><td class="Freeze">Dewa Gede Darmika</td><td class="Freeze">09:00</td><td class="Freeze">13:00</td><td class="Freeze">14:00</td><td class="Freeze">18:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665865736250_d_29_d_1509206400000_d_2017092800_d_19750922001_symbol" value="P9" size="2" title="09:00-18:00&nbsp;[13:00&nbsp;14:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092800','1509206400000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092800','1509206400000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092800','1509206400000')">[New]</a></center></td><td><center></center></td><td><select name="504404665865736250_d_29_d_1509206400000_d_2017092800_d_19750922001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665865736250_d_29_d_1509206400000_d_2017092800_d_19750922001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665865736250_d_29_d_1509206400000_d_2017092800_d_19750922001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665865736250_d_29_d_1509206400000_d_2017092800_d_19750922001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665865736250_d_29_d_1509206400000_d_2017092800_d_19750922001_oVerIdx" value="-" type="text"></td><td><select name="504404665865736250_d_29_d_1509206400000_d_2017092800_d_19750922001_status" onkeydown="javascript:fnTrapKD()" id="504404665865736250_d_29_d_1509206400000_d_2017092800_d_19750922001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665865736250_d_29_d_1509206400000_d_2017092800_d_19750922001_reason" onkeydown="javascript:fnTrapKD()" id="504404665865736250_d_29_d_1509206400000_d_2017092800_d_19750922001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665865736250_d_29_d_1509206400000_d_2017092800_d_19750922001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665865736250_d_29_d_1509206400000_d_2017092800_d_19750922001" value="504404665865736250_d_29_d_1509206400000_d_2017092800_d_19750922001" type="checkbox"><input size="4" name="504404665865736250_d_29_d_1509206400000_d_2017092800_d_19750922001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">142</td><td class="Freeze"><font color="red">Sun</font>,<br><font color="red">29/10/2017</font>&nbsp;</td><td class="Freeze">19820202001</td><td class="Freeze">Arif Eko Cahyono</td><td class="Freeze">09:00</td><td class="Freeze">13:00</td><td class="Freeze">14:00</td><td class="Freeze">18:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665865736287_d_29_d_1509206400000_d_2017092802_d_19820202001_symbol" value="P9" size="2" title="09:00-18:00&nbsp;[13:00&nbsp;14:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092802','1509206400000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092802','1509206400000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092802','1509206400000')">[New]</a></center></td><td><center></center></td><td><select name="504404665865736287_d_29_d_1509206400000_d_2017092802_d_19820202001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665865736287_d_29_d_1509206400000_d_2017092802_d_19820202001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665865736287_d_29_d_1509206400000_d_2017092802_d_19820202001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665865736287_d_29_d_1509206400000_d_2017092802_d_19820202001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665865736287_d_29_d_1509206400000_d_2017092802_d_19820202001_oVerIdx" value="-" type="text"></td><td><select name="504404665865736287_d_29_d_1509206400000_d_2017092802_d_19820202001_status" onkeydown="javascript:fnTrapKD()" id="504404665865736287_d_29_d_1509206400000_d_2017092802_d_19820202001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665865736287_d_29_d_1509206400000_d_2017092802_d_19820202001_reason" onkeydown="javascript:fnTrapKD()" id="504404665865736287_d_29_d_1509206400000_d_2017092802_d_19820202001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665865736287_d_29_d_1509206400000_d_2017092802_d_19820202001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665865736287_d_29_d_1509206400000_d_2017092802_d_19820202001" value="504404665865736287_d_29_d_1509206400000_d_2017092802_d_19820202001" type="checkbox"><input size="4" name="504404665865736287_d_29_d_1509206400000_d_2017092802_d_19820202001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">143</td><td class="Freeze"><font color="red">Sun</font>,<br><font color="red">29/10/2017</font>&nbsp;</td><td class="Freeze">19870603001</td><td class="Freeze">Moses Sangga Mila</td><td class="Freeze">08:00</td><td class="Freeze">12:00</td><td class="Freeze">13:00</td><td class="Freeze">17:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665865736276_d_29_d_1509206400000_d_2017092801_d_19870603001_symbol" value="P8" size="2" title="08:00-17:00&nbsp;[12:00&nbsp;13:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092801','1509206400000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092801','1509206400000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092801','1509206400000')">[New]</a></center></td><td><center></center></td><td><select name="504404665865736276_d_29_d_1509206400000_d_2017092801_d_19870603001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665865736276_d_29_d_1509206400000_d_2017092801_d_19870603001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665865736276_d_29_d_1509206400000_d_2017092801_d_19870603001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665865736276_d_29_d_1509206400000_d_2017092801_d_19870603001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665865736276_d_29_d_1509206400000_d_2017092801_d_19870603001_oVerIdx" value="-" type="text"></td><td><select name="504404665865736276_d_29_d_1509206400000_d_2017092801_d_19870603001_status" onkeydown="javascript:fnTrapKD()" id="504404665865736276_d_29_d_1509206400000_d_2017092801_d_19870603001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665865736276_d_29_d_1509206400000_d_2017092801_d_19870603001_reason" onkeydown="javascript:fnTrapKD()" id="504404665865736276_d_29_d_1509206400000_d_2017092801_d_19870603001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665865736276_d_29_d_1509206400000_d_2017092801_d_19870603001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665865736276_d_29_d_1509206400000_d_2017092801_d_19870603001" value="504404665865736276_d_29_d_1509206400000_d_2017092801_d_19870603001" type="checkbox"><input size="4" name="504404665865736276_d_29_d_1509206400000_d_2017092801_d_19870603001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">144</td><td class="Freeze"><font color="red">Sun</font>,<br><font color="red">29/10/2017</font>&nbsp;</td><td class="Freeze">19890519001</td><td class="Freeze">Khoirul Huda</td><td class="Freeze">08:00</td><td class="Freeze">12:00</td><td class="Freeze">13:00</td><td class="Freeze">17:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665795260590_d_29_d_1509206400000_d_2017092799_d_19890519001_symbol" value="P8" size="2" title="08:00-17:00&nbsp;[12:00&nbsp;13:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092799','1509206400000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092799','1509206400000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092799','1509206400000')">[New]</a></center></td><td><center></center></td><td><select name="504404665795260590_d_29_d_1509206400000_d_2017092799_d_19890519001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665795260590_d_29_d_1509206400000_d_2017092799_d_19890519001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665795260590_d_29_d_1509206400000_d_2017092799_d_19890519001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665795260590_d_29_d_1509206400000_d_2017092799_d_19890519001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665795260590_d_29_d_1509206400000_d_2017092799_d_19890519001_oVerIdx" value="-" type="text"></td><td><select name="504404665795260590_d_29_d_1509206400000_d_2017092799_d_19890519001_status" onkeydown="javascript:fnTrapKD()" id="504404665795260590_d_29_d_1509206400000_d_2017092799_d_19890519001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665795260590_d_29_d_1509206400000_d_2017092799_d_19890519001_reason" onkeydown="javascript:fnTrapKD()" id="504404665795260590_d_29_d_1509206400000_d_2017092799_d_19890519001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665795260590_d_29_d_1509206400000_d_2017092799_d_19890519001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665795260590_d_29_d_1509206400000_d_2017092799_d_19890519001" value="504404665795260590_d_29_d_1509206400000_d_2017092799_d_19890519001" type="checkbox"><input size="4" name="504404665795260590_d_29_d_1509206400000_d_2017092799_d_19890519001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">145</td><td class="Freeze"><font color="red">Sun</font>,<br><font color="red">29/10/2017</font>&nbsp;</td><td class="Freeze">19930325001</td><td class="Freeze">Dita Ayuningtyas Fitriani</td><td class="Freeze">08:00</td><td class="Freeze">12:00</td><td class="Freeze">13:00</td><td class="Freeze">17:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665865735931_d_29_d_1509206400000_d_2017092798_d_19930325001_symbol" value="P8" size="2" title="08:00-17:00&nbsp;[12:00&nbsp;13:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092798','1509206400000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092798','1509206400000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092798','1509206400000')">[New]</a></center></td><td><center></center></td><td><select name="504404665865735931_d_29_d_1509206400000_d_2017092798_d_19930325001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665865735931_d_29_d_1509206400000_d_2017092798_d_19930325001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665865735931_d_29_d_1509206400000_d_2017092798_d_19930325001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665865735931_d_29_d_1509206400000_d_2017092798_d_19930325001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665865735931_d_29_d_1509206400000_d_2017092798_d_19930325001_oVerIdx" value="-" type="text"></td><td><select name="504404665865735931_d_29_d_1509206400000_d_2017092798_d_19930325001_status" onkeydown="javascript:fnTrapKD()" id="504404665865735931_d_29_d_1509206400000_d_2017092798_d_19930325001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665865735931_d_29_d_1509206400000_d_2017092798_d_19930325001_reason" onkeydown="javascript:fnTrapKD()" id="504404665865735931_d_29_d_1509206400000_d_2017092798_d_19930325001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665865735931_d_29_d_1509206400000_d_2017092798_d_19930325001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665865735931_d_29_d_1509206400000_d_2017092798_d_19930325001" value="504404665865735931_d_29_d_1509206400000_d_2017092798_d_19930325001" type="checkbox"><input size="4" name="504404665865735931_d_29_d_1509206400000_d_2017092798_d_19930325001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">146</td><td class="Freeze">Mon,<br>30/10/2017&nbsp;</td><td class="Freeze">19750922001</td><td class="Freeze">Dewa Gede Darmika</td><td class="Freeze">09:00</td><td class="Freeze">13:00</td><td class="Freeze">14:00</td><td class="Freeze">18:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665865736250_d_30_d_1509292800000_d_2017092800_d_19750922001_symbol" value="P9" size="2" title="09:00-18:00&nbsp;[13:00&nbsp;14:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092800','1509292800000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092800','1509292800000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092800','1509292800000')">[New]</a></center></td><td><center></center></td><td><select name="504404665865736250_d_30_d_1509292800000_d_2017092800_d_19750922001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665865736250_d_30_d_1509292800000_d_2017092800_d_19750922001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665865736250_d_30_d_1509292800000_d_2017092800_d_19750922001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665865736250_d_30_d_1509292800000_d_2017092800_d_19750922001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665865736250_d_30_d_1509292800000_d_2017092800_d_19750922001_oVerIdx" value="-" type="text"></td><td><select name="504404665865736250_d_30_d_1509292800000_d_2017092800_d_19750922001_status" onkeydown="javascript:fnTrapKD()" id="504404665865736250_d_30_d_1509292800000_d_2017092800_d_19750922001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665865736250_d_30_d_1509292800000_d_2017092800_d_19750922001_reason" onkeydown="javascript:fnTrapKD()" id="504404665865736250_d_30_d_1509292800000_d_2017092800_d_19750922001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665865736250_d_30_d_1509292800000_d_2017092800_d_19750922001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665865736250_d_30_d_1509292800000_d_2017092800_d_19750922001" value="504404665865736250_d_30_d_1509292800000_d_2017092800_d_19750922001" type="checkbox"><input size="4" name="504404665865736250_d_30_d_1509292800000_d_2017092800_d_19750922001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">147</td><td class="Freeze">Mon,<br>30/10/2017&nbsp;</td><td class="Freeze">19820202001</td><td class="Freeze">Arif Eko Cahyono</td><td class="Freeze">09:00</td><td class="Freeze">13:00</td><td class="Freeze">14:00</td><td class="Freeze">18:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665865736287_d_30_d_1509292800000_d_2017092802_d_19820202001_symbol" value="P9" size="2" title="09:00-18:00&nbsp;[13:00&nbsp;14:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092802','1509292800000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092802','1509292800000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092802','1509292800000')">[New]</a></center></td><td><center></center></td><td><select name="504404665865736287_d_30_d_1509292800000_d_2017092802_d_19820202001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665865736287_d_30_d_1509292800000_d_2017092802_d_19820202001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665865736287_d_30_d_1509292800000_d_2017092802_d_19820202001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665865736287_d_30_d_1509292800000_d_2017092802_d_19820202001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665865736287_d_30_d_1509292800000_d_2017092802_d_19820202001_oVerIdx" value="-" type="text"></td><td><select name="504404665865736287_d_30_d_1509292800000_d_2017092802_d_19820202001_status" onkeydown="javascript:fnTrapKD()" id="504404665865736287_d_30_d_1509292800000_d_2017092802_d_19820202001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665865736287_d_30_d_1509292800000_d_2017092802_d_19820202001_reason" onkeydown="javascript:fnTrapKD()" id="504404665865736287_d_30_d_1509292800000_d_2017092802_d_19820202001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665865736287_d_30_d_1509292800000_d_2017092802_d_19820202001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665865736287_d_30_d_1509292800000_d_2017092802_d_19820202001" value="504404665865736287_d_30_d_1509292800000_d_2017092802_d_19820202001" type="checkbox"><input size="4" name="504404665865736287_d_30_d_1509292800000_d_2017092802_d_19820202001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">148</td><td class="Freeze">Mon,<br>30/10/2017&nbsp;</td><td class="Freeze">19870603001</td><td class="Freeze">Moses Sangga Mila</td><td class="Freeze">08:00</td><td class="Freeze">12:00</td><td class="Freeze">13:00</td><td class="Freeze">17:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665865736276_d_30_d_1509292800000_d_2017092801_d_19870603001_symbol" value="P8" size="2" title="08:00-17:00&nbsp;[12:00&nbsp;13:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092801','1509292800000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092801','1509292800000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092801','1509292800000')">[New]</a></center></td><td><center></center></td><td><select name="504404665865736276_d_30_d_1509292800000_d_2017092801_d_19870603001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665865736276_d_30_d_1509292800000_d_2017092801_d_19870603001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665865736276_d_30_d_1509292800000_d_2017092801_d_19870603001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665865736276_d_30_d_1509292800000_d_2017092801_d_19870603001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665865736276_d_30_d_1509292800000_d_2017092801_d_19870603001_oVerIdx" value="-" type="text"></td><td><select name="504404665865736276_d_30_d_1509292800000_d_2017092801_d_19870603001_status" onkeydown="javascript:fnTrapKD()" id="504404665865736276_d_30_d_1509292800000_d_2017092801_d_19870603001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665865736276_d_30_d_1509292800000_d_2017092801_d_19870603001_reason" onkeydown="javascript:fnTrapKD()" id="504404665865736276_d_30_d_1509292800000_d_2017092801_d_19870603001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665865736276_d_30_d_1509292800000_d_2017092801_d_19870603001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665865736276_d_30_d_1509292800000_d_2017092801_d_19870603001" value="504404665865736276_d_30_d_1509292800000_d_2017092801_d_19870603001" type="checkbox"><input size="4" name="504404665865736276_d_30_d_1509292800000_d_2017092801_d_19870603001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">149</td><td class="Freeze">Mon,<br>30/10/2017&nbsp;</td><td class="Freeze">19890519001</td><td class="Freeze">Khoirul Huda</td><td class="Freeze">09:00</td><td class="Freeze">13:00</td><td class="Freeze">14:00</td><td class="Freeze">18:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665795260590_d_30_d_1509292800000_d_2017092799_d_19890519001_symbol" value="P9" size="2" title="09:00-18:00&nbsp;[13:00&nbsp;14:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092799','1509292800000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092799','1509292800000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092799','1509292800000')">[New]</a></center></td><td><center></center></td><td><select name="504404665795260590_d_30_d_1509292800000_d_2017092799_d_19890519001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665795260590_d_30_d_1509292800000_d_2017092799_d_19890519001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665795260590_d_30_d_1509292800000_d_2017092799_d_19890519001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665795260590_d_30_d_1509292800000_d_2017092799_d_19890519001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665795260590_d_30_d_1509292800000_d_2017092799_d_19890519001_oVerIdx" value="-" type="text"></td><td><select name="504404665795260590_d_30_d_1509292800000_d_2017092799_d_19890519001_status" onkeydown="javascript:fnTrapKD()" id="504404665795260590_d_30_d_1509292800000_d_2017092799_d_19890519001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665795260590_d_30_d_1509292800000_d_2017092799_d_19890519001_reason" onkeydown="javascript:fnTrapKD()" id="504404665795260590_d_30_d_1509292800000_d_2017092799_d_19890519001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665795260590_d_30_d_1509292800000_d_2017092799_d_19890519001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665795260590_d_30_d_1509292800000_d_2017092799_d_19890519001" value="504404665795260590_d_30_d_1509292800000_d_2017092799_d_19890519001" type="checkbox"><input size="4" name="504404665795260590_d_30_d_1509292800000_d_2017092799_d_19890519001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">150</td><td class="Freeze">Mon,<br>30/10/2017&nbsp;</td><td class="Freeze">19930325001</td><td class="Freeze">Dita Ayuningtyas Fitriani</td><td class="Freeze">08:00</td><td class="Freeze">12:00</td><td class="Freeze">13:00</td><td class="Freeze">17:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665865735931_d_30_d_1509292800000_d_2017092798_d_19930325001_symbol" value="P8" size="2" title="08:00-17:00&nbsp;[12:00&nbsp;13:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092798','1509292800000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092798','1509292800000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092798','1509292800000')">[New]</a></center></td><td><center></center></td><td><select name="504404665865735931_d_30_d_1509292800000_d_2017092798_d_19930325001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665865735931_d_30_d_1509292800000_d_2017092798_d_19930325001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665865735931_d_30_d_1509292800000_d_2017092798_d_19930325001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665865735931_d_30_d_1509292800000_d_2017092798_d_19930325001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665865735931_d_30_d_1509292800000_d_2017092798_d_19930325001_oVerIdx" value="-" type="text"></td><td><select name="504404665865735931_d_30_d_1509292800000_d_2017092798_d_19930325001_status" onkeydown="javascript:fnTrapKD()" id="504404665865735931_d_30_d_1509292800000_d_2017092798_d_19930325001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665865735931_d_30_d_1509292800000_d_2017092798_d_19930325001_reason" onkeydown="javascript:fnTrapKD()" id="504404665865735931_d_30_d_1509292800000_d_2017092798_d_19930325001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665865735931_d_30_d_1509292800000_d_2017092798_d_19930325001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665865735931_d_30_d_1509292800000_d_2017092798_d_19930325001" value="504404665865735931_d_30_d_1509292800000_d_2017092798_d_19930325001" type="checkbox"><input size="4" name="504404665865735931_d_30_d_1509292800000_d_2017092798_d_19930325001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">151</td><td class="Freeze">Tue,<br>31/10/2017&nbsp;</td><td class="Freeze">19750922001</td><td class="Freeze">Dewa Gede Darmika</td><td class="Freeze">09:00</td><td class="Freeze">13:00</td><td class="Freeze">14:00</td><td class="Freeze">18:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665865736250_d_31_d_1509379200000_d_2017092800_d_19750922001_symbol" value="P9" size="2" title="09:00-18:00&nbsp;[13:00&nbsp;14:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092800','1509379200000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092800','1509379200000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092800','1509379200000')">[New]</a></center></td><td><center></center></td><td><select name="504404665865736250_d_31_d_1509379200000_d_2017092800_d_19750922001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665865736250_d_31_d_1509379200000_d_2017092800_d_19750922001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665865736250_d_31_d_1509379200000_d_2017092800_d_19750922001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665865736250_d_31_d_1509379200000_d_2017092800_d_19750922001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665865736250_d_31_d_1509379200000_d_2017092800_d_19750922001_oVerIdx" value="-" type="text"></td><td><select name="504404665865736250_d_31_d_1509379200000_d_2017092800_d_19750922001_status" onkeydown="javascript:fnTrapKD()" id="504404665865736250_d_31_d_1509379200000_d_2017092800_d_19750922001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665865736250_d_31_d_1509379200000_d_2017092800_d_19750922001_reason" onkeydown="javascript:fnTrapKD()" id="504404665865736250_d_31_d_1509379200000_d_2017092800_d_19750922001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665865736250_d_31_d_1509379200000_d_2017092800_d_19750922001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665865736250_d_31_d_1509379200000_d_2017092800_d_19750922001" value="504404665865736250_d_31_d_1509379200000_d_2017092800_d_19750922001" type="checkbox"><input size="4" name="504404665865736250_d_31_d_1509379200000_d_2017092800_d_19750922001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">152</td><td class="Freeze">Tue,<br>31/10/2017&nbsp;</td><td class="Freeze">19820202001</td><td class="Freeze">Arif Eko Cahyono</td><td class="Freeze">09:00</td><td class="Freeze">13:00</td><td class="Freeze">14:00</td><td class="Freeze">18:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665865736287_d_31_d_1509379200000_d_2017092802_d_19820202001_symbol" value="P9" size="2" title="09:00-18:00&nbsp;[13:00&nbsp;14:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092802','1509379200000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092802','1509379200000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092802','1509379200000')">[New]</a></center></td><td><center></center></td><td><select name="504404665865736287_d_31_d_1509379200000_d_2017092802_d_19820202001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665865736287_d_31_d_1509379200000_d_2017092802_d_19820202001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665865736287_d_31_d_1509379200000_d_2017092802_d_19820202001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665865736287_d_31_d_1509379200000_d_2017092802_d_19820202001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665865736287_d_31_d_1509379200000_d_2017092802_d_19820202001_oVerIdx" value="-" type="text"></td><td><select name="504404665865736287_d_31_d_1509379200000_d_2017092802_d_19820202001_status" onkeydown="javascript:fnTrapKD()" id="504404665865736287_d_31_d_1509379200000_d_2017092802_d_19820202001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665865736287_d_31_d_1509379200000_d_2017092802_d_19820202001_reason" onkeydown="javascript:fnTrapKD()" id="504404665865736287_d_31_d_1509379200000_d_2017092802_d_19820202001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665865736287_d_31_d_1509379200000_d_2017092802_d_19820202001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665865736287_d_31_d_1509379200000_d_2017092802_d_19820202001" value="504404665865736287_d_31_d_1509379200000_d_2017092802_d_19820202001" type="checkbox"><input size="4" name="504404665865736287_d_31_d_1509379200000_d_2017092802_d_19820202001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">153</td><td class="Freeze">Tue,<br>31/10/2017&nbsp;</td><td class="Freeze">19870603001</td><td class="Freeze">Moses Sangga Mila</td><td class="Freeze">08:00</td><td class="Freeze">12:00</td><td class="Freeze">13:00</td><td class="Freeze">17:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665865736276_d_31_d_1509379200000_d_2017092801_d_19870603001_symbol" value="P8" size="2" title="08:00-17:00&nbsp;[12:00&nbsp;13:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092801','1509379200000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092801','1509379200000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092801','1509379200000')">[New]</a></center></td><td><center></center></td><td><select name="504404665865736276_d_31_d_1509379200000_d_2017092801_d_19870603001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665865736276_d_31_d_1509379200000_d_2017092801_d_19870603001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665865736276_d_31_d_1509379200000_d_2017092801_d_19870603001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665865736276_d_31_d_1509379200000_d_2017092801_d_19870603001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665865736276_d_31_d_1509379200000_d_2017092801_d_19870603001_oVerIdx" value="-" type="text"></td><td><select name="504404665865736276_d_31_d_1509379200000_d_2017092801_d_19870603001_status" onkeydown="javascript:fnTrapKD()" id="504404665865736276_d_31_d_1509379200000_d_2017092801_d_19870603001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665865736276_d_31_d_1509379200000_d_2017092801_d_19870603001_reason" onkeydown="javascript:fnTrapKD()" id="504404665865736276_d_31_d_1509379200000_d_2017092801_d_19870603001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665865736276_d_31_d_1509379200000_d_2017092801_d_19870603001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665865736276_d_31_d_1509379200000_d_2017092801_d_19870603001" value="504404665865736276_d_31_d_1509379200000_d_2017092801_d_19870603001" type="checkbox"><input size="4" name="504404665865736276_d_31_d_1509379200000_d_2017092801_d_19870603001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">154</td><td class="Freeze">Tue,<br>31/10/2017&nbsp;</td><td class="Freeze">19890519001</td><td class="Freeze">Khoirul Huda</td><td class="Freeze">09:00</td><td class="Freeze">13:00</td><td class="Freeze">14:00</td><td class="Freeze">18:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665795260590_d_31_d_1509379200000_d_2017092799_d_19890519001_symbol" value="P9" size="2" title="09:00-18:00&nbsp;[13:00&nbsp;14:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092799','1509379200000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092799','1509379200000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092799','1509379200000')">[New]</a></center></td><td><center></center></td><td><select name="504404665795260590_d_31_d_1509379200000_d_2017092799_d_19890519001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665795260590_d_31_d_1509379200000_d_2017092799_d_19890519001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665795260590_d_31_d_1509379200000_d_2017092799_d_19890519001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665795260590_d_31_d_1509379200000_d_2017092799_d_19890519001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665795260590_d_31_d_1509379200000_d_2017092799_d_19890519001_oVerIdx" value="-" type="text"></td><td><select name="504404665795260590_d_31_d_1509379200000_d_2017092799_d_19890519001_status" onkeydown="javascript:fnTrapKD()" id="504404665795260590_d_31_d_1509379200000_d_2017092799_d_19890519001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665795260590_d_31_d_1509379200000_d_2017092799_d_19890519001_reason" onkeydown="javascript:fnTrapKD()" id="504404665795260590_d_31_d_1509379200000_d_2017092799_d_19890519001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665795260590_d_31_d_1509379200000_d_2017092799_d_19890519001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665795260590_d_31_d_1509379200000_d_2017092799_d_19890519001" value="504404665795260590_d_31_d_1509379200000_d_2017092799_d_19890519001" type="checkbox"><input size="4" name="504404665795260590_d_31_d_1509379200000_d_2017092799_d_19890519001_ovId" value="0" type="hidden"></td></tr><tr class="GridviewScrollItem"><td class="Freeze">155</td><td class="Freeze">Tue,<br>31/10/2017&nbsp;</td><td class="Freeze">19930325001</td><td class="Freeze">Dita Ayuningtyas Fitriani</td><td class="Freeze">08:00</td><td class="Freeze">12:00</td><td class="Freeze">13:00</td><td class="Freeze">17:00</td><td class="Freeze"><font color="#CC0000">-</font></td><td class="Freeze">-</td><td class="Freeze">-</td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><font color="#CC0000">-</font></td><td><input class="masterTooltip" onblur="javascript:checkSymbol(this)" id="inputName" name="504404665865735931_d_31_d_1509379200000_d_2017092798_d_19930325001_symbol" value="P8" size="2" title="08:00-17:00&nbsp;[12:00&nbsp;13:00]" type="text"><br><a href="javascript:cmdEditAttendace('2017092798','1509379200000')"><font color="#6666FFF">Edit</font></a> || <a href="javascript:cmdEditAttendaceManual('2017092798','1509379200000')"><font color="#6666FFF">Edit Duty</font></a></td><td><center><a href="javascript:cmdNewLeave('2017092798','1509379200000')">[New]</a></center></td><td><center></center></td><td><select name="504404665865735931_d_31_d_1509379200000_d_2017092798_d_19930325001_oVForm" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Draft</option>
	<option value="1">To Be Approved</option>
	<option value="2">Final</option>
	<option value="3">Revised</option>
	<option value="4">Proceed</option>
	<option value="5">Closed</option>
	<option value="6">Cancelled</option>
	<option value="7">Posted</option>
	<option value="8">Paid</option>
</select>
</td><td><select name="504404665865735931_d_31_d_1509379200000_d_2017092798_d_19930325001_allwance" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Food</option>
	<option value="1">Money</option>
</select>
</td><td><select name="504404665865735931_d_31_d_1509379200000_d_2017092798_d_19930325001_paid" onkeydown="javascript:fnTrapKD()">
	<option value="" selected="">select...</option>
	<option value="0">Salary</option>
	<option value="1">Day Off</option>
</select>
</td><td><input size="4" name="504404665865735931_d_31_d_1509379200000_d_2017092798_d_19930325001_NetOv" value="-" type="text"></td><td><input size="4" name="504404665865735931_d_31_d_1509379200000_d_2017092798_d_19930325001_oVerIdx" value="-" type="text"></td><td><select name="504404665865735931_d_31_d_1509379200000_d_2017092798_d_19930325001_status" onkeydown="javascript:fnTrapKD()" id="504404665865735931_d_31_d_1509379200000_d_2017092798_d_19930325001" disabled="" class="elementForm">
	<option value="0" selected="">Not Prosses</option>
	<option value="1">Late</option>
	<option value="2">Absence</option>
	<option value="3">Only IN</option>
	<option value="4">Only OUT</option>
	<option value="5">Early Out/Home</option>
	<option value="6">Late Early</option>
	<option value="7">OK</option>
</select>
</td><td><select name="504404665865735931_d_31_d_1509379200000_d_2017092798_d_19930325001_reason" onkeydown="javascript:fnTrapKD()" id="504404665865735931_d_31_d_1509379200000_d_2017092798_d_19930325001_reasonSelect">
	<option value="" selected="">select...</option>
</select>
</td><td><textarea name="504404665865735931_d_31_d_1509379200000_d_2017092798_d_19930325001_note" placeholder="saat mengisi note, reason tidak boleh A" cols="15"></textarea></td><td><input name="userSelect" id="userSelect504404665865735931_d_31_d_1509379200000_d_2017092798_d_19930325001" value="504404665865735931_d_31_d_1509379200000_d_2017092798_d_19930325001" type="checkbox"><input size="4" name="504404665865735931_d_31_d_1509379200000_d_2017092798_d_19930325001_ovId" value="0" type="hidden"></td></tr></tbody></table>
        <script type="text/javascript" src="<%= approot%>/javascripts/jquery.min.js"></script>
    <script type="text/javascript" src="<%= approot%>/javascripts/jquery-ui.min.js"></script>
  <script type="text/javascript" src="<%= approot%>/javascripts/gridviewScroll.min.js"></script>
    <link href="<%= approot%>/stylesheets/GridviewScroll.css" rel="stylesheet" />
  <script type="text/javascript">
	    $(document).ready(function () {
	        gridviewScroll();
	    });
            <%
                int freesize=4;
                if(attdConfig.getConfigurasiReportScheduleDaily() == I_Atendance.CONFIGURASI_I_REPORT_DAILY_SCHEDULE_AND_CEK_BOX_ADA_DIBELAKANG){
                    freesize = 7;
                }
            %>
	    function gridviewScroll() {
	        gridView1 = $('#GridView1').gridviewScroll({
                width: 1310,
                height: 500,
                railcolor: "##33AAFF",
                barcolor: "#CDCDCD",
                barhovercolor: "#606060",
                bgcolor: "##33AAFF",
                freezesize: <%=freesize%>,
                arrowsize: 30,
                varrowtopimg: "<%=approot%>/images/arrowvt.png",
                varrowbottomimg: "<%=approot%>/images/arrowvb.png",
                harrowleftimg: "<%=approot%>/images/arrowhl.png",
                harrowrightimg: "<%=approot%>/images/arrowhr.png",
                headerrowcount: 2,
                railsize: 16,
                barsize: 15
            });
	    }
	</script>
    </body>
</html>
