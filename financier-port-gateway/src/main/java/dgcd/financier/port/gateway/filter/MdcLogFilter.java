package dgcd.financier.port.gateway.filter;

import dgcd.financier.port.gateway.WebConstants;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

@Component
public class MdcLogFilter implements Filter {

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain
    ) throws IOException, ServletException {
        var rqId = UUID.randomUUID().toString().substring(0, 8);
        try (var ignored = MDC.putCloseable(WebConstants.MDC_RQ_ID, rqId)) {
            chain.doFilter(request, response);
        }
    }

}
