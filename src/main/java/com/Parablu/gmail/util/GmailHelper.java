package com.Parablu.gmail.util;
import com.Parablu.gmail.model.EmailMetadata;
import com.google.api.services.gmail.model.Message;
import com.google.api.services.gmail.model.MessagePart;
import com.google.api.services.gmail.model.MessagePartHeader;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class GmailHelper {
    public static EmailMetadata extractMetadata(Message message) {
        EmailMetadata metadata = new EmailMetadata();

        try {
            metadata.setId(message.getId());
            metadata.setSnippet(message.getSnippet());

            MessagePart payload = message.getPayload();
            if (payload != null && payload.getHeaders() != null) {
                List<MessagePartHeader> headers = payload.getHeaders();
                for (MessagePartHeader header : headers) {
                    String name = header.getName();
                    String value = header.getValue();

                    switch (name) {
                        case "From" -> metadata.setFrom(value);
                        case "To" -> metadata.setTo(value);
                        case "Subject" -> metadata.setSubject(value);
                    }
                }
            }
        } catch (Exception e) {
            log.error("Error extracting metadata from message: {}", e.getMessage(), e);
        }

        return metadata;
    }
}
