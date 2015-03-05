/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package producttest.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Класс, представляющий партию.
 *
 * @author tonkushin
 */
public class Part {

    String partNum;
    private Date dateTime;
    private int productId;
    private String productName;

    DateFormat df;

    /**
     * Возвращает номер партии.
     *
     * @return номер партии.
     */
    public String getPartNum() {
        return this.partNum;
    }

    /**
     * Задаёт номер партии
     *
     * @param partNum - номер партии.
     */
    public void setPartNum(String partNum) {
        this.partNum = partNum;
    }

    /**
     * @return the dateTime
     */
    public Date getDateTime() {
        return dateTime;
    }

    /**
     * @param dateTime the dateTime to set
     */
    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
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

    //Конструктор класса по-умолчанию.
    public Part() {
        this.df = new SimpleDateFormat("yyyy.MM.dd");
    }

    /**
     * Конструктор класса.
     *
     * @param partNum - номер партии.
     * @param dateTime - дата партии.
     * @param productName - наименование продукции.
     */
    public Part(String partNum, Date dateTime, String productName, int productId) {
        this.df = new SimpleDateFormat("dd.MM.yyyy");
        
        this.partNum = partNum;
        this.dateTime = dateTime;
        this.productName = productName;
        this.productId = productId;
    }

    @Override
    public String toString() {
        if (this.dateTime == null) {
            return "";
        }
        
        df.format(dateTime);
        String readableDate = df.format(dateTime);
        return this.partNum + " (" + readableDate + ") "+productName;
    }
}
