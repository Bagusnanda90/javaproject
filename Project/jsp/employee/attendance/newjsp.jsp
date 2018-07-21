<%-- 
    Document   : newjsp
    Created on : Jun 28, 2017, 9:34:42 AM
    Author     : Gunadi
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <style>
            #masterdata_icon {
                color: black;
            }
            #masterdata_icon:hover {
                color: #0088cc;
            }
            .modal-lg{
	    max-width: 1200px;
	    width: 100%;
            }
            
            /* declare a 7 column grid on the table */
            #calendar {
                    width: 100%;
              display: grid;
              grid-template-columns: repeat(7, 1fr);
            }

            #calendar tr, #calendar tbody {
              grid-column: 1 / -1;
              display: grid;
              grid-template-columns: repeat(7, 1fr);
             width: 100%;
            }

            caption {
                    text-align: center;
              grid-column: 1 / -1;
              font-size: 130%;
              font-weight: bold;
              padding: 10px 0;
            }

            #calendar a {
                    color: #8e352e;
                    text-decoration: none;
            }

            #calendar td, #calendar th {
                    padding: 5px;
                    box-sizing:border-box;
                    border: 1px solid #ccc;
            }

            #calendar .weekdays {
                    background: #8e352e;  
            }


            #calendar .weekdays th {
                    text-align: center;
                    text-transform: uppercase;
                    line-height: 20px;
                    border: none !important;
                    padding: 10px 6px;
                    color: #fff;
                    font-size: 13px;
            }

            #calendar td {
                    min-height: 180px;
              display: flex;
              flex-direction: column;
            }

            #calendar .days li:hover {
                    background: #d3d3d3;
            }

            #calendar .date {
                    text-align: center;
                    margin-bottom: 5px;
                    padding: 4px;
                    background: #333;
                    color: #fff;
                    width: 20px;
                    border-radius: 50%;
              flex: 0 0 auto;
              align-self: flex-end;
            }

            #calendar .event {
              flex: 0 0 auto;
                    font-size: 13px;
                    border-radius: 4px;
                    padding: 5px;
                    margin-bottom: 5px;
                    line-height: 14px;
                    background: #e4f2f2;
                    border: 1px solid #b5dbdc;
                    color: #009aaf;
                    text-decoration: none;
            }

            #calendar .event-desc {
                    color: #666;
                    margin: 3px 0 7px 0;
                    text-decoration: none;	
            }

            #calendar .other-month {
                    background: #f5f5f5;
                    color: #666;
            }

            /* ============================
                                            Mobile Responsiveness
               ============================*/


            @media(max-width: 768px) {

                    #calendar .weekdays, #calendar .other-month {
                            display: none;
                    }

                    #calendar li {
                            height: auto !important;
                            border: 1px solid #ededed;
                            width: 100%;
                            padding: 10px;
                            margin-bottom: -1px;
                    }

              #calendar, #calendar tr, #calendar tbody {
                grid-template-columns: 1fr;
              }

              #calendar  tr {
                grid-column: 1 / 2;
              }

                    #calendar .date {
                            align-self: flex-start;
                    }
            }
            
        </style>
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World!</h1>
    </body>
</html>
