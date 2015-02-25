/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package producttest.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import producttest.bll.BLogic;

/**
 *
 * @author tonkushin
 */
public class Result {
    // <editor-fold defaultstate="collapsed" desc=" Поля, геттеры и сеттеры ">
    //Номер образца.
    private IntegerProperty sampleNum = new SimpleIntegerProperty();    
    /**
     * Номер образца.
     * @param sampleNum
     */
    public final void setSampleNum(int sampleNum){
        this.sampleNum.set(sampleNum);
    }
    /**
     * Номер образца
     * @return
     */
    public final int getSampleName(){
        return this.sampleNum.get();
    }
    public IntegerProperty sampleNumProperty(){
        return this.sampleNum;
    }
    
    //Плотность во влажном состоянии.
    private DoubleProperty wetDensity = new SimpleDoubleProperty();  
    /**
     * Плотность во влажном состоянии
     * @param wetDensity
     */
    public final void setWetDensity(Double wetDensity){
        this.wetDensity.set(wetDensity);
    }
    /**
     * Плотность во влажном состоянии
     * @return
     */
    public final Double getWetDensity(){
        return this.wetDensity.get();
    }
    /**
     *
     * @return
     */
    public DoubleProperty wetDensityProperty(){
        return this.wetDensity;
    }
    
    //Плотность в сухом состоянии.
    private DoubleProperty dryDensity = new SimpleDoubleProperty();
    /**
     * Плотность в сухом состоянии
     * @param dryDensity
     */
    public final void setDryDensity(Double dryDensity){
        this.dryDensity.set(dryDensity);
    }
    /**
     * Плотность в сухом состоянии
     * @return
     */
    public final Double getDryDensity(){
        return this.dryDensity.get();
    }
    public DoubleProperty dryDensityProperty(){
        return this.dryDensity;
    }
        
    private IntegerProperty densityMark = new SimpleIntegerProperty();
    private DoubleProperty densityVariation = new SimpleDoubleProperty();
    
    //Предел прочности при сжатии R.
    private DoubleProperty durability = new SimpleDoubleProperty();
    public final void setDurability(Double durability){
        this.durability.set(durability);
    }
    public final Double getDurability(){
        return this.durability.get();
    }
    public DoubleProperty durabilityProperty(){
        return this.durability;
    }
    
    private DoubleProperty durabilityMark = new SimpleDoubleProperty();
    private DoubleProperty durabilityVariation = new SimpleDoubleProperty();
// </editor-fold>

    /**
     * Конструктор класса по-умолчанию.
     */
    public Result(){}
    
    /**
     * Конструктор класса.
     * @param sampleNum - номер образца.
     */
    public Result(int sampleNum){
        this.sampleNum.set(sampleNum);
    }
    
    /**
     * Расчитывает плотность в сухом состоянии.
     * @param w - влажность.
     * @return плотность в сухом состоянии.
     */
  /*  public double CalculateDryDensity(Double w)
    {
        if (w != null && (1+(0.01d*w) != 0d)) {
            Double result = wetDensity.get() / (1+(0.01d*w));
        return BLogic.round(result, 2);
        }
        
        return 0;
    }*/
}
    
