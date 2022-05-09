package com.abc.agency.job.util;

public class Constants {

    private Constants(){}

    public static final String API_REGISTER_USER = "/api/registerUser";
    public static final String API_AUTHENTICATE = "/api/authenticate";
    public static final String API_GET_USER_DETAILS = "/api/getUserDetails";
    public static final String API_UPDATE_USER = "/api/updateUser";
    public static final String API_DOWNLOAD_CV = "/api/downloadCV";
    public static final String API_DOWNLOAD_USER_CV = "/api/downloadUserCV";
    public static final String API_SEARCH_CVS = "/api/searchCVs";

    public static final String REPORT_TEMPLATE_CV = "CV_template.jrxml";


    public static final String REPORT_DIRECTORY= "classpath:reports/";
    public static final String REPORT_PARAM_SUBREPORT_DIRECTORY= "SUBREPORT_DIR";

    public static final String APPLICATION_TYPE_PDF = "application/pdf";
    public static final String ATTACHMENT_NAME_ATTR = "attachment; filename=\"";
    public static final String DOUBLE_QUOTE = "\"";

    public static final String FILE_NAME_CV= "CV.pdf";


    public static final String ERROR_MESSAGE_USER_NOT_FOUND = "User not found";

}
