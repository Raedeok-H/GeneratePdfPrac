package com.itexttest.service;

import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

@Service
public class PdfLayoutService {

    // 폰트 경로 설정
    private static final String FONT_PATH = "src/main/resources/fonts/NanumGothic.ttf";

    public byte[] createComplexPdf() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try {
            // PDFWriter 생성
            PdfWriter writer = new PdfWriter(outputStream);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc, PageSize.A4);

            // 폰트 설정
            PdfFont koreanFont = PdfFontFactory.createFont(FONT_PATH, PdfFontFactory.EmbeddingStrategy.PREFER_EMBEDDED);

            // 1. 헤더 추가
            Paragraph header = new Paragraph("복잡한 PDF 생성 예시")
                    .setFont(koreanFont)
                    .setFontSize(24)
                    .setBold()
                    .setTextAlignment(TextAlignment.CENTER);
            document.add(header);

            // 2. 간단한 텍스트 섹션
            Paragraph intro = new Paragraph("이 문서는 다양한 레이아웃과 스타일을 포함한 복잡한 PDF 예시입니다.")
                    .setFont(koreanFont)
                    .setFontSize(14)
                    .setTextAlignment(TextAlignment.LEFT);
            document.add(intro);

            // 3. 테이블 추가 (2x2 그리드)
            Table table = new Table(3); // 2열 테이블
            table.addCell(new Cell().add(new Paragraph("섹션 1").setFont(koreanFont).setBold()));
            table.addCell(new Cell().add(new Paragraph("섹션 2").setFont(koreanFont)));
            table.addCell(new Cell().add(new Paragraph("섹션 3").setFont(koreanFont)));
            table.addCell(new Cell().add(new Paragraph("섹션 4").setFont(koreanFont)));
            table.addCell(new Cell().add(new Paragraph("섹션 5").setFont(koreanFont)));
            table.addCell(new Cell().add(new Paragraph("섹션 6").setFont(koreanFont)));
            table.setFixedPosition(50, PageSize.A4.getHeight() - 50, 500); // 테이블의 고정 위치 설정 (x, y, 너비)
            document.add(table);

            // 4. 푸터를 우측 하단에 고정
            Paragraph footer = new Paragraph("© 2024, 복잡한 PDF 예시")
                    .setFont(koreanFont)
                    .setFontSize(10)
                    .setFontColor(ColorConstants.GRAY)
                    .setTextAlignment(TextAlignment.RIGHT);

            // 푸터의 고정 위치 설정 (우하단 좌표)
            float x = PageSize.A4.getWidth() - 250; // 페이지 너비에서 200포인트를 뺀 위치
            float y = 20; // 페이지 하단에서 20포인트 위쪽 여백
            footer.setFixedPosition(x, y, 200); // 우측 하단에 고정 (너비 설정 얼추 맞춰서 꼭 넣어줘야 함.)
            document.add(footer);

            // 문서 닫기
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return outputStream.toByteArray();
    }
}
