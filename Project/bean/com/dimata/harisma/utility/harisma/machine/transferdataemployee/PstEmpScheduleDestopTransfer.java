
/* Created on 	:  [date] [time] AM/PM 
 * 
 * @author  	: Satrya Ramayu
 * @version  	: 01
 */
/*******************************************************************
 * Class Description 	: [project description ... ] 
 * Imput Parameters 	: [input parameter ...] 
 * Output 		: [output ...] 
 *******************************************************************/
package com.dimata.harisma.utility.harisma.machine.transferdataemployee;

/* package java */
import com.dimata.harisma.entity.attendance.PstEmpSchedule;
import java.sql.*;
import java.util.*;
import java.util.Date;

/* package qdep */
import com.dimata.util.lang.I_Language;
import com.dimata.util.*;
import com.dimata.qdep.entity.*;

//Gede_7Feb2012 {
import com.dimata.harisma.session.employee.*;
import com.dimata.harisma.entity.search.*;
import com.dimata.harisma.entity.payroll.*;
import com.dimata.harisma.entity.masterdata.*;
//}

/* package harisma */
//import com.dimata.harisma.db.DBHandler;
//import com.dimata.harisma.db.DBException;
//import com.dimata.harisma.db.DBLogger;
import com.dimata.harisma.entity.employee.*;
import com.dimata.harisma.utility.harisma.machine.db.DBException;
import com.dimata.harisma.utility.harisma.machine.db.DBHandler;
import com.dimata.harisma.utility.harisma.machine.db.DBResultSet;
import com.dimata.harisma.utility.harisma.machine.db.I_DBInterface;
import com.dimata.harisma.utility.harisma.machine.db.I_DBType;

/**
 * Ari_20111002
 * Menambah Company, Division, Level dan EmpCategory
 * @author Wiweka
 */
public class PstEmpScheduleDestopTransfer extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

    public static final String TBL_HR_EMP_SCHEDULE = "hr_emp_schedule";//"HR_EMP_SCHEDULE";
    public static final int FLD_EMP_SCHEDULE_ID = 0;
    public static final int FLD_PERIOD_ID = 1;
    public static final int FLD_EMPLOYEE_ID = 2;
    public static final int FLD_D1 = 3;
    public static final int FLD_D2 = 4;
    public static final int FLD_D3 = 5;
    public static final int FLD_D4 = 6;
    public static final int FLD_D5 = 7;
    public static final int FLD_D6 = 8;
    public static final int FLD_D7 = 9;
    public static final int FLD_D8 = 10;
    public static final int FLD_D9 = 11;
    public static final int FLD_D10 = 12;
    public static final int FLD_D11 = 13;
    public static final int FLD_D12 = 14;
    public static final int FLD_D13 = 15;
    public static final int FLD_D14 = 16;
    public static final int FLD_D15 = 17;
    public static final int FLD_D16 = 18;
    public static final int FLD_D17 = 19;
    public static final int FLD_D18 = 20;
    public static final int FLD_D19 = 21;
    public static final int FLD_D20 = 22;
    public static final int FLD_D21 = 23;
    public static final int FLD_D22 = 24;
    public static final int FLD_D23 = 25;
    public static final int FLD_D24 = 26;
    public static final int FLD_D25 = 27;
    public static final int FLD_D26 = 28;
    public static final int FLD_D27 = 29;
    public static final int FLD_D28 = 30;
    public static final int FLD_D29 = 31;
    public static final int FLD_D30 = 32;
    public static final int FLD_D31 = 33;
    // added by Edhy for split shift
    public static final int FLD_D2ND1 = 34;
    public static final int FLD_D2ND2 = 35;
    public static final int FLD_D2ND3 = 36;
    public static final int FLD_D2ND4 = 37;
    public static final int FLD_D2ND5 = 38;
    public static final int FLD_D2ND6 = 39;
    public static final int FLD_D2ND7 = 40;
    public static final int FLD_D2ND8 = 41;
    public static final int FLD_D2ND9 = 42;
    public static final int FLD_D2ND10 = 43;
    public static final int FLD_D2ND11 = 44;
    public static final int FLD_D2ND12 = 45;
    public static final int FLD_D2ND13 = 46;
    public static final int FLD_D2ND14 = 47;
    public static final int FLD_D2ND15 = 48;
    public static final int FLD_D2ND16 = 49;
    public static final int FLD_D2ND17 = 50;
    public static final int FLD_D2ND18 = 51;
    public static final int FLD_D2ND19 = 52;
    public static final int FLD_D2ND20 = 53;
    public static final int FLD_D2ND21 = 54;
    public static final int FLD_D2ND22 = 55;
    public static final int FLD_D2ND23 = 56;
    public static final int FLD_D2ND24 = 57;
    public static final int FLD_D2ND25 = 58;
    public static final int FLD_D2ND26 = 59;
    public static final int FLD_D2ND27 = 60;
    public static final int FLD_D2ND28 = 61;
    public static final int FLD_D2ND29 = 62;
    public static final int FLD_D2ND30 = 63;
    public static final int FLD_D2ND31 = 64;
    public static final int FLD_STATUS1 = 65;
    public static final int FLD_STATUS2 = 66;
    public static final int FLD_STATUS3 = 67;
    public static final int FLD_STATUS4 = 68;
    public static final int FLD_STATUS5 = 69;
    public static final int FLD_STATUS6 = 70;
    public static final int FLD_STATUS7 = 71;
    public static final int FLD_STATUS8 = 72;
    public static final int FLD_STATUS9 = 73;
    public static final int FLD_STATUS10 = 74;
    public static final int FLD_STATUS11 = 75;
    public static final int FLD_STATUS12 = 76;
    public static final int FLD_STATUS13 = 77;
    public static final int FLD_STATUS14 = 78;
    public static final int FLD_STATUS15 = 79;
    public static final int FLD_STATUS16 = 80;
    public static final int FLD_STATUS17 = 81;
    public static final int FLD_STATUS18 = 82;
    public static final int FLD_STATUS19 = 83;
    public static final int FLD_STATUS20 = 84;
    public static final int FLD_STATUS21 = 85;
    public static final int FLD_STATUS22 = 86;
    public static final int FLD_STATUS23 = 87;
    public static final int FLD_STATUS24 = 88;
    public static final int FLD_STATUS25 = 89;
    public static final int FLD_STATUS26 = 90;
    public static final int FLD_STATUS27 = 91;
    public static final int FLD_STATUS28 = 92;
    public static final int FLD_STATUS29 = 93;
    public static final int FLD_STATUS30 = 94;
    public static final int FLD_STATUS31 = 95;
    public static final int FLD_STATUS2ND1 = 96;
    public static final int FLD_STATUS2ND2 = 97;
    public static final int FLD_STATUS2ND3 = 98;
    public static final int FLD_STATUS2ND4 = 99;
    public static final int FLD_STATUS2ND5 = 100;
    public static final int FLD_STATUS2ND6 = 101;
    public static final int FLD_STATUS2ND7 = 102;
    public static final int FLD_STATUS2ND8 = 103;
    public static final int FLD_STATUS2ND9 = 104;
    public static final int FLD_STATUS2ND10 = 105;
    public static final int FLD_STATUS2ND11 = 106;
    public static final int FLD_STATUS2ND12 = 107;
    public static final int FLD_STATUS2ND13 = 108;
    public static final int FLD_STATUS2ND14 = 109;
    public static final int FLD_STATUS2ND15 = 110;
    public static final int FLD_STATUS2ND16 = 111;
    public static final int FLD_STATUS2ND17 = 112;
    public static final int FLD_STATUS2ND18 = 113;
    public static final int FLD_STATUS2ND19 = 114;
    public static final int FLD_STATUS2ND20 = 115;
    public static final int FLD_STATUS2ND21 = 116;
    public static final int FLD_STATUS2ND22 = 117;
    public static final int FLD_STATUS2ND23 = 118;
    public static final int FLD_STATUS2ND24 = 119;
    public static final int FLD_STATUS2ND25 = 120;
    public static final int FLD_STATUS2ND26 = 121;
    public static final int FLD_STATUS2ND27 = 122;
    public static final int FLD_STATUS2ND28 = 123;
    public static final int FLD_STATUS2ND29 = 124;
    public static final int FLD_STATUS2ND30 = 125;
    public static final int FLD_STATUS2ND31 = 126;
    public static final int FLD_REASON1 = 127;
    public static final int FLD_REASON2 = 128;
    public static final int FLD_REASON3 = 129;
    public static final int FLD_REASON4 = 130;
    public static final int FLD_REASON5 = 131;
    public static final int FLD_REASON6 = 132;
    public static final int FLD_REASON7 = 133;
    public static final int FLD_REASON8 = 134;
    public static final int FLD_REASON9 = 135;
    public static final int FLD_REASON10 = 136;
    public static final int FLD_REASON11 = 137;
    public static final int FLD_REASON12 = 138;
    public static final int FLD_REASON13 = 139;
    public static final int FLD_REASON14 = 140;
    public static final int FLD_REASON15 = 141;
    public static final int FLD_REASON16 = 142;
    public static final int FLD_REASON17 = 143;
    public static final int FLD_REASON18 = 144;
    public static final int FLD_REASON19 = 145;
    public static final int FLD_REASON20 = 146;
    public static final int FLD_REASON21 = 147;
    public static final int FLD_REASON22 = 148;
    public static final int FLD_REASON23 = 149;
    public static final int FLD_REASON24 = 150;
    public static final int FLD_REASON25 = 151;
    public static final int FLD_REASON26 = 152;
    public static final int FLD_REASON27 = 153;
    public static final int FLD_REASON28 = 154;
    public static final int FLD_REASON29 = 155;
    public static final int FLD_REASON30 = 156;
    public static final int FLD_REASON31 = 157;
    public static final int FLD_REASON2ND1 = 158;
    public static final int FLD_REASON2ND2 = 159;
    public static final int FLD_REASON2ND3 = 160;
    public static final int FLD_REASON2ND4 = 161;
    public static final int FLD_REASON2ND5 = 162;
    public static final int FLD_REASON2ND6 = 163;
    public static final int FLD_REASON2ND7 = 164;
    public static final int FLD_REASON2ND8 = 165;
    public static final int FLD_REASON2ND9 = 166;
    public static final int FLD_REASON2ND10 = 167;
    public static final int FLD_REASON2ND11 = 168;
    public static final int FLD_REASON2ND12 = 169;
    public static final int FLD_REASON2ND13 = 170;
    public static final int FLD_REASON2ND14 = 171;
    public static final int FLD_REASON2ND15 = 172;
    public static final int FLD_REASON2ND16 = 173;
    public static final int FLD_REASON2ND17 = 174;
    public static final int FLD_REASON2ND18 = 175;
    public static final int FLD_REASON2ND19 = 176;
    public static final int FLD_REASON2ND20 = 177;
    public static final int FLD_REASON2ND21 = 178;
    public static final int FLD_REASON2ND22 = 179;
    public static final int FLD_REASON2ND23 = 180;
    public static final int FLD_REASON2ND24 = 181;
    public static final int FLD_REASON2ND25 = 182;
    public static final int FLD_REASON2ND26 = 183;
    public static final int FLD_REASON2ND27 = 184;
    public static final int FLD_REASON2ND28 = 185;
    public static final int FLD_REASON2ND29 = 186;
    public static final int FLD_REASON2ND30 = 187;
    public static final int FLD_REASON2ND31 = 188;
    public static final int FLD_NOTE1 = 189;
    public static final int FLD_NOTE2 = 190;
    public static final int FLD_NOTE3 = 191;
    public static final int FLD_NOTE4 = 192;
    public static final int FLD_NOTE5 = 193;
    public static final int FLD_NOTE6 = 194;
    public static final int FLD_NOTE7 = 195;
    public static final int FLD_NOTE8 = 196;
    public static final int FLD_NOTE9 = 197;
    public static final int FLD_NOTE10 = 198;
    public static final int FLD_NOTE11 = 199;
    public static final int FLD_NOTE12 = 200;
    public static final int FLD_NOTE13 = 201;
    public static final int FLD_NOTE14 = 202;
    public static final int FLD_NOTE15 = 203;
    public static final int FLD_NOTE16 = 204;
    public static final int FLD_NOTE17 = 205;
    public static final int FLD_NOTE18 = 206;
    public static final int FLD_NOTE19 = 207;
    public static final int FLD_NOTE20 = 208;
    public static final int FLD_NOTE21 = 209;
    public static final int FLD_NOTE22 = 210;
    public static final int FLD_NOTE23 = 211;
    public static final int FLD_NOTE24 = 212;
    public static final int FLD_NOTE25 = 213;
    public static final int FLD_NOTE26 = 214;
    public static final int FLD_NOTE27 = 215;
    public static final int FLD_NOTE28 = 216;
    public static final int FLD_NOTE29 = 217;
    public static final int FLD_NOTE30 = 218;
    public static final int FLD_NOTE31 = 219;
    public static final int FLD_NOTE2ND1 = 220;
    public static final int FLD_NOTE2ND2 = 221;
    public static final int FLD_NOTE2ND3 = 222;
    public static final int FLD_NOTE2ND4 = 223;
    public static final int FLD_NOTE2ND5 = 224;
    public static final int FLD_NOTE2ND6 = 225;
    public static final int FLD_NOTE2ND7 = 226;
    public static final int FLD_NOTE2ND8 = 227;
    public static final int FLD_NOTE2ND9 = 228;
    public static final int FLD_NOTE2ND10 = 229;
    public static final int FLD_NOTE2ND11 = 230;
    public static final int FLD_NOTE2ND12 = 231;
    public static final int FLD_NOTE2ND13 = 232;
    public static final int FLD_NOTE2ND14 = 233;
    public static final int FLD_NOTE2ND15 = 234;
    public static final int FLD_NOTE2ND16 = 235;
    public static final int FLD_NOTE2ND17 = 236;
    public static final int FLD_NOTE2ND18 = 237;
    public static final int FLD_NOTE2ND19 = 238;
    public static final int FLD_NOTE2ND20 = 239;
    public static final int FLD_NOTE2ND21 = 240;
    public static final int FLD_NOTE2ND22 = 241;
    public static final int FLD_NOTE2ND23 = 242;
    public static final int FLD_NOTE2ND24 = 243;
    public static final int FLD_NOTE2ND25 = 244;
    public static final int FLD_NOTE2ND26 = 245;
    public static final int FLD_NOTE2ND27 = 246;
    public static final int FLD_NOTE2ND28 = 247;
    public static final int FLD_NOTE2ND29 = 248;
    public static final int FLD_NOTE2ND30 = 249;
    public static final int FLD_NOTE2ND31 = 250;
    public static final int FLD_IN1 = 251;
    public static final int FLD_IN2 = 252;
    public static final int FLD_IN3 = 253;
    public static final int FLD_IN4 = 254;
    public static final int FLD_IN5 = 255;
    public static final int FLD_IN6 = 256;
    public static final int FLD_IN7 = 257;
    public static final int FLD_IN8 = 258;
    public static final int FLD_IN9 = 259;
    public static final int FLD_IN10 = 260;
    public static final int FLD_IN11 = 261;
    public static final int FLD_IN12 = 262;
    public static final int FLD_IN13 = 263;
    public static final int FLD_IN14 = 264;
    public static final int FLD_IN15 = 265;
    public static final int FLD_IN16 = 266;
    public static final int FLD_IN17 = 267;
    public static final int FLD_IN18 = 268;
    public static final int FLD_IN19 = 269;
    public static final int FLD_IN20 = 270;
    public static final int FLD_IN21 = 271;
    public static final int FLD_IN22 = 272;
    public static final int FLD_IN23 = 273;
    public static final int FLD_IN24 = 274;
    public static final int FLD_IN25 = 275;
    public static final int FLD_IN26 = 276;
    public static final int FLD_IN27 = 277;
    public static final int FLD_IN28 = 278;
    public static final int FLD_IN29 = 279;
    public static final int FLD_IN30 = 280;
    public static final int FLD_IN31 = 281;
    public static final int FLD_IN2ND1 = 282;
    public static final int FLD_IN2ND2 = 283;
    public static final int FLD_IN2ND3 = 284;
    public static final int FLD_IN2ND4 = 285;
    public static final int FLD_IN2ND5 = 286;
    public static final int FLD_IN2ND6 = 287;
    public static final int FLD_IN2ND7 = 288;
    public static final int FLD_IN2ND8 = 289;
    public static final int FLD_IN2ND9 = 290;
    public static final int FLD_IN2ND10 = 291;
    public static final int FLD_IN2ND11 = 292;
    public static final int FLD_IN2ND12 = 293;
    public static final int FLD_IN2ND13 = 294;
    public static final int FLD_IN2ND14 = 295;
    public static final int FLD_IN2ND15 = 296;
    public static final int FLD_IN2ND16 = 297;
    public static final int FLD_IN2ND17 = 298;
    public static final int FLD_IN2ND18 = 299;
    public static final int FLD_IN2ND19 = 300;
    public static final int FLD_IN2ND20 = 301;
    public static final int FLD_IN2ND21 = 302;
    public static final int FLD_IN2ND22 = 303;
    public static final int FLD_IN2ND23 = 304;
    public static final int FLD_IN2ND24 = 305;
    public static final int FLD_IN2ND25 = 306;
    public static final int FLD_IN2ND26 = 307;
    public static final int FLD_IN2ND27 = 308;
    public static final int FLD_IN2ND28 = 309;
    public static final int FLD_IN2ND29 = 310;
    public static final int FLD_IN2ND30 = 311;
    public static final int FLD_IN2ND31 = 312;
    public static final int FLD_OUT1 = 313;
    public static final int FLD_OUT2 = 314;
    public static final int FLD_OUT3 = 315;
    public static final int FLD_OUT4 = 316;
    public static final int FLD_OUT5 = 317;
    public static final int FLD_OUT6 = 318;
    public static final int FLD_OUT7 = 319;
    public static final int FLD_OUT8 = 320;
    public static final int FLD_OUT9 = 321;
    public static final int FLD_OUT10 = 322;
    public static final int FLD_OUT11 = 323;
    public static final int FLD_OUT12 = 324;
    public static final int FLD_OUT13 = 325;
    public static final int FLD_OUT14 = 326;
    public static final int FLD_OUT15 = 327;
    public static final int FLD_OUT16 = 328;
    public static final int FLD_OUT17 = 329;
    public static final int FLD_OUT18 = 330;
    public static final int FLD_OUT19 = 331;
    public static final int FLD_OUT20 = 332;
    public static final int FLD_OUT21 = 333;
    public static final int FLD_OUT22 = 334;
    public static final int FLD_OUT23 = 335;
    public static final int FLD_OUT24 = 336;
    public static final int FLD_OUT25 = 337;
    public static final int FLD_OUT26 = 338;
    public static final int FLD_OUT27 = 339;
    public static final int FLD_OUT28 = 340;
    public static final int FLD_OUT29 = 341;
    public static final int FLD_OUT30 = 342;
    public static final int FLD_OUT31 = 343;
    public static final int FLD_OUT2ND1 = 344;
    public static final int FLD_OUT2ND2 = 345;
    public static final int FLD_OUT2ND3 = 346;
    public static final int FLD_OUT2ND4 = 347;
    public static final int FLD_OUT2ND5 = 348;
    public static final int FLD_OUT2ND6 = 349;
    public static final int FLD_OUT2ND7 = 350;
    public static final int FLD_OUT2ND8 = 351;
    public static final int FLD_OUT2ND9 = 352;
    public static final int FLD_OUT2ND10 = 353;
    public static final int FLD_OUT2ND11 = 354;
    public static final int FLD_OUT2ND12 = 355;
    public static final int FLD_OUT2ND13 = 356;
    public static final int FLD_OUT2ND14 = 357;
    public static final int FLD_OUT2ND15 = 358;
    public static final int FLD_OUT2ND16 = 359;
    public static final int FLD_OUT2ND17 = 360;
    public static final int FLD_OUT2ND18 = 361;
    public static final int FLD_OUT2ND19 = 362;
    public static final int FLD_OUT2ND20 = 363;
    public static final int FLD_OUT2ND21 = 364;
    public static final int FLD_OUT2ND22 = 365;
    public static final int FLD_OUT2ND23 = 366;
    public static final int FLD_OUT2ND24 = 367;
    public static final int FLD_OUT2ND25 = 368;
    public static final int FLD_OUT2ND26 = 369;
    public static final int FLD_OUT2ND27 = 370;
    public static final int FLD_OUT2ND28 = 371;
    public static final int FLD_OUT2ND29 = 372;
    public static final int FLD_OUT2ND30 = 373;
    public static final int FLD_OUT2ND31 = 374;
    public static final int FLD_SCHEDULE_TYPE = 375;
    public static final String[] fieldNames = {
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
    public static final int[] fieldTypes = {
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

    public PstEmpScheduleDestopTransfer() {
    }

    public PstEmpScheduleDestopTransfer(int i) throws DBException {
        super(new PstEmpScheduleDestopTransfer());
    }

    public PstEmpScheduleDestopTransfer(String sOid) throws DBException {
        super(new PstEmpScheduleDestopTransfer(0));
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }

    public PstEmpScheduleDestopTransfer(long lOid) throws DBException {
        super(new PstEmpScheduleDestopTransfer(0));
        String sOid = "0";
        try {
            sOid = String.valueOf(lOid);
        } catch (Exception e) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }

    public int getFieldSize() {
        return fieldNames.length;
    }

    public String getTableName() {
        return TBL_HR_EMP_SCHEDULE;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getPersistentName() {
        return new PstEmpScheduleDestopTransfer().getClass().getName();
    }

    public long fetchExc(Entity ent) throws Exception {
       return 0;
    }

    public long insertExc(Entity ent) throws Exception {
        return 0;
    }

    public long updateExc(Entity ent) throws Exception {
        return 0;
    }

    public long deleteExc(Entity ent) throws Exception {
        if (ent == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(ent.getOID());
    }

    public static long insertExc(TabelEmployeeScheduleTransfer tabelEmployeeScheduleTransfer) throws DBException {
        try {
            PstEmpScheduleDestopTransfer pstPositionDestopTransfer = new PstEmpScheduleDestopTransfer(0);

            pstPositionDestopTransfer.setLong(FLD_EMPLOYEE_ID, tabelEmployeeScheduleTransfer.getEmployeeId());
            //pstPositionDestopTransfer.setLong(FLD_EMP_SCHEDULE_ID, tabelEmployeeScheduleTransfer.getEmpScheduleId());
            pstPositionDestopTransfer.setLong(FLD_PERIOD_ID, tabelEmployeeScheduleTransfer.getPeriodId());
             pstPositionDestopTransfer.setInt(FLD_SCHEDULE_TYPE, (int)tabelEmployeeScheduleTransfer.getScheduleType());
            
            pstPositionDestopTransfer.setLong(FLD_D1, tabelEmployeeScheduleTransfer.getD1());
            pstPositionDestopTransfer.setLong(FLD_D2, tabelEmployeeScheduleTransfer.getD2());
            pstPositionDestopTransfer.setLong(FLD_D3, tabelEmployeeScheduleTransfer.getD3());
            pstPositionDestopTransfer.setLong(FLD_D4, tabelEmployeeScheduleTransfer.getD4());
            pstPositionDestopTransfer.setLong(FLD_D5, tabelEmployeeScheduleTransfer.getD5());
            pstPositionDestopTransfer.setLong(FLD_D6, tabelEmployeeScheduleTransfer.getD6());
            pstPositionDestopTransfer.setLong(FLD_D7, tabelEmployeeScheduleTransfer.getD7());
            pstPositionDestopTransfer.setLong(FLD_D8, tabelEmployeeScheduleTransfer.getD8());
            pstPositionDestopTransfer.setLong(FLD_D9, tabelEmployeeScheduleTransfer.getD9());
            pstPositionDestopTransfer.setLong(FLD_D10, tabelEmployeeScheduleTransfer.getD10());
            pstPositionDestopTransfer.setLong(FLD_D11, tabelEmployeeScheduleTransfer.getD11());
            pstPositionDestopTransfer.setLong(FLD_D12, tabelEmployeeScheduleTransfer.getD12());
            pstPositionDestopTransfer.setLong(FLD_D13, tabelEmployeeScheduleTransfer.getD13());
            pstPositionDestopTransfer.setLong(FLD_D14, tabelEmployeeScheduleTransfer.getD14());
            pstPositionDestopTransfer.setLong(FLD_D15, tabelEmployeeScheduleTransfer.getD15());
            pstPositionDestopTransfer.setLong(FLD_D16, tabelEmployeeScheduleTransfer.getD16());
            pstPositionDestopTransfer.setLong(FLD_D17, tabelEmployeeScheduleTransfer.getD17());
            pstPositionDestopTransfer.setLong(FLD_D18, tabelEmployeeScheduleTransfer.getD18());
            pstPositionDestopTransfer.setLong(FLD_D19, tabelEmployeeScheduleTransfer.getD19());
            pstPositionDestopTransfer.setLong(FLD_D20, tabelEmployeeScheduleTransfer.getD20());
            pstPositionDestopTransfer.setLong(FLD_D21, tabelEmployeeScheduleTransfer.getD21());
            pstPositionDestopTransfer.setLong(FLD_D22, tabelEmployeeScheduleTransfer.getD22());
            pstPositionDestopTransfer.setLong(FLD_D23, tabelEmployeeScheduleTransfer.getD23());
            pstPositionDestopTransfer.setLong(FLD_D24, tabelEmployeeScheduleTransfer.getD24());
            pstPositionDestopTransfer.setLong(FLD_D25, tabelEmployeeScheduleTransfer.getD25());
            pstPositionDestopTransfer.setLong(FLD_D26, tabelEmployeeScheduleTransfer.getD26());
            pstPositionDestopTransfer.setLong(FLD_D27, tabelEmployeeScheduleTransfer.getD27());
            pstPositionDestopTransfer.setLong(FLD_D28, tabelEmployeeScheduleTransfer.getD28());
            pstPositionDestopTransfer.setLong(FLD_D29, tabelEmployeeScheduleTransfer.getD29());
            pstPositionDestopTransfer.setLong(FLD_D30, tabelEmployeeScheduleTransfer.getD30());
            pstPositionDestopTransfer.setLong(FLD_D31, tabelEmployeeScheduleTransfer.getD31());
           
           pstPositionDestopTransfer.setInt(FLD_STATUS1, tabelEmployeeScheduleTransfer.getStatus1());
            pstPositionDestopTransfer.setInt(FLD_STATUS2, tabelEmployeeScheduleTransfer.getStatus2());
            pstPositionDestopTransfer.setInt(FLD_STATUS3, tabelEmployeeScheduleTransfer.getStatus3());
            pstPositionDestopTransfer.setInt(FLD_STATUS4, tabelEmployeeScheduleTransfer.getStatus4());
            pstPositionDestopTransfer.setInt(FLD_STATUS5, tabelEmployeeScheduleTransfer.getStatus5());
            pstPositionDestopTransfer.setInt(FLD_STATUS6, tabelEmployeeScheduleTransfer.getStatus6());
            pstPositionDestopTransfer.setInt(FLD_STATUS7, tabelEmployeeScheduleTransfer.getStatus7());
            pstPositionDestopTransfer.setInt(FLD_STATUS8, tabelEmployeeScheduleTransfer.getStatus8());
            pstPositionDestopTransfer.setInt(FLD_STATUS9, tabelEmployeeScheduleTransfer.getStatus9());
            pstPositionDestopTransfer.setInt(FLD_STATUS10, tabelEmployeeScheduleTransfer.getStatus10());
            pstPositionDestopTransfer.setInt(FLD_STATUS11, tabelEmployeeScheduleTransfer.getStatus11());
            pstPositionDestopTransfer.setInt(FLD_STATUS12, tabelEmployeeScheduleTransfer.getStatus12());
            pstPositionDestopTransfer.setInt(FLD_STATUS13, tabelEmployeeScheduleTransfer.getStatus13());
            pstPositionDestopTransfer.setInt(FLD_STATUS14, tabelEmployeeScheduleTransfer.getStatus14());
            pstPositionDestopTransfer.setInt(FLD_STATUS15, tabelEmployeeScheduleTransfer.getStatus15());
            pstPositionDestopTransfer.setInt(FLD_STATUS16, tabelEmployeeScheduleTransfer.getStatus16());
            pstPositionDestopTransfer.setInt(FLD_STATUS17, tabelEmployeeScheduleTransfer.getStatus17());
            pstPositionDestopTransfer.setInt(FLD_STATUS18, tabelEmployeeScheduleTransfer.getStatus18());
            pstPositionDestopTransfer.setInt(FLD_STATUS19, tabelEmployeeScheduleTransfer.getStatus19());
            pstPositionDestopTransfer.setInt(FLD_STATUS20, tabelEmployeeScheduleTransfer.getStatus20());
            pstPositionDestopTransfer.setInt(FLD_STATUS21, tabelEmployeeScheduleTransfer.getStatus21());
            pstPositionDestopTransfer.setInt(FLD_STATUS22, tabelEmployeeScheduleTransfer.getStatus22());
            pstPositionDestopTransfer.setInt(FLD_STATUS23, tabelEmployeeScheduleTransfer.getStatus23());
            pstPositionDestopTransfer.setInt(FLD_STATUS24, tabelEmployeeScheduleTransfer.getStatus24());
            pstPositionDestopTransfer.setInt(FLD_STATUS25, tabelEmployeeScheduleTransfer.getStatus25());
            pstPositionDestopTransfer.setInt(FLD_STATUS26, tabelEmployeeScheduleTransfer.getStatus26());
            pstPositionDestopTransfer.setInt(FLD_STATUS27, tabelEmployeeScheduleTransfer.getStatus27());
            pstPositionDestopTransfer.setInt(FLD_STATUS28, tabelEmployeeScheduleTransfer.getStatus28());
            pstPositionDestopTransfer.setInt(FLD_STATUS29, tabelEmployeeScheduleTransfer.getStatus29());
            pstPositionDestopTransfer.setInt(FLD_STATUS30, tabelEmployeeScheduleTransfer.getStatus30());
            pstPositionDestopTransfer.setInt(FLD_STATUS31, tabelEmployeeScheduleTransfer.getStatus31());
            
            
            pstPositionDestopTransfer.insert(tabelEmployeeScheduleTransfer.getEmpScheduleId());
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstEmpScheduleDestopTransfer(0), DBException.UNKNOWN);
        }
        return tabelEmployeeScheduleTransfer.getEmpScheduleId();
    }

    public static long updateExc(TabelEmployeeScheduleTransfer tabelEmployeeScheduleTransfer) throws DBException {
        try {
            if (tabelEmployeeScheduleTransfer.getEmpScheduleId() != 0) {
                PstEmpScheduleDestopTransfer pstPositionDestopTransfer = new PstEmpScheduleDestopTransfer(tabelEmployeeScheduleTransfer.getEmpScheduleId());

               
               pstPositionDestopTransfer.setLong(FLD_D1, tabelEmployeeScheduleTransfer.getD1());
            pstPositionDestopTransfer.setLong(FLD_D2, tabelEmployeeScheduleTransfer.getD2());
            pstPositionDestopTransfer.setLong(FLD_D3, tabelEmployeeScheduleTransfer.getD3());
            pstPositionDestopTransfer.setLong(FLD_D4, tabelEmployeeScheduleTransfer.getD4());
            pstPositionDestopTransfer.setLong(FLD_D5, tabelEmployeeScheduleTransfer.getD5());
            pstPositionDestopTransfer.setLong(FLD_D6, tabelEmployeeScheduleTransfer.getD6());
            pstPositionDestopTransfer.setLong(FLD_D7, tabelEmployeeScheduleTransfer.getD7());
            pstPositionDestopTransfer.setLong(FLD_D8, tabelEmployeeScheduleTransfer.getD8());
            pstPositionDestopTransfer.setLong(FLD_D9, tabelEmployeeScheduleTransfer.getD9());
            pstPositionDestopTransfer.setLong(FLD_D10, tabelEmployeeScheduleTransfer.getD10());
            pstPositionDestopTransfer.setLong(FLD_D11, tabelEmployeeScheduleTransfer.getD11());
            pstPositionDestopTransfer.setLong(FLD_D12, tabelEmployeeScheduleTransfer.getD12());
            pstPositionDestopTransfer.setLong(FLD_D13, tabelEmployeeScheduleTransfer.getD13());
            pstPositionDestopTransfer.setLong(FLD_D14, tabelEmployeeScheduleTransfer.getD14());
            pstPositionDestopTransfer.setLong(FLD_D15, tabelEmployeeScheduleTransfer.getD15());
            pstPositionDestopTransfer.setLong(FLD_D16, tabelEmployeeScheduleTransfer.getD16());
            pstPositionDestopTransfer.setLong(FLD_D17, tabelEmployeeScheduleTransfer.getD17());
            pstPositionDestopTransfer.setLong(FLD_D18, tabelEmployeeScheduleTransfer.getD18());
            pstPositionDestopTransfer.setLong(FLD_D19, tabelEmployeeScheduleTransfer.getD19());
            pstPositionDestopTransfer.setLong(FLD_D20, tabelEmployeeScheduleTransfer.getD20());
            pstPositionDestopTransfer.setLong(FLD_D21, tabelEmployeeScheduleTransfer.getD21());
            pstPositionDestopTransfer.setLong(FLD_D22, tabelEmployeeScheduleTransfer.getD22());
            pstPositionDestopTransfer.setLong(FLD_D23, tabelEmployeeScheduleTransfer.getD23());
            pstPositionDestopTransfer.setLong(FLD_D24, tabelEmployeeScheduleTransfer.getD24());
            pstPositionDestopTransfer.setLong(FLD_D25, tabelEmployeeScheduleTransfer.getD25());
            pstPositionDestopTransfer.setLong(FLD_D26, tabelEmployeeScheduleTransfer.getD26());
            pstPositionDestopTransfer.setLong(FLD_D27, tabelEmployeeScheduleTransfer.getD27());
            pstPositionDestopTransfer.setLong(FLD_D28, tabelEmployeeScheduleTransfer.getD28());
            pstPositionDestopTransfer.setLong(FLD_D29, tabelEmployeeScheduleTransfer.getD29());
            pstPositionDestopTransfer.setLong(FLD_D30, tabelEmployeeScheduleTransfer.getD30());
            pstPositionDestopTransfer.setLong(FLD_D31, tabelEmployeeScheduleTransfer.getD31());
           
           pstPositionDestopTransfer.setInt(FLD_STATUS1, tabelEmployeeScheduleTransfer.getStatus1());
            pstPositionDestopTransfer.setInt(FLD_STATUS2, tabelEmployeeScheduleTransfer.getStatus2());
            pstPositionDestopTransfer.setInt(FLD_STATUS3, tabelEmployeeScheduleTransfer.getStatus3());
            pstPositionDestopTransfer.setInt(FLD_STATUS4, tabelEmployeeScheduleTransfer.getStatus4());
            pstPositionDestopTransfer.setInt(FLD_STATUS5, tabelEmployeeScheduleTransfer.getStatus5());
            pstPositionDestopTransfer.setInt(FLD_STATUS6, tabelEmployeeScheduleTransfer.getStatus6());
            pstPositionDestopTransfer.setInt(FLD_STATUS7, tabelEmployeeScheduleTransfer.getStatus7());
            pstPositionDestopTransfer.setInt(FLD_STATUS8, tabelEmployeeScheduleTransfer.getStatus8());
            pstPositionDestopTransfer.setInt(FLD_STATUS9, tabelEmployeeScheduleTransfer.getStatus9());
            pstPositionDestopTransfer.setInt(FLD_STATUS10, tabelEmployeeScheduleTransfer.getStatus10());
            pstPositionDestopTransfer.setInt(FLD_STATUS11, tabelEmployeeScheduleTransfer.getStatus11());
            pstPositionDestopTransfer.setInt(FLD_STATUS12, tabelEmployeeScheduleTransfer.getStatus12());
            pstPositionDestopTransfer.setInt(FLD_STATUS13, tabelEmployeeScheduleTransfer.getStatus13());
            pstPositionDestopTransfer.setInt(FLD_STATUS14, tabelEmployeeScheduleTransfer.getStatus14());
            pstPositionDestopTransfer.setInt(FLD_STATUS15, tabelEmployeeScheduleTransfer.getStatus15());
            pstPositionDestopTransfer.setInt(FLD_STATUS16, tabelEmployeeScheduleTransfer.getStatus16());
            pstPositionDestopTransfer.setInt(FLD_STATUS17, tabelEmployeeScheduleTransfer.getStatus17());
            pstPositionDestopTransfer.setInt(FLD_STATUS18, tabelEmployeeScheduleTransfer.getStatus18());
            pstPositionDestopTransfer.setInt(FLD_STATUS19, tabelEmployeeScheduleTransfer.getStatus19());
            pstPositionDestopTransfer.setInt(FLD_STATUS20, tabelEmployeeScheduleTransfer.getStatus20());
            pstPositionDestopTransfer.setInt(FLD_STATUS21, tabelEmployeeScheduleTransfer.getStatus21());
            pstPositionDestopTransfer.setInt(FLD_STATUS22, tabelEmployeeScheduleTransfer.getStatus22());
            pstPositionDestopTransfer.setInt(FLD_STATUS23, tabelEmployeeScheduleTransfer.getStatus23());
            pstPositionDestopTransfer.setInt(FLD_STATUS24, tabelEmployeeScheduleTransfer.getStatus24());
            pstPositionDestopTransfer.setInt(FLD_STATUS25, tabelEmployeeScheduleTransfer.getStatus25());
            pstPositionDestopTransfer.setInt(FLD_STATUS26, tabelEmployeeScheduleTransfer.getStatus26());
            pstPositionDestopTransfer.setInt(FLD_STATUS27, tabelEmployeeScheduleTransfer.getStatus27());
            pstPositionDestopTransfer.setInt(FLD_STATUS28, tabelEmployeeScheduleTransfer.getStatus28());
            pstPositionDestopTransfer.setInt(FLD_STATUS29, tabelEmployeeScheduleTransfer.getStatus29());
            pstPositionDestopTransfer.setInt(FLD_STATUS30, tabelEmployeeScheduleTransfer.getStatus30());
            pstPositionDestopTransfer.setInt(FLD_STATUS31, tabelEmployeeScheduleTransfer.getStatus31());
            

                pstPositionDestopTransfer.update();
                return tabelEmployeeScheduleTransfer.getEmpScheduleId();

            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstEmpScheduleDestopTransfer(0), DBException.UNKNOWN);
        }
        return 0;
    }

    public static long deleteExc(long oid) throws DBException {
        try {
            PstEmpScheduleDestopTransfer pstCareerPath = new PstEmpScheduleDestopTransfer(oid);
            pstCareerPath.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstEmpScheduleDestopTransfer(0), DBException.UNKNOWN);
        }
        return oid;
    }

    public static boolean checkOID(long empSchId) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_HR_EMP_SCHEDULE + " WHERE "
                    + PstEmpScheduleDestopTransfer.fieldNames[PstEmpScheduleDestopTransfer.FLD_EMP_SCHEDULE_ID] + " = " + empSchId;

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                result = true;
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("err : " + e.toString());
        } finally {
            DBResultSet.close(dbrs);
            return result;
        }
    }
    
    public static Hashtable<String,Boolean> hashEmpScheduleSdhAda(int limitStart, int recordToGet, String whereClause, String order) {
        Hashtable hashEmpScheduleSdhAda= new Hashtable();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT "+fieldNames[FLD_EMP_SCHEDULE_ID]+" FROM " + TBL_HR_EMP_SCHEDULE;
            if (whereClause != null && whereClause.length() > 0) {
                sql = sql + " WHERE " + whereClause;
            }
            if (order != null && order.length() > 0) {
                sql = sql + " ORDER BY " + order;
            }
            if (limitStart == 0 && recordToGet == 0) {
                sql = sql + "";
            } else {
                sql = sql + " LIMIT " + limitStart + "," + recordToGet;
            }
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                hashEmpScheduleSdhAda.put(""+rs.getLong(fieldNames[FLD_EMP_SCHEDULE_ID]), true);
            }
            rs.close();
            

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
            return hashEmpScheduleSdhAda;
        }
        
    }
}
