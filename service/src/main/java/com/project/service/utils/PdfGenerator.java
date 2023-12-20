package com.project.service.utils;

import com.project.domain.relations.ArrangementInOrder;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PdfGenerator {
    public static byte[] generatePdfStream(ArrangementInOrder arrangementInOrder) throws IOException {
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
            contentStream.beginText();
            contentStream.setFont(PDType1Font.TIMES_BOLD, 14);
            contentStream.setLeading(15f);
            contentStream.newLineAtOffset(50, 750);

            contentStream.showText("-----Order Details-----");
            contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
            contentStream.newLineAtOffset(0, -20);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

            contentStream.showText("Villa: " + arrangementInOrder.getArrangement().getAccommodation().getName());
            contentStream.newLine();
            contentStream.showText("Destination: " + arrangementInOrder.getArrangement().getAccommodation().getDestination());
            contentStream.newLine();
            contentStream.showText("Country/region: " + arrangementInOrder.getArrangement().getAccommodation().getPlace().getName());
            contentStream.newLine();
            contentStream.showText("From Date: " + arrangementInOrder.getFromDate().format(formatter));
            contentStream.newLine();
            contentStream.showText("To Date: " + arrangementInOrder.getToDate().format(formatter));
            contentStream.newLine();
            contentStream.showText("Customer: " + arrangementInOrder.getOrder().getUser().getUsername());
            contentStream.newLine();
            contentStream.showText("Price: " + String.format("%.2f", arrangementInOrder.getPrice()) +" €");
            contentStream.newLine();
            contentStream.newLine();

            LocalDateTime currentDateTime = LocalDateTime.now();
            formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
            String formattedDateTime = currentDateTime.format(formatter);
            contentStream.showText("Generated at: " + formattedDateTime +"h");
            contentStream.endText();
        }

        document.save(outputStream);
        document.close();

        return outputStream.toByteArray();
    }
}