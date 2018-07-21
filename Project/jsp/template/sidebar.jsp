<%-- 
    Class Name   : sidebar.jsp
    Author       : Dian Putra
    Created      : Apr 17, 2016 11:11:38 PM
    Function     : Sidebar, so other file just need to call include this file.
--%>

<aside class="main-sidebar">
    <section class="sidebar">
        <div class="user-panel">
            &nbsp;
        </div>
        <ul class="sidebar-menu">
            <li class="header text-center">DIMATA HAIRISMA</li>
            <li>
                <a href="<%= approot%>/home.jsp"><i class="fa fa-dashboard "></i> <span>Dashboard</span></a>
            </li>
            <li id="Employee">
              <a href="#"><i class="fa fa-users"></i> <span>Employee</span> <i class="fa fa-angle-left pull-right"></i></a>
              <ul class="treeview-menu">
                  <li id="databank">
                    <a href="#"><i class="fa fa-circle-o"></i> <span>Employee</span> <i class="fa fa-angle-left pull-right"></i></a>
                    <ul class="treeview-menu">
                      <li id="EmpData"><a  href="<%= approot%>/employee/databank/employee_list.jsp"><i class="fa fa-circle-o"></i> Employee Data</a></li>
                      <li id="AddEmp"><a  href="<%= approot%>/employee/databank/employee_edit.jsp?command=2"><i class="fa fa-circle-o"></i> Add New Employee</a></li>
                    </ul>
                  </li>
                  <li id="recruitment">
                    <a href="#"><i class="fa fa-circle-o"></i> <span>Recruitment</span> <i class="fa fa-angle-left pull-right"></i></a>
                    <ul class="treeview-menu">
                      <li id="staffRequisition"><a  href="<%= approot%>/employee/recruitment/staffrequisition.jsp"><i class="fa fa-circle-o"></i> Staff Requisition</a></li>
                    </ul>
                  </li>
              </ul>
            </li>
            
            <li id="attendancemenu">
              <a href="#"><i class="fa fa-clock-o"></i> <span>Attendance</span> <i class="fa fa-angle-left pull-right"></i></a>
              <ul class="treeview-menu">
                <li ><a href="<%= approot%>/employee/attendance/srcempscheduleNew.jsp"><i class="fa fa-circle-o"></i>Working Schedule</a></li>
                <li id="manual_registration"><a href="<%= approot%>/employee/presence/presence_list_new.jsp"><i class="fa fa-circle-o"></i>Manual Registration</a></li>
                <li><a href="<%= approot%>/employee/attendance/reportAtt.jsp"><i class="fa fa-circle-o"></i>Report</a></li>
              </ul>
            </li>
            <li>
              <a href="#"><i class="fa fa-file"></i> <span>Leave</span> <i class="fa fa-angle-left pull-right"></i></a>
              <ul class="treeview-menu">
                <li><a href="<%= approot%>/employee/leave/leave_app_src.jsp"><i class="fa fa-circle-o"></i>Leave Form</a></li>
              </ul>
            </li>
            <li id="Aset_Inventory">
              <a href="#"><i class="fa fa-file"></i> <span>Asset & Inventory</span> <i class="fa fa-angle-left pull-right"></i></a>
              <ul class="treeview-menu">
                <li id="aset_Inventory"><a href="<%= approot%>/employee/asset/asset_inventory.jsp"><i class="fa fa-circle-o"></i>Asset & Inventory</a></li>
              </ul>
            </li>
            <li>
              <a href="#"><i class="fa fa-edit"></i> <span>Appraisal</span> <i class="fa fa-angle-left pull-right"></i></a>
              <ul class="treeview-menu">
                  <li><a href="<%= approot%>/employee/appraisalnew/srcappraisal.jsp"><i class="fa fa-circle-o"></i> Employee Appraisal</a></li>
              <li>
              <a href="#"><i class="fa fa-circle-o"></i> Master Appraisal <i class="fa fa-angle-left pull-right"></i></a>
              
              <ul class="treeview-menu">
                <li><a href="<%= approot%>/masterdata/grouprankHR.jsp"><i class="fa fa-circle-o"></i> Group Rank</a></li>
                <li><a href="<%= approot%>/masterdata/evaluation.jsp"><i class="fa fa-circle-o"></i> Evaluation Criteria</a></li>
                <li><a href="<%= approot%>/masterdata/assessment/assessmentFormMain.jsp"><i class="fa fa-circle-o"></i> Form Creator</a></li>

              </ul>
              </li>
              </ul>
            </li>
            
            <li>
              <a href="#"><i class="fa fa-university"></i> <span>Organization</span> <i class="fa fa-angle-left pull-right"></i></a>
              
              
              <ul class="treeview-menu">
                <li><a href="<%= approot%>/masterdata/structure_template.jsp""><i class="fa fa-circle-o"></i> Structure Template</a></li>

              </ul>
            </li>
            
            <li>
              <a href="#"><i class="fa fa-bar-chart"></i> <span>Training</span> <i class="fa fa-angle-left pull-right"></i></a>
              
              
              <ul class="treeview-menu">
                <li><a href="<%= approot%>/masterdata/contact/contact_company_list.jsp""><i class="fa fa-circle-o"></i> Training Organizer</a></li>

              </ul>
            </li>
            
            
            <li id="EmpDoc">
              <a href="#"><i class="fa fa-files-o"></i> <span>Document</span> <i class="fa fa-angle-left pull-right"></i></a>
              <ul class="treeview-menu">
                  <li id="empdoc"><a href="<%= approot%>/employee/document/emp_document.jsp"><i class="fa fa-circle-o"></i> Employee Document</a></li>
              </ul>
            </li>
            <li id="Master_data">
              <a href="#"><i class="fa fa-list"></i> <span>Master Data</span> <i class="fa fa-angle-left pull-right"></i></a>
              <ul class="treeview-menu">
                <li id="Company">
                  <a href="#"><i class="fa fa-circle-o"></i> Company <i class="fa fa-angle-left pull-right"></i></a>
                  <ul class="treeview-menu">
                    <li id="subCompany"><a href="<%= approot%>/masterdata/company.jsp"><i class="fa fa-circle-o"></i> Company</a></li>
                    <li><a href="<%= approot%>/masterdata/division_new.jsp"><i class="fa fa-circle-o"></i> Division</a></li>
                    <li><a href="<%= approot%>/masterdata/department.jsp"><i class="fa fa-circle-o"></i> Department</a></li>
                    <li><a href="<%= approot%>/masterdata/position.jsp"><i class="fa fa-circle-o"></i> Position</a></li>
                    <li><a href="<%= approot%>/masterdata/positiongroup.jsp"><i class="fa fa-circle-o"></i> Position Group</a></li>
                    <li><a href="<%= approot%>/masterdata/section.jsp"><i class="fa fa-circle-o"></i> Section</a></li>
                    <li><a href="<%= approot%>/masterdata/sub_section.jsp"><i class="fa fa-circle-o"></i> Sub Section</a></li>
                    <li><a href="<%= approot%>/masterdata/publicholiday.jsp"><i class="fa fa-circle-o"></i> Public Holiday</a></li>
                  </ul>
                </li>
                <li>
                  <a href="#"><i class="fa fa-circle-o"></i> Employee <i class="fa fa-angle-left pull-right"></i></a>
                  <ul class="treeview-menu">
                    <li><a href="<%= approot%>/masterdata/education.jsp"><i class="fa fa-circle-o"></i> Education</a></li>
                    <li><a href="<%= approot%>/masterdata/language.jsp"><i class="fa fa-circle-o"></i> Language</a></li>
                    <li><a href="<%= approot%>/masterdata/famRelation.jsp"><i class="fa fa-circle-o"></i> Family Relationship</a></li>
                    <li><a href="<%= approot%>/masterdata/empwarning.jsp"><i class="fa fa-circle-o"></i> Warning</a></li>
                    <li><a href="<%= approot%>/masterdata/empreprimand.jsp"><i class="fa fa-circle-o"></i> Reprimand</a></li>
                    <li><a href="<%= approot%>/masterdata/level.jsp"><i class="fa fa-circle-o"></i> Level</a></li>
                    <li><a href="<%= approot%>/masterdata/empcategory.jsp"><i class="fa fa-circle-o"></i> Category</a></li>
                    <li><a href="<%= approot%>/masterdata/religion.jsp"><i class="fa fa-circle-o"></i> Religion</a></li>
                    <li><a href="<%= approot%>/masterdata/race.jsp"><i class="fa fa-circle-o"></i> Race</a></li>
                    <li><a href="<%= approot%>/masterdata/image_assign.jsp"><i class="fa fa-circle-o"></i> Image Assign</a></li>
                    <li><a href="<%= approot%>/masterdata/resignedreason.jsp"><i class="fa fa-circle-o"></i> Resigned Reason</a></li>
                    <li><a href="<%= approot%>/masterdata/awardtype.jsp"><i class="fa fa-circle-o"></i> Award Type</a></li>
                    <li><a href="<%= approot%>/masterdata/reason.jsp"><i class="fa fa-circle-o"></i> Absence Reason</a></li>
                    <li><a href="<%= approot%>/masterdata/custom_field_master.jsp"><i class="fa fa-circle-o"></i> Custom Field Master</a></li>
                    <li><a href="<%= approot%>/masterdata/PayrollGroup.jsp"><i class="fa fa-circle-o"></i> Payroll Group</a></li>
                  </ul>
                </li>
                <li id="Schedule">
                  <a href="#"><i class="fa fa-circle-o"></i> Schedule <i class="fa fa-angle-left pull-right"></i></a>
                  <ul class="treeview-menu">
                    <li id="Period"><a href="<%= approot%>/masterdata/PeriodNew.jsp"><i class="fa fa-circle-o"></i> Period</a></li>
                    <li id="schCategory"><a href="<%= approot%>/masterdata/schedulecategoryNew.jsp"><i class="fa fa-circle-o"></i> Category</a></li>
                    <li id="schSymbol"><a href="<%= approot%>/masterdata/schedulesymbolnew.jsp"><i class="fa fa-circle-o"></i> Symbol</a></li>
                  </ul>
                </li>
                <li>
                  <a href="#"><i class="fa fa-circle-o"></i> Warning and Reprimand <i class="fa fa-angle-left pull-right"></i></a>
                  <ul class="treeview-menu">
                    <li><a href="<%= approot%>/masterdata/warningreprimand_bab.jsp"><i class="fa fa-circle-o"></i> Chapter</a></li>
                    <li><a href="<%= approot%>/masterdata/warningreprimand_pasal.jsp"><i class="fa fa-circle-o"></i> Article</a></li>
                    <li><a href="<%= approot%>/masterdata/warningreprimand_ayat.jsp"><i class="fa fa-circle-o"></i> Verse</a></li>
                  </ul>
                </li>
                <li>
                  <a href="#"><i class="fa fa-circle-o"></i> Company Document <i class="fa fa-angle-left pull-right"></i></a>
                  <ul class="treeview-menu">
                    <li><a href="<%= approot%>/masterdata/doc_type.jsp"><i class="fa fa-circle-o"></i> Document Type</a></li>
                    <li><a href="<%= approot%>/masterdata/doc_expenses.jsp"><i class="fa fa-circle-o"></i> Document Expenses</a></li>
                    <li><a href="<%= approot%>/masterdata/doc_master.jsp"><i class="fa fa-circle-o"></i> Document Master</a></li>
                    <li><a href="#"><i class="fa fa-circle-o"></i> Employee Document</a></li>
                  </ul>
                </li>
                <li>
                  <a href="#"><i class="fa fa-circle-o"></i> Competency <i class="fa fa-angle-left pull-right"></i></a>
                  <ul class="treeview-menu">
                    <li><a href="<%= approot%>/masterdata/competency_type.jsp"><i class="fa fa-circle-o"></i> Competency Type</a></li>
                    <li><a href="<%= approot%>/masterdata/competency_group.jsp"><i class="fa fa-circle-o"></i> Competency Group</a></li>
                    <li><a href="<%= approot%>/masterdata/competency.jsp"><i class="fa fa-circle-o"></i> Competency</a></li>
                    <li><a href="<%= approot%>/masterdata/competency_detail.jsp"><i class="fa fa-circle-o"></i> Competency Detail</a></li>
                    <li><a href="<%= approot%>/masterdata/competency_level.jsp"><i class="fa fa-circle-o"></i> Competency Level</a></li>  
                  </ul>
                </li>
                <li><a href="<%= approot%>/masterdata/relevant_doc_group.jsp"><i class="fa fa-circle-o"></i> Relevant Document Master</a></li>
                <li><a href="<%= approot%>/masterdata/machine.jsp"><i class="fa fa-circle-o"></i> Master Machine</a></li>
              </ul>
            </li>
            <li>
              <a href="#"><i class="fa fa-files-o"></i> <span>Report</span> <i class="fa fa-angle-left pull-right"></i></a>
              <ul class="treeview-menu">
                  <li><a href="<%= approot%>/report/payroll/custom_rpt_main_new.jsp"><i class="fa fa-circle-o"></i> Custom Report</a></li>
                  <li id="r_presence">
                    <a href="#"><i class="fa fa-circle-o"></i> <span>Presence</span> <i class="fa fa-angle-left pull-right"></i></a>
                    <ul class="treeview-menu">
                      <li id="DailyPresence"><a  href="<%= approot%>/report/presence/presence_report_daily_n.jsp"><i class="fa fa-circle-o"></i> Daily Presence</a></li>
                      <li id="Rekapitulasi"><a  href="<%= approot%>/report/presence/rekapitulasi_absensi_n.jsp"><i class="fa fa-circle-o"></i> Rekapitulasi Absensi</a></li>
                    </ul>
                  </li>
              </ul>
            </li>
            <li>
              <a href="#"><i class="fa fa-cog"></i> <span>System</span> <i class="fa fa-angle-left pull-right"></i></a>
              <ul class="treeview-menu">
                  <li><a href="<%= approot%>/service/notification/notification.jsp"><i class="fa fa-circle-o"></i> Notification</a></li>
                  <li><a href="<%= approot%>/system/timekeepingpro/svcmgrNew.jsp"><i class="fa fa-circle-o"></i>Time Keeping & Analyze</a></li>
                  
              </ul>
              <ul class="treeview-menu">
                  <li><a href="<%= approot%>/employee/databank/RunExe.jsp"><i class="fa fa-circle-o"></i> Run</a></li>
              </ul>
            </li>
            
        </ul>
    </section>
</aside>
