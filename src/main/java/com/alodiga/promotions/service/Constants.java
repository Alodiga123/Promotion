package com.alodiga.promotions.service;

import java.time.LocalDate;

/**
 * Application constants.
 */
public final class Constants {

    // Regex for acceptable logins
    public static final String LOGIN_REGEX = "^(?>[a-zA-Z0-9!$&*+=?^_`{|}~.-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*)|(?>[_.@A-Za-z0-9-]+)$";

    public static final String SYSTEM_ACCOUNT = "system";
    public static final String DEFAULT_LANGUAGE = "es";
    public static final String ANONYMOUS_USER = "anonymoususer";
    
	//PRODUCTOS DEL CORE
    public static final int AHORRO_ZACCO_EN_DOLARES = 8;
    public static final int AHORRO_ZACCO_EN_BOLIVARES = 7;
    public static final String NOMINAL_ANNUAL_INTERESTRATE = "3";
    public static final int INTEREST_COMPOUNDING_PERIOD_TYPE = 4;
    public static final int INTEREST_POSTING_PERIOD_TYPE = 4; 
    public static final int INTEREST_CALCULATION_TYPE = 2;
    public static final int INTEREST_CALCULATION_DAYS_IN_YEAR_TYPE = 360;
    public static final String LOCALE = "es";
    public static final String DATE_FORMAT = "dd MMMM yyyy";
    public static final String MONTH_DAY_FORMAT = "dd MMM";
    public static final String DOCUMENT_TYPE_FOTO_PERFIL = "4";
    public static final String DOCUMENT_TYPE_LOGO_COMERCIO = "19";
    public static final String SPRING_PATH = "/img4/";    
    public static final String SPRING_PATH_ALL = SPRING_PATH + "**";
    public static final String FORTMAT_COMPLIANCE_COMMERCE_VALID = "PDF";
    public static final String FORTMAT_COMPLIANCE_COMMERCE_LOGO = "PNG";
    public static final String BASE_PROFILE_IMAGE_TEXT = "ProfileImage.png";   
    
    public static final int NUMBER1000 = 1000;   /*2842*/ 
    
    public static final String URL_TO_DOWNLOAD_OFAC = "https://www.treasury.gov/ofac/downloads/sdn.csv";
    public static final String FILENAME = "/home/jonatan/Documentos/" + LocalDate.now() + "-sdn.csv";
    
// "externalId":"1234564563213","submittedOnDate":"26 septiembre 2022",,"dateFormat":"dd MMMM yyyy","monthDayFormat":"dd MMM","charges":[],"clientId":"84"}
    
    private Constants() {
    }
}
