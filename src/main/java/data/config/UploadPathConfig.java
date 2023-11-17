package data.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UploadPathConfig {

    @Value("${upload-path}")
    private String uploadPath;

    public String getUploadPath() {
        return uploadPath;
    }
}
