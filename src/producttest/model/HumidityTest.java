/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package producttest.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

/**
 *
 * @author tonkushin
 */
public class HumidityTest {

    private int id;
    private int bottleNum;          //Номер бюкса.
    private Double bottleWeight;    //масса бюкса.
    private Double wetWeight;       //масса навески.
    private Double dryWeight;       //масса после сушки.
    private DoubleProperty humidity;        //влажность, %

    public final Double getHumidity() {
        return humidity.get();
    }
    public final void setHumidity(Double humidity) {
        this.humidity.set(humidity);
    }
    public DoubleProperty humidityProperty() {
        return humidity;
    }

    
    public int getId(){
        return id;
    }
    
    public void setId(int id){
        this.id = id;
    }
    
    /**
     * @return the bottleNum
     */
    public int getBottleNum() {
        return bottleNum;
    }

    /**
     * @param bottleNum the bottleNum to set
     */
    public void setBottleNum(int bottleNum) {
        this.bottleNum = bottleNum;
    }

    /**
     * @return the bottleWeight
     */
    public Double getBottleWeight() {
        return bottleWeight;
    }

    /**
     * @param bottleWeight the bottleWeight to set
     */
    public void setBottleWeight(Double bottleWeight) {
        this.bottleWeight = bottleWeight;
        CalculateHumidity();
    }

    /**
     * @return the wetWeight
     */
    public Double getWetWeight() {
        return wetWeight;
    }

    /**
     * @param wetWeight the wetWeight to set
     */
    public void setWetWeight(Double wetWeight) {
        this.wetWeight = wetWeight;
        CalculateHumidity();
    }

    /**
     * @return the dryWeight
     */
    public Double getDryWeight() {
        return dryWeight;
    }

    /**
     * @param dryWeight the dryWeight to set
     */
    public void setDryWeight(Double dryWeight) {
        this.dryWeight = dryWeight;
        CalculateHumidity();
    }

    /**
     * Класс, представляющий один тест на влажность. Конструктор по-умолчанию.
     */
    public HumidityTest() {
        this.bottleNum = 0;
        this.bottleWeight = 0d;
        this.dryWeight = 0d;
        this.wetWeight = 0d;
        humidity = new SimpleDoubleProperty(0d);
    }

    /**
     * Класс, представляющий один тест на влажность.
     *
     * @param bottleNum - номер бюкса
     * @param bottleWeight - масса бюкса
     * @param dryWeight - вес в сухом состоянии
     * @param wetWeight - вес во влажном
     */
    public HumidityTest(int bottleNum, Double bottleWeight,
            Double dryWeight, Double wetWeight) {
    }

    /**
     * Расчёт влажности.
     */
    private void CalculateHumidity() {
        if (dryWeight != 0 && bottleWeight != 0 && !dryWeight.equals(bottleWeight)) {
            Double numerator, denumerator;

            numerator = wetWeight - (dryWeight-bottleWeight);   //
            denumerator = dryWeight - bottleWeight;         //m сух.
            Double result = (numerator / denumerator)*100;
            
            long factor = (long) Math.pow(10, 2);
            result = result * factor;
            long tmp = Math.round(result);
            setHumidity((double) tmp / factor);
        }
    }
}
