package com.itexttest.service;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.TextAlignment;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class HtmlToPdfService {
    private static final String FONT_PATH = "src/main/resources/fonts/NanumGothic.ttf";

    public byte[] convertHtmlToPdf(String htmlFilePath) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try {
            // 1. HTML을 임시 PDF 파일로 변환
            String tempHtmlToPdfPath = "tempHtmlToPdf.pdf";
            try (PdfWriter tempWriter = new PdfWriter(tempHtmlToPdfPath);
                 PdfDocument tempPdfDoc = new PdfDocument(tempWriter)) {
                ConverterProperties converterProperties = new ConverterProperties();
                converterProperties.setBaseUri(new File(htmlFilePath).getParent());
                HtmlConverter.convertToPdf(new FileInputStream(htmlFilePath), tempPdfDoc, converterProperties);
            }

            // 2. 기존 PdfDocument에 HTML 변환된 페이지 추가
            PdfWriter writer = new PdfWriter(outputStream);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc, PageSize.A4);
            pdfDoc.setDefaultPageSize(PageSize.A4);

            // 임시 PDF 파일의 모든 페이지를 기존 pdfDoc에 추가
            PdfDocument tempHtmlPdfDoc = new PdfDocument(new PdfReader(tempHtmlToPdfPath));
            tempHtmlPdfDoc.copyPagesTo(1, tempHtmlPdfDoc.getNumberOfPages(), pdfDoc);
            tempHtmlPdfDoc.close(); // 임시 문서 닫기

            // 3. 두 번째 페이지에 PDF 직접 작업
            PdfPage secondPage = pdfDoc.addNewPage();  // 두 번째 페이지 생성
            PdfCanvas canvas = new PdfCanvas(secondPage); // 새 페이지에 PdfCanvas 생성
            PdfFont koreanFont = PdfFontFactory.createFont(FONT_PATH, PdfFontFactory.EmbeddingStrategy.PREFER_EMBEDDED);

            // 텍스트 박스 추가 (예: 박스 그리기)
            canvas.setStrokeColor(ColorConstants.BLUE)
                    .setLineWidth(2)
                    .rectangle(100, 500, 400, 100) // 위치와 크기 설정
                    .stroke();

            // 페이지 중앙에 텍스트 추가 (위치를 직접 지정)
            Paragraph paragraph = new Paragraph("직접 생성한 PDF 페이지")
                    .setFont(koreanFont)
                    .setFontSize(24)
                    .setTextAlignment(TextAlignment.CENTER);
            paragraph.setFixedPosition(2, 100, 600, 400); // 페이지 중앙으로 위치 설정
            document.add(paragraph);
            document.flush(); // 현재 페이지 레이아웃 종료


            // 텍스트 박스 내 텍스트 추가
            Paragraph boxText = new Paragraph("이것은 박스 안에 있는 텍스트입니다.")
                    .setFont(koreanFont)
                    .setFontSize(16)
                    .setTextAlignment(TextAlignment.CENTER);
            boxText.setFixedPosition(2, 120, 540, 360); // 박스 내 텍스트 위치 설정
            document.add(boxText);

            // 두 번째 페이지 완료 후 페이지 브레이크 추가
            document.add(new AreaBreak());

            // 4. HTML 변환한 첫 번째 페이지를 다시 추가 (3번째 페이지)
            PdfDocument tempHtmlPdfDoc2 = new PdfDocument(new PdfReader(tempHtmlToPdfPath));
            tempHtmlPdfDoc2.copyPagesTo(1, tempHtmlPdfDoc2.getNumberOfPages(), pdfDoc);
            tempHtmlPdfDoc2.close(); // 임시 문서 닫기

            // 임시 파일 삭제
            Files.deleteIfExists(Paths.get(tempHtmlToPdfPath));

            // 문서 닫기
            document.close();
            pdfDoc.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return outputStream.toByteArray();
    }
}
