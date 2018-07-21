/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dimata.harisma.session.jfreechart;

import com.dimata.harisma.entity.logrpt.LogSrcReportList;
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

public class piechartdemo extends HttpServlet {

private static final long serialVersionUID = 1L;
public piechartdemo() {
// TODO Auto-generated constructor stub
}
protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {

        //membuat valuenya
        HttpSession session = request.getSession(true);
        int typeReport = FRMQueryString.requestInt(request, "type");
        int typediagram = FRMQueryString.requestInt(request, "typediagram");
        
        LogSrcReportList srcReport = new LogSrcReportList();
          try {
                srcReport = (LogSrcReportList) session.getValue("srcReport");
         } catch (Exception e) {
                //out.println(textListTitleHeader[SESS_LANGUAGE][3]);
                srcReport = new LogSrcReportList();
            }

        DefaultPieDataset pieDataset = new DefaultPieDataset();
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        
        Vector getReportPIC = new Vector();
        String nameReport ="";
        if(typeReport==1){
            getReportPIC = SessJFreeChart.getReportOnPIC(srcReport);
            nameReport= "BASE ON PIC REPORT";
        }else if(typeReport==2){
            getReportPIC = SessJFreeChart.getReportBy(srcReport);
            nameReport= "BASE ON REPORT BY";
        //BASE ON RECORDED REPORT
        }else if(typeReport==3){
            getReportPIC = SessJFreeChart.getRecordBy(srcReport);
            nameReport= "BASE ON RECORD BY";
        //BASE ON READ
        }else if(typeReport==4){
            getReportPIC = SessJFreeChart.getReadBy(srcReport);
            nameReport= "BASE ON READ";
        //BASE ON FOLLOW UP
        }else{
            getReportPIC = SessJFreeChart.getFollowUpBy(srcReport);
            nameReport= "BASE ON FOLLOW UP";
        //ALL
        }

        //create pie chart
        if(typediagram==1){
             createPieChart(getReportPIC,response,pieDataset,nameReport);
        }else{
             //create bar chart
            createBarChart(getReportPIC,response,dataset,nameReport);
        }
        
    }

    public void createPieChart( Vector objClass, HttpServletResponse response,DefaultPieDataset pieDataset, String nameReport)
    {
       for(int i = 0;i <objClass.size();i++){
            ReportFreeChart rptFreeChart = (ReportFreeChart) objClass.get(i);
            pieDataset.setValue(rptFreeChart.getFullName(), rptFreeChart.getCountReport());
        }
       
        try {
                JFreeChart chart = ChartFactory.createPieChart("REPORT - "+nameReport, pieDataset, true, true, false);
                chart.setBorderPaint(Color.black);
                chart.setBorderStroke(new BasicStroke(10.0f));
                chart.setBorderVisible(true);
                if (chart != null) {
                    //ukuran frame nya
                    int width = 1000;
                    int height = 750;
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


    public void createBarChart( Vector objClass, HttpServletResponse response, DefaultCategoryDataset dataset, String nameReport)
    {
       for(int i = 0;i <objClass.size();i++){
            ReportFreeChart rptFreeChart = (ReportFreeChart) objClass.get(i);
            //dataset.setValue(rptFreeChart.getFullName(), rptFreeChart.getCountReport());
            dataset.addValue(rptFreeChart.getCountReport(), "", rptFreeChart.getFullName() );
        }

        try {
                JFreeChart chart = ChartFactory.createBarChart( "REPORT - "+nameReport, "Employe Name", "Percentage", dataset, PlotOrientation.HORIZONTAL, false, false, false );
                chart.setBorderPaint(Color.black);
                chart.setBorderStroke(new BasicStroke(10.0f));
                chart.setBorderVisible(true);
                if (chart != null) {
                    //ukuran frame nya
                    int width = 1000;
                    int height = 750;
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
