/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package producttest;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import producttest.bll.BLogic;
import producttest.helpers.Validator;
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

    Boolean firstChange = false;                //Флаг первого изменения в комбобоксе (нужен, что бы не перезаписывать данные полей при первой загрузке в режиме изменения)
    @FXML
    private Label lblAvgDurability;
    
    ArrayList<Boolean> errors = new ArrayList<>();
    @FXML
    private Label lblDate;
    @FXML
    private Label lblPartNum;
    @FXML
    private Label lblProduct;
    @FXML
    private Label lblAvgDensity;
    @FXML
    private Label lblHumidity;
    @FXML
    private Label lblDurabilityMark;
    @FXML
    private Label lblReqDurability;
    @FXML
    private Label lblSteamFactor;
    @FXML
    private Label lblFrostresist;
    @FXML
    private Label lblHeatConduction;
    @FXML
    private Label lblShrinkage;
    @FXML
    private Label lblActivity;
    
    public void initData(ProductPassport productPassport, BLogic blogic) {
        
        this.productPassport = productPassport;
        this.blogic = blogic;

        comboPartNum.setItems(blogic.getPartNumbers());
        comboProduct.setItems(blogic.getProducts());
        
        Validator validator = new Validator();
        validator.add(txtAvgDurability, lblAvgDurability);
        validator.add(txtAvgDensity, lblAvgDensity);
        validator.add(txtHumidity, lblHumidity);
        validator.add(txtReqDurability, lblReqDurability);
        validator.add(txtSteamFactor, lblSteamFactor);
        validator.add(txtHeatConduction, lblHeatConduction);
        validator.add(txtShrinkage, lblShrinkage);

        //validator.add(datePicker, lblDate);
        datePicker.setConverter(new StringConverter<LocalDate>() {
            String pattern = "dd.MM.YYYY";
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);
            
            {
                datePicker.setPromptText(pattern.toLowerCase());
            }
            
            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }
            
            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        });

        //validator.add(comboPartNum, lblPartNum);
        validator.add(comboProduct, lblProduct);
        
        btnOk.disableProperty().bindBidirectional(validator.hasErrorProperty());
        
        if (productPassport.getId() != -1) {
            firstChange = true;
            comboProduct.getSelectionModel().select(productPassport.getProduct());
            datePicker.setValue(productPassport.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            txtAvgDurability.setText(productPassport.getAvgDurability().toString());
            txtAvgDensity.setText(productPassport.getAvgDensity().toString());
            txtHumidity.setText(productPassport.getHumidity().toString());
            txtDurabilityMark.setText(productPassport.getDurabilityMark());
            txtReqDurability.setText(productPassport.getReqDurability().toString());
            txtSteamFactor.setText(productPassport.getSteamFactor().toString());
            txtFrostresist.setText(productPassport.getFrostResist());
            txtHeatConduction.setText(productPassport.getHeatConduction().toString());
            txtShrinkage.setText(productPassport.getShrinkage().toString());
            txtActivity.setText(productPassport.getActivity());
            txtNotes.setText(productPassport.getNotes());
            
            for (Part p : comboPartNum.getItems()) {
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
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnOk.setDefaultButton(true);
        btnCancel.setCancelButton(true);
    }
    
    @FXML
    private void btnOkOnAction(ActionEvent event) {
        if (comboPartNum.getSelectionModel().getSelectedIndex() == 0 || comboPartNum.getSelectionModel().getSelectedItem() == null) {
            lblPartNum.setTextFill(Color.RED);
            return;
        } else {
            lblPartNum.setTextFill(Color.BLACK);
        }

        //Устанавливаем все поля в паспорте ГП.
        productPassport.setPartNum(comboPartNum.getSelectionModel().getSelectedItem().getPartNum());
        Instant instant = datePicker.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
        productPassport.setDate(Date.from(instant));
        productPassport.setProduct(comboProduct.getSelectionModel().getSelectedItem());
        
        productPassport.setAvgDurability(parseToFloat(txtAvgDurability.getText()));
        productPassport.setAvgDensity(parseToFloat(txtAvgDensity.getText()));
        productPassport.setHumidity(parseToFloat(txtHumidity.getText()));
        productPassport.setDurabilityMark(txtDurabilityMark.getText());
        productPassport.setReqDurability(parseToFloat(txtReqDurability.getText()));
        
        productPassport.setSteamFactor(parseToFloat(txtSteamFactor.getText()));
        productPassport.setFrostResist(txtFrostresist.getText());
        productPassport.setHeatConduction(parseToFloat(txtHeatConduction.getText()));
        productPassport.setShrinkage(parseToFloat(txtShrinkage.getText()));
        productPassport.setActivity(txtActivity.getText());
        
        productPassport.setNotes(txtNotes.getText());

        //Сохраняем данные в БД.
        try {
            blogic.createOrUpdateProductPassport(productPassport);

            //Закрываем окно.
            Stage stage = (Stage) btnCancel.getScene().getWindow();
            stage.close();
        } catch (SQLException ex) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText(null);
            alert.setContentText("Ошибка при сохранении паспорта готовой продукции.");

            // Добавляем иконку.
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image(this.getClass().getResource("logo.png").toString()));

            // Create expandable Exception.
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            ex.printStackTrace(pw);
            String exceptionText = sw.toString();
            
            Label label = new Label("Подробнее:");
            
            TextArea textArea = new TextArea(exceptionText);
            textArea.setEditable(false);
            textArea.setWrapText(true);
            
            textArea.setMaxWidth(Double.MAX_VALUE);
            textArea.setMaxHeight(Double.MAX_VALUE);
            GridPane.setVgrow(textArea, Priority.ALWAYS);
            GridPane.setHgrow(textArea, Priority.ALWAYS);
            
            GridPane expContent = new GridPane();
            expContent.setMaxWidth(Double.MAX_VALUE);
            expContent.add(label, 0, 0);
            expContent.add(textArea, 0, 1);

            // Set expandable Exception into the dialog pane.
            alert.getDialogPane().setExpandableContent(expContent);
            
            alert.showAndWait();
        }
        
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
        
        if (comboPartNum.getSelectionModel().getSelectedIndex() == 0) {
            return;
        }
        
        if (firstChange) {
            firstChange = false;
            return;
        }

        //Устанавливаем тип продукции.
        for (Product product : comboProduct.getItems()) {
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
        
        productPassport.setAvgDurability(stat.getAvgDurability());
        productPassport.setAvgDensity(stat.getAvgDryDensity());
        productPassport.setHumidity(stat.getHumidity());
        productPassport.setDurabilityMark(stat.getDurabilityMark());
        productPassport.setReqDurability(stat.getReqDurability());
        productPassport.setProduct(comboProduct.getSelectionModel().getSelectedItem());
        
        productPassport = blogic.getProductPassportParameters(productPassport);
        
        txtSteamFactor.setText(String.valueOf(productPassport.getSteamFactor()));
        txtFrostresist.setText(productPassport.getFrostResist());
        txtHeatConduction.setText(String.valueOf(productPassport.getHeatConduction()));
        txtShrinkage.setText(String.valueOf(productPassport.getShrinkage()));
        txtActivity.setText(productPassport.getActivity());
    }
    
    /**
     * Преобразует строку в тип Float. При этом если строка пустая, то сичтаем её как 0.0.
     * @param text
     * @return 
     */
    private Float parseToFloat(String text){
        if (text.isEmpty()) {
            return 0.0f;
        }
        
        text = text.replace(",", ".");
        
        return Float.valueOf(text);
    }
}
