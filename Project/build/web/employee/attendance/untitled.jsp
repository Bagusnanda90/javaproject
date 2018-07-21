<html>
<head>
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>
<script language="javascript">
function changeSchedule(objForm,selectedVal)
{	
	var schld1 = objForm.value;
	var schld2 = selectedVal;	
	
	alert('schld1 : ' + schld1);
	alert('schld2 : ' + schld2);
}
</script>
<body bgcolor="#FFFFFF" text="#000000">
<select name="select" onChange="javascript:changeSchedule(this,'1')">
  <option value="1" selected>1</option>
  <option value="2">2</option>
  <option value="3">3</option>
  <option value="4">4</option>
  <option value="5">5</option>
  <option value="6">6</option>
</select>
</body>
</html>
