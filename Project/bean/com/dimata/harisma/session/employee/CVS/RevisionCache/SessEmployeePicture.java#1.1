/*
 * SessEmployeePicture.java
 *
 * Created on December 1, 2007, 9:49 AM
 */
package com.dimata.harisma.session.employee;

// core java package
import java.util.*;
import java.sql.*;

// dimata package
import com.dimata.qdep.db.*;
import com.dimata.util.blob.*;

// project package
import com.dimata.harisma.entity.employee.*;
import com.dimata.system.entity.system.PstSystemProperty;

/**
 *
 * @author  yunny
 */
public class SessEmployeePicture {

    public static final String SESKEY_PICTURE = "PHOTO_PICT_ID";
    private static String IMGCACHE_REALPATH = "imgcache/"; // 'imgcache' + System.getProperty('file.separator');
    private static String IMGCACHE_ABSPATH = "";
    private static String IMG_PREFIX = "";
    public static String IMG_POSTFIX = ".JPEG";
    private static Vector imgExtention = new Vector();

    /** Creates a new instance of SessEmployeePicture */
    public SessEmployeePicture() {
        // IMG_POSTFIX = PstSystemProperty.getValueByName("IMG_POSTFIX");
        IMGCACHE_ABSPATH = PstSystemProperty.getValueByName("IMGCACHE");
        System.out.println("IMG_POSTFIX = : " + IMG_POSTFIX);
        if (imgExtention == null) {
            imgExtention = new Vector();
        }

    }

    public static void addPictureExtention(String ext) {
        if (imgExtention != null) {
            for (int i = 0; i < imgExtention.size(); i++) {
                String extI = (String) imgExtention.get(i);
                if (extI != null && extI.compareTo(ext) == 0) {
                    return;
                }
            }
            imgExtention.add(ext);
        } else {
            imgExtention = new Vector();
            imgExtention.add(ext);
        }
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

            String sql = "UPDATE " + PstEmpPicture.TBL_HR_EMP_PICTURE + " SET "
                    + PstEmpPicture.fieldNames[PstEmpPicture.FLD_PIC] + " = ? WHERE "
                    + PstEmpPicture.fieldNames[PstEmpPicture.FLD_PIC_EMP_ID] + " = ?";

            System.out.println("SessEmployeePicture.updateImage --- > " + sql);
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
            String sql = " DELETE FROM  " + PstEmpPicture.TBL_HR_EMP_PICTURE + " "
                    + " WHERE " + PstEmpPicture.fieldNames[PstEmpPicture.FLD_PIC_EMP_ID] + " = '" + oid + "'";

            System.out.println("SessEmployeePicture.deleteImage : " + sql);
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
            System.out.println("....." + absImgPath);
            return IMGCACHE_REALPATH + IMG_PREFIX + oid + IMG_POSTFIX;
        }

        if (queryImage(oid) == 0) {
            System.out.println("....." + IMGCACHE_REALPATH + IMG_PREFIX + oid + IMG_POSTFIX);
            return IMGCACHE_REALPATH + IMG_PREFIX + oid + IMG_POSTFIX;
        } else {
            return "";
        }

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
        } catch (Exception e) {
            System.out.println("Exc when fetchImagePeserta : " + e.toString());
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
       // System.out.println("..... " + absImgPath);
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

    public String fetchImageEmployee(long employeeId) {
        try {
         if(employeeId!=0){
            Employee objEmployee = PstEmployee.fetchExc(employeeId);
             return fetchImageEmployee(objEmployee.getEmployeeNum());
         }else{
              return "";
         }
          //  Employee objEmployee = PstEmployee.fetchExc(employeeId);
           // return fetchImageEmployee(objEmployee.getEmployeeNum());
        } catch (Exception e) {
            System.out.println("Exc when fetchImagePeserta : " + e.toString());
        }
        return "";
    }
    
    /**
     * create by satrya 2013-11-04
     * desc: unutk gambar image di email
     * @param employeeId
     * @return 
     */
     public String fetchImageEmployeeVer1(long employeeId) {
        try {
         if(employeeId!=0){
            Employee objEmployee = PstEmployee.fetchExc(employeeId);
             return fetchImagePesertaVer1(objEmployee.getEmployeeNum());
         }else{
              return "";
         }
          //  Employee objEmployee = PstEmployee.fetchExc(employeeId);
           // return fetchImageEmployee(objEmployee.getEmployeeNum());
        } catch (Exception e) {
            System.out.println("Exc when fetchImagePeserta : " + e.toString());
        }
        return "";
    }

    /**
     * //update by satrya 2013-11-04
     * desc: unutk gambar image di email
     * @param empNum
     * @return 
     */
    public String fetchImagePesertaVer1(String empNum) {
       
        String absImgPath = IMGCACHE_ABSPATH + IMG_PREFIX + empNum + IMG_POSTFIX;
        System.out.println("..... " + absImgPath);
        java.io.File flImg = new java.io.File(absImgPath);
        /*}catch(Exception e){
        System.out.println(e.toString());
        }*/
        if (flImg.exists()) {
            System.out.println("....." + IMGCACHE_REALPATH + IMG_PREFIX + empNum + IMG_POSTFIX);
            return absImgPath;
        } else {
            //return "" memanggil default picture yg ada
            return IMGCACHE_ABSPATH + IMG_PREFIX + "no_photo.JPEG";
        }
    }
    
    public String fetchImageEmployee(String empNum) {
        String path = fetchImagePeserta(empNum);
        if (path == null || path.length() < 1) {            
            java.io.File dir = new java.io.File(IMGCACHE_ABSPATH);
            String[] chld = dir.list();
            if (chld == null) {
                //update by satrya 2014-06-06 return "Specified directory does not exist or is not a directory.";
               // System.out.print("return \"Specified directory does not exist or is not a directory.\";");
                return "";
            } else {
                for (int i = 0; i < chld.length; i++) {
                    String fileName = chld[i];
                    if ((fileName != null) && fileName.contains(empNum)) {
                        return (IMGCACHE_REALPATH+fileName);
                    }
                }
                return "";
            }
        } else {
            return path;
        }
    }

    public boolean checkOID(long oid) {
        DBResultSet dbrs = null;
        long count = 0;
        try {
            String sql = "SELECT " + PstEmpPicture.fieldNames[PstEmpPicture.FLD_PIC_EMP_ID]
                    + " FROM " + PstEmpPicture.TBL_HR_EMP_PICTURE
                    + " WHERE " + PstEmpPicture.fieldNames[PstEmpPicture.FLD_PIC_EMP_ID]
                    + " = '" + oid + "'";

            System.out.println("SessEmployeePicture.checkOID sql : " + sql);
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
            String sql = " SELECT " + PstEmpPicture.fieldNames[PstEmpPicture.FLD_PIC]
                    + " FROM " + PstEmpPicture.TBL_HR_EMP_PICTURE
                    + " WHERE " + PstEmpPicture.fieldNames[PstEmpPicture.FLD_PIC_EMP_ID]
                    + " = '" + oid + "'";

            System.out.println("sql SessEmployeePicture.queryImage : " + sql);
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
        SessEmployeePicture objSessEmployeePicture = new SessEmployeePicture();
        System.out.println("objSessEmployeePicture.IMGCACHE_ABSPATH : " + objSessEmployeePicture.getAbsoluteFileName("3453"));
    }
}
