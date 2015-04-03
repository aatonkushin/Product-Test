/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAL;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Locale;
import oracle.jdbc.pool.OracleDataSource;
import producttest.Config;
import producttest.model.DefectType;
import producttest.model.Product;

/**
 *
 * @author tonkushin
 */
public class LimeTestDataContext {
     //
    OracleDataSource dataSource;

    //Настройки.
    Config cfg;

    private OracleDataSource getDataSource() throws SQLException {
        if (dataSource == null) {
            dataSource = new OracleDataSource();
            dataSource.setURL(url);
            dataSource.setUser(username);
            dataSource.setPassword(password);
        }

        return dataSource;
    }

    Connection connection;
    
    private Connection getConnection() throws SQLException{
        if (connection == null) {
            Locale.setDefault(Locale.ENGLISH);
            connection = getDataSource().getConnection();
        }
        
        return connection;
    }

    private String url = "jdbc:oracle:thin:@192.168.1.37:1521:xe";
    private String username = "SUPP";
    private String password = "@!Oracle01";

    //Список продукции для кэша.
    ArrayList<Product> products;

    //Список видов дефектов для кэша.
    ArrayList<DefectType> defectTypes;

    int currentDensity = 0;     //Хранит информацию о плотности с момента последнего запроса.
    int currentProductId = 0;   //Хранит информацию о типе продукции с момента последнего запроса.

    /**
     * Конструктор класса по-умолчанию.
     */
    public LimeTestDataContext() {
        //пробуем считать настройки изфайла.
        try {

        } catch (Exception ex) {
            System.out.println("Ошибка при загрузке настроек: " + ex.toString());
        }
        cfg = new Config();
        cfg.readPropertiesFromFile();

        if (cfg.getConnectionString() != null) {
            url = cfg.getConnectionString();
        }
        if (cfg.getUsername() != null) {
            username = cfg.getUsername();
        }
        if (cfg.getPassword() != null) {
            password = cfg.getPassword();
        }
    }
}
