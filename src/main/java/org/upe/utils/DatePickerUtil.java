package org.upe.utils;

import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.util.Callback;

import java.time.LocalDate;

public class DatePickerUtil {

    private DatePickerUtil() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static void restrictDatePicker(DatePicker datePicker) {
        datePicker.setDayCellFactory(new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {
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
                };
            }
        });

    }
}