/*
 * CardneticCardIntimas.java
 *
 * 
 * Created on November 25, 2006, 1:21 PM
 */

package com.dimata.harisma.utility.service.parser;

import java.util.*;
import com.dimata.util.*;
import com.dimata.system.entity.system.*;
//import com.dimata.harisma.utility.odbc.*;

/**
 *
 * @author  darmasusila
 */
   public class CardneticTextIntimas{
   // public static final String tableName = "HR_EMP_SCHEDULE_HISTORY";
    public String dateFormat = "yyyyMMddhhmm";
    public int dateIdxNum = 12;
    public int idIdxNum = 12;
    public int typeIdxNum = 1;
    public int swapIn = 9;
    public int swapOut = 8;
    public String swapp;
    
    /** Creates a new instance of CardneticCard */
    public CardneticTextIntimas(String swapp) {
        this.swapp = swapp;
        this.dateFormat = PstSystemProperty.getValueByName("CARDNETIC_TEXT_DATE_FORMAT");
        this.dateIdxNum = Integer.parseInt(PstSystemProperty.getValueByName("CARDNETIC_TEXT_DATE_IDX_NUM"));
        this.idIdxNum = Integer.parseInt(PstSystemProperty.getValueByName("CARDNETIC_TEXT_ID_IDX_NUM"));
        this.typeIdxNum = Integer.parseInt(PstSystemProperty.getValueByName("CARDNETIC_TEXT_TYPE_IDX_NUM"));
    }
    //String tadinya Date
  
    public String getSwappingDate(String id){
        //System.out.println("idku::::" +id);
        if(swapp!=null && swapp.length()>0){
            
           if(id.length()==1){
                    String s = swapp.substring(2, 12);
                    //System.out.println("digit 1");
                    return s;

                }
                 else if(id.trim().length()==3){
                     //lakukan kondisi dengan token
                      int i=0;
                      StringTokenizer tokId = new StringTokenizer(id);
                       while(tokId.hasMoreTokens()){
                         String tokenId=(String)tokId.nextToken();
                         //System.out.println("tokendi3"+tokenId);
                         i++;
                       }
                     
                         if(i>1){
                             String s = swapp.substring(2, 12);
                             //System.out.println("digit 1");
                             return s;
                             
                         }
                         else{
                              String s = swapp.substring(4, 14);
                              //System.out.println("digit 3");
                              return s;
                         }  
                  

                }
                else if(id.trim().length()==2){
                    String s = swapp.substring(3, 13);
                    //System.out.println("digit 2");
                    return s;   
                }
           // return Formater.formatDate(s, dateFormat);
        
        }
        return null;
    }
    
    public String getSwappingId(){
        if(swapp!=null && swapp.length()>0){
            String s = swapp.substring(0,3);
            System.out.println("s::::"+s);
            return s;
        }
        return "";
        
    }
    
    
    public String getSwappingTime(String id){
        if(swapp!=null && swapp.length()>0){
            int i=0;
             if(id.trim().length()==3){
                 //digit yang dipisahkan oleh spasi diambil dengan StringTokenizer
                 StringTokenizer tokId = new StringTokenizer(id);
                 while(tokId.hasMoreTokens()){
                     
                     String tokenId=(String)tokId.nextToken();
                     System.out.println("token1"+tokenId);
                     i++;
                      
                 }
                 System.out.println("jumlah" +i);
                     if(i>1){
                        String s = swapp.substring(13, 18);
                        
                        System.out.println("digit 1");
                        return s;    
                      }
                     else{
                        String s = swapp.substring(15,20);
                        System.out.println("digit 3");
                        return s;
                     }
                
                   
                }
                else if(id.trim().length()==2){
                    String s = swapp.substring(14, 19);
                    
                    System.out.println("digit 2");
                    return s;   
                }
            
        
        }
        return null;
    }
    
    public int getSwappingType(){
        if(swapp!=null && swapp.length()>0){
            /*String s = swapp.substring(dateIdxNum+idIdxNum, dateIdxNum+idIdxNum+typeIdxNum);
            return Integer.parseInt(s);*/
            //select data dari dateODBC sampai newDate
            /*String sql = "SELECT * FROM " + tableName;
                        //" where  trans_date = #"+strDate+"# AND time_in > '"+strTime+"' AND time_in <= '"+strNewTime+"'" ;
            System.out.println("sql : "+sql);
            //dbConn = doConnect();                
            Statement stmt = DBConnection.getStatement(DBConnection.getConnection());
            ResultSet rs = stmt.executeQuery(sql);            
            while(rs.next())
            {                                
                Vector vectTemp = new Vector(1,1);
                vectTemp.add(rs.getString(1));
                vectTemp.add(rs.getDate(5));
                vectTemp.add(rs.getString(10));
                result.add(vectTemp);                 
            }*/
        }
        return -1;
    }

}
