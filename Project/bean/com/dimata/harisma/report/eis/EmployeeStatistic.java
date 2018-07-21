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
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardXYItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;

public class EmployeeStatistic extends HttpServlet {

private static final long serialVersionUID = 1L;
public final static int REPORT_TYPE_EDUCATION_SUMMARY  = 0;
public final static int REPORT_TYPE_AGE_SUMMARY  = 1;
public final static String[][] REPORT_TYPE = {
                    {"Rekap Pendidikan", "Rekap Umur"},
                    {"Education Summary", "Age Summary"}
                };

public EmployeeStatistic() {
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
        reportType = reportType < REPORT_TYPE_EDUCATION_SUMMARY  || reportType > REPORT_TYPE_AGE_SUMMARY ? REPORT_TYPE_EDUCATION_SUMMARY: reportType;
                
        LogSrcReportList srcReport = new LogSrcReportList();
          try {
                srcReport = (LogSrcReportList) session.getValue("srcReport");
         } catch (Exception e) {
                //out.println(textListTitleHeader[SESS_LANGUAGE][3]);
                srcReport = new LogSrcReportList();
            }

        DefaultPieDataset pieDataset = new DefaultPieDataset();
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        
        Vector reportData = new Vector();
        String reportTitle ="";
        switch (reportType){
            case REPORT_TYPE_EDUCATION_SUMMARY: 
                reportData= SessEmployee.listEducationSummary(0, null, 0);
                break;
            case REPORT_TYPE_AGE_SUMMARY: 
                reportData= SessEmployee.listAgeSummary(0, null, 0);
                break;
            default:
        }
        reportTitle =  REPORT_TYPE[language][reportType];
       
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
            EmployeeSum employeeSum = (EmployeeSum) objClass.get(i);
            pieDataset.setValue(employeeSum.getReportItemName(), employeeSum.getReportItemNumber());
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


    public void createBarChart( Vector objClass, HttpServletResponse response, DefaultCategoryDataset dataset, String nameReport, int diagramWidth, int diagramHigh)
    {
       for(int i = 0;i <objClass.size();i++){
            EmployeeSum employeeSum = (EmployeeSum) objClass.get(i);            
            dataset.addValue( (double) employeeSum.getReportItemNumber(), "", employeeSum.getReportItemName());       
        }

        try {
                JFreeChart chart = ChartFactory.createBarChart( ""+nameReport, "Range", "Number", dataset, PlotOrientation.HORIZONTAL, false, false, false );
                chart.setBorderPaint(Color.GRAY);
                chart.setBorderStroke(new BasicStroke(10.0f));                
                chart.setBorderVisible(true);
                if (chart != null) {
                    //ukuran frame nya
                    int width = diagramWidth < 1 ? 300 : diagramWidth;
                    int height = diagramHigh < 1 ? 300 : diagramHigh;
                    CategoryPlot catplot = chart.getCategoryPlot();            
                    CategoryItemRenderer renderer = (CategoryItemRenderer) catplot.getRenderer();            
                    renderer.setBaseItemLabelsVisible(true);
                    renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator()); 

                    catplot.getRangeAxis().setUpperMargin(catplot.getRangeAxis().getUpperMargin() + 0.05);                       
                    
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
}
