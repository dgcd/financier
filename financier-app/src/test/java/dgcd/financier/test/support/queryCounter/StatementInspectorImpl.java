package dgcd.financier.test.support.queryCounter;

import org.hibernate.resource.jdbc.spi.StatementInspector;

public class StatementInspectorImpl implements StatementInspector {

    private static final QueryCountService QUERY_HANDLER = QueryCountService.getInstance();

    @Override
    public String inspect(String sql) {
        QUERY_HANDLER.handleSql(sql);
        return sql;
    }

}
