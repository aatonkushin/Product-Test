/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package producttest.model;

/**
 * Класс, представляющий запись в таблице Статистика испытаний ГП.
 * @author tonkushin
 */
public class Stat {
    private String testDate;            //Дата испытаний
    private String partNo;              //№ партии
    private String productName;         //Наименование
    private float humidity;             //Влажность
    private float reqDensity;           //Требуемая плотность
    private float reqDurability;        //Требуемая прочность
    private float avgWetDensity;        //Плотность влажн.
    private float avgDryDensity;        //Плотность сух.
    private String densityMark;         //Марка по плотности
    private float densityVariation;     //Коэф. вариации по плотности
    private float avgDurability;        //Предел прочности при сжатии
    private String durabilityMark;      //Класс по прочности
    private float durabilityVariation;  //Коэф. вариации по прочности
    private String personName;          //Сотрудник

    /**
     * @return the testDate
     */
    public String getTestDate() {
        return testDate;
    }

    /**
     * @param testDate the testDate to set
     */
    public void setTestDate(String testDate) {
        this.testDate = testDate;
    }

    /**
     * @return the partNo
     */
    public String getPartNo() {
        return partNo;
    }

    /**
     * @param partNo the partNo to set
     */
    public void setPartNo(String partNo) {
        this.partNo = partNo;
    }

    /**
     * @return the productName
     */
    public String getProductName() {
        return productName;
    }

    /**
     * @param productName the productName to set
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }

    /**
     * @return the humidity
     */
    public float getHumidity() {
        return humidity;
    }

    /**
     * @param humidity the humidity to set
     */
    public void setHumidity(float humidity) {
        this.humidity = humidity;
    }

    /**
     * @return the reqDensity
     */
    public float getReqDensity() {
        return reqDensity;
    }

    /**
     * @param reqDensity the reqDensity to set
     */
    public void setReqDensity(float reqDensity) {
        this.reqDensity = reqDensity;
    }

    /**
     * @return the reqDurability
     */
    public float getReqDurability() {
        return reqDurability;
    }

    /**
     * @param reqDurability the reqDurability to set
     */
    public void setReqDurability(float reqDurability) {
        this.reqDurability = reqDurability;
    }

    /**
     * @return the avgWetDensity
     */
    public float getAvgWetDensity() {
        return avgWetDensity;
    }

    /**
     * @param avgWetDensity the avgWetDensity to set
     */
    public void setAvgWetDensity(float avgWetDensity) {
        this.avgWetDensity = avgWetDensity;
    }

    /**
     * @return the avgDryDensity
     */
    public float getAvgDryDensity() {
        return avgDryDensity;
    }

    /**
     * @param avgDryDensity the avgDryDensity to set
     */
    public void setAvgDryDensity(float avgDryDensity) {
        this.avgDryDensity = avgDryDensity;
    }

    /**
     * @return the densityMark
     */
    public String getDensityMark() {
        return densityMark;
    }

    /**
     * @param densityMark the densityMark to set
     */
    public void setDensityMark(String densityMark) {
        this.densityMark = densityMark;
    }

    /**
     * @return the densityVariation
     */
    public float getDensityVariation() {
        return densityVariation;
    }

    /**
     * @param densityVariation the densityVariation to set
     */
    public void setDensityVariation(float densityVariation) {
        this.densityVariation = densityVariation;
    }

    /**
     * @return the avgDurability
     */
    public float getAvgDurability() {
        return avgDurability;
    }

    /**
     * @param avgDurability the avgDurability to set
     */
    public void setAvgDurability(float avgDurability) {
        this.avgDurability = avgDurability;
    }

    /**
     * @return the durabilityMark
     */
    public String getDurabilityMark() {
        return durabilityMark;
    }

    /**
     * @param durabilityMark the durabilityMark to set
     */
    public void setDurabilityMark(String durabilityMark) {
        this.durabilityMark = durabilityMark;
    }

    /**
     * @return the durabilityVariation
     */
    public float getDurabilityVariation() {
        return durabilityVariation;
    }

    /**
     * @param durabilityVariation the durabilityVariation to set
     */
    public void setDurabilityVariation(float durabilityVariation) {
        this.durabilityVariation = durabilityVariation;
    }

    /**
     * @return the personName
     */
    public String getPersonName() {
        return personName;
    }

    /**
     * @param personName the personName to set
     */
    public void setPersonName(String personName) {
        this.personName = personName;
    }
}
