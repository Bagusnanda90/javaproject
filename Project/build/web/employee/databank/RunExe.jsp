<%
    response.setContentType("application/x-msdownload");            
response.setHeader("Content-disposition", "attachment; filename=tes.bat");
%>
"C:\Program Files (x86)\WinSCP\WinSCP.exe"
pause
