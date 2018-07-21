/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.utility.machine.testmachine;

import com.dimata.harisma.entity.attendance.MachineTransaction;
import com.dimata.qdep.db.DBHandler;
import com.dimata.qdep.db.I_DBType;
import com.dimata.qdep.form.Control;
import com.dimata.qdep.form.FRMMessage;
import com.dimata.util.Command;
import com.dimata.util.lang.I_Language;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 *
 * @author RAMA
 */
public class CtrlTestMachine extends Control implements I_Language {

    public static int RSLT_OK = 0;
    public static int RSLT_UNKNOWN_ERROR = 1;
    public static int RSLT_FORM_INCOMPLETE = 2;
    public static int RSLT_QUERY_INCORRECT = 3;
    public static String[][] resultText = {
        {"Koneksi Berhasil", "Koneksi Bermasalah", "Data tidak lengkap", "Query anda salah"},
        {"Succes", "Not Connect", "Data incomplete", "querty false"}};
    private int start;
    private String msgString;
    private TestMachine testMachine;
    private QueryResult queryResult = new QueryResult();
    private FrmTestMachine frmTestMachine;
    int language = 0;

    public CtrlTestMachine(HttpServletRequest request) {
        msgString = "";
        testMachine = new TestMachine();
        frmTestMachine = new FrmTestMachine(request, testMachine);

    }

    public int getLanguage() {
        return language;
    }

    public void setLanguage(int language) {
        this.language = language;
    }

    public TestMachine getMachine() {
        return testMachine;
    }
    
    public QueryResult getQueryResult() {
        return queryResult;
    }

    public FrmTestMachine getForm() {
        return frmTestMachine;
    }

    public String getMessage() {
        return msgString;
    }

    public int getStart() {
        return start;
    }

    public int action(int cmd) {


        msgString = "";


        int rsCode = RSLT_OK;

        switch (cmd) {
            case Command.LIST:
                //untuk query
                Connection getConn = null;
                frmTestMachine.requestEntityObject(testMachine);
                if (frmTestMachine.errorSize()> 0) {
                    msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
                    return RSLT_FORM_INCOMPLETE;
                }
                
                try {
                    String URL = "";
                    if (testMachine.getTypeOdbc() == FrmTestMachine.TYPE_MYSQL) {
                        URL = "jdbc:mysql://" + testMachine.getDns();

                    } else if (testMachine.getTypeOdbc() == FrmTestMachine.TYPE_MDB) {
                        URL = "jdbc:odbc:" + testMachine.getDns();

                    } else if (testMachine.getTypeOdbc() == FrmTestMachine.TYPE_DBF) {

                        URL = "jdbc:odbc:" + "DRIVER={Microsoft Visual FoxPro Driver};SourceType=DBF;SourceDB=" + testMachine.getDns();
                    } else {
                        URL = "jdbc:odbc:" + testMachine.getDns();

                    }
                    getConn = DriverManager.getConnection(URL, testMachine.getUser(), testMachine.getPassword());

                } catch (Exception exc) {
                    rsCode = RSLT_UNKNOWN_ERROR;
                }
                if (rsCode == RSLT_OK) {
                    
                    
                    Statement statement1 = null;
                    try {
                      
                        if (testMachine.getSampleQuery() != null && testMachine.getSampleQuery().length() > 0) {
                            
                            if(testMachine.getTypeOdbc()== FrmTestMachine.TYPE_DBF || testMachine.getTypeOdbc()== FrmTestMachine.TYPE_MDB){
                                statement1 = getConn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY); 
                            }else{
                                statement1 = getConn.createStatement();
                            }
                            if (testMachine.getCodeExsample() == FrmTestMachine.LIST) {
                              
                                //jika system melakukan limit jika di gunakan
                                String sql="";
                                sql = testMachine.getSampleQuery();                               
                                ResultSet rs = statement1.executeQuery(sql);
                                rs.last();
                                testMachine.setTotalRecordQuery(rs.getRow());  
                            }
                        }

                    } catch (Exception exc) {
                        System.out.println("Error query" + exc);
                        rsCode = RSLT_QUERY_INCORRECT;
                    } finally {
                        try {
                            statement1.close();
                            //getConn.close();
                        } catch (Exception e) {
                            System.out.println("EXCEPTION " + e.toString());
                            rsCode = RSLT_QUERY_INCORRECT;
                        }

                    }
                    
                    ///
                    //maka jalankan querynya
                    Statement statement = null;
                    try {
                      
                        if (testMachine.getSampleQuery() != null && testMachine.getSampleQuery().length() > 0) {
                            statement = getConn.createStatement();
                            if (testMachine.getCodeExsample() == FrmTestMachine.LIST) {
                              
                                //jika system melakukan limit jika di gunakan
                                String sql="";
                             if(testMachine.getTypeOdbc()==FrmTestMachine.TYPE_MDB){   
                                if(testMachine.isUseLimit()){
                                    String replacaSelect = testMachine.getSampleQuery().replace("select", "select Top "+testMachine.getUseLimitValue());
                                    String replacaSelect1= replacaSelect.replace("Select", "select Top "+testMachine.getUseLimitValue());
                                    String replacaSelect2= replacaSelect1.replace("SELECT", "select Top "+testMachine.getUseLimitValue());
                                    sql = replacaSelect2;
                                    
                                }else{
                                    sql = testMachine.getSampleQuery();
                                }
                             }else if(testMachine.getTypeOdbc()==FrmTestMachine.TYPE_DBF){   
                                if(testMachine.isUseLimit()){
                                  try{
                                    String cariWhere = testMachine.getSampleQuery().replace("where", "x");
                                    String cariWhere1= cariWhere.replace("Where", "x");
                                    String cariWhere2= cariWhere1.replace("WHERE", "x");
                                    if(cariWhere2!=null && cariWhere2.length()>0 && cariWhere2.equalsIgnoreCase(testMachine.getSampleQuery())){
                                        String cariWherex = testMachine.getSampleQuery().replace("x", " where recno()<="+testMachine.getUseLimitValue());
                                        if(cariWherex!=null && cariWherex.length()>0 && cariWherex.equalsIgnoreCase(testMachine.getSampleQuery())){
                                            cariWherex = cariWherex + " where recno()<="+testMachine.getUseLimitValue();
                                        }
                                      sql = cariWherex;
                                    }else{
                                        //jika sdh berubah where nya berarti ada penambahan where
                                        String cariWherex = testMachine.getSampleQuery().replace("order", " and recno()<="+testMachine.getUseLimitValue()+ " order ");
                                    String cariWherex1= cariWherex.replace("Order", " and recno()<="+testMachine.getUseLimitValue()+ " order ");
                                    String cariWherex2= cariWherex1.replace("ORDER", " and recno()<="+testMachine.getUseLimitValue()+ " order ");
                                    sql = cariWherex2;
                                        
                                    }
                                  }catch(Exception exc){
                                      
                                  }
                                }else{
                                    sql = testMachine.getSampleQuery();
                                }
                             }else{
                                 if(testMachine.isUseLimit()){
                                    sql = testMachine.getSampleQuery() + " limit 0,"+testMachine.getUseLimitValue();
                                    
                                }else{
                                    sql = testMachine.getSampleQuery();
                                }
                             
                             }
                                ResultSet rs = statement.executeQuery(sql);
                               ResultSetMetaData rstMetaData=null;
                                try{
                                   rstMetaData =rs.getMetaData();
                                }catch(Exception exc){
                                    System.out.println("Exc metadata"+exc);
                                }
                                
                                int rowCount=rstMetaData==null?0:rstMetaData.getColumnCount();
                               
                                Vector vHeader = new Vector();
                                
                               
                                for(int x=1;x<=rowCount;x++){
                                     //sessTestMachine.addvNamaFieldTable(rstMetaData.getColumnLabel(x));
                                     //sessTestMachine.addvNamaTypeData(rstMetaData.getColumnTypeName(x));
                                     QueryRecordHeader queryRecordHeader = new QueryRecordHeader();
                                   if(rstMetaData.getColumnTypeName(x)!=null){
                                      if(testMachine.getTypeOdbc()== FrmTestMachine.TYPE_DBF || testMachine.getTypeOdbc()== FrmTestMachine.TYPE_MDB){
                                          if(rstMetaData.getColumnClassName(x).equalsIgnoreCase("java.sql.Date") || rstMetaData.getColumnClassName(x).equalsIgnoreCase("java.sql.DateTime")||rstMetaData.getColumnClassName(x).equalsIgnoreCase("java.sql.Time")){
                                               queryRecordHeader.setType(I_DBType.TYPE_DATE); 
                                          }else if(rstMetaData.getColumnClassName(x).equalsIgnoreCase("java.lang.Integer") || rstMetaData.getColumnClassName(x).equalsIgnoreCase("java.math.BigDecimal")){
                                               queryRecordHeader.setType(I_DBType.TYPE_INT); 
                                          }else if(rstMetaData.getColumnClassName(x).equalsIgnoreCase("java.lang.Boolean")){
                                               queryRecordHeader.setType(I_DBType.TYPE_BOOL); 
                                          }else if(rstMetaData.getColumnClassName(x).equalsIgnoreCase("java.lang.Long")){
                                               queryRecordHeader.setType(I_DBType.TYPE_LONG); 
                                          }else if(rstMetaData.getColumnClassName(x).equalsIgnoreCase("java.lang.Float")|| rstMetaData.getColumnClassName(x).equalsIgnoreCase("java.lang.Long")){
                                               queryRecordHeader.setType(I_DBType.TYPE_FLOAT); 
                                          }else if(rstMetaData.getColumnClassName(x).equalsIgnoreCase("java.lang.Blob")){
                                              queryRecordHeader.setType(I_DBType.TYPE_BLOB);
                                          }else if(rstMetaData.getColumnClassName(x).equalsIgnoreCase("java.lang.String")){
                                               queryRecordHeader.setType(I_DBType.TYPE_STRING);
                                          }else if(rstMetaData.getColumnClassName(x).equalsIgnoreCase("java.sql.Timestamp")){
                                                 queryRecordHeader.setType(I_DBType.TYPE_TIMESTAMP); 
                                          }
                                      }else{
                                       if(rstMetaData.getColumnTypeName(x).equalsIgnoreCase("BIGINT") || rstMetaData.getColumnTypeName(x).equalsIgnoreCase("BIGINT UNSIGNED") || rstMetaData.getColumnTypeName(x).equalsIgnoreCase("LONG")){
                                              queryRecordHeader.setType(I_DBType.TYPE_LONG); 
                                       }else if(rstMetaData.getColumnTypeName(x).equalsIgnoreCase("BOOLEAN")){
                                            queryRecordHeader.setType(I_DBType.TYPE_BOOL); 
                                       }else if(rstMetaData.getColumnTypeName(x).equalsIgnoreCase("BLOB")){
                                            queryRecordHeader.setType(I_DBType.TYPE_BLOB); 
                                       }else if(rstMetaData.getColumnTypeName(x).equalsIgnoreCase("BOOL")){
                                            queryRecordHeader.setType(I_DBType.TYPE_BOOL); 
                                       }else if(rstMetaData.getColumnTypeName(x).equalsIgnoreCase("CHAR")|| rstMetaData.getColumnTypeName(x).equalsIgnoreCase("VARCHAR")|| rstMetaData.getColumnTypeName(x).equalsIgnoreCase("TEXT")){
                                            queryRecordHeader.setType(I_DBType.TYPE_STRING); 
                                       }else if(rstMetaData.getColumnTypeName(x).equalsIgnoreCase("DATE")|| rstMetaData.getColumnTypeName(x).equalsIgnoreCase("DATETIME")){
                                            queryRecordHeader.setType(I_DBType.TYPE_DATE); 
                                       }else if(rstMetaData.getColumnTypeName(x).equalsIgnoreCase("DECIMAL")|| rstMetaData.getColumnTypeName(x).equalsIgnoreCase("DOUBLE")|| rstMetaData.getColumnTypeName(x).equalsIgnoreCase("FLOAT")){
                                            queryRecordHeader.setType(I_DBType.TYPE_FLOAT); 
                                       }else if(rstMetaData.getColumnTypeName(x).equalsIgnoreCase("INT")|| rstMetaData.getColumnTypeName(x).equalsIgnoreCase("MEDIUMINT")|| rstMetaData.getColumnTypeName(x).equalsIgnoreCase("NUMERIC")|| rstMetaData.getColumnTypeName(x).equalsIgnoreCase("SMALLINT")|| rstMetaData.getColumnTypeName(x).equalsIgnoreCase("TINYINT")){
                                            queryRecordHeader.setType(I_DBType.TYPE_INT); 
                                       }else if(rstMetaData.getColumnTypeName(x).equalsIgnoreCase("TIMESTAMP")){
                                            queryRecordHeader.setType(I_DBType.TYPE_TIMESTAMP); 
                                       }
                                      }
                                    queryRecordHeader.setTitle(rstMetaData.getColumnLabel(x));
                                    vHeader.add(queryRecordHeader);
                                   }
                                                                      
                                }
                                 QueryRecordHeader queryRecordHeader1 = new QueryRecordHeader();
                                 for(int x=0;x<rowCount;x++){
                                         //RecordHeader rHeader =(RecordHeader)  queryResult.getHeader(x);
                                        queryRecordHeader1 = (QueryRecordHeader) vHeader.get(x);
                                         //switch(rHeader.getDataTypeInt()){
                                           switch(queryRecordHeader1.getType()){
                                             case I_DBType.TYPE_LONG:
                                                   
                                                    queryResult.addvHeader(queryRecordHeader1);
                                               break;
                                             case I_DBType.TYPE_BOOL:
                                                  
                                                     queryResult.addvHeader(queryRecordHeader1);
                                               break;
                                              case I_DBType.TYPE_BLOB:
                                                 
                                                    queryResult.addvHeader(queryRecordHeader1);
                                               break;
                                              case I_DBType.TYPE_STRING:
                                                 
                                                    queryResult.addvHeader(queryRecordHeader1);
                                               break;
                                             case I_DBType.TYPE_DATE:
                                                   
                                                  queryResult.addvHeader(queryRecordHeader1);
                                              break;
                                            case I_DBType.TYPE_FLOAT:
                                                 
                                                    queryResult.addvHeader(queryRecordHeader1);
                                             break;
                                            case I_DBType.TYPE_INT:
                                                 
                                                    queryResult.addvHeader(queryRecordHeader1);
                                            break;  
                                           case I_DBType.TYPE_TIMESTAMP:
                                                 
                                                    queryResult.addvHeader(queryRecordHeader1);
                                           break;
                                           default:
                                         }
                                    }
                                 //Vector tmpVector = new Vector();
                                while(rs.next()){
                                    Vector vRecord = new Vector();
                                 
                                   QueryRecordHeader queryRecordHeader = new QueryRecordHeader();
                                    for(int x=0;x<rowCount;x++){
                                         //RecordHeader rHeader =(RecordHeader)  queryResult.getHeader(x);
                                        queryRecordHeader = (QueryRecordHeader) vHeader.get(x);
                                         //switch(rHeader.getDataTypeInt()){
                                        String sValue=null;
                                       if(testMachine.getTypeOdbc()!=FrmTestMachine.TYPE_MYSQL){
                                           switch(queryRecordHeader.getType()){
                                             case I_DBType.TYPE_LONG:
                                                    sValue=rs.getString(queryRecordHeader.getTitle());
                                                    long lValue = sValue!=null && sValue.length()>0? Long.parseLong(sValue):0;
                                                   vRecord.add(lValue);
                                                    //queryResult.addvHeader(queryRecordHeader);
                                               break;
                                             case I_DBType.TYPE_BOOL:
                                                    sValue=rs.getString(queryRecordHeader.getTitle());
                                                    boolean bValue = sValue!=null && sValue.length()>0? Boolean.parseBoolean(sValue):false; 
                                                   vRecord.add(bValue);
                                                   
                                               break;
                                              case I_DBType.TYPE_BLOB:
                                                  sValue=rs.getString(queryRecordHeader.getTitle());
                                                   
                                                   vRecord.add(sValue);
                                                   
                                               break;
                                              case I_DBType.TYPE_STRING:
                                                   vRecord.add(rs.getString(queryRecordHeader.getTitle()));
                                                    //queryResult.addvHeader(queryRecordHeader);
                                               break;
                                             case I_DBType.TYPE_DATE:
                                              Date dt=null;  
                                              String sdt=rs.getString(queryRecordHeader.getTitle());
                                              try{   
                                                    dt = MachineTransaction.formatDate(sdt, "yyyy-MM-dd HH:mm:ss");
                                                if(dt==null){
                                                    dt = MachineTransaction.formatDate(sdt, "yyyy-MM-dd");
                                                }
                                              }catch(Exception exc){
                                             
                                             }
                                                vRecord.add(dt);
                                              break;
                                            case I_DBType.TYPE_FLOAT:
                                                sValue=rs.getString(queryRecordHeader.getTitle());
                                                    float fValue = sValue!=null && sValue.length()>0? Float.parseFloat(sValue):0f; 
                                                   vRecord.add(fValue);
                                                   //vRecord.add(rs.getFloat(queryRecordHeader.getTitle()));
                                                    //queryResult.addvHeader(queryRecordHeader);
                                             break;
                                            case I_DBType.TYPE_INT:
                                                 sValue=rs.getString(queryRecordHeader.getTitle());
                                                    int iValue = sValue!=null && sValue.length()>0? Integer.parseInt(sValue):0; 
                                                   vRecord.add(iValue);
                                                   
                                                    //queryResult.addvHeader(queryRecordHeader);
                                            break;  
                                           case I_DBType.TYPE_TIMESTAMP:
                                               sValue=rs.getString(queryRecordHeader.getTitle());
                                                    
                                                   vRecord.add(sValue);
                                                 
                                                   //vRecord.add(rs.getTimestamp(queryRecordHeader.getTitle()));
                                                    //queryResult.addvHeader(queryRecordHeader);
                                           break;
                                           default:
                                         }
                                       }else{
                                           switch(queryRecordHeader.getType()){
                                             case I_DBType.TYPE_LONG:
                                                 
                                                   vRecord.add(rs.getLong(queryRecordHeader.getTitle()));
                                                    //queryResult.addvHeader(queryRecordHeader);
                                               break;
                                             case I_DBType.TYPE_BOOL:
                                                   vRecord.add(rs.getBoolean(queryRecordHeader.getTitle()));
                                                     //queryResult.addvHeader(queryRecordHeader);
                                               break;
                                              case I_DBType.TYPE_BLOB:
                                                   vRecord.add(rs.getBlob(queryRecordHeader.getTitle()));
                                                    //queryResult.addvHeader(queryRecordHeader);
                                               break;
                                              case I_DBType.TYPE_STRING:
                                                   vRecord.add(rs.getString(queryRecordHeader.getTitle()));
                                                    //queryResult.addvHeader(queryRecordHeader);
                                               break;
                                             case I_DBType.TYPE_DATE:
                                                  java.sql.Time dtTime = null;
                                                 try{
                                                     dtTime =  rs.getTime(queryRecordHeader.getTitle());
                                                 }catch(Exception exc){
                                                     //System.out.println("Exc Time"+exc);
                                                 }
                                                   vRecord.add(DBHandler.convertDate(rs.getDate(queryRecordHeader.getTitle()),dtTime)); 
                                                  //queryResult.addvHeader(queryRecordHeader);
                                              break;
                                            case I_DBType.TYPE_FLOAT:
                                                   vRecord.add(rs.getFloat(queryRecordHeader.getTitle()));
                                                    //queryResult.addvHeader(queryRecordHeader);
                                             break;
                                            case I_DBType.TYPE_INT:
                                                   vRecord.add(rs.getInt(queryRecordHeader.getTitle()));
                                                    //queryResult.addvHeader(queryRecordHeader);
                                            break;  
                                           case I_DBType.TYPE_TIMESTAMP:
                                                   vRecord.add(rs.getTimestamp(queryRecordHeader.getTitle()));
                                                    //queryResult.addvHeader(queryRecordHeader);
                                           break;
                                           default:
                                         }
                                       }
                                           
                                            
                                    }
                                    queryResult.addvIsiContent(vRecord);
                                 }
                               
                            } else {
                                //statement = getConn.createStatement();
                                statement.executeUpdate(testMachine.getSampleQuery());

                            }


                        }

                    } catch (Exception exc) {
                        System.out.println("Error query" + exc);
                        rsCode = RSLT_QUERY_INCORRECT;
                    } finally {
                        try {
                            statement.close();
                            getConn.close();
                        } catch (Exception e) {
                            System.out.println("EXCEPTION " + e.toString());
                            rsCode = RSLT_QUERY_INCORRECT;
                        }

                    }
                }
                break;



            case Command.OK:
                //untuk test koneksi
                frmTestMachine.requestEntityObject(testMachine);
                if (frmTestMachine.errorSize() > 0) {
                    msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
                    return RSLT_FORM_INCOMPLETE;
                }
                try {
                    String URL = "";
                    if (testMachine.getTypeOdbc() == FrmTestMachine.TYPE_MYSQL) {
                        URL = "jdbc:mysql://" + testMachine.getDns();

                    } else if (testMachine.getTypeOdbc() == FrmTestMachine.TYPE_MDB) {
                        URL = "jdbc:odbc:" + testMachine.getDns();

                    } else if (testMachine.getTypeOdbc() == FrmTestMachine.TYPE_DBF) {

                        URL = "jdbc:odbc:" + "DRIVER={Microsoft Visual FoxPro Driver};SourceType=DBF;SourceDB=" + testMachine.getDns();
                    } else {
                        URL = "jdbc:odbc:" + testMachine.getDns();

                    }
                    Connection c = DriverManager.getConnection(URL, testMachine.getUser(), testMachine.getPassword());

                } catch (Exception exc) {
                    System.out.println("error koneksi"+exc);
                    rsCode = RSLT_UNKNOWN_ERROR;
                }

                break;

            case Command.GOTO:
                frmTestMachine.requestEntityObject(testMachine);
                if (testMachine.getCodeExsample() == FrmTestMachine.INSERT) {
                    testMachine.setSampleQuery("insert into nama_table value(nilai) WHERE field/isiTable = nilainya");
                } else if (testMachine.getCodeExsample() == FrmTestMachine.UPDATE) {
                    testMachine.setSampleQuery(" UPDATE tadat_2.dbf SET status_har=1 WHERE Idabs IN ('8008','8008')");
                } else if (testMachine.getCodeExsample() == FrmTestMachine.DELETE) {
                    testMachine.setSampleQuery("delete from nama_table WHERE field/isiTable = nilainya");
                } else {
                    testMachine.setSampleQuery("");
                }
                break;

            default:

        }
        return rsCode;
    }

   
}
