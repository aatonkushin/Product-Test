/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package producttest.helpers;

import javafx.geometry.Pos;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;
import producttest.model.ProductReportRecord;


/**
 *
 * @author tonkushin
 */
public class FloatProductReportCallback implements Callback<TableColumn<ProductReportRecord, Float>, TableCell<ProductReportRecord, Float>> {

    private final Boolean round;
    
    public FloatProductReportCallback(Boolean round){
        this.round = round;
    }
    
    @Override
    public TableCell<ProductReportRecord, Float> call(TableColumn<ProductReportRecord, Float> param) {
        return new TableCell<ProductReportRecord, Float>() {
            @Override
            protected void updateItem(Float item, boolean empty) {
                super.updateItem(item, empty);

                //Устанавливаем выравнивание по правой стороне.
                setAlignment(Pos.CENTER_RIGHT);

                
                if (!empty) {
                    if (round) {
                        //Округляем до целого числа.
                        setText(String.valueOf(Math.round(item)));
                    } else{
                        //без округления.
                        setText(String.valueOf(item));
                    }
                    
                } else {
                    setText(null);
                }
            }
        };
    }
    
}
