<%@page import="com.sun.org.apache.xml.internal.security.Init"%>
<%@ page import="java.util.*" %>
<%@ page import="com.dimata.util.*" %>
<%@ page import="com.dimata.gui.jsp.*" %>
<%@ page import="com.dimata.qdep.entity.*" %>
<%@ page import="com.dimata.system.entity.system.*" %>
<%@ page import="com.dimata.qdep.form.*" %>
<%@ page import="com.dimata.system.form.system.*" %>
<%@ page import="com.dimata.system.session.system.*" %>

<%@ include file = "../main/javainit.jsp" %>
<%  int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_SYSTEM, AppObjInfo.G2_SYSTEM_MANAGEMENT, AppObjInfo.OBJ_SYSTEM_PROPERTIES); %>
<%@ include file = "../main/checkuser.jsp" %>
<%!
Vector vSysProp = new Vector();

public void init(){
SystemProperty sysProp = null;

sysProp = new SystemProperty();	sysProp.setName("JOIN_DEPARMENT");	sysProp.setValueType("STRING");	sysProp.setGroup("Application");	sysProp.setNote("deparment yang mempunyai sekretaris satu shg schedule di handle satu orang. contoh : 2004=2007/2001=2002=2003 tanda = join , tanda   . Ini memungkinkan satu user edit schedule bbbrp department,  tanda = join , tanda / pemisah group");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("HARISMA_URL");	sysProp.setValueType("TEXT");	sysProp.setGroup("Application");	sysProp.setNote("URL of Harisma , sent by some task as notification and to click by user");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("HARISMA_URL_AKSES_INTERNET");	sysProp.setValueType("TEXT");	sysProp.setGroup("Application");	sysProp.setNote("URL of Harisma with Internet , sent by some task as notification and to click by user");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("FTP_PHOTO_HOME");	sysProp.setValueType("STRING");	sysProp.setGroup("Application");	sysProp.setNote("Default Admin email address");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("ADMIN_EMAIL");	sysProp.setValueType("STRING");	sysProp.setGroup("Application");	sysProp.setNote("Admin email address");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("PROP_IMGCACHE");	sysProp.setValueType("STRING");	sysProp.setGroup("Application");	sysProp.setNote("Absolute path of image cache");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("TIMEKEEPING_INTERVAL");	sysProp.setValueType("NUMBER");	sysProp.setGroup("Application");	sysProp.setNote("Timekeeping interval time (in Minutes)");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("PRESENCE_INTERVAL");	sysProp.setValueType("NUMBER");	sysProp.setGroup("Application");	sysProp.setNote("Interval for analyzing presence data");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("HINDU");	sysProp.setValueType("STRING");	sysProp.setGroup("Application");	sysProp.setNote("Hindu Religion OID");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("CANTEEN_CHECK_SWEEP_TIME");	sysProp.setValueType("NUMBER");	sysProp.setGroup("Application");	sysProp.setNote("0 : Not check 1 : Check by specified interval");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("CANTEEN_IGNORE_SWEEP_TIME");	sysProp.setValueType("NUMBER");	sysProp.setGroup("Application");	sysProp.setNote("Ignored time of sweeping same card in TMA canteen (in minutes)");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("CANTEEN_TMA_USED_PORT");	sysProp.setValueType("STRING");	sysProp.setGroup("Application");	sysProp.setNote("Name of port that used by TMA canteen machine");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("CHECK_UPDATE_SCHEDULE");	sysProp.setValueType("NUMBER");	sysProp.setGroup("Application");	sysProp.setNote("Check update process flag 0:do not check 1:check");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("OID_AL_LEAVE");	sysProp.setValueType("STRING");	sysProp.setGroup("Application");	sysProp.setNote("OID Schedule Annual Leave");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("OID_DAY_OFF_PAYMENT");	sysProp.setValueType("STRING");	sysProp.setGroup("Application");	sysProp.setNote("OID Schedule Day Off Payment");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("OID_HRD_DEPARTMENT");	sysProp.setValueType("STRING");	sysProp.setGroup("Application");	sysProp.setNote("OID HRD Department");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("OID_LL_LEAVE");	sysProp.setValueType("STRING");	sysProp.setGroup("Application");	sysProp.setNote("OID Schedule Long Leave");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("OID_MATERNITY_LEAVE");	sysProp.setValueType("STRING");	sysProp.setGroup("Application");	sysProp.setNote("OID Schedule Meternity Leave");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("OID_SICK_LEAVE");	sysProp.setValueType("STRING");	sysProp.setGroup("Application");	sysProp.setNote("OID Schedule Sickness Leave");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("OID_SPECIAL_LEAVE");	sysProp.setValueType("STRING");	sysProp.setGroup("Application");	sysProp.setNote("OID Schedule Special Leave");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("OID_UNPAID_LEAVE");	sysProp.setValueType("STRING");	sysProp.setGroup("Application");	sysProp.setNote("OID Schedule Unpaid Leave");	vSysProp.add(sysProp);
//update by devin 2014-03-06
sysProp = new SystemProperty();	sysProp.setName("SET_CONFIGURATION_LEAVE_NOT_SAME_PERIOD");	sysProp.setValueType("NUMBER");	sysProp.setGroup("Application");	sysProp.setNote("Konfigurasi jika menginginkan cutinya beda period pisah form leave 1: true : 0:false");	vSysProp.add(sysProp);
//update by satrya 2013-04-11
sysProp = new SystemProperty();	sysProp.setName("OID_EXCUSE_SCHEDULE_CATEGORY");	sysProp.setValueType("STRING");	sysProp.setGroup("Application");	sysProp.setNote("OID Schedule Category Excuse ");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("UPDATE_SCHLD_TIME_AL_ROSTER");	sysProp.setValueType("TEXT");	sysProp.setGroup("Application");	sysProp.setNote("Update schedule time for AL roster (in day)");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("UPDATE_SCHLD_TIME_OTHER_ROSTER");	sysProp.setValueType("TEXT");	sysProp.setGroup("Application");	sysProp.setNote("Update schedule time for other roster (in day)");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("UPDATE_SCHLD_TIME_LEAVE_ROSTER");	sysProp.setValueType("TEXT");	sysProp.setGroup("Application");	sysProp.setNote("Update schedule time for leave (AL, LL) roster (in day)");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("UPDATE_SCHLD_TIME_DP_ROSTER");	sysProp.setValueType("TEXT");	sysProp.setGroup("Application");	sysProp.setNote("Update schedule time for DP roster (in day)");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("EDP_SECTION_OID");	sysProp.setValueType("STRING");	sysProp.setGroup("Application");	sysProp.setNote("OID of EDD Section");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("IMG_ROOT");	sysProp.setValueType("STRING");	sysProp.setGroup("Application");	sysProp.setNote("");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("CARDNETIC_TEXT_DATE_FORMAT");	sysProp.setValueType("STRING");	sysProp.setGroup("Application");	sysProp.setNote("format date pada data text .sdf");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("CARDNETIC_TEXT_DATE_IDX_NUM");	sysProp.setValueType("STRING");	sysProp.setGroup("Application");	sysProp.setNote("jumlah digit datetime");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("CARDNETIC_TEXT_ID_IDX_NUM");	sysProp.setValueType("STRING");	sysProp.setGroup("Application");	sysProp.setNote("jumlah digit idnumber");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("CARDNETIC_TEXT_TYPE_IDX_NUM");	sysProp.setValueType("STRING");	sysProp.setGroup("Application");	sysProp.setNote("jumlah digit type swapping");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("CARDNETIC_TEXT_SWAP_IN_VALUE");	sysProp.setValueType("STRING");	sysProp.setGroup("Application");	sysProp.setNote("code swaping in");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("CARDNETIC_TEXT_SWAP_OUT_VALUE");	sysProp.setValueType("STRING");	sysProp.setGroup("Application");	sysProp.setNote("code swapping out");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("CARDNETIC_TEXT_FILE_PATH");	sysProp.setValueType("STRING");	sysProp.setGroup("Application");	sysProp.setNote("file path exclude drive & file name");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("CARDNETIC_DATA_SOURCE");	sysProp.setValueType("STRING");	sysProp.setGroup("Application");	sysProp.setNote("DBF/TEXT(MDF) file");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("CARDNETIC_TEXT_FILE_DRIVE");	sysProp.setValueType("STRING");	sysProp.setGroup("Application");	sysProp.setNote("DRIVE OF FILE PLACE");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("CARDNETIC_TEXT_FILE_NAME");	sysProp.setValueType("STRING");	sysProp.setGroup("Application");	sysProp.setNote("NAME OF FILE SDF");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("FINGER_PRINT");	sysProp.setValueType("STRING");	sysProp.setGroup("Application");	sysProp.setNote("CARDNETIC/TMA/FINGER_PRINT");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("POSTING_IN_TOLERANCE");	sysProp.setValueType("STRING");	sysProp.setGroup("Application");	sysProp.setNote("MENIT, TOLERANSI SAAT POSTING IN");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("POSTING_OUT_TOLERANCE");	sysProp.setValueType("STRING");	sysProp.setGroup("Application");	sysProp.setNote("MENIT, TOLERANSI POSTING OUT");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("START_DATE_PERIOD");	sysProp.setValueType("STRING");	sysProp.setGroup("Application");	sysProp.setNote("START_DATE_PERIOD");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("OVERTIME_VALUE");	sysProp.setValueType("STRING");	sysProp.setGroup("Application");	sysProp.setNote("OVERTIME_VALUE");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("TIME_WORKS_DAY");	sysProp.setValueType("STRING");	sysProp.setGroup("Application");	sysProp.setNote("TIME_WORKS_DAY");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("TIME_WORKS_MONTH");	sysProp.setValueType("STRING");	sysProp.setGroup("Application");	sysProp.setNote("TIME_WORKS_MONTH");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("DAY_ABSENT");	sysProp.setValueType("STRING");	sysProp.setGroup("Application");	sysProp.setNote("Day absent");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("MEAL_ALLOWANCE");	sysProp.setValueType("STRING");	sysProp.setGroup("Application");	sysProp.setNote("MEAL_ALLOWANCE");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("PROCENTASE_PRESENCE");	sysProp.setValueType("STRING");	sysProp.setGroup("Application");	sysProp.setNote("Procentase_Presence");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("SALARY_LEVEL4");	sysProp.setValueType("STRING");	sysProp.setGroup("Application");	sysProp.setNote("");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("SALARY_LEVEL5");	sysProp.setValueType("STRING");	sysProp.setGroup("Application");	sysProp.setNote("");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("SALARY_LEVEL6");	sysProp.setValueType("STRING");	sysProp.setGroup("Application");	sysProp.setNote("");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("COMP_CODE1");	sysProp.setValueType("STRING");	sysProp.setGroup("Application");	sysProp.setNote("");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("COMP_CODE2");	sysProp.setValueType("STRING");	sysProp.setGroup("Application");	sysProp.setNote("");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("ACC_NAME");	sysProp.setValueType("STRING");	sysProp.setGroup("Application");	sysProp.setNote("");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("SALARY_FOR_OVT");	sysProp.setValueType("STRING");	sysProp.setGroup("Application");	sysProp.setNote("");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("TOTAL_IDX_OVT");	sysProp.setValueType("STRING");	sysProp.setGroup("Application");	sysProp.setNote("");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("OID_KOMPRESSOR_POSITION");	sysProp.setValueType("STRING");	sysProp.setGroup("Application");	sysProp.setNote("");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("CODE_OVT");	sysProp.setValueType("STRING");	sysProp.setGroup("Application");	sysProp.setNote("");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("CODE_EKSP");	sysProp.setValueType("STRING");	sysProp.setGroup("Application");	sysProp.setNote("");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("CODE_KSB");	sysProp.setValueType("STRING");	sysProp.setGroup("Application");	sysProp.setNote("");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("CODE_CUTI");	sysProp.setValueType("STRING");	sysProp.setGroup("Application");	sysProp.setNote("");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("CODE_LL");	sysProp.setValueType("STRING");	sysProp.setGroup("Application");	sysProp.setNote("");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("KASIR");	sysProp.setValueType("STRING");	sysProp.setGroup("Application");	sysProp.setNote("");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("GM");	sysProp.setValueType("STRING");	sysProp.setGroup("Application");	sysProp.setNote("");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("PENGHITUNG");	sysProp.setValueType("STRING");	sysProp.setGroup("Application");	sysProp.setNote("");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("MEAL_TRAINING");	sysProp.setValueType("STRING");	sysProp.setGroup("Application");	sysProp.setNote("");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("OVT_DURATION");	sysProp.setValueType("STRING");	sysProp.setGroup("Application");	sysProp.setNote("");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("OID_TRAINEE/DW");	sysProp.setValueType("STRING");	sysProp.setGroup("Application");	sysProp.setNote("OID_TRAINEE/DW");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("LEAVE_CODE");	sysProp.setValueType("STRING");	sysProp.setGroup("Application");	sysProp.setNote("");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("DAY_PRESENT");	sysProp.setValueType("STRING");	sysProp.setGroup("Application");	sysProp.setNote("");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("IMGCACHE");	sysProp.setValueType("STRING");	sysProp.setGroup("Application");	sysProp.setNote("Tempat menampung image employee");	vSysProp.add(sysProp);
//update by satrya 2013-11-13
sysProp = new SystemProperty();	sysProp.setName("PATH_LOCATION_DBF");	sysProp.setValueType("STRING");	sysProp.setGroup("Application");	sysProp.setNote("letak database DBF");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("CANTEEN_TMA_NO");	sysProp.setValueType("STRING");	sysProp.setGroup("Application");	sysProp.setNote("Canteen TMA No, jika terdapat lebih dari satu lokasi pisahkan dengan , ex: 01,02");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("ABSEN_TMA_NO");	sysProp.setValueType("STRING");	sysProp.setGroup("Application");	sysProp.setNote("Mengeset no mesin absensi tma. Jika terdapat lebih dari satu mesin gunakan , untuk memisahkan nomor mesin ex: 01,02,03");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("OID_CASH");	sysProp.setValueType("STRING");	sysProp.setGroup("Application");	sysProp.setNote("OID_CASH");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("OID_POSITION_SATPAM");	sysProp.setValueType("STRING");	sysProp.setGroup("Application");	sysProp.setNote("");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("OID_TRAINING");	sysProp.setValueType("STRING");	sysProp.setGroup("Application");	sysProp.setNote("");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("OID_BREAVEMENT_LEAVE");	sysProp.setValueType("STRING");	sysProp.setGroup("Application");	sysProp.setNote("");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("OID_TOOTH_FILING_LEAVE");	sysProp.setValueType("STRING");	sysProp.setGroup("Application");	sysProp.setNote("");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("OID_PATERNITY_LEAVE");	sysProp.setValueType("STRING");	sysProp.setGroup("Application");	sysProp.setNote("");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("OID_MARRIAGE_SELF_LEAVE");	sysProp.setValueType("STRING");	sysProp.setGroup("Application");	sysProp.setNote("");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("OID_MARRIAGE_CHILD_LEAVE");	sysProp.setValueType("STRING");	sysProp.setGroup("Application");	sysProp.setNote("");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("OID_BAPTISM_LEAVE");	sysProp.setValueType("STRING");	sysProp.setGroup("Application");	sysProp.setNote("");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("OID_DAY_OFF");	sysProp.setValueType("STRING");	sysProp.setGroup("Application");	sysProp.setNote("");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("OID_EXTRA_OFF");	sysProp.setValueType("STRING");	sysProp.setGroup("Application");	sysProp.setNote("");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("OID_PUBLIC_HOLIDAY");	sysProp.setValueType("STRING");	sysProp.setGroup("Application");	sysProp.setNote("");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("OID_PRESENCE_HOLIDAY");	sysProp.setValueType("STRING");	sysProp.setGroup("Application");	sysProp.setNote("oid dari schedule symbole presence holiday");	vSysProp.add(sysProp);
//update by satrya 2012-12-20
sysProp = new SystemProperty();	sysProp.setName("OID_EMP_CATEGORY");	sysProp.setValueType("STRING");	sysProp.setGroup("Application");	sysProp.setNote("cek oid Category employee contrack and permanent");	vSysProp.add(sysProp);
//update by satrya 2013-10-30
sysProp = new SystemProperty();	sysProp.setName("OID_EMP_HRD_NOT_SEND_EMAIL");	sysProp.setValueType("STRING");	sysProp.setGroup("Application");	sysProp.setNote("oid  employee yang tidak ingin di kirimkan email, jika lebih dari satu diisikan ',' ");	vSysProp.add(sysProp);

sysProp = new SystemProperty();	sysProp.setName("FINGER_PRINT_NUMBER");	sysProp.setValueType("STRING");	sysProp.setGroup("Application");	sysProp.setNote("Banyak sidik jari yang dipergunakan untuk enroll");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("MIN_TRAINING_HOURS");	sysProp.setValueType("NUMBER");	sysProp.setGroup("Application");	sysProp.setNote("");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("PRINT_HEADER");	sysProp.setValueType("STRING");	sysProp.setGroup("Application");	sysProp.setNote("");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("INFORMATION");	sysProp.setValueType("STRING");	sysProp.setGroup("Application");	sysProp.setNote(" stop=normal/default header. Catatan / informasi untuk ditampilkan di header, jika diperlukan untuk maintenance");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("LEAVE_CONFIG");	sysProp.setValueType("STRING");	sysProp.setGroup("Application");	sysProp.setNote("Name of class to load for leave management process");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("DP_PROCESS_APPROVAL");	sysProp.setValueType("STRING");	sysProp.setGroup("Application");	sysProp.setNote("Dipergunakan dalam proses pengambilan DP TRUE : yang diproses hanya dp application yang sudah di approve FALSE : semua dp yang di ajukan akan di proses");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("LL_PROCESS_APPROVAL");	sysProp.setValueType("STRING");	sysProp.setGroup("Application");	sysProp.setNote("TRUE : dengan approval FALSE : tanpa approval");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("UNPAID_LEAVE_SYMBOL");	sysProp.setValueType("STRING");	sysProp.setGroup("Application");	sysProp.setNote("Unpaid");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("IMG_CACHE");	sysProp.setValueType("STRING");	sysProp.setGroup("Application");	sysProp.setNote("-");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("IMGASSIGN");	sysProp.setValueType("STRING");	sysProp.setGroup("Application");	sysProp.setNote("-");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("SL_PROCESS_APPROVAL");	sysProp.setValueType("STRING");	sysProp.setGroup("Application");	sysProp.setNote("");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("BIRD_DAY_MESSAGE");	sysProp.setValueType("STRING");	sysProp.setGroup("Application");	sysProp.setNote("Ucapan yang akan ditampilkan pada mesin jika employee ulang tahun");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("IMGDOC");	sysProp.setValueType("STRING");	sysProp.setGroup("Application");	sysProp.setNote("RELEVANT DOC");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("TRAINING_MATERIAL");	sysProp.setValueType("STRING");	sysProp.setGroup("Application");	sysProp.setNote("");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("TRAINING_MATERIAL_SHORT");	sysProp.setValueType("STRING");	sysProp.setGroup("Application");	sysProp.setNote("short form of training material destination path");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("LOCKER_MANY_USER");	sysProp.setValueType("STRING");	sysProp.setGroup("Application");	sysProp.setNote("1 : ya (satu locker bisa beberapa pemakai) 0 : tidak (satu locker satu pemakai)");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("SICK_LEAVE_SYMBOL");	sysProp.setValueType("STRING");	sysProp.setGroup("Application");	sysProp.setNote("Symbol untuk Sick Leave Ini akan dipergunakan dalam pengecekan status sakit");	vSysProp.add(sysProp);
//update by satrya 2012-10-27
sysProp = new SystemProperty();	sysProp.setName("SICK_REASON_WITH_DC");	sysProp.setValueType("STRING");	sysProp.setGroup("Application");	sysProp.setNote("Nilai dari Master Reason untuk yg memakai DC,contoh: 1");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("SICK_REASON_NOT_DC");	sysProp.setValueType("STRING");	sysProp.setGroup("Application");	sysProp.setNote("Nilai dari Master Reason untuk yg Tanpa DC,contoh: 2");	vSysProp.add(sysProp);

sysProp = new SystemProperty();	sysProp.setName("AL_PROCESS_APPROVAL");	sysProp.setValueType("STRING");	sysProp.setGroup("Application");	sysProp.setNote("");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("OID_SICK_LEAVE_WO_DC");	sysProp.setValueType("NUMBER");	sysProp.setGroup("Application");	sysProp.setNote("Sick Leave Without dc");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("OID_DEPARTMENT_EXECUTIVE_OFFICE");	sysProp.setValueType("NUMBER");	sysProp.setGroup("Application");	sysProp.setNote("Property untuk approvalForm untuk menampilkan GM");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("OID_SPECIAL");	sysProp.setValueType("NUMBER");	sysProp.setGroup("Application");	sysProp.setNote("set OID Special leave from Schedule Category");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("OID_UNPAID");	sysProp.setValueType("NUMBER");	sysProp.setGroup("Application");	sysProp.setNote("Schedule category unpaid");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("OID_LL");	sysProp.setValueType("STRING");	sysProp.setGroup("Application");	sysProp.setNote("set OID Long  leave from Schedule Category");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("OID_DP");	sysProp.setValueType("STRING");	sysProp.setGroup("Application");	sysProp.setNote("set OID Day Off Payment  leave from Schedule Category");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("OID_AL");	sysProp.setValueType("STRING");	sysProp.setGroup("Application");	sysProp.setNote("set OID Annual  leave from Schedule Category");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("OID_LEVEL_EXCOM_LOCAL");	sysProp.setValueType("STRING");	sysProp.setGroup("Application");	sysProp.setNote("Oid Level Excom local");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("OID_LEVEL_EXCOM_EXPATRIAT");	sysProp.setValueType("STRING");	sysProp.setGroup("Application");	sysProp.setNote("Oid Level excom expatriat");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("OID_LEVEL_A");	sysProp.setValueType("STRING");	sysProp.setGroup("Application");	sysProp.setNote("Oid Level A");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("OID_LEVEL_B");	sysProp.setValueType("STRING");	sysProp.setGroup("Application");	sysProp.setNote("Oid Level B");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("MAX_DAY_EXECUTION");	sysProp.setValueType("STRING");	sysProp.setGroup("Application");	sysProp.setNote("Maksimum hari untuk eksekusi");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("CONFIGURATION_LEAVE_UPDATE_SCHEDULE");	sysProp.setValueType("STRING");	sysProp.setGroup("Application");	sysProp.setNote("");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("JOIN_DEPARTMENT_SECTION");	sysProp.setValueType("STRING");	sysProp.setGroup("Application");	sysProp.setNote("Join section");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("MACHINE_FN_SPOT");	sysProp.setValueType("STRING");	sysProp.setGroup("Application");	sysProp.setNote("no -&gt belum menggunakan mesin finger, ok -menggunakan mesin finger spot");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("ABSENCE_REPORT_OFF");	sysProp.setValueType("STRING");	sysProp.setGroup("Application");	sysProp.setNote("");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("DATA_BASE_FN");	sysProp.setValueType("STRING");	sysProp.setGroup("Application");	sysProp.setNote("Nama ODBC databse finger spot");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("PROCESS_EXPIRED_DP");	sysProp.setValueType("STRING");	sysProp.setGroup("Application");	sysProp.setNote("");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("JOIN_DEPARMENT_REPORT_EXCEPTION");	sysProp.setValueType("STRING");	sysProp.setGroup("Application");	sysProp.setNote("Department yang akan di tampilkan dalam leave report");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("ATT_MACHINE_ODBC_CLASS");	sysProp.setValueType("STRING");	sysProp.setGroup("Application");	sysProp.setNote("Nama class untuk ODBC ke mesin");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("USE_LONG_LEAVE");	sysProp.setValueType("NUMBER");	sysProp.setGroup("Application");	sysProp.setNote("use of long leave 0=no 1=yes");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("HR_DIRECTOR");	sysProp.setValueType("STRING");	sysProp.setGroup("Application");	sysProp.setNote("HR Director employee oid , use for leave approval");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("EMAIL_NOTIFICATION_FROM");	sysProp.setValueType("TEXT");	sysProp.setGroup("Application");	sysProp.setNote("email used to sent notification to users");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("EMAIL_NOTIFICATION_HOST");	sysProp.setValueType("TEXT");	sysProp.setGroup("Application");	sysProp.setNote("email SMTP host used to send email for notification");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("EMAIL_NOTIFICATION_ON");	sysProp.setValueType("STRING");	sysProp.setGroup("Application");	sysProp.setNote("email notification status : 1=on ,  other then 1 is off");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("EMAIL_NOTIFICATION_PORT");	sysProp.setValueType("NUMBER");	sysProp.setGroup("Application");	sysProp.setNote("port of email server");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("EMAIL_NOTIFICATION_USERNAME");	sysProp.setValueType("STRING");	sysProp.setGroup("Application");	sysProp.setNote("user for email notification server");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("EMAIL_NOTIFICATION_PASSWORD");	sysProp.setValueType("STRING");	sysProp.setGroup("Application");	sysProp.setNote("password of email account for notification");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("EMAIL_SSL_SETTING");	sysProp.setValueType("STRING");	sysProp.setGroup("Application");	sysProp.setNote("true= use SSL , false= not use SSL");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("LEAVE_CONFIGURATION_APPROVAL_HOME");	sysProp.setValueType("STRING");	sysProp.setGroup("Application");	sysProp.setNote("true= USE config tersusun general s/d manager,general s/d ass.director,general s/d director,general s/d vip , false= not use config");	vSysProp.add(sysProp);
//sysProp = new SystemProperty();	sysProp.setName("EMAIL_CONFIG_TYPE_SENDING");	sysProp.setValueType("STRING");	sysProp.setGroup("Application");	sysProp.setNote("true= use type send email ver 1 , false= use type send email ver 2");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("GM_POS_ID");	sysProp.setValueType("NUMBER");	sysProp.setGroup("Application");	sysProp.setNote("OID of GM Master Position");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("HR_DIR_POS_ID");	sysProp.setValueType("NUMBER");	sysProp.setGroup("Application");	sysProp.setNote("OID of HR Director Position");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("HR_MAN_POS_ID");	sysProp.setValueType("NUMBER");	sysProp.setGroup("Application");	sysProp.setNote("OID of HR Manager Position");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("EMAIL_NOTIFICATION_GM");	sysProp.setValueType("NUMBER");	sysProp.setGroup("Application");	sysProp.setNote("Enable notification for GM : 0 or blank=false ; 1= true");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("DAY_LATE");	sysProp.setValueType("STRING");	sysProp.setGroup("Application");	sysProp.setNote("Date late for payroll calculation");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("MIN_OVERTM_DURATION");	sysProp.setValueType("NUMBER");	sysProp.setGroup("Application");	sysProp.setNote("Minimum minutes as overtime for payroll calculation");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("MIN_OVERTM_DURATION_ASST_MAN");	sysProp.setValueType("NUMBER");	sysProp.setGroup("Application");	sysProp.setNote("Minimum hour as overtime asistance manager for payroll calculation, ex. 2h");	vSysProp.add(sysProp);
//update by devin 2014-01-22
sysProp = new SystemProperty();	sysProp.setName("ATTENDANCE_CONFIG_WEEKLY_TO_DISABLED");	sysProp.setValueType("TEXT");	sysProp.setGroup("Application");	sysProp.setNote("Jika True maka schedulenya enabled 2 minggu dari tanggal sekarang jika false maka schedulenya enable semuanya");	vSysProp.add(sysProp);
//update by satrya 2012-07-31  
sysProp = new SystemProperty();	sysProp.setName("ATTENDANCE_CONFIG");	sysProp.setValueType("NUMBER");	sysProp.setGroup("Application");	sysProp.setNote("konfigurasi bentuk attendance pencarian schedule,contoh com.dimata.harisma.entity.attendance.AttendanceConfigMelia");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("ATTANDACE_ON_NO_SCHEDULE");	sysProp.setValueType("NUMBER");	sysProp.setGroup("Application");	sysProp.setNote("1= Enable attandace IN OUT time when no schedule on a day, 0 = IN OUT time only taken when schedule exists");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("ATTANDACE_IN_OUT_BUTTON");	sysProp.setValueType("NUMBER");	sysProp.setGroup("Application");	sysProp.setNote("1= Enable employe while push tipe of attandace(IN,OUT,OUT ON DUTY, etc) on attandace machine, 0 = type of attandace is not enter by employee on attandace machine but annalyzed by system");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("ATTANDACE_ANALIZER_TYPE");	sysProp.setValueType("NUMBER");	sysProp.setGroup("Application");	sysProp.setNote("Type of attendance analizer : 0=default based on working schedule time, 1=based on presence time matching the schedule time");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("ATTANDACE_WHEN_LEAVE_EXIST");	sysProp.setValueType("NUMBER");	sysProp.setGroup("Application");	sysProp.setNote("1=Enable,perhitungan diambil berdasarakan range schedule dia telat/absence,0=disable,perhitungan diambil berdasarkan real Time yg ada");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("ATTENDANCE_DATA_ACCESS_VIA_WEB"); sysProp.setValueType("NUMBER");	sysProp.setGroup("Application");sysProp.setNote("0(atau tidak ada)= time keeping database will be accesses via Dimata Desktop app.  Svc manager will NOT import data presence 1 = time keeping database will be accesses via Dimata Harisma Web , savc manager imports data presence ");vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("ATTANDACE_DEFAULT_SCHEDULE_PER_WEEK");	sysProp.setValueType("NUMBER");	sysProp.setGroup("Application");	sysProp.setNote("1=Enable set default schedule per week (5 minggu), 0 = Disable  set default schedule per week (5 minggu)");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("ATTANDACE_SHOW_OVERTIME_IN_REPORT_DAILY");	sysProp.setValueType("NUMBER");	sysProp.setGroup("Application");	sysProp.setNote("0=Enable show overtime in report daily, 1 = Disable  show overtime in report daily");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("ATTANDACE_REASON_DUTTY_NO");	sysProp.setValueType("NUMBER");	sysProp.setGroup("Application");	sysProp.setNote("set Number Reason Tugas Kantor di master data > absen reason");	vSysProp.add(sysProp);

sysProp = new SystemProperty();	sysProp.setName("OVERTIME_IGNORE_OVERLAP");	sysProp.setValueType("NUMBER");	sysProp.setGroup("Application");	sysProp.setNote("true=can't view combobox approval,if list employee overlap schedule,false=disable");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("OVERTIME_ROUND_START"); sysProp.setValueType("NUMBER");	sysProp.setGroup("Application");
sysProp.setNote("start for overtime real duration rounding minunes, e.g. 60 means  Ovtime 63 minutes rount to 60, but when 59 not rounded ");vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("OVERTIME_ROUND_TO"); sysProp.setValueType("NUMBER");	sysProp.setGroup("Application");
sysProp.setNote("round Overtime duration to nearest smaler minutes , e.g. 30 means , when ov=85 minutes round to 60 min, 95=> 90");vSysProp.add(sysProp);
//update by satrya 2012-08-25
sysProp = new SystemProperty();	sysProp.setName("ATTANDACE_ON_BREAK_IN_BREAK_OUT"); sysProp.setValueType("NUMBER");	sysProp.setGroup("Application");sysProp.setNote("1= Enable Attendance Break Out & Break IN, 0=Disable Attendance Break Out & Break IN");vSysProp.add(sysProp);
//update by kartika 2012-09-04
sysProp = new SystemProperty();	sysProp.setName("PAYROLL_PRINT_pageLength"); sysProp.setValueType("NUMBER");	sysProp.setGroup("Application");sysProp.setNote("maximum row per slip");vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("PAYROLL_PRINT_pageWidth"); sysProp.setValueType("NUMBER");	sysProp.setGroup("Application");sysProp.setNote(" maximum lebar per slip");vSysProp.add(sysProp);                
sysProp = new SystemProperty();	sysProp.setName("PAYROLL_PRINT_leftMargin"); sysProp.setValueType("NUMBER");	sysProp.setGroup("Application");sysProp.setNote(" margin di kiri");vSysProp.add(sysProp);                                
sysProp = new SystemProperty();	sysProp.setName("PAYROLL_PRINT_maxLeftgSiteLength"); sysProp.setValueType("NUMBER");	sysProp.setGroup("Application");sysProp.setNote(" maximum lebar bagian kiri slip spt untuk company dan data karyawan");vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("PAYROLL_PRINT_startCompany");  sysProp.setValueType("NUMBER");	sysProp.setGroup("Application");sysProp.setNote(" row mulai print company");vSysProp.add(sysProp);                                
sysProp = new SystemProperty();	sysProp.setName("PAYROLL_PRINT_startColHeaderValue"); sysProp.setValueType("NUMBER");	sysProp.setGroup("Application"); sysProp.setNote(" column mulai nilai text header");vSysProp.add(sysProp);sysProp = new SystemProperty();	
sysProp = new SystemProperty();	sysProp.setName("PAYROLL_PRINT_startRowSlipComp"); sysProp.setValueType("NUMBER");	sysProp.setGroup("Application");sysProp.setNote(" start row slip component");vSysProp.add(sysProp); 
sysProp = new SystemProperty();	sysProp.setName("PAYROLL_CALC_CLASS_NAME");	sysProp.setValueType("STRING");	sysProp.setGroup("Application");	sysProp.setNote("package and Name of class implements I_PayrollCalculator");	vSysProp.add(sysProp);
//update by devin 2014-02-15
sysProp = new SystemProperty();	sysProp.setName("PAYROLL_PTKP_DIRI_SENDIRI");	sysProp.setValueType("NUMBER");	sysProp.setGroup("Application");	sysProp.setNote("Input Jumlah PTKP DIRI SENDIRI");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("PAYROLL_PTKP_KAWIN");	sysProp.setValueType("NUMBER");	sysProp.setGroup("Application");	sysProp.setNote("Input Jumlah PTKP_KAWIN");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("PAYROLL_PTKP_KAWIN_ANAK_1");	sysProp.setValueType("NUMBER");	sysProp.setGroup("Application");	sysProp.setNote("Input Jumlah PTKP_KAWIN_ANAK_1");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("PAYROLL_PTKP_KAWIN_ANAK_2");	sysProp.setValueType("NUMBER");	sysProp.setGroup("Application");	sysProp.setNote("Input Jumlah PTKP_KAWIN_ANAK_2");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("PAYROLL_PTKP_KAWIN_ANAK_3");	sysProp.setValueType("NUMBER");	sysProp.setGroup("Application");	sysProp.setNote("Input Jumlah PTKP_KAWIN_ANAK_3");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("PAYROLL_BIAYA_JABATAN_PERSEN");	sysProp.setValueType("NUMBER");	sysProp.setGroup("Application");	sysProp.setNote("Input Jumlah BIAYA_JABATAN_PERSEN misalnya jika biaya jabatannya 5% maka di tulis 0.05");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("PAYROLL_BIAYA_JABATAN_MAX");	sysProp.setValueType("NUMBER");	sysProp.setGroup("Application");	sysProp.setNote("Input Jumlah BIAYA_JABATAN_MAX");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("PAYROLL_NETTO_PERCENT_CONSULTANT");	sysProp.setValueType("NUMBER");	sysProp.setGroup("Application");	sysProp.setNote("Input Jumlah NETTO_PERCENT_CONSULTANT misalnya jika biaya jabatannya 5% maka di tulis 0.05");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("PAYROLL_range_1");	sysProp.setValueType("NUMBER");	sysProp.setGroup("Application");	sysProp.setNote("Input Jumlah PAYROLL_range_1");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("PAYROLL_percen_1");	sysProp.setValueType("NUMBER");	sysProp.setGroup("Application");	sysProp.setNote("Input Jumlah percen_1 misalnya jika biaya jabatannya 5% maka di tulis 0.05");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("PAYROLL_range_2");	sysProp.setValueType("NUMBER");	sysProp.setGroup("Application");	sysProp.setNote("Input Jumlah PAYROLL_range_2");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("PAYROLL_percen_2");	sysProp.setValueType("NUMBER");	sysProp.setGroup("Application");	sysProp.setNote("Input Jumlah percen_2 misalnya jika biaya jabatannya 5% maka di tulis 0.05");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("PAYROLL_range_3");	sysProp.setValueType("NUMBER");	sysProp.setGroup("Application");	sysProp.setNote("Input Jumlah PAYROLL_range_3");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("PAYROLL_percen_3");	sysProp.setValueType("NUMBER");	sysProp.setGroup("Application");	sysProp.setNote("Input Jumlah percen_3 misalnya jika biaya jabatannya 5% maka di tulis 0.05");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("PAYROLL_percen_4");	sysProp.setValueType("NUMBER");	sysProp.setGroup("Application");	sysProp.setNote("Input Jumlah percen_4 misalnya jika biaya jabatannya 5% maka di tulis 0.05");	vSysProp.add(sysProp);
//update by satrya 2012-10-08
sysProp = new SystemProperty();	sysProp.setName("PAYROLL_INSENTIF_MAX_LEVEL"); sysProp.setValueType("NUMBER");	sysProp.setGroup("Application");sysProp.setNote(" 1.staf,2....,3.Assistant Manager");vSysProp.add(sysProp); 
// tidak jadi di gunakan karena sdh ada di konfigurasi sysProp = new SystemProperty();	sysProp.setName("PAYROLL_INSENTIF_DEPT_ID_EXCEPT"); sysProp.setValueType("NUMBER");	sysProp.setGroup("Application");sysProp.setNote(" Oid departement untuk ,Cek apakah departement itu mendapatkan insentif, contoh di KTI untuk HouseKeeping ");vSysProp.add(sysProp); 
sysProp = new SystemProperty();	sysProp.setName("PAYROLL_PAY_SLIP_CONFIG_OVERTIME_LOCATION"); sysProp.setValueType("NUMBER");	sysProp.setGroup("Application");sysProp.setNote(" configurasi bentuk payslip yang dimana tata letak pengaturan overtime idx,duration dll, 1= letaknya dekat note, 0= letaknya di sebelah kanan");vSysProp.add(sysProp); 
//update by satrya 2013-02-20
sysProp = new SystemProperty();	sysProp.setName("PAYROLL_CALCULATE_UNPAID");	sysProp.setValueType("NUMBER");	sysProp.setGroup("Application");	sysProp.setNote("1= set unpaid with AL,DP,LL, 0 = not Set unpaid with AL,DP,LL and hanya menghitung special unpaid leave");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("PAY_TAX_CON");	sysProp.setValueType("TEXT");	sysProp.setGroup("Application");	sysProp.setNote("silahkan masukkan componen PPH yang dihitung konsultan dengan kode 'TAX_CON', fungsi ini untuk tidak menampilakan di payslip pph yg di hitungkonsultan");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("PAYROLL_PAY_SLIP_CODE_UPAH_LEMBUR");	sysProp.setValueType("TEXT");	sysProp.setGroup("Application");	sysProp.setNote("silahkan masukkan componen Code Upah Lembur misalnya UL, fungsi ini untuk tidak menampilakan jam upah lemburnya");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("PAYROLL_COMPONEN_CODE_GAJI");	sysProp.setValueType("TEXT");	sysProp.setGroup("Application");	sysProp.setNote("silahkan masukkan componen Code Gaji misalnya GAJI, fungsi ini untuk mencari componen code yang bernama gaji di salary componen");	vSysProp.add(sysProp);

sysProp = new SystemProperty();	sysProp.setName("LEAVE_MINUTE_ENABLE");	sysProp.setValueType("NUMBER");	sysProp.setGroup("Application");	sysProp.setNote("1= Leave may set in minutes min (15 Minutes), 0 = full day leave only");	vSysProp.add(sysProp);

sysProp = new SystemProperty();	sysProp.setName("LEAVE_EXCECUTE_BY_ADMIN");	sysProp.setValueType("NUMBER");	sysProp.setGroup("Application");	sysProp.setNote("1= set admin execute leave, 0 = not admin execute leave");	vSysProp.add(sysProp);
//update by satrya 2012-12-03
sysProp = new SystemProperty();	sysProp.setName("LEAVE_FOR_ABSENCE");	sysProp.setValueType("NUMBER");	sysProp.setGroup("Application");	sysProp.setNote("1= set leave to absence, 0 = not set leave to absen (status OK)");	vSysProp.add(sysProp);
//update by satrya 2013-11-14
sysProp = new SystemProperty();	sysProp.setName("CONFIGURASI_EMAIL_LEAVE_WITH_PICTURE");	sysProp.setValueType("TEXT");	sysProp.setGroup("Application");	sysProp.setNote("true= set email with image, false or null = not set email with text");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("USE_PICTURE_EMPLOYEE_IN_LEAVE_FORM");	sysProp.setValueType("TEXT");	sysProp.setGroup("Application");	sysProp.setNote("true= set email with image, false or null = not set email with text");	vSysProp.add(sysProp);

//update by satrya 2013-11-23
sysProp = new SystemProperty();	sysProp.setName("MAX_LEAVE_AL_MINUS");	sysProp.setValueType("NUMBER");	sysProp.setGroup("Application");	sysProp.setNote("insert value maximum minus AL");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("USE_LEAVE_AL_INPUT_MINUS");	sysProp.setValueType("TEXT");	sysProp.setGroup("Application");	sysProp.setNote("true= data yg di pakai adalah inputan user, false or null = data yg dipakai sesuai dengan system");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("MAX_LEAVE_DP_MINUS");	sysProp.setValueType("NUMBER");	sysProp.setGroup("Application");	sysProp.setNote("insert value maximum minus DP");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("MAX_LEAVE_LL_MINUS");	sysProp.setValueType("NUMBER");	sysProp.setGroup("Application");	sysProp.setNote("insert value maximum minus LL");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("USE_LEAVE_LL_INPUT_MINUS");	sysProp.setValueType("TEXT");	sysProp.setGroup("Application");	sysProp.setNote("true= data yg di pakai adalah inputan user, false or null = data yg dipakai sesuai dengan system");	vSysProp.add(sysProp);
//update by satrya 2013-12-18 
sysProp = new SystemProperty();	sysProp.setName("TRANSFER_ABSENSI_WITH_PARAMETER");	sysProp.setValueType("TEXT");	sysProp.setGroup("Application");	sysProp.setNote("true= ada parameter, false: tidak ada parameter");	vSysProp.add(sysProp);
//update by satrya 2013-12-09
//sysProp = new SystemProperty();	sysProp.setName("REPLACE_GM_APPROVALL");	sysProp.setValueType("NUMBER");	sysProp.setGroup("Application");	sysProp.setNote("OID Employee yg digunakan untuk approvall pengganti GM");	vSysProp.add(sysProp);
//sysProp = new SystemProperty();	sysProp.setName("REPLACE_HOTEL_MANAGER_APPROVALL");	sysProp.setValueType("NUMBER");	sysProp.setGroup("Application");	sysProp.setNote("OID Employee yg digunakan untuk approvall pengganti HOTEL MANAGER");	vSysProp.add(sysProp);
//sysProp = new SystemProperty();	sysProp.setName("EMPLOYEE_WHO_APPROVE_BY_HOTEL_MANAGER");	sysProp.setValueType("NUMBER");	sysProp.setGroup("Application");	sysProp.setNote("OID Employee yg akan di approve oleh HOTEL MANAGER");	vSysProp.add(sysProp);
//sysProp = new SystemProperty();	sysProp.setName("OID_POSITION_SPECIAL_APPROVE_BY_GM");	sysProp.setValueType("NUMBER");	sysProp.setGroup("Application");	sysProp.setNote("OID Position Special dari employee yg akan di approve oleh GM");	vSysProp.add(sysProp);
//sysProp = new SystemProperty();	sysProp.setName("OID_POSITION_SPECIAL_APPROVE_BY_HOTEL_MANAGER");	sysProp.setValueType("NUMBER");	sysProp.setGroup("Application");	sysProp.setNote("OID Position Special dari employee yg akan di approve oleh HOTEL MANAGER");	vSysProp.add(sysProp);
//sysProp = new SystemProperty();	sysProp.setName("HM_POS_ID");	sysProp.setValueType("NUMBER");	sysProp.setGroup("Application");	sysProp.setNote("OID Position dari HOTEL MANAGER");	vSysProp.add(sysProp);
//sysProp = new SystemProperty();	sysProp.setName("APPROVALL_EMPLOYEE_KHUSUS");	sysProp.setValueType("NUMBER");	sysProp.setGroup("Application");	sysProp.setNote("berfungsi untuk approvall khusus employee leave ,contohnya oidPosition = oidPositionApprove, ex: 1=12,13,14;2=13,16,17");	vSysProp.add(sysProp);

//update by satrya 2013-12-12
sysProp = new SystemProperty();	sysProp.setName("LEAVE_CONFIG_CALCULATE_CATEGORY_SCHEDULE_OFF");	sysProp.setValueType("TEXT");	sysProp.setGroup("Application");	sysProp.setNote("true= cuti taken tidak akan menghitung jika categorynya off, false: dihitung off");	vSysProp.add(sysProp);
//update by satrya 2014-01-15
sysProp = new SystemProperty();	sysProp.setName("LEVEL_PROP_LEAVE_APPROVALL");	sysProp.setValueType("TEXT");	sysProp.setGroup("Application");	sysProp.setNote("silahkan masukan OID dari level yg diinginkan agar GM yg approve");	vSysProp.add(sysProp);
//update by satrya 2014-01-18
sysProp = new SystemProperty();	sysProp.setName("LEAVE_MAKSIMUM_AL_TAKEN_LEAVE");	sysProp.setValueType("NUMBER");	sysProp.setGroup("Application");	sysProp.setNote("Masukkan Berapa cuti AL maksimum yg boleh di ambil");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("USE_LEAVE_SETTING_CONFIG_DINAMIS");	sysProp.setValueType("TEXT");	sysProp.setGroup("Application");	sysProp.setNote("Masukkan Berapa cuti AL maksimum yg boleh di ambil");	vSysProp.add(sysProp);
//priska 2014-12-10
sysProp = new SystemProperty();	sysProp.setName("CONFIGURATION_SCH_CATEGORY_NIGHT_WORKER");	sysProp.setValueType("TEXT");	sysProp.setGroup("Application");	sysProp.setNote("Masukkan Berapa cuti AL maksimum yg boleh di ambil");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("USE_LOCATION_SET");	sysProp.setValueType("NUMBER");	sysProp.setGroup("Application");	sysProp.setNote("Melakukan pilihan untuk menggunakan lokasi atau tidak");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("USE_GRADE_SET");	sysProp.setValueType("NUMBER");	sysProp.setGroup("Application");	sysProp.setNote("Melakukan pilihan untuk menggunakan grade atau tidak");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("SET_LENGTH_END_CONTRACT");	sysProp.setValueType("TEXT");	sysProp.setGroup("Application");	sysProp.setNote("set hari end cotract");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("PAYROLL_TAX_POST_DEDUCTION");	sysProp.setValueType("TEXT");	sysProp.setGroup("Application");	sysProp.setNote("PAYROLL_TAX_POST_DEDUCTION");	vSysProp.add(sysProp);

// add by Dian 2014-12-25
sysProp = new SystemProperty();	sysProp.setName("VALUE_ANNUAL_LEAVE");	sysProp.setValueType("NUMBER");	sysProp.setGroup("Application");	sysProp.setNote("VALUE_ANNUAL_LEAVE");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("VALUE_DAY_OF_PAYMENT");	sysProp.setValueType("NUMBER");	sysProp.setGroup("Application");	sysProp.setNote("VALUE_DAY_OF_PAYMENT");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("VALUE_B_REASON_SYMBOL");	sysProp.setValueType("NUMBER");	sysProp.setGroup("Application");	sysProp.setNote("VALUE_B_REASON_SYMBOL");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("VALUE_ABSENSE");	sysProp.setValueType("NUMBER");	sysProp.setGroup("Application");	sysProp.setNote("VALUE_ABSENSE");	vSysProp.add(sysProp);

// add by Dian 2015-01-08
sysProp = new SystemProperty();	sysProp.setName("AL_EXPIRED_LOCK_DAYS_TOLERANCE");	sysProp.setValueType("NUMBER");	sysProp.setGroup("Application");	sysProp.setNote("0=Directly Lock, D >= 1 lock after D days = length of period in days as tolerance to lock the AL after expired date (sampai tanggal berapa bisa input cuti sebelum expired date. misal hari ini tgl 8, jika diisi 15, maka cuti sebelum expired akan bisa diinput sampai tanggal 15)");	vSysProp.add(sysProp);

// add by Dian 2015-01-09
sysProp = new SystemProperty();	sysProp.setName("OID_PROBATION");	sysProp.setValueType("NUMBER");	sysProp.setGroup("Application");	sysProp.setNote("OID Employee Category Probation dan sederajat");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("OID_DAILYWORKER");	sysProp.setValueType("NUMBER");	sysProp.setGroup("Application");	sysProp.setNote("OID Employee Category Daily Worker dan sederajat");	vSysProp.add(sysProp);
sysProp = new SystemProperty();	sysProp.setName("OID_GAJIPOKOK");	sysProp.setValueType("NUMBER");	sysProp.setGroup("Application");	sysProp.setNote("OID Salary Component Gaji Pokok dan sejenisnya");	vSysProp.add(sysProp);


//SET_LENGTH_END_CONTRACT
//sysProp = new SystemProperty();	sysProp.setName("USE_LOCATION_SET");	sysProp.setValueType("NUMBER");	sysProp.setGroup("Application");	sysProp.setNote("Melakukan pilihan untuk menggunakan lokasi atau tidak");	vSysProp.add(sysProp);


}




%>

<%!
public String drawList(int iCommand, FrmSystemProperty frmSystemProperty, String groupName, long lOid)
{
	ControlList ctrlist = new ControlList();
	ctrlist.setAreaWidth("100%");
	ctrlist.setListStyle("listgen");
	ctrlist.setTitleStyle("listgensell");
	ctrlist.setCellStyle("listgensell");
	ctrlist.setHeaderStyle("listgentitle");
	ctrlist.setTitle(groupName + " Properties");	
	ctrlist.dataFormat("Name","20%","center","center");
	ctrlist.dataFormat("Value","30%","left","left");
	ctrlist.dataFormat("Value Type","10%","left","left");	
	ctrlist.dataFormat("Description","40%","left","left");	

	String editValPre = "<input type=\"text\" name=\"" + frmSystemProperty.fieldNames[frmSystemProperty.FRM_VALUE] +"\" size=\"100\" value=\"";
	String editValSup = "\"> * "+ frmSystemProperty.getErrorMsg(frmSystemProperty.FRM_NAME);

	ctrlist.setLinkRow(0);
	ctrlist.setLinkSufix("");
	Vector lstData 		= ctrlist.getData();
	Vector lstLinkData 	= ctrlist.getLinkData();
	ctrlist.setLinkPrefix("javascript:cmdEdit('");
	ctrlist.setLinkSufix("')");
	ctrlist.reset();
	
	try{
		if((vSysProp!=null) && (vSysProp.size()>0)){  
			for(int i=0; i<vSysProp.size(); i++){
				 Vector rowx = new Vector();
				 SystemProperty sysProp2 = (SystemProperty)vSysProp.get(i);
                                 if(sysProp2!=null){
                                    SystemProperty sysPropX = PstSystemProperty.fetchByName(sysProp2.getName());                                 
                                    if(sysPropX!=null && sysPropX.getOID()!=0){
                                        sysProp2.setOID(sysPropX.getOID());
                                        sysProp2.setValue(sysPropX.getValue());
                                    } else{
                                        PstSystemProperty.insert(sysProp2);
                                    }
                                 }
                                 if(sysProp2.getValue()==null || sysProp2.getValue().length()<1  ){
                                    rowx.add("<blink>"+sysProp2.getName()+"</blink>");
                                 } else{
                                    rowx.add( sysProp2.getName());
                                 }

				 if(iCommand==Command.ASSIGN && lOid==sysProp2.getOID()){
					rowx.add("<b>"+editValPre + sysProp2.getValue() + editValSup+"</b>");
				 }else{
					rowx.add("<a href=\"javascript:cmdAssign('"+sysProp2.getOID()+"')\">"+sysProp2.getValue()+"</a>");
				 }

				 rowx.add(sysProp2.getValueType());
				 rowx.add(sysProp2.getNote());

				 lstData.add(rowx); 
				 lstLinkData.add(String.valueOf(sysProp2.getOID()));
			}
		}
	}catch(Exception e){
		System.out.println("Exc : " + e.toString());
	} 
	return ctrlist.draw();
}
%>

<%   
int iCommand = FRMQueryString.requestCommand(request);
long lOid = FRMQueryString.requestLong(request, "oid");
CtrlSystemProperty ctrlSystemProperty = new CtrlSystemProperty(request);
ctrlSystemProperty.action(iCommand, lOid, request);

SystemProperty sysProp = ctrlSystemProperty.getSystemProperty();
FrmSystemProperty frmSystemProperty = ctrlSystemProperty.getForm();
%>
<html>
<!-- #BeginTemplate "/Templates/main.dwt" --> 
<head>
<!-- #BeginEditable "doctitle" --> 
<title>System Property</title>
<!-- #EndEditable --> 
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<!-- #BeginEditable "styles" --> 
<link rel="stylesheet" href="../styles/main.css" type="text/css">
<!-- #EndEditable --> <!-- #BeginEditable "stylestab" --> 
<link rel="stylesheet" href="../styles/tab.css" type="text/css">
<!-- #EndEditable --> <!-- #BeginEditable "headerscript" --> 
<SCRIPT language=JavaScript>
function hideObjectForEmployee()
{    
} 
 
function hideObjectForLockers()
{ 
}

function hideObjectForCanteen()
{
}

function hideObjectForClinic()
{
}

function hideObjectForMasterdata()
{
}

function showObjectForMenu()
{
}
	
function cmdList() 
{
  document.frmData.command.value="<%= Command.LIST %>";          
  document.frmData.action = "sysprop.jsp";
  document.frmData.submit();
}

function cmdLoad() 
{
  document.frmData.command.value="<%= Command.LOAD %>";          
  document.frmData.action = "sysprop.jsp";
  document.frmData.submit();
}

function cmdNew() 
{
  document.frmData.command.value="<%= Command.ADD %>";          
  document.frmData.action = "syspropnew.jsp";
  document.frmData.submit();
}

function cmdEdit(oid) 
{
  document.frmData.command.value="<%= Command.EDIT %>";                    
  document.frmData.oid.value = oid;
  document.frmData.action = "syspropnew.jsp";
  document.frmData.submit();
}

function cmdAssign(oid) 
{
  document.frmData.command.value="<%= Command.ASSIGN %>";       
  document.frmData.oid.value= oid;          
  document.frmData.action = "sysprop.jsp";
  document.frmData.submit();
}

function cmdUpdate(oid) 
{
  document.frmData.command.value="<%= Command.UPDATE %>";          
  document.frmData.oid.value = oid;
  document.frmData.action = "sysprop.jsp";
  document.frmData.submit();
}	
</SCRIPT>
<!-- #EndEditable --> 
</head>
<body <%=noBack%> bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
     <%if(headerStyle && !verTemplate.equalsIgnoreCase("0")){%> 
           <%@include file="../styletemplate/template_header.jsp" %>
            <%}else{%>
  <tr> 
    <td ID="TOPTITLE" background="<%=approot%>/images/HRIS_HeaderBg3.jpg" width="100%" height="54"> 
      <!-- #BeginEditable "header" --> 
      <%@ include file = "../main/header.jsp" %>
      <!-- #EndEditable --> </td>
  </tr>
  <tr> 
    <td  bgcolor="#9BC1FF" height="15" ID="MAINMENU" valign="middle"> <!-- #BeginEditable "menumain" --> 
      <%@ include file = "../main/mnmain.jsp" %>
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
                <td height="20"> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" -->System 
                  &gt; Property<!-- #EndEditable --> </strong></font> </td>
              </tr> 
              <tr> 
                <td> 
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td  style="background-color:<%=bgColorContent%>; "> 
                        <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                          <tr> 
                            <td valign="top"> 
                              <table style="border:1px solid <%=garisContent%>" width="100%" border="0" cellspacing="1" cellpadding="1" class="tabbg">
                                <tr> 
                                  <td valign="top"> <!-- #BeginEditable "content" --> 
                                    <form name="frmData" method="post" action="">
                                      <input type="hidden" name="command" value="0">
                                      <input type="hidden" name="oid" value="<%=lOid%>">
                                      <table width="100%" border="0" cellspacing="6" cellpadding="0">
                                        <tr> 
                                          <td> 
                                            <%
											String cbxName = FrmSystemProperty.fieldNames[FrmSystemProperty.FRM_GROUP];
								
											String groupName = FRMQueryString.requestString(request, cbxName);
											if(groupName == null || groupName.equals("")) groupName = "";
								
											Vector grs = Validator.toVector(SessSystemProperty.groups, SessSystemProperty.subGroups, "> ", "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; - ", true);
											Vector val = Validator.toVector(SessSystemProperty.groups, SessSystemProperty.subGroups, "", "", false);
											
											String strChange = "onChange=\"javascript:cmdList()\"";
											out.println("&nbsp;"+ControlCombo.draw(cbxName, "formElemen", null, groupName, val, grs, strChange));
											Vector vctData = PstSystemProperty.listByGroup(groupName);							
											%>
                                        </tr>
                                        <tr> 
                                          <td><%=drawList(iCommand,frmSystemProperty,groupName,lOid)%></td>
                                        </tr>
                                        <tr> 
                                          <td align="right"><%="<i>"+ctrlSystemProperty.getMessage()+"</i>"%> 
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td align="right"> 
                                            <% 
											if(iCommand==Command.ASSIGN && privUpdate)
											{
												out.println("<a href=\"javascript:cmdUpdate('"+lOid+"')\">Update Value</a> | <a href='javascript:cmdList()'>Cancel</a> | ");
											}
											
											if(privAdd)
											{
												out.println("<a href=\"javascript:cmdNew()\">New System Property</a> | ");
											}
											
											out.println("<a href=\"javascript:cmdLoad()\">Load New value</a>&nbsp;");
											%>
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td align="left"> N O T E : <br>
                                            - Use "\\" character when you want 
                                            to input "\" character in value field.<br>
                                            - Click "Load new value" link when 
                                            property it's updated. </td>
                                        </tr>
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
                                <%@include file="../footer.jsp" %>
                            </td>
                            
            </tr>
            <%}else{%>
            <tr> 
                <td colspan="2" height="20" <%=bgFooterLama%>> <!-- #BeginEditable "footer" --> 
      <%@ include file = "../main/footer.jsp" %>
                <!-- #EndEditable --> </td>
            </tr>
            <%}%>
</table>
      <SCRIPT>
// Before you reuse this script you may want to have your head examined
// 
// Copyright 1999 InsideDHTML.com, LLC.  

function doBlink() {
  // Blink, Blink, Blink...
  var blink = document.all.tags("BLINK")
  for (var i=0; i < blink.length; i++)
    blink[i].style.visibility = blink[i].style.visibility == "" ? "hidden" : "" 
}

function startBlink() {
  // Make sure it is IE4
  if (document.all)
    setInterval("doBlink()",1000)
}
window.onload = startBlink;
</SCRIPT>
</body>
<!-- #BeginEditable "script" -->
<!-- #EndEditable --> <!-- #EndTemplate -->
</html>
