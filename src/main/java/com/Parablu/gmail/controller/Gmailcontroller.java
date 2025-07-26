package com.Parablu.gmail.controller;
import com.Parablu.gmail.model.EmailMetadata;
import com.Parablu.gmail.service.GmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@Slf4j
@RestController
@RequestMapping("/api/gmail")

public class Gmailcontroller {
    @Autowired
    private GmailService gmailService;

    @GetMapping("/metadata/{userEmail}")
    public List<EmailMetadata> getEmailMetadata(@PathVariable String userEmail) {
        log.info("Request received to fetch email metadata for {}", userEmail);
        return gmailService.fetchMetadataForUser(userEmail);
    }
}
