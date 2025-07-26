package com.Parablu.gmail.service;


import com.Parablu.gmail.model.EmailMetadata;
import com.Parablu.gmail.util.GmailHelper;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.ListMessagesResponse;
import com.google.api.services.gmail.model.Message;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class GmailService {

    @Autowired
    private Gmail gmail;
    public List<EmailMetadata> fetchMetadataForUser(String userEmail) {
        List<EmailMetadata> metadataList = new ArrayList<>();
        try {
            ListMessagesResponse messagesResponse = gmail.users().messages()
                    .list(userEmail)
                    .setMaxResults(10L)
                    .execute();

            List<Message> messages = messagesResponse.getMessages();

            if (messages == null || messages.isEmpty()) {
                log.info("No messages found for user: {}", userEmail);
                return metadataList;
            }

            for (Message message : messages) {
                Message fullMessage = gmail.users().messages()
                        .get(userEmail, message.getId())
                        .setFormat("metadata")
                        .execute();

                EmailMetadata metadata = GmailHelper.extractMetadata(fullMessage);
                metadataList.add(metadata);
            }

            log.info("Fetched {} messages for user {}", metadataList.size(), userEmail);
        } catch (Exception e) {
            log.error("Failed to fetch metadata for user {}: {}", userEmail, e.getMessage());
        }
        return metadataList;
    }
}

