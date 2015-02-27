/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package producttest.bll;

import DAL.DataContext;
import java.sql.SQLException;
import java.sql.SQLRecoverableException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import producttest.model.HumidityTest;
import producttest.model.Month;
import producttest.model.Part;
import producttest.model.Person;
import producttest.model.Result;
import producttest.model.SampleTest;
import producttest.model.SampleType;
import producttest.model.TestReport;
import producttest.model.Product;
import producttest.model.RequiredDensity;
import producttest.model.RequiredDurability;
import producttest.model.Stat;
import producttest.model.Year;

/**
 *
 * @author tonkushin
 */
public class BLogic {

    //<editor-fold defaultstate="collapsed" desc="Поля, геттеры, сеттеры">
    private ObservableList<Part> partNumbers; //Номера партий.
    private ObservableList<SampleType> sampleTypes; //Типы образцов
    private ObservableList<SampleTest> sampleTests; //Испытания (для таблицы Испытание образцов)
    private ObservableList<HumidityTest> humidityTests; //Испытания на влажность (для таблицы оценка влажности).
    private ObservableList<Result> results; //Результаты испытаний.
    private ObservableList<Person> personal; //Список сотрудников-лаборантов.
    private ObservableList<String> positions; //Позиции испытуемых образцов в массиве.
    private ObservableList<Product> products; //Продукция.
    private ObservableList<Month> months;    //Месяцы в году.
    private ObservableList<Year> years;    //Годы.
    private ObservableList<String> densityMarks; //Марки по плотности.
    private ObservableList<String> durabilityMarks; //Марки по прочности.
    private ObservableList<Stat> productTestStatistics; //Статистика испытаний ГП.
    private ObservableList<RequiredDensity> requiredDensities;  //Требуемые плотности.
    private ObservableList<RequiredDurability> requiredDurabilities; //Требуемые прочности.

    //Требуемые плотности.
    public ObservableList<RequiredDensity> getRequiredDensities() {
        return requiredDensities;
    }

    public void setRequiredDensities(ObservableList<RequiredDensity> requiredDensities) {
        this.requiredDensities = requiredDensities;
    }

    //Требуемые прочности.
    public ObservableList<RequiredDurability> getRequiredDurabilities() {
        return requiredDurabilities;
    }

    public void setRequiredDurabilities(ObservableList<RequiredDurability> requiredDurabilities) {
        this.requiredDurabilities = requiredDurabilities;
    }
    //Коэф. вариации по прочности во вкладке со статистикой испытаний ГП.
    private FloatProperty durabilityVarStat;

    public final void setDurabilityVarStat(Float durability) {
        this.durabilityVarStat.set(durability);
    }

    public final Float getDurabilityVarStat() {
        return this.durabilityVarStat.get();
    }

    public FloatProperty durabilityVarStatProperty() {
        return this.durabilityVarStat;
    }

    //Коэф. вариации по плотности во вкладке со статистикой испытаний ГП.
    private FloatProperty densityVarStat;

    public final void setDensityVarStat(Float density) {
        this.densityVarStat.set(density);
    }

    public final Float getDensityVarStat() {
        return this.densityVarStat.get();
    }

    public FloatProperty densityVarStatProperty() {
        return this.densityVarStat;
    }

    //Выбранный месяц во вкладке со статистикой испытаний ГП.
    private ObjectProperty<Month> selectedMonthStat;

    public final void setSelectedMonthStat(Month selectedMonthStat) {
        this.selectedMonthStat.set(selectedMonthStat);
    }

    public final Month getSelectedMonthStat() {
        return this.selectedMonthStat.get();
    }

    public ObjectProperty<Month> selectedMonthStatProperty() {
        return this.selectedMonthStat;
    }

    //Выбранный год во вкладке со статистикой испытаний ГП.
    private ObjectProperty<Year> selectedYearStat;

    public final void setSelectedYearStat(Year selectedYearStat) {
        this.selectedYearStat.set(selectedYearStat);
    }

    public final Year getSelectedYearStat() {
        return this.selectedYearStat.get();
    }

    public ObjectProperty<Year> selectedYearStatProperty() {
        return this.selectedYearStat;
    }

    //Выбранная марка по плотности во вкладке со статистикой испытаний ГП.
    private StringProperty selectedDensityMarkStat;

    public final void setSelectedDensityMarkStat(String selectedDensityMarkStat) {
        this.selectedDensityMarkStat.set(selectedDensityMarkStat);
    }

    public final String getSelectedDensityMarkStat() {
        return selectedDensityMarkStat.get();
    }

    public StringProperty selectedDensityMarkStatProperty() {
        return this.selectedDensityMarkStat;
    }

    //Выбранная марка по прочности во вкладке со статистикой испытаний ГП.
    private StringProperty selectedDurabilityMarkStat;

    public final void setSelectedDurabilityMarkStat(String selectedDurabilityMarkStat) {
        this.selectedDurabilityMarkStat.set(selectedDurabilityMarkStat);
    }

    public final String getSelectedDurabilityMarkStat() {
        return selectedDurabilityMarkStat.get();
    }

    public StringProperty selectedDurabilityMarkStatProperty() {
        return this.selectedDurabilityMarkStat;
    }

    //Количество записей в таблице Статистики ГП во вкладке со статистикой испытаний ГП.
    private IntegerProperty tblStatisticsItemsCount;

    public final void setTblStatisticsItemsCount(Integer tblStatisticsItemsCount) {
        this.tblStatisticsItemsCount.set(tblStatisticsItemsCount);
    }

    public final Integer getTblStatisticsItemsCount() {
        return this.tblStatisticsItemsCount.get();
    }

    public IntegerProperty tblStatisticsItemsCountProperty() {
        return tblStatisticsItemsCount;
    }

    //Выбранная номенклатура во вкладке со статистикой испытаний ГП.
    private ObjectProperty<Product> selectedProdNameStat;

    public final void setSelectedProdNameStat(Product selectedProdNameStat) {
        this.selectedProdNameStat.set(selectedProdNameStat);
    }

    public final Product getSelectedProdNameStat() {
        return this.selectedProdNameStat.get();
    }

    public ObjectProperty<Product> selectedProdNameStatProperty() {
        return this.selectedProdNameStat;
    }

    //Выбранная партия во вкладке со статистикой испытаний ГП.
    private ObjectProperty<Part> selectedPartNumStat;

    public final void setSelectedPartNumStat(Part selectedPartNumStat) {
        this.selectedPartNumStat.set(selectedPartNumStat);
    }

    public final Part getSelectedPartNumStat() {
        return this.selectedPartNumStat.get();
    }

    public ObjectProperty<Part> selectedPartNumStatProperty() {
        return this.selectedPartNumStat;
    }
    //--------------------------------------------------------------------------

    //Срядняя плотность во влажном состоянии.
    private DoubleProperty avgWetDensity;

    public final void setAvgWetDensity(Double avgWetDensity) {
        this.avgWetDensity.set(avgWetDensity);
    }

    public final Double getAvgWetDensity() {
        return this.avgWetDensity.get();
    }

    public DoubleProperty avgWetDensityProperty() {
        return this.avgWetDensity;
    }

    //Средняя плотность в сухом состоянии.
    private DoubleProperty avgDryDensity;

    public final void setAvgDryDensity(Double avgDryDensity) {
        this.avgDryDensity.set(avgDryDensity);
    }

    public final Double getAvgDryDensity() {
        return this.avgDryDensity.get();
    }

    public DoubleProperty avgDryDensityProperty() {
        return this.avgDryDensity;
    }

    //Марка по плотности.
    private StringProperty densityMark;

    public final void setDensityMark(String densityMark) {
        this.densityMark.set(densityMark);
    }

    public final String getDensityMark() {
        return this.densityMark.get();
    }

    public StringProperty densityMarkProperty() {
        return this.densityMark;
    }

    //Текущий коэффициент вариации партии по плотности Vm.
    private DoubleProperty densityVariation;

    public final void setDensityVariation(Double densityVariation) {
        this.densityVariation.set(densityVariation);
    }

    public final Double getDensityVariation() {
        return this.densityVariation.get();
    }

    public DoubleProperty densityVariationProperty() {
        return this.densityVariation;
    }

    //Предел прочности при сжатии Rm.
    private DoubleProperty avgDuarbility;

    public final void setAvgDurability(Double avgDurability) {
        this.avgDuarbility.set(avgDurability);
    }

    public final Double getAvgDurability() {
        return this.avgDuarbility.get();
    }

    public DoubleProperty avgDurabilityProperty() {
        return this.avgDuarbility;
    }

    //Класс по прочности B.
    private StringProperty durabilityMark;

    public final void setDurabilityMark(String dm) {
        this.durabilityMark.set(dm);
    }

    public final String getDurabilityMark() {
        return this.durabilityMark.get();
    }

    public StringProperty durabilityMarkProperty() {
        return this.durabilityMark;
    }

    //Текущий коэффициент вариации в партии (по прочности) Vm.
    private DoubleProperty durabilityVariation;

    public final void setDurabilityVariation(Double dv) {
        this.durabilityVariation.set(dv);
    }

    public final Double getDurabilityVariation() {
        return this.durabilityVariation.get();
    }

    public DoubleProperty durabilityVariationProperty() {
        return this.durabilityVariation;
    }

    /**
     * Возвращает список номеров партий.
     *
     * @return номера партий
     */
    public ObservableList<Part> getPartNumbers() {
        return partNumbers;
    }

    /**
     * Задаёт номера партий.
     *
     * @param partNumbers the partNumbers to set
     */
    public void setPartNumbers(ObservableList<Part> partNumbers) {
        this.partNumbers = partNumbers;
    }

    /**
     * @return the sampleTypes - список типов образцов
     */
    public ObservableList<SampleType> getSampleTypes() {
        return sampleTypes;
    }

    /**
     * @param sampleTypes - список типов образцов.
     */
    public void setSampleTypes(ObservableList<SampleType> sampleTypes) {
        this.sampleTypes = sampleTypes;
    }

    /**
     * @return the sampleTests
     */
    public ObservableList<SampleTest> getSampleTests() {
        return sampleTests;
    }

    /**
     * @return the results
     */
    public ObservableList<Result> getResults() {
        return results;
    }

    /**
     * @param results the results to set
     */
    public void setResults(ObservableList<Result> results) {
        this.results = results;
    }

    /**
     * @return the humidityTests
     */
    public ObservableList<HumidityTest> getHumidityTests() {
        return humidityTests;
    }

    /**
     * @param humidityTests the humidityTests to set
     */
    public void setHumidityTests(ObservableList<HumidityTest> humidityTests) {
        this.humidityTests = humidityTests;
    }

    /**
     * @param sampleTests the sampleTests to set
     */
    public void setSampleTests(ObservableList<SampleTest> sampleTests) {
        this.sampleTests = sampleTests;
    }

    //Выбранный вид образца (70х70 или 100х100)
    private SampleType selectedSampleType;

    /**
     * @return Выбранный вид образца (70х70 или 100х100)
     */
    public SampleType getSelectedSampleType() {
        return selectedSampleType;
    }

    /**
     * @param selectedSampleType Выбранный вид образца (70х70 или 100х100)
     */
    public void setSelectedSampleType(SampleType selectedSampleType) {
        this.selectedSampleType = selectedSampleType;
        fireSelectedSampleTypeChanged(selectedSampleType);
    }

    //Прочие испытания.
    private ObservableList<String> additionalMeasures;

    /**
     * @return the additionalMeasures
     */
    public ObservableList<String> getAdditionalMeasures() {
        return additionalMeasures;
    }

    /**
     * @param additionalMeasures the additionalMeasures to set
     */
    public void setAdditionalMeasures(ObservableList<String> additionalMeasures) {
        this.additionalMeasures = additionalMeasures;
    }

    //Информационные сообщения и ошибки.
    private StringProperty infoMessage;

    public final String getInfoMessage() {
        return this.infoMessage.get();
    }

    public final void setInfoMessage(String im) {
        this.infoMessage.set(im);
    }

    public StringProperty infoMessageProperty() {
        return this.infoMessage;
    }

    //Выбранный сотрудник.
    private Person selectedPerson;

    public Person getSelectedPerson() {
        return this.selectedPerson;
    }

    public void setSelectedPerson(Person p) {
        this.selectedPerson = p;
        fireSelectedPersonChanged(p);
        CheckIfSaveTestAllowed();
    }

    /**
     * @return the personal
     */
    public ObservableList<Person> getPersonal() {
        return personal;
    }

    /**
     * @param personal the personal to set
     */
    public void setPersonal(ObservableList<Person> personal) {
        this.personal = personal;
    }

    //Выбранная партия.
    private Part selectedPart;

    public void setSelectedPart(Part selectedPart) {

        //Смотрим, что новое значение партии не совпадает со старым.
        if (this.selectedPart != selectedPart) {
            this.selectedPart = selectedPart;

            this.humidityTests.clear();
            this.sampleTests.clear();
            this.results.clear();

            setDensityMark("");
            setAvgDurability(0d);
            setDurabilityMark("");
            setDurabilityVariation(0d);

            setAddMeasureName1("");
            setAddMeasureValue1(0d);
            setAddMeasureUnit1("");

            setAddMeasureName2("");
            setAddMeasureValue2(0d);
            setAddMeasureUnit2("");

            setApplyRatios(Boolean.FALSE);

            //Если контекст данных не пустой, 
            //то пытаемся загрузить данные об испытаниях партии.
            if (dc != null) {
                try {
                    TestReport testReport = dc.getSampleTest(selectedPart);

                    //Если нет данных о партии, то ничего не делаем.
                    if (testReport == null || testReport.getId() < 0) {
                        this.setSelectedPerson(null);

                        //Автоматически подставляем текущую дату, если для партии ещё ничего не заведено.
                        if (getSelectedTestDate().equals("")) {
                            DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
                            this.setSelectedTestDate(df.format(new Date()));
                        }

                        return;
                    }

                    //Загружаем информацию о сотруднике.
                    for (Person p : personal) {
                        if (p.getId() == testReport.getPersonId()) {
                            this.setSelectedPerson(p);
                        }
                    }

                    //Загружаем дату испытаний.
                    DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
                    setSelectedTestDate(df.format(testReport.getTestDate()));

                    //Загружаем тип образцов.
                    for (SampleType st : sampleTypes) {
                        if (st.getName().equals(testReport.getSampleType())) {
                            this.setSelectedSampleType(st);
                        }
                    }

                    //Загружаем тесты на влажность.
                    ArrayList<HumidityTest> humidityTestList = dc.getHumidityTestList(testReport);

                    for (HumidityTest ht : humidityTestList) {
                        this.humidityTests.add(ht);
                    }

                    //Загружаем испытания образцов.
                    ArrayList<SampleTest> sampleTestList = dc.getSampleTests(testReport);

                    for (SampleTest st : sampleTestList) {
                        this.sampleTests.add(st);
                    }

                    //Пересчитываем результаты испытаний.
                    if (results.size() == sampleTests.size()) {
                        for (int i = 0; i < results.size(); i++) {
                            SampleTest st = sampleTests.get(i);

                            //Плотность во влажном состоянии.
                            Double wetDensity = Calculation.calcWetDensity(st.getWidth(), st.getHeight(), st.getLength(), st.getWeight());
                            results.get(i).setWetDensity(wetDensity);

                            if (humidityTests.size() > 0) {
                                //Плотность в сухом состоянии.
                                Double dryDensity = Calculation.calcDryDensity(wetDensity, humidityTests.get(0).getHumidity());
                                results.get(i).setDryDensity(dryDensity);

                                //Предел прочности при сжатии.
                                results.get(i).setDurability(Calculation.calcDurability(getSelectedSampleType().toString(), humidityTests.get(0).getHumidity(), st.getDestructLoad(), st.getLength(), st.getWidth()));
                            }
                        }

                        //Средняя плотность в сухом состоянии.
                        setAvgDryDensity(Calculation.calcAvgDryDensity(results));

                        //Рассчитываем средний предел прочности при сжатии.
                        setAvgDurability(Calculation.calcAvgDurability(results));

                        //Пересчитываем  марку по прочности.
                        ArrayList<Double> vars = dc.getDurabilityVariationsByDateAndProduct(getSelectedTestDate(), selectedPart.getProductId());
                        Double avgVariation = Calculation.calcDurabilityVariationByParts(vars);
                        Double kt = Calculation.calcReqDurabilityRatio(avgVariation);
                        setDurabilityMark(Calculation.calcDurabilityMark(getAvgDurability(), kt, results));

                        //Пересчитываем коэффициент вариации по прочности.
                        setDurabilityVariation(Calculation.calcDurabilityVariation(getAvgDurability(), results));
                    }

                    //Расчитываем требуемую плотность. ---
                    ArrayList<Double> Vm = dc.getDensityVariationsByDateAndProduct(df.format(testReport.getTestDate()), testReport.getProductId());

                    if (Vm.size() < 30 && getDensityMark().length() > 0) {
                        //Если в статистике меньше 30 образцов, то устанавливаем требуемую плотность
                        //равной значению марки по плотности.
                        String tmp = getDensityMark().substring(1);
                        setReqDensity(Double.valueOf(tmp));
                    } else {

                        Double Vn = Calculation.calcDensityVariationByParts(Vm);    //Средний коэф вариации по партиям.

                        Double Kt = Calculation.calcReqDensityRatio(Vn);            //Коэффициент требуемой плотности.

                        setReqDensity(Calculation.calcReqDensity(Kt, getDensityMark()));
                    }

                    //Рассчитываем требуемую прочность
                    ArrayList<Double> vars = dc.getDurabilityVariationsByDateAndProduct(getSelectedTestDate(), selectedPart.getProductId());
                    Double kt = 1.43;

                    //При количестве результатов меньще 30 делаем kt=1.43
                    if (vars.size() >= 30) {
                        Double avgVariation = Calculation.calcDurabilityVariationByParts(vars);
                        kt = Calculation.calcReqDurabilityRatio(avgVariation);
                    }

                    Double Rt = Calculation.calcReqDurability(kt, getDurabilityMark());
                    setReqDurability(Rt);
                    //---

                    //Устанавливаем дополнительные измерения.
                    setAddMeasureName1(testReport.getAddMeasureName1() != null ? "" : testReport.getAddMeasureName1());
                    setAddMeasureValue1(testReport.getAddMeasureValue1());
                    setAddMeasureUnit1(testReport.getAddMeasureUnit1() != null ? "" : testReport.getAddMeasureUnit1());

                    setAddMeasureName2(testReport.getAddMeasureName2() != null ? "" : testReport.getAddMeasureName2());
                    setAddMeasureValue2(testReport.getAddMeasureValue2());
                    setAddMeasureUnit2(testReport.getAddMeasureUnit2() != null ? "" : testReport.getAddMeasureUnit2());

                    //Устанавливаем флаг "Принять текущие коэффициенты вариации"
                    this.setApplyRatios(testReport.getApplyRatios());

                    //Проверяем возможность сохранения.
                    CheckIfSaveTestAllowed();

                    //Обнуляем строку состояния.
                    setInfoMessage("");

                } catch (SQLException | ParseException ex) {
                    Logger.getLogger(BLogic.class.getName()).log(Level.SEVERE, null, ex);
                    infoMessage.set(ex.getLocalizedMessage());
                }
            }
        }
    }

    public Part getSelectedPart() {
        return this.selectedPart;
    }

    //Выбранная дата.
    private StringProperty selectedTestDate;

    public final String getSelectedTestDate() {
        return this.selectedTestDate.get();
    }

    public final void setSelectedTestDate(String selectedTestDate) {
        this.selectedTestDate.set(selectedTestDate);
    }

    public StringProperty selectedTestDateProperty() {
        return this.selectedTestDate;
    }

    //Контекст данных.
    DataContext dc;

    //Запрет на сохранение данных (деактивация кнопки "Сохранить").
    private BooleanProperty saveButtonDisabled;

    public final Boolean getSaveButtonDisabled() {
        return this.saveButtonDisabled.get();
    }

    public final void setSaveButtonDisabled(Boolean b) {
        this.saveButtonDisabled.set(b);
    }

    public BooleanProperty saveButtonDisabledProperty() {
        return this.saveButtonDisabled;
    }

    //Название дополнительного измерения.
    private StringProperty addMeasureName1;

    public final String getAddMeasureName1() {
        return this.addMeasureName1.get();
    }

    public final void setAddMeasureName1(String addMeasureName) {
        this.addMeasureName1.set(addMeasureName);
        fireAddMeasureName1Changed(addMeasureName);
    }

    private StringProperty addMeasureName1Property() {
        return this.addMeasureName1;
    }

    //Значение дополнительного измерения.
    private DoubleProperty addMeasureValue1;

    public final Double getAddMeasureValue1() {
        return this.addMeasureValue1.get();
    }

    public final void setAddMeasureValue1(Double addMeasureValue) {
        this.addMeasureValue1.set(addMeasureValue);
    }

    public DoubleProperty addMeasureValue1Property() {
        return this.addMeasureValue1;
    }

    //Единица измерения дополнительного измерения.
    private StringProperty addMeasureUnit1;

    public final String getAddMeasureUnit1() {
        return this.addMeasureUnit1.get();
    }

    public final void setAddMeasureUnit1(String addMeasureUnit) {
        this.addMeasureUnit1.set(addMeasureUnit);
    }

    public StringProperty addMeasureUnit1Property() {
        return this.addMeasureUnit1;
    }

    //Название дополнительного измерения.
    private StringProperty addMeasureName2;

    public final String getAddMeasureName2() {
        return this.addMeasureName2.get();
    }

    public final void setAddMeasureName2(String addMeasureName) {
        this.addMeasureName2.set(addMeasureName);
        fireAddMeasureName2Changed(addMeasureName);
    }

    private StringProperty addMeasureName2Property() {
        return this.addMeasureName2;
    }

    //Значение дополнительного измерения.
    private DoubleProperty addMeasureValue2;

    public final Double getAddMeasureValue2() {
        return this.addMeasureValue2.get();
    }

    public final void setAddMeasureValue2(Double addMeasureValue) {
        this.addMeasureValue2.set(addMeasureValue);
    }

    public DoubleProperty addMeasureValue2Property() {
        return this.addMeasureValue2;
    }

    //Единица измерения дополнительного измерения.
    private StringProperty addMeasureUnit2;

    public final String getAddMeasureUnit2() {
        return this.addMeasureUnit2.get();
    }

    public final void setAddMeasureUnit2(String addMeasureUnit) {
        this.addMeasureUnit2.set(addMeasureUnit);
    }

    public StringProperty addMeasureUnit2Property() {
        return this.addMeasureUnit2;
    }

    //Требуемая плотность.
    private DoubleProperty reqDensity;

    public final Double getReqDensity() {
        return this.reqDensity.get();
    }

    public final void setReqDensity(Double reqDensity) {
        this.reqDensity.set(reqDensity);
    }

    public DoubleProperty reqDensityProperty() {
        return this.reqDensity;
    }

    //Требуемая прочность.
    private DoubleProperty reqDurability;

    public final Double getReqDurability() {
        return this.reqDurability.get();
    }

    public final void setReqDurability(Double reqDurability) {
        this.reqDurability.set(reqDurability);
    }

    public DoubleProperty reqDurabilityProperty() {
        return this.reqDurability;
    }
    //---------------------------------
    //Слушатели событий от экземпляра этого класса.
    List<IValueChanged> listeners = new ArrayList<>();

    //Применить текущие коэффициенты вариации.
    private BooleanProperty applyRatios;

    public final Boolean getApplyRatios() {
        return this.applyRatios.get();
    }

    public final void setApplyRatios(Boolean apply) {
        this.applyRatios.set(apply);
    }

    public BooleanProperty applyRatiosProperty() {
        return this.applyRatios;
    }

    public ObservableList<String> getPositions() {
        return this.positions;
    }

    public void setPositions(ObservableList<String> positions) {
        this.positions = positions;
    }

    //---------------------------------
    //Продукция.
    /**
     * @return the products
     */
    public ObservableList<Product> getProducts() {
        return products;
    }

    /**
     * @param products the products to set
     */
    public void setProducts(ObservableList<Product> products) {
        this.products = products;
    }

    //---------------------------------
    //Месяцы
    /**
     * @return the months
     */
    public ObservableList<Month> getMonths() {
        return months;
    }

    /**
     * @param months the months to set
     */
    public void setMonths(ObservableList<Month> months) {
        this.months = months;
    }

    //---------------------------------
    //Годы
    /**
     * @return the years
     */
    public ObservableList<Year> getYears() {
        return years;
    }

    /**
     * @param years the years to set
     */
    public void setYears(ObservableList<Year> years) {
        this.years = years;
    }

    //---------------------------------
    //Плотности
    /**
     * @return the densityMarks
     */
    public ObservableList<String> getDensityMarks() {
        return densityMarks;
    }

    /**
     * @param densityMarks the densityMarks to set
     */
    public void setDensityMarks(ObservableList<String> densityMarks) {
        this.densityMarks = densityMarks;
    }

    //---------------------------------
    //Марки по прочности.
    /**
     * @return the durabilityMarks
     */
    public ObservableList<String> getDurabilityMarks() {
        return durabilityMarks;
    }

    /**
     * @param durabilityMarks the durabilityMarks to set
     */
    public void setDurabilityMarks(ObservableList<String> durabilityMarks) {
        this.durabilityMarks = durabilityMarks;
    }

    //--------------------------------------------------------------------------
    //Статистика испытаний ГП.
    /**
     * @return the productTestStatistics
     */
    public ObservableList<Stat> getProductTestStatistics() {
        return productTestStatistics;
    }

    /**
     * @param productTestStatistics the productTestStatistics to set
     */
    public void setProductTestStatistics(ObservableList<Stat> productTestStatistics) {
        this.productTestStatistics = productTestStatistics;
    }

//</editor-fold>
    public BLogic() {
        this.additionalMeasures = FXCollections.observableArrayList("Прочность на изгиб", "Теплопроводность", "Морозостойкость", "Усадка");
        this.positions = FXCollections.observableArrayList("к. верх", "к. середина", "к. низ", "с. верх", "с. середина", "с. низ");
        partNumbers = FXCollections.observableArrayList();
        sampleTypes = FXCollections.observableArrayList(new SampleType("100x100", 100), new SampleType("70x70", 70));
        results = FXCollections.observableArrayList();
        sampleTests = FXCollections.observableArrayList();
        humidityTests = FXCollections.observableArrayList();
        personal = FXCollections.observableArrayList();
        avgWetDensity = new SimpleDoubleProperty(0d);
        avgDryDensity = new SimpleDoubleProperty(0d);
        densityMark = new SimpleStringProperty("");
        densityVariation = new SimpleDoubleProperty(0d);
        avgDuarbility = new SimpleDoubleProperty(0d);
        durabilityMark = new SimpleStringProperty("");
        durabilityVariation = new SimpleDoubleProperty(0d);
        infoMessage = new SimpleStringProperty("");
        selectedTestDate = new SimpleStringProperty("");
        saveButtonDisabled = new SimpleBooleanProperty(false);
        reqDensity = new SimpleDoubleProperty();
        reqDurability = new SimpleDoubleProperty();
        applyRatios = new SimpleBooleanProperty(false);

        addMeasureName1 = new SimpleStringProperty();
        addMeasureValue1 = new SimpleDoubleProperty();
        addMeasureUnit1 = new SimpleStringProperty();

        addMeasureName2 = new SimpleStringProperty();
        addMeasureValue2 = new SimpleDoubleProperty();
        addMeasureUnit2 = new SimpleStringProperty();

        //--- Вкладка испытаний ГП
        products = FXCollections.observableArrayList();
        months = FXCollections.observableArrayList(new Month(), new Month(1, "Январь"),
                new Month(2, "Февраль"), new Month(3, "Март"),
                new Month(4, "Апрель"), new Month(5, "Май"), new Month(6, "Июнь"),
                new Month(7, "Июль"), new Month(8, "Август"), new Month(9, "Сентябрь"),
                new Month(10, "Октябрь"), new Month(11, "Ноябрь"), new Month(12, "Декабрь"));
        years = FXCollections.observableArrayList();
        years.add(new Year(0, ""));
        for (int i = 2010; i < 2050; i++) {
            years.add(new Year(i, String.valueOf(i)));
        }

        //Выбранная партия на вкладке Статистика испытаний ГП.
        selectedPartNumStat = new SimpleObjectProperty<>();
        selectedPartNumStat.addListener(new ChangeListener<Part>() {
            @Override
            public void changed(ObservableValue<? extends Part> ov, Part oldVal, Part newVal) {
                updateProductTestStatistics();
                updateDensityAndDurabilityStats();
            }
        });

        //Выбранная продукция на вкладке Статистика испытаний ГП.
        selectedProdNameStat = new SimpleObjectProperty<>();
        selectedProdNameStat.addListener(new ChangeListener<Product>() {
            @Override
            public void changed(ObservableValue<? extends Product> ov, Product oldVal, Product newVal) {
                updateProductTestStatistics();
                updateDensityAndDurabilityStats();
            }
        });

        //Выбранный месяц на вкладке Статистика испытаний ГП.
        selectedMonthStat = new SimpleObjectProperty<>();
        selectedMonthStat.addListener(new ChangeListener<Month>() {

            @Override
            public void changed(ObservableValue<? extends Month> ov, Month oldVal, Month newVal) {
                updateProductTestStatistics();
                updateDensityAndDurabilityStats();
                updateRequiredDensityAndDurabilityStatistics();
            }
        });

        //Выбранный год на вкладке Статистика испытаний ГП.
        selectedYearStat = new SimpleObjectProperty<>();
        selectedYearStat.addListener(new ChangeListener<Year>() {

            @Override
            public void changed(ObservableValue<? extends Year> ov, Year oldVal, Year newVal) {
                updateProductTestStatistics();
                updateDensityAndDurabilityStats();
                updateRequiredDensityAndDurabilityStatistics();
            }
        });

        //Выбранная плотность на вкладке Статистика испытаний ГП.
        selectedDensityMarkStat = new SimpleStringProperty();
        selectedDensityMarkStat.addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                updateProductTestStatistics();
                updateDensityAndDurabilityStats();
            }
        });

        //Выбранная прочность на вкладке Статистика испытаний ГП.
        selectedDurabilityMarkStat = new SimpleStringProperty();
        selectedDurabilityMarkStat.addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                updateProductTestStatistics();
                updateDensityAndDurabilityStats();
            }
        });

        //Коэффициенты вариации прочности и плотности.
        densityVarStat = new SimpleFloatProperty();
        durabilityVarStat = new SimpleFloatProperty();

        //Требуемые плотности в разделе Статистика испытаний ГП.
        requiredDensities = FXCollections.observableArrayList();

        //Требуемые прочности в разделе Статистика испытаний ГП.
        requiredDurabilities = FXCollections.observableArrayList();

        //------------------------------------------------
        //Отслеживаем изменение средней сухой плотности 
        //и по ней выставляем марку по плотности, а также расчитываем текущий
        //коэффициент вариации (по плотности).
        avgDryDensityProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> ov, Number t, Number t1) {
                setDensityMark(Calculation.calcDensityMark((Double) t1));
                setDensityVariation(Calculation.calcDensityVariation(results, (Double) t1));
            }
        });

        //Отслеживаем добавление и удаление образцов.
        sampleTests.addListener(new ListChangeListener<SampleTest>() {

            @Override
            public void onChanged(ListChangeListener.Change<? extends SampleTest> change) {
                try {
                    if (!change.next()) {
                        return;
                    }

                    //Смотрим на добавленные образцы
                    for (int i = 0; i < change.getAddedSize(); i++) {
                        final SampleTest st = change.getAddedSubList().get(i);
                        final Result rs = new Result(st.getSampleNum());
                        //Добавляем запись в таблицу результатов.
                        results.add(rs);

                        //Мониторим изменения сырой плотности.
                        st.wetDensityProperty().addListener(new ChangeListener<Number>() {

                            @Override
                            public void changed(ObservableValue<? extends Number> ov, Number t, Number t1) {
                                //Обновляем сырую плотность в таблицу результатов.
                                rs.setWetDensity((Double) t1);

                                //Обновляем сухую плотность в таблице результатов.
                                //rs.setDryDensity(rs.CalculateDryDensity(humidityTests.get(0).getHumidity()));
                                if (humidityTests.size() > 0) {
                                    rs.setDryDensity(Calculation.calcDryDensity(rs.getWetDensity(), humidityTests.get(0).getHumidity()));
                                }

                                //расчитываем средние плотности.
                                CalculateDensities();

                                //Проверяем: можно ли сохранять данные в БД.
                                CheckIfSaveTestAllowed();
                            }
                        });

                        //Мониторим изменения разрушающей нагрузки P.
                        st.destructLoadProperty().addListener(new ChangeListener<Number>() {

                            @Override
                            public void changed(ObservableValue<? extends Number> ov, Number t, Number t1) {
                                //Проверяем: можно ли сохранять данные в БД.
                                CheckIfSaveTestAllowed();

                                if (humidityTests.size() < 1) {
                                    return;
                                }

                                if (selectedSampleType != null && humidityTests.size() > 0 && humidityTests.get(0) != null && humidityTests.get(0).getHumidity() > 0) {
                                    rs.setDurability(Calculation.calcDurability(selectedSampleType.toString(),
                                            humidityTests.get(0).getHumidity(), (Double) t1, st.getLength(), st.getWidth()));

                                    //Рассчитываем средний предел прочности при сжатии.
                                    setAvgDurability(Calculation.calcAvgDurability(results));
                                }
                            }
                        });
                    }

                    //Смотрим на удалённые образцы.
                    for (int i = 0; i < change.getRemovedSize(); i++) {
                        SampleTest st = change.getRemoved().get(i);

                        for (int j = 0; j < results.size(); j++) {
                            if (results.get(j).getSampleName() == st.getSampleNum()) {
                                results.remove(j);
                            }
                        }
                    }

                    //расчитываем плотности, если есть добавленные или удалённые испытания.
                    CalculateDensities();

                    //Проверяем: можно ли сохранять данные в БД.
                    CheckIfSaveTestAllowed();

                } catch (Exception ex) {
                    Logger.getLogger(BLogic.class.getName()).log(Level.SEVERE, null, ex);
                    infoMessage.set(ex.getLocalizedMessage());
                }
            }

            private void CalculateDensities() {
                //Рассчитываем среднюю сырую плотность.
                setAvgWetDensity(Calculation.calcAvgWetDensity(sampleTests));

                //Рассчитываем среднюю плотность в сухом состоянии.
                setAvgDryDensity(Calculation.calcAvgDryDensity(results));
            }
        });

        //Отслеживаем добавление и удаление оценок влажности.
        humidityTests.addListener(new ListChangeListener<HumidityTest>() {

            @Override
            public void onChanged(ListChangeListener.Change<? extends HumidityTest> change) {
                if (!change.next()) {
                    return;
                }

                for (int i = 0; i < change.getAddedSize(); i++) {
                    HumidityTest ht = change.getAddedSubList().get(i);
                    ht.humidityProperty().addListener(new ChangeListener<Number>() {

                        @Override
                        public void changed(ObservableValue<? extends Number> ov, Number t, Number t1) {
                            //Рассчитываем плотность в сухом состоянии.
                            for (int j = 0; j < results.size(); j++) {
                                Result r = results.get(j);

                                //Берём в расчёт только нулевую строку таблицы оценки влажности!!!
                                r.setDryDensity(Calculation.calcDryDensity(r.getWetDensity(), humidityTests.get(0).getHumidity()));
                                r.setDurability(Calculation.calcDurability(selectedSampleType.toString(), humidityTests.get(0).getHumidity(),
                                        sampleTests.get(j).getDestructLoad(), sampleTests.get(j).getLength(), sampleTests.get(j).getWidth()));
                            }

                            //Расчитываем среднюю плотность в сухом состоянии.
                            setAvgDryDensity(Calculation.calcAvgDryDensity(results));

                            //Расчитываем среднюю прочность.
                            setAvgDurability(Calculation.calcAvgDurability(results));

                            //Проверяем: можно ли сохранять данные в БД.
                            CheckIfSaveTestAllowed();
                        }
                    });
                }
            }
        }
        );

        //Отслеживаем изменение среднего предела прочности при сжатии Rm.
        avgDuarbility.addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> ov, Number t, Number t1) {
                //Рассчитываем класс по прочности B.
                ArrayList<Double> vars;
                try {
                    vars = dc.getDurabilityVariationsByDateAndProduct(getSelectedTestDate(), getSelectedPart().getProductId());
                    Double avgVariation = Calculation.calcDurabilityVariationByParts(vars);
                    Double kt = Calculation.calcReqDurabilityRatio(avgVariation);
                    setDurabilityMark(Calculation.calcDurabilityMark((Double) t1, kt, results));

                    //Рассчитываем требуемую прочность
                    //ArrayList<Double> vars = dc.getDurabilityVariationsByDateAndProduct(getSelectedTestDate(), selectedPart.getProductId());
                    kt = 1.43;

                    //При количестве результатов меньще 30 делаем kt=1.43
                    if (vars.size() >= 30) {
                        avgVariation = Calculation.calcDurabilityVariationByParts(vars);
                        kt = Calculation.calcReqDurabilityRatio(avgVariation);
                    }

                    Double Rt = Calculation.calcReqDurability(kt, getDurabilityMark());
                    setReqDurability(Rt);
                    //---
                } catch (SQLException | ParseException ex) {
                    Logger.getLogger(BLogic.class.getName()).log(Level.SEVERE, null, ex);
                    infoMessage.set(ex.getLocalizedMessage());
                }

                //Рассчитываем текущий коэффициент вариации в партии (по прочности) Vm
                setDurabilityVariation(Calculation.calcDurabilityVariation((Double) t1, results));

                //Расчитываем требуемую плотность. ---
                ArrayList<Double> Vm;
                try {
                    Vm = dc.getDensityVariationsByDateAndProduct(getSelectedTestDate(), selectedPart.getProductId());
                } catch (SQLException | ParseException ex) {
                    Logger.getLogger(BLogic.class.getName()).log(Level.SEVERE, null, ex);
                    infoMessage.set(ex.getLocalizedMessage());
                    return;
                }

                if (Vm.size() < 30 && getDensityMark().length() > 0) {
                    //Если в статистике меньше 7 образцов, то устанавливаем требуемую плотность
                    //равной значению марки по плотности.
                    String tmp = getDensityMark().substring(1);
                    setReqDensity(Double.valueOf(tmp));
                } else {

                    Double Vn = Calculation.calcDensityVariationByParts(Vm);    //Средний коэф вариации по партиям.

                    Double Kt = Calculation.calcReqDensityRatio(Vn);            //Коэффициент требуемой плотности.

                    setReqDensity(Calculation.calcReqDensity(Kt, getDensityMark()));
                }
                //---
            }
        });

        //Соединяемся с базой данных.
        connectToDB();
    }

    /**
     * Обновляет значения коэффициентов вариации по прочности и плотности во
     * вкладке статистика испытаний ГП.
     */
    private void updateDensityAndDurabilityStats() {
        try {
            densityVarStat.set(dc.getDensityVariationStat(selectedPartNumStat.get(),
                    selectedProdNameStat.get(), selectedMonthStat.get(),
                    selectedYearStat.get(), selectedDensityMarkStat.get(),
                    selectedDurabilityMarkStat.get()));

            durabilityVarStat.set(dc.getDurabilityVariationStat(selectedPartNumStat.get(),
                    selectedProdNameStat.get(), selectedMonthStat.get(),
                    selectedYearStat.get(), selectedDensityMarkStat.get(),
                    selectedDurabilityMarkStat.get()));
        } catch (SQLException ex) {
            Logger.getLogger(BLogic.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Обновляет таблицу tblStatistics.
     */
    private void updateProductTestStatistics() {
        if (productTestStatistics == null) {
            productTestStatistics = FXCollections.observableArrayList();
            tblStatisticsItemsCount = new SimpleIntegerProperty();
        }

        try {
            productTestStatistics.clear();
            productTestStatistics.addAll(dc.getProductTestStatistics(selectedPartNumStat.get(),
                    selectedProdNameStat.get(), selectedMonthStat.get(),
                    selectedYearStat.get(), selectedDensityMarkStat.get(),
                    selectedDurabilityMarkStat.get()));

            setTblStatisticsItemsCount(productTestStatistics.size());
        } catch (SQLException | ParseException ex) {
            Logger.getLogger(BLogic.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Обновляет вкладку статистики испытаний ГП.
     */
    private void updateRequiredDensityAndDurabilityStatistics() {
        try {

            if (requiredDensities == null) {
                requiredDensities = FXCollections.observableArrayList();
            }

            requiredDensities.addAll(dc.getRequiredDensities(getSelectedMonthStat(), getSelectedYearStat()));
            
        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }

    /**
     * Добавляет новую запись в таблицу испытаний образцов.
     *
     * @return true - если добавлено.
     */
    public boolean AddNewSampleTest() {
        SampleTest newTest = new SampleTest();
        newTest.setSampleNum(sampleTests.size() + 1);   //Устанавливаем номер по порядку

        //Устанавливаем расположение образца в массиве.
        if (sampleTests.size() < positions.size()) {
            newTest.setPosition(positions.get(sampleTests.size()));
        }

        return sampleTests.add(newTest);
    }

    /**
     * Удаляет запись из таблицы испытаний образцов.
     *
     * @param st - сущность класса испытаний образца.
     * @return true - если удалено.
     */
    public boolean RemoveSampleTest(SampleTest st) {
        return sampleTests.remove(st);
    }

    /**
     * Добавляет новую запись в таблицу оценки влажности.
     *
     * @return true - если добавлено.
     */
    public boolean AddNewHumidityTest() {
        HumidityTest newTest = new HumidityTest();
        return humidityTests.add(newTest);
    }

    /**
     * Удаляет указанную запись из таблицы оценки влажности.
     *
     * @param ht - сущность класса оценки влажности
     * @return true - если удалено.
     */
    public boolean RemoveHumidityTest(HumidityTest ht) {
        return humidityTests.remove(ht);
    }

    public void connectToDB() {
        try {
            //Подключение к БД.
            dc = new DataContext();
            dc.connect();
            partNumbers = FXCollections.observableArrayList(dc.GetPartNumbers());
            partNumbers.set(0, new Part());

            personal = FXCollections.observableArrayList(dc.GetPersonal());
            products = FXCollections.observableArrayList(dc.getProducts());
            products.add(0, new Product());

            densityMarks = FXCollections.observableArrayList(dc.getDensityMarks());
            densityMarks.add(0, "");

            durabilityMarks = FXCollections.observableArrayList(dc.getDurabilityMarks());
            durabilityMarks.add(0, "");

            updateProductTestStatistics();
        } catch (SQLRecoverableException ex) {
            Logger.getLogger(BLogic.class.getName()).log(Level.SEVERE, null, ex);
            infoMessage.set(ex.getLocalizedMessage());
        } catch (SQLException ex) {
            Logger.getLogger(BLogic.class.getName()).log(Level.SEVERE, null, ex);
            infoMessage.set(ex.getLocalizedMessage());
        } catch (ParseException ex) {
            Logger.getLogger(BLogic.class.getName()).log(Level.SEVERE, null, ex);
            infoMessage.set(ex.getLocalizedMessage());
        } catch (Exception ex) {
            Logger.getLogger(BLogic.class.getName()).log(Level.SEVERE, null, ex);
            infoMessage.set(ex.getLocalizedMessage());
        }
    }

    public void addOrUpdateTestIntoDb() throws ParseException {
        if (dc == null) {
            dc = new DataContext();
            try {
                dc.connect();
            } catch (SQLException ex) {
                Logger.getLogger(BLogic.class.getName()).log(Level.SEVERE, null, ex);
                infoMessage.set(ex.getLocalizedMessage());
                return;
            }
        }

        Date dt;
        DateFormat df;
        try {
            df = new SimpleDateFormat("dd.MM.yyyy");
            dt = df.parse(getSelectedTestDate());
        } catch (ParseException ex) {
            setInfoMessage("Не правильно указана дата! Данные не сохранены.");
            infoMessage.set(ex.getLocalizedMessage());
            return;
        }

        try {

            //Добавляем запись в таблицу тестироввния (t_q_st).
            int retVal = dc.addOrUpdateTestIntoDb(getSelectedPart().getPartNum(), getSelectedPart().getProductId(),
                    getSelectedPart().getDateTime(), dt, getSelectedPerson().getId(),
                    getSelectedSampleType().getName(), getReqDensity(), getReqDurability(), getAvgWetDensity(), getAvgDryDensity(),
                    getDensityMark(), getDensityVariation(), getAvgDurability(), getDurabilityMark(), getDurabilityVariation(),
                    getAddMeasureName1(), getAddMeasureValue1(), getAddMeasureUnit1(),
                    getAddMeasureName2(), getAddMeasureValue2(), getAddMeasureUnit2(), getApplyRatios());
            switch (retVal) {
                case 0:
                    infoMessageProperty().set("Ошибка при добавлении или обновлении в базу данных.");
                    break;
                case 1:
                    infoMessageProperty().set("Сохранено");
                    break;
                case 2:
                    infoMessageProperty().set("Обновлено");
                    break;
            }

            int testId = this.dc.getTestId(getSelectedPart().getPartNum(), getSelectedPart().getDateTime());

            if (testId < 0) {
                return;
            }

            //Добавляем испытания образцов.
            SampleTest st; //испытания образцов.
            Result rt;     //результаты испытаний.

            for (int i = 0; i < this.getSampleTests().size(); i++) {
                st = this.getSampleTests().get(i);
                rt = this.getResults().get(i);
                dc.addOrUpdateTestSampleIntoDb(testId, st.getSampleNum(), st.getPosition(), st.getLength(), st.getWidth(), st.getHeight(), st.getWeight(), st.getDestructLoad(),
                        st.getWetDensity(), rt.getDryDensity(), rt.getDurability());
            }

            //Добавляем оценку влажности.
            HumidityTest ht;

            for (int i = 0; i < this.getHumidityTests().size(); i++) {
                ht = this.getHumidityTests().get(i);
                dc.addOrUpdateHumidity(testId, ht.getBottleNum(), ht.getBottleWeight(), ht.getWetWeight(), ht.getDryWeight(), ht.getHumidity());
            }

        } catch (SQLException ex) {
            Logger.getLogger(BLogic.class.getName()).log(Level.SEVERE, null, ex);
            infoMessage.set(ex.getLocalizedMessage());
        }
    }

    private void CheckIfSaveTestAllowed() {
        int sampleTestCount = 0; //число ненулевых образцов.
        //int humidityCount = 0; //число ненулевых тестов на влажность.

        //считаем ненулевые образцы.
        for (SampleTest sampleTest : sampleTests) {
            if (sampleTest.getWetDensity() > 0) {
                sampleTestCount++;
            }
        }
        /*
         //считаем ненулевые испытания на влажность
         for (HumidityTest humidityTest : humidityTests) {
         if (humidityTest.getHumidity() > 0) {
         humidityCount++;
         }
         }
         */
        //Проверяем: можно ли сохранять данные в БД.
        if (sampleTestCount < 2 || selectedPerson == null || selectedPart == null) {
            setSaveButtonDisabled(Boolean.TRUE);
        } else {
            setSaveButtonDisabled(Boolean.FALSE);
        }
    }

    /**
     * Добавляет слушателей свойств этого класса.
     *
     * @param toAdd - изменённое свойство.
     */
    public void addListener(IValueChanged toAdd) {
        listeners.add(toAdd);
    }

    /**
     * Сообщает слушателям, что изменился выбранный сотрудник.
     *
     * @param p - выбранный сотрудник.
     */
    private void fireSelectedPersonChanged(Person p) {

        for (IValueChanged hl : listeners) {
            hl.onValueChanged("Person", p);
        }
    }

    private void fireSelectedSampleTypeChanged(SampleType st) {
        for (IValueChanged hl : listeners) {
            hl.onValueChanged("SampleType", st);
        }
    }

    private void fireAddMeasureName1Changed(String addMeasureName) {
        for (IValueChanged hl : listeners) {
            hl.onValueChanged("AddMeasureName1", addMeasureName);
        }
    }

    private void fireAddMeasureName2Changed(String addMeasureName) {
        for (IValueChanged hl : listeners) {
            hl.onValueChanged("AddMeasureName2", addMeasureName);
        }
    }
}
