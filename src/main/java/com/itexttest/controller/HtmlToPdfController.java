package com.itexttest.controller;

import com.itexttest.service.HtmlToPdfService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HtmlToPdfController {

    private final HtmlToPdfService htmlToPdfService;

    @GetMapping("/convert-html-to-pdf")
    public ResponseEntity<byte[]> convertHtmlToPdf() {
        byte[] pdfBytes = htmlToPdfService.convertHtmlToPdf("src/main/resources/templates/index.html");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "converted.pdf");

        return ResponseEntity.ok().headers(headers).body(pdfBytes);
    }
}
