package com.britishspokentime.service.util;

import com.britishspokentime.constants.TimeConstants;
import org.springframework.stereotype.Component;

/**
 * Utility class for converting numbers to their word representations.
 * Follows Single Responsibility Principle.
 */
@Component
public class NumberToWordConverter {

    private static final String[] HOURS = {
            "twelve", "one", "two", "three", "four", "five",
            "six", "seven", "eight", "nine", "ten", "eleven",
            "twelve", "one", "two", "three", "four", "five",
            "six", "seven", "eight", "nine", "ten", "eleven"
    };

    private static final String[] MINUTES = {
            "", "one", "two", "three", "four", "five",
            "six", "seven", "eight", "nine", "ten",
            "eleven", "twelve", "thirteen", "fourteen", "fifteen",
            "sixteen", "seventeen", "eighteen", "nineteen", "twenty",
            "twenty one", "twenty two", "twenty three", "twenty four", "twenty five",
            "twenty six", "twenty seven", "twenty eight", "twenty nine"
    };

    public String getHourWord(int hour) {
        if (hour < TimeConstants.MIN_TWELVE_HOUR || hour > TimeConstants.MAX_TWELVE_HOUR) {
            throw new IllegalArgumentException(
                    String.format("Hour must be between %d and %d: %d",
                            TimeConstants.MIN_TWELVE_HOUR, TimeConstants.MAX_TWELVE_HOUR, hour));
        }
        return HOURS[hour];
    }

    public String getMinuteWord(int minute) {
        if (minute <= TimeConstants.MIN_MINUTE || minute >= TimeConstants.PAST_THRESHOLD) {
            throw new IllegalArgumentException(
                    String.format("Minute must be between %d and %d: %d",
                            TimeConstants.MIN_MINUTE + 1, TimeConstants.PAST_THRESHOLD - 1, minute));
        }
        return MINUTES[minute];
    }

    public String getMinuteAsNumber(int minute) {
        if (minute < TimeConstants.THIRTY || minute >= TimeConstants.MINUTES_IN_HOUR) {
            throw new IllegalArgumentException("Invalid minute: " + minute);
        }

        if (minute < TimeConstants.FORTY) {
            return "thirty" + (minute == TimeConstants.THIRTY ? "" :
                    " " + MINUTES[minute - TimeConstants.THIRTY]);
        } else if (minute < TimeConstants.FIFTY) {
            return "forty" + (minute == TimeConstants.FORTY ? "" :
                    " " + MINUTES[minute - TimeConstants.FORTY]);
        } else {
            return "fifty" + (minute == TimeConstants.FIFTY ? "" :
                    " " + MINUTES[minute - TimeConstants.FIFTY]);
        }
    }
}
