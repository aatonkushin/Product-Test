/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package producttest.model;

import java.util.Date;

/**
 * Паспорт готовой продукции.
 * @author tonkushin
 */
public class ProductPassport {
    private int id;                     //Код
    private String partNum;             //Номер партии
    private Date date;                  //Дата паспорта
    private Product product;            //Продукция
    private Float avgDurability;        //Предел прочности при сжатии
    private Float reqDurability;        //Требуемая прочность
    private String durabilityMark;      //Клас прочности
    private Float avgDensity;           //Средняя плотность
    private Float steamFactor;          //Коэффициент паропроницаемости
    private String frostResist;         //Марка по морозостойкости
    private Float heatConduction;       //Коэффициент теплопроводности в сухом состоянии
    private Float shrinkage;            //Усадка при высыхании
    private String activity;             //Удельная эффективная активность
    private Float hymidity;             //Влажность
    private String notes;               //Примечание

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
     * @return the product
     */
    public Product getProduct() {
        return product;
    }

    /**
     * @param product the product to set
     */
    public void setProduct(Product product) {
        this.product = product;
    }

    /**
     * @return the avgDurability - Предел прочности при сжатии
     */
    public Float getAvgDurability() {
        return avgDurability;
    }

    /**
     * @param avgDurability - Предел прочности при сжатии
     */
    public void setAvgDurability(Float avgDurability) {
        this.avgDurability = avgDurability;
    }

    /**
     * @return the reqDurability - Требуемая прочность
     */
    public Float getReqDurability() {
        return reqDurability;
    }

    /**
     * @param reqDurability - Требуемая прочность
     */
    public void setReqDurability(Float reqDurability) {
        this.reqDurability = reqDurability;
    }

    /**
     * @return the durabilityMark - Клас прочности
     */
    public String getDurabilityMark() {
        return durabilityMark;
    }

    /**
     * @param durabilityMark - Клас прочности
     */
    public void setDurabilityMark(String durabilityMark) {
        this.durabilityMark = durabilityMark;
    }

    /**
     * @return the avgDensity - Средняя плотность
     */
    public Float getAvgDensity() {
        return avgDensity;
    }

    /**
     * @param avgDensity - Средняя плотность
     */
    public void setAvgDensity(Float avgDensity) {
        this.avgDensity = avgDensity;
    }

    /**
     * @return the steamFactor - Коэффициент паропроницаемости
     */
    public Float getSteamFactor() {
        return steamFactor;
    }

    /**
     * @param steamFactor - Коэффициент паропроницаемости
     */
    public void setSteamFactor(Float steamFactor) {
        this.steamFactor = steamFactor;
    }

    /**
     * @return the frostResist - Марка по морозостойкости
     */
    public String getFrostResist() {
        return frostResist;
    }

    /**
     * @param frostResist - Марка по морозостойкости
     */
    public void setFrostResist(String frostResist) {
        this.frostResist = frostResist;
    }

    /**
     * @return the heatConduction - Коэффициент теплопроводности в сухом состоянии.
     */
    public Float getHeatConduction() {
        return heatConduction;
    }

    /**
     * @param heatConduction - Коэффициент теплопроводности в сухом состоянии.
     */
    public void setHeatConduction(Float heatConduction) {
        this.heatConduction = heatConduction;
    }

    /**
     * @return the shrinkage - Усадка при высыхании
     */
    public Float getShrinkage() {
        return shrinkage;
    }

    /**
     * @param shrinkage - Усадка при высыхании
     */
    public void setShrinkage(Float shrinkage) {
        this.shrinkage = shrinkage;
    }

    /**
     * @return the activity - Удельная эффективная активность
     */
    public String getActivity() {
        return activity;
    }

    /**
     * @param activity - Удельная эффективная активность
     */
    public void setActivity(String activity) {
        this.activity = activity;
    }

    /**
     * @return the hymidity - Влажность
     */
    public Float getHymidity() {
        return hymidity;
    }

    /**
     * @param hymidity - Влажность
     */
    public void setHymidity(Float hymidity) {
        this.hymidity = hymidity;
    }

    /**
     * @return the notes - Примечание
     */
    public String getNotes() {
        return notes;
    }

    /**
     * @param notes - Примечание
     */
    public void setNotes(String notes) {
        this.notes = notes;
    }
}
