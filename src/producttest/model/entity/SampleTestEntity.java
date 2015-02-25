/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package producttest.model.entity;

import java.sql.Date;

/**
 *
 * @author tonkushin
 */
public class SampleTestEntity {
    private int id;
    private String partNo;
    private int productId;
    private Date partDate;
    private Date testDate;
    private int personId;

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
     * @return the partDate
     */
    public Date getPartDate() {
        return partDate;
    }

    /**
     * @param partDate the partDate to set
     */
    public void setPartDate(Date partDate) {
        this.partDate = partDate;
    }

    /**
     * @return the testDate
     */
    public Date getTestDate() {
        return testDate;
    }

    /**
     * @param testDate the testDate to set
     */
    public void setTestDate(Date testDate) {
        this.testDate = testDate;
    }

    /**
     * @return the personId
     */
    public int getPersonId() {
        return personId;
    }

    /**
     * @param personId the personId to set
     */
    public void setPersonId(int personId) {
        this.personId = personId;
    }
    
    public SampleTestEntity(){
        
    }
}
