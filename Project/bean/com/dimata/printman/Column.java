/* Generated by Together */

package com.dimata.printman;

public class Column extends DSJ_PrintObj{
    /*private DSJ_PrintObj obj = new DSJ_PrintObj();

    //create new laine blank before add value from project
    public Column(int maxCol, String text){
        text = text.substring(0, 1);
        int intCpi = getCharacterSelected();
        int minCols = getLeftMargin() + getRightMargin();
        int line = 0;
		if(lines!=null && lines.size()>0)
        	line = lines.size()+1;
        else
            line = 0;

        newLine(line,intCpi);
        int chr = ((intCpi-minCols)/ maxCol);
        int intMnd = (intCpi-minCols) % maxCol;
        int start = getLeftMargin()-1;
        this.arrayCols = new float[maxCol+1];
		for (int k = 0; k <= maxCol; ++k) {
            if(k==maxCol){
	            setLine(line,start+intMnd,text);
				arrayCols[k] = start+intMnd;
            }else{
	            setLine(line,start+1,text);
				arrayCols[k] = start+1;
            }
            start = start + chr;
		}
        System.out.println(lines.get(line));
    }

    public void setColumnValue(int idx ,int line, String text, int pos){
        try{
            if(lines.size()>0){
                if(lines.size()<=line)
                    newLine(line,getCharacterSelected());

	            String val1 = String.valueOf(arrayCols[idx]);
				int stCol = Integer.parseInt(val1.substring(0,val1.length()-2));
	            val1 = String.valueOf(arrayCols[idx+1]);
	            int endCol = Integer.parseInt(val1.substring(0,val1.length()-2));
				endCol = endCol - stCol -2;
	
	            switch(pos){
	            	case TEXT_LEFT:
	                    setLine(line,stCol+1,text,endCol+1);
	                    break;
	                case TEXT_CENTER:
	                     setLineCenterAlign(line,stCol+1,endCol+1,text);
	                    break;
	                case TEXT_RIGHT:
	                    setLineRightAlign(line,stCol+1, text, endCol+1);
	                    break;
	                default:
	            }
            }
		}catch(Exception e){
        	System.out.println("ERR : setColumnValue "+e.toString());
        }
    }


    public int getCharacterSelected(){
        int intCpi = cpiChar[PRINTER_10_CPI];
		switch(getCpiIndex()){
        	case PRINTER_10_CPI:
                intCpi = cpiChar[PRINTER_10_CPI];
                break;
        	case PRINTER_12_CPI:
				intCpi = cpiChar[PRINTER_12_CPI];
                break;
        	case PRINTER_CONDENSED_MODE:
                intCpi = cpiChar[PRINTER_CONDENSED_MODE];
                break;
            default:
        }
        return intCpi;
    }*/
}
