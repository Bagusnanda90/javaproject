/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.form.search;

import com.dimata.harisma.session.employee.SearchSpecialQuery;
import com.dimata.qdep.form.FRMHandler;
import com.dimata.qdep.form.FRMQueryString;
import com.dimata.qdep.form.I_FRMInterface;
import com.dimata.qdep.form.I_FRMType;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Dimata 007
 */
public class FrmSrcSpecialEmployeeQuery extends FRMHandler implements I_FRMInterface, I_FRMType{
    private SearchSpecialQuery searchSpecialQuery;

	public static final String FRM_NAME_SRCEMPLOYEE		=  "FRM_NAME_SRCSPECIAL_QUERY" ;

	public static final int FRM_FIELD_DEPARTMENT		=  0 ;
	public static final int FRM_FIELD_POSITION		=  1 ;
	public static final int FRM_FIELD_SECTION		=  2 ;
	public static final int FRM_FIELD_LEVEL			=  3 ;
	public static final int FRM_FIELD_COMMDATEFROM		=  4 ;
	public static final int FRM_FIELD_COMMDATETO		=  5 ;
	public static final int FRM_FIELD_WORKYEARFROM		=  6 ;
	public static final int FRM_FIELD_WORKMONTHFROM		=  7 ;
	public static final int FRM_FIELD_WORKYEARTO		=  8 ;
	public static final int FRM_FIELD_WORKMONTHTO		=  9 ;
	public static final int FRM_FIELD_AGEYEARFROM		=  10 ;
	public static final int FRM_FIELD_AGEMONTHFROM		=  11 ;
	public static final int FRM_FIELD_AGEYEARTO		=  12 ;
	public static final int FRM_FIELD_AGEMONTHTO		=  13 ;
	public static final int FRM_FIELD_EDUCATION		=  14 ;
	public static final int FRM_FIELD_RELIGION		=  15 ;
	public static final int FRM_FIELD_MARITAL		=  16 ;
	public static final int FRM_FIELD_BLOOD			=  17 ;
	public static final int FRM_FIELD_LANGUAGE		=  18 ;
	public static final int FRM_FIELD_SEX			=  19 ;
	public static final int FRM_FIELD_RESIGNED		=  20 ;
	public static final int FRM_FIELD_SORTBY		=  21 ;
        public static final int FRM_FIELD_RACE                  =  22 ;
        //update by satrya 2012-11-15
        public static final int FRM_FIELD_ORDER                  =  23 ;
        public static final int FRM_FIELD_COMPANY_ID            = 24;
        
        //update by satrya 2013-08-05
        public static final int FRM_FIELD_EMPLOYEENUM            = 25;
        public static final int FRM_FIELD_EMP_FULLNAME            = 26;
        public static final int FRM_FIELD_EMP_ADDRESS            = 27;
        public static final int FRM_FIELD_EMP_CATEGORY_ID            = 28;
        public static final int FRM_FIELD_EMP_DIVISION_ID            = 29;
        public static final int FRM_FIELD_EMP_BIRTH_DAY_CHEKED            = 30;
        public static final int FRM_FIELD_EMP_BIRTH_DAY_MONTH            = 31;
        public static final int FRM_FIELD_EMP_SALARY_LEVEL_ID           = 32;
        public static final int FRM_FIELD_EMP_START_RESIGN           = 33;
        public static final int FRM_FIELD_EMP_END_RESIGN           = 34;
        public static final int FRM_FIELD_RADIO_COMERCING_DATE           = 35;

        public static final int FRM_FIELD_PROVINSI_ID           = 36;
        public static final int FRM_FIELD_KABUPATEN_ID           = 37;
        public static final int FRM_FIELD_KECAMATAN_ID           = 38;
        public static final int FRM_FIELD_BIRTH_MONTH           =39;
        
        public static final int FRM_FIELD_ADDRESS_EMP_PERMANET           =40;
        public static final int FRM_FIELD_COUNTRY               = 41;
        public static final int FRM_FIELD_COUNTRY_PERMANENT     = 42;
        public static final int FRM_FIELD_PROVINSI_ID_PERMANENT = 43;
        public static final int FRM_FIELD_KABUPATEN_ID_PERMANENT= 44;
        public static final int FRM_FIELD_KECAMATAN_ID_PERMANENT= 45;
        
        public static final int FRM_FIELD_BIRTH_DATE_FROM           = 46;
        public static final int FRM_FIELD_BIRTH_DATE_TO           = 47;
        
        public static final int FRM_FIELD_BIRTH_YEAR_FROM           = 48;
        public static final int FRM_FIELD_BIRTH_YEAR_TO           = 49;
        
        //update by satrya 2013-10-16
        public static final int FRM_FIELD_DATE_PERIOD_START          = 50;
        public static final int FRM_FIELD_DATE_PERIOD_END          = 51;
        
        //update by satrya 2013-11-13
         public static final int FRM_FIELD_DATE_CARRIER_WORK_START          = 52;
        public static final int FRM_FIELD_DATE_CARRIER_WORK_END          = 53;
        public static final int FRM_FIELD_CARRIER_EMP_CATEGORY          = 54;
        
        //update by agus priska 29 - 10 2014
        //ini digunakan untuk style list baru yang akan digunakan
        public static final int FRM_FIELD_STYLIST = 55;
        public static final int FRM_FIELD_LOCATION = 56;
        public static final int FRM_FIELD_GRADE = 57;
        
        //update agus priska 2014-10-30
        
        public static final int FRM_FIELD_RADIO_END_CONTRACT           = 58;
	public static final int FRM_FIELD_ENDCONTRACTFROM		=  59 ;
	public static final int FRM_FIELD_ENDCONTRACTTO		=  60 ;
        // update by Hendra Putu 2015-01-31
        public static final int FRM_FIELD_RESIGNED_REASON_ID = 61;
        
        public static final int FRM_FIELD_PAYROLL_GROUP_ID = 62;
           
	public static String[] fieldNames = {
		"FRM_FIELD_DEPARTMENT",//0
                "FRM_FIELD_POSITION",//1
		"FRM_FIELD_SECTION",//2
                "FRM_FIELD_LEVEL",//3
		"FRM_FIELD_COMMDATEFROM",//4
                "FRM_FIELD_COMMDATETO",//5
		"FRM_FIELD_WORKYEARFROM",//6
                "FRM_FIELD_WORKMONTHFROM",//7
		"FRM_FIELD_WORKYEARTO",//8
                "FRM_FIELD_WORKMONTHTO",//9
		"FRM_FIELD_AGEYEARFROM",//10
                "FRM_FIELD_AGEMONTHFROM",//11
		"FRM_FIELD_AGEYEARTO",  //12
                "FRM_FIELD_AGEMONTHTO",//13
		"FRM_FIELD_EDUCATION",  //14
                "FRM_FIELD_RELIGION",//15
		"FRM_FIELD_MARITAL",  //16
                "FRM_FIELD_BLOOD",//17
		"FRM_FIELD_LANGUAGE",//18
                "FRM_FIELD_SEX",//19
		"FRM_FIELD_RESIGNED",//20
                "FRM_FIELD_SORTBY",//21
                "FRM_FIELD_RACE",//22
                "FRM_FIELD_ORDER",//23 //update by satrya 2012-11-15
                "FRM_FIELD_COMPANY_ID",//24
                
                "FRM_FIELD_EMPLOYEENUM",
                "FRM_FIELD_EMP_FULLNAME",
                "FRM_FIELD_EMP_ADDRESS",
                "FRM_FIELD_EMP_CATEGORY_ID",
                "FRM_FIELD_EMP_DIVISION_ID",
                "FRM_FIELD_EMP_BIRTH_DAY_CHEKED",
                "FRM_FIELD_EMP_BIRTH_DAY_MONTH",
                "FRM_FIELD_EMP_SALARY_LEVEL_ID",
                "FRM_FIELD_EMP_START_RESIGN",
                "FRM_FIELD_EMP_END_RESIGN",
                "FRM_FIELD_RADIO_COMERCING_DATE",
                
                "FRM_FIELD_PROVINSI_ID",
                "FRM_FIELD_KABUPATEN_ID",
                "FRM_FIELD_KECAMATAN_ID",
                "FRM_FIELD_BIRTH_MONTH",
                "FRM_FIELD_ADDRESS_EMP_PERMANET",
                "FRM_FIELD_COUNTRY",
                "FRM_FIELD_COUNTRY_PERMANENT",
                "FRM_FIELD_PROVINSI_ID_PERMANENT",
                "FRM_FIELD_KABUPATEN_ID_PERMANENT",
                "FRM_FIELD_KECAMATAN_ID_PERMANENT",
                "FRM_FIELD_BIRTH_DATE_FROM",
                "FRM_FIELD_BIRTH_DATE_TO",
                "FRM_FIELD_BIRTH_YEAR_FROM",
                "FRM_FIELD_BIRTH_YEAR_TO",
                "FRM_FIELD_DATE_PERIOD_START",
                "FRM_FIELD_DATE_PERIOD_END",
                 //update by satrya 2013-11-13
                "FRM_FIELD_DATE_CARRIER_WORK_START",
                "FRM_FIELD_DATE_CARRIER_WORK_END",
                "FRM_FIELD_CARRIER_EMP_CATEGORY",
                "FRM_FIELD_STYLIST",
                //update by priska 2014-11-3
                "FRM_FIELD_LOCATION",
                "FRM_FIELD_GRADE",
                "FRM_FIELD_RADIO_END_CONTRACT",
                "FRM_FIELD_ENDCONTRACTFROM",
                "FRM_FIELD_ENDCONTRACTTO",
                // update by hendra
                "FRM_FIELD_RESIGNED_REASON_ID",
                
                "FRM_FIELD_PAYROLL_GROUP_ID"
                
                
	} ;

	public static int[] fieldTypes = {
		TYPE_LONG,//0 FRM_FIELD_DEPARTMENT
                TYPE_LONG,//1 FRM_FIELD_POSITION
		TYPE_LONG,//2 FRM_FIELD_SECTION
                TYPE_LONG,//3 FRM_FIELD_LEVEL
		TYPE_DATE,//4 FRM_FIELD_COMMDATEFROM
                TYPE_DATE,//5 FRM_FIELD_COMMDATETO
		TYPE_INT,//6  FRM_FIELD_WORKYEARFROM
                TYPE_INT,//7  FRM_FIELD_WORKMONTHFROM
		TYPE_INT,//8  FRM_FIELD_WORKYEARTO
                TYPE_INT,//9  FRM_FIELD_WORKMONTHTO
		TYPE_INT,//10 FRM_FIELD_AGEYEARFROM
                TYPE_INT,//11 FRM_FIELD_AGEMONTHFROM
		TYPE_INT,//12 FRM_FIELD_AGEYEARTO
                TYPE_INT,//13 FRM_FIELD_AGEMONTHTO
		TYPE_LONG,//14 FRM_FIELD_EDUCATION
                TYPE_LONG,//15 FRM_FIELD_RELIGION
		TYPE_LONG,//16 FRM_FIELD_MARITAL
                TYPE_INT,//17  FRM_FIELD_BLOOD
		TYPE_LONG,//18 FRM_FIELD_LANGUAGE
                TYPE_INT,//19  FRM_FIELD_SEX
		TYPE_INT,//20  FRM_FIELD_RESIGNED
                TYPE_INT, //21 FRM_FIELD_SORTBY
                TYPE_INT,//22  FRM_FIELD_RACE   //update by satrya 2012-11-15
                TYPE_LONG,//23 FRM_FIELD_ORDER
                TYPE_LONG, //24 FRM_FIELD_COMPANY_ID
                
                TYPE_STRING,
                TYPE_STRING,
                TYPE_STRING,
                TYPE_LONG,
                TYPE_LONG,
                TYPE_BOOL,
                TYPE_INT,
                TYPE_LONG,
                TYPE_DATE,
                TYPE_DATE,
                TYPE_INT,
                TYPE_LONG,//"FRM_FIELD_PROVINSI_ID",
                TYPE_LONG,//"FRM_FIELD_KABUPATEN_ID",
                TYPE_LONG,//"FRM_FIELD_KECAMATAN_ID",
                TYPE_INT,//"FRM_FIELD_BIRTH_MONTH",
                TYPE_STRING,//"FRM_FIELD_ADDRESS_EMP_PERMANET",
                TYPE_LONG,//"FRM_FIELD_COUNTRY",
                TYPE_LONG,//"FRM_FIELD_COUNTRY_PERMANENT",
                TYPE_LONG,//"FRM_FIELD_PROVINSI_ID_PERMANENT",
                TYPE_LONG,//"FRM_FIELD_KABUPATEN_ID_PERMANENT",
                TYPE_LONG,//"FRM_FIELD_KECAMATAN_ID_PERMANENT"
                TYPE_STRING,//"FRM_FIELD_BIRTH_DATE_FROM",
                TYPE_STRING,//"FRM_FIELD_BIRTH_DATE_TO",
                TYPE_INT,
                TYPE_INT,
                TYPE_DATE,
                TYPE_DATE,
                //update by satrya 2013-11-13
                TYPE_DATE,
                TYPE_DATE,
                TYPE_LONG,
                TYPE_INT,
                //update by priska 2014-11-03
                TYPE_LONG,
                TYPE_LONG,
                TYPE_INT,//RADIO END CONTRACT
		TYPE_DATE,// FRM_FIELD_ENDCONTRACTFROM
                TYPE_DATE,// FRM_FIELD_ENDCONTRACTTO
                // hendra
                TYPE_LONG,
                TYPE_LONG
                
	} ;

	public FrmSrcSpecialEmployeeQuery(){
	}
	public FrmSrcSpecialEmployeeQuery(SearchSpecialQuery specialQuery){
		this.searchSpecialQuery = specialQuery;
	}

	public FrmSrcSpecialEmployeeQuery(HttpServletRequest request, SearchSpecialQuery specialQuery){
		super(new FrmSrcSpecialEmployeeQuery(specialQuery), request);
		this.searchSpecialQuery = specialQuery;
	}

	public String getFormName() { return FRM_NAME_SRCEMPLOYEE; } 

	public int[] getFieldTypes() { return fieldTypes; }

	public String[] getFieldNames() { return fieldNames; } 

	public int getFieldSize() { return fieldNames.length; } 

	public SearchSpecialQuery getEntityObject(){ return searchSpecialQuery; }

	public void requestEntityObject(SearchSpecialQuery specialQuery) {
		try{
			//this.requestParam();
                    this.requestParam();
                    SimpleDateFormat sdf = new SimpleDateFormat("EE MMM dd HH:mm:ss z yyyy",
                                            Locale.ENGLISH);
                        specialQuery.addArrCompany(this.getParamsStringValues(fieldNames[FRM_FIELD_COMPANY_ID]));
                        specialQuery.addArrDepartmentId(this.getParamsStringValues(fieldNames[FRM_FIELD_DEPARTMENT]));
                        specialQuery.addArrPositionId(this.getParamsStringValues(fieldNames[FRM_FIELD_POSITION]));        
                        specialQuery.addArrSectionId(this.getParamsStringValues(fieldNames[FRM_FIELD_SECTION]));        
                        specialQuery.addArrLevelId(this.getParamsStringValues(fieldNames[FRM_FIELD_LEVEL]));
                        specialQuery.addArrEducationId(this.getParamsStringValues(fieldNames[FRM_FIELD_EDUCATION]));
                        specialQuery.addArrReligionId(this.getParamsStringValues(fieldNames[FRM_FIELD_RELIGION]));
                        specialQuery.addArrMaritalId(this.getParamsStringValues(fieldNames[FRM_FIELD_MARITAL]));
                        specialQuery.addArrBlood(this.getParamsStringValues(fieldNames[FRM_FIELD_BLOOD]));
                        
                        specialQuery.addArrLanguageId(this.getParamsStringValues(fieldNames[FRM_FIELD_LANGUAGE]));
                        specialQuery.setiSex(getInt(FRM_FIELD_SEX));
                        specialQuery.setiResigned(getInt(FRM_FIELD_RESIGNED));

                        specialQuery.setRadiocommdate(getInt(FRM_FIELD_RADIO_COMERCING_DATE));
                       

                        specialQuery.addWorkyearfrom(""+getInt(FRM_FIELD_WORKYEARFROM));
                        specialQuery.addWorkmonthfrom(""+getInt(FRM_FIELD_WORKMONTHFROM));
                        specialQuery.addWorkyearto(""+getInt(FRM_FIELD_WORKYEARTO));
                        specialQuery.addWorkmonthto(""+getInt(FRM_FIELD_WORKMONTHTO));

                        specialQuery.addAgeyearfrom(""+getInt(FRM_FIELD_AGEYEARFROM));
                        specialQuery.addAgemonthfrom(""+getInt(FRM_FIELD_AGEMONTHFROM));
                        specialQuery.addAgeyearto(""+getInt(FRM_FIELD_AGEYEARTO));
                        specialQuery.addAgemonthto(""+getInt(FRM_FIELD_AGEMONTHTO));

                        specialQuery.addSort(""+getInt(FRM_FIELD_SORTBY));
                        specialQuery.addArrRaceId(this.getParamsStringValues(fieldNames[FRM_FIELD_RACE]));
                        
                        String commDateFrom = getString(FRM_FIELD_COMMDATEFROM);
                        if (commDateFrom.equals("")){
                            specialQuery.addCommdatefrom(null);
                        } else {
                            java.util.Date dtCommDateFrom = sdf.parse(commDateFrom);
                            specialQuery.addCommdatefrom(dtCommDateFrom);
                        }
                        String commDateTo = getString(FRM_FIELD_COMMDATETO);
                        if (commDateTo.equals("")){
                            specialQuery.addCommdateto(null);
                        } else {
                            java.util.Date dtCommDateTo = sdf.parse(commDateTo);
                            specialQuery.addCommdateto(dtCommDateTo);
                        }
                        
                        
                        specialQuery.setEmpnumber(getString(FRM_FIELD_EMPLOYEENUM));
                        specialQuery.setFullNameEmp(getString(FRM_FIELD_EMP_FULLNAME));
                        specialQuery.setAddrsEmployee(getString(FRM_FIELD_EMP_ADDRESS));
                        specialQuery.addArrEmpCategory(this.getParamsStringValues(fieldNames[FRM_FIELD_EMP_CATEGORY_ID]));
			specialQuery.addArrDivision(this.getParamsStringValues(fieldNames[FRM_FIELD_EMP_DIVISION_ID]));
                        specialQuery.setBirthdayChecked(getBoolean(FRM_FIELD_EMP_BIRTH_DAY_CHEKED));
                        specialQuery.setBirthMonth(getInt(FRM_FIELD_EMP_BIRTH_DAY_MONTH));
                        specialQuery.setSalaryLevel(getString(FRM_FIELD_EMP_SALARY_LEVEL_ID));
                        specialQuery.setStartResign(getDate(FRM_FIELD_EMP_START_RESIGN));
                        specialQuery.setEndResign(getDate(FRM_FIELD_EMP_END_RESIGN));
                        
                        
                        specialQuery.setAddrsEmployee(getString(FRM_FIELD_EMP_ADDRESS));
                        specialQuery.setAddressPermanent(getString(FRM_FIELD_ADDRESS_EMP_PERMANET));
                        
                        specialQuery.addArrCountryId(this.getParamsStringValues(fieldNames[FRM_FIELD_COUNTRY]));
                        specialQuery.addArrCountryIdPermanent(this.getParamsStringValues(fieldNames[FRM_FIELD_COUNTRY_PERMANENT]));
                        specialQuery.addArrProvinsiId(this.getParamsStringValues(fieldNames[FRM_FIELD_PROVINSI_ID]));
			specialQuery.addArrProvinsiIdPermanent(this.getParamsStringValues(fieldNames[FRM_FIELD_PROVINSI_ID_PERMANENT]));
                        specialQuery.addArrKabupatenId(this.getParamsStringValues(fieldNames[FRM_FIELD_KABUPATEN_ID]));
                        specialQuery.addArrKabupatenIdPermanent(this.getParamsStringValues(fieldNames[FRM_FIELD_KABUPATEN_ID_PERMANENT]));
                        
                        specialQuery.addArrKecamatanId(this.getParamsStringValues(fieldNames[FRM_FIELD_KECAMATAN_ID]));
                        specialQuery.addArrKecamatanIdPermanent(this.getParamsStringValues(fieldNames[FRM_FIELD_KECAMATAN_ID_PERMANENT]));
                       //specialQuery.setBirthMonth(getInt(FRM_FIELD_BIRTH_MONTH));
                       
                       String birthFrom = getString(FRM_FIELD_BIRTH_DATE_FROM);
                       if (birthFrom.equals("")){
                           specialQuery.setDtBirthFrom(null);
                       } else {
                           java.util.Date dtBirthFrom = sdf.parse(birthFrom);
                           specialQuery.setDtBirthFrom(dtBirthFrom);
                       }
                       String birthTo = getString(FRM_FIELD_BIRTH_DATE_TO);
                       if (birthTo.equals("")){
                           specialQuery.setDtBirthTo(null);
                       } else {
                           java.util.Date dtBirthTo = sdf.parse(birthTo);
                           specialQuery.setDtBirthTo(dtBirthTo);
                       }
                       
                       specialQuery.setBirthYearFrom(getInt(FRM_FIELD_BIRTH_YEAR_FROM));
                       specialQuery.setBirthYearTo(getInt(FRM_FIELD_BIRTH_YEAR_TO));
                       
                       //update by satrya 2013-10-16
                       specialQuery.setDtPeriodStart(getDate(FRM_FIELD_DATE_PERIOD_START));//belum di pakai 2013-10-17
                       specialQuery.setDtPeriodEnd(getDate(FRM_FIELD_DATE_PERIOD_END));
                       
                        //update by satrya 2013-11-13
                        specialQuery.setDtCarrierWorkStart(getDate(FRM_FIELD_DATE_CARRIER_WORK_START));
                        specialQuery.setDtCarrierWorkEnd(getDate(FRM_FIELD_DATE_CARRIER_WORK_END));
                        specialQuery.setCarrierCategoryEmp(getLong(FRM_FIELD_CARRIER_EMP_CATEGORY));
                        
                        //update by priska 2014-11-3
                        specialQuery.setStylist(getInt(FRM_FIELD_STYLIST));
                        specialQuery.addArrLocationId(this.getParamsStringValues(fieldNames[FRM_FIELD_LOCATION]));
                        specialQuery.addArrGradeId(this.getParamsStringValues(fieldNames[FRM_FIELD_GRADE]));        
                        
                        //update by priska 2014-10-30
                        specialQuery.setRadioendcontract(getInt(FRM_FIELD_RADIO_END_CONTRACT));
                       
                        specialQuery.addEndcontractfrom(getDate(FRM_FIELD_ENDCONTRACTFROM));
                        specialQuery.addEndcontractto(this.getDate(FRM_FIELD_ENDCONTRACTTO));
                        
                        // update by hendra
                        specialQuery.setResignedReasonId(getLong(FRM_FIELD_RESIGNED_REASON_ID));
                        specialQuery.addArrPayrollGroupId(this.getParamsStringValues(fieldNames[FRM_FIELD_PAYROLL_GROUP_ID]));   
                        
                        
                        //update by satrya 2014-08-01
                        //specialQuery.addSort(getString(FRM_FIELD_ORDER)) ;
		}catch(Exception e){
			System.out.println("Error on requestEntityObject : "+e.toString());
		}
	}
}
