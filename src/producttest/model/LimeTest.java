/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package producttest.model;

import java.util.Date;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

/**
 * Класс, представляющий запсиь в таблице испытаний извести. Решил попробовать
 * сделать все свойства через Property.
 *
 * @author tonkushin
 */
public class LimeTest {

    private IntegerProperty id;                 //код

    /**
     * Код
     *
     * @return
     */
    private int getId() {
        return this.id.get();
    }

    /**
     * Код
     *
     * @param id
     */
    private void setId(int id) {
        this.id.set(id);
    }

    public IntegerProperty getIdProperty() {
        return id;
    }

    private Date date;                          //дата

    /**
     * @return Дата испытаний.
     */
    public Date getDate() {
        return date;
    }

    /**
     * @param date - дата испытаний.
     */
    public void setDate(Date date) {
        this.date = date;
    }

    private StringProperty screw;               //место отбора

    /**
     *
     * @return место отбора
     */
    public String getScrew() {
        return this.screw.get();
    }

    /**
     * место отбора
     *
     * @param screw - место отбора
     */
    public void setScrew(String screw) {
        this.screw.set(screw);
    }

    /**
     *
     * @return место отбора (свойство)
     */
    public StringProperty getScrewProperty() {
        return this.screw;
    }

    private int manufacturerId;                 //код производителя.

    /**
     * @return the manufacturerId - код производителя.
     */
    public int getManufacturerId() {
        return manufacturerId;
    }

    /**
     * @param manufacturerId - код производителя.
     */
    public void setManufacturerId(int manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

    private StringProperty manufacturerName;    //наименование производителя. 

    /**
     *
     * @return наименование производителя.
     */
    public String getManufacturerName() {
        return this.manufacturerName.get();
    }

    /**
     *
     * @param manufacturerName наименование производителя.
     */
    public void setManufacturerName(String manufacturerName) {
        this.manufacturerName.set(manufacturerName);
    }

    /**
     *
     * @return наименование производителя. (свойство).
     */
    public StringProperty getManufacturerNameProperty() {
        return this.manufacturerName;
    }

    private int controllerId;                   //код лаборанта.

    /**
     * @return код лаборанта.
     */
    public int getControllerId() {
        return controllerId;
    }

    /**
     * @param controllerId код лаборанта.
     */
    public void setControllerId(int controllerId) {
        this.controllerId = controllerId;
    }

    private StringProperty controllerName;      //имя лаборанта.

    /**
     *
     * @return имя лаборанта
     */
    public String getControllerName() {
        return this.controllerName.get();
    }

    /**
     *
     * @param controllerName имя лаборанта
     */
    public void setControllerName(String controllerName) {
        this.controllerName.set(controllerName);
    }

    /**
     *
     * @return имя лаборанта (свойство)
     */
    public StringProperty getControllerNameProperty() {
        return this.controllerName;
    }

    private StringProperty limeType;            //тип извести
        /**
     * @return the limeType тип извести
     */
    public String getLimeType() {
        return limeType.get();
    }

    /**
     * @param limeType тип извести
     */
    public void setLimeType(String limeType) {
        this.limeType.set(limeType);
    }
    /**
     * 
     * @return тип извести
     */
    public StringProperty  getLimeTypeProperty(){
        return this.limeType;
    }

    private FloatProperty caoMgoVolumeHcl;      //Объём HCl, мл. (Определение CaO + MgO)
        /**
     * @return Объём HCl, мл. (Определение CaO + MgO)
     */
    public Float getCaoMgoVolumeHcl() {
        return caoMgoVolumeHcl.get();
    }

    /**
     * @param caoMgoVolumeHcl Объём HCl, мл. (Определение CaO + MgO)
     */
    public void setCaoMgoVolumeHcl(Float caoMgoVolumeHcl) {
        this.caoMgoVolumeHcl.set(caoMgoVolumeHcl);
    }
    
    /**
     * 
     * @return Объём HCl, мл. (Определение CaO + MgO)
     */
    public FloatProperty getCaoMgoVolumeHclProperty(){
        return this.caoMgoVolumeHcl;
    }
    
    private FloatProperty caoMgoTitreCao;       //Титр CaO
    
       /**
     * @return the Титр CaO
     */
    public Float getCaoMgoTitreCao() {
        return caoMgoTitreCao.get();
    }

    /**
     * @param caoMgoTitreCao Титр CaO
     */
    public void setCaoMgoTitreCao(Float caoMgoTitreCao) {
        this.caoMgoTitreCao.set(caoMgoTitreCao);
    }
    
    /**
     * 
     * @return Титр CaO (свойство)
     */
    public FloatProperty getCaoMgoTitreCaoProperty(){
        return this.caoMgoTitreCao;
    }
    
    private FloatProperty caoMgoWeightCaoMgo;   //Масса навески.
        /**
     * @return Масса навески
     */
    public Float getCaoMgoWeightCaoMgo() {
        return caoMgoWeightCaoMgo.get();
    }

    /**
     * @param caoMgoWeightCaoMgo Масса навески
     */
    public void setCaoMgoWeightCaoMgo(Float caoMgoWeightCaoMgo) {
        this.caoMgoWeightCaoMgo.set(caoMgoWeightCaoMgo);
    }
    
    /**
     * Масса навески (свойство).
     * @return 
     */
    public FloatProperty getCaoMgoWeightCaoMgoProperty(){
        return this.caoMgoWeightCaoMgo;
    }
    
    private FloatProperty saMatVolume;          //Объём слоя (Оценка удельной поверхности).
        /**
     * @return Объём слоя (Оценка удельной поверхности).
     */
    public Float getSaMatVolume() {
        return saMatVolume.get();
    }

    /**
     * @param saMatVolume Объём слоя (Оценка удельной поверхности).
     */
    public void setSaMatVolume(Float saMatVolume) {
        this.saMatVolume.set(saMatVolume);
    }
    
    /**
     * 
     * @return Объём слоя (Оценка удельной поверхности) (свойство).
     */
    public FloatProperty getSaMatVolumeProperty(){
        return this.saMatVolume;
    }
    private FloatProperty saMatDensity;         //Истинная плотность.
    private FloatProperty saWeightLime;         //Навеска, г
    private FloatProperty saConstantDevice;     //Постоянная прибора.
    private FloatProperty saTime;               //Время стекания жидкости
    private FloatProperty saTemperature;        //Температура воздуха.
    private FloatProperty mgoVolumeTrilonCaoMgo;    //Объём трилона Б CaO + MgO.
    private FloatProperty mgoVolumeTrilonCao;   //Объём трилона Б CaO.
    private FloatProperty mgoTitreMgo;          //Титр MgO.
    private FloatProperty mgoWeightMgo;         //Масса навески

    private StringProperty time;                //Время гашения.
    private FloatProperty temperature;          //Температура гашения.


}
