/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.system.session.dataupload;

import com.dimata.qdep.db.DBHandler;
import com.dimata.qdep.db.DBResultSet;
import com.dimata.system.entity.PstSystemProperty;
import com.dimata.system.entity.dataupload.DataUploadDetail;
import com.dimata.system.entity.dataupload.PstDataUploadDetail;
import com.dimata.system.entity.dataupload.PstDataUploadMain;
import com.dimata.util.blob.ImageLoader;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author khirayinnura
 */
public class SessDataUpload {

    public static final String SESKEY_PICTURE = "DATA_DETAIL_ID";
    private static String IMGCACHE_REALPATH = "imgdoc" + System.getProperty("file.separator");
    private static String IMGCACHE_ABSPATH = "";
    private static String IMG_PREFIX = "";
    private static String IMG_POSTFIX = "";

    /**
     * Creates a new instance of SessDataUpload
     */
    public SessDataUpload() {
        // IMG_POSTFIX = PstSystemProperty.getValueByName("IMG_POSTFIX");
        IMGCACHE_ABSPATH = PstSystemProperty.getValueByName("IMGDOC");
        //System.out.println("IMG_POSTFIX = : "+IMG_POSTFIX);
    }

    public int updateImage(Object obj, long oid) {
        DBResultSet dbrs = null;
        try {
            if (obj == null) {
                return -1;
            }
            PreparedStatement pstmt = null;

            byte b[] = null;
            b = (byte[]) obj;

            String sql = "UPDATE " + PstDataUploadDetail.TBL_DSJ_DATA_DETAIL + " SET "
                    + PstDataUploadDetail.fieldNames[PstDataUploadDetail.FLD_FILENAME] + " = ? WHERE "
                    + PstDataUploadDetail.fieldNames[PstDataUploadDetail.FLD_FILENAME] + " = ?";

            System.out.println("SessDataUpload.updateImage --- > " + sql);
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
            String sql = " DELETE FROM  " + PstDataUploadDetail.TBL_DSJ_DATA_DETAIL + " "
                    + " WHERE " + PstDataUploadDetail.fieldNames[PstDataUploadDetail.FLD_DATA_DETAIL_ID] + " = '" + oid + "'";

            System.out.println("SessDataUpload.deleteImage : " + sql);
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
        } else {
            return "";
        }

    }
    
    public String fetchImageRelevantDoc(long oid) {
        System.out.println("oid....." + oid);
        try {
            DataUploadDetail objDataUploadDetail = PstDataUploadDetail.fetchExc(oid);

            return fetchImageRelevantDocPath(objDataUploadDetail.getOID());
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
    public String fetchImageRelevantDocPath(long oid) {
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
            String sql = "SELECT " + PstDataUploadDetail.fieldNames[PstDataUploadDetail.FLD_DATA_DETAIL_ID]
                    + " FROM " + PstDataUploadDetail.TBL_DSJ_DATA_DETAIL
                    + " WHERE " + PstDataUploadDetail.fieldNames[PstDataUploadDetail.FLD_DATA_DETAIL_ID]
                    + " = '" + oid + "'";

            System.out.println("SessDataUpload.checkOID sql : " + sql);
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
            String sql = " SELECT " + PstDataUploadDetail.fieldNames[PstDataUploadDetail.FLD_DATA_DETAIL_ID]
                    + " FROM " + PstDataUploadDetail.TBL_DSJ_DATA_DETAIL
                    + " WHERE " + PstDataUploadDetail.fieldNames[PstDataUploadDetail.FLD_DATA_DETAIL_ID]
                    + " = '" + oid + "'";

            System.out.println("sql SessDataUpload.queryImage : " + sql);
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
    public String getAbsoluteFileName(String empNum) {
        return IMGCACHE_ABSPATH + IMG_PREFIX + empNum + IMG_POSTFIX;
    }
    
    public String getAbsoluteFileName(String noPeserta, String patchPhoto) {
        return patchPhoto +"//" + IMG_PREFIX + noPeserta;
    }

    public String getAbsoluteFileName(long oidDoc) {
        System.out.println("oidDoc..........." + oidDoc);
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

    public static void main(String args[]) {
        SessDataUpload objSessDataUpload = new SessDataUpload();
        System.out.println("objSessEmployeePicture.IMGCACHE_ABSPATH : " + objSessDataUpload.getAbsoluteFileName("3453"));
    }
}
