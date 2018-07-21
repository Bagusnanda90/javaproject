/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.session.payroll;

import com.dimata.harisma.entity.employee.Employee;
import com.dimata.harisma.entity.employee.PstEmployee;
import com.dimata.harisma.entity.log.ChangeValue;
import com.dimata.harisma.entity.overtime.OvertimeDetail;
import com.dimata.harisma.entity.overtime.PstOvertimeDetail;
import com.dimata.util.Formater;
import com.dimata.util.blob.TextLoader;
import java.io.ByteArrayInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspWriter;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

/**
 *
 * @author mchen
 */
public class ImportOvertimeDetail {
    public void drawImport(ServletConfig config, HttpServletRequest request, HttpServletResponse response, JspWriter output, long oidOvertime){
        String html = "";
        int NUM_HEADER = 2;
        int NUM_CELL = 0;

        ChangeValue changeValue = new ChangeValue();
        String tdColor = "#FFF;";
        try {
            TextLoader uploader = new TextLoader();
            ByteArrayInputStream inStream = null;

            uploader.uploadText(config, request, response);
            Object obj = uploader.getTextFile("file");
            byte byteText[] = null;
            byteText = (byte[]) obj;
            inStream = new ByteArrayInputStream(byteText);

            POIFSFileSystem fs = new POIFSFileSystem(inStream);

            HSSFWorkbook wb = new HSSFWorkbook(fs);
            System.out.println("creating workbook");

            int numOfSheets = wb.getNumberOfSheets();
            for (int i = 0; i < numOfSheets; i++) {
                int r = 0;
                HSSFSheet sheet = (HSSFSheet) wb.getSheetAt(i);
                output.print("<strong> Sheet name : " + sheet.getSheetName() + "</strong></div>");
                if (sheet == null || sheet.getSheetName() == null || sheet.getSheetName().length() < 1) {
                    output.print(" NOT MATCH : Period name and sheet name ");
                    continue;
                }
                    int rows = sheet.getPhysicalNumberOfRows();

                    // loop untuk row dimulai dari numHeaderRow (0, .. numHeaderRow diabaikan) => untuk yang bukan sheet pertaman
                    int start = (i == 0) ? 0 : NUM_HEADER;
                    String empNum = "";
                    output.print("<table class=\"tblStyle\">");
                    String[][] dataDetail = new String[rows][7];
                    for (r = start; r < rows; r++) {
                        Employee employee = null;
                        try {
                            HSSFRow row = sheet.getRow(r);
                            int cells = 0;
                            //if number of cell is static
                            if (NUM_CELL > 0) {
                                cells = NUM_CELL;
                            } else { //number of cell is dinamyc
                                cells = row.getPhysicalNumberOfCells();
                            }
                            tdColor = "#FFF;";
                            // ambil jumlah kolom yang sebenarnya
                            NUM_CELL = cells;
                            output.print("<tr>");
                            int caseValue = 0;
                            for (int c = 0; c < cells; c++) {
                                HSSFCell cell = row.getCell((short) c);
                                String value = null;
                                if (cell != null) {
                                    /* proses mem-filter value */
                                    switch (cell.getCellType()) {
                                        case HSSFCell.CELL_TYPE_FORMULA:
                                            value = String.valueOf(cell.getCellFormula());
                                            caseValue = 1;
                                            break;
                                        case HSSFCell.CELL_TYPE_NUMERIC:
                                            value = Formater.formatNumber(cell.getNumericCellValue(), "###");
                                            caseValue = 2;
                                            break;
                                        case HSSFCell.CELL_TYPE_STRING:
                                            value = String.valueOf(cell.getStringCellValue());
                                            caseValue = 3;
                                            break;
                                        default:
                                            value = String.valueOf(cell.getStringCellValue() != null ? cell.getStringCellValue() : "");
                                    }
                                }

                                /* Ambil data employee num */
                                if (caseValue == 3 && c == 1 && r > 0){ /* colom ini adalah employee number */
                                    empNum = value;
                                }
                                /* Ambil data Employee */
                                if (empNum.length()>0 && r > 0 && c == 1){
                                    try {
                                        employee = PstEmployee.getEmployeeByNum(empNum);
                                        value = "("+ empNum + ") "+ employee.getFullName();
                                        dataDetail[r][c] = String.valueOf(employee.getOID());
                                    } catch(Exception e){
                                        System.out.println("emp num is not available=>"+e.toString());
                                    }
                                    /* change color if nothing employee with emp num */
                                    if (employee == null){
                                        tdColor = "#fde1e8;";
                                    }
                                }
                                
                                if (r > 0 && c > 1){
                                    dataDetail[r][c] = value;
                                }
                                
                                
                                /* Proses menampilkan data ke html table */
                                if (r == 0){ /* Baris Header table */
                                    output.print("<td style=\"background-color:#DDD;\"><strong>"+ value + "</strong></td>");
                                } else {
                                    if (value.equals("NULL")){
                                        output.print("<td style=\"background-color:"+tdColor+"\">-</td>");
                                    } else {
                                        output.print("<td style=\"background-color:"+tdColor+"\">"+value+"</td>");
                                    }
                                }
                                
                                
                            } /*End For Cols*/
                            
                            
                            output.print("</tr>");
                        } catch (Exception e) {
                            System.out.println("=> Can't get data ..sheet : " + i + ", row : " + r + ", => Exception e : " + e.toString());
                        }
                    } //end of sheet
                    output.print("</table>");
                    output.print("<div>&nbsp;</div>");
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    
                    for (int inc=1; inc < rows; inc++){
                        OvertimeDetail ovrDetail = new OvertimeDetail();
                        ovrDetail.setOvertimeId(oidOvertime);
                        ovrDetail.setEmployeeId(Long.valueOf(dataDetail[inc][1]));
                        Date dateFrom = sdf.parse(dataDetail[inc][2]+" "+dataDetail[inc][3]);
                        ovrDetail.setDateFrom(dateFrom);
                        Date dateTo = sdf.parse(dataDetail[inc][4]+" "+dataDetail[inc][5]);
                        ovrDetail.setDateTo(dateTo);
                        ovrDetail.setJobDesk(dataDetail[inc][6]);
                        try {
                            PstOvertimeDetail overtimeDetail = new PstOvertimeDetail();
                            overtimeDetail.insertExc(ovrDetail);
                        } catch(Exception e){
                            System.out.println(e.toString());
                        }
                    }
                    
                    
            } //end of all sheets
            
        } catch (Exception e) {
            System.out.println("---=== Error : ReadStream ===---\n" + e);
        }
    }
}
