/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package producttest.helpers;

import java.util.HashMap;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

/**
 * Класс, отвечающий за проверку элементов формы.
 *
 * @author tonkushin
 */
public class Validator {

    //Список контролируемых элементов.
    private final HashMap<Control, Boolean> controls = new HashMap<>();

    //Флаг, указывающий на наличие ошибок.
    BooleanProperty hasErrors;

    public final Boolean getHasErrors() {
        return hasErrors.get();
    }

    public final void setHasErrors(Boolean hasErrors) {
        this.hasErrors.set(hasErrors);
    }

    /**
     * Флаг, указывающий на наличие ошибок в отслеживаемых элементах.
     * @return 
     */
    public BooleanProperty hasErrorProperty() {
        return this.hasErrors;
    }

    /**
     * Добавляет текстовое поле в список контролируеммых элементов. 
     * Пока контроль сделан только для типа Float.
     * @param txt - контролируемое поле.
     * @param lbl - подпись контролируемого поля (чтобы изменять её цвет).
     */
    public void add(final TextField txt, final Label lbl) {

        if (!controls.containsKey(txt)) {
            controls.put(txt, false);
        }

        txt.textProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                try {
                    Float.valueOf(newValue);
                    txt.setStyle("-fx-text-inner-color: black;");
                    controls.replace(txt, Boolean.FALSE);
                    lbl.setTextFill(Color.BLACK);
                } catch (Exception e) {
                    txt.setStyle("-fx-text-inner-color: red;");
                    controls.replace(txt, Boolean.TRUE);
                    lbl.setTextFill(Color.RED);
                }

                if (controls.containsValue(Boolean.TRUE)) {
                    setHasErrors(Boolean.TRUE);
                } else {
                    setHasErrors(Boolean.FALSE);
                }
            }
        });
    }

    /**
     * Добавляет поле выбора даты в список контролируеммых элементов.
     * Дата не должна быть null.
     * @param datePicker - поле выбора даты.
     * @param lbl - подпись контролируемого поля (чтобы изменять её цвет).
     */
    public void add(final DatePicker datePicker, final Label lbl) {
        if (!controls.containsKey(datePicker)) {
            controls.put(datePicker, false);
        }

        datePicker.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                if (datePicker.getValue() != null) {
                    controls.replace(datePicker, Boolean.FALSE);
                    lbl.setTextFill(Color.BLACK);
                } else {
                    controls.replace(datePicker, Boolean.TRUE);
                    lbl.setTextFill(Color.RED);
                }

                checkErrors();
            }
        });
    }

    /**
     * Добавляет ComboBox в список контролируеммых элементов.
     * Выбранный элемент не должен быть null или нулевым по списку.
     * @param combo - выпадающий список.
     * @param lbl - подпись контролируемого поля (чтобы изменять её цвет).
     */
    public void add(final ComboBox combo, final Label lbl) {
        if (!controls.containsKey(combo)) {
            controls.put(combo, Boolean.FALSE);
        }

        combo.setOnAction(new EventHandler() {

            @Override
            public void handle(Event event) {
                if(combo.getSelectionModel().getSelectedItem() != null && combo.getSelectionModel().getSelectedIndex() != 0){
                    controls.replace(combo, Boolean.FALSE);
                    lbl.setTextFill(Color.BLACK);
                } else{
                    controls.replace(combo, Boolean.TRUE);
                    lbl.setTextFill(Color.RED);
                }
                
                checkErrors();
            }
        });
    }

    /**
     * Проверка ошибок в отслежываемых элементах.
     * @return true - если хотябы в одном поле есть ошибка, иначе - false.
     */
    public boolean checkErrors() {
        if (controls.containsValue(Boolean.TRUE)) {
            setHasErrors(Boolean.TRUE);
            return true;
        } else {
            setHasErrors(Boolean.FALSE);
            return false;
        }
    }

    /**
     * Конструктор класса по-умолчанию.
     */
    public Validator() {
        hasErrors = new SimpleBooleanProperty(false);
    }
}
