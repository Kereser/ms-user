package com.emazon.ms_user;

import java.util.Map;

public class ConsUtils {
    private ConsUtils() {
    }

    public static final String TELEPHONE_NUMBER_ERROR = "Number must have 10 digits or 13 with +57.";
    public static final String PASSWORD_ERROR = "Password doesn't meet constrains.";
    public static final String EMAIL_ERROR = "Email doesn't meet constrains.";
    public static final String DATE_ERROR = "dates must be provided in the following structure: YYYY-MM-DD";

    public static final String AUX_DEPOT_DESCRIPTION = "Description A_D";

    public static final String NAME = "Test name";
    public static final String LAST_NAME = "last name";
    public static final String PASSWORD = "Password01?";
    public static final String EMAIL = "email@email.com";
    public static final String NUMBER = "+573018584935";
    public static final Long ID_NUMBER = 18584935L;
    public static final String BIRTH_DATE_STRING = "1999-09-09";

    public static final String BIRTH_DATE = "birthDate";

    public static final String FIELD_ERROR = "$.fieldErrors";
    public static final String FIELD_MESSAGE = "$.message";
    public static final String FIELD_ROLE = "$.fieldErrors.role";
    public static final String FIELD_NAME_PATH = "$.fieldErrors.name";
    public static final String FIELD_LAST_NAME_PATH = "$.fieldErrors.lastName";
    public static final String FIELD_NUMBER_PATH = "$.fieldErrors.number";
    public static final String FIELD_PASSWORD_PATH = "$.fieldErrors.password";
    public static final String FIELD_ID_NUMBER_PATH = "$.fieldErrors.idNumber";
    public static final String FIELD_BIRTH_DATE_PATH = "$.fieldErrors.birthDate";
    public static final String FIELD_EMAIL_PATH = "$.fieldErrors.email";
    public static final Integer FIELD_WITH_ERRORS_AT_USER = 7;

    public static final Long LONG_ONE = 1L;

    public static final Map<String, String> TYPE_AUX_DEPOT_PARAM = Map.of("role", "AUX_DEPOT");
    public static final Map<String, String> TYPE_NON_VALID_PARAM = Map.of("role", "NonValidType");
    public static final String TYPE_PARAM = "role";

    public static final String BASIC_USER_URL = "/users";

    /*** DB ***/
    public static final boolean FALSE = false;
    public static final int LENGTH_OF_3 = 3;
    public static final int LENGTH_OF_10 = 10;
    public static final int LENGTH_OF_13 = 13;
    public static final int LENGTH_OF_20 = 20;
}
