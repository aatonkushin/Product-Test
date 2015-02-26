/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package producttest.model;

/**
 *
 * @author tonkushin
 */
public class Year {
    private int returnValue;
    private String displayValue;
    
    public Year(){
        
    }
    
    public Year(int returnValue, String displayValue){
        this.returnValue = returnValue;
        this.displayValue = displayValue;
    }

    /**
     * @return the returnValue
     */
    public int getReturnValue() {
        return returnValue;
    }

    /**
     * @param returnValue the returnValue to set
     */
    public void setReturnValue(int returnValue) {
        this.returnValue = returnValue;
    }

    /**
     * @return the dispalyValue
     */
    public String getDisplayValue() {
        return displayValue;
    }

    /**
     * @param dispalyValue the dispalyValue to set
     */
    public void setDisplayValue(String dispalyValue) {
        this.displayValue = dispalyValue;
    }
    
    public String toString(){
        if (displayValue != null) {
            return displayValue;
        } else return "";
    }
}
