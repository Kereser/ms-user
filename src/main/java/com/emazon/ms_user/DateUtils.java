package com.emazon.ms_user;

import java.time.LocalDate;

public class DateUtils {
    private DateUtils() {}

    public static Boolean isOlderEighteen(LocalDate birthDate) {
        LocalDate eighteenYearsAgo = LocalDate.now().minusYears(18);
        return birthDate.isBefore(eighteenYearsAgo) || birthDate.isEqual(eighteenYearsAgo);
    }
}
