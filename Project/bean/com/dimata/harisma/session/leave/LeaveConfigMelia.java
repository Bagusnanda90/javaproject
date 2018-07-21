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
import com.dimata.harisma.session.attendance.SessAnnualLeave;
import com.dimata.harisma.session.attendance.SessLeaveManagement;
import com.dimata.harisma.utility.service.leavedp.ServiceAlStock;
import com.dimata.harisma.utility.service.leavedp.ServiceLLStock;
import static com.dimata.system.entity.PstSystemProperty.SYS_NOT_INITIALIZED;
import java.util.Hashtable;

/**
 * Konfiguration for KTI
 *
 * @author kartika
 * @notes <TABLE FRAME=VOID CELLSPACING=0 COLS=11 RULES=NONE BORDER=0>
 * <COLGROUP><COL WIDTH=141><COL WIDTH=99><COL WIDTH=114><COL WIDTH=110><COL
 * WIDTH=254><COL WIDTH=248><COL WIDTH=220><COL WIDTH=64><COL WIDTH=64><COL
 * WIDTH=64><COL WIDTH=64></COLGROUP> <TBODY> <TR> <TD WIDTH=141 HEIGHT=25
 * ALIGN=LEFT BGCOLOR="#99CC00"><B><FONT FACE="Calibri" SIZE=4
 * COLOR="#000000">Use Cases Analysis</FONT></B></TD> <TD WIDTH=99 ALIGN=LEFT
 * BGCOLOR="#99CC00"><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD> <TD
 * WIDTH=114 ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
 * <TD WIDTH=110 ALIGN=LEFT><FONT FACE="Calibri"
 * COLOR="#000000"><BR></FONT></TD> <TD WIDTH=254 ALIGN=LEFT><FONT
 * FACE="Calibri" COLOR="#000000"><BR></FONT></TD> <TD WIDTH=248
 * ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD> <TD
 * WIDTH=220 ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
 * <TD WIDTH=64 ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
 * <TD WIDTH=64 ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
 * <TD WIDTH=64 ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
 * <TD WIDTH=64 ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD>
 * </TR> <TR> <TD HEIGHT=19 ALIGN=LEFT><FONT FACE="Calibri"
 * COLOR="#000000"><BR></FONT></TD> <TD ALIGN=LEFT><FONT FACE="Calibri"
 * COLOR="#000000"><BR></FONT></TD> <TD ALIGN=LEFT><FONT FACE="Calibri"
 * COLOR="#000000"><BR></FONT></TD> <TD ALIGN=LEFT><FONT FACE="Calibri"
 * COLOR="#000000"><BR></FONT></TD> <TD ALIGN=LEFT><FONT FACE="Calibri"
 * COLOR="#000000"><BR></FONT></TD> <TD ALIGN=LEFT><FONT FACE="Calibri"
 * COLOR="#000000"><BR></FONT></TD> <TD ALIGN=LEFT><FONT FACE="Calibri"
 * COLOR="#000000"><BR></FONT></TD> <TD ALIGN=LEFT><FONT FACE="Calibri"
 * COLOR="#000000"><BR></FONT></TD> <TD ALIGN=LEFT><FONT FACE="Calibri"
 * COLOR="#000000"><BR></FONT></TD> <TD ALIGN=LEFT><FONT FACE="Calibri"
 * COLOR="#000000"><BR></FONT></TD> <TD ALIGN=LEFT><FONT FACE="Calibri"
 * COLOR="#000000"><BR></FONT></TD> </TR> <TR> <TD HEIGHT=24 ALIGN=LEFT><B><FONT
 * FACE="Calibri" SIZE=4 COLOR="#000000">Leave Approval</FONT></B></TD> <TD
 * ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD> <TD
 * ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD> <TD
 * ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD> <TD
 * ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD> <TD
 * ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD> <TD
 * ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD> <TD
 * ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD> <TD
 * ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD> <TD
 * ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD> <TD
 * ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD> </TR> <TR>
 * <TD HEIGHT=19 ALIGN=LEFT><FONT FACE="Calibri"
 * COLOR="#000000"><BR></FONT></TD> <TD ALIGN=LEFT><FONT FACE="Calibri"
 * COLOR="#000000"><BR></FONT></TD> <TD ALIGN=LEFT><FONT FACE="Calibri"
 * COLOR="#000000"><BR></FONT></TD> <TD ALIGN=LEFT><FONT FACE="Calibri"
 * COLOR="#000000"><BR></FONT></TD> <TD ALIGN=LEFT><FONT FACE="Calibri"
 * COLOR="#000000"><BR></FONT></TD> <TD ALIGN=LEFT><FONT FACE="Calibri"
 * COLOR="#000000"><BR></FONT></TD> <TD ALIGN=LEFT><FONT FACE="Calibri"
 * COLOR="#000000"><BR></FONT></TD> <TD ALIGN=LEFT><FONT FACE="Calibri"
 * COLOR="#000000"><BR></FONT></TD> <TD ALIGN=LEFT><FONT FACE="Calibri"
 * COLOR="#000000"><BR></FONT></TD> <TD ALIGN=LEFT><FONT FACE="Calibri"
 * COLOR="#000000"><BR></FONT></TD> <TD ALIGN=LEFT><FONT FACE="Calibri"
 * COLOR="#000000"><BR></FONT></TD> </TR> <TR> <TD STYLE="border-top: 1px solid
 * #000000; border-bottom: 1px solid #000000; border-right: 1px solid #000000"
 * ROWSPAN=2 HEIGHT=39 ALIGN=CENTER VALIGN=MIDDLE><B><FONT FACE="Calibri"
 * COLOR="#000000">Applier</FONT></B></TD> <TD STYLE="border-top: 1px solid
 * #000000; border-bottom: 1px solid #000000; border-right: 1px solid #000000"
 * ALIGN=CENTER><B><FONT FACE="Calibri" COLOR="#000000">Manager</FONT></B></TD>
 * <TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000;
 * border-right: 1px solid #000000" ALIGN=CENTER><B><FONT FACE="Calibri"
 * COLOR="#000000"><BR></FONT></B></TD> <TD STYLE="border-top: 1px solid
 * #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000"
 * ALIGN=CENTER><B><FONT FACE="Calibri" COLOR="#000000">Division</FONT></B></TD>
 * <TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000;
 * border-right: 1px solid #000000" COLSPAN=2 ALIGN=CENTER><B><FONT
 * FACE="Calibri" COLOR="#000000">Approval by</FONT></B></TD> <TD
 * ALIGN=LEFT><BR></TD> <TD ALIGN=LEFT><FONT FACE="Calibri"
 * COLOR="#000000"><BR></FONT></TD> <TD ALIGN=LEFT><FONT FACE="Calibri"
 * COLOR="#000000"><BR></FONT></TD> <TD ALIGN=LEFT><FONT FACE="Calibri"
 * COLOR="#000000"><BR></FONT></TD> <TD ALIGN=LEFT><FONT FACE="Calibri"
 * COLOR="#000000"><BR></FONT></TD> </TR> <TR> <TD STYLE="border-bottom: 1px
 * solid #000000; border-left: 1px solid #000000; border-right: 1px solid
 * #000000" ALIGN=CENTER><B><FONT FACE="Calibri" COLOR="#000000">HOD
 * </FONT></B></TD> <TD STYLE="border-top: 1px solid #000000; border-bottom: 1px
 * solid #000000; border-left: 1px solid #000000; border-right: 1px solid
 * #000000" ALIGN=CENTER><B><FONT FACE="Calibri" COLOR="#000000">Linked
 * HOD</FONT></B></TD> <TD STYLE="border-top: 1px solid #000000; border-bottom:
 * 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid
 * #000000" ALIGN=CENTER><B><FONT FACE="Calibri" COLOR="#000000">Div.Head
 * </FONT></B></TD> <TD STYLE="border-top: 1px solid #000000; border-bottom: 1px
 * solid #000000; border-left: 1px solid #000000; border-right: 1px solid
 * #000000" ALIGN=CENTER><B><FONT FACE="Calibri" COLOR="#000000">Approved ( w/
 * email)</FONT></B></TD> <TD STYLE="border-top: 1px solid #000000;
 * border-bottom: 1px solid #000000; border-left: 1px solid #000000;
 * border-right: 1px solid #000000" ALIGN=CENTER><B><FONT FACE="Calibri"
 * COLOR="#000000">HR Approved ( w/ email)</FONT></B></TD> <TD
 * ALIGN=LEFT><BR></TD> <TD ALIGN=LEFT><FONT FACE="Calibri"
 * COLOR="#000000"><BR></FONT></TD> <TD ALIGN=LEFT><FONT FACE="Calibri"
 * COLOR="#000000"><BR></FONT></TD> <TD ALIGN=LEFT><FONT FACE="Calibri"
 * COLOR="#000000"><BR></FONT></TD> <TD ALIGN=LEFT><FONT FACE="Calibri"
 * COLOR="#000000"><BR></FONT></TD> </TR> <TR> <TD STYLE="border-top: 1px solid
 * #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000;
 * border-right: 1px solid #000000" HEIGHT=19 ALIGN=LEFT><FONT FACE="Calibri"
 * COLOR="#000000"><BR></FONT></TD> <TD STYLE="border-top: 1px solid #000000;
 * border-bottom: 1px solid #000000; border-left: 1px solid #000000;
 * border-right: 1px solid #000000" ALIGN=LEFT><FONT FACE="Calibri"
 * COLOR="#000000"><BR></FONT></TD> <TD STYLE="border-top: 1px solid #000000;
 * border-bottom: 1px solid #000000; border-left: 1px solid #000000;
 * border-right: 1px solid #000000" ALIGN=LEFT><FONT FACE="Calibri"
 * COLOR="#000000"><BR></FONT></TD> <TD STYLE="border-top: 1px solid #000000;
 * border-bottom: 1px solid #000000; border-left: 1px solid #000000;
 * border-right: 1px solid #000000" ALIGN=LEFT><FONT FACE="Calibri"
 * COLOR="#000000"><BR></FONT></TD> <TD STYLE="border-top: 1px solid #000000;
 * border-bottom: 1px solid #000000; border-left: 1px solid #000000;
 * border-right: 1px solid #000000" ALIGN=LEFT><FONT FACE="Calibri"
 * COLOR="#000000"><BR></FONT></TD> <TD STYLE="border-top: 1px solid #000000;
 * border-bottom: 1px solid #000000; border-left: 1px solid #000000;
 * border-right: 1px solid #000000" ALIGN=LEFT><FONT FACE="Calibri"
 * COLOR="#000000"><BR></FONT></TD> <TD ALIGN=LEFT><FONT FACE="Calibri"
 * COLOR="#000000"><BR></FONT></TD> <TD ALIGN=LEFT><FONT FACE="Calibri"
 * COLOR="#000000"><BR></FONT></TD> <TD ALIGN=LEFT><FONT FACE="Calibri"
 * COLOR="#000000"><BR></FONT></TD> <TD ALIGN=LEFT><FONT FACE="Calibri"
 * COLOR="#000000"><BR></FONT></TD> <TD ALIGN=LEFT><FONT FACE="Calibri"
 * COLOR="#000000"><BR></FONT></TD> </TR> <TR> <TD STYLE="border-top: 1px solid
 * #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000;
 * border-right: 1px solid #000000" HEIGHT=20 ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT
 * FACE="Calibri" COLOR="#000000">Staff-Ast Man.</FONT></TD> <TD
 * STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000;
 * border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER
 * BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000">ada</FONT></TD> <TD
 * STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000;
 * border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER
 * BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000">-</FONT></TD> <TD
 * STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000;
 * border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER
 * BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000">ada</FONT></TD> <TD
 * STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000;
 * border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=LEFT
 * BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000">HOD,
 * Div.Head</FONT></TD> <TD STYLE="border-top: 1px solid #000000; border-bottom:
 * 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid
 * #000000" ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT FACE="Calibri"
 * COLOR="#000000">Staff, Spv, Ast.Man, Man, Dir. HR</FONT></TD> <TD
 * ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD> <TD
 * ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD> <TD
 * ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD> <TD
 * ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD> <TD
 * ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD> </TR> <TR>
 * <TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000;
 * border-left: 1px solid #000000; border-right: 1px solid #000000" HEIGHT=20
 * ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000">Staff- Ast
 * Man</FONT></TD> <TD STYLE="border-top: 1px solid #000000; border-bottom: 1px
 * solid #000000; border-left: 1px solid #000000; border-right: 1px solid
 * #000000" ALIGN=CENTER BGCOLOR="#FFFFFF"><FONT FACE="Calibri"
 * COLOR="#000000">ada</FONT></TD> <TD STYLE="border-top: 1px solid #000000;
 * border-bottom: 1px solid #000000; border-left: 1px solid #000000;
 * border-right: 1px solid #000000" ALIGN=CENTER BGCOLOR="#FFFFFF"><FONT
 * FACE="Calibri" COLOR="#000000">-</FONT></TD> <TD STYLE="border-top: 1px solid
 * #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000;
 * border-right: 1px solid #000000" ALIGN=CENTER BGCOLOR="#FFFFFF"><FONT
 * FACE="Calibri" COLOR="#000000">tidak ada</FONT></TD> <TD STYLE="border-top:
 * 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid
 * #000000; border-right: 1px solid #000000" ALIGN=LEFT><FONT FACE="Calibri"
 * COLOR="#000000">HOD,HR Man, HR.Dir</FONT></TD> <TD STYLE="border-top: 1px
 * solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid
 * #000000; border-right: 1px solid #000000" ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT
 * FACE="Calibri" COLOR="#000000">Staff, Spv, Ast.Man, Man, Dir. HR</FONT></TD>
 * <TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000">Man.= Position with
 * Manager Level</FONT></TD> <TD ALIGN=LEFT><FONT FACE="Calibri"
 * COLOR="#000000"><BR></FONT></TD> <TD ALIGN=LEFT><FONT FACE="Calibri"
 * COLOR="#000000"><BR></FONT></TD> <TD ALIGN=LEFT><FONT FACE="Calibri"
 * COLOR="#000000"><BR></FONT></TD> <TD ALIGN=LEFT><FONT FACE="Calibri"
 * COLOR="#000000"><BR></FONT></TD> </TR> <TR> <TD STYLE="border-top: 1px solid
 * #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000;
 * border-right: 1px solid #000000" HEIGHT=20 ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT
 * FACE="Calibri" COLOR="#000000">Staff &ndash; Ast Man.</FONT></TD> <TD
 * STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000;
 * border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER
 * BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000">tidak ada</FONT></TD>
 * <TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000;
 * border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER
 * BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000">ada</FONT></TD> <TD
 * STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000;
 * border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER
 * BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000">ada</FONT></TD> <TD
 * STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000;
 * border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=LEFT
 * BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000">Link.HOD,
 * Div.Head</FONT></TD> <TD STYLE="border-top: 1px solid #000000; border-bottom:
 * 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid
 * #000000" ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT FACE="Calibri"
 * COLOR="#000000">Staff, Spv, Ast.Man, Man, Dir. HR</FONT></TD> <TD
 * ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000">Director = Position with
 * Director Level</FONT></TD> <TD ALIGN=LEFT><FONT FACE="Calibri"
 * COLOR="#000000"><BR></FONT></TD> <TD ALIGN=LEFT><FONT FACE="Calibri"
 * COLOR="#000000"><BR></FONT></TD> <TD ALIGN=LEFT><FONT FACE="Calibri"
 * COLOR="#000000"><BR></FONT></TD> <TD ALIGN=LEFT><FONT FACE="Calibri"
 * COLOR="#000000"><BR></FONT></TD> </TR> <TR> <TD STYLE="border-top: 1px solid
 * #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000;
 * border-right: 1px solid #000000" HEIGHT=20 ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT
 * FACE="Calibri" COLOR="#000000">Staff &ndash; Ast Man.</FONT></TD> <TD
 * STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000;
 * border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER
 * BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000">tidak ada</FONT></TD>
 * <TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000;
 * border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER
 * BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000">ada</FONT></TD> <TD
 * STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000;
 * border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER
 * BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000">tidak ada</FONT></TD>
 * <TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000;
 * border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=LEFT
 * BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000">Link.HOD, Hr
 * Man,HR.Dir</FONT></TD> <TD STYLE="border-top: 1px solid #000000;
 * border-bottom: 1px solid #000000; border-left: 1px solid #000000;
 * border-right: 1px solid #000000" ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT
 * FACE="Calibri" COLOR="#000000">Staff, Spv, Ast.Man, Man, Dir. HR</FONT></TD>
 * <TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD> <TD
 * ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD> <TD
 * ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD> <TD
 * ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD> <TD
 * ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD> </TR> <TR>
 * <TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000;
 * border-left: 1px solid #000000; border-right: 1px solid #000000" HEIGHT=20
 * ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000">Staff
 * &ndash; Ast Man.</FONT></TD> <TD STYLE="border-top: 1px solid #000000;
 * border-bottom: 1px solid #000000; border-left: 1px solid #000000;
 * border-right: 1px solid #000000" ALIGN=CENTER BGCOLOR="#FFFFFF"><FONT
 * FACE="Calibri" COLOR="#000000">tidak ada</FONT></TD> <TD STYLE="border-top:
 * 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid
 * #000000; border-right: 1px solid #000000" ALIGN=CENTER
 * BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000">tidak ada</FONT></TD>
 * <TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000;
 * border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER
 * BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000">ada</FONT></TD> <TD
 * STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000;
 * border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=LEFT
 * BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000">Div. Head, Hr
 * Man,HR.Dir</FONT></TD> <TD STYLE="border-top: 1px solid #000000;
 * border-bottom: 1px solid #000000; border-left: 1px solid #000000;
 * border-right: 1px solid #000000" ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT
 * FACE="Calibri" COLOR="#000000">Staff, Spv, Ast.Man, Man, Dir. HR</FONT></TD>
 * <TD ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD> <TD
 * ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD> <TD
 * ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD> <TD
 * ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD> <TD
 * ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD> </TR> <TR>
 * <TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000;
 * border-left: 1px solid #000000; border-right: 1px solid #000000" HEIGHT=20
 * ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000">Staff
 * &ndash; Ast Man.</FONT></TD> <TD STYLE="border-top: 1px solid #000000;
 * border-bottom: 1px solid #000000; border-left: 1px solid #000000;
 * border-right: 1px solid #000000" ALIGN=CENTER BGCOLOR="#FFFFFF"><FONT
 * FACE="Calibri" COLOR="#000000">tidak ada</FONT></TD> <TD STYLE="border-top:
 * 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid
 * #000000; border-right: 1px solid #000000" ALIGN=CENTER
 * BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000">tidak ada</FONT></TD>
 * <TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000;
 * border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER
 * BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000">tidak ada</FONT></TD>
 * <TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000;
 * border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=LEFT
 * BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000">GM, HR Man,
 * HR.Dir</FONT></TD> <TD STYLE="border-top: 1px solid #000000; border-bottom:
 * 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid
 * #000000" ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT FACE="Calibri"
 * COLOR="#000000">Staff, Spv, Ast.Man, Man, Dir. HR</FONT></TD> <TD
 * ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD> <TD
 * ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD> <TD
 * ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD> <TD
 * ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD> <TD
 * ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD> </TR> <TR>
 * <TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000;
 * border-left: 1px solid #000000; border-right: 1px solid #000000" HEIGHT=20
 * ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT FACE="Calibri"
 * COLOR="#000000">Manager</FONT></TD> <TD STYLE="border-top: 1px solid #000000;
 * border-bottom: 1px solid #000000; border-left: 1px solid #000000;
 * border-right: 1px solid #000000" ALIGN=CENTER BGCOLOR="#FFFFFF"><FONT
 * FACE="Calibri" COLOR="#000000">tidak ada</FONT></TD> <TD STYLE="border-top:
 * 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid
 * #000000; border-right: 1px solid #000000" ALIGN=CENTER
 * BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000">ada</FONT></TD> <TD
 * STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000;
 * border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER
 * BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000">ada</FONT></TD> <TD
 * STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000;
 * border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=LEFT
 * BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000">Linked HOD, Div.Head,
 * </FONT></TD> <TD STYLE="border-top: 1px solid #000000; border-bottom: 1px
 * solid #000000; border-left: 1px solid #000000; border-right: 1px solid
 * #000000" ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT FACE="Calibri"
 * COLOR="#000000">Staff, Spv, Ast.Man, Man, Dir. HR</FONT></TD> <TD ALIGN=LEFT
 * BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD> <TD
 * ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT FACE="Calibri"
 * COLOR="#000000"><BR></FONT></TD> <TD ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT
 * FACE="Calibri" COLOR="#000000"><BR></FONT></TD> <TD ALIGN=LEFT
 * BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD> <TD
 * ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT FACE="Calibri"
 * COLOR="#000000"><BR></FONT></TD> </TR> <TR> <TD STYLE="border-top: 1px solid
 * #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000;
 * border-right: 1px solid #000000" HEIGHT=20 ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT
 * FACE="Calibri" COLOR="#000000">Manager</FONT></TD> <TD STYLE="border-top: 1px
 * solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid
 * #000000; border-right: 1px solid #000000" ALIGN=CENTER
 * BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000">ada</FONT></TD> <TD
 * STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000;
 * border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER
 * BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD> <TD
 * STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000;
 * border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER
 * BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000">ada</FONT></TD> <TD
 * STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000;
 * border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=LEFT
 * BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000">HOD, Div.Head,
 * </FONT></TD> <TD STYLE="border-top: 1px solid #000000; border-bottom: 1px
 * solid #000000; border-left: 1px solid #000000; border-right: 1px solid
 * #000000" ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT FACE="Calibri"
 * COLOR="#000000">Staff, Spv, Ast.Man, Man, Dir. HR</FONT></TD> <TD ALIGN=LEFT
 * BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD> <TD
 * ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT FACE="Calibri"
 * COLOR="#000000"><BR></FONT></TD> <TD ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT
 * FACE="Calibri" COLOR="#000000"><BR></FONT></TD> <TD ALIGN=LEFT
 * BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD> <TD
 * ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT FACE="Calibri"
 * COLOR="#000000"><BR></FONT></TD> </TR> <TR> <TD STYLE="border-top: 1px solid
 * #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000;
 * border-right: 1px solid #000000" HEIGHT=20 ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT
 * FACE="Calibri" COLOR="#000000">Manager</FONT></TD> <TD STYLE="border-top: 1px
 * solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid
 * #000000; border-right: 1px solid #000000" ALIGN=CENTER
 * BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000">tidak ada</FONT></TD>
 * <TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000;
 * border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER
 * BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000">tidak ada</FONT></TD>
 * <TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000;
 * border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER
 * BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000">ada</FONT></TD> <TD
 * STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000;
 * border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=LEFT
 * BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000">Div.Head, HR.Dir,
 * GM</FONT></TD> <TD STYLE="border-top: 1px solid #000000; border-bottom: 1px
 * solid #000000; border-left: 1px solid #000000; border-right: 1px solid
 * #000000" ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT FACE="Calibri"
 * COLOR="#000000">Staff, Spv, Ast.Man, Man, Dir. HR</FONT></TD> <TD ALIGN=LEFT
 * BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD> <TD
 * ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT FACE="Calibri"
 * COLOR="#000000"><BR></FONT></TD> <TD ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT
 * FACE="Calibri" COLOR="#000000"><BR></FONT></TD> <TD ALIGN=LEFT
 * BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD> <TD
 * ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT FACE="Calibri"
 * COLOR="#000000"><BR></FONT></TD> </TR> <TR> <TD STYLE="border-top: 1px solid
 * #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000;
 * border-right: 1px solid #000000" HEIGHT=20 ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT
 * FACE="Calibri" COLOR="#000000">Manager</FONT></TD> <TD STYLE="border-top: 1px
 * solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid
 * #000000; border-right: 1px solid #000000" ALIGN=CENTER
 * BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000">tidak ada</FONT></TD>
 * <TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000;
 * border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER
 * BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000">tidak ada</FONT></TD>
 * <TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000;
 * border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER
 * BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000">tidak ada</FONT></TD>
 * <TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000;
 * border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=LEFT
 * BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000">GM, HR.Dir</FONT></TD>
 * <TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000;
 * border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=LEFT
 * BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000">Staff, Spv, Ast.Man,
 * Man, Dir. HR</FONT></TD> <TD ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT
 * FACE="Calibri" COLOR="#000000"><BR></FONT></TD> <TD ALIGN=LEFT
 * BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD> <TD
 * ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT FACE="Calibri"
 * COLOR="#000000"><BR></FONT></TD> <TD ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT
 * FACE="Calibri" COLOR="#000000"><BR></FONT></TD> <TD ALIGN=LEFT
 * BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD> </TR>
 * <TR> <TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid
 * #000000; border-left: 1px solid #000000; border-right: 1px solid #000000"
 * HEIGHT=20 ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT FACE="Calibri"
 * COLOR="#000000">HOD</FONT></TD> <TD STYLE="border-top: 1px solid #000000;
 * border-bottom: 1px solid #000000; border-left: 1px solid #000000;
 * border-right: 1px solid #000000" ALIGN=CENTER BGCOLOR="#FFFFFF"><FONT
 * FACE="Calibri" COLOR="#000000"><BR></FONT></TD> <TD STYLE="border-top: 1px
 * solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid
 * #000000; border-right: 1px solid #000000" ALIGN=CENTER
 * BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD> <TD
 * STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000;
 * border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER
 * BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000">ada</FONT></TD> <TD
 * STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000;
 * border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=LEFT
 * BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000">Div.Head, HR.Dir,
 * GM</FONT></TD> <TD STYLE="border-top: 1px solid #000000; border-bottom: 1px
 * solid #000000; border-left: 1px solid #000000; border-right: 1px solid
 * #000000" ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT FACE="Calibri"
 * COLOR="#000000">Staff, Spv, Ast.Man, Man, Dir. HR</FONT></TD> <TD ALIGN=LEFT
 * BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD> <TD
 * ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT FACE="Calibri"
 * COLOR="#000000"><BR></FONT></TD> <TD ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT
 * FACE="Calibri" COLOR="#000000"><BR></FONT></TD> <TD ALIGN=LEFT
 * BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD> <TD
 * ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT FACE="Calibri"
 * COLOR="#000000"><BR></FONT></TD> </TR> <TR> <TD STYLE="border-top: 1px solid
 * #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000;
 * border-right: 1px solid #000000" HEIGHT=20 ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT
 * FACE="Calibri" COLOR="#000000">HOD</FONT></TD> <TD STYLE="border-top: 1px
 * solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid
 * #000000; border-right: 1px solid #000000" ALIGN=CENTER
 * BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD> <TD
 * STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000;
 * border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER
 * BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD> <TD
 * STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000;
 * border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER
 * BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000">tidak ada</FONT></TD>
 * <TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000;
 * border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=LEFT
 * BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000">GM, HR.Dir</FONT></TD>
 * <TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000;
 * border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=LEFT
 * BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000">Staff, Spv, Ast.Man,
 * Man, Dir. HR</FONT></TD> <TD ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT
 * FACE="Calibri" COLOR="#000000"><BR></FONT></TD> <TD ALIGN=LEFT
 * BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD> <TD
 * ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT FACE="Calibri"
 * COLOR="#000000"><BR></FONT></TD> <TD ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT
 * FACE="Calibri" COLOR="#000000"><BR></FONT></TD> <TD ALIGN=LEFT
 * BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD> </TR>
 * <TR> <TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid
 * #000000; border-left: 1px solid #000000; border-right: 1px solid #000000"
 * HEIGHT=20 ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT FACE="Calibri"
 * COLOR="#000000">Director</FONT></TD> <TD STYLE="border-top: 1px solid
 * #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000;
 * border-right: 1px solid #000000" ALIGN=CENTER BGCOLOR="#FFFFFF"><FONT
 * FACE="Calibri" COLOR="#000000"><BR></FONT></TD> <TD STYLE="border-top: 1px
 * solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid
 * #000000; border-right: 1px solid #000000" ALIGN=CENTER
 * BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD> <TD
 * STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000;
 * border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER
 * BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000">tidak ada</FONT></TD>
 * <TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000;
 * border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=LEFT
 * BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000">GM, HR.Dir</FONT></TD>
 * <TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000;
 * border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=LEFT
 * BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000">Staff, Spv, Ast.Man,
 * Man, Dir. HR</FONT></TD> <TD ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT
 * FACE="Calibri" COLOR="#000000"><BR></FONT></TD> <TD ALIGN=LEFT
 * BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD> <TD
 * ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT FACE="Calibri"
 * COLOR="#000000"><BR></FONT></TD> <TD ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT
 * FACE="Calibri" COLOR="#000000"><BR></FONT></TD> <TD ALIGN=LEFT
 * BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD> </TR>
 * <TR> <TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid
 * #000000; border-left: 1px solid #000000; border-right: 1px solid #000000"
 * HEIGHT=20 ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT FACE="Calibri"
 * COLOR="#000000">Director</FONT></TD> <TD STYLE="border-top: 1px solid
 * #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000;
 * border-right: 1px solid #000000" ALIGN=CENTER BGCOLOR="#FFFFFF"><FONT
 * FACE="Calibri" COLOR="#000000"><BR></FONT></TD> <TD STYLE="border-top: 1px
 * solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid
 * #000000; border-right: 1px solid #000000" ALIGN=CENTER
 * BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD> <TD
 * STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000;
 * border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER
 * BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000">ada</FONT></TD> <TD
 * STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000;
 * border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=LEFT
 * BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000">Div.Head, HR.Dir,
 * GM</FONT></TD> <TD STYLE="border-top: 1px solid #000000; border-bottom: 1px
 * solid #000000; border-left: 1px solid #000000; border-right: 1px solid
 * #000000" ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT FACE="Calibri"
 * COLOR="#000000">Staff, Spv, Ast.Man, Man, Dir. HR</FONT></TD> <TD ALIGN=LEFT
 * BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD> <TD
 * ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT FACE="Calibri"
 * COLOR="#000000"><BR></FONT></TD> <TD ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT
 * FACE="Calibri" COLOR="#000000"><BR></FONT></TD> <TD ALIGN=LEFT
 * BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD> <TD
 * ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT FACE="Calibri"
 * COLOR="#000000"><BR></FONT></TD> </TR> <TR> <TD STYLE="border-top: 1px solid
 * #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000;
 * border-right: 1px solid #000000" HEIGHT=20 ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT
 * FACE="Calibri" COLOR="#000000">Div. Head</FONT></TD> <TD STYLE="border-top:
 * 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid
 * #000000; border-right: 1px solid #000000" ALIGN=CENTER
 * BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD> <TD
 * STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000;
 * border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER
 * BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD> <TD
 * STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000;
 * border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER
 * BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD> <TD
 * STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000;
 * border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=LEFT
 * BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000">GM, HR.Dir</FONT></TD>
 * <TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000;
 * border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=LEFT
 * BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000">Staff, Spv, Ast.Man,
 * Man, Dir. HR</FONT></TD> <TD ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT
 * FACE="Calibri" COLOR="#000000"><BR></FONT></TD> <TD ALIGN=LEFT
 * BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD> <TD
 * ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT FACE="Calibri"
 * COLOR="#000000"><BR></FONT></TD> <TD ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT
 * FACE="Calibri" COLOR="#000000"><BR></FONT></TD> <TD ALIGN=LEFT
 * BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD> </TR>
 * <TR> <TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid
 * #000000; border-left: 1px solid #000000; border-right: 1px solid #000000"
 * HEIGHT=20 ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT FACE="Calibri"
 * COLOR="#000000">GM</FONT></TD> <TD STYLE="border-top: 1px solid #000000;
 * border-bottom: 1px solid #000000; border-left: 1px solid #000000;
 * border-right: 1px solid #000000" ALIGN=CENTER BGCOLOR="#FFFFFF"><FONT
 * FACE="Calibri" COLOR="#000000"><BR></FONT></TD> <TD STYLE="border-top: 1px
 * solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid
 * #000000; border-right: 1px solid #000000" ALIGN=CENTER
 * BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD> <TD
 * STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000;
 * border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER
 * BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD> <TD
 * STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000;
 * border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=LEFT
 * BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000">HR Dir</FONT></TD> <TD
 * STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000;
 * border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=LEFT
 * BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000">Staff, Spv, Ast.Man,
 * Man, Dir. HR</FONT></TD> <TD ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT
 * FACE="Calibri" COLOR="#000000"><BR></FONT></TD> <TD ALIGN=LEFT
 * BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD> <TD
 * ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT FACE="Calibri"
 * COLOR="#000000"><BR></FONT></TD> <TD ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT
 * FACE="Calibri" COLOR="#000000"><BR></FONT></TD> <TD ALIGN=LEFT
 * BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD> </TR>
 * <TR> <TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid
 * #000000; border-left: 1px solid #000000; border-right: 1px solid #000000"
 * HEIGHT=19 ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT FACE="Calibri"
 * COLOR="#000000"><BR></FONT></TD> <TD STYLE="border-top: 1px solid #000000;
 * border-bottom: 1px solid #000000; border-left: 1px solid #000000;
 * border-right: 1px solid #000000" ALIGN=CENTER BGCOLOR="#FFFFFF"><FONT
 * FACE="Calibri" COLOR="#000000"><BR></FONT></TD> <TD STYLE="border-top: 1px
 * solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid
 * #000000; border-right: 1px solid #000000" ALIGN=CENTER
 * BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD> <TD
 * STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000;
 * border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=CENTER
 * BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD> <TD
 * STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000;
 * border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=LEFT
 * BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD> <TD
 * STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000;
 * border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=LEFT
 * BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD> <TD
 * ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD> <TD
 * ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD> <TD
 * ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD> <TD
 * ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD> <TD
 * ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD> </TR> <TR>
 * <TD HEIGHT=19 ALIGN=LEFT><FONT FACE="Calibri"
 * COLOR="#000000"><BR></FONT></TD> <TD ALIGN=LEFT><FONT FACE="Calibri"
 * COLOR="#000000"><BR></FONT></TD> <TD ALIGN=LEFT><FONT FACE="Calibri"
 * COLOR="#000000"><BR></FONT></TD> <TD ALIGN=LEFT><FONT FACE="Calibri"
 * COLOR="#000000"><BR></FONT></TD> <TD ALIGN=LEFT><FONT FACE="Calibri"
 * COLOR="#000000"><BR></FONT></TD> <TD ALIGN=LEFT><FONT FACE="Calibri"
 * COLOR="#000000"><BR></FONT></TD> <TD ALIGN=LEFT><FONT FACE="Calibri"
 * COLOR="#000000"><BR></FONT></TD> <TD ALIGN=LEFT><FONT FACE="Calibri"
 * COLOR="#000000"><BR></FONT></TD> <TD ALIGN=LEFT><FONT FACE="Calibri"
 * COLOR="#000000"><BR></FONT></TD> <TD ALIGN=LEFT><FONT FACE="Calibri"
 * COLOR="#000000"><BR></FONT></TD> <TD ALIGN=LEFT><FONT FACE="Calibri"
 * COLOR="#000000"><BR></FONT></TD> </TR> <TR> <TD HEIGHT=19 ALIGN=LEFT><FONT
 * FACE="Calibri" COLOR="#000000"><BR></FONT></TD> <TD ALIGN=LEFT><BR></TD> <TD
 * ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD> <TD
 * ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD> <TD
 * ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD> <TD
 * ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD> <TD
 * ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD> <TD
 * ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD> <TD
 * ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD> <TD
 * ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD> <TD
 * ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD> </TR> <TR>
 * <TD HEIGHT=24 ALIGN=LEFT><B><FONT FACE="Calibri" SIZE=4
 * COLOR="#000000">Overtime Approval</FONT></B></TD> <TD ALIGN=LEFT><FONT
 * FACE="Calibri" COLOR="#000000"><BR></FONT></TD> <TD ALIGN=LEFT><FONT
 * FACE="Calibri" COLOR="#000000"><BR></FONT></TD> <TD ALIGN=LEFT><FONT
 * FACE="Calibri" COLOR="#000000"><BR></FONT></TD> <TD ALIGN=LEFT><FONT
 * FACE="Calibri" COLOR="#000000"><BR></FONT></TD> <TD ALIGN=LEFT><FONT
 * FACE="Calibri" COLOR="#000000"><BR></FONT></TD> <TD ALIGN=LEFT><FONT
 * FACE="Calibri" COLOR="#000000"><BR></FONT></TD> <TD ALIGN=LEFT><FONT
 * FACE="Calibri" COLOR="#000000"><BR></FONT></TD> <TD ALIGN=LEFT><FONT
 * FACE="Calibri" COLOR="#000000"><BR></FONT></TD> <TD ALIGN=LEFT><FONT
 * FACE="Calibri" COLOR="#000000"><BR></FONT></TD> <TD ALIGN=LEFT><FONT
 * FACE="Calibri" COLOR="#000000"><BR></FONT></TD> </TR> <TR> <TD HEIGHT=19
 * ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD> <TD
 * ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD> <TD
 * ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD> <TD
 * ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD> <TD
 * ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD> <TD
 * ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD> <TD
 * ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD> <TD
 * ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD> <TD
 * ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD> <TD
 * ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD> <TD
 * ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD> </TR> <TR>
 * <TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000;
 * border-right: 1px solid #000000" ROWSPAN=2 HEIGHT=37 ALIGN=CENTER
 * VALIGN=MIDDLE><B><FONT FACE="Calibri" COLOR="#000000">Applier</FONT></B></TD>
 * <TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000;
 * border-right: 1px solid #000000" ALIGN=CENTER><B><FONT FACE="Calibri"
 * COLOR="#000000">Manager</FONT></B></TD> <TD STYLE="border-top: 1px solid
 * #000000; border-bottom: 1px solid #000000; border-right: 1px solid #000000"
 * ALIGN=CENTER><B><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></B></TD> <TD
 * STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000;
 * border-left: 1px solid #000000" ALIGN=CENTER><B><FONT FACE="Calibri"
 * COLOR="#000000">Division</FONT></B></TD> <TD STYLE="border-top: 1px solid
 * #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000;
 * border-right: 1px solid #000000" ALIGN=LEFT><BR></TD> <TD STYLE="border-top:
 * 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid
 * #000000; border-right: 1px solid #000000" COLSPAN=2 ALIGN=CENTER><B><FONT
 * FACE="Calibri" COLOR="#000000">Approval by</FONT></B></TD> <TD
 * ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD> <TD
 * ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD> <TD
 * ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD> <TD
 * ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD> </TR> <TR>
 * <TD STYLE="border-bottom: 1px solid #000000; border-left: 1px solid #000000;
 * border-right: 1px solid #000000" ALIGN=CENTER><B><FONT FACE="Calibri"
 * COLOR="#000000">HOD </FONT></B></TD> <TD STYLE="border-top: 1px solid
 * #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000;
 * border-right: 1px solid #000000" ALIGN=CENTER><B><FONT FACE="Calibri"
 * COLOR="#000000">Linked HOD</FONT></B></TD> <TD STYLE="border-top: 1px solid
 * #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000;
 * border-right: 1px solid #000000" ALIGN=CENTER><B><FONT FACE="Calibri"
 * COLOR="#000000">Div.Head</FONT></B></TD> <TD STYLE="border-top: 1px solid
 * #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000;
 * border-right: 1px solid #000000" ALIGN=CENTER><B>Requested by</B></TD> <TD
 * STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000;
 * border-left: 1px solid #000000; border-right: 1px solid #000000"
 * ALIGN=CENTER><B><FONT FACE="Calibri" COLOR="#000000">Approved by (w/
 * email)</FONT></B></TD> <TD STYLE="border-top: 1px solid #000000;
 * border-bottom: 1px solid #000000; border-left: 1px solid #000000;
 * border-right: 1px solid #000000" ALIGN=CENTER><B><FONT FACE="Calibri"
 * COLOR="#000000">GM Approval ( w/ email)-FINAL</FONT></B></TD> <TD
 * ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD> <TD
 * ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD> <TD
 * ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD> <TD
 * ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD> </TR> <TR>
 * <TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000;
 * border-left: 1px solid #000000; border-right: 1px solid #000000" HEIGHT=19
 * ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD> <TD
 * STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000;
 * border-left: 1px solid #000000; border-right: 1px solid #000000"
 * ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD> <TD
 * STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000;
 * border-left: 1px solid #000000; border-right: 1px solid #000000"
 * ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD> <TD
 * STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000;
 * border-left: 1px solid #000000; border-right: 1px solid #000000"
 * ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD> <TD
 * STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000;
 * border-left: 1px solid #000000; border-right: 1px solid #000000"
 * ALIGN=LEFT><BR></TD> <TD STYLE="border-top: 1px solid #000000; border-bottom:
 * 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid
 * #000000" ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD> <TD
 * STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000;
 * border-left: 1px solid #000000; border-right: 1px solid #000000"
 * ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD> <TD
 * ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD> <TD
 * ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD> <TD
 * ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD> <TD
 * ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD> </TR> <TR>
 * <TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000;
 * border-left: 1px solid #000000; border-right: 1px solid #000000" HEIGHT=19
 * ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000">Staff
 * &ndash; Ast Man.</FONT></TD> <TD STYLE="border-top: 1px solid #000000;
 * border-bottom: 1px solid #000000; border-left: 1px solid #000000;
 * border-right: 1px solid #000000" ALIGN=CENTER><FONT FACE="Calibri"
 * COLOR="#000000">ada</FONT></TD> <TD STYLE="border-top: 1px solid #000000;
 * border-bottom: 1px solid #000000; border-left: 1px solid #000000;
 * border-right: 1px solid #000000" ALIGN=CENTER><FONT FACE="Calibri"
 * COLOR="#000000">-</FONT></TD> <TD STYLE="border-top: 1px solid #000000;
 * border-bottom: 1px solid #000000; border-left: 1px solid #000000;
 * border-right: 1px solid #000000" ALIGN=CENTER><FONT FACE="Calibri"
 * COLOR="#000000">ada</FONT></TD> <TD STYLE="border-top: 1px solid #000000;
 * border-bottom: 1px solid #000000; border-left: 1px solid #000000;
 * border-right: 1px solid #000000" ALIGN=LEFT> Supervisor, HOD, Manager,
 * Dir</TD> <TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid
 * #000000; border-left: 1px solid #000000; border-right: 1px solid #000000"
 * ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000">HOD, Div.Head</FONT></TD> <TD
 * STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000;
 * border-left: 1px solid #000000; border-right: 1px solid #000000"
 * ALIGN=CENTER><FONT FACE="Calibri" COLOR="#000000">HR Dir, GM</FONT></TD> <TD
 * ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD> <TD
 * ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD> <TD
 * ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD> <TD
 * ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD> </TR> <TR>
 * <TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000;
 * border-left: 1px solid #000000; border-right: 1px solid #000000" HEIGHT=19
 * ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000">Staff
 * &ndash; Ast Man.</FONT></TD> <TD STYLE="border-top: 1px solid #000000;
 * border-bottom: 1px solid #000000; border-left: 1px solid #000000;
 * border-right: 1px solid #000000" ALIGN=CENTER><FONT FACE="Calibri"
 * COLOR="#000000">ada</FONT></TD> <TD STYLE="border-top: 1px solid #000000;
 * border-bottom: 1px solid #000000; border-left: 1px solid #000000;
 * border-right: 1px solid #000000" ALIGN=CENTER><FONT FACE="Calibri"
 * COLOR="#000000">-</FONT></TD> <TD STYLE="border-top: 1px solid #000000;
 * border-bottom: 1px solid #000000; border-left: 1px solid #000000;
 * border-right: 1px solid #000000" ALIGN=CENTER><FONT FACE="Calibri"
 * COLOR="#000000">tidak ada</FONT></TD> <TD STYLE="border-top: 1px solid
 * #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000;
 * border-right: 1px solid #000000" ALIGN=LEFT> Supervisor, HOD, Manager</TD>
 * <TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000;
 * border-left: 1px solid #000000; border-right: 1px solid #000000"
 * ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000">HOD, HR Dir, GM</FONT></TD>
 * <TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000;
 * border-left: 1px solid #000000; border-right: 1px solid #000000"
 * ALIGN=CENTER><FONT FACE="Calibri" COLOR="#000000">HR Dir, GM</FONT></TD> <TD
 * ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD> <TD
 * ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD> <TD
 * ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD> <TD
 * ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD> </TR> <TR>
 * <TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000;
 * border-left: 1px solid #000000; border-right: 1px solid #000000" HEIGHT=19
 * ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT FACE="Calibri" COLOR="#000000">Staff
 * &ndash; Ast Man.</FONT></TD> <TD STYLE="border-top: 1px solid #000000;
 * border-bottom: 1px solid #000000; border-left: 1px solid #000000;
 * border-right: 1px solid #000000" ALIGN=CENTER><FONT FACE="Calibri"
 * COLOR="#000000">tidak ada</FONT></TD> <TD STYLE="border-top: 1px solid
 * #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000;
 * border-right: 1px solid #000000" ALIGN=CENTER><FONT FACE="Calibri"
 * COLOR="#000000">ada</FONT></TD> <TD STYLE="border-top: 1px solid #000000;
 * border-bottom: 1px solid #000000; border-left: 1px solid #000000;
 * border-right: 1px solid #000000" ALIGN=CENTER><FONT FACE="Calibri"
 * COLOR="#000000">ada</FONT></TD> <TD STYLE="border-top: 1px solid #000000;
 * border-bottom: 1px solid #000000; border-left: 1px solid #000000;
 * border-right: 1px solid #000000" ALIGN=LEFT> Supervisor, Link.HOD, Dir</TD>
 * <TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000;
 * border-left: 1px solid #000000; border-right: 1px solid #000000"
 * ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000">Link.HOD,
 * Div.Head</FONT></TD> <TD STYLE="border-top: 1px solid #000000; border-bottom:
 * 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid
 * #000000" ALIGN=CENTER><FONT FACE="Calibri" COLOR="#000000">HR Dir,
 * GM</FONT></TD> <TD ALIGN=LEFT><FONT FACE="Calibri"
 * COLOR="#000000"><BR></FONT></TD> <TD ALIGN=LEFT><FONT FACE="Calibri"
 * COLOR="#000000"><BR></FONT></TD> <TD ALIGN=LEFT><FONT FACE="Calibri"
 * COLOR="#000000"><BR></FONT></TD> <TD ALIGN=LEFT><FONT FACE="Calibri"
 * COLOR="#000000"><BR></FONT></TD> </TR> <TR> <TD STYLE="border-top: 1px solid
 * #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000;
 * border-right: 1px solid #000000" HEIGHT=19 ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT
 * FACE="Calibri" COLOR="#000000">Staff &ndash; Ast Man.</FONT></TD> <TD
 * STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000;
 * border-left: 1px solid #000000; border-right: 1px solid #000000"
 * ALIGN=CENTER><FONT FACE="Calibri" COLOR="#000000">tidak ada</FONT></TD> <TD
 * STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000;
 * border-left: 1px solid #000000; border-right: 1px solid #000000"
 * ALIGN=CENTER><FONT FACE="Calibri" COLOR="#000000">ada</FONT></TD> <TD
 * STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000;
 * border-left: 1px solid #000000; border-right: 1px solid #000000"
 * ALIGN=CENTER><FONT FACE="Calibri" COLOR="#000000">tidak ada</FONT></TD> <TD
 * STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000;
 * border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=LEFT>
 * Supervisor, Link.HOD,Dir</TD> <TD STYLE="border-top: 1px solid #000000;
 * border-bottom: 1px solid #000000; border-left: 1px solid #000000;
 * border-right: 1px solid #000000" ALIGN=LEFT><FONT FACE="Calibri"
 * COLOR="#000000">Link.HOD, HR Dir, GM</FONT></TD> <TD STYLE="border-top: 1px
 * solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid
 * #000000; border-right: 1px solid #000000" ALIGN=CENTER><FONT FACE="Calibri"
 * COLOR="#000000">HR Dir, GM</FONT></TD> <TD ALIGN=LEFT><FONT FACE="Calibri"
 * COLOR="#000000"><BR></FONT></TD> <TD ALIGN=LEFT><FONT FACE="Calibri"
 * COLOR="#000000"><BR></FONT></TD> <TD ALIGN=LEFT><FONT FACE="Calibri"
 * COLOR="#000000"><BR></FONT></TD> <TD ALIGN=LEFT><FONT FACE="Calibri"
 * COLOR="#000000"><BR></FONT></TD> </TR> <TR> <TD STYLE="border-top: 1px solid
 * #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000;
 * border-right: 1px solid #000000" HEIGHT=19 ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT
 * FACE="Calibri" COLOR="#000000">Staff &ndash; Ast Man.</FONT></TD> <TD
 * STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000;
 * border-left: 1px solid #000000; border-right: 1px solid #000000"
 * ALIGN=CENTER><FONT FACE="Calibri" COLOR="#000000">tidak ada</FONT></TD> <TD
 * STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000;
 * border-left: 1px solid #000000; border-right: 1px solid #000000"
 * ALIGN=CENTER><FONT FACE="Calibri" COLOR="#000000">tidak ada</FONT></TD> <TD
 * STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000;
 * border-left: 1px solid #000000; border-right: 1px solid #000000"
 * ALIGN=CENTER><FONT FACE="Calibri" COLOR="#000000">ada</FONT></TD> <TD
 * STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000;
 * border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=LEFT>
 * Supervisor, Div.Head.</TD> <TD STYLE="border-top: 1px solid #000000;
 * border-bottom: 1px solid #000000; border-left: 1px solid #000000;
 * border-right: 1px solid #000000" ALIGN=LEFT><FONT FACE="Calibri"
 * COLOR="#000000">Div.Head, HR Dir, GM</FONT></TD> <TD STYLE="border-top: 1px
 * solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid
 * #000000; border-right: 1px solid #000000" ALIGN=CENTER><FONT FACE="Calibri"
 * COLOR="#000000">HR Dir, GM</FONT></TD> <TD ALIGN=LEFT><FONT FACE="Calibri"
 * COLOR="#000000"><BR></FONT></TD> <TD ALIGN=LEFT><FONT FACE="Calibri"
 * COLOR="#000000"><BR></FONT></TD> <TD ALIGN=LEFT><FONT FACE="Calibri"
 * COLOR="#000000"><BR></FONT></TD> <TD ALIGN=LEFT><FONT FACE="Calibri"
 * COLOR="#000000"><BR></FONT></TD> </TR> <TR> <TD STYLE="border-top: 1px solid
 * #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000;
 * border-right: 1px solid #000000" HEIGHT=19 ALIGN=LEFT BGCOLOR="#FFFFFF"><FONT
 * FACE="Calibri" COLOR="#000000">Staff &ndash; Ast Man.</FONT></TD> <TD
 * STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000;
 * border-left: 1px solid #000000; border-right: 1px solid #000000"
 * ALIGN=CENTER><FONT FACE="Calibri" COLOR="#000000">tidak ada</FONT></TD> <TD
 * STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000;
 * border-left: 1px solid #000000; border-right: 1px solid #000000"
 * ALIGN=CENTER><FONT FACE="Calibri" COLOR="#000000">tidak ada</FONT></TD> <TD
 * STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000;
 * border-left: 1px solid #000000; border-right: 1px solid #000000"
 * ALIGN=CENTER><FONT FACE="Calibri" COLOR="#000000">tidak ada</FONT></TD> <TD
 * STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000;
 * border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=LEFT>
 * HR.Man, HR.Dir</TD> <TD STYLE="border-top: 1px solid #000000; border-bottom:
 * 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid
 * #000000" ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000">HR.Dir,
 * GM</FONT></TD> <TD STYLE="border-top: 1px solid #000000; border-bottom: 1px
 * solid #000000; border-left: 1px solid #000000; border-right: 1px solid
 * #000000" ALIGN=CENTER><FONT FACE="Calibri" COLOR="#000000">HR Dir,
 * GM</FONT></TD> <TD ALIGN=LEFT><FONT FACE="Calibri"
 * COLOR="#000000"><BR></FONT></TD> <TD ALIGN=LEFT><FONT FACE="Calibri"
 * COLOR="#000000"><BR></FONT></TD> <TD ALIGN=LEFT><FONT FACE="Calibri"
 * COLOR="#000000"><BR></FONT></TD> <TD ALIGN=LEFT><FONT FACE="Calibri"
 * COLOR="#000000"><BR></FONT></TD> </TR> <TR> <TD STYLE="border-top: 1px solid
 * #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000;
 * border-right: 1px solid #000000" HEIGHT=19 ALIGN=LEFT><FONT FACE="Calibri"
 * COLOR="#000000">Manager</FONT></TD> <TD STYLE="border-top: 1px solid #000000;
 * border-bottom: 1px solid #000000; border-left: 1px solid #000000;
 * border-right: 1px solid #000000" ALIGN=CENTER><FONT FACE="Calibri"
 * COLOR="#000000">tidak ada</FONT></TD> <TD STYLE="border-top: 1px solid
 * #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000;
 * border-right: 1px solid #000000" ALIGN=CENTER><FONT FACE="Calibri"
 * COLOR="#000000"><BR></FONT></TD> <TD STYLE="border-top: 1px solid #000000;
 * border-bottom: 1px solid #000000; border-left: 1px solid #000000;
 * border-right: 1px solid #000000" ALIGN=CENTER><FONT FACE="Calibri"
 * COLOR="#000000">ada</FONT></TD> <TD STYLE="border-top: 1px solid #000000;
 * border-bottom: 1px solid #000000; border-left: 1px solid #000000;
 * border-right: 1px solid #000000" ALIGN=LEFT> Div.Head, HR Dir</TD> <TD
 * STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000;
 * border-left: 1px solid #000000; border-right: 1px solid #000000"
 * ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000">Div.Head, HR Dir,
 * GM</FONT></TD> <TD STYLE="border-top: 1px solid #000000; border-bottom: 1px
 * solid #000000; border-left: 1px solid #000000; border-right: 1px solid
 * #000000" ALIGN=CENTER><FONT FACE="Calibri" COLOR="#000000">HR Dir,
 * GM</FONT></TD> <TD ALIGN=LEFT><FONT FACE="Calibri"
 * COLOR="#000000"><BR></FONT></TD> <TD ALIGN=LEFT><FONT FACE="Calibri"
 * COLOR="#000000"><BR></FONT></TD> <TD ALIGN=LEFT><FONT FACE="Calibri"
 * COLOR="#000000"><BR></FONT></TD> <TD ALIGN=LEFT><FONT FACE="Calibri"
 * COLOR="#000000"><BR></FONT></TD> </TR> <TR> <TD STYLE="border-top: 1px solid
 * #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000;
 * border-right: 1px solid #000000" HEIGHT=19 ALIGN=LEFT><FONT FACE="Calibri"
 * COLOR="#000000">Manager</FONT></TD> <TD STYLE="border-top: 1px solid #000000;
 * border-bottom: 1px solid #000000; border-left: 1px solid #000000;
 * border-right: 1px solid #000000" ALIGN=CENTER><FONT FACE="Calibri"
 * COLOR="#000000">ada</FONT></TD> <TD STYLE="border-top: 1px solid #000000;
 * border-bottom: 1px solid #000000; border-left: 1px solid #000000;
 * border-right: 1px solid #000000" ALIGN=CENTER><FONT FACE="Calibri"
 * COLOR="#000000"><BR></FONT></TD> <TD STYLE="border-top: 1px solid #000000;
 * border-bottom: 1px solid #000000; border-left: 1px solid #000000;
 * border-right: 1px solid #000000" ALIGN=CENTER><FONT FACE="Calibri"
 * COLOR="#000000">ada</FONT></TD> <TD STYLE="border-top: 1px solid #000000;
 * border-bottom: 1px solid #000000; border-left: 1px solid #000000;
 * border-right: 1px solid #000000" ALIGN=LEFT> HOD, Div.Head</TD> <TD
 * STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000;
 * border-left: 1px solid #000000; border-right: 1px solid #000000"
 * ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000">HOD, Div.Head</FONT></TD> <TD
 * STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000;
 * border-left: 1px solid #000000; border-right: 1px solid #000000"
 * ALIGN=CENTER><FONT FACE="Calibri" COLOR="#000000">HR Dir, GM</FONT></TD> <TD
 * ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD> <TD
 * ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD> <TD
 * ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD> <TD
 * ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD> </TR> <TR>
 * <TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000;
 * border-left: 1px solid #000000; border-right: 1px solid #000000" HEIGHT=19
 * ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000">Manager</FONT></TD> <TD
 * STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000;
 * border-left: 1px solid #000000; border-right: 1px solid #000000"
 * ALIGN=CENTER><FONT FACE="Calibri" COLOR="#000000">tidak ada</FONT></TD> <TD
 * STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000;
 * border-left: 1px solid #000000; border-right: 1px solid #000000"
 * ALIGN=CENTER><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD> <TD
 * STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000;
 * border-left: 1px solid #000000; border-right: 1px solid #000000"
 * ALIGN=CENTER><FONT FACE="Calibri" COLOR="#000000">tidak ada</FONT></TD> <TD
 * STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000;
 * border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=LEFT>
 * HR. Dir, GM</TD> <TD STYLE="border-top: 1px solid #000000; border-bottom: 1px
 * solid #000000; border-left: 1px solid #000000; border-right: 1px solid
 * #000000" ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000">HR Dir,
 * GM</FONT></TD> <TD STYLE="border-top: 1px solid #000000; border-bottom: 1px
 * solid #000000; border-left: 1px solid #000000; border-right: 1px solid
 * #000000" ALIGN=CENTER><FONT FACE="Calibri" COLOR="#000000">HR Dir,
 * GM</FONT></TD> <TD ALIGN=LEFT><FONT FACE="Calibri"
 * COLOR="#000000"><BR></FONT></TD> <TD ALIGN=LEFT><FONT FACE="Calibri"
 * COLOR="#000000"><BR></FONT></TD> <TD ALIGN=LEFT><FONT FACE="Calibri"
 * COLOR="#000000"><BR></FONT></TD> <TD ALIGN=LEFT><FONT FACE="Calibri"
 * COLOR="#000000"><BR></FONT></TD> </TR> <TR> <TD STYLE="border-top: 1px solid
 * #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000;
 * border-right: 1px solid #000000" HEIGHT=19 ALIGN=LEFT><FONT FACE="Calibri"
 * COLOR="#000000">Manager</FONT></TD> <TD STYLE="border-top: 1px solid #000000;
 * border-bottom: 1px solid #000000; border-left: 1px solid #000000;
 * border-right: 1px solid #000000" ALIGN=CENTER><FONT FACE="Calibri"
 * COLOR="#000000">ada</FONT></TD> <TD STYLE="border-top: 1px solid #000000;
 * border-bottom: 1px solid #000000; border-left: 1px solid #000000;
 * border-right: 1px solid #000000" ALIGN=CENTER><FONT FACE="Calibri"
 * COLOR="#000000"><BR></FONT></TD> <TD STYLE="border-top: 1px solid #000000;
 * border-bottom: 1px solid #000000; border-left: 1px solid #000000;
 * border-right: 1px solid #000000" ALIGN=CENTER><FONT FACE="Calibri"
 * COLOR="#000000">tidak ada</FONT></TD> <TD STYLE="border-top: 1px solid
 * #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000;
 * border-right: 1px solid #000000" ALIGN=LEFT> HOD, HR. Dir</TD> <TD
 * STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000;
 * border-left: 1px solid #000000; border-right: 1px solid #000000"
 * ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000">HOD, HR Dir, GM</FONT></TD>
 * <TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000;
 * border-left: 1px solid #000000; border-right: 1px solid #000000"
 * ALIGN=CENTER><FONT FACE="Calibri" COLOR="#000000">HR Dir, GM</FONT></TD> <TD
 * ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD> <TD
 * ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD> <TD
 * ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD> <TD
 * ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD> </TR> <TR>
 * <TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000;
 * border-left: 1px solid #000000; border-right: 1px solid #000000" HEIGHT=19
 * ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000">HOD</FONT></TD> <TD
 * STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000;
 * border-left: 1px solid #000000; border-right: 1px solid #000000"
 * ALIGN=CENTER><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD> <TD
 * STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000;
 * border-left: 1px solid #000000; border-right: 1px solid #000000"
 * ALIGN=CENTER><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD> <TD
 * STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000;
 * border-left: 1px solid #000000; border-right: 1px solid #000000"
 * ALIGN=CENTER><FONT FACE="Calibri" COLOR="#000000">ada</FONT></TD> <TD
 * STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000;
 * border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=LEFT>
 * Div.Head, HR Dir</TD> <TD STYLE="border-top: 1px solid #000000;
 * border-bottom: 1px solid #000000; border-left: 1px solid #000000;
 * border-right: 1px solid #000000" ALIGN=LEFT><FONT FACE="Calibri"
 * COLOR="#000000">HOD, Div.Head</FONT></TD> <TD STYLE="border-top: 1px solid
 * #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000;
 * border-right: 1px solid #000000" ALIGN=CENTER><FONT FACE="Calibri"
 * COLOR="#000000">HR Dir, GM</FONT></TD> <TD ALIGN=LEFT><FONT FACE="Calibri"
 * COLOR="#000000"><BR></FONT></TD> <TD ALIGN=LEFT><FONT FACE="Calibri"
 * COLOR="#000000"><BR></FONT></TD> <TD ALIGN=LEFT><FONT FACE="Calibri"
 * COLOR="#000000"><BR></FONT></TD> <TD ALIGN=LEFT><FONT FACE="Calibri"
 * COLOR="#000000"><BR></FONT></TD> </TR> <TR> <TD STYLE="border-top: 1px solid
 * #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000;
 * border-right: 1px solid #000000" HEIGHT=19 ALIGN=LEFT><FONT FACE="Calibri"
 * COLOR="#000000">HOD</FONT></TD> <TD STYLE="border-top: 1px solid #000000;
 * border-bottom: 1px solid #000000; border-left: 1px solid #000000;
 * border-right: 1px solid #000000" ALIGN=CENTER><FONT FACE="Calibri"
 * COLOR="#000000"><BR></FONT></TD> <TD STYLE="border-top: 1px solid #000000;
 * border-bottom: 1px solid #000000; border-left: 1px solid #000000;
 * border-right: 1px solid #000000" ALIGN=CENTER><FONT FACE="Calibri"
 * COLOR="#000000"><BR></FONT></TD> <TD STYLE="border-top: 1px solid #000000;
 * border-bottom: 1px solid #000000; border-left: 1px solid #000000;
 * border-right: 1px solid #000000" ALIGN=CENTER><FONT FACE="Calibri"
 * COLOR="#000000">tidak ada</FONT></TD> <TD STYLE="border-top: 1px solid
 * #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000;
 * border-right: 1px solid #000000" ALIGN=LEFT> HR. Dir, GM</TD> <TD
 * STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000;
 * border-left: 1px solid #000000; border-right: 1px solid #000000"
 * ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000">HR Dir, GM</FONT></TD> <TD
 * STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000;
 * border-left: 1px solid #000000; border-right: 1px solid #000000"
 * ALIGN=CENTER><FONT FACE="Calibri" COLOR="#000000">HR Dir, GM</FONT></TD> <TD
 * ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD> <TD
 * ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD> <TD
 * ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD> <TD
 * ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD> </TR> <TR>
 * <TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000;
 * border-left: 1px solid #000000; border-right: 1px solid #000000" HEIGHT=19
 * ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000">Director</FONT></TD> <TD
 * STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000;
 * border-left: 1px solid #000000; border-right: 1px solid #000000"
 * ALIGN=CENTER><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD> <TD
 * STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000;
 * border-left: 1px solid #000000; border-right: 1px solid #000000"
 * ALIGN=CENTER><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD> <TD
 * STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000;
 * border-left: 1px solid #000000; border-right: 1px solid #000000"
 * ALIGN=CENTER><FONT FACE="Calibri" COLOR="#000000">ada</FONT></TD> <TD
 * STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000;
 * border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=LEFT>
 * Div.Head, HR Dir</TD> <TD STYLE="border-top: 1px solid #000000;
 * border-bottom: 1px solid #000000; border-left: 1px solid #000000;
 * border-right: 1px solid #000000" ALIGN=LEFT><FONT FACE="Calibri"
 * COLOR="#000000">Div.Head, HR Dir, GM</FONT></TD> <TD STYLE="border-top: 1px
 * solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid
 * #000000; border-right: 1px solid #000000" ALIGN=CENTER><FONT FACE="Calibri"
 * COLOR="#000000">HR Dir, GM</FONT></TD> <TD ALIGN=LEFT><FONT FACE="Calibri"
 * COLOR="#000000"><BR></FONT></TD> <TD ALIGN=LEFT><FONT FACE="Calibri"
 * COLOR="#000000"><BR></FONT></TD> <TD ALIGN=LEFT><FONT FACE="Calibri"
 * COLOR="#000000"><BR></FONT></TD> <TD ALIGN=LEFT><FONT FACE="Calibri"
 * COLOR="#000000"><BR></FONT></TD> </TR> <TR> <TD STYLE="border-top: 1px solid
 * #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000;
 * border-right: 1px solid #000000" HEIGHT=19 ALIGN=LEFT><FONT FACE="Calibri"
 * COLOR="#000000">Director</FONT></TD> <TD STYLE="border-top: 1px solid
 * #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000;
 * border-right: 1px solid #000000" ALIGN=CENTER><FONT FACE="Calibri"
 * COLOR="#000000"><BR></FONT></TD> <TD STYLE="border-top: 1px solid #000000;
 * border-bottom: 1px solid #000000; border-left: 1px solid #000000;
 * border-right: 1px solid #000000" ALIGN=CENTER><FONT FACE="Calibri"
 * COLOR="#000000"><BR></FONT></TD> <TD STYLE="border-top: 1px solid #000000;
 * border-bottom: 1px solid #000000; border-left: 1px solid #000000;
 * border-right: 1px solid #000000" ALIGN=CENTER><FONT FACE="Calibri"
 * COLOR="#000000">tidak ada</FONT></TD> <TD STYLE="border-top: 1px solid
 * #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000;
 * border-right: 1px solid #000000" ALIGN=LEFT> HR. Dir, GM</TD> <TD
 * STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000;
 * border-left: 1px solid #000000; border-right: 1px solid #000000"
 * ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000">HR Dir, GM</FONT></TD> <TD
 * STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000;
 * border-left: 1px solid #000000; border-right: 1px solid #000000"
 * ALIGN=CENTER><FONT FACE="Calibri" COLOR="#000000">HR Dir, GM</FONT></TD> <TD
 * ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD> <TD
 * ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD> <TD
 * ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD> <TD
 * ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD> </TR> <TR>
 * <TD STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000;
 * border-left: 1px solid #000000; border-right: 1px solid #000000" HEIGHT=19
 * ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000">Div. Head</FONT></TD> <TD
 * STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000;
 * border-left: 1px solid #000000; border-right: 1px solid #000000"
 * ALIGN=CENTER><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD> <TD
 * STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000;
 * border-left: 1px solid #000000; border-right: 1px solid #000000"
 * ALIGN=CENTER><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD> <TD
 * STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000;
 * border-left: 1px solid #000000; border-right: 1px solid #000000"
 * ALIGN=CENTER><FONT FACE="Calibri" COLOR="#000000"><BR></FONT></TD> <TD
 * STYLE="border-top: 1px solid #000000; border-bottom: 1px solid #000000;
 * border-left: 1px solid #000000; border-right: 1px solid #000000" ALIGN=LEFT>
 * HR. Dir, GM</TD> <TD STYLE="border-top: 1px solid #000000; border-bottom: 1px
 * solid #000000; border-left: 1px solid #000000; border-right: 1px solid
 * #000000" ALIGN=LEFT><FONT FACE="Calibri" COLOR="#000000">HR Dir,
 * GM</FONT></TD> <TD STYLE="border-top: 1px solid #000000; border-bottom: 1px
 * solid #000000; border-left: 1px solid #000000; border-right: 1px solid
 * #000000" ALIGN=CENTER><FONT FACE="Calibri" COLOR="#000000">HR Dir,
 * GM</FONT></TD> <TD ALIGN=LEFT><FONT FACE="Calibri"
 * COLOR="#000000"><BR></FONT></TD> <TD ALIGN=LEFT><FONT FACE="Calibri"
 * COLOR="#000000"><BR></FONT></TD> <TD ALIGN=LEFT><FONT FACE="Calibri"
 * COLOR="#000000"><BR></FONT></TD> <TD ALIGN=LEFT><FONT FACE="Calibri"
 * COLOR="#000000"><BR></FONT></TD> </TR> </TBODY> </TABLE>
 */
public class LeaveConfigMelia implements I_Leave {

    private final int minTimeToHasDP = 7; //Dalam jam  ; Minimum jam kerja untuk memperoleh DP
    private final int iDayAYear = 365;
    private int iEntitleType = AL_ENTITLE_BY_COMMENCING;//jenis type entitle yg di terima
    private int iALEarnedBy = AL_EARNED_BY_TOTAL;
    private int MIN_LL_MINUS = -15;
    private boolean isALStockMinus = false;//update by satrya 2012-08-10 true 
    private boolean isLLStockMinus = false;
    //update by satrya 2013-08-18
    //private int iMinMinusAl = -12;
    //private int iMaxMinusAl = -24;
    //update satrya 2012-10-08
    private boolean isDPStockMinus = false;
    private float eligibleDayMaxMinus = -6;
    private int iALExpirationDay = getiDayAYear();
    private int iALExpWarning = getiDayAYear() - 60;
    private int iMinLLTakenAnual = 0;
    private int iMaxLLTakenAnual = 0;
    //private int iLLExpirationDay = iDayAYear * iIntervalLLs[0] ;
    private int iLLExpirationDay = getiDayAYear() * (iIntervalLLs[0] / 12);
    private int iLLExpWarning = iLLExpirationDay - 90;

    /* in month, minimal berapa bulan bekerja untuk mendapatkan stock AL */
    private int iMinWorkToGetAnual = 0/*12*/;   // month
    /* in month, minimal berapa bulan bekerja untuk boleh mengambil cuti */
    private int iMinWorkCanGetAnnual = 12; // month

    /*Tanggal dan bulan closing jika memakai Leave by Period*/
    private int iDateClosingAnnual = 31; //Tanggal closing jika by commercing di set by commercing
    private int iMonthClosingAnnual = 12; //Bulan closing jika by commercing di set by commercing
    private int date = iDateClosingAnnual;
    private int month = iMonthClosingAnnual;

    /* Untuk mengecek batas tanggal untu menentukan periode bulanan, seperti bila limit adalah 17,
     * kemudian karyawan tersebut masuk tanggal 15,karena 15 kurang dari 17 maka akan dapat periode bulana pada bulan sekarang,
     * Sedangkan bila commencing date adalah 19, karena 19 lebih besar dari 17, maka periode bulanan yang didapt adalah periode berikutnya
     * tanggal pembatas berhak atau tidaknya karyawan mendapatkan AL berdasarkan commercing date'nya
     */
    private int dateLimit = 15; /* bulan januari */

    /* Periode untuk mendapatkan entitle Al , dimana mendapatkan al ketika awal periode yaitu 26 Januari*/
    private String startAnnualLeavePeriod = ""; //month-date // PEriode 26 januari    
    public static final int LEVEL_EXCO = 0;//Executive Committee
    public static final int LEVEL_JM1 = 1;//Junior Manager 1
    public static final int LEVEL_JM2 = 2;//Junior Manager 2
    public static final int LEVEL_RF1 = 3;//Rank & File 1
    public static final int LEVEL_RF2 = 4;//Rank & File 2
    public static final int LEVEL_RF3 = 5;//Rank & File 3
    public static final int LEVEL_RF4 = 6;//Rank & File 4
    public static final int LEVEL_SM1 = 7;//Senior Manager 1
    public static final int LEVEL_SM2 = 8;//Senior Manager 2
    public static final int LEVEL_SPV1 = 9;//Supervisor 1
    public static final int LEVEL_SPV2 = 10;//Supervisor 2
    public static final int LEVEL_GENERAL_MANAGER = 11;
    private final String[] strLevels = {
        "EXCO", //0
        "JM1",
        "JM2",
        "RF1",
        "RF2",
        "RF3",
        "RF4",
        "SM1",
        "SM2",
        "SPV1",
        "SPV2",
        "General Manager"
    };
    public static final int CATEGORY_APPRENTICESHIP = 0;
    public static final int CATEGORY_CONTRACT = 1;
    public static final int CATEGORY_DAILY_WORKER = 2;
    public static final int CATEGORY_PERMANENT = 3;
    public static final int CATEGORY_SEMI_PERMANENT = 4;
    public static final int CATEGORY_TRAINEE = 5;
    public static final int CATEGORY_EXPATRIAT = 6;
    public static final int CATEGORY_OUTSOURCH = 7;
    private final String[] strCategory = {
        "APPRENTICESHIP",
        "CONTRACTUAL",
        "DAILY WORKER",
        "PERMANENT",
        "SEMI PERMANENT",
        "TRAINEE",
        "EXPATRIAT",
        "OUTSOURCHING"
    };
    //public static final int HARDROCK_HOTEL = 0;
    //public static final int SANUR_PARADISE = 1;
    // Penambahan AL perbulan 1
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
    // Penambahan AL perbulan 1
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
    // index entitle untuk LL
    public static final int iInterval_6_year = 0;
    public static final int iInterval_8_year = 1;
    public static final int iINTERVAL_LL_HARDROCK_I = 0;
    public static final int iINTERVAL_LL_HARDROCK_II = 1;
    //public static final String OID_POSITION_SPECIAL_APPROVE_BY_GM = "504404193525679227,504404193872456655,504404544835815699";
    // index Interval untuk LL
    private static final int[] iIntervalLLs = {
        5 * 12,
        0 * 12
    };
    public static final int INTERVAL_AL_KONFIGURASI_I = 0;
    private static final int[] iIntervalAL = {
        1 * 12
    };
    //AL Standard / default
    private int[][] iALEntitles = {
        //"APPRENTICESHIP","CONTRACT","DAILY WORKER","PERMANENT","SEMI PERMANENT","TRAINEE","EXPATRIAT","OUTSOURCH"
        {0, 12, 0, 12, 12, 0, 12, 0}, //Level 0
        {0, 12, 0, 12, 12, 0, 12, 0}, //Level 1
        {0, 12, 0, 12, 12, 0, 12, 0}, //Level 2
        {0, 12, 0, 12, 12, 0, 12, 0}, //Level 3
        {0, 12, 0, 12, 12, 0, 12, 0}, //Level 4
        {0, 12, 0, 12, 12, 0, 12, 0}, //Level 5
        {0, 12, 0, 12, 12, 0, 12, 0}, //Level 6
        {0, 12, 0, 12, 12, 0, 12, 0}, //Level 7
        {0, 12, 0, 12, 12, 0, 12, 0}, //Level 8
        {0, 12, 0, 12, 12, 0, 12, 0}, //Level 9
        {0, 12, 0, 12, 12, 0, 12, 0}, //Level 10
        {0, 12, 0, 12, 12, 0, 12, 0} //Level 11
    };
    //penambahan entitle AL Extra setelah masa kerja 11 tahun
    private int iYearsExtraAl = 11;
    private int[][] iALEntitlesExtra = { // tambahan cuti tahunan extra
        //"APPRENTICESHIP","CONTRACT","DAILY WORKER","PERMANENT","SEMI PERMANENT","TRAINEE","EXPATRIAT","OUTSOURCH"
        {0, 12, 0, 12, 12, 0, 12, 0}, //Level 0
        {0, 12, 0, 12, 12, 0, 12, 0}, //Level 1
        {0, 12, 0, 12, 12, 0, 12, 0}, //Level 2
        {0, 12, 0, 12, 12, 0, 12, 0}, //Level 3
        {0, 12, 0, 12, 12, 0, 12, 0}, //Level 4
        {0, 12, 0, 12, 12, 0, 12, 0}, //Level 5
        {0, 12, 0, 12, 12, 0, 12, 0}, //Level 6
        {0, 12, 0, 12, 12, 0, 12, 0}, //Level 7
        {0, 12, 0, 12, 12, 0, 12, 0}, //Level 8
        {0, 12, 0, 12, 12, 0, 12, 0}, //Level 9
        {0, 12, 0, 12, 12, 0, 12, 0}, //Level 10
        {0, 12, 0, 12, 12, 0, 12, 0} //Level 11
    };
    //AL ketika memperoleh LL
    // mendapatkan cuti tahuanan tpi di tahun itu karyawan mendapatkan LL
    // AL yg akan bertambah
    private int[][] iALEntitlesByLL = {
        //"APPRENTICESHIP","CONTRACT","DAILY WORKER","PERMANENT","SEMI PERMANENT","TRAINEE","EXPATRIAT","OUTSOURCH"
        {0, 0, 0, 0, 0, 0, 0, 0}, //Level 0
        {0, 0, 0, 0, 0, 0, 0, 0}, //Level 1
        {0, 0, 0, 0, 0, 0, 0, 0}, //Level 2
        {0, 0, 0, 0, 0, 0, 0, 0}, //Level 3
        {0, 0, 0, 0, 0, 0, 0, 0}, //Level 4
        {0, 0, 0, 0, 0, 0, 0, 0}, //Level 5
        {0, 0, 0, 0, 0, 0, 0, 0}, //Level 6
        {0, 0, 0, 0, 0, 0, 0, 0}, //Level 7
        {0, 0, 0, 0, 0, 0, 0, 0}, //Level 8
        {0, 0, 0, 0, 0, 0, 0, 0}, //Level 9
        {0, 0, 0, 0, 0, 0, 0, 0}, //Level 10
        {0, 0, 0, 0, 0, 0, 0, 0} //Level 11
    };
    //LL Default
    private int[][][] iLLEntitles = {
        //"APPRENTICESHIP","CONTRACT","DAILY WORKER","PERMANENT","SEMI PERMANENT","TRAINEE","EXPATRIAT","OUTSOURCH"
        //"In 6 Year","In 7 Year"
        {{0, 0}, {0, 0}, {0, 0}, {30, 0}, {30, 0}, {0, 0}, {30, 0}, {0, 0}}, //Level 0
        {{0, 0}, {0, 0}, {0, 0}, {30, 0}, {30, 0}, {0, 0}, {30, 0}, {0, 0}}, //Level 1
        {{0, 0}, {0, 0}, {0, 0}, {30, 0}, {30, 0}, {0, 0}, {30, 0}, {0, 0}}, //Level 2
        {{0, 0}, {0, 0}, {0, 0}, {30, 0}, {30, 0}, {0, 0}, {30, 0}, {0, 0}}, //Level 3
        {{0, 0}, {0, 0}, {0, 0}, {30, 0}, {30, 0}, {0, 0}, {30, 0}, {0, 0}}, //Level 4
        {{0, 0}, {0, 0}, {0, 0}, {30, 0}, {30, 0}, {0, 0}, {30, 0}, {0, 0}}, //Level 5
        {{0, 0}, {0, 0}, {0, 0}, {30, 0}, {30, 0}, {0, 0}, {30, 0}, {0, 0}}, //Level 6
        {{0, 0}, {0, 0}, {0, 0}, {30, 0}, {30, 0}, {0, 0}, {30, 0}, {0, 0}}, //Level 7
        {{0, 0}, {0, 0}, {0, 0}, {30, 0}, {30, 0}, {0, 0}, {30, 0}, {0, 0}}, //Level 8
        {{0, 0}, {0, 0}, {0, 0}, {30, 0}, {30, 0}, {0, 0}, {30, 0}, {0, 0}}, //Level 9
        {{0, 0}, {0, 0}, {0, 0}, {30, 0}, {30, 0}, {0, 0}, {30, 0}, {0, 0}}, //Level 10
        {{0, 0}, {0, 0}, {0, 0}, {30, 0}, {30, 0}, {0, 0}, {30, 0}, {0, 0}} //Level 11
    };
// masa berlakunya LL  dalam bulan misalkan karyawan 
//mendapakan LL tgl 10 juni 2013 maka masa berlakunya LL 10 juni 2014, setelah itu hangus
    // {1 * 12, 1 * 12}  entitle LL di bagi berdasarkan periode I dan II agar karyawan tidak lama cuti LL   
    private int[][][] iLLValidity = {
        //APAKAH INI MASA BERLAKU LL
        //"APPRENTICESHIP","CONTRACT","DAILY WORKER","PERMANENT","SEMI PERMANENT","TRAINEE","EXPATRIAT","OUTSOURCH"
        //"In 7 Year","In 8 Year"     100*12 means 100 year = never expired
        {{0, 0}, {5 * 12, 0 * 12}, {0, 0}, {5 * 12, 0 * 12}, {5 * 12, 0 * 12}, {0, 0}, {5 * 12, 0 * 12}, {0, 0}}, //Level 0
        {{0, 0}, {5 * 12, 0 * 12}, {0, 0}, {5 * 12, 0 * 12}, {5 * 12, 0 * 12}, {0, 0}, {5 * 12, 0 * 12}, {0, 0}}, //Level 1
        {{0, 0}, {5 * 12, 0 * 12}, {0, 0}, {5 * 12, 0 * 12}, {5 * 12, 0 * 12}, {0, 0}, {5 * 12, 0 * 12}, {0, 0}}, //Level 2
        {{0, 0}, {5 * 12, 0 * 12}, {0, 0}, {5 * 12, 0 * 12}, {5 * 12, 0 * 12}, {0, 0}, {5 * 12, 0 * 12}, {0, 0}}, //Level 3
        {{0, 0}, {5 * 12, 0 * 12}, {0, 0}, {5 * 12, 0 * 12}, {5 * 12, 0 * 12}, {0, 0}, {5 * 12, 0 * 12}, {0, 0}}, //Level 4
        {{0, 0}, {5 * 12, 0 * 12}, {0, 0}, {5 * 12, 0 * 12}, {5 * 12, 0 * 12}, {0, 0}, {5 * 12, 0 * 12}, {0, 0}}, //Level 5
        {{0, 0}, {5 * 12, 0 * 12}, {0, 0}, {5 * 12, 0 * 12}, {5 * 12, 0 * 12}, {0, 0}, {5 * 12, 0 * 12}, {0, 0}}, //Level 6
        {{0, 0}, {5 * 12, 0 * 12}, {0, 0}, {5 * 12, 0 * 12}, {5 * 12, 0 * 12}, {0, 0}, {5 * 12, 0 * 12}, {0, 0}}, //Level 7
        {{0, 0}, {5 * 12, 0 * 12}, {0, 0}, {5 * 12, 0 * 12}, {5 * 12, 0 * 12}, {0, 0}, {5 * 12, 0 * 12}, {0, 0}}, //Level 8
        {{0, 0}, {5 * 12, 0 * 12}, {0, 0}, {5 * 12, 0 * 12}, {5 * 12, 0 * 12}, {0, 0}, {5 * 12, 0 * 12}, {0, 0}}, //Level 9
        {{0, 0}, {5 * 12, 0 * 12}, {0, 0}, {5 * 12, 0 * 12}, {5 * 12, 0 * 12}, {0, 0}, {5 * 12, 0 * 12}, {0, 0}}, //Level 10
        {{0, 0}, {5 * 12, 0 * 12}, {0, 0}, {5 * 12, 0 * 12}, {5 * 12, 0 * 12}, {0, 0}, {5 * 12, 0 * 12}, {0, 0}}, //Level 11
    };
    //DP Standard
    private int[] iDPStandardEntitle = {
        2, //Level 0
        2, //Level 1
        2, //Level 2
        2, //Level 3
        2, //Level 4
        2, //Level 5
        2, //Level 6
        2, //Level 7
        2, //Level 8
        2, //Level 9
        2, //Level 10
        2 //Level 11
    };
    /**
     * Hari libur dan dia harusnya Off tpi employee kerja ( ada hari raya tpi
     * jadwalnya libur) misalkan hari raya galungan(karyawan beragama hindu),
     * karyawan seharusnya libur tapi karyawan tsb harus masuk
     */
    private int[] iDPHolidayOffEntitle = {
        2, //Level 0
        2, //Level 1
        2, //Level 2
        2, //Level 3
        2, //Level 4
        2, //Level 5
        2, //Level 6
        2, //Level 7
        2, //Level 8
        2, //Level 9
        2, //Level 10
        2 //Level 11
    };
    private Hashtable iDPSpecialHolidayEntitle = new Hashtable();
    private final String HOLIDAY_NYEPI = "Nyepi";
    /**
     * Pada tanggal 1 january seharusnya karyawan tsb libur tapi karyawan tsb
     * kerja, maka mendapatkan DP
     */
    private int[] iDPHolidayPresentEntitle = {
        2, //Level 0
        2, //Level 1
        2, //Level 2
        2, //Level 3
        2, //Level 4
        2, //Level 5
        2, //Level 6
        2, //Level 7
        2, //Level 8
        2, //Level 9
        2, //Level 10
        2 //Level 11
    };
    /**
     * karyawan ulang tahun dan perusahaan menjadwalkan karywan tersebut OFF tpi
     * karywan tsb bekerja
     */
    private int[] iDPBirthdayOffEntitle = {
        0, //Level 0
        0, //Level 1
        0, //Level 2
        0, //Level 3
        0, //Level 4
        0, //Level 5
        0, //Level 6
        0, //Level 7
        0, //Level 8
        0, //Level 9
        0, //Level 10
        0 //Level 11
    };
    /**
     * karyawan ulang tahun dan perusahaan menjadwalkan karywan tersebut kerja
     * tpi karywan tsb bekerja
     */
    private int[] iDPBirthdayPresentEntitle = {
        0, //Level 0
        0, //Level 1
        0, //Level 2
        0, //Level 3
        0, //Level 4
        0, //Level 5
        0, //Level 6
        0, //Level 7
        0, //Level 8
        0, //Level 9
        0, //Level 10
        0 //Level 11
    };
    /**
     * Masa berlaku DP yg di terima, sejak tanggal di terima
     */
    private int[] iDpValidity = {
        //update 2012-10-12
        3, //Level 0
        3, //Level 1
        3, //Level 2
        3, //Level 3
        3, //Level 4
        3, //Level 5
        3, //Level 6
        3, //Level 7
        3, //Level 8
        3, //Level 9
        3, //Level 10
        3 //Level 11
    };
    /**
     * Approvall dari leave, tinggat level karyawan yg mengajukan cuti
     */
    int[] approvalNeed = {
        2, //Level 0        -> old = 3
        2, //Level 1        -> old = 3
        2, //Level 2        -> old = 3
        2, //Level 3
        2, //Level 4
        2, //Level 5
        2, //Level 6
        2, //Level 7
        2, //Level 8
        2, //Level 9
        2, //Level 10
        2 //Level 11
    };

    //Konstruktor
    public LeaveConfigMelia() {
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

        /*System.out.println("\nCONFIG ::::::: " + PstSystemProperty.getValueByName("LEAVE_CONFIG"));
         System.out.println(" => Entitle Type      : " + AL_ENTITLE_TYPE[iEntitleType]);
         System.out.println(" => AL Expiration Day : " + iALExpirationDay);
         System.out.println(" => AL Exp Warning    : " + iALExpWarning);
         System.out.println(" => Min LL Taken Anual: " + iMinLLTakenAnual);
         System.out.println(" => Max LL Taken Anual: " + iMaxLLTakenAnual);
         System.out.println(" => LL Expiration Day : " + iLLExpirationDay);
         System.out.println(" => LL Exp Warning    : " + iLLExpWarning);*/
        //  System.out.println(" => AL getALEntitleAnualLeave(Sample) : "+getALEntitleAnualLeave("Level B","DAILY WORKER"));
        //  System.out.println(" => LL getLLEntile(Sample)            : "+getLLEntile("Level B","CONTRACT",iIntervalLLs[INTERVAL_LL_8_YEAR])+"\n");
        Date dt = new Date();
        dt.setDate(20);
        dt.setMonth(0);
        dt.setYear(2008 - 1900);
        //   System.out.println(" => DP getDPEntitleByDate(Sample)            : "+getDPEntitleByDate("7008",dt)+"\n");
        //Test
        //getDPEntitleByDate("7005",new Date());
        /**
         * 1. di service center penambahan pengecekan public holiday(dilakukan
         * pada setiap hari) 2. pencArian kata nyepi
         */
        //penambahan Entitle DP jika ada public holiday nyepi
        iDPSpecialHolidayEntitle.put(HOLIDAY_NYEPI, 3);

    }

    /**
     * Set type of AL earned
     *
     * @param al_earn_by : AL_EARNED_BY_TOTAL=0 or AL_EARNED_PER_MONTH=1;
     */
    public void setALEarnedBy(int al_earn_by) {
        iALEarnedBy = al_earn_by;
    }

    /**
     * get type of AL earned
     *
     * @return
     */
    public int getAlEarnedBy() {
        return iALEarnedBy;
    }

    /**
     * set if AL stock can be minus ( more AL is taken by employee then earned )
     *
     * @param ALminus
     */
    public void setALStockMinus(boolean ALminus) {
        isALStockMinus = ALminus;
    }

    /**
     * get AL stock minus configuration
     *
     * @return boolean
     */
    public boolean getALStockMinus(AlStockTaken alStockTaken) {
        //update by satrya 2013-08-18
        //public boolean getALStockMinus() {
        //keterangan maximum minus/advance adalah setara entile (12 atau 24);

        if (alStockTaken != null && alStockTaken.getEmployeeId() != 0) {
            float stockAl = SessLeaveApplication.getEligibleAnnualLeave(null, alStockTaken);//- alStockTaken.getTakenQty();
            //float entitleByAlStockManagement =0;
            float alEntitleComming = 0;
            float maxMinusAl = 0;//Long.parseLong(PstSystemProperty.getValueByName("OID_HRD_DEPARTMENT"));\
            boolean useInputUser = false;
            try {
                maxMinusAl = Float.parseFloat(PstSystemProperty.getValueByName("MAX_LEAVE_AL_MINUS"));
                useInputUser = Boolean.parseBoolean(PstSystemProperty.getValueByName("USE_LEAVE_AL_INPUT_MINUS"));
            } catch (Exception exc) {
                System.out.println("Exc" + exc);

            }
            try {
                Employee emp = PstEmployee.fetchExc(alStockTaken.getEmployeeId());
                String strLevel = getLevel(emp.getOID());
                String strType = getCategory(emp.getOID());

                /*AlStockManagement alStockManagement = PstAlStockManagement.fetchExc(alStockTaken.getAlStockId());
                 entitleByAlStockManagement=alStockManagement!=null?alStockManagement.getEntitled():0f;*/

                if (maxMinusAl != 0) {
                    if (useInputUser) {
                        alEntitleComming = Math.abs(maxMinusAl);
                    } else {
                        alEntitleComming = getCommingALEntitleAnualLeave(
                                strLevel, strType, 0, emp.getCommencingDate(),
                                alStockTaken.getTakenFinnishDate());

                    }
                }
                /*alEntitleComming = getCommingALEntitleAnualLeave(
                 strLevel,strType,0, emp.getCommencingDate(),
                 alStockTaken.getTakenFinnishDate());*/
            } catch (Exception exc) {
            }

            /* if(alEntitleComming!=0 && (stockAl >= (alEntitleComming/2)*-1)){
             //(stockAl >= iMinMinusAl || stockAl >= iMaxMinusAl)){
             return true;
             }*/
            if (maxMinusAl == 0) {
                return true;
            } else if (alEntitleComming != 0 && (stockAl >= (useInputUser ? alEntitleComming : alEntitleComming / 2) * -1)) {
                //(stockAl >= iMinMinusAl || stockAl >= iMaxMinusAl)){
                return true;
            }
        }
        return isALStockMinus;
        //return isALStockMinus;
    }

    public boolean getALStockMinus(float eligible, long oidEmployee) {
        /*if(eligible >= iMinMinusAl || eligible >= iMaxMinusAl){
         return true;
         }*/
        //keterangan maximum minus/advance adalah setara entile (12 atau 24);

        if (oidEmployee != 0) {
            AlStockManagement alStockManagement = new AlStockManagement();
            float maxMinusAl = 0;//Long.parseLong(PstSystemProperty.getValueByName("OID_HRD_DEPARTMENT"));\
            boolean useInputUser = false;
            try {
                maxMinusAl = PstSystemProperty.getValueFloat("MAX_LEAVE_AL_MINUS");
                useInputUser = PstSystemProperty.getValueBoolean("USE_LEAVE_AL_INPUT_MINUS");
            } catch (Exception exc) {
                System.out.println("Exc" + exc);

            }
            float alEntitleComming = 0;
            try {
                Employee emp = PstEmployee.fetchExc(oidEmployee);
                String strLevel = getLevel(emp.getOID());
                String strType = getCategory(emp.getOID());

                /* String whareClause = PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_EMPLOYEE_ID]+"="+oidEmployee + " AND " + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_AL_STATUS]+"="+PstAlStockManagement.AL_STS_AKTIF;
                 Vector listAlStockManagement = PstAlStockManagement.list(0, 1,whareClause, "");
                 alStockManagement = (AlStockManagement)listAlStockManagement.get(0);*/
                if (maxMinusAl != 0) {
                    if (useInputUser) {
                        alEntitleComming = Math.abs(maxMinusAl);
                    } else {
                        alEntitleComming = getCommingALEntitleAnualLeave(
                                strLevel, strType, 0, emp.getCommencingDate(),
                                new Date());

                    }
                }

            } catch (Exception exc) {
            }

            if (maxMinusAl == 0) {
                return true;
            } else if (alEntitleComming != 0 && (eligible >= (useInputUser ? alEntitleComming : alEntitleComming / 2) * -1)) {
                //(stockAl >= iMinMinusAl || stockAl >= iMaxMinusAl)){
                return true;
            }
            /**
             * update by satrya 2013-11-23 if(alEntitleComming!=0 && (eligible
             * >= (alEntitleComming/2)*-1)){ //(stockAl >= iMinMinusAl ||
             * stockAl >= iMaxMinusAl)){ return true; }
             */
        }
        return isALStockMinus;
    }

    /**
     * set if LL stock can be minus ( more LL is taken by employee then earned )
     *
     * @param ALminus
     */
    public void setLLStockMinus(boolean LLminus) {
        isLLStockMinus = LLminus;
    }

    /**
     * get AL stock minus configuration
     *
     * @return boolean
     */
    public boolean getLLStockMinus(LlStockTaken llStockTaken, I_Leave leave, float eligbleDay) {
        //Keterangan  boleh minus setelah 5 tahun, masa berlaku LL: 5 tahun
        if (llStockTaken != null && llStockTaken.getEmployeeId() != 0) {
            float maxMinusLL = 0;
            boolean useInputUserMinusLL = false;
            try {
                maxMinusLL = Float.parseFloat(PstSystemProperty.getValueByName("MAX_LEAVE_LL_MINUS"));
                useInputUserMinusLL = Boolean.parseBoolean(PstSystemProperty.getValueByName("USE_LEAVE_LL_INPUT_MINUS"));
            } catch (Exception exc) {
                System.out.println("Exc" + exc);

            }
            Date comDate = PstEmployee.getCommercingDatePerEmployee(llStockTaken.getEmployeeId());
            String strLevel = "0";//hanya default
            String strType = "0";//hanya default
            try {
                strLevel = getLevel(llStockTaken.getEmployeeId());
                strType = getCategory(llStockTaken.getEmployeeId());
            } catch (Exception exc) {
            }
            int currYear = new Date().getYear() + 1900;
            int cmYear = 0;
            if (comDate != null) {
                cmYear = comDate.getYear() + 1900;
            }
            int age = currYear - cmYear;

            int[] intervalLL = leave.getIntervalLLinMonths();
            //int max = (intervalLL[leave.INTERVAL_LL_5_YEAR]/12)!=0?age / (intervalLL[leave.INTERVAL_LL_5_YEAR]/12):-1;

            if (comDate != null) {
                if (eligbleDay - llStockTaken.getTakenQty() > 0) {
                    return true;
                } else {
                    //artinya jika dia sudah bekerja min 5 tahun baru bisa minus LL
                    if (age >= (intervalLL[leave.INTERVAL_LL_5_YEAR] / 12)) {
                        //if ((age % (intervalLL[leave.INTERVAL_LL_5_YEAR]/12) == 0)){
                        //belum ada pengecekan berapa min dia boleh minus

                        float entitleLLComming = 0;//getLLEntile(strLevel, strType, intervalLL[leave.INTERVAL_LL_5_YEAR]);
                        if (maxMinusLL != 0) {
                            if (useInputUserMinusLL) {
                                entitleLLComming = Math.abs(maxMinusLL);
                            } else {
                                entitleLLComming = getLLEntile(strLevel, strType, intervalLL[leave.INTERVAL_LL_5_YEAR]);
                            }
                        }
                        if (maxMinusLL == 0) {
                            return true;
                        } else if (eligbleDay - llStockTaken.getTakenQty() >= ((useInputUserMinusLL ? entitleLLComming : entitleLLComming / 2) * -1)) {
                            return true;
                        } else {
                            return false;
                        }

                        // }
                    }
                }
            }
        }

        return isLLStockMinus;
    }

    public boolean getLLStockMinus(float eligbleDay) {
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
    public boolean getDPStockMinus(Vector listal, long oidEmployee, DpStockTaken dpStockTaken, float dpTakenPrev) {

        float expiredQTY = SessLeaveManagement.getDpExpired(oidEmployee, null);
        float maxMinus = 0;//Long.parseLong(PstSystemProperty.getValueByName("OID_HRD_DEPARTMENT"));
        try {
            maxMinus = Float.parseFloat(PstSystemProperty.getValueByName("MAX_LEAVE_DP_MINUS"));
            eligibleDayMaxMinus = maxMinus * -1;
        } catch (Exception exc) {
            System.out.println("Exc" + exc);
            eligibleDayMaxMinus = maxMinus;
        }
        if (listal != null && listal.size() > 0) {
            for (int j = 0; j < listal.size(); j++) {
                RepItemLeaveAndDp item = null;
                item = (RepItemLeaveAndDp) listal.get(j);

                //float dpQty = item.getDPQty();
                float curentDpQty = item.getDPQty() - item.getDPTaken();
                float eligbleDay = curentDpQty - item.getDP2BTaken() - expiredQTY - dpStockTaken.getTakenQty() + dpTakenPrev;
                //totalDpTaken = item.getDPTaken();
                //totalDp2BeTaken = item.getDP2BTaken();
                if (eligibleDayMaxMinus == 0 || eligbleDay >= eligibleDayMaxMinus) {
                    //if(eligbleDay >= eligibleDayMaxMinus){
                    isDPStockMinus = true;
                }

            }
        }
        return isDPStockMinus;
    }

    /**
     * @descr Method cek Extra AL create by satrya 2013-08-24
     * @param level
     * @param employeeType
     * @return
     */
    public float getALExtra(String level, String employeeType, int LoSDays, Date commencingDate, Date checkDate) {
        int iEntitleAL = 0;
        try {
            System.out.println("level =" + level);
            System.out.println("type  =" + employeeType);
            int iLevel = getIndexLevel(level);
            int iType = getIndexType(employeeType);
            //iEntitleAL = iALEntitles[iLevel][iType];

            if (commencingDate != null && checkDate != null) {
                Date tmpCheckDate = (Date) checkDate.clone();
                Calendar cald = Calendar.getInstance();
                cald.setTime(tmpCheckDate);
                tmpCheckDate.setDate(cald.getActualMaximum(Calendar.DATE));  // set check date to the end of the month sehingga perhitunganan AL berlaku skope bulan.

                if (LoSDays == 0) {
                    LoSDays = (int) ((tmpCheckDate.getTime() - commencingDate.getTime()) / (1000 * 60 * 60 * 24));
                }
                if (commencingDate.getYear() < tmpCheckDate.getYear()) {
                    // sudah beda tahun berarti akan dapat full                  
                    if (LoSDays >= (getiDayAYear() * iYearsExtraAl) && (commencingDate.getMonth() == tmpCheckDate.getMonth())) { // length of service >= 10 tahun
                        iEntitleAL = iALEntitlesExtra[iLevel][iType];
                    }
                }
//                else {
//                    if (commencingDate.getYear() == checkDate.getYear()) {
//                        if (checkDate.getMonth() >= commencingDate.getMonth()) {
//                            // commencing sudah lewat dibanding check date di tahun yg sama => porposional
//                            iEntitleAL = (int) (((float) ((checkDate.getMonth() - commencingDate.getMonth()) * iEntitleAL)) / 12.0f);
//                        } else {
//                            // tahun sama bulan commencing date lebih baru dari check date = belum dapat cuti, karena belum masuk saat itu
//                            iEntitleAL = 0;
//                        }
//                    } else {
//                        // tahun berbeda berarti commencing date lebih baru dari check date =belum dapat cuti
//                        iEntitleAL = 0;
//                    }
//                }
            }
        } catch (Exception exc) {
            //iEntitleAL = iALEntitles[0][0];
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
    
    public float getALEntitleAnualLeave(String level, String employeeType, int LoSDays, Date commencingDate, Date checkDate) {
        int iEntitleAL = 0;
        try {
            //System.out.println("level =" + level);
            //System.out.println("type  =" + employeeType);
            int iLevel = getIndexLevel(level);
            int iType = getIndexType(employeeType);
            iEntitleAL = iALEntitles[iLevel][iType];

            if (commencingDate != null && checkDate != null) {
                Date tmpCheckDate = (Date) checkDate.clone();
                Calendar cald = Calendar.getInstance();
                cald.setTime(tmpCheckDate);
                tmpCheckDate.setDate(cald.getActualMaximum(Calendar.DATE));  // set check date to the end of the month sehingga perhitunganan AL berlaku skope bulan.

                if (LoSDays == 0) {
                    LoSDays = (int) ((tmpCheckDate.getTime() - commencingDate.getTime()) / (1000 * 60 * 60 * 24));
                }
                if (commencingDate.getYear() < tmpCheckDate.getYear()) {
                    // sudah beda tahun berarti akan dapat full                  
                    if (LoSDays >= (getiDayAYear() * iYearsExtraAl) && (commencingDate.getMonth() == tmpCheckDate.getMonth())) { // length of service >= 10 tahun
                        iEntitleAL = iEntitleAL + iALEntitlesExtra[iLevel][iType];
                    }
                } else {
                    if (commencingDate.getYear() == tmpCheckDate.getYear()) {
                        if (tmpCheckDate.getMonth() >= commencingDate.getMonth()) {
                            // commencing sudah lewat dibanding check date di tahun yg sama => porposional
                            iEntitleAL = (int) (((float) ((tmpCheckDate.getMonth() - commencingDate.getMonth()) * iEntitleAL)) / 12.0f);
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

    /**
     * keterangan: mencari entitle yg encomming entitle create by satrya
     * 2013-09-28
     *
     * @param level
     * @param employeeType
     * @param LoSDays
     * @param commencingDate
     * @param checkDate
     * @return
     */
    public float getCommingALEntitleAnualLeave(String level, String employeeType, int LoSDays, Date commencingDate, Date checkDate) {
        int iEntitleAL = 0;
        try {
            //System.out.println("level =" + level);
            //System.out.println("type  =" + employeeType);
            int iLevel = getIndexLevel(level);
            int iType = getIndexType(employeeType);
            //iEntitleAL = iALEntitles[iLevel][iType];
            iEntitleAL = iALEntitles[iLevel][iType];
            if (commencingDate != null && checkDate != null) {
                Date tmpCheckDate = (Date) checkDate.clone();
                Calendar cald = Calendar.getInstance();
                cald.setTime(tmpCheckDate);
                tmpCheckDate.setDate(cald.getActualMaximum(Calendar.DATE));  // set check date to the end of the month sehingga perhitunganan AL berlaku skope bulan.

                if (LoSDays == 0) {
                    LoSDays = (int) ((tmpCheckDate.getTime() - commencingDate.getTime()) / (1000 * 60 * 60 * 24));
                }

                if (commencingDate.getYear() < tmpCheckDate.getYear()) {
                    // sudah beda tahun berarti akan dapat full    
                    //Date owningLL = new Date(tmpCheckDate.getYear(),commencingDate.getMonth(),commencingDate.getDate());
                    //int diffMonth = DateCalc.monthDifference(tmpCheckDate, owningLL);
                    //iEntitleAL = diffMonth;
                    if (LoSDays >= (getiDayAYear() * iYearsExtraAl) && (tmpCheckDate.getMonth() >= commencingDate.getMonth())/*(commencingDate.getMonth() == tmpCheckDate.getMonth())*/) { // length of service >= 10 tahun
                        iEntitleAL = iEntitleAL + iALEntitlesExtra[iLevel][iType];
                    }
                } else {
                    if (commencingDate.getYear() == tmpCheckDate.getYear()) {
                        if (tmpCheckDate.getMonth() >= commencingDate.getMonth()) {
                            // commencing sudah lewat dibanding check date di tahun yg sama => porposional
                            //Date owningLL = new Date(tmpCheckDate.getYear(),commencingDate.getMonth(),commencingDate.getDate());
                            // int diffMonth = DateCalc.monthDifference(owningLL,tmpCheckDate );
                            //iEntitleAL = diffMonth;
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

    /**
     * create by satrya 20130926
     *
     * @param level
     * @param employeeType
     * @param LoSDays
     * @param commencingDate
     * @param checkDate
     * @return
     */
    public float getChekExtraEntitle(String level, String employeeType, int LoSDays, Date commencingDate, Date checkDate) {
        int iEntitleAL = 0;
        try {
            //System.out.println("level =" + level);
            //System.out.println("type  =" + employeeType);
            int iLevel = getIndexLevel(level);
            int iType = getIndexType(employeeType);
            Date tmpCheckDate = null;
            if (commencingDate != null && checkDate != null) {
                tmpCheckDate = (Date) checkDate.clone();
                Calendar cald = Calendar.getInstance();
                cald.setTime(tmpCheckDate);
                tmpCheckDate.setDate(cald.getActualMaximum(Calendar.DATE));  // set check date to the end of the month sehingga perhitunganan AL berlaku skope bulan.

                if (LoSDays == 0) {
                    LoSDays = (int) ((tmpCheckDate.getTime() - commencingDate.getTime()) / (1000 * 60 * 60 * 24));
                }
                if (commencingDate.getYear() < tmpCheckDate.getYear()) {
                    // sudah beda tahun berarti akan dapat full                  
                    if (LoSDays >= (getiDayAYear() * iYearsExtraAl) && (commencingDate.getMonth() == tmpCheckDate.getMonth())) { // length of service >= 10 tahun
                        iEntitleAL = iALEntitlesExtra[iLevel][iType];
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
     * @descr set configuration for AL number entitling for employee depending
     * on Level and type of employee
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
     * @descr setter method for configuration of AL entetling period the value
     * shall be AL_ENTITLE_BY_COMMENCING or AL_ENTITLE_ANUAL
     * @param al_entitle_by
     */
    public void setALEntitleBy(int al_entitle_by) {
        iEntitleType = al_entitle_by;
    }

    /**
     * @descr getter method for configuration of AL entetling period. the return
     * value will be AL_ENTITLE_BY_COMMENCING or AL_ENTITLE_ANUAL
     * @return
     */
    public int getALEntitleBy() {
        return iEntitleType;
    }

    /**
     * @descr set configuration for expiration days of AL after retreating of
     * entitling date.
     * @param days
     */
    public void setALExpirationDay(int days) {
        iALExpirationDay = days;
    }

    /**
     * @descr get configuration for expiration days of AL after retreating of
     * entitling date.
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
     * @descr Method for getting number of AL during LL entetling depending on
     * employee Level and Employee type
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
     * @descr set configuration for AL number entitling for employee DURING LL
     * ENTETLING depending on Level and type of employee
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
     * @descr set configuration for expiration days of LL after retreating of
     * entitling date.
     * @param days
     */
    public void setLLExpirationDay(int days) {
        iLLExpirationDay = days;
    }

    /**
     * @descr get configuration for expiration days of LL after retreating of
     * entitling date.
     * @return
     */
    public int getLLExpirationDay() {
        return iLLExpirationDay;
    }

    /**
     * @descr set configuration for warning days before LL expiration for
     * employee expires
     * @param days
     */
    public void setLLExpWarning(int days) {
        iLLExpWarning = days;
    }

    /**
     * @descr get configuration for warning days before LL for employee expires
     * @param days
     */
    public int getLLExpWarning() {
        return iLLExpWarning;
    }

    /**
     * @descr menset konfigurasi list dari jangka waktu dan jumlah LL yang
     * diperoleh ( bisa lebih dari satu setting : e.g. : 5 tahun = 26 , 7 tahun
     * = 2 ) tergantung dari Level karyawan.
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
     * @descr get konfigurasi list dari jangka waktu dan jumlah LL yang
     * diperoleh ( bisa lebih dari satu setting : e.g. : 5 tahun = 26 , 7 tahun
     * = 2 ) tergantung dari Level karyawan.
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
     * @descr getDP entitle for a specific employee on a specific date Level E-D
     * : 2 hak DP Level C ke atas sampai A: 1 Public holiday , jatuh pada day
     * off kalau libur ( schedule off ) : dapat pengganti 1 kalau dia di set
     * masuk : dapat pengganti 3 di tambah BirthDay : kalau dia libur : dapat
     * pengganti 2 kalau dia masuk : dapat pengganti 4
     * @param payNum
     * @param date
     * @return
     */
    public int getDPEntitleByDate(String payNum, Date date, LeaveConfigurationParameter leaveConfigurationParameter) {
        int iDPEntitleByDate = 0;
        Employee employee = new Employee();
        employee = PstEmployee.getEmployeeByNum(payNum);

        //Cek kondisi
        boolean boolSchedulePrenseceHoliday = false;//artinya dia hadir di saat sebenarnya dia libur
        boolean boolPresent = false;
        boolean boolPublicHoliday = false;
        //boolean boolBirthDay = false;
        boolean boolPublicHolidayNyepi = false;

        boolSchedulePrenseceHoliday = isScheduleOff(employee, date, leaveConfigurationParameter);
        boolPresent = isPresent(employee, date);
        boolPublicHoliday = isPublicHoliday(employee, date);
        boolPublicHolidayNyepi = isPublicHolidayNyepi(date);
        //boolBirthDay = isBirthDay(employee, date);

        //System.out.println("\nEMPLOYEE STATUS :::::: " + payNum + " at date :" + date);
        // System.out.println("  => ScheduleOff    :" + boolScheduleOff);
        //System.out.println("  => Present        :" + boolPresent);
        //System.out.println("  => PublicHoliday  :" + boolPublicHoliday);
        //System.out.println("  => PublicHolidayNyepi  :" + boolPublicHolidayNyepi);
        //System.out.println("  => BirthDay       :" + boolBirthDay + "\n");
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

        if (boolSchedulePrenseceHoliday) {
            //di karenakan pihak melia mengatakan jika dia tidak masuk n asalkan sudah schedule symbolnya PH maka dia akan dapat DP
            //di confirm oleh pak diana
            //tgl 7 oktober 2013 sekitar jam 2.30 PM
            //if (boolPresent) {
            iDPEntitleByDate = getDPStandardEntitle(strLevel);
            if (boolPublicHoliday) {
                if (boolPublicHolidayNyepi) {
                    iDPEntitleByDate = (Integer) iDPSpecialHolidayEntitle.get(HOLIDAY_NYEPI);
                }
                /*else{
                 iDPEntitleByDate += iDPHolidayPresentEntitle[getIndexLevel(strLevel)];
                 }*/
            }
            /*if (boolBirthDay) {
             iDPEntitleByDate += iDPBirthdayPresentEntitle[getIndexLevel(strLevel)];
             }*/
            //} 
            //mencari hanya jika dia absennya tidak cacat
            /*else {
             if (boolPublicHoliday) {
             if(boolPublicHolidayNyepi){
             iDPEntitleByDate +=(Integer)iDPSpecialHolidayEntitle.get(HOLIDAY_NYEPI);
             }else{
             iDPEntitleByDate += iDPHolidayPresentEntitle[getIndexLevel(strLevel)];
             }
                    
             }
             /*if (boolBirthDay) {
             iDPEntitleByDate += iDPBirthdayOffEntitle[getIndexLevel(strLevel)];
             }*/
            //}*/
        } else {
            if (boolPresent) {
                if (boolPublicHoliday) {
                    if (boolPublicHolidayNyepi) {
                        iDPEntitleByDate += (Integer) iDPSpecialHolidayEntitle.get(HOLIDAY_NYEPI);
                    } else {
                        iDPEntitleByDate += iDPHolidayPresentEntitle[getIndexLevel(strLevel)];
                    }
                }
            }
            /*else {
             //Belum didefinisikan
             //if (boolPublicHoliday) {
             //    iDPEntitleByDate += iDPHolidayOffEntitle[getIndexLevel(strLevel)] ;;
             //}
             if (boolBirthDay) {
             iDPEntitleByDate += iDPBirthdayOffEntitle[getIndexLevel(strLevel)];
             }
             }*/
        }

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
     * @desc mengecek apakah employee DO atau tidak, jika DO maka akan diset
     * True jika tidak maka di set false
     * @param employee Employee
     * @param date Date
     * @return boolean
     */
    public boolean isScheduleOff(Employee employee, Date date, LeaveConfigurationParameter leaveConfigurationParameter) {
        boolean status = false;

        //Cek apakah employee ada jadwal pada tgl tersebut?
        ScheduleSymbol scheduleSymbol = new ScheduleSymbol();
        long lScheduleSymbolId = 0;
        lScheduleSymbolId = SessEmpSchedule.getScheduleId(employee.getOID(), date);

        try {
            if (lScheduleSymbolId != 0) {
                scheduleSymbol = (ScheduleSymbol) PstScheduleSymbol.fetchExc(lScheduleSymbolId);
            }
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
            //update by satrya 2013-08-2013 leaveConfigurationParameter
            //if (scheduleCategory.getCategoryType() == PstScheduleCategory.CATEGORY_OFF) {
            if (scheduleCategory.getCategoryType() == PstScheduleCategory.CATEGORY_OFF
                    && leaveConfigurationParameter.getPresenceHoliday() != 0 && lScheduleSymbolId != 0
                    && leaveConfigurationParameter.getPresenceHoliday() == lScheduleSymbolId) {
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
        Date dActualPresentIn = null;
        Date dActualPresentOut = null;

        DBResultSet dbrs = null;
        long periodId = PstPeriod.getPeriodIdBySelectedDate(date);
        try {
            String sFieldIn = "IN" + date.getDate();
            String sFieldOut = "OUT" + date.getDate();

            String sql = " SELECT " + sFieldIn + "," + sFieldOut + " FROM " + PstEmpSchedule.TBL_HR_EMP_SCHEDULE + " WHERE " + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID] + "=" + employee.getOID() + " AND " + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_PERIOD_ID] + "=" + periodId;
            //System.out.println("SQL Mencari  jam masuk dan keluar :::::: " + sql);
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
        //System.out.println("::::::::::: IN  :::::" + dActualPresentIn);
        //System.out.println("::::::::::: OUT :::::" + dActualPresentOut);

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

        //System.out.println("::::::::::: INTERVAL :::::" + intervalDate);
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
     * create by satrya 2013-07-01 mencari hari raya nyepi
     *
     * @param date
     * @return
     */
    public boolean isPublicHolidayNyepi(Date date) {
        boolean status = false;
        //Apakah tgl tersebut merupakan hari libur dan ada hari raya nyepi?
        Vector vPublicHolidays = new Vector();
        vPublicHolidays = PstPublicHolidays.getHolidayByDateAndNameNyepi(date);
        if (vPublicHolidays != null && vPublicHolidays.size() > 0) {
            status = true;
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

    /**
     * ;
     *
     * @descr get konfigurasi list dari jangka waktu validity LL ( misal yang
     * didapat setiap 5 tahun valid 4 tahun)tergantung dari Level karyawan.
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
     * @descr menset konfigurasi list dari jangka waktu dan jumlah LL yang
     * diperoleh ( bisa lebih dari satu setting : e.g. : 5 tahun = 26 , 7 tahun
     * = 2 ) tergantung dari Level karyawan.
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
     * @desc menambil level employee per id employee - level yang dimaksud
     * adalah level kerja/type
     * @param employee id
     * @return string dari level
     */
    public String getLevel(long employeeId) {
        Level objLevel = new Level();
        try {
            Employee objEmployee = new Employee();
            if (employeeId != 0) {
                objEmployee = PstEmployee.fetchExc(employeeId);
                objLevel = PstLevel.fetchExc(objEmployee.getLevelId());
                return objLevel.getLevel();
            }
            return "";

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
     *
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
     * create by satrya 2014-04-15
     *
     * @param positionId
     * @param list
     * @return
     */
    private boolean SamePosition(long positionId, String[] list) {
        if (positionId == 0 || list == null || list.length < 1) {
            return false;
        }

        for (int i = 0; i < list.length; i++) {
            String posId = list[i];
            if (String.valueOf(positionId).equalsIgnoreCase(posId)) {
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
        Vector listEmployee = new Vector();
        String whereClause  = "HE." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + " = "+employeeId+" AND HMP."+ PstTopPosition.fieldNames[PstTopPosition.FLD_TYPE_OF_LINK] + " = "+typeOfLink ;
        String listTopLink = PstTopPosition.listEmployeeTopPositionId(0, 0, whereClause, "");
        if (listTopLink.length() > 0 ){
           listTopLink = listTopLink.substring(0, (listTopLink.length() - 1 ));
        }
        Employee employee = new Employee();
        try {
            employee = PstEmployee.fetchExc(employeeId);
        } catch (Exception e) {
            
        }
        Vector listEmployeeSectionTopLink = PstEmployee.listEmployeeApprovalTopLink(0, 0, " POSITION_ID IN ("+listTopLink+")" , "", employee.getSectionId(), employee.getDepartmentId() , employee.getDivisionId());
        Vector listEmployeeDepartmentTopLink = PstEmployee.listEmployeeApprovalTopLink(0, 0, " POSITION_ID IN ("+listTopLink+")" , "", 0 ,employee.getDepartmentId(), employee.getDivisionId());
        Vector listEmployeeDivisionTopLink = PstEmployee.listEmployeeApprovalTopLink(0, 0, " POSITION_ID IN ("+listTopLink+")" , "", 0,0, 0);
        
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
    public Vector getApprovalDepartmentHead_old(long employeeId) {
        Employee employee = new Employee();
        Section section = new Section();
        Department department = new Department();
        Division division = new Division();
        Position position = new Position();
        String sOidApprovallReplaceGM = "";
        String sOidApprovallReplaceHotelManager = "";
        //String sOidApprovalHotelManager = "";
        String sOIdApprovalEmployeeKhusus = "";
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

        Vector listApproveGM = new Vector();

        Vector listApproveByHotelManager = new Vector();

        //Vector listEmployeeKhususApproveByHotellManager = new Vector();

        //listEmployee = listSectionApproval(department.getOID(), section.getOID(), employee.getOID());
        if (position != null) {
            Employee emGM = new Employee();
            //Employee emHM = new Employee();
            Employee emHRDir = new Employee();
            //Employee emHRMan = new Employee();
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

            } catch (Exception E) {
                emGM = new Employee();
                emGM.setFullName("HR Director not set in System Property : HR_DIRECTOR");
            }

            boolean HOD = false;
            boolean DivHead = false;
            if (position.getPositionLevel() >= PstPosition.LEVEL_GENERAL && position.getPositionLevel() <= PstPosition.LEVEL_ASST_MANAGER) { // staff to manager
                if (position.getPositionLevel() > PstPosition.LEVEL_ASST_MANAGER && position.getDisabledAppDeptScope() == 0) { // Ast. Manager as HOD
                    listEmpDivision = listDivisionApproval(division.getOID(), employee.getOID());
                    if (listEmpDivision != null && listEmpDivision.size() > 0) {
                        for (int i = 0; i < listEmpDivision.size(); i++) {
                            Employee objEmployee = new Employee();
                            objEmployee = (Employee) listEmpDivision.get(i);
                            if (!existEmployee(objEmployee.getOID(), listEmployee)) {
                                if (objEmployee.getOID() != 0) {
                                    listEmployee.add(objEmployee);
                                }
                            };
                        }
                        DivHead = true;
                    }
                    if (!existEmployee(emHRDir.getOID(), listEmployee)) {
                        if (emHRDir.getOID() != 0) {
                            listEmployee.add(emHRDir);
                        }
                    }
                    if (!existEmployee(emGM.getOID(), listEmployee)) {
                        if (emGM.getOID() != 0) {
                            listEmployee.add(emGM);
                        }
                    }
                } else {

                    if (position.getPositionLevel() == PstPosition.LEVEL_SECRETARY) {

                        try {
                            sOIdApprovalEmployeeKhusus = (PstSystemProperty.getValueByName("APPROVALL_EMPLOYEE_KHUSUS"));

                        } catch (Exception e) {
                            System.out.println("EXCEPTION " + e.toString());
                        }
                        //list karywan yg akan di approve oleh GM

                        if (sOIdApprovalEmployeeKhusus != null && !sOIdApprovalEmployeeKhusus.equalsIgnoreCase(PstSystemProperty.SYS_NOT_INITIALIZED)) {
                            Hashtable hashLevelProps = com.dimata.util.StringParser.parseGroupVer2(sOIdApprovalEmployeeKhusus);
                            if (employee != null && hashLevelProps != null) {
                                String positionId = (String) hashLevelProps.get("" + employee.getPositionId());
                                if (positionId != null && positionId.length() > 0) {
                                    listEmpDepartment = listApprovalKhusus(positionId, employee);
                                    if (listEmpDepartment != null && listEmpDepartment.size() > 0) {
                                        for (int i = 0; i < listEmpDepartment.size(); i++) {
                                            Employee objEmployee = new Employee();
                                            objEmployee = (Employee) listEmpDepartment.get(i);
                                            if (!existEmployee(objEmployee.getOID(), listEmployee)) {
                                                if (objEmployee.getOID() != 0) {
                                                    listEmployee.add(objEmployee);
                                                }
                                            };
                                        }
                                        DivHead = true;
                                    }
                                } else {
                                    listEmpDepartment = listDepartmentApproval(department.getOID(), employee, PstPosition.LEVEL_MANAGER, PstPosition.LEVEL_MANAGER);
                                    if (listEmpDepartment != null && listEmpDepartment.size() > 0) {
                                        for (int i = 0; i < listEmpDepartment.size(); i++) {
                                            Employee objEmployee = new Employee();
                                            objEmployee = (Employee) listEmpDepartment.get(i);
                                            if (!existEmployee(objEmployee.getOID(), listEmployee)) {
                                                if (objEmployee.getOID() != 0) {
                                                    listEmployee.add(objEmployee);
                                                }
                                            };
                                        }
                                        HOD = true;
                                    }

                                }

                            } else {
                                listEmpDepartment = listDepartmentApproval(department.getOID(), employee, PstPosition.LEVEL_MANAGER, PstPosition.LEVEL_MANAGER);
                                if (listEmpDepartment != null && listEmpDepartment.size() > 0) {
                                    for (int i = 0; i < listEmpDepartment.size(); i++) {
                                        Employee objEmployee = new Employee();
                                        objEmployee = (Employee) listEmpDepartment.get(i);
                                        if (!existEmployee(objEmployee.getOID(), listEmployee)) {
                                            if (objEmployee.getOID() != 0) {
                                                listEmployee.add(objEmployee);
                                            }
                                        };
                                    }
                                    HOD = true;
                                }
                            }
                        } else {
                            listEmpDepartment = listDepartmentApproval(department.getOID(), employee, PstPosition.LEVEL_MANAGER, PstPosition.LEVEL_MANAGER);
                            if (listEmpDepartment != null && listEmpDepartment.size() > 0) {
                                for (int i = 0; i < listEmpDepartment.size(); i++) {
                                    Employee objEmployee = new Employee();
                                    objEmployee = (Employee) listEmpDepartment.get(i);
                                    if (!existEmployee(objEmployee.getOID(), listEmployee)) {
                                        if (objEmployee.getOID() != 0) {
                                            listEmployee.add(objEmployee);
                                        }
                                    };
                                }
                                HOD = true;
                            }

                        }


                    } else {
                        // staff to ast. manager
                        listEmpDepartment = listDepartmentApproval(department.getOID(), employee, PstPosition.LEVEL_MANAGER, PstPosition.LEVEL_DIRECTOR);
                        //listEmpDepartment = listDepartmentApproval(department.getOID(), employee, PstPosition.LEVEL_ASST_MANAGER, PstPosition.LEVEL_MANAGER);
                        listEmpDivision = listDivisionApproval(division.getOID(), employee.getOID());
                        if (listEmpDepartment != null && listEmpDepartment.size() > 0) {
                            for (int i = 0; i < listEmpDepartment.size(); i++) {
                                Employee objEmployee = new Employee();
                                objEmployee = (Employee) listEmpDepartment.get(i);
                                if (!existEmployee(objEmployee.getOID(), listEmployee)) {
                                    if (objEmployee.getOID() != 0) {
                                        listEmployee.add(objEmployee);
                                    }
                                };
                            }
                            HOD = true;
                        }

                        if (listEmpDivision != null && listEmpDivision.size() > 0) {
                            for (int i = 0; i < listEmpDivision.size(); i++) {
                                Employee objEmployee = new Employee();
                                objEmployee = (Employee) listEmpDivision.get(i);
                                if (!existEmployee(objEmployee.getOID(), listEmployee)) {
                                    //update by satrya 2013-07-05
                                    if (objEmployee.getOID() != 0) {
                                        listEmployee.add(objEmployee);
                                    }
                                };
                            }
                            DivHead = true;
                        }

//                    if ((HOD && !DivHead) || (!HOD && DivHead)) { // HOD found but not Div Head
//                        if(!existEmployee(emHRMan.getOID(), listEmployee)){
//                         //update by satrya 2013-07-05
//                           if(emHRMan.getOID()!=0){
//                            listEmployee.add(emHRMan);
//                           }
//                        }
//                        if(!existEmployee(emHRDir.getOID(), listEmployee)){
//                          //update by satrya 2013-07-05
//                           if(emHRDir.getOID()!=0){
//                            listEmployee.add(emHRDir);
//                           }
//                           //listEmployee.add(emHRDir);
//                        }
//                    }
//
//                    if (!HOD && !DivHead) {
//                        if(!existEmployee(emHRMan.getOID(), listEmployee)){
//                            //update by satrya 2013-07-05
//                            if(emHRMan.getOID()!=0){
//                                listEmployee.add(emHRMan);
//                            }
//                            //  listEmployee.add(emHRMan);
//                        }
//                        if(!existEmployee(emHRDir.getOID(), listEmployee)){
//                            if(emHRDir.getOID()!=0){
//                                listEmployee.add(emHRDir);
//                            }
//                        }
//                        if(!existEmployee(emGM.getOID(), listEmployee)){
//                           if(emGM.getOID()!=0){
//                            listEmployee.add(emGM);
//                           }
//                        }                        
//                    }
                    }
                }
            } else {
                if (position.getPositionLevel() == PstPosition.LEVEL_MANAGER) {
                    if (position.getDisabedAppDivisionScope() == 0 && position.getDisabledAppDeptScope() == 0) {
                        listEmpDivision = listDivisionApproval(division.getOID(), employee.getOID());
                        if (listEmpDivision != null && listEmpDivision.size() > 0) {
                            if (listEmpDivision != null && listEmpDivision.size() > 0) {
                                for (int i = 0; i < listEmpDivision.size(); i++) {
                                    Employee objEmployee = new Employee();
                                    objEmployee = (Employee) listEmpDivision.get(i);
                                    if (!existEmployee(objEmployee.getOID(), listEmployee)) {
                                        //update by satrya 2013-07-05
                                        if (objEmployee.getOID() != 0) {
                                            listEmployee.add(objEmployee);
                                        }
                                    };
                                }
                                DivHead = true;
                            }
                            if (listEmployee == null || listEmployee.size() == 0) {
                                try {
                                    sOIdApprovalEmployeeKhusus = (PstSystemProperty.getValueByName("APPROVALL_EMPLOYEE_KHUSUS"));

                                } catch (Exception e) {
                                    System.out.println("EXCEPTION " + e.toString());
                                }
                                //list karywan yg akan di approve oleh GM

                                if (sOIdApprovalEmployeeKhusus != null && !sOIdApprovalEmployeeKhusus.equalsIgnoreCase(PstSystemProperty.SYS_NOT_INITIALIZED)) {
                                    Hashtable hashLevelProps = com.dimata.util.StringParser.parseGroupVer2(sOIdApprovalEmployeeKhusus);
                                    if (employee != null && hashLevelProps != null) {
                                        String positionId = (String) hashLevelProps.get("" + employee.getPositionId());
                                        if (positionId != null && positionId.length() > 0) {
                                            listEmpDepartment = listApprovalKhusus(positionId, employee);
                                            if (listEmpDepartment != null && listEmpDepartment.size() > 0) {
                                                for (int i = 0; i < listEmpDepartment.size(); i++) {
                                                    Employee objEmployee = new Employee();
                                                    objEmployee = (Employee) listEmpDepartment.get(i);
                                                    if (!existEmployee(objEmployee.getOID(), listEmployee)) {
                                                        if (objEmployee.getOID() != 0) {
                                                            listEmployee.add(objEmployee);
                                                        }
                                                    };
                                                }
                                                DivHead = true;
                                            }
                                        }

                                    }
                                }
                            }
                        } else {
                            listEmpDepartment = listDepartmentApproval(department.getOID(), employee, PstPosition.LEVEL_DIRECTOR, PstPosition.LEVEL_DIRECTOR);
                            if (listEmpDepartment != null && listEmpDepartment.size() > 0) {
                                if (listEmpDepartment != null && listEmpDepartment.size() > 0) {
                                    for (int i = 0; i < listEmpDepartment.size(); i++) {
                                        Employee objEmployee = new Employee();
                                        objEmployee = (Employee) listEmpDepartment.get(i);
                                        if (!existEmployee(objEmployee.getOID(), listEmployee)) {
                                            if (objEmployee.getOID() != 0) {
                                                listEmployee.add(objEmployee);
                                            }
                                        };
                                    }
                                    DivHead = true;
                                }
                            }

                            if (listEmployee == null || listEmployee.size() == 0) {
                                try {
                                    sOIdApprovalEmployeeKhusus = (PstSystemProperty.getValueByName("APPROVALL_EMPLOYEE_KHUSUS"));

                                } catch (Exception e) {
                                    System.out.println("EXCEPTION " + e.toString());
                                }
                                //list karywan yg akan di approve oleh GM

                                if (sOIdApprovalEmployeeKhusus != null && !sOIdApprovalEmployeeKhusus.equalsIgnoreCase(PstSystemProperty.SYS_NOT_INITIALIZED)) {
                                    Hashtable hashLevelProps = com.dimata.util.StringParser.parseGroupVer2(sOIdApprovalEmployeeKhusus);
                                    if (employee != null && hashLevelProps != null) {
                                        String positionId = (String) hashLevelProps.get("" + employee.getPositionId());
                                        if (positionId != null && positionId.length() > 0) {
                                            listEmpDepartment = listApprovalKhusus(positionId, employee);
                                            if (listEmpDepartment != null && listEmpDepartment.size() > 0) {
                                                for (int i = 0; i < listEmpDepartment.size(); i++) {
                                                    Employee objEmployee = new Employee();
                                                    objEmployee = (Employee) listEmpDepartment.get(i);
                                                    if (!existEmployee(objEmployee.getOID(), listEmployee)) {
                                                        if (objEmployee.getOID() != 0) {
                                                            listEmployee.add(objEmployee);
                                                        }
                                                    };
                                                }
                                                DivHead = true;
                                            }
                                        }

                                    }
                                }
                            }
                        }
                    } else if (position.getDisabledAppDeptScope() == 0) { // antar manager
                        try {
                            sOIdApprovalEmployeeKhusus = (PstSystemProperty.getValueByName("APPROVALL_EMPLOYEE_KHUSUS"));

                        } catch (Exception e) {
                            System.out.println("EXCEPTION " + e.toString());
                        }
                        //list karywan yg akan di approve oleh GM

                        if (sOIdApprovalEmployeeKhusus != null && !sOIdApprovalEmployeeKhusus.equalsIgnoreCase(PstSystemProperty.SYS_NOT_INITIALIZED)) {
                            Hashtable hashLevelProps = com.dimata.util.StringParser.parseGroupVer2(sOIdApprovalEmployeeKhusus);
                            if (employee != null && hashLevelProps != null) {
                                String positionId = (String) hashLevelProps.get("" + employee.getPositionId());
                                if (positionId != null && positionId.length() > 0) {
                                    listEmpDepartment = listApprovalKhusus(positionId, employee);
                                    if (listEmpDepartment != null && listEmpDepartment.size() > 0) {
                                        for (int i = 0; i < listEmpDepartment.size(); i++) {
                                            Employee objEmployee = new Employee();
                                            objEmployee = (Employee) listEmpDepartment.get(i);
                                            if (!existEmployee(objEmployee.getOID(), listEmployee)) {
                                                if (objEmployee.getOID() != 0) {
                                                    listEmployee.add(objEmployee);
                                                }
                                            };
                                        }
                                        DivHead = true;
                                    }
                                }

                            }
                        }

                        if (listEmployee == null || listEmployee.size() == 0) {
                            listEmpDepartment = listDepartmentApproval(department.getOID(), employee, PstPosition.LEVEL_DIRECTOR, PstPosition.LEVEL_DIRECTOR);
                            // listEmpDivision = listDivisionApproval(division.getOID(), employee.getOID());
                            if (listEmpDepartment != null && listEmpDepartment.size() > 0) {
                                for (int i = 0; i < listEmpDepartment.size(); i++) {
                                    Employee objEmployee = new Employee();
                                    objEmployee = (Employee) listEmpDepartment.get(i);
                                    if (!existEmployee(objEmployee.getOID(), listEmployee)) {
                                        if (objEmployee.getOID() != 0) {
                                            listEmployee.add(objEmployee);
                                        }
                                    };
                                }
                                HOD = true;
                            }

                        }
//                      if(position.getDisabedAppDivisionScope()==0){
//                         // executiv house keeping
//                          x
//                        if (listEmpDivision != null && listEmpDivision.size() > 0) {
//                            for (int i = 0; i < listEmpDivision.size(); i++) {
//                                Employee objEmployee = new Employee();
//                                objEmployee = (Employee) listEmpDivision.get(i);
//                                if (!existEmployee(objEmployee.getOID(), listEmployee)) {
//                                    if (objEmployee.getOID() != 0) {
//                                        listEmployee.add(objEmployee);
//                                    }
//                                };
//                            }
//                            DivHead = true;
//                        }
//                      }


                        //update by satrya 2013-12-09
                        /**
                         * create by satrya 2013-12-09
                         */
                        try {
                            sOidApprovallReplaceHotelManager = (PstSystemProperty.getValueByName("REPLACE_HOTEL_MANAGER_APPROVALL"));
                            //String sOidEmployeeKhususApproveByHotelManager = (PstSystemProperty.getValueByName("EMPLOYEE_WHO_APPROVE_BY_HOTEL_MANAGER"));
                            if (sOidApprovallReplaceHotelManager != null && !sOidApprovallReplaceHotelManager.equalsIgnoreCase(PstSystemProperty.SYS_NOT_INITIALIZED)) {
                                listApproveByHotelManager = listDepHeadApproveGMorHotelManager(sOidApprovallReplaceHotelManager);
                            }
//                            if (sOidEmployeeKhususApproveByHotelManager != null && !sOidEmployeeKhususApproveByHotelManager.equalsIgnoreCase(PstSystemProperty.SYS_NOT_INITIALIZED)) {
//                                listEmployeeKhususApproveByHotellManager = listEmployeeKhususYgApproveByHotelManager(sOidEmployeeKhususApproveByHotelManager);
//                            }
                        } catch (Exception e) {
                            System.out.println("EXCEPTION " + e.toString());
                        }

                        if (listApproveByHotelManager != null && listApproveByHotelManager.size() > 0) {
                            for (int i = 0; i < listApproveByHotelManager.size(); i++) {
                                Employee objEmployee = new Employee();
                                objEmployee = (Employee) listApproveByHotelManager.get(i);
                                if (!existEmployee(objEmployee.getOID(), listEmployee)) {
                                    if (existEmployee(employeeId, listApproveByHotelManager)) {
                                        if (objEmployee.getOID() != 0) {
                                            objEmployee.setFullName(objEmployee.getFullName() + " for Hotel Manager");
                                            listEmployee.add(objEmployee);
                                        }
                                    }
                                };
                            }
                            DivHead = true;
                        }

//                        if (!HOD) {
//                        if(!existEmployee(emHRDir.getOID(), listEmployee)){
//                           if(emHRDir.getOID()!=0){
//                            listEmployee.add(emHRDir);
//                           }
//                        }
//                        if(!existEmployee(emGM.getOID(), listEmployee)){
//                           if(emGM.getOID()!=0){
//                            listEmployee.add(emGM);
//                           }
//                        }                        
//                        }
                    } else {
                        // HOD manager
                        //update by satrya 2014-04-29
                        try {
                            sOIdApprovalEmployeeKhusus = (PstSystemProperty.getValueByName("APPROVALL_EMPLOYEE_KHUSUS"));

                        } catch (Exception e) {
                            System.out.println("EXCEPTION " + e.toString());
                        }
                        //list karywan yg akan di approve oleh GM

                        if (sOIdApprovalEmployeeKhusus != null && !sOIdApprovalEmployeeKhusus.equalsIgnoreCase(PstSystemProperty.SYS_NOT_INITIALIZED)) {
                            Hashtable hashLevelProps = com.dimata.util.StringParser.parseGroupVer2(sOIdApprovalEmployeeKhusus);
                            if (employee != null && hashLevelProps != null) {
                                String positionId = (String) hashLevelProps.get("" + employee.getPositionId());
                                if (positionId != null && positionId.length() > 0) {
                                    listEmpDepartment = listApprovalKhusus(positionId, employee);
                                    if (listEmpDepartment != null && listEmpDepartment.size() > 0) {
                                        for (int i = 0; i < listEmpDepartment.size(); i++) {
                                            Employee objEmployee = new Employee();
                                            objEmployee = (Employee) listEmpDepartment.get(i);
                                            if (!existEmployee(objEmployee.getOID(), listEmployee)) {
                                                if (objEmployee.getOID() != 0) {
                                                    listEmployee.add(objEmployee);
                                                }
                                            };
                                        }
                                        DivHead = true;
                                    }
                                }

                            }
                        }

                        if (listEmployee == null || listEmployee.size() == 0) {
                            listEmpDivision = listDivisionApproval(division.getOID(), employee.getOID());
                            if (listEmpDivision != null && listEmpDivision.size() > 0) {
                                for (int i = 0; i < listEmpDivision.size(); i++) {
                                    Employee objEmployee = new Employee();
                                    objEmployee = (Employee) listEmpDivision.get(i);
                                    if (!existEmployee(objEmployee.getOID(), listEmployee)) {
                                        if (objEmployee.getOID() != 0) {
                                            listEmployee.add(objEmployee);
                                        }
                                    };
                                }
                                DivHead = true;
                            }
                            //update by satrya 2014-06-03
                            //karena denen tidak bisa di approve oleh pak wirata
                            if (listEmployee == null || listEmployee.size() == 0) {
                                listEmpDepartment = listDepartmentApproval(department.getOID(), employee, PstPosition.LEVEL_DIRECTOR, PstPosition.LEVEL_DIRECTOR);
                                // listEmpDivision = listDivisionApproval(division.getOID(), employee.getOID());
                                if (listEmpDepartment != null && listEmpDepartment.size() > 0) {
                                    for (int i = 0; i < listEmpDepartment.size(); i++) {
                                        Employee objEmployee = new Employee();
                                        objEmployee = (Employee) listEmpDepartment.get(i);
                                        if (!existEmployee(objEmployee.getOID(), listEmployee)) {
                                            if (objEmployee.getOID() != 0) {
                                                listEmployee.add(objEmployee);
                                            }
                                        };
                                    }
                                    HOD = true;
                                }

                            }
                        }


                        //update by satrya 2013-12-09
                        /**
                         * create by satrya 2013-12-09
                         */
                        try {
                            sOidApprovallReplaceHotelManager = (PstSystemProperty.getValueByName("REPLACE_HOTEL_MANAGER_APPROVALL"));
                            //String sOidEmployeeKhususApproveByHotelManager = (PstSystemProperty.getValueByName("EMPLOYEE_WHO_APPROVE_BY_HOTEL_MANAGER"));
                            if (sOidApprovallReplaceHotelManager != null && !sOidApprovallReplaceHotelManager.equalsIgnoreCase(PstSystemProperty.SYS_NOT_INITIALIZED)) {
                                listApproveByHotelManager = listDepHeadApproveGMorHotelManager(sOidApprovallReplaceHotelManager);
                            }
//                            if (sOidEmployeeKhususApproveByHotelManager != null && !sOidEmployeeKhususApproveByHotelManager.equalsIgnoreCase(PstSystemProperty.SYS_NOT_INITIALIZED)) {
//                                listEmployeeKhususApproveByHotellManager = listEmployeeKhususYgApproveByHotelManager(sOidEmployeeKhususApproveByHotelManager);
//                            }
                        } catch (Exception e) {
                            System.out.println("EXCEPTION " + e.toString());
                        }

                        if (listApproveByHotelManager != null && listApproveByHotelManager.size() > 0) {
                            for (int i = 0; i < listApproveByHotelManager.size(); i++) {
                                Employee objEmployee = new Employee();
                                objEmployee = (Employee) listApproveByHotelManager.get(i);
                                if (!existEmployee(objEmployee.getOID(), listEmployee)) {
                                    if (existEmployee(employeeId, listApproveByHotelManager)) {
                                        if (objEmployee.getOID() != 0) {
                                            objEmployee.setFullName(objEmployee.getFullName() + " for Hotel Manager");
                                            listEmployee.add(objEmployee);
                                        }
                                    }
                                };
                            }
                            DivHead = true;
                        }
                        if (listEmployee == null || listEmployee.size() == 0) {
                            if (!existEmployee(emHRDir.getOID(), listEmployee)) {
                                if (emHRDir.getOID() != 0) {
                                    listEmployee.add(emHRDir);
                                }
                            }
                            if (!existEmployee(emGM.getOID(), listEmployee)) {
                                if (emGM.getOID() != 0) {
                                    listEmployee.add(emGM);
                                }
                            }
                        }

                    }
                } else {
                    if (position.getPositionLevel() == PstPosition.LEVEL_ASST_DIRECTOR) {
                        if (position.getDisabledAppDeptScope() == 0) { // antar manager
                            listEmpDepartment = listDepartmentApproval(department.getOID(), employee, PstPosition.LEVEL_DIRECTOR, PstPosition.LEVEL_DIRECTOR);
                            //listEmpDivision = listDivisionApproval(division.getOID(), employee.getOID());
                            if (listEmpDepartment != null && listEmpDepartment.size() > 0) {
                                for (int i = 0; i < listEmpDepartment.size(); i++) {
                                    Employee objEmployee = new Employee();
                                    objEmployee = (Employee) listEmpDepartment.get(i);
                                    if (!existEmployee(objEmployee.getOID(), listEmployee)) {
                                        if (objEmployee.getOID() != 0) {
                                            listEmployee.add(objEmployee);
                                        }
                                    };
                                }
                                HOD = true;
                            }

                            if (listEmpDivision != null && listEmpDivision.size() > 0) {
                                for (int i = 0; i < listEmpDivision.size(); i++) {
                                    Employee objEmployee = new Employee();
                                    objEmployee = (Employee) listEmpDivision.get(i);
                                    if (!existEmployee(objEmployee.getOID(), listEmployee)) {
                                        if (objEmployee.getOID() != 0) {
                                            listEmployee.add(objEmployee);
                                        }
                                    };
                                }
                                DivHead = true;
                            }

//                        if (!HOD) {
//                        if(!existEmployee(emHRDir.getOID(), listEmployee)){
//                           if(emHRDir.getOID()!=0){
//                            listEmployee.add(emHRDir);
//                           }
//                        }
//                        if(!existEmployee(emGM.getOID(), listEmployee)){
//                           if(emGM.getOID()!=0){
//                            listEmployee.add(emGM);
//                           }
//                        }                        
//                      }
                        } else { // level ast director non div head 
                            listEmpDivision = listDivisionApproval(division.getOID(), employee.getOID());
                            if (listEmpDivision != null && listEmpDivision.size() > 0) {
                                for (int i = 0; i < listEmpDivision.size(); i++) {
                                    Employee objEmployee = new Employee();
                                    objEmployee = (Employee) listEmpDivision.get(i);
                                    if (!existEmployee(objEmployee.getOID(), listEmployee)) {
                                        if (objEmployee.getOID() != 0) {
                                            listEmployee.add(objEmployee);
                                        }
                                    };
                                }
                                DivHead = true;
                            }
                            if (!existEmployee(emHRDir.getOID(), listEmployee)) {
                                if (emHRDir.getOID() != 0) {
                                    listEmployee.add(emHRDir);
                                }
                            }
                            if (!existEmployee(emGM.getOID(), listEmployee)) {
                                if (emGM.getOID() != 0) {
                                    listEmployee.add(emGM);
                                }
                            }
                        }
                    } else {
                        if (position.getPositionLevel() == PstPosition.LEVEL_DIRECTOR) {
                            if (position.getDisabledAppDeptScope() == 0) { // antar manager
                                listEmpDepartment = listDepartmentApproval(department.getOID(), employee, PstPosition.LEVEL_GENERAL_MANAGER, PstPosition.LEVEL_GENERAL_MANAGER);
                                //listEmpDivision = listDivisionApproval(division.getOID(), employee.getOID());
                                if (listEmpDepartment != null && listEmpDepartment.size() > 0) {
                                    for (int i = 0; i < listEmpDepartment.size(); i++) {
                                        Employee objEmployee = new Employee();
                                        objEmployee = (Employee) listEmpDepartment.get(i);
                                        if (!existEmployee(objEmployee.getOID(), listEmployee)) {
                                            if (objEmployee.getOID() != 0) {
                                                listEmployee.add(objEmployee);
                                            }
                                        };
                                    }
                                    HOD = true;
                                }


                                if (listEmployee == null || listEmployee.size() == 0) {

                                    try {
                                        sOIdApprovalEmployeeKhusus = (PstSystemProperty.getValueByName("APPROVALL_EMPLOYEE_KHUSUS"));

                                    } catch (Exception e) {
                                        System.out.println("EXCEPTION " + e.toString());
                                    }
                                    //list karywan yg akan di approve oleh GM

                                    if (sOIdApprovalEmployeeKhusus != null && !sOIdApprovalEmployeeKhusus.equalsIgnoreCase(PstSystemProperty.SYS_NOT_INITIALIZED)) {
                                        Hashtable hashLevelProps = com.dimata.util.StringParser.parseGroupVer2(sOIdApprovalEmployeeKhusus);
                                        if (employee != null && hashLevelProps != null) {
                                            String positionId = (String) hashLevelProps.get("" + employee.getPositionId());
                                            if (positionId != null && positionId.length() > 0) {
                                                listEmpDepartment = listApprovalKhusus(positionId, employee);
                                                if (listEmpDepartment != null && listEmpDepartment.size() > 0) {
                                                    for (int i = 0; i < listEmpDepartment.size(); i++) {
                                                        Employee objEmployee = new Employee();
                                                        objEmployee = (Employee) listEmpDepartment.get(i);
                                                        if (!existEmployee(objEmployee.getOID(), listEmployee)) {
                                                            if (objEmployee.getOID() != 0) {
                                                                listEmployee.add(objEmployee);
                                                            }
                                                        };
                                                    }
                                                    DivHead = true;
                                                }
                                            }

                                        }
                                    }
                                }

                                //update by satrya 2013-12-09
                                /**
                                 * create by satrya 2013-12-09
                                 */
                                if (DivHead == false) {
                                    try {
                                        sOidApprovallReplaceGM = (PstSystemProperty.getValueByName("REPLACE_GM_APPROVALL"));
                                        if (sOidApprovallReplaceGM != null && !sOidApprovallReplaceGM.equalsIgnoreCase(PstSystemProperty.SYS_NOT_INITIALIZED)) {
                                            listApproveGM = listDepHeadApproveGMorHotelManager(sOidApprovallReplaceGM);
                                        }
                                    } catch (Exception e) {
                                        System.out.println("EXCEPTION " + e.toString());
                                    }

                                    if (listApproveGM != null && listApproveGM.size() > 0) {
                                        for (int i = 0; i < listApproveGM.size(); i++) {
                                            Employee objEmployee = new Employee();
                                            objEmployee = (Employee) listApproveGM.get(i);
                                            if (!existEmployee(objEmployee.getOID(), listEmployee)) {
                                                if (objEmployee.getOID() != 0) {
                                                    objEmployee.setFullName(objEmployee.getFullName() + " for General Manager");
                                                    listEmployee.add(objEmployee);
                                                }
                                            };
                                        }
                                        DivHead = true;
                                    }
                                }

//                        if (!HOD) {
//                        if(!existEmployee(emHRDir.getOID(), listEmployee)){
//                           if(emHRDir.getOID()!=0){
//                            listEmployee.add(emHRDir);
//                           }
//                        }
//                        if(!existEmployee(emGM.getOID(), listEmployee)){
//                           if(emGM.getOID()!=0){
//                            listEmployee.add(emGM);
//                           }
//                        }                        
//                      }
                            } else { // non div head 

                                try {
                                    sOIdApprovalEmployeeKhusus = (PstSystemProperty.getValueByName("APPROVALL_EMPLOYEE_KHUSUS"));

                                } catch (Exception e) {
                                    System.out.println("EXCEPTION " + e.toString());
                                }
                                //list karywan yg akan di approve oleh GM

                                if (sOIdApprovalEmployeeKhusus != null && !sOIdApprovalEmployeeKhusus.equalsIgnoreCase(PstSystemProperty.SYS_NOT_INITIALIZED)) {
                                    Hashtable hashLevelProps = com.dimata.util.StringParser.parseGroupVer2(sOIdApprovalEmployeeKhusus);
                                    if (employee != null && hashLevelProps != null) {
                                        String positionId = (String) hashLevelProps.get("" + employee.getPositionId());
                                        if (positionId != null && positionId.length() > 0) {
                                            listEmpDepartment = listApprovalKhusus(positionId, employee);
                                            if (listEmpDepartment != null && listEmpDepartment.size() > 0) {
                                                for (int i = 0; i < listEmpDepartment.size(); i++) {
                                                    Employee objEmployee = new Employee();
                                                    objEmployee = (Employee) listEmpDepartment.get(i);
                                                    if (!existEmployee(objEmployee.getOID(), listEmployee)) {
                                                        if (objEmployee.getOID() != 0) {
                                                            listEmployee.add(objEmployee);
                                                        }
                                                    };
                                                }
                                                DivHead = true;
                                            }
                                        }

                                    }
                                }
                                if (listEmployee == null || listEmployee.size() == 0) {
                                    listEmpDivision = listDivisionApproval(division.getOID(), employee.getOID());
                                    if (listEmpDivision != null && listEmpDivision.size() > 0) {
                                        for (int i = 0; i < listEmpDivision.size(); i++) {
                                            Employee objEmployee = new Employee();
                                            objEmployee = (Employee) listEmpDivision.get(i);
                                            if (!existEmployee(objEmployee.getOID(), listEmployee)) {
                                                if (objEmployee.getOID() != 0) {
                                                    listEmployee.add(objEmployee);
                                                }
                                            };
                                        }
                                        DivHead = true;
                                    }

                                    //update by satrya 2013-12-09
                                    /**
                                     * create by satrya 2013-12-09
                                     */
                                    if (DivHead == false) {
                                        try {
                                            sOidApprovallReplaceGM = (PstSystemProperty.getValueByName("REPLACE_GM_APPROVALL"));
                                            if (sOidApprovallReplaceGM != null && !sOidApprovallReplaceGM.equalsIgnoreCase(PstSystemProperty.SYS_NOT_INITIALIZED)) {
                                                listApproveGM = listDepHeadApproveGMorHotelManager(sOidApprovallReplaceGM);
                                            }
                                        } catch (Exception e) {
                                            System.out.println("EXCEPTION " + e.toString());
                                        }

                                        if (listApproveGM != null && listApproveGM.size() > 0) {
                                            for (int i = 0; i < listApproveGM.size(); i++) {
                                                Employee objEmployee = new Employee();
                                                objEmployee = (Employee) listApproveGM.get(i);
                                                if (!existEmployee(objEmployee.getOID(), listEmployee)) {
                                                    if (objEmployee.getOID() != 0) {
                                                        objEmployee.setFullName(objEmployee.getFullName() + " for General Manager");
                                                        listEmployee.add(objEmployee);
                                                    }
                                                };
                                            }
                                            DivHead = true;
                                        }
                                    }

                                    if (!existEmployee(emHRDir.getOID(), listEmployee)) {
                                        if (emHRDir.getOID() != 0) {
                                            listEmployee.add(emHRDir);
                                        }
                                    }
                                    if (!existEmployee(emGM.getOID(), listEmployee)) {
                                        if (emGM.getOID() != 0) {
                                            listEmployee.add(emGM);
                                        }
                                    }
                                }


                            }
                        } else {
                            if (position.getPositionLevel() == PstPosition.LEVEL_GENERAL_MANAGER) {
                                if (!existEmployee(emGM.getOID(), listEmployee)) {
                                    if (emGM.getOID() != 0) {
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
                        if (emGM.getOID() != 0) {
                            listEmployee.add(emGM);
                        }
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
                        if (emHRDir.getOID() != 0) {
                            listEmployee.add(emHRDir);
                        }
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

        //Vector listEmpDepartment = new Vector();

        //Vector listEmpDivision = new Vector();

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
                    + " ) AND (POS."
                    + PstPosition.fieldNames[PstPosition.FLD_DISABLED_APP_DEPT_SCOPE] + " = " + PstPosition.DISABLED_APP_DEPT_SCOPE_FALSE + " OR POS."
                    + PstPosition.fieldNames[PstPosition.FLD_DISABLED_APP_DEPT_SCOPE] + " IS NULL )"
                    + " AND ( POS."
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

    /**
     * cerate by satrya 2014-04-16 list employee approve khgusus
     */
    public Vector listApprovalKhusus(String PosisitionId, Employee theEmployee) {
        if (PosisitionId == null || PosisitionId.length() == 0 || theEmployee == null) {
            return new Vector();
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
                    + PstLevel.fieldNames[PstLevel.FLD_LEVEL_ID] + " WHERE  EMP."
                    + PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID] + " IN ( " + PosisitionId + ")"
                    + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + " = " + PstEmployee.NO_RESIGN
                    + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + "<>" + theEmployee.getOID();

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
            return result;
        }
    }

    /**
     * create by satrya 2013-12-09 Keterangan : untuk mencari dep head yg untuk
     * approve GM
     *
     * @param oidEmployee
     * @return
     */
    public Vector listDepHeadApproveGMorHotelManager(String oidEmployee) {
        DBResultSet dbrs = null;
        Vector result = new Vector();
        if (oidEmployee == null || oidEmployee.length() == 0) {
            return result;
        }
        try {

            String sql = "SELECT EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMAIL_ADDRESS]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME] + " FROM "
                    + PstEmployee.TBL_HR_EMPLOYEE + " EMP "
                    + " WHERE " + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + " IN(" + oidEmployee + ")";

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
        return null;
    }

    /**
     * create by satrya 2014-04-15 Keterangan: untuk mencari list position
     *
     * @param oidPosition
     * @return
     */
    public Vector listPositionApproveGMSpecial(String oidPosition) {
        DBResultSet dbrs = null;
        Vector result = new Vector();
        if (oidPosition == null || oidPosition.length() == 0) {
            return result;
        }
        try {

            String sql = "SELECT EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMAIL_ADDRESS]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME] + " FROM "
                    + PstEmployee.TBL_HR_EMPLOYEE + " EMP "
                    + " WHERE " + PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID] + " IN(" + oidPosition + ")";

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
        return null;
    }

    /**
     * create by satrya 2013-12-09 keterangan: untuk mencari karyawan yg di
     * approve manager
     *
     * @param oidEmployee
     * @return
     */
    public Vector listEmployeeKhususYgApproveByHotelManager(String oidEmployee) {
        DBResultSet dbrs = null;
        Vector result = new Vector();
        if (oidEmployee == null || oidEmployee.length() == 0) {
            return result;
        }
        try {

            String sql = "SELECT EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMAIL_ADDRESS]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME] + " FROM "
                    + PstEmployee.TBL_HR_EMPLOYEE + " EMP "
                    + " WHERE " + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + " IN(" + oidEmployee + ")";

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
                    + PstPosition.fieldNames[PstPosition.FLD_POSITION_LEVEL] + " IN (" + PstPosition.LEVEL_ASST_MANAGER + "," + PstPosition.LEVEL_MANAGER + ","
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
     * @Desc Untuk mendapatkan periode dari entitle 1. JIka type entitle by
     * annual/ by periode maka akan direturn periodenya 2. Sedangkan jika by
     * commencing date maka akan direturn null
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
     * @Author Roy Andika
     * @Desc Untuk mendapatkan waktu minimum bekerja karyawan mempunyai stock
     * cuti
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
     * @Author Roy Andika
     * @Desc Untuk mendapatkan waktu minimum karyawan itu bekerja boleh
     * mengambil AL
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
     * @Desc Untuk mendapatkan limit date peride bulanan Untuk mengecek batas
     * tanggal untu menentukan periode bulanan, seperti bila limit adalah 17,
     * kemudian karyawan tersebut masuk tanggal 15,karena 15 kurang dari 17 maka
     * akan dapat periode bulana pada bulan sekarang, Sedangkan bila commencing
     * date adalah 19, karena 19 lebih besar dari 17, maka periode bulanan yang
     * didapt adalah periode berikutnya
     * @return
     */
    public int getLimitPeriode() {

        return dateLimit;

    }

    /**
     * @Author Roy Andika
     * @param leaveConfig
     * @param entDt
     * @Desc Untuk mendapatkan perhitungan stock Al dari setiap employee
     * @return
     */
    public float getQtyEntitle(Date entDt, String level, String employeeType, Date commencingDate) {

        int mnth = entDt.getMonth() + 1;
        int tmpPeriod = 0;

        if (mnth == 12) {
            tmpPeriod = 1;
        } else {
            tmpPeriod = mnth + 1;
        }

        try {
            int iLevel = getIndexLevel(level);
            int iType = getIndexType(employeeType);

            int entitle = getMonthPeriod() - tmpPeriod + 1;

            if (commencingDate != null) {
                int LoS = (int) DateCalc.dayDifference(commencingDate, entDt);

                if (LoS >= (iYearsExtraAl * getiDayAYear()) && (commencingDate.getMonth() == entDt.getMonth())) {
                    //update by satrya 2013-08-24
                    // if (LoS >= (iYearsExtraAl * iDayAYear)) {
                    entitle = entitle + iALEntitlesExtra[iLevel][iType];
                }
            }
            return entitle;
        } catch (Exception exc) {;
        }

        return 0;
    }

    /**
     * @Author Roy Andika
     * @param leaveConfig
     * @param DtCommencing
     * @Desc Untuk mendapatakan date entitle pertama bagi yang menggunakan
     * konfigurasi leave menggunakan periode
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

                if (tmp_mnth_ent1 == 1) {

                    month_ent1 = 12;
                    year_ent1 = tmp_year_ent1 - 1;

                } else {

                    month_ent1 = tmp_mnth_ent1 - 1;
                    year_ent1 = tmp_year_ent1;

                }

                tmp = "" + year_ent1 + "-" + month_ent1 + "-" + dt_ent1;

            } else {

                int month_ent1 = 0;
                int dt_ent1 = 0;
                int year_ent1 = 0;

                dt_ent1 = getDatePeriod();
                year_ent1 = Closing_I.getYear() + 1900;
                month_ent1 = Closing_I.getMonth() + 1;

                tmp = "" + year_ent1 + "-" + month_ent1 + "-" + dt_ent1;
            }
        }
        return Formater.formatDate(tmp, "yyyy-MM-dd");
    }

    /**
     * @Author Roy Andika
     * @Desc Untuk mendapatakan Priode start dan end bila menggunakan periode
     * @param commancingDate, dtStartm, leaveConfig
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
     * @Author Roy Andika
     * @Desc
     * @param yer
     * @param dtPeriod
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
     * @Author Roy Andika
     * @param leaveConfig
     */
    public void procesGetEntitleAutomatic() {
    }

    /**
     * @Author Roy Andika
     * @Desc Untuk mendapatkan periode closing bulanan
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
//        String sysMessage = "";
//        int yearMin = iIntervalAL[INTERVAL_AL_KONFIGURASI_I]!=0?iIntervalAL[INTERVAL_AL_KONFIGURASI_I]/12:1;// minimum tahun untuk mendapatkan AL /12 artinya 1 tahun ada 12 bulan
//        Date today = startDate!=null ?startDate:new Date();
//        Date prev10Year = new Date(today.getYear() - 10, today.getMonth(), today.getDate(), 23, 59, 59);
//        Date prev10YearMin7day = new Date(today.getTime() - (7L * 24L * 60L * 60L * 1000L));
//
//        String strWhere = 
//               /*"((" + (today.getYear() + 1900) + "-LEFT(" + PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]
//                + ",4))>=" + yearMin + ") AND " + PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE] + " LIKE \"%-"
//                + Formater.formatDate(today, "MM-dd") + "\"";*/
//                "(("+(today.getYear() + 1900) + " -YEAR("+PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]+"))>="+yearMin+") AND MONTH("+PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]+") <="
//                +(today.getMonth()+1)+" AND DAY("+PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]+")<="+today.getDate();
//
//        DBResultSet dbrs = null;
//        try {
//            String sql = "SELECT e." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + ","
//                    + " e." + PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE] + ","
//                    + " e." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME] + ","
//                    + "l." + PstLevel.fieldNames[PstLevel.FLD_LEVEL] + ","
//                    + "c." + PstEmpCategory.fieldNames[PstEmpCategory.FLD_EMP_CATEGORY]
//                    + " FROM " + PstEmployee.TBL_HR_EMPLOYEE + " AS e"
//                    + " INNER JOIN " + PstLevel.TBL_HR_LEVEL + " AS l ON "
//                    + " l." + PstLevel.fieldNames[PstLevel.FLD_LEVEL_ID] + "=e." + PstEmployee.fieldNames[PstEmployee.FLD_LEVEL_ID]
//                    + " INNER JOIN " + PstEmpCategory.TBL_HR_EMP_CATEGORY + " AS c ON "
//                    + " c." + PstEmpCategory.fieldNames[PstEmpCategory.FLD_EMP_CATEGORY_ID] + "=e." + PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID]
//                    + " WHERE " + strWhere
//                    + " LIMIT 0,10000";
//
//            dbrs = DBHandler.execQueryResult(sql);
//            ResultSet rs = dbrs.getResultSet();
//            while (rs.next()) {
//                boolean adaEmployee=false;
////                if(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]).equalsIgnoreCase("Ni Ketut Ariani")){
////                    int x=0;
////                }
//                float AlEnt = getALEntitleAnualLeave(
//                        rs.getString(PstLevel.fieldNames[PstLevel.FLD_LEVEL]),
//                        rs.getString(PstEmpCategory.fieldNames[PstEmpCategory.FLD_EMP_CATEGORY]),
//                        0, rs.getDate(PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]),
//                        today);
//                if (AlEnt > 0.0F) {
//                    if (sysMessage.length() < 1) {
//                        sysMessage = "Employee(s) have new AL entitle : ";
//                        
//                    }
/////update by satrya 2012-10-16
//                    String where = PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_EMPLOYEE_ID]
//                            + " = " + rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]) + " AND "
//                            + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_AL_STATUS] + "="
//                            + PstAlStockManagement.AL_STS_AKTIF;
//
//                    Vector listAlAktif = PstAlStockManagement.list(0, 0, where, null);
//
//                    AlStockManagement alStockManagement = new AlStockManagement();
//                    Date entitleDate = null;
//                    if(rs.getDate(PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE])!=null){
//                        Date commercingDate = rs.getDate(PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]);
//                        commercingDate.setYear(startDate.getYear());
//                        entitleDate = commercingDate;
//                    }
//                    if (listAlAktif != null && listAlAktif.size() > 0) {
//                        alStockManagement = (AlStockManagement) listAlAktif.get(0);
//                        alStockManagement.setEntitled(AlEnt);
//                        alStockManagement.setAlQty(AlEnt);
//                        //alStockManagement.setEntitleDate(today);
//                        alStockManagement.setEntitleDate(entitleDate);
//                        try {
//                            PstAlStockManagement.updateExc(alStockManagement);
//                            sysMessage = sysMessage + rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]) + "=" + AlEnt + "; ";
//                            adaEmployee=true;
//                        } catch (Exception e) {
//                            System.out.println("Exeption " + e.toString());
//                        }
//                    }
//                }
//                if(!adaEmployee){
//                        sysMessage = "";
//                }
//            }
//            rs.close();
//        } catch (Exception e) {
//            System.out.println(e);
//        } finally {
//            DBResultSet.close(dbrs);
//        }
//
//        return sysMessage;
        String sysMessage = "";

        /*int yearMin = iIntervalAL[INTERVAL_AL_KONFIGURASI_I]!=0?iIntervalAL[INTERVAL_AL_KONFIGURASI_I]/12:1;// minimum tahun untuk mendapatkan AL /12 artinya 1 tahun ada 12 bulan
         Date today = startDate!=null ?startDate:new Date();
         Date prev10Year = new Date(today.getYear() - 10, today.getMonth(), today.getDate(), 23, 59, 59);
         Date prev10YearMin7day = new Date(today.getTime() - (7L * 24L * 60L * 60L * 1000L));

         String strWhere = 
         /*"((" + (today.getYear() + 1900) + "-LEFT(" + PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]
         + ",4))>=" + yearMin + ") AND " + PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE] + " LIKE \"%-"
         + Formater.formatDate(today, "MM-dd") + "\"";*/
        /*    "(("+(today.getYear() + 1900) + " -YEAR("+PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]+"))>="+yearMin+") AND MONTH("+PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]+") <="
         +(today.getMonth()+1)+" AND DAY("+PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]+")<="+today.getDate()
         ;

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
         if(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]).equalsIgnoreCase("I PUTU AGUSTANA")){
         int x=0;
         }
         if(rs.getDate(PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE])!=null){
         float AlEnt = getALEntitleAnualLeave(
         rs.getString(PstLevel.fieldNames[PstLevel.FLD_LEVEL]),
         rs.getString(PstEmpCategory.fieldNames[PstEmpCategory.FLD_EMP_CATEGORY]),
         0, rs.getDate(PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]),
         today);
         Date todaysEntitle = new Date();
         todaysEntitle.setDate(rs.getDate(PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]).getDate());
         todaysEntitle.setMonth(rs.getDate(PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]).getMonth());// ini di set bulan january tergantung configurasi Leave
        
         if (AlEnt > 0.0F) {*/
        /* if (sysMessage.length() < 1) {
         sysMessage = "Employee(s) have new AL entitle : ";
         }*/
///update by satrya 2012-10-16
                    /*String where = PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_EMPLOYEE_ID]
         + " = " + rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]) + " AND "
         + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_AL_STATUS] + "="
         + PstAlStockManagement.AL_STS_AKTIF;

         Vector listAlAktif = PstAlStockManagement.list(0, 0, where, null);*/
//              int iLevel = getIndexLevel(rs.getString(PstLevel.fieldNames[PstLevel.FLD_LEVEL]));
//              int iType = getIndexType(rs.getString(PstEmpCategory.fieldNames[PstEmpCategory.FLD_EMP_CATEGORY]));
        //AlStockManagement alStockManagement = new AlStockManagement();
        // AlStockManagement alStockManagementX = new AlStockManagement();
                    /*if (listAlAktif != null && listAlAktif.size() > 0) {
         alStockManagement = (AlStockManagement) listAlAktif.get(0);
         //u[pdate by satrya 2013-02-25
         //jika extra Al == 0 artinya belum di tambakan cuti +3 pada sebelum tutp periode
         //if(alStockManagement.getExtraAl()==0){
         alStockManagement.setEntitled(AlEnt);
         alStockManagement.setAlQty(alStockManagement.getPrevBalance()+AlEnt);
         //update by satrya 2013-08-24
         // alStockManagement.setAlQty(AlEnt);
         if (sysMessage.length() < 1) {
         sysMessage = "Employee(s) have new AL entitle : ";
         }
         //alStockManagement.setEntitleDate(today);
         //update by satrya 2013-02-25
         alStockManagement.setEntitleDate(todaysEntitle);/// sesuai konfigurasi / jika tahunan ( untuk KTI di pakai berdasarkan commercingdate)

         try {
         PstAlStockManagement.updateExc(alStockManagement);
         sysMessage = sysMessage + rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]) + "=" + AlEnt + "; ";
         } catch (Exception e) {
         System.out.println("Exeption " + e.toString());
         }
         //}
         }*/
        /*         AlStockManagement alStockManagement = new AlStockManagement();
         AlStockManagement alStockManagementX = new AlStockManagement();
         //update by satrya 2013-09-24
         boolean isUpdate = false;
         if (listAlAktif != null && listAlAktif.size() > 0) {
         alStockManagement = (AlStockManagement) listAlAktif.get(0);
         //u[pdate by satrya 2013-02-25
         //jika extra Al == 0 artinya belum di tambakan cuti +3 pada sebelum tutp periode
         if(alStockManagement.getExtraAl()==0){
         alStockManagement.setEntitled(AlEnt);
         alStockManagement.setAlQty(alStockManagement.getPrevBalance()+AlEnt);
         //update by satrya 2013-08-24
         // alStockManagement.setAlQty(AlEnt);
         if (sysMessage.length() < 1) {
         sysMessage = "Employee(s) have new AL entitle : ";
         }
         //alStockManagement.setEntitleDate(today);
         //update by satrya 2013-02-25
         alStockManagement.setEntitleDate(todaysEntitle);/// sesuai konfigurasi / jika tahunan ( untuk KTI di pakai berdasarkan commercingdate)

                       
         //dibuatkan fungsi untuk mencari alStockManagement.getExtraAlDate() hanya mengecek jika dia sudah di tambah 10 tahun comercing datnenya, diambil statusnya yg paling akhir
         //mecari extraAl sebelumnya
         String whereX = PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_ENTITLE_DATE]  + " < (SELECT AL1."+ PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_ENTITLE_DATE] + " FROM "+PstAlStockManagement.TBL_AL_STOCK_MANAGEMENT +" AL1 WHERE AL1."+PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_AL_STOCK_ID]+"="+alStockManagement.getOID()+")" 
         + " AND "+PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_EMPLOYEE_ID]+ " = "+rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]);
                       
         String order = PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_ENTITLE_DATE]+" DESC ";
         Vector getUseExtraAL = PstAlStockManagement.list(0, 0, whereX, order);
                              
         if(getUseExtraAL!=null && getUseExtraAL.size()>0){
         alStockManagementX  = (AlStockManagement) getUseExtraAL.get(0);
         }
         float getExtraAl = getALExtra( rs.getString(PstLevel.fieldNames[PstLevel.FLD_LEVEL]),
         rs.getString(PstEmpCategory.fieldNames[PstEmpCategory.FLD_EMP_CATEGORY]),
         0, rs.getDate(PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]),
         today); 
         if(alStockManagementX.getExtraAlDate()==null && getExtraAl!=0){
                           
         if(rs.getDate(PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE])!=null){
         Date extraAl = rs.getDate(PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]);
         //int year = (rs.getDate(PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]).getYear()+1900)+10;
         extraAl.setYear((rs.getDate(PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]).getYear())+11);
                               
         alStockManagement.setExtraAlDate(extraAl); // by commercing date + 11 //dan setelah itu ketika sdh lebih dari 11 th tinggal di tambah 4
                               
         alStockManagement.setExtraAl(getExtraAl); // ini yang di dapatkan oleh karyawan (12)
                               
         isUpdate=true;
         }
                           
         }else{
         if(rs.getDate(PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE])!=null && getExtraAl!=0){
         Date extraAl = alStockManagement.getExtraAlDate();
         if(extraAl!=null){
         extraAl.setYear( (alStockManagement.getExtraAlDate().getYear()+1900)+4);
         }
         alStockManagement.setExtraAlDate(extraAl); // by commercing date + 11 //dan setelah itu ketika sdh lebih dari 11 th tinggal di tambah 4
         alStockManagement.setExtraAl(getExtraAl); // ini yang di dapatkan oleh karyawan (12)
         isUpdate=true;
         }
         }
         try {
         if(isUpdate){
         PstAlStockManagement.updateExc(alStockManagement);
         sysMessage = sysMessage + rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]) + "=" + AlEnt + "; ";
         }
         } catch (Exception e) {
         System.out.println("Exeption " + e.toString());
         }
         }
         }
         }
         }
         //untuk paid
         SessAnnualLeave.proccessPaid(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]));
         }
         rs.close();
         } catch (Exception e) {
         System.out.println(e);
         } finally {
         DBResultSet.close(dbrs);
         }*/
        /*try 
         {
         String where = PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] 
         + " = " + PstEmployee.NO_RESIGN
         +" AND MONTH(" + PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE] + ")" +
         " = " + (startDate.getMonth() + 1);
         Vector vct = PstEmployee.list(0, 0, where, null);            
             
         if (vct!=null && vct.size()>0) 
         {                                    
         // iterate process for each employee
         for (int i = 0; i < vct.size(); i++)   
         {
         Employee emp = (Employee) vct.get(i);
                    
         //hanya test
         if(
         emp.getFullName().equalsIgnoreCase("FRIEDA FRANCISCA ZUTHER") ||
         emp.getFullName().equalsIgnoreCase("WAYAN WETNY")
                            
         ){
         int x=0;
         }
         // pengecekan apakan commencing date employee ini adalah tahun ini atau tidak
         Date empCommDate = emp.getCommencingDate();

         //Selisih tahun
         //int diff = ServiceAlStock.differenceYear(empCommDate, startDate);

         // jika masa kerja adalah lebih dari satu tahun
                   
         if(ServiceAlStock.isOverThanAYear(empCommDate,startDate))
         {
         Date owningLL = new Date(startDate.getYear(),empCommDate.getMonth(),empCommDate.getDate());

         //Mencari apakah AL sudah diperoleh untuk periode ini?
         //update by satrya 2012-10-16
         String whereClause = PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_EMPLOYEE_ID]
         +" = "+emp.getOID()
         +" AND "+PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_OWNING_DATE]
         +" = '"+Formater.formatDate(owningLL, "yyyy-MM-dd")+"'";

         Vector vAlStockMan = new Vector(1,1);
         try{
         vAlStockMan = PstAlStockManagement.list(0, 0, whereClause, null);
         }catch(Exception ex){}
         // jika karyawan telah bekerja 12 bulang
         if (vAlStockMan==null || vAlStockMan.size()==0)
         {
         float alQty = 0;                          

         String strLevel = getLevel(emp.getOID());
         String strType = getCategory(emp.getOID());
         int LoS = (int) DateCalc.dayDifference(emp.getCommencingDate(), startDate);
         alQty = getALEntitleAnualLeave(strLevel, strType, LoS, empCommDate, startDate );
         float checkAlExtra = getChekExtraEntitle(strLevel, strType, LoS, empCommDate, startDate);
                            
         AlStockManagement alStkMnt = new AlStockManagement();
         alStkMnt.setEntitled(alQty);
         alStkMnt.setPrevBalance(0);
         alStkMnt.setAlQty(alStkMnt.getEntitled()+alStkMnt.getPrevBalance());                                                
         alStkMnt.setDtOwningDate(owningLL);
         alStkMnt.setEntitleDate(owningLL);
         alStkMnt.setEmployeeId(emp.getOID());                      
         alStkMnt.setAlStatus(PstAlStockManagement.AL_STS_AKTIF);
         alStkMnt.setQtyResidue(alStkMnt.getEntitled()+alStkMnt.getPrevBalance());  
         alStkMnt.setQtyUsed(0);
         alStkMnt.setStNote("");
         alStkMnt.setRecordDate(startDate);
                            
                            
         if(emp.getCommencingDate()!=null && checkAlExtra!=0){
         Date extraAl = emp.getCommencingDate();
         //int year = (rs.getDate(PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]).getYear()+1900)+10;
         extraAl.setYear(emp.getCommencingDate().getYear()+11);
                               
         alStkMnt.setExtraAlDate(extraAl); // by commercing date + 11 //dan setelah itu ketika sdh lebih dari 11 th tinggal di tambah 4
                               
         alStkMnt.setExtraAl(checkAlExtra);
         }
                            
         PstAlStockManagement.insertExc(alStkMnt);
         sysMessage = sysMessage + emp.getFullName() + "=" + alQty + "; ";
         // pembayaran hutang AL jika ada                                                                                                                                     
         SessAnnualLeave.proccessPaid(emp.getOID());
                            
         }
         }
         }
         }
         else 
         {
         System.out.println("No employee on prosesOwningAlStock ... ");
         }
         }
         catch (Exception e) 
         {
         System.out.println("Exc when prosesOwningAlStock : " + e.toString());   
         }*/
        return sysMessage;
    }

    public String setAutoALEntitle(Date startDate, long employeeId) {
        //public String setAutoALEntitle(Date startDate) {
        String sysMessage = "";
        if (employeeId == 0) {
            return sysMessage;
        }
        int yearMin = iIntervalAL[INTERVAL_AL_KONFIGURASI_I] != 0 ? iIntervalAL[INTERVAL_AL_KONFIGURASI_I] / 12 : 1;// minimum tahun untuk mendapatkan AL /12 artinya 1 tahun ada 12 bulan
        Date today = startDate != null ? startDate : new Date();
        Date prev10Year = new Date(today.getYear() - 10, today.getMonth(), today.getDate(), 23, 59, 59);
        Date prev10YearMin7day = new Date(today.getTime() - (7L * 24L * 60L * 60L * 1000L));

        String strWhere = /*"((" + (today.getYear() + 1900) + "-LEFT(" + PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]
                 + ",4))>=" + yearMin + ") AND " + PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE] + " LIKE \"%-"
                 + Formater.formatDate(today, "MM-dd") + "\"";*/ "((" + (today.getYear() + 1900) + " -YEAR(" + PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE] + "))>=" + yearMin + ") AND MONTH(" + PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE] + ") <="
                + (today.getMonth() + 1) + " AND DAY(" + PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE] + ")<=" + today.getDate()
                + " AND " + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + "=" + employeeId;

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
                String empName = rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]);
                if (empName.equalsIgnoreCase("SUANA, I MADE")
                        || empName.equalsIgnoreCase("WAYAN WETNY")) {
                    int x = 0;
                }
                boolean isUpdate = false;
                if (rs.getDate(PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]) != null) {
                    float AlEnt = getALExtra( // update by satrya 2013-10-21 getALEntitleAnualLeave
                            rs.getString(PstLevel.fieldNames[PstLevel.FLD_LEVEL]),
                            rs.getString(PstEmpCategory.fieldNames[PstEmpCategory.FLD_EMP_CATEGORY]),
                            0, rs.getDate(PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]),
                            today);
                    Date todaysEntitle = new Date();
                    todaysEntitle.setDate(rs.getDate(PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]).getDate());
                    todaysEntitle.setMonth(rs.getDate(PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]).getMonth());// ini di set bulan january tergantung configurasi Leave
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
                        int iLevel = getIndexLevel(rs.getString(PstLevel.fieldNames[PstLevel.FLD_LEVEL]));
                        int iType = getIndexType(rs.getString(PstEmpCategory.fieldNames[PstEmpCategory.FLD_EMP_CATEGORY]));

                        AlStockManagement alStockManagement = new AlStockManagement();
                        AlStockManagement alStockManagementX = new AlStockManagement();
                        if (listAlAktif != null && listAlAktif.size() > 0) {
                            alStockManagement = (AlStockManagement) listAlAktif.get(0);
                            //u[pdate by satrya 2013-02-25
                            //jika extra Al == 0 artinya belum di tambakan cuti +3 pada sebelum tutp periode
                            if (alStockManagement.getExtraAl() == 0) {
                                alStockManagement.setEntitled(alStockManagement.getEntitled() + AlEnt);
                                alStockManagement.setAlQty(alStockManagement.getPrevBalance() + alStockManagement.getEntitled());
                                alStockManagement.setQtyResidue(alStockManagement.getAlQty() - alStockManagement.getQtyUsed());
                                //update by satrya 2013-08-24
                                // alStockManagement.setAlQty(AlEnt);
                                if (sysMessage.length() < 1) {
                                    sysMessage = "Employee(s) have new AL entitle : ";
                                }
                                //alStockManagement.setEntitleDate(today);
                                //update by satrya 2013-02-25
                                //hiden by satrya 
                                //alStockManagement.setEntitleDate(todaysEntitle);/// sesuai konfigurasi / jika tahunan ( untuk KTI di pakai berdasarkan commercingdate)

                                //dibuatkan fungsi untuk mencari alStockManagement.getExtraAlDate() hanya mengecek jika dia sudah di tambah 10 tahun comercing datnenya, diambil statusnya yg paling akhir
                                //mecari extraAl sebelumnya
                                String whereX = PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_ENTITLE_DATE] + " < (SELECT AL1." + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_ENTITLE_DATE] + " FROM " + PstAlStockManagement.TBL_AL_STOCK_MANAGEMENT + " AL1 WHERE AL1." + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_AL_STOCK_ID] + "=" + alStockManagement.getOID() + ")"
                                        + " AND " + PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_EMPLOYEE_ID] + " = " + rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]);

                                String order = PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_ENTITLE_DATE] + " DESC ";
                                Vector getUseExtraAL = PstAlStockManagement.list(0, 0, whereX, order);

                                if (getUseExtraAL != null && getUseExtraAL.size() > 0) {
                                    alStockManagementX = (AlStockManagement) getUseExtraAL.get(0);
                                }
                                float getExtraAl = getALExtra(rs.getString(PstLevel.fieldNames[PstLevel.FLD_LEVEL]),
                                        rs.getString(PstEmpCategory.fieldNames[PstEmpCategory.FLD_EMP_CATEGORY]),
                                        0, rs.getDate(PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]),
                                        today);
                                if (alStockManagementX.getExtraAlDate() == null && getExtraAl != 0) {

                                    if (rs.getDate(PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]) != null) {
                                        Date extraAl = rs.getDate(PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]);
                                        //int year = (rs.getDate(PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]).getYear()+1900)+10;
                                        extraAl.setYear((rs.getDate(PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]).getYear()) + 11);

                                        alStockManagement.setExtraAlDate(extraAl); // by commercing date + 11 //dan setelah itu ketika sdh lebih dari 11 th tinggal di tambah 4

                                        alStockManagement.setExtraAl(getExtraAl); // ini yang di dapatkan oleh karyawan (12)

                                        isUpdate = true;
                                    }

                                } else {
                                    if (rs.getDate(PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]) != null && getExtraAl != 0) {
                                        Date extraAl = alStockManagement.getExtraAlDate();
                                        if (extraAl != null) {
                                            extraAl.setYear((alStockManagement.getExtraAlDate().getYear() + 1900) + 11);
                                        }
                                        alStockManagement.setExtraAlDate(extraAl); // by commercing date + 11 //dan setelah itu ketika sdh lebih dari 11 th tinggal di tambah 4
                                        alStockManagement.setExtraAl(getExtraAl); // ini yang di dapatkan oleh karyawan (12)
                                        isUpdate = true;
                                    }
                                }
                                try {
                                    if (isUpdate) {
                                        PstAlStockManagement.updateExc(alStockManagement);
                                        sysMessage = sysMessage + rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]) + "=" + AlEnt + "; ";
                                    }
                                } catch (Exception e) {
                                    System.out.println("Exeption " + e.toString());
                                }
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

        return sysMessage;
    }

    /**
     * keterangan : mendapatkan entitle LL secara otomatis create by satrya
     * 2013-09-27
     *
     * @param startDate
     * @param leave
     * @return
     */
    public String setAutoLLEntitle(Date startDate, I_Leave leave) {
        String sysMessage = "";
        try {
            if (startDate != null && leave != null) {
                ServiceLLStock.processLLStockHR(startDate, leave);
            }
        } catch (Exception e) {
            System.out.println("Exc when prosesOwningLLStock : " + e.toString());
        }
        return sysMessage;
    }

    public Vector<Employee> overtimeRequester(long overtimeId) {
        if (overtimeId == 0) {
            return new Vector();
        }
        try {
            Overtime overtime = PstOvertime.fetchExc(overtimeId);
            Vector listMaxEmp = PstOvertimeDetail.maxLevelEmployees(overtimeId);
            Department department = PstDepartment.fetchExc(overtime.getDepartmentId());
            int maxLevel = 0;
            boolean hod = false;
            boolean divHead = false;
            long employeeId = 0;
            if (listMaxEmp != null) {
                for (int idx = 0; idx < listMaxEmp.size(); idx++) {
                    OvertimeDetail ovd = (OvertimeDetail) listMaxEmp.get(idx);
                    maxLevel = ovd.getPositionLevel();
                    if (!hod && !divHead) {
                        employeeId = ovd.getEmployeeId();
                    }
                    if (ovd.getPositionDisableDepartmentApproval() == PstPosition.DISABLED_APP_DEPT_SCOPE_FALSE
                            && ovd.getPositionLevel() >= PstPosition.LEVEL_ASST_MANAGER) {
                        hod = true;
                        employeeId = ovd.getEmployeeId();
                    }
                    if (ovd.getPositionDisableDivisionApproval() == PstPosition.DISABLED_APP_DIV_SCOPE_FALSE
                            && ovd.getPositionLevel() >= PstPosition.LEVEL_ASST_MANAGER) {
                        divHead = true;
                        employeeId = ovd.getEmployeeId();
                        break;
                    }
                }
            }

            Position position = new Position();
            position.setPositionLevel(maxLevel);
            position.setDisabedAppDivisionScope(divHead ? 0 : 1);
            position.setDisabledAppDeptScope(hod ? 0 : 1);
            Division division = new Division();
            division.setOID(department.getDivisionId());
            Employee employee = new Employee();
            employee.setOID(employeeId);

            Vector listEmployee = new Vector();
            Vector listEmpDepartment = new Vector();
            Vector listEmpDivision = new Vector();

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
                    if (!existEmployee(emHRDir.getOID(), listEmployee)) {
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
                        if (!existEmployee(emHRMan.getOID(), listEmployee)) {
                            listEmployee.add(emHRMan);
                        }
                        if (!existEmployee(emHRDir.getOID(), listEmployee)) {
                            listEmployee.add(emHRDir);
                        }
                    }

                    if (!HOD && !DivHead) {
                        if (!existEmployee(emHRMan.getOID(), listEmployee)) {
                            listEmployee.add(emHRMan);
                        }
                        if (!existEmployee(emHRDir.getOID(), listEmployee)) {
                            listEmployee.add(emHRDir);
                        }
                        if (!existEmployee(emGM.getOID(), listEmployee)) {
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
                            if (!existEmployee(emHRDir.getOID(), listEmployee)) {
                                listEmployee.add(emHRDir);
                            }
                            if (!existEmployee(emGM.getOID(), listEmployee)) {
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
                        if (!existEmployee(emHRDir.getOID(), listEmployee)) {
                            listEmployee.add(emHRDir);
                        }
                        if (!existEmployee(emGM.getOID(), listEmployee)) {
                            listEmployee.add(emGM);
                        }
                    }
                } else {
                    if (position.getPositionLevel() == PstPosition.LEVEL_ASST_DIRECTOR) {
                        if (position.getDisabedAppDivisionScope() == 0) { // level ast director as Div.Head                                 
                            if (!existEmployee(emHRDir.getOID(), listEmployee)) {
                                listEmployee.add(emHRDir);
                            }
                            if (!existEmployee(emGM.getOID(), listEmployee)) {
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
                            if (!existEmployee(emHRDir.getOID(), listEmployee)) {
                                listEmployee.add(emHRDir);
                            }
                            if (!existEmployee(emGM.getOID(), listEmployee)) {
                                listEmployee.add(emGM);
                            }
                        }
                    } else {
                        if (position.getPositionLevel() == PstPosition.LEVEL_DIRECTOR) {
                            if (position.getDisabedAppDivisionScope() == 0) { // level director as Div.Head                                 
                                if (!existEmployee(emHRDir.getOID(), listEmployee)) {
                                    listEmployee.add(emHRDir);
                                }
                                if (!existEmployee(emGM.getOID(), listEmployee)) {
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
                                if (!existEmployee(emHRDir.getOID(), listEmployee)) {
                                    listEmployee.add(emHRDir);
                                }
                                if (!existEmployee(emGM.getOID(), listEmployee)) {
                                    listEmployee.add(emGM);
                                }
                            }
                        } else {
                            if (position.getPositionLevel() == PstPosition.LEVEL_GENERAL_MANAGER) {
                                if (!existEmployee(emGM.getOID(), listEmployee)) {
                                    listEmployee.add(emGM);
                                }
                            }
                        }
                    }
                }
            }

            return listEmployee;//PstEmployee.listEmployeeSupervisi(overtime.getDepartmentId(), 0L, PstPosition.LEVEL_ASST_MANAGER, PstPosition.LEVEL_DIRECTOR);
        } catch (Exception exc) {
            return new Vector();
        }

    }

    public Vector<Employee> overtimeApprover(long overtimeId) {
        if (overtimeId == 0) {
            return new Vector();
        }
        try {
            Overtime overtime = PstOvertime.fetchExc(overtimeId);
            Vector listMaxEmp = PstOvertimeDetail.maxLevelEmployees(overtimeId);
            Department department = PstDepartment.fetchExc(overtime.getDepartmentId());
            int maxLevel = 0;
            boolean hod = false;
            boolean divHead = false;
            long employeeId = 0;
            if (listMaxEmp != null) {
                for (int idx = 0; idx < listMaxEmp.size(); idx++) {
                    OvertimeDetail ovd = (OvertimeDetail) listMaxEmp.get(idx);
                    maxLevel = ovd.getPositionLevel();
                    if (!hod && !divHead) {
                        employeeId = ovd.getEmployeeId();
                    }
                    if (ovd.getPositionDisableDepartmentApproval() == PstPosition.DISABLED_APP_DEPT_SCOPE_FALSE
                            && ovd.getPositionLevel() >= PstPosition.LEVEL_ASST_MANAGER) {
                        hod = true;
                        employeeId = ovd.getEmployeeId();
                    }
                    if (ovd.getPositionDisableDivisionApproval() == PstPosition.DISABLED_APP_DIV_SCOPE_FALSE
                            && ovd.getPositionLevel() >= PstPosition.LEVEL_ASST_MANAGER) {
                        divHead = true;
                        employeeId = ovd.getEmployeeId();
                        break;
                    }
                }
            }

            Position position = new Position();
            position.setPositionLevel(maxLevel);
            position.setDisabedAppDivisionScope(divHead ? 0 : 1);
            position.setDisabledAppDeptScope(hod ? 0 : 1);
            Division division = new Division();
            division.setOID(department.getDivisionId());
            Employee employee = new Employee();
            employee.setOID(employeeId);

            Vector listEmployee = new Vector();
            Vector listEmpDepartment = new Vector();
            Vector listEmpDivision = new Vector();

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
                    if (!existEmployee(emHRDir.getOID(), listEmployee)) {
                        listEmployee.add(emHRDir);
                    }
                    if (!existEmployee(emGM.getOID(), listEmployee)) {
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
                        if (!existEmployee(emHRMan.getOID(), listEmployee)) {
                            listEmployee.add(emHRMan);
                        }
                        if (!existEmployee(emHRDir.getOID(), listEmployee)) {
                            listEmployee.add(emHRDir);
                        }
                    }

                    if (!HOD && !DivHead) {
                        if (!existEmployee(emHRMan.getOID(), listEmployee)) {
                            listEmployee.add(emHRMan);
                        }
                        if (!existEmployee(emHRDir.getOID(), listEmployee)) {
                            listEmployee.add(emHRDir);
                        }
                        if (!existEmployee(emGM.getOID(), listEmployee)) {
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
                            if (!existEmployee(emHRDir.getOID(), listEmployee)) {
                                listEmployee.add(emHRDir);
                            }
                            if (!existEmployee(emGM.getOID(), listEmployee)) {
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
                        if (!existEmployee(emHRDir.getOID(), listEmployee)) {
                            listEmployee.add(emHRDir);
                        }
                        if (!existEmployee(emGM.getOID(), listEmployee)) {
                            listEmployee.add(emGM);
                        }
                    }
                } else {
                    if (position.getPositionLevel() == PstPosition.LEVEL_ASST_DIRECTOR) {
                        if (position.getDisabedAppDivisionScope() == 0) { // level ast director as Div.Head                                 
                            if (!existEmployee(emHRDir.getOID(), listEmployee)) {
                                listEmployee.add(emHRDir);
                            }
                            if (!existEmployee(emGM.getOID(), listEmployee)) {
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
                            if (!existEmployee(emHRDir.getOID(), listEmployee)) {
                                listEmployee.add(emHRDir);
                            }
                            if (!existEmployee(emGM.getOID(), listEmployee)) {
                                listEmployee.add(emGM);
                            }
                        }
                    } else {
                        if (position.getPositionLevel() == PstPosition.LEVEL_DIRECTOR) {
                            if (position.getDisabedAppDivisionScope() == 0) { // level director as Div.Head                                 
                                if (!existEmployee(emHRDir.getOID(), listEmployee)) {
                                    listEmployee.add(emHRDir);
                                }
                                if (!existEmployee(emGM.getOID(), listEmployee)) {
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
                                if (!existEmployee(emHRDir.getOID(), listEmployee)) {
                                    listEmployee.add(emHRDir);
                                }
                                if (!existEmployee(emGM.getOID(), listEmployee)) {
                                    listEmployee.add(emGM);
                                }
                            }
                        } else {
                            if (position.getPositionLevel() == PstPosition.LEVEL_GENERAL_MANAGER) {
                                if (!existEmployee(emGM.getOID(), listEmployee)) {
                                    listEmployee.add(emGM);
                                }
                            }
                        }
                    }
                }
            }
            return listEmployee;
            //return PstEmployee.listEmployeeSupervisi(overtime.getDepartmentId(), 0L, PstPosition.LEVEL_ASST_MANAGER, PstPosition.LEVEL_DIRECTOR);
        } catch (Exception exc) {
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
        return true;
    }

    public boolean getDPUnpaidIsNull() {
        return false;
    }

    public boolean getDPEligibleMinus(float eligbleDay) {
        float maxMinus = 0;//Long.parseLong(PstSystemProperty.getValueByName("OID_HRD_DEPARTMENT"));
        try {
            maxMinus = Float.parseFloat(PstSystemProperty.getValueByName("MAX_LEAVE_DP_MINUS"));
            eligibleDayMaxMinus = maxMinus * -1;
        } catch (Exception exc) {
            System.out.println("Exc" + exc);
            eligibleDayMaxMinus = maxMinus;
        }
        if (eligbleDay >= eligibleDayMaxMinus) {
            return true;
        } else {
            return false;
        }
    }

    public boolean getDPExpired() {
        return false;
    }

    public boolean getDPStockMinus(DpStockManagement dpStockManagement, DpStockTaken dpStockTaken) {
        float maxMinus = 0;//Long.parseLong(PstSystemProperty.getValueByName("OID_HRD_DEPARTMENT"));
        try {
            maxMinus = Float.parseFloat(PstSystemProperty.getValueByName("MAX_LEAVE_DP_MINUS"));
            eligibleDayMaxMinus = maxMinus * -1;
        } catch (Exception exc) {
            System.out.println("Exc" + exc);
            eligibleDayMaxMinus = maxMinus;
        }
        if (dpStockManagement.getiDpQty() - dpStockTaken.getTakenQty() >= eligibleDayMaxMinus) {
            return false;
        } else {
            return true;
        }
    }

    public boolean getDPPaidPayable() {
        return true;
    }

    public boolean getLLShowEntile2() {
        return false;
    }

    public Vector getAlValExtend() {
        Vector vectValExtend = new Vector();
        for (int x = 0; x <= 12; x++) {
            vectValExtend.add("" + x);
        }
        return vectValExtend;
    }

    public Vector getAlKeyExtend() {
        Vector vectListExtend = new Vector();
        for (int x = 0; x <= 12; x++) {
            if (x != 0) {
                vectListExtend.add(x + " MONTH ");
            } else {
                vectListExtend.add("select");
            }
        }
        return vectListExtend;
    }

    public Vector listgetEmployeeActiveByPeriode(SrcLeaveAppAlClosing objSrcLeaveAppAlClosing, I_Leave leaveConfig) {
        Vector listEmployeeActiveByPeriod = SessLeaveClosing.getEmployeeActiveByPeriode(objSrcLeaveAppAlClosing, leaveConfig);
        return listEmployeeActiveByPeriod;
    }

    public boolean getConfigurationDpMinus() {
        return false;
    }

    public boolean isMessageUseAdvanceMinusLimit() {
        return true;
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
                    + PstPosition.fieldNames[PstPosition.FLD_POSITION_LEVEL] + " IN (" + PstPosition.LEVEL_ASST_MANAGER + "," + PstPosition.LEVEL_MANAGER + ","
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
     * @return the iDayAYear
     */
    public int getiDayAYear() {
        return iDayAYear;
    }

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

    public Date getAlExtraDateAl(float extraAl, Date commercingDate) {
        Date extraAlDate = null;
        if (commercingDate != null && extraAl != 0) {
            extraAlDate = (Date) commercingDate.clone();
            extraAlDate.setYear((extraAlDate.getYear() + 1900) + iYearsExtraAl);
        }
        return extraAlDate;
    }

    public int getConfigurationSendMail() {
        return I_Leave.LEAVE_CONFIG_SEND_EMAIL_CREATE_BY_LEAVE_FORM;
    }

    public int getConfigurationLeaveApprovall() {
        return I_Leave.LEAVE_CONFIG_AFTER_APPROVALL_HRD_YES_EXECUTE;
    }

    public int getConfigurationLeaveDpTakenLong() {
        return I_Leave.LEAVE_CONFIG_REQUEST_DP_TAKEN_YES_LONG;
    }

    public boolean getDPStockMinus(DpStockTaken dpStockTaken, float residueSystem, float dpTakenPrev) {
        return false;
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
