/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package producttest.model;

/**
 * Класс, представляющий продукцию.
 * @author tonkushin
 */
public class Product {
    private int id;
    private String name;
    private int productTypeId;
    private float packVolume;
    private float cakeVolume;
    private int density;
    private int width;
    private int height;
    private int length;
    private int slot;               //Наличие паза/гребня 1 или 0.
    private int consumptionsId;
    private String notes;

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
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the productTypeId
     */
    public int getProductTypeId() {
        return productTypeId;
    }

    /**
     * @param productTypeId the productTypeId to set
     */
    public void setProductTypeId(int productTypeId) {
        this.productTypeId = productTypeId;
    }

    /**
     * @return the packVolume
     */
    public float getPackVolume() {
        return packVolume;
    }

    /**
     * @param packVolume the packVolume to set
     */
    public void setPackVolume(float packVolume) {
        this.packVolume = packVolume;
    }

    /**
     * @return the cakeVolume
     */
    public float getCakeVolume() {
        return cakeVolume;
    }

    /**
     * @param cakeVolume the cakeVolume to set
     */
    public void setCakeVolume(float cakeVolume) {
        this.cakeVolume = cakeVolume;
    }

    /**
     * @return the density
     */
    public int getDensity() {
        return density;
    }

    /**
     * @param density the density to set
     */
    public void setDensity(int density) {
        this.density = density;
    }

    /**
     * @return the width
     */
    public int getWidth() {
        return width;
    }

    /**
     * @param width the width to set
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * @return the height
     */
    public int getHeight() {
        return height;
    }

    /**
     * @param height the height to set
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * @return the length
     */
    public int getLength() {
        return length;
    }

    /**
     * @param length the length to set
     */
    public void setLength(int length) {
        this.length = length;
    }

    /**
     * @return the slot
     */
    public int getSlot() {
        return slot;
    }

    /**
     * @param slot the slot to set
     */
    public void setSlot(int slot) {
        this.slot = slot;
    }

    /**
     * @return the consumptionsId
     */
    public int getConsumptionsId() {
        return consumptionsId;
    }

    /**
     * @param consumptionsId the consumptionsId to set
     */
    public void setConsumptionsId(int consumptionsId) {
        this.consumptionsId = consumptionsId;
    }

    /**
     * @return the notes
     */
    public String getNotes() {
        return notes;
    }

    /**
     * @param notes the notes to set
     */
    public void setNotes(String notes) {
        this.notes = notes;
    }
    
    @Override
    public String toString(){
        return this.name;
    }
}
