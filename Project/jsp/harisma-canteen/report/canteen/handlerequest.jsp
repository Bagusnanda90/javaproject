<%@ page import="com.dimata.qdep.form.FRMQueryString,
                 java.util.Vector"%>
<%@ include file = "../../../main/javainit.jsp" %>
<%
    String appPrepare = FRMQueryString.requestString(request, "app_prepared");
    String appApproved = FRMQueryString.requestString(request, "app_approved");
    System.out.println(appPrepare);
            System.out.println(appApproved);
    if (session.getValue("CANTEEN_PAYMENT_MEAL_DATA") != null){
        Vector monthlyData = (Vector)session.getValue("CANTEEN_PAYMENT_MEAL_DATA");
        System.out.println(monthlyData.size());
        if(monthlyData.size() > 9){
            monthlyData.setElementAt(appPrepare,9);
            monthlyData.setElementAt(appApproved,10);
        }else{
            monthlyData.add(appPrepare);
            monthlyData.add(appApproved);
        }
        session.putValue("CANTEEN_PAYMENT_MEAL_DATA",monthlyData);
        System.out.println(monthlyData.size());
    }
%>
<script language="JavaScript" type="text/JavaScript">
<!--
var linkPage = "<%=printroot%>.report.canteen.PeriodicMealPayment";
handle = window.open(linkPage, "<%=canteenWindowName%>");
//-->
</script>