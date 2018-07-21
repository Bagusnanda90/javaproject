<%@ page language="java"%>

<%@ page import = "java.util.*"%>
<%@ page import = "java.text.*"%>
<%@ page import = "com.dimata.backoffice.accon.parser.*"%>

<%!

/**
 * Adapt calendar to client time zone.
 * @param calendar - adapting calendar
 * @param timeZone - client time zone
 * @return adapt calendar to client time zone
 */

public static Calendar convertCalendar(final Calendar calendar, final TimeZone timeZone) {
    Calendar ret = new GregorianCalendar(timeZone);
    ret.setTimeInMillis(calendar.getTimeInMillis() +
            timeZone.getOffset(calendar.getTimeInMillis()) -
            TimeZone.getDefault().getOffset(calendar.getTimeInMillis()));
    ret.getTime();
    return ret;
}

%>

<html>
<head>
<title>Test DB</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>
<body bgcolor="#FFFFFF" text="#000000">
 <%//TimeZone.setDefault(TimeZone.getTimeZone("America/St_Thomas"));
 com.dimata.util.DateCalc.setUserTimezone("America/St_Thomas");
  Date dt0= com.dimata.util.DateCalc.getDate();
  TimeZone.setDefault(TimeZone.getTimeZone("Asia/Makassar"));  
  Calendar calX = new GregorianCalendar();
  Date dtX = calX.getTime();

  TimeZone tmY = TimeZone.getTimeZone("Europe/Zurich");
  Calendar calY = convertCalendar(calX, tmY);
  Date dtY = calY.getTime();
%>
  <%=dt0%><br>
  <%=dtX%><br>
  <%=dtY%><br>
  <%
   String tms[] = TimeZone.getAvailableIDs();
   if(tms!=null){
        for(int i=0;i<tms.length;i++){
               %> 
                 <%=tms[i]%>
                <%
            }
       }
   %>

    <%
    DateFormat dfm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    dfm.setTimeZone(TimeZone.getTimeZone("Europe/Zurich"));
    Date a = dfm.parse("2007-02-26 20:15:00");
    Date b = dfm.parse("2007-02-27 08:00:00");
    out.println(a);

    Date dt = com.dimata.util.DateCalc.getDate();
    %>
    <br>
    <%
    out.println(b); %> 
    <br> dt1=<%
    out.println(dt);
  %>
  <br>
  <%=TimeZone.getTimeZone("Singapore")%><br>
  <%
    dfm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    dfm.setTimeZone(TimeZone.getTimeZone("Singapore"));
    a = dfm.parse("2007-02-26 20:15:00");
    b = dfm.parse("2007-02-27 08:00:00");
    out.println(a);
    Date dt2 = com.dimata.util.DateCalc.getDate();
   %>
    <br>
    <%
    out.println(b); %> <br>Dt2 =<%
    out.println(dt2);
  %>


</body>

</html>

