/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.utility.machine.testmachine;

import com.dimata.qdep.db.I_DBType;
import com.dimata.util.Formater;
import com.sun.org.apache.xpath.internal.operations.Bool;
import java.util.Date;
import java.util.Vector;

/**
 *
 * @author Dimata 007
 */
public class QueryResult {

    private Vector vHeader = new Vector();
    private Vector vIsiContent = new Vector();

    public int getSizeHeader() {
        return vHeader.size();
    }

    /**
     * @return the vHeader
     */
    public QueryRecordHeader getvHeader(int idx) {
        if (idx >= vHeader.size()) {
            return null;
        }
        return (QueryRecordHeader) vHeader.get(idx);

    }

    /**
     * @param vHeader the vHeader to set
     */
    public void addvHeader(QueryRecordHeader queryRecordHeader) {
        this.vHeader.add(queryRecordHeader);
    }

    /**
     * @return the vIsiContent
     */
    public Vector getvIsiContent(int idx) {
        if (idx >= vIsiContent.size()) {
            return null;
        }
        return (Vector) vIsiContent.get(idx);
        // return vIsiContent;
    }

    public int getContentSize() {
        //Vector sizeContent = (Vector) vIsiContent.get(0);
        return vIsiContent.size();
    }
    
    public Vector getContent() {
        //Vector sizeContent = (Vector) vIsiContent.get(0);
        return vIsiContent;
    }

    /**
     * @param vIsiContent the vIsiContent to set
     */
    public void addvIsiContent(Vector vIsiContent) {
        this.vIsiContent.add(vIsiContent);
    }

    public Vector getContentData(int idxcon,int no) {
       
         Vector sData = new Vector();
        try {
           
         if(vIsiContent!=null && vIsiContent.size()>0){
          //   for(int idxcon=0;idxcon<vIsiContent.size();idxcon++){
                  Vector sizeContent = (Vector) vIsiContent.get(idxcon);
            
            if (sizeContent!=null && sizeContent.size()>0) {
                 //no = no +1;    
                sData.add(""+no);
                   
              for(int idx=0;idx<sizeContent.size();idx++){
                QueryRecordHeader queryRecordHeader = (QueryRecordHeader)this.vHeader.get(idx);  
                //if(idx!=0 && (idx % (vHeader.size()) == 0)){
                   
                //}
               
                
                if (queryRecordHeader.getType() == I_DBType.TYPE_DATE) {
                    Date dtx = (Date) sizeContent.get(idx);
                    try {
                        if (dtx != null) {
                            sData.add(Formater.formatDate(dtx, "yyyy-MM-dd HH:mm:ss"));
                        }else{
                            sData.add("-");
                        }
                    } catch (Exception exc) {
                         sData.add(Formater.formatDate(dtx, "yyyy-MM-dd"));
                    }
                }else if(queryRecordHeader.getType() == I_DBType.TYPE_LONG){
                    long lData = (Long) sizeContent.get(idx);
                     sData.add(""+lData);
                }else if(queryRecordHeader.getType() == I_DBType.TYPE_BOOL){
                    Bool lData = (Bool) sizeContent.get(idx);
                     sData.add(""+lData);
                }else if(queryRecordHeader.getType() == I_DBType.TYPE_STRING){
                    sData.add((String) sizeContent.get(idx));
                }else if(queryRecordHeader.getType() == I_DBType.TYPE_FLOAT){
                     float lData = (Float) sizeContent.get(idx);
                     sData.add(""+lData);
                }else if(queryRecordHeader.getType() == I_DBType.TYPE_INT){
                    int lData = (Integer) sizeContent.get(idx);
                     sData.add(""+lData);
                }else if(queryRecordHeader.getType() == I_DBType.TYPE_TIMESTAMP){
                    java.sql.Timestamp lData = (java.sql.Timestamp) sizeContent.get(idx);
                     sData.add(""+lData);
                }
               
              }
            }//end
         // }//end
       }
        } catch (Exception exc) {
            System.out.println("Error"+exc);
        }
        return sData;
    }
}
