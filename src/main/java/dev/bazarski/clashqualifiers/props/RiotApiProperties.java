package dev.bazarski.clashqualifiers.props;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "api")
public class RiotApiProperties {
    private String token;
    private String generalUrl;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getGeneralUrl() {
        return generalUrl;
    }

    public void setGeneralUrl(String generalUrl) {
        this.generalUrl = generalUrl;
    }
}
