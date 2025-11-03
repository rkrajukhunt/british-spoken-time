package com.britishspokentime.service.factory;

import com.britishspokentime.model.Time;
import com.britishspokentime.service.strategy.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for TimeFormatStrategyFactory.
 * Verifies that the factory selects the correct strategy for different time inputs.
 */
@SpringBootTest
class TimeFormatStrategyFactoryTest {

    @Autowired
    private TimeFormatStrategyFactory factory;

    @Test
    void testGetStrategy_midnight() {
        Time time = new Time(0, 0);
        TimeFormatStrategy strategy = factory.getStrategy(time);
        assertInstanceOf(MidnightStrategy.class, strategy);
    }

    @Test
    void testGetStrategy_noon() {
        Time time = new Time(12, 0);
        TimeFormatStrategy strategy = factory.getStrategy(time);
        assertInstanceOf(NoonStrategy.class, strategy);
    }

    @Test
    void testGetStrategy_oClock() {
        Time time = new Time(5, 0);
        TimeFormatStrategy strategy = factory.getStrategy(time);
        assertInstanceOf(OClockStrategy.class, strategy);
    }

    @Test
    void testGetStrategy_quarterPast() {
        Time time = new Time(3, 15);
        TimeFormatStrategy strategy = factory.getStrategy(time);
        assertInstanceOf(QuarterPastStrategy.class, strategy);
    }

    @Test
    void testGetStrategy_halfPast() {
        Time time = new Time(7, 30);
        TimeFormatStrategy strategy = factory.getStrategy(time);
        assertInstanceOf(HalfPastStrategy.class, strategy);
    }

    @Test
    void testGetStrategy_quarterTo() {
        Time time = new Time(9, 45);
        TimeFormatStrategy strategy = factory.getStrategy(time);
        assertInstanceOf(QuarterToStrategy.class, strategy);
    }

    @Test
    void testGetStrategy_past() {
        Time time = new Time(2, 10);
        TimeFormatStrategy strategy = factory.getStrategy(time);
        assertInstanceOf(MinutesPastStrategy.class, strategy);
    }

    @Test
    void testGetStrategy_specialMinutes() {
        Time time = new Time(6, 32);
        TimeFormatStrategy strategy = factory.getStrategy(time);
        assertInstanceOf(SpecialMinutesStrategy.class, strategy);
    }

    @Test
    void testGetStrategy_to() {
        Time time = new Time(8, 40);
        TimeFormatStrategy strategy = factory.getStrategy(time);
        assertInstanceOf(MinutesToStrategy.class, strategy);
    }

    @Test
    void testGetStrategy_priorityOrder() {
        // Test that midnight has higher priority than o'clock
        Time midnight = new Time(0, 0);
        TimeFormatStrategy strategy = factory.getStrategy(midnight);
        assertInstanceOf(MidnightStrategy.class, strategy);
        assertFalse(strategy instanceof OClockStrategy);
    }

    @Test
    void testGetStrategy_invalidTime() {
        Time invalidTime = new Time(25, 70);
        // Should still return a strategy, even though the time is invalid
        // The actual validation happens in the converter
        assertDoesNotThrow(() -> factory.getStrategy(invalidTime));
    }
}
