package com.itexttest.service;

import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.AreaBreakType;
import com.itextpdf.layout.properties.TextAlignment;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

@Service
public class PageNumberPdfService {

    public byte[] createPdfWithPageNumbers() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try {
            PdfWriter writer = new PdfWriter(outputStream);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc, PageSize.A4);

            // 첫 번째 페이지
            Paragraph pageInfo1 = new Paragraph("Page 1 of 3")
                    .setFontSize(18)
                    .setTextAlignment(TextAlignment.CENTER);
            document.add(pageInfo1);

            // 페이지를 명확하게 넘기기 위해 AreaBreak 사용
            document.add(new AreaBreak(AreaBreakType.NEXT_PAGE));

            // 두 번째 페이지
            Paragraph pageInfo2 = new Paragraph("Page 2 of 3")
                    .setFontSize(18)
                    .setTextAlignment(TextAlignment.CENTER);
            document.add(pageInfo2);

            document.add(new AreaBreak(AreaBreakType.NEXT_PAGE));

            // 세 번째 페이지
            Paragraph pageInfo3 = new Paragraph("Page 3 of 3")
                    .setFontSize(18)
                    .setTextAlignment(TextAlignment.CENTER);
            document.add(pageInfo3);

            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return outputStream.toByteArray();
    }
}