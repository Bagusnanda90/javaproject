
<%@ page language="java" %>

<!-- package qdep -->
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>
<%@ page import = "com.dimata.qdep.db.*" %>
<!-- package dimata -->
<%@ page import = "com.dimata.util.*" %>

<!-- package java -->
<%@ page import ="java.util.*,
                  com.dimata.harisma.entity.payroll.Ovt_Employee,
                  com.dimata.harisma.entity.payroll.PstOvt_Employee,
				  com.dimata.harisma.entity.payroll.Ovt_Idx,
				  com.dimata.harisma.entity.payroll.PstOvt_Idx,
				  com.dimata.harisma.entity.payroll.Ovt_Type,
				  com.dimata.harisma.entity.payroll.PstOvt_Type,
				  com.dimata.harisma.entity.payroll.PaySlip,
				  com.dimata.harisma.entity.payroll.PstPaySlip,
				  com.dimata.harisma.entity.payroll.PstPayEmpLevel,
				  com.dimata.harisma.entity.payroll.PayEmpLevel,
				  com.dimata.harisma.entity.payroll.PstSalaryLevelDetail,
				  com.dimata.harisma.entity.payroll.PstPaySlipComp,
				  com.dimata.harisma.entity.payroll.PaySlipComp,
				  com.dimata.harisma.entity.payroll.SalaryLevel,
				  com.dimata.harisma.entity.payroll.PstSalaryLevel,
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
public Vector drawList(Vector objectClass,long oidPeriod,int dayMonth,int startDatePeriod,int dateOfMonth,long departmentId, long sectionId)
	{
		
		Vector result = new Vector(1,1);
		Vector vectDatePeriod= new Vector(1,1);
		ControlList ctrlist = new ControlList();
		ctrlist.setAreaWidth("100%");
		ctrlist.setListStyle("listgen");
		ctrlist.setTitleStyle("listgentitle");
		ctrlist.setCellStyle("listgensell");
		ctrlist.setHeaderStyle("listgentitle");
		ctrlist.addHeader("No","1%","2","0");
		ctrlist.addHeader("Name","5%","2","0");
		ctrlist.addHeader("Emp.Category","2%","2","0");
                if(PstOvt_Employee.OV_SALARY>0.0){
                    ctrlist.addHeader("<div align=\"center\">Salary","3%","2","0</td>");
                    }
		//ctrlist.addHeader("Date - Time Overtime","5%","0",""+maxDayOfMonth);
		// ambil nilai overtime dalam satu period
		vectDatePeriod.add(""+startDatePeriod);
			for(int d=0;d < (dateOfMonth-1);d++){
				if(startDatePeriod==dateOfMonth){
					startDatePeriod = 0;
				}
				startDatePeriod ++;
				vectDatePeriod.add(""+startDatePeriod);
			}
		//System.out.println("vectDatePeriod..............."+vectDatePeriod);
		String groupBy = "";
		groupBy = "type."+PstOvt_Type.fieldNames[PstOvt_Type.FLD_OVT_TYPE_CODE];
		double idxLimit = 0;
		Vector vectOvtType = PstOvt_Type.getOvertimeType(PstOvt_Type.WORKING_DAY,"");
		Vector vectOvtType1 = PstOvt_Type.getOvertimeType(PstOvt_Type.WORKING_DAY,groupBy);
		// jika perhitungan sesuai dengan pertauran pemerintah
		/*if(vectOvtType1!=null && vectOvtType1.size() > 0){
			for(int l=0;l<vectOvtType1.size();l++){
				Vector temp = (Vector)vectOvtType1.get(l);
				Ovt_Idx ovtIdx = (Ovt_Idx)temp.get(0);
				Ovt_Type ovtType = (Ovt_Type)temp.get(1);
				idxLimit = ovtIdx.getHour_to();

			}
		}*/
		//jika pertauran tidak sesuai dengan peraturan pemerintah,khusus untuk intimas
		if(vectOvtType!=null && vectOvtType.size() > 0){
			for(int l=0;l<vectOvtType.size();l++){
				Vector temp = (Vector)vectOvtType.get(l);
				Ovt_Idx ovtIdx = (Ovt_Idx)temp.get(0);
				Ovt_Type ovtType = (Ovt_Type)temp.get(1);
				idxLimit = ovtIdx.getHour_from();

			}
		}
		if(vectOvtType!=null && vectOvtType.size() > 0){
			for(int l=0;l<vectOvtType.size();l++){
				Vector temp = (Vector)vectOvtType.get(l);
				Ovt_Idx ovtIdx = (Ovt_Idx)temp.get(0);
				Ovt_Type ovtType = (Ovt_Type)temp.get(1);
				String tarif = "TARIF_"+ovtIdx.getOvt_idx();
				String formula = PstSalaryLevelDetail.getFormula(tarif);
				String compToken = "";
				StringTokenizer token = new StringTokenizer(formula,"=");
				while(token.hasMoreTokens()){
					compToken=(String)token.nextToken();
				}
				//ctrlist.addHeader("<div align=\"center\">"+tarif+" &nbsp;&nbsp; &nbsp; &nbsp;(Salary :"+dayMonth+")*"+ovtIdx.getOvt_idx(),"3%","2","0</div>");
                                ctrlist.addHeader("<div align=\"center\">"+tarif+" &nbsp;","3%","2","0</div>");

			}
		}
		System.out.println("idxLimit..................."+idxLimit);
		ctrlist.addHeader("Time Overtime","2%","0",""+vectOvtType.size());
			//**************** untuk indexnya************************ 
			if(vectOvtType!=null && vectOvtType.size() > 0){
				String strIdx = "";
				for (int o=0;o<vectOvtType.size();o++){
					strIdx = strIdx + "I";
					ctrlist.addHeader(""+strIdx,"1%","0","0");
				}
			}
		ctrlist.addHeader("Total Time","2%","2","0");
		ctrlist.addHeader("Nominal Overtime","2%","0",""+vectOvtType.size());
			//**************** untuk indexnya************************ 
			if(vectOvtType!=null && vectOvtType.size() > 0){
				String strIdx = "";
				for (int o=0;o<vectOvtType.size();o++){
					strIdx = strIdx + "I";
					ctrlist.addHeader(""+strIdx,"1%","0","0");
				}
			}
		ctrlist.addHeader("Total Overtime","2%","2","0");
		ctrlist.addHeader("Take Home Pay","3%","2","0");
		ctrlist.setLinkRow(0);
		ctrlist.setLinkSufix("");
		Vector lstData = ctrlist.getData();
		Vector vectDataToPdf = new Vector(1,1);
		Vector vectFooter = new Vector(1,1);
		Vector vectTypeOvt = new Vector(1,1);
		String whereClause = "";
		String frmCurrency = "#,###";
		Vector lstLinkData = ctrlist.getLinkData();
		ctrlist.setLinkPrefix("javascript:cmdEdit('");
		ctrlist.setLinkSufix("')");
		ctrlist.reset();
		int index = -1;
		vectTypeOvt.add(""+vectOvtType.size());
		double sumTotalSalary = 0;
		double sumOvtI= 0;
		double sumOvtII= 0;
		double sumTotTimeOvt = 0 ;
		double nominalI = 0;
		double nominalII=0;
		double sumNominal = 0;
		double sumTakeHome = 0;
		for (int i = 0; i <=objectClass.size(); i++) {
			 if(i==objectClass.size()){
				 Vector rowx = new Vector();
				 Vector rowxPdf = new Vector();
				 rowx.add("");
				 rowxPdf.add("");
				 rowx.add("<b>Total</b>");
				 rowxPdf.add("Total");
				 rowx.add("");
				 rowxPdf.add("");
                                 if(PstOvt_Employee.OV_SALARY>0.0){
                                    rowx.add("<div align=\"right\"><b>"+Formater.formatNumber(sumTotalSalary, frmCurrency)+"</div></b>");                                    
                                 }
                                 rowxPdf.add(""+Formater.formatNumber(sumTotalSalary, frmCurrency));
				 for(int l=0;l<vectOvtType.size();l++){
					 rowx.add("");
					 rowxPdf.add("");
				 }
				 rowx.add("<div align=\"right\"><b>"+Formater.formatNumber(sumOvtI, "#,###.##")+"</div></b>");
				 rowxPdf.add(""+Formater.formatNumber(sumOvtI, "#,###.##"));
				 rowx.add("<div align=\"right\"><b>"+Formater.formatNumber(sumOvtII, "#,###.##")+"</div></b>");
				 rowxPdf.add(""+Formater.formatNumber(sumOvtII, "#,###.##"));
				 rowx.add("<div align=\"right\"><b>"+Formater.formatNumber(sumTotTimeOvt, "#,###.##")+"</div></b>");
				 rowxPdf.add(""+Formater.formatNumber(sumTotTimeOvt, "#,###.##"));
				 rowx.add("<div align=\"right\"><b>"+Formater.formatNumber(nominalI, frmCurrency)+"</div></b>");
				 rowxPdf.add(""+Formater.formatNumber(nominalI, frmCurrency));
				 rowx.add("<div align=\"right\"><b>"+Formater.formatNumber(nominalII, frmCurrency)+"</div></b>");
				 rowxPdf.add(""+Formater.formatNumber(nominalII, frmCurrency));
				 rowx.add("<div align=\"right\"><b>"+Formater.formatNumber(sumNominal, frmCurrency)+"</div></b>");
				 rowxPdf.add(""+Formater.formatNumber(sumNominal, frmCurrency));
				 rowx.add("<div align=\"right\"><b>"+Formater.formatNumber(sumTakeHome, frmCurrency)+"</div></b>");
				 rowxPdf.add(""+Formater.formatNumber(sumTakeHome, frmCurrency));
				 lstData.add(rowx);
				 vectDataToPdf.add(rowxPdf);
			 }
			 else{
			 Vector temp = (Vector)objectClass.get(i);
			 Employee employee = (Employee)temp.get(0);
			 Ovt_Employee ovtEmp = (Ovt_Employee)temp.get(1);
			 EmpCategory empCateg = (EmpCategory)temp.get(2);
			 PayEmpLevel payEmpLevel = (PayEmpLevel)temp.get(3);
			 Vector rowx = new Vector();
			 Vector rowxPdf = new Vector();
			 double totI = 0;
			 double totII = 0;
			 long paySlipId = PstPaySlip.getPaySlipId(oidPeriod,employee.getOID());
			 rowx.add(String.valueOf(i+1));
			 rowxPdf.add(String.valueOf(i+1));
			 rowx.add(""+employee.getFullName());
			 rowxPdf.add(""+employee.getFullName());
			 rowx.add(""+empCateg.getEmpCategory());
			 rowxPdf.add(""+empCateg.getEmpCategory());
			 //ambil salary
			 double sumSalary = PstOvt_Employee.OV_SALARY;
                         
                         if(PstOvt_Employee.OV_SALARY>0.0){
                            rowx.add("<div align=\"right\">"+Formater.formatNumber(sumSalary, frmCurrency));                            
			 //rowx.add("<div align=\"right\">");
                         }
                         rowxPdf.add(""+Formater.formatNumber(sumSalary, frmCurrency));
                         sumTotalSalary = sumTotalSalary + sumSalary;

                         //tarif overtime index pertama
			 Vector vectOvtIdx = new Vector(1,1);
			 for(int l=0;l<vectOvtType.size();l++){
				Vector tempOvt = (Vector)vectOvtType.get(l);
				Ovt_Idx ovtIdx = (Ovt_Idx)tempOvt.get(0);
				Ovt_Type ovtType = (Ovt_Type)tempOvt.get(1);
				String comp_code = "TARIF_"+ovtIdx.getOvt_idx();
				//double tarif = (sumSalary/dayMonth) * ovtIdx.getOvt_idx();
				double tarif = PstPaySlipComp.getCompValueDouble(paySlipId,comp_code);
			 	rowx.add("<div align=\"right\">"+Formater.formatNumber(tarif, frmCurrency)+"</div></b>");
				rowxPdf.add(""+Formater.formatNumber(tarif, frmCurrency));
				vectOvtIdx.add(""+tarif);
			}
			
			//ambil jam overtime dalam satu period
			for(int j=0;j<vectDatePeriod.size();j++){
			 	double ovTime = PstOvt_Employee.getOvtDuration(String.valueOf(vectDatePeriod.get(j)),oidPeriod,employee.getEmployeeNum());
				if(ovTime > 0){
					if(payEmpLevel.getOvtIdxType()==PstPayEmpLevel.NO_INDEX){
						totII = totII + ovTime;
					}
					else{
						/* Jika sesuai dengan peraturan pemerintah
						if(ovTime > idxLimit ){
								double ovTimeResidue = ovTime - idxLimit;
								totI = totI + idxLimit;
								totII = totII + ovTimeResidue;
						}
						else {
								totI = totI + ovTime;
						}*/
						// Jika tidak sesuai dengan perturan pemerintah
						if(ovTime >= idxLimit ){
								totII = totII + ovTime;
						}
						else {
								totI = totI + ovTime;
						}
					}
				}
			 }
			 //pengambilan jam ovt dalam satu period sampai disini
			 rowx.add("<div align=\"right\">"+Formater.formatNumber(totI, "#,###.##")+"</div>");
			 rowxPdf.add(""+Formater.formatNumber(totI, "#,###.##"));
			 sumOvtI = sumOvtI + totI;
			 rowx.add("<div align=\"right\">"+Formater.formatNumber(totII, "#,###.##")+"</div>");
			 rowxPdf.add(""+Formater.formatNumber(totII, "#,###.##"));
			 sumOvtII = sumOvtII + totII;
 			 rowx.add("<div align=\"right\">"+Formater.formatNumber(totII+totI, "#,###.##")+"</div>");
			 rowxPdf.add(""+Formater.formatNumber(totII+totI, "#,###.##"));
			 sumTotTimeOvt = sumTotTimeOvt + (totII+totI);
			 double totNominal =0;
			 if(vectOvtIdx!=null && vectOvtIdx.size() > 0){
				double nominal = 0;
			 	for(int idx=0;idx<vectOvtIdx.size();idx++){
					if(idx==vectOvtIdx.size()-1){
						nominal = Double.parseDouble(String.valueOf(vectOvtIdx.get(idx)))*totII;
						//---------------------------------------
						 String strNominal = Formater.formatNumber(nominal, frmCurrency);
						 String strNominalValue = "";
						 String tokenStrNominal = "";
						 StringTokenizer tokenNominal = new StringTokenizer(strNominal,",");
						 while(tokenNominal.hasMoreTokens()){
							tokenStrNominal=(String)tokenNominal.nextToken();
							strNominalValue = strNominalValue + tokenStrNominal;
						}
						//---------------------------------------
						nominalII = nominalII + Double.parseDouble(strNominalValue);
					}else{
						nominal = Double.parseDouble(String.valueOf(vectOvtIdx.get(idx)))*totI;
						//---------------------------------------
						 String strNominal = Formater.formatNumber(nominal, frmCurrency);
						 String strNominalValue = "";
						 String tokenStrNominal = "";
						 StringTokenizer tokenNominal = new StringTokenizer(strNominal,",");
						 while(tokenNominal.hasMoreTokens()){
							tokenStrNominal=(String)tokenNominal.nextToken();
							strNominalValue = strNominalValue + tokenStrNominal;
						}
						//---------------------------------------
						nominalI = nominalI + Double.parseDouble(strNominalValue);
					}
					//System.out.println("nilali......"+Double.parseDouble(String.valueOf(vectOvtIdx.get(idx))));
 			 		rowx.add("<div align=\"right\">"+Formater.formatNumber(nominal, frmCurrency)+"</div>");
					//rowx.add("<div align=\"right\">"+nominal+"</div>");
					rowxPdf.add(""+Formater.formatNumber(nominal, frmCurrency));
					totNominal = totNominal + nominal;
				}
			 }
			 	 String strTotNominal = Formater.formatNumber(totNominal, frmCurrency);
				 String strOvtValue = "";
				 String tokenStrTotNominal = "";
				 StringTokenizer tokenTotNominal = new StringTokenizer(strTotNominal,",");
				 while(tokenTotNominal.hasMoreTokens()){
					tokenStrTotNominal=(String)tokenTotNominal.nextToken();
					strOvtValue = strOvtValue + tokenStrTotNominal;
				}
				  //insert ke paySlipComp jumlah overtimenya :
			  	String compCodeOvt =String.valueOf(PstSystemProperty.getValueByName("CODE_OVT"));  
				PaySlipComp paySlipComp = new PaySlipComp();
				paySlipComp.setPaySlipId(paySlipId);
				paySlipComp.setCompCode(compCodeOvt);
				paySlipComp.setCompValue(Double.parseDouble(strOvtValue));
				//cek apakah nilai untuk paySlipId dan comp_code sudah ada
				long paySlipCompId = PstPaySlipComp.getPaySlipId(paySlipId,compCodeOvt);
				if(paySlipCompId > 0){
					paySlipComp.setOID(paySlipCompId);
					try{
						PstPaySlipComp.updateExc(paySlipComp);
					}catch(Exception e){System.out.println("ERR"+e.toString());}
				}
				else{
					 try{
						
						PstPaySlipComp.insertExc(paySlipComp);
					}catch(Exception e){System.out.println("ERR"+e.toString());}
				}
			 //_________________________________________________________________________
			 // row untuk jumlah jam lemburnya
			 double takeHomePay = PstPaySlipComp.getCompValueDouble(paySlipId,String.valueOf(PstSystemProperty.getValueByName("CODE_OVT")));
			 sumNominal = sumNominal + takeHomePay;
			 rowx.add("<div align=\"right\">"+Formater.formatNumber(takeHomePay, frmCurrency)+"</div>");
			 rowxPdf.add(""+Formater.formatNumber(takeHomePay, frmCurrency));
			 rowx.add("<div align=\"right\">"+Formater.formatNumber(takeHomePay, frmCurrency)+"</div>");
			 rowxPdf.add(""+Formater.formatNumber(totNominal, frmCurrency));
			 sumTakeHome = sumTakeHome + takeHomePay;
				lstData.add(rowx);
				vectDataToPdf.add(rowxPdf);
			}
		}
		result.add(ctrlist.drawList());
		result.add(vectDataToPdf);
		result.add(vectTypeOvt);
		return result;
	}
%>
<%!
public Vector drawListNonIndex(Vector objectClass,long oidPeriod,int dayMonth,int startDatePeriod,int dateOfMonth,long departmentId, long sectionId)
	{
		
		Vector result = new Vector(1,1);
		Vector vectDatePeriod= new Vector(1,1);
		ControlList ctrlist = new ControlList();
		ctrlist.setAreaWidth("100%");
		ctrlist.setListStyle("listgen");
		ctrlist.setTitleStyle("listgentitle");
		ctrlist.setCellStyle("listgensell");
		ctrlist.setHeaderStyle("listgentitle");
		ctrlist.addHeader("No","1%","0","0");
		ctrlist.addHeader("Name","5%","0","0");
		ctrlist.addHeader("Emp.Category","2%","0","0");
                if(PstOvt_Employee.OV_SALARY>0.0){
		ctrlist.addHeader("Salary","2%","0","0");
                }
		//ctrlist.addHeader("<div align=\"center\">Salary","3%","0","0</td>");
		// ambil nilai overtime dalam satu period
		vectDatePeriod.add(""+startDatePeriod);
			for(int d=0;d < (dateOfMonth-1);d++){
				if(startDatePeriod==dateOfMonth){
					startDatePeriod = 0;
				}
				startDatePeriod ++;
				vectDatePeriod.add(""+startDatePeriod);
			}
		double idxLimit = 0;
		Vector vectOvtType = new Vector(1,1);
		double IdxMax = PstOvt_Idx.getMaxOvtIdx(PstOvt_Type.WORKING_DAY);
		//ctrlist.addHeader("<div align=\"center\">Tarif &nbsp;&nbsp; &nbsp; &nbsp;(Salary :"+dayMonth+")*"+IdxMax,"3%","0","0</div>");
		//ctrlist.addHeader("Tarif &nbsp;&nbsp; &nbsp; &nbsp;&nbsp;&nbsp; &nbsp; &nbsp;&nbsp;&nbsp; &nbsp; &nbsp;&nbsp;&nbsp; &nbsp; (Salary :"+dayMonth+")*"+IdxMax,"3%","0","0");
                ctrlist.addHeader("Tarif &nbsp;&nbsp; &nbsp; &nbsp;&nbsp;&nbsp; &nbsp; &nbsp;&nbsp;&nbsp; &nbsp; &nbsp;&nbsp;&nbsp; &nbsp; ","3%","0","0");
		ctrlist.addHeader("Time Overtime","2%","0","0");
		ctrlist.addHeader("Take Home Pay","3%","0","0");
		ctrlist.setLinkRow(0);
		ctrlist.setLinkSufix("");
		Vector lstData = ctrlist.getData();
		Vector vectDataToPdf = new Vector(1,1);
		Vector vectFooter = new Vector(1,1);
		Vector vectTypeOvt = new Vector(1,1);
		String whereClause = "";
		String frmCurrency = "#,###";
		Vector lstLinkData = ctrlist.getLinkData();
		ctrlist.setLinkPrefix("javascript:cmdEdit('");
		ctrlist.setLinkSufix("')");
		ctrlist.reset();
		int index = -1;
		vectTypeOvt.add(""+vectOvtType.size());
		double sumTotalSalary = 0;
		double sumOvtI= 0;
		double sumOvtII= 0;
		double sumTotTimeOvt = 0 ;
		double nominalI = 0;
		double nominalII=0;
		double sumNominal = 0;
		double sumTakeHome = 0;
		for (int i = 0; i <objectClass.size(); i++) {
			 Vector temp = (Vector)objectClass.get(i);
			 Employee employee = (Employee)temp.get(0);
			 Ovt_Employee ovtEmp = (Ovt_Employee)temp.get(1);
			 EmpCategory empCateg = (EmpCategory)temp.get(2);
			 Vector rowx = new Vector();
			 Vector rowxPdf = new Vector();
			 double totI = 0;
			 double totII = 0;
			 rowx.add(String.valueOf(i+1));
			 rowxPdf.add(String.valueOf(i+1));
			 rowx.add(""+employee.getFullName());
			 rowxPdf.add(""+employee.getFullName());
			 rowx.add(""+empCateg.getEmpCategory());
			 rowxPdf.add(""+empCateg.getEmpCategory());
			 //ambil salary
			// double sumSalary = PstPaySlip.getSumSalary(oidPeriod,employee.getOID());
			 double sumSalary = PstOvt_Employee.OV_SALARY;
                         if(PstOvt_Employee.OV_SALARY>0.0){                         
                            rowx.add("<div align=\"right\">"+Formater.formatNumber(sumSalary, frmCurrency));
                            }
                            rowxPdf.add(""+Formater.formatNumber(sumSalary, frmCurrency));
                            sumTotalSalary = sumTotalSalary + sumSalary;
			 //tarif overtime index pertama
			 Vector vectOvtIdx = new Vector(1,1);
				double tarif = (sumSalary/dayMonth) * IdxMax;
				
			 	rowx.add("<div align=\"right\">"+Formater.formatNumber(tarif, frmCurrency)+"</div></b>");
				rowxPdf.add(""+Formater.formatNumber(tarif, frmCurrency));
			//ambil jam overtime dalam satu period
			for(int j=0;j<vectDatePeriod.size();j++){
			 	double ovTime = PstOvt_Employee.getOvtDuration(String.valueOf(vectDatePeriod.get(j)),oidPeriod,employee.getEmployeeNum());
				if(ovTime > 0){
					totI = totI + ovTime;
				}
			 }
			 //pengambilan jam ovt dalam satu period sampai disini
			 rowx.add("<div align=\"right\">"+Formater.formatNumber(totI, "#,###.##")+"</div>");
			 rowxPdf.add(""+Formater.formatNumber(totI, "#,###.##"));
			 sumOvtI = sumOvtI + totI;
			 double totNominal =0;
			rowx.add("<div align=\"right\">"+Formater.formatNumber(tarif * totI, frmCurrency)+"</div>");
			 rowxPdf.add(""+Formater.formatNumber(tarif * totI, frmCurrency));
			 sumTakeHome = sumTakeHome + totNominal;
			 // row untuk jumlah jam lemburnya
			lstData.add(rowx);
			vectDataToPdf.add(rowxPdf);
		
		}
		result.add(ctrlist.drawList());
		result.add(vectDataToPdf);
		result.add(vectTypeOvt);
		return result;
	}
%>
<%!
public String getSelected(SalaryLevel s, Vector secSelect){
	if(secSelect!=null && secSelect.size()>0){
		for(int i=0; i<secSelect.size(); i++){
			SalaryLevel salaryLevel = (SalaryLevel)secSelect.get(i);
			if(salaryLevel.getLevelCode()==s.getLevelCode()){
				return "checked";
			}
		}
	}
	return "";
}
%>
<%
int iCommand = FRMQueryString.requestCommand(request);
long oidDepartment= FRMQueryString.requestLong(request,"department");
long oidPeriod = FRMQueryString.requestLong(request,"periode");
long oidSection = FRMQueryString.requestLong(request,"section");
int start = FRMQueryString.requestInt(request, "start");
int prevCommand = FRMQueryString.requestInt(request, "prev_command");
int reportPeriod = FRMQueryString.requestInt(request,"reportPeriod");
int setFooter =  FRMQueryString.requestInt(request,"setFooter");
String footer = FRMQueryString.requestString(request, "footer");
String titleName = FRMQueryString.requestString(request, "titleName");
int summRadio= FRMQueryString.requestInt(request, "idx_type");
Vector vct = new Vector(1,1);

PstOvt_Employee.OV_SALARY = Double.parseDouble(PstSystemProperty.getValueByName("OV_SALARY"));

vct = PstSalaryLevel.list(0,0, "", PstSalaryLevel.fieldNames[PstSalaryLevel.FLD_LEVEL_NAME]);
Vector secSelect = new Vector(1,1);

if(vct!=null && vct.size()>0){
	for(int i=0; i<vct.size(); i++){
		SalaryLevel salaryLevel = (SalaryLevel)vct.get(i);
		if(iCommand!=Command.NONE && iCommand!=Command.ADD){
			int ix = FRMQueryString.requestInt(request, "chx_"+salaryLevel.getLevelCode());
			if(ix==1){
				secSelect.add(salaryLevel);
			}
		}
		else{
			secSelect.add(salaryLevel);
		}
	}
}


Vector tempQueryReport = new Vector(1,1);
if(iCommand == Command.LIST){
		tempQueryReport = SessEmployee.getOvtEmployee(oidDepartment,oidSection,oidPeriod,summRadio,secSelect);
}

//fetch data untuk period
Period pr = new Period();
Date date = new Date();
String periodName ="";
		try{
			pr = PstPeriod.fetchExc(oidPeriod);
			date =  pr.getStartDate();
			periodName = pr.getPeriod();
		}
		catch(Exception e){
	}

//fetch data untuk department
Department dept = new Department();
String deptName ="";
		try{
			dept = PstDepartment.fetchExc(oidDepartment);
			deptName =  dept.getDepartment();
		}
		catch(Exception e){
	}

//fetch data untuk section
Section sect = new Section();
String sectName ="";
		try{
			sect = PstSection.fetchExc(oidSection);
			sectName =  sect.getSection();
		}
		catch(Exception e){
	}
	
Vector temp = new Vector();

// get maximum date of selected month
Calendar newCalendar = Calendar.getInstance();
newCalendar.setTime(date);
int dateOfMonth = newCalendar.getActualMaximum(Calendar.DAY_OF_MONTH); 

int startDatePeriod = Integer.parseInt(String.valueOf(PstSystemProperty.getValueByName("START_DATE_PERIOD")));  

int dayMonth = Integer.parseInt(String.valueOf(PstSystemProperty.getValueByName("TIME_WORKS_MONTH")));  


// process on drawlist
Vector vectResult = new Vector(1,1);
String listData = "";
Vector vectDataToPdf = new Vector (1,1);
Vector vectTypeOvt = new Vector (1,1);
/*if(summRadio==PstPayEmpLevel.WITH_INDEX){*/
	vectResult = drawList(tempQueryReport,oidPeriod,dayMonth,startDatePeriod,dateOfMonth,oidDepartment,oidSection);
/*}
else if(summRadio==PstPayEmpLevel.NO_INDEX){
		vectResult = drawListNonIndex(tempQueryReport,oidPeriod,dayMonth,startDatePeriod,dateOfMonth,oidDepartment,oidSection);
}*/
listData = String.valueOf(vectResult.get(0));
vectDataToPdf = (Vector)vectResult.get(1);
vectTypeOvt = (Vector)vectResult.get(2);
/*vectHeadCompD = (Vector)vectResult.get(3);*/
// design vector that handle data to store in session
Vector vectPresence = new Vector(1,1);
vectPresence.add(vectDataToPdf);  
vectPresence.add(""+periodName);  
vectPresence.add(""+deptName);  
vectPresence.add(""+sectName);  
vectPresence.add(""+footer); 
vectPresence.add(""+dateOfMonth);  
vectPresence.add(""+startDatePeriod);  
vectPresence.add(""+vectTypeOvt.get(0)); 
vectPresence.add("0"); 

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
<title>HARISMA - Report of Employee Overtime</title>
<script language="JavaScript">
<%if(iCommand==Command.PRINT){%>
    //com.dimata.harisma.report.listRequest	
	window.open("<%=printroot%>.report.listRequest.ListEmpCategPdf");
<%}%>

function cmdView(){
	document.frmreason.command.value="<%=Command.LIST%>";
	document.frmreason.action="upah_ovt.jsp";
	document.frmreason.submit();
}

function cmdFooter(){
	document.frmreason.setFooter.value="1";
	document.frmreason.action="upah_ovt.jsp";
	document.frmreason.submit();
}



function reportPdf(){
	var linkPage ="<%=printroot%>.report.payroll.ListOvtValuePdf";
	window.open(linkPage); 
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
                <td height="20"> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" -->Report 
                  &gt; Overtime<!-- #EndEditable --> </strong></font> </td>
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
                                            <div align="left"><%=dictionaryD.getWord(I_Dictionary.DEPARTMENT) %></div></td>
                                          <td width="83%"> : 
                                            <%
                                          Vector deptValue = new Vector(1,1);
										  Vector deptKey = new Vector(1,1);
										  deptKey.add(" ALL DEPARTMET");
										  deptValue.add("0");
						                  Vector listDept = PstDepartment.list(0, 0, "", "DEPARTMENT");
                                          for(int p=0;p<listDept.size();p++){
										  	Department department = (Department)listDept.get(p);
											deptValue.add(""+department.getOID());
											deptKey.add(department.getDepartment());
										  }
										  %> 
                                            <%=ControlCombo.draw("department",null,""+oidDepartment,deptValue,deptKey,"")%></td>
                                        </tr>
                                        <tr> 
                                          <td width="1%">&nbsp;</td>
                                          <td width="16%" align="right" nowrap> 
                                            <div align="left"><%=dictionaryD.getWord(I_Dictionary.SECTION) %></div></td>
                                          <td width="83%"> : 
                                            <%
                                          Vector sectValue = new Vector(1,1);
										  Vector sectKey = new Vector(1,1);
										  sectKey.add(" ALL ");
										  sectValue.add("0");
						                  Vector listSect = PstSection.list(0, 0, "", "SECTION");
                                          for(int s=0;s<listSect.size();s++){
										  	Section section = (Section)listSect.get(s);
											sectValue.add(""+section.getOID());
											sectKey.add(section.getSection());
										  }
										  %> 
                                            <%=ControlCombo.draw("section",null,""+oidSection,sectValue,sectKey,"")%></td>
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
                                        </tr>
                                        <tr align="left" valign="top"> 
                                          <td>&nbsp;</td>
                                          <td height="21" nowrap align="left">Salary Level</td>
                                          <td>
										 <%
										  if(vct!=null && vct.size()>0){
										  	for(int i=0; i<vct.size(); i++){
												SalaryLevel salaryLevel = (SalaryLevel)vct.get(i);
												String isSelect = getSelected(salaryLevel, secSelect);
												
												%>
												<input type="checkbox" name="chx_<%=salaryLevel.getLevelCode()%>" value="1" <%=isSelect%>><%=salaryLevel.getLevelName()%>
											<%}			
										  }
										  %>	
										  </td>
                                          <td>&nbsp;</td>
                                          <td>&nbsp;</td>
                                        </tr>
                                        <tr align="left" valign="top"> 
                                          <td width="1%">&nbsp;</td>
                                          <td width="17%" height="21" nowrap align="left">Index 
                                            Type </td>
                                          <%
													   if(summRadio==PstPayEmpLevel.WITH_INDEX){
													   %>
                                          <td width="83%"> <input type="radio" name="idx_type" value="0" checked>
                                            With Index 
                                            <input type="radio" name="idx_type" value="1">
                                            Without Index 
                                            <input type="radio" name="idx_type" value="2" >
                                            All </td>
                                          <%
														}else if(summRadio==PstPayEmpLevel.NO_INDEX){
														%>
                                          <td width="83%"> <input type="radio" name="idx_type" value="0">
                                            With Index 
                                            <input type="radio" name="idx_type" value="1" checked>
                                            Without Index 
                                            <input type="radio" name="idx_type" value="2" >
                                            All </td>
                                          <%
														}else if(summRadio==2){
															%>
                                          <td width="83%"> <input type="radio" name="idx_type" value="0">
                                            With Index 
                                            <input type="radio" name="idx_type" value="1" >
                                            Without Index 
                                            <input type="radio" name="idx_type" value="2" checked>
                                            All </td>
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
											  <table width="23%" border="0" cellspacing="1" cellpadding="1">
                                              <tr>
												  <td width="5%"><a href="javascript:reportPdf()"><img src="../../images/BtnNew.jpg" width="24" height="24" border="0"></a></td>
												  <td width="95%"><b><a href="javascript:reportPdf()" class="buttonlink">Print
													Overtime Payment Report</a></b>
												  </td>
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
											out.println("<div class=\"msginfo\">&nbsp;&nbsp;No employee leave  data found ...</div>");											
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
