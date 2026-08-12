package com.resumeforge.pdf;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

@Service
public class PdfResumeService {

    public byte[] generateResumePdf(String resumeContent) {

        try {

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            Document document = new Document();

            PdfWriter.getInstance(document, outputStream);

            document.open();

            Font titleFont = FontFactory.getFont(
                    FontFactory.HELVETICA_BOLD,
                    18
            );

            Font bodyFont = FontFactory.getFont(
                    FontFactory.HELVETICA,
                    11
            );

            document.add(new Paragraph("ResumeForge AI", titleFont));
            document.add(new Paragraph(" "));
            document.add(new Paragraph(resumeContent, bodyFont));

            document.close();

            return outputStream.toByteArray();

        } catch (Exception ex) {
            throw new RuntimeException("Failed to generate PDF.", ex);
        }
    }
}