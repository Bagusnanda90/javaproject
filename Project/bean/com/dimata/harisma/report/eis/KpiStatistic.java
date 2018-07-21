/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 *  created by : Kartika, 12 Januari 2015
 */

package com.dimata.harisma.report.eis;

import com.dimata.harisma.entity.employee.SessEmpEducation;
import com.dimata.harisma.session.jfreechart.*;
import com.dimata.harisma.entity.logrpt.LogSrcReportList;
import com.dimata.harisma.entity.masterdata.EmployeeSum;
import com.dimata.qdep.form.FRMQueryString;
import com.sun.xml.tree.ParseContext;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.BasicStroke;
import java.awt.Color;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.entity.StandardEntityCollection;
import java.io.OutputStream;
import java.util.Vector;
import javax.servlet.http.HttpSession;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import com.dimata.harisma.session.employee.SessEmployee;
import com.dimata.common.jfreechart.*;
import com.dimata.qdep.db.DBHandler;
import com.dimata.qdep.db.DBResultSet;
import com.dimata.util.Formater;
import java.sql.ResultSet;
import java.util.Date;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.CategoryItemRenderer;

public class KpiStatistic extends HttpServlet {

private static final long serialVersionUID = 1L;
public final static int REPORT_TYPE_KPI_SUMMARY  = 0;
public final static String[][] REPORT_TYPE = {
                    {"Prestasi KPI"},
                    {"KPI Performance Summary"}
                };

public KpiStatistic() {
// TODO Auto-generated constructor stub
}
protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {

        //membuat valuenya
        HttpSession session = request.getSession(true);
        int reportType = FRMQueryString.requestInt(request, "type");
        int diagramType = FRMQueryString.requestInt(request, "typediagram");
        int diagramWidth = FRMQueryString.requestInt(request, "diagramWidth");
        int diagramHigh = FRMQueryString.requestInt(request, "diagramHigh");
        int language = FRMQueryString.requestInt(request, "language");
        language = language > 1 || language<0 ? 0: language;
        reportType = reportType < REPORT_TYPE_KPI_SUMMARY  || reportType > REPORT_TYPE_KPI_SUMMARY ? REPORT_TYPE_KPI_SUMMARY: reportType;
                
        LogSrcReportList srcReport = new LogSrcReportList();
          try {
                srcReport = (LogSrcReportList) session.getValue("srcReport");
         } catch (Exception e) {
                //out.println(textListTitleHeader[SESS_LANGUAGE][3]);
                srcReport = new LogSrcReportList();
            }

        DefaultPieDataset pieDataset = new DefaultPieDataset();
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        
        Vector<KpiSummary> reportData = new Vector();
        String reportTitle ="";
        switch (reportType){
            case REPORT_TYPE_KPI_SUMMARY: 
                reportData= listKPISummary(0, new Date());
                reportTitle =  REPORT_TYPE[reportType][language];
                break;
            default:
        }
       
        //create pie chart
        if(I_JFreeConstanta.DIAGRAM_TYPE_PIE==diagramType){
             createPieChart(reportData,response,pieDataset,reportTitle, diagramWidth, diagramHigh);
        }else{
             //create bar chart
            createBarChart(reportData,response,dataset,reportTitle, diagramWidth, diagramHigh);
        }
        
    }

    public void createPieChart( Vector objClass, HttpServletResponse response,DefaultPieDataset pieDataset, String nameReport, int diagramWidth,int diagramHigh)
    {
        for(int i = 0;i <objClass.size();i++){
            KpiSummary kpi = (KpiSummary) objClass.get(i);            
            pieDataset.setValue(kpi.getKpiTitle(), kpi.getAchive());       
        }   
       
        try {
                JFreeChart chart = ChartFactory.createPieChart(""+nameReport, pieDataset, true, true, false);
                chart.setBorderPaint(Color.GRAY);
                chart.setBorderStroke(new BasicStroke(10.0f));
                chart.setBorderVisible(true);
                if (chart != null) {
                    //ukuran frame nya
                    int width = diagramWidth < 1 ? 300 : diagramWidth;
                    int height = diagramHigh < 1 ? 300 : diagramHigh;
                    final ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());
                    response.setContentType("image/png");
                    OutputStream out=response.getOutputStream();
                    ChartUtilities.writeChartAsPNG(out, chart, width, height,info);
                }
        }catch(Exception e) {
            System.out.print("ada eror di "+e);
        }
        return;
    }


    public void createBarChart( Vector objClass, HttpServletResponse response, DefaultCategoryDataset dataset, String reportTitle, int diagramWidth, int diagramHigh)
    {
       final  String achived="Achived";
       final  String target="Target";
       for(int i = 0;i <objClass.size();i++){
            KpiSummary kpi = (KpiSummary) objClass.get(i);            
            dataset.addValue( (double) kpi.getAchive(), achived, kpi.getKpiTitle()+ "\n" + kpi.getValType());       
        }
       for(int i = 0;i <objClass.size();i++){
            KpiSummary kpi = (KpiSummary) objClass.get(i);            
            dataset.addValue( (double) kpi.getTarget(), target, kpi.getKpiTitle()+ "\n" + kpi.getValType());       
        }

        try {
                JFreeChart chart = ChartFactory.createBarChart(reportTitle, "KPI", "Value", dataset, PlotOrientation.HORIZONTAL, true, true, false );
                chart.setBorderPaint(Color.GRAY);
                chart.setBorderStroke(new BasicStroke(10.0f));
                chart.setBorderVisible(true);                                
                if (chart != null) {
                    CategoryPlot catplot = chart.getCategoryPlot();            
                    CategoryItemRenderer renderer = (CategoryItemRenderer) catplot.getRenderer();            
                    renderer.setBaseItemLabelsVisible(true);
                    renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator()); 
                    catplot.getRangeAxis().setUpperMargin(catplot.getRangeAxis().getUpperMargin() + 0.05);                       
                    
                    //ukuran frame nya
                    int width = diagramWidth < 1 ? 300 : diagramWidth;
                    int height = diagramHigh < 1 ? 300 : diagramHigh;
                    final ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());
                    response.setContentType("image/png");
                    OutputStream out=response.getOutputStream();
                    ChartUtilities.writeChartAsPNG(out, chart, width, height,info);
                }
        }catch(Exception e) {
            System.out.print("ada eror di "+e);
        }
        return;
    }


protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
// TODO Auto-generated method stub
}

public static Vector<KpiSummary> listKPISummary( long companyId, Date dateToCheck) {
        DBResultSet dbrs = null;
       
        Vector<KpiSummary>  vSumKPI = new Vector();
        try {
            String sql = "SELECT `KPI_LIST_ID`, `KPI_TITLE`, SUM(ACHIEV ) AS  SUM_ACHIEV, SUM(SUM_TARGET ) AS SUM_TARGET, `VALUE_TYPE`  FROM (" +
                " SELECT kpi.`KPI_LIST_ID`, kpi.`KPI_TITLE`, SUM(eac.`ACHIEVEMENT`) AS ACHIEV ,0 AS SUM_TARGET, kpi.`VALUE_TYPE` FROM hr_kpi_list kpi " +
                " LEFT JOIN `hr_kpi_employee_achiev` eac ON eac.`KPI_LIST_ID`= kpi.`KPI_LIST_ID` AND eac.`ACHIEVEMENT`>0 " +
                " WHERE  (\""+ Formater.formatDate(dateToCheck, "yyyy-MM-dd") +"\" BETWEEN kpi.`VALID_FROM` AND kpi.`VALID_TO` ) AND eac.`ENTRYDATE` BETWEEN kpi.`VALID_FROM` AND kpi.`VALID_TO` " + 
                " GROUP BY kpi.`KPI_LIST_ID` " +
                " UNION " +
                " SELECT kpi.`KPI_LIST_ID`, kpi.`KPI_TITLE`, 0  AS ACHIEV , SUM(ct.`TARGET`) AS SUM_TARGET, kpi.`VALUE_TYPE` FROM hr_kpi_list kpi  " +
                " LEFT JOIN `hr_kpi_company_target` ct ON ct.`KPI_LIST_ID` = kpi.`KPI_LIST_ID` " + (companyId!=0 ?  " AND (ct.`COMPANY_ID` =  "+ companyId +") " :"" ) +
                " WHERE (ct.`STARTDATE` BETWEEN kpi.`VALID_FROM` AND kpi.`VALID_TO` ) " + 
                " GROUP BY  kpi.`KPI_LIST_ID` ) AS SUMKPI GROUP BY `KPI_LIST_ID` " ;

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
               KpiSummary kpi = new KpiSummary();
               kpi.setCompanyId(companyId);
               kpi.setKpiID(rs.getLong("KPI_LIST_ID"));
               kpi.setKpiTitle(rs.getString("KPI_TITLE"));
               kpi.setAchive(rs.getDouble("SUM_ACHIEV"));
               kpi.setTarget(rs.getDouble("SUM_TARGET"));
               kpi.setValType(rs.getString("VALUE_TYPE"));
               vSumKPI.add(kpi);
            }

            rs.close();
            return vSumKPI;

        } catch (Exception ex) {
            return vSumKPI;
        } finally {
            DBResultSet.close(dbrs);
        }
    }

}
