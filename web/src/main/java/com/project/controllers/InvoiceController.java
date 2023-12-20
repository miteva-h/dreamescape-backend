package com.project.controllers;

import com.project.domain.dto.InvoiceDTO;
import com.project.domain.relations.ArrangementInOrder;
import com.project.service.OrderService;
import com.project.service.utils.PdfGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/invoices")
public class InvoiceController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<byte[]> exportPdf(@RequestBody InvoiceDTO invoiceDTO) {
        try {
            ArrangementInOrder arrangementInOrder = orderService.getArrangmentInOrderDetails(invoiceDTO.getId());
            byte[] pdfData = PdfGenerator.generatePdfStream(arrangementInOrder);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDisposition(ContentDisposition.attachment().filename("document.pdf").build());
            return new ResponseEntity<>(pdfData, headers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}