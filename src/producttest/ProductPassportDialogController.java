/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package producttest;

import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import producttest.bll.BLogic;
import producttest.model.Part;
import producttest.model.Product;
import producttest.model.ProductPassport;
import producttest.model.Stat;
import producttest.model.Year;

/**
 * FXML Controller class
 *
 * @author tonkushin
 */
public class ProductPassportDialogController implements Initializable {
    @FXML
    private ComboBox<Part> comboPartNum;
    @FXML
    private ComboBox<Product> comboProduct;
    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField txtAvgDurability;
    @FXML
    private TextField txtAvgDensity;
    @FXML
    private TextField txtHumidity;
    @FXML
    private TextField txtDurabilityMark;
    @FXML
    private TextField txtReqDurability;
    @FXML
    private TextField txtSteamFactor;
    @FXML
    private TextField txtFrostresist;
    @FXML
    private TextField txtHeatConduction;
    @FXML
    private TextField txtShrinkage;
    @FXML
    private TextField txtActivity;
    @FXML
    private TextField txtNotes;
    @FXML
    private Button btnOk;
    @FXML
    private Button btnCancel;
    
    BLogic blogic;                      //
    ProductPassport productPassport;    //
    
    Boolean firstChange = false;                //Флаг первого изменения в комбобоксе (нужен, что бы не перезаписывать данные полях при первой загрузке в режиме изменения)

    public void initData(ProductPassport productPassport, BLogic blogic){
        
        this.productPassport = productPassport;
        this.blogic = blogic;
        
        Locale.setDefault(new Locale("ru"));    //Из за datePicker'a, но вообще, нужно было в начале установить ru в ProductTest.java
        
        comboPartNum.setItems(blogic.getPartNumbers());
        comboProduct.setItems(blogic.getProducts());
        
        if (productPassport.getId() != -1) {
            firstChange = true;
            comboProduct.getSelectionModel().select(productPassport.getProduct());
            datePicker.setValue(productPassport.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            txtAvgDurability.setText(productPassport.getAvgDurability().toString());
            txtAvgDensity.setText(productPassport.getAvgDensity().toString());
            txtHumidity.setText(productPassport.getHymidity().toString());
            txtDurabilityMark.setText(productPassport.getDurabilityMark());
            txtReqDurability.setText(productPassport.getReqDurability().toString());
            txtSteamFactor.setText(productPassport.getSteamFactor().toString());
            txtFrostresist.setText(productPassport.getFrostResist());
            txtHeatConduction.setText(productPassport.getHeatConduction().toString());
            txtShrinkage.setText(productPassport.getShrinkage().toString());
            txtActivity.setText(productPassport.getActivity());
            txtNotes.setText(productPassport.getNotes());
            
            for(Part p : comboPartNum.getItems()){
                if (p.getPartNum() != null && p.getPartNum().equals(productPassport.getPartNum())) {
                    comboPartNum.getSelectionModel().select(p);
                    break;
                }
            }
        } else {
            datePicker.setValue(LocalDate.now());
        }
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    @FXML
    private void btnOkOnAction(ActionEvent event) {
        Locale.setDefault(new Locale("en"));    //Из за datePicker'a
        
         // get a handle to the stage
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

    @FXML
    private void btnCancelOnAction(ActionEvent event) {
        Locale.setDefault(new Locale("en"));    //Из за datePicker'a
        
        // get a handle to the stage
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

    @FXML
    private void comboPartNumOnChange(ActionEvent event) {
        
        if(comboPartNum.getSelectionModel().getSelectedIndex() == 0){
            return;
        }
        
        if (firstChange) {
            firstChange = false;
            return;
        }
        
        //Устанавливаем тип продукции.
        for(Product product: comboProduct.getItems()){
            if (product.getId() == comboPartNum.getSelectionModel().getSelectedItem().getProductId()) {
                comboProduct.getSelectionModel().select(product);
                break;
            }
        }
        
        //Получаем данные по испытаниям.
        Calendar cal = Calendar.getInstance();
        cal.setTime(comboPartNum.getSelectionModel().getSelectedItem().getDateTime());
        Year y = new Year();
        y.setReturnValue(cal.get(Calendar.YEAR));
        y.setDisplayValue(String.valueOf(y.getReturnValue()));
        
        ArrayList<Stat> stats = blogic.getProductStatsByPartYear(comboPartNum.getSelectionModel().getSelectedItem(), y);
        
        if (stats == null || stats.isEmpty()) {
            return;
        }
        
        Stat stat = stats.get(0);
        
        //Устанавливаем полученные значения в элементы управления.
        txtAvgDurability.setText(String.valueOf(Math.round(stat.getAvgDurability())));     
        txtAvgDensity.setText(String.valueOf(Math.round(stat.getAvgDryDensity())));
        txtHumidity.setText(String.valueOf(stat.getHumidity()));
        txtDurabilityMark.setText(stat.getDurabilityMark());
        txtReqDurability.setText(String.valueOf(stat.getReqDurability()));
    }
    
    
}
