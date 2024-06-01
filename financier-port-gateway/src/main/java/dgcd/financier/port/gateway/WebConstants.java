package dgcd.financier.port.gateway;

public final class WebConstants {

    private static final String API_PATH = "/api";

    public static final String INIT_PATH = API_PATH + "/init";

    public static final String ACCOUNTS_PATH = API_PATH + "/accounts";
    public static final String ACCOUNTS_CREATE_PATH = ACCOUNTS_PATH + "/create";
    public static final String ACCOUNTS_CLOSE_PATH = ACCOUNTS_PATH + "/close";

    public static final String CATEGORIES_PATH = API_PATH + "/categories";
    public static final String CATEGORIES_CREATE_PATH = CATEGORIES_PATH + "/create";

    public static final String OPERATIONS_PATH = API_PATH + "/operations";
    public static final String OPERATIONS_CREATE_PATH = OPERATIONS_PATH + "/create";
    public static final String OPERATIONS_CANCEL_PATH = OPERATIONS_PATH + "/cancel";

    private static final String DATA_EXCEL_PATH = API_PATH + "/data";
    public static final String DATA_EXCEL_EXPORT_PATH = DATA_EXCEL_PATH + "/export";
    public static final String DATA_EXCEL_IMPORT_PATH = DATA_EXCEL_PATH + "/import";

    public static final String X_APPLICATION_XLSX_VALUE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

    public static final String ALLDATA_ATTACHMENT_NAMING = "attachment;filename=financier_data_%s.xlsx";

    public static final String MDC_RQ_ID = "rqId";

}
