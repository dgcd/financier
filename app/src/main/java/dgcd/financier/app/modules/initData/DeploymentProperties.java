package dgcd.financier.app.modules.initData;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:deployment.properties")
public record DeploymentProperties(
        String version,
        String buildtime
) {

    public DeploymentProperties(
            @Value("${version}") String version,
            @Value("${buildtime}") String buildtime
    ) {
        this.version = version;
        this.buildtime = buildtime;
    }

}
