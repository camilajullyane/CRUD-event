package org.upe.utils;

import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;

public class DatePickerUtil {

    private DatePickerUtil() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static void restrictDatePicker(DatePicker datePicker) {
        datePicker.setDayCellFactory(dp -> new DateCell() { // Usando lambda para Callback
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                LocalDate now = LocalDate.now();
                LocalDate oneYearLater = now.plusYears(1);
                if (item.isBefore(now) || item.isAfter(oneYearLater)) {
                    setDisable(true);
                    setStyle("-fx-background-color: #D9D9D9;");
                }
            }
        });
    }

    public static void restrictDatePicker(DatePicker datePicker, LocalDate eventStartDate, LocalDate eventEndDate) {
        datePicker.setDayCellFactory(dp -> new DateCell() { // Usando lambda para Callback
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                if (item.isBefore(eventStartDate) || item.isAfter(eventEndDate)) {
                    setDisable(true);
                    setStyle("-fx-background-color: #D9D9D9;");
                }
            }
        });
    }
}
