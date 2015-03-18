/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package producttest;

import java.io.IOException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Accordion;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author tonkushin
 */
public class LimeTestControl extends VBox{
    
    @FXML
    private VBox mainVBox;
    
    @FXML
    private TitledPane tabInterimDefs;
    
    @FXML
    private TableView tblInterimDefs;
    
    @FXML
    private Accordion accordion;
    
    public LimeTestControl() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                "LimeTestControl.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        
        tabInterimDefs.setExpanded(true);

        //Отслеживаем высоту элемента управления для изменения высоты таблицы.
        mainVBox.heightProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                System.out.println("LimeTestControl: "+ oldValue + "|"+ newValue);
                if ((double)oldValue == 0.0)
                    return;
                
                tblInterimDefs.setPrefHeight(tblInterimDefs.getPrefHeight() + ((Double)newValue - (Double)oldValue));
                
                tabInterimDefs.setPrefHeight(tabInterimDefs.getPrefHeight() + ((Double)newValue - (Double)oldValue));
                
                accordion.setPrefHeight(accordion.getPrefHeight() + ((Double)newValue - (Double)oldValue));
            }
        });
    }
    
    //<editor-fold defaultstate="collapsed" desc="Обработчики событий">
    
    @FXML
    private void btnAddOnAction(ActionEvent e){
        
    }
    
    @FXML
    private void comboReportTypeOnAction(ActionEvent e) {
        
    }
    
    @FXML
    private void dateFromOnAction(ActionEvent e){
        
    }
    
    @FXML
    private void dateToOnAction(ActionEvent e){
        
    }
//</editor-fold>
}
