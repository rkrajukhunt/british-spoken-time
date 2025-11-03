package com.britishspokentime.service.strategy;

import com.britishspokentime.constants.TimeConstants;
import com.britishspokentime.model.Time;
import com.britishspokentime.service.util.NumberToWordConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Strategy for formatting quarter to times (X:45).
 */
@Component
@RequiredArgsConstructor
public class QuarterToStrategy implements TimeFormatStrategy {

    private final NumberToWordConverter converter;

    @Override
    public boolean canHandle(Time time) {
        return time.getMinute() == TimeConstants.THREE_QUARTER_MINUTES;
    }

    @Override
    public String format(Time time) {
        return "quarter to " + converter.getHourWord(time.getNextHour());
    }

    @Override
    public int getPriority() {
        return 6;
    }
}
