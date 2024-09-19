package com.itexttest.controller;

import com.itexttest.service.PdfFormService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PdfFormController {
    private final PdfFormService pdfFormService;

    @GetMapping("/generate-form-pdf")
    public ResponseEntity<byte[]> generateFormPdf() {
        byte[] pdfBytes = pdfFormService.createFormPdf();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "learning-material.pdf");

        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }

}
