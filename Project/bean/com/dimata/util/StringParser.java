/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dimata.util;

/**
 *
 * @author Ketut Kartika T
 * @date 12 Feb 2008
 * @uses : function to parse strings in various combination
 */

import java.util.Hashtable;
import java.util.Vector;

public class StringParser {
    
    /**  parsing satu string ke group dan bagian yang ada contoh :  2004=226;2001=2002=2003
     *   tanda ";" adalah pemisah group ; tanda "=" pemisah bagian pada satu group
     **/
    public static Vector parseGroup(String str, String groupSeparator, String contentSeparator){
        Vector v = new Vector();
        try{
        if((str==null) || (str.length()<1))
                return  v;
        
        if(groupSeparator==null)
                groupSeparator="";
        if(contentSeparator==null)
                contentSeparator="";
        
        String[] strGroup=str.split(groupSeparator);
        if(strGroup!=null){
            for(int i=0;i<strGroup.length;i++){
                String aGroup = strGroup[i];
                if(aGroup!=null){
                    String[] comps = aGroup.split(contentSeparator);
                    if(comps!=null)
                        v.add(comps);
                }
            }
            
        }                
        } catch(Exception exc){
            v.add(exc.toString());
        }
        return v;
    }
    /**
     * create by satrya 2014-04-16
     * Keterangan: untuk mengambil nialai dari parameter yg di split
     * @param str
     * @param groupSeparator
     * @param contentSeparator
     * @return 
     */
    public static Hashtable<String,String> parseGroupVer2(String str, String groupSeparator, String contentSeparator){
        //Vector v = new Vector();
        Hashtable hashList = new Hashtable();
        try{
        if((str==null) || (str.length()<1))
                return  hashList;
        
        if(groupSeparator==null)
                groupSeparator="";
        if(contentSeparator==null)
                contentSeparator="";
        
        String[] strGroup=str.split(groupSeparator);
        if(strGroup!=null){
            for(int i=0;i<strGroup.length;i++){
                String aGroup = strGroup[i];
                if(aGroup!=null && aGroup.length()>0){
                    String[] comps = aGroup.split(contentSeparator);
                    if(comps!=null && comps.length==2)
                        //v.add(comps);
                        hashList.put((String)comps[0], (String)comps[1]);
                }
            }
            
        }                
        } catch(Exception exc){
            hashList = new Hashtable();
        }
        return hashList;
    }
    
    /**  parsing satu string ke group dan bagian yang ada contoh :  2004=226/2001=2002=2003
     *   tanda "/" adalah pemisah group ; tanda "=" pemisah bagian pada satu group
     *  default pemisah yang dipergunakan adalah /
     **/
    public static Vector parseGroup(String str){
        return parseGroup(str.trim(), "/","=");
    }
    
    /**
     * create By satrya 2014-04-16
     * Keterangan: untuk mengambil nialai dari parameter yg di split
     * @param str
     * @return 
     */
     public static Hashtable<String,String> parseGroupVer2(String str){
        return parseGroupVer2(str.trim(), "/","=");
    }
    

    public static void main(String[] args){
        String str="2004=2007/2001=2002=2003";
        Vector v=StringParser.parseGroup(str, "/", "=");
        for(int i=0;i< v.size();i++){
            System.out.println("grp"+i);
            String[] grp= (String[]) (v.get(i));
            for(int g=0;g<grp.length;g++){
                System.out.println(grp[g]);
            }
        }
        
        System.out.println(v.toString());        
        System.out.println("END");
    }
    
}
