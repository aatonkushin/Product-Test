/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package producttest.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import producttest.bll.Calculation;

/**
 * Класс, представляющий запись об испытании образца.
 * @author tonkushin
 */
public class SampleTest extends Object{
    
    // <editor-fold defaultstate="collapsed" desc=" Поля, геттеры, сеттеры ">

    private int id;
    private int sampleNum;  //Номер образца.
    private Double width;   //ширина.
    private Double height;  //высота.
    private Double length;  //длина.
    private Double weight;  //вес.
    //private Double destructLoad; //Разрушающая нагрузка.
    
    //Влажность в сыром состоянии.
    private DoubleProperty wetDensity = new SimpleDoubleProperty();
    public final Double getWetDensity() {
        return this.wetDensity.get();
    }
    public final void setWetDensity(Double wd) {
        this.wetDensity.set(wd);
    }
    public DoubleProperty wetDensityProperty() {
        return this.wetDensity;
    }
    
    //Разрушающая нагрузка P.
    private DoubleProperty destructLoad = new SimpleDoubleProperty(0);
    public final Double getDestructLoad(){
        return this.destructLoad.get();
    }
    public final void setDestructLoad(Double destructLoad){
        this.destructLoad.set(destructLoad);
    }
    public DoubleProperty destructLoadProperty(){
        return this.destructLoad;
    }
    
    //Расположение образца в массиве.
    private StringProperty position = new SimpleStringProperty();
    public final String getPosition(){
        return this.position.get();
    }
    public final void setPosition(String pos){
        this.position.set(pos);
    }
    public StringProperty positionProperty(){
        return this.position;
    }
    
    public int getId(){
        return this.id;
    }
    public void setId(int id){
        this.id = id;
    }
    
    /**
     * @return the sampleNum
     */
    public int getSampleNum() {
        return sampleNum;
    }

    /**
     * @param sampleNum the sampleNum to set
     */
    public void setSampleNum(int sampleNum) {
        this.sampleNum = sampleNum;
    }

    /**
     * @return the width
     */
    public Double getWidth() {
        return width;
    }

    /**
     * @param width the width to set
     */
    public void setWidth(Double width) {
        this.width = width;
        setWetDensity(Calculation.calcWetDensity(width, height, length, weight));
    }

    /**
     * @return the height
     */
    public Double getHeight() {
        return height;
    }

    /**
     * @param height the height to set
     */
    public void setHeight(Double height) {
        this.height = height;
         setWetDensity(Calculation.calcWetDensity(width, height, length, weight));
    }

    /**
     * @return the length
     */
    public Double getLength() {
        return length;
    }

    /**
     * @param length the length to set
     */
    public void setLength(Double length) {
        this.length = length;
        setWetDensity(Calculation.calcWetDensity(width, height, length, weight));
    }

    /**
     * @return the weight
     */
    public Double getWeight() {
        return weight;
    }

    /**
     * @param weight the weight to set
     */
    public void setWeight(Double weight) {
        this.weight = weight;
         setWetDensity(Calculation.calcWetDensity(width, height, length, weight));
    }
// </editor-fold>
    
    public SampleTest()
    {
        this.position.set("");
        this.width = 0d;
        this.length = 0d;
        this.height = 0d;
        this.weight = 0d;
        this.destructLoad.set(0d);
    }
    
    /**
     *
     * @param num - номер образца
     * @param position - расположение образца в массиве
     * @param width - ширина
     * @param length - длина
     * @param height - высота
     * @param weight - масса образца
     * @param destructLoad - разрушающая нагрузка.
     */
    public SampleTest(int num, String position, Double width, Double length, Double height, Double weight, Double destructLoad){
        this.sampleNum = num;
        
        this.position.set(position);
        this.width = width;
        this.length = length;
        this.height = height;
        this.weight = weight;
        this.destructLoad.set(destructLoad);
    }
    
    /**
     * Рассчитывает плотность в сыром состоянии.
     */
 /*   private void CalculateWetDensity(){
        if (width == 0 || height == 0 || length ==0 || weight == 0){
            return;
        }
        
        Double result = (weight / (length*width*height))*1000;
        result = BLogic.round(result, 2);
        this.setWetDensity(result);
    }*/
}
