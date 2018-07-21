<%-- 
    Document   : organisasi
    Created on : Aug 7, 2015, 11:38:03 AM
    Author     : Dimata 007
--%>

<%@page import="com.dimata.harisma.entity.masterdata.TopPosition"%>
<%@page import="com.dimata.harisma.entity.masterdata.PstTopPosition"%>
<%@page import="com.dimata.harisma.entity.masterdata.PositionDivision"%>
<%@page import="com.dimata.harisma.entity.masterdata.PstPositionDivision"%>
<%@page import="com.dimata.harisma.entity.masterdata.Division"%>
<%@page import="com.dimata.harisma.entity.masterdata.PstDivision"%>
<%@page import="com.dimata.harisma.entity.masterdata.Position"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.Vector"%>
<%@page import="com.dimata.harisma.entity.masterdata.PstPosition"%>
<!DOCTYPE html>
<%!

    public String getPositionName(long posId){
        String position = "";
        Position pos = new Position();
        try {
            pos = PstPosition.fetchExc(posId);
        } catch(Exception ex){
            System.out.println("getPositionName ==> "+ex.toString());
        }
        position = pos.getPosition();
        return position;
    }
   
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <style type="text/css">

            #mn_utama {color: #FF6600; padding: 5px 14px; border-left: 1px solid #999; font-size: 12px; font-weight: bold; background-color: #F5F5F5;}
            
            #menu_utama {padding: 9px 14px; border-left: 1px solid #0099FF; font-size: 14px; background-color: #F3F3F3; color:#0099FF; font-size: 14px; font-weight: bold;}
            
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
            #btn1 {
              background: #f27979;
              border: 1px solid #d74e4e;
              border-radius: 3px;
              font-family: Arial;
              color: #ffffff;
              font-size: 12px;
              padding: 3px 9px 3px 9px;
            }

            #btn1:hover {
              background: #d22a2a;
              border: 1px solid #c31b1b;
            }
            #tdForm {
                padding: 5px;
            }
            .tblStyle {border-collapse: collapse;font-size: 9px;}
            .tblStyle td {padding: 3px 5px; border: 1px solid #999;}
            .title_tbl {font-weight: bold;background-color: #DDD; color: #575757;}
            #confirm {background-color: #fad9d9;border: 1px solid #da8383; color: #bf3c3c; padding: 14px 21px;border-radius: 5px;}
            #desc_field_type{padding:7px 12px; background-color: #F3F3F3; border:1px solid #FFF; margin:3px 0px;}
            #text_desc {background-color: #FFF;color:#575757; padding:3px; font-size: 9px;}
            #data_list{padding:3px 5px; color:#FFF; background-color: #79bbff; margin:2px 1px 2px 0px; border-radius: 3px;}
            #data_list_close {padding:3px 5px; color:#FFF; background-color: #79bbff; margin:2px 1px 2px 0px; border-radius: 3px; cursor: pointer;}
            #data_list_close:hover {padding:3px 5px; color:#FFF; background-color: #0099FF; margin:2px 1px 2px 0px; border-radius: 3px;}
        </style>
    </head>
    <body>

<%
/* Search Divisi BOD */
// get data that have BOD type
String whereBOD = PstDivision.fieldNames[PstDivision.FLD_TYPE_OF_DIVISION]+"=2";
Vector listBOD = PstDivision.list(0, 0, whereBOD, "");
long oidBOD = 0;
if (listBOD != null && listBOD.size()>0){
    for(int b=0; b<listBOD.size(); b++){
        Division div = (Division)listBOD.get(b);
        oidBOD = div.getOID(); /* get BOD ID*/
    }
}
/* mendapatkan list yang bertipe BOD */
String wherePosBOD = PstPositionDivision.fieldNames[PstPositionDivision.FLD_DIVISION_ID]+"="+oidBOD;
Vector listPosBOD = PstPositionDivision.list(0, 0, wherePosBOD, "");
String whereManagePos = "";
long direkturTop = 0;
Vector listDownDir = new Vector(1,1);
if (listPosBOD != null && listPosBOD.size()>0){
    for(int db=0; db<listPosBOD.size(); db++){
        PositionDivision posDiv = (PositionDivision)listPosBOD.get(db);
        /* Cek pada hr_managing_position */
        whereManagePos = PstTopPosition.fieldNames[PstTopPosition.FLD_POSITION_ID]+"="+posDiv.getPositionId();
        listDownDir = PstTopPosition.list(0, 0, whereManagePos, "");
        if (listDownDir != null && listDownDir.size()>0){
            TopPosition tp = (TopPosition)listDownDir.get(0);
            direkturTop = tp.getPositionToplink(); /* mendapatkan ID Top Link */
        }
    }
}
%>
<table class="tblStyle">
    <tr>
        <td valign="middle">
            <i style="font-size: 11px">Board Of Director</i>
            <%
            /* mencari bawahannya direktur */
            String whereDownlinkDir = PstTopPosition.fieldNames[PstTopPosition.FLD_POSITION_TOPLINK]+"="+direkturTop;
            Vector listDownlinkDir = PstTopPosition.list(0, 0, whereDownlinkDir, "");
            %>
            <table class="tblStyle">
                <%if(direkturTop > 0){%>
                <tr>
                    <td align="center" colspan="<%=listDownlinkDir.size()%>"><%=getPositionName(direkturTop)%></td>
                </tr>
                <%}%>
                <tr>
                    <%
                    if (listDownlinkDir != null && listDownlinkDir.size()>0){
                        for(int dld=0; dld<listDownlinkDir.size(); dld++){
                            TopPosition dwn = (TopPosition)listDownlinkDir.get(dld);//
                            String whereDiv = PstTopPosition.fieldNames[PstTopPosition.FLD_POSITION_TOPLINK]+"="+dwn.getPositionId();
                            Vector listDivision = PstTopPosition.list(0, 0, whereDiv, "");
                            %>
                            <td>
                                
                                <table class="tblStyle">
                                    <tr>
                                        <td colspan="<%=listDivision.size()%>"><%=getPositionName(dwn.getPositionId())%></td>
                                    </tr>
                                    <tr>
                                        <%
                                        if (listDivision != null && listDivision.size()>0){
                                            for(int ldiv=0; ldiv<listDivision.size(); ldiv++){
                                                TopPosition dvp = (TopPosition)listDivision.get(ldiv);
                                                String whDep = PstTopPosition.fieldNames[PstTopPosition.FLD_POSITION_TOPLINK]+"="+dvp.getPositionId();
                                                Vector listDep = PstTopPosition.list(0, 0, whDep, "");
                                                %>
                                                <td>
                                                    <table class="tblStyle">
                                                        <tr>
                                                            <td colspan="<%=listDep.size()%>"><%=getPositionName(dvp.getPositionId())%></td>
                                                        </tr>
                                                        <tr>
                                                            <%
                                                            if (listDep != null && listDep.size()>0){
                                                                for(int ldp=0; ldp<listDep.size(); ldp++){
                                                                    TopPosition dp = (TopPosition)listDep.get(ldp);
                                                                    %>
                                                                    <td><%=getPositionName(dp.getPositionId())%></td>
                                                                    <%
                                                                }
                                                            }
                                                            %>
                                                        </tr>
                                                    </table>
                                                </td>
                                                <%
                                            }
                                        }
                                        %>
                                        
                                    </tr>
                                </table>
                            </td>
                            <%
                        }
                    }
                    %>
                </tr>
            </table>
        </td>
    </tr>
</table>
        
    </body>
</html>
