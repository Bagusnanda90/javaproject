
/* Created on 	:  [date] [time] AM/PM 
 * 
 * @author  	: edhy
 * @version  	: 01 
 */

/*******************************************************************
 * Class Description 	: [project description ... ] 
 * Imput Parameters 	: [input parameter ...] 
 * Output 		: [output ...] 
 *******************************************************************/

package com.dimata.harisma.entity.attendance; 

// package java 
import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.Date;

// package qdep 
import com.dimata.util.lang.I_Language;
import com.dimata.util.Formater;
import com.dimata.qdep.db.*;
import com.dimata.qdep.entity.*;

// package harisma 
import com.dimata.harisma.entity.masterdata.*; 
import com.dimata.harisma.entity.attendance.*; 

public class PstEmpScheduleOrg extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language { 

	public static final  String TBL_HR_EMP_SCHEDULE ="hr_emp_schedule_edhy";// "HR_EMP_SCHEDULE_EDHY";

	public static final  int FLD_EMP_SCHEDULE_ID = 0;
	public static final  int FLD_PERIOD_ID = 1;
	public static final  int FLD_EMPLOYEE_ID = 2;  
	public static final  int FLD_D1 = 3;
	public static final  int FLD_D2 = 4;
	public static final  int FLD_D3 = 5;
	public static final  int FLD_D4 = 6;
	public static final  int FLD_D5 = 7;
	public static final  int FLD_D6 = 8;
	public static final  int FLD_D7 = 9;
	public static final  int FLD_D8 = 10;
	public static final  int FLD_D9 = 11;
	public static final  int FLD_D10 = 12;
	public static final  int FLD_D11 = 13;
	public static final  int FLD_D12 = 14;
	public static final  int FLD_D13 = 15;
	public static final  int FLD_D14 = 16;
	public static final  int FLD_D15 = 17;
	public static final  int FLD_D16 = 18;
	public static final  int FLD_D17 = 19;
	public static final  int FLD_D18 = 20;
	public static final  int FLD_D19 = 21;
	public static final  int FLD_D20 = 22;
	public static final  int FLD_D21 = 23;
	public static final  int FLD_D22 = 24;
	public static final  int FLD_D23 = 25;
	public static final  int FLD_D24 = 26;
	public static final  int FLD_D25 = 27;
	public static final  int FLD_D26 = 28;
	public static final  int FLD_D27 = 29;
	public static final  int FLD_D28 = 30;
	public static final  int FLD_D29 = 31;
	public static final  int FLD_D30 = 32;
	public static final  int FLD_D31 = 33;
        
        // added by Edhy for split shift
	public static final  int FLD_D2ND1 = 34;
	public static final  int FLD_D2ND2 = 35;
	public static final  int FLD_D2ND3 = 36;
	public static final  int FLD_D2ND4 = 37;
	public static final  int FLD_D2ND5 = 38;
	public static final  int FLD_D2ND6 = 39;
	public static final  int FLD_D2ND7 = 40;
	public static final  int FLD_D2ND8 = 41;
	public static final  int FLD_D2ND9 = 42;
	public static final  int FLD_D2ND10 = 43;
	public static final  int FLD_D2ND11 = 44;
	public static final  int FLD_D2ND12 = 45;
	public static final  int FLD_D2ND13 = 46;
	public static final  int FLD_D2ND14 = 47;
	public static final  int FLD_D2ND15 = 48;
	public static final  int FLD_D2ND16 = 49;
	public static final  int FLD_D2ND17 = 50;
	public static final  int FLD_D2ND18 = 51;
	public static final  int FLD_D2ND19 = 52;
	public static final  int FLD_D2ND20 = 53;
	public static final  int FLD_D2ND21 = 54;
	public static final  int FLD_D2ND22 = 55;
	public static final  int FLD_D2ND23 = 56;
	public static final  int FLD_D2ND24 = 57;
	public static final  int FLD_D2ND25 = 58;
	public static final  int FLD_D2ND26 = 59;
	public static final  int FLD_D2ND27 = 60;
	public static final  int FLD_D2ND28 = 61;
	public static final  int FLD_D2ND29 = 62;
	public static final  int FLD_D2ND30 = 63;
	public static final  int FLD_D2ND31 = 64;
        
	public static final  int FLD_STATUS1 = 65;        
        public static final  int FLD_STATUS2 = 66;
        public static final  int FLD_STATUS3 = 67;
        public static final  int FLD_STATUS4 = 68;
        public static final  int FLD_STATUS5 = 69;
        public static final  int FLD_STATUS6 = 70;
        public static final  int FLD_STATUS7 = 71;
        public static final  int FLD_STATUS8 = 72;
        public static final  int FLD_STATUS9 = 73;
        public static final  int FLD_STATUS10 = 74;
	public static final  int FLD_STATUS11 = 75;        
        public static final  int FLD_STATUS12 = 76;
        public static final  int FLD_STATUS13 = 77;
        public static final  int FLD_STATUS14 = 78;
        public static final  int FLD_STATUS15 = 79;
        public static final  int FLD_STATUS16 = 80;
        public static final  int FLD_STATUS17 = 81;
        public static final  int FLD_STATUS18 = 82;
        public static final  int FLD_STATUS19 = 83;
        public static final  int FLD_STATUS20 = 84;
	public static final  int FLD_STATUS21 = 85;        
        public static final  int FLD_STATUS22 = 86;
        public static final  int FLD_STATUS23 = 87;
        public static final  int FLD_STATUS24 = 88;
        public static final  int FLD_STATUS25 = 89;
        public static final  int FLD_STATUS26 = 90;
        public static final  int FLD_STATUS27 = 91;
        public static final  int FLD_STATUS28 = 92;
        public static final  int FLD_STATUS29 = 93;
        public static final  int FLD_STATUS30 = 94;
	public static final  int FLD_STATUS31 = 95;        
        public static final  int FLD_STATUS2ND1 = 96;
        public static final  int FLD_STATUS2ND2 = 97;
        public static final  int FLD_STATUS2ND3 = 98;
        public static final  int FLD_STATUS2ND4 = 99;
        public static final  int FLD_STATUS2ND5 = 100;
        public static final  int FLD_STATUS2ND6 = 101;
        public static final  int FLD_STATUS2ND7 = 102;
        public static final  int FLD_STATUS2ND8 = 103;
        public static final  int FLD_STATUS2ND9 = 104;
	public static final  int FLD_STATUS2ND10 = 105;        
        public static final  int FLD_STATUS2ND11 = 106;
        public static final  int FLD_STATUS2ND12 = 107;
        public static final  int FLD_STATUS2ND13 = 108;
        public static final  int FLD_STATUS2ND14 = 109;
        public static final  int FLD_STATUS2ND15 = 110;
        public static final  int FLD_STATUS2ND16 = 111;
        public static final  int FLD_STATUS2ND17 = 112;
        public static final  int FLD_STATUS2ND18 = 113;
        public static final  int FLD_STATUS2ND19 = 114;
	public static final  int FLD_STATUS2ND20 = 115;        
        public static final  int FLD_STATUS2ND21 = 116;
        public static final  int FLD_STATUS2ND22 = 117;
        public static final  int FLD_STATUS2ND23 = 118;
        public static final  int FLD_STATUS2ND24 = 119;
        public static final  int FLD_STATUS2ND25 = 120;
        public static final  int FLD_STATUS2ND26 = 121;
        public static final  int FLD_STATUS2ND27 = 122;
        public static final  int FLD_STATUS2ND28 = 123;
        public static final  int FLD_STATUS2ND29 = 124;
        public static final  int FLD_STATUS2ND30 = 125;        
        public static final  int FLD_STATUS2ND31 = 126;
        
	public static final  int FLD_REASON1 = 127;                
	public static final  int FLD_REASON2 = 128;                
	public static final  int FLD_REASON3 = 129;                
	public static final  int FLD_REASON4 = 130;                
	public static final  int FLD_REASON5 = 131;                
	public static final  int FLD_REASON6 = 132;                
	public static final  int FLD_REASON7 = 133;                
	public static final  int FLD_REASON8 = 134;                
	public static final  int FLD_REASON9 = 135;                
	public static final  int FLD_REASON10 = 136;                
	public static final  int FLD_REASON11 = 137;                
	public static final  int FLD_REASON12 = 138;                        
	public static final  int FLD_REASON13 = 139;                
	public static final  int FLD_REASON14 = 140;                
	public static final  int FLD_REASON15 = 141;                
	public static final  int FLD_REASON16 = 142;                
	public static final  int FLD_REASON17 = 143;                
	public static final  int FLD_REASON18 = 144;                
	public static final  int FLD_REASON19 = 145;                
	public static final  int FLD_REASON20 = 146;                
	public static final  int FLD_REASON21 = 147;                
	public static final  int FLD_REASON22 = 148;                
	public static final  int FLD_REASON23 = 149;                
	public static final  int FLD_REASON24 = 150;                
	public static final  int FLD_REASON25 = 151;                
	public static final  int FLD_REASON26 = 152;                
	public static final  int FLD_REASON27 = 153;                
	public static final  int FLD_REASON28 = 154;                
	public static final  int FLD_REASON29 = 155;                
	public static final  int FLD_REASON30 = 156;                
	public static final  int FLD_REASON31 = 157;                
	public static final  int FLD_REASON2ND1 = 158;                
	public static final  int FLD_REASON2ND2 = 159;                
	public static final  int FLD_REASON2ND3 = 160;                
	public static final  int FLD_REASON2ND4 = 161;                
	public static final  int FLD_REASON2ND5 = 162;                
	public static final  int FLD_REASON2ND6 = 163;                
	public static final  int FLD_REASON2ND7 = 164;                
	public static final  int FLD_REASON2ND8 = 165;                
	public static final  int FLD_REASON2ND9 = 166;                
	public static final  int FLD_REASON2ND10 = 167;                
	public static final  int FLD_REASON2ND11 = 168;                
	public static final  int FLD_REASON2ND12 = 169;                
	public static final  int FLD_REASON2ND13 = 170;                
	public static final  int FLD_REASON2ND14 = 171;                
	public static final  int FLD_REASON2ND15 = 172;                
	public static final  int FLD_REASON2ND16 = 173;                
	public static final  int FLD_REASON2ND17 = 174;                
	public static final  int FLD_REASON2ND18 = 175;                
	public static final  int FLD_REASON2ND19 = 176;                
	public static final  int FLD_REASON2ND20 = 177;                
	public static final  int FLD_REASON2ND21 = 178;                
        public static final  int FLD_REASON2ND22 = 179;                
	public static final  int FLD_REASON2ND23 = 180;                
	public static final  int FLD_REASON2ND24 = 181;                
	public static final  int FLD_REASON2ND25 = 182;                
	public static final  int FLD_REASON2ND26 = 183;                
	public static final  int FLD_REASON2ND27 = 184;                
	public static final  int FLD_REASON2ND28 = 185;                
	public static final  int FLD_REASON2ND29 = 186;                
	public static final  int FLD_REASON2ND30 = 187;                
	public static final  int FLD_REASON2ND31 = 188;                        
        
        public static final  int FLD_NOTE1 = 189;   
        public static final  int FLD_NOTE2 = 190;   
        public static final  int FLD_NOTE3 = 191;   
        public static final  int FLD_NOTE4 = 192;   
        public static final  int FLD_NOTE5 = 193;   
        public static final  int FLD_NOTE6 = 194;   
        public static final  int FLD_NOTE7 = 195;   
        public static final  int FLD_NOTE8 = 196;   
        public static final  int FLD_NOTE9 = 197;   
        public static final  int FLD_NOTE10 = 198;   
        public static final  int FLD_NOTE11 = 199;   
        public static final  int FLD_NOTE12 = 200;   
        public static final  int FLD_NOTE13 = 201;   
        public static final  int FLD_NOTE14 = 202;   
        public static final  int FLD_NOTE15 = 203;   
        public static final  int FLD_NOTE16 = 204;   
        public static final  int FLD_NOTE17 = 205;   
        public static final  int FLD_NOTE18 = 206;   
        public static final  int FLD_NOTE19 = 207;   
        public static final  int FLD_NOTE20 = 208;   
        public static final  int FLD_NOTE21 = 209;   
        public static final  int FLD_NOTE22 = 210;   
        public static final  int FLD_NOTE23 = 211;   
        public static final  int FLD_NOTE24 = 212;   
        public static final  int FLD_NOTE25 = 213;   
        public static final  int FLD_NOTE26 = 214;   
        public static final  int FLD_NOTE27 = 215;   
        public static final  int FLD_NOTE28 = 216;   
        public static final  int FLD_NOTE29 = 217;   
        public static final  int FLD_NOTE30 = 218;   
        public static final  int FLD_NOTE31 = 219;   
        public static final  int FLD_NOTE2ND1 = 220;   
        public static final  int FLD_NOTE2ND2 = 221;   
        public static final  int FLD_NOTE2ND3 = 222;   
        public static final  int FLD_NOTE2ND4 = 223;   
        public static final  int FLD_NOTE2ND5 = 224;   
        public static final  int FLD_NOTE2ND6 = 225;   
        public static final  int FLD_NOTE2ND7= 226;   
        public static final  int FLD_NOTE2ND8 = 227;   
        public static final  int FLD_NOTE2ND9 = 228;   
        public static final  int FLD_NOTE2ND10 = 229;   
        public static final  int FLD_NOTE2ND11 = 230;   
        public static final  int FLD_NOTE2ND12 = 231;   
        public static final  int FLD_NOTE2ND13 = 232;   
        public static final  int FLD_NOTE2ND14 = 233;   
        public static final  int FLD_NOTE2ND15 = 234;   
        public static final  int FLD_NOTE2ND16 = 235;   
        public static final  int FLD_NOTE2ND17 = 236;   
        public static final  int FLD_NOTE2ND18 = 237;   
        public static final  int FLD_NOTE2ND19 = 238;   
        public static final  int FLD_NOTE2ND20 = 239;   
        public static final  int FLD_NOTE2ND21 = 240;   
        public static final  int FLD_NOTE2ND22 = 241;   
        public static final  int FLD_NOTE2ND23 = 242;   
        public static final  int FLD_NOTE2ND24 = 243;   
        public static final  int FLD_NOTE2ND25 = 244;   
        public static final  int FLD_NOTE2ND26 = 245;   
        public static final  int FLD_NOTE2ND27 = 246;   
        public static final  int FLD_NOTE2ND28 = 247;   
        public static final  int FLD_NOTE2ND29 = 248;   
        public static final  int FLD_NOTE2ND30 = 249;   
        public static final  int FLD_NOTE2ND31 = 250;   
        
        public static final  int FLD_IN1 = 251;           
        public static final  int FLD_IN2 = 252;           
        public static final  int FLD_IN3 = 253;           
        public static final  int FLD_IN4 = 254;           
        public static final  int FLD_IN5 = 255;           
        public static final  int FLD_IN6 = 256;           
        public static final  int FLD_IN7 = 257;           
        public static final  int FLD_IN8 = 258;           
        public static final  int FLD_IN9 = 259;           
        public static final  int FLD_IN10 = 260;           
        public static final  int FLD_IN11 = 261;           
        public static final  int FLD_IN12 = 262;           
        public static final  int FLD_IN13 = 263;           
        public static final  int FLD_IN14 = 264;           
        public static final  int FLD_IN15 = 265;           
        public static final  int FLD_IN16 = 266;           
        public static final  int FLD_IN17 = 267;           
        public static final  int FLD_IN18 = 268;           
        public static final  int FLD_IN19 = 269;           
        public static final  int FLD_IN20 = 270;           
        public static final  int FLD_IN21 = 271;           
        public static final  int FLD_IN22 = 272;           
        public static final  int FLD_IN23 = 273;           
        public static final  int FLD_IN24 = 274;           
        public static final  int FLD_IN25 = 275;           
        public static final  int FLD_IN26 = 276;           
        public static final  int FLD_IN27 = 277;           
        public static final  int FLD_IN28 = 278;           
        public static final  int FLD_IN29 = 279;           
        public static final  int FLD_IN30 = 280;       
        public static final  int FLD_IN31 = 281;                   
        public static final  int FLD_IN2ND1 = 282;           
        public static final  int FLD_IN2ND2 = 283;           
        public static final  int FLD_IN2ND3 = 284;           
        public static final  int FLD_IN2ND4 = 285;           
        public static final  int FLD_IN2ND5 = 286;           
        public static final  int FLD_IN2ND6 = 287;           
        public static final  int FLD_IN2ND7 = 288;           
        public static final  int FLD_IN2ND8 = 289;           
        public static final  int FLD_IN2ND9 = 290;           
        public static final  int FLD_IN2ND10 = 291;           
        public static final  int FLD_IN2ND11 = 292;           
        public static final  int FLD_IN2ND12 = 293;            
        public static final  int FLD_IN2ND13 = 294;           
        public static final  int FLD_IN2ND14 = 295;           
        public static final  int FLD_IN2ND15 = 296;             
        public static final  int FLD_IN2ND16 = 297;           
        public static final  int FLD_IN2ND17 = 298;           
        public static final  int FLD_IN2ND18 = 299;           
        public static final  int FLD_IN2ND19 = 300;           
        public static final  int FLD_IN2ND20 = 301;           
        public static final  int FLD_IN2ND21 = 302;           
        public static final  int FLD_IN2ND22 = 303;           
        public static final  int FLD_IN2ND23 = 304;           
        public static final  int FLD_IN2ND24 = 305;           
        public static final  int FLD_IN2ND25 = 306;           
        public static final  int FLD_IN2ND26 = 307;           
        public static final  int FLD_IN2ND27 = 308;           
        public static final  int FLD_IN2ND28 = 309;           
        public static final  int FLD_IN2ND29 = 310;           
        public static final  int FLD_IN2ND30 = 311;           
        public static final  int FLD_IN2ND31 = 312;           
        
        public static final  int FLD_OUT1 = 313;                   
        public static final  int FLD_OUT2 = 314;                           
        public static final  int FLD_OUT3 = 315;                   
        public static final  int FLD_OUT4 = 316;                   
        public static final  int FLD_OUT5 = 317;                   
        public static final  int FLD_OUT6 = 318;                   
        public static final  int FLD_OUT7 = 319;                   
        public static final  int FLD_OUT8 = 320;                   
        public static final  int FLD_OUT9 = 321;                   
        public static final  int FLD_OUT10 = 322;                   
        public static final  int FLD_OUT11 = 323;                   
        public static final  int FLD_OUT12 = 324;                   
        public static final  int FLD_OUT13 = 325;                   
        public static final  int FLD_OUT14 = 326;                   
        public static final  int FLD_OUT15 = 327;                   
        public static final  int FLD_OUT16 = 328;                   
        public static final  int FLD_OUT17 = 329;                   
        public static final  int FLD_OUT18 = 330;                   
        public static final  int FLD_OUT19 = 331;                   
        public static final  int FLD_OUT20 = 332;                   
        public static final  int FLD_OUT21 = 333;                   
        public static final  int FLD_OUT22 = 334;                   
        public static final  int FLD_OUT23 = 335;                   
        public static final  int FLD_OUT24 = 336;                   
        public static final  int FLD_OUT25 = 337;                   
        public static final  int FLD_OUT26 = 338;                   
        public static final  int FLD_OUT27 = 339;                   
        public static final  int FLD_OUT28 = 340;                   
        public static final  int FLD_OUT29 = 341;                   
        public static final  int FLD_OUT30 = 342;                   
        public static final  int FLD_OUT31 = 343;                   
        public static final  int FLD_OUT2ND1 = 314;   
        public static final  int FLD_OUT2ND2 = 345;                   
        public static final  int FLD_OUT2ND3 = 346;                   
        public static final  int FLD_OUT2ND4 = 347;                   
        public static final  int FLD_OUT2ND5 = 348;                   
        public static final  int FLD_OUT2ND6 = 349;                   
        public static final  int FLD_OUT2ND7 = 350;                   
        public static final  int FLD_OUT2ND8 = 351;                   
        public static final  int FLD_OUT2ND9 = 352;                   
        public static final  int FLD_OUT2ND10 = 353;                   
        public static final  int FLD_OUT2ND11 = 354;                   
        public static final  int FLD_OUT2ND12 = 355;                   
        public static final  int FLD_OUT2ND13 = 356;                   
        public static final  int FLD_OUT2ND14 = 357;                   
        public static final  int FLD_OUT2ND15 = 358;                   
        public static final  int FLD_OUT2ND16 = 359;                   
        public static final  int FLD_OUT2ND17 = 360;                   
        public static final  int FLD_OUT2ND18 = 361;                   
        public static final  int FLD_OUT2ND19 = 362;                   
        public static final  int FLD_OUT2ND20 = 363;                   
        public static final  int FLD_OUT2ND21 = 364;                   
        public static final  int FLD_OUT2ND22 = 365;                   
        public static final  int FLD_OUT2ND23 = 366;                   
        public static final  int FLD_OUT2ND24 = 367;                   
        public static final  int FLD_OUT2ND25 = 368;                   
        public static final  int FLD_OUT2ND26 = 369;                   
        public static final  int FLD_OUT2ND27 = 370;                   
        public static final  int FLD_OUT2ND28 = 371;                   
        public static final  int FLD_OUT2ND29 = 372;                   
        public static final  int FLD_OUT2ND30 = 373;                   
        public static final  int FLD_OUT2ND31 = 374;                                           

        public static final  int FLD_SCHEDULE_TYPE = 375;

        
	public static final  String[] fieldNames = {
		"EMP_SCHEDULE_ID",
                "PERIOD_ID",
                "EMPLOYEE_ID",
		"D1",
                "D2",
                "D3",
                "D4",
                "D5",
                "D6",
                "D7",
                "D8",
                "D9",
                "D10",
		"D11",
                "D12",
                "D13",
                "D14",
                "D15",
                "D16",
                "D17",
                "D18",
                "D19",
                "D20",
		"D21",
                "D22",
                "D23",
                "D24",
                "D25",
                "D26",
                "D27",
                "D28",
                "D29",
                "D30",
                "D31",
                
                // add by Edhy for split shift
		"D2ND1",
                "D2ND2",
                "D2ND3",
                "D2ND4",
                "D2ND5",
                "D2ND6",
                "D2ND7",
                "D2ND8",
                "D2ND9",
                "D2ND10",
		"D2ND11",
                "D2ND12",
                "D2ND13",
                "D2ND14",
                "D2ND15",
                "D2ND16",
                "D2ND17",
                "D2ND18",
                "D2ND19",
                "D2ND20",
		"D2ND21",
                "D2ND22",
                "D2ND23",
                "D2ND24",
                "D2ND25",
                "D2ND26",
                "D2ND27",
                "D2ND28",
                "D2ND29",
                "D2ND30",
                "D2ND31",
		"STATUS1",
		"STATUS2",
		"STATUS3",
		"STATUS4",
		"STATUS5",
		"STATUS6",
		"STATUS7",
		"STATUS8",
		"STATUS9",
		"STATUS10",
		"STATUS11",
		"STATUS12",
		"STATUS13",
		"STATUS14",
		"STATUS15",
		"STATUS16",
		"STATUS17",
		"STATUS18",
		"STATUS19",
		"STATUS20",                
		"STATUS21",
		"STATUS22",
		"STATUS23",
		"STATUS24",
		"STATUS25",
		"STATUS26",
		"STATUS27",
		"STATUS28",
		"STATUS29",
		"STATUS30",                
                "STATUS31",                                
                "STATUS2ND1",                                
                "STATUS2ND2",                               
                "STATUS2ND3",                               
                "STATUS2ND4",                               
                "STATUS2ND5",                               
                "STATUS2ND6",                               
                "STATUS2ND7",                               
                "STATUS2ND8",                               
                "STATUS2ND9",                               
                "STATUS2ND10",                               
                "STATUS2ND11",                                
                "STATUS2ND12",                               
                "STATUS2ND13",                               
                "STATUS2ND14",                               
                "STATUS2ND15",                               
                "STATUS2ND16",                               
                "STATUS2ND17",                               
                "STATUS2ND18",                               
                "STATUS2ND19",                               
                "STATUS2ND20",                               
                "STATUS2ND21",                                
                "STATUS2ND22",                               
                "STATUS2ND23",                               
                "STATUS2ND24",                               
                "STATUS2ND25",                               
                "STATUS2ND26",                               
                "STATUS2ND27",                               
                "STATUS2ND28",                               
                "STATUS2ND29",                               
                "STATUS2ND30",                               
                "STATUS2ND31",                                               
                "REASON1",                               
                "REASON2",                               
                "REASON3",                               
                "REASON4",                               
                "REASON5",                               
                "REASON6",                               
                "REASON7",                               
                "REASON8",                               
                "REASON9",                               
                "REASON10",                               
                "REASON11",                               
                "REASON12",                               
                "REASON13",                               
                "REASON14",                               
                "REASON15",                               
                "REASON16",                               
                "REASON17",                               
                "REASON18",                               
                "REASON19",                               
                "REASON20",                               
                "REASON21",                               
                "REASON22",                               
                "REASON23",                               
                "REASON24",                               
                "REASON25",                               
                "REASON26",                               
                "REASON27",                               
                "REASON28",                               
                "REASON29",                               
                "REASON30",                               
                "REASON31",                                               
                "REASON2ND1",                               
                "REASON2ND2",                               
                "REASON2ND3",                               
                "REASON2ND4",                               
                "REASON2ND5",                               
                "REASON2ND6",                               
                "REASON2ND7",                               
                "REASON2ND8",                               
                "REASON2ND9",                               
                "REASON2ND10",                               
                "REASON2ND11",                               
                "REASON2ND12",                               
                "REASON2ND13",                               
                "REASON2ND14",                               
                "REASON2ND15",                               
                "REASON2ND16",                               
                "REASON2ND17",                               
                "REASON2ND18",                               
                "REASON2ND19",                               
                "REASON2ND20",                               
                "REASON2ND21",                               
                "REASON2ND22",                               
                "REASON2ND23",                               
                "REASON2ND24",                               
                "REASON2ND25",                               
                "REASON2ND26",                               
                "REASON2ND27",                               
                "REASON2ND28",                               
                "REASON2ND29",                               
                "REASON2ND30",                               
                "REASON2ND31",                               
                "NOTE1",                               
                "NOTE2",                               
                "NOTE3",                               
                "NOTE4",                               
                "NOTE5",                               
                "NOTE6",                               
                "NOTE7",                               
                "NOTE8",                               
                "NOTE9",                               
                "NOTE10",                               
                "NOTE11",                               
                "NOTE12",                               
                "NOTE13",                               
                "NOTE14",                               
                "NOTE15",                               
                "NOTE16",                               
                "NOTE17",                               
                "NOTE18",                               
                "NOTE19",                               
                "NOTE20",                               
                "NOTE21",                               
                "NOTE22",                               
                "NOTE23",                               
                "NOTE24",                               
                "NOTE25",                               
                "NOTE26",                               
                "NOTE27",                               
                "NOTE28",                               
                "NOTE29",                               
                "NOTE30",                               
                "NOTE31",                               
                "NOTE2ND1",                               
                "NOTE2ND2",                               
                "NOTE2ND3",                               
                "NOTE2ND4",                               
                "NOTE2ND5",                               
                "NOTE2ND6",                               
                "NOTE2ND7",                               
                "NOTE2ND8",                               
                "NOTE2ND9",                               
                "NOTE2ND10",                               
                "NOTE2ND11",                               
                "NOTE2ND12",                               
                "NOTE2ND13",                               
                "NOTE2ND14",                               
                "NOTE2ND15",                               
                "NOTE2ND16",                               
                "NOTE2ND17",                               
                "NOTE2ND18",                               
                "NOTE2ND19",                               
                "NOTE2ND20",                               
                "NOTE2ND21",                               
                "NOTE2ND22",                               
                "NOTE2ND23",                               
                "NOTE2ND24",                               
                "NOTE2ND25",                               
                "NOTE2ND26",                               
                "NOTE2ND27",                               
                "NOTE2ND28",                               
                "NOTE2ND29",                               
                "NOTE2ND30",                               
                "NOTE2ND31",                               
                "IN1",                               
                "IN2",                               
                "IN3",                               
                "IN4",                               
                "IN5",                               
                "IN6",                               
                "IN7",                               
                "IN8",                               
                "IN9",                               
                "IN10",                               
                "IN11",                               
                "IN12",                               
                "IN13",                               
                "IN14",                               
                "IN15",                               
                "IN16",                               
                "IN17",                               
                "IN18",                               
                "IN19",                               
                "IN20",                               
                "IN21",                               
                "IN22",                               
                "IN23",                               
                "IN24",                               
                "IN25",                               
                "IN26",                               
                "IN27",                               
                "IN28",                               
                "IN29",                               
                "IN30",                               
                "IN31",                               
                "IN2ND1",                               
                "IN2ND2",                               
                "IN2ND3",                               
                "IN2ND4",                               
                "IN2ND5",                               
                "IN2ND6",                               
                "IN2ND7",                               
                "IN2ND8",                               
                "IN2ND9",                               
                "IN2ND10",                               
                "IN2ND11",                               
                "IN2ND12",                               
                "IN2ND13",                               
                "IN2ND14",                               
                "IN2ND15",                               
                "IN2ND16",                               
                "IN2ND17",                               
                "IN2ND18",                               
                "IN2ND19",                               
                "IN2ND20",                               
                "IN2ND21",                               
                "IN2ND22",                               
                "IN2ND23",                               
                "IN2ND24",                               
                "IN2ND25",                               
                "IN2ND26",                               
                "IN2ND27",                               
                "IN2ND28",                               
                "IN2ND29",                               
                "IN2ND30",                               
                "IN2ND31",                               
                "OUT1",                               
                "OUT2",                               
                "OUT3",                               
                "OUT4",                               
                "OUT5",                               
                "OUT6",                               
                "OUT7",                               
                "OUT8",                               
                "OUT9",                               
                "OUT10",                               
                "OUT11",                               
                "OUT12",                               
                "OUT13",                               
                "OUT14",                               
                "OUT15",                               
                "OUT16",                               
                "OUT17",                               
                "OUT18",                               
                "OUT19",                               
                "OUT20",                               
                "OUT21",                               
                "OUT22",                               
                "OUT23",                               
                "OUT24",                               
                "OUT25",                               
                "OUT26",                               
                "OUT27",                               
                "OUT28",                               
                "OUT29",                               
                "OUT30",                               
                "OUT31",                               
                "OUT2ND1",                               
                "OUT2ND2",                               
                "OUT2ND3",                               
                "OUT2ND4",                               
                "OUT2ND5",                               
                "OUT2ND6",                               
                "OUT2ND7",                               
                "OUT2ND8",                               
                "OUT2ND9",                               
                "OUT2ND10",                               
                "OUT2ND11",                               
                "OUT2ND12",                               
                "OUT2ND13",                               
                "OUT2ND14",                               
                "OUT2ND15",                               
                "OUT2ND16",                               
                "OUT2ND17",                               
                "OUT2ND18",                               
                "OUT2ND19",                               
                "OUT2ND20",                               
                "OUT2ND21",                               
                "OUT2ND22",                               
                "OUT2ND23",                               
                "OUT2ND24",                               
                "OUT2ND25",                               
                "OUT2ND26",                               
                "OUT2ND27",                               
                "OUT2ND28",                               
                "OUT2ND29",                               
                "OUT2ND30",                               
                "OUT2ND31",
                "SCHEDULE_TYPE"
	 }; 

	public static final  int[] fieldTypes = {
		TYPE_LONG + TYPE_PK + TYPE_ID,
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
                
                // added by edhy for split shft
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
                TYPE_STRING,
                
                TYPE_DATE,
                TYPE_DATE,
                TYPE_DATE,
                TYPE_DATE,
                TYPE_DATE,
                TYPE_DATE,
                TYPE_DATE,
                TYPE_DATE,
                TYPE_DATE,
                TYPE_DATE,
                TYPE_DATE,
                TYPE_DATE,
                TYPE_DATE,
                TYPE_DATE,
                TYPE_DATE,
                TYPE_DATE,
                TYPE_DATE,
                TYPE_DATE,
                TYPE_DATE,
                TYPE_DATE,
                TYPE_DATE,
                TYPE_DATE,
                TYPE_DATE,
                TYPE_DATE,
                TYPE_DATE,
                TYPE_DATE,
                TYPE_DATE,
                TYPE_DATE,
                TYPE_DATE,
                TYPE_DATE,
                TYPE_DATE,
                TYPE_DATE,
                TYPE_DATE,
                TYPE_DATE,
                TYPE_DATE,
                TYPE_DATE,
                TYPE_DATE,
                TYPE_DATE,
                TYPE_DATE,
                TYPE_DATE,
                TYPE_DATE,
                TYPE_DATE,
                TYPE_DATE,
                TYPE_DATE,
                TYPE_DATE,
                TYPE_DATE,
                TYPE_DATE,
                TYPE_DATE,
                TYPE_DATE,
                TYPE_DATE,
                TYPE_DATE,
                TYPE_DATE,
                TYPE_DATE,
                TYPE_DATE,
                TYPE_DATE,
                TYPE_DATE,
                TYPE_DATE,
                TYPE_DATE,
                TYPE_DATE,
                TYPE_DATE,
                TYPE_DATE,
                TYPE_DATE,

                
                TYPE_DATE,
                TYPE_DATE,
                TYPE_DATE,
                TYPE_DATE,
                TYPE_DATE,
                TYPE_DATE,
                TYPE_DATE,
                TYPE_DATE,
                TYPE_DATE,
                TYPE_DATE,
                TYPE_DATE,
                TYPE_DATE,
                TYPE_DATE,
                TYPE_DATE,
                TYPE_DATE,
                TYPE_DATE,
                TYPE_DATE,
                TYPE_DATE,
                TYPE_DATE,
                TYPE_DATE,
                TYPE_DATE,
                TYPE_DATE,
                TYPE_DATE,
                TYPE_DATE,
                TYPE_DATE,
                TYPE_DATE,
                TYPE_DATE,
                TYPE_DATE,
                TYPE_DATE,
                TYPE_DATE,
                TYPE_DATE,
                TYPE_DATE,
                TYPE_DATE,
                TYPE_DATE,
                TYPE_DATE,
                TYPE_DATE,
                TYPE_DATE,
                TYPE_DATE,
                TYPE_DATE,
                TYPE_DATE,
                TYPE_DATE,
                TYPE_DATE,
                TYPE_DATE,
                TYPE_DATE,
                TYPE_DATE,
                TYPE_DATE,
                TYPE_DATE,
                TYPE_DATE,
                TYPE_DATE,
                TYPE_DATE,
                TYPE_DATE,
                TYPE_DATE,
                TYPE_DATE,
                TYPE_DATE,
                TYPE_DATE,
                TYPE_DATE,
                TYPE_DATE,
                TYPE_DATE,
                TYPE_DATE,
                TYPE_DATE,
                TYPE_DATE,
                TYPE_DATE,
                TYPE_INT
	 };

 	public static final String FLD_SCHEDULE = "D";

        
        // property for setting this schedule
        public static final int FIELD_COUNT_BEFORE_CALENDAR = 2;        
        public static final int INTERVAL_INDEX_HALF_CALENDAR = 31; 
        public static final int INTERVAL_INDEX_FULL_CALENDAR = 62; 
        public static final int PRESENCE_CORRECTION_TIME = 60; // in seconds
        
        public static final int OFFSET_INDEX_CALENDAR = 3; // dimulai dari 0        
        public static final int OFFSET_INDEX_STATUS = 65; // dimulai dari 0  
        public static final int OFFSET_INDEX_REASON = 127; // dimulai dari 0  
        public static final int OFFSET_INDEX_NOTE = 189; // dimulai dari 0        
        public static final int OFFSET_INDEX_IN = 251; // dimulai dari 0        
        public static final int OFFSET_INDEX_OUT = 313; // dimulai dari 0        
        
        // presence status compare with schedule
        public static final int PRESENCE_OK = 0;
        public static final int PRESENCE_LATE = 1;
        public static final int ABSENCE_ALPHA = 2;
        public static final int ABSENCE_SICKNESS = 3;
        public static final int ABSENCE_DISPENSATION = 4;
        public static final String[] strPresenceStatus  = {
            "Ok",
            "Late",
            "Absence",
            "Sick",
            "Special Dispensation",
        };   
        
        // presence type base on command on timekeeping machine
        public static final int STATUS_IN = 0;
        public static final int STATUS_OUT_HOME = 1;
        public static final int STATUS_OUT_ON_DUTY = 2;
        public static final int STATUS_IN_LUNCH = 3;
        public static final int STATUS_IN_BREAK = 4;
        public static final int STATUS_IN_CALLBACK = 5;

        public static final String[] strPresenceStatusName  = {
            "In",
            "Out - Home",
            "Out - On Duty",
            "In - Lunch",
            "In - Break",
            "In - Callback"
        };   

        public static final int[] strPresenceStatusValue  = {
            0,
            1,
            2,
            3,
            4,
            5
        };   

        // schedule type
        public static final int SCHEDULE_ORIGINAL = 0;
        public static final int SCHEDULE_UPDATED = 1;
        public static final String[] strScheduleType  = {
            "Schedule Orinigal",
            "Schedule Updated",
        };           
        
        public PstEmpScheduleOrg(){
	}

	public PstEmpScheduleOrg(int i) throws DBException { 
		super(new PstEmpScheduleOrg()); 
	}

	public PstEmpScheduleOrg(String sOid) throws DBException { 
		super(new PstEmpScheduleOrg(0)); 
		if(!locate(sOid)) 
			throw new DBException(this,DBException.RECORD_NOT_FOUND); 
		else 
			return; 
	}

	public PstEmpScheduleOrg(long lOid) throws DBException { 
		super(new PstEmpScheduleOrg(0)); 
		String sOid = "0"; 
		try { 
			sOid = String.valueOf(lOid); 
		}catch(Exception e) { 
			throw new DBException(this,DBException.RECORD_NOT_FOUND); 
		} 
		if(!locate(sOid)) 
			throw new DBException(this,DBException.RECORD_NOT_FOUND); 
		else 
			return; 
	} 

	public int getFieldSize(){ 
		return fieldNames.length; 
	}

	public String getTableName(){ 
		return TBL_HR_EMP_SCHEDULE;
	}

	public String[] getFieldNames(){ 
		return fieldNames; 
	}

	public int[] getFieldTypes(){ 
		return fieldTypes; 
	}

	public String getPersistentName(){ 
		return new PstEmpScheduleOrg().getClass().getName(); 
	}

	public long fetchExc(Entity ent) throws Exception{ 
		EmpSchedule empschedule = fetchExc(ent.getOID()); 
		ent = (Entity)empschedule; 
		return empschedule.getOID(); 
	}

	public long insertExc(Entity ent) throws Exception{ 
		return insertExc((EmpSchedule) ent); 
	}

	public long updateExc(Entity ent) throws Exception{ 
		return updateExc((EmpSchedule) ent); 
	}

	public long deleteExc(Entity ent) throws Exception{ 
		if(ent==null){ 
			throw new DBException(this,DBException.RECORD_NOT_FOUND); 
		} 
		return deleteExc(ent.getOID()); 
	}

	public static EmpSchedule fetchExc(long oid) throws DBException{ 
		try{ 
			EmpSchedule empschedule = new EmpSchedule();
			PstEmpScheduleOrg pstEmpSchedule = new PstEmpScheduleOrg(oid); 
			empschedule.setOID(oid);

			empschedule.setPeriodId(pstEmpSchedule.getlong(FLD_PERIOD_ID));
			empschedule.setEmployeeId(pstEmpSchedule.getlong(FLD_EMPLOYEE_ID));
			empschedule.setD1(pstEmpSchedule.getlong(FLD_D1));
			empschedule.setD2(pstEmpSchedule.getlong(FLD_D2));
			empschedule.setD3(pstEmpSchedule.getlong(FLD_D3));
			empschedule.setD4(pstEmpSchedule.getlong(FLD_D4));
			empschedule.setD5(pstEmpSchedule.getlong(FLD_D5));
			empschedule.setD6(pstEmpSchedule.getlong(FLD_D6));
			empschedule.setD7(pstEmpSchedule.getlong(FLD_D7));
			empschedule.setD8(pstEmpSchedule.getlong(FLD_D8));
			empschedule.setD9(pstEmpSchedule.getlong(FLD_D9));
			empschedule.setD10(pstEmpSchedule.getlong(FLD_D10));
			empschedule.setD11(pstEmpSchedule.getlong(FLD_D11));
			empschedule.setD12(pstEmpSchedule.getlong(FLD_D12));
			empschedule.setD13(pstEmpSchedule.getlong(FLD_D13));
			empschedule.setD14(pstEmpSchedule.getlong(FLD_D14));
			empschedule.setD15(pstEmpSchedule.getlong(FLD_D15));
			empschedule.setD16(pstEmpSchedule.getlong(FLD_D16));
			empschedule.setD17(pstEmpSchedule.getlong(FLD_D17));
			empschedule.setD18(pstEmpSchedule.getlong(FLD_D18));
			empschedule.setD19(pstEmpSchedule.getlong(FLD_D19));
			empschedule.setD20(pstEmpSchedule.getlong(FLD_D20));
			empschedule.setD21(pstEmpSchedule.getlong(FLD_D21));
			empschedule.setD22(pstEmpSchedule.getlong(FLD_D22));
			empschedule.setD23(pstEmpSchedule.getlong(FLD_D23));
			empschedule.setD24(pstEmpSchedule.getlong(FLD_D24));
			empschedule.setD25(pstEmpSchedule.getlong(FLD_D25));
			empschedule.setD26(pstEmpSchedule.getlong(FLD_D26));
			empschedule.setD27(pstEmpSchedule.getlong(FLD_D27));
			empschedule.setD28(pstEmpSchedule.getlong(FLD_D28));
			empschedule.setD29(pstEmpSchedule.getlong(FLD_D29));
			empschedule.setD30(pstEmpSchedule.getlong(FLD_D30));
			empschedule.setD31(pstEmpSchedule.getlong(FLD_D31));

                        // added by edhy for split shift
			empschedule.setD2nd1(pstEmpSchedule.getlong(FLD_D2ND1));
			empschedule.setD2nd2(pstEmpSchedule.getlong(FLD_D2ND2));
			empschedule.setD2nd3(pstEmpSchedule.getlong(FLD_D2ND3));
			empschedule.setD2nd4(pstEmpSchedule.getlong(FLD_D2ND4));
			empschedule.setD2nd5(pstEmpSchedule.getlong(FLD_D2ND5));
			empschedule.setD2nd6(pstEmpSchedule.getlong(FLD_D2ND6));
			empschedule.setD2nd7(pstEmpSchedule.getlong(FLD_D2ND7));
			empschedule.setD2nd8(pstEmpSchedule.getlong(FLD_D2ND8));
			empschedule.setD2nd9(pstEmpSchedule.getlong(FLD_D2ND9));
			empschedule.setD2nd10(pstEmpSchedule.getlong(FLD_D2ND10));
			empschedule.setD2nd11(pstEmpSchedule.getlong(FLD_D2ND11));
			empschedule.setD2nd12(pstEmpSchedule.getlong(FLD_D2ND12));
			empschedule.setD2nd13(pstEmpSchedule.getlong(FLD_D2ND13));
			empschedule.setD2nd14(pstEmpSchedule.getlong(FLD_D2ND14));
			empschedule.setD2nd15(pstEmpSchedule.getlong(FLD_D2ND15));
			empschedule.setD2nd16(pstEmpSchedule.getlong(FLD_D2ND16));
			empschedule.setD2nd17(pstEmpSchedule.getlong(FLD_D2ND17));
			empschedule.setD2nd18(pstEmpSchedule.getlong(FLD_D2ND18));
			empschedule.setD2nd19(pstEmpSchedule.getlong(FLD_D2ND19));
			empschedule.setD2nd20(pstEmpSchedule.getlong(FLD_D2ND20));
			empschedule.setD2nd21(pstEmpSchedule.getlong(FLD_D2ND21));
			empschedule.setD2nd22(pstEmpSchedule.getlong(FLD_D2ND22));
			empschedule.setD2nd23(pstEmpSchedule.getlong(FLD_D2ND23));
			empschedule.setD2nd24(pstEmpSchedule.getlong(FLD_D2ND24));
			empschedule.setD2nd25(pstEmpSchedule.getlong(FLD_D2ND25));
			empschedule.setD2nd26(pstEmpSchedule.getlong(FLD_D2ND26));
			empschedule.setD2nd27(pstEmpSchedule.getlong(FLD_D2ND27));
			empschedule.setD2nd28(pstEmpSchedule.getlong(FLD_D2ND28));
			empschedule.setD2nd29(pstEmpSchedule.getlong(FLD_D2ND29));
			empschedule.setD2nd30(pstEmpSchedule.getlong(FLD_D2ND30));
			empschedule.setD2nd31(pstEmpSchedule.getlong(FLD_D2ND31));
                        
                        empschedule.setStatus1(pstEmpSchedule.getInt(FLD_STATUS1));
                        empschedule.setStatus2(pstEmpSchedule.getInt(FLD_STATUS2));
                        empschedule.setStatus3(pstEmpSchedule.getInt(FLD_STATUS3));
                        empschedule.setStatus4(pstEmpSchedule.getInt(FLD_STATUS4));
                        empschedule.setStatus5(pstEmpSchedule.getInt(FLD_STATUS5));
                        empschedule.setStatus6(pstEmpSchedule.getInt(FLD_STATUS6));
                        empschedule.setStatus7(pstEmpSchedule.getInt(FLD_STATUS7));
                        empschedule.setStatus8(pstEmpSchedule.getInt(FLD_STATUS8));
                        empschedule.setStatus9(pstEmpSchedule.getInt(FLD_STATUS9));
                        empschedule.setStatus10(pstEmpSchedule.getInt(FLD_STATUS10));
                        empschedule.setStatus11(pstEmpSchedule.getInt(FLD_STATUS11));
                        empschedule.setStatus12(pstEmpSchedule.getInt(FLD_STATUS12));
                        empschedule.setStatus13(pstEmpSchedule.getInt(FLD_STATUS13));
                        empschedule.setStatus14(pstEmpSchedule.getInt(FLD_STATUS14));
                        empschedule.setStatus15(pstEmpSchedule.getInt(FLD_STATUS15));
                        empschedule.setStatus16(pstEmpSchedule.getInt(FLD_STATUS16));
                        empschedule.setStatus17(pstEmpSchedule.getInt(FLD_STATUS17));
                        empschedule.setStatus18(pstEmpSchedule.getInt(FLD_STATUS18));
                        empschedule.setStatus19(pstEmpSchedule.getInt(FLD_STATUS19));
                        empschedule.setStatus20(pstEmpSchedule.getInt(FLD_STATUS20));
                        empschedule.setStatus21(pstEmpSchedule.getInt(FLD_STATUS21));
                        empschedule.setStatus22(pstEmpSchedule.getInt(FLD_STATUS22));
                        empschedule.setStatus23(pstEmpSchedule.getInt(FLD_STATUS23));
                        empschedule.setStatus24(pstEmpSchedule.getInt(FLD_STATUS24));
                        empschedule.setStatus25(pstEmpSchedule.getInt(FLD_STATUS25));
                        empschedule.setStatus26(pstEmpSchedule.getInt(FLD_STATUS26));
                        empschedule.setStatus27(pstEmpSchedule.getInt(FLD_STATUS27));
                        empschedule.setStatus28(pstEmpSchedule.getInt(FLD_STATUS28));
                        empschedule.setStatus29(pstEmpSchedule.getInt(FLD_STATUS29));
                        empschedule.setStatus30(pstEmpSchedule.getInt(FLD_STATUS30));
                        empschedule.setStatus31(pstEmpSchedule.getInt(FLD_STATUS31));
                        empschedule.setStatus2nd1(pstEmpSchedule.getInt(FLD_STATUS2ND1));
                        empschedule.setStatus2nd2(pstEmpSchedule.getInt(FLD_STATUS2ND2));
                        empschedule.setStatus2nd3(pstEmpSchedule.getInt(FLD_STATUS2ND3));
                        empschedule.setStatus2nd4(pstEmpSchedule.getInt(FLD_STATUS2ND4));
                        empschedule.setStatus2nd5(pstEmpSchedule.getInt(FLD_STATUS2ND5));
                        empschedule.setStatus2nd6(pstEmpSchedule.getInt(FLD_STATUS2ND6));
                        empschedule.setStatus2nd7(pstEmpSchedule.getInt(FLD_STATUS2ND7));
                        empschedule.setStatus2nd8(pstEmpSchedule.getInt(FLD_STATUS2ND8));
                        empschedule.setStatus2nd9(pstEmpSchedule.getInt(FLD_STATUS2ND9));
                        empschedule.setStatus2nd10(pstEmpSchedule.getInt(FLD_STATUS2ND10));
                        empschedule.setStatus2nd11(pstEmpSchedule.getInt(FLD_STATUS2ND11));
                        empschedule.setStatus2nd12(pstEmpSchedule.getInt(FLD_STATUS2ND12));
                        empschedule.setStatus2nd13(pstEmpSchedule.getInt(FLD_STATUS2ND13));
                        empschedule.setStatus2nd14(pstEmpSchedule.getInt(FLD_STATUS2ND14));
                        empschedule.setStatus2nd15(pstEmpSchedule.getInt(FLD_STATUS2ND15));
                        empschedule.setStatus2nd16(pstEmpSchedule.getInt(FLD_STATUS2ND16));
                        empschedule.setStatus2nd17(pstEmpSchedule.getInt(FLD_STATUS2ND17));
                        empschedule.setStatus2nd18(pstEmpSchedule.getInt(FLD_STATUS2ND18));
                        empschedule.setStatus2nd19(pstEmpSchedule.getInt(FLD_STATUS2ND19));
                        empschedule.setStatus2nd20(pstEmpSchedule.getInt(FLD_STATUS2ND20));
                        empschedule.setStatus2nd21(pstEmpSchedule.getInt(FLD_STATUS2ND21));
                        empschedule.setStatus2nd22(pstEmpSchedule.getInt(FLD_STATUS2ND22));
                        empschedule.setStatus2nd23(pstEmpSchedule.getInt(FLD_STATUS2ND23));
                        empschedule.setStatus2nd24(pstEmpSchedule.getInt(FLD_STATUS2ND24));
                        empschedule.setStatus2nd25(pstEmpSchedule.getInt(FLD_STATUS2ND25));
                        empschedule.setStatus2nd26(pstEmpSchedule.getInt(FLD_STATUS2ND26));
                        empschedule.setStatus2nd27(pstEmpSchedule.getInt(FLD_STATUS2ND27));
                        empschedule.setStatus2nd28(pstEmpSchedule.getInt(FLD_STATUS2ND28));
                        empschedule.setStatus2nd29(pstEmpSchedule.getInt(FLD_STATUS2ND29));
                        empschedule.setStatus2nd30(pstEmpSchedule.getInt(FLD_STATUS2ND30));
                        empschedule.setStatus2nd31(pstEmpSchedule.getInt(FLD_STATUS2ND31));
                        
                        empschedule.setReason1(pstEmpSchedule.getInt(FLD_REASON1));
                        empschedule.setReason2(pstEmpSchedule.getInt(FLD_REASON2));
                        empschedule.setReason3(pstEmpSchedule.getInt(FLD_REASON3));
                        empschedule.setReason4(pstEmpSchedule.getInt(FLD_REASON4));
                        empschedule.setReason5(pstEmpSchedule.getInt(FLD_REASON5));
                        empschedule.setReason6(pstEmpSchedule.getInt(FLD_REASON6));
                        empschedule.setReason7(pstEmpSchedule.getInt(FLD_REASON7));
                        empschedule.setReason8(pstEmpSchedule.getInt(FLD_REASON8));
                        empschedule.setReason9(pstEmpSchedule.getInt(FLD_REASON9));
                        empschedule.setReason10(pstEmpSchedule.getInt(FLD_REASON10));
                        empschedule.setReason11(pstEmpSchedule.getInt(FLD_REASON11));
                        empschedule.setReason12(pstEmpSchedule.getInt(FLD_REASON12));
                        empschedule.setReason13(pstEmpSchedule.getInt(FLD_REASON13));
                        empschedule.setReason14(pstEmpSchedule.getInt(FLD_REASON14));
                        empschedule.setReason15(pstEmpSchedule.getInt(FLD_REASON15));
                        empschedule.setReason16(pstEmpSchedule.getInt(FLD_REASON16));
                        empschedule.setReason17(pstEmpSchedule.getInt(FLD_REASON17));
                        empschedule.setReason18(pstEmpSchedule.getInt(FLD_REASON18));
                        empschedule.setReason19(pstEmpSchedule.getInt(FLD_REASON19));
                        empschedule.setReason20(pstEmpSchedule.getInt(FLD_REASON20));
                        empschedule.setReason21(pstEmpSchedule.getInt(FLD_REASON21));
                        empschedule.setReason22(pstEmpSchedule.getInt(FLD_REASON22));
                        empschedule.setReason23(pstEmpSchedule.getInt(FLD_REASON23));
                        empschedule.setReason24(pstEmpSchedule.getInt(FLD_REASON24));
                        empschedule.setReason25(pstEmpSchedule.getInt(FLD_REASON25));
                        empschedule.setReason26(pstEmpSchedule.getInt(FLD_REASON26));
                        empschedule.setReason27(pstEmpSchedule.getInt(FLD_REASON27));
                        empschedule.setReason28(pstEmpSchedule.getInt(FLD_REASON28));
                        empschedule.setReason29(pstEmpSchedule.getInt(FLD_REASON29));
                        empschedule.setReason30(pstEmpSchedule.getInt(FLD_REASON30));
                        empschedule.setReason31(pstEmpSchedule.getInt(FLD_REASON31));
                        empschedule.setReason2nd1(pstEmpSchedule.getInt(FLD_REASON2ND1));
                        empschedule.setReason2nd2(pstEmpSchedule.getInt(FLD_REASON2ND2));
                        empschedule.setReason2nd3(pstEmpSchedule.getInt(FLD_REASON2ND3));
                        empschedule.setReason2nd4(pstEmpSchedule.getInt(FLD_REASON2ND4));
                        empschedule.setReason2nd5(pstEmpSchedule.getInt(FLD_REASON2ND5));
                        empschedule.setReason2nd6(pstEmpSchedule.getInt(FLD_REASON2ND6));
                        empschedule.setReason2nd7(pstEmpSchedule.getInt(FLD_REASON2ND7));
                        empschedule.setReason2nd8(pstEmpSchedule.getInt(FLD_REASON2ND8));
                        empschedule.setReason2nd9(pstEmpSchedule.getInt(FLD_REASON2ND9));
                        empschedule.setReason2nd10(pstEmpSchedule.getInt(FLD_REASON2ND10));
                        empschedule.setReason2nd11(pstEmpSchedule.getInt(FLD_REASON2ND11));
                        empschedule.setReason2nd12(pstEmpSchedule.getInt(FLD_REASON2ND12));
                        empschedule.setReason2nd13(pstEmpSchedule.getInt(FLD_REASON2ND13));
                        empschedule.setReason2nd14(pstEmpSchedule.getInt(FLD_REASON2ND14));
                        empschedule.setReason2nd15(pstEmpSchedule.getInt(FLD_REASON2ND15));
                        empschedule.setReason2nd16(pstEmpSchedule.getInt(FLD_REASON2ND16));
                        empschedule.setReason2nd17(pstEmpSchedule.getInt(FLD_REASON2ND17));
                        empschedule.setReason2nd18(pstEmpSchedule.getInt(FLD_REASON2ND18));
                        empschedule.setReason2nd19(pstEmpSchedule.getInt(FLD_REASON2ND19));
                        empschedule.setReason2nd20(pstEmpSchedule.getInt(FLD_REASON2ND20));                        
                        empschedule.setReason2nd21(pstEmpSchedule.getInt(FLD_REASON2ND21));
                        empschedule.setReason2nd22(pstEmpSchedule.getInt(FLD_REASON2ND22));
                        empschedule.setReason2nd23(pstEmpSchedule.getInt(FLD_REASON2ND23));
                        empschedule.setReason2nd24(pstEmpSchedule.getInt(FLD_REASON2ND24));
                        empschedule.setReason2nd25(pstEmpSchedule.getInt(FLD_REASON2ND25));
                        empschedule.setReason2nd26(pstEmpSchedule.getInt(FLD_REASON2ND26));
                        empschedule.setReason2nd27(pstEmpSchedule.getInt(FLD_REASON2ND27));
                        empschedule.setReason2nd28(pstEmpSchedule.getInt(FLD_REASON2ND28));
                        empschedule.setReason2nd29(pstEmpSchedule.getInt(FLD_REASON2ND29));
                        empschedule.setReason2nd30(pstEmpSchedule.getInt(FLD_REASON2ND30));                        
                        empschedule.setReason2nd31(pstEmpSchedule.getInt(FLD_REASON2ND31));                        
                        
                        empschedule.setNote1(pstEmpSchedule.getString(FLD_NOTE1));
                        empschedule.setNote2(pstEmpSchedule.getString(FLD_NOTE2));
                        empschedule.setNote3(pstEmpSchedule.getString(FLD_NOTE3));
                        empschedule.setNote4(pstEmpSchedule.getString(FLD_NOTE4));
                        empschedule.setNote5(pstEmpSchedule.getString(FLD_NOTE5));
                        empschedule.setNote6(pstEmpSchedule.getString(FLD_NOTE6));
                        empschedule.setNote7(pstEmpSchedule.getString(FLD_NOTE7));
                        empschedule.setNote8(pstEmpSchedule.getString(FLD_NOTE8));
                        empschedule.setNote9(pstEmpSchedule.getString(FLD_NOTE9));
                        empschedule.setNote10(pstEmpSchedule.getString(FLD_NOTE10));
                        empschedule.setNote11(pstEmpSchedule.getString(FLD_NOTE11));
                        empschedule.setNote12(pstEmpSchedule.getString(FLD_NOTE12));
                        empschedule.setNote13(pstEmpSchedule.getString(FLD_NOTE13));
                        empschedule.setNote14(pstEmpSchedule.getString(FLD_NOTE14));
                        empschedule.setNote15(pstEmpSchedule.getString(FLD_NOTE15));
                        empschedule.setNote16(pstEmpSchedule.getString(FLD_NOTE16));
                        empschedule.setNote17(pstEmpSchedule.getString(FLD_NOTE17));
                        empschedule.setNote18(pstEmpSchedule.getString(FLD_NOTE18));
                        empschedule.setNote19(pstEmpSchedule.getString(FLD_NOTE19));
                        empschedule.setNote20(pstEmpSchedule.getString(FLD_NOTE20));                        
                        empschedule.setNote21(pstEmpSchedule.getString(FLD_NOTE21));
                        empschedule.setNote22(pstEmpSchedule.getString(FLD_NOTE22));
                        empschedule.setNote23(pstEmpSchedule.getString(FLD_NOTE23));
                        empschedule.setNote24(pstEmpSchedule.getString(FLD_NOTE24));
                        empschedule.setNote25(pstEmpSchedule.getString(FLD_NOTE25));
                        empschedule.setNote26(pstEmpSchedule.getString(FLD_NOTE26));
                        empschedule.setNote27(pstEmpSchedule.getString(FLD_NOTE27));
                        empschedule.setNote28(pstEmpSchedule.getString(FLD_NOTE28));
                        empschedule.setNote29(pstEmpSchedule.getString(FLD_NOTE29));
                        empschedule.setNote30(pstEmpSchedule.getString(FLD_NOTE30));                        
                        empschedule.setNote31(pstEmpSchedule.getString(FLD_NOTE31));
                        empschedule.setNote2nd1(pstEmpSchedule.getString(FLD_NOTE2ND1));
                        empschedule.setNote2nd2(pstEmpSchedule.getString(FLD_NOTE2ND2));
                        empschedule.setNote2nd3(pstEmpSchedule.getString(FLD_NOTE2ND3));
                        empschedule.setNote2nd4(pstEmpSchedule.getString(FLD_NOTE2ND4));
                        empschedule.setNote2nd5(pstEmpSchedule.getString(FLD_NOTE2ND5));
                        empschedule.setNote2nd6(pstEmpSchedule.getString(FLD_NOTE2ND6));
                        empschedule.setNote2nd7(pstEmpSchedule.getString(FLD_NOTE2ND7));
                        empschedule.setNote2nd8(pstEmpSchedule.getString(FLD_NOTE2ND8));
                        empschedule.setNote2nd9(pstEmpSchedule.getString(FLD_NOTE2ND9));
                        empschedule.setNote2nd10(pstEmpSchedule.getString(FLD_NOTE2ND10));
                        empschedule.setNote2nd11(pstEmpSchedule.getString(FLD_NOTE2ND11));
                        empschedule.setNote2nd12(pstEmpSchedule.getString(FLD_NOTE2ND12));
                        empschedule.setNote2nd13(pstEmpSchedule.getString(FLD_NOTE2ND13));
                        empschedule.setNote2nd14(pstEmpSchedule.getString(FLD_NOTE2ND14));
                        empschedule.setNote2nd15(pstEmpSchedule.getString(FLD_NOTE2ND15));
                        empschedule.setNote2nd16(pstEmpSchedule.getString(FLD_NOTE2ND16));
                        empschedule.setNote2nd17(pstEmpSchedule.getString(FLD_NOTE2ND17));
                        empschedule.setNote2nd18(pstEmpSchedule.getString(FLD_NOTE2ND18));
                        empschedule.setNote2nd19(pstEmpSchedule.getString(FLD_NOTE2ND19));
                        empschedule.setNote2nd20(pstEmpSchedule.getString(FLD_NOTE2ND20));
                        empschedule.setNote2nd21(pstEmpSchedule.getString(FLD_NOTE2ND21));
                        empschedule.setNote2nd22(pstEmpSchedule.getString(FLD_NOTE2ND22));
                        empschedule.setNote2nd23(pstEmpSchedule.getString(FLD_NOTE2ND23));
                        empschedule.setNote2nd24(pstEmpSchedule.getString(FLD_NOTE2ND24));
                        empschedule.setNote2nd25(pstEmpSchedule.getString(FLD_NOTE2ND25));
                        empschedule.setNote2nd26(pstEmpSchedule.getString(FLD_NOTE2ND26));
                        empschedule.setNote2nd27(pstEmpSchedule.getString(FLD_NOTE2ND27));
                        empschedule.setNote2nd28(pstEmpSchedule.getString(FLD_NOTE2ND28));
                        empschedule.setNote2nd29(pstEmpSchedule.getString(FLD_NOTE2ND29));
                        empschedule.setNote2nd30(pstEmpSchedule.getString(FLD_NOTE2ND30));
                        empschedule.setNote2nd31(pstEmpSchedule.getString(FLD_NOTE2ND31));
                        
                        empschedule.setIn1(pstEmpSchedule.getDate(FLD_IN1));
                        empschedule.setIn2(pstEmpSchedule.getDate(FLD_IN2));
                        empschedule.setIn3(pstEmpSchedule.getDate(FLD_IN3));
                        empschedule.setIn4(pstEmpSchedule.getDate(FLD_IN4));
                        empschedule.setIn5(pstEmpSchedule.getDate(FLD_IN5));
                        empschedule.setIn6(pstEmpSchedule.getDate(FLD_IN6));
                        empschedule.setIn7(pstEmpSchedule.getDate(FLD_IN7));
                        empschedule.setIn8(pstEmpSchedule.getDate(FLD_IN8));
                        empschedule.setIn9(pstEmpSchedule.getDate(FLD_IN9));
                        empschedule.setIn10(pstEmpSchedule.getDate(FLD_IN10));
                        empschedule.setIn11(pstEmpSchedule.getDate(FLD_IN11));
                        empschedule.setIn12(pstEmpSchedule.getDate(FLD_IN12));
                        empschedule.setIn13(pstEmpSchedule.getDate(FLD_IN13));
                        empschedule.setIn14(pstEmpSchedule.getDate(FLD_IN14));
                        empschedule.setIn15(pstEmpSchedule.getDate(FLD_IN15));
                        empschedule.setIn16(pstEmpSchedule.getDate(FLD_IN16));
                        empschedule.setIn17(pstEmpSchedule.getDate(FLD_IN17));
                        empschedule.setIn18(pstEmpSchedule.getDate(FLD_IN18));
                        empschedule.setIn19(pstEmpSchedule.getDate(FLD_IN19));
                        empschedule.setIn20(pstEmpSchedule.getDate(FLD_IN20));
                        empschedule.setIn21(pstEmpSchedule.getDate(FLD_IN21));
                        empschedule.setIn22(pstEmpSchedule.getDate(FLD_IN22));
                        empschedule.setIn23(pstEmpSchedule.getDate(FLD_IN23));
                        empschedule.setIn24(pstEmpSchedule.getDate(FLD_IN24));
                        empschedule.setIn25(pstEmpSchedule.getDate(FLD_IN25));
                        empschedule.setIn26(pstEmpSchedule.getDate(FLD_IN26));
                        empschedule.setIn27(pstEmpSchedule.getDate(FLD_IN27));
                        empschedule.setIn28(pstEmpSchedule.getDate(FLD_IN28));
                        empschedule.setIn29(pstEmpSchedule.getDate(FLD_IN29));
                        empschedule.setIn30(pstEmpSchedule.getDate(FLD_IN30));
                        empschedule.setIn31(pstEmpSchedule.getDate(FLD_IN31));
                        empschedule.setIn2nd1(pstEmpSchedule.getDate(FLD_IN2ND1));
                        empschedule.setIn2nd2(pstEmpSchedule.getDate(FLD_IN2ND2));
                        empschedule.setIn2nd3(pstEmpSchedule.getDate(FLD_IN2ND3));
                        empschedule.setIn2nd4(pstEmpSchedule.getDate(FLD_IN2ND4));
                        empschedule.setIn2nd5(pstEmpSchedule.getDate(FLD_IN2ND5));
                        empschedule.setIn2nd6(pstEmpSchedule.getDate(FLD_IN2ND6));
                        empschedule.setIn2nd7(pstEmpSchedule.getDate(FLD_IN2ND7));
                        empschedule.setIn2nd8(pstEmpSchedule.getDate(FLD_IN2ND8));
                        empschedule.setIn2nd9(pstEmpSchedule.getDate(FLD_IN2ND9));
                        empschedule.setIn2nd10(pstEmpSchedule.getDate(FLD_IN2ND10));
                        empschedule.setIn2nd11(pstEmpSchedule.getDate(FLD_IN2ND11));
                        empschedule.setIn2nd12(pstEmpSchedule.getDate(FLD_IN2ND12));
                        empschedule.setIn2nd13(pstEmpSchedule.getDate(FLD_IN2ND13));
                        empschedule.setIn2nd14(pstEmpSchedule.getDate(FLD_IN2ND14));
                        empschedule.setIn2nd15(pstEmpSchedule.getDate(FLD_IN2ND15));
                        empschedule.setIn2nd16(pstEmpSchedule.getDate(FLD_IN2ND16));
                        empschedule.setIn2nd17(pstEmpSchedule.getDate(FLD_IN2ND17));
                        empschedule.setIn2nd18(pstEmpSchedule.getDate(FLD_IN2ND18));
                        empschedule.setIn2nd19(pstEmpSchedule.getDate(FLD_IN2ND19));
                        empschedule.setIn2nd20(pstEmpSchedule.getDate(FLD_IN2ND20));
                        empschedule.setIn2nd21(pstEmpSchedule.getDate(FLD_IN2ND21));
                        empschedule.setIn2nd22(pstEmpSchedule.getDate(FLD_IN2ND22));
                        empschedule.setIn2nd23(pstEmpSchedule.getDate(FLD_IN2ND23));
                        empschedule.setIn2nd24(pstEmpSchedule.getDate(FLD_IN2ND24));
                        empschedule.setIn2nd25(pstEmpSchedule.getDate(FLD_IN2ND25));
                        empschedule.setIn2nd26(pstEmpSchedule.getDate(FLD_IN2ND26));
                        empschedule.setIn2nd27(pstEmpSchedule.getDate(FLD_IN2ND27));
                        empschedule.setIn2nd28(pstEmpSchedule.getDate(FLD_IN2ND28));
                        empschedule.setIn2nd29(pstEmpSchedule.getDate(FLD_IN2ND29));
                        empschedule.setIn2nd30(pstEmpSchedule.getDate(FLD_IN2ND30));
                        empschedule.setIn2nd31(pstEmpSchedule.getDate(FLD_IN2ND31));
                        
                        empschedule.setOut1(pstEmpSchedule.getDate(FLD_OUT1));
                        empschedule.setOut2(pstEmpSchedule.getDate(FLD_OUT2));
                        empschedule.setOut3(pstEmpSchedule.getDate(FLD_OUT3));
                        empschedule.setOut4(pstEmpSchedule.getDate(FLD_OUT4));
                        empschedule.setOut5(pstEmpSchedule.getDate(FLD_OUT5));
                        empschedule.setOut6(pstEmpSchedule.getDate(FLD_OUT6));
                        empschedule.setOut7(pstEmpSchedule.getDate(FLD_OUT7));
                        empschedule.setOut8(pstEmpSchedule.getDate(FLD_OUT8));
                        empschedule.setOut9(pstEmpSchedule.getDate(FLD_OUT9));
                        empschedule.setOut10(pstEmpSchedule.getDate(FLD_OUT10));
                        empschedule.setOut11(pstEmpSchedule.getDate(FLD_OUT11));
                        empschedule.setOut12(pstEmpSchedule.getDate(FLD_OUT12));
                        empschedule.setOut13(pstEmpSchedule.getDate(FLD_OUT13));
                        empschedule.setOut14(pstEmpSchedule.getDate(FLD_OUT14));
                        empschedule.setOut15(pstEmpSchedule.getDate(FLD_OUT15));
                        empschedule.setOut16(pstEmpSchedule.getDate(FLD_OUT16));
                        empschedule.setOut17(pstEmpSchedule.getDate(FLD_OUT17));
                        empschedule.setOut18(pstEmpSchedule.getDate(FLD_OUT18));
                        empschedule.setOut19(pstEmpSchedule.getDate(FLD_OUT19));
                        empschedule.setOut20(pstEmpSchedule.getDate(FLD_OUT20));
                        empschedule.setOut21(pstEmpSchedule.getDate(FLD_OUT21));
                        empschedule.setOut22(pstEmpSchedule.getDate(FLD_OUT22));
                        empschedule.setOut23(pstEmpSchedule.getDate(FLD_OUT23));
                        empschedule.setOut24(pstEmpSchedule.getDate(FLD_OUT24));
                        empschedule.setOut25(pstEmpSchedule.getDate(FLD_OUT25));
                        empschedule.setOut26(pstEmpSchedule.getDate(FLD_OUT26));
                        empschedule.setOut27(pstEmpSchedule.getDate(FLD_OUT27));
                        empschedule.setOut28(pstEmpSchedule.getDate(FLD_OUT28));
                        empschedule.setOut29(pstEmpSchedule.getDate(FLD_OUT29));
                        empschedule.setOut30(pstEmpSchedule.getDate(FLD_OUT30));
                        empschedule.setOut31(pstEmpSchedule.getDate(FLD_OUT31));
                        empschedule.setOut2nd1(pstEmpSchedule.getDate(FLD_OUT2ND1));                        
                        empschedule.setOut2nd2(pstEmpSchedule.getDate(FLD_OUT2ND2));
                        empschedule.setOut2nd3(pstEmpSchedule.getDate(FLD_OUT2ND3));
                        empschedule.setOut2nd4(pstEmpSchedule.getDate(FLD_OUT2ND4));
                        empschedule.setOut2nd5(pstEmpSchedule.getDate(FLD_OUT2ND5));
                        empschedule.setOut2nd6(pstEmpSchedule.getDate(FLD_OUT2ND6));
                        empschedule.setOut2nd7(pstEmpSchedule.getDate(FLD_OUT2ND7));
                        empschedule.setOut2nd8(pstEmpSchedule.getDate(FLD_OUT2ND8));
                        empschedule.setOut2nd9(pstEmpSchedule.getDate(FLD_OUT2ND9));
                        empschedule.setOut2nd10(pstEmpSchedule.getDate(FLD_OUT2ND10));
                        empschedule.setOut2nd11(pstEmpSchedule.getDate(FLD_OUT2ND11));                        
                        empschedule.setOut2nd12(pstEmpSchedule.getDate(FLD_OUT2ND12));
                        empschedule.setOut2nd13(pstEmpSchedule.getDate(FLD_OUT2ND13));
                        empschedule.setOut2nd14(pstEmpSchedule.getDate(FLD_OUT2ND14));
                        empschedule.setOut2nd15(pstEmpSchedule.getDate(FLD_OUT2ND15));
                        empschedule.setOut2nd16(pstEmpSchedule.getDate(FLD_OUT2ND16));
                        empschedule.setOut2nd17(pstEmpSchedule.getDate(FLD_OUT2ND17));
                        empschedule.setOut2nd18(pstEmpSchedule.getDate(FLD_OUT2ND18));
                        empschedule.setOut2nd19(pstEmpSchedule.getDate(FLD_OUT2ND19));
                        empschedule.setOut2nd20(pstEmpSchedule.getDate(FLD_OUT2ND20));                        
                        empschedule.setOut2nd21(pstEmpSchedule.getDate(FLD_OUT2ND21));                        
                        empschedule.setOut2nd22(pstEmpSchedule.getDate(FLD_OUT2ND22));
                        empschedule.setOut2nd23(pstEmpSchedule.getDate(FLD_OUT2ND23));
                        empschedule.setOut2nd24(pstEmpSchedule.getDate(FLD_OUT2ND24));
                        empschedule.setOut2nd25(pstEmpSchedule.getDate(FLD_OUT2ND25));
                        empschedule.setOut2nd26(pstEmpSchedule.getDate(FLD_OUT2ND26));
                        empschedule.setOut2nd27(pstEmpSchedule.getDate(FLD_OUT2ND27));
                        empschedule.setOut2nd28(pstEmpSchedule.getDate(FLD_OUT2ND28));
                        empschedule.setOut2nd29(pstEmpSchedule.getDate(FLD_OUT2ND29));
                        empschedule.setOut2nd30(pstEmpSchedule.getDate(FLD_OUT2ND30));                        
                        empschedule.setOut2nd31(pstEmpSchedule.getDate(FLD_OUT2ND31));                        
                        
                        empschedule.setScheduleType(pstEmpSchedule.getInt(FLD_SCHEDULE_TYPE));    
                        
			return empschedule; 
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstEmpScheduleOrg(0),DBException.UNKNOWN); 
		} 
	}

	public static long insertExc(EmpSchedule empschedule) throws DBException{ 
		try{ 
			PstEmpScheduleOrg pstEmpSchedule = new PstEmpScheduleOrg(0);

			pstEmpSchedule.setLong(FLD_PERIOD_ID, empschedule.getPeriodId());
			pstEmpSchedule.setLong(FLD_EMPLOYEE_ID, empschedule.getEmployeeId());
			pstEmpSchedule.setLong(FLD_D1, empschedule.getD1());
			pstEmpSchedule.setLong(FLD_D2, empschedule.getD2());
			pstEmpSchedule.setLong(FLD_D3, empschedule.getD3());
			pstEmpSchedule.setLong(FLD_D4, empschedule.getD4());
			pstEmpSchedule.setLong(FLD_D5, empschedule.getD5());
			pstEmpSchedule.setLong(FLD_D6, empschedule.getD6());
			pstEmpSchedule.setLong(FLD_D7, empschedule.getD7());
			pstEmpSchedule.setLong(FLD_D8, empschedule.getD8());
			pstEmpSchedule.setLong(FLD_D9, empschedule.getD9());
			pstEmpSchedule.setLong(FLD_D10, empschedule.getD10());
			pstEmpSchedule.setLong(FLD_D11, empschedule.getD11());
			pstEmpSchedule.setLong(FLD_D12, empschedule.getD12());
			pstEmpSchedule.setLong(FLD_D13, empschedule.getD13());
			pstEmpSchedule.setLong(FLD_D14, empschedule.getD14());
			pstEmpSchedule.setLong(FLD_D15, empschedule.getD15());
			pstEmpSchedule.setLong(FLD_D16, empschedule.getD16());
			pstEmpSchedule.setLong(FLD_D17, empschedule.getD17());
			pstEmpSchedule.setLong(FLD_D18, empschedule.getD18());
			pstEmpSchedule.setLong(FLD_D19, empschedule.getD19());
			pstEmpSchedule.setLong(FLD_D20, empschedule.getD20());
			pstEmpSchedule.setLong(FLD_D21, empschedule.getD21());
			pstEmpSchedule.setLong(FLD_D22, empschedule.getD22());
			pstEmpSchedule.setLong(FLD_D23, empschedule.getD23());
			pstEmpSchedule.setLong(FLD_D24, empschedule.getD24());
			pstEmpSchedule.setLong(FLD_D25, empschedule.getD25());
			pstEmpSchedule.setLong(FLD_D26, empschedule.getD26());
			pstEmpSchedule.setLong(FLD_D27, empschedule.getD27());
			pstEmpSchedule.setLong(FLD_D28, empschedule.getD28());
			pstEmpSchedule.setLong(FLD_D29, empschedule.getD29());
			pstEmpSchedule.setLong(FLD_D30, empschedule.getD30());
			pstEmpSchedule.setLong(FLD_D31, empschedule.getD31());

                        // added by edhy for split shift
			pstEmpSchedule.setLong(FLD_D2ND1, empschedule.getD2nd1());
			pstEmpSchedule.setLong(FLD_D2ND2, empschedule.getD2nd2());
			pstEmpSchedule.setLong(FLD_D2ND3, empschedule.getD2nd3());
			pstEmpSchedule.setLong(FLD_D2ND4, empschedule.getD2nd4());
			pstEmpSchedule.setLong(FLD_D2ND5, empschedule.getD2nd5());
			pstEmpSchedule.setLong(FLD_D2ND6, empschedule.getD2nd6());
			pstEmpSchedule.setLong(FLD_D2ND7, empschedule.getD2nd7());
			pstEmpSchedule.setLong(FLD_D2ND8, empschedule.getD2nd8());
			pstEmpSchedule.setLong(FLD_D2ND9, empschedule.getD2nd9());
			pstEmpSchedule.setLong(FLD_D2ND10, empschedule.getD2nd10());
			pstEmpSchedule.setLong(FLD_D2ND11, empschedule.getD2nd11());
			pstEmpSchedule.setLong(FLD_D2ND12, empschedule.getD2nd12());
			pstEmpSchedule.setLong(FLD_D2ND13, empschedule.getD2nd13());
			pstEmpSchedule.setLong(FLD_D2ND14, empschedule.getD2nd14());
			pstEmpSchedule.setLong(FLD_D2ND15, empschedule.getD2nd15());
			pstEmpSchedule.setLong(FLD_D2ND16, empschedule.getD2nd16());
			pstEmpSchedule.setLong(FLD_D2ND17, empschedule.getD2nd17());
			pstEmpSchedule.setLong(FLD_D2ND18, empschedule.getD2nd18());
			pstEmpSchedule.setLong(FLD_D2ND19, empschedule.getD2nd19());
			pstEmpSchedule.setLong(FLD_D2ND20, empschedule.getD2nd20());
			pstEmpSchedule.setLong(FLD_D2ND21, empschedule.getD2nd21());
			pstEmpSchedule.setLong(FLD_D2ND22, empschedule.getD2nd22());
			pstEmpSchedule.setLong(FLD_D2ND23, empschedule.getD2nd23());
			pstEmpSchedule.setLong(FLD_D2ND24, empschedule.getD2nd24());
			pstEmpSchedule.setLong(FLD_D2ND25, empschedule.getD2nd25());
			pstEmpSchedule.setLong(FLD_D2ND26, empschedule.getD2nd26());
			pstEmpSchedule.setLong(FLD_D2ND27, empschedule.getD2nd27());
			pstEmpSchedule.setLong(FLD_D2ND28, empschedule.getD2nd28());
			pstEmpSchedule.setLong(FLD_D2ND29, empschedule.getD2nd29());
			pstEmpSchedule.setLong(FLD_D2ND30, empschedule.getD2nd30());
			pstEmpSchedule.setLong(FLD_D2ND31, empschedule.getD2nd31());
                        
                        pstEmpSchedule.setInt(FLD_STATUS1, empschedule.getStatus1());
                        pstEmpSchedule.setInt(FLD_STATUS2, empschedule.getStatus2());
                        pstEmpSchedule.setInt(FLD_STATUS3, empschedule.getStatus3());
                        pstEmpSchedule.setInt(FLD_STATUS4, empschedule.getStatus4());
                        pstEmpSchedule.setInt(FLD_STATUS5, empschedule.getStatus5());
                        pstEmpSchedule.setInt(FLD_STATUS6, empschedule.getStatus6());
                        pstEmpSchedule.setInt(FLD_STATUS7, empschedule.getStatus7());
                        pstEmpSchedule.setInt(FLD_STATUS8, empschedule.getStatus8());
                        pstEmpSchedule.setInt(FLD_STATUS9, empschedule.getStatus9());
                        pstEmpSchedule.setInt(FLD_STATUS10, empschedule.getStatus10());
                        pstEmpSchedule.setInt(FLD_STATUS11, empschedule.getStatus11());
                        pstEmpSchedule.setInt(FLD_STATUS12, empschedule.getStatus12());
                        pstEmpSchedule.setInt(FLD_STATUS13, empschedule.getStatus13());
                        pstEmpSchedule.setInt(FLD_STATUS14, empschedule.getStatus14());
                        pstEmpSchedule.setInt(FLD_STATUS15, empschedule.getStatus15());
                        pstEmpSchedule.setInt(FLD_STATUS16, empschedule.getStatus16());
                        pstEmpSchedule.setInt(FLD_STATUS17, empschedule.getStatus17());
                        pstEmpSchedule.setInt(FLD_STATUS18, empschedule.getStatus18());
                        pstEmpSchedule.setInt(FLD_STATUS19, empschedule.getStatus19());
                        pstEmpSchedule.setInt(FLD_STATUS20, empschedule.getStatus20());
                        pstEmpSchedule.setInt(FLD_STATUS21, empschedule.getStatus21());
                        pstEmpSchedule.setInt(FLD_STATUS22, empschedule.getStatus22());
                        pstEmpSchedule.setInt(FLD_STATUS23, empschedule.getStatus23());
                        pstEmpSchedule.setInt(FLD_STATUS24, empschedule.getStatus24());
                        pstEmpSchedule.setInt(FLD_STATUS25, empschedule.getStatus25());
                        pstEmpSchedule.setInt(FLD_STATUS26, empschedule.getStatus26());
                        pstEmpSchedule.setInt(FLD_STATUS27, empschedule.getStatus27());
                        pstEmpSchedule.setInt(FLD_STATUS28, empschedule.getStatus28());
                        pstEmpSchedule.setInt(FLD_STATUS29, empschedule.getStatus29());
                        pstEmpSchedule.setInt(FLD_STATUS30, empschedule.getStatus30());
                        pstEmpSchedule.setInt(FLD_STATUS31, empschedule.getStatus31());
                        pstEmpSchedule.setInt(FLD_STATUS2ND1, empschedule.getStatus2nd1());
                        pstEmpSchedule.setInt(FLD_STATUS2ND2, empschedule.getStatus2nd2());
                        pstEmpSchedule.setInt(FLD_STATUS2ND3, empschedule.getStatus2nd3());
                        pstEmpSchedule.setInt(FLD_STATUS2ND4, empschedule.getStatus2nd4());
                        pstEmpSchedule.setInt(FLD_STATUS2ND5, empschedule.getStatus2nd5());
                        pstEmpSchedule.setInt(FLD_STATUS2ND6, empschedule.getStatus2nd6());
                        pstEmpSchedule.setInt(FLD_STATUS2ND7, empschedule.getStatus2nd7());
                        pstEmpSchedule.setInt(FLD_STATUS2ND8, empschedule.getStatus2nd8());
                        pstEmpSchedule.setInt(FLD_STATUS2ND9, empschedule.getStatus2nd9());
                        pstEmpSchedule.setInt(FLD_STATUS2ND10, empschedule.getStatus2nd10());
                        pstEmpSchedule.setInt(FLD_STATUS2ND11, empschedule.getStatus2nd11());
                        pstEmpSchedule.setInt(FLD_STATUS2ND12, empschedule.getStatus2nd12());
                        pstEmpSchedule.setInt(FLD_STATUS2ND13, empschedule.getStatus2nd13());
                        pstEmpSchedule.setInt(FLD_STATUS2ND14, empschedule.getStatus2nd14());
                        pstEmpSchedule.setInt(FLD_STATUS2ND15, empschedule.getStatus2nd15());
                        pstEmpSchedule.setInt(FLD_STATUS2ND16, empschedule.getStatus2nd16());
                        pstEmpSchedule.setInt(FLD_STATUS2ND17, empschedule.getStatus2nd17());
                        pstEmpSchedule.setInt(FLD_STATUS2ND18, empschedule.getStatus2nd18());
                        pstEmpSchedule.setInt(FLD_STATUS2ND19, empschedule.getStatus2nd19());
                        pstEmpSchedule.setInt(FLD_STATUS2ND20, empschedule.getStatus2nd20());
                        pstEmpSchedule.setInt(FLD_STATUS2ND21, empschedule.getStatus2nd21());
                        pstEmpSchedule.setInt(FLD_STATUS2ND22, empschedule.getStatus2nd22());
                        pstEmpSchedule.setInt(FLD_STATUS2ND23, empschedule.getStatus2nd23());
                        pstEmpSchedule.setInt(FLD_STATUS2ND24, empschedule.getStatus2nd24());
                        pstEmpSchedule.setInt(FLD_STATUS2ND25, empschedule.getStatus2nd25());
                        pstEmpSchedule.setInt(FLD_STATUS2ND26, empschedule.getStatus2nd26());
                        pstEmpSchedule.setInt(FLD_STATUS2ND27, empschedule.getStatus2nd27());
                        pstEmpSchedule.setInt(FLD_STATUS2ND28, empschedule.getStatus2nd28());
                        pstEmpSchedule.setInt(FLD_STATUS2ND29, empschedule.getStatus2nd29());
                        pstEmpSchedule.setInt(FLD_STATUS2ND30, empschedule.getStatus2nd30());
                        pstEmpSchedule.setInt(FLD_STATUS2ND31, empschedule.getStatus2nd31());                        
                        
                        pstEmpSchedule.setInt(FLD_REASON1, empschedule.getReason1());
                        pstEmpSchedule.setInt(FLD_REASON2, empschedule.getReason2());
                        pstEmpSchedule.setInt(FLD_REASON3, empschedule.getReason3());
                        pstEmpSchedule.setInt(FLD_REASON4, empschedule.getReason4());
                        pstEmpSchedule.setInt(FLD_REASON5, empschedule.getReason5());
                        pstEmpSchedule.setInt(FLD_REASON6, empschedule.getReason6());
                        pstEmpSchedule.setInt(FLD_REASON7, empschedule.getReason7());
                        pstEmpSchedule.setInt(FLD_REASON8, empschedule.getReason8());
                        pstEmpSchedule.setInt(FLD_REASON9, empschedule.getReason9());
                        pstEmpSchedule.setInt(FLD_REASON10, empschedule.getReason10());
                        pstEmpSchedule.setInt(FLD_REASON11, empschedule.getReason11());
                        pstEmpSchedule.setInt(FLD_REASON12, empschedule.getReason12());
                        pstEmpSchedule.setInt(FLD_REASON13, empschedule.getReason13());
                        pstEmpSchedule.setInt(FLD_REASON14, empschedule.getReason14());
                        pstEmpSchedule.setInt(FLD_REASON15, empschedule.getReason15());
                        pstEmpSchedule.setInt(FLD_REASON16, empschedule.getReason16());
                        pstEmpSchedule.setInt(FLD_REASON17, empschedule.getReason17());
                        pstEmpSchedule.setInt(FLD_REASON18, empschedule.getReason18());
                        pstEmpSchedule.setInt(FLD_REASON19, empschedule.getReason19());
                        pstEmpSchedule.setInt(FLD_REASON20, empschedule.getReason20());                        
                        pstEmpSchedule.setInt(FLD_REASON21, empschedule.getReason21());
                        pstEmpSchedule.setInt(FLD_REASON22, empschedule.getReason22());
                        pstEmpSchedule.setInt(FLD_REASON23, empschedule.getReason23());
                        pstEmpSchedule.setInt(FLD_REASON24, empschedule.getReason24());
                        pstEmpSchedule.setInt(FLD_REASON25, empschedule.getReason25());
                        pstEmpSchedule.setInt(FLD_REASON26, empschedule.getReason26());
                        pstEmpSchedule.setInt(FLD_REASON27, empschedule.getReason27());
                        pstEmpSchedule.setInt(FLD_REASON28, empschedule.getReason28());
                        pstEmpSchedule.setInt(FLD_REASON29, empschedule.getReason29());
                        pstEmpSchedule.setInt(FLD_REASON30, empschedule.getReason30());                        
                        pstEmpSchedule.setInt(FLD_REASON31, empschedule.getReason31());                                                
                        pstEmpSchedule.setInt(FLD_REASON2ND1, empschedule.getReason2nd1());                                                
                        pstEmpSchedule.setInt(FLD_REASON2ND2, empschedule.getReason2nd2());                                                
                        pstEmpSchedule.setInt(FLD_REASON2ND3, empschedule.getReason2nd3());                                                
                        pstEmpSchedule.setInt(FLD_REASON2ND4, empschedule.getReason2nd4());                                                
                        pstEmpSchedule.setInt(FLD_REASON2ND5, empschedule.getReason2nd5());                                                
                        pstEmpSchedule.setInt(FLD_REASON2ND6, empschedule.getReason2nd6());                                                
                        pstEmpSchedule.setInt(FLD_REASON2ND7, empschedule.getReason2nd7());                                                
                        pstEmpSchedule.setInt(FLD_REASON2ND8, empschedule.getReason2nd8());                                                
                        pstEmpSchedule.setInt(FLD_REASON2ND9, empschedule.getReason2nd9());                                                
                        pstEmpSchedule.setInt(FLD_REASON2ND10, empschedule.getReason2nd10());                                                
                        pstEmpSchedule.setInt(FLD_REASON2ND11, empschedule.getReason2nd11());                                                
                        pstEmpSchedule.setInt(FLD_REASON2ND12, empschedule.getReason2nd12());                                                
                        pstEmpSchedule.setInt(FLD_REASON2ND13, empschedule.getReason2nd13());                                                
                        pstEmpSchedule.setInt(FLD_REASON2ND14, empschedule.getReason2nd14());                                                
                        pstEmpSchedule.setInt(FLD_REASON2ND15, empschedule.getReason2nd15());                                                
                        pstEmpSchedule.setInt(FLD_REASON2ND16, empschedule.getReason2nd16());                                                
                        pstEmpSchedule.setInt(FLD_REASON2ND17, empschedule.getReason2nd17());                                                
                        pstEmpSchedule.setInt(FLD_REASON2ND18, empschedule.getReason2nd18());                                                
                        pstEmpSchedule.setInt(FLD_REASON2ND19, empschedule.getReason2nd19());                                                
                        pstEmpSchedule.setInt(FLD_REASON2ND20, empschedule.getReason2nd20());                                                
                        pstEmpSchedule.setInt(FLD_REASON2ND21, empschedule.getReason2nd21());                                                
                        pstEmpSchedule.setInt(FLD_REASON2ND22, empschedule.getReason2nd22());                                                
                        pstEmpSchedule.setInt(FLD_REASON2ND23, empschedule.getReason2nd23());                                                
                        pstEmpSchedule.setInt(FLD_REASON2ND24, empschedule.getReason2nd24());                                                
                        pstEmpSchedule.setInt(FLD_REASON2ND25, empschedule.getReason2nd25());                                                
                        pstEmpSchedule.setInt(FLD_REASON2ND26, empschedule.getReason2nd26());                                                
                        pstEmpSchedule.setInt(FLD_REASON2ND27, empschedule.getReason2nd27());                                                
                        pstEmpSchedule.setInt(FLD_REASON2ND28, empschedule.getReason2nd28());                                                
                        pstEmpSchedule.setInt(FLD_REASON2ND29, empschedule.getReason2nd29());                                                
                        pstEmpSchedule.setInt(FLD_REASON2ND30, empschedule.getReason2nd30());                                                
                        pstEmpSchedule.setInt(FLD_REASON2ND31, empschedule.getReason2nd31());
                        
                        pstEmpSchedule.setString(FLD_NOTE1, empschedule.getNote1());
                        pstEmpSchedule.setString(FLD_NOTE2, empschedule.getNote2());
                        pstEmpSchedule.setString(FLD_NOTE3, empschedule.getNote3());
                        pstEmpSchedule.setString(FLD_NOTE4, empschedule.getNote4());
                        pstEmpSchedule.setString(FLD_NOTE5, empschedule.getNote5());
                        pstEmpSchedule.setString(FLD_NOTE6, empschedule.getNote6());
                        pstEmpSchedule.setString(FLD_NOTE7, empschedule.getNote7());
                        pstEmpSchedule.setString(FLD_NOTE8, empschedule.getNote8());
                        pstEmpSchedule.setString(FLD_NOTE9, empschedule.getNote9());
                        pstEmpSchedule.setString(FLD_NOTE10, empschedule.getNote10());
                        pstEmpSchedule.setString(FLD_NOTE11, empschedule.getNote11());
                        pstEmpSchedule.setString(FLD_NOTE12, empschedule.getNote12());
                        pstEmpSchedule.setString(FLD_NOTE13, empschedule.getNote13());
                        pstEmpSchedule.setString(FLD_NOTE14, empschedule.getNote14());
                        pstEmpSchedule.setString(FLD_NOTE15, empschedule.getNote15());
                        pstEmpSchedule.setString(FLD_NOTE16, empschedule.getNote16());
                        pstEmpSchedule.setString(FLD_NOTE17, empschedule.getNote17());
                        pstEmpSchedule.setString(FLD_NOTE18, empschedule.getNote18());
                        pstEmpSchedule.setString(FLD_NOTE19, empschedule.getNote19());
                        pstEmpSchedule.setString(FLD_NOTE20, empschedule.getNote20());                        
                        pstEmpSchedule.setString(FLD_NOTE21, empschedule.getNote21());
                        pstEmpSchedule.setString(FLD_NOTE22, empschedule.getNote22());
                        pstEmpSchedule.setString(FLD_NOTE23, empschedule.getNote23());
                        pstEmpSchedule.setString(FLD_NOTE24, empschedule.getNote24());
                        pstEmpSchedule.setString(FLD_NOTE25, empschedule.getNote25());
                        pstEmpSchedule.setString(FLD_NOTE26, empschedule.getNote26());
                        pstEmpSchedule.setString(FLD_NOTE27, empschedule.getNote27());
                        pstEmpSchedule.setString(FLD_NOTE28, empschedule.getNote28());
                        pstEmpSchedule.setString(FLD_NOTE29, empschedule.getNote29());
                        pstEmpSchedule.setString(FLD_NOTE30, empschedule.getNote30());
                        pstEmpSchedule.setString(FLD_NOTE31, empschedule.getNote31());
                        pstEmpSchedule.setString(FLD_NOTE2ND1, empschedule.getNote2nd1());
                        pstEmpSchedule.setString(FLD_NOTE2ND2, empschedule.getNote2nd2());
                        pstEmpSchedule.setString(FLD_NOTE2ND3, empschedule.getNote2nd3());
                        pstEmpSchedule.setString(FLD_NOTE2ND4, empschedule.getNote2nd4());
                        pstEmpSchedule.setString(FLD_NOTE2ND5, empschedule.getNote2nd5());
                        pstEmpSchedule.setString(FLD_NOTE2ND6, empschedule.getNote2nd6());
                        pstEmpSchedule.setString(FLD_NOTE2ND7, empschedule.getNote2nd7());
                        pstEmpSchedule.setString(FLD_NOTE2ND8, empschedule.getNote2nd8());
                        pstEmpSchedule.setString(FLD_NOTE2ND9, empschedule.getNote2nd9());
                        pstEmpSchedule.setString(FLD_NOTE2ND10, empschedule.getNote2nd10());
                        pstEmpSchedule.setString(FLD_NOTE2ND11, empschedule.getNote2nd11());
                        pstEmpSchedule.setString(FLD_NOTE2ND12, empschedule.getNote2nd12());
                        pstEmpSchedule.setString(FLD_NOTE2ND13, empschedule.getNote2nd13());
                        pstEmpSchedule.setString(FLD_NOTE2ND14, empschedule.getNote2nd14());
                        pstEmpSchedule.setString(FLD_NOTE2ND15, empschedule.getNote2nd15());
                        pstEmpSchedule.setString(FLD_NOTE2ND16, empschedule.getNote2nd16());
                        pstEmpSchedule.setString(FLD_NOTE2ND17, empschedule.getNote2nd17());
                        pstEmpSchedule.setString(FLD_NOTE2ND18, empschedule.getNote2nd18());
                        pstEmpSchedule.setString(FLD_NOTE2ND19, empschedule.getNote2nd19());
                        pstEmpSchedule.setString(FLD_NOTE2ND20, empschedule.getNote2nd20());
                        pstEmpSchedule.setString(FLD_NOTE2ND21, empschedule.getNote2nd21());
                        pstEmpSchedule.setString(FLD_NOTE2ND22, empschedule.getNote2nd22());
                        pstEmpSchedule.setString(FLD_NOTE2ND23, empschedule.getNote2nd23());
                        pstEmpSchedule.setString(FLD_NOTE2ND24, empschedule.getNote2nd24());
                        pstEmpSchedule.setString(FLD_NOTE2ND25, empschedule.getNote2nd25());
                        pstEmpSchedule.setString(FLD_NOTE2ND26, empschedule.getNote2nd26());
                        pstEmpSchedule.setString(FLD_NOTE2ND27, empschedule.getNote2nd27());
                        pstEmpSchedule.setString(FLD_NOTE2ND28, empschedule.getNote2nd28());
                        pstEmpSchedule.setString(FLD_NOTE2ND29, empschedule.getNote2nd29());
                        pstEmpSchedule.setString(FLD_NOTE2ND30, empschedule.getNote2nd30());
                        pstEmpSchedule.setString(FLD_NOTE2ND31, empschedule.getNote2nd31());
                        
                        pstEmpSchedule.setDate(FLD_IN1, empschedule.getIn1());
                        pstEmpSchedule.setDate(FLD_IN2, empschedule.getIn2());
                        pstEmpSchedule.setDate(FLD_IN3, empschedule.getIn3());
                        pstEmpSchedule.setDate(FLD_IN4, empschedule.getIn4());
                        pstEmpSchedule.setDate(FLD_IN5, empschedule.getIn5());
                        pstEmpSchedule.setDate(FLD_IN6, empschedule.getIn6());
                        pstEmpSchedule.setDate(FLD_IN7, empschedule.getIn7());
                        pstEmpSchedule.setDate(FLD_IN8, empschedule.getIn8());
                        pstEmpSchedule.setDate(FLD_IN9, empschedule.getIn9());
                        pstEmpSchedule.setDate(FLD_IN10, empschedule.getIn10());
                        pstEmpSchedule.setDate(FLD_IN11, empschedule.getIn11());
                        pstEmpSchedule.setDate(FLD_IN12, empschedule.getIn12());
                        pstEmpSchedule.setDate(FLD_IN13, empschedule.getIn13());
                        pstEmpSchedule.setDate(FLD_IN14, empschedule.getIn14());
                        pstEmpSchedule.setDate(FLD_IN15, empschedule.getIn15());
                        pstEmpSchedule.setDate(FLD_IN16, empschedule.getIn16());
                        pstEmpSchedule.setDate(FLD_IN17, empschedule.getIn17());
                        pstEmpSchedule.setDate(FLD_IN18, empschedule.getIn18());
                        pstEmpSchedule.setDate(FLD_IN19, empschedule.getIn19());
                        pstEmpSchedule.setDate(FLD_IN20, empschedule.getIn20());
                        pstEmpSchedule.setDate(FLD_IN21, empschedule.getIn21());
                        pstEmpSchedule.setDate(FLD_IN22, empschedule.getIn22());
                        pstEmpSchedule.setDate(FLD_IN23, empschedule.getIn23());
                        pstEmpSchedule.setDate(FLD_IN24, empschedule.getIn24());
                        pstEmpSchedule.setDate(FLD_IN25, empschedule.getIn25());
                        pstEmpSchedule.setDate(FLD_IN26, empschedule.getIn26());
                        pstEmpSchedule.setDate(FLD_IN27, empschedule.getIn27());
                        pstEmpSchedule.setDate(FLD_IN28, empschedule.getIn28());
                        pstEmpSchedule.setDate(FLD_IN29, empschedule.getIn29());
                        pstEmpSchedule.setDate(FLD_IN30, empschedule.getIn30());
                        pstEmpSchedule.setDate(FLD_IN31, empschedule.getIn31());
                        pstEmpSchedule.setDate(FLD_IN2ND1, empschedule.getIn2nd1());
                        pstEmpSchedule.setDate(FLD_IN2ND2, empschedule.getIn2nd2());
                        pstEmpSchedule.setDate(FLD_IN2ND3, empschedule.getIn2nd3());
                        pstEmpSchedule.setDate(FLD_IN2ND4, empschedule.getIn2nd4());
                        pstEmpSchedule.setDate(FLD_IN2ND5, empschedule.getIn2nd5());
                        pstEmpSchedule.setDate(FLD_IN2ND6, empschedule.getIn2nd6());
                        pstEmpSchedule.setDate(FLD_IN2ND7, empschedule.getIn2nd7());
                        pstEmpSchedule.setDate(FLD_IN2ND8, empschedule.getIn2nd8());
                        pstEmpSchedule.setDate(FLD_IN2ND9, empschedule.getIn2nd9());
                        pstEmpSchedule.setDate(FLD_IN2ND10, empschedule.getIn2nd10());
                        pstEmpSchedule.setDate(FLD_IN2ND11, empschedule.getIn2nd11());
                        pstEmpSchedule.setDate(FLD_IN2ND12, empschedule.getIn2nd12());
                        pstEmpSchedule.setDate(FLD_IN2ND13, empschedule.getIn2nd13());
                        pstEmpSchedule.setDate(FLD_IN2ND14, empschedule.getIn2nd14());
                        pstEmpSchedule.setDate(FLD_IN2ND15, empschedule.getIn2nd15());
                        pstEmpSchedule.setDate(FLD_IN2ND16, empschedule.getIn2nd16());
                        pstEmpSchedule.setDate(FLD_IN2ND17, empschedule.getIn2nd17());
                        pstEmpSchedule.setDate(FLD_IN2ND18, empschedule.getIn2nd18());
                        pstEmpSchedule.setDate(FLD_IN2ND19, empschedule.getIn2nd19());
                        pstEmpSchedule.setDate(FLD_IN2ND20, empschedule.getIn2nd20());                        
                        pstEmpSchedule.setDate(FLD_IN2ND21, empschedule.getIn2nd21());
                        pstEmpSchedule.setDate(FLD_IN2ND22, empschedule.getIn2nd22());
                        pstEmpSchedule.setDate(FLD_IN2ND23, empschedule.getIn2nd23());
                        pstEmpSchedule.setDate(FLD_IN2ND24, empschedule.getIn2nd24());
                        pstEmpSchedule.setDate(FLD_IN2ND25, empschedule.getIn2nd25());
                        pstEmpSchedule.setDate(FLD_IN2ND26, empschedule.getIn2nd26());
                        pstEmpSchedule.setDate(FLD_IN2ND27, empschedule.getIn2nd27());
                        pstEmpSchedule.setDate(FLD_IN2ND28, empschedule.getIn2nd28());
                        pstEmpSchedule.setDate(FLD_IN2ND29, empschedule.getIn2nd29());
                        pstEmpSchedule.setDate(FLD_IN2ND30, empschedule.getIn2nd30());                        
                        pstEmpSchedule.setDate(FLD_IN2ND31, empschedule.getIn2nd31());                        
                        
                        pstEmpSchedule.setDate(FLD_OUT1, empschedule.getOut1());
                        pstEmpSchedule.setDate(FLD_OUT2, empschedule.getOut2());
                        pstEmpSchedule.setDate(FLD_OUT3, empschedule.getOut3());
                        pstEmpSchedule.setDate(FLD_OUT4, empschedule.getOut4());
                        pstEmpSchedule.setDate(FLD_OUT5, empschedule.getOut5());
                        pstEmpSchedule.setDate(FLD_OUT6, empschedule.getOut6());
                        pstEmpSchedule.setDate(FLD_OUT7, empschedule.getOut7());
                        pstEmpSchedule.setDate(FLD_OUT8, empschedule.getOut8());
                        pstEmpSchedule.setDate(FLD_OUT9, empschedule.getOut9());
                        pstEmpSchedule.setDate(FLD_OUT10, empschedule.getOut10());
                        pstEmpSchedule.setDate(FLD_OUT11, empschedule.getOut11());
                        pstEmpSchedule.setDate(FLD_OUT12, empschedule.getOut12());
                        pstEmpSchedule.setDate(FLD_OUT13, empschedule.getOut13());
                        pstEmpSchedule.setDate(FLD_OUT14, empschedule.getOut14());
                        pstEmpSchedule.setDate(FLD_OUT15, empschedule.getOut15());
                        pstEmpSchedule.setDate(FLD_OUT16, empschedule.getOut16());
                        pstEmpSchedule.setDate(FLD_OUT17, empschedule.getOut17());
                        pstEmpSchedule.setDate(FLD_OUT18, empschedule.getOut18());
                        pstEmpSchedule.setDate(FLD_OUT19, empschedule.getOut19());
                        pstEmpSchedule.setDate(FLD_OUT20, empschedule.getOut20());                        
                        pstEmpSchedule.setDate(FLD_OUT21, empschedule.getOut21());
                        pstEmpSchedule.setDate(FLD_OUT22, empschedule.getOut22());
                        pstEmpSchedule.setDate(FLD_OUT23, empschedule.getOut23());
                        pstEmpSchedule.setDate(FLD_OUT24, empschedule.getOut24());
                        pstEmpSchedule.setDate(FLD_OUT25, empschedule.getOut25());
                        pstEmpSchedule.setDate(FLD_OUT26, empschedule.getOut26());
                        pstEmpSchedule.setDate(FLD_OUT27, empschedule.getOut27());
                        pstEmpSchedule.setDate(FLD_OUT28, empschedule.getOut28());
                        pstEmpSchedule.setDate(FLD_OUT29, empschedule.getOut29());
                        pstEmpSchedule.setDate(FLD_OUT30, empschedule.getOut30());                        
                        pstEmpSchedule.setDate(FLD_OUT31, empschedule.getOut31());                        
                        pstEmpSchedule.setDate(FLD_OUT2ND1, empschedule.getOut2nd1());                        
                        pstEmpSchedule.setDate(FLD_OUT2ND2, empschedule.getOut2nd2());
                        pstEmpSchedule.setDate(FLD_OUT2ND3, empschedule.getOut2nd3());
                        pstEmpSchedule.setDate(FLD_OUT2ND4, empschedule.getOut2nd4());
                        pstEmpSchedule.setDate(FLD_OUT2ND5, empschedule.getOut2nd5());
                        pstEmpSchedule.setDate(FLD_OUT2ND6, empschedule.getOut2nd6());
                        pstEmpSchedule.setDate(FLD_OUT2ND7, empschedule.getOut2nd7());
                        pstEmpSchedule.setDate(FLD_OUT2ND8, empschedule.getOut2nd8());
                        pstEmpSchedule.setDate(FLD_OUT2ND9, empschedule.getOut2nd9());
                        pstEmpSchedule.setDate(FLD_OUT2ND10, empschedule.getOut2nd10());
                        pstEmpSchedule.setDate(FLD_OUT2ND11, empschedule.getOut2nd11());                        
                        pstEmpSchedule.setDate(FLD_OUT2ND12, empschedule.getOut2nd12());
                        pstEmpSchedule.setDate(FLD_OUT2ND13, empschedule.getOut2nd13());
                        pstEmpSchedule.setDate(FLD_OUT2ND14, empschedule.getOut2nd14());
                        pstEmpSchedule.setDate(FLD_OUT2ND15, empschedule.getOut2nd15());
                        pstEmpSchedule.setDate(FLD_OUT2ND16, empschedule.getOut2nd16());
                        pstEmpSchedule.setDate(FLD_OUT2ND17, empschedule.getOut2nd17());
                        pstEmpSchedule.setDate(FLD_OUT2ND18, empschedule.getOut2nd18());
                        pstEmpSchedule.setDate(FLD_OUT2ND19, empschedule.getOut2nd19());
                        pstEmpSchedule.setDate(FLD_OUT2ND20, empschedule.getOut2nd20());
                        pstEmpSchedule.setDate(FLD_OUT2ND21, empschedule.getOut2nd21());                        
                        pstEmpSchedule.setDate(FLD_OUT2ND22, empschedule.getOut2nd22());
                        pstEmpSchedule.setDate(FLD_OUT2ND23, empschedule.getOut2nd23());
                        pstEmpSchedule.setDate(FLD_OUT2ND24, empschedule.getOut2nd24());
                        pstEmpSchedule.setDate(FLD_OUT2ND25, empschedule.getOut2nd25());
                        pstEmpSchedule.setDate(FLD_OUT2ND26, empschedule.getOut2nd26());
                        pstEmpSchedule.setDate(FLD_OUT2ND27, empschedule.getOut2nd27());
                        pstEmpSchedule.setDate(FLD_OUT2ND28, empschedule.getOut2nd28());
                        pstEmpSchedule.setDate(FLD_OUT2ND29, empschedule.getOut2nd29());
                        pstEmpSchedule.setDate(FLD_OUT2ND30, empschedule.getOut2nd30());
                        pstEmpSchedule.setDate(FLD_OUT2ND31, empschedule.getOut2nd31());
                        
                        pstEmpSchedule.setInt(FLD_SCHEDULE_TYPE, empschedule.getScheduleType());
                        
                        pstEmpSchedule.insert(); 
			empschedule.setOID(pstEmpSchedule.getlong(FLD_EMP_SCHEDULE_ID));
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstEmpScheduleOrg(0),DBException.UNKNOWN); 
		}
		return empschedule.getOID();
	}

	public static long updateExc(EmpSchedule empschedule) throws DBException{ 
		try{ 
			if(empschedule.getOID() != 0){ 
				PstEmpScheduleOrg pstEmpSchedule = new PstEmpScheduleOrg(empschedule.getOID());

				pstEmpSchedule.setLong(FLD_PERIOD_ID, empschedule.getPeriodId());
				pstEmpSchedule.setLong(FLD_EMPLOYEE_ID, empschedule.getEmployeeId());
				pstEmpSchedule.setLong(FLD_D1, empschedule.getD1());
				pstEmpSchedule.setLong(FLD_D2, empschedule.getD2());
				pstEmpSchedule.setLong(FLD_D3, empschedule.getD3());
				pstEmpSchedule.setLong(FLD_D4, empschedule.getD4());
				pstEmpSchedule.setLong(FLD_D5, empschedule.getD5());
				pstEmpSchedule.setLong(FLD_D6, empschedule.getD6());
				pstEmpSchedule.setLong(FLD_D7, empschedule.getD7());
				pstEmpSchedule.setLong(FLD_D8, empschedule.getD8());
				pstEmpSchedule.setLong(FLD_D9, empschedule.getD9());
				pstEmpSchedule.setLong(FLD_D10, empschedule.getD10());
				pstEmpSchedule.setLong(FLD_D11, empschedule.getD11());
				pstEmpSchedule.setLong(FLD_D12, empschedule.getD12());
				pstEmpSchedule.setLong(FLD_D13, empschedule.getD13());
				pstEmpSchedule.setLong(FLD_D14, empschedule.getD14());
				pstEmpSchedule.setLong(FLD_D15, empschedule.getD15());
				pstEmpSchedule.setLong(FLD_D16, empschedule.getD16());
				pstEmpSchedule.setLong(FLD_D17, empschedule.getD17());
				pstEmpSchedule.setLong(FLD_D18, empschedule.getD18());
				pstEmpSchedule.setLong(FLD_D19, empschedule.getD19());
				pstEmpSchedule.setLong(FLD_D20, empschedule.getD20());
				pstEmpSchedule.setLong(FLD_D21, empschedule.getD21());
				pstEmpSchedule.setLong(FLD_D22, empschedule.getD22());
				pstEmpSchedule.setLong(FLD_D23, empschedule.getD23());
				pstEmpSchedule.setLong(FLD_D24, empschedule.getD24());
				pstEmpSchedule.setLong(FLD_D25, empschedule.getD25());
				pstEmpSchedule.setLong(FLD_D26, empschedule.getD26());
				pstEmpSchedule.setLong(FLD_D27, empschedule.getD27());
				pstEmpSchedule.setLong(FLD_D28, empschedule.getD28());
				pstEmpSchedule.setLong(FLD_D29, empschedule.getD29());
				pstEmpSchedule.setLong(FLD_D30, empschedule.getD30());
				pstEmpSchedule.setLong(FLD_D31, empschedule.getD31());

                                // added by edhy for split shift
                                pstEmpSchedule.setLong(FLD_D2ND1, empschedule.getD2nd1());
                                pstEmpSchedule.setLong(FLD_D2ND2, empschedule.getD2nd2());
                                pstEmpSchedule.setLong(FLD_D2ND3, empschedule.getD2nd3());
                                pstEmpSchedule.setLong(FLD_D2ND4, empschedule.getD2nd4());
                                pstEmpSchedule.setLong(FLD_D2ND5, empschedule.getD2nd5());
                                pstEmpSchedule.setLong(FLD_D2ND6, empschedule.getD2nd6());
                                pstEmpSchedule.setLong(FLD_D2ND7, empschedule.getD2nd7());
                                pstEmpSchedule.setLong(FLD_D2ND8, empschedule.getD2nd8());
                                pstEmpSchedule.setLong(FLD_D2ND9, empschedule.getD2nd9());
                                pstEmpSchedule.setLong(FLD_D2ND10, empschedule.getD2nd10());
                                pstEmpSchedule.setLong(FLD_D2ND11, empschedule.getD2nd11());
                                pstEmpSchedule.setLong(FLD_D2ND12, empschedule.getD2nd12());
                                pstEmpSchedule.setLong(FLD_D2ND13, empschedule.getD2nd13());
                                pstEmpSchedule.setLong(FLD_D2ND14, empschedule.getD2nd14());
                                pstEmpSchedule.setLong(FLD_D2ND15, empschedule.getD2nd15());
                                pstEmpSchedule.setLong(FLD_D2ND16, empschedule.getD2nd16());
                                pstEmpSchedule.setLong(FLD_D2ND17, empschedule.getD2nd17());
                                pstEmpSchedule.setLong(FLD_D2ND18, empschedule.getD2nd18());
                                pstEmpSchedule.setLong(FLD_D2ND19, empschedule.getD2nd19());
                                pstEmpSchedule.setLong(FLD_D2ND20, empschedule.getD2nd20());
                                pstEmpSchedule.setLong(FLD_D2ND21, empschedule.getD2nd21());
                                pstEmpSchedule.setLong(FLD_D2ND22, empschedule.getD2nd22());
                                pstEmpSchedule.setLong(FLD_D2ND23, empschedule.getD2nd23());
                                pstEmpSchedule.setLong(FLD_D2ND24, empschedule.getD2nd24());
                                pstEmpSchedule.setLong(FLD_D2ND25, empschedule.getD2nd25());
                                pstEmpSchedule.setLong(FLD_D2ND26, empschedule.getD2nd26());
                                pstEmpSchedule.setLong(FLD_D2ND27, empschedule.getD2nd27());
                                pstEmpSchedule.setLong(FLD_D2ND28, empschedule.getD2nd28());
                                pstEmpSchedule.setLong(FLD_D2ND29, empschedule.getD2nd29());
                                pstEmpSchedule.setLong(FLD_D2ND30, empschedule.getD2nd30());
                                pstEmpSchedule.setLong(FLD_D2ND31, empschedule.getD2nd31());

                                pstEmpSchedule.setInt(FLD_STATUS1, empschedule.getStatus1());
                                pstEmpSchedule.setInt(FLD_STATUS2, empschedule.getStatus2());
                                pstEmpSchedule.setInt(FLD_STATUS3, empschedule.getStatus3());
                                pstEmpSchedule.setInt(FLD_STATUS4, empschedule.getStatus4());
                                pstEmpSchedule.setInt(FLD_STATUS5, empschedule.getStatus5());
                                pstEmpSchedule.setInt(FLD_STATUS6, empschedule.getStatus6());
                                pstEmpSchedule.setInt(FLD_STATUS7, empschedule.getStatus7());
                                pstEmpSchedule.setInt(FLD_STATUS8, empschedule.getStatus8());
                                pstEmpSchedule.setInt(FLD_STATUS9, empschedule.getStatus9());
                                pstEmpSchedule.setInt(FLD_STATUS10, empschedule.getStatus10());
                                pstEmpSchedule.setInt(FLD_STATUS11, empschedule.getStatus11());
                                pstEmpSchedule.setInt(FLD_STATUS12, empschedule.getStatus12());
                                pstEmpSchedule.setInt(FLD_STATUS13, empschedule.getStatus13());
                                pstEmpSchedule.setInt(FLD_STATUS14, empschedule.getStatus14());
                                pstEmpSchedule.setInt(FLD_STATUS15, empschedule.getStatus15());
                                pstEmpSchedule.setInt(FLD_STATUS16, empschedule.getStatus16());
                                pstEmpSchedule.setInt(FLD_STATUS17, empschedule.getStatus17());
                                pstEmpSchedule.setInt(FLD_STATUS18, empschedule.getStatus18());
                                pstEmpSchedule.setInt(FLD_STATUS19, empschedule.getStatus19());
                                pstEmpSchedule.setInt(FLD_STATUS20, empschedule.getStatus20());
                                pstEmpSchedule.setInt(FLD_STATUS21, empschedule.getStatus21());
                                pstEmpSchedule.setInt(FLD_STATUS22, empschedule.getStatus22());
                                pstEmpSchedule.setInt(FLD_STATUS23, empschedule.getStatus23());
                                pstEmpSchedule.setInt(FLD_STATUS24, empschedule.getStatus24());
                                pstEmpSchedule.setInt(FLD_STATUS25, empschedule.getStatus25());
                                pstEmpSchedule.setInt(FLD_STATUS26, empschedule.getStatus26());
                                pstEmpSchedule.setInt(FLD_STATUS27, empschedule.getStatus27());
                                pstEmpSchedule.setInt(FLD_STATUS28, empschedule.getStatus28());
                                pstEmpSchedule.setInt(FLD_STATUS29, empschedule.getStatus29());
                                pstEmpSchedule.setInt(FLD_STATUS30, empschedule.getStatus30());
                                pstEmpSchedule.setInt(FLD_STATUS31, empschedule.getStatus31());
                                pstEmpSchedule.setInt(FLD_STATUS2ND1, empschedule.getStatus2nd1());
                                pstEmpSchedule.setInt(FLD_STATUS2ND2, empschedule.getStatus2nd2());
                                pstEmpSchedule.setInt(FLD_STATUS2ND3, empschedule.getStatus2nd3());
                                pstEmpSchedule.setInt(FLD_STATUS2ND4, empschedule.getStatus2nd4());
                                pstEmpSchedule.setInt(FLD_STATUS2ND5, empschedule.getStatus2nd5());
                                pstEmpSchedule.setInt(FLD_STATUS2ND6, empschedule.getStatus2nd6());
                                pstEmpSchedule.setInt(FLD_STATUS2ND7, empschedule.getStatus2nd7());
                                pstEmpSchedule.setInt(FLD_STATUS2ND8, empschedule.getStatus2nd8());
                                pstEmpSchedule.setInt(FLD_STATUS2ND9, empschedule.getStatus2nd9());
                                pstEmpSchedule.setInt(FLD_STATUS2ND10, empschedule.getStatus2nd10());
                                pstEmpSchedule.setInt(FLD_STATUS2ND11, empschedule.getStatus2nd11());
                                pstEmpSchedule.setInt(FLD_STATUS2ND12, empschedule.getStatus2nd12());
                                pstEmpSchedule.setInt(FLD_STATUS2ND13, empschedule.getStatus2nd13());
                                pstEmpSchedule.setInt(FLD_STATUS2ND14, empschedule.getStatus2nd14());
                                pstEmpSchedule.setInt(FLD_STATUS2ND15, empschedule.getStatus2nd15());
                                pstEmpSchedule.setInt(FLD_STATUS2ND16, empschedule.getStatus2nd16());
                                pstEmpSchedule.setInt(FLD_STATUS2ND17, empschedule.getStatus2nd17());
                                pstEmpSchedule.setInt(FLD_STATUS2ND18, empschedule.getStatus2nd18());
                                pstEmpSchedule.setInt(FLD_STATUS2ND19, empschedule.getStatus2nd19());
                                pstEmpSchedule.setInt(FLD_STATUS2ND20, empschedule.getStatus2nd20());
                                pstEmpSchedule.setInt(FLD_STATUS2ND21, empschedule.getStatus2nd21());
                                pstEmpSchedule.setInt(FLD_STATUS2ND22, empschedule.getStatus2nd22());
                                pstEmpSchedule.setInt(FLD_STATUS2ND23, empschedule.getStatus2nd23());
                                pstEmpSchedule.setInt(FLD_STATUS2ND24, empschedule.getStatus2nd24());
                                pstEmpSchedule.setInt(FLD_STATUS2ND25, empschedule.getStatus2nd25());
                                pstEmpSchedule.setInt(FLD_STATUS2ND26, empschedule.getStatus2nd26());
                                pstEmpSchedule.setInt(FLD_STATUS2ND27, empschedule.getStatus2nd27());
                                pstEmpSchedule.setInt(FLD_STATUS2ND28, empschedule.getStatus2nd28());
                                pstEmpSchedule.setInt(FLD_STATUS2ND29, empschedule.getStatus2nd29());
                                pstEmpSchedule.setInt(FLD_STATUS2ND30, empschedule.getStatus2nd30());
                                pstEmpSchedule.setInt(FLD_STATUS2ND31, empschedule.getStatus2nd31());

                                pstEmpSchedule.setInt(FLD_REASON1, empschedule.getReason1());
                                pstEmpSchedule.setInt(FLD_REASON2, empschedule.getReason2());
                                pstEmpSchedule.setInt(FLD_REASON3, empschedule.getReason3());
                                pstEmpSchedule.setInt(FLD_REASON4, empschedule.getReason4());
                                pstEmpSchedule.setInt(FLD_REASON5, empschedule.getReason5());
                                pstEmpSchedule.setInt(FLD_REASON6, empschedule.getReason6());
                                pstEmpSchedule.setInt(FLD_REASON7, empschedule.getReason7());
                                pstEmpSchedule.setInt(FLD_REASON8, empschedule.getReason8());
                                pstEmpSchedule.setInt(FLD_REASON9, empschedule.getReason9());
                                pstEmpSchedule.setInt(FLD_REASON10, empschedule.getReason10());
                                pstEmpSchedule.setInt(FLD_REASON11, empschedule.getReason11());
                                pstEmpSchedule.setInt(FLD_REASON12, empschedule.getReason12());
                                pstEmpSchedule.setInt(FLD_REASON13, empschedule.getReason13());
                                pstEmpSchedule.setInt(FLD_REASON14, empschedule.getReason14());
                                pstEmpSchedule.setInt(FLD_REASON15, empschedule.getReason15());
                                pstEmpSchedule.setInt(FLD_REASON16, empschedule.getReason16());
                                pstEmpSchedule.setInt(FLD_REASON17, empschedule.getReason17());
                                pstEmpSchedule.setInt(FLD_REASON18, empschedule.getReason18());
                                pstEmpSchedule.setInt(FLD_REASON19, empschedule.getReason19());
                                pstEmpSchedule.setInt(FLD_REASON20, empschedule.getReason20());                        
                                pstEmpSchedule.setInt(FLD_REASON21, empschedule.getReason21());
                                pstEmpSchedule.setInt(FLD_REASON22, empschedule.getReason22());
                                pstEmpSchedule.setInt(FLD_REASON23, empschedule.getReason23());
                                pstEmpSchedule.setInt(FLD_REASON24, empschedule.getReason24());
                                pstEmpSchedule.setInt(FLD_REASON25, empschedule.getReason25());
                                pstEmpSchedule.setInt(FLD_REASON26, empschedule.getReason26());
                                pstEmpSchedule.setInt(FLD_REASON27, empschedule.getReason27());
                                pstEmpSchedule.setInt(FLD_REASON28, empschedule.getReason28());
                                pstEmpSchedule.setInt(FLD_REASON29, empschedule.getReason29());
                                pstEmpSchedule.setInt(FLD_REASON30, empschedule.getReason30());                        
                                pstEmpSchedule.setInt(FLD_REASON31, empschedule.getReason31());                                                
                                pstEmpSchedule.setInt(FLD_REASON2ND1, empschedule.getReason2nd1());                                                
                                pstEmpSchedule.setInt(FLD_REASON2ND2, empschedule.getReason2nd2());                                                
                                pstEmpSchedule.setInt(FLD_REASON2ND3, empschedule.getReason2nd3());                                                
                                pstEmpSchedule.setInt(FLD_REASON2ND4, empschedule.getReason2nd4());                                                
                                pstEmpSchedule.setInt(FLD_REASON2ND5, empschedule.getReason2nd5());                                                
                                pstEmpSchedule.setInt(FLD_REASON2ND6, empschedule.getReason2nd6());                                                
                                pstEmpSchedule.setInt(FLD_REASON2ND7, empschedule.getReason2nd7());                                                
                                pstEmpSchedule.setInt(FLD_REASON2ND8, empschedule.getReason2nd8());                                                
                                pstEmpSchedule.setInt(FLD_REASON2ND9, empschedule.getReason2nd9());                                                
                                pstEmpSchedule.setInt(FLD_REASON2ND10, empschedule.getReason2nd10());                                                
                                pstEmpSchedule.setInt(FLD_REASON2ND11, empschedule.getReason2nd11());                                                
                                pstEmpSchedule.setInt(FLD_REASON2ND12, empschedule.getReason2nd12());                                                
                                pstEmpSchedule.setInt(FLD_REASON2ND13, empschedule.getReason2nd13());                                                
                                pstEmpSchedule.setInt(FLD_REASON2ND14, empschedule.getReason2nd14());                                                
                                pstEmpSchedule.setInt(FLD_REASON2ND15, empschedule.getReason2nd15());                                                
                                pstEmpSchedule.setInt(FLD_REASON2ND16, empschedule.getReason2nd16());                                                
                                pstEmpSchedule.setInt(FLD_REASON2ND17, empschedule.getReason2nd17());                                                
                                pstEmpSchedule.setInt(FLD_REASON2ND18, empschedule.getReason2nd18());                                                
                                pstEmpSchedule.setInt(FLD_REASON2ND19, empschedule.getReason2nd19());                                                
                                pstEmpSchedule.setInt(FLD_REASON2ND20, empschedule.getReason2nd20());                                                
                                pstEmpSchedule.setInt(FLD_REASON2ND21, empschedule.getReason2nd21());                                                
                                pstEmpSchedule.setInt(FLD_REASON2ND22, empschedule.getReason2nd22());                                                
                                pstEmpSchedule.setInt(FLD_REASON2ND23, empschedule.getReason2nd23());                                                
                                pstEmpSchedule.setInt(FLD_REASON2ND24, empschedule.getReason2nd24());                                                
                                pstEmpSchedule.setInt(FLD_REASON2ND25, empschedule.getReason2nd25());                                                
                                pstEmpSchedule.setInt(FLD_REASON2ND26, empschedule.getReason2nd26());                                                
                                pstEmpSchedule.setInt(FLD_REASON2ND27, empschedule.getReason2nd27());                                                
                                pstEmpSchedule.setInt(FLD_REASON2ND28, empschedule.getReason2nd28());                                                
                                pstEmpSchedule.setInt(FLD_REASON2ND29, empschedule.getReason2nd29());                                                
                                pstEmpSchedule.setInt(FLD_REASON2ND30, empschedule.getReason2nd30());                                                
                                pstEmpSchedule.setInt(FLD_REASON2ND31, empschedule.getReason2nd31());

                                pstEmpSchedule.setString(FLD_NOTE1, empschedule.getNote1());
                                pstEmpSchedule.setString(FLD_NOTE2, empschedule.getNote2());
                                pstEmpSchedule.setString(FLD_NOTE3, empschedule.getNote3());
                                pstEmpSchedule.setString(FLD_NOTE4, empschedule.getNote4());
                                pstEmpSchedule.setString(FLD_NOTE5, empschedule.getNote5());
                                pstEmpSchedule.setString(FLD_NOTE6, empschedule.getNote6());
                                pstEmpSchedule.setString(FLD_NOTE7, empschedule.getNote7());
                                pstEmpSchedule.setString(FLD_NOTE8, empschedule.getNote8());
                                pstEmpSchedule.setString(FLD_NOTE9, empschedule.getNote9());
                                pstEmpSchedule.setString(FLD_NOTE10, empschedule.getNote10());
                                pstEmpSchedule.setString(FLD_NOTE11, empschedule.getNote11());
                                pstEmpSchedule.setString(FLD_NOTE12, empschedule.getNote12());
                                pstEmpSchedule.setString(FLD_NOTE13, empschedule.getNote13());
                                pstEmpSchedule.setString(FLD_NOTE14, empschedule.getNote14());
                                pstEmpSchedule.setString(FLD_NOTE15, empschedule.getNote15());
                                pstEmpSchedule.setString(FLD_NOTE16, empschedule.getNote16());
                                pstEmpSchedule.setString(FLD_NOTE17, empschedule.getNote17());
                                pstEmpSchedule.setString(FLD_NOTE18, empschedule.getNote18());
                                pstEmpSchedule.setString(FLD_NOTE19, empschedule.getNote19());
                                pstEmpSchedule.setString(FLD_NOTE20, empschedule.getNote20());                        
                                pstEmpSchedule.setString(FLD_NOTE21, empschedule.getNote21());
                                pstEmpSchedule.setString(FLD_NOTE22, empschedule.getNote22());
                                pstEmpSchedule.setString(FLD_NOTE23, empschedule.getNote23());
                                pstEmpSchedule.setString(FLD_NOTE24, empschedule.getNote24());
                                pstEmpSchedule.setString(FLD_NOTE25, empschedule.getNote25());
                                pstEmpSchedule.setString(FLD_NOTE26, empschedule.getNote26());
                                pstEmpSchedule.setString(FLD_NOTE27, empschedule.getNote27());
                                pstEmpSchedule.setString(FLD_NOTE28, empschedule.getNote28());
                                pstEmpSchedule.setString(FLD_NOTE29, empschedule.getNote29());
                                pstEmpSchedule.setString(FLD_NOTE30, empschedule.getNote30());
                                pstEmpSchedule.setString(FLD_NOTE31, empschedule.getNote31());
                                pstEmpSchedule.setString(FLD_NOTE2ND1, empschedule.getNote2nd1());
                                pstEmpSchedule.setString(FLD_NOTE2ND2, empschedule.getNote2nd2());
                                pstEmpSchedule.setString(FLD_NOTE2ND3, empschedule.getNote2nd3());
                                pstEmpSchedule.setString(FLD_NOTE2ND4, empschedule.getNote2nd4());
                                pstEmpSchedule.setString(FLD_NOTE2ND5, empschedule.getNote2nd5());
                                pstEmpSchedule.setString(FLD_NOTE2ND6, empschedule.getNote2nd6());
                                pstEmpSchedule.setString(FLD_NOTE2ND7, empschedule.getNote2nd7());
                                pstEmpSchedule.setString(FLD_NOTE2ND8, empschedule.getNote2nd8());
                                pstEmpSchedule.setString(FLD_NOTE2ND9, empschedule.getNote2nd9());
                                pstEmpSchedule.setString(FLD_NOTE2ND10, empschedule.getNote2nd10());
                                pstEmpSchedule.setString(FLD_NOTE2ND11, empschedule.getNote2nd11());
                                pstEmpSchedule.setString(FLD_NOTE2ND12, empschedule.getNote2nd12());
                                pstEmpSchedule.setString(FLD_NOTE2ND13, empschedule.getNote2nd13());
                                pstEmpSchedule.setString(FLD_NOTE2ND14, empschedule.getNote2nd14());
                                pstEmpSchedule.setString(FLD_NOTE2ND15, empschedule.getNote2nd15());
                                pstEmpSchedule.setString(FLD_NOTE2ND16, empschedule.getNote2nd16());
                                pstEmpSchedule.setString(FLD_NOTE2ND17, empschedule.getNote2nd17());
                                pstEmpSchedule.setString(FLD_NOTE2ND18, empschedule.getNote2nd18());
                                pstEmpSchedule.setString(FLD_NOTE2ND19, empschedule.getNote2nd19());
                                pstEmpSchedule.setString(FLD_NOTE2ND20, empschedule.getNote2nd20());
                                pstEmpSchedule.setString(FLD_NOTE2ND21, empschedule.getNote2nd21());
                                pstEmpSchedule.setString(FLD_NOTE2ND22, empschedule.getNote2nd22());
                                pstEmpSchedule.setString(FLD_NOTE2ND23, empschedule.getNote2nd23());
                                pstEmpSchedule.setString(FLD_NOTE2ND24, empschedule.getNote2nd24());
                                pstEmpSchedule.setString(FLD_NOTE2ND25, empschedule.getNote2nd25());
                                pstEmpSchedule.setString(FLD_NOTE2ND26, empschedule.getNote2nd26());
                                pstEmpSchedule.setString(FLD_NOTE2ND27, empschedule.getNote2nd27());
                                pstEmpSchedule.setString(FLD_NOTE2ND28, empschedule.getNote2nd28());
                                pstEmpSchedule.setString(FLD_NOTE2ND29, empschedule.getNote2nd29());
                                pstEmpSchedule.setString(FLD_NOTE2ND30, empschedule.getNote2nd30());
                                pstEmpSchedule.setString(FLD_NOTE2ND31, empschedule.getNote2nd31());

                                pstEmpSchedule.setDate(FLD_IN1, empschedule.getIn1());
                                pstEmpSchedule.setDate(FLD_IN2, empschedule.getIn2());
                                pstEmpSchedule.setDate(FLD_IN3, empschedule.getIn3());
                                pstEmpSchedule.setDate(FLD_IN4, empschedule.getIn4());
                                pstEmpSchedule.setDate(FLD_IN5, empschedule.getIn5());
                                pstEmpSchedule.setDate(FLD_IN6, empschedule.getIn6());
                                pstEmpSchedule.setDate(FLD_IN7, empschedule.getIn7());
                                pstEmpSchedule.setDate(FLD_IN8, empschedule.getIn8());
                                pstEmpSchedule.setDate(FLD_IN9, empschedule.getIn9());
                                pstEmpSchedule.setDate(FLD_IN10, empschedule.getIn10());
                                pstEmpSchedule.setDate(FLD_IN11, empschedule.getIn11());
                                pstEmpSchedule.setDate(FLD_IN12, empschedule.getIn12());
                                pstEmpSchedule.setDate(FLD_IN13, empschedule.getIn13());
                                pstEmpSchedule.setDate(FLD_IN14, empschedule.getIn14());
                                pstEmpSchedule.setDate(FLD_IN15, empschedule.getIn15());
                                pstEmpSchedule.setDate(FLD_IN16, empschedule.getIn16());
                                pstEmpSchedule.setDate(FLD_IN17, empschedule.getIn17());
                                pstEmpSchedule.setDate(FLD_IN18, empschedule.getIn18());
                                pstEmpSchedule.setDate(FLD_IN19, empschedule.getIn19());
                                pstEmpSchedule.setDate(FLD_IN20, empschedule.getIn20());
                                pstEmpSchedule.setDate(FLD_IN21, empschedule.getIn21());
                                pstEmpSchedule.setDate(FLD_IN22, empschedule.getIn22());
                                pstEmpSchedule.setDate(FLD_IN23, empschedule.getIn23());
                                pstEmpSchedule.setDate(FLD_IN24, empschedule.getIn24());
                                pstEmpSchedule.setDate(FLD_IN25, empschedule.getIn25());
                                pstEmpSchedule.setDate(FLD_IN26, empschedule.getIn26());
                                pstEmpSchedule.setDate(FLD_IN27, empschedule.getIn27());
                                pstEmpSchedule.setDate(FLD_IN28, empschedule.getIn28());
                                pstEmpSchedule.setDate(FLD_IN29, empschedule.getIn29());
                                pstEmpSchedule.setDate(FLD_IN30, empschedule.getIn30());
                                pstEmpSchedule.setDate(FLD_IN31, empschedule.getIn31());
                                pstEmpSchedule.setDate(FLD_IN2ND1, empschedule.getIn2nd1());
                                pstEmpSchedule.setDate(FLD_IN2ND2, empschedule.getIn2nd2());
                                pstEmpSchedule.setDate(FLD_IN2ND3, empschedule.getIn2nd3());
                                pstEmpSchedule.setDate(FLD_IN2ND4, empschedule.getIn2nd4());
                                pstEmpSchedule.setDate(FLD_IN2ND5, empschedule.getIn2nd5());
                                pstEmpSchedule.setDate(FLD_IN2ND6, empschedule.getIn2nd6());
                                pstEmpSchedule.setDate(FLD_IN2ND7, empschedule.getIn2nd7());
                                pstEmpSchedule.setDate(FLD_IN2ND8, empschedule.getIn2nd8());
                                pstEmpSchedule.setDate(FLD_IN2ND9, empschedule.getIn2nd9());
                                pstEmpSchedule.setDate(FLD_IN2ND10, empschedule.getIn2nd10());
                                pstEmpSchedule.setDate(FLD_IN2ND11, empschedule.getIn2nd11());
                                pstEmpSchedule.setDate(FLD_IN2ND12, empschedule.getIn2nd12());
                                pstEmpSchedule.setDate(FLD_IN2ND13, empschedule.getIn2nd13());
                                pstEmpSchedule.setDate(FLD_IN2ND14, empschedule.getIn2nd14());
                                pstEmpSchedule.setDate(FLD_IN2ND15, empschedule.getIn2nd15());
                                pstEmpSchedule.setDate(FLD_IN2ND16, empschedule.getIn2nd16());
                                pstEmpSchedule.setDate(FLD_IN2ND17, empschedule.getIn2nd17());
                                pstEmpSchedule.setDate(FLD_IN2ND18, empschedule.getIn2nd18());
                                pstEmpSchedule.setDate(FLD_IN2ND19, empschedule.getIn2nd19());
                                pstEmpSchedule.setDate(FLD_IN2ND20, empschedule.getIn2nd20());                        
                                pstEmpSchedule.setDate(FLD_IN2ND21, empschedule.getIn2nd21());
                                pstEmpSchedule.setDate(FLD_IN2ND22, empschedule.getIn2nd22());
                                pstEmpSchedule.setDate(FLD_IN2ND23, empschedule.getIn2nd23());
                                pstEmpSchedule.setDate(FLD_IN2ND24, empschedule.getIn2nd24());
                                pstEmpSchedule.setDate(FLD_IN2ND25, empschedule.getIn2nd25());
                                pstEmpSchedule.setDate(FLD_IN2ND26, empschedule.getIn2nd26());
                                pstEmpSchedule.setDate(FLD_IN2ND27, empschedule.getIn2nd27());
                                pstEmpSchedule.setDate(FLD_IN2ND28, empschedule.getIn2nd28());
                                pstEmpSchedule.setDate(FLD_IN2ND29, empschedule.getIn2nd29());
                                pstEmpSchedule.setDate(FLD_IN2ND30, empschedule.getIn2nd30());                        
                                pstEmpSchedule.setDate(FLD_IN2ND31, empschedule.getIn2nd31());                        

                                pstEmpSchedule.setDate(FLD_OUT1, empschedule.getOut1());
                                pstEmpSchedule.setDate(FLD_OUT2, empschedule.getOut2());
                                pstEmpSchedule.setDate(FLD_OUT3, empschedule.getOut3());
                                pstEmpSchedule.setDate(FLD_OUT4, empschedule.getOut4());
                                pstEmpSchedule.setDate(FLD_OUT5, empschedule.getOut5());
                                pstEmpSchedule.setDate(FLD_OUT6, empschedule.getOut6());
                                pstEmpSchedule.setDate(FLD_OUT7, empschedule.getOut7());
                                pstEmpSchedule.setDate(FLD_OUT8, empschedule.getOut8());
                                pstEmpSchedule.setDate(FLD_OUT9, empschedule.getOut9());
                                pstEmpSchedule.setDate(FLD_OUT10, empschedule.getOut10());
                                pstEmpSchedule.setDate(FLD_OUT11, empschedule.getOut11());
                                pstEmpSchedule.setDate(FLD_OUT12, empschedule.getOut12());
                                pstEmpSchedule.setDate(FLD_OUT13, empschedule.getOut13());
                                pstEmpSchedule.setDate(FLD_OUT14, empschedule.getOut14());
                                pstEmpSchedule.setDate(FLD_OUT15, empschedule.getOut15());
                                pstEmpSchedule.setDate(FLD_OUT16, empschedule.getOut16());
                                pstEmpSchedule.setDate(FLD_OUT17, empschedule.getOut17());
                                pstEmpSchedule.setDate(FLD_OUT18, empschedule.getOut18());
                                pstEmpSchedule.setDate(FLD_OUT19, empschedule.getOut19());
                                pstEmpSchedule.setDate(FLD_OUT20, empschedule.getOut20());                        
                                pstEmpSchedule.setDate(FLD_OUT21, empschedule.getOut21());
                                pstEmpSchedule.setDate(FLD_OUT22, empschedule.getOut22());
                                pstEmpSchedule.setDate(FLD_OUT23, empschedule.getOut23());
                                pstEmpSchedule.setDate(FLD_OUT24, empschedule.getOut24());
                                pstEmpSchedule.setDate(FLD_OUT25, empschedule.getOut25());
                                pstEmpSchedule.setDate(FLD_OUT26, empschedule.getOut26());
                                pstEmpSchedule.setDate(FLD_OUT27, empschedule.getOut27());
                                pstEmpSchedule.setDate(FLD_OUT28, empschedule.getOut28());
                                pstEmpSchedule.setDate(FLD_OUT29, empschedule.getOut29());
                                pstEmpSchedule.setDate(FLD_OUT30, empschedule.getOut30());                        
                                pstEmpSchedule.setDate(FLD_OUT31, empschedule.getOut31());                        
                                pstEmpSchedule.setDate(FLD_OUT2ND1, empschedule.getOut2nd1());                        
                                pstEmpSchedule.setDate(FLD_OUT2ND2, empschedule.getOut2nd2());
                                pstEmpSchedule.setDate(FLD_OUT2ND3, empschedule.getOut2nd3());
                                pstEmpSchedule.setDate(FLD_OUT2ND4, empschedule.getOut2nd4());
                                pstEmpSchedule.setDate(FLD_OUT2ND5, empschedule.getOut2nd5());
                                pstEmpSchedule.setDate(FLD_OUT2ND6, empschedule.getOut2nd6());
                                pstEmpSchedule.setDate(FLD_OUT2ND7, empschedule.getOut2nd7());
                                pstEmpSchedule.setDate(FLD_OUT2ND8, empschedule.getOut2nd8());
                                pstEmpSchedule.setDate(FLD_OUT2ND9, empschedule.getOut2nd9());
                                pstEmpSchedule.setDate(FLD_OUT2ND10, empschedule.getOut2nd10());
                                pstEmpSchedule.setDate(FLD_OUT2ND11, empschedule.getOut2nd11());                        
                                pstEmpSchedule.setDate(FLD_OUT2ND12, empschedule.getOut2nd12());
                                pstEmpSchedule.setDate(FLD_OUT2ND13, empschedule.getOut2nd13());
                                pstEmpSchedule.setDate(FLD_OUT2ND14, empschedule.getOut2nd14());
                                pstEmpSchedule.setDate(FLD_OUT2ND15, empschedule.getOut2nd15());
                                pstEmpSchedule.setDate(FLD_OUT2ND16, empschedule.getOut2nd16());
                                pstEmpSchedule.setDate(FLD_OUT2ND17, empschedule.getOut2nd17());
                                pstEmpSchedule.setDate(FLD_OUT2ND18, empschedule.getOut2nd18());
                                pstEmpSchedule.setDate(FLD_OUT2ND19, empschedule.getOut2nd19());
                                pstEmpSchedule.setDate(FLD_OUT2ND20, empschedule.getOut2nd20());
                                pstEmpSchedule.setDate(FLD_OUT2ND21, empschedule.getOut2nd21());                        
                                pstEmpSchedule.setDate(FLD_OUT2ND22, empschedule.getOut2nd22());
                                pstEmpSchedule.setDate(FLD_OUT2ND23, empschedule.getOut2nd23());
                                pstEmpSchedule.setDate(FLD_OUT2ND24, empschedule.getOut2nd24());
                                pstEmpSchedule.setDate(FLD_OUT2ND25, empschedule.getOut2nd25());
                                pstEmpSchedule.setDate(FLD_OUT2ND26, empschedule.getOut2nd26());
                                pstEmpSchedule.setDate(FLD_OUT2ND27, empschedule.getOut2nd27());
                                pstEmpSchedule.setDate(FLD_OUT2ND28, empschedule.getOut2nd28());
                                pstEmpSchedule.setDate(FLD_OUT2ND29, empschedule.getOut2nd29());
                                pstEmpSchedule.setDate(FLD_OUT2ND30, empschedule.getOut2nd30());
                                pstEmpSchedule.setDate(FLD_OUT2ND31, empschedule.getOut2nd31());
                                
                                pstEmpSchedule.setInt(FLD_SCHEDULE_TYPE, empschedule.getScheduleType());
                                
                                pstEmpSchedule.update(); 
				return empschedule.getOID();

			}
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstEmpScheduleOrg(0),DBException.UNKNOWN); 
		}
		return 0;
	}

	public static long deleteExc(long oid) throws DBException{ 
		try{ 
			PstEmpScheduleOrg pstEmpSchedule = new PstEmpScheduleOrg(oid);
			pstEmpSchedule.delete();
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstEmpScheduleOrg(0),DBException.UNKNOWN); 
		}
		return oid;
	}

	public static Vector listAll(){ 
		return list(0, 0, "",""); 
	}

	public static Vector list(int limitStart,int recordToGet, String whereClause, String order){
		Vector lists = new Vector(); 
		DBResultSet dbrs = null;
		try {
			String sql = "SELECT * FROM " + TBL_HR_EMP_SCHEDULE; 
			if(whereClause != null && whereClause.length() > 0)
				sql = sql + " WHERE " + whereClause;
			if(order != null && order.length() > 0)
				sql = sql + " ORDER BY " + order;
			if(limitStart == 0 && recordToGet == 0)
				sql = sql + "";
			else
				sql = sql + " LIMIT " + limitStart + ","+ recordToGet ;
			dbrs = DBHandler.execQueryResult(sql);
			ResultSet rs = dbrs.getResultSet();
			while(rs.next()) {
				EmpSchedule empschedule = new EmpSchedule();
				resultToObject(rs, empschedule);
				lists.add(empschedule);
			}
			rs.close();
			return lists;

		}catch(Exception e) {
			System.out.println(e);
		}finally {
			DBResultSet.close(dbrs);
		}
			return new Vector();
	}

	private static void resultToObject(ResultSet rs, EmpSchedule empschedule){
		try{
			empschedule.setOID(rs.getLong(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_EMP_SCHEDULE_ID]));
			empschedule.setPeriodId(rs.getLong(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_PERIOD_ID]));
			empschedule.setEmployeeId(rs.getLong(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_EMPLOYEE_ID]));
			empschedule.setD1(rs.getLong(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_D1]));
			empschedule.setD2(rs.getLong(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_D2]));
			empschedule.setD3(rs.getLong(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_D3]));
			empschedule.setD4(rs.getLong(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_D4]));
			empschedule.setD5(rs.getLong(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_D5]));
			empschedule.setD6(rs.getLong(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_D6]));
			empschedule.setD7(rs.getLong(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_D7]));
			empschedule.setD8(rs.getLong(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_D8]));
			empschedule.setD9(rs.getLong(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_D9]));
			empschedule.setD10(rs.getLong(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_D10]));
			empschedule.setD11(rs.getLong(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_D11]));
			empschedule.setD12(rs.getLong(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_D12]));
			empschedule.setD13(rs.getLong(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_D13]));
			empschedule.setD14(rs.getLong(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_D14]));
			empschedule.setD15(rs.getLong(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_D15]));
			empschedule.setD16(rs.getLong(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_D16]));
			empschedule.setD17(rs.getLong(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_D17]));
			empschedule.setD18(rs.getLong(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_D18]));
			empschedule.setD19(rs.getLong(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_D19]));
			empschedule.setD20(rs.getLong(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_D20]));
			empschedule.setD21(rs.getLong(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_D21]));
			empschedule.setD22(rs.getLong(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_D22]));
			empschedule.setD23(rs.getLong(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_D23]));
			empschedule.setD24(rs.getLong(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_D24]));
			empschedule.setD25(rs.getLong(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_D25]));
			empschedule.setD26(rs.getLong(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_D26]));
			empschedule.setD27(rs.getLong(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_D27]));
			empschedule.setD28(rs.getLong(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_D28]));
			empschedule.setD29(rs.getLong(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_D29]));
			empschedule.setD30(rs.getLong(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_D30]));
			empschedule.setD31(rs.getLong(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_D31]));

                        // added by edhy for split shift
			empschedule.setD2nd1(rs.getLong(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_D2ND1]));
			empschedule.setD2nd2(rs.getLong(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_D2ND2]));
			empschedule.setD2nd3(rs.getLong(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_D2ND3]));
			empschedule.setD2nd4(rs.getLong(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_D2ND4]));
			empschedule.setD2nd5(rs.getLong(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_D2ND5]));
			empschedule.setD2nd6(rs.getLong(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_D2ND6]));
			empschedule.setD2nd7(rs.getLong(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_D2ND7]));
			empschedule.setD2nd8(rs.getLong(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_D2ND8]));
			empschedule.setD2nd9(rs.getLong(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_D2ND9]));
			empschedule.setD2nd10(rs.getLong(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_D2ND10]));
			empschedule.setD2nd11(rs.getLong(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_D2ND11]));
			empschedule.setD2nd12(rs.getLong(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_D2ND12]));
			empschedule.setD2nd13(rs.getLong(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_D2ND13]));
			empschedule.setD2nd14(rs.getLong(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_D2ND14]));
			empschedule.setD2nd15(rs.getLong(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_D2ND15]));
			empschedule.setD2nd16(rs.getLong(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_D2ND16]));
			empschedule.setD2nd17(rs.getLong(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_D2ND17]));
			empschedule.setD2nd18(rs.getLong(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_D2ND18]));
			empschedule.setD2nd19(rs.getLong(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_D2ND19]));
			empschedule.setD2nd20(rs.getLong(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_D2ND20]));
			empschedule.setD2nd21(rs.getLong(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_D2ND21]));
			empschedule.setD2nd22(rs.getLong(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_D2ND22]));
			empschedule.setD2nd23(rs.getLong(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_D2ND23]));
			empschedule.setD2nd24(rs.getLong(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_D2ND24]));
			empschedule.setD2nd25(rs.getLong(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_D2ND25]));
			empschedule.setD2nd26(rs.getLong(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_D2ND26]));
			empschedule.setD2nd27(rs.getLong(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_D2ND27]));
			empschedule.setD2nd28(rs.getLong(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_D2ND28]));
			empschedule.setD2nd29(rs.getLong(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_D2ND29]));
			empschedule.setD2nd30(rs.getLong(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_D2ND30]));
			empschedule.setD2nd31(rs.getLong(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_D2ND31]));
                        
                        empschedule.setStatus1(rs.getInt(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_STATUS1]));
                        empschedule.setStatus2(rs.getInt(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_STATUS2]));
                        empschedule.setStatus3(rs.getInt(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_STATUS3]));
                        empschedule.setStatus4(rs.getInt(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_STATUS4]));
                        empschedule.setStatus5(rs.getInt(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_STATUS5]));
                        empschedule.setStatus6(rs.getInt(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_STATUS6]));
                        empschedule.setStatus7(rs.getInt(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_STATUS7]));
                        empschedule.setStatus8(rs.getInt(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_STATUS8]));
                        empschedule.setStatus9(rs.getInt(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_STATUS9]));
                        empschedule.setStatus10(rs.getInt(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_STATUS10]));
                        empschedule.setStatus11(rs.getInt(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_STATUS11]));
                        empschedule.setStatus12(rs.getInt(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_STATUS12]));
                        empschedule.setStatus13(rs.getInt(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_STATUS13]));
                        empschedule.setStatus14(rs.getInt(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_STATUS14]));
                        empschedule.setStatus15(rs.getInt(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_STATUS15]));
                        empschedule.setStatus16(rs.getInt(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_STATUS16]));
                        empschedule.setStatus17(rs.getInt(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_STATUS17]));
                        empschedule.setStatus18(rs.getInt(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_STATUS18]));
                        empschedule.setStatus19(rs.getInt(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_STATUS19]));
                        empschedule.setStatus20(rs.getInt(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_STATUS20]));
                        empschedule.setStatus21(rs.getInt(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_STATUS21]));
                        empschedule.setStatus22(rs.getInt(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_STATUS22]));
                        empschedule.setStatus23(rs.getInt(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_STATUS23]));
                        empschedule.setStatus24(rs.getInt(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_STATUS24]));
                        empschedule.setStatus25(rs.getInt(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_STATUS25]));
                        empschedule.setStatus26(rs.getInt(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_STATUS26]));
                        empschedule.setStatus27(rs.getInt(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_STATUS27]));
                        empschedule.setStatus28(rs.getInt(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_STATUS28]));
                        empschedule.setStatus29(rs.getInt(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_STATUS29]));
                        empschedule.setStatus30(rs.getInt(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_STATUS30]));
                        empschedule.setStatus31(rs.getInt(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_STATUS31]));
                        empschedule.setStatus2nd1(rs.getInt(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_STATUS2ND1]));
                        empschedule.setStatus2nd2(rs.getInt(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_STATUS2ND2]));
                        empschedule.setStatus2nd3(rs.getInt(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_STATUS2ND3]));
                        empschedule.setStatus2nd4(rs.getInt(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_STATUS2ND4]));
                        empschedule.setStatus2nd5(rs.getInt(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_STATUS2ND5]));
                        empschedule.setStatus2nd6(rs.getInt(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_STATUS2ND6]));
                        empschedule.setStatus2nd7(rs.getInt(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_STATUS2ND7]));
                        empschedule.setStatus2nd8(rs.getInt(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_STATUS2ND8]));
                        empschedule.setStatus2nd9(rs.getInt(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_STATUS2ND9]));
                        empschedule.setStatus2nd10(rs.getInt(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_STATUS2ND10]));
                        empschedule.setStatus2nd11(rs.getInt(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_STATUS2ND11]));
                        empschedule.setStatus2nd12(rs.getInt(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_STATUS2ND12]));
                        empschedule.setStatus2nd13(rs.getInt(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_STATUS2ND13]));
                        empschedule.setStatus2nd14(rs.getInt(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_STATUS2ND14]));
                        empschedule.setStatus2nd15(rs.getInt(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_STATUS2ND15]));
                        empschedule.setStatus2nd16(rs.getInt(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_STATUS2ND16]));
                        empschedule.setStatus2nd17(rs.getInt(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_STATUS2ND17]));
                        empschedule.setStatus2nd18(rs.getInt(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_STATUS2ND18]));
                        empschedule.setStatus2nd19(rs.getInt(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_STATUS2ND19]));
                        empschedule.setStatus2nd20(rs.getInt(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_STATUS2ND20]));
                        empschedule.setStatus2nd21(rs.getInt(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_STATUS2ND21]));
                        empschedule.setStatus2nd22(rs.getInt(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_STATUS2ND22]));
                        empschedule.setStatus2nd23(rs.getInt(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_STATUS2ND23]));
                        empschedule.setStatus2nd24(rs.getInt(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_STATUS2ND24]));
                        empschedule.setStatus2nd25(rs.getInt(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_STATUS2ND25]));
                        empschedule.setStatus2nd26(rs.getInt(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_STATUS2ND26]));
                        empschedule.setStatus2nd27(rs.getInt(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_STATUS2ND27]));
                        empschedule.setStatus2nd28(rs.getInt(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_STATUS2ND28]));
                        empschedule.setStatus2nd29(rs.getInt(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_STATUS2ND29]));
                        empschedule.setStatus2nd30(rs.getInt(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_STATUS2ND30]));
                        empschedule.setStatus2nd31(rs.getInt(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_STATUS2ND31]));
                        
                        empschedule.setReason1(rs.getInt(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_REASON1]));
                        empschedule.setReason2(rs.getInt(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_REASON2]));
                        empschedule.setReason3(rs.getInt(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_REASON3]));
                        empschedule.setReason4(rs.getInt(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_REASON4]));
                        empschedule.setReason5(rs.getInt(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_REASON5]));
                        empschedule.setReason6(rs.getInt(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_REASON6]));
                        empschedule.setReason7(rs.getInt(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_REASON7]));
                        empschedule.setReason8(rs.getInt(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_REASON8]));
                        empschedule.setReason9(rs.getInt(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_REASON9]));
                        empschedule.setReason10(rs.getInt(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_REASON10]));
                        empschedule.setReason11(rs.getInt(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_REASON11]));
                        empschedule.setReason12(rs.getInt(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_REASON12]));
                        empschedule.setReason13(rs.getInt(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_REASON13]));
                        empschedule.setReason14(rs.getInt(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_REASON14]));
                        empschedule.setReason15(rs.getInt(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_REASON15]));
                        empschedule.setReason16(rs.getInt(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_REASON16]));
                        empschedule.setReason17(rs.getInt(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_REASON17]));
                        empschedule.setReason18(rs.getInt(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_REASON18]));
                        empschedule.setReason19(rs.getInt(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_REASON19]));
                        empschedule.setReason20(rs.getInt(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_REASON20]));
                        empschedule.setReason21(rs.getInt(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_REASON21]));
                        empschedule.setReason22(rs.getInt(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_REASON22]));
                        empschedule.setReason23(rs.getInt(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_REASON23]));
                        empschedule.setReason24(rs.getInt(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_REASON24]));
                        empschedule.setReason25(rs.getInt(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_REASON25]));
                        empschedule.setReason26(rs.getInt(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_REASON26]));
                        empschedule.setReason27(rs.getInt(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_REASON27]));
                        empschedule.setReason28(rs.getInt(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_REASON28]));
                        empschedule.setReason29(rs.getInt(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_REASON29]));
                        empschedule.setReason30(rs.getInt(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_REASON30]));
                        empschedule.setReason31(rs.getInt(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_REASON31]));
                        empschedule.setReason2nd1(rs.getInt(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_REASON2ND1]));
                        empschedule.setReason2nd2(rs.getInt(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_REASON2ND2]));
                        empschedule.setReason2nd3(rs.getInt(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_REASON2ND3]));
                        empschedule.setReason2nd4(rs.getInt(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_REASON2ND4]));
                        empschedule.setReason2nd5(rs.getInt(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_REASON2ND5]));
                        empschedule.setReason2nd6(rs.getInt(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_REASON2ND6]));
                        empschedule.setReason2nd7(rs.getInt(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_REASON2ND7]));
                        empschedule.setReason2nd8(rs.getInt(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_REASON2ND8]));
                        empschedule.setReason2nd9(rs.getInt(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_REASON2ND9]));
                        empschedule.setReason2nd10(rs.getInt(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_REASON2ND10]));
                        empschedule.setReason2nd11(rs.getInt(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_REASON2ND11]));
                        empschedule.setReason2nd12(rs.getInt(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_REASON2ND12]));
                        empschedule.setReason2nd13(rs.getInt(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_REASON2ND13]));
                        empschedule.setReason2nd14(rs.getInt(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_REASON2ND14]));
                        empschedule.setReason2nd15(rs.getInt(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_REASON2ND15]));
                        empschedule.setReason2nd16(rs.getInt(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_REASON2ND16]));
                        empschedule.setReason2nd17(rs.getInt(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_REASON2ND17]));
                        empschedule.setReason2nd18(rs.getInt(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_REASON2ND18]));
                        empschedule.setReason2nd19(rs.getInt(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_REASON2ND19]));
                        empschedule.setReason2nd20(rs.getInt(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_REASON2ND20]));                        
                        empschedule.setReason2nd21(rs.getInt(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_REASON2ND21]));
                        empschedule.setReason2nd22(rs.getInt(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_REASON2ND22]));
                        empschedule.setReason2nd23(rs.getInt(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_REASON2ND23]));
                        empschedule.setReason2nd24(rs.getInt(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_REASON2ND24]));
                        empschedule.setReason2nd25(rs.getInt(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_REASON2ND25]));
                        empschedule.setReason2nd26(rs.getInt(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_REASON2ND26]));
                        empschedule.setReason2nd27(rs.getInt(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_REASON2ND27]));
                        empschedule.setReason2nd28(rs.getInt(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_REASON2ND28]));
                        empschedule.setReason2nd29(rs.getInt(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_REASON2ND29]));
                        empschedule.setReason2nd30(rs.getInt(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_REASON2ND30]));                        
                        empschedule.setReason2nd31(rs.getInt(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_REASON2ND31]));                        
                        
                        empschedule.setNote1(rs.getString(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_NOTE1]));
                        empschedule.setNote2(rs.getString(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_NOTE2]));
                        empschedule.setNote3(rs.getString(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_NOTE3]));
                        empschedule.setNote4(rs.getString(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_NOTE4]));
                        empschedule.setNote5(rs.getString(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_NOTE5]));
                        empschedule.setNote6(rs.getString(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_NOTE6]));
                        empschedule.setNote7(rs.getString(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_NOTE7]));
                        empschedule.setNote8(rs.getString(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_NOTE8]));
                        empschedule.setNote9(rs.getString(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_NOTE9]));
                        empschedule.setNote10(rs.getString(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_NOTE10]));
                        empschedule.setNote11(rs.getString(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_NOTE11]));
                        empschedule.setNote12(rs.getString(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_NOTE12]));
                        empschedule.setNote13(rs.getString(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_NOTE13]));
                        empschedule.setNote14(rs.getString(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_NOTE14]));
                        empschedule.setNote15(rs.getString(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_NOTE15]));
                        empschedule.setNote16(rs.getString(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_NOTE16]));
                        empschedule.setNote17(rs.getString(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_NOTE17]));
                        empschedule.setNote18(rs.getString(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_NOTE18]));
                        empschedule.setNote19(rs.getString(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_NOTE19]));
                        empschedule.setNote20(rs.getString(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_NOTE20]));
                        empschedule.setNote21(rs.getString(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_NOTE21]));
                        empschedule.setNote22(rs.getString(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_NOTE22]));
                        empschedule.setNote23(rs.getString(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_NOTE23]));
                        empschedule.setNote24(rs.getString(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_NOTE24]));
                        empschedule.setNote25(rs.getString(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_NOTE25]));
                        empschedule.setNote26(rs.getString(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_NOTE26]));
                        empschedule.setNote27(rs.getString(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_NOTE27]));
                        empschedule.setNote28(rs.getString(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_NOTE28]));
                        empschedule.setNote29(rs.getString(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_NOTE29]));
                        empschedule.setNote30(rs.getString(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_NOTE30]));
                        empschedule.setNote31(rs.getString(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_NOTE31]));
                        empschedule.setNote2nd1(rs.getString(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_NOTE2ND1]));
                        empschedule.setNote2nd2(rs.getString(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_NOTE2ND2]));
                        empschedule.setNote2nd3(rs.getString(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_NOTE2ND3]));
                        empschedule.setNote2nd4(rs.getString(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_NOTE2ND4]));
                        empschedule.setNote2nd5(rs.getString(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_NOTE2ND5]));
                        empschedule.setNote2nd6(rs.getString(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_NOTE2ND6]));
                        empschedule.setNote2nd7(rs.getString(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_NOTE2ND7]));
                        empschedule.setNote2nd8(rs.getString(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_NOTE2ND8]));
                        empschedule.setNote2nd9(rs.getString(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_NOTE2ND9]));
                        empschedule.setNote2nd10(rs.getString(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_NOTE2ND10]));
                        empschedule.setNote2nd11(rs.getString(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_NOTE2ND11]));
                        empschedule.setNote2nd12(rs.getString(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_NOTE2ND12]));
                        empschedule.setNote2nd13(rs.getString(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_NOTE2ND13]));
                        empschedule.setNote2nd14(rs.getString(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_NOTE2ND14]));
                        empschedule.setNote2nd15(rs.getString(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_NOTE2ND15]));
                        empschedule.setNote2nd16(rs.getString(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_NOTE2ND16]));
                        empschedule.setNote2nd17(rs.getString(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_NOTE2ND17]));
                        empschedule.setNote2nd18(rs.getString(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_NOTE2ND18]));
                        empschedule.setNote2nd19(rs.getString(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_NOTE2ND19]));
                        empschedule.setNote2nd20(rs.getString(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_NOTE2ND20]));                        
                        empschedule.setNote2nd21(rs.getString(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_NOTE2ND21]));
                        empschedule.setNote2nd22(rs.getString(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_NOTE2ND22]));
                        empschedule.setNote2nd23(rs.getString(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_NOTE2ND23]));
                        empschedule.setNote2nd24(rs.getString(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_NOTE2ND24]));
                        empschedule.setNote2nd25(rs.getString(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_NOTE2ND25]));
                        empschedule.setNote2nd26(rs.getString(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_NOTE2ND26]));
                        empschedule.setNote2nd27(rs.getString(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_NOTE2ND27]));
                        empschedule.setNote2nd28(rs.getString(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_NOTE2ND28]));
                        empschedule.setNote2nd29(rs.getString(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_NOTE2ND29]));
                        empschedule.setNote2nd30(rs.getString(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_NOTE2ND30]));
                        empschedule.setNote2nd31(rs.getString(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_NOTE2ND31]));
                        
                        empschedule.setIn1(rs.getDate(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_IN1]));
                        empschedule.setIn2(rs.getDate(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_IN2]));
                        empschedule.setIn3(rs.getDate(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_IN3]));
                        empschedule.setIn4(rs.getDate(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_IN4]));
                        empschedule.setIn5(rs.getDate(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_IN5]));
                        empschedule.setIn6(rs.getDate(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_IN6]));
                        empschedule.setIn7(rs.getDate(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_IN7]));
                        empschedule.setIn8(rs.getDate(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_IN8]));
                        empschedule.setIn9(rs.getDate(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_IN9]));
                        empschedule.setIn10(rs.getDate(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_IN10]));
                        empschedule.setIn11(rs.getDate(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_IN11]));
                        empschedule.setIn12(rs.getDate(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_IN12]));
                        empschedule.setIn13(rs.getDate(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_IN13]));
                        empschedule.setIn14(rs.getDate(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_IN14]));
                        empschedule.setIn15(rs.getDate(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_IN15]));
                        empschedule.setIn16(rs.getDate(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_IN16]));
                        empschedule.setIn17(rs.getDate(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_IN17]));
                        empschedule.setIn18(rs.getDate(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_IN18]));
                        empschedule.setIn19(rs.getDate(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_IN19]));
                        empschedule.setIn20(rs.getDate(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_IN20]));
                        empschedule.setIn21(rs.getDate(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_IN21]));
                        empschedule.setIn22(rs.getDate(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_IN22]));
                        empschedule.setIn23(rs.getDate(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_IN23]));
                        empschedule.setIn24(rs.getDate(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_IN24]));
                        empschedule.setIn25(rs.getDate(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_IN25]));
                        empschedule.setIn26(rs.getDate(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_IN26]));
                        empschedule.setIn27(rs.getDate(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_IN27]));
                        empschedule.setIn28(rs.getDate(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_IN28]));
                        empschedule.setIn29(rs.getDate(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_IN29]));
                        empschedule.setIn30(rs.getDate(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_IN30]));
                        empschedule.setIn31(rs.getDate(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_IN31]));
                        empschedule.setIn2nd1(rs.getDate(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_IN2ND1]));
                        empschedule.setIn2nd2(rs.getDate(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_IN2ND2]));
                        empschedule.setIn2nd3(rs.getDate(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_IN2ND3]));
                        empschedule.setIn2nd4(rs.getDate(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_IN2ND4]));
                        empschedule.setIn2nd5(rs.getDate(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_IN2ND5]));
                        empschedule.setIn2nd6(rs.getDate(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_IN2ND6]));
                        empschedule.setIn2nd7(rs.getDate(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_IN2ND7]));
                        empschedule.setIn2nd8(rs.getDate(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_IN2ND8]));
                        empschedule.setIn2nd9(rs.getDate(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_IN2ND9]));
                        empschedule.setIn2nd10(rs.getDate(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_IN2ND10]));
                        empschedule.setIn2nd11(rs.getDate(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_IN2ND11]));
                        empschedule.setIn2nd12(rs.getDate(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_IN2ND12]));
                        empschedule.setIn2nd13(rs.getDate(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_IN2ND13]));
                        empschedule.setIn2nd14(rs.getDate(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_IN2ND14]));
                        empschedule.setIn2nd15(rs.getDate(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_IN2ND15]));
                        empschedule.setIn2nd16(rs.getDate(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_IN2ND16]));
                        empschedule.setIn2nd17(rs.getDate(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_IN2ND17]));
                        empschedule.setIn2nd18(rs.getDate(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_IN2ND18]));
                        empschedule.setIn2nd19(rs.getDate(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_IN2ND19]));
                        empschedule.setIn2nd20(rs.getDate(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_IN2ND20]));
                        empschedule.setIn2nd21(rs.getDate(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_IN2ND21]));
                        empschedule.setIn2nd22(rs.getDate(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_IN2ND22]));
                        empschedule.setIn2nd23(rs.getDate(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_IN2ND23]));
                        empschedule.setIn2nd24(rs.getDate(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_IN2ND24]));
                        empschedule.setIn2nd25(rs.getDate(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_IN2ND25]));
                        empschedule.setIn2nd26(rs.getDate(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_IN2ND26]));
                        empschedule.setIn2nd27(rs.getDate(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_IN2ND27]));
                        empschedule.setIn2nd28(rs.getDate(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_IN2ND28]));
                        empschedule.setIn2nd29(rs.getDate(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_IN2ND29]));
                        empschedule.setIn2nd30(rs.getDate(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_IN2ND30]));
                        empschedule.setIn2nd31(rs.getDate(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_IN2ND31]));
                        
                        empschedule.setOut1(rs.getDate(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_OUT1]));
                        empschedule.setOut2(rs.getDate(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_OUT2]));
                        empschedule.setOut3(rs.getDate(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_OUT3]));
                        empschedule.setOut4(rs.getDate(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_OUT4]));
                        empschedule.setOut5(rs.getDate(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_OUT5]));
                        empschedule.setOut6(rs.getDate(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_OUT6]));
                        empschedule.setOut7(rs.getDate(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_OUT7]));
                        empschedule.setOut8(rs.getDate(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_OUT8]));
                        empschedule.setOut9(rs.getDate(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_OUT9]));
                        empschedule.setOut10(rs.getDate(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_OUT10]));
                        empschedule.setOut11(rs.getDate(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_OUT11]));
                        empschedule.setOut12(rs.getDate(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_OUT12]));
                        empschedule.setOut13(rs.getDate(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_OUT13]));
                        empschedule.setOut14(rs.getDate(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_OUT14]));
                        empschedule.setOut15(rs.getDate(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_OUT15]));
                        empschedule.setOut16(rs.getDate(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_OUT16]));
                        empschedule.setOut17(rs.getDate(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_OUT17]));
                        empschedule.setOut18(rs.getDate(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_OUT18]));
                        empschedule.setOut19(rs.getDate(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_OUT19]));
                        empschedule.setOut20(rs.getDate(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_OUT20]));
                        empschedule.setOut21(rs.getDate(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_OUT21]));
                        empschedule.setOut22(rs.getDate(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_OUT22]));
                        empschedule.setOut23(rs.getDate(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_OUT23]));
                        empschedule.setOut24(rs.getDate(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_OUT24]));
                        empschedule.setOut25(rs.getDate(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_OUT25]));
                        empschedule.setOut26(rs.getDate(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_OUT26]));
                        empschedule.setOut27(rs.getDate(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_OUT27]));
                        empschedule.setOut28(rs.getDate(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_OUT28]));
                        empschedule.setOut29(rs.getDate(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_OUT29]));
                        empschedule.setOut30(rs.getDate(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_OUT30]));
                        empschedule.setOut31(rs.getDate(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_OUT31]));
                        empschedule.setOut2nd1(rs.getDate(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_OUT2ND1]));
                        empschedule.setOut2nd2(rs.getDate(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_OUT2ND2]));
                        empschedule.setOut2nd3(rs.getDate(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_OUT2ND3]));
                        empschedule.setOut2nd4(rs.getDate(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_OUT2ND4]));
                        empschedule.setOut2nd5(rs.getDate(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_OUT2ND5]));
                        empschedule.setOut2nd6(rs.getDate(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_OUT2ND6]));
                        empschedule.setOut2nd7(rs.getDate(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_OUT2ND7]));
                        empschedule.setOut2nd8(rs.getDate(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_OUT2ND8]));
                        empschedule.setOut2nd9(rs.getDate(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_OUT2ND9]));
                        empschedule.setOut2nd10(rs.getDate(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_OUT2ND10]));
                        empschedule.setOut2nd11(rs.getDate(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_OUT2ND11]));
                        empschedule.setOut2nd12(rs.getDate(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_OUT2ND12]));
                        empschedule.setOut2nd13(rs.getDate(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_OUT2ND13]));
                        empschedule.setOut2nd14(rs.getDate(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_OUT2ND14]));
                        empschedule.setOut2nd15(rs.getDate(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_OUT2ND15]));
                        empschedule.setOut2nd16(rs.getDate(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_OUT2ND16]));
                        empschedule.setOut2nd17(rs.getDate(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_OUT2ND17]));
                        empschedule.setOut2nd18(rs.getDate(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_OUT2ND18]));
                        empschedule.setOut2nd19(rs.getDate(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_OUT2ND19]));
                        empschedule.setOut2nd20(rs.getDate(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_OUT2ND20]));                        
                        empschedule.setOut2nd21(rs.getDate(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_OUT2ND21]));
                        empschedule.setOut2nd22(rs.getDate(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_OUT2ND22]));
                        empschedule.setOut2nd23(rs.getDate(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_OUT2ND23]));
                        empschedule.setOut2nd24(rs.getDate(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_OUT2ND24]));
                        empschedule.setOut2nd25(rs.getDate(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_OUT2ND25]));
                        empschedule.setOut2nd26(rs.getDate(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_OUT2ND26]));
                        empschedule.setOut2nd27(rs.getDate(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_OUT2ND27]));
                        empschedule.setOut2nd28(rs.getDate(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_OUT2ND28]));
                        empschedule.setOut2nd29(rs.getDate(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_OUT2ND29]));
                        empschedule.setOut2nd30(rs.getDate(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_OUT2ND30]));                        
                        empschedule.setOut2nd31(rs.getDate(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_OUT2ND31]));                        
                        
                        empschedule.setScheduleType(rs.getInt(PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_SCHEDULE_TYPE]));                        
                        
                }catch(Exception e){ }
	}

	public static boolean checkOID(long empScheduleId){
		DBResultSet dbrs = null;
		boolean result = false;
		try{
			String sql = "SELECT * FROM " + TBL_HR_EMP_SCHEDULE + " WHERE " + 
						PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_EMP_SCHEDULE_ID] + " = '" + empScheduleId +"'";

			dbrs = DBHandler.execQueryResult(sql);
			ResultSet rs = dbrs.getResultSet();

			while(rs.next()) { result = true; }
			rs.close();
		}catch(Exception e){
			System.out.println("err : "+e.toString());
		}finally{
			DBResultSet.close(dbrs);
			return result;
		}
	}

	public static int getCount(String whereClause){
		DBResultSet dbrs = null;
		try {
			String sql = "SELECT COUNT("+ PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_EMP_SCHEDULE_ID] + ") FROM " + TBL_HR_EMP_SCHEDULE;
			if(whereClause != null && whereClause.length() > 0)
				sql = sql + " WHERE " + whereClause;

			dbrs = DBHandler.execQueryResult(sql);
			ResultSet rs = dbrs.getResultSet();

			int count = 0;
			while(rs.next()) { count = rs.getInt(1); }

			rs.close();
			return count;
		}catch(Exception e) {
			return 0;
		}finally {
			DBResultSet.close(dbrs);
		}
	}


	/* This method used to find current data */
	public static int findLimitStart( long oid, int recordToGet, String whereClause){
		String order = "";
		int size = getCount(whereClause);
		int start = 0;
		boolean found =false;
		for(int i=0; (i < size) && !found ; i=i+recordToGet){
			 Vector list =  list(i,recordToGet, whereClause, order); 
			 start = i;
			 if(list.size()>0){
			  for(int ls=0;ls<list.size();ls++){ 
			  	   EmpSchedule empschedule = (EmpSchedule)list.get(ls);
				   if(oid == empschedule.getOID())
					  found=true;
			  }
		  }
		}
		if((start >= size) && (size > 0))
		    start = start - recordToGet;

		return start;
	}
        
    public static long deleteByEmployee(long emplOID)
    {
    	try{
            String sql = " DELETE FROM "+PstEmpScheduleOrg.TBL_HR_EMP_SCHEDULE +
                 " WHERE " + PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_EMPLOYEE_ID] +
                 " = " + emplOID;
            int status = DBHandler.execUpdate(sql);
    	}
        catch(Exception exc){
        	System.out.println("error delete empschedule by employee "+exc.toString());
    	}
    	return emplOID;
    }

    public static boolean checkPeriode(long periodeId){
            DBResultSet dbrs = null;
            boolean result = false;
            try{
                    String sql = "SELECT * FROM " + TBL_HR_EMP_SCHEDULE + " WHERE " + 
                                            PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_PERIOD_ID] + " = '" + periodeId +"'";

                    dbrs = DBHandler.execQueryResult(sql);
                    ResultSet rs = dbrs.getResultSet();

                    while(rs.next()) { result = true; }
                    rs.close();
            }catch(Exception e){
                    System.out.println("err : "+e.toString());
            }finally{
                    DBResultSet.close(dbrs);
                    return result;
            }
    }

    public static boolean checkScheduleSymbol(long scheduleSymbolId){
		DBResultSet dbrs = null;
		boolean result = false;
		try{
			String sql = "SELECT * FROM " + TBL_HR_EMP_SCHEDULE + " WHERE " + 
                               PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_D1] + " = '" + scheduleSymbolId +"'"+
                        " OR "+PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_D2] + " = '" + scheduleSymbolId +"'"+
                        " OR "+PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_D3] + " = '" + scheduleSymbolId +"'"+
                        " OR "+PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_D4] + " = '" + scheduleSymbolId +"'"+
                        " OR "+PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_D5] + " = '" + scheduleSymbolId +"'"+
                        " OR "+PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_D6] + " = '" + scheduleSymbolId +"'"+
                        " OR "+PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_D7] + " = '" + scheduleSymbolId +"'"+
                        " OR "+PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_D8] + " = '" + scheduleSymbolId +"'"+
                        " OR "+PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_D9] + " = '" + scheduleSymbolId +"'"+
                        " OR "+PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_D10] + " = '" + scheduleSymbolId +"'"+
                        " OR "+PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_D11] + " = '" + scheduleSymbolId +"'"+
                        " OR "+PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_D12] + " = '" + scheduleSymbolId +"'"+
                        " OR "+PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_D13] + " = '" + scheduleSymbolId +"'"+
                        " OR "+PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_D14] + " = '" + scheduleSymbolId +"'"+
                        " OR "+PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_D15] + " = '" + scheduleSymbolId +"'"+
                        " OR "+PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_D16] + " = '" + scheduleSymbolId +"'"+
                        " OR "+PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_D17] + " = '" + scheduleSymbolId +"'"+
                        " OR "+PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_D18] + " = '" + scheduleSymbolId +"'"+
                        " OR "+PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_D19] + " = '" + scheduleSymbolId +"'"+
                        " OR "+PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_D20] + " = '" + scheduleSymbolId +"'"+
                        " OR "+PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_D21] + " = '" + scheduleSymbolId +"'"+
                        " OR "+PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_D22] + " = '" + scheduleSymbolId +"'"+
                        " OR "+PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_D23] + " = '" + scheduleSymbolId +"'"+
                        " OR "+PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_D24] + " = '" + scheduleSymbolId +"'"+
                        " OR "+PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_D25] + " = '" + scheduleSymbolId +"'"+
                        " OR "+PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_D26] + " = '" + scheduleSymbolId +"'"+
                        " OR "+PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_D27] + " = '" + scheduleSymbolId +"'"+
                        " OR "+PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_D28] + " = '" + scheduleSymbolId +"'"+
                        " OR "+PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_D29] + " = '" + scheduleSymbolId +"'"+
                        " OR "+PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_D30] + " = '" + scheduleSymbolId +"'"+
                        " OR "+PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_D31] + " = '" + scheduleSymbolId +"'";

			dbrs = DBHandler.execQueryResult(sql);
			ResultSet rs = dbrs.getResultSet();

			while(rs.next()) { result = true; }
			rs.close();
		}catch(Exception e){
			System.out.println("err : "+e.toString());
		}finally{
			DBResultSet.close(dbrs);
			return result;
		}
	}
    
    
    /**
     * get apopriate field on empschedule table
     * @param selectedDate
     * @return
     * @created by Edhy
     */    
    public static int getIdxNameOfTableBySelectedDate(Date selectedDate)
    {
        return selectedDate.getDate();
    }    
    
    /** 
     * get field index that will update
     * @param periodId
     * @param employeeId
     * @param presenceStatus
     * @param idxFieldName
     * @return
     * @created by Edhy
     */        
    public static Vector getFieldIndexWillUpdated(long periodId, long employeeId, int presenceStatus, Date presenceDate)
    {
        Vector result = new Vector();
        int indexResult = -1;
        int idxFieldName = getIdxNameOfTableBySelectedDate(presenceDate);
        long periodIdBeforeCurr = PstPeriod.getPeriodIdJustBefore(periodId); 
        DBResultSet dbrs = null;         
        try {
                String sql = "SELECT CAT." + PstScheduleCategory.fieldNames[PstScheduleCategory.FLD_CATEGORY_TYPE] +                                                      
                             ", SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_TIME_IN] +
                             ", SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_TIME_OUT] +                
                             ", SYM2." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_TIME_IN] +
                             ", SYM2." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_TIME_OUT] +                                   
                             " FROM " + PstEmpScheduleOrg.TBL_HR_EMP_SCHEDULE + " AS SCH" +   
                             " INNER JOIN " + PstScheduleSymbol.TBL_HR_SCHEDULE_SYMBOL + " AS SYM" +
                             " ON SCH." + PstEmpScheduleOrg.fieldNames[OFFSET_INDEX_CALENDAR + idxFieldName - 1] +
                             " = SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID] + 
                             " LEFT JOIN " + PstScheduleSymbol.TBL_HR_SCHEDULE_SYMBOL + " AS SYM2" +
                             " ON SCH." + PstEmpScheduleOrg.fieldNames[OFFSET_INDEX_CALENDAR + INTERVAL_INDEX_HALF_CALENDAR + idxFieldName - 1] +
                             " = SYM2." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID] + 
                             " INNER JOIN " + PstScheduleCategory.TBL_HR_SCHEDULE_CATEGORY + " AS CAT" +                             
                             " ON SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_CATEGORY_ID] +                             
                             " = CAT." + PstScheduleCategory.fieldNames[PstScheduleCategory.FLD_SCHEDULE_CATEGORY_ID] +                             
                             " WHERE SCH." + PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_PERIOD_ID] + 
                             " = " + periodId + 
                             " AND SCH." + PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_EMPLOYEE_ID] + 
                             " = " + employeeId;  
                      
                //System.out.println("\tgetFieldIndexWillUpdated : "+sql);
                dbrs = DBHandler.execQueryResult(sql);
                ResultSet rs = dbrs.getResultSet();     
                
                System.out.println("presenceDate : "+presenceDate);
                long longPresenceTime = presenceDate.getTime();
                int intYear = presenceDate.getYear();
                int intMonth = presenceDate.getMonth();
                int intDate = presenceDate.getDate();
                
                long longSchld1stIn = 0;
                long longSchld1stOut = 0;
                long longSchld2ndIn = 0;
                long longSchld2ndOut = 0;
                
                boolean rsNull = true;
                while(rs.next()) 
                { 
                    rsNull = false;
                    
                    // first schedule IN
                    if(rs.getTime(2)!=null)
                    {                    
                        longSchld1stIn = (new Date(intYear,intMonth,intDate,rs.getTime(2).getHours(),rs.getTime(2).getMinutes(),rs.getTime(2).getSeconds())).getTime();
                    }    
                    
                    // first schedule OUT
                    if(rs.getTime(3)!=null)
                    {                    
                        longSchld1stOut = (new Date(intYear,intMonth,intDate,rs.getTime(3).getHours(),rs.getTime(3).getMinutes(),rs.getTime(3).getSeconds())).getTime();
                    }    

                    // second schedule IN
                    if(rs.getTime(4)!=null)
                    {
                        longSchld2ndIn = (new Date(intYear,intMonth,intDate,rs.getTime(4).getHours(),rs.getTime(4).getMinutes(),rs.getTime(4).getSeconds())).getTime();                        
                    }
                    
                    // second schedule OUT
                    if(rs.getTime(5)!=null)
                    {                        
                        longSchld2ndOut = (new Date(intYear,intMonth,intDate,rs.getTime(5).getHours(),rs.getTime(5).getMinutes(),rs.getTime(5).getSeconds())).getTime();                    
                    }

                    
                    // tipe schedule adalah SPLIT SHIFT
                    if( rs.getInt(1) == PstScheduleCategory.CATEGORY_SPLIT_SHIFT )
                    {                                               
                        // tipe presence adalah IN
                        if(presenceStatus==STATUS_IN || presenceStatus==STATUS_IN_BREAK || presenceStatus==STATUS_IN_CALLBACK || presenceStatus==STATUS_IN_LUNCH)
                        {    
                            // waktu ACTUAL_PRESENCE sebelum FIRST_SCHEDULE_PRESENCE_IN maka :                             
                            //   - update IN schedule I
                            //   - presence OK                        
                            if(longPresenceTime < longSchld1stIn)
                            {
                                System.out.println("IN SPLIT longPresenceTime < longSchld1stIn ==> D1st and OK");
                                indexResult = OFFSET_INDEX_IN + idxFieldName - 1;      
                                result.add(String.valueOf(periodId));
                                result.add(String.valueOf(indexResult));
                            } 

                            // waktu ACTUAL_PRESENCE diantara FIRST_SCHEDULE_PRESENCE_IN dan FIRST_SCHEDULE_PRESENCE_OUT maka : 
                            //   - update IN schedule I
                            //   - presence LATE
                            if((longSchld1stIn < longPresenceTime) && (longPresenceTime < longSchld1stOut))
                            {                                
                                System.out.println("IN SPLIT (longSchld1stIn < longPresenceTime) && (longPresenceTime < longSchld1stOut) ==> D1st and LATE");
                                indexResult = OFFSET_INDEX_IN + idxFieldName - 1; 
                                result.add(String.valueOf(periodId));
                                result.add(String.valueOf(indexResult));                                
                            }

                            // waktu ACTUAL_PRESENCE diantara FIRST_SCHEDULE_PRESENCE_OUT dan SECOND_SCHEDULE_PRESENCE_IN maka : 
                            //   - update IN schedule II
                            //   - presence OK
                            if((longSchld1stOut < longPresenceTime) && (longPresenceTime < longSchld2ndIn))
                            {                                                         
                                System.out.println("IN SPLIT (longSchld1stOut < longPresenceTime) && (longPresenceTime < longSchld2ndIn) ==> D2nd and OK");                                
                                indexResult = OFFSET_INDEX_IN + INTERVAL_INDEX_HALF_CALENDAR + idxFieldName - 1;      
                                result.add(String.valueOf(periodId));
                                result.add(String.valueOf(indexResult));                                
                            }                        

                            // waktu ACTUAL_PRESENCE lebih dari SECOND_SCHEDULE_PRESENCE_IN maka : 
                            //   - update IN schedule II
                            //   - presence LATE
                            if(longPresenceTime > longSchld2ndIn)
                            {                         
                                System.out.println("IN SPLIT longPresenceTime > longSchld2ndIn ==> D2nd and LATE");                                
                                indexResult = OFFSET_INDEX_IN + INTERVAL_INDEX_HALF_CALENDAR + idxFieldName - 1;      
                                result.add(String.valueOf(periodId));
                                result.add(String.valueOf(indexResult));                                                                
                            }                        
                        }         
                    
                        
                        // tipe presence adalah OUT
                        else
                        {                            
                            
                            // waktu ACTUAL_OUT sebelum FIRST_SCHEDULE_PRESENCE_OUT maka : 
                            //   - update OUT schedule I
                            //   - out MENDAHULUI                        
                            if(longPresenceTime < longSchld1stOut)
                            {                             
                                System.out.println("OUT SPLIT longPresenceTime < longSchld1stOut ==> D1st and MENDAHULUI");
                                indexResult = OFFSET_INDEX_OUT + idxFieldName - 1;      
                                result.add(String.valueOf(periodId));
                                result.add(String.valueOf(indexResult));                                                                
                            }

                            // waktu ACTUAL_OUT diantara FIRST_SCHEDULE_PRESENCE_OUT dan SECOND_SCHEDULE_PRESENCE_IN maka : 
                            //   - update OUT schedule I
                            //   - out OK
                            if((longSchld1stOut < longPresenceTime) && (longPresenceTime < longSchld2ndIn))
                            {                                
                                System.out.println("OUT SPLIT (longSchld1stOut < longPresenceTime) && (longPresenceTime < longSchld2ndIn) ==> D1st and OK");
                                indexResult = OFFSET_INDEX_OUT + idxFieldName - 1;      
                                result.add(String.valueOf(periodId));
                                result.add(String.valueOf(indexResult));                                                                
                            }

                            // waktu ACTUAL_OUT antara SECOND_SCHEDULE_PRESENCE_IN dan SECOND_SCHEDULE_PRESENCE_OUT maka : 
                            //   - update OUT schedule II
                            //   - out MENDAHULUI
                            if((longSchld2ndIn < longPresenceTime) && (longPresenceTime < longSchld2ndOut))
                            {                                                         
                                System.out.println("OUT SPLIT (longSchld2ndIn < longPresenceTime) && (longPresenceTime < longSchld2ndOut) ==> D2nd and MENDAHULUI");
                                indexResult = OFFSET_INDEX_OUT + INTERVAL_INDEX_HALF_CALENDAR + idxFieldName - 1;      
                                result.add(String.valueOf(periodId));
                                result.add(String.valueOf(indexResult));                                                                
                            }                        

                            // waktu ACTUAL_OUT setelah SECOND_SCHEDULE_PRESENCE_OUT maka : 
                            //   - update OUT schedule II
                            //   - out OK
                            if(longPresenceTime > longSchld2ndOut)
                            {                                                         
                                System.out.println("OUT SPLIT longPresenceTime > longSchld2ndOut ==> D2nd and OK");
                                indexResult = OFFSET_INDEX_OUT + INTERVAL_INDEX_HALF_CALENDAR + idxFieldName - 1;      
                                result.add(String.valueOf(periodId));
                                result.add(String.valueOf(indexResult));                                                                
                            }                                                    
                        }                        
                    }
                    
                    
                    
                    // tipe schedule selain SPLIT SHIFT (seperti : REGULAR / NIGHT WORKER)
                    else
                    {                     
                        if(presenceStatus==STATUS_IN || presenceStatus==STATUS_IN_BREAK || presenceStatus==STATUS_IN_CALLBACK || presenceStatus==STATUS_IN_LUNCH)
                        {                                                      
                            System.out.println("IN REGULAR / NIGHT SHIFT");                            
                            indexResult = OFFSET_INDEX_IN + idxFieldName - 1;                                   
                            result.add(String.valueOf(periodId));
                            result.add(String.valueOf(indexResult));                                                            
                        }
                        else
                        {
                            // ngecek apakah out night shift atau tidak
                            if(rs.getInt(1) == PstScheduleCategory.CATEGORY_NIGHT_WORKER)
                            {    
                                // ngecek jam keluar, kalo jam keluar dibawah jam 10.00 berarti sesuai dengan schedule hr sebelumnya
                                // kalo tidak berarti change roster
                                if(presenceDate.getHours() <= 10)   
                                {
                                    // jika OUT pada tanggal satu maka merupakan schedule pada
                                    // periode sebelumnya
                                    if(presenceDate.getDate() == 1)
                                    {                                        
                                        System.out.println("OUT NIGHT SHIFT LAST SCHEDULE ==> OK");
                                        indexResult = OFFSET_INDEX_OUT + idxFieldName - 2;                   
                                        result.add(String.valueOf(periodIdBeforeCurr));
                                        result.add(String.valueOf(indexResult));                                                                                                        
                                    }
                                    else
                                    {
                                        System.out.println("OUT NIGHT SHIFT ==> OK");
                                        indexResult = OFFSET_INDEX_OUT + idxFieldName - 2;                   
                                        result.add(String.valueOf(periodId));
                                        result.add(String.valueOf(indexResult));                                                                                                                                                
                                    }
                                }
                                else
                                {
                                    System.out.println("OUT NIGHT SHIFT ==> CHANGE ROSTER");
                                    indexResult = OFFSET_INDEX_OUT + idxFieldName - 1;   
                                    result.add(String.valueOf(periodId));
                                    result.add(String.valueOf(indexResult));                                                                                                    
                                }
                            }  
                            else
                            {                                
                                System.out.println("OUT REGULAR");
                                indexResult = OFFSET_INDEX_OUT + idxFieldName - 1;    
                                result.add(String.valueOf(periodId));
                                result.add(String.valueOf(indexResult));                                                                                                
                            }
                        }                                                    
                    }                                        
                }  
                
                if(rsNull)
                {                    
                    System.out.println("\tpresence without schedule ...");
                }
                else
                {
                    System.out.println("result try : "+result);
                }
                
                System.out.println("indexResult : "+indexResult);
                return result;
        }
        catch(Exception e) 
        {
            System.out.println("getFieldIndexWillUpdated Exception : "+e.toString());
            return new Vector(1,1);
        }  
        finally 
        {
            DBResultSet.close(dbrs);
        }                  
    }    
    

    
    /** 
     * get field index that will update
     * @param periodId
     * @param employeeId
     * @param presenceStatus
     * @param idxFieldName
     * @return
     * @created by Edhy
     */        
    public static int updateScheduleDataByPresence(long periodId, long employeeId, int idxFieldName, Date presenceDate)
    {
        int result = 0;
        if(periodId!=0 && idxFieldName!=-1)      
        {
            DBResultSet dbrs = null;
            String strPresenceTime = "\"" + Formater.formatDate(presenceDate,"yyyy-MM-dd HH:mm:ss") + "\"";
            try {
                String sql = "UPDATE " + PstEmpScheduleOrg.TBL_HR_EMP_SCHEDULE + 
                             " SET " + PstEmpScheduleOrg.fieldNames[idxFieldName] +
                             " = " + strPresenceTime +                              
                             " WHERE " + PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_PERIOD_ID] + 
                             " = " + periodId + 
                             " AND " + PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_EMPLOYEE_ID] + 
                             " = " + employeeId; 

                System.out.println("\tupdateScheduleDataByPresence : "+sql);  
                result = DBHandler.execUpdate(sql);                
            }
            catch(Exception e) 
            {
                System.out.println("Exc updateScheduleDataByPresence : "+e.toString());
            }
            finally 
            {
                DBResultSet.close(dbrs);
                return result;
            }                  
        }
        else
        {
            System.out.println("Index updated is -1");
            return result;    
        }
    }    

    
    /**
     * import data from present to emp schedule
     * @return
     * @created by Edhy
     */      
    public static void importDataFromPresence()   
    {
        String whereClause = PstPresence.fieldNames[PstPresence.FLD_TRANSFERRED] + "=" + PstPresence.PRESENCE_NOT_TRANSFERRED;        
        String orderBy = PstPresence.fieldNames[PstPresence.FLD_PRESENCE_DATETIME];
        Vector listPresence = PstPresence.list(0,0,whereClause,orderBy);         
        if(listPresence != null && listPresence.size()>0)  
        {
            int maxPresence = listPresence.size();                
            for(int i=0; i<maxPresence; i++)
            {
                Presence presence = (Presence)listPresence.get(i);

                // update presence (IN or OUT) on employee schedule  
                long periodId = PstPeriod.getPeriodIdBySelectedDate(presence.getPresenceDatetime());                                
                //int updatedFieldIndex = getFieldIndexWillUpdated(periodId, presence.getEmployeeId(), presence.getStatus(), presence.getPresenceDatetime());                                
                
                int updatedFieldIndex = -1;  
                long updatePeriodId = periodId;
                Vector vectFieldIndex = getFieldIndexWillUpdated(periodId, presence.getEmployeeId(), presence.getStatus(), presence.getPresenceDatetime());                                
                if(vectFieldIndex!=null && vectFieldIndex.size()==2)
                {
                    updatePeriodId = Long.parseLong(String.valueOf(vectFieldIndex.get(0)));
                    updatedFieldIndex = Integer.parseInt(String.valueOf(vectFieldIndex.get(1)));
                }
                
                int updateStatus = 0;  
                try
                {
                    updateStatus = PstEmpScheduleOrg.updateScheduleDataByPresence(updatePeriodId, presence.getEmployeeId(), updatedFieldIndex, presence.getPresenceDatetime());                    
                    if(updateStatus>0)
                    {
                        presence.setTransferred(PstPresence.PRESENCE_TRANSFERRED);
                        PstPresence.updateExc(presence);
                    }
                    
                }
                catch(Exception e)
                {
                    System.out.println("Update Presence exc : "+e.toString());
                }
                
            }
        }
    }
    

    /**
     * update reason and status of employee schedule based on absence management data
     * @param empScheduleOid
     * @param vectAbsenceDate
     * @param vectAbsenceReason
     * @param vectAbsenceNotes
     * @created by Edhy
     * @return     
     */        
    public static int updateScheduleByAbsenceManagement(long empScheduleOid, Vector vectAbsenceDate, Vector vectAbsenceReason, Vector vectAbsenceNotes)
    {
        int result = 0;        
        if(vectAbsenceDate!=null && vectAbsenceDate.size()>0)
        {
            String sql = "UPDATE " + PstEmpScheduleOrg.TBL_HR_EMP_SCHEDULE + " SET ";
            
            // generate update query value
            int maxAbsenceDate = vectAbsenceDate.size();
            String sqlUpdate = "";
            for(int i=0; i<maxAbsenceDate; i++)
            {
                int intAbsenceDate = Integer.parseInt(String.valueOf(vectAbsenceDate.get(i)));
                int intAbsenceReason = Integer.parseInt(String.valueOf(vectAbsenceReason.get(i)));
                String strAbsenceNote = String.valueOf(vectAbsenceNotes.get(i));
                sqlUpdate = sqlUpdate + PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.OFFSET_INDEX_REASON + intAbsenceDate-1] + " = " + intAbsenceReason + 
                            "," + PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.OFFSET_INDEX_NOTE + intAbsenceDate-1] + " = \"" + strAbsenceNote + "\",";
            }            
            
            // fixing update query
            if(sqlUpdate!=null && sqlUpdate.length()>0)
            {
                sqlUpdate = sqlUpdate.substring(0,sqlUpdate.length()-1);
                sql = sql + sqlUpdate + " WHERE " + PstEmpScheduleOrg.fieldNames[PstEmpScheduleOrg.FLD_EMP_SCHEDULE_ID] + " = " + empScheduleOid;
            }
            
            
            try
            {
                //System.out.println("\tUpdateScheduleByAbsenceManagement sql : "+sql);  
                result = DBHandler.execUpdate(sql);                                
            }
            catch(Exception e)
            {
                System.out.println("Exception on UpdateScheduleByAbsenceManagement : "+e.toString());
            }
            
        }        
        
        return result;
    }    
    
    
    public static void main(String args[])     
    {
        System.out.println(".:: importDataFromPresence start");  
        importDataFromPresence();
        System.out.println(".:: importDataFromPresence finish");   
        /*        
        long periodId = 504404240112750884L;
        long employeeId = 504404240100958350L;
        int presenceStatus = 0;   
        int idxFieldName = 2;   
        Date newDate = new Date();
        //boolean result = existDataOnSelectedField(periodId, employeeId, presenceStatus, idxFieldName);
        int result = getFieldIndexWillUpdated(periodId, employeeId, presenceStatus, newDate);
        System.out.println("result : "+result);
        */
    }
    
}
