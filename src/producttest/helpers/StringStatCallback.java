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
 * Обратный вызов для колонок с типом String.
 * @author tonkushin
 */
public class StringStatCallback implements Callback<TableColumn<Stat, String>, TableCell<Stat, String>> {

    @Override
    public TableCell<Stat, String> call(TableColumn<Stat, String> p) {
        return new TableCell<Stat, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                //Устанавливаем выравнивание по правой стороне.
                setAlignment(Pos.CENTER);

                //Округляем до целого числа.
                if (!empty) {
                    setText(item);
                } else {
                    setText(null);
                }
            }
        };
    }
}
