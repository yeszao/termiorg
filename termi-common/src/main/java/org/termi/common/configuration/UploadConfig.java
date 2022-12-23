package org.termi.common.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@ConfigurationProperties(prefix = "upload")
@Component
@Data
public class UploadConfig {
    private String dir;
    private String baseUrl;
}