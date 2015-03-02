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
import producttest.model.Stat;

/**
 Обратный вызов для колонок с типом Float без округления.
 *Не переводим на Int, так как не уверены, что следует всегда округлять.
 *@author tonkushin
 */
public class FloatStatWORoundCallback implements Callback<TableColumn<Stat, Float>, TableCell<Stat, Float>> {

    @Override
    public TableCell<Stat, Float> call(TableColumn<Stat, Float> p) {
        return new TableCell<Stat, Float>() {
            @Override
            protected void updateItem(Float item, boolean empty) {
                super.updateItem(item, empty);

                //Устанавливаем выравнивание по правой стороне.
                setAlignment(Pos.CENTER_RIGHT);

                //Округляем до целого числа.
                if (!empty) {
                    setText(item.toString());
                } else {
                    setText(null);
                }
            }
        };
    }
}
