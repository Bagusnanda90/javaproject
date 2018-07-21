<%-- 
    Class Name   : footer.jsp
    Author       : Dian Putra
    Created      : Apr 17, 2016 11:12:58 PM
    Function     : Footer, so other file just need to call include this file.
--%>

<footer class="main-footer">
    <strong>Copyright &copy; 2016 <a href="http://www.dimata.com">Dimata IT Solutions</a></strong> All rights reserved.
    
</footer>

<script src="<%= approot%>/styles/toastr/toastr.js"></script>
<script type="text/javascript">
    toastr.options = {
        "closeButton": false,
        "debug": false,
        "newestOnTop": false,
        "progressBar": false,
        "positionClass": "toast-bottom-right",
        "preventDuplicates": false,
        "onclick": null,
        "showDuration": "300",
        "hideDuration": "1000",
        "timeOut": "5000",
        "extendedTimeOut": "1000",
        "showEasing": "swing",
        "hideEasing": "linear",
        "showMethod": "fadeIn",
        "hideMethod": "fadeOut"
      }
     $(document).ready(function(){
//        $("body").addClass("sidebar-collapse");
        var birthday = <%= sizeBirthday %>;
        var endContractNow = <%= sizeContractEndToday %>;
        var endContractMonth = <%= sizeContractEndThismonth %>;
        var endWorkAssign = <%= sizeEndedWorkAssign %>;
        var expiredId = <%= sizeExpiredIdCard %>;
        var empName = "<%= empHeader.getFullName() %>";
        var listMemo = <%= listHeaderMemo.size() %>;

        var isEmpBirthday = "<%=isEmpBirthday%>";
        var namaUser = "<%=namaUser1%>";
        if( namaUser === 'Employee'){
            toastr.info('Happy Birthday ' + empName + ' <br> Wish You All The Best')
        } else {
            if (birthday == 1){
                toastr.info(birthday + ' employee birthday today')
            } else if (birthday > 1){
                toastr.info(birthday + ' employees birthdays today')
            } if ( endContractNow == 1) {
                toastr.info(endContractNow + ' employee contract expires today')    
            } else if ( endContractNow > 1) {
                toastr.info(endContractNow + ' employees contract expires today')    
            } if ( endContractMonth == 1) {
                toastr.info(endContractMonth + ' employee contract expires in 30 days')    
            } else if ( endContractMonth > 1) {
                toastr.info(endContractMonth + ' employees contract expires in 30 days')    
            } if (endWorkAssign == 1){
                toastr.info(endWorkAssign + ' employee work assignment expires in 30 Days')    
            } else if (endWorkAssign > 1){
                toastr.info(endWorkAssign + ' employees work assignment expires in 30 Days')
            } if (expiredId == 1){
                toastr.info(endWorkAssign + ' employee id card expires in 30 Days')    
            } else if (expiredId > 1){
                toastr.info(endWorkAssign + ' employees id card expires in 30 Days')
            }
        }
        
        if (listMemo != 0){
            $("a#logout").click(function(){
               alert('You have ' + listMemo + ' unacknowledge memo \nplease acknowledge first before checkout' ); 
            });
        } else {
            $("a#logout").click(function(){
              $('a#logout').attr('href','<%= approot%>/logout.jsp'); 
            });
        }

        /*
        if ( birthday > 0 && endContractNow > 0 && endContractMonth >0){
            toastr.info('There Are ' + birthday + ' Employee Birthday Today <br> There Are ' + endContractNow + ' Employee Contract Ended Today <br> There Are ' + endContractMonth + ' Employee Contract Ended This Month' )
        }  else if ( birthday > 0 && endContractNow > 0 ){
            toastr.info('There Are ' + birthday + ' Employee Birthday Today <br> There Are ' + endContractNow + ' Employee Contract Ended Today' )
        }  else if ( birthday > 0 && endContractMonth > 0 ){
            toastr.info('There Are ' + birthday + ' Employee Birthday Today <br> There Are ' + endContractMonth + ' Employee Contract Ended This Month' )
        }  else if ( endContractNow > 0 && endContractMonth > 0 ){
            toastr.info('There Are ' + endContractNow + ' Employee Contract Ended Today <br> There Are ' + endContractMonth + ' Employee Contract Ended This Month ' )
        }  else if (birthday > 0){
            toastr.info('There Are ' + birthday + ' Employee Birthday Today')
        } else if ( endContractNow > 0) {
            toastr.info('There Are ' + endContractNow + ' Employee Ended Contract Today')    
        } else if ( endContractMonth > 0) {
            toastr.info('There Are ' + endContractMonth + ' Employee Contract Ended This Month')    
        } else {
            toastr.info('there are no Birthday Today')
        } */

        });

</script> 