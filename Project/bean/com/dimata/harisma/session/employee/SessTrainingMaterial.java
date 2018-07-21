/*
 * SessTrainingMaterial.java
 *
 * Created on December 8, 2007, 10:07 AM
 */

package com.dimata.harisma.session.employee;

/**
 *
 * @author  Yunny
 */

// core java package

import java.util.*;
import java.sql.*;

// dimata package
import com.dimata.qdep.db.*;
import com.dimata.util.blob.*;

// project package
import com.dimata.harisma.entity.employee.*;
import com.dimata.harisma.entity.masterdata.*;
import com.dimata.system.entity.system.PstSystemProperty;



public class SessTrainingMaterial {
    public static final String SESKEY_PICTURE = "TRAINING_FILE_ID";
    private static String IMGCACHE_REALPATH = "trainingmaterial" + System.getProperty("file.separator");
    private static String IMGCACHE_ABSPATH = "";
    private static String IMG_PREFIX = "";
    private static String IMG_POSTFIX = "";
   
  
    /** Creates a new instance of SessTrainingMaterial */
    public SessTrainingMaterial() {
        // IMG_POSTFIX = PstSystemProperty.getValueByName("IMG_POSTFIX");
        IMGCACHE_ABSPATH = PstSystemProperty.getValueByName("TRAINING_MATERIAL");
        //System.out.println("IMG_POSTFIX = : "+IMG_POSTFIX);
    }
    
    public int updateImage(Object obj, long oid) {
        DBResultSet dbrs = null;
        try {
            if (obj == null) return -1;
            PreparedStatement pstmt = null;
            
            byte b[] = null;
            b = (byte[]) obj;
            
            String sql = "UPDATE " + PstTrainingFile.TBL_HR_TRAINING_FILE + " SET " +
            PstTrainingFile.fieldNames[PstTrainingFile.FLD_ATTACH_FILE] + " = ? WHERE " +
            PstTrainingFile.fieldNames[PstTrainingFile.FLD_ATTACH_FILE] + " = ?";
            
            System.out.println("SessTrainingMaterial.updateImage --- > " + sql);
            dbrs = DBHandler.getPSTMTConnection(sql);
            pstmt = dbrs.getPreparedStatement();
            
            pstmt.setBytes(1, b);
            pstmt.setLong(2, oid);
            
            DBHandler.execUpdatePreparedStatement(pstmt);
            ImageLoader.deleteChace(IMGCACHE_ABSPATH + IMG_PREFIX + oid + IMG_POSTFIX);
            
        } catch (Exception e) {
            System.out.println(e.toString());
        } finally {
            DBResultSet.closePstmt(dbrs);
        }
        return 0;
    }
    
    public int deleteImage(long oid) {
        boolean exist = false;
        try {
            String sql = " DELETE FROM  " + PstTrainingFile.TBL_HR_TRAINING_FILE + " " +
            " WHERE " + PstTrainingFile.fieldNames[PstTrainingFile.FLD_TRAINING_FILE_ID] + " = '" + oid + "'";
            
            System.out.println("SessTrainingMaterial.deleteImage : " + sql);
            DBHandler.execUpdate(sql);
            
            ImageLoader.deleteChace(IMGCACHE_ABSPATH + IMG_PREFIX + oid + IMG_POSTFIX);
            
            return 0;
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return -1;
    }
    
    public String fetchImage(long oid) {
        int resCode = -1;
        
        String absImgPath = IMGCACHE_ABSPATH + IMG_PREFIX + oid + IMG_POSTFIX;
        java.io.File flImg = new java.io.File(absImgPath);
        
        if (flImg.exists()) {
            System.out.println("....." + IMGCACHE_REALPATH + IMG_PREFIX + oid + IMG_POSTFIX);
            return IMGCACHE_REALPATH + IMG_PREFIX + oid + IMG_POSTFIX;
        }
        
        if (queryImage(oid) == 0) {
            System.out.println("....." + IMGCACHE_REALPATH + IMG_PREFIX + oid + IMG_POSTFIX);
            return IMGCACHE_REALPATH + IMG_PREFIX + oid + IMG_POSTFIX;
        } else
            return "";
        
    }
    
       /**
     * @param oid
     * @return
     */
    public String fetchImagePeserta(long oid) {
        try {
             //update by satrya 2012-11-23
         if(oid!=0){
            Employee objEmployee = PstEmployee.fetchExc(oid);
            return fetchImagePeserta(objEmployee.getEmployeeNum());
         }else{
              return "";
         }
           // Employee objEmployee = PstEmployee.fetchExc(oid);
           // return fetchImagePeserta(objEmployee.getEmployeeNum());
        } catch (Exception e) {
            System.out.println("Exc when fetchImagePeserta : " + e.toString());
        }
        return "";
    }
    
     public String fetchImageTrainingFile(long oid) {
        System.out.println("oid....."+oid);
        try {
            TrainingFile trainingFile = PstTrainingFile.fetchExc(oid);
            
            return fetchImageTrainingFilePath(trainingFile.getOID());
        } catch (Exception e) {
            System.out.println("Exc when fetchImageRelevantDoc : " + e.toString());
        }
        return "";
    }
     
     /**
     * @param noPeserta
     * @return
     */
    public String fetchImagePeserta(String empNum) {
        int resCode = -1;
        //java.io.File flImg = null;
        //try{
            String absImgPath = IMGCACHE_ABSPATH + IMG_PREFIX + empNum + IMG_POSTFIX;
            System.out.println("..... " + absImgPath);
            java.io.File flImg = new java.io.File(absImgPath);
        /*}catch(Exception e){
            System.out.println(e.toString());
        }*/
        if (flImg.exists()) {
            System.out.println("....." + IMGCACHE_REALPATH + IMG_PREFIX + empNum + IMG_POSTFIX);
            return IMGCACHE_REALPATH + IMG_PREFIX + empNum + IMG_POSTFIX;
        } else {
            return "";
        }
    }
    
    /**
     * @param noPeserta
     * @return
     */
    public String fetchImageTrainingFilePath(long oid) {
        int resCode = -1;
        //java.io.File flImg = null;
        //try{
            String absImgPath = IMGCACHE_ABSPATH + IMG_PREFIX + oid + IMG_POSTFIX;
            System.out.println("..... " + absImgPath);
            java.io.File flImg = new java.io.File(absImgPath);
        /*}catch(Exception e){
            System.out.println(e.toString());
        }*/
        if (flImg.exists()) {
            System.out.println("....." + IMGCACHE_REALPATH + IMG_PREFIX + oid + IMG_POSTFIX);
            return IMGCACHE_REALPATH + IMG_PREFIX + oid + IMG_POSTFIX;
        } else {
            return "";
        }
    }
    
     public boolean checkOID(long oid) {
        DBResultSet dbrs = null;
        long count = 0;
        try {
            String sql = "SELECT " + PstTrainingFile.fieldNames[PstTrainingFile.FLD_TRAINING_FILE_ID] +
            " FROM " + PstTrainingFile.TBL_HR_TRAINING_FILE +
            " WHERE " + PstTrainingFile.fieldNames[PstTrainingFile.FLD_TRAINING_FILE_ID] +
            " = '" + oid + "'";
            
            System.out.println("SessTrainingMaterial.checkOID sql : " + sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            
            while (rs.next()) {
                count = rs.getLong(1);
            }
        } catch (Exception e) {
            System.out.println("exc when select checkOID : " + e.toString());
        } finally {
            DBResultSet.close(dbrs);
            if (count > 0) {
                return true;
            }
            return false;
        }
    }
     
     private int queryImage(long oid) {
        DBResultSet dbrs = null;
        java.io.InputStream ins = null;
        try {
            String sql = " SELECT " + PstTrainingFile.fieldNames[PstTrainingFile.FLD_TRAINING_FILE_ID] +
            " FROM " + PstTrainingFile.TBL_HR_TRAINING_FILE +
            " WHERE " + PstTrainingFile.fieldNames[PstTrainingFile.FLD_TRAINING_FILE_ID] +
            " = '" + oid + "'";
            
            System.out.println("sql SessEmpRelevantDoc.queryImage : " + sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            
            while (rs.next()) {
                ins = rs.getBinaryStream(1);
                break;
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        } finally {
            DBResultSet.close(dbrs);
            return ImageLoader.writeCache(ins, IMGCACHE_ABSPATH + IMG_PREFIX + oid + IMG_POSTFIX, true);
        }
    }
     
      /**
     * get absolute path and file name
     *
     * @param photoOid
     * @return
     */
    public String getAbsoluteFileName(String fileName) {
        return IMGCACHE_ABSPATH + IMG_PREFIX + fileName + IMG_POSTFIX;
    }
    
    
     public String getAbsoluteFileName(long oidDoc) {
       // System.out.println("oidDoc..........."+oidDoc);
        return IMGCACHE_ABSPATH + IMG_PREFIX + oidDoc + IMG_POSTFIX;
    }
    
     
    /**
     * get real path and file name
     *
     * @param photoOid
     * @return
     */
    public String getRealFileName(String empNum) {
        return IMGCACHE_REALPATH + IMG_PREFIX + empNum + IMG_POSTFIX;
    }
    
      public String getRealFileName(long oidDoc) {
        return IMGCACHE_REALPATH + IMG_PREFIX + oidDoc + IMG_POSTFIX;
    }
    
}
