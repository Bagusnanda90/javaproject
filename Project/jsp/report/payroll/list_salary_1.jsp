
<%@ page language="java" %>

<!-- package qdep -->
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>
<%@ page import = "com.dimata.qdep.db.*" %>
<!-- package dimata -->
<%@ page import = "com.dimata.util.*" %>
<%@ page import = "com.dimata.qdep.db.*"%>;
<%@ page import = "java.sql.ResultSet" %>
<%@ page import = "com.dimata.harisma.session.payroll.*" %>



<!-- package java -->
<%@ page import ="java.util.*,
                  com.dimata.harisma.entity.payroll.Query,
				  com.dimata.harisma.entity.payroll.PayAdditional,
				  com.dimata.harisma.entity.payroll.PstPayAdditional,
				  com.dimata.harisma.form.payroll.CtrlPayAdditional,
				  com.dimata.harisma.form.payroll.FrmPayAdditional,
                  com.dimata.harisma.entity.payroll.PstQuery,
				  com.dimata.harisma.entity.payroll.SalaryLevel,
				  com.dimata.harisma.entity.payroll.PstSalaryLevel,
				  com.dimata.harisma.entity.payroll.PayEmpLevel,
				  com.dimata.harisma.entity.payroll.PstPayEmpLevel,
				  com.dimata.harisma.entity.payroll.PayComponent,
				  com.dimata.harisma.entity.payroll.PstPayComponent,
				  com.dimata.harisma.entity.payroll.PaySlip,
				  com.dimata.harisma.entity.payroll.PstPaySlip,
				  com.dimata.harisma.entity.payroll.PstSalaryLevelDetail,
				  com.dimata.harisma.entity.payroll.PaySlipComp,
				  com.dimata.harisma.entity.payroll.PstPaySlipComp,
                  com.dimata.harisma.form.payroll.FrmQuery,
				  com.dimata.harisma.form.payroll.CtrlQuery,
				  com.dimata.harisma.entity.payroll.PstPayEmpLevel,
	              com.dimata.harisma.entity.employee.Employee,
				  com.dimata.harisma.session.employee.SessEmployee,
                  com.dimata.harisma.entity.masterdata.PstDepartment,
                  com.dimata.harisma.entity.employee.PstEmployee"%>
<!-- package qdep -->
<%@ include file = "../../main/javainit.jsp" %>
<!-- JSP Block -->
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_REPORTS, AppObjInfo.G2_LATENESS_REPORT, AppObjInfo.OBJ_LATENESS_MONTHLY_REPORT); %>
<%@ include file = "../../main/checkuser.jsp" %>

<%
/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
//boolean privPrint 	= userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_PRINT));
%>
<%!

String codeMealAllowance="TJ.MAKAN";
String codeLateDeduc ="P.LATE/ABSENCE";
String codeLateDeducSatpam = "P.SATPAM";
String codeSukaDukaDeduc="P.SUKA DUKA";

public Vector drawList(Vector objectClass,String levelCode,long oidPeriod,Vector vectDept,long sectionId,int transfered)
	{
		Vector result = new Vector(1,1);
		Vector vectCompBenefit= new Vector(1,1);
		Vector vectCompDeduction= new Vector(1,1);
		ControlList ctrlist = new ControlList();
		ctrlist.setAreaWidth("100%");
		ctrlist.setListStyle("listgen");
		ctrlist.setTitleStyle("listgentitle");
		ctrlist.setCellStyle("listgensell");
		ctrlist.setHeaderStyle("listgentitle");
		ctrlist.addHeader("NO","2%","0","0");
		ctrlist.addHeader("NAME","5%","0","0");
		ctrlist.addHeader("EMP.CATEGORY","5%","0","0");
		ctrlist.addHeader("RELIGION","5%","0","0");
		ctrlist.addHeader("COMM.DATE","5%","0","0");
		ctrlist.addHeader("SECTION","5%","0","0");
		//header dinamis(benefit)
		String whereB = "slip."+PstSalaryLevelDetail.fieldNames[PstSalaryLevelDetail.FLD_TAKE_HOME_PAY]+"="+PstSalaryLevelDetail.YES_TAKE+
					   (!levelCode.trim().equals("0")? " AND "+PstSalaryLevelDetail.fieldNames[PstSalaryLevelDetail.FLD_LEVEL_CODE]+"='"+levelCode+"'" :" ")+
					   " AND "+PstPayComponent.fieldNames[PstPayComponent.FLD_TAX_ITEM]+"="+PstPayComponent.GAJI;
		// header dinamis ( deduction )
		String whereD = "slip."+PstSalaryLevelDetail.fieldNames[PstSalaryLevelDetail.FLD_TAKE_HOME_PAY]+"="+PstSalaryLevelDetail.YES_TAKE+
					   (!levelCode.trim().equals("0")? " AND "+PstSalaryLevelDetail.fieldNames[PstSalaryLevelDetail.FLD_LEVEL_CODE]+"='"+levelCode+"'":" ")+
					   " AND "+PstPayComponent.fieldNames[PstPayComponent.FLD_TAX_ITEM]+"!="+PstPayComponent.GAJI;			  
		String order = "PAY."+PstPayComponent.fieldNames[PstPayComponent.FLD_COMP_TYPE] + ", PAY."+PstPayComponent.fieldNames[PstPayComponent.FLD_SORT_IDX];
		Vector headCompB = PstPayComponent.getManualComponent(whereB,order);
		if(headCompB !=null && headCompB.size() >0){
			for(int b=0;b<headCompB.size();b++){
				PayComponent payComp = (PayComponent)headCompB.get(b);
				ctrlist.addHeader(""+payComp.getCompName().toUpperCase(),"5%","0","0");
				vectCompBenefit.add(payComp.getCompName());
			}
		}
		ctrlist.addHeader("SALARY","5%","0","0");
		//header deduction
		Vector headCompD = PstPayComponent.getManualComponent(whereD,order);
		if(headCompD !=null && headCompD.size() >0){
			for(int d=0;d<headCompD.size();d++){
				PayComponent payComp = (PayComponent)headCompD.get(d);
				ctrlist.addHeader(""+payComp.getCompName().toUpperCase(),"5%","0","0");
				vectCompDeduction.add(payComp.getCompName());
			}
		}
		ctrlist.addHeader("NON SALARY","5%","0","0");
		ctrlist.addHeader("TAKE HOME PAY","5%","0","0");
		ctrlist.setLinkRow(0);
		ctrlist.setLinkSufix("");
		Vector lstData = ctrlist.getData();
		Vector vectDataToPdf = new Vector(1,1);
		Vector vectFooter = new Vector(1,1);
		
		String whereClause = "";
		String frmCurrency = "#,###";
		//mengambil content dari query
		Vector lstLinkData = ctrlist.getLinkData();
		ctrlist.setLinkPrefix("javascript:cmdEdit('");
		ctrlist.setLinkSufix("')");
		ctrlist.reset();
		int index = -1;
		double totalTakeHomePay = 0;
                
	    double sumDeduction = 0;
		for (int i = 0; i <=objectClass.size(); i++) {
			if(i==objectClass.size()){
			//untuk footer
				 Vector rowx = new Vector();
				  Vector rowxPdf = new Vector();
				  rowx.add("&nbsp;");
				  rowxPdf.add("");
				  rowx.add("<b>Total</b>");
				  rowxPdf.add("Total");
				  rowx.add("&nbsp;");
				  rowxPdf.add("");
				  rowx.add("&nbsp;");
				  rowxPdf.add("");
				  rowx.add("&nbsp;");
				  rowxPdf.add("");
				  rowx.add("&nbsp;");
				  rowxPdf.add("");
				 double sumBenefit=0;
				//untuk footer Benefit
				 for(int b=0;b<headCompB.size();b++){
				 	 PayComponent payComp = (PayComponent)headCompB.get(b);
					 double sumComponent = PstPaySlipComp.getSumComponentValue(payComp.getCompCode(),oidPeriod,levelCode,vectDept,sectionId,transfered);
					 rowx.add("<b><div align=\"right\">"+Formater.formatNumber(sumComponent, frmCurrency)+"</div></b>");
					 rowxPdf.add(""+Formater.formatNumber(sumComponent, frmCurrency));
					 sumBenefit = sumBenefit + sumComponent;
				 }
				 rowx.add("<b><div align=\"right\">"+Formater.formatNumber(sumBenefit, frmCurrency)+"</div></b>");
				 rowxPdf.add(""+Formater.formatNumber(sumBenefit, frmCurrency));
				 for(int d=0;d<headCompD.size();d++){
					PayComponent payComp = (PayComponent)headCompD.get(d);
					double sumComponent = PstPaySlipComp.getSumComponentValue(payComp.getCompCode(),oidPeriod,levelCode,vectDept,sectionId,transfered);
					rowx.add("<b><div align=\"right\">"+Formater.formatNumber(sumComponent, frmCurrency)+"</div></b>");
					rowxPdf.add(""+Formater.formatNumber(sumComponent, frmCurrency));
					//sumDeduction = sumDeduction + sumComponent;
				 }
				 rowx.add("<b><div align=\"right\">"+Formater.formatNumber(sumDeduction, frmCurrency)+"</div></b>");
				 rowxPdf.add(""+Formater.formatNumber(sumDeduction, frmCurrency));
				 rowx.add("<b><div align=\"right\">"+Formater.formatNumber((totalTakeHomePay), frmCurrency)+"</div></b>");
				 rowxPdf.add(""+Formater.formatNumber((totalTakeHomePay), frmCurrency));
				lstData.add(rowx);
			 	vectDataToPdf.add(rowxPdf);
			}else{
			Vector temp = (Vector)objectClass.get(i);
			 Employee employee = (Employee)temp.get(0);
			 PayEmpLevel payEmpLevel = (PayEmpLevel)temp.get(1);
			 Religion religion = (Religion)temp.get(2);
			 EmpCategory empCategory = (EmpCategory)temp.get(3);
			 PaySlip paySlip = (PaySlip)temp.get(4);
			 
			 Vector rowx = new Vector();
			 Vector rowxPdf = new Vector();
			 rowx.add(String.valueOf(i+1));
			 rowxPdf.add(String.valueOf(i+1));
			 rowx.add(""+employee.getFullName());
			 rowxPdf.add(""+employee.getFullName());
			 rowx.add(""+empCategory.getEmpCategory());
			 rowxPdf.add(""+empCategory.getEmpCategory());
			 rowx.add(""+religion.getReligion());
			 rowxPdf.add(""+religion.getReligion());
			 rowx.add(""+Formater.formatTimeLocale(employee.getCommencingDate(),"dd-MMM-yyy"));
			 rowxPdf.add(""+Formater.formatTimeLocale(employee.getCommencingDate(),"dd-MMM-yyy"));
			 //fetch section
			 Section section = new Section();
			 try{
			 	section = PstSection.fetchExc(employee.getSectionId());
			 }catch(Exception e){
			 	System.out.println("err fetch section"+e.toString());
			 }
			 
			 rowx.add(""+section.getSection());
			 rowxPdf.add(""+section.getSection());
			 double totBenefit=0;
			
			 //ambil nilai dari pay slip id dengan comp_code tertentu (benefit)
			 if(headCompB !=null && headCompB.size() >0){
				for(int b=0;b<headCompB.size();b++){
					PayComponent payComp = (PayComponent)headCompB.get(b);
					double compValue = PstPaySlipComp.getReportCompValue(paySlip.getOID(),payComp.getCompCode());
					rowx.add("<div align=\"right\">"+Formater.formatNumber(compValue, frmCurrency)+"</div>");
					rowxPdf.add(""+Formater.formatNumber(compValue, frmCurrency));
					totBenefit = totBenefit + compValue;
				}
			}
			rowx.add("<b><div align=\"right\">"+Formater.formatNumber(totBenefit, frmCurrency)+"</div></b>");
			rowxPdf.add(""+Formater.formatNumber(totBenefit, frmCurrency));
 			double totDeduction = 0;
			double totTakeHomeaPay = totBenefit;
			 //ambil nilai dari pay slip id dengan comp_code tertentu (deduction)
			  if(headCompD !=null && headCompD.size() >0){
				for(int d=0;d<headCompD.size();d++){
					PayComponent payComp = (PayComponent)headCompD.get(d);
					double compValue = PstPaySlipComp.getReportCompValue(paySlip.getOID(),payComp.getCompCode());
					rowx.add("<div align=\"right\">"+Formater.formatNumber(compValue, frmCurrency)+"</div>");
					
					if(payComp.getCompType()==PstPayComponent.TYPE_BENEFIT){
						totTakeHomeaPay = totTakeHomeaPay + compValue ;
						totDeduction = totDeduction + compValue;
					}
					else if(payComp.getCompType()==PstPayComponent.TYPE_DEDUCTION){
						totTakeHomeaPay = totTakeHomeaPay - compValue ;
						totDeduction = totDeduction - compValue;
					}
					rowxPdf.add(""+Formater.formatNumber(compValue, frmCurrency));
				}
			}
			
			rowx.add("<b><div align=\"right\">"+Formater.formatNumber(totDeduction, frmCurrency)+"</div><b>");
			rowxPdf.add(""+Formater.formatNumber(totDeduction, frmCurrency));
			rowx.add("<b><div align=\"right\">"+Formater.formatNumber((totTakeHomeaPay), frmCurrency)+"</div><b>");
			rowxPdf.add(""+Formater.formatNumber((totTakeHomeaPay), frmCurrency));
			totalTakeHomePay = totalTakeHomePay + totTakeHomeaPay;
			sumDeduction = sumDeduction + totDeduction;
			lstData.add(rowx);
			vectDataToPdf.add(rowxPdf);
			 }
		}
		result.add(ctrlist.drawList());
		result.add(vectDataToPdf);
		result.add(vectCompBenefit);
		result.add(vectCompDeduction);
		return result;
	}
	
	/**
     * Get vector of component salary   
     * @param periodId
     * @param compCode : code of salary component
     * @param bankOid  : bank oid, bisa null jika tidak diperlukan perbedaan bank oid
     * @param compareStringBankOID  : logica comparasi field BANK_ID dgn bankOid , contoh : = atau <> atau !=  
     * @return
     */
    public static Vector getVectSalaryComp(long periodId, String compCode, Long bankOid, String compareStringBankOID) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT pay.COMP_VALUE,EMPLEV.LEVEL_CODE FROM " + PstPaySlipComp.TBL_PAY_SLIP_COMP + " as pay " +
                    " INNER JOIN " + PstPaySlip.TBL_PAY_SLIP + " as slip" +
                    " ON pay." + PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_PAY_SLIP_ID] +
                    " = slip." + PstPaySlip.fieldNames[PstPaySlip.FLD_PAY_SLIP_ID] +
                    " INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " AS EMP" +
                    " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] +
                    " = SLIP." + PstPaySlip.fieldNames[PstPaySlip.FLD_EMPLOYEE_ID] +
                    " INNER JOIN " + PstPayEmpLevel.TBL_PAY_EMP_LEVEL + " AS EMPLEV" +
                    " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + "=" +
                    " EMPLEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_EMPLOYEE_ID] +
                    " WHERE SLIP." + PstPaySlip.fieldNames[PstPaySlip.FLD_PERIOD_ID] + "=" + periodId +
                    " AND PAY." + PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_COMP_CODE] + "='" + compCode + "'" +
                    " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + "=" + PstEmployee.NO_RESIGN +
                    (bankOid != null ? " AND EMPLEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_BANK_ID] + compareStringBankOID + bankOid.toString() : "") +
                    " GROUP BY SLIP." + PstPaySlip.fieldNames[PstPaySlip.FLD_PAY_SLIP_ID];

            System.out.println("sql PstPaySlipComp.getVectSalaryComp  " + sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            // System.out.println("SQL    "+compType+"-"+sql);
            //System.out.println("masuk:::::::::::: "+rs);
            while (rs.next()) {
                Vector vect = new Vector(1, 1);

                PaySlipComp paySlipComp = new PaySlipComp();
                paySlipComp.setCompValue(rs.getDouble(PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_COMP_VALUE]));
                //resultToObject(rs, paySlipComp);
                vect.add(paySlipComp);

                PayEmpLevel payEmpLevel = new PayEmpLevel();
                payEmpLevel.setLevelCode(rs.getString(PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_LEVEL_CODE]));
                vect.add(payEmpLevel);

                lists.add(vect);

            }
            rs.close();
            return lists;

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return new Vector();
    }	
	
public static double sumCompSalary(long periodId, String compCode, Long bankOid, String compareStringBankOID) {	
        Vector vctCompSal = getVectSalaryComp(periodId, compCode, bankOid, compareStringBankOID);
        double sumComp = 0;
        if (vctCompSal != null && vctCompSal.size() > 0) {
            for (int i = 0; i < vctCompSal.size(); i++) {
                Vector temp = (Vector) vctCompSal.get(i);
                PaySlipComp paySlipComp = (PaySlipComp) temp.get(0);
                PayEmpLevel payEmpLevel = (PayEmpLevel) temp.get(1);
                int cekTakeHomePay = PstSalaryLevelDetail.getTakeHomePay(payEmpLevel.getLevelCode(), compCode);
                if (cekTakeHomePay == PstSalaryLevelDetail.YES_TAKE) {
                    //System.out.println(sumMealAllowance+ "   " +paySlipComp.getCompValue() );
                    sumComp = sumComp + paySlipComp.getCompValue();                    
                }
            }
        }	
		return sumComp;
}

			/*Vector vectSumOther = PstPaySlipComp.getVectOtherDeduction(oidPeriod,payComponent.getCompCode());
			//jumlahkan nilainya
				if(vectSumOther!=null && vectSumOther.size() > 0){
					for (int j = 0; j < vectSumOther.size(); j++) {
						Vector temp = (Vector)vectSumOther.get(j);
						PaySlipComp paySlipComp = (PaySlipComp)temp.get(0);
						PayEmpLevel payEmpLevel = (PayEmpLevel)temp.get(1);
						int cekTakeHomePay = PstSalaryLevelDetail.getTakeHomePay(payEmpLevel.getLevelCode(),payComponent.getCompCode());
						if(cekTakeHomePay==PstSalaryLevelDetail.YES_TAKE){
							sumOther = sumOther + paySlipComp.getCompValue();
						}
					}
				}
                        * */




	
%>
<%!
public String getSelected(Department dept, Vector secSelect){
	if(secSelect!=null && secSelect.size()>0){
		for(int i=0; i<secSelect.size(); i++){
			Department department = (Department)secSelect.get(i);
			if(department.getOID()==dept.getOID()){
				return "checked";
			}
		}
	}
	return "";
}
%>
<%
int iCommand = FRMQueryString.requestCommand(request);
String levelCode = FRMQueryString.requestString(request,"salaryLevel");
long oidPeriod = FRMQueryString.requestLong(request,"periode");
long departmentId = FRMQueryString.requestLong(request,"department");
long sectionId = FRMQueryString.requestLong(request,"section");
int start = FRMQueryString.requestInt(request, "start");
int prevCommand = FRMQueryString.requestInt(request, "prev_command");
int reportPeriod = FRMQueryString.requestInt(request,"reportPeriod");
int setFooter =  FRMQueryString.requestInt(request,"setFooter");
String footer = FRMQueryString.requestString(request, "footer");
String titleName = FRMQueryString.requestString(request, "titleName");
int summRadio= FRMQueryString.requestInt(request, "summary");
int transfered= FRMQueryString.requestInt(request, "trasfered");


String frmCurrency = "#,###";
Vector vct = new Vector(1,1);
vct = PstDepartment.list(0,0, "", PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]);
Vector secSelect = new Vector(1,1);

if(vct!=null && vct.size()>0){
	for(int i=0; i<vct.size(); i++){
		Department department = (Department)vct.get(i);
		if(iCommand!=Command.NONE && iCommand!=Command.ADD){
			int ix = FRMQueryString.requestInt(request, "chx_"+department.getOID());
			if(ix==1){
				secSelect.add(department);
			}
		}
		else{
			secSelect.add(department);
		}
	}
}

Vector tempQueryReport = new Vector(1,1);
if(iCommand == Command.LIST){
		tempQueryReport = SessEmployee.getEmpSalary(levelCode,oidPeriod,secSelect,sectionId,transfered);
}

Period pr = new Period();
String periodName ="";
		try{
			pr = PstPeriod.fetchExc(oidPeriod);
			periodName = pr.getPeriod();
		}
		catch(Exception e){
	}

Vector listDept = PstDepartment.list(0,0,"","");
String strDeptName = "";
if(secSelect.size()==listDept.size()){
	strDeptName = "ALL DEPARTMENT";
}
else{
	if(secSelect!=null && secSelect.size()>0){
		 for(int x=0; x<secSelect.size(); x++){
			Department dept = (Department)secSelect.get(x);
			if(x==secSelect.size()-1){
				strDeptName = strDeptName + dept.getDepartment() ;
			}else{
				strDeptName = strDeptName + dept.getDepartment()+",";
			}
		 }
	 }
}

//ambil section Name	
Section sect = new Section();
String sectName ="";
		try{
			sect = PstSection.fetchExc(sectionId);
			sectName = sect.getSection();
		}
		catch(Exception e){
	}

//ambil summary
// inisialisasi summary
String totalSalary = "Total Salary";
String totalOvertime = "Total Overtime";
String totalEksAllowance = "Ekspor Allowance";
String totalMealAllowance ="Meal Allowance";
String totalKasbon = "Kasbon";
String totalLeaveDeduction = "Leave Deduction";
String totalOthersDeduction = "The Other Deduction";
String totalLateDeduction="Total Late Deduction";
String totalLateSatpamDeduction="Total Late Satpam Deduc.";
String totalSukaDukaDeduction="Total Suka Duka Deduc.";
String totalNonTransfered = "Total Non Transfered";
String totalSummaryTransfered = "Total Transfered";


double sumSummary= PstPaySlipComp.getSummSalary(oidPeriod);
//Jika ada additional
	double addSummSalary = PstPayAdditional.getAddValue(totalSalary);
	sumSummary = sumSummary + addSummSalary;
                System.out.print("addSummSalary >> "+addSummSalary);
//------------------------------

double totalTransfered = 0;
double nonTransfered = 0;
Vector vctLev = new Vector(1,1);
vctLev = PstSalaryLevel.list(0,0, "", PstSalaryLevel.fieldNames[PstSalaryLevel.FLD_LEVEL_NAME]);

if(summRadio==1){
// total transfered
	Vector vectTransfered= SessEmployee.getEmpSalary(vctLev,oidPeriod,0, false);
	for (int i = 0; i <vectTransfered.size(); i++) {
		Vector temp = (Vector)vectTransfered.get(i);
		Employee employee = (Employee)temp.get(0);
		PayEmpLevel payEmpLevel = (PayEmpLevel)temp.get(1);
		PaySlip paySlip = (PaySlip)temp.get(4);
		int totalBenefit = PstSalaryLevelDetail.getSumBenefit(PstSalaryLevelDetail.YES_TAKE,payEmpLevel.getLevelCode(),PstPayComponent.TYPE_BENEFIT,paySlip.getOID(),PstSalaryLevelDetail.PERIODE_MONTHLY);
		int totalDeduction = PstSalaryLevelDetail.getSumBenefit(PstSalaryLevelDetail.YES_TAKE,payEmpLevel.getLevelCode(),PstPayComponent.TYPE_DEDUCTION,paySlip.getOID(),PstSalaryLevelDetail.PERIODE_MONTHLY);
		int takeHomePay = totalBenefit - totalDeduction;
		totalTransfered = totalTransfered + takeHomePay;
	}

//total non transfered secara general
	/*Vector vectNonTransfered= SessEmployee.getEmpNonTransfered(oidPeriod);
	for (int j = 0; j <vectNonTransfered.size(); j++) {
		Vector temp = (Vector)vectNonTransfered.get(j);
		Employee employee = (Employee)temp.get(0);
		PayEmpLevel payEmpLevel = (PayEmpLevel)temp.get(1);
		PaySlip paySlip = (PaySlip)temp.get(2);
		int totalBenefit = PstSalaryLevelDetail.getSumBenefit(PstSalaryLevelDetail.YES_TAKE,payEmpLevel.getLevelCode(),PstPayComponent.TYPE_BENEFIT,paySlip.getOID(),PstSalaryLevelDetail.PERIODE_MONTHLY);
		int totalDeduction = PstSalaryLevelDetail.getSumBenefit(PstSalaryLevelDetail.YES_TAKE,payEmpLevel.getLevelCode(),PstPayComponent.TYPE_DEDUCTION,paySlip.getOID(),PstSalaryLevelDetail.PERIODE_MONTHLY);
		int takeHomePay = totalBenefit - totalDeduction;
		nonTransfered = nonTransfered + takeHomePay;				
	}*/
	// Total NonTransfered khsus intimas
	// total nonTransfered disini adalah jumlah lembur dari karyawan yang yang berstatus training
        /*
	Vector vectNonTransfered= SessEmployee.getEmpNonTransfered(oidPeriod,PstSystemProperty.getValueByName("OID_TRAINING"));
	if(vectNonTransfered!=null && vectNonTransfered.size() > 0){
		for (int j = 0; j <vectNonTransfered.size(); j++) {
		Vector temp = (Vector)vectNonTransfered.get(j);
		PaySlip paySlip = (PaySlip)temp.get(0);
		// kode lembur diset di system property dengan nama "CODE_OVT"
		double empNonTransfered = PstPaySlipComp.getReportCompValue(paySlip.getOID(),PstSystemProperty.getValueByName("CODE_OVT"));
		nonTransfered = nonTransfered + empNonTransfered;				
		}
	}
        */
	//ambil summ salary
	
}

//Jika ada additional
	//int addTransfered = PstPayAdditional.getAddValue(totalSummaryTransfered);
	//totalTransfered = totalTransfered + addTransfered;

	//double addNonTransfered = PstPayAdditional.getAddValue(totalNonTransfered);
        ListSalary lstSalNonTransfer = SessPaySlip.getSalary(true, null, oidPeriod,0, true, totalNonTransfered,0); 
	nonTransfered = lstSalNonTransfer.totalSalary;//nonTransfered + addNonTransfered;
//------------------------------
//double sumOvertime = PstPaySlipComp.getSummOvt(oidPeriod);
// inisialisai untuk summary

												
Vector summOvt = PstPaySlipComp.getVectOvt(oidPeriod);
double sumOvertime = 0;
// jumlahkan semua nilai yang didapat
if(summOvt!=null && summOvt.size() > 0){
	for (int i = 0; i < summOvt.size(); i++) {
			Vector temp = (Vector)summOvt.get(i);
			PaySlipComp paySlipComp = (PaySlipComp)temp.get(0);
			PayEmpLevel payEmpLevel = (PayEmpLevel)temp.get(1);
			int cekTakeHomePay = PstSalaryLevelDetail.getTakeHomePay(payEmpLevel.getLevelCode(),PstSystemProperty.getValueByName("CODE_OVT"));
			if(cekTakeHomePay==PstSalaryLevelDetail.YES_TAKE){
				sumOvertime = sumOvertime + paySlipComp.getCompValue();
			}
	}
}
//Jika ada additional
	double addSummOvt = PstPayAdditional.getAddValue(totalOvertime);
	sumOvertime = sumOvertime + addSummOvt;

//------------------------------
Vector summEksAll = PstPaySlipComp.getVectEkspAll(oidPeriod);
//double sumEksAllowance = PstPaySlipComp.getSummEksAllowance(oidPeriod);
double sumEksAllowance = 0;
if(summEksAll!=null && summEksAll.size() > 0){
	for (int i = 0; i < summEksAll.size(); i++) {
			Vector temp = (Vector)summEksAll.get(i);
			PaySlipComp paySlipComp = (PaySlipComp)temp.get(0);
			PayEmpLevel payEmpLevel = (PayEmpLevel)temp.get(1);
			int cekTakeHomePay = PstSalaryLevelDetail.getTakeHomePay(payEmpLevel.getLevelCode(),PstSystemProperty.getValueByName("CODE_EKSP"));
			if(cekTakeHomePay==PstSalaryLevelDetail.YES_TAKE){
				sumEksAllowance = sumEksAllowance + paySlipComp.getCompValue();
			}
	}
}
//Jika ada additional
	double addEksAllowance = PstPayAdditional.getAddValue(totalEksAllowance);
	sumEksAllowance = sumEksAllowance + addEksAllowance;
	
    double sumMealAllowance = sumCompSalary(oidPeriod, codeMealAllowance, null, "")+ PstPayAdditional.getAddValue(totalMealAllowance);
	
//------------------------------
double total = sumSummary + sumOvertime + sumEksAllowance + sumMealAllowance;
// total kasbon
Vector vectSumKasbon = PstPaySlipComp.getVectKasbon(oidPeriod);
//double sumKasbon = PstPaySlipComp.getSummKasbon(oidPeriod);
double sumKasbon = 0;
if(vectSumKasbon!=null && vectSumKasbon.size() > 0){
	for (int i = 0; i < vectSumKasbon.size(); i++) {
			Vector temp = (Vector)vectSumKasbon.get(i);
			PaySlipComp paySlipComp = (PaySlipComp)temp.get(0);
			PayEmpLevel payEmpLevel = (PayEmpLevel)temp.get(1);
			int cekTakeHomePay = PstSalaryLevelDetail.getTakeHomePay(payEmpLevel.getLevelCode(),PstSystemProperty.getValueByName("CODE_KSB"));
			if(cekTakeHomePay==PstSalaryLevelDetail.YES_TAKE){
				sumKasbon = sumKasbon + paySlipComp.getCompValue();
			}
	}
}
//Jika ada additional
	double addKasbon = PstPayAdditional.getAddValue(totalKasbon);
	sumKasbon = sumKasbon + addKasbon;
//------------------------------
// nilai untuk potongan cuti
Vector vectSumLeaveDeduction = PstPaySlipComp.getVectSumLeaveDeduction(oidPeriod);
//double sumLeaveDeduction = PstPaySlipComp.getLeaveDeduction(oidPeriod);
double sumLeaveDeduction = 0;
if(vectSumLeaveDeduction!=null && vectSumLeaveDeduction.size() > 0){
	for (int i = 0; i < vectSumLeaveDeduction.size(); i++) {
			Vector temp = (Vector)vectSumLeaveDeduction.get(i);
			PaySlipComp paySlipComp = (PaySlipComp)temp.get(0);
			PayEmpLevel payEmpLevel = (PayEmpLevel)temp.get(1);
			int cekTakeHomePay = PstSalaryLevelDetail.getTakeHomePay(payEmpLevel.getLevelCode(),PstSystemProperty.getValueByName("LEAVE_CODE"));
			if(cekTakeHomePay==PstSalaryLevelDetail.YES_TAKE){
				sumLeaveDeduction = sumLeaveDeduction + paySlipComp.getCompValue();
			}
	}
}
//Jika ada additional
	double addLeaveDeduction = PstPayAdditional.getAddValue(totalLeaveDeduction);
	sumLeaveDeduction = sumLeaveDeduction + addLeaveDeduction;
//------------------------------
// ambil jumlah total potongan lain2
String whereOther = PstPayComponent.fieldNames[PstPayComponent.FLD_COMP_TYPE]+"="+PstPayComponent.TYPE_DEDUCTION+
					" AND "+PstPayComponent.fieldNames[PstPayComponent.FLD_COMP_CODE]+"!='"+PstSystemProperty.getValueByName("CODE_KSB")+"'"+
					" AND "+PstPayComponent.fieldNames[PstPayComponent.FLD_COMP_CODE]+"!='"+PstSystemProperty.getValueByName("CODE_CUTI")+"'";
Vector vectCompOther = PstPayComponent.list(0,0,whereOther,"");
//Vector vectSumOther = PstPaySlipComp.getVectSumOther(oidPeriod);
double sumOther = 0;//PstPaySlipComp.getSumOtherDeduction(oidPeriod);

if(vectCompOther!=null && vectCompOther.size() > 0){
	for (int i = 0; i < vectCompOther.size(); i++) {
			PayComponent payComponent= (PayComponent)vectCompOther.get(i);
			//System.out.println("PayComponent.getCompCode()..."+payComponent.getCompCode());
                        if(payComponent.getCompCode()!=null && payComponent.getCompCode().length()>0) {
                        sumOther=sumOther+sumCompSalary(oidPeriod, payComponent.getCompCode(), null, "");
                        }
			/*Vector vectSumOther = PstPaySlipComp.getVectOtherDeduction(oidPeriod,payComponent.getCompCode());
			//jumlahkan nilainya
				if(vectSumOther!=null && vectSumOther.size() > 0){
					for (int j = 0; j < vectSumOther.size(); j++) {
						Vector temp = (Vector)vectSumOther.get(j);
						PaySlipComp paySlipComp = (PaySlipComp)temp.get(0);
						PayEmpLevel payEmpLevel = (PayEmpLevel)temp.get(1);
						int cekTakeHomePay = PstSalaryLevelDetail.getTakeHomePay(payEmpLevel.getLevelCode(),payComponent.getCompCode());
						if(cekTakeHomePay==PstSalaryLevelDetail.YES_TAKE){
							sumOther = sumOther + paySlipComp.getCompValue();
						}
					}
				}
                        * */
			//------------------
	}		
}
/*if(vectSumOther!=null && vectSumOther.size() > 0){
	for (int i = 0; i < vectSumOther.size(); i++) {
			Vector temp = (Vector)vectSumOther.get(i);
			PaySlipComp paySlipComp= (PaySlipComp)temp.get(0);
			PayEmpLevel payEmpLevel = (PayEmpLevel)temp.get(1);
			int cekTakeHomePay = 0;
			System.out.println("paySlipComp.getCompCode()..."+paySlipComp.getCompCode());
			cekTakeHomePay = PstSalaryLevelDetail.getTakeHomePay(payEmpLevel.getLevelCode(),paySlipComp.getCompCode());
			if(cekTakeHomePay==PstSalaryLevelDetail.YES_TAKE){
				sumOther = sumOther + paySlipComp.getCompValue();
			}
	}
}*/



        double sumLateDeduc = sumCompSalary(oidPeriod, codeLateDeduc, null, "")+PstPayAdditional.getAddValue(totalLateDeduction);
        double sumLateSatpamDeduc = sumCompSalary(oidPeriod, codeLateDeducSatpam, null, "")+PstPayAdditional.getAddValue(totalLateSatpamDeduction);
        double sumSukaDukaDeduc = sumCompSalary(oidPeriod, codeSukaDukaDeduc, null, "")+PstPayAdditional.getAddValue(totalSukaDukaDeduction);
                
				
//nonTransfered = nonTransfered + sumLateDeduc +  sumLateSatpamDeduc + sumSukaDukaDeduc;

//Jika ada additional
	double addOther = PstPayAdditional.getAddValue(totalOthersDeduction);
	sumOther = sumOther + addOther;
//------------------------------
double finalTotal = 0;// total - (sumKasbon+sumLeaveDeduction+sumOther);

//finalTotal = finalTotal - sumLateDeduc -  sumLateSatpamDeduc -  sumSukaDukaDeduc;
//totalTransfered = finalTotal;

//finalTotal = totalTransfered;
//total = finalTotal + (sumKasbon+sumLeaveDeduction+sumOther);
//sumSummary =  total  - (sumOvertime + sumEksAllowance + sumMealAllowance) + addSummSalary;


finalTotal =  total - sumKasbon - sumLeaveDeduction - sumOther - sumLateDeduc - sumLateSatpamDeduc - sumSukaDukaDeduc; 
//totalTransfered = finalTotal - sumSukaDukaDeduc - nonTransfered;

//String totalSummaryTransfered = "Total Transfered";
ListSalary listSalTransfer = SessPaySlip.getSalary(false, null, oidPeriod,0, true, totalSummaryTransfered);
totalTransfered = listSalTransfer.totalSalary;

//_______________________________________________________________________________		
String levelName = PstSalaryLevel.getSalaryName(levelCode);
Vector temp = new Vector();

// process on drawlist
Vector vectResult = new Vector(1,1);
String listData = "";
Vector vectDataToPdf = new Vector (1,1);
Vector vectHeadCompB = new Vector (1,1);
Vector vectHeadCompD = new Vector (1,1);

vectResult = drawList(tempQueryReport,levelCode,oidPeriod,secSelect,sectionId,transfered);
listData = String.valueOf(vectResult.get(0));
vectDataToPdf = (Vector)vectResult.get(1);
vectHeadCompB = (Vector)vectResult.get(2);
vectHeadCompD = (Vector)vectResult.get(3);

System.out.println("strDeptName..........."+strDeptName);

// design vector that handle data to store in session
Vector vectPresence = new Vector(1,1);
vectPresence.add(vectDataToPdf);  
vectPresence.add(""+periodName);  
vectPresence.add(""+levelName);  
vectPresence.add(""+footer);  
vectPresence.add(vectHeadCompB);  
vectPresence.add(vectHeadCompD);  
vectPresence.add(strDeptName);  
vectPresence.add(""+sectName);  
vectPresence.add(""+summRadio);  
if(summRadio==0){
	vectPresence.add("0");  
	vectPresence.add("0");  
	vectPresence.add("0");  
	vectPresence.add("0");  
	vectPresence.add("0");  
	vectPresence.add("0");  
	vectPresence.add("0");  
	vectPresence.add("0");  
	vectPresence.add("0");  
	vectPresence.add("0");  
	vectPresence.add("0");  
	vectPresence.add("0");  
	vectPresence.add("0");  
	vectPresence.add("0");  

}else {
	vectPresence.add(""+sumSummary);  
	vectPresence.add(""+sumOvertime);  
	vectPresence.add(""+sumEksAllowance);  
	vectPresence.add(""+total);  
	vectPresence.add(""+sumKasbon);  
	vectPresence.add(""+sumLeaveDeduction);  
	vectPresence.add(""+sumOther);  
	vectPresence.add(""+finalTotal);  
	vectPresence.add(""+totalTransfered);  
	vectPresence.add(""+nonTransfered);  
        vectPresence.add(""+sumMealAllowance);  
        vectPresence.add(""+sumLateDeduc);  
        vectPresence.add(""+sumLateSatpamDeduc);  
        vectPresence.add(""+sumSukaDukaDeduc);  
}
vectPresence.add(pr);


if(session.getValue("QUERY_REPORT")!=null){
	session.removeValue("QUERY_REPORT");
}
session.putValue("QUERY_REPORT",vectPresence);













%>

<!-- End of JSP Block -->
<html>
<!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" -->
<title>HARISMA - Report of List Salary</title>
<script language="JavaScript">
<%if(iCommand==Command.PRINT){%>
    //com.dimata.harisma.report.listRequest	
	window.open("<%=printroot%>.report.listRequest.ListEmpCategPdf?mls=<%=""+System.currentTimeMillis()%>");
<%}%>

function cmdView(){
	document.frmreason.command.value="<%=Command.LIST%>";
	document.frmreason.action="list_salary_1.jsp?mls=<%=""+System.currentTimeMillis()%>";
	document.frmreason.submit();
}

function cmdFooter(){
	document.frmreason.setFooter.value="1";
	document.frmreason.action="list_salary_1.jsp?mls=<%=""+System.currentTimeMillis()%>";
	document.frmreason.submit();
}

function periodChange(){
	document.frmreason.command.value="<%=Command.ADD%>";
	document.frmreason.action="list_absence_reason.jsp?mls=<%=""+System.currentTimeMillis()%>";
	document.frmreason.submit();
}

function reportPdf(){
	var linkPage ="<%=printroot%>.report.payroll.ListSalaryPdf?mls=<%=""+System.currentTimeMillis()%>";
	window.open(linkPage); 
}

function reportPdfYear(){
	var linkPage ="<%=printroot%>.report.ListAbsenceReasonYearPdf?mls=<%=""+System.currentTimeMillis()%>";
	window.open(linkPage); 
}

function deptChange() {
	document.frmreason.command.value = "<%=Command.GOTO%>";
	document.frmreason.hidden_goto_period.value = document.frmreason.reportPeriod.value;

	//document.frmsrcpresence.hidden_goto_dept.value = document.frmsrcpresence.DEPARTMENT_ID.value;
	document.frmreason.action = "list_absence_reason.jsp";
	document.frmreason.submit();
}

//-------------- script control line -------------------
function MM_swapImgRestore() { //v3.0
		var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
	}

function MM_preloadImages() { //v3.0
		var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
		var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
		if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
	}

function MM_findObj(n, d) { //v4.0
		var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
		d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
		if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
		for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
		if(!x && document.getElementById) x=document.getElementById(n); return x;
	}

function MM_swapImage() { //v3.0
		var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
		if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
	}
</script>
<!-- #EndEditable -->
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<!-- #BeginEditable "styles" -->
<link rel="stylesheet" href="../../styles/main.css" type="text/css">
<!-- #EndEditable --> <!-- #BeginEditable "stylestab" -->
<link rel="stylesheet" href="../../styles/tab.css" type="text/css">
<!-- #EndEditable --> <!-- #BeginEditable "headerscript" -->
<SCRIPT language=JavaScript>
    function hideObjectForEmployee(){
    }

    function hideObjectForLockers(){
    }

    function hideObjectForCanteen(){
    }

    function hideObjectForClinic(){
    }

    function hideObjectForMasterdata(){
    }

	function showObjectForMenu(){
    }
	
	
function cmdUpdateDep(){
	document.frpresence.command.value="<%=Command.ADD%>";
	document.frpresence.action="lateness_monthly_report.jsp"; 
	document.frpresence.submit();
}
	function cmdUpdateLevp(){
	document.frpresence.command.value="<%=Command.ADD%>";
	document.frpresence.action="list_employee_category.jsp"; 
	document.frpresence.submit();
}
</SCRIPT>
<!-- #EndEditable -->
</head>
<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnSearchOn.jpg')">
<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
  <tr>
    <td ID="TOPTITLE" background="<%=approot%>/images/HRIS_HeaderBg3.jpg" width="100%" height="54">
      <!-- #BeginEditable "header" -->
      <%@ include file = "../../main/header.jsp" %>
      <!-- #EndEditable --> </td>
  </tr>
  <tr>
    <td  bgcolor="#9BC1FF" height="15" ID="MAINMENU" valign="middle"> <!-- #BeginEditable "menumain" -->
      <%@ include file = "../../main/mnmain.jsp" %>
      <!-- #EndEditable --> </td>
  </tr>
  <tr>
    <td  bgcolor="#9BC1FF" height="10" valign="middle">
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td align="left"><img src="<%=approot%>/images/harismaMenuLeft1.jpg" width="8" height="8"></td>
          <td align="center" background="<%=approot%>/images/harismaMenuLine1.jpg" width="100%"><img src="<%=approot%>/images/harismaMenuLine1.jpg" width="8" height="8"></td>
          <td align="right"><img src="<%=approot%>/images/harismaMenuRight1.jpg" width="8" height="8"></td>
        </tr>
      </table>
    </td>
  </tr>
  <tr>
    <td width="88%" valign="top" align="left">
      <table width="100%" border="0" cellspacing="3" cellpadding="2">
        <tr>
          <td width="100%">
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td height="20"> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" -->Employee
                  &gt; List Of Employee Salary<!-- #EndEditable --> </strong></font> </td>
              </tr>
              <tr>
                <td>
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td class="tablecolor">
                        <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                          <tr>
                            <td valign="top">
                              <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tabbg">
                                <tr>
                                  <td valign="top"> <!-- #BeginEditable "content" -->
                                    <form name="frmreason" method="post" action="">
									<input type="hidden" name="command" value="">
									<input type="hidden" name="start" value="<%=start%>">
									<input type="hidden" name="prev_command" value="<%=prevCommand%>">
									<input type="hidden" name="setFooter" value="<%=setFooter%>">
									<input type="hidden" name="titleName" value="<%=titleName%>">
									  <table width="93%" border="0" cellspacing="2" cellpadding="2">
                                        <tr> 
                                          <td width="1%">&nbsp;</td>
                                          <td width="16%" align="right" nowrap> 
                                            <div align="left">Salary Level </div></td>
                                          <td width="83%"> : 
                                            <%
                                                    Vector salValue = new Vector(1,1);
										  Vector salKey = new Vector(1,1);
										  //salKey.add("ALL SALARY LEVELS");
										  //salValue.add("0");
						                  Vector listSalLevel = PstSalaryLevel.list(0, 0, "", "LEVEL_NAME");
                                          for(int p=0;p<listSalLevel.size();p++){
										  	SalaryLevel salaryLevel = (SalaryLevel)listSalLevel.get(p);
											salValue.add(""+salaryLevel.getLevelCode());
											salKey.add(salaryLevel.getLevelCode()+" - "+salaryLevel.getLevelName());
										  }
										  %> 
                                            <%=ControlCombo.draw("salaryLevel",null,""+levelCode,salValue,salKey,"")%></td>
                                          <td width="1%">&nbsp;</td>
                                        </tr>
										<tr> 
                                          <td>&nbsp;</td>
                                          <td nowrap><%=dictionaryD.getWord(I_Dictionary.DEPARTMENT) %></td>
                                          <td> <%
										  if(vct!=null && vct.size()>0){
										  	for(int i=0; i<vct.size(); i++){
												Department department = (Department)vct.get(i);
												String isSelect = getSelected(department, secSelect);
												
												%> 
                                            <input type="checkbox" name="chx_<%=department.getOID()%>" value="1" <%=isSelect%>> 
                                            <%=department.getDepartment()%> <%}			
										  }
										  %> 
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td width="1%">&nbsp;</td>
                                          <td width="16%" align="right" nowrap> 
                                            <div align="left"><%=dictionaryD.getWord(I_Dictionary.SECTION) %></div></td>
                                          <td width="83%"> : 
                                            <%
                                          Vector sectValue = new Vector(1,1);
										  Vector sectKey = new Vector(1,1);
										  sectKey.add(" ALL SECTION");
										  sectValue.add("0");
						                  Vector listSection = PstSection.list(0, 0, "", "SECTION");
                                          for(int p=0;p<listSection.size();p++){
										  	Section section = (Section)listSection.get(p);
											sectValue.add(""+section.getOID());
											sectKey.add(section.getSection());
										  }
										  %> 
                                            <%=ControlCombo.draw("section",null,""+sectionId,sectValue,sectKey,"")%></td>
                                          <td width="1%">&nbsp;</td>
                                        </tr>
                                        <tr> 
                                          <td width="1%">&nbsp;</td>
                                          <td width="16%" align="right" nowrap> 
                                            <div align="left">Period </div></td>
                                          <td width="83%"> : 
                                            <%
                                          Vector perValue = new Vector(1,1);
										  Vector perKey = new Vector(1,1);
										 // salkey.add(" ALL DEPARTMET");
										  //deptValue.add("0");
						                  Vector listPeriod = PstPeriod.list(0, 0, "", "START_DATE DESC");
                                          for(int r=0;r<listPeriod.size();r++){
										  	Period period = (Period)listPeriod.get(r);
											perValue.add(""+period.getOID());
											perKey.add(period.getPeriod());
										  }
										  %> 
                                            <%=ControlCombo.draw("periode",null,""+oidPeriod,perValue,perKey,"")%></td>
                                          <td width="1%">&nbsp;</td>
                                        </tr>
                                        <!-- update parameter unutk include summary.
											  		Khusus unutk Intimas
													Updated by Yunny
											  -->
                                        <tr align="left" valign="top"> 
                                          <td>&nbsp;</td>
                                          <td nowrap>Transfered</td>
										  <% 
										  	if(transfered==PstPayEmpLevel.TRANSFERED_YES){
										  %>
                                          <td> <input type="radio" name="trasfered" value="0" checked>
                                            Yes 
                                            <input type="radio" name="trasfered" value="1">
                                            No 
											<input type="radio" name="trasfered" value="2">
                                            All</td>
											<%
											}else if(transfered==PstPayEmpLevel.TRANSFERED_NO){
											%>
                                          <td> <input type="radio" name="trasfered" value="0">
                                            Yes 
                                            <input type="radio" name="trasfered" value="1" checked>
                                            No 
											<input type="radio" name="trasfered" value="2">
                                            All</td>
											<% }else{
											%>
											<td>  <input type="radio" name="trasfered" value="0">
                                            Yes 
                                            <input type="radio" name="trasfered" value="1" >
                                            No 
											<input type="radio" name="trasfered" value="2" checked>
                                            All
											</td>
											<%
											}
											%>
                                        </tr>
                                        <tr align="left" valign="top"> 
                                          <td width="1%">&nbsp;</td>
                                          <td width="17%" height="21" nowrap align="left">Summary 
                                            Report </td>
                                          <%
											 if(summRadio==0){
													   %>
                                          <td width="83%"> <input type="radio" name="summary" value="0" checked>
                                            Without Summary 
                                            <input type="radio" name="summary" value="1">
                                            With Summary </td>
                                          <%
														}else if(summRadio==1){
														%>
                                          <td width="83%"> <input type="radio" name="summary" value="0">
                                            Without Summary 
                                            <input type="radio" name="summary" value="1" checked>
                                            With Summary </td>
                                          <%
														}
														%>
                                        </tr>
                                        
                                        <tr> 
                                          <td width="1%">&nbsp;</td>
                                          <td width="16%" nowrap> <div align="left"><a href="javascript:cmdFooter()">Set 
                                              Footer</a></div></td>
                                          <td width="83%"> <table border="0" cellspacing="0" cellpadding="0" width="550">
                                              <% if(setFooter==1){ %>
                                              <td  colspan="2"> : 
                                                <input name="footer" type="text" size="85" value="<%=footer%>"> 
                                              </td>
                                              <td width="1" class="command" nowrap> 
                                              </td>
                                              </tr>
                                              <%
											  }
											  %>
                                            </table></td>
                                        </tr>
                                        <tr> 
                                          <td>&nbsp;</td>
                                          <td nowrap><div align="right"><a href="javascript:cmdView()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','<%=approot%>/images/BtnSearchOn.jpg',1)" id="aSearch"><img name="Image10" border="0" src="<%=approot%>/images/BtnSearch.jpg" width="24" height="24" alt="Search Employee"></a></div></td>
                                          <td> <a href="javascript:cmdView()">Search 
                                            for Employee</a> </td>
                                        </tr>
                                      </table>
									  <%if(iCommand == Command.LIST || iCommand==Command.PRINT){%>
									  <table width="100%" border="0" cellspacing="2" cellpadding="2">
									  <%if(tempQueryReport != null && tempQueryReport.size() > 0){%>
									    <tr><td><hr></td></tr>
                                        <tr>
											<td>
											<%
												out.println(listData);
												
											%>
												<% //out.println(vectPresence);%>											
											</td>
										  </tr>
										  <%if(privPrint){%>
										  <tr>
											<td>
											  <table width="64%" border="0" cellspacing="1" cellpadding="1">
                                              <%
											  	// untuk menampilkan report dengan summary
												if(summRadio==1){
												
												
												 
											  %>
                                              <tr> 
                                                <td>&nbsp;</td>
                                                <td><%=totalSalary%></td>
                                                <td width="16%" align="right"><%=Formater.formatNumber(sumSummary, frmCurrency)%></td>
                                                <td width="21%">&nbsp;</td>
                                                <td width="20%">Total Transfered</td>
                                                <td width="15%" align="right"><%=Formater.formatNumber(totalTransfered, frmCurrency)%></td>
                                              </tr>
                                              <tr> 
                                                <td>&nbsp;</td>
                                                <td><%=totalOvertime%></td>
                                                <td  align="right"><%=Formater.formatNumber(sumOvertime, frmCurrency)%></td>
                                                <td>&nbsp;</td>
                                                <td>Total Non Transfered</td>
                                                <td width="15%" align="right"><%=Formater.formatNumber(nonTransfered, frmCurrency)%></td>
                                              </tr>
                                              <tr> 
                                                <td>&nbsp;</td>
                                                <td><%=totalEksAllowance%></td>
                                                <td  align="right"><%=Formater.formatNumber(sumEksAllowance, frmCurrency)%></td>
                                                <td>&nbsp;</td>
                                                <td>&nbsp;</td>
                                                <td width="15%" align="right">&nbsp;</td>
                                              </tr>
                                              <tr> 
                                                <td>&nbsp; </td>
                                                <td><%=totalMealAllowance%></td>
                                                <td  align="right"><%=Formater.formatNumber(sumMealAllowance, frmCurrency)%></td>
                                                <td>&nbsp;</td>
                                                <td>&nbsp;</td>
                                                <td align="right">&nbsp;</td>
                                              </tr>
                                              <tr> 
                                                <td>&nbsp;</td>
                                                <td><b>Total</b></td>
                                                <td  align="right"><b><%=Formater.formatNumber(total, frmCurrency)%></b></td>
                                                <td>&nbsp;</td>
                                                <td>&nbsp;</td>
                                                <td width="15%" align="right">&nbsp;</td>
                                              </tr>
                                              <tr> 
                                                <td height="21">&nbsp;</td>
                                                <td>&nbsp;</td>
                                                <td  align="right">&nbsp;</td>
                                                <td>&nbsp;</td>
                                                <td>&nbsp;</td>
                                                <td width="15%" align="right">&nbsp;</td>
                                              </tr>
                                              <tr> 
                                                <td>&nbsp;</td>
                                                <td><%=totalKasbon%></td>
                                                <td  align="right"><%=Formater.formatNumber(sumKasbon, frmCurrency)%></td>
                                                <td>&nbsp;</td>
                                                <td>&nbsp;</td>
                                                <td width="15%" align="right">&nbsp;</td>
                                              </tr>
                                              <tr> 
                                                <td>&nbsp;</td>
                                                <td><%=totalLeaveDeduction%></td>
                                                <td  align="right"><%=Formater.formatNumber(sumLeaveDeduction, frmCurrency)%></td>
                                                <td>&nbsp;</td>
                                                <td>&nbsp;</td>
                                                <td width="15%" align="right">&nbsp;</td>
                                              </tr>
                                              <tr> 
                                                <td>&nbsp;</td>
                                                <td><%=totalOthersDeduction%></td>
                                                <td align="right"><%=Formater.formatNumber(sumOther, frmCurrency)%></td>
                                                <td>&nbsp;</td>
                                                <td>&nbsp;</td>
                                                <td width="15%" align="right">&nbsp;</td>
                                              </tr>
                                              <tr>
                                                <td>&nbsp;</td>
                                                <td><%=totalLateDeduction%></td>
                                                <td align="right"><%=Formater.formatNumber(sumLateDeduc, frmCurrency)%></td>
                                                <td>&nbsp;</td>
                                                <td>&nbsp;</td>
                                                <td align="right">&nbsp;</td>
                                              </tr>
                                              <tr> 
                                                <td>&nbsp;</td>
                                                <td><%=totalLateSatpamDeduction%></td>
                                                <td align="right"><%=Formater.formatNumber(sumLateSatpamDeduc, frmCurrency)%></td>
                                                <td>&nbsp;</td>
                                                <td>&nbsp;</td>
                                                <td align="right">&nbsp;</td>
                                              </tr>
                                              <tr> 
                                                <td>&nbsp;</td>
                                                <td><%=totalSukaDukaDeduction%></td>
                                                <td align="right"><%=Formater.formatNumber(sumSukaDukaDeduc, frmCurrency)%></td>
                                                <td>&nbsp;</td>
                                                <td>&nbsp;</td>
                                                <td width="15%" align="right">&nbsp;</td>
                                              </tr>
                                              <tr> 
                                                <td>&nbsp;</td>
                                                <td><b>Total Salary</b></td>
                                                <td align="right"><b><%=Formater.formatNumber(finalTotal, frmCurrency)%></b></td>
                                                <td>&nbsp;</td>
                                                <td>&nbsp;</td>
                                                <td width="15%" align="right">&nbsp;</td>
                                              </tr>
                                              <%
											  }
											  %>
                                              <tr> 
                                                <td width="7%"><a href="javascript:reportPdf()"><img src="../../images/BtnNew.jpg" width="24" height="24" border="0"></a></td>
                                                <td width="21%"><b><a href="javascript:reportPdf()" class="buttonlink">Print 
                                                  Salary Report</a></b> </td>
                                                <td>&nbsp;</td>
                                              </tr>
                                            </table>
											 </td>
										  </tr>
										  <%}%>
									    <%}else{%>
									    <tr><td></td></tr>
											<tr>
											<td>
											<%
											out.println("<div class=\"msginfo\">&nbsp;&nbsp;No employee  data found ...</div>");											
											%>
											</td>
										  </tr>
										  <%}%>
										</table>
										<%}%>
                                    </form>
                                    <!-- #EndEditable --> </td>
                                </tr>
                              </table>
                            </td>
                          </tr>
                        </table>
                      </td>
                    </tr>
                    <tr>
                      <td>&nbsp; </td>
                    </tr>
                  </table>
                </td>
              </tr>
            </table>
          </td>
        </tr>
      </table>
    </td>
  </tr>
  <tr>
    <td colspan="2" height="20" <%=bgFooterLama%>> <!-- #BeginEditable "footer" -->
      <%@ include file = "../../main/footer.jsp" %>
      <!-- #EndEditable --> </td>
  </tr>
</table>
</body>
<!-- #BeginEditable "script" -->
<!-- #EndEditable --> <!-- #EndTemplate -->
</html>
