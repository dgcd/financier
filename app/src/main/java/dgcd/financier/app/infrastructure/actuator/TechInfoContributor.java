package dgcd.financier.app.infrastructure.actuator;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootVersion;
import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Map;

@Component
public class TechInfoContributor implements InfoContributor {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");


    private final String appName;
    private final String appVersion;
    private final String appBuildtime;
    private final String springProfiles;
    private final String springBootVersion;
    private final String hostname;
    private final String javaVersion;
    private final String javaVmInfo;

    @SneakyThrows
    public TechInfoContributor(
            @Value("${spring.application.name}") String appName,
            DeploymentProperties deploymentProperties,
            Environment env
    ) {
        this.appName = appName;
        this.appVersion = deploymentProperties.version();
        this.appBuildtime = deploymentProperties.buildtime();
        this.springProfiles = Arrays.toString(env.getActiveProfiles());
        this.springBootVersion = SpringBootVersion.getVersion();
        this.hostname = InetAddress.getLocalHost().getHostName();
        this.javaVersion = System.getProperty("java.version");
        this.javaVmInfo = String.format(
                "%s - %s (build %s, %s)",
                System.getProperty("java.vm.name"),
                System.getProperty("java.vendor"),
                System.getProperty("java.vm.version"),
                System.getProperty("java.vm.info")
        );
    }


    @Override
    public void contribute(Info.Builder builder) {
        builder.withDetail("techInfo", getTechInfo());
    }

    public Map<String, String> getTechInfo() {
        return Map.of(
                "appName", appName,
                "appVersion", appVersion,
                "appBuildtime", appBuildtime,
                "springProfiles", springProfiles,
                "springBootVersion", springBootVersion,
                "hostname", hostname,
                "javaVersion", javaVersion,
                "javaVmInfo", javaVmInfo,
                "currentTime", LocalDateTime.now().format(FORMATTER)
        );
    }

}
