package com.aurionpro.loanapp.dto;

import com.aurionpro.loanapp.property.DocumentType;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class DocumentDto {
    private Long id;
    private DocumentType type;
    private String name;
    private String url;
    private LocalDateTime uploadedAt;
}