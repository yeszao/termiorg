package org.termi.common.configuration;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "termi")
@Component
@Data
public class TermiConfig {
    private String uploadDir;
    private String uploadBaseUrl;

    public String getUploadBaseUrl() {
        return StringUtils.stripEnd(uploadBaseUrl, "/") + "/";
    }
}
