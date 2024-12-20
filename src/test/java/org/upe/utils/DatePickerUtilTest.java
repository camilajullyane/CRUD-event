package org.upe.utils;

import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class DatePickerUtilTest extends ApplicationTest {

    private DatePicker datePicker;

    @BeforeEach
    void setUp() {
        datePicker = new DatePicker();
        DatePickerUtil.restrictDatePicker(datePicker);
    }

    @Test
    void testDatePickerRestriction() {
        LocalDate now = LocalDate.now();
        LocalDate oneYearLater = now.plusYears(1);

        DateCell cellBefore = datePicker.getDayCellFactory().call(datePicker);
        cellBefore.updateItem(now.minusDays(1), false);
        assertTrue(cellBefore.isDisable());

        DateCell cellAfter = datePicker.getDayCellFactory().call(datePicker);
        cellAfter.updateItem(oneYearLater.plusDays(1), false);
        assertTrue(cellAfter.isDisable());

        DateCell cellWithin = datePicker.getDayCellFactory().call(datePicker);
        cellWithin.updateItem(now.plusMonths(6), false);
        assertFalse(cellWithin.isDisable());
    }

    @Test
    void testDatePickerRestrictionWithEventDates() {
        LocalDate eventStartDate = LocalDate.now().plusDays(10);
        LocalDate eventEndDate = LocalDate.now().plusDays(20);
        DatePickerUtil.restrictDatePicker(datePicker, eventStartDate, eventEndDate);

        DateCell cellBefore = datePicker.getDayCellFactory().call(datePicker);
        cellBefore.updateItem(eventStartDate.minusDays(1), false);
        assertTrue(cellBefore.isDisable());

        DateCell cellAfter = datePicker.getDayCellFactory().call(datePicker);
        cellAfter.updateItem(eventEndDate.plusDays(1), false);
        assertTrue(cellAfter.isDisable());

        DateCell cellWithin = datePicker.getDayCellFactory().call(datePicker);
        cellWithin.updateItem(eventStartDate.plusDays(5), false);
        assertFalse(cellWithin.isDisable());
    }

    @Test
    void testPrivateConstructor() throws NoSuchMethodException {
        Constructor<DatePickerUtil> constructor = DatePickerUtil.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        assertThrows(InvocationTargetException.class, constructor::newInstance);
    }
}