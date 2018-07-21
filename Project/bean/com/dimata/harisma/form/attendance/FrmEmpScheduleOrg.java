/* 
 * Form Name  	:  FrmEmpScheduleOrg.java 
 * Created on 	:  [date] [time] AM/PM 
 * 
 * @author  	: karya 
 * @version  	: 01 
 */

/*******************************************************************
 * Class Description 	: [project description ... ] 
 * Imput Parameters 	: [input parameter ...] 
 * Output 		: [output ...] 
 *******************************************************************/

package com.dimata.harisma.form.attendance;

/* java package */ 
import java.io.*; 
import java.util.*; 
import javax.servlet.*;
import javax.servlet.http.*; 
/* qdep package */   
import com.dimata.qdep.form.*;
/* project package */
import com.dimata.harisma.entity.attendance.*;

public class FrmEmpScheduleOrg extends FRMHandler implements I_FRMInterface, I_FRMType 
{
	private EmpSchedule empSchedule;

	public static final String FRM_NAME_EMPSCHEDULE =  "FRM_NAME_EMPSCHEDULE" ;

	public static final int FRM_FIELD_EMP_SCHEDULE_ID =  0 ;
	public static final int FRM_FIELD_PERIOD_ID	=  1 ;
	public static final int FRM_FIELD_EMPLOYEE_ID	=  2 ;
	public static final int FRM_FIELD_D1	=  3 ;
	public static final int FRM_FIELD_D2	=  4 ;
	public static final int FRM_FIELD_D3	=  5 ;
	public static final int FRM_FIELD_D4	=  6 ;
	public static final int FRM_FIELD_D5	=  7 ;
	public static final int FRM_FIELD_D6	=  8 ;
	public static final int FRM_FIELD_D7	=  9 ;
	public static final int FRM_FIELD_D8	=  10 ;
	public static final int FRM_FIELD_D9	=  11 ;
	public static final int FRM_FIELD_D10	=  12 ;
	public static final int FRM_FIELD_D11	=  13 ;
	public static final int FRM_FIELD_D12	=  14 ;
	public static final int FRM_FIELD_D13	=  15 ;
	public static final int FRM_FIELD_D14	=  16 ;
	public static final int FRM_FIELD_D15	=  17 ;
	public static final int FRM_FIELD_D16	=  18 ;
	public static final int FRM_FIELD_D17	=  19 ;
	public static final int FRM_FIELD_D18	=  20 ;
	public static final int FRM_FIELD_D19	=  21 ;
	public static final int FRM_FIELD_D20	=  22 ;
	public static final int FRM_FIELD_D21	=  23 ;
	public static final int FRM_FIELD_D22	=  24 ;
	public static final int FRM_FIELD_D23	=  25 ;
	public static final int FRM_FIELD_D24	=  26 ;
	public static final int FRM_FIELD_D25	=  27 ;
	public static final int FRM_FIELD_D26	=  28 ;
	public static final int FRM_FIELD_D27	=  29 ;
	public static final int FRM_FIELD_D28	=  30 ;
	public static final int FRM_FIELD_D29	=  31 ;
	public static final int FRM_FIELD_D30	=  32 ;
	public static final int FRM_FIELD_D31	=  33 ;

	public static final int FRM_FIELD_D2ND1	=  34 ;
	public static final int FRM_FIELD_D2ND2	=  35 ;
	public static final int FRM_FIELD_D2ND3	=  36 ;
	public static final int FRM_FIELD_D2ND4	=  37 ;
	public static final int FRM_FIELD_D2ND5	=  38 ;
	public static final int FRM_FIELD_D2ND6	=  39 ;
	public static final int FRM_FIELD_D2ND7	=  40 ;
	public static final int FRM_FIELD_D2ND8 =  41 ;
	public static final int FRM_FIELD_D2ND9	=  42 ;
	public static final int FRM_FIELD_D2ND10 =  43 ;
	public static final int FRM_FIELD_D2ND11 =  44 ;
	public static final int FRM_FIELD_D2ND12 =  45 ;
	public static final int FRM_FIELD_D2ND13 =  46 ;
	public static final int FRM_FIELD_D2ND14 =  47 ;
	public static final int FRM_FIELD_D2ND15 =  48 ;
	public static final int FRM_FIELD_D2ND16 =  49 ;
	public static final int FRM_FIELD_D2ND17 =  50 ;
	public static final int FRM_FIELD_D2ND18 =  51 ;
	public static final int FRM_FIELD_D2ND19 =  52 ;
	public static final int FRM_FIELD_D2ND20 =  53 ;
	public static final int FRM_FIELD_D2ND21 =  54 ;
	public static final int FRM_FIELD_D2ND22 =  55 ;
	public static final int FRM_FIELD_D2ND23 =  56 ;
	public static final int FRM_FIELD_D2ND24 =  57 ;
	public static final int FRM_FIELD_D2ND25 =  58 ;
	public static final int FRM_FIELD_D2ND26 =  59 ;
	public static final int FRM_FIELD_D2ND27 =  60 ;
	public static final int FRM_FIELD_D2ND28 =  61 ;
	public static final int FRM_FIELD_D2ND29 =  62 ;
	public static final int FRM_FIELD_D2ND30 =  63 ;
	public static final int FRM_FIELD_D2ND31 =  64 ;
        
	public static final int FRM_FIELD_STATUS1 = 65;        
        public static final int FRM_FIELD_STATUS2 = 66;
        public static final int FRM_FIELD_STATUS3 = 67;
        public static final int FRM_FIELD_STATUS4 = 68;
        public static final int FRM_FIELD_STATUS5 = 69;
        public static final int FRM_FIELD_STATUS6 = 70;
        public static final int FRM_FIELD_STATUS7 = 71;
        public static final int FRM_FIELD_STATUS8 = 72;
        public static final int FRM_FIELD_STATUS9 = 73;
        public static final int FRM_FIELD_STATUS10 = 74;
	public static final int FRM_FIELD_STATUS11 = 75;        
        public static final int FRM_FIELD_STATUS12 = 76;
        public static final int FRM_FIELD_STATUS13 = 77;
        public static final int FRM_FIELD_STATUS14 = 78;
        public static final int FRM_FIELD_STATUS15 = 79;
        public static final int FRM_FIELD_STATUS16 = 80;
        public static final int FRM_FIELD_STATUS17 = 81;
        public static final int FRM_FIELD_STATUS18 = 82;
        public static final int FRM_FIELD_STATUS19 = 83;
        public static final int FRM_FIELD_STATUS20 = 84;
	public static final int FRM_FIELD_STATUS21 = 85;        
        public static final int FRM_FIELD_STATUS22 = 86;
        public static final int FRM_FIELD_STATUS23 = 87;
        public static final int FRM_FIELD_STATUS24 = 88;
        public static final int FRM_FIELD_STATUS25 = 89;
        public static final int FRM_FIELD_STATUS26 = 90;
        public static final int FRM_FIELD_STATUS27 = 91;
        public static final int FRM_FIELD_STATUS28 = 92;
        public static final int FRM_FIELD_STATUS29 = 93;
        public static final int FRM_FIELD_STATUS30 = 94;
	public static final int FRM_FIELD_STATUS31 = 95;        
        public static final int FRM_FIELD_STATUS2ND1 = 96;
        public static final int FRM_FIELD_STATUS2ND2 = 97;
        public static final int FRM_FIELD_STATUS2ND3 = 98;
        public static final int FRM_FIELD_STATUS2ND4 = 99;
        public static final int FRM_FIELD_STATUS2ND5 = 100;
        public static final int FRM_FIELD_STATUS2ND6 = 101;
        public static final int FRM_FIELD_STATUS2ND7 = 102;
        public static final int FRM_FIELD_STATUS2ND8 = 103;
        public static final int FRM_FIELD_STATUS2ND9 = 104;
	public static final int FRM_FIELD_STATUS2ND10 = 105;        
        public static final int FRM_FIELD_STATUS2ND11 = 106;
        public static final int FRM_FIELD_STATUS2ND12 = 107;
        public static final int FRM_FIELD_STATUS2ND13 = 108;
        public static final int FRM_FIELD_STATUS2ND14 = 109;
        public static final int FRM_FIELD_STATUS2ND15 = 110;
        public static final int FRM_FIELD_STATUS2ND16 = 111;
        public static final int FRM_FIELD_STATUS2ND17 = 112;
        public static final int FRM_FIELD_STATUS2ND18 = 113;
        public static final int FRM_FIELD_STATUS2ND19 = 114;
	public static final int FRM_FIELD_STATUS2ND20 = 115;        
        public static final int FRM_FIELD_STATUS2ND21 = 116;
        public static final int FRM_FIELD_STATUS2ND22 = 117;
        public static final int FRM_FIELD_STATUS2ND23 = 118;
        public static final int FRM_FIELD_STATUS2ND24 = 119;
        public static final int FRM_FIELD_STATUS2ND25 = 120;
        public static final int FRM_FIELD_STATUS2ND26 = 121;
        public static final int FRM_FIELD_STATUS2ND27 = 122;
        public static final int FRM_FIELD_STATUS2ND28 = 123;
        public static final int FRM_FIELD_STATUS2ND29 = 124;
        public static final int FRM_FIELD_STATUS2ND30 = 125;        
        public static final int FRM_FIELD_STATUS2ND31 = 126;
        
	public static final int FRM_FIELD_REASON1 = 127;                
	public static final int FRM_FIELD_REASON2 = 128;                
	public static final int FRM_FIELD_REASON3 = 129;                
	public static final int FRM_FIELD_REASON4 = 130;                
	public static final int FRM_FIELD_REASON5 = 131;                
	public static final int FRM_FIELD_REASON6 = 132;                
	public static final int FRM_FIELD_REASON7 = 133;                
	public static final int FRM_FIELD_REASON8 = 134;                
	public static final int FRM_FIELD_REASON9 = 135;                
	public static final int FRM_FIELD_REASON10 = 136;                
	public static final int FRM_FIELD_REASON11 = 137;                
	public static final int FRM_FIELD_REASON12 = 138;                        
	public static final int FRM_FIELD_REASON13 = 139;                
	public static final int FRM_FIELD_REASON14 = 140;                
	public static final int FRM_FIELD_REASON15 = 141;                
	public static final int FRM_FIELD_REASON16 = 142;                
	public static final int FRM_FIELD_REASON17 = 143;                
	public static final int FRM_FIELD_REASON18 = 144;                
	public static final int FRM_FIELD_REASON19 = 145;                
	public static final int FRM_FIELD_REASON20 = 146;                
	public static final int FRM_FIELD_REASON21 = 147;                
	public static final int FRM_FIELD_REASON22 = 148;                
	public static final int FRM_FIELD_REASON23 = 149;                
	public static final int FRM_FIELD_REASON24 = 150;                
	public static final int FRM_FIELD_REASON25 = 151;                
	public static final int FRM_FIELD_REASON26 = 152;                
	public static final int FRM_FIELD_REASON27 = 153;                
	public static final int FRM_FIELD_REASON28 = 154;                
	public static final int FRM_FIELD_REASON29 = 155;                
	public static final int FRM_FIELD_REASON30 = 156;                
	public static final int FRM_FIELD_REASON31 = 157;                
	public static final int FRM_FIELD_REASON2ND1 = 158;                
	public static final int FRM_FIELD_REASON2ND2 = 159;                
	public static final int FRM_FIELD_REASON2ND3 = 160;                
	public static final int FRM_FIELD_REASON2ND4 = 161;                
	public static final int FRM_FIELD_REASON2ND5 = 162;                
	public static final int FRM_FIELD_REASON2ND6 = 163;                
	public static final int FRM_FIELD_REASON2ND7 = 164;                
	public static final int FRM_FIELD_REASON2ND8 = 165;                
	public static final int FRM_FIELD_REASON2ND9 = 166;                
	public static final int FRM_FIELD_REASON2ND10 = 167;                
	public static final int FRM_FIELD_REASON2ND11 = 168;                
	public static final int FRM_FIELD_REASON2ND12 = 169;                
	public static final int FRM_FIELD_REASON2ND13 = 170;                
	public static final int FRM_FIELD_REASON2ND14 = 171;                
	public static final int FRM_FIELD_REASON2ND15 = 172;                
	public static final int FRM_FIELD_REASON2ND16 = 173;                
	public static final int FRM_FIELD_REASON2ND17 = 174;                
	public static final int FRM_FIELD_REASON2ND18 = 175;                
	public static final int FRM_FIELD_REASON2ND19 = 176;                
	public static final int FRM_FIELD_REASON2ND20 = 177;                
	public static final int FRM_FIELD_REASON2ND21 = 178;                
        public static final int FRM_FIELD_REASON2ND22 = 179;                
	public static final int FRM_FIELD_REASON2ND23 = 180;                
	public static final int FRM_FIELD_REASON2ND24 = 181;                
	public static final int FRM_FIELD_REASON2ND25 = 182;                
	public static final int FRM_FIELD_REASON2ND26 = 183;                
	public static final int FRM_FIELD_REASON2ND27 = 184;                
	public static final int FRM_FIELD_REASON2ND28 = 185;                
	public static final int FRM_FIELD_REASON2ND29 = 186;                
	public static final int FRM_FIELD_REASON2ND30 = 187;                
	public static final int FRM_FIELD_REASON2ND31 = 188;                        
        
        public static final int FRM_FIELD_NOTE1 = 189;   
        public static final int FRM_FIELD_NOTE2 = 190;   
        public static final int FRM_FIELD_NOTE3 = 191;   
        public static final int FRM_FIELD_NOTE4 = 192;   
        public static final int FRM_FIELD_NOTE5 = 193;   
        public static final int FRM_FIELD_NOTE6 = 194;   
        public static final int FRM_FIELD_NOTE7 = 195;   
        public static final int FRM_FIELD_NOTE8 = 196;   
        public static final int FRM_FIELD_NOTE9 = 197;   
        public static final int FRM_FIELD_NOTE10 = 198;   
        public static final int FRM_FIELD_NOTE11 = 199;   
        public static final int FRM_FIELD_NOTE12 = 200;   
        public static final int FRM_FIELD_NOTE13 = 201;   
        public static final int FRM_FIELD_NOTE14 = 202;   
        public static final int FRM_FIELD_NOTE15 = 203;   
        public static final int FRM_FIELD_NOTE16 = 204;   
        public static final int FRM_FIELD_NOTE17 = 205;   
        public static final int FRM_FIELD_NOTE18 = 206;   
        public static final int FRM_FIELD_NOTE19 = 207;   
        public static final int FRM_FIELD_NOTE20 = 208;   
        public static final int FRM_FIELD_NOTE21 = 209;   
        public static final int FRM_FIELD_NOTE22 = 210;   
        public static final int FRM_FIELD_NOTE23 = 211;   
        public static final int FRM_FIELD_NOTE24 = 212;   
        public static final int FRM_FIELD_NOTE25 = 213;   
        public static final int FRM_FIELD_NOTE26 = 214;   
        public static final int FRM_FIELD_NOTE27 = 215;   
        public static final int FRM_FIELD_NOTE28 = 216;   
        public static final int FRM_FIELD_NOTE29 = 217;   
        public static final int FRM_FIELD_NOTE30 = 218;   
        public static final int FRM_FIELD_NOTE31 = 219;   
        public static final int FRM_FIELD_NOTE2ND1 = 220;   
        public static final int FRM_FIELD_NOTE2ND2 = 221;   
        public static final int FRM_FIELD_NOTE2ND3 = 222;   
        public static final int FRM_FIELD_NOTE2ND4 = 223;   
        public static final int FRM_FIELD_NOTE2ND5 = 224;   
        public static final int FRM_FIELD_NOTE2ND6 = 225;   
        public static final int FRM_FIELD_NOTE2ND7= 226;   
        public static final int FRM_FIELD_NOTE2ND8 = 227;   
        public static final int FRM_FIELD_NOTE2ND9 = 228;   
        public static final int FRM_FIELD_NOTE2ND10 = 229;   
        public static final int FRM_FIELD_NOTE2ND11 = 230;   
        public static final int FRM_FIELD_NOTE2ND12 = 231;   
        public static final int FRM_FIELD_NOTE2ND13 = 232;   
        public static final int FRM_FIELD_NOTE2ND14 = 233;   
        public static final int FRM_FIELD_NOTE2ND15 = 234;   
        public static final int FRM_FIELD_NOTE2ND16 = 235;   
        public static final int FRM_FIELD_NOTE2ND17 = 236;   
        public static final int FRM_FIELD_NOTE2ND18 = 237;   
        public static final int FRM_FIELD_NOTE2ND19 = 238;   
        public static final int FRM_FIELD_NOTE2ND20 = 239;   
        public static final int FRM_FIELD_NOTE2ND21 = 240;   
        public static final int FRM_FIELD_NOTE2ND22 = 241;   
        public static final int FRM_FIELD_NOTE2ND23 = 242;   
        public static final int FRM_FIELD_NOTE2ND24 = 243;   
        public static final int FRM_FIELD_NOTE2ND25 = 244;   
        public static final int FRM_FIELD_NOTE2ND26 = 245;   
        public static final int FRM_FIELD_NOTE2ND27 = 246;   
        public static final int FRM_FIELD_NOTE2ND28 = 247;   
        public static final int FRM_FIELD_NOTE2ND29 = 248;   
        public static final int FRM_FIELD_NOTE2ND30 = 249;   
        public static final int FRM_FIELD_NOTE2ND31 = 250;          
        
        public static String[] fieldNames = {
		"FRM_FIELD_EMP_SCHEDULE_ID",  
                "FRM_FIELD_PERIOD_ID",
		"FRM_FIELD_EMPLOYEE_ID",  
                "FRM_FIELD_D1",
		"FRM_FIELD_D2",  
                "FRM_FIELD_D3",
		"FRM_FIELD_D4",  
                "FRM_FIELD_D5",
		"FRM_FIELD_D6",  
                "FRM_FIELD_D7",
		"FRM_FIELD_D8",  
                "FRM_FIELD_D9",
		"FRM_FIELD_D10",  
                "FRM_FIELD_D11",
		"FRM_FIELD_D12",  
                "FRM_FIELD_D13",
		"FRM_FIELD_D14",  
                "FRM_FIELD_D15",
		"FRM_FIELD_D16",  
                "FRM_FIELD_D17",
		"FRM_FIELD_D18",  
                "FRM_FIELD_D19",
		"FRM_FIELD_D20",  
                "FRM_FIELD_D21",
		"FRM_FIELD_D22",  
                "FRM_FIELD_D23",
		"FRM_FIELD_D24",  
                "FRM_FIELD_D25",
		"FRM_FIELD_D26",  
                "FRM_FIELD_D27",
		"FRM_FIELD_D28",  
                "FRM_FIELD_D29",
		"FRM_FIELD_D30",  
                "FRM_FIELD_D31",
                "FRM_FIELD_D2ND1",
		"FRM_FIELD_D2ND2",  
                "FRM_FIELD_D2ND3",
		"FRM_FIELD_D2ND4",  
                "FRM_FIELD_D2ND5",
		"FRM_FIELD_D2ND6",  
                "FRM_FIELD_D2ND7",
		"FRM_FIELD_D2ND8",  
                "FRM_FIELD_D2ND9",
		"FRM_FIELD_D2ND10",  
                "FRM_FIELD_D2ND11",
		"FRM_FIELD_D2ND12", 
                "FRM_FIELD_D2ND13",
		"FRM_FIELD_D2ND14", 
                "FRM_FIELD_D2ND15",
		"FRM_FIELD_D2ND16", 
                "FRM_FIELD_D2ND17",
		"FRM_FIELD_D2ND18", 
                "FRM_FIELD_D2ND19",
		"FRM_FIELD_D2ND20", 
                "FRM_FIELD_D2ND21",
		"FRM_FIELD_D2ND22",  
                "FRM_FIELD_D2ND23",
		"FRM_FIELD_D2ND24", 
                "FRM_FIELD_D2ND25",
		"FRM_FIELD_D2ND26", 
                "FRM_FIELD_D2ND27",
		"FRM_FIELD_D2ND28",  
                "FRM_FIELD_D2ND29",
		"FRM_FIELD_D2ND30",  
                "FRM_FIELD_D2ND31",
		"FRM_FIELD_STATUS1",
		"FRM_FIELD_STATUS2",
		"FRM_FIELD_STATUS3",
		"FRM_FIELD_STATUS4",
		"FRM_FIELD_STATUS5",
		"FRM_FIELD_STATUS6",
		"FRM_FIELD_STATUS7",
		"FRM_FIELD_STATUS8",
		"FRM_FIELD_STATUS9",
		"FRM_FIELD_STATUS10",
		"FRM_FIELD_STATUS11",
		"FRM_FIELD_STATUS12",
		"FRM_FIELD_STATUS13",
		"FRM_FIELD_STATUS14",
		"FRM_FIELD_STATUS15",
		"FRM_FIELD_STATUS16",
		"FRM_FIELD_STATUS17",
		"FRM_FIELD_STATUS18",
		"FRM_FIELD_STATUS19",
		"FRM_FIELD_STATUS20",                
		"FRM_FIELD_STATUS21",
		"FRM_FIELD_STATUS22",
		"FRM_FIELD_STATUS23",
		"FRM_FIELD_STATUS24",
		"FRM_FIELD_STATUS25",
		"FRM_FIELD_STATUS26",
		"FRM_FIELD_STATUS27",
		"FRM_FIELD_STATUS28",
		"FRM_FIELD_STATUS29",
		"FRM_FIELD_STATUS30",                
                "FRM_FIELD_STATUS31",                                
                "FRM_FIELD_STATUS2ND1",                                
                "FRM_FIELD_STATUS2ND2",                               
                "FRM_FIELD_STATUS2ND3",                               
                "FRM_FIELD_STATUS2ND4",                               
                "FRM_FIELD_STATUS2ND5",                               
                "FRM_FIELD_STATUS2ND6",                               
                "FRM_FIELD_STATUS2ND7",                               
                "FRM_FIELD_STATUS2ND8",                               
                "FRM_FIELD_STATUS2ND9",                               
                "FRM_FIELD_STATUS2ND10",                               
                "FRM_FIELD_STATUS2ND11",                                
                "FRM_FIELD_STATUS2ND12",                               
                "FRM_FIELD_STATUS2ND13",                               
                "FRM_FIELD_STATUS2ND14",                               
                "FRM_FIELD_STATUS2ND15",                               
                "FRM_FIELD_STATUS2ND16",                               
                "FRM_FIELD_STATUS2ND17",                               
                "FRM_FIELD_STATUS2ND18",                               
                "FRM_FIELD_STATUS2ND19",                               
                "FRM_FIELD_STATUS2ND20",                               
                "FRM_FIELD_STATUS2ND21",                                
                "FRM_FIELD_STATUS2ND22",                               
                "FRM_FIELD_STATUS2ND23",                               
                "FRM_FIELD_STATUS2ND24",                               
                "FRM_FIELD_STATUS2ND25",                               
                "FRM_FIELD_STATUS2ND26",                               
                "FRM_FIELD_STATUS2ND27",                               
                "FRM_FIELD_STATUS2ND28",                               
                "FRM_FIELD_STATUS2ND29",                               
                "FRM_FIELD_STATUS2ND30",                               
                "FRM_FIELD_STATUS2ND31",                                               
                "FRM_FIELD_REASON1",                               
                "FRM_FIELD_REASON2",                               
                "FRM_FIELD_REASON3",                               
                "FRM_FIELD_REASON4",                               
                "FRM_FIELD_REASON5",                               
                "FRM_FIELD_REASON6",                               
                "FRM_FIELD_REASON7",                               
                "FRM_FIELD_REASON8",                               
                "FRM_FIELD_REASON9",                               
                "FRM_FIELD_REASON10",                               
                "FRM_FIELD_REASON11",                               
                "FRM_FIELD_REASON12",                               
                "FRM_FIELD_REASON13",                               
                "FRM_FIELD_REASON14",                               
                "FRM_FIELD_REASON15",                               
                "FRM_FIELD_REASON16",                               
                "FRM_FIELD_REASON17",                               
                "FRM_FIELD_REASON18",                               
                "FRM_FIELD_REASON19",                               
                "FRM_FIELD_REASON20",                               
                "FRM_FIELD_REASON21",                               
                "FRM_FIELD_REASON22",                               
                "FRM_FIELD_REASON23",                               
                "FRM_FIELD_REASON24",                               
                "FRM_FIELD_REASON25",                               
                "FRM_FIELD_REASON26",                               
                "FRM_FIELD_REASON27",                               
                "FRM_FIELD_REASON28",                               
                "FRM_FIELD_REASON29",                               
                "FRM_FIELD_REASON30",                               
                "FRM_FIELD_REASON31",                                               
                "FRM_FIELD_REASON2ND1",                               
                "FRM_FIELD_REASON2ND2",                               
                "FRM_FIELD_REASON2ND3",                               
                "FRM_FIELD_REASON2ND4",                               
                "FRM_FIELD_REASON2ND5",                               
                "FRM_FIELD_REASON2ND6",                               
                "FRM_FIELD_REASON2ND7",                               
                "FRM_FIELD_REASON2ND8",                               
                "FRM_FIELD_REASON2ND9",                               
                "FRM_FIELD_REASON2ND10",                               
                "FRM_FIELD_REASON2ND11",                               
                "FRM_FIELD_REASON2ND12",                               
                "FRM_FIELD_REASON2ND13",                               
                "FRM_FIELD_REASON2ND14",                               
                "FRM_FIELD_REASON2ND15",                               
                "FRM_FIELD_REASON2ND16",                               
                "FRM_FIELD_REASON2ND17",                               
                "FRM_FIELD_REASON2ND18",                               
                "FRM_FIELD_REASON2ND19",                               
                "FRM_FIELD_REASON2ND20",                               
                "FRM_FIELD_REASON2ND21",                               
                "FRM_FIELD_REASON2ND22",                               
                "FRM_FIELD_REASON2ND23",                               
                "FRM_FIELD_REASON2ND24",                               
                "FRM_FIELD_REASON2ND25",                               
                "FRM_FIELD_REASON2ND26",                               
                "FRM_FIELD_REASON2ND27",                               
                "FRM_FIELD_REASON2ND28",                               
                "FRM_FIELD_REASON2ND29",                               
                "FRM_FIELD_REASON2ND30",                               
                "FRM_FIELD_REASON2ND31",                               
                "FRM_FIELD_NOTE1",                               
                "FRM_FIELD_NOTE2",                               
                "FRM_FIELD_NOTE3",                               
                "FRM_FIELD_NOTE4",                               
                "FRM_FIELD_NOTE5",                               
                "FRM_FIELD_NOTE6",                               
                "FRM_FIELD_NOTE7",                               
                "FRM_FIELD_NOTE8",                               
                "FRM_FIELD_NOTE9",                               
                "FRM_FIELD_NOTE10",                               
                "FRM_FIELD_NOTE11",                               
                "FRM_FIELD_NOTE12",                               
                "FRM_FIELD_NOTE13",                               
                "FRM_FIELD_NOTE14",                               
                "FRM_FIELD_NOTE15",                               
                "FRM_FIELD_NOTE16",                               
                "FRM_FIELD_NOTE17",                               
                "FRM_FIELD_NOTE18",                               
                "FRM_FIELD_NOTE19",                               
                "FRM_FIELD_NOTE20",                               
                "FRM_FIELD_NOTE21",                               
                "FRM_FIELD_NOTE22",                               
                "FRM_FIELD_NOTE23",                               
                "FRM_FIELD_NOTE24",                               
                "FRM_FIELD_NOTE25",                               
                "FRM_FIELD_NOTE26",                               
                "FRM_FIELD_NOTE27",                               
                "FRM_FIELD_NOTE28",                               
                "FRM_FIELD_NOTE29",                               
                "FRM_FIELD_NOTE30",                               
                "FRM_FIELD_NOTE31",                               
                "FRM_FIELD_NOTE2ND1",                               
                "FRM_FIELD_NOTE2ND2",                               
                "FRM_FIELD_NOTE2ND3",                               
                "FRM_FIELD_NOTE2ND4",                               
                "FRM_FIELD_NOTE2ND5",                               
                "FRM_FIELD_NOTE2ND6",                               
                "FRM_FIELD_NOTE2ND7",                               
                "FRM_FIELD_NOTE2ND8",                               
                "FRM_FIELD_NOTE2ND9",                               
                "FRM_FIELD_NOTE2ND10",                               
                "FRM_FIELD_NOTE2ND11",                               
                "FRM_FIELD_NOTE2ND12",                               
                "FRM_FIELD_NOTE2ND13",                               
                "FRM_FIELD_NOTE2ND14",                               
                "FRM_FIELD_NOTE2ND15",                               
                "FRM_FIELD_NOTE2ND16",                               
                "FRM_FIELD_NOTE2ND17",                               
                "FRM_FIELD_NOTE2ND18",                               
                "FRM_FIELD_NOTE2ND19",                               
                "FRM_FIELD_NOTE2ND20",                               
                "FRM_FIELD_NOTE2ND21",                               
                "FRM_FIELD_NOTE2ND22",                               
                "FRM_FIELD_NOTE2ND23",                               
                "FRM_FIELD_NOTE2ND24",                               
                "FRM_FIELD_NOTE2ND25",                               
                "FRM_FIELD_NOTE2ND26",                               
                "FRM_FIELD_NOTE2ND27",                               
                "FRM_FIELD_NOTE2ND28",                               
                "FRM_FIELD_NOTE2ND29",                               
                "FRM_FIELD_NOTE2ND30",                               
                "FRM_FIELD_NOTE2ND31"                                               
	} ;

	public static int[] fieldTypes = {
		TYPE_LONG,  
                TYPE_LONG + ENTRY_REQUIRED,
		TYPE_LONG + ENTRY_REQUIRED,  
                TYPE_LONG,
		TYPE_LONG,  
                TYPE_LONG,
		TYPE_LONG,  
                TYPE_LONG,
		TYPE_LONG,  
                TYPE_LONG,
		TYPE_LONG,  
                TYPE_LONG,
		TYPE_LONG,  
                TYPE_LONG,
		TYPE_LONG,  
                TYPE_LONG,
		TYPE_LONG,  
                TYPE_LONG,
		TYPE_LONG,  
                TYPE_LONG,
		TYPE_LONG,  
                TYPE_LONG,
		TYPE_LONG, 
                TYPE_LONG,
		TYPE_LONG, 
                TYPE_LONG,
		TYPE_LONG, 
                TYPE_LONG,
		TYPE_LONG,  
                TYPE_LONG,
		TYPE_LONG, 
                TYPE_LONG,
		TYPE_LONG, 
                TYPE_LONG,
                TYPE_LONG,
		TYPE_LONG,  
                TYPE_LONG,
		TYPE_LONG,  
                TYPE_LONG,
		TYPE_LONG,  
                TYPE_LONG,
		TYPE_LONG,  
                TYPE_LONG,
		TYPE_LONG, 
                TYPE_LONG,
		TYPE_LONG,  
                TYPE_LONG,
		TYPE_LONG,  
                TYPE_LONG,
		TYPE_LONG, 
                TYPE_LONG,
		TYPE_LONG,  
                TYPE_LONG,
		TYPE_LONG,  
                TYPE_LONG,
		TYPE_LONG, 
                TYPE_LONG,
		TYPE_LONG, 
                TYPE_LONG,
		TYPE_LONG, 
                TYPE_LONG,
		TYPE_LONG,  
                TYPE_LONG,
		TYPE_LONG, 
                TYPE_LONG,
                TYPE_INT,
                TYPE_INT,
                TYPE_INT,
                TYPE_INT,
                TYPE_INT,
                TYPE_INT,
                TYPE_INT,
                TYPE_INT,
                TYPE_INT,
                TYPE_INT,
                TYPE_INT,
                TYPE_INT,
                TYPE_INT,
                TYPE_INT,
                TYPE_INT,
                TYPE_INT,
                TYPE_INT,
                TYPE_INT,
                TYPE_INT,
                TYPE_INT,
                TYPE_INT,
                TYPE_INT,
                TYPE_INT,
                TYPE_INT,
                TYPE_INT,
                TYPE_INT,
                TYPE_INT,
                TYPE_INT,
                TYPE_INT,
                TYPE_INT,
                TYPE_INT,
                TYPE_INT,
                TYPE_INT,
                TYPE_INT,
                TYPE_INT,
                TYPE_INT,
                TYPE_INT,
                TYPE_INT,
                TYPE_INT,
                TYPE_INT,
                TYPE_INT,
                TYPE_INT,
                TYPE_INT,
                TYPE_INT,
                TYPE_INT,
                TYPE_INT,
                TYPE_INT,
                TYPE_INT,
                TYPE_INT,
                TYPE_INT,
                TYPE_INT,
                TYPE_INT,
                TYPE_INT,
                TYPE_INT,
                TYPE_INT,
                TYPE_INT,
                TYPE_INT,
                TYPE_INT,
                TYPE_INT,
                TYPE_INT,
                TYPE_INT,
                TYPE_INT,
                
                TYPE_INT,
                TYPE_INT,
                TYPE_INT,
                TYPE_INT,
                TYPE_INT,
                TYPE_INT,
                TYPE_INT,
                TYPE_INT,
                TYPE_INT,
                TYPE_INT,
                TYPE_INT,
                TYPE_INT,
                TYPE_INT,
                TYPE_INT,
                TYPE_INT,
                TYPE_INT,
                TYPE_INT,
                TYPE_INT,
                TYPE_INT,
                TYPE_INT,
                TYPE_INT,
                TYPE_INT,
                TYPE_INT,
                TYPE_INT,
                TYPE_INT,
                TYPE_INT,
                TYPE_INT,
                TYPE_INT,
                TYPE_INT,
                TYPE_INT,
                TYPE_INT,
                TYPE_INT,
                TYPE_INT,
                TYPE_INT,
                TYPE_INT,
                TYPE_INT,
                TYPE_INT,
                TYPE_INT,
                TYPE_INT,
                TYPE_INT,
                TYPE_INT,
                TYPE_INT,
                TYPE_INT,
                TYPE_INT,
                TYPE_INT,
                TYPE_INT,
                TYPE_INT,
                TYPE_INT,
                TYPE_INT,
                TYPE_INT,
                TYPE_INT,
                TYPE_INT,
                TYPE_INT,
                TYPE_INT,
                TYPE_INT,
                TYPE_INT,
                TYPE_INT,
                TYPE_INT,
                TYPE_INT,
                TYPE_INT,
                TYPE_INT,
                TYPE_INT,
                
                TYPE_STRING,
                TYPE_STRING,
                TYPE_STRING,
                TYPE_STRING,
                TYPE_STRING,
                TYPE_STRING,
                TYPE_STRING,
                TYPE_STRING,
                TYPE_STRING,
                TYPE_STRING,
                TYPE_STRING,
                TYPE_STRING,
                TYPE_STRING,
                TYPE_STRING,
                TYPE_STRING,
                TYPE_STRING,
                TYPE_STRING,
                TYPE_STRING,
                TYPE_STRING,
                TYPE_STRING,
                TYPE_STRING,
                TYPE_STRING,
                TYPE_STRING,
                TYPE_STRING,
                TYPE_STRING,
                TYPE_STRING,
                TYPE_STRING,
                TYPE_STRING,
                TYPE_STRING,
                TYPE_STRING,
                TYPE_STRING,
                TYPE_STRING,
                TYPE_STRING,
                TYPE_STRING,
                TYPE_STRING,
                TYPE_STRING,
                TYPE_STRING,
                TYPE_STRING,
                TYPE_STRING,
                TYPE_STRING,
                TYPE_STRING,
                TYPE_STRING,
                TYPE_STRING,
                TYPE_STRING,
                TYPE_STRING,
                TYPE_STRING,
                TYPE_STRING,
                TYPE_STRING,
                TYPE_STRING,
                TYPE_STRING,
                TYPE_STRING,
                TYPE_STRING,
                TYPE_STRING,
                TYPE_STRING,
                TYPE_STRING,
                TYPE_STRING,
                TYPE_STRING,
                TYPE_STRING,
                TYPE_STRING,
                TYPE_STRING,
                TYPE_STRING,
                TYPE_STRING                
	} ;

	public FrmEmpScheduleOrg(){
	}
	public FrmEmpScheduleOrg(EmpSchedule empSchedule){
		this.empSchedule = empSchedule;
	}

	public FrmEmpScheduleOrg(HttpServletRequest request, EmpSchedule empSchedule){
		super(new FrmEmpScheduleOrg(empSchedule), request);
		this.empSchedule = empSchedule;
	}

	public String getFormName() { return FRM_NAME_EMPSCHEDULE; } 

	public int[] getFieldTypes() { return fieldTypes; }

	public String[] getFieldNames() { return fieldNames; } 

	public int getFieldSize() { return fieldNames.length; } 

	public EmpSchedule getEntityObject(){ return empSchedule; }
        public static long parseThisLong (String s){
            long scheduleId=0;
            try{
                scheduleId = Long.parseLong(s);
            }catch(Exception exc){
                scheduleId=0;
            }
            return scheduleId;
        }
	public void requestEntityObject(EmpSchedule empSchedule) {
		try{
			this.requestParam();                        
                        
			empSchedule.setPeriodId(getLong(FRM_FIELD_PERIOD_ID));
			empSchedule.setEmployeeId(getLong(FRM_FIELD_EMPLOYEE_ID));
                        
                        // edited by edhy
			/*empSchedule.setD1(Long.parseLong(getString(FRM_FIELD_D1).length()<18 ? "0" : getString(FRM_FIELD_D1).substring(0,18)));
                        empSchedule.setD2(Long.parseLong(getString(FRM_FIELD_D2).length()<18 ? "0" : getString(FRM_FIELD_D2).substring(0,18)));
                        empSchedule.setD3(Long.parseLong(getString(FRM_FIELD_D3).length()<18 ? "0" : getString(FRM_FIELD_D3).substring(0,18)));
                        empSchedule.setD4(Long.parseLong(getString(FRM_FIELD_D4).length()<18 ? "0" : getString(FRM_FIELD_D4).substring(0,18)));
                        empSchedule.setD5(Long.parseLong(getString(FRM_FIELD_D5).length()<18 ? "0" : getString(FRM_FIELD_D5).substring(0,18)));
                        empSchedule.setD6(Long.parseLong(getString(FRM_FIELD_D6).length()<18 ? "0" : getString(FRM_FIELD_D6).substring(0,18)));
                        empSchedule.setD7(Long.parseLong(getString(FRM_FIELD_D7).length()<18 ? "0" : getString(FRM_FIELD_D7).substring(0,18)));
                        empSchedule.setD8(Long.parseLong(getString(FRM_FIELD_D8).length()<18 ? "0" : getString(FRM_FIELD_D8).substring(0,18)));
                        empSchedule.setD9(Long.parseLong(getString(FRM_FIELD_D9).length()<18 ? "0" : getString(FRM_FIELD_D9).substring(0,18)));
                        empSchedule.setD10(Long.parseLong(getString(FRM_FIELD_D10).length()<18 ? "0" : getString(FRM_FIELD_D10).substring(0,18)));
                        empSchedule.setD11(Long.parseLong(getString(FRM_FIELD_D11).length()<18 ? "0" : getString(FRM_FIELD_D11).substring(0,18)));
                        empSchedule.setD12(Long.parseLong(getString(FRM_FIELD_D12).length()<18 ? "0" : getString(FRM_FIELD_D12).substring(0,18)));
                        empSchedule.setD13(Long.parseLong(getString(FRM_FIELD_D13).length()<18 ? "0" : getString(FRM_FIELD_D13).substring(0,18)));
                        empSchedule.setD14(Long.parseLong(getString(FRM_FIELD_D14).length()<18 ? "0" : getString(FRM_FIELD_D14).substring(0,18)));
                        empSchedule.setD15(Long.parseLong(getString(FRM_FIELD_D15).length()<18 ? "0" : getString(FRM_FIELD_D15).substring(0,18)));
                        empSchedule.setD16(Long.parseLong(getString(FRM_FIELD_D16).length()<18 ? "0" : getString(FRM_FIELD_D16).substring(0,18)));
                        empSchedule.setD17(Long.parseLong(getString(FRM_FIELD_D17).length()<18 ? "0" : getString(FRM_FIELD_D17).substring(0,18)));
                        empSchedule.setD18(Long.parseLong(getString(FRM_FIELD_D18).length()<18 ? "0" : getString(FRM_FIELD_D18).substring(0,18)));
                        empSchedule.setD19(Long.parseLong(getString(FRM_FIELD_D19).length()<18 ? "0" : getString(FRM_FIELD_D19).substring(0,18)));
                        empSchedule.setD20(Long.parseLong(getString(FRM_FIELD_D20).length()<18 ? "0" : getString(FRM_FIELD_D20).substring(0,18)));
                        empSchedule.setD21(Long.parseLong(getString(FRM_FIELD_D21).length()<18 ? "0" : getString(FRM_FIELD_D21).substring(0,18)));
                        empSchedule.setD22(Long.parseLong(getString(FRM_FIELD_D22).length()<18 ? "0" : getString(FRM_FIELD_D22).substring(0,18)));
                        empSchedule.setD23(Long.parseLong(getString(FRM_FIELD_D23).length()<18 ? "0" : getString(FRM_FIELD_D23).substring(0,18)));
                        empSchedule.setD24(Long.parseLong(getString(FRM_FIELD_D24).length()<18 ? "0" : getString(FRM_FIELD_D24).substring(0,18)));
                        empSchedule.setD25(Long.parseLong(getString(FRM_FIELD_D25).length()<18 ? "0" : getString(FRM_FIELD_D25).substring(0,18)));
                        empSchedule.setD26(Long.parseLong(getString(FRM_FIELD_D26).length()<18 ? "0" : getString(FRM_FIELD_D26).substring(0,18)));
                        empSchedule.setD27(Long.parseLong(getString(FRM_FIELD_D27).length()<18 ? "0" : getString(FRM_FIELD_D27).substring(0,18)));
                        empSchedule.setD28(Long.parseLong(getString(FRM_FIELD_D28).length()<18 ? "0" : getString(FRM_FIELD_D28).substring(0,18)));
                        empSchedule.setD29(Long.parseLong(getString(FRM_FIELD_D29).length()<18 ? "0" : getString(FRM_FIELD_D29).substring(0,18)));
                        empSchedule.setD30(Long.parseLong(getString(FRM_FIELD_D30).length()<18 ? "0" : getString(FRM_FIELD_D30).substring(0,18)));
                        empSchedule.setD31(Long.parseLong(getString(FRM_FIELD_D31).length()<18 ? "0" : getString(FRM_FIELD_D31).substring(0,18)));
                       */
                        empSchedule.setD1(parseThisLong(getString(FRM_FIELD_D1)));//Long.parseLong(getString(FRM_FIELD_D1).length()<18 ? "0" : getString(FRM_FIELD_D1).substring(0,18)));
                        empSchedule.setD2(Long.parseLong(getString(FRM_FIELD_D2)));//.length()<18 ? "0" : getString(FRM_FIELD_D2).substring(0,18)));
                        empSchedule.setD3(Long.parseLong(getString(FRM_FIELD_D3)));//.length()<18 ? "0" : getString(FRM_FIELD_D3).substring(0,18)));
                        empSchedule.setD4(Long.parseLong(getString(FRM_FIELD_D4)));//.length()<18 ? "0" : getString(FRM_FIELD_D4).substring(0,18)));
                        empSchedule.setD5(Long.parseLong(getString(FRM_FIELD_D5)));//.length()<18 ? "0" : getString(FRM_FIELD_D5).substring(0,18)));
                        empSchedule.setD6(Long.parseLong(getString(FRM_FIELD_D6)));//.length()<18 ? "0" : getString(FRM_FIELD_D6).substring(0,18)));
                        empSchedule.setD7(Long.parseLong(getString(FRM_FIELD_D7)));//.length()<18 ? "0" : getString(FRM_FIELD_D7).substring(0,18)));
                        empSchedule.setD8(Long.parseLong(getString(FRM_FIELD_D8)));//.length()<18 ? "0" : getString(FRM_FIELD_D8).substring(0,18)));
                        empSchedule.setD9(Long.parseLong(getString(FRM_FIELD_D9)));//.length()<18 ? "0" : getString(FRM_FIELD_D9).substring(0,18)));
                        empSchedule.setD10(Long.parseLong(getString(FRM_FIELD_D10)));//.length()<18 ? "0" : getString(FRM_FIELD_D10).substring(0,18)));
                        empSchedule.setD11(Long.parseLong(getString(FRM_FIELD_D11)));//.length()<18 ? "0" : getString(FRM_FIELD_D11).substring(0,18)));
                        empSchedule.setD12(Long.parseLong(getString(FRM_FIELD_D12)));//.length()<18 ? "0" : getString(FRM_FIELD_D12).substring(0,18)));
                        empSchedule.setD13(Long.parseLong(getString(FRM_FIELD_D13)));//.length()<18 ? "0" : getString(FRM_FIELD_D13).substring(0,18)));
                        empSchedule.setD14(Long.parseLong(getString(FRM_FIELD_D14)));//.length()<18 ? "0" : getString(FRM_FIELD_D14).substring(0,18)));
                        empSchedule.setD15(Long.parseLong(getString(FRM_FIELD_D15)));//.length()<18 ? "0" : getString(FRM_FIELD_D15).substring(0,18)));
                        empSchedule.setD16(Long.parseLong(getString(FRM_FIELD_D16)));//.length()<18 ? "0" : getString(FRM_FIELD_D16).substring(0,18)));
                        empSchedule.setD17(Long.parseLong(getString(FRM_FIELD_D17)));//.length()<18 ? "0" : getString(FRM_FIELD_D17).substring(0,18)));
                        empSchedule.setD18(Long.parseLong(getString(FRM_FIELD_D18)));//.length()<18 ? "0" : getString(FRM_FIELD_D18).substring(0,18)));
                        empSchedule.setD19(Long.parseLong(getString(FRM_FIELD_D19)));//.length()<18 ? "0" : getString(FRM_FIELD_D19).substring(0,18)));
                        empSchedule.setD20(Long.parseLong(getString(FRM_FIELD_D20)));//.length()<18 ? "0" : getString(FRM_FIELD_D20).substring(0,18)));
                        empSchedule.setD21(Long.parseLong(getString(FRM_FIELD_D21)));//.length()<18 ? "0" : getString(FRM_FIELD_D21).substring(0,18)));
                        empSchedule.setD22(Long.parseLong(getString(FRM_FIELD_D22)));//.length()<18 ? "0" : getString(FRM_FIELD_D22).substring(0,18)));
                        empSchedule.setD23(Long.parseLong(getString(FRM_FIELD_D23)));//.length()<18 ? "0" : getString(FRM_FIELD_D23).substring(0,18)));
                        empSchedule.setD24(Long.parseLong(getString(FRM_FIELD_D24)));//.length()<18 ? "0" : getString(FRM_FIELD_D24).substring(0,18)));
                        empSchedule.setD25(Long.parseLong(getString(FRM_FIELD_D25)));//.length()<18 ? "0" : getString(FRM_FIELD_D25).substring(0,18)));
                        empSchedule.setD26(Long.parseLong(getString(FRM_FIELD_D26)));//.length()<18 ? "0" : getString(FRM_FIELD_D26).substring(0,18)));
                        empSchedule.setD27(Long.parseLong(getString(FRM_FIELD_D27)));//.length()<18 ? "0" : getString(FRM_FIELD_D27).substring(0,18)));
                        empSchedule.setD28(Long.parseLong(getString(FRM_FIELD_D28)));//.length()<18 ? "0" : getString(FRM_FIELD_D28).substring(0,18)));
                        empSchedule.setD29(Long.parseLong(getString(FRM_FIELD_D29)));//.length()<18 ? "0" : getString(FRM_FIELD_D29).substring(0,18)));
                        empSchedule.setD30(Long.parseLong(getString(FRM_FIELD_D30)));//.length()<18 ? "0" : getString(FRM_FIELD_D30).substring(0,18)));
                        empSchedule.setD31(Long.parseLong(getString(FRM_FIELD_D31)));//.length()<18 ? "0" : getString(FRM_FIELD_D31).substring(0,18)));                          
                        /* 
			empSchedule.setD2nd1(Long.parseLong(getString(FRM_FIELD_D2ND1).length()<18 ? "0" : getString(FRM_FIELD_D2ND1).substring(0,18)));
                        empSchedule.setD2nd2(Long.parseLong(getString(FRM_FIELD_D2ND2).length()<18 ? "0" : getString(FRM_FIELD_D2ND2).substring(0,18)));
                        empSchedule.setD2nd3(Long.parseLong(getString(FRM_FIELD_D2ND3).length()<18 ? "0" : getString(FRM_FIELD_D2ND3).substring(0,18)));
                        empSchedule.setD2nd4(Long.parseLong(getString(FRM_FIELD_D2ND4).length()<18 ? "0" : getString(FRM_FIELD_D2ND4).substring(0,18)));
                        empSchedule.setD2nd5(Long.parseLong(getString(FRM_FIELD_D2ND5).length()<18 ? "0" : getString(FRM_FIELD_D2ND5).substring(0,18)));
                        empSchedule.setD2nd6(Long.parseLong(getString(FRM_FIELD_D2ND6).length()<18 ? "0" : getString(FRM_FIELD_D2ND6).substring(0,18)));
                        empSchedule.setD2nd7(Long.parseLong(getString(FRM_FIELD_D2ND7).length()<18 ? "0" : getString(FRM_FIELD_D2ND7).substring(0,18)));
                        empSchedule.setD2nd8(Long.parseLong(getString(FRM_FIELD_D2ND8).length()<18 ? "0" : getString(FRM_FIELD_D2ND8).substring(0,18)));
                        empSchedule.setD2nd9(Long.parseLong(getString(FRM_FIELD_D2ND9).length()<18 ? "0" : getString(FRM_FIELD_D2ND9).substring(0,18)));
                        empSchedule.setD2nd10(Long.parseLong(getString(FRM_FIELD_D2ND10).length()<18 ? "0" : getString(FRM_FIELD_D2ND10).substring(0,18)));
                        empSchedule.setD2nd11(Long.parseLong(getString(FRM_FIELD_D2ND11).length()<18 ? "0" : getString(FRM_FIELD_D2ND11).substring(0,18)));
                        empSchedule.setD2nd12(Long.parseLong(getString(FRM_FIELD_D2ND12).length()<18 ? "0" : getString(FRM_FIELD_D2ND12).substring(0,18)));
                        empSchedule.setD2nd13(Long.parseLong(getString(FRM_FIELD_D2ND13).length()<18 ? "0" : getString(FRM_FIELD_D2ND13).substring(0,18)));
                        empSchedule.setD2nd14(Long.parseLong(getString(FRM_FIELD_D2ND14).length()<18 ? "0" : getString(FRM_FIELD_D2ND14).substring(0,18)));
                        empSchedule.setD2nd15(Long.parseLong(getString(FRM_FIELD_D2ND15).length()<18 ? "0" : getString(FRM_FIELD_D2ND15).substring(0,18)));
                        empSchedule.setD2nd16(Long.parseLong(getString(FRM_FIELD_D2ND16).length()<18 ? "0" : getString(FRM_FIELD_D2ND16).substring(0,18)));
                        empSchedule.setD2nd17(Long.parseLong(getString(FRM_FIELD_D2ND17).length()<18 ? "0" : getString(FRM_FIELD_D2ND17).substring(0,18)));
                        empSchedule.setD2nd18(Long.parseLong(getString(FRM_FIELD_D2ND18).length()<18 ? "0" : getString(FRM_FIELD_D2ND18).substring(0,18)));
                        empSchedule.setD2nd19(Long.parseLong(getString(FRM_FIELD_D2ND19).length()<18 ? "0" : getString(FRM_FIELD_D2ND19).substring(0,18)));
                        empSchedule.setD2nd20(Long.parseLong(getString(FRM_FIELD_D2ND20).length()<18 ? "0" : getString(FRM_FIELD_D2ND20).substring(0,18)));
                        empSchedule.setD2nd21(Long.parseLong(getString(FRM_FIELD_D2ND21).length()<18 ? "0" : getString(FRM_FIELD_D2ND21).substring(0,18)));
                        empSchedule.setD2nd22(Long.parseLong(getString(FRM_FIELD_D2ND22).length()<18 ? "0" : getString(FRM_FIELD_D2ND22).substring(0,18)));
                        empSchedule.setD2nd23(Long.parseLong(getString(FRM_FIELD_D2ND23).length()<18 ? "0" : getString(FRM_FIELD_D2ND23).substring(0,18)));
                        empSchedule.setD2nd24(Long.parseLong(getString(FRM_FIELD_D2ND24).length()<18 ? "0" : getString(FRM_FIELD_D2ND24).substring(0,18)));
                        empSchedule.setD2nd25(Long.parseLong(getString(FRM_FIELD_D2ND25).length()<18 ? "0" : getString(FRM_FIELD_D2ND25).substring(0,18)));
                        empSchedule.setD2nd26(Long.parseLong(getString(FRM_FIELD_D2ND26).length()<18 ? "0" : getString(FRM_FIELD_D2ND26).substring(0,18)));
                        empSchedule.setD2nd27(Long.parseLong(getString(FRM_FIELD_D2ND27).length()<18 ? "0" : getString(FRM_FIELD_D2ND27).substring(0,18)));
                        empSchedule.setD2nd28(Long.parseLong(getString(FRM_FIELD_D2ND28).length()<18 ? "0" : getString(FRM_FIELD_D2ND28).substring(0,18)));
                        empSchedule.setD2nd29(Long.parseLong(getString(FRM_FIELD_D2ND29).length()<18 ? "0" : getString(FRM_FIELD_D2ND29).substring(0,18)));
                        empSchedule.setD2nd30(Long.parseLong(getString(FRM_FIELD_D2ND30).length()<18 ? "0" : getString(FRM_FIELD_D2ND30).substring(0,18)));
                        empSchedule.setD2nd31(Long.parseLong(getString(FRM_FIELD_D2ND31).length()<18 ? "0" : getString(FRM_FIELD_D2ND31).substring(0,18)));                        
                        
                        
                        
                        
			empSchedule.setStatus1(Integer.parseInt(getString(FRM_FIELD_STATUS1)));
			empSchedule.setStatus2(Integer.parseInt(getString(FRM_FIELD_STATUS2)));
			empSchedule.setStatus3(Integer.parseInt(getString(FRM_FIELD_STATUS3)));
			empSchedule.setStatus4(Integer.parseInt(getString(FRM_FIELD_STATUS4)));
			empSchedule.setStatus5(Integer.parseInt(getString(FRM_FIELD_STATUS5)));
			empSchedule.setStatus6(Integer.parseInt(getString(FRM_FIELD_STATUS6)));
			empSchedule.setStatus7(Integer.parseInt(getString(FRM_FIELD_STATUS7)));
			empSchedule.setStatus8(Integer.parseInt(getString(FRM_FIELD_STATUS8)));
			empSchedule.setStatus9(Integer.parseInt(getString(FRM_FIELD_STATUS9)));
			empSchedule.setStatus10(Integer.parseInt(getString(FRM_FIELD_STATUS10)));
			empSchedule.setStatus11(Integer.parseInt(getString(FRM_FIELD_STATUS11)));
			empSchedule.setStatus12(Integer.parseInt(getString(FRM_FIELD_STATUS12)));
			empSchedule.setStatus13(Integer.parseInt(getString(FRM_FIELD_STATUS13)));
			empSchedule.setStatus14(Integer.parseInt(getString(FRM_FIELD_STATUS14)));
			empSchedule.setStatus15(Integer.parseInt(getString(FRM_FIELD_STATUS15)));
			empSchedule.setStatus16(Integer.parseInt(getString(FRM_FIELD_STATUS16)));
			empSchedule.setStatus17(Integer.parseInt(getString(FRM_FIELD_STATUS17)));
			empSchedule.setStatus18(Integer.parseInt(getString(FRM_FIELD_STATUS18)));
			empSchedule.setStatus19(Integer.parseInt(getString(FRM_FIELD_STATUS19)));
			empSchedule.setStatus20(Integer.parseInt(getString(FRM_FIELD_STATUS20)));
			empSchedule.setStatus21(Integer.parseInt(getString(FRM_FIELD_STATUS21)));
			empSchedule.setStatus22(Integer.parseInt(getString(FRM_FIELD_STATUS22)));
			empSchedule.setStatus23(Integer.parseInt(getString(FRM_FIELD_STATUS23)));
			empSchedule.setStatus24(Integer.parseInt(getString(FRM_FIELD_STATUS24)));
			empSchedule.setStatus25(Integer.parseInt(getString(FRM_FIELD_STATUS25)));
			empSchedule.setStatus26(Integer.parseInt(getString(FRM_FIELD_STATUS26)));
			empSchedule.setStatus27(Integer.parseInt(getString(FRM_FIELD_STATUS27)));
			empSchedule.setStatus28(Integer.parseInt(getString(FRM_FIELD_STATUS28)));
			empSchedule.setStatus29(Integer.parseInt(getString(FRM_FIELD_STATUS29)));
			empSchedule.setStatus30(Integer.parseInt(getString(FRM_FIELD_STATUS30)));
			empSchedule.setStatus31(Integer.parseInt(getString(FRM_FIELD_STATUS31)));
                        
			empSchedule.setStatus2nd1(Integer.parseInt(getString(FRM_FIELD_STATUS2ND1)));
                        empSchedule.setStatus2nd2(Integer.parseInt(getString(FRM_FIELD_STATUS2ND2)));                        
                        empSchedule.setStatus2nd3(Integer.parseInt(getString(FRM_FIELD_STATUS2ND3)));                        
                        empSchedule.setStatus2nd4(Integer.parseInt(getString(FRM_FIELD_STATUS2ND4)));                        
                        empSchedule.setStatus2nd5(Integer.parseInt(getString(FRM_FIELD_STATUS2ND5)));                        
                        empSchedule.setStatus2nd6(Integer.parseInt(getString(FRM_FIELD_STATUS2ND6)));                        
                        empSchedule.setStatus2nd7(Integer.parseInt(getString(FRM_FIELD_STATUS2ND7)));                        
                        empSchedule.setStatus2nd8(Integer.parseInt(getString(FRM_FIELD_STATUS2ND8)));                        
                        empSchedule.setStatus2nd9(Integer.parseInt(getString(FRM_FIELD_STATUS2ND9)));                        
                        empSchedule.setStatus2nd10(Integer.parseInt(getString(FRM_FIELD_STATUS2ND10)));                        
                        empSchedule.setStatus2nd11(Integer.parseInt(getString(FRM_FIELD_STATUS2ND11)));                        
                        empSchedule.setStatus2nd12(Integer.parseInt(getString(FRM_FIELD_STATUS2ND12)));                        
                        empSchedule.setStatus2nd13(Integer.parseInt(getString(FRM_FIELD_STATUS2ND13)));                        
                        empSchedule.setStatus2nd14(Integer.parseInt(getString(FRM_FIELD_STATUS2ND14)));                        
                        empSchedule.setStatus2nd15(Integer.parseInt(getString(FRM_FIELD_STATUS2ND15)));                        
                        empSchedule.setStatus2nd16(Integer.parseInt(getString(FRM_FIELD_STATUS2ND16)));                                                
                        empSchedule.setStatus2nd17(Integer.parseInt(getString(FRM_FIELD_STATUS2ND17)));                                                
                        empSchedule.setStatus2nd18(Integer.parseInt(getString(FRM_FIELD_STATUS2ND18)));                                                
                        empSchedule.setStatus2nd19(Integer.parseInt(getString(FRM_FIELD_STATUS2ND19)));                                                
                        empSchedule.setStatus2nd20(Integer.parseInt(getString(FRM_FIELD_STATUS2ND20)));                                                
                        empSchedule.setStatus2nd21(Integer.parseInt(getString(FRM_FIELD_STATUS2ND21)));                                                
                        empSchedule.setStatus2nd22(Integer.parseInt(getString(FRM_FIELD_STATUS2ND22)));                                                
                        empSchedule.setStatus2nd23(Integer.parseInt(getString(FRM_FIELD_STATUS2ND23)));                                                
                        empSchedule.setStatus2nd24(Integer.parseInt(getString(FRM_FIELD_STATUS2ND24)));                                                
                        empSchedule.setStatus2nd25(Integer.parseInt(getString(FRM_FIELD_STATUS2ND25)));                                                
                        empSchedule.setStatus2nd26(Integer.parseInt(getString(FRM_FIELD_STATUS2ND26)));                                                
                        empSchedule.setStatus2nd27(Integer.parseInt(getString(FRM_FIELD_STATUS2ND27)));                                                
                        empSchedule.setStatus2nd28(Integer.parseInt(getString(FRM_FIELD_STATUS2ND28)));                                                
                        empSchedule.setStatus2nd29(Integer.parseInt(getString(FRM_FIELD_STATUS2ND29)));                                                
                        empSchedule.setStatus2nd30(Integer.parseInt(getString(FRM_FIELD_STATUS2ND30)));                                                
                        empSchedule.setStatus2nd31(Integer.parseInt(getString(FRM_FIELD_STATUS2ND31)));                                                
                        

                        empSchedule.setReason1(Integer.parseInt(getString(FRM_FIELD_REASON1)));
                        empSchedule.setReason2(Integer.parseInt(getString(FRM_FIELD_REASON2)));
                        empSchedule.setReason3(Integer.parseInt(getString(FRM_FIELD_REASON3)));
                        empSchedule.setReason4(Integer.parseInt(getString(FRM_FIELD_REASON4)));
                        empSchedule.setReason5(Integer.parseInt(getString(FRM_FIELD_REASON5)));
                        empSchedule.setReason6(Integer.parseInt(getString(FRM_FIELD_REASON6)));
                        empSchedule.setReason7(Integer.parseInt(getString(FRM_FIELD_REASON7)));
                        empSchedule.setReason8(Integer.parseInt(getString(FRM_FIELD_REASON8)));
                        empSchedule.setReason9(Integer.parseInt(getString(FRM_FIELD_REASON9)));
                        empSchedule.setReason10(Integer.parseInt(getString(FRM_FIELD_REASON10)));
                        empSchedule.setReason11(Integer.parseInt(getString(FRM_FIELD_REASON11)));
                        empSchedule.setReason12(Integer.parseInt(getString(FRM_FIELD_REASON12)));
                        empSchedule.setReason13(Integer.parseInt(getString(FRM_FIELD_REASON13)));
                        empSchedule.setReason14(Integer.parseInt(getString(FRM_FIELD_REASON14)));
                        empSchedule.setReason15(Integer.parseInt(getString(FRM_FIELD_REASON15)));
                        empSchedule.setReason16(Integer.parseInt(getString(FRM_FIELD_REASON16)));
                        empSchedule.setReason17(Integer.parseInt(getString(FRM_FIELD_REASON17)));
                        empSchedule.setReason18(Integer.parseInt(getString(FRM_FIELD_REASON18)));
                        empSchedule.setReason19(Integer.parseInt(getString(FRM_FIELD_REASON19)));
                        empSchedule.setReason20(Integer.parseInt(getString(FRM_FIELD_REASON20)));
                        empSchedule.setReason21(Integer.parseInt(getString(FRM_FIELD_REASON21)));
                        empSchedule.setReason22(Integer.parseInt(getString(FRM_FIELD_REASON22)));
                        empSchedule.setReason23(Integer.parseInt(getString(FRM_FIELD_REASON23)));
                        empSchedule.setReason24(Integer.parseInt(getString(FRM_FIELD_REASON24)));
                        empSchedule.setReason25(Integer.parseInt(getString(FRM_FIELD_REASON25)));
                        empSchedule.setReason26(Integer.parseInt(getString(FRM_FIELD_REASON26)));
                        empSchedule.setReason27(Integer.parseInt(getString(FRM_FIELD_REASON27)));
                        empSchedule.setReason28(Integer.parseInt(getString(FRM_FIELD_REASON28)));
                        empSchedule.setReason29(Integer.parseInt(getString(FRM_FIELD_REASON29)));
                        empSchedule.setReason30(Integer.parseInt(getString(FRM_FIELD_REASON30)));
                        empSchedule.setReason31(Integer.parseInt(getString(FRM_FIELD_REASON31)));
                        
                        empSchedule.setReason2nd1(Integer.parseInt(getString(FRM_FIELD_REASON2ND1)));
                        empSchedule.setReason2nd2(Integer.parseInt(getString(FRM_FIELD_REASON2ND2)));
                        empSchedule.setReason2nd3(Integer.parseInt(getString(FRM_FIELD_REASON2ND3)));
                        empSchedule.setReason2nd4(Integer.parseInt(getString(FRM_FIELD_REASON2ND4)));
                        empSchedule.setReason2nd5(Integer.parseInt(getString(FRM_FIELD_REASON2ND5)));
                        empSchedule.setReason2nd6(Integer.parseInt(getString(FRM_FIELD_REASON2ND6)));
                        empSchedule.setReason2nd7(Integer.parseInt(getString(FRM_FIELD_REASON2ND7)));
                        empSchedule.setReason2nd8(Integer.parseInt(getString(FRM_FIELD_REASON2ND8)));
                        empSchedule.setReason2nd9(Integer.parseInt(getString(FRM_FIELD_REASON2ND9)));
                        empSchedule.setReason2nd10(Integer.parseInt(getString(FRM_FIELD_REASON2ND10)));
                        empSchedule.setReason2nd11(Integer.parseInt(getString(FRM_FIELD_REASON2ND11)));
                        empSchedule.setReason2nd12(Integer.parseInt(getString(FRM_FIELD_REASON2ND12)));
                        empSchedule.setReason2nd13(Integer.parseInt(getString(FRM_FIELD_REASON2ND13)));
                        empSchedule.setReason2nd14(Integer.parseInt(getString(FRM_FIELD_REASON2ND14)));
                        empSchedule.setReason2nd15(Integer.parseInt(getString(FRM_FIELD_REASON2ND15)));
                        empSchedule.setReason2nd16(Integer.parseInt(getString(FRM_FIELD_REASON2ND16)));
                        empSchedule.setReason2nd17(Integer.parseInt(getString(FRM_FIELD_REASON2ND17)));
                        empSchedule.setReason2nd18(Integer.parseInt(getString(FRM_FIELD_REASON2ND18)));
                        empSchedule.setReason2nd19(Integer.parseInt(getString(FRM_FIELD_REASON2ND19)));
                        empSchedule.setReason2nd20(Integer.parseInt(getString(FRM_FIELD_REASON2ND20)));
                        empSchedule.setReason2nd21(Integer.parseInt(getString(FRM_FIELD_REASON2ND21)));
                        empSchedule.setReason2nd22(Integer.parseInt(getString(FRM_FIELD_REASON2ND22)));
                        empSchedule.setReason2nd23(Integer.parseInt(getString(FRM_FIELD_REASON2ND23)));
                        empSchedule.setReason2nd24(Integer.parseInt(getString(FRM_FIELD_REASON2ND24)));
                        empSchedule.setReason2nd25(Integer.parseInt(getString(FRM_FIELD_REASON2ND25)));
                        empSchedule.setReason2nd26(Integer.parseInt(getString(FRM_FIELD_REASON2ND26)));
                        empSchedule.setReason2nd27(Integer.parseInt(getString(FRM_FIELD_REASON2ND27)));
                        empSchedule.setReason2nd28(Integer.parseInt(getString(FRM_FIELD_REASON2ND28)));
                        empSchedule.setReason2nd29(Integer.parseInt(getString(FRM_FIELD_REASON2ND29)));
                        empSchedule.setReason2nd30(Integer.parseInt(getString(FRM_FIELD_REASON2ND30)));
                        empSchedule.setReason2nd31(Integer.parseInt(getString(FRM_FIELD_REASON2ND31)));
                        
                        empSchedule.setNote1(getString(FRM_FIELD_NOTE1));                         
                        empSchedule.setNote2(getString(FRM_FIELD_NOTE2)!=null ? getString(FRM_FIELD_NOTE2) : "");                         
                        empSchedule.setNote3(getString(FRM_FIELD_NOTE3)); 
                        empSchedule.setNote4(getString(FRM_FIELD_NOTE4)); 
                        empSchedule.setNote5(getString(FRM_FIELD_NOTE5)); 
                        empSchedule.setNote6(getString(FRM_FIELD_NOTE6)); 
                        empSchedule.setNote7(getString(FRM_FIELD_NOTE7)); 
                        empSchedule.setNote8(getString(FRM_FIELD_NOTE8)); 
                        empSchedule.setNote9(getString(FRM_FIELD_NOTE9)); 
                        empSchedule.setNote10(getString(FRM_FIELD_NOTE10)); 
                        empSchedule.setNote11(getString(FRM_FIELD_NOTE11)); 
                        empSchedule.setNote12(getString(FRM_FIELD_NOTE12)); 
                        empSchedule.setNote13(getString(FRM_FIELD_NOTE13)); 
                        empSchedule.setNote14(getString(FRM_FIELD_NOTE14)); 
                        empSchedule.setNote15(getString(FRM_FIELD_NOTE15)); 
                        empSchedule.setNote16(getString(FRM_FIELD_NOTE16)); 
                        empSchedule.setNote17(getString(FRM_FIELD_NOTE17)); 
                        empSchedule.setNote18(getString(FRM_FIELD_NOTE18)); 
                        empSchedule.setNote19(getString(FRM_FIELD_NOTE19)); 
                        empSchedule.setNote20(getString(FRM_FIELD_NOTE20)); 
                        empSchedule.setNote21(getString(FRM_FIELD_NOTE21)); 
                        empSchedule.setNote22(getString(FRM_FIELD_NOTE22)); 
                        empSchedule.setNote23(getString(FRM_FIELD_NOTE23)); 
                        empSchedule.setNote24(getString(FRM_FIELD_NOTE24)); 
                        empSchedule.setNote25(getString(FRM_FIELD_NOTE25)); 
                        empSchedule.setNote26(getString(FRM_FIELD_NOTE26)); 
                        empSchedule.setNote27(getString(FRM_FIELD_NOTE27)); 
                        empSchedule.setNote28(getString(FRM_FIELD_NOTE28)); 
                        empSchedule.setNote29(getString(FRM_FIELD_NOTE29)); 
                        empSchedule.setNote30(getString(FRM_FIELD_NOTE30)); 
                        empSchedule.setNote31(getString(FRM_FIELD_NOTE31)); 
                        
                        empSchedule.setNote2nd1(getString(FRM_FIELD_NOTE2ND1));
                        empSchedule.setNote2nd2(getString(FRM_FIELD_NOTE2ND2));
                        empSchedule.setNote2nd3(getString(FRM_FIELD_NOTE2ND3));
                        empSchedule.setNote2nd4(getString(FRM_FIELD_NOTE2ND4));
                        empSchedule.setNote2nd5(getString(FRM_FIELD_NOTE2ND5));
                        empSchedule.setNote2nd6(getString(FRM_FIELD_NOTE2ND6));
                        empSchedule.setNote2nd7(getString(FRM_FIELD_NOTE2ND7));
                        empSchedule.setNote2nd8(getString(FRM_FIELD_NOTE2ND8));
                        empSchedule.setNote2nd9(getString(FRM_FIELD_NOTE2ND9));
                        empSchedule.setNote2nd10(getString(FRM_FIELD_NOTE2ND10));
                        empSchedule.setNote2nd11(getString(FRM_FIELD_NOTE2ND11));
                        empSchedule.setNote2nd12(getString(FRM_FIELD_NOTE2ND12));
                        empSchedule.setNote2nd13(getString(FRM_FIELD_NOTE2ND13));
                        empSchedule.setNote2nd14(getString(FRM_FIELD_NOTE2ND14));
                        empSchedule.setNote2nd15(getString(FRM_FIELD_NOTE2ND15));
                        empSchedule.setNote2nd16(getString(FRM_FIELD_NOTE2ND16));
                        empSchedule.setNote2nd17(getString(FRM_FIELD_NOTE2ND17));
                        empSchedule.setNote2nd18(getString(FRM_FIELD_NOTE2ND18));
                        empSchedule.setNote2nd19(getString(FRM_FIELD_NOTE2ND19));
                        empSchedule.setNote2nd20(getString(FRM_FIELD_NOTE2ND20));
                        empSchedule.setNote2nd21(getString(FRM_FIELD_NOTE2ND21));
                        empSchedule.setNote2nd22(getString(FRM_FIELD_NOTE2ND22));
                        empSchedule.setNote2nd23(getString(FRM_FIELD_NOTE2ND23));
                        empSchedule.setNote2nd24(getString(FRM_FIELD_NOTE2ND24));
                        empSchedule.setNote2nd25(getString(FRM_FIELD_NOTE2ND25));
                        empSchedule.setNote2nd26(getString(FRM_FIELD_NOTE2ND26));
                        empSchedule.setNote2nd27(getString(FRM_FIELD_NOTE2ND27));
                        empSchedule.setNote2nd28(getString(FRM_FIELD_NOTE2ND28));
                        empSchedule.setNote2nd29(getString(FRM_FIELD_NOTE2ND29));
                        empSchedule.setNote2nd30(getString(FRM_FIELD_NOTE2ND30));
                        empSchedule.setNote2nd31(getString(FRM_FIELD_NOTE2ND31));   
                        */                     
		}
                catch(Exception e)
                {
			System.out.println("Error on requestEntityObject : "+e.toString());
		}
	}
}
