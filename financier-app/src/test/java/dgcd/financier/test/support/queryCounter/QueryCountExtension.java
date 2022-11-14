package dgcd.financier.test.support.queryCounter;

import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class QueryCountExtension implements BeforeEachCallback, ExtensionContext.Store.CloseableResource {

    @Override
    public void beforeEach(ExtensionContext context) {
        AssertQueryCount.reset();
    }

    @Override
    public void close() {
    }

}
