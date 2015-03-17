/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package producttest.helpers;

import java.io.PrintWriter;
import java.io.StringWriter;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

/**
 *
 * @author tonkushin
 */
public class ErrorDialog extends Alert {

    /**
     *
     * @param alertType
     * @param contentText
     * @param ex
     */
    public ErrorDialog(AlertType alertType, String contentText, Exception ex) {
            super(alertType);
            
            this.setTitle("Ошибка");
            this.setHeaderText(null);
            this.setContentText(contentText);

            // Добавляем иконку.
            Stage stage = (Stage) this.getDialogPane().getScene().getWindow();
            //stage.getIcons().add(new Image(this.getClass().getResource("logo.png").toString()));

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
            this.getDialogPane().setExpandableContent(expContent);        
    }
    
    public ErrorDialog(AlertType alertType){
        super(alertType);  
    }
}
