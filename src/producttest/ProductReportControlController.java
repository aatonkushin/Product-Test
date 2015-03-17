/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package producttest;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author tonkushin
 */
public class ProductReportControlController implements Initializable {
    @FXML
    private VBox mainVBox;
    @FXML
    private ComboBox<?> comboReportType;
    @FXML
    private DatePicker dateFrom;
    @FXML
    private DatePicker dateTo;
    @FXML
    private TableView<?> tblProductReport;

    @FXML
    private TableColumn<?, ?> colPartNum;
    @FXML
    private TableColumn<?, ?> colDate;
    @FXML
    private TableColumn<?, ?> colProductName;
    @FXML
    private TableColumn<?, ?> colVolume;
    @FXML
    private TableColumn<?, ?> colAutoclaveNum;
    @FXML
    private TableColumn<?, ?> colDefName;
    @FXML
    private TableColumn<?, ?> colDefRate;
    @FXML
    private TableColumn<?, ?> colHumidity;
    @FXML
    private TableColumn<?, ?> colAvgDensity;
    @FXML
    private TableColumn<?, ?> colDensityMark;
    @FXML
    private TableColumn<?, ?> colAvgDurability;
    @FXML
    private TableColumn<?, ?> colDurabilityMark;
    @FXML
    private TableColumn<?, ?> colFrostresist;
    @FXML
    private TableColumn<?, ?> colActivity;
    @FXML
    private TableColumn<?, ?> colHeatconduction;
    @FXML
    private TableColumn<?, ?> colSteamfactor;
    @FXML
    private TableColumn<?, ?> colShrinkage;
    @FXML
    private Label lblItemsCount;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void comboReportTypeOnAction(ActionEvent event) {
    }

    @FXML
    private void dateFromOnChanged(ActionEvent event) {
    }

    @FXML
    private void dateToOnChanged(ActionEvent event) {
    }


    
}
