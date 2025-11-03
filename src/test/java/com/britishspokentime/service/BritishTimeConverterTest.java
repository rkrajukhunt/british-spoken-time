package com.britishspokentime.service;

import com.britishspokentime.model.Time;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive unit tests for BritishTimeConverter.
 * Tests all conversion rules and edge cases based on the assignment requirements.
 */
@SpringBootTest
class BritishTimeConverterTest {

    @Autowired
    private BritishTimeConverter converter;

    // ========== Tests from Assignment Examples ==========

    @ParameterizedTest
    @CsvSource({
            "1:00, one o'clock",
            "2:05, five past two",
            "3:10, ten past three",
            "4:15, quarter past four",
            "5:20, twenty past five",
            "6:25, twenty five past six",
            "6:32, six thirty two",
            "7:30, half past seven",
            "7:35, twenty five to eight",
            "8:40, twenty to nine",
            "9:45, quarter to ten",
            "10:50, ten to eleven",
            "11:55, five to twelve",
            "00:00, midnight",
            "12:00, noon"
    })
    void testConvert_assignmentExamples(String input, String expected) {
        Time time = converter.parseTime(input);
        String result = converter.convert(time);
        assertEquals(expected, result);
    }

    // ========== Special Cases Tests ==========

    @Test
    void testConvert_midnight() {
        Time time = new Time(0, 0);
        assertEquals("midnight", converter.convert(time));
    }

    @Test
    void testConvert_noon() {
        Time time = new Time(12, 0);
        assertEquals("noon", converter.convert(time));
    }

    // ========== O'Clock Tests ==========

    @ParameterizedTest
    @CsvSource({
            "0, 0, midnight",
            "1, 0, one o'clock",
            "2, 0, two o'clock",
            "12, 0, noon",
            "13, 0, one o'clock",
            "23, 0, eleven o'clock"
    })
    void testConvert_oClock(int hour, int minute, String expected) {
        Time time = new Time(hour, minute);
        assertEquals(expected, converter.convert(time));
    }

    // ========== Quarter Tests ==========

    @ParameterizedTest
    @CsvSource({
            "1, 15, quarter past one",
            "6, 15, quarter past six",
            "12, 15, quarter past twelve",
            "23, 15, quarter past eleven"
    })
    void testConvert_quarterPast(int hour, int minute, String expected) {
        Time time = new Time(hour, minute);
        assertEquals(expected, converter.convert(time));
    }

    @ParameterizedTest
    @CsvSource({
            "1, 45, quarter to two",
            "6, 45, quarter to seven",
            "11, 45, quarter to twelve",
            "23, 45, quarter to twelve"
    })
    void testConvert_quarterTo(int hour, int minute, String expected) {
        Time time = new Time(hour, minute);
        assertEquals(expected, converter.convert(time));
    }

    // ========== Half Past Tests ==========

    @ParameterizedTest
    @CsvSource({
            "1, 30, half past one",
            "7, 30, half past seven",
            "12, 30, half past twelve",
            "23, 30, half past eleven"
    })
    void testConvert_halfPast(int hour, int minute, String expected) {
        Time time = new Time(hour, minute);
        assertEquals(expected, converter.convert(time));
    }

    // ========== Past Minutes Tests (1-30) ==========

    @ParameterizedTest
    @CsvSource({
            "2, 1, one past two",
            "2, 5, five past two",
            "3, 10, ten past three",
            "4, 20, twenty past four",
            "5, 25, twenty five past five",
            "6, 29, twenty nine past six"
    })
    void testConvert_minutesPast(int hour, int minute, String expected) {
        Time time = new Time(hour, minute);
        assertEquals(expected, converter.convert(time));
    }

    // ========== To Minutes Tests (31-59) ==========

    @ParameterizedTest
    @CsvSource({
            "7, 35, twenty five to eight",
            "7, 40, twenty to eight",
            "7, 50, ten to eight",
            "7, 55, five to eight",
            "7, 59, one to eight"
    })
    void testConvert_minutesTo(int hour, int minute, String expected) {
        Time time = new Time(hour, minute);
        assertEquals(expected, converter.convert(time));
    }

    // ========== Special Format Tests (32-34, 37-39) ==========

    @ParameterizedTest
    @CsvSource({
            "6, 32, six thirty two",
            "6, 33, six thirty three",
            "6, 34, six thirty four",
            "6, 37, six thirty seven",
            "6, 38, six thirty eight",
            "6, 39, six thirty nine"
    })
    void testConvert_specialMinuteFormat(int hour, int minute, String expected) {
        Time time = new Time(hour, minute);
        assertEquals(expected, converter.convert(time));
    }

    // ========== Edge Cases for Different Hours ==========

    @ParameterizedTest
    @CsvSource({
            "0, 30, half past twelve",
            "0, 45, quarter to one",
            "13, 30, half past one",
            "23, 30, half past eleven",
            "23, 45, quarter to twelve"
    })
    void testConvert_edgeCasesForDifferentHours(int hour, int minute, String expected) {
        Time time = new Time(hour, minute);
        assertEquals(expected, converter.convert(time));
    }

    // ========== Parse Time Tests ==========

    @ParameterizedTest
    @CsvSource({
            "00:00, 0, 0",
            "01:05, 1, 5",
            "12:30, 12, 30",
            "23:59, 23, 59"
    })
    void testParseTime_validFormats(String input, int expectedHour, int expectedMinute) {
        Time time = converter.parseTime(input);
        assertEquals(expectedHour, time.getHour());
        assertEquals(expectedMinute, time.getMinute());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"  ", "\t", "\n"})
    void testParseTime_nullOrEmpty(String input) {
        assertThrows(IllegalArgumentException.class, () -> converter.parseTime(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"1234", "12", "ab:cd", "12:60", "24:00", "-1:00", "12:-1"})
    void testParseTime_invalidFormats(String input) {
        assertThrows(IllegalArgumentException.class, () -> converter.parseTime(input));
    }

    // ========== Invalid Time Tests ==========

    @Test
    void testConvert_nullTime() {
        assertThrows(IllegalArgumentException.class, () -> converter.convert(null));
    }

    @Test
    void testConvert_invalidTime() {
        Time invalidTime = new Time(25, 0);
        assertThrows(IllegalArgumentException.class, () -> converter.convert(invalidTime));
    }

    // ========== Comprehensive Coverage Tests ==========

    @ParameterizedTest
    @CsvSource({
            "0, 1, one past twelve",
            "0, 15, quarter past twelve",
            "0, 31, twenty nine to one",
            "11, 59, one to twelve",
            "12, 1, one past twelve",
            "12, 59, one to one",
            "13, 1, one past one",
            "23, 1, one past eleven",
            "23, 59, one to twelve"
    })
    void testConvert_comprehensiveCoverage(int hour, int minute, String expected) {
        Time time = new Time(hour, minute);
        assertEquals(expected, converter.convert(time));
    }

    // ========== Additional Edge Cases ==========

    @ParameterizedTest
    @CsvSource({
            "5, 31, twenty nine to six",
            "5, 35, twenty five to six",
            "5, 36, twenty four to six",
            "8, 40, twenty to nine",
            "8, 41, nineteen to nine",
            "8, 44, sixteen to nine"
    })
    void testConvert_additionalToMinutes(int hour, int minute, String expected) {
        Time time = new Time(hour, minute);
        assertEquals(expected, converter.convert(time));
    }
}
