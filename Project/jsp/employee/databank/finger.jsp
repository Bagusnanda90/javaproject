<%-- 
    Document   : finger
    Created on : 10-May-2017, 14:27:26
    Author     : Gunadi
--%>
<%@page import="com.dimata.harisma.utility.machine.Finger"%>
<%

    String buffered = Finger.executeThroughSocket(80,"192.168.16.90", "");
    String hasil = "";
    int first = buffered.indexOf("<GetUserTemplateResponse>") + "<GetUserTemplateResponse>".length();
    int end = buffered.indexOf("</GetUserTemplateResponse>",first);
    String subString = buffered.substring(first, end);
    String[] stringArray = subString.split(" ");
    
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1><%=buffered%></h1>
        <table border="1">
            <tr>
                <td>User ID</td>
                <td>Finger ID</td>
                <td>Size</td>
                <td>Valid</td>
                <td>Template</td>
            </tr>
            <%
                if (stringArray.length >0){
                    for (int i=0; i< stringArray.length;i++){
                    
                    if (stringArray[i].length()>0){                         
                    int dataStart = stringArray[i].indexOf("<Row>") + "<Row>".length();
                    int dataEnd = stringArray[i].indexOf("</Row>",dataStart);
                    String data = stringArray[i].substring(dataStart, dataEnd); 
                    
                    int pinStart = data.indexOf("<PIN>") + "<PIN>".length();
                    int pinEnd = data.indexOf("</PIN>",pinStart);
                    String pin = data.substring(pinStart, pinEnd);
                    
                    int fingerStart = data.indexOf("<FingerID>") + "<FingerID>".length();
                    int fingerEnd = data.indexOf("</FingerID>",fingerStart);
                    String finger = data.substring(fingerStart, fingerEnd);
                    
                    int sizeStart = data.indexOf("<Size>") + "<Size>".length();
                    int sizeEnd = data.indexOf("</Size>",sizeStart);
                    String size = data.substring(sizeStart, sizeEnd);
                    
                    int validStart = data.indexOf("<Valid>") + "<Valid>".length();
                    int validEnd = data.indexOf("</Valid>",validStart);
                    String valid = data.substring(validStart, validEnd);
                    
                    int templateStart = data.indexOf("<Template>") + "<Template>".length();
                    int templateEnd = data.indexOf("</Template>",templateStart);
                    String template = data.substring(templateStart, templateEnd);
                    
                    
            %>
            <tr>
                <td><%=pin%></td>
                <td><%=finger%></td>
                <td><%=size%></td>
                <td><%=valid%></td>
                <td><%=template%></td>
                
            </tr>

            <%          }
                    }
                }
            %>
        </table>
    </body>
</html>
