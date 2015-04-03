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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import producttest.model.LimeTest;

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
    
    @FXML
    private Button btnAdd;
    
    @FXML
    private Button btnChange;
    
    @FXML
    private Button btnRemove;
    
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
        
        accordion.setExpandedPane(tabInterimDefs);
        tabInterimDefs.setExpanded(true);
        tblInterimDefs.setPlaceholder(new Text("Испытания извести"));

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
        FXMLLoader root;
        try {
            root = new FXMLLoader(getClass().getResource("LimeTestControlDialog.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Ввод данных об испытании извести");
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            Image img = new Image(getClass().getResourceAsStream("logo.png"));
            stage.getIcons().add(img);
            stage.setScene(new Scene((Pane) root.load()));

            LimeTestControlDialogController controller = root.<LimeTestControlDialogController>getController();
            controller.initData(new LimeTest());

            stage.showAndWait();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
     @FXML
    private void btnChangeOnAction(ActionEvent e){
        
    }
    
     @FXML
    private void btnRemoveOnAction(ActionEvent e){
        
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
