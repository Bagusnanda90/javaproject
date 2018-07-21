/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.attendance;

import java.util.Vector;

/**
 *
 * @author Satrya
 */
public class LeaveApplicationSummary {
    private long employeeId;
    private Vector symbol= new Vector();
    private Vector jumlahTaken= new Vector();
    //private Vector symbolAl=new Vector();
    //private Vector symbolLL=new Vector();
    //private Vector symbolDp=new Vector();
    
//    public void addSymbolAl(String symbol) {
//        if(symbol!=null && symbol.length()>0){
//            this.symbolAl.add(symbol);
//        }
//        
//    }
//    
//    public void addSymbolLL(String symbol) {
//        if(symbol!=null && symbol.length()>0){
//            this.symbolLL.add(symbol);
//        }
//        
//    }
//    
//     public void addSymbolDp(String symbol) {
//        if(symbol!=null && symbol.length()>0){
//            this.symbolDp.add(symbol);
//        }
//        
//    }
    public int getSizeSymbol(){
        return symbol.size();
    }
    
    public Vector getVsymbol(){
        return symbol;
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
     * @return the symbol
     */
    public String getSymbol(int idx) {
        String sSymbol="";
        if(symbol!=null && symbol.size()>0){
            sSymbol = (String)symbol.get(idx);
        }
        return sSymbol;
    }

    /**
     * @param symbol the symbol to set
     */
    public void addSymbol(String symbol) {
        if(symbol!=null && symbol.length()>0){
            this.symbol.add(symbol);
        }
        
    }

    /**
     * @return the jumlahTaken
     */
    public double getJumlahTaken(int idx) {
         double dJumlahTaken=0;
        if(getJumlahTaken()!=null && getJumlahTaken().size()>0){
            dJumlahTaken = (Double)getJumlahTaken().get(idx);
        }
        return dJumlahTaken;
        //return jumlahTaken;
    }

    /**
     * @param jumlahTaken the jumlahTaken to set
     */
    public void addJumlahTaken(double jumlahTaken) {
        this.getJumlahTaken().add(jumlahTaken);
    }

    /**
     * @return the jumlahTaken
     */
    public Vector getJumlahTaken() {
        return jumlahTaken;
    }

    /**
     * @param jumlahTaken the jumlahTaken to set
     */
    public void setJumlahTaken(Vector jumlahTaken) {
        this.jumlahTaken = jumlahTaken;
    }
}
