/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.report;

import com.dimata.harisma.entity.employee.Employee;
import com.dimata.harisma.entity.employee.PstEmployee;
import com.dimata.harisma.entity.masterdata.MappingPosition;
import com.dimata.harisma.entity.masterdata.Position;
import com.dimata.harisma.entity.masterdata.PstMappingPosition;
import com.dimata.harisma.entity.masterdata.PstPosition;
import com.dimata.qdep.form.FRMQueryString;
import java.util.Vector;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

/**
 *
 * @author Dimata 007
 */
public class StructureListXLS extends HttpServlet {
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

    }

    public void destroy() {
        
    }
    
    public String getPositionName(long posId) {
        String position = "";
        Position pos = new Position();
        try {
            pos = PstPosition.fetchExc(posId);
        } catch (Exception ex) {
            System.out.println("getPositionName ==> " + ex.toString());
        }
        position = pos.getPosition();
        return position;
    }
    
    public String getEmployeePayroll(long posId, long divId) {
        String empPayroll = "";
        String whereClause = PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID] + "=" + posId;
        if (divId > 0) {
            whereClause += " AND " + PstEmployee.fieldNames[PstEmployee.FLD_DIVISION_ID] + "=" + divId;
        }
        Vector listEmp = PstEmployee.list(0, 0, whereClause, "");
        if (listEmp != null && listEmp.size() > 0) {
            for (int i = 0; i < listEmp.size(); i++) {
                Employee emp = (Employee) listEmp.get(i);
                empPayroll = emp.getEmployeeNum();
            }
        } else {
            empPayroll = "0000";
        }
        return empPayroll;
    }
    
    public String getEmployeeName(long posId, long divId) {
        String empName = "";
        String whereClause = PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID] + "=" + posId;
        if (divId > 0) {
            whereClause += " AND " + PstEmployee.fieldNames[PstEmployee.FLD_DIVISION_ID] + "=" + divId;
        }
        Vector listEmp = PstEmployee.list(0, 0, whereClause, "");
        if (listEmp != null && listEmp.size() > 0) {
            for (int i = 0; i < listEmp.size(); i++) {
                Employee emp = (Employee) listEmp.get(i);
                empName = emp.getFullName();
            }
        } else {
            empName = "Kosong";
        }
        return empName;
    }
    
       
    public Vector getViewPrint(long oidPosition, long oidTemplate, long oidDivision, Vector list) {
        String str = "";
        String whereClause = PstMappingPosition.fieldNames[PstMappingPosition.FLD_UP_POSITION_ID] + "=" + oidPosition;
        whereClause += " AND " + PstMappingPosition.fieldNames[PstMappingPosition.FLD_TEMPLATE_ID] + "=" + oidTemplate;
        Vector listDown = PstMappingPosition.list(0, 0, whereClause, "");
        if (listDown != null && listDown.size() > 0) {
            for (int i = 0; i < listDown.size(); i++) {
                MappingPosition pos = (MappingPosition) listDown.get(i);
                String[] data = new String[4];
                data[0] = getPositionName(oidPosition);
                data[1] = getPositionName(pos.getDownPositionId());
                data[2] = getEmployeePayroll(pos.getDownPositionId(), oidDivision);
                data[3] = getEmployeeName(pos.getDownPositionId(), oidDivision);
                list.add(data);
                list = getViewPrint(pos.getDownPositionId(), oidTemplate, oidDivision, list);
            }
        }
        return list;
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException {
        System.out.println("---===| Excel Report |===---");
        response.setContentType("application/x-msexcel");

        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("Struktur Organisasi");

        HSSFCellStyle style1 = wb.createCellStyle();
        style1.setFillBackgroundColor(new HSSFColor.BLUE_GREY().getIndex());
        style1.setFillForegroundColor(new HSSFColor.BLUE_GREY().getIndex());
        style1.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style1.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style1.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style1.setBorderRight(HSSFCellStyle.BORDER_THIN);
        
        HSSFCellStyle style2 = wb.createCellStyle();
        style2.setFillBackgroundColor(new HSSFColor.WHITE().getIndex());
        style2.setFillForegroundColor(new HSSFColor.WHITE().getIndex());
        style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
        
        
        
        HSSFRow row = sheet.createRow((short) 0);
        HSSFCell cell = row.createCell((short) 0);

        long positionId = FRMQueryString.requestLong(request, "parent_main");
        long templateId = FRMQueryString.requestLong(request, "template_id");
        long divisionId = FRMQueryString.requestLong(request, "division_id");
                
        int countRow = 0;
        Vector list = new Vector();
        Vector dataList = getViewPrint(positionId, templateId, divisionId, list);
        
        if (dataList != null && dataList.size()>0){
            row = sheet.createRow((short) (countRow));
            cell = row.createCell((short) 0);
            cell.setCellValue("Atasan");
            cell.setCellStyle(style1);
            cell = row.createCell((short) 1);
            cell.setCellValue("Position");
            cell.setCellStyle(style1);
            cell = row.createCell((short) 2);
            cell.setCellValue("Payroll");
            cell.setCellStyle(style1);
            cell = row.createCell((short) 3);
            cell.setCellValue("Full Name");
            cell.setCellStyle(style1);
            for(int i=0; i<dataList.size(); i++){
                countRow++;
                String[] data = (String[])dataList.get(i);
                row = sheet.createRow((short) (countRow));
                cell = row.createCell((short) 0);
                cell.setCellValue(data[0]);
                cell.setCellStyle(style2);
                cell = row.createCell((short) 1);
                cell.setCellValue(data[1]);
                cell.setCellStyle(style2);
                cell = row.createCell((short) 2);
                cell.setCellValue(data[2]);
                cell.setCellStyle(style2);
                cell = row.createCell((short) 3);
                cell.setCellValue(data[3]);
                cell.setCellStyle(style2);
            }
        }
        countRow++;
        row = sheet.createRow((short) (countRow));
        cell = row.createCell((short) 0);
        cell.setCellValue("Position Available");
        cell.setCellStyle(style2);
        
        
        ServletOutputStream sos = response.getOutputStream();
        wb.write(sos);
        sos.close();
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException {
        processRequest(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException {
        processRequest(request, response);
    }

    public String getServletInfo() {
        return "Short description";
    }
    
}
