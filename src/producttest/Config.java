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

/**
 *
 * @author tonkushin
 */
public class Config {

    private Properties prop;
    private InputStream inputStream;
    private OutputStream outputStream;
    
    private String connectionString;
    private String username;
    private String password;
    private String filterPersonal;

    public Config() {
         prop = new Properties();
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

            // load a properties file
            prop.load(inputStream);

            this.setConnectionString(prop.getProperty("connectionString"));
            this.setUsername(prop.getProperty("username"));
            this.setPassword(prop.getProperty("password"));
            this.setFilterPersonal(prop.getProperty("filterPersonal"));
            

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
