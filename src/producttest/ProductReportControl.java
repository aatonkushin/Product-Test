/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package producttest;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import javafx.util.converter.NumberStringConverter;
import producttest.bll.BLogic;
import producttest.helpers.ErrorDialog;
import producttest.helpers.FloatProductReportCallback;
import producttest.helpers.IntegerProductReportCallback;
import producttest.model.ProductReportRecord;

/**
 * FXML Controller class
 *
 * @author tonkushin
 */
public class ProductReportControl extends VBox {

    //<editor-fold defaultstate="collapsed" desc="Поля элементов упарвления">
    @FXML
    private ComboBox<String> comboReportType;

    @FXML
    private DatePicker dateFrom;
    @FXML
    private DatePicker dateTo;

    @FXML
    private TableColumn<ProductReportRecord, String> colPartNum;

    @FXML
    private TableView<ProductReportRecord> tblProductReport;

    @FXML
    private TableColumn<ProductReportRecord, Date> colDate;

    @FXML
    private TableColumn<ProductReportRecord, String> colProductName;

    @FXML
    private TableColumn<ProductReportRecord, Float> colVolume;

    @FXML
    private TableColumn<ProductReportRecord, Integer> colAutoclaveNum;

    @FXML
    private TableColumn<ProductReportRecord, String> colDefName;

    @FXML
    private TableColumn<ProductReportRecord, Integer> colDefRate;

    @FXML
    private TableColumn<ProductReportRecord, Float> colHumidity;

    @FXML
    private TableColumn<ProductReportRecord, Float> colAvgDensity;

    @FXML
    private TableColumn<ProductReportRecord, String> colDensityMark;

    @FXML
    private TableColumn<ProductReportRecord, Float> colAvgDurability;

    @FXML
    private TableColumn<ProductReportRecord, String> colDurabilityMark;

    @FXML
    private TableColumn<ProductReportRecord, String> colFrostresist;

    @FXML
    private TableColumn<ProductReportRecord, String> colActivity;

    @FXML
    private TableColumn<ProductReportRecord, Float> colHeatconduction;

    @FXML
    private TableColumn<ProductReportRecord, Float> colSteamfactor;

    @FXML
    private TableColumn<ProductReportRecord, Float> colShrinkage;

    @FXML
    private Label lblItemsCount;    //количество записей в таблице

    @FXML
    private VBox mainVBox;

    @FXML
    private MenuItem mnuCopy;

//</editor-fold>
    BLogic blogic;

    private boolean isInitialized = false;

    /**
     * Указывает на то, что элемент управления был инициализирован.
     *
     * @return
     */
    public boolean IsInitialized() {
        return this.isInitialized;
    }

    public ProductReportControl() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                "ProductReportControl.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        //Отслеживаем высоту элемента управления для изменения высоты таблицы.
        mainVBox.heightProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                tblProductReport.setPrefHeight(tblProductReport.getPrefHeight() + ((Double) newValue - (Double) oldValue));
                //System.out.println("ProductReportControloldValue: " + oldValue +"|" + newValue);
                //System.out.println("tblProductReport height: "+ tblProductReport.getHeight());
            }
        });
    }

    /**
     * Инициализирует элемент управления с отчётом по готовой продукции.
     *
     * @param bl - класс бизнес логики для приложения.
     */
    public void init(BLogic bl) {
        this.blogic = bl;

        //Устанавливаем виды отчётов в выпадающий список.
        comboReportType.setItems(FXCollections.observableArrayList("По умолчанию", "Дефекты", "Полный", "Краткий", "Характеристики"));
        comboReportType.getSelectionModel().select(0);

        //Настройка DatePicker'ов.
        Calendar c = new GregorianCalendar();
        c.set(Calendar.DAY_OF_MONTH, 1);
        dateFrom.setValue(c.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());

        dateTo.setValue((new Date()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate());

        //Настройка колонок таблицы.
        tblProductReport.setEditable(false);

        colPartNum.setEditable(false);
        colPartNum.setCellValueFactory(new PropertyValueFactory<>("partNum"));

        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colDate.setCellFactory(new Callback<TableColumn<ProductReportRecord, Date>, TableCell<ProductReportRecord, Date>>() {

            //Взято из: http://code.makery.ch/blog/javafx-2-tableview-cell-renderer/
            @Override
            public TableCell<ProductReportRecord, Date> call(TableColumn<ProductReportRecord, Date> p) {
                return new TableCell<ProductReportRecord, Date>() {
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

        colProductName.setCellValueFactory(new PropertyValueFactory<>("productName"));

        colVolume.setCellValueFactory(new PropertyValueFactory<>("volume"));
        colVolume.setCellFactory(new FloatProductReportCallback(Boolean.FALSE));

        colAutoclaveNum.setCellValueFactory(new PropertyValueFactory<>("autoclaveNo"));
        colAutoclaveNum.setCellFactory(new IntegerProductReportCallback());

        colDefName.setCellValueFactory(new PropertyValueFactory<>("defName"));

        colDefRate.setCellValueFactory(new PropertyValueFactory<>("defRate"));
        colDefRate.setCellFactory(new IntegerProductReportCallback());

        colHumidity.setCellValueFactory(new PropertyValueFactory<>("humidity"));
        colHumidity.setCellFactory(new FloatProductReportCallback(false));

        colAvgDensity.setCellValueFactory(new PropertyValueFactory<>("avgDensity"));
        colAvgDensity.setCellFactory(new FloatProductReportCallback(false));

        colDensityMark.setCellValueFactory(new PropertyValueFactory<>("densityMark"));

        colAvgDurability.setCellValueFactory(new PropertyValueFactory<>("avgDurability"));
        colAvgDurability.setCellFactory(new FloatProductReportCallback(false));

        colDurabilityMark.setCellValueFactory(new PropertyValueFactory<>("durabilityMark"));

        colFrostresist.setCellValueFactory(new PropertyValueFactory<>("frostResist"));

        colActivity.setCellValueFactory(new PropertyValueFactory<>("activity"));

        colHeatconduction.setCellValueFactory(new PropertyValueFactory<>("heatConduction"));
        colHeatconduction.setCellFactory(new FloatProductReportCallback(false));

        colSteamfactor.setCellValueFactory(new PropertyValueFactory<>("steamFactor"));
        colSteamfactor.setCellFactory(new FloatProductReportCallback(false));

        colShrinkage.setCellValueFactory(new PropertyValueFactory<>("shrinkage"));
        colShrinkage.setCellFactory(new FloatProductReportCallback(false));

        //loadProductsReport(); //загрузка происходит в обработчиках dateTo и dateFrom.
        //Заполняем таблицу из базы данных.
        tblProductReport.setItems(blogic.getProductsReport());
        tblProductReport.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        //Количество записей в таблице.
        lblItemsCount.textProperty().bindBidirectional(blogic.productReportsCountProperty(), new NumberStringConverter());

        isInitialized = true;
    }

    /**
     * Загружает отчёт по ГП из БД.
     */
    private void loadProductsReport() {
        if (dateFrom.getValue() == null || dateTo.getValue() == null) {
            return;
        }

        Boolean shortReport = false;

        if (comboReportType.getSelectionModel().getSelectedIndex() == 3) {
            shortReport = true;
        }

        Instant instantFrom = dateFrom.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
        Instant instantTo = dateTo.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();

        try {
            blogic.updateProductsReport(Date.from(instantFrom), Date.from(instantTo), shortReport);
        } catch (ParseException | SQLException ex) {
            ErrorDialog dialog = new ErrorDialog(Alert.AlertType.ERROR, "Ошибка загрузки данных в таблицу ", ex);
            dialog.showAndWait();
        }

        System.out.println("Кол-во записей в Отчёте по ГП: " + blogic.getProductsReport().size());
    }

    //<editor-fold defaultstate="collapsed" desc="Обработчики событий">
    @FXML
    private void comboReportTypeOnAction(ActionEvent e) {
        loadProductsReport();

        if (comboReportType.getSelectionModel().getSelectedIndex() == 0) {
            //Отображение по умолчанию.
            colPartNum.setVisible(true);
            colDate.setVisible(true);
            colProductName.setVisible(true);
            colAutoclaveNum.setVisible(true);
            colDefName.setVisible(true);
            colDefRate.setVisible(true);
            colVolume.setVisible(true);
            colHumidity.setVisible(true);
            colAvgDensity.setVisible(true);
            colDensityMark.setVisible(true);
            colAvgDurability.setVisible(true);
            colDurabilityMark.setVisible(true);

            colFrostresist.setVisible(false);
            colActivity.setVisible(false);
            colHeatconduction.setVisible(false);
            colSteamfactor.setVisible(false);
            colShrinkage.setVisible(false);
        } else if (comboReportType.getSelectionModel().getSelectedIndex() == 1) {
            //Отображение только дефектов.
            colPartNum.setVisible(true);
            colDate.setVisible(true);
            colProductName.setVisible(true);
            colAutoclaveNum.setVisible(true);
            colDefName.setVisible(true);
            colDefRate.setVisible(true);

            colVolume.setVisible(false);
            colHumidity.setVisible(false);
            colAvgDensity.setVisible(false);
            colDensityMark.setVisible(false);
            colAvgDurability.setVisible(false);
            colDurabilityMark.setVisible(false);
            colFrostresist.setVisible(false);
            colActivity.setVisible(false);
            colHeatconduction.setVisible(false);
            colSteamfactor.setVisible(false);
            colShrinkage.setVisible(false);
        } else if (comboReportType.getSelectionModel().getSelectedIndex() == 2) {
            //Отображение полного отчёта.
            colPartNum.setVisible(true);
            colDate.setVisible(true);
            colProductName.setVisible(true);
            colAutoclaveNum.setVisible(true);
            colDefName.setVisible(true);
            colDefRate.setVisible(true);
            colVolume.setVisible(true);
            colHumidity.setVisible(true);
            colAvgDensity.setVisible(true);
            colDensityMark.setVisible(true);
            colAvgDurability.setVisible(true);
            colDurabilityMark.setVisible(true);
            colFrostresist.setVisible(true);
            colActivity.setVisible(true);
            colHeatconduction.setVisible(true);
            colSteamfactor.setVisible(true);
            colShrinkage.setVisible(true);
        } else if (comboReportType.getSelectionModel().getSelectedIndex() == 3) {
            //Отображение краткого отчёта.
            colPartNum.setVisible(true);
            colDate.setVisible(true);
            colProductName.setVisible(true);
            colAutoclaveNum.setVisible(false);
            colDefName.setVisible(true);
            colDefRate.setVisible(true);
            colVolume.setVisible(false);
            colHumidity.setVisible(true);
            colAvgDensity.setVisible(true);
            colDensityMark.setVisible(false);
            colAvgDurability.setVisible(true);
            colDurabilityMark.setVisible(false);
            colFrostresist.setVisible(false);
            colActivity.setVisible(false);
            colHeatconduction.setVisible(false);
            colSteamfactor.setVisible(false);
            colShrinkage.setVisible(false);
        } else if (comboReportType.getSelectionModel().getSelectedIndex() == 4) {
            //Отображение отчёта с характеристиками.
            colPartNum.setVisible(true);
            colDate.setVisible(true);
            colProductName.setVisible(true);
            colAutoclaveNum.setVisible(true);
            colDefName.setVisible(false);
            colDefRate.setVisible(false);
            colVolume.setVisible(false);
            colHumidity.setVisible(true);
            colAvgDensity.setVisible(true);
            colDensityMark.setVisible(true);
            colAvgDurability.setVisible(true);
            colDurabilityMark.setVisible(true);
            colFrostresist.setVisible(true);
            colActivity.setVisible(true);
            colHeatconduction.setVisible(true);
            colSteamfactor.setVisible(true);
            colShrinkage.setVisible(true);
        }
    }

    @FXML
    private void dateFromOnChanged(ActionEvent e) {
        loadProductsReport();
    }

    @FXML
    private void dateToOnChanged(ActionEvent e) {
        loadProductsReport();
    }

    @FXML
    private void mnuCopyOnAction(ActionEvent event) {
        ObservableList rowList = (ObservableList) tblProductReport.getSelectionModel().getSelectedItems();

        StringBuilder clipboardString = new StringBuilder();

        for (Iterator it = rowList.iterator(); it.hasNext();) {
            ProductReportRecord row = (ProductReportRecord) it.next();

                clipboardString.append(row);
            clipboardString.append('\n');

        }
        final ClipboardContent content = new ClipboardContent();

        content.putString(clipboardString.toString());
        Clipboard.getSystemClipboard().setContent(content);
    }

//</editor-fold>
}
