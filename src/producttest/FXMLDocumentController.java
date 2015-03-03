/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package producttest;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.Property;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javafx.util.converter.IntegerStringConverter;
import javafx.util.converter.NumberStringConverter;
import producttest.bll.BLogic;
import producttest.bll.IValueChanged;
import producttest.helpers.FloatPassCallback;
import producttest.helpers.FloatStatCallback;
import producttest.helpers.FloatStatWORoundCallback;
import producttest.helpers.StringStatCallback;
import producttest.model.HumidityTest;
import producttest.model.Month;
import producttest.model.Part;
import producttest.model.Person;
import producttest.model.Product;
import producttest.model.ProductPassport;
import producttest.model.RequiredDensity;
import producttest.model.RequiredDurability;
import producttest.model.Result;
import producttest.model.SampleTest;
import producttest.model.SampleType;
import producttest.model.Stat;
import producttest.model.Year;

/**
 *
 * @author tonkushin
 */
public class FXMLDocumentController implements Initializable, IValueChanged {

// <editor-fold defaultstate="collapsed" desc="Объявления элементов управления">
    @FXML
    private ComboBox<Part> comboPartNum; //Номер партии

    @FXML
    private Label lblProductType;
    @FXML
    private Label lblReqDensity;
    @FXML
    private Label lblReqDurability;
    @FXML
    private ComboBox<SampleType> comboSampleType;
    @FXML
    private Button btnAddSample;
    @FXML
    private Button btnRemoveSample;
    @FXML
    private TableView<SampleTest> tblSample;
    @FXML
    private TableColumn<SampleTest, Integer> colSampleNumSample; //номер образца.
    @FXML
    private TableColumn<SampleTest, String> colPositionSample; //расположение образца в массиве.
    @FXML
    private TableColumn<SampleTest, Double> colLengthSample;
    @FXML
    private TableColumn<SampleTest, Double> colWidthSample; //ширина.
    @FXML
    private TableColumn<SampleTest, Double> colHeightSample;
    @FXML
    private TableColumn<SampleTest, Double> colSampleWeightSample;
    @FXML
    private TableColumn<SampleTest, Double> colDestructLoadSample;
    @FXML
    private Button addBottle;
    @FXML
    private Button btnRemoveBottle;
    @FXML
    private TableView<HumidityTest> tblHumidity;
    @FXML
    private TableColumn<HumidityTest, Integer> colBottleNumHumidity;
    @FXML
    private TableColumn<HumidityTest, Double> colBottleWeightHumidity;
    @FXML
    private TableColumn<HumidityTest, Double> colWetHumidity;
    @FXML
    private TableColumn<HumidityTest, Double> colDryHumidity;
    @FXML
    private TableColumn<HumidityTest, Double> colHumidity;
    @FXML
    private ComboBox<String> comboAdditionalMeasure1;
    @FXML
    private TextField txtAdditionalMeasureValue1;
    @FXML
    private TextField txtAdditionalMeasureUnit1;
    @FXML
    private ComboBox<String> comboAdditionalMeasure2;
    @FXML
    private TextField txtAdditionalMeasureValue2;
    @FXML
    private TextField txtAdditionalMeasureUnit2;
    @FXML
    private TableView<Result> tblResults;
    @FXML
    private TableColumn<Result, Integer> colSampleNumResults;
    @FXML
    private TableColumn<Result, Double> colWetDensityResults;
    @FXML
    private TableColumn<Result, Double> colDryDensityResults;
    @FXML
    private TableColumn<Result, Double> colDensityMarkResults;
    @FXML
    private TableColumn<Result, Double> colDensityVariationResults;
    @FXML
    private TableColumn<Result, Double> colDurabilityResults;
    @FXML
    private TableColumn<Result, Double> colDurabilityMarkResults;
    @FXML
    private TableColumn<Result, Double> colDurabilityVariationResults;
    @FXML
    private TableColumn<?, ?> colVal1Results;
    @FXML
    private TableColumn<?, ?> colVal2Results;
    @FXML
    private Button btnAccept;
    @FXML
    private Label lblInfo;      //Для текстов сообщений и ошибок.
    @FXML
    private Label txtFooterRowStart;
    @FXML
    private TextField txtAvgWetDensity;
    @FXML
    private TextField txtAvgDryDensity;
    @FXML
    private TextField txtDensityMark;
// </editor-fold>

    BLogic blogic;
    @FXML
    private TextField txtDensityVariation;
    @FXML
    private TextField txtDurability;
    @FXML
    private TextField txtDurabilityMark;
    @FXML
    private TextField txtDurabilityVariation;
    @FXML
    private TextField txtTestsDate;
    @FXML
    private ComboBox<Person> comboTester;
    @FXML
    private CheckBox chkApplyRatios;
    @FXML
    private Label lblRho;
    @FXML
    private ContextMenu contextMenu;
    @FXML
    private MenuItem mnuSettings;
    @FXML
    private TabPane tabPane;
    @FXML
    private Tab tabProductTest;

    //------------- Вкладка Статистика испытаний ГП -------------
    @FXML
    private Tab tabStatistics;
    @FXML
    private ComboBox<Part> comboPartNumStat;
    @FXML
    private TableView<Stat> tblStatistics;
    @FXML
    private ComboBox<String> comboDensityMarkStat;
    @FXML
    private ComboBox<Product> comboProdNameStat;
    @FXML
    private ComboBox<String> comboDurabilityMarkStat;
    @FXML
    private ComboBox<Month> comboMonthStat;
    @FXML
    private ComboBox<Year> comboYearStat;
    @FXML
    private TableColumn<Stat, Date> colDateStat;
    @FXML
    private TableColumn<Stat, String> colPartNumStat;
    @FXML
    private TableColumn<Stat, String> colProdNameStat;
    @FXML
    private TableColumn<Stat, Float> colReqDensityStat;
    @FXML
    private TableColumn<Stat, Float> colReqDurabilityStat;
    @FXML
    private TableColumn<Stat, Float> colDryDensityStat;
    @FXML
    private TableColumn<Stat, String> colDensityMarkStat;
    @FXML
    private TableColumn<Stat, Float> colDensityVariationStat;
    @FXML
    private TableColumn<Stat, Float> colDurabilityStat;
    @FXML
    private TableColumn<Stat, String> colDurabilityMarkStat;
    @FXML
    private TableColumn<Stat, Float> colDurabilityVarStat;
    @FXML
    private TableColumn<Stat, String> colPersonStat;
    @FXML
    private Label tblStatisticsItemsCount;
    @FXML
    private Label lblDensityVarStat;
    @FXML
    private Label lblDurabilityVarStat;
    @FXML
    private Label lblDensityVarStatTitle;
    @FXML
    private Label lblDurabilityVarStatTitle;
    @FXML
    private GridPane grdStatisticsContols;
    @FXML
    private TableView<RequiredDensity> tblRequiredDensity;
    @FXML
    private TableView<RequiredDurability> tblRequiredDurability;
    @FXML
    private TableColumn<RequiredDensity, String> colDensityMarkReq;
    @FXML
    private TableColumn<RequiredDensity, Integer> colReqDensityReq;
    @FXML
    private TableColumn<RequiredDurability, String> colDurabilityMarkReq;
    @FXML
    private TableColumn<RequiredDurability, Float> colReqDurabilityReq;
    @FXML
    private Tab tabPassports;
    @FXML
    private ComboBox<Month> comboMonthPass;
    @FXML
    private ComboBox<Year> comboYearPass;
    @FXML
    private Button btnAddPass;
    @FXML
    private Button btnChangePass;
    @FXML
    private Button btnDeletePass;
    @FXML
    private Button btnPrintPass;
    @FXML
    private TableView<ProductPassport> tblPassports;
    @FXML
    private TableColumn<ProductPassport, Date> colDatePass;
    @FXML
    private TableColumn<ProductPassport, String> colPartNumPass;
    @FXML
    private TableColumn<ProductPassport, Product> colProdNamePass;
    @FXML
    private TableColumn<ProductPassport, Float> colDurabilityPass;
    @FXML
    private TableColumn<ProductPassport, Float> colDensityPass;
    @FXML
    private TableColumn<ProductPassport, String> colNotesPass;
    @FXML
    private Label tblPassportsItemsCount;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        blogic = new BLogic();      //Инициализируем класс бизнес логики.
        blogic.addListener(this);   //Подписваемся на его события изменения некоторых элементов.

        //-------------------------------------------------
        comboPartNum.setItems(blogic.getPartNumbers());
        comboSampleType.setItems(blogic.getSampleTypes());
        comboAdditionalMeasure1.setItems(blogic.getAdditionalMeasures());
        comboAdditionalMeasure2.setItems(blogic.getAdditionalMeasures());

        txtAdditionalMeasureValue1.textProperty().bindBidirectional(blogic.addMeasureValue1Property(), new NumberStringConverter());
        txtAdditionalMeasureUnit1.textProperty().bindBidirectional(blogic.addMeasureUnit1Property());

        txtAdditionalMeasureValue2.textProperty().bindBidirectional(blogic.addMeasureValue2Property(), new NumberStringConverter());
        txtAdditionalMeasureUnit2.textProperty().bindBidirectional(blogic.addMeasureUnit2Property());

        lblReqDensity.textProperty().bindBidirectional(blogic.reqDensityProperty(), new NumberStringConverter());
        lblReqDurability.textProperty().bindBidirectional(blogic.reqDurabilityProperty(), new NumberStringConverter());

        //Вкладка Статиситка испытаний ГП.
        comboPartNumStat.valueProperty().bindBidirectional(blogic.selectedPartNumStatProperty());
        comboProdNameStat.valueProperty().bindBidirectional(blogic.selectedProdNameStatProperty());
        comboMonthStat.valueProperty().bindBidirectional(blogic.selectedMonthStatProperty());
        comboMonthStat.setItems(blogic.getMonths());
        comboMonthStat.getSelectionModel().select(Calendar.getInstance().get(Calendar.MONTH) + 1);  //Устанавливаем текущий месяц на вкладке статистики ГП.
        comboYearStat.valueProperty().bindBidirectional(blogic.selectedYearStatProperty());
        comboYearStat.setItems(blogic.getYears());                                              //Устанавливаем текущий год на вкладке статистики ГП.
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);                            //
        for (Year y : blogic.getYears()) {                                                      //    
            if (y.getReturnValue() == currentYear) {                                            //        
                comboYearStat.getSelectionModel().select(y);                                    //
                break;                                                                          //
            }                                                                                   //
        }                                                                                       //

        comboDensityMarkStat.valueProperty().bindBidirectional(blogic.selectedDensityMarkStatProperty());
        comboDurabilityMarkStat.valueProperty().bindBidirectional(blogic.selectedDurabilityMarkStatProperty());
        tblStatisticsItemsCount.textProperty().bindBidirectional(blogic.tblStatisticsItemsCountProperty(), new NumberStringConverter());
        lblDensityVarStat.textProperty().bindBidirectional(blogic.densityVarStatProperty(), new NumberStringConverter());
        lblDurabilityVarStat.textProperty().bindBidirectional(blogic.durabilityVarStatProperty(), new NumberStringConverter());

        //Инициализация колонок в таблицах.
        InitSampleColumns();
        InitHumidityColumns();
        InitResultsColumns();
        InitStatColumns();
        InitDensityColumns();
        InitDurabilityColumns();
        InitPassportColumns();

        //Устанавлеваем подписи для пустых таблиц.
        tblSample.setPlaceholder(new Text("Испытание образцов"));
        tblHumidity.setPlaceholder(new Text("Оценка влажности"));
        tblResults.setPlaceholder(new Text("Результаты испытаний"));
        tblStatistics.setPlaceholder(new Text("Статистика испытаний ГП"));
        tblRequiredDensity.setPlaceholder(new Text("Требуемая плотность"));
        tblRequiredDurability.setPlaceholder(new Text("Требуемая прочность"));

        tblHumidity.setEditable(false);
        tblSample.setEditable(false);

        tblSample.setItems(blogic.getSampleTests());
        tblHumidity.setItems(blogic.getHumidityTests());
        tblResults.setItems(blogic.getResults());
        tblStatistics.setItems(blogic.getProductTestStatistics());
        tblRequiredDensity.setItems(blogic.getRequiredDensities());
        tblRequiredDurability.setItems(blogic.getRequiredDurabilities());
        tblPassports.setItems(blogic.getProductPassports());

        txtFooterRowStart.setMinWidth(colSampleNumResults.getPrefWidth());
        txtFooterRowStart.setMaxWidth(colSampleNumResults.getPrefWidth());

        txtAvgWetDensity.setMaxWidth(colWetDensityResults.getPrefWidth());
        txtAvgWetDensity.textProperty().bindBidirectional(blogic.avgWetDensityProperty(), new NumberStringConverter());
        txtAvgWetDensity.setTooltip(new Tooltip("Средняя плотность во влажном состоянии, кг/м.куб."));

        txtAvgDryDensity.setMaxWidth(colDryDensityResults.getPrefWidth());
        txtAvgDryDensity.textProperty().bindBidirectional(blogic.avgDryDensityProperty(), new NumberStringConverter());
        txtAvgDryDensity.setTooltip(new Tooltip("Средняя плотность во сухом состоянии, кг/м.куб."));

        txtDensityMark.setMaxWidth(colDensityMarkResults.getPrefWidth());
        txtDensityMark.textProperty().bindBidirectional(blogic.densityMarkProperty());
        txtDensityMark.setTooltip(new Tooltip("Марка по плотности"));

        txtDensityVariation.setMaxWidth(colDensityVariationResults.getPrefWidth());
        txtDensityVariation.textProperty().bindBidirectional(blogic.densityVariationProperty(), new NumberStringConverter());
        txtDensityVariation.setTooltip(new Tooltip("Текущий коэффициент вариации в партии (по плотности)"));

        txtDurability.setMaxWidth(colDurabilityResults.getPrefWidth());
        txtDurability.textProperty().bindBidirectional(blogic.avgDurabilityProperty(), new NumberStringConverter());
        txtDurability.setTooltip(new Tooltip("Предел прочности при сжатии, Rm"));

        txtDurabilityMark.setMaxWidth(colDurabilityMarkResults.getPrefWidth());
        txtDurabilityMark.textProperty().bindBidirectional(blogic.durabilityMarkProperty());
        txtDurabilityMark.setTooltip(new Tooltip("Класс по прочности, B"));

        txtDurabilityVariation.setMaxWidth(colDurabilityVariationResults.getPrefWidth());
        txtDurabilityVariation.textProperty().bindBidirectional(blogic.durabilityVariationProperty(), new NumberStringConverter());
        txtDurabilityVariation.setTooltip(new Tooltip("Текущий коэффициент вариации в партии (по прочности), Vm"));

        txtTestsDate.textProperty().bindBidirectional(blogic.selectedTestDateProperty());
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        Date date = new Date();
        txtTestsDate.textProperty().set(df.format(date));

        comboTester.setItems(blogic.getPersonal());

        lblProductType.setText("-");

        //Приаязка Принять текущие коэффициенты вариации.
        chkApplyRatios.selectedProperty().bindBidirectional(blogic.applyRatiosProperty());

        //Привязка строки состояния.
        lblInfo.textProperty().bind(blogic.infoMessageProperty());

        //Привязка включения/выключения кнопки сохранения.
        btnAccept.disableProperty().bindBidirectional(blogic.saveButtonDisabledProperty());

        //Устанавливаем ро, так как из SceneBuilder не получается.
        lblRho.setText("\u03c1");
    }

    /**
     * Инициализация колонок в таблице Реестр паспортов ГП.
     */
    private void InitPassportColumns() {
        colDatePass.setEditable(false);
        colDatePass.setCellValueFactory(new PropertyValueFactory<ProductPassport, Date>("date"));
        colDatePass.setCellFactory(new Callback<TableColumn<ProductPassport, Date>, TableCell<ProductPassport, Date>>() {

            //Взято из: http://code.makery.ch/blog/javafx-2-tableview-cell-renderer/
            @Override
            public TableCell<ProductPassport, Date> call(TableColumn<ProductPassport, Date> p) {
                return new TableCell<ProductPassport, Date>() {
                    @Override
                    protected void updateItem(Date item, boolean empty) {
                        super.updateItem(item, empty);

                        if (!empty) {
                            setText(new SimpleDateFormat("dd.MM.yyyy").format(item));
                        } else {
                            setText(null);
                        }
                    }
                };
            }
        });

        colPartNumPass.setEditable(false);
        colPartNumPass.setCellValueFactory(new PropertyValueFactory<ProductPassport, String>("partNum"));

        colProdNamePass.setEditable(false);
        colProdNamePass.setCellValueFactory(new PropertyValueFactory<ProductPassport, Product>("product"));

        colDurabilityPass.setEditable(false);
        colDurabilityPass.setCellValueFactory(new PropertyValueFactory<ProductPassport, Float>("avgDurability"));
        colDurabilityPass.setCellFactory(new FloatPassCallback());

        colDensityPass.setEditable(false);
        colDensityPass.setCellValueFactory(new PropertyValueFactory<ProductPassport, Float>("avgDensity"));
        colDensityPass.setCellFactory(new FloatPassCallback());

        colNotesPass.setEditable(false);
        colNotesPass.setCellValueFactory(new PropertyValueFactory<ProductPassport, String>("notes"));
    }

    /**
     * Инициализация колонок в таблице tblRequiredDurability - статистика
     * испытаний ГП.
     */
    private void InitDurabilityColumns() {
        colDurabilityMarkReq.setEditable(false);
        colDurabilityMarkReq.setCellValueFactory(new PropertyValueFactory<RequiredDurability, String>("name"));

        colReqDurabilityReq.setEditable(false);
        colReqDurabilityReq.setCellValueFactory(new PropertyValueFactory<RequiredDurability, Float>("value"));
        colReqDurabilityReq.setCellFactory(new Callback<TableColumn<RequiredDurability, Float>, TableCell<RequiredDurability, Float>>() {

            @Override
            public TableCell<RequiredDurability, Float> call(TableColumn<RequiredDurability, Float> p) {
                return new TableCell<RequiredDurability, Float>() {
                    @Override
                    protected void updateItem(Float item, boolean empty) {
                        super.updateItem(item, empty);

                        //Устанавливаем выравнивание по правой стороне.
                        setAlignment(Pos.CENTER_RIGHT);

                        //Округляем до целого числа.
                        if (!empty) {
                            setText(item.toString());
                        } else {
                            setText(null);
                        }
                    }
                };
            }
        });
    }

    /**
     * Инициализация колонок в таблице tblRequiredDensity - статистика испытаний
     * ГП.
     */
    private void InitDensityColumns() {
        colDensityMarkReq.setEditable(false);
        colDensityMarkReq.setCellValueFactory(new PropertyValueFactory<RequiredDensity, String>("name"));

        colReqDensityReq.setEditable(false);
        colReqDensityReq.setCellValueFactory(new PropertyValueFactory<RequiredDensity, Integer>("value"));
    }

    /**
     * Инициализация колонок в таблице tblStatistics - статистика испытаний ГП.
     */
    private void InitStatColumns() {
        colDateStat.setEditable(false);
        colDateStat.setCellValueFactory(new PropertyValueFactory<Stat, Date>("testDate"));
        colDateStat.setCellFactory(new Callback<TableColumn<Stat, Date>, TableCell<Stat, Date>>() {
            //Взято из: http://code.makery.ch/blog/javafx-2-tableview-cell-renderer/
            @Override
            public TableCell<Stat, Date> call(TableColumn<Stat, Date> p) {
                return new TableCell<Stat, Date>() {
                    @Override
                    protected void updateItem(Date item, boolean empty) {
                        super.updateItem(item, empty);

                        if (!empty) {
                            setText(new SimpleDateFormat("dd.MM.yyyy").format(item));
                        } else {
                            setText(null);
                        }
                    }
                };
            }
        });

        colPartNumStat.setEditable(false);
        colPartNumStat.setCellValueFactory(new PropertyValueFactory<Stat, String>("partNo"));

        colProdNameStat.setEditable(false);
        colProdNameStat.setCellValueFactory(new PropertyValueFactory<Stat, String>("productName"));

        colReqDensityStat.setEditable(false);
        colReqDensityStat.setCellValueFactory(new PropertyValueFactory<Stat, Float>("reqDensity"));
        colReqDensityStat.setCellFactory(new FloatStatCallback());

        colReqDurabilityStat.setEditable(false);
        colReqDurabilityStat.setCellValueFactory(new PropertyValueFactory<Stat, Float>("reqDurability"));
        colReqDurabilityStat.setCellFactory(new FloatStatCallback());

        colDryDensityStat.setEditable(false);
        colDryDensityStat.setCellValueFactory(new PropertyValueFactory<Stat, Float>("avgDryDensity"));
        colDryDensityStat.setCellFactory(new FloatStatCallback());

        colDensityMarkStat.setEditable(false);
        colDensityMarkStat.setCellValueFactory(new PropertyValueFactory<Stat, String>("densityMark"));
        colDensityMarkStat.setCellFactory(new StringStatCallback());

        colDensityVariationStat.setEditable(false);
        colDensityVariationStat.setCellValueFactory(new PropertyValueFactory<Stat, Float>("densityVariation"));
        colDensityVariationStat.setCellFactory(new FloatStatWORoundCallback());

        colDurabilityStat.setEditable(false);
        colDurabilityStat.setCellValueFactory(new PropertyValueFactory<Stat, Float>("avgDurability"));
        colDurabilityStat.setCellFactory(new FloatStatCallback());

        colDurabilityMarkStat.setEditable(false);
        colDurabilityMarkStat.setCellValueFactory(new PropertyValueFactory<Stat, String>("durabilityMark"));
        colDurabilityMarkStat.setCellFactory(new StringStatCallback());

        colDurabilityVarStat.setEditable(false);
        colDurabilityVarStat.setCellValueFactory(new PropertyValueFactory<Stat, Float>("durabilityVariation"));
        colDurabilityVarStat.setCellFactory(new FloatStatWORoundCallback());

        colPersonStat.setEditable(false);
        colPersonStat.setCellValueFactory(new PropertyValueFactory<Stat, String>("personName"));
    }

    /**
     * Инициализирует столбцы таблицы tblSample - Таблица образцов испытаний.
     */
    private void InitSampleColumns() {
        tblSample.getSelectionModel().setCellSelectionEnabled(true);

        //Фабрика ячеек для объёктов типа Double.
        Callback<TableColumn<SampleTest, Double>, TableCell<SampleTest, Double>> cellFactory = new Callback<TableColumn<SampleTest, Double>, TableCell<SampleTest, Double>>() {

            @Override
            public TableCell<SampleTest, Double> call(TableColumn<SampleTest, Double> p) {
                return new EditingCell();
            }
        };

        //Фабрика ячеек для объектов типа Integer.
        Callback<TableColumn<SampleTest, Integer>, TableCell<SampleTest, Integer>> intCellFactory = new Callback<TableColumn<SampleTest, Integer>, TableCell<SampleTest, Integer>>() {

            @Override
            public TableCell<SampleTest, Integer> call(TableColumn<SampleTest, Integer> p) {
                return new EditingCellSampleTestInteger();
            }
        };

        //-------------------------------------------------
        colSampleNumSample.setCellValueFactory(new PropertyValueFactory<SampleTest, Integer>("sampleNum"));
        colSampleNumSample.setCellFactory(intCellFactory);

        colPositionSample.setCellValueFactory(new PropertyValueFactory<SampleTest, String>("position"));
        colPositionSample.setCellFactory(ComboBoxTableCell.<SampleTest, String>forTableColumn(blogic.getPositions()));
        colPositionSample.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<SampleTest, String>>() {

            @Override
            public void handle(TableColumn.CellEditEvent<SampleTest, String> t) {
                ((SampleTest) t.getTableView().getItems().get(t.getTablePosition().getRow())).setPosition(t.getNewValue());
            }
        });

        colLengthSample.setCellValueFactory(new PropertyValueFactory<SampleTest, Double>("length"));
        //colLengthSample.setCellFactory(TextFieldTableCell.<SampleTest, Double>forTableColumn(new DoubleConverter()));
        colLengthSample.setCellFactory(cellFactory);
        colLengthSample.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<SampleTest, Double>>() {

            @Override
            public void handle(TableColumn.CellEditEvent<SampleTest, Double> t) {
                ((SampleTest) t.getTableView().getItems().get(t.getTablePosition().getRow())).setLength(t.getNewValue());
            }
        });

        colWidthSample.setCellValueFactory(new PropertyValueFactory<SampleTest, Double>("width"));
        colWidthSample.setCellFactory(cellFactory);
        colWidthSample.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<SampleTest, Double>>() {

            @Override
            public void handle(TableColumn.CellEditEvent<SampleTest, Double> t) {
                ((SampleTest) t.getTableView().getItems().get(t.getTablePosition().getRow())).setWidth(t.getNewValue());
            }
        });

        colHeightSample.setCellValueFactory(new PropertyValueFactory<SampleTest, Double>("height"));
        colHeightSample.setCellFactory(cellFactory);
        colHeightSample.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<SampleTest, Double>>() {

            @Override
            public void handle(TableColumn.CellEditEvent<SampleTest, Double> t) {
                ((SampleTest) t.getTableView().getItems().get(t.getTablePosition().getRow())).setHeight(t.getNewValue());
            }
        });

        colSampleWeightSample.setCellValueFactory(new PropertyValueFactory<SampleTest, Double>("weight"));
        colSampleWeightSample.setCellFactory(cellFactory);
        colSampleWeightSample.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<SampleTest, Double>>() {

            @Override
            public void handle(TableColumn.CellEditEvent<SampleTest, Double> t) {
                ((SampleTest) t.getTableView().getItems().get(t.getTablePosition().getRow())).setWeight(t.getNewValue());
            }
        });

        colDestructLoadSample.setCellValueFactory(new PropertyValueFactory<SampleTest, Double>("destructLoad"));
        colDestructLoadSample.setCellFactory(cellFactory);
        colDestructLoadSample.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<SampleTest, Double>>() {

            @Override
            public void handle(TableColumn.CellEditEvent<SampleTest, Double> t) {
                ((SampleTest) t.getTableView().getItems().get(t.getTablePosition().getRow())).setDestructLoad(t.getNewValue());
            }
        });
    }

    /**
     * Инициализирует столбцы таблицы tblHumidity - Таблица оценки влажности.
     */
    private void InitHumidityColumns() {
        tblHumidity.getSelectionModel().setCellSelectionEnabled(true);

        //Для ячеек типа Double.
        Callback<TableColumn<HumidityTest, Double>, TableCell<HumidityTest, Double>> cellFactory = new Callback<TableColumn<HumidityTest, Double>, TableCell<HumidityTest, Double>>() {

            @Override
            public TableCell<HumidityTest, Double> call(TableColumn<HumidityTest, Double> p) {
                return new EditingCell2();
            }
        };

        //Для ячеек типа Integer.
        Callback<TableColumn<HumidityTest, Integer>, TableCell<HumidityTest, Integer>> integerCellFactory = new Callback<TableColumn<HumidityTest, Integer>, TableCell<HumidityTest, Integer>>() {

            @Override
            public TableCell<HumidityTest, Integer> call(TableColumn<HumidityTest, Integer> p) {
                return new EditingCellHumidityInteger();
            }
        };

        //-------------------------------------------------
        colBottleNumHumidity.setCellValueFactory(new PropertyValueFactory<HumidityTest, Integer>("bottleNum"));
        colBottleNumHumidity.setCellFactory(integerCellFactory);
        colBottleNumHumidity.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<HumidityTest, Integer>>() {

            @Override
            public void handle(TableColumn.CellEditEvent<HumidityTest, Integer> t) {
                ((HumidityTest) t.getTableView().getItems().get(t.getTablePosition().getRow())).setBottleNum(t.getNewValue());
            }
        });

        colBottleWeightHumidity.setCellValueFactory(new PropertyValueFactory<HumidityTest, Double>("bottleWeight"));
        colBottleWeightHumidity.setCellFactory(cellFactory);
        colBottleWeightHumidity.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<HumidityTest, Double>>() {

            @Override
            public void handle(TableColumn.CellEditEvent<HumidityTest, Double> t) {
                ((HumidityTest) t.getTableView().getItems().get(t.getTablePosition().getRow())).setBottleWeight(t.getNewValue());
            }
        });

        colWetHumidity.setCellValueFactory(new PropertyValueFactory<HumidityTest, Double>("wetWeight"));
        colWetHumidity.setCellFactory(cellFactory);
        colWetHumidity.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<HumidityTest, Double>>() {

            @Override
            public void handle(TableColumn.CellEditEvent<HumidityTest, Double> t) {
                ((HumidityTest) t.getTableView().getItems().get(t.getTablePosition().getRow())).setWetWeight(t.getNewValue());
            }
        });

        colDryHumidity.setCellValueFactory(new PropertyValueFactory<HumidityTest, Double>("dryWeight"));
        colDryHumidity.setCellFactory(cellFactory);
        colDryHumidity.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<HumidityTest, Double>>() {

            @Override
            public void handle(TableColumn.CellEditEvent<HumidityTest, Double> t) {
                ((HumidityTest) t.getTableView().getItems().get(t.getTablePosition().getRow())).setDryWeight(t.getNewValue());
            }
        });

        colHumidity.setCellValueFactory(new PropertyValueFactory<HumidityTest, Double>("humidity"));
        //colHumidity.setCellFactory(TextFieldTableCell.<HumidityTest, Double>forTableColumn(new DoubleConverter()));
        colHumidity.setCellFactory(cellFactory);
    }

    /**
     * Инициализирует столбцы таблицы tblResults - Таблица результатов
     * испытаний.
     */
    private void InitResultsColumns() {
        tblResults.getSelectionModel().setCellSelectionEnabled(true);

        Callback<TableColumn<Result, Double>, TableCell<Result, Double>> cellFactory = new Callback<TableColumn<Result, Double>, TableCell<Result, Double>>() {

            @Override
            public TableCell<Result, Double> call(TableColumn<Result, Double> p) {
                return new EditingCell3();
            }
        };

        Callback<TableColumn<Result, Integer>, TableCell<Result, Integer>> integerCellFactory = new Callback<TableColumn<Result, Integer>, TableCell<Result, Integer>>() {

            @Override
            public TableCell<Result, Integer> call(TableColumn<Result, Integer> p) {
                return new EditingCellResultInteger();
            }
        };

        colSampleNumResults.setEditable(false);
        colSampleNumResults.setCellValueFactory(new PropertyValueFactory<Result, Integer>("sampleNum"));
        //colSampleNumResults.setCellFactory(TextFieldTableCell.<Result, Integer>forTableColumn(new IntegerStringConverter()));
        colSampleNumResults.setCellFactory(integerCellFactory);

        colWetDensityResults.setEditable(false);
        colWetDensityResults.setCellValueFactory(new PropertyValueFactory<Result, Double>("wetDensity"));
        colWetDensityResults.setCellFactory(cellFactory);

        colDryDensityResults.setEditable(false);
        colDryDensityResults.setCellValueFactory(new PropertyValueFactory<Result, Double>("dryDensity"));
        colDryDensityResults.setCellFactory(cellFactory);

        colDurabilityResults.setEditable(false);
        colDurabilityResults.setCellValueFactory(new PropertyValueFactory<Result, Double>("durability"));
        colDurabilityResults.setCellFactory(cellFactory);
    }

    @FXML
    private void btnAddSampleOnAction(ActionEvent event) {
        blogic.AddNewSampleTest();
    }

    @FXML
    private void btnRemoveSampleOnAction(ActionEvent event) {
        blogic.RemoveSampleTest(tblSample.getSelectionModel().getSelectedItem());
    }

    @FXML
    private void btnAddBottleOnAction(ActionEvent event) {
        blogic.AddNewHumidityTest();
    }

    @FXML
    private void btnRemoveBottleOnAction(ActionEvent event) {
        blogic.RemoveHumidityTest(tblHumidity.getSelectionModel().getSelectedItem());
    }

    @FXML
    private void comboSampleTypeOnAction(ActionEvent event) {
        blogic.setSelectedSampleType(comboSampleType.getSelectionModel().getSelectedItem());

        tblHumidity.setEditable(true);
        tblSample.setEditable(true);

        if (comboSampleType.getSelectionModel().getSelectedItem() == null) {
            tblHumidity.setEditable(false);
            tblSample.setEditable(false);
        }
    }

    @FXML
    private void comboPartNumOnAction(ActionEvent event) {
        txtTestsDate.textProperty().set("");
        comboSampleType.getSelectionModel().select(null);
        comboTester.getSelectionModel().select(null);

        if (comboPartNum.getSelectionModel().getSelectedItem() != null) {
            lblProductType.setText(comboPartNum.getSelectionModel().getSelectedItem().getProductName());
            blogic.setSelectedPart(comboPartNum.getSelectionModel().getSelectedItem());
        }
    }

    @FXML
    private void comboTesterOnAction(ActionEvent event) {
        if (comboTester.getSelectionModel().getSelectedItem() != null) {
            blogic.setSelectedPerson((Person) comboTester.getSelectionModel().getSelectedItem());
        }
    }

    @FXML
    private void btnAcceptOnAction(ActionEvent event) {
        try {
            blogic.addOrUpdateTestIntoDb();
        } catch (ParseException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void onValueChanged(String name, Object changedObject) {

        if (changedObject == null || name == null) {
            return;
        }

        switch (name) {
            case "Person":
                comboTester.getSelectionModel().select((Person) changedObject);
                break;
            case "SampleType":
                comboSampleType.getSelectionModel().select((SampleType) changedObject);
                break;
            case "AddMeasureName1":
                comboAdditionalMeasure1.getSelectionModel().select((String) changedObject);
                break;
            case "AddMeasureName2":
                comboAdditionalMeasure2.getSelectionModel().select((String) changedObject);
                break;
        }
    }

    @FXML
    private void comboAdditionalMeasure1onAction(ActionEvent event) {
        blogic.setAddMeasureName1(comboAdditionalMeasure1.getSelectionModel().getSelectedItem());
    }

    @FXML
    private void comboAdditionalMeasure2onAction(ActionEvent event) {
        blogic.setAddMeasureName2(comboAdditionalMeasure2.getSelectionModel().getSelectedItem());
    }

    @FXML
    private void mnuSettingsOnClick(ActionEvent event) {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("SettingsDialog.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Настройки");
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void comboPartNumStatOnAction(ActionEvent event) {
    }

    @FXML
    private void tabStatisticsOnSelectionChanged(Event event) {
        //Если выбрана вкладка со статистикой ГП.
        if (tabStatistics.isSelected()) {
            //Заполняем номера партий.
            comboPartNumStat.setItems(blogic.getPartNumbers());
            comboProdNameStat.setItems(blogic.getProducts());
            //comboMonthStat.setItems(blogic.getMonths());
            //comboYearStat.setItems(blogic.getYears());
            comboDensityMarkStat.setItems(blogic.getDensityMarks());
            comboDurabilityMarkStat.setItems(blogic.getDurabilityMarks());
        }
    }

    @FXML
    private void comboDensityMarkStatOnAction(ActionEvent event) {
    }

    @FXML
    private void comboProdNameStatOnAction(ActionEvent event) {
    }

    @FXML
    private void comboDurabilityMarkStatOnAction(ActionEvent event) {
    }

    @FXML
    private void comboMonthStatOnAction(ActionEvent event) {
    }

    @FXML
    private void comboYearStatOnAction(ActionEvent event) {
    }

    @FXML
    private void tblStatisticsOnMouseClicked(MouseEvent event) {
        if (event.getClickCount() == 2) {
            Stat s = tblStatistics.getSelectionModel().getSelectedItem();
            tabPane.getSelectionModel().select(tabProductTest);

            for (Part p : blogic.getPartNumbers()) {
                if (p.getPartNum() != null && p.getPartNum().equals(s.getPartNo())) {
                    comboPartNum.getSelectionModel().select(p);
                    return;
                }
            }
        }
    }

    @FXML
    private void tabPassportsOnSelectionChanged(Event event) {
        if (tabPassports.isSelected()) {
            //Инициализируем элементы управления при заходе на вкладку.
            if (comboMonthPass.getItems().size() == 0) {
                comboMonthPass.setItems(blogic.getMonths());
                comboMonthPass.valueProperty().bindBidirectional(blogic.selectedMonthPassProperty());
                comboMonthPass.getSelectionModel().select(Calendar.getInstance().get(Calendar.MONTH) + 1);  //Устанавливаем текущий месяц.

                //Устанавливаем текст по-умлочанию для таблицы с паспортами.
                tblPassports.setPlaceholder(new Text("Реестр паспортов ГП"));

                //Связываем количество строк в таблице с элементом управления.
                tblPassportsItemsCount.textProperty().bindBidirectional(blogic.tblPassportsItemsCountProperty(), new NumberStringConverter());
            }

            if (comboYearPass.getItems().size() == 0) {
                comboYearPass.setItems(blogic.getYears());
                comboYearPass.valueProperty().bindBidirectional(blogic.selectedYearPassProperty());

                int currentYear = Calendar.getInstance().get(Calendar.YEAR);
                for (Year y : blogic.getYears()) {
                    if (y.getReturnValue() == currentYear) {
                        comboYearPass.getSelectionModel().select(y);
                        break;
                    }
                }
            }
        }
    }

    @FXML
    private void btnAddPassOnAction(ActionEvent event) {
        OpenPassportDialog(new ProductPassport());
    }

    @FXML
    private void btnChangePassOnAction(ActionEvent event) {
        if (tblPassports.getSelectionModel().getSelectedItem() != null) {
            OpenPassportDialog(tblPassports.getSelectionModel().getSelectedItem());
        }
    }

    private void OpenPassportDialog(ProductPassport pp) {
        FXMLLoader root;
        try {
            root = new FXMLLoader(getClass().getResource("ProductPassportDialog.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Добавление паспорта ГП");
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            Image img = new Image(getClass().getResourceAsStream("logo.png"));
            stage.getIcons().add(img);
            stage.setScene(new Scene((Pane) root.load()));

            ProductPassportDialogController ppdc = root.<ProductPassportDialogController>getController();
            ppdc.initData(pp, blogic.getPartNumbers(), blogic.getProducts());

            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void tblPassportsOnMouseClicked(MouseEvent event) {
        if (event.getClickCount() == 2) {
            if (tblPassports.getSelectionModel().getSelectedItem() != null) {
                OpenPassportDialog(tblPassports.getSelectionModel().getSelectedItem());
            }
        }
    }

    class EditingCell extends TableCell<SampleTest, Double> {

        private TextField textField;

        public EditingCell() {
        }

        @Override
        public void startEdit() {
            if (!isEmpty()) {
                super.startEdit();
                createTextField();
                setText(null);
                setGraphic(textField);
                textField.selectAll();

                if (textField.getText().equals("0.00")) {
                    textField.clear();
                    textField.requestFocus();
                } else {
                    textField.requestFocus();
                    textField.positionCaret(textField.getText().length() - 1);
                }
            }
        }

        @Override
        public void cancelEdit() {
            super.cancelEdit();

            setText((String) getItem().toString());
            setGraphic(null);
        }

        @Override
        public void updateItem(Double item, boolean empty) {
            super.updateItem(item, empty);

            if (empty) {
                setText(null);
                setGraphic(null);
            } else {
                if (isEditing()) {
                    if (textField != null) {
                        textField.setText(getString());
                    }
                    setText(null);
                    setGraphic(textField);
                } else {
                    setText(getString());
                    setGraphic(null);
                }
            }

            //Устанавливаем выравнивание по правой стороне.
            setAlignment(Pos.CENTER_RIGHT);
        }

        private void createTextField() {
            textField = new TextField(getString());
            textField.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
            final DoubleConverter dc = new DoubleConverter();
            textField.focusedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> arg0,
                        Boolean arg1, Boolean arg2) {
                    if (!arg2) {
                        commitEdit(dc.fromString(textField.getText()));
                    }
                }
            });

            textField.setOnKeyPressed(new EventHandler<KeyEvent>() {

                @Override
                public void handle(KeyEvent t) {
                    if (t.getCode() == KeyCode.ENTER) {
                        commitEdit(dc.fromString(textField.getText()));

                        if (getTableView().getFocusModel().getFocusedCell().getColumn() - 1 == getTableView().getColumns().size()) {
                            return;
                        }

                        getTableView().getFocusModel().focusRightCell();
                        TableColumn tc = getTableView().getFocusModel().getFocusedCell().getTableColumn();
                        getTableView().edit(getTableRow().getIndex(), tc);
                    }
                }
            });
        }

        private String getString() {
            return getItem() == null ? "" : String.format("%1$,.2f", getItem());
        }
    }

    class EditingCell2 extends TableCell<HumidityTest, Double> {

        private TextField textField;

        public EditingCell2() {
        }

        @Override
        public void startEdit() {
            if (!isEmpty()) {
                super.startEdit();
                createTextField();
                setText(null);
                setGraphic(textField);
                //textField.selectAll();

                if (textField.getText().equals("0.00")) {
                    textField.clear();
                    textField.requestFocus();
                } else {
                    textField.requestFocus();
                    textField.positionCaret(textField.getText().length() - 1);
                }
            }
        }

        @Override
        public void cancelEdit() {
            super.cancelEdit();

            setText((String) getItem().toString());
            setGraphic(null);
        }

        @Override
        public void updateItem(Double item, boolean empty) {
            super.updateItem(item, empty);

            if (empty) {
                setText(null);
                setGraphic(null);
            } else {
                if (isEditing()) {
                    if (textField != null) {
                        textField.setText(getString());
                    }
                    setText(null);
                    setGraphic(textField);
                } else {
                    setText(getString());
                    setGraphic(null);
                }
            }

            //Устанавливаем выравнивание по правой стороне.
            setAlignment(Pos.CENTER_RIGHT);
        }

        private void createTextField() {
            textField = new TextField(getString());
            textField.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
            final DoubleConverter dc = new DoubleConverter();
            textField.focusedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> arg0,
                        Boolean arg1, Boolean arg2) {
                    if (!arg2) {
                        commitEdit(dc.fromString(textField.getText()));
                    }
                }
            });

            textField.setOnKeyPressed(new EventHandler<KeyEvent>() {

                @Override
                public void handle(KeyEvent t) {
                    if (t.getCode() == KeyCode.ENTER) {
                        commitEdit(dc.fromString(textField.getText()));
                        if (getTableView().getFocusModel().getFocusedCell().getColumn() == getTableView().getColumns().size()) {
                            return;
                        }

                        getTableView().getFocusModel().focusRightCell();
                        getTableView().edit(getTableRow().getIndex(), getTableView().getFocusModel().getFocusedCell().getTableColumn());
                    }
                }
            });
        }

        private String getString() {
            return getItem() == null ? "" : String.format("%1$,.2f", getItem());
        }
    }

    class EditingCell3 extends TableCell<Result, Double> {

        private TextField textField;

        public EditingCell3() {
        }

        @Override
        public void startEdit() {
            if (!isEmpty()) {
                super.startEdit();
                createTextField();
                setText(null);
                setGraphic(textField);
                //textField.selectAll();

                if (textField.getText().equals("0.00")) {
                    textField.clear();
                    textField.requestFocus();
                } else {
                    textField.requestFocus();
                    textField.positionCaret(textField.getText().length() - 1);
                }
            }
        }

        @Override
        public void cancelEdit() {
            super.cancelEdit();

            setText((String) getItem().toString());
            setGraphic(null);
        }

        @Override
        public void updateItem(Double item, boolean empty) {
            super.updateItem(item, empty);

            if (empty) {
                setText(null);
                setGraphic(null);
            } else {
                if (isEditing()) {
                    if (textField != null) {
                        textField.setText(getString());
                    }
                    setText(null);
                    setGraphic(textField);
                } else {
                    setText(getString());
                    setGraphic(null);
                }
            }

            //Устанавливаем выравнивание по правой стороне.
            setAlignment(Pos.CENTER_RIGHT);
        }

        private void createTextField() {
            textField = new TextField(getString());
            textField.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
            final DoubleConverter dc = new DoubleConverter();
            textField.focusedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> arg0,
                        Boolean arg1, Boolean arg2) {
                    if (!arg2) {
                        commitEdit(dc.fromString(textField.getText()));
                    }
                }
            });

            textField.setOnKeyPressed(new EventHandler<KeyEvent>() {

                @Override
                public void handle(KeyEvent t) {
                    if (t.getCode() == KeyCode.ENTER) {
                        commitEdit(dc.fromString(textField.getText()));
                    }
                }
            });
        }

        private String getString() {
            return getItem() == null ? "" : String.format("%1$,.2f", getItem());
        }
    }

    class EditingCellSampleTestInteger extends TableCell<SampleTest, Integer> {

        private TextField textField;

        public EditingCellSampleTestInteger() {
        }

        @Override
        public void startEdit() {
            if (!isEmpty()) {
                super.startEdit();
                createTextField();
                setText(null);
                setGraphic(textField);
                textField.selectAll();

                if (textField.getText().equals("0")) {
                    textField.clear();
                    textField.requestFocus();
                } else {
                    textField.requestFocus();
                    textField.positionCaret(textField.getText().length() - 1);
                }
            }
        }

        @Override
        public void cancelEdit() {
            super.cancelEdit();

            setText((String) getItem().toString());
            setGraphic(null);
        }

        @Override
        public void updateItem(Integer item, boolean empty) {
            super.updateItem(item, empty);

            if (empty) {
                setText(null);
                setGraphic(null);
            } else {
                if (isEditing()) {
                    if (textField != null) {
                        textField.setText(getString());
                    }
                    setText(null);
                    setGraphic(textField);
                } else {
                    setText(getString());
                    setGraphic(null);
                }
            }

            //Устанавливаем выравнивание по правой стороне.
            setAlignment(Pos.CENTER_RIGHT);
        }

        private void createTextField() {
            textField = new TextField(getString());
            textField.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
            final IntegerStringConverter ic = new IntegerStringConverter();
            //final DoubleConverter dc = new DoubleConverter();
            textField.focusedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> arg0,
                        Boolean arg1, Boolean arg2) {
                    if (!arg2) {
                        commitEdit(ic.fromString(textField.getText()));
                    }
                }
            });

            textField.setOnKeyPressed(new EventHandler<KeyEvent>() {

                @Override
                public void handle(KeyEvent t) {
                    if (t.getCode() == KeyCode.ENTER) {
                        commitEdit(ic.fromString(textField.getText()));

                        if (getTableView().getFocusModel().getFocusedCell().getColumn() - 1 == getTableView().getColumns().size()) {
                            return;
                        }

                        getTableView().getFocusModel().focusRightCell();
                        getTableView().edit(getTableRow().getIndex(), getTableView().getFocusModel().getFocusedCell().getTableColumn());
                    }
                }
            });
        }

        private String getString() {
            return getItem() == null ? "" : getItem().toString();
        }
    }

    class EditingCellResultInteger extends TableCell<Result, Integer> {

        private TextField textField;

        public EditingCellResultInteger() {
        }

        @Override
        public void startEdit() {
            if (!isEmpty()) {
                super.startEdit();
                createTextField();
                setText(null);
                setGraphic(textField);
                textField.selectAll();

                if (textField.getText().equals("0")) {
                    textField.clear();
                    textField.requestFocus();
                } else {
                    textField.requestFocus();
                    textField.positionCaret(textField.getText().length() - 1);
                }
            }
        }

        @Override
        public void cancelEdit() {
            super.cancelEdit();

            setText((String) getItem().toString());
            setGraphic(null);
        }

        @Override
        public void updateItem(Integer item, boolean empty) {
            super.updateItem(item, empty);

            if (empty) {
                setText(null);
                setGraphic(null);
            } else {
                if (isEditing()) {
                    if (textField != null) {
                        textField.setText(getString());
                    }
                    setText(null);
                    setGraphic(textField);
                } else {
                    setText(getString());
                    setGraphic(null);
                }
            }

            //Устанавливаем выравнивание по правой стороне.
            setAlignment(Pos.CENTER_RIGHT);
        }

        private void createTextField() {
            textField = new TextField(getString());
            textField.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
            final IntegerStringConverter ic = new IntegerStringConverter();
            //final DoubleConverter dc = new DoubleConverter();
            textField.focusedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> arg0,
                        Boolean arg1, Boolean arg2) {
                    if (!arg2) {
                        commitEdit(ic.fromString(textField.getText()));
                    }
                }
            });

            textField.setOnKeyPressed(new EventHandler<KeyEvent>() {

                @Override
                public void handle(KeyEvent t) {
                    if (t.getCode() == KeyCode.ENTER) {
                        commitEdit(ic.fromString(textField.getText()));

                        if (getTableView().getFocusModel().getFocusedCell().getColumn() - 1 == getTableView().getColumns().size()) {
                            return;
                        }

                        getTableView().getFocusModel().focusRightCell();
                        getTableView().edit(getTableRow().getIndex(), getTableView().getFocusModel().getFocusedCell().getTableColumn());
                    }
                }
            });
        }

        private String getString() {
            return getItem() == null ? "" : getItem().toString();
        }
    }

    class EditingCellHumidityInteger extends TableCell<HumidityTest, Integer> {

        private TextField textField;

        public EditingCellHumidityInteger() {
        }

        @Override
        public void startEdit() {
            if (!isEmpty()) {
                super.startEdit();
                createTextField();
                setText(null);
                setGraphic(textField);
                textField.selectAll();

                if (textField.getText().equals("0")) {
                    textField.clear();
                    textField.requestFocus();
                } else {
                    textField.requestFocus();
                    textField.positionCaret(textField.getText().length() - 1);
                }
            }
        }

        @Override
        public void cancelEdit() {
            super.cancelEdit();

            setText((String) getItem().toString());
            setGraphic(null);
        }

        @Override
        public void updateItem(Integer item, boolean empty) {
            super.updateItem(item, empty);

            if (empty) {
                setText(null);
                setGraphic(null);
            } else {
                if (isEditing()) {
                    if (textField != null) {
                        textField.setText(getString());
                    }
                    setText(null);
                    setGraphic(textField);
                } else {
                    setText(getString());
                    setGraphic(null);
                }
            }

            //Устанавливаем выравнивание по правой стороне.
            setAlignment(Pos.CENTER_RIGHT);
        }

        private void createTextField() {
            textField = new TextField(getString());
            textField.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
            final IntegerStringConverter ic = new IntegerStringConverter();
            //final DoubleConverter dc = new DoubleConverter();
            textField.focusedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> arg0,
                        Boolean arg1, Boolean arg2) {
                    if (!arg2) {
                        commitEdit(ic.fromString(textField.getText()));
                    }
                }
            });

            textField.setOnKeyPressed(new EventHandler<KeyEvent>() {

                @Override
                public void handle(KeyEvent t) {
                    if (t.getCode() == KeyCode.ENTER) {
                        commitEdit(ic.fromString(textField.getText()));

                        if (getTableView().getFocusModel().getFocusedCell().getColumn() - 1 == getTableView().getColumns().size()) {
                            return;
                        }

                        getTableView().getFocusModel().focusRightCell();
                        getTableView().edit(getTableRow().getIndex(), getTableView().getFocusModel().getFocusedCell().getTableColumn());
                    }
                }
            });
        }

        private String getString() {
            return getItem() == null ? "" : getItem().toString();
        }
    }

    /**
     * Используется для преобразования строки в Double и обратно.
     */
    private class DoubleConverter extends StringConverter<Double> {

        @Override
        public String toString(Double t) {
            if (t != null) {
                return t.toString();
            } else {
                return "0.0";
            }
        }

        @Override
        public Double fromString(String string) {
            //lblInfo.setVisible(false);

            string = string.replace(',', '.');

            try {
                return Double.parseDouble(string);
            } catch (NumberFormatException e) {
                //lblInfo.setText("'" + string + "' не является числом!");
                //lblInfo.setTextFill(Color.RED);
                //lblInfo.setVisible(true);
                return 0.0;
            }
        }

    }
}
