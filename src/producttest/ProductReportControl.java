/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package producttest;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import producttest.bll.BLogic;
import producttest.model.Month;
import producttest.model.ProductPassport;
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
    private DatePicker txtDateFrom;
    @FXML
    private DatePicker txtDateTo;
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
            
//</editor-fold>

    BLogic blogic;

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
    }

    /**
     * Инициализирует элемент управления с отчётом по готовой продукции.
     * @param bl - класс бизнес логики для приложения.
     */
    public void init(BLogic bl) {
        this.blogic = bl;
        
        //Устанавливаем виды отчётов в выпадающий список.
        comboReportType.setItems(FXCollections.observableArrayList("По умолчанию", "Дефекты", "Полный", "Краткий", "Характеристики"));
        comboReportType.getSelectionModel().select(0);

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
        
        colAutoclaveNum.setCellValueFactory(new PropertyValueFactory<>("autoclaveNo"));
        
        colDefName.setCellValueFactory(new PropertyValueFactory<>("defName"));
                
        colDefRate.setCellValueFactory(new PropertyValueFactory<>("defRate"));
                
        colHumidity.setCellValueFactory(new PropertyValueFactory<>("humidity"));
                        
        colAvgDensity.setCellValueFactory(new PropertyValueFactory<>("avgDensity"));
                        
        colDensityMark.setCellValueFactory(new PropertyValueFactory<>("densityMark"));
                
        colAvgDurability.setCellValueFactory(new PropertyValueFactory<>("avgDuarability"));
                        
        colDurabilityMark.setCellValueFactory(new PropertyValueFactory<>("durabilityMark"));

        tblProductReport.setItems(blogic.getProductsReport());
    }
    
    
    //<editor-fold defaultstate="collapsed" desc="Обработчики событий">
    @FXML
    private void comboReportTypeOnAction(ActionEvent e){
        
    }
//</editor-fold>
}
