package com.Parablu.gmail.model;
import lombok.Data;


@Data
public class EmailMetadata {
    private String id;
    private String snippet;
    private String from;
    private String to;
    private String subject;
}


