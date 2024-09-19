package com.itexttest.service;

import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.forms.fields.PdfButtonFormField;
import com.itextpdf.forms.fields.PdfFormField;
import com.itextpdf.forms.fields.PdfTextFormField;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

@Service
public class PdfFormService {

    public byte[] createFormPdf() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try {
            // PDFWriter 생성
            PdfWriter writer = new PdfWriter(outputStream);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc, PageSize.A4);

            // 폰트 설정
            PdfFont font = PdfFontFactory.createFont("src/main/resources/fonts/NanumGothic.ttf", "Identity-H");

            // 폼 필드 생성
            PdfAcroForm form = PdfAcroForm.getAcroForm(pdfDoc, true);

            // 1. 텍스트 필드 추가
            PdfTextFormField nameField = PdfTextFormField.createText(
                    pdfDoc, new com.itextpdf.kernel.geom.Rectangle(150, 750, 200, 20),
                    "name", ""); // 필드 이름과 초기값 설정
            nameField.setFont(font).setFontSize(12);
            nameField.setBorderColor(com.itextpdf.kernel.colors.ColorConstants.BLUE);
            form.addField(nameField);

            // 2. 체크박스 추가
            PdfButtonFormField checkBox = PdfFormField.createCheckBox(
                    pdfDoc, new com.itextpdf.kernel.geom.Rectangle(150, 700, 20, 20),
                    "agreement", "Yes");
            form.addField(checkBox);

            // 3. 라디오 버튼 추가
            PdfButtonFormField radioGroup = PdfFormField.createRadioGroup(pdfDoc, "gender", "Male");
            PdfFormField.createRadioButton(pdfDoc, new com.itextpdf.kernel.geom.Rectangle(150, 650, 20, 20), radioGroup, "Male");
            PdfFormField.createRadioButton(pdfDoc, new com.itextpdf.kernel.geom.Rectangle(200, 650, 20, 20), radioGroup, "Female");
            form.addField(radioGroup);

            // 4. 버튼 추가
            PdfButtonFormField button = PdfFormField.createPushButton(
                    pdfDoc, new com.itextpdf.kernel.geom.Rectangle(150, 600, 100, 20),
                    "submit", "Submit");
            button.setFont(font).setFontSize(12);
            form.addField(button);

            // 필드 설명 텍스트 추가
            document.add(new Paragraph("이름을 입력하세요: ").setFont(font).setFontSize(12).setFixedPosition(50, 750, 100));
            document.add(new Paragraph("동의: ").setFont(font).setFontSize(12).setFixedPosition(50, 700, 100));
            document.add(new Paragraph("성별: ").setFont(font).setFontSize(12).setFixedPosition(50, 650, 100));
            document.add(new Paragraph("제출 버튼: ").setFont(font).setFontSize(12).setFixedPosition(50, 600, 100));

            // 문서 닫기
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return outputStream.toByteArray();
    }
}