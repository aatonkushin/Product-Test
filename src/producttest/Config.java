/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package producttest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author tonkushin
 */
public class Config {

    private final Properties prop;
    private InputStream inputStream;
    private OutputStream outputStream;
    
    private String connectionString;
    private String username;
    private String password;
    
    private String filterPersonal;
    
    private StringProperty headerLine1;
    public final String getHeaderLine1(){
        return headerLine1.get();
    }
    public final void setHeaderLine1(String hl){
        this.headerLine1.set(hl);
    }
    public StringProperty getHeaderLine1Property(){
        return this.headerLine1;
    }
    
    private StringProperty headerLine2;
    public final String getHeaderLine2(){
        return headerLine2.get();
    }
    public final void setHeaderLine2(String hl){
        this.headerLine2.set(hl);
    }
    public StringProperty getHeaderLine2Property(){
        return this.headerLine2;
    }
    
    private StringProperty headerLine3;
    public final String getHeaderLine3(){
        return headerLine3.get();
    }
    public final void setHeaderLine3(String hl){
        this.headerLine3.set(hl);
    }
    public StringProperty getHeaderLine3Property(){
        return this.headerLine3;
    }
    
    private StringProperty headerLine4;
    public final String getHeaderLine4(){
        return headerLine4.get();
    }
    public final void setHeaderLine4(String hl){
        this.headerLine4.set(hl);
    }
    public StringProperty getHeaderLine4Property(){
        return this.headerLine4;
    }
    
    private StringProperty headerPathToLogo;
    public final String getHeaderPathToLogo(){
        return this.headerPathToLogo.get();
    }
    public final void setHeaderPathToLogo(String str){
        headerPathToLogo.set(str);
    }
    public StringProperty getHeaderPathToLogoProperty(){
        return this.headerPathToLogo;
    }
    
    private StringProperty headerProductName;
    public final String getHeaderProductName(){
        return this.headerProductName.get();
    }
    public final void setHeaderProductName(String productName){
        this.headerProductName.set(productName);
    }
    public StringProperty getHeaderProductNameProperty(){
        return this.headerProductName;
    }
    
    private StringProperty footerProfession;
    public final String getFooterProfession(){
        return this.footerProfession.get();
    }
    public final void setFooterProfession(String profession){
        this.footerProfession.set(profession);
    }
    public StringProperty getFooterProfessionProperty(){
        return this.footerProfession;
    }
    
    private StringProperty footerPersonName;
    public final String getFooterPersonName(){
        return this.footerPersonName.get();
    }
    public final void setFooterPersonName(String personName){
        this.footerPersonName.set(personName);
    }
    public StringProperty getFooterPersonNameProperty(){
        return this.footerPersonName;
    }
    
    public IntegerProperty partNumsToLoad;
    public final Integer getPartNumsToLoad(){
        return this.partNumsToLoad.get();
    }
    public final void setPartNumsToLoad(Integer n){
        this.partNumsToLoad.set(n);
    }
    public IntegerProperty getPartNumsToLoadProperty(){
        return this.partNumsToLoad;
    }
    
    /**
     * Конструктор класса по усолчанию.
     */
    public Config() {
         prop = new Properties();
         
         headerLine1 = new SimpleStringProperty("");
         headerLine2 = new SimpleStringProperty("");
         headerLine3 = new SimpleStringProperty("");
         headerLine4 = new SimpleStringProperty("");
         
         headerPathToLogo = new SimpleStringProperty("");
         headerProductName = new SimpleStringProperty("");
         
         footerProfession = new SimpleStringProperty("");
         footerPersonName = new SimpleStringProperty("");
         
         partNumsToLoad = new SimpleIntegerProperty(500);
    }

    /**
     * Записывает настройки в файл.
     */
    public void writePropertiesToFile() {
        try {
            outputStream = new FileOutputStream("config.properties");

            // set the properties value
            prop.setProperty("connectionString", getConnectionString() != null ? getConnectionString() : "");
            prop.setProperty("username", getUsername() != null ? getUsername() : "");
            prop.setProperty("password", getPassword() != null ? getPassword() : "");
            prop.setProperty("filterPersonal", getFilterPersonal() != null ? getFilterPersonal() : "" );
            prop.setProperty("headerLine1", getHeaderLine1() != null ? getHeaderLine1() : "");
            prop.setProperty("headerLine2", getHeaderLine2()!= null ? getHeaderLine2() : "");
            prop.setProperty("headerLine3", getHeaderLine3()!= null ? getHeaderLine3() : "");
            prop.setProperty("headerLine4", getHeaderLine4()!= null ? getHeaderLine4() : "");
            prop.setProperty("headerPathToLogo", getHeaderPathToLogo() != null ? getHeaderPathToLogo() : "");
            prop.setProperty("headerProductName", getHeaderProductName() != null ? getHeaderProductName() : "");
            prop.setProperty("footerProfession", getFooterProfession() != null ? getFooterProfession() : "");
            prop.setProperty("footerPersonName", getFooterPersonName() != null ? getFooterPersonName() : "");
            prop.setProperty("partNumsToLoad", getPartNumsToLoad() != null ? getPartNumsToLoad().toString() : "500");
            
            // save properties to project root folder
            prop.store(outputStream, null);

        } catch (IOException io) {
            io.printStackTrace();
            System.out.println("--- Ошибка сохранения файла настроек ---");
            System.out.println(io);
        }
        finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    System.out.println("--- Ошибка закрытия файла настроек ---");
                    System.out.println(e);
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Считывает настройки из файла.
     */
    public void readPropertiesFromFile() {
        try {

            inputStream = new FileInputStream("config.properties");

            //Загружаем файл настроек.
            prop.load(inputStream);

            this.setConnectionString(prop.getProperty("connectionString"));
            this.setUsername(prop.getProperty("username"));
            this.setPassword(prop.getProperty("password"));
            this.setFilterPersonal(prop.getProperty("filterPersonal"));
            this.setHeaderLine1(prop.getProperty("headerLine1"));
            this.setHeaderLine2(prop.getProperty("headerLine2"));
            this.setHeaderLine3(prop.getProperty("headerLine3"));
            this.setHeaderLine4(prop.getProperty("headerLine4"));
            this.setHeaderPathToLogo(prop.getProperty("headerPathToLogo"));
            this.setHeaderProductName(prop.getProperty("headerProductName"));
            this.setFooterProfession(prop.getProperty("footerProfession"));
            this.setFooterPersonName(prop.getProperty("footerPersonName"));
            this.setPartNumsToLoad(Integer.valueOf(prop.getProperty("partNumsToLoad", "500")));
        } catch (IOException ex) {
            System.out.println("--- Ошибка чтения файла настроек ---");
            System.out.println(ex);
            ex.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    System.out.println("--- Ошибка закрытия файла настроек при чтении---");
                    System.out.println(e);
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * @return the connectionString
     */
    public String getConnectionString() {
        return connectionString;
    }

    /**
     * @param connectionString the connectionString to set
     */
    public void setConnectionString(String connectionString) {
        this.connectionString = connectionString;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the filterPersonal
     */
    public String getFilterPersonal() {
        return filterPersonal;
    }

    /**
     * @param filterPersonal the filterPersonal to set
     */
    public void setFilterPersonal(String filterPersonal) {
        this.filterPersonal = filterPersonal;
    }
}
