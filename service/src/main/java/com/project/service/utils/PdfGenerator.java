package com.project.service.utils;

import com.project.domain.relations.ArrangementInOrder;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.TextAlignment;

import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

public class PdfGenerator {
    public static byte[] generatePdfStream(ArrangementInOrder arrangementInOrder) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PdfDocument pdfDocument = new PdfDocument(new PdfWriter(outputStream));
        Document document = new Document(pdfDocument);

        try {
            document.add(new Paragraph("-----Order Details-----")
                    .setTextAlignment(TextAlignment.CENTER));

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

            document.add(new Paragraph("Villa: " + arrangementInOrder.getArrangement().getAccommodation().getName()));
            document.add(new Paragraph("Destination: " + arrangementInOrder.getArrangement().getAccommodation().getDestination()));
            document.add(new Paragraph("Country/region: " + arrangementInOrder.getArrangement().getAccommodation().getPlace().getName()));
            document.add(new Paragraph("From Date: " + arrangementInOrder.getFromDate().format(formatter)));
            document.add(new Paragraph("To Date: " + arrangementInOrder.getToDate().format(formatter)));
            document.add(new Paragraph("Customer: " + arrangementInOrder.getOrder().getUser().getUsername()));
            document.add(new Paragraph("Price: " + String.format("%.2f", arrangementInOrder.getPrice()) + " €"));

            LocalDateTime currentDateTime = LocalDateTime.now();
            formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
            String formattedDateTime = currentDateTime.format(formatter);
            document.add(new Paragraph("Generated at: " + formattedDateTime + "h"));

        } finally {
            document.close();
        }

        return outputStream.toByteArray();
    }
}