/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package producttest;

import DAL.DataContext;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

/**
 * FXML Controller class
 *
 * @author tonkushin
 */
public class SettingsDialogController implements Initializable {

    @FXML
    private TextField txtConnectionString;
    @FXML
    private TextField txtUsername;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private Button btnTest;
    @FXML
    private Button btnOk;
    @FXML
    private Button btnCancel;
    
    Config cfg;
    @FXML
    private Label lblStatus;
    @FXML
    private TextField txtFilterPersonal;
    @FXML
    private TextField txtHeaderLine1;
    @FXML
    private TextField txtHeaderLine2;
    @FXML
    private TextField txtHeaderLine3;
    @FXML
    private TextField txtHeaderLine4;
    @FXML
    private TextField txtHeaderProductName;
    @FXML
    private TextField txtHeaderPathToLogo;
    @FXML
    private TextField txtFooterProfession;
    @FXML
    private TextField txtFooterPersonName;
    @FXML
    private TextField txtPartNumsToLoad;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lblStatus.setVisible(false);
        
        if (cfg == null) {
            cfg = new Config();
        }
        
        cfg.readPropertiesFromFile();
        
        txtConnectionString.setText(cfg.getConnectionString());
        txtUsername.setText(cfg.getUsername());
        txtPassword.setText(cfg.getPassword());
        txtFilterPersonal.setText(cfg.getFilterPersonal());
        
        txtHeaderLine1.textProperty().bindBidirectional(cfg.getHeaderLine1Property());
        txtHeaderLine2.textProperty().bindBidirectional(cfg.getHeaderLine2Property());
        txtHeaderLine3.textProperty().bindBidirectional(cfg.getHeaderLine3Property());
        txtHeaderLine4.textProperty().bindBidirectional(cfg.getHeaderLine4Property());
        
        txtHeaderPathToLogo.textProperty().bindBidirectional(cfg.getHeaderPathToLogoProperty());
        txtHeaderProductName.textProperty().bindBidirectional(cfg.getHeaderProductNameProperty());
        
        txtFooterProfession.textProperty().bindBidirectional(cfg.getFooterProfessionProperty());
        txtFooterPersonName.textProperty().bindBidirectional(cfg.getFooterPersonNameProperty());
        
        txtPartNumsToLoad.textProperty().bindBidirectional(cfg.partNumsToLoad, new NumberStringConverter());
    }

    @FXML
    private void btnTestOnAction(ActionEvent event) {
        saveSettings();
        
        DataContext dc = new DataContext();
        
        try {
            dc.connect();
            lblStatus.setText("Соединение успешно.");
            
        } catch (SQLException ex) {
            lblStatus.setText(ex.toString());
            lblStatus.setTooltip(new Tooltip(ex.toString()));
           
            Logger.getLogger(SettingsDialogController.class.getName()).log(Level.SEVERE, null, ex);
        } catch(Exception ex){
            
            Logger.getLogger(SettingsDialogController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        lblStatus.setVisible(true);
    }

    @FXML
    private void btnOkOnAction(ActionEvent event) {
        saveSettings();
        
        // get a handle to the stage
        Stage stage = (Stage) btnOk.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

    private void saveSettings() {
        if (cfg == null) {
            cfg = new Config();
        }
        
        try{
        cfg.setConnectionString(this.txtConnectionString.getText());
        cfg.setUsername(this.txtUsername.getText());
        cfg.setPassword(this.txtPassword.getText());
        cfg.setFilterPersonal(this.txtFilterPersonal.getText());
        
        cfg.writePropertiesToFile();
        } catch(Exception ex){
            lblStatus.setText(ex.toString());
            lblStatus.setTooltip(new Tooltip(ex.toString()));
            Logger.getLogger(SettingsDialogController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void btnCancelOnAction(ActionEvent event) {
        // get a handle to the stage
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        // do what you have to do
        stage.close();
    }
}
