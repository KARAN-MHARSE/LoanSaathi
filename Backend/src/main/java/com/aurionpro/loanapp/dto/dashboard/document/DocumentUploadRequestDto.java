package com.aurionpro.loanapp.dto;

import org.springframework.web.multipart.MultipartFile;

import com.aurionpro.loanapp.property.DocumentType;

import lombok.Data;

@Data
public class DocumentUploadRequestDto {
	private DocumentType documentType;
	private MultipartFile file;  
}
