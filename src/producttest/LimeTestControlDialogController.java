/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package producttest;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Accordion;
import javafx.scene.control.TitledPane;
import producttest.bll.BLogic;
import producttest.model.LimeTest;

/**
 * FXML Controller class
 *
 * @author tonkushin
 */
public class LimeTestControlDialogController implements Initializable {
    @FXML
    private Accordion accordion;
    @FXML
    private TitledPane paneMain;
    @FXML
    private TitledPane paneSecond;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        accordion.setExpandedPane(paneMain);
    }    
    
    public void initData(LimeTest limeTest){
        
    }
    
}
