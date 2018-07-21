<%@page language="java"%>
<%@page import="java.util.*"%>
<%@page import="java.sql.*"%>
<%@page import="com.dimata.harisma.entity.attendance.*"%>
<%@page import="com.dimata.harisma.db.*"%>
<%

String sql = "select count(employee_id) as x, emp_schedule_id, employee_id from hr_emp_schedule group by employee_id order by x desc";
DBResultSet dbrs = null;
try{
    
    dbrs = DBHandler.execQueryResult(sql);		 
    ResultSet rs = dbrs.getResultSet();
    int count = 0;
    long oid = 0;
    while(rs.next() && count!=1){
	count = rs.getInt(1);
     	oid = rs.getLong(2);
	if(count>1){
	   oid = PstEmpSchedule.deleteExc(oid);
	   out.println("<br>delete schedule : "+oid);	
	}
    }		
}
catch(Exception e){
	out.println(e.toString());
}
finally{
	DBResultSet.close(dbrs);
}


%>
<script language="javascript">
window.close();	
</script>
