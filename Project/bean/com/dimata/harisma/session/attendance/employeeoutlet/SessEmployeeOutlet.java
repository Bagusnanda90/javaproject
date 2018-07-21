/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.session.attendance.employeeoutlet;

import com.dimata.util.Formater;
import java.util.Date;

/**
 *
 * @author Satrya Ramayu
 */
public class SessEmployeeOutlet {

   
    private long employeeOutletId;
    private long locationId;
    private Date dtFrom;
    private Date dtTo;
    private long positionId;
    private long employeeId;
    private long schedleSymbolId;
    private long schedleSymbolId2nd;
    private String locationColor;
    private String locationColorPrev;
    private String locationName;
    private String locationNamePrev;
    private String position;
    private String positionPrev;
    private int typeSchedule;
    private String symbolSchedule;
    private String symbolSchedulePrev;
    private String timeIn;
    private String timeOut;
    private String breakOut;
    private String breakIn;
    private int prevColomStart;
    private int prevColomEnd;
    private String prevTimeIn;
    private String prevTimeOut;
    private String prevSymbolSchedule;
    private int widthSpacePemisah;
    //private Date dtTimeIn;
    //private Date dtTimeOut;
    private String positionKode;
    private String positionKodePrev;
    private String objTableOutletStart;
    private String objTableOutletEnd;
    private String linkPopUpEditOutletMapping;
    private String empNumber;
    private String fullName;
     private long positionIdFromEmp;

    /**
     * Keterangan: untuk pemberian warna di masing" kolom create by satrya
     * 2014-03-14
     *
     * @param colom
     * @param colomEnd
     * @param sessEmployeeOutlet
     * @param isDtCurrentCrossDay
     * @param isDtPrevCrossDay
     * @return
     */
    public String getObjTableOutlet(int colom, int colomEnd, SessEmployeeOutlet sessEmployeeOutlet, boolean isDtCurrentCrossDay, boolean isDtPrevCrossDay) {

        String color = sessEmployeeOutlet==null?"":sessEmployeeOutlet.getLocationColor();
        String scheduleSymbol = sessEmployeeOutlet==null?"":sessEmployeeOutlet.getSymbolSchedule();
        String positionName = sessEmployeeOutlet==null?"":sessEmployeeOutlet.getPositionKode();
        String locationName = sessEmployeeOutlet==null?"":sessEmployeeOutlet.getLocationName();
        String schTimeIn = sessEmployeeOutlet==null?"":sessEmployeeOutlet.getTimeIn();
        String schTimeOut = sessEmployeeOutlet==null?"":sessEmployeeOutlet.getTimeOut();

        String colorPrev = sessEmployeeOutlet==null?"":sessEmployeeOutlet.getLocationColorPrev();
        String scheduleSymbolPrev = sessEmployeeOutlet==null?"":sessEmployeeOutlet.getSymbolSchedulePrev();
        String positionNamePrev = sessEmployeeOutlet==null?"":sessEmployeeOutlet.getPositionKodePrev();
        String locationNamePrev = sessEmployeeOutlet==null?"":sessEmployeeOutlet.getLocationNamePrev();
        String schTimeInPrev = sessEmployeeOutlet==null?"":sessEmployeeOutlet.getPrevTimeIn();
        String schTimeOutPrev = sessEmployeeOutlet==null?"":sessEmployeeOutlet.getPrevTimeOut();
        String tableKu = "";
        String linknya = sessEmployeeOutlet.getLinkPopUpEditOutletMapping();

//        if (colom == 0) {
//            return "";
//        }
        String isiTable = positionName + "/" + scheduleSymbol;//"<p title=\""+" Location:"+locationName+"\">"+positionName +"/"+scheduleSymbol+"</p>";
        String isiTablePrev = "";//positionNamePrev + "/" + scheduleSymbolPrev;
        String toolsTips = "title=\"" + " Location:" + locationName + " ;&nbsp; Position:" + (sessEmployeeOutlet==null?"":sessEmployeeOutlet.getPosition()) + "\"";
        String toolsTipsPrev = "title=\"" + " Location:" + locationNamePrev + " ;&nbsp; Position:" + (sessEmployeeOutlet==null?"":sessEmployeeOutlet.getPositionPrev()) + "\"";
        //String isiTableAwal = "Location:"+locationName;
        double hitung = 100d / 8d;
        //int totalUseColor= Math.abs(8-(colomEnd+colom));//8 itu artinya jumlah colom maxsimal
        //String widthPrevLeft= (""+(int)Math.floor(hitung * sessEmployeeOutlet.getPrevColomEnd()))+"%";
        //String widthPrevRight = ("" + (int) Math.floor(hitung * (sessEmployeeOutlet==null?0:sessEmployeeOutlet.getPrevColomStart()))) + "%";
        //int tmpPrevRight = (int) Math.floor(hitung * (sessEmployeeOutlet==null?0:sessEmployeeOutlet.getPrevColomStart()));
        int widthSpace = (int) Math.floor(hitung * (sessEmployeeOutlet==null?0:sessEmployeeOutlet.getWidthSpacePemisah())) > 100 ? 100 : (int) Math.floor(hitung * (sessEmployeeOutlet==null?0:sessEmployeeOutlet.getWidthSpacePemisah()));
        int tmpWidthLeft = (int) Math.floor(hitung * colom);
        int tmpWidthRight = (int) Math.floor(hitung * colomEnd);
        int tmpWidthSpace = (int) Math.floor(hitung * (sessEmployeeOutlet==null?0:sessEmployeeOutlet.getWidthSpacePemisah()));
        //String widthSpaceMorePerColom = tmpWidthSpace > 100 ? (100 + "%") : ("" + (int) Math.floor(hitung * (sessEmployeeOutlet==null?0:sessEmployeeOutlet.getWidthSpacePemisah()))) + "%";

        //String widthLeft = ("" + (int) Math.floor(hitung * colom)) + "%";
        String widthCenter = "";
        int iwidthCenter = 0;
        
        if (isDtCurrentCrossDay || isDtPrevCrossDay) {
            if (isDtPrevCrossDay) {
                //jika hari sebelumnya dia cross pasti letak warnanya ada di sebelah kiri
                //widthCenter=Math.abs((int)Math.floor(hitung * colomEnd) - (100 - widthSpace))+"%";
            } else {
                //pasti letak warnanya di sebelah kanan
                //widthCenter=Math.abs((int)Math.floor(hitung * colom) - (100 - widthSpace))+"%";
            }

        } else {
            widthCenter = Math.abs((int) Math.floor(hitung * colomEnd - colom) - 100) + "%";
            //widthCenter = Math.abs((int) Math.floor(hitung * colom) + (int) Math.floor(hitung * colomEnd) - 100) + "%";
        }

        String widthRight = ("" + (int) Math.floor(hitung * colomEnd)) + "%";
        String style = "style=\"cursor:pointer;\" onMouseover=\"this.style.color='white'\" onMouseout=\"this.style.color='black'\"";
        //boolean awal=true;
        String spasiKosongColom ="";
        for(int c=0;c<colom;c++){
            spasiKosongColom=spasiKosongColom+"&nbsp;";
        }
        String spasiKosongColomEnd ="";
        for(int c=0;c<colomEnd;c++){
            spasiKosongColomEnd=spasiKosongColomEnd+"&nbsp;";
        }
        int colomPrevStart=sessEmployeeOutlet.getPrevColomStart();
        int colomPrevEnd=sessEmployeeOutlet.getPrevColomEnd();
        
        tableKu =
                "<table width=\"100%\" height=\"100%\" cellspacing=\"0\" border=\"0\">"
                + " <tr>";
        //if (colom == 0) {
        //karena jika off maka dia tidak muncull
        if(colom==0 && colomEnd==0){
            //for(int x=1;x<=8;x++){
            //if(x==1 || x==2 || x==3 || x==4 || x==5 || x==6 || x==7 || x==8){
              boolean adaPrev = false;
            if (isDtCurrentCrossDay || isDtPrevCrossDay) {
                if (isDtPrevCrossDay) {
                    //jika prev pasti colornya ke kiri
                    //tableKu = tableKu + "<th bgcolor=\"#FFFFFF\"  width=\""+widthLeft+"\">&nbsp;</th>";
                    if (colorPrev != null && colorPrev.length() > 0) {
                        
                        tableKu = tableKu + "<th " + style + toolsTipsPrev + " bgcolor=\"#" + colorPrev + "\"   onClick=\""+linknya+"\">" + "<b>" + isiTablePrev + "</b>&nbsp;" + "<sub>" + schTimeOutPrev + "<sub></th>";
                        //tableKu = tableKu + "<th " + style + toolsTipsPrev + " bgcolor=\"#" + colorPrev + "\"  width=\"" + widthPrevRight + "\" onClick=\""+linknya+"\">" + "<b>" + isiTablePrev + "</b>&nbsp;" + "<sub>" + schTimeOutPrev + "<sub></th>";
                        adaPrev = true;
                    }
                    if (color != null && color.length() > 0 && (colom > 0 || colomEnd > 0)) {
                        //widthCenter = Math.abs(((adaPrev ? tmpPrevRight + widthSpace : tmpWidthLeft) + tmpWidthRight) - 100) + "%";
                        //iwidthCenter = Math.abs(((adaPrev ? tmpPrevRight + widthSpace : tmpWidthLeft) + tmpWidthRight) - 100);
                        int spaseColomCenter =  ((adaPrev ? colomPrevEnd + sessEmployeeOutlet.getWidthSpacePemisah() : colom) + colomEnd)/3;
                         String spasiKosongColomCenter ="";
                            for(int c=0;c<spaseColomCenter;c++){
                                spasiKosongColomCenter=spasiKosongColomCenter+"&nbsp;";
                            }
                        if (adaPrev) {
                            tableKu = tableKu + "<th bgcolor=\"#FFFFFF\""+ style +"   onClick=\""+linknya+"\">"+spasiKosongColomCenter+"</th>";
                            //tableKu = tableKu + "<th bgcolor=\"#FFFFFF\""+ style +"  width=\"" + widthSpaceMorePerColom + "\" onClick=\""+linknya+"\">&nbsp;</th>";
                        }else{
                            tableKu = tableKu + "<th bgcolor=\"#FFFFFF\" "+ style +"  onClick=\""+linknya+"\">"+spasiKosongColom+"</th>";
                            //tableKu = tableKu + "<th bgcolor=\"#FFFFFF\" "+ style +" width=\"" + widthLeft + "\" onClick=\""+linknya+"\">"+spasiKosongColom+"</th>";
                        }
                        if(colom > colomEnd){
                            //jika cross maka hanya in saja yg d tampilkan
                        tableKu = tableKu + "<th " + style + toolsTips + " bgcolor=\"#" + color + "\"  onClick=\""+linknya+"\"><sup>" + schTimeIn + "</sup>" + "&nbsp;<b>" + isiTable + "</b>"+ "</th>";
                        //tableKu = tableKu + "<th " + style + toolsTips + " bgcolor=\"#" + color + "\"  width=\"" + widthCenter + "\" onClick=\""+linknya+"\"><sup>" + schTimeIn + "</sup>" + "&nbsp;<b>" + isiTable + "</b>"+ "</th>";
                        }else{
                            if(schTimeIn!=null && schTimeIn.length()>0 && schTimeOut!=null && schTimeOut.length()>0 && schTimeOut.equalsIgnoreCase(schTimeIn)){
                                tableKu = tableKu + "<th " + style + toolsTips + " bgcolor=\"#" + color + "\"   onClick=\""+linknya+"\">" + "&nbsp;<b>" + isiTable + "</b>&nbsp;" + "</th>";
                            }else{
                                tableKu = tableKu + "<th " + style + toolsTips + " bgcolor=\"#" + color + "\"   onClick=\""+linknya+"\"><sup>" + schTimeIn + "</sup>" + "&nbsp;<b>" + isiTable + "</b>&nbsp;" + "<sub>" + schTimeOut + "<sub></th>";
                            }
                            
                            //tableKu = tableKu + "<th " + style + toolsTips + " bgcolor=\"#" + color + "\"  width=\"" + widthCenter + "\" onClick=\""+linknya+"\"><sup>" + schTimeIn + "</sup>" + "&nbsp;<b>" + isiTable + "</b>&nbsp;" + "<sub>" + schTimeOut + "<sub></th>";
                        }
                        if (tmpWidthRight < 100 && iwidthCenter < 100 && colom<8) {
                            tableKu = tableKu + "<th " + style +" bgcolor=\"#FFFFFF\"   onClick=\""+linknya+"\">"+spasiKosongColomEnd+"</th>";
                            //tableKu = tableKu + "<th " + style +" bgcolor=\"#FFFFFF\"  width=\"" + widthRight + "\" onClick=\""+linknya+"\">"+spasiKosongColomEnd+"</th>";
                        }
                    }

                } else {
                    //jika current pasti di kanan
                                                /*if(colom>0 || colomEnd>0){
                     tableKu = tableKu + "<th bgcolor=\"#FFFFFF\"  width=\""+widthSpaceMorePerColom+"\">&nbsp;</th>";                                      
                     //tableKu = tableKu + "<th bgcolor=\"#FFFFFF\"  width=\""+widthLeft+"\">&nbsp;</th>";                                      
                     tableKu = tableKu + "<th " +toolsTips+ " bgcolor=\"#"+color+"\"  width=\""+widthCenter+"\"><sup>"+schTimeIn+"</sup>"+"&nbsp;<b>"+isiTable+"</b>&nbsp;"+"<sub>"+schTimeOut+"<sub></th>";
                     //tableKu = tableKu + "<th bgcolor=\"#FFFFFF\"  width=\""+widthRight+"\">&nbsp;</th>";       
                     tableKu = tableKu + "<th bgcolor=\"#FFFFFF\"  width=\""+widthSpaceMorePerColom+"\">&nbsp;</th>"; 
                     }*/
                    if (color != null && color.length() > 0) {
                        widthCenter = Math.abs((tmpWidthLeft) - 100) + "%";
                        tableKu = tableKu + "<th bgcolor=\"#FFFFFF\"  onClick=\""+linknya+"\">"+spasiKosongColom+"</th>";
                        //tableKu = tableKu + "<th bgcolor=\"#FFFFFF\"  width=\"" + widthLeft + "\" onClick=\""+linknya+"\">"+spasiKosongColom+"</th>";
                        tableKu = tableKu + "<th " + style + toolsTips + " bgcolor=\"#" + color + "\"   onClick=\""+linknya+"\"><sup>" + schTimeIn + "</sup>" + "&nbsp;<b>" + isiTable + "</b>&nbsp;" + "</th>";
                        //tableKu = tableKu + "<th " + style + toolsTips + " bgcolor=\"#" + color + "\"  width=\"" + widthCenter + "\" onClick=\""+linknya+"\"><sup>" + schTimeIn + "</sup>" + "&nbsp;<b>" + isiTable + "</b>&nbsp;" + "</th>";
                    }

                }


            }
            tableKu = tableKu + "<th " + style + " bgcolor=\"#FFFFFF\" onClick=\""+linknya+"\">&nbsp;</th>";
            //}
            //}
        } else {
            boolean adaPrev = false;
            if (isDtCurrentCrossDay || isDtPrevCrossDay) {
                if (isDtPrevCrossDay) {
                    //jika prev pasti colornya ke kiri
                    //tableKu = tableKu + "<th bgcolor=\"#FFFFFF\"  width=\""+widthLeft+"\">&nbsp;</th>";
                    if (colorPrev != null && colorPrev.length() > 0) {
                        tableKu = tableKu + "<th " + style + toolsTipsPrev + " bgcolor=\"#" + colorPrev + "\"    onClick=\""+linknya+"\">" + "<b>" + isiTablePrev + "</b>&nbsp;" + "<sub>" + schTimeOutPrev + "<sub></th>";
                        //tableKu = tableKu + "<th " + style + toolsTipsPrev + " bgcolor=\"#" + colorPrev + "\"  width=\"" + widthPrevRight + "\"  onClick=\""+linknya+"\">" + "<b>" + isiTablePrev + "</b>&nbsp;" + "<sub>" + schTimeOutPrev + "<sub></th>";
                        adaPrev = true;
                    }
                    if (color != null && color.length() > 0 && (colom > 0 || colomEnd > 0)) {
                        //widthCenter = Math.abs(((adaPrev ? tmpPrevRight + widthSpace : tmpWidthLeft) + tmpWidthRight) - 100) + "%";
                        //iwidthCenter = Math.abs(((adaPrev ? tmpPrevRight + widthSpace : tmpWidthLeft) + tmpWidthRight) - 100);
                  
                        int spaseColomCenter =  ((adaPrev ? colomPrevEnd + sessEmployeeOutlet.getWidthSpacePemisah()+colom : colom));
                         String spasiKosongColomCenter ="";
                            for(int c=0;c<spaseColomCenter;c++){
                                spasiKosongColomCenter=spasiKosongColomCenter+"&nbsp;";
                            }
                        if (adaPrev) {
                             if(schTimeIn!=null && schTimeIn.length()>0 && schTimeOutPrev!=null && schTimeOutPrev.length()>0 && schTimeIn.equalsIgnoreCase(schTimeOutPrev)){
                            
                             }else{
                                 tableKu = tableKu + "<th " + style + " bgcolor=\"#FFFFFF\"   onClick=\""+linknya+"\">"+spasiKosongColomCenter+"</th>";
                            //tableKu = tableKu + "<th " + style + " bgcolor=\"#FFFFFF\"  width=\"" + widthSpaceMorePerColom + "\" onClick=\""+linknya+"\">&nbsp;</th>";
                             }
                        }else{
                             if(schTimeIn!=null && schTimeIn.length()>0 && schTimeOutPrev!=null && schTimeOutPrev.length()>0 && schTimeIn.equalsIgnoreCase(schTimeOutPrev)){
                            
                             }else{
                                 tableKu = tableKu + "<th " + style + " bgcolor=\"#FFFFFF\"   onClick=\""+linknya+"\">"+spasiKosongColom+"</th>";
                            //tableKu = tableKu + "<th " + style + " bgcolor=\"#FFFFFF\"  width=\"" + widthLeft + "\" onClick=\""+linknya+"\">"+spasiKosongColom+"</th>";
                             }
                        }
                   
                        if(colom > colomEnd){
                            //jika cross maka hanya in saja yg d tampilkan
                        tableKu = tableKu + "<th " + style + toolsTips + " bgcolor=\"#" + color + "\"  onClick=\""+linknya+"\"><sup>" + schTimeIn + "</sup>" + "&nbsp;<b>" + isiTable + "</b>"+ "</th>";
                        //tableKu = tableKu + "<th " + style + toolsTips + " bgcolor=\"#" + color + "\"  width=\"" + widthCenter + "\" onClick=\""+linknya+"\"><sup>" + schTimeIn + "</sup>" + "&nbsp;<b>" + isiTable + "</b>"+ "</th>";
                        }else{
                            if(schTimeIn!=null && schTimeIn.length()>0 && schTimeOut!=null && schTimeOut.length()>0 && schTimeOut.equalsIgnoreCase(schTimeIn)){
                                tableKu = tableKu + "<th " + style + toolsTips + " bgcolor=\"#" + color + "\"   onClick=\""+linknya+"\">" + "&nbsp;<b>" + isiTable + "</b>&nbsp;" + "</th>";
                            }else{
                                tableKu = tableKu + "<th " + style + toolsTips + " bgcolor=\"#" + color + "\"   onClick=\""+linknya+"\"><sup>" + schTimeIn + "</sup>" + "&nbsp;<b>" + isiTable + "</b>&nbsp;" + "<sub>" + schTimeOut + "<sub></th>";
                            }
                            
                            //tableKu = tableKu + "<th " + style + toolsTips + " bgcolor=\"#" + color + "\"  width=\"" + widthCenter + "\" onClick=\""+linknya+"\"><sup>" + schTimeIn + "</sup>" + "&nbsp;<b>" + isiTable + "</b>&nbsp;" + "<sub>" + schTimeOut + "<sub></th>";
                        }
                        if (tmpWidthRight < 100 && iwidthCenter < 100 && colom<8) {
                            tableKu = tableKu + "<th " + style + " bgcolor=\"#FFFFFF\"   onClick=\""+linknya+"\">"+spasiKosongColomEnd+"</th>";
                            //tableKu = tableKu + "<th " + style + " bgcolor=\"#FFFFFF\"  width=\"" + widthRight + "\" onClick=\""+linknya+"\">"+spasiKosongColomEnd+"</th>";
                        }
                    }

                } else {
                    //jika current pasti di kanan
                                                /*if(colom>0 || colomEnd>0){
                     tableKu = tableKu + "<th bgcolor=\"#FFFFFF\"  width=\""+widthSpaceMorePerColom+"\">&nbsp;</th>";                                      
                     //tableKu = tableKu + "<th bgcolor=\"#FFFFFF\"  width=\""+widthLeft+"\">&nbsp;</th>";                                      
                     tableKu = tableKu + "<th " +toolsTips+ " bgcolor=\"#"+color+"\"  width=\""+widthCenter+"\"><sup>"+schTimeIn+"</sup>"+"&nbsp;<b>"+isiTable+"</b>&nbsp;"+"<sub>"+schTimeOut+"<sub></th>";
                     //tableKu = tableKu + "<th bgcolor=\"#FFFFFF\"  width=\""+widthRight+"\">&nbsp;</th>";       
                     tableKu = tableKu + "<th bgcolor=\"#FFFFFF\"  width=\""+widthSpaceMorePerColom+"\">&nbsp;</th>"; 
                     }*/
                    if (color != null && color.length() > 0) {
                        //widthCenter = Math.abs((tmpWidthLeft) - 100) + "%";
                        //tableKu = tableKu + "<th " + style + " bgcolor=\"#FFFFFF\"  width=\"" + widthLeft + "\" onClick=\""+linknya+"\">"+spasiKosongColom+"</th>";
                        //tableKu = tableKu + "<th " + style + toolsTips + " bgcolor=\"#" + color + "\"  width=\"" + widthCenter + "\" onClick=\""+linknya+"\"><sup>" + schTimeIn + "</sup>" + "&nbsp;<b>" + isiTable + "</b>&nbsp;" + "</th>";
                        tableKu = tableKu + "<th " + style + " bgcolor=\"#FFFFFF\"   onClick=\""+linknya+"\">"+spasiKosongColom+"</th>";
                        tableKu = tableKu + "<th " + style + toolsTips + " bgcolor=\"#" + color + "\"  onClick=\""+linknya+"\"><sup>" + schTimeIn + "</sup>" + "&nbsp;<b>" + isiTable + "</b>&nbsp;" + "</th>";
                    }

                }


            } else {
                if (color != null && color.length() > 0) {
                    //jika off otomatis full dan tmpWidthLeft==0 maka harusnya dia full
                    if (tmpWidthLeft > 0) {
                        tableKu = tableKu + "<th  " + style + " bgcolor=\"#FFFFFF\"   onClick=\""+linknya+"\">"+spasiKosongColom+"</th>";
                        //tableKu = tableKu + "<th  " + style + " bgcolor=\"#FFFFFF\"  width=\"" + widthLeft + "\" onClick=\""+linknya+"\">"+spasiKosongColom+"</th>";
                    }
                    if(schTimeIn!=null && schTimeIn.length()>0 && schTimeOut!=null && schTimeOut.length()>0 && schTimeOut.equalsIgnoreCase(schTimeIn)){
                        tableKu = tableKu + "<th " + style + toolsTips + " bgcolor=\"#" + color + "\"   onClick=\""+linknya+"\">&nbsp;<b>" + isiTable + "</b>&nbsp;" + "</th>";
                    }else{
                        if(schTimeIn!=null && schTimeIn.length()>0 && schTimeOut!=null && schTimeOut.length()>0 && schTimeOut.equalsIgnoreCase(schTimeIn)){
                            tableKu = tableKu + "<th " + style + toolsTips + " bgcolor=\"#" + color + "\"   onClick=\""+linknya+"\">" + "&nbsp;<b>" + isiTable + "</b>&nbsp;" + "</th>";
                        }else{
                            tableKu = tableKu + "<th " + style + toolsTips + " bgcolor=\"#" + color + "\"   onClick=\""+linknya+"\"><sup>" + schTimeIn + "</sup>" + "&nbsp;<b>" + isiTable + "</b>&nbsp;" + "<sub>" + schTimeOut + "<sub></th>";
                        }
                        
                    }
                    
                    //tableKu = tableKu + "<th " + style + toolsTips + " bgcolor=\"#" + color + "\"  width=\"" + widthCenter + "\" onClick=\""+linknya+"\"><sup>" + schTimeIn + "</sup>" + "&nbsp;<b>" + isiTable + "</b>&nbsp;" + "<sub>" + schTimeOut + "<sub></th>";
                    if (tmpWidthRight < 100 && tmpWidthLeft < 100 && tmpWidthRight>0) {
                        tableKu = tableKu + "<th  " + style + " bgcolor=\"#FFFFFF\"   onClick=\""+linknya+"\">"+spasiKosongColomEnd+"</th>";
                        //tableKu = tableKu + "<th  " + style + " bgcolor=\"#FFFFFF\"  width=\"" + widthRight + "\" onClick=\""+linknya+"\">"+spasiKosongColomEnd+"</th>";
                    }
                }
            }
        }
        tableKu = tableKu + "</tr>" + "</table>";
        objTableOutletStart = tableKu; 
        return objTableOutletStart;
    }

    
    /**
     *
     * @param colom
     * @param colomEnd
     * @param sessEmployeeOutlet
     * @param isDtCurrentCrossDay
     * @param isDtPrevCrossDay
     * @return 
     * Author : Hendra McHen 
     * Method : getObjTableOutletStyleWithoutTime
     * Date : 04 November 2014
     */
    public String getObjTableOutletStyleWithoutTime(int colom, int colomEnd, SessEmployeeOutlet sessEmployeeOutlet, boolean isDtCurrentCrossDay, boolean isDtPrevCrossDay) {

        String color = sessEmployeeOutlet == null ? "" : sessEmployeeOutlet.getLocationColor();
        String scheduleSymbol = sessEmployeeOutlet == null ? "" : sessEmployeeOutlet.getSymbolSchedule();
        String locationName = sessEmployeeOutlet == null ? "" : sessEmployeeOutlet.getLocationName();
        String schTimeIn = sessEmployeeOutlet == null ? "" : sessEmployeeOutlet.getTimeIn();
        String schTimeOut = sessEmployeeOutlet == null ? "" : sessEmployeeOutlet.getTimeOut();
        String colorPrev = sessEmployeeOutlet == null ? "" : sessEmployeeOutlet.getLocationColorPrev();
        String locationNamePrev = sessEmployeeOutlet == null ? "" : sessEmployeeOutlet.getLocationNamePrev();
        String schTimeOutPrev = sessEmployeeOutlet == null ? "" : sessEmployeeOutlet.getPrevTimeOut();
        String tableKu = "";
        String linknya = sessEmployeeOutlet.getLinkPopUpEditOutletMapping();

        String isiTable = scheduleSymbol;
        String toolsTips = "title=\"" + "Time in: " + schTimeIn + " | Time out: " + schTimeOut + " | Location: " + locationName + " | Position: " + (sessEmployeeOutlet == null ? "" : sessEmployeeOutlet.getPosition()) + "\"";

        double hitung = 100d / 8d;
        int tmpWidthLeft = (int) Math.floor(hitung * colom);
        int tmpWidthRight = (int) Math.floor(hitung * colomEnd);

        String widthCenter = "";
        int iwidthCenter = 0;

        if (isDtCurrentCrossDay || isDtPrevCrossDay) {
            // nothing
        } else {
            widthCenter = Math.abs((int) Math.floor(hitung * colomEnd - colom) - 100) + "%";
        }

        String widthRight = ("" + (int) Math.floor(hitung * colomEnd)) + "%";
        String style = "style=\"cursor:pointer;\" onMouseover=\"this.style.color='white'\" onMouseout=\"this.style.color='black'\"";
        //boolean awal=true;
        String spasiKosongColom = "";
        for (int c = 0; c < colom; c++) {
            spasiKosongColom = spasiKosongColom + "";
        }
        String spasiKosongColomEnd = "";
        for (int c = 0; c < colomEnd; c++) {
            spasiKosongColomEnd = spasiKosongColomEnd + "";
        }
        int colomPrevStart = sessEmployeeOutlet.getPrevColomStart();
        int colomPrevEnd = sessEmployeeOutlet.getPrevColomEnd();

        tableKu = "<table width=\"100%\" height=\"100%\" cellspacing=\"0\" border=\"0\">"
                + "<tr>";
        //if (colom == 0) {
        //karena jika off maka dia tidak muncull
        if (colom == 0 && colomEnd == 0) {
            boolean adaPrev = false;
            if (isDtCurrentCrossDay || isDtPrevCrossDay) {
                if (isDtPrevCrossDay) {
                    //jika prev pasti colornya ke kiri
                    if (colorPrev != null && colorPrev.length() > 0) {
                        adaPrev = true;
                    }
                    if (color != null && color.length() > 0 && (colom > 0 || colomEnd > 0)) {
                        int spaseColomCenter = ((adaPrev ? colomPrevEnd + sessEmployeeOutlet.getWidthSpacePemisah() : colom) + colomEnd) / 3;
                        String spasiKosongColomCenter = "";
                        for (int c = 0; c < spaseColomCenter; c++) {
                            spasiKosongColomCenter = spasiKosongColomCenter + "";
                        }
                        if (adaPrev) {
                            //tableKu = tableKu + "<th bgcolor=\"#FFFFFF\"" + style + "   onClick=\"" + linknya + "\">" + spasiKosongColomCenter + "</th>";
                        } else {
                            //tableKu = tableKu + "<th bgcolor=\"#FFFFFF\" " + style + "  onClick=\"" + linknya + "\">" + spasiKosongColom + "</th>";
                        }
                        if (colom > colomEnd) {
                            //jika cross maka hanya in saja yg d tampilkan
                            tableKu = tableKu + "<th " + style + toolsTips + " bgcolor=\"#" + color + "\"  onClick=\"" + linknya + "\"><b>" + isiTable + "</b>" + "</th>";
                        } else {
                            if (schTimeIn != null && schTimeIn.length() > 0 && schTimeOut != null && schTimeOut.length() > 0 && schTimeOut.equalsIgnoreCase(schTimeIn)) {
                                tableKu = tableKu + "<th " + style + toolsTips + " bgcolor=\"#" + color + "\"   onClick=\"" + linknya + "\"><b>" + isiTable + "</b></th>";
                            } else {
                                tableKu = tableKu + "<th " + style + toolsTips + " bgcolor=\"#" + color + "\"   onClick=\"" + linknya + "\"><b>" + isiTable + "</b></th>";
                            }
                        }
                        if (tmpWidthRight < 100 && iwidthCenter < 100 && colom < 8) {
                            //tableKu = tableKu + "<th " + style + " bgcolor=\"#FFFFFF\"   onClick=\"" + linknya + "\">" + spasiKosongColomEnd + "</th>";
                        }
                    }

                } else {
                    //jika current pasti di kanan
                    if (color != null && color.length() > 0) {
                        widthCenter = Math.abs((tmpWidthLeft) - 100) + "%";
                        //tableKu = tableKu + "<th bgcolor=\"#FFFFFF\"  onClick=\"" + linknya + "\">" + spasiKosongColom + "</th>";
                        tableKu = tableKu + "<th " + style + toolsTips + " bgcolor=\"#" + color + "\"   onClick=\"" + linknya + "\"><b>" + isiTable + "</b></th>";
                    }

                }


            }
            tableKu = tableKu + "<th " + style + " bgcolor=\"#FFFFFF\" onClick=\"" + linknya + "\">&nbsp;</th>";
        } else {
            boolean adaPrev = false;
            if (isDtCurrentCrossDay || isDtPrevCrossDay) {
                if (isDtPrevCrossDay) {
                    //jika prev pasti colornya ke kiri
                    if (colorPrev != null && colorPrev.length() > 0) {
                        adaPrev = true;
                    }
                    if (color != null && color.length() > 0 && (colom > 0 || colomEnd > 0)) {
                        int spaseColomCenter = ((adaPrev ? colomPrevEnd + sessEmployeeOutlet.getWidthSpacePemisah() + colom : colom));
                        String spasiKosongColomCenter = "";
                        for (int c = 0; c < spaseColomCenter; c++) {
                            spasiKosongColomCenter = spasiKosongColomCenter + "";
                        }
                        if (adaPrev) {
                            if (schTimeIn != null && schTimeIn.length() > 0 && schTimeOutPrev != null && schTimeOutPrev.length() > 0 && schTimeIn.equalsIgnoreCase(schTimeOutPrev)) {
                            } else {
                                //tableKu = tableKu + "<th " + style + " bgcolor=\"#FFFFFF\"   onClick=\"" + linknya + "\">" + spasiKosongColomCenter + "</th>";
                            }
                        } else {
                            if (schTimeIn != null && schTimeIn.length() > 0 && schTimeOutPrev != null && schTimeOutPrev.length() > 0 && schTimeIn.equalsIgnoreCase(schTimeOutPrev)) {
                            } else {
                                //tableKu = tableKu + "<th " + style + " bgcolor=\"#FFFFFF\"   onClick=\"" + linknya + "\">" + spasiKosongColom + "</th>";
                            }
                        }

                        if (colom > colomEnd) {
                            //jika cross maka hanya in saja yg d tampilkan
                            tableKu = tableKu + "<th " + style + toolsTips + " bgcolor=\"#" + color + "\"  onClick=\"" + linknya + "\"><b>" + isiTable + "</b>" + "</th>";
                        } else {
                            if (schTimeIn != null && schTimeIn.length() > 0 && schTimeOut != null && schTimeOut.length() > 0 && schTimeOut.equalsIgnoreCase(schTimeIn)) {
                                tableKu = tableKu + "<th " + style + toolsTips + " bgcolor=\"#" + color + "\"   onClick=\"" + linknya + "\"><b>" + isiTable + "</b></th>";
                            } else {
                                tableKu = tableKu + "<th " + style + toolsTips + " bgcolor=\"#" + color + "\"   onClick=\"" + linknya + "\"><b>" + isiTable + "</b></th>";
                            }

                        }
                        if (tmpWidthRight < 100 && iwidthCenter < 100 && colom < 8) {
                            //tableKu = tableKu + "<th " + style + " bgcolor=\"#FFFFFF\"   onClick=\"" + linknya + "\">" + spasiKosongColomEnd + "</th>";
                        }
                    }

                } else {
                    //jika current pasti di kanan
                    if (color != null && color.length() > 0) {
                        //tableKu = tableKu + "<th " + style + " bgcolor=\"#FFFFFF\"   onClick=\"" + linknya + "\">" + spasiKosongColom + "</th>";
                        tableKu = tableKu + "<th " + style + toolsTips + " bgcolor=\"#" + color + "\"  onClick=\"" + linknya + "\"><b>" + isiTable + "</b></th>";
                    }

                }


            } else {
                if (color != null && color.length() > 0) {
                    //jika off otomatis full dan tmpWidthLeft==0 maka harusnya dia full
                    if (tmpWidthLeft > 0) {
                        //tableKu = tableKu + "<th  " + style + " bgcolor=\"#FFFFFF\"   onClick=\"" + linknya + "\">" + spasiKosongColom + "</th>";
                    }
                    if (schTimeIn != null && schTimeIn.length() > 0 && schTimeOut != null && schTimeOut.length() > 0 && schTimeOut.equalsIgnoreCase(schTimeIn)) {
                        tableKu = tableKu + "<th " + style + toolsTips + " bgcolor=\"#" + color + "\"   onClick=\"" + linknya + "\"><b>" + isiTable + "</b>" + "</th>";
                    } else {
                        if (schTimeIn != null && schTimeIn.length() > 0 && schTimeOut != null && schTimeOut.length() > 0 && schTimeOut.equalsIgnoreCase(schTimeIn)) {
                            tableKu = tableKu + "<th " + style + toolsTips + " bgcolor=\"#" + color + "\"   onClick=\"" + linknya + "\"><b>" + isiTable + "</b></th>";
                        } else {
                            tableKu = tableKu + "<th " + style + toolsTips + " bgcolor=\"#" + color + "\"   onClick=\"" + linknya + "\"><b>" + isiTable + "</b></th>";
                        }

                    }

                    if (tmpWidthRight < 100 && tmpWidthLeft < 100 && tmpWidthRight > 0) {
                        //tableKu = tableKu + "<th  " + style + " bgcolor=\"#FFFFFF\"   onClick=\"" + linknya + "\">" + spasiKosongColomEnd + "</th>";
                    }
                }
            }
        }
        tableKu = tableKu + "</tr>" + "</table>";
        objTableOutletStart = tableKu;
        return objTableOutletStart;
    }
    /*public String getObjTableOutletEnd(int colom,SessEmployeeOutlet sessEmployeeOutlet) {
     String tableKu="";
     String color=sessEmployeeOutlet.getLocationColor();
     String scheduleSymbol= sessEmployeeOutlet.getSymbolSchedule();
     String positionName=sessEmployeeOutlet.getPositionKode();
     String locationName=sessEmployeeOutlet.getLocationName();
     if(colom==0){
     return "";
     }
     String isiTable = positionName +"/"+scheduleSymbol;//"<p title=\""+" Location:"+locationName+"\">"+positionName +"/"+scheduleSymbol+"</p>";
     String toolsTips = "title=\""+" Location:"+locationName+"\"";
     double hitung = 100d/8d;
     String width= (""+(int)Math.floor(hitung * colom))+"%";
     String widthEnd= (""+(Math.abs((int)Math.floor(hitung * colom)-100)))+"%";
     //boolean awal=true;
     tableKu =
     "<table width=\"100%\" cellspacing=\"0\">"
     + " <tr>" ;
     if(colom==0){
     //for(int x=1;x<=8;x++){
     //if(x==1 || x==2 || x==3 || x==4 || x==5 || x==6 || x==7 || x==8){
     tableKu = tableKu + "<th bgcolor=\"#FFFFFF\">&nbsp;</th>";
     //}
     //}
     }
     else if(colom==1){
     tableKu = tableKu + "<th " +toolsTips+ " width=\""+widthEnd+"\" bgcolor=\"#"+color+"\">"+isiTable+"</th>";
     //                                    for(int x=1;x<=8;x++){
     //                                        if((x==2 || x==3 || x==4 || x==5 || x==6 || x==7 || x==8)&&awal){
     //                                             awal=false;
     tableKu = tableKu + "<th bgcolor=\"#FFFFFF\" width=\""+width+"\" colspan=\"7\">&nbsp;</th>";
     //                                        }else{
                                           
                                            
     //                                        }
     //                                    }
     }else if(colom==2){
     tableKu = tableKu + "<th " +toolsTips+ " bgcolor=\"#"+color+"\" colspan=\""+colom+"\"  width=\""+widthEnd+"\">"+isiTable+"</th>";
     //tableKu = tableKu + "<th bgcolor=\"#FFFFFF\"></th>";
     //                                    for(int x=1;x<=8;x++){
     //                                        if((x==3 || x==4 || x==5 || x==6 || x==7 || x==8)&&awal){
     //                                             awal=false;
     tableKu = tableKu + "<th bgcolor=\"#FFFFFF\" colspan=\"6\" width=\""+width+"\">&nbsp;</th>";
     //                                        }else{
                                            
     //                                           x= x+(colom-1);
     //                                        }
     //                                    }
     //                                    
     }else if(colom==3){
     tableKu = tableKu + "<th " +toolsTips+ " bgcolor=\"#"+color+"\" colspan=\""+colom+"\"  width=\""+widthEnd+"\">"+isiTable+"</th>";
     //                                    for(int x=1;x<=8;x++){
     //                                        if((x==4 || x==5 || x==6 || x==7 || x==8)&&awal){
     //                                             awal=false;
     tableKu = tableKu + "<th bgcolor=\"#FFFFFF\"  colspan=\"5\" width=\""+width+"\">&nbsp;</th>";
     //                                        }else{
                                            
     //                                             x= x+(colom-1);
     //                                        }
     //                                    }
     }else if(colom==4){
     tableKu = tableKu + "<th " +toolsTips+ " bgcolor=\"#"+color+"\" colspan=\""+colom+"\"  width=\""+widthEnd+"\">"+isiTable+"</th>";
     //                                    for(int x=1;x<=8;x++){
     //                                        if( (x==5 || x==6 || x==7 || x==8)&&awal ){
     //                                             awal=false;
     tableKu = tableKu + "<th bgcolor=\"#FFFFFF\"  colspan=\"4\" width=\""+width+"\">&nbsp;</th>";
     //                                        }else{
                                              
     //                                             x= x+(colom-1);
     //                                        
     //                                        }
     //                                    }
     }else if(colom==5){
     tableKu = tableKu + "<th " +toolsTips+ " bgcolor=\"#"+color+"\" colspan=\""+colom+"\"  width=\""+widthEnd+"\">"+isiTable+"</th>";
     //                                     for(int x=1;x<=8;x++){
     //                                        if( (x==6 || x==7 || x==8)&&awal){
     //                                             awal=false;
     tableKu = tableKu + "<th bgcolor=\"#FFFFFF\"  colspan=\"3\" width=\""+width+"\">&nbsp;</th>";
     //                                        }else{
                                             
     //                                             x= x+(colom-1);
     //                                        
     //                                        }
     //                                    }
     }else if(colom==6){
     tableKu = tableKu + "<th " +toolsTips+ " bgcolor=\"#"+color+"\" colspan=\""+colom+"\"  width=\""+widthEnd+"\">"+isiTable+"</th>";
     //                                     for(int x=1;x<=8;x++){
     //                                        if((x==7 || x==8)&&awal){
     //                                            awal=false;
     tableKu = tableKu + "<th bgcolor=\"#FFFFFF\"  colspan=\"2\" width=\""+width+"\">&nbsp;</th>";
     //                                        }else{
                                             
     //                                             x= x+(colom-1);
                                        
     //                                        }
     //                                    }
     }else if(colom==7){
     for(int x=1;x<=8;x++){
     if(x==8){
     tableKu = tableKu + "<th bgcolor=\"#FFFFFF\" width=\""+width+"\">&nbsp;</th>";
     }else{
     tableKu = tableKu + "<th " +toolsTips+ " bgcolor=\"#"+color+"\" colspan=\""+colom+"\"  width=\""+widthEnd+"\">"+isiTable+"</th>";
     x= x+(colom-1);
     }
     }
     }else if(colom==8){
     //for(int x=1;x<=8;x++){
     tableKu = tableKu +"<th " +toolsTips+ " bgcolor=\"#"+color+"\" width=\""+widthEnd+"\">"+isiTable+"</th>";
     //}
     }
     tableKu = tableKu + "</tr>" + "</table>";
     objTableOutletEnd = tableKu; 
     return objTableOutletEnd;
     }*/
    /**
     * @return the locationId
     */
    public long getLocationId() {
        return locationId;
    }

    /**
     * @param locationId the locationId to set
     */
    public void setLocationId(long locationId) {
        this.locationId = locationId;
    }

    /**
     * @return the dtFrom
     */
    public Date getDtFrom() {
        return dtFrom;
    }

    /**
     * @param dtFrom the dtFrom to set
     */
    public void setDtFrom(Date dtFrom) {
        this.dtFrom = dtFrom;
    }

    /**
     * @return the dtTo
     */
    public Date getDtTo() {
        return dtTo;
    }

    /**
     * @param dtTo the dtTo to set
     */
    public void setDtTo(Date dtTo) {
        this.dtTo = dtTo;
    }

    /**
     * @return the positionId
     */
    public long getPositionId() {
        return positionId;
    }

    /**
     * @param positionId the positionId to set
     */
    public void setPositionId(long positionId) {
        this.positionId = positionId;
    }

    /**
     * @return the employeeId
     */
    public long getEmployeeId() {
        return employeeId;
    }

    /**
     * @param employeeId the employeeId to set
     */
    public void setEmployeeId(long employeeId) {
        this.employeeId = employeeId;
    }

    /**
     * @return the schedleSymbolId
     */
    public long getSchedleSymbolId() {
        return schedleSymbolId;
    }

    /**
     * @param schedleSymbolId the schedleSymbolId to set
     */
    public void setSchedleSymbolId(long schedleSymbolId) {
        this.schedleSymbolId = schedleSymbolId;
    }

    /**
     * @return the locationColor
     */
    public String getLocationColor() {
        if(locationColor==null || locationColor.length()==0){
            return "";
        }
        return locationColor;
    }

    /**
     * @param locationColor the locationColor to set
     */
    public void setLocationColor(String locationColor) {
        this.locationColor = locationColor;
    }

    /**
     * @return the locationName
     */
    public String getLocationName() {
        if(locationName==null || locationName.length()==0){
            return "";
        }
        return locationName;
    }

    /**
     * @param locationName the locationName to set
     */
    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    /**
     * @return the position
     */
    public String getPosition() {
        if(position==null || position.length()==0){
            return "";
        }
        return position;
    }

    /**
     * @param position the position to set
     */
    public void setPosition(String position) {
        this.position = position;
    }

//    /**
//     * @return the symbolSchedule
//     */
//    public String getSymbolSchedule() {
//        return symbolSchedule;
//    }
//
//    /**
//     * @param symbolSchedule the symbolSchedule to set
//     */
//    public void setSymbolSchedule(String symbolSchedule) {
//        this.symbolSchedule = symbolSchedule;
//    }
//
//    /**
//     * @return the timeIn
//     */
//    public String getTimeIn() {
//        return timeIn;
//    }
//
//    /**
//     * @param timeIn the timeIn to set
//     */
//    public void setTimeIn(Date dtTimeIn) {
//        if(dtTimeIn!=null){
//             this.timeIn = Formater.formatDate(dtTimeIn, "HH:mm");
//        }else{
//            this.timeIn = "-";
//        }
//    }
//
//    /**
//     * @return the timeOut
//     */
//    public String getTimeOut() {
//        return timeOut;
//    }
//
//    /**
//     * @param timeOut the timeOut to set
//     */
//    public void setTimeOut(Date dtTimeOut) {
//        if(dtTimeOut!=null){
//             this.timeOut = Formater.formatDate(dtTimeOut, "HH:mm");
//        }else{
//            this.timeOut = "-";
//        }
//    }
//
//    /**
//     * @return the breakOut
//     */
//    public String getBreakOut() {
//        return breakOut;
//    }
//
//    /**
//     * @param breakOut the breakOut to set
//     */
//    public void setBreakOut(Date dtBreakOut) {
//        if(dtBreakOut!=null){
//             this.breakOut = Formater.formatDate(dtBreakOut, "HH:mm");
//        }else{
//            this.breakOut = "-";
//        }
//    }
//
//    /**
//     * @return the breakIn
//     */
//    public String getBreakIn() {
//        return breakIn;
//    }
//
//    /**
//     * @param breakIn the breakIn to set
//     */
//    public void setBreakIn(Date dtBreakIn) {
//        if(dtBreakIn!=null){
//             this.breakIn = Formater.formatDate(dtBreakIn, "HH:mm");
//        }else{
//            this.breakIn = "-";
//        }
//    }
    /**
     * @return the employeeOutletId
     */
    public long getEmployeeOutletId() {
        return employeeOutletId;
    }

    /**
     * @param employeeOutletId the employeeOutletId to set
     */
    public void setEmployeeOutletId(long employeeOutletId) {
        this.employeeOutletId = employeeOutletId;
    }

    /**
     * @return the schedleSymbolId2nd
     */
    public long getSchedleSymbolId2nd() {
        return schedleSymbolId2nd;
    }

    /**
     * @param schedleSymbolId2nd the schedleSymbolId2nd to set
     */
    public void setSchedleSymbolId2nd(long schedleSymbolId2nd) {
        this.schedleSymbolId2nd = schedleSymbolId2nd;
    }

    /**
     * @return the positionKode
     */
    public String getPositionKode() {
        if (positionKode == null) {
            return "";
        }
        return positionKode;
    }

    /**
     * @param positionKode the positionKode to set
     */
    public void setPositionKode(String positionKode) {
        this.positionKode = positionKode;
    }

    /**
     * @return the typeSchedule
     */
    public int getTypeSchedule() {
        return typeSchedule;
    }

    /**
     * @param typeSchedule the typeSchedule to set
     */
    public void setTypeSchedule(int typeSchedule) {
        this.typeSchedule = typeSchedule;
    }

    /**
     * @return the symbolSchedule
     */
    public String getSymbolSchedule() {
         if(symbolSchedule==null || symbolSchedule.length()==0){
            return "";
        }
        return symbolSchedule;
    }

    /**
     * @param symbolSchedule the symbolSchedule to set
     */
    public void setSymbolSchedule(String symbolSchedule) {
        this.symbolSchedule = symbolSchedule;
    }

    /**
     * @return the timeIn
     */
    public String getTimeIn() {
        if(timeIn==null || timeIn.length()==0){
            return "";
        }
        return timeIn;
    }

    /**
     * @param timeIn the timeIn to set
     */
    public void setTimeIn(Date timeIn) {
        if (timeIn != null) {
            this.timeIn = Formater.formatDate(timeIn, "HH:mm");
        } else {
            this.timeIn = "";
        }

    }

    /**
     * @return the timeOut
     */
    public String getTimeOut() {
        if(timeOut==null || timeOut.length()==0){
            return "";
        }
        return timeOut;
    }

    /**
     * @param timeOut the timeOut to set
     */
    public void setTimeOut(Date timeOut) {
        //this.timeOut = timeOut;
        if (timeIn != null) {
            this.timeOut = Formater.formatDate(timeOut, "HH:mm");
        } else {
            this.timeOut = "";
        }
    }

    /**
     * @return the breakOut
     */
    public String getBreakOut() {
        if(breakOut==null || breakOut.length()==0){
            return "";
        }
        return breakOut;
    }

    /**
     * @param breakOut the breakOut to set
     */
    public void setBreakOut(Date breakOut) {
        //this.breakOut = breakOut;
        if (breakOut != null) {
            this.breakOut = Formater.formatDate(breakOut, "HH:mm");
        } else {
            this.breakOut = "";
        }
    }

    /**
     * @return the breakIn
     */
    public String getBreakIn() {
        if(breakIn==null || breakIn.length()==0){
            return "";
        }
        return breakIn;
    }

    /**
     * @param breakIn the breakIn to set
     */
    public void setBreakIn(Date breakIn) {
        //this.breakIn = breakIn;
        if (breakIn != null) {
            this.breakIn = Formater.formatDate(breakIn, "HH:mm");
        } else {
            this.breakIn = "";
        }
    }

    /**
     * @return the prevColomStart
     */
    public int getPrevColomStart() {
        return prevColomStart;
    }

    /**
     * @param prevColomStart the prevColomStart to set
     */
    public void setPrevColomStart(int prevColomStart) {
        this.prevColomStart = prevColomStart;
    }

    /**
     * @return the prevColomEnd
     */
    public int getPrevColomEnd() {
        return prevColomEnd;
    }

    /**
     * @param prevColomEnd the prevColomEnd to set
     */
    public void setPrevColomEnd(int prevColomEnd) {
        this.prevColomEnd = prevColomEnd;
    }

    /**
     * @return the prevTimeIn
     */
    public String getPrevTimeIn() {
        if(prevTimeIn==null || prevTimeIn.length()==0){
            return "";
        }
        return prevTimeIn;
    }

    /**
     * @param prevTimeIn the prevTimeIn to set
     */
    public void setPrevTimeIn(Date prevTimeIn) {
        //this.prevTimeIn = prevTimeIn;
        if (prevTimeIn != null) {
            this.prevTimeIn = Formater.formatDate(prevTimeIn, "HH:mm");
        } else {
            this.prevTimeIn = "";
        }
    }

    /**
     * @return the prevTimeOut
     */
    public String getPrevTimeOut() {
        if(prevTimeOut==null || prevTimeOut.length()==0){
            return "";
        }
        return prevTimeOut;
    }

    /**
     * @param prevTimeOut the prevTimeOut to set
     */
    public void setPrevTimeOut(Date prevTimeOut) {
        //this.prevTimeOut = prevTimeOut;
        if (prevTimeOut != null) {
            this.prevTimeOut = Formater.formatDate(prevTimeOut, "HH:mm");
        } else {
            this.prevTimeOut = "";
        }
    }

    /**
     * @return the prevSymbolSchedule
     */
    public String getPrevSymbolSchedule() {
        if(prevSymbolSchedule==null || prevSymbolSchedule.length()==0){
            return "";
        }
        return prevSymbolSchedule;
    }

    /**
     * @param prevSymbolSchedule the prevSymbolSchedule to set
     */
    public void setPrevSymbolSchedule(String prevSymbolSchedule) {
        if (prevSymbolSchedule == null) {
            this.prevSymbolSchedule = "";
        } else {
            this.prevSymbolSchedule = prevSymbolSchedule;
        }

    }

    /**
     * @return the widthSpacePemisah
     */
    public int getWidthSpacePemisah() {
        return widthSpacePemisah;
    }

    /**
     * @param widthSpacePemisah the widthSpacePemisah to set
     */
    public void setWidthSpacePemisah(int widthSpacePemisah) {
        this.widthSpacePemisah = widthSpacePemisah;
    }

    /**
     * @return the locationColorPrev
     */
    public String getLocationColorPrev() {
        if (locationColorPrev == null) {
            return "";
        }
        return locationColorPrev;
        //return locationColorPrev;
    }

    /**
     * @param locationColorPrev the locationColorPrev to set
     */
    public void setLocationColorPrev(String locationColorPrev) {
        this.locationColorPrev = locationColorPrev;
    }

    /**
     * @return the locationNamePrev
     */
    public String getLocationNamePrev() {
        //return locationNamePrev;
        if (locationNamePrev == null) {
            return "";
        }
        return locationNamePrev;
    }

    /**
     * @param locationNamePrev the locationNamePrev to set
     */
    public void setLocationNamePrev(String locationNamePrev) {
        this.locationNamePrev = locationNamePrev;
    }

    /**
     * @return the positionPrev
     */
    public String getPositionPrev() {
        //return positionPrev;
        if (positionPrev == null) {
            return "";
        }
        return positionPrev;
    }

    /**
     * @param positionPrev the positionPrev to set
     */
    public void setPositionPrev(String positionPrev) {
        this.positionPrev = positionPrev;
    }

    /**
     * @return the symbolSchedulePrev
     */
    public String getSymbolSchedulePrev() {
        //return symbolSchedulePrev;
        if (symbolSchedulePrev == null) {
            return "";
        }
        return symbolSchedulePrev;
    }

    /**
     * @param symbolSchedulePrev the symbolSchedulePrev to set
     */
    public void setSymbolSchedulePrev(String symbolSchedulePrev) {
        this.symbolSchedulePrev = symbolSchedulePrev;
    }

    /**
     * @return the positionKodePrev
     */
    public String getPositionKodePrev() {
        if (positionKodePrev == null) {
            return "";
        }
        return positionKodePrev;
        //return positionKodePrev;
    }

    /**
     * @param positionKodePrev the positionKodePrev to set
     */
    public void setPositionKodePrev(String positionKodePrev) {
        this.positionKodePrev = positionKodePrev;
    }

    /**
     * @return the linkPopUpEditOutletMapping
     */
    public String getLinkPopUpEditOutletMapping() {
        if(linkPopUpEditOutletMapping==null){
            return "";
        }
        return linkPopUpEditOutletMapping;
    }

    /**
     * @param linkPopUpEditOutletMapping the linkPopUpEditOutletMapping to set
     */
    public void setLinkPopUpEditOutletMapping(String linkPopUpEditOutletMapping) {
        this.linkPopUpEditOutletMapping = linkPopUpEditOutletMapping;
    }

    /**
     * @return the empNumber
     */
    public String getEmpNumber() {
        if(empNumber==null || empNumber.length()==0){
            return "";
        }
        return empNumber;
    }

    /**
     * @param empNumber the empNumber to set
     */
    public void setEmpNumber(String empNumber) {
        this.empNumber = empNumber;
    }

    /**
     * @return the fullName
     */
    public String getFullName() {
        if(fullName==null || fullName.length()==0){
            return "";
        }
        return fullName;
    }

    /**
     * @param fullName the fullName to set
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * @return the positionIdFromEmp
     */
    public long getPositionIdFromEmp() {
        return positionIdFromEmp;
    }

    /**
     * @param positionIdFromEmp the positionIdFromEmp to set
     */
    public void setPositionIdFromEmp(long positionIdFromEmp) {
        this.positionIdFromEmp = positionIdFromEmp;
    }
}

