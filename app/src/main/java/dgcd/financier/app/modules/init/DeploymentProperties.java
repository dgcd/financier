package dgcd.financier.app.modules.init;

import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Setter
@ToString
@Component
@PropertySource("classpath:deployment.properties")
public class DeploymentProperties {

    @Value("${version}")
    private String version;

    @Value("${buildtime}")
    private String buildtime;

}
