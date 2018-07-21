<%-- 
    Document   : home-emp
    Created on : Jan 8, 2016, 11:28:12 AM
    Author     : Dimata 007
--%>
<%@page import="com.dimata.harisma.session.leave.SessLeaveApplication"%>
<%@ include file = "main/javainit.jsp" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%int  appObjCode = 1;%>

<%
if(isLoggedIn==false)
{
%>
	<script language="javascript">
		 window.location="index.jsp";
	</script>
<%
}
%>

<%
String sic = (request.getParameter("ic")==null) ? "0" : request.getParameter("ic");
int infCode = 0;
String msgAccess = "";

I_Dictionary dictionaryD = userSession.getUserDictionary();
               
try
{
	infCode = Integer.parseInt(sic);
}
catch(Exception e)
{ 
	infCode = 0;
}

switch(infCode) 
{
	case I_SystemInfo.DATA_LOCKED : 
		msgAccess  = I_SystemInfo.textInfo[infCode];
		break;

	case I_SystemInfo.HAVE_NOPRIV : 
		msgAccess  = I_SystemInfo.textInfo[infCode];
		break;

	case I_SystemInfo.NOT_LOGIN : 
		msgAccess  = I_SystemInfo.textInfo[infCode];
		break;

	default:
%>
<script language="javascript">
    window.location="index.jsp";
</script>
<%
}
%>
<%
    int iCommand = FRMQueryString.requestCommand(request);
    Vector listNeedApprove = new Vector();
    if (iCommand == Command.LOAD) { 

        listNeedApprove = SessLeaveApplication.getListEmployeeLeaveApplication(emplx.getOID());

    }
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Home</title>
        <link rel="stylesheet" href="styles/main.css" type="text/css">
        <link rel="stylesheet" href="styles/tab.css" type="text/css">
        <style type="text/css">
            .tblStyle {border-collapse: collapse; font-size: 12px;}
            .tblStyle td {padding: 7px 9px; border: 1px solid #CCC; font-size: 12px; background-color: #FFF;}
            .title_tbl {font-weight: bold;background-color: #DDD; color: #575757;}
            .title_page {color:#0db2e1; font-weight: bold; font-size: 14px; background-color: #EEE; border-left: 1px solid #0099FF; padding: 12px 18px;}

            body {color:#373737;}
            #menu_utama {padding: 9px 14px; border-left: 1px solid #0099FF; font-size: 14px; background-color: #F3F3F3;}
            #menu_title {color:#0099FF; font-size: 14px;}
            #menu_teks {color:#CCC;}
            
            #item {
                font-size: 16px;
                color:#474747;
            }
            #char {
                font-size: 16px;
                color:#ff0000;
            }
            #btn {
              background: #3498db;
              border: 1px solid #0066CC;
              border-radius: 3px;
              font-family: Arial;
              color: #ffffff;
              font-size: 12px;
              padding: 3px 9px 3px 9px;
            }

            #btn:hover {
              background: #3cb0fd;
              border: 1px solid #3498db;
            }

            .active {
                background-color: #0b71d0;
                border-bottom: 1px solid #033a6d;
            }
            .title_part {color:#FF7E00; background-color: #F7F7F7; border-left: 1px solid #0099FF; padding: 9px 11px;}
            
            body {background-color: #EEE;}
            .header {
                
            }
            .content-main {
                padding: 21px 32px;
                margin: 0px 23px 59px 23px;
            }
            .content-info {
                padding: 21px;
                border-bottom: 1px solid #E5E5E5;
            }
            .content-title {
                padding: 21px;
                border-bottom: 1px solid #E5E5E5;
                margin-bottom: 5px;
            }
            #title-large {
                color: #575757;
                font-size: 16px;
                font-weight: bold;
            }
            #title-small {
                color:#797979;
                font-size: 11px;
            }
            .content {
                padding: 21px;
            }
            .box {
                margin: 17px 7px;
                background-color: #FFF;
                color:#575757;
            }
            #box_title {
                padding:21px; 
                font-size: 14px; 
                color: #007fba;
                border-top: 1px solid #28A7D1;
                border-bottom: 1px solid #EEE;
            }
            #box_content {
                padding:21px; 
                font-size: 12px;
                color: #575757;
            }
            .box-info {
                padding:21px 43px; 
                background-color: #F7F7F7;
                border-bottom: 1px solid #CCC;
                -webkit-box-shadow: 0 1px 4px rgba(0, 0, 0, 0.065);
                 -moz-box-shadow: 0 1px 4px rgba(0, 0, 0, 0.065);
                      box-shadow: 0 1px 4px rgba(0, 0, 0, 0.065);
            }
            #title-info-name {
                padding: 11px 15px;
                font-size: 35px;
                color: #535353;
            }
            #title-info-desc {
                padding: 7px 15px;
                font-size: 21px;
                color: #676767;
            }
            
            #photo {
                padding: 7px; 
                background-color: #FFF; 
                border: 1px solid #DDD;
            }

            .btn {
                background-color: #00a1ec;
                border-radius: 3px;
                font-family: Arial;
                color: #EEE;
                font-size: 12px;
                padding: 7px 11px 7px 11px;
                text-decoration: none;
            }

            .btn:hover {
                color: #FFF;
                background-color: #007fba;
                text-decoration: none;
            }
            
            .btn-small {
                font-family: sans-serif;
                text-decoration: none;
                padding: 5px 7px; 
                background-color: #676767; color: #F5F5F5; 
                font-size: 11px; cursor: pointer;
                border-radius: 3px;
            }
            .btn-small:hover { background-color: #474747; color: #FFF;}
            
            .btn-small-1 {
                font-family: sans-serif;
                text-decoration: none;
                padding: 5px 7px; 
                background-color: #EEE; color: #575757; 
                font-size: 11px; cursor: pointer;
                border-radius: 3px;
            }
            .btn-small-1:hover { background-color: #DDD; color: #474747;}
            
            .tbl-main {border-collapse: collapse; font-size: 11px; background-color: #FFF; margin: 0px;}
            .tbl-main td {padding: 4px 7px; border: 1px solid #DDD; }
            #tbl-title {font-weight: bold; background-color: #F5F5F5; color: #575757;}
            
            .tbl-small {border-collapse: collapse; font-size: 11px; background-color: #FFF;}
            .tbl-small td {padding: 2px 3px; border: 1px solid #DDD; }
            
            #caption {
                font-size: 12px;
                color: #474747;
                font-weight: bold;
                padding: 2px 0px 3px 0px;
            }
            #divinput {
                margin-bottom: 5px;
                padding-bottom: 5px;
            }
            
            #div_item_sch {
                background-color: #EEE;
                color: #575757;
                padding: 5px 7px;
            }
            
            #record_count{
                font-size: 12px;
                font-weight: bold;
                padding-bottom: 9px;
            }
            #box-form {
                background-color: #EEE; 
                border-radius: 5px;
            }
            .form-style {
                font-size: 12px;
                color: #575757;
                border: 1px solid #DDD;
                border-radius: 5px;
            }
            .form-title {
                padding: 11px 21px;
                text-align: left;
                border-bottom: 1px solid #DDD;
                background-color: #FFF;
                border-top-left-radius: 5px;
                border-top-right-radius: 5px;
                font-weight: bold;
            }
            .form-content {
                text-align: left;
                padding: 21px;
                background-color: #DDD;
            }
            .form-footer {
                border-top: 1px solid #DDD;
                padding: 11px 21px;
                background-color: #FFF;
                border-bottom-left-radius: 5px;
                border-bottom-right-radius: 5px;
            }
            #confirm {
                padding: 13px 17px;
                background-color: #FF6666;
                color: #FFF;
                border-radius: 3px;
                font-size: 12px;
                font-weight: bold;
                visibility: hidden;
            }
            #btn-confirm {
                padding: 4px 9px; border-radius: 3px;
                background-color: #CF5353; color: #FFF; 
                font-size: 11px; cursor: pointer;
            }
            .footer-page {
                font-size: 12px;
            }
            
        </style>
    </head>
    <body onload="prepare()">
        <div class="header">
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <%if (headerStyle && !verTemplate.equalsIgnoreCase("0")) {%> 
            <%@include file="styletemplate/template_header.jsp" %>
            <%} else {%>
            <tr> 
                <td ID="TOPTITLE" background="<%=approot%>/images/HRIS_HeaderBg3.jpg" width="100%" height="54"> 
                    <!-- #BeginEditable "header" --> 
                    <%@ include file = "main/header.jsp" %>
                    <!-- #EndEditable --> 
                </td>
            </tr>
            <tr> 
                <td  bgcolor="#9BC1FF" height="15" ID="MAINMENU" valign="middle"> <!-- #BeginEditable "menumain" --> 
                    <%@ include file = "main/mnmain.jsp" %>
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
            </table>
        </div>
        <div id="menu_utama">
            <span id="menu_title">Home</span>
        </div>
        <div class="content-main">
            <h2>KODE ETIK BANKIR INDONESIA</h2>
            <p>
            <ol style="font-size: 13px">
                <li>Seorang bankir  patuh dan taat pada ketentuan perundang-undangan dan peraturan yang berlaku</li>
                <li>Seorang bankir melakukan pencatatan yang benar mengenai segala transaksi yang bertalian dengan banknya</li>
                <li>Seorang bankir menghindarkan diri dari persaingan yang tidak sehat</li>
                <li>Seorang bankir tidak menyalahgunakan wewenangnya untuk kepentingan pribadi</li>
                <li>Seorang bankir menghindarkan diri dari keterlibatan pengambilan keputusan  dalam hal terdapat pertentangan kepentingan</li>
                <li>Seorang bankir menjaga kerahasiaan nasabah dan banknya</li>
                <li>Seorang bankir memperhitungkan dampak yang merugikan dari setiap kebijakan yang ditetapkan banknya terhadap keadaan ekonomi, sosial dan lingkungan</li>
                <li>Seorang bankir tidak menerima hadiah atau imbalan yang memperkaya diri sendiri maupun keluarga</li>
                <li>Seorang bankir tidak melakukan perbuatan tercela yang dapat merugikan citra profesinya</li>
            </ol>	
	
            </p>
            <h2>BUDAYA KERJA DAN KODE ETIK</h2>
            
            <table class="tblStyle">
                <tr>
                    <td style="color:#d2ff00; background-color: #51a20f; font-weight: bold;" rowspan="2">BUDAYA KERJA</td>
                    <td style="color:#d2ff00; background-color: #51a20f; font-weight: bold;" colspan="2" align="center">KODE ETIK</td>
                </tr>
                <tr>
                    <td style="color:#d2ff00; background-color: #51a20f; font-weight: bold;">KEWAJIBAN</td>
                    <td style="color:#d2ff00; background-color: #51a20f; font-weight: bold;">LARANGAN</td>
                </tr>
                <tr>
                    <td colspan="3" style="background-color: #F5F5F5; font-weight: bold;"><span id="char">C</span>ompetent</td>
                </tr>
                <tr>
                    <td>Memiliki pengetahuan, keterampilan dan kemampuan yang dibutuhkan untuk melakukan dan menyelesaikan suatu pekerjaan sesuai dengan kualitas yang telah ditetapkan</td>
                    <td>
                        <ul>
                            <li>Bekerja dengan menggunakan ketrampilan dan berpikir secara ilmiah untuk mencapai visi misi satuan kerja</li>
                            <li>Bekerja dengan memanfaatkan teknologi dan ilmu pengetahuan yang relevan dalam penyelesaian tugas</li>
                            <li>Bekerja dengan prosedur, akurat teliti serta memahami risiko tugas secara profesional</li>
                        </ul>
                    </td>
                    <td>
                        <ul>
                            <li>Menutup peluang untuk meningkatkan pengetahuan dan ketrampilan dalam rangka menajga kredibilitas Bank secara kontinyu</li>
                        </ul>
                    </td>
                </tr>
                <tr>
                    <td colspan="3" style="background-color: #F5F5F5; font-weight: bold;"><span id="char">IN</span>tegrity</td>
                </tr>
                <tr>
                    <td>Konsisten dan selalu patuh terhadap nilai-nilai moral atau peraturan lainnya, terutama nilai kejujuran dan anti korupsi serta kolusi</td>
                    <td>
                        <ul>
                            <li>Menerapkan pelaksanaan tugas sesuai dengan ketentuan, berperilaku kerja sesuai tata tertib dan tidak menyiasati aturan untuk kepentingan  pribadi</li>
                            <li>Berlaku jujur dan tidak memberi, menerima serta tidak membuka peluang suap menyuap atau mengharap jasa berkaitan dengan jabatan</li>
                            <li>Mengembangkan etos kerja dengan dasar agama dan memandang kerja sebagai ibadah serta memiliki akhlak yang baik</li>
                        </ul>
                    </td>
                    <td>
                        <ul>
                            <li>Melakukan tindakan tercela</li>
                            <li>Mengambil tindakan yang dapat merugikan atau mengurangi keuntungan Bank</li>
                            <li>Menggunakan aset Bank tanpa ijin pihak yang berwenang untuk kepentingan pribadi dan/atau pihak ketiga yang dapat merugikan Bank</li>
                            <li>Menerima hadiah baik secara langsung atau tidak langsung dari pemangku  kepentingan  dalam bentuk apapun terkait dengan tugas dan tanggung jawabnya di Bank</li>
                            <li>Membuat kesepakatan, komitmen, yang dapat mengikat Bank tanpa kewenangan dari Bank</li>
                            <li>Berpolitik praktis</li>
                        </ul>
                    </td>
                </tr>
                <tr>
                    <td colspan="3" style="background-color: #F5F5F5; font-weight: bold;"><span id="char">T</span>eam Work</td>
                </tr>
                <tr>
                    <td>Rasa kekompakan atau persatuan yang ada dalam organisasi dan kedekatan dengan sesama individu atau pada sesama satuan kerja sehingga mampu mendukung terciptanya kerjasama dan komunikasi yang baik.</td>
                    <td>
                        <ul>
                            <li>Menghargai perbedaan pendapat   dan   membantu jika diminta bantuan satuan kerja lain serta tidak menonjolkan ego sektoral/satuan kerja yang berlebihan </li>
                            <li>Menghargai eksistensi dan wewenang pimpinan secara proporsional mengembangkan pada prinsip positif kepada orang lain serta menghargai apa yang dikerjakan orang lain </li>
                            <li>Tidak mengeksploitasi perbedaan (pangkat, jabatan, sektor) dan saling menghormati, serta bertegur sapa sebagai ungkapan kekeluargaan</li>
                        </ul> 
                    </td>
                    <td>
                        <ul>
                            <li>Memberikan komitmen  dan loyalitaskepada kelompok dan/atau pribadi  di  atas kepentingan Bank</li>
                            <li>Melecehkan agama, kepercayaan, budaya, dan adat istiadat</li>
                        </ul> 
                    </td>
                </tr>
                <tr>
                    <td colspan="3" style="background-color: #F5F5F5; font-weight: bold;">Customer <span id="char">A</span>wareness</td>
                </tr>
                <tr>
                    <td>Menjadikan pengguna sebagai faktor utama dari tindakan kita, mengemban dan mempertahankan hubungan dengan pelanggan secara produktif.</td>
                    <td>
                        <ul>
                            <li>Berbagi informasi dengan nasabah atau membangun pemahaman mereka akan isu dan kemampuan untuk menyelesaikan</li>
                            <li>Secara aktif mencari informasi untuk memahami situasi, masalah, harapan dan kebutuhan nasabah untuk ditindaklanjuti sesuai dengan Kewenangan</li>
                            <li>Menjaga hubungan baik dengan nasabah, bertindak   cepat   untuk memenuhi kebutuhannya dan menyelesaikan masalahnya dan menghindari komitmen yang berlebihan</li>
                        </ul>
                    </td>
                    <td>
                        <ul>
                            <li>Melanggar ketentuan   tentang kerahasiaan    data dan informasi sesuai dengan kebijakan  Bank dan ketentuan hukum yang berlaku</li>
                            <li>Mengambil peluang bisnis Bank untuk kepentingan dirinya sendiri</li>
                        </ul>
                    </td>
                </tr>
            </table>
            
            
            
            
            
            <h2>ANTI FRAUD STATEMENT</h2>
         
            <ul style="font-size: 13px;">
                <li>Setiap insan Bank sebelum melaksanakan kegiatan, selalu diawali dengan niat baik dan semata-mata bekerja untuk kepentingan Bank serta tidak merugikan orang lain, yang dilandasi keikhlasan dan tulus sehingga dapat memuaskan stake holders.</li>
                <li>Setiap Insan Bank berkewajiban menghindari perbuatan-perbuatan penyimpangan yang dilakukan untuk mengelabui, menipu atau memanipulasi Bank, nasabah atau pihak lain, yang terjadi di lingkungan Bank dan/atau menggunakan sarana Bank sehingga mengakibatkan Bank dan/atau menggunakan sarana sehingga mengakibatkan Bank, nasabah atau pihak lain menderita kerugian dan/atau pelaku fraud memperoleh keuntungan.</li>
                <li>Setiap Insan Bank berkewajiban melaporkan setiap fakta penyimpangan yang dilakukan untuk mengelabui, menipu atau memanipulasi Bank, nasabah atau pihak lain yang terjadi di lingkungan Bank dan/atau menggunakan sarana Bank sehingga mengakibatkan Bank, nasabah atau pihak lain menderita kerugian.</li>
                <li>Setiap Insan Bank tidak diperbolehkan memberi dan atau menerima hadiah/ cinderamata/ gratifikasi dalam bentuk apapun yang berhubugan dengan jabatan dan pekerjaannya sehingga menimbulkan konflik kepentingan.</li>
            </ul>
        </div>
        <div class="footer-page">
            <table>
                <%if (headerStyle && !verTemplate.equalsIgnoreCase("0")) {%>
                <tr>
                    <td valign="bottom"><%@include file="footer.jsp" %></td>
                </tr>
                <%} else {%>
                <tr> 
                    <td colspan="2" height="20" ><%@ include file = "main/footer.jsp" %></td>
                </tr>
                <%}%>
            </table>
        </div>
    </body>
</html>
