/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package producttest.model;

import java.util.Date;

/**
 * Класс, представляющиё протокол испытаний вцелом. 
 * Необходим для упаковки данных и передачи их в/из DataContext.
 * @author tonkushin
 */
public class TestReport {
    private int id;
    private String partNo;
    private int productId;
    private Date partDate;
    private Date testDate;
    private int personId;
    private String sampleType;
    private Double reqDensity;
    private Double reqDurability;
    private Double avgWetDensity;
    private Double avgDryDensity;
    private String densityMark;
    private Double densityVariation;
    private Double avgDurability;
    private String durabilityMark;
    private Double durabilityVariation;
    private String addMeasureName1;
    private Double addMeasureValue1;
    private String addMeasureUnit1;
    private String addMeasureName2;
    private Double addMeasureValue2;
    private String addMeasureUnit2;
    private Boolean applyRatios;

    /**
     * @return the id - код в БД
     */
    public int getId() {
        return id;
    }

    /**
     * @param id - код в БД
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the partNo - номер партии
     */
    public String getPartNo() {
        return partNo;
    }

    /**
     * @param partNo - номер партии
     */
    public void setPartNo(String partNo) {
        this.partNo = partNo;
    }

    /**
     * @return the productId - код продукции в БД
     */
    public int getProductId() {
        return productId;
    }

    /**
     * @param productId - код продукции в БД
     */
    public void setProductId(int productId) {
        this.productId = productId;
    }

    /**
     * @return the partDate - дата партии
     */
    public Date getPartDate() {
        return partDate;
    }

    /**
     * @param partDate  - дата партии
     */
    public void setPartDate(Date partDate) {
        this.partDate = partDate;
    }

    /**
     * @return the testDate - дата испытаний
     */
    public Date getTestDate() {
        return testDate;
    }

    /**
     * @param testDate  - дата испытаний
     */
    public void setTestDate(Date testDate) {
        this.testDate = testDate;
    }

    /**
     * @return the personId - код сотрудника в БД
     */
    public int getPersonId() {
        return personId;
    }

    /**
     * @param personId - код сотрудника в БД
     */
    public void setPersonId(int personId) {
        this.personId = personId;
    }

    /**
     * @return the sampleType - тип образца (100 или 70)
     */
    public String getSampleType() {
        return sampleType;
    }

    /**
     * @param sampleType - тип образца (100 или 70)
     */
    public void setSampleType(String sampleType) {
        this.sampleType = sampleType;
    }

    /**
     * @return the reqDensity - требуемая плотность Pтр.
     */
    public Double getReqDensity() {
        return reqDensity;
    }

    /**
     * @param reqDensity - требуемая плотность Pтр.
     */
    public void setReqDensity(Double reqDensity) {
        this.reqDensity = reqDensity;
    }

    /**
     * @return the reqDurability - требуемая прочность Rтр
     */
    public Double getReqDurability() {
        return reqDurability;
    }

    /**
     * @param reqDurability - требуемая прочность Rтр
     */
    public void setReqDurability(Double reqDurability) {
        this.reqDurability = reqDurability;
    }

    /**
     * @return the avgWetDensity - средняя плотность во влажном состоянии
     */
    public Double getAvgWetDensity() {
        return avgWetDensity;
    }

    /**
     * @param avgWetDensity  - средняя плотность во влажном состоянии 
     */
    public void setAvgWetDensity(Double avgWetDensity) {
        this.avgWetDensity = avgWetDensity;
    }

    /**
     * @return the avgDryDensity  - средняя плотность во сухом состоянии
     */
    public Double getAvgDryDensity() {
        return avgDryDensity;
    }

    /**
     * @param avgDryDensity  - средняя плотность во сухом состоянии
     */
    public void setAvgDryDensity(Double avgDryDensity) {
        this.avgDryDensity = avgDryDensity;
    }

    /**
     * @return the densityMark - марка по плотности
     */
    public String getDensityMark() {
        return densityMark;
    }

    /**
     * @param densityMark - марка по плотности
     */
    public void setDensityMark(String densityMark) {
        this.densityMark = densityMark;
    }

    /**
     * @return the densityVariation - коэф. вариации по плотности
     */
    public Double getDensityVariation() {
        return densityVariation;
    }

    /**
     * @param densityVariation - коэф. вариации по плотности
     */
    public void setDensityVariation(Double densityVariation) {
        this.densityVariation = densityVariation;
    }

    /**
     * @return the avgDurability - средняя прочность
     */
    public Double getAvgDurability() {
        return avgDurability;
    }

    /**
     * @param avgDurability - средняя прочность
     */
    public void setAvgDurability(Double avgDurability) {
        this.avgDurability = avgDurability;
    }

    /**
     * @return the durabilityMark - марка по прочности
     */
    public String getDurabilityMark() {
        return durabilityMark;
    }

    /**
     * @param durabilityMark - марка по прочности
     */
    public void setDurabilityMark(String durabilityMark) {
        this.durabilityMark = durabilityMark;
    }

    /**
     * @return the durabilityVariation - коэффициент вариации по прочности
     */
    public Double getDurabilityVariation() {
        return durabilityVariation;
    }

    /**
     * @param durabilityVariation - коэффициент вариации по прочности
     */
    public void setDurabilityVariation(Double durabilityVariation) {
        this.durabilityVariation = durabilityVariation;
    }

    /**
     * @return the addMeasureName1 - название доп. измерения 1.
     */
    public String getAddMeasureName1() {
        return addMeasureName1;
    }

    /**
     * @param addMeasureName1  - название доп. измерения 1.
     */
    public void setAddMeasureName1(String addMeasureName1) {
        this.addMeasureName1 = addMeasureName1;
    }

    /**
     * @return the addMeasureValue1  - значение доп. измерения 1.
     */
    public Double getAddMeasureValue1() {
        return addMeasureValue1;
    }

    /**
     * @param addMeasureValue1 - значение доп. измерения 1.
     */
    public void setAddMeasureValue1(Double addMeasureValue1) {
        this.addMeasureValue1 = addMeasureValue1;
    }

    /**
     * @return the - ед. измерения доп. измерения 1.
     */
    public String getAddMeasureUnit1() {
        return addMeasureUnit1;
    }

    /**
     * @param addMeasureUnit1 ед. измерения доп. измерения 1.
     */
    public void setAddMeasureUnit1(String addMeasureUnit1) {
        this.addMeasureUnit1 = addMeasureUnit1;
    }

    /**
     * @return the addMeasureName2 название доп. измерения 2.
     */
    public String getAddMeasureName2() {
        return addMeasureName2;
    }

    /**
     * @param addMeasureName2 название доп. измерения 2.
     */
    public void setAddMeasureName2(String addMeasureName2) {
        this.addMeasureName2 = addMeasureName2;
    }

    /**
     * @return the addMeasureValue2 - значение доп. измерения 2.
     */
    public Double getAddMeasureValue2() {
        return addMeasureValue2;
    }

    /**
     * @param addMeasureValue2 - значение доп. измерения 2.
     */
    public void setAddMeasureValue2(Double addMeasureValue2) {
        this.addMeasureValue2 = addMeasureValue2;
    }

    /**
     * @return the addMeasureUnit2 ед. измерения доп. измерения 2.
     */
    public String getAddMeasureUnit2() {
        return addMeasureUnit2;
    }

    /**
     * @param addMeasureUnit2 ед. измерения доп. измерения 2.
     */
    public void setAddMeasureUnit2(String addMeasureUnit2) {
        this.addMeasureUnit2 = addMeasureUnit2;
    }

    /**
     * @return the applyRatios
     */
    public Boolean getApplyRatios() {
        return applyRatios;
    }

    /**
     * @param applyRatios the applyRatios to set
     */
    public void setApplyRatios(Boolean applyRatios) {
        this.applyRatios = applyRatios;
    }
    
}
