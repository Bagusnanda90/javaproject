<%-- 
    Document   : careerpath_detail
    Created on : Oct 27, 2015, 10:41:26 AM
    Author     : Dimata 007
--%>

<%@page import="com.dimata.common.entity.contact.PstContactList"%>
<%@page import="com.dimata.common.entity.contact.ContactList"%>
<%@page import="com.dimata.util.lang.I_Dictionary"%>
<%@ include file = "../../main/javainit.jsp" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
long oidCareerPath = FRMQueryString.requestLong(request, "oid");
CareerPath careerPath = new CareerPath();
if (oidCareerPath != 0){
    try {
        careerPath = PstCareerPath.fetchExc(oidCareerPath);
    } catch(Exception e){
        System.out.println(e.toString());
    }
}
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Career Detail</title>
        <style type="text/css">
            body {
                font-family: sans-serif;
                font-size: 11px;
                margin: 0; padding: 0;
                background-color: #FFF;
                color: #474747;
            }
            #header-title {
                font-size: 24px; 
                color: #007592; text-align: center;
            }
            .header {
                margin: 0;
                background-color: #FFF;
                border-bottom: 1px solid #BEE1ED;
            }
            .content-main{
                margin: 0;
                padding: 11px;
                background-color: #EEE;
            }
            .content-box {
                background-color: #FFF;
                padding: 15px;
                margin: 15px 9px;
                border-radius: 3px;
            }
            .item {margin: 7px 0px;}
            #caption {font-weight: bold;}
            #value {padding-top: 2px;}
        </style>
    </head>
    <body>
        <div class="header">
            <p id="header-title">View Career Detail</p>
        </div>
        <div class="content-main">
            <table cellpadding="0" cellspacing="0" width="100%">
                <tr>
                    <td valign="top">
                        <div class="content-box">
                            <div class="item">
                                <div id="caption"><%=dictionaryD.getWord(I_Dictionary.COMPANY)%></div>
                                <div id="value"><%= careerPath.getCompany() %></div>
                            </div>
                            <div class="item">
                                <div id="caption"><%=dictionaryD.getWord(I_Dictionary.DIVISION)%></div>
                                <div id="value"><%= careerPath.getDivision() %></div>
                            </div>
                            <div class="item">
                                <div id="caption"><%=dictionaryD.getWord(I_Dictionary.DEPARTMENT)%></div>
                                <div id="value"><%= careerPath.getDepartment() %></div>
                            </div>
                            <div class="item">
                                <div id="caption"><%=dictionaryD.getWord("SECTION")%></div>
                                <div id="value"><%= careerPath.getSection() %></div>
                            </div>
                            <div class="item">
                                <div id="caption"><%=dictionaryD.getWord(I_Dictionary.POSITION)%></div>
                                <div id="value"><%= careerPath.getPosition() %></div>
                            </div>
                                <div class="item">
                                <div id="caption"><%=dictionaryD.getWord("LEVEL")%></div>
                                <div id="value"><%= careerPath.getLevel() %></div>
                            </div>
                            <div class="item">
                                <div id="caption"><%=dictionaryD.getWord("CATEGORY")%></div>
                                <div id="value"><%= careerPath.getEmpCategory() %></div>
                            </div>  
                        </div>
                            <div class="content-box">
                                <div class="item">
                                    <div id="caption">Work from</div>
                                    <div id="value"><%= careerPath.getWorkFrom() %></div>
                                </div>
                                <div class="item">
                                    <div id="caption">Work to</div>
                                    <div id="value"><%= careerPath.getWorkTo() %></div>
                                </div>
                            </div>
                    </td>
                    <td valign="top">
                        <div class="content-box">
                            <div class="item">
                                <div id="caption">Salary</div>
                                <div id="value"><%= careerPath.getSalary() %></div>
                            </div>
                            <div class="item">
                                <div id="caption">Description</div>
                                <div id="value"><%= careerPath.getDescription() %></div>
                            </div>
                        </div>
                        <div class="content-box">
                            <div class="item">
                                <div id="caption">History Type</div>
                                <div id="value"><%= PstCareerPath.historyType[careerPath.getHistoryType()] %></div>
                            </div>
                            <div class="item">
                                <div id="caption">History Group</div>
                                <div id="value"><%= PstCareerPath.historyGroup[careerPath.getHistoryGroup()] %></div>
                            </div>
                            <div class="item">
                                <div id="caption">Provider</div>
                                <%
                                ContactList contact = new ContactList();
                                String provider = "-";
                                try {
                                    contact = PstContactList.fetchExc(careerPath.getProviderID());
                                    provider = contact.getCompName();
                                } catch (Exception e){
                                    System.out.println(e.toString());
                                }
                                %>
                                <div id="value"><%= provider %></div>
                            </div>
                            <div class="item">
                                <div id="caption">Nomor SK</div>
                                <div id="value"><%= careerPath.getNomorSk() %></div>
                            </div>
                            <div class="item">
                                <div id="caption">Tanggal SK</div>
                                <div id="value"><%= careerPath.getTanggalSk() %></div>
                            </div>
                        </div>
                    </td>
                </tr>
            </table>
        </div>
        
    </body>
</html>
