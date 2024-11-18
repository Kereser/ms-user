package com.emazon.ms_user;

public class ConsUtils {
    private ConsUtils() {
    }

    public static PathBuilder builderPath() {
        return new PathBuilder();
    }

    public static final String COMMA_DELIMITER = ",";
    public static final String SPACE = " ";
    public static final String SEMI_COLON_DELIMITER = ";";
    public static final String COLON_DELIMITER = ":";
    public static final Long PLUS_30_MINUTES = 1800000L;
    public static final Long PLUS_1_DAY = 86400000L;

    public static final String TELEPHONE_NUMBER_ERROR = "Number must have 10 digits or 13 with +57.";
    public static final String PASSWORD_ERROR = "Password doesn't meet constrains.";
    public static final String EMAIL_ERROR = "Email doesn't meet constrains.";
    public static final String DATE_ERROR = "dates must be provided in the following structure: YYYY-MM-DD";

    public static final String AUX_DEPOT_DESCRIPTION = "Description A_D";

    public static final String NAME = "Test name";
    public static final String LAST_NAME = "last name";
    public static final String PASSWORD = "Password01?";
    public static final String EMAIL = "email@email.com";
    public static final String EMAIL_STR = "email";
    public static final String NUMBER = "+573018584935";
    public static final Long ID_NUMBER = 18584935L;
    public static final String BIRTH_DATE_STRING = "1999-09-09";

    public static final String USER = "testuser";
    public static final Long USER_ID_1 = 1L;

    public static final String ROLE = "ROLE_";
    public static final String NOOP_PASSWORD = "{noop}";
    public static final String AUTHORIZATION = "Authorization";
    public static final String LOGIN_ENDPOINT = "/users/login";
    public static final String BEARER = "Bearer ";

    public static final String BIRTH_DATE = "birthDate";

    public static final String FIELD_TOKEN = "$.token";
    public static final String FIELD_ERROR = "$.fieldErrors";
    public static final String FIELD_MESSAGE = "$.message";
    public static final String FIELD_NAME_PATH = "$.fieldErrors.name";
    public static final String FIELD_LAST_NAME_PATH = "$.fieldErrors.lastName";
    public static final String FIELD_NUMBER_PATH = "$.fieldErrors.number";
    public static final String FIELD_PASSWORD_PATH = "$.fieldErrors.password";
    public static final String FIELD_ID_NUMBER_PATH = "$.fieldErrors.idNumber";
    public static final String FIELD_BIRTH_DATE_PATH = "$.fieldErrors.birthDate";
    public static final String FIELD_EMAIL_PATH = "$.fieldErrors.email";
    public static final Integer FIELD_WITH_ERRORS_AT_USER = 7;

    /*** Methods ***/
    public static final String GET = "GET";
    public static final String POST = "POST";
    public static final String PUT = "PUT";
    public static final String DELETE = "DELETE";
    public static final String REQUESTED_WITH = "X-Requested-With";
    public static final String CONTENT_TYPE = "Content-Type";
    public static final String FRONT_URL = "http://localhost:4200";
    public static final String MATCH_ALL_URL = "/**";

    public static final String NOT_TEST = "!test";

    public static final Long LONG_ONE = 1L;
    public static final Integer INTEGER_1 = 1;
    public static final Integer INTEGER_10_000 = 10_000;

    public static final String SWAGGER_URL = "/swagger-ui/**";
    public static final String SWAGGER_DOCS_URL = "/v3/api-docs/**";

    public static final String BASIC_USER_URL = "/users";
    public static final String AUX_DEPOT_URL = "/aux-depot";
    public static final String CLIENT_URL = "/client";
    public static final String LOGIN_URL = "/login";
    public static final String CLIENT = "CLIENT";
    public static final String ADMIN = "ADMIN";

    /*** DB ***/
    public static final boolean FALSE = false;
    public static final int LENGTH_OF_3 = 3;
    public static final int LENGTH_OF_10 = 10;
    public static final int LENGTH_OF_13 = 13;
    public static final int LENGTH_OF_20 = 20;

    /*** Regex ***/
    public static final String PHONE_NUMBER_REGEX = "^(\\+57\\d{10}|\\d{10})$";
    public static final String DATE_REGEX = "^(\\d{4}-\\d{2}-\\d{2})$";
    public static final String PASSWORD_REGEX = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*\\W).{7,}$";

    public static class PathBuilder {
        private String finalPath = BASIC_USER_URL;

        public PathBuilder withAuxDepot() {
            this.finalPath += ConsUtils.AUX_DEPOT_URL;
            return this;
        }

        public PathBuilder withLogin() {
            this.finalPath += ConsUtils.LOGIN_URL;
            return this;
        }

        public PathBuilder withClient() {
            this.finalPath += CLIENT_URL;
            return this;
        }

        public String build() {
            return finalPath;
        }
    }
}
