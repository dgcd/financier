package dgcd.financier.test.support.queryCounter;

public class QueryCountService {

    private final static QueryCountService INSTANCE = new QueryCountService();

    public static QueryCountService getInstance() {
        return INSTANCE;
    }


    private final ThreadLocal<QueryCountInfo> QUERY_INFO_HOLDER = ThreadLocal.withInitial(QueryCountInfo::new);


    private QueryCountService() {
    }


    public void clear() {
        QUERY_INFO_HOLDER.get().clear();
    }

    public int countAll() {
        return QUERY_INFO_HOLDER.get().countAll();
    }

    public String getStatsString() {
        return QUERY_INFO_HOLDER.get().getStatsString();
    }

    public int getCount(QueryType queryType) {
        return switch (queryType) {
            case SELECT -> QUERY_INFO_HOLDER.get().getSelectCount();
            case NEXTVAL -> QUERY_INFO_HOLDER.get().getNextvalCount();
            case INSERT -> QUERY_INFO_HOLDER.get().getInsertCount();
            case UPDATE -> QUERY_INFO_HOLDER.get().getUpdateCount();
            case DELETE -> QUERY_INFO_HOLDER.get().getDeleteCount();
            case CALL -> QUERY_INFO_HOLDER.get().getCallCount();
        };
    }


    public void handleSql(String sql) {
        var queryType = getQueryType(sql);
        var queryCountInfo = QUERY_INFO_HOLDER.get();
        switch (queryType) {
            case SELECT -> queryCountInfo.incrementSelectCount();
            case NEXTVAL -> queryCountInfo.incrementNextvalCount();
            case INSERT -> queryCountInfo.incrementInsertCount();
            case UPDATE -> queryCountInfo.incrementUpdateCount();
            case DELETE -> queryCountInfo.incrementDeleteCount();
            case CALL -> queryCountInfo.incrementCallCount();
        }
    }


    private QueryType getQueryType(String query) {
        var trimmedQuery = query
                .toLowerCase()
                .replaceAll("--.*\n", "")
                .replaceAll("\n", "")
                .replaceAll("/\\*.*\\*/", "")
                .trim();

        if (trimmedQuery.startsWith("select nextval")) {
            return QueryType.NEXTVAL;
        }

        var firstChar = trimmedQuery.charAt(0);
        return switch (firstChar) {
            case 'w', 's' -> QueryType.SELECT; // query can be started 'with'
            case 'i' -> QueryType.INSERT;
            case 'u' -> QueryType.UPDATE;
            case 'd' -> QueryType.DELETE;
            case 'c', '?' -> QueryType.CALL;
            default -> throw new RuntimeException("Unknown QueryType: " + trimmedQuery);
        };
    }

}
