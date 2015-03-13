/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package producttest.model;

import java.util.Date;

/**
 * Класс, представляющий запись в таблице: Отчёт по готовой продукции.
 * @author tonkushin
 */
public class ProductReportRecord {
    private int id;                 //Код
    private String partNum;         //Номер партии
    private Date date;              //Дата
    private int autoclaveNo;        //Номер автоклава
    private int productId;          //Код продукции
    private String productName;     //Название продукции
    private int cakeQuantity;       //Кол-во массивов в партии
    private float volume;           //Объём партии
    private int defTypeId;          //Вид дефекта
    private String defName;         //Наименование дефекта
    private int defRate;            //Интенсивность дефекта
    private float humidity;         //Влажность
    private float avgDensity;       //Средняя плотность
    private String densityMark;     //Марка по плотности
    private float avgDurability;    //Средняя прочность
    private String durabilityMark;  //Марка по прочности
    private String frostResist;     //Морозостойкость
    private String activity;        //Удельная активность
    private float heatConduction;   //Теплопроводность
    private float steamFactor;      //Паропроницаемость
    private float shrinkage;        //Усадка при высыхании

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the partNum
     */
    public String getPartNum() {
        return partNum;
    }

    /**
     * @param partNum the partNum to set
     */
    public void setPartNum(String partNum) {
        this.partNum = partNum;
    }

    /**
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * @return the autoclaveNo
     */
    public int getAutoclaveNo() {
        return autoclaveNo;
    }

    /**
     * @param autoclaveNo the autoclaveNo to set
     */
    public void setAutoclaveNo(int autoclaveNo) {
        this.autoclaveNo = autoclaveNo;
    }

    /**
     * @return the productId
     */
    public int getProductId() {
        return productId;
    }

    /**
     * @param productId the productId to set
     */
    public void setProductId(int productId) {
        this.productId = productId;
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
     * @return the cakeQuantity
     */
    public int getCakeQuantity() {
        return cakeQuantity;
    }

    /**
     * @param cakeQuantity the cakeQuantity to set
     */
    public void setCakeQuantity(int cakeQuantity) {
        this.cakeQuantity = cakeQuantity;
    }

    /**
     * @return the volume
     */
    public float getVolume() {
        return volume;
    }

    /**
     * @param volume the volume to set
     */
    public void setVolume(float volume) {
        this.volume = volume;
    }

    /**
     * @return the defTypeId
     */
    public int getDefTypeId() {
        return defTypeId;
    }

    /**
     * @param defTypeId the defTypeId to set
     */
    public void setDefTypeId(int defTypeId) {
        this.defTypeId = defTypeId;
    }

    /**
     * @return the defName
     */
    public String getDefName() {
        return defName;
    }

    /**
     * @param defName the defName to set
     */
    public void setDefName(String defName) {
        this.defName = defName;
    }

    /**
     * @return the defRate
     */
    public int getDefRate() {
        return defRate;
    }

    /**
     * @param defRate the defRate to set
     */
    public void setDefRate(int defRate) {
        this.defRate = defRate;
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
     * @return the avgDensity
     */
    public float getAvgDensity() {
        return avgDensity;
    }

    /**
     * @param avgDensity the avgDensity to set
     */
    public void setAvgDensity(float avgDensity) {
        this.avgDensity = avgDensity;
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
     * @return the frostResist
     */
    public String getFrostResist() {
        return frostResist;
    }

    /**
     * @param frostResist the frostResist to set
     */
    public void setFrostResist(String frostResist) {
        this.frostResist = frostResist;
    }

    /**
     * @return the activity
     */
    public String getActivity() {
        return activity;
    }

    /**
     * @param activity the activity to set
     */
    public void setActivity(String activity) {
        this.activity = activity;
    }

    /**
     * @return the heatConduction
     */
    public float getHeatConduction() {
        return heatConduction;
    }

    /**
     * @param heatConduction the heatConduction to set
     */
    public void setHeatConduction(float heatConduction) {
        this.heatConduction = heatConduction;
    }

    /**
     * @return the steamFactor
     */
    public float getSteamFactor() {
        return steamFactor;
    }

    /**
     * @param steamFactor the steamFactor to set
     */
    public void setSteamFactor(float steamFactor) {
        this.steamFactor = steamFactor;
    }

    /**
     * @return the shrinkage
     */
    public float getShrinkage() {
        return shrinkage;
    }

    /**
     * @param shrinkage the shrinkage to set
     */
    public void setShrinkage(float shrinkage) {
        this.shrinkage = shrinkage;
    }
}
