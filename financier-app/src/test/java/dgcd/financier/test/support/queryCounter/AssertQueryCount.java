package dgcd.financier.test.support.queryCounter;

import static dgcd.financier.test.support.queryCounter.QueryType.CALL;
import static dgcd.financier.test.support.queryCounter.QueryType.DELETE;
import static dgcd.financier.test.support.queryCounter.QueryType.INSERT;
import static dgcd.financier.test.support.queryCounter.QueryType.NEXTVAL;
import static dgcd.financier.test.support.queryCounter.QueryType.SELECT;
import static dgcd.financier.test.support.queryCounter.QueryType.UPDATE;

public class AssertQueryCount {

    private final static QueryCountService queryCountService = QueryCountService.getInstance();

    public static void reset() {
        queryCountService.clear();
    }

    public static int countAll() {
        return queryCountService.countAll();
    }

    public static void assertSelectCount(int expectedCount) {
        assertQueryCount("select", expectedCount, queryCountService.getCount(SELECT));
    }

    public static void assertNextvalCount(int expectedCount) {
        assertQueryCount("select nextval", expectedCount, queryCountService.getCount(NEXTVAL));
    }

    public static void assertUpdateCount(int expectedCount) {
        assertQueryCount("update", expectedCount, queryCountService.getCount(UPDATE));
    }

    public static void assertInsertCount(int expectedCount) {
        assertQueryCount("insert", expectedCount, queryCountService.getCount(INSERT));
    }

    public static void assertDeleteCount(int expectedCount) {
        assertQueryCount("delete", expectedCount, queryCountService.getCount(DELETE));
    }

    public static void assertCallCount(int expectedCount) {
        assertQueryCount("call", expectedCount, queryCountService.getCount(CALL));
    }


    private static final String EXCEPTION_MESSAGE = "Expected %s query count: %d, actual: %d";

    private static void assertQueryCount(String statement, int expectedCount, int actualCount) {
        if (expectedCount != actualCount) {
            var message = String.format(EXCEPTION_MESSAGE, statement, expectedCount, actualCount);
            throw new RuntimeException(message);
        }
    }

}
