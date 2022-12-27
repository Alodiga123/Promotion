package com.alodiga.promotions.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties specific to Promotions.
 * <p>
 * Properties are configured in the {@code application.yml} file.
 * See {@link io.github.jhipster.config.JHipsterProperties} for a good example.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

    
    public final Banner banner = new Banner();

    public Banner getBanner() {
        return banner;
    }

    public static class Banner {


        private String source_image_profile = "";
        private String address_image_profile = "";

       
        public String getSource_image_profile() {
            return source_image_profile;
        }

        public void setSource_image_profile(String source_image_profile) {
            this.source_image_profile = source_image_profile;
        }

        public String getAddress_image_profile() {
            return address_image_profile;
        }

        public void setAddress_image_profile(String address_image_profile) {
            this.address_image_profile = address_image_profile;
        }
        
        


    }
}
