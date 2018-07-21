
/* Created on 	:  [date] [time] AM/PM 
 * 
 * @author  	: karya
 * @version  	: 01
 */

/*******************************************************************
 * Class Description 	: [project description ... ] 
 * Imput Parameters 	: [input parameter ...] 
 * Output 		: [output ...] 
 *******************************************************************/

package com.dimata.harisma.entity.masterdata; 
 
/* package java */ 
import java.util.Date;

/* package qdep */
import com.dimata.qdep.entity.*;
import java.util.Vector;

public class Evaluation extends Entity { 

	private String code = "";
	private String name = "";
	private String desription = "";
        private int maxPoint =0;
        private double maxPercentage = 0.0d;
        private long evalType =0;
        public final static int EVAL_TYPE_SELECT = 0;
        public final static int EVAL_TYPE_PERFORMANCE_RANGE = 1;
        public final static int EVAL_TYPE_BEHAVIOUR_LEVEL = 2;
        public final static int EVAL_TYPE_PREDICAT = 3;
        
        public final static String[][] EVAL_TYPE = { 
            {"-Pilih-","Skala Prestasi Kerja", "Tingkat Kompetensi Prilaku", "Skala Predikat/Yudicium"},
            {"-Select-","Work Performance Range","Behaviour Competent Level","Predicat Range"}            
           };
        public double getMaxPercentage(){
            return maxPercentage;
        }        

        public void setMaxPercentage(double maxPrc ){
            maxPercentage = maxPrc;
        }
        
        
	public String getCode(){ 
		return code; 
	} 

	public void setCode(String code){ 
		if ( code == null ) {
			code = ""; 
		} 
		this.code = code; 
	} 

	public String getName(){ 
		return name; 
	} 

	public void setName(String name){ 
		if ( name == null ) {
			name = ""; 
		} 
		this.name = name; 
	} 

	public String getDesription(){ 
		return desription; 
	} 

	public void setDesription(String desription){ 
		if ( desription == null ) {
			desription = ""; 
		} 
		this.desription = desription; 
	} 

    /**
     * @return the evalType
     */
    public long getEvalType() {
        return evalType;
    }

    /**
     * @param evalType the evalType to set
     */
    public void setEvalType(long evalType) {
        this.evalType = evalType;
    }

    /**
     * @return the maxPoint
     */
    public int getMaxPoint() {
        return maxPoint;
    }

    /**
     * @param maxPoint the maxPoint to set
     */
    public void setMaxPoint(int maxPoint) {
        this.maxPoint = maxPoint;
    }
    
    /**
     * check point convertion jika list vEvaluations mengandung ranges yhang ada % maka akan diconvert menjadi point sesuai range  %
     * jika tidak maka akan dilakukan point convertion 
     * @param realization
     * @param vEvaluations
     * @return 
     */
    public static float checkPoint(float realization, Vector<Evaluation> vEvaluations){        
        if( vEvaluations !=null && vEvaluations.size()>0 ){
            double prevValueMax = -1.0f;
            for(int i=0;i < vEvaluations.size();i++ ){
                Evaluation eval = (Evaluation) vEvaluations.get(i);
                if(eval.getMaxPercentage()>0.0f){ // check for percentage
                 if( (prevValueMax < realization &&  realization<=eval.getMaxPercentage()) ||
                    (i==(vEvaluations.size()-1) && (realization>prevValueMax)) ){
                    return eval.getMaxPoint();
                 }
                 prevValueMax = eval.getMaxPercentage();
                }else{                          // check for point converting can be also x to x
                  if( (prevValueMax < realization &&  realization<=eval.getMaxPoint()) ||
                    (i==(vEvaluations.size()-1) && (realization>prevValueMax)) ){
                    return eval.getMaxPoint();
                   }                   
                }
            }
        }                 
        return 0.0f;
    }

}
