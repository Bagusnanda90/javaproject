 
<%@ page language="java" %>
<%@ page import = "java.util.*" %>
<!-- package wihita -->
<%@ page import = "com.dimata.util.*" %>
<!-- package qdep -->
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>

<%@ page import = "java.util.Date" %>

<!--package harisma -->
<%@ page import = "com.dimata.harisma.entity.search.*" %>
<%@ page import = "com.dimata.harisma.form.search.*" %>
<%@ page import = "com.dimata.harisma.entity.payroll.*" %>
<%@ page import = "com.dimata.harisma.form.payroll.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ page import = "com.dimata.harisma.session.employee.*" %>


<%@ include file = "../../main/javainit.jsp" %>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_PAYROLL, AppObjInfo.G2_PAYROLL_SETUP, AppObjInfo.OBJ_PAYROLL_SETUP_LEVEL);%>
<%@ include file = "../../main/checkuser.jsp" %>

<%
	CtrlPayEmpLevel ctrlPayEmpLevel = new CtrlPayEmpLevel(request);
	long oidPayEmpLevel = FRMQueryString.requestLong(request, "pay_emp_level_oid");
	long oidEmployee = FRMQueryString.requestLong(request, "employee_oid");
	long oidDivision = FRMQueryString.requestLong(request,"division");
	long oidDepartment = FRMQueryString.requestLong(request,"department");
	long oidSection = FRMQueryString.requestLong(request,"section");
	int prevCommand = FRMQueryString.requestInt(request, "prev_command");
	int iCommand = FRMQueryString.requestCommand(request);
	int start = FRMQueryString.requestInt(request,"start");
	int status = FRMQueryString.requestInt(request,"status");
	String cariName = FRMQueryString.requestString(request,"cariName");
	String payrollNum = FRMQueryString.requestString(request,"payrollNum");
	int statusPayroll = FRMQueryString.requestInt(request,"statusPayroll");
	int dataStatus = FRMQueryString.requestInt(request,"dataStatus");
	int aksiCommand = FRMQueryString.requestInt(request,"aksiCommand");
	int countList = FRMQueryString.requestInt(request,"countList");
        int periodBln = FRMQueryString.requestInt(request,"period_bln");
        int periodThn = FRMQueryString.requestInt(request,"period_thn");
	//SrcEmployee srcEmployee = new SrcEmployee();
	//FrmSrcEmployee frmSrcEmployee = new FrmSrcEmployee();
	
	System.out.println("iCommand................"+iCommand);
	
	Vector listEmployee = new Vector(1,1);
	int numberInner=0;
	if(iCommand == Command.LIST || iCommand==Command.EDIT || iCommand == Command.SAVE || iCommand == Command.ADD || iCommand == Command.FIRST)
		{
			if(iCommand == Command.ADD){
				listEmployee = SessEmployee.listEmployeePayrollBlank(oidDepartment,oidDivision,oidSection,cariName,payrollNum,statusPayroll);
			}
			else{
				listEmployee = SessEmployee.listEmployeePayroll(oidDepartment,oidDivision,oidSection,cariName,payrollNum,statusPayroll,dataStatus);
				numberInner  = listEmployee.size(); 
					if(listEmployee.size()==0){
					listEmployee = SessEmployee.listEmployeePayrollBlank(oidDepartment,oidDivision,oidSection,cariName,payrollNum,statusPayroll);
			 	}
			}
		}

	
	int iErrCode = FRMMessage.ERR_NONE;
	String msgString = "";
	ControlLine ctrLine = new ControlLine();
	System.out.println("iCommand = "+iCommand);
	//iErrCode = ctrlPayEmpLevel.action(iCommand , oidPayEmpLevel,listEmployee);
	msgString = ctrlPayEmpLevel.getMessage();
	FrmPayEmpLevel frmPayEmpLevel = ctrlPayEmpLevel.getForm();
	PayEmpLevel payEmpLevel = ctrlPayEmpLevel.getPayEmpLevel();
	//oidPayEmpLevel = payEmpLevel.getOID();
	//System.out.println("oidPayEmpLevel = "+oidPayEmpLevel);

	/*if(iCommand==Command.BACK)
	{
	frmSrcEmployee = new FrmSrcEmployee(request, srcEmployee);
	try
	{
		srcEmployee = (SrcEmployee)session.getValue(SessEmployee.SESS_SRC_EMPLOYEE);
		if(srcEmployee == null) 
		{
			srcEmployee = new SrcEmployee();
		}
		System.out.println("ecccccc "+srcEmployee.getOrderBy());
	}
	catch (Exception e)
	{
		srcEmployee = new SrcEmployee();
	}
}*/


	
	 /*variable declaration*/
	//String frmCurrency = "#,###";
    int recordToGet = 400;
    String whereClause = "";
    String orderClause = "";
	//Vector listPayEmpLevel= new Vector(1,1);
	// untuk status
	Vector vct = new Vector(1,1);

	vct = PstEmployee.getStatusPayroll();
	//System.out.println("vect nilai "+vct.size());
	 /*switch statement */
   // iErrCode = ctrlPayEmpLevel.action(iCommand , oidPayEmpLevel, listEmployee);
    /* end switch*/
   

    /*count list All Language*/
    int vectSize = PstPayEmpLevel.getCount(whereClause);

    /*switch list Language*/
    if((iCommand == Command.FIRST || iCommand == Command.PREV )||
      (iCommand == Command.NEXT || iCommand == Command.LAST)){
                    start = ctrlPayEmpLevel.actionList(iCommand, start, vectSize, recordToGet);
     } 
    /* end switch list*/

    //PayExecutive payExecutive = ctrLanguage.getLanguage();
    msgString =  ctrlPayEmpLevel.getMessage();

    /* get record to display */
    //listPayEmpLevel = PstPayEmpLevel.list(start,recordToGet, whereClause , orderClause);
	
	
	
	
//System.out.println("statusPayroll  "+statusPayroll)	;
%>

<!-- JSP Block -->

<%!
public String drawList(int iCommand,FrmPayEmpLevel frmObject, PayEmpLevel objEntity,Vector objectClass, long payEmpLevelId, int st, int jumlah, int periodBln, int periodThn){

	
	ControlList ctrlist = new ControlList();
	ctrlist.setAreaWidth("100%");
	ctrlist.setListStyle("listgen");
	ctrlist.setTitleStyle("listgentitle");
	ctrlist.setCellStyle("listgensell");
	ctrlist.setHeaderStyle("listgentitle");
	ctrlist.addHeader("No.","2%");
	ctrlist.addHeader("Payroll","6%");
	ctrlist.addHeader("Name","15%");
	ctrlist.addHeader("Position","10%");
	ctrlist.addHeader("Comm.Date","7%");
	ctrlist.addHeader("Resign","5%");
	ctrlist.addHeader("Sal.Level","5%");	
	ctrlist.addHeader("Level Start","20%");
	ctrlist.addHeader("End Date","20%");
	ctrlist.addHeader("Cash/Bank","10%");
	ctrlist.addHeader("Bank Acc.","10%");
	ctrlist.addHeader("NPWP","7%");
	ctrlist.addHeader("Position For Tax","7%");
	ctrlist.addHeader("Address For Tax","15%");
	ctrlist.addHeader("Payroll Period","7%");
	ctrlist.addHeader("Comm Status","7%");
	ctrlist.addHeader("Prev.Income","7%");
	ctrlist.addHeader("Prev.Tax PPh 21","15%");
	ctrlist.addHeader("Meal Allowance","5%");
	ctrlist.addHeader("Ovt Idx Type","15%");
		
	ctrlist.setLinkRow(1);
	ctrlist.setLinkSufix("");
	Vector lstData = ctrlist.getData();
	Vector lstLinkData = ctrlist.getLinkData();
	ctrlist.setLinkPrefix("javascript:cmdEdit('");
	ctrlist.setLinkSufix("')");
	ctrlist.reset();
	// untuk menampikan salary-Level
	Vector sal_value = new Vector(1,1);
    Vector sal_key = new Vector(1,1);
	//String orderCl = PstCurrencyType.fieldNames[PstCurrencyType.FLD_NAME]+" DESC ";
    Vector listSal= PstSalaryLevel.list(0, 0, "", " LEVEL_CODE ");
    for (int i = 0; i < listSal.size(); i++) {
		 SalaryLevel sal = (SalaryLevel) listSal.get(i);
		 sal_key.add(sal.getLevelCode());
		 sal_value.add(String.valueOf(sal.getLevelCode()));
    }
	
	// untuk menampikan list Bank
	Vector bank_value = new Vector(1,1);
    Vector bank_key = new Vector(1,1);
	//String orderCl = PstCurrencyType.fieldNames[PstCurrencyType.FLD_NAME]+" DESC ";
    Vector listBank= PstPayBanks.list(0, 0, "", " BANK_NAME ");
    for (int i = 0; i < listBank.size(); i++) {
		 PayBanks bank= (PayBanks) listBank.get(i);
		 bank_key.add(bank.getBankName());
		 bank_value.add(String.valueOf(bank.getOID()));
    }
	//untuk mengisi cash
	bank_key.add("-CASH-");
	bank_value.add(String.valueOf(PstSystemProperty.getValueByName("OID_CASH")));
	// commencing stutus
	Vector commKey = new Vector();
    Vector commValue = new Vector();
	commKey.add(PstPayEmpLevel.KARYAWAN_LAMA+"");
    commKey.add(PstPayEmpLevel.KARYAWAN_BARU+"");
	commKey.add(PstPayEmpLevel.PINDAHAN+"");
	commKey.add(PstPayEmpLevel.EXPATRIAT+"");
    commValue.add(PstPayEmpLevel.stComm[PstPayEmpLevel.KARYAWAN_LAMA]);
	commValue.add(PstPayEmpLevel.stComm[PstPayEmpLevel.KARYAWAN_BARU]);
	commValue.add(PstPayEmpLevel.stComm[PstPayEmpLevel.PINDAHAN]);
	commValue.add(PstPayEmpLevel.stComm[PstPayEmpLevel.EXPATRIAT]);
	
	// meall allowance
	Vector mealKey = new Vector();
    Vector mealValue = new Vector();
	mealKey.add(PstPayEmpLevel.WEEKLY+"");
    mealKey.add(PstPayEmpLevel.MONTLY+"");
    mealValue.add(PstPayEmpLevel.stAllowance[PstPayEmpLevel.WEEKLY]);
	mealValue.add(PstPayEmpLevel.stAllowance[PstPayEmpLevel.MONTLY]);
	
	// Overtime Index Type
	Vector idxTypeKey = new Vector();
    Vector idxTypeValue = new Vector();
	idxTypeKey.add(PstPayEmpLevel.WITH_INDEX+"");
	idxTypeKey.add(PstPayEmpLevel.NO_INDEX+"");
	idxTypeValue.add(PstPayEmpLevel.stOvtIndex[PstPayEmpLevel.WITH_INDEX]);
    idxTypeValue.add(PstPayEmpLevel.stOvtIndex[PstPayEmpLevel.NO_INDEX]);
	
	
	Vector listPayEmpLevel = new Vector(1,1);
	for (int i = 0; i < objectClass.size(); i++) {
		Vector temp = (Vector)objectClass.get(i);
		Employee employee = (Employee)temp.get(0);
		Department department = (Department)temp.get(1);
		Position position = (Position)temp.get(2);
		Section section = (Section)temp.get(3);
		EmpCategory empCategory = (EmpCategory)temp.get(4);
		System.out.println("temp.size()  "+temp.size());
		if(temp.size() == 11) {
			PayEmpLevel payEmpLevel = (PayEmpLevel)temp.get(10);
			// ambil data dari pay_emp_level berdasarkan payEmpLevelId
		 String wherePay = PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_PAY_EMP_LEVEL_ID]+" = '"+payEmpLevel.getOID()+"'";
		 listPayEmpLevel= PstPayEmpLevel.list(0, 0, wherePay, "");
		}
		int index = -1;
		String BankAcc = "";
		String PosFortax = "";
		int periodBegin = 0;
		int periodEnd =0;
		String comStatus="";
		int mealAllowance=0;
		double prevIncome = 0;
		int prevTaxPaid =0;
		Date startDate = new Date();
		String level="";
		long bankId = 0;
		int ovtIdxType=0;
			Vector rowx = new Vector();
                        PayEmpLevel empLevel = new PayEmpLevel();
			//System.out.println("listPayEmpLevel.size()"+listPayEmpLevel.size());
			//for (int j = 0; j < listPayEmpLevel.size(); j++) {
			if ((listPayEmpLevel != null) && (listPayEmpLevel.size() > 0)) {
                                empLevel = (PayEmpLevel) listPayEmpLevel.get(0);
                                BankAcc = empLevel.getBankAccNr();
                                PosFortax = empLevel.getPosForTax();
                                periodBegin = empLevel.getPayPerBegin();
                                periodEnd = empLevel.getPayPerEnd();
                                comStatus = (PstPayEmpLevel.stComm[empLevel.getCommencingSt()]);
                                prevIncome = empLevel.getPrevIncome();
                                prevTaxPaid = empLevel.getPrevTaxPaid();
                                startDate = empLevel.getStartDate();
                                level = empLevel.getLevelCode();
                                bankId = empLevel.getBankId();
                                mealAllowance = empLevel.getMealAllowance();
                                ovtIdxType = empLevel.getOvtIdxType();

			 //jika sudah masuk di tabel pay_emp_level
                                // mengambil nama bank
                                PayBanks objBank = new PayBanks();
                                String whereCl = PstPayBanks.fieldNames[PstPayBanks.FLD_BANK_ID] + " = '" + bankId + "'";
                                String bankName = "";
                                long idCash = Long.parseLong(String.valueOf(PstSystemProperty.getPropertyLongbyName("OID_CASH")));
                                Vector listBankChoose = PstPayBanks.list(0, 0, whereCl, "");
                                if (listBankChoose.size() > 0) {
                                    objBank = (PayBanks) listBankChoose.get(0);
                                    bankName = objBank.getBankName();
                                } else {
                                    if (bankId == idCash) {
                                        bankName = " CASH ";
                                    }
                                }
                                //System.out.println("payEmpLevelId  "+payEmpLevelId);
                                if (payEmpLevelId == empLevel.getOID()) {
                                    index = i;
                                }
                                if (index == i && (iCommand == Command.EDIT || iCommand == Command.ASK)) {
                                    rowx.add((empLevel.getStatusData() == 0 ? "<b>" : "") + String.valueOf(st + 1 + i) + (empLevel.getStatusData() == 0 ? "</b>" : ""));
                                    rowx.add(employee.getEmployeeNum() + "<input type=\"hidden\" name=\"employee_id\" value=\"" + employee.getOID() + "\" size=\"25\" class=\"elemenForm\"><input type=\"hidden\" name=\"empLevelId\" value=\"" + payEmpLevelId + "\" size=\"25\" class=\"elemenForm\">");
                                    rowx.add(employee.getFullName());
                                    rowx.add(position.getPosition());
                                    rowx.add("" + Formater.formatTimeLocale(employee.getCommencingDate(), "dd-MMM-yyyy"));
                                    //rowx.add(""+employee.getResigned());
                                    if (employee.getResigned() == 0) {
                                        rowx.add(" - ");
                                    }
                                    if (employee.getResigned() == 1) {
                                        rowx.add(" Yes ");
                                    }
                                    rowx.add("" + ControlCombo.draw("salary_level", "formElemen", null, "" + level, sal_value, sal_key));
                                    rowx.add("" + ControlDate.drawDateWithStyle("start_date", startDate == null ? new Date() : startDate, 10, -75, "formElemen"));
                                    rowx.add("" + ControlDate.drawDateWithStyle("end_date", empLevel.getEndDate() == null ? new Date() : empLevel.getEndDate(), 10, -75, "formElemen"));
                                    rowx.add("" + ControlCombo.draw("bank_name", "formElemen", null, "" + bankName, bank_value, bank_key));
                                    rowx.add("<input type=\"text\" name=\"bank_acc\" value=\"" + BankAcc + "\" size=\"25\" class=\"elemenForm\">");
                                    rowx.add(employee.getTaxRegNr());
                                    rowx.add("<input type=\"text\" name=\"pos_for_tax\" value=\"" + PosFortax + "\" size=\"25\" class=\"elemenForm\">");
                                    rowx.add(employee.getAddressForTax());
                                    rowx.add("<input type=\"text\" name=\"period_begin\" value=\"" + periodBegin + "\" size=\"5\" class=\"elemenForm\"> to <input type=\"text\"name=\"period_end\"  value=\"" + periodEnd + "\" size=\"5\" class=\"elemenForm\"> ");
                                    rowx.add("" + ControlCombo.draw("com_status", "formElemen", null, "" + comStatus, commKey, commValue));
                                    rowx.add("<input type=\"text\" name=\"prev_income\" value=\"" + Formater.formatNumber(prevIncome, "###,###.#") + "\" size=\"25\" class=\"elemenForm\">");
                                    rowx.add("<input type=\"text\" name=\"prev_tax_paid\" value=\"" + Formater.formatNumber(prevTaxPaid, "###,###.#") + "\" size=\"25\" class=\"elemenForm\">");
                                    rowx.add("" + ControlCombo.draw("meal_allowance", "formElemen", null, "" + mealAllowance, mealKey, mealValue));
                                    rowx.add("" + ControlCombo.draw("ovt_idx_type", "formElemen", null, "" + ovtIdxType, idxTypeKey, idxTypeValue));
                                    lstData.add(rowx);
                                    lstLinkData.add(String.valueOf(empLevel.getOID()));
                                } else {
                                    String endDateThn = Formater.formatTimeLocale(empLevel.getEndDate(), "yyyy");
                                    String endDateBln = Formater.formatTimeLocale(empLevel.getEndDate(), "MM");
                                    int tahun = Integer.valueOf(endDateThn);
                                    int bulan = Integer.valueOf(endDateBln);//
                                    if (periodBln != 0 && periodThn != 0){
                                            if (tahun > periodThn){
                                                rowx.add((empLevel.getStatusData() == 0 ? "<b>" : "") + String.valueOf(st + 1 + i) + (empLevel.getStatusData() == 0 ? "</b>" : ""));
                                                rowx.add(employee.getEmployeeNum() + "<input type=\"hidden\" name=\"" + frmObject.fieldNames[FrmPayEmpLevel.FRM_FIELD_EMPLOYEE_ID] + "\" value=\"" + employee.getOID() + "\" size=\"25\" class=\"elemenForm\"><input type=\"hidden\" name=\"empLevelId\" value=\"" + payEmpLevelId + "\" size=\"25\" class=\"elemenForm\">");
                                                rowx.add(employee.getFullName());
                                                rowx.add(position.getPosition());
                                                rowx.add("" + Formater.formatTimeLocale(employee.getCommencingDate(), "dd-MMM-yyyy"));
                                                //rowx.add(""+employee.getResigned());
                                                if (employee.getResigned() == 0) {
                                                    rowx.add(" - ");
                                                }
                                                if (employee.getResigned() == 1) {
                                                    rowx.add(" Yes ");
                                                }
                                                rowx.add("" + level);
                                                rowx.add("" + Formater.formatTimeLocale(empLevel.getStartDate(), "dd-MMM-yyyy"));
                                                rowx.add("" + Formater.formatTimeLocale(empLevel.getEndDate(), "dd-MMM-yyyy"));
                                                rowx.add(bankName); //10
                                                rowx.add(BankAcc);
                                                rowx.add(employee.getTaxRegNr());
                                                rowx.add(PosFortax);
                                                rowx.add(employee.getAddressForTax());
                                                rowx.add("" + periodBegin + " - " + periodEnd);
                                                rowx.add("" + comStatus);
                                                rowx.add("" + prevIncome);
                                                rowx.add("" + prevTaxPaid);
                                                rowx.add("" + PstPayEmpLevel.stAllowance[mealAllowance]);
                                                rowx.add("" + PstPayEmpLevel.stOvtIndex[ovtIdxType]);
                                                lstData.add(rowx);
                                                lstLinkData.add(String.valueOf(empLevel.getOID()));
                                            } else {
                                                if (tahun == periodThn){
                                                    if (bulan > periodBln){
                                                        rowx.add((empLevel.getStatusData() == 0 ? "<b>" : "") + String.valueOf(st + 1 + i) + (empLevel.getStatusData() == 0 ? "</b>" : ""));
                                                        rowx.add(employee.getEmployeeNum() + "<input type=\"hidden\" name=\"" + frmObject.fieldNames[FrmPayEmpLevel.FRM_FIELD_EMPLOYEE_ID] + "\" value=\"" + employee.getOID() + "\" size=\"25\" class=\"elemenForm\"><input type=\"hidden\" name=\"empLevelId\" value=\"" + payEmpLevelId + "\" size=\"25\" class=\"elemenForm\">");
                                                        rowx.add(employee.getFullName());
                                                        rowx.add(position.getPosition());
                                                        rowx.add("" + Formater.formatTimeLocale(employee.getCommencingDate(), "dd-MMM-yyyy"));
                                                        //rowx.add(""+employee.getResigned());
                                                        if (employee.getResigned() == 0) {
                                                            rowx.add(" - ");
                                                        }
                                                        if (employee.getResigned() == 1) {
                                                            rowx.add(" Yes ");
                                                        }
                                                        rowx.add("" + level);
                                                        rowx.add("" + Formater.formatTimeLocale(empLevel.getStartDate(), "dd-MMM-yyyy"));
                                                        rowx.add("" + Formater.formatTimeLocale(empLevel.getEndDate(), "dd-MMM-yyyy"));
                                                        rowx.add(bankName); //10
                                                        rowx.add(BankAcc);
                                                        rowx.add(employee.getTaxRegNr());
                                                        rowx.add(PosFortax);
                                                        rowx.add(employee.getAddressForTax());
                                                        rowx.add("" + periodBegin + " - " + periodEnd);
                                                        rowx.add("" + comStatus);
                                                        rowx.add("" + prevIncome);
                                                        rowx.add("" + prevTaxPaid);
                                                        rowx.add("" + PstPayEmpLevel.stAllowance[mealAllowance]);
                                                        rowx.add("" + PstPayEmpLevel.stOvtIndex[ovtIdxType]);
                                                        lstData.add(rowx);
                                                        lstLinkData.add(String.valueOf(empLevel.getOID()));
                                                    } else {
                                                        if (bulan == periodBln){
                                                            rowx.add((empLevel.getStatusData() == 0 ? "<b>" : "") + String.valueOf(st + 1 + i) + (empLevel.getStatusData() == 0 ? "</b>" : ""));
                                                            rowx.add(employee.getEmployeeNum() + "<input type=\"hidden\" name=\"" + frmObject.fieldNames[FrmPayEmpLevel.FRM_FIELD_EMPLOYEE_ID] + "\" value=\"" + employee.getOID() + "\" size=\"25\" class=\"elemenForm\"><input type=\"hidden\" name=\"empLevelId\" value=\"" + payEmpLevelId + "\" size=\"25\" class=\"elemenForm\">");
                                                            rowx.add(employee.getFullName());
                                                            rowx.add(position.getPosition());
                                                            rowx.add("" + Formater.formatTimeLocale(employee.getCommencingDate(), "dd-MMM-yyyy"));
                                                            //rowx.add(""+employee.getResigned());
                                                            if (employee.getResigned() == 0) {
                                                                rowx.add(" - ");
                                                            }
                                                            if (employee.getResigned() == 1) {
                                                                rowx.add(" Yes ");
                                                            }
                                                            rowx.add("" + level);
                                                            rowx.add("" + Formater.formatTimeLocale(empLevel.getStartDate(), "dd-MMM-yyyy"));
                                                            rowx.add("" + Formater.formatTimeLocale(empLevel.getEndDate(), "dd-MMM-yyyy"));
                                                            rowx.add(bankName); //10
                                                            rowx.add(BankAcc);
                                                            rowx.add(employee.getTaxRegNr());
                                                            rowx.add(PosFortax);
                                                            rowx.add(employee.getAddressForTax());
                                                            rowx.add("" + periodBegin + " - " + periodEnd);
                                                            rowx.add("" + comStatus);
                                                            rowx.add("" + prevIncome);
                                                            rowx.add("" + prevTaxPaid);
                                                            rowx.add("" + PstPayEmpLevel.stAllowance[mealAllowance]);
                                                            rowx.add("" + PstPayEmpLevel.stOvtIndex[ovtIdxType]);
                                                            lstData.add(rowx);
                                                            lstLinkData.add(String.valueOf(empLevel.getOID()));
                                                        }
                                                    }
                                                }
                                            }
                                                
                                            
                                        
                                    } else {

                                        rowx.add((empLevel.getStatusData() == 0 ? "<b>" : "") + String.valueOf(st + 1 + i) + (empLevel.getStatusData() == 0 ? "</b>" : ""));
                                        rowx.add(employee.getEmployeeNum() + "<input type=\"hidden\" name=\"" + frmObject.fieldNames[FrmPayEmpLevel.FRM_FIELD_EMPLOYEE_ID] + "\" value=\"" + employee.getOID() + "\" size=\"25\" class=\"elemenForm\"><input type=\"hidden\" name=\"empLevelId\" value=\"" + payEmpLevelId + "\" size=\"25\" class=\"elemenForm\">");
                                        rowx.add(employee.getFullName());
                                        rowx.add(position.getPosition());
                                        rowx.add("" + Formater.formatTimeLocale(employee.getCommencingDate(), "dd-MMM-yyyy"));
                                        //rowx.add(""+employee.getResigned());
                                        if (employee.getResigned() == 0) {
                                            rowx.add(" - ");
                                        }
                                        if (employee.getResigned() == 1) {
                                            rowx.add(" Yes ");
                                        }
                                        rowx.add("" + level);
                                        rowx.add("" + Formater.formatTimeLocale(empLevel.getStartDate(), "dd-MMM-yyyy"));
                                        rowx.add("" + Formater.formatTimeLocale(empLevel.getEndDate(), "dd-MMM-yyyy"));
                                        rowx.add(bankName); //10
                                        rowx.add(BankAcc);
                                        rowx.add(employee.getTaxRegNr());
                                        rowx.add(PosFortax);
                                        rowx.add(employee.getAddressForTax());
                                        rowx.add("" + periodBegin + " - " + periodEnd);
                                        rowx.add("" + comStatus);
                                        rowx.add("" + prevIncome);
                                        rowx.add("" + prevTaxPaid);
                                        rowx.add("" + PstPayEmpLevel.stAllowance[mealAllowance]);
                                        rowx.add("" + PstPayEmpLevel.stOvtIndex[ovtIdxType]);
                                        lstData.add(rowx);
                                        lstLinkData.add(String.valueOf(empLevel.getOID()));
                                    }
                                }
                            }
		
			rowx = new Vector();
			int noAdd = 0;
			if((iCommand == Command.ADD)|| (objectClass.size()<1)){
				noAdd = noAdd+1; 
				//System.out.println("masuk ADD");
				rowx.add((empLevel.getStatusData()==0?"<b>":"")+ String.valueOf( 1 + i) +(empLevel.getStatusData()==0?"</b>":""));
				rowx.add(employee.getEmployeeNum()+"<input type=\"hidden\" name=\"employee_id\" value=\""+employee.getOID()+"\" size=\"25\" class=\"elemenForm\">");
				rowx.add(employee.getFullName());
				rowx.add(position.getPosition());
				rowx.add(""+Formater.formatTimeLocale(employee.getCommencingDate(),"dd-MMM-yyyy"));
				//rowx.add(""+employee.getResigned());
				if(employee.getResigned()==0){
					rowx.add(" - ");
				}
				if(employee.getResigned()==1){
					rowx.add(" Yes ");
				}
				rowx.add(""+ControlCombo.draw("salary_level","formElemen",null, ""+level,sal_value,sal_key) );
				rowx.add(""+ControlDate.drawDateWithStyle("start_date", startDate==null?new Date():startDate, 9, -15,"formElemen"));
                                //update by satrya 2014-04-02
                                rowx.add(""+ControlDate.drawDateWithStyle("end_date", empLevel.getEndDate()==null?new Date():empLevel.getEndDate(), 9, -15,"formElemen"));
				// mengambil nama bank
				PayBanks objBank = new PayBanks();
				String whereCl = PstPayBanks.fieldNames[PstPayBanks.FLD_BANK_ID]+" = '"+bankId+"'";
				String bankName = "";
				Vector listBankChoose = PstPayBanks.list(0,0,whereCl,"");
				if(listBankChoose.size()>0){
					objBank = (PayBanks)listBankChoose.get(0);
					bankName = objBank.getBankName();
				}
				rowx.add(""+ControlCombo.draw("bank_name","formElemen",null, ""+bankName, bank_value,bank_key) );
				rowx.add("<input type=\"text\" name=\"bank_acc\" value=\""+BankAcc+"\" size=\"25\" class=\"elemenForm\">");
				rowx.add(employee.getTaxRegNr());
				rowx.add("<input type=\"text\" name=\"pos_for_tax\" value=\""+PosFortax+"\" size=\"25\" class=\"elemenForm\">");
				rowx.add(employee.getAddressForTax());
				rowx.add("<input type=\"text\" name=\"period_begin\" value=\""+periodBegin+"\" size=\"5\" class=\"elemenForm\"> to <input type=\"text\"name=\"period_end\"  value=\""+periodEnd+"\" size=\"5\" class=\"elemenForm\"> ");
				rowx.add(""+ControlCombo.draw("com_status","formElemen",null, ""+comStatus, commKey, commValue)) ;
				rowx.add("<input type=\"text\" name=\"prev_income\" value=\""+prevIncome+"\" size=\"25\" class=\"elemenForm\">");
				rowx.add("<input type=\"text\" name=\"prev_tax_paid\" value=\""+prevTaxPaid+"\" size=\"25\" class=\"elemenForm\">");
				rowx.add(""+ControlCombo.draw("meal_allowance","formElemen",null, ""+mealAllowance, mealKey, mealValue)) ;
				rowx.add(""+ControlCombo.draw("ovt_idx_type","formElemen",null, ""+ovtIdxType, idxTypeKey, idxTypeValue)) ;
				lstData.add(rowx);
				if(jumlah > 0){
					st = st + 1;
				}
			}
		
	}
	return ctrlist.draw();
}
%>
<%
	if(iCommand == Command.DELETE){
		PstPayEmpLevel.deleteExc(oidPayEmpLevel);
		iCommand = Command.LIST;
	}
%>

<%
	String s_employee_id = null;
    String s_salary_level= null;
    String s_start_date = null;
	String s_bank_name = null;
	String s_bank_acc = null;
	String s_pos_for_tax = null;
	String s_pay_per_begin = null;
	String s_pay_per_end = null;
	String s_com_status = null;
	String s_prev_income= null;
	String s_prev_tax_paid= null;
	String s_date = null;
        String s_level_end = null;
	Date date = new Date();        
        Date level_end = new Date();
	String s_pay_emp_level_id = null;
	String s_meal_allowance = null;
    String s_ovt_idx_type = null;

	// Proses jika command adalah ASVE	
    if (iCommand == Command.SAVE  ) {
		// Inisialisasi variable yang meng-handle nilai2 berikut
        String[] employee_id = null;		
        String[] salary_level = null;
        String[] start_date = null;
        String[] s_end_date = null;
        String[] bank_name = null;
        String[] bank_acc = null;
        String[] pos_for_tax = null;
        String[] period_begin = null;
        String[] period_end = null;
        String[] com_status = null;
        String[] prev_income = null;
        String[] prev_tax_paid = null;
        String[] meal_allowance = null;
        String[] ovt_idx_type = null;
		// Mengambil array nilai2 berikut
        try {
            employee_id = request.getParameterValues("employee_id");
            salary_level = request.getParameterValues("salary_level");
            //start_date = request.getParameterValues("start_date");
			bank_name = request.getParameterValues("bank_name");
			bank_acc = request.getParameterValues("bank_acc");
			pos_for_tax = request.getParameterValues("pos_for_tax");
			period_begin = request.getParameterValues("period_begin");
			period_end = request.getParameterValues("period_end");
			com_status = request.getParameterValues("com_status");
			prev_income = request.getParameterValues("prev_income");
			prev_tax_paid = request.getParameterValues("prev_tax_paid");
			date = FRMQueryString.requestDate(request,"start_date");
                        level_end = FRMQueryString.requestDate(request,"end_date");
			meal_allowance = request.getParameterValues("meal_allowance");
			ovt_idx_type = request.getParameterValues("ovt_idx_type");
        }
        catch (Exception e) 
		{
			System.out.println("Err : "+e.toString());
		}

		for (int i = 0; i < listEmployee.size(); i++) 
			{
				
				// new barcode number			
                /*if ((barcode_number[i].length() > 0)) 
				{*/
                    try 
					{
                       s_employee_id = String.valueOf(employee_id[i]);
                        s_salary_level = String.valueOf(salary_level[i]);
                       // s_start_date = String.valueOf(start_date[i]);
                        s_bank_name = String.valueOf(bank_name[i]);
                        s_bank_acc = String.valueOf(bank_acc[i]);
                        s_pos_for_tax = String.valueOf(pos_for_tax[i]);
                        s_pay_per_begin = String.valueOf(period_begin[i]);
                        s_pay_per_end = String.valueOf(period_end[i]);
                        s_com_status = String.valueOf(com_status[i]);
                         //out.println("s_com_status   "+s_com_status);
                        s_prev_income = String.valueOf(prev_income[i]);
                        s_prev_tax_paid = String.valueOf(prev_tax_paid[i]);
                        s_date = Formater.formatDate(date,"yyyy-MM-dd");
                        s_level_end = Formater.formatDate(level_end,"yyyy-MM-dd");
                        s_meal_allowance = String.valueOf(meal_allowance[i]);
                        s_ovt_idx_type = String.valueOf(ovt_idx_type[i]);
					   
					
							//out.println("payEmpLevelId  "+payEmpLevelId);
						   // cari apakah id employee sudah ada di tabel atau belum untuk melakukan update status
							PayEmpLevel objPayEmp = new PayEmpLevel();
							String whereLevel = PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_EMPLOYEE_ID]+" = '"+Long.parseLong(employee_id[i])+"'";
							Vector listEmpLevelByEmployeeId = PstPayEmpLevel.list(0,0,whereLevel,"");
							if(listEmpLevelByEmployeeId.size()>0){
								objPayEmp = (PayEmpLevel)listEmpLevelByEmployeeId.get(0);
								Date obj_start_date = objPayEmp.getStartDate();
								Date dateInput = Formater.formatDate(s_date,"yyyy-MM-dd");
								long duration = DateCalc.timeDifference(obj_start_date, dateInput);
								if(duration > 0) {
									PstPayEmpLevel.UpdateStatus(Long.parseLong(employee_id[i]));
								}
							}
				       //PstPayEmpLevel.setupEmployee(Long.parseLong(employee_id[i]), s_salary_level,s_date, Long.parseLong(bank_name[i]),s_bank_acc,s_pos_for_tax,Integer.parseInt(period_begin[i]),Integer.parseInt(period_end[i]),s_com_status,s_prev_income,Integer.parseInt(prev_tax_paid[i] ));
					   //PayEmpLevel payEmpLevel = new PayEmpLevel();
					   payEmpLevel.setEmployeeId(Long.parseLong(employee_id[i])) ;
					   payEmpLevel.setLevelCode(s_salary_level);
					   payEmpLevel.setStartDate(date);
                                           payEmpLevel.setEndDate(level_end);
					   payEmpLevel.setBankId(Long.parseLong(bank_name[i]));
					   payEmpLevel.setBankAccNr(s_bank_acc);
					   payEmpLevel.setPosForTax(s_pos_for_tax);
					   payEmpLevel.setPayPerBegin(Integer.parseInt(period_begin[i]));
					   payEmpLevel.setPayPerEnd(Integer.parseInt(period_end[i]));
					   payEmpLevel.setCommencingSt(Integer.parseInt(s_com_status));
					   payEmpLevel.setPrevIncome(Double.parseDouble(s_prev_income));
					   payEmpLevel.setPrevTaxPaid(Integer.parseInt(prev_tax_paid[i]));
					   payEmpLevel.setStatusData(PstPayEmpLevel.CURRENT);
					   payEmpLevel.setMealAllowance(Integer.parseInt(meal_allowance[i]));
					   payEmpLevel.setOvtIdxType(Integer.parseInt(ovt_idx_type[i]));
					   PstPayEmpLevel.insertExc(payEmpLevel);
					// PstPayEmpLevel.setupEmployee(Long.parseLong(employee_id[i]), s_salary_level,s_start_date);//, s_start_date, Long.parseLong(bank_name[i]),s_bank_acc_nr,s_pos_for_tax,Integer.parseInt(s_pay_per_begin[i]),Integer.parseInt(s_pay_per_end[i]),Integer.parseInt(s_com_status[i]),Double.parseDouble(s_prev_income[i]),Double.parseDouble(s_prev_tax_paid[i] ));
					
                    } catch (Exception e) 
					{
					}					
               /* }
                else 
				{
                    s_barcode_number = null;
                }*/

		}
						listEmployee = SessEmployee.listEmployeePayroll(oidDepartment,oidDivision,oidSection,cariName,payrollNum,statusPayroll,dataStatus);

	}

%>
<%
		// untuk edit
	  if (iCommand == Command.FIRST  ) {
		// Inisialisasi variable yang meng-handle nilai2 berikut
    	// Mengambil array nilai2 berikut
        try {
            long pay_emp_level_id1 = FRMQueryString.requestLong(request, "empLevelId");
			String salary_level = FRMQueryString.requestString(request,"salary_level");
			Date startDate = FRMQueryString.requestDate(request,"start_date");
                        Date levelEndDate = FRMQueryString.requestDate(request,"end_date");
            long bank_name = FRMQueryString.requestLong(request,"bank_name");
			String bank_acc = FRMQueryString.requestString(request,"bank_acc");
			String pos_for_tax = FRMQueryString.requestString(request,"pos_for_tax");
			int period_begin = FRMQueryString.requestInt(request,"period_begin");
			int period_end = FRMQueryString.requestInt(request,"period_end");
			int com_status = FRMQueryString.requestInt(request,"com_status");
			double prev_income = FRMQueryString.requestDouble(request,"prev_income");
			int prev_tax_paid = FRMQueryString.requestInt(request,"prev_tax_paid");
			String str_date = Formater.formatDate(startDate,"yyyy-MM-dd");
                        String str_end_date = Formater.formatDate(levelEndDate,"yyyy-MM-dd");
			int meal_allowance = FRMQueryString.requestInt(request,"meal_allowance");
			int ovt_idx_type = FRMQueryString.requestInt(request,"ovt_idx_type");			
			PstPayEmpLevel.updateSetupEmployee(salary_level,str_date,str_end_date, bank_name,bank_acc,pos_for_tax,period_begin,period_end,com_status,prev_income,prev_tax_paid,pay_emp_level_id1,meal_allowance,ovt_idx_type);
				
			}
			//System.out.println("oidoidEmployee...."+employee_id);
			
	    
        catch (Exception e) 
		{
			System.out.println("Err : "+e.toString());
		}

	}

%>

<!-- End of JSP Block -->
<html>
<!-- #BeginTemplate "/Templates/main.dwt" --> 
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - </title>
<!-- #EndEditable --> 
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<!-- #BeginEditable "styles" --> 
<link rel="stylesheet" href="../../styles/main.css" type="text/css">
<!-- #EndEditable --> <!-- #BeginEditable "stylestab" --> 
<link rel="stylesheet" href="../../styles/tab.css" type="text/css">
<!-- #EndEditable --> <!-- #BeginEditable "headerscript" --> 
<SCRIPT language=JavaScript>

function fnTrapKD(){
   if (event.keyCode == 13) {
		document.all.aSearch.focus();
		cmdSearch();
   }
}

function cmdSearch(){
	document.frm_pay_emp_level.command.value="<%=Command.LIST%>";
	document.frm_pay_emp_level.action="employee-setup.jsp";
	document.frm_pay_emp_level.submit();
}
function cmdCloseAllSalary(){
	//document.frm_pay_emp_level.command.value="<%=Command.POST%>";
	//document.frm_pay_emp_level.action="employee-setup.jsp";
	//document.frm_pay_emp_level.submit();

	window.open("closeAllSalarySlipForm.jsp", null, "height=550,width=600, status=yes,toolbar=no,menubar=no,location=no, scrollbars=yes");       
}


function cmdSave(){
	document.frm_pay_emp_level.command.value="<%=Command.SAVE%>";
	document.frm_pay_emp_level.aksiCommand.value="0";
	document.frm_pay_emp_level.action="employee-setup.jsp";
	document.frm_pay_emp_level.submit();
}

function cmdEdit(oidPayEmpLevel){
	document.frm_pay_emp_level.pay_emp_level_oid.value=oidPayEmpLevel;
	document.frm_pay_emp_level.command.value="<%=Command.EDIT%>";
	document.frm_pay_emp_level.prev_command.value="<%=prevCommand%>";
	document.frm_pay_emp_level.action="employee-setup.jsp";
	document.frm_pay_emp_level.submit();
}

function cmdEditSave(){
	document.frm_pay_emp_level.aksiCommand.value="3";
	document.frm_pay_emp_level.command.value="<%=Command.FIRST%>";
	document.frm_pay_emp_level.prev_command.value="<%=prevCommand%>";
	document.frm_pay_emp_level.action="employee-setup.jsp";
	document.frm_pay_emp_level.submit();
}

function cmdUpload(){
	document.frm_pay_emp_level.command.value="<%=Command.LOAD %>"; 
	document.frm_pay_emp_level.action="<%=approot%>/system/excel_up/up_salary_struct_v2.jsp";
	document.frm_pay_emp_level.submit();
}

function cmdBack(){
	document.frm_pay_emp_level.command.value="<%=Command.BACK%>";
	document.frm_pay_emp_level.action="employee-setup.jsp";
	document.frm_pay_emp_level.submit();
}

function cmdAdd(){
	emp_department = document.frm_pay_emp_level.department.value;
	document.frm_pay_emp_level.pay_emp_level_oid.value="0";
	document.frm_pay_emp_level.command.value="<%=Command.LIST%>";
	document.frm_pay_emp_level.prev_command.value="<%=prevCommand%>";
	newWindow=window.open("empsearch.jsp?emp_department="+emp_department 
            ,"SelectEmployee", "height=600,width=700,status=yes,toolbar=no,menubar=no,location=no,scrollbars=yes");
        newWindow.focus();
	//document.frm_pay_emp_level.submit();
}

function cmdConfirmDelete(oid){
		var x = confirm(" Are You Sure to Delete?");
		if(x){
			document.frm_pay_emp_level.command.value="<%=Command.DELETE%>";
			document.frm_pay_emp_level.action="employee-setup.jsp";
			document.frm_pay_emp_level.submit();
		}
}

 function fnTrapKD(){
	//alert(event.keyCode);
		switch(event.keyCode) {
			case <%=LIST_PREV%>:
				cmdListPrev();
				break;
			case <%=LIST_NEXT%>:
				cmdListNext();
				break;
			case <%=LIST_FIRST%>:
				cmdListFirst();
				break;
			case <%=LIST_LAST%>:
				cmdListLast();
				break;
			default:
				break;
		}
	}  
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
</SCRIPT>
<!-- #EndEditable --> 
</head>
<body <%=noBack%> bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
     <%if(headerStyle && !verTemplate.equalsIgnoreCase("0")){%> 
           <%@include file="../../styletemplate/template_header.jsp" %>
            <%}else{%>
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
  <%}%>
  <tr> 
    <td width="88%" valign="top" align="left"> 
      <table width="100%" border="0" cellspacing="3" cellpadding="2">
        <tr> 
          <td width="100%"> 
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td height="20"> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" -->Employee 
                  Setup <!-- #EndEditable --> </strong></font> </td>
              </tr>
              <tr> 
                <td> 
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td  style="background-color:<%=bgColorContent%>; "> 
                        <table width="100%" border="0" cellspacing="1" cellpadding="1" >
                          <tr> 
                            <td valign="top"> 
                              <table style="border:1px solid <%=garisContent%>" width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                                <tr> 
                                  <td valign="top"> <!-- #BeginEditable "content" --> 
                                    <form name="frm_pay_emp_level" method="post" action="">
									<input type="hidden" name="command" value="">
										<input type="hidden" name="start" value="<%=start%>">
										<input type="hidden" name="pay_emp_level_oid" value="<%=oidPayEmpLevel%>">
										<input type="hidden" name="employee_oid" value="<%=oidEmployee%>">
										 <input type="hidden" name="prev_command" value="<%=prevCommand%>">
										  <input type="hidden" name="aksiCommand" value="<%=aksiCommand%>">
										   <input type="hidden" name="countList" value="<%=numberInner%>">
                                      <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr> 
                                          <td height="13" width="0%">&nbsp;</td>
                                          <td height="13" width="1%">&nbsp; </td>
                                          <td height="13" width="27%">&nbsp;</td>
                                          <td height="13" width="71%">&nbsp;</td>
                                          <td height="13" width="1%" nowrap>&nbsp;</td>
                                          <td height="13" width="50%" nowrap>&nbsp;</td>
                                        </tr>
                                        <tr> 
                                          <td height="13" width="0%">&nbsp;</td>
                                          <td height="13" colspan="5">
                                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                              <tr> 
                                                <td height="24" width="17%" align="left">Division 
                                                  :</td>
                                                <td height="24" width="28%"> 
                                                  <%
												 	
													  Vector listDivision = PstDivision.list(0, 0, "", "DIVISION");										  
													  Vector divValue = new Vector(1,1);
													  Vector divKey = new Vector(1,1);
													  divValue.add("0");
                                                      divKey.add("select ..."); 
													  for(int d=0;d<listDivision.size();d++)
													  {
														Division division = (Division)listDivision.get(d);
														divValue.add(""+division.getOID());
														divKey.add(division.getDivision());										  
													  }
													  out.println(ControlCombo.draw("division",null,""+oidDivision,divValue,divKey));
                                                        %><%//= ControlCombo.draw(frmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_DIVISION_ID],"formElemen",null, ""+srcEmployee.getDivisionId(), divValue, divKey, " onkeydown=\"javascript:fnTrapKD()\"") %> </td> 
                                                <td height="24" width="12%" nowrap align="left">Department 
                                                  : </td>
                                                <td height="24" width="17%" nowrap> 
                                                   <% 
                                                            Vector dept_value = new Vector(1,1);
                                                            Vector dept_key = new Vector(1,1);        
															dept_value.add("0");
                                                            dept_key.add("select ...");                                                          
                                                            Vector listDept = PstDepartment.list(0, 0, "", " DEPARTMENT ");                                                        
                                                            for (int i = 0; i < listDept.size(); i++) {
                                                                    Department dept = (Department) listDept.get(i);
                                                                    dept_key.add(dept.getDepartment());
                                                                    dept_value.add(String.valueOf(dept.getOID()));
                                                            }
														out.println(ControlCombo.draw("department",null,""+oidDepartment,dept_value,dept_key));

                                                        %><%//= ControlCombo.draw(frmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_DEPARTMENT],"formElemen",null, ""+srcEmployee.getDepartment(), dept_value, dept_key, " onkeydown=\"javascript:fnTrapKD()\"") %> 
                                                </td>
                                                <td height="24" width="7%" nowrap align="left">Section 
                                                  : </td>
                                                <td height="24" width="4%" nowrap> 
                                                  <% 
                                                            Vector sec_value = new Vector(1,1);
                                                            Vector sec_key = new Vector(1,1); 
															sec_value.add("0");
                                                            sec_key.add("select ...");
                                                            //Vector listSec = PstSection.list(0, 0, "", " DEPARTMENT_ID, SECTION ");
															Vector listSec = PstSection.list(0, 0, "", " SECTION ");
                                                            for (int i = 0; i < listSec.size(); i++) {
                                                                    Section sec = (Section) listSec.get(i);
                                                                    sec_key.add(sec.getSection());
                                                                    sec_value.add(String.valueOf(sec.getOID()));
                                                            }
															out.println(ControlCombo.draw("section",null,""+oidSection,sec_value,sec_key));

															%><%//= ControlCombo.draw(frmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_SECTION],"formElemen",null, "" + srcEmployee.getSection(), sec_value, sec_key, " onkeydown=\"javascript:fnTrapKD()\"") %>
                                                </td>
                                                <td width="15%">&nbsp;</td>
                                              </tr>
                                              <tr> 
                                                <td width="17%" height="30" nowrap>Employee 
                                                  Name: </td>
                                                <td width="28%"> 
											     <input type="text" name="cariName"  value="<%=""+cariName%>" class="elemenForm" size="30" onkeydown="javascript:fnTrapKD()">
                                                </td>
                                                <td width="12%" align="left">Payroll Nr. : </td>
                                                <td width="17%"> 
                                                  <input type="text" name="payrollNum"  value="<%= ""+payrollNum %>" class="elemenForm" onkeydown="javascript:fnTrapKD()">
                                                </td>
                                                <td>Period</td>
                                                <td>
                                                    <select name="period_bln">
                                                        <option value="0">
                                                         - select -
                                                        </option>
                                                        <%
                                                        
                                                        String[] blnName = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
                                                        String selectedBln = "";
                                                        int bln = 0;
                                                            for(int p=0; p<12; p++){
                                                                if ((periodBln-1) == p){
                                                                    selectedBln = " selected ";
                                                                } else {
                                                                    selectedBln = "";
                                                                }
                                                                bln++;
                                                                %>
                                                                <option value="<%=bln%>" <%=selectedBln%>><%=blnName[p]%></option>
                                                                <%
                                                            }
                                                        
                                                        %>
                                                        
                                                    </select>
                                                    <select name="period_thn">
                                                        <option value="0">-select-</option>
                                                        <%
                                                            int[] thnName = {2011,2012,2013,2014,2015,2016,2017,2018,2019,2020};
                                                            String selectedThn = "";
                                                            int thn = 2010;
                                                            for (int t=0; t<10; t++){
                                                                if (periodThn == thnName[t]){
                                                                    selectedThn = " selected ";
                                                                } else {
                                                                    selectedThn = "";
                                                                }
                                                                thn++;
                                                                %>
                                                                <option value="<%=thn%>" <%=selectedThn%>><%=""+thnName[t]%></option>
                                                                <%
                                                            }
                                                        %>
                                                    </select>
                                                </td>
                                                <td width="15%">&nbsp;</td>
                                              </tr>
                                              <tr> 
                                                <td width="17%" height="26">Employee status 
                                                  :</td>
                                                 <td width="28%"> 
												 <% if(statusPayroll==0){%>
												 		<input type="radio" name="statusPayroll" value="0" checked>
                                                        Aktive 
                                                        <input type="radio" name="statusPayroll" value="1">
                                                        Resign This Month
                                                        <input type="radio" name="statusPayroll" value="2" >
                                                        All <% }%>
													<% 
													if(statusPayroll==1){%>
												 		<input type="radio" name="statusPayroll" value="0" >
                                                        Aktive 
                                                        <input type="radio" name="statusPayroll" value="1" checked>
                                                        Resign This Month
                                                        <input type="radio" name="statusPayroll" value="2" >
                                                        All <% }%>
												<%
													if(statusPayroll==2){%>
												 		<input type="radio" name="statusPayroll" value="0" >
                                                        Aktive 
                                                        <input type="radio" name="statusPayroll" value="1" >
                                                        Resign This Month
                                                        <input type="radio" name="statusPayroll" value="2" checked>
                                                        All <% }%>
													</td>
                                                 
                                              </tr>
                                              <tr> 
                                                <td width="17%" height="31">Data status :</td>
                                                <td width="28%"> 
														<%
															if(dataStatus==0){%>
													
																<input type="radio" name="dataStatus" value="0" checked >
																Current 
																<input type="radio" name="dataStatus" value="1">
																History
																<input type="radio" name="dataStatus" value="2" >
																All 
																<% } %>
																<%
															if(dataStatus==1){%>
													
																<input type="radio" name="dataStatus" value="0"  >
																Current 
																<input type="radio" name="dataStatus" value="1" checked>
																History
																<input type="radio" name="dataStatus" value="2" >
																All 
																<% } %>
																<%
																if(dataStatus==2){%>
													
																<input type="radio" name="dataStatus" value="0"  >
																Current 
																<input type="radio" name="dataStatus" value="1" >
																History
																<input type="radio" name="dataStatus" value="2" checked>
																All 
																<% } %>
															</td>	
                                              </tr>
                                              <tr> 
                                                            <td width="17%"><a href="javascript:cmdSearch()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','<%=approot%>/images/BtnSearchOn.jpg',1)" id="aSearch"><img name="Image10" border="0" src="<%=approot%>/images/BtnSearch.jpg" width="24" height="24" alt="Search Employee"></a>
                                                            <img src="<%=approot%>/images/spacer.gif" width="6" height="1"></td>
                                                            <td width="28%" class="command" nowrap><a href="javascript:cmdSearch()">Search 
                                                              for Employee</a> | <a href="javascript:cmdUpload()">Upload from Excel</a> | <a href="javascript:cmdCloseAllSalary()">Close all Salary</a></td></tr>
											
                                            </table>
											<table width="100%" border="0">
										 	<%if((listEmployee!=null)&&(listEmployee.size()>0)){
											//System.out.println("listEmployee "+listEmployee.size());%>
												<tr> 
												
											  <td   width="100%" colspan="6" height="8"><%=drawList(iCommand,frmPayEmpLevel, payEmpLevel,listEmployee, oidPayEmpLevel,start,countList, periodBln, periodThn)%></td>
											</tr>
										  <%}else{%>
											<tr> 
											
                                          <td height="8" width="17%" class="comment"><%//=drawList(iCommand,frmPayEmpLevel, payEmpLevel,listEmployee, oidPayEmpLevel,start,countList)%><span class="comment"><br>
                                            &nbsp;No Employee available</span> 
                                          </td>
                                        </tr>
										 <%}%>
										  
										</table>

                                          </td>
                                        </tr>
                                        <tr> 
                                          <td width="0%">&nbsp;</td>
                                          <td colspan="5"> 
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td class="listtitle" width="0%">&nbsp;</td>
                                          <td class="listtitle" colspan="5">&nbsp;</td>
                                        </tr>
                                      
                                      </table>
									  <table width="100%" border="0">
									 <%
									 
									 	 if((listEmployee.size()>0) &&!(iCommand==Command.ADD) &&!(iCommand==Command.EDIT)||(iCommand==Command.ASK)|| (iCommand==0)){%>
											<tr align="left" valign="top"> 
											 <td>
											 <a href="javascript:cmdAdd()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnSave.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/BtnSave.jpg" width="24" height="24" alt="Save Data"></a>
											  <a href="javascript:cmdAdd()" class="command">Add Employee Setup</a> &nbsp;&nbsp; &nbsp;&nbsp;
											  <a href="javascript:cmdBack()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnBack.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/BtnBack.jpg" width="24" height="24" alt="Back to List"></a>
											  <a href="javascript:cmdBack()" class="command">Back to Employee Search</a> </td>
											</tr>
										<%}%>
										<%
									 	 if((listEmployee.size()>0) &&(iCommand==Command.EDIT) ||(iCommand==Command.ASK)){%>
											<tr align="left" valign="top"> 
											 <td>
											 <a href="javascript:cmdEditSave()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnSave.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/BtnSave.jpg" width="24" height="24" alt="Save Data"></a>
											  <a href="javascript:cmdEditSave()" class="command">Save Employee Setup</a> &nbsp;&nbsp; &nbsp;&nbsp;
											  	<img name="Image261" border="0" src="<%=approot%>/images/BtnDel.jpg" width="24" height="24" alt="Delete">
										 		<a href="javascript:cmdConfirmDelete('<%=oidPayEmpLevel%>')" class="command">Delete Benefit</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											 <a href="javascript:cmdBack()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnBack.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/BtnBack.jpg" width="24" height="24" alt="Back to List"></a>
											  <a href="javascript:cmdBack()" class="command">Back to Employee Search</a> </td>
											</tr>
										<%}%>
										
										<%
									 	 if((listEmployee.size()>0) &&(iCommand==Command.ADD) ||(iCommand==Command.ASK)){%>
											<tr align="left" valign="top"> 
											 <td>
											 <a href="javascript:cmdSave()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnSave.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/BtnSave.jpg" width="24" height="24" alt="Save Data"></a>
											  <a href="javascript:cmdSave()" class="command">Save Employee Setup</a> &nbsp;&nbsp; &nbsp;&nbsp;
											 <a href="javascript:cmdBack()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnBack.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/BtnBack.jpg" width="24" height="24" alt="Back to List"></a>
											  <a href="javascript:cmdBack()" class="command">Back to Employee Search</a> </td>
											</tr>
										<%}
									 %>
									  
									</table>

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
  <%if(headerStyle && !verTemplate.equalsIgnoreCase("0")){%>
            <tr>
                            <td valign="bottom">
                               <!-- untuk footer -->
                                <%@include file="../../footer.jsp" %>
                            </td>
                            
            </tr>
            <%}else{%>
            <tr> 
                <td colspan="2" height="20" > <!-- #BeginEditable "footer" --> 
      <%@ include file = "../../main/footer.jsp" %>
                <!-- #EndEditable --> </td>
            </tr>
            <%}%>
</table>
</body>
<!-- #BeginEditable "script" --> 
	<script language="JavaScript">
	var oBody = document.body;
	var oSuccess = oBody.attachEvent('onkeydown',fnTrapKD);
</script>
<!-- #EndEditable --> <!-- #EndTemplate -->
</html>
