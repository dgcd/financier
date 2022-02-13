package dgcd.financier.app.modules.init;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TestStarter {

    private final DeploymentProperties deploymentProperties;

    @EventListener(ApplicationStartedEvent.class)
    public void started() {
        log.info(deploymentProperties.toString());
    }

}
