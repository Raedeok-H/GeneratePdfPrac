package com.itexttest.service;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

@Service
public class HtmlToPdfService {

    public byte[] convertHtmlToPdf(String htmlFilePath) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try {
            // PDFWriter 생성
            PdfWriter writer = new PdfWriter(outputStream);
            PdfDocument pdfDoc = new PdfDocument(writer);

            // A4 크기 지정 (여백 없이 설정)
            Document document = new Document(pdfDoc, new PageSize(595, 842));
            document.setMargins(0, 0, 0, 0); // 모든 여백을 0으로 설정

            // HTML 변환 속성 설정
            ConverterProperties converterProperties = new ConverterProperties();
            converterProperties.setBaseUri(new File(htmlFilePath).getParent());

            // HTML 파일을 읽어와서 PDF로 변환
            FileInputStream htmlSource = new FileInputStream(htmlFilePath);
            HtmlConverter.convertToPdf(htmlSource, pdfDoc, converterProperties);

            // 문서 닫기
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return outputStream.toByteArray();
    }
}
