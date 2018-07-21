

package com.dimata.util.blob;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import java.io.*;
import com.dimata.util.*;
import java.util.StringTokenizer;


public class File
{

    private SmartUpload m_parent;
    private int m_startData;
    private int m_endData;
    private int m_size;
    private String m_fieldname;
    private String m_filename;
    private String m_fileExt;
    private String m_filePathName;
    private String m_contentType;
    private String m_contentDisp;
    private String m_typeMime;
    private String m_subTypeMime;
    private String m_contentString;
    private boolean m_isMissing;
    public static final int SAVEAS_AUTO = 0;
    public static final int SAVEAS_VIRTUAL = 1;
    public static final int SAVEAS_PHYSICAL = 2;

    public File()
    {
        m_startData = 0;
        m_endData = 0;
        m_size = 0;
        m_fieldname = new String();
        m_filename = new String();
        m_fileExt = new String();
        m_filePathName = new String();
        m_contentType = new String();
        m_contentDisp = new String();
        m_typeMime = new String();
        m_subTypeMime = new String();
        m_contentString = new String();
        m_isMissing = true;
    }

    public void saveAs(String destFilePathName)
        throws SmartUploadException, IOException
    {
        saveAs(destFilePathName, 0);
    }

    public void saveAs(String destFilePathName, int optionSaveAs)
        throws SmartUploadException, IOException
    {
        String path = new String();
        path = m_parent.getPhysicalPath(destFilePathName, optionSaveAs);
        if(path == null)
            throw new IllegalArgumentException("There is no specified destination file (1140).");
        try
        {
            java.io.File file = new java.io.File(path);
            FileOutputStream fileOut = new FileOutputStream(file);
            fileOut.write(m_parent.m_binArray, m_startData, m_size);
            fileOut.close();
        }
        catch(IOException e)
        {
            throw new SmartUploadException("File can't be saved (1120).");
        }
    }

    public void fileToField(ResultSet rs, String columnName)
        throws SQLException, SmartUploadException, IOException, ServletException
    {
        long numBlocks = 0L;
        int blockSize = 0x10000;
        int leftOver = 0;
        int pos = 0;
        if(rs == null)
            throw new IllegalArgumentException("The RecordSet cannot be null (1145).");
        if(columnName == null)
            throw new IllegalArgumentException("The columnName cannot be null (1150).");
        if(columnName.length() == 0)
            throw new IllegalArgumentException("The columnName cannot be empty (1155).");
        numBlocks = BigInteger.valueOf(m_size).divide(BigInteger.valueOf(blockSize)).longValue();
        leftOver = BigInteger.valueOf(m_size).mod(BigInteger.valueOf(blockSize)).intValue();
        try
        {
            for(int i = 1; (long)i < numBlocks; i++)
            {
                rs.updateBinaryStream(columnName, new ByteArrayInputStream(m_parent.m_binArray, pos, blockSize), blockSize);
                pos = pos != 0 ? pos : 1;
                pos = i * blockSize;
            }

            if(leftOver > 0)
                rs.updateBinaryStream(columnName, new ByteArrayInputStream(m_parent.m_binArray, pos, leftOver), leftOver);
        }
        catch(SQLException e)
        {
            byte binByte2[] = new byte[m_size];
            System.arraycopy(m_parent.m_binArray, m_startData, binByte2, 0, m_size);
            rs.updateBytes(columnName, binByte2);
        }
        catch(Exception e)
        {
            throw new SmartUploadException("Unable to save file in the DataBase (1130).");
        }
    }

    public boolean isMissing()
    {
        return m_isMissing;
    }

    public String getFieldName()
    {
        return m_fieldname;
    }

    public String getFileName()
    {
        return m_filename;
    }

    public String getFilePathName()
    {
        return m_filePathName;
    }

    public String getFileExt()
    {
        return m_fileExt;
    }

    public String getContentType()
    {
        return m_contentType;
    }

    public String getContentDisp()
    {
        return m_contentDisp;
    }

    public String getContentString()
    {
        String strTMP = new String(m_parent.m_binArray, m_startData, m_size);
        return strTMP;
    }

    public String getTypeMIME()
        throws IOException
    {
        return m_typeMime;
    }

    public String getSubTypeMIME()
    {
        return m_subTypeMime;
    }

    public int getSize()
    {
        return m_size;
    }

    public int getStartData()
    {
        return m_startData;
    }

    public int getEndData()
    {
        return m_endData;
    }

    protected void setParent(SmartUpload parent)
    {
        m_parent = parent;
    }

    protected void setStartData(int startData)
    {
        m_startData = startData;
    }

    protected void setEndData(int endData)
    {
        m_endData = endData;
    }

    protected void setSize(int size)
    {
        m_size = size;
    }

    protected void setIsMissing(boolean isMissing)
    {
        m_isMissing = isMissing;
    }

    protected void setFieldName(String fieldName)
    {
        m_fieldname = fieldName;
    }

    protected void setFileName(String fileName)
    {
        m_filename = fileName;
    }

    protected void setFilePathName(String filePathName)
    {
        m_filePathName = filePathName;
    }

    protected void setFileExt(String fileExt)
    {
        m_fileExt = fileExt;
    }

    protected void setContentType(String contentType)
    {
        m_contentType = contentType;
    }

    protected void setContentDisp(String contentDisp)
    {
        m_contentDisp = contentDisp;
    }

    protected void setTypeMIME(String TypeMime)
    {
        m_typeMime = TypeMime;
    }

    protected void setSubTypeMIME(String subTypeMime)
    {
        m_subTypeMime = subTypeMime;
    }

    public byte getBinaryData(int index)
    {
        if(m_startData + index > m_endData)
            throw new ArrayIndexOutOfBoundsException("Index Out of range (1115).");
        if(m_startData + index <= m_endData)
            return m_parent.m_binArray[m_startData + index];
        else
            return 0;
    }
    
    
    /*
     * return string content of file 
     * param, is file complete with its path : c:/xxx/xx/xx.txt => slash
     */
    public String getFileString(String filePath){
        
        StringTokenizer strTok = new StringTokenizer(filePath,"/");
        filePath = "";
        while(strTok.hasMoreTokens()){
                filePath = filePath + strTok.nextToken() + System.getProperty("file.separator");
        }
        
        filePath = filePath.substring(0, filePath.length()-1);
        
        System.out.println("\n\n--- file path : "+filePath+"\n");
        
        FileInputStream fIn = null;
        String stAll="";
        try {
            fIn = new FileInputStream(filePath);
            int maxbuf = 1024;
            int rbyte = maxbuf;
            
            for (int ib = 0; (ib < Integer.MAX_VALUE) && (rbyte == maxbuf); ib = ib + maxbuf) {
                byte buffer[] = new byte[maxbuf];
                rbyte = fIn.read(buffer);
                if (rbyte > 0){
                    String st = new String(buffer,0, rbyte);
                    stAll = stAll + st;
                }
            }
            
        }
        catch (Exception e)  {
            System.out.println("Exception getFileString : "+e.toString());
        }
        
        return stAll;
    }
    
    /*
     rename a file into another file name
     * drive = d etc
     * path = temp/aa/aa/aa => use slash
     * fileName = test.txt
     * targetFile = test-1.txt
     */
    public void renameFile(String drive, String path, String fileName, String targetFile){
        
        java.io.File filex = new java.io.File(path+System.getProperty("file.separator")+fileName);
        
        try{
                StringTokenizer st = new StringTokenizer(path,"/");
                path = "";
                while(st.hasMoreTokens()){
                        path = path + st.nextToken() + System.getProperty("file.separator");
                }
                
                path = path.substring(0, path.length()-1);
                
                System.out.println("\n\n-- rename file path : "+path+"\n");
            
                String execBatFileName = "rename.bat";
                String cmd = drive+":\n";
                cmd = cmd + "cd"+System.getProperty("file.separator")+"\n";
                cmd = cmd + "cd "+path +"\n";
                cmd = cmd + "rename "+fileName+" "+targetFile+"\n";

                System.out.println("file exe : "+drive+":"+System.getProperty("file.separator")+path+System.getProperty("file.separator")+execBatFileName+"\n");
                
                FileOutputStream fff = new FileOutputStream(drive+":"+System.getProperty("file.separator")+path+System.getProperty("file.separator")+execBatFileName);
                fff.write(cmd.getBytes());
                fff.flush();
                fff.close();

                ExecCommand exccomm = new ExecCommand();
                exccomm.runCommmand(drive+":"+System.getProperty("file.separator")+path+System.getProperty("file.separator")+execBatFileName);
        }
        catch(Exception e){
                System.out.println(e.toString());
        }
    }
    
    /*
     rename a file into another file name
     * drive = d etc
     * path = temp/aa/aa/aa => use slash
     * fileName = test.txt
     * targetFile = test-1.txt
     */
    public void deleteFile(String drive, String path, String fileName){
        
        java.io.File filex = new java.io.File(path+System.getProperty("file.separator")+fileName);
        
        try{
                StringTokenizer st = new StringTokenizer(path,"/");
                path = "";
                while(st.hasMoreTokens()){
                        path = path + st.nextToken() + System.getProperty("file.separator");
                }
                
                path = path.substring(0, path.length()-1);
                
                System.out.println("\n\n-- rename file path : "+path+"\n");
            
                String execBatFileName = "delete.bat";
                String cmd = drive+":\n";
                cmd = cmd + "cd"+System.getProperty("file.separator")+"\n";
                cmd = cmd + "cd "+path +"\n";
                cmd = cmd + "del "+fileName;

                System.out.println("file exe : "+drive+":"+System.getProperty("file.separator")+path+System.getProperty("file.separator")+execBatFileName+"\n");
                
                FileOutputStream fff = new FileOutputStream(drive+":"+System.getProperty("file.separator")+path+System.getProperty("file.separator")+execBatFileName);
                fff.write(cmd.getBytes());
                fff.flush();
                fff.close();

                ExecCommand exccomm = new ExecCommand();
                exccomm.runCommmand(drive+":"+System.getProperty("file.separator")+path+System.getProperty("file.separator")+execBatFileName);
        }
        catch(Exception e){
                System.out.println(e.toString());
        }
    }
    
}
