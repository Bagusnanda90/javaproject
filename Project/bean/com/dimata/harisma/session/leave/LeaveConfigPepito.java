package com.dimata.harisma.session.leave;

import com.dimata.harisma.entity.attendance.PstEmpSchedule;
import com.dimata.harisma.entity.employee.Employee;
import com.dimata.harisma.entity.employee.PstEmployee;
import com.dimata.harisma.entity.leave.AlAutomaticStart;
import com.dimata.harisma.utility.service.presence.RunAutomaticEntileAl;
import com.dimata.harisma.entity.leave.I_Leave;
import com.dimata.harisma.entity.masterdata.Department;
import com.dimata.harisma.entity.masterdata.Division;
import com.dimata.harisma.entity.masterdata.EmpCategory;
import com.dimata.harisma.entity.masterdata.Level;
import com.dimata.harisma.entity.masterdata.PstDepartment;
import com.dimata.harisma.entity.masterdata.PstDivision;
import com.dimata.harisma.entity.masterdata.PstEmpCategory;
import com.dimata.harisma.entity.masterdata.PstLevel;
import com.dimata.harisma.entity.masterdata.PstPeriod;
import com.dimata.harisma.entity.masterdata.Position;
import com.dimata.harisma.entity.masterdata.PstPosition;
import com.dimata.harisma.entity.masterdata.PstPublicHolidays;
import com.dimata.harisma.entity.masterdata.PstScheduleCategory;
import com.dimata.harisma.entity.masterdata.PstScheduleSymbol;
import com.dimata.harisma.entity.masterdata.PstSection;
import com.dimata.harisma.entity.masterdata.PublicHolidays;
import com.dimata.harisma.entity.masterdata.ScheduleCategory;
import com.dimata.harisma.entity.masterdata.ScheduleSymbol;
import com.dimata.harisma.entity.masterdata.Section;
import com.dimata.harisma.session.attendance.SessEmpSchedule;
import com.dimata.harisma.entity.attendance.PstAlStockManagement;
import com.dimata.harisma.entity.attendance.AlStockManagement;
import com.dimata.harisma.entity.attendance.AlStockTaken;
import com.dimata.harisma.entity.attendance.DpStockManagement;
import com.dimata.harisma.entity.attendance.DpStockTaken;
import com.dimata.harisma.entity.attendance.LlStockTaken;
import com.dimata.harisma.entity.leave.LeaveAlClosing;
import com.dimata.harisma.entity.leave.LeaveApplication;
import com.dimata.harisma.entity.leave.LeaveConfigurationParameter;
import com.dimata.harisma.entity.masterdata.PstMappingPosition;
import com.dimata.harisma.entity.masterdata.PstTopPosition;
import com.dimata.harisma.entity.masterdata.leaveconfiguration.LeaveConfigurationMain;
import com.dimata.harisma.entity.masterdata.leaveconfiguration.PstLeaveConfigurationMain;
import com.dimata.system.entity.*;
import com.dimata.qdep.db.DBException;
import com.dimata.qdep.db.DBHandler;
import com.dimata.qdep.db.DBResultSet;
import com.dimata.util.DateCalc;
import com.dimata.util.Formater;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;
import com.dimata.harisma.entity.overtime.*;
import com.dimata.harisma.entity.search.SrcLeaveAppAlClosing;
import com.dimata.harisma.session.attendance.SessLeaveManagement;

/**
 * Konfiguration for KTI
 * @author kartika 
 * @notes
 * <TABLE FRAME=VOID CELLSPACING=0 COLS=11 RULES=NONE BORDER=0>
<COLGROUP><COL WIDTH=141><COL WIDTH=99><COL WIDTH=114><COL WIDTH=110><COL WIDTH=254><COL WIDTH=248><COL WIDTH=220><COL WIDTH=64><COL WIDTH=64><COL WIDTH=64><COL WIDTH=64></COLGROUP>
<TBODY>
<TR>
<TD WIDTH=141 HEIGHT=25 ALIGN=LEFT BGCOLOR="#99CC00"><B><FONT FACE="Calibri" SIZE=4 COLOR="#000000">Use Cases Analysis</FONT></B></TD>
<TD WIDTH=99 ALIGN=LEFT BGCOLOR="#99CC00"><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD WIDTH=114 ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD WIDTH=110 ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD WIDTH=254 ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD WIDTH=248 ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD WIDTH=220 ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD WIDTH=64 ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD WIDTH=64 ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD WIDTH=64 ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD WIDTH=64 ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
</TR>
<TR>
<TD HEIGHT=19 ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
</TR>
<TR>
<TD HEIGHT=24 ALIGN=LEFT><B><FONT FACE="Calibri" SIZE=4 COLOR="#000000">Leave Approval</FONT></B></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
</TR>
<TR>
<TD HEIGHT=19 ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
</TR>
<TR>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-right: 1px solid #000000" ROWSPAN=2 HEIGHT=39 ALIGN=CENTER VALIGN=MIDDLE><B><FONT FACE="Calibri" COLOR="#000000">Applier</FONT></B></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER><B><FONT FACE="Calibri" COLOR="#000000">Manager</FONT></B></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER><B><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></B></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000" ALIGN=CENTER><B><FONT FACE="Calibri" COLOR="#000000">Division</FONT></B></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-right: 1px solid #000000" COLSPAN=2 ALIGN=CENTER><B><FONT FACE="Calibri" COLOR="#000000">Approval by</FONT></B></TD>
<TD ALIGN=LEFT><BR></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
</TR>
<TR>
<TD STYLE="border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER><B><FONT FACE="Calibri" COLOR="#000000">HOD </FONT></B></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER><B><FONT FACE="Calibri" COLOR="#000000">Linked HOD</FONT></B></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER><B><FONT FACE="Calibri" COLOR="#000000">Div.Head </FONT></B></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER><B><FONT FACE="Calibri" COLOR="#000000">Approved ( w/ email)</FONT></B></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER><B><FONT FACE="Calibri" COLOR="#000000">HR Approved ( w/ email)</FONT></B></TD>
<TD ALIGN=LEFT><BR></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
</TR>
<TR>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" HEIGHT=19 ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
</TR>
<TR>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" HEIGHT=20 ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000">Staff-Ast Man.</FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000">ada</FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000">-</FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000">ada</FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000">HOD, Div.Head</FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000">Staff, Spv, Ast.Man, Man, Dir. HR</FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
</TR>
<TR>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" HEIGHT=20 ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000">Staff- Ast Man</FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000">ada</FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000">-</FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000">tidak ada</FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000">HOD,HR Man, HR.Dir</FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000">Staff, Spv, Ast.Man, Man, Dir. HR</FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000">Man.= Position with  Manager Level</FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
</TR>
<TR>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" HEIGHT=20 ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000">Staff &ndash; Ast Man.</FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000">tidak ada</FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000">ada</FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000">ada</FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000">Link.HOD, Div.Head</FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000">Staff, Spv, Ast.Man, Man, Dir. HR</FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000">Director = Position with Director Level</FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
</TR>
<TR>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" HEIGHT=20 ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000">Staff &ndash; Ast Man.</FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000">tidak ada</FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000">ada</FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000">tidak ada</FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000">Link.HOD, Hr Man,HR.Dir</FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000">Staff, Spv, Ast.Man, Man, Dir. HR</FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
</TR>
<TR>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" HEIGHT=20 ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000">Staff &ndash; Ast Man.</FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000">tidak ada</FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000">tidak ada</FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000">ada</FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000">Div. Head, Hr Man,HR.Dir</FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000">Staff, Spv, Ast.Man, Man, Dir. HR</FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
</TR>
<TR>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" HEIGHT=20 ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000">Staff &ndash; Ast Man.</FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000">tidak ada</FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000">tidak ada</FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000">tidak ada</FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000">GM, HR Man, HR.Dir</FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000">Staff, Spv, Ast.Man, Man, Dir. HR</FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
</TR>
<TR>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" HEIGHT=20 ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000">Manager</FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000">tidak ada</FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000">ada</FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000">ada</FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000">Linked HOD, Div.Head, </FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000">Staff, Spv, Ast.Man, Man, Dir. HR</FONT></TD>
<TD ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
</TR>
<TR>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" HEIGHT=20 ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000">Manager</FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000">ada</FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000">ada</FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000">HOD, Div.Head, </FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000">Staff, Spv, Ast.Man, Man, Dir. HR</FONT></TD>
<TD ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
</TR>
<TR>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" HEIGHT=20 ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000">Manager</FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000">tidak ada</FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000">tidak ada</FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000">ada</FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000">Div.Head, HR.Dir, GM</FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000">Staff, Spv, Ast.Man, Man, Dir. HR</FONT></TD>
<TD ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
</TR>
<TR>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" HEIGHT=20 ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000">Manager</FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000">tidak ada</FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000">tidak ada</FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000">tidak ada</FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000">GM, HR.Dir</FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000">Staff, Spv, Ast.Man, Man, Dir. HR</FONT></TD>
<TD ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
</TR>
<TR>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" HEIGHT=20 ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000">HOD</FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000">ada</FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000">Div.Head, HR.Dir, GM</FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000">Staff, Spv, Ast.Man, Man, Dir. HR</FONT></TD>
<TD ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
</TR>
<TR>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" HEIGHT=20 ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000">HOD</FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000">tidak ada</FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000">GM, HR.Dir</FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000">Staff, Spv, Ast.Man, Man, Dir. HR</FONT></TD>
<TD ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
</TR>
<TR>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" HEIGHT=20 ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000">Director</FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000">tidak ada</FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000">GM, HR.Dir</FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000">Staff, Spv, Ast.Man, Man, Dir. HR</FONT></TD>
<TD ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
</TR>
<TR>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" HEIGHT=20 ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000">Director</FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000">ada</FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000">Div.Head, HR.Dir, GM</FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000">Staff, Spv, Ast.Man, Man, Dir. HR</FONT></TD>
<TD ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
</TR>
<TR>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" HEIGHT=20 ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000">Div. Head</FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000">GM, HR.Dir</FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000">Staff, Spv, Ast.Man, Man, Dir. HR</FONT></TD>
<TD ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
</TR>
<TR>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" HEIGHT=20 ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000">GM</FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000">HR Dir</FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000">Staff, Spv, Ast.Man, Man, Dir. HR</FONT></TD>
<TD ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
</TR>
<TR>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" HEIGHT=19 ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
</TR>
<TR>
<TD HEIGHT=19 ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
</TR>
<TR>
<TD HEIGHT=19 ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><BR></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
</TR>
<TR>
<TD HEIGHT=24 ALIGN=LEFT><B><FONT FACE="Calibri" SIZE=4 COLOR="#000000">Overtime Approval</FONT></B></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
</TR>
<TR>
<TD HEIGHT=19 ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
</TR>
<TR>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-right: 1px solid #000000" ROWSPAN=2 HEIGHT=37 ALIGN=CENTER VALIGN=MIDDLE><B><FONT FACE="Calibri" COLOR="#000000">Applier</FONT></B></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER><B><FONT FACE="Calibri" COLOR="#000000">Manager</FONT></B></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER><B><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></B></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000" ALIGN=CENTER><B><FONT FACE="Calibri" COLOR="#000000">Division</FONT></B></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=LEFT><BR></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" COLSPAN=2 ALIGN=CENTER><B><FONT FACE="Calibri" COLOR="#000000">Approval by</FONT></B></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
</TR>
<TR>
<TD STYLE="border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER><B><FONT FACE="Calibri" COLOR="#000000">HOD </FONT></B></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER><B><FONT FACE="Calibri" COLOR="#000000">Linked HOD</FONT></B></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER><B><FONT FACE="Calibri" COLOR="#000000">Div.Head</FONT></B></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER><B>Requested by</B></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER><B><FONT FACE="Calibri" COLOR="#000000">Approved by (w/ email)</FONT></B></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER><B><FONT FACE="Calibri" COLOR="#000000">GM Approval ( w/ email)-FINAL</FONT></B></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
</TR>
<TR>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" HEIGHT=19 ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=LEFT><BR></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
</TR>
<TR>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" HEIGHT=19 ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000">Staff &ndash; Ast Man.</FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER><FONT FACE="Calibri" COLOR="#000000">ada</FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER><FONT FACE="Calibri" COLOR="#000000">-</FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER><FONT FACE="Calibri" COLOR="#000000">ada</FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=LEFT> Supervisor, HOD, Manager, Dir</TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000">HOD, Div.Head</FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER><FONT FACE="Calibri" COLOR="#000000">HR Dir, GM</FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
</TR>
<TR>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" HEIGHT=19 ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000">Staff &ndash; Ast Man.</FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER><FONT FACE="Calibri" COLOR="#000000">ada</FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER><FONT FACE="Calibri" COLOR="#000000">-</FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER><FONT FACE="Calibri" COLOR="#000000">tidak ada</FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=LEFT> Supervisor, HOD, Manager</TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000">HOD, HR Dir, GM</FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER><FONT FACE="Calibri" COLOR="#000000">HR Dir, GM</FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
</TR>
<TR>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" HEIGHT=19 ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000">Staff &ndash; Ast Man.</FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER><FONT FACE="Calibri" COLOR="#000000">tidak ada</FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER><FONT FACE="Calibri" COLOR="#000000">ada</FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER><FONT FACE="Calibri" COLOR="#000000">ada</FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=LEFT> Supervisor, Link.HOD, Dir</TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000">Link.HOD, Div.Head</FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER><FONT FACE="Calibri" COLOR="#000000">HR Dir, GM</FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
</TR>
<TR>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" HEIGHT=19 ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000">Staff &ndash; Ast Man.</FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER><FONT FACE="Calibri" COLOR="#000000">tidak ada</FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER><FONT FACE="Calibri" COLOR="#000000">ada</FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER><FONT FACE="Calibri" COLOR="#000000">tidak ada</FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=LEFT> Supervisor, Link.HOD,Dir</TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000">Link.HOD, HR Dir, GM</FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER><FONT FACE="Calibri" COLOR="#000000">HR Dir, GM</FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
</TR>
<TR>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" HEIGHT=19 ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000">Staff &ndash; Ast Man.</FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER><FONT FACE="Calibri" COLOR="#000000">tidak ada</FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER><FONT FACE="Calibri" COLOR="#000000">tidak ada</FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER><FONT FACE="Calibri" COLOR="#000000">ada</FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=LEFT> Supervisor, Div.Head.</TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000">Div.Head, HR Dir, GM</FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER><FONT FACE="Calibri" COLOR="#000000">HR Dir, GM</FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
</TR>
<TR>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" HEIGHT=19 ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000">Staff &ndash; Ast Man.</FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER><FONT FACE="Calibri" COLOR="#000000">tidak ada</FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER><FONT FACE="Calibri" COLOR="#000000">tidak ada</FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER><FONT FACE="Calibri" COLOR="#000000">tidak ada</FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=LEFT> HR.Man, HR.Dir</TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000">HR.Dir, GM</FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER><FONT FACE="Calibri" COLOR="#000000">HR Dir, GM</FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
</TR>
<TR>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" HEIGHT=19 ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000">Manager</FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER><FONT FACE="Calibri" COLOR="#000000">tidak ada</FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER><FONT FACE="Calibri" COLOR="#000000">ada</FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=LEFT> Div.Head, HR Dir</TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000">Div.Head, HR Dir, GM</FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER><FONT FACE="Calibri" COLOR="#000000">HR Dir, GM</FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
</TR>
<TR>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" HEIGHT=19 ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000">Manager</FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER><FONT FACE="Calibri" COLOR="#000000">ada</FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER><FONT FACE="Calibri" COLOR="#000000">ada</FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=LEFT> HOD, Div.Head</TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000">HOD, Div.Head</FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER><FONT FACE="Calibri" COLOR="#000000">HR Dir, GM</FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
</TR>
<TR>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" HEIGHT=19 ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000">Manager</FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER><FONT FACE="Calibri" COLOR="#000000">tidak ada</FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER><FONT FACE="Calibri" COLOR="#000000">tidak ada</FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=LEFT> HR. Dir, GM</TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000">HR Dir, GM</FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER><FONT FACE="Calibri" COLOR="#000000">HR Dir, GM</FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
</TR>
<TR>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" HEIGHT=19 ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000">Manager</FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER><FONT FACE="Calibri" COLOR="#000000">ada</FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER><FONT FACE="Calibri" COLOR="#000000">tidak ada</FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=LEFT> HOD, HR. Dir</TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000">HOD, HR Dir, GM</FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER><FONT FACE="Calibri" COLOR="#000000">HR Dir, GM</FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
</TR>
<TR>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" HEIGHT=19 ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000">HOD</FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER><FONT FACE="Calibri" COLOR="#000000">ada</FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=LEFT> Div.Head, HR Dir</TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000">HOD, Div.Head</FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER><FONT FACE="Calibri" COLOR="#000000">HR Dir, GM</FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
</TR>
<TR>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" HEIGHT=19 ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000">HOD</FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER><FONT FACE="Calibri" COLOR="#000000">tidak ada</FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=LEFT> HR. Dir, GM</TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000">HR Dir, GM</FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER><FONT FACE="Calibri" COLOR="#000000">HR Dir, GM</FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
</TR>
<TR>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" HEIGHT=19 ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000">Director</FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER><FONT FACE="Calibri" COLOR="#000000">ada</FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=LEFT> Div.Head, HR Dir</TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000">Div.Head, HR Dir, GM</FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER><FONT FACE="Calibri" COLOR="#000000">HR Dir, GM</FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
</TR>
<TR>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" HEIGHT=19 ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000">Director</FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER><FONT FACE="Calibri" COLOR="#000000">tidak ada</FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=LEFT> HR. Dir, GM</TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000">HR Dir, GM</FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER><FONT FACE="Calibri" COLOR="#000000">HR Dir, GM</FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
</TR>
<TR>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" HEIGHT=19 ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000">Div. Head</FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=LEFT> HR. Dir, GM</TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000">HR Dir, GM</FONT></TD>
<TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER><FONT FACE="Calibri" COLOR="#000000">HR Dir, GM</FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
<TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
</TR>
</TBODY>
</TABLE>
 */
public class LeaveConfigPepito implements I_Leave {

    private final int minTimeToHasDP = 8; //Dalam jam  ; Minimum jam kerja untuk memperoleh DP
    private final int iDayAYear = 365;
    //update by satrya 2013-11-22
    private final int iYearsExtraAl=1;
    private int iEntitleType = AL_ENTITLE_BY_COMMENCING_PERIODE;
    private int iALEarnedBy = AL_EARNED_BY_TOTAL;
    private boolean isALStockMinus = false;//update by satrya 2012-08-10 true 
    private boolean isLLStockMinus = false;
    //update satrya 2012-10-08
    private boolean isDPStockMinus = false;
    private int iALExpirationDay = iDayAYear;
    private int iALExpWarning = iDayAYear - 60;
    private int iMinLLTakenAnual = 0;
    private int iMaxLLTakenAnual = 0;
    //private int iLLExpirationDay = iDayAYear * iIntervalLLs[0] ;
    private int iLLExpirationDay = iDayAYear * (iIntervalLLs[0] / 12);
    private int iLLExpWarning = iLLExpirationDay - 90;

    /* in month, minimal berapa bulan bekerja untuk mendapatkan stock AL */
    private int iMinWorkToGetAnual = /* update by satrya 2013-11-09 0*/ 0;   // month
    /* in month, minimal berapa bulan bekerja untuk boleh mengambil cuti */
    private int iMinWorkCanGetAnnual = 0; // month

    /*Tanggal dan bulan closing jika memakai Leave by Period*/
    private int iDateClosingAnnual = 31; //Tanggal closing
    private int iMonthClosingAnnual = 12; //Bulan closing
    private int date = iDateClosingAnnual;
    private int month = iMonthClosingAnnual;

    /* Untuk mengecek batas tanggal untu menentukan periode bulanan, seperti bila limit adalah 17,
     * kemudian karyawan tersebut masuk tanggal 15,karena 15 kurang dari 17 maka akan dapat periode bulana pada bulan sekarang,
     * Sedangkan bila commencing date adalah 19, karena 19 lebih besar dari 17, maka periode bulanan yang didapt adalah periode berikutnya
     */
    private int dateLimit = 15; /* bulan januari */

    /* Periode untuk mendapatkan entitle Al , dimana mendapatkan al ketika awal periode yaitu 26 Januari*/
    private String startAnnualLeavePeriod = ""; //month-date // PEriode 26 januari    
    public static final int LEVEL_STAFF = 0;
    public static final int LEVEL_SUPERVISOR = 1;
    public static final int LEVEL_ASST_MAN = 2;
    public static final int LEVEL_MANAGER = 3;
    public static final int LEVEL_DIRECTOR = 4;
    public static final int LEVEL_GENERAL = 5;
    
    public static final int LEVEL_JR_AST_MNG = 6;
    public static final int LEVEL_JR_MAN = 7;
    public static final int LEVEL_JUNIOR_SPV = 8;
    public static final int LEVEL_SENIOR_SPV = 9;
    public static final int LEVEL_SENIOR_STAFF = 10;
    private final String[] strLevels = {
        "Magang E1",
        "Staff F3",
        "Staff F2",
        "Staff F1",
        "Supervisor E3",
        "Supervisor E2",
        "Supervisor E1",
        "Assistant Manager D3",
        "Assistant Manager D2",
        "Assistant Manager D1",
        "Manager C3",
        "Manager C2",
        "Manager C1",
        "General Manager B3",
        "General Manager B2",
        "General Manager B1"

    };
    public static final int CATEGORY_APPRENTICESHIP = 0;
    public static final int CATEGORY_CONTRACT = 1;
    public static final int CATEGORY_DAILY_WORKER = 2;
    public static final int CATEGORY_PERMANENT = 3;
    public static final int CATEGORY_SEMI_PERMANENT = 4;
    public static final int CATEGORY_TRAINEE = 5;
    public static final int CATEGORY_EXPATRIAT = 6;
    public static final int CATEGORY_OUTSOURCH = 7;
    //update by satrya 2013-10-28 
    public static final int CATEGORY_PROBATION = 8;
  /*  private final String[] strCategory = {
        "APPRENTICESHIP",
        "Contract",
        "Daily Worker",
        "Permanent",
        "SEMI PERMANENT",
        "Trainee",
        "EXPATRIAT",
        "OUTSOURCH",
        "Probation"
    }; */
     private final String[] strCategory = {
        "Trainee",
        "Daily Worker",
        "Probation",
        "Freelance",
        "Contract",
        "Permanent"
    };
    //public static final int HARDROCK_HOTEL = 0;
    //public static final int SANUR_PARADISE = 1;
    //distribution by month
    private final int[] iValueDistribution = {
        1, //month 1
        1, //month 2
        1, //month 3
        1, //month 4
        1, //month 5
        1, //month 6
        1, //month 7
        1, //month 8
        1, //month 9
        1, //month 10
        1, //month 11
        1 //month 12
    };
    //distribution by month only Expatriat
    private final int[] iValueDistributionExpatriat = {
        1, //month 1
        1, //month 2
        1, //month 3
        1, //month 4
        1, //month 5
        1, //month 6
        1, //month 7
        1, //month 8
        1, //month 9
        2, //month 10
        2, //month 11
        2 //month 12
    };
    public static final int iInterval_6_year = 0;
    public static final int iInterval_8_year = 1;
    public static final int iINTERVAL_LL_HARDROCK_I = 0;
    public static final int iINTERVAL_LL_HARDROCK_II = 1;
    public static final int INTERVAL_AL_HARDROCK = 0;
    private static final int[] iIntervalLLs = {
      /*  6 * 12,
        7 * 12*/
        0 * 12,
        0 * 12
    };
    private static final int[] iIntervalAL = {
        1 * 12
    };
    private final String[] extendeValue = {
        "1",
        "2",
        "3",
        "6",
        "12",
        "0"
    };
    
    //AL Standard
    private int[][] iALEntitles = {
        //"APPRENTICESHIP","CONTRACT","DAILY WORKER","PERMANENT","SEMI PERMANENT","TRAINEE","EXPATRIAT","OUTSOURCH","Probation"
        // update by satrya 2013-10-28 {0, 12, 0, 12, 12, 0, 12, 0}, //Level 0
        {0, 12, 0, 12, 12, 0, 12, 0, 0}, //Level 0
        {0, 12, 0, 12, 12, 0, 12, 0, 0}, //Level 1
        {0, 12, 0, 12, 12, 0, 12, 0, 0}, //Level 2
        {0, 12, 0, 12, 12, 0, 12, 0, 0}, //Level 3
        {0, 12, 0, 12, 12, 0, 12, 0, 0}, //Level 4
        {0, 12, 0, 12, 12, 0, 12, 0, 0}, //Level 5
            //update by satrya 2013-10-29
         {0, 12, 0, 12, 12, 0, 12, 0, 0}, //"Jr Ast Mng",
         {0, 12, 0, 12, 12, 0, 12, 0, 0},   //"Jr. Man",
         {0, 12, 0, 12, 12, 0, 12, 0, 0},  //"Junior Spv",
         {0, 12, 0, 12, 12, 0, 12, 0, 0}, //"Senior Spv",
         {0, 12, 0, 12, 12, 0, 12, 0, 0},  //"Snr Staff"
         
      
         
            
    };
    private int[] iALEntitlesByPepito = 
      
         {0, 0, 0, 0, 12, 12, 0
    };
    private int[][] iALEntitlesExtra = { // tambahan cuti tahunan extra
        //"APPRENTICESHIP","CONTRACT","DAILY WORKER","PERMANENT","SEMI PERMANENT","TRAINEE","EXPATRIAT","OUTSOURCH","Probation"
        // update by satrya 2013-10-28 {0, 3, 0, 3, 3, 0, 3, 0}, //Level 0
        {0, 3, 0, 3, 3, 0, 3, 0, 0}, //Level 0
        {0, 3, 0, 3, 3, 0, 3, 0, 0}, //Level 1
        {0, 3, 0, 3, 3, 0, 3, 0, 0}, //Level 2
        {0, 3, 0, 3, 3, 0, 3, 0, 0}, //Level 3
        {0, 3, 0, 3, 3, 0, 3, 0, 0}, //Level 4
        {0, 3, 0, 3, 3, 0, 3, 0, 0}, //Level 5
            
        {0, 3, 0, 3, 3, 0, 3, 0, 0}, //"Jr Ast Mng",
        {0, 3, 0, 3, 3, 0, 3, 0, 0},   //"Jr. Man",
        {0, 3, 0, 3, 3, 0, 3, 0, 0},  //"Junior Spv",
        {0, 3, 0, 3, 3, 0, 3, 0, 0}, //"Senior Spv",
        {0, 3, 0, 3, 3, 0, 3, 0, 0}  //"Snr Staff"
    };
    //AL ketika memperoleh LL
    private int[][] iALEntitlesByLL = {
        //"APPRENTICESHIP","CONTRACT","DAILY WORKER","PERMANENT","SEMI PERMANENT","TRAINEE","EXPATRIAT","OUTSOURCH","Probation"
        {0, 0, 0, 0, 0, 0, 0, 0, 0}, //Level 0
        {0, 0, 0, 0, 0, 0, 0, 0, 0}, //Level 1
        {0, 0, 0, 0, 0, 0, 0, 0, 0}, //Level 2
        {0, 0, 0, 0, 0, 0, 0, 0, 0}, //Level 3
        {0, 0, 0, 0, 0, 0, 0, 0, 0}, //Level 4
        {0, 0, 0, 0, 0, 0, 0, 0, 0}, //Level 5
            
        //update by satrya 2013-10-29
        {0, 0, 0, 0, 0, 0, 0, 0, 0}, //"Jr Ast Mng",
        {0, 0, 0, 0, 0, 0, 0, 0, 0},   //"Jr. Man",
        {0, 0, 0, 0, 0, 0, 0, 0, 0},  //"Junior Spv",
        {0, 0, 0, 0, 0, 0, 0, 0, 0}, //"Senior Spv",
        {0, 0, 0, 0, 0, 0, 0, 0, 0},  //"Snr Staff"
    };
    //LL Default
    private int[][][] iLLEntitles = {
        //"APPRENTICESHIP","CONTRACT","DAILY WORKER","PERMANENT","SEMI PERMANENT","TRAINEE","EXPATRIAT", "OUTSOURCH","Probation"
        //"In 6 Year","In 7 Year"
        {{0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}}, //Level 0
        {{0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}}, //Level 1
        {{0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}}, //Level 2
        {{0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}}, //Level 3
        {{0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}}, //Level 4
        {{0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}}, //Level 5
            
         //update by satrya 2013-10-29
        {{0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}}, //"Jr Ast Mng",
        {{0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}},   //"Jr. Man",
        {{0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}},  //"Junior Spv",
        {{0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}}, //"Senior Spv",
        {{0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}},  //"Snr Staff"
    };
    private int[][][] iLLValidity = {
        //"APPRENTICESHIP","CONTRACT","DAILY WORKER","PERMANENT","SEMI PERMANENT","TRAINEE","EXPATRIAT","OUTSOURCH","Probation"
        //"In 7 Year","In 8 Year"     100*12 means 100 year = never expired
        {{0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}}, //Level 0
        {{0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}}, //Level 1
        {{0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}}, //Level 2
        {{0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}}, //Level 3
        {{0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}}, //Level 4
        {{0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}}, //Level 5
        
         //update by satrya 2013-10-29
        {{0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}}, //"Jr Ast Mng",
        {{0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}},   //"Jr. Man",
        {{0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}},  //"Junior Spv",
        {{0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}}, //"Senior Spv",
        {{0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}},  //"Snr Staff"
    };
    //DP Standard
    private int[] iDPStandardEntitle = {
        1, //Level 0
        1, //Level 1
        1, //Level 2
        2, //Level 3
        2, //Level 4
        2 //Level 5
    };
    private int[] iDPHolidayOffEntitle = {
        1, //Level 0
        1, //Level 1
        1, //Level 2
        2, //Level 3
        2, //Level 4
        2 //Level 5
    };
    private int[] iDPHolidayPresentEntitle = {
        1, //Level 0
        1, //Level 1
        1, //Level 2
        2, //Level 3
        2, //Level 4
        2 //Level 5
    };
    private int[] iDPBirthdayOffEntitle = {
        0, //Level 0
        0, //Level 1
        0, //Level 2
        0, //Level 3
        0, //Level 4
        0 //Level 5
    };
    private int[] iDPBirthdayPresentEntitle = {
        0, //Level 0
        0, //Level 1
        0, //Level 2
        0, //Level 3
        0, //Level 4
        0 //Level 5
    };
    private int[] iDpValidity = {
        //update 2012-10-12
        3, //Level 0
        3, //Level 1
        3, //Level 2
        3, //Level 3
        3, //Level 4
        3 //Level 5
    };
    int[] approvalNeed = {
        2, //Level 0        -> old = 3
        2, //Level 1        -> old = 3
        2, //Level 2        -> old = 3
        2, //Level 3
        2, //Level 4
        2, //Level 5 //update by satrya 2013-10-29
        2, //Level 6
        2, //Level 7
        2, //Level 8
        2, //Level 9
        2, //Level 10
        2, //Level 11
        2, //Level 12
        2, //Level 13
        2, //Level 14
        2, //Level 15
        2  //Level 16
    };

    //Konstruktor
    public LeaveConfigPepito() {
         //Type entitle, cara penentuan AL dan LL
        //iEntitleType = AL_ENTITLE_BY_COMMENCING;

        //Waktu hidup AL sampai Expired
        //iALExpirationDay = 100*iDayAYear; // no expiration
        //Tempo waktu kemunculan warning message sebelum AL Expired
        //iALExpWarning = 1*30; //DEf 1 bln

        //Minimal LL yang bisa diambil dalam setahun       
        //iMinLLTakenAnual = 0;
        //Maksimal LL yang bisa diambil dalam setahun
        //iMaxLLTakenAnual = 26;
        //Waktu hidup LL sampai Expired
        //iLLExpirationDay = 100*iDayAYear; // no expiration
        //Waktu hidup LL sampai Expired
        //iLLExpWarning = 3*30; //Def 3 bulan

        System.out.println("\nCONFIG ::::::: " + PstSystemProperty.getValueByName("LEAVE_CONFIG"));
        System.out.println(" => Entitle Type      : " + AL_ENTITLE_TYPE[iEntitleType]);
        System.out.println(" => AL Expiration Day : " + iALExpirationDay);
        System.out.println(" => AL Exp Warning    : " + iALExpWarning);
        System.out.println(" => Min LL Taken Anual: " + iMinLLTakenAnual);
        System.out.println(" => Max LL Taken Anual: " + iMaxLLTakenAnual);
        System.out.println(" => LL Expiration Day : " + iLLExpirationDay);
        System.out.println(" => LL Exp Warning    : " + iLLExpWarning);
        //  System.out.println(" => AL getALEntitleAnualLeave(Sample) : "+getALEntitleAnualLeave("Level B","DAILY WORKER"));
        //  System.out.println(" => LL getLLEntile(Sample)            : "+getLLEntile("Level B","CONTRACT",iIntervalLLs[INTERVAL_LL_8_YEAR])+"\n");
        Date dt = new Date();
        dt.setDate(20);
        dt.setMonth(0);
        dt.setYear(2008 - 1900);
        //   System.out.println(" => DP getDPEntitleByDate(Sample)            : "+getDPEntitleByDate("7008",dt)+"\n");
        //Test
        //getDPEntitleByDate("7005",new Date());
    }
/**
     * Set type of AL earned
     * @param al_earn_by  : AL_EARNED_BY_TOTAL=0 or  AL_EARNED_PER_MONTH=1;
     */
    public void setALEarnedBy(int al_earn_by) {
        iALEarnedBy = al_earn_by;
    }

    /**
     * get type of AL earned
     * @return
     */
    public int getAlEarnedBy() {
        return iALEarnedBy;
    }

    /**
     * set if AL stock can be minus ( more AL is taken by employee then earned )
     * @param ALminus
     */
    public void setALStockMinus(boolean ALminus) {
        isALStockMinus = ALminus;
    }

    /**
     * get AL stock minus configuration
     * @return boolean
     */
    public boolean getALStockMinus(AlStockTaken alStockTaken) {
        if(alStockTaken!=null && alStockTaken.getEmployeeId()!=0){
         float stockAl = SessLeaveApplication.getEligibleAnnualLeave(null, alStockTaken);
         //float stockAl = SessLeaveApplication.getEligibleAnnualLeave(null, alStockTaken) - alStockTaken.getTakenQty();
         if(stockAl >= 0){
             return true;
         }
      }
        return isALStockMinus;
       // return isALStockMinus;
    }
    
    public boolean getALStockMinus(float eligible,long oidEmployee) {
        if(eligible >= 0){
             return true;
         }
         return isALStockMinus;
    }

    /**
     * set if LL stock can be minus ( more LL is taken by employee then earned )
     * @param ALminus
     */
    public void setLLStockMinus(boolean LLminus) {
        isLLStockMinus = LLminus;
    }

    /**
     * get AL stock minus configuration
     * @return boolean
     */
    public boolean getLLStockMinus(LlStockTaken llStockTaken,I_Leave leave,float eligbleDay) {
        return isLLStockMinus;
    }
    
    public boolean getLLStockMinus(float eligbleDay){
          return isLLStockMinus;
          
     }
 //update by satrya 2012-10-08   
    /**
     * @param isDPStockMinus the isDPStockMinus to set
     */
    public void setDPStockMinus(boolean DPminus) {
        isDPStockMinus = DPminus;
    }
        /**
     * @return the isDPStockMinus
     */
    public boolean getDPStockMinus(Vector listal,long oidEmployee,DpStockTaken dpStockTaken,float dpTakenPrev) {
       
            float expiredQTY = SessLeaveManagement.getDpExpired(oidEmployee,null);
         if(listal!=null && listal.size()>0){
            for (int j=0; j<listal.size(); j++)
            {
                RepItemLeaveAndDp item = null;
                item = (RepItemLeaveAndDp)listal.get(j);
               
               //float dpQty = item.getDPQty();
              float  curentDpQty = item.getDPQty() - item.getDPTaken();
              float eligbleDay = curentDpQty - item.getDP2BTaken()- expiredQTY - dpStockTaken.getTakenQty() + dpTakenPrev;
                //totalDpTaken = item.getDPTaken();
                //totalDp2BeTaken = item.getDP2BTaken();
                if(eligbleDay >= 0){
                    isDPStockMinus = true;
                }
                
              }
         }
        return isDPStockMinus;
    }

   /**
     * @descr Method for getting number of AL depending on employee Level and Employee type
     * @param level
     * @param employeeType
     * @return
     */
    public float getALEntitleAnualLeaveAlaCommecingPeriode(LeaveAlClosing leaveAlClosing) {
        int iEntitleAL = 0;
        try {
            Date entDtCommencing = Formater.formatDate(leaveAlClosing.getCommencingDate(), "yyyy-MM-dd");
            
            Date entDtCommencingPlusOneYears = (Date) entDtCommencing.clone();
            entDtCommencingPlusOneYears.setYear(entDtCommencingPlusOneYears.getYear()+1);
            
            Date entDtCommencingPlusOneYearsPlus1JanuaryInNewYears = (Date) entDtCommencingPlusOneYears.clone(); //mencari entitle kedua dimana nilainya diambil dari 1 januari dua tahun depan setelah commencing dikurang satu tahun entitle setelah commencing
            entDtCommencingPlusOneYearsPlus1JanuaryInNewYears.setYear(entDtCommencingPlusOneYearsPlus1JanuaryInNewYears.getYear()+1);
            entDtCommencingPlusOneYearsPlus1JanuaryInNewYears.setMonth(0);
            entDtCommencingPlusOneYearsPlus1JanuaryInNewYears.setDate(1);
            
            Date  entDtClosing = Formater.formatDate(leaveAlClosing.getEntitledDate(), "yyyy-MM-dd");
            if   (entDtClosing != null){
                try {
                 entDtClosing = Formater.formatDate(leaveAlClosing.getEntitledDate(), "yyyy-MM-dd");
                }catch (Exception e){}
            } else {
                entDtClosing = Formater.formatDate(leaveAlClosing.getCommencingDate(), "yyyy-MM-dd");
            }
            
            if (entDtClosing.getTime() == entDtCommencing.getTime()){
                iEntitleAL = DateCalc.monthDifference(entDtCommencingPlusOneYears, entDtCommencingPlusOneYearsPlus1JanuaryInNewYears);
            } else {
                iEntitleAL = 12;
            }   
          
          
        } catch (Exception exc) {
            
            System.out.println(exc);
        }
        return iEntitleAL;
    }
 

    /**
     * @descr Method for getting number of AL depending on employee Level and Employee type
     * @param level
     * @param employeeType
     * @return
     */
    public float getALEntitleAnualLeave(String level, String employeeType, int LoSDays, Date commencingDate, Date checkDate) {
        int iEntitleAL = 0;
        try {
            System.out.println("level =" + level);
            System.out.println("type  =" + employeeType);
            int iLevel = getIndexLevel(level);
            int iType = getIndexType(employeeType);
            iEntitleAL = iALEntitlesByPepito[iType];
            if (commencingDate != null && checkDate != null) {
                Calendar cald = Calendar.getInstance();
                cald.setTime(checkDate);
                checkDate.setDate(cald.getActualMaximum(Calendar.DATE));  // set check date to the end of the month sehingga perhitunganan AL berlaku skope bulan.

                if (LoSDays == 0) {
                    LoSDays = (int) ((checkDate.getTime() - commencingDate.getTime()) / (1000 * 60 * 60 * 24));
                }
                if (commencingDate.getYear() < checkDate.getYear()) {
                    // sudah beda tahun berarti akan dapat full                  
                    if (LoSDays >= (365 * 10) && ((commencingDate.getMonth() == checkDate.getMonth()) || (commencingDate.getMonth() < checkDate.getMonth()) ) ) { // length of service >= 10 tahun
                        //update by satrya 2013-02-27
                        //if (LoSDays >= (365 * 10) && ((commencingDate.getMonth() == checkDate.getMonth()) {
                        //update by satrya 2014-03-05
                        //ini akan di update di service center
                        //iEntitleAL = iEntitleAL + iALEntitlesExtra[iLevel][iType];
                        //iEntitleAL = iEntitleAL;
                    }
                } else {
                    if (commencingDate.getYear() == checkDate.getYear()) {
                        if (checkDate.getMonth() >= commencingDate.getMonth()) {
                            // commencing sudah lewat dibanding check date di tahun yg sama => porposional
                            iEntitleAL = (int) (((float) ((checkDate.getMonth() - commencingDate.getMonth()) * iEntitleAL)) / 12.0f);
                        } else {
                            // tahun sama bulan commencing date lebih baru dari check date = belum dapat cuti, karena belum masuk saat itu
                            iEntitleAL = 0;
                        }
                    } else {
                        // tahun berbeda berarti commencing date lebih baru dari check date =belum dapat cuti
                        iEntitleAL = 0;
                    }
                }
            }
        } catch (Exception exc) {
            //iEntitleAL = iALEntitles[0][0];
            System.out.println(exc);
        }
        return iEntitleAL;
    }

    //update by satrya 2013-10-21
    public float getALEntitleAnualLeavePlusTiga(String level, String employeeType, int LoSDays, Date commencingDate, Date checkDate) {
        int iEntitleAL = 0;
        try {
            System.out.println("level =" + level);
            System.out.println("type  =" + employeeType);
            int iLevel = getIndexLevel(level);
            int iType = getIndexType(employeeType);
            //iEntitleAL = iALEntitles[iLevel][iType];
            if (commencingDate != null && checkDate != null) {
                Calendar cald = Calendar.getInstance();
                cald.setTime(checkDate);
                checkDate.setDate(cald.getActualMaximum(Calendar.DATE));  // set check date to the end of the month sehingga perhitunganan AL berlaku skope bulan.

                if (LoSDays == 0) {
                    LoSDays = (int) ((checkDate.getTime() - commencingDate.getTime()) / (1000 * 60 * 60 * 24));
                }
                if (commencingDate.getYear() < checkDate.getYear()) {
                    // sudah beda tahun berarti akan dapat full                  
                    if (LoSDays >= (365 * 10) && ((commencingDate.getMonth() == checkDate.getMonth()) || (commencingDate.getMonth() < checkDate.getMonth()) ) ) { // length of service >= 10 tahun
                        //update by satrya 2013-02-27
                        //if (LoSDays >= (365 * 10) && ((commencingDate.getMonth() == checkDate.getMonth()) {
                        iEntitleAL =iALEntitlesExtra[iLevel][iType];
                    }
                }
            }
        } catch (Exception exc) {
            //iEntitleAL = iALEntitles[0][0];
            System.out.println(exc);
        }
        return iEntitleAL;
    }
    /**
     * @descr set configuration for AL number entitling for employee depending on Level and type of employee
     * @param level
     * @param employeeType
     * @param numberAL
     */
    public void setALEntitleAnualLeave(String level, String employeeType, int numberAL) {
        int iLevel = getIndexLevel(level);
        int iType = getIndexType(employeeType);
        iALEntitles[iLevel][iType] = numberAL;
    }

    /**
     * @descr setter method for configuration of AL entetling period the value shall be AL_ENTITLE_BY_COMMENCING or AL_ENTITLE_ANUAL
     * @param al_entitle_by
     */
    public void setALEntitleBy(int al_entitle_by) {
        iEntitleType = al_entitle_by;
    }

    /**
     * @descr getter method for configuration of AL entetling period. the return value will be AL_ENTITLE_BY_COMMENCING or AL_ENTITLE_ANUAL 
     * @return
     */
    public int getALEntitleBy() {
        return iEntitleType;
    }

    /**
     * @descr set configuration for expiration days of AL after retreating of entitling date. 
     * @param days
     */
    public void setALExpirationDay(int days) {
        iALExpirationDay = days;
    }

    /**
     * @descr get configuration for expiration days of AL after retreating of entitling date. 
     * @return
     */
    public int getALExpirationDay() {
        return iALExpirationDay;
    }

    /**
     * @descr set configuration for warning days before AL for employee expired 
     * @param days
     */
    public void setALExpWarning(int days) {
        iALExpWarning = days;
    }

    /**
     * @descr get configuration for warning days before AL for employee expired 
     * @param days
     */
    public int getALExpWarning() {
        return iALExpWarning;
    }

    /**
     * @descr Method for getting number of AL during LL entetling depending on employee Level and Employee type
     * @param level
     * @param employeeType
     * @return
     */
    public int getALEntitlebyLL(String level, String employeeType) {
        int iEntitleAL = 0;
        int iLevel = getIndexLevel(level);
        int iType = getIndexType(employeeType);
        iEntitleAL = iALEntitlesByLL[iLevel][iType];
        return iEntitleAL;
    }

    /**
     * @descr set configuration for AL number entitling for employee DURING LL ENTETLING depending on Level and type of employee
     * @param level
     * @param employeeType
     * @param numberAL
     */
    public void setALEntitleLL(String level, String employeeType, int numberAL) {
        int iLevel = getIndexLevel(level);
        int iType = getIndexType(employeeType);
        iALEntitles[iLevel][iType] = numberAL;
    }
    
     //priska 20150805 menambahkan al & ll Allowance
    public boolean getALallowance(LeaveApplication leaveApplication){
        return false;
    }
    public boolean getLLallowance(LeaveApplication leaveApplication){
        return false;
    }
    //priska 20150807
    public boolean getALallowanceByPeriode(long periodeId, long employeeId){
        return false;
    }
    public boolean getLLallowanceByPeriode(long periodeId, long employeeId){
        return false;
    }
    /**
     * menset jumlah minimal LL yang boleh di ambil dalam satu tahun
     *
     */
    public void setMinLLTakenAnual(int minLL) {
        iMinLLTakenAnual = minLL;
    }

    /**
     * mengambil jumlah minimal LL yang boleh di ambil dalam satu tahun
     *
     */
    public int getMinLLTakenAnual() {
        return iMinLLTakenAnual;
    }

    /**
     * menset jumlah maximum LL yang boleh di ambil dalam satu tahun
     *
     */
    public void setMaxLLTakenAnual(int minLL) {
        iMaxLLTakenAnual = minLL;
    }

    /**
     * mengambil jumlah maximum LL yang boleh di ambil dalam satu tahun
     *
     */
    public int getMaxLLTakenAnual() {
        return iMaxLLTakenAnual;
    }

    /**
     * @descr set configuration for expiration days of LL after retreating of entitling date. 
     * @param days
     */
    public void setLLExpirationDay(int days) {
        iLLExpirationDay = days;
    }

    /**
     * @descr get configuration for expiration days of LL after retreating of entitling date. 
     * @return
     */
    public int getLLExpirationDay() {
        return iLLExpirationDay;
    }

    /**
     * @descr set configuration for warning days before LL expiration for employee expires
     * @param days
     */
    public void setLLExpWarning(int days) {
        iLLExpWarning = days;
    }

    /**
     * @descr get configuration for warning days before LL for employee  expires
     * @param days
     */
    public int getLLExpWarning() {
        return iLLExpWarning;
    }

    /**
     * @descr menset konfigurasi list dari jangka waktu dan jumlah LL yang diperoleh ( bisa lebih dari satu setting : e.g.  : 5 tahun = 26  , 7 tahun = 2 ) tergantung dari Level karyawan.
     * @param level
     * @param employeeType
     * @param monthsLoS : Length of service in months 
     * @param numberLL
     * @param validMonths : length of validity in months
     */
    public void setLLEntitle(String level, String employeeType, int monthsLoS, int numberLL) {
        int iLevel = getIndexLevel(level);
        int iType = getIndexType(employeeType);
        int indexMonth = -1;
        for (int i = 0; i < iIntervalLLs.length; i++) {
            if (monthsLoS == iIntervalLLs[i]) {
                indexMonth = i;
                break;
            }
        }
        if (iLevel >= 0 && iType >= 0 && indexMonth >= 0) {
            iLLEntitles[iLevel][iType][indexMonth] = numberLL;
        }
    }

    /**
     * @descr get konfigurasi list dari jangka waktu dan jumlah LL yang diperoleh ( bisa lebih dari satu setting : e.g.  : 5 tahun = 26  , 7 tahun = 2 ) tergantung dari Level karyawan.
     * @param level
     * @param employeeType
     * @param monthsLoS : Length of service in months 
     * @param numberLL
     */
    public int getLLEntile(String level, String employeeType, int monthsLoS) {
        int iLevel = getIndexLevel(level);
        int iType = getIndexType(employeeType);
        int indexMonth = -1;
        for (int i = 0; i < iIntervalLLs.length; i++) {
            if (monthsLoS == iIntervalLLs[i]) {
                indexMonth = i;
                break;
            }
        }
        if (iLevel >= 0 && iType >= 0 && indexMonth >= 0) {
            return iLLEntitles[iLevel][iType][indexMonth];
        }
        return 0;
    }

    /**
     * @param set standard DP entitle based on employee level
     * @param level
     * @param dpDays
     */
    public void setDPStandardEntitle(String level, int dpDays) {
        int iLevel = getIndexLevel(level);
        iDPStandardEntitle[iLevel] = dpDays;
    }

    /**
     * @descr get standard DP entitle based on employee level
     * @param level
     * @return
     */
    public int getDPStandardEntitle(String level) {
        int iEntitleDP = 0;
        int iLevel = getIndexLevel(level);
        iEntitleDP = iDPStandardEntitle[iLevel];
        return iEntitleDP;
    }

    /**
     * @descr getDP entitle for a specific employee on a specific date
     * Level E-D : 2 hak DP
     * Level C ke atas sampai A: 1
     * Public holiday , jatuh pada day off
     * kalau libur ( schedule off ) : dapat pengganti 1
     * kalau dia di set masuk : dapat pengganti 3
     * di tambah BirthDay :
     * kalau dia libur : dapat pengganti 2
     * kalau dia masuk : dapat pengganti 4
     * @param payNum
     * @param date
     * @return
     */
    public int getDPEntitleByDate(String payNum, Date date,LeaveConfigurationParameter leaveConfigurationParameter) {
        
        int iDPEntitleByDate = 0;
        /*Employee employee = new Employee();
        employee = PstEmployee.getEmployeeByNum(payNum);

        //Cek kondisi
        boolean boolScheduleOff = false;
        boolean boolPresent = false;
        boolean boolPublicHoliday = false;
        boolean boolBirthDay = false;

        boolScheduleOff = isScheduleOff(employee, date,leaveConfigurationParameter);
        boolPresent = isPresent(employee, date);
        boolPublicHoliday = isPublicHoliday(employee, date);
        boolBirthDay = isBirthDay(employee, date);

        System.out.println("\nEMPLOYEE STATUS :::::: " + payNum + " at date :" + date);
        System.out.println("  => ScheduleOff    :" + boolScheduleOff);
        System.out.println("  => Present        :" + boolPresent);
        System.out.println("  => PublicHoliday  :" + boolPublicHoliday);
        System.out.println("  => BirthDay       :" + boolBirthDay + "\n");

        //Mengecek level employee
        int iDefEntitleDP = 0;
        String strLevel = "";
        long lLevelId = 0;
        try {
            lLevelId = employee.getLevelId();
            Level objLevel = new Level();
            objLevel = (Level) PstLevel.fetchExc(lLevelId);
            strLevel = objLevel.getLevel();
        } catch (DBException ex) {
            ex.printStackTrace();
        }

        if (boolScheduleOff) {
            if (boolPresent) {
                iDPEntitleByDate += getDPStandardEntitle(strLevel);
                if (boolPublicHoliday) {
                    iDPEntitleByDate += iDPHolidayPresentEntitle[getIndexLevel(strLevel)];
                }
                if (boolBirthDay) {
                    iDPEntitleByDate += iDPBirthdayPresentEntitle[getIndexLevel(strLevel)];
                }
            } else {
                if (boolPublicHoliday) {
                    iDPEntitleByDate += iDPHolidayOffEntitle[getIndexLevel(strLevel)];
                    ;
                }
                if (boolBirthDay) {
                    iDPEntitleByDate += iDPBirthdayOffEntitle[getIndexLevel(strLevel)];
                }
            }
        } else {
            if (boolPresent) {
                if (boolPublicHoliday) {
                    iDPEntitleByDate += iDPHolidayPresentEntitle[getIndexLevel(strLevel)];
                }
                if (boolBirthDay) {
                    iDPEntitleByDate += iDPBirthdayPresentEntitle[getIndexLevel(strLevel)];
                }
            } else {
                //Belum didefinisikan
                //if (boolPublicHoliday) {
                //    iDPEntitleByDate += iDPHolidayOffEntitle[getIndexLevel(strLevel)] ;;
                //}
                if (boolBirthDay) {
                    iDPEntitleByDate += iDPBirthdayOffEntitle[getIndexLevel(strLevel)];
                }
            }
        }*/

        return iDPEntitleByDate;
    }

    /**
     * @param mengambil masa aktif dari dp per level
     * @param level
     */
    public int getDpValidity(String levelArg) {
        int level = getIndexLevel(levelArg);
        if (level < 0) {
            return iDpValidity[0];

        }
        return iDpValidity[level];
    }

    //Add by artha
    /**
     * @desc mengambil nilai index dari level employee
     * @param 
     */
    public int getIndexLevel(String level) {
        int index = -1;
        if (level == null) {
            return -1;
        }
        for (int i = 0; i < strLevels.length; i++) {
            // System.out.println("LEVEl ::: "+level + " >< "+strLevels[i]);
            if (level.equalsIgnoreCase(strLevels[i])) {
                index = i;
                break;
            }
        }
        return index;
    }

    /**
     * @desc mengambil nilai index dari type employee
     * @param 
     */
    public int getIndexType(String type) {
        int index = -1;
        if (type == null) {
            return -1;
        }
        for (int i = 0; i < strCategory.length; i++) {
            //  System.out.println("CATEGORY ::: "+type + " >< "+strCategory[i]);
            if (type.equalsIgnoreCase(strCategory[i])) {
                index = i;
                break;
            }
        }
        return index;
    }

    //Untuk mengecek kondisi pada penentuan DP
    /**
     * @desc mengecek apakah employee DO atau tidak, jika DO maka akan diset True jika tidak maka di set false
     * @param employee Employee
     * @param date Date
     * @return boolean
     */
    public boolean isScheduleOff(Employee employee, Date date,LeaveConfigurationParameter leaveConfigurationParameter) {
        boolean status = false;

        //Cek apakah employee ada jadwal pada tgl tersebut?
        ScheduleSymbol scheduleSymbol = new ScheduleSymbol();
        long lScheduleSymbolId = 0;
        lScheduleSymbolId = SessEmpSchedule.getScheduleId(employee.getOID(), date);

        try {
            scheduleSymbol = (ScheduleSymbol) PstScheduleSymbol.fetchExc(lScheduleSymbolId);
        } catch (DBException ex) {
            ex.printStackTrace();
        }

        long lScheduleCategoryId = 0;
        lScheduleCategoryId = scheduleSymbol.getScheduleCategoryId();
        if (lScheduleCategoryId > 0) {
            ScheduleCategory scheduleCategory = new ScheduleCategory();
            try {
                scheduleCategory = (ScheduleCategory) PstScheduleCategory.fetchExc(lScheduleCategoryId);
            } catch (DBException ex) {
                System.out.println("[ERROR] com.dimata.harisma.session.leave.SPLeaveConfig isScheduleOff :::: " + ex.toString());
            }

            //Cek apakah employee day off atau tidak
            //Day Off
            if (scheduleCategory.getCategoryType() == PstScheduleCategory.CATEGORY_OFF) {
                return true;
            }
        }
        return status;
    }

    /**
     * @desc mengecek apakah employee hadir pd tgl tsb
     * @param employee Employee
     * @param date Date
     * @return boolean
     * 
     */
    public boolean isPresent(Employee employee, Date date) {
        boolean status = false;
        int intervalDate = 0;
        //Cek apakah employee hadir pada tgl tersebut?
        //  Date dActualPresentIn = (Date)SessEmpSchedule.getTimeIn(employee.getOID(),date);
        //  Date dActualPresentOut = (Date)SessEmpSchedule.getTimeOut(date,employee.getOID()
        Date dActualPresentIn = new Date();
        Date dActualPresentOut = new Date();

        DBResultSet dbrs = null;
        long periodId = PstPeriod.getPeriodIdBySelectedDate(date);
        try {
            String sFieldIn = "IN" + date.getDate();
            String sFieldOut = "OUT" + date.getDate();


            String sql = " SELECT " + sFieldIn + "," + sFieldOut + " FROM " + PstEmpSchedule.TBL_HR_EMP_SCHEDULE + " WHERE " + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID] + "=" + employee.getOID() + " AND " + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_PERIOD_ID] + "=" + periodId;
            System.out.println("SQL Mencari  jam masuk dan keluar :::::: " + sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                dActualPresentIn = rs.getTime(1);
                dActualPresentOut = rs.getTime(2);
            }

        } catch (Exception e) {
        } finally {

            DBResultSet.close(dbrs);
        }


        long lTimeIn = 0;
        long lTimeOut = 0;
        System.out.println("::::::::::: IN  :::::" + dActualPresentIn);
        System.out.println("::::::::::: OUT :::::" + dActualPresentOut);

        if (dActualPresentIn != null) {
            lTimeIn = dActualPresentIn.getTime();
        }
        if (dActualPresentOut != null) {
            lTimeOut = dActualPresentOut.getTime();
        }

        /*   if(Formater.formatDate(dActualPresentIn, "yyyy-MM-dd 00:00:00").equals(
        Formater.formatDate(date, "yyyy-MM-dd 00:00:00"))){
        if(Formater.formatDate(new Date(), "yyyy-MM-dd 00:00:00").equals(
        Formater.formatDate(date, "yyyy-MM-dd 00:00:00"))){
        intervalDate = (int)((lTimeOut-lTimeIn)/(1000));//Menjadi jam
        }else{
        if(!Formater.formatDate(new Date(), "yyyy-MM-dd 00:00:00").equals(
        Formater.formatDate(dActualPresentOut, "yyyy-MM-dd 00:00:00"))){
        intervalDate = (int)((lTimeOut-lTimeIn)/(1000));
        }
        }
        }
         * */
        intervalDate = (int) ((lTimeOut - lTimeIn) / (1000 * 60 * 60));

        System.out.println("::::::::::: INTERVAL :::::" + intervalDate);
        if (intervalDate >= minTimeToHasDP) {
            status = true;
        }
        return status;
    }

    /**
     * @desc mengecek apakah employee mendapat public holiday pada tgl tertentu
     * @param employee Employee
     * @param date Date
     * @return boolean
     */
    public boolean isPublicHoliday(Employee employee, Date date) {
        boolean status = false;
        //Apakah tgl tersebut merupakan hari libur?
        Vector vPublicHolidays = new Vector();
        vPublicHolidays = PstPublicHolidays.getHolidayByDate(date);
        if (vPublicHolidays.size() > 0) {
            for (int i = 0; i < vPublicHolidays.size(); i++) {
                PublicHolidays publicHolidays = new PublicHolidays();
                publicHolidays = (PublicHolidays) vPublicHolidays.get(i);
                if (publicHolidays.getiHolidaySts() == 1 || publicHolidays.getiHolidaySts() == employee.getReligionId()) {
                    return true;
                }
            }
        }
        return status;
    }

    /**
     * @desc mengecek apakah employee sedang ulang tahun pada tgl tertentu
     * @param employee Employee
     * @param date Date
     * @return boolean
     */
    public boolean isBirthDay(Employee employee, Date date) {
        boolean status = false;

        if (employee.getBirthDate() != null) {
            if (employee.getBirthDate().getDate() == date.getDate()
                    && employee.getBirthDate().getMonth() == date.getMonth()) {
                return true;
            }
        }
        return status;
    }

    /**;
     * @descr get konfigurasi list dari jangka waktu validity LL ( misal yang didapat setiap 5 tahun valid 4 tahun)tergantung dari Level karyawan.
     * @param level
     * @param employeeType
     * @param monthsLoS : Length of service in months 
     * @param numberLL
     * @return range of months of validity of LL
     */
    public int getLLValidityMonths(String level, String employeeType, int monthsLoS) {
        int iLevel = getIndexLevel(level);
        int iType = getIndexType(employeeType);
        int indexMonth = -1;
        for (int i = 0; i < iIntervalLLs.length; i++) {
            if (monthsLoS == iIntervalLLs[i]) {
                indexMonth = i;
                break;
            }
        }
        if (iLevel >= 0 && iType >= 0 && indexMonth >= 0) {
            return iLLValidity[iLevel][iType][indexMonth];
        }
        return 0;
    }

    /**
     * @descr menset konfigurasi list dari jangka waktu dan jumlah LL yang diperoleh ( bisa lebih dari satu setting : e.g.  : 5 tahun = 26  , 7 tahun = 2 ) tergantung dari Level karyawan.
     * @param level
     * @param employeeType
     * @param monthsLoS : Length of service in months 
     * @param numberLL
     * @param validMonths : length of validity in months
     */
    public void setLLEntitle(String level, String employeeType, int monthsLoS, int numberLL, int validMonths) {
        int iLevel = getIndexLevel(level);
        int iType = getIndexType(employeeType);
        int indexMonth = -1;
        for (int i = 0; i < iIntervalLLs.length; i++) {
            if (monthsLoS == iIntervalLLs[i]) {
                indexMonth = i;
                break;
            }
        }
        if (iLevel >= 0 && iType >= 0 && indexMonth >= 0) {
            iLLEntitles[iLevel][iType][indexMonth] = numberLL;
            iLLValidity[iLevel][iType][indexMonth] = validMonths;
        }
    }

    /**
     * @desc menambil level employee per id employee
     *      - level yang dimaksud adalah level kerja/type
     * @param employee id
     * @return string dari level
     */
    public String getLevel(long employeeId) {
        Level objLevel = new Level();
        try {
            Employee objEmployee = PstEmployee.fetchExc(employeeId);
            objLevel = PstLevel.fetchExc(objEmployee.getLevelId());
            return objLevel.getLevel();
        } catch (Exception ex) {
            System.out.println("SPLeaveConfig.getLevel Employee not found ");
            return "";
        }
    }

    /**
     * @desc mengabil employee category per employee id
     * @param employee id
     * @return String dari category
     */
    public String getCategory(long employeeId) {
        EmpCategory objEmpCategory = new EmpCategory();
        try {
            Employee objEmployee = PstEmployee.fetchExc(employeeId);
            objEmpCategory = PstEmpCategory.fetchExc(objEmployee.getEmpCategoryId());
            return objEmpCategory.getEmpCategory();
        } catch (DBException ex) {
            System.out.println("[ERROR] SPLeaveConfig.getCategory ::: " + ex.toString());
            return "";
        }
    }

    public String[] getStrLevels() {
        return strLevels;
    }

    public String[] getStrCategory() {
        return strCategory;
    }

    public int[] getIntervalLLinMonths() {
        return iIntervalLLs;
    }

    public int[] getIntervalALinMonths() {
        return iIntervalAL;
    }

    public int getMaxApproval(long employeeId) {
        int maxApporval = 0;
        try {
            String empLevel = getLevel(employeeId);
            int iLevel = getIndexLevel(empLevel);
            maxApporval = approvalNeed[iLevel];
        } catch (Exception ex) {
        }
        return maxApporval;
    }

    public int getMaxLeaveApprovalLevel() {
        int maxApporval = 0;
        try {
            for (int i = 0; i < approvalNeed.length; i++) {
                if (maxApporval < approvalNeed[i]) {
                    maxApporval = approvalNeed[i];
                }
            }
        } catch (Exception ex) {
        }
        return maxApporval;
    }

    public boolean isLeaveApprovalLevel(int level) {
        try {
            for (int i = 0; i < approvalNeed.length; i++) {
                if (level == approvalNeed[i]) {
                    return true;
                }
            }
        } catch (Exception ex) {
        }
        return false;
    }

    public int getDistributionBy() {
        int Distribution = 0;

        return Distribution;
    }

    public Vector getManagerApproval(long employeeId) {

        Employee employee = new Employee();
        Department department = new Department();

        try {
            employee = PstEmployee.fetchExc(employeeId);
        } catch (Exception e) {
            System.out.println("EXCEPTION " + e.toString());
        }

        try {
            department = PstDepartment.fetchExc(employee.getDepartmentId());
        } catch (Exception e) {
            System.out.println("EXCEPTION " + e.toString());
        }

        Vector listEmp = new Vector();

        listEmp = listDepartmentApproval(department.getOID(), employee, PstPosition.LEVEL_ASST_MANAGER, PstPosition.LEVEL_ASST_DIRECTOR);

        if (listEmp != null && listEmp.size() > 0) {

            return listEmp;
        }

        return null;
    }

    /**
     * Check apakah employee id ada di list, yang dicocokan adalah employeeId
     * @param employeeId
     * @param list
     * @return 
     */
    private boolean existEmployee(long employeeId, Vector<Employee> list) {
        if (employeeId == 0 || list == null || list.size() < 1) {
            return false;
        }

        for (int i = 0; i < list.size(); i++) {
            Employee emp = (Employee) list.get(i);
            if (emp.getOID() == employeeId) {
                return true;
            }
        }
        return false;
    }

    /**
     * //update by priska 20150807
     * @param employeeId
     * @return
     */
 public Vector getApprovalEmployeeTopLink(long employeeId, int typeOfLink) {
        
     
        Employee employeeObj = new Employee();
        try {
            employeeObj = PstEmployee.fetchExc(employeeId);
        }catch (Exception e){}
     
        Level level = new Level();
        try{
            level = PstLevel.fetchExc(employeeObj.getLevelId());
        }catch(Exception e){}
        
        Level maxLevelObj = new Level();
        try{
            maxLevelObj = PstLevel.fetchExc(level.getMaxLevelApproval());
        }catch(Exception e){}
        
        int minLevel = level.getLevelPoint();
        int maxLevel = maxLevelObj.getLevelPoint();
        String byLevelId = "";
        for (int xx = minLevel ; xx < maxLevel; xx++){
            Level levelX = new Level();
            levelX = PstLevel.getLevelByPoint(xx);
            byLevelId = byLevelId +levelX.getOID()+",";
        }
        if (byLevelId.length()>0){
        byLevelId = byLevelId.substring(0, byLevelId.length()-1);
        }
        Vector listEmployee = new Vector();
        String whereClause  = "HE." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + " = "+employeeId+" AND HMP."+ PstMappingPosition.fieldNames[PstMappingPosition.FLD_TYPE_OF_LINK] + " = "+typeOfLink+" AND HE." + PstEmployee.fieldNames[PstEmployee.FLD_LEVEL_ID] + " IN ("+byLevelId+")" ;
        String listTopLink = PstMappingPosition.listEmployeeTopPositionId(0, 0, whereClause, "");
        if (listTopLink.length() > 0 ){
           listTopLink = listTopLink.substring(0, (listTopLink.length() - 1 ));
        }
        Employee employee = new Employee();
        try {
            employee = PstEmployee.fetchExc(employeeId);
        } catch (Exception e) {
            
        }
        Vector listEmployeeSectionTopLink       = new Vector();
        Vector listEmployeeDepartmentTopLink    = new Vector();
        Vector listEmployeeDivisionTopLink      = new Vector();
               //listEmployeeSectionTopLink       = PstEmployee.listEmployeeApprovalTopLink(0, 0, " POSITION_ID IN ("+listTopLink+")" , "", employee.getSectionId(), employee.getDepartmentId() , employee.getDivisionId());
               //listEmployeeDepartmentTopLink    = PstEmployee.listEmployeeApprovalTopLink(0, 0, " POSITION_ID IN ("+listTopLink+")" , "", 0 ,employee.getDepartmentId(), employee.getDivisionId());
               listEmployeeDivisionTopLink      = PstEmployee.listEmployeeApprovalTopLink(0, 0, " POSITION_ID IN ("+listTopLink+")" , "", 0,0, 0);
        
        if (listEmployeeSectionTopLink.size() != 0 || listEmployeeSectionTopLink.size() > 0 ){
            listEmployee = listEmployeeSectionTopLink;
        } else if (listEmployeeDepartmentTopLink.size() != 0 || listEmployeeDepartmentTopLink.size() > 0 ){
            listEmployee = listEmployeeDepartmentTopLink;
        } else if (listEmployeeDivisionTopLink.size() != 0 || listEmployeeDivisionTopLink.size() > 0){ 
            listEmployee = listEmployeeDivisionTopLink;
        } else {
          //  employee.setFullName("Tidak Employee yang di set pada top Link");
          //  listEmployee.add(employee);
        }
        return listEmployee;
    }
 
 
    /**
     * //update by priska 20150807
     * @param employeeId
     * @return
     */
 public Vector getApprovalEmployeeTopLinkByLevel(long employeeId, int typeOfLink,int levelPoint) {
       String byLevelId = "";
       try{
       Level levelX = new Level();
       levelX = PstLevel.getLevelByPoint(levelPoint);
       byLevelId = byLevelId +levelX.getOID();
       }catch(Exception e){} 
       
        Vector listEmployee = new Vector();
        String whereClause  = "HE." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + " = "+employeeId+" AND HMP."+ PstMappingPosition.fieldNames[PstMappingPosition.FLD_TYPE_OF_LINK] + " = "+typeOfLink+" AND HP." + PstPosition.fieldNames[PstPosition.FLD_LEVEL_ID] + " IN ("+byLevelId+")" ;
        String listTopLink = PstMappingPosition.listEmployeeTopPositionId(0, 0, whereClause, "");
        if (listTopLink.length() > 0 ){
           listTopLink = listTopLink.substring(0, (listTopLink.length() - 1 ));
        }
        Employee employee = new Employee();
        try {
            employee = PstEmployee.fetchExc(employeeId);
        } catch (Exception e) {
            
        }
        Vector listEmployeeDivisionTopLink      = new Vector();
               listEmployeeDivisionTopLink      = PstEmployee.listEmployeeApprovalTopLink(0, 0, " POSITION_ID IN ("+listTopLink+")" , "", 0,0, 0);
        
        if (listEmployeeDivisionTopLink.size() != 0 || listEmployeeDivisionTopLink.size() > 0){ 
            listEmployee = listEmployeeDivisionTopLink;
        }
        return listEmployee;
    }
 
 public Vector getApprovalEmployeeTopLinkByLevelMultipleEmp(String employeeId, int typeOfLink,int levelPoint) {
       String byLevelId = "";
       try{
       Vector vLevel = PstLevel.getAllLevelByPoint(levelPoint);
       
       for (int xx = 0; xx < vLevel.size(); xx++ ){
           Level levelX = (Level) vLevel.get(xx);
           byLevelId = byLevelId +levelX.getOID()+",";
       }
           byLevelId = byLevelId.substring(0,byLevelId.length()-1);
           //byLevelId = byLevelId +"10003";
       }catch(Exception e){} 
       
        Vector listEmployee = new Vector();
        String whereClause  = "HE." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + " IN ("+employeeId+") AND HMP."+ PstMappingPosition.fieldNames[PstMappingPosition.FLD_TYPE_OF_LINK] + " = "+typeOfLink+" AND HP." + PstPosition.fieldNames[PstPosition.FLD_LEVEL_ID] + " IN ("+byLevelId+")" ;
        String listTopLink = PstMappingPosition.listEmployeeTopPositionId(0, 0, whereClause, "");
        if (listTopLink.length() > 0 ){
           listTopLink = listTopLink.substring(0, (listTopLink.length() - 1 ));
        }
     
        Vector listEmployeeDivisionTopLink      = new Vector();
               listEmployeeDivisionTopLink      = PstEmployee.listEmployeeApprovalTopLink(0, 0, " POSITION_ID IN ("+listTopLink+")" , "", 0,0, 0);
        
        if (listEmployeeDivisionTopLink.size() != 0 || listEmployeeDivisionTopLink.size() > 0){ 
            listEmployee = listEmployeeDivisionTopLink;
        }
        return listEmployee;
    }  
 
    /**
     * 
     * @param employeeId
     * @return
     */
    public Vector getApprovalDepartmentHead(long employeeId) {
        Employee employee = new Employee();
        Section section = new Section();
        Department department = new Department();
        Division division = new Division();
        Position position = new Position();

        try {
            employee = PstEmployee.fetchExc(employeeId);
        } catch (Exception e) {
            System.out.println("EXCEPTION " + e.toString());
        }

        try {
            section = PstSection.fetchExc(employee.getSectionId());
        } catch (Exception e) {
            System.out.println("EXCEPTION " + e.toString());
        }

        try {
            department = PstDepartment.fetchExc(employee.getDepartmentId());
        } catch (Exception e) {
            System.out.println("EXCEPTION " + e.toString());
        }

        try {
            division = PstDivision.fetchExc(employee.getDivisionId());
        } catch (Exception e) {
            System.out.println("EXCEPTION " + e.toString());
        }

        try {
            position = PstPosition.fetchExc(employee.getPositionId());
        } catch (Exception e) {
            System.out.println("EXCEPTION " + e.toString());
        }

        Vector listEmpDepartment = new Vector();

        Vector listEmpDivision = new Vector();

        Vector listEmployee = new Vector();

        //listEmployee = listSectionApproval(department.getOID(), section.getOID(), employee.getOID());
        if (position != null) {
            Employee emGM = new Employee();
            Employee emHRDir = new Employee();
            Employee emHRMan = new Employee();
            try {
                long oidGM = Long.parseLong(PstSystemProperty.getValueByName("GM_POS_ID"));
                if (oidGM != 0) {
                    if (!existEmployee(oidGM, listEmployee)) {
                        emGM = PstEmployee.getEmployeeByPositionId(oidGM);
                    };
                }
            } catch (Exception E) {
                emGM = new Employee();
                emGM.setFullName("Please set OID for General Manager in System Ppoperty : GM");
            }
            try {
                long oidHRDir = Long.parseLong(PstSystemProperty.getValueByName("HR_DIR_POS_ID"));
                if (oidHRDir != 0 && oidHRDir != employee.getOID()) {
                    emHRDir = PstEmployee.getEmployeeByPositionId(oidHRDir);
                }
            } catch (Exception E) {
                emGM = new Employee();
                emGM.setFullName("HR Director not set in System Property : HR_DIRECTOR");
            }
            try {
                long oidHRMan = Long.parseLong(PstSystemProperty.getValueByName("HR_MAN_POS_ID"));
                if (oidHRMan != 0 && oidHRMan != employee.getOID()) { // && !existEmployee(oidHRMan, listEmployee)) {
                    emHRMan = PstEmployee.getEmployeeByPositionId(oidHRMan);
                }
            } catch (Exception E) {
                emGM = new Employee();
                emGM.setFullName("HR Director not set in System Property : HR_DIRECTOR");
            }

            boolean HOD = false;
            boolean DivHead = false;
            //update by satrya 2014-07-28
            Vector listApprovall = PstLeaveConfigurationMain.listDepHeadApprovall(""+employee.getDepartmentId(), position.getPositionLevel(), section.getOID(), 0, 0, "");
            if (listApprovall != null && listApprovall.size() > 0) {
                for (int i = 0; i < listApprovall.size(); i++) {
                     Employee objEmployee = new Employee();
                    LeaveConfigurationMain leaveConfigurationMain = (LeaveConfigurationMain)listApprovall.get(i);
                    objEmployee.setOID(leaveConfigurationMain.getEmployeeId());
                    objEmployee.setFullName(leaveConfigurationMain.getNamaEmployee()); 
                    objEmployee.setEmailAddress(leaveConfigurationMain.getEmailEmployee()); 
                    if (!existEmployee(objEmployee.getOID(), listEmployee) && employeeId!=objEmployee.getOID()) {
                        listEmployee.add(objEmployee);
                    };
                }
            }
            if(listEmployee==null  || listEmployee!=null && listEmployee.size()==0){
                        if (position.getPositionLevel() >= PstPosition.LEVEL_GENERAL && position.getPositionLevel() <= PstPosition.LEVEL_ASST_MANAGER) { // staff to manager
                        if (position.getPositionLevel() == PstPosition.LEVEL_ASST_MANAGER && position.getDisabledAppDeptScope() == 0) { // Ast. Manager as HOD
                                listEmpDivision = listDivisionApproval(division.getOID(), employee.getOID());
                                if (listEmpDivision != null && listEmpDivision.size() > 0) {
                                    for (int i = 0; i < listEmpDivision.size(); i++) {
                                        Employee objEmployee = new Employee();
                                        objEmployee = (Employee) listEmpDivision.get(i);
                                        if (!existEmployee(objEmployee.getOID(), listEmployee)) {
                                            listEmployee.add(objEmployee);
                                        };
                                    }
                                    DivHead = true;
                                }
                                if(!existEmployee(emHRDir.getOID(), listEmployee)){
                                    listEmployee.add(emHRDir);
                                }
                                if(!existEmployee(emGM.getOID(), listEmployee)){
                                    listEmployee.add(emGM);
                                }                        
                        } else {
                            // staff to ast. manager
                            listEmpDepartment = listDepartmentApproval(department.getOID(), employee, PstPosition.LEVEL_ASST_MANAGER, PstPosition.LEVEL_MANAGER);
                            listEmpDivision = listDivisionApproval(division.getOID(), employee.getOID());
                            if (listEmpDepartment != null && listEmpDepartment.size() > 0) {
                                for (int i = 0; i < listEmpDepartment.size(); i++) {
                                    Employee objEmployee = new Employee();
                                    objEmployee = (Employee) listEmpDepartment.get(i);
                                    if (!existEmployee(objEmployee.getOID(), listEmployee)) {
                                        listEmployee.add(objEmployee);
                                    };
                                }
                                HOD = true;
                            }

                            if (listEmpDivision != null && listEmpDivision.size() > 0) {
                                for (int i = 0; i < listEmpDivision.size(); i++) {
                                    Employee objEmployee = new Employee();
                                    objEmployee = (Employee) listEmpDivision.get(i);
                                    if (!existEmployee(objEmployee.getOID(), listEmployee)) {
                                        listEmployee.add(objEmployee);
                                    };
                                }
                                DivHead = true;
                            }

                            if ((HOD && !DivHead) || (!HOD && DivHead)) { // HOD found but not Div Head
                                if(!existEmployee(emHRMan.getOID(), listEmployee)){
                                    listEmployee.add(emHRMan);
                                }
                                if(!existEmployee(emHRDir.getOID(), listEmployee)){
                                    listEmployee.add(emHRDir);
                                }
                            }

                            if (!HOD && !DivHead) {
                                if(!existEmployee(emHRMan.getOID(), listEmployee)){
                                    listEmployee.add(emHRMan);
                                }
                                if(!existEmployee(emHRDir.getOID(), listEmployee)){
                                    listEmployee.add(emHRDir);
                                }
                                if(!existEmployee(emGM.getOID(), listEmployee)){
                                    listEmployee.add(emGM);
                                }                        
                            }
                        }
                    } else {
                        if (position.getPositionLevel() == PstPosition.LEVEL_MANAGER) {
                            if (position.getDisabledAppDeptScope() != 0) { // Non HOD manager
                                listEmpDepartment = listDepartmentApproval(department.getOID(), employee, PstPosition.LEVEL_MANAGER, PstPosition.LEVEL_MANAGER);
                                listEmpDivision = listDivisionApproval(division.getOID(), employee.getOID());
                                if (listEmpDepartment != null && listEmpDepartment.size() > 0) {
                                    for (int i = 0; i < listEmpDepartment.size(); i++) {
                                        Employee objEmployee = new Employee();
                                        objEmployee = (Employee) listEmpDepartment.get(i);
                                        if (!existEmployee(objEmployee.getOID(), listEmployee)) {
                                            listEmployee.add(objEmployee);
                                        };
                                    }
                                    HOD = true;
                                }

                                if (listEmpDivision != null && listEmpDivision.size() > 0) {
                                    for (int i = 0; i < listEmpDivision.size(); i++) {
                                        Employee objEmployee = new Employee();
                                        objEmployee = (Employee) listEmpDivision.get(i);
                                        if (!existEmployee(objEmployee.getOID(), listEmployee)) {
                                            listEmployee.add(objEmployee);
                                        };
                                    }
                                    DivHead = true;
                                }

                                if (!HOD) {
                                if(!existEmployee(emHRDir.getOID(), listEmployee)){
                                    listEmployee.add(emHRDir);
                                }
                                if(!existEmployee(emGM.getOID(), listEmployee)){
                                    listEmployee.add(emGM);
                                }                        
                                }

                            } else {
                                // HOD manager
                                listEmpDivision = listDivisionApproval(division.getOID(), employee.getOID());
                                if (listEmpDivision != null && listEmpDivision.size() > 0) {
                                    for (int i = 0; i < listEmpDivision.size(); i++) {
                                        Employee objEmployee = new Employee();
                                        objEmployee = (Employee) listEmpDivision.get(i);
                                        if (!existEmployee(objEmployee.getOID(), listEmployee)) {
                                            listEmployee.add(objEmployee);
                                        };
                                    }
                                    DivHead = true;
                                }
                                if(!existEmployee(emHRDir.getOID(), listEmployee)){
                                    listEmployee.add(emHRDir);
                                }
                                if(!existEmployee(emGM.getOID(), listEmployee)){
                                    listEmployee.add(emGM);
                                }                        
                            }
                        } else {
                            if (position.getPositionLevel() == PstPosition.LEVEL_ASST_DIRECTOR) {
                                if (position.getDisabedAppDivisionScope() == 0) { // level ast director as Div.Head                                 
                                    if(!existEmployee(emHRDir.getOID(), listEmployee)){
                                        listEmployee.add(emHRDir);
                                    }
                                    if(!existEmployee(emGM.getOID(), listEmployee)){
                                        listEmployee.add(emGM);
                                    }                        
                                } else { // level ast director non div head 
                                    listEmpDivision = listDivisionApproval(division.getOID(), employee.getOID());
                                    if (listEmpDivision != null && listEmpDivision.size() > 0) {
                                        for (int i = 0; i < listEmpDivision.size(); i++) {
                                            Employee objEmployee = new Employee();
                                            objEmployee = (Employee) listEmpDivision.get(i);
                                            if (!existEmployee(objEmployee.getOID(), listEmployee)) {
                                                listEmployee.add(objEmployee);
                                            };
                                        }
                                        DivHead = true;
                                    }
                                    if(!existEmployee(emHRDir.getOID(), listEmployee)){
                                        listEmployee.add(emHRDir);
                                    }
                                    if(!existEmployee(emGM.getOID(), listEmployee)){
                                        listEmployee.add(emGM);
                                    }                        
                                }
                            } else {
                                if (position.getPositionLevel() == PstPosition.LEVEL_DIRECTOR) {
                                    if (position.getDisabedAppDivisionScope() == 0) { // level director as Div.Head                                 
                                        if(!existEmployee(emHRDir.getOID(), listEmployee)){
                                            listEmployee.add(emHRDir);
                                        }
                                        if(!existEmployee(emGM.getOID(), listEmployee)){
                                            listEmployee.add(emGM);
                                        }                        
                                    } else { // non div head 
                                        listEmpDivision = listDivisionApproval(division.getOID(), employee.getOID());
                                        if (listEmpDivision != null && listEmpDivision.size() > 0) {
                                            for (int i = 0; i < listEmpDivision.size(); i++) {
                                                Employee objEmployee = new Employee();
                                                objEmployee = (Employee) listEmpDivision.get(i);
                                                if (!existEmployee(objEmployee.getOID(), listEmployee)) {
                                                    listEmployee.add(objEmployee);
                                                };
                                            }
                                            DivHead = true;
                                        }
                                        if(!existEmployee(emHRDir.getOID(), listEmployee)){
                                            listEmployee.add(emHRDir);
                                        }
                                        if(!existEmployee(emGM.getOID(), listEmployee)){
                                            listEmployee.add(emGM);
                                        }                        
                                    }
                                } else {
                                    if (position.getPositionLevel() == PstPosition.LEVEL_GENERAL_MANAGER) {
                                        if(!existEmployee(emGM.getOID(), listEmployee)){
                                            listEmployee.add(emGM);
                                        }                        
                                    }                            
                                }
                            }
                        }
                    }
            }
        }



      //  if (position.getPositionLevel() >= PstPosition.LEVEL_MANAGER) {
       // }

        if (listEmployee != null && listEmployee.size() > 0) {
            return listEmployee;
        } else {
            try {
                long oidGM = Long.parseLong(PstSystemProperty.getValueByName("GM_POS_ID"));
                if (oidGM != 0) {
                    Employee emGM = PstEmployee.getEmployeeByPositionId(oidGM);
                    if (emGM != null && emGM.getFullName().length() > 1) {
                        listEmployee.add(emGM);
                    }
                }
            } catch (Exception E) {
                Employee emGM = new Employee();
                emGM.setFullName("Please set OID for General Manager in System Ppoperty : GM");
            }

            try {
                long oidHRDir = Long.parseLong(PstSystemProperty.getValueByName("HR_DIR_POS_ID"));
                if (oidHRDir != 0 && oidHRDir != employee.getOID()) {
                    Employee emHRDir = PstEmployee.getEmployeeByPositionId(oidHRDir);
                    if (emHRDir != null && emHRDir.getFullName().length() > 1) {
                        listEmployee.add(emHRDir);
                    }
                }
            } catch (Exception E) {
                Employee emGM = new Employee();
                emGM.setFullName("HR Director not set in System Property : HR_DIRECTOR");
            }

            if (listEmployee != null && listEmployee.size() > 0) {

                return listEmployee;

            } else {
                return null;
            }
        }

    }

    public Vector listSectionApproval(long departmentId, long sectionId, long employee_id) {

        DBResultSet dbrs = null;
        Vector result = new Vector();

        try {

            String sql = "SELECT EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + ","
                    + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMAIL_ADDRESS]
                    + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME] + " FROM "
                    + PstEmployee.TBL_HR_EMPLOYEE + " EMP " + " LEFT JOIN " + PstSection.TBL_HR_SECTION + " SEC ON "
                    + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID] + " = SEC."
                    + PstSection.fieldNames[PstSection.FLD_SECTION_ID] + " INNER JOIN "
                    + PstPosition.TBL_HR_POSITION + " POS ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID] + " = POS."
                    + PstPosition.fieldNames[PstPosition.FLD_POSITION_ID] + " INNER JOIN "
                    + PstLevel.TBL_HR_LEVEL + " LEV ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_LEVEL_ID] + " = LEV."
                    + PstLevel.fieldNames[PstLevel.FLD_LEVEL_ID] + " WHERE EMP."
                    + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] + " = " + departmentId + (sectionId == 0 ? "" : (" AND EMP."
                    + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID] + " = " + sectionId)) + " AND POS."
                    + PstPosition.fieldNames[PstPosition.FLD_DISABLED_APP_UNDER_SUPERVISOR] + " = " + PstPosition.DISABLED_APP_UNDER_SUPERVISOR_FALSE + " AND ( POS."
                    + PstPosition.fieldNames[PstPosition.FLD_POSITION_LEVEL] + " IN (" + PstPosition.LEVEL_MANAGER + ", "
                    + PstPosition.LEVEL_ASST_MANAGER + ", " + PstPosition.LEVEL_SUPERVISOR + ") ) AND EMP."
                    + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + " = " + PstEmployee.NO_RESIGN;

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {

                Employee employee = new Employee();
                employee.setOID(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]));
                employee.setFullName(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]));
                employee.setEmailAddress(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_EMAIL_ADDRESS]));
                result.add(employee);

            }

            if (result != null && result.size() > 0) {
                return result;
            } else {
                return null;
            }

        } catch (Exception e) {
            System.out.println("EXCEPTION " + e.toString());
        } finally {
            DBResultSet.close(dbrs);
        }

        return null;

    }

    public Vector listDepartmentApproval(long departmentId, long employee_id) {
        Employee theEmployee = new Employee();
        theEmployee.setOID(employee_id);
        return listDepartmentApproval(departmentId, theEmployee, PstPosition.LEVEL_ASST_MANAGER, PstPosition.LEVEL_ASST_DIRECTOR);
    }

    public Vector listDepartmentApproval(long departmentId, Employee theEmployee, int minLevel, int maxLevel) {
        Position pos = new Position();
        try {
            pos = PstPosition.fetchExc(theEmployee.getPositionId());
        } catch (Exception exc) {
        }

        DBResultSet dbrs = null;
        Vector result = new Vector();

        try {

            String sql = "SELECT EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMAIL_ADDRESS]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME] + " FROM "
                    + PstEmployee.TBL_HR_EMPLOYEE + " EMP INNER JOIN "
                    + PstPosition.TBL_HR_POSITION + " POS ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID] + " = POS."
                    + PstPosition.fieldNames[PstPosition.FLD_POSITION_ID] + " INNER JOIN "
                    + PstLevel.TBL_HR_LEVEL + " LEV ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_LEVEL_ID] + " = LEV."
                    + PstLevel.fieldNames[PstLevel.FLD_LEVEL_ID] + " WHERE ( EMP."
                    + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] + " = " + departmentId
                    + " OR " + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] + " = (SELECT dd."
                    + PstDepartment.fieldNames[PstDepartment.FLD_JOIN_TO_DEPARTMENT_ID] + " FROM " + PstDepartment.TBL_HR_DEPARTMENT
                    + " dd WHERE dd." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID] + "=" + departmentId + " ) "
                    + " ) AND POS."
                    + PstPosition.fieldNames[PstPosition.FLD_DISABLED_APP_DEPT_SCOPE] + " = " + PstPosition.DISABLED_APP_DEPT_SCOPE_FALSE + " AND ( POS."
                    + PstPosition.fieldNames[PstPosition.FLD_POSITION_LEVEL] + " >= " + minLevel + " AND POS."
                    + PstPosition.fieldNames[PstPosition.FLD_POSITION_LEVEL] + " <= " + maxLevel + ") "
                    + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + " = " + PstEmployee.NO_RESIGN
                    + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + "<>" + theEmployee.getOID();
            /*+ " AND ( (POS." + PstPosition.fieldNames[PstPosition.FLD_POSITION_LEVEL] + ">" + pos.getPositionLevel() + " ) OR ("
            + "(POS." + PstPosition.fieldNames[PstPosition.FLD_POSITION_LEVEL] + "=" + pos.getPositionLevel() + " ) AND "
            + "(POS." + PstPosition.fieldNames[PstPosition.FLD_DISABLED_APP_DEPT_SCOPE] + "=" + PstPosition.DISABLED_APP_DEPT_SCOPE_FALSE + ")  "
            + " AND (" + (pos.getDisabledAppDeptScope() == PstPosition.DISABLED_APP_DEPT_SCOPE_FALSE ? "1=0" : "1=1") + ") ))";*/

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                Employee employee = new Employee();
                employee.setOID(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]));
                employee.setFullName(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]));
                employee.setEmailAddress(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_EMAIL_ADDRESS]));
                result.add(employee);
            }

            if (result != null && result.size() > 0) {
                return result;
            }

        } catch (Exception e) {
            System.out.println("EXCEPTION " + e.toString());
        } finally {
            DBResultSet.close(dbrs);
        }

        /*  if (result == null || result.size() < 1) {
        Department dept = null;
        try{
        dept=PstDepartment.fetchExc(departmentId);
        }catch(Exception ex){                
        }
        if(dept!=null && dept.getOID()!=0 && dept.getJoinToDepartmentId()!=0){
        result=PstEmployee.listEmployeeInSameDivisionAsDepartmentId(dept.getJoinToDepartmentId(), PstPosition.LEVEL_MANAGER, PstPosition.LEVEL_GENERAL_MANAGER);
        if (result != null && result.size() > 0) {
        return result;
        } 
        }
        }  */

        /* if (result == null || result.size() < 1) {
        result=PstEmployee.listEmployeeInSameDivisionAsDepartmentId(departmentId, PstPosition.LEVEL_MANAGER, PstPosition.LEVEL_GENERAL_MANAGER);
        if (result != null && result.size() > 0) {
        return result;
        } 
        } */
        return null;
    }

    public Vector listDivisionApproval(long division_id, long employee_id) {

        DBResultSet dbrs = null;
        Vector result = new Vector();

        try {

            String sql = "SELECT EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMAIL_ADDRESS]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME] + " FROM "
                    + PstEmployee.TBL_HR_EMPLOYEE + " EMP INNER JOIN " + PstPosition.TBL_HR_POSITION
                    + " POS ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID] + " = POS."
                    + PstPosition.fieldNames[PstPosition.FLD_POSITION_ID] + " INNER JOIN "
                    + PstLevel.TBL_HR_LEVEL + " LEV ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_LEVEL_ID] + " = LEV."
                    + PstLevel.fieldNames[PstLevel.FLD_LEVEL_ID] + " WHERE EMP."
                    + PstEmployee.fieldNames[PstEmployee.FLD_DIVISION_ID] + " = " + division_id + " AND POS."
                    + PstPosition.fieldNames[PstPosition.FLD_DISABLED_APP_DIV_SCOPE] + " = " + PstPosition.DISABLED_APP_DIV_SCOPE_FALSE + " AND POS."
                    + PstPosition.fieldNames[PstPosition.FLD_POSITION_LEVEL] + " IN  (" + PstPosition.LEVEL_MANAGER
                    + "," + PstPosition.LEVEL_ASST_DIRECTOR + "," + PstPosition.LEVEL_DIRECTOR + "," + PstPosition.LEVEL_GENERAL_MANAGER + " ) AND "
                    + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + " = " + PstEmployee.NO_RESIGN
                    + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + "<>" + employee_id;
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                Employee employee = new Employee();
                employee.setOID(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]));
                employee.setFullName(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]));
                employee.setEmailAddress(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_EMAIL_ADDRESS]));
                result.add(employee);
            }

            if (result != null && result.size() > 0) {
                return result;
            } else {
                return null;
            }
        } catch (Exception e) {
            System.out.println("EXCEPTION " + e.toString());
        } finally {
            DBResultSet.close(dbrs);
        }

        return null;
    }

    public Vector listHRManager(long employee_id) {

        DBResultSet dbrs = null;
        Vector result = new Vector();

        long hr_department = Long.parseLong(PstSystemProperty.getValueByName("OID_HRD_DEPARTMENT"));

        try {

            String sql = "SELECT EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMAIL_ADDRESS]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME] + " FROM "
                    + PstEmployee.TBL_HR_EMPLOYEE + " EMP INNER JOIN "
                    + PstPosition.TBL_HR_POSITION + " POS ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID] + " = POS."
                    + PstPosition.fieldNames[PstPosition.FLD_POSITION_ID] + " INNER JOIN "
                    + PstLevel.TBL_HR_LEVEL + " LEV ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_LEVEL_ID] + " = LEV."
                    + PstLevel.fieldNames[PstLevel.FLD_LEVEL_ID] + " WHERE EMP."
                    + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] + " = " + hr_department + " AND POS."
                    + PstPosition.fieldNames[PstPosition.FLD_DISABLED_APP_DEPT_SCOPE] + " = " + PstPosition.DISABLED_APP_DEPT_SCOPE_FALSE + " AND POS."
                    + PstPosition.fieldNames[PstPosition.FLD_POSITION_LEVEL] + " IN (" + PstPosition.LEVEL_SECRETARY + "," + PstPosition.LEVEL_SUPERVISOR + "," + PstPosition.LEVEL_ASST_MANAGER + "," + PstPosition.LEVEL_MANAGER + ","
                    + PstPosition.LEVEL_ASST_DIRECTOR + "," + PstPosition.LEVEL_DIRECTOR + ")" + " AND EMP."
                    + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + " = " + PstEmployee.NO_RESIGN;

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                Employee employee = new Employee();
                employee.setOID(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]));
                employee.setFullName(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]));
                employee.setEmailAddress(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_EMAIL_ADDRESS]));
                result.add(employee);
            }

            if (result != null && result.size() > 0) {
                return result;
            } else {
                return null;
            }


        } catch (Exception e) {
            System.out.println("EXCEPTION " + e.toString());
        } finally {
            DBResultSet.close(dbrs);
        }

        return null;
    }

    /**
     * @Desc Untuk mendapatkan periode dari entitle
     * 1. JIka type entitle by annual/ by periode maka akan direturn periodenya
     * 2. Sedangkan jika by commencing date maka akan direturn null
     * @return
     */
    public int getDatePeriod() {
        if (date != 0) {
            return date;
        } else {
            return 0;
        }
    }

    public int getMonthPeriod() {
        if (month != 0) {
            return month;
        } else {
            return 0;
        }
    }

    /**
     * @Author  Roy Andika
     * @Desc    Untuk mendapatkan waktu minimum bekerja karyawan mempunyai stock cuti
     * @return
     */
    public int getMinimalWorkAL() {
        if (iMinWorkToGetAnual >= 0) {
            return iMinWorkToGetAnual;
        } else {
            return 0;
        }
    }

    /**
     * @Author  Roy Andika
     * @Desc    Untuk mendapatkan waktu minimum karyawan itu bekerja boleh mengambil AL
     * @return
     */
    public int getMinimalWorkCanTakeAL() {
        if (iMinWorkCanGetAnnual >= 0) {
            return iMinWorkCanGetAnnual;
        } else {
            return 0;
        }
    }

    /* Untuk mendapatkan tanggal closing jika menggunakan Leave by Period */
    public int getDateClosingAnnual() {
        if (iDateClosingAnnual > 0) {
            return iDateClosingAnnual;
        } else {
            return 0;
        }
    }

    /* Untuk mendapatkan bulan closing jika menggunakan Leave by Period */
    public int getMonthClosingAnnual() {
        if (iMonthClosingAnnual > 0) {
            return iMonthClosingAnnual;
        } else {
            return 0;
        }
    }

    /**
     * @Desc Untuk mendapatkan limit date peride bulanan
     * Untuk mengecek batas tanggal untu menentukan periode bulanan, seperti bila limit adalah 17,
     * kemudian karyawan tersebut masuk tanggal 15,karena 15 kurang dari 17 maka akan dapat periode bulana pada bulan sekarang,
     * Sedangkan bila commencing date adalah 19, karena 19 lebih besar dari 17, maka periode bulanan yang didapt adalah periode berikutnya
     * @return
     */
    public int getLimitPeriode() {

        return dateLimit;

    }

    /**
     * @Author  Roy Andika
     * @param   leaveConfig
     * @param   entDt
     * @Desc    Untuk mendapatkan perhitungan stock Al dari setiap employee
     * @return
     */
    public float getQtyEntitle(Date entDt, String level, String employeeType, Date commencingDate) {

        int mnth = /*update by satrya 2013-11-12 entDt.getMonth() + 1*/ commencingDate.getMonth()+1;
        int tmpPeriod = 0;
        
        /**
         * if (mnth == 12) {
            tmpPeriod = 1;
        } 
         */
        int entitle=0;
        //konfigurasinya jika karyawan tsb commercing tgl 14 oktober 2013(atatu tgl 15 kebawah) maka dia berhak mendapatkan 3 
        //entitle karena dihitung dari bulan desember, lalu jika 15 oktober ke atas dia berhak menadatkan 2 entitle
           Date Closing_I = SessLeaveApplication.DATE_ADD(commencingDate, getMinimalWorkAL());
           
           if (Closing_I.getDate() < getLimitPeriode()) {
                 if (mnth == 12) {
                   tmpPeriod = 1;
                 }else {
                     tmpPeriod = mnth;
                 }
                entitle = getMonthPeriod() - tmpPeriod + 1;
           }else{
               if (mnth == 12) {
                   tmpPeriod = 1;
                 }else {
                     tmpPeriod = mnth;
                 }
                entitle = getMonthPeriod() - tmpPeriod;
           }
      
        /*if (commencingDate.getYear()==new Date().getYear() && mnth == 12) {
            tmpPeriod = 1;
        }
        else {
            tmpPeriod = mnth + 1;//KENAPA DI KURANGI SATU karena kasusnya jika commercing di bulan oktober ototmatis entitlenya 1 november jadi dimulai dari bulan okober
        }*/

        try {
            int iLevel = getIndexLevel(level);
            int iType = getIndexType(employeeType);

           // int entitle = getMonthPeriod() - tmpPeriod + 1;

            if (commencingDate != null) {
                int LoS = (int) DateCalc.dayDifference(commencingDate, entDt);
                  if (LoS >= (10 * 365) && (commencingDate.getMonth() == entDt.getMonth())) {
                    //update by satrya 2013-08-24
//                if (LoS >= (10 * 365)) {
                    entitle = entitle + iALEntitlesExtra[iLevel][iType];
                }
            }
            return entitle;
        } catch (Exception exc) {;
        }

        return 0;
    }

    /**
     * @Author  Roy Andika
     * @param   leaveConfig
     * @param   DtCommencing
     * @Desc    Untuk mendapatakan date entitle pertama bagi yang menggunakan konfigurasi leave menggunakan periode
     * @return
     */
    public Date getEntitle_I(Date DtCommencing, String level, String employeeType) {
        if (DtCommencing == null) {
            return null;
        }

        /* Untuk melakukan pengecekan untuk mengetahiu berapa lama karyawan tersebut bekerja setelah commencing date */
        Date Closing_I = SessLeaveApplication.DATE_ADD(DtCommencing, getMinimalWorkAL());
        String tmp = "";

        if (Closing_I.getTime() / (24L * 60L * 60L * 1000L) < new Date().getTime() / (24L * 60L * 60L * 1000L)) {
            //dimana karyawan tersebut telah melewati batas minimum dya bekerja untuk mendapatkan leave stock

            // jika kurang dari pada periode batas yang ditentukan, maka akan masuk ke periode itu
            if (Closing_I.getDate() < getLimitPeriode()) {

                int month_ent1 = 0;
                int dt_ent1 = 0;
                int year_ent1 = 0;

                int tmp_mnth_ent1 = Closing_I.getMonth() + 1;
                int tmp_year_ent1 = Closing_I.getYear() + 1900;
                dt_ent1 = getDatePeriod();

                month_ent1 = tmp_mnth_ent1 - 1;

              /*  //update by satrya 2013-11-13
               * if (tmp_mnth_ent1 == 1) {

                    month_ent1 = 12;
                    year_ent1 = tmp_year_ent1 - 1;

                } else {*/
                   //update by satrya 2013-11-12
                  /**
                   * month_ent1 = tmp_mnth_ent1 - 1;
                    year_ent1 = tmp_year_ent1;
                   */
                  //jika dia commercing tgl 14 desember 2013 maka entitlenya 1 january 2014, batasnya tgl 15
                  if(tmp_mnth_ent1==12){
                       dt_ent1 = 1;
                      month_ent1 = 1;
                      year_ent1 = tmp_year_ent1 + 1;
                  }else{
                    dt_ent1 = 1;
                    month_ent1 = tmp_mnth_ent1 + 1;
                    year_ent1 = tmp_year_ent1;
                  }

              /*  }*/

                tmp = "" + year_ent1 + "-" + month_ent1 + "-" + dt_ent1;

            } else {

                int month_ent1 = 0;
                int dt_ent1 = 0;
                int year_ent1 = 0;

                //update by satrya 2013-09-21
                //dt_ent1 = getDatePeriod();
                //mencari max date
                 
                /* update by satrya 2013-11-12 
                 * if(DtCommencing!=null){
                    Date tmpCommercing = (Date)DtCommencing.clone();
                    Calendar gre = Calendar.getInstance();
                    gre.setTime(tmpCommercing);
                    dt_ent1 = gre.getActualMaximum(Calendar.DAY_OF_MONTH);  
                }else{
                    dt_ent1 = getDatePeriod();
                }
                
                year_ent1 = Closing_I.getYear() + 1900;
                month_ent1 = Closing_I.getMonth() + 1;*/
                
                //jika commercingnya melebihi tanggal 15 , misalkan tgl 16 november maka entitlenya menjadi 1 desember 2013, dan jika commercingnya 16 desember 2013 maka entitlenya menjadi 1 january 2014
                 int tmp_mnth_ent1 = Closing_I.getMonth() + 1;
                int tmp_year_ent1 = Closing_I.getYear() + 1900;
                if(tmp_mnth_ent1==12){
                       dt_ent1 = 1;
                      month_ent1 = 1;
                      year_ent1 = tmp_year_ent1 + 1;
                  }else{
                    dt_ent1 = 1;
                    month_ent1 = tmp_mnth_ent1 + 2;
                    year_ent1 = tmp_year_ent1;
                  }

                tmp = "" + year_ent1 + "-" + month_ent1 + "-" + dt_ent1;
            }
        }
        return Formater.formatDate(tmp, "yyyy-MM-dd");
    }

    /**
     * @Author  Roy Andika
     * @Desc    Untuk mendapatakan Priode start dan end bila menggunakan periode
     * @param   commancingDate, dtStartm, leaveConfig
     * @return
     */
    public Date getPeriode(Date commancingDate, int dtStart) {

        int tgl = commancingDate.getDate();
        int mont = commancingDate.getMonth() + 1;
        int year = commancingDate.getYear() + 1900;

        String date = "";

        if (tgl >= 1 && tgl < dtStart) { //1 periode mulai dari tgl 26 sampai 25

            int tglPeriode = dtStart;
            int monPeriode = mont;
            int yearPeriode = year;

            date = "" + yearPeriode + "-" + monPeriode + "-" + tglPeriode;

        } else if (tgl >= dtStart) {

            int tglPeriode = dtStart;
            int monthPeriod;
            int yearPeriode;

            if (mont == 12) { /* Bila sudah bulan desember, maka bulan kembali ke januari dan tahun bertambah 1 */

                monthPeriod = 1;
                yearPeriode = year + 1;

            } else {

                monthPeriod = mont + 1;
                yearPeriode = year;
            }

            date = "" + yearPeriode + "-" + monthPeriod + "-" + tglPeriode;

        }

        try {
            Date tmp = Formater.formatDate(date, "yyyy-MM-dd");
            return tmp;
        } catch (Exception e) {
            System.out.println("Exception " + e.toString());
        }
        return null;
    }

    /**
     * @Author  Roy Andika
     * @Desc
     * @param   yer
     * @param   dtPeriod
     * @return
     */
    public Date getPeriodeClosing(Date yer, int dtPeriod) {

        Date cmDate = SessLeaveApplication.DATE_ADD(yer, 12);

        int diff = 0;

        diff = SessLeaveClosing.DATEDIFF(cmDate, new Date());

        if (diff > 0) { //jika belum satu tahun, maka entitle yang digunakan adalah commencing date.

            return yer;

        } else {

            int date = cmDate.getDate();
            int month = cmDate.getMonth() + 1;
            int year = cmDate.getYear() + 1900;

            int dateP = 0;
            int mnthP = 0;
            int yerP = 0;

            if (date < dtPeriod) {

                dateP = dtPeriod;
                mnthP = month;
                yerP = year;

            } else {
                dateP = dtPeriod;
                if (month == 12) {
                    mnthP = 1;
                    yerP = year + 1;
                } else {
                    mnthP = month + 1;
                    yerP = year;
                }
            }

            String tmp = "" + yerP + "-" + mnthP + "-" + dateP;
            Date perDate = new Date();

            try {

                perDate = Formater.formatDate(tmp, "yyyy-MM-dd");
                return perDate;

            } catch (Exception e) {
                System.out.println("Exception " + e.toString());
            }
        }
        return null;
    }

    /**
     * @Author  Roy Andika
     * @param   leaveConfig
     */
    public void procesGetEntitleAutomatic() {
    }

    /**
     * @Author  Roy Andika
     * @Desc    Untuk mendapatkan periode closing bulanan
     */
    public Date getPeriodMonth() {
        return null;
    }

    public String getFormatLeave() {
        return "#,###.###";
    }

    public float getHourOneWorkday() {
        return 8f;// 1 hari kerja = 8 jam   
    }

    public String setAutoALEntitle(Date startDate) {
        //public String setAutoALEntitle(Date startDate) {
        String sysMessage = "";
        int yearMin = 10;// KTI , karyawan akan dapat tambahan cuti di ultah ke 10 
        
        Date today = startDate!=null?startDate:new Date();
        //hidden by satrya 2013-08-23
//        today.setDate(startDate.getDate());
//        today.setMonth(startDate.getMonth());
//        today.setYear(startDate.getYear());
        // Date today = new Date();
        Date prev10Year = new Date(today.getYear() - 10, today.getMonth(), today.getDate(), 23, 59, 59);
        Date prev10YearMin7day = new Date(today.getTime() - (7L * 24L * 60L * 60L * 1000L));
        Date todaysEntitle = new Date();
        todaysEntitle.setDate(1);
        todaysEntitle.setMonth(0);// ini di set bulan january tergantung configurasi Leave
        String strWhere = 
                /*"((" + (today.getYear() + 1900) + "-LEFT(" + PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]
                + ",4))>=" + yearMin + ") AND " + PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE] + " LIKE \"%-"
                + Formater.formatDate(today, "MM-dd") + "\"";*/
                "(("+(today.getYear() + 1900) + " -YEAR("+PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]+"))>=10) AND MONTH("+PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]+") <="
                +(today.getMonth()+1)+" AND DAY("+PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]+")<="+today.getDate();
         
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT e." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + ","
                    + " e." + PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE] + ","
                    + " e." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME] + ","
                    + "l." + PstLevel.fieldNames[PstLevel.FLD_LEVEL] + ","
                    + "c." + PstEmpCategory.fieldNames[PstEmpCategory.FLD_EMP_CATEGORY]
                    + " FROM " + PstEmployee.TBL_HR_EMPLOYEE + " AS e"
                    + " INNER JOIN " + PstLevel.TBL_HR_LEVEL + " AS l ON "
                    + " l." + PstLevel.fieldNames[PstLevel.FLD_LEVEL_ID] + "=e." + PstEmployee.fieldNames[PstEmployee.FLD_LEVEL_ID]
                    + " INNER JOIN " + PstEmpCategory.TBL_HR_EMP_CATEGORY + " AS c ON "
                    + " c." + PstEmpCategory.fieldNames[PstEmpCategory.FLD_EMP_CATEGORY_ID] + "=e." + PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID]
                    + " WHERE " + strWhere
                    + " LIMIT 0,10000";

            dbrs = DBHandler.execQueryResult(sql);
           ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                //hanya untuk test
            long oidempTest=10500l;
            if(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]).equalsIgnoreCase("SWARTINI")){
              boolean test=true;
            }
            //hanya untuk test
            long oidempTestx=10406;
            if(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]).equalsIgnoreCase("I WAYAN SUKARTA")){
              boolean test=true;
            }
            if(oidempTestx==rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID])){
              boolean x=true;  
            }
            
            if(oidempTest==rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID])){
             boolean test = true;
            }
                float AlEnt = getALEntitleAnualLeavePlusTiga(// update by satrya 2013-10-21 getALEntitleAnualLeave 
                        rs.getString(PstLevel.fieldNames[PstLevel.FLD_LEVEL]),
                        rs.getString(PstEmpCategory.fieldNames[PstEmpCategory.FLD_EMP_CATEGORY]),
                        0, rs.getDate(PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]),
                        today);
                if (AlEnt > 0.0F) {
                   /* if (sysMessage.length() < 1) {
                        sysMessage = "Employee(s) have new AL entitle : ";
                    }*/
///update by satrya 2012-10-16
                    String where = PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_EMPLOYEE_ID]
                            + " = " + rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]) + " AND "
                            + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_AL_STATUS] + "="
                            + PstAlStockManagement.AL_STS_AKTIF;

                    Vector listAlAktif = PstAlStockManagement.list(0, 0, where, null);
                    
              //int iLevel = getIndexLevel(rs.getString(PstLevel.fieldNames[PstLevel.FLD_LEVEL]));
            //int iType = getIndexType(rs.getString(PstEmpCategory.fieldNames[PstEmpCategory.FLD_EMP_CATEGORY]));
            
                    AlStockManagement alStockManagement = new AlStockManagement();
                    AlStockManagement alStockManagementX = new AlStockManagement();
                    if (listAlAktif != null && listAlAktif.size() > 0) {
                        alStockManagement = (AlStockManagement) listAlAktif.get(0);
                    //u[pdate by satrya 2013-02-25
                        //jika extra Al == 0 artinya belum di tambakan cuti +3 pada sebelum tutp periode
                    if(alStockManagement.getExtraAl()==0){
                        alStockManagement.setEntitled(alStockManagement.getEntitled()+AlEnt);
                        alStockManagement.setAlQty(alStockManagement.getPrevBalance()+alStockManagement.getEntitled());
                        //update by satrya 2013-10-24
                        alStockManagement.setQtyResidue(alStockManagement.getAlQty()-alStockManagement.getQtyUsed());
                        //update by satrya 2013-08-24
                        // alStockManagement.setAlQty(AlEnt);
                   if (sysMessage.length() < 1) {
                        sysMessage = "Employee(s) have new AL entitle : ";
                    }
                        //alStockManagement.setEntitleDate(today);
                        //update by satrya 2013-02-25
                   //hidden by satrya 2013-10-21
                       //alStockManagement.setEntitleDate(todaysEntitle);/// sesuai konfigurasi / jika tahunan ( untuk KTI di pakai berdasarkan commercingdate)

                       alStockManagement.setExtraAl(AlEnt/* update by satrya 2013-10-21 iALEntitlesExtra[iLevel][iType]*/); // ini yang di dapatkan oleh karyawan (3.0)
                       //dibuatkan fungsi untuk mencari alStockManagement.getExtraAlDate() hanya mengecek jika dia sudah di tambah 10 tahun comercing datnenya, diambil statusnya yg paling akhir
                       //mecari extraAl sebelumnya
                       String whereX = PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_ENTITLE_DATE]  + " < (SELECT AL1."+ PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_ENTITLE_DATE] + " FROM "+PstAlStockManagement.TBL_AL_STOCK_MANAGEMENT +" AL1 WHERE AL1."+PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_AL_STOCK_ID]+"="+alStockManagement.getOID()+")" 
                        + " AND "+PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_EMPLOYEE_ID]+ " = "+rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]);
                       
                       String order = PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_ENTITLE_DATE]+" DESC ";
                        Vector getUseExtraAL = PstAlStockManagement.list(0, 0, whereX, order);
                              
                           if(getUseExtraAL!=null && getUseExtraAL.size()>0){
                             alStockManagementX  = (AlStockManagement) getUseExtraAL.get(0);
                           }
                           
                       if(alStockManagementX.getExtraAlDate()==null){
                           
                           if(rs.getDate(PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE])!=null){
                               Date extraAl = rs.getDate(PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]);
                               //int year = (rs.getDate(PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]).getYear()+1900)+10;
                               extraAl.setYear((rs.getDate(PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]).getYear())+10);
                               
                               alStockManagement.setExtraAlDate(extraAl); // by commercing date + 10 //dan setelah itu ketika sdh lebih dari 10 th tinggal di tambah 1
                           }
                           
                       }else{
                           //update by devin 2014-02-28
                           //if(rs.getDate(PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE])!=null){
                           if(rs.getDate(PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE])!=null && alStockManagement.getExtraAlDate() !=null){
                               Date extraAl = alStockManagement.getExtraAlDate();
                               extraAl.setYear( (alStockManagement.getExtraAlDate().getYear()+1900)+1);
                               alStockManagement.setExtraAlDate(extraAl); // by commercing date + 10 //dan setelah itu ketika sdh lebih dari 10 th tinggal di tambah 1
                           }
                       }
                        try {
                            PstAlStockManagement.updateExc(alStockManagement);
                            sysMessage = sysMessage + rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]) + "=" + AlEnt + "; ";
                        } catch (Exception e) {
                            System.out.println("Exeption " + e.toString());
                        }
                     }
                    }
                }
            }
            rs.close();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }

        //return "";
        return sysMessage;
    }

    public Vector<Employee> overtimeRequester(long overtimeId) {
        if(overtimeId==0){
            return new Vector();
        }
        try{        
            Overtime overtime =  PstOvertime.fetchExc(overtimeId);  
            Vector listMaxEmp = PstOvertimeDetail.maxLevelEmployees(overtimeId);
            Department department = PstDepartment.fetchExc(overtime.getDepartmentId());
            int maxLevel =0;
            boolean hod = false;
            boolean divHead =false;
            long employeeId=0;
            if(listMaxEmp!=null){
                for(int idx=0;idx < listMaxEmp.size();idx++){
                    OvertimeDetail ovd = (OvertimeDetail) listMaxEmp.get(idx); 
                    maxLevel = ovd.getPositionLevel();                                        
                    if(!hod && !divHead){
                        employeeId = ovd.getEmployeeId();
                    }
                    if(ovd.getPositionDisableDepartmentApproval()==PstPosition.DISABLED_APP_DEPT_SCOPE_FALSE &&
                            ovd.getPositionLevel() >= PstPosition.LEVEL_ASST_MANAGER){
                       hod = true;
                       employeeId = ovd.getEmployeeId();
                    }
                    if(ovd.getPositionDisableDivisionApproval()==PstPosition.DISABLED_APP_DIV_SCOPE_FALSE &&
                            ovd.getPositionLevel() >= PstPosition.LEVEL_ASST_MANAGER){
                      divHead =true;
                      employeeId = ovd.getEmployeeId();
                      break;
                    }                    
                }
            }
            
            Position position = new Position();
            position.setPositionLevel(maxLevel);
            position.setDisabedAppDivisionScope(divHead?0:1);
            position.setDisabledAppDeptScope(hod?0:1);
            Division division = new Division();
            division.setOID(department.getDivisionId());
            Employee employee = new Employee();
            employee.setOID(employeeId);
            
            Vector listEmployee = new Vector();
            Vector listEmpDepartment = new Vector();
            Vector listEmpDivision= new Vector();
            
            Employee emGM = null;
            Employee emHRDir = null;
            Employee emHRMan = null;
            try {
                long oidGM = Long.parseLong(PstSystemProperty.getValueByName("GM_POS_ID"));
                if (oidGM != 0) {
                    if (!existEmployee(oidGM, listEmployee)) {
                        emGM = PstEmployee.getEmployeeByPositionId(oidGM);
                    };
                }
            } catch (Exception E) {
                emGM = new Employee();
                emGM.setFullName("Please set OID for General Manager in System Ppoperty : GM");
            }
            try {
                long oidHRDir = Long.parseLong(PstSystemProperty.getValueByName("HR_DIR_POS_ID"));
                if (oidHRDir != 0 && oidHRDir != employee.getOID()) {
                    emHRDir = PstEmployee.getEmployeeByPositionId(oidHRDir);
                }
            } catch (Exception E) {
                emGM = new Employee();
                emGM.setFullName("HR Director not set in System Property : HR_DIRECTOR");
            }
            try {
                long oidHRMan = Long.parseLong(PstSystemProperty.getValueByName("HR_MAN_POS_ID"));
                if (oidHRMan != 0 && oidHRMan != employee.getOID()) { // && !existEmployee(oidHRMan, listEmployee)) {
                    emHRMan = PstEmployee.getEmployeeByPositionId(oidHRMan);
                }
            } catch (Exception E) {
                emGM = new Employee();
                emGM.setFullName("HR Director not set in System Property : HR_DIRECTOR");
            }            
            
            boolean DivHead = false;
            boolean HOD = true;
            if (position.getPositionLevel() >= PstPosition.LEVEL_GENERAL && position.getPositionLevel() <= PstPosition.LEVEL_ASST_MANAGER) { // staff to manager
                if (position.getPositionLevel() == PstPosition.LEVEL_ASST_MANAGER && position.getDisabledAppDeptScope() == 0) { // Ast. Manager as HOD
                        listEmpDivision = listDivisionApproval(division.getOID(), employee.getOID());
                        if (listEmpDivision != null && listEmpDivision.size() > 0) {
                            for (int i = 0; i < listEmpDivision.size(); i++) {
                                Employee objEmployee = new Employee();
                                objEmployee = (Employee) listEmpDivision.get(i);
                                if (!existEmployee(objEmployee.getOID(), listEmployee)) {
                                    listEmployee.add(objEmployee);
                                };
                            }
                            DivHead = true;
                        }
                        if(!existEmployee(emHRDir.getOID(), listEmployee)){
                            listEmployee.add(emHRDir);
                        }
                        /*if(!existEmployee(emGM.getOID(), listEmployee)){
                            listEmployee.add(emGM);
                        } */                       
                } else {
                    // staff to ast. manager
                    listEmpDepartment = listDepartmentApproval(department.getOID(), employee, PstPosition.LEVEL_ASST_MANAGER, PstPosition.LEVEL_MANAGER);
                    listEmpDivision = listDivisionApproval(division.getOID(), employee.getOID());
                    if (listEmpDepartment != null && listEmpDepartment.size() > 0) {
                        for (int i = 0; i < listEmpDepartment.size(); i++) {
                            Employee objEmployee = new Employee();
                            objEmployee = (Employee) listEmpDepartment.get(i);
                            if (!existEmployee(objEmployee.getOID(), listEmployee)) {
                                listEmployee.add(objEmployee);
                            };
                        }
                        HOD = true;
                    }

                    if (listEmpDivision != null && listEmpDivision.size() > 0) {
                        for (int i = 0; i < listEmpDivision.size(); i++) {
                            Employee objEmployee = new Employee();
                            objEmployee = (Employee) listEmpDivision.get(i);
                            if (!existEmployee(objEmployee.getOID(), listEmployee)) {
                                listEmployee.add(objEmployee);
                            };
                        }
                        DivHead = true;
                    }

                    if ((HOD && !DivHead) || (!HOD && DivHead)) { // HOD found but not Div Head
                        if(!existEmployee(emHRMan.getOID(), listEmployee)){
                            listEmployee.add(emHRMan);
                        }
                        if(!existEmployee(emHRDir.getOID(), listEmployee)){
                            listEmployee.add(emHRDir);
                        }
                    }

                    if (!HOD && !DivHead) {
                        if(!existEmployee(emHRMan.getOID(), listEmployee)){
                            listEmployee.add(emHRMan);
                        }
                        if(!existEmployee(emHRDir.getOID(), listEmployee)){
                            listEmployee.add(emHRDir);
                        }
                        if(!existEmployee(emGM.getOID(), listEmployee)){
                            listEmployee.add(emGM);
                        }                        
                    }
                }
            } else {
                if (position.getPositionLevel() == PstPosition.LEVEL_MANAGER) {
                    if (position.getDisabledAppDeptScope() != 0) { // Non HOD manager
                        listEmpDepartment = listDepartmentApproval(department.getOID(), employee, PstPosition.LEVEL_MANAGER, PstPosition.LEVEL_MANAGER);
                        listEmpDivision = listDivisionApproval(division.getOID(), employee.getOID());
                        if (listEmpDepartment != null && listEmpDepartment.size() > 0) {
                            for (int i = 0; i < listEmpDepartment.size(); i++) {
                                Employee objEmployee = new Employee();
                                objEmployee = (Employee) listEmpDepartment.get(i);
                                if (!existEmployee(objEmployee.getOID(), listEmployee)) {
                                    listEmployee.add(objEmployee);
                                };
                            }
                            HOD = true;
                        }

                        if (listEmpDivision != null && listEmpDivision.size() > 0) {
                            for (int i = 0; i < listEmpDivision.size(); i++) {
                                Employee objEmployee = new Employee();
                                objEmployee = (Employee) listEmpDivision.get(i);
                                if (!existEmployee(objEmployee.getOID(), listEmployee)) {
                                    listEmployee.add(objEmployee);
                                };
                            }
                            DivHead = true;
                        }

                        if (!HOD) {
                        if(!existEmployee(emHRDir.getOID(), listEmployee)){
                            listEmployee.add(emHRDir);
                        }
                        if(!existEmployee(emGM.getOID(), listEmployee)){
                            listEmployee.add(emGM);
                        }                        
                        }

                    } else {
                        // HOD manager
                        listEmpDivision = listDivisionApproval(division.getOID(), employee.getOID());
                        if (listEmpDivision != null && listEmpDivision.size() > 0) {
                            for (int i = 0; i < listEmpDivision.size(); i++) {
                                Employee objEmployee = new Employee();
                                objEmployee = (Employee) listEmpDivision.get(i);
                                if (!existEmployee(objEmployee.getOID(), listEmployee)) {
                                    listEmployee.add(objEmployee);
                                };
                            }
                            DivHead = true;
                        }
                        if(!existEmployee(emHRDir.getOID(), listEmployee)){
                            listEmployee.add(emHRDir);
                        }
                        if(!existEmployee(emGM.getOID(), listEmployee)){
                            listEmployee.add(emGM);
                        }                        
                    }
                } else {
                    if (position.getPositionLevel() == PstPosition.LEVEL_ASST_DIRECTOR) {
                        if (position.getDisabedAppDivisionScope() == 0) { // level ast director as Div.Head                                 
                            if(!existEmployee(emHRDir.getOID(), listEmployee)){
                                listEmployee.add(emHRDir);
                            }
                            if(!existEmployee(emGM.getOID(), listEmployee)){
                                listEmployee.add(emGM);
                            }                        
                        } else { // level ast director non div head 
                            listEmpDivision = listDivisionApproval(division.getOID(), employee.getOID());
                            if (listEmpDivision != null && listEmpDivision.size() > 0) {
                                for (int i = 0; i < listEmpDivision.size(); i++) {
                                    Employee objEmployee = new Employee();
                                    objEmployee = (Employee) listEmpDivision.get(i);
                                    if (!existEmployee(objEmployee.getOID(), listEmployee)) {
                                        listEmployee.add(objEmployee);
                                    };
                                }
                                DivHead = true;
                            }
                            if(!existEmployee(emHRDir.getOID(), listEmployee)){
                                listEmployee.add(emHRDir);
                            }
                            if(!existEmployee(emGM.getOID(), listEmployee)){
                                listEmployee.add(emGM);
                            }                        
                        }
                    } else {
                        if (position.getPositionLevel() == PstPosition.LEVEL_DIRECTOR) {
                            if (position.getDisabedAppDivisionScope() == 0) { // level director as Div.Head                                 
                                if(!existEmployee(emHRDir.getOID(), listEmployee)){
                                    listEmployee.add(emHRDir);
                                }
                                if(!existEmployee(emGM.getOID(), listEmployee)){
                                    listEmployee.add(emGM);
                                }                        
                            } else { // non div head 
                                listEmpDivision = listDivisionApproval(division.getOID(), employee.getOID());
                                if (listEmpDivision != null && listEmpDivision.size() > 0) {
                                    for (int i = 0; i < listEmpDivision.size(); i++) {
                                        Employee objEmployee = new Employee();
                                        objEmployee = (Employee) listEmpDivision.get(i);
                                        if (!existEmployee(objEmployee.getOID(), listEmployee)) {
                                            listEmployee.add(objEmployee);
                                        };
                                    }
                                    DivHead = true;
                                }
                                if(!existEmployee(emHRDir.getOID(), listEmployee)){
                                    listEmployee.add(emHRDir);
                                }
                                if(!existEmployee(emGM.getOID(), listEmployee)){
                                    listEmployee.add(emGM);
                                }                        
                            }
                        } else {
                            if (position.getPositionLevel() == PstPosition.LEVEL_GENERAL_MANAGER) {
                                if(!existEmployee(emGM.getOID(), listEmployee)){
                                    listEmployee.add(emGM);
                                }                        
                            }                            
                        }
                    }
                }
            }
                    
            
            
            return listEmployee;//PstEmployee.listEmployeeSupervisi(overtime.getDepartmentId(), 0L, PstPosition.LEVEL_ASST_MANAGER, PstPosition.LEVEL_DIRECTOR);
        }catch(Exception exc){
            return new Vector();
        }
        
    }

    
    public Vector<Employee> overtimeApprover(long overtimeId) {
        if(overtimeId==0){
            return new Vector();
        }
        try{        
            Overtime overtime =  PstOvertime.fetchExc(overtimeId);  
            Vector listMaxEmp = PstOvertimeDetail.maxLevelEmployees(overtimeId);
            Department department = PstDepartment.fetchExc(overtime.getDepartmentId());
            int maxLevel =0;
            boolean hod = false;
            boolean divHead =false;
            long employeeId=0;
            if(listMaxEmp!=null){
                for(int idx=0;idx < listMaxEmp.size();idx++){
                    OvertimeDetail ovd = (OvertimeDetail) listMaxEmp.get(idx); 
                    maxLevel = ovd.getPositionLevel();                                        
                    if(!hod && !divHead){
                        employeeId = ovd.getEmployeeId();
                    }
                    if(ovd.getPositionDisableDepartmentApproval()==PstPosition.DISABLED_APP_DEPT_SCOPE_FALSE &&
                            ovd.getPositionLevel() >= PstPosition.LEVEL_ASST_MANAGER){
                       hod = true;
                       employeeId = ovd.getEmployeeId();
                    }
                    if(ovd.getPositionDisableDivisionApproval()==PstPosition.DISABLED_APP_DIV_SCOPE_FALSE &&
                            ovd.getPositionLevel() >= PstPosition.LEVEL_ASST_MANAGER){
                      divHead =true;
                      employeeId = ovd.getEmployeeId();
                      break;
                    }                    
                }
            }
            
            Position position = new Position();
            position.setPositionLevel(maxLevel);
            position.setDisabedAppDivisionScope(divHead?0:1);
            position.setDisabledAppDeptScope(hod?0:1);
            Division division = new Division();
            division.setOID(department.getDivisionId());
            Employee employee = new Employee();
            employee.setOID(employeeId);
            
            Vector listEmployee = new Vector();
            Vector listEmpDepartment = new Vector();
            Vector listEmpDivision= new Vector();
            
            Employee emGM = null;
            Employee emHRDir = null;
            Employee emHRMan = null;
            try {
                long oidGM = Long.parseLong(PstSystemProperty.getValueByName("GM_POS_ID"));
                if (oidGM != 0) {
                    if (!existEmployee(oidGM, listEmployee)) {
                        emGM = PstEmployee.getEmployeeByPositionId(oidGM);
                    };
                }
            } catch (Exception E) {
                emGM = new Employee();
                emGM.setFullName("Please set OID for General Manager in System Ppoperty : GM");
            }
            try {
                long oidHRDir = Long.parseLong(PstSystemProperty.getValueByName("HR_DIR_POS_ID"));
                if (oidHRDir != 0 && oidHRDir != employee.getOID()) {
                    emHRDir = PstEmployee.getEmployeeByPositionId(oidHRDir);
                }
            } catch (Exception E) {
                emGM = new Employee();
                emGM.setFullName("HR Director not set in System Property : HR_DIRECTOR");
            }
            try {
                long oidHRMan = Long.parseLong(PstSystemProperty.getValueByName("HR_MAN_POS_ID"));
                if (oidHRMan != 0 && oidHRMan != employee.getOID()) { // && !existEmployee(oidHRMan, listEmployee)) {
                    emHRMan = PstEmployee.getEmployeeByPositionId(oidHRMan);
                }
            } catch (Exception E) {
                emGM = new Employee();
                emGM.setFullName("HR Director not set in System Property : HR_DIRECTOR");
            }            
            
            boolean DivHead = false;
            boolean HOD = true;
            if (position.getPositionLevel() >= PstPosition.LEVEL_GENERAL && position.getPositionLevel() <= PstPosition.LEVEL_ASST_MANAGER) { // staff to manager
                if (position.getPositionLevel() == PstPosition.LEVEL_ASST_MANAGER && position.getDisabledAppDeptScope() == 0) { // Ast. Manager as HOD
                        listEmpDivision = listDivisionApproval(division.getOID(), employee.getOID());
                        if (listEmpDivision != null && listEmpDivision.size() > 0) {
                            for (int i = 0; i < listEmpDivision.size(); i++) {
                                Employee objEmployee = new Employee();
                                objEmployee = (Employee) listEmpDivision.get(i);
                                if (!existEmployee(objEmployee.getOID(), listEmployee)) {
                                    listEmployee.add(objEmployee);
                                };
                            }
                            DivHead = true;
                        }
                        if(!existEmployee(emHRDir.getOID(), listEmployee)){
                            listEmployee.add(emHRDir);
                        }
                        if(!existEmployee(emGM.getOID(), listEmployee)){
                            listEmployee.add(emGM);
                        }                        
                } else {
                    // staff to ast. manager
                    listEmpDepartment = listDepartmentApproval(department.getOID(), employee, PstPosition.LEVEL_ASST_MANAGER, PstPosition.LEVEL_MANAGER);
                    listEmpDivision = listDivisionApproval(division.getOID(), employee.getOID());
                    if (listEmpDepartment != null && listEmpDepartment.size() > 0) {
                        for (int i = 0; i < listEmpDepartment.size(); i++) {
                            Employee objEmployee = new Employee();
                            objEmployee = (Employee) listEmpDepartment.get(i);
                            if (!existEmployee(objEmployee.getOID(), listEmployee)) {
                                listEmployee.add(objEmployee);
                            };
                        }
                        HOD = true;
                    }

                    if (listEmpDivision != null && listEmpDivision.size() > 0) {
                        for (int i = 0; i < listEmpDivision.size(); i++) {
                            Employee objEmployee = new Employee();
                            objEmployee = (Employee) listEmpDivision.get(i);
                            if (!existEmployee(objEmployee.getOID(), listEmployee)) {
                                listEmployee.add(objEmployee);
                            };
                        }
                        DivHead = true;
                    }

                    if ((HOD && !DivHead) || (!HOD && DivHead)) { // HOD found but not Div Head
                        if(!existEmployee(emHRMan.getOID(), listEmployee)){
                            listEmployee.add(emHRMan);
                        }
                        if(!existEmployee(emHRDir.getOID(), listEmployee)){
                            listEmployee.add(emHRDir);
                        }
                    }

                    if (!HOD && !DivHead) {
                        if(!existEmployee(emHRMan.getOID(), listEmployee)){
                            listEmployee.add(emHRMan);
                        }
                        if(!existEmployee(emHRDir.getOID(), listEmployee)){
                            listEmployee.add(emHRDir);
                        }
                        if(!existEmployee(emGM.getOID(), listEmployee)){
                            listEmployee.add(emGM);
                        }                        
                    }
                }
            } else {
                if (position.getPositionLevel() == PstPosition.LEVEL_MANAGER) {
                    if (position.getDisabledAppDeptScope() != 0) { // Non HOD manager
                        listEmpDepartment = listDepartmentApproval(department.getOID(), employee, PstPosition.LEVEL_MANAGER, PstPosition.LEVEL_MANAGER);
                        listEmpDivision = listDivisionApproval(division.getOID(), employee.getOID());
                        if (listEmpDepartment != null && listEmpDepartment.size() > 0) {
                            for (int i = 0; i < listEmpDepartment.size(); i++) {
                                Employee objEmployee = new Employee();
                                objEmployee = (Employee) listEmpDepartment.get(i);
                                if (!existEmployee(objEmployee.getOID(), listEmployee)) {
                                    listEmployee.add(objEmployee);
                                };
                            }
                            HOD = true;
                        }

                        if (listEmpDivision != null && listEmpDivision.size() > 0) {
                            for (int i = 0; i < listEmpDivision.size(); i++) {
                                Employee objEmployee = new Employee();
                                objEmployee = (Employee) listEmpDivision.get(i);
                                if (!existEmployee(objEmployee.getOID(), listEmployee)) {
                                    listEmployee.add(objEmployee);
                                };
                            }
                            DivHead = true;
                        }

                        if (!HOD) {
                        if(!existEmployee(emHRDir.getOID(), listEmployee)){
                            listEmployee.add(emHRDir);
                        }
                        if(!existEmployee(emGM.getOID(), listEmployee)){
                            listEmployee.add(emGM);
                        }                        
                        }

                    } else {
                        // HOD manager
                        listEmpDivision = listDivisionApproval(division.getOID(), employee.getOID());
                        if (listEmpDivision != null && listEmpDivision.size() > 0) {
                            for (int i = 0; i < listEmpDivision.size(); i++) {
                                Employee objEmployee = new Employee();
                                objEmployee = (Employee) listEmpDivision.get(i);
                                if (!existEmployee(objEmployee.getOID(), listEmployee)) {
                                    listEmployee.add(objEmployee);
                                };
                            }
                            DivHead = true;
                        }
                        if(!existEmployee(emHRDir.getOID(), listEmployee)){
                            listEmployee.add(emHRDir);
                        }
                        if(!existEmployee(emGM.getOID(), listEmployee)){
                            listEmployee.add(emGM);
                        }                        
                    }
                } else {
                    if (position.getPositionLevel() == PstPosition.LEVEL_ASST_DIRECTOR) {
                        if (position.getDisabedAppDivisionScope() == 0) { // level ast director as Div.Head                                 
                            if(!existEmployee(emHRDir.getOID(), listEmployee)){
                                listEmployee.add(emHRDir);
                            }
                            if(!existEmployee(emGM.getOID(), listEmployee)){
                                listEmployee.add(emGM);
                            }                        
                        } else { // level ast director non div head 
                            listEmpDivision = listDivisionApproval(division.getOID(), employee.getOID());
                            if (listEmpDivision != null && listEmpDivision.size() > 0) {
                                for (int i = 0; i < listEmpDivision.size(); i++) {
                                    Employee objEmployee = new Employee();
                                    objEmployee = (Employee) listEmpDivision.get(i);
                                    if (!existEmployee(objEmployee.getOID(), listEmployee)) {
                                        listEmployee.add(objEmployee);
                                    };
                                }
                                DivHead = true;
                            }
                            if(!existEmployee(emHRDir.getOID(), listEmployee)){
                                listEmployee.add(emHRDir);
                            }
                            if(!existEmployee(emGM.getOID(), listEmployee)){
                                listEmployee.add(emGM);
                            }                        
                        }
                    } else {
                        if (position.getPositionLevel() == PstPosition.LEVEL_DIRECTOR) {
                            if (position.getDisabedAppDivisionScope() == 0) { // level director as Div.Head                                 
                                if(!existEmployee(emHRDir.getOID(), listEmployee)){
                                    listEmployee.add(emHRDir);
                                }
                                if(!existEmployee(emGM.getOID(), listEmployee)){
                                    listEmployee.add(emGM);
                                }                        
                            } else { // non div head 
                                listEmpDivision = listDivisionApproval(division.getOID(), employee.getOID());
                                if (listEmpDivision != null && listEmpDivision.size() > 0) {
                                    for (int i = 0; i < listEmpDivision.size(); i++) {
                                        Employee objEmployee = new Employee();
                                        objEmployee = (Employee) listEmpDivision.get(i);
                                        if (!existEmployee(objEmployee.getOID(), listEmployee)) {
                                            listEmployee.add(objEmployee);
                                        };
                                    }
                                    DivHead = true;
                                }
                                if(!existEmployee(emHRDir.getOID(), listEmployee)){
                                    listEmployee.add(emHRDir);
                                }
                                if(!existEmployee(emGM.getOID(), listEmployee)){
                                    listEmployee.add(emGM);
                                }                        
                            }
                        } else {
                            if (position.getPositionLevel() == PstPosition.LEVEL_GENERAL_MANAGER) {
                                if(!existEmployee(emGM.getOID(), listEmployee)){
                                    listEmployee.add(emGM);
                                }                        
                            }                            
                        }
                    }
                }                
            }    
             return listEmployee;
            //return PstEmployee.listEmployeeSupervisi(overtime.getDepartmentId(), 0L, PstPosition.LEVEL_ASST_MANAGER, PstPosition.LEVEL_DIRECTOR);
        }catch(Exception exc){
            return new Vector();
        }
       
    }


    public Vector<Employee> overtimeFinalApprover(long overtimeId) {
            Vector listEmployee = new Vector();
            Employee emGM = null;
            Employee emHRDir = null;
            Employee emHRMan = null;
            try {
                long oidGM = Long.parseLong(PstSystemProperty.getValueByName("GM_POS_ID"));
                if (oidGM != 0) {
                    if (!existEmployee(oidGM, listEmployee)) {
                        emGM = PstEmployee.getEmployeeByPositionId(oidGM);
                        listEmployee.add(emGM);
                    };
                }
            } catch (Exception E) {
                emGM = new Employee();
                emGM.setFullName("Please set OID for General Manager in System Ppoperty : GM");
            }
            try {
                long oidHRDir = Long.parseLong(PstSystemProperty.getValueByName("HR_DIR_POS_ID"));
                if (oidHRDir != 0) {
                    emHRDir = PstEmployee.getEmployeeByPositionId(oidHRDir);
                    listEmployee.add(emHRDir);
                }
            } catch (Exception E) {
                emGM = new Employee();
                emGM.setFullName("HR Director not set in System Property : HR_DIRECTOR");
            }
        return listEmployee;
    }

    public boolean getAutomaticDPStockTaken() {
        return false;
    }

    public String setAutoALEntitle(Date startDate, long employeeId) {
       return "";
    }

    public boolean getDPUnpaidIsNull() {
       return true;
    }

    public boolean getDPEligibleMinus(float eligbleDay) {
       if(eligbleDay > 0){
            return true;
        }else{
            return false;
        }
    }

    public boolean getDPExpired() {
       return true;
    }

    public boolean getDPStockMinus(DpStockManagement dpStockManagement, DpStockTaken dpStockTaken) {
       if(dpStockManagement.getiDpQty()<dpStockTaken.getTakenQty()){
           return true;
       }else{
           return false;
       }
    }

    public boolean getDPPaidPayable() {
        return false;
    }

    public boolean getLLShowEntile2() {
        return true;
    }
    
    public String setAutoLLEntitle(Date startDate,I_Leave leave) {
        return "";
    }

    public Vector getAlValExtend() {
        Vector vectValExtend = new Vector();
        vectValExtend.add(""+EXTEND_12_MONTH);
        vectValExtend.add(""+EXTEND_6_MONTH);
        vectValExtend.add(""+EXTEND_3_MONTH);
        vectValExtend.add(""+EXTEND_2_MONTH);
        vectValExtend.add(""+EXTEND_1_MONTH);
        return  vectValExtend;     
    }

    public Vector getAlKeyExtend() {
       Vector vectListExtend = new Vector();
         vectListExtend.add("12 MONTH");
         vectListExtend.add("6 MONTH");
         vectListExtend.add("3 MONTH");
         vectListExtend.add("2 MONTH");
         vectListExtend.add("1 MONTH");
       return vectListExtend;
    }
    
    public Vector listgetEmployeeActiveByPeriode(SrcLeaveAppAlClosing objSrcLeaveAppAlClosing, I_Leave leaveConfig) {
      Vector listEmployeeActiveByPeriod = SessLeaveClosing.getEmployeeActiveByPeriode(objSrcLeaveAppAlClosing,leaveConfig);
      return listEmployeeActiveByPeriod;
    }
    
    
    public boolean getConfigurationDpMinus() {
        return true;
    }
    
    public boolean isMessageUseAdvanceMinusLimit() {
        return false;
    }

    public Vector listHRManagerSendEmail(long employee_id, String oidEmpHr) {
       DBResultSet dbrs = null;
        Vector result = new Vector();

        long hr_department = Long.parseLong(PstSystemProperty.getValueByName("OID_HRD_DEPARTMENT"));

        try {

            String sql = "SELECT EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMAIL_ADDRESS]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME] + " FROM "
                    + PstEmployee.TBL_HR_EMPLOYEE + " EMP INNER JOIN "
                    + PstPosition.TBL_HR_POSITION + " POS ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID] + " = POS."
                    + PstPosition.fieldNames[PstPosition.FLD_POSITION_ID] + " INNER JOIN "
                    + PstLevel.TBL_HR_LEVEL + " LEV ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_LEVEL_ID] + " = LEV."
                    + PstLevel.fieldNames[PstLevel.FLD_LEVEL_ID] + " WHERE EMP."
                    + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] + " = " + hr_department + " AND POS."
                    + PstPosition.fieldNames[PstPosition.FLD_DISABLED_APP_DEPT_SCOPE] + " = " + PstPosition.DISABLED_APP_DEPT_SCOPE_FALSE + " AND POS."
                    + PstPosition.fieldNames[PstPosition.FLD_POSITION_LEVEL] + " IN (" + PstPosition.LEVEL_SECRETARY + "," + PstPosition.LEVEL_SUPERVISOR + "," + PstPosition.LEVEL_ASST_MANAGER + "," + PstPosition.LEVEL_MANAGER + ","
                    + PstPosition.LEVEL_ASST_DIRECTOR + "," + PstPosition.LEVEL_DIRECTOR + ")" + " AND EMP."
                    + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + " = " + PstEmployee.NO_RESIGN;
                    //untuk employee id yg tdk mau di kirim email
                   if(oidEmpHr!=null && oidEmpHr.length()>0 && !oidEmpHr.equalsIgnoreCase(PstSystemProperty.SYS_NOT_INITIALIZED)){
                    sql = sql + " AND EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + " NOT IN("+oidEmpHr+")";
                   }

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                Employee employee = new Employee();
                employee.setOID(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]));
                employee.setFullName(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]));
                employee.setEmailAddress(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_EMAIL_ADDRESS]));
                result.add(employee);
            }

            if (result != null && result.size() > 0) {
                return result;
            } else {
                return null;
            }


        } catch (Exception e) {
            System.out.println("EXCEPTION " + e.toString());
        } finally {
            DBResultSet.close(dbrs);
        }

        return null;
    }
    //update by satrya 2013-11-22
    public float getAlExtraAl(String level, String employeeType, int LoSDays, Date commencingDate, Date checkDate) {
        int iLevel = getIndexLevel(level);
        int iType = getIndexType(employeeType);
        Date tmpCheckDate = (Date) checkDate.clone();
        Calendar cald = Calendar.getInstance();
        cald.setTime(tmpCheckDate);
        tmpCheckDate.setDate(cald.getActualMaximum(Calendar.DATE));  // set check date to the end of the month sehingga perhitunganan AL berlaku skope bulan.
        float iEntitleALExtra = 0;
        if (LoSDays == 0) {
            LoSDays = (int) ((tmpCheckDate.getTime() - commencingDate.getTime()) / (1000 * 60 * 60 * 24));
        }
        if (commencingDate.getYear() < tmpCheckDate.getYear()) {
            // sudah beda tahun berarti akan dapat full                  
            if (LoSDays >= (iDayAYear * iYearsExtraAl) && (commencingDate.getMonth() == tmpCheckDate.getMonth())) { // length of service >= 10 tahun
                iEntitleALExtra = iALEntitlesExtra[iLevel][iType];
            }
        }
        return iEntitleALExtra;
    }
//update by satrya 2013-11-22
    public Date getAlExtraDateAl(float extraAl, Date commercingDate) {
        Date extraAlDate=null;
        if (commercingDate != null && extraAl!=0) {
            extraAlDate = (Date) commercingDate.clone();
            extraAlDate.setYear((extraAlDate.getYear()+1900) + iYearsExtraAl);
        }
        return extraAlDate;
    }

    public int getConfigurationSendMail() {
        return I_Leave.LEAVE_CONFIG_SEND_EMAIL_CREATE_DEP_HEAD;
    }
    
    public int getConfigurationLeaveApprovall() {
        return I_Leave.LEAVE_CONFIG_AFTER_APPROVALL_HRD_NO_EXECUTE;
    }
    
    public int getConfigurationLeaveDpTakenLong() {
        return I_Leave.LEAVE_CONFIG_REQUEST_DP_TAKEN_NOT_LONG;
    }
    
    public boolean getDPStockMinus(DpStockTaken dpStockTaken, float residueSystem, float dpTakenPrev) {
       if((dpStockTaken.getTakenQty() > (residueSystem + dpTakenPrev))){
           return true;
       }else{
           return false;
       }
    }
    public boolean CanNotChangeApproval() {
        return false;
    }
    
     public boolean getBalanceNotCalculationDpExpired() {
        return false;
    }
     
      public boolean isLeaveApplicationIsDay() {
        return I_Leave.LEAVE_APPLICATION_IS_DAY;
    }
}
