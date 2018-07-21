/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dimata.gui.jsp;

/**
 *
 * @author wirka
 */
public class DrsControlText {
    /**
      * to draw a input text but no user can input a value to the field like registration date
      * @param caption
      * @param nameText
      * @param initVal
      * @param size
      * @param maxChar
      * @param style
      * @return
      */
     public static String drawTextWithoutInput(String caption, String initVal) {
        return drawTextWithoutInput(caption, "15%", initVal);
    }
     
     /**
      * to draw a input text but no user can input a value to the field like registration date
      * @param caption
      * @param nameText
      * @param initVal
      * @param size
      * @param maxChar
      * @param style
      * @return
      */
     public static String drawTextWithoutInput(String caption, String CaptionWidth, String initVal) {
        String text="<table border=\"0\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\"><tr>\n<td width=\""+CaptionWidth+"\">\n<label>"+caption+"</label>";
        text+="\n</td><td>";        
        if (initVal != null) {
            text+= initVal;
        } 
        text+="</td></tr></table>";
        return text;
    }
      /**
      * to draw input text like follow
      * <code><label for="labelId">caption</label><br>
      * <input type"text" class="style" id="labeld" value="initValue" name="nameText" length="length" maxLength="maxChar">
      * @param caption
      * @param nameText
      * @param initVal
      * @param size
      * @param maxChar
      * @param style
      * @return
      */
     public static String drawPasswordWithStyleLabel(String caption, String nameText, String initVal, int size, int maxChar, String style) {
        String labelId=nameText;
        String sz = "";
        String mc = "";
        String text="<table border=\"0\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\"><tr>\n<td width=\"15%\">";
        if(caption==null)
            caption="Input Data";
        
        if(labelId!=null && labelId.length()>0){
            text+="<label for=\""+labelId+"\">"+caption+"</label>";
        }
        else{
            labelId="";
            text+=caption;        
        }
        text+="\n</td><td>";        
        
        if (nameText == null) {
            nameText = new String("txt_input");
        }

        if (size > 0) {
            sz = String.valueOf(size);
        }
        if (maxChar > 0) {
            mc = String.valueOf(maxChar);
        }
        if (initVal == null) {
            text+= "<input id=\""+labelId+"\" type=\"password\" name=\"" + nameText + "\" size=\"" + sz + "\" maxlength=\"" + mc + "\" class=\"" + style + "\">\n";
        } else {
            text+= "<input id=\""+labelId+"\" type=\"password\" value=\"" + initVal + "\" name=\"" + nameText + "\" size=\"" + sz + "\" maxlength=\"" + mc + "\" class=\"" + style + "\">\n";
        }
        text+="</td></tr></table>";
        return text;
    }
     
     /**
      * to draw input text like follow
      <table><tr><td><label for="labelId">caption</label></td><td>
      * <input type"text" class="style" id="labeld" value="initValue" name="nameText" length="length" maxLength="maxChar" addStyle>addedStyle</code></td></tr></table>
      * @param caption
      * @param nameText
      * @param initVal
      * @param size
      * @param maxChar
      * @param style
       * @param addStyle
       * @param addedStyle 
      * @return
      */
     public static String drawTextWithStyleLabel(String caption, String nameText, String initVal, int size, int maxChar, String style, String addStyle, String addedComp) {
         return drawTextWithStyleLabel(caption, "15%",nameText, initVal, size, maxChar, style, addStyle, addedComp);
     }
     
      /**
      * to draw input text like follow
      <table><tr><td><label for="labelId">caption</label></td><td>
      * <input type"text" class="style" id="labeld" value="initValue" name="nameText" length="length" maxLength="maxChar" addStyle>addedStyle</code></td></tr></table>
      * @param caption
       * @param widthCaption 
      * @param nameText
      * @param initVal
      * @param size
      * @param maxChar
      * @param style
       * @param addStyle
       * @param addedStyle 
      * @return
      */
     public static String drawTextWithStyleLabel(String caption,String widthCaption, String nameText, String initVal, int size, int maxChar, String style, String addStyle, String addedComp) {
            return drawTextWithStyleLabel("",caption, widthCaption ,nameText, initVal, size, maxChar, style, false, addStyle, addedComp);
     }
     
     /**
      * to draw input text like follow
      * <code><table><tr><td><label for="labelId">caption</label></td><td>
      * <input type"text" class="style" id="labeld" value="initValue" name="nameText" length="length" maxLength="maxChar" addStyle>addedStyle</code></td></tr></table></code>
      * if tr is true, return like this.
      * <table><tr><td><label for="labelId">caption</label></td></tr><tr><td>
      * <input type"text" class="style" id="labeld" value="initValue" name="nameText" length="length" maxLength="maxChar" addStyle>addedStyle</code></td></tr></table>
      * @param align
      * @param caption
       * @param widthCaption 
      * @param nameText
      * @param initVal
      * @param size
      * @param maxChar
      * @param style
       * @param addStyle
       * @param addedStyle 
      * @return
      */
     public static String drawTextWithStyleLabel(String align, String caption,String widthCaption, String nameText, String initVal, int size, int maxChar, String style,boolean tr, String addStyle, String addedComp) 
     {
        String labelId=nameText;
        String sz = "";
        String mc = "";
        String width="";
        String alignSlash="";
        if (widthCaption!=null || widthCaption.length()>0)
            width=" width=\""+widthCaption+"\"";
        if(align!=null&&align.length()>0){
            align="<div align=\""+align+"\">";
            alignSlash="</div>";
        }
        String text="<table border=\"0\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\"><tr>\n<td "+width+">"+align;
        if(caption==null)
            caption="Input Data";        
        if(labelId!=null && labelId.length()>0){
            text+="<label for=\""+labelId+"\">"+caption+"</label>";
        }
        else{
            labelId="";
            text+=caption;        
        }
        text+=alignSlash+"\n</td>"+(tr?"</tr><tr><td>":"<td>")+align;        
        
        if (nameText == null) {
            nameText = new String("txt_input");
        }

        if (size > 0) {
            sz = String.valueOf(size);
        }
        if (maxChar > 0) {
            mc = String.valueOf(maxChar);
        }
        if (initVal == null) {
            text+= "<input id=\""+labelId+"\" type=\"text\" name=\"" + nameText + "\" size=\"" + sz + "\" maxlength=\"" + mc + "\" class=\"" + style + "\" "+addStyle+">"+addedComp+"\n";
        } else {
            text+= "<input id=\""+labelId+"\" type=\"text\" value=\"" + initVal + "\" name=\"" + nameText + "\" size=\"" + sz + "\" maxlength=\"" + mc + "\" class=\"" + style + "\" "+addStyle+">"+addedComp+"\n";
        }
        text+=alignSlash+"</td></tr></table>";
        return text;
    }
     
     public static String drawTextOnly(String name, String initValue, int size, int maxChar, String style, String addStyle){
         String text="";
         if (initValue == null) {
            text+= "<input type=\"text\" name=\"" + name + "\" size=\"" + size + "\" maxlength=\"" + maxChar + "\" class=\"" + style + "\" "+addStyle+" />\n";
         } else {
            text+= "<input type=\"text\" value=\"" + initValue + "\" name=\"" + name + "\" size=\"" + size + "\" maxlength=\"" + maxChar + "\" class=\"" + style + "\" "+addStyle+" />\n";
         }
         return text;
     }
     
     public static String drawTextOnly(String name, String initValue, int size, int maxChar, String style){
         return drawTextOnly(name, initValue, size, maxChar, style, "");
     }
     /**
      * to draw input text like follow
      <table><tr><td><label for="labelId">caption</label></td><td>
      * <input type"text" class="style" id="labeld" value="initValue" name="nameText" length="length" maxLength="maxChar" addStyle>addedStyle</code></td></tr></table>
      * @param caption
      * @param nameText
      * @param initVal
      * @param size
      * @param maxChar
      * @param style
      * @return
      */
     public static String drawTextWithStyleLabel(String caption, String nameText, String initVal, int size, int maxChar, String style) {
        return drawTextWithStyleLabel(caption, "15%",nameText, initVal, size, maxChar, style, "", "");
        /* String labelId=nameText;
        String sz = "";
        String mc = "";
        String text="<table border=\"0\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\"><tr>\n<td width=\"15%\">";
        if(caption==null)
            caption="Input Data";
        
        if(labelId!=null && labelId.length()>0){
            text+="<label for=\""+labelId+"\">"+caption+"</label>";
        }
        else{
            labelId="";
            text+=caption;        
        }
        text+="\n</td><td>";        
        
        if (nameText == null) {
            nameText = new String("txt_input");
        }

        if (size > 0) {
            sz = String.valueOf(size);
        }
        if (maxChar > 0) {
            mc = String.valueOf(maxChar);
        }
        if (initVal == null) {
            text+= "<input id=\""+labelId+"\" type=\"text\" name=\"" + nameText + "\" size=\"" + sz + "\" maxlength=\"" + mc + "\" class=\"" + style + "\">\n";
        } else {
            text+= "<input id=\""+labelId+"\" type=\"text\" value=\"" + initVal + "\" name=\"" + nameText + "\" size=\"" + sz + "\" maxlength=\"" + mc + "\" class=\"" + style + "\">\n";
        }
        text+="</td></tr></table>";
        return text;*/
    }
    
     /**
      * to draw input text like follow
      * <code><label for="labelId">caption</label><br>
      * <input type"text" class="style" id="labeld" value="initValue" name="nameText" length="length" maxLength="maxChar">
      * @param lableId
      * @param caption
      * @param nameText
      * @param initVal
      * @param size
      * @param maxChar
      * @param style
      * @return
      */
     public static String drawTextAreaWithStyleLabel(String labelId, String caption, String nameText, String initVal, int row, int col, String style) {
        return drawTextAreaWithStyleLabel(labelId, caption, "15%", nameText, initVal, row, col, style, false);
     }
     /**
      * to draw input text like follow
      * <code><label for="labelId">caption</label><br>
      * <input type"text" class="style" id="labeld" value="initValue" name="nameText" length="length" maxLength="maxChar">
      * @param lableId
      * @param caption
      * @param nameText
      * @param initVal
      * @param size
      * @param maxChar
      * @param style
      * @return
      */
     public static String drawTextAreaWithStyleLabel(String labelId, String caption,String captionWidth, String nameText, String initVal, int row, int col, String style,boolean tr) {
        
        String sz = "";
        String mc = "";
        String text="<table border=\"0\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\"><tr>\n<td width=\""+captionWidth+"\">";
        if(caption==null)
            caption="Input Data";
        
        if(labelId!=null && labelId.length()>0){
            text+="<label for=\""+labelId+"\">"+caption+"</label>";
        }
        else{
            labelId="";
            text+=caption;        
        }
        text+="\n</td>"+((tr)?"</tr><tr>":"")+"<td>";        
        
        if(nameText == null) 
            nameText = new String("lntxt_input");
        String cl = "";
        String rw = "";

        if(col > 0) cl = String.valueOf(col);
        if(row > 0) rw = String.valueOf(row);
        //<textarea name="textfield" cols="12" rows="12"></textarea>
        if(initVal == null)
            text+= "<textarea id=\""+labelId+"\" name=\"" + nameText + "\" cols=\""+ cl +"\" rows=\""+ rw +"\" class=\""+style+"\">\n</textarea>";
        else
            text+= "<textarea  id=\""+labelId+"\" name=\"" + nameText + "\" cols=\""+ cl +"\" rows=\""+ rw +"\" class=\""+style+"\">\n"+ initVal +"</textarea>";
        text+="</td></tr></table>";
        return text;
    }

}
