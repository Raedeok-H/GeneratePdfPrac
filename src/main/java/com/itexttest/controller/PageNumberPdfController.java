package com.itexttest.controller;


import com.itexttest.service.PageNumberPdfService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PageNumberPdfController {

    private final PageNumberPdfService pdfService;

    public PageNumberPdfController(PageNumberPdfService pdfService) {
        this.pdfService = pdfService;
    }

    @GetMapping("/generate-page-number-pdf")
    public ResponseEntity<byte[]> generatePdf() {
        byte[] pdfBytes = pdfService.createPdfWithPageNumbers();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "page_number_pdf.pdf");

        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }
}