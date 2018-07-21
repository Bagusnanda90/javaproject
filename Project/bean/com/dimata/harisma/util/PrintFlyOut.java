/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.util;

import com.dimata.harisma.entity.admin.AppObjInfo;
import com.dimata.harisma.entity.template.Flyout;
import com.dimata.harisma.session.admin.SessUserSession;
import com.dimata.util.lang.I_Dictionary;
import javax.servlet.jsp.JspWriter;


/**
 *
 * @author Kartika
 */
public class PrintFlyOut {
    
    
    public static void flyOutBank (boolean  isMSIEE, SessUserSession userSession,  String approot, JspWriter out,  String url, I_Dictionary dictionaryD, String namaUser1){
        Flyout flyout = new Flyout();
flyout.setUrlKlik(url);
if(namaUser1.equals("Employee")){
    flyout.addMenuFlyOut("employee.jsp", dictionaryD.getWord(I_Dictionary.EMPLOYEE), dictionaryD.getWord(I_Dictionary.DATA_BANK), false, null, "data_bank", ""+approot+"/employee/databank/employee_view_new.jsp", "", userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_EMPLOYEE, AppObjInfo.G2_MENU_DATABANK, AppObjInfo.OBJ_MENU_DATABANK), 
             AppObjInfo.COMMAND_VIEW)));
} else {
    flyout.addMenuFlyOut("employee.jsp", dictionaryD.getWord(I_Dictionary.EMPLOYEE), dictionaryD.getWord(I_Dictionary.DATA_BANK), false, null, "data_bank", ""+approot+"/employee/databank/srcemployee.jsp", "", userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_EMPLOYEE, AppObjInfo.G2_MENU_DATABANK, AppObjInfo.OBJ_MENU_DATABANK), 
             AppObjInfo.COMMAND_VIEW)));
}

/* Menu Organisasi */
/*
1) Organisasi
  Organsasi Perusahaan
  Cetak Unit Kerja
  Posisi History
  Posisi
  Informasi Pejabat
  Struktur Organisasi
  KPI
*/
flyout.addMenuFlyOut("organisasi.jsp", dictionaryD.getWord(I_Dictionary.COMPANY_ORGANIZATION), dictionaryD.getWord(I_Dictionary.ORGANIZATION_STRUCTURE), false, null, "company_structure", ""+approot+"/organization/organization_search.jsp", "", userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_ORGANISASI, AppObjInfo.G2_MENU_STRUKTUR_ORGANISASI, AppObjInfo.OBJ_MENU_STRUKTUR_ORGANISASI), 
             AppObjInfo.COMMAND_VIEW)));
/*
flyout.addMenuFlyOut("organisasi.jsp", dictionaryD.getWord(I_Dictionary.COMPANY_ORGANIZATION), dictionaryD.getWord(I_Dictionary.PRINT_PER_DIVISION), false, null, "position", ""+approot+"/employee/databank/srcemployee.jsp", "", userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_ORGANISASI, AppObjInfo.G2_MENU_CETAK_UNIT_KERJA, AppObjInfo.OBJ_MENU_CETAK_UNIT_KERJA), 
             AppObjInfo.COMMAND_VIEW)));
flyout.addMenuFlyOut("organisasi.jsp", dictionaryD.getWord(I_Dictionary.COMPANY_ORGANIZATION), "Sejarah Jabatan", false, null, "company_structure", ""+approot+"/employee/databank/srcEmployee_structure.jsp", "", userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_ORGANISASI, AppObjInfo.G2_MENU_SEJARAH_JABATAN, AppObjInfo.OBJ_MENU_SEJARAH_JABATAN), 
             AppObjInfo.COMMAND_VIEW)));
*/
/* Add New by Hendra Putu | 2016-01-13 */
flyout.addMenuFlyOut("organisasi.jsp", dictionaryD.getWord(I_Dictionary.COMPANY_ORGANIZATION), "Jumlah Karyawan", false, null, "search_amount_emp", ""+approot+"/masterdata/search_amount_emp.jsp", "", userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_MASTERDATA, AppObjInfo.G2_MENU_MD_COMPANY, AppObjInfo.OBJ_MENU_COMMPANY), 
             AppObjInfo.COMMAND_VIEW)));
/*
flyout.addMenuFlyOut("organisasi.jsp", dictionaryD.getWord(I_Dictionary.COMPANY_ORGANIZATION), dictionaryD.getWord(I_Dictionary.DEPT_HEAD_DETAILS), false, null, "new_employee", ""+approot+"/employee/databank/srcemployee.jsp", "",userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_ORGANISASI, AppObjInfo.G2_MENU_INFORMASI_PEJABAT, AppObjInfo.OBJ_MENU_INFORMASI_PEJABAT), 
             AppObjInfo.COMMAND_VIEW)));
*/
flyout.addMenuFlyOut("organisasi.jsp", dictionaryD.getWord(I_Dictionary.COMPANY_ORGANIZATION), "Master Data Organisasi", true, dictionaryD.getWord(I_Dictionary.COMPANY), "company", "#", ""+approot+"/masterdata/company.jsp",	userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_ORGANISASI, AppObjInfo.G2_MENU_STRUKTUR_PERUSAHAAN, AppObjInfo.OBJ_MENU_PERUSAHAAN), 
             AppObjInfo.COMMAND_VIEW)));
			 
flyout.addMenuFlyOut("organisasi.jsp", dictionaryD.getWord(I_Dictionary.COMPANY_ORGANIZATION), "Master Data Organisasi", true, dictionaryD.getWord(I_Dictionary.DIVISION), "division", "#", ""+approot+"/masterdata/division.jsp",	userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_ORGANISASI, AppObjInfo.G2_MENU_STRUKTUR_PERUSAHAAN, AppObjInfo.OBJ_MENU_SATUAN_KERJA), 
             AppObjInfo.COMMAND_VIEW)));
			 
flyout.addMenuFlyOut("organisasi.jsp", dictionaryD.getWord(I_Dictionary.COMPANY_ORGANIZATION), "Master Data Organisasi", true, dictionaryD.getWord(I_Dictionary.DEPARTMENT), "department", "#", ""+approot+"/masterdata/department.jsp",	userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_ORGANISASI, AppObjInfo.G2_MENU_STRUKTUR_PERUSAHAAN, AppObjInfo.OBJ_MENU_UNIT), 
             AppObjInfo.COMMAND_VIEW)));

flyout.addMenuFlyOut("organisasi.jsp", dictionaryD.getWord(I_Dictionary.COMPANY_ORGANIZATION), "Master Data Organisasi", true, dictionaryD.getWord(I_Dictionary.SECTION), "srcsection", "#", ""+approot+"/masterdata/srcsection.jsp",	userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_ORGANISASI, AppObjInfo.G2_MENU_STRUKTUR_PERUSAHAAN, AppObjInfo.OBJ_MENU_SUB_UNIT), 
             AppObjInfo.COMMAND_VIEW)));
			 
flyout.addMenuFlyOut("organisasi.jsp", dictionaryD.getWord(I_Dictionary.COMPANY_ORGANIZATION), "Master Data Organisasi", true, dictionaryD.getWord(I_Dictionary.POSITION), "position_new", "#", ""+approot+"/masterdata/position_new.jsp",userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_ORGANISASI, AppObjInfo.G2_MENU_STRUKTUR_PERUSAHAAN, AppObjInfo.OBJ_MENU_JABATAN), 
             AppObjInfo.COMMAND_VIEW)));
			 

flyout.addMenuFlyOut("organisasi.jsp", dictionaryD.getWord(I_Dictionary.COMPANY_ORGANIZATION), "Master Data Organisasi", true, dictionaryD.getWord(I_Dictionary.PUBLIC_HOLIDAY), "publicHoliday", "#", ""+approot+"/masterdata/publicHoliday.jsp",	userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_ORGANISASI, AppObjInfo.G2_MENU_STRUKTUR_PERUSAHAAN, AppObjInfo.OBJ_MENU_PUBLIC_LIBURAN), 
             AppObjInfo.COMMAND_VIEW)));

flyout.addMenuFlyOut("organisasi.jsp", dictionaryD.getWord(I_Dictionary.COMPANY_ORGANIZATION), "Master Data Organisasi", true, dictionaryD.getWord(I_Dictionary.DIVISION)+" Type", "division_type", "#", ""+approot+"/masterdata/division_type.jsp",	userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_ORGANISASI, AppObjInfo.G2_MENU_STRUKTUR_PERUSAHAAN, AppObjInfo.OBJ_MENU_DIVISION_TYPE), 
             AppObjInfo.COMMAND_VIEW)));

flyout.addMenuFlyOut("organisasi.jsp", dictionaryD.getWord(I_Dictionary.COMPANY_ORGANIZATION), "Master Data Organisasi", true, dictionaryD.getWord(I_Dictionary.DEPARTMENT)+" Type", "department_type", "#", ""+approot+"/masterdata/department_type.jsp",	userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_ORGANISASI, AppObjInfo.G2_MENU_STRUKTUR_PERUSAHAAN, AppObjInfo.OBJ_MENU_DEPARTMENT_TYPE), 
             AppObjInfo.COMMAND_VIEW)));

flyout.addMenuFlyOut("organisasi.jsp", dictionaryD.getWord(I_Dictionary.COMPANY_ORGANIZATION), "Master Data Organisasi", true, "Manajemen Jabatan", "position_management", "#", ""+approot+"/organization/position_management.jsp",userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_ORGANISASI, AppObjInfo.G2_MENU_STRUKTUR_PERUSAHAAN, AppObjInfo.OBJ_MENU_JABATAN), 
             AppObjInfo.COMMAND_VIEW)));

flyout.addMenuFlyOut("organisasi.jsp", dictionaryD.getWord(I_Dictionary.COMPANY_ORGANIZATION), "Master Data Organisasi", true, "Pemetaan Organisasi", "organization_mapping", "#", ""+approot+"/organization/organization_mapping.jsp",userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_ORGANISASI, AppObjInfo.G2_MENU_STRUKTUR_PERUSAHAAN, AppObjInfo.OBJ_MENU_JABATAN), 
             AppObjInfo.COMMAND_VIEW)));

flyout.addMenuFlyOut("organisasi.jsp", dictionaryD.getWord(I_Dictionary.COMPANY_ORGANIZATION), "Structure Template", false, null, "structure_template", ""+approot+"/masterdata/structure_template.jsp", "", userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_ORGANISASI, AppObjInfo.G2_MENU_STRUKTUR_TEMPLATE, AppObjInfo.OBJ_MENU_STRUKTUR_TEMPLATE), 
             AppObjInfo.COMMAND_VIEW)));
			 
/* end structur organisasi */

/* Menu Karyawan */
/*
2) Karyawan
  Databank
  Pencarian Karyawan
  Cetak Karyawan
*/
flyout.addMenuFlyOut("employee.jsp", dictionaryD.getWord(I_Dictionary.EMPLOYEE), dictionaryD.getWord(I_Dictionary.DATA_BANK), false, null, "data_bank", ""+approot+"/employee/databank/srcemployee.jsp", "", userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_DATABANK, AppObjInfo.OBJ_DATABANK), 
             AppObjInfo.COMMAND_VIEW)));
/*
flyout.addMenuFlyOut("employee.jsp", dictionaryD.getWord(I_Dictionary.EMPLOYEE), dictionaryD.getWord(I_Dictionary.EMPLOYEE_SEARCH), false, null, "data_bank", ""+approot+"/employee/databank/srcemployee.jsp", "", userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_EMPLOYEE, AppObjInfo.G2_MENU_DATABANK, AppObjInfo.OBJ_MENU_DATABANK), 
             AppObjInfo.COMMAND_VIEW)));
flyout.addMenuFlyOut("employee.jsp", dictionaryD.getWord(I_Dictionary.EMPLOYEE), dictionaryD.getWord(I_Dictionary.PRINT_EMPLOYEE), false, null, "data_bank", ""+approot+"/employee/databank/srcemployee.jsp", "", userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_EMPLOYEE, AppObjInfo.G2_MENU_DATABANK, AppObjInfo.OBJ_MENU_DATABANK), 
             AppObjInfo.COMMAND_VIEW)));
*/
flyout.addMenuFlyOut("employee.jsp", dictionaryD.getWord(I_Dictionary.EMPLOYEE), dictionaryD.getWord(I_Dictionary.CUSTOM_FIELD_MASTER), false, null, "data_bank", ""+approot+"/masterdata/custom_field_master.jsp", "", userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_EMPLOYEE, AppObjInfo.G2_MENU_DATABANK, AppObjInfo.OBJ_MENU_DATABANK), 
             AppObjInfo.COMMAND_VIEW)));

flyout.addMenuFlyOut("employee.jsp", dictionaryD.getWord(I_Dictionary.EMPLOYEE), "Pencarian Cepat", false, null, "data_bank", ""+approot+"/employee/databank/employee_search.jsp", "", userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_EMPLOYEE, AppObjInfo.G2_MENU_DATABANK, AppObjInfo.OBJ_MENU_DATABANK), 
             AppObjInfo.COMMAND_VIEW)));


flyout.addMenuFlyOut("employee.jsp", dictionaryD.getWord(I_Dictionary.EMPLOYEE), "Master Data", true,  dictionaryD.getWord("EDUCATION"), "employee", "#", ""+approot+"/masterdata/education.jsp",	userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_EMPLOYEE, AppObjInfo.G2_MENU_DATABANK, AppObjInfo.OBJ_MENU_DATABANK), 
             AppObjInfo.COMMAND_VIEW)));

flyout.addMenuFlyOut("employee.jsp", dictionaryD.getWord(I_Dictionary.EMPLOYEE), "Master Data", true,  "Education Score", "employee", "#", ""+approot+"/masterdata/education_score.jsp",	userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_EMPLOYEE, AppObjInfo.G2_MENU_DATABANK, AppObjInfo.OBJ_MENU_DATABANK), 
             AppObjInfo.COMMAND_VIEW)));
			 
flyout.addMenuFlyOut("employee.jsp", dictionaryD.getWord(I_Dictionary.EMPLOYEE), "Master Data", true, dictionaryD.getWord("FAMILY_RELATIONSHIP"), "employee", "#", ""+approot+"/masterdata/famRelation.jsp",	userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_EMPLOYEE, AppObjInfo.G2_MENU_DATABANK, AppObjInfo.OBJ_MENU_DATABANK), 
             AppObjInfo.COMMAND_VIEW)));
			 
flyout.addMenuFlyOut("employee.jsp", dictionaryD.getWord(I_Dictionary.EMPLOYEE), "Master Data", true, dictionaryD.getWord("WARNING"), "employee", "#", ""+approot+"/masterdata/empwarning.jsp",	userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_EMPLOYEE, AppObjInfo.G2_MENU_DATABANK, AppObjInfo.OBJ_MENU_DATABANK), 
             AppObjInfo.COMMAND_VIEW)));
			 
flyout.addMenuFlyOut("employee.jsp", dictionaryD.getWord(I_Dictionary.EMPLOYEE), "Master Data", true, dictionaryD.getWord("REPRIMAND"), "employee", "#", ""+approot+"/masterdata/empreprimand.jsp",	userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_EMPLOYEE, AppObjInfo.G2_MENU_DATABANK, AppObjInfo.OBJ_MENU_DATABANK), 
             AppObjInfo.COMMAND_VIEW)));
			 
flyout.addMenuFlyOut("employee.jsp", dictionaryD.getWord(I_Dictionary.EMPLOYEE), "Master Data", true, dictionaryD.getWord("LEVEL"), "employee", "#", ""+approot+"/masterdata/level.jsp",	userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_EMPLOYEE, AppObjInfo.G2_MENU_DATABANK, AppObjInfo.OBJ_MENU_DATABANK), 
             AppObjInfo.COMMAND_VIEW)));
			 
flyout.addMenuFlyOut("employee.jsp", dictionaryD.getWord(I_Dictionary.EMPLOYEE), "Master Data", true, dictionaryD.getWord("CATEGORY"), "employee", "#", ""+approot+"/masterdata/empcategory.jsp",	userSession.checkPrivilege(AppObjInfo.composeCode(
            AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_EMPLOYEE, AppObjInfo.G2_MENU_DATABANK, AppObjInfo.OBJ_MENU_DATABANK), 
             AppObjInfo.COMMAND_VIEW)));
			 
flyout.addMenuFlyOut("employee.jsp", dictionaryD.getWord(I_Dictionary.EMPLOYEE), "Master Data", true, dictionaryD.getWord(I_Dictionary.RELIGION), "employee", "#", ""+approot+"/masterdata/religion.jsp",	userSession.checkPrivilege(AppObjInfo.composeCode(
            AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_EMPLOYEE, AppObjInfo.G2_MENU_DATABANK, AppObjInfo.OBJ_MENU_DATABANK), 
             AppObjInfo.COMMAND_VIEW)));
			 
flyout.addMenuFlyOut("employee.jsp", dictionaryD.getWord(I_Dictionary.EMPLOYEE), "Master Data", true, dictionaryD.getWord(I_Dictionary.MARITAL_STATUS), "employee", "#", ""+approot+"/masterdata/marital.jsp",	userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_EMPLOYEE, AppObjInfo.G2_MENU_DATABANK, AppObjInfo.OBJ_MENU_DATABANK), 
             AppObjInfo.COMMAND_VIEW)));
			 
flyout.addMenuFlyOut("employee.jsp", dictionaryD.getWord(I_Dictionary.EMPLOYEE), "Master Data", true, dictionaryD.getWord("RACE"), "employee", "#", ""+approot+"/masterdata/race.jsp",	userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_EMPLOYEE, AppObjInfo.G2_MENU_DATABANK, AppObjInfo.OBJ_MENU_DATABANK), 
             AppObjInfo.COMMAND_VIEW)));
/*
flyout.addMenuFlyOut("employee.jsp", dictionaryD.getWord(I_Dictionary.EMPLOYEE), "Master Data", true, dictionaryD.getWord(I_Dictionary.COUNTRY), "employee", "#", ""+approot+"/masterdata/negara.jsp",	userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_EMPLOYEE, AppObjInfo.G2_MENU_DATABANK, AppObjInfo.OBJ_MENU_DATABANK), 
             AppObjInfo.COMMAND_VIEW)));

flyout.addMenuFlyOut("employee.jsp", dictionaryD.getWord(I_Dictionary.EMPLOYEE), "Master Data", true, dictionaryD.getWord(I_Dictionary.PROVINCE), "employee", "#", ""+approot+"/masterdata/provinsi.jsp",	userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_EMPLOYEE, AppObjInfo.G2_MENU_DATABANK, AppObjInfo.OBJ_MENU_DATABANK), 
             AppObjInfo.COMMAND_VIEW)));

flyout.addMenuFlyOut("employee.jsp", dictionaryD.getWord(I_Dictionary.EMPLOYEE), "Master Data", true, dictionaryD.getWord(I_Dictionary.REGENCY), "employee", "#", ""+approot+"/masterdata/kabupaten.jsp",	userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_EMPLOYEE, AppObjInfo.G2_MENU_DATABANK, AppObjInfo.OBJ_MENU_DATABANK), 
             AppObjInfo.COMMAND_VIEW)));

flyout.addMenuFlyOut("employee.jsp", dictionaryD.getWord(I_Dictionary.EMPLOYEE), "Master Data", true, dictionaryD.getWord(I_Dictionary.SUB_REGENCY), "employee", "#", ""+approot+"/masterdata/kecamatan.jsp",	userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_EMPLOYEE, AppObjInfo.G2_MENU_DATABANK, AppObjInfo.OBJ_MENU_DATABANK), 
             AppObjInfo.COMMAND_VIEW)));*/

flyout.addMenuFlyOut("employee.jsp", dictionaryD.getWord(I_Dictionary.EMPLOYEE), "Master Data", true, dictionaryD.getWord(I_Dictionary.LANGUAGE), "employee", "#", ""+approot+"/masterdata/masterlanguage.jsp",	userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_EMPLOYEE, AppObjInfo.G2_MENU_DATABANK, AppObjInfo.OBJ_MENU_DATABANK), 
             AppObjInfo.COMMAND_VIEW)));
			 
flyout.addMenuFlyOut("employee.jsp", dictionaryD.getWord(I_Dictionary.EMPLOYEE), "Master Data", true, "Image Assign", "employee", "#", ""+approot+"/masterdata/image_assign.jsp",	userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_EMPLOYEE, AppObjInfo.G2_MENU_DATABANK, AppObjInfo.OBJ_MENU_DATABANK), 
             AppObjInfo.COMMAND_VIEW)));
			 
flyout.addMenuFlyOut("employee.jsp", dictionaryD.getWord(I_Dictionary.EMPLOYEE), "Master Data", true, "Resigned Reason", "employee", "#", ""+approot+"/masterdata/resignedreason.jsp",	userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_EMPLOYEE, AppObjInfo.G2_MENU_DATABANK, AppObjInfo.OBJ_MENU_DATABANK), 
             AppObjInfo.COMMAND_VIEW)));
			 
flyout.addMenuFlyOut("employee.jsp", dictionaryD.getWord(I_Dictionary.EMPLOYEE), "Master Data", true, dictionaryD.getWord("AWARD_TYPE"), "employee", "#", ""+approot+"/masterdata/awardtype.jsp",	userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_EMPLOYEE, AppObjInfo.G2_MENU_DATABANK, AppObjInfo.OBJ_MENU_DATABANK), 
             AppObjInfo.COMMAND_VIEW)));			 
			 
flyout.addMenuFlyOut("employee.jsp", dictionaryD.getWord(I_Dictionary.EMPLOYEE), "Master Data", true,dictionaryD.getWord("CUSTOM_FIELD")+" Master", "employee", "#", ""+approot+"/masterdata/custom_field_master.jsp",	userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_EMPLOYEE, AppObjInfo.G2_MENU_DATABANK, AppObjInfo.OBJ_MENU_DATABANK), 
             AppObjInfo.COMMAND_VIEW)));

flyout.addMenuFlyOut("employee.jsp", dictionaryD.getWord(I_Dictionary.EMPLOYEE), "Master Data", true, "Payroll Group", "employee", "#", ""+approot+"/masterdata/PayrollGroup.jsp",	userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_EMPLOYEE, AppObjInfo.G2_MENU_DATABANK, AppObjInfo.OBJ_MENU_DATABANK), 
             AppObjInfo.COMMAND_VIEW)));
flyout.addMenuFlyOut("employee.jsp", dictionaryD.getWord(I_Dictionary.EMPLOYEE), "Master Data", true, dictionaryD.getWord(I_Dictionary.GRADE), "employee", "#", ""+approot+"/masterdata/grade_level.jsp",	userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_EMPLOYEE, AppObjInfo.G2_MENU_DATABANK, AppObjInfo.OBJ_MENU_DATABANK), 
             AppObjInfo.COMMAND_VIEW)));

flyout.addMenuFlyOut("employee.jsp", dictionaryD.getWord(I_Dictionary.EMPLOYEE), "Master Data", true, dictionaryD.getWord(I_Dictionary.RELEVANT_DOCS), "employee", "#", ""+approot+"/masterdata/relevant_doc_group.jsp",	userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_EMPLOYEE, AppObjInfo.G2_MENU_DATABANK, AppObjInfo.OBJ_MENU_DATABANK), 
             AppObjInfo.COMMAND_VIEW)));


flyout.addMenuFlyOut("employee.jsp", dictionaryD.getWord(I_Dictionary.EMPLOYEE), "Geo Area", true, dictionaryD.getWord(I_Dictionary.COUNTRY), "geo_area", "#", ""+approot+"/masterdata/negara.jsp", userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_MASTERDATA, AppObjInfo.G2_MENU_MD_GEO_AREA, AppObjInfo.OBJ_MENU_EMP_COUNTRY), 
             AppObjInfo.COMMAND_VIEW)));
			 
flyout.addMenuFlyOut("employee.jsp", dictionaryD.getWord(I_Dictionary.EMPLOYEE), "Geo Area", true, dictionaryD.getWord(I_Dictionary.PROVINCE), "geo_area", "#", ""+approot+"/masterdata/provinsi.jsp", userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_MASTERDATA, AppObjInfo.G2_MENU_MD_GEO_AREA, AppObjInfo.OBJ_MENU_EMP_PROVINCE), 
             AppObjInfo.COMMAND_VIEW)));
			 
flyout.addMenuFlyOut("employee.jsp", dictionaryD.getWord(I_Dictionary.EMPLOYEE), "Geo Area", true, dictionaryD.getWord(I_Dictionary.REGENCY), "geo_area", "#", ""+approot+"/masterdata/kabupaten.jsp", userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_MASTERDATA, AppObjInfo.G2_MENU_MD_GEO_AREA, AppObjInfo.OBJ_MENU_EMP_REGENCY), 
             AppObjInfo.COMMAND_VIEW)));
			 
flyout.addMenuFlyOut("employee.jsp", dictionaryD.getWord(I_Dictionary.EMPLOYEE), "Geo Area", true, dictionaryD.getWord(I_Dictionary.SUB_REGENCY), "geo_area", "#", ""+approot+"/masterdata/kecamatan.jsp", userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_MASTERDATA, AppObjInfo.G2_MENU_MD_GEO_AREA, AppObjInfo.OBJ_MENU_EMP_SUBREGENCY), 
             AppObjInfo.COMMAND_VIEW)));



/*
flyout.addMenuFlyOut("employee.jsp", dictionaryD.getWord(I_Dictionary.EMPLOYEE), "Master Data", true, "Form Emp Schedule Change", "employee", "#", ""+approot+"/employee/workSchedule/emp_schedule_change_edit.jsp",	userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_EMPLOYEE, AppObjInfo.G2_MENU_DATABANK, AppObjInfo.OBJ_MENU_DATABANK), 
             AppObjInfo.COMMAND_VIEW)));

flyout.addMenuFlyOut("employee.jsp", dictionaryD.getWord(I_Dictionary.EMPLOYEE), "Schedule EO", true, "Form Emp Schedule EO", "employee", "#", ""+approot+"/employee/workSchedule/emp_schedule_EO.jsp",	userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_EMPLOYEE, AppObjInfo.G2_MENU_DATABANK, AppObjInfo.OBJ_MENU_DATABANK), 
             AppObjInfo.COMMAND_VIEW)));
*/
//add by priska
		 
flyout.addMenuFlyOut("employee.jsp", dictionaryD.getWord(I_Dictionary.EMPLOYEE), dictionaryD.getWord(I_Dictionary.RECRUITMENT), true, dictionaryD.getWord(I_Dictionary.STAFF_REQUISITION), "recruitment", "#", ""+approot+"/employee/recruitment/srcstaffrequisition.jsp", userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_EMPLOYEE, AppObjInfo.G2_MENU_RECRUITMENT, AppObjInfo.OBJ_MENU_STAFF_REQUISITION), 
             AppObjInfo.COMMAND_VIEW))); 

flyout.addMenuFlyOut("employee.jsp", dictionaryD.getWord(I_Dictionary.EMPLOYEE),  dictionaryD.getWord(I_Dictionary.RECRUITMENT), true, dictionaryD.getWord(I_Dictionary.EMPLOYEMENT_APPLICATION), "recruitment", "#", ""+approot+"/employee/recruitment/srcrecrapplication.jsp", userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_EMPLOYEE, AppObjInfo.G2_MENU_RECRUITMENT, AppObjInfo.OBJ_MENU_EMPLOYEE_APPLICATION), 
             AppObjInfo.COMMAND_VIEW)));
			 
flyout.addMenuFlyOut("employee.jsp", dictionaryD.getWord(I_Dictionary.EMPLOYEE),  dictionaryD.getWord(I_Dictionary.RECRUITMENT), true, dictionaryD.getWord(I_Dictionary.ORIENTATION_CHECKLIST), "recruitment", "#", ""+approot+"/employee/recruitment/srcorichecklist.jsp", userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_EMPLOYEE, AppObjInfo.G2_MENU_RECRUITMENT, AppObjInfo.OBJ_MENU_ORIENTATION_CHECK_LIST), 
             AppObjInfo.COMMAND_VIEW)));

flyout.addMenuFlyOut("employee.jsp", dictionaryD.getWord(I_Dictionary.EMPLOYEE),  dictionaryD.getWord(I_Dictionary.RECRUITMENT), true, dictionaryD.getWord(I_Dictionary.REMINDER), "recruitment", "#", ""+approot+"/employee/recruitment/reminder.jsp", userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_EMPLOYEE, AppObjInfo.G2_MENU_RECRUITMENT, AppObjInfo.OBJ_MENU_REMINDER), 
             AppObjInfo.COMMAND_VIEW)));


		 
flyout.addMenuFlyOut("employee.jsp", dictionaryD.getWord(I_Dictionary.EMPLOYEE), dictionaryD.getWord(I_Dictionary.RECRUITMENT), true, dictionaryD.getWord(I_Dictionary.GENERAL_QUESTION), "recruitment", "#", ""+approot+"/employee/recruitment/recrgeneral.jsp",	userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_MASTERDATA, AppObjInfo.G2_MENU_MD_RECRUITMENT, AppObjInfo.OBJ_MENU_GENERAL_QUESTION), 
             AppObjInfo.COMMAND_VIEW)));
			 
flyout.addMenuFlyOut("employee.jsp", dictionaryD.getWord(I_Dictionary.EMPLOYEE), dictionaryD.getWord(I_Dictionary.RECRUITMENT), true, dictionaryD.getWord(I_Dictionary.ILLNESS_TYPE), "recruitment", "#", ""+approot+"/employee/recruitment/recrillness.jsp",	userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_MASTERDATA, AppObjInfo.G2_MENU_MD_RECRUITMENT, AppObjInfo.OBJ_MENU_ILLNESS_TYPE), 
             AppObjInfo.COMMAND_VIEW)));
			 
flyout.addMenuFlyOut("employee.jsp", dictionaryD.getWord(I_Dictionary.EMPLOYEE), dictionaryD.getWord(I_Dictionary.RECRUITMENT), true, "Interview Point", "recruitment", "#", ""+approot+"/employee/recruitment/recrinterviewpoint.jsp",	userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_MASTERDATA, AppObjInfo.G2_MENU_MD_RECRUITMENT, AppObjInfo.OBJ_MENU_INTERVIEW_POINTS), 
             AppObjInfo.COMMAND_VIEW)));
			 
flyout.addMenuFlyOut("employee.jsp", dictionaryD.getWord(I_Dictionary.EMPLOYEE), dictionaryD.getWord(I_Dictionary.RECRUITMENT), true, "Interviewer", "recruitment", "#", ""+approot+"/employee/recruitment/recrinterviewer.jsp",userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_MASTERDATA, AppObjInfo.G2_MENU_MD_RECRUITMENT, AppObjInfo.OBJ_MENU_INTERVIEWER), 
             AppObjInfo.COMMAND_VIEW)));
			 
flyout.addMenuFlyOut("employee.jsp", dictionaryD.getWord(I_Dictionary.EMPLOYEE), dictionaryD.getWord(I_Dictionary.RECRUITMENT), true, "Interview Factor", "recruitment", "#", ""+approot+"/employee/recruitment/recrinterviewer.jsp",userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_MASTERDATA, AppObjInfo.G2_MENU_MD_RECRUITMENT, AppObjInfo.OBJ_MENU_INTERVIEWER), 
             AppObjInfo.COMMAND_VIEW)));
			 
flyout.addMenuFlyOut("employee.jsp", dictionaryD.getWord(I_Dictionary.EMPLOYEE), dictionaryD.getWord(I_Dictionary.RECRUITMENT), true, "Orientation Group", "recruitment", "#", ""+approot+"/employee/recruitment/origroup.jsp", userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_MASTERDATA, AppObjInfo.G2_MENU_MD_RECRUITMENT, AppObjInfo.OBJ_MENU_ORIENTATION_GROUP), 
             AppObjInfo.COMMAND_VIEW)));
			 
flyout.addMenuFlyOut("employee.jsp", dictionaryD.getWord(I_Dictionary.EMPLOYEE), dictionaryD.getWord(I_Dictionary.RECRUITMENT), true, "Orientation Activity", "recruitment", "#", ""+approot+"/employee/recruitment/oriactivity.jsp", userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_MASTERDATA, AppObjInfo.G2_MENU_MD_RECRUITMENT, AppObjInfo.OBJ_MENU_ORIENTATION_ACTIVITY), 
             AppObjInfo.COMMAND_VIEW)));
			 



/* End Menu Karyawan*/

/*
3) Penggajian (Payroll)
  Pay.Setup
  Overtime
  Pay.Process
  Perubahan Karyawan
  Penghasilan Tambahan
  Slip Gaji
  Simulasi implikasi
*/
flyout.addMenuFlyOut("payroll.jsp", dictionaryD.getWord(I_Dictionary.PAYROLL), dictionaryD.getWord(I_Dictionary.PAYROLL_SETUP), false, null, "general", ""+approot+"/payroll/setup/general_list.jsp", "", userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_PAYROLL, AppObjInfo.G2_MENU_PAYROLL_SETUP, AppObjInfo.OBJ_MENU_GENERAL), 
             AppObjInfo.COMMAND_VIEW)));
			 
flyout.addMenuFlyOut("payroll.jsp", dictionaryD.getWord(I_Dictionary.PAYROLL), dictionaryD.getWord(I_Dictionary.PAYROLL_PERIODE), false, null, "payroll_periode", ""+approot+"/payroll/setup/period.jsp", "", userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_PAYROLL, AppObjInfo.G2_MENU_PAYROLL_SETUP, AppObjInfo.OBJ_MENU_PAYROLL_PERIOD), 
             AppObjInfo.COMMAND_VIEW)));

flyout.addMenuFlyOut("payroll.jsp", dictionaryD.getWord(I_Dictionary.PAYROLL), dictionaryD.getWord(I_Dictionary.BANK_LIST), false, null, "bank_list", ""+approot+"/payroll/setup/list-bank.jsp", "", userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_PAYROLL, AppObjInfo.G2_MENU_PAYROLL_SETUP, AppObjInfo.OBJ_MENU_BANK_LIST), 
             AppObjInfo.COMMAND_VIEW)));

flyout.addMenuFlyOut("payroll.jsp", dictionaryD.getWord(I_Dictionary.PAYROLL), dictionaryD.getWord(I_Dictionary.PAY_SLIP_GROUP), false, null, "pay_slip_group", ""+approot+"/payroll/process/pay_payslip_group.jsp", "", userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_PAYROLL, AppObjInfo.G2_MENU_PAYROLL_SETUP, AppObjInfo.OBJ_MENU_PAY_SLIP_GROUP), 
             AppObjInfo.COMMAND_VIEW)));
// update by Hendra McHen 2015-01-29
flyout.addMenuFlyOut("payroll.jsp", dictionaryD.getWord(I_Dictionary.PAYROLL), dictionaryD.getWord(I_Dictionary.BENEFIT_CONFIGURATION), true, "Benefit Configuration", "benefit_config", "#", ""+approot+"/payroll/setup/benefit_config.jsp", userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_PAYROLL, AppObjInfo.G2_MENU_PAYROLL_SETUP, AppObjInfo.OBJ_MENU_BANK_LIST), 
             AppObjInfo.COMMAND_VIEW)));
flyout.addMenuFlyOut("payroll.jsp", dictionaryD.getWord(I_Dictionary.PAYROLL), dictionaryD.getWord(I_Dictionary.BENEFIT_CONFIGURATION), true, "Benefit Input", "benefit_input", "#", ""+approot+"/payroll/setup/benefit_input.jsp", userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_PAYROLL, AppObjInfo.G2_MENU_PAYROLL_SETUP, AppObjInfo.OBJ_MENU_BANK_LIST), 
             AppObjInfo.COMMAND_VIEW)));
flyout.addMenuFlyOut("payroll.jsp", dictionaryD.getWord(I_Dictionary.PAYROLL), dictionaryD.getWord(I_Dictionary.BENEFIT_CONFIGURATION), true, "Benefit Employee", "benefit_employee", "#", ""+approot+"/payroll/setup/benefit_employee.jsp", userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_PAYROLL, AppObjInfo.G2_MENU_PAYROLL_SETUP, AppObjInfo.OBJ_MENU_BANK_LIST), 
             AppObjInfo.COMMAND_VIEW)));
flyout.addMenuFlyOut("payroll.jsp", dictionaryD.getWord(I_Dictionary.PAYROLL), dictionaryD.getWord(I_Dictionary.BENEFIT_CONFIGURATION), true, "Benefit Input History", "benefit_input_history", "#", ""+approot+"/payroll/setup/benefit_input_history.jsp", userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_PAYROLL, AppObjInfo.G2_MENU_PAYROLL_SETUP, AppObjInfo.OBJ_MENU_BANK_LIST), 
             AppObjInfo.COMMAND_VIEW)));

flyout.addMenuFlyOut("payroll.jsp", dictionaryD.getWord(I_Dictionary.PAYROLL), dictionaryD.getWord(I_Dictionary.SALARY_COMPONENT), false, null, "salary_component", ""+approot+"/payroll/setup/salary-comp.jsp", "", userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_PAYROLL, AppObjInfo.G2_MENU_PAYROLL_SETUP, AppObjInfo.OBJ_MENU_SALARY_COMPONENT), 
             AppObjInfo.COMMAND_VIEW)));

flyout.addMenuFlyOut("payroll.jsp", dictionaryD.getWord(I_Dictionary.PAYROLL), dictionaryD.getWord(I_Dictionary.SALARY_LEVEL), false, null, "salary_level", ""+approot+"/payroll/setup/salary-level.jsp", "", userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_PAYROLL, AppObjInfo.G2_MENU_PAYROLL_SETUP, AppObjInfo.OBJ_MENU_SALARY_LEVEL), 
             AppObjInfo.COMMAND_VIEW))); 

flyout.addMenuFlyOut("payroll.jsp", dictionaryD.getWord(I_Dictionary.PAYROLL), dictionaryD.getWord(I_Dictionary.EMPLOYEE_SETUP), false, null, "employee_setup", ""+approot+"/payroll/setup/employee-setup.jsp", "", userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_PAYROLL, AppObjInfo.G2_MENU_PAYROLL_SETUP, AppObjInfo.OBJ_MENU_EMPLOYEE_SETUP), 
             AppObjInfo.COMMAND_VIEW)));

flyout.addMenuFlyOut("payroll.jsp", dictionaryD.getWord(I_Dictionary.PAYROLL), dictionaryD.getWord(I_Dictionary.CURRENCY), false, null, "currency", ""+approot+"/payroll/setup/currency.jsp", "",userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_PAYROLL, AppObjInfo.G2_MENU_PAYROLL_SETUP, AppObjInfo.OBJ_MENU_CURRENCY), 
             AppObjInfo.COMMAND_VIEW)));
			 			 
flyout.addMenuFlyOut("payroll.jsp", dictionaryD.getWord(I_Dictionary.PAYROLL), dictionaryD.getWord(I_Dictionary.CURRENCY_RATE), false, null, "currency", ""+approot+"/payroll/setup/currency_rate.jsp", "",userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_PAYROLL, AppObjInfo.G2_MENU_PAYROLL_SETUP, AppObjInfo.OBJ_MENU_CURRENCY_RATE), 
             AppObjInfo.COMMAND_VIEW)));
			 
flyout.addMenuFlyOut("payroll.jsp", dictionaryD.getWord(I_Dictionary.PAYROLL), dictionaryD.getWord(I_Dictionary.CHART_OF_ACCOUNT), false, null, "chartofaccount", ""+approot+"/masterdata/account_chart.jsp", "",userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_PAYROLL, AppObjInfo.G2_MENU_PAYROLL_SETUP, AppObjInfo.OBJ_MENU_CURRENCY_RATE), 
             AppObjInfo.COMMAND_VIEW)));
flyout.addMenuFlyOut("payroll.jsp", dictionaryD.getWord(I_Dictionary.PAYROLL), dictionaryD.getWord(I_Dictionary.PREPARE_DATA), false, null, "prepare_data",""+approot+"/payroll/process/pay-pre-data.jsp", "",userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_PAYROLL, AppObjInfo.G2_MENU_PAYROLL_PROCESS, AppObjInfo.OBJ_MENU_PAYROLL_PROCESS_PREPARE_DATA), 
             AppObjInfo.COMMAND_VIEW)));

flyout.addMenuFlyOut("payroll.jsp", dictionaryD.getWord(I_Dictionary.PAYROLL), dictionaryD.getWord(I_Dictionary.PAYROLL_INPUT), false, null, "payroll_input","" + approot + "/payroll/process/pay-input.jsp", "",userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_PAYROLL, AppObjInfo.G2_MENU_PAYROLL_PROCESS, AppObjInfo.OBJ_MENU_PAYROLL_PROCESS_INPUT), 
             AppObjInfo.COMMAND_VIEW)));

flyout.addMenuFlyOut("payroll.jsp", dictionaryD.getWord(I_Dictionary.PAYROLL), dictionaryD.getWord(I_Dictionary.EMPLOYEE_DEDUCTION), true, "Entry "+dictionaryD.getWord(I_Dictionary.EMPLOYEE_DEDUCTION), "master_data", "#", ""+approot+"/employee/arap/arap_main_list.jsp", userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_PAYROLL, AppObjInfo.G2_MENU_PAYROLL_SETUP, AppObjInfo.OBJ_MENU_GENERAL), 
             AppObjInfo.COMMAND_VIEW)));
flyout.addMenuFlyOut("payroll.jsp", dictionaryD.getWord(I_Dictionary.PAYROLL), dictionaryD.getWord(I_Dictionary.EMPLOYEE_DEDUCTION), true, "List "+dictionaryD.getWord(I_Dictionary.EMPLOYEE_DEDUCTION), "master_data", "#", ""+approot+"/employee/arap/list_employee_deduction.jsp", userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_PAYROLL, AppObjInfo.G2_MENU_PAYROLL_SETUP, AppObjInfo.OBJ_MENU_GENERAL), 
             AppObjInfo.COMMAND_VIEW)));
flyout.addMenuFlyOut("payroll.jsp", dictionaryD.getWord(I_Dictionary.PAYROLL), dictionaryD.getWord(I_Dictionary.EMPLOYEE_DEDUCTION), true, "Submit "+dictionaryD.getWord(I_Dictionary.ALL)+" to "+dictionaryD.getWord("PAYMENT")+" "+dictionaryD.getWord(I_Dictionary.EMPLOYEE_DEDUCTION), "master_data", "#", ""+approot+"/employee/arap/list_payment_deduction.jsp", userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_PAYROLL, AppObjInfo.G2_MENU_PAYROLL_SETUP, AppObjInfo.OBJ_MENU_GENERAL), 
             AppObjInfo.COMMAND_VIEW)));
flyout.addMenuFlyOut("payroll.jsp", dictionaryD.getWord(I_Dictionary.PAYROLL), dictionaryD.getWord(I_Dictionary.EMPLOYEE_DEDUCTION), true, "Creditor List", "master_data", "#", ""+approot+"/masterdata/arap_creditor.jsp", userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_PAYROLL, AppObjInfo.G2_MENU_PAYROLL_SETUP, AppObjInfo.OBJ_MENU_GENERAL), 
             AppObjInfo.COMMAND_VIEW)));
/*Menu created by Kartika : 20150225 */
//flyout.addMenuFlyOut("payroll.jsp", dictionaryD.getWord(I_Dictionary.PAYROLL), dictionaryD.getWord(I_Dictionary.EMPLOYEE_AR), true, "AR Entry"          , "payroll_employee_ar","#", ""+approot + "/arap/arap_entry_edit.jsp?get_time_menu=1397466008793&arap_type=0&menu_type=1", userSession.checkPrivilege(AppObjInfo.composeCode(
//             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_PAYROLL, AppObjInfo.G2_MENU_PAYROLL_PROCESS, AppObjInfo.OBJ_MENU_PAYROLL_PROCESS_INPUT), 
//             AppObjInfo.COMMAND_VIEW)));

/*End Menu byh Kartika*/

flyout.addMenuFlyOut("payroll.jsp", dictionaryD.getWord(I_Dictionary.PAYROLL), dictionaryD.getWord(I_Dictionary.PAYROLL_PROCESS), false, null, "payroll_process","" + approot + "/payroll/process/pay-process.jsp", "",userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_PAYROLL, AppObjInfo.G2_MENU_PAYROLL_PROCESS, AppObjInfo.OBJ_MENU_PAYROLL_PROCESS_PROCESS), 
             AppObjInfo.COMMAND_VIEW)));
 

flyout.addMenuFlyOut("payroll.jsp", dictionaryD.getWord(I_Dictionary.PAYROLL), dictionaryD.getWord(I_Dictionary.PAYSLIP_PRINTING), false, null, "payroll_process", "" + approot + "/payroll/process/pay-printing.jsp", "",userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_PAYROLL, AppObjInfo.G2_MENU_PAYROLL_PROCESS, AppObjInfo.OBJ_MENU_PAYROLL_PROCESS_PRINTING), 
             AppObjInfo.COMMAND_VIEW)));
flyout.addMenuFlyOut("payroll.jsp", dictionaryD.getWord(I_Dictionary.PAYROLL), "Simulasi Perubahan Gaji", false, null, "payroll_process", "" + approot + "/payroll/process/pay-printing.jsp", "",userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_PAYROLL, AppObjInfo.G2_MENU_PAYROLL_PROCESS, AppObjInfo.OBJ_MENU_PAYROLL_PROCESS_PRINTING), 
             AppObjInfo.COMMAND_VIEW)));
/*
4) Kandidat
  Pencarian Kandidat
  Rotasi Karyawan
  Analisa Kandidat
  Persyaratan Jabatan
*/
flyout.addMenuFlyOut("candidate.jsp", dictionaryD.getWord(I_Dictionary.CANDIDATE), dictionaryD.getWord(I_Dictionary.CANDIDATE_SEARCH), false, null, "candidate", ""+approot+"/employee/candidate/candidate_main.jsp?type_menu=0", "", userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_CANDIDATE, AppObjInfo.OBJ_CANDIDATE_SEARCH), 
             AppObjInfo.COMMAND_VIEW)));
flyout.addMenuFlyOut("candidate.jsp", dictionaryD.getWord(I_Dictionary.CANDIDATE), dictionaryD.getWord(I_Dictionary.EMPLOYEE_ROTATION), false, null, "data_bank", ""+approot+"/employee/databank/srcemployee.jsp", "", userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_CANDIDATE, AppObjInfo.OBJ_CANDIDATE_ROTATION), 
             AppObjInfo.COMMAND_VIEW)));
flyout.addMenuFlyOut("candidate.jsp", dictionaryD.getWord(I_Dictionary.CANDIDATE), dictionaryD.getWord(I_Dictionary.CANDIDATE_ANALYSIS), false, null, "data_bank", ""+approot+"/employee/databank/srcemployee.jsp", "", userSession.checkPrivilege(AppObjInfo.composeCode(
//flyout.addMenuFlyOut("candidate.jsp", dictionaryD.getWord(I_Dictionary.CANDIDATE), "Pencarian Talent Pool", false, null, "candidate", ""+approot+"/employee/candidate/candidate_main.jsp?type_menu=1", "", userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_CANDIDATE, AppObjInfo.OBJ_CANDIDATE_ANALYSIS), 
             AppObjInfo.COMMAND_VIEW)));
flyout.addMenuFlyOut("candidate.jsp", dictionaryD.getWord(I_Dictionary.CANDIDATE), "Daftar Pencarian", false, null, "candidate", ""+approot+"/employee/candidate/candidate_list.jsp", "", userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_CANDIDATE, AppObjInfo.OBJ_CANDIDATE_LIST), 
             AppObjInfo.COMMAND_VIEW)));
flyout.addMenuFlyOut("candidate.jsp", dictionaryD.getWord(I_Dictionary.CANDIDATE), dictionaryD.getWord(I_Dictionary.POSITION_REQUIREMENTS), false, null, "data_bank", ""+approot+"/masterdata/srcposition.jsp", "", userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_CANDIDATE, AppObjInfo.OBJ_CANDIDATE_POSITION_REQ), 
             AppObjInfo.COMMAND_VIEW)));
/* 
5) Kompetensi & talent pool
*/

flyout.addMenuFlyOut("competence.jsp", "Kompetensi & Talent Pool", "Tipe Kompetensi", false, null, "competency_type", ""+approot+"/masterdata/competency_type.jsp", "",	userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_COMPETENCE, AppObjInfo.OBJ_COMPETENCE_TYPE), 
             AppObjInfo.COMMAND_VIEW)));
			 
flyout.addMenuFlyOut("competence.jsp", "Kompetensi & Talent Pool", dictionaryD.getWord(I_Dictionary.COMPETENCY_GROUP), false, null, "competency_group", ""+approot+"/masterdata/competency_group.jsp", "",	userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_COMPETENCE, AppObjInfo.OBJ_COMPETENCE_GROUP), 
             AppObjInfo.COMMAND_VIEW)));

flyout.addMenuFlyOut("competence.jsp", "Kompetensi & Talent Pool", dictionaryD.getWord(I_Dictionary.COMPETENCY), false, null, "competency", ""+approot+"/masterdata/competency.jsp", "",	userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_COMPETENCE, AppObjInfo.OBJ_COMPETENCE), 
             AppObjInfo.COMMAND_VIEW)));

flyout.addMenuFlyOut("competence.jsp", "Kompetensi & Talent Pool", dictionaryD.getWord(I_Dictionary.COMPETENCY_DETAIL), false, null, "competency_detail", ""+approot+"/masterdata/competency_detail.jsp", "",	userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_COMPETENCE, AppObjInfo.OBJ_COMPETENCE_DETAIL), 
             AppObjInfo.COMMAND_VIEW)));

flyout.addMenuFlyOut("competence.jsp", "Kompetensi & Talent Pool", dictionaryD.getWord(I_Dictionary.COMPETENCY_LEVEL), false, null, "competency_level", ""+approot+"/masterdata/competency_level.jsp", "",	userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_COMPETENCE, AppObjInfo.OBJ_COMPETENCE_LEVEL), 
             AppObjInfo.COMMAND_VIEW)));



			 
/*
6) Performa
*/

flyout.addMenuFlyOut("performance.jsp", "Performance Management", "Target & Distribusi", false, null, "absence_management", ""+approot+"/masterdata/kpi_company_target.jsp", "",userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_PERFORMANCE, AppObjInfo.OBJ_KPI_COMPANY_TARGET), 
             AppObjInfo.COMMAND_VIEW))); 
			 
flyout.addMenuFlyOut("performance.jsp", "Performance Management", "Target Karyawan", false, null, "absence_management", ""+approot+"/masterdata/kpi_company_target.jsp", "",userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_PERFORMANCE, AppObjInfo.OBJ_KPI_COMPANY_TARGET), 
             AppObjInfo.COMMAND_VIEW))); 
			 
			 
flyout.addMenuFlyOut("performance.jsp", "Performance Management", "Input Realisasi", false, null, "absence_management", ""+approot+"/masterdata/kpi_company_target.jsp", "",userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_PERFORMANCE, AppObjInfo.OBJ_KPI_COMPANY_TARGET), 
             AppObjInfo.COMMAND_VIEW))); 
flyout.addMenuFlyOut("performance.jsp", "Performance Management", "Akumulasi Performa", false, null, "absence_management", ""+approot+"/masterdata/kpi_company_target.jsp", "",userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_PERFORMANCE, AppObjInfo.OBJ_KPI_COMPANY_TARGET), 
             AppObjInfo.COMMAND_VIEW))); 
flyout.addMenuFlyOut("performance.jsp", "Performance Management", "Master Data", true, "Tipe KPI", "company", "#", ""+approot+"/masterdata/kpi_type.jsp",	userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_PERFORMANCE, AppObjInfo.OBJ_KPI_TYPE), 
             AppObjInfo.COMMAND_VIEW)));
			 
flyout.addMenuFlyOut("performance.jsp", "Performance Management", "Master Data", true, "Grup KPI", "company", "#", ""+approot+"/masterdata/kpi_group.jsp",	userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_PERFORMANCE, AppObjInfo.OBJ_KPI_GROUP), 
             AppObjInfo.COMMAND_VIEW)));
flyout.addMenuFlyOut("performance.jsp", "Performance Management", "Master Data", true, "Pesaing", "company", "#", ""+approot+"/masterdata/competitor.jsp",	userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_PERFORMANCE, AppObjInfo.OBJ_COMPETITOR), 
             AppObjInfo.COMMAND_VIEW)));			 
flyout.addMenuFlyOut("performance.jsp", "Performance Management", "Master Data", true, "Daftar KPI", "company", "#", ""+approot+"/masterdata/kpi_list.jsp",	userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_PERFORMANCE, AppObjInfo.OBJ_KPI_LIST), 
             AppObjInfo.COMMAND_VIEW)));	
flyout.addMenuFlyOut("performance.jsp", "Performance Management", "Master Data", true, "KPI Achieve Score", "company", "#", ""+approot+"/masterdata/kpi_achiev_score.jsp", userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_PERFORMANCE, AppObjInfo.OBJ_KPI_ACHIEV_SCORE), 
             AppObjInfo.COMMAND_VIEW)));
			 
/*
7) Kinerja
*/			 
			 /*
flyout.addMenuFlyOut("kinerja.jsp", "Kinerja", "Manajemen Kinerja", true, dictionaryD.getWord("EXPLANATIONS")+" & "+dictionaryD.getWord("COVERAGE"), "assessment", "#", ""+approot+"/employee/appraisal/expcoverage.jsp", userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_EMPLOYEE, AppObjInfo.G2_MENU_ASSESSMENT, AppObjInfo.OBJ_MENU_EXPLANATION_COVERAGE), 
             AppObjInfo.COMMAND_VIEW)));
			 */
			 
flyout.addMenuFlyOut("kinerja.jsp", "Kinerja", "Manajemen Kinerja", true, "Penilaian Karyawan", "assessment", "#", ""+approot+"/employee/appraisalnew/srcappraisal.jsp", userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_EMPLOYEE, AppObjInfo.G2_MENU_ASSESSMENT, AppObjInfo.OBJ_MENU_PERFORMANCE_ASSESSMENT), 
             AppObjInfo.COMMAND_VIEW)));

flyout.addMenuFlyOut("kinerja.jsp", "Kinerja", "Manajemen Kinerja", true, "Laporan Kinerja", "assessment", "#", ""+approot+"/employee/kpi/kpi_achievement.jsp", userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_EMPLOYEE, AppObjInfo.G2_MENU_COMPANY_STRUCTURE, AppObjInfo.OBJ_MENU_COMPANY_STRUCTURE), 
             AppObjInfo.COMMAND_VIEW)));	


flyout.addMenuFlyOut("kinerja.jsp", "Kinerja", "Form Penilaian", true, "Tingkat Karyawan", "assessment", "#", ""+approot+"/masterdata/grouprankHR.jsp",	userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_MASTERDATA, AppObjInfo.G2_MENU_MD_ASSESSMENT, AppObjInfo.OBJ_MENU_MD_ASSESSMENT_GROUP_RANK), 
             AppObjInfo.COMMAND_VIEW)));
			 
flyout.addMenuFlyOut("kinerja.jsp", "Kinerja", "Form Penilaian", true, "Kriteria Penilaian", "assessment", "#", ""+approot+"/masterdata/evaluation.jsp",	userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_MASTERDATA, AppObjInfo.G2_MENU_MD_ASSESSMENT, AppObjInfo.OBJ_MENU_MD_ASSESSMENT_EVALUATION_CRITERIA), 
             AppObjInfo.COMMAND_VIEW)));
			 
flyout.addMenuFlyOut("kinerja.jsp", "Kinerja", "Form Penilaian", true, "Form Creator", "assessment", "#", ""+approot+"/masterdata/assessment/assessmentFormMain.jsp",	userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_MASTERDATA, AppObjInfo.G2_MENU_MD_ASSESSMENT, AppObjInfo.OBJ_MENU_MD_ASSESSMENT_FORM_CREATOR), 
             AppObjInfo.COMMAND_VIEW)));


/*
	Surat
*/			 

flyout.addMenuFlyOut("surat.jsp", "Surat Perusahaan", "Master Data", true, "Tipe Surat", "company", "#", ""+approot+"/masterdata/doc_type.jsp",	userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_MASTERDATA, AppObjInfo.G2_MENU_MD_COMPANY, AppObjInfo.OBJ_MENU_COMMPANY), 
             AppObjInfo.COMMAND_VIEW)));
		 
flyout.addMenuFlyOut("surat.jsp", "Surat Perusahaan", "Master Data", true, "Tipe Biaya", "company", "#", ""+approot+"/masterdata/doc_expenses.jsp",	userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_MASTERDATA, AppObjInfo.G2_MENU_MD_COMPANY, AppObjInfo.OBJ_MENU_DIVISION), 
             AppObjInfo.COMMAND_VIEW)));
			 
flyout.addMenuFlyOut("surat.jsp", "Surat Perusahaan", "Master Data", true, "Template Surat", "company", "#", ""+approot+"/masterdata/doc_master.jsp",	userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_MASTERDATA, AppObjInfo.G2_MENU_MD_COMPANY, AppObjInfo.OBJ_MENU_DIVISION), 
             AppObjInfo.COMMAND_VIEW)));

flyout.addMenuFlyOut("surat.jsp", "Surat Perusahaan", "Daftar Surat", false, null, "Surat",  ""+approot+"/employee/document/EmpDocument.jsp", "", userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_DOKUMEN_SURAT, AppObjInfo.G2_MASTER_DOCUMENT, AppObjInfo.OBJ_DOCUMENT_TYPE), 
             AppObjInfo.COMMAND_VIEW)));


/*
10) Pelatihan (Training)
*/

flyout.addMenuFlyOut("training.jsp", dictionaryD.getWord(I_Dictionary.TRAINING), dictionaryD.getWord(I_Dictionary.TRAINING_TYPE), false, null, "training_type", ""+approot+"/masterdata/list_train_type.jsp", "", userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_PELATIHAN, AppObjInfo.G2_MENU_TIPE_PELATIHAN, AppObjInfo.OBJ_MENU_TIPE_PELATIHAN), 
             AppObjInfo.COMMAND_VIEW)));
			 
flyout.addMenuFlyOut("training.jsp", dictionaryD.getWord(I_Dictionary.TRAINING), dictionaryD.getWord(I_Dictionary.TRAINING_VENUE), false, null, "training_venue", ""+approot+"/masterdata/list_train_venue.jsp", "", userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_PELATIHAN, AppObjInfo.G2_MENU_LOKASI_PELATIHAN, AppObjInfo.OBJ_MENU_LOKASI_PELATIHAN), 
             AppObjInfo.COMMAND_VIEW)));
/*		 			 
flyout.addMenuFlyOut("training.jsp", dictionaryD.getWord(I_Dictionary.TRAINING), dictionaryD.getWord(I_Dictionary.TRAINING_PROGRAM), false, null, "training_program", ""+approot+"/masterdata/srctraining.jsp", "", userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_PELATIHAN, AppObjInfo.G2_MENU_PROGRAM_PELATIHAN, AppObjInfo.OBJ_MENU_PROGRAM_PELATIHAN), 
             AppObjInfo.COMMAND_VIEW))); */

flyout.addMenuFlyOut("training.jsp", dictionaryD.getWord(I_Dictionary.TRAINING), dictionaryD.getWord(I_Dictionary.TRAINING_PROGRAM), false, null, "training_program", ""+approot+"/masterdata/training-program.jsp", "", userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_PELATIHAN, AppObjInfo.G2_MENU_PROGRAM_PELATIHAN, AppObjInfo.OBJ_MENU_PROGRAM_PELATIHAN), 
             AppObjInfo.COMMAND_VIEW)));
			 	 
flyout.addMenuFlyOut("training.jsp", dictionaryD.getWord(I_Dictionary.TRAINING), dictionaryD.getWord(I_Dictionary.TRAINING_PLAN), false, null, "training_plan", ""+approot+"/employee/training/training_plan_list.jsp", "", userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_PELATIHAN, AppObjInfo.G2_MENU_RENCANA_PELATIHAN, AppObjInfo.OBJ_MENU_RENCANA_PELATIHAN), 
             AppObjInfo.COMMAND_VIEW)));
			 
flyout.addMenuFlyOut("training.jsp", dictionaryD.getWord(I_Dictionary.TRAINING), dictionaryD.getWord(I_Dictionary.TRAINING_ACTUAL), false, null, "training_actual", ""+approot+"/employee/training/training_actual_list.jsp", "", userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_PELATIHAN, AppObjInfo.G2_MENU_PELATIHAN_REAL, AppObjInfo.OBJ_MENU_PELATIHAN_REAL), 
             AppObjInfo.COMMAND_VIEW)));
			 
flyout.addMenuFlyOut("training.jsp", dictionaryD.getWord(I_Dictionary.TRAINING), dictionaryD.getWord(I_Dictionary.TRAINING_SEARCH), false, null, "training_search", ""+approot+"/employee/training/search_training.jsp", "", userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_PELATIHAN, AppObjInfo.G2_MENU_PENCARIAN_PELATIHAN, AppObjInfo.OBJ_MENU_PENCARIAN_PELATIHAN), 
             AppObjInfo.COMMAND_VIEW)));
			 
flyout.addMenuFlyOut("training.jsp", dictionaryD.getWord(I_Dictionary.TRAINING), dictionaryD.getWord(I_Dictionary.TRAINING_HISTORY), false, null, "training_history", ""+approot+"/employee/training/src_training_hist_hr.jsp", "", userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_PELATIHAN, AppObjInfo.G2_MENU_SEJARAH_PELATIHAN, AppObjInfo.OBJ_MENU_SEJARAH_PELATIHAN), 
             AppObjInfo.COMMAND_VIEW)));
			 
flyout.addMenuFlyOut("training.jsp", dictionaryD.getWord(I_Dictionary.TRAINING), "Gap Training", false, null, "training_history", ""+approot+"/employee/training/src_training_hist_hr.jsp", "", userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_PELATIHAN, AppObjInfo.G2_MENU_GAP_TRAINING, AppObjInfo.OBJ_MENU_GAP_TRAINING), 
             AppObjInfo.COMMAND_VIEW)));

flyout.addMenuFlyOut("training.jsp", dictionaryD.getWord(I_Dictionary.TRAINING), "Training Organizer", false ,null,  "training_organizer", ""+approot+"/masterdata/contact/srccontact_list.jsp"  , "", userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_PELATIHAN, AppObjInfo.G2_MENU_TRAINING_ORGANIZER , AppObjInfo.OBJ_MENU_TRAINING_ORGANIZER ), 
             AppObjInfo.COMMAND_VIEW)));

flyout.addMenuFlyOut("training.jsp", dictionaryD.getWord(I_Dictionary.TRAINING), "Organizer Type"    , false, null,  "training_organizer", ""+approot+"/masterdata/contact/contact_class.jsp"    , "", userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_PELATIHAN, AppObjInfo.G2_MENU_ORGANIZER_TYPE , AppObjInfo.OBJ_MENU_ORGANIZER_TYPE ), 
             AppObjInfo.COMMAND_VIEW)));
flyout.addMenuFlyOut("training.jsp", dictionaryD.getWord(I_Dictionary.TRAINING), "Training Score"    , false, null,  "training_score", ""+approot+"/masterdata/training_score.jsp", "", userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_PELATIHAN, AppObjInfo.G2_MENU_TRAINING_SCORE , AppObjInfo.OBJ_MENU_TRAINING_SCORE ), 
             AppObjInfo.COMMAND_VIEW)));


/* 11) Absensi */
flyout.addMenuFlyOut("absensi.jsp", "Absensi", dictionaryD.getWord(I_Dictionary.ATTENDANCE), true, dictionaryD.getWord(I_Dictionary.WORKING_SCHEDULE), "attendance", "#", ""+approot+"/employee/attendance/srcempschedule.jsp", userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_EMPLOYEE, AppObjInfo.G2_MENU_ATTENDANCE, AppObjInfo.OBJ_MENU_WORKING_SCHEDULE), 
             AppObjInfo.COMMAND_VIEW)));
			 
flyout.addMenuFlyOut("absensi.jsp", "Absensi", dictionaryD.getWord(I_Dictionary.ATTENDANCE), true, "Daftar Data IN/OUT", "attendance", "#", ""+approot+"/employee/presence/srcpresence.jsp", userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_ATTENDANCE, AppObjInfo.OBJ_MANUAL_PRESENCE), 
             AppObjInfo.COMMAND_VIEW)));
			 
flyout.addMenuFlyOut("absensi.jsp", "Absensi", dictionaryD.getWord(I_Dictionary.ATTENDANCE), true, "Set Libur Nasional di Jadwal", "attendance", "#", ""+approot+"/report/presence/Update_schedule_If_holidays.jsp", userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_EMPLOYEE, AppObjInfo.G2_MENU_ATTENDANCE, AppObjInfo.OBJ_MENU_REGENERATE_SCHEDULE_HOLIDAYS), 
             AppObjInfo.COMMAND_VIEW)));


flyout.addMenuFlyOut("absensi.jsp", "Absensi", dictionaryD.getWord(I_Dictionary.SERVICE_MANAGER), true, dictionaryD.getWord(I_Dictionary.SERVICE_MANAGER), "time_keeping", "#", ""+approot+"/system/timekeepingpro/svcmgr.jsp", userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_SYSTEM, AppObjInfo.G2_TIMEKEEPING, AppObjInfo.OBJ_SERVICE_MANAGER), 
             AppObjInfo.COMMAND_VIEW)));

flyout.addMenuFlyOut("absensi.jsp", "Absensi", dictionaryD.getWord(I_Dictionary.SERVICE_MANAGER), true, dictionaryD.getWord(I_Dictionary.MANUAL_SERVICE), "time_keeping", "#", ""+approot+"/service/attendance_manual_calculation.jsp", userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_SYSTEM, AppObjInfo.G2_MANUAL_CHECKING, AppObjInfo.OBJ_MANUAL_CHECKING_ALL), 
             AppObjInfo.COMMAND_VIEW)));

flyout.addMenuFlyOut("absensi.jsp", "Absensi", "Master Data", true, dictionaryD.getWord(I_Dictionary.PERIODE), "Schedule", "#", ""+approot+"/masterdata/period.jsp",	userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MASTERDATA, AppObjInfo.G2_MD_SCHEDULE, AppObjInfo.OBJ_SCHEDULE_PERIOD), 
             AppObjInfo.COMMAND_VIEW)));
			 
flyout.addMenuFlyOut("absensi.jsp", "Absensi", "Master Data", true, dictionaryD.getWord(I_Dictionary.CATEGORY), "Schedule", "#", ""+approot+"/masterdata/schedulecategory.jsp",	userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MASTERDATA, AppObjInfo.G2_MD_SCHEDULE, AppObjInfo.OBJ_SCHEDULE_CATEGORY), 
             AppObjInfo.COMMAND_VIEW)));
			 
flyout.addMenuFlyOut("absensi.jsp", "Absensi", "Master Data", true, dictionaryD.getWord(I_Dictionary.SYMBOL), "Schedule", "#", ""+approot+"/masterdata/srcschedulesymbol.jsp",	userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MASTERDATA, AppObjInfo.G2_MD_SCHEDULE, AppObjInfo.OBJ_SCHEDULE_SYMBOL), 
             AppObjInfo.COMMAND_VIEW)));
			 
flyout.addMenuFlyOut("absensi.jsp", "Absensi", "Master Data", true, dictionaryD.getWord(I_Dictionary.PUBLIC_HOLIDAY), "company", "#", ""+approot+"/masterdata/publicHoliday.jsp",	userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MASTERDATA, AppObjInfo.G2_MD_COMPANY, AppObjInfo.OBJ_PUBLIC_HOLIDAY), 
             AppObjInfo.COMMAND_VIEW)));

flyout.addMenuFlyOut("absensi.jsp", "Absensi", "Master Data", true, "Abseance Reason", "Master Data", "#", ""+approot+"/masterdata/reason.jsp",	userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_MASTERDATA, AppObjInfo.G2_MENU_MD_EMPLOYEE, AppObjInfo.OBJ_MENU_EMPLOYEE_ABSENCE_REASON), 
             AppObjInfo.COMMAND_VIEW)));			 

/*
12) Lembur (Overtime)
*/
flyout.addMenuFlyOut("overtime.jsp", dictionaryD.getWord(I_Dictionary.OVERTIME), dictionaryD.getWord(I_Dictionary.OVERTIME_FORM), false, null, "overtime_form", ""+approot+"/payroll/overtimeform/src_overtime.jsp", "",userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_PAYROLL, AppObjInfo.G2_PAYROLL_OVERTIME, AppObjInfo.OBJ_PAYROLL_OVERTIME_FORM), 
             AppObjInfo.COMMAND_VIEW))); 
			 	 
flyout.addMenuFlyOut("overtime.jsp", dictionaryD.getWord(I_Dictionary.OVERTIME), dictionaryD.getWord(I_Dictionary.OVERTIME_REPORT_PROCESS), false, null, "overtime_report", ""+approot+"/payroll/overtimeform/src_overtime_report.jsp", "",userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_PAYROLL, AppObjInfo.G2_PAYROLL_PROCESS, AppObjInfo.OBJ_PAYROLL_PROCESS_PROCESS), 
             AppObjInfo.COMMAND_VIEW))); 


flyout.addMenuFlyOut("overtime.jsp", dictionaryD.getWord(I_Dictionary.OVERTIME), dictionaryD.getWord(I_Dictionary.OVERTIME_INDEX), false, null, "overtime_index", ""+approot+"/payroll/overtime/ov-index.jsp", "",userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_PAYROLL, AppObjInfo.G2_PAYROLL_OVERTIME, AppObjInfo.OBJ_PAYROLL_OVERTIME_INDEX), 
             AppObjInfo.COMMAND_VIEW))); 
			 
flyout.addMenuFlyOut("overtime.jsp", dictionaryD.getWord(I_Dictionary.OVERTIME), dictionaryD.getWord(I_Dictionary.OVERTIME_SUMMARY), false, null, "overtime_summary", ""+approot+"/payroll/overtimeform/src_overtime_summary.jsp", "",userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_PAYROLL, AppObjInfo.G2_PAYROLL_OVERTIME, AppObjInfo.OBJ_PAYROLL_OVERTIME_SUMMARY), 
             AppObjInfo.COMMAND_VIEW)));
			 

/* 13) Cuti (Leave) */
/*if(namaUser1.equals("Employee")){
    flyout.addMenuFlyOut("leave.jsp", dictionaryD.getWord(I_Dictionary.LEAVE), dictionaryD.getWord(I_Dictionary.LEAVE_APPLICATION), true, dictionaryD.getWord(I_Dictionary.LEAVE_APPLICATION), "leave_application", "#", ""+approot+"/employee/leave/leave_app_edit_emp.jsp", userSession.checkPrivilege(AppObjInfo.composeCode(
                 AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_EMPLOYEE, AppObjInfo.G2_MENU_LEAVE_APPLICATION, AppObjInfo.OBJ_MENU_LEAVE_FORM), 
                 AppObjInfo.COMMAND_VIEW)));***/
/*} else {*/	 
flyout.addMenuFlyOut("leave.jsp", dictionaryD.getWord(I_Dictionary.LEAVE), dictionaryD.getWord(I_Dictionary.LEAVE_APPLICATION), true, "Form Cuti", "leave_application", "#", "" + approot + "/employee/leave/leave_app_src.jsp", userSession.checkPrivilege(AppObjInfo.composeCode(
        AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_EMPLOYEE, AppObjInfo.G2_MENU_LEAVE_APPLICATION, AppObjInfo.OBJ_MENU_LEAVE_FORM),
        AppObjInfo.COMMAND_VIEW)));

flyout.addMenuFlyOut("leave.jsp", dictionaryD.getWord(I_Dictionary.LEAVE), dictionaryD.getWord(I_Dictionary.LEAVE_APPLICATION), true, dictionaryD.getWord(I_Dictionary.LEAVE_APPLICATION), "leave_application", "#", ""+approot+"/employee/leave/leave_app_edit_emp.jsp?command=3", userSession.checkPrivilege(AppObjInfo.composeCode(
        AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_EMPLOYEE, AppObjInfo.G2_MENU_LEAVE_APPLICATION, AppObjInfo.OBJ_MENU_LEAVE_FORM), 
        AppObjInfo.COMMAND_VIEW)));	


flyout.addMenuFlyOut("leave.jsp", dictionaryD.getWord(I_Dictionary.LEAVE), dictionaryD.getWord(I_Dictionary.LEAVE_BALANCING), true, "Annual Leave", "leave_balancing", "#", "" + approot + "/system/leave/AL_Balancing.jsp", userSession.checkPrivilege(AppObjInfo.composeCode(
        AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_EMPLOYEE, AppObjInfo.G2_MENU_LEAVE_BALANCING, AppObjInfo.OBJ_MENU_ANNUAL_LEAVE),
        AppObjInfo.COMMAND_VIEW)));

flyout.addMenuFlyOut("leave.jsp", dictionaryD.getWord(I_Dictionary.LEAVE), dictionaryD.getWord(I_Dictionary.LEAVE_BALANCING), true, "Long Leave", "leave_balancing", "#", "" + approot + "/system/leave/LL_Balancing.jsp", userSession.checkPrivilege(AppObjInfo.composeCode(
        AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_EMPLOYEE, AppObjInfo.G2_MENU_LEAVE_BALANCING, AppObjInfo.OBJ_MENU_LONG_LEAVE),
        AppObjInfo.COMMAND_VIEW)));

flyout.addMenuFlyOut("leave.jsp", dictionaryD.getWord(I_Dictionary.LEAVE), dictionaryD.getWord(I_Dictionary.LEAVE_BALANCING), true, "Day off Payment", "leave_balancing", "#", "" + approot + "/system/leave/DP_Balancing.jsp", userSession.checkPrivilege(AppObjInfo.composeCode(
        AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_EMPLOYEE, AppObjInfo.G2_MENU_LEAVE_BALANCING, AppObjInfo.OBJ_MENU_DAY_OFF_PAYMENT),
        AppObjInfo.COMMAND_VIEW)));

flyout.addMenuFlyOut("leave.jsp", dictionaryD.getWord(I_Dictionary.LEAVE), "Cut Off Cuti", true, dictionaryD.getWord(I_Dictionary.LEAVEL_AL_CLOSING), "leave_application", "#", "" + approot + "/employee/leave/leave_al_closing.jsp", userSession.checkPrivilege(AppObjInfo.composeCode(
        AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_EMPLOYEE, AppObjInfo.G2_MENU_LEAVE_APPLICATION, AppObjInfo.OBJ_MENU_LEAVE_AL_CLOSING),
        AppObjInfo.COMMAND_VIEW)));

flyout.addMenuFlyOut("leave.jsp", dictionaryD.getWord(I_Dictionary.LEAVE), "Cut Off Cuti", true, dictionaryD.getWord(I_Dictionary.LEAVE_LL_CLOSING), "leave_application", "#", "" + approot + "/employee/leave/leave_ll_closing.jsp", userSession.checkPrivilege(AppObjInfo.composeCode(
        AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_EMPLOYEE, AppObjInfo.G2_MENU_LEAVE_APPLICATION, AppObjInfo.OBJ_MENU_LEAVE_LI_CLOSING),
        AppObjInfo.COMMAND_VIEW)));

flyout.addMenuFlyOut("leave.jsp", dictionaryD.getWord(I_Dictionary.LEAVE), dictionaryD.getWord(I_Dictionary.DP_MANAGEMENT), true, dictionaryD.getWord(I_Dictionary.DP_MANAGEMENT), "leave_application", "#", "" + approot + "/employee/attendance/dp.jsp", userSession.checkPrivilege(AppObjInfo.composeCode(
        AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_EMPLOYEE, AppObjInfo.G2_MENU_LEAVE_APPLICATION, AppObjInfo.OBJ_MENU_DP_MANAGEMENT),
        AppObjInfo.COMMAND_VIEW)));

flyout.addMenuFlyOut("leave.jsp", dictionaryD.getWord(I_Dictionary.LEAVE), dictionaryD.getWord(I_Dictionary.DP_MANAGEMENT), true, dictionaryD.getWord(I_Dictionary.DP_RECALCULATE), "leave_application", "#", "" + approot + "/employee/leave/if_dp_not_balance.jsp", userSession.checkPrivilege(AppObjInfo.composeCode(
        AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_EMPLOYEE, AppObjInfo.G2_MENU_LEAVE_APPLICATION, AppObjInfo.OBJ_MENU_DP_RECALCULATE),
        AppObjInfo.COMMAND_VIEW)));

flyout.addMenuFlyOut("leave.jsp", dictionaryD.getWord(I_Dictionary.LEAVE), dictionaryD.getWord(I_Dictionary.LEAVE_TARGET), false, null, "company", "" + approot + "/masterdata/leaveTarget.jsp", "", userSession.checkPrivilege(AppObjInfo.composeCode(
        AppObjInfo.composeObjCode(AppObjInfo.G1_MASTERDATA, AppObjInfo.G2_MD_COMPANY, AppObjInfo.OBJ_LEAVE_TARGET),
        AppObjInfo.COMMAND_VIEW)));
/*}*/

flyout.addMenuFlyOut("leave.jsp", dictionaryD.getWord(I_Dictionary.LEAVE),  dictionaryD.getWord("LEAVE") +" "+ dictionaryD.getWord("CONFIGURATION"), false, null, "master_data", ""+approot+"/masterdata/leave_setting.jsp", "",userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MASTERDATA, AppObjInfo.G2_MD_COMPANY, AppObjInfo.OBJ_LEAVE_CONFIGURATION), 
             AppObjInfo.COMMAND_VIEW)));

/* 14) Warning & Reprimand | Peringatan dan Teguran */ 
	
flyout.addMenuFlyOut("teguran.jsp", "Kasus Karyawan", "Peringatan dan Teguran", true, "Peringatan", "peringatan", "#", ""+approot+"/employee/warning/src_warning.jsp",	userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_PERINGATAN_DAN_TEGURAN, AppObjInfo.G2_MENU_PERINGATAN, AppObjInfo.OBJ_MENU_PERINGATAN_RINGAN), 
             AppObjInfo.COMMAND_VIEW)));
flyout.addMenuFlyOut("teguran.jsp", "Kasus Karyawan", "Peringatan dan Teguran", true, "Teguran", "teguran", "#", ""+approot+"/employee/warning/src_reprimand.jsp",	userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_PERINGATAN_DAN_TEGURAN, AppObjInfo.G2_MENU_PERINGATAN, AppObjInfo.OBJ_MENU_PERINGATAN_BERAT), 
             AppObjInfo.COMMAND_VIEW)));

flyout.addMenuFlyOut("teguran.jsp", "Kasus Karyawan", "Peraturan Perusahaan", true, dictionaryD.getWord(I_Dictionary.CHAPTER), "teguran", "#", ""+approot+"/masterdata/warningreprimand_bab.jsp",	userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_PERINGATAN_DAN_TEGURAN, AppObjInfo.G2_MENU_TEGURAN, AppObjInfo.OBJ_MENU_TEGURAN_BAB), 
             AppObjInfo.COMMAND_VIEW)));
flyout.addMenuFlyOut("teguran.jsp", "Kasus Karyawan", "Peraturan Perusahaan", true, dictionaryD.getWord(I_Dictionary.ARTICLE), "teguran", "#", ""+approot+"/masterdata/warningreprimand_pasal.jsp",	userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_PERINGATAN_DAN_TEGURAN, AppObjInfo.G2_MENU_TEGURAN, AppObjInfo.OBJ_MENU_TEGURAN_PASAL), 
             AppObjInfo.COMMAND_VIEW)));
flyout.addMenuFlyOut("teguran.jsp", "Kasus Karyawan", "Peraturan Perusahaan", true, dictionaryD.getWord(I_Dictionary.VERSE), "teguran", "#", ""+approot+"/masterdata/warningreprimand_ayat.jsp",	userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_PERINGATAN_DAN_TEGURAN, AppObjInfo.G2_MENU_TEGURAN, AppObjInfo.OBJ_MENU_TEGURAN_AYAT), 
             AppObjInfo.COMMAND_VIEW)));
/*
15) Laporan (Report)
*/
		 
flyout.addMenuFlyOut("reports.jsp", dictionaryD.getWord(I_Dictionary.REPORT), dictionaryD.getWord(I_Dictionary.PRESENCE), true, dictionaryD.getWord(I_Dictionary.DAILY_REPORT), "Presence", "#", ""+approot+"/report/presence/presence_report_daily.jsp", userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_REPORTS, AppObjInfo.G2_MENU_PRESENCE_REPORT, AppObjInfo.OBJ_MENU_PRESENCE_DAILY_REPORT), 
             AppObjInfo.COMMAND_VIEW)));
			 
flyout.addMenuFlyOut("reports.jsp", dictionaryD.getWord(I_Dictionary.REPORT), dictionaryD.getWord(I_Dictionary.PRESENCE), true, dictionaryD.getWord(I_Dictionary.WEEKLY_REPORT), "Presence", "#", ""+approot+"/report/presence/weekly_presence.jsp", userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_REPORTS, AppObjInfo.G2_MENU_PRESENCE_REPORT, AppObjInfo.OBJ_MENU_PRESENCE_WEEKLY_REPORT), 
             AppObjInfo.COMMAND_VIEW)));
			 
flyout.addMenuFlyOut("reports.jsp", dictionaryD.getWord(I_Dictionary.REPORT), dictionaryD.getWord(I_Dictionary.PRESENCE), true, dictionaryD.getWord(I_Dictionary.MONTHLY_REPORT), "Presence", "#", ""+approot+"/report/presence/monthly_presence.jsp", userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_REPORTS, AppObjInfo.G2_MENU_PRESENCE_REPORT, AppObjInfo.OBJ_MENU_PRESENCE_MONTHLY_REPORT), 
             AppObjInfo.COMMAND_VIEW)));
			 
flyout.addMenuFlyOut("reports.jsp", dictionaryD.getWord(I_Dictionary.REPORT), dictionaryD.getWord(I_Dictionary.PRESENCE), true, dictionaryD.getWord(I_Dictionary.YEAR_REPORT), "Presence", "#", ""+approot+"/report/presence/year_report_presence.jsp", userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_REPORTS, AppObjInfo.G2_MENU_PRESENCE_REPORT, AppObjInfo.OBJ_MENU_YEAR_REPORT), 
             AppObjInfo.COMMAND_VIEW)));
			 
flyout.addMenuFlyOut("reports.jsp", dictionaryD.getWord(I_Dictionary.REPORT), dictionaryD.getWord(I_Dictionary.PRESENCE), true, dictionaryD.getWord(I_Dictionary.ATTENDANCE_SUMMARY), "Presence", "#", ""+approot+"/report/presence/summary_attendance_sheet.jsp", userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_REPORTS, AppObjInfo.G2_MENU_PRESENCE_REPORT, AppObjInfo.OBJ_MENU_ATTENDANCE_SUMMARY), 
             AppObjInfo.COMMAND_VIEW)));

flyout.addMenuFlyOut("reports.jsp", dictionaryD.getWord(I_Dictionary.REPORT), dictionaryD.getWord(I_Dictionary.PRESENCE), true, dictionaryD.getWord(I_Dictionary.REKAPITULASI_ABSENSI), "Presence", "#", ""+approot+"/report/presence/rekapitulasi_absensi.jsp", userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_REPORTS, AppObjInfo.G2_MENU_PRESENCE_REPORT, AppObjInfo.OBJ_MENU_ATTENDANCE_SUMMARY), 
             AppObjInfo.COMMAND_VIEW)));

flyout.addMenuFlyOut("reports.jsp", dictionaryD.getWord(I_Dictionary.REPORT), dictionaryD.getWord(I_Dictionary.PRESENCE), true, "Data Kehadiran per Karyawan", "staff_control", "#", ""+approot+"/employee/presence/att_record_monthly.jsp", userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_REPORTS, AppObjInfo.G2_MENU_STAFF_CONTROL_REPORT, AppObjInfo.OBJ_MENU_ATTENDANCE_RECORD_REPORT), 
             AppObjInfo.COMMAND_VIEW)));
			 
flyout.addMenuFlyOut("reports.jsp", dictionaryD.getWord(I_Dictionary.REPORT), dictionaryD.getWord(I_Dictionary.LATENESS), true, dictionaryD.getWord(I_Dictionary.DAILY_REPORT), "lateness", "#", ""+approot+"/report/lateness/lateness_report.jsp", userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_REPORTS, AppObjInfo.G2_MENU_LATENESS_REPORT, AppObjInfo.OBJ_MENU_LATENESS_DAILY_REPORT), 
             AppObjInfo.COMMAND_VIEW)));
			 
flyout.addMenuFlyOut("reports.jsp", dictionaryD.getWord(I_Dictionary.REPORT), dictionaryD.getWord(I_Dictionary.LATENESS), true, dictionaryD.getWord(I_Dictionary.WEEKLY_REPORT), "lateness", "#", ""+approot+"/report/lateness/lateness_weekly_report.jsp", userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_REPORTS, AppObjInfo.G2_MENU_LATENESS_REPORT, AppObjInfo.OBJ_MENU_LATENESS_WEEKLY_REPORT), 
             AppObjInfo.COMMAND_VIEW)));
			 
flyout.addMenuFlyOut("reports.jsp", dictionaryD.getWord(I_Dictionary.REPORT), dictionaryD.getWord(I_Dictionary.LATENESS), true, dictionaryD.getWord(I_Dictionary.MONTHLY_REPORT), "lateness", "#", ""+approot+"/report/lateness/lateness_monthly_report.jsp", userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_REPORTS, AppObjInfo.G2_MENU_LATENESS_REPORT, AppObjInfo.OBJ_MENU_LATENESS_MONTHLY_REPORT), 
             AppObjInfo.COMMAND_VIEW)));
			 
flyout.addMenuFlyOut("reports.jsp", dictionaryD.getWord(I_Dictionary.REPORT), dictionaryD.getWord(I_Dictionary.LATENESS), true, dictionaryD.getWord(I_Dictionary.YEAR_REPORT), "lateness", "#", ""+approot+"/report/lateness/lateness_yearly_report.jsp", userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_REPORTS, AppObjInfo.G2_MENU_LATENESS_REPORT, AppObjInfo.OBJ_MENU_LATENESS_MONTHLY_REPORT), 
             AppObjInfo.COMMAND_VIEW)));
			 
flyout.addMenuFlyOut("reports.jsp", dictionaryD.getWord(I_Dictionary.REPORT), dictionaryD.getWord(I_Dictionary.SPLIT_SHIFT), true, dictionaryD.getWord(I_Dictionary.DAILY_REPORT), "split_shift", "#", ""+approot+"/report/splitshift/daily_split.jsp", userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_REPORTS, AppObjInfo.G2_MENU_SPLIT_SHIFT_REPORT, AppObjInfo.OBJ_MENU_SPLIT_SHIFT_DAILY_REPORT), 
             AppObjInfo.COMMAND_VIEW)));
			 
flyout.addMenuFlyOut("reports.jsp", dictionaryD.getWord(I_Dictionary.REPORT), dictionaryD.getWord(I_Dictionary.SPLIT_SHIFT), true, dictionaryD.getWord(I_Dictionary.WEEKLY_REPORT), "split_shift", "#", ""+approot+"/report/splitshift/weekly_split.jsp", userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_REPORTS, AppObjInfo.G2_MENU_SPLIT_SHIFT_REPORT, AppObjInfo.OBJ_MENU_SPLIT_SHIFT_WEEKLY_REPORT), 
             AppObjInfo.COMMAND_VIEW)));
			 
flyout.addMenuFlyOut("reports.jsp", dictionaryD.getWord(I_Dictionary.REPORT), dictionaryD.getWord(I_Dictionary.SPLIT_SHIFT), true, dictionaryD.getWord(I_Dictionary.MONTHLY_REPORT), "split_shift", "#", ""+approot+"/report/splitshift/monthly_split.jsp", userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_REPORTS, AppObjInfo.G2_MENU_SPLIT_SHIFT_REPORT, AppObjInfo.OBJ_MENU_SPLIT_SHIFT_MONTHLY_REPORT), 
             AppObjInfo.COMMAND_VIEW)));
			 
flyout.addMenuFlyOut("reports.jsp", dictionaryD.getWord(I_Dictionary.REPORT), dictionaryD.getWord(I_Dictionary.SPLIT_SHIFT), true, dictionaryD.getWord(I_Dictionary.YEAR_REPORT), "split_shift", "#", ""+approot+"/report/splitshift/yearly_split.jsp", userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_REPORTS, AppObjInfo.G2_MENU_SPLIT_SHIFT_REPORT, AppObjInfo.OBJ_MENU_SPLIT_SHIFT_MONTHLY_REPORT), 
             AppObjInfo.COMMAND_VIEW)));

flyout.addMenuFlyOut("reports.jsp", dictionaryD.getWord(I_Dictionary.REPORT), dictionaryD.getWord(I_Dictionary.NIGHT_SHIFT), true, dictionaryD.getWord(I_Dictionary.DAILY_REPORT), "night_shift", "#", ""+approot+"/report/nightshift/daily_night.jsp",userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_REPORTS, AppObjInfo.G2_MENU_NIGHT_SHIFT_REPORT, AppObjInfo.OBJ_MENU_NIGHT_SHIFT_DAILY_REPORT), 
             AppObjInfo.COMMAND_VIEW)));
			 
flyout.addMenuFlyOut("reports.jsp", dictionaryD.getWord(I_Dictionary.REPORT), dictionaryD.getWord(I_Dictionary.NIGHT_SHIFT), true, dictionaryD.getWord(I_Dictionary.WEEKLY_REPORT), "night_shift", "#", ""+approot+"/report/nightshift/weekly_night.jsp",	userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_REPORTS, AppObjInfo.G2_MENU_NIGHT_SHIFT_REPORT, AppObjInfo.OBJ_MENU_NIGHT_SHIFT_WEEKLY_REPORT), 
             AppObjInfo.COMMAND_VIEW)));
			 
flyout.addMenuFlyOut("reports.jsp", dictionaryD.getWord(I_Dictionary.REPORT), dictionaryD.getWord(I_Dictionary.NIGHT_SHIFT), true, dictionaryD.getWord(I_Dictionary.MONTHLY_REPORT), "night_shift", "#", ""+approot+"/report/nightshift/monthly_night.jsp",	userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_REPORTS, AppObjInfo.G2_MENU_NIGHT_SHIFT_REPORT, AppObjInfo.OBJ_MENU_NIGHT_SHIFT_MONTHLY_REPORT), 
             AppObjInfo.COMMAND_VIEW)));

flyout.addMenuFlyOut("reports.jsp", dictionaryD.getWord(I_Dictionary.REPORT), dictionaryD.getWord(I_Dictionary.NIGHT_SHIFT), true, dictionaryD.getWord(I_Dictionary.YEAR_REPORT), "night_shift", "#", ""+approot+"/report/nightshift/yearly_night.jsp",	userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_REPORTS, AppObjInfo.G2_MENU_NIGHT_SHIFT_REPORT, AppObjInfo.OBJ_MENU_NIGHT_SHIFT_MONTHLY_REPORT), 
             AppObjInfo.COMMAND_VIEW)));
			 
flyout.addMenuFlyOut("reports.jsp", dictionaryD.getWord(I_Dictionary.REPORT), dictionaryD.getWord(I_Dictionary.ABSENTEEISM), true, dictionaryD.getWord(I_Dictionary.DAILY_REPORT), "absenteeism", "#", ""+approot+"/report/absenteeism/daily_absence.jsp",	userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_REPORTS, AppObjInfo.G2_MENU_ABSENTEEISM_REPORT, AppObjInfo.OBJ_MENU_ABSENTEEISM_DAILY_REPORT), 
             AppObjInfo.COMMAND_VIEW)));
			 
flyout.addMenuFlyOut("reports.jsp", dictionaryD.getWord(I_Dictionary.REPORT), dictionaryD.getWord(I_Dictionary.ABSENTEEISM), true, dictionaryD.getWord(I_Dictionary.WEEKLY_REPORT), "absenteeism", "#", ""+approot+"/report/absenteeism/weekly_absence.jsp",	userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_REPORTS, AppObjInfo.G2_MENU_ABSENTEEISM_REPORT, AppObjInfo.OBJ_MENU_ABSENTEEISM_WEEKLY_REPORT), 
             AppObjInfo.COMMAND_VIEW)));
			 
flyout.addMenuFlyOut("reports.jsp", dictionaryD.getWord(I_Dictionary.REPORT), dictionaryD.getWord(I_Dictionary.ABSENTEEISM), true, dictionaryD.getWord(I_Dictionary.MONTHLY_REPORT), "absenteeism", "#", ""+approot+"/report/absenteeism/monthly_absence.jsp",	userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_REPORTS, AppObjInfo.G2_MENU_ABSENTEEISM_REPORT, AppObjInfo.OBJ_MENU_ABSENTEEISM_MONTHLY_REPORT), 
             AppObjInfo.COMMAND_VIEW)));
			 
flyout.addMenuFlyOut("reports.jsp", dictionaryD.getWord(I_Dictionary.REPORT), dictionaryD.getWord(I_Dictionary.ABSENTEEISM), true, dictionaryD.getWord(I_Dictionary.YEAR_REPORT), "absenteeism", "#", ""+approot+"/report/absenteeism/yearly_absence.jsp",	userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_REPORTS, AppObjInfo.G2_MENU_ABSENTEEISM_REPORT, AppObjInfo.OBJ_MENU_ABSENTEEISM_MONTHLY_REPORT), 
             AppObjInfo.COMMAND_VIEW)));

flyout.addMenuFlyOut("reports.jsp", dictionaryD.getWord(I_Dictionary.REPORT), "Laporan Cuti Sakit", true, dictionaryD.getWord(I_Dictionary.DAILY_REPORT), "sickness", "#", ""+approot+"/report/sickness/daily_sickness.jsp",	userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_REPORTS, AppObjInfo.G2_MENU_SICKNESS_REPORT, AppObjInfo.OBJ_MENU_SICKNESS_DAILY_REPORT), 
             AppObjInfo.COMMAND_VIEW)));
			 
flyout.addMenuFlyOut("reports.jsp", dictionaryD.getWord(I_Dictionary.REPORT), "Laporan Cuti Sakit", true, dictionaryD.getWord(I_Dictionary.WEEKLY_REPORT), "sickness", "#", ""+approot+"/report/sickness/weekly_sickness.jsp",	userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_REPORTS, AppObjInfo.G2_MENU_SICKNESS_REPORT, AppObjInfo.OBJ_MENU_SICKNESS_WEEKLY_REPORT), 
             AppObjInfo.COMMAND_VIEW)));
			 
flyout.addMenuFlyOut("reports.jsp", dictionaryD.getWord(I_Dictionary.REPORT), "Laporan Cuti Sakit", true, dictionaryD.getWord(I_Dictionary.MONTHLY_REPORT), "sickness", "#", ""+approot+"/report/sickness/monthly_sickness.jsp",	userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_REPORTS, AppObjInfo.G2_MENU_SICKNESS_REPORT, AppObjInfo.OBJ_MENU_SICKNESS_MONTHLY_REPORT), 
             AppObjInfo.COMMAND_VIEW)));
			 
flyout.addMenuFlyOut("reports.jsp", dictionaryD.getWord(I_Dictionary.REPORT), "Laporan Cuti Sakit", true, dictionaryD.getWord(I_Dictionary.YEAR_REPORT), "sickness", "#", ""+approot+"/report/sickness/yearly_sickness.jsp",	userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_REPORTS, AppObjInfo.G2_MENU_SICKNESS_REPORT, AppObjInfo.OBJ_MENU_SICKNESS_MONTHLY_REPORT), 
             AppObjInfo.COMMAND_VIEW)));
			 
flyout.addMenuFlyOut("reports.jsp", dictionaryD.getWord(I_Dictionary.REPORT), "Laporan Cuti Sakit", true, dictionaryD.getWord(I_Dictionary.ZERO_SICKNESS), "sickness", "#", ""+approot+"/report/sickness/zero_sickness.jsp",	userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_REPORTS, AppObjInfo.G2_MENU_SICKNESS_REPORT, AppObjInfo.OBJ_MENU_SICKNESS_ZERO_REPORT), 
             AppObjInfo.COMMAND_VIEW)));
			 
flyout.addMenuFlyOut("reports.jsp", dictionaryD.getWord(I_Dictionary.REPORT),"Laporan Ijin Khusus", true, dictionaryD.getWord(I_Dictionary.DAILY_REPORT), "special_dispensation", "#", ""+approot+"/report/specialdisp/daily_specialdisp.jsp",	userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_REPORTS, AppObjInfo.G2_MENU_SPECIAL_DISPENSATION_REPORT, AppObjInfo.OBJ_MENU_SPECIAL_DISPENSATION_DAILY_REPORT), 
             AppObjInfo.COMMAND_VIEW)));
			 
flyout.addMenuFlyOut("reports.jsp", dictionaryD.getWord(I_Dictionary.REPORT), "Laporan Ijin Khusus", true, dictionaryD.getWord(I_Dictionary.WEEKLY_REPORT), "special_dispensation", "#", ""+approot+"/report/specialdisp/weekly_specialdisp.jsp",	userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_REPORTS, AppObjInfo.G2_MENU_SPECIAL_DISPENSATION_REPORT, AppObjInfo.OBJ_MENU_SPECIAL_DISPENSATION_WEEKLY_REPORT), 
             AppObjInfo.COMMAND_VIEW)));
			 
flyout.addMenuFlyOut("reports.jsp", dictionaryD.getWord(I_Dictionary.REPORT), "Laporan Ijin Khusus", true, dictionaryD.getWord(I_Dictionary.MONTHLY_REPORT), "special_dispensation", "#", ""+approot+"/report/specialdisp/monthly_specialdisp.jsp",	userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_REPORTS, AppObjInfo.G2_MENU_SPECIAL_DISPENSATION_REPORT, AppObjInfo.OBJ_MENU_SPECIAL_DISPENSATION_MONTHLY_REPORT), 
             AppObjInfo.COMMAND_VIEW)));
		
flyout.addMenuFlyOut("reports.jsp", dictionaryD.getWord(I_Dictionary.REPORT), "Laporan Ijin Khusus", true, dictionaryD.getWord(I_Dictionary.YEAR_REPORT), "special_dispensation", "#", ""+approot+"/report/specialdisp/yearly_specialdisp.jsp",	userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_REPORTS, AppObjInfo.G2_MENU_SPECIAL_DISPENSATION_REPORT, AppObjInfo.OBJ_MENU_SPECIAL_DISPENSATION_MONTHLY_REPORT), 
             AppObjInfo.COMMAND_VIEW)));
			 
flyout.addMenuFlyOut("reports.jsp", dictionaryD.getWord(I_Dictionary.REPORT), dictionaryD.getWord(I_Dictionary.LEAVE_REPORT), true, dictionaryD.getWord(I_Dictionary.LEAVE_DP_SUMMARY), "leave_report", "#", ""+approot+"/report/leavedp/leave_dp_sum.jsp",	userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_REPORTS, AppObjInfo.G2_MENU_LEAVE_REPORT, AppObjInfo.OBJ_MENU_LEAVE_DP_SUMMARY), 
             AppObjInfo.COMMAND_VIEW)));
			 
flyout.addMenuFlyOut("reports.jsp", dictionaryD.getWord(I_Dictionary.REPORT), dictionaryD.getWord(I_Dictionary.LEAVE_REPORT), true, dictionaryD.getWord(I_Dictionary.LEAVE_DP_DETAIL), "leave_report", "#", ""+approot+"/report/leavedp/leave_dp_detail.jsp",	userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_REPORTS, AppObjInfo.G2_MENU_LEAVE_REPORT, AppObjInfo.OBJ_MENU_LEAVE_DP_DETAIL), 
             AppObjInfo.COMMAND_VIEW)));
			 
flyout.addMenuFlyOut("reports.jsp", dictionaryD.getWord(I_Dictionary.REPORT), dictionaryD.getWord(I_Dictionary.LEAVE_REPORT), true, dictionaryD.getWord(I_Dictionary.LEAVE_DP_SUMMARY), "leave_report", "#", ""+approot+"/report/leavedp/leave_department_by_period.jsp",	userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_REPORTS, AppObjInfo.G2_MENU_LEAVE_REPORT, AppObjInfo.OBJ_MENU_LEAVE_DP_SUMMARY_PERIOD), 
             AppObjInfo.COMMAND_VIEW)));
			 
flyout.addMenuFlyOut("reports.jsp", dictionaryD.getWord(I_Dictionary.REPORT), dictionaryD.getWord(I_Dictionary.LEAVE_REPORT), true, dictionaryD.getWord(I_Dictionary.LEAVE_DP_DETAIL_PERIOD), "leave_report", "#", ""+approot+"/report/leavedp/leave_dp_detail_period.jsp",	userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_REPORTS, AppObjInfo.G2_MENU_LEAVE_REPORT, AppObjInfo.OBJ_MENU_LEAVE_DP_DETAIL_PERIOD), 
             AppObjInfo.COMMAND_VIEW)));
			 
flyout.addMenuFlyOut("reports.jsp", dictionaryD.getWord(I_Dictionary.REPORT), dictionaryD.getWord(I_Dictionary.LEAVE_REPORT), true, dictionaryD.getWord(I_Dictionary.SPECIAL_UNPAID_PERIOD), "leave_report", "#", ""+approot+"/employee/leave/leave_sp_period.jsp",	userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_REPORTS, AppObjInfo.G2_MENU_LEAVE_REPORT, AppObjInfo.OBJ_MENU_SPECIAL_UNPAID_PERIOD), 
             AppObjInfo.COMMAND_VIEW)));
			 
flyout.addMenuFlyOut("reports.jsp", dictionaryD.getWord(I_Dictionary.REPORT), dictionaryD.getWord(I_Dictionary.LEAVE_REPORT), true, dictionaryD.getWord(I_Dictionary.DP_EXPIRED), "leave_report", "#", ""+approot+"/report/attendance/dpexp_report.jsp",	userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_REPORTS, AppObjInfo.G2_MENU_LEAVE_REPORT, AppObjInfo.OBJ_MENU_DP_EXPIRED), 
             AppObjInfo.COMMAND_VIEW)));
			 
flyout.addMenuFlyOut("reports.jsp", dictionaryD.getWord(I_Dictionary.REPORT), "Karyawan On Job Training", true, dictionaryD.getWord(I_Dictionary.MONTHLY_REPORT), "trainee", "#", ""+approot+"/report/training/monthly_tr_rpt.jsp",	userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_REPORTS, AppObjInfo.G2_MENU_TRAINEE_REPORT, AppObjInfo.OBJ_MENU_TRAINEE_MONTHLY_REPORT), 
             AppObjInfo.COMMAND_VIEW)));
			 
flyout.addMenuFlyOut("reports.jsp", dictionaryD.getWord(I_Dictionary.REPORT), "Karyawan On Job Training", true, dictionaryD.getWord(I_Dictionary.END_PERIOD), "trainee", "#", ""+approot+"/report/training/end_tr_rpt.jsp",	userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_REPORTS, AppObjInfo.G2_MENU_TRAINEE_REPORT, AppObjInfo.OBJ_MENU_TRAINEE_END_PERIOD), 
             AppObjInfo.COMMAND_VIEW)));
			 
flyout.addMenuFlyOut("reports.jsp", dictionaryD.getWord(I_Dictionary.REPORT), "Databank Karyawan", true, dictionaryD.getWord(I_Dictionary.REPORT_BY_CATEGORY), "employee", "#", ""+approot+"/report/employee/list_employee_category.jsp",	userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_REPORTS, AppObjInfo.G2_MENU_EMPLOYEE_REPORT, AppObjInfo.OBJ_MENU_LIST_EMPLOYEE_CATEGORY_REPORT), 
             AppObjInfo.COMMAND_VIEW)));
			 
flyout.addMenuFlyOut("reports.jsp", dictionaryD.getWord(I_Dictionary.REPORT), "Databank Karyawan", true, dictionaryD.getWord(I_Dictionary.REPORT_BY_RESIGN), "employee", "#", ""+approot+"/report/employee/list_employee_resignation1.jsp",	userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_REPORTS, AppObjInfo.G2_MENU_EMPLOYEE_REPORT, AppObjInfo.OBJ_MENU_LIST_EMPLOYEE_RESIGNATION_REPORT), 
             AppObjInfo.COMMAND_VIEW)));
			 
flyout.addMenuFlyOut("reports.jsp", dictionaryD.getWord(I_Dictionary.REPORT), "Databank Karyawan", true, dictionaryD.getWord(I_Dictionary.REPORT_BY_EDUCATION), "employee", "#", ""+approot+"/report/employee/list_employee_education.jsp",	userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_REPORTS, AppObjInfo.G2_MENU_EMPLOYEE_REPORT, AppObjInfo.OBJ_MENU_LIST_EMPLOYEE_EDUCATION_REPORT), 
             AppObjInfo.COMMAND_VIEW)));
			 
flyout.addMenuFlyOut("reports.jsp", dictionaryD.getWord(I_Dictionary.REPORT), "Databank Karyawan", true, dictionaryD.getWord(I_Dictionary.REPORT_BY_NAME), "employee", "#", ""+approot+"/report/employee/list_employee_by_Name.jsp",	userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_REPORTS, AppObjInfo.G2_MENU_EMPLOYEE_REPORT, AppObjInfo.OBJ_MENU_LIST_EMPLOYEE_CATEGORY_BY_NAME_REPORT), 
             AppObjInfo.COMMAND_VIEW)));
			 
flyout.addMenuFlyOut("reports.jsp", dictionaryD.getWord(I_Dictionary.REPORT), "Databank Karyawan", true, dictionaryD.getWord(I_Dictionary.REPORT_BY_ABSENCE), "employee", "#", ""+approot+"/report/employee/list_absence_reason.jsp",	userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_REPORTS, AppObjInfo.G2_MENU_EMPLOYEE_REPORT, AppObjInfo.OBJ_MENU_LIST_NUMBER_ABSENCES_REPORT), 
             AppObjInfo.COMMAND_VIEW)));
			
flyout.addMenuFlyOut("reports.jsp", dictionaryD.getWord(I_Dictionary.REPORT), "Databank Karyawan", true, dictionaryD.getWord(I_Dictionary.REPORT_BY_RACE), "employee", "#", ""+approot+"/report/employee/list_employee_race.jsp",	userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_REPORTS, AppObjInfo.G2_MENU_EMPLOYEE_REPORT, AppObjInfo.OBJ_MENU_LIST_EMPLOYEE_RACE_REPORT), 
             AppObjInfo.COMMAND_VIEW)));
			  
flyout.addMenuFlyOut("reports.jsp", dictionaryD.getWord(I_Dictionary.REPORT), dictionaryD.getWord(I_Dictionary.TRAINING_REPORT), true, dictionaryD.getWord(I_Dictionary.MONTHLY_REPORT), "training_report", "#", ""+approot+"/report/training/monthly_train.jsp",	userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_REPORTS, AppObjInfo.G2_MENU_TRAINING_REPORT, AppObjInfo.OBJ_MENU_MONTHLY_REPORT), 
             AppObjInfo.COMMAND_VIEW)));
			  
flyout.addMenuFlyOut("reports.jsp", dictionaryD.getWord(I_Dictionary.REPORT), dictionaryD.getWord(I_Dictionary.TRAINING_REPORT), true, dictionaryD.getWord(I_Dictionary.TRAINING_PROFILES), "training_report", "#", ""+approot+"/report/training/src_training_profiles.jsp",	userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_REPORTS, AppObjInfo.G2_MENU_TRAINING_REPORT, AppObjInfo.OBJ_MENU_PROFILES_REPORT), 
             AppObjInfo.COMMAND_VIEW))); 
			  
flyout.addMenuFlyOut("reports.jsp", dictionaryD.getWord(I_Dictionary.REPORT), dictionaryD.getWord(I_Dictionary.TRAINING_REPORT), true, dictionaryD.getWord(I_Dictionary.TRAINING_TARGET), "training_report", "#", ""+approot+"/report/training/src_training_target.jsp",	userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_REPORTS, AppObjInfo.G2_MENU_TRAINING_REPORT, AppObjInfo.OBJ_MENU_TARGET_REPORT), 
             AppObjInfo.COMMAND_VIEW)));
			  
flyout.addMenuFlyOut("reports.jsp", dictionaryD.getWord(I_Dictionary.REPORT), dictionaryD.getWord(I_Dictionary.TRAINING_REPORT), true, dictionaryD.getWord(I_Dictionary.REPORT_BY_DEPARTMENT), "training_report", "#", ""+approot+"/report/training/src_report_dept.jsp",	userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_REPORTS, AppObjInfo.G2_MENU_TRAINING_REPORT, AppObjInfo.OBJ_MENU_REPORT_BY_DEPARTEMENT), 
             AppObjInfo.COMMAND_VIEW)));
			  
flyout.addMenuFlyOut("reports.jsp", dictionaryD.getWord(I_Dictionary.REPORT), dictionaryD.getWord(I_Dictionary.TRAINING_REPORT), true, dictionaryD.getWord(I_Dictionary.REPORT_BY_EMPLOYEE), "training_report", "#", ""+approot+"/report/training/src_report_emp.jsp",	userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_REPORTS, AppObjInfo.G2_MENU_TRAINING_REPORT, AppObjInfo.OBJ_MENU_REPORT_BY_EMPLOYEE), 
             AppObjInfo.COMMAND_VIEW)));
			 
flyout.addMenuFlyOut("reports.jsp", dictionaryD.getWord(I_Dictionary.REPORT), dictionaryD.getWord(I_Dictionary.TRAINING_REPORT), true, dictionaryD.getWord(I_Dictionary.REPORT_BY_TRAINNER), "training_report", "#", ""+approot+"/report/training/src_report_trainer.jsp",	userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_REPORTS, AppObjInfo.G2_MENU_TRAINING_REPORT, AppObjInfo.OBJ_MENU_REPORT_BY_TRAINNER), 
             AppObjInfo.COMMAND_VIEW))); 
			 
flyout.addMenuFlyOut("reports.jsp", dictionaryD.getWord(I_Dictionary.REPORT), dictionaryD.getWord(I_Dictionary.TRAINING_REPORT), true, dictionaryD.getWord(I_Dictionary.REPORT_BY_COURSE_DETAIL), "training_report", "#", ""+approot+"/report/training/src_training_course.jsp",	userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_REPORTS, AppObjInfo.G2_MENU_TRAINING_REPORT, AppObjInfo.OBJ_MENU_REPORT_BY_COURSE_DETAIL), 
             AppObjInfo.COMMAND_VIEW))); 
			 
flyout.addMenuFlyOut("reports.jsp", dictionaryD.getWord(I_Dictionary.REPORT), dictionaryD.getWord(I_Dictionary.TRAINING_REPORT), true, dictionaryD.getWord(I_Dictionary.REPORT_BY_COURSE_DATE), "training_report", "#", ""+approot+"/report/training/src_training_coursedate.jsp",	userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_REPORTS, AppObjInfo.G2_MENU_TRAINING_REPORT, AppObjInfo.OBJ_MENU_REPORT_BY_COURSE_DATE), 
             AppObjInfo.COMMAND_VIEW))); 
			 
flyout.addMenuFlyOut("reports.jsp", dictionaryD.getWord(I_Dictionary.REPORT), "Laporan Khusus", true, "Laporan Pihak Terkait", "laporan_khusus", "#", ""+approot+"/report/training/src_training_coursedate.jsp",	userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_REPORTS, AppObjInfo.G2_MENU_TRAINING_REPORT, AppObjInfo.OBJ_MENU_REPORT_BY_COURSE_DATE), 
             AppObjInfo.COMMAND_VIEW))); 
flyout.addMenuFlyOut("reports.jsp", dictionaryD.getWord(I_Dictionary.REPORT), "Laporan Khusus", true, "Laporan LKPBU Form 801", "laporan_khusus", "#", ""+approot+"/report/employee/list_lkpbu_801.jsp",	userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_REPORTS, AppObjInfo.G2_MENU_TRAINING_REPORT, AppObjInfo.OBJ_MENU_REPORT_BY_COURSE_DATE), 
             AppObjInfo.COMMAND_VIEW))); 			 
flyout.addMenuFlyOut("reports.jsp", dictionaryD.getWord(I_Dictionary.REPORT), "Laporan Khusus", true, "Laporan LKPBU Form 802", "laporan_khusus", "#", ""+approot+"/report/employee/list_lkpbu_802.jsp",	userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_REPORTS, AppObjInfo.G2_MENU_TRAINING_REPORT, AppObjInfo.OBJ_MENU_REPORT_BY_COURSE_DATE), 
             AppObjInfo.COMMAND_VIEW))); 
flyout.addMenuFlyOut("reports.jsp", dictionaryD.getWord(I_Dictionary.REPORT), "Laporan Khusus", true, "Laporan LKPBU Form 803", "laporan_khusus", "#", ""+approot+"/report/employee/list_lkpbu_803.jsp",	userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_REPORTS, AppObjInfo.G2_MENU_TRAINING_REPORT, AppObjInfo.OBJ_MENU_REPORT_BY_COURSE_DATE), 
             AppObjInfo.COMMAND_VIEW))); 
flyout.addMenuFlyOut("reports.jsp", dictionaryD.getWord(I_Dictionary.REPORT), "Laporan Khusus", true, "Laporan LKPBU Form 804", "laporan_khusus", "#", ""+approot+"/report/employee/list_lkpbu_804.jsp",	userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_REPORTS, AppObjInfo.G2_MENU_TRAINING_REPORT, AppObjInfo.OBJ_MENU_REPORT_BY_COURSE_DATE), 
             AppObjInfo.COMMAND_VIEW))); 
flyout.addMenuFlyOut("reports.jsp", dictionaryD.getWord(I_Dictionary.REPORT), "Laporan Khusus", true, "Laporan LKPBU Form 805", "laporan_khusus", "#", ""+approot+"/report/employee/list_lkpbu_805.jsp",	userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_REPORTS, AppObjInfo.G2_MENU_TRAINING_REPORT, AppObjInfo.OBJ_MENU_REPORT_BY_COURSE_DATE), 
             AppObjInfo.COMMAND_VIEW))); 
flyout.addMenuFlyOut("reports.jsp", dictionaryD.getWord(I_Dictionary.REPORT), "Laporan Khusus", true, "Laporan LKPBU Form 806", "laporan_khusus", "#", ""+approot+"/report/employee/list_lkpbu_806.jsp",	userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_REPORTS, AppObjInfo.G2_MENU_TRAINING_REPORT, AppObjInfo.OBJ_MENU_REPORT_BY_COURSE_DATE), 
             AppObjInfo.COMMAND_VIEW))); 
			 
flyout.addMenuFlyOut("reports.jsp", dictionaryD.getWord(I_Dictionary.REPORT), "Laporan Alih Daya", true, "Form Master Alih Daya", "laporan_khusus", "#", ""+approot+"/outsource/outsource_plan_list.jsp",	userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_OUTSOURCE, AppObjInfo.G2_OUTSOURCE_DATA_ENTRY, AppObjInfo.OBJ_OUTSOURCE_MASTER_PLAN), 
             AppObjInfo.COMMAND_VIEW)));  
flyout.addMenuFlyOut("reports.jsp", dictionaryD.getWord(I_Dictionary.REPORT), "Laporan Alih Daya", true, "Evaluasi Alih Daya", "laporan_khusus", "#", ""+approot+"/outsource/evaluation_src.jsp",	userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_OUTSOURCE, AppObjInfo.G2_OUTSOURCE_DATA_ENTRY, AppObjInfo.OBJ_OUTSOURCE_FORM_EVALUASI), 
             AppObjInfo.COMMAND_VIEW)));  
flyout.addMenuFlyOut("reports.jsp", dictionaryD.getWord(I_Dictionary.REPORT), "Laporan Alih Daya", true, "Form Master Cost Alih Daya", "laporan_khusus", "#", ""+approot+"/outsource/master_cost.jsp",	userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_OUTSOURCE, AppObjInfo.G2_OUTSOURCE_DATA_ENTRY, AppObjInfo.OBJ_OUTSOURCE_COST_MASTER), 
             AppObjInfo.COMMAND_VIEW)));  
flyout.addMenuFlyOut("reports.jsp", dictionaryD.getWord(I_Dictionary.REPORT), "Laporan Alih Daya", true, "Form Input Cost Alih Daya", "laporan_khusus", "#", ""+approot+"/outsource/cost_src.jsp",	userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_OUTSOURCE, AppObjInfo.G2_OUTSOURCE_DATA_ENTRY, AppObjInfo.OBJ_OUTSOURCE_COST_INPUT), 
             AppObjInfo.COMMAND_VIEW))); 
flyout.addMenuFlyOut("reports.jsp", dictionaryD.getWord(I_Dictionary.REPORT), "Laporan Alih Daya", true, "Laporan Persetujuan Alih Daya", "laporan_khusus", "#", ""+approot+"/outsource/report_approval.jsp",	userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_OUTSOURCE, AppObjInfo.G2_OUTSOURCE_REPORT, AppObjInfo.OBJ_OUTSOURCE_RPT_APPROVAL), 
             AppObjInfo.COMMAND_VIEW))); 	 
flyout.addMenuFlyOut("reports.jsp", dictionaryD.getWord(I_Dictionary.REPORT), "Laporan Alih Daya", true, "Laporan Rencana Biaya Alih Daya", "laporan_khusus", "#", ""+approot+"/outsource/report_plan_cost.jsp",	userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_REPORTS, AppObjInfo.G2_MENU_TRAINING_REPORT, AppObjInfo.OBJ_MENU_REPORT_BY_COURSE_DATE), 
             AppObjInfo.COMMAND_VIEW))); 
flyout.addMenuFlyOut("reports.jsp", dictionaryD.getWord(I_Dictionary.REPORT), "Laporan Alih Daya", true, "Laporan Rekap Rencana Alih Daya", "laporan_khusus", "#", ""+approot+"/outsource/report_plan_sum.jsp",	userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_REPORTS, AppObjInfo.G2_MENU_TRAINING_REPORT, AppObjInfo.OBJ_MENU_REPORT_BY_COURSE_DATE), 
             AppObjInfo.COMMAND_VIEW))); 
flyout.addMenuFlyOut("reports.jsp", dictionaryD.getWord(I_Dictionary.REPORT), "Laporan Alih Daya", true, "Laporan Keluar Masuk Alih Daya", "laporan_khusus", "#", ""+approot+"/outsource/report_in_out.jsp",	userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_REPORTS, AppObjInfo.G2_MENU_TRAINING_REPORT, AppObjInfo.OBJ_MENU_REPORT_BY_COURSE_DATE), 
             AppObjInfo.COMMAND_VIEW))); 
flyout.addMenuFlyOut("reports.jsp", dictionaryD.getWord(I_Dictionary.REPORT), "Laporan Alih Daya", true, "Laporan Evaluation Alih Daya", "laporan_khusus", "#", ""+approot+"/outsource/outsource_evaluation_report.jsp",	userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_OUTSOURCE, AppObjInfo.G2_OUTSOURCE_REPORT, AppObjInfo.OBJ_OUTSOURCE_RPT_APPROVAL), 
             AppObjInfo.COMMAND_VIEW))); 
//flyout.addMenuFlyOut("reports.jsp", dictionaryD.getWord(I_Dictionary.REPORT), "Laporan Alih Daya", true, "Laporan Rencana Biaya Alih Daya", "laporan_khusus", "#", ""+approot+"/outsource/outsource_evaluation_report.jsp",	userSession.checkPrivilege(AppObjInfo.composeCode(
flyout.addMenuFlyOut("reports.jsp", dictionaryD.getWord(I_Dictionary.REPORT), dictionaryD.getWord(I_Dictionary.PAYROLL), true, dictionaryD.getWord(I_Dictionary.SALARY_TRANSFER), "payroll", "#", ""+approot+"/report/payroll/gaji_transfer.jsp",	userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_REPORTS, AppObjInfo.G2_MENU_PAYROLL_REPORT, AppObjInfo.OBJ_MENU_LIST_SALARY_SUMMARY_REPORT), 
             AppObjInfo.COMMAND_VIEW)));             
//flyout.addMenuFlyOut("reports.jsp", dictionaryD.getWord(I_Dictionary.REPORT), "Laporan Alih Daya", true, "Laporan Rekap Rencana Alih Daya", "laporan_khusus", "#", ""+approot+"/outsource/outsource_rekap.jsp",	userSession.checkPrivilege(AppObjInfo.composeCode(

flyout.addMenuFlyOut("reports.jsp", dictionaryD.getWord(I_Dictionary.REPORT), dictionaryD.getWord(I_Dictionary.PAYROLL), true, dictionaryD.getWord(I_Dictionary.SALARY_SUMMARY), "payroll", "#", ""+approot+"/report/payroll/src_list_benefit_deduction.jsp",	userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_REPORTS, AppObjInfo.G2_MENU_PAYROLL_REPORT, AppObjInfo.OBJ_MENU_LIST_SALARY_SUMMARY_REPORT), 
             AppObjInfo.COMMAND_VIEW)));
			 
flyout.addMenuFlyOut("reports.jsp", dictionaryD.getWord(I_Dictionary.REPORT), dictionaryD.getWord(I_Dictionary.PAYROLL), true, dictionaryD.getWord(I_Dictionary.REPORT_REKONSILIASI), "payroll", "#", ""+approot+"/report/payroll/src_list_benefit_deduction_department.jsp",	userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_REPORTS, AppObjInfo.G2_MENU_PAYROLL_REPORT, AppObjInfo.OBJ_MENU_LIST_SALARY_SUMMARY_REPORT), 
             AppObjInfo.COMMAND_VIEW)));

flyout.addMenuFlyOut("reports.jsp", dictionaryD.getWord(I_Dictionary.REPORT), dictionaryD.getWord(I_Dictionary.PAYROLL), true, dictionaryD.getWord(I_Dictionary.REPORT_ESPT_MONTHLY), "payroll", "#", ""+approot+"/report/payroll/src_espt_month.jsp",	userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_REPORTS, AppObjInfo.G2_MENU_PAYROLL_REPORT, AppObjInfo.OBJ_MENU_LIST_SALARY_SUMMARY_REPORT), 
             AppObjInfo.COMMAND_VIEW)));
			 
flyout.addMenuFlyOut("reports.jsp", dictionaryD.getWord(I_Dictionary.REPORT), dictionaryD.getWord(I_Dictionary.PAYROLL), true, "Report for A1 Tax-Form ", "payroll", "#", ""+approot+"/report/payroll/src_espt_A1.jsp",	userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_REPORTS, AppObjInfo.G2_MENU_PAYROLL_REPORT, AppObjInfo.OBJ_MENU_LIST_SALARY_SUMMARY_REPORT), 
             AppObjInfo.COMMAND_VIEW)));
			 
flyout.addMenuFlyOut("reports.jsp", dictionaryD.getWord(I_Dictionary.REPORT), dictionaryD.getWord(I_Dictionary.PAYROLL), true, dictionaryD.getWord(I_Dictionary.JV_REPORT), "payroll", "#", ""+approot+"/report/payroll/jv_report.jsp",	userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_REPORTS, AppObjInfo.G2_MENU_PAYROLL_REPORT, AppObjInfo.OBJ_MENU_LIST_SALARY_SUMMARY_REPORT), 
             AppObjInfo.COMMAND_VIEW)));

flyout.addMenuFlyOut("reports.jsp", dictionaryD.getWord(I_Dictionary.REPORT), dictionaryD.getWord(I_Dictionary.PAYROLL), true, dictionaryD.getWord(I_Dictionary.LEAVE_ENTITLE_REPORT), "payroll", "#", ""+approot+"/report/payroll/leave_entitle_report.jsp",	userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_REPORTS, AppObjInfo.G2_MENU_PAYROLL_REPORT, AppObjInfo.OBJ_MENU_LIST_SALARY_SUMMARY_REPORT), 
             AppObjInfo.COMMAND_VIEW)));

flyout.addMenuFlyOut("reports.jsp", dictionaryD.getWord(I_Dictionary.REPORT), dictionaryD.getWord(I_Dictionary.PAYROLL), true, dictionaryD.getWord(I_Dictionary.TAKEN_LEAVE_REPORT), "payroll", "#", ""+approot+"/report/payroll/taken_leave_report.jsp",	userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_REPORTS, AppObjInfo.G2_MENU_PAYROLL_REPORT, AppObjInfo.OBJ_MENU_LIST_SALARY_SUMMARY_REPORT), 
             AppObjInfo.COMMAND_VIEW)));

flyout.addMenuFlyOut("reports.jsp", dictionaryD.getWord(I_Dictionary.REPORT), dictionaryD.getWord(I_Dictionary.CUSTOM_REPORT), false, null, "payroll", ""+approot+"/report/payroll/custom_rpt_main.jsp", "",	userSession.checkPrivilege(AppObjInfo.composeCode(
			AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_REPORTS, AppObjInfo.G2_MENU_PAYROLL_REPORT, AppObjInfo.OBJ_MENU_LIST_SALARY_SUMMARY_REPORT), 
			AppObjInfo.COMMAND_VIEW)));

/* 16) System */
flyout.addMenuFlyOut("system.jsp", dictionaryD.getWord(I_Dictionary.SYSTEM), dictionaryD.getWord(I_Dictionary.SERVICE), true, dictionaryD.getWord(I_Dictionary.SERVICE_CENTER), "service_center", "#", ""+approot+"/service/service_center.jsp", userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_SYSTEM, AppObjInfo.G2_MENU_SERVICE_CENTER, AppObjInfo.OBJ_MENU_SERVICE_CENTER), 
             AppObjInfo.COMMAND_VIEW))); 
			 
flyout.addMenuFlyOut("system.jsp", dictionaryD.getWord(I_Dictionary.SYSTEM), dictionaryD.getWord(I_Dictionary.SERVICE), true, dictionaryD.getWord(I_Dictionary.MANUAL_SERVICE), "manual_prosess", "#", ""+approot+"/service/attendance_manual_calculation.jsp", userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_SYSTEM, AppObjInfo.G2_MENU_MANUAL_PROCESS, AppObjInfo.OBJ_MENU_MANUAL_PROCESS), 
             AppObjInfo.COMMAND_VIEW)));

flyout.addMenuFlyOut("system.jsp", dictionaryD.getWord(I_Dictionary.SYSTEM), dictionaryD.getWord(I_Dictionary.EMAIL_LIST), false, null, "system_management", ""+approot+"/service/email_logs.jsp", "", userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_SYSTEM, AppObjInfo.G2_SERVICE_CENTER, AppObjInfo.OBJ_SERVICE_EMAIL), 
             AppObjInfo.COMMAND_VIEW))); 

flyout.addMenuFlyOut("system.jsp", dictionaryD.getWord(I_Dictionary.SYSTEM), dictionaryD.getWord(I_Dictionary.USER_MANAGEMENT), true, dictionaryD.getWord(I_Dictionary.USER_LIST), "user_management", "#", ""+approot+"/admin/userlist.jsp", userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_SYSTEM, AppObjInfo.G2_MENU_USER_MANAGEMENT, AppObjInfo.OBJ_MENU_USER_LIST), 
             AppObjInfo.COMMAND_VIEW)));

flyout.addMenuFlyOut("system.jsp", dictionaryD.getWord(I_Dictionary.SYSTEM), dictionaryD.getWord(I_Dictionary.USER_MANAGEMENT), true, dictionaryD.getWord(I_Dictionary.GROUP_PRIVILEDGE), "user_management", "#", ""+approot+"/admin/grouplist.jsp", userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_SYSTEM, AppObjInfo.G2_MENU_USER_MANAGEMENT, AppObjInfo.OBJ_MENU_USER_GROUP), 
             AppObjInfo.COMMAND_VIEW)));

flyout.addMenuFlyOut("system.jsp", dictionaryD.getWord(I_Dictionary.SYSTEM), dictionaryD.getWord(I_Dictionary.USER_MANAGEMENT), true, dictionaryD.getWord(I_Dictionary.PRIVILEDGE), "user_management", "#", ""+approot+"/admin/privilegelist.jsp", userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_SYSTEM, AppObjInfo.G2_MENU_USER_MANAGEMENT, AppObjInfo.OBJ_MENU_USER_PRIVILEGE), 
             AppObjInfo.COMMAND_VIEW)));

		 
flyout.addMenuFlyOut("system.jsp", dictionaryD.getWord(I_Dictionary.SYSTEM), dictionaryD.getWord(I_Dictionary.SYSTEM_PROPERTIES), false, null, "system_management", ""+approot+"/system/sysprop.jsp", "", userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_SYSTEM, AppObjInfo.G2_MENU_SYSTEM_MANAGEMENT, AppObjInfo.OBJ_MENU_SYSTEM_PROPERTIES), 
             AppObjInfo.COMMAND_VIEW))); 
			 		 
	
flyout.addMenuFlyOut("system.jsp", dictionaryD.getWord(I_Dictionary.SYSTEM), dictionaryD.getWord(I_Dictionary.SERVICE_MANAGER), false, null, "time_keeping", ""+approot+"/system/timekeepingpro/svcmgr.jsp", "", userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_SYSTEM, AppObjInfo.G2_MENU_TIMEKEEPING, AppObjInfo.OBJ_MENU_SERVICE_MANAGER), 
             AppObjInfo.COMMAND_VIEW)));

flyout.addMenuFlyOut("system.jsp", dictionaryD.getWord(I_Dictionary.SYSTEM), "User Activity Log", false, null, "User Activity Log", ""+approot+"/system/user_activity_log/user_activity_log.jsp", "", userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_SYSTEM, AppObjInfo.G2_MENU_SYSTEM_MANAGEMENT, AppObjInfo.OBJ_MENU_SYSTEM_PROPERTIES), 
             AppObjInfo.COMMAND_VIEW))); 

flyout.addMenuFlyOut("system.jsp", dictionaryD.getWord(I_Dictionary.SYSTEM), "Master Data Upload", false, null, "Master Data Upload", ""+approot+"/system/dataupload/data_upload_group.jsp", "", userSession.checkPrivilege(AppObjInfo.composeCode(
             AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_SYSTEM, AppObjInfo.G2_MENU_SYSTEM_MANAGEMENT, AppObjInfo.OBJ_MENU_SYSTEM_PROPERTIES), 
             AppObjInfo.COMMAND_VIEW))); 
//flyout.MenuFlyout(out, url, isMSIE, approot);
flyout.drawMenuFlayOut(out, url, isMSIEE, approot);
// flyout.drawMenuFlayOutMcHen(out, url, isMSIEE, approot);
    }
    
    
}
