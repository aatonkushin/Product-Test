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
public class IntegerProductReportCallback implements Callback<TableColumn<ProductReportRecord, Integer>, TableCell<ProductReportRecord, Integer>> {

    @Override
    public TableCell<ProductReportRecord, Integer> call(TableColumn<ProductReportRecord, Integer> param) {
        return new TableCell<ProductReportRecord, Integer>() {
            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);

                //Устанавливаем выравнивание по правой стороне.
                setAlignment(Pos.CENTER_RIGHT);

                if (!empty) {
                    setText(String.valueOf(item));
                } else {
                    setText(null);
                }
            }
        };
    }
}
