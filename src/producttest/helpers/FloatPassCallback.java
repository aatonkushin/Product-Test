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
import producttest.model.ProductPassport;

/**
 * Обратный вызов для колонок с типом Float и необходимостью округления до целого.
 * Не переводим на Int, так как не уверены, что следует всегда округлять.
 * @author tonkushin
 */
public class FloatPassCallback implements Callback<TableColumn<ProductPassport, Float>, TableCell<ProductPassport, Float>> {

    @Override
    public TableCell<ProductPassport, Float> call(TableColumn<ProductPassport, Float> p) {
        return new TableCell<ProductPassport, Float>() {
            @Override
            protected void updateItem(Float item, boolean empty) {
                super.updateItem(item, empty);

                //Устанавливаем выравнивание по правой стороне.
                setAlignment(Pos.CENTER_RIGHT);

                //Округляем до целого числа.
                if (!empty) {
                    setText(String.valueOf(Math.round(item)));
                } else {
                    setText(null);
                }
            }
        };
    }
}
