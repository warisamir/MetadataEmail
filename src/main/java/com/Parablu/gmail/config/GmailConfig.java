package com.Parablu.gmail.config;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.InputStream;
import java.util.Collections;
@Configuration
public class GmailConfig {
    @Configuration
    public class GoogleConfig {

        @Value("${google.service.account.key.path}")
        private String credentialsPath;

        @Value("${google.admin.email}")
        private String adminEmail;

        @Value("${google.app.name}")
        private String applicationName;

        @Bean
        public Gmail getGmailService() throws Exception {
            InputStream serviceAccountStream = getClass().getResourceAsStream(credentialsPath);
            GoogleCredential credential = GoogleCredential.fromStream(serviceAccountStream)
                    .createScoped(Collections.singleton(GmailScopes.GMAIL_READONLY))
                    .createDelegated(adminEmail);

            return new Gmail.Builder(
                    credential.getTransport(),
                    credential.getJsonFactory(),
                    credential
            ).setApplicationName(applicationName).build();
        }
    }
}

