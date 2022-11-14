package dgcd.financier.test.support.queryCounter;

import lombok.Getter;

@Getter
public class QueryCountInfo {

    private int selectCount;
    private int nextvalCount;
    private int insertCount;
    private int updateCount;
    private int deleteCount;
    private int callCount;


    public void incrementSelectCount() {
        selectCount++;
    }

    public void incrementInsertCount() {
        insertCount++;
    }

    public void incrementNextvalCount() {
        nextvalCount++;
    }

    public void incrementUpdateCount() {
        updateCount++;
    }

    public void incrementDeleteCount() {
        deleteCount++;
    }

    public void incrementCallCount() {
        callCount++;
    }


    public void clear() {
        selectCount = 0;
        nextvalCount = 0;
        insertCount = 0;
        updateCount = 0;
        deleteCount = 0;
        callCount = 0;
    }


    /**
     * Не учитывает Nextval
     */
    public int countAll() {
        return selectCount + insertCount + updateCount + deleteCount + callCount;
    }


    public String getStatsString() {
        return String.format(
                "select: %s, nextval: %s, insert: %s, update: %s, delete: %s, call: %s",
                selectCount,
                nextvalCount,
                insertCount,
                updateCount,
                deleteCount,
                callCount
        );
    }

}
