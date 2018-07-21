<%-- 
    Class Name   : sidebar.jsp
    Author       : Dian Putra
    Created      : Apr 17, 2016 11:11:38 PM
    Function     : Sidebar, so other file just need to call include this file.
--%>

<aside class="main-sidebar">
    <section class="sidebar">
        <ul class="sidebar-menu">
            <li class="header text-center">DIMATA HAIRISMA</li>
            <li class="active">
                <a href="<%= approot%>/home.jsp"><i class="fa fa-dashboard"></i> <span>Dashboard</span></a>
            </li>
            <li>
                <a href="<%= approot%>/employee/databank/employee_list.jsp"><i class="fa fa-users"></i> <span>Databank</span></a>
            </li>
            <li>
              <a href="#"><i class="fa fa-list"></i> <span>Master Data</span> <i class="fa fa-angle-left pull-right"></i></a>
              <ul class="treeview-menu">
                <li>
                  <a href="#"><i class="fa fa-circle-o"></i> Company <i class="fa fa-angle-left pull-right"></i></a>
                  <ul class="treeview-menu">
                    <li><a href="<%= approot%>/masterdata/company.jsp"><i class="fa fa-circle-o"></i> Company</a></li>
                    <li><a href="<%= approot%>/masterdata/division.jsp"><i class="fa fa-circle-o"></i> Division</a></li>
                    <li><a href="<%= approot%>/masterdata/department.jsp"><i class="fa fa-circle-o"></i> Department</a></li>
                    <li><a href="<%= approot%>/masterdata/position.jsp"><i class="fa fa-circle-o"></i> Position</a></li>
                    <li><a href="<%= approot%>/masterdata/positiongroup.jsp"><i class="fa fa-circle-o"></i> Position Group</a></li>
                    <li><a href="<%= approot%>/masterdata/section.jsp"><i class="fa fa-circle-o"></i> Section</a></li>
                    <li><a href="<%= approot%>/masterdata/publicholiday.jsp"><i class="fa fa-circle-o"></i> Public Holiday</a></li>
                  </ul>
                </li>
                <li>
                  <a href="#"><i class="fa fa-circle-o"></i> Employee <i class="fa fa-angle-left pull-right"></i></a>
                  <ul class="treeview-menu">
                    <li><a href="<%= approot%>/masterdata/education.jsp"><i class="fa fa-circle-o"></i> Education</a></li>
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
              </ul>
            </li>
            <li>
              <a href="#"><i class="fa fa-list"></i> <span>Appraisal</span> <i class="fa fa-angle-left pull-right"></i></a>
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
              <a href="#"><i class="fa fa-list"></i> <span>Organization</span> <i class="fa fa-angle-left pull-right"></i></a>
              
              
              <ul class="treeview-menu">
                <li><a href="<%= approot%>/masterdata/structure_template.jsp""><i class="fa fa-circle-o"></i> Structure Template</a></li>

              </ul>
            </li>
            
            <li>
              <a href="#"><i class="fa fa-list"></i> <span>Document</span> <i class="fa fa-angle-left pull-right"></i></a>
              <ul class="treeview-menu">
                  <li><a href="<%= approot%>/employee/document/emp_document.jsp"><i class="fa fa-circle-o"></i> Employee Document</a></li>
              </ul>
            </li>
            
        </ul>
    </section>
</aside>
