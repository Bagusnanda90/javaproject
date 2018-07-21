/*
 * EmpScheduleListXLS.java
 *
 * Created on October 16, 2002, 12:08 PM
 */
 
package com.dimata.harisma.report;           

import javax.servlet.*;
import javax.servlet.http.*;

import java.util.*;

//import java.io.InputStream;
//import java.io.IOException;
//import java.io.ByteArrayInputStream;
//import java.io.FileInputStream;
//import java.io.FileOutputStream;

//import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.hssf.record.*;
import org.apache.poi.hssf.model.*;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.*;

import com.dimata.harisma.entity.search.*;
import com.dimata.harisma.session.attendance.*;
import com.dimata.harisma.entity.employee.*;
import com.dimata.harisma.entity.masterdata.*;
import com.dimata.harisma.entity.attendance.*;
import com.dimata.system.entity.system.*;

/** 
 *
 * @author  karya
 * @version 
 */
public class EmpScheduleListXLS extends HttpServlet {
   
    /** Initializes the servlet.
    */  
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

    }

    /** Destroys the servlet.
    */  
    public void destroy() {

    }

    /** Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
    * @param request servlet request
    * @param response servlet response
    */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, java.io.IOException {
        System.out.println("---===| Excel Report |===---");
        //response.setContentType("text/html");
        //response.setContentType("application/vnd.ms-excel");
        response.setContentType("application/x-msexcel");

        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("Schedule List");

        HSSFCellStyle style = wb.createCellStyle();
        style.setFillBackgroundColor(new HSSFColor.WHITE().getIndex());//new HSSFColor.WHITE().getIndex());//HSSFCellStyle.WHITE);
        style.setFillForegroundColor(new HSSFColor.WHITE().getIndex());//new HSSFColor.WHITE().getIndex());//HSSFCellStyle.WHITE);
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setBorderBottom(HSSFCellStyle.BORDER_DOUBLE);

        HSSFCellStyle style2 = wb.createCellStyle();
        style2.setFillBackgroundColor(new HSSFColor.WHITE().getIndex());//new HSSFColor.WHITE().getIndex());//HSSFCellStyle.WHITE);
        style2.setFillForegroundColor(new HSSFColor.WHITE().getIndex());//new HSSFColor.WHITE().getIndex());//HSSFCellStyle.WHITE);
        style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style2.setBorderBottom(HSSFCellStyle.BORDER_DOTTED);

        HSSFCellStyle style3 = wb.createCellStyle();
        style3.setFillBackgroundColor(new HSSFColor.GREY_25_PERCENT().getIndex());//HSSFCellStyle.GREY_25_PERCENT);
        style3.setFillForegroundColor(new HSSFColor.GREY_25_PERCENT().getIndex());//HSSFCellStyle.GREY_25_PERCENT);
        style3.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        //style3.setBorderBottom(HSSFCellStyle.BORDER_THIN);

        HSSFCellStyle style4 = wb.createCellStyle();
        style4.setFillBackgroundColor(new HSSFColor.WHITE().getIndex());//new HSSFColor.WHITE().getIndex());//HSSFCellStyle.WHITE);
        style4.setFillForegroundColor(new HSSFColor.WHITE().getIndex());//new HSSFColor.WHITE().getIndex());//HSSFCellStyle.WHITE);
        style4.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

        // Create a row and put some cells in it. Rows are 0 based.
        HSSFRow row = sheet.createRow((short) 0);
        HSSFCell cell = row.createCell((short) 0);
        cell.setCellValue("WORKING SCHEDULE REPORT");
        cell.setCellStyle(style);
        for (int j = 1; j < 33; j++) {
            cell = row.createCell((short) j);
            cell.setCellStyle(style);
        }
        sheet.addMergedRegion(new Region(0,(short)0,0,(short)32));

        row = sheet.createRow((short) (1));
        for (int k = 0; k < 33; k++) {
            cell = row.createCell((short) k);
            cell.setCellValue("");
            cell.setCellStyle(style4);
        }

        /*
        // utk MELIA 
        String[] tableHeader = = {
            "Period", "Name", 
            "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31",
            "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", 
            "11", "12", "13", "14", "15", "16", "17", "18", "19", "20"
        };
         */ 
        String[] tableHeader = {
            "Period", "Name","Emp Num", 
            "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", 
            "11", "12", "13", "14", "15", "16", "17", "18", "19", "20",
            "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"                      
        };            
        
        row = sheet.createRow((short) (2));
        for (int k = 0; k < 34; k++) {
            cell = row.createCell((short) k);
            cell.setCellValue(tableHeader[k]);
            cell.setCellStyle(style3);
        }
        
        SessEmpSchedule sessEmpSchedule = new SessEmpSchedule();
        SrcEmpSchedule srcEmpSchedule = new SrcEmpSchedule();
        HttpSession session = request.getSession();
        srcEmpSchedule = (SrcEmpSchedule) session.getValue(SessEmpSchedule.SESS_SRC_EMPSCHEDULE);
        Vector listEmpSchedule = new Vector(1,1);
        listEmpSchedule = SessEmpSchedule.searchEmpSchedule(srcEmpSchedule, 0, 500);

        Hashtable scheduleSymbol = new Hashtable();
        Vector listScd = PstScheduleSymbol.listAll();
        scheduleSymbol.put("0", "-");

        for (int ls = 0; ls < listScd.size(); ls++) {
            ScheduleSymbol scd = (ScheduleSymbol) listScd.get(ls);
            scheduleSymbol.put(String.valueOf(scd.getOID()), scd.getSymbol());
        }

        
        // ngecek tipe schdeule yang akan di print, MIDDLE or FULL
        if(SystemProperty.SYS_PROP_SCHEDULE_PERIOD == SystemProperty.TYPE_SCHEDULE_PERIOD_A_MONTH_FULL)        
        {    
                    
            //String d = "";
            for (int i = 0; i < listEmpSchedule.size(); i++) {
                Vector temp = (Vector) listEmpSchedule.get(i);
                Employee employee = (Employee) temp.get(1);
                Period period = (Period) temp.get(2);
                EmpSchedule empSchedule = (EmpSchedule)temp.get(0);
                //System.out.println(period.getPeriod());
                //System.out.println(employee.getFullName());
                //System.out.println(scheduleSymbol.get(String.valueOf(empSchedule.getD21())));
                row = sheet.createRow((short) (i+3));

                cell = row.createCell((short) 0);
                cell.setCellValue(period.getPeriod());
                cell.setCellStyle(style2);

                cell = row.createCell((short) 1);
                cell.setCellValue(employee.getFullName());
                cell.setCellStyle(style2);

                 
                cell = row.createCell((short) 2);
                cell.setCellValue( String.valueOf(employee.getEmployeeNum()) );                                
                cell.setCellStyle(style2);
                /*
                rowx.add( String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD1()))) + (empSchedule.getD2nd1()==0 ? "" : "/"+String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD2nd1())))) );
                rowx.add( String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD2()))) + (empSchedule.getD2nd2()==0 ? "" : "/"+String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD2nd2())))) );
                rowx.add( String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD3()))) + (empSchedule.getD2nd3()==0 ? "" : "/"+String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD2nd3())))) );
                rowx.add( String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD4()))) + (empSchedule.getD2nd4()==0 ? "" : "/"+String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD2nd4())))) );
                rowx.add( String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD5()))) + (empSchedule.getD2nd5()==0 ? "" : "/"+String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD2nd5())))) );
                rowx.add( String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD6()))) + (empSchedule.getD2nd6()==0 ? "" : "/"+String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD2nd6())))) );
                rowx.add( String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD7()))) + (empSchedule.getD2nd7()==0 ? "" : "/"+String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD2nd7())))) );
                rowx.add( String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD8()))) + (empSchedule.getD2nd8()==0 ? "" : "/"+String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD2nd8())))) );
                rowx.add( String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD9()))) + (empSchedule.getD2nd9()==0 ? "" : "/"+String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD2nd9())))) );
                rowx.add( String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD10()))) + (empSchedule.getD2nd10()==0 ? "" : "/"+String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD2nd10())))) );
                rowx.add( String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD11()))) + (empSchedule.getD2nd11()==0 ? "" : "/"+String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD2nd11())))) );
                rowx.add( String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD12()))) + (empSchedule.getD2nd12()==0 ? "" : "/"+String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD2nd12())))) );
                rowx.add( String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD13()))) + (empSchedule.getD2nd13()==0 ? "" : "/"+String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD2nd13())))) );
                rowx.add( String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD14()))) + (empSchedule.getD2nd14()==0 ? "" : "/"+String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD2nd14())))) );
                rowx.add( String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD15()))) + (empSchedule.getD2nd15()==0 ? "" : "/"+String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD2nd15())))) );
                rowx.add( String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD16()))) + (empSchedule.getD2nd16()==0 ? "" : "/"+String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD2nd16())))) );
                rowx.add( String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD17()))) + (empSchedule.getD2nd17()==0 ? "" : "/"+String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD2nd17())))) );
                rowx.add( String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD18()))) + (empSchedule.getD2nd18()==0 ? "" : "/"+String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD2nd18())))) );
                rowx.add( String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD19()))) + (empSchedule.getD2nd19()==0 ? "" : "/"+String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD2nd19())))) );
                rowx.add( String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD20()))) + (empSchedule.getD2nd20()==0 ? "" : "/"+String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD2nd20())))) );
                rowx.add( String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD21()))) + (empSchedule.getD2nd21()==0 ? "" : "/"+String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD2nd21())))) );
                rowx.add( String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD22()))) + (empSchedule.getD2nd22()==0 ? "" : "/"+String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD2nd22())))) );
                rowx.add( String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD23()))) + (empSchedule.getD2nd23()==0 ? "" : "/"+String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD2nd23())))) );
                rowx.add( String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD24()))) + (empSchedule.getD2nd24()==0 ? "" : "/"+String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD2nd24())))) );
                rowx.add( String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD25()))) + (empSchedule.getD2nd25()==0 ? "" : "/"+String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD2nd25())))) );
                rowx.add( String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD26()))) + (empSchedule.getD2nd26()==0 ? "" : "/"+String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD2nd26())))) );
                rowx.add( String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD27()))) + (empSchedule.getD2nd27()==0 ? "" : "/"+String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD2nd27())))) );
                rowx.add( String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD28()))) + (empSchedule.getD2nd28()==0 ? "" : "/"+String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD2nd28())))) );
                rowx.add( String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD29()))) + (empSchedule.getD2nd29()==0 ? "" : "/"+String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD2nd29())))) );
                rowx.add( String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD30()))) + (empSchedule.getD2nd30()==0 ? "" : "/"+String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD2nd30())))) );
                rowx.add( String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD31()))) + (empSchedule.getD2nd31()==0 ? "" : "/"+String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD2nd31())))) );																																																																																																																											
                 
                 */
                cell = row.createCell((short) 3); 
                cell.setCellValue( String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD1()))) + (empSchedule.getD2nd1()==0 ? "" : "/"+String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD2nd1())))) );
                cell.setCellStyle(style2);
                
                cell = row.createCell((short) 4);
                cell.setCellValue( String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD2()))) + (empSchedule.getD2nd2()==0 ? "" : "/"+String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD2nd2())))) );
                cell.setCellStyle(style2);
                
                cell = row.createCell((short) 5);
                cell.setCellValue( String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD3()))) + (empSchedule.getD2nd3()==0 ? "" : "/"+String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD2nd3())))) );
                cell.setCellStyle(style2);
                
                cell = row.createCell((short) 6);
                cell.setCellValue( String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD4()))) + (empSchedule.getD2nd4()==0 ? "" : "/"+String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD2nd4())))) );                                
                cell.setCellStyle(style2);
                
                cell = row.createCell((short) 7);
                cell.setCellValue( String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD5()))) + (empSchedule.getD2nd5()==0 ? "" : "/"+String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD2nd5())))) );                                
                cell.setCellStyle(style2);
                
                cell = row.createCell((short) 8);
                cell.setCellValue( String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD6()))) + (empSchedule.getD2nd6()==0 ? "" : "/"+String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD2nd6())))) );                
                cell.setCellStyle(style2);
                
                cell = row.createCell((short) 9);
                cell.setCellValue( String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD7()))) + (empSchedule.getD2nd7()==0 ? "" : "/"+String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD2nd7())))) );                                
                cell.setCellStyle(style2);
                
                cell = row.createCell((short) 10);
                cell.setCellValue( String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD8()))) + (empSchedule.getD2nd8()==0 ? "" : "/"+String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD2nd8())))) );                                
                cell.setCellStyle(style2);
                
                cell = row.createCell((short) 11);
                cell.setCellValue( String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD9()))) + (empSchedule.getD2nd9()==0 ? "" : "/"+String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD2nd9())))) );                                
                cell.setCellStyle(style2);
                
                cell = row.createCell((short) 12);
                cell.setCellValue( String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD10()))) + (empSchedule.getD2nd10()==0 ? "" : "/"+String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD2nd10())))) );                                
                cell.setCellStyle(style2);
                
                cell = row.createCell((short) 13);
                cell.setCellValue( String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD11()))) + (empSchedule.getD2nd11()==0 ? "" : "/"+String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD2nd11())))) );                                
                cell.setCellStyle(style2);
                
                cell = row.createCell((short) 14);
                cell.setCellValue( String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD12()))) + (empSchedule.getD2nd12()==0 ? "" : "/"+String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD2nd12())))) );                                
                cell.setCellStyle(style2);
                
                cell = row.createCell((short) 15);
                cell.setCellValue( String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD13()))) + (empSchedule.getD2nd13()==0 ? "" : "/"+String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD2nd13())))) );                                
                cell.setCellStyle(style2);
                
                cell = row.createCell((short) 16);
                cell.setCellValue( String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD14()))) + (empSchedule.getD2nd14()==0 ? "" : "/"+String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD2nd14())))) );                                
                cell.setCellStyle(style2);
                
                cell = row.createCell((short) 17);
                cell.setCellValue( String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD15()))) + (empSchedule.getD2nd15()==0 ? "" : "/"+String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD2nd15())))) );                                
                cell.setCellStyle(style2);
                
                cell = row.createCell((short) 18);
                cell.setCellValue( String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD16()))) + (empSchedule.getD2nd16()==0 ? "" : "/"+String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD2nd16())))) );                                
                cell.setCellStyle(style2);
                
                cell = row.createCell((short) 19);
                cell.setCellValue( String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD17()))) + (empSchedule.getD2nd17()==0 ? "" : "/"+String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD2nd17())))) );                
                cell.setCellStyle(style2);
                
                cell = row.createCell((short) 20);
                cell.setCellValue( String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD18()))) + (empSchedule.getD2nd18()==0 ? "" : "/"+String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD2nd18())))) );                                
                cell.setCellStyle(style2);
                
                cell = row.createCell((short) 21);
                cell.setCellValue( String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD19()))) + (empSchedule.getD2nd19()==0 ? "" : "/"+String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD2nd19())))) );                                
                cell.setCellStyle(style2);
                
                cell = row.createCell((short) 22);
                cell.setCellValue( String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD20()))) + (empSchedule.getD2nd20()==0 ? "" : "/"+String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD2nd20())))) );                                
                cell.setCellStyle(style2);
                
                cell = row.createCell((short) 23);
                cell.setCellValue( String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD21()))) + (empSchedule.getD2nd21()==0 ? "" : "/"+String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD2nd21())))) );                                
                cell.setCellStyle(style2);
                
                cell = row.createCell((short) 24);
                cell.setCellValue( String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD22()))) + (empSchedule.getD2nd22()==0 ? "" : "/"+String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD2nd22())))) );                                
                cell.setCellStyle(style2);
                
                cell = row.createCell((short) 25);
                cell.setCellValue( String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD23()))) + (empSchedule.getD2nd23()==0 ? "" : "/"+String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD2nd23())))) );                                
                cell.setCellStyle(style2);
                
                cell = row.createCell((short) 26);
                cell.setCellValue( String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD24()))) + (empSchedule.getD2nd24()==0 ? "" : "/"+String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD2nd24())))) );                                
                cell.setCellStyle(style2);
                
                cell = row.createCell((short) 27);
                cell.setCellValue( String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD25()))) + (empSchedule.getD2nd25()==0 ? "" : "/"+String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD2nd25())))) );                                
                cell.setCellStyle(style2);
                
                cell = row.createCell((short) 28);
                cell.setCellValue( String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD26()))) + (empSchedule.getD2nd26()==0 ? "" : "/"+String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD2nd26())))) );                                
                cell.setCellStyle(style2);
                
                cell = row.createCell((short) 29);
                cell.setCellValue( String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD27()))) + (empSchedule.getD2nd27()==0 ? "" : "/"+String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD2nd27())))) );                                
                cell.setCellStyle(style2);
                
                cell = row.createCell((short) 30);
                cell.setCellValue( String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD28()))) + (empSchedule.getD2nd28()==0 ? "" : "/"+String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD2nd28())))) );                                
                cell.setCellStyle(style2);
                
                cell = row.createCell((short) 31);
                cell.setCellValue( String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD29()))) + (empSchedule.getD2nd20()==0 ? "" : "/"+String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD2nd29())))) );                                
                cell.setCellStyle(style2);
                
                cell = row.createCell((short) 32);
                cell.setCellValue( String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD30()))) + (empSchedule.getD2nd30()==0 ? "" : "/"+String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD2nd30())))) );                                
                cell.setCellStyle(style2);
                
                cell = row.createCell((short) 33);
                cell.setCellValue( String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD31()))) + (empSchedule.getD2nd31()==0 ? "" : "/"+String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD2nd31())))) );                                
                cell.setCellStyle(style2);
               
            }
            
        }
        else
        {
            
            //String d = "";
            for (int i = 0; i < listEmpSchedule.size(); i++) {
                Vector temp = (Vector) listEmpSchedule.get(i);
                Employee employee = (Employee) temp.get(1);
                Period period = (Period) temp.get(2);
                EmpSchedule empSchedule = (EmpSchedule)temp.get(0);
                //System.out.println(period.getPeriod());
                //System.out.println(employee.getFullName());
                //System.out.println(scheduleSymbol.get(String.valueOf(empSchedule.getD21())));
                row = sheet.createRow((short) (i+3));

                cell = row.createCell((short) 0);
                cell.setCellValue(period.getPeriod());
                cell.setCellStyle(style2);

                cell = row.createCell((short) 1);
                cell.setCellValue(employee.getFullName());
                cell.setCellStyle(style2);

                cell = row.createCell((short) 2);
                cell.setCellValue(employee.getEmployeeNum());
                cell.setCellStyle(style2);
                
                cell = row.createCell((short) 3); 
                cell.setCellValue(String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD21()))));
                cell.setCellStyle(style2);
                cell = row.createCell((short) 4);
                cell.setCellValue(String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD22()))));
                cell.setCellStyle(style2);
                cell = row.createCell((short) 5);
                cell.setCellValue(String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD23()))));
                cell.setCellStyle(style2);
                cell = row.createCell((short) 6);
                cell.setCellValue(String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD24()))));
                cell.setCellStyle(style2);
                cell = row.createCell((short) 7);
                cell.setCellValue(String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD25()))));
                cell.setCellStyle(style2);
                cell = row.createCell((short) 8);
                cell.setCellValue(String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD26()))));
                cell.setCellStyle(style2);
                cell = row.createCell((short) 9);
                cell.setCellValue(String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD27()))));
                cell.setCellStyle(style2);
                cell = row.createCell((short) 10);
                cell.setCellValue(String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD28()))));
                cell.setCellStyle(style2);
                cell = row.createCell((short) 11);
                cell.setCellValue(String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD29()))));
                cell.setCellStyle(style2);
                cell = row.createCell((short) 12);
                cell.setCellValue(String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD30()))));
                cell.setCellStyle(style2);
                cell = row.createCell((short) 13);
                cell.setCellValue(String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD31()))));
                cell.setCellStyle(style2);
                cell = row.createCell((short) 14);
                cell.setCellValue(String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD1()))));
                cell.setCellStyle(style2);
                cell = row.createCell((short) 15);
                cell.setCellValue(String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD2()))));
                cell.setCellStyle(style2);
                cell = row.createCell((short) 16);
                cell.setCellValue(String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD3()))));
                cell.setCellStyle(style2);
                cell = row.createCell((short) 17);
                cell.setCellValue(String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD4()))));
                cell.setCellStyle(style2);
                cell = row.createCell((short) 18);
                cell.setCellValue(String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD5()))));
                cell.setCellStyle(style2);
                cell = row.createCell((short) 19);
                cell.setCellValue(String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD6()))));
                cell.setCellStyle(style2);
                cell = row.createCell((short) 20);
                cell.setCellValue(String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD7()))));
                cell.setCellStyle(style2);
                cell = row.createCell((short) 21);
                cell.setCellValue(String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD8()))));
                cell.setCellStyle(style2);
                cell = row.createCell((short) 22);
                cell.setCellValue(String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD9()))));
                cell.setCellStyle(style2);
                cell = row.createCell((short) 23);
                cell.setCellValue(String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD10()))));
                cell.setCellStyle(style2);
                cell = row.createCell((short) 24);
                cell.setCellValue(String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD11()))));
                cell.setCellStyle(style2);
                cell = row.createCell((short) 25);
                cell.setCellValue(String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD12()))));
                cell.setCellStyle(style2);
                cell = row.createCell((short) 26);
                cell.setCellValue(String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD13()))));
                cell.setCellStyle(style2);
                cell = row.createCell((short) 27);
                cell.setCellValue(String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD14()))));
                cell.setCellStyle(style2);
                cell = row.createCell((short) 28);
                cell.setCellValue(String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD15()))));
                cell.setCellStyle(style2);
                cell = row.createCell((short) 29);
                cell.setCellValue(String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD16()))));
                cell.setCellStyle(style2);
                cell = row.createCell((short) 30);
                cell.setCellValue(String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD17()))));
                cell.setCellStyle(style2);
                cell = row.createCell((short) 31);
                cell.setCellValue(String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD18()))));
                cell.setCellStyle(style2);
                cell = row.createCell((short) 32);
                cell.setCellValue(String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD19()))));
                cell.setCellStyle(style2);
                cell = row.createCell((short) 33);
                cell.setCellValue(String.valueOf(scheduleSymbol.get(String.valueOf(empSchedule.getD20()))));
                cell.setCellStyle(style2);
                
                
            }
            
        }
        
        
        // Write the output to a file
        //FileOutputStream fileOut = new FileOutputStream("workbook.xls");
        //wb.write(fileOut);
        //fileOut.close();        
        ServletOutputStream sos = response.getOutputStream();
        wb.write(sos);
        sos.close();
    } 

    /** Handles the HTTP <code>GET</code> method.
    * @param request servlet request
    * @param response servlet response
    */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, java.io.IOException {
        processRequest(request, response);
    } 

    /** Handles the HTTP <code>POST</code> method.
    * @param request servlet request
    * @param response servlet response
    */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, java.io.IOException {
        processRequest(request, response);
    }

    /** Returns a short description of the servlet.
    */
    public String getServletInfo() {
        return "Short description";
    }

}
