<%-- 
    Document   : up_sch_process_mfull_responsive
    Created on : 08-Jul-2017, 09:28:20
    Author     : Gunadi
--%>

<%@page import="com.dimata.system.entity.system.PstSystemProperty"%>
<%@page import="org.apache.poi.poifs.filesystem.POIFSFileSystem"%>
<%@ page language = "java" %>

<%@ page import="java.util.*"%>
<%@ page import="java.lang.System"%>

<%@ page import="java.io.*"%>
<%@ page import="java.io.IOException"%>
<%@ page import="java.io.ByteArrayInputStream"%>

<%@ page import="org.apache.poi.hssf.usermodel.*" %>

<%@ page import="com.dimata.util.Excel"%>
<%@ page import="com.dimata.util.Formater"%>
<%@ page import="com.dimata.util.blob.TextLoader" %>
<%@ page import="com.dimata.util.*" %>
<%@ page import="com.dimata.gui.jsp.*" %>
<%@ page import="com.dimata.qdep.form.*" %>
<%@ page import="com.dimata.harisma.session.leave.*" %>

<%@ page import="com.dimata.harisma.entity.masterdata.*" %>
<%@ page import="com.dimata.harisma.entity.employee.*" %>
<%@ page import="com.dimata.harisma.entity.attendance.*" %>
<%@ page import="com.dimata.harisma.entity.leave.PstDpApplication" %>
<%@ page import="com.dimata.harisma.entity.leave.PstLeaveApplication" %>
<%@ page import="com.dimata.harisma.form.attendance.CtrlDpStockManagement" %>
<%@ page import="com.dimata.harisma.session.attendance.*" %>
<%@ include file = "../../main/javainit.jsp" %>

<% int appObjCode = 1;//AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_DATABANK, AppObjInfo.OBJ_DATABANK); %>

<%
            long periodIdSelected = FRMQueryString.requestLong(request,"periodup");
            Period periodX = new Period();
            try{
            periodX = PstPeriod.fetchExc(periodIdSelected);
            //period = PstPeriod.fetchExc(periodId);
            } catch(Exception exc){

            }
            int diffDay=31;
           
            int startDatePeriod = Integer.parseInt(String.valueOf(PstSystemProperty.getValueByName("START_DATE_PERIOD")));
           
            if(periodX!=null && periodX.getOID()!=0){
                try{
                    diffDay = PstPresence.DATEDIFF(periodX.getEndDate(),periodX.getStartDate()); 
                }catch(Exception exc){
                    System.out.println("Exception"+exc);
                    diffDay = 31;
                }
                 
            }
            //mencari last date for month
int startDatePeriodX = periodX==null || periodX.getOID()==0 ?  Integer.parseInt(String.valueOf(PstSystemProperty.getValueByName("START_DATE_PERIOD"))) : periodX.getStartDate().getDate() ; 
Date dtCalc = new Date();
Calendar calendar = Calendar.getInstance();
if(periodX!=null && periodX.getStartDate()!=null){
    dtCalc = (Date)periodX.getStartDate().clone();
}

//(Date)srcTransaction.getStartDate().clone();
calendar.setTime(dtCalc);
int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);//mencari tanggal terakhir

         int NUM_HEADER = 2;
	int NUM_CELL = 0;//22;
        int ROW_PERIOD =2;
        
        
        int ROW_DEPARTEMENT =1;
        int COL_DEPARTEMENT_MIN = 0;
        int COL_DEPARTEMENT_MAX = 1;
        
        int COL_PERIOD_MIN = 0;
        int COL_PERIOD_MAX = 1;
         int ROW_CREATE_BY =3;
         int COL_CREATE_BY_MIN = 0;
         int COL_CREATE_BY_MAX = 1;
       
         int ROW_SCH =6;
         int COL_SCH_MIN=2;
         int COL_SCH_MAX=diffDay+2;// +2 artinya di tambah Name dan payrol
       
        
         int ROW_SCH_DATE=5;
         int COL_SCHDATE_MIN=2;
         int COL_SCHDATE_MAX=diffDay+2;// +2 artinya di tambah Name dan payrol
    
         int ROW_EMPLOYEE =7;
         int COL_EMPLOYEE_MIN=0;
         int COL_EMPLOYEE_MAX=diffDay+2;// +2 artinya di tambah Name dan payrol
         
         //int ROW_WORKSCHNAME_MIN =0;
        //int ROW_PAYROLL_NUMBER =1; 
        int COL_EMP_SCH=2; 
        // int ROW_EMPNUMB_MIN =1;
        //int ROW_EMPNUMB_MAX =1; 
        int COL_EMPNUMB=1; 

         //   int NUM_DATE = diffDay;      
           // int NUM_HEADER = 3;
           // int NUM_CELL = -1;
            String SPIT_SHIFT_SEPARATOR = "/";

            // deklarasi jumlah rows yang akan di hapus untuk header dan footer
            int ROWS_NUM_HEADER_DEL = 4;
            int ROWS_NUM_FOOTER_DEL = 9;

            // index for header value
            int IDX_TOP = 0;
            int IDX_DEP = 2;
            int IDX_PER = 4;
            int IDX_CRT = 6;

            String topHeader = "";
            String depHeader = "";
            String depName = "";
            String creatorHeader = "";
            String periodHeader = "";
             String periodName = "";
             String creatorName = "";
             String fullNameExcel="";

            int iCommand = FRMQueryString.requestCommand(request);
            long periodId = FRMQueryString.requestLong(request, "periodup");

            //int intAdvanceDp = 100;
            String msgString = "";
            boolean noErrWithLeaveStock = true;
            Hashtable hashEmployeeError = new Hashtable();
            Hashtable hashLeaveDataError = new Hashtable();
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
            
            double Beforedays =  positionOfLoginUser.getDeadlineScheduleBefore()/24;
            Date deadDay = new Date();//Date today = new Date();
                //mencari batasan harinya (sebelumnya)
            deadDay.setHours(deadDay.getHours() - positionOfLoginUser.getDeadlineScheduleBefore());
            Period periodDead = PstPeriod.getPeriodBySelectedDate(deadDay);
            
            TextLoader uploader = new TextLoader();
            FileOutputStream fOut = null;
            ByteArrayInputStream inStream = null;
            StringBuffer drawList = new StringBuffer();
            Vector vSchedulePerEmp = new Vector(1,1);
            Hashtable schdelueDate = new Hashtable();
            Hashtable schdelueName = new Hashtable();


            // jumlah kolom schedule (employee name, payroll number, 31 column for date schedule 
            int numcol = 33;

            try {
                    uploader.uploadText(config, request, response);
                    Object obj = uploader.getTextFile("FRM_DOC");
                    byte byteText[] = null;
                    byteText = (byte[]) obj;
                    inStream = new ByteArrayInputStream(byteText);
                    Excel tp = new Excel();
                    
                schdelueDate = new Hashtable();
                schdelueName = new Hashtable();
                  EmployeeUpload employeeUpload = new EmployeeUpload();
                 
                Vector headerX = new Vector(); 
                boolean schDate = false;
                int col_name = -1;
                int row_name = -1;
                POIFSFileSystem fs = new POIFSFileSystem(inStream);
                HSSFWorkbook wb = new HSSFWorkbook(fs);
                HSSFSheet sheet = (HSSFSheet)wb.getSheetAt(0);
                int rows = sheet.getPhysicalNumberOfRows();                
                  for (int r =0; r < rows; r++) {
                       Employee employee = null;
                      try{ 
                            employeeUpload = new EmployeeUpload();
	                    HSSFRow row = sheet.getRow(r);
	                    int cells = 0;
                        //if number of cell is static
                        if(NUM_CELL > 0){
                        	cells = NUM_CELL;
                        }
                        //number of cell is dinamyc
                        else{
                            cells = row.getPhysicalNumberOfCells();
                        }

                        // ambil jumlah kolom yang sebenarnya
                        NUM_CELL = cells;
                        
                        String cellColor="";                            
	                    for (int c = 0; c <= cells; c++)
	                    {   
                                cellColor="#CCCCCC";
	                        HSSFCell cell  = row.getCell((short) c);
                                String   value = null;
                                int dtInt = 0;
                                if(cell!=null){
	                        switch (cell.getCellType())
	                        {
	                            case HSSFCell.CELL_TYPE_FORMULA :
	                                //value = "FORMULA ";
	                                value = String.valueOf(cell.getCellFormula());;
	                                break;
	                            case HSSFCell.CELL_TYPE_NUMERIC :
	                                //value = "NUMERIC value=" + cell.getNumericCellValue();
	                                value = String.valueOf(cell.getNumericCellValue());
                                         if(value.endsWith(".0")){
                                         value=value.substring(0, value.length()-2);
                                         };
                                        if(value!=null && value.length()>0){
                                            dtInt = Integer.parseInt(value);
                                        }
                                        if(!schDate && value!=null && (r>=ROW_SCH) && (c>=COL_SCH_MIN && c <= COL_SCH_MAX)){                                             
                                            if(dtInt<=maxDay){ 
                                               schdelueDate.put(""+c, value);   
                                            }
                                         }
	                                break;
                                    case HSSFCell.CELL_TYPE_STRING :
	                                //value = "STRING value=" + cell.getStringCellValue();
	                                value = String.valueOf(cell.getStringCellValue());
                                        
                                        if(value!=null && r==ROW_DEPARTEMENT && c==COL_DEPARTEMENT_MIN){
                                            depHeader = value;
                                        }else if(value!=null && r==ROW_DEPARTEMENT && c==COL_DEPARTEMENT_MAX){
                                            depName = value;
                                        }
                                        //mencari period Name
                                        if(value!=null && r==ROW_PERIOD && c==COL_PERIOD_MIN){
                                            periodHeader = value;
                                        }else if(value!=null && r==ROW_PERIOD && c==COL_PERIOD_MAX){
                                            periodName = value;
                                        }
                                        // mencari create BY
                                        if(value!=null && r==ROW_CREATE_BY && c==COL_CREATE_BY_MIN){
                                            creatorHeader = value; 
                                        }else if(value!=null && r==ROW_CREATE_BY && c==COL_CREATE_BY_MAX){
                                            creatorName = value; 
                                        }
                                        // mencari sch date
                                        if(value!=null && r==ROW_SCH_DATE && c>=COL_SCHDATE_MIN && c<=COL_SCHDATE_MAX){
                                            schdelueName.put(""+c, value);  
                                        }
	                                break;                                                                        
	                            default :
                                        value = String.valueOf(cell.getStringCellValue()!=null?cell.getStringCellValue():"");
                                        ;
	                        }
                                 try{ // search for the row containts the first employee data
                                if(value!=null && r>=ROW_EMPLOYEE && c>=COL_EMPLOYEE_MIN && c<=COL_EMPLOYEE_MAX && value!=null){
                                    
                                    if(c==COL_EMPLOYEE_MIN){
                                        //mencari namanya
                                        fullNameExcel = value;
                                    }
                                     //mencari employee berdasarkan employee_number
                                   if(employee==null && c==COL_EMPNUMB){
                                       employee = PstEmployee.getEmployeeByNum(value);
                                     if(employee!=null){
                                       employeeUpload.setEmpNumberExcel(value);
                                       employeeUpload.setEmpId(employee.getOID());
                                       employeeUpload.setEmpName(employee.getFullName());
                                       employeeUpload.setEmpNumb(employee.getEmployeeNum());
                                       employeeUpload.setDeptName(depName);
                                       employeeUpload.setEmpNameKeyPayrol(fullNameExcel);
                                       employeeUpload.setCreatorName(creatorName); 
                                     }else{
                                       employee = PstEmployee.getEmployeeByFullName(fullNameExcel);
                                       employeeUpload.setEmpNumberExcel(value);
                                      if(employee!=null){
                                       employeeUpload.setEmpId(employee.getOID());
                                       employeeUpload.setEmpName(employee.getFullName());
                                       employeeUpload.setEmpNumb(employee.getEmployeeNum());
                                      }else{
                                       employeeUpload.setEmpId(0);
                                       employeeUpload.setEmpName(null);
                                       employeeUpload.setEmpNumb(null);
                                       employee = new Employee(); 
                                      } 
                                       employeeUpload.setDeptName(depName);
                                       employeeUpload.setEmpNameKeyPayrol(fullNameExcel);
                                       employeeUpload.setCreatorName(creatorName); 
                                     }
                                   }
                                   if(employee!=null && c>=COL_EMP_SCH){
                                       employeeUpload.addSchedule(c, value);
                                   }
                                 } 
                                } catch(Exception exc){
                                    System.out.println("r="+r+" c="+c+" "+exc);
                                }
                            }
                        
                         }
                      }catch(Exception exc){
                            System.out.println("Exception"+exc); 
                        }
                      //prosess menampung schedule input karyawan
                     if(employeeUpload!=null && employeeUpload.getSchedule()!=null && employeeUpload.getSchedule().size()>0){
                       vSchedulePerEmp.add(employeeUpload); 
                      }
                  }
                    // proses data vector hasil parsing ke SESSION
                    if (session.getValue("WORK_SCHEDULE") != null) {
                        session.removeValue("WORK_SCHEDULE");
                        session.removeValue("SCHEDULE_NAME");
                        session.removeValue("SCHEDULE_DATE");
                        session.removeValue("CREATOR_NAME");
                        session.removeValue("DEPARTEMENT_NAME");
                               
                    }
                    session.putValue("WORK_SCHEDULE", vSchedulePerEmp);
                    session.putValue("SCHEDULE_NAME", schdelueName);
                    session.putValue("SCHEDULE_DATE", schdelueDate);
                    session.putValue("CREATOR_NAME", creatorName);
                    session.putValue("DEPARTEMENT_NAME", depName);
                    

                } catch (Exception exc){}
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <script>
            window.location="<%=approot%>/employee/attendance/srcempscheduleNew.jsp?period_id=<%=periodIdSelected%>&command=<%=Command.LIST%>";
        </script>
    </body>
</html>
