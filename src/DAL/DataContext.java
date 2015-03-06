/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAL;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import oracle.jdbc.pool.OracleDataSource;
import producttest.Config;
import producttest.model.HumidityTest;
import producttest.model.Month;
import producttest.model.Part;
import producttest.model.Person;
import producttest.model.SampleTest;
import producttest.model.TestReport;
import producttest.model.Product;
import producttest.model.ProductPassport;
import producttest.model.RequiredDensity;
import producttest.model.RequiredDurability;
import producttest.model.Stat;
import producttest.model.Year;

/**
 * Класс доступа к данным.
 *
 * @author tonkushin
 */
public class DataContext {

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

    private String url = "jdbc:oracle:thin:@192.168.1.37:1521:xe";
    private String username = "SUPP";
    private String password = "@!Oracle01";

    //Список продукции для кэша.
    ArrayList<Product> products;

    /**
     * Конструктор класса по-умолчанию.
     */
    public DataContext() {
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

    /**
     * Создаёт соединение с базой данных.
     *
     * @throws SQLException
     */
    public void connect() throws SQLException {
        Locale.setDefault(Locale.ENGLISH);
        connection = getDataSource().getConnection();
    }

    /**
     * Получает номер партий из БД
     *
     * @return Список номеров партий.
     * @throws SQLException
     * @throws java.text.ParseException
     */
    public ArrayList<Part> getParts() throws SQLException, ParseException {
        Date start = new Date();
        System.out.println(" getParts() Start: " + start.toString());
        ArrayList<Part> retVal = new ArrayList<>();

        if (connection == null) {
            return retVal;
        }

        Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        String query = "SELECT DISTINCT TRUNC(DATE_TIME) DATE_TIME, PART_NO, PRODUCT_ID, NAME FROM T_Q_CUTTING, T_PRODUCT WHERE\n"
                + "T_Q_CUTTING.PRODUCT_ID = T_PRODUCT.ID(+) ORDER BY DATE_TIME DESC, PART_NO DESC";
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        //System.out.println("\nExecuting query: " + query);
        try (ResultSet rset = stmt.executeQuery(query)) {

            int n = 0;
            while (rset.next()) {
                Date dt = df.parse(rset.getString(1).substring(0, 10));
                retVal.add(new Part(rset.getString(2), dt, rset.getString(4), rset.getInt(3)));
                n++;
            }

            //System.out.println("\nLoaded: " + n);
        }
/*
        String q = "SELECT T_Q_ST.ID ID FROM T_Q_ST WHERE PART_NO = '%s' AND TO_CHAR(TRUNC(T_Q_ST.PART_DATE), 'yyyy-MM-dd') =  '%s' AND T_Q_ST.PRODUCT_ID = %d";
        for (Part part : retVal) {
            //Calendar cal = Calendar.getInstance();
            //cal.setTime(part.getDateTime());
            //cal.get(Calendar.YEAR)
            query = String.format(q, part.getPartNum(), df.format(part.getDateTime()), part.getProductId());

            try (ResultSet rset = stmt.executeQuery(query)) {
                if (rset.next()) {
                    part.setTestId(rset.getInt("ID"));
                } else {
                    part.setTestId(-1);
                }
            }
        }
        */

        Date end = new Date();
        System.out.println(" getParts() End: " + end.toString());
        return retVal;
    }

    /**
     * Получает сотрудников из БД.
     *
     * @return Список сотрудников - лаборантов.
     * @throws SQLException
     */
    public ArrayList<Person> GetPersonal() throws SQLException {
        ArrayList<Person> retVal = new ArrayList<>(10);
        if (connection == null) {
            return retVal;
        }

        //String filter = " and prof.name IN ('Лаборант', 'Техник-лаборант', 'Главный технолог', 'Инженер-лаборант', 'Начальник лаборатории по контролю производства') ";
        String filter = "";
        if (cfg.getFilterPersonal() != null && !cfg.getFilterPersonal().equals("")) {
            filter = " and prof.name IN (" + cfg.getFilterPersonal() + ") ";
        }

        Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        String query = "select pers.name as display_value, pers.id as return_value \n"
                + "from t_personal pers, t_professions prof \n"
                + "where pers.profession_id = prof.id \n"
                + filter
                + "order by display_value";

        //System.out.println("\nExecuting query: " + query);
        try (ResultSet rset = stmt.executeQuery(query)) {
            while (rset.next()) {
                retVal.add(new Person(rset.getInt(2), rset.getString(1)));
            }
        }
        return retVal;
    }

    /**
     * Добавляет новую запись в таблицу испытаний или обновляет уже
     * существуюшую.
     *
     * @param partNo
     * @param productId
     * @param partDate
     * @param testDate
     * @param personId
     * @param sampleType
     * @param reqDensity
     * @param reqDurability
     * @param avgWetDensity
     * @param avgDryDensity
     * @param densityMark
     * @param densityVariation
     * @param avgDurability
     * @param durabilityMark
     * @param durabilityVariation
     * @param addMeasureName1
     * @param addMeasureValue1
     * @param addMeasureUnit1
     * @param addMeasureName2
     * @param addMeasureValue2
     * @param addMeasureUnit2
     * @param applyRatios
     * @return
     * @throws SQLException
     */
    public int addOrUpdateTestIntoDb(String partNo, int productId, Date partDate,
            Date testDate, int personId, String sampleType,
            Double reqDensity, Double reqDurability, Double avgWetDensity,
            Double avgDryDensity, String densityMark, Double densityVariation,
            Double avgDurability, String durabilityMark, Double durabilityVariation,
            String addMeasureName1, Double addMeasureValue1, String addMeasureUnit1,
            String addMeasureName2, Double addMeasureValue2, String addMeasureUnit2, Boolean applyRatios) throws SQLException {

        if (connection == null) {
            return 0;
        }

        int applyRatiosInt = 0;
        if (applyRatios) {
            applyRatiosInt = 1;
        }

        DateFormat df = new SimpleDateFormat("dd.MM.yyyy");

        //Проверяем: есть ли запись с таким номером партии и датой.
        String query = "SELECT ID FROM T_Q_ST WHERE PART_NO = '" + partNo + "' AND PART_DATE=TO_DATE('" + df.format(partDate) + "', 'DD.MM.YYYY')";

        Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);

        //System.out.println("\nExecuting query: " + query);
        ResultSet rset = stmt.executeQuery(query);

        //Если есть результаты, значит запись уже есть...
        if (rset.next()) {
            //...в таком случае просто обновляем запись в базе данных.
            stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            //query = "UPDATE T_Q_ST SET PART_NO ='" + partNo + "', PRODUCT_ID=" + productId + ", PART_DATE = TO_DATE('" + df.format(partDate)
            //        + "', 'DD.MM.YYYY'), TEST_DATE = TO_DATE('" + df.format(testDate) + "', 'DD.MM.YYYY'), PERSON_ID=" + personId + ", SAMPLE_TYPE='" + sampleType
            //        + "' WHERE ID=" + rset.getInt(1);

            query = String.format("UPDATE T_Q_ST SET PART_NO ='%s', PRODUCT_ID=%d, PART_DATE = TO_DATE('%s', 'DD.MM.YYYY'), TEST_DATE = TO_DATE('%s', 'DD.MM.YYYY'), PERSON_ID=%d, SAMPLE_TYPE='%s',"
                    + "REQ_DENSITY = %f, REQ_DURABILITY = %f,  AVG_WET_DENSITY=%f, AVG_DRY_DENSITY =%f, DENSITY_MARK='%s', DENSITY_VARIATION=%f, AVG_DURABILITY=%f,"
                    + "DURABILITY_MARK='%s', DURABILITY_VARIATION=%f, ADD_MEASURE_NAME_1='%s', ADD_MEASURE_VALUE_1=%f, ADD_MEASURE_UNIT_1='%s', ADD_MEASURE_NAME_2='%s', ADD_MEASURE_VALUE_2=%f, ADD_MEASURE_UNIT_2='%s', "
                    + "APPLY_RATIOS=%d "
                    + "WHERE ID=%d",
                    partNo, productId, df.format(partDate), df.format(testDate), personId, sampleType,
                    reqDensity, reqDurability, avgWetDensity, avgDryDensity, densityMark, densityVariation, avgDurability,
                    durabilityMark, durabilityVariation, addMeasureName1, addMeasureValue1, addMeasureUnit1, addMeasureName2, addMeasureValue2, addMeasureUnit2,
                    applyRatiosInt,
                    rset.getInt(1));

            //System.out.println("\nUpdating: " + query);
            stmt.execute(query);

            return 2;
        } else {
            //...иначе добавляем новую запись.
            stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            //query = "INSERT INTO T_Q_ST(ID, PART_NO, PRODUCT_ID, PART_DATE, TEST_DATE, PERSON_ID, SAMPLE_TYPE) "
            //        + "VALUES(T_Q_ST_SEQ.NEXTVAL, '" + partNo + "', " + productId
            //        + ", TO_DATE('" + df.format(partDate) + "', 'DD.MM.YYYY'), TO_DATE('" + df.format(testDate) + "', 'DD.MM.YYYY'), " + personId + ", '" + sampleType + "')";

            query = String.format("INSERT INTO T_Q_ST(ID, PART_NO, PRODUCT_ID, PART_DATE, TEST_DATE, PERSON_ID, SAMPLE_TYPE, "
                    + "REQ_DENSITY, REQ_DURABILITY, AVG_WET_DENSITY, AVG_DRY_DENSITY, DENSITY_MARK, DENSITY_VARIATION, AVG_DURABILITY, DURABILITY_MARK, DURABILITY_VARIATION, "
                    + "ADD_MEASURE_NAME_1, ADD_MEASURE_VALUE_1, ADD_MEASURE_UNIT_1, ADD_MEASURE_NAME_2, ADD_MEASURE_VALUE_2, ADD_MEASURE_UNIT_2, APPLY_RATIOS) "
                    + "VALUES(T_Q_ST_SEQ.NEXTVAL, '%s', %d, TO_DATE('%s', 'DD.MM.YYYY'), TO_DATE('%s', 'DD.MM.YYYY'), %d, '%s', "
                    + "%f, %f, %f, %f, '%s', %f, %f, '%s', %f, "
                    + "'%s', %f, '%s', '%s', %f, '%s', %d)",
                    partNo, productId, df.format(partDate), df.format(testDate), personId, sampleType,
                    reqDensity, reqDurability, avgWetDensity, avgDryDensity, densityMark, densityVariation, avgDurability, durabilityMark, durabilityVariation,
                    addMeasureName1, addMeasureValue1, addMeasureUnit1, addMeasureName2, addMeasureValue2, addMeasureUnit2, applyRatiosInt);

            //System.out.println("\nInserting: " + query);
            stmt.execute(query);

            rset.close();

            return 1;
        }
    }

    /**
     * Добавляет данные об образце испытаний в табдицу t_q_st_details БД.
     *
     * @param testId - номер теста в БД.
     * @param sampleNum - номер образца.
     * @param positionSample - расположение образца в массиве.
     * @param length - длина образца.
     * @param width - ширина образца.
     * @param height - высота образца.
     * @param weight - масса образца.
     * @param destructLoad - разрушающая нагрузка.
     * @param wetDensity - плотность во влажном состоянии.
     * @param dryDensity - плотность в сухом состоянии.
     * @param durability - предел прочности при сжатии.
     * @return 0 - если не удалось вставить запись или изменить её. 1 - если
     * запись успешно добавлена. 2 - если запись успешно обновлена.
     * @throws java.sql.SQLException
     */
    public int addOrUpdateTestSampleIntoDb(int testId, int sampleNum,
            String positionSample, Double length, Double width, Double height,
            Double weight, Double destructLoad, Double wetDensity, Double dryDensity, Double durability) throws SQLException {
        if (connection == null) {
            return 0;
        }

        //1. Проверяем: есть ли запись с таким testId и sampleNum.
        String query = String.format("SELECT ID FROM T_Q_ST_DETAILS WHERE T_Q_ST_ID = %d AND SAMPLE_NUM = %d", testId, sampleNum);
        Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);

        //System.out.println("\nExecuting query: " + query);
        ResultSet rset = stmt.executeQuery(query);

        //2. Если есть результаты, значит запись уже есть...
        if (rset.next()) {
            stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            query = String.format("UPDATE T_Q_ST_DETAILS "
                    + "SET T_Q_ST_ID       = %d"
                    + ", SAMPLE_NUM      = %d"
                    + ", POSITION_SAMPLE = '%s' "
                    + ", LENGTH          = %f "
                    + ", WIDTH           = %f "
                    + ", HEIGHT          = %f "
                    + ", WEIGHT          = %f "
                    + ", DESTRUCT_LOAD   = %f "
                    + ", WET_DENSITY     = %f "
                    + ", DRY_DENSITY     = %f "
                    + ", DURABILITY      = %f "
                    + " WHERE ID         = %d", testId, sampleNum,
                    positionSample, length, width, height, weight, destructLoad,
                    wetDensity, dryDensity, durability, rset.getInt(1));

            //System.out.println("\nExecuting query: " + query);
            stmt.executeQuery(query);
            return 2;
        } //... иначе вставляем новую запись.
        else {
            stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            query = String.format("INSERT INTO T_Q_ST_DETAILS"
                    + "  (T_Q_ST_ID, SAMPLE_NUM, POSITION_SAMPLE, LENGTH, WIDTH, HEIGHT, WEIGHT, DESTRUCT_LOAD, WET_DENSITY, DRY_DENSITY, DURABILITY)"
                    + "  VALUES ( %d, %d, '%s', %f, %f, %f, %f, %f, %f, %f, %f)",
                    testId, sampleNum, positionSample, length, width, height,
                    weight, destructLoad, wetDensity, dryDensity, durability);

            //System.out.println("\nInserting: " + query);
            rset.close();

            stmt.execute(query);
            return 1;
        }
    }

    public int addOrUpdateHumidity(int testId, int bottleNum, Double bottleWeight, Double wetWeight, Double dryWeight, Double humidity) throws SQLException {
        if (connection == null) {
            return 0;
        }

        //1.Проверяем: есть ли запись с такими testId и bottleNum в базе.
        String query = String.format("SELECT ID FROM T_Q_ST_HUMIDITY WHERE T_Q_ST_ID = %d AND BOTTLE_NUM = %d", testId, bottleNum);
        Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);

        //System.out.println("\nExecuting query: " + query);
        ResultSet rset = stmt.executeQuery(query);

        //2. Если есть результаты, значит запись уже есть...
        if (rset.next()) {
            stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            query = String.format("UPDATE T_Q_ST_HUMIDITY SET "
                    + "T_Q_ST_ID = %d "
                    + ",BOTTLE_NUM    = %d "
                    + ",BOTTLE_WEIGHT = %f "
                    + ",WET_WEIGHT    = %f "
                    + ",DRY_WEIGHT    = %f "
                    + ",HUMIDITY      = %f "
                    + "WHERE ID = %d", testId, bottleNum, bottleWeight, wetWeight, dryWeight, humidity, rset.getInt(1));

            //System.out.println("\nUpdating: " + query);
            stmt.executeQuery(query);
            return 2;
        } else {
            stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            query = String.format("INSERT INTO T_Q_ST_HUMIDITY (T_Q_ST_ID, BOTTLE_NUM, BOTTLE_WEIGHT, WET_WEIGHT, DRY_WEIGHT, HUMIDITY) "
                    + "VALUES (%d, %d, %f, %f, %f, %f)", testId, bottleNum, bottleWeight, wetWeight, dryWeight, humidity);

            //System.out.println("\nInserting: " + query);
            stmt.execute(query);

            return 1;
        }
    }

    /**
     * Получает код в базе данных для партии с указанным номером и датой.
     *
     * @param partNo - номер партии.
     * @param partDate - дата партии.
     * @return код в БД или -1, если ничего не найдено.
     * @throws SQLException
     */
    public int getTestId(String partNo, Date partDate) throws SQLException {
        int retVal = -1;

        if (connection == null) {
            return 0;
        }

        DateFormat df = new SimpleDateFormat("dd.MM.yyyy");

        //Проверяем: есть ли запись с таким номером партии и датой.
        String query = "SELECT ID FROM T_Q_ST WHERE PART_NO = '" + partNo + "' AND PART_DATE=TO_DATE('" + df.format(partDate) + "', 'DD.MM.YYYY')";

        Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);

        //System.out.println("\nExecuting query: " + query);
        ResultSet rset = stmt.executeQuery(query);

        if (rset.next()) {
            retVal = rset.getInt(1);
        }

        return retVal;
    }

    public TestReport getSampleTest(Part part) throws SQLException {
        TestReport retVal = null;

        if (connection == null) {
            return retVal;
        }

        if (part.getDateTime() == null) {
            return retVal;
        }

        DateFormat df = new SimpleDateFormat("dd.MM.yyyy");

        Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        String query = String.format("SELECT ID, PART_NO, PRODUCT_ID, PART_DATE, TEST_DATE, PERSON_ID, SAMPLE_TYPE, REQ_DENSITY, REQ_DURABILITY, "
                + "AVG_WET_DENSITY, AVG_DRY_DENSITY, DENSITY_MARK, DENSITY_VARIATION, AVG_DURABILITY, DURABILITY_MARK, DURABILITY_VARIATION, "
                + "ADD_MEASURE_NAME_1, ADD_MEASURE_VALUE_1, ADD_MEASURE_UNIT_1, ADD_MEASURE_NAME_2, ADD_MEASURE_VALUE_2, ADD_MEASURE_UNIT_2, APPLY_RATIOS "
                + "FROM T_Q_ST WHERE PART_NO='%s' AND PART_DATE =TO_DATE('%s','DD.MM.YYYY')", part.getPartNum(), df.format(part.getDateTime()));

        //System.out.println("\nExecuting query: " + query);
        try (ResultSet rset = stmt.executeQuery(query)) {
            if (rset.next()) {
                retVal = new TestReport();
                retVal.setId(rset.getInt(1));
                retVal.setPartNo(rset.getString(2));
                retVal.setProductId(rset.getInt(3));
                retVal.setPartDate(rset.getDate(4));
                retVal.setTestDate(rset.getDate(5));
                retVal.setPersonId(rset.getInt(6));
                retVal.setSampleType(rset.getString(7));
                retVal.setReqDensity(rset.getDouble(8));
                retVal.setReqDurability(rset.getDouble(9));

                retVal.setAvgWetDensity(rset.getDouble(10));
                retVal.setAvgDryDensity(rset.getDouble(11));
                retVal.setDensityMark(rset.getString(12));
                retVal.setDensityVariation(rset.getDouble(13));
                retVal.setAvgDurability(rset.getDouble(14));
                retVal.setDurabilityMark(rset.getString(15));
                retVal.setDurabilityVariation(rset.getDouble(16));

                retVal.setAddMeasureName1(rset.getString(17));
                retVal.setAddMeasureValue1(rset.getDouble(18));
                retVal.setAddMeasureUnit1(rset.getString(19));
                retVal.setAddMeasureName2(rset.getString(20));
                retVal.setAddMeasureValue2(rset.getDouble(21));
                retVal.setAddMeasureUnit2(rset.getString(22));

                retVal.setApplyRatios(rset.getBoolean(23));
            }
        }

        return retVal;
    }

    /**
     * Получает из БД список тестов на влажность для данной партии.
     *
     * @param tr - отчёт по тестированию партии.
     * @return список тестов на влажность.
     * @throws SQLException
     */
    public ArrayList<HumidityTest> getHumidityTestList(TestReport tr) throws SQLException {
        ArrayList<HumidityTest> retVal = new ArrayList<>();

        if (connection == null) {
            return null;
        }

        Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        String query = String.format("SELECT ID, T_Q_ST_ID, BOTTLE_NUM, "
                + "BOTTLE_WEIGHT, WET_WEIGHT, DRY_WEIGHT, HUMIDITY "
                + "FROM T_Q_ST_HUMIDITY WHERE T_Q_ST_ID = %d", tr.getId());

        //System.out.println("\nExecuting query: " + query);
        try (ResultSet rset = stmt.executeQuery(query)) {
            while (rset.next()) {
                HumidityTest ht = new HumidityTest();
                ht.setId(rset.getInt(1));
                ht.setBottleNum(rset.getInt(3));
                ht.setBottleWeight(rset.getDouble(4));
                ht.setWetWeight(rset.getDouble(5));
                ht.setDryWeight(rset.getDouble(6));
                retVal.add(ht);
            }
        }

        return retVal;
    }

    /**
     * Получить все испытания образцов для заданной партии (отчёта по испытанию
     * партии).
     *
     * @param tr - отчёт по тестированию партии.
     * @return Список испытаний образцов.
     * @throws SQLException
     */
    public ArrayList<SampleTest> getSampleTests(TestReport tr) throws SQLException {
        ArrayList<SampleTest> retVal = new ArrayList<>();
        if (connection == null) {
            return null;
        }

        Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        String query = String.format("SELECT ID, T_Q_ST_ID, SAMPLE_NUM, "
                + "POSITION_SAMPLE, LENGTH, WIDTH, HEIGHT, WEIGHT, "
                + "DESTRUCT_LOAD FROM T_Q_ST_DETAILS WHERE T_Q_ST_ID = %d",
                tr.getId());

        // System.out.println("\nExecuting query: " + query);
        try (ResultSet rset = stmt.executeQuery(query)) {
            while (rset.next()) {
                SampleTest st = new SampleTest();
                st.setId(rset.getInt(1));
                st.setSampleNum(rset.getInt(3));
                st.setPosition(rset.getString(4));
                st.setLength(rset.getDouble(5));
                st.setWidth(rset.getDouble(6));
                st.setHeight(rset.getDouble(7));
                st.setWeight(rset.getDouble(8));
                st.setDestructLoad(rset.getDouble(9));

                retVal.add(st);
            }
        }

        return retVal;
    }

    /**
     * Получает из БД коэффициенты вариации по плотности за 30 дней от указанной
     * даты для указанной продукции.
     *
     * @param dt - дата, начиная от которой получаем вариации.
     * @param productId - продукция, для плотности которой получаем коэфициенты
     * вариации.
     * @return список коэффициентов вариации.
     * @throws SQLException
     * @throws java.text.ParseException
     */
    public ArrayList<Double> getDensityVariationsByDateAndProduct(String dt, int productId) throws SQLException, ParseException {
        if (connection == null) {
            return null;
        }

        ArrayList<Double> retVal = new ArrayList<>();   //Возвращаемое значение.

        if (dt.equals("")) {
            return retVal;
        }

        int density = this.getDensityByProductId(productId); //Плотность продукции, для которой выбираем коэффициенты вариации.

        //Сначала смотрим плотность продукции
        if (density <= 0) {
            return retVal;
        }

        //Выбираем начальную и конечную дату предыдущего месяца.
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        Date d = df.parse(dt);

        Calendar start = new GregorianCalendar();
        start.setTime(d);
        start.add(Calendar.MONTH, -1);
        start.set(Calendar.DAY_OF_MONTH, 1);

        Calendar end = new GregorianCalendar();
        end.setTime(d);
        end.add(Calendar.MONTH, -1);
        end.set(Calendar.DAY_OF_MONTH, end.getMaximum(Calendar.DAY_OF_MONTH));

        //Затем получаем 30 результатов за последние 30 дней.
        Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        String query = String.format("SELECT DENSITY_VARIATION variation "
                + "FROM T_Q_ST, T_PRODUCT "
                + "WHERE T_Q_ST.PRODUCT_ID = T_PRODUCT.ID and T_PRODUCT.DENSITY = %d "
                + "and T_Q_ST.TEST_DATE BETWEEN TO_DATE('%s', 'dd.MM.YYYY') AND TO_DATE('%s', 'dd.MM.YYYY') and rownum < 31 AND APPLY_RATIOS = 1 "
                + "order by T_Q_ST.PART_DATE DESC",
                density, df.format(start.getTime()), df.format(end.getTime()));

        //System.out.println("\nExecuting query: " + query);
        try (ResultSet rset = stmt.executeQuery(query)) {
            while (rset.next()) {
                retVal.add(rset.getDouble(1));
            }
        }

        return retVal;
    }

    /**
     * Возвращает из БД коэффициенты вариации за последний месмяц.
     *
     * @param dt - дата от которой отсчитывается месяц.
     * @param productId - код продукции для получения плотности.
     * @return Список коэффициентов вариации за последний месмяц.
     * @throws SQLException
     * @throws java.text.ParseException
     */
    public ArrayList<Double> getDurabilityVariationsByDateAndProduct(String dt, int productId) throws SQLException, ParseException {
        if (connection == null) {
            return null;
        }

        ArrayList<Double> retVal = new ArrayList<>();   //Возвращаемое значение.

        if (dt.equals("")) {
            return retVal;
        }

        //Выбираем начальную и конечную дату предыдущего месяца.
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        Date d = df.parse(dt);

        Calendar start = new GregorianCalendar();
        start.setTime(d);
        start.add(Calendar.MONTH, -1);
        start.set(Calendar.DAY_OF_MONTH, 1);

        Calendar end = new GregorianCalendar();
        end.setTime(d);
        end.add(Calendar.MONTH, -1);
        end.set(Calendar.DAY_OF_MONTH, end.getActualMaximum(Calendar.DAY_OF_MONTH));

        int density = this.getDensityByProductId(productId); //Плотность продукции, для которой выбираем коэффициенты вариации.
        //Затем получаем 30 результатов за последние 30 дней.
        Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        String query = String.format("SELECT DURABILITY_VARIATION variation "
                + "FROM T_Q_ST, T_PRODUCT "
                + "WHERE T_Q_ST.PRODUCT_ID = T_PRODUCT.ID and T_PRODUCT.DENSITY = %d "
                + "and T_Q_ST.PART_DATE BETWEEN TO_DATE('%s', 'dd.MM.YYYY') AND TO_DATE('%s', 'dd.MM.YYYY') and APPLY_RATIOS = 1 "
                + "order by T_Q_ST.PART_DATE DESC",
                density, df.format(start.getTime()), df.format(end.getTime()));

        //System.out.println("\nExecuting query: " + query);
        try (ResultSet rset = stmt.executeQuery(query)) {
            while (rset.next()) {
                retVal.add(rset.getDouble(1));
            }
        }
        //System.out.println("Result: " + retVal.size());
        return retVal;
    }

    /**
     * Возвращает список с продукцией.
     *
     * @return
     * @throws java.sql.SQLException
     */
    public ArrayList<Product> getProducts() throws SQLException {

        //Если список продуктов уже загружен, то просто возвращаем его.
        if (products != null && products.size() > 0) {
            return products;
        }

        if (connection == null) {
            return null;
        }

        products = new ArrayList<>();   //Возвращаемое значение.

        Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);

        String query = "SELECT id, name, density FROM T_PRODUCT order by name";

        //System.out.println("\nExecuting query: " + query);
        try (ResultSet rset = stmt.executeQuery(query)) {
            while (rset.next()) {
                Product p = new Product();
                p.setId(rset.getInt("id"));
                p.setName(rset.getString("name"));
                p.setDensity(rset.getInt("density"));
                products.add(p);
            }
        }

        return products;
    }

    //--------------------------------------------------------------------------
    /**
     * Возвращает все марки по плотности.
     *
     * @return
     * @throws SQLException
     */
    public ArrayList<String> getDensityMarks() throws SQLException {
        if (connection == null) {
            return null;
        }

        ArrayList<String> retVal = new ArrayList<>();   //Возвращаемое значение.

        Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);

        String query = "SELECT DISTINCT(DENSITY_MARK) AS DM FROM T_Q_ST WHERE DENSITY_MARK IS NOT NULL ORDER BY DENSITY_MARK";

        try (ResultSet rset = stmt.executeQuery(query)) {
            while (rset.next()) {
                retVal.add(rset.getString("DM"));
            }
        }

        return retVal;
    }

    //--------------------------------------------------------------------------
    /**
     * Возвращает все марки по прочности.
     *
     * @return
     * @throws SQLException
     */
    public ArrayList<String> getDurabilityMarks() throws SQLException {
        if (connection == null) {
            return null;
        }

        ArrayList<String> retVal = new ArrayList<>();   //Возвращаемое значение.

        Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);

        String query = "SELECT DISTINCT(DURABILITY_MARK) DM from t_q_st WHERE DURABILITY_MARK is not null ORDER BY DURABILITY_MARK";

        try (ResultSet rset = stmt.executeQuery(query)) {
            while (rset.next()) {
                retVal.add(rset.getString("DM"));
            }
        }

        return retVal;
    }

    //<editor-fold defaultstate="collapsed" desc="Статистика испытаний ГП">
    /**
     * Возвращает статистику испытаний ГП
     *
     * @param part - партия
     * @param year - год
     * @return
     * @throws SQLException
     * @throws ParseException
     */
    public ArrayList<Stat> getProductTestStatistics(Part part, Year year) throws SQLException, ParseException {
        return this.getProductTestStatistics(part, null, null, year, "", "");
    }

    /**
     * Возвращает статистику испытаний ГП
     *
     * @param part - партия
     * @param product - продукция
     * @param month - месяц
     * @param year - год
     * @param density - плотность
     * @param durability - прочность
     * @return
     * @throws SQLException
     * @throws ParseException
     */
    public ArrayList<Stat> getProductTestStatistics(Part part, Product product, Month month, Year year, String density, String durability) throws SQLException, ParseException {
        if (connection == null) {
            return null;
        }

        ArrayList<Stat> retVal = new ArrayList<>();   //Возвращаемое значение.

        Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);

        String partWhere = (part != null && part.getPartNum() != null) ? "AND T_Q_ST.PART_NO = '" + part.getPartNum() + "' " : "";
        String productWhere = (product != null && product.getName() != null) ? " AND T_Q_ST.PRODUCT_ID =" + product.getId() + " " : "";
        String monthWhere = (month != null && month.getName() != null) ? " AND EXTRACT (MONTH FROM T_Q_ST.TEST_DATE) = " + month.getId() + " " : "";
        String yearWhere = (year != null && year.getReturnValue() != 0) ? " AND EXTRACT (YEAR FROM T_Q_ST.TEST_DATE) = " + year.getReturnValue() + " " : "";
        String densityWhere = (density != null && !density.equals("")) ? "AND T_Q_ST.DENSITY_MARK = '" + density + "' " : "";
        String durabilityWhere = (durability != null && !durability.equals("")) ? " AND T_Q_ST.DURABILITY_MARK = '" + durability + "' " : "";

        String query = "SELECT T_Q_ST.ID,\n"
                + "  T_Q_ST.PART_NO,\n"
                + "  T_Q_ST.PRODUCT_ID,\n"
                + "  T_PRODUCT.NAME PRODUCT_NAME,\n"
                + "  TO_CHAR(T_Q_ST.PART_DATE, 'dd.mm.yyyy') PART_DATE,\n"
                + "  TO_CHAR(T_Q_ST.TEST_DATE, 'dd.mm.yyyy') TEST_DATE,\n"
                + "  T_Q_ST.PERSON_ID,\n"
                + "  T_PERSONAL.NAME PERSON_NAME,\n"
                + "  T_Q_ST.SAMPLE_TYPE,\n"
                + "  T_Q_ST.REQ_DENSITY,\n"
                + "  ROUND(T_Q_ST.REQ_DURABILITY, 0) REQ_DURABILITY,\n"
                + "  T_Q_ST.AVG_WET_DENSITY,\n"
                + "  ROUND(T_Q_ST.AVG_DRY_DENSITY, 0) AVG_DRY_DENSITY,\n"
                + "  T_Q_ST.DENSITY_MARK,\n"
                + "  T_Q_ST.DENSITY_VARIATION,\n"
                + "  ROUND(T_Q_ST.AVG_DURABILITY,0) AVG_DURABILITY,\n"
                + "  T_Q_ST.DURABILITY_MARK,\n"
                + "  T_Q_ST.DURABILITY_VARIATION,\n"
                + "  T_Q_ST.ADD_MEASURE_NAME_1,\n"
                + "  T_Q_ST.ADD_MEASURE_VALUE_1,\n"
                + "  T_Q_ST.ADD_MEASURE_UNIT_1,\n"
                + "  T_Q_ST.ADD_MEASURE_NAME_2,\n"
                + "  T_Q_ST.ADD_MEASURE_VALUE_2,\n"
                + "  T_Q_ST.ADD_MEASURE_UNIT_2,\n"
                + "  T_Q_ST.APPLY_RATIOS,\n"
                + "  T_Q_ST_HUMIDITY.HUMIDITY\n"
                + "FROM T_Q_ST, T_PRODUCT, T_PERSONAL, T_Q_ST_HUMIDITY\n"
                + "WHERE T_Q_ST.PRODUCT_ID = T_PRODUCT.ID AND T_Q_ST.PERSON_ID = T_PERSONAL.ID AND T_Q_ST.ID = T_Q_ST_HUMIDITY.T_Q_ST_ID\n"
                + partWhere + productWhere + monthWhere + yearWhere + densityWhere + durabilityWhere
                + "ORDER BY T_Q_ST.TEST_DATE DESC, T_Q_ST.PART_NO DESC";

        try (ResultSet rset = stmt.executeQuery(query)) {
            while (rset.next()) {
                Stat st = new Stat();
                DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
                Date d = df.parse(rset.getString("TEST_DATE"));
                st.setTestDate(d);
                st.setPartNo(rset.getString("PART_NO"));
                st.setProductName(rset.getString("PRODUCT_NAME"));
                st.setReqDensity(rset.getFloat("REQ_DENSITY"));
                st.setReqDurability(rset.getFloat("REQ_DURABILITY"));
                st.setAvgDryDensity(rset.getFloat("AVG_DRY_DENSITY"));
                st.setDensityMark(rset.getString("DENSITY_MARK"));
                st.setDensityVariation(rset.getFloat("DENSITY_VARIATION"));
                st.setAvgDurability(rset.getFloat("AVG_DURABILITY"));
                st.setDurabilityMark(rset.getString("DURABILITY_MARK"));
                st.setDurabilityVariation(rset.getFloat("DURABILITY_VARIATION"));
                st.setPersonName(rset.getString("PERSON_NAME"));
                st.setHumidity(rset.getFloat("HUMIDITY"));
                retVal.add(st);
            }
        }

        return retVal;
    }

    /**
     * Возвращает коэффициент вариации по плотности, полученный согласно
     * заданных параметров.
     *
     * @param part - партия
     * @param product - продукция
     * @param month - месяц
     * @param year - год
     * @param density - плотность
     * @param durability - прочность
     * @return
     * @throws SQLException
     */
    public float getDensityVariationStat(Part part, Product product, Month month, Year year, String density, String durability) throws SQLException {
        float retVal = 0;

        if (connection == null || density == null || density.equals("")) {
            return retVal;
        }

        Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);

        String densityWhere = (density != null && !density.equals("")) ? "WHERE T_Q_ST.DENSITY_MARK = '" + density + "' " : "";
        String partWhere = (part != null && part.getPartNum() != null) ? " AND T_Q_ST.PART_NO = '" + part.getPartNum() + "' " : "";
        String productWhere = (product != null && product.getName() != null) ? " AND T_Q_ST.PRODUCT_ID =" + product.getId() + " " : "";
        String monthWhere = (month != null && month.getName() != null) ? " AND EXTRACT (MONTH FROM T_Q_ST.TEST_DATE) = " + month.getId() + " " : "";
        String yearWhere = (year != null && year.getReturnValue() != 0) ? " AND EXTRACT (YEAR FROM T_Q_ST.TEST_DATE) = " + year.getReturnValue() + " " : "";
        String durabilityWhere = (durability != null && !durability.equals("")) ? " AND T_Q_ST.DURABILITY_MARK = '" + durability + "' " : "";

        String query = "SELECT AVG(DENSITY_VARIATION) DV FROM T_Q_ST " + densityWhere + yearWhere + monthWhere + partWhere + durabilityWhere + productWhere;

        try (ResultSet rset = stmt.executeQuery(query)) {
            if (rset.next()) {
                retVal = rset.getFloat("DV");
            }
        }

        return retVal;
    }

    /**
     * Возвращает коэффициент вариации по прочности, полученный согласно
     * заданных параметров.
     *
     * @param part - партия
     * @param product - продукция
     * @param month - месяц (коэффициэнт вариации расчитывается по месяцу,
     * идущему перед этим.)
     * @param year - год
     * @param density - плотность
     * @param durability - прочность
     * @return
     * @throws SQLException
     */
    public float getDurabilityVariationStat(Part part, Product product, Month month, Year year, String density, String durability) throws SQLException {
        float retVal = 0;

        if (connection == null || density == null || density.isEmpty()) {
            return retVal;
        }

        Month monthCopy = month.copy();
        //Вычитаем один месяц из указанного года и месяца.
        if (monthCopy != null && monthCopy.substractByOne()) {
            year.setReturnValue(year.getReturnValue() - 1);
            year.setDisplayValue(String.valueOf(year.getReturnValue()));
        }

        Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);

        String densityWhere = " AND T_Q_ST.DENSITY_MARK = '" + density + "' ";
        String durabilityWhere = (durability != null && !durability.equals("")) ? " AND T_Q_ST.DURABILITY_MARK = '" + durability + "' " : "";
        String partWhere = (part != null && part.getPartNum() != null) ? " AND T_Q_ST.PART_NO = '" + part.getPartNum() + "' " : "";
        String productWhere = (product != null && product.getName() != null) ? " AND T_Q_ST.PRODUCT_ID =" + product.getId() + " " : "";
        String monthWhere = (monthCopy != null && monthCopy.getName() != null) ? " AND EXTRACT (MONTH FROM T_Q_ST.PART_DATE) = " + monthCopy.getId() + " " : "";
        String yearWhere = (year != null && year.getReturnValue() != 0) ? " AND EXTRACT (YEAR FROM T_Q_ST.PART_DATE) = " + year.getReturnValue() + " " : "";

        String query = "SELECT AVG(DURABILITY_VARIATION) DV FROM T_Q_ST WHERE APPLY_RATIOS = 1 \n" + densityWhere + durabilityWhere + yearWhere + monthWhere + partWhere + productWhere;

        try (ResultSet rset = stmt.executeQuery(query)) {
            if (rset.next()) {
                retVal = rset.getFloat("DV");
            }
        }

        return retVal;
    }

    /**
     * Возвращает список "класс по плотности - требуемая плотность" для таблицы
     * Требуемая плотность.
     *
     * @param month - месяц, выбранный пользователем.
     * @param year - год, выбранный пользователем.
     * @return
     * @throws SQLException
     */
    public ArrayList<RequiredDensity> getRequiredDensities(Month month, Year year) throws SQLException {
        ArrayList<RequiredDensity> retVal = new ArrayList<>();

        if (connection == null || month == null || month.getName() == null
                || month.getId() == 0 || year == null || year.getReturnValue() == 0) {
            return retVal;
        }

        Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);

        String query = String.format("SELECT DISTINCT(DENSITY_MARK) DENSITY_MARK,\n"
                + " SUPP_PKG.GET_REQ_DENSITY(%d - 1, %d, DENSITY_MARK) P\n"
                + " FROM T_Q_ST ORDER BY DENSITY_MARK", month.getId(), year.getReturnValue());

        try (ResultSet rset = stmt.executeQuery(query)) {
            while (rset.next()) {
                RequiredDensity rd = new RequiredDensity();
                rd.setName(rset.getString("DENSITY_MARK"));
                rd.setValue(rset.getInt("P"));
                if (rd.getName() != null && !rd.getName().equals("")) {
                    retVal.add(rd);
                }
            }
        }

        return retVal;
    }

    /**
     * Возвращает список "класс по прочности - требуемая прочность" для таблицы
     * Требуемая прочность.
     *
     * @param month - месяц, выбранный пользователем.
     * @param year - год, выбранный пользователем.
     * @return
     * @throws SQLException
     */
    public ArrayList<RequiredDurability> getRequiredDurabilities(Month month, Year year) throws SQLException {
        ArrayList<RequiredDurability> retVal = new ArrayList<>();

        if (connection == null || month == null || month.getName() == null
                || month.getId() == 0 || year == null || year.getReturnValue() == 0) {
            return retVal;
        }

        Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);

        String query = String.format("SELECT DISTINCT(DURABILITY_MARK) DM,\n"
                + " SUPP_PKG.GET_REQ_DURABILITY(%d - 1, %d, DURABILITY_MARK) P\n"
                + " FROM T_Q_ST\n"
                + " ORDER BY DURABILITY_MARK", month.getId(), year.getReturnValue());

        try (ResultSet rset = stmt.executeQuery(query)) {
            while (rset.next()) {
                RequiredDurability rd = new RequiredDurability();
                rd.setName(rset.getString("DM"));
                rd.setValue(rset.getFloat("P"));
                if (rd.getName() != null && !rd.getName().equals("")) {
                    retVal.add(rd);
                }
            }
        }

        return retVal;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Реестр паспортов ГП">
    /**
     * Возвращает список пасспортов по указанным критериям.
     *
     * @param month - месяц (не обязательно)
     * @param year - год (обязательно).
     * @return
     * @throws SQLException
     * @throws java.text.ParseException
     */
    public ArrayList<ProductPassport> getProductPassports(Month month, Year year) throws SQLException, ParseException {
        ArrayList<ProductPassport> retVal = new ArrayList<>();   //Возвращаемое значение.

        if (connection == null) {
            return retVal;
        }

        if (year == null || year.getReturnValue() == 0) {
            return retVal;
        }

        Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);

        String monthWhere = (month != null && month.getName() != null) ? " AND EXTRACT (MONTH FROM DAY) = " + month.getId() + " " : "";

        String query = String.format("SELECT ID, PART_NO, TO_CHAR(DAY, 'dd.mm.yyyy') DAY, PRODUCT_ID, COMPRESS_STRENGTH,\n"
                + "  REQ_STRENGTH, AVG_DENSITY, STEAM_FACTOR, FROST_RESIST,\n"
                + "  HEAT_CONDUCTION, SHRINKAGE, ACTIVITY, NOTES, STRENGTH_CLASS,\n"
                + "  HUMIDITY FROM T_Q_PASS WHERE EXTRACT(YEAR FROM DAY) = %d", year.getReturnValue());

        query += monthWhere + " ORDER BY DAY DESC";

        try (ResultSet rset = stmt.executeQuery(query)) {
            while (rset.next()) {
                ProductPassport pp = new ProductPassport();
                pp.setId(rset.getInt("ID"));
                pp.setPartNum(rset.getString("PART_NO"));

                DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
                Date d = df.parse(rset.getString("DAY"));
                pp.setDate(d);

                for (Product p : getProducts()) {
                    if (p.getId() == rset.getInt("PRODUCT_ID")) {
                        pp.setProduct(p);
                        break;
                    }
                }

                pp.setAvgDurability(rset.getFloat("COMPRESS_STRENGTH"));
                pp.setReqDurability(rset.getFloat("REQ_STRENGTH"));
                pp.setAvgDensity(rset.getFloat("AVG_DENSITY"));
                pp.setSteamFactor(rset.getFloat("STEAM_FACTOR"));
                pp.setFrostResist(rset.getString("FROST_RESIST"));
                pp.setHeatConduction(rset.getFloat("HEAT_CONDUCTION"));
                pp.setShrinkage(rset.getFloat("SHRINKAGE"));
                pp.setActivity(rset.getString("ACTIVITY"));
                pp.setNotes(rset.getString("NOTES"));
                pp.setDurabilityMark(rset.getString("STRENGTH_CLASS"));
                pp.setHumidity(rset.getFloat("HUMIDITY"));

                retVal.add(pp);
            }
        }

        return retVal;
    }
    //</editor-fold>

    public ProductPassport getProductPassportParameters(ProductPassport productPassport) throws SQLException {
        if (connection == null) {
            return productPassport;
        }

        Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);

        String query = String.format("SELECT HEAT_CONDUCTION, FROST_RESIST, STEAM_FACTOR, SHRINKAGE, ACTIVITY "
                + "FROM T_Q_PARAMETERS WHERE DENSITY = (SELECT DENSITY FROM T_PRODUCT WHERE ID = %d)",
                productPassport.getProduct().getId());

        try (ResultSet rset = stmt.executeQuery(query)) {
            if (rset.next()) {
                productPassport.setHeatConduction(rset.getFloat("HEAT_CONDUCTION"));
                productPassport.setFrostResist(rset.getString("FROST_RESIST"));
                productPassport.setSteamFactor(rset.getFloat("STEAM_FACTOR"));
                productPassport.setShrinkage(rset.getFloat("SHRINKAGE"));
                productPassport.setActivity(rset.getString("ACTIVITY"));
            }
        }

        return productPassport;
    }

    //--------------------- Вспомогательные -------------------------------- //
    int currentDensity = 0;     //Хранит информацию о плотности с момента последнего запроса.
    int currentProductId = 0;   //Хранит информацию о типе продукции с момента последнего запроса.

    private int getDensityByProductId(int productId) throws SQLException {
        //Если код продукцции совпадает с предыдущим кодом, то просто возвращаем плотность для предыдущего продукта.
        if (productId == currentProductId) {
            return currentDensity;
        }

        if (connection == null) {
            return 0;
        }

        int density = 0; //Плотность продукции, для которой выбираем коэффициенты вариации.

        //Сначала смотрим плотность продукции
        Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        String query = String.format("SELECT  DENSITY FROM T_PRODUCT WHERE ID = %d", productId);

        //System.out.println("\nExecuting query: " + query);
        try (ResultSet rset = stmt.executeQuery(query)) {
            if (rset.next()) {
                density = rset.getInt(1);
                //System.out.println("\nResult is " + density);
            }

            //Сохраняем код продукции и плотность.
            currentDensity = density;
            currentProductId = productId;
        }

        return density;
    }
}
