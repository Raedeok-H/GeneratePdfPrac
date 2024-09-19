package com.itexttest.controller;

import com.itexttest.service.HtmlToPdfService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping("/generate-pdf-use-template")
    public ResponseEntity<byte[]> generatePdf(
            @RequestParam String title,
            @RequestParam String description,
            @RequestParam String footerText) {

        // HTML 템플릿을 사용하여 PDF 생성
        byte[] pdfBytes = htmlToPdfService.convertHtmlToPdfWithTemplate(
                "dynamic", title, description, footerText);

        // PDF 파일을 ResponseEntity로 반환
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("inline", "generated.pdf");
        return ResponseEntity.ok()
                .headers(headers)
                .body(pdfBytes);
    }
}
