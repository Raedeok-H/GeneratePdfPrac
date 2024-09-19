package com.itexttest.service;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.TextAlignment;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

@Service
public class PdfService {

    // 폰트 경로 설정
    private static final String FONT_PATH = "src/main/resources/fonts/NanumGothic.ttf";
    private static final String BOLD_FONT_PATH = "src/main/resources/fonts/NanumGothicBold.ttf";

    // 이미지 경로 하드코딩 (학습물의 배경 이미지 및 상장 테두리 이미지)
    private static final String IMAGE_PATH = "src/main/resources/image/main.png";
    private static final String CERTIFICATE_BORDER_IMAGE_PATH = "src/main/resources/image/round.png"; // 상장 테두리

    public byte[] createPdf(String childName) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try {
            // PDFWriter 생성
            PdfWriter writer = new PdfWriter(outputStream);
            PdfDocument pdfDoc = new PdfDocument(writer);  // PdfDocument 사용
            Document document = new Document(pdfDoc, PageSize.A4);

            // 1. 첫 번째 페이지: 제목, 이미지, 어린이 이름 추가
            PdfFont koreanFont = PdfFontFactory.createFont(FONT_PATH, PdfFontFactory.EmbeddingStrategy.PREFER_EMBEDDED);
            PdfFont boldKoreanFont = PdfFontFactory.createFont(BOLD_FONT_PATH, PdfFontFactory.EmbeddingStrategy.PREFER_EMBEDDED);

            // 첫 번째 페이지 내용 추가
            Paragraph titleParagraph = new Paragraph("\n\n어린이 학습물\n\n")
                    .setFont(boldKoreanFont)
                    .setFontSize(24)
                    .setTextAlignment(TextAlignment.CENTER);
            document.add(titleParagraph);

            // 메인 이미지 추가 (중앙 정렬)
            Image img = new Image(ImageDataFactory.create(IMAGE_PATH));
            img.scaleToFit(400, 300); // 이미지 크기 조정
            img.setHorizontalAlignment(com.itextpdf.layout.properties.HorizontalAlignment.CENTER); // 이미지 수평 중앙 정렬
            document.add(img);

            // 어린이 이름 텍스트 추가
            String content = "\n\n\n" + childName + " 어린이, 안녕하세요!";
            Paragraph bodyParagraph = new Paragraph(content)
                    .setFont(koreanFont)
                    .setFontSize(16)
                    .setTextAlignment(TextAlignment.CENTER);
            document.add(bodyParagraph);

            // 첫 번째 페이지 종료 후 페이지 전환
            document.add(new AreaBreak());

            // 2. 두 번째 페이지에 배경 이미지 추가
            PdfCanvas canvas = new PdfCanvas(pdfDoc.getLastPage()); // PdfCanvas로 배경 그리기
            ImageData borderImage = ImageDataFactory.create(CERTIFICATE_BORDER_IMAGE_PATH);
            canvas.addImageWithTransformationMatrix(
                    borderImage,
                    PageSize.A4.getWidth(), 0, 0, PageSize.A4.getHeight(), 0, 0); // 배경 이미지 추가

            // 3. 두 번째 페이지에 상장 제목 추가
            Paragraph titleCertificate = new Paragraph("상장")
                    .setFont(boldKoreanFont)
                    .setFontSize(60)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setMarginTop(100); // 상장 제목 중간 상단 배치
            document.add(titleCertificate); // 두 번째 페이지에 상장 제목 추가

            // 4. 상장 내용 추가
            Paragraph contentCertificate = new Paragraph("\n\n\n\n\n이 상장은 " + childName + " 어린이가\n우수한 성적을 거두었음을 증명합니다.")
                    .setFont(koreanFont)
                    .setFontSize(16)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setMarginTop(50); // 상장 내용 중앙 배치
            document.add(contentCertificate); // 두 번째 페이지에 상장 내용 추가

            // 5. 상장 내용 추가
            Paragraph date = new Paragraph("\n\n\n\nYYYY년 MM월 DD일\n 내가 줌.")
                    .setFont(koreanFont)
                    .setFontSize(20)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setMarginTop(50); // 상장 내용 중앙 배치
            document.add(date); // 두 번째 페이지에 상장 내용 추가



            // 세 번째 페이지로 전환
            document.add(new AreaBreak());

            // 왼쪽 하단이 (0,0) 이고 우측상단이 (595, 842)이다.

            // 왼쪽 하단부터 위로 쓰이는 글씨
            PdfCanvas canvas1 = new PdfCanvas(pdfDoc.getLastPage());
            canvas1.beginText()
                    .setFontAndSize(koreanFont, 12)
                    .moveText(50, 50)  // 왼쪽 하단 좌표
                    .setTextMatrix(0, 1, -1, 0, 50, 50)  // 수직 방향 텍스트 설정
                    .showText("왼쪽 하단에서 위로 쓰이는 텍스트")
                    .endText();

            // 위에서 아래로 쓰이는 글씨 (왼쪽 하단의 위쪽에 위치)
            PdfCanvas canvas4 = new PdfCanvas(pdfDoc.getLastPage());
            canvas4.beginText()
                    .setFontAndSize(koreanFont, 12)
                    .moveText(70, 500)  // 왼쪽 상단 좌표
                    .setTextMatrix(0, -1, 1, 0, 70, 500)  // 위에서 아래로 설정
                    .showText("위에서 아래로 쓰이는 텍스트")
                    .endText();

            // 세로로 쓰인 글씨를 중앙에 배치하기 위해 한 글자씩 줄바꿈 추가
            String verticalText = "세\n로\n로\n\n쓰\n인\n\n글\n씨";
            Paragraph verticalParagraph = new Paragraph(verticalText)
                    .setFont(koreanFont)
                    .setFontSize(16)
                    .setTextAlignment(com.itextpdf.layout.properties.TextAlignment.CENTER)
                    .setFixedPosition(PageSize.A4.getWidth() / 2 - 50, 150, 100); // 페이지 중앙으로 위치 설정

            document.add(verticalParagraph);

            // 왼쪽 하단부터 우측 상단 대각선 글씨
            PdfCanvas canvas2 = new PdfCanvas(pdfDoc.getLastPage());
            canvas2.beginText()
                    .setFontAndSize(koreanFont, 12)
                    .moveText(50, 50)  // 왼쪽 하단 좌표
                    .setTextMatrix(0.707f, 0.707f, -0.707f, 0.707f, 50, 50)  // 대각선 설정 (45도)
                    .showText("대각선으로 쓰인 글씨")
                    .endText();

            // 하단 중간부터 우측 하단으로 쓰인 글씨
            PdfCanvas canvas3 = new PdfCanvas(pdfDoc.getLastPage());
            canvas3.beginText()
                    .setFontAndSize(koreanFont, 12)
                    .moveText(200, 100)  // 하단 중간 좌표
                    .setTextMatrix(1, 0, 0, 1, 200, 100)  // 가로로 쓰인 글씨
                    .showText("하단 중간부터 우측 하단으로 쓰인 글씨")
                    .endText();

            // 문서 닫기
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return outputStream.toByteArray();
    }
}
