package dgcd.financier.app.infrastructure.actuator;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootVersion;
import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.boot.info.BuildProperties;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.LinkedHashMap;
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


    public TechInfoContributor(
            @Value("${spring.application.name}") String appName,
            Environment env,
            BuildProperties buildProperties
    ) {
        this.appName = appName;
        this.appVersion = buildProperties.getVersion();
        this.appBuildtime = LocalDateTime.ofInstant(buildProperties.getTime(), ZoneId.systemDefault()).format(FORMATTER);
        this.springProfiles = Arrays.toString(env.getActiveProfiles());
        this.springBootVersion = SpringBootVersion.getVersion();
        try {
            this.hostname = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
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
        var map = new LinkedHashMap<String, String>();
        map.put("appName", appName);
        map.put("appVersion", appVersion);
        map.put("appBuildtime", appBuildtime);
        map.put("springProfiles", springProfiles);
        map.put("springBootVersion", springBootVersion);
        map.put("hostname", hostname);
        map.put("javaVersion", javaVersion);
        map.put("javaVmInfo", javaVmInfo);
        map.put("currentTime", LocalDateTime.now().format(FORMATTER));
        return map;
    }

}
